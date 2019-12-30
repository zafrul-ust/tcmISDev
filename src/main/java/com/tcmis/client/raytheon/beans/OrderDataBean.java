package com.tcmis.client.raytheon.beans;

import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: OrderDataBean <br>
 * @version: 1.0, Jun 28, 2005 <br>
 *****************************************************************************/

public class OrderDataBean
    extends BaseDataBean {

  private java.lang.String buyerOrderNumber;
  private java.lang.String orderIssueDate;
  private java.lang.String releaseNumber;
  private java.lang.String purposeCoded;
  private java.lang.String requestResponseCoded;
  private java.lang.String orderTypeCoded;
  private java.lang.String orderCurrencyCoded;
  private java.lang.String orderLanguageCoded;
  private java.lang.String orderPartyAgencyCoded;
  private java.lang.String orderPartyIdent;
  private java.lang.String orderPartyName1;
  private java.lang.String orderPartyName2;
  private java.lang.String orderPartyName3;
  private java.lang.String orderPartyStreet;
  private java.lang.String orderPartyPostalCode;
  private java.lang.String orderPartyCity;
  private java.lang.String orderPartyRegionCoded;
  private java.lang.String orderPartyCountryCoded;
  private java.lang.String orderPartyContactName;
  private java.lang.String orderPartyContactFunctionCoded;
  private java.lang.String sellerPartyAgencyCoded;
  private java.lang.String sellerPartyIdent;
  private java.lang.String sellerPartyName1;
  private java.lang.String sellerPartyName2;
  private java.lang.String sellerPartyStreet;
  private java.lang.String sellerPartyPostalCode;
  private java.lang.String sellerPartyCity;
  private java.lang.String sellerPartyRegionCoded;
  private java.lang.String sellerPartyCountryCoded;
  private java.lang.String shipToPartyAgencyCoded;
  private java.lang.String shipToPartyIdent;
  private java.lang.String shipToPartyName1;
  private java.lang.String shipToPartyName2;
  private java.lang.String shipToPartyStreet;
  private java.lang.String shipToPartyPostalCode;
  private java.lang.String shipToPartyCity;
  private java.lang.String shipToPartyRegionCoded;
  private java.lang.String shipToPartyCountryCoded;
  private java.lang.String shipToPartyContactName;
  private java.lang.String shipToPartyContactFunctionCoded;
  private java.lang.String billToPartyAgencyCoded;
  private java.lang.String billToPartyIdent;
  private java.lang.String billToPartyName1;
  private java.lang.String billToPartyName2;
  private java.lang.String billToPartyStreet;
  private java.lang.String billToPartyPostalCode;
  private java.lang.String billToPartyCity;
  private java.lang.String billToPartyRegionCoded;
  private java.lang.String billToPartyCountryCoded;
  private java.lang.String termsOfDeliveryFunctionCoded;
  private java.lang.String transportTermsCoded;
  private java.lang.String shipmentMethodOfPaymentCoded;
  private java.lang.String locationQualifierCoded;
  private java.lang.String locationQualifierCodedOther;
  private java.lang.String locationAgencyCoded;
  private java.lang.String paymentTermCoded;
  private java.lang.String paymentTermsNote;
  private java.lang.String paymentMeanCoded;
  private java.lang.String monetaryAmount;
  private java.util.Collection orderTermsOfDeliveryColl = new Vector();
  private java.util.Collection messageIDColl = new Vector();
  private java.util.Collection contractColl = new Vector();
  private java.util.Collection orderReferenceColl = new Vector();
  private java.util.Collection dateCodedColl = new Vector();
  private java.util.Collection identifierColl = new Vector();
  private java.util.Collection buyerPartyContactNumberColl = new Vector();
  private java.util.Collection shipToPartyContactNumberColl = new Vector();
  private java.util.Collection partyCodedColl = new Vector();
  private java.util.Collection transportColl = new Vector();
  private java.util.Collection transportEquipmentColl = new Vector();
  private java.util.Collection dimensionColl = new Vector();
  private java.util.Collection sealInfoColl = new Vector();
  private java.util.Collection priceColl = new Vector();
  private java.util.Collection paymentTermsColl = new Vector();
  private java.util.Collection paymentMethodColl = new Vector();
  private java.util.Collection referenceColl = new Vector();
  private java.util.Collection allowOrChargeColl = new Vector();
  private java.util.Collection structuredNoteColl = new Vector();
  private java.util.Collection nameValueSetColl = new Vector();
  private java.util.Collection itemDetailColl = new Vector();
  private java.util.Collection productIdentifierCodedColl = new Vector();
  private java.util.Collection quantityCodedColl = new Vector();
  private java.util.Collection contractItemColl = new Vector();
  private java.util.Collection salesRequirementColl = new Vector();
  private java.util.Collection hazardousIdentifiersColl = new Vector();
  private java.util.Collection taxColl = new Vector();
  private java.util.Collection termsOfDeliveryColl = new Vector();
  private java.util.Collection scheduleLineColl = new Vector();
  private java.util.Collection statusReasonColl = new Vector();
  private java.util.Collection shipToSubInformationColl = new Vector();
  private java.util.Collection packageReferenceColl = new Vector();
  private java.util.Collection packageDetailColl = new Vector();
  private java.util.Collection packageColl = new Vector();
  private java.util.Collection orderReferencesColl = new Vector();
  private java.util.Collection packageMarkColl = new Vector();
  private java.util.Collection packageCharacteristicColl = new Vector();
  private java.util.Collection packageDescriptionColl = new Vector();
  private java.util.Collection packageIdentifierColl = new Vector();
  private java.util.Collection documentLooseColl = new Vector();
  private java.util.Collection documentAttachedColl = new Vector();

  //constructor
  public OrderDataBean() {
  }

  //setters
  public void setBuyerOrderNumber(java.lang.String buyerOrderNumber) {
    this.buyerOrderNumber = buyerOrderNumber;
  }

  public void setOrderIssueDate(java.lang.String orderIssueDate) {
    this.orderIssueDate = orderIssueDate;
  }

  public void setReleaseNumber(java.lang.String releaseNumber) {
    this.releaseNumber = releaseNumber;
  }

  public void setPurposeCoded(java.lang.String purposeCoded) {
    this.purposeCoded = purposeCoded;
  }

  public void setRequestResponseCoded(java.lang.String requestResponseCoded) {
    this.requestResponseCoded = requestResponseCoded;
  }

  public void setOrderTypeCoded(java.lang.String orderTypeCoded) {
    this.orderTypeCoded = orderTypeCoded;
  }

  public void setOrderCurrencyCoded(java.lang.String orderCurrencyCoded) {
    this.orderCurrencyCoded = orderCurrencyCoded;
  }

  public void setOrderLanguageCoded(java.lang.String orderLanguageCoded) {
    this.orderLanguageCoded = orderLanguageCoded;
  }

  public void setOrderPartyAgencyCoded(java.lang.String orderPartyAgencyCoded) {
    this.orderPartyAgencyCoded = orderPartyAgencyCoded;
  }

  public void setOrderPartyIdent(java.lang.String orderPartyIdent) {
    this.orderPartyIdent = orderPartyIdent;
  }

  public void setOrderPartyName1(java.lang.String orderPartyName1) {
    this.orderPartyName1 = orderPartyName1;
  }

  public void setOrderPartyName2(java.lang.String orderPartyName2) {
    this.orderPartyName2 = orderPartyName2;
  }

  public void setOrderPartyName3(java.lang.String orderPartyName3) {
    this.orderPartyName3 = orderPartyName3;
  }

  public void setOrderPartyStreet(java.lang.String orderPartyStreet) {
    this.orderPartyStreet = orderPartyStreet;
  }

  public void setOrderPartyPostalCode(java.lang.String orderPartyPostalCode) {
    this.orderPartyPostalCode = orderPartyPostalCode;
  }

  public void setOrderPartyCity(java.lang.String orderPartyCity) {
    this.orderPartyCity = orderPartyCity;
  }

  public void setOrderPartyRegionCoded(java.lang.String orderPartyRegionCoded) {
    this.orderPartyRegionCoded = orderPartyRegionCoded;
  }

  public void setOrderPartyCountryCoded(java.lang.String orderPartyCountryCoded) {
    this.orderPartyCountryCoded = orderPartyCountryCoded;
  }

  public void setOrderPartyContactName(java.lang.String orderPartyContactName) {
    this.orderPartyContactName = orderPartyContactName;
  }

  public void setOrderPartyContactFunctionCoded(java.lang.String
                                                orderPartyContactFunctionCoded) {
    this.orderPartyContactFunctionCoded = orderPartyContactFunctionCoded;
  }

  public void setSellerPartyAgencyCoded(java.lang.String sellerPartyAgencyCoded) {
    this.sellerPartyAgencyCoded = sellerPartyAgencyCoded;
  }

  public void setSellerPartyIdent(java.lang.String sellerPartyIdent) {
    this.sellerPartyIdent = sellerPartyIdent;
  }

  public void setSellerPartyName1(java.lang.String sellerPartyName1) {
    this.sellerPartyName1 = sellerPartyName1;
  }

  public void setSellerPartyName2(java.lang.String sellerPartyName2) {
    this.sellerPartyName2 = sellerPartyName2;
  }

  public void setSellerPartyStreet(java.lang.String sellerPartyStreet) {
    this.sellerPartyStreet = sellerPartyStreet;
  }

  public void setSellerPartyPostalCode(java.lang.String sellerPartyPostalCode) {
    this.sellerPartyPostalCode = sellerPartyPostalCode;
  }

  public void setSellerPartyCity(java.lang.String sellerPartyCity) {
    this.sellerPartyCity = sellerPartyCity;
  }

  public void setSellerPartyRegionCoded(java.lang.String sellerPartyRegionCoded) {
    this.sellerPartyRegionCoded = sellerPartyRegionCoded;
  }

  public void setSellerPartyCountryCoded(java.lang.String sellerPartyCountryCoded) {
    this.sellerPartyCountryCoded = sellerPartyCountryCoded;
  }

  public void setShipToPartyAgencyCoded(java.lang.String shipToPartyAgencyCoded) {
    this.shipToPartyAgencyCoded = shipToPartyAgencyCoded;
  }

  public void setShipToPartyIdent(java.lang.String shipToPartyIdent) {
    this.shipToPartyIdent = shipToPartyIdent;
  }

  public void setShipToPartyName1(java.lang.String shipToPartyName1) {
    this.shipToPartyName1 = shipToPartyName1;
  }

  public void setShipToPartyName2(java.lang.String shipToPartyName2) {
    this.shipToPartyName2 = shipToPartyName2;
  }

  public void setShipToPartyStreet(java.lang.String shipToPartyStreet) {
    this.shipToPartyStreet = shipToPartyStreet;
  }

  public void setShipToPartyPostalCode(java.lang.String shipToPartyPostalCode) {
    this.shipToPartyPostalCode = shipToPartyPostalCode;
  }

  public void setShipToPartyCity(java.lang.String shipToPartyCity) {
    this.shipToPartyCity = shipToPartyCity;
  }

  public void setShipToPartyRegionCoded(java.lang.String shipToPartyRegionCoded) {
    this.shipToPartyRegionCoded = shipToPartyRegionCoded;
  }

  public void setShipToPartyCountryCoded(java.lang.String shipToPartyCountryCoded) {
    this.shipToPartyCountryCoded = shipToPartyCountryCoded;
  }

  public void setShipToPartyContactName(java.lang.String shipToPartyContactName) {
    this.shipToPartyContactName = shipToPartyContactName;
  }

  public void setShipToPartyContactFunctionCoded(java.lang.String
                                                 shipToPartyContactFunctionCoded) {
    this.shipToPartyContactFunctionCoded = shipToPartyContactFunctionCoded;
  }

  public void setBillToPartyAgencyCoded(java.lang.String billToPartyAgencyCoded) {
    this.billToPartyAgencyCoded = billToPartyAgencyCoded;
  }

  public void setBillToPartyIdent(java.lang.String billToPartyIdent) {
    this.billToPartyIdent = billToPartyIdent;
  }

  public void setBillToPartyName1(java.lang.String billToPartyName1) {
    this.billToPartyName1 = billToPartyName1;
  }

  public void setBillToPartyName2(java.lang.String billToPartyName2) {
    this.billToPartyName2 = billToPartyName2;
  }

  public void setBillToPartyStreet(java.lang.String billToPartyStreet) {
    this.billToPartyStreet = billToPartyStreet;
  }

  public void setBillToPartyPostalCode(java.lang.String billToPartyPostalCode) {
    this.billToPartyPostalCode = billToPartyPostalCode;
  }

  public void setBillToPartyCity(java.lang.String billToPartyCity) {
    this.billToPartyCity = billToPartyCity;
  }

  public void setBillToPartyRegionCoded(java.lang.String billToPartyRegionCoded) {
    this.billToPartyRegionCoded = billToPartyRegionCoded;
  }

  public void setBillToPartyCountryCoded(java.lang.String billToPartyCountryCoded) {
    this.billToPartyCountryCoded = billToPartyCountryCoded;
  }

  public void setTermsOfDeliveryFunctionCoded(java.lang.String
                                              termsOfDeliveryFunctionCoded) {
    this.termsOfDeliveryFunctionCoded = termsOfDeliveryFunctionCoded;
  }

  public void setTransportTermsCoded(java.lang.String transportTermsCoded) {
    this.transportTermsCoded = transportTermsCoded;
  }

  public void setShipmentMethodOfPaymentCoded(java.lang.String
                                              shipmentMethodOfPaymentCoded) {
    this.shipmentMethodOfPaymentCoded = shipmentMethodOfPaymentCoded;
  }

  public void setLocationQualifierCoded(java.lang.String locationQualifierCoded) {
    this.locationQualifierCoded = locationQualifierCoded;
  }

  public void setLocationQualifierCodedOther(java.lang.String locationQualifierCodedOther) {
    this.locationQualifierCodedOther = locationQualifierCodedOther;
  }

  public void setLocationAgencyCoded(java.lang.String locationAgencyCoded) {
    this.locationAgencyCoded = locationAgencyCoded;
  }

  public void setMonetaryAmount(java.lang.String monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
  }

  public void setPaymentTermCoded(java.lang.String paymentTermCoded) {
    this.paymentTermCoded = paymentTermCoded;
  }

  public void setPaymentTermsNote(java.lang.String paymentTermsNote) {
    this.paymentTermsNote = paymentTermsNote;
  }

  public void setPaymentMeanCoded(java.lang.String paymentMeanCoded) {
    this.paymentMeanCoded = paymentMeanCoded;
  }

  public void addOrderTermsOfDeliveryDataBean(OrderTermsOfDeliveryDataBean bean) {
    this.orderTermsOfDeliveryColl.add(bean);
  }

  public void setOrderTermsOfDeliveryColl(java.util.Collection orderTermsOfDeliveryColl) {
    this.orderTermsOfDeliveryColl = orderTermsOfDeliveryColl;
  }

  public void addMessageIDDataBean(MessageIDDataBean bean) {
    this.messageIDColl.add(bean);
  }

  public void setMessageIDColl(java.util.Collection messageIDColl) {
    this.messageIDColl = messageIDColl;
  }

  public void addContractDataBean(ContractDataBean bean) {
    this.contractColl.add(bean);
  }

  public void setContractColl(java.util.Collection contractColl) {
    this.contractColl = contractColl;
  }

  public void addOrderReferenceDataBean(OrderReferenceDataBean bean) {
    orderReferenceColl.add(bean);
  }

  public void setOrderReferenceColl(java.util.Collection orderReferenceColl) {
    this.orderReferenceColl = orderReferenceColl;
  }

  public void addDateCodedDataBean(DateCodedDataBean bean) {
    this.dateCodedColl.add(bean);
  }

  public void setDateCodedColl(java.util.Collection dateCodedColl) {
    this.dateCodedColl = dateCodedColl;
  }

  public void addIdentifierDataBean(IdentifierDataBean bean) {
    this.identifierColl.add(bean);
  }

  public void setIdentifierColl(java.util.Collection identifierColl) {
    this.identifierColl = identifierColl;
  }

  public void addBuyerPartyContactNumberDataBean(ContactNumberDataBean bean) {
    this.buyerPartyContactNumberColl.add(bean);
  }

  public void setBuyerPartyContactNumberColl(java.util.Collection contactNumberColl) {
    this.buyerPartyContactNumberColl = contactNumberColl;
  }

  public void addShipToPartyContactNumberDataBean(ContactNumberDataBean bean) {
    this.shipToPartyContactNumberColl.add(bean);
  }

  public void setShipToPartyContactNumberColl(java.util.Collection contactNumberColl) {
    this.shipToPartyContactNumberColl = contactNumberColl;
  }

  public void addPartyCodedDataBean(PartyCodedDataBean bean) {
    this.partyCodedColl.add(bean);
  }

  public void setPartyCodedColl(java.util.Collection partyCodedColl) {
    this.partyCodedColl = partyCodedColl;
  }

  public void addTransportDataBean(TransportDataBean bean) {
    this.transportColl.add(bean);
  }

  public void setTransportColl(java.util.Collection transportColl) {
    this.transportColl = transportColl;
  }

  public void addTransportEquipmentDataBean(TransportEquipmentDataBean bean) {
    this.transportEquipmentColl.add(bean);
  }

  public void setTransportEquipmentColl(java.util.Collection transportEquipmentColl) {
    this.transportEquipmentColl = transportEquipmentColl;
  }

  public void addDimensionDataBean(DimensionDataBean bean) {
    this.dimensionColl.add(bean);
  }

  public void setDimensionColl(java.util.Collection dimensionColl) {
    this.dimensionColl = dimensionColl;
  }

  public void addSealInfoDataBean(SealInfoDataBean bean) {
    this.sealInfoColl.add(bean);
  }

  public void setSealInfoColl(java.util.Collection sealInfoColl) {
    this.sealInfoColl = sealInfoColl;
  }

  public void addPriceDataBean(PriceDataBean bean) {
    this.priceColl.add(bean);
  }

  public void setPriceColl(java.util.Collection priceColl) {
    this.priceColl = priceColl;
  }

  public void addPaymentTermsDataBean(PaymentTermsDataBean bean) {
    this.paymentTermsColl.add(bean);
  }

  public void setPaymentTermsColl(java.util.Collection paymentTermsColl) {
    this.paymentTermsColl = paymentTermsColl;
  }

  public void addPaymentMethodDataBean(PaymentMethodDataBean bean) {
    this.paymentMethodColl.add(bean);
  }

  public void setPaymentMethodColl(java.util.Collection paymentMethodColl) {
    this.paymentMethodColl = paymentMethodColl;
  }

  public void addReferenceDataBean(ReferenceDataBean bean) {
    this.referenceColl.add(bean);
  }

  public void setReferenceColl(java.util.Collection referenceColl) {
    this.referenceColl = referenceColl;
  }

  public void addAllowOrChargeDataBean(AllowOrChargeDataBean bean) {
    this.allowOrChargeColl.add(bean);
  }

  public void setAllowOrChargeColl(java.util.Collection allowOrChargeColl) {
    this.allowOrChargeColl = allowOrChargeColl;
  }

  public void addStructuredNoteDataBean(StructuredNoteDataBean bean) {
    this.structuredNoteColl.add(bean);
  }

  public void setStructuredNoteColl(java.util.Collection structuredNoteColl) {
    this.structuredNoteColl = structuredNoteColl;
  }

  public void addNameValueSetDataBean(NameValueSetDataBean bean) {
    this.nameValueSetColl.add(bean);
  }

  public void setNameValueSetColl(java.util.Collection nameValueSetColl) {
    this.nameValueSetColl = nameValueSetColl;
  }

  public void addItemDetailDataBean(ItemDetailDataBean bean) {
    this.itemDetailColl.add(bean);
  }

  public void setItemDetailColl(java.util.Collection itemDetailColl) {
    this.itemDetailColl = itemDetailColl;
  }

  public void addProductIdentifierCodedDataBean(ProductIdentifierCodedDataBean bean) {
    this.productIdentifierCodedColl.add(bean);
  }

  public void setProductIdentifierCodedColl(java.util.Collection
                                            productIdentifierCodedColl) {
    this.productIdentifierCodedColl = productIdentifierCodedColl;
  }

  public void addQuantityCodedDataBean(QuantityCodedDataBean bean) {
    this.quantityCodedColl.add(bean);
  }

  public void setQuantityCodedColl(java.util.Collection quantityCodedColl) {
    this.quantityCodedColl = quantityCodedColl;
  }

  public void addContractItemDataBean(ContractItemDataBean bean) {
    this.contractItemColl.add(bean);
  }

  public void setContractItemColl(java.util.Collection contractItemColl) {
    this.contractItemColl = contractItemColl;
  }

  public void addSalesRequirementDataBean(SalesRequirementDataBean bean) {
    this.salesRequirementColl.add(bean);
  }

  public void setSalesRequirementColl(java.util.Collection salesRequirementColl) {
    this.salesRequirementColl = salesRequirementColl;
  }

  public void addHazardousIdentifiersDataBean(HazardousIdentifiersDataBean bean) {
    this.hazardousIdentifiersColl.add(bean);
  }

  public void setHazardousIdentifiersColl(java.util.Collection hazardousIdentifiersColl) {
    this.hazardousIdentifiersColl = hazardousIdentifiersColl;
  }

  public void addTaxDataBean(TaxDataBean bean) {
    this.taxColl.add(bean);
  }

  public void setTaxColl(java.util.Collection taxColl) {
    this.taxColl = taxColl;
  }

  public void addTermsOfDeliveryDataBean(TermsOfDeliveryDataBean bean) {
    this.termsOfDeliveryColl.add(bean);
  }

  public void setTermsOfDeliveryColl(java.util.Collection termsOfDeliveryColl) {
    this.termsOfDeliveryColl = termsOfDeliveryColl;
  }

  public void addScheduleLineDataBean(ScheduleLineDataBean bean) {
    this.scheduleLineColl.add(bean);
  }

  public void setScheduleLineColl(java.util.Collection scheduleLineColl) {
    this.scheduleLineColl = scheduleLineColl;
  }

  public void addStatusReasonDataBean(StatusReasonDataBean bean) {
    this.statusReasonColl.add(bean);
  }

  public void setStatusReasonColl(java.util.Collection statusReasonColl) {
    this.statusReasonColl = statusReasonColl;
  }

  public void addShipToSubInformationDataBean(ShipToSubInformationDataBean bean) {
    this.shipToSubInformationColl.add(bean);
  }

  public void setShipToSubInformationColl(java.util.Collection shipToSubInformationColl) {
    this.shipToSubInformationColl = shipToSubInformationColl;
  }

  public void addPackageReferenceDataBean(PackageReferenceDataBean bean) {
    this.packageReferenceColl.add(bean);
  }

  public void setPackageReferenceColl(java.util.Collection packageReferenceColl) {
    this.packageReferenceColl = packageReferenceColl;
  }

  public void addPackageDetailDataBean(PackageDetailDataBean bean) {
    this.packageDetailColl.add(bean);
  }

  public void setPackageDetailColl(java.util.Collection packageDetailColl) {
    this.packageDetailColl = packageDetailColl;
  }

  public void addPackageDataBean(PackageDataBean bean) {
    this.packageColl.add(bean);
  }

  public void setPackageColl(java.util.Collection packageColl) {
    this.packageColl = packageColl;
  }

  public void addOrderReferencesDataBean(OrderReferencesDataBean bean) {
    this.orderReferencesColl.add(bean);
  }

  public void setOrderReferencesColl(java.util.Collection orderReferencesColl) {
    this.orderReferencesColl = orderReferencesColl;
  }

  public void addPackageMarkDataBean(PackageMarkDataBean bean) {
    this.packageMarkColl.add(bean);
  }

  public void setPackageMarkColl(java.util.Collection packageMarkColl) {
    this.packageMarkColl = packageMarkColl;
  }

  public void addPackageCharacteristicDataBean(PackageCharacteristicDataBean bean) {
    this.packageCharacteristicColl.add(bean);
  }

  public void setPackageCharacteristicColl(java.util.Collection packageCharacteristicColl) {
    this.packageCharacteristicColl = packageCharacteristicColl;
  }

  public void addPackageDescriptionDataBean(PackageDescriptionDataBean bean) {
    this.packageDescriptionColl.add(bean);
  }

  public void setPackageDescriptionColl(java.util.Collection packageDescriptionColl) {
    this.packageDescriptionColl = packageDescriptionColl;
  }

  public void addPackageIdentifierDataBean(PackageIdentifierDataBean bean) {
    this.packageIdentifierColl.add(bean);
  }

  public void setPackageIdentifierColl(java.util.Collection packageIdentifierColl) {
    this.packageIdentifierColl = packageIdentifierColl;
  }

  public void addDocumentLooseDataBean(DocumentLooseDataBean bean) {
    this.documentLooseColl.add(bean);
  }

  public void setDocumentLooseColl(java.util.Collection documentLooseColl) {
    this.documentLooseColl = documentLooseColl;
  }

  public void addDocumentAttachedDataBean(DocumentAttachedDataBean bean) {
    this.documentAttachedColl.add(bean);
  }

  public void setDocumentAttachedColl(java.util.Collection documentAttachedColl) {
    this.documentAttachedColl = documentAttachedColl;
  }

  //getters
  public java.lang.String getBuyerOrderNumber() {
    return buyerOrderNumber;
  }

  public java.lang.String getOrderIssueDate() {
    return orderIssueDate;
  }

  public java.lang.String getReleaseNumber() {
    return releaseNumber;
  }

  public java.lang.String getPurposeCoded() {
    return purposeCoded;
  }

  public java.lang.String getRequestResponseCoded() {
    return requestResponseCoded;
  }

  public java.lang.String getOrderTypeCoded() {
    return orderTypeCoded;
  }

  public java.lang.String getOrderCurrencyCoded() {
    return orderCurrencyCoded;
  }

  public java.lang.String getOrderLanguageCoded() {
    return orderLanguageCoded;
  }

  public java.lang.String getOrderPartyAgencyCoded() {
    return orderPartyAgencyCoded;
  }

  public java.lang.String getOrderPartyIdent() {
    return orderPartyIdent;
  }

  public java.lang.String getOrderPartyName1() {
    return orderPartyName1;
  }

  public java.lang.String getOrderPartyName2() {
    return orderPartyName2;
  }

  public java.lang.String getOrderPartyName3() {
    return orderPartyName3;
  }

  public java.lang.String getOrderPartyStreet() {
    return orderPartyStreet;
  }

  public java.lang.String getOrderPartyPostalCode() {
    return orderPartyPostalCode;
  }

  public java.lang.String getOrderPartyCity() {
    return orderPartyCity;
  }

  public java.lang.String getOrderPartyRegionCoded() {
    return orderPartyRegionCoded;
  }

  public java.lang.String getOrderPartyCountryCoded() {
    return orderPartyCountryCoded;
  }

  public java.lang.String getOrderPartyContactName() {
    return orderPartyContactName;
  }

  public java.lang.String getOrderPartyContactFunctionCoded() {
    return orderPartyContactFunctionCoded;
  }

  public java.lang.String getSellerPartyAgencyCoded() {
    return sellerPartyAgencyCoded;
  }

  public java.lang.String getSellerPartyIdent() {
    return sellerPartyIdent;
  }

  public java.lang.String getSellerPartyName1() {
    return sellerPartyName1;
  }

  public java.lang.String getSellerPartyName2() {
    return sellerPartyName2;
  }

  public java.lang.String getSellerPartyStreet() {
    return sellerPartyStreet;
  }

  public java.lang.String getSellerPartyPostalCode() {
    return sellerPartyPostalCode;
  }

  public java.lang.String getSellerPartyCity() {
    return sellerPartyCity;
  }

  public java.lang.String getSellerPartyRegionCoded() {
    return sellerPartyRegionCoded;
  }

  public java.lang.String getSellerPartyCountryCoded() {
    return sellerPartyCountryCoded;
  }

  public java.lang.String getShipToPartyAgencyCoded() {
    return shipToPartyAgencyCoded;
  }

  public java.lang.String getShipToPartyIdent() {
    return shipToPartyIdent;
  }

  public java.lang.String getShipToPartyName1() {
    return shipToPartyName1;
  }

  public java.lang.String getShipToPartyName2() {
    return shipToPartyName2;
  }

  public java.lang.String getShipToPartyStreet() {
    return shipToPartyStreet;
  }

  public java.lang.String getShipToPartyPostalCode() {
    return shipToPartyPostalCode;
  }

  public java.lang.String getShipToPartyCity() {
    return shipToPartyCity;
  }

  public java.lang.String getShipToPartyRegionCoded() {
    return shipToPartyRegionCoded;
  }

  public java.lang.String getShipToPartyCountryCoded() {
    return shipToPartyCountryCoded;
  }

  public java.lang.String getShipToPartyContactName() {
    return shipToPartyContactName;
  }

  public java.lang.String getShipToPartyContactFunctionCoded() {
    return shipToPartyContactFunctionCoded;
  }

  public java.lang.String getBillToPartyAgencyCoded() {
    return billToPartyAgencyCoded;
  }

  public java.lang.String getBillToPartyIdent() {
    return billToPartyIdent;
  }

  public java.lang.String getBillToPartyName1() {
    return billToPartyName1;
  }

  public java.lang.String getBillToPartyName2() {
    return billToPartyName2;
  }

  public java.lang.String getBillToPartyStreet() {
    return billToPartyStreet;
  }

  public java.lang.String getBillToPartyPostalCode() {
    return billToPartyPostalCode;
  }

  public java.lang.String getBillToPartyCity() {
    return billToPartyCity;
  }

  public java.lang.String getBillToPartyRegionCoded() {
    return billToPartyRegionCoded;
  }

  public java.lang.String getBillToPartyCountryCoded() {
    return billToPartyCountryCoded;
  }

  public java.lang.String getTermsOfDeliveryFunctionCoded() {
    return termsOfDeliveryFunctionCoded;
  }

  public java.lang.String getTransportTermsCoded() {
    return transportTermsCoded;
  }

  public java.lang.String getShipmentMethodOfPaymentCoded() {
    return shipmentMethodOfPaymentCoded;
  }

  public java.lang.String getLocationQualifierCoded() {
    return locationQualifierCoded;
  }

  public java.lang.String getLocationQualifierCodedOther() {
    return locationQualifierCodedOther;
  }

  public java.lang.String getLocationAgencyCoded() {
    return locationAgencyCoded;
  }

  public java.lang.String getMonetaryAmount() {
    return monetaryAmount;
  }

  public java.lang.String getPaymentTermCoded() {
    return paymentTermCoded;
  }

  public java.lang.String getPaymentTermsNote() {
    return paymentTermsNote;
  }

  public java.lang.String getPaymentMeanCoded() {
    return paymentMeanCoded;
  }

  public java.util.Collection getOrderTermsOfDeliveryColl() {
    return orderTermsOfDeliveryColl;
  }

  public java.util.Collection getMessageIDColl() {
    return messageIDColl;
  }

  public java.util.Collection getContractColl() {
    return contractColl;
  }

  public java.util.Collection getOrderReferenceColl() {
    return orderReferenceColl;
  }

  public java.util.Collection getDateCodedColl() {
    return dateCodedColl;
  }

  public java.util.Collection getIdentifierColl() {
    return identifierColl;
  }

  public java.util.Collection getBuyerPartyContactNumberColl() {
    return buyerPartyContactNumberColl;
  }

  public java.util.Collection getShipToPartyContactNumberColl() {
    return shipToPartyContactNumberColl;
  }

  public java.util.Collection getPartyCodedColl() {
    return partyCodedColl;
  }

  public java.util.Collection getTransportColl() {
    return transportColl;
  }

  public java.util.Collection getTransportEquipmentColl() {
    return transportEquipmentColl;
  }

  public java.util.Collection getDimensionColl() {
    return dimensionColl;
  }

  public java.util.Collection getSealInfoColl() {
    return sealInfoColl;
  }

  public java.util.Collection getPriceColl() {
    return priceColl;
  }

  public java.util.Collection getPaymentTermsColl() {
    return paymentTermsColl;
  }

  public java.util.Collection getPaymentMethodColl() {
    return paymentMethodColl;
  }

  public java.util.Collection getReferenceColl() {
    return referenceColl;
  }

  public java.util.Collection getAllowOrChargeColl() {
    return allowOrChargeColl;
  }

  public java.util.Collection getStructuredNoteColl() {
    return structuredNoteColl;
  }

  public java.util.Collection getNameValueSetColl() {
    return nameValueSetColl;
  }

  public java.util.Collection getItemDetailColl() {
    return itemDetailColl;
  }

  public java.util.Collection getProductIdentifierCodedColl() {
    return productIdentifierCodedColl;
  }

  public java.util.Collection getQuantityCodedColl() {
    return quantityCodedColl;
  }

  public java.util.Collection getContractItemColl() {
    return contractItemColl;
  }

  public java.util.Collection getSalesRequirementColl() {
    return salesRequirementColl;
  }

  public java.util.Collection getHazardousIdentifiersColl() {
    return hazardousIdentifiersColl;
  }

  public java.util.Collection getTaxColl() {
    return taxColl;
  }

  public java.util.Collection getTermsOfDeliveryColl() {
    return termsOfDeliveryColl;
  }

  public java.util.Collection getScheduleLineColl() {
    return scheduleLineColl;
  }

  public java.util.Collection getStatusReasonColl() {
    return statusReasonColl;
  }

  public java.util.Collection getShipToSubInformationColl() {
    return shipToSubInformationColl;
  }

  public java.util.Collection getPackageReferenceColl() {
    return packageReferenceColl;
  }

  public java.util.Collection getPackageDetailColl() {
    return packageDetailColl;
  }

  public java.util.Collection getPackageColl() {
    return packageColl;
  }

  public java.util.Collection getOrderReferencesColl() {
    return orderReferencesColl;
  }

  public java.util.Collection getPackageMarkColl() {
    return packageMarkColl;
  }

  public java.util.Collection getPackageCharacteristicColl() {
    return packageCharacteristicColl;
  }

  public java.util.Collection getPackageDescriptionColl() {
    return packageDescriptionColl;
  }

  public java.util.Collection getPackageIdentifierColl() {
    return packageIdentifierColl;
  }

  public java.util.Collection getDocumentLooseColl() {
    return documentLooseColl;
  }

  public java.util.Collection getDocumentAttachedColl() {
    return documentAttachedColl;
  }
}