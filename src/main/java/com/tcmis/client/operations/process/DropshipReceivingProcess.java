package com.tcmis.client.operations.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.operations.beans.CustDropShipReceivingViewBean;
import com.tcmis.client.operations.beans.DropShipReceivingInputBean;
import com.tcmis.client.operations.factory.CustDropShipReceivingViewBeanFactory;
import com.tcmis.client.operations.factory.DshipDropdownOvBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.SearchDropDownBeanFactory;
import com.tcmis.common.admin.factory.UserGroupMemberLocationBeanFactory;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceiptItemPriorBinViewBean;
import com.tcmis.internal.hub.beans.ReceivingKitBean;
import com.tcmis.internal.hub.factory.IssueBeanFactory;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class DropshipReceivingProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public DropshipReceivingProcess(String client) {
    super(client);
  }
  
  public DropshipReceivingProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getLocationPermissions(BigDecimal personnelId) throws BaseException
  {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    UserGroupMemberLocationBeanFactory locationFactory = new UserGroupMemberLocationBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("personnelId", SearchCriterion.EQUALS, personnelId.toString());

    return locationFactory.select(criteria);
  }
   
  public Collection getMyFacilityDockDropdownData(int userId) throws BaseException,Exception {
    Collection dropdownColl = null;
    DbManager dbManager = new DbManager(this.getClient(),getLocale());
    try {
      DshipDropdownOvBeanFactory factory = new DshipDropdownOvBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("personnelId", SearchCriterion.EQUALS,(new Integer(userId)).toString());
      dropdownColl = factory.selectObject(criteria);
    } finally {
      dbManager = null;
    }

    return dropdownColl;
  } //end of method


  public Collection getSearchDropDownBean(String category) throws BaseException, Exception {

    Collection searchDropDownBeanCollection = new Vector();

    DbManager dbManager = new DbManager(getClient(),getLocale());
    SearchDropDownBeanFactory SearchDropDownBeanFactory = new SearchDropDownBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("category", SearchCriterion.EQUALS, category);
    criteria.addCriterion("screenName", SearchCriterion.EQUALS, "DROPSHIP_RECEIVING");
    SortCriteria sortCriteria = new SortCriteria();
    sortCriteria.addCriterion("displayOrder");
    searchDropDownBeanCollection = SearchDropDownBeanFactory.selectDistinct(criteria, sortCriteria);

    return searchDropDownBeanCollection;
  }

  public Collection addBinToItemBinCollection(Collection receivingViewBeanCollection, String itemId, String binName) {
    Collection finalreceivingViewBeanCollection = new Vector();

    Iterator iterator = receivingViewBeanCollection.iterator();
    while (iterator.hasNext()) {
      CustDropShipReceivingViewBean currentReceivingViewBean = (CustDropShipReceivingViewBean) iterator.next();
      String currentItem = "" + currentReceivingViewBean.getItemId() + "";

      if (currentItem.equalsIgnoreCase(itemId)) {
        Collection receiptItemPriorBinViewBeanCollection = (Collection) currentReceivingViewBean.getReceiptItemPriorBinViewBeanCollection();

        ReceiptItemPriorBinViewBean receiptItemPriorBinViewBean = new ReceiptItemPriorBinViewBean();
        receiptItemPriorBinViewBean.setBin(binName);

        receiptItemPriorBinViewBeanCollection.add(receiptItemPriorBinViewBean);

        currentReceivingViewBean.setReceiptItemPriorBinViewBeanCollection(receiptItemPriorBinViewBeanCollection);
      }
      finalreceivingViewBeanCollection.add(currentReceivingViewBean);
    }
    return finalreceivingViewBeanCollection;
  }

  public Collection flattenRelationship(DropShipReceivingInputBean bean, Collection receivingViewBeanInputCollection, Collection receivingViewBeanCollection) {
    Collection finalNewBeanCollection = new Vector();

    if (bean.getDuplicateLine().length() == 0 && bean.getDuplicatePkgLine().length() == 0 && bean.getDuplicateKitLine().length() == 0) {

      Iterator finalIterator = receivingViewBeanCollection.iterator();
      while (finalIterator.hasNext()) {
        CustDropShipReceivingViewBean currentReceivingViewBean = (CustDropShipReceivingViewBean) finalIterator.next(); ;

        Collection receivingKitBeanCollection = currentReceivingViewBean.getKitCollection();

        Iterator kitIterator = receivingKitBeanCollection.iterator();
        while (kitIterator.hasNext()) {
          ReceivingKitBean receivingKitBean = (ReceivingKitBean) kitIterator.next(); ;

          Iterator finalKitIterator = receivingViewBeanInputCollection.iterator();
          while (finalKitIterator.hasNext()) {
            CustDropShipReceivingViewBean finalBean = (CustDropShipReceivingViewBean) finalKitIterator.next(); ;

            if (receivingKitBean.getRowNumber().equalsIgnoreCase(finalBean.getRowNumber())) {
              finalNewBeanCollection.add(finalBean);
              break;
            }
          }
        }
      }
    } else {
      finalNewBeanCollection = receivingViewBeanInputCollection;
    }
    return finalNewBeanCollection;
  }

  public HashMap receiveSelected(DropShipReceivingInputBean bean, Collection receivingViewBeanCollection, BigDecimal personnelId) {
    Collection receivedReceiptsCollection = new Vector();
    Collection finalReceivedReceiptsCollection = new Vector();
    String receivedReceipts = "";
    String errorMessage = "";

    if (bean.getDuplicateLine().length() == 0 && bean.getDuplicatePkgLine().length() == 0 &&
             bean.getDuplicateKitLine().length() == 0) {
             try {
                    /*if (bean.getCategory().equalsIgnoreCase("chemicals")) {
                     receivedReceiptsCollection = receiveSelected(receivingViewBeanCollection,
                            personnelId, false);
                    }
                    else*/ {
                     receivedReceiptsCollection = receiveSelected(receivingViewBeanCollection,
                            personnelId, false);
                    }
             }
             catch (BaseException ex) {
             }

      Iterator mainIterator = receivedReceiptsCollection.iterator();
      while (mainIterator.hasNext()) {
        CustDropShipReceivingViewBean currentReceivingViewBean = (CustDropShipReceivingViewBean) mainIterator.next(); ;

        Collection receivingKitBeanCollection = currentReceivingViewBean.getKitCollection();

        String currentOk = currentReceivingViewBean.getOk();
        String currentMvItem = currentReceivingViewBean.getMvItem();
        String currentUpdateStatus = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getUpdateStatus());

        if ((currentUpdateStatus != null && !"readOnly".equalsIgnoreCase(currentUpdateStatus)) && (currentOk != null && currentOk.length() > 0) || receivingKitBeanCollection.size() > 1) {
          String currentUpdate = currentReceivingViewBean.getUpdateStatus();
          if ("OK".equalsIgnoreCase(currentUpdate) || receivingKitBeanCollection.size() > 1) {
            Vector receiptKitVector = new Vector();
            Iterator kitIterator = receivingKitBeanCollection.iterator();
            while (kitIterator.hasNext()) {
              ReceivingKitBean receivingKitBean = (ReceivingKitBean) kitIterator.next(); ;

              String currentKitUpdate = receivingKitBean.getUpdateStatus();
              String currentKitOk = receivingKitBean.getOk();
              log.info("Result currentKitOk "+currentKitOk+" currentKitUpdate "+currentKitUpdate+"");
              if ((currentKitOk != null && currentKitOk.length() > 0) || ("Y".equalsIgnoreCase(currentMvItem))) {
                if (currentKitUpdate != null && "OK".equalsIgnoreCase(currentKitUpdate)) {
                  BigDecimal receivedReceipt1 = receivingKitBean.getReceivedReceipt1();
                  BigDecimal receivedReceipt2 = receivingKitBean.getReceivedReceipt2();

                  if (receivedReceipt1 != null) {
                    receivedReceipts += "" + receivedReceipt1 + ",";
                  }

                  if (receivedReceipt2 != null) {
                    if (receivedReceipt2.intValue() != 0) {
                      receivedReceipts += receivedReceipt2 + ",";
                    }
                  }
                } else {
                  if (currentKitUpdate != null && "Error".equalsIgnoreCase(currentKitUpdate)) {
                    errorMessage += " " + receivingKitBean.getErrorMessage();

                  }
                  receiptKitVector.add(receivingKitBean);
                }
              } else {
                //log.info("Here adding KitBean to the vector");
                receiptKitVector.add(receivingKitBean);
              }
            }

            if (receiptKitVector.size() > 0) {
              currentReceivingViewBean.setKitCollection((Vector) receiptKitVector.clone());
              currentReceivingViewBean.setRowSpan(new BigDecimal(receiptKitVector.size()));

              finalReceivedReceiptsCollection.add(currentReceivingViewBean);
            }
          } else {
            if (currentReceivingViewBean.getErrorMessage() != null) {
              errorMessage += " " + currentReceivingViewBean.getErrorMessage();
            }
            finalReceivedReceiptsCollection.add(currentReceivingViewBean);
          }
        } else {
          finalReceivedReceiptsCollection.add(currentReceivingViewBean);
        }
      }
    } else {
      finalReceivedReceiptsCollection = receivingViewBeanCollection;
    }

    if (receivedReceipts.length() > 1) {
      receivedReceipts = receivedReceipts.substring(0, (receivedReceipts.length() - 1));

      com.tcmis.common.util.StringHandler.replace(receivedReceipts, ",", "%44");
      //log.info("Here receivedReceipts " + receivedReceipts + "");
    }

    HashMap result = new HashMap();
    result.put("ERRORMESSAGE", errorMessage);
    result.put("RECEIVEDRECEIPTS", receivedReceipts);
    result.put("RECEIVEDRECEIPTSCOLLECTION", finalReceivedReceiptsCollection);
    return result;
  }

  public Collection copyAttributes(DropShipReceivingInputBean bean, Collection receivingViewBeanCollection, Vector savedBeanVector) {
    int duplicateKitCount = 0;
    int kitSize = 0;
    Collection receivingViewBeanColl = new Vector();
    Collection duplicateKitColl = new Vector();

    Iterator iterator = receivingViewBeanCollection.iterator();
    int count = 0;
    while (iterator.hasNext()) {
      CustDropShipReceivingViewBean inputBean = (CustDropShipReceivingViewBean) iterator.next();
      CustDropShipReceivingViewBean savedBean = (CustDropShipReceivingViewBean) savedBeanVector.get(count);

      String updateStatus = com.tcmis.common.util.StringHandler.emptyIfNull(inputBean.getUpdateStatus());
      if ("readOnly".equalsIgnoreCase(updateStatus)) {
        savedBean.setRowNumber(inputBean.getRowNumber());
        savedBean.setUpdateStatus(inputBean.getUpdateStatus());
        receivingViewBeanColl.add(savedBean);
      } else {
        BigDecimal radianPo = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(inputBean.getRadianPo());
        BigDecimal lineItem = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(inputBean.getLineItem());

        BigDecimal savedRadianPo = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(savedBean.getRadianPo());
        BigDecimal savedLineItem = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(savedBean.getLineItem());

        //log.info("Receiving  PO "+inputBean.getRadianPo()+"-"+inputBean.getLineItem()+" Saved PO "+savedRadianPo+"-"+savedLineItem+" Qty "+inputBean.getQuantityReceived()+" Lot "+inputBean.getMfgLot()+" Close "+inputBean.getClosePoLine()+"");
        if ((radianPo.equals(savedRadianPo)) && (lineItem.equals(savedLineItem))) {

          if (inputBean.getOk() != null) {
            String rowid = (String) inputBean.getOk();
            savedBean.setOk(rowid);
            //log.info("rowid     " + rowid + "");
          } else {
            savedBean.setOk(null);
          }

          savedBean.setBin(inputBean.getBin());
          savedBean.setMfgLot(inputBean.getMfgLot());
          savedBean.setSupplierShipDate(inputBean.getSupplierShipDate());
          savedBean.setDateOfReceipt(inputBean.getDateOfReceipt());
          savedBean.setDom(inputBean.getDom());
          savedBean.setDos(inputBean.getDos());
          savedBean.setExpirationDate(inputBean.getExpirationDate());
          savedBean.setQuantityReceived(inputBean.getQuantityReceived());
          savedBean.setLotStatus(inputBean.getLotStatus());
          savedBean.setReceiptNotes(inputBean.getReceiptNotes());
          savedBean.setDeliveryTicket(inputBean.getDeliveryTicket());
          savedBean.setCountryAbbrev(inputBean.getCountryAbbrev());
          savedBean.setPackagedQty(inputBean.getPackagedQty());
          savedBean.setPackagedSize(inputBean.getPackagedSize());
          savedBean.setTransferReceiptId(inputBean.getTransferReceiptId());
          savedBean.setDuplicatePkgLine(inputBean.getDuplicatePkgLine());
          savedBean.setDuplicateKitLine(inputBean.getDuplicateKitLine());
          savedBean.setRowSpan(inputBean.getRowSpan());
          savedBean.setRowNumber(inputBean.getRowNumber());
          savedBean.setClosePoLine(inputBean.getClosePoLine());

          receivingViewBeanColl.add(savedBean);
          try {
            if ((bean.getDuplicateLine() != null && bean.getDuplicateLine().length() > 0) && bean.getDuplicateLine().equalsIgnoreCase("" + count + "")) {
              CustDropShipReceivingViewBean duplicateBean = (CustDropShipReceivingViewBean) savedBean.clone();
              duplicateBean.setOk(null);
              duplicateBean.setMfgLot(null);
              duplicateBean.setTransferReceiptId(null);
              duplicateBean.setDom(null);
              duplicateBean.setDos(null);
              duplicateBean.setExpirationDate(null);
              duplicateBean.setQuantityReceived(null);
              duplicateBean.setPackagedQty(null);
              duplicateBean.setPackagedSize(null);
              duplicateBean.setReceiptNotes(null);
              duplicateBean.setDeliveryTicket(null);
              duplicateBean.setCountryAbbrev(null);
              duplicateBean.setClosePoLine(null);
              receivingViewBeanColl.add(duplicateBean);
            } else if ((bean.getDuplicatePkgLine() != null && bean.getDuplicatePkgLine().length() > 0) && bean.getDuplicatePkgLine().equalsIgnoreCase("" + count + "")) {
              CustDropShipReceivingViewBean duplicateBean = (CustDropShipReceivingViewBean) savedBean.clone();
              duplicateBean.setDuplicatePkgLine("");
              duplicateBean.setOk(null);
              duplicateBean.setMfgLot(null);
              duplicateBean.setTransferReceiptId(null);
              duplicateBean.setDom(null);
              duplicateBean.setDos(null);
              duplicateBean.setExpirationDate(null);
              duplicateBean.setQuantityReceived(null);
              duplicateBean.setPackagedQty(null);
              duplicateBean.setPackagedSize(null);
              duplicateBean.setReceiptNotes(null);
              duplicateBean.setDeliveryTicket(null);
              duplicateBean.setCountryAbbrev(null);
              duplicateBean.setClosePoLine(null);
              receivingViewBeanColl.add(duplicateBean);
            } else if ((bean.getDuplicateKitLine() != null && bean.getDuplicateKitLine().length() > 0)) {
              if (savedBean.getDuplicateKitLine().length() > 0 && bean.getDuplicateKitLine().equalsIgnoreCase("" + count + "")) {
                duplicateKitCount++;
                kitSize = savedBean.getRowSpan().intValue();
                //log.info("duplicateKitCount  "+duplicateKitCount+"  duplicateKitLineRownum  "+kitSize+"");
                CustDropShipReceivingViewBean duplicateBean = (CustDropShipReceivingViewBean) savedBean.clone();
                duplicateBean.setDuplicateKitLine("");
                duplicateBean.setOk(null);
                duplicateBean.setMfgLot(null);
                duplicateBean.setTransferReceiptId(null);
                duplicateBean.setDom(null);
                duplicateBean.setDos(null);
                duplicateBean.setExpirationDate(null);
                duplicateBean.setQuantityReceived(null);
                duplicateBean.setPackagedQty(null);
                duplicateBean.setPackagedSize(null);
                duplicateBean.setReceiptNotes(null);
                duplicateBean.setDeliveryTicket(null);
                duplicateBean.setCountryAbbrev(null);
                duplicateBean.setClosePoLine(null);
                duplicateKitColl.add(duplicateBean);
              } else if (savedBean.getDuplicateKitLine().length() > 0 && bean.getDuplicateKitLine().equalsIgnoreCase(savedBean.getDuplicateKitLine())) {
                duplicateKitCount++;
                //log.info("duplicateKitCount  "+duplicateKitCount+"");
                CustDropShipReceivingViewBean duplicateBean = (CustDropShipReceivingViewBean) savedBean.clone();
                duplicateBean.setDuplicateKitLine("");
                duplicateBean.setOk(null);
                duplicateBean.setMfgLot(null);
                duplicateBean.setTransferReceiptId(null);
                duplicateBean.setDom(null);
                duplicateBean.setDos(null);
                duplicateBean.setExpirationDate(null);
                duplicateBean.setQuantityReceived(null);
                duplicateBean.setPackagedQty(null);
                duplicateBean.setPackagedSize(null);
                duplicateBean.setReceiptNotes(null);
                duplicateBean.setDeliveryTicket(null);
                duplicateBean.setCountryAbbrev(null);
                duplicateBean.setClosePoLine(null);
                duplicateKitColl.add(duplicateBean);

                if (duplicateKitCount == kitSize) {
                  //log.info("adding duplicateBean");
                  receivingViewBeanColl.addAll(duplicateKitColl);
                  duplicateKitCount = 0;
                }
              }
            }
          } catch (CloneNotSupportedException ex) {
          }
        }
      }
      count++;
    }

    if (duplicateKitCount > 0) {
      //log.info("adding duplicateBean outside the loop");
      receivingViewBeanColl.addAll(duplicateKitColl);
      duplicateKitCount = 0;
    }
    return receivingViewBeanColl;
  }

  public Collection createRelationalObject(Collection receivingViewBeanCollection) {
    Collection finalreceivingViewBeanCollection = new Vector();
    String nextPoNumber = "";
    String nextPoLine = "";
    String nextTransferRequestId = "";
    String manageKitAsSingle = "";
    String nextItem = "";
    String nextDuplicatePkgLine = "";
    String nextDuplicateKitLine = "";
    String nextMrNumber = "";
    String nextMrLine = "";

    int samePoLineCount = 0;
    Vector collectionVector = new Vector(receivingViewBeanCollection);
    Vector poLineKitVector = new Vector();

    for (int loop = 0; loop < collectionVector.size(); loop++) {

      CustDropShipReceivingViewBean currentReceivingViewBean = (CustDropShipReceivingViewBean) collectionVector.elementAt(loop);

      String currentPo = (currentReceivingViewBean.getRadianPo() == null?"":""+currentReceivingViewBean.getRadianPo()+"") ;
      String currentPOLine = "" + currentReceivingViewBean.getLineItem() + "";
      String currentTransferRequestId = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getTransferRequestId());
      String currentItem = "" + currentReceivingViewBean.getItemId() + "";
      manageKitAsSingle = currentReceivingViewBean.getManageKitsAsSingleUnit();
      String currentDuplicatePkgLine = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getDuplicatePkgLine());
      String currentDuplicateKitLine = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getDuplicateKitLine());
      String currentMrNumber = (currentReceivingViewBean.getMrNumber() == null?"":"" + currentReceivingViewBean.getMrNumber() + "");
      String currentMrLine = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getMrLineItem());

      if (!((loop + 1) == collectionVector.size())) {
        CustDropShipReceivingViewBean nextReceivingViewBean = (CustDropShipReceivingViewBean) collectionVector.elementAt(loop + 1);

        nextPoNumber = "" + nextReceivingViewBean.getRadianPo() + "";
        nextPoLine = (nextReceivingViewBean.getLineItem()==null?"":""+nextReceivingViewBean.getLineItem()+"");
        nextTransferRequestId = com.tcmis.common.util.StringHandler.emptyIfNull(nextReceivingViewBean.getTransferRequestId());
        nextItem = (nextReceivingViewBean.getItemId() == null?"":"" + nextReceivingViewBean.getItemId() + "");
        nextDuplicatePkgLine = com.tcmis.common.util.StringHandler.emptyIfNull(nextReceivingViewBean.getDuplicatePkgLine());
        nextDuplicateKitLine = com.tcmis.common.util.StringHandler.emptyIfNull(nextReceivingViewBean.getDuplicateKitLine());
        nextMrNumber = "" + nextReceivingViewBean.getMrNumber() + "";
        nextMrLine = nextReceivingViewBean.getMrLineItem();
      } else {
        nextPoNumber = "";
        nextPoLine = "";
        nextTransferRequestId = "";
        nextItem = "";
        nextDuplicatePkgLine = "";
        nextDuplicateKitLine = "";
        nextMrNumber = "";
        nextMrLine = "";
      }

      boolean samePoLineNumber = false;      
      if (currentDuplicateKitLine.equalsIgnoreCase(nextDuplicateKitLine) && currentDuplicatePkgLine.equalsIgnoreCase(nextDuplicatePkgLine) && currentPo.equalsIgnoreCase(nextPoNumber) && nextPoLine.equalsIgnoreCase(currentPOLine) &&
          currentMrNumber.equalsIgnoreCase(nextMrNumber) && currentMrLine.equalsIgnoreCase(nextMrLine) &&
          nextTransferRequestId.equalsIgnoreCase(currentTransferRequestId) && nextItem.equalsIgnoreCase(currentItem)) {
        samePoLineNumber = true;
        samePoLineCount++;
      }

      ReceivingKitBean receivingKitBean = new ReceivingKitBean();
      receivingKitBean.setMaterialDesc(currentReceivingViewBean.getMaterialDesc());
      receivingKitBean.setPackaging(currentReceivingViewBean.getPackaging());
      receivingKitBean.setOk(currentReceivingViewBean.getOk());
      receivingKitBean.setBin(currentReceivingViewBean.getBin());
//	 receivingKitBean.setOriginalMfgLot(currentReceivingViewBean.getOriginalMfgLot());
      receivingKitBean.setMfgLot(currentReceivingViewBean.getMfgLot());
      receivingKitBean.setManageKitsAsSingleUnit(currentReceivingViewBean.getManageKitsAsSingleUnit());
//	 receivingKitBean.setOriginalReceiptId(currentReceivingViewBean.getOriginalReceiptId());
      receivingKitBean.setSupplierShipDate(currentReceivingViewBean.getSupplierShipDate());
      receivingKitBean.setDateOfReceipt(currentReceivingViewBean.getDateOfReceipt());
      receivingKitBean.setDom(currentReceivingViewBean.getDom());
      receivingKitBean.setDos(currentReceivingViewBean.getDos());
      receivingKitBean.setExpirationDate(currentReceivingViewBean.getExpirationDate());
      receivingKitBean.setPackagedQty(currentReceivingViewBean.getPackagedQty());
      receivingKitBean.setPackagedSize(currentReceivingViewBean.getPackagedSize());
      receivingKitBean.setQuantityReceived(currentReceivingViewBean.getQuantityReceived());
      receivingKitBean.setLotStatus(currentReceivingViewBean.getLotStatus());
      receivingKitBean.setSkipReceivingQc(currentReceivingViewBean.getSkipReceivingQc());
      receivingKitBean.setReceiptNotes(currentReceivingViewBean.getReceiptNotes());
      receivingKitBean.setDeliveryTicket(currentReceivingViewBean.getDeliveryTicket());
      receivingKitBean.setCountryAbbrev(currentReceivingViewBean.getCountryAbbrev());
      receivingKitBean.setItemId(currentReceivingViewBean.getItemId());
      receivingKitBean.setComponentId(currentReceivingViewBean.getComponentId());
      receivingKitBean.setTransferReceiptId(currentReceivingViewBean.getTransferReceiptId());
      receivingKitBean.setRowNumber(currentReceivingViewBean.getRowNumber());
      receivingKitBean.setOrderQtyUpdateOnReceipt(currentReceivingViewBean.getOrderQtyUpdateOnReceipt());
      receivingKitBean.setClosePoLine(currentReceivingViewBean.getClosePoLine());
      poLineKitVector.add(receivingKitBean);
      receivingKitBean.setMvItem(currentReceivingViewBean.getMvItem());
      receivingKitBean.setPurchasingUnitsPerItem(currentReceivingViewBean.getPurchasingUnitsPerItem());
      receivingKitBean.setPurchasingUnitOfMeasure(currentReceivingViewBean.getPurchasingUnitOfMeasure());

      if (samePoLineNumber) {

      } else {
        currentReceivingViewBean.setKitCollection((Vector) poLineKitVector.clone());
        poLineKitVector = new Vector();
        currentReceivingViewBean.setRowSpan(new BigDecimal(samePoLineCount + 1));

        finalreceivingViewBeanCollection.add(currentReceivingViewBean);

        samePoLineCount = 0;
      }
    }

    return finalreceivingViewBeanCollection;
  }

  public Collection receiveSelected(Collection receivingViewBeanCollection, BigDecimal personnelId, boolean nonChemicalreceipt) throws BaseException {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    Vector shipmentIds = new Vector();
    HashMap mrDeliveryTkt = new HashMap();
        
    CustDropShipReceivingViewBeanFactory factory = new CustDropShipReceivingViewBeanFactory(dbManager);
    IssueBeanFactory issueBeanFactory = new IssueBeanFactory(dbManager);
    BigDecimal picklistId = issueBeanFactory.selectBatchNumber();
    Iterator mainIterator = receivingViewBeanCollection.iterator();
    while (mainIterator.hasNext()) {
      CustDropShipReceivingViewBean currentReceivingViewBean = (CustDropShipReceivingViewBean) mainIterator.next(); ;
      String mrDtkt = currentReceivingViewBean.getMrNumber()+currentReceivingViewBean.getDeliveryTicket();    
      String currentPo = "" + currentReceivingViewBean.getRadianPo() + "";
      String currentPOLine = "" + currentReceivingViewBean.getLineItem() + "";
      String currentItem = "" + currentReceivingViewBean.getItemId() + "";
      String currentOk = currentReceivingViewBean.getOk();
      String currentMvItem = currentReceivingViewBean.getMvItem();
      String currentmanageKitAsSingle = currentReceivingViewBean.getManageKitsAsSingleUnit();
      String currentUpdateStatus = com.tcmis.common.util.StringHandler.emptyIfNull(currentReceivingViewBean.getUpdateStatus());
      Collection receivingKitBeanCollection = currentReceivingViewBean.getKitCollection();

      if ((currentUpdateStatus != null && !"readOnly".equalsIgnoreCase(currentUpdateStatus)) && ((currentOk != null && currentOk.length() > 0) || receivingKitBeanCollection.size() > 1)) {

        Iterator kitIterator = receivingKitBeanCollection.iterator();
        int kitCount = 0;
        Collection result = null;
        boolean receiptSuccess = true;
        boolean receiptKitSuccess = true;
        while (kitIterator.hasNext()) {
          ReceivingKitBean receivingKitBean = (ReceivingKitBean) kitIterator.next(); ;

          BigDecimal receivedReceipt1 = currentReceivingViewBean.getReceivedReceipt1();
          BigDecimal receivedReceipt2 = currentReceivingViewBean.getReceivedReceipt2();
          String currentKitOk = receivingKitBean.getOk();
          if (currentKitOk == null) {
            currentKitOk = "";
          }
          //log.info("currentOk  "+currentOk+"  currentKitOk "+currentKitOk+"");
          if ((kitCount == 0 && (currentOk != null && currentOk.length() > 0)) || (!("N".equalsIgnoreCase(currentmanageKitAsSingle)) && (currentKitOk != null && currentKitOk.length() > 0)) || "Y".equalsIgnoreCase(currentMvItem)) {

            if (kitCount == 0)
            {
            	if (getClient().equalsIgnoreCase("TCM_OPS"))
            	{
                  if(mrDeliveryTkt.get(mrDtkt) == null)	
                  {	               
                	BigDecimal newShipmentId =  getNextShipmentSeq();
	                currentReceivingViewBean.setShipmentId(newShipmentId);
	                shipmentIds.add( newShipmentId );
	                mrDeliveryTkt.put(mrDtkt, newShipmentId);
	               }
	               else
	               {
	            	  BigDecimal id = (BigDecimal) mrDeliveryTkt.get(mrDtkt);
	            	  currentReceivingViewBean.setShipmentId(id); 
	            	 // shipmentIds.add(id);
	               }
               }
            }

            try {
              boolean validQuantityReceived = false;
              boolean closePoLine = false;
              BigDecimal quantityReceived = currentReceivingViewBean.getQuantityReceived();
              BigDecimal kitQuantityReceived = receivingKitBean.getQuantityReceived();
              if (("N".equalsIgnoreCase(currentReceivingViewBean.getManageKitsAsSingleUnit()))) {
                if (quantityReceived != null && quantityReceived.floatValue() > 0) {
                  validQuantityReceived = true;
                } else if (quantityReceived != null && quantityReceived.floatValue() == 0 && currentReceivingViewBean.getClosePoLine() != null && currentReceivingViewBean.getClosePoLine().trim().length() > 0) {
                  closePoLine = true;
                }
              } else if ("Y".equalsIgnoreCase(currentReceivingViewBean.getMvItem()) && receivingKitBean.getPackagedQty() != null) {
                BigDecimal packagedQuantityReceived = new BigDecimal(receivingKitBean.getPackagedQty());

                if (packagedQuantityReceived != null && packagedQuantityReceived.floatValue() > 0) {
                  validQuantityReceived = true;
                  BigDecimal packagedQuantitySize = new BigDecimal(receivingKitBean.getPackagedSize());
                  BigDecimal purchasingUnitsPerItem = receivingKitBean.getPurchasingUnitsPerItem();
                  if (packagedQuantitySize.subtract(purchasingUnitsPerItem).divide(purchasingUnitsPerItem, 3, BigDecimal.ROUND_HALF_UP).abs().compareTo(new BigDecimal("0.25")) > 0) {
                    BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
                    bulkMailProcess.sendBulkEmail(new BigDecimal("19143"), "MV item Packaged Size received is 25% off for Purchase Order : " + currentPo + "  Line: " + currentPOLine + "",
                        "MV item Packaged Size received is 25% off for Purchase Order : " + currentPo + "  Line: " + currentPOLine + "\n\nIt is 25% off of the defined value (" + purchasingUnitsPerItem + " " +
                        receivingKitBean.getPurchasingUnitOfMeasure() + ").Received size - " + packagedQuantitySize + " " + receivingKitBean.getPurchasingUnitOfMeasure() + ".\n\nReceived By personnel Id- " + personnelId + "", false);
                  }
                } else if (packagedQuantityReceived != null && packagedQuantityReceived.floatValue() == 0 && receivingKitBean.getClosePoLine() != null && receivingKitBean.getClosePoLine().trim().length() > 0) {
                  closePoLine = true;
                }
              } else {
                if (kitQuantityReceived != null && kitQuantityReceived.floatValue() > 0) {
                  validQuantityReceived = true;
                } else if (kitQuantityReceived != null && kitQuantityReceived.floatValue() == 0 && receivingKitBean.getClosePoLine() != null && receivingKitBean.getClosePoLine().trim().length() > 0) {
                  closePoLine = true;
                }
              }

              if (validQuantityReceived && ((currentKitOk != null && currentKitOk.length() > 0) || ("Y".equalsIgnoreCase(currentMvItem) && (currentOk != null && currentOk.length() > 0)))) {
                currentReceivingViewBean.setBatch(picklistId);
                boolean internalDropShipReceipt = false;
                if (getClient().equalsIgnoreCase("TCM_OPS"))
                    internalDropShipReceipt = true;
                result = factory.insertReceipt(currentReceivingViewBean, receivingKitBean, personnelId, nonChemicalreceipt,internalDropShipReceipt);
              } else if (closePoLine) {
                //log.info("Calling p_cust_po_qty_update_from_rcpt radian PO " + currentReceivingViewBean.getRadianPo() + " Line " + currentReceivingViewBean.getLineItem() + "");
                Collection closePoLineresult = null;
                closePoLineresult = factory.closePoLine(currentReceivingViewBean, personnelId);

                Iterator closePoLineresultterator = closePoLineresult.iterator();
                int closePoLineresultCount = 0;
                //boolean closePoLineSuccess = true;
                String closePoLineerrorCode = "";
                while (closePoLineresultterator.hasNext()) {
                  if (closePoLineresultCount == 0) {
                    closePoLineerrorCode = (String) closePoLineresultterator.next(); ;
                    if (closePoLineerrorCode == null) {
                      closePoLineerrorCode = "";
                    }
                    if (closePoLineerrorCode.length() > 0) {
                      //log.info("error from p_cust_po_qty_update_from_rcpt " + closePoLineerrorCode + "");
                      receiptSuccess = false;
                    }
                  }
                  closePoLineresultCount++;
                }

                if (receiptSuccess) {
                  currentReceivingViewBean.setUpdateStatus("OK");
                } else {
                  currentReceivingViewBean.setUpdateStatus("Error");
                  currentReceivingViewBean.setErrorMessage(closePoLineerrorCode);
                }
              }

              //if (kitCount > 0)
              {
                receivingKitBean.setUpdateStatus("OK");
              }
            } catch (Exception ex) {
              ex.printStackTrace();
              log.info("sefsdf "+ex.getMessage());
              currentReceivingViewBean.setErrorMessage(ex.getMessage());
              currentReceivingViewBean.setUpdateStatus("Error");
              receiptSuccess = false;
              if (kitCount > 0) {
                receivingKitBean.setErrorMessage(ex.getMessage());
                receivingKitBean.setUpdateStatus("Error");
                receiptKitSuccess = false;
              }
            }

            if (result != null) {
              Iterator resultIterator = result.iterator();
              int resultCount = 0;
              while (resultIterator.hasNext()) {
                if (resultCount == 0) {
                  receivedReceipt1 = (BigDecimal) resultIterator.next(); ;
                  if ("Y".equalsIgnoreCase(currentMvItem) && kitCount == 0) {
                    currentReceivingViewBean.setReceivedReceipt1(receivedReceipt1);
                  } else if (!"Y".equalsIgnoreCase(currentMvItem)) {
                    currentReceivingViewBean.setReceivedReceipt1(receivedReceipt1);
                  }
                  receivingKitBean.setReceivedReceipt1(receivedReceipt1);
                } else if (resultCount == 1) {
                  receivedReceipt2 = (BigDecimal) resultIterator.next(); ;
                  receivingKitBean.setReceivedReceipt2(receivedReceipt2);
                } else if (resultCount == 2) {
                  BigDecimal issueId = (BigDecimal) resultIterator.next(); ;
                  currentReceivingViewBean.setIssueId(issueId);
                }
                else if (resultCount == 3) {
                  String errorCode = (String) resultIterator.next(); ;
                  currentReceivingViewBean.setErrorMessage(errorCode);
                }
                resultCount++;
              }
              log.info("Result from P_CUSTOMER_RECEIVE_DROP_SHIP PO " + currentPo + " Line " + currentPOLine + " Receipt 1:" + currentReceivingViewBean.getReceivedReceipt1() + " issue Id :" + currentReceivingViewBean.getIssueId() + "  Error Code: " + currentReceivingViewBean.getErrorMessage() + "");

              if (receivedReceipt1 == null || receivedReceipt1.intValue() == -1) {
                receiptSuccess = false;
              }
            }
          }

          if (receivedReceipt1 != null && ("N".equalsIgnoreCase(currentmanageKitAsSingle) && (currentOk != null && currentOk.length() > 0))) {
            //Call p_receipt_component
            if (receiptSuccess) {
              log.info("Calling p_customer_receipt_component for COMPONENT_ID " + currentReceivingViewBean.getComponentId() + " RECEIPT_ID1 " + currentReceivingViewBean.getReceivedReceipt1() + " Personnel ID  " + personnelId + "");
              try {
                receiptSuccess = factory.insertReceiptComponenet(receivingKitBean, currentReceivingViewBean.getReceivedReceipt1(), personnelId);
                receivingKitBean.setUpdateStatus("OK");
              } catch (Exception ex1) {
                receiptSuccess = false;
                receivingKitBean.setErrorMessage("Error Calling p_customer_receipt_component for Componenet " + currentReceivingViewBean.getComponentId() + " Receipt ID: " + currentReceivingViewBean.getReceivedReceipt1() + ". ");
                receivingKitBean.setUpdateStatus("Error");
                receiptKitSuccess = false;
              }
              if (currentReceivingViewBean.getReceivedReceipt2() !=null && currentReceivingViewBean.getReceivedReceipt2().intValue() != 0) {
                log.info("Calling p_customer_receipt_component for COMPONENT_ID " + currentReceivingViewBean.getComponentId() + " RECEIPT_ID2 " + currentReceivingViewBean.getReceivedReceipt2() + " Personnel ID  " + personnelId + "");
                try {
                  receiptSuccess = factory.insertReceiptComponenet(receivingKitBean, currentReceivingViewBean.getReceivedReceipt2(), personnelId);
                  receivingKitBean.setUpdateStatus("OK");
                } catch (Exception ex2) {
                  receivingKitBean.setErrorMessage("Error Calling p_customer_receipt_component for Componenet " + currentReceivingViewBean.getComponentId() + " Receipt ID: " + currentReceivingViewBean.getReceivedReceipt2() + ". ");
                  receivingKitBean.setUpdateStatus("Error");
                  receiptKitSuccess = false;
                  receiptSuccess = false;
                }
              }
            }
          }

          //log.info("Here receiptSuccess " + receiptSuccess + " receiptKitSuccess " +	receiptKitSuccess + "");
          if (receiptSuccess) {
            currentReceivingViewBean.setUpdateStatus("OK");
          } else {
            currentReceivingViewBean.setUpdateStatus("Error");
          }
          kitCount++;
        }
      }
    }

      /*
    if (getClient().equalsIgnoreCase("TCM_OPS"))
    {
     Collection invoiceResult = null;
     if (shipmentIds !=null && shipmentIds.size() > 0)
     {
      for ( int i=0; i < shipmentIds.size(); i++ )
      {
        BigDecimal shipmentId = ( BigDecimal ) shipmentIds.elementAt( i );
        try
        {
          invoiceResult = factory.generateInvoice(shipmentId);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        String errorCode = "";
        if (invoiceResult != null) {
         Iterator resultIterator = invoiceResult.iterator();
         while (resultIterator.hasNext()) {
            errorCode = (String) resultIterator.next();
         }
        log.info("errorCode "+errorCode);
      }
     }
     }
    }
    */

    return receivingViewBeanCollection;
  }

    public BigDecimal getNextShipmentSeq() throws BaseException
   {
    BigDecimal b = null;
    Connection connection = null;
    DbManager dbManager1 = new DbManager(getClient(),this.getLocale());
    try {
    	connection = dbManager1.getConnection();
        b = new BigDecimal("" +new SqlManager().getOracleSequence(connection, "shipment_seq"));
    }
    finally {
    	dbManager1.returnConnection(connection);
		dbManager1 = null;
		connection = null;
    }
    return b;
  }
    
  public Collection getResult(DropShipReceivingInputBean bean, boolean hasUpdatePermission, PersonnelBean personnelBean) throws BaseException {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    CustDropShipReceivingViewBeanFactory factory = new CustDropShipReceivingViewBeanFactory(dbManager);
    String sortBy = null;
    SearchCriteria criteria = new SearchCriteria();
 
    if(StringHandler.isBlankString(bean.getInventoryGroup())) {
//		if(!StringHandler.isBlankString(bean.getOperatingEntityId()))
//			criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOperatingEntityId());
	    if(!StringHandler.isBlankString(bean.getBranchPlant()))
	        criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getBranchPlant());
    }
    if(!StringHandler.isBlankString(bean.getInventoryGroup())) {

        criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());

    }
    else if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0
            && personnelBean.getCompanyId().equalsIgnoreCase("Radian")) {
        String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
       
        if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
        invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
        
        criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
    }

    if (bean.getCompanyId()!=null && bean.getCompanyId().length() >0)
    {
//    	This is added for the search from RAY
    	String query = " select ship_to_location_id from user_group_member_location where user_group_id = 'DropshipReceiving' and personnel_id = " + personnelBean.getPersonnelId() ;
        
        criteria.addCriterion("shipToLocationId", SearchCriterion.IN, query);
        
    	criteria.addCriterion("companyId", SearchCriterion.EQUALS, bean.getCompanyId());
    }
    if (bean.getFacilityId()!=null && bean.getFacilityId().length() >0)
    {
    	criteria.addCriterion("shipToFacilityId", SearchCriterion.EQUALS, bean.getFacilityId());
    }
    if (!"All Docks".equals(bean.getDockId())&& bean.getDockId().length() >0) {
      criteria.addCriterion("shipToLocationId", SearchCriterion.EQUALS, bean.getDockId());
    }  
    
    if(bean.getCustomerId() != null) 
    	criteria.addCriterion("customerId", SearchCriterion.EQUALS, bean.getCustomerId().toString());
    		

    //add description search if it's not null
    if (bean.getSearchText() != null && bean.getSearchText().trim().length() > 0) {
      if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("IS")) {
        criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.EQUALS, bean.getSearchText().trim());
      } else if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("LIKE")) {
        criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.LIKE, bean.getSearchText().trim(), SearchCriterion.IGNORE_CASE);
      } else if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("STARTSWITH")) {
        criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.STARTS_WITH, bean.getSearchText().trim(),SearchCriterion.IGNORE_CASE);
      } else if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("ENDSWITH")) {
        criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.ENDS_WITH, bean.getSearchText().trim(),SearchCriterion.IGNORE_CASE);
      }
    }
    //add expected date if not null
    if (bean.getExpectedWithin() != null) {
      //get number of days
      int days = bean.getExpectedWithin().intValue();
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, +days);
      criteria.addCriterion("expected", SearchCriterion.LESS_THAN_OR_EQUAL_TO, DateHandler.getOracleDate(cal.getTime()));
    }

    if (hasUpdatePermission) {
      return factory.select(criteria, bean.getSortBy());
    } else {
      return factory.selectReadOnly(criteria, bean.getSortBy());
    }
  }
/*  
	public CustDropShipReceivingViewBean processIndefinite(CustDropShipReceivingViewBean bean) throws BaseException {   
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		String expDateMaskS = library.getString("java.dateformat");
		String indefinite = library.getString("label.indefinite");
		java.text.SimpleDateFormat dateParser = new java.text.SimpleDateFormat(expDateMaskS,getLocaleObject());
		dateParser.setLenient(false);

		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM/dd/yyyy");

		String expirationDateString = com.tcmis.common.util.StringHandler.
		 emptyIfNull(bean.getExpirationDateString());
			if (indefinite.equalsIgnoreCase(expirationDateString)) {
				expirationDateString = "01/01/3000";
				bean.setExpirationDate(null);
				try {
					bean.setExpirationDate(format.parse(expirationDateString));
				} catch(Exception ex){}
			}
			else
			{
				try {
					bean.setExpirationDate(dateParser.parse(expirationDateString));
				} catch(Exception ex){}
			}
		return bean;
	}
*/
  
    public ExcelHandler getExcelReport(DropShipReceivingInputBean bean, Locale locale,PersonnelBean personnelBean ) throws
                BaseException, Exception {
            ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

            Collection<CustDropShipReceivingViewBean> data = getResult(bean,false, personnelBean);
            Iterator iterator = data.iterator();
            ExcelHandler pw = new ExcelHandler(library);
            pw.addTable();
            pw.addRow();
            String[] headerkeys = {
                    "label.mr","label.mrline","label.userpo","label.customer","label.csr","label.requestor","label.deliverto","label.haaspo",
                    "label.poline","label.dateexptd","label.openamt","label.mrqtyopen", "label.packaging",
                    "label.description"
                    };
            int[] widths = {0,0,12,10,7,15,12,12,0,
                            0, 12,0,0,0,0
                            };
            int [] types = {0,0,0,0,0,0,0,0,
                            0,ExcelHandler.TYPE_CALENDAR,0,0,ExcelHandler.TYPE_PARAGRAPH,
                           ExcelHandler.TYPE_PARAGRAPH};
            int[] aligns = {0,0,0,0,0,0,0,0,
                            0,0,0,0,0,0
                            };
            pw.applyColumnHeader(headerkeys, types, widths, aligns)  ;
            while (iterator.hasNext()) {
            CustDropShipReceivingViewBean inputbean = (CustDropShipReceivingViewBean) iterator.next();
            pw.addRow();
            pw.addCell(inputbean.getMrNumber());
            pw.addCell(inputbean.getMrLineItem());
            pw.addCell(inputbean.getCustomerPo());
            pw.addCell(inputbean.getCustomerName());
            pw.addCell(inputbean.getCsrName());
            pw.addCell(inputbean.getRequestorName());
            pw.addCell(inputbean.getShipToLocationId());
            pw.addCell(inputbean.getRadianPo());
            pw.addCell(inputbean.getLineItem());
            pw.addCell(inputbean.getExpected());
            pw.addCell(inputbean.getQtyOpen());
            pw.addCell(inputbean.getMrQtyOpen());
            pw.addCell(inputbean.getPackaging());
            pw.addCell(inputbean.getItemDescription());
        }
           return pw;

        }       
}