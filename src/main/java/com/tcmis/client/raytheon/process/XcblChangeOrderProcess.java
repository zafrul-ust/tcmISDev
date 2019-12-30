package com.tcmis.client.raytheon.process;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.client.order.beans.*;
import com.tcmis.client.order.factory.CustomerPo855ViewBeanFactory;
import com.tcmis.client.order.factory.CustomerPoPreStageBeanFactory;
import com.tcmis.client.raytheon.beans.XcblOrderBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailReferenceBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailScheduleBean;
import com.tcmis.client.raytheon.net.HostnameVerifier;
import com.tcmis.client.raytheon.util.XcblHandler;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process to process xcbl documents.
 * @version 1.0
 *****************************************************************************/
public class XcblChangeOrderProcess
    extends BaseProcess {

  private String backupDir;
  private String exostarUserName;
  private String exostarPassword;
  private String exostarUrl;
  private XcblChangeOrderResponseParser xcblChangeOrderResponseParser;
  private HostnameVerifier hostnameVerifier;
  private String toEmail = "deverror@haastcm.com";
  private String fromEmail = "raytheon_xcbl@haastcm.com";

  public XcblChangeOrderProcess(String client) {
    super(client);
    ResourceLibrary resource = new ResourceLibrary("raytheon");
    backupDir = resource.getString("xcbl.backup.dir");
    exostarUserName = resource.getString("exostar.username");
    exostarPassword = resource.getString("exostar.password");
    exostarUrl = resource.getString("exostar.url");
    xcblChangeOrderResponseParser = new XcblChangeOrderResponseParser(getClient());
    hostnameVerifier = new HostnameVerifier();
  }

  /*
   * Process incoming document
   */
  public String processDocument(File xcblFile) throws BaseException {
    String xcblResponse = null;
    boolean validOrder = true;
    XcblOrderBean xcblChangeOrderBean = null;
    //save order to stage table
    xcblChangeOrderBean = this.saveChangeOrder(xcblFile);
    xcblResponse = xcblChangeOrderResponseParser.getChangeOrderResponse(
        xcblChangeOrderBean);
    return xcblResponse;
  }

  /*********************************************************************
   * Queries the Exostar_response_view for any orders to respond to thru Exostar.
   * If a response is successfully sent the pkg_dbuy_from_customer.customer_po_update_dcn
   * procedure is called.
   *********************************************************************/

  public void checkStatus() throws BaseException {
    //query view and see if I got something to send to Exostar
    //if a PO is found send response and update view
    //send email if PO is rejected
    try {
      DbManager dbManager = new DbManager(getClient());
      CustomerPo855ViewBeanFactory factory = new CustomerPo855ViewBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("changeOrderSequence", SearchCriterion.GREATER_THAN, "0");
      Collection customerPo855ViewBeanColl = factory.getExostarOrders(criteria);
      //see if there any change orders, if so, get the original order data
      Iterator po855Iterator = customerPo855ViewBeanColl.iterator();
      while(po855Iterator.hasNext()) {
        CustomerPo855ViewBean customerPo855ViewBean = (CustomerPo855ViewBean)po855Iterator.next();
        //if(XcblProcess.isOrderChanged(customerPo855ViewBean.getAcceptanceCode())) {
          //get original order data
          CustomerPoPreStageBeanFactory preFactory = new CustomerPoPreStageBeanFactory(dbManager);
          SearchCriteria preCriteria = new SearchCriteria();
          preCriteria.addCriterion("loadId", SearchCriterion.EQUALS, customerPo855ViewBean.getFromLoadId().toString());
          //preCriteria.addCriterion("loadLine", SearchCriterion.EQUALS, customerPo855ViewBean.getFromLoadLine().toString());
          customerPo855ViewBean.setCustomerPoPreStageBeanCollection(preFactory.select(preCriteria));
        //}
      }
      Collection xcblChangeOrderBeanColl = XcblProcess.getXcblOrderBeanCollection(customerPo855ViewBeanColl);
      if (log.isDebugEnabled()) {
        log.debug("Sending " + xcblChangeOrderBeanColl.size() + " responses");
      }
      Iterator iterator = xcblChangeOrderBeanColl.iterator();
      while (iterator.hasNext()) {
        XcblOrderBean xcblOrderBean = (XcblOrderBean) iterator.next();
        String xcblResponse = xcblChangeOrderResponseParser.getChangeOrderResponse(
            xcblOrderBean);
//System.out.println("RESPONSE:" + xcblResponse);

        if(log.isDebugEnabled()) {
          log.debug("RESPONSE:" + xcblResponse);
        }
        try {
          XcblHandler.saveDocument(xcblResponse, backupDir);
        }
        catch (IOException ioe) {
          log.error("Error saving response:" + xcblResponse, ioe);
        }
        int responseCode = 0;
        try {
          responseCode = NetHandler.sendHttpsPost(exostarUrl,
                                                  exostarUserName,
                                                  exostarPassword,
                                                  xcblResponse,
                                                  hostnameVerifier);
        }
        catch (Exception e) {
          //System.out.println("Error sending response:" + e.getMessage());
          log.error("Error sending response", e);
          MailProcess.sendEmail(toEmail,
                                "",
                                fromEmail,
                                "Error sending response",
                                "Can't send response to exostar, check the logs." +
                                e.getMessage());
        }
        if (log.isDebugEnabled()) {
          log.debug("Response code:" + responseCode);
        }
        //if valid response update thru procedure
        if (responseCode == 200) {

          Iterator detailIterator = xcblOrderBean.getXcblOrderDetailBeanColl().iterator();
          while (detailIterator.hasNext()) {
            XcblOrderDetailBean detailBean = (XcblOrderDetailBean) detailIterator.next();
            Iterator scheduleIterator = detailBean.getXcblOrderDetailScheduleBeanColl().
                iterator();
            while (scheduleIterator.hasNext()) {
              XcblOrderDetailScheduleBean scheduleBean = (XcblOrderDetailScheduleBean)
                  scheduleIterator.next();
              Collection inArgs = new Vector(8);
              inArgs.add(xcblOrderBean.getBuyerOrderNumber().toString());
              inArgs.add(xcblOrderBean.getTransactionType());
              inArgs.add(scheduleBean.getScheduleLineId());
              inArgs.add(xcblOrderBean.getBuyerOrderNumber().toString());
              inArgs.add(xcblOrderBean.getTransport());
              inArgs.add(xcblOrderBean.getTransporterAccount());
              inArgs.add(xcblOrderBean.getTradingPartner());
              inArgs.add(xcblOrderBean.getTradingPartnerId());
              Collection outArgs = new Vector(1);
              outArgs.add(new Integer(java.sql.Types.VARCHAR));
              GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
              Collection resultColl = procFactory.doProcedure(
                  "pkg_dbuy_from_customer.customer_po_update_dcn", inArgs, outArgs);
              if (log.isDebugEnabled()) {
                log.debug("In arguments:" + inArgs);
                log.debug("Result size:" + resultColl.size());
                Iterator resultIterator = resultColl.iterator();
                while (resultIterator.hasNext()) {
                  String result = (String) resultIterator.next();
                  log.debug("Proc result:" + result);
                }
              }
            }
          }
        }
        else {
          log.fatal("Error sending response to Exostar:" + responseCode);
        }

      }
    }
    catch (Exception e) {
      log.error("Error:" + e.getMessage(), e);
    }
  }

  /*
   * Save order in flat format to table and set processing values for response
   */
  private XcblOrderBean saveChangeOrder(File xcblFile) throws BaseException {
    String orderResponse = null;
    //When we save the order we'll always return the status as processing
    //when getting the order back in the checkStatus method we'll update
    //with the "actual" status, ie, accepted, not accepted, etc
    String orderStatusNotes = "";
    DbManager dbManager = null;
    Connection connection = null;
    XcblOrderBean xcblChangeOrderBean = null;
    try {
      if (log.isDebugEnabled()) {
        log.debug("processing change order");
      }
      XcblChangeOrderParser parser = new XcblChangeOrderParser(getClient());
      xcblChangeOrderBean = parser.parse(xcblFile);
      //set Raytheon status codes for processing
      xcblChangeOrderBean.setResponseType(XcblProcess.ACCEPTED_WITH_AMENDMENT);
      xcblChangeOrderBean.setResponseOrderStatus(XcblProcess.ACCEPTED_WITH_AMENDMENT);
      xcblChangeOrderBean.setStatusEvent(XcblProcess.PROCESSING);
      //xcblChangeOrderBean.setOrderIssueDate(xcblChangeOrderBean.getChangeOrderDate());
      Iterator loop = xcblChangeOrderBean.getXcblOrderDetailBeanColl().iterator();
      while (loop.hasNext()) {
        XcblOrderDetailBean detailBean = (XcblOrderDetailBean) loop.next();
        detailBean.setResponseOrderStatus(XcblProcess.PENDING);
        detailBean.setStatusEvent(XcblProcess.PROCESSING);
      }
      if (log.isDebugEnabled()) {
        log.debug("BEAN:" + xcblChangeOrderBean);
      }
      if(XcblProcess.isValidTucsonOrder(xcblChangeOrderBean)) {
        //map normalized beans to a denormalized bean
        Collection customerPoPreStageBeanColl = this.getCustomerPoPreStageBeanCollection(
            xcblChangeOrderBean);
        if (log.isDebugEnabled()) {
          log.debug("Flat collection size:" + customerPoPreStageBeanColl.size());
        }
        Iterator iterator = customerPoPreStageBeanColl.iterator();
        dbManager = new DbManager(getClient());
        CustomerPoPreStageBeanFactory factory = new CustomerPoPreStageBeanFactory(
            dbManager);
        while (iterator.hasNext()) {
          CustomerPoPreStageBean customerPoPreStageBean = (CustomerPoPreStageBean)
              iterator.
              next();
          factory.insert(customerPoPreStageBean);
        }
      }
    }
    catch (Exception e) {
//I'll eat this error for now
      log.fatal("ERROR:" + e.getMessage());
      //System.out.println("e:" + e.getMessage());
      //BaseException be = new BaseException(e);
      //be.setRootCause(e);
      //throw be;
    }
    return xcblChangeOrderBean;
  }

  /*
   * Creates flat beans from a relational beans.
   */
  private Collection getCustomerPoPreStageBeanCollection(XcblOrderBean
      xcblChangeOrderBean) throws
      BaseException {
    DbManager dbManager = new DbManager(getClient());
    BigDecimal loadId = new BigDecimal(dbManager.getOracleSequence("CUSTOMER_PO_LOAD_SEQ"));
    Collection collection = new Vector();
    //since I need to "flatten" this bean into multiple "flat" beans I'll add the beans
    //to the collection in the iteraton of the schedule at the bottom of this method
    CustomerPoPreStageBean customerPoPreStageBean = new CustomerPoPreStageBean();
    //first copy the "header" bean
    customerPoPreStageBean.setCustomerPoNo(xcblChangeOrderBean.getBuyerOrderNumber());
    customerPoPreStageBean.setDateIssued(xcblChangeOrderBean.getChangeOrderDate());
    customerPoPreStageBean.setOriginalDateIssued(xcblChangeOrderBean.getOrderIssueDate());
    customerPoPreStageBean.setCustomerHaasAccountNo(xcblChangeOrderBean.
        getAccountCodeRefNum());
    customerPoPreStageBean.setAcceptanceCode(xcblChangeOrderBean.getRequestResponse());
    customerPoPreStageBean.setTransactionType(xcblChangeOrderBean.getOrderType());
    customerPoPreStageBean.setCurrencyId(xcblChangeOrderBean.getOrderCurrency());
    customerPoPreStageBean.setTransporterAccount(xcblChangeOrderBean.getOrderPartyIdent());
    customerPoPreStageBean.setBuyerPartyName(xcblChangeOrderBean.getOrderPartyName1() +
                                             ", " +
                                             StringHandler.emptyIfNull(xcblChangeOrderBean.getOrderPartyName2()) +
                                             ", " +
                                             StringHandler.emptyIfNull(xcblChangeOrderBean.getOrderPartyName3()));
    customerPoPreStageBean.setBuyerNameOnPo(xcblChangeOrderBean.getOrderPartyName1());
    customerPoPreStageBean.setBuyerAddress1(xcblChangeOrderBean.getOrderPartyStreet());
    customerPoPreStageBean.setBuyerZip(xcblChangeOrderBean.getOrderPartyPostalCode());
    customerPoPreStageBean.setBuyerCity(xcblChangeOrderBean.getOrderPartyCity());
    customerPoPreStageBean.setBuyerRegion(xcblChangeOrderBean.getOrderPartyRegion());
    customerPoPreStageBean.setBuyerCountry(xcblChangeOrderBean.getOrderPartyCountry());
    customerPoPreStageBean.setCustomerHaasContractId(xcblChangeOrderBean.getSellerIdent());
    customerPoPreStageBean.setCustomerHaasAccountNo(xcblChangeOrderBean.getSellerName1());
    customerPoPreStageBean.setSellerPartyName(xcblChangeOrderBean.getSellerName1() + ", " +
                                              StringHandler.emptyIfNull(xcblChangeOrderBean.getSellerName2()));
    customerPoPreStageBean.setSellerAddress1(xcblChangeOrderBean.getSellerStreet());
    customerPoPreStageBean.setSellerZip(xcblChangeOrderBean.getSellerPostalCode());
    customerPoPreStageBean.setSellerCity(xcblChangeOrderBean.getSellerCity());
    customerPoPreStageBean.setSellerRegion(xcblChangeOrderBean.getSellerRegion());
    customerPoPreStageBean.setSellerCountry(xcblChangeOrderBean.getSellerCountry());
    customerPoPreStageBean.setShiptoPartyId(xcblChangeOrderBean.getShipToIdent());
    customerPoPreStageBean.setShiptoPartyName(xcblChangeOrderBean.getShipToName1());
    //if they are sending a value in shiptoname2 ignore shiptoname1
    if(xcblChangeOrderBean.getShipToName2() != null && xcblChangeOrderBean.getShipToName2().length() > 0) {
      customerPoPreStageBean.setShiptoPartyName(xcblChangeOrderBean.getShipToName2());
    }
    customerPoPreStageBean.setShiptoAddress1(xcblChangeOrderBean.getShipToStreet());
    customerPoPreStageBean.setShiptoZip(xcblChangeOrderBean.getShipToPostalCode());
    customerPoPreStageBean.setShiptoCity(xcblChangeOrderBean.getShipToCity());
    customerPoPreStageBean.setShiptoRegion(xcblChangeOrderBean.getShipToRegion());
    customerPoPreStageBean.setShiptoCountry(xcblChangeOrderBean.getShipToCountry());
    customerPoPreStageBean.setShiptoContactName(xcblChangeOrderBean.getShipToContactName());
    customerPoPreStageBean.setBilltoPartyId(xcblChangeOrderBean.getBillToIdent());
    customerPoPreStageBean.setBilltoParty(xcblChangeOrderBean.getBillToName1());
    customerPoPreStageBean.setBilltoName2(xcblChangeOrderBean.getBillToName2());
    customerPoPreStageBean.setBilltoAddress1(xcblChangeOrderBean.getBillToStreet());
    customerPoPreStageBean.setBilltoZip(xcblChangeOrderBean.getBillToPostalCode());
    customerPoPreStageBean.setBilltoCity(xcblChangeOrderBean.getBillToCity());
    customerPoPreStageBean.setBilltoRegion(xcblChangeOrderBean.getBillToRegion());
    customerPoPreStageBean.setBilltoCountry(xcblChangeOrderBean.getBillToCountry());
    customerPoPreStageBean.setFreightOnBoardNotes(StringHandler.emptyIfNull(xcblChangeOrderBean.getTermsOfDeliveryFunction()) + " " + StringHandler.emptyIfNull(xcblChangeOrderBean.getShipmentMethodOfPayment()));
    customerPoPreStageBean.setFreightOnBoard(xcblChangeOrderBean.getTransportTerms());
    customerPoPreStageBean.setPaymentTerms(xcblChangeOrderBean.getPaymentTerm());
    customerPoPreStageBean.setPaymentTermsNotes(StringHandler.emptyIfNull(xcblChangeOrderBean.getPaymentTermsNote()) + " " + StringHandler.emptyIfNull(xcblChangeOrderBean.getPaymentMean()));
    customerPoPreStageBean.setCustomerPoNote(xcblChangeOrderBean.getNotes());
    customerPoPreStageBean.setTotalAmountOnPo(xcblChangeOrderBean.getMonetaryAmount());
    customerPoPreStageBean.setLoadId(loadId);
    customerPoPreStageBean.setDateInserted(new Date());
    //this is for a constraint on the customer_po_pre_stage table
    customerPoPreStageBean.setTransport("XCBL");
    customerPoPreStageBean.setTradingPartner("Exostar");
    customerPoPreStageBean.setTradingPartnerId("Exostar");
    customerPoPreStageBean.setPre860("N");
    customerPoPreStageBean.setStatus("NEW");
    customerPoPreStageBean.setChangeOrderSequence(xcblChangeOrderBean.
                                                  getChangeOrderSequence());
    //copy detail
    int loadLine = 1;
    Iterator detailIterator = xcblChangeOrderBean.getXcblOrderDetailBeanColl().iterator();
    while (detailIterator.hasNext()) {
      XcblOrderDetailBean detailBean = (XcblOrderDetailBean) detailIterator.next();
      customerPoPreStageBean.setCustomerPoLineNo(detailBean.getBuyerLineItemNum().
                                                 toString());
      customerPoPreStageBean.setManufacturerPartNum(detailBean.getManufacturerPartId());
      customerPoPreStageBean.setItemDescription(detailBean.getItemDescription());
      customerPoPreStageBean.setQuantity(detailBean.getQuantity());
      customerPoPreStageBean.setUom(detailBean.getUom());
      customerPoPreStageBean.setUnitPrice(detailBean.getUnitPrice());
      customerPoPreStageBean.setCurrencyId(detailBean.getCurrency());
      customerPoPreStageBean.setCustomerPoLineNote(detailBean.getNotes());
      customerPoPreStageBean.setLoadLine(new BigDecimal(loadLine));
      customerPoPreStageBean.setPriceBasisUom(detailBean.getPriceBasisUom());
      customerPoPreStageBean.setPriceBasisQuantity(detailBean.getPriceBasisQuantity());
      if (detailBean.getSellerPartId() != null &&
          detailBean.getSellerPartId().trim().length() > 0) {
        customerPoPreStageBean.setCatPartNo(detailBean.getSellerPartId());
      }
      else {
        customerPoPreStageBean.setCatPartNo(detailBean.getManufacturerPartId());
      }
      //copy references
      Iterator refIterator = detailBean.getXcblOrderDetailReferenceBeanColl().iterator();
      while (refIterator.hasNext()) {
        XcblOrderDetailReferenceBean refBean = (XcblOrderDetailReferenceBean) refIterator.
            next();
        if ("BinLocationNumber".equals(refBean.getKey())) {
          customerPoPreStageBean.setShiptoPartyId(refBean.getValue());
        }
        else if ("MaterialStorageLocation".equals(refBean.getKey())) {
          customerPoPreStageBean.setShiptoPartyName(customerPoPreStageBean.getShiptoPartyName() + " " + refBean.getValue());
        }
        else if ("PartialShipmentFlag".equals(refBean.getKey())) {
          customerPoPreStageBean.setPartialShipment(refBean.getValue());
        }
        else if ("Requisitioner Name".equals(refBean.getKey())) {
          customerPoPreStageBean.setRequestorName(refBean.getValue());
          customerPoPreStageBean.setBuyerIdOnPo(refBean.getValue());
        }
      }
      //copy schedule
      int lineSequence = 1;
      Iterator scheduleIterator = detailBean.getXcblOrderDetailScheduleBeanColl().
          iterator();
      while (scheduleIterator.hasNext()) {
        //this is the last iterator so I need to copy the attributes to a different bean
        //and add that bean to the collection
        CustomerPoPreStageBean flatBean = new CustomerPoPreStageBean();
        BeanHandler.copyAttributes(customerPoPreStageBean, flatBean);
        XcblOrderDetailScheduleBean scheduleBean = (XcblOrderDetailScheduleBean)
            scheduleIterator.next();
        flatBean.setLineSequence(new BigDecimal(lineSequence));
        flatBean.setScheduleQuantity(scheduleBean.getQuantity());
        flatBean.setScheduleUom(scheduleBean.getUom());
        flatBean.setRequestedDeliveryDate(scheduleBean.getRequestedDeliveryDate());
        //if there is a ship to id in the schedule bean I'll use that
        if(scheduleBean.getShipToIdent() != null && scheduleBean.getShipToIdent().length() > 0) {
          flatBean.setShiptoPartyId(scheduleBean.getShipToIdent());
          flatBean.setShiptoPartyName(scheduleBean.getShipToName1());
          //if they are sending a value in shiptoname2 ignore shiptoname1
          if (scheduleBean.getShipToName2() != null &&
              scheduleBean.getShipToName2().length() > 0) {
            flatBean.setShiptoPartyName(scheduleBean.getShipToName2());
          }
          flatBean.setShiptoAddress1(scheduleBean.getShipToStreet());
          flatBean.setShiptoZip(scheduleBean.getShipToPostalCode());
          flatBean.setShiptoCity(scheduleBean.getShipToCity());
          flatBean.setShiptoRegion(scheduleBean.getShipToRegion());
          flatBean.setShiptoCountry(scheduleBean.getShipToCountry());
          flatBean.setShiptoContactName(scheduleBean.getShipToContactName());
        }
        collection.add(flatBean);
        lineSequence++;
      }
      loadLine++;
    }
    return collection;
  }

  private String processChangeOrder(File xcblFile) throws BaseException {
    String changeOrderResponse = null;
    try {
      if (log.isDebugEnabled()) {
        log.debug("processing change order");
      }
      XcblChangeOrderParser parser = new XcblChangeOrderParser(getClient());
      XcblOrderBean xcblChangeOrderBean = parser.parse(xcblFile);
      if (log.isDebugEnabled()) {
        log.debug("BEAN:" + xcblChangeOrderBean);
      }
      changeOrderResponse = xcblChangeOrderResponseParser.getChangeOrderResponse(
          xcblChangeOrderBean);
    }
    catch (Exception e) {
//I'll eat this error for now
      log.fatal("ERROR:" + e.getMessage());
      //System.out.println("e:" + e.getMessage());
      //BaseException be = new BaseException(e);
      //be.setRootCause(e);
      //throw be;
    }
    return changeOrderResponse;
  }
}