package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BoxLabelViewBean <br>
 * 
 * @version: 1.0, Nov 5, 2009 <br>
 *****************************************************************************/

public class BoxLabelViewBean extends BaseDataBean {

	private String		application;
	private String		applicationDesc;
	private String		catPartNo;
	private String		companyId;
	private BigDecimal  boxCount = new BigDecimal("1");
	private String		deliveryPoint;
	private String		deliveryPointDesc;
	private String		endUser;
	private String		facilityId;
	private String		facilityName;
	private String		firstName;
	private String		inventoryGroup;
	private String		lastName;
	private String		lineItem;
	private String		paymentTerms;
	private BigDecimal	pickingUnitId;
	private String		poNumber;
	private BigDecimal	prNumber;
	private String		requestorEmail;
	private String		requestorFax;
	private String		requestorName;
	private String		requestorPhone;
	private String		shipFromAddressLine1;
	private String		shipFromAddressLine2;
	private String		shipFromAddressLine3;
	private String		shipFromAddressLine4;
	private String		shipFromAddressLine5;
	private String		shipFromFax;
	private String		shipFromPhone;
	private String		shippingReference;
	private String		shipToAddressLine1;
	private String		shipToAddressLine2;
	private String		shipToAddressLine3;
	private String		shipToAddressLine4;
	private String		shipToAddressLine5;
	private String		shipToFax;
	private String		shipToPhone;
	private String		receiptList;
	private int			curLabelCount;
	private String		ppsBarcode;
	private String		datePrinted;

	// constructor
	public BoxLabelViewBean() {
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}

	public String getDetail0() {
		return "" + this.boxCount;
	}

	public String getDetail1() {
		return this.firstName + "  " + this.lastName;
	}

	public String getDetail2() {
		return this.endUser;
	}

	public String getDetail3() {
		return this.deliveryPointDesc;
	}

	public String getDetail4() {
		return this.prNumber + "-" + this.lineItem;
	}

	public String getDetail5() {
		return this.facilityName;
	}

	public String getDetail6() {
		return this.applicationDesc;
	}

	public String getDetail7() {
		return this.catPartNo;
	}

	public String getEndUser() {
		return endUser;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return this.facilityName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	// getters
	public String getLastName() {
		return lastName;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public BigDecimal getPickingUnitId() {
		return this.pickingUnitId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public String getRequestorEmail() {
		return requestorEmail;
	}

	public String getRequestorFax() {
		return requestorFax;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public String getRequestorPhone() {
		return requestorPhone;
	}

	public String getShipFromAddressLine1() {
		return shipFromAddressLine1;
	}

	public String getShipFromAddressLine2() {
		return shipFromAddressLine2;
	}

	public String getShipFromAddressLine3() {
		return shipFromAddressLine3;
	}

	public String getShipFromAddressLine4() {
		return shipFromAddressLine4;
	}

	public String getShipFromAddressLine5() {
		return shipFromAddressLine5;
	}

	public String getShipFromFax() {
		return shipFromFax;
	}

	public String getShipFromPhone() {
		return shipFromPhone;
	}

	public String getShippingReference() {
		return shippingReference;
	}

	public String getShipToAddressLine1() {
		return shipToAddressLine1;
	}

	public String getShipToAddressLine2() {
		return shipToAddressLine2;
	}

	public String getShipToAddressLine3() {
		return shipToAddressLine3;
	}

	public String getShipToAddressLine4() {
		return shipToAddressLine4;
	}

	public String getShipToAddressLine5() {
		return shipToAddressLine5;
	}

	public String getShipToFax() {
		return shipToFax;
	}

	public String getShipToPhone() {
		return shipToPhone;
	}
	
	public String getReceiptList() {
		StringBuilder receipts = new StringBuilder("");
		  
		  if(!StringHandler.isBlankString(receiptList)){
			  // receiptList is expected to be in the format of receipt_id|"Qty" qty ie. 21027551|Qty 8
			  // Corresonding database update TCMISDEV-3988
			  String[] receiptListArray = receiptList.split(";");
			  int maxReceipts = 10;
			  
			  if(receiptListArray.length < 10)
				  maxReceipts = receiptListArray.length;
			  
			  for(int i=0; i < maxReceipts; i++){
				  receipts.append(receiptListArray[i]);
				  receipts.append("   ");
			  }
		  }
		  return receipts.toString();
	}

	public int getCurLabelCount() {
		return curLabelCount;
	}

	public String getPpsBarcode() {
		 StringBuilder barcodeStr = new StringBuilder("");
		  
		  if(getPickingUnitId() != null){
			  barcodeStr.append("PU|");
			  barcodeStr.append(getPickingUnitId() + "|");
			  barcodeStr.append(getCurLabelCount() + "|");
			  barcodeStr.append(getDetail0());
		  }
		  
		  return barcodeStr.toString();
	}
	
	public String getDatePrinted() {
		String pattern = "yyyy/MM/dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String today = simpleDateFormat.format(new Date());
		return today;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	// setters
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}

	public void setRequestorFax(String requestorFax) {
		this.requestorFax = requestorFax;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setRequestorPhone(String requestorPhone) {
		this.requestorPhone = requestorPhone;
	}

	public void setShipFromAddressLine1(String shipFromAddressLine1) {
		this.shipFromAddressLine1 = shipFromAddressLine1;
	}

	public void setShipFromAddressLine2(String shipFromAddressLine2) {
		this.shipFromAddressLine2 = shipFromAddressLine2;
	}

	public void setShipFromAddressLine3(String shipFromAddressLine3) {
		this.shipFromAddressLine3 = shipFromAddressLine3;
	}

	public void setShipFromAddressLine4(String shipFromAddressLine4) {
		this.shipFromAddressLine4 = shipFromAddressLine4;
	}

	public void setShipFromAddressLine5(String shipFromAddressLine5) {
		this.shipFromAddressLine5 = shipFromAddressLine5;
	}

	public void setShipFromFax(String shipFromFax) {
		this.shipFromFax = shipFromFax;
	}

	public void setShipFromPhone(String shipFromPhone) {
		this.shipFromPhone = shipFromPhone;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	public void setShipToAddressLine1(String shipToAddressLine1) {
		this.shipToAddressLine1 = shipToAddressLine1;
	}

	public void setShipToAddressLine2(String shipToAddressLine2) {
		this.shipToAddressLine2 = shipToAddressLine2;
	}

	public void setShipToAddressLine3(String shipToAddressLine3) {
		this.shipToAddressLine3 = shipToAddressLine3;
	}

	public void setShipToAddressLine4(String shipToAddressLine4) {
		this.shipToAddressLine4 = shipToAddressLine4;
	}

	public void setShipToAddressLine5(String shipToAddressLine5) {
		this.shipToAddressLine5 = shipToAddressLine5;
	}

	public void setShipToFax(String shipToFax) {
		this.shipToFax = shipToFax;
	}

	public void setShipToPhone(String shipToPhone) {
		this.shipToPhone = shipToPhone;
	}
	
	public void setReceiptList(String receiptList) {
		this.receiptList = receiptList;
	}

	public void setCurLabelCount(int curLabelCount) {
		this.curLabelCount = curLabelCount;
	}

	public void setPpsBarcode(String ppsBarcode) {
		this.ppsBarcode = ppsBarcode;
	}

	public BigDecimal getBoxCount() {
		return this.boxCount;
	}

	public void setBoxCount(BigDecimal boxCount) {
		this.boxCount = boxCount;
	}
}