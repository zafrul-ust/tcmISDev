package com.tcmis.client.seagate.process;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import com.tcmis.client.seagate.beans.CxmlDocumentDataBean;
import com.tcmis.client.seagate.beans.PunchoutSetupRequestBean;
import com.tcmis.client.seagate.beans.ShoppingCartViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.StringHandler;

public class CxmlShoppingCartParser extends BaseProcess{

  public CxmlShoppingCartParser(String client) {
    super(client);
  }

  public String getCxml(CxmlDocumentDataBean cxmlDocumentDataBean, 
                        PunchoutSetupRequestBean punchoutSetupRequestBean,
                        Collection shoppingCartViewBeanCollection) throws BaseException {
    String response = null;
    BigDecimal totalAmount = new BigDecimal("0");
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    StringWriter sw = new StringWriter();
    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
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
    sw.write("<Message deploymentMode=\"production\">");
    sw.write("<PunchOutOrderMessage>");
    sw.write("<BuyerCookie>" + punchoutSetupRequestBean.getBuyerCookie() + "</BuyerCookie>");

    //to be able to put the total amount I'll have to jump to after the loop

//for each item
    StringWriter itemSw = new StringWriter();
    Iterator iterator = shoppingCartViewBeanCollection.iterator();
    while(iterator.hasNext()) {
      ShoppingCartViewBean shoppingCartViewBean = (ShoppingCartViewBean)iterator.next();
      totalAmount = totalAmount.add((shoppingCartViewBean.getQuantity()).multiply(shoppingCartViewBean.getQuotedPrice()));
      itemSw.write("<ItemIn quantity=\"" + shoppingCartViewBean.getQuantity() + "\">");
      itemSw.write("<ItemID>");
      itemSw.write("<SupplierPartID>" + shoppingCartViewBean.getItemId() + "</SupplierPartID>");
      itemSw.write("<SupplierPartAuxiliaryID>" + shoppingCartViewBean.getPrNumber() + "-" + shoppingCartViewBean.getLineItem() + "</SupplierPartAuxiliaryID>");
      itemSw.write("</ItemID>");
      itemSw.write("<ItemDetail>");
      itemSw.write("<UnitPrice>");
      itemSw.write("<Money currency=\"USD\">" + shoppingCartViewBean.getQuotedPrice() + "</Money>");
      itemSw.write("</UnitPrice>");

      itemSw.write("<Description xml:lang=\"en-US\">" + shoppingCartViewBean.getFacPartNo() + " - " + 
                   StringHandler.xmlEncode(shoppingCartViewBean.getMaterialDesc()) + " [" + StringHandler.xmlEncode(shoppingCartViewBean.getPackaging()) + 
                   " (MR" + shoppingCartViewBean.getPrNumber() + "-" + shoppingCartViewBean.getLineItem() + 
                   ")]</Description>");
      itemSw.write("<UnitOfMeasure>EA</UnitOfMeasure>");
      itemSw.write("<Classification domain=\"UNSPSC\">" + shoppingCartViewBean.getAccountSysDesc() + "</Classification>");
      itemSw.write("</ItemDetail>");
      itemSw.write("</ItemIn>");
    }
//end for each item
//now I have the total amount and can write the rest of the cxml
    //this is hardcode in Chucks code
    sw.write("<PunchOutOrderMessageHeader operationAllowed=\"create\">");
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