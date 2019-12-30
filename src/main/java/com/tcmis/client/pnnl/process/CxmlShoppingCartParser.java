package com.tcmis.client.pnnl.process;

import java.io.File;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.*;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.client.cxml.beans.*;
import com.tcmis.client.pnnl.beans.PnnlShoppingCartBean;
//import com.tcmis.client.seagate.beans.*;
import com.tcmis.common.util.StringHandler;

public class CxmlShoppingCartParser extends BaseProcess{

  public CxmlShoppingCartParser(String client) {
    super(client);
  }

  public String getCxml(CxmlDocumentDataBean cxmlDocumentDataBean, 
                        PunchOutSetupRequestBean punchoutSetupRequestBean,
                        Collection shoppingCartBeanCollection) throws BaseException {
    String response = null;
    BigDecimal totalAmount = new BigDecimal("0");
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    StringWriter sw = new StringWriter();
    sw.write("<?xml version=\"1.0\">");
    sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.2.003/cXML.dtd\">");
    sw.write("<cXML payloadID=\"" + cxmlDocumentDataBean.getResponsePayloadId() + "\" timestamp=\"" + cxmlDocumentDataBean.getTimestamp() + "\">");
    sw.write("<Header>");
//the incoming "from" is the outgoing "to" and vice versa.
    sw.write("<From>");
    sw.write("<Credential domain=\"" + cxmlDocumentDataBean.getToDomain() + "\">");
    sw.write("<Identity>" + cxmlDocumentDataBean.getToIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("</From>");
    sw.write("<To>");
    sw.write("<Credential domain=\"" + cxmlDocumentDataBean.getFromDomain() + "\">");
    sw.write("<Identity>" + cxmlDocumentDataBean.getFromIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("</To>");
    sw.write("<Sender>");
    sw.write("<Credential domain=\"" + cxmlDocumentDataBean.getToDomain() + "\">");
    sw.write("<Identity>" + cxmlDocumentDataBean.getToIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("<UserAgent>tcmis</UserAgent>");
    sw.write("</Sender>");
    sw.write("</Header>");
    //in Chucks code this could be set to test but I don't see the need...
    sw.write("<Message>");
    sw.write("<PunchOutOrderMessage>");
    sw.write("<BuyerCookie>" + punchoutSetupRequestBean.getBuyerCookie() + "</BuyerCookie>");

    //to be able to put the total amount I'll have to jump to after the loop

    //for each item
    StringWriter itemSw = new StringWriter();
    Iterator iterator = shoppingCartBeanCollection.iterator();
    while(iterator.hasNext()) {
      PnnlShoppingCartBean shoppingCartBean = (PnnlShoppingCartBean)iterator.next();
      totalAmount = totalAmount.add((shoppingCartBean.getQuantity()).multiply(shoppingCartBean.getQuotedPrice()));
      itemSw.write("<ItemIn quantity=\"" + shoppingCartBean.getQuantity() + "\">");
      itemSw.write("<ItemID>");
      itemSw.write("<SupplierPartID>" + shoppingCartBean.getFacPartNo() + "</SupplierPartID>");
      // not using PR-Line for PNNL
      //itemSw.write("<SupplierPartAuxiliaryID>" + shoppingCartViewBean.getPrNumber() + "-" + shoppingCartViewBean.getLineItem() + "</SupplierPartAuxiliaryID>");
      itemSw.write("</ItemID>");
      itemSw.write("<ItemDetail>");
      itemSw.write("<UnitPrice>");
      itemSw.write("<Money currency=\"" + shoppingCartBean.getCurrencyId() + "\">" + shoppingCartBean.getQuotedPrice() + "</Money>");
      itemSw.write("</UnitPrice>");

      itemSw.write("<Description xml:lang=\"en-US\">" + shoppingCartBean.getFacPartNo() + " - " + 
                   StringHandler.xmlEncode(shoppingCartBean.getMaterialDesc()) + " [" + StringHandler.xmlEncode(shoppingCartBean.getPackaging()) + 
                   "] </Description>");
//                   " (MR" + shoppingCartViewBean.getPrNumber() + "-" + shoppingCartViewBean.getLineItem() + 
//                   ")]</Description>");
      itemSw.write("<UnitOfMeasure>EA</UnitOfMeasure>");
      // * TODO: make sure this is the right field for the classification
      // * TODO: make sure UNSPSC is ok to hard-code, or we could pull it from the data
      itemSw.write("<Classification domain=\"UNSPSC\">" + shoppingCartBean.getAccountSysDesc() + "</Classification>");
      itemSw.write("</ItemDetail>");
      itemSw.write("</ItemIn>");
    }
    //end for each item
    //now I have the total amount and can write the rest of the cxml
    
    sw.write("<PunchOutOrderMessageHeader operationAllowed=\"edit\">");
    sw.write("<Total>");
    //currency is hardcoded in Chucks code
    sw.write("<Money currency=\"USD\">" + decimalFormat.format(totalAmount) + "</Money>");
    sw.write("</Total>");
    sw.write("</PunchOutOrderMessageHeader>");
    sw.write(itemSw.toString());
    sw.write("</PunchOutOrderMessage>");
    sw.write("</Message>");
    sw.write("</cXML>");
    response = sw.toString();
    return response;
  }
}