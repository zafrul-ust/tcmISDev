package com.tcmis.supplier.shipping.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.factory.UserSupplierLocationBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.HubBean;
import com.tcmis.supplier.shipping.beans.SupplierShippingInputBean;
import com.tcmis.supplier.shipping.beans.SupplierShippingViewBean;
import com.tcmis.supplier.shipping.factory.SupplierLocationOvBeanFactory;
import com.tcmis.supplier.shipping.factory.SupplierShippingViewBeanFactory;

/******************************************************************************
 * Process for SupplierShippingView
 * @version 1.0
 *****************************************************************************/
public class ShipmentSelectionProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ShipmentSelectionProcess(String client) {
		super(client);
	}

	public ShipmentSelectionProcess(String client,String locale) {
		super(client,locale);
	}
	
	public Collection getSearchDropDown(BigDecimal personnelId) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		SupplierLocationOvBeanFactory factory = new
		SupplierLocationOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId",
				SearchCriterion.EQUALS,
				personnelId.toString());
		return factory.selectObject(criteria);
	}

	public Collection getDLASearchDropDown(BigDecimal personnelId) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		SupplierLocationOvBeanFactory factory = new
		SupplierLocationOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId",
				SearchCriterion.EQUALS,
				personnelId.toString());
		return factory.selectDLAObject(criteria);
	}
	
	
	public Collection getDlaHubs() throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory s = new GenericSqlFactory(dbManager);
		s.setBean(new HubBean());
		return s.selectQuery("select * from dla_gas_hub_view order by hub_name");
	}
	
	public Collection getSearchResult(SupplierShippingInputBean
			bean) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		SupplierShippingViewBeanFactory factory = new
		SupplierShippingViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		String s = null;
		s = bean.getSupplier();
		if ( s != null && !s.equals("") )
			criteria.addCriterion("supplier",
					SearchCriterion.EQUALS,
					s);
		//s = bean.getShipFromLocationId();
		if (bean.getSuppLocationIdArray() != null && bean.getSuppLocationIdArray().length == 1
				&& bean.getSuppLocationIdArray()[0].length() == 0)
			/*if ( s != null && !s.equals("") )
			{
			  	 criteria.addCriterion("shipFromLocationId",
			  		SearchCriterion.EQUALS,
			  		s);
			}
			else*/
		{
			/*All supplier locations means all supplier locations the user has privilages to*/
			UserSupplierLocationBeanFactory userSuppLocfactory = new
			UserSupplierLocationBeanFactory(dbManager);
			SearchCriteria suppLoccriteria = new SearchCriteria();

			suppLoccriteria.addCriterion("supplier",
					SearchCriterion.EQUALS,
					bean.getSupplier());

			suppLoccriteria.addCriterion("personnelId",
					SearchCriterion.EQUALS,
					bean.getPersonnelId().toString());
			Collection userSuppLocCollection = userSuppLocfactory.selectDistinctSuppLoc(suppLoccriteria,null);
			criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS, userSuppLocCollection,"");
		}
		else  if(bean.getSuppLocationIdArray() != null && bean.getSuppLocationIdArray().length > 0) {
			boolean initFlag = true;
			for(int i=0; i<bean.getSuppLocationIdArray().length; i++) {
				if(bean.getSuppLocationIdArray()[i].length() > 0) {
					if (initFlag) {
						criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS,
								bean.getSuppLocationIdArray()[i].toString());
					}
					else {
						criteria.addValueToCriterion("shipFromLocationId",
								bean.getSuppLocationIdArray()[i].toString());
					}
					initFlag = false;
				}
			}
		}

		s = bean.getSearchArgument();
		if ( !StringHandler.isBlankString(s) ) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
			if( field.equals("cityCommaState"))
				s = s.replaceAll("\\s",""); // remove all white spaces
			if( mode.equals("is") )
				criteria.addCriterion(field,
						SearchCriterion.EQUALS,
						s);
			if( mode.equals("contains"))
				criteria.addCriterion(field,
						SearchCriterion.LIKE,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("startsWith"))
				criteria.addCriterion(field,
						SearchCriterion.STARTS_WITH,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("endsWith"))
				criteria.addCriterion(field,
						SearchCriterion.ENDS_WITH,
						s,SearchCriterion.IGNORE_CASE);
		}
		
		if (bean.isAllocatedOnlyOrderStatus())
			criteria.addCriterion("availableInventoryQty", SearchCriterion.NOT_EQUAL, "0");
		else if (bean.isUnallocatedOnlyOrderStatus())
			criteria.addCriterion("availableInventoryQty", SearchCriterion.EQUALS, "0");
		
		SortCriteria scriteria = new SortCriteria();
		s = bean.getSort();
		scriteria.addCriterion(s);

		return factory.select(criteria,scriteria);
	}

	public ExcelHandler  getExcelReport(Collection bean, Locale locale) throws
	NoDataException, BaseException {
		if(log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());}
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//		  write column headers
		pw.addRow();
		String[] headerkeys = { "label.supplierlocation","label.shipto","label.ultimatedodaac","label.priority","label.order.type","label.origininspectionrequired", "label.milstrip", /*"label.mrline",*/
				"label.haaspoline","label.shipdate","label.deliverydate","label.part", "label.item",
				"label.description","label.openqty","label.availableqty","label.packaging"};
		int  [] widths = {17, 34, 11, 10,0, 10,10,
				0, 13, 16, 15, 0,
				0, 0, 0, 35};
		int [] types =   {0,0,0,0,0,0,
				0, ExcelHandler.TYPE_CALENDAR, ExcelHandler.TYPE_CALENDAR,0,0,0,
				ExcelHandler.TYPE_PARAGRAPH,0,0, ExcelHandler.TYPE_PARAGRAPH};
		int[] aligns = {0,0,0,0,0,ExcelHandler.ALIGN_CENTER,0,
				0,0,0,0,0,
				0,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		Iterator iterator = bean.iterator();
		//	   now write data
		while(iterator.hasNext()){
			SupplierShippingViewBean member = (SupplierShippingViewBean) iterator.next();
			pw.addRow();
			pw.addCell(member.getShipFromLocationName());
			pw.addCell(member.getLocationDesc()+","+member.getCity()+", "+member.getStateAbbrev()+" "+member.getZip());
			pw.addCell(member.getApplication());
			pw.addCell(member.getPriorityRating());
			if (member.getOriginalTransactionType().equalsIgnoreCase("850"))
			{
				pw.addCell("Commercial");
			}
			else if (member.getOriginalTransactionType().equalsIgnoreCase("940"))
			{
				pw.addCell("VMI");
			}
			else
			{
				pw.addCell("");
			}
			pw.addCell(member.getOriginInspectionRequired()  );
			pw.addCell(member.getCustomerPo());
			pw.addCell( member.getRadianPo()+"-"+member.getLineItem());
			pw.addCell(member.getDesiredShipDate());
			pw.addCell(member.getDesiredDeliveryDate());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDescription());
			pw.addCell(member.getQtyOpen());
			pw.addCell(member.getAvailableInventoryQty());
			pw.addCell(member.getPackaging());
		}
		return pw;

	}
}
