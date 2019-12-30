package radian.tcmis.server7.share.servlets;

//ACJEngine
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TcmISFeature;
import radian.tcmis.server7.share.dbObjs.WRAccount;
import radian.tcmis.server7.share.dbObjs.WasteContainer;
import radian.tcmis.server7.share.dbObjs.WastePickupLocation;
import radian.tcmis.server7.share.dbObjs.WastePickupRequestScreen;
import radian.tcmis.server7.share.dbObjs.WasteProfile;
import radian.tcmis.server7.share.dbObjs.WasteRequest;
import radian.tcmis.server7.share.dbObjs.WasteRequestLineItem;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.actuate.ereport.common.IViewerInterface;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.WasteChargeNum;
import radian.tcmis.both1.reports.WasteMgtRequest;
import radian.tcmis.both1.reports.WasteContainerReport;
import radian.tcmis.both1.reports.RegisterTable;

public class Waste extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  //ACJEngine
  ACJEngine erw = null;
  OutputStream os = null;
  TemplateManager tm = null;
  ACJOutputProcessor ec = null;

  public static final String[] mainCols = {"Rad Profile","Row Number","Vendor Profile","Description","Qty","Packaging","Vendor",
                                           "Work Area","Waste Mgmt Option","Replace Container","Notes","Transferred","Work Area Id","Waste Tag #"};

  public Waste(ServerResourceBundle b, TcmISDBModel d ){
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
    //System.out.println("inData:"+inData.toString());
    function = inData.get("ACTION").toString();
    if(function.equalsIgnoreCase("SEARCH")) {
      doSearch();
    }else if(function.equalsIgnoreCase("CREATE_WASTE_REQUEST")) {
      createWasteRequest();
    }else if(function.equalsIgnoreCase("GET_WASTE_REQUEST_DATA")) {
      getWasteRequestData();
    }else if(function.equalsIgnoreCase("ADD_PICKUP_LOCATION")) {
      addPickupLocation();
    }else if(function.equalsIgnoreCase("CHANGE_PICKUP_LOCATION_DESC")) {
      changePickupLocationDesc();
    }else if(function.equalsIgnoreCase("GET_LOCATION_DATA")) {
      loadPickupLocationScreen();
    }else if(function.equalsIgnoreCase("DELETE_WR")) {
      deleteWr();
    }else if(function.equalsIgnoreCase("DELETE_LINE")) {
      deleteLine();
    }else if(function.equalsIgnoreCase("SUBMIT")) {
      submitWr();
    }else if(function.equalsIgnoreCase("SAVE")) {
      saveWr();
    }else if(function.equalsIgnoreCase("GETUSERNAME")) {
      getUserName();
    }else if(function.equalsIgnoreCase("GET_INITIAL_INFO")) {
      getInitialInfo();
    }else if(function.equalsIgnoreCase("CHANGE_QTY")) {
      changeQty();
    }else if(function.equalsIgnoreCase("IS_WASTE_MANAGER")) {
      isWasteManager();
    }else if(function.equalsIgnoreCase("PRINT_SCREEN")) {
      printScreen();
    }
    //System.out.println("outData:"+mySendObj);
  }

  protected void printScreen() {
    Hashtable headerInfo = (Hashtable)inData.get("HEADER_INFO");
    Vector lineItem = (Vector)inData.get("DETAIL_INFO");
    String url = buildReportUrl(lineItem,headerInfo);
    Hashtable retH = new Hashtable();
    if (url.length() > 0) {
      retH.put("SUCCEEDED",new Boolean(true));
    }else {
      retH.put("SUCCEEDED",new Boolean(false));
    }
    retH.put("MSG"," Generating report failed.\n Please restart tcmIS and try again.\n If problem recurs, contact tcmIS Customer Service Representative (CSR).");
    mySendObj.put("RETURN_CODE",retH);
    mySendObj.put("URL",url);
  }

  protected String buildReportUrl(Vector lineItems, Hashtable headerInfo) {
    String url = "";
    erw = new ACJEngine();
    erw.setDebugMode(true);
    erw.setX11GfxAvailibility(false);
    erw.setTargetOutputDevice(ACJConstants.PDF);
    ec = new ACJOutputProcessor();
		String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
		ec.setPathForFontMapFile(fontmappath);
    try {
      String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
      erw.readTemplate(templatpath+"wasteReq.erw");
    }catch (Exception e) {
      //System.out.println("ERROR loading template");
      e.printStackTrace();
      return url;
    }

    AppDataHandler ds = new AppDataHandler();
    tm = erw.getTemplateManager();

    Integer wr = (Integer)headerInfo.get("REQ_NUM");
    tm.setLabel("LAB018",wr.toString());
    tm.setLabel("LAB019",(String)headerInfo.get("FAC_NAME"));
    int lines = lineItems.size();
    Integer myLines = new Integer(lines);
    tm.setLabel("LAB021",myLines.toString());
    tm.setLabel("LAB022",(String)headerInfo.get("STATUS_DESC"));
    tm.setLabel("LAB023",(String)headerInfo.get("DATE"));
    tm.setLabel("LAB020",(String)headerInfo.get("REQ_NAME"));
    String elpp = (String)headerInfo.get("EXTENDED_LPP");
    tm.setLabel("LAB024",elpp);
    tm.setLabel("LAB025",(String)headerInfo.get("ACC_SYS_ID"));

    RegisterTable[] rt = new RegisterTable[3];
    rt[0] = new RegisterTable(WasteMgtRequest.getVector(lineItems,headerInfo),"WASTEMGTREQ",
                              WasteMgtRequest.getFieldVector(),null);
    rt[1] = new RegisterTable(WasteChargeNum.getVector(lineItems),"WASTECHARGENUM",
                              WasteChargeNum.getFieldVector(),null);
    rt[2] = new RegisterTable(WasteContainerReport.getVector(lineItems),"WASTECONT",
                              WasteContainerReport.getFieldVector(),null);
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
    String HomeDirectory=radian.web.tcmisResourceLoader.getsaveltempreportpath();
    erw.setCacheOption(true, ""+HomeDirectory+"wtractive"+wr.toString()+".joi");
    try {
      IViewerInterface ivi = erw.generateReport();
      if (!ec.setReportData(ivi,null)) System.exit(0);
    } catch(Exception ex){
      System.out.println("ERROR: While generating report");
      return url;
    }
    try {
      ec.generatePDF(HomeDirectory+"wtractive"+wr.toString()+".pdf",null);
    }catch (Exception e) {
      //System.out.println("ERROR generating report");
      e.printStackTrace();
      return url;
    }
    return(HomeDirectory+"wtractive"+wr.toString()+".pdf");
  } //end of method


  //trong 8-31
  public void isWasteManager(){
    Integer uid = (Integer)inData.get("USER_ID");
    try{
      String q = "select count(*) from user_group_member ";
      q = q + "where personnel_id = "+uid.intValue()+" and user_group_id = 'WasteManager'";
      int count = DbHelpers.countQuery(db,q);
      mySendObj.put("WASTE_MANAGER_FOR",new Integer(count));
      mySendObj.put("CAN_CREATE_REPORT",new Integer(canCreateReport(uid)));
      mySendObj.put("TCMIS_FEATURE",getTcmISMainFeatures());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  // 6-1-01
  public Hashtable getTcmISMainFeatures() {
    Hashtable value = null;;
    try {
      TcmISFeature tcmISFeature = new TcmISFeature(db);
      value = tcmISFeature.getTcmISMainFeatures();
    }catch(Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  //4-24-01
  public int canCreateReport(Integer uid) {
    int value = 0;
    try {
      String query = "select count(*) from user_group_member";
      query += " where personnel_id = "+uid.intValue()+" and user_group_id = 'CreateReport'";
      value = DbHelpers.countQuery(db,query);
    }catch(Exception e) {
      e.printStackTrace();
    }
    return value;
  }


  public void getInitialInfo() {
    Integer uid = (Integer)inData.get("USER_ID");
    try {
      Personnel p = new Personnel(db);
      p.setPersonnelId(uid.intValue());
      p.load();
      mySendObj.put("USER_NAME",p.getFullName());
      mySendObj.put("PREF_FAC",p.getFacilityId());

      Hashtable initialInfo = WasteRequest.getInitialInfo(db,uid.intValue());
      mySendObj.put("INITIAL_INFO",initialInfo);
      //Nawaz 09/19/01
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      //System.out.println("\n\n\n========== initial info: " + mySendObj);

    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getUserName() {
    Integer uid = (Integer)inData.get("USER_ID");
    try {
      Personnel p = new Personnel(db);
      p.setPersonnelId(uid.intValue());
      p.load();
      mySendObj.put("USER_NAME",p.getFullName());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void submitWr() {
    goSubmitRequest("SUBMIT");
  }

  public void saveWr() {
    goSubmitRequest("SAVE");
  }

  public void changeQty() {
    goSubmitRequest("SAVE");
  }

   public void goSubmitRequest(String action) {
    Hashtable tempH = new Hashtable();
    try{
      WasteRequest wr = new WasteRequest(db);
      Hashtable h = wr.submitRequest(action,(Vector)inData.get("DETAIL_INFO"),(Hashtable)inData.get("HEADER_INFO"));
      tempH = (Hashtable)h.get("RETURN_CODE");
      Boolean b = (Boolean)tempH.get("SUCCEEDED");
      Hashtable rCode = new Hashtable();
      if(b.booleanValue()){
        if ("SUBMIT".equalsIgnoreCase(action)) {
          sendNotificationEmail( (Hashtable) inData.get("HEADER_INFO"));
        }
        rCode.put("SUCCEEDED", new Boolean(true));
        mySendObj.put("RETURN_CODE",rCode);
      }else{
        rCode.put("SUCCEEDED", new Boolean(false));
        rCode.put("MSG",tempH.get("MSG").toString());
        mySendObj.put("RETURN_CODE",rCode);
      }
    }catch(Exception e) {
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED", new Boolean(false));
      //rCode.put("MSG", "Error! Can't save/submit the current request.");
      rCode.put("MSG", tempH.get("MSG"));
      mySendObj.put("RETURN_CODE",rCode);
      return;
    }
  }

  protected void sendNotificationEmail(Hashtable headerInfo) {
    try {
      Integer reqNum = (Integer) headerInfo.get("REQ_NUM");
      //check to see if request needs notification
      WasteRequest wr = new WasteRequest(db);
      Hashtable h = wr.getTransferNotification(reqNum);
      Enumeration enuma = h.keys();
      String subject = "User requested waste pickup";
      while (enuma.hasMoreElements()) {
        String key = enuma.nextElement().toString();
        Vector v = (Vector)h.get(key);
        String message = "";
        String[] rec = new String[1];
        for (int i = 0; i < v.size(); i++) {
          message += v.elementAt(i).toString()+"\n";
        }
        message += "\n\nhttps://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/Register\n";
        HelpObjs.sendMail(subject,message,rec);
      }
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error in waste.sendNotificationEmail (waste_request_id: "+(Integer)headerInfo.get("REQ_NUM")+")","Error in waste.sendNotificationEmail",86030,false);
    }
  } //end of method

  protected void deleteLine(){
    //trong 8-16 save data before deleting selected line item
    goSubmitRequest("SAVE");
    try {
      WasteRequestLineItem wrli = new WasteRequestLineItem(db);
      Integer reqId = (Integer)inData.get("REQ_NUM");
      Integer lineItem = (Integer)inData.get("SELECTED_LINE");
      wrli.setWasteRequestId(reqId.intValue());
      wrli.setLineItem(lineItem.intValue());
      wrli.deleteLineItem();
    } catch (Exception e) {
      e.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED", new Boolean(false));
      rCode.put("MSG","Error on deleting selected line item ");
      mySendObj.put("RETURN_CODE",rCode);
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

      //delete all container for this request
      WasteContainer wc = new WasteContainer(db);
      wc.setWasteRequestId(reqNum.intValue());
      wc.deleteAllContainerForReq();

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
      Boolean previousTransfer = (Boolean)inData.get("PREVIOUS_TRANSFER");
      mySendObj.put("SEARCH_DATA",WasteProfile.doSearch(db,previousTransfer,inData.get("FAC_ID").toString(),inData.get("WORK_AREA").toString(),inData.get("SEARCH_STRING").toString(),false));
      // load the key-column hash
      Hashtable hash = new Hashtable();
      hash.put("PROFILE_ID",new Integer(WasteProfile.SEARCH_ITEM_ID_COL));
      hash.put("VENDOR_PROFILE",new Integer(WasteProfile.SEARCH_VENDOR_PROFILE_ID_COL));
      hash.put("VENDOR",new Integer(WasteProfile.SEARCH_VENDOR_ID_COL));
      hash.put("DESCRIPTION",new Integer(WasteProfile.SEARCH_DESC_COL));
      hash.put("PACKAGING",new Integer(WasteProfile.SEARCH_PACKAGING_COL));
      hash.put("CURRENCY_ID",new Integer(WasteProfile.SEARCH_CURRENCY_ID_COL));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      mySendObj.put("KEY_COLS",hash);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  protected void createWasteRequest(){
    try{
      Vector wasteReqV = (Vector)inData.get("WASTE_REQUEST");
      String facilityId = (String)inData.get("FAC_ID");
      String accSysId = (String)inData.get("ACC_SYS_ID");
      if(wasteReqV.size()<1){
        mySendObj.put("WASTE_REQUEST_NUM",new Integer(0));
        return;
      }
      int userId = Integer.parseInt(inData.get("USER_ID").toString());
      WasteRequest wr = WasteRequest.createWasteRequest(db,userId,facilityId,accSysId,wasteReqV);
      mySendObj.put("WASTE_REQUEST_NUM",new Integer(wr.getWasteRequestId()));

      //load waste transfer request screen from here
      Hashtable h = new Hashtable(3);
      WastePickupRequestScreen wprs = new WastePickupRequestScreen(db);
      h.put("SCREEN_DATA",wprs.getScreenData(wr.getWasteRequestId()));
      h.put("COLUMN_KEY",(Hashtable)getColKey());
      h.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      mySendObj.put("REQUEST_DATA",h);

    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void getWasteRequestData(){
    try{
      Integer reqNum = (Integer)inData.get("REQ_NUM");
      WastePickupRequestScreen wprs = new WastePickupRequestScreen(db);
      mySendObj.put("SCREEN_DATA",wprs.getScreenData(reqNum.intValue()));
      mySendObj.put("COLUMN_KEY",(Hashtable)getColKey());
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected Hashtable getColKey(){
    Hashtable h = new Hashtable();
    for (int i=0;i<mainCols.length;i++) {
      h.put(mainCols[i],String.valueOf(i));
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