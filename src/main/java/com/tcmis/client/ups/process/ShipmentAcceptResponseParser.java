package com.tcmis.client.ups.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jan 21, 2008
 * Time: 3:33:54 PM
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
import com.tcmis.client.raytheon.beans.XcblOrderBuyerContactBean;


public class ShipmentAcceptResponseParser extends BaseProcess {
  public ShipmentAcceptResponseParser(String client) {
    super(client);
  }

  public ShipmentAcceptResponseBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    ShipmentAcceptResponseBean shipmentAcceptResponseBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("ShipmentAcceptResponse", ShipmentAcceptResponseBean.class);

      digester.addCallMethod("ShipmentAcceptResponse/Response/TransactionReference/CustomerReference/InternalKey", "setInternalKey", 0);
      digester.addCallMethod("ShipmentAcceptResponse/Response/TransactionReference/XpciVersion", "setXpciVersion", 0);
      digester.addCallMethod("ShipmentAcceptResponse/Response/ResponseStatus", "setResponseStatus", 0);
      digester.addCallMethod("ShipmentAcceptResponse/Response/ResponseStatusDescription", "setResponseStatusDescription", 0);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TransportationCharges/MonetaryValue", "setTransportationCharges", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/ServiceOptionsCharges/MonetaryValue", "setServiceOptionsCharges", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/ShipmentCharges/TotalCharges/MonetaryValue", "setTotalCharges", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/BillingWeight/Weight", "setBillingWeight", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/ShipmentIdentificationNumber", "setShipmentIdentificationNumber", 0);

      digester.addObjectCreate("ShipmentAcceptResponse/ShipmentResults/PackageResults", PackageResultsBean.class);
      digester.addSetNext("ShipmentAcceptResponse/ShipmentResults/PackageResults", "addPackageResultsBean");
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/PackageResults/TrackingNumber", "setTrackingNumber", 0);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/PackageResults/ServiceOptionsCharges/MonetaryValue", "setServiceOptionsCharges", 0, bigDecimalClass);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/PackageResults/LabelImage/LabelImageFormat/Code", "setLabelImageFormat", 0);
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/PackageResults/LabelImage/GraphicImage", "setGraphicImage", 0);
        
      digester.addCallMethod("ShipmentAcceptResponse/ShipmentResults/ControlLogReceipt/GraphicImage", "setHighValueReportGraphicImage", 0);
      digester.parse(file);
      shipmentAcceptResponseBean = (ShipmentAcceptResponseBean) digester.getRoot();
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
    return shipmentAcceptResponseBean;
  }

}