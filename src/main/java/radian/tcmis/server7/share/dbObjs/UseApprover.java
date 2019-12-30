package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;
import java.awt.Color;

public class UseApprover {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected String facilityID;
   protected String application;
   protected String personnelID;

   public UseApprover(TcmISDBModel db){
      this.db = db;
   }
   public UseApprover(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public String getFacilityId(){return facilityID;}
   public String getApplication(){return application;}
   public String getPersonnelId(){return personnelID;}

   // set Methods
   public void setFacilityId(String s){facilityID = s;}
   public void setApplication(String s){application = s;}
   public void setPersonnelId(String s){personnelID = s;}

   public Hashtable loadScreenData(String userID, Vector facIDs) throws Exception {
    Hashtable result = new Hashtable();
    String facilityIds = "";
    for (int i = 0; i < facIDs.size(); i++) {
      facilityIds += "'" +facIDs.elementAt(i).toString() + "',";
    }
    //removing the last commas
    if (facilityIds.length() > 0) {
      facilityIds = facilityIds.substring(0,facilityIds.length()-1);
    }

    String query = "select a.facility_id,a.application,a.application_desc app_desc from fac_loc_app a,over_limit_use_approver l";
    query += " where a.facility_id = l.facility_id (+) and a.application = l.application (+) and l.application is null and";
  	query += " l.personnel_id (+) = "+userID+ " and";
	  query += " a.facility_id in ("+facilityIds+")";
    query += " order by facility_id,application_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        String fac = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
        if (result.containsKey(fac)) {
          Vector myApp = (Vector)result.get(fac);
          String appDesc = BothHelpObjs.makeBlankFromNull(rs.getString("app_desc"));
          myApp.addElement(appDesc);
          result.put(fac,myApp);
        }else {
          Vector myApp = new Vector();
          String appDesc = BothHelpObjs.makeBlankFromNull(rs.getString("app_desc"));
          myApp.addElement(appDesc);
          result.put(fac,myApp);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally{
      dbrs.close();
    }

    Hashtable h = new Hashtable();
    //border color and inset
    h.put("COLOR",new Color(255,255,255));
    h.put("INSET_TOP",new Integer(0));
    h.put("INSET_LEFT",new Integer(1));
    h.put("INSET_BOTTOM",new Integer(1));
    h.put("INSET_RIGHT",new Integer(1));
    h.put("INSET_ALIGN",new Integer(2));    //1- right, 2- left, 3- center
    //font and foreground and background color for columns heading
    h.put("FONT_NAME","Dialog");
    h.put("FONT_STYLE",new Integer(0));
    h.put("FONT_SIZE",new Integer(10));
    h.put("FOREGROUND",new Color(255,255,255));
    h.put("BACKGROUND",new Color(0,0,66));
    h.put("COL_WIDTH",new Integer(200));
    String[] leftTableHeader = {"Work Areas"};
    h.put("LEFT_TABLE_HEADER",leftTableHeader);
    String[] rightTableHeader = {"Approval Authority"};
    h.put("RIGHT_TABLE_HEADER",rightTableHeader);
    result.put("TABLE_STYLE",h);

    //this is where I set hashtable of vector of work areas where user is an use approver
    //Hashtable useApproverH = new Hashtable();
    //result.put("USE_APPROVER",useApproverH);
    result.put("USE_APPROVER",getUseApprovers(userID));

    return result;
   }

   public static boolean needMyUseApproval(TcmISDBModel db, int rNum, int userID) throws Exception {
    boolean result = false;
    String query = "select count(*) from request_line_item a, over_limit_use_approver b, purchase_request c";
    query += " where a.application = decode(b.application,'All',a.application,b.application) and";
    query += " c.facility_id = b.facility_id and";
    query += " c.pr_number = a.pr_number and";
    query += " lower(a.use_approval_status) = 'pending' and a.pr_number = "+rNum;
    query += " and b.personnel_id = "+userID;
    try {
      //System.out.println("\n\n-------- here it is: "+query);
      int count = DbHelpers.countQuery(db,query);
      result = count > 0;
    }catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
   }

   //return a list of approvers info who can approve my usage level
   public Hashtable getUseApproverInfoPerLine(int reqNum, String lineItem, String status) throws Exception {
    if (status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("rejected")) {
      return getApprovedRejectedUseInfo(reqNum,lineItem);
    }else {
      return getPendingUseApprovers(reqNum,lineItem);
    }
   }
   public Hashtable getApprovedRejectedUseInfo(int reqNum, String lineItem) throws Exception {
    Hashtable result = new Hashtable();
    String query = "select a.use_approval_comment,b.personnel_id,b.email,b.phone,b.last_name || ', ' ||b.first_name full_name from request_line_item a, personnel b";
    query += " where b.personnel_id = a.use_approver and";
    query += " a.pr_number = "+reqNum+ " and";
    query += " a.line_item = '"+lineItem+"'";
    query += " order by b.last_name";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;
      while (rs.next()){
        result.put("NAME",BothHelpObjs.makeBlankFromNull(rs.getString("full_name")));
        result.put("PHONE",BothHelpObjs.makeBlankFromNull(rs.getString("phone")));
        result.put("EMAIL",BothHelpObjs.makeBlankFromNull(rs.getString("email")));
        result.put("COMMENT",BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_comment")));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally{
      dbrs.close();
    }
    return result;
   }

   public Hashtable getPendingUseApprovers(int reqNum, String lineItem) throws Exception {
    Hashtable result = new Hashtable(10);
    Vector dataV = new Vector();
    String comment = "";
    String[] colHeads = {"Approver","Phone","E-mail"};
    int[] colWidths = {120,100,180};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};

    String temp = "select count(*) from request_line_item a, purchase_request b, over_limit_use_approver c";
    temp += " where a.pr_number = b.pr_number and";
    temp += " b.facility_id = c.facility_id and";
    temp += " a.application = decode(c.application,'All',a.application,c.application) and";
    temp += " a.pr_number = "+reqNum;
    temp += " and a.line_item = '"+lineItem+"'";
    int count = 0;
    try {
      count = DbHelpers.countQuery(db,temp);
    }catch (Exception ee) {
      ee.printStackTrace();
    }
    String query = "";
    if (count > 0) {
      query = "select r.use_approval_comment,a.personnel_id,b.email,b.phone,b.last_name || ', ' ||b.first_name full_name from over_limit_use_approver a,personnel b, request_line_item r, purchase_request p";
      query += " where p.pr_number = r.pr_number and";
      query += " a.personnel_id = b.personnel_id and";
      query += " a.facility_id = p.facility_id and";
      query += " decode(a.application,'All',r.application,a.application) = r.application and";
      query += " r.pr_number = "+reqNum+ " and";
      query += " r.line_item = '"+lineItem+"'";
    }else {
      colHeads = new String[]{"Administrator","Phone","E-mail"};
      query = "select b.personnel_id,b.email,b.phone,b.last_name || ', ' ||b.first_name full_name from user_group_member a, personnel b, request_line_item r, purchase_request p";
      query += " where p.pr_number = r.pr_number and";
      query += " a.personnel_id = b.personnel_id and";
      query += " lower(a.user_group_id) = 'administrator' and";
      query += " a.facility_id = p.facility_id and";
      query += " r.pr_number = "+reqNum+ " and";
      query += " r.line_item = '"+lineItem+"'";
      comment = "There have been no use approvers set for this work area. Please call your adminstrators.";
    }
    query += " order by b.last_name";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      //count = 0;
      while (rs.next()){
        Object[] oa = new Object[colHeads.length];
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("full_name"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("phone"));
        oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("email"));
        dataV.addElement(oa);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally{
      dbrs.close();
    }
    result.put("TABLE_HEADERS",colHeads);
    result.put("TABLE_WIDTHS",colWidths);
    result.put("TABLE_TYPES",colTypes);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    result.put("COMMENT",comment);
    return result;
   }
   //return a list of approvers info who can approve my usage level
   public Vector getUseApproverInfo(int reqNum) throws Exception {
    Vector result = new Vector();
    String query = "select distinct a.personnel_id,b.email,b.phone,b.last_name || ',' ||b.first_name full_name from over_limit_use_approver a,personnel b, request_line_item r, purchase_request p";
    query += " where p.pr_number = r.pr_number and";
    query += " lower(r.use_approval_status) = 'pending' and";
    query += " a.personnel_id = b.personnel_id and";
    query += " a.facility_id = p.facility_id and";
    query += " decode(a.application,'All',r.application,a.application) = r.application and";
    query += " r.pr_number = "+reqNum;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Hashtable h = new Hashtable();
        h.put("EMAIL",BothHelpObjs.makeBlankFromNull(rs.getString("email")));
        h.put("PHONE",BothHelpObjs.makeBlankFromNull(rs.getString("phone")));
        h.put("FULL_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("full_name")));
        h.put("APPROVER_ID",BothHelpObjs.makeBlankFromNull(rs.getString("personnel_id")));
        result.addElement(h);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally{
      dbrs.close();
    }
    return result;
   }
   //return true if the requestor is one of the use approver for the given facility, application
   public boolean requesterIsUseApprover(String userID, String facID, String app) throws Exception {
    boolean result = false;
    String query = "select count(*) from over_limit_use_approver where";
    query += " facility_id = '"+facID+"' and application in ('All','"+app+"') and personnel_id = "+userID;
    try {
      int count = DbHelpers.countQuery(db,query);
      result = count > 0;
    }catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
   }  //end of method
   //return true if the requestor is one of the use approver for the given facility, application
   public boolean requesterIsUseApprover(String userID, String facID, int prNum, int lineItem) throws Exception {
    boolean result = false;
    String query = "select count(*) from over_limit_use_approver a, request_line_item b where"+
                   " a.facility_id = '"+facID+"' and decode(a.application,'All',b.application,a.application) = b.application and a.personnel_id = "+userID+
                   " and b.pr_number = "+prNum+" and b.line_item = '"+lineItem+"'";
    try {
      int count = DbHelpers.countQuery(db,query);
      result = count > 0;
    }catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
   }

   public Hashtable getUseApprovers(String userID) throws Exception {
    Hashtable result = new Hashtable();
    String query = "select distinct fla.facility_id,fla.application_desc  app_desc from over_limit_use_approver o, fac_loc_app fla";
    query += " where o.facility_id = fla.facility_id and decode(o.application,'All',fla.application,o.application) = fla.application and o.personnel_id = "+userID;
    query += " order by fla.facility_id,fla.application_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        String fac = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
        if (result.containsKey(fac)) {
          Vector myApp = (Vector)result.get(fac);
          String appDesc = BothHelpObjs.makeBlankFromNull(rs.getString("app_desc"));
          myApp.addElement(appDesc);
          result.put(fac,myApp);
        }else {
          Vector myApp = new Vector();
          String appDesc = BothHelpObjs.makeBlankFromNull(rs.getString("app_desc"));
          myApp.addElement(appDesc);
          result.put(fac,myApp);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally{
      dbrs.close();
    }
    return result;
   }

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update over_limit_use_approver set " + col + "=");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.intValue();
         break;
       case DATE:
         if (val.equals("nowDate")){
           query = query + " SYSDATE";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       case NULLVAL:
         query = query + null;
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query += " where facility_id = '"+getFacilityId()+"'";
     query += " and application = '"+getApplication()+"'";
     query += " and personnel_id = "+getPersonnelId();
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }


  public void delete() throws Exception {
    String query = "delete over_limit_use_approver where personnel_id = "+getPersonnelId();
    DBResultSet dbrs = null;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      }
      return;
   }

   public void load()throws Exception{
     String query = "select * from over_limit_use_approver where facility_id = '"+getFacilityId()+"'";
     query += " and application = '"+getApplication()+"'";
     query += " and personnel_id = "+getPersonnelId();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
         setApplication(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
         setPersonnelId(BothHelpObjs.makeBlankFromNull(rs.getString("personnel_id")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public void createUseApprover(String requesterId, String facID, String appDesc)throws Exception{
     try{
       String query = "insert into over_limit_use_approver ";
       query = query + "(facility_id,application,personnel_id) ";
       query = query + " select fla.facility_id,application,"+requesterId+" from fac_loc_app fla where facility_id = '"+facID+"' and application_desc = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(appDesc)+"'";
       //System.out.println("\n\n------- here is it: "+query);
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }  //end of method
}    //end of class
