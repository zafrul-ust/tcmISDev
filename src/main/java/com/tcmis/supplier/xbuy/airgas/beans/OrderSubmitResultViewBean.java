package com.tcmis.supplier.xbuy.airgas.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PolchemNsnVerificationBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class OrderSubmitResultViewBean extends BaseDataBean {

	String airgasPN;
	String availDate;
	String contractType;
	String custPrice;
	//String errorMessage;
	String hazardData;
	String itemType;
	String minSellQty;
	String qtyAvailable;
	String ok;
	
	/* result view bean info */
	String approvingParty;
	
	// bill to	
	String    billTo_account;
	String    billTo_billToAddress1;
	String    billTo_billToAddress2;
	String    billTo_billToCity;
	String    billTo_billToEmail;
	String    billTo_billToState;
	String    billTo_billToZip;
	String    billTo_name;
	//branch
	String    branch_id;
	String    branch_name;
	String    branch_number;
	//buyer
	String buyerName_Last;
	String buyerName_Middle;
	String buyerName_First;
	String custPO;
	String errorMessage;
	String notes;
	String notifications;
	String orderDate;
	
	//carrier
	String carrierInfo_DateShipped;
	String carrierInfo_Name;
	String carrierInfo_PackageId;

	/* order lines, assuming only one line */
	String comments;
	String custPartNum;
	String description;
	String extendedPrice;
	String feeLine;
	String itemChangeCoded;
	String itemNumber;
	String lineNumber;
	String lineStatus;
	String orderPrice;
	String orderQty;
	String partNum;
	String line_requiredDate;
	String select;
	String selectMessage;
	String shippedQty;
	String uom;

	/* payment, only one obj */
	String creditCardType;
//	String ExtraAttributes;
//	String ExtraElement;
//	String OMElement(parentQName, factory)
	String paymentType;
//	String PullParser(qName);
	
	String paymentMethod;
	String payTerms;
	/* other info */
	String requiredDate;
	String shippingInst;

/* ship to info */
		String shipto_Name;
		String shipto_ShipToAddress1;
		String shipto_ShipToAddress2;
		String shipto_ShipToCity;
		String shipto_ShipToState;
		String shipto_ShipToZip;
/* total info */
		String discounts;
		String handling;
		String orderTotal;
		String shipChrg;
		String tax;
	
	String userID;
    // order
    String order_number;
    String order_orderName;
//60
    String order_ridgewoodOrder;
    String order_sequence;
    String order_status;
    String order_type;

	

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getAirgasPN() {
		return airgasPN;
	}

	public void setAirgasPN(String airgasPN) {
		this.airgasPN = airgasPN;
	}

	public String getAvailDate() {
		return availDate;
	}

	public void setAvailDate(String availDate) {
		this.availDate = availDate;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getCustPrice() {
		return custPrice;
	}

	public void setCustPrice(String custPrice) {
		this.custPrice = custPrice;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getHazardData() {
		return hazardData;
	}

	public void setHazardData(String hazardData) {
		this.hazardData = hazardData;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getMinSellQty() {
		return minSellQty;
	}

	public void setMinSellQty(String minSellQty) {
		this.minSellQty = minSellQty;
	}

	public String getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(String qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public String getApprovingParty() {
		return approvingParty;
	}

	public void setApprovingParty(String approvingParty) {
		this.approvingParty = approvingParty;
	}

	public String getBillTo_account() {
		return billTo_account;
	}

	public void setBillTo_account(String billTo_account) {
		this.billTo_account = billTo_account;
	}

	public String getBillTo_billToAddress1() {
		return billTo_billToAddress1;
	}

	public void setBillTo_billToAddress1(String billTo_billToAddress1) {
		this.billTo_billToAddress1 = billTo_billToAddress1;
	}

	public String getBillTo_billToAddress2() {
		return billTo_billToAddress2;
	}

	public void setBillTo_billToAddress2(String billTo_billToAddress2) {
		this.billTo_billToAddress2 = billTo_billToAddress2;
	}

	public String getBillTo_billToCity() {
		return billTo_billToCity;
	}

	public void setBillTo_billToCity(String billTo_billToCity) {
		this.billTo_billToCity = billTo_billToCity;
	}

	public String getBillTo_billToEmail() {
		return billTo_billToEmail;
	}

	public void setBillTo_billToEmail(String billTo_billToEmail) {
		this.billTo_billToEmail = billTo_billToEmail;
	}

	public String getBillTo_billToState() {
		return billTo_billToState;
	}

	public void setBillTo_billToState(String billTo_billToState) {
		this.billTo_billToState = billTo_billToState;
	}

	public String getBillTo_billToZip() {
		return billTo_billToZip;
	}

	public void setBillTo_billToZip(String billTo_billToZip) {
		this.billTo_billToZip = billTo_billToZip;
	}

	public String getBillTo_name() {
		return billTo_name;
	}

	public void setBillTo_name(String billTo_name) {
		this.billTo_name = billTo_name;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getBranch_number() {
		return branch_number;
	}

	public void setBranch_number(String branch_number) {
		this.branch_number = branch_number;
	}

	public String getBuyerName_First() {
		return buyerName_First;
	}

	public void setBuyerName_First(String buyerName_First) {
		this.buyerName_First = buyerName_First;
	}

	public String getBuyerName_Last() {
		return buyerName_Last;
	}

	public void setBuyerName_Last(String buyerName_Last) {
		this.buyerName_Last = buyerName_Last;
	}

	public String getBuyerName_Middle() {
		return buyerName_Middle;
	}

	public void setBuyerName_Middle(String buyerName_Middle) {
		this.buyerName_Middle = buyerName_Middle;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCustPartNum() {
		return custPartNum;
	}

	public void setCustPartNum(String custPartNum) {
		this.custPartNum = custPartNum;
	}

	public String getCustPO() {
		return custPO;
	}

	public void setCustPO(String custPO) {
		this.custPO = custPO;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}

	public String getExtendedPrice() {
		return extendedPrice;
	}

	public void setExtendedPrice(String extendedPrice) {
		this.extendedPrice = extendedPrice;
	}

	public String getFeeLine() {
		return feeLine;
	}

	public void setFeeLine(String feeLine) {
		this.feeLine = feeLine;
	}

	public String getHandling() {
		return handling;
	}

	public void setHandling(String handling) {
		this.handling = handling;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}

	public String getItemChangeCoded() {
		return itemChangeCoded;
	}

	public void setItemChangeCoded(String itemChangeCoded) {
		this.itemChangeCoded = itemChangeCoded;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLine_requiredDate() {
		return line_requiredDate;
	}

	public void setLine_requiredDate(String line_requiredDate) {
		this.line_requiredDate = line_requiredDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotifications() {
		return notifications;
	}

	public void setNotifications(String notifications) {
		this.notifications = notifications;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPayTerms() {
		return payTerms;
	}

	public void setPayTerms(String payTerms) {
		this.payTerms = payTerms;
	}

	public String getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(String requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getSelectMessage() {
		return selectMessage;
	}

	public void setSelectMessage(String selectMessage) {
		this.selectMessage = selectMessage;
	}

	public String getShipChrg() {
		return shipChrg;
	}

	public void setShipChrg(String shipChrg) {
		this.shipChrg = shipChrg;
	}

	public String getShippedQty() {
		return shippedQty;
	}

	public void setShippedQty(String shippedQty) {
		this.shippedQty = shippedQty;
	}

	public String getShippingInst() {
		return shippingInst;
	}

	public void setShippingInst(String shippingInst) {
		this.shippingInst = shippingInst;
	}

	public String getShipto_Name() {
		return shipto_Name;
	}

	public void setShipto_Name(String shipto_Name) {
		this.shipto_Name = shipto_Name;
	}

	public String getShipto_ShipToAddress1() {
		return shipto_ShipToAddress1;
	}

	public void setShipto_ShipToAddress1(String shipto_ShipToAddress1) {
		this.shipto_ShipToAddress1 = shipto_ShipToAddress1;
	}

	public String getShipto_ShipToAddress2() {
		return shipto_ShipToAddress2;
	}

	public void setShipto_ShipToAddress2(String shipto_ShipToAddress2) {
		this.shipto_ShipToAddress2 = shipto_ShipToAddress2;
	}

	public String getShipto_ShipToCity() {
		return shipto_ShipToCity;
	}

	public void setShipto_ShipToCity(String shipto_ShipToCity) {
		this.shipto_ShipToCity = shipto_ShipToCity;
	}

	public String getShipto_ShipToState() {
		return shipto_ShipToState;
	}

	public void setShipto_ShipToState(String shipto_ShipToState) {
		this.shipto_ShipToState = shipto_ShipToState;
	}

	public String getShipto_ShipToZip() {
		return shipto_ShipToZip;
	}

	public void setShipto_ShipToZip(String shipto_ShipToZip) {
		this.shipto_ShipToZip = shipto_ShipToZip;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getCarrierInfo_DateShipped() {
		return carrierInfo_DateShipped;
	}

	public void setCarrierInfo_DateShipped(String carrierInfo_DateShipped) {
		this.carrierInfo_DateShipped = carrierInfo_DateShipped;
	}

	public String getCarrierInfo_Name() {
		return carrierInfo_Name;
	}

	public void setCarrierInfo_Name(String carrierInfo_Name) {
		this.carrierInfo_Name = carrierInfo_Name;
	}

	public String getCarrierInfo_PackageId() {
		return carrierInfo_PackageId;
	}

	public void setCarrierInfo_PackageId(String carrierInfo_PackageId) {
		this.carrierInfo_PackageId = carrierInfo_PackageId;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrder_orderName() {
		return order_orderName;
	}

	public void setOrder_orderName(String order_orderName) {
		this.order_orderName = order_orderName;
	}

	public String getOrder_ridgewoodOrder() {
		return order_ridgewoodOrder;
	}

	public void setOrder_ridgewoodOrder(String order_ridgewoodOrder) {
		this.order_ridgewoodOrder = order_ridgewoodOrder;
	}

	public String getOrder_sequence() {
		return order_sequence;
	}

	public void setOrder_sequence(String order_sequence) {
		this.order_sequence = order_sequence;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
}