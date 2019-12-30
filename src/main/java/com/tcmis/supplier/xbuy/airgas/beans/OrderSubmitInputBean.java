package com.tcmis.supplier.xbuy.airgas.beans;

/******************************************************************************
 * CLASSNAME: PolchemNsnVerificationBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/

public class OrderSubmitInputBean {
	private String inventoryLocation;
	private String region;
	private String ticketPrinter;
	private String partNum;
	private String account;
	private String haaspo;   // po number
	private String haasline; // line number
	
	/** ordersubmit input params **/

	String approvingParty;
	String billToAcct;
	String buyerFirstName;
	String buyerLastName;
	String buyerMiddleName;
	String chartOfAccts;
	String confirmationType;
	String custPO;
	String deliveryFlag;
	String Notes;
	String OrderDate;

	/** order detail lines, only one line */
	String orderName;
	String orderNumber;
	String orderType;
	
	/** os bill to, only one obj */
	String billto_City;
	String billto_Country;
	String billto_Customerid;
	String billto_Email;
/*	don't know what these for.
 *  OMAttribute[] omaArr = null;//new OMAttribute[1];
	//OMAttribute oma  = new OMAttribute();
	//oma.setAttributeValue(arg0);
	String setExtraAttributes(omaArr);
	//OMElement ome = new OMElement();
	String setExtraElement(null);
*/	String billto_Name;
	String billto_State;
	String billto_Street;
	String billto_Zip;
	
	/* osthip to , only one obj */
	
	String shipto_Account;
	String shipto_Address1;
	String shipto_Address2;
	String shipto_City;
	String shipto_Name;
	String shipto_State;
	String shipto_Zip;
	
	/* payment , only one obj */	
	String paymentCreditCardNumber;
	String paymentCreditCardType;
	String paymentCVNum;
	String paymentExpDate;
	String paymentMethod;
	String paymentName;
	String paymentOrigID;
	String paymentType;
	
	/* other info */
	String pickupBranch;
	String poRelease;
	String sharesecret;
	String ShipChrg;
	String shipDate;
	String shippingInst;
	String status;
	String taxableFlag;
	String useCustPartNum;
	String userID;
	String userInfo;

	/* order Line */
    String order_comments;
    String order_companyName;
    String order_custPartNum;
    String order_dateShipped;
    String order_description;
    String order_extendedPrice;
    String order_gasVolume;
    String order_itemChangeCoded;
    String order_itemNum;
    String order_lineStatus;
    String order_orderPrice;
    String order_orderQty;
    String order_packageID;
    String order_partNum;
    String order_priorityCode;
    String order_requiredDate;
    String order_returnQty;
    String order_select;
    String order_shipQty;
    String order_uom;


	public String getInventoryLocation() {
		return inventoryLocation;
	}
	public void setInventoryLocation(String inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
	public String getPartNum() {
		return partNum;
	}
	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTicketPrinter() {
		return ticketPrinter;
	}
	public void setTicketPrinter(String ticketPrinter) {
		this.ticketPrinter = ticketPrinter;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getHaaspo() {
		return haaspo;
	}
	public void setHaaspo(String haaspo) {
		this.haaspo = haaspo;
	}
	public String getApprovingParty() {
		return approvingParty;
	}
	public void setApprovingParty(String approvingParty) {
		this.approvingParty = approvingParty;
	}
	public String getBillto_City() {
		return billto_City;
	}
	public void setBillto_City(String billto_City) {
		this.billto_City = billto_City;
	}
	public String getBillto_Country() {
		return billto_Country;
	}
	public void setBillto_Country(String billto_Country) {
		this.billto_Country = billto_Country;
	}
	public String getBillto_Customerid() {
		return billto_Customerid;
	}
	public void setBillto_Customerid(String billto_Customerid) {
		this.billto_Customerid = billto_Customerid;
	}
	public String getBillto_Email() {
		return billto_Email;
	}
	public void setBillto_Email(String billto_Email) {
		this.billto_Email = billto_Email;
	}
	public String getBillto_Name() {
		return billto_Name;
	}
	public void setBillto_Name(String billto_Name) {
		this.billto_Name = billto_Name;
	}
	public String getBillto_State() {
		return billto_State;
	}
	public void setBillto_State(String billto_State) {
		this.billto_State = billto_State;
	}
	public String getBillto_Street() {
		return billto_Street;
	}
	public void setBillto_Street(String billto_Street) {
		this.billto_Street = billto_Street;
	}
	public String getBillto_Zip() {
		return billto_Zip;
	}
	public void setBillto_Zip(String billto_Zip) {
		this.billto_Zip = billto_Zip;
	}
	public String getBillToAcct() {
		return billToAcct;
	}
	public void setBillToAcct(String billToAcct) {
		this.billToAcct = billToAcct;
	}
	public String getBuyerFirstName() {
		return buyerFirstName;
	}
	public void setBuyerFirstName(String buyerFirstName) {
		this.buyerFirstName = buyerFirstName;
	}
	public String getBuyerLastName() {
		return buyerLastName;
	}
	public void setBuyerLastName(String buyerLastName) {
		this.buyerLastName = buyerLastName;
	}
	public String getBuyerMiddleName() {
		return buyerMiddleName;
	}
	public void setBuyerMiddleName(String buyerMiddleName) {
		this.buyerMiddleName = buyerMiddleName;
	}
	public String getChartOfAccts() {
		return chartOfAccts;
	}
	public void setChartOfAccts(String chartOfAccts) {
		this.chartOfAccts = chartOfAccts;
	}
	public String getConfirmationType() {
		return confirmationType;
	}
	public void setConfirmationType(String confirmationType) {
		this.confirmationType = confirmationType;
	}
	public String getCustPO() {
		return custPO;
	}
	public void setCustPO(String custPO) {
		this.custPO = custPO;
	}
	public String getDeliveryFlag() {
		return deliveryFlag;
	}
	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public String getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPaymentCreditCardNumber() {
		return paymentCreditCardNumber;
	}
	public void setPaymentCreditCardNumber(String paymentCreditCardNumber) {
		this.paymentCreditCardNumber = paymentCreditCardNumber;
	}
	public String getPaymentCreditCardType() {
		return paymentCreditCardType;
	}
	public void setPaymentCreditCardType(String paymentCreditCardType) {
		this.paymentCreditCardType = paymentCreditCardType;
	}
	public String getPaymentCVNum() {
		return paymentCVNum;
	}
	public void setPaymentCVNum(String paymentCVNum) {
		this.paymentCVNum = paymentCVNum;
	}
	public String getPaymentExpDate() {
		return paymentExpDate;
	}
	public void setPaymentExpDate(String paymentExpDate) {
		this.paymentExpDate = paymentExpDate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getPaymentOrigID() {
		return paymentOrigID;
	}
	public void setPaymentOrigID(String paymentOrigID) {
		this.paymentOrigID = paymentOrigID;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPickupBranch() {
		return pickupBranch;
	}
	public void setPickupBranch(String pickupBranch) {
		this.pickupBranch = pickupBranch;
	}
	public String getPoRelease() {
		return poRelease;
	}
	public void setPoRelease(String poRelease) {
		this.poRelease = poRelease;
	}
	public String getSharesecret() {
		return sharesecret;
	}
	public void setSharesecret(String sharesecret) {
		this.sharesecret = sharesecret;
	}
	public String getShipChrg() {
		return ShipChrg;
	}
	public void setShipChrg(String shipChrg) {
		ShipChrg = shipChrg;
	}
	public String getShipDate() {
		return shipDate;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public String getShippingInst() {
		return shippingInst;
	}
	public void setShippingInst(String shippingInst) {
		this.shippingInst = shippingInst;
	}
	public String getShipto_Account() {
		return shipto_Account;
	}
	public void setShipto_Account(String shipto_Account) {
		this.shipto_Account = shipto_Account;
	}
	public String getShipto_Address1() {
		return shipto_Address1;
	}
	public void setShipto_Address1(String shipto_Address1) {
		this.shipto_Address1 = shipto_Address1;
	}
	public String getShipto_Address2() {
		return shipto_Address2;
	}
	public void setShipto_Address2(String shipto_Address2) {
		this.shipto_Address2 = shipto_Address2;
	}
	public String getShipto_City() {
		return shipto_City;
	}
	public void setShipto_City(String shipto_City) {
		this.shipto_City = shipto_City;
	}
	public String getShipto_Name() {
		return shipto_Name;
	}
	public void setShipto_Name(String shipto_Name) {
		this.shipto_Name = shipto_Name;
	}
	public String getShipto_State() {
		return shipto_State;
	}
	public void setShipto_State(String shipto_State) {
		this.shipto_State = shipto_State;
	}
	public String getShipto_Zip() {
		return shipto_Zip;
	}
	public void setShipto_Zip(String shipto_Zip) {
		this.shipto_Zip = shipto_Zip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaxableFlag() {
		return taxableFlag;
	}
	public void setTaxableFlag(String taxableFlag) {
		this.taxableFlag = taxableFlag;
	}
	public String getUseCustPartNum() {
		return useCustPartNum;
	}
	public void setUseCustPartNum(String useCustPartNum) {
		this.useCustPartNum = useCustPartNum;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public String getOrder_comments() {
		return order_comments;
	}
	public void setOrder_comments(String order_comments) {
		this.order_comments = order_comments;
	}
	public String getOrder_companyName() {
		return order_companyName;
	}
	public void setOrder_companyName(String order_companyName) {
		this.order_companyName = order_companyName;
	}
	public String getOrder_custPartNum() {
		return order_custPartNum;
	}
	public void setOrder_custPartNum(String order_custPartNum) {
		this.order_custPartNum = order_custPartNum;
	}
	public String getOrder_dateShipped() {
		return order_dateShipped;
	}
	public void setOrder_dateShipped(String order_dateShipped) {
		this.order_dateShipped = order_dateShipped;
	}
	public String getOrder_description() {
		return order_description;
	}
	public void setOrder_description(String order_description) {
		this.order_description = order_description;
	}
	public String getOrder_extendedPrice() {
		return order_extendedPrice;
	}
	public void setOrder_extendedPrice(String order_extendedPrice) {
		this.order_extendedPrice = order_extendedPrice;
	}
	public String getOrder_gasVolume() {
		return order_gasVolume;
	}
	public void setOrder_gasVolume(String order_gasVolume) {
		this.order_gasVolume = order_gasVolume;
	}
	public String getOrder_itemChangeCoded() {
		return order_itemChangeCoded;
	}
	public void setOrder_itemChangeCoded(String order_itemChangeCoded) {
		this.order_itemChangeCoded = order_itemChangeCoded;
	}
	public String getOrder_itemNum() {
		return order_itemNum;
	}
	public void setOrder_itemNum(String order_itemNum) {
		this.order_itemNum = order_itemNum;
	}
	public String getOrder_lineStatus() {
		return order_lineStatus;
	}
	public void setOrder_lineStatus(String order_lineStatus) {
		this.order_lineStatus = order_lineStatus;
	}
	public String getOrder_orderPrice() {
		return order_orderPrice;
	}
	public void setOrder_orderPrice(String order_orderPrice) {
		this.order_orderPrice = order_orderPrice;
	}
	public String getOrder_orderQty() {
		return order_orderQty;
	}
	public void setOrder_orderQty(String order_orderQty) {
		this.order_orderQty = order_orderQty;
	}
	public String getOrder_packageID() {
		return order_packageID;
	}
	public void setOrder_packageID(String order_packageID) {
		this.order_packageID = order_packageID;
	}
	public String getOrder_partNum() {
		return order_partNum;
	}
	public void setOrder_partNum(String order_partNum) {
		this.order_partNum = order_partNum;
	}
	public String getOrder_priorityCode() {
		return order_priorityCode;
	}
	public void setOrder_priorityCode(String order_priorityCode) {
		this.order_priorityCode = order_priorityCode;
	}
	public String getOrder_requiredDate() {
		return order_requiredDate;
	}
	public void setOrder_requiredDate(String order_requiredDate) {
		this.order_requiredDate = order_requiredDate;
	}
	public String getOrder_returnQty() {
		return order_returnQty;
	}
	public void setOrder_returnQty(String order_returnQty) {
		this.order_returnQty = order_returnQty;
	}
	public String getOrder_select() {
		return order_select;
	}
	public void setOrder_select(String order_select) {
		this.order_select = order_select;
	}
	public String getOrder_shipQty() {
		return order_shipQty;
	}
	public void setOrder_shipQty(String order_shipQty) {
		this.order_shipQty = order_shipQty;
	}
	public String getOrder_uom() {
		return order_uom;
	}
	public void setOrder_uom(String order_uom) {
		this.order_uom = order_uom;
	}
	public String getHaasline() {
		return haasline;
	}
	public void setHaasline(String haasline) {
		this.haasline = haasline;
	}
}