package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;


public class NewChemTrack extends TcmisServletObj {
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  public NewChemTrack(ServerResourceBundle b, TcmISDBModel d ){
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
    inData = (Hashtable)myRecvObj;
    function = inData.get("ACTION").toString();
    if(function.equalsIgnoreCase("LOAD_TABLE")) {
      loadTable();
    }else if(function.equalsIgnoreCase("APPROVAL_DETAIL")) {
      loadApprDetail();
    }else if (function.equalsIgnoreCase("LOAD_INITIAL_DATA")) {
      loadInitialData();
    }
  }

  protected void loadInitialData() {
    try {
      mySendObj.put("USER_NAME",Personnel.getUserName(db,(String)inData.get("USERID")));
      mySendObj.put("STATUSES",NewChemTrackTable.getVVStatus(db));
      mySendObj.put("RETURN_CODE",new Boolean(true));
    }catch (Exception e) {
      mySendObj.put("USER_NAME","");
      e.printStackTrace();
      mySendObj.put("RETURN_CODE",new Boolean(false));
    }
  }

  protected void loadTable() {
    try{
      NewChemTrackTable nctt = new NewChemTrackTable(db);
      String requestor = (String)inData.get("REQUESTOR");
      String approver = (String)inData.get("APPROVER");
      String userid = (String)inData.get("USERID");
      String facility = (String)inData.get("FACILITY");
      String workArea = (String)inData.get("WORK_AREA");
      String reqID = (String)inData.get("REQ_ID");
      String searchString = (String)inData.get("SEARCH_STRING");
      String needApp = (String)inData.get("NEED_APPROVAL");
      String status = (String)inData.get("STATUS_SELECTION");
      Object[][] oa = nctt.getTableData(userid,requestor,reqID,approver,facility,workArea,searchString,status,needApp.equalsIgnoreCase("true"));
      if (oa == null){
        mySendObj.put("DATA",null);
        mySendObj.put("NUM_RECS","0");
      }else{
        Vector sendData = BothHelpObjs.getVectorFrom2DArray(oa);
        for(int x=0;x<sendData.size();x++) {
          if(sendData.elementAt(x)==null){
            sendData.setElementAt("",x);
          }
        }
        mySendObj.put("DATA",sendData);
        mySendObj.put("NUM_RECS",String.valueOf(oa.length));
      }
   }catch(Exception e){ e.printStackTrace();}
  }

  protected void loadApprDetail() {
    String reqID = (String)inData.get("REQ_ID");
    Vector sendData = new Vector();
    try{
      NewChemApproval ca = new NewChemApproval(db);
      Hashtable h = ca.getApprovalDetailInfo(reqID);
      mySendObj.put("DATA",(Vector)h.get("HEADER_INFO"));
      mySendObj.put("MATERIAL_TABLE_DATA",(Vector)h.get("MATERIAL_INFO"));
      mySendObj.put("ROLES",(Vector)h.get("ROLE"));
      mySendObj.put("STATUS",(Vector)h.get("STATUS"));
      mySendObj.put("APPROVERS",(Vector)h.get("APPROVER_NAME"));
      mySendObj.put("DATES",(Vector)h.get("DATE"));
      mySendObj.put("REASONS",(Vector)h.get("REASON"));
      mySendObj.put("APPROVAL_GROUP",(Vector)h.get("APPROVAL_GROUP"));
   }catch(Exception e){ e.printStackTrace();}
  }

  protected  int getServInt(){
    return TcmisOutputStreamServer.NEW_CHEM_TRACKING;
  }
}

