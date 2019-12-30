package com.tcmis.client.cxml.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.cxml.beans.*;



public class OrderRequestResponseParser extends BaseProcess {

  public OrderRequestResponseParser(String client) {
    super(client);
  }
  
  public String getResponse(OrderRequestResponseBean bean) throws BaseException {
    String response = null;
    StringWriter sw = new StringWriter();
    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.2.003/cXML.dtd\">");
    sw.write("<cXML version=\"\" payloadID=\"" + bean.getPayloadId() + "\" timestamp=\"" + bean.getTimestamp() + "\">");
    sw.write("<Response><Status code=\"" + bean.getStatusCode() + "\" text=\"" + bean.getStatusText() + "\"/></Response>");
    sw.write("</cXML>");
    response = sw.toString();
    return response;
  }
}