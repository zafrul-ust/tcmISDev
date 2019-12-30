package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;


public class UserProfile extends TcmisServlet{
  //Client Version
  String action = null;
  String userid = null;
  String proxy = null;
  String port =  null;
  String fname = null;
  String lname =  null;
  String mi =  null;
  String phone =  null;
  String altphone =  null;
  String pager =  null;
  String email =  null;
  String mail =  null;
  String ship =  null;
  String fax =  null;
  Vector group =  null;
  String fac =  null;
  Vector appids =  null;
  Vector appamt =  null;
  Vector rel = null;
  Vector genOrdersFac = null;
  Vector adminFac = null;
  Vector newChemFac = null;
  Vector managerFac = null;
  Vector reportFac = null;

  String upmsg =  null;
  Vector resultdata =null;
  Vector releasersV = null;
  Hashtable approversH = null;
  Vector defGroup = null;
  Vector defGroupdesc = null;
  Vector defGroupFacId = null;
  Hashtable defFac = null;
  Vector facV = null;
  String newPW = null;
  boolean pwChanged = false;
  String userLogon = null;
  String logonID = null;


  public UserProfile(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
     action = null;
     userid = null;
     proxy = null;
     port =  null;
     fname = null;
     lname =  null;
     mi =  null;
     phone =  null;
     altphone =  null;
     pager =  null;
     email =  null;
     mail =  null;
     ship =  null;
     fax =  null;
     group =  null;
     fac =  null;
     appids =  null;
     appamt =  null;
     rel = null;

     upmsg =  null;
     resultdata =null;
     releasersV = null;
     approversH = null;
     defGroup = null;
     defGroupdesc = null;
     defFac = null;
     facV = null;
     newPW = null;
     pwChanged = false;
     userLogon = null;
     logonID = null;
     genOrdersFac = null;
     adminFac = null;
     newChemFac = null;
     managerFac = null;
     reportFac = null;
  }

  protected void getResult(HttpInput httpI){
   try {
      action = httpI.getString("ACTION");
      userid = httpI.getString("USERID");
      proxy = httpI.getString("PROXY");
      port = httpI.getString("PORT");
      fname = httpI.getString("FNAME");
      lname = httpI.getString("LNAME");
      mi = httpI.getString("MI");
      phone = httpI.getString("PHONE");
      altphone = httpI.getString("ALTPHONE");
      pager = httpI.getString("PAGER");
      email = httpI.getString("EMAIL");
      mail = httpI.getString("MAIL");
      ship = httpI.getString("SHIP");
      fax = httpI.getString("FAX");
      group = httpI.getVector("GROUP");
      fac = httpI.getString("FAC");
      /*10-01-01
      appids = httpI.getVector("APPRIDS");
      appamt = httpI.getVector("APPRAMT");
      */
      rel = httpI.getVector("REL");
      facV = httpI.getVector("FACIDS");
      newPW = httpI.getString("NEWPW");
      logonID = httpI.getString("LOGONID");
      genOrdersFac = httpI.getVector("GEN_ORDERS_FAC");
      adminFac = httpI.getVector("ADMIN_FAC");
      newChemFac = httpI.getVector("NEW_CHEM_FAC");
      managerFac = httpI.getVector("WASTE_MANAGER");
      reportFac = httpI.getVector("CREATE_REPORT");
      if (action.equals("LOAD_SCREEN")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"UserProfile:getResult = Going getScreenData",getBundle());
        getScreenData();
      }else if(action.equals("UPDATE")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"UserProfile:getResult = Going getScreenData",getBundle());
        update();
      }else if(action.equals("CHANGEPW")) {
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"UserProfile:getResult = Going changingPassword",getBundle());
        changingPassword();
      }

    }catch(Exception e){e.printStackTrace();}
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.printStart();
      printScreenData();
      out.printEnd();

      out.printStart();
      printDefFac();
      out.printEnd();

      out.printStart();
      printUpdateMessage();
      out.printEnd();

      out.printStart();
      printChangePW();
      out.printEnd();

      out.printStart();
      printUserLogon();
      out.printEnd();

      out.printStart();
      printGenOrdersFac();
      out.printEnd();

      out.printStart();
      printAdminFac();
      out.printEnd();

      out.printStart();
      printNewChemFac();
      out.printEnd();

      out.printStart();
      printManagerFac();
      out.printEnd();

      out.printStart();
      printCreateReportFac();
      out.printEnd();

    }catch(Exception e){e.printStackTrace();}
  }

  protected void getScreenData() throws Exception {
    PersonnelProfile p = new PersonnelProfile(db);
    resultdata = p.getScreenData((new Integer(userid)).intValue());

    Personnel pe = new Personnel(db);
    pe.setPersonnelId((new Integer(userid)).intValue());
    releasersV = pe.getReleaserFacs();
    //10-01-01 approversH = pe.getApproverFacsAndAmt();
    pe.load();
    defFac = pe.getRelatedFacilities();
    userLogon = pe.getUserLogon();
    if(BothHelpObjs.isBlankString(userLogon))userLogon = "";

    try{
      genOrdersFac = new Vector();
      adminFac = new Vector();
      UserGroup ug = new UserGroup(db);
      Vector groups = ug.getAllGroupsIsMember(userid);
      for(int i=0;i<groups.size();i++){
        UserGroup g = (UserGroup)groups.elementAt(i);
        if(g.getGroupId().equalsIgnoreCase("GenerateOrders")){
          genOrdersFac.addElement(g.getGroupFacId());
          continue;
        }
        if(g.getGroupId().equalsIgnoreCase("Administrator")){
          adminFac.addElement(g.getGroupFacId());
          continue;
        }
        if(g.getGroupId().equalsIgnoreCase("CreateNewChemical")){
          newChemFac.addElement(g.getGroupFacId());
          continue;
        }
        if(g.getGroupId().equalsIgnoreCase("WasteManager")){
          managerFac.addElement(g.getGroupFacId());
          continue;
        }
        if(g.getGroupId().equalsIgnoreCase("CreateReport")){
          reportFac.addElement(g.getGroupFacId());
          continue;
        }
      }
    }catch(Exception e){e.printStackTrace();}
    if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"UserProfile:Done Loading Data:"+resultdata+releasersV.toString()+approversH.toString()+defFac+defGroup,getBundle());
  }

  protected void printScreenData() throws IOException {
    if (resultdata != null){
      for (int i=0;i<resultdata.size();i++){
        out.println(resultdata.elementAt(i)==null?"":resultdata.elementAt(i).toString());
      }
    }
    out.printEnd();

    out.printStart();
    if (releasersV != null){
      for (int i=0;i<releasersV.size();i++){
        out.println(releasersV.elementAt(i)==null?"":releasersV.elementAt(i).toString());
      }
    }
    out.printEnd();

    if (approversH == null || approversH.size() == 0){
         out.printStart();
         out.printEnd();
         out.printStart();
         return;
    }

    Enumeration E;
    Vector facids = new Vector();
    Vector amts = new Vector();
    int i=0;
    for(E=approversH.keys();E.hasMoreElements();){
      Object tmp = E.nextElement();
      facids.addElement(tmp);
      amts.addElement(approversH.get(tmp));
    }

    out.printStart();
    if (facids != null){
      for(i=0;i<facids.size();i++){
            out.println(facids.elementAt(i)==null?"":facids.elementAt(i).toString());
      }
    }
    out.printEnd();

    out.printStart();
    if (amts != null){
      for(i=0;i<amts.size();i++){
            out.println(amts.elementAt(i)==null||amts.elementAt(i).toString().length()==0?"0.0":amts.elementAt(i).toString());
      }
    }

  }

  protected void update() throws Exception {
    try{
      if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"UserProfile:updating data:",getBundle());
      PersonnelProfile p = new PersonnelProfile(db);
      Hashtable newData = new Hashtable();
      newData.put("PROXY",proxy);
      newData.put("PORT",port);
      newData.put("UID",new Integer(userid));
      newData.put("FNAME",fname);
      newData.put("LNAME",lname);
      newData.put("MI",mi);
      newData.put("PHONE",phone);
      newData.put("ALTPHONE",altphone);
      newData.put("PAGER",pager);
      newData.put("EMAIL",email);
      newData.put("MAIL",mail);
      newData.put("SHIP",ship);
      newData.put("FAX",fax);
      newData.put("FAC",fac);
      newData.put("LOGONID",logonID);
      Hashtable tmpH = new Hashtable();

      Vector tmpV = new Vector();
      for (int i=0;i<rel.size();i++) {
        if (rel.elementAt(i).toString().length()==0||rel.elementAt(i).toString().equals(" ")) continue;
        tmpV.addElement(rel.elementAt(i));
      }
      newData.put("REL",tmpV);
      newData.put("FACIDS",facV);

      upmsg = p.update(newData)?"OK":"";

      UserGroup ug = new UserGroup(db);
      Vector v = ug.getAllGroupsIsMember(userid);
      for(int i=0;i<v.size();i++){
        UserGroup g = (UserGroup)v.elementAt(i);
        if(g.getGroupId().equalsIgnoreCase("generateorders")||
           g.getGroupId().equalsIgnoreCase("administrator") ||
           g.getGroupId().equalsIgnoreCase("createnewchemical") ||
           g.getGroupId().equalsIgnoreCase("createreport") ||
           g.getGroupId().equalsIgnoreCase("wastemanager")){
           g.deleteGroupMember(userid);
        }
      }

      // generate orders group
      ug = new UserGroup(db);
      ug.setGroupId("GenerateOrders");
      String facilityList = "";
      for(int i=0;i<genOrdersFac.size();i++){
        if(BothHelpObjs.isBlankString(genOrdersFac.elementAt(i).toString())) continue;
        String s = genOrdersFac.elementAt(i).toString();
        ug.setGroupFacId(s);
        ug.addGroupMember(userid);
        facilityList += "'"+s+"',";
      }
      //insert record into user_group_member_application
      if (facilityList.length() > 1) {
        //remove the last commas ','
        facilityList = facilityList.substring(0,facilityList.length()-1);
      }
      PersonnelProfile pp = new PersonnelProfile(db);
      pp.updateUserGroupMemberApplication(userid,facilityList,facV);
      pp = null;

      // administrator group
      ug = new UserGroup(db);
      ug.setGroupId("Administrator");
      for(int i=0;i<adminFac.size();i++){
        if(BothHelpObjs.isBlankString(adminFac.elementAt(i).toString())) continue;
        String s = adminFac.elementAt(i).toString();
        ug.setGroupFacId(s);
        ug.addGroupMember(userid);
      }

      // createNewChem requests group
      ug = new UserGroup(db);
      ug.setGroupId("CreateNewChemical");
      for(int i=0;i<newChemFac.size();i++){
        if(BothHelpObjs.isBlankString(newChemFac.elementAt(i).toString())) continue;
        String s = newChemFac.elementAt(i).toString();
        ug.setGroupFacId(s);
        ug.addGroupMember(userid);
      }

      //create Waste management group
      ug = new UserGroup(db);
      ug.setGroupId("WasteManager");
      for(int i=0;i<managerFac.size();i++){
        if(BothHelpObjs.isBlankString(managerFac.elementAt(i).toString())) continue;
        String s = managerFac.elementAt(i).toString();
        ug.setGroupFacId(s);
        ug.addGroupMember(userid);
      }

      //create Waste management group
      ug = new UserGroup(db);
      ug.setGroupId("CreateReport");
      for(int i=0;i<reportFac.size();i++){
        if(BothHelpObjs.isBlankString(reportFac.elementAt(i).toString())) continue;
        String s = reportFac.elementAt(i).toString();
        ug.setGroupFacId(s);
        ug.addGroupMember(userid);
      }

      //ug.removeUserFromExtraGroups(userid);

      Personnel pl = new Personnel(db);
      pl.setPersonnelId(Integer.parseInt(userid));
      pl.removeExtraApproverAuths();
      pl.removeExtraReleaserAuths();

      if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"UserProfile:calling done",getBundle());
    } catch (Exception e1){e1.printStackTrace(); throw e1; }
  }

  protected void changingPassword() throws Exception {
    Password pw = new Password(db,userid,"justAString");
    pwChanged = pw.changePassword(newPW);
  }

  protected void printUpdateMessage() throws IOException {
     if (upmsg ==null) return;
     out.println(upmsg);
  }

  protected void printGenOrdersFac() throws IOException {
     if (genOrdersFac ==null) return;
     try{
       out.println(genOrdersFac);
     }catch(Exception e){e.printStackTrace();}
  }
  protected void printAdminFac() throws IOException {
     if (adminFac ==null) return;
     try{
       out.println(adminFac);
     }catch(Exception e){e.printStackTrace();}
  }

  protected void printNewChemFac() throws IOException {
     if (adminFac ==null) return;
     try{
       out.println(newChemFac);
     }catch(Exception e){e.printStackTrace();}
  }

  protected void printManagerFac() throws IOException {
     if (adminFac ==null) return;
     try{
       out.println(managerFac);
     }catch(Exception e){e.printStackTrace();}
  }

  protected void printCreateReportFac() throws IOException {
     if (adminFac ==null) return;
     try{
       out.println(reportFac);
     }catch(Exception e){e.printStackTrace();}
  }

  protected void printDefFac() throws IOException {
     if (defFac == null) {
         out.printEnd();
         out.printStart();
         return;
     }
     Vector facid = new Vector();
     Vector facdesc = new Vector();
     for (Enumeration E=defFac.keys();E.hasMoreElements();){
        Object tmpO = E.nextElement();
        facid.addElement(tmpO);
        facdesc.addElement(defFac.get(tmpO));
     }

     if (facid != null){
       for (int i=0;i<facid.size();i++){
          out.println(facid.elementAt(i)==null?"":facid.elementAt(i).toString());
       }
     }
     out.printEnd();

     out.printStart();
     if (facdesc != null){
       for (int i=0;i<facdesc.size();i++){
          out.println(facdesc.elementAt(i)==null?"":facdesc.elementAt(i).toString());
       }
     }

  }

  protected void printChangePW() throws IOException  {
    out.println(pwChanged?"0":"1");
  }

  protected void printUserLogon() throws IOException  {
    out.println(userLogon);
  }

  protected  int getServInt(){
    return TcmisOutputStreamServer.USER_PROFILE;
  }
}
