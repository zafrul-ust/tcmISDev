package com.tcmis.client.cxml.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.cxml.beans.*;


public class IProcLoginRequestXmlParser extends BaseProcess {
  public IProcLoginRequestXmlParser(String client) {
    super(client);
  }

  public PunchOutSetupRequestBean parse(File file,String payloadId) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    PunchOutSetupRequestBean punchOutSetupRequestBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("request", PunchOutSetupRequestBean.class);
//      digester.addSetProperties("xml");
//      digester.addSetProperties("xml", "payloadID", "payloadId");
//      digester.addSetProperties("xml", "timestamp", "timestamp");
      //header section
//      digester.addCallMethod("xml/request/header/login/username", "extrinsicName", 0);
      digester.addCallMethod("request/header/login/password", "setSharedSecret", 0);
      //request section
//      digester.addSetProperties("cXML/Request/PunchOutSetupRequest", "operation", "operation");
      digester.addCallMethod("request/body/loginInfo/userInfo/appUserName", "setExtrinsicName", 0);
      digester.addCallMethod("request/body/loginInfo/returnURL", "setBrowserFormPost", 0);
      //right now we always get one Extrinisic name but that might not always be the case in the future
      //digester.addSetProperties("cXML/Request/PunchOutSetupRequest/Extrinsic", "name", "extrinsicName");
      //digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Extrinsic", "setExtrinisicName", 0);
//      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/BrowserFormPost/URL", "setBrowserFormPost", 0);
//      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Contact/Name", "setContactName", 0);
//      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Contact/Email", "setContactEmail", 0);
//      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/SupplierSetup/URL", "setSupplierSetupUrl", 0);
//
//      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Extrinsic", "addAdditionalInfo", 2);
//      digester.addCallParam("cXML/Request/PunchOutSetupRequest/Extrinsic", 0, "name");
//      digester.addCallParam("cXML/Request/PunchOutSetupRequest/Extrinsic", 1);

      digester.parse(file);
      punchOutSetupRequestBean = (PunchOutSetupRequestBean) digester.getRoot();
      punchOutSetupRequestBean.setPayloadId(payloadId);
    }
    catch (Exception e) {
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("");
      be.setRootCause(e);
      throw be;
    }
    return punchOutSetupRequestBean;
  }
  
}