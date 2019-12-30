package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import com.tcmis.common.util.SqlHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.tcmis.internal.hub.beans.PickingInputBean;
import com.tcmis.internal.hub.beans.PicklistViewBean;
import com.tcmis.internal.hub.factory.PicklistViewBeanFactory;
import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Process for picking qc
 * @version 1.0
 *****************************************************************************/
public class PickingQcProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PickingQcProcess(String client) {
		super(client);
	}

	public PickingQcProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getNormalizedPicklistCollection(PickingInputBean bean) throws BaseException {
		return getNormalizedPicklistCollection(getPickList(bean));
	}
	
	public Vector bolPrintData(Collection pickedMrs) {
		Vector printData = new Vector();
		Vector prnumbers = new Vector();
		Vector linenumber = new Vector();
		Vector picklistid = new Vector();
		Hashtable FResult = new Hashtable();

		Iterator mainIterator = pickedMrs.iterator();
		while (mainIterator.hasNext()) {
			PicklistViewBean picklistViewBean = (PicklistViewBean) mainIterator.next();

				prnumbers.addElement("" + picklistViewBean.getPrNumber() + "");
				linenumber.addElement("" + picklistViewBean.getLineItem() + "");
				//log.debug("getPicklistId  "+shipConfirmInputBean.getPicklistId());
				picklistid.addElement("" + picklistViewBean.getPicklistId() + "");

			
		}

		FResult.put("mr_number", prnumbers);
		FResult.put("line_number", linenumber);
		FResult.put("picklist_number", picklistid);
		printData.add(FResult);
		return printData;
	}
	
	public Vector exitLabelPrintData(Collection<PicklistViewBean>pickedMrs) 
	{
		Vector dataVec = new Vector();
		Hashtable data;
		data = new Hashtable();
		data.put("TOTAL_NUMBER",pickedMrs.size());
		dataVec.add(data);
		for(PicklistViewBean picklistViewBean:pickedMrs)
		{
			data = new Hashtable();
		    data.put("MR_LINE",picklistViewBean.getMrLine());
		    data.put("ITEM_ID",picklistViewBean.getItemId());
		    data.put("PICKLIST_ID",picklistViewBean.getPicklistId());
		    data.put("RECEIPT_ID",picklistViewBean.getReceiptId());
			data.put("FACILITY_ID",picklistViewBean.getFacilityId());
			data.put("APPLICATION",picklistViewBean.getApplication());
			data.put("CAT_PART_NO",picklistViewBean.getCatPartNo());
			data.put("QUANTITY_PICKED",picklistViewBean.getQuantityPicked());
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
			data.put("EXPIRE_DATE",date.format(picklistViewBean.getExpireDate()));
			//data.put("CUSTOMER_PART_NO",picklistViewBean.
			data.put("LINE_STATUS","");
			data.put("USERCHK","yes");
		    data.put("REPORTING_ENTITY_NAME",picklistViewBean.getReportingEntityName());
			data.put( "PICKABLE",picklistViewBean.getPickable());
		    data.put("CUSTOMER_PART_NO",picklistViewBean.getCustomerPartNo() != null ? picklistViewBean.getCustomerPartNo() : "");
		    data.put("APPLICATION_DESC",picklistViewBean.getApplicationDesc());
			data.put("ISSUE_ID",picklistViewBean.getIssueId());
			data.put("OWNER_SEGMENT_ID",picklistViewBean.getOwnerSegmentId() != null ? picklistViewBean.getOwnerSegmentId() : "");
			data.put("ALLOCATE_BY_CHARGE_NUMBER_1",picklistViewBean.getAllocateByChargeNumber1() != null ? picklistViewBean.getAllocateByChargeNumber1() : "");
			data.put("ALLOCATE_BY_CHARGE_NUMBER_2",picklistViewBean.getAllocateByChargeNumber2() != null ? picklistViewBean.getAllocateByChargeNumber2() : "");
			data.put("ALLOCATE_BY_CHARGE_NUMBER_3",picklistViewBean.getAllocateByChargeNumber3() != null ? picklistViewBean.getAllocateByChargeNumber3() : "");
			data.put("ALLOCATE_BY_CHARGE_NUMBER_4",picklistViewBean.getAllocateByChargeNumber4() != null ? picklistViewBean.getAllocateByChargeNumber4() : "");
			data.put("RECEIPT_SPEC_NAME_LIST",picklistViewBean.getReceiptSpecNameList() != null ? picklistViewBean.getReceiptSpecNameList() : "");
			data.put("RECEIPT_SPEC_VERSION",picklistViewBean.getReceiptSpecVersion() != null ? picklistViewBean.getReceiptSpecVersion() : "");
			data.put("PO_NUMBER",picklistViewBean.getPoNumber() != null ? picklistViewBean.getPoNumber() : "");
			data.put("RELEASE_NUMBER",picklistViewBean.getReleaseNumber() != null ? picklistViewBean.getReleaseNumber() : "");
			dataVec.add(data);
		}
		return dataVec;
	}
	
	public Vector rePrintData(Collection<PicklistViewBean>pickedMrs) 
	{
		Vector dataVec = new Vector();
		Hashtable data;
		data = new Hashtable();
		data.put("TOTAL_NUMBER",pickedMrs.size());
		dataVec.add(data);
		for(PicklistViewBean picklistViewBean:pickedMrs)
		{
			data = new Hashtable();
			data.put("ISSUE_ID",picklistViewBean.getIssueId());
		    data.put("MR_LINE",picklistViewBean.getMrLine());
		    data.put("ITEM_ID",picklistViewBean.getItemId());
		    data.put("PICKLIST_ID",picklistViewBean.getPicklistId());
		    data.put("RECEIPT_ID",picklistViewBean.getReceiptId());
			data.put("FACILITY_ID",picklistViewBean.getFacilityId());
			data.put("APPLICATION",picklistViewBean.getApplication());
			data.put("CAT_PART_NO",picklistViewBean.getCatPartNo());
			data.put("QUANTITY_PICKED",picklistViewBean.getQuantityPicked());
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
			data.put("EXPIRE_DATE",date.format(picklistViewBean.getExpireDate()));
			//data.put("CUSTOMER_PART_NO",picklistViewBean.
			data.put("LINE_STATUS","");
			data.put("USERCHK","yes");
			data.put( "PICKABLE",picklistViewBean.getPickable());
		    data.put("CUSTOMER_PART_NO",!StringHandler.isBlankString(picklistViewBean.getCustomerPartNo())  ? picklistViewBean.getCustomerPartNo() : "");
		    data.put("REPORTING_ENTITY_NAME",picklistViewBean.getReportingEntityName());
		    data.put("APPLICATION_DESC",picklistViewBean.getApplicationDesc());
			dataVec.add(data);
		}
		return dataVec;
	}
	
	public Vector deliverTicket(Collection<PicklistViewBean>pickedMrs) 
	{
		Vector dataVec = new Vector();
		Hashtable data;
		data = new Hashtable();
		data.put("TOTAL_NUMBER",pickedMrs.size());
		dataVec.add(data);
		for(PicklistViewBean picklistViewBean:pickedMrs)
		{
			data = new Hashtable();
		    data.put("MR_LINE",picklistViewBean.getMrLine());
		    data.put("ITEM_ID",picklistViewBean.getItemId());
		    data.put("PICKLIST_ID",picklistViewBean.getPicklistId());
		    data.put("RECEIPT_ID",picklistViewBean.getReceiptId());
			data.put("FACILITY_ID",picklistViewBean.getFacilityId());
			data.put("APPLICATION",picklistViewBean.getApplication());
			data.put("CAT_PART_NO",picklistViewBean.getCatPartNo());
			data.put("QUANTITY_PICKED",picklistViewBean.getQuantityPicked());
			data.put("PICKLIST_QUANTITY",picklistViewBean.getPicklistQuantity());
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
			//data.put("QC_DATE",date.format(picklistViewBean.getQcDate()));
			data.put("EXPIRE_DATE",date.format(picklistViewBean.getExpireDate()));
			//data.put("NEED_DATE",date.format(picklistViewBean.getNeedDate()));
			//data.put("CUSTOMER_PART_NO",picklistViewBean.
			//data.put("LINE_STATUS","");
			data.put("USERCHK","yes");
			//data.put( "PICKABLE",picklistViewBean.getPickable());
		    //data.put("CUSTOMER_PART_NO",picklistViewBean.getCustomerPartNo() != null ? picklistViewBean.getCustomerPartNo() : "");
		    //data.put("REPORTING_ENTITY_NAME",picklistViewBean.getReportingEntityName());
		    data.put("APPLICATION_DESC",picklistViewBean.getApplicationDesc());
		    data.put("REQUESTOR",picklistViewBean.getRequestor());
		    //data.put("HUB",picklistViewBean.getHub());
		    //data.put("INVENTORY_GROUP",picklistViewBean.getInventoryGroup());
		    data.put("PART_DESCRIPTION",picklistViewBean.getPartDescription());
		    data.put("SHIP_TO_LOCATION_ID",picklistViewBean.getShipToLocationId());
		    data.put("PACKAGING",picklistViewBean.getPackaging());
		    data.put("COMPANY_ID",picklistViewBean.getCompanyId());
		    data.put("END_USER",!StringHandler.isBlankString(picklistViewBean.getEndUser()) ? picklistViewBean.getEndUser() : "");
		    data.put("MFG_LOT",picklistViewBean.getMfgLot());

			dataVec.add(data);
		}
		return dataVec;
	}
	
	public Collection getPickList(PickingInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PicklistViewBeanFactory factory = new PicklistViewBeanFactory(dbManager);
		String searchCriteria = null;
		
		if (bean.getPrNumber()!=null && bean.getPrNumber().length()>0) {
			searchCriteria =  "_by_mr('" + SqlHandler.validQuery(bean.getHub())  + "'," + SqlHandler.validQuery(bean.getPrNumber());
		}
		else if (bean.getPicklistId()!=null) {
			searchCriteria = "('" + SqlHandler.validQuery(bean.getHub()) + "'," + bean.getPicklistId().toPlainString();
		}
		else if (bean.getFacilityId()!=null && bean.getFacilityId().length()>0) {
			searchCriteria =  "('" + SqlHandler.validQuery(bean.getHub())  + "','" + SqlHandler.validQuery(bean.getFacilityId()) + "'";
		}
		else if (bean.getInventoryGroup()!=null && bean.getInventoryGroup().length()>0) {
			searchCriteria =  "('" + SqlHandler.validQuery(bean.getHub())  + "','" + SqlHandler.validQuery(bean.getInventoryGroup()) + "'";
		}
		else if (bean.getHub()!=null && bean.getHub().length()>0) {
			searchCriteria = "('"+ SqlHandler.validQuery(bean.getHub()) + "'";
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("mrLine");
		sortCriteria.addCriterion("picklistId");
		sortCriteria.addCriterion("itemId");

		return factory.select(sortCriteria,searchCriteria);
	}

	public void enterPick(Collection inputBeans, BigDecimal personnelId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PicklistViewBeanFactory factory = new PicklistViewBeanFactory(dbManager);
		// Collection pickedPicklistIds = new Vector();
		Iterator iter = inputBeans.iterator();
		PicklistViewBean pickBean = null;
		Collection issueIds = new Vector();
		Collection errorCodes = new Vector();
		HashSet pickedPicklistIds = new HashSet();
		PicklistViewBean prevBean = null;
		Vector args;
		while (iter.hasNext()) {
			pickBean = (PicklistViewBean) iter.next();
			if (pickBean.getQuantityPicked()!=null && pickBean.getQuantityPicked().intValue()>=0) {
				log.info("calling p_enter_pick("+pickBean.getPicklistId()+","+pickBean.getCompanyId()+","+pickBean.getPrNumber()+","+pickBean.getLineItem()+","+pickBean.getNeedDate()+","+pickBean.getHub()+","+pickBean.getReceiptId()+",now,"+pickBean.getQuantityPicked().toString()+","+personnelId+")");
				args = new Vector(10);
				if (pickBean.getPicklistId()!=null) args.add(pickBean.getPicklistId()); else args.add("");
				if (pickBean.getCompanyId()!=null) args.add(pickBean.getCompanyId()); else args.add("");
				if (pickBean.getPrNumber()!=null) args.add(pickBean.getPrNumber());  else args.add("");
				if (pickBean.getLineItem()!=null) args.add(pickBean.getLineItem()); else args.add("");
				args.add(new Timestamp(pickBean.getNeedDate().getTime()));
				if (pickBean.getHub()!=null) args.add(pickBean.getHub()); else args.add("");
				if (pickBean.getReceiptId()!=null) args.add(pickBean.getReceiptId()); else  args.add("");
				args.add(new Timestamp((new Date()).getTime()));
				if (pickBean.getQuantityPicked()!=null) args.add(pickBean.getQuantityPicked()); else args.add("");
				args.add(personnelId);
				Collection result = factory.enterPick(args);
				if (result!=null) {
					Iterator it = result.iterator();
					if (it.hasNext()) {
						Object o = it.next();
						if (o!=null) {
							issueIds.add(o);
							pickedPicklistIds.add(pickBean.getPicklistId());
							if (log.isDebugEnabled()) {
								log.debug("issueId for picklistid:" +pickBean.getPicklistId() + ", reciept: " + pickBean.getReceiptId() +": " +o.toString());
							}
						}
						if (it.hasNext()) {
							o = it.next();
							if (o!=null) {
								errorCodes.add(o);
								if (log.isDebugEnabled()) {
									log.debug("error for " +pickBean.getPicklistId() + "-" + pickBean.getReceiptId() +": " +o.toString());
								}
							}
						}
					}
				}
			}
			
			if(!iter.hasNext() || (prevBean != null  && !(StringHandler.emptyIfNull(prevBean.getMrLine())).equalsIgnoreCase(pickBean.getMrLine())))
				lineClose(pickBean);
			
			prevBean = pickBean;
		}
		this.lineItemAllocate(inputBeans);
		// call p_qc_picklist for all picked picklist id's
		iter = pickedPicklistIds.iterator();
		BigDecimal enteredPickId;
		while (iter.hasNext()) {
			enteredPickId = (BigDecimal) iter.next();
			log.info("calling p_qc_picklist("+enteredPickId+","+personnelId+")");
			args = new Vector(2);
			args.add(enteredPickId);
			args.add(personnelId);
			factory.qcPicklist(args);
		}
	}

	public void lineClose(PicklistViewBean pickBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PicklistViewBeanFactory factory = new PicklistViewBeanFactory(dbManager);
		if (!StringHandler.isBlankString(pickBean.getCloseMr()) && pickBean.getCloseMr().equalsIgnoreCase("Y")) {
			log.info("calling p_line_close_from_pickqc for company_id="+ pickBean.getCompanyId() + ",pr_number=" + pickBean.getPrNumber() + ",line=" + pickBean.getLineItem());
			Vector args = new Vector(3);
			args.add(pickBean.getCompanyId());
			args.add(pickBean.getPrNumber());
			args.add(pickBean.getLineItem());
			factory.lineCloseFromPickqc(args);
		}
	}
	
	public void lineItemAllocate(Collection<PicklistViewBean> inputBeans) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PicklistViewBeanFactory factory = new PicklistViewBeanFactory(dbManager);
		for (PicklistViewBean pickBean: inputBeans) {
			if (pickBean.getCabinetReplenishment().equalsIgnoreCase("Y") && pickBean.getCloseMr()!=null && pickBean.getCloseMr().length()>0) 
			{
				log.info("calling P_LINE_ITEM_ALLOCATE  for company_id="+ pickBean.getCompanyId() + ",pr_number=" + pickBean.getPrNumber() + ",line=" + pickBean.getLineItem());
				Vector args = new Vector(5);
				args.add(pickBean.getCompanyId());
				args.add(pickBean.getPrNumber());
				args.add(pickBean.getLineItem());
				args.add("Y");
				args.add(new Timestamp(pickBean.getNeedDate().getTime()));
				factory.lineItemAllocateFromPickqc(args);
			}
		}
	}
	

	
	public Collection reversePick(PickingInputBean pickBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PicklistViewBeanFactory factory = new PicklistViewBeanFactory(dbManager);
		log.info("calling p_reverse_pick_qc(pr_number=" + pickBean.getPrNumber() + ",line=" + pickBean.getLineItem()+",PicklistId="+ pickBean.getPicklistId()+")");
		Vector args = new Vector(3);
		if (pickBean.getPrNumber()!=null) args.add(pickBean.getPrNumber());  else args.add("");
		if (pickBean.getLineItem()!=null) args.add(pickBean.getLineItem()); else args.add("");
		if (pickBean.getPicklistId()!=null) args.add(pickBean.getPicklistId()); else args.add("");
		return factory.reversePick(args);
	}

	public Collection getNormalizedPicklistCollection(Collection flatData) throws BaseException {
		PicklistViewBean flatBean = null;
		PicklistViewBean mrLine = null;
		PicklistViewBean itemId = null;
		PicklistViewBean receiptId = null;
		String lastMrLine = "";
		String lastItemId = "";
		Vector mrLines = new Vector();
		Vector itemIds = null;
		Vector receiptIds = null;
		Iterator flatDataIter = flatData.iterator();
		int mrLineSize = 0;
		try {
			while (flatDataIter.hasNext()) {
				flatBean = (PicklistViewBean) flatDataIter.next();
				if (! flatBean.getMrLine().equalsIgnoreCase(lastMrLine)) {
					// new mr-Line
					if (mrLine != null) {
						itemId.setReceipts((Vector)receiptIds.clone());
						itemId.setReceiptRowspan(receiptIds.size());
						itemIds.add(itemId.clone());
						mrLine.setItems((Vector)itemIds.clone());
						mrLine.setItemRowspan(mrLineSize + receiptIds.size());
						mrLines.add(mrLine.clone());
					}
					mrLine = new PicklistViewBean();
					copyMrLineData(flatBean,mrLine);
					itemIds = new Vector();
					lastMrLine = mrLine.getMrLine();
					lastItemId = "";
					itemId = null;
					receiptId = null;
					mrLineSize = 0;
				}
				if (! flatBean.getItemId().toString().equalsIgnoreCase(lastItemId)) {
					// new item id
					if (itemId != null) {
						itemId.setReceipts((Vector)receiptIds.clone());
						itemId.setReceiptRowspan(receiptIds.size());
						itemIds.add(itemId.clone());
						mrLineSize += receiptIds.size();
					}
					itemId = new PicklistViewBean();
					copyItemIdData(flatBean,itemId);
					receiptIds = new Vector();
					lastItemId = itemId.getItemId().toString();
					receiptId = null;
				}
				receiptId = new PicklistViewBean();
				copyReceiptIdData(flatBean,receiptId);
				receiptIds.add(receiptId.clone());
			}
			if (mrLine != null) {
				itemId.setReceipts((Vector)receiptIds.clone());
				itemId.setReceiptRowspan(receiptIds.size());
				itemIds.add(itemId.clone());
				mrLine.setItems((Vector)itemIds.clone());
				mrLine.setItemRowspan(mrLineSize+receiptIds.size());
				mrLines.add(mrLine.clone());
			}
		} catch (Exception e) {
			log.error("Exception in getNormalizedPicklistCollection: " + e);
		}
		return mrLines;
	}
	//int count = 0;
	private void copyMrLineData(PicklistViewBean source, PicklistViewBean dest) {
		dest.setMrLine(source.getMrLine());
		dest.setPicklistId(source.getPicklistId());
		dest.setFacilityId(source.getFacilityId());
		dest.setFacilityName(source.getFacilityName());
		dest.setApplication(source.getApplication());
		dest.setShipToLocationDesc(source.getShipToLocationDesc());
		dest.setShipToLocationId(source.getShipToLocationId());
		dest.setRequestor(source.getRequestor());
		dest.setMrNotes(source.getMrNotes());
		dest.setCatPartNo(source.getCatPartNo());
		dest.setStockingMethod(source.getStockingMethod());
		dest.setDeliveryType(source.getDeliveryType());
		dest.setCabinetReplenishment(source.getCabinetReplenishment());
		dest.setCritical(source.getCritical());
		dest.setHazmatIdMissing(source.getHazmatIdMissing());
		dest.setCompanyId(source.getCompanyId());
		dest.setNeedDate(source.getNeedDate());
		dest.setHub(source.getHub());
		dest.setPrNumber(source.getPrNumber());
		dest.setLineItem(source.getLineItem());
		dest.setInventoryGroup(source.getInventoryGroup());	
		dest.setDeliveryPoint(source.getDeliveryPoint());
		dest.setApplicationDesc(source.getApplicationDesc());
		dest.setIssueId(source.getIssueId());
		/*if(count % 2 == 0)
		{
			dest.setDeliveryPoint("EQUAL");
			dest.setApplicationDesc("NOT EQUAL");
		}
		else
		{
			dest.setDeliveryPoint("EQUAL");
			dest.setApplicationDesc("EQUAL");
		}
		count++;*/
		/*dest.setPartDescription(source.getPartDescription());
		dest.setPackaging(source.getPackaging());
		dest.setBin(source.getBin());
		dest.setReceiptId(source.getReceiptId());
		dest.setMfgLot(source.getMfgLot());
		dest.setMrLine(source.getMrLine());
		dest.setPicklistQuantity(source.getPicklistQuantity());
		dest.setQuantityPicked(source.getQuantityPicked());
		dest.setItemId(source.getItemId());
		dest.setExpireDate(source.getExpireDate());*/
	}

	private void copyReceiptIdData(PicklistViewBean source, PicklistViewBean dest) {
		dest.setReceiptId(source.getReceiptId());
		dest.setBin(source.getBin());
		dest.setExpireDate(source.getExpireDate());
		dest.setInventoryGroup(source.getInventoryGroup());
		dest.setLotStatus(source.getLotStatus());
		dest.setMfgLot(source.getMfgLot());
		dest.setNonintegerReceiving(source.getNonintegerReceiving());
		dest.setPackaging(source.getPackaging());
		dest.setPicklistQuantity(source.getPicklistQuantity());
		dest.setQuantityPicked(source.getQuantityPicked());
		dest.setReceiptDocumentAvailable(source.getReceiptDocumentAvailable());
		dest.setPickable(source.getPickable());
		dest.setPicklistId(source.getPicklistId());
		dest.setMrCompletePickable(source.getMrCompletePickable());
		dest.setMaterialRequestOrigin(source.getMaterialRequestOrigin());
		dest.setQcDate(source.getQcDate());
		dest.setCustomerPartNo(source.getCustomerPartNo());
		dest.setReportingEntityName(source.getReportingEntityName());
		//dest.setApplicationDesc(source.getApplicationDesc());
		dest.setOwnerSegmentId(source.getOwnerSegmentId());
		dest.setAllocateByChargeNumber1(source.getAllocateByChargeNumber1());
		dest.setAllocateByChargeNumber2(source.getAllocateByChargeNumber2());
		dest.setAllocateByChargeNumber3(source.getAllocateByChargeNumber3());
		dest.setAllocateByChargeNumber4(source.getAllocateByChargeNumber4());
		dest.setReceiptSpecNameList(source.getReceiptSpecNameList());
		dest.setReceiptSpecVersion(source.getReceiptSpecVersion());
		dest.setReleaseNumber(source.getReleaseNumber());
	}

	private void copyItemIdData(PicklistViewBean source, PicklistViewBean dest) {
		dest.setItemId(source.getItemId());
		dest.setPartDescription(source.getPartDescription());
		dest.setApplication(source.getApplication());
		dest.setPickable(source.getPickable());
	}

	public ExcelHandler  createExcelFile(Collection bean, Locale locale)

	throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PicklistViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.picklistid","label.facility","label.workarea","label.deliverypoint","label.shipto",
				"label.requestor","label.mrline","label.closemr", "label.mrnotes", "label.partnumber",
				"label.type","label.item","label.partdescription","label.packaging","label.bin",
				"label.receiptid","label.mfglot","label.expdate", "pickingqc.pickqty","pickingqc.qtypicked"};
		/*This array defines the type of the excel column.
       0 means default depending on the data type. */
		int[] types = {
				0,0,0,0,0,
				0,0,0,0,0,
				0,0,pw.TYPE_PARAGRAPH,0,0,
				0,0,pw.TYPE_CALENDAR,0,0};
		/*This array defines the default width of the column when the Excel file

   opens.
       0 means the width will be default depending on the data type.*/
		int[] widths = {
				0,0,0,0,0,
				20,0,0,0,12,
				0,0,0,30,0,
				0,0,0,0,0};
		/*This array defines the horizontal alignment of the data in a cell.
       0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				0,0,0,0,0,
				0,0,0,0,0,
				0,0,0,0,0,
				0,0,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		int i = 1;
		for (PicklistViewBean member : data) {
			i++;
			pw.addRow();

			pw.addCell(member.getPicklistId());
			pw.addCell(member.getFacilityName());
			pw.addCell(member.getApplication());
			pw.addCell(member.getDeliveryPoint());
			pw.addCell(member.getShipToLocationDesc());
			pw.addCell(member.getRequestor());
			pw.addCell(member.getMrLine());
			pw.addCell(member.getCloseMr());
			pw.addCell(member.getMrNotes());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getDeliveryType());
			pw.addCell(member.getItemId());
			pw.addCell(member.getPartDescription());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getBin());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getMfgLot());
			pw.addCell(member.getExpireDate());
			pw.addCell(member.getPicklistQuantity());
			pw.addCell(member.getQuantityPicked());
		}
		return pw;
	}
	
	public Collection populatePickList(String hub) throws BaseException
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new PickingInputBean());
		SearchCriteria searchCriteria = new SearchCriteria();

		searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, hub);
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("picklistId","desc");
		return factory.select(searchCriteria, sortCriteria, "open_picklist_id_view");
	}

   public Collection getFacilities(PersonnelBean personnelBean,HashMap warehouseInfo) throws BaseException
   {

	 Vector<HubInventoryGroupOvBean> v = (Vector)personnelBean.getHubInventoryGroupOvBeanCollection();
	 Vector<PickingInputBean> beans = new Vector<PickingInputBean>();
	 for(HubInventoryGroupOvBean o: v) 
	 { 
		 Map facilityInfo = (Map)warehouseInfo.get(o.getBranchPlant());
		 if(facilityInfo != null)
			 {
				 sorthashbyValue(facilityInfo.entrySet(), o.getHubName(),beans);
			 }
	 }

	 return beans;
   }
   
	public Collection sorthashbyValue(Collection colla, String hub_branch_id, Vector<PickingInputBean> beans) {
		ArrayList as = new ArrayList(colla);
		as = sortstringcollec(as);

		String hub_selected = "";
		Iterator i1 = as.iterator();
		while (i1.hasNext()) {
			Map.Entry e1 = (Map.Entry) i1.next();
			String keyvalue = (String) e1.getValue();
			String strkey = (String) e1.getKey();

			hub_selected = "";
			if (strkey.equalsIgnoreCase(hub_branch_id)) {
				hub_selected = "selected";
			}
			PickingInputBean bean = new PickingInputBean();
			bean.setHubName(hub_branch_id);
			bean.setFacilityName(keyvalue);
			bean.setFacilityId(strkey);
			beans.add(bean);
		}

		return beans;
	}

	public static ArrayList sortstringcollec(ArrayList ascoll) {
		Collections.sort(ascoll, new Comparator() {
			public int compare(Object o1, Object o2) {
				Map.Entry e1 = (Map.Entry) o1;
				Map.Entry e2 = (Map.Entry) o2;
				String first = (String) e1.getValue();
				String second = (String) e2.getValue();
				if (first == null) {
					first = "";
				}
				if (second == null) {
					second = "";
				}
				return first.compareTo(second);
			}
		});
		return ascoll;
	}
	
   public HashMap setWarehouseInfo(PersonnelBean personnelBean) throws BaseException
   {
		 DbManager dbManager = new DbManager(getClient(),getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager, new PickingInputBean());
		 String query = "SELECT distinct igd.hub branch_plant,x.facility_id,fx_facility_name(x.facility_id, x.company_id) facility_name, ";
			query += "x.company_id FROM facility_inventory_group x,inventory_group_definition igd WHERE "
				+ " x.inventory_group IN( SELECT inventory_group FROM user_inventory_group WHERE  personnel_id = "+ personnelBean.getPersonnelId()+" AND company_id = '"+ personnelBean.getCompanyId() +"') AND "
				+ "x.company_id <> 'Radian' AND status = 'ACTIVE' AND x.inventory_group = igd.inventory_group";
		Vector<PickingInputBean> hubList =(Vector<PickingInputBean>)factory.selectQuery(query);
		HashMap warehouseInfo = new HashMap();
		String warehouse = null;
		Map facilityInfo = null;
		for (PickingInputBean b : hubList) {
			String pwh = b.getBranchPlant();
			if (warehouse == null || !warehouse.equals(pwh)) {
				warehouse = b.getBranchPlant();
				warehouseInfo.put(warehouse, facilityInfo = new HashMap());
			}
			String fid = b.getFacilityId();
			String fname = b.getFacilityName();
			facilityInfo.put(fid, fname);
		}
	 return warehouseInfo;   
   }



}
