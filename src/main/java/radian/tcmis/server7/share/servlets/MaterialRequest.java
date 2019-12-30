package radian.tcmis.server7.share.servlets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import com.actuate.ereport.common.IViewerInterface;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.BothHelpObjs;
//import radian.tcmis.cxml.CreationLogic;
//ACJEngine
import radian.tcmis.both1.reports.ChargeNum;
import radian.tcmis.both1.reports.Delivery;
import radian.tcmis.both1.reports.MaterialDescription;
import radian.tcmis.both1.reports.MaterialRequestReport;
import radian.tcmis.both1.reports.MrNotes;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * <p>Title:Material Request</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Trong Ngo
 * @version 1.0
 *
 * 09-15-03 Getting the template Path info from the tcmISresourceBundle. Passing the cancel notes when a MR is requested for cancel
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

public class MaterialRequest extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;
  String userId = null;
  String reqNum = null;

  //ACJEngine
  ACJEngine erw = null;
  OutputStream os = null;
  TemplateManager tm = null;
  ACJOutputProcessor ec = null;
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

  public MaterialRequest(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
    inData = null;
    userId = null;
    reqNum = null;
  }

  protected void getResult(){
    //System.out.println("------ here in material request");
    long sTime = new java.util.Date().getTime();
    mySendObj = new Hashtable();
    inData = (Hashtable)myRecvObj;
    function = inData.get("FUNCTION").toString();
    userId = inData.get("USER_ID").toString();
    reqNum = inData.get("REQ_NUM").toString();
    //System.out.println("\n\n=========== in Data material request: "+inData);
    if(function.equalsIgnoreCase("GET_SCREEN_DATA")) {
      getScreenData();
    }else if(function.equalsIgnoreCase("CHECK_BYPASS")) {
      checkByPass();
    }else if(function.equalsIgnoreCase("DELETE_MR")) {
      deleteMr();
    }else if(function.equalsIgnoreCase("DELETE_LINE")) {
      deleteLine();
    }else if(function.equalsIgnoreCase("DELETE_MR_SEARCH")) {
      deleteMrSearch();
    }else if(function.equalsIgnoreCase("DELETE_LINE_SEARCH")) {
      deleteLineSearch();
    }else if(function.equalsIgnoreCase("APPROVE")) {
      approveMr();
    }else if(function.equalsIgnoreCase("USE_APPROVE")) {
      approveUsage();
    }else if(function.equalsIgnoreCase("RELEASE")) {
      releaseMr();
    }else if(function.equalsIgnoreCase("SAVE")) {
      saveMr();
    }else if(function.equalsIgnoreCase("REJECT")) {
      rejectMr();
    }else if(function.equalsIgnoreCase("SUBMIT")) {
      submitMr();
    }else if(function.equalsIgnoreCase("UPDATE")) {
      updateMr();
    }else if(function.equalsIgnoreCase("SAVE_DELIVERY_SCHEDULE")) {
      saveDeliverySchedule();
    }else if(function.equalsIgnoreCase("CANCEL_ORDER")) {
      cancelOrder();
      //not yet working requestQtyChange();
    }else if(function.equalsIgnoreCase("PRINTSCREEN")) {
      printScreen();
    }else if(function.equalsIgnoreCase("GET_USE_APPROVAL_INFO")) {
      getUseApprovalInfo();
    }else if(function.equalsIgnoreCase("REJECT_USE_APPROVE")) {
      rejectUseApprove();
    }else if(function.equalsIgnoreCase("REJECT_LINE_USE_APPROVE")) {
      rejectLineUseApprove();
    }else if(function.equalsIgnoreCase("GET_FINANCE_APPROVER_INFO")) {
      getFinanceApproverInfo();
    }else if(function.equalsIgnoreCase("GET_LIST_APPROVAL_INFO")) {
      getListApprovalInfo();
    }
    //System.out.println("\n\n======== my output: "+mySendObj); \
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new java.util.Date(min));
    //System.out.println("\n\n---- NChemObj: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds");
  }

  protected void getFinanceApproverInfo() {
    String prNum = (String)inData.get("REQ_NUM");
    String status = (String)inData.get("STATUS");
    try {
      FinanceApproverList fal = new FinanceApproverList(db);
      if (status.equalsIgnoreCase("PENDING")) {
        mySendObj.put("FINANCE_APPROVER_INFO",fal.getAllApproversForPR(prNum));
        mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      }else{
        mySendObj.put("FINANCE_APPROVER_INFO",fal.getApprovedApprover(prNum));
      }
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch (Exception e) {
       e.printStackTrace();
       mySendObj.put("RETURN_CODE",new Boolean(false));
       mySendObj.put("MSG","An error occurred while loading use approver(s) info.\nPlease restart tcmIS and try again.");
    }
  }

  protected void getUseApprovalInfo() {
    String prNum = (String)inData.get("REQ_NUM");
    String lineItem = (String)inData.get("LINE_ITEM");
    String status = (String)inData.get("STATUS");
    try {
      UseApprover ua = new UseApprover(db);
      Integer pr = new Integer(prNum);
      mySendObj.put("USE_APPROVAL_INFO",ua.getUseApproverInfoPerLine(pr.intValue(),lineItem,status));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch (Exception e) {
       e.printStackTrace();
       mySendObj.put("RETURN_CODE",new Boolean(false));
       mySendObj.put("MSG","An error occurred while loading use approver(s) info.\nPlease restart tcmIS and try again.");
    }
  }

  protected void getListApprovalInfo() {
    String prNum = (String)inData.get("REQ_NUM");
    String lineItem = (String)inData.get("LINE_ITEM");
    String status = (String)inData.get("STATUS");
    try {
      ListApprover ua = new ListApprover(db);
      Integer pr = new Integer(prNum);
      mySendObj.put("LIST_APPROVAL_INFO",ua.getListApproverInfoPerLine(pr.intValue(),lineItem,status));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch (Exception e) {
       e.printStackTrace();
       mySendObj.put("RETURN_CODE",new Boolean(false));
       mySendObj.put("MSG","An error occurred while loading list approver(s) info.\nPlease restart tcmIS and try again.");
    }
  }

  protected void printScreen() {
    Hashtable headerInfo = (Hashtable)inData.get("HEADER_INFO");
    Vector lineItem = (Vector)inData.get("DETAIL_INFO");
    //System.out.println("\n\n--------- line item: "+lineItem);
    String url = buildTest(lineItem,headerInfo);
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

  protected String buildTest(Vector lineItems, Hashtable headerInfo) {
    String url = "";
    erw = new ACJEngine();
    ec = new ACJOutputProcessor();

    erw.setDebugMode( true );
    erw.setX11GfxAvailibility( false );
    erw.setTargetOutputDevice( ACJConstants.PDF );

    String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
    String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
    ec.setPathForFontMapFile( fontmappath );

    try {
      String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
      erw.readTemplate(""+templatpath+"mr.erw");
    }catch (Exception e) {
      e.printStackTrace();
      //System.out.println("ERROR loading template");
      return url;
    }

    AppDataHandler ds = new AppDataHandler();
    tm = erw.getTemplateManager();

    tm.setLabel("MR_NUM",(String)headerInfo.get("REQ_NUM"));
    tm.setLabel("FACILITY",(String)headerInfo.get("FACILITY"));
    tm.setLabel("END_USER",(String)headerInfo.get("END_USER"));
    tm.setLabel("DEPT",(String)headerInfo.get("DEPARTMENT"));
    tm.setLabel("NUM_LINES",headerInfo.get("NUMBER_OF_LINES").toString());
    tm.setLabel("STATUS",(String)headerInfo.get("STATUS_DESC"));
    tm.setLabel("REQ_DATE",(String)headerInfo.get("DATE"));
    tm.setLabel("REQUESTOR",(String)headerInfo.get("REQUESTOR"));
    tm.setLabel("LPP",(String)headerInfo.get("EXTENDED_LPP"));
    tm.setLabel("ACCT",(String)headerInfo.get("ACC_SYS_ID"));
    tm.setLabel("NOPOL","");

    String tmpReqNum = (String)headerInfo.get("REQ_NUM");
    String HomeDirectory=radian.web.tcmisResourceLoader.getsaveltempreportpath();
    reoprtlog.info("buildTest   Start "+tmpReqNum.toString()+"    Size: "+lineItems.size()+"  ");

    RegisterTable[] rt = new RegisterTable[5];
    rt[0] = new RegisterTable(MaterialRequestReport.getVector(lineItems,headerInfo),"MATREQ",
                              MaterialRequestReport.getFieldVector(),null);
    rt[1] = new RegisterTable(MaterialDescription.getVector(lineItems),"MATDESC",
                              MaterialDescription.getFieldVector(),null);
    rt[2] = new RegisterTable(ChargeNum.getVector(lineItems),"CHARGENUM",
                              ChargeNum.getFieldVector(),null);
    rt[3] = new RegisterTable(MrNotes.getVector(lineItems),"MRNOTES",
                              MrNotes.getFieldVector(),null);
    rt[4] = new RegisterTable(Delivery.getVector(lineItems),"DELINFO",
                              Delivery.getFieldVector(),null);
    for (int k = 0; k <rt.length; k++) {
      Vector v1 = rt[k].getData();
      Vector v2 = rt[k].getMethods();
      String name = rt[k].getName();
      String where = rt[k].getWhere();
      ds.RegisterTable(v1,name, v2,where);
    }
    try {
      erw.setDataSource(ds);
    }catch (Exception e) {
      //System.out.println("ERROR setting data source");
      e.printStackTrace();
      return url;
    }
    //erw.setCacheOption(true, ""+HomeDirectory+"mractive"+tmpReqNum.toString()+".joi");
    try {
      IViewerInterface ivi = erw.generateReport();
      if (!ec.setReportData(ivi,null)) System.exit(0);
    } catch(Exception ex){
      System.out.println("ERROR: While generating report");
      return url;
    }
    try{
      ec.generatePDF(""+HomeDirectory+"mractive"+tmpReqNum.toString()+".pdf",null);
    }catch (Exception e) {
      //System.out.println("ERROR generating report");
      e.printStackTrace();
      return url;
    }
	reoprtlog.info("buildTest    Done  "+tmpReqNum.toString()+"");
    return("/reports/temp/mractive"+tmpReqNum.toString()+".pdf");
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void getScreenData(){
    Hashtable header = new Hashtable();
    Vector detail = new Vector();
    try{
      RequestsScreen rs = new RequestsScreen(db);
      Vector v = rs.getScreenData(Integer.parseInt(userId),Integer.parseInt(reqNum));
      header = (Hashtable)v.elementAt(0);
      detail = (Vector)v.elementAt(1);
      mySendObj.put("HEADER_INFO",header);
      mySendObj.put("DETAIL_INFO",detail);
      mySendObj.put("NOW",Calendar.getInstance());

      //System.out.println("\n\n---------------- header: "+header+"\n\n"+detail);

    }catch(Exception e){e.printStackTrace();}
  }

  protected void rejectUseApprove(){
    goSubmitMr("REJECT_USE_APPROVE");
    getScreenData();
  }

  protected void rejectLineUseApprove(){
    goSubmitMr("REJECT_LINE_USE_APPROVE");
    getScreenData();
  }

  protected void approveUsage(){
    goSubmitMr("USE_APPROVE");
    getScreenData();
  }
  protected void approveMr(){
    goSubmitMr("APPROVE");
    getScreenData();
  }
  protected void releaseMr(){
    goSubmitMr("RELEASE");
    getScreenData();
  }
  protected void saveMr(){
    goSubmitMr("SAVE");
    getScreenData();
  }
  protected void submitMr(){
    goSubmitMr("SUBMIT");
    getScreenData();
  }
  protected void updateMr(){
    goSubmitMr("UPDATE");
    getScreenData();
  }

  protected void goSubmitMr(String action){
    try{
      //System.out.println("here in submit MR");
      mySendObj.put("POSTING_MSG",new Boolean(false));    //11-14-01 define here so that this variable always assigned to something
      RequestsScreen rs = new RequestsScreen(db);
      if (inData.containsKey("ORIG_MR_QUANTITY")) {
        rs.setOrigMRQuantity((Vector)inData.get("ORIG_MR_QUANTITY"));
        rs.setNewMRQuantity((Vector)inData.get("NEW_MR_QUANTITY"));
      }
      Hashtable h = rs.submitRequest(action,(Hashtable)inData.get("HEADER_INFO"),(Vector)inData.get("DETAIL_INFO"),userId,this.getBundle().get("DB_CLIENT").toString());
      Boolean b = (Boolean)h.get("RETURN_CODE");
      Hashtable retH = new Hashtable();
      retH.put("SUCCEEDED",b);
      if(b.booleanValue()){
        if(h.get("NEXT_STATUS").toString().equalsIgnoreCase("posubmit")){
          if (!"UPDATE".equalsIgnoreCase(action)) {
            rs.setReleaseInfo( (Hashtable) inData.get("HEADER_INFO"), (Vector) inData.get("DETAIL_INFO"));
          }
          //if user(s) logon as guest then don't create sales order
          String query = "select count(*) from personnel a, purchase_request b where lower(a.logon_id) like lower('guest%') and a.personnel_id = b.requestor and b.pr_number = "+reqNum;
          int count = HelpObjs.countQuery(db,query);
          if (count == 0 ) {
				 Hashtable headerInfo = (Hashtable)inData.get("HEADER_INFO");
				 Boolean aribaLogon = (Boolean)headerInfo.get("ARIBA_LOGON");
				//this is where I need to call Chuck class for xml document and url
				if (aribaLogon.booleanValue()) {
              //FOR SEAGATE go to CatalogNew - this is for clients that use RequestsScreen
              String payLoadID = (String)headerInfo.get("PAY_LOAD_ID");
              //System.out.println("\n\n--- my pay load ID: "+payLoadID);
              String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/cxml/punchout/checkout?payloadID="+payLoadID,"payloadID="+payLoadID,"text/html");
              Hashtable payloadH = rs.getPunchoutOrderMessage(payLoadID);
              mySendObj.put("XML_DOC",postingMsg);
              mySendObj.put("URL",(String)payloadH.get("url"));
              mySendObj.put("POSTING_MSG",new Boolean(true));
            }else {
              //send user an email stating that their MR is now released to us
              //don't send users email if user click on submit and no approver was needed
              if ("APPROVE".equalsIgnoreCase(action) || "RELEASE".equalsIgnoreCase(action) || "USE_APPROVE".equalsIgnoreCase(action)) {
                MaterialRequestObj materialRequestObj = new MaterialRequestObj(db);
                materialRequestObj.sendReleasedMsgToUser((String)((Hashtable)inData.get("HEADER_INFO")).get("REQ_NUM"));
              }
              //now allocate MR
              try {
                String[] args = new String[1];
                args[0] = (String)((Hashtable)inData.get("HEADER_INFO")).get("REQ_NUM");
                db.doProcedure("p_mr_allocate",args);
              }catch(Exception dbe) {
                HelpObjs.sendMail(db,"p_mr_allocate error (pr_number: "+(String)((Hashtable)inData.get("HEADER_INFO")).get("REQ_NUM")+")","Error occured while trying to call procedure",86030,false);
              }
            }
          }
        }
      }else{
        retH.put("MSG",h.get("MSG").toString());
      }
      retH.put("ERROR_ROW",(Integer)h.get("ERROR_ROW"));    //trong 3/28
      retH.put("ERROR_COL",(Integer)h.get("ERROR_COL"));    //trong 3/28
      retH.put("ERROR_LINE",(Integer)h.get("ERROR_LINE"));  //trong 3/28
      mySendObj.put("RETURN_CODE",retH);
      //System.out.println("\n\n\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% : " + mySendObj);
    }catch(Exception e){
      e.printStackTrace();
      Hashtable hh = (Hashtable)inData.get("HEADER_INFO");
      HelpObjs.sendMail(db,"Error in goSubmitMr (pr_number: "+(String)hh.get("REQ_NUM")+")","Error occured while submitting material request",86030,false);
    }
  }

  protected void rejectMr(){
    try{
      PurchaseRequest PR = new PurchaseRequest(db);
      PR.setPRNumber((new Integer(reqNum)).intValue());
      PR.insert("pr_status","rejected",PurchaseRequest.STRING);
      PR.insert("rejection_reason",inData.get("REJECT_REASON").toString(),PurchaseRequest.STRING);
      PR.insert("requested_finance_approver",inData.get("USER_ID").toString(),PurchaseRequest.INT);
      PR.commit();
      //update request_line_item
      db.doUpdate("update request_line_item set request_line_status = 'Rejected', category_status = 'Rejected' where pr_number = "+reqNum);
      RequestsScreen rs = new RequestsScreen(db);
      rs.sendRejectMsgToUser((new Integer(reqNum)).intValue(),new Vector(0));
    }catch(Exception e){
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED",new Boolean(false));
      rCode.put("MSG","Error on rejecting Material Request.");
      mySendObj.put("RETURN_CODE",rCode);
      getScreenData();
      return;
    }
    Hashtable rCode = new Hashtable();
    rCode.put("SUCCEEDED",new Boolean(true));
    mySendObj.put("RETURN_CODE",rCode);
    getScreenData();
  }

  protected void checkByPass(){
    Hashtable h = new Hashtable();
    try{
      RequestsScreen rs = new RequestsScreen(db);
      h = rs.checkByPass((Hashtable)inData.get("HEADER_INFO"),(Vector)inData.get("DETAIL_INFO"));
      mySendObj.put("BYPASS",h);
    }catch(Exception e){e.printStackTrace();}
  }
  protected void deleteLine(){
    try{
      RequestsScreen reqS = new RequestsScreen(db);
      String itemnum = inData.get("SELECTED_LINE").toString();
      Hashtable reqH = reqS.deleteLineItem((new Integer(reqNum)).intValue(),itemnum);
      Boolean go = (Boolean)reqH.get("GO");
      if(!go.booleanValue()){
        Hashtable rCode = new Hashtable();
        rCode.put("SUCCEEDED",new Boolean(false));
        rCode.put("MSG",reqH.get("MSG"));
        mySendObj.put("RETURN_CODE",rCode);
        getScreenData();
        return;
      }
    }catch(Exception e){
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED",new Boolean(false));
      rCode.put("MSG","Error on deleting Line Item.");
      mySendObj.put("RETURN_CODE",rCode);
      getScreenData();
      return;
    }
    Hashtable rCode = new Hashtable();
    rCode.put("SUCCEEDED",new Boolean(true));
    mySendObj.put("RETURN_CODE",rCode);
    getScreenData();
  }
  protected void deleteMr(){
    try{
      int reqnum = Integer.parseInt(inData.get("REQ_NUM").toString());
      PRAccount PRA = new PRAccount(db);
      PRA.setPRNumber((new Integer(reqnum)).intValue());
      PRA.delete();
      RequestLineItem RLI =  new RequestLineItem(db);
      RLI.setPRNumber((new Integer(reqnum)).intValue());
      RLI.delete();
      PurchaseRequest PR = new PurchaseRequest(db);
      PR.setPRNumber((new Integer(reqnum)).intValue());
      PR.delete();
    }catch(Exception e){
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED",new Boolean(false));
      rCode.put("MSG","Error on deleting Material Request.");
      mySendObj.put("RETURN_CODE",rCode);
      getScreenData();
      return;
    }

    Hashtable rCode = new Hashtable();
    rCode.put("SUCCEEDED",new Boolean(true));
    mySendObj.put("RETURN_CODE",rCode);
  }

  protected void deleteMrSearch(){
    try{
      int reqnum = Integer.parseInt(inData.get("REQ_NUM").toString());
      PRAccount PRA = new PRAccount(db);
      PRA.setPRNumber((new Integer(reqnum)).intValue());
      PRA.delete();
      RequestLineItem RLI =  new RequestLineItem(db);
      RLI.setPRNumber((new Integer(reqnum)).intValue());
      RLI.delete();
      PurchaseRequest PR = new PurchaseRequest(db);
      PR.setPRNumber((new Integer(reqnum)).intValue());
      PR.delete();
    }catch(Exception e){
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED",new Boolean(false));
      rCode.put("MSG","Error on deleting Material Request.");
      mySendObj.put("RETURN_CODE",rCode);
      return;
    }

    Hashtable rCode = new Hashtable();
    rCode.put("SUCCEEDED",new Boolean(true));
    mySendObj.put("RETURN_CODE",rCode);
  }
  protected void deleteLineSearch(){
    try{
      RequestsScreen reqS = new RequestsScreen(db);
      String itemnum = inData.get("SELECTED_LINE").toString();
      Hashtable reqH = reqS.deleteLineItem((new Integer(reqNum)).intValue(),itemnum);
      Boolean go = (Boolean)reqH.get("GO");
      if(!go.booleanValue()){
        Hashtable rCode = new Hashtable();
        rCode.put("SUCCEEDED",new Boolean(false));
        rCode.put("MSG",reqH.get("MSG"));
        mySendObj.put("RETURN_CODE",rCode);
        return;
      }
    }catch(Exception e){
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED",new Boolean(false));
      rCode.put("MSG","Error on deleting Line Item.");
      mySendObj.put("RETURN_CODE",rCode);
      return;
    }
    Hashtable rCode = new Hashtable();
    rCode.put("SUCCEEDED",new Boolean(true));
    mySendObj.put("RETURN_CODE",rCode);
  }

  protected void saveDeliverySchedule(){
    try{
      DeliverySchedule ds = new DeliverySchedule(db);
      ds.setPrNum(Integer.parseInt(inData.get("REQ_NUM").toString()));
      ds.setLineNum(inData.get("LINE_NUM").toString());
      int userId = ((Integer)inData.get("USER_ID")).intValue();
      Vector v = (Vector)inData.get("DELIVERY_SCHEDULE");
      if (inData.containsKey("QTY")) {
        ds.setAllowQtyChange(true);
        ds.setQty(((BigDecimal)inData.get("QTY")));
      }
      ds.saveSchedule(v,userId,this.getBundle().get("DB_CLIENT").toString());
      //don't allocate if pr_status is not 'posubmit'
      if (ds.getChangedSchedule()) {
        try {
          String[] args = new String[2];
          args[0] = inData.get("REQ_NUM").toString();
          args[1] = inData.get("LINE_NUM").toString();
          db.doProcedure( "p_line_item_allocate",args );
        }catch(Exception dbe) {
          HelpObjs.sendMail(db,"p_line_item_allocate error schedule change (pr_number: "+inData.get("REQ_NUM").toString()+")","Error occured while trying to call procedure",86030,false);
        }
      }
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch(Exception e){
      mySendObj.put("RETURN_CODE",new Boolean(false));
      e.printStackTrace();
    }
  }

  // new
  protected void cancelOrder(){
    try{
      RequestsScreen rs = new RequestsScreen(db);
      String lineNum =  (String)inData.get("LINE_NUM");
      String user = (String)inData.get("USER_ID");
      String reqNum  =  (String)inData.get("REQ_NUM");
      String cancelnotes = "";
      if (inData.containsKey("CANCEL_NOTES")) {
        cancelnotes  =  (String)inData.get("CANCEL_NOTES");
      }
      rs.requestCancel(reqNum,lineNum,user,this.getBundle().get("DB_CLIENT").toString(),cancelnotes);
      mySendObj.put("MSG","Cancellation for Material Request "+reqNum+", line "+lineNum+" was submitted");
    }catch(Exception e){
      e.printStackTrace();
      mySendObj.put("MSG","Error: An email has been sent to system admin.\nContact your customer representative if you have any question.");
    }
  }

  protected void requestQtyChange(){
    try{
      RequestQtyChange rqc = new RequestQtyChange(db);
      mySendObj.put("QTY_INFO",rqc.getQtyChangeInfo((String)inData.get("REQ_NUM"),(String)inData.get("LINE_NUM")));
      mySendObj.put("MSG","OK");
    }catch(Exception e){
      e.printStackTrace();
      mySendObj.put("MSG","Error: An email has been sent to system admin.\nContact your customer representative if you have any question.");
      HelpObjs.sendMail(db,"Error while get qty change info (pr_number: "+inData.get("REQ_NUM").toString()+")","Error occured while getting qty change info: (requestor - "+(String)inData.get("USER_ID")+")",86030,false);
    }
  }

  /* old
    protected void cancelOrder(){
    try{
      RequestsScreen rs = new RequestsScreen(db);
      String lineNum =  (String)inData.get("LINE_NUM");
      String user = (String)inData.get("USER_ID");
      String reqNum  =  (String)inData.get("REQ_NUM");

      //5-23-01 if there is sales order then give the user an error message
      RequestLineItem rli = new RequestLineItem(db);
      rli.setLineItem(lineNum);
      rli.setPRNumber(Integer.parseInt(reqNum));
      rli.load();
      if (BothHelpObjs.isBlankString(rli.getSalesOrder())) {
        mySendObj.put("MSG","Material Request "+reqNum+", line "+lineNum+".\nSales order processing is still in progress \nand cannot be cancelled at this time.\nPlease try again later.");
      }else {
        rs.requestCancel(reqNum,lineNum,user,this.getBundle().get("DB_CLIENT").toString());
        mySendObj.put("MSG","Cancellation for Material Request "+reqNum+", line "+lineNum+" was submitted");
      }
    }catch(Exception e){
      e.printStackTrace();
      mySendObj.put("MSG","An error has occurr:\n"+e.getMessage());
    }
  }*/


  void callSalesOrder(Integer prn){
    try {

      int port = Integer.parseInt((String) this.getBundle().get("SALES_ORDER_PORT"));
      String uri  = (String) this.getBundle().get("SALES_ORDER_URI");
      String host = (String) this.getBundle().get("SALES_ORDER_LOCATION");
      String sData = "prnum="+prn.toString();


      //********* the line below will call some cgi to create saleOrder ********* set for production!!!!
      URL url = new URL("http",host,port,uri + "?" + new String(sData.getBytes()) );
      //URL url = new URL("http://129.160.17.32/servlet/radian.tcmis.server7.client.servlets.RayBuildSalesOrder?" + new String(sData.getBytes()) );

      //System.out.println("Calling : " + url.toString());
      HelpObjs.monitor(1,"Calling : " + url.toString(),null);

      SSLURLConnection c = new SSLURLConnection(url);
      c.connect();
      c.sendData("GET",sData.getBytes());
      InputStream in = c.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String bug = null;
      while (true){
         bug=br.readLine();
         if (bug==null) break;
         //System.out.println(bug);
      }
      in.close();
      // HelpObjs.monitor(1,"pr:"+prn+" released",null);
      //System.out.println(" ***** Done Calling SalesOrder **** ");
    }catch(Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object(HelpObjs): " + e.getMessage(),null);
    }
  }
}