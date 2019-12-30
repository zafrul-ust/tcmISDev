package com.tcmis.internal.hub.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.HubInventoryOwnerBean;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceiptDescDropshipViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.VvLotStatusBean;
import com.tcmis.internal.hub.factory.HubInventoryOwnerBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptDescriptionViewBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptDescDropshipViewBeanFactory;
/******************************************************************************
 * Process for receiving qc
 * @version 1.0
 *****************************************************************************/
public class DropshipReceivingProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public DropshipReceivingProcess(String client) {
	super(client);
 }
 
 public DropshipReceivingProcess(String client,String locale) {
	    super(client,locale);
 }

 /*public Collection flattenRelationship(ReceivingInputBean bean, Collection
	receivingQcViewBeanInputCollection, Collection
	receivingQcViewBeanCollection) {
	Collection finalNewBeanCollection = new Vector();

	if (bean.getSubmitReceive() != null &&
	bean.getSubmitReceive().trim().length() > 0) {

	Iterator finalIterator = receivingQcViewBeanCollection.iterator();
	 while (finalIterator.hasNext()) {
		ReceiptDescriptionViewBean currentReceiptDescriptionViewBean = (
		 ReceiptDescriptionViewBean) finalIterator.next(); ;

		Collection receivingKitBeanCollection =
			currentReceiptDescriptionViewBean.
			getKitCollection();

		Iterator kitIterator = receivingKitBeanCollection.iterator();
		while (kitIterator.hasNext()) {
		 ReceiptDescriptionViewBean receivingQcKitBean = (ReceiptDescriptionViewBean)
			kitIterator.next(); ;

		 Iterator finalKitIterator = receivingQcViewBeanInputCollection.iterator();
		 while (finalKitIterator.hasNext()) {
			{
			 ReceiptDescriptionViewBean finalBean = (ReceiptDescriptionViewBean)
			 finalKitIterator.next(); ;

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

 public HashMap receiveSelected(ReceivingInputBean inputBean,Collection
 receivingQcViewBeanCollection,	BigDecimal personnelId)
{
 Collection receivedQcReceiptsCollection = new Vector();
 Collection finalReceivedQcReceiptsCollection = new Vector();
 String labelReceipts = "";
 String errorMessage = "";

 if (inputBean.getSubmitReceive() != null &&
	inputBean.getSubmitReceive().trim().length() > 0) {

	 try {
		if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
		 receivedQcReceiptsCollection = receiveQcSelected(
			receivingQcViewBeanCollection, personnelId, false);
		}
		else {
		 receivedQcReceiptsCollection = receiveQcSelected(
			receivingQcViewBeanCollection, personnelId, true);
		}
	 }
	 catch (BaseException ex) {
	 }

	 Iterator mainIterator = receivedQcReceiptsCollection.iterator();
	 while (mainIterator.hasNext()) {
		ReceiptDescriptionViewBean currentReceiptDescriptionViewBean = (
		 ReceiptDescriptionViewBean) mainIterator.next(); ;

		labelReceipts += ""+currentReceiptDescriptionViewBean.getReceiptId()+",";

		Collection receivingQcKitBeanCollection =
		 currentReceiptDescriptionViewBean.
		 getKitCollection();

		String currentOk = currentReceiptDescriptionViewBean.getOk();
		String currentUpdateStatus = currentReceiptDescriptionViewBean.getUpdateStatus();
		boolean readOnly = false;
		if (currentUpdateStatus != null &&
		 "readOnly".equalsIgnoreCase(currentUpdateStatus))
		{
		 readOnly = true;
		}

		if ( !readOnly && (currentOk != null && currentOk.length() > 0) ||
		 receivingQcKitBeanCollection.size() > 1) {

		 Vector receiptQcKitVector = new Vector();
		 Iterator kitIterator = receivingQcKitBeanCollection.iterator();
		 while (kitIterator.hasNext()) {
			ReceiptDescriptionViewBean receivingQcKitBean = (
			 ReceiptDescriptionViewBean)
			 kitIterator.next(); ;

			String currentKitUpdate = receivingQcKitBean.getUpdateStatus();
			String currentKitOk = receivingQcKitBean.getOk();
			String currentMvItem = receivingQcKitBean.getMvItem();

			//log.info("Result currentKitOk " + currentKitOk + " currentKitUpdate " + currentKitUpdate + "");
			if ("Y".equalsIgnoreCase(currentMvItem)) {
			 if (currentOk != null && currentOk.length() > 0) {
				if (currentKitUpdate != null ) {
				 if (currentKitUpdate != null &&
					"Error".equalsIgnoreCase(currentKitUpdate)) {
					errorMessage += " " +
					 receivingQcKitBean.getErrorMessage();
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
			else if ( currentKitOk != null && currentKitOk.length() > 0) {
			 if (currentKitUpdate != null ) {
				if (currentKitUpdate != null &&
				 "Error".equalsIgnoreCase(currentKitUpdate)) {
				 errorMessage += " " +
					receivingQcKitBean.getErrorMessage();
				 receiptQcKitVector.add(receivingQcKitBean);
				}
			 }
			}
			else {
			 receiptQcKitVector.add(receivingQcKitBean);
			}
		 }

		 if (receiptQcKitVector.size() > 0) {
			currentReceiptDescriptionViewBean.setKitCollection( (Vector)
			 receiptQcKitVector.clone());
			currentReceiptDescriptionViewBean.setRowSpan(new BigDecimal(
			 receiptQcKitVector.size()));

			finalReceivedQcReceiptsCollection.add(currentReceiptDescriptionViewBean);
		 }
		}
		else {
		 finalReceivedQcReceiptsCollection.add(currentReceiptDescriptionViewBean);
		}
	 }
	}
  else {
	 //Build the labels here
	 finalReceivedQcReceiptsCollection = receivingQcViewBeanCollection;
	}

	HashMap result = new HashMap();
	result.put("ERRORMESSAGE", errorMessage);
	result.put("RECEIVEDQCCOLLECTION", finalReceivedQcReceiptsCollection);
	return result;
}

 public Collection copyAttributes(ReceivingInputBean bean,Collection
	receivingQcViewBeanCollection,Vector savedBeanVector,Collection vvLotStatusBeanCollection) {
	//int kitSize = 0;
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
		 String rowid = (String) inputBean.getOk();
		 savedBean.setOk(rowid);
		 //log.info("rowid     " + rowid + "");
		}
		else {
		 savedBean.setOk(null);
		}

		savedBean.setRowNumber(inputBean.getRowNumber());
		savedBean.setUpdateStatus(inputBean.getUpdateStatus());
		receivingQcViewBeanColl.add(savedBean);
	 }
	 else
	 {
		BigDecimal receiptId = new BigDecimal(com.tcmis.common.util.
		 StringHandler.emptyIfNull(inputBean.getReceiptId()));

		//log.info("Receiving Saved Receipt ID "+savedBean.getReceiptId()+" Receipt "+receiptId+" Lot "+inputBean.getMfgLot()+"  DOR "+inputBean.getDateOfReceipt()+"");
		if ( receiptId.equals(savedBean.getReceiptId())) {
		 if (inputBean.getOk() != null) {
			String rowid = (String) inputBean.getOk();
			savedBean.setOk(rowid);
			//log.info("rowid     " + rowid + "");
		 }
		 else {
			savedBean.setOk(null);
		 }

		 if (bean.getCategory().equalsIgnoreCase("chemicals")) {
			savedBean.setMfgLot(inputBean.getMfgLot());
			savedBean.setLotStatus(inputBean.getLotStatus());

			if (savedBean.getQualityControlItem() != null &&
			 "Y".equalsIgnoreCase(savedBean.getQualityControlItem().trim())) {
			 Iterator vvLotStatusIterator = vvLotStatusBeanCollection.iterator();
			 while (vvLotStatusIterator.hasNext()) {
				VvLotStatusBean vvLotStatusBean = (
				 VvLotStatusBean) vvLotStatusIterator.next(); ;

				String lotStatus = vvLotStatusBean.getLotStatus();
				if (lotStatus.equalsIgnoreCase(savedBean.getLotStatus())) {
				 String pickable = vvLotStatusBean.getPickable();
				 if ("Y".equalsIgnoreCase(pickable)) {
					savedBean.setCertificationUpdate("Yes");
				 }
				}
			 }
			}

			savedBean.setVendorShipDate(inputBean.getVendorShipDate());
			savedBean.setDateOfReceipt(inputBean.getDateOfReceipt());
			savedBean.setDateOfManufacture(inputBean.getDateOfManufacture());
			savedBean.setDateOfShipment(inputBean.getDateOfShipment());
			savedBean.setExpireDate(inputBean.getExpireDate());
			savedBean.setLabelQuantity(inputBean.getLabelQuantity());
			savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
			savedBean.setNotes(inputBean.getNotes());
			savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
			savedBean.setLotStatusRootCause(inputBean.getLotStatusRootCause());
			savedBean.setLotStatusRootCauseNotes(inputBean.getLotStatusRootCauseNotes());
			savedBean.setResponsibleCompanyId(inputBean.getResponsibleCompanyId());
			savedBean.setRowSpan(inputBean.getRowSpan());
			savedBean.setClosePoLine(inputBean.getClosePoLine());
		 }
		 else {
			savedBean.setNotes(inputBean.getNotes());
			savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
		 }
		 savedBean.setRowNumber(inputBean.getRowNumber());
		 //log.info("rowNumber  "+savedBean.getRowNumber()+"");

		 receivingQcViewBeanColl.add(savedBean);
		}
	 }
	 count++;
	}
	return receivingQcViewBeanColl;
 }*/

 public String receiptsToLabelList(Collection receivingQcViewBeanCollection) {
	Iterator mainIterator = receivingQcViewBeanCollection.iterator();
	String labelReceipts = "";
	while (mainIterator.hasNext()) {
	 ReceiptDescDropshipViewBean receiptDescDropshipViewBean = (
		ReceiptDescDropshipViewBean) mainIterator.next(); ;

	 /*if ("Y".equalsIgnoreCase(receiptDescriptionViewBean.getMvItem())) {
		BigDecimal pendingReceipt = receiptDescriptionViewBean.getPendingReceiptId();
		labelReceipts += "" + pendingReceipt + ",";
	 }
	 else */{
		BigDecimal receivedReceipt = receiptDescDropshipViewBean.getReceiptId();
		labelReceipts += "" + receivedReceipt + ",";
	 }
	}

	if (labelReceipts.length() > 1) {
	 labelReceipts = labelReceipts.substring(0,
		(labelReceipts.length() - 1));
	}

	return labelReceipts;
 }

 /*public Collection createRelationalObject(Collection
	receivingQcViewBeanCollection) {
	Collection finalreceivingQcViewBeanCollection = new Vector();
	String nextReceiptId = "";
  String nextReceiptGroup = "";
	//String nextMvItem = "";

	int sameReceiptIdCount = 0;
	BigDecimal totalMvQuantityReceived = new BigDecimal("0");
	Vector collectionVector = new Vector(receivingQcViewBeanCollection);
	Vector receiptKitVector = new Vector();

	for (int loop = 0; loop < collectionVector.size(); loop++) {
	 ReceiptDescriptionViewBean currentReceiptDescriptionViewBean = (
		ReceiptDescriptionViewBean) collectionVector.elementAt(loop);

	 String currentReceiptId = "" +
		currentReceiptDescriptionViewBean.getReceiptId() + "";
	 String currentReceiptGroup = "" +
	 	currentReceiptDescriptionViewBean.getReceiptGroup() + "";
	 //String currentMvItem = currentReceiptDescriptionViewBean.getMvItem();

	 if (! ( (loop + 1) == collectionVector.size())) {
		ReceiptDescriptionViewBean nextReceiptDescriptionViewBean = (
		 ReceiptDescriptionViewBean) collectionVector.elementAt(loop + 1);

		nextReceiptId = "" + nextReceiptDescriptionViewBean.getReceiptId() + "";
		nextReceiptGroup = "" + nextReceiptDescriptionViewBean.getReceiptGroup() + "";
		//nextMvItem = nextReceiptDescriptionViewBean.getMvItem();
	 }
	 else {
		nextReceiptId = "";
		nextReceiptGroup = "";
	 }

	 boolean sameReceiptId = false;
	 if (currentReceiptId.equalsIgnoreCase(nextReceiptId) ||
		("Y".equalsIgnoreCase(currentReceiptDescriptionViewBean.getMvItem()) &&
		currentReceiptGroup.equalsIgnoreCase(nextReceiptGroup))) {
		sameReceiptId = true;
		sameReceiptIdCount++;
	 }
	 //log.info("sameReceiptId "+sameReceiptId+" Receiving "+currentReceiptDescriptionViewBean.getOk()+" currentReceiptGroup  "+currentReceiptGroup+"  nextReceiptGroup "+nextReceiptGroup+" Receipt ID "+currentReceiptDescriptionViewBean.getReceiptId()+" Next Receipt "+nextReceiptId+"  Lot "+currentReceiptDescriptionViewBean.getMfgLot()+"  DOR "+currentReceiptDescriptionViewBean.getDateOfReceipt()+"");

	 receiptKitVector.add(currentReceiptDescriptionViewBean);
	 if ("Y".equalsIgnoreCase(currentReceiptDescriptionViewBean.getMvItem()))
	 {
	 totalMvQuantityReceived = totalMvQuantityReceived.add(
		currentReceiptDescriptionViewBean.getQuantityReceived().multiply(
		currentReceiptDescriptionViewBean.getCostFactor()));
	 }

	 if (sameReceiptId) {

	 }
	 else {
		currentReceiptDescriptionViewBean.setKitCollection( (Vector)
		 receiptKitVector.clone());
		receiptKitVector = new Vector();
		currentReceiptDescriptionViewBean.setRowSpan(new BigDecimal(
		 sameReceiptIdCount + 1));
		currentReceiptDescriptionViewBean.setTotalMvQuantityReceived(totalMvQuantityReceived);
		finalreceivingQcViewBeanCollection.add(currentReceiptDescriptionViewBean);

		sameReceiptIdCount = 0;
		totalMvQuantityReceived = new BigDecimal("0");
	 }
	}

	//log.info("Final collectionSize here " +finalreceivingQcViewBeanCollection.size() + "");
	return finalreceivingQcViewBeanCollection;
 }*/

 /*public Collection receiveQcSelected(Collection receivingViewBeanCollection,
	BigDecimal personnelId, boolean nonChemicalreceipt) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	ReceiptDescriptionViewBeanFactory factory = new
	 ReceiptDescriptionViewBeanFactory(dbManager);

	Iterator mainIterator = receivingViewBeanCollection.iterator();
	while (mainIterator.hasNext()) {
	 ReceiptDescriptionViewBean currentReceiptDescriptionViewBean = (
		ReceiptDescriptionViewBean) mainIterator.next(); ;
	 String currentReceiptId = "" +
		currentReceiptDescriptionViewBean.getReceiptId() + "";
	 String currentOk = currentReceiptDescriptionViewBean.getOk();
	 String currentMvItem = currentReceiptDescriptionViewBean.getMvItem();
	 String currentmanageKitAsSingle = currentReceiptDescriptionViewBean.
		getManageKitsAsSingleUnit();
	 String currentUpdateStatus = currentReceiptDescriptionViewBean.getUpdateStatus();
	 boolean readOnly = false;
	 if (currentUpdateStatus != null &&
		"readOnly".equalsIgnoreCase(currentUpdateStatus))
	 {
		readOnly = true;
	 }

	 Collection receiptKitBeanCollection = currentReceiptDescriptionViewBean.
		getKitCollection();

	 if ( !readOnly && ( (currentOk != null &&
		currentOk.length() > 0) ||
		receiptKitBeanCollection.size() > 1)) {
		Iterator kitIterator = receiptKitBeanCollection.iterator();
		int kitCount = 0;
		Collection result = null;
		boolean receiptSuccess = true;
		boolean receiptKitSuccess = true;
		while (kitIterator.hasNext()) {
		 ReceiptDescriptionViewBean receiptKitBean = (ReceiptDescriptionViewBean)
			kitIterator.next(); ;
		 //log.info("currentMvItem  "+currentMvItem+" currentReceiptId  "+receiptKitBean.getReceiptId()+"");
		 String currentKitOk = receiptKitBean.getOk();
		 if ( (kitCount == 0) || (!("N".equalsIgnoreCase(currentmanageKitAsSingle)) && (currentKitOk != null &&
			currentKitOk.length() > 0)) || "Y".equalsIgnoreCase(currentMvItem) ) {
			//Call p_receipt_qc

			String lotStatus = "";
			if (receiptKitBean.getLotStatus() != null &&
			 receiptKitBean.getLotStatus().trim().length() > 0) {
			 lotStatus = new String(receiptKitBean.getLotStatus());
			}

			if ("Customer Purchase".equalsIgnoreCase(lotStatus) ||
			 "Write Off Requested".equalsIgnoreCase(lotStatus) ||
			 "3PL Purchase".equalsIgnoreCase(lotStatus)) {
			 if ( (receiptKitBean.getLotStatusRootCause() != null &&
				receiptKitBean.getLotStatusRootCause().trim().length() > 0) ||
				(receiptKitBean.getResponsibleCompanyId() != null &&
				receiptKitBean.getResponsibleCompanyId().trim().length() > 0)) {
				receiptSuccess = false;
				currentReceiptDescriptionViewBean.setErrorMessage(
				 "Please choose the Root Cause and Root Cause Company for Receipt ID :" +
				 currentReceiptDescriptionViewBean.getReceiptId() + ". ");
				currentReceiptDescriptionViewBean.setUpdateStatus("Error");
				mainIterator.next();
			 }
			}

			try {
			 BigDecimal quantityReceived = currentReceiptDescriptionViewBean.
				getQuantityReceived();
			 if ( (currentKitOk != null && currentKitOk.length() > 0 &&
				quantityReceived.intValue() > 0) ||
				("Y".equalsIgnoreCase(currentMvItem) && (currentOk != null &&
				currentOk.length() > 0))) {
				log.info("Receiving QC currentOk " + currentOk + " ReceiptId " +
				 currentReceiptId + " Qty " + receiptKitBean.getQuantityReceived() +
				 " Lot " + receiptKitBean.getMfgLot() + "  Status " +
				 currentReceiptDescriptionViewBean.getLotStatus() + "");
				/*log.info("RootCause  " + receiptKitBean.getLotStatusRootCause() +
				 " RootCauseNotes " + receiptKitBean.getLotStatusRootCauseNotes() +
				 " ResponsibleCompanyId " + receiptKitBean.getResponsibleCompanyId() +
				 "");*/
				//log.info("Calling P_RECEIPT_QC");
				/*result = factory.receiptQc(currentReceiptDescriptionViewBean,
				 receiptKitBean, personnelId, nonChemicalreceipt);
			 }
			 receiptKitBean.setUpdateStatus("Ok");
			}
			catch (Exception ex) {
			 ex.printStackTrace();
			 currentReceiptDescriptionViewBean.setErrorMessage(com.tcmis.common.util.
				StringHandler.emptyIfNull(ex.getMessage()));
			 currentReceiptDescriptionViewBean.setUpdateStatus("Error");
			 receiptSuccess = false;
			 if (kitCount > 0) {
				receiptKitBean.setErrorMessage(com.tcmis.common.util.StringHandler.
				 emptyIfNull(ex.getMessage()));
				receiptKitBean.setUpdateStatus("Error");
				receiptKitSuccess = false;
			 }
			}

			String errorCode = "";
			if (result != null) {
			 Iterator resultIterator = result.iterator();
			 while (resultIterator.hasNext()) {
				errorCode = (String) resultIterator.next(); ;
			 }

			 if (!"OK".equalsIgnoreCase(errorCode)) {
				log.info("P_RECEIPT_QC errorCode " + errorCode + "");
				currentReceiptDescriptionViewBean.setErrorMessage(" " + errorCode + " ");
				receiptKitBean.setErrorMessage(" " + errorCode + " ");
				receiptKitBean.setUpdateStatus("Error");
				receiptKitSuccess = false;
				receiptSuccess = false;
			 }
			}
		 }

		 if ("N".equalsIgnoreCase(currentmanageKitAsSingle) && (currentOk != null &&
			currentOk.length() > 0)) {
			//Call p_receipt_component
			if (receiptSuccess) {
			 log.info("Calling p_receipt_component for COMPONENT_ID " +
				currentReceiptDescriptionViewBean.getComponentId() + " ReceiptId " +
				currentReceiptDescriptionViewBean.getReceiptId() + " Personnel ID  " +
				personnelId + "");
			 try {
				receiptSuccess = factory.insertReceiptComponenet(receiptKitBean,
				 currentReceiptDescriptionViewBean.getReceiptId(), personnelId);
				receiptKitBean.setUpdateStatus("Ok");
			 }
			 catch (Exception ex1) {
				receiptSuccess = false;
				receiptKitBean.setUpdateStatus("Error");
				receiptKitBean.setErrorMessage(
				 "Error Calling p_receipt_component for COMPONENT_ID " +
				 currentReceiptDescriptionViewBean.getComponentId() + " ReceiptId " +
				 currentReceiptDescriptionViewBean.getReceiptId() + ". ");
				receiptKitSuccess = false;
			 }
			}
		 }
		 /*else if ("Y".equalsIgnoreCase(currentMvItem) && (currentOk != null &&
			currentOk.length() > 0)) {
			//Call p_receipt_insert_mv
			if (receiptSuccess) {
			 log.info("Calling p_receipt_insert_mv for  MV Item " +
				currentReceiptDescriptionViewBean.getItemId() + " ReceiptId " +
				currentReceiptDescriptionViewBean.getReceiptId() + " Personnel ID  " +
				personnelId + "");
			 try {
				receiptSuccess = factory.insertReceiptMv(receiptKitBean,
				 currentReceiptDescriptionViewBean.getReceiptId(), personnelId);
				receiptKitBean.setUpdateStatus("Ok");
			 }
			 catch (Exception ex1) {
				receiptSuccess = false;
				receiptKitBean.setUpdateStatus("Error");
				receiptKitBean.setErrorMessage(
				 "Error Calling p_receipt_insert_mv for  MV Item " +
				 currentReceiptDescriptionViewBean.getItemId() + " ReceiptId " +
				 currentReceiptDescriptionViewBean.getReceiptId() + ". ");
				receiptKitSuccess = false;
			 }
			}
		 }*/

		 /*kitCount++;
		 //log.info("Here kitCount  " + kitCount + " receiptSuccess  " + receiptSuccess + " receiptKitSuccess " + receiptKitSuccess + "");
		}

		if ( (nonChemicalreceipt &&
		 ! ("SEAGATE".equalsIgnoreCase(currentReceiptDescriptionViewBean.
		 getCompanyId()))) && (currentOk != null && currentOk.length() > 0)) {
		 NonChemicalReceivingQcProcess nonChemicalReceivingQcProcess = new
			NonChemicalReceivingQcProcess(this.getClient());
		 // Create MR and issue it out
		 HashMap createMrResult = new HashMap();
		 try {
			createMrResult = nonChemicalReceivingQcProcess.createMaterialRequest(currentReceiptDescriptionViewBean,
			 personnelId);

			Boolean createMrSuccess = (Boolean) createMrResult.get("SUCESS");
			if (!createMrSuccess.booleanValue()) {
			 currentReceiptDescriptionViewBean.setErrorMessage( (String)
				createMrResult.get("ERROR"));
			 //currentReceiptDescriptionViewBean.setUpdateStatus("Error");
			 receiptSuccess = false;
			}
		 }
		 catch (Exception ex2) {
			ex2.printStackTrace();
			currentReceiptDescriptionViewBean.setErrorMessage(
			 "Error in Creating MR for Receipt ID  " +
			 currentReceiptDescriptionViewBean.getReceiptId() + ". ");
			receiptSuccess = false;
		 }
		}
		else if (!nonChemicalreceipt && currentOk != null && currentOk.length() > 0) {
		 if ("N".equalsIgnoreCase(currentReceiptDescriptionViewBean.getConsignedPo())) {
			HashMap converTReceiptResult = new HashMap();
			try {
			 converTReceiptResult = convertReceiptToCustomerOwened(
				currentReceiptDescriptionViewBean);

			 Boolean converReceiptSuccess = (Boolean) converTReceiptResult.get(
				"SUCESS");
			 if (!converReceiptSuccess.booleanValue()) {
				currentReceiptDescriptionViewBean.setErrorMessage( (String)
				 converTReceiptResult.get("ERROR"));
				//currentReceiptDescriptionViewBean.setUpdateStatus("Error");
				receiptSuccess = false;
			 }
			}
			catch (Exception ex4) {
			 ex4.printStackTrace();
			 currentReceiptDescriptionViewBean.setErrorMessage(
				"Error in Converting Receipt ID  " +
				currentReceiptDescriptionViewBean.getReceiptId() +
				" to Coustomer Owened. ");
			 receiptSuccess = false;
			}
		 }

		 // call P_RECEIPT_ALLOCATE
		 ReceiptBeanFactory receiptBeanFactory = new ReceiptBeanFactory(dbManager);
		 try {
			log.info("call P_RECEIPT_ALLOCATE " +
			 currentReceiptDescriptionViewBean.getReceiptId() + "");
			receiptBeanFactory.allocateReceipt(currentReceiptDescriptionViewBean);
		 }
		 catch (Exception ex3) {
			ex3.printStackTrace();
			currentReceiptDescriptionViewBean.setErrorMessage(
			 "Error Calling P_RECEIPT_ALLOCATE for Receipt ID:" +
			 currentReceiptDescriptionViewBean.getReceiptId() + "");
			receiptSuccess = false;
			//currentReceiptDescriptionViewBean.setUpdateStatus("Error");
		 }
		}

		//log.info("Here kitCount  " + kitCount + " receiptSuccess " + receiptSuccess + "");
		if (receiptSuccess) {
		 currentReceiptDescriptionViewBean.setUpdateStatus("OK");
		}
		else {
		 //Update receipt to approved = 'N'
		 ReceiptBeanFactory receiptBeanFactory = new ReceiptBeanFactory(dbManager);

		 SearchCriteria criteria = new SearchCriteria();
		 criteria.addCriterion("receiptId", SearchCriterion.IN, currentReceiptId);
		 try {
			receiptBeanFactory.updateApprovedStatus("N", personnelId, criteria);
		 }
		 catch (BaseException ex5) {
			ex5.printStackTrace();
		 }
		 currentReceiptDescriptionViewBean.setUpdateStatus("Error");
		}
	 }
	}
	//log.info("Final collectionSize here " + receivingViewBeanCollection.size() + "");
	return receivingViewBeanCollection;
 }

 public boolean reverseReceipt(BigDecimal receiptId) throws Exception {
	boolean reverseReceiptSuccess = true;
	DbManager dbManager = new DbManager(getClient(),getLocale());
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

 public HashMap convertReceiptToCustomerOwened(ReceiptDescriptionViewBean bean) throws
	Exception {
	boolean converReceiptSuccess = true;
	String errorMessage = "";
	Collection resultColl = null;
	DbManager dbManager = new DbManager(getClient(),getLocale());

	HubInventoryOwnerBeanFactory hubInventoryOwnerBeanFactory = new
	 HubInventoryOwnerBeanFactory(dbManager);

	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getBranchPlant());
	criteria.addCriterion("status", SearchCriterion.EQUALS, "A");

	Collection hubInventoryOwnerBeanColl = hubInventoryOwnerBeanFactory.
	 selectDistinct(criteria);

	if (hubInventoryOwnerBeanColl.size() > 0) {
	 String coustomerOwnedCompanyId = "";
	 Iterator hubInventoryOwnerIterator = hubInventoryOwnerBeanColl.iterator();
	 while (hubInventoryOwnerIterator.hasNext()) {
		HubInventoryOwnerBean hubInventoryOwnerBean = (HubInventoryOwnerBean)
		 hubInventoryOwnerIterator.next(); ;

		coustomerOwnedCompanyId = hubInventoryOwnerBean.getCompanyId();
	 }
	 //Call P_CONVERT_RECEIPT_TO_CUSTOMER
	 try {
		resultColl = hubInventoryOwnerBeanFactory.convertReceiptToCustomerOwened(
		 bean.getReceiptId(), coustomerOwnedCompanyId);
	 }
	 catch (Exception ex2) {
		ex2.printStackTrace();
		errorMessage = "Convert Receipt to Customer Failed for Receipt ID :" +
		 bean.getReceiptId() + ". ";
		converReceiptSuccess = false;
		//bean.setUpdateStatus("Error");
	 }

	 if (resultColl != null) {
		Iterator resultIterator = resultColl.iterator();
		int resultCount = 0;
		String errorCode = "";

		while (resultIterator.hasNext()) {
		 if (resultCount == 2) {
			try {
			 errorCode = (String) resultIterator.next(); ;
			}
			catch (Exception ex) {
			 ex.printStackTrace();
			 errorCode = "";
			}
		 }
		 else
		 {
			resultIterator.next();
		 }
		 resultCount++;
		}

		if (errorCode != null && errorCode.trim().length() > 0) {
		 log.info("P_CONVERT_RECEIPT_TO_CUSTOMER errorCode " + errorCode + "");
		 errorMessage = "Convert Receipt to Customer Failed for Receipt ID :" +
			bean.getReceiptId() + "   Msg" + errorCode + ". ";
		 //bean.setUpdateStatus("Error");
		 converReceiptSuccess = false;
		}
	 }
	 else {
		errorMessage = "Convert Receipt to Customer Failed for Receipt ID :" +
		 bean.getReceiptId() + ". ";
		converReceiptSuccess = false;
	 }
	}

	HashMap result = new HashMap();
	result.put("ERROR", errorMessage);
	result.put("SUCESS", new Boolean(converReceiptSuccess));
	return result;
 }*/

 public Collection getChemicalResult(String receivedReceipts) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	ReceiptDescDropshipViewBeanFactory factory = new
	 ReceiptDescDropshipViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	/*if (hubNumber!=null && hubNumber.length() > 0) {
	 criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, hubNumber);

	 if (!justReceived)
	 {
		criteria.addCriterion("approved", SearchCriterion.EQUALS, "N");
	 }
	}*/

	/*HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl,
	 hubNumber, inventoryGroup);

	if (inventoryGroup != null &&
	 !inventoryGroup.equalsIgnoreCase("ALL")) {

	 if (iscollection) {

	 }
	 else if (inventoryGroup.length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 inventoryGroup);
	 }
	}*/

	/*if (inventoryGroup!=null && !inventoryGroup.equalsIgnoreCase("ALL") && inventoryGroup.length() > 0) {
	 criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		inventoryGroup);
	}*/

	if (receivedReceipts!=null && receivedReceipts.length() > 0) {
	 criteria.addCriterion("receiptId", SearchCriterion.IN, receivedReceipts);
	}

	/*ReceivingInputBean inputBean = new ReceivingInputBean();
	inputBean.setInventoryGroup(inventoryGroup);
	inputBean.setSourceHub(hubNumber);
	inputBean.setSort("Receipt ID");*/
	return factory.select(criteria);
 }

 /*public Collection getChemicalResult(ReceivingInputBean bean,Collection hubInventoryGroupOvBeanColl) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	ReceiptDescriptionViewBeanFactory factory = new
	 ReceiptDescriptionViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("branchPlant", SearchCriterion.EQUALS,
	 bean.getSourceHub());

	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl,
	 bean.getSourceHub(), bean.getInventoryGroup());

	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

	 if (iscollection) {

	 }
	 else if (bean.getInventoryGroup().length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 bean.getInventoryGroup());
	 }
	}

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

	 String searchWhat = bean.getSearchWhat();
	 //check what to search by
	 if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("PO")) {
		criteria.addCriterion("radianPo", criterion, bean.getSearch());
	 }
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("Receipt Id")) {
		criteria.addCriterion("receiptId", criterion, bean.getSearch());
	 }
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("ITEM DESC")) {
		criteria.addCriterion("lineDesc", criterion, bean.getSearch(),
		 SearchCriterion.IGNORE_CASE);
	 }
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("ITEM ID")) {
		criteria.addCriterion("itemId", criterion, bean.getSearch());
	 }
	}

	criteria.addCriterion("approved", SearchCriterion.EQUALS, "N");

	return factory.selectSorted(criteria, bean,iscollection);
 }

 public void writeExcelFile(ReceivingInputBean headerBean,
	Collection searchCollection, String filePath) throws
	BaseException, Exception {

	PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
	pw.println("<html>");

	pw.println("<TABLE BORDER=\"1\">");
	pw.println("<TR>");
	pw.println("<TD>");
	pw.println("<B>Hub:</B>");
	pw.println("</TD>");

	pw.println("<TD>");
	pw.println("" + headerBean.getSourceHubName() + "");
	pw.println("</TD>");

	pw.println("<TD>");
	pw.println("<B>Category:</B>");
	pw.println("</TD>");

	pw.println("<TD>");
	pw.println("" + headerBean.getCategory() + "");
	pw.println("</TD>");

	pw.println("</TR>");
	pw.println("<TR>");

	pw.println("<TD>");
	pw.println("<B>Inven Grp:</B>");
	pw.println("</TD>");

	pw.println("<TD>");
	pw.println("" + headerBean.getInventoryGroup() + "");
	pw.println("</TD>");

	pw.println("<TD>");
	pw.println("<B>Search:</B>");
	pw.println("</TD>");

	pw.println("<TD>");
	pw.println("" + headerBean.getSearchWhat() + " " + headerBean.getSearchType() +
	 "  " + headerBean.getSearch() + "");
	pw.println("</TD>");

	pw.println("</TR>");

	pw.println("<TR>");
	pw.println("</TR>");
	pw.println("<TR>");
	pw.println("</TR>");

	pw.println("</TABLE>");

	pw.println("<table border=\"1\">");
	pw.println("<TR>");
	pw.println("<TH width=\"2%\">PO</TH>");
	pw.println("<TH width=\"5%\">PO Line</TH>");
	pw.println("<TH width=\"5%\">Inventory Group</TH>");
	pw.println("<TH width=\"5%\">Item</TH>");
	pw.println("<TH width=\"5%\">Packaging</TH>");
	pw.println("<TH width=\"7%\">Description</TH>");
	pw.println("<TH width=\"7%\">Mfg Lot</TH>");
	pw.println("<TH width=\"7%\">Original Mfg Lot</TH>");
	pw.println("<TH width=\"7%\">Act Supp Ship Date</TH>");
	pw.println("<TH width=\"7%\">DOR</TH>");
	pw.println("<TH width=\"7%\">DOM</TH>");
	pw.println("<TH width=\"7%\">Manufacturer DOS</TH>");
	pw.println("<TH width=\"7%\">Exp Date</TH>");
	pw.println("<TH width=\"7%\">Bin</TH>");
	pw.println("<TH width=\"7%\">Qty Rec'd</TH>");
	pw.println("<TH width=\"7%\">UOM</TH>");
	pw.println("<TH width=\"7%\">Receipt ID</TH>");
	pw.println("<TH width=\"7%\">Transfer Receipt ID</TH>");
	pw.println("<TH width=\"7%\">Packaged Qty x Packaged Size</TH>");
	pw.println("<TH width=\"7%\">Notes</TH>");
	pw.println("<TH width=\"7%\">Delivery Ticket</TH>");
	pw.println("</TR>");

	//print rows
	Iterator mainIterator = searchCollection.iterator();
	while (mainIterator.hasNext()) {
	 pw.println("<TR>");

	 ReceiptDescriptionViewBean
		receiptDescriptionViewBean = (
		ReceiptDescriptionViewBean) mainIterator.next(); ;

	 int mainRowSpan = receiptDescriptionViewBean.getRowSpan().
		intValue();
	 Collection receivingQcKitBeanCollection = receiptDescriptionViewBean.
		getKitCollection();

	 if ("IT".equalsIgnoreCase(receiptDescriptionViewBean.getDocType())) {
		pw.println("<TD ROWSPAN=" + mainRowSpan + ">TR " +
		 receiptDescriptionViewBean.getTransferRequestId() + "</TD>");
	 }
	 if ("IA".equalsIgnoreCase(receiptDescriptionViewBean.getDocType())) {
		pw.println("<TD ROWSPAN=" + mainRowSpan + ">TR " +
		 receiptDescriptionViewBean.getReturnPrNumber() + " - " +
		 receiptDescriptionViewBean.getReturnLineItem() + "</TD>");
	 }
	 else {
		pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		 receiptDescriptionViewBean.getRadianPo() + "</TD>");
	 }

	 if ("IT".equalsIgnoreCase(receiptDescriptionViewBean.getDocType()) ||
		"IA".equalsIgnoreCase(receiptDescriptionViewBean.getDocType())) {
		pw.println("<TD ROWSPAN=" + mainRowSpan + ">&nbsp;</TD>");
	 }
	 else {
		pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		 receiptDescriptionViewBean.getPoLine() + "</TD>");
	 }

	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		receiptDescriptionViewBean.getInventoryGroupName() +
		"</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		receiptDescriptionViewBean.getItemId() +
		"</TD>");

	 int kitCount = 0;
	 Iterator kitIterator = receivingQcKitBeanCollection.iterator();
	 while (kitIterator.hasNext()) {
		kitCount++;
		ReceiptDescriptionViewBean receiptDescriptionViewKitBean = (
		 ReceiptDescriptionViewBean) kitIterator.next(); ;

		if (kitCount > 1 && receivingQcKitBeanCollection.size() > 1) {
		 pw.println("<TR>");
		}

		if (receivingQcKitBeanCollection.size() > 1 &&
		 "N".equalsIgnoreCase(receiptDescriptionViewBean.getManageKitsAsSingleUnit())) {
		 pw.println("<TD>" +
			receiptDescriptionViewKitBean.getPackaging() +
			"</TD>");
		 pw.println("<TD>" +
			receiptDescriptionViewKitBean.getMaterialDesc() +
			"</TD>");
		}
		else if (!"N".equalsIgnoreCase(receiptDescriptionViewBean.
		 getManageKitsAsSingleUnit()) &&
		 kitCount == 1) {
		 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			receiptDescriptionViewBean.getPackaging() + "</TD>");

		 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			receiptDescriptionViewBean.getLineDesc() + "</TD>");
		}

		if (!"Y".equalsIgnoreCase(receiptDescriptionViewBean.getMvItem())) {
		 pw.println("<TD>" +
			receiptDescriptionViewKitBean.getMfgLot() + "</TD>");
		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getOrigMfgLot()) + "</TD>");

		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getVendorShipDate()) + "</TD>");
		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getDateOfReceipt()) + "</TD>");
		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getDateOfManufacture()) + "</TD>");
		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getDateOfShipment()) + "</TD>");
		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewKitBean.getExpireDate()) + "</TD>");
		 pw.println("<TD>" +
			receiptDescriptionViewKitBean.getBin() + "</TD>");

		 if (kitCount == 1 &&
			"N".equalsIgnoreCase(receiptDescriptionViewBean.getManageKitsAsSingleUnit())) {
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 receiptDescriptionViewKitBean.getQuantityReceived() + "</TD>");
			pw.println("<TD>" +
			 receiptDescriptionViewKitBean.getPackaging() + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 receiptDescriptionViewKitBean.getReceiptId() + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getTransferReceiptId()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getNotes()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getDeliveryTicket()) + "</TD>");
		 }
		 if (kitCount > 1 &&
			"N".equalsIgnoreCase(receiptDescriptionViewBean.getManageKitsAsSingleUnit())) {
			pw.println("<TD>" +
			 receiptDescriptionViewKitBean.getPackaging() + "</TD>");
		 }
		 else if (!"N".equalsIgnoreCase(receiptDescriptionViewBean.
			getManageKitsAsSingleUnit())) {
			pw.println("<TD>" +
			 receiptDescriptionViewKitBean.getQuantityReceived() + "</TD>");
			pw.println("<TD>" +
			 receiptDescriptionViewKitBean.getPackaging() + "</TD>");
			pw.println("<TD>" +
			 receiptDescriptionViewKitBean.getReceiptId() + "</TD>");
			pw.println("<TD>" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getTransferReceiptId()) + "</TD>");
			pw.println("<TD>" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getNotes()) + "</TD>");
			pw.println("<TD>" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getDeliveryTicket()) + "</TD>");
		 }
		}
		else { //variable packaging stuff
		 if (kitCount == 1) {
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 receiptDescriptionViewBean.getMfgLot() + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getOrigMfgLot()) + "</TD>");

			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getVendorShipDate()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getDateOfReceipt()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getDateOfManufacture()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getDateOfShipment()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getExpireDate()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 receiptDescriptionViewBean.getBin() + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 receiptDescriptionViewBean.getTotalMvQuantityReceived() + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 receiptDescriptionViewBean.getPackaging() + "</TD>");
		 }

		 pw.println("<TD>" +
			receiptDescriptionViewKitBean.getReceiptId() + "</TD>");
		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getTransferReceiptId()) + "</TD>");

		 pw.println("<TD>" +
			receiptDescriptionViewKitBean.getQuantityReceived() + " X " +
			receiptDescriptionViewKitBean.getCostFactor() + " " +
			receiptDescriptionViewKitBean.getPurchasingUnitOfMeasure() + " " +
			receiptDescriptionViewKitBean.getDisplayPkgStyle() + "</TD>");

		 if (kitCount == 1) {
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getNotes()) + "</TD>");
			pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
			 com.tcmis.common.util.StringHandler.emptyIfNull(receiptDescriptionViewBean.getDeliveryTicket()) + "</TD>");
		 }
		}

		if (kitCount > 1 || receivingQcKitBeanCollection.size() == 1) {
		 pw.println("</TR>");
		}
	 }
	}
	pw.println("</html>");
	pw.close();
 }*/
}