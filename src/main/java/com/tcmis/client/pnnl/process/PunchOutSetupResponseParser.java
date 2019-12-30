package com.tcmis.client.pnnl.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.cxml.beans.*;



public class PunchOutSetupResponseParser extends BaseProcess {

  public PunchOutSetupResponseParser(String client) {
    super(client);
  }
  
  public String getResponse(PunchOutSetupResponseBean bean) throws BaseException {
    String response = null;
    StringWriter sw = new StringWriter();
    sw.write("<?xml version=\"1.0\">");
    sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.1.009/cXML.dtd\">");
    sw.write("<cXML version=\"1.1\" payloadID=\"" + bean.getPayloadId() + "\" timestamp=\"" + bean.getTimestamp() + "\">");
    sw.write("<Response>");
    sw.write("<Status code=\"" + bean.getStatusCode() + "\" text=\"" + bean.getStatusText() + "\"/>");
    sw.write("<PunchOutSetupResponse>");
    sw.write("<StartPage>");
    sw.write("<URL>");
    sw.write(bean.getStartPageUrl());
    sw.write("</URL>");
    sw.write("</StartPage>");
    sw.write("</PunchOutSetupResponse>");
    sw.write("</Response>");
    sw.write("</cXML>");
    response = sw.toString();
    return response;
  }
}