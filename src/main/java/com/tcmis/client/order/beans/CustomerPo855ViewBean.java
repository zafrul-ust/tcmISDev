package com.tcmis.client.order.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CustomerPo855ViewBean <br>
 * @version: 1.0, Nov 17, 2005 <br>
 *****************************************************************************/

public class CustomerPo855ViewBean
    extends BaseDataBean {

  private String customerPoNo;
  private String customerPoLineNo;
  private String transactionType;
  private BigDecimal lineSequence;
  private BigDecimal changeOrderSequence;
  private BigDecimal quantity;
  private BigDecimal quantityLeft;
  private String uom;
  private BigDecimal unitPrice;
  private String status;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private Date dateIssued;
  private String haasItemNo;
  private String manufacturerPartNum;
  private String itemDescription;
  private String customerPoLineNote;
  private Date requestedDeliveryDate;
  private Date estimatedDockDate;
  private BigDecimal totalAmountOnPo;
  private Date dateInserted;
  private String unitPriceCode;
  private String acceptanceCode;
  private String changeTypeCode;
  private String changeSubTypeCode;
  private String buyerPhone;
  private String buyerFax;
  private String documentControlNumber;
  private String transport;
  private String transporterAccount;
  private String tradingPartner;
  private String tradingPartnerId;
  private String customerHaasContractId;
  private String customerHaasAccountNo;
  private String customerPaymentTerms;
  private String customerPoNote;
  private String buyerNameOnPo;
  private String buyerAddress1;
  private String buyerAddress2;
  private String buyerCity;
  private String buyerState;
  private String buyerZip;
  private String buyerCountry;
  private String sellerNameOnPo;
  private String sellerIdOnPo;
  private String sellerAddress1;
  private String sellerAddress2;
  private String sellerCity;
  private String sellerState;
  private String sellerZip;
  private String sellerCountry;
  private String shiptoPartyName;
  private String shiptoPartyId;
  private String shiptoAddress1;
  private String shiptoAddress2;
  private String shiptoAddress3;
  private String shiptoCity;
  private String shiptoState;
  private String shiptoZip;
  private String shiptoCountry;
  private String billtoParty;
  private String billtoPartyId;
  private String billtoName2;
  private String billtoAddress1;
  private String billtoAddress2;
  private String billtoAddress3;
  private String billtoCity;
  private String billtoState;
  private String billtoZip;
  private String billtoCountry;
  private BigDecimal prNumber;
  private String lineItem;
  private BigDecimal suggestedDbuyContractId;
  private BigDecimal suggestedQuantity;
  private String accountSysId;
  private String chargeType;
  private String facilityId;
  private String application;
  private String interchangeControlNumber;
  private BigDecimal sumQtyForAllLines;
  //private BigDecimal originalQuantityOrdered;
  //private BigDecimal originalUnitPrice;
  //private Date origRequestedDeliveryDate;
  //private BigDecimal origQuantity;
  private BigDecimal fromLoadId;
  private BigDecimal fromLoadLine;

  private Collection customerPoPreStageBeanCollection = new Vector();

  //constructor
  public CustomerPo855ViewBean() {
  }

  //setters
  public void setCustomerPoNo(String customerPoNo) {
    this.customerPoNo = customerPoNo;
  }

  public void setCustomerPoLineNo(String customerPoLineNo) {
    this.customerPoLineNo = customerPoLineNo;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public void setLineSequence(BigDecimal lineSequence) {
    this.lineSequence = lineSequence;
  }

  public void setChangeOrderSequence(BigDecimal changeOrderSequence) {
    this.changeOrderSequence = changeOrderSequence;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setQuantityLeft(BigDecimal quantityLeft) {
    this.quantityLeft = quantityLeft;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }

  public void setDateIssued(Date dateIssued) {
    this.dateIssued = dateIssued;
  }

  public void setHaasItemNo(String haasItemNo) {
    this.haasItemNo = haasItemNo;
  }

  public void setManufacturerPartNum(String manufacturerPartNum) {
    this.manufacturerPartNum = manufacturerPartNum;
  }

  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  public void setCustomerPoLineNote(String customerPoLineNote) {
    this.customerPoLineNote = customerPoLineNote;
  }

  public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
    this.requestedDeliveryDate = requestedDeliveryDate;
  }

  public void setEstimatedDockDate(Date estimatedDockDate) {
    this.estimatedDockDate = estimatedDockDate;
  }

  public void setTotalAmountOnPo(BigDecimal totalAmountOnPo) {
    this.totalAmountOnPo = totalAmountOnPo;
  }

  public void setDateInserted(Date dateInserted) {
    this.dateInserted = dateInserted;
  }

  public void setUnitPriceCode(String unitPriceCode) {
    this.unitPriceCode = unitPriceCode;
  }

  public void setAcceptanceCode(String acceptanceCode) {
    this.acceptanceCode = acceptanceCode;
  }

  public void setChangeTypeCode(String changeTypeCode) {
    this.changeTypeCode = changeTypeCode;
  }

  public void setChangeSubTypeCode(String changeSubTypeCode) {
    this.changeSubTypeCode = changeSubTypeCode;
  }

  public void setBuyerPhone(String buyerPhone) {
    this.buyerPhone = buyerPhone;
  }

  public void setBuyerFax(String buyerFax) {
    this.buyerFax = buyerFax;
  }

  public void setDocumentControlNumber(String documentControlNumber) {
    this.documentControlNumber = documentControlNumber;
  }

  public void setTransport(String transport) {
    this.transport = transport;
  }

  public void setTransporterAccount(String transporterAccount) {
    this.transporterAccount = transporterAccount;
  }

  public void setTradingPartner(String tradingPartner) {
    this.tradingPartner = tradingPartner;
  }

  public void setTradingPartnerId(String tradingPartnerId) {
    this.tradingPartnerId = tradingPartnerId;
  }

  public void setCustomerHaasContractId(String customerHaasContractId) {
    this.customerHaasContractId = customerHaasContractId;
  }

  public void setCustomerHaasAccountNo(String customerHaasAccountNo) {
    this.customerHaasAccountNo = customerHaasAccountNo;
  }

  public void setCustomerPaymentTerms(String customerPaymentTerms) {
    this.customerPaymentTerms = customerPaymentTerms;
  }

  public void setCustomerPoNote(String customerPoNote) {
    this.customerPoNote = customerPoNote;
  }

  public void setBuyerNameOnPo(String buyerNameOnPo) {
    this.buyerNameOnPo = buyerNameOnPo;
  }

  public void setBuyerAddress1(String buyerAddress1) {
    this.buyerAddress1 = buyerAddress1;
  }

  public void setBuyerAddress2(String buyerAddress2) {
    this.buyerAddress2 = buyerAddress2;
  }

  public void setBuyerCity(String buyerCity) {
    this.buyerCity = buyerCity;
  }

  public void setBuyerState(String buyerState) {
    this.buyerState = buyerState;
  }

  public void setBuyerZip(String buyerZip) {
    this.buyerZip = buyerZip;
  }

  public void setBuyerCountry(String buyerCountry) {
    this.buyerCountry = buyerCountry;
  }

  public void setSellerNameOnPo(String sellerNameOnPo) {
    this.sellerNameOnPo = sellerNameOnPo;
  }

  public void setSellerIdOnPo(String sellerIdOnPo) {
    this.sellerIdOnPo = sellerIdOnPo;
  }

  public void setSellerAddress1(String sellerAddress1) {
    this.sellerAddress1 = sellerAddress1;
  }

  public void setSellerAddress2(String sellerAddress2) {
    this.sellerAddress2 = sellerAddress2;
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

  public void setShiptoPartyName(String shiptoPartyName) {
    this.shiptoPartyName = shiptoPartyName;
  }

  public void setShiptoPartyId(String shiptoPartyId) {
    this.shiptoPartyId = shiptoPartyId;
  }

  public void setShiptoAddress1(String shiptoAddress1) {
    this.shiptoAddress1 = shiptoAddress1;
  }

  public void setShiptoAddress2(String shiptoAddress2) {
    this.shiptoAddress2 = shiptoAddress2;
  }

  public void setShiptoAddress3(String shiptoAddress3) {
    this.shiptoAddress3 = shiptoAddress3;
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

  public void setBilltoParty(String billtoParty) {
    this.billtoParty = billtoParty;
  }

  public void setBilltoPartyId(String billtoPartyId) {
    this.billtoPartyId = billtoPartyId;
  }

  public void setBilltoName2(String billtoName2) {
    this.billtoName2 = billtoName2;
  }

  public void setBilltoAddress1(String billtoAddress1) {
    this.billtoAddress1 = billtoAddress1;
  }

  public void setBilltoAddress2(String billtoAddress2) {
    this.billtoAddress2 = billtoAddress2;
  }

  public void setBilltoAddress3(String billtoAddress3) {
    this.billtoAddress3 = billtoAddress3;
  }

  public void setBilltoCity(String billtoCity) {
    this.billtoCity = billtoCity;
  }

  public void setBilltoState(String billtoState) {
    this.billtoState = billtoState;
  }

  public void setBilltoZip(String billtoZip) {
    this.billtoZip = billtoZip;
  }

  public void setBilltoCountry(String billtoCountry) {
    this.billtoCountry = billtoCountry;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setSuggestedDbuyContractId(BigDecimal suggestedDbuyContractId) {
    this.suggestedDbuyContractId = suggestedDbuyContractId;
  }

  public void setSuggestedQuantity(BigDecimal suggestedQuantity) {
    this.suggestedQuantity = suggestedQuantity;
  }

  public void setAccountSysId(String accountSysId) {
    this.accountSysId = accountSysId;
  }

  public void setChargeType(String chargeType) {
    this.chargeType = chargeType;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setInterchangeControlNumber(String interchangeControlNumber) {
    this.interchangeControlNumber = interchangeControlNumber;
  }

  public void setSumQtyForAllLines(BigDecimal sumQtyForAllLines) {
    this.sumQtyForAllLines = sumQtyForAllLines;
  }
/*
  public void setOriginalQuantityOrdered(BigDecimal originalQuantityOrdered) {
    this.originalQuantityOrdered = originalQuantityOrdered;
  }

  public void setOriginalUnitPrice(BigDecimal originalUnitPrice) {
    this.originalUnitPrice = originalUnitPrice;
  }

  public void setOrigRequestedDeliveryDate(Date origRequestedDeliveryDate) {
    this.origRequestedDeliveryDate = origRequestedDeliveryDate;
  }

  public void setOrigQuantity(BigDecimal origQuantity) {
    this.origQuantity = origQuantity;
  }
*/
  public void setFromLoadId(BigDecimal fromLoadId) {
    this.fromLoadId = fromLoadId;
  }

  public void setFromLoadLine(BigDecimal fromLoadLine) {
    this.fromLoadLine = fromLoadLine;
  }



  public void setCustomerPoPreStageBeanCollection(Collection c) {
    this.customerPoPreStageBeanCollection = c;
  }

  //getters
  public String getCustomerPoNo() {
    return customerPoNo;
  }

  public String getCustomerPoLineNo() {
    return customerPoLineNo;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public BigDecimal getLineSequence() {
    return lineSequence;
  }

  public BigDecimal getChangeOrderSequence() {
    return changeOrderSequence;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getQuantityLeft() {
    return quantityLeft;
  }

  public String getUom() {
    return uom;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public String getStatus() {
    return status;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }

  public Date getDateIssued() {
    return dateIssued;
  }

  public String getHaasItemNo() {
    return haasItemNo;
  }

  public String getManufacturerPartNum() {
    return manufacturerPartNum;
  }

  public String getItemDescription() {
    return itemDescription;
  }

  public String getCustomerPoLineNote() {
    return customerPoLineNote;
  }

  public Date getRequestedDeliveryDate() {
    return requestedDeliveryDate;
  }

  public Date getEstimatedDockDate() {
    return estimatedDockDate;
  }

  public BigDecimal getTotalAmountOnPo() {
    return totalAmountOnPo;
  }

  public Date getDateInserted() {
    return dateInserted;
  }

  public String getUnitPriceCode() {
    return unitPriceCode;
  }

  public String getAcceptanceCode() {
    return acceptanceCode;
  }

  public String getChangeTypeCode() {
    return changeTypeCode;
  }

  public String getChangeSubTypeCode() {
    return changeSubTypeCode;
  }

  public String getBuyerPhone() {
    return buyerPhone;
  }

  public String getBuyerFax() {
    return buyerFax;
  }

  public String getDocumentControlNumber() {
    return documentControlNumber;
  }

  public String getTransport() {
    return transport;
  }

  public String getTransporterAccount() {
    return transporterAccount;
  }

  public String getTradingPartner() {
    return tradingPartner;
  }

  public String getTradingPartnerId() {
    return tradingPartnerId;
  }

  public String getCustomerHaasContractId() {
    return customerHaasContractId;
  }

  public String getCustomerHaasAccountNo() {
    return customerHaasAccountNo;
  }

  public String getCustomerPaymentTerms() {
    return customerPaymentTerms;
  }

  public String getCustomerPoNote() {
    return customerPoNote;
  }

  public String getBuyerNameOnPo() {
    return buyerNameOnPo;
  }

  public String getBuyerAddress1() {
    return buyerAddress1;
  }

  public String getBuyerAddress2() {
    return buyerAddress2;
  }

  public String getBuyerCity() {
    return buyerCity;
  }

  public String getBuyerState() {
    return buyerState;
  }

  public String getBuyerZip() {
    return buyerZip;
  }

  public String getBuyerCountry() {
    return buyerCountry;
  }

  public String getSellerNameOnPo() {
    return sellerNameOnPo;
  }

  public String getSellerIdOnPo() {
    return sellerIdOnPo;
  }

  public String getSellerAddress1() {
    return sellerAddress1;
  }

  public String getSellerAddress2() {
    return sellerAddress2;
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

  public String getShiptoPartyName() {
    return shiptoPartyName;
  }

  public String getShiptoPartyId() {
    return shiptoPartyId;
  }

  public String getShiptoAddress1() {
    return shiptoAddress1;
  }

  public String getShiptoAddress2() {
    return shiptoAddress2;
  }

  public String getShiptoAddress3() {
    return shiptoAddress3;
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

  public String getBilltoParty() {
    return billtoParty;
  }

  public String getBilltoPartyId() {
    return billtoPartyId;
  }

  public String getBilltoName2() {
    return billtoName2;
  }

  public String getBilltoAddress1() {
    return billtoAddress1;
  }

  public String getBilltoAddress2() {
    return billtoAddress2;
  }

  public String getBilltoAddress3() {
    return billtoAddress3;
  }

  public String getBilltoCity() {
    return billtoCity;
  }

  public String getBilltoState() {
    return billtoState;
  }

  public String getBilltoZip() {
    return billtoZip;
  }

  public String getBilltoCountry() {
    return billtoCountry;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public BigDecimal getSuggestedDbuyContractId() {
    return suggestedDbuyContractId;
  }

  public BigDecimal getSuggestedQuantity() {
    return suggestedQuantity;
  }

  public String getAccountSysId() {
    return accountSysId;
  }

  public String getChargeType() {
    return chargeType;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getApplication() {
    return application;
  }

  public String getInterchangeControlNumber() {
    return interchangeControlNumber;
  }

  public BigDecimal getSumQtyForAllLines() {
    return sumQtyForAllLines;
  }
/*
  public BigDecimal getOriginalQuantityOrdered() {
    return originalQuantityOrdered;
  }

  public BigDecimal getOriginalUnitPrice() {
    return originalUnitPrice;
  }

  public Date getOrigRequestedDeliveryDate() {
    return origRequestedDeliveryDate;
  }

  public BigDecimal getOrigQuantity() {
    return origQuantity;
  }
*/
  public BigDecimal getFromLoadId() {
    return fromLoadId;
  }

  public BigDecimal getFromLoadLine() {
    return fromLoadLine;
  }

  public Collection getCustomerPoPreStageBeanCollection() {
    return this.customerPoPreStageBeanCollection;
  }
}