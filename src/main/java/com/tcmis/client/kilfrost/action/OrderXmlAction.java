package com.tcmis.client.kilfrost.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 10:51:05 AM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlReaderAction;
import com.tcmis.common.framework.XmlRequestResponseAction;
import com.tcmis.client.kilfrost.process.OrderProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/******************************************************************************
 *
 * @version 1.0
 ******************************************************************************/
public class OrderXmlAction
    extends XmlRequestResponseAction {

  public String processCall(HttpServletRequest request,
                          HttpServletResponse response,
                          String xmlString) throws BaseException {
    String message = "OK";
    try {
      String address = "deverror@tcmis.com";
      MailProcess.sendEmail(address, "", "kilfrost@haastcm.com",
                            "Received order ", "ORDER:\n" + xmlString);
    }
    catch (Exception ex) {
      log.error("error sending mail:" + ex.getMessage());
    }
    if(xmlString != null && xmlString.length() > 0) {
        try {
            OrderProcess process = new OrderProcess(this.getDbUser(request));
            process.processOrder(xmlString);
        }
        catch(Exception e) {
            e.printStackTrace();
            log.fatal("Error receiving order from Kilfrost:" + e.getMessage(), e);
            message = "Error:" + e.getMessage();
        }
    }
    return message;
  }
}