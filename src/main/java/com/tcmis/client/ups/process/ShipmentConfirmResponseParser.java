package com.tcmis.client.ups.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jan 21, 2008
 * Time: 2:53:33 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.ups.beans.*;


public class ShipmentConfirmResponseParser extends BaseProcess {
  public ShipmentConfirmResponseParser(String client) {
    super(client);
  }

  public ShipmentConfirmResponseBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    ShipmentConfirmResponseBean shipmentConfirmResponseBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("ShipmentConfirmResponse", ShipmentConfirmResponseBean.class);

      digester.addCallMethod("ShipmentConfirmResponse/Response/TransactionReference/CustomerReference/InternalKey", "setInternalKey", 0);
      digester.addCallMethod("ShipmentConfirmResponse/Response/TransactionReference/XpciVersion", "setXpciVersion", 0);
      digester.addCallMethod("ShipmentConfirmResponse/Response/ResponseStatusCode", "setResponseStatus", 0);
      digester.addCallMethod("ShipmentConfirmResponse/Response/ResponseStatusDescription", "setResponseStatusDescription", 0);
      digester.addCallMethod("ShipmentConfirmResponse/Response/Error/ErrorCode", "setErrorCode", 0);
      digester.addCallMethod("ShipmentConfirmResponse/Response/Error/ErrorDescription", "setErrorDescription", 0);
      digester.addCallMethod("ShipmentConfirmResponse/ShipmentCharges/TransportationCharges/MonetaryValue", "setTransportationCharges", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentConfirmResponse/ShipmentCharges/ServiceOptionsCharges/MonetaryValue", "setServiceOptionsCharges", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentConfirmResponse/ShipmentCharges/TotalCharges/MonetaryValue", "setTotalCharges", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentConfirmResponse/BillingWeight/Weight", "setBillingWeight", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentConfirmResponse/ShipmentIdentificationNumber", "setShipmentIdentificationNumber", 0);
      digester.addCallMethod("ShipmentConfirmResponse/ShipmentDigest", "setShipmentDigest", 0);

      digester.parse(file);
      shipmentConfirmResponseBean = (ShipmentConfirmResponseBean) digester.getRoot();
    }
    catch (Exception e) {
        System.out.println("Error:" + e.getMessage());
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("");
      be.setRootCause(e);
      throw be;
    }
    return shipmentConfirmResponseBean;
  }

}
