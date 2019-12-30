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
 * Process to translate xcbl order documents into bean objects.
 * @version 1.0
 *****************************************************************************/
public class XcblOrderParser
    extends BaseProcess {

  public XcblOrderParser(String client) {
    super(client);
  }

  public XcblOrderBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    XcblOrderBean orderDataBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("Order", XcblOrderBean.class);
      //order header section
      digester.addCallMethod("Order/OrderHeader/OrderNumber/BuyerOrderNumber",
                             "setBuyerOrderNumber", 0);
      digester.addCallMethod("Order/OrderHeader/OrderIssueDate", "setOrderIssueDateString", 0);
      digester.addCallMethod("Order/OrderHeader/ReleaseNumber", "setReleaseNumber", 0);
      digester.addCallMethod("Order/OrderHeader/Purpose/PurposeCoded", "setPurpose", 0);
      digester.addCallMethod("Order/OrderHeader/RequestedResponse/RequestedResponseCoded",
                             "setRequestResponse", 0);
      digester.addCallMethod("Order/OrderHeader/OrderType/OrderTypeCoded", "setOrderType",
                             0);
      digester.addCallMethod("Order/OrderHeader/OrderCurrency/Currency/CurrencyCoded",
                             "setOrderCurrency", 0);
      digester.addCallMethod("Order/OrderHeader/OrderLanguage/Language/LanguageCoded",
                             "setOrderLanguage", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/PartyID/Identifier/Agency/AgencyCoded",
          "setOrderPartyAgency", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/PartyID/Identifier/Ident",
          "setOrderPartyIdent", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Name1",
          "setOrderPartyName1", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Name2",
          "setOrderPartyName2", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Name3",
          "setOrderPartyName3", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Street",
          "setOrderPartyStreet", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/PostalCode",
          "setOrderPartyPostalCode", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/City",
          "setOrderPartyCity", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Region/RegionCoded",
          "setOrderPartyRegion", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/NameAddress/Country/CountryCoded",
          "setOrderPartyCountry", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ContactName",
          "setOrderPartyContactName", 0);
      digester.addCallMethod("Order/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ContactFunction/ContactFunctionCoded",
                             "setOrderPartyContactFunction", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/PartyID/Identifier/Agency/AgencyCoded",
          "setSellerAgency", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/PartyID/Identifier/Ident",
          "setSellerIdent", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Name1",
          "setSellerName1", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Name2",
          "setSellerName2", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Street",
          "setSellerStreet", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/NameAddress/PostalCode",
          "setSellerPostalCode", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/NameAddress/City",
          "setSellerCity", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Region/RegionCoded",
          "setSellerRegion", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/SellerParty/Party/NameAddress/Country/CountryCoded",
          "setSellerCountry", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/PartyID/Identifier/Agency/AgencyCoded",
          "setShipToAgency", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/PartyID/Identifier/Ident",
          "setShipToIdent", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Name1",
          "setShipToName1", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Name2",
          "setShipToName2", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Street",
          "setShipToStreet", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/PostalCode",
          "setShipToPostalCode", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/City",
          "setShipToCity", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Region/RegionCoded",
          "setShipToRegion", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/NameAddress/Country/CountryCoded",
          "setShipToCountry", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ContactName",
          "setShipToContactName", 0);
      digester.addCallMethod("Order/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ContactFunction/ContactFunctionCoded",
                             "setShipToContactFunction", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/PartyID/Identifier/Agency/AgencyCoded",
          "setBillToAgency", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/PartyID/Identifier/Ident",
          "setBillToIdent", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Name1",
          "setBillToName1", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Name2",
          "setBillToName2", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Street",
          "setBillToStreet", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/NameAddress/PostalCode",
          "setBillToPostalCode", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/NameAddress/City",
          "setBillToCity", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Region/RegionCoded",
          "setBillToRegion", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderParty/BillToParty/Party/NameAddress/Country/CountryCoded",
          "setBillToCountry", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/TermsOfDeliveryFunctionCoded",
          "setTermsOfDeliveryFunction", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/TransportTermsCoded",
          "setTransportTerms", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/ShipmentMethodOfPaymentCoded",
          "setShipmentMethodOfPayment", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/Location/LocationQualifierCoded",
          "setLocationQualifier", 0);
      digester.addCallMethod("Order/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/Location/LocationQualifierCodedOther",
                             "setLocationQualifier", 0);
      digester.addCallMethod("Order/OrderHeader/OrderTermsOfDelivery/TermsOfDelivery/Location/LocationIdentifier/LocID/Identifier/Agency/AgencyCoded",
                             "setLocationAgency", 0);
      digester.addCallMethod("Order/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentTerms/PaymentTerm/PaymentTermCoded",
                             "setPaymentTerm", 0);
      digester.addCallMethod("Order/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentTerms/PaymentTerm/PaymentTermsNote",
                             "setPaymentTermsNote", 0);
      digester.addCallMethod("Order/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentMethod/PaymentMeanCoded",
                             "setPaymentMean", 0);
      digester.addCallMethod("Order/OrderHeader/OrderPaymentInstructions/PaymentInstructions/PaymentMethod/PaymentMeanCodedOther",
                             "setPaymentMean", 0);
      digester.addCallMethod(
          "Order/OrderHeader/OrderReferences/AccountCode/Reference/RefNum",
          "setAccountCodeRefNum", 0);

      digester.addObjectCreate("Order/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                               XcblOrderBuyerContactBean.class);
      digester.addSetNext("Order/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                          "addXcblOrderBuyerContactBean");
      digester.addCallMethod("Order/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberValue",
                             "setValue", 0);
      digester.addCallMethod("Order/OrderHeader/OrderParty/BuyerParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberTypeCoded",
                             "setKey", 0);
      digester.addObjectCreate("Order/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                               XcblOrderShipToContactBean.class);
      digester.addSetNext("Order/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber",
                          "addXcblOrderShipToContactBean");
      digester.addCallMethod("Order/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberValue",
                             "setValue", 0);
      digester.addCallMethod("Order/OrderHeader/OrderParty/ShipToParty/Party/OrderContact/Contact/ListOfContactNumber/ContactNumber/ContactNumberTypeCoded",
                             "setKey", 0);
      digester.addCallMethod(
          "Order/OrderHeader/ListOfStructuredNote/StructuredNote/GeneralNote", "addNotes",
          0);
      //order detail section
      digester.addObjectCreate("Order/OrderDetail/ListOfItemDetail/ItemDetail",
                               XcblOrderDetailBean.class);
      digester.addSetNext("Order/OrderDetail/ListOfItemDetail/ItemDetail",
                          "addXcblOrderDetailBean");
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/LineItemNum/BuyerLineItemNum",
                             "setBuyerLineItemNum", 0, bigDecimalClass);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/SellerPartNumber/PartNum/PartID",
                             "setSellerPartId", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/BuyerPartNumber/PartNum/PartID",
                             "setBuyerPartId", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/ManufacturerPartNumber/PartID",
                             "setManufacturerPartId", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/ItemDescription",
                             "setItemDescription", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/TotalQuantity/Quantity/QuantityValue",
                             "setQuantity", 0, bigDecimalClass);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/TotalQuantity/Quantity/UnitOfMeasurement/UOMCoded",
                             "setUom", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/TotalQuantity/Quantity/UnitOfMeasurement/UOMCodedOther",
                             "setUom", 0);
      digester.addCallMethod(
          "Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/OffCatalogFlag",
          "setOffCatalogFlag", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/PricingDetail/ListOfPrice/Price/UnitPrice/UnitPriceValue",
                             "setUnitPrice", 0, bigDecimalClass);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/PricingDetail/ListOfPrice/Price/UnitPrice/Currency/CurrencyCoded",
                             "setCurrency", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/PricingDetail/ListOfPrice/Price/PriceBasisQuantity/Quantity/QuantityValue",
                             "setPriceBasisQuantity", 0, bigDecimalClass);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/PricingDetail/ListOfPrice/Price/PriceBasisQuantity/Quantity/UnitOfMeasurement/UOMCoded",
                             "setPriceBasisUom", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/PricingDetail/ListOfPrice/Price/PriceBasisQuantity/Quantity/UnitOfMeasurement/UOMCodedOther",
                             "setPriceBasisUom", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/ListOfStructuredNote/StructuredNote/GeneralNote",
                             "addNotes", 0);
      digester.addObjectCreate("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded",
                               XcblOrderDetailIdentifierBean.class);
      digester.addSetNext("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded",
                          "addXcblOrderDetailIdentifierBean");
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded/ProductIdentifier",
                             "setKey", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded/ProductIdentifierQualifierCoded",
                             "setValue", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ItemIdentifiers/PartNumbers/OtherItemIdentifiers/ListOfProductIdentifierCoded/ProductIdentifierCoded/ProductIdentifierQualifierCodedOther",
                             "setValue", 0);
      digester.addObjectCreate("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded",
                               XcblOrderDetailReferenceBean.class);
      digester.addSetNext("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded",
                          "addXcblOrderDetailReferenceBean");
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded/ReferenceTypeCoded",
                             "setKey", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded/ReferenceTypeCodedOther",
                             "setKey", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/BaseItemDetail/ListOfItemReferences/ListOfReferenceCoded/ReferenceCoded/PrimaryReference/Reference/RefNum",
                             "setValue", 0);
      digester.addObjectCreate("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail",
                               XcblOrderDetailScheduleBean.class);
      digester.addSetNext("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine",
                          "addXcblOrderDetailScheduleBean");
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/Quantity/QuantityValue",
                             "setQuantity", 0, bigDecimalClass);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/ScheduleLineID",
                             "setScheduleLineId", 0, bigDecimalClass);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/Quantity/UnitOfMeasurement/UOMCoded",
                             "setUom", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/Quantity/UnitOfMeasurement/UOMCodedOther",
                             "setUom", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ListOfScheduleLine/ScheduleLine/RequestedDeliveryDate",
                             "setRequestedDeliveryDateString", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Name1",
                             "setShipToName1", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Name2",
                             "setShipToName2", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Street",
                             "setShipToStreet", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/PostalCode",
                             "setShipToPostalCode", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/City",
                             "setShipToCity", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Region/RegionCodedOther",
                             "setShipToRegion", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Country/CountryCoded",
                             "setShipToCountry", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Identifier/Agency/AgencyCoded",
                             "setShipToAgency", 0);
      digester.addCallMethod("Order/OrderDetail/ListOfItemDetail/ItemDetail/DeliveryDetail/ShipToLocation/Location/NameAddress/Identifier/Ident",
                             "setShipToIdent", 0);
      //order summary section
      digester.addCallMethod(
          "Order/OrderSummary/TotalAmount/MonetaryValue/MonetaryAmount",
          "setMonetaryAmount", 0, bigDecimalClass);
      digester.parse(file);
      orderDataBean = (XcblOrderBean) digester.getRoot();
    }
    catch (Exception e) {
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("currency.error.load");
      be.setRootCause(e);
      throw be;
    }
    return orderDataBean;
  }

}