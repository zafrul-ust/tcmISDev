package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;


public class WasteTrack extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  public final String[] mainCols = {"Waste Item Id","Status","Requested Transfer","Age","Transferred","From","To","Container Id","Packaging","Waste Profile","Description",
                                    "Work Area","Requester","DOT Desc.","Replace Container","Transfer Date","Note","Days Since Pickup","Real Notes",
                                    "Waste Request Id","Line Item","Request Date","Age Warning"};

  public WasteTrack(ServerResourceBundle b, TcmISDBModel d ){
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
    loadWasteTrack();
    //System.out.println("outData:"+mySendObj);
  }

  public void loadWasteTrack() {
    doSearch();
  }


  protected void doSearch(){
    Vector v = (Vector)inData.get("UPDATE_VECTOR");
    String action = (String)inData.get("ACTION");
    Integer userId = (Integer)inData.get("USER_ID");
    String facilityId = (String)inData.get("FACILITY_ID");
    WasteRequestLineItem wrli = new WasteRequestLineItem(db);
    try{
      if ((action.equalsIgnoreCase("update")) && (v.size() > 0)) {
        for (int i = 0; i < v.size(); i++) {
          Hashtable h = new Hashtable();
          h = (Hashtable)v.elementAt(i);
          Integer num = new Integer(h.get("LINE_ITEM").toString());
          int lineNum = num.intValue();
          wrli.setLineItem(lineNum);
          num = new Integer(h.get("WASTE_REQUEST_ID").toString());
          int wasteRequestId = num.intValue();
          wrli.setWasteRequestId(wasteRequestId);
          wrli.load();
          int travelerId = wrli.getTravelerId();
          Integer containerId = new Integer(h.get("CONTAINER_ID").toString());
          //updating traveler container with transfer date
          WasteTravelerContainer wtc = new WasteTravelerContainer(db);
          wtc.setContainerId(containerId.intValue());
          wtc.setTravelerId(travelerId);
          wtc.insert("transfer_date",(String)h.get("TRANSFER_DATE"),WasteTravelerContainer.DATE);
          //updating waste traveler with pickup user id
          WasteTraveler wt = new WasteTraveler(db);
          wt.setTravelerId(travelerId);
          Integer pickupUserId = new Integer(inData.get("USER_ID").toString());
          wt.insert("pickup_user_id",new String(pickupUserId.toString()),WasteTraveler.INT);
          //updating waste container current_location_id
          WasteContainer wc = new WasteContainer(db);
          wc.setContainerId(containerId.intValue());
          wc.insert("current_location_id",(String)h.get("TO_LOCATION_ID"),WasteContainer.STRING);
        }
      }

      mySendObj.put("SEARCH_DATA",WasteTrackScreen.doSearch(db,(String)inData.get("FACILITY_ID"),(String)inData.get("ACTION"),(String)inData.get("DAYS_SINCE_PICKUP")));
      Vector prefFac = WasteTrackScreen.getPreferFac(db,userId.intValue());
      Personnel p = new Personnel(db);
      p.setPersonnelId(userId.intValue());
      p.load();
      mySendObj.put("USER_NAME",p.getFullName());
      mySendObj.put("PREFER_FAC",prefFac);
      mySendObj.put("COLUMN_KEY",(Hashtable)getColKey());

      //checking to see if the user is a waste manager
      UserGroup ug = new UserGroup(db);
      ug.setGroupFacId(facilityId);
      ug.setGroupId(new String("WasteManager"));
      mySendObj.put("IS_WASTE_MANAGER",new Boolean(ug.isWasteManager(userId.intValue())));
      //checking to see id user is waste pickup
      ug.setGroupId("WastePickup");
      mySendObj.put("IS_WASTE_PICKUP",new Boolean(ug.isMember(userId.intValue())));

      //checking to see if the user can delete container(s)
      ug.setGroupFacId("All");
      ug.setGroupId("WasteContainerDelete");
      mySendObj.put("ALLOW_DELETE",new Boolean(ug.isMember(userId.intValue())));
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


