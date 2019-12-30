package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class AdminNew extends TcmisServletObj {
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;
  Vector containerId = null;

  public AdminNew(ServerResourceBundle b, TcmISDBModel d) {
    super();
    bundle = b;
    db = d;
  }

  protected void resetAllVars() {
    function = null;
    inData = null;
  }

  protected void print(TcmisOutputStreamServer out) {
    try {
      out.sendObject( (Hashtable) mySendObj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void getResult() {
    mySendObj = new Hashtable();
    inData = (Hashtable) myRecvObj;
    //System.out.println("in data: " + inData);
    String function = (String) inData.get("ACTION");
    if (function.equalsIgnoreCase("LOAD_DATA")) {
      loadData();
    } else if (function.equalsIgnoreCase("UPDATE_USE_APPROVAL")) {
      updateUseApproval();
    } else if (function.equalsIgnoreCase("LOAD_MEMBER_GROUP_SCREEN")) {
      loadMemberGroupScreen();
    } else if (function.equalsIgnoreCase("UPDATE_GROUP_MEMBERSHIP")) {
      updateGroupMembership();
    } else if (function.equalsIgnoreCase("LOAD_WORK_AREA_INFO")) {
      loadWorkAreaInfo();
    }
    //System.out.println("outData:"+mySendObj);
  } //end of method

  void loadWorkAreaInfo() {
    Vector statusV = new Vector(2);
    statusV.addElement("Active");
    statusV.addElement("Inactive");
    Vector locs = new Vector(2);
    //Vector sendData = new Vector(5);
    try {
      /*
             ApplicationView av = new ApplicationView(db);
             Vector v = new Vector();
             Personnel p = new Personnel(db);
             p.setPersonnelId(Integer.parseInt((String)inData.get("USERID")));
             if(p.isSuperUser()) {
        v = av.getAllApps("");
             } else {
        v = av.getAppsForFacs(p.getAdminFacilities());
             }
             for(int i = 0; i < v.size(); i++) {
        ApplicationView a = (ApplicationView) v.elementAt(i);
        if(a.getApplication().equalsIgnoreCase("all")) {
          continue;
        }
        sendData.addElement(a.getFacilityId());
        sendData.addElement(a.getApplication());
        sendData.addElement(a.getApplicationDesc());
        sendData.addElement(a.getLocationId());
        sendData.addElement(a.getWorkAreaStatus());     //trong 1-29-01
             }*/
      ApplicationView av = new ApplicationView(db);
      Hashtable h = av.getWorkAreaInfo( (String) inData.get("USERID"));
      mySendObj.put("DATA", (Vector) h.get("FAC_LOC_APP"));
      mySendObj.put("FACILITY_ID", (Vector) h.get("FACILITY_ID"));
      mySendObj.put("FACILITY_NAME", (Vector) h.get("FACILITY_NAME"));

      Location l = new Location(db);
      Vector v1 = l.getAllLocations();
      for (int i = 0; i < v1.size(); i++) {
        Location ln = (Location) v1.elementAt(i);
        locs.addElement(ln.getLocationId());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    mySendObj.put("LOCATIONS", locs);
    mySendObj.put("STATUS", statusV);
  } //end of method

  /**
   * Description of the Method
   */
  void updateGroupMembership() {
    try {
      UserGroup ug = new UserGroup(db);
      ug.setGroupType("Approval");
      String memberId = (String) inData.get("MEMBER_ID");
      Vector facIDs = (Vector) inData.get("FACILITY_ID");
      Hashtable h = (Hashtable) inData.get("UPDATE_INFO");
      for (int j = 0; j < facIDs.size(); j++) {
        String facID = (String) facIDs.elementAt(j);
        if (h.containsKey(facID)) {
          Hashtable innerH = (Hashtable) h.get(facID);
          Vector gID = (Vector) innerH.get("GROUP_ID");
          Vector mID = (Vector) innerH.get("MEMBER_GROUP_ID");
          //first delete all user group for this user
          for (int i = 0; i < gID.size(); i++) {
            ug.setGroupId(gID.elementAt(i).toString());
            ug.setGroupFacId(facID);
            ug.deleteGroupMember(memberId);
          }
          //next add user group to user
          for (int k = 0; k < mID.size(); k++) {
            ug.setGroupId(mID.elementAt(k).toString());
            ug.setGroupFacId(facID);
            ug.addGroupMember(memberId);
          }
        }
      }
      mySendObj.put("RETURN_CODE", new Boolean(true));
      mySendObj.put("MSG", "Updating user group member data completed.");
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("MSG", "An error occurred while updating user group member data.\nPlease restart tcmIS and try again.");
    }
  }

  /**
   * Description of the Method
   */
  void loadMemberGroupScreen() {
    try {
      Hashtable dataH = new Hashtable();
      String userid = (String) inData.get("USER_ID");
      String memberId = (String) inData.get("MEMBER_ID");
      UserGroup ug = new UserGroup(db);
      ug.setGroupType("Approval");
      Vector grp = ug.getApprovalGroupsCanEdit(userid);
      Vector mem = ug.getAllGroupsIsMember(memberId);
      for (int i = 0; i < grp.size(); i++) {
        UserGroup g = (UserGroup) grp.elementAt(i);
        if (g.getGroupId().equalsIgnoreCase("all")) {
          continue;
        }
        String facID = g.getGroupFacId();
        if (dataH.containsKey(facID)) {
          Hashtable h = (Hashtable) dataH.get(facID);
          Vector groupID = (Vector) h.get("GROUP_ID");
          Vector groupDesc = (Vector) h.get("GROUP_DESC");
          Vector mGroupID = (Vector) h.get("MEMBER_GROUP_ID");
          Vector mGroupDesc = (Vector) h.get("MEMBER_GROUP_DESC");
          boolean match = false;
          for (int j = 0; j < mem.size(); j++) {
            UserGroup m = (UserGroup) mem.elementAt(j);
            if (m.equals(g)) {
              match = true;
              break;
            }
          }
          if (match) {
            mGroupID.addElement(g.getGroupId());
            mGroupDesc.addElement(g.getGroupDesc());
          } else {
            groupID.addElement(g.getGroupId());
            groupDesc.addElement(g.getGroupDesc());
          }
          h.put("GROUP_ID", groupID);
          h.put("GROUP_DESC", groupDesc);
          h.put("MEMBER_GROUP_ID", mGroupID);
          h.put("MEMBER_GROUP_DESC", mGroupDesc);
          dataH.put(facID, h);
        } else {
          Hashtable h = new Hashtable();
          Vector groupID = new Vector();
          Vector groupDesc = new Vector();
          Vector mGroupID = new Vector();
          Vector mGroupDesc = new Vector();
          boolean match = false;
          for (int j = 0; j < mem.size(); j++) {
            UserGroup m = (UserGroup) mem.elementAt(j);
            if (m.equals(g)) {
              match = true;
              break;
            }
          }
          if (match) {
            mGroupID.addElement(g.getGroupId());
            mGroupDesc.addElement(g.getGroupDesc());
          } else {
            groupID.addElement(g.getGroupId());
            groupDesc.addElement(g.getGroupDesc());
          }
          h.put("GROUP_ID", groupID);
          h.put("GROUP_DESC", groupDesc);
          h.put("MEMBER_GROUP_ID", mGroupID);
          h.put("MEMBER_GROUP_DESC", mGroupDesc);
          dataH.put(facID, h);
        }
      }
      mySendObj.put("DATA", dataH);
      mySendObj.put("TABLE_STYLE", ug.getTableStyle());
      mySendObj.put("RETURN_CODE", new Boolean(true));
      mySendObj.put("MSG", "Loading use user group member data completed.");
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("MSG", "An error occurred while loading user group member data.\nPlease restart tcmIS and try again.");
    }
  }

  protected void updateUseApproval() {
    try {
      UseApprover ua = new UseApprover(db);
      Hashtable h = (Hashtable) inData.get("UPDATE_DATA");
      String userID = (String) inData.get("MEMBER_ID");
      //first delete all info for this user
      ua.setPersonnelId(userID);
      ua.delete();
      //next update records
      Vector facIDs = (Vector) inData.get("FACILITIES");
      for (int i = 0; i < facIDs.size(); i++) {
        String facID = (String) facIDs.elementAt(i);
        if (h.containsKey(facID)) {
          Vector v = (Vector) h.get(facID);
          for (int j = 0; j < v.size(); j++) {
            String app = (String) v.elementAt(j);
            //app = app.substring(0,app.indexOf(" - "));
            ua.createUseApprover(userID, facID, app);
          }
        }
      }
      mySendObj.put("RETURN_CODE", new Boolean(true));
      mySendObj.put("MSG", "Updating use approval data completed.");
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("MSG", "Error occurred while updating data.\nPlease restart tcmIS and try again.");
    }
  }

  protected void loadData() {
    try {
      UseApprover ua = new UseApprover(db);
      mySendObj.put("SCREEN_DATA", ua.loadScreenData( (String) inData.get("MEMBER_ID"), (Vector) inData.get("FACILITIES")));
      mySendObj.put("RETURN_CODE", new Boolean(true));
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("RETURN_CODE", new Boolean(false));
      mySendObj.put("MSG", "Error occurred while loading screen data.\nPlease restart tcmIS and try again.");
    }
  } //end of method
} //end of class