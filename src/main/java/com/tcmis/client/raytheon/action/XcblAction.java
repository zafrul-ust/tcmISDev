package com.tcmis.client.raytheon.action;

import com.tcmis.client.raytheon.process.XcblProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlReaderAction;

/******************************************************************************
 *
 * @version 1.0
 ******************************************************************************/
public class XcblAction
    extends XmlReaderAction {

  public void processCall(String dbUser, String xmlString) throws BaseException {
    try {
      String address = "deverror@tcmis.com";
      MailProcess.sendEmail(address, "", "raytheon_xcbl@haastcm.com",
                            "Received xcbl order ", "ORDER:\n" + xmlString);
    }
    catch (Exception ex) {
      log.error("error sending mail:" + ex.getMessage());
    }
    XcblProcess process = new XcblProcess(dbUser);
    process.processDocument(xmlString);
  }
}
