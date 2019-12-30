package com.tcmis.client.raytheon.process;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;
import com.tcmis.client.raytheon.beans.XcblOrderBean;
import com.tcmis.client.raytheon.beans.XcblOrderBuyerContactBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailIdentifierBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailReferenceBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailScheduleBean;
import com.tcmis.client.raytheon.beans.XcblOrderShipToContactBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;

/******************************************************************************
 * Process to translate xcbl change order documents into bean objects.
 * @version 1.0
 *****************************************************************************/
public class XcblChangeOrderParser
    extends BaseProcess {

  public XcblChangeOrderParser(String client) {
    super(client);
  }

  public XcblOrderBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    XcblOrderBean xcblChangeOrderBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("ChangeOrder", XcblOrderBean.class);
      //header section
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/ChangeOrderSequence",
                             "setChangeOrderSequence", 0, bigDecimalClass);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/ChangeOrderIssueDate",
                             "setChangeOrderDateString", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/OrderReference/Reference/RefDate",
          "setOrderIssueDateString", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/ChangeType/ChangeTypeCoded",
                             "setChangeType", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/ChangeType/ChangeTypeCodedOther",
          "setChangeType", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/ChangeOrderNumber/BuyerChangeOrderNumber",
          "setBuyerOrderNumber", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/ReleaseNumber",
                             "setReleaseNumber", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/Purpose/PurposeCoded",
                             "setPurpose", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/OrderCurrency/Currency/CurrencyCoded",
          "setOrderCurrency", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/OrderLanguage/Language/LanguageCoded",
          "setOrderLanguage", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/PartyID/Identifier/Agency/AgencyCoded",
          "setOrderPartyAgency", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/PartyID/Identifier/Ident",
          "setOrderPartyIdent", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/Name1",
          "setOrderPartyName1", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/Name2",
          "setOrderPartyName2", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/Name3",
          "setOrderPartyName3", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/Street",
          "setOrderPartyStreet", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/PostalCode",
          "setOrderPartyPostalCode", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/City",
          "setOrderPartyCity", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/Region/RegionCoded",
          "setOrderPartyRegion", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/NameAddress/Country/CountryCoded",
          "setOrderPartyCountry", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/BuyerParty/Party/OrderContact/Contact/ContactName",
          "setOrderPartyContactName", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/BuyerParty/Party/OrderContact/Contact/ContactFunction/ContactFunctionCoded",
                             "setOrderPartyContactFunction", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/PartyID/Identifier/Agency/AgencyCoded",
          "setSellerAgency", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/PartyID/Identifier/Ident",
          "setSellerIdent", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/NameAddress/Name1",
          "setSellerName1", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/NameAddress/Name2",
          "setSellerName2", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/NameAddress/Street",
          "setSellerStreet", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/NameAddress/PostalCode",
          "setSellerPostalCode", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/NameAddress/City",
          "setSellerCity", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/NameAddress/Region/RegionCoded",
          "setSellerRegion", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/SellerParty/Party/NameAddress/Country/CountryCoded",
          "setSellerCountry", 0);

      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/PartyID/Identifier/Agency/AgencyCoded",
                             "setOrderPartyAgency", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/PartyID/Identifier/Ident",
                             "setOrderPartyIdent", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Name1",
                             "setOrderPartyName1", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Name2",
                             "setOrderPartyName2", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Street",
                             "setOrderPartyStreet", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/PostalCode",
                             "setOrderPartyPostalCode", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/City",
                             "setOrderPartyCity", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Region/RegionCoded",
                             "setOrderPartyRegion", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Country/CountryCoded",
                             "setOrderPartyCountry", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ContactName",
                             "setOrderPartyContactName", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ContactFunction/ContactFunctionCoded",
                             "setOrderPartyContactFunction", 0);

      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/PartyID/Identifier/Agency/AgencyCoded",
                             "setSellerAgency", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/PartyID/Identifier/Ident",
                             "setSellerIdent", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Name1",
                             "setSellerName1", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Name2",
                             "setSellerName2", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Street",
                             "setSellerStreet", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/NameAddress/PostalCode",
                             "setSellerPostalCode", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/NameAddress/City",
                             "setSellerCity", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Region/RegionCoded",
                             "setSellerRegion", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Country/CountryCoded",
                             "setSellerCountry", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/OrderContact/Contact/ContactName",
                             "setSellerContactName", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/SellerParty/Party/OrderContact/Contact/ContactFunction/ContactFunctionCoded",
                             "setSellerContactFunction", 0);

      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/PartyID/Identifier/Agency/AgencyCoded",
                             "setShipToAgency", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/PartyID/Identifier/Ident",
                             "setShipToIdent", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Name1",
                             "setShipToName1", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Name2",
                             "setShipToName2", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Street",
                             "setShipToStreet", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/PostalCode",
                             "setShipToPostalCode", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/City",
                             "setShipToCity", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Region/RegionCoded",
                             "setShipToRegion", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Country/CountryCoded",
                             "setShipToCountry", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ContactName",
                             "setShipToContactName", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ContactFunction/ContactFunctionCoded",
                             "setShipToContactFunction", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/PartyID/Identifier/Agency/AgencyCoded",
                             "setBillToAgency", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/PartyID/Identifier/Ident",
                             "setBillToIdent", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Name1",
                             "setBillToName1", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Name2",
                             "setBillToName2", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Street",
                             "setBillToStreet", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/NameAddress/PostalCode",
                             "setBillToPostalCode", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/NameAddress/City",
                             "setBillToCity", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Region/RegionCoded",
                             "setBillToRegion", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Country/CountryCoded",
                             "setBillToCountry", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/RequestedResponse/RequestedResponseCoded",
                             "setRequestResponse", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderType/OrderTypeCoded",
          "setOrderType", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/TermsOfDeliveryFunctionCoded",
                             "setTermsOfDeliveryFunction", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/TransportTermsCoded",
                             "setTransportTerms", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/ShipmentMethodOfPaymentCoded",
                             "setShipmentMethodOfPayment", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/Location/LocationQualifierCoded",
                             "setLocationQualifier", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/Location/LocationQualifierCodedOther",
                             "setLocationQualifier", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/Location/LocationIdentifier/LocID/Identifier/Agency/AgencyCoded",
                             "setLocationAgency", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentTerms/PaymentTerm/PaymentTermCoded",
                             "setPaymentTerm", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentTerms/PaymentTerm/PaymentTermsNote",
                             "setPaymentTermsNote", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentMethod/PaymentMeanCoded",
                             "setPaymentMean", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentMethod/PaymentMeanCodedOther",
                             "setPaymentMean", 0);
      digester.addCallMethod(
          "ChangeOrder/ChangeOrderHeader/OrderReferences/AccountCode/Reference/RefNum",
          "setAccountCodeRefNum", 0);
      digester.addObjectCreate("ChangeOrder/ChangeOrderHeader/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                               XcblOrderBuyerContactBean.class);
      digester.addSetNext("ChangeOrder/ChangeOrderHeader/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                          "addXcblOrderBuyerContactBean");
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberValue",
                             "setValue", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberTypeCoded",
                             "setKey", 0);
      digester.addObjectCreate("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                               XcblOrderShipToContactBean.class);
      digester.addSetNext("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                          "addXcblOrderShipToContactBean");
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberValue",
                             "setValue", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberTypeCoded",
                             "setKey", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderHeader/OrderHeaderChanges/OrderHeader/ListOfStructuredNote/StructuredNote/GeneralNote",
                             "addNotes", 0);
      //order detail section
      digester.addObjectCreate(
          "ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail",
          XcblOrderDetailBean.class);
      digester.addSetNext(
          "ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail",
          "addXcblOrderDetailBean");
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/LineItemNum/BuyerLineItemNum",
                             "setBuyerLineItemNum", 0, bigDecimalClass);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/SellerPartNumber/PartNum/PartID",
                             "setSellerPartId", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/BuyerPartNumber/PartNum/PartID",
                             "setBuyerPartId", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/ManufacturerPartNumber/PartID",
                             "setManufacturerPartId", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/ItemDescription",
                             "setItemDescription", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/TotalQuantity/Quantity/QuantityValue",
                             "setQuantity", 0, bigDecimalClass);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/TotalQuantity/Quantity/UnitOfMeasurement/UOMCoded",
                             "setUom", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/TotalQuantity/Quantity/UnitOfMeasurement/UOMCodedOther",
                             "setUom", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/OffCatalogFlag",
                             "setOffCatalogFlag", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/PricingDetail/ListOfPrice/Price/UnitPrice/UnitPriceValue",
                             "setUnitPrice", 0, bigDecimalClass);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/PricingDetail/ListOfPrice/Price/UnitPrice/Currency/CurrencyCoded",
                             "setCurrency", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/PricingDetail/ListOfPrice/Price/PriceBasisQuantity/Quantity/QuantityValue",
                             "setPriceBasisQuantity", 0, bigDecimalClass);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/PricingDetail/ListOfPrice/Price/PriceBasisQuantity/Quantity/UnitOfMeasurement/UOMCoded",
                             "setPriceBasisUom", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/PricingDetail/ListOfPrice/Price/PriceBasisQuantity/Quantity/UnitOfMeasurement/UOMCodedOther",
                             "setPriceBasisUom", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/ListOfStructuredNote/StructuredNote/GeneralNote",
                             "addNotes", 0);
      digester.addObjectCreate("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded",
                               XcblOrderDetailIdentifierBean.class);
      digester.addSetNext("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded",
                          "addXcblOrderDetailIdentifierBean");
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded/ProductIdentifier",
                             "setKey", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded/ProductIdentifierQualifierCoded",
                             "setValue", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded/ProductIdentifierQualifierCodedOther",
                             "setValue", 0);
      digester.addObjectCreate("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded",
                               XcblOrderDetailReferenceBean.class);
      digester.addSetNext("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded",
                          "addXcblOrderDetailReferenceBean");
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded/ReferenceTypeCoded",
                             "setKey", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded/ReferenceTypeCodedOther",
                             "setKey", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded/PrimaryReference/Reference/RefNum",
                             "setValue", 0);

//      digester.addSetRoot("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Identifier/Ident",
//                          "setShipToIdent", "java.lang.String");

      digester.addObjectCreate("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail",
                               XcblOrderDetailScheduleBean.class);
      digester.addSetNext("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine",
                          "addXcblOrderDetailScheduleBean");
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/Quantity/QuantityValue",
                             "setQuantity", 0, bigDecimalClass);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/ScheduleLineID",
                             "setScheduleLineId", 0, bigDecimalClass);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/Quantity/UnitOfMeasurement/UOMCoded",
                             "setUom", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/Quantity/UnitOfMeasurement/UOMCodedOther",
                             "setUom", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/RequestedDeliveryDate",
                             "setRequestedDeliveryDateString", 0);

      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Name1",
                             "setShipToName1", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Name2",
                             "setShipToName2", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Street",
                             "setShipToStreet", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/PostalCode",
                             "setShipToPostalCode", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/City",
                             "setShipToCity", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Region/RegionCodedOther",
                             "setShipToRegion", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Country/CountryCoded",
                             "setShipToCountry", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Identifier/Agency/AgencyCoded",
                             "setShipToAgency", 0);
      digester.addCallMethod("ChangeOrder/ChangeOrderDetail/ListOfChangeOrderItemDetail/ChangeOrderItemDetail/ItemDetailChanges/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Identifier/Ident",
                             "setShipToIdent", 0);


      //order summary section
      digester.addCallMethod("ChangeOrder/ChangeOrderSummary/RevisedOrderSummary/OrderSummary/TotalAmount/MonetaryValue/MonetaryAmount",
                             "setMonetaryAmount", 0, bigDecimalClass);
      digester.parse(file);
      xcblChangeOrderBean = (XcblOrderBean) digester.getRoot();
    }
    catch (Exception e) {
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("currency.error.load");
      be.setRootCause(e);
      throw be;
    }
    return xcblChangeOrderBean;
  }

}