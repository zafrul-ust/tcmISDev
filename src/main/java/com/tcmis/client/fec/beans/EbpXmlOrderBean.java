package com.tcmis.client.fec.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: EbpXmlOrderBean <br>
 * @version: 1.0, Sep 7, 2005 <br>
 *****************************************************************************/


public class EbpXmlOrderBean extends BaseDataBean {

	private String buyerordernumber;
	private String issuedate;
	private String currency;
	private String buyerName;
	private String buyerAddr1;
	private String buyerZip;
	private String buyerCity;
	private String buyerState;
	private String buyerCountry;
	private String buyerContact;
	private String sellerPartyid;
	private String sellerName;
	private String sellerAddr1;
	private String sellerCity;
	private String sellerState;
	private String sellerZip;
	private String sellerCountry;
	private String shiptoPartyid;
	private String shiptoName1;
	private String shiptoName2;
	private String shiptoName3;
	private String shiptoAddr1;
	private String shiptoCity;
	private String shiptoState;
	private String shiptoZip;
	private String shiptoCountry;
	private String billtoPartyid;
	private String billtoName;
	private String paymentTerm;
	private String paymentMean;
	private String cardNumber;
	private String cardType;
	private String cardHolderName;
	private String cardExpDate;
	private String lineNo;
	private BigDecimal itemId;
	private String mfgPartnum;
	private String itemDesc;
	private String quantity;
	private String uom;
	private String recipientId;
	private String unitPrice;
	private String unitPriceCurrency;
	private String lineTotalAmount;
	private BigDecimal mrNumber;
	private String lineItem;
	private String itemPkg;
	private String buyerPartnum;
	private String totalLines;
	private String totalAmount;
	private String contactName;
	private String contactPhone;
	private String contactFax;
	private String contactEmail;
	private String status;
	private Date statusDate;
	private String error;


	//constructor
	public EbpXmlOrderBean() {
	}

	//setters
	public void setBuyerordernumber(String buyerordernumber) {
		this.buyerordernumber = buyerordernumber;
	}
	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setBuyerAddr1(String buyerAddr1) {
		this.buyerAddr1 = buyerAddr1;
	}
	public void setBuyerZip(String buyerZip) {
		this.buyerZip = buyerZip;
	}
	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}
	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}
	public void setBuyerCountry(String buyerCountry) {
		this.buyerCountry = buyerCountry;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public void setSellerPartyid(String sellerPartyid) {
		this.sellerPartyid = sellerPartyid;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public void setSellerAddr1(String sellerAddr1) {
		this.sellerAddr1 = sellerAddr1;
	}
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}
	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}
	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}
	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}
	public void setShiptoPartyid(String shiptoPartyid) {
		this.shiptoPartyid = shiptoPartyid;
	}
	public void setShiptoName1(String shiptoName1) {
		this.shiptoName1 = shiptoName1;
	}
	public void setShiptoName2(String shiptoName2) {
		this.shiptoName2 = shiptoName2;
	}
	public void setShiptoName3(String shiptoName3) {
		this.shiptoName3 = shiptoName3;
	}
	public void setShiptoAddr1(String shiptoAddr1) {
		this.shiptoAddr1 = shiptoAddr1;
	}
	public void setShiptoCity(String shiptoCity) {
		this.shiptoCity = shiptoCity;
	}
	public void setShiptoState(String shiptoState) {
		this.shiptoState = shiptoState;
	}
	public void setShiptoZip(String shiptoZip) {
		this.shiptoZip = shiptoZip;
	}
	public void setShiptoCountry(String shiptoCountry) {
		this.shiptoCountry = shiptoCountry;
	}
	public void setBilltoPartyid(String billtoPartyid) {
		this.billtoPartyid = billtoPartyid;
	}
	public void setBilltoName(String billtoName) {
		this.billtoName = billtoName;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public void setPaymentMean(String paymentMean) {
		this.paymentMean = paymentMean;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setMfgPartnum(String mfgPartnum) {
		this.mfgPartnum = mfgPartnum;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setUnitPriceCurrency(String unitPriceCurrency) {
		this.unitPriceCurrency = unitPriceCurrency;
	}
	public void setLineTotalAmount(String lineTotalAmount) {
		this.lineTotalAmount = lineTotalAmount;
	}
	public void setMrNumber(BigDecimal mrNumber) {
		this.mrNumber = mrNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setItemPkg(String itemPkg) {
		this.itemPkg = itemPkg;
	}
	public void setBuyerPartnum(String buyerPartnum) {
		this.buyerPartnum = buyerPartnum;
	}
	public void setTotalLines(String totalLines) {
		this.totalLines = totalLines;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public void setError(String error) {
		this.error = error;
	}


	//getters
	public String getBuyerordernumber() {
		return buyerordernumber;
	}
	public String getIssuedate() {
		return issuedate;
	}
	public String getCurrency() {
		return currency;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getBuyerAddr1() {
		return buyerAddr1;
	}
	public String getBuyerZip() {
		return buyerZip;
	}
	public String getBuyerCity() {
		return buyerCity;
	}
	public String getBuyerState() {
		return buyerState;
	}
	public String getBuyerCountry() {
		return buyerCountry;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public String getSellerPartyid() {
		return sellerPartyid;
	}
	public String getSellerName() {
		return sellerName;
	}
	public String getSellerAddr1() {
		return sellerAddr1;
	}
	public String getSellerCity() {
		return sellerCity;
	}
	public String getSellerState() {
		return sellerState;
	}
	public String getSellerZip() {
		return sellerZip;
	}
	public String getSellerCountry() {
		return sellerCountry;
	}
	public String getShiptoPartyid() {
		return shiptoPartyid;
	}
	public String getShiptoName1() {
		return shiptoName1;
	}
	public String getShiptoName2() {
		return shiptoName2;
	}
	public String getShiptoName3() {
		return shiptoName3;
	}
	public String getShiptoAddr1() {
		return shiptoAddr1;
	}
	public String getShiptoCity() {
		return shiptoCity;
	}
	public String getShiptoState() {
		return shiptoState;
	}
	public String getShiptoZip() {
		return shiptoZip;
	}
	public String getShiptoCountry() {
		return shiptoCountry;
	}
	public String getBilltoPartyid() {
		return billtoPartyid;
	}
	public String getBilltoName() {
		return billtoName;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public String getPaymentMean() {
		return paymentMean;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public String getCardType() {
		return cardType;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public String getCardExpDate() {
		return cardExpDate;
	}
	public String getLineNo() {
		return lineNo;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getMfgPartnum() {
		return mfgPartnum;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getQuantity() {
		return quantity;
	}
	public String getUom() {
		return uom;
	}
	public String getRecipientId() {
		return recipientId;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public String getUnitPriceCurrency() {
		return unitPriceCurrency;
	}
	public String getLineTotalAmount() {
		return lineTotalAmount;
	}
	public BigDecimal getMrNumber() {
		return mrNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getItemPkg() {
		return itemPkg;
	}
	public String getBuyerPartnum() {
		return buyerPartnum;
	}
	public String getTotalLines() {
		return totalLines;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public String getContactName() {
		return contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public String getContactFax() {
		return contactFax;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public String getStatus() {
		return status;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public String getError() {
		return error;
	}
}