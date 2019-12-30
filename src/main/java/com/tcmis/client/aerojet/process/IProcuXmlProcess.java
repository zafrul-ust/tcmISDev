package com.tcmis.client.aerojet.process;

import java.io.*;
import java.math.*;
import java.util.*;

import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.cxml.beans.*;
import com.tcmis.client.cxml.process.*;
import com.tcmis.client.order.beans.*;
import com.tcmis.client.order.factory.*;
import com.tcmis.client.cxml.beans.OrderRequestBean;
import com.tcmis.client.cxml.factory.*;
import com.tcmis.client.cxml.util.*;
import com.tcmis.common.admin.beans.*;
import com.tcmis.common.admin.factory.*;
import com.tcmis.common.admin.process.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;

public class IProcuXmlProcess
    extends BaseProcess {

  private final static String TO_EMAIL = "deverror@haastcm.com";
  private final static String FROM_EMAIL = "aerojet_cxml@haastcm.com";
  private String payloadId;
  private String sender;
  private String browserFormPost;
  private String buyerCockie;
  private String responsePayloadId;
  private String userId;
  private String logonId = null;
  private boolean newProc = false;
  private ResourceLibrary library = null;
  private GenericSqlFactory factory = null;
  public IProcuXmlProcess(String client) {
    super(client);
    library = new ResourceLibrary("aerojet");
    factory = new GenericSqlFactory(new DbManager(getClient()));
  }

  //this is a bit ugly....
  public String getPayloadId() {
    return this.payloadId;
  }
// Larry Note: This is really bad, but I don't have time to change it so just follow the ugly mode...
  public String getSender() {
	    return this.sender;
  }
  public void setResourceLib(String client) {
	  library = new ResourceLibrary(client);
  }
  public String getBuyerCockie() {
	    return this.buyerCockie;
  }
  public String getBrowserFormPost() {
	    return this.browserFormPost;
  }
  public String getResponsePayloadId() {
	    return this.responsePayloadId;
  }
  public String getUserId() {
	    return this.userId;
  }
  public boolean isNewProc() {
	return newProc;
  }
  public void setNewProc(boolean newProc) {
	this.newProc = newProc;
  }

//  

  /*********************************************************************
   * Save the incoming document to the file system and then process it.
   *********************************************************************/
  public String processSetUpDocument(String xmlDocument, String sessionId) throws
      BaseException {
    if (log.isDebugEnabled()) {
      log.debug("processing xml document:" + xmlDocument);
    }
    //System.out.println("processing document" + cxmlDocument);
    String xmlResponse = null;
    boolean validOrder = true;
//    OrderRequestParser orderRequestParser;
    //PunchOutOrderMessageParser punchOutOrderMessageParser;
    IProcLoginRequestXmlParser punchOutSetupRequestParser;
    PunchOutSetupResponseParser punchOutSetupResponseParser;
    OrderRequestBean orderRequestBean;
    PunchOutOrderMessageBean punchOutOrderMessageBean;
    PunchOutSetupRequestBean punchOutSetupRequestBean;
    File cxmlFile;
    //fake it...
    if (xmlDocument == null) {
      xmlDocument = "";
    }
    //save request document
    try {
      cxmlFile = FileHandler.saveTempFile(xmlDocument,
                                          "XML_", ".XML",
                                          library.getString(
          "document.backup.dir"));
    }
    catch (IOException ioe) {
      log.error("Error saving xml request:" + xmlDocument, ioe);
      BaseException be = new BaseException("Error saving request");
      be.setRootCause(ioe);
      throw be;
    }
    //determine what type of document is coming in and call appropriate method
    if (xmlDocument != null &&
        xmlDocument.indexOf("<request") > -1) {
      punchOutSetupRequestParser = new IProcLoginRequestXmlParser(getClient());
      punchOutSetupRequestBean = punchOutSetupRequestParser.parse(cxmlFile,sessionId);
      if (!this.newProc && !this.checkSharedSecret(punchOutSetupRequestBean.getSharedSecret())) {
        log.fatal("Invalid shared secret: " +
                  punchOutSetupRequestBean.getSharedSecret());
        MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid shared secret",
                              "Document with invalid shared secret:" +
                              punchOutSetupRequestBean.getSharedSecret());
      }
//      sender = punchOutSetupRequestBean.getSenderIdentity();
      if( newProc )  {
//          buyerCockie = punchOutSetupRequestBean.getBuyerCookie();
          browserFormPost = punchOutSetupRequestBean.getBrowserFormPost();
          userId = (String) punchOutSetupRequestBean.getExtrinsicName();//.get("User");
    	  String personnelId = getPersonnelIdFromCxmlId(userId);
    	  this.logonId = getLoginIdfromPersonnelId(personnelId);
      }
      xmlResponse = this.processPunchOutSetupRequest(punchOutSetupRequestBean);
      this.payloadId = punchOutSetupRequestBean.getPayloadId();

    }
    else {
      //System.out.println("invalid document");
      //invalid order
      log.error("Invalid Aerojet CXML doc:" + xmlDocument);
      MailProcess.sendEmail(TO_EMAIL,
                            "",
                            FROM_EMAIL,
                            "Invalid Aerojet CXML doc",
                            "Document:" + xmlDocument);
      xmlResponse = "<Response>Invalid document</Response>";
    }

    return xmlResponse;
  }

  public String processOrderRequestDocument(String cxmlDocument, String sessionId) throws
  BaseException {
	  if (log.isDebugEnabled()) {
		  log.debug("processing document:" + cxmlDocument);
	  }
	  //System.out.println("processing document" + cxmlDocument);
	  String cxmlResponse = null;
	  boolean validOrder = true;
	  OrderRequestParser orderRequestParser;
	  //PunchOutOrderMessageParser punchOutOrderMessageParser;
	  PunchOutSetupRequestParser punchOutSetupRequestParser;
	  PunchOutSetupResponseParser punchOutSetupResponseParser;
	  OrderRequestBean orderRequestBean;
	  PunchOutOrderMessageBean punchOutOrderMessageBean;
	  PunchOutSetupRequestBean punchOutSetupRequestBean;
	  File cxmlFile;
	  //fake it...
	  if (cxmlDocument == null) {
		  cxmlDocument = "";
	  }
	  //save request document
	  try {
		  cxmlFile = FileHandler.saveTempFile(cxmlDocument,
				  "XML_", ".XML",
				  library.getString(
						  "document.backup.dir"));
	  }
	  catch (IOException ioe) {
		  log.error("Error saving request:" + cxmlDocument, ioe);
		  BaseException be = new BaseException("Error saving request");
		  be.setRootCause(ioe);
		  throw be;
	  }
	  //determine what type of document is coming in and call appropriate method
	  if (cxmlDocument != null &&
			  cxmlDocument.indexOf("<OrderRequest>") > -1) {
		  orderRequestParser = new OrderRequestParser(getClient());
		  orderRequestBean = orderRequestParser.parse(cxmlFile);
		  if (!this.checkSharedSecret(orderRequestBean.getSharedSecret())) {
			  log.fatal("Invalid shared secret: " + orderRequestBean.getSharedSecret());
			  MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid shared secret",
					  "Document with invalid shared secret:" +
							  orderRequestBean.getSharedSecret());
		  }
		  cxmlResponse = this.processOrderRequest(orderRequestBean);
	  }
	  else {
		  //System.out.println("invalid document");
		  //invalid order
		  log.error("Invalid Aerojet XML doc:" + cxmlDocument);
		  MailProcess.sendEmail(TO_EMAIL,
				  "",
				  FROM_EMAIL,
				  "Invalid Aerojet CXML doc",
				  "Document:" + cxmlDocument);
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
	  }
	  catch (Exception e) {
		  //ignore as flag is false by default
	  }
	  return flag;
  }

  public String processPunchOutSetupRequest(PunchOutSetupRequestBean bean) throws
      BaseException {
	LoginResponseXmlParser punchOutSetupResponseParser = new
			  LoginResponseXmlParser(getClient());
    PunchOutSetupResponseBean punchOutSetupResponseBean = new
        PunchOutSetupResponseBean();
    //do whatever Chuck does
    CxmlDocumentDataBean cxmlDocumentDataBean = new CxmlDocumentDataBean();
    cxmlDocumentDataBean.setFromDomain(bean.getFromDomain());
    cxmlDocumentDataBean.setFromIdentity(bean.getFromIdentity());
    cxmlDocumentDataBean.setPayloadId(bean.getPayloadId());
    cxmlDocumentDataBean.setResponseCode("200");
    responsePayloadId = CxmlHandler.getPayloadId();
    cxmlDocumentDataBean.setResponsePayloadId(responsePayloadId);
    cxmlDocumentDataBean.setSenderDomain(bean.getSenderDomain());
    cxmlDocumentDataBean.setSenderIdentity(bean.getSenderIdentity());
    cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
    cxmlDocumentDataBean.setToDomain(bean.getToDomain());
    cxmlDocumentDataBean.setToIdentity(bean.getToIdentity());
    cxmlDocumentDataBean.setType("LoginRequest");
    cxmlDocumentDataBean.setUserAgent(bean.getUserAgent());
    DbManager dbManager = new DbManager(getClient());
    CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new
        CxmlDocumentDataBeanFactory(dbManager);
    cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
    PunchOutSetupRequestBean
        punchoutSetupRequestBean = new PunchOutSetupRequestBean();
        														   
    punchoutSetupRequestBean.setBrowserFormPost(bean.getBrowserFormPost());
    punchoutSetupRequestBean.setBuyerCookie(bean.getBuyerCookie());
    punchoutSetupRequestBean.setOperation(bean.getOperation());
    punchoutSetupRequestBean.setPayloadId(bean.getPayloadId());
    PunchOutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new
        PunchOutSetupRequestBeanFactory(dbManager);
    punchoutSetupRequestBeanFactory.insert(punchoutSetupRequestBean);
    PunchoutSessionBean punchoutSessionBean = new PunchoutSessionBean();
    punchoutSessionBean.setOracle("Y");
    punchoutSessionBean.setPayloadId(bean.getPayloadId());
    punchoutSessionBean.setSessionId(bean.getPayloadId());
    punchoutSessionBean.setUserId( bean.getExtrinsicName() );//
    PunchoutSessionBeanFactory punchoutSessionBeanFactory = new
        PunchoutSessionBeanFactory(dbManager);
    punchoutSessionBeanFactory.insert(punchoutSessionBean);
    //create resonse
    punchOutSetupResponseBean.setPayloadId(cxmlDocumentDataBean.
                                           getResponsePayloadId());
    punchOutSetupResponseBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
    punchOutSetupResponseBean.setStatusCode("200");
    punchOutSetupResponseBean.setStatusText("Success");
    String urlparams = bean.getPayloadId();
    if( this.logonId != null )  {
    	urlparams += "&amp;logonId="+logonId;
        punchOutSetupResponseBean.setStartPageUrl(library.getString(
                "loginrequest.startpageurl") +urlparams );
    }
    return punchOutSetupResponseParser.getResponse(punchOutSetupResponseBean);
  }

  public String processOrderRequest(OrderRequestBean bean) throws BaseException {

    //do whatever Chuck does
    VvAccountSysBean vvAccountSysBean = new VvAccountSysBean();
    ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean = new
        ShoppingCartItemTypeViewBean();
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
    CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new
        CxmlDocumentDataBeanFactory(dbManager);
    cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);

    OrderRequestBean cxmlOrderRequestBean = new OrderRequestBean();
    cxmlOrderRequestBean.setCurrency(bean.getCurrency());
    cxmlOrderRequestBean.setOrderDate(bean.getOrderDate());
    cxmlOrderRequestBean.setOrderId(bean.getOrderId());
    cxmlOrderRequestBean.setOrderType(bean.getOrderType());
    cxmlOrderRequestBean.setPayloadId(bean.getPayloadId());
    cxmlOrderRequestBean.setTotalAmount(bean.getTotalAmount());
    CxmlOrderRequestBeanFactory cxmlOrderRequestBeanFactory = new
        CxmlOrderRequestBeanFactory(dbManager);
    cxmlOrderRequestBeanFactory.insert(cxmlOrderRequestBean);
    //get line items
    Collection itemCollection = bean.getItemBeanCollection();
    if (log.isDebugEnabled()) {
      log.debug("There are " + itemCollection.size() + " line items.");
    }
    Map purchaseReqs = new HashMap();
    String status = null;
    VvAccountSysBeanFactory vvAccountSysBeanFactory = new
        VvAccountSysBeanFactory(dbManager);
    ShoppingCartItemTypeViewBeanFactory shoppingCartItemTypeViewBeanFactory = new
        ShoppingCartItemTypeViewBeanFactory(dbManager);
    RequestLineItemBeanFactory requestLineItemBeanFactory = new
        RequestLineItemBeanFactory(dbManager);
    PurchaseRequestBeanFactory purchaseRequestBeanFactory = new
        PurchaseRequestBeanFactory(dbManager);
    DoManifestBeanFactory doManifestBeanFactory = new DoManifestBeanFactory(
        dbManager);
    Iterator iterator = itemCollection.iterator();
    while (iterator.hasNext()) {
      ItemBean itemBean = (ItemBean) iterator.next();
      //validate unit price and quantity
      try {
        BigDecimal up = new BigDecimal(itemBean.getUnitPrice());
      }
      catch (Exception e) {
        log.error("Invalid unit price:" + itemBean.getUnitPrice());
        MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid unit price",
                              "Invalid unit price:" + itemBean.getUnitPrice() +
                              " for line item " +
                              itemBean.getSupplierPartAuxiliaryId());
        continue;
      }
      try {
        BigDecimal qty = new BigDecimal(itemBean.getQuantity());
      }
      catch (Exception e) {
        log.error("Invalid quantity:" + itemBean.getQuantity());
        MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL, "Invalid quantity",
                              "Invalid quantity:" + itemBean.getQuantity() +
                              " for line item " +
                              itemBean.getSupplierPartAuxiliaryId());
        continue;
      }
      //validate classification
      if (itemBean.getClassificationDomain().toLowerCase().indexOf("spsc") < 0) {
        log.error("Invalid classification: " + itemBean.getClassificationDomain() +
                  " for line item " + itemBean.getSupplierPartAuxiliaryId());
        throw new BaseException("Invalid classification:" +
                                itemBean.getClassificationDomain());
      }
      SearchCriteria accountSysCriteria = new SearchCriteria("accountSysDesc",
          SearchCriterion.EQUALS, itemBean.getClassification());

      Iterator accountSysIterator = vvAccountSysBeanFactory.select(
          accountSysCriteria).iterator();
      while (accountSysIterator.hasNext()) {
        vvAccountSysBean = (VvAccountSysBean) accountSysIterator.next();
      }
      //get item type
      SearchCriteria itemTypeCriteria = new SearchCriteria("prNumber",
          SearchCriterion.EQUALS,
          itemBean.getSupplierPartAuxiliaryId().
          substring(0,
                    itemBean.getSupplierPartAuxiliaryId().
                    indexOf("-")));
      itemTypeCriteria.addCriterion("lineItem", SearchCriterion.EQUALS,
                                    itemBean.getSupplierPartAuxiliaryId().
                                    substring(itemBean.
                                              getSupplierPartAuxiliaryId().
                                              indexOf("-") + 1));
      Iterator itemTypeIterator = shoppingCartItemTypeViewBeanFactory.select(
          itemTypeCriteria).iterator();
      while (itemTypeIterator.hasNext()) {
        shoppingCartItemTypeViewBean = (ShoppingCartItemTypeViewBean)
            itemTypeIterator.next();
        if (!purchaseReqs.containsKey(itemBean.getSupplierPartAuxiliaryId().
                                      substring(0,
                                                itemBean.
                                                getSupplierPartAuxiliaryId().
                                                indexOf("-")).intern())) {
          status = shoppingCartItemTypeViewBean.getPrStatus();
        }
        else {
          status = (String) purchaseReqs.get(itemBean.
                                             getSupplierPartAuxiliaryId().
                                             substring(0,
              itemBean.getSupplierPartAuxiliaryId().indexOf("-")));
        }
      }
      try {
    	  String taxExempt="Y";
    	  String taxAbleValue = (String) itemBean.getAdditionalInfo().get("Taxable");
    	  if( "true".equalsIgnoreCase(taxAbleValue))
    	  	taxExempt = "N";
        requestLineItemBeanFactory.updateDoNumberAndTaxStatus(bean.getOrderId(),
                                                  new
                                                  BigDecimal(itemBean.
            getSupplierPartAuxiliaryId().
            substring(0, itemBean.getSupplierPartAuxiliaryId().indexOf("-"))),
                                                  new
                                                  BigDecimal(itemBean.
            getSupplierPartAuxiliaryId().
            substring(itemBean.getSupplierPartAuxiliaryId().indexOf("-") + 1)),
            taxExempt);
        if (!purchaseReqs.containsKey(itemBean.getSupplierPartAuxiliaryId().
                                      substring(0,
                                                itemBean.
                                                getSupplierPartAuxiliaryId().
                                                indexOf("-")).intern())) {
          purchaseReqs.put(itemBean.getSupplierPartAuxiliaryId().substring(0,
              itemBean.getSupplierPartAuxiliaryId().indexOf("-")).intern(),
                           status);
          //there won't be any cc info for ERS orders
          if (bean.getToIdentity() != null &&
              !bean.getToIdentity().endsWith("ERS")) {
            purchaseRequestBeanFactory.updateCreditCard(new BigDecimal(itemBean.
                getSupplierPartAuxiliaryId().substring(0,
                itemBean.getSupplierPartAuxiliaryId().indexOf("-"))),
                bean.getPaymentCardName(),
                new BigDecimal(bean.getPaymentCardNumber()),
                DateHandler.getDateFromIso8601String(bean.
                getPaymentCardExpiration()));
          }
        }
      }
      catch (Exception e) {
        log.error("Order request processing error:" + e.getMessage());
        continue;
      }
      if (log.isDebugEnabled()) {
        log.debug(
            "MR Number and line number is: "
            +
            itemBean.getSupplierPartAuxiliaryId().substring(0,
            itemBean.getSupplierPartAuxiliaryId().indexOf("-"))
            + "/"
            +
            itemBean.getSupplierPartAuxiliaryId().substring(itemBean.
            getSupplierPartAuxiliaryId().
            indexOf("-") + 1)
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
        if (vvAccountSysBean.getAccountSysId() != null &&
            !"posubmit".equalsIgnoreCase(status)) {
          if (shoppingCartItemTypeViewBean.getCancelStatus() == null ||
              !"canceled".equals(shoppingCartItemTypeViewBean.getCancelStatus())) {
            try {
              if (log.isDebugEnabled()) {
                log.debug("SETTING POSUBMIT");
              }

              purchaseRequestBeanFactory.updatePrStatus(new BigDecimal(itemBean.
                  getSupplierPartAuxiliaryId().
                  substring(0,
                            itemBean.getSupplierPartAuxiliaryId().
                            indexOf("-"))), "posubmit", new Date());

              requestLineItemBeanFactory.updateStatus("In Progress", "Open",
                  new Date(), new
                  BigDecimal(itemBean.
                             getSupplierPartAuxiliaryId().
                             substring(0,
                                       itemBean.getSupplierPartAuxiliaryId().
                                       indexOf("-"))),
                  new
                  BigDecimal(itemBean.
                             getSupplierPartAuxiliaryId().
                             substring(itemBean.getSupplierPartAuxiliaryId().
                                       indexOf("-") + 1)));

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
              procParams.add(new BigDecimal(itemBean.getSupplierPartAuxiliaryId().
                                            substring(0,
                  itemBean.getSupplierPartAuxiliaryId().indexOf("-"))));
              procParams.add(new BigDecimal(itemBean.getSupplierPartAuxiliaryId().
                                            substring(itemBean.
                  getSupplierPartAuxiliaryId().indexOf("-") + 1)));
              GenericProcedureFactory genericProcedureFactory = new
                  GenericProcedureFactory(dbManager);
              genericProcedureFactory.doProcedure("p_line_item_allocate",
                                                  procParams);
            }
            catch (Exception e) {
              log.error(
                  "Order request processing error: Unable to allocate inventory for MR"
                  + itemBean.getSupplierPartAuxiliaryId(),
                  e);
              MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,
                                    "Unable to allocate inventory",
                                    "Unable to allocate inventory for " +
                                    itemBean.getSupplierPartAuxiliaryId());
              continue;
            }
          }
        }
        
      }
      //Chucks code had a piece that inserted values into account_entry that I'm skipping

      DoManifestBean doManifestBean = new DoManifestBean();
      doManifestBean.setDoNumber(bean.getOrderId());
      doManifestBean.setPrNumber(new BigDecimal(itemBean.
                                                getSupplierPartAuxiliaryId().
                                                substring(0,
          itemBean.getSupplierPartAuxiliaryId().
          indexOf("-"))));
      doManifestBean.setLineItem(itemBean.
                                 getSupplierPartAuxiliaryId().
                                 substring(itemBean.getSupplierPartAuxiliaryId().
                                           indexOf("-") + 1));
      doManifestBean.setQuantity(new BigDecimal(itemBean.getQuantity()));
      doManifestBean.setUnitPrice(new BigDecimal(itemBean.getUnitPrice()));
      // Larry: for taxable better here???
//      doManifestBean.setTaxable( (String) bean.getAdditionalInfo().get(
//              "Taxable"));
      
      doManifestBeanFactory.insert(doManifestBean);

    }
    OrderRequestResponseBean responseBean = new OrderRequestResponseBean();
    responseBean.setPayloadId(CxmlHandler.getPayloadId());
    responseBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
    responseBean.setStatusCode("200");
    responseBean.setStatusText("Success");
    OrderRequestResponseParser parser = new OrderRequestResponseParser(
        getClient());
    return parser.getResponse(responseBean);
  }

  public String getPunchoutOrderMessage(String payloadId) throws BaseException {
    DbManager dbManager = new DbManager(getClient());

    CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new
        CxmlDocumentDataBeanFactory(dbManager);
    Collection cxmlDocumentDataBeanColl = cxmlDocumentDataBeanFactory.select(new
        SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId));
    Iterator cxmlDocumentIterator = cxmlDocumentDataBeanColl.iterator();
    CxmlDocumentDataBean cxmlDocumentDataBean = null;
    while (cxmlDocumentIterator.hasNext()) {
      cxmlDocumentDataBean = (CxmlDocumentDataBean) cxmlDocumentIterator.next();
      cxmlDocumentDataBean.setPayloadId(CxmlHandler.getPayloadId());
      cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
    }

    PunchOutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new
        PunchOutSetupRequestBeanFactory(dbManager);
    Collection punchoutSetupRequestBeanColl = punchoutSetupRequestBeanFactory.
        select(new SearchCriteria("payloadId",
                                  SearchCriterion.EQUALS,
                                  payloadId));
    Iterator punchoutSetupRequestIterator = punchoutSetupRequestBeanColl.
        iterator();
    PunchOutSetupRequestBean punchoutSetupRequestBean = null;
    while (punchoutSetupRequestIterator.hasNext()) {
      punchoutSetupRequestBean = (PunchOutSetupRequestBean)
          punchoutSetupRequestIterator.next();
    }

    //get pr_number
    RequestLineItemBeanFactory requestLineItemBeanFactory = new
        RequestLineItemBeanFactory(dbManager);
    Collection requestLineItemBeanColl = requestLineItemBeanFactory.select(new
        SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId));
    Iterator rliIterator = requestLineItemBeanColl.iterator();
    RequestLineItemBean requestLineItemBean = null;
    while (rliIterator.hasNext()) {
      requestLineItemBean = (RequestLineItemBean) rliIterator.next();
    }
    //get line items
    ShoppingCartViewBeanFactory shoppingCartViewBeanFactory = new
        ShoppingCartViewBeanFactory(dbManager);
    SearchCriteria lineItemCriteria = new SearchCriteria("payloadId",
        SearchCriterion.EQUALS, payloadId);
//    lineItemCriteria.addCriterion("prNumber", SearchCriterion.EQUALS,requestLineItemBean.getPrNumber().toString());
    Collection<ShoppingCartViewBean> shoppingCartViewBeanColl = shoppingCartViewBeanFactory.select(
        lineItemCriteria);
    Iterator shoppingCartIterator = shoppingCartViewBeanColl.iterator();
    //not sure why this is done but Chucks code does it....
    for(ShoppingCartViewBean shoppingCartViewBean:shoppingCartViewBeanColl) {
      if (shoppingCartViewBean.getMaterialDesc() != null &&
          shoppingCartViewBean.getMaterialDesc().length() > 100) {
        shoppingCartViewBean.setMaterialDesc(shoppingCartViewBean.
                                             getMaterialDesc().substring(0, 100));
      }

      if (shoppingCartViewBean.getPackaging() != null &&
          shoppingCartViewBean.getPackaging().length() > 100) {
        shoppingCartViewBean.setPackaging(shoppingCartViewBean.getPackaging().
                                          substring(0, 100));
      }
      // do not allow edits of Production or Service Fee orders
      if ("Production".equalsIgnoreCase(shoppingCartViewBean.getAccountSysId()) ||
          "SF".equalsIgnoreCase(shoppingCartViewBean.getItemType()) ||
          "TC".equalsIgnoreCase(shoppingCartViewBean.getItemType()) ||
          "BG".equalsIgnoreCase(shoppingCartViewBean.getItemType())) {
        //set operationAllowed to "create"
      }
      // if this is a service fee set the UN/SPSC code appropriately
      if ("SF".equalsIgnoreCase(shoppingCartViewBean.getItemType())) {
        if ("SHAK".equals(shoppingCartViewBean.getUseFacilityId())) {
          shoppingCartViewBean.setAccountSysDesc("12000020");
        }
        else {
          shoppingCartViewBean.setAccountSysDesc("12000040");
        }
      }
      // if this is a tank lease set the UN/SPSC code appropriately
      if ("TC".equalsIgnoreCase(shoppingCartViewBean.getItemType())) {
        if ("SHAK".equals(shoppingCartViewBean.getUseFacilityId())) {
          shoppingCartViewBean.setAccountSysDesc("12000020");
        }
        else {
          shoppingCartViewBean.setAccountSysDesc("12000030");
        }
      }
      // if this is a tank lease set the UN/SPSC code appropriately
      if ("BG".equalsIgnoreCase(shoppingCartViewBean.getItemType()) &&
          "SHAK".equals(shoppingCartViewBean.getUseFacilityId())) {
        shoppingCartViewBean.setAccountSysDesc("12000020");
      }
    }

    //save-cxml-document-data
    cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
    //save-oracle-punchout-order-message
    CxmlPunchOutOrderMessageBeanFactory punchoutOrderMessageBeanFactory = new
        CxmlPunchOutOrderMessageBeanFactory(dbManager);
    CxmlPunchOutOrderMessageBean punchoutOrderMessageBean = new
        CxmlPunchOutOrderMessageBean();
    punchoutOrderMessageBean.setBrowserFormPost(punchoutSetupRequestBean.
                                                getBrowserFormPost());
    punchoutOrderMessageBean.setBuyerCookie(punchoutSetupRequestBean.
                                            getBuyerCookie());
    punchoutOrderMessageBean.setPayloadId(payloadId);
    punchoutOrderMessageBean.setPostedToOracle("Y");
    punchoutOrderMessageBean.setPrNumber(requestLineItemBean.getPrNumber());
    punchoutOrderMessageBean.setPunchout("Y");
    punchoutOrderMessageBean.setResponsePayloadId(cxmlDocumentDataBean.
                                                  getResponsePayloadId());
    punchoutOrderMessageBeanFactory.update(punchoutOrderMessageBean);
    //save-session-id
    PunchoutSessionBeanFactory punchoutSessionBeanFactory = new
        PunchoutSessionBeanFactory(dbManager);

    CxmlShoppingCartParser parser = new CxmlShoppingCartParser(getClient());
    String returnString = parser.getAerojetCxml(cxmlDocumentDataBean,
                                         punchoutSetupRequestBean,
                                         shoppingCartViewBeanColl);
    log.debug("Original Xml:"+returnString);
    //since we're sending this back in a html form we need to do this
    returnString = returnString.replaceAll("&", "&amp;");
    returnString = returnString.replaceAll("\"", "&quot;");
    return returnString;
  }
// Calling this function means no Draft MR created.  
  public String getPunchoutOrderXML(String payloadId,Collection<ShoppingCartBean> beans) throws BaseException {
	  
	    DbManager dbManager = new DbManager(getClient());

	    CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new
	        CxmlDocumentDataBeanFactory(dbManager);
	    Collection cxmlDocumentDataBeanColl = cxmlDocumentDataBeanFactory.select(new
	        SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId));
	    Iterator cxmlDocumentIterator = cxmlDocumentDataBeanColl.iterator();
	    CxmlDocumentDataBean cxmlDocumentDataBean = null;
	    while (cxmlDocumentIterator.hasNext()) {
	      cxmlDocumentDataBean = (CxmlDocumentDataBean) cxmlDocumentIterator.next();
	      cxmlDocumentDataBean.setPayloadId(CxmlHandler.getPayloadId());
	      cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
	    }

	    PunchOutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new
	        PunchOutSetupRequestBeanFactory(dbManager);
	    Collection punchoutSetupRequestBeanColl = punchoutSetupRequestBeanFactory.
	        select(new SearchCriteria("payloadId",
	                                  SearchCriterion.EQUALS,
	                                  payloadId));
	    Iterator punchoutSetupRequestIterator = punchoutSetupRequestBeanColl.
	        iterator();
	    PunchOutSetupRequestBean punchoutSetupRequestBean = null;
	    while (punchoutSetupRequestIterator.hasNext()) {
	      punchoutSetupRequestBean = (PunchOutSetupRequestBean)
	          punchoutSetupRequestIterator.next();
	    }

	    //get pr_number
	    // ... deleted code in another getPunchoutOrderXML
	    //save-cxml-document-data
	    cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
	    //save-oracle-punchout-order-message
	    CxmlPunchOutOrderMessageBeanFactory punchoutOrderMessageBeanFactory = new
	        CxmlPunchOutOrderMessageBeanFactory(dbManager);
	    CxmlPunchOutOrderMessageBean punchoutOrderMessageBean = new
	        CxmlPunchOutOrderMessageBean();
	    punchoutOrderMessageBean.setBrowserFormPost(punchoutSetupRequestBean.
	                                                getBrowserFormPost());
	    punchoutOrderMessageBean.setBuyerCookie(punchoutSetupRequestBean.
	                                            getBuyerCookie());
	    punchoutOrderMessageBean.setPayloadId(payloadId);
	    punchoutOrderMessageBean.setPostedToOracle("Y");
// No MR.	    
	    punchoutOrderMessageBean.setPrNumber(new BigDecimal("-1"));
	    punchoutOrderMessageBean.setPunchout("Y");
	    punchoutOrderMessageBean.setResponsePayloadId(cxmlDocumentDataBean.
	                                                  getResponsePayloadId());
	    punchoutOrderMessageBeanFactory.update(punchoutOrderMessageBean);
	    //save-session-id
	    PunchoutSessionBeanFactory punchoutSessionBeanFactory = new
	        PunchoutSessionBeanFactory(dbManager);
	    return getShoppingCartCxmlWithGrid(
	    		cxmlDocumentDataBean,
				punchoutSetupRequestBean,
				beans);
  }

// This function in the same as cxml except the last getting shopping cart xml part  
  public String getPunchoutOrderXML(String payloadId) throws BaseException {
	    DbManager dbManager = new DbManager(getClient());

	    CxmlDocumentDataBeanFactory cxmlDocumentDataBeanFactory = new
	        CxmlDocumentDataBeanFactory(dbManager);
	    Collection cxmlDocumentDataBeanColl = cxmlDocumentDataBeanFactory.select(new
	        SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId));
	    Iterator cxmlDocumentIterator = cxmlDocumentDataBeanColl.iterator();
	    CxmlDocumentDataBean cxmlDocumentDataBean = null;
	    while (cxmlDocumentIterator.hasNext()) {
	      cxmlDocumentDataBean = (CxmlDocumentDataBean) cxmlDocumentIterator.next();
	      cxmlDocumentDataBean.setPayloadId(CxmlHandler.getPayloadId());
	      cxmlDocumentDataBean.setTimestamp(CxmlHandler.getTimeStamp(new Date()));
	    }

	    PunchOutSetupRequestBeanFactory punchoutSetupRequestBeanFactory = new
	        PunchOutSetupRequestBeanFactory(dbManager);
	    Collection punchoutSetupRequestBeanColl = punchoutSetupRequestBeanFactory.
	        select(new SearchCriteria("payloadId",
	                                  SearchCriterion.EQUALS,
	                                  payloadId));
	    Iterator punchoutSetupRequestIterator = punchoutSetupRequestBeanColl.
	        iterator();
	    PunchOutSetupRequestBean punchoutSetupRequestBean = null;
	    while (punchoutSetupRequestIterator.hasNext()) {
	      punchoutSetupRequestBean = (PunchOutSetupRequestBean)
	          punchoutSetupRequestIterator.next();
	    }

	    //get pr_number
	    RequestLineItemBeanFactory requestLineItemBeanFactory = new
	        RequestLineItemBeanFactory(dbManager);
	    Collection requestLineItemBeanColl = requestLineItemBeanFactory.select(new
	        SearchCriteria("payloadId", SearchCriterion.EQUALS, payloadId));
	    Iterator rliIterator = requestLineItemBeanColl.iterator();
	    RequestLineItemBean requestLineItemBean = null;
	    while (rliIterator.hasNext()) {
	      requestLineItemBean = (RequestLineItemBean) rliIterator.next();
	    }
	    //get line items
	    ShoppingCartViewBeanFactory shoppingCartViewBeanFactory = new
	        ShoppingCartViewBeanFactory(dbManager);
	    SearchCriteria lineItemCriteria = new SearchCriteria("payloadId",
	        SearchCriterion.EQUALS, payloadId);
//	    lineItemCriteria.addCriterion("prNumber", SearchCriterion.EQUALS,requestLineItemBean.getPrNumber().toString());
	    Collection<ShoppingCartViewBean> shoppingCartViewBeanColl = shoppingCartViewBeanFactory.select(
	        lineItemCriteria);
	    Iterator shoppingCartIterator = shoppingCartViewBeanColl.iterator();
	    //not sure why this is done but Chucks code does it....
	    for(ShoppingCartViewBean shoppingCartViewBean:shoppingCartViewBeanColl) {
	      if (shoppingCartViewBean.getMaterialDesc() != null &&
	          shoppingCartViewBean.getMaterialDesc().length() > 100) {
	        shoppingCartViewBean.setMaterialDesc(shoppingCartViewBean.
	                                             getMaterialDesc().substring(0, 100));
	      }

	      if (shoppingCartViewBean.getPackaging() != null &&
	          shoppingCartViewBean.getPackaging().length() > 100) {
	        shoppingCartViewBean.setPackaging(shoppingCartViewBean.getPackaging().
	                                          substring(0, 100));
	      }
	      // do not allow edits of Production or Service Fee orders
	      if ("Production".equalsIgnoreCase(shoppingCartViewBean.getAccountSysId()) ||
	          "SF".equalsIgnoreCase(shoppingCartViewBean.getItemType()) ||
	          "TC".equalsIgnoreCase(shoppingCartViewBean.getItemType()) ||
	          "BG".equalsIgnoreCase(shoppingCartViewBean.getItemType())) {
	        //set operationAllowed to "create"
	      }
	      // if this is a service fee set the UN/SPSC code appropriately
	      if ("SF".equalsIgnoreCase(shoppingCartViewBean.getItemType())) {
	        if ("SHAK".equals(shoppingCartViewBean.getUseFacilityId())) {
	          shoppingCartViewBean.setAccountSysDesc("12000020");
	        }
	        else {
	          shoppingCartViewBean.setAccountSysDesc("12000040");
	        }
	      }
	      // if this is a tank lease set the UN/SPSC code appropriately
	      if ("TC".equalsIgnoreCase(shoppingCartViewBean.getItemType())) {
	        if ("SHAK".equals(shoppingCartViewBean.getUseFacilityId())) {
	          shoppingCartViewBean.setAccountSysDesc("12000020");
	        }
	        else {
	          shoppingCartViewBean.setAccountSysDesc("12000030");
	        }
	      }
	      // if this is a tank lease set the UN/SPSC code appropriately
	      if ("BG".equalsIgnoreCase(shoppingCartViewBean.getItemType()) &&
	          "SHAK".equals(shoppingCartViewBean.getUseFacilityId())) {
	        shoppingCartViewBean.setAccountSysDesc("12000020");
	      }
	    }

	    //save-cxml-document-data
	    cxmlDocumentDataBeanFactory.insert(cxmlDocumentDataBean);
	    //save-oracle-punchout-order-message
	    CxmlPunchOutOrderMessageBeanFactory punchoutOrderMessageBeanFactory = new
	        CxmlPunchOutOrderMessageBeanFactory(dbManager);
	    CxmlPunchOutOrderMessageBean punchoutOrderMessageBean = new
	        CxmlPunchOutOrderMessageBean();
	    punchoutOrderMessageBean.setBrowserFormPost(punchoutSetupRequestBean.
	                                                getBrowserFormPost());
	    punchoutOrderMessageBean.setBuyerCookie(punchoutSetupRequestBean.
	                                            getBuyerCookie());
	    punchoutOrderMessageBean.setPayloadId(payloadId);
	    punchoutOrderMessageBean.setPostedToOracle("Y");
	    punchoutOrderMessageBean.setPrNumber(requestLineItemBean.getPrNumber());
	    punchoutOrderMessageBean.setPunchout("Y");
	    punchoutOrderMessageBean.setResponsePayloadId(cxmlDocumentDataBean.
	                                                  getResponsePayloadId());
	    punchoutOrderMessageBeanFactory.update(punchoutOrderMessageBean);
	    //save-session-id
	    PunchoutSessionBeanFactory punchoutSessionBeanFactory = new
	        PunchoutSessionBeanFactory(dbManager);
	    return getShoppingCartCxml(
	    		cxmlDocumentDataBean,
				punchoutSetupRequestBean,
				shoppingCartViewBeanColl);
    }
  
  	private String getShoppingCartCxml( CxmlDocumentDataBean cxmlDocumentDataBean,
  										PunchOutSetupRequestBean punchoutSetupRequestBean,
  										Collection<ShoppingCartViewBean> shoppingCartViewBeanColl) throws BaseException {
	    IProcuShoppingCartParser parser = new IProcuShoppingCartParser(getClient());
	    String returnString = parser.getAerojetXml(cxmlDocumentDataBean,
	                                         punchoutSetupRequestBean,
	                                         shoppingCartViewBeanColl);
	    log.debug("Original Xml:"+returnString);
	    //since we're sending this back in a html form we need to do this
	    return returnString;
	  }

  	private String getShoppingCartCxmlWithGrid( CxmlDocumentDataBean cxmlDocumentDataBean,
  			PunchOutSetupRequestBean punchoutSetupRequestBean,
  			Collection<ShoppingCartBean> beans) throws BaseException {
  		//Collection<ShoppingCartViewBean> shoppingCartViewBeanColl;
  		IProcuShoppingCartParser parser = new IProcuShoppingCartParser(getClient());
  		String returnString = parser.getAerojetXmlWithGrid(cxmlDocumentDataBean,
  				punchoutSetupRequestBean,
  				beans);
  		log.debug("Original Xml:"+returnString);
  		//since we're sending this back in a html form we need to do this
  		return returnString;
  	}
  public String getPersonnelIdFromCxmlId(String user)  {
	  String personnelId = "";
	  try {
	  	personnelId = factory.selectSingleValue(
	  			"select personnel_id  from company_application_logon where COMPANY_APPLICATION_LOGON_ID = "+ GenericProcess.getSqlString(user));
	  } catch(Exception ex) { ex.printStackTrace() ; }
	  return personnelId;
  }
  public String getLoginIdfromPersonnelId(String personnelId)  {
	  String logonId = "";
	  try {
		  logonId = factory.selectSingleValue(
	  			"select logon_id  from personnel where Personnel_ID = "+ GenericProcess.getSqlString(personnelId));
	  } catch(Exception ex) { ex.printStackTrace() ; }
	  return logonId;
  }
  public String getLoginToken()  {
	  String token = "";
	  try {
		  token = factory.getFunctionValue("sys_guid");
	  } catch(Exception ex) { ex.printStackTrace() ; }
	  return token;
  }
  public String setLoginSessionInfo(String payloadId,String buyerCockie, String browserFormPost, String responsePayloadId )  {
	  String logonId = "";
	  try {
	  String query= "insert into punchout_order_message(PAYLOAD_ID,BUYER_COOKIE,BROWSER_FORM_POST,RESPONSE_PAYLOAD_ID) values(" +
			  GenericProcess.getSqlString(payloadId) + "," +
			  GenericProcess.getSqlString(buyerCockie) + "," +
			  GenericProcess.getSqlString(browserFormPost) + "," +
			  GenericProcess.getSqlString(responsePayloadId) + ")";
		  	  factory.deleteInsertUpdate(query);
	  } catch(Exception ex) { ex.printStackTrace() ; }
	  return logonId;
  }

  public String getBrowserPostFromPayloadId(String payloadId)  {
	  String brwoserPost = "";
	  try {
		  brwoserPost = factory.selectSingleValue(
	  			"select browser_form_post from punchout_setup_request where payload_id = '" + payloadId + "'");
	  } catch(Exception ex) { ex.printStackTrace() ; }
	  return brwoserPost;
  }
 
}