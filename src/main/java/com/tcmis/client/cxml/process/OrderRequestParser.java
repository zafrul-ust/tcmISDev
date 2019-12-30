package com.tcmis.client.cxml.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.cxml.beans.*;


public class OrderRequestParser extends BaseProcess {
  public OrderRequestParser(String client) {
    super(client);
  }

  public OrderRequestBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    OrderRequestBean orderRequestBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("cXML", OrderRequestBean.class);
      digester.addSetProperties("cXML");
      digester.addSetProperties("cXML", "payloadID", "payloadId");
      digester.addSetProperties("cXML", "timestamp", "timestamp");
      //header section
      digester.addSetProperties("cXML/Header/From/Credential", "domain", "fromDomain");
      digester.addCallMethod("cXML/Header/From/Credential/Identity", "setFromIdentity", 0);
      digester.addSetProperties("cXML/Header/To/Credential", "domain", "toDomain");
      digester.addCallMethod("cXML/Header/To/Credential/Identity", "setToIdentity", 0);
      digester.addSetProperties("cXML/Header/Sender/Credential", "domain", "senderDomain");
      digester.addCallMethod("cXML/Header/Sender/Credential/Identity", "setSenderIdentity", 0);
      digester.addCallMethod("cXML/Header/Sender/Credential/SharedSecret", "setSharedSecret", 0);
      digester.addCallMethod("cXML/Header/Sender/UserAgent", "setUserAgent", 0);
      //request section
      digester.addSetProperties("cXML/Request", "deploymentMode", "deploymentMode");
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader", "orderID", "orderId");
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader", "orderDate", "orderDate");
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader", "orderType", "orderType");
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader", "type", "type");
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader/Total/Money", "currency", "currency");
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/Total/Money", "setTotalAmount", 0);
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address", "isoCountryCode", "billToCountryCode");
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address", "addressID", "billToAddressID");
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/Name", "setBillToName", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/PostalAddress/DeliverTo", "setBillToDeliverTo", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/PostalAddress/Street", "setBillToPostalStreet", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/PostalAddress/City", "setBillToPostalCity", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/PostalAddress/State", "setBillToPostalState", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/PostalAddress/PostalCode", "setBillToPostalZip", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/PostalAddress/Country", "setBillToPostalCountry", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/Email", "setBillToEmail", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/Phone/TelephoneNumber/CountryCode", "setBillToPhoneCountryNumber", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/Phone/TelephoneNumber/AreaOrCityCode", "setBillToPhoneAreaCode", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/Phone/TelephoneNumber/Number", "setBillToPhoneNumber", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/Phone/TelephoneNumber/Extension", "setBillToPhoneExtension", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/BillTo/Address/URL", "setBillToUrl", 0);
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader/Payment/PCard", "number", "paymentCardNumber");
      digester.addSetProperties("cXML/Request/OrderRequest/OrderRequestHeader/Payment/PCard", "expiration", "paymentCardExpiration");
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/Comments", "setComments", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/OrderRequestHeader/Extrinsic", "addAdditionalInfo", 2);
      digester.addCallParam("cXML/Request/OrderRequest/OrderRequestHeader/Extrinsic", 0, "name");
      digester.addCallParam("cXML/Request/OrderRequest/OrderRequestHeader/Extrinsic", 1);
      //itemout section
      digester.addObjectCreate("cXML/Request/OrderRequest/ItemOut", ItemBean.class);
      digester.addSetNext("cXML/Request/OrderRequest/ItemOut", "addItemBean");
      digester.addSetProperties("cXML/Request/OrderRequest/ItemOut", "quantity", "quantity");
      digester.addSetProperties("cXML/Request/OrderRequest/ItemOut", "agreementItemNumber", "agreementItemNumber");
      digester.addSetProperties("cXML/Request/OrderRequest/ItemOut", "requestedDeliveryDate", "requestedDeliveryDate");
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemID/SupplierPartID", "setSupplierPartId", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemID/SupplierPartAuxiliaryID", "setSupplierPartAuxiliaryId", 0);
      digester.addSetProperties("cXML/Request/OrderRequest/ItemOut/ItemDetail/UnitPrice/Money", "currency", "currency");
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/UnitPrice/Money", "setUnitPrice", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/Description", "setDescription", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/UnitOfMeasure", "setUnitOfMeasure", 0);
      digester.addSetProperties("cXML/Request/OrderRequest/ItemOut/ItemDetail/Classification", "domain", "classificationDomain");
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/Classification", "setClassification", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/ManufacturerPartID", "setManufacturerPartId", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/ManufacturerName", "setManufacturerName", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/URL", "setUrl", 0);
      digester.addCallMethod("cXML/Request/OrderRequest/ItemOut/ItemDetail/Extrinsic", "addAdditionalInfo", 2);
      digester.addCallParam("cXML/Request/OrderRequest/ItemOut/ItemDetail/Extrinsic", 0, "name");
      digester.addCallParam("cXML/Request/OrderRequest/ItemOut/ItemDetail/Extrinsic", 1);

      digester.parse(file);
      orderRequestBean = (OrderRequestBean) digester.getRoot();
    }
    catch (Exception e) {
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("");
      be.setRootCause(e);
      throw be;
    }
    return orderRequestBean;
  }
  
}