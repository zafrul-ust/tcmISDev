package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;


public class SearchInfoNew extends TcmisServletObj {
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  public SearchInfoNew(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
  }

  protected void getResult(){
    mySendObj    = new Hashtable();
    inData = (Hashtable)myRecvObj;
    function = (String)inData.get("ACTION");
    try {
      if (function.equalsIgnoreCase("SEARCH_PERSONNEL")){
        searchPersonnel();
      }else if (function.equalsIgnoreCase("SEARCH_CATALOG_DIRECTED_CHARGE")) {
        searchCatalogDirectedCharge();
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  protected void searchCatalogDirectedCharge()  throws Exception {
    try {
      NChem nchem = new NChem(db);
      mySendObj.put("COST_ELEMENT",nchem.getCostELement(inData));
    }catch(Exception e) {
      e.printStackTrace();
      mySendObj.put("COST_ELEMENT","");
    }
  }

  protected void searchPersonnel()  throws Exception {
    try {
      Personnel p = new Personnel(db);
      mySendObj.put("DATA",p.getNameSearch((Integer)inData.get("USER_ID"),(String)inData.get("FACILITY_ID"),(String)inData.get("SEARCH_TEXT"),(String)inData.get("SEARCHBY")));
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    } catch(Exception e){
      e.printStackTrace();
    }
  }

}