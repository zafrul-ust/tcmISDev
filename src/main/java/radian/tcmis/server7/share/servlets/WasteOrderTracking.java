package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;


public class WasteOrderTracking extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  public final String[] mainCols =  {"STATUS","SHIPMENT_ID","VENDOR","DATE_SUBMITTED",
                                     "DATE_RESUBMITTED","DATE_CANCELLED","DATE_SHIPPED","COPY1_RETURN","COD","INVOICE","ORDER_NO"};

  public WasteOrderTracking(ServerResourceBundle b, TcmISDBModel d ){
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
    String action = (String)inData.get("ACTION");
    if (action.equalsIgnoreCase("search"))
      loadWasteOrderTracking();
    if (action.equalsIgnoreCase("getInitialInfo"))
      getInitialInfo();
  }

  public void loadWasteOrderTracking() {
    doSearch();
  }

  public void getInitialInfo() {
    try{
      Integer userId = (Integer)inData.get("USER_ID");
      Hashtable initialInfo = WasteShipmentScreen.getInitialInfo3(db,userId.intValue(),"B");       //B - Both active and inactive
      Personnel p = new Personnel(db);
      p.setPersonnelId(userId.intValue());
      p.load();
      mySendObj.put("PREFER_FACILITY",p.getFacilityId());
      mySendObj.put("INITIAL_INFO",initialInfo);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void doSearch(){
    try{
      mySendObj.put("SEARCH_DATA",WasteOrderTrackingScreen.doSearch(db,(Hashtable)inData.get("SEARCH_INFO")));
      mySendObj.put("COLUMN_KEY",(Hashtable)getColKey());
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public Hashtable getColKey(){
    Hashtable h = new Hashtable();
    for (int i=0;i<mainCols.length;i++) {
      h.put(mainCols[i],String.valueOf(i));
    }
    return h;
  }
}


