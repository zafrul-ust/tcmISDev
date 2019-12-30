package radian.tcmis.server7.share.factory;

import java.util.Vector;
import java.util.Hashtable;
import java.sql.*;
import java.lang.String;
import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/******************************************************************************
 * CLASSNAME: PartNumberCommentFactory <br>
 * @version: 1.0, Mar 12, 2004 <br>
 *****************************************************************************/


public class AlternateApproverFactory {
  protected TcmISDBModel db;

  public AlternateApproverFactory(){
  }

  public AlternateApproverFactory(TcmISDBModel db) {
    this.db = db;
  }

  public void processAlternateApproverList(Hashtable inData) throws Exception {
    try {
      Integer approverID = (Integer)inData.get("APPROVER_ID");
      String facilityID = (String)inData.get("FACILITY_ID");
      Vector dataV = (Vector)inData.get("ALTERNATE_APPROVER_DATA");
      Vector newAltApproverIDV = new Vector(dataV.size());
      for (int i = 0; i < dataV.size(); i++) {
        Hashtable h = (Hashtable)dataV.elementAt(i);
        Boolean deleteFlag = (Boolean)h.get("DELETE");
        if (deleteFlag.booleanValue()) {
          continue;
        }
        newAltApproverIDV.addElement((Integer)h.get("ALTERNATE_APPROVER_ID"));
      }
      //first remove all alternate approvers for approver
      deleteAlternateApprover(approverID,facilityID);
      //now insert alternate approvers for approver
      for (int j = 0; j < newAltApproverIDV.size(); j++) {
        addAlternateApprover(approverID,facilityID,(Integer)newAltApproverIDV.elementAt(j));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  void deleteAlternateApprover(Integer approverID, String facilityID) throws Exception {
    try {
      String query = "delete from finance_alternate_approver where facility_id = '"+facilityID+"' and approver = "+approverID.intValue();
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  void addAlternateApprover(Integer approverID, String facilityID, Integer alternateApprover) throws Exception {
    try {
      String query = "insert into finance_alternate_approver(facility_id,approver,alternate_approver)"+
                     " values('"+facilityID+"',"+approverID.intValue()+","+alternateApprover.intValue()+")";
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public Hashtable getData(Hashtable inData) throws Exception {
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    result.put("DEFAULT_TABLE_SIZE",new Integer(51));
    try {
      //get alternate approvers
      Integer approverID = (Integer)inData.get("APPROVER_ID");
      String facilityID = (String)inData.get("FACILITY_ID");
      String where = "";
      getAlternateApprover(result,facilityID,approverID);
      //get catalog desc
      where = "where facility_id = '"+facilityID+"'";
      this.getFacilityName(result,where);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return result;
  } //end of method

  void getAlternateApprover(Hashtable result, String facilityID, Integer approverID) throws Exception {
    Vector dataV = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select faa.alternate_approver,nvl(p.last_name,' ') last_name,nvl(p.first_name,' ') first_name,nvl(phone,' ') phone,nvl(email,' ') email"+
                     " from finance_alternate_approver faa, personnel p where faa.alternate_approver = p.personnel_id and"+
                     " faa.facility_id = '"+facilityID+"' and faa.approver = "+approverID.intValue()+" order by p.last_name";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Hashtable h = new Hashtable(7);
        h.put("LAST_NAME",rs.getString("last_name"));
        h.put("FIRST_NAME",rs.getString("first_name"));
        h.put("PHONE",rs.getString("phone"));
        h.put("EMAIL",rs.getString("email"));
        h.put("DELETE",new Boolean(false));
        h.put("ALTERNATE_APPROVER_ID",new Integer(rs.getInt("alternate_approver")));
        dataV.addElement(h);
      }
      result.put("ALTERNATE_APPROVER_DATA",dataV);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
  } //end of method

  void getFacilityName(Hashtable result, String where) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select nvl(facility_name,facility_id) facility_name from facility "+where;
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        result.put("FACILITY_NAME",rs.getString("facility_name"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
  } //end of method

  void getCatalogDesc(Hashtable result, String where) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select nvl(catalog_desc,catalog_id) catalog_desc from catalog "+where;
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        result.put("CATALOG_DESC",rs.getString("catalog_desc"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
  } //end of method

  void getUserName(Hashtable result, Integer userID) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select fx_customer_personnel_name("+userID.intValue()+") full_name from dual";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        result.put("USER_NAME",rs.getString("full_name"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
  } //end of method

} //end of class






























