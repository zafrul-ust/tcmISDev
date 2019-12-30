package radian.tcmis.server7.share.servlets;

import java.util.*;
//import Acme.Crypto.*;
import java.awt.Color;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;

public class CatalogNew extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;
  Vector containerId = null;

  public CatalogNew(ServerResourceBundle b, TcmISDBModel d ){
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
    long sTime = new java.util.Date().getTime();
    mySendObj = new Hashtable();
    // using myRecvObj you get the function the way you want
    inData = (Hashtable)myRecvObj;
    function = inData.get("ACTION").toString();

    //System.out.println("in data: " + inData);
    if (function.equals("LOAD_ALL_DATA")) {
      loadAllData();
    }else if (function.equals("GET_SPEC_AND_MATERIAL")) {
      getSpecMat();
    }else if (function.equals("BUILD_REQUEST")) {
      buildRequest();
    }else if (function.equals("BUILD_EMPTY_REQUEST")) {
      buildEmptyRequest();
    }else if (function.equals("PRICE_REQUEST")) {
      priceRequest();
    }else if(function.equalsIgnoreCase("LOG_PUNCHOUT")) {
      logPunchout();
    }else if (function.equalsIgnoreCase("GET_TCMIS_MESSAGES")) {
      getDisplayMessage();
    }else if (function.equals("ARIBA_REPAIR_SEARCH")) {
      aribaRepairSearch();
    }else if (function.equals("ARIBA_REPAIR_SUBMIT")) {
      aribaRepairSubmit();
    }else if (function.equals("GET_ITEM_CATALOG_TABLE_DATA")) {
      searchItemCatalog();
    }else if (function.equals("GET_EVAL_DATA")) {
      getEvalData();
    }else if (function.equals("GET_EVAL_DETAIL")) {
      getEvalDetail();
    }else if (function.equals("REJECT_EVAL")) {
      goRejectEval();
    }else if (function.equals("GET_POS_CUSTOMER_DATA")) {
      getPOSCustomerData();
    }else if (function.equals("GET_POS_CHARGE_INFO")) {
      getPOSChargeInfo();
    }else if (function.equals("BUILD_POS_REQUEST")) {
      goBuildPOSRequest();
    }else if (function.equals("GET_POS_REPACK_DATA")) {
      goGetPOSRepackData();
    }else if (function.equals("GET_POS_TAP_DATA")) {
      goGetPOSTapData();
    }else if (function.equals("GET_POS_BIN_DATA")) {
      goGetBinData();
    }else if (function.equals("POS_TAP")) {
      goPOSTap();
    }else if (function.equals("POS_CLOSE_RECEIPT")) {
      goPOSCloseReceipt();
    }else if (function.equals("PROCESS_POS_PICK_DATA")) {
      goProcessPOSPickData();
    }else if (function.equals("PRINT_POS_PICK_RECEIPT")) {
      printPOSPickReceipt();
    }else if (function.equals("REFRESH_PICK_DATA")) {
      goRefreshPickData();
    }else if (function.equals("PROCESS_POS_REPACK")) {
      goProcessPOSRepack();
    }else if (function.equals("CREATE_NEW_POS_BIN")) {
      goCreateNewBin();
    }else if (function.equals("CANCEL_POS_PICK_DATA")) {
      goCancelPOSRequest();
    }

    //System.out.println("outData:"+mySendObj);
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new java.util.Date(min));
    //System.out.println("\n\n---- NChemObj: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds");
  }

  protected void goCancelPOSRequest() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      String msg = pos.goCancelPOSRequest(inData);
      if (!"OK".equalsIgnoreCase(msg)) {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG",msg);
      }else {
        mySendObj.put("RETURN_CODE", new Boolean(true));
        mySendObj.put("RETURN_MSG",msg);
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goCreateNewBin() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      String msg = pos.goCreateNewBin(inData);
      if ("OK".equalsIgnoreCase(msg)) {
        mySendObj.put("RETURN_CODE", new Boolean(true));
        mySendObj.put("RETURN_MSG", "");
      }else {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG", msg);
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while creating new bin.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goProcessPOSRepack() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      String msg = pos.goProcessPOSRepack(inData);
      if ("OK".equalsIgnoreCase(msg)) {
        mySendObj.put("RETURN_CODE", new Boolean(true));
        mySendObj.put("RETURN_MSG", "");
      }else {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG", msg);
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while closing (POS) receipt.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goRefreshPickData() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      Hashtable h = new Hashtable();
      h.put("PR_NUMBER",(String)inData.get("PR_NUMBER"));
      h.put("FACILITY_ID",(String)inData.get("FACILITY_ID"));
      String msg = pos.getPickInfo(h);
      if (!"OK".equalsIgnoreCase(msg)) {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG",msg);
      }else {
        mySendObj.put("PICK_DATA",(Vector)h.get("PICK_INFO"));
        mySendObj.put("RETURN_CODE", new Boolean(true));
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while refreshing (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void printPOSPickReceipt() {
    POSPickReportGenerator osrg = new POSPickReportGenerator();
    String url = osrg.buildReport((Vector)inData.get("POS_PICK_DATA"),(String)inData.get("CLERK_NAME"),(String)inData.get("CUSTOMER_NAME"),
                                  (String)inData.get("HUB_NAME"),(String)inData.get("INVENTORY_GROUP"),(String)inData.get("FACILITY_ID"));
    if (url.length() > 1) {
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("URL",url);
      mySendObj.put("MSG","");
    }else {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","Error message here.");
    }
  }

  protected void goProcessPOSPickData() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      String[] msg = pos.goProcessPOSPickData(inData);
      if (!"OK".equalsIgnoreCase(msg[0])) {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG",msg[0]);
      }else {
        mySendObj.put("RECEIPT_DATA",pos.getPOSReceiptData(msg[1]));
        mySendObj.put("HUB_NAME",pos.getHubName((String)inData.get("HUB")));
        mySendObj.put("RETURN_CODE", new Boolean(true));
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goPOSCloseReceipt() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      String msg = pos.goPOSCloseReceipt(inData);
      if ("OK".equalsIgnoreCase(msg)) {
        mySendObj.put("RETURN_CODE", new Boolean(true));
        mySendObj.put("RETURN_MSG", "");
      }else {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG", msg);
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while closing (POS) receipt.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goPOSTap() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      Hashtable h = pos.goPOSTap(inData);
      if ("OK".equalsIgnoreCase((String)h.get("MSG"))) {
        mySendObj.put("RECEIPT_ID", (String) h.get("RECEIPT_ID"));
        mySendObj.put("BIN", (String) h.get("BIN"));
        mySendObj.put("RETURN_CODE", new Boolean(true));
        mySendObj.put("RETURN_MSG", "");
      }else {
        mySendObj.put("RECEIPT_ID", "");
        mySendObj.put("BIN", "");
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG", (String)h.get("MSG"));
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goGetBinData() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      mySendObj.put("BIN",pos.goGetBinData(inData));
      mySendObj.put("RETURN_CODE", new Boolean(true));
      mySendObj.put("RETURN_MSG","");
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goGetPOSTapData() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      Hashtable h = pos.goGetPOSTapData(inData);
      mySendObj.put("DATA",(Vector)h.get("DATA"));
      mySendObj.put("BIN",(Vector)h.get("BIN"));
      mySendObj.put("RETURN_CODE", new Boolean(true));
      mySendObj.put("RETURN_MSG","");
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goGetPOSRepackData() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      mySendObj.put("DATA",pos.goGetPOSRepackData(inData));
      mySendObj.put("RETURN_CODE", new Boolean(true));
      mySendObj.put("RETURN_MSG","");
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void goBuildPOSRequest() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      Hashtable h = pos.buildPOSRequest(inData);
      if (!"OK".equalsIgnoreCase((String) h.get("MSG"))) {
        mySendObj.put("RETURN_CODE", new Boolean(false));
        mySendObj.put("RETURN_MSG",(String) h.get("MSG"));
      }else {
        //next get pick info
        String tmpVal = pos.getPickInfo(h);
        if (!"OK".equalsIgnoreCase(tmpVal)) {
          mySendObj.put("RETURN_CODE", new Boolean(false));
          mySendObj.put("RETURN_MSG",tmpVal);
        }else {
          mySendObj.put("PICK_INFO",h);
          mySendObj.put("RETURN_CODE", new Boolean(true));
        }
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  } //end of method

  protected void getPOSChargeInfo() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      mySendObj.put("POS_CHARGE_INFO",pos.getChargeInfo(inData));
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while loading customer (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void getPOSCustomerData() {
    try {
      PointOfSaleObj pos = new PointOfSaleObj(db);
      mySendObj.put("POS_DATA",pos.getCustomerData(inData));
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while loading customer (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void goRejectEval() {
    try {
      EngEval engEval = new EngEval(db);
      if (engEval.rejectEval(inData)) {
        mySendObj.put("ENG_EVAL_DATA",engEval.getEvalData(inData));
        mySendObj.put("RETURN_CODE",new Boolean(true));
        mySendObj.put("RETURN_MSG","I am not suppose to see this message.");
      }else {
        mySendObj.put("RETURN_CODE",new Boolean(false));
        mySendObj.put("RETURN_MSG","tcmIS encountered an error while rejecting engineering evaluation.\nPease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while rejecting engineering evaluation.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }
  protected void getEvalData() {
    try {
      EngEval engEval = new EngEval(db);
      mySendObj.put("ENG_EVAL_DATA",engEval.getEvalData(inData));
      mySendObj.put("RETURN_CODE",new Boolean(true));
      mySendObj.put("RETURN_MSG","I am not suppose to see this message.");
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while getting engineering evaluation data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }
  protected void getEvalDetail() {
    try {
      EngEval engEval = new EngEval(db);
      mySendObj.put("ENG_EVAL_DETAIL",engEval.getEvalDetail(inData));
      mySendObj.put("RETURN_CODE",new Boolean(true));
      mySendObj.put("RETURN_MSG","I am not suppose to see this message.");
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while getting engineering evaluation detail.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void searchItemCatalog() {
    try {
      ItemCatalog itemCatalog = new ItemCatalog(db);
      mySendObj.put("SEARCH_RESULT",itemCatalog.doSearch(inData));
      mySendObj.put("RETURN_CODE",new Boolean(true));
      mySendObj.put("RETURN_MSG","I am not suppose to see this message.");
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error while searching item catalog.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void aribaRepairSearch() {
    try {
      Hashtable tableDataH = new Hashtable();
      String[] aribaTableCols = {"Requestor","Work Area","MR","Line","Date","Part","Desc","Qty","Ext. Price"};
      int[] aribaTableColWidths = {100,100,50,40,80,100,150,50,90};
      int[] aribaTableColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING};
      tableDataH.put("COL_HEADS",aribaTableCols);
      tableDataH.put("COL_TYPES",aribaTableColTypes);
      tableDataH.put("COL_WIDTHS",aribaTableColWidths);
      /*
      Vector tableDataV = new Vector(20);
      for (int i = 0; i < 20; i++) {
        Hashtable h = new Hashtable(9);
        h.put("REQUESTOR","REQUESTOR"+i);
        h.put("WORK_AREA","WORK_AREA"+i);
        h.put("MR","MR"+i);
        h.put("LINE","LINE"+i);
        h.put("DATE","DATE"+i);
        h.put("PART","PART"+i);
        h.put("DESC","DESC"+i);
        h.put("QTY","QTY"+i);
        h.put("EXT_PRICE","EXT_PRICE"+i);
        tableDataV.addElement(h);
      } */
      AribaRepairObj aribaRepairObj = new AribaRepairObj(db);
      tableDataH.put("TABLE_DATA",aribaRepairObj.doSearch((String)inData.get("SEARCH_TEXT")));

      mySendObj.put("RETURN_CODE",new Boolean(true));
      mySendObj.put("RETURN_MSG","I am not suppose to see this message.");
      mySendObj.put("SEARCH_RESULT",tableDataH);
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void aribaRepairSubmit() {
    try {
      AribaRepairObj aribaRepairObj = new AribaRepairObj(db);
      boolean b = aribaRepairObj.resubmitRequests((Vector)inData.get("RESUBMIT_DATA"),(String)inData.get("PAYLOAD_ID"));
      mySendObj.put("RETURN_CODE",new Boolean(b));
      if (b) {
        String payLoadID = (String)inData.get("PAYLOAD_ID");
        boolean comeFromOracle = HelpObjs.countQuery(db,"select count(*) from punchout_session where payload_id = '"+payLoadID+"' and oracle = 'Y'") > 0;
        if (!comeFromOracle) {
          //System.out.println("\n\n--------- right before posting message");
          //10-29-01 if user(s) logon as guest then don't create sales order
          //this is where I need to call Chuck class for xml document and url
          String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/tcmIS/seagate/punchout/checkout?payloadID=" +payLoadID, "payloadID=" + payLoadID, "text/html"); //PROD
          RequestsScreen rs = new RequestsScreen(db);
          Hashtable payloadH = rs.getPunchoutOrderMessage(payLoadID);
          mySendObj.put("XML_DOC", postingMsg);
          mySendObj.put("URL", (String) payloadH.get("url"));
          mySendObj.put("POSTING_MSG",new Boolean(true));
        }else {
          db.doUpdate("insert into punchout_order_message (payload_id,punchout) values('" +payLoadID + "','Y')");
          mySendObj.put("XML_DOC", "");
          mySendObj.put("URL", "");
          mySendObj.put("POSTING_MSG", new Boolean(true));
        }
        mySendObj.put("COME_FROM_ORACLE",new Boolean(comeFromOracle));

      }else {
        mySendObj.put("RETURN_MSG","tcmIS encountered an error.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
      }
    }catch (Exception e) {
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("RETURN_MSG","tcmIS encountered an error.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void getDisplayMessage() {
    mySendObj.put("DISPLAY_MESSAGE",new Boolean(false));
    try {
      TcmISFeature tf = new TcmISFeature(db);
      Hashtable h = tf.getTcmISMessage();
      mySendObj.put("DISPLAY_MESSAGE",(Boolean)h.get("DISPLAY_MESSAGE"));
      //mySendObj.put("MESSAGES",(String)h.get("MESSAGES"));
      mySendObj.put("MESSAGES","1. this is a test\n2. yet another test\n3. more test\n4. last test");
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error while getting display message","Error while getting display message info.",86030,false);
    }
    //System.out.println("\n\n------ my output: "+mySendObj);
  }

  //1-04-02 log response code from Ariba
  protected void logPunchout() {
    String cIP = super.getClientIP();
    String payLoadID = (String)inData.get("PAY_LOAD_ID");
    String userID = (String)inData.get("USER_ID");
    Integer rspcode = (Integer)inData.get("RESPONSE_CODE");
    try {
      String query = "insert into punch_out_log (pay_load_id,response_code,punchout_date,ip_address,personnel_id)";
      query += " values ('"+payLoadID+"',"+rspcode+",sysdate,'"+cIP+"',"+userID+")";
      //System.out.println("\n\n------ punchout log: "+query);
      db.doUpdate(query);
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
    }
  }

  protected void priceRequest() {
    String facid = (String)inData.get("FACID");
    String item = (String)inData.get("ITEM");
    String userid = (String)inData.get("USERID");
    try {
      PriceRequest pr = new PriceRequest(db);
      pr.setFacId(facid);
      pr.setItemId(Integer.parseInt(item));
      pr.setRequestorId(Integer.parseInt(userid));
      pr.updateCatalogSnapshot();
      mySendObj.put("PRICE_REQUEST_INFO",pr.getRequestPriceInfo());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void buildEmptyRequest(){
    try {
      RequestsScreen rs = new RequestsScreen(db);
      String payLoadID = (String)inData.get("PAY_LOAD_ID");
      boolean comeFromOracle = HelpObjs.countQuery(db,"select count(*) from punchout_session where payload_id = '"+payLoadID+"' and oracle = 'Y'") > 0;
      if (!comeFromOracle) {
        //System.out.println("\n\n--------- right before posting message");
        //10-29-01 if user(s) logon as guest then don't create sales order
        //this is where I need to call Chuck class for xml document and url
        String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/tcmIS/seagate/punchout/checkout?payloadID=" +payLoadID, "payloadID=" + payLoadID, "text/html"); //PROD
        Hashtable payloadH = rs.getPunchoutOrderMessage(payLoadID);
        mySendObj.put("XML_DOC", postingMsg);
        mySendObj.put("URL", (String) payloadH.get("url"));
        mySendObj.put("POSTING_MSG",new Boolean(true));
      }else {
        db.doUpdate("insert into punchout_order_message (payload_id,punchout) values('" +payLoadID + "','Y')");
        mySendObj.put("XML_DOC", "");
        mySendObj.put("URL", "");
        mySendObj.put("POSTING_MSG", new Boolean(true));
      }
      mySendObj.put("COME_FROM_ORACLE",new Boolean(comeFromOracle));
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void buildRequest(){
      Hashtable partItemH = (Hashtable)inData.get("PART_ITEM_DETAIL");
      Integer userid = (Integer)inData.get("USERID");
      String facid = (String)inData.get("FACID");
      String accSysId = (String)inData.get("ACC_SYS");
      Boolean newMR = (Boolean)inData.get("NEW_MR");
      Boolean aribaLogon = (Boolean)inData.get("ARIBA_LOGON");
      Boolean ecomByPass = (Boolean)inData.get("ECOM_BY_PASS");
      String[] temp = new String[2];
      Vector rreturn = new Vector();
      try {
        SearchPScreen scr = new SearchPScreen(db);
        if (newMR.booleanValue()) {
          temp = scr.buildRequest(userid.intValue(),facid,partItemH,accSysId,aribaLogon.booleanValue());
        }else {
          Integer cPR = new Integer((String)inData.get("PR_NUMBER"));
          temp = scr.buildUpdateRequest(cPR.intValue(),partItemH,facid,aribaLogon.booleanValue());
        }
        rreturn.addElement(temp[0]);
        rreturn.addElement(temp[1]);
        mySendObj.put("REQUEST_RETURN",rreturn);

        boolean requiredMRScreen = HelpObjs.countQuery(db,"select count(*) from pr_rules where status = 'A' and mr_screen_required = 'Y' and facility_id = '"+facid+"' and account_sys_id = '"+accSysId+"'") > 0;
        mySendObj.put("REQUIRED_MR_SCREEN",new Boolean(requiredMRScreen));
        //if facility and account sys required to go to Material Request Screen
        if (requiredMRScreen) {
          Hashtable requestDataH = new Hashtable(3);
          RequestsScreen rs = new RequestsScreen(db);
          Vector v = rs.getScreenData(userid.intValue(), Integer.parseInt(temp[0]));
          requestDataH.put("HEADER_INFO", (Hashtable) v.elementAt(0));
          requestDataH.put("DETAIL_INFO", (Vector) v.elementAt(1));
          requestDataH.put("NOW", Calendar.getInstance());
          mySendObj.put("REQUEST_DATA", requestDataH);
          mySendObj.put("RETURN_CODE", new Boolean(true));
        }else {
          //if Ecomerce then go ahead and submit the request
          if (aribaLogon.booleanValue() || ecomByPass.booleanValue()) {
            RequestsScreen rs = new RequestsScreen(db);
            Vector dataV = rs.getScreenData(userid.intValue(), Integer.parseInt(temp[0]));
            mySendObj.put("POSTING_MSG", new Boolean(false)); //11-14-01 define here so that this variable always assigned to something
            Hashtable headerInfo = (Hashtable) dataV.elementAt(0);
            headerInfo.put("PAY_LOAD_ID", (String) inData.get("PAY_LOAD_ID"));
            headerInfo.put("ARIBA_LOGON", aribaLogon);
            Hashtable h = rs.submitRequest("SUBMIT", headerInfo, (Vector) dataV.elementAt(1), userid.toString(), this.getBundle().get("DB_CLIENT").toString());
            Boolean b = (Boolean) h.get("RETURN_CODE");
            mySendObj.put("RETURN_CODE", b);
            if (b.booleanValue()) {
              if (aribaLogon.booleanValue()) {
                //if FEC I don't have to do anything special coz the Mikey's code will use the flag from RLI
                //payload_id = 'xxx' and request_line_status = 'Pending Finance Approval'
                String payLoadID = (String) inData.get("PAY_LOAD_ID");
                if ("FEC".equalsIgnoreCase(db.getClient())) {
                  db.doUpdate("insert into punchout_order_message (payload_id,punchout,pr_number) values('" + payLoadID + "','Y'," + Integer.parseInt(temp[0]) + ")");
                } else if ("Seagate".equalsIgnoreCase(db.getClient())) {
                  if (h.get("NEXT_STATUS").toString().equalsIgnoreCase("posubmit")) {
                    rs.setReleaseInfo(headerInfo, (Vector) dataV.elementAt(1));
                  }
                  boolean comeFromOracle = HelpObjs.countQuery(db, "select count(*) from punchout_session where payload_id = '" + payLoadID + "' and oracle = 'Y'") > 0;
                  if (!comeFromOracle) {
                    //System.out.println("\n\n--------- right before posting message");
                    //10-29-01 if user(s) logon as guest then don't create sales order
                    //this is where I need to call Chuck class for xml document and url
                    String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/tcmIS/seagate/punchout/checkout?payloadID=" + payLoadID, "payloadID=" + payLoadID, "text/html"); //PROD
                    Hashtable payloadH = rs.getPunchoutOrderMessage(payLoadID);
                    mySendObj.put("XML_DOC", postingMsg);
                    mySendObj.put("URL", (String) payloadH.get("url"));
                    mySendObj.put("POSTING_MSG", new Boolean(true));
                  } else {
                    db.doUpdate("insert into punchout_order_message (payload_id,punchout) values('" + payLoadID + "','Y')");
                    mySendObj.put("XML_DOC", "");
                    mySendObj.put("URL", "");
                    mySendObj.put("POSTING_MSG", new Boolean(true));
                  }
                  mySendObj.put("COME_FROM_ORACLE", new Boolean(comeFromOracle));
                }
              } //end of ariba logon
            } else {
              mySendObj.put("RETURN_MSG", (String) h.get("MSG"));
            }
          } //end of if aribalogon or ecomByPass
        } //end of not required MR Screen
      }catch (Exception e) {
        e.printStackTrace();
      }
  } //end of method

  protected void loadAllData() {
    //System.out.println("\n\n----- here in catalognew loadalldata: "+inData);
    String userid = (String)inData.get("USERID");
    try {
      SearchPScreen scr = new SearchPScreen(db);
      if (inData.containsKey("PAYLOAD_ID")) {
        scr.setPayloadId( (String) inData.get("PAYLOAD_ID"));
      }
      Hashtable result = new Hashtable();
      if (inData.containsKey("NEW")) {
        result = scr.getMyFacWorkAreaList((new Integer(userid)).intValue());
        mySendObj.put("CAN_CREATE_MR_APP",(Hashtable) result.get("CAN_CREATE_MR_APP"));
        mySendObj.put("CAN_CREATE_FOR_POS",scr.getCreateMRForPOS(userid));
        mySendObj.put("POINT_OF_SALE_NAME","POS: ");
      }else {
        result = scr.loadAllDataNew( (new Integer(userid)).intValue());
      }

      String username = new String((String) result.get("userName"));

      Enumeration E;
      Hashtable facXref = (Hashtable) result.get("facility");
      String[] facids = new String[facXref.size()];
      facids[0]="";
      String[] facnames = new String[facXref.size()];
      facnames[0]="";
      int i=0;
      for(E=facXref.keys();E.hasMoreElements();){
           String facNameTmp = new String((String) E.nextElement());
           String facIDTmp = new String((String)facXref.get(facNameTmp));
           facids[i] = facIDTmp ;
           facnames[i] = facNameTmp ;
           i++;
      }
      //application
      Hashtable appXref2D = (Hashtable) result.get("application");
      Vector facxapp = new Vector();
      i=0;
      Vector appids = new Vector();
      Vector appnames = new Vector();
      for(E=appXref2D.keys();E.hasMoreElements();){
           String facIdTmp = new String((String) E.nextElement());
           Hashtable appXref = (Hashtable) appXref2D.get(facIdTmp);
           Enumeration E2;
           for (E2=appXref.keys();E2.hasMoreElements();){
             String appNameTmp = new String((String) E2.nextElement());
             String appIDTmp = new String((String)appXref.get(appNameTmp));
             appids.addElement(appIDTmp==null?"":appIDTmp) ;
             appnames.addElement(appNameTmp==null?"":appNameTmp);
             facxapp.addElement(facIdTmp==null?"":facIdTmp);
             i++;
           }
      }
      facxapp = new Vector();
      appids = new Vector();
      appnames = new Vector();

      if (appids.isEmpty()) appids.addElement("");
      if (appnames.isEmpty()) appids.addElement("");
      if (facxapp.isEmpty()) facxapp.addElement("");

      String username2 = new String(username);
      String username3 = new String(username);

      //preferred fac
      Personnel per = new Personnel(db);
      per.setPersonnelId((new Integer(userid)).intValue());
      per.load();
      String userFac = per.getFacilityId();

      Vector userNames = new Vector();
      userNames.addElement(username);
      userNames.addElement(username2);
      userNames.addElement(username3);
      mySendObj.put("USER_NAME",userNames);
      Vector facIDs = new Vector();
      for(i=0;i<facids.length;i++){
        facIDs.addElement((facids[i]));
      }
      mySendObj.put("FAC_ID",facIDs);
      Vector facNames = new Vector();
      for(i=0;i<facnames.length;i++){
        facNames.addElement((facnames[i]));
      }
      mySendObj.put("FAC_NAME",facNames);
      mySendObj.put("FACXAPP",facxapp);
		if (BothHelpObjs.isBlankString(userFac)) {
			if (facIDs.size() > 0) {
				userFac = facIDs.firstElement().toString();
			}else {
				userFac = "";
			}
		}
		Vector facUser = new Vector();
      facUser.addElement(userFac);
      mySendObj.put("FAC_USER",facUser);

      //mySendObj.put("APP_USER","F006 : FC11 - MEDICAL");            //7-03-02
      mySendObj.put("APP_USER","");                       //7-03-02  until I figure out how to get the application_desc from application

      //getting facilities that are definited as ECOMMERCE or catalog add single work are
      mySendObj.put("FAC_IN_ECOM_LIST",FacPref.getFacilityInEComList(db,userid));
		mySendObj.put("CAT_ADD_FAC_SINGLE_APP_LIST",FacPref.getCatAddFacSingleAppList(db,userid));

		//filling catalog type combo box
      Vector myCatTypeV = new Vector(2);
      myCatTypeV.addElement("Part Catalog");
      myCatTypeV.addElement("Item Catalog");
      mySendObj.put("CATALOG_TYPE",myCatTypeV);

      //filling catalog view combo box
      Vector viewType = new Vector(1);
      //viewType.addElement("Part View");
      viewType.addElement("Detail");
      mySendObj.put("CATALOG_VIEW",viewType);

      //shopping cart table
      String[] baskColsNew = new String[]{"Work Area ID","Work Area","Part Number","Part Desc.","Part Qty","Ext. Price","Example Packaging","Date Needed","Item","Notes","Real Notes","Critical","Facility","PR","Catalog","Type","Example Item","Catalog Price","Unit Price","Min Buy","Part Group","Inventory Group","Dispensed UOM","Dispensed Item Type","Currency ID","Order Quantity Rule","Line Item","Catalog Company ID"};
      int[] baskColWidths = {0,100,70,90,50,65,100,90,0,50,0,50,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
      int[] baskColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_BOOLEAN,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
			 					  BothHelpObjs.TABLE_COL_TYPE_STRING,
								  BothHelpObjs.TABLE_COL_TYPE_STRING};
      mySendObj.put("BASKET_COL",baskColsNew);
      mySendObj.put("BASKET_COL_WIDTHS",baskColWidths);
      mySendObj.put("BASKET_COL_TYPES",baskColTypes);
      Hashtable h = new Hashtable();
      for (i = 0; i < baskColsNew.length; i++) {
        h.put(baskColsNew[i],new Integer(i));
      }
      mySendObj.put("KEY_COLUMNS",h);
      //System.out.println("------- key col: "+h);
      Hashtable baskStyle = new Hashtable();
      //border color and inset
      baskStyle.put("BASKET_COLOR",new Color(255,255,255));
      baskStyle.put("BASKET_INSET_TOP",new Integer(0));
      baskStyle.put("BASKET_INSET_LEFT",new Integer(1));
      baskStyle.put("BASKET_INSET_BOTTOM",new Integer(1));
      baskStyle.put("BASKET_INSET_RIGHT",new Integer(1));
      baskStyle.put("BASKET_INSET_ALIGN",new Integer(3));
      //font and foreground and background color for columns heading
      baskStyle.put("BASKET_FONT_NAME","Dialog");
      baskStyle.put("BASKET_FONT_STYLE",new Integer(0));
      baskStyle.put("BASKET_FONT_SIZE",new Integer(10));
      baskStyle.put("BASKET_FOREGROUND",new Color(255,255,255));
      baskStyle.put("BASKET_BACKGROUND",new Color(0,0,66));
      //to use color rendering and border feature we must pre-define the number of rows
      baskStyle.put("BASKET_TABLE_ROW",new Integer(50));
      baskStyle.put("EDITABLE_FLAG",new Integer(BothHelpObjs.mypow(2,4)+BothHelpObjs.mypow(2,7)+BothHelpObjs.mypow(2,11)));

      mySendObj.put("BASKET_STYLE",baskStyle);

      //part catalog table
      String[] catColsNew = {"Part","Description","Type","Price","Shelf Life @ Storage Temp","Items/Part","Item","Item Component","Packaging","Manufacturer","Manufacturer's Part Number","Status"};
      int[] catColWidths = {100,80,70,55,100,55,55,55,55,55,55,55};
      int[] catColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
      mySendObj.put("CATALOG_COL",catColsNew);
      mySendObj.put("CATALOG_COL_WIDTHS",catColWidths);
      mySendObj.put("CATALOG_COL_TYPES",catColTypes);
      //item catalog table
      String[] itemCatCols = {" \nItem\n "," \nComponent\n "," \nComponent\nDescription"," \nGrade\n "," \nPackaging\n "," \nManufacturer\n "," \nCountry\n ","Mfg\nPart\nNo.","Mfg Shelf Life\n@\nStorage Temp","MSDS\nOn\nLine"," \nSample\nSize"," \nEng\nEval"};
      int[] itemCatColWidths = {50,0,190,80,100,155,80,65,135,0,0,0};
      int[] itemCatColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
      mySendObj.put("ITEM_CATALOG_COL",itemCatCols);
      mySendObj.put("ITEM_CATALOG_COL_WIDTHS",itemCatColWidths);
      mySendObj.put("ITEM_CATALOG_COL_TYPES",itemCatColTypes);
      Hashtable catStyle = new Hashtable();
      //border color and inset
      catStyle.put("CATALOG_COLOR",new Color(255,255,255));
      catStyle.put("CATALOG_INSET_TOP",new Integer(0));
      catStyle.put("CATALOG_INSET_LEFT",new Integer(1));
      catStyle.put("CATALOG_INSET_BOTTOM",new Integer(1));
      catStyle.put("CATALOG_INSET_RIGHT",new Integer(1));
      catStyle.put("CATALOG_INSET_ALIGN",new Integer(3));
      //font and foreground and background color for columns heading
      catStyle.put("CATALOG_FONT_NAME","Dialog");
      catStyle.put("CATALOG_FONT_STYLE",new Integer(0));
      catStyle.put("CATALOG_FONT_SIZE",new Integer(10));
      catStyle.put("CATALOG_FOREGROUND",new Color(255,255,255));
      catStyle.put("CATALOG_BACKGROUND",new Color(0,0,66));
      mySendObj.put("CATALOG_STYLE",catStyle);


      //12-04-01 find out to see if payload id had a request associated with it
      PurchaseRequest pr = new PurchaseRequest(db);
      Boolean aribaLogon = (Boolean)inData.get("ARIBA_LOGON");
      if (aribaLogon.booleanValue()) {
        String payLoadID = (String)inData.get("PAYLOAD_ID");
        if ("FEC".equalsIgnoreCase(db.getClient())) {
          //EBP can't handle the same MR
          /*reload MR from shopping cart ID here
          String editMR = pr.getMRForPayLoadID("max(pr_number) pr_number","pr_number","request_line_item",payLoadID);
          if (!BothHelpObjs.isBlankString(editMR)) {
            inData.put("UPDATE_MR", new Boolean(true));
            inData.put("PR_NUMBER", new Integer(editMR));
          }
          pr.deletePunchoutFlagByPayLoadId(payLoadID);
          */
        }else if ("Seagate".equalsIgnoreCase(db.getClient())) {
          String editMR = pr.getMRForPayLoadID(payLoadID);
          if (!BothHelpObjs.isBlankString(editMR)) {
            inData.put("UPDATE_MR", new Boolean(true));
            inData.put("PR_NUMBER", new Integer(editMR));
            pr.updatePayLoadForMR(editMR, payLoadID);
          }
        }
      }

      //if user choose to return to catalog from MR screen then get info
      Boolean updateMR = (Boolean)inData.get("UPDATE_MR");
      if (updateMR.booleanValue()) {
        Integer prNum = (Integer)inData.get("PR_NUMBER");
        Vector v = pr.getUpdateMRInfo(prNum.intValue());
        mySendObj.put("UPDATE_MR_INFO",v);
        //getting the account sys id for the give purchase_request
        Hashtable hh = (Hashtable)v.firstElement();
        mySendObj.put("ACCOUNT_SYS_ID",(String)hh.get("ACCOUNT_SYS_ID"));
      }
      mySendObj.put("UPDATE_MR",updateMR);
      //3-21-02
      mySendObj.put("ARIBA_REPAIR",new Boolean(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'Ariba Repair' and personnel_id = "+userid) > 0));
      mySendObj.put("ECOM_BY_PASS",new Boolean(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'EcomByPass' and personnel_id = "+userid) > 0));
      //4-9-02 have Eng Eval
      mySendObj.put("ENG_EVAL",new Boolean(HelpObjs.countQuery(db,"select count(*) from tcmis_feature where feature = 'engEval' and feature_mode = 1") > 0));
      //list of catalog(s) for each fac pref
      radian.tcmis.server7.share.dbObjs.Catalog catalog = new radian.tcmis.server7.share.dbObjs.Catalog(db);
      mySendObj.put("FACILITY_CATALOG",catalog.getFacilityCatalog(userid));
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void getSpecMat() {
     String itemid_cat = (String)inData.get("ITEMID_CAT");
     String facid_cat = (String)inData.get("FACID_CAT");
     String partid_cat = (String)inData.get("PARTID_CAT");
     String material_desc = (String)inData.get("MATERIAL_DESC");
    try {
     ClientPart  cp = new ClientPart(db);
     cp.setItemId((new Integer(itemid_cat)).intValue());
     cp.setFacilityId(facid_cat);
     cp.setPartId(partid_cat);
     cp.load();
     String temp = cp.getSpecId();
     String specid = new String((temp==null?"":temp));

     temp = cp.getMaterialId(material_desc).toString();
     mySendObj.put("MAT_ID",new String((temp==null?"":temp)));
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

} //end of class
