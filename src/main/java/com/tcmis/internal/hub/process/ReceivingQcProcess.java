package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.VvLotStatusBean;
import com.tcmis.internal.hub.factory.ReceiptBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptDescriptionViewBeanFactory;

/******************************************************************************
 * Process for receiving qc
 * 
 * @version 1.0
 *****************************************************************************/
public class ReceivingQcProcess extends BaseProcess {
	Log							log	= LogFactory.getLog(this.getClass());
	private GenericSqlFactory	genericSqlFactory;

	public ReceivingQcProcess(String client) {
		super(client);
	}

	public ReceivingQcProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection flattenRelationship(ReceivingInputBean bean, Collection receivingQcViewBeanInputCollection, Collection receivingQcViewBeanCollection) {
		Collection finalNewBeanCollection = new Vector();

		if (bean.getSubmitReceive() != null && bean.getSubmitReceive().trim().length() > 0) {

			Iterator finalIterator = receivingQcViewBeanCollection.iterator();
			while (finalIterator.hasNext()) {
				ReceiptDescriptionViewBean currentReceiptDescriptionViewBean = (ReceiptDescriptionViewBean) finalIterator.next();;

				Collection receivingKitBeanCollection = currentReceiptDescriptionViewBean.getKitCollection();

				Iterator kitIterator = receivingKitBeanCollection.iterator();
				while (kitIterator.hasNext()) {
					ReceiptDescriptionViewBean receivingQcKitBean = (ReceiptDescriptionViewBean) kitIterator.next();;

					Iterator finalKitIterator = receivingQcViewBeanInputCollection.iterator();
					while (finalKitIterator.hasNext()) {
						{
							ReceiptDescriptionViewBean finalBean = (ReceiptDescriptionViewBean) finalKitIterator.next();;

							if (receivingQcKitBean.getRowNumber().equalsIgnoreCase(finalBean.getRowNumber())) {
								finalNewBeanCollection.add(finalBean);
								break;
							}
						}
					}
				}
			}
		}
		else {
			finalNewBeanCollection = receivingQcViewBeanInputCollection;
		}
		return finalNewBeanCollection;
	}

	public HashMap receiveSelected(ReceivingInputBean input, Collection receivingQcViewBeanCollection, PersonnelBean personnelBean) {
		Collection<ReceiptDescriptionViewBean> receivedQcReceiptsCollection = new Vector();
		Collection finalReceivedQcReceiptsCollection = new Vector();
		String labelReceipts = "";
		String errorMessage = "";

		if (input.hasSubmitReceive()) {

			try {
				receivedQcReceiptsCollection = receiveQcSelected(receivingQcViewBeanCollection, personnelBean, input.isCategoryChemicals() ? false : true);

				// if inventory group allow user to select work area for receipt
				if (!StringHandler.isBlankString(input.getWorkAreaRestriction())) {
					// if user selected a different work area
					if (!input.getWorkAreaRestriction().equals(input.getOriginalApplication())) {
						changeWorkArea(input);
					}
				}
			}
			catch (Exception e) {
			} // catch (BaseException ex) {}

			for (ReceiptDescriptionViewBean currentReceipt : receivedQcReceiptsCollection) {

				labelReceipts += "" + currentReceipt.getReceiptId() + ",";

				Collection<ReceiptDescriptionViewBean> currentReceiptKits = currentReceipt.getKitCollection();

				String currentOk = currentReceipt.getOk();
				String currentUpdateStatus = currentReceipt.getUpdateStatus();
				boolean readOnly = false;
				if (currentUpdateStatus != null && "readOnly".equalsIgnoreCase(currentUpdateStatus)) {
					readOnly = true;
				}

				if (!readOnly && (currentOk != null && currentOk.length() > 0) || currentReceiptKits.size() > 1) {

					Vector receiptQcKitVector = new Vector();
					for (ReceiptDescriptionViewBean receivingQcKitBean : currentReceiptKits) {

						String currentKitUpdate = receivingQcKitBean.getUpdateStatus();
						String currentKitOk = receivingQcKitBean.getOk();
						String currentMvItem = receivingQcKitBean.getMvItem();

						// log.info("Result currentKitOk " + currentKitOk + "
						// currentKitUpdate " + currentKitUpdate + "");
						if ("Y".equalsIgnoreCase(currentMvItem)) {
							if (currentOk != null && currentOk.length() > 0) {
								if (currentKitUpdate != null) {
									if (currentKitUpdate != null && "Error".equalsIgnoreCase(currentKitUpdate)) {
										errorMessage += " " + receivingQcKitBean.getErrorMessage();
										receiptQcKitVector.add(receivingQcKitBean);
									}
								}
								else {
									receiptQcKitVector.add(receivingQcKitBean);
								}
							}
							else {
								receiptQcKitVector.add(receivingQcKitBean);
							}
						}
						else if (currentKitOk != null && currentKitOk.length() > 0) {
							if (currentKitUpdate != null) {
								if (currentKitUpdate != null && "Error".equalsIgnoreCase(currentKitUpdate)) {
									errorMessage += " " + receivingQcKitBean.getErrorMessage();
									receiptQcKitVector.add(receivingQcKitBean);
								}
							}
						}
						else {
							receiptQcKitVector.add(receivingQcKitBean);
						}
					}

					if (receiptQcKitVector.size() > 0) {
						currentReceipt.setKitCollection((Vector) receiptQcKitVector.clone());
						currentReceipt.setRowSpan(new BigDecimal(receiptQcKitVector.size()));

						finalReceivedQcReceiptsCollection.add(currentReceipt);
					}
				}
				else {
					finalReceivedQcReceiptsCollection.add(currentReceipt);
				}
			}
		}
		else {
			// Build the labels here
			finalReceivedQcReceiptsCollection = receivingQcViewBeanCollection;
		}

		HashMap result = new HashMap();
		result.put("ERRORMESSAGE", errorMessage);
		result.put("RECEIVEDQCCOLLECTION", finalReceivedQcReceiptsCollection);
		return result;
	}

	private void changeWorkArea(ReceivingInputBean inputBean) {
		try {
			DbManager dbManager = new DbManager(getClient());
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			GenericProcess process = new GenericProcess(this.getClient());
			Collection inArgs = process.buildProcedureInput(inputBean.getReceiptId(), inputBean.getWorkAreaRestriction());
			if (log.isDebugEnabled()) {
				log.info("Input Args for pkg_hub_automation.p_update_receipt_application  :" + inArgs);
			}
			factory.doProcedure("pkg_hub_automation.p_update_receipt_application", inArgs);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} // end of method

	public Collection copyAttributes(ReceivingInputBean bean, Collection receivingQcViewBeanCollection, Vector savedBeanVector, Collection vvLotStatusBeanCollection) {
		// int kitSize = 0;
		Collection receivingQcViewBeanColl = new Vector();
		Iterator iterator = receivingQcViewBeanCollection.iterator();
		int count = 0;

		while (iterator.hasNext()) {
			ReceiptDescriptionViewBean inputBean = (ReceiptDescriptionViewBean) iterator.next();
			ReceiptDescriptionViewBean savedBean = (ReceiptDescriptionViewBean) savedBeanVector.get(count);

			String updateStatus = com.tcmis.common.util.StringHandler.emptyIfNull(inputBean.getUpdateStatus());
			if ("readOnly".equalsIgnoreCase(updateStatus)) {
				savedBean.setLabelQuantity(inputBean.getLabelQuantity());

				if (inputBean.getOk() != null) {
					String rowid = inputBean.getOk();
					savedBean.setOk(rowid);
					// log.info("rowid " + rowid + "");
				}
				else {
					savedBean.setOk(null);
				}

				savedBean.setRowNumber(inputBean.getRowNumber());
				savedBean.setUpdateStatus(inputBean.getUpdateStatus());
				receivingQcViewBeanColl.add(savedBean);
			}
			else {
				BigDecimal receiptId = new BigDecimal(com.tcmis.common.util.StringHandler.emptyIfNull(inputBean.getReceiptId()));

				// log.info("Receiving Saved Receipt ID
				// "+savedBean.getReceiptId()+" Receipt "+receiptId+" Lot
				// "+inputBean.getMfgLot()+" DOR
				// "+inputBean.getDateOfReceipt()+"");
				if (receiptId.equals(savedBean.getReceiptId())) {
					if (inputBean.getOk() != null) {
						String rowid = inputBean.getOk();
						savedBean.setOk(rowid);
						// log.info("rowid " + rowid + "");
					}
					else {
						savedBean.setOk(null);
					}

					if (bean.getCategory().equalsIgnoreCase("chemicals")) {
						savedBean.setMfgLot(inputBean.getMfgLot());
						savedBean.setLotStatus(inputBean.getLotStatus());

						if (savedBean.getQualityControlItem() != null && "Y".equalsIgnoreCase(savedBean.getQualityControlItem().trim())) {
							Iterator vvLotStatusIterator = vvLotStatusBeanCollection.iterator();
							while (vvLotStatusIterator.hasNext()) {
								VvLotStatusBean vvLotStatusBean = (VvLotStatusBean) vvLotStatusIterator.next();;

								String lotStatus = vvLotStatusBean.getLotStatus();
								if (lotStatus.equalsIgnoreCase(savedBean.getLotStatus())) {
									String certified = vvLotStatusBean.getCertified();
									if ("Y".equalsIgnoreCase(certified)) {
										savedBean.setCertificationUpdate("Yes");
									}
								}
							}
						}

						savedBean.setVendorShipDate(inputBean.getVendorShipDate());
						savedBean.setDateOfReceipt(inputBean.getDateOfReceipt());
						savedBean.setDateOfManufacture(inputBean.getDateOfManufacture());
						savedBean.setDateOfRepackaging(inputBean.getDateOfRepackaging());
						savedBean.setDateOfShipment(inputBean.getDateOfShipment());
						savedBean.setExpireDate(inputBean.getExpireDate());
						savedBean.setLabelQuantity(inputBean.getLabelQuantity());
						savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
						savedBean.setNotes(inputBean.getNotes());
						savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
						savedBean.setQaStatement(inputBean.getQaStatement());
						savedBean.setLotStatusRootCause(inputBean.getLotStatusRootCause());
						savedBean.setLotStatusRootCauseNotes(inputBean.getLotStatusRootCauseNotes());
						savedBean.setResponsibleCompanyId(inputBean.getResponsibleCompanyId());
						savedBean.setRowSpan(inputBean.getRowSpan());
						savedBean.setClosePoLine(inputBean.getClosePoLine());
						savedBean.setBin(inputBean.getBin());
						savedBean.setUnitLabelPrinted(inputBean.getUnitLabelPrinted());
						savedBean.setUnitLabelCatPartNo(inputBean.getUnitLabelCatPartNo());
						savedBean.setNewQuantityReceived(inputBean.getNewQuantityReceived());
						savedBean.setSupplierCageCode(inputBean.getSupplierCageCode());
					}
					else {
						savedBean.setNotes(inputBean.getNotes());
						savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
						savedBean.setQaStatement(inputBean.getQaStatement());
					}
					savedBean.setRowNumber(inputBean.getRowNumber());
					savedBean.setQualityTrackingNumber(inputBean.getQualityTrackingNumber());
					// log.info("rowNumber "+savedBean.getRowNumber()+"");

					receivingQcViewBeanColl.add(savedBean);
				}
			}
			count++;
		}
		return receivingQcViewBeanColl;
	}

	/*
	 * public ReceiptDescriptionViewBean
	 * processIndefinite(ReceiptDescriptionViewBean bean) throws BaseException {
	 * ResourceLibrary library = new
	 * ResourceLibrary("com.tcmis.common.resources.CommonResources",
	 * this.getLocaleObject()); String expDateMaskS =
	 * library.getString("java.dateformat"); String indefinite =
	 * library.getString("label.indefinite"); java.text.SimpleDateFormat
	 * dateParser = new
	 * java.text.SimpleDateFormat(expDateMaskS,getLocaleObject());
	 * dateParser.setLenient(false);
	 * 
	 * java.text.SimpleDateFormat format = new
	 * java.text.SimpleDateFormat("MM/dd/yyyy");
	 * 
	 * String expirationDateString = com.tcmis.common.util.StringHandler.
	 * emptyIfNull(bean.getExpireDateString()); if
	 * (indefinite.equalsIgnoreCase(expirationDateString)) {
	 * expirationDateString = "01/01/3000"; bean.setExpireDate(null); try {
	 * bean.setExpireDate(format.parse(expirationDateString)); } catch(Exception
	 * ex){} } else { try {
	 * bean.setExpireDate(dateParser.parse(expirationDateString)); }
	 * catch(Exception ex){} } return bean; }
	 */
	public String receiptsToLabelList(Collection receivingQcViewBeanCollection) {
		Iterator mainIterator = receivingQcViewBeanCollection.iterator();
		String labelReceipts = "";
		while (mainIterator.hasNext()) {
			ReceiptDescriptionViewBean receiptDescriptionViewBean = (ReceiptDescriptionViewBean) mainIterator.next();;

			/*
			 * if ("Y".equalsIgnoreCase(receiptDescriptionViewBean.getMvItem()))
			 * { BigDecimal pendingReceipt =
			 * receiptDescriptionViewBean.getPendingReceiptId(); labelReceipts
			 * += "" + pendingReceipt + ","; } else
			 */ {
				BigDecimal receivedReceipt = receiptDescriptionViewBean.getReceiptId();
				labelReceipts += "" + receivedReceipt + ",";
			}
		}

		if (labelReceipts.length() > 1) {
			labelReceipts = labelReceipts.substring(0, (labelReceipts.length() - 1));
		}

		return labelReceipts;
	}

	public Collection createRelationalObject(Collection receivingQcViewBeanCollection) {
		Collection finalreceivingQcViewBeanCollection = new Vector();
		String nextReceiptId = "";
		String nextReceiptGroup = "";
		// String nextMvItem = "";

		int sameReceiptIdCount = 0;
		BigDecimal totalMvQuantityReceived = new BigDecimal("0");
		Vector collectionVector = new Vector(receivingQcViewBeanCollection);
		Vector receiptKitVector = new Vector();

		for (int loop = 0; loop < collectionVector.size(); loop++) {
			ReceiptDescriptionViewBean currentReceiptDescriptionViewBean = (ReceiptDescriptionViewBean) collectionVector.elementAt(loop);

			String currentReceiptId = "" + currentReceiptDescriptionViewBean.getReceiptId() + "";
			String currentReceiptGroup = "" + NumberHandler.emptyIfNull(currentReceiptDescriptionViewBean.getReceiptGroup()) + "";
			// String currentMvItem =
			// currentReceiptDescriptionViewBean.getMvItem();

			if (!((loop + 1) == collectionVector.size())) {
				ReceiptDescriptionViewBean nextReceiptDescriptionViewBean = (ReceiptDescriptionViewBean) collectionVector.elementAt(loop + 1);

				nextReceiptId = "" + nextReceiptDescriptionViewBean.getReceiptId() + "";
				nextReceiptGroup = "" + NumberHandler.emptyIfNull(nextReceiptDescriptionViewBean.getReceiptGroup()) + "";
				// nextMvItem = nextReceiptDescriptionViewBean.getMvItem();
			}
			else {
				nextReceiptId = "";
				nextReceiptGroup = "";
			}

			boolean sameReceiptId = false;
			if (currentReceiptId.equalsIgnoreCase(nextReceiptId) || ("Y".equalsIgnoreCase(currentReceiptDescriptionViewBean.getMvItem()) && currentReceiptGroup.length() > 0
					&& currentReceiptGroup.equalsIgnoreCase(nextReceiptGroup))) {
				sameReceiptId = true;
				sameReceiptIdCount++;
			}
			// log.info("sameReceiptId "+sameReceiptId+" Receiving
			// "+currentReceiptDescriptionViewBean.getOk()+" currentReceiptGroup
			// "+currentReceiptGroup+" nextReceiptGroup "+nextReceiptGroup+"
			// Receipt ID "+currentReceiptDescriptionViewBean.getReceiptId()+"
			// Next Receipt "+nextReceiptId+" Lot
			// "+currentReceiptDescriptionViewBean.getMfgLot()+" DOR
			// "+currentReceiptDescriptionViewBean.getDateOfReceipt()+"");

			receiptKitVector.add(currentReceiptDescriptionViewBean);
			if ("Y".equalsIgnoreCase(currentReceiptDescriptionViewBean.getMvItem())) {
				totalMvQuantityReceived = totalMvQuantityReceived
						.add(currentReceiptDescriptionViewBean.getQuantityReceived().multiply(currentReceiptDescriptionViewBean.getCostFactor()));
			}

			if (sameReceiptId) {

			}
			else {
				currentReceiptDescriptionViewBean.setKitCollection((Vector) receiptKitVector.clone());
				receiptKitVector = new Vector();
				currentReceiptDescriptionViewBean.setRowSpan(new BigDecimal(sameReceiptIdCount + 1));
				currentReceiptDescriptionViewBean.setTotalMvQuantityReceived(totalMvQuantityReceived);
				finalreceivingQcViewBeanCollection.add(currentReceiptDescriptionViewBean);

				sameReceiptIdCount = 0;
				totalMvQuantityReceived = new BigDecimal("0");
			}
		}

		// log.info("Final collectionSize here "
		// +finalreceivingQcViewBeanCollection.size() + "");
		return finalreceivingQcViewBeanCollection;
	}

	public Collection receiveQcSelected(Collection<ReceiptDescriptionViewBean> receipts, PersonnelBean user, boolean nonChemicalreceipt) throws BaseException {
		BigDecimal personnelId = new BigDecimal(user.getPersonnelId());
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Vector rmaRequestIds = new Vector();
		Vector receiptIds = new Vector();
		ReceiptDescriptionViewBeanFactory factory = new ReceiptDescriptionViewBeanFactory(dbManager);

		for  (ReceiptDescriptionViewBean receipt : receipts) {
			String currentReceiptId = "" + receipt.getReceiptId() + "";
			String currentOk = receipt.getOk();
			String currentMvItem = receipt.getMvItem();
			String currentmanageKitAsSingle = receipt.getManageKitsAsSingleUnit();
			String currentUpdateStatus = receipt.getUpdateStatus();
			boolean readOnly = false;
			if (currentUpdateStatus != null && "readOnly".equalsIgnoreCase(currentUpdateStatus)) {
				readOnly = true;
			}

			Collection<ReceiptDescriptionViewBean> receiptKits = receipt.getKitCollection();

			if (!readOnly && ((currentOk != null && currentOk.length() > 0) || receiptKits.size() > 1)) {
				int kitCount = 0;
				Collection result = null;
				boolean receiptSuccess = true;
				boolean receiptKitSuccess = true;
				boolean calledReceiptQc = false;
				for (ReceiptDescriptionViewBean receiptKit : receiptKits) {

					String currentKitOk = receiptKit.getOk();
					if ((kitCount == 0) || (!("N".equalsIgnoreCase(currentmanageKitAsSingle)) && (currentKitOk != null && currentKitOk.length() > 0))
							|| "Y".equalsIgnoreCase(currentMvItem)) {
						// Call p_receipt_qc
						String lotStatus = "";
						if (StringUtils.isNotBlank(receiptKit.getLotStatus())) {
							lotStatus = receiptKit.getLotStatus();
						}

						if ("Customer Purchase".equalsIgnoreCase(lotStatus) || "Write Off Requested".equalsIgnoreCase(lotStatus) || "3PL Purchase".equalsIgnoreCase(lotStatus)) {
							if (StringUtils.isBlank(receiptKit.getLotStatusRootCause()) || StringUtils.isBlank(receiptKit.getResponsibleCompanyId())) {
								receiptSuccess = false;
								receipt.setErrorMessage(
										"Please choose the Root Cause and Root Cause Company for Receipt ID :" + receipt.getReceiptId() + ". ");
								receipt.setUpdateStatus("Error");
								break;
							}
						}

						try {
							BigDecimal quantityReceived = receipt.getQuantityReceived();
							if ((currentKitOk != null && currentKitOk.length() > 0 && quantityReceived.floatValue() > 0)
									|| ("Y".equalsIgnoreCase(currentMvItem) && (currentOk != null && currentOk.length() > 0))) {

								result = factory.receiptQc(receipt, receiptKit, personnelId, nonChemicalreceipt);
								calledReceiptQc = true;

								BigDecimal customerRmaId = receipt.getCustomerRmaId();
								if (customerRmaId != null && !rmaRequestIds.contains(customerRmaId)) {
									rmaRequestIds.add(customerRmaId);
								}

								if ("N".equals(receipt.getQcOk()) && currentKitOk != null && currentKitOk.length() > 0
										&& receipt.getReceiptId() != null && !receiptIds.contains(receipt.getReceiptId())) {
									BigDecimal receiptId = receipt.getReceiptId();
									if (user.getPermissionBean().hasInventoryGroupPermission("transferReconciliation",
											receipt.getInventoryGroup(), null, null)) {
										receiptIds.add(receiptId);
									}
								}
							}
							// Update the GHS label
							updateGHSLabelReqs(receipt);

							receiptKit.setUpdateStatus("Ok");
						}
						catch (Exception ex) {
							ex.printStackTrace();
							receipt.setErrorMessage(com.tcmis.common.util.StringHandler.emptyIfNull(ex.getMessage()));
							receipt.setUpdateStatus("Error");
							receiptSuccess = false;
							if (kitCount > 0) {
								receiptKit.setErrorMessage(com.tcmis.common.util.StringHandler.emptyIfNull(ex.getMessage()));
								receiptKit.setUpdateStatus("Error");
								receiptKitSuccess = false;
							}
						}

						String errorCode = "";
						if (result != null) {
							Iterator resultIterator = result.iterator();
							while (resultIterator.hasNext()) {
								errorCode = (String) resultIterator.next();;
							}

							if (!"OK".equalsIgnoreCase(errorCode)) {
								log.info("P_RECEIPT_QC errorCode " + errorCode + "");
								receipt.setErrorMessage(" " + errorCode + " ");
								receiptKit.setErrorMessage(" " + errorCode + " ");
								receiptKit.setUpdateStatus("Error");
								receiptKitSuccess = false;
								receiptSuccess = false;
							}
						}
						// Call write off request here
						if (receiptSuccess) {
							try {
								if ("Write Off Requested".equalsIgnoreCase(lotStatus)) {
									String writeOffErrorMessage = createWriteOffRequest(receipt, receiptKit, personnelId);
									if (writeOffErrorMessage != null && writeOffErrorMessage.length() > 0) {
										receipt.setErrorMessage(" " + errorCode + " ");
										receiptKit.setErrorMessage(" " + errorCode + " ");
									}
								}
							}
							catch (Exception writeOffEx) {
								writeOffEx.printStackTrace();
								receipt
										.setErrorMessage("Error Adding New Write Off Request for receipt Id: " + receipt.getReceiptId());
								receiptKit.setErrorMessage("Error Adding New Write Off Request for receipt Id: " + receipt.getReceiptId());
							}
						}
					}

					if ("N".equalsIgnoreCase(currentmanageKitAsSingle) && (currentOk != null && currentOk.length() > 0)) {
						// Call p_receipt_component
						if (receiptSuccess) {
							if (log.isDebugEnabled()) {
								log.debug("Calling p_receipt_component for COMPONENT_ID " + receipt.getComponentId() + " ReceiptId "
										+ receipt.getReceiptId() + " Personnel ID  " + personnelId + "");
							}
							try {
								receiptSuccess = factory.insertReceiptComponenet(receiptKit, receipt.getReceiptId(), personnelId);
								receiptKit.setUpdateStatus("Ok");
							}
							catch (Exception ex1) {
								receiptSuccess = false;
								receiptKit.setUpdateStatus("Error");
								receiptKit.setErrorMessage("Error Calling p_receipt_component for COMPONENT_ID " + receipt.getComponentId()
										+ " ReceiptId " + receipt.getReceiptId() + ". ");
								receiptKitSuccess = false;
							}
						}
					}

					kitCount++;
					// log.info("Here kitCount " + kitCount + " receiptSuccess "
					// + receiptSuccess + " receiptKitSuccess " +
					// receiptKitSuccess + "");
				}

				if (calledReceiptQc && receiptSuccess && !nonChemicalreceipt && currentOk != null && currentOk.length() > 0) {

					// call P_RECEIPT_ALLOCATE
					ReceiptBeanFactory receiptBeanFactory = new ReceiptBeanFactory(dbManager);
					try {
						// log.debug("call P_RECEIPT_ALLOCATE " +
						// currentReceiptDescriptionViewBean.getReceiptId() +
						// "");
						receiptBeanFactory.allocateReceipt(receipt);
					}
					catch (Exception ex3) {
						ex3.printStackTrace();
						receipt
								.setErrorMessage("Error Calling P_RECEIPT_ALLOCATE for Receipt ID:" + receipt.getReceiptId() + "");
						receiptSuccess = false;
						// currentReceiptDescriptionViewBean.setUpdateStatus("Error");
					}
				}

				// log.info("Here kitCount " + kitCount + " receiptSuccess " +
				// receiptSuccess + "");
				if (receiptSuccess) {
					receipt.setUpdateStatus("OK");
				}
				else {
					// Update receipt to approved = 'N'
					ReceiptBeanFactory receiptBeanFactory = new ReceiptBeanFactory(dbManager);

					SearchCriteria criteria = new SearchCriteria();
					criteria.addCriterion("receiptId", SearchCriterion.IN, currentReceiptId);
					try {
						receiptBeanFactory.updateApprovedStatus("N", personnelId, criteria);
					}
					catch (BaseException ex5) {
						ex5.printStackTrace();
					}
					receipt.setUpdateStatus("Error");
				}
			}
		}

		Collection rmaResult = null;
		if (rmaRequestIds != null && rmaRequestIds.size() > 0) {
			for (int i = 0; i < rmaRequestIds.size(); i++) {
				BigDecimal customerRmaId = (BigDecimal) rmaRequestIds.elementAt(i);
				try {
					// rmaResult = factory.processRmaRequest(customerRmaId,
					// personnelId);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}

				String errorCode = "";
				if (rmaResult != null) {
					Iterator resultIterator = rmaResult.iterator();
					while (resultIterator.hasNext()) {
						errorCode = (String) resultIterator.next();;
					}
					log.info("errorCode " + errorCode);
				}
			}
		}

		if (receiptIds != null && receiptIds.size() > 0) {
			for (int i = 0; i < receiptIds.size(); i++) {
				BigDecimal receiptId = (BigDecimal) receiptIds.elementAt(i);
				try {
					factory.processInvTransferAdjustment(receiptId, personnelId);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		// log.info("Final collectionSize here " +
		// receivingViewBeanCollection.size() + "");
		return receipts;
	}

	public String createWriteOffRequest(ReceiptDescriptionViewBean receiptBean, ReceiptDescriptionViewBean receiptKitBean, BigDecimal personnelId) throws Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String writeOffReqComment = "";

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		GenericProcess process = new GenericProcess(this.getClient());

		try {
			if (receiptKitBean.getLotStatusRootCause() != null && receiptKitBean.getLotStatusRootCause().trim().length() > 0) {
				writeOffReqComment = receiptKitBean.getLotStatusRootCause();
			}

			if (receiptKitBean.getLotStatusRootCauseNotes() != null && receiptKitBean.getLotStatusRootCauseNotes().trim().length() > 0) {
				writeOffReqComment = writeOffReqComment + " " + receiptKitBean.getLotStatusRootCauseNotes();
			}

			/*
			 * The procedure expects a -ve value for write-on and a positive
			 * value for write-off
			 */
			inArgs = process.buildProcedureInput(receiptBean.getReceiptId(), new Date(), personnelId, receiptBean.getQuantityReceived(), writeOffReqComment);
			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			if (log.isDebugEnabled()) {
				log.info("Input Args for PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request  :" + inArgs);
			}
			Vector error = (Vector) factory.doProcedure("PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request", inArgs, outArgs);
			if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
				errorMsg = (String) error.get(0);
				log.info("Error in Procedure PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request: " + receiptBean.getReceiptId() + " Error Code " + errorMsg + " ");
			}
		}
		catch (Exception e) {
			errorMsg = "Error Adding New Write Off Request for Id: " + receiptBean.getReceiptId();
		}

		return errorMsg;
	}

	public boolean reverseReceipt(BigDecimal receiptId) throws Exception {
		boolean reverseReceiptSuccess = true;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptBeanFactory receiptBeanFactory = new ReceiptBeanFactory(dbManager);
		try {
			receiptBeanFactory.reverseReceipt(receiptId);
		}
		catch (Exception ex3) {
			ex3.printStackTrace();
			reverseReceiptSuccess = false;
		}
		return reverseReceiptSuccess;
	}

	/*
	 * public HashMap convertReceiptToCustomerOwened(ReceiptDescriptionViewBean
	 * bean) throws Exception { boolean converReceiptSuccess = true; String
	 * errorMessage = ""; Collection resultColl = null; DbManager dbManager =
	 * new DbManager(getClient());
	 * 
	 * HubInventoryOwnerBeanFactory hubInventoryOwnerBeanFactory = new
	 * HubInventoryOwnerBeanFactory(dbManager);
	 * 
	 * SearchCriteria criteria = new SearchCriteria();
	 * criteria.addCriterion("hub", SearchCriterion.EQUALS,
	 * bean.getBranchPlant()); criteria.addCriterion("status",
	 * SearchCriterion.EQUALS, "A");
	 * 
	 * Collection hubInventoryOwnerBeanColl = hubInventoryOwnerBeanFactory.
	 * selectDistinct(criteria);
	 * 
	 * if (hubInventoryOwnerBeanColl.size() > 0) { String
	 * coustomerOwnedCompanyId = ""; Iterator hubInventoryOwnerIterator =
	 * hubInventoryOwnerBeanColl.iterator(); while
	 * (hubInventoryOwnerIterator.hasNext()) { HubInventoryOwnerBean
	 * hubInventoryOwnerBean = (HubInventoryOwnerBean)
	 * hubInventoryOwnerIterator.next(); ;
	 * 
	 * coustomerOwnedCompanyId = hubInventoryOwnerBean.getCompanyId(); } //Call
	 * P_CONVERT_RECEIPT_TO_CUSTOMER try { resultColl =
	 * hubInventoryOwnerBeanFactory.convertReceiptToCustomerOwened(
	 * bean.getReceiptId(), coustomerOwnedCompanyId); } catch (Exception ex2) {
	 * ex2.printStackTrace(); errorMessage =
	 * "Convert Receipt to Customer Failed for Receipt ID :" +
	 * bean.getReceiptId() + ". "; converReceiptSuccess = false;
	 * //bean.setUpdateStatus("Error"); }
	 * 
	 * if (resultColl != null) { Iterator resultIterator =
	 * resultColl.iterator(); int resultCount = 0; String errorCode = "";
	 * 
	 * while (resultIterator.hasNext()) { if (resultCount == 2) { try {
	 * errorCode = (String) resultIterator.next(); ; } catch (Exception ex) {
	 * ex.printStackTrace(); errorCode = ""; } } else { resultIterator.next(); }
	 * resultCount++; }
	 * 
	 * if (errorCode != null && errorCode.trim().length() > 0) {
	 * log.info("P_CONVERT_RECEIPT_TO_CUSTOMER errorCode " + errorCode + "");
	 * errorMessage = "Convert Receipt to Customer Failed for Receipt ID :" +
	 * bean.getReceiptId() + "   Msg" + errorCode + ". ";
	 * //bean.setUpdateStatus("Error"); converReceiptSuccess = false; } } else {
	 * errorMessage = "Convert Receipt to Customer Failed for Receipt ID :" +
	 * bean.getReceiptId() + ". "; converReceiptSuccess = false; } }
	 * 
	 * HashMap result = new HashMap(); result.put("ERROR", errorMessage);
	 * result.put("SUCESS", new Boolean(converReceiptSuccess)); return result; }
	 */

	public Collection getChemicalResult(String receivedReceipts, String hubNumber, String inventoryGroup, PersonnelBean personnelBean, boolean justReceived,
			boolean hasUpdatePermission) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptDescriptionViewBeanFactory factory = new ReceiptDescriptionViewBeanFactory(dbManager);
		String sortBy = null;
		SearchCriteria criteria = new SearchCriteria();

		if (hubNumber != null && hubNumber.length() > 0) {
			criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, hubNumber);

			if (!justReceived) {
				criteria.addCriterion("approved", SearchCriterion.EQUALS, "N");
			}
		}

		HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(this.getClient());
		boolean iscollection = false; // TODO: make this work
		// hubInventoryGroupProcess.isCollection(personnelBean.getOpsHubIgOvBeanCollection(),hubNumber,
		// inventoryGroup);

		if (!StringHandler.isBlankString(inventoryGroup))
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
			if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0)
				invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' ";
			if (!StringHandler.isBlankString(hubNumber))
				invQuery += " and hub =  " + hubNumber;
			criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}

		/*
		 * if (inventoryGroup != null &&
		 * !inventoryGroup.equalsIgnoreCase("ALL")) {
		 * 
		 * if (iscollection) {
		 * 
		 * } else if (inventoryGroup.length() > 0) {
		 * criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 * inventoryGroup); } }
		 */
		/*
		 * if (inventoryGroup!=null && !inventoryGroup.equalsIgnoreCase("ALL")
		 * && inventoryGroup.length() > 0) {
		 * criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 * inventoryGroup); }
		 */

		if (receivedReceipts != null && receivedReceipts.length() > 0) {
			criteria.addCriterion("receiptId", SearchCriterion.IN, receivedReceipts);
		}

		ReceivingInputBean inputBean = new ReceivingInputBean();
		inputBean.setInventoryGroup(inventoryGroup);
		inputBean.setSourceHub(hubNumber);
		inputBean.setSort("Receipt ID");
		return factory.selectSorted(criteria, inputBean, iscollection, hasUpdatePermission);
	}

	public Collection getChemicalResult(ReceivingInputBean bean, PersonnelBean personnelBean, boolean hasUpdatePermission) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptDescriptionViewBeanFactory factory = new ReceiptDescriptionViewBeanFactory(dbManager);
		String sortBy = null;
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getSourceHub());

		HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(this.getClient());
		boolean iscollection = false; // TODO: make this work
		// hubInventoryGroupProcess.isCollection(personnelBean.getOpsHubIgOvBeanCollection(),
		// bean.getSourceHub(), bean.getInventoryGroup());

		if (StringHandler.isBlankString(bean.getInventoryGroup())) {
			// if(!StringHandler.isBlankString(bean.getOpsEntityId()))
			// criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS,
			// bean.getOpsEntityId());
			if (!StringHandler.isBlankString(bean.getSourceHub()))
				criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getSourceHub());
		}
		if (!StringHandler.isBlankString(bean.getInventoryGroup()))
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
			if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0)
				invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' " + " and ops_entity_id = '" + bean.getOpsEntityId() + "' ";
			if (!StringHandler.isBlankString(bean.getSourceHub()))
				invQuery += " and hub =  " + bean.getSourceHub();
			criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}
		/*
		 * if (bean.getInventoryGroup() != null &&
		 * !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
		 * 
		 * if (iscollection) {
		 * 
		 * } else if (bean.getInventoryGroup().length() > 0) {
		 * criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 * bean.getInventoryGroup()); } }
		 */
		// add description search if it's not null
		if (bean.getSearchWhat() != null && bean.getSearch() != null && bean.getSearch().trim().length() > 0) {
			// check search criterion
			String criterion = null;
			if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("IS")) {
				criterion = SearchCriterion.EQUALS;
			}
			else if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
				criterion = SearchCriterion.LIKE;
			}
			else if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("STARTSWITH")) {
				criterion = SearchCriterion.STARTS_WITH;
			}
			else if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("ENDSWITH")) {
				criterion = SearchCriterion.ENDS_WITH;
			}

			String searchWhat = bean.getSearchWhat();
			// check what to search by
			if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("PO")) {
				criteria.addCriterion("radianPo", criterion, bean.getSearch());
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("Receipt Id")) {
				criteria.addCriterion("receiptId", criterion, bean.getSearch());
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("ITEM DESC")) {
				criteria.addCriterion("lineDesc", criterion, bean.getSearch(), SearchCriterion.IGNORE_CASE);
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("ITEM ID")) {
				criteria.addCriterion("itemId", criterion, bean.getSearch());
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("transferRequestId")) {
				criteria.addCriterion("transferRequestId", criterion, bean.getSearch());
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("customerRmaId")) {
				criteria.addCriterion("customerRmaId", criterion, bean.getSearch());
			}
		}

		criteria.addCriterion("approved", SearchCriterion.EQUALS, "N");

		return factory.selectSorted(criteria, bean, iscollection, hasUpdatePermission);
	}

	public ExcelHandler writeExcelFile(ReceivingInputBean headerBean, Collection searchCollection, Locale locale) throws BaseException, Exception {
		log.debug("create excel file....");
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		pw.addRow();
		pw.addCellKeyBold("label.hub");

		pw.addCell(headerBean.getSourceHubName());
		pw.addCellKeyBold("label.category");
		pw.addCell(headerBean.getCategory());
		pw.addRow();

		pw.addCellKeyBold("label.invengroup");

		pw.addCell(headerBean.getInventoryGroup());

		pw.addCellKeyBold("label.search");
		pw.addCell(headerBean.getSearchWhat() + " " + headerBean.getSearchType() + "  " + headerBean.getSearch());

		pw.addRow();
		pw.addRow();

		/* Pass the header keys for the Excel. */
		String[] headerkeys = {"label.po", "label.poline", "label.transferrequestid", "label.customerpo", "label.invengroup", "label.item", "label.packaging", "label.description",
				"label.mfglot", "label.orimfglot", "label.actsupshpdate", "receivedreceipts.label.dor", "receivedreceipts.label.dom", "label.dateofrepackaging",
				"label.manufacturerdos", "label.expdate", "label.bin", "label.qtyrecd", "label.uom", "label.receiptid", "label.transferreceiptid", "label.pkgqtyxsize",
				"label.notes", "receiving.label.deliveryticket", "label.qastatement", "label.cagecode"};
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type.
		 */
		int[] types = {0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR,
				pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, 0};
		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = {12, 10, 12, 11, 13, 11, 0, 0, 10, 16, 12, 0, 0, 13, 0, 0, 0, 0, 0, 0, 15, 38, 0, 0};

		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// print rows
		Iterator mainIterator = searchCollection.iterator();
		while (mainIterator.hasNext()) {
			pw.addRow();

			ReceiptDescriptionViewBean receiptDescriptionViewBean = (ReceiptDescriptionViewBean) mainIterator.next();;

			Collection receivingQcKitBeanCollection = receiptDescriptionViewBean.getKitCollection();
			/*
			 * int mainRowSpan = 1; if
			 * (!"N".equalsIgnoreCase(receiptDescriptionViewBean.
			 * getManageKitsAsSingleUnit() ) ) mainRowSpan = 1; else if (
			 * receivingQcKitBeanCollection.size() > 1 ) mainRowSpan =
			 * receivingQcKitBeanCollection.size();
			 */
			/*
			 * String cell1 = "TR ";
			 * 
			 * if
			 * ("IT".equalsIgnoreCase(receiptDescriptionViewBean.getDocType()))
			 * { if(receiptDescriptionViewBean.getTransferRequestId() != null)
			 * cell1 = cell1 +
			 * receiptDescriptionViewBean.getTransferRequestId().toString();
			 * }else if
			 * ("IA".equalsIgnoreCase(receiptDescriptionViewBean.getDocType()))
			 * { if(receiptDescriptionViewBean.getReturnPrNumber() != null)
			 * cell1 = cell1 + receiptDescriptionViewBean.getReturnPrNumber() +
			 * " - " + receiptDescriptionViewBean.getReturnLineItem(); }else
			 * cell1 = receiptDescriptionViewBean.getRadianPo().toString();
			 * 
			 * if
			 * ("IT".equalsIgnoreCase(receiptDescriptionViewBean.getDocType())
			 * ||
			 * "IA".equalsIgnoreCase(receiptDescriptionViewBean.getDocType())) {
			 * pw.addTdRegion("" ,mainRowSpan,1); } else { pw.addTdRegion(
			 * receiptDescriptionViewBean.getPoLine() ,mainRowSpan,1); }
			 */

			pw.addCell(receiptDescriptionViewBean.getRadianPo());
			pw.addCell(receiptDescriptionViewBean.getPoLine());
			pw.addCell(StringHandler.nullIfZero(receiptDescriptionViewBean.getTransferRequestId()));
			pw.addCell(receiptDescriptionViewBean.getPoNumber());
			pw.addCell(receiptDescriptionViewBean.getInventoryGroupName());
			pw.addCell(receiptDescriptionViewBean.getItemId());

			int kitCount = 0;
			Iterator kitIterator = receivingQcKitBeanCollection.iterator();
			while (kitIterator.hasNext()) {
				kitCount++;
				ReceiptDescriptionViewBean receiptDescriptionViewKitBean = (ReceiptDescriptionViewBean) kitIterator.next();;
				/*
				 * if (kitCount > 1 ) {//&& receivingQcKitBeanCollection.size()
				 * > 1) { pw.addRow();
				 * pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty
				 * (); }
				 */

				if ("N".equalsIgnoreCase(receiptDescriptionViewBean.getManageKitsAsSingleUnit())) {
					pw.addCell(receiptDescriptionViewKitBean.getPackaging());
					pw.addCell(receiptDescriptionViewKitBean.getMaterialDesc());
				}
				else {
					pw.addCell(receiptDescriptionViewBean.getPackaging());
					pw.addCell(receiptDescriptionViewBean.getLineDesc());
				}

				pw.addCell(receiptDescriptionViewKitBean.getMfgLot());
				pw.addCell(receiptDescriptionViewBean.getOrigMfgLot());
				pw.addCell(receiptDescriptionViewBean.getVendorShipDate());
				pw.addCell(receiptDescriptionViewBean.getDateOfReceipt());
				pw.addCell(receiptDescriptionViewBean.getDateOfManufacture());
				pw.addCell(receiptDescriptionViewBean.getDateOfRepackaging());
				pw.addCell(receiptDescriptionViewBean.getDateOfShipment());
				pw.addCell(receiptDescriptionViewBean.getExpireDate());

				pw.addCell(receiptDescriptionViewKitBean.getBin());
				if (!"Y".equalsIgnoreCase(receiptDescriptionViewBean.getMvItem())) {
					pw.addCell(receiptDescriptionViewKitBean.getQuantityReceived());
				}
				else {
					pw.addCell(receiptDescriptionViewBean.getTotalMvQuantityReceived());
				}
				pw.addCell(receiptDescriptionViewKitBean.getPackaging());
				pw.addCell(receiptDescriptionViewKitBean.getReceiptId());
				pw.addCell(receiptDescriptionViewBean.getTransferReceiptId());
				pw.addCell(
						receiptDescriptionViewKitBean.getQuantityReceived() + " X " + com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getCostFactor())
								+ " " + com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getPurchasingUnitOfMeasure()) + " "
								+ com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getDisplayPkgStyle()));
				pw.addCell(receiptDescriptionViewBean.getNotes());
				pw.addCell(receiptDescriptionViewBean.getDeliveryTicket());
				pw.addCell(receiptDescriptionViewBean.getQaStatement());
				pw.addCell(receiptDescriptionViewBean.getSupplierCageCode());
			}
		}
		return pw;
	}

	public void updateGHSLabelReqs(ReceiptDescriptionViewBean receipt) throws BaseException {

		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));

		try {
			StringBuilder query = new StringBuilder(" UPDATE receipt_ghs_label_reqs SET");
			query.append(" PRODUCT_NAME = '").append("true".equals(receipt.getGhsProductName()) ? "Y" : "N").append("',");
			query.append(" PICTOGRAM = '").append("true".equals(receipt.getGhsPictogram()) ? "Y" : "N").append("',");
			query.append(" SIGNAL_WORD = '").append("true".equals(receipt.getGhsSignalWord()) ? "Y" : "N").append("',");
			query.append(" HAZARD_STATEMENT  = '").append("true".equals(receipt.getGhsHazardStatement()) ? "Y" : "N").append("',");
			query.append(" PRECAUTIONARY_STATEMENT = '").append("true".equals(receipt.getGhsPrecautionaryStatement()) ? "Y" : "N").append("',");
			query.append(" SUPPLIER_INFO = '").append("true".equals(receipt.getGhsSupplierInfo()) ? "Y" : "N").append("'");
			query.append(" WHERE   receipt_id = " + receipt.getReceiptId());

			factory.deleteInsertUpdate(query.toString());
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
