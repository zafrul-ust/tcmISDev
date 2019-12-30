package radian.tcmis.server7.share.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.text.*;
import Acme.Crypto.*;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;

public class NewChemical extends TcmisServlet{
//Initialize global variables

  //Client Version
  protected static final int SPEC_VALUES_PASSED = 7;

  String action = null;
  String userid = null;
  String facid = null;
  String colnum = null;
  String item = null;
  String reqid = null;
  String item_id = null;
  String fac_part_no = null;

  //new item
  Vector mfg   = null;
  Vector matdesc = null;
  Vector mfgpart = null;
  Vector size  = null;
  Vector unit = null;
  Vector pack = null;
  Vector msds = null;
  Vector mattype = null;

  // new part
  Vector newpart = null;
  Vector specs = null;
  Vector flows = null;

  // new approval
  String app = null;
  String rfac = null;
  Vector fate = null;
  Vector desc = null;
  Vector comp = null;
  Vector procu = null;

  //new grps
  Vector newgrp = null;


  //aproval process
  Vector roles_data = null;

  String reqstatus = null;

  Object[][] tabledata = null;
  String[] msgs = new String[2];
  Hashtable allData = null;

  Vector vvsize = null;
  Vector vvperiod = null;
  Vector vvpkg = null;
  Vector defGroup = null;
  Vector defGroupdesc = null;
  Vector defGroupFacId = null;

  public NewChemical(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){

   action = null;
   userid = null;
   facid = null;
   colnum = null;
   item = null;
   reqid = null;
   item_id = null;
   fac_part_no = null;

  //new item
   mfg   = null;
   matdesc = null;
   mfgpart = null;
   size  = null;
   unit = null;
   pack = null;
   msds = null;
   mattype = null;

  // new part
   newpart = null;
   specs = null;
   flows = null;

  // new approval
   app = null;
   rfac=null;
   fate = null;
   desc = null;
   comp = null;
   procu = null;

  //new grps
   newgrp = null;


  //aproval process
   roles_data = null;

   reqstatus = null;

   tabledata = null;
   msgs = new String [2];
   allData = null;

   vvsize = null;
   vvperiod = null;
   vvpkg = null;
   defGroup = null;
   defGroupdesc = null;
   defGroupFacId = null;
  }

  protected void getResult(HttpInput httpI) throws Exception {

    try {
      action    =  httpI.getString("ACTION");
      userid    =  httpI.getString("USERID");
      facid     =  httpI.getString("FACID");
      colnum    =  httpI.getString("COLNUM");
      item      =  httpI.getString("ITEM");
      reqid     =  httpI.getString("REQ_ID");
      reqstatus =  httpI.getString("REQ_STATUS");

      mfg      =  httpI.getVector("MFG");
      matdesc  =  httpI.getVector("MAT_DESC");
      mfgpart  =  httpI.getVector("MFG_PART");
      size     =  httpI.getVector("SIZE");
      unit     =  httpI.getVector("UNIT");
      pack     =  httpI.getVector("PACK");
      msds     =  httpI.getVector("MSDS");
      mattype  =  httpI.getVector("MAT_TYPE");

      newpart  =  httpI.getVector("NEW_PART_DATA");
      specs  =  httpI.getVector("SPECS");
      flows  =  httpI.getVector("FLOWS");

      app =  httpI.getString("APP");
      rfac =  httpI.getString("FAC");
      fate  =  httpI.getVector("FATE");
      comp  =  httpI.getVector("COMP");
      desc  =  httpI.getVector("DESC");
      procu  =  httpI.getVector("PROCU");

      newgrp = httpI.getVector("GRP_DATA");

      item_id = httpI.getString("ITEM_ID");

      fac_part_no = httpI.getString("FAC_PART_NO");

      roles_data = httpI.getVector("ROLES_DATA");

    } catch (Exception e){
      e.printStackTrace();
      out.printDebug("Error on Catalog:Allocating Resources" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      return;
    }

    if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"NewChecm:getResult = Going "+action,getBundle());
    if (action.equals("GET_TABLE_DATA")){
        getTableData();
    } else if (action.equals("BUILD_NEW_MATERIAL")) { //comming from the searchpt
        buildNewRequest();
    } else if (action.equals("BUILD_NEW_SIZE")) {
        buildNewSize();
    } else if (action.equals("BUILD_NEW_PART")) {
        buildNewPart();
    } else if (action.equals("BUILD_NEW_APPROVAL")) {
        buildNewApproval();
    } else if (action.equals("LOAD_SCREEN")) {
        getScreenData();
        getVVs();
        getGroups();
    } else if (action.equals("INSERT_NEW_ITEM")) {
        insertNewItem();
    } else if (action.equals("INSERT_NEW_PART")) {
        insertNewPart();
    } else if (action.equals("INSERT_NEW_APP")) {
        insertNewAppr();
    } else if (action.equals("INSERT_NEW_GRP")) {
        insertNewGrp();
    } else if (action.equals("APPROVE")) {
        approveRoles();
    } else if (action.equals("DELETE")) {
        deleteRequest();
    } else if (action.equals("APPROVE_UPDATE")){
        updateData();
    }
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      // using data for several actions (first vector)
      out.printStart();
      if (action.equals("GET_TABLE_DATA")){
        printData();
      } else if (action.equals("BUILD_NEW_MATERIAL") ||
                 action.equals("DELETE")) {
        printMsgs();
      } else if (action.equals("LOAD_SCREEN")) {
        printScrDataRequest();
      } else if (action.equals("INSERT_NEW_ITEM")     ||
                 action.equals("INSERT_NEW_PART")     ||
                 action.equals("INSERT_NEW_APP")      ||
                 action.equals("BUILD_NEW_SIZE")      ||
                 action.equals("BUILD_NEW_PART")      ||
                 action.equals("BUILD_NEW_APPROVAL")  ||
                 action.equals("APPROVE")             ||
                 action.equals("APPROVE_UPDATE")      ||
                 action.equals("INSERT_NEW_GRP")) {
        printMsgs();
      }

      out.printEnd();

      out.printStart();
      printData2();
      out.printEnd();

      out.printStart();
      printData3();
      out.printEnd();

      out.printStart();
      printData4();
      out.printEnd();

      out.printStart();
      printVVPkgStyle();
      out.printEnd();

      out.printStart();
      printVVSizeUnit();
      out.printEnd();

      out.printStart();
      printVVPeriodUnit();
      out.printEnd();

      out.printStart();
      printDefGroup();
      out.printEnd();

      out.printStart();
      printSpecIDs();
      out.printEnd();

      out.printStart();
      printFlows();
      out.printEnd();

    } catch (Exception e){
       e.printStackTrace();
    }

  }

  protected void getTableData() throws Exception {
    SearchPScreen sea = new SearchPScreen(db);
    tabledata = sea.getTableDataNewChem(Integer.parseInt(colnum),(facid.equals("")?null:facid),item);
  }

  protected void printData()  throws IOException {
    if (tabledata == null) return;

    for (int r=0;r<tabledata.length;r++){
      Object[] col = tabledata[r];
      for (int c=0;c<col.length;c++){
        String tmpStr = new String(col[c]==null?"":(col[c]).toString());
        out.println(tmpStr);
      }
      col = null;
    }
  }

  protected void buildNewRequest() throws Exception {
    if (reqid != null && reqid.length() > 0) return;
    String msg = new String("");
    int nx = 0;
    try {
      CatalogAddRequest car = new CatalogAddRequest(db);
      nx = car.getNext();
      if ( nx == 0) {
        msgs[0] = "0";
        msgs[1] = "No CatalogAddRequest Produced";
        return;
      }
      car.setRequestId(nx);
      car.setRequestor(Integer.parseInt(userid));
      car.setRequestDate(new String("nowDate")); // sysdate update
      car.insert();
      car.commit();

      car.insert("REQUEST_STATUS","0",CatalogAddRequest.INT);
      car.insert("STARTING_VIEW","0",CatalogAddRequest.INT);

      //clear flag
      car.insert("REQUEST_REJECTED","",car.STRING);

      //clear roles
      this.clearAllRoles(car.getRequestId().intValue());

      car.commit();

      // Add a record on catalogAddItem just to register for tracking
      CatalogAddItem cai = new CatalogAddItem(db);
      cai.setRequestId(nx);
      cai.setPartId(new Integer(1));
      cai.insert();

    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      msg = "Error on inserting CatalogAddRequest. Error: " + e.getMessage();
      msgs[0] = "0";
      msgs[1] = msg;
      return;
    }
    Integer temp = new Integer(nx);
    msgs[0] = new String(temp.toString());
    reqid = new String(temp.toString());
    msgs[1] = "New request created.";
    return;
  }

  protected void buildNewSize() throws Exception {
    this.buildNewRequest();

    Integer req_id =  new Integer(reqid);
    radian.tcmis.server7.share.dbObjs.Catalog cat = new radian.tcmis.server7.share.dbObjs.Catalog(db);
    Vector v = cat.retrieve(" item_id = "+item_id,"material_id");
    Vector mats = new Vector();

    // get all materials
    for (int i =0;i<v.size();i++){
        radian.tcmis.server7.share.dbObjs.Catalog c = (radian.tcmis.server7.share.dbObjs.Catalog) v.elementAt(i);
        if (mats.contains(c.getMatId())) continue;
        mats.addElement(c.getMatId());
    }

    CatalogAddItem catI = new CatalogAddItem(db);
    catI.setRequestId(req_id.intValue());
    for (int i=0;i<mats.size();i++){
        catI.setPartId(new Integer(i+1));
        catI.insert("material_id",mats.elementAt(i).toString(),catI.INT);
    }
    CatalogAddRequest catR = new CatalogAddRequest(db);
    catR.setRequestId(req_id.intValue());
    catR.insert("request_status","1",catR.INT);
    catR.insert("STARTING_VIEW","1",CatalogAddRequest.INT);

    //clear flag
    catR.insert("REQUEST_REJECTED","",catR.STRING);

    //clear roles
    this.clearAllRoles(catR.getRequestId().intValue());

  }


  protected void buildNewPart() throws Exception {
    this.buildNewRequest();
    buildNewSize();

    Integer req_id =  new Integer(reqid);

    CatalogAddRequest catR = new CatalogAddRequest(db);
    catR.setRequestId(req_id.intValue());
    catR.insert("item_id",item_id,catR.INT);
    catR.insert("request_status","2",catR.INT);
    catR.insert("STARTING_VIEW","2",CatalogAddRequest.INT);

    //clear flag
    catR.insert("REQUEST_REJECTED","",catR.STRING);

    //clear roles
    this.clearAllRoles(catR.getRequestId().intValue());
  }

  protected void buildNewApproval() throws Exception {
    this.buildNewRequest();
    buildNewSize();
    buildNewPart();

    Integer req_id =  new Integer(reqid);

    // Get catalog id
    Facility facO = new Facility(db);
    String catid = (String) facO.getCatForFac(facid).elementAt(0);

    CatalogAddRequest catR = new CatalogAddRequest(db);
    catR.setRequestId(req_id.intValue());
    catR.insert("fac_part_no",fac_part_no,catR.STRING);
    catR.insert("catalog_id",catid,catR.STRING);
    catR.insert("request_status","3",catR.INT);
    catR.insert("STARTING_VIEW","3",CatalogAddRequest.INT);
    //clear flag
    catR.insert("REQUEST_REJECTED","",catR.STRING);

    //clear roles
    this.clearAllRoles(catR.getRequestId().intValue());
  }

  protected void approveRoles() throws Exception {
     //get facility
     CatalogAddRequest catR = new CatalogAddRequest(db);
     catR.setRequestId((new Integer(this.reqid)).intValue());
     catR.load();
     String fac = catR.getFacilityId();

     /* roles_data
        0 - role
        1 - A,R,B
        2 - notes
     */
     Hashtable app = new Hashtable();
     Hashtable rej = new Hashtable();
     Hashtable ban = new Hashtable();
     Hashtable non = new Hashtable();
     for (int i=0;i<roles_data.size();i=i+3){
         if (roles_data.elementAt(i+1).toString().equals("A")){
           app.put(roles_data.elementAt(i).toString(),roles_data.elementAt(i+2).toString());
         } else if (roles_data.elementAt(i+1).toString().equals("R")){
           rej.put(roles_data.elementAt(i).toString(),roles_data.elementAt(i+2).toString());
         } else if (roles_data.elementAt(i+1).toString().equals("B")){
           ban.put(roles_data.elementAt(i).toString(),roles_data.elementAt(i+2).toString());
         } else if (roles_data.elementAt(i+1).toString().equals("N")){
           non.put(roles_data.elementAt(i).toString(),roles_data.elementAt(i+2).toString());
         }
     }

     //inserts
     NewChemApproval nca = new NewChemApproval(db);
     nca.setReqId((new Integer(this.reqid)).intValue()); // key1
     nca.setFacilityId(fac);

     Enumeration E;
     // app
//     for (E = app.keys();E.hasMoreElements();){
//        nca.setApprover((new Integer(this.userid)).intValue());
//        String role = E.nextElement().toString();
//        nca.setRole(role);
//        nca.insert("approval_date","nowDate",nca.DATE);
//        nca.insert("reason",HelpObjs.singleQuoteToDouble(app.get(role).toString()),nca.STRING);
//        nca.insert("status","Approved",nca.STRING);
//     }
     // rej
//	     for (E = rej.keys();E.hasMoreElements();){
//	        nca.setApprover((new Integer(this.userid)).intValue());
//	        String role = E.nextElement().toString();
//	        nca.setRole(role);
//	        nca.insert("approval_date","nowDate",nca.DATE);
//	        nca.insert("reason",HelpObjs.singleQuoteToDouble(rej.get(role).toString()),nca.STRING);
//	        nca.insert("status","Rejected",nca.STRING);
//	     }
     // ban
//     for (E = ban.keys();E.hasMoreElements();){
//        nca.setApprover((new Integer(this.userid)).intValue());
//        String role = E.nextElement().toString();
//        nca.setRole(role);
//        nca.insert("approval_date","nowDate",nca.DATE);
//        nca.insert("reason",HelpObjs.singleQuoteToDouble(ban.get(role).toString()),nca.STRING);
//        nca.insert("status","Banned",nca.STRING);
//     }
     //  none
     for (E = non.keys();E.hasMoreElements();){
        nca.setApprover((new Integer(this.userid)).intValue());
        String role = E.nextElement().toString();
        nca.setRole(role);
        nca.delete();
     }

     // now decide if the request is done or not.
     // change status to approved
     msgs[0] = "OK";
     msgs[1] = "Record updated.";

     String newStatus = nca.getFullStatus();

     catR.insert("request_status",newStatus,catR.INT);

     // empty reject flag
     catR.insert("REQUEST_REJECTED","",catR.STRING);

     Personnel p = null;

     if (newStatus == "8" || newStatus == "7" || newStatus == "10"){  // send email

       p = new Personnel(db);
       p.setPersonnelId(catR.getRequestor().intValue());
       p.load();

       String esub = "";
       String emsg = "";
       if (newStatus == "8") {   //approved
         esub = new String("Request "+this.reqid+" was APPROVED for all Roles.");
         emsg = new String("Status     : APPROVED\n");
       } else if (newStatus == "7") { // rejected
         esub = new String("Request "+this.reqid+" was REJECTED.");
         emsg = new String("Status     : REJECTED\n");
       } else if (newStatus == "10") {  // banned
         esub = new String("Request "+this.reqid+" was BANNED.");
         emsg = new String("Status     : BANNED\n");
       }

       emsg = emsg + "Request Id : "+this.reqid+"\n";
       emsg = emsg + "Requestor  : "+p.getFullName()+"\n";
       emsg = emsg + "Facility   : "+catR.getFacilityId()+"\n";
       emsg = emsg + "Part Number: "+catR.getFacPartNum()+"\n\n";

       emsg = emsg + "Roles:\n--------------------------\n";

       //  Roles
       Vector allRoles = nca.getAllRoles(Integer.parseInt(this.reqid));
       NewChemApproval nca2 = new NewChemApproval(db);
       nca2.setReqId(Integer.parseInt(this.reqid));
       nca2.setFacilityId(fac);

       for (int i=0;i<allRoles.size();i++){

          nca2.setRole(allRoles.elementAt(i).toString());
          nca2.load();
          Vector apprs = nca2.getApprovers();

          for (int j=0;j<apprs.size();j++){
            emsg = emsg + "Name  : " + (nca2.getRole()==null?"":nca2.getRole().toString())+"\n" ;
            emsg = emsg + "Status: " + (nca2.getStatus()==null?"":nca2.getStatus().toString())+"\n";
            emsg = emsg + "Date  : " + (nca2.getDate()==null?"":BothHelpObjs.formatDate("toCharfromDB",nca2.getDate()))+"\n";
            emsg = emsg + "By    : " ;

            Personnel p2 = new Personnel(db);
            p2.setPersonnelId(Integer.parseInt(apprs.elementAt(j).toString()));
            p2.load();
            emsg = emsg + (p2.getFullName()==null?"":p2.getFullName()) +  "\n" ;
            emsg = emsg + "Notes : " + (nca2.getReason()==null?"":nca2.getReason())+ "\n\n";
          }
       }

       HelpObjs.sendMail(db,esub,emsg,catR.getRequestor().intValue(),true);
       HelpObjs.sendMail(db,esub,emsg,19143,true); // Steve Buffum
       HelpObjs.sendMail(db,esub,emsg,19349,true); // Veronica Morton


       // rejected
       if (newStatus == "7"){
         // empty reject flag
         catR.insert("REQUEST_REJECTED","Y",catR.STRING);
       }
     }

     return;

  }


  protected void printMsgs()  throws IOException {
    if (msgs == null) return;

    for (int i=0;i<msgs.length;i++){
        String tmpStr = new String(msgs[i]==null?"":msgs[i]);
        out.println(tmpStr);
    }
  }

  protected void getScreenData() throws Exception {
    if (reqid == null) return;
    CatalogAddRequest car = new CatalogAddRequest(db);
    car.setRequestId(Integer.parseInt(reqid));
    allData = car.getAllDBValues();  // on DB order
    return;
  }

  protected void printScrDataRequest()  throws IOException {
    if (allData == null) return;
    if (allData.get("REQUEST") == null) return;
    Vector data = (Vector) allData.get("REQUEST");
    data.trimToSize();
    for (int i=0;i<data.size();i++){
        out.println(data.elementAt(i).toString());
    }
  }

  protected void printData2()  throws IOException {
    if (allData == null) return;
    if (action.equals("LOAD_SCREEN")) {
      if (allData.get("ITEMS") == null) return;
      Vector data = (Vector) allData.get("ITEMS");
      for (int r=0;r<data.size();r++){
        Vector cols = (Vector) data.elementAt(r);
        for (int c=0;c<cols.size();c++){
          String tmpStr = new String(cols.elementAt(c)==null?"":cols.elementAt(c).toString());
          out.println(tmpStr);
        }
        cols = null;
      }
    }
  }

  protected void printData3()  throws IOException {
    if (allData == null) return;
    if (action.equals("LOAD_SCREEN")) {
      if (allData.get("FATE") == null) return;
      // // System.out.println("allData: "+allData);
      Vector data = (Vector) allData.get("FATE");
      // // System.out.println("data: "+data);
      for (int r=0;r<data.size();r++){
        Vector cols = (Vector) data.elementAt(r);
        for (int c=0;c<cols.size();c++){
          String tmpStr = new String(cols.elementAt(c)==null?"":cols.elementAt(c).toString());
          out.println(tmpStr);
        }
        cols = null;
      }
    }
  }

  protected void printData4()  throws Exception {
    if (allData == null) return;
    if (action.equals("LOAD_SCREEN")) {
      if (allData.get("COMP") == null) return;
      Vector data = (Vector) allData.get("COMP");
      out.println(data);
    }
  }

  protected void getVVs() throws Exception {
    vvperiod = VVs.getVVPeriodUnit(db);
    vvpkg = VVs.getVVPkgStyle(db);
    vvsize = VVs.getVVSizeUnit(db);
    return;
  }

  protected void printVVPkgStyle()  throws Exception {
    if (vvpkg == null) return;
    vvpkg.trimToSize();
    out.println(vvpkg);
  }

  protected void printVVSizeUnit() throws Exception {
    if (vvsize == null) return;
    vvsize.trimToSize();
    out.println(vvsize);
  }
  protected void printVVPeriodUnit()  throws Exception {
    if (vvperiod == null) return;
    vvperiod.trimToSize();
    out.println(vvperiod);
  }

  protected void printSpecIDs() throws Exception {
    if (allData == null) return;
    if (allData.get("SPECS") == null) return;
    Vector vvSpecs = (Vector) allData.get("SPECS");
    vvSpecs.trimToSize();
    out.println(vvSpecs);
  }

  protected void printFlows() throws Exception {
    if (allData == null) return;
    if (allData.get("FLOWS") == null) return;
    Vector vvFlows = (Vector) allData.get("FLOWS");
    vvFlows.trimToSize();
    out.println(vvFlows);
  }

  protected void getGroups()  throws Exception {
    Personnel pe = new Personnel(db);
    Object[][] tmp = pe.getAllApprovalGroups("","");
    defGroup = new Vector();
    defGroupdesc = new Vector();
    defGroupFacId = new Vector();
    for (int r=0;r<tmp.length;r++){
      Object[] col = tmp[r];
      defGroup.addElement(col[0]);
      defGroupdesc.addElement(col[1]);
      defGroupFacId.addElement(col[2]);
    }

    /*
    pe.setPersonnelId((new Integer(userid)).intValue());
    pe.load();
    defGroup = pe.getUserGroups();
    defGroupdesc = new Vector();
    for (int i=0;i<defGroup.size();i++){
        defGroupdesc.addElement(pe.getGroupDesc(defGroup.elementAt(i).toString()) ==null?"":pe.getGroupDesc(defGroup.elementAt(i).toString()));
    }
    */
  }

  protected void printDefGroup() throws IOException {
     if (defGroup == null) {
        out.printEnd();
        out.printStart();
        return;
     }

     for (int i=0;i<defGroup.size();i++){
       out.println(defGroup.elementAt(i)==null?"":defGroup.elementAt(i).toString());
     }
     out.printEnd();
     out.printStart();
     for (int i=0;i<defGroupdesc.size();i++){
       out.println(defGroupdesc.elementAt(i)==null?"":defGroupdesc.elementAt(i).toString());
     }

     out.printEnd();
     out.printStart();
     try{
       out.println(defGroupFacId);
     }catch(Exception e){e.printStackTrace();}

  }

  protected void insertNewItem()  {
    try{
      String msg = new String("");
      int nx = 0;

      CatalogAddRequest car = new CatalogAddRequest(db);
      car.setRequestId(Integer.parseInt(reqid));

      int records = car.getCount();
      if (records < 1){
        msgs[0] = "NOT";
        msgs[1] = "This request has been corrupted. Please contact the DB administrator.";
        return;
      }

      CatalogAddItem cai = new CatalogAddItem(db);
      cai.setRequestId(Integer.parseInt(reqid));

      records = cai.getCount();
      /*  keep th information
      if (records > 0){
        cai.delete();
      }
      */
      for (int i=0;i<mfg.size();i++){
        cai.setPartId(new Integer(i+1));
        cai.insert("MATERIAL_DESC",HelpObjs.singleQuoteToDouble(matdesc.elementAt(i).toString()),cai.STRING);
        cai.insert("MANUFACTURER",HelpObjs.singleQuoteToDouble(mfg.elementAt(i).toString()),cai.STRING);
        if (mfgpart.size() > i){
          cai.insert("MFG_CATALOG_ID",HelpObjs.singleQuoteToDouble(mfgpart.elementAt(i).toString()),cai.STRING);
        }
        cai.insert("PART_SIZE",size.elementAt(i).toString(),cai.INT);
        cai.insert("SIZE_UNIT",unit.elementAt(i).toString(),cai.STRING);
        cai.insert("PKG_STYLE",pack.elementAt(i).toString(),cai.STRING);
        cai.insert("MATERIAL_TYPE",HelpObjs.singleQuoteToDouble(mattype.elementAt(i).toString()),cai.STRING);

        cai.commit();
      }



      int status = Integer.parseInt(reqstatus);
      status++;
      car.insert("REQUEST_STATUS",(new Integer(status)).toString(),CatalogAddRequest.INT);
      car.commit();

      //clear flag
      car.insert("REQUEST_REJECTED","",car.STRING);

      //clear roles
      this.clearAllRoles(car.getRequestId().intValue());

    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      msgs[0] = "NOT";
      msgs[1] = e.getMessage();
      return;
    }

    msgs[0] = new String("OK");
    msgs[1] = "New Item information successfully stored. You can fax the MSDS to (512)419-5286. Attn: NEW ITEM REQ. \""+reqid+"\"";

    return;
  }

  protected void updateData(){
    try{
      String msg = new String("");
      int nx = 0;

      CatalogAddRequest car = new CatalogAddRequest(db);
      car.setRequestId(Integer.parseInt(reqid));

      int records = car.getCount();
      if (records < 1){
        msgs[0] = "NOT";
        msgs[1] = "This request has been corrupted. Please contact the DB administrator.";
        return;
      }
      car.insert("CATALOG_ID",newpart.elementAt(0).toString(),car.STRING);
      car.insert("FAC_PART_NO",newpart.elementAt(1).toString(),car.STRING);
      car.insert("STOCKED",(newpart.elementAt(2).toString().equals("1")?"MM":"OOR"),car.STRING);
      if (newpart.elementAt(2).toString().equals("1")) {
          if (!newpart.elementAt(3).toString().equals(" ")) car.insert("ANNUAL_USAGE",newpart.elementAt(3).toString(),car.INT);
      }
      car.insert("SHELF_LIFE",newpart.elementAt(4).toString(),car.INT);
      car.insert("SHELF_LIFE_UNIT",newpart.elementAt(5).toString(),car.STRING);
      car.insert("SHELF_LIFE_BASIS",newpart.elementAt(6).toString(),car.STRING);
      car.insert("STORAGE_TEMP",newpart.elementAt(7).toString(),car.STRING);
      car.insert("STORAGE_TEMP_UNIT",newpart.elementAt(8).toString(),car.STRING);

      //specs
      if (specs.size()>=SPEC_VALUES_PASSED){
        for (int i=0;i<specs.size();i+=SPEC_VALUES_PASSED){
          String s1 = HelpObjs.singleQuoteToDouble((String) specs.elementAt(i));
          String s2 = null;
          String s3 = null;
          String s4 = null;
          String s5 = null;
          String s6 = null;
          try { s2 = HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+1));}catch (Exception e){e.printStackTrace();};
          try { s3 = HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+2));}catch (Exception e){e.printStackTrace();};
          try { s4 = HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+3));}catch (Exception e){e.printStackTrace();};
          try { s5 = HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+4));}catch (Exception e){e.printStackTrace();};
          try { s6 = HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+5));}catch (Exception e){e.printStackTrace();};
          car.insertSpec(car.getRequestId(),s1,s2==null?"":s2,s3==null?"":s3,s4==null?"":s4,s5==null?"":s5,s6==null?"":s6);
        }
      }

      // desc: vector, process def1, process def2 and process description
      car.insert("PROCESS_TITLE",HelpObjs.singleQuoteToDouble(desc.elementAt(0).toString()+desc.elementAt(1).toString()),car.STRING);
      car.insert("PROCESS_DESC",HelpObjs.singleQuoteToDouble(desc.elementAt(2).toString()),car.STRING);
      // comp: vector, 0 to n where repeat every two colus.
      // delete comp first
      car.deleteComp(new Integer(reqid));
      Vector tmp = new Vector();
      for (int i=0;i<comp.size();i=i+2){
         tmp = new Vector();
         tmp.addElement(comp.elementAt(i));
         tmp.addElement((comp.size()>1?comp.elementAt(i+1):""));
         car.insertComp(tmp);
      }
      // procu: vector, material, storage loc., month cons., max on site and import/export(y,n)
      car.insert("MATERIALS_REPLACED",HelpObjs.singleQuoteToDouble(procu.elementAt(0).toString()),car.STRING);
      car.insert("STORAGE_LOCATIONS",HelpObjs.singleQuoteToDouble(procu.elementAt(1).toString()),car.STRING);
      car.insert("MONTHLY_USAGE",procu.elementAt(2).toString(),car.INT);
      car.insert("MAX_ON_SITE",procu.elementAt(3).toString(),car.INT);
      car.insert("IMPORT_EXPORT",(procu.elementAt(4).toString().equals("1")?"Y":"N"),car.STRING);
      car.insert("SUGGESTED_VENDOR",HelpObjs.singleQuoteToDouble(procu.elementAt(5).toString()),car.STRING);
      car.insert("VENDOR_PART_NO",HelpObjs.singleQuoteToDouble(procu.elementAt(6).toString()),car.STRING);
      car.insert("PROC_SPECIAL_INSTR",HelpObjs.singleQuoteToDouble(procu.elementAt(7).toString()),car.STRING);


      car.commit();

       // fate: vector , 0 to n where repeat every three (part, fatedesc, fateperc) colus. for each part
      CatalogAddItem cai = new CatalogAddItem(db);
      cai.setRequestId(Integer.parseInt(reqid));
      //delete fate first
      cai.deleteFate();
      for (int i=0;i<fate.size();i=i+3){
         cai.setPartId(new Integer(fate.elementAt(i).toString()));
         Vector tmp1 = new Vector();
         tmp1.addElement(fate.elementAt(i+1).toString());
         tmp1.addElement(fate.elementAt(i+2).toString());
         cai.insertFate(tmp1);
      }
      cai.commit();

    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      msgs[0] = "NOT";
      msgs[1] = e.getMessage();
      return;
    }

    msgs[0] = new String("OK");
    msgs[1] = "OK";
    return;
  }



  protected void insertNewPart()  {
    try{
      String msg = new String("");
      int nx = 0;

      CatalogAddRequest car = new CatalogAddRequest(db);
      car.setRequestId(Integer.parseInt(reqid));

      int records = car.getCount();
      if (records < 1){
        msgs[0] = "NOT";
        msgs[1] = "This request has been corrupted. Please contact the DB administrator.";
        return;
      }


      int status = Integer.parseInt(reqstatus);
      status++;
      car.insert("REQUEST_STATUS",(new Integer(status)).toString(),CatalogAddRequest.INT);
      /* Data sent from gui
      0 facXref.get(facChoice.getSelectedItem()); its really catalogid
      1 partNumT.getText();
      2 (mmRadio.isEnabled()?"1":"0");
      3 usageT.getText();
      4 shelfT.getText();
      5 shelfTimeComboBox.getSelectedItem();
      6 (rcvRadio.isEnabled()?"rcv":(mfgRadio.isEnabled()?"mfg":"ship"));
      //7 specIDT.getText();
      //8 specRevT.getText();
      ///9 specAmendT.getText();
      7 tempT.getText();
      8 tempCombo.getSelectedItem();
      //9 (ccRadio.isSelected()?"1":"0");
      //10 (caRadio.isSelected()?"1":"0");
      */
      // // System.out.println("Vector:"+newpart);
      //System.out.println("Inserting catalog id:"+ newpart.elementAt(0).toString());
      car.insert("CATALOG_ID",newpart.elementAt(0).toString(),car.STRING);
      car.insert("FAC_PART_NO",newpart.elementAt(1).toString(),car.STRING);
      car.insert("STOCKED",(newpart.elementAt(2).toString().equals("1")?"MM":"OOR"),car.STRING);
      if (newpart.elementAt(2).toString().equals("1")) {
          if (!newpart.elementAt(3).toString().equals(" ")) car.insert("ANNUAL_USAGE",newpart.elementAt(3).toString(),car.INT);
      }
      car.insert("SHELF_LIFE",newpart.elementAt(4).toString(),car.INT);
      car.insert("SHELF_LIFE_UNIT",newpart.elementAt(5).toString(),car.STRING);
      car.insert("SHELF_LIFE_BASIS",newpart.elementAt(6).toString(),car.STRING);
      /*  it is done dif. now on another table
      car.insert("SPEC_NAME",newpart.elementAt(7).toString(),car.STRING);
      if (!newpart.elementAt(8).toString().equals(" ")) car.insert("SPEC_VERSION",newpart.elementAt(8).toString(),car.STRING);
      if (!newpart.elementAt(9).toString().equals(" ")) car.insert("SPEC_AMENDMENT",newpart.elementAt(9).toString(),car.STRING);
      */
      car.insert("STORAGE_TEMP",newpart.elementAt(7).toString(),car.STRING);
      car.insert("STORAGE_TEMP_UNIT",newpart.elementAt(8).toString(),car.STRING);
      // done on another table
      //car.insert("C_OF_C",(newpart.elementAt(12).toString().equals("1")?"Y":"N"),car.STRING);
      //car.insert("C_OF_A",(newpart.elementAt(13).toString().equals("1")?"Y":"N"),car.STRING);

      //specs
      if (specs.size()>=SPEC_VALUES_PASSED){
        for (int i=0;i<specs.size();i+=SPEC_VALUES_PASSED){
          //HelpObjs.monitor(1,"insert:"+ (String) specs.elementAt(i));
          /*
          HelpObjs.singleQuoteToDouble
          (String) specs.elementAt(i)),
          HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+1)),
          HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+2)),
          HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+3)),
          HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+4)),
          HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+5)));
          */
          car.insertSpec(car.getRequestId(),HelpObjs.singleQuoteToDouble((String) specs.elementAt(i)),HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+1)),HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+2)),HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+3)),HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+4)),HelpObjs.singleQuoteToDouble((String) specs.elementAt(i+5)));
        }
      }

      //flows
      for (int i=0;i<flows.size();i++){
        car.insertFlow(car.getRequestId(),(String) flows.elementAt(i));
      }

      car.commit();

      //clear flag
      car.insert("REQUEST_REJECTED","",car.STRING);

      //clear roles
      this.clearAllRoles(car.getRequestId().intValue());

    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      msgs[0] = "NOT";
      msgs[1] = e.getMessage();
      return;
    }

    msgs[0] = new String("OK");
    msgs[1] = "New Part information successfully stored. You can fax the SPECIFICATION  to (512)419-5286. Attn: NEW PART REQ. \""+reqid+"\"";

    return;
  }

  protected void insertNewAppr()  {
    try{
      String msg = new String("");
      int nx = 0;

      CatalogAddRequest car = new CatalogAddRequest(db);
      car.setRequestId(Integer.parseInt(reqid));

      int records = car.getCount();
      if (records < 1){
        msgs[0] = "NOT";
        msgs[1] = "This request has been corrupted. Please contact the DB administrator.";
        return;
      }


      int status = Integer.parseInt(reqstatus);
      status++;
      car.insert("REQUEST_STATUS",(new Integer(status)).toString(),CatalogAddRequest.INT);

      // app : application
      car.insert("APPLICATION",app,car.STRING);
      // rfac : facility
      car.insert("FACILITY_ID",rfac,car.STRING);
      // desc: vector, process def1, process def2 and process description
      car.insert("PROCESS_TITLE",HelpObjs.singleQuoteToDouble(desc.elementAt(0).toString()+desc.elementAt(1).toString()),car.STRING);
      car.insert("PROCESS_DESC",HelpObjs.singleQuoteToDouble(desc.elementAt(2).toString()),car.STRING);
      // comp: vector, 0 to n where repeat every two colus.
      // delete comp first
      car.deleteComp(new Integer(reqid));
      Vector tmp = new Vector();

      for (int i=0;i<comp.size();i=i+2){
         tmp = new Vector();
         tmp.addElement(comp.elementAt(i));
          try {
            tmp.addElement(comp.elementAt(i+1));
          } catch (Exception e) {
            tmp.addElement("");
            e.printStackTrace();
          }
         car.insertComp(tmp);
      }
      // procu: vector, material, storage loc., month cons., max on site and import/export(y,n)
      car.insert("MATERIALS_REPLACED",HelpObjs.singleQuoteToDouble(procu.elementAt(0).toString()),car.STRING);
      car.insert("STORAGE_LOCATIONS",HelpObjs.singleQuoteToDouble(procu.elementAt(1).toString()),car.STRING);
      car.insert("MONTHLY_USAGE",procu.elementAt(2).toString(),car.INT);
      car.insert("MAX_ON_SITE",procu.elementAt(3).toString(),car.INT);
      car.insert("IMPORT_EXPORT",(procu.elementAt(4).toString().equals("1")?"Y":"N"),car.STRING);
      car.insert("SUGGESTED_VENDOR",HelpObjs.singleQuoteToDouble(procu.elementAt(5).toString()),car.STRING);
      car.insert("VENDOR_PART_NO",HelpObjs.singleQuoteToDouble(procu.elementAt(6).toString()),car.STRING);
      car.insert("PROC_SPECIAL_INSTR",HelpObjs.singleQuoteToDouble(procu.elementAt(7).toString()),car.STRING);


      car.commit();

      //clear flag
      car.insert("REQUEST_REJECTED","",car.STRING);
      //clear roles
      this.clearAllRoles(car.getRequestId().intValue());

       // fate: vector , 0 to n where repeat every three (part, fatedesc, fateperc) colus. for each part
      CatalogAddItem cai = new CatalogAddItem(db);
      cai.setRequestId(Integer.parseInt(reqid));
      //delete fate first
      cai.deleteFate();
      for (int i=0;i<fate.size();i=i+3){
         cai.setPartId(new Integer(fate.elementAt(i).toString()));
         Vector tmp1 = new Vector();
         tmp1.addElement(fate.elementAt(i+1).toString());
         tmp1.addElement(fate.elementAt(i+2).toString());
         cai.insertFate(tmp1);
      }
      cai.commit();

    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      msgs[0] = "NOT";
      msgs[1] = e.getMessage();
      return;
    }

    msgs[0] = new String("OK");
    msgs[1] = "New Approval information successfully stored.";

    return;
  }

  protected void insertNewGrp()  {
    try{
      // data: group, qty1, qty2, unit1, unit2, qtyTime1, qtyTime2
      String msg = new String("");
      int nx = 0;

      CatalogAddRequest car = new CatalogAddRequest(db);
      car.setRequestId(Integer.parseInt(reqid));

      int records = car.getCount();
      if (records < 1){
        msgs[0] = "NOT";
        msgs[1] = "This request has been corrupted. Please contact the DB administrator.";
        return;
      }


      int status = Integer.parseInt(reqstatus);
      status++;
      car.insert("REQUEST_STATUS",(new Integer(status)).toString(),CatalogAddRequest.INT);

      // grp
      car.insert("USER_GROUP_ID",newgrp.elementAt(0).toString(),car.STRING);
      car.insert("QUANTITY",newgrp.elementAt(1).toString(),car.INT);
      car.insert("QUANTITY2",newgrp.elementAt(2).toString(),car.INT);
      car.insert("PERIOD",newgrp.elementAt(3).toString(),car.INT);
      car.insert("PERIOD2",newgrp.elementAt(4).toString(),car.INT);
      car.insert("PERIOD_UNIT",newgrp.elementAt(5).toString(),car.STRING);
      car.insert("PERIOD_UNIT2",newgrp.elementAt(6).toString(),car.STRING);

      car.commit();

      //clear flag
      car.insert("REQUEST_REJECTED","",car.STRING);
      //clear roles
      this.clearAllRoles(car.getRequestId().intValue());
    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      msgs[0] = new String("NOT");
      msgs[1] = e.getMessage();
      return;
    }

    msgs[0] = new String("OK");
    msgs[1] = "Request successfully submitted for approval.";

    //Send email  to the approvers
    try {
      CatalogAddRequest car = new CatalogAddRequest(db);
      car.setRequestId(Integer.parseInt(reqid));
      car.load();
      Personnel p = new Personnel(db);
      p.setPersonnelId(car.getRequestor().intValue());
      p.load();

      //build the material table data
      NewChemTrackTable nctt = new NewChemTrackTable(db);
      Vector big1 = nctt.getApprDetailData(reqid);
      Vector roleV = new Vector();
      Vector statusV =  new Vector();
      Vector appV =  new Vector();
      Vector dateV =  new Vector();
      Vector reasonV =  new Vector();
      Vector appNumV =  new Vector();
      Vector roleTypeV =  new Vector();
      if(big1 != null && big1.size() > 0){
        roleV = (Vector)big1.elementAt(0);
        statusV = (Vector)big1.elementAt(1);
        appV = (Vector)big1.elementAt(2);
        dateV = (Vector)big1.elementAt(3);
        reasonV = (Vector)big1.elementAt(4);
        appNumV = (Vector)big1.elementAt(5);
        roleTypeV = (Vector)big1.elementAt(6);
      }

      String esub = "";
      String emsg = "";

      esub = new String("tcmIS New Chemical Request "+this.reqid+" is waiting for your approval.");
      emsg = new String("Status     : WAITING APPROVAL\n");
      emsg = emsg + "Request Id : "+this.reqid+"\n";
      emsg = emsg + "Requestor  : "+p.getFullName()+"\n";
      emsg = emsg + "Facility   : "+car.getFacilityId()+"\n";
      emsg = emsg + "Part Number: "+car.getFacPartNum()+"\n\n";

      emsg = emsg + "Roles:\n--------------------------\n";

      for (int i=0;i<roleV.size();i++){
            emsg = emsg + "Name  : " + (roleV.elementAt(i)==null?"":roleV.elementAt(i))+"\n" ;
            emsg = emsg + "Status: " + (statusV.elementAt(i)==null?"":statusV.elementAt(i))+"\n" ;
            emsg = emsg + "Date  : " + (dateV.elementAt(i)==null?"":BothHelpObjs.formatDate("toCharfromDB",dateV.elementAt(i).toString()))+"\n";
            emsg = emsg + "By    : " ;

            Personnel p2 = new Personnel(db);
            p2.setPersonnelId(Integer.parseInt(appNumV.elementAt(i).toString()));
            p2.load();
            emsg = emsg + (p2.getFullName()==null?"":p2.getFullName()) +  "\n" ;
            emsg = emsg + "Notes : " + (reasonV.elementAt(i)==null?"":reasonV.elementAt(i))+ "\n\n";
      }

      Vector hol = new Vector();
      for (int i=0;i<appNumV.size();i++){
        int nn = Integer.parseInt(appNumV.elementAt(i).toString());
        if (!hol.contains(new Integer(nn))){
          hol.addElement(new Integer(nn));
          HelpObjs.sendMail(db,esub,emsg,nn,true);
        }
      }
      HelpObjs.sendMail(db,esub,emsg,19143,true); // Steve Buffum
      HelpObjs.sendMail(db,esub,emsg,19349,true); // Veronica Morton
      if (!hol.contains(new Integer(car.getRequestor().intValue()))){
         HelpObjs.sendMail(db,esub,emsg,car.getRequestor().intValue(),true);
      }

      //HelpObjs.sendMail(db,esub,emsg,18864,true); // Steve Buffum
    } catch (Exception e){
      e.printStackTrace();
    }

    return;
  }

  void clearAllRoles(int req) throws Exception {
    if (req == 0 || req == 1) return;
    NewChemApproval nca = new NewChemApproval(db);
    nca.setReqId(req); // key1
    nca.clearAllRoles();
    return;
  }

  void deleteRequest(){
    boolean done = false;
    try{
      CatalogAddItem cai = new CatalogAddItem(db);
      CatalogAddRequest car = new CatalogAddRequest(db);
      cai.setRequestId(Integer.parseInt(reqid));
      car.setRequestId(Integer.parseInt(reqid));
      cai.delete();
      car.delete();
      done = true;
    }catch(Exception e) {e.printStackTrace();}
    msgs[0] = done?"true":"false";


  }

  protected  int getServInt(){
    return TcmisOutputStreamServer.NEW_CHEM;
  }
}
