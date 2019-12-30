package com.tcmis.client.raytheon.process;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.client.order.beans.CustomerPo855ViewBean;
import com.tcmis.client.order.beans.CustomerPoPreStageBean;
import com.tcmis.client.raytheon.beans.XcblOrderBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailScheduleBean;
import com.tcmis.client.raytheon.net.HostnameVerifier;
import com.tcmis.client.raytheon.util.XcblHandler;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Process to process raytheon xcbl documents.
 * @version 1.0
 *****************************************************************************/
public class XcblProcess
    extends BaseProcess {

  public final static String ACCEPTED = "Accepted";
  public final static String ACCEPTED_WITH_CHANGES = "AcceptedWithChanges";
  public final static String ACCEPTED_WITH_AMENDMENT = "AcceptedWithAmendment";
  public final static String NOT_ACCEPTED = "NotAccepted";
  public final static String REJECTED = "NotAccepted";
  public final static String ITEM_ACCEPTED = "ItemAccepted";
  public final static String ITEM_NOT_ACCEPTED = "ItemNotAcceptedByTheSeller";
  //public final static String ITEM_REJECTED = "Rejected";
  public final static String PENDING = "Pending";
  public final static String PROCESSING = "Processing";
  public final static String ACKNOWLEDGED_WITH_EXCEPTIONS =
      "Acknowledged With Exceptions";
  public final static String OTHER = "Other";

  private String backupDir;
  private String exostarUserName;
  private String exostarPassword;
  private String exostarUrl;
  private HostnameVerifier hostnameVerifier;
  private XcblOrderProcess xcblOrderProcess;
  private XcblChangeOrderProcess xcblChangeOrderProcess;
  private Collection responses;
  private String toEmail = "deverror@haastcm.com";
  private String fromEmail = "raytheon_xcbl@haastcm.com";
  //this is ugly but will be fine for now
  private static String client;

  public XcblProcess(String client) {
    super(client);
    this.client = client;
    ResourceLibrary resource = new ResourceLibrary("raytheon");
    backupDir = resource.getString("xcbl.backup.dir");
    exostarUserName = resource.getString("exostar.username");
    exostarPassword = resource.getString("exostar.password");
    exostarUrl = resource.getString("exostar.url");
    xcblOrderProcess = new XcblOrderProcess(getClient());
    xcblChangeOrderProcess = new XcblChangeOrderProcess(getClient());
    hostnameVerifier = new HostnameVerifier();
  }

  /**********************************************************************
   * Checks the status of orders in Customer_Po_855_View and replies with
   * appropriate .
   **********************************************************************/
  public void checkStatus() throws BaseException {
    xcblOrderProcess.checkStatus();
    xcblChangeOrderProcess.checkStatus();
  }

  /*********************************************************************
   * Save the incoming document to the file system and then pass it to either
   * XcblOrderProcess or XcblChangeOrderProcess.
   *********************************************************************/
  public void processDocument(String xcblDocument) throws BaseException {
    if (log.isDebugEnabled()) {
      log.debug("processing document:" + xcblDocument);
    }
    //System.out.println("processing document" + xcblDocument);
    String xcblResponse = null;
    boolean validOrder = true;
    XcblOrderBean xcblOrderBean = null;
    File xcblFile;
    //fake it...
    if (xcblDocument == null) {
      xcblDocument = "";
    }
    //save request document
    try {
      xcblFile = XcblHandler.saveDocument(xcblDocument, backupDir);
    }
    catch (IOException ioe) {
      log.error("Error saving request:" + xcblDocument, ioe);
      BaseException be = new BaseException("Error saving request");
      be.setRootCause(ioe);
      throw be;
    }
    //determine what type of document is coming in a call appropriate method
    if (xcblDocument != null && xcblDocument.indexOf("<Order>") > -1) {
      xcblResponse = xcblOrderProcess.processDocument(xcblFile);
    }
    else if (xcblDocument != null && xcblDocument.indexOf("<ChangeOrder>") > -1) {
      //process change order
      xcblResponse = xcblChangeOrderProcess.processDocument(xcblFile);
    }
    else {
      //System.out.println("invalid document");
      //invalid order
      log.error("Invalid Raytheon PO:" + xcblDocument);
      MailProcess.sendEmail(toEmail,
                            "",
                            fromEmail,
                            "Invalid Raytheon xcbl order",
                            xcblDocument);
      validOrder = false;
    }
    //save response document
    try {
      XcblHandler.saveDocument(xcblResponse, backupDir);
    }
    catch (IOException ioe) {
      log.error("Error saving response:" + xcblResponse, ioe);
    }
    if (validOrder) {
      //the document comes in thru tomcat so I'll have to use the old impl response
      try {
        NetHandler.sendOldImplHttpsPost(exostarUrl,
                                        exostarUserName,
                                        exostarPassword,
                                        xcblResponse,
                                        hostnameVerifier);
      }
      catch (Exception e) {
        //e.printStackTrace(System.out);
        //System.out.println("Error sending response:" + e.getMessage());
        log.error("Error sending response", e);
        MailProcess.sendEmail(toEmail,
                              "",
                              fromEmail,
                              "Error sending response",
                              "Can't send response to exostar, check the logs." +
                              e.getMessage());
      }
    }
  }

  /***************************************************************************
       * Converts a collection of CustomerPo855ViewBean to a collection of XcblChangeOrderBean
   *************************************************************************/
  public static Collection getXcblOrderBeanCollection(Collection
      customerPo855ViewBeanColl) throws
      BaseException {
//System.out.println("SIZE:" + customerPo855ViewBeanColl.size());
    Collection xcblOrderBeanColl = new Vector();
    XcblOrderBean xcblOrderBean = new XcblOrderBean();
    XcblOrderDetailBean xcblOrderDetailBean = new XcblOrderDetailBean();
    XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean = new
        XcblOrderDetailScheduleBean();
    //I'll need something to see if it's a new PO or a line on previous PO
    String poNumber = "";
    String poLineNumber = "";
    String orderStatus = "";
    Iterator iterator = customerPo855ViewBeanColl.iterator();
    while (iterator.hasNext()) {
      CustomerPo855ViewBean customerPo855ViewBean = (CustomerPo855ViewBean) iterator.next();
      if (!poNumber.equalsIgnoreCase(customerPo855ViewBean.getCustomerPoNo())) {
        //new PO
        //if bean is not empty it's not the first time thru the loop
        if (poNumber.trim().length() > 0) {
          //clone bean and add to collection
          try {
            XcblOrderBean bean = (XcblOrderBean) xcblOrderBean.clone();
            xcblOrderBeanColl.add(bean);
          }
          catch (Exception e) {
            BaseException be = new BaseException("Error cloning bean");
            be.setRootCause(e);
            throw be;
          }
          xcblOrderBean = new XcblOrderBean();
          xcblOrderDetailBean = new XcblOrderDetailBean();
          xcblOrderDetailScheduleBean = new XcblOrderDetailScheduleBean();
        }
        copyHeaderAttributes(customerPo855ViewBean, xcblOrderBean);
        copyDetailAttributes(customerPo855ViewBean, xcblOrderBean);
        copyDetailScheduleAttributes(customerPo855ViewBean, xcblOrderBean);
      }
      else {
        if (!poLineNumber.equalsIgnoreCase(customerPo855ViewBean.getCustomerPoLineNo())) {
          //new line on PO - add detail bean
          copyDetailAttributes(customerPo855ViewBean, xcblOrderBean);
          copyDetailScheduleAttributes(customerPo855ViewBean, xcblOrderBean);
        }
        else {
          //add schedule bean
          copyDetailScheduleAttributes(customerPo855ViewBean, xcblOrderBean);
        }
      }
      poNumber = customerPo855ViewBean.getCustomerPoNo();
      poLineNumber = customerPo855ViewBean.getCustomerPoLineNo();
    }
    //add last bean if it got a po number
    if (xcblOrderBean.getBuyerOrderNumber() != null) {
      xcblOrderBeanColl.add(xcblOrderBean);
    }
    return xcblOrderBeanColl;
  }

  /**************************************************************************
   * Copies header attributes from a CustomerPo855ViewBean to a XcblChangeOrderBean
   *************************************************************************/
  private static void copyHeaderAttributes(CustomerPo855ViewBean customerPo855ViewBean,
                                           XcblOrderBean xcblChangeOrderBean) {
    xcblChangeOrderBean.setBuyerOrderNumber(customerPo855ViewBean.getCustomerPoNo());
    xcblChangeOrderBean.setOrderIssueDate(customerPo855ViewBean.getDateIssued());
    xcblChangeOrderBean.setSellerIdent(customerPo855ViewBean.getCustomerHaasContractId());
    xcblChangeOrderBean.setSellerName1(customerPo855ViewBean.getCustomerHaasAccountNo());
    xcblChangeOrderBean.setOrderPartyIdent(customerPo855ViewBean.getTransporterAccount());
    xcblChangeOrderBean.setOrderPartyName1(customerPo855ViewBean.getBuyerNameOnPo());
    xcblChangeOrderBean.setOrderPartyContactName(customerPo855ViewBean.getBuyerNameOnPo());
    xcblChangeOrderBean.setResponseOrderStatus(getStatus(customerPo855ViewBean.
        getAcceptanceCode()));
    xcblChangeOrderBean.setResponseOrderStatusNotes(getStatusNotes(customerPo855ViewBean.
        getAcceptanceCode()));
    xcblChangeOrderBean.setStatusEvent(getStatus(customerPo855ViewBean.getAcceptanceCode()));
    xcblChangeOrderBean.setStatusEventOther(getStatusOther(customerPo855ViewBean.
        getAcceptanceCode()));
    xcblChangeOrderBean.setTransport(customerPo855ViewBean.getTransport());
    xcblChangeOrderBean.setTransporterAccount(customerPo855ViewBean.getTransporterAccount());
    xcblChangeOrderBean.setTradingPartner(customerPo855ViewBean.getTradingPartner());
    xcblChangeOrderBean.setTradingPartnerId(customerPo855ViewBean.getTradingPartnerId());
    //xcblChangeOrderBean.setTotalQuantity(customerPo855ViewBean.getOriginalSumQtyForAllLines());
    xcblChangeOrderBean.setTotalQuantity(customerPo855ViewBean.getSumQtyForAllLines());
    xcblChangeOrderBean.setChangeOrderTotalQuantity(customerPo855ViewBean.
        getSumQtyForAllLines());
    xcblChangeOrderBean.setChangeOrderSequence(customerPo855ViewBean.
                                               getChangeOrderSequence());
    xcblChangeOrderBean.setChangeOrderDate(customerPo855ViewBean.getDateIssued());
    xcblChangeOrderBean.setTransactionType(customerPo855ViewBean.getTransactionType());
  }

  /***************************************************************************
   * Copies detail attributes from a CustomerPo855ViewBean to a XcblOrderDetailBean
   * and adds it to a XcblChangeOrderBean
   *************************************************************************/
  private static void copyDetailAttributes(CustomerPo855ViewBean customerPo855ViewBean,
                                           XcblOrderBean xcblOrderBean) {
    xcblOrderBean.setResponseType(getHeaderResponseType(customerPo855ViewBean.
        getAcceptanceCode()));
    XcblOrderDetailBean xcblOrderDetailBean = new XcblOrderDetailBean();
    xcblOrderDetailBean.setResponseType(getDetailResponse(customerPo855ViewBean.
        getAcceptanceCode()));
    xcblOrderDetailBean.setResponseOrderStatus(getDetailResponse(customerPo855ViewBean.
        getAcceptanceCode()));
    xcblOrderDetailBean.setResponseOrderStatusNotes(getStatusNotes(customerPo855ViewBean.
        getAcceptanceCode()));
    //xcblOrderDetailBean.setStatusEvent(getDetailStatus(customerPo855ViewBean.getAcceptanceCode()));
    xcblOrderDetailBean.setStatusEvent(getResponseType(customerPo855ViewBean.
        getAcceptanceCode()));
    xcblOrderDetailBean.setBuyerLineItemNum(new BigDecimal(customerPo855ViewBean.
        getCustomerPoLineNo()));
    xcblOrderDetailBean.setSellerPartId(customerPo855ViewBean.getCatPartNo());
    xcblOrderDetailBean.setResponseUnitPrice(customerPo855ViewBean.getUnitPrice());
    xcblOrderDetailBean.setUnitPrice(customerPo855ViewBean.getUnitPrice());
    xcblOrderDetailBean.setQuantity(customerPo855ViewBean.getSumQtyForAllLines());
    xcblOrderDetailBean.setUom(customerPo855ViewBean.getUom());
    xcblOrderDetailBean.setResponseQuantity(customerPo855ViewBean.getSumQtyForAllLines());
//check for original values on order
    Iterator iterator = customerPo855ViewBean.getCustomerPoPreStageBeanCollection().
        iterator();
    while (iterator.hasNext()) {
      CustomerPoPreStageBean b = (CustomerPoPreStageBean) iterator.next();
      xcblOrderDetailBean.setQuantity(b.getQuantity());
      xcblOrderDetailBean.setUnitPrice(b.getUnitPrice());
    }
    xcblOrderBean.addXcblOrderDetailBean(xcblOrderDetailBean);

  }

  /**************************************************************************
       * Copies detail attributes from a CustomerPo855ViewBean to a XcblOrderDetailScheduleBean
   * and adds it to a XcblOrderDetailBean
   **************************************************************************/
  private static void copyDetailScheduleAttributes(CustomerPo855ViewBean
      customerPo855ViewBean,
      XcblOrderBean xcblOrderBean) {
    XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean = new
        XcblOrderDetailScheduleBean();
    xcblOrderDetailScheduleBean.setResponseQuantity(customerPo855ViewBean.getQuantity());
    xcblOrderDetailScheduleBean.setUom(customerPo855ViewBean.getUom());
    xcblOrderDetailScheduleBean.setResponseDeliveryDate(customerPo855ViewBean.
        getEstimatedDockDate());
    xcblOrderDetailScheduleBean.setScheduleLineId(customerPo855ViewBean.
                                                  getChangeOrderSequence());
    xcblOrderDetailScheduleBean.setQuantity(customerPo855ViewBean.getQuantity());
    xcblOrderDetailScheduleBean.setRequestedDeliveryDate(customerPo855ViewBean.
        getRequestedDeliveryDate());
    //add schedule bean to last detail bean
    Collection c = xcblOrderBean.getXcblOrderDetailBeanColl();
    Vector v = new Vector(c);
    XcblOrderDetailBean detailBean = (XcblOrderDetailBean) v.lastElement();
    detailBean.addXcblOrderDetailScheduleBean(xcblOrderDetailScheduleBean);
    Collection customerPoPreStageBeanColl = customerPo855ViewBean.
        getCustomerPoPreStageBeanCollection();
    Iterator it = customerPoPreStageBeanColl.iterator();
    Collection preVector = new Vector();
    while (it.hasNext()) {
      CustomerPoPreStageBean preBean = (CustomerPoPreStageBean) it.next();
//System.out.println("Setting issue date:" + preBean.getDateIssued());
      //set the original issue date
      xcblOrderBean.setOrderIssueDate(preBean.getOriginalDateIssued());
      //xcblOrderBean.setOrderIssueDateString(DateHandler.formatDate(preBean.getDateIssued(), "yyyyMMdd'T'HH:mm:ss"));
      XcblOrderDetailScheduleBean originalScheduleBean = new XcblOrderDetailScheduleBean();
      originalScheduleBean.setQuantity(preBean.getQuantity());
      originalScheduleBean.setRequestedDeliveryDate(preBean.getRequestedDeliveryDate());
      originalScheduleBean.setUom(preBean.getUom());
      preVector.add(originalScheduleBean);
    }
    //check if it's a change order and if so add the original schedule
    if (isOrderChanged(customerPo855ViewBean.getAcceptanceCode())) {
      detailBean.setXcblOrderDetailOriginalScheduleBeanColl(preVector);
    }
  }

  /*********************************************************************
   * Converts the code value from the customer_po_855_view to a Exostar value
   ********************************************************************/
  public static String getHeaderResponseType(String code) {
    if (isOrderAccepted(code)) {
      return ACCEPTED;
    }
    else if (isOrderChanged(code)) {
      return ACCEPTED_WITH_AMENDMENT;
    }
    return NOT_ACCEPTED;
  }

  /*********************************************************************
   * Converts the code value from the customer_po_855_view to a Exostar value
   *********************************************************************/
  public static String getResponseType(String code) {
    if (isOrderAccepted(code)) {
      return ACCEPTED;
    }
    else if (isOrderChanged(code)) {
      return ACCEPTED_WITH_CHANGES;
    }
    return NOT_ACCEPTED;
  }

  /*********************************************************************
   * Converts the code value from the customer_po_855_view to a Exostar value
   *********************************************************************/
  public static String getStatus(String code) {
    if (isOrderAccepted(code)) {
      return ACCEPTED;
    }
    else if (isOrderChanged(code)) {
      return OTHER;
    }
    return NOT_ACCEPTED;
  }

  /*********************************************************************
   * Converts the code value from the customer_po_855_view to a Exostar value
   *********************************************************************/
  public static String getStatusOther(String code) {
    if (isOrderChanged(code)) {
      return ACCEPTED_WITH_CHANGES;
    }
    return "";
  }

  /*********************************************************************
   * Converts the code value from the customer_po_855_view to a Exostar value
   *********************************************************************/
  public static String getDetailResponse(String code) {
    if (isOrderAccepted(code)) {
      return ITEM_ACCEPTED;
    }
    else if (isOrderChanged(code)) {
      return ACCEPTED_WITH_AMENDMENT;
    }
    return ITEM_NOT_ACCEPTED;
  }

  /*********************************************************************
   * Converts the code value from the customer_po_855_view to a Exostar value
   *********************************************************************/
  public static String getDetailStatus(String code) {
    if (isOrderAccepted(code)) {
      return ITEM_ACCEPTED;
    }
    else if (isOrderChanged(code)) {
      return ACCEPTED_WITH_CHANGES;
    }
    return ITEM_NOT_ACCEPTED;
  }

  /*********************************************************************
   * Returns a error note when the order is rejected.
   *********************************************************************/
  public static String getStatusNotes(String code) {
    if (isOrderAccepted(code)) {
      return "";
    }
    else if (isOrderChanged(code)) {
      return "Changes were made to quantity and/or price and/or delivery date";
    }
    return "Please make sure that the part number and ship to are valid.";
  }

  /*********************************************************************
   * Returns true if (order code is null) or (not "rejected" and not "changed"),
   * otherwise returns false.
   *********************************************************************/
  public static boolean isOrderAccepted(String code) {
    if (code == null ||
        (!code.equalsIgnoreCase("IR") && !code.equalsIgnoreCase("RI") &&
         !code.equalsIgnoreCase("IC"))) {
      return true;
    }
    return false;
  }

  /*********************************************************************
   * Returns true if order code is "changed", otherwise returns false.
   *********************************************************************/
  public static boolean isOrderChanged(String code) {
    if (code != null && code.equalsIgnoreCase("IC")) {
      return true;
    }
    return false;
  }

  public static boolean isValidTucsonOrder(XcblOrderBean bean) {
    //kanban POs starts with 75 and non-kanban POs starts with 42
    if (("42".equalsIgnoreCase(bean.getBuyerOrderNumber().substring(0, 2)) ||
        "75".equalsIgnoreCase(bean.getBuyerOrderNumber().substring(0, 2))) &&
       !"RAYTHEON.SAS.FOREST".equalsIgnoreCase(bean.getAccountCodeRefNum())) {
      return true;
    }
    return false;
  }
}