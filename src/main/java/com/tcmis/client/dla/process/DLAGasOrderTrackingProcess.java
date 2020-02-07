/*
 * VMIProcess.java
 *
 * Created on March 28, 2008, 12:12 PM
 */

package com.tcmis.client.dla.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.dla.beans.DlaGasSupplierTrackingViewBean;
import com.tcmis.client.dla.factory.DlaGasSupplierTrackingViewBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.SupplierShippingInputBean;
/**
 *
 * @author  mnajera
 */
public class DLAGasOrderTrackingProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	/** Creates a new instance of VMIProcess */
	public DLAGasOrderTrackingProcess(String client) {
		super(client);
	}

	public DLAGasOrderTrackingProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getFacilityInventoryGroup() throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		return (new com.tcmis.client.common.factory.FacilityInventoryGroupBeanFactory(dbManager)).selectFix();
	}

	public Collection<DlaGasSupplierTrackingViewBean> getSearchResult(SupplierShippingInputBean bean,PersonnelBean personnelBean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		DlaGasSupplierTrackingViewBeanFactory currentFactory = new DlaGasSupplierTrackingViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		String s = null;
		s = bean.getSupplier();
		
		if (!"AWAITING ADDRESS".equalsIgnoreCase(bean.getStatus())) {
			if(!"Radian".equalsIgnoreCase(personnelBean.getCompanyId()) && !personnelBean.getPermissionBean().hasSupplierPermission("SupplierAndHubOrders", "", ""))
			{
					String supplierQuery = " select supplier from user_supplier where personnel_id = " + personnelBean.getPersonnelId() ;
					criteria.addCriterion("supplier", SearchCriterion.IN, supplierQuery);
			}		
			else if ( s != null && !s.isEmpty())
			{
				if("WESCO".equalsIgnoreCase(s))
				{
					if("all".equalsIgnoreCase(bean.getHub()))
						criteria.addCriterion("branchPlant", SearchCriterion.IN, "select branch_plant from dla_gas_hub_view");
					else
						criteria.addCriterion("branchPlant",SearchCriterion.EQUALS,bean.getHub());
				}
				else	
					criteria.addCriterion("supplier",SearchCriterion.EQUALS,s);
			}
		
			if(bean.getSuppLocationIdArray() != null && bean.getSuppLocationIdArray().length > 0) {
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
		}

		s = bean.getSearchArgument();
		if ( s != null && !s.equals("") ) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
			if (bean.getSearchField() != null && bean.getSearchField().equalsIgnoreCase("callNumber"))
			{
				s = bean.getContractNumber() + "-"+ s;
				field = "poNumber";
			}
			else
				criteria.addCriterion("contractNumber",
						SearchCriterion.EQUALS,bean.getContractNumber());
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
		else
			criteria.addCriterion("contractNumber",
					SearchCriterion.EQUALS,bean.getContractNumber());			

		String status = bean.getStatus();
		String additionalWhereClause = "";

		if ( status != null && !status.equals("") ) {
			if (status.equalsIgnoreCase("OPEN ORDERS"))
			{
				additionalWhereClause = " and upper(ORDER_STATUS) not in ('SHIPPED','CANCELED') ";
				/*criteria.addCriterion("openQuantity", SearchCriterion.GREATER_THAN,"0");
        /*criteria.addCriterion("shipmentId", SearchCriterion.IS,null);
        criteria.addCriterion("cancelStatus", SearchCriterion.IS,null);*/
			}
			else if (status.equalsIgnoreCase("PO NOT CONFIRMED"))
			{
				/*DATE_FIRST_CONFIRMED IS NULL AND cancel_status IS NULL/*Po is not confirmed at supplier-po sent*/
				criteria.addCriterion("dateFirstConfirmed", SearchCriterion.IS,null);
				criteria.addCriterion("cancelStatus", SearchCriterion.IS,null);
			}
			else if (status.equalsIgnoreCase("AT SUPPLIER - NOT SHIPPED"))
			{
				/*--DATE_CONFIRMED is not null and ISSUE_QUANTITY is null /*Not picked, at supplier not shipped*/
				criteria.addCriterion("dateFirstConfirmed", SearchCriterion.IS_NOT,null);
				criteria.addCriterion("issueQuantity", SearchCriterion.IS,null);
			}
			else if (status.equalsIgnoreCase("PICKED NOT SHIP CONFIRMED"))
			{
				/*--DATE_CONFIRMED is not null and ISSUE_QUANTITY is not null and shipment_id is null/*Picked For Shipment*/
				criteria.addCriterion("dateFirstConfirmed", SearchCriterion.IS_NOT,null);
				criteria.addCriterion("issueQuantity", SearchCriterion.IS_NOT,null);
				criteria.addCriterion("shipmentId", SearchCriterion.IS,null);
			}
			else if (status.equalsIgnoreCase("SHIPPED"))
			{
				criteria.addCriterion("shipmentId", SearchCriterion.IS_NOT,null);
			}
			else if (status.equalsIgnoreCase("IN PREPERATION FOR ASN"))
			{
				/*--shipment_id is not null and VERIFIED_FOR_ASN ='YES' and asn_id is null/*In preperation for ASN*/
				criteria.addCriterion("shipmentId", SearchCriterion.IS_NOT,null);
				criteria.addCriterion("asnStatus", SearchCriterion.NOT_EQUAL,"SENT");
				/*criteria.addCriterion("asnId", SearchCriterion.IS,null);*/
			}
			else if (status.equalsIgnoreCase("ASN SENT"))
			{
				/*--shipment_id is not null and VERIFIED_FOR_ASN ='YES' and asn_id is not null/*ASN Sent*/
				/*criteria.addCriterion("asnId", SearchCriterion.IS_NOT,null);*/
				criteria.addCriterion("asnStatus", SearchCriterion.EQUALS,"SENT");
			}
			else if (status.equalsIgnoreCase("CANCELED"))
			{
				/*--cancel_status = 'canceled' /*Canceled Orders*/
				criteria.addCriterion("cancelStatus", SearchCriterion.EQUALS,"canceled");
			}
			else if (status.equalsIgnoreCase("AWAITING ADDRESS"))
			{
				criteria.addCriterion("orderStatus", SearchCriterion.EQUALS, "Awaiting Address");
			}
			else if (status.equalsIgnoreCase("ALLEXCEPTCANCELED"))
			{
				criteria.addCriterion("orderStatus", SearchCriterion.NOT_EQUAL, "Canceled");
			} 
		}
		
		/* Searching for order FROM date */
		if (bean.hasDateFrom()) {
			criteria.addCriterion(bean.getDateOpt(), SearchCriterion.FROM_DATE, bean.getDateFrom());
		}
		/* Searching for order TO date */
		if (bean.hasDateTo()) {
			
			String whichDate = bean.getDateOpt();
			if("dateSent".equalsIgnoreCase(whichDate))
			{
				final long MILLISECONDS_PER_DAY = 86400000;
				Date endOfDay = new Date( bean.getDateSentEnd().getTime() + MILLISECONDS_PER_DAY );
				criteria.addCriterion("dateSent",SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatOracleDate(endOfDay));
			}
			else if("desiredShipDate".equalsIgnoreCase(whichDate))
			{
				final long MILLISECONDS_PER_DAY = 86400000;
				Date endOfDay = new Date( bean.getDesiredShipDateEnd().getTime() + MILLISECONDS_PER_DAY );
				criteria.addCriterion("desiredShipDate",SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatOracleDate(endOfDay));
			}
			else
				criteria.addCriterion(bean.getDateOpt(), SearchCriterion.TO_DATE, bean.getDateTo());
		}


		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("radianPo");
		scriteria.addCriterion("poNumber");

		return currentFactory.select(criteria, scriteria,additionalWhereClause);
	}

	public Object[] showResult(SupplierShippingInputBean inputBean,PersonnelBean personnelBean) throws BaseException {
		Vector bv = (Vector) this.getSearchResult(inputBean, personnelBean);
		HashMap m1 = new HashMap();
		Integer i1 = null;
		String partNum = null;

		DlaGasSupplierTrackingViewBean pbean = null;
		for (int i = 0; i < bv.size(); i++) {
			pbean = (DlaGasSupplierTrackingViewBean) bv.get(i);
			partNum = NumberHandler.emptyIfNull(pbean.getRadianPo())
			+StringHandler.emptyIfNull(pbean.getPoNumber());

			if (m1.get(partNum) == null) {
				i1 = new Integer(0);
				m1.put(partNum, i1);
			}
			i1 = (Integer) m1.get(partNum);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(partNum, i1);
		}

		Object[] objs = {bv,m1};
		return objs;
	}

	public ExcelHandler  getExcelReportNoSplit(Collection bean, Locale locale,String module,PersonnelBean personnelBean) throws NoDataException, BaseException {

		if (log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<DlaGasSupplierTrackingViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//		  write column headers
		pw.addRow();
		
		boolean hasSupplierAndHubOrdersPerm = "Radian".equalsIgnoreCase(personnelBean.getCompanyId()) || personnelBean.getPermissionBean().hasSupplierPermission("SupplierAndHubOrders", "", "");

		if(hasSupplierAndHubOrdersPerm)
		{
			String[] headerkeys = {
					"label.haaspo","label.dlapriority","label.supplierpriority","label.order.type","label.usgovorderdate",
					"label.date.created","label.datesent","label.dlametricdate", "label.orderqty", "label.status",
					"label.shippingsupplierlocation","label.originalsupplierlocation","label.hub","label.inventorygroup","label.projectedshipdate","label.actualshipdate","label.orderage","label.quantityopen",
					"label.qtyshipped","label.shipto","label.ordernumber","label.milstrip","label.shiptolocationid", "label.shiptododaac", "label.ultimatedodaac",
					"label.oconus","label.part","label.shortname","label.ordercomments","label.carrier",
					"label.trackingno","label.shippedby","label.deliverycoments","label.origininspectionrequired","label.datesenttowawf"};
			int[] types = {
					0,0,0,0,pw.TYPE_DATE,
					pw.TYPE_DATE,pw.TYPE_DATE,pw.TYPE_CALENDAR,pw.TYPE_NUMBER, pw.TYPE_STRING,
					0,0,0,0,pw.TYPE_CALENDAR,pw.TYPE_CALENDAR,0,pw.TYPE_NUMBER,
					pw.TYPE_NUMBER,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,0,0,
					0,0,0,pw.TYPE_PARAGRAPH,0,
					0,0,pw.TYPE_PARAGRAPH,0,pw.TYPE_DATE
			};
			int[] widths = {
					13,0,0,0,0,
					0,0,0,0,0,
					20,20,10,10,16,14,0,0,
					13,30,25,18,18,12,12,
					0,15,22,0,20,
					22,28,0,0,0
					};
			int[] aligns = {
					pw.ALIGN_CENTER,0,0,0,0,
					0,0,0,0,0,
					0,0,0,0,0,0,0,0,
					0,0,pw.ALIGN_CENTER,pw.ALIGN_CENTER,pw.ALIGN_CENTER,0,0,
					pw.ALIGN_CENTER,pw.ALIGN_CENTER,0,0,0,
					0,0,0,0,0
					};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);
		}
		else
		{
			String[] headerkeys = {
					"label.haaspo","label.dlapriority","label.supplierpriority","label.order.type","label.usgovorderdate",
					"label.date.created","label.datesent","label.dlametricdate", "label.orderqty", "label.status",
					"label.shippingsupplierlocation","label.originalsupplierlocation","label.projectedshipdate","label.actualshipdate","label.orderage","label.quantityopen",
					"label.qtyshipped","label.shipto","label.ordernumber","label.shiptolocationid", "label.ultimatedodaac",
					"label.oconus","label.part","label.shortname","label.ordercomments","label.carrier",
					"label.trackingno","label.shippedby","label.deliverycoments","label.origininspectionrequired","label.datesenttowawf"};
			int[] types = {
					0,0,0,0,pw.TYPE_DATE,
					pw.TYPE_DATE,pw.TYPE_DATE,pw.TYPE_CALENDAR,pw.TYPE_NUMBER, pw.TYPE_STRING,
					0,0,pw.TYPE_CALENDAR,pw.TYPE_CALENDAR,0,pw.TYPE_NUMBER,
					pw.TYPE_NUMBER,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,0,
					0,0,0,pw.TYPE_PARAGRAPH,0,
					0,0,pw.TYPE_PARAGRAPH,0,pw.TYPE_DATE
			};
			int[] widths = {
					13,0,0,0,0,
					0,0,0,0,0,
					20,20,16,14,0,0,
					13,30,25,18,12,
					0,15,22,0,20,
					22,28,0,0,0
					};
			int[] aligns = {
					pw.ALIGN_CENTER,0,0,0,0,
					0,0,0,0,0,
					0,0,0,0,0,0,
					0,0,pw.ALIGN_CENTER,pw.ALIGN_CENTER,0,
					pw.ALIGN_CENTER,pw.ALIGN_CENTER,0,0,0,
					0,0,0,0,0
					};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);
		}
		//pw.setColumnHeader(headerkeys, types, widths, aligns);
		//pw.setColumnWidth(21,15);
		
		// now write data
		int i = 1;
		for (DlaGasSupplierTrackingViewBean member : data) {
			i++;
			pw.addRow();
			String openQuantity = "";
			String qtyShipped = "";
			openQuantity =  NumberHandler.emptyIfNull(member.getOpenQuantity());
			qtyShipped = NumberHandler.emptyIfNull(member.getIssueQuantity());
			BigDecimal qtyship = member.getIssueQuantity();
			BigDecimal qtyopen = member.getOpenQuantity();
			if (member.getIssueQuantity() !=null && member.getIssueQuantity().intValue() > 0 && member.getShipmentId() ==null)
			{
				openQuantity = qtyShipped;
				qtyopen = member.getIssueQuantity();
				qtyShipped = "";
				qtyship = null;
			}
			else if (member.getIssueQuantity() !=null && member.getIssueQuantity().intValue() > 0 && member.getShipmentId() !=null)
			{
				member.setVendorShipDate(null);
				openQuantity = "";
				qtyopen = null;
			}
			if( member.getShipmentId() ==null )
				member.setDateShipped(null);

			pw.addCell(member.getRadianPo());
			if (module.equalsIgnoreCase("haas"))
			{
				pw.addCell(member.getIpg());
			}
			else
			{
				pw.addCell("");
			} 
			pw.addCell(member.getAirgasIpg());
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
			pw.addCellDateTime(member.getOrderDate());
			pw.addCellDateTime(member.getDateCreated());
			pw.addCell(member.getDateSent());
			/*dateConfirmed,*/
			pw.addCell(member.getDesiredShipDate());
			pw.addCell(member.getOrderQuantity());
			pw.addCell(member.getOrderStatus());
			pw.addCell(member.getShipFrom());
			pw.addCell(member.getOriginalShipFrom());
			if(hasSupplierAndHubOrdersPerm)
			{
				pw.addCell(member.getHubName());
				pw.addCell(member.getInventoryGroupName());
			}
			if(member.getShipmentId() == null)
			{
				if(member.getExpediteDate() != null)
				{	 pw.addCell(member.getExpediteDate());}
				else
				{  pw.addCell(member.getVendorShipDate());}
			}
			else
			{ pw.addCell("");}
			//   		  pw.addCell(member.getVendorShipDate());

			pw.addCell(member.getDateShipped());
			pw.addCell(member.getOrderAge());
			pw.addCell(qtyopen);//openQuantity,
			pw.addCell(qtyship);//qtyShipped,
			pw.addCell(member.getShipToInfo());
			pw.addCell(member.getPoNumber());
			pw.addCell(member.getMilstripCode());
			pw.addCell("USGOV."+member.getShipToLocationId());
			pw.addCell(member.getShipViaDodaac());
			pw.addCell(member.getShipToDodaac());
			pw.addCell(member.getOconus());
			pw.addCell(member.getFacPartNo());
			pw.addCell(member.getPartShortName());
			pw.addCell(member.getDeliveryComments());
			pw.addCell(member.getCarrierName());
			pw.addCell(member.getTrackingNumber());
			pw.addCell(member.getIssuer());
			pw.addCell(member.getComments());
			pw.addCell(member.getOriginInspectionRequired());
			pw.addCell(member.getDateSentWawf());

		}
		/*	This is some test example for customized cells.
		pw.addRow();
		pw.addTdEmpty(14);
		int style = pw.getStyle(pw.ALIGN_CENTER,pw.VALIGN_CENTER,pw.BORDER_DOTTED,pw.COLOR_YELLOW,0,0);
		String formula = "SUM(O2:O"+i+")";
		pw.insertCell(formula,pw.TYPE_FORMULA,style);
		 */
		return pw;
	}
	public String changeSupplier(String radianPO,String inventoryGroup ) throws
	BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
		Vector inArgs = new Vector();
		String errorMsg ="";
		Collection coll = new Vector();
		inArgs.add(new BigDecimal(radianPO));
		inArgs.add(inventoryGroup);
		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR)) ;
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		try{
		    coll = procFactory.doProcedure("p_move_ig_for_gas", inArgs,outArgs);
		  }
		catch (Exception e) {			
			e.printStackTrace();
			errorMsg = library.getString("error.db.update");
		}
		if((String)((Vector)coll).get(0)!=null)
		{
		 errorMsg = (String)((Vector)coll).get(0);
		}
		
		return errorMsg;

	}
	public String cancelOrder(String refNum,String comments ) throws Exception {
		Vector inArgs = new Vector();
		String errorCode = "";
		inArgs.add( refNum );
		inArgs.add( comments );
		Vector outArgs = new Vector();
		//		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		try {
			if (log.isDebugEnabled()) {
				log.debug("p_gas_cancel_by_milstrip  "+inArgs);
			}
			Collection coll = procFactory.doProcedure("p_gas_cancel_by_milstrip", inArgs,outArgs);
		}
		catch (Exception e) {
			errorCode = e.getMessage();
		}
		//		String errorMsg = (String)((Vector)coll).get(0);
		return errorCode;
	}
}
