package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.internal.hub.beans.BoxLabelBean;
import com.tcmis.internal.hub.beans.ConsolidatedItemPickListBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PicklistReprintViewBean;
import com.tcmis.internal.hub.beans.PicklistSelectionViewBean;
import com.tcmis.internal.hub.factory.IssueBeanFactory;
import com.tcmis.internal.hub.factory.PicklistReprintViewBeanFactory;
import com.tcmis.internal.hub.factory.PicklistSelectionViewBeanFactory;
import com.tcmis.internal.hub.factory.ReprintPicklistIdViewBeanFactory;

/******************************************************************************
 * Process for cabinet definition
 * @version 1.0
 *****************************************************************************/
public class PicklistPickingProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	
	private ResourceLibrary library = null;

	private final long MILLISECONDS_PER_DAY = 86400000;

	public PicklistPickingProcess(String client) {
		super(client);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public PicklistPickingProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getSearchResult(PicklistSelectionViewBean bean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PicklistSelectionViewBeanFactory factory = new PicklistSelectionViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();

		/*This is to avoid people from picking MRs that are waiting for freight advice*/
		if (bean.getPackingGroupId() != null)
		{
			criteria.addCriterion("packingGroupId", SearchCriterion.IS_NOT,"null");
			criteria.addCriterion("consolidationNumber", SearchCriterion.IS_NOT,"null");
		}
		else
		{
			criteria.addCriterion("packingGroupId", SearchCriterion.IS,"null");
			criteria.addCriterion("consolidationNumber", SearchCriterion.IS,"null");
		}

		if (bean.getHub()!=null && bean.getHub().length()>0) {
			criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
		}

		if(!StringHandler.isBlankString(bean.getInventoryGroup())){
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
		}
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
			if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
				invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
			if( bean.getHub()!=null && bean.getHub().length()>0 )
				invQuery +=  " and hub = '" + bean.getHub() +"' ";
			if( bean.getOpsEntityId()!=null && bean.getOpsEntityId().length()>0 )
				invQuery +=  " and ops_entity_id = '" + bean.getOpsEntityId() +"' ";
			criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}

		if( (null!= bean.getTransportationMode()) && ( bean.getTransportationMode().length() != 0) ) {
			if (bean.getTransportationMode().equalsIgnoreCase("parcel")) {
				criteria.addCriterion("transportationMode", SearchCriterion.EQUALS,bean.getTransportationMode(),SearchCriterion.IGNORE_CASE);
			}
			else if(bean.getTransportationMode().equalsIgnoreCase("ltltl") ) {
				criteria.addCriterion("transportationMode", SearchCriterion.NOT_EQUAL,"parcel",SearchCriterion.IGNORE_CASE);
			}
		}
		/*
      if (bean.getTransportationMode()!=null && bean.getTransportationMode().length()>0) {
         criteria.addCriterion("transportationMode", SearchCriterion.EQUALS,bean.getTransportationMode());
      }

      if (bean.getCarrierCode()!=null && bean.getCarrierCode().length()>0) {
         criteria.addCriterion("carrierCode", SearchCriterion.EQUALS,bean.getCarrierCode());
      }
		 */
		if (bean.getFacilityId()!=null  && bean.getFacilityId().length()>0) {
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS,bean.getFacilityId());
		}

		if (bean.getShowOCONUSonly()!=null  && bean.getShowOCONUSonly().length()>0) {
			criteria.addCriterion("oconusFlag", SearchCriterion.EQUALS,"Y");
		}

		if (bean.getShowHazardousOnly()!=null  && bean.getShowHazardousOnly().length()>0) {
			criteria.addCriterion("hazardous", SearchCriterion.EQUALS,"Y");
		}

		if ( null!= bean.getCustomerId() )
		{
			criteria.addCriterion("customerId", SearchCriterion.EQUALS, bean.getCustomerId().toString());
		}

		if ( null!= bean.getCustomerServiceRepId() )
		{
			criteria.addCriterion("customerServiceRepId", SearchCriterion.EQUALS, bean.getCustomerServiceRepId().toString());
		}

		String additionalWhereClause ="";
		String s = bean.getSearchArgument();
		if ( s != null && !(s.trim().length()==0) ) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
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

		if (bean.getExpireDays()!=null && bean.getExpireDays().length()>0 && !("prNumber".equals(bean.getSearchField()) && bean.getSearchArgument().length()>0)) {
			additionalWhereClause = " and (expire_date > SYSDATE + "+bean.getExpireDays()+" OR scrap = 'y' OR expire_date IS NULL) ";
		}

		SortCriteria sortCriteria = null;
		if (bean.getSortBy() != null) {
			sortCriteria =new SortCriteria();
			sortCriteria.addCriterion(bean.getSortBy());
		}

		return factory.selectPicking(criteria,sortCriteria,additionalWhereClause);
	}

	@SuppressWarnings("unchecked")
	public  Object[] createPicklist(PicklistSelectionViewBean inpBean,Collection<PicklistSelectionViewBean> inputBeans, PersonnelBean personnelBean, Locale locale) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		IssueBeanFactory issueFactory = new IssueBeanFactory(dbManager);
		BigDecimal picklistId = issueFactory.selectBatchNumber();
		Timestamp pickListTime = new Timestamp((new Date()).getTime());
		PicklistSelectionViewBeanFactory factory = new PicklistSelectionViewBeanFactory(dbManager);
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		PermissionBean permissionBean = personnelBean.getPermissionBean();

		Vector errorCodes = new Vector();


		for (PicklistSelectionViewBean bean : inputBeans) {
			//log.info("Check Picking Permission for IG "+bean.getInventoryGroup());
			if (!permissionBean.hasInventoryGroupPermission("Picking",
					bean.getInventoryGroup(), null, null))
				continue;
			if ("true".equals(bean.getOk()))
			{
				Collection inArgs = new Vector(10);
				inArgs.add(picklistId);
				inArgs.add(bean.getCompanyId());
				inArgs.add(bean.getPrNumber());
				inArgs.add(bean.getLineItem());
				inArgs.add(bean.getHub());
				if (bean.getNeedDate() != null)
				{
					inArgs.add(new Timestamp(bean.getNeedDate().getTime()));
				}
				else
				{
					inArgs.add(new Timestamp(new Date().getTime()));
				}
				inArgs.add(personnelId);
				inArgs.add(pickListTime);

				Collection cout = new Vector();
				cout.add(new Integer(java.sql.Types.VARCHAR));

				Collection optionalInArgs = new Vector(2);
				optionalInArgs.add((!StringHandler.isBlankString(inpBean.getExpireDays())?(new BigDecimal(inpBean.getExpireDays())):(new Integer(java.sql.Types.NULL))));
				if (inpBean.getFromPickingPicklist() != null && !inpBean.getFromPickingPicklist().equalsIgnoreCase("Y"))
				{
					optionalInArgs.add( (null!=bean.getPackingGroupId()?bean.getPackingGroupId(): (new Integer(java.sql.Types.NULL))));
				}
				if (log.isDebugEnabled()) {
					log.debug("input Args for the Procedure: "+inArgs+cout+optionalInArgs);
				}
				Vector result = (Vector)factory.createPicklist(inArgs,cout,optionalInArgs,locale);
				if (result.size()>0 && result.get(0) != null) {

					errorCodes.add((result).get(0));
					if (log.isDebugEnabled()) {
						log.debug("result for " +bean.getPrNumber() + "-" + bean.getLineItem() +": "+(result).get(0).toString() );
					}
				}
			}
		}
		Object[] objs = {picklistId,errorCodes};
		return objs;
	}


	public Collection getPicklistReprint(PicklistReprintViewBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PicklistReprintViewBeanFactory factory = new PicklistReprintViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("picklistId", SearchCriterion.EQUALS,bean.getPicklistId().toString());
		String s = bean.getSearchArgument();
		if ( s != null && !(s.trim().length()==0) ) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
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
		if (bean.getShowOCONUSonly()!=null  && bean.getShowOCONUSonly().length()>0) {
			criteria.addCriterion("oconusFlag", SearchCriterion.EQUALS,"Y");
		}

		if (bean.getShowHazardousOnly()!=null  && bean.getShowHazardousOnly().length()>0) {
			criteria.addCriterion("hazardous", SearchCriterion.EQUALS,"Y");
		}


		SortCriteria sortCriteria = null;
		if (bean.getSortBy() != null) {
			sortCriteria =new SortCriteria();
			sortCriteria.addCriterion(bean.getSortBy());
		}

		return  factory.select(criteria,sortCriteria);
	}

	/*This is to release the page quickly, can be removed after moving to new code*/
	public Vector bolPrintData(Collection pickedMrs) {
		Vector printData = new Vector();
		Vector prnumbers = new Vector();
		Vector linenumber = new Vector();
		Vector picklistid=new Vector();

		Hashtable FResult = new Hashtable();

		Iterator mainIterator = pickedMrs.iterator();
		while (mainIterator.hasNext()) {
			PicklistReprintViewBean pickViewBean = (PicklistReprintViewBean) mainIterator.next();
			prnumbers.addElement(""+pickViewBean.getPrNumber()+"");
			linenumber.addElement(""+pickViewBean.getLineItem()+"");
			picklistid.addElement(""+pickViewBean.getPicklistId()+"");
		}

		FResult.put("mr_number", prnumbers);
		FResult.put("line_number", linenumber);
		FResult.put("picklist_number", picklistid);
		printData.add(FResult);
		return printData;
	}

	public Collection getOpenPicklist(PicklistSelectionViewBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ReprintPicklistIdViewBeanFactory factory = new ReprintPicklistIdViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("hub", SearchCriterion.EQUALS,bean.getHub());
		/*This is to avoid people from picking MRs that are waiting for freight advice*/
		if (bean.getFromPickingPicklist() != null && bean.getFromPickingPicklist().equalsIgnoreCase("Y"))
		{
			criteria.addCriterion("packingGroupId", SearchCriterion.IS,"null");
		}
		else
		{
			criteria.addCriterion("packingGroupId", SearchCriterion.IS_NOT,"null");
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("picklistPrintDate");
		sortCriteria.setSortAscending(false);

		return  factory.selectDistinctPicklist(criteria,sortCriteria);
	}

	public ExcelHandler getExcelReport(PicklistSelectionViewBean bean,PersonnelBean personnelBean, Locale locale) throws
	BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PicklistSelectionViewBean> data = getSearchResult(bean, personnelBean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//		write column headers
		pw.addRow();

		if (bean.getPackingGroupId() != null)
		{
			String[] headerkeys = {
					"label.inventorygroup","label.facility","label.carrier","label.trailer","label.stop","label.pickuptime","label.mode","label.consolidationno","label.ordercount","label.shipto",
					"label.transportationpriority","label.hazardous","label.oconus","label.bin","label.mrline","label.quantity","label.extendedprice","label.currency","label.type","label.needed",
					"label.releasedate","label.partnumber","label.itemid","label.type","label.partdescription","label.packaging", "label.notes","label.dot"};

			int[] widths = {12,12,17,0,0,18,14,15,0,0,
					15,11,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,15};
			int[] types = {0,0,0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,0,pw.TYPE_CALENDAR,
					pw.TYPE_DATE,0,0,0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH};
			int[] aligns = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);

			for (PicklistSelectionViewBean member : data) {
				pw.addRow();
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getFacilityName());
				pw.addCell(member.getCarrierName());
				pw.addCell(member.getTrailerNumber());
				pw.addCell(member.getStopNumber());
				pw.addCell(member.getPickupTime());
				pw.addCell(member.getTransportationMode());
				pw.addCell(member.getConsolidationNumber());
				pw.addCell(member.getMrCount());
				pw.addCell(StringHandler.emptyIfNull(member.getShipToLocationDesc())
						+ member.getShipToCity()
						+ member.getShipToStateAbbrev());
				pw.addCell(member.getTransportationPriority());
				pw.addCell(member.getHazardous());
				pw.addCell(member.getOconusFlag());
				pw.addCell(member.getBin());
				pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
				pw.addCell(member.getPickQty());
				if(member.getExtendedPrice() != null) {
					pw.addCell(member.getExtendedPrice());
					pw.addCell(member.getCurrencyId());
				}else {
					pw.addCell("");
					pw.addCell("");
				}
				pw.addCell(member.getNeedDatePrefix());
				pw.addCell(member.getNeedDate());
				pw.addCell(member.getReleaseDate());
				pw.addCell(member.getCatPartNo());
				pw.addCell(member.getItemId());
				pw.addCell(member.getDeliveryType());
				pw.addCell(member.getPartDescription());
				pw.addCell(member.getPackaging());
				pw.addCell(member.getMrNotes());
				pw.addCell(member.getDot());
			}

		}
		else
		{
			String[] headerkeys = {
					"label.inventorygroup","label.facility","label.shipto","label.requestor","label.csr","label.customer","label.mrline","label.releasedate","label.partnumber","label.type",
					"label.quantity","label.extendedprice","label.currency","label.type","label.needed","label.partdescription","label.packaging","label.notes", "label.workarea","label.deliverypoint",
					"label.dot"};

			int[] widths = {12,12,12,12,12,12,12,10,12,6,
					9,9,6,6,12,12,22,12,12,12,
					15};
			int[] types = {0,0,0,0,0,0,0,pw.TYPE_CALENDAR,0,0,
					0,0,0,0,pw.TYPE_CALENDAR,0,0,0,0,0,
					0};
			int[] aligns = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);

			for (PicklistSelectionViewBean member : data) {
				if("USGOV".equals(member.getCompanyId()) && (!"0".equals(member.getLineItem())))
				{

				}
				else
				{
					pw.addRow();
					pw.addCell(member.getInventoryGroupName());
					pw.addCell(member.getFacilityName());
					if("Y".equals(member.getDistribution()))
						pw.addCell(StringHandler.emptyIfNull(member.getAddressLine1Display()) + " " 
								+ StringHandler.emptyIfNull(member.getAddressLine2Display()) + " " 
								+ StringHandler.emptyIfNull(member.getAddressLine3Display()) + " " 
								+ StringHandler.emptyIfNull(member.getAddressLine4Display()) + " " 
								+ StringHandler.emptyIfNull(member.getAddressLine5Display()));
					else if (member.getLineItem().equals("0"))
						pw.addCell(member.getDestInventoryGroupName() + "-" + member.getApplication());
					else 
						pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getRequestor());
					pw.addCell(member.getCustomerServiceRepName());
					pw.addCell(member.getCustomerName());
					pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
					pw.addCell(member.getReleaseDate());
					pw.addCell(member.getCatPartNo());
					pw.addCell(member.getStockingMethod());
					pw.addCell(member.getPickQty());
					if(member.getCatalogPrice() != null && member.getPickQty() != null) {
						pw.addCell(member.getPickQty().multiply(member.getCatalogPrice()));
						pw.addCell(member.getCurrencyId());
					}else {
						pw.addCell("");
						pw.addCell("");
					}
					pw.addCell(member.getNeedDatePrefix());
					pw.addCell(member.getNeedDate());
					pw.addCell(member.getPartDescription());
					pw.addCell(member.getPackaging());
					pw.addCell(member.getMrNotes());
					if (member.getLineItem().equals("0"))
						pw.addCell(member.getApplication());
					else 
						pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getDeliveryPointDesc());					
					pw.addCell(member.getDot());
				}
			}
		}
		return pw;
	}

	public ExcelHandler getReprintExcelReport(String fromPickingPicklist, PicklistReprintViewBean bean, Locale locale) throws
	BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PicklistReprintViewBean> data = getPicklistReprint(bean);
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//		write column headers
		pw.addRow();

		int []widths;
		String[] headerkeys;
		if(!"Y".equals(fromPickingPicklist))
		{
			headerkeys = new String[]  {"label.inventorygroup",
					"label.facility","label.carrier","label.trailer","label.stop","label.pickuptime","label.mode",
					"label.consolidationno","label.ordercount","label.transportationpriority",
					"label.hazardous","label.oconus","label.shipto", "label.bin","label.mrline",
					"label.quantity","label.needed","label.partnumber","label.type","label.partdescription",
					"label.packaging", "label.notes"};
		}
		else
		{
			headerkeys = new String[] {
					"label.inventorygroup",
					"label.facility","label.shipto",
					"label.bin","label.mrline",
					"label.quantity","label.needed","label.partnumber","label.type","label.partdescription",
					"label.packaging", "label.notes"};

		}
		if(!"Y".equals(fromPickingPicklist))
		{
			widths = new int[] {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18};
		}
		else
		{
			widths = new int[] {18,18,18,18,18,18,18,18,18,18,18,18};
		}

		int[] types;
		if(!"Y".equals(fromPickingPicklist))
		{
			types = new  int[] {0,0,0,0,0,pw.TYPE_CALENDAR,0,
					0,13,0,0,
					0,0,0,pw.TYPE_CALENDAR,pw.TYPE_DATE,
					0,0,0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,
					pw.TYPE_PARAGRAPH};
		}
		else
		{
			types = new  int[] {0,0,0,0,pw.TYPE_CALENDAR,pw.TYPE_DATE,
					0,0,0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,
					pw.TYPE_PARAGRAPH};
		}

		int[] aligns;
		if(!"Y".equals(fromPickingPicklist))
		{
			aligns = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		}
		else
		{
			aligns = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};
		}
		pw.applyColumnHeader(headerkeys, types, widths, aligns);
		for (PicklistReprintViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getFacilityId());
			if(!"Y".equals(fromPickingPicklist))
			{
				pw.addCell(member.getCarrierName());
				pw.addCell(member.getTrailerNumber());
				pw.addCell(member.getStopNumber());
				pw.addCell(member.getPickupTime());
				pw.addCell(member.getTransportationMode());
				pw.addCell(member.getConsolidationNumber());
				pw.addCell(member.getMrCount());
				pw.addCell(member.getTransportationPriority());
				pw.addCell(member.getHazardous());
				pw.addCell(member.getOconusFlag());
			}
			pw.addCell(StringHandler.emptyIfNull(member.getShipToLocationDesc())
					+ member.getShipToCity()
					+ member.getShipToStateAbbrev());
			pw.addCell(member.getBin());
			pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
			pw.addCell(member.getPickQty());
			pw.addCell(member.getNeedDatePrefix()+" "+member.getNeedDate());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getDeliveryType());
			pw.addCell(member.getPartDescription());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getMrNotes());
		}
		return pw;
	}
	
	public ExcelHandler getConsPicklistExcelReport(String picklistID, Locale locale) throws
	BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
    	GenericSqlFactory f = new GenericSqlFactory(dbManager,new ConsolidatedItemPickListBean());
		String Where=" PICKLIST.PICKLIST_ID =" + picklistID + "";
	    String Query_Statement="";
		Query_Statement="Select PICKLIST_ID,item_id,CATALOG_ID, CAT_PART_NO, PART_GROUP_NO,RECEIPT_ID,bin,sum(picklist_quantity) QUANTITY,PART_DESCRIPTION from table (pkg_pick.fx_picklist("+picklistID+")) where ";
		Query_Statement+=" PICKLIST_QUANTITY is not null group by PICKLIST_ID,item_id,CATALOG_ID, CAT_PART_NO, PART_GROUP_NO,RECEIPT_ID,bin,PART_DESCRIPTION ";
		Vector<ConsolidatedItemPickListBean> v = (Vector<ConsolidatedItemPickListBean>)f.selectQuery(Query_Statement);
		
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//		write column headers
		pw.addCellBold(library.getString("label.picklistno"));
		pw.addCell(picklistID);
		pw.addRow();

		int []widths;
		String[] headerkeys;

		headerkeys = new String[]  {"label.item","label.partnumber","label.description","label.bin","label.receiptid","label.qty"};
		widths = new int[] {10,10,50,10,10,10};

		int[] types;
		types = new  int[] {pw.TYPE_NUMBER,0,pw.TYPE_PARAGRAPH,0,pw.TYPE_NUMBER,pw.TYPE_NUMBER};
		int[] aligns;
		aligns = new int[] {0,0,0,0,0,0,};
		
		pw.applyColumnHeader(headerkeys, types, widths, aligns);
		for (ConsolidatedItemPickListBean member : v) {
			pw.addRow();
    		pw.addCell(member.getItemId());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getPartDescription());
			pw.addCell(member.getBin());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getQuantity());
		}
		return pw;
	}
	
	public void attachSerialNumbers(Collection<PicklistReprintViewBean> mrLines) throws BaseException {	
		Collection<PicklistReprintViewBean> ret = new ArrayList();
		
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new BoxLabelBean());
		
		for(PicklistReprintViewBean line: mrLines) {
			
			if(line.isSerialNumberTracked()) {
				String query  = "SELECT bx.box_label_id, bx.serial_number " + 
							   "   FROM picklist_view1 p,box_label_issue bi, box_label bx" +
							   "  WHERE p.picklist_id = " + line.getPicklistId() +
							   "	AND p.pr_number = " + line.getPrNumber() +
							   " 	AND p.line_item = " + line.getLineItem() +
							   "	AND p.bin = " + SqlHandler.delimitString(line.getBin()) +
							   "	AND p.issue_id = bi.issue_id" +
							   "    AND bi.box_label_id = bx.box_label_id " +
							  "ORDER BY box_label_id";
				
				Collection boxLabels = genericSqlFactory.selectQuery(query.toString()); 
				
				line.setBoxLabels(boxLabels);
			}
		}			
	}
	
	public void updateSerialNumbers(Collection boxes) throws BaseException{
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new BoxLabelBean());

		Iterator iter = boxes.iterator();
		while(iter.hasNext()) {
			BoxLabelBean box = (BoxLabelBean)iter.next();

			String query  = "UPDATE box_label" + 
							"   SET serial_number = " + SqlHandler.delimitString(box.getSerialNumber()) +
							" WHERE box_label_id = " + box.getBoxLabelId();
			
			genericSqlFactory.deleteInsertUpdate(query);
		}
	}
}
