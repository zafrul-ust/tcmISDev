package com.tcmis.client.fedex.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Oct 6, 2008
 * Time: 11:30:17 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.fedex.beans.ServiceAvailabilityBean;
import com.tcmis.client.fedex.beans.ServiceOptionBean;


public class ServiceAvailabilityReplyParser extends BaseProcess {
  public ServiceAvailabilityReplyParser(String client) {
    super(client);
  }

  public ServiceAvailabilityBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    ServiceAvailabilityBean serviceAvailabilityBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("v2:ServiceAvailabilityReply", ServiceAvailabilityBean.class);

      digester.addCallMethod("v2:ServiceAvailabilityReply/v3:HighestSeverity", "setNotification", 0);
      digester.addCallMethod("v2:ServiceAvailabilityReply/v2:Message", "setMessage", 0);

      digester.addObjectCreate("v2:ServiceAvailabilityReply/v2:Options", ServiceOptionBean.class);
      digester.addSetNext("v2:ServiceAvailabilityReply/v2:Options", "addServiceOptionBean");
      digester.addCallMethod("v2:ServiceAvailabilityReply/v2:Options/v2:Service", "setService", 0);
      digester.addCallMethod("v2:ServiceAvailabilityReply/v2:Options/v2:DeliveryDate", "setDeliveryDateString", 0);
      digester.addCallMethod("v2:ServiceAvailabilityReply/v2:Options/v2:DeliveryDay", "setDeliveryDay", 0);
      digester.addCallMethod("v2:ServiceAvailabilityReply/v2:Options/v2:DestinationStationId", "setDestinationStationId", 0);

      digester.parse(file);
      serviceAvailabilityBean = (ServiceAvailabilityBean) digester.getRoot();
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
    return serviceAvailabilityBean;
  }

}

