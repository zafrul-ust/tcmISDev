package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class PersonnelInfo extends TcmisServlet{
  //Client Version
  String action = null;
  String actiontype = null;
  String fac = null;
  String  who = null;
  String newid = null;
  String uid = null;
  String facid = null;
  Vector whoV = null;
  String msg = null;
  String fname = null;
  String lname = null;
  String mi = null;
  String logonId = null;


  public PersonnelInfo(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    action = null;
    actiontype = null;
    fac = null;
    who = null;
    newid = null;
    uid = null;
    facid = null;
    whoV = null;
    msg = null;
    fname = null;
    lname = null;
    mi = null;
    logonId = null;
  }

  protected void getResult(HttpInput httpI){
    try {
      action = httpI.getString("ACTION");
      actiontype = httpI.getString("ACTIONTYPE");
      fac = httpI.getString("FAC");
      who = httpI.getString("WHO");
      uid = httpI.getString("UID");
      fname = httpI.getString("FNAME");
      lname = httpI.getString("LNAME");
      mi = httpI.getString("MI");
      logonId = httpI.getString("LOGONID");

      if (action.equals("GET_NEXT_NAME")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Personnel:getResult = Going getnextname",getBundle());
        getNextName();
      }else if (action.equals("GET_NEW_UID")){
        HelpObjs.monitor(1,"uid1:" + uid,getBundle());
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Personnel:getResult = Going getnewuid",getBundle());
        getNewUid();
      }else if (action.equals("INSERT_USER")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Personnel:getResult = Going insertuser",getBundle());
        insertUser();
      }else if (action.equals("DELETE_USER")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Personnel:getResult = Going deleteuser",getBundle());
        deleteUser();
      }
    } catch (Exception e){e.printStackTrace();return;    }
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.printStart();
      printNextName();
      out.printEnd();
      out.printStart();
      if (newid != null) out.println(newid);
      out.printEnd();
      out.printStart();
      printMsg();
      out.printEnd();
    }catch(Exception e){}
  }
  protected void getNextName() throws Exception {
     Personnel PR =  new Personnel(db);
     whoV = PR.getNextName(actiontype,fac,who);
  }
  protected void  getNewUid() throws Exception {
      Personnel p = new Personnel(db);
      newid = p.getNewUid().toString();
      return;
  }
  void insertUser()  throws Exception {
       //10-25-01 first get the new personnel id from seq
       try {
        getNewUid();
        uid = newid;
       }catch (Exception ee) {
        ee.printStackTrace();
        msg = "User ID already exist.";
        return;
       }

       Personnel p = new Personnel(db);
       HelpObjs.monitor(1,"uid2:" + uid,getBundle());

       Integer I = null;
       try{
         I = new Integer(uid);
       }catch(Exception e){
         msg = "User ID must be a number.";
         return;
       }
       p.setPersonnelId((new Integer(uid)).intValue());
       p.setFacilityId(fac);
       p.setUserLogon(logonId);
       if (p.isAvailable()){
          p.insert2();
          msg = new String("OK");
       }else{
         msg = "User ID number "+uid+" is already in use.";
         return;
       }
       p.setFirstName(fname);
       p.setLastName(lname);
       p.setMidInitial(mi);
       p.update();
       //now insert record in fac_pref
       db.doUpdate("insert into fac_pref (personnel_id,facility_id) values("+uid+",'"+fac+"')");
  } //end of method

  void deleteUser()  throws Exception {
       Personnel p = new Personnel(db);
       p.setPersonnelId((new Integer(uid)).intValue());
       if (p.delete()){
          msg = new String("OK");
       }
  }
  protected void printNextName()  throws IOException {
     if (whoV == null) return;
     if (whoV.size() == 0) return;
     for (int i=0;i<whoV.size();i++){
       out.println(whoV.elementAt(i).toString());
     }
  }
  protected void printMsg()  throws IOException {
     if (msg == null) return;
     if (msg.length() == 0) return;
     out.println(msg);
  }

  protected  int getServInt(){
    return TcmisOutputStreamServer.PERSONNEL;
  }
}
