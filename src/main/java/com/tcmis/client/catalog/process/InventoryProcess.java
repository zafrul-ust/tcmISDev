package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.*;
import com.tcmis.client.catalog.factory.FacilityIgViewOvBeanFactory;
import com.tcmis.client.catalog.factory.PkgInventoryDetailWebPrInventoryDetailBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.InventoryGroupDefinitionBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Process for receiving
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class InventoryProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public InventoryProcess(String client) {
		super(client);
	}

	public InventoryProcess(String client, String locale) {
		super(client, locale);
	}

	private Collection getUserAllFacilitiesIg(PersonnelBean personnelBean) throws BaseException {
		Collection c = null;
		try {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new InventoryGroupNameOvBean());
            StringBuilder query = new StringBuilder("select * from user_inventory_group_view where personnel_id = ").append(personnelBean.getPersonnelId());
            query.append(" and company_id = '").append(personnelBean.getCompanyId()).append("' order by inventory_group_name");
            c = factory.selectQuery(query.toString());
        } catch (Exception e) {
			BaseException be = new BaseException("Error in InventoryProcess getUserAllFacilitiesIg()");
			be.setRootCause(e);
			throw be;
		}
		return c;
	} //end of method

    public Collection getDropDownData(PersonnelBean personnelBean) throws BaseException {
		Collection result = new Vector();
		try {
            //get specific facility data
            DbManager dbManager = new DbManager(getClient(), getLocale());
			FacilityIgViewOvBeanFactory factory = new FacilityIgViewOvBeanFactory(dbManager);
            Collection userFacilityIgColl = factory.selectUserObject(personnelBean.getCompanyId(),personnelBean.getPersonnelIdBigDecimal());
            if (userFacilityIgColl != null && userFacilityIgColl.size() != 0) {
                ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
                //if user has only 1 facility then add All Facilities if company has option
                //otherwise user can only look at data for that facility
                if (userFacilityIgColl.size() == 1) {
                    if (personnelBean.isFeatureReleased("InventoryAllowAllFacilities","ALL",personnelBean.getCompanyId())) {
                        //getting inventory groups for ALL facilities
                        FacilityIgViewOvBean bean = new FacilityIgViewOvBean();
                        bean.setFacilityId("ALL");
                        bean.setFacilityName(library.getString("label.allfacilities"));
                        bean.setInventoryGroups(getUserAllFacilitiesIg(personnelBean));
                        result.add(bean);
                    }
                }else {
                    Collection userAllFacilitiesIg = getUserAllFacilitiesIg(personnelBean);
                    //handle All Facilities
                    if (personnelBean.isFeatureReleased("InventoryAllowAllFacilities","ALL",personnelBean.getCompanyId())) {
                        //getting inventory groups for ALL facilities
                        FacilityIgViewOvBean bean = new FacilityIgViewOvBean();
                        bean.setFacilityId("ALL");
                        bean.setFacilityName(library.getString("label.allfacilities"));
                        bean.setInventoryGroups(userAllFacilitiesIg);
                        result.add(bean);
                    }
                    //handle My facilities
                    FacilityIgViewOvBean bean = new FacilityIgViewOvBean();
                    bean.setFacilityId("My Facilities");
                    bean.setFacilityName(library.getString("label.myfacilities"));
                    bean.setInventoryGroups(userAllFacilitiesIg);
                    result.add(bean);
                }
                //put data together
                Iterator iter = userFacilityIgColl.iterator();
                while (iter.hasNext()) {
                    FacilityIgViewOvBean bean = (FacilityIgViewOvBean)iter.next();
                    result.add(bean);
                }
            }
        } catch (Exception e) {
			BaseException be = new BaseException("Error in InventoryProcess getDropDownData()");
			be.setRootCause(e);
			throw be;
		}
		return result;
	} //end of method

    public Collection getsearchResult(InventoryInputBean bean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
        Connection connection = dbManager.getConnection();
        Collection resultColl = new Vector();
        try {
            //turn auto commit off
            try {
                connection.setAutoCommit(false);
            } catch (SQLException ex) {
            }

            GenericSqlFactory factory = new GenericSqlFactory(dbManager, new PkgInventoryDetailWebPrInventoryBean());
            Vector inArgs = new Vector();
            inArgs.add(bean.getCompanyId());
            inArgs.add(bean.getSearchText());
            if (bean.getFacilityId() != null && bean.getFacilityId().trim().length() > 0) {
                inArgs.add(bean.getFacilityId());
            } else {
                inArgs.add("ALL");
            }
            if (bean.getInventory() != null && bean.getInventory().trim().length() > 0 ) {
                inArgs.add(bean.getInventory());
            } else {
                inArgs.add("ALL");
            }
            if (bean.getExpiresAfter() != null) {
                inArgs.add(bean.getExpiresAfter());
            } else {
                inArgs.add("");
            }

            if (bean.getExpiresWithin() != null) {
                inArgs.add(bean.getExpiresWithin());
            } else {
                inArgs.add("");
            }
            if (bean.getArrivesWithin() != null) {
                inArgs.add(bean.getArrivesWithin());
            } else {
                inArgs.add("");
            }
            inArgs.add(personnelBean.getPersonnelIdBigDecimal());
            Collection outArgs = new Vector();
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            Collection result = null;
            SqlManager sqlManager = new SqlManager();
            try {
                result = sqlManager.doProcedure(connection, "PKG_INVENTORY_DETAIL_WEB.PR_INVENTORY_WEB", inArgs, outArgs);
            } catch (Exception ex) {
                return new Vector();
            }
            Iterator i11 = result.iterator();
            String searchQuery = "";
            int count = 1;
            while (i11.hasNext()) {
                if (count == 1) {
                    searchQuery = (String) i11.next();
                }
                count++;
            }

            if (!StringHandler.isBlankString(searchQuery))
                resultColl = factory.selectQuery(searchQuery,connection);

            //turn auto commit back ON            
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
            }
		//make sure to always return connection
        }finally{
			dbManager.returnConnection(connection);
		}

        return resultColl;
    } //end of method



    public Collection createRelationalObject(Collection pkgInventoryDetailWebPrInventoryBeanCollection) {

		Collection finalPkgInventoryDetailWebPrInventoryBeanCollection = new Vector();
		String nextPartNumber = "";
		String nextItem = "";
		String nextInventoryGroup = "";

		int samePartNumberCount = 0;
		Vector collectionVector = new Vector(pkgInventoryDetailWebPrInventoryBeanCollection);
		Vector itemIdV1 = new Vector();
		Vector materialIdV1 = new Vector();
		for (int loop = 0; loop < collectionVector.size(); loop++) {

			PkgInventoryDetailWebPrInventoryBean currentPkgInventoryDetailWebPrInventoryBean = (PkgInventoryDetailWebPrInventoryBean) collectionVector.elementAt(loop);
			String currentPartNumber = currentPkgInventoryDetailWebPrInventoryBean.getCatPartNo();
			String currentItem = "" + currentPkgInventoryDetailWebPrInventoryBean.getItemId() + "";
			String currentInventoryGroup = "" + currentPkgInventoryDetailWebPrInventoryBean.getInventoryGroup() + "";

			if (!((loop + 1) == collectionVector.size())) {
				PkgInventoryDetailWebPrInventoryBean nextPkgInventoryDetailWebPrInventoryBean = (PkgInventoryDetailWebPrInventoryBean) collectionVector.elementAt(loop + 1);

				nextPartNumber = nextPkgInventoryDetailWebPrInventoryBean.getCatPartNo();
				nextItem = "" + nextPkgInventoryDetailWebPrInventoryBean.getItemId() + "";
				nextInventoryGroup = "" + nextPkgInventoryDetailWebPrInventoryBean.getInventoryGroup() + "";
			} else {
				nextPartNumber = "";
				nextItem = "";
				nextInventoryGroup = "";
			}

			boolean samePartNumber = false;
			boolean sameItemId = false;

			if (currentPartNumber.equalsIgnoreCase(nextPartNumber) && currentInventoryGroup.equalsIgnoreCase(nextInventoryGroup)) {
				samePartNumber = true;
				samePartNumberCount++;
				if (nextItem.equalsIgnoreCase(currentItem)) {
					sameItemId = true;
				}
			}

			PkgInventoryDetailComponentBean pkgInventoryDetailComponentBean = new PkgInventoryDetailComponentBean();
			pkgInventoryDetailComponentBean.setMaterialDesc(currentPkgInventoryDetailWebPrInventoryBean.getMaterialDesc());
			pkgInventoryDetailComponentBean.setMaterialId(currentPkgInventoryDetailWebPrInventoryBean.getMaterialId());
			pkgInventoryDetailComponentBean.setMfgDesc(currentPkgInventoryDetailWebPrInventoryBean.getMfgDesc());
			pkgInventoryDetailComponentBean.setMfgPartNo(currentPkgInventoryDetailWebPrInventoryBean.getMfgPartNo());
			pkgInventoryDetailComponentBean.setMsdsOnLine(currentPkgInventoryDetailWebPrInventoryBean.getMsdsOnLine());
			pkgInventoryDetailComponentBean.setPackaging(currentPkgInventoryDetailWebPrInventoryBean.getPackaging());

			materialIdV1.add(pkgInventoryDetailComponentBean);

			if (sameItemId) {

			} else {
				PkgInventoryDetailItemBean pkgInventoryDetailItemBean = new PkgInventoryDetailItemBean();
				pkgInventoryDetailItemBean.setComponentCollection((Vector) materialIdV1.
						clone());
				materialIdV1 = new Vector();

				pkgInventoryDetailItemBean.setItemId(currentPkgInventoryDetailWebPrInventoryBean.getItemId());
				pkgInventoryDetailItemBean.setItemsPerPart(currentPkgInventoryDetailWebPrInventoryBean.getItemsPerPart());
				/*pkgInventoryDetailItemBean.setQtyAvailable(
						 currentPkgInventoryDetailWebPrInventoryBean.getQtyAvailable());
							 pkgInventoryDetailItemBean.setQtyHeld(
						 currentPkgInventoryDetailWebPrInventoryBean.getQtyHeld());
							 pkgInventoryDetailItemBean.setQtyInPurchasing(
						 currentPkgInventoryDetailWebPrInventoryBean.getQtyInPurchasing());
							 pkgInventoryDetailItemBean.setQtyOnOrder(
						 currentPkgInventoryDetailWebPrInventoryBean.getQtyOnOrder());*/
				pkgInventoryDetailItemBean.setItemPackaging(currentPkgInventoryDetailWebPrInventoryBean.getItemPackaging());
				/*pkgInventoryDetailItemBean.setLastCountDate(
						 currentPkgInventoryDetailWebPrInventoryBean.getLastCountDate());*/

				itemIdV1.add(pkgInventoryDetailItemBean);
			}

			if (samePartNumber) {

			} else {
				currentPkgInventoryDetailWebPrInventoryBean.setItemCollection((Vector) itemIdV1.clone());
				itemIdV1 = new Vector();
				currentPkgInventoryDetailWebPrInventoryBean.setRowSpan(new BigDecimal(samePartNumberCount + 1));

				finalPkgInventoryDetailWebPrInventoryBeanCollection.add(currentPkgInventoryDetailWebPrInventoryBean);
				samePartNumberCount = 0;

			}
		}

		//log.info("Final collectionSize here " + finalPkgInventoryDetailWebPrInventoryBeanCollection.size() + "");
		return finalPkgInventoryDetailWebPrInventoryBeanCollection;

	}

	public Collection getInventoryDetails(String catPartNo, String inventoryGroup, String catalogId, String partGroupNo, String catalogCompanyId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PkgInventoryDetailWebPrInventoryDetailBeanFactory factory = new PkgInventoryDetailWebPrInventoryDetailBeanFactory(dbManager);
		return factory.select(catPartNo, inventoryGroup, catalogId, partGroupNo, catalogCompanyId,"inventoryDetails");
	}

	public ExcelHandler getExcelReport(InventoryInputBean bean, Locale locale, PersonnelBean personnelBean) throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection searchCollection = this.getsearchResult(bean,personnelBean);

		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//	  write column headers
		pw.addRow();
		pw.addCellKeyBold("label.facility");
		pw.addCell(bean.getFacilityId());
		pw.addCellKeyBold("inventory.label.expireswithin");
		if (bean.getExpiresWithin() != null) {
			pw.addCell(bean.getExpiresWithin());
			pw.addCellKeyBold("label.days");
		} else {
			pw.addCell("");
			pw.addCellKeyBold("label.days");
		}

		//row 2
		pw.addRow();
		pw.addCellKeyBold("label.inventory");
		pw.addCell(bean.getInventory());
		pw.addCellKeyBold("inventory.label.expiresafter");
		if (bean.getExpiresAfter() != null) {
			pw.addCell(bean.getExpiresAfter());
			pw.addCellKeyBold("label.days");
		} else {
			pw.addCell("");
			pw.addCellKeyBold("label.days");
		}

		//row 3
		pw.addRow();
		pw.addCellKeyBold("label.searchtext");
		pw.addCell(bean.getSearchText());
		pw.addCellKeyBold("inventory.label.arriveswithin");
		if (bean.getArrivesWithin() != null) {
			pw.addCell(bean.getArrivesWithin());
			pw.addCellKeyBold("label.days");
		} else {
			pw.addCell("");
			pw.addCellKeyBold("label.days");
		}

		//blank row
		pw.addRow();

		//result table
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {"label.catalog", "label.part", "label.description", "label.type", "label.rpslrq", "label.inventorygroup", "inventory.label.inventoryuom", "label.available", "label.held", "label.onorder", "label.inpurchasing", "label.item", "inventory.label.componentdescription", "inventory.label.packaging", "label.manufacturer", "label.mfgpartno"};
		/*
				  pw.addCellKeyBold("label.catalog");
				  pw.addCellKeyBold("label.part");
				  pw.addCellKeyBold("label.description");
				  pw.addCellKeyBold("label.type");
				  pw.addCellBold(library.getString("inventory.label.setpoint")+library.getString("inventory.label.setpointlabel"));
				  pw.addCellKeyBold("label.invengroup");
				  pw.addCellKeyBold("inventory.label.inventoryuom");
				  pw.addCellBold(library.getString("label.available")+library.getString("inventory.label.lastcounted"));
				  pw.addCellKeyBold("label.held");
				  pw.addCellKeyBold("label.onorder");
				  pw.addCellKeyBold("label.inpurchasing");
				  pw.addCellKeyBold("label.item");
				  pw.addCellKeyBold("inventory.label.componentdescription");
				  pw.addCellKeyBold("inventory.label.packaging");
				  pw.addCellKeyBold("label.manufacturer");
				  pw.addCellKeyBold("label.mfgpartno");  */
		/*This array defines the type of the excel column.
				 0 means default depending on the data type. */
		int[] types = {0, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, 0};
		/*This array defines the default width of the column when the Excel file opens.
				 0 means the width will be default depending on the data type.*/
		int[] widths = {16, 16, 0, 0, 10, 20, 22, 11, 0, 0, 11, 0, 0, 0, 21, 11};
		/*This array defines the horizontal alignment of the data in a cell.
				 0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//print rows
		Iterator i11 = searchCollection.iterator();
		while (i11.hasNext()) {
			pw.addRow();
			PkgInventoryDetailWebPrInventoryBean pkgInventoryDetailWebPrInventoryBean = (PkgInventoryDetailWebPrInventoryBean) i11.next();
			;
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getCatalogDesc());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getCatPartNo());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getPartDescription());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getStockingMethod());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getSetPoints());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getInventoryGroup());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getItemPackaging());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getQtyAvailable());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getQtyHeld());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getQtyOnOrder());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getQtyInPurchasing());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getItemId());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getMaterialDesc());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getPackaging());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getMfgDesc());
			pw.addCell(pkgInventoryDetailWebPrInventoryBean.getMfgPartNo());

		}
		return pw;
	} //end of method

	public ExcelHandler getDetailExcelReport(PrCatalogScreenSearchBean bean, PersonnelBean personnelBean, Locale locale) throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection searchCollection = this.getInventoryDetails(bean.getCatPartNo(),bean.getInventoryGroup(),bean.getCatalogId(),bean.getPartGroupNo().toString(),bean.getCatalogCompanyId());
		PkgInventoryDetailWebPrInventoryDetailBean dataBean = null;
		Iterator i11 = searchCollection.iterator();
		while (i11.hasNext()) {
			dataBean = (PkgInventoryDetailWebPrInventoryDetailBean)i11.next();
		}

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FacItemBean());
		String query = "select part_description from fac_item where facility_id = '"+bean.getCatalogId()+"' and company_id = '"+bean.getCatalogCompanyId()+
		"' and fac_part_no = '"+bean.getCatPartNo()+"'";

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//	  write column headers
		pw.addRow();
		pw.addCellKeyBold("label.part");
		pw.addCell(bean.getCatPartNo());
		pw.addRow();
		pw.addCellKeyBold("label.partdescription");
		pw.addCell(factory.selectSingleValue(query));
		//blank row
		pw.addRow();

		//result table
		//item desc
		pw.addRow();
		pw.addCellKeyBold("label.itemdescription");
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {"label.item", "label.description"};

		/*This array defines the type of the excel column.
				 0 means default depending on the data type. */
		int[] types = {0, 0};
		/*This array defines the default width of the column when the Excel file opens.
				 0 means the width will be default depending on the data type.*/
		int[] widths = {0, 15};
		/*This array defines the horizontal alignment of the data in a cell.
				 0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {0, 0};
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//print rows
		i11 = dataBean.getItemDescription().iterator();
		while (i11.hasNext()) {
			pw.addRow();
			ItemBean cBean = (ItemBean) i11.next();
			pw.addCell(cBean.getItemId());
			pw.addCell(cBean.getItemDesc());
		}

		String tmpExcelDateFormat = library.getString("java.exceldateformat");
		//on hand
		if (dataBean.getOnHandMaterial().size() > 0) {
			pw.addRow();
			pw.addRow();
			pw.addCellKeyBold("inventorydetail.label.onhandmaterial");
			

			if(personnelBean.isFeatureReleased("DisplayChargeNoOwnerSeqment", "ALL",personnelBean.getCompanyId())){
				String[] headerkeys2 = {"label.item", "label.status","label.inventorygroup","label.qty","label.lot","label.owner","label.program","label.trace","label.expdate","label.qualitynote","inventorydetail.label.readytoship"};
				int[] types2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				/*This array defines the default width of the column when the Excel file opens.
						 0 means the width will be default depending on the data type.*/
				int[] widths2 = {25, 0, 0, 0, 0, 0, 0, 0, 20, 0, 20};
				/*This array defines the horizontal alignment of the data in a cell.
						 0 means excel defaults the horizontal alignemnt by the data type.*/
				int[] horizAligns2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				pw.applyColumnHeader(headerkeys2, types2, widths2, horizAligns2);
				//set date format
				pw.setColumnDateFormat(7,tmpExcelDateFormat);
				pw.setColumnDateFormat(8,tmpExcelDateFormat);
			}
			else{
				String[] headerkeys2 = {"label.item", "label.status","label.inventorygroup","label.qty","label.lot","label.expdate","label.qualitynote","inventorydetail.label.readytoship"};
				int[] types2 = {0, 0, 0, 0, 0, 0, 0, 0};
				/*This array defines the default width of the column when the Excel file opens.
						 0 means the width will be default depending on the data type.*/
				int[] widths2 = {25, 0, 0, 0, 0, 20, 0, 20};
				/*This array defines the horizontal alignment of the data in a cell.
						 0 means excel defaults the horizontal alignemnt by the data type.*/
				int[] horizAligns2 = {0, 0, 0, 0, 0, 0, 0, 0};
				pw.applyColumnHeader(headerkeys2, types2, widths2, horizAligns2);
				//set date format
				pw.setColumnDateFormat(5,tmpExcelDateFormat);
				pw.setColumnDateFormat(6,tmpExcelDateFormat);
			}
			
			//print rows
			i11 = dataBean.getOnHandMaterial().iterator();
			while (i11.hasNext()) {
				pw.addRow();
				InventoryDetailOnHandMaterialBean cBean = (InventoryDetailOnHandMaterialBean) i11.next();
				pw.addCell(cBean.getItemId());
				pw.addCell(cBean.getLotStatus());
				pw.addCell(cBean.getInventoryGroup());
				pw.addCell(cBean.getQuantity());
				pw.addCell(cBean.getMfgLot());
				if(personnelBean.isFeatureReleased("DisplayChargeNoOwnerSeqment", "ALL",personnelBean.getCompanyId())){
					pw.addCell(cBean.getOwnerSegmentId());
					pw.addCell(cBean.getProgramId());
                    pw.addCell(cBean.getTraceId());
                }
				pw.addCell(cBean.getExpireDate());
				pw.addCell(cBean.getQualityTrackingNumber());
				pw.addCell(cBean.getReadyToShipDate());
			}
		}

		//in supply
		if (dataBean.getInSupplyChain().size() > 0) {
			pw.addRow();
			pw.addRow();
			pw.addCellKeyBold("inventorydetail.label.insupplychain");
			pw.setColumnHeader(7,"");
			pw.setColumnHeader(8,"");
			String[] headerkeys3 = {"label.item", "label.status","label.inventorygroup","label.qty","inventorydetail.label.ref","inventorydetail.label.readytoship","label.notes"};
			int[] types3 = {0, 0, 0, 0, 0, 0, 0};
			/*This array defines the default width of the column when the Excel file opens.
					 0 means the width will be default depending on the data type.*/
			int[] widths3 = {25, 0, 0, 0, 0, 20, 20};
			/*This array defines the horizontal alignment of the data in a cell.
					 0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns3 = {0, 0, 0, 0, 0, 0, 0};
			pw.applyColumnHeader(headerkeys3, types3, widths3, horizAligns3);
			//set date format
			pw.setColumnDateFormat(5,tmpExcelDateFormat);

			//print rows
			i11 = dataBean.getInSupplyChain().iterator();
			while (i11.hasNext()) {
				pw.addRow();
				InventoryDetailInSupplyChainBean cBean = (InventoryDetailInSupplyChainBean) i11.next();
				pw.addCell(cBean.getItemId());
				pw.addCell(cBean.getStatus());
				pw.addCell(cBean.getInventoryGroup());
				pw.addCell(cBean.getQuantity());
				pw.addCell(cBean.getReference());
				pw.addCell(cBean.getReadyToShipDate());
				pw.addCell(cBean.getNotes());

			}
		}

		return pw;
	} //end of method

}