package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;

public class PersonnelProfile {

  protected TcmISDBModel db;

  public PersonnelProfile() throws Exception {

  }

  public PersonnelProfile(TcmISDBModel db) throws Exception {
    this.db = db;
  }

  public void setDB(TcmISDBModel db) {
    this.db = db;
  }

  public Vector getScreenData(int userId) throws Exception {
    Vector result = new Vector();
    try {
      Personnel p = new Personnel(db);
      p.setPersonnelId(userId);
      p.load();
      result.addElement(p.getPersonnelId() == null ? "" : p.getPersonnelId().toString());
      result.addElement(p.getFirstName() == null ? "" : p.getFirstName());
      result.addElement(p.getLastName() == null ? "" : p.getLastName());
      result.addElement(p.getMidInitial() == null ? "" : p.getMidInitial());
      result.addElement(p.getProxy() == null ? "" : p.getProxy());
      result.addElement(p.getPort() == null ? "" : p.getPort());
      result.addElement(p.getEmail() == null ? "" : p.getEmail());
      result.addElement(p.getPhone() == null ? "" : p.getPhone());
      result.addElement(p.getPager() == null ? "" : p.getPager());
      result.addElement(p.getFax() == null ? "" : p.getFax());
      result.addElement(p.getAltPhone() == null ? "" : p.getAltPhone());
      result.addElement(p.getMailLocation() == null ? "" : p.getMailLocation());
      result.addElement(p.getShippingLocation() == null ? "" : p.getShippingLocation());

      // Usng thendefault faiclity
      result.addElement(p.getFacilityId() == null ? "" : p.getFacilityId());
      // check approver
      if (p.isReleaser()) {
        result.addElement(new Boolean(true));
      } else {
        result.addElement(new Boolean(false));
      }

      // check releaser
      if (p.isApprover()) {
        result.addElement(new Boolean(true));
        result.addElement(p.getApproverAmt() == null
                          || p.getApproverAmt().toString().length() == 0 ? "0.0" : (p.getApproverAmt()).toString());
      } else {
        result.addElement(new Boolean(false));
        result.addElement("0.0");
      }
      result.addElement(p.getUserLogon() == null ? "" : p.getUserLogon());
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
    return result;
  }

  public void updateUserGroupMemberApplication(String userID, String generateOrderFacilities, Vector facilityList) throws Exception {
    try {
      String facIDs = "";
      //first get the list of facilities for user
      for (int i = 0; i < facilityList.size(); i++) {
        facIDs += "'" + (String) facilityList.elementAt(i) + "',";
      }
      if (facIDs.length() > 1) {
        //remove the last commas ','
        facIDs = facIDs.substring(0, facIDs.length() - 1);
        //remove record from user_group_member_application
        String query = "delete from user_group_member_application where user_group_id in ('GenerateOrders','DisplayApplication') and personnel_id = " + userID;
        db.doUpdate(query);
        //next insert new records as display for the given facilities
        query = "insert into user_group_member_application (facility_id,application,personnel_id,user_group_id) " +
            "(select facility_id,application," + userID + " personnel_id,'DisplayApplication' user_group_id from fac_loc_app_with_all" +
            " where application = 'All' and facility_id in (" + facIDs + "))";
        db.doUpdate(query);
        //update display to GenerateOrders for those facilities that users are allow to create MR for
        if (generateOrderFacilities.length() > 1) {
          query = "update user_group_member_application set user_group_id = 'GenerateOrders' where user_group_id = 'DisplayApplication'"+
                  " and personnel_id = " + userID + " and facility_id in (" + generateOrderFacilities + ")";
          db.doUpdate(query);
        }
        //call procedure to insert data in user_application
        try {
          Vector args = new Vector(2);
          args.addElement(userID);
          args.addElement("error_val");
          db.doProcedureWithErrorMsg("P_USER_APPLICATION_SYNCH",args,2);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  boolean updateFacPrefData(Hashtable inData) {
    boolean result = false;
    try {
      Integer userID = (Integer) inData.get("USER_ID");
      //first delete list
      String query = "delete from fac_pref where personnel_id = " + userID.intValue();
      db.doUpdate(query);
      //now insert new facilities
      Vector prefFacV = (Vector) inData.get("FACILITY_ID");
      query = "";
      String tmpVal = "";
      for (int i = 0; i < prefFacV.size(); i++) {
        tmpVal += "'" + (String) prefFacV.elementAt(i) + "',";
      }
      //removing the last commas and update data
      if (tmpVal.length() > 1) {
        tmpVal = tmpVal.substring(0, tmpVal.length() - 1);
        query = "insert into fac_pref (personnel_id,facility_id) " +
            "(select " + userID.intValue() + " personnel_id,facility_id from facility" +
            " where facility_id in (" + tmpVal + "))";
        db.doUpdate(query);
      }
      result = true;
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  } //end of method

  boolean updateUserGroupData(Hashtable inData) {
    boolean result = false;
    try {
      Integer userID = (Integer) inData.get("USER_ID");
      //first delete list
      String query = "delete from user_group_member where user_group_id in ('Releaser','CreateNewChemical','Administrator','WasteManager','CreateReport','CostReport')" +
          " and personnel_id = " + userID.intValue();
      db.doUpdate(query);
      //now insert new facilities
      Vector prefFacV = (Vector) inData.get("FACILITY_ID");
      Vector releaserV = (Vector) inData.get("RELEASER");
      Vector newChemV = (Vector) inData.get("CREATE_NEW_CHEM");
      Vector adminV = (Vector) inData.get("ADMIN");
      Vector wasteManagerV = (Vector) inData.get("WASTE_MANAGER");
      Vector createReportV = (Vector) inData.get("CREATE_REPORT");
      Vector createCostReportV = (Vector) inData.get("COST_REPORT");
      query = "";
      String tmpVal = "";
      for (int i = 0; i < prefFacV.size(); i++) {
        String tmpFacID = (String) prefFacV.elementAt(i);
        String tmpUserGroupID = "";
        if (releaserV.contains(tmpFacID)) {
          tmpUserGroupID += "'Releaser',";
        }
        if (newChemV.contains(tmpFacID)) {
          //don't add this user group to user for all PWA facilities
          if ("UTC".equalsIgnoreCase(db.getClient())) {
            if (!tmpFacID.startsWith("PWA")) {
              tmpUserGroupID += "'CreateNewChemical',";
            }
          }else {
            tmpUserGroupID += "'CreateNewChemical',";
          }
        }
        if (adminV.contains(tmpFacID)) {
          tmpUserGroupID += "'Administrator',";
        }
        if (wasteManagerV.contains(tmpFacID)) {
          tmpUserGroupID += "'WasteManager',";
        }
        if (createReportV.contains(tmpFacID)) {
          tmpUserGroupID += "'CreateReport',";
        }
        if (createCostReportV.contains(tmpFacID)) {
          tmpUserGroupID += "'CostReport',";
        }
        //now put it together
        if (tmpUserGroupID.length() > 1) {
          //remove the last commas
          tmpUserGroupID = tmpUserGroupID.substring(0, tmpUserGroupID.length() - 1);
          tmpVal += " (facility_id = '" + tmpFacID + "' and user_group_id in (" + tmpUserGroupID + ")) or";
        }
      }
      //removing the last 'or' and update data
      if (tmpVal.length() > 2) {
        tmpVal = tmpVal.substring(0, tmpVal.length() - 2);
        query = "insert into user_group_member (personnel_id,facility_id,user_group_id) " +
            "(select " + userID.intValue() + " personnel_id,facility_id,user_group_id from user_group" +
            " where (" + tmpVal + "))";
        db.doUpdate(query);
      }
      //for WasteManager - extend to all storage locations
      //first remove data
      query = "delete from user_group_member_waste_loc where user_group_id = 'WasteLocManager' and personnel_id = "+userID.intValue()+
              " and (facility_id,waste_location_id) in (select facility_id,waste_location_id from waste_location where tsdf = 'N')";
      db.doUpdate(query);
      //insert new data
      query = "insert into user_group_member_waste_loc (user_group_id,personnel_id,facility_id,waste_location_id,admin) "+
              " select 'WasteLocManager',ugm.personnel_id,ugm.facility_id,wl.waste_location_id,'N' admin"+
              " from user_group_member ugm, waste_location wl where ugm.facility_id = wl.facility_id and wl.location_type = 'storage'"+
              " and wl.tsdf = 'N' and ugm.user_group_id = 'WasteManager' and ugm.personnel_id = "+userID.intValue();
      db.doUpdate(query);
      //if everything ok
      result = true;
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  } //end of method

  boolean updateUserGroupMemberAppData(Hashtable inData) {
    boolean result = false;
    try {
      Integer userID = (Integer) inData.get("USER_ID");
      //first delete list
      String query = "delete from user_group_member_application where user_group_id in ('GenerateOrders','DisplayApplication') and personnel_id = " + userID.intValue();
      db.doUpdate(query);
      //get facilities that user is not in finance_approver_list
      Vector facNotInApprovalTree = (Vector) inData.get("FAC_NOT_IN_APPROVAL_TREE");
      //now insert new work areas
      Vector prefFacV = (Vector) inData.get("FACILITY_ID");
      Hashtable workAreaH = (Hashtable) inData.get("WORK_AREA");
      query = "";
      String tmpVal = "";
      String tmpUpdateVal = "";
      for (int i = 0; i < prefFacV.size(); i++) {
        String tmpFacID = (String) prefFacV.elementAt(i);
        String tmpWorkArea = "";
        String tmpUpdateWorkArea = "";
        if (workAreaH.containsKey(tmpFacID)) {
          Vector dataV = (Vector) workAreaH.get(tmpFacID);
          for (int k = 0; k < dataV.size(); k++) {
            Object[] oa = (Object[]) dataV.elementAt(k);
            tmpWorkArea += "'" + (String) oa[0] + "',";
            if ( ( (Boolean) oa[2]).booleanValue()) {
              tmpUpdateWorkArea += "'" + (String) oa[0] + "',";
            }
          }
        }
        //now put it together
        if (tmpWorkArea.length() > 1) {
          //remove the last commas
          tmpWorkArea = tmpWorkArea.substring(0, tmpWorkArea.length() - 1);
          tmpVal += " (facility_id = '" + tmpFacID + "' and application in (" + tmpWorkArea + ")) or";
        }
        if (tmpUpdateWorkArea.length() > 1) {
          //don't update facility to GenerateOrder if facility is in the list of facility not in finance_approver_list and facility does required finance approver
          if (!facNotInApprovalTree.contains(tmpFacID)) {
            //remove the last commas
            tmpUpdateWorkArea = tmpUpdateWorkArea.substring(0, tmpUpdateWorkArea.length() - 1);
            tmpUpdateVal += " (facility_id = '" + tmpFacID + "' and application in (" + tmpUpdateWorkArea + ")) or";
          }
        }
      } //end of for loop
      //removing the last 'or' and update data
      if (tmpVal.length() > 2) {
        tmpVal = tmpVal.substring(0, tmpVal.length() - 2);
        query = "insert into user_group_member_application (facility_id,application,personnel_id,user_group_id) " +
            "(select facility_id,application," + userID.intValue() + " personnel_id,'DisplayApplication' user_group_id from fac_loc_app_with_all" +
            " where (" + tmpVal + "))";
        db.doUpdate(query);
      }
      //next update where user can create MR
      if (tmpUpdateVal.length() > 2) {
        tmpUpdateVal = tmpUpdateVal.substring(0, tmpUpdateVal.length() - 2);
        query = "update user_group_member_application set user_group_id = 'GenerateOrders' where user_group_id = 'DisplayApplication'"+
                " and personnel_id = " + userID.intValue() + " and (" + tmpUpdateVal + ")";
        db.doUpdate(query);
      }
      //Done without error
      result = true;
      //call procedure to insert data in user_application
      try {
        Vector args = new Vector(2);
        args.addElement(userID.toString());
        args.addElement("error_val");
        db.doProcedureWithErrorMsg("P_USER_APPLICATION_SYNCH",args,2);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  } //end of method

  boolean updateGenerateOrder(Hashtable inData) {
    boolean result = false;
    try {
      Integer userID = (Integer) inData.get("USER_ID");
      //get facilities that user is not in finance_approver_list
      Vector facNotInApprovalTree = (Vector) inData.get("FAC_NOT_IN_APPROVAL_TREE");
      //now insert new work areas
      Vector prefFacV = (Vector) inData.get("FACILITY_ID");
      Hashtable workAreaH = (Hashtable) inData.get("WORK_AREA");
      String query = "";
      String tmpVal = "";
      String facilityList = "";
      for (int i = 0; i < prefFacV.size(); i++) {
        String tmpFacID = (String) prefFacV.elementAt(i);
        //ship facility if facility is in the list of facility not in finance_approver_list and facility does required finance approver
        if (facNotInApprovalTree.contains(tmpFacID)) {
          continue;
        }
        String tmpUpdateWorkArea = "";
        if (workAreaH.containsKey(tmpFacID)) {
          Vector dataV = (Vector) workAreaH.get(tmpFacID);
          for (int k = 0; k < dataV.size(); k++) {
            Object[] oa = (Object[]) dataV.elementAt(k);
            if ( ( (Boolean) oa[2]).booleanValue()) {
              tmpUpdateWorkArea += "'" + (String) oa[0] + "',";
            }
          }
        }
        //if one of the work area is Yes to Generate order then insert GenerateOrder for this facility
        if (tmpUpdateWorkArea.length() > 1) {
          tmpVal += "'" + tmpFacID + "',";
        }
        //keep track of which facilities is in user list
        facilityList += "'" + tmpFacID + "',";
      } //end of for each facility

      //first remove GenerateOrder from  user_group_member per facility and personnel
      if (facilityList.length() > 1) {
        //remove the last commas ','
        facilityList = facilityList.substring(0, facilityList.length() - 1);
        query = "delete from user_group_member where user_group_id = 'GenerateOrders' and facility_id in (" + facilityList + ") and personnel_id = " + userID.intValue();
        db.doUpdate(query);
      }
      //now insert GenerateOrder into user_group_member for facility and personnel if one of the work area is yes to GenerateOrder
      if (tmpVal.length() > 1) {
        //removing the last commas ','
        tmpVal = tmpVal.substring(0, tmpVal.length() - 1);
        query = "insert into user_group_member (personnel_id,facility_id,user_group_id) " +
            "(select " + userID.intValue() + " personnel_id,facility_id,user_group_id from user_group" +
            " where user_group_id = 'GenerateOrders' and facility_id in (" + tmpVal + "))";
        db.doUpdate(query);
      }
      //Done without error
      result = true;
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  } //end of method

  void getFacNotInApprovalTree(Hashtable inData) {
    Vector v = new Vector(47);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      Integer userID = (Integer) inData.get("USER_ID");
      Vector prefFacV = (Vector) inData.get("FACILITY_ID");
      Hashtable workAreaH = (Hashtable) inData.get("WORK_AREA");
      String facIDList = "";
      for (int i = 0; i < prefFacV.size(); i++) {
        facIDList += "'" + (String) prefFacV.elementAt(i) + "',";
      }
      //remove the last commas ','
      if (facIDList.length() > 1) {
        facIDList = facIDList.substring(0, facIDList.length() - 1);
      }
      //put query together
      String query = "select a.facility_id,a.approver_required,decode(fal.personnel_id,null,'n','y') in_tree" +
          " from (select  pr.facility_id,max(pr.approver_required) approver_required  from pr_rules pr" +
          " where pr.status = 'A' and pr.facility_id in (" + facIDList + ") group by pr.facility_id) a," +
          " finance_approver_list fal where a.facility_id = fal.facility_id(+)" +
          " and fal.personnel_id(+) = " + userID.intValue();
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        //if approver required and in_tree is no, then add facility to list of facilities where
        //user is not setup in finance_approver_list
        if ("y".equalsIgnoreCase(rs.getString("approver_required")) &&
            "n".equalsIgnoreCase(rs.getString("in_tree"))) {
          String facID = rs.getString("facility_id");
          boolean generateOrder = false;
          if (workAreaH.containsKey(facID)) {
            Vector dataV = (Vector) workAreaH.get(facID);
            for (int k = 0; k < dataV.size(); k++) {
              Object[] oa = (Object[]) dataV.elementAt(k);
              if ( ( (Boolean) oa[2]).booleanValue()) {
                generateOrder = true;
                break;
              }
            } //end of for each work area per facility
          }
          //if one of the work area is Yes to Generate order then add facility to facility user is not in finance_approver_list for
          if (generateOrder) {
            v.addElement(facID);
          }
        }
      } //end of while
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    inData.put("FAC_NOT_IN_APPROVAL_TREE", v);
  } //end of method

  public String updateUserInfo(Hashtable inData) throws Exception {
    String result = "";
    try {
      //get a list of facilities from user's facilities where he/she is not in financial_approver_list
      getFacNotInApprovalTree(inData);

      //first update personnel
      Personnel personnel = new Personnel(db);
      String tmp = personnel.updateUserPersonInfo(inData,false);
      if (!"OK".equalsIgnoreCase(tmp)) {
        return tmp;
      }

      //next update fac_pref
      if (!updateFacPrefData(inData)) {
        return result;
      }
      //now update user_group_member
      if (!updateUserGroupData(inData)) {
        return result;
      }
      //update user_group_member_application
      if (!updateUserGroupMemberAppData(inData)) {
        return result;
      }
      //finally update user_group_member GenerateOrder
      if (!updateGenerateOrder(inData)) {
        return result;
      }
      result = "OK";
    } catch (Exception e) {
      throw e;
    }
    return result;
  }

  public Hashtable getFacilityData(int userID) throws Exception {
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      //first check to see if user is a super user
      String query = "select count(*) from user_group_member where user_group_id = 'SuperUser' and personnel_id = " + userID;
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int count = 0;
      while (rs.next()) {
        count = rs.getInt(1);
      }
      //build data sql
      query = "select f.facility_id,nvl(f.facility_name,f.facility_id) facility_name,f.active,fla.application,fla.application_desc";
      //if super User then he/she see all facilities; otherwise, he/she will only see facilities that he/she is an administrator for
      if (count == 0) {
        query += " from facility f,user_group_member ugm, fac_loc_app fla" +
            " where f.facility_id = fla.facility_id and fla.status = 'A'" +
            " and f.facility_id = ugm.facility_id and f.branch_plant is null" +
            " and ugm.user_group_id = 'Administrator' and ugm.personnel_id = " + userID;
      } else {
        query += " from facility f, fac_loc_app fla where f.facility_id = fla.facility_id and fla.status = 'A' and f.branch_plant is null";
      }
      query += " and f.active in ('y','c') order by f.facility_id,fla.application_desc";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String lastFacilityID = "";
      Vector facilityDataV = new Vector();
      while (rs.next()) {
        String currentFacilityID = rs.getString("facility_id");
        if (currentFacilityID.equalsIgnoreCase(lastFacilityID)) {
          //don't need to add facilities info here
          //now get work areas
          Vector waInfoV = (Vector) result.get(currentFacilityID);
          Object[] oa = new Object[2];
          oa[0] = rs.getString("application");
          oa[1] = rs.getString("application_desc");
          waInfoV.addElement(oa);
          result.put(currentFacilityID, waInfoV);
        } else {
          //first get facilities data
          Object[] oa = new Object[4];
          oa[0] = currentFacilityID;
          oa[1] = rs.getString("facility_name");
          oa[2] = new Boolean(false); //this is use to determine whether this facility is in the user pref list
          oa[3] = rs.getString("active");
          facilityDataV.addElement(oa);
          //now get work areas
          Vector waInfoV = new Vector();
          Object[] oa2 = new Object[2];
          oa2[0] = rs.getString("application");
          oa2[1] = rs.getString("application_desc");
          waInfoV.addElement(oa2);
          result.put(currentFacilityID, waInfoV);

        }
        lastFacilityID = currentFacilityID;
      }
      result.put("FACILITY_DATA", facilityDataV);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  }

  public Hashtable getUserInfo(int userID) throws Exception {
    Hashtable result = new Hashtable(20);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      //FIRST GET USER INFO - 11 keys
      String query = "select personnel_id,nvl(first_name,' ') first_name,nvl(last_name,' ') last_name,nvl(mid_initial,' ') mid_initial," +
          "nvl(email,' ') email,nvl(phone,' ') phone,nvl(pager,' ') pager,nvl(fax,' ') fax,nvl(alt_phone,' ') alt_phone," +
          "nvl(facility_id,' ') facility_id,nvl(logon_id,' ') logon_id from personnel where personnel_id = " + userID;
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result.put("PERSONNEL_ID", rs.getString("personnel_id"));
        result.put("FIRST_NAME", rs.getString("first_name"));
        result.put("LAST_NAME", rs.getString("last_name"));
        result.put("MID_INITIAL", rs.getString("mid_initial"));
        result.put("EMAIL", rs.getString("email"));
        result.put("PHONE", rs.getString("phone"));
        result.put("PAGER", rs.getString("pager"));
        result.put("FAX", rs.getString("fax"));
        result.put("ALT_PHONE", rs.getString("alt_phone"));
        result.put("PREF_FACILITY_ID", rs.getString("facility_id"));
        result.put("LOGON_ID", rs.getString("logon_id"));
      }
      //NEXT GET USER PERMISSION
      //releasers, create new chem, administrator, waste manager and create report
      Vector releaserV = new Vector();
      Vector newChemV = new Vector();
      Vector adminV = new Vector();
      Vector wasteManagerV = new Vector();
      Vector createReportV = new Vector();
      Vector createCostReportV = new Vector();
      Hashtable createAppH = new Hashtable();
      Vector facIDV = new Vector();
      Vector facDescV = new Vector();
      Hashtable facActiveList = new Hashtable();

      query = "select a.facility_id, nvl(f.facility_name,f.facility_id) facility_name,f.active, nvl(b.user_group_id,' ') user_group_id," +
          "nvl(d.application,' ') application,fla.application_desc," +
          "decode(d.user_group_id,'Display','N','GenerateOrders','Y','N') create_mr from fac_pref a, facility f, user_group_member b, user_group_member_application d,fac_loc_app_with_all fla" +
          " where d.facility_id = fla.facility_id(+) and d.application = fla.application(+) and a.facility_id = d.facility_id(+) and d.user_group_id in ('GenerateOrders','DisplayApplication')" +
          " and a.personnel_id = d.personnel_id(+) and a.facility_id = f.facility_id and f.active in ('y','c') and a.facility_id = b.facility_id(+) " +
          " and a.personnel_id = b.personnel_id(+) and a.personnel_id = " + userID + " order by a.facility_id,fla.application_desc";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String lastFacID = "";
      while (rs.next()) {
        String currentFacID = rs.getString("facility_id");
        //first get distinct list of facilities and descriptions
        if (!facIDV.contains(currentFacID)) {
          facIDV.addElement(currentFacID);
          facDescV.addElement(rs.getString("facility_name"));
          facActiveList.put(currentFacID, rs.getString("active"));
        }
        //now get user group member
        String tmp = rs.getString("user_group_id");
        if ("Administrator".equalsIgnoreCase(tmp)) {
          if (!adminV.contains(currentFacID)) {
            adminV.addElement(currentFacID);
          }
        } else if ("CreateNewChemical".equalsIgnoreCase(tmp)) {
          if (!newChemV.contains(currentFacID)) {
            newChemV.addElement(currentFacID);
          }
        } else if ("WasteManager".equalsIgnoreCase(tmp)) {
          if (!wasteManagerV.contains(currentFacID)) {
            wasteManagerV.addElement(currentFacID);
          }
        } else if ("CreateReport".equalsIgnoreCase(tmp)) {
          if (!createReportV.contains(currentFacID)) {
            createReportV.addElement(currentFacID);
          }
        } else if ("CostReport".equalsIgnoreCase(tmp)) {
          if (!createCostReportV.contains(currentFacID)) {
            createCostReportV.addElement(currentFacID);
          }
        } else if ("Releaser".equalsIgnoreCase(tmp)) {
          if (!releaserV.contains(currentFacID)) {
            releaserV.addElement(currentFacID);
          }
        }
        //next get preferred work areas
        tmp = rs.getString("application");
        if (currentFacID.equalsIgnoreCase(lastFacID)) {
          if (tmp.length() > 0 && !" ".equalsIgnoreCase(tmp)) {
            Vector waPrefDataV = (Vector) createAppH.get(currentFacID);
            //check to see if work area alread in list
            boolean inList = false;
            for (int i = 0; i < waPrefDataV.size(); i++) {
              Object[] oa = (Object[]) waPrefDataV.elementAt(i);
              if (tmp.equalsIgnoreCase( (String) oa[0])) {
                inList = true;
                break;
              }
            }
            //if work area is in pref list then don't add it to main work area list
            if (inList) {
              continue;
            }
            //if not add to list
            Object[] oa = new Object[3];
            oa[0] = tmp;
            oa[1] = rs.getString("application_desc");
            oa[2] = new Boolean("Y".equalsIgnoreCase(rs.getString("create_mr")));
            waPrefDataV.addElement(oa);
            createAppH.put(currentFacID, waPrefDataV);
          }
        } else {
          Vector waPrefDataV = new Vector();
          if (tmp.length() > 0 && !" ".equalsIgnoreCase(tmp)) {
            Object[] oa = new Object[3];
            oa[0] = tmp;
            oa[1] = rs.getString("application_desc");
            oa[2] = new Boolean("Y".equalsIgnoreCase(rs.getString("create_mr")));
            waPrefDataV.addElement(oa);
          }
          createAppH.put(currentFacID, waPrefDataV);
        }
        lastFacID = currentFacID;
      } //end of while loop
      result.put("FACILITY_ID", facIDV);
      result.put("FACILITY_DESC", facDescV);
      result.put("WORK_AREA_PREF", createAppH);
      result.put("RELEASER", releaserV);
      result.put("ADMIN", adminV);
      result.put("CREATE_NEW_CHEM", newChemV);
      result.put("WASTE_MANAGER", wasteManagerV);
      result.put("CREATE_REPORT", createReportV);
      result.put("COST_REPORT", createCostReportV);
      result.put("FACILITY_ACTIVE_LIST", facActiveList);
      //next get list of work area for list of facilities
      Hashtable workAreaInfoH = new Hashtable(2);
      query = "select fla.facility_id,fla.application,fla.application_desc" +
          " from fac_loc_app fla where status = 'A' and facility_id in (";
      for (int i = 0; i < facIDV.size(); i++) {
        query += "'" + (String) facIDV.elementAt(i) + "',";
      }
      //replacing the last commas with close parathensis
      query = query.substring(0, query.length() - 1) + ") order by fla.facility_id,fla.application_desc";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      lastFacID = "";
      while (rs.next()) {
        String currentFacID = rs.getString("facility_id");
        String currentWA = rs.getString("application");
        //check to see if work area in work area pref
        if (createAppH.containsKey(currentFacID)) {
          boolean inList = false;
          Vector waPrefDataV = (Vector) createAppH.get(currentFacID);
          for (int i = 0; i < waPrefDataV.size(); i++) {
            Object[] oa = (Object[]) waPrefDataV.elementAt(i);
            if (currentWA.equalsIgnoreCase( (String) oa[0])) {
              inList = true;
              break;
            }
          }
          //if work area is in pref list then don't add it to main work area list
          if (inList) {
            continue;
          }
        }

        if (currentFacID.equalsIgnoreCase(lastFacID)) {
          Vector waInfoV = (Vector) workAreaInfoH.get(currentFacID);
          Object[] oa = new Object[2];
          oa[0] = currentWA;
          oa[1] = rs.getString("application_desc");
          waInfoV.addElement(oa);
          workAreaInfoH.put(currentFacID, waInfoV);
        } else {
          Vector waInfoV = new Vector();
          Object[] oa = new Object[2];
          oa[0] = currentWA;
          oa[1] = rs.getString("application_desc");
          waInfoV.addElement(oa);
          workAreaInfoH.put(currentFacID, waInfoV);
        }
        lastFacID = currentFacID;
      } //end of work area while
      result.put("WORK_AREA_INFO", workAreaInfoH);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  } //end of method

  public boolean update(Hashtable data) throws Exception {
    if (data.get("UID") == null) {
      return false;
    }
    try {
      Personnel p = new Personnel(db);
      p.setPersonnelId( ( (Integer) data.get("UID")).intValue());
      p.setMailLocation( (String) data.get("MAIL"));
      p.setFirstName( (String) data.get("FNAME"));
      p.setLastName( (String) data.get("LNAME"));
      p.setMidInitial( (String) data.get("MI"));
      p.setPhone( (String) data.get("PHONE"));
      p.setAltPhone( (String) data.get("ALTPHONE"));
      p.setPager( (String) data.get("PAGER"));
      p.setEmail( (String) data.get("EMAIL"));
      p.setShippingLocation( (String) data.get("SHIP"));
      p.setFax( (String) data.get("FAX"));
      p.setProxy( (String) data.get("PROXY"));
      p.setPort( (String) data.get("PORT"));
      p.setFacilityId( (String) data.get("FAC"));
      p.setUserLogon( (String) data.get("LOGONID"));
      if (!p.update()) {
        return false;
      }

      p.setReleasers( (Vector) data.get("REL"));
      if (!p.updateReleaserFacs()) {
        return false;
      }

      p.setPrefFacs( (Vector) data.get("FACIDS"));
      if (!p.updatePrefFacs()) {
        return false;
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;

    }
  }

  void updateCompanyApplicationLogonFromWebPage(String userID, String companyApplicationLogon) throws Exception {
    try {
      //first delete user records
      String query = "delete from company_application_logon where personnel_id = " + userID+ " and application = 'iProcurement'";
      db.doUpdate(query);
      //next insert
      query = "insert into company_application_logon (company_application_logon_id,application,personnel_id)" +
              " values ('"+companyApplicationLogon+"','iProcurement',"+userID+")";
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  void updateUserGroupMemberFromWebPage(String userID, String facID) throws Exception {
    try {
      //first delete user records
      String query = "delete from user_group_member where personnel_id = " + userID;
      db.doUpdate(query);
      //next insert
      query = "insert into user_group_member (personnel_id,facility_id,user_group_id) " +
          "(select " + userID + " personnel_id,facility_id,user_group_id from user_group" +
          " where user_group_id in ('GenerateOrders','CreateNewChemical','CreateReport','WasteManager') and facility_id = '" + facID + "')";
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  public boolean webInsert(Hashtable data) throws Exception {
    try {
      Hashtable inData = new Hashtable(13);
      inData.put("LOGON_ID",(String) data.get("LOGONID"));
      inData.put("EMAIL",(String) data.get("EMAIL"));
      inData.put("FIRST_NAME",(String) data.get("FNAME"));
      inData.put("LAST_NAME",(String) data.get("LNAME"));
      inData.put("MI",(String) data.get("MI"));
      inData.put("PHONE",(String) data.get("PHONE"));
      inData.put("ALT_PHONE",(String) data.get("ALTPHONE"));
      inData.put("PAGER",(String) data.get("PAGER"));
      inData.put("FAX",(String) data.get("FAX"));
      inData.put("PREF_FACILITY_ID",(String) data.get("FAC"));
      Personnel p = new Personnel(db);
      String[] result = p.createNewUser(inData);
      String msg = result[1];
      Integer IUID = new Integer(0);
      if ("OK".equalsIgnoreCase(msg)) {
        IUID = new Integer(result[0]);
      }else {
        return false;
      }

      int uid = IUID.intValue();
      p.setPersonnelId(uid);
      //add to fac_pref
      Vector vfac = new Vector();
      vfac.addElement( (String) data.get("FAC"));
      p.setPrefFacs(vfac);
      if (!p.updatePrefFacs()) {
        return false;
      }

      //company_application_logon
      updateCompanyApplicationLogonFromWebPage(IUID.toString(),(String) data.get("LOGONID"));
      //user_group
      updateUserGroupMemberFromWebPage(IUID.toString(), (String) data.get("FAC"));
      //insert record into user_group_member_application
      String generateFacList = "'" + (String) data.get("FAC") + "'";
      updateUserGroupMemberApplication(IUID.toString(), generateFacList, vfac);

      Password pw = new Password(db, (String) data.get("LOGONID"), "justAString");
      boolean pwChanged = false;
      pwChanged = pw.changePassword( (String) data.get("PASSWORD"));

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
  } //end of method

  public boolean webUpdate(Hashtable data) throws Exception {
    try {
      Hashtable inData = new Hashtable(13);
      inData.put("USER_ID",(Integer) data.get("UID"));
      inData.put("LOGON_ID",(String) data.get("LOGONID"));
      inData.put("EMAIL",(String) data.get("EMAIL"));
      inData.put("FIRST_NAME",(String) data.get("FNAME"));
      inData.put("LAST_NAME",(String) data.get("LNAME"));
      inData.put("MI",(String) data.get("MI"));
      inData.put("PHONE",(String) data.get("PHONE"));
      inData.put("ALT_PHONE",(String) data.get("ALTPHONE"));
      inData.put("PAGER",(String) data.get("PAGER"));
      inData.put("FAX",(String) data.get("FAX"));
      inData.put("PREF_FACILITY_ID",(String) data.get("FAC"));
      Personnel p = new Personnel(db);
      String result = p.updateUserPersonInfo(inData,false);
      if (!"OK".equalsIgnoreCase(result)) {
        return false;
      }
      //add to fac_pref
      Vector vfac = new Vector();
      vfac.addElement( (String) data.get("FAC"));
      p.setPrefFacs(vfac);
      if (!p.updatePrefFacs()) {
        return false;
      }
      // generate orders group
      Integer userID = (Integer) data.get("UID");
      //company_application_logon
      updateCompanyApplicationLogonFromWebPage(userID.toString(),(String) data.get("LOGONID"));
      //user_group
      updateUserGroupMemberFromWebPage(userID.toString(), (String) data.get("FAC"));
      //insert record into user_group_member_application
      String generateFacList = "'" + (String) data.get("FAC") + "'";
      updateUserGroupMemberApplication(userID.toString(), generateFacList, vfac);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
  } //end of method

} //end of class
