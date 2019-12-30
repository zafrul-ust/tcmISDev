package radian.tcmis.server7.share.servlets;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

//ACJEngine
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class WasteManagement extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  //ACJEngine
  ACJEngine erw = null;
  OutputStream os = null;
  TemplateManager tm = null;
  ACJOutputProcessor ec = null;

  public static final String[] mainCols = {"WASTE_ITEM_ID","STATE_WASTE_CODE","ORDER_NUMBER","CONTAINER_ID","DRUM AGE","WEIGHT","FACILITY_ID","WORK_AREA","VENDOR_PROFILE_ID","VENDOR_ID","QTY",
                                           "WASTE_PROFILE_DESC","MGMT_OPTION","MGMT_OPTION_LOCATIOM","PACKAGING",
                                           "WASTE_CATEGORY_ID","WASTE_TYPE_ID","LPP","TRANSFER_DATE","COD","WASTE_REQUEST_ID","LINE_ITEM","SELECTED_ROW","BULK_OR_CONTAINER","SHIPMENT_ID","AGE_WARNING","PREF","SIZE_UNIT","TSDF"};


  public WasteManagement(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
    inData = null;
  }
  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void getResult(){
    mySendObj = new Hashtable();
    // using myRecvObj you get the function the way you want
    inData = (Hashtable)myRecvObj;
    //System.out.println("\n\n inData: "+inData.toString());
    function = inData.get("ACTION").toString();
    if(function.equalsIgnoreCase("SEARCH")) {
      doSearch();
    }else if(function.equalsIgnoreCase("CREATE_ORDER_ID")) {
      createOrderId();
    }else if(function.equalsIgnoreCase("GET_SHIPMENT_LIST")) {
      getShipmentData();
    }else if(function.equalsIgnoreCase("GET_SHIPMENT_INFO_TAB")) {
      getShipmentInfoData();
    }else if(function.equalsIgnoreCase("GET_SHIPMENT_EDIT_INFO")) {
      getShipmentEditInfo();
    }else if(function.equalsIgnoreCase("UPDATE_SHIPMENT_REQUEST")) {
      updateShipmentRequest();
    }else if(function.equalsIgnoreCase("GET_LOCATION_DATA")) {
      loadPickupLocationScreen();
    }else if(function.equalsIgnoreCase("UPDATE")) {
      updatePickupDate();
    }else if(function.equalsIgnoreCase("SUBMIT")) {
      submitShipmentRequest();
    }else if(function.equalsIgnoreCase("RESUBMIT")) {
      reSubmitShipmentRequest();
    }else if(function.equalsIgnoreCase("CANCEL")) {
      cancelShipmentRequest();
    }else if(function.equalsIgnoreCase("GET_INITIAL_INFO")) {
      getInitialInfo();
    }else if(function.equalsIgnoreCase("GET_DRAFT_INVOICE_INFO_TAB")) {
      getDraftInvoiceInfoTab();
    }else if(function.equalsIgnoreCase("UPDATE_SHIPMENT")) {
      updateShipment();
    }else if(function.equalsIgnoreCase("UPDATE_SHIPMENT_INVOICE_DATA")) {
      updateShipmentInvoiceData();
    }else if(function.equalsIgnoreCase("GET_VENDOR_INVOICE_INFO_TAB")) {
      getVendorInvoiceInfoTab();
    }else if(function.equalsIgnoreCase("GET_PROFILE")) {
      getProfile();
    }else if(function.equalsIgnoreCase("CHANGE_PROFILE")) {
      changeProfile();
    }else if(function.equalsIgnoreCase("GET_NEW_CONTAINER_PROFILE")) {
      getNewContainerProfile();
    }else if(function.equalsIgnoreCase("CREATE_NEW_CONTAINER")) {
      createNewContainer();
    }else if(function.equalsIgnoreCase("GET_ACCUMULATION_CONTAINER_PROFILE")) {
      getAccumulationContainerProfile();
    }else if(function.equalsIgnoreCase("UPDATE_EMPTY_INTO")) {
      updateEmptyInto();
    }else if(function.equalsIgnoreCase("SEAL_CONTAINER")) {
      sealContainer();
    }else if(function.equalsIgnoreCase("GET_ADD_INTO_INFO")) {
      getAddIntoInfo();
    }else if(function.equalsIgnoreCase("UPDATE_ADD_INTO")) {
      updateAddInto();
    }else if(function.equalsIgnoreCase("UPDATE_CONTAINER_WEIGHT")) {
      updateContainerWeight();
    }else if(function.equalsIgnoreCase("PRINT_SCREEN")) {
      printScreen();
    }else if(function.equalsIgnoreCase("SUBMIT_LAB_PACK")) {
      submitLabPack();
    }else if(function.equalsIgnoreCase("LOAD_LAB_PACK")) {
      loadLabPack();
    }else if(function.equalsIgnoreCase("CLEAR_LAB_PACK")) {
      clearLabPack();
    }else if(function.equalsIgnoreCase("GET_SURCHARGE_INFO")) {
      getSurchargeInfo();
    }else if(function.equalsIgnoreCase("UPDATE_SURCHARGE")) {
      updateSurcharge();
    }else if(function.equalsIgnoreCase("GET_SHIPMENT_CHARGES_INFO")) {
      getShipmentChargesInfo();
    }else if(function.equalsIgnoreCase("GET_CHARGES_FOR_SHIPMENT")) {
      getChargesForShipment();
    }else if(function.equalsIgnoreCase("UPDATE_SHIPMENT_CHARGES")) {
      updateShipmentCharges();
    }else if(function.equalsIgnoreCase("GET_ADD_LAB_PACK_INFO")) {
      getAddLabPackInfo();
    }else if(function.equalsIgnoreCase("GET_NEW_LAB_PACK_CONTAINER_PROFILE")) {
      getNewLabPackContainerProfile();
    }else if(function.equalsIgnoreCase("UPDATE_ADD_LAB_PACK")) {
      updateAddLabPack();
    }else if(function.equalsIgnoreCase("ALLOW_EDIT")) {
      unApproveInvoice();
      //approveInvoice();
    }else if(function.equalsIgnoreCase("RETRACT_INVOICE")) {
      unApproveInvoice();
    }else if(function.equalsIgnoreCase("APPROVE_INVOICE")) {
      approveInvoice();
    }else if(function.equalsIgnoreCase("GET_REQUESTED_WASTE_MATERIAL")) {
      getRequestedWasteMaterial();
    }else if(function.equalsIgnoreCase("GET_WASTE_MATERIAL_CATALOG")) {
      getWasteMaterialCatalog();
    }else if(function.equalsIgnoreCase("CREATE_WASTE_MATERIAL_REQUEST")) {
      createWasteMaterialRequest();
    }else if(function.equalsIgnoreCase("LOAD_WASTE_MATERIAL_REQUEST")) {
      loadWasteMaterialRequest();
    }else if(function.equalsIgnoreCase("DELETE_WASTE_MATERIAL_ITEM")) {
      deleteWasteMaterialItem();
    }else if(function.equalsIgnoreCase("DELETE_CONTAINER")) {
      deleteContainer();
    }
    //System.out.println("outData:"+mySendObj);
  }

  public void deleteContainer() {
    Vector containerIDs = (Vector)inData.get("CONTAINER_IDS");
    try {
      for (int i = 0; i < containerIDs.size();i++) {
        Vector v = new Vector();
        v.addElement(containerIDs.elementAt(i).toString());
        v.addElement("error_val");
        String val = db.doInvoiceProcedure("p_waste_container_delete",v);
        if (val.startsWith("OK")) {
          //mySendObj.put("SUCCEEDED",new Boolean(true));
          mySendObj.put("RETURN_CODE",new Boolean(true));
          mySendObj.put("MSG","Deleting container(s) done.");
        }else {
          //mySendObj.put("SUCCEEDED",new Boolean(false));
          mySendObj.put("RETURN_CODE",new Boolean(false));
          mySendObj.put("MSG",val);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
      mySendObj.put("MSG","An error occurred while deleting container(s).\nPlease try again.  If the problem re-occurs please contact\ntcmIS Customer Service Representative (CSR).");
    }
  }

  public void loadWasteMaterialRequest() {
    try {
      Integer orderNo = (Integer)inData.get("ORDER_NO");
      WasteOrderItem woi = new WasteOrderItem(db);
      woi.setOrderNo(orderNo.toString());
      Hashtable h = woi.getScreenData();

    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEED",new Boolean(false));
    }
    mySendObj.put("SUCCEED",new Boolean(true));
  }

  public void deleteWasteMaterialItem() {
    try {
      Integer orderNo = (Integer)inData.get("ORDER_NO");
      Vector deleteItemV = (Vector)inData.get("ITEM_IDS");
      for (int i = 0; i < deleteItemV.size(); i++) {
        String itemID = (String)deleteItemV.elementAt(i);
        //first delete item from wi_account
        WiAccount wia = new WiAccount(db);
        wia.setOrderNo(orderNo.toString());
        wia.setItemID(itemID);
        wia.deleteLineItem();
        //then delete item from waste_order_item
        WasteOrderItem woi = new WasteOrderItem(db);
        woi.setOrderNo(orderNo.toString());
        woi.setItemId(itemID);
        woi.deleteLineItem();
      }
      mySendObj.put("RETURN_CODE",new Boolean(true));
      mySendObj.put("MSG","Your request was successfully updated.");
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
      mySendObj.put("MSG","An error occurred while deleting line item.\nPlease try again. If the problem re-occurs please contact\ntcmIS Customer Service Representative (CSR).");
    }

  }

  public void createWasteMaterialRequest() {
    Hashtable h = null;
    try {
      //first delete item(s) that user doesn't want
      Integer orderNo = (Integer)inData.get("ORDER_NO");
      Hashtable lineItemInfo = (Hashtable)inData.get("LINE_ITEM_INFO");
      Vector deleteItemV = (Vector)lineItemInfo.get("DELETE_ITEM_IDS");
      for (int i = 0; i < deleteItemV.size(); i++) {
        String itemID = (String)deleteItemV.elementAt(i);
        //first delete item from wi_account
        WiAccount wia = new WiAccount(db);
        wia.setOrderNo(orderNo.toString());
        wia.setItemID(itemID);
        wia.deleteLineItem();
        //then delete item from waste_order_item
        WasteOrderItem woi = new WasteOrderItem(db);
        woi.setOrderNo(orderNo.toString());
        woi.setItemId(itemID);
        woi.deleteLineItem();
      }
      //next create waste order item
      WasteOrderItem woi = new WasteOrderItem(db);
      h = woi.createWasteOrderItemRequest(inData);
      Boolean b = (Boolean)h.get("SUCCEED");
      if (!b.booleanValue()) {
        mySendObj.put("RETURN_CODE",new Boolean(false));
        mySendObj.put("MSG",(String)h.get("MSG"));
        mySendObj.put("ERROR_LINE",(Integer)h.get("ERROR_LINE"));
      }else {
        mySendObj.put("RETURN_CODE",new Boolean(true));
        mySendObj.put("MSG","Your request was successfully updated.");
        mySendObj.put("LIST_CHANGED",(Boolean)h.get("LIST_CHANGED"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
      mySendObj.put("MSG","An error occurred while updating your request.\nPlease  try again. If the problem re-occurs please contact\ntcmIS Customer Service Representative (CSR).");
      mySendObj.put("ERROR_LINE",new Integer(0));
    }

  }

  public void getWasteMaterialCatalog() {
    final String[] catalogColH = {"Qty","Order","Item ID","Vendor Part No","Description","Packaging","Specification","Price","Pick"};
    final int[] catalogColW = {40,40,60,90,215,105,100,60,0};

    String vendorID = (String)inData.get("VENDOR_ID");
    String facilityID = (String)inData.get("FAC_ID");
    Vector currentItem = (Vector)inData.get("CURRENT_ITEM");

    WasteMaterialRequest wmr = new WasteMaterialRequest(db);
    try {
      mySendObj.put("WASTE_MATERIAL_CATALOG_INFO",wmr.getWasteMaterialCatalog(vendorID,currentItem));
      mySendObj.put("SUCCEEDED",new Boolean(true));
      mySendObj.put("MSG","OK");
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","An error occurred while retrieving requested waste material.");
    }
  }

  public void getRequestedWasteMaterial() {
    //String shipID = (String)inData.get("SHIPMENT_ID");
    Integer orderN = (Integer)inData.get("ORDER_NO");

    WasteMaterialRequest wmr = new WasteMaterialRequest(db);
    try {
      Hashtable h = wmr.getRequestedWasteMaterial(orderN);
      mySendObj.put("REQUESTED_WASTE_MATERIAL_INFO",h);
      mySendObj.put("SUCCEEDED",new Boolean(true));
      mySendObj.put("MSG","OK");
      WasteAddInto wai = new WasteAddInto(db);
      Hashtable headerInfo = (Hashtable)h.get("HEADER_INFO");
      mySendObj.put("GENERATION_PTS",wai.getGenerationPts(BothHelpObjs.makeBlankFromNull((String)headerInfo.get("FACILITY_ID"))));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","An error occurred while retrieving requested waste material.");
    }
  }

  public void unApproveInvoice() {
    //first approve invoice
    //   call procedure here
    Integer shipID = (Integer)inData.get("SHIPMENT_ID");
    try {
      Vector v = new Vector();
      v.addElement(shipID.toString());
      v.addElement("error_val");
      String val = db.doInvoiceProcedure("p_waste_invoice_unapprove",v);
      if (val.startsWith("OK")) {
        String status = WasteCostNInvoice.getStatus(db,shipID.toString());
        mySendObj.put("STATUS",status);
        mySendObj.put("SUCCEEDED",new Boolean(true));
        mySendObj.put("MSG","OK");
      }else {
        mySendObj.put("SUCCEEDED",new Boolean(false));
        mySendObj.put("MSG",val);
      }

      //next load invoice data
      getVendorInvoiceInfoTab();

    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS unable to unapprove invoice.\nPlease  try again.\nIf the problem re-occurs please contact\ntcmIS Customer Service Representative (CSR).");
    }
  }

  public void approveInvoice() {
    //first approve invoice
    //   call procedure here
    Integer shipID = (Integer)inData.get("SHIPMENT_ID");
    try {
      Vector v = new Vector();
      v.addElement(shipID.toString());
      v.addElement("error_val");
      //db.doInvoiceProcedure("p_waste_invoice_unapprove",v);
      String val = db.doInvoiceProcedure("p_waste_invoice_approve",v);
      if (val.startsWith("OK")) {
        String status = WasteCostNInvoice.getStatus(db,shipID.toString());
        mySendObj.put("STATUS",status);
        mySendObj.put("SUCCEEDED",new Boolean(true));
        mySendObj.put("MSG","Approving invoice done.");
      }else {
        mySendObj.put("SUCCEEDED",new Boolean(false));
        mySendObj.put("MSG",val);
      }

      //next load invoice data
      getVendorInvoiceInfoTab();

    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS unable to approve invoice.\nPlease  try again.\nIf the problem re-occurs please contact\ntcmIS Customer Service Representative (CSR).");
    }
  }
  public void updateShipmentCharges() {
    Integer myShipmentId = (Integer)inData.get("SHIPMENT_ID");
    Vector updateData = (Vector)inData.get("UPDATE_VECTOR");
    WasteShipmentCharges wsc = new WasteShipmentCharges(db);
    try {
      if (wsc.updateShipmentCharges(myShipmentId.intValue(),updateData)) {
        mySendObj.put("SUCCEEDED",new Boolean(true));
        mySendObj.put("MSG","Updating shipment charges completed.");
      }else {
        mySendObj.put("SUCCEEDED",new Boolean(false));
        mySendObj.put("MSG","Error: tcmIS unable to update shipment charges.\nPlease contact tcmIS Customer Service Representative (CSR).");
      }
    }catch (Exception eee) {
      eee.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS unable to update shipment charges.\nPlease contact tcmIS Customer Service Representative (CSR).");
    }
  }
  public void getChargesForShipment() {
    Integer myShipmentId = (Integer)inData.get("SHIPMENT_ID");
    WasteShipmentCharges wsc = new WasteShipmentCharges(db);
    try {
      mySendObj.put("SHIPMENT_CHARGES_INFO",wsc.doSearch(myShipmentId.intValue()));
      mySendObj.put("SUCCEEDED",new Boolean(true));
      mySendObj.put("MSG","Loading shipment charges completed.");
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception eee) {
      eee.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS unable to load shipment charges information.\nPlease contact tcmIS Customer Service Representative (CSR).");
    }
  }
  public void getShipmentChargesInfo() {
    Integer myOrderNo = (Integer)inData.get("ORDER_NO");
    WasteShipmentCharges wsc = new WasteShipmentCharges(db);
    wsc.setOrderNo(myOrderNo.intValue());
    try {
      mySendObj.put("SHIPMENT_CHARGES_INFO",wsc.getShipmentChargesInfo());
      mySendObj.put("SUCCEEDED",new Boolean(true));
      mySendObj.put("MSG","Loading shipment charges completed.");
    }catch (Exception eee) {
      eee.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS unable to load shipment charges information.\nPlease contact tcmIS Customer Service Representative (CSR).");
    }
  }

  public void updateSurcharge() {
    Integer myShipId = (Integer)inData.get("SHIPMENT_ID");
    WasteSurcharge ws = new WasteSurcharge(db);
    ws.setShipmentId(myShipId.intValue());
    try {
      boolean done = ws.updateSurchargeInfo((Vector)inData.get("UPDATE_VECTOR"));
      if (done) {
        mySendObj.put("SUCCEEDED",new Boolean(true));
        mySendObj.put("MSG","Update surcharge completed.");
      }else {
        mySendObj.put("SUCCEEDED",new Boolean(false));
        mySendObj.put("MSG","Error: tcmIS unable to update surcharge information.\nPlease contact tcmIS Customer Service Representative (CSR).");
      }
    }catch (Exception eee) {
      eee.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS unable to update surcharge information.\nPlease contact tcmIS Customer Service Representative (CSR).");
    }
  }

  public void getSurchargeInfo() {
    Integer myShipId = (Integer)inData.get("SHIPMENT_ID");
    WasteSurcharge ws = new WasteSurcharge(db);
    ws.setShipmentId(myShipId.intValue());
    try {
      mySendObj.put("SURCHARGE_INFO",ws.getSurchargeInfo());
      mySendObj.put("SUCCEEDED",new Boolean(true));
      mySendObj.put("MSG","Loading surcharge completed.");
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception eee) {
      eee.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS unable to load surcharge information.\nPlease contact tcmIS Customer Service Representative (CSR).");
    }
  }

  public void clearLabPack() {
    //System.out.println("\n\n========== submit lab pack: "+inData);
    String facilityId = (String)inData.get("FACILITY_ID");
    String vendorId = (String)inData.get("VENDOR");
    String storageArea = (String)inData.get("STORAGE_AREA");
    try {
      WasteLabPackRequest wlpr = new WasteLabPackRequest(db);
      wlpr.setFacilityId(facilityId);
      wlpr.setVendorId(vendorId);
      wlpr.setStorageArea(storageArea);
      wlpr.delete();
      mySendObj.put("SUCCEEDED",new Boolean(true));
      mySendObj.put("MSG","Lab Pack request cleared.");
    } catch (Exception e){
      e.printStackTrace();
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MSG","Error: tcmIS clear Lab Pack information failed.\nPlease contact tcmIS Customer Service Representative (CSR).");
    } finally {
    }

  }

  public void loadLabPack() {
    //System.out.println("\n\n========== submit lab pack: "+inData);
    String facilityId = (String)inData.get("FACILITY_ID");
    String vendorId = (String)inData.get("VENDOR");
    String storageArea = (String)inData.get("STORAGE_AREA");
    try {
      WasteLabPackRequest wlpr = new WasteLabPackRequest(db);
      wlpr.setFacilityId(facilityId);
      wlpr.setVendorId(vendorId);
      wlpr.setStorageArea(storageArea);
      mySendObj.put("SEARCH_DATA",wlpr.getLabPackOrders());
      mySendObj.put("SUCCEEDED",new Boolean(true));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    } catch (Exception e){
        e.printStackTrace();
        mySendObj.put("SUCCEEDED",new Boolean(false));
        mySendObj.put("MSG","Error: tcmIS load Lab Pack information failed.\nPlease contact tcmIS Customer Service Representative (CSR).");
    } finally {
    }

  }

  public void submitLabPack() {
    //System.out.println("\n\n========== submit lab pack: "+inData);
    String drums = (String)inData.get("DRUMS");
    String psd = BothHelpObjs.makeBlankFromNull((String)inData.get("PREFERRED_SERVICE_DATE"));
    String actionText = (String)inData.get("ACTION_TEXT");
    String facilityId = (String)inData.get("FACILITY_ID");
    String vendorId = (String)inData.get("VENDOR");
    String storageArea = (String)inData.get("STORAGE_AREA");
    Integer userId = (Integer)inData.get("USER_ID");
    //String orderN = (String)inData.get("ORDER_NO");
    Calendar cal = Calendar.getInstance();
    String currentDate = BothHelpObjs.formatDate("toCharfromNum",new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR)));

    try {
      WasteLabPackRequest wlpr = new WasteLabPackRequest(db);
      wlpr.setFacilityId(facilityId);
      wlpr.setDrumEst(drums);
      wlpr.setPreferredServiceDate(psd);
      wlpr.setVendorId(vendorId);
      wlpr.setStorageArea(storageArea);
      //String msg = wlpr.submitLabPackRequest(actionText,userId,orderN);
      String msg = wlpr.submitLabPackRequest(actionText,userId);
      if (msg.length() > 0) {
        mySendObj.put("SUCCEEDED",new Boolean(true));
        mySendObj.put("MSG","Your Lab Pack request was successfully sent to your vendor.");
        mySendObj.put("REQUESTED_ON",currentDate);
        Personnel p = new Personnel(db);
        p.setPersonnelId(userId.intValue());
        p.load();
        mySendObj.put("LAST_REQUESTOR",p.getFullName());
      }else {
        mySendObj.put("SUCCEEDED",new Boolean(false));
        mySendObj.put("MSG","Error: tcmIS unable to send your Lab Pack request to vendor.\nPlease contact tcmIS Customer Service Representative (CSR).");
      }
    } catch (Exception e){
        e.printStackTrace();
        mySendObj.put("SUCCEEDED",new Boolean(false));
        mySendObj.put("MSG","Error: tcmIS unable to send your Lab Pack request to vendor.\nPlease contact tcmIS Customer Service Representative (CSR).");
    } finally {
    }

  }

  public void printScreen() {
    Hashtable headerInfo = (Hashtable)inData.get("HEADER_INFO");
    Hashtable containerInfo = (Hashtable)inData.get("CONTAINER_INFO");
    Vector bulkInfo = (Vector)inData.get("BULK_INFO");
    //System.out.println("\n\n--------- header: "+headerInfo);
    //System.out.println("\n\n--------- container: "+containerInfo);
    //System.out.println("\n\n--------- bulk: "+bulkInfo);
    //String url = "";
    String url = buildReportUrl(headerInfo,containerInfo,bulkInfo);
    Hashtable retH = new Hashtable();
    if (url.length() > 0) {
      retH.put("SUCCEEDED",new Boolean(true));
    }else {
      retH.put("SUCCEEDED",new Boolean(false));
    }
    retH.put("MSG"," Generating report failed.\n Please try again.\n If the problem recurs, contact tcmIS Customer Service Representative (CSR).");
    mySendObj.put("RETURN_CODE",retH);
    mySendObj.put("URL",url);
  }
  protected String buildReportUrl(Hashtable headerInfo, Hashtable containerInfo, Vector bulkInfo) {
    String url = "";
    /*
    erw = new ACJEngine();
    erw.setDebugMode(true);
    erw.setX11GfxAvailibility(false);
    erw.setTargetOutputDevice(ACJConstants.PDF);
    ec = new ACJOutputProcessor();
    try {
      erw.readTemplate("/home/servlet/radian/web/erw/printScreen/shipmentInfo.erw");
    }catch (Exception e) {
      System.out.println("ERROR loading template");
      e.printStackTrace();
      return url;
    }

    AppDataHandler ds = new AppDataHandler();
    tm = erw.getTemplateManager();

    String wasteOrder = (String)headerInfo.get("WASTE_ORDER");
    //Header info
    tm.setLabel("LAB007",(String)headerInfo.get("VENDOR"));
    tm.setLabel("LAB008",(String)headerInfo.get("WASTE_ORDER"));
    tm.setLabel("LAB009",(String)headerInfo.get("REQUESTER"));
    tm.setLabel("LAB010",(String)headerInfo.get("STORAGE_AREA"));
    tm.setLabel("LAB011",(String)headerInfo.get("SUBMITTED"));
    tm.setLabel("LAB012",(String)headerInfo.get("RESUBMITTED"));

    //Container info
    tm.setLabel("LAB037",(String)containerInfo.get("SHIPMENT_ID"));
    tm.setLabel("LAB038",(String)containerInfo.get("VENDOR_ORDER_NO"));
    tm.setLabel("LAB041",(String)containerInfo.get("PLANNED_PICKUP_DATE"));
    tm.setLabel("LAB044",(String)containerInfo.get("ACTUAL_PICKUP_DATE"));
    tm.setLabel("LAB045",(String)containerInfo.get("MANIFEST_NO"));
    tm.setLabel("LAB046",(String)containerInfo.get("STATE"));
    tm.setLabel("LAB047",(String)containerInfo.get("COUNTRY"));
    tm.setLabel("LAB048",(String)containerInfo.get("COPY_RETURNED_DATE"));

    Vector manifestL = (Vector)containerInfo.get("MANIFEST_LINE_INFO");

    RegisterTable[] rt = new RegisterTable[2];
    rt[0] = new RegisterTable(BulkInfo.getVector(bulkInfo),"BULKINFO",
                              BulkInfo.getFieldVector(),null);
    rt[1] = new RegisterTable(ManifestLine.getVector(manifestL),"MANIFESTLINE",
                              ManifestLine.getFieldVector(),null);
    for (int k = 0; k <rt.length; k++) {
      Vector v1 = rt[k].getData();
      Vector v2 = rt[k].getMethods();
      String name = rt[k].getName();
      String where = rt[k].getWhere();
      //System.out.println("got here haha v2 "+v2+" - "+name+" - "+where);
      ds.RegisterTable(v1,name, v2,where);
    }
    try {
      erw.setDataSource(ds);
    }catch (Exception e) {
      System.out.println("ERROR setting data source");
      e.printStackTrace();
      return url;
    }
    /*
    try {
      IViewerInterface ivi = erw.generateReport();
      if (!ec.setReportData(ivi,null)) System.exit(0);
    } catch(Exception ex){
      System.out.println("ERROR: While generating report");
      return url;
    }/
    try {
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.setPDFFontMap("Serif",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
      ec.setPDFFontMap("lucidasans",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
      ec.setPDFFontMap("SansSerif",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
      ec.setPDFFontMap("Dialog",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
      ec.generatePDF("/home/httpd/htdocs/reports/shipmentInfo-"+wasteOrder+".pdf",null);
    }catch (Exception e) {
      System.out.println("ERROR generating report");
      e.printStackTrace();
      return url;
    }
    */ //uncomment when find out what to do
    return "";
    //return("/reports/shipmentInfo-"+wasteOrder+".pdf");
  }


  //10-3
  public void updateContainerWeight() {
    Vector containerIDV = (Vector)inData.get("CONTAINER_IDS");
    Vector containerWeightV = (Vector)inData.get("WEIGHT");

    for (int i = 0; i < containerIDV.size(); i++) {
      Integer containerId = new Integer(containerIDV.elementAt(i).toString());
      String tempWeight = (String)containerWeightV.elementAt(i);
      if (tempWeight.length() < 1 || tempWeight.equalsIgnoreCase("tbd")) {
        continue;
      }else {
          try {
            WasteContainer wc = new WasteContainer(db);
            wc.setContainerId(containerId.intValue());
            wc.insert("weight",tempWeight,WasteContainer.STRING);
            //wc.insert("weight",containerWeightV.elementAt(i).toString(),WasteContainer.STRING);
            mySendObj.put("RETURN_CODE",new Boolean(true));
            mySendObj.put("MSG","Container(s) weight was successfully changed.");
          }catch (Exception e) {
            e.printStackTrace();
            mySendObj.put("RETURN_CODE",new Boolean(false));
            mySendObj.put("MSG","Error. Changing container(s) weight failed.");
            break;
        }
      }
    }

  }

  public void updateAddInto() {
    String facilityId = (String)inData.get("FACILITY_ID");
    String generationPt = (String)inData.get("GENERATION_POINT");
    String actSysId = (String)inData.get("ACCOUNT_SYS");
    String chargeType = (String)inData.get("CHARGE_TYPE");
    Integer toContainerId = (Integer)inData.get("TO_CONTAINER_ID");
    Integer amount = (Integer)inData.get("AMOUNT");
    Integer userId = (Integer)inData.get("USER_ID");
    String needPo = (String)inData.get("PO_REQUIRED");
    String prRequired = (String)inData.get("PR_ACCOUNT_REQUIRED");
    Hashtable chargeH = null;
    Hashtable creditCardInfo = null;
    if (prRequired.equalsIgnoreCase("y")) {
      chargeH = (Hashtable)inData.get("CHARGE_NUMBER");
    }
    if (needPo.equalsIgnoreCase("c")) {
      creditCardInfo = (Hashtable)inData.get("CREDIT_CARD_INFO");
    }

    try {
      //11-05-01
      Boolean b = new Boolean(true);
      String msg = "";
      //check charge number if charge number is required
      if (prRequired.equalsIgnoreCase("y")) {
        Hashtable checkCharge = WasteAddInto.checkChargeNumber(db,facilityId,actSysId,chargeType,chargeH);
        b = (Boolean)checkCharge.get("RETURN_CODE");
        msg = (String)checkCharge.get("MSG");
      }
      if (b.booleanValue()) {
        //creating waste mgmt request, waste request line item, and waste container
        WasteRequest wr = new WasteRequest(db);
        int requestId = wr.createAddIntoRequest(userId.intValue(),facilityId,actSysId,generationPt);

        //11-05-01 updating credit card info
        if (needPo.equalsIgnoreCase("c")) {
          wr.setWasteRequestId(requestId);
          wr.insertCreditCardInfo(creditCardInfo);
        }

        //update waste request line item charge type
        WasteRequestLineItem wrli = new WasteRequestLineItem(db);
        wrli.setWasteRequestId(requestId);
        wrli.setLineItem(1);
        wrli.insert("charge_type",chargeType,WasteRequestLineItem.STRING);
        Boolean bb = new Boolean(true);
        String msga = "";
        if (prRequired.equalsIgnoreCase("y")) {
          //adding charge info into wr account
          Hashtable ac = WasteAddInto.insertChargeNumber(db,requestId,1,chargeType,chargeH);
          bb = (Boolean)ac.get("RETURN_CODE");
          msga = (String)ac.get("MSG");
        }
        if (bb.booleanValue()) {
          //adding info into waste accumulation
          WasteContainer wc = new WasteContainer(db);
          Vector containerIds = wc.getContainerIdTable(requestId,1);
          Integer cId = (Integer)containerIds.firstElement();
          WasteAccumulation wa = new WasteAccumulation(db);
          wa.setAccumulationId(toContainerId.intValue());
          wa.setContianerId(cId.intValue());
          wa.setAmount(amount.intValue());
          wa.createWasteAccumulation();
          mySendObj.put("RETURN_CODE",new Boolean(true));
          mySendObj.put("MSG","Your request was successfully updated.");
        }else {
          mySendObj.put("RETURN_CODE",new Boolean(true));
          mySendObj.put("MSG",msga);
        }
      }else {
        mySendObj.put("RETURN_CODE",new Boolean(false));
        mySendObj.put("MSG",msg);
      }
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
      mySendObj.put("MSG","Error. Your 'Add Waste' request failed.");
    }
  }

  public void getAddIntoInfo() {
    String facilityId = (String)inData.get("FAC_ID");
    Integer toContainerId = (Integer)inData.get("TO_CONTAINER_ID");
    try {
      WasteAddInto wai =  new WasteAddInto(db);
      wai.setFacilityId(facilityId);
      mySendObj.put("ADD_INTO_INFO",wai.getAddIntoInfo(facilityId));
      mySendObj.put("GENERATION_PTS",wai.getGenerationStoragePts(facilityId));
      mySendObj.put("CURRENT_AMOUNT",wai.getCurrentAmount(toContainerId));
      Hashtable tmpH = new Hashtable();
      tmpH.put("PROFILE_ID",new Integer(0));
      tmpH.put("PROFILE_DESC",new Integer(1));
      tmpH.put("CONTAINER_ID",new Integer(3));
      mySendObj.put("KEY_COLS",tmpH);
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getAddLabPackInfo() {
    String facilityId = (String)inData.get("FAC_ID");
    try {
      //Integer userId = (Integer)inData.get("USER_ID");
      WasteAddInto wai =  new WasteAddInto(db);
      wai.setFacilityId(facilityId);                    //3-13-01
      Hashtable h = wai.getAddIntoInfo(facilityId);
      mySendObj.put("ADD_LAB_PACK_INFO",h);

      //System.out.println("\n\n----- McKinney: "+wai.getAddIntoInfo("McKinney")); //checking to see output structure

      Hashtable generateH = wai.getGenerationPts(facilityId);
      mySendObj.put("GENERATION_PTS",generateH);

      Hashtable tmpH = new Hashtable();
      tmpH.put("PROFILE_ID",new Integer(0));
      tmpH.put("PROFILE_DESC",new Integer(1));
      mySendObj.put("KEY_COLS",tmpH);
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      //System.out.println("----------- lab pack info: "+mySendObj);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sealContainer() {
    try {
      int containerId = Integer.parseInt((String)inData.get("CONTAINER_ID"));
      //first make sure that container is not empty, if empty don't seal container
      if (HelpObjs.countQuery(db,"select count(*) from waste_accumulation where accumulation_id = "+containerId) < 1) {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("MSG", "This accumulation container is currently empty.\nPlease add waste to it before closing.");
      }else {
        WasteContainer wc = new WasteContainer(db);
        wc.setContainerId(containerId);
        wc.load();
        int wrId = wc.getWasteRequestId();
        int line = wc.getLineItem();
        WasteRequestLineItem wrli = new WasteRequestLineItem(db);
        wrli.setWasteRequestId(wrId);
        wrli.setLineItem(line);
        wrli.insert("seal_date", "nowDate", WasteRequestLineItem.DATE);
        mySendObj.put("RETURN_CODE", new Boolean(true));
        mySendObj.put("MSG", "Your request was successfully updated.");
      }
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
      mySendObj.put("MSG","Error. Your 'Seal Container' request failed.");
    }
  }

  public void updateEmptyInto() {
    Integer fromContainerId = (Integer)inData.get("FROM_CONTAINER_ID");
    Integer toContainerId = (Integer)inData.get("TO_CONTAINER_ID");
    Integer amount = (Integer)inData.get("AMOUNT");
    try {
      WasteAccumulation wa = new WasteAccumulation(db);
      wa.setAccumulationId(toContainerId.intValue());
      wa.setContianerId(fromContainerId.intValue());
      wa.setAmount(amount.intValue());
      wa.createWasteAccumulation();
      mySendObj.put("RETURN_CODE",new Boolean(true));
      mySendObj.put("MSG","Your request was successfully updated.");
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
      mySendObj.put("MSG","Error. Your 'empty into' request failed.");
    }
  }


  public void getAccumulationContainerProfile() {
    String facilityId = (String)inData.get("FAC_ID");
    String storageLocation = (String)inData.get("STORAGE_LOCATION");
    String vendorId = (String)inData.get("VENDOR_ID");
    String vendorProfileId = (String)inData.get("VENDOR_PROFILE_ID");
    try {
      mySendObj.put("CONTAINER_PROFILE_INFO",WasteEmptyInto.getAccumulationContainerProfile(db,facilityId,storageLocation,vendorId,vendorProfileId));
      Hashtable tmpH = new Hashtable();
      tmpH.put("PROFILE_ID",new Integer(0));
      tmpH.put("PROFILE_DESC",new Integer(1));
      tmpH.put("CONTAINER_ID",new Integer(3));
      tmpH.put("SIZE_UNIT",new Integer(4));
      mySendObj.put("KEY_COLS",tmpH);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getNewContainerProfile() {
    String facilityId = (String)inData.get("FAC_ID");
    String vendorId = (String)inData.get("VENDOR_ID");
    try {
      mySendObj.put("NEW_CONTAINER_PROFILE_INFO",WasteNewContainer.getNewContainerProfile(db,facilityId,vendorId));
      WastePickupRequestScreen wprs = new WastePickupRequestScreen(db);
      mySendObj.put("SHOW_WASTE_TAG",new Boolean(wprs.showWasteTag(0,facilityId)));
      Hashtable tmpH = new Hashtable();
      tmpH.put("STATE_WASTE_CODES",new Integer(0));
      tmpH.put("PROFILE_ID",new Integer(1));
      tmpH.put("PROFILE_DESC",new Integer(2));
      tmpH.put("PACKAGING",new Integer(3));
      tmpH.put("WASTE_ITEM_ID",new Integer(4));
      mySendObj.put("KEY_COLS",tmpH);
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getNewLabPackContainerProfile() {
    String facilityId = (String)inData.get("FACILITY_ID");
    String vendorId = (String)inData.get("VENDOR_ID");
    String orderNo = (String)inData.get("ORDER_NO");
    try {
      mySendObj.put("NEW_LAB_PACK_CONTAINER_PROFILE_INFO",WasteNewContainer.getNewLabPackContainerProfile(db,orderNo,vendorId,facilityId));
      Hashtable tmpH = new Hashtable();
      tmpH.put("STATE_WASTE_CODES",new Integer(0));
      tmpH.put("PROFILE_ID",new Integer(1));
      tmpH.put("PROFILE_DESC",new Integer(2));
      tmpH.put("PACKAGING",new Integer(3));
      tmpH.put("WASTE_ITEM_ID",new Integer(4));
      mySendObj.put("KEY_COLS",tmpH);
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void updateAddLabPack() {
    String facilityId = (String)inData.get("FACILITY_ID");
    String generationPt = (String)inData.get("GENERATION_POINT");
    String actSysId = (String)inData.get("ACCOUNT_SYS");
    String chargeType = (String)inData.get("CHARGE_TYPE");
    Integer toContainerId = (Integer)inData.get("TO_CONTAINER_ID");
    Integer amount = (Integer)inData.get("AMOUNT");
    Integer userId = (Integer)inData.get("USER_ID");
    String orderNo = (String)inData.get("ORDER_NO");
    String wasteItemID = (String)inData.get("WASTE_ITEM_ID");
    String needPo = (String)inData.get("PO_REQUIRED");
    String prRequired = (String)inData.get("PR_ACCOUNT_REQUIRED");
    Hashtable chargeH = null;
    Hashtable creditCardInfo = null;
    if (prRequired.equalsIgnoreCase("y")) {
      chargeH = (Hashtable)inData.get("CHARGE_NUMBER");
    }
    if (needPo.equalsIgnoreCase("c")) {
      creditCardInfo = (Hashtable)inData.get("CREDIT_CARD_INFO");
    }

    //4-23-01
    String shipId = (String)inData.get("SHIPMENT_ID");
    Integer shipmentId = new Integer(shipId);

    try {
      //11-05-01
      Boolean b = new Boolean(true);
      String msg = "";
      //check charge number if charge number is required
      if (prRequired.equalsIgnoreCase("y")) {
        Hashtable checkCharge = WasteAddInto.checkChargeNumber(db,facilityId,actSysId,chargeType,chargeH);
        b = (Boolean)checkCharge.get("RETURN_CODE");
        msg = (String)checkCharge.get("MSG");
      }
      if (b.booleanValue()) {
        //creating waste mgmt request, waste request line item, and waste container
        WasteRequest wr = new WasteRequest(db);
        int requestId = wr.createLabPackRequest(userId.intValue(),facilityId,actSysId,generationPt,amount.intValue(),wasteItemID);

        //update waste request line item charge type
        WasteRequestLineItem wrli = new WasteRequestLineItem(db);
        wrli.setWasteRequestId(requestId);
        wrli.setLineItem(1);
        wrli.insert("charge_type",chargeType,WasteRequestLineItem.STRING);
        wrli.insert("seal_date","nowDate",WasteRequestLineItem.DATE);

        //11-05-01 updating credit card info
        if (needPo.equalsIgnoreCase("c")) {
          wr.setWasteRequestId(requestId);
          wr.insertCreditCardInfo(creditCardInfo);
        }else if ("p".equalsIgnoreCase(needPo)) {
          String temp = (String)inData.get("PO_NUMBER");
          if (temp != null) {
            if (temp.length() > 1) {
              wrli.insert("po_number",temp,WasteRequestLineItem.STRING);
            }else {
              HelpObjs.sendMail(db,"PO is blank for waste request: "+requestId,"PO is blank when it is suppose to be something.",86030,false);
            }
          }else {
            HelpObjs.sendMail(db,"PO is null for waste request: "+requestId,"PO is blank when it is suppose to be something.",86030,false);
          }
        }

        //adding charge info into wr account
        Boolean bb = new Boolean(true);
        String msga = "";
        if (prRequired.equalsIgnoreCase("y")) {
          //adding charge info into wr account
          Hashtable ac = WasteAddInto.insertChargeNumber(db,requestId,1,chargeType,chargeH);
          bb = (Boolean)ac.get("RETURN_CODE");
          msga = (String)ac.get("MSG");
        }
        if (bb.booleanValue()) {
          //put order_no, shipment_id into waste_container
          WasteContainer wc = new WasteContainer(db);

          //4-23-01
          if (shipmentId.intValue() == 0) {
            Integer currentOrder = new Integer(orderNo);
            WasteShipment ws = WasteShipment.insertWasteShipment(db,currentOrder.intValue());
            shipmentId = new Integer(ws.getWasteShipmentId());
          }
          wc.insertOrderShipment(orderNo,requestId,shipmentId.intValue());   //4-23-01
          wc.updateCurrentLocationId(orderNo,requestId); //4-29-01

          //preset manifest line letter
          //first remove manifest_line_letter from waste_container
          wc.removeManifestLineLetter(orderNo);
          //next delete manifest_line_item for a given order
          Integer myOrder = new Integer(orderNo);
          WasteManifest.deleteManifestShipment(db,myOrder.intValue());
          WasteManifest.setLabPackManifestLine(db,myOrder.intValue());

          mySendObj.put("RETURN_CODE",new Boolean(true));
          mySendObj.put("MSG","Your request was successfully updated.");
        }else {
          mySendObj.put("RETURN_CODE",new Boolean(true));
          mySendObj.put("MSG",msga);
        }
      }else {
        mySendObj.put("RETURN_CODE",new Boolean(false));
        mySendObj.put("MSG",msg);
      }
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
      mySendObj.put("MSG","Error occurred while creating lab pack container(s).\nPlease try again. If problem re-occurs please contact\ntcmIS staff directly: 512-519-3918 for help.");
    }
  }


  public void createNewContainer() {
    try {
      String facilityId = (String)inData.get("FAC_ID");
      String storageArea = (String)inData.get("STORAGE_AREA");
      int wasteItemId = Integer.parseInt(inData.get("WASTE_ITEM_ID").toString());
      int userId = Integer.parseInt(inData.get("USER_ID").toString());
      String accumulationDate = "";
      if (inData.containsKey("ACCUMULATION_DATE")) {
        accumulationDate = BothHelpObjs.makeBlankFromNull(inData.get("ACCUMULATION_DATE").toString());
      }
      WasteRequest wr = new WasteRequest(db);
      wr.setAccumulationStartDate(accumulationDate);
      if (inData.containsKey("WASTE_TAG")) {
        wr.setWasteTag((String)inData.get("WASTE_TAG"));
      }
      String msg = wr.createAccumulationRequest(userId,facilityId,wasteItemId,storageArea);
      if (msg.startsWith("ERROR")) {
        mySendObj.put("RETURN_CODE",new Boolean(false));
      }else {
        mySendObj.put("RETURN_CODE",new Boolean(true));
      }
      mySendObj.put("MSG",msg);
    }catch(Exception e){
      e.printStackTrace();
    }
  }


  public void getProfile() {
    String facilityId = (String)inData.get("FAC_ID");
    String packaging = (String)inData.get("PACKAGING");
    Boolean changeTsdfProfile = (Boolean)inData.get("CHANGE_TSDF_PROFILE");
    try {
      /*
      if (changeTsdfProfile.booleanValue()) {
        String containerId = (String)inData.get("CONTAINER_ID");
        mySendObj.put("PROFILE_INFO", WasteChangeProfile.getTsdfProfile(db, containerId));
      }else {
        mySendObj.put("PROFILE_INFO", WasteChangeProfile.getProfile(db, facilityId, packaging));
      }
      */
      mySendObj.put("PROFILE_INFO", WasteChangeProfile.getProfile(db, facilityId, packaging));
      Hashtable tmpH = new Hashtable();
      tmpH.put("PROFILE_ID",new Integer(0));
      tmpH.put("PROFILE_DESC",new Integer(1));
      tmpH.put("WASTE_ITEM_ID",new Integer(2));
      mySendObj.put("KEY_COLS",tmpH);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void changeProfile() {
    String containerId = (String)inData.get("CONTAINER_ID");
    String wasteItemId = (String)inData.get("WASTE_ITEM_ID");
    try {
      WasteChangeProfile wcp = new WasteChangeProfile(db);
      String msg = wcp.changeProfile(containerId,wasteItemId);
      if (msg.startsWith("Can't")) {
        mySendObj.put("RETURN_CODE",new Boolean(false));
      }else {
        mySendObj.put("RETURN_CODE",new Boolean(true));
      }
      mySendObj.put("MSG",msg);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getVendorInvoiceInfoTab() {
    Integer uid = (Integer)inData.get("MANAGER_ID");
    Integer orderNo = (Integer)inData.get("SHIPMENT_NUM");
    try {
      mySendObj.put("VENDOR_INVOICE_DATA",WasteCostNInvoice.getVendorInvoiceInfoTab(db,orderNo.intValue(),uid.intValue()));
      mySendObj.put("EXTENDED_COL",new Integer(3));
      mySendObj.put("TOTAL_COL",new Integer(10));
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());

      //System.out.println("\n\n========== invoice tab info: "+mySendObj+"\n\n");

    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  //can't uncomment til run with actual test data
  public void updateShipmentInvoiceData(){
    Hashtable h = (Hashtable)inData.get("SHIPMENT_INVOICE_UPDATE_INFO");
    Vector v = (Vector)inData.get("SHIPMENT_ID");
    WasteShipment ws = new WasteShipment(db);
    try {
      for (int i = 0; i < v.size(); i++) {
        String shipmentId = (String)v.elementAt(i);
        if (!h.containsKey(shipmentId)) {
          continue;
        }

        Integer sid = new Integer(shipmentId);
        //first delete all invoice for this shipment_id
        String ok = WasteCostNInvoice.deleteVendorInvoice(db,sid.intValue());

        Hashtable innerH = (Hashtable)h.get(shipmentId);
        Vector vendorInvoice = (Vector)innerH.get("VENDOR_INVOICE");
        for (int j = 0; j < vendorInvoice.size(); j++) {
          Hashtable hh = (Hashtable)vendorInvoice.elementAt(j);
          String invoice = (String)hh.get("INVOICE");
          String date = (String)hh.get("DATE");
          String amount = (String)hh.get("AMOUNT");
          String received = (String)hh.get("RECEIVED");

          if (BothHelpObjs.isBlankString(invoice)) {
            continue;
          }

          if (ok.equalsIgnoreCase("OK")) {
            String ok2 = WasteCostNInvoice.insertVendorInvoice(db,sid.intValue(),invoice);
            if (ok2.equalsIgnoreCase("OK")) {
              if (date.length() > 0) {
                WasteCostNInvoice.insert(db,"vendor_invoice_date",date,WasteCostNInvoice.DATE,sid.intValue(),invoice);
              }
              if (amount.length() > 0) {
                WasteCostNInvoice.insert(db,"vendor_invoice_amount",amount,WasteCostNInvoice.STRING,sid.intValue(),invoice);
              }
              if (received.length() > 0) {
                WasteCostNInvoice.insert(db,"received",received,WasteCostNInvoice.DATE,sid.intValue(),invoice);
              }
              String msg = new String("Your request was succeesfully updated.");
              mySendObj.put("SUCCEEDED",new Boolean(true));
              mySendObj.put("MESSAGE",msg);
            }else {
              String msg = new String("ERROR: Inserting data into waste vendor invoice failed.");
              mySendObj.put("SUCCEEDED",new Boolean(false));
              mySendObj.put("MESSAGE",msg);
            }
          }else {
            String msg = new String("ERROR: Updating waste vendor invoice failed.");
            mySendObj.put("SUCCEEDED",new Boolean(false));
            mySendObj.put("MESSAGE",msg);
          }
        }

        //now update wasteshipment
        String vendorShipmentID = (String)innerH.get("VENDOR_SHIPMENT_ID");
        if (vendorShipmentID.length() > 0 ) {
          ws.setWasteShipmentId(sid.intValue());
          ws.insert("vendor_shipment_id",vendorShipmentID,WasteShipment.STRING);
        }
        String msg = new String("Your request was succeesfully updated.");
        mySendObj.put("SUCCEEDED",new Boolean(true));
        mySendObj.put("MESSAGE",msg);
      }
    }catch (Exception e) {
      e.printStackTrace();
      String msg = new String("ERROR: Unable to update your request.");
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MESSAGE",msg);
    }


  }
  /*
  public void updateShipmentInvoiceData(){
    Hashtable h = (Hashtable)inData.get("SHIPMENT_INVOICE_UPDATE_INFO");
    Vector v = (Vector)inData.get("SHIPMENT_ID");
    WasteShipment ws = new WasteShipment(db);
    try {
      for (int i = 0; i < v.size(); i++) {
        String shipmentId = (String)v.elementAt(i);
        if (!h.containsKey(shipmentId)) {
          continue;
        }
        Hashtable innerH = (Hashtable)h.get(shipmentId);
        String invoice = (String)innerH.get("INVOICE");
        String date = (String)innerH.get("DATE");
        Integer sid = new Integer(shipmentId);
        ws.setWasteShipmentId(sid.intValue());
        ws.insert("vendor_invoice_number",invoice,WasteShipment.STRING);
        ws.insert("vendor_invoice_date",date,WasteShipment.DATE);
      }
    }catch (Exception e) {
      e.printStackTrace();
      String msg = new String("ERROR: Unable to update your request.");
      mySendObj.put("SUCCEEDED",new Boolean(false));
      mySendObj.put("MESSAGE",msg);
    }
    String msg = new String("Your request was succeesfully updated.");
    mySendObj.put("SUCCEEDED",new Boolean(true));
    mySendObj.put("MESSAGE",msg);

  }
  */


  public void updateShipment() {
    String bulkContainer = (String)inData.get("BULK_CONTAINER");
    Integer orderNo = (Integer)inData.get("ORDER_NO");
    String containerShipmentId = null;
    String vendorOrderNo = null;
    String plannedPickup = null;
    String actualPickup = null;
    String manifestId = null;
    String state = null;
    String country = null;
    String manifestReturn = null;
    Vector containerIds = null;
    Vector containerFormInfo = null;
    String containerMsg = null;
    Vector bulkInfo = null;
    String bulkMsg = null;
    String transCost = "";
    WasteShipment ws = new WasteShipment(db);
    Vector costInfo = null;
    try {
      if (bulkContainer.equalsIgnoreCase("container")) {
        containerShipmentId = (String)inData.get("CONTAINER_SHIPMENT_ID");
        //vendorOrderNo = (String)inData.get("VENDOR_ORDER_NO");
        //plannedPickup = (String)inData.get("PLANNED_PICKUP");
        //containerIds = (Vector)inData.get("CONTAINER_ID");
        actualPickup = (String)inData.get("ACTUAL_PICKUP");
        manifestId = (String)inData.get("MANIFEST_ID");
        state = (String)inData.get("STATE");
        country = (String)inData.get("COUNTRY");
        manifestReturn = (String)inData.get("MANIFEST_RETURN");
        containerFormInfo = (Vector)inData.get("CONTAINER_FORM_INFO");
        //costInfo = (Vector)inData.get("COST_INFO");
        if (inData.get("SHOW_TRANS_COST").toString().equalsIgnoreCase("Y")) {
          transCost = (String)inData.get("TRANS_COST");
        }
        ws.updateShipmentContainer(orderNo.intValue(),containerShipmentId,actualPickup,manifestId,state,country,manifestReturn,containerFormInfo,transCost,costInfo);
        //ws.updateShipmentContainer(orderNo.intValue(),containerShipmentId,vendorOrderNo,plannedPickup,actualPickup,manifestId,state,country,manifestReturn,containerFormInfo);
      } else if (bulkContainer.equalsIgnoreCase("bulk")) {
        bulkInfo = (Vector)inData.get("BULK_INFO");
        ws.updateShipmentBulk(orderNo.intValue(),bulkInfo);
      } else {
        containerShipmentId = (String)inData.get("CONTAINER_SHIPMENT_ID");
        //vendorOrderNo = (String)inData.get("VENDOR_ORDER_NO");
        //plannedPickup = (String)inData.get("PLANNED_PICKUP");
        //containerIds = (Vector)inData.get("CONTAINER_ID");
        actualPickup = (String)inData.get("ACTUAL_PICKUP");
        manifestId = (String)inData.get("MANIFEST_ID");
        state = (String)inData.get("STATE");
        country = (String)inData.get("COUNTRY");
        manifestReturn = (String)inData.get("MANIFEST_RETURN");
        containerFormInfo = (Vector)inData.get("CONTAINER_FORM_INFO");
        //costInfo = (Vector)inData.get("COST_INFO");
        if (inData.get("SHOW_TRANS_COST").toString().equalsIgnoreCase("Y")) {
          transCost = (String)inData.get("TRANS_COST");
        }
        ws.updateShipmentContainer(orderNo.intValue(),containerShipmentId,actualPickup,manifestId,state,country,manifestReturn,containerFormInfo,transCost,costInfo);
        //ws.updateShipmentContainer(orderNo.intValue(),containerShipmentId,vendorOrderNo,plannedPickup,actualPickup,manifestId,state,country,manifestReturn,containerFormInfo);

        bulkInfo = (Vector)inData.get("BULK_INFO");
        ws.updateShipmentBulk(orderNo.intValue(),bulkInfo);
      }
      mySendObj.put("SUCCEEDED",new String("succeeded"));
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEEDED",new String("ERROR"));
    }
  }


  public void getDraftInvoiceInfoTab() {
    Integer uid = (Integer)inData.get("MANAGER_ID");
    Integer orderNo = (Integer)inData.get("SHIPMENT_NUM");
    try {
      mySendObj.put("COST_DATA",WasteCostNInvoice.getDraftInvoiceInfoTab(db,orderNo.intValue()));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      mySendObj.put("EXTENDED_COL",new Integer(3));
      mySendObj.put("TOTAL_COL",new Integer(10));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getInitialInfo() {
    Integer uid = (Integer)inData.get("USER_ID");
    try {
      Personnel p = new Personnel(db);
      p.setPersonnelId(uid.intValue());
      p.load();
      mySendObj.put("USER_NAME",p.getFullName());
      mySendObj.put("PREF_FAC",p.getFacilityId());

      Hashtable initialInfo = WasteShipmentScreen.getInitialInfo3(db,uid.intValue(),"A");    //A -- only show active storage area
      mySendObj.put("INITIAL_INFO",initialInfo);
      mySendObj.put("ALLOW_DELETE",new Boolean(WasteShipmentScreen.allowToDeleteContainer(db,uid)));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());

    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void submitShipmentRequest() {
    goSubmitRequest("SUBMIT");
  }

  public void reSubmitShipmentRequest() {
    goSubmitRequest("RESUBMIT");
  }

  public void cancelShipmentRequest() {
    goSubmitRequest("CANCEL");
  }

  public void updatePickupDate() {
    goSubmitRequest("UPDATE");
  }

  //trong 4-28-01
  public void sendEmailToVendor(int orderNo, String action) {
    try {
      //need a view here
      Vector infoV = WasteOrder.getEmailHeader(db,orderNo);
      String esub = "";
      String emsg = "";
      String url = "";
      boolean firstTime = true;
      boolean sendNotification = true;
      for (int i = 0; i < infoV.size(); i++) {
        Hashtable h = (Hashtable)infoV.elementAt(i);
        if (firstTime) {
          esub = new String("tcmIS Waste Order Request "+orderNo+"");
          emsg = new String("Status         : "+action+"\n");
          emsg = emsg + "Facility       : "+h.get("VENDOR_FAC_ID").toString()+"\n";
          emsg = emsg + "Storage Area   : "+h.get("STORAGE_LOCATION").toString()+"\n";
          emsg = emsg + "Requestor      : "+h.get("FULL_NAME").toString()+"\n";
          emsg = emsg + "Phone          : "+h.get("PHONE").toString()+"\n";
          emsg = emsg + "Email          : "+h.get("EMAIL").toString()+"\n";
          url = "\n\nLink       : "+h.get("ORDER_NOTIFICATION_URL").toString();
          firstTime = false;
          if (BothHelpObjs.isBlankString(h.get("ORDER_NOTIFICATION_URL").toString()) ||
              "N/A".equalsIgnoreCase(h.get("ORDER_NOTIFICATION_URL").toString())) {
            sendNotification = false;
          }
        }
        //String client = db.getClient();
        //emsg = emsg + "\n\nLink       : https://www.tcmis.com/tcmIS/servlet/radian.web.servlets."+client.toLowerCase()+"."+client+"WasteVendorUpdate?Session=0&order_no="+orderNo+"&Shp_id="+h.get("SHIPMENT_ID").toString()+"\n";
        if (url.indexOf("WasteVendorUpdate") > 0) {
          emsg += url + "&order_no=" + orderNo + "&Shp_id=" + h.get("SHIPMENT_ID").toString() + "\n";
        }else {
          emsg += url+"\n";
        }
      }

      //don't send email notification if order_notification_url is empty
      if (sendNotification) {
        String[] recipients;
        recipients = getVendorEmailAddress(orderNo);
        HelpObjs.javaSendMail("tcmis@tcmis.com", esub, recipients, emsg);
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  } //end of method

  public String[] getVendorEmailAddress(int orderNo) {
    String[] recipients;
    String query = "select wvf.email,wo.order_no from waste_vendor_facility wvf, waste_order wo";
    query += " where wvf.vendor_id = wo.vendor_id";
    query += " and wvf.facility_id = wo.facility_id";
    query += " and wvf.waste_location_id = wo.storage_location_id";
    query += " and wo.order_no = "+orderNo;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String vendorEmail = null;
    try{
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        vendorEmail = BothHelpObjs.makeBlankFromNull(rs.getString("email"));
      }
    }catch(Exception e) {
      e.printStackTrace();
      vendorEmail = "tngo@haastcm.com";
      recipients = new String[]{vendorEmail};
      return recipients;
    }finally {
      dbrs.close();
    }

    if(BothHelpObjs.isBlankString(vendorEmail)) {
      vendorEmail = "tngo@haastcm.com";
      recipients = new String[]{vendorEmail};
    }else{
      StringTokenizer st = new StringTokenizer(vendorEmail,",");
      recipients = new String[st.countTokens()];
      int i = 0;
      while (st.hasMoreElements()) {
        recipients[i] = st.nextElement().toString();
        i++;
      }

    }
    return recipients;
  } //end of getVendorEmailAddress

   public void goSubmitRequest(String action)
    {

        WasteOrder wo = new WasteOrder(db);
        WasteShipment wsh = new WasteShipment(db);
        Integer orderNo = (Integer)inData.get("ORDER_NO");
        wo.setOrderNo(orderNo.intValue());
        Vector plannedAV = (Vector)inData.get("PLANNED_AMOUNT_VECTOR");
        Vector bulkShipIdV = (Vector)inData.get("BULK_SHIPMENT_ID");

        //Nawaz          System.out.println("Got here 1");
        String Prefered_pickupdate = (String)inData.get("PREFERED_PICKUP_DATE");
        String bulkPrefered_date = "";
        Vector BulkPrefDate = (Vector)inData.get("BULK_PREFERED_PICKUP_DATE");
        //System.out.println("Got here 1");
        int Shipment_Id_c = Integer.parseInt(inData.get("SHIPMENT_ID_CONT").toString());

        //4-10-01
        String labPackPreferredServiceDate = (String)inData.get("LAB_PACK_PREFERRED_SERVICE_DATE");
        String estDrum = (String)inData.get("LAB_PACK_DRUM_ESTIMATE");

        //4-23-01
        if ((Prefered_pickupdate.length() > 0 || labPackPreferredServiceDate.length() > 0 || estDrum.length() > 0) && Shipment_Id_c == 0) {
          try {
            WasteShipment ws = WasteShipment.insertWasteShipment(db,orderNo.intValue());
            Shipment_Id_c = ws.getWasteShipmentId();
          }catch (Exception myE) {
            myE.printStackTrace();
            Hashtable rCode = new Hashtable();
            rCode.put("SUCCEEDED", new Boolean(false));
            rCode.put("MSG", "Error! Can't submit/resubmit the your request.");
            mySendObj.put("RETURN_CODE",rCode);
            return;
          }
        }


        //if there is some planned amount then up waste shipment for each bulk
        if (plannedAV.size() > 0)
        {
            try
            {
                WasteBulkOrder wbo = new WasteBulkOrder(db);
                for (int i = 0; i < bulkShipIdV.size(); i++)
                {
                    int shipId = Integer.parseInt(bulkShipIdV.elementAt(i).toString());
                    int plannedA = Integer.parseInt(plannedAV.elementAt(i).toString());
                    wbo.updatePlannedAmount(shipId,plannedA);
                    bulkPrefered_date = (String)BulkPrefDate.elementAt(i).toString();
                    if ((!BothHelpObjs.isBlankString(bulkPrefered_date)) && (action.equalsIgnoreCase("SUBMIT")))
                    {
                        wsh.setWasteShipmentId(shipId);
                        wsh.insert("preferred_service_date",
                                   bulkPrefered_date,WasteShipment.DATE);
                    }
                    else if ((!BothHelpObjs.isBlankString(bulkPrefered_date)) && (action.equalsIgnoreCase("RESUBMIT")))
                    {
                        wsh.setWasteShipmentId(shipId);
                        wsh.insert("preferred_service_date",
                                   bulkPrefered_date,WasteShipment.DATE);
                    }
                    else if ((BothHelpObjs.isBlankString(bulkPrefered_date)) && (action.equalsIgnoreCase("RESUBMIT")))
                     {
                        bulkPrefered_date = "";
                        wsh.setWasteShipmentId(shipId);
                        wsh.insert("preferred_service_date",
                                   bulkPrefered_date,WasteShipment.NULLVAL);
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Hashtable rCode = new Hashtable();
                rCode.put("SUCCEEDED", new Boolean(false));
                rCode.put("MSG",
                          "Error! Can't update planned amount for bulk shipment(s).");
                mySendObj.put("RETURN_CODE",rCode);
                return;
            }
        }

        if (action.equalsIgnoreCase("SUBMIT"))
        {
            try
            {
                wo.insert("original_submit_date","nowDate",WasteOrder.DATE);
                if (Shipment_Id_c != 0) {
                  wsh.setWasteShipmentId(Shipment_Id_c);
                  if (!BothHelpObjs.isBlankString(Prefered_pickupdate)) {
                    wsh.insert("preferred_service_date",Prefered_pickupdate,WasteShipment.DATE);
                  }
                  //4-10-01
                  if (!BothHelpObjs.isBlankString(labPackPreferredServiceDate)) {
                    wsh.insert("lab_pack_preferred_service_dat",labPackPreferredServiceDate,WasteShipment.DATE);
                  }
                  if (!BothHelpObjs.isBlankString(estDrum)) {
                    wsh.insert("lab_pack_drum_estimate",estDrum,WasteShipment.INT);
                  }
                }

                sendEmailToVendor(orderNo.intValue(),action);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Hashtable rCode = new Hashtable();
                rCode.put("SUCCEEDED", new Boolean(false));
                rCode.put("MSG", "Error! Can't submit the current request.");
                mySendObj.put("RETURN_CODE",rCode);
                return;
            }
        }
        else if(action.equalsIgnoreCase("RESUBMIT"))
        {
            try
            {
                wo.insert("resubmit_date","nowDate",WasteOrder.DATE);
                if (Shipment_Id_c != 0) {
                  wsh.setWasteShipmentId(Shipment_Id_c);
                  if (!BothHelpObjs.isBlankString(Prefered_pickupdate)) {
                    wsh.insert("preferred_service_date",Prefered_pickupdate,WasteShipment.DATE);
                  }else if  (BothHelpObjs.isBlankString(Prefered_pickupdate)) {
                    Prefered_pickupdate = "";
                    wsh.insert("preferred_service_date",Prefered_pickupdate,WasteShipment.NULLVAL);
                  }

                  //4-10-01
                  if (!BothHelpObjs.isBlankString(labPackPreferredServiceDate)) {
                    wsh.insert("lab_pack_preferred_service_dat",labPackPreferredServiceDate,WasteShipment.DATE);
                  }
                  if (!BothHelpObjs.isBlankString(estDrum)) {
                    wsh.insert("lab_pack_drum_estimate",estDrum,WasteShipment.INT);
                  }
                }

                sendEmailToVendor(orderNo.intValue(),action);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Hashtable rCode = new Hashtable();
                rCode.put("SUCCEEDED", new Boolean(false));
                rCode.put("MSG", "Error! Can't resubmit the current request.");
                mySendObj.put("RETURN_CODE",rCode);
                return;
            }
        }
        else if (action.equalsIgnoreCase("CANCEL"))
        {
            try
            {
                wo.insert("cancel_date","nowDate",WasteOrder.DATE);
                WasteShipment.cancelShipment(db,orderNo.intValue());
                sendEmailToVendor(orderNo.intValue(),action);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Hashtable rCode = new Hashtable();
                rCode.put("SUCCEEDED", new Boolean(false));
                rCode.put("MSG", "Error! Can't cancel the current request.");
                mySendObj.put("RETURN_CODE",rCode);
                return;
            }
        }

        Hashtable rCode = new Hashtable();
        rCode.put("SUCCEEDED", new Boolean(true));
        mySendObj.put("RETURN_CODE",rCode);
    }

  protected void deleteWr() {
    try{
      Integer reqNum = (Integer)inData.get("REQ_NUM");
      //delete any account associate with this waste request number
      WRAccount wra = new WRAccount(db);
      wra.setWasteRequestId(reqNum.intValue());
      wra.delete();

      WasteRequest wr = new WasteRequest(db);
      wr.setWasteRequestId(reqNum.intValue());
      wr.delete();
    }catch (Exception e) {
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED", new Boolean(false));
      rCode.put("MSG","Error on deleting Waste Request.");
      mySendObj.put("RETURN_CODE",rCode);
      try {
        Integer reqNum = (Integer)inData.get("REQ_NUM");
        WastePickupRequestScreen wprs = new WastePickupRequestScreen(db);
        mySendObj.put("SCREEN_DATA",wprs.getScreenData(reqNum.intValue()));
      }catch(Exception e2){
        e2.printStackTrace();
      }
      return;
    }
    Hashtable rCode = new Hashtable();
    rCode.put("SUCCEEDED", new Boolean(true));
    mySendObj.put("RETURN_CODE",rCode);
  }


  protected void doSearch(){
    try{
      // load the data model
      mySendObj.put("SEARCH_DATA",WasteShipmentScreen.doSearch(db,inData.get("FAC_ID").toString(),inData.get("STORAGE_AREA").toString(),inData.get("VENDOR").toString()));
      mySendObj.put("KEY_COLS",getColKey());
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  protected void createOrderId(){
    try{
      Vector wasteReqV = (Vector)inData.get("SHIPMENT_REQUEST");
      Hashtable shipmentInfo = (Hashtable)inData.get("SHIPMENT_INFO");
      if(wasteReqV.size()<1){
        mySendObj.put("SHIPMENT_NUM",new Integer(0));
        return;
      }
      int userId = Integer.parseInt(inData.get("USER_ID").toString());
      WasteOrder wo = WasteOrder.createWasteOrder(db,userId,wasteReqV,shipmentInfo);

      //preset manifest line letter for shipment
      WasteManifest.setManifestLine(db,wo.getOrderNo());

      mySendObj.put("SHIPMENT_NUM",new Integer(wo.getOrderNo()));
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void getShipmentData(){
    try {
      Integer orderNo = (Integer)inData.get("ORDER_NO");
      Integer userId = (Integer)inData.get("USER_ID");
      mySendObj.put("SCREEN_DATA",WasteShipmentScreen.getScreenData(db,orderNo.intValue(),userId.intValue()));
      mySendObj.put("KEY_COLS",getColKey());
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void getShipmentInfoData(){
    try {
      Integer orderNo = (Integer)inData.get("SHIPMENT_NUM");
      mySendObj.put("SCREEN_DATA",WasteShipmentInfoTabScreen.getScreenData(db,orderNo.intValue()));
      //determine whether to show transportation cost for vendor and facility combo
      mySendObj.put("SHOW_TRANS_COST",WasteShipmentInfoTabScreen.showTransCost(db,orderNo.intValue()));
      //System.out.println("\n\n--------------- ship info: "+mySendObj);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void getShipmentEditInfo(){
    try{
      // load the data model
      String orderNo = inData.get("ORDER_NO").toString();
      Integer orderId = new Integer(orderNo);
      WasteShipmentScreen.setShipmentId(orderId.intValue());
      mySendObj.put("EDIT_INFO",WasteShipmentScreen.getEditInfo(db,inData.get("FAC_ID").toString(),inData.get("STORAGE_AREA").toString(),inData.get("VENDOR").toString()));
      mySendObj.put("KEY_COLS",getColKey());
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void updateShipmentRequest() {
    try {
      String orderNo = inData.get("ORDER_NO").toString();
      Integer orderId = new Integer(orderNo);
      WasteOrder wo = new WasteOrder(db);
      wo.updateOrderRequest((Vector)inData.get("UPDATE_VECTOR"),orderId.intValue(),(String)inData.get("CONTAINER_SHIPMENT_ID"));
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected Hashtable getColKey(){
    Hashtable h = new Hashtable();
    for (int i=0;i<mainCols.length;i++) {
      h.put(mainCols[i],new Integer(i));
     // h.put(mainCols[i],String.valueOf(i));
    }
    return h;
  }

  protected void addPickupLocation(){
    try{
      mySendObj.put("RETURN_CODE",new Boolean(false));
      String facId = inData.get("FAC_ID").toString();
      String locDesc = inData.get("LOCATION_DESC").toString();
      WastePickupLocation.addWastePickupLocation(db,facId,locDesc);
      mySendObj.put("RETURN_CODE",new Boolean(true));
      loadPickupLocationScreen();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  protected void changePickupLocationDesc(){
    try{
      mySendObj.put("RETURN_CODE",new Boolean(false));
      String facId = inData.get("FAC_ID").toString();
      String locDesc = inData.get("LOCATION_DESC").toString();
      int locId = ((Integer)inData.get("LOCATION_ID")).intValue();
      WastePickupLocation l = new WastePickupLocation(db);
      l.setLocationId(locId);
      l.updateDescription(locDesc);
      mySendObj.put("RETURN_CODE",new Boolean(true));
      loadPickupLocationScreen();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  protected void loadPickupLocationScreen(){
    try{
      String facId = inData.get("FAC_ID").toString();
      Vector v = WastePickupLocation.getAllPickupLocations(db,facId);
      mySendObj.put("WASTE_LOCATION_DATA",v);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}