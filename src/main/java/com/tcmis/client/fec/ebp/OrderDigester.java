/*
 * OrderDigester.java
 *
 * Created on June 13, 2005, 3:19 PM
 */

package com.tcmis.client.fec.ebp;

import com.tcmis.common.util.ResourceLibrary;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
/**
 *
 * @author  mike.najera
 */
/**
 *
 * @author  mike.najera
 */
public class OrderDigester {
   
   /** Creates a new instance of OrderDigester */

   
   private Digester d;   
   private String orderXmlFile = "";   
   private Order order = null;
   private static ResourceLibrary fecLibrary;
   
   /** Creates a new instance of ProcessDigester */
   public OrderDigester(String xmlFile) {
      orderXmlFile=xmlFile;
      init();
   }
   
   public Order getOrder() {
      return order;
   }
   
   private void init() {
        fecLibrary = new ResourceLibrary("client");
        // Create a Digester instance
        d = new Digester();
               
        // Prime the digester stack with an object for rules to
        // operate on. Note that it is quite common for "this"
        // to be the object pushed.
        order = new Order();
        d.push(order);
                
        // Add rules to the digester that will be triggered while
        // parsing occurs.
        addRules(d);
        
        // Process the input file.
        try {
            File srcfile = new File(orderXmlFile);
            d.parse(srcfile);
        }
        catch(IOException ioe) {
            System.out.println("Error reading input file:" + ioe.getMessage());
            // System.exit(-1);
        }
        catch(SAXException se) {
            System.out.println("Error parsing input file:" + se.getMessage());
            // System.exit(-1);
        }        
        
    }
    
    private static void addRules(Digester d) {

        //--------------------------------------------------        
        // when we encounter a "supplier" tag, do the following:
       
        // create a new instance of class Supplier, and push that
        // object onto the digester stack of objects
       
       
        d.addObjectCreate(fecLibrary.getString("fec.ebp.xml.object.header"), OrderHeader.class);
        
        // map *any* attributes on the tag to appropriate
        // setter-methods on the top object on the stack (the Person
        // instance created by the preceeding rule). 
        //
        // For example:
        // if attribute "id" exists on the xml tag, and method setId 
        // with one parameter exists on the object that is on top of
        // the digester object stack, then a call will be made to that
        // method. The value will be type-converted from string to
        // whatever type the target method declares (where possible), 
        // using the commons ConvertUtils functionality.
        //
        // Attributes on the xml tag for which no setter methods exist
        // on the top object on the stack are just ignored.
        
        
        // d.addSetProperties("dbuy/process");

        // call the addPerson method on the second-to-top object on
        // the stack (the AddressBook object), passing the top object
        // on the stack (the recently created Person object).
        d.addSetNext(fecLibrary.getString("fec.ebp.xml.object.header"), "addHeader");        
        
        //--------------------------------------------------        
        // when we encounter a "name" tag, call setName on the top
        // object on the stack, passing the text contained within the
        // body of that name element [specifying a zero parameter count
        // implies one actual parameter, being the body text]. 
        // The top object on the stack will be a person object, because 
        // the pattern address-book/person always triggers the 
        // ObjectCreateRule we added previously.
                
        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.ordernumber"), "setOrderNumber", 0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.issuedate"), "setIssueDate", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.currency"), "setCurrency", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.name"), "setBuyerParty", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.street"), "setBuyerAddress", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.zip"), "setBuyerZip", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.city"), "setBuyerCity", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.state"), "setBuyerState", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.country"), "setBuyerCountry", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.buyer.contact"), "setBuyerContact", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.seller.id"), "setSellerId", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.seller.name"), "setSellerParty", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.seller.street"), "setSellerAddress", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.seller.zip"), "setSellerZip", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.seller.city"), "setSellerCity", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.seller.state"), "setSellerState", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.seller.country"), "setSellerCountry", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.id"), "setShiptoId", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.name1"), "setShiptoParty", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.name2"), "setShiptoParty2", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.name3"), "setShiptoParty3", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.street"), "setShiptoAddress", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.zip"), "setShiptoZip", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.city"), "setShiptoCity", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.state"), "setShiptoState", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.country"), "setShiptoCountry", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.contact.name"), "setShiptoContactName", 0);          
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.billto.id"), "setBilltoId", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.billto.name"), "setBilltoName", 0);        

        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.payment.term"), "setPaymentTerm", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.payment.mean"), "setPaymentMean", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.payment.cardnum"), "setCardNumber", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.payment.cardexp"), "setCardExpDate", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.payment.cardtype"), "setCardType", 0);        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.payment.cardholder"), "setCardHolder", 0);        
        
        // Contact Numbers
        
        d.addObjectCreate(fecLibrary.getString("fec.ebp.xml.object.contactnumber"),ContactNumber.class);
        
        d.addSetNext(fecLibrary.getString("fec.ebp.xml.object.contactnumber"), "addContactNumber");
        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.contact.number"),"setNumber",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.shipto.contact.type"),"setType",0);
        
        // Lines
        
        d.addObjectCreate(fecLibrary.getString("fec.ebp.xml.object.item"),Line.class);
        
        d.addSetNext(fecLibrary.getString("fec.ebp.xml.object.item"), "addLine");
        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.line"),"setLineNum",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.sellerpart"),"setPartNum",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.mfgpart"),"setManufactPartNum",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.desc"),"setItemDesc",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.quantity"),"setQuantity",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.uom"),"setUom",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.recipientid"),"setRecipientId",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.unitprice"),"setUnitPrice",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.currency"),"setCurrency",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.totalamount"),"setTotalValue",0);

        // Refernce Coded [ref. info: mr, linr, fec part #, pkg
        d.addObjectCreate(fecLibrary.getString("fec.ebp.xml.object.reference"),ReferenceCoded.class);
        
        d.addSetNext(fecLibrary.getString("fec.ebp.xml.object.reference"),"addReferenceCoded");
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.reference.type"),"setType",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.item.reference.value"),"setValue",0);        
        
        // Summary
        // 
        d.addObjectCreate(fecLibrary.getString("fec.ebp.xml.object.summary"),OrderSummary.class);
        
        d.addSetNext(fecLibrary.getString("fec.ebp.xml.object.summary"),"addSummary");
        
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.linecount"),"setLineCount",0);
        d.addCallMethod(fecLibrary.getString("fec.ebp.xml.totalamount"),"setTotalAmount",0);
    }
   
}
