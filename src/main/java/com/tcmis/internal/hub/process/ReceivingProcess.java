package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.IsValidTransferReceiptBean;
import com.tcmis.internal.hub.beans.ReceiptItemPriorBinViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.ReceivingKitBean;
import com.tcmis.internal.hub.beans.ReceivingViewBean;
import com.tcmis.internal.hub.factory.NoBuyOrderPoReceivingViewBeanFactory;
import com.tcmis.internal.hub.factory.ReceivingViewBeanFactory;
/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class ReceivingProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ReceivingProcess(String client) {
		super(client);
	}

	public ReceivingProcess(String client, String locale) {
		super(client,locale);
	}

	public Collection addBinToItemBinCollection(Collection receivingViewBeanCollection,String itemId,String binName)
	{
		Collection finalreceivingViewBeanCollection = new Vector();

		Iterator iterator = receivingViewBeanCollection.iterator();
		while (iterator.hasNext()) {
			ReceivingViewBean currentReceivingViewBean = (ReceivingViewBean) iterator.
			next();
			String currentItem = "" + currentReceivingViewBean.getItemId() + "";

			if (currentItem.equalsIgnoreCase(itemId)) {
				Collection receiptItemPriorBinViewBeanCollection = currentReceivingViewBean.
				getReceiptItemPriorBinViewBeanCollection();

				ReceiptItemPriorBinViewBean receiptItemPriorBinViewBean = new ReceiptItemPriorBinViewBean();
				receiptItemPriorBinViewBean.setBin(binName);

				receiptItemPriorBinViewBeanCollection.add(receiptItemPriorBinViewBean);

				currentReceivingViewBean.setReceiptItemPriorBinViewBeanCollection(
						receiptItemPriorBinViewBeanCollection);
			}
			finalreceivingViewBeanCollection.add(currentReceivingViewBean);
		}
		return finalreceivingViewBeanCollection;
	}

	public Collection flattenRelationship(ReceivingInputBean bean, Collection
			receivingViewBeanInputCollection, Collection
			receivingViewBeanCollection) {
		Collection finalNewBeanCollection = new Vector();

		if (bean.getDuplicateLine().length() == 0 &&
				bean.getDuplicatePkgLine().length() == 0 &&
				bean.getDuplicateKitLine().length() == 0) {

			Iterator finalIterator = receivingViewBeanCollection.iterator();
			while (finalIterator.hasNext()) {
				ReceivingViewBean currentReceivingViewBean = (
						ReceivingViewBean) finalIterator.next(); ;

						Collection receivingKitBeanCollection =
							currentReceivingViewBean.
							getKitCollection();

						Iterator kitIterator = receivingKitBeanCollection.iterator();
						while (kitIterator.hasNext()) {
							ReceivingKitBean receivingKitBean = (ReceivingKitBean)
							kitIterator.next(); ;

							Iterator finalKitIterator = receivingViewBeanInputCollection.iterator();
							while (finalKitIterator.hasNext()) {
								ReceivingViewBean finalBean = (ReceivingViewBean)
								finalKitIterator.next(); ;

								if (receivingKitBean.getRowNumber().equalsIgnoreCase(finalBean.
										getRowNumber())) {
									finalNewBeanCollection.add(finalBean);
									break;
								}
							}
						}
			}
		}
		else {
			finalNewBeanCollection = receivingViewBeanInputCollection;
		}
		return finalNewBeanCollection;
	}

	public HashMap receiveSelected(ReceivingInputBean bean,Collection
			receivingViewBeanCollection,	BigDecimal personnelId)
	{
		Collection receivedReceiptsCollection = new Vector();
		Collection finalReceivedReceiptsCollection = new Vector();
		String receivedReceipts = "";
		String errorMessage = "";

		if (bean.getDuplicateLine().length() == 0 && bean.getDuplicatePkgLine().length() == 0 &&
				bean.getDuplicateKitLine().length() == 0) {
			try {
				if (bean.getCategory().equalsIgnoreCase("chemicals")) {
					receivedReceiptsCollection = receiveSelected(receivingViewBeanCollection,
							personnelId, false);
				}
				else {
					receivedReceiptsCollection = receiveSelected(receivingViewBeanCollection,
							personnelId, true);
				}
			}
			catch (BaseException ex) {
			}

			Iterator mainIterator = receivedReceiptsCollection.iterator();
			while (mainIterator.hasNext()) {
				ReceivingViewBean currentReceivingViewBean = (
						ReceivingViewBean) mainIterator.next(); ;

						Collection receivingKitBeanCollection =
							currentReceivingViewBean.
							getKitCollection();

						String currentOk = currentReceivingViewBean.getOk();
						String currentMvItem = currentReceivingViewBean.getMvItem();
						String currentUpdateStatus = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getUpdateStatus());

						if ( (currentUpdateStatus != null &&
								!"readOnly".equalsIgnoreCase(currentUpdateStatus)) &&
								(currentOk != null && currentOk.length() > 0) ||
								receivingKitBeanCollection.size() > 1) {
							String currentUpdate = currentReceivingViewBean.getUpdateStatus();

							//log.info("Result currentok "+currentOk+" currentUpdate "+currentUpdate+"");
							if ("OK".equalsIgnoreCase(currentUpdate) ||
									receivingKitBeanCollection.size() > 1) {
								Vector receiptKitVector = new Vector();
								Iterator kitIterator = receivingKitBeanCollection.iterator();
								while (kitIterator.hasNext()) {
									ReceivingKitBean receivingKitBean = (ReceivingKitBean)
									kitIterator.next(); ;

									String currentKitUpdate = receivingKitBean.getUpdateStatus();
									String currentKitOk = receivingKitBean.getOk();
									//log.info("Result currentKitOk "+currentKitOk+" currentKitUpdate "+currentKitUpdate+"");
									if ( (currentKitOk != null && currentKitOk.length() > 0) ||
											("Y".equalsIgnoreCase(currentMvItem))) {
										if (currentKitUpdate != null &&
												"OK".equalsIgnoreCase(currentKitUpdate)) {
											BigDecimal receivedReceipt1 = receivingKitBean.
											getReceivedReceipt1();
											BigDecimal receivedReceipt2 = receivingKitBean.
											getReceivedReceipt2();

											if (receivedReceipt1 != null) {
												receivedReceipts += "" + receivedReceipt1 + ",";
											}

											if (receivedReceipt2 != null) {
												if (receivedReceipt2.intValue() != 0) {
													receivedReceipts += receivedReceipt2 + ",";
												}
											}
										}
										else
										{
											if (currentKitUpdate != null &&
													"Error".equalsIgnoreCase(currentKitUpdate)) {
												errorMessage += " " +receivingKitBean.getErrorMessage();

											}
											receiptKitVector.add(receivingKitBean);
										}
									}
									else {
										//log.info("Here adding KitBean to the vector");
										receiptKitVector.add(receivingKitBean);
									}
								}

								if (receiptKitVector.size() > 0) {
									currentReceivingViewBean.setKitCollection( (Vector)
											receiptKitVector.clone());
									currentReceivingViewBean.setRowSpan(new BigDecimal(
											receiptKitVector.size()));

									finalReceivedReceiptsCollection.add(currentReceivingViewBean);
								}
							}
							else {
								if (currentReceivingViewBean.getErrorMessage() != null) {
									errorMessage += " " +
									currentReceivingViewBean.getErrorMessage();
								}
								finalReceivedReceiptsCollection.add(currentReceivingViewBean);
							}
						}
						else {
							finalReceivedReceiptsCollection.add(currentReceivingViewBean);
						}
			}
		}
		else {
			finalReceivedReceiptsCollection = receivingViewBeanCollection;
		}

		if (receivedReceipts.length() > 1) {
			receivedReceipts = receivedReceipts.substring(0,
					(receivedReceipts.length() - 1));

			com.tcmis.common.util.StringHandler.replace(receivedReceipts, ",","%44");
			//log.info("Here receivedReceipts " + receivedReceipts + "");
		}

		HashMap result = new HashMap();
		result.put("ERRORMESSAGE", errorMessage);
		result.put("RECEIVEDRECEIPTS", receivedReceipts);
		result.put("RECEIVEDRECEIPTSCOLLECTION", finalReceivedReceiptsCollection);
		return result;
		//return finalReceivedReceiptsCollection;
	}

	public Collection copyAttributes(ReceivingInputBean bean,Collection
			receivingViewBeanCollection,Vector savedBeanVector) {
		int duplicateKitCount = 0;
		int kitSize = 0;
		Collection receivingViewBeanColl = new Vector();
		Collection duplicateKitColl = new Vector();

		Iterator iterator = receivingViewBeanCollection.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			ReceivingViewBean inputBean = (ReceivingViewBean) iterator.next();
			ReceivingViewBean savedBean = (ReceivingViewBean) savedBeanVector.get(count);

			String updateStatus = com.tcmis.common.util.StringHandler.emptyIfNull(inputBean.getUpdateStatus());
			if ("readOnly".equalsIgnoreCase(updateStatus)) {
				savedBean.setRowNumber(inputBean.getRowNumber());
				savedBean.setUpdateStatus(inputBean.getUpdateStatus());
				receivingViewBeanColl.add(savedBean);
			}
			else {
				BigDecimal radianPo = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(inputBean.getRadianPo());
				BigDecimal lineItem = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(inputBean.getLineItem());

				BigDecimal savedRadianPo = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(
						savedBean.getRadianPo());
				BigDecimal savedLineItem = com.tcmis.common.util.
				NumberHandler.zeroBigDecimalIfNull(savedBean.getLineItem());

				//log.info("Receiving  PO "+inputBean.getRadianPo()+"-"+inputBean.getLineItem()+" Saved PO "+savedRadianPo+"-"+savedLineItem+" Qty "+inputBean.getQuantityReceived()+" Lot "+inputBean.getMfgLot()+" Close "+inputBean.getClosePoLine()+"");
				if ( (radianPo.equals(savedRadianPo)) &&
						(lineItem.equals(savedLineItem))) {

					if (inputBean.getOk() != null) {
						String rowid = inputBean.getOk();
						savedBean.setOk(rowid);
						//log.info("rowid     " + rowid + "");
					}
					else {
						savedBean.setOk(null);
					}

					savedBean.setBin(inputBean.getBin());
					savedBean.setMfgLot(inputBean.getMfgLot());
					savedBean.setSupplierShipDate(inputBean.getSupplierShipDate());
					savedBean.setDateOfReceipt(inputBean.getDateOfReceipt());
					savedBean.setDom(inputBean.getDom());
					savedBean.setDateOfRepackaging(inputBean.getDateOfRepackaging());
					savedBean.setDos(inputBean.getDos());
					savedBean.setExpirationDate(inputBean.getExpirationDate());
					savedBean.setQuantityReceived(inputBean.getQuantityReceived());
					savedBean.setLotStatus(inputBean.getLotStatus());
					savedBean.setReceiptNotes(inputBean.getReceiptNotes());
					savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
					savedBean.setQaStatement(inputBean.getQaStatement());
					savedBean.setPackagedQty(inputBean.getPackagedQty());
					savedBean.setPackagedSize(inputBean.getPackagedSize());
					savedBean.setTransferReceiptId(inputBean.getTransferReceiptId());
					savedBean.setOriginalQty(inputBean.getOriginalQty());
					savedBean.setDuplicatePkgLine(inputBean.getDuplicatePkgLine());
					savedBean.setDuplicateKitLine(inputBean.getDuplicateKitLine());
					savedBean.setRowSpan(inputBean.getRowSpan());
					savedBean.setRowNumber(inputBean.getRowNumber());
					savedBean.setClosePoLine(inputBean.getClosePoLine());
                    savedBean.setCustomerRmaId(inputBean.getCustomerRmaId());
                    savedBean.setOwnerSegmentId(inputBean.getOwnerSegmentId());
                    savedBean.setAccountNumber(inputBean.getAccountNumber());
                    savedBean.setAccountNumber2(inputBean.getAccountNumber2());
                    savedBean.setAccountNumber3(inputBean.getAccountNumber3());
                    savedBean.setAccountNumber4(inputBean.getAccountNumber4());
                    savedBean.setCustomerReceiptId(inputBean.getCustomerReceiptId());
                    savedBean.setQualityTrackingNumber(inputBean.getQualityTrackingNumber());

                    receivingViewBeanColl.add(savedBean);
					try {
						if ( (bean.getDuplicateLine() != null &&
								bean.getDuplicateLine().length() > 0) &&
								bean.getDuplicateLine().equalsIgnoreCase("" + count + "")) {
							ReceivingViewBean duplicateBean = (ReceivingViewBean) savedBean.
							clone();
							duplicateBean.setOk(null);
							duplicateBean.setMfgLot(null);
							duplicateBean.setTransferReceiptId(null);
//							duplicateBean.setOriginalQty(null);
							duplicateBean.setDom(null);
							duplicateBean.setDateOfRepackaging(null);
							duplicateBean.setDos(null);
							duplicateBean.setExpirationDate(null);
							duplicateBean.setQuantityReceived(null);
							duplicateBean.setPackagedQty(null);
							duplicateBean.setPackagedSize(com.tcmis.common.util.NumberHandler.emptyIfNull(
									savedBean.getReceivingPagePackagedSize()));
							duplicateBean.setReceiptNotes(null);
							duplicateBean.setDeliveryTicket(null);
							duplicateBean.setQaStatement(null);
							duplicateBean.setClosePoLine(null);
							receivingViewBeanColl.add(duplicateBean);
						}
						else if ( (bean.getDuplicatePkgLine() != null &&
								bean.getDuplicatePkgLine().length() > 0) &&
								bean.getDuplicatePkgLine().equalsIgnoreCase("" + count + "")) {
							ReceivingViewBean duplicateBean = (ReceivingViewBean) savedBean.clone();
							duplicateBean.setDuplicatePkgLine("");
							duplicateBean.setOk(null);
							duplicateBean.setMfgLot(null);
							duplicateBean.setTransferReceiptId(null);
//							duplicateBean.setOriginalQty(null);
							duplicateBean.setDom(null);
							duplicateBean.setDateOfRepackaging(null);
							duplicateBean.setDos(null);
							duplicateBean.setExpirationDate(null);
							duplicateBean.setQuantityReceived(null);
							duplicateBean.setPackagedQty(null);
							duplicateBean.setPackagedSize(com.tcmis.common.util.NumberHandler.emptyIfNull(
									savedBean.getReceivingPagePackagedSize()));
							duplicateBean.setReceiptNotes(null);
							duplicateBean.setDeliveryTicket(null);
							duplicateBean.setQaStatement(null);
							duplicateBean.setClosePoLine(null);
							receivingViewBeanColl.add(duplicateBean);
						}
						else if ( (bean.getDuplicateKitLine() != null &&
								bean.getDuplicateKitLine().length() > 0)) {
							if (savedBean.getDuplicateKitLine().length() > 0 &&
									bean.getDuplicateKitLine().equalsIgnoreCase("" + count + "")) {
								duplicateKitCount++;
								kitSize = savedBean.getRowSpan().intValue();
								//log.info("duplicateKitCount  "+duplicateKitCount+"  duplicateKitLineRownum  "+kitSize+"");
								ReceivingViewBean duplicateBean = (ReceivingViewBean) savedBean.
								clone();
								duplicateBean.setDuplicateKitLine("");
								duplicateBean.setOk(null);
								duplicateBean.setMfgLot(null);
								duplicateBean.setTransferReceiptId(null);
//								duplicateBean.setOriginalQty(null);
								duplicateBean.setDom(null);
								duplicateBean.setDateOfRepackaging(null);
								duplicateBean.setDos(null);
								duplicateBean.setExpirationDate(null);
								duplicateBean.setQuantityReceived(null);
								duplicateBean.setPackagedQty(null);
								duplicateBean.setPackagedSize(com.tcmis.common.util.NumberHandler.emptyIfNull(
										savedBean.getReceivingPagePackagedSize()));
								duplicateBean.setReceiptNotes(null);
								duplicateBean.setDeliveryTicket(null);
								duplicateBean.setQaStatement(null);
								duplicateBean.setClosePoLine(null);
								duplicateKitColl.add(duplicateBean);
							}
							else if (savedBean.getDuplicateKitLine().length() > 0 &&
									bean.getDuplicateKitLine().equalsIgnoreCase(savedBean.
											getDuplicateKitLine())) {
								duplicateKitCount++;
								//log.info("duplicateKitCount  "+duplicateKitCount+"");
								ReceivingViewBean duplicateBean = (ReceivingViewBean) savedBean.
								clone();
								duplicateBean.setDuplicateKitLine("");
								duplicateBean.setOk(null);
								duplicateBean.setMfgLot(null);
								duplicateBean.setTransferReceiptId(null);
//								duplicateBean.setOriginalQty(null);
								duplicateBean.setDom(null);
								duplicateBean.setDateOfRepackaging(null);
								duplicateBean.setDos(null);
								duplicateBean.setExpirationDate(null);
								duplicateBean.setQuantityReceived(null);
								duplicateBean.setPackagedQty(null);
								duplicateBean.setPackagedSize(null);
								duplicateBean.setReceiptNotes(null);
								duplicateBean.setDeliveryTicket(null);
								duplicateBean.setQaStatement(null);
								duplicateBean.setClosePoLine(null);
								duplicateKitColl.add(duplicateBean);

								if (duplicateKitCount == kitSize) {
									//log.info("adding duplicateBean");
									receivingViewBeanColl.addAll(duplicateKitColl);
									duplicateKitCount = 0;
								}
							}
						}
					}
					catch (CloneNotSupportedException ex) {
					}
				}
			}
			count++;
		}

		if (duplicateKitCount > 0) {
			//log.info("adding duplicateBean outside the loop");
			receivingViewBeanColl.addAll(duplicateKitColl);
			duplicateKitCount = 0;
		}
		return  receivingViewBeanColl;
	}

	public Collection createRelationalObject(Collection
			receivingViewBeanCollection) {
		Collection finalreceivingViewBeanCollection = new Vector();
		String nextPoNumber = "";
		String nextPoLine = "";
		String nextTransferRequestId = "";
		//String manageKitAsSingle = "";
		String nextItem = "";
		String nextDuplicatePkgLine = "";
		String nextDuplicateKitLine = "";
		String nextDocNum = "";
		String nextCustomerRmaId = "";
		String nextOriginalReceiptId = "";
        String nextOriginalMfgLot = "";

        int samePoLineCount = 0;
		Vector collectionVector = new Vector(receivingViewBeanCollection);
		Vector poLineKitVector = new Vector();

		for (int loop = 0; loop < collectionVector.size(); loop++) {

			ReceivingViewBean currentReceivingViewBean = (ReceivingViewBean)
			collectionVector.elementAt(loop);

			String currentPo = "" + currentReceivingViewBean.getRadianPo() + "";
			String currentPOLine = "" + currentReceivingViewBean.getLineItem() + "";
			String currentTransferRequestId = com.tcmis.common.util.StringHandler.
			emptyIfNull(currentReceivingViewBean.getTransferRequestId());
			String currentItem = "" + currentReceivingViewBean.getItemId() + "";
			//manageKitAsSingle = currentReceivingViewBean.getManageKitsAsSingleUnit();
			String currentDuplicatePkgLine = com.tcmis.common.util.StringHandler.
			emptyIfNull(currentReceivingViewBean.getDuplicatePkgLine());
			String currentDuplicateKitLine = com.tcmis.common.util.StringHandler.
			emptyIfNull(currentReceivingViewBean.getDuplicateKitLine());
			String currentDocNum = com.tcmis.common.util.NumberHandler.emptyIfNull(currentReceivingViewBean.getDocNum());
			String currentCustomerRmaId = com.tcmis.common.util.NumberHandler.emptyIfNull(currentReceivingViewBean.getCustomerRmaId());
			String currentOriginalReceiptId = com.tcmis.common.util.NumberHandler.emptyIfNull(currentReceivingViewBean.getOriginalReceiptId());
            String currentOriginalMfgLot = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getOriginalMfgLot());

            if (! ( (loop + 1) == collectionVector.size())) {
				ReceivingViewBean nextReceivingViewBean = (ReceivingViewBean)
				collectionVector.elementAt(loop + 1);

				nextPoNumber = "" + nextReceivingViewBean.getRadianPo() + "";
				nextPoLine = "" + nextReceivingViewBean.getLineItem() + "";
				nextTransferRequestId = com.tcmis.common.util.StringHandler.
				emptyIfNull(nextReceivingViewBean.getTransferRequestId());
				nextItem = "" + nextReceivingViewBean.getItemId() + "";
				nextDuplicatePkgLine = com.tcmis.common.util.StringHandler.emptyIfNull(
						nextReceivingViewBean.getDuplicatePkgLine());
				nextDuplicateKitLine = com.tcmis.common.util.StringHandler.emptyIfNull(
						nextReceivingViewBean.getDuplicateKitLine());
				nextDocNum = com.tcmis.common.util.NumberHandler.emptyIfNull(nextReceivingViewBean.getDocNum());
				nextCustomerRmaId = com.tcmis.common.util.NumberHandler.emptyIfNull(nextReceivingViewBean.getCustomerRmaId());
				nextOriginalReceiptId = com.tcmis.common.util.NumberHandler.emptyIfNull(nextReceivingViewBean.getOriginalReceiptId());
                nextOriginalMfgLot = com.tcmis.common.util.StringHandler.emptyIfNull(nextReceivingViewBean.getOriginalMfgLot());
            }
			else {
				nextPoNumber = "";
				nextPoLine = "";
				nextTransferRequestId = "";
				nextItem = "";
				nextDuplicatePkgLine = "";
				nextDuplicateKitLine = "";
				nextDocNum = "";
				nextCustomerRmaId = "";
				nextOriginalReceiptId = "";
                nextOriginalMfgLot = "";
            }

			boolean samePoLineNumber = false;
			if (currentCustomerRmaId.equalsIgnoreCase(nextCustomerRmaId) && currentDocNum.equalsIgnoreCase(nextDocNum) && currentDuplicateKitLine.equalsIgnoreCase(nextDuplicateKitLine) &&
					currentDuplicatePkgLine.equalsIgnoreCase(nextDuplicatePkgLine) &&
					currentPo.equalsIgnoreCase(nextPoNumber) &&
					nextPoLine.equalsIgnoreCase(currentPOLine) &&
					nextTransferRequestId.equalsIgnoreCase(currentTransferRequestId) &&
					nextItem.equalsIgnoreCase(currentItem) &&
					nextOriginalReceiptId.equalsIgnoreCase(currentOriginalReceiptId) && nextOriginalMfgLot.equalsIgnoreCase(currentOriginalMfgLot)) {
				samePoLineNumber = true;
				samePoLineCount++;
			}

			ReceivingKitBean receivingKitBean = new ReceivingKitBean();
			receivingKitBean.setMaterialDesc(currentReceivingViewBean.getMaterialDesc());
			receivingKitBean.setPackaging(currentReceivingViewBean.getPackaging());
			receivingKitBean.setOk(currentReceivingViewBean.getOk());
			receivingKitBean.setBin(currentReceivingViewBean.getBin());
			receivingKitBean.setOriginalMfgLot(currentReceivingViewBean.
					getOriginalMfgLot());
			receivingKitBean.setMfgLot(currentReceivingViewBean.getMfgLot());
			receivingKitBean.setManageKitsAsSingleUnit(currentReceivingViewBean.
					getManageKitsAsSingleUnit());
			receivingKitBean.setOriginalReceiptId(currentReceivingViewBean.
					getOriginalReceiptId());
			receivingKitBean.setSupplierShipDate(currentReceivingViewBean.
					getSupplierShipDate());
			receivingKitBean.setDateOfReceipt(currentReceivingViewBean.getDateOfReceipt());
			receivingKitBean.setDom(currentReceivingViewBean.getDom());
			receivingKitBean.setDateOfRepackaging(currentReceivingViewBean.getDateOfRepackaging());
			receivingKitBean.setDos(currentReceivingViewBean.getDos());
			receivingKitBean.setExpirationDate(currentReceivingViewBean.
					getExpirationDate());
			receivingKitBean.setPackagedQty(currentReceivingViewBean.getPackagedQty());
			receivingKitBean.setPackagedSize(currentReceivingViewBean.getPackagedSize());
			receivingKitBean.setQuantityReceived(currentReceivingViewBean.
					getQuantityReceived());
			receivingKitBean.setLotStatus(currentReceivingViewBean.getLotStatus());
			receivingKitBean.setSkipReceivingQc(currentReceivingViewBean.
					getSkipReceivingQc());
			receivingKitBean.setReceiptNotes(currentReceivingViewBean.getReceiptNotes());
			receivingKitBean.setDeliveryTicket(currentReceivingViewBean.
					getDeliveryTicket());
			receivingKitBean.setQaStatement(currentReceivingViewBean.
					getQaStatement());
			receivingKitBean.setItemId(currentReceivingViewBean.getItemId());
			receivingKitBean.setComponentId(currentReceivingViewBean.getComponentId());
			receivingKitBean.setTransferReceiptId(currentReceivingViewBean.
					getTransferReceiptId());
			receivingKitBean.setOriginalQty(currentReceivingViewBean.getOriginalQty());
			receivingKitBean.setRowNumber(currentReceivingViewBean.getRowNumber());
			receivingKitBean.setOrderQtyUpdateOnReceipt(currentReceivingViewBean.getOrderQtyUpdateOnReceipt());
			receivingKitBean.setClosePoLine(currentReceivingViewBean.getClosePoLine());
			poLineKitVector.add(receivingKitBean);
			receivingKitBean.setMvItem(currentReceivingViewBean.getMvItem());
			receivingKitBean.setPurchasingUnitsPerItem(currentReceivingViewBean.
					getPurchasingUnitsPerItem());
			receivingKitBean.setPurchasingUnitOfMeasure(currentReceivingViewBean.getPurchasingUnitOfMeasure());
			receivingKitBean.setReceivingPagePackagedSize(currentReceivingViewBean.getReceivingPagePackagedSize());
            receivingKitBean.setIntercompanyPo(currentReceivingViewBean.getIntercompanyPo());
            receivingKitBean.setIntercompanyPoLine(currentReceivingViewBean.getIntercompanyPoLine());
            receivingKitBean.setDefinedShelfLifeItem(currentReceivingViewBean.getDefinedShelfLifeItem());
            receivingKitBean.setDefinedShelfLifeBasis(currentReceivingViewBean.getDefinedShelfLifeBasis());
            if (samePoLineNumber) {

			}
			else {
                currentReceivingViewBean.setKitCollection( (Vector) poLineKitVector.clone());
				poLineKitVector = new Vector();
				currentReceivingViewBean.setRowSpan(new BigDecimal(samePoLineCount + 1));

				finalreceivingViewBeanCollection.add(currentReceivingViewBean);

				samePoLineCount = 0;
			}
		}

		return finalreceivingViewBeanCollection;
	}

	public Collection receiveSelected(Collection receivingViewBeanCollection,
			BigDecimal personnelId, boolean nonChemicalreceipt) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ReceivingViewBeanFactory factory = new ReceivingViewBeanFactory(dbManager);

		Iterator mainIterator = receivingViewBeanCollection.iterator();
		while (mainIterator.hasNext()) {
			ReceivingViewBean currentReceivingViewBean = (ReceivingViewBean)
			mainIterator.next(); ;

			String currentPo = "" + currentReceivingViewBean.getRadianPo() + "";
			String currentPOLine = "" + currentReceivingViewBean.getLineItem() + "";
			String currentItem = "" + currentReceivingViewBean.getItemId() + "";
			String currentOk = currentReceivingViewBean.getOk();
			String currentMvItem = currentReceivingViewBean.getMvItem();
			String currentmanageKitAsSingle = currentReceivingViewBean.
			getManageKitsAsSingleUnit();
			String currentUpdateStatus = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getUpdateStatus());
			Collection receivingKitBeanCollection = currentReceivingViewBean.
			getKitCollection();

			if ( (currentUpdateStatus != null &&
					!"readOnly".equalsIgnoreCase(currentUpdateStatus)) && ( (currentOk != null &&
							currentOk.length() > 0) ||
							receivingKitBeanCollection.size() > 1)) {

				Iterator kitIterator = receivingKitBeanCollection.iterator();
				int kitCount = 0;
				Collection result = null;
				boolean receiptSuccess = true;
				boolean receiptKitSuccess = true;
				while (kitIterator.hasNext()) {
					ReceivingKitBean receivingKitBean = (ReceivingKitBean) kitIterator.next(); ;

					BigDecimal receivedReceipt1 = currentReceivingViewBean.getReceivedReceipt1();
					BigDecimal receivedReceipt2 = currentReceivingViewBean.getReceivedReceipt2();
					String currentKitOk = receivingKitBean.getOk();
					if (currentKitOk == null) {
						currentKitOk = "";
					}
					//log.info("currentOk  "+currentOk+"  currentKitOk "+currentKitOk+"");
					if ( (kitCount == 0 && (currentOk != null && currentOk.length() > 0)) ||
							(! ("N".equalsIgnoreCase(currentmanageKitAsSingle)) && (currentKitOk != null &&
									currentKitOk.length() > 0)) || "Y".equalsIgnoreCase(currentMvItem) ) {

						try {
							if (nonChemicalreceipt) {
								NoBuyOrderPoReceivingViewBeanFactory
								noBuyOrderPoReceivingViewBeanFactory = new
								NoBuyOrderPoReceivingViewBeanFactory(dbManager);
								if (currentReceivingViewBean.getLineItem() != null && !currentReceivingViewBean.getLineItem().equals("")) 
									currentReceivingViewBean.setReceivePoLine(currentReceivingViewBean.getLineItem().toString());
								else 
									currentReceivingViewBean.setReceivePoLine(
										noBuyOrderPoReceivingViewBeanFactory.selectPoLineToReceive(currentPo,
												currentReceivingViewBean.getCatPartNo(),
												currentReceivingViewBean.getDateOfReceipt(),currentReceivingViewBean.getInventoryGroup()));
							}

							boolean validQuantityReceived = false;
							boolean closePoLine = false;
							BigDecimal quantityReceived = currentReceivingViewBean.
							getQuantityReceived();
							BigDecimal kitQuantityReceived = receivingKitBean.getQuantityReceived();
							if ( ("N".equalsIgnoreCase(currentReceivingViewBean.
									getManageKitsAsSingleUnit()))) {
								if (quantityReceived != null && quantityReceived.floatValue() > 0) {
									validQuantityReceived = true;
								}
								else if (quantityReceived != null && quantityReceived.floatValue() == 0 &&
										currentReceivingViewBean.getClosePoLine() != null &&
										currentReceivingViewBean.getClosePoLine().trim().length() > 0) {
									closePoLine = true;
								}
							}
							else if ("Y".equalsIgnoreCase(currentReceivingViewBean.getMvItem()) && receivingKitBean.getPackagedQty() !=null) {
								BigDecimal packagedQuantityReceived = new BigDecimal(receivingKitBean.getPackagedQty());

								if (packagedQuantityReceived != null && packagedQuantityReceived.floatValue() > 0) {
									validQuantityReceived = true;
									BigDecimal packagedQuantitySize = new BigDecimal(receivingKitBean.getPackagedSize());
									BigDecimal purchasingUnitsPerItem = receivingKitBean.getPurchasingUnitsPerItem();
									if (packagedQuantitySize.subtract(purchasingUnitsPerItem).divide(
											purchasingUnitsPerItem, 3,
											BigDecimal.ROUND_HALF_UP).abs().compareTo(new BigDecimal("0.25")) > 0) {
										BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
										bulkMailProcess.sendBulkEmail(new BigDecimal("19143"),
												"MV item Packaged Size received is 25% off for Purchase Order : " +
												currentPo + "  Line: " + currentPOLine + "","MV item Packaged Size received is 25% off for Purchase Order : " +
												currentPo + "  Line: " + currentPOLine + "\n\nIt is 25% off of the defined value (" + purchasingUnitsPerItem +
												" " + receivingKitBean.getPurchasingUnitOfMeasure() + ").Received size - "+packagedQuantitySize+
												" "+receivingKitBean.getPurchasingUnitOfMeasure()+".\n\nReceived By personnel Id- "+personnelId+"", false);
									}
								}
								else if (packagedQuantityReceived != null &&
										packagedQuantityReceived.floatValue() == 0 &&
										receivingKitBean.getClosePoLine() != null &&
										receivingKitBean.getClosePoLine().trim().length() > 0) {
									closePoLine = true;
								}
							}
							else {
								if (kitQuantityReceived != null && kitQuantityReceived.floatValue() > 0) {
									validQuantityReceived = true;
								}
								else if (kitQuantityReceived != null &&
										kitQuantityReceived.floatValue() == 0 &&
										receivingKitBean.getClosePoLine() != null &&
										receivingKitBean.getClosePoLine().trim().length() > 0) {
									closePoLine = true;
								}
							}

							if (validQuantityReceived && ( (currentKitOk != null &&
									currentKitOk.length() > 0) ||
									("Y".equalsIgnoreCase(currentMvItem) && (currentOk != null &&
											currentOk.length() > 0)))) {
								//log.info("Calling p_receipt_insert   currentKitOk " + currentKitOk + "");
								//Call p_receipt_insert to insert main receipt
								log.info("Calling p_receipt_insert PO " + currentPo + " Line " + currentPOLine +
										" Qty " + receivingKitBean.getQuantityReceived() + " Lot " +
										currentReceivingViewBean.getMfgLot() + "  Bin " +
										currentReceivingViewBean.getBin() + " Current Line "+currentOk+"-"+currentKitOk+"");

								result = factory.insertReceipt(currentReceivingViewBean,
										receivingKitBean, personnelId, nonChemicalreceipt);
							}
							else if (closePoLine)
							{
								log.info("Calling p_order_qty_update_from_rcpt   radian PO " + currentReceivingViewBean.getRadianPo() + " Line "+currentReceivingViewBean.getLineItem()+"");
								Collection closePoLineresult = null;
								closePoLineresult = factory.closePoLine(currentReceivingViewBean,personnelId);

								Iterator closePoLineresultterator = closePoLineresult.iterator();
								int closePoLineresultCount = 0;
								//boolean closePoLineSuccess = true;
								String closePoLineerrorCode = "";
								while (closePoLineresultterator.hasNext()) {
									if (closePoLineresultCount == 0) {
										closePoLineerrorCode = (String) closePoLineresultterator.next(); ;
										if (closePoLineerrorCode == null) {
											closePoLineerrorCode = "";
										}
										if (closePoLineerrorCode.length() > 0) {
											log.info("error from p_order_qty_update_from_rcpt "+closePoLineerrorCode+"");
											receiptSuccess = false;
										}
									}
									closePoLineresultCount++;
								}

								if (receiptSuccess) {
									currentReceivingViewBean.setUpdateStatus("OK");
								}
								else {
									currentReceivingViewBean.setUpdateStatus("Error");
									currentReceivingViewBean.setErrorMessage(closePoLineerrorCode);
								}
							}

							//if (kitCount > 0)
							{
								receivingKitBean.setUpdateStatus("OK");
							}
						}
						catch (Exception ex) {
							ex.printStackTrace();
							currentReceivingViewBean.setErrorMessage(ex.getMessage());
							currentReceivingViewBean.setUpdateStatus("Error");
							receiptSuccess = false;
							if (kitCount > 0) {
								receivingKitBean.setErrorMessage(ex.getMessage());
								receivingKitBean.setUpdateStatus("Error");
								receiptKitSuccess = false;
							}
						}

						if (result != null) {
							Iterator resultIterator = result.iterator();
							int resultCount = 0;
							while (resultIterator.hasNext()) {
								if (resultCount == 0) {
									receivedReceipt1 = (BigDecimal) resultIterator.next(); ;
									if ("Y".equalsIgnoreCase(currentMvItem) && kitCount == 0)
									{
										currentReceivingViewBean.setReceivedReceipt1(receivedReceipt1);
									}
									else if (!"Y".equalsIgnoreCase(currentMvItem))
									{
										currentReceivingViewBean.setReceivedReceipt1(receivedReceipt1);
									}
									receivingKitBean.setReceivedReceipt1(receivedReceipt1);
								}
								else if (resultCount == 1) {
									receivedReceipt2 = (BigDecimal) resultIterator.next(); ;
									currentReceivingViewBean.setReceivedReceipt2(receivedReceipt2);
									receivingKitBean.setReceivedReceipt2(receivedReceipt2);
								}
								else if (resultCount == 2) {
									String errorCode = (String) resultIterator.next(); ;
									currentReceivingViewBean.setErrorMessage(errorCode);
								}
								resultCount++;
							}

							if (receivedReceipt1 == null || receivedReceipt1.intValue() == -1) {
								receiptSuccess = false;
							}

							if (!receiptSuccess)
							{
								log.info("Result from p_receipt_insert PO " + currentPo + " Line " + currentPOLine +
										" Receipt 1:" + currentReceivingViewBean.getReceivedReceipt1() + " Receipt 2:" +
										currentReceivingViewBean.getReceivedReceipt2() + "  Error Code: " +
										currentReceivingViewBean.getErrorMessage() +"");
							}
						}
					}

					if (receivedReceipt1 != null &&
							("N".equalsIgnoreCase(currentmanageKitAsSingle) && (currentOk != null &&
									currentOk.length() > 0))) {
						//Call p_receipt_component
						if (receiptSuccess) {
							if (log.isDebugEnabled()) {
								log.debug("Calling p_receipt_component for COMPONENT_ID " +
										currentReceivingViewBean.getComponentId() + " RECEIPT_ID1 " +
										currentReceivingViewBean.getReceivedReceipt1() + " Personnel ID  " +
										personnelId + "");
							}
							try {
								receiptSuccess = factory.insertReceiptComponenet(receivingKitBean,
										currentReceivingViewBean.getReceivedReceipt1(), personnelId);
								receivingKitBean.setUpdateStatus("OK");
							}
							catch (Exception ex1) {
								receiptSuccess = false;
								receivingKitBean.setErrorMessage(
										"Error Calling p_receipt_component for Componenet " +
										currentReceivingViewBean.getComponentId() + " Receipt ID: " +
										currentReceivingViewBean.getReceivedReceipt1() + ". ");
								receivingKitBean.setUpdateStatus("Error");
								receiptKitSuccess = false;
							}
							if (currentReceivingViewBean.getReceivedReceipt2().intValue() != 0) {
								if (log.isDebugEnabled()) {
									log.debug("Calling p_receipt_component for COMPONENT_ID " +
											currentReceivingViewBean.getComponentId() + " RECEIPT_ID2 " +
											currentReceivingViewBean.getReceivedReceipt2() + " Personnel ID  " +
											personnelId + "");
								}
								try {
									receiptSuccess = factory.insertReceiptComponenet(receivingKitBean,
											currentReceivingViewBean.getReceivedReceipt2(), personnelId);
									receivingKitBean.setUpdateStatus("OK");
								}
								catch (Exception ex2) {
									receivingKitBean.setErrorMessage(
											"Error Calling p_receipt_component for Componenet " +
											currentReceivingViewBean.getComponentId() + " Receipt ID: " +
											currentReceivingViewBean.getReceivedReceipt2() + ". ");
									receivingKitBean.setUpdateStatus("Error");
									receiptKitSuccess = false;
									receiptSuccess = false;
								}
							}
						}
					}
					/*else if (receivedReceipt1 != null && ("Y".equalsIgnoreCase(currentMvItem) &&
			(currentOk != null && currentOk.length() > 0))) {
			//Call p_receipt_insert_mv
			if (receiptSuccess) {
			 log.info("Calling p_receipt_insert_mv for  MV Item " +
				currentReceivingViewBean.getItemId() + " RECEIPT_ID1 " +
				currentReceivingViewBean.getReceivedReceipt1() + " Personnel ID  " +
				personnelId + ". ");
			 try {
				receiptSuccess = factory.insertReceiptMv(receivingKitBean,
				 currentReceivingViewBean.getReceivedReceipt1(), personnelId);
				receivingKitBean.setUpdateStatus("OK");
			 }
			 catch (Exception ex1) {
				receivingKitBean.setErrorMessage(
				 "Error Calling p_receipt_insert_mv for  MV Item " +
				 currentReceivingViewBean.getItemId() + " Receipt ID " +
				 currentReceivingViewBean.getReceivedReceipt1() + ". ");
				receivingKitBean.setUpdateStatus("Error");
				receiptKitSuccess = false;
				receiptSuccess = false;
			 }
			 if (currentReceivingViewBean.getReceivedReceipt2().intValue() != 0) {
				log.info("Calling p_receipt_insert_mv for  MV Item " +
				 currentReceivingViewBean.getItemId() + " RECEIPT_ID2 " +
				 currentReceivingViewBean.getReceivedReceipt2() + " Personnel ID  " +
				 personnelId + ". ");
				try {
				 receiptSuccess = factory.insertReceiptMv(receivingKitBean,
					currentReceivingViewBean.getReceivedReceipt2(), personnelId);
				 receivingKitBean.setUpdateStatus("OK");
				}
				catch (Exception ex2) {
				 receivingKitBean.setErrorMessage(
					"Error Calling p_receipt_insert_mv for  MV Item " +
					currentReceivingViewBean.getItemId() + " Receipt ID " +
					currentReceivingViewBean.getReceivedReceipt2() + ". ");
				 receivingKitBean.setUpdateStatus("Error");
				 receiptKitSuccess = false;
				 receiptSuccess = false;
				}
			 }
			}
		 }*/

					//log.info("Here receiptSuccess " + receiptSuccess + " receiptKitSuccess " +	receiptKitSuccess + "");
					if (receiptSuccess) {
						currentReceivingViewBean.setUpdateStatus("OK");
					}
					else {
						currentReceivingViewBean.setUpdateStatus("Error");
					}
					kitCount++;
				}
			}
		}

		return receivingViewBeanCollection;
	}
	
	public Collection isValidTransferRecceipt(String originalReceiptId, String transferReceiptId) throws BaseException {
		Vector results = new Vector(1);
		
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new IsValidTransferReceiptBean());
		
		StringBuilder query = new StringBuilder("select * from table(pkg_inventory_transfer.fx_is_valid_transfer_receipt('");
		query.append(StringHandler.emptyIfNull(originalReceiptId)).append("','");
		query.append(StringHandler.emptyIfNull(transferReceiptId)).append("'))");
		
		if (log.isDebugEnabled()) {
			log.debug(query);
		}
		
		return factory.selectQuery(query.toString());
	}

	public Collection getChemicalResult(ReceivingInputBean bean,
			boolean hasUpdatePermission, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ReceivingViewBeanFactory factory = new ReceivingViewBeanFactory(dbManager);
		String sortBy = null;
		SearchCriteria criteria = new SearchCriteria();
		//		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS,
		//				bean.getSourceHub());

		//log.info("hasUpdatePermission    " + hasUpdatePermission + "");
		HubInventoryGroupProcess hubInventoryGroupProcess = new
		HubInventoryGroupProcess(this.getClient());
		boolean iscollection = hubInventoryGroupProcess.isCollection(personnelBean.getHubInventoryGroupOvBeanCollection(),
				bean.getSourceHub(), bean.getInventoryGroup());

		if(StringHandler.isBlankString(bean.getInventoryGroup())) {
			//			if(!StringHandler.isBlankString(bean.getOpsEntityId()))
			//				criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId());
			if(!StringHandler.isBlankString(bean.getSourceHub()))
				criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getSourceHub());
		}
		if(!StringHandler.isBlankString(bean.getInventoryGroup()))
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
			if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
				invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' " + " and ops_entity_id = '" + bean.getOpsEntityId() +"' " ;
            if(!StringHandler.isBlankString(bean.getSourceHub()))
                invQuery +=  " and hub =  " +bean.getSourceHub();
            criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}



		/*
		if (bean.getInventoryGroup() != null &&
				!bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

			if (iscollection) {

			}
			else if (bean.getInventoryGroup().length() > 0) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
						bean.getInventoryGroup());
			}
		}
		 */
		if (bean.getDropship() !=null && "Y".equalsIgnoreCase(bean.getDropship()) ) {
			criteria.addCriterion("dropship", SearchCriterion.EQUALS,
					bean.getDropship());
		}

		//add description search if it's not null
		String searchWhat = bean.getSearchWhat();
		if (bean.getSearchWhat() != null && bean.getSearch() != null &&
				bean.getSearch().trim().length() > 0) {
			//check search criterion
			String criterion = null;
			if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("IS")) {
				criterion = SearchCriterion.EQUALS;
			}
			else if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
				criterion = SearchCriterion.LIKE;
			}
			else if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("STARTSWITH")) {
				criterion = SearchCriterion.STARTS_WITH;
			}
			else if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("ENDSWITH")) {
				criterion = SearchCriterion.ENDS_WITH;
			}

            if (searchWhat.equalsIgnoreCase("radianPo") )
			{
				//criteria.addCriterion(searchWhat, criterion, bean.getSearch());
			}
            else if (searchWhat.equalsIgnoreCase("itemId") || searchWhat.equalsIgnoreCase("transferRequestId") || searchWhat.equalsIgnoreCase("customerRmaId"))
			{
				criteria.addCriterion(searchWhat, criterion, bean.getSearch());
			}
			else
			{
				criteria.addCriterion(searchWhat, criterion, bean.getSearch(),
						SearchCriterion.IGNORE_CASE);
			}
		} 
		//if RadianPo is entered in the search field then blank out the expected number of days
		if (!StringHandler.isBlankString(bean.getSearch()) && searchWhat.equalsIgnoreCase("radianPo")) {
			bean.setExpected(null);
		} 
		//add expected date if not null
		if (bean.getExpected() != null && bean.getExpected().trim().length() > 0 ) {
			//get number of days
			int days = new Integer(bean.getExpected()).intValue();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +days);
			criteria.addCriterion("expected", SearchCriterion.LESS_THAN_OR_EQUAL_TO,
					DateHandler.getOracleDate(cal.getTime()));
		}

		if (hasUpdatePermission) {
			return factory.selectSorted(criteria, bean,iscollection,searchWhat, bean.getSearch());
		}
		else {
			Collection c = factory.selectSortedReadonly(criteria, bean,iscollection,searchWhat, bean.getSearch());
			return c;
		}
	}

	public Collection getNonChemicalResult(ReceivingInputBean bean, PersonnelBean personnelBean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		NoBuyOrderPoReceivingViewBeanFactory factory = new
		NoBuyOrderPoReceivingViewBeanFactory(dbManager);
		String sortBy = null;
		SearchCriteria criteria = new SearchCriteria();
		//		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS,
		//				bean.getSourceHub());

		HubInventoryGroupProcess hubInventoryGroupProcess = new
		HubInventoryGroupProcess(this.getClient());
		//TODO: make it work
		//		boolean iscollection = hubInventoryGroupProcess.isCollection(personnelBean.getOpsHubIgOvBeanCollection(),
		//				bean.getSourceHub(), bean.getInventoryGroup());
		if(StringHandler.isBlankString(bean.getInventoryGroup())) {
			//			if(!StringHandler.isBlankString(bean.getOpsEntityId()))
			//				criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId());
			if(!StringHandler.isBlankString(bean.getSourceHub()))
				criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getSourceHub());
		}
		if(!StringHandler.isBlankString(bean.getInventoryGroup()))
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
			if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
				invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' " + " and ops_entity_id = '" + bean.getOpsEntityId() +"' " ;
            if(!StringHandler.isBlankString(bean.getSourceHub()))
                invQuery +=  " and hub =  " +bean.getSourceHub();
			criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}
		/*
		if (bean.getInventoryGroup() != null &&
				!bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

			if (iscollection) {

			}
			else if (bean.getInventoryGroup().length() > 0) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
						bean.getInventoryGroup());
			}
		}
		 */
		//add description search if it's not null
		if (bean.getSearchWhat() != null && bean.getSearch() != null &&
				bean.getSearch().trim().length() > 0) {
			//check search criterion
			String criterion = null;
			if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("IS")) {
				criterion = SearchCriterion.EQUALS;
			}
			else if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
				criterion = SearchCriterion.LIKE;
			}
			else if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("STARTSWITH")) {
				criterion = SearchCriterion.STARTS_WITH;
			}
			else if (bean.getSearchType() != null &&
					bean.getSearchType().equalsIgnoreCase("ENDSWITH")) {
				criterion = SearchCriterion.ENDS_WITH;
			}

			String searchWhat = bean.getSearchWhat();
			if (searchWhat.equalsIgnoreCase("radianPo") || searchWhat.equalsIgnoreCase("itemId"))
			{
				criteria.addCriterion(searchWhat, criterion, bean.getSearch());
			}
			else
			{
				criteria.addCriterion(searchWhat, criterion, bean.getSearch(),
						SearchCriterion.IGNORE_CASE);
			}
		}

		return factory.selectSorted(criteria, bean,false);
	}

	public ExcelHandler writeExcelFile(ReceivingInputBean headerBean,
			Collection searchCollection,Locale locale) throws
			BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<ReceivingViewBean> data = searchCollection;
		ExcelHandler pw = new ExcelHandler(library);



		pw.addTable();
		pw.addRow();
		pw.addCellKeyBold("label.hub");

		pw.addCell(headerBean.getSourceHubName());

		pw.addCellKeyBold("label.category");

		pw.addCell(headerBean.getCategory());

		pw.addCellKeyBold("label.search");

		pw.addCell(headerBean.getSearchWhat() + " " + headerBean.getSearchType() +
				"  " + headerBean.getSearch() + "");

		pw.addRow();

		pw.addCellKeyBold("label.invengroup");

		pw.addCell(headerBean.getInventoryGroup());

		pw.addCellKeyBold("receiving.label.expected");

		pw.addCell(headerBean.getExpected() + " Days");

		//		pw.addTdEmpty();
		//		pw.addTdEmpty();
		pw.addCell("");
		pw.addCell("");

		pw.addRow();
		pw.addRow();
		pw.addRow();

		String[] headerkeys = {
				"label.po","label.poline","label.customerpo", "receiving.label.dateexpected","label.supplier",
				"label.partnumber","receiving.label.openamount","cyclecountresults.label.expectedqty","label.inventorygroup", "label.item", "label.packaging",
		"label.description"};
		/*This array defines the type of the excel column.
		    0 means default depending on the data type. */
		int[] types = {
				0,0,0,pw.TYPE_CALENDAR,0,
				pw.TYPE_NUMBER,0,0,0,0,pw.TYPE_PARAGRAPH,
				pw.TYPE_PARAGRAPH};
		/*This array defines the default width of the column when the Excel file opens.
		    0 means the width will be default depending on the data type.*/
		int[] widths = {
				10,15,16,0,22,
				22,0,0,22,0,0,
				0};
		/*This array defines the horizontal alignment of the data in a cell.
		    0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				pw.ALIGN_CENTER,0,0,0,0,
				0,0,0,0,pw.ALIGN_CENTER,0,
				0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//print rows
		for (ReceivingViewBean member : data) {
			pw.addRow();

			/*			Collection receivingKitBeanCollection = member.getKitCollection();
			int mainRowSpan = 1;
			if (!"N".equalsIgnoreCase(member.getManageKitsAsSingleUnit() ) )
				mainRowSpan = 1;
			else if (receivingKitBeanCollection.size() > 1)
				mainRowSpan = receivingKitBeanCollection.size();  */

			if ("IT".equalsIgnoreCase(member.getTransType()) && member.getCustomerRmaId() != null)
				pw.addCell("RMA " + member.getCustomerRmaId());
			else if ("IT".equalsIgnoreCase(member.getTransType())) {
				pw.addCell("TR " + member.getTransferRequestId());
			}
			else {
				pw.addCell( member.getRadianPo());
			}

			pw.addCell(member.getLineItem());
			pw.addCell(member.getCustomerPo());
			pw.addCell(member.getExpected());
			pw.addCell(member.getLastSupplier());
			if ("Yes".equalsIgnoreCase(headerBean.getDisplayPartNumber())) {
				pw.addCell(member.getCatPartNo());
			}
			else {
				pw.addCell("");
			}
			pw.addCell(member.getQtyOpen());
			pw.addCell(member.getOriginalQty());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getItemId());

			//			int kitCount = 0;
			//			Iterator kitIterator = receivingKitBeanCollection.iterator();
			//			while (kitIterator.hasNext() && kitCount < mainRowSpan ) {
			//				kitCount++;
			//				ReceivingKitBean receivingKitBean = (ReceivingKitBean) kitIterator.next();

			//				if (kitCount > 1) {// && receivingKitBeanCollection.size() > 1) {
			//					pw.addRow();
			//					pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();
			//				}

			if ( "N".equalsIgnoreCase(member.getManageKitsAsSingleUnit())) {
				pw.addCell(	member.getPackaging());
				pw.addCell( member.getMaterialDesc());
			}
			else {
				pw.addCell(	member.getPackaging() );

				if (headerBean.getCategory().equalsIgnoreCase("chemicals"))
					pw.addCell(	member.getItemDescription());
				else
					pw.addCell(	member.getPartDescription());
			}
			//			}
		}
		return pw;
	}
	/*
	public ReceivingViewBean processIndefinite(ReceivingViewBean bean) throws BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		String expDateMaskS = library.getString("java.dateformat");
		String indefinite = library.getString("label.indefinite");
		java.text.SimpleDateFormat dateParser = new java.text.SimpleDateFormat(expDateMaskS,getLocaleObject());
		dateParser.setLenient(false);

		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM/dd/yyyy");

		String expirationDateString = com.tcmis.common.util.StringHandler.
		 emptyIfNull(bean.getExpirationDateString());
			if (indefinite.equalsIgnoreCase(expirationDateString)) {
				expirationDateString = "01/01/3000";
				bean.setExpirationDate(null);
				try {
					bean.setExpirationDate(format.parse(expirationDateString));
				} catch(Exception ex){}
			}
			else
			{
				try {
					bean.setExpirationDate(dateParser.parse(expirationDateString));
				} catch(Exception ex){}
			}
		return bean;
	}
	 */

	private boolean validateInput(ReceivingInputBean bean) {
		/*
	 if (bean == null ||
		bean.getCompanyId() == null || bean.getCompanyId().trim().length() < 1 ||
		bean.getPersonnelId() == 0 ||
		bean.getQueryName() == null || bean.getQueryName().trim().length() < 1 ||
		bean.getQuery() == null || bean.getQuery().trim().length() < 1) {
		 return false;
	 }
	 else {
		 return true;
	 }
		 */
		return true;
	}
}