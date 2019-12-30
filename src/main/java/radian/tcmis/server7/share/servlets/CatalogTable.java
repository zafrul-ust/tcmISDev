package radian.tcmis.server7.share.servlets;

import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.server7.share.dbObjs.SearchPScreen;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CatalogTable extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;
  Vector containerId = null;

  public CatalogTable(ServerResourceBundle b, TcmISDBModel d ){
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
    //System.out.println("I am in server7 in data: " + inData);
    String function = (String)inData.get("ACTION");
    if (function.equalsIgnoreCase("GET_TABLE_DATA")) {
      getTableData();
    }else if (function.equalsIgnoreCase("GET_INVENTORY_SPEC_INFO")) {
      getInventorySpecInfo();
    }else if (function.equalsIgnoreCase("UPDATE_COMMENT")) {
      updateComment();
    }else if (function.equalsIgnoreCase("GET_ITEM_INVENTORY_INFO")) {
      getItemInventoryInfo();
    }else if (function.equalsIgnoreCase("GET_ITEM_DOCS_INFO")) {
      getItemDocsInfo();
    }
    //System.out.println("outData:"+mySendObj);
  }

  protected void getItemDocsInfo() {
    try {
      SearchPScreen scr = new SearchPScreen(db);
      scr.getItemImageMfgLit(mySendObj,inData.get("ITEM_ID").toString());
      mySendObj.put("SUCCEED",new Boolean(true));
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEED",new Boolean(false));
      mySendObj.put("MSG","Error occurred while retrieving data.\nPlease exit tcmIS, relogon and try again.");
    }
  } //end of method

  protected void getItemInventoryInfo() {
     try {
      SearchPScreen scr = new SearchPScreen(db);
      mySendObj.put("ITEM_INVENTORY",scr.getItemInventoryInfo(inData));
      scr.getItemImageMfgLit(mySendObj,(String)inData.get("ITEM_ID"));
      mySendObj.put("SUCCEED",new Boolean(true));
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEED",new Boolean(false));
      mySendObj.put("MSG","Error occurred while retrieving data.\nPlease exit tcmIS, relogon and try again.");
    }
  }

  protected void updateComment() {
    try {
      SearchPScreen scr = new SearchPScreen(db);
      if (scr.updateComment(inData)) {
        mySendObj.put("SUCCEED",new Boolean(true));
      }else {
        mySendObj.put("SUCCEED",new Boolean(false));
        mySendObj.put("MSG","Error occurred while updating data.\nPlease exit tcmIS, relogon and try again.");
      }
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEED",new Boolean(false));
      mySendObj.put("MSG","Error occurred while updating data.\nPlease exit tcmIS, relogon and try again.");
    }
  }

  protected void getInventorySpecInfo() {
    try {
      SearchPScreen scr = new SearchPScreen(db);
      Hashtable h = scr.getInventorySpecInfo(inData);
      mySendObj.put("PART_INVENTORY",(Float)h.get("PART_INVENTORY"));
      mySendObj.put("ITEM_INVENTORY",(String[])h.get("ITEM_INVENTORY"));
      mySendObj.put("SPEC",(String[])h.get("SPEC"));
      mySendObj.put("SPEC_ON_LINE",(String[])h.get("SPEC_ON_LINE"));
      mySendObj.put("SUCCEED",new Boolean(true));
      //1-26-03
      mySendObj.put("FLOW_DOWN",(Vector)h.get("FLOW_DOWN"));
      mySendObj.put("FLOW_DOWN_URL",(Vector)h.get("FLOW_DOWN_URL"));
      mySendObj.put("REORDER_POINT",(String)h.get("REORDER_POINT"));
      mySendObj.put("STOCKING_LEVEL",(String)h.get("STOCKING_LEVEL"));
      mySendObj.put("APPROVED_PROCESS",scr.getApprovedProcess(inData));
    }catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEED",new Boolean(false));
      mySendObj.put("MSG","Error occurred while retrieving data.\nPlease exit tcmIS, relogon and try again.");
    }
  }

  protected void getTableData() {
    try {
      SearchPScreen scr = new SearchPScreen(db);
      mySendObj.put("SEARCH_RESULT",scr.getTableDataNew(inData));
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

} //end of class
