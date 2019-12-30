package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.PickViewBean;
import com.tcmis.internal.hub.beans.PickingInputBean;
import com.tcmis.internal.hub.factory.IssueBeanFactory;
import com.tcmis.internal.hub.factory.IssueHistoryViewBeanFactory;
import com.tcmis.internal.hub.factory.PickViewBeanFactory;

/******************************************************************************
 * Process for picking qc
 * @version 1.0
 *****************************************************************************/

public class NoPicklistPickingProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public NoPicklistPickingProcess(String client) {
		super(client);
	}

	public NoPicklistPickingProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getNormalizedPickCollection(PickingInputBean bean) throws
	BaseException {
		return getNormalizedPickCollection(getPicks(bean));
	}

	public Collection getIssueHistory(BigDecimal batchNumber) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		Collection issueHistoryViewBeanColl = new Vector();
		IssueHistoryViewBeanFactory factory = new IssueHistoryViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (batchNumber != null) {
			criteria.addCriterion("batch", SearchCriterion.EQUALS,
					batchNumber + "");

			SortCriteria sortCriteria = new SortCriteria();
			sortCriteria.addCriterion("mrLine");

			issueHistoryViewBeanColl = factory.select(criteria, sortCriteria);
		}
		return issueHistoryViewBeanColl;
	}

	public Collection getPicks(PickingInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PickViewBeanFactory factory = new PickViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (bean.getHub() != null && bean.getHub().length() > 0) {
			criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
		}
		if (bean.getInventoryGroup() != null && bean.getInventoryGroup().length() > 0) {
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
					bean.getInventoryGroup());
		}
		if (bean.getFacilityId() != null && bean.getFacilityId().length() > 0) {
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS,
					bean.getFacilityId());
		}
		if (bean.getPrNumber() != null && bean.getPrNumber().length() > 0) {
			criteria.addCriterion("prNumber", SearchCriterion.EQUALS,
					bean.getPrNumber() + "");
		}

		SortCriteria sortCriteria = null;
		if (bean.getSortBy() != null) {
			sortCriteria = new SortCriteria();
			sortCriteria.addCriterion(bean.getSortBy());
		}

		return factory.select(criteria, sortCriteria);
	}

	public HashMap confirmPicks(Collection inputBeans,
			BigDecimal personnelId) throws BaseException {

		Collection pickedMrsCollection = new Vector();
		Collection errorCodes = new Vector();
		Vector pickedMrList = new Vector();

		DbManager dbManager = new DbManager(getClient(),getLocale());
		IssueBeanFactory issueFactory = new IssueBeanFactory(dbManager);
		BigDecimal batchNumber = issueFactory.selectBatchNumber();

		Iterator mainIterator = inputBeans.iterator();
		while (mainIterator.hasNext()) {
			PickViewBean currentPickViewBean = (PickViewBean)mainIterator.next();

			Collection pickingItemBeanCollection = currentPickViewBean.getItems();
			Iterator itemIterator = pickingItemBeanCollection.iterator();

			int pickedCount = 0;
			while (itemIterator.hasNext()) {
				PickViewBean itemPickViewBean = (PickViewBean) itemIterator.next();
				Collection pickingReceiptBeanCollection = itemPickViewBean.getReceipts();
				Iterator receiptIterator = pickingReceiptBeanCollection.iterator();

				while (receiptIterator.hasNext()) {
					PickViewBean receiptPickViewBean = (PickViewBean) receiptIterator.next();

					//log.debug("currentMrLine: " + currentPickViewBean.getMrLine() + "pickQty: " + receiptPickViewBean.getPickQty() + " QuantityPicked: " + receiptPickViewBean.getQuantityPicked() + "");
					if (receiptPickViewBean.getQuantityPicked() != null &&
							receiptPickViewBean.getQuantityPicked().intValue() >= 0) {

						pickedCount++;
						Collection result = null;
						result = issueInsert(receiptPickViewBean,personnelId,batchNumber);

						if (result != null) {
							Iterator it = result.iterator();
							if (it.hasNext()) {
								Object o = it.next();
								if (o != null) {
									BigDecimal issueId = (BigDecimal) o;
									if (log.isDebugEnabled()) {
										log.debug("MR "+receiptPickViewBean.getMrLine()+" issueId for receipt: " + receiptPickViewBean.getReceiptId() + ": " + o.toString());
									}
									if (issueId.intValue() != -1) {
										if (!pickedMrList.contains(receiptPickViewBean.getMrLine())) {
											PickViewBean lineItemAllocateInfo = new PickViewBean();
											lineItemAllocateInfo.setPrNumber(receiptPickViewBean.getPrNumber());
											lineItemAllocateInfo.setLineItem(receiptPickViewBean.getLineItem());
											lineItemAllocateInfo.setBatchNumber(batchNumber);
											pickedMrsCollection.add(lineItemAllocateInfo);

											pickedMrList.add(receiptPickViewBean.getMrLine());
										}
									}
								}
								if (it.hasNext()) {
									o = it.next();
									if (o != null) {
										errorCodes.add(""+receiptPickViewBean.getMrLine()+": "+o.toString()+"\n");
										if (log.isDebugEnabled()) {
											log.debug("error for " + receiptPickViewBean.getReceiptId() + ": " + o.toString());
										}
									}
								}
							}
						}

					}
				}
			}
			//Call line item Allocate
			if (	 pickedCount > 0 )
			{
				lineItemAllocate(currentPickViewBean);
			}
		}

		HashMap result = new HashMap();
		result.put("errorCodes", errorCodes);
		result.put("pickedMrs", pickedMrsCollection);
		return result;
	}

	public Collection issueInsert(PickViewBean pickBean,
			BigDecimal personnelId,BigDecimal batchNumber) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		PickViewBeanFactory factory = new PickViewBeanFactory(dbManager);
		Collection result = null;
		Vector args;
		String message = null;

		if (pickBean.getQuantityPicked() != null &&
				pickBean.getQuantityPicked().intValue() >= 0) {
			args = new Vector(13);
			if (pickBean.getCompanyId() != null)
				args.add(pickBean.getCompanyId());
			else
				args.add("");
			if (pickBean.getPrNumber() != null)
				args.add(pickBean.getPrNumber());
			else
				args.add("");
			if (pickBean.getLineItem() != null)
				args.add(pickBean.getLineItem());
			else
				args.add("");
			if (pickBean.getHub() != null)
				args.add(pickBean.getHub());
			else
				args.add("");
			if (pickBean.getReceiptId() != null)
				args.add(pickBean.getReceiptId());
			else
				args.add("");
			if (pickBean.getItemId() != null)
				args.add(pickBean.getItemId());
			else
				args.add("");
			args.add(new Timestamp( (new Date()).getTime()));
			if (pickBean.getQuantityPicked() != null)
				args.add(pickBean.getQuantityPicked());
			else
				args.add("");
			args.add(new Timestamp( (new Date()).getTime()));
			args.add(batchNumber);
			if (pickBean.getTransferRequestId() != null)
				args.add(pickBean.getTransferRequestId());
			else
				args.add("");
			if (pickBean.getConsignedFlag() != null &&
					pickBean.getConsignedFlag().length() > 0)
				args.add(pickBean.getConsignedFlag());
			else
				args.add("");
			args.add(personnelId);

			message = "calling p_issue_insert(" + pickBean.getCompanyId() + "," +
			pickBean.getPrNumber() + "," + pickBean.getLineItem() + "," +
			pickBean.getHub() + "," + pickBean.getReceiptId() + "," +
			pickBean.getItemId() + ",now," + pickBean.getQuantityPicked().toString() +
			",now," + batchNumber + "," + pickBean.getTransferRequestId() + "," +
			pickBean.getConsignedFlag() + "," + personnelId + ")";
			log.debug(message);

			try {
				result = factory.issueInsert(args);
			}
			catch (BaseException be) {
				log.error("BaseException calling issue_insert(" + pickBean.getPrNumber() +
						"," + pickBean.getLineItem() + "): " + be);

				try {
					MailProcess.sendEmail("deverror@tcmis.com","","webbot@tcmis.com","Error calling p_issue_insert() from NoPicklistPicking page.","There was a database error while calling: \n" + message+"error: " + be.getMessage());
				}
				catch (Exception me) {
					log.error("MessagingException Error sending p_issue_insert failure email.");
				}
			}
		}
		return result;
	}

	private void lineItemAllocate(PickViewBean pickBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PickViewBeanFactory factory = new PickViewBeanFactory(dbManager);
		Vector args = new Vector(3);
		args.add(pickBean.getCompanyId());
		args.add(pickBean.getPrNumber());
		args.add(pickBean.getLineItem());
		try {
			factory.lineItemAllocate(args);
			if (log.isDebugEnabled()) {
				log.debug("called p_line_item_allocate for (companyId,pr,line): "+pickBean.getCompanyId()+"," +
						pickBean.getPrNumber() + "," + pickBean.getLineItem());
			}
		}
		catch (BaseException be2) {
			log.error("BaseException calling p_line_item_allocate("+pickBean.getCompanyId()+"," +
					pickBean.getPrNumber() + "," + pickBean.getLineItem() + "): " +
					be2);
		}
	}

	/*This is to release the page quickly, can be removed after moving to new code*/
	public Vector bolPrintData(Collection pickedMrs) {
		Vector printData = new Vector();
		Vector prnumbers = new Vector();
		Vector linenumber = new Vector();
		String batch = "";
		Hashtable FResult = new Hashtable();

		Iterator mainIterator = pickedMrs.iterator();
		while (mainIterator.hasNext()) {
			PickViewBean pickViewBean = (PickViewBean) mainIterator.next();
			prnumbers.addElement(""+pickViewBean.getPrNumber()+"");
			linenumber.addElement(""+pickViewBean.getLineItem()+"");
			batch = ""+pickViewBean.getBatchNumber()+"";
		}

		FResult.put("mr_number", prnumbers);
		FResult.put("line_number", linenumber);
		FResult.put("batch", batch);
		printData.add(FResult);
		return printData;
	}

	public Collection getNormalizedPickCollection(Collection flatData) throws
	BaseException {
		PickViewBean flatBean = null;
		PickViewBean mrLine = null;
		PickViewBean itemId = null;
		PickViewBean receiptId = null;
		String lastMrLine = "";
		String lastItemId = "";
		Vector mrLines = new Vector();
		Vector itemIds = null;
		Vector receiptIds = null;
		Iterator flatDataIter = flatData.iterator();
		int mrLineSize = 0;

		try {
			while (flatDataIter.hasNext()) {
				flatBean = (PickViewBean) flatDataIter.next();
				if (!flatBean.getMrLine().equalsIgnoreCase(lastMrLine)) {
					// new mr-Line
					if (mrLine != null) {
						itemId.setReceipts( (Vector) receiptIds.clone());
						itemId.setReceiptRowspan(receiptIds.size());
						itemIds.add( itemId.clone());
						mrLine.setItems( (Vector) itemIds.clone());
						mrLine.setItemRowspan(mrLineSize + receiptIds.size());
						mrLines.add( mrLine.clone());
					}
					mrLine = new PickViewBean();
					copyMrLineData(flatBean, mrLine);
					itemIds = new Vector();
					lastMrLine = mrLine.getMrLine();
					lastItemId = "";
					itemId = null;
					receiptId = null;
					mrLineSize = 0;
				}
				if (!flatBean.getItemId().toString().equalsIgnoreCase(lastItemId)) {
					// new item id
					if (itemId != null) {
						itemId.setReceipts( (Vector) receiptIds.clone());
						itemId.setReceiptRowspan(receiptIds.size());
						itemIds.add( itemId.clone());
						mrLineSize += receiptIds.size();
					}
					itemId = new PickViewBean();
					copyItemIdData(flatBean, itemId);
					receiptIds = new Vector();
					lastItemId = itemId.getItemId().toString();
					receiptId = null;
				}
				receiptId = new PickViewBean();
				copyReceiptIdData(flatBean, receiptId);
				receiptIds.add( receiptId.clone());
			}
			if (mrLine != null) {
				itemId.setReceipts( (Vector) receiptIds.clone());
				itemId.setReceiptRowspan(receiptIds.size());
				itemIds.add( itemId.clone());
				mrLine.setItems( (Vector) itemIds.clone());
				mrLine.setItemRowspan(mrLineSize + receiptIds.size());
				mrLines.add( mrLine.clone());
			}
		}
		catch (Exception e) {
			log.error("Exception in getNormalizedPicklistCollection: " + e);
		}
		return mrLines;
	}

	private void copyMrLineData(PickViewBean source, PickViewBean dest) {
		dest.setMrLine(source.getMrLine());
		dest.setFacilityId(source.getFacilityId());
		dest.setApplication(source.getApplication());
		dest.setDeliveryPoint(source.getDeliveryPoint());
		dest.setRequestor(source.getRequestor());
		dest.setCompanyId(source.getCompanyId());
		dest.setHub(source.getHub());
		dest.setPrNumber(source.getPrNumber());
		dest.setLineItem(source.getLineItem());
		dest.setInventoryGroup(source.getInventoryGroup());
		dest.setTotalQty(source.getTotalQty());
	}

	private void copyReceiptIdData(PickViewBean source, PickViewBean dest) {
		dest.setReceiptId(source.getReceiptId());
		dest.setBin(source.getBin());
		dest.setExpireDate(source.getExpireDate());
		dest.setInventoryGroup(source.getInventoryGroup());
		dest.setMfgLot(source.getMfgLot());
		dest.setPackaging(source.getPackaging());
		dest.setPickQty(source.getPickQty());
		dest.setQuantityPicked(source.getQuantityPicked());
		dest.setPickable(source.getPickable());
		dest.setTransferRequestId(source.getTransferRequestId());
		dest.setConsignedFlag(source.getConsignedFlag());
		dest.setCompanyId(source.getCompanyId());
		dest.setHub(source.getHub());
		dest.setPrNumber(source.getPrNumber());
		dest.setLineItem(source.getLineItem());
		dest.setItemId(source.getItemId());
		dest.setMrLine(source.getMrLine());
	}

	private void copyItemIdData(PickViewBean source, PickViewBean dest) {
		dest.setItemId(source.getItemId());
		dest.setPartDescription(source.getPartDescription());
		dest.setCatPartDescription(source.getCatPartDescription());
		dest.setFacPartNo(source.getFacPartNo());
		dest.setPackaging(source.getPackaging());
	}

}