package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class WasteLabel extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;
  Vector containerId = null;

  public WasteLabel(ServerResourceBundle b, TcmISDBModel d ){
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
    //System.out.println("in data: " + inData);
    containerId = (Vector)inData.get("CONTAINER_ID");
    loadWasteLabel();
    //System.out.println("outData:"+mySendObj);
  }

  public void loadWasteLabel() {
    WasteLabelTraveler wlt = new WasteLabelTraveler(db);
    Vector v = new Vector();
    try{
    v = wlt.buildWasteLabel(db, containerId);
    }catch(Exception ex)
    {ex.printStackTrace();}

    mySendObj.put("CONTAINER_INFO",v);
  }


}

