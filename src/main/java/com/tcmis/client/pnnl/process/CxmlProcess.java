package com.tcmis.client.pnnl.process;

import java.io.*;

import java.math.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.cxml.beans.*;
import com.tcmis.client.cxml.factory.*;
import com.tcmis.client.cxml.process.OrderRequestResponseParser;
import com.tcmis.client.cxml.process.PunchOutSetupRequestParser;
import com.tcmis.client.order.beans.*;
import com.tcmis.client.order.factory.PurchaseRequestBeanFactory;
import com.tcmis.client.order.factory.RequestLineItemBeanFactory;
import com.tcmis.client.pnnl.beans.PunchOutInspectRequestBean;
import com.tcmis.client.pnnl.beans.PnnlShoppingCartBean;
import com.tcmis.client.pnnl.process.CxmlShoppingCartParser;
import com.tcmis.client.pnnl.process.OrderRequestParser;
import com.tcmis.client.pnnl.process.PunchOutSetupResponseParser;
import com.tcmis.client.pnnl.process.PunchOutInspectRequestParser;
//import com.tcmis.client.seagate.beans.CxmlDocumentDataBean;
//import com.tcmis.client.seagate.beans.CxmlOrderRequestBean;
//import com.tcmis.client.seagate.beans.DoManifestBean;
//import com.tcmis.client.seagate.beans.PunchoutOrderMessageBean;
//import com.tcmis.client.seagate.beans.PunchoutSessionBean;
//import com.tcmis.client.seagate.beans.PunchoutSetupRequestBean;
//import com.tcmis.client.seagate.beans.ShoppingCartViewBean;
//import com.tcmis.client.seagate.beans.ShoppingCartItemTypeViewBean;
//import com.tcmis.client.seagate.factory.CxmlDocumentDataBeanFactory;
//import com.tcmis.client.seagate.factory.CxmlOrderRequestBeanFactory;
//import com.tcmis.client.seagate.factory.DoManifestBeanFactory;
//import com.tcmis.client.seagate.factory.PunchoutOrderMessageBeanFactory;
//import com.tcmis.client.seagate.factory.PunchoutSessionBeanFactory;
//import com.tcmis.client.seagate.factory.PunchoutSetupRequestBeanFactory;
//import com.tcmis.client.seagate.factory.ShoppingCartItemTypeViewBeanFactory;
//import com.tcmis.client.seagate.factory.ShoppingCartViewBeanFactory;
import com.tcmis.client.cxml.util.*;
import com.tcmis.common.admin.beans.VvAccountSysBean;
import com.tcmis.common.admin.factory.VvAccountSysBeanFactory;
import com.tcmis.common.admin.process.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.net.TcmisHttpURLConnection;
import com.tcmis.common.util.*;

/**
 * cXML handling for PNNL.
 */
public class CxmlProcess extends BaseProcess {
   
   private final static String TO_EMAIL = "deverror@haastcm.com";
   private final static String FROM_EMAIL = "pnnl_cxml@haastcm.com";
   private String payloadId;
   private ResourceLibrary library = null;
   
   /**
    * Initialize the process.
    */   
   public CxmlProcess(String client) {
      super(client);
      library = new ResourceLibrary("pnnl");
   }
   
  
   /**    
    * @return The payload ID of this XML document.
    */   
   public String getPayloadId() {
      return this.payloadId;
   }
   
   /**
    * Save the incoming document to the file system, determine what type of document it is, 
    * and then process it. Types (w/ actions):
    *   PunchOutSetupRequest [create] - initialize a new shoping cart
    *   PunchOutSetupRequest [inspect] - view details on an item    
    *   OrderRequest - process an incoming order
    */
   public String processDocument(String cxmlDocument, String sessionId) throws BaseException {
      if (log.isDebugEnabled()) {
         log.debug("processing document:" + cxmlDocument);
      }
      //System.out.println("processing document" + cxmlDocument);
      String cxmlResponse = null;
      boolean validOrder = true;
      OrderRequestParser orderRequestParser;
      //PunchOutOrderMessageParser punchOutOrderMessageParser;
      PunchOutSetupRequestParser punchOutSetupRequestParser;
      PunchOutInspectRequestParser punchOutInspectRequestParser;
      PunchOutSetupResponseParser punchOutSetupResponseParser;
      OrderRequestBean orderRequestBean;
      //PunchOutOrderMessageBean punchOutOrderMessageBean;
      PunchOutSetupRequestBean punchOutSetupRequestBean;
      PunchOutInspectRequestBean punchOutInspectRequestBean;
      File cxmlFile;
      //fake it...
      if (cxmlDocument == null) {
         cxmlDocument = "";
      }
      //save request document
      try {
         cxmlFile = FileHandler.saveTempFile(cxmlDocument,"CXML_", ".XML",library.getString("document.backup.dir"));
      } catch (IOException ioe) {
         log.error("Error saving request:" + cxmlDocument, ioe);
         BaseException be = new BaseException("Error saving request");
         be.setRootCause(ioe);
         throw be;
      }
      //determine what type of document is coming in and call appropriate method
      if (cxmlDocument != null && cxmlDocument.indexOf("<PunchOutSetupRequest") > -1) {
         // create 
         if (cxmlDocument.indexOf("operation=\"create", cxmlDocument.indexOf("<PunchOutSetupRequest")) > -1) {
            punchOutSetupRequestParser = new PunchOutSetupRequestParser(getClient());
            punchOutSetupRequestBean = punchOutSetupRequestParser.parse(cxmlFile);
            if (!this.checkSharedSecret(punchOutSetupRequestBean.getSharedSecret())) {
               log.fatal("Invalid shared secret: " + punchOutSetupRequestBean.getSharedSecret());
               MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid shared secret","Document with invalid shared secret:" +
                                       punchOutSetupRequestBean.getSharedSecret());
            }
            cxmlResponse = this.processPunchOutSetupRequest(punchOutSetupRequestBean,sessionId);
            this.payloadId = punchOutSetupRequestBean.getPayloadId();
         // inspect   
         } else if (cxmlDocument.indexOf("operation=\"inspect", cxmlDocument.indexOf("<PunchOutSetupRequest")) > -1) {
            punchOutInspectRequestParser = new PunchOutInspectRequestParser(getClient());
            punchOutInspectRequestBean = punchOutInspectRequestParser.parse(cxmlFile);
            if (!this.checkSharedSecret(punchOutInspectRequestBean.getSharedSecret())) {
               log.fatal("Invalid shared secret: " + punchOutInspectRequestBean.getSharedSecret());
               MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid shared secret","Document with invalid shared secret:" +
                                       punchOutInspectRequestBean.getSharedSecret());
            }
            cxmlResponse = this.processPunchOutInspectRequest(punchOutInspectRequestBean,sessionId);
            this.payloadId = punchOutInspectRequestBean.getPayloadId();
         }
         
      } else if (cxmlDocument != null && cxmlDocument.indexOf("<OrderRequest>") > -1) {
         orderRequestParser = new OrderRequestParser(getClient());
         orderRequestBean = orderRequestParser.parse(cxmlFile);
         if (!this.checkSharedSecret(orderRequestBean.getSharedSecret())) {
            log.fatal("Invalid shared secret: " + orderRequestBean.getSharedSecret());
            MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid shared secret",
                                  "Document with invalid shared secret:" + orderRequestBean.getSharedSecret());
         }
         cxmlResponse = this.processOrderRequest(orderRequestBean);
      } else {
         //System.out.println("invalid document");
         //invalid order
         log.error("Invalid Seagate CXML doc:" + cxmlDocument);
         MailProcess.sendEmail(TO_EMAIL,"",FROM_EMAIL,"Invalid PNNL CXML doc","Document:" + cxmlDocument);
         cxmlResponse = "<Response>Invalid document</Response>";
      }
      
      return cxmlResponse;
   }
   
   private boolean checkSharedSecret(String s) {
      String SECRET = library.getString("secret.password");
      boolean flag = false;
      try {
         if (SECRET.matches(s)) {
            flag = true;
         }
      }  catch (Exception e) {
         //ignore as flag is false by default
      }
      return flag;
   }
   
   /**
    * Punch Out Setup Request
    */   
   public String processPunchOutSetupRequest(PunchOutSetupRequestBean bean, String sessionId) throws BaseException {
      PunchOutSetupResponseParser punchOutSetupResponseParser = new PunchOutSetupResponseParser(getClient());
      PunchOutSetupResponseBean punchOutSetupResponseBean = new PunchOutSetupResponseBean();
      //do whatever Chuck does
      CxmlDocumentDataBean cxmlDocumentDataBean = new CxmlDocumentDataBean();
      cxmlDocumentDataBean.setFromDomain(bean.getFromDomain());
      cxmlDocumentDataBean.setFromIdentity(bean.getFromIdentity());
      cxmlDocumentDataBean.setPayloadId(bean.getPayloadId());
      cxmlDocumentDataBean.setResponseCode("200");  
      cxmlDocumentDataBean.setResponsePayloadId(CxmlHandler.getPayloadId());
      cxmlDocumentDataBean.setSenderDomain(bean.getSenderDomain());
      cxmlDocumentDataBean.setSenderIdentity(bean.getSenderIdentity());
      cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
      cxmlDocumentDataBean.setToDomain(bean.getToDomain());
      cxmlDocumentDataBean.setToIdentity(bean.getToIdentity());
      cxmlDocumentDataBean.setType("PunchOutSetupRequest");
      cxmlDocumentDataBean.setUserAgent(bean.getUserAgent());
      DbManager dbManager = new DbManager(getClient());
      CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new CxmlDocumentDataBeanFactory(dbManager);
      cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
      PunchOutSetupRequestBean punchoutSetupRequestBean = new PunchOutSetupRequestBean();
      punchoutSetupRequestBean.setBrowserFormPost(bean.getBrowserFormPost());
      punchoutSetupRequestBean.setBuyerCookie(bean.getBuyerCookie());
      punchoutSetupRequestBean.setOperation(bean.getOperation());
      punchoutSetupRequestBean.setPayloadId(bean.getPayloadId());
      PunchOutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new PunchOutSetupRequestBeanFactory(dbManager);
      punchoutSetupRequestBeanFactory.insert(punchoutSetupRequestBean);
      PunchoutSessionBean punchoutSessionBean = new PunchoutSessionBean();
      punchoutSessionBean.setOracle("Y");
      punchoutSessionBean.setPayloadId(bean.getPayloadId());
      punchoutSessionBean.setSessionId(sessionId);
      // punchoutSessionBean.setUserId( (String) bean.getAdditionalInfo().get("User"));  // additional info is Extrinsic -- do not have here (yet)
      PunchoutSessionBeanFactory punchoutSessionBeanFactory = new PunchoutSessionBeanFactory(dbManager);
      punchoutSessionBeanFactory.insert(punchoutSessionBean);
      //create resonse
      punchOutSetupResponseBean.setPayloadId(cxmlDocumentDataBean.getResponsePayloadId());
      punchOutSetupResponseBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
      punchOutSetupResponseBean.setStatusCode("200"); 
      punchOutSetupResponseBean.setStatusText("OK");
      punchOutSetupResponseBean.setStartPageUrl(library.getString("punchoutsetuprequest.startpageurl") +bean.getPayloadId());
      return punchOutSetupResponseParser.getResponse(punchOutSetupResponseBean);
   }
   
   /**
    * Order Request
    */   
   public String processOrderRequest(OrderRequestBean bean) throws BaseException {   
      String prNum;
      String lineNum;
      BigDecimal prNumber;
      BigDecimal lineNumber;
      //do whatever Chuck does
      VvAccountSysBean vvAccountSysBean = new VvAccountSysBean();
      ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean = new ShoppingCartItemTypeViewBean();
      CxmlDocumentDataBean cxmlDocumentDataBean = new CxmlDocumentDataBean();
      cxmlDocumentDataBean.setFromDomain(bean.getFromDomain());
      cxmlDocumentDataBean.setFromIdentity(bean.getFromIdentity());
      cxmlDocumentDataBean.setPayloadId(bean.getPayloadId());
      cxmlDocumentDataBean.setResponseCode("200");
      cxmlDocumentDataBean.setResponsePayloadId(CxmlHandler.getPayloadId());
      cxmlDocumentDataBean.setSenderDomain(bean.getSenderDomain());
      cxmlDocumentDataBean.setSenderIdentity(bean.getSenderIdentity());
      cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
      cxmlDocumentDataBean.setToDomain(bean.getToDomain());
      cxmlDocumentDataBean.setToIdentity(bean.getToIdentity());
      cxmlDocumentDataBean.setType("OrderRequest");
      cxmlDocumentDataBean.setUserAgent(bean.getUserAgent());
      DbManager dbManager = new DbManager(getClient());
      CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new CxmlDocumentDataBeanFactory(dbManager);
      cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
      
      OrderRequestBean cxmlOrderRequestBean = new OrderRequestBean();
      cxmlOrderRequestBean.setCurrency(bean.getCurrency());
      cxmlOrderRequestBean.setOrderDate(bean.getOrderDate());
      cxmlOrderRequestBean.setOrderId(bean.getOrderId());
      cxmlOrderRequestBean.setOrderType(bean.getOrderType());
      cxmlOrderRequestBean.setPayloadId(bean.getPayloadId());
      cxmlOrderRequestBean.setTotalAmount(bean.getTotalAmount());
      CxmlOrderRequestBeanFactory cxmlOrderRequestBeanFactory = new CxmlOrderRequestBeanFactory(dbManager);
      cxmlOrderRequestBeanFactory.insert(cxmlOrderRequestBean);
      //get line items
      Collection itemCollection = bean.getItemBeanCollection();
      if (log.isDebugEnabled()) {
         log.debug("There are " + itemCollection.size() + " line items.");
      }
      Map purchaseReqs = new HashMap();
      String status = null;
      VvAccountSysBeanFactory vvAccountSysBeanFactory = new VvAccountSysBeanFactory(dbManager);
      ShoppingCartItemTypeViewBeanFactory shoppingCartItemTypeViewBeanFactory = new ShoppingCartItemTypeViewBeanFactory(dbManager);
      RequestLineItemBeanFactory requestLineItemBeanFactory = new  RequestLineItemBeanFactory(dbManager);
      PurchaseRequestBeanFactory purchaseRequestBeanFactory = new PurchaseRequestBeanFactory(dbManager);
      DoManifestBeanFactory doManifestBeanFactory = new DoManifestBeanFactory(dbManager);
      Iterator iterator = itemCollection.iterator();
      while (iterator.hasNext()) {
         ItemBean itemBean = (ItemBean) iterator.next();
         //validate unit price and quantity
         try {
            BigDecimal up = new BigDecimal(itemBean.getUnitPrice());
         } catch (Exception e) {
            log.error("Invalid unit price:" + itemBean.getUnitPrice());
            MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid unit price",
                  "Invalid unit price:" + itemBean.getUnitPrice() +  " for supplier part " + itemBean.getSupplierPartId());
            continue;
         }
         try {
            BigDecimal qty = new BigDecimal(itemBean.getQuantity());
         } catch (Exception e) {
            log.error("Invalid quantity:" + itemBean.getQuantity());
            MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid quantity",
                 "Invalid quantity:" + itemBean.getQuantity() + " for supplier part " + itemBean.getSupplierPartId());
            continue;
         }
         //validate classification
         if (itemBean.getClassificationDomain().toLowerCase().indexOf("spsc") < 0) {
            log.error("Invalid classification: " + itemBean.getClassificationDomain() +" for supplier part " + itemBean.getSupplierPartId());
            throw new BaseException("Invalid classification:" + itemBean.getClassificationDomain());
         }
         SearchCriteria accountSysCriteria = new SearchCriteria("accountSysDesc",SearchCriterion.EQUALS, itemBean.getClassification());         
         Iterator accountSysIterator = vvAccountSysBeanFactory.select(accountSysCriteria).iterator();
         while (accountSysIterator.hasNext()) {
            vvAccountSysBean = (VvAccountSysBean) accountSysIterator.next();
         }
         // extract the PR num and line from SupplierPartAuxiliaryId(): (pr-line)
         // * PROBLEM: getSupplierPartAuxiliaryId()
         prNum = itemBean.getSupplierPartAuxiliaryId().substring(0,itemBean.getSupplierPartAuxiliaryId().indexOf("-"));
         lineNum = itemBean.getSupplierPartAuxiliaryId().substring(itemBean.getSupplierPartAuxiliaryId().indexOf("-") + 1);
         prNumber = new BigDecimal(prNum);
         lineNumber = new BigDecimal(lineNum);
         //get item type
         
         SearchCriteria itemTypeCriteria = new SearchCriteria("prNumber",SearchCriterion.EQUALS,prNum);
         itemTypeCriteria.addCriterion("lineItem", SearchCriterion.EQUALS,lineNum);
         Iterator itemTypeIterator = shoppingCartItemTypeViewBeanFactory.select(itemTypeCriteria).iterator();
         while (itemTypeIterator.hasNext()) {
            shoppingCartItemTypeViewBean = (ShoppingCartItemTypeViewBean)
            itemTypeIterator.next();                        
            if (!purchaseReqs.containsKey(prNum.intern())) {
               status = shoppingCartItemTypeViewBean.getPrStatus();
            } else {               
               status = (String) purchaseReqs.get(prNum);
            }
         }
         try {
            requestLineItemBeanFactory.updateDoNumber(bean.getOrderId(),prNumber,lineNumber);
            if (!purchaseReqs.containsKey(prNum.intern())) {
               purchaseReqs.put(prNum.intern(),status);
               // *TODO: does this apply to any PNNL case?
               //there won't be any cc info for ERS orders
               if (bean.getToIdentity() != null && !bean.getToIdentity().endsWith("ERS")) {
                  purchaseRequestBeanFactory.updateCreditCard(prNumber,
                                                              bean.getPaymentCardName(),
                                                              new BigDecimal(bean.getPaymentCardNumber()),
                                                              DateHandler.getDateFromIso8601String(bean.getPaymentCardExpiration()));
               }
            }
         } catch (Exception e) {
            log.error("Order request processing error:" + e.getMessage());
            continue;
         }
         if (log.isDebugEnabled()) {
            log.debug("MR Number and line number is: "
            + prNum
            + "/"
            + lineNum
            + " item type is: "
            + shoppingCartItemTypeViewBean.getItemType()
            + " account sys is: "
            + vvAccountSysBean.getAccountSysId()
            + " cancel status is "
            + shoppingCartItemTypeViewBean.getCancelStatus());
         }
         
         if ("canceled".equals(shoppingCartItemTypeViewBean.getCancelStatus()) ||
             "Cancelled".equals(shoppingCartItemTypeViewBean.getRequestLineStatus())) {
            continue;
         }
         
         // if itemType IS NOT service fee (SF) or bulk gas (BG) and NOT Production,
         // then change order status to POSUBMIT and allocate inventory
         if (shoppingCartItemTypeViewBean.getItemType() == null
            || (shoppingCartItemTypeViewBean.getItemType() != null
                && !"BG".equals(shoppingCartItemTypeViewBean.getItemType())
                && !"SF".equals(shoppingCartItemTypeViewBean.getItemType())
                && !"TC".equals(shoppingCartItemTypeViewBean.getItemType()))) {
            if (vvAccountSysBean.getAccountSysId() != null && !"posubmit".equalsIgnoreCase(status)) {
               if (shoppingCartItemTypeViewBean.getCancelStatus() == null || !"canceled".equals(shoppingCartItemTypeViewBean.getCancelStatus())) {
                  try {
                     if (log.isDebugEnabled()) {
                        log.debug("SETTING POSUBMIT");
                     }                     
                     purchaseRequestBeanFactory.updatePrStatus(prNumber, "posubmit", new Date());                     
                     requestLineItemBeanFactory.updateStatus("In Progress", "Open", new Date(), prNumber, lineNumber);                     
                  }
                  catch (Exception e) {
                     log.error("OrderRecord processing error:", e);
                     continue;
                  }
                  try {
                     if (log.isDebugEnabled()) {
                        log.debug("CALLING LINE_ITEM_ALLOCATE");
                     }
                     Collection procParams = new Vector(2);
                     procParams.add(prNumber);
                     procParams.add(lineNumber);
                     GenericProcedureFactory genericProcedureFactory = new GenericProcedureFactory(dbManager);
                     genericProcedureFactory.doProcedure("p_line_item_allocate",procParams);
                  }
                  catch (Exception e) {
                     log.error("Order request processing error: Unable to allocate inventory for MR" + itemBean.getSupplierPartAuxiliaryId(),e);
                     MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,"Unable to allocate inventory","Unable to allocate inventory for " +itemBean.getSupplierPartAuxiliaryId());
                     continue;
                  }
               }
            }
         }
         //Chucks code had a piece that inserted values into account_entry that I'm skipping
         
         DoManifestBean doManifestBean = new DoManifestBean();
         doManifestBean.setDoNumber(bean.getOrderId());
         doManifestBean.setPrNumber(prNumber);
         doManifestBean.setLineItem(lineNum);
         doManifestBean.setQuantity(new BigDecimal(itemBean.getQuantity()));
         doManifestBean.setUnitPrice(new BigDecimal(itemBean.getUnitPrice()));
         doManifestBeanFactory.insert(doManifestBean);         
      }
      // * TODO: determine if we need an OrderRequestResponse (if not what do we need to return here, "" (empty str) ?)
      //OrderRequestResponseBean responseBean = new OrderRequestResponseBean();
      //responseBean.setPayloadId(CxmlHandler.getPayloadId());
      //responseBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
      //responseBean.setStatusCode("200");
      //responseBean.setStatusText("OK");
      //OrderRequestResponseParser parser = new OrderRequestResponseParser(getClient());
      //return parser.getResponse(responseBean);
      return "";
   }
   
   /**
    * Punch Out Inspect Request
    */   
   public String processPunchOutInspectRequest(PunchOutInspectRequestBean bean, String sessionId) throws BaseException {
      PunchOutSetupResponseParser punchOutSetupResponseParser = new PunchOutSetupResponseParser(getClient());
      PunchOutSetupResponseBean punchOutInspectResponseBean = new PunchOutSetupResponseBean();
      DbManager dbManager = new DbManager(getClient());
      /*    
      //do whatever Chuck does
       *** no need to insert anything in the cXML_Document_Data table      
      CxmlDocumentDataBean cxmlDocumentDataBean = new CxmlDocumentDataBean();
      cxmlDocumentDataBean.setFromDomain(bean.getFromDomain());
      cxmlDocumentDataBean.setFromIdentity(bean.getFromIdentity());
      cxmlDocumentDataBean.setPayloadId(bean.getPayloadId());
      cxmlDocumentDataBean.setResponseCode("200");  
      cxmlDocumentDataBean.setResponsePayloadId(CxmlHandler.getPayloadId());
      cxmlDocumentDataBean.setSenderDomain(bean.getSenderDomain());
      cxmlDocumentDataBean.setSenderIdentity(bean.getSenderIdentity());
      cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
      cxmlDocumentDataBean.setToDomain(bean.getToDomain());
      cxmlDocumentDataBean.setToIdentity(bean.getToIdentity());
      cxmlDocumentDataBean.setType("PunchOutSetupRequest");
      cxmlDocumentDataBean.setUserAgent(bean.getUserAgent());
      
      CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new CxmlDocumentDataBeanFactory(dbManager);
      cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
       
       *** no need to insert anything in the punchout_setup_request table
      PunchoutSetupRequestBean punchoutSetupRequestBean = new PunchoutSetupRequestBean();
      punchoutSetupRequestBean.setBrowserFormPost(bean.getBrowserFormPost());
      punchoutSetupRequestBean.setBuyerCookie(bean.getBuyerCookie());
      punchoutSetupRequestBean.setOperation(bean.getOperation());
      punchoutSetupRequestBean.setPayloadId(bean.getPayloadId());
      PunchoutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new PunchoutSetupRequestBeanFactory(dbManager);
      punchoutSetupRequestBeanFactory.insert(punchoutSetupRequestBean);
       */
      PunchoutSessionBean punchoutSessionBean = new PunchoutSessionBean();
      punchoutSessionBean.setOracle("Y");
      punchoutSessionBean.setPayloadId(bean.getPayloadId());
      punchoutSessionBean.setSessionId(sessionId);
      // punchoutSessionBean.setUserId( (String) bean.getAdditionalInfo().get("User"));  // additional info is Extrinsic -- do not have here (yet)
      PunchoutSessionBeanFactory punchoutSessionBeanFactory = new PunchoutSessionBeanFactory(dbManager);
      //  ** do we need to insert into Punchout_Session? (will go ahead and do it)
      punchoutSessionBeanFactory.insert(punchoutSessionBean);
      //create response
      punchOutInspectResponseBean.setPayloadId(CxmlHandler.getPayloadId());
      punchOutInspectResponseBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
      punchOutInspectResponseBean.setStatusCode("200"); 
      punchOutInspectResponseBean.setStatusText("OK");
      punchOutInspectResponseBean.setStartPageUrl(library.getString("punchoutinspectrequest.startpageurl") +bean.getSupplierPartId());
      return punchOutSetupResponseParser.getResponse(punchOutInspectResponseBean);            
   }
   
   /**
    * Punchout Order Message
    * (Shopping Cart Response)
    */   
   public String getPunchoutOrderMessage(String payloadId, PunchOutSetupRequestBean punchoutSetupRequestBean, Collection shoppingCartBeanColl, DbManager dbManager) throws BaseException {
      //DbManager dbManager = new DbManager(getClient());
      
      CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new CxmlDocumentDataBeanFactory(dbManager);
      Collection cxmlDocumentDataBeanColl = cxmlDocumentDataBeanFactory.select(new SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId));
      Iterator cxmlDocumentIterator = cxmlDocumentDataBeanColl.iterator();
      CxmlDocumentDataBean cxmlDocumentDataBean = null;
      while (cxmlDocumentIterator.hasNext()) {
         cxmlDocumentDataBean = (CxmlDocumentDataBean) cxmlDocumentIterator.next();
         cxmlDocumentDataBean.setPayloadId(CxmlHandler.getPayloadId());
         cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
      }
      
      /* -- replaced
      PunchoutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new PunchoutSetupRequestBeanFactory(dbManager);
      Collection punchoutSetupRequestBeanColl = punchoutSetupRequestBeanFactory.select(new SearchCriteria("payloadId",SearchCriterion.EQUALS,payloadId));
      Iterator punchoutSetupRequestIterator = punchoutSetupRequestBeanColl.iterator();
      PunchoutSetupRequestBean punchoutSetupRequestBean = null;
      while (punchoutSetupRequestIterator.hasNext()) {
         punchoutSetupRequestBean = (PunchoutSetupRequestBean) punchoutSetupRequestIterator.next();
      }
      */
      
      
      //get pr_number  **** PR Number not needed for PNNL - shopping cart is just a 'wishlist', not an order
      /*
      RequestLineItemBeanFactory requestLineItemBeanFactory = new  RequestLineItemBeanFactory(dbManager);
      Collection requestLineItemBeanColl = requestLineItemBeanFactory.select(new SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId));
      Iterator rliIterator = requestLineItemBeanColl.iterator();
      RequestLineItemBean requestLineItemBean = null;
      while (rliIterator.hasNext()) {
         requestLineItemBean = (RequestLineItemBean) rliIterator.next();
      }
       */
      
      //get line items  **** for now these come in to this procedure
      /*
      ShoppingCartViewBeanFactory shoppingCartViewBeanFactory = new ShoppingCartViewBeanFactory(dbManager);
      SearchCriteria lineItemCriteria = new SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId);
      //    lineItemCriteria.addCriterion("prNumber", SearchCriterion.EQUALS,requestLineItemBean.getPrNumber().toString());
      Collection shoppingCartViewBeanColl = shoppingCartViewBeanFactory.select(lineItemCriteria);
      Iterator shoppingCartIterator = shoppingCartViewBeanColl.iterator();
       */
      Iterator shoppingCartIterator = shoppingCartBeanColl.iterator();
      //not sure why this is done but Chucks code does it....
      while (shoppingCartIterator.hasNext()) {
         PnnlShoppingCartBean shoppingCartBean = (PnnlShoppingCartBean) shoppingCartIterator.next();
         // **TODO: verify this business logic for PNNL
         if (shoppingCartBean.getMaterialDesc() != null && shoppingCartBean.getMaterialDesc().length() > 100) {
            shoppingCartBean.setMaterialDesc(shoppingCartBean.getMaterialDesc().substring(0, 100));
         }         
         if (shoppingCartBean.getPackaging() != null && shoppingCartBean.getPackaging().length() > 100) {
            shoppingCartBean.setPackaging(shoppingCartBean.getPackaging().substring(0, 100));
         }
         // do not allow edits of Production or Service Fee orders
         if ("Production".equalsIgnoreCase(shoppingCartBean.getAccountSysId()) ||
             "SF".equalsIgnoreCase(shoppingCartBean.getItemType()) ||
             "TC".equalsIgnoreCase(shoppingCartBean.getItemType()) ||
             "BG".equalsIgnoreCase(shoppingCartBean.getItemType())) {
            //set operationAllowed to "create"
         }
         // if this is a service fee set the UN/SPSC code appropriately
         if ("SF".equalsIgnoreCase(shoppingCartBean.getItemType())) {
            if ("SHAK".equals(shoppingCartBean.getUseFacilityId())) {
               shoppingCartBean.setAccountSysDesc("12000020");
            } else {
               shoppingCartBean.setAccountSysDesc("12000040");
            }
         }
         // if this is a tank lease set the UN/SPSC code appropriately
         if ("TC".equalsIgnoreCase(shoppingCartBean.getItemType())) {
            if ("SHAK".equals(shoppingCartBean.getUseFacilityId())) {
               shoppingCartBean.setAccountSysDesc("12000020");
            } else {
               shoppingCartBean.setAccountSysDesc("12000030");
            }
         }
         // if this is a tank lease set the UN/SPSC code appropriately
         if ("BG".equalsIgnoreCase(shoppingCartBean.getItemType()) && 
             "SHAK".equals(shoppingCartBean.getUseFacilityId())) {
            shoppingCartBean.setAccountSysDesc("12000020");
         }
      }
      
      //save-cxml-document-data
      cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
      //save-oracle-punchout-order-message
      CxmlPunchOutOrderMessageBeanFactory punchoutOrderMessageBeanFactory = new CxmlPunchOutOrderMessageBeanFactory(dbManager);
      CxmlPunchOutOrderMessageBean punchoutOrderMessageBean = new CxmlPunchOutOrderMessageBean();
      punchoutOrderMessageBean.setBrowserFormPost(punchoutSetupRequestBean.getBrowserFormPost());
      punchoutOrderMessageBean.setBuyerCookie(punchoutSetupRequestBean.getBuyerCookie());
      punchoutOrderMessageBean.setPayloadId(payloadId);
      punchoutOrderMessageBean.setPostedToOracle("Y");
      //punchoutOrderMessageBean.setPrNumber(requestLineItemBean.getPrNumber());
      punchoutOrderMessageBean.setPunchout("Y");
      punchoutOrderMessageBean.setResponsePayloadId(cxmlDocumentDataBean.getResponsePayloadId());
      punchoutOrderMessageBeanFactory.update(punchoutOrderMessageBean);
      
      //save-session-id --??
      //PunchoutSessionBeanFactory punchoutSessionBeanFactory = new PunchoutSessionBeanFactory(dbManager);
      
      CxmlShoppingCartParser parser = new CxmlShoppingCartParser(getClient());
      String returnString = parser.getCxml(cxmlDocumentDataBean,punchoutSetupRequestBean,shoppingCartBeanColl);
      //since we're sending this back in a html form we need to do this
      returnString = returnString.replaceAll("&", "&amp;");
      returnString = returnString.replaceAll("\"", "&quot;");
      return returnString;
   }
   
   public void sendShoppingCart(String payloadId, Collection cartBeanCollection) throws BaseException {
      DbManager dbManager = new DbManager(getClient());
      Collection pnnlCartBeanColl = convertCartBeans(cartBeanCollection);
      PunchOutSetupRequestBean punchoutSetupRequestBean = getSetupRequestBean(payloadId, dbManager);
      String xmlShoppingCartResponse = getPunchoutOrderMessage(payloadId,punchoutSetupRequestBean,pnnlCartBeanColl,dbManager);
      try {
         URL cartResponseURL = new URL(punchoutSetupRequestBean.getBrowserFormPost());
         TcmisHttpURLConnection httpURLConn = new TcmisHttpURLConnection(cartResponseURL);
         httpURLConn.connect();
         httpURLConn.sendRequest(xmlShoppingCartResponse,"text/xml; charset=UTF-8");
      } catch (MalformedURLException mfe) {
         throw new BaseException(mfe.getCause());
      }
   }
   
   private PunchOutSetupRequestBean getSetupRequestBean(String payloadId, DbManager dbManager) throws BaseException {
      PunchOutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new PunchOutSetupRequestBeanFactory(dbManager);
      Collection punchoutSetupRequestBeanColl = punchoutSetupRequestBeanFactory.select(new SearchCriteria("payloadId",SearchCriterion.EQUALS,payloadId));
      Iterator punchoutSetupRequestIterator = punchoutSetupRequestBeanColl.iterator();
      PunchOutSetupRequestBean punchoutSetupRequestBean = null;
      while (punchoutSetupRequestIterator.hasNext()) {
         punchoutSetupRequestBean = (PunchOutSetupRequestBean) punchoutSetupRequestIterator.next();
      }
      return punchoutSetupRequestBean;
   }
   
   /* 
    * @param shoppingCartBeanColl A collection of ShoppingCartBean
    * @return Collection of PnnlShoppingCartBean
    */
   private Collection convertCartBeans(Collection shoppingCartBeanColl) {
      Collection pnnlCart = null;
      if (shoppingCartBeanColl != null) {
         pnnlCart = new Vector(shoppingCartBeanColl.size());
         Iterator cartIter = shoppingCartBeanColl.iterator();
         ShoppingCartBean sourceCartBean = null;
         while (cartIter.hasNext()) {
            sourceCartBean = (ShoppingCartBean) cartIter.next();
            PnnlShoppingCartBean  pnnlCartBean = new PnnlShoppingCartBean();
            pnnlCartBean.setQuantity(sourceCartBean.getQuantity());
            pnnlCartBean.setFacPartNo(sourceCartBean.getCatPartNo());
            pnnlCartBean.setQuotedPrice(sourceCartBean.getCatalogPrice());
            pnnlCartBean.setMaterialDesc(sourceCartBean.getPartDescription());
            pnnlCartBean.setAccountSysDesc("12000000");
            pnnlCartBean.setPackaging(sourceCartBean.getExamplePackaging());
            pnnlCartBean.setCurrencyId(sourceCartBean.getCurrencyId());
            pnnlCartBean.setClassification("12000000");
            pnnlCart.add(pnnlCartBean);
         }
         
      }
      return pnnlCart;
   }
}