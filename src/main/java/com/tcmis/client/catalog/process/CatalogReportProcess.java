package com.tcmis.client.catalog.process;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.sql.Connection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.*;
import com.tcmis.client.catalog.factory.FacAppUserGrpOvBeanFactory;
import com.tcmis.client.catalog.factory.PrCatalogScreenSearchBeanFactory;
import com.tcmis.client.catalog.factory.SpecDisplayViewBeanFactory;
import com.tcmis.client.catalog.factory.UserFacCatAppOvBeanFactory;
import com.tcmis.client.common.beans.*;
import com.tcmis.client.common.process.ItemCatalogProcess;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.AddSpecsBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class CatalogReportProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public CatalogReportProcess(String client) {
	 super(client);
 }
 
 public CatalogReportProcess(String client,String locale) {
	    super(client,locale);
}	

 public CatalogReportProcess(String client,Locale locale) {
	    super(client,locale);
 }


 public Collection getSearchResult(CatalogInputBean bean,BigDecimal personnelId) throws BaseException {
	 return getSearchResult(bean,personnelId,true);
 }
 
 public Collection getSearchResult(CatalogInputBean bean,BigDecimal personnelId, boolean searchFlag) throws BaseException {
	
		DbManager dbManager = new DbManager(getClient(),getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PrCatalogScreenSearchBean());
		Collection<PrCatalogScreenSearchBean> c = null;
		String workApproved = "Y";
		if(bean.getWorkAreaApprovedOnly() == null && StringUtils.isEmpty(bean.getApplicationId()))
		{
			workApproved = "N";
		}
		
		StringBuilder query= new StringBuilder("select a.*,nvl(fx_catalog_desc(a.company_id,a.catalog_id),a.catalog_id) catalog_desc from table (pkg_catalog_search.FX_CATALOG_SEARCH(");

		query.append( "'" ).append(bean.getCompanyId()).append("'" )
		.append(",'").append(bean.getFacilityId()).append("'" )
		.append(",'").append(bean.getCatalogId()).append("'" )
		.append(",'").append(StringHandler.emptyIfNull(bean.getApplicationId())).append("'" )
		.append(",'" ).append(workApproved).append("'" )
        .append(",'").append(StringHandler.emptyIfNull(bean.getSearchText())).append("'" ).append( ")) a");

		c = factory.selectQuery(query.toString());
		Iterator<PrCatalogScreenSearchBean> iter = c.iterator();
		while (iter.hasNext()) {
			PrCatalogScreenSearchBean pcssb = iter.next();
			getMaterialCategoryData(pcssb, factory);
		}
		
		return c;
 }
 
 
 public Collection getUserFacCatAppOvBeanColl(BigDecimal personnelId) throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS,personnelId.toString());
		UserFacCatAppOvBeanFactory factory = new UserFacCatAppOvBeanFactory(dbManager);
		return factory.selectObject(criteria);
	 }

 
 public Object getInventoryMenu(PrCatalogScreenSearchBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());

		PrCatalogScreenSearchBeanFactory factory = new
		 PrCatalogScreenSearchBeanFactory(dbManager);

		return factory.selectInventory(bean);
 }
 

 public String getKitMsdsNumber(PrCatalogScreenSearchBean bean) throws BaseException {
	DbManager dbManager = new DbManager(getClient());
	GenericSqlFactory factory = new GenericSqlFactory(dbManager);
	StringBuilder query = new StringBuilder("select msds_number from catalog_part_item_group where company_id = '"+bean.getCatalogCompanyId()).append("'");
	query.append(" and catalog_id = '").append(bean.getCatalogId()).append("' and cat_part_no = ").append(SqlHandler.delimitString(bean.getCatPartNo()));
	query.append(" and part_group_no = ").append(bean.getPartGroupNo()).append(" and item_id = ").append(bean.getItemId());
	return factory.selectSingleValue(query.toString());
 }
public ExcelHandler  getPartExcelReport(CatalogInputBean inputbean, BigDecimal personnelId) throws
	NoDataException, BaseException, Exception {
	 
	 Vector<PrCatalogScreenSearchBean> data = (Vector<PrCatalogScreenSearchBean>) getSearchResult(inputbean, personnelId, false);
	 ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	 ExcelHandler pw = new ExcelHandler(library);

	 pw.addTable();
	 
//	 write column headers
	 pw.addRow();
	 pw.addTdRegionBold(library.getString("label.facility")+":"+inputbean.getFacilityName(),1,3);
	 pw.addRow();
	 pw.addTdRegionBold(library.getString("label.workarea")+":"+inputbean.getApplicationDesc(),1,3);
	 pw.addRow();
	 pw.addRow();
	 
	 /*Pass the header keys for the Excel.*/
	 String[] headerkeys = {
			   "label.catalog",
			   "label.part",
			   "label.description",
			   "label.spec",
			   "label.item",
			   "label.material",
			   "inventory.label.componentdescription",
			   "label.grade",
			   "inventory.label.packaging",
			   "label.manufacturer",
			   "catalog.label.shelflife",
			   "label.mfgpartno"
	 			};
/*This array defines the type of the excel column.
 0 means default depending on the data type. */
	 int[] types = {
			 0,0,pw.TYPE_PARAGRAPH,0,0,0,pw.TYPE_PARAGRAPH,0,pw.TYPE_PARAGRAPH,0,0,0};
	 /*This array defines the default width of the column when the Excel file opens.
 0 means the width will be default depending on the data type.*/
	 int[] widths = {
			  8,8,15,12,8,10,15,8,15,15,10,15
			  };
	 	 /*This array defines the horizontal alignment of the data in a cell.
 0 means excel defaults the horizontal alignemnt by the data type.*/
	 int[] horizAligns = null;
	// pw.setColumnDigit(4,4); // final price 4 digit
	// pw.setColumnDigit(7,4); // unitOfSaleQuantityPerEach 4 digit
	 pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

//	 now write data
//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	 for(PrCatalogScreenSearchBean bean: data) {
		 pw.addRow();
		 pw.addCell(bean.getCatalogId());
		 pw.addCell(bean.getCatPartNo());
		 pw.addCell(bean.getPartDescription());
		 pw.addCell(bean.getSpecList());
		 pw.addCell(bean.getItemId());
		 pw.addCell(bean.getMaterialId());
		 pw.addCell(bean.getComponentDesc());
		 pw.addCell(bean.getGrade());
		 pw.addCell(bean.getComponentPackaging());
		 pw.addCell(bean.getManufacturerName());
		 String storageTemp = bean.getDisplayTemp();
		 if( storageTemp == null || storageTemp.trim().length() == 0 )
			 storageTemp = "";
		 else {
			 BigDecimal shelfLifeDays = bean.getShelfLifeDays(); 
			 if(shelfLifeDays != null && bean.getShelfLifeDays().equals(new BigDecimal(-1))){
				 
					 storageTemp = library.getString("label.indefinite") + " " + "@"+ " " + storageTemp;
				
			 }
			 else
			 {
				 String shelfBasis = bean.getShelfLifeBasis();
				 if( shelfBasis != null && shelfBasis.length() != 0 ) 
					 storageTemp = bean.getShelfLifeDays() +" "+ library.getString("label.days")+" " + bean.getShelfLifeBasis() + "@" + storageTemp;
				 
				 else 
					 storageTemp = bean.getShelfLifeDays()+" "+ library.getString("label.days")+ " " + "@" + " " + storageTemp;
				 
			 }
		 }
		 pw.addCell(storageTemp);
		 pw.addCell(bean.getMfgPartNo());
		 		 		
	 }
	 return pw;
}

 public Object[] createRelationalObjects(Vector<PrCatalogScreenSearchBean> bv) {
//		Vector bv = (Vector) this.getSearchResult(inputBean);
		// calculate m1 and m2
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		String partNum = null;

		for (PrCatalogScreenSearchBean pbean:bv) {
			partNum = pbean.getCatPartNo()+pbean.getCatalogId()+pbean.getCatalogCompanyId()+pbean.getPartGroupNo();
//			System.out.println(partNum);

			if (m1.get(partNum) == null) {
				i1 = new Integer(0);
				m1.put(partNum, i1);
				map = new HashMap();
				m2.put(partNum, map);
			}
			i1 = (Integer) m1.get(partNum);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(partNum, i1);

			String itemId = "itemId" + pbean.getItemId();

			if (map.get(itemId) == null) {
				i2 = new Integer(0);
				map.put(itemId, i2);
			}
			i2 = (Integer) map.get(itemId);
			i2 = new Integer(i2.intValue() + 1);
			map.put(itemId, i2);
		}
		Object[] objs = {bv,m1,m2};
		return objs;
	}

public Collection createRelationalObject(Collection
	pkgInventoryDetailWebPrInventoryBeanCollection) {

	Collection finalpkgInventoryDetailWebPrInventoryBeanCollection = new Vector();
	String nextPartNumber = "";
	String nextItem = "";

	int samePartNumberCount = 0;
	Vector collectionVector = new Vector(
	 pkgInventoryDetailWebPrInventoryBeanCollection);
	Vector itemIdV1 = new Vector();
	Vector materialIdV1 = new Vector();
	for (int loop = 0; loop < collectionVector.size(); loop++) {

	 PrCatalogScreenSearchBean currentPrCatalogScreenSearchBean = (
		PrCatalogScreenSearchBean) collectionVector.elementAt(loop);
	 String currentPartNumber = currentPrCatalogScreenSearchBean.getCatPartNo()+currentPrCatalogScreenSearchBean.getCatalogId()+
		  								 currentPrCatalogScreenSearchBean.getCatalogCompanyId()+currentPrCatalogScreenSearchBean.getPartGroupNo();
	 String currentItem = "" + currentPrCatalogScreenSearchBean.getItemId() + "";

	 if (! ( (loop + 1) == collectionVector.size())) {
		PrCatalogScreenSearchBean nextPrCatalogScreenSearchBean = (
		 PrCatalogScreenSearchBean) collectionVector.elementAt(loop + 1);

		nextPartNumber = nextPrCatalogScreenSearchBean.getCatPartNo()+nextPrCatalogScreenSearchBean.getCatalogId()+
			 				  nextPrCatalogScreenSearchBean.getCatalogCompanyId()+nextPrCatalogScreenSearchBean.getPartGroupNo();
		nextItem = "" + nextPrCatalogScreenSearchBean.getItemId() + "";
	 }
	 else {
		nextPartNumber = "";
		nextItem = "";
	 }

	 boolean samePartNumber = false;
	 boolean sameItemId = false;

	 if (currentPartNumber.equalsIgnoreCase(nextPartNumber)) {
		samePartNumber = true;
		samePartNumberCount++;
		if (nextItem.equalsIgnoreCase(currentItem)) {
		 sameItemId = true;
		}
	 }

	 PrCatalogScreenSearchComponentBean prCatalogScreenSearchComponentBean = new
		PrCatalogScreenSearchComponentBean();
	 prCatalogScreenSearchComponentBean.setMaterialDesc(
		currentPrCatalogScreenSearchBean.getMaterialDesc());
	 prCatalogScreenSearchComponentBean.setMaterialId(
		currentPrCatalogScreenSearchBean.getMaterialId());
	 prCatalogScreenSearchComponentBean.setMfgDesc(
		currentPrCatalogScreenSearchBean.getMfgDesc());
	 prCatalogScreenSearchComponentBean.setMfgPartNo(
		currentPrCatalogScreenSearchBean.getMfgPartNo());
	 prCatalogScreenSearchComponentBean.setMsdsOnLine(
		currentPrCatalogScreenSearchBean.getMsdsOnLine());
	 prCatalogScreenSearchComponentBean.setPackaging(
		currentPrCatalogScreenSearchBean.getPackaging());
	 prCatalogScreenSearchComponentBean.setGrade(
       currentPrCatalogScreenSearchBean.getGrade());

   materialIdV1.add(prCatalogScreenSearchComponentBean);

	 if (sameItemId) {

	 }
	 else {
		PrCatalogScreenSearchItemBean prCatalogScreenSearchItemBean = new
		 PrCatalogScreenSearchItemBean();
		prCatalogScreenSearchItemBean.setComponentCollection( (Vector) materialIdV1.
		 clone());
		materialIdV1 = new Vector();

		prCatalogScreenSearchItemBean.setItemId(currentPrCatalogScreenSearchBean.
		 getItemId());
		prCatalogScreenSearchItemBean.setApprovalStatus(
		 currentPrCatalogScreenSearchBean.getApprovalStatus());
		prCatalogScreenSearchItemBean.setBundle(currentPrCatalogScreenSearchBean.
		 getBundle());

		itemIdV1.add(prCatalogScreenSearchItemBean);
	 }

	 if (samePartNumber) {

	 }
	 else {
		currentPrCatalogScreenSearchBean.setItemCollection( (Vector) itemIdV1.clone());
		itemIdV1 = new Vector();
		currentPrCatalogScreenSearchBean.setRowSpan(new BigDecimal(
		 samePartNumberCount + 1));

		finalpkgInventoryDetailWebPrInventoryBeanCollection.add(
		 currentPrCatalogScreenSearchBean);
		samePartNumberCount = 0;
	 }
	}

	//log.info("Final collectionSize here " + finalpkgInventoryDetailWebPrInventoryBeanCollection.size() + "");
	return finalpkgInventoryDetailWebPrInventoryBeanCollection;

 }

	private void getMaterialCategoryData(PrCatalogScreenSearchBean inputBean, GenericSqlFactory factory) {
		try {
			if (!StringHandler.isBlankString(inputBean.getCatPartNo())) {
				StringBuilder query = new StringBuilder("select msc.material_subcategory_name,mc.material_category_name");
				query.append(" from fac_part_use_code fpuc, vv_material_subcategory msc, vv_material_category mc");
				query.append(" where fpuc.catalog_company_id = '").append(inputBean.getCatalogCompanyId());
				query.append("' and fpuc.catalog_id = '").append(inputBean.getCatalogId()).append("' and fpuc.cat_part_no = '").append(inputBean.getCatPartNo());
				query.append("' and fpuc.part_group_no = ").append(inputBean.getPartGroupNo());
				query.append(" and fpuc.company_id = msc.company_id and fpuc.material_subcategory_id = msc.material_subcategory_id");
				query.append(" and msc.material_category_id = mc.material_category_id");
				factory.setBeanObject(new MaterialCategorySubcatViewBean());
	              Iterator iter = factory.selectQuery(query.toString()).iterator();
	              while(iter.hasNext()) {
	                  MaterialCategorySubcatViewBean bean = (MaterialCategorySubcatViewBean)iter.next();
	                  if (bean.getMaterialCategoryName() != null)
	                      inputBean.setMaterialCategoryName(bean.getMaterialCategoryName().toString());
	                  else
	                     inputBean.setMaterialCategoryName("");
	                  if (bean.getMaterialSubcategoryName() != null)
	                      inputBean.setMaterialSubcategoryName(bean.getMaterialSubcategoryName().toString());
	                  else
	                      inputBean.setMaterialSubcategoryName("");
	                  break;
	              } //end of loop
	          }
		  }
		  catch (Exception e) {
	          e.printStackTrace();
		  }
	}
 

}