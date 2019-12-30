package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
//import Acme.Crypto.*;
import java.awt.Color;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;


public class CheckLogon extends TcmisServletObj{
  //Client Version
  String o = null;
  String ou = null;
  String pwd = null;
  String cn = null;
  String userId = null;
  String passed = null;
  Vector groupids = null;
  Vector groupdesc = null;
  Vector groupFacId = null;
  String connection = null;
  String version = null;
  String dbSide = null;

  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;
  Vector containerId = null;

  public CheckLogon(ServerResourceBundle b, TcmISDBModel d ){
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

  protected void getResult() throws Exception {
    long sTime = new java.util.Date().getTime();
    mySendObj = new Hashtable();
    inData = (Hashtable)myRecvObj;
    //System.out.println("\n\n----- here in check logon: "+inData);
    boolean newStructure = false;
    if (inData.containsKey("NEW")) {
      newStructure = true;
    }
    o = (String)inData.get("O");
    ou = (String)inData.get("OU");
    pwd = (String)inData.get("PWD");
    cn = (String)inData.get("CN");
    version = (String)inData.get("VERSION");
    connection = (String)inData.get("CONNECTION");
    if(db.getDBUrl().indexOf("hawkdev")>0 || db.getDBUrl().indexOf("hawktst")>0){
      dbSide = "owldev";
    }else {
      dbSide = "owlprod";
    }
    String aribaLogon = (String)inData.get("ARIBA_LOGON");
    if (db==null) System.out.println("DB is NULL 01");
    Password pw = new Password(db,cn,pwd,aribaLogon);
    if (connection.equalsIgnoreCase("DEVELOPER")){
       passed = pw.checkForValidUser();
    } else {
       //System.out.println("Going as user");
       passed = pw.getAuthStatus();
    }
    userId = pw.getUserId();
    try{
      //System.out.println("Passed:"+passed+ " for userid:"+userId);
      if (passed.equals("0")||passed.equals("99")) {  // 99- developer
        getUserGroup();
      }
      if (passed.equals("0")||passed.equals("99")){
        checkConnection();
        addUserInfo();
      } else {
        connection = "ENCRYPTED";
      }
      mySendObj.put("PASSED",passed);
      mySendObj.put("USER_ID",userId);
      mySendObj.put("CONNECTION",connection);
      mySendObj.put("DB_INST",dbSide);
      //get is waste manager
      Hashtable isWasteManager = new Hashtable(3);
      isWasteManager.put("WASTE_MANAGER_FOR",new Integer(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'WasteManager' and personnel_id = "+userId)));
      isWasteManager.put("CAN_CREATE_REPORT",new Integer(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'CreateReport' and personnel_id = "+userId)));
      TcmISFeature tf = new TcmISFeature(db);
      isWasteManager.put("TCMIS_FEATURE",tf.getTcmISMainFeatures());
      mySendObj.put("IS_WASTE_MANAGER_DATA",isWasteManager);
      mySendObj.put("YEAR_OF_REPORT",tf.getYearOfReports());
      if (newStructure) {
        getMyFacWorkAreaListInfo(userId,"Catalog");
      }else {
        //get fac loc app info
        getFacWorkAreaInfoNew(userId,"Catalog");
      }
      //get load all data
      loadAllData(userId,new Boolean("T".equalsIgnoreCase(aribaLogon)),(String)inData.get("PAYLOAD_ID"),newStructure);
      long eTime = new java.util.Date().getTime();
      long min = (eTime-sTime);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(new java.util.Date(min));
      //System.out.println("\n\n---- checklogon: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds - "+min);
    }catch(Exception e){e.printStackTrace();}
  }

  protected void loadAllData(String userid, Boolean aribaLogon, String payLoadID, boolean newStructure) {
    try {
      Hashtable loadAllDataInfo = new Hashtable(25);
      SearchPScreen scr = new SearchPScreen(db);
      scr.setPayloadId(payLoadID);
      Hashtable result = new Hashtable();
      //System.out.println("--------- here in loadAllData: "+db.getClient());
      if (newStructure) {
        result = scr.getMyFacWorkAreaList((new Integer(userid)).intValue());
        loadAllDataInfo.put("CAN_CREATE_MR_APP",(Hashtable) result.get("CAN_CREATE_MR_APP"));
        loadAllDataInfo.put("CAN_CREATE_FOR_POS",scr.getCreateMRForPOS(userid));
        loadAllDataInfo.put("POINT_OF_SALE_NAME","POS: ");
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
      loadAllDataInfo.put("USER_NAME",userNames);
      Vector facIDs = new Vector();
      for(i=0;i<facids.length;i++){
        facIDs.addElement((facids[i]));
      }
      loadAllDataInfo.put("FAC_ID",facIDs);
      Vector facNames = new Vector();
      for(i=0;i<facnames.length;i++){
        facNames.addElement((facnames[i]));
      }
      loadAllDataInfo.put("FAC_NAME",facNames);
      loadAllDataInfo.put("FACXAPP",facxapp);
		if (BothHelpObjs.isBlankString(userFac)) {
			if (facIDs.size() > 0) {
				userFac = facIDs.firstElement().toString();
			}else {
				userFac = "";
			}
		} 
		Vector facUser = new Vector();
      facUser.addElement(userFac);
      loadAllDataInfo.put("FAC_USER",facUser);
      loadAllDataInfo.put("APP_USER","");                       //7-03-02  until I figure out how to get the application_desc from application

      //getting facilities that are definited as ECOMMERCE or catalog add single work area
      loadAllDataInfo.put("FAC_IN_ECOM_LIST",FacPref.getFacilityInEComList(db,userid));
		loadAllDataInfo.put("CAT_ADD_FAC_SINGLE_APP_LIST",FacPref.getCatAddFacSingleAppList(db,userid));

		//filling catalog type combo box
      Vector myCatTypeV = new Vector(2);
      myCatTypeV.addElement("Part Catalog");
      myCatTypeV.addElement("Item Catalog");
      loadAllDataInfo.put("CATALOG_TYPE",myCatTypeV);

      //filling catalog view combo box
      Vector viewType = new Vector(1);
      //viewType.addElement("Part View");
      viewType.addElement("Detail");
      loadAllDataInfo.put("CATALOG_VIEW",viewType);

      //shopping cart table
      String[] baskColsNew = new String[]{"Work Area ID","Work Area","Part Number","Part Desc.","Part Qty","Ext. Price","Example Packaging","Date Needed","Item","Notes","Real Notes","Critical","Facility","PR","Catalog","Type","Example Item","Catalog Price","Unit Price","Min Buy","Part Group","Inventory Group","Dispensed UOM","Dispensed Item Type","Currency ID","Order Quantity Rule","Line Item","Catalog Company ID"};
      int []baskColWidths = new int[]{0,100,70,90,50,65,100,90,0,50,0,50,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
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
      loadAllDataInfo.put("BASKET_COL",baskColsNew);
      loadAllDataInfo.put("BASKET_COL_WIDTHS",baskColWidths);
      loadAllDataInfo.put("BASKET_COL_TYPES",baskColTypes);
      Hashtable h = new Hashtable(baskColsNew.length);
      for (i = 0; i < baskColsNew.length; i++) {
        h.put(baskColsNew[i],new Integer(i));
      }
      loadAllDataInfo.put("KEY_COLUMNS",h);
      Hashtable baskStyle = new Hashtable(13);
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
      loadAllDataInfo.put("BASKET_STYLE",baskStyle);

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
      loadAllDataInfo.put("CATALOG_COL",catColsNew);
      loadAllDataInfo.put("CATALOG_COL_WIDTHS",catColWidths);
      loadAllDataInfo.put("CATALOG_COL_TYPES",catColTypes);
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
      loadAllDataInfo.put("ITEM_CATALOG_COL",itemCatCols);
      loadAllDataInfo.put("ITEM_CATALOG_COL_WIDTHS",itemCatColWidths);
      loadAllDataInfo.put("ITEM_CATALOG_COL_TYPES",itemCatColTypes);
      Hashtable catStyle = new Hashtable(11);
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
      loadAllDataInfo.put("CATALOG_STYLE",catStyle);

      //12-04-01 find out to see if payload id had a request associated with it
      PurchaseRequest pr = new PurchaseRequest(db);
      Boolean updateMR = new Boolean(false);
      String editMRNo = null;
      if (aribaLogon.booleanValue()) {
        if ("FEC".equalsIgnoreCase(db.getClient())) {
          //EBP can't handle this
          /*reload MR from shopping cart ID here
          editMRNo = pr.getMRForPayLoadID("max(pr_number) pr_number","pr_number","request_line_item",payLoadID);
          if (!BothHelpObjs.isBlankString(editMRNo)) {
            updateMR = new Boolean(true);
          }
          pr.deletePunchoutFlagByPayLoadId(payLoadID);
          */
        }else if ("Seagate".equalsIgnoreCase(db.getClient())) {
          editMRNo = pr.getMRForPayLoadID(payLoadID);
          if (!BothHelpObjs.isBlankString(editMRNo)) {
            updateMR = new Boolean(true);
            pr.updatePayLoadForMR(editMRNo, payLoadID);
          }
        }
      }
      //if user choose to return to catalog from MR screen then get info
      if (updateMR.booleanValue()) {
        Integer prNum = new Integer(editMRNo);
        Vector v = pr.getUpdateMRInfo(prNum.intValue());
        loadAllDataInfo.put("UPDATE_MR_INFO",v);
        //getting the account sys id for the give purchase_request
        Hashtable hh = (Hashtable)v.firstElement();
        loadAllDataInfo.put("ACCOUNT_SYS_ID",(String)hh.get("ACCOUNT_SYS_ID"));
      }
      loadAllDataInfo.put("UPDATE_MR",updateMR);
      //3-21-02
      loadAllDataInfo.put("ARIBA_REPAIR",new Boolean(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'Ariba Repair' and personnel_id = "+userid) > 0));
      loadAllDataInfo.put("ECOM_BY_PASS",new Boolean(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'EcomByPass' and personnel_id = "+userid) > 0));
      //4-9-02 have Eng Eval
      loadAllDataInfo.put("ENG_EVAL",new Boolean(HelpObjs.countQuery(db,"select count(*) from tcmis_feature where feature = 'engEval' and feature_mode = 1") > 0));
      //list of catalog(s) for each fac pref
      radian.tcmis.server7.share.dbObjs.Catalog catalog = new radian.tcmis.server7.share.dbObjs.Catalog(db);
      loadAllDataInfo.put("FACILITY_CATALOG",catalog.getFacilityCatalog(userid));
      //04-17-03
      loadAllDataInfo.put("SCANNER_FUCNTIONS",new Boolean(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'Scanner Fucntions' and personnel_id = "+userid) > 0));
      loadAllDataInfo.put("INVENTORY_COUNT",new Boolean(HelpObjs.countQuery(db,"select count(*) from user_group_member where user_group_id = 'Inventory Count' and personnel_id = "+userid) > 0));
		loadAllDataInfo.put("COMPANY_ID",catalog.getCompanyId());

		mySendObj.put("LOAD_ALL_DATA",loadAllDataInfo);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  void getFacWorkAreaInfoNew(String userId, String currentScreen){
    try{
      String workAreaStatus = null;
      if (currentScreen.equalsIgnoreCase("Catalog") ||currentScreen.equalsIgnoreCase("NewChem")) {
        workAreaStatus = "A";
      }else {
        workAreaStatus = "B";
      }

      // get the fac/app data
      Facility f = new Facility(db);
      Hashtable facAppActSysInfo = new Hashtable();

      if (currentScreen.equalsIgnoreCase("ReportWin")) {
        facAppActSysInfo = f.getEveryReportFac(userId,workAreaStatus);
      }else {
        facAppActSysInfo = f.getEveryFacNew(userId,workAreaStatus);
      }
      Hashtable facLocAppInfo = new Hashtable(7);
      facLocAppInfo.put("ACC_SYS",(Hashtable)facAppActSysInfo.get("ACC_SYS"));
      facLocAppInfo.put("PREF_ACC_SYS",(Hashtable)facAppActSysInfo.get("PREF_ACC_SYS"));
      facLocAppInfo.put("FAC_IDS",(Vector)facAppActSysInfo.get("FAC_ID"));
      facLocAppInfo.put("FAC_NAMES",(Vector)facAppActSysInfo.get("FAC_NAME"));
      facLocAppInfo.put("APP_IDS",(Vector)facAppActSysInfo.get("APP_ID"));
      facLocAppInfo.put("APP_NAMES",(Vector)facAppActSysInfo.get("APP_DESC"));
      facLocAppInfo.put("FAC_X_APP",(Vector)facAppActSysInfo.get("FAC_X_APP"));
      mySendObj.put("FAC_LOC_APP_DATA",facLocAppInfo);
    }catch(Exception e){e.printStackTrace();}
  }

  void getMyFacWorkAreaListInfo(String userId, String currentScreen){
    try{
      String workAreaStatus = null;
      if (currentScreen.equalsIgnoreCase("Catalog") ||currentScreen.equalsIgnoreCase("NewChem")) {
        workAreaStatus = "A";
      }else {
        workAreaStatus = "B";
      }
      // get the fac/app data
      Facility f = new Facility(db);
      if (inData.containsKey("PAYLOAD_ID")) {
        f.setPayloadId( (String) inData.get("PAYLOAD_ID"));
      }

      Hashtable facAppActSysInfo = f.getMyFacWorkAreaList(userId,workAreaStatus,currentScreen);
      Hashtable facLocAppInfo = new Hashtable(7);
      facLocAppInfo.put("ACC_SYS",(Hashtable)facAppActSysInfo.get("ACC_SYS"));
      facLocAppInfo.put("PREF_ACC_SYS",(Hashtable)facAppActSysInfo.get("PREF_ACC_SYS"));
      facLocAppInfo.put("FAC_IDS",(Vector)facAppActSysInfo.get("FAC_ID"));
      facLocAppInfo.put("FAC_NAMES",(Vector)facAppActSysInfo.get("FAC_NAME"));
      facLocAppInfo.put("APP_IDS",(Vector)facAppActSysInfo.get("APP_ID"));
      facLocAppInfo.put("APP_NAMES",(Vector)facAppActSysInfo.get("APP_DESC"));
      facLocAppInfo.put("FAC_X_APP",(Vector)facAppActSysInfo.get("FAC_X_APP"));
      mySendObj.put("FAC_LOC_APP_DATA",facLocAppInfo);
    }catch(Exception e){e.printStackTrace();}
  } //end of method

  protected void getUserGroup() throws Exception {
      Personnel p =  new Personnel(db);
      p.setPersonnelId((new Integer(userId)).intValue());
      Vector v = p.getUserGroups();
      groupids = new Vector();
      groupdesc = new Vector();
      groupFacId = new Vector();
      for (int i=0;i<v.size();i++){
        UserGroup ug = (UserGroup)v.elementAt(i);
        groupids.addElement(ug.getGroupId());
        groupdesc.addElement(ug.getGroupDesc());
        groupFacId.addElement(ug.getGroupFacId());
      }
      mySendObj.put("GROUPIDS",groupids);
      mySendObj.put("GROUPDESC",groupdesc);
      mySendObj.put("GROUPFACS",groupFacId);
   }
   protected void checkConnection() throws Exception {
      connection = "ENCRYPTED";
      Authenticator auth = new Authenticator(db);
      auth.addConnectionType(token, connection);
   }
   protected void addUserInfo() throws Exception {
      Authenticator auth = new Authenticator(db);
      auth.addUserInfo(token,cn,ou,version);
   }
   protected void printGroups() throws IOException {
     if (groupids == null) return;
     for(int i=0;i<groupids.size();i++){
           out.println((groupids.elementAt(i)==null?"":groupids.elementAt(i).toString()));
     }
   }
   protected void printGroupsDesc() throws IOException {
     if (groupdesc == null) return;
     for(int i=0;i<groupdesc.size();i++){
           out.println((groupdesc.elementAt(i)==null?"":groupdesc.elementAt(i).toString()));
     }
   }
   protected void printGroupsFacId() throws IOException {
     if (groupFacId == null) return;
     for(int i=0;i<groupFacId.size();i++){
           out.println((groupFacId.elementAt(i)==null?"":groupFacId.elementAt(i).toString()));
     }
   }
   protected void printConnection() throws IOException {
     out.println(connection==null?" ":connection);
   }
   protected void printDBInstance() throws IOException {
     out.println(dbSide==null?"":dbSide);
   }

   protected  int getServInt(){
    return TcmisOutputStreamServer.CHECK_LOGON;
  }
}

