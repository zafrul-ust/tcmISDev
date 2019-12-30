package radian.tcmis.server7.share.factory;

import java.util.Vector;
import java.util.Hashtable;
import java.sql.*;
import java.lang.String;
import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.both1.helpers.*;

/******************************************************************************
 * CLASSNAME: WorkAreaCommentFactory <br>
 * @version: 1.0, Mar 12, 2004 <br>
 *****************************************************************************/


public class WorkAreaCommentFactory {
  protected TcmISDBModel db;

  public WorkAreaCommentFactory(){
  }

  public WorkAreaCommentFactory(TcmISDBModel db) {
    this.db = db;
  }

  public void processWorkAreaComments(Hashtable inData) throws Exception {
    try {
      //get comments
      Integer userID = (Integer)inData.get("USER_ID");
      String facilityID = (String)inData.get("FACILITY_ID");
      String workArea = (String)inData.get("WORK_AREA");
      String catalogID = (String)inData.get("CATALOG_ID");
		String catalogCompanyId = (String)inData.get("CATALOG_COMPANY_ID");
		String catPartNo = (String)inData.get("CAT_PART_NO");
      String partGroupNo = (String)inData.get("PART_GROUP_NO");
      Vector dataV = (Vector)inData.get("WORK_AREA_COMMENTS_DATA");
      for (int i = 0; i < dataV.size(); i++) {
        Hashtable h = (Hashtable)dataV.elementAt(i);
        //go to next item if user has not change current comments
        String status = (String)h.get("STATUS");
        if ("OLD".equalsIgnoreCase(status)) {
          continue;
        }
        //decide whether the comment is new or updated or delete
        String commentID = (String)h.get("COMMENT_ID");
        String comments = (String)h.get("COMMENTS");
        if ("NEW".equalsIgnoreCase(status)) {
          createNewComments(userID,facilityID,workArea,catalogID,catPartNo,partGroupNo,comments,catalogCompanyId);
        }else if ("UPDATE".equalsIgnoreCase(status)){
          updateComments(comments,commentID);
        }else if ("DELETE".equalsIgnoreCase(status)) {
          deleteComments(commentID);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  void deleteComments(String commentID) throws Exception {
    try {
      String query = "delete from fac_loc_app_part_comment where comment_id = "+commentID;
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  void updateComments(String comments, String commentID) throws Exception {
    try {
      String query = "update fac_loc_app_part_comment set comments = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(comments)+"'"+
                   ",date_entered = sysdate where comment_id = "+commentID;
		db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  void createNewComments(Integer userID, String facilityID, String workArea, String catalogID, String catPartNo, String partGroupNo, String comments, String catalogCompanyId) throws Exception {
    try {
      String query = "insert into fac_loc_app_part_comment(comment_id,facility_id,application,catalog_id,catalog_company_id,cat_part_no,part_group_no,date_entered,entered_by,comments)"+
                     " select fac_app_part_comment_seq.nextval,'"+facilityID+"','"+workArea+"','"+catalogID+"','"+catalogCompanyId+"','"+catPartNo+"',"+partGroupNo+
                     ",sysdate,"+userID.intValue()+",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(comments)+"' from dual";
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
      //get comments
      Integer userID = (Integer)inData.get("USER_ID");
      String facilityID = (String)inData.get("FACILITY_ID");
      String workArea = (String)inData.get("WORK_AREA");
      String catalogID = (String)inData.get("CATALOG_ID");
		String catalogCompanyId = (String)inData.get("CATALOG_COMPANY_ID");
		String catPartNo = (String)inData.get("CAT_PART_NO");
      String partGroupNo = (String)inData.get("PART_GROUP_NO");
      String where = "where facility_id = '"+facilityID+"' and application = '"+workArea+"' and catalog_company_id = '"+catalogCompanyId+"'"+
                     " and catalog_id = '"+catalogID+"' and cat_part_no = '"+catPartNo+"' and part_group_no = "+partGroupNo;
      getComments(result,where);
      //get facility name
      where = "where facility_id = '"+facilityID+"'";
      getFacilityName(result,where);
      //get catalog desc
      where = "where catalog_id = '"+catalogID+"' and company_id = '"+catalogCompanyId+"'";
      getCatalogDesc(result,where);
      //get user name
      getUserName(result,userID);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return result;
  } //end of method

  void getComments(Hashtable result, String where) throws Exception {
    Vector dataV = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select comment_id,comments,fx_customer_personnel_name(entered_by) full_name,to_char(date_entered,'MM/dd/yyyy') date_entered,entered_by"+
                     " from fac_loc_app_part_comment "+where;
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Hashtable h = new Hashtable(7);
        h.put("COMMENT_ID",rs.getString("comment_id"));
        h.put("COMMENTS",rs.getString("comments"));
        h.put("BY",rs.getString("full_name"));
        h.put("DATE",rs.getString("date_entered"));
        h.put("DELETE",new Boolean(false));
        h.put("STATUS","OLD");
        h.put("USER_ID",new Integer(rs.getInt("entered_by")));
        dataV.addElement(h);
      }
      result.put("WORK_AREAS_COMMENT_DATA",dataV);
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






























