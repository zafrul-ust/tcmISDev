package com.tcmis.client.kilfrost.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:44:36 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.kilfrost.beans.OrderBean;
import com.tcmis.client.kilfrost.beans.OrderLineBean;


public class OrderParser extends BaseProcess {
  public OrderParser(String client) {
    super(client);
  }

  public OrderBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    OrderBean orderBean = null;
    try {
    	log.debug("This is new code, should not have bigdecimal");;
    	Digester digester = new Digester();
        digester.addObjectCreate("kilfrostorder", OrderBean.class);

        digester.addCallMethod("kilfrostorder/orderheader/ordernumber", "setOrderNumber", 0);
        digester.addCallMethod("kilfrostorder/orderheader/orderdate", "setOrderDateString", 0);
        digester.addCallMethod("kilfrostorder/orderheader/purchaseorder", "setPurchaseOrder", 0);
        digester.addCallMethod("kilfrostorder/orderheader/orderstatus", "setOrderStatus", 0);
        digester.addCallMethod("kilfrostorder/orderheader/specialinstructions", "setShippingNote", 0);
        
      digester.addObjectCreate("kilfrostorder/orderdetails/orderrow", OrderLineBean.class);
           digester.addSetNext("kilfrostorder/orderdetails/orderrow", "addOrderLineBean");
        digester.addCallMethod("kilfrostorder/orderdetails/orderrow/partnumber", "setPartNumber", 0);
        digester.addCallMethod("kilfrostorder/orderdetails/orderrow/quantity", "setQuantity", 0);//, bigDecimalClass);

        digester.addCallMethod("kilfrostorder/customerdetails/customerpickup", "setCustomerPickup", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/customername", "setCustomerName", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/contactname", "setContactName", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/address1", "setAddress1", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/address2", "setAddress2", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/address3", "setAddress3", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/city", "setCity", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/stateprovence", "setStateProvence", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/zipcode", "setZip", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/country", "setCountry", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/telephone", "setPhone", 0);
        digester.addCallMethod("kilfrostorder/customerdetails/emailaddress", "setEmail", 0);
        digester.addCallMethod("kilfrostorder/deliverynote/content", "setDeliveryNote", 0);

        digester.parse(file);
        orderBean = (OrderBean) digester.getRoot();
        log.debug(orderBean);
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
    return orderBean;
  }
  public static void main(String[] args) {
	  String outPath     = "c:\\GeneratedJava\\";
	  File f = new File(outPath+"test.xml");
	  OrderParser p = new OrderParser("KILFROST");	  
	  try {
		  p.parse(f);
	  } catch(Exception ex){
		  
	  }
  }
}
