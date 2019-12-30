package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class UserProfileNew extends TcmisServletObj {
  //Client Version
  private String function = null;
  private Hashtable mySendObj = null;
  private Hashtable inData = null;

  public UserProfileNew(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
  }

  protected void getResult(){
    mySendObj = new Hashtable();
    inData = (Hashtable)myRecvObj;
    function = (String)inData.get("ACTION");
    if ("LOAD_SCREEN".equalsIgnoreCase(function)){
      getScreenData();
    }else if ("UPDATE".equalsIgnoreCase(function)){
      update();
    }else if("CHANGE_PASSWORD".equalsIgnoreCase(function)) {
      changingPassword();
    }else if ("LOAD_FACILITY_DATA".equalsIgnoreCase(function)) {
      getFacilityData();
    }else if ("CREATE_NEW_USER".equalsIgnoreCase(function)) {
      createNewUser();
    }

  } //end of method

  protected void createNewUser() {
    try {
      Personnel personnel = new Personnel(db);
      mySendObj.put("DATA",personnel.createNewUser(inData));
    }catch(Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("ERROR while creating new user","data: "+inData+"\n"+e.getMessage());
    }


  } //end of method

  protected void getFacilityData() {
    Integer userID = (Integer)inData.get("USER_ID");
    try {
      PersonnelProfile p = new PersonnelProfile(db);
      mySendObj.put("DATA",p.getFacilityData(userID.intValue()));
      mySendObj.put("MSG","OK");
    }catch(Exception e){
      e.printStackTrace();
      mySendObj.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
      radian.web.emailHelpObj.sendtrongemail("ERROR while loading facility info","User: "+userID.toString()+"\n"+e.getMessage());
    }
  } //end of method

  protected void getScreenData() {
    Integer userID = (Integer)inData.get("USER_ID");
    try {
      //first get personnel info
      PersonnelProfile p = new PersonnelProfile(db);
      mySendObj.put("DATA",p.getUserInfo(userID.intValue()));
    }catch(Exception e){
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("ERROR while loading personnal info","User: "+userID.toString()+"\n"+e.getMessage());
    }
  } //end of method

  protected void update() {
    Integer userID = (Integer)inData.get("USER_ID");
    try{
      PersonnelProfile p = new PersonnelProfile(db);
      mySendObj.put("MSG",p.updateUserInfo(inData));
      //get facilities that user is not in finance_approver_list
      Vector v = (Vector) inData.get("FAC_NOT_IN_APPROVAL_TREE");
      if (v.size() > 0) {
        mySendObj.put("MSG","Incomplete");
        String facList = "";
        for (int i = 0; i < v.size(); i++) {
          if (i % 6 == 0 && i != 0) {
            facList += "\n";
          }
          facList += (String)v.elementAt(i)+", ";
        }
        //remove the last commas ','
        if (facList.length() > 1) {
          facList = facList.substring(0,facList.length()-2);
        }
        String reason = "The following facilities required financial approval:\n"+facList+
                        "\nPlease add user to the financial approval tree for the above facilities."+
                        "\nThen click in 'Update' again.\nAny facilities not added to the finanical tree will"+
                        "\nappear as 'display only' to the user.";
        mySendObj.put("REASON",reason);
      }
    } catch (Exception e){
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("ERROR while update personnal info","User: "+userID.toString()+"\n"+e.getMessage());
      mySendObj.put("MSG","");
    }
  } //end of method

  protected void changingPassword() {
    String logonId = (String)inData.get("LOGON_ID");
    Integer userID = (Integer)inData.get("USER_ID");
    String newPW = (String)inData.get("NEW_PASSWORD");
    try {
      Password pw = new Password(db, logonId, "justAString");
      if (pw.changePassword(newPW)) {
        mySendObj.put("MSG", "OK");
      }else {
        mySendObj.put("MSG", "");
      }
    }catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("ERROR while changing password","User: "+userID+"\n"+e.getMessage());
      mySendObj.put("MSG", "");
    }
  }


  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}