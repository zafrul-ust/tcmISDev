package com.tcmis.client.cxml.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.cxml.beans.*;


public class PunchOutOrderMessageParser extends BaseProcess {

  public PunchOutOrderMessageParser(String client) {
    super(client);
  }
  
  public String getResponse(PunchOutOrderMessageBean bean) throws BaseException {
    String response = null;
    StringWriter sw = new StringWriter();
    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.2.003/cXML.dtd\">");
    sw.write("<cXML payloadID=\"" + bean.getPayloadId() + "\" timestamp=\"" + bean.getTimestamp() + "\">");
    sw.write("<Header>");
    sw.write("<From>");
    sw.write("<Credential domain=\"" + bean.getFromDomain() + "\">");
    sw.write("<Identity>" + bean.getFromIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("</From>");
    sw.write("<To>");
    sw.write("<Credential domain=\"" + bean.getToDomain() + "\">");
    sw.write("<Identity>" + bean.getToIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("</To>");
    sw.write("<Sender>");
    sw.write("<Credential domain=\"" + bean.getToDomain() + "\">");
    sw.write("<Identity>" + bean.getToIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("<UserAgent>" + bean.getUserAgent() + "</UserAgent>");
    sw.write("</Sender>");
    sw.write("</Header>");
    sw.write("<Message deploymentMode=\"" + bean.getDeploymentMode() + "\">");
    sw.write("<PunchOutOrderMessage>");
    sw.write("<BuyerCookie>" + bean.getBuyerCookie() + "</BuyerCookie>");
    sw.write("<PunchOutOrderMessageHeader quoteStatus=\"" + bean.getQuteStatus() + "\" operationAllowed=\"" + bean.getOperationAllowed() + "\">");
    sw.write("<Total><Money currency=\"" + bean.getCurrency() + "\">" + bean.getTotalAmount() + "</Money></Total>");
    sw.write("</PunchOutOrderMessageHeader>");
    Iterator iterator = bean.getItemBeanCollection().iterator();
    while(iterator.hasNext()) {
      ItemBean itemBean = (ItemBean)iterator.next();
      sw.write("<ItemIn quantity=\"" + itemBean.getQuantity() + "\">");
      sw.write("<ItemID>");
      sw.write("<SupplierPartID>" + itemBean.getSupplierPartId() + "</SupplierPartID>");
      sw.write("<SupplierPartAuxiliaryID>" + itemBean.getSupplierPartAuxiliaryId() + "</SupplierPartAuxiliaryID>");
      sw.write("</ItemID>");
      sw.write("<ItemDetail>");
      sw.write("<UnitPrice><Money currency=\"" + itemBean.getCurrency() + "\">" + itemBean.getUnitPrice() + "</Money></UnitPrice>");
      sw.write("<Description xml:lang=\"en-US\">" + itemBean.getDescription() + "</Description>");
      sw.write("<UnitOfMeasure>" + itemBean.getUnitOfMeasure() + "</UnitOfMeasure>");
      sw.write("<Classification domain=\"" + itemBean.getClassificationDomain() + "\">" + itemBean.getClassification() + "</Classification>");
      sw.write("</ItemDetail>");
      sw.write("</ItemIn>");
    }
    sw.write("</PunchOutOrderMessage>");
    sw.write("</Message>");
    sw.write("</cXML>");
    response = sw.toString();
    return response;
  }
}