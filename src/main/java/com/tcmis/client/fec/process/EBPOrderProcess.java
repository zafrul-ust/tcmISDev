/*
 * EBPOrderProcess.java
 *
 * Created on July 21, 2005, 5:16 PM
 */

package com.tcmis.client.fec.process;

import com.tcmis.client.fec.beans.EbpXmlOrderBean; 
import com.tcmis.client.fec.ebp.ContactNumber;
import com.tcmis.client.fec.ebp.Feed;
import com.tcmis.client.fec.ebp.FeedDigester;
import com.tcmis.client.fec.ebp.Order;
import com.tcmis.client.fec.ebp.OrderHeader;
import com.tcmis.client.fec.ebp.OrderSummary;
import com.tcmis.client.fec.ebp.Line;
import com.tcmis.client.fec.ebp.OrderDigester;
import com.tcmis.client.fec.factory.EbpXmlOrderBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Vector;

/**
 * Handle the EBP XML order files coming from First Enery via Pantellos
 *
 * @author  mnajera
 */
public class EBPOrderProcess extends BaseProcess {
   
   private Feed feed;
   private Order ebpOrder;
   
   /** Creates a new instance of EBPOrderProcess */
   public EBPOrderProcess(String client) {
      super(client);
      feed=null;
      ebpOrder=null;
   }
   
   /*
    * read, parse, and extract the XML order from within the XML wrapper. (Pantellos test only)
    */ 
   public String preProcess(String feedFile) {
      String payload="";
      feed = readFeed(feedFile);
      payload = feed.getPayload();
      return payload;
   }
      
   /*
    * read, parse, and save the order data out of the incoming XML order file.
    */
   public void process(String xmlOrderFile) {      
      Order order = null;      
      order = readXmlOrder(xmlOrderFile);      
      loadStageLines(order,this.getClient());      
      ebpOrder = order;
   }
   
   /*
    * call the db stored procedure that process the incokming data against the lines waiting
    * in request_line_item
    */
   public void databaseUpdate() {
      try {
         dbEbpPushToMr();
      } catch (BaseException be) {
         log.error("BaseException trying to call stored procudre P_EBP_PUSH_TO_MR");
      }
   }
         
   /*
    * send a receipt email to the person mentioned on the order
    */
   public boolean sendReceivedNotificationEmail() {
      String contactEmail = getContactEmail();
      String orderNumber = getOrderNumber();
      
      OrderHeader header = ebpOrder.getHeader();
      Vector lines = ebpOrder.getLines();
      OrderSummary summary = ebpOrder.getSummary();
      
      String orderHeaderText = "Current Status:\n\tAuthorized/Unshipped\n\t";
      orderHeaderText += "Payment Method:\t"+header.getCardType()+"\n\n\t";
      orderHeaderText += "Order Number: " + header.getOrderNumber() +"\n\t";
      orderHeaderText += "Order Time: " + header.getIssueDate() +"\n\n";
      orderHeaderText += "Ship To:\n";
      orderHeaderText += header.getShiptoParty() + "\n";
      orderHeaderText += header.getShiptoId() + "\n";
      orderHeaderText += header.getShiptoAddress() + "\n";
      orderHeaderText += header.getShiptoCity() + ", " + header.getShiptoState() + " " + header.getShiptoZip() + "\n\n";
         
      String orderDetailText = "Part Num\tQty\tUnit Price\tProduct Name\n\n";
      if (lines!=null) {
         Iterator iter = lines.iterator();
         Line line = null;
         while (iter.hasNext()) {
            line = (Line) iter.next();
            orderDetailText += line.getPartNum() + "\t" + line.getQuantity() + "\t" + line.getUnitPrice() + "\t" +line.getItemDesc() + "\n";
         }
      }      
      String orderSummaryText = "\nOrder Total: " + summary.getTotalAmount() + "\n\n\nPlease retain this for your records.";
      
      MailProcess.sendEmail(contactEmail,"fec-notification@haastcm.com","messagebot@haastcm.com","EBP Order Num " + orderNumber + " received by Haas", orderHeaderText + orderDetailText + orderSummaryText );
      return true;
   }
   
   /*
    * return the order
    */
   public Order getOrder() {
      return ebpOrder;
   }
   
   /*
    * return the order number
    */
   public String getOrderNumber() {
      String ordNum="";
      if (ebpOrder!=null) {
         OrderHeader header = ebpOrder.getHeader();
         if (header!=null) {
            return header.getOrderNumber();
         }
      }
      return ordNum;
   }
   
   /*
    * return the contact email
    */
   private String getContactEmail() {
      String contactEmail = "";
      Vector contactList = null;
      try {
         if (ebpOrder!=null) {
            contactList = ebpOrder.getHeader().getContactList();
            ContactNumber contactNumber = null;
            Iterator iter = contactList.iterator();
            while (iter.hasNext()) {
               contactNumber = (ContactNumber) iter.next();
               if (contactNumber.getType().equalsIgnoreCase("EmailAddress")) {
                  contactEmail = contactNumber.getNumber();
               }
            }
         }            
      } catch (Exception e) {
         log.error("Exception gtting email addr: " + e);
      }
      
      return contactEmail;
   }
   
   /*
    * call the digeter for the wrapped XML order from Pantellos
    */
   private Feed readFeed(String feedFile) {
      Feed feed = null;
      
      FeedDigester digester = new FeedDigester(feedFile);
      feed = digester.getFeed();
      
      return feed;
   }
   
   /*
    * call the digester for the incoming ebp XML order
    */
   private Order readXmlOrder(String xmlOrderFile) {
      Order order = null;

      OrderDigester digester = new OrderDigester(xmlOrderFile);
      order = digester.getOrder();
      
      return order;
   }
   
   /*
    * take the contents of the internal object Order and insert into Fec_Ebp_Xml_Order
    */
   private void loadStageLines(Order order, String client) {
      DbManager dbManager = null;
      
      try {
         dbManager = new DbManager(client);
         EbpXmlOrderBeanFactory factory = new EbpXmlOrderBeanFactory(dbManager);

         OrderHeader header = order.getHeader();
         Vector lines = order.getLines();
         OrderSummary summary = order.getSummary();

         BigDecimal mrNum = null;
         BigDecimal itemId = null;
         Vector contactList = null;
            ContactNumber contactNumber = null;
         Iterator iter = lines.iterator();
         Line line = null;
         EbpXmlOrderBean ebpBean = null;
         while (iter.hasNext()) {
            line = (Line) iter.next();
            line.parseReferences(); // load up MR line, MR num, FE_mat_id, pkging

            ebpBean = new EbpXmlOrderBean();

            // set header info
            ebpBean.setBuyerordernumber( header.getOrderNumber() );
            ebpBean.setIssuedate( header.getIssueDate() );
            ebpBean.setCurrency( header.getCurrency() );         
            ebpBean.setBuyerName( header.getBuyerParty() );
            ebpBean.setBuyerAddr1( header.getBuyerAddress() );
            ebpBean.setBuyerCity( header.getBuyerCity() );
            ebpBean.setBuyerState( header.getBuyerState() );
            ebpBean.setBuyerZip( header.getBuyerZip() );
            ebpBean.setBuyerCountry( header.getBuyerCountry() );
            ebpBean.setBuyerContact( header.getBuyerContact() );
            ebpBean.setSellerPartyid( header.getSellerId() );
            ebpBean.setSellerName( header.getSellerParty() );
            ebpBean.setSellerAddr1( header.getSellerAddress() );
            ebpBean.setSellerCity( header.getSellerCity() );
            ebpBean.setSellerState( header.getSellerState() );
            ebpBean.setSellerZip( header.getSellerZip() );
            ebpBean.setSellerCountry( header.getSellerCountry() );
            ebpBean.setShiptoPartyid( header.getShiptoId() );
            ebpBean.setShiptoName1( header.getShiptoParty() );
            ebpBean.setShiptoName2( header.getShiptoParty2() );
            ebpBean.setShiptoName3( header.getShiptoParty3() );
            ebpBean.setShiptoAddr1( header.getShiptoAddress() );
            ebpBean.setShiptoCity( header.getShiptoCity() );
            ebpBean.setShiptoState( header.getShiptoState() );
            ebpBean.setShiptoZip( header.getShiptoZip() );
            ebpBean.setShiptoCountry( header.getShiptoCountry() );         
            ebpBean.setBilltoPartyid( header.getBilltoId() );
            ebpBean.setBilltoName( header.getBilltoName() );
            ebpBean.setPaymentTerm( header.getPaymentTerm() );
            ebpBean.setPaymentMean( header.getPaymentMean() );
            ebpBean.setCardNumber( header.getCardNumber() );
            ebpBean.setCardType( header.getCardType() );
            ebpBean.setCardHolderName( header.getCardHolder() );
            ebpBean.setCardExpDate( header.getCardExpDate() );

            ebpBean.setContactName( header.getShiptoContactName() );
            contactList = header.getContactList();
            Iterator contactsIter = contactList.iterator();
            while (contactsIter.hasNext()) {
               contactNumber = (ContactNumber) contactsIter.next();
               if (contactNumber.getType().equalsIgnoreCase("TelephoneNumber")) {
                  ebpBean.setContactPhone( contactNumber.getNumber() );
               } else if (contactNumber.getType().equalsIgnoreCase("FaxNumber")) {
                  ebpBean.setContactFax( contactNumber.getNumber() );
               } else if (contactNumber.getType().equalsIgnoreCase("EmailAddress")) {
                  ebpBean.setContactEmail( contactNumber.getNumber() );
               } 
            }
            
            // set line info
            try {
               mrNum = new BigDecimal(line.getMrNum());
            } catch (NumberFormatException nfe) {
               mrNum = new BigDecimal(0);
               log.error("Number format error converting MrNum");
            }
             
            try {
               itemId = new BigDecimal(line.getPartNum());
            } catch (NumberFormatException nfe2) {
               itemId = new BigDecimal(0);
               log.error("Number format error converting ItemId");
            }
            
            ebpBean.setMrNumber( mrNum );
            ebpBean.setLineItem( line.getMrLine() );
            ebpBean.setLineNo( line.getLineNum() );
            ebpBean.setItemId( itemId );
            ebpBean.setMfgPartnum( line.getManufactPartNum() );
            ebpBean.setItemDesc( line.getItemDesc() );
            ebpBean.setQuantity( line.getQuantity() );
            ebpBean.setUom( line.getUom() );
            ebpBean.setRecipientId( line.getRecipientId() );
            ebpBean.setUnitPrice( line.getUnitPrice() );
            ebpBean.setUnitPriceCurrency( line.getCurrency() );
            ebpBean.setLineTotalAmount( line.getTotalValue() );
            ebpBean.setItemPkg( line.getPackaging() );
            ebpBean.setBuyerPartnum( line.getMaterialId() );

            // get summary info
            ebpBean.setTotalLines( summary.getLineCount() );
            ebpBean.setTotalAmount( summary.getTotalAmount() );
            ebpBean.setStatus("NEW");

            factory.insert( ebpBean );            
         }
      } catch (BaseException be) {
         log.error("BaseException inserting row for EBP: " + be);
      }
      
   }

  /*
   * Call p_ebp_push_to_mr procedures
   */ 
  private void dbEbpPushToMr() throws BaseException {
    log.info("executing p_ebp_push_to_mr stored procedure");
    
    DbManager dbManager = new DbManager(this.getClient());       
    try {     
      GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);      
      Vector params = new Vector();
    
      procFactory.doProcedure("P_EBP_PUSH_TO_MR",params);
    } catch (BaseException be) {
      log.error("Base Exception calling ebp_push_to_mr: (ProcedureFactory failed.) " + be);
      log.trace(be);
    }     
  }
    
}