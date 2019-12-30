package com.tcmis.client.seagate.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;

import com.tcmis.client.seagate.beans.*;
import com.tcmis.client.seagate.factory.*;


public class CxmlPunchoutSetupParser extends BaseProcess {
  public CxmlPunchoutSetupParser(String client) {
    super(client);
  }
/*
  public CxmlDocumentDataBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    CxmlDocumentDataBean cxmlDocumentDataBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("cXML", CxmlDocumentDataBean.class);
      digester.addSetProperties("cXML");
      digester.addSetProperties("cXML", "payloadID", "payloadId");
      //order header section
      //digester.addCallMethod("cXML/Header/From/Credential/domain", "setFromDomain", 0);
      digester.addSetProperties("cXML/Header/From/Credential", "domain", "fromDomain");

      digester.addCallMethod("cXML/Header/From/Credential/Identity", "setFromIdentity", 0);
      //digester.addCallMethod("cXML/Header/To/Credential/domain", "setToDomain", 0);
      digester.addSetProperties("cXML/Header/To/Credential", "domain", "toDomain");
      digester.addCallMethod("cXML/Header/To/Credential/Identity", "setToIdentity",
                             0);
      //digester.addCallMethod("cXML/Header/Sender/Credential/domain", "setSenderDomain", 0);
      digester.addSetProperties("cXML/Header/Sender/Credential", "domain", "senderDomain");
      digester.addCallMethod("cXML/Header/Sender/Credential/Identity",
                             "setSenderIdentity", 0);
      digester.addCallMethod(
          "cXML/Header/Sender/Credential/SharedSecret",
          "setSharedSecret", 0);
      digester.addCallMethod(
          "cXML/Header/Sender/UserAgent",
          "setUserAgent", 0);

      digester.parse(file);
      cxmlDocumentDataBean = (CxmlDocumentDataBean) digester.getRoot();
    }
    catch (Exception e) {
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("");
      be.setRootCause(e);
      throw be;
    }
    return cxmlDocumentDataBean;
  }
  
  public String getResponse(PunchOutSetupRequestBean bean) throws BaseException {
    String response = null;
    StringWriter sw = new StringWriter();
    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.2.003/cXML.dtd\">");
    sw.write("<cXML version=\"\" payloadID=\"" + bean.getPayloadId() + "\" timestamp=\"" + bean.getTimestamp() + "\">");
    sw.write("<Response>");
    sw.write("<Status code=\"200\" text=\"Success\"/>");
    sw.write("<PunchOutSetupResponse>");
    sw.write("<StartPage>");
    sw.write("<URL>");
    sw.write("http://www.foo.com?" + bean.getPayloadId());
    sw.write("</URL>");
    sw.write("</StartPage>");
    sw.write("</PunchOutSetupResponse>");
    sw.write("</Response>");
    sw.write("</cXML>");
    response = sw.toString();
    return response;
  }
*/
}