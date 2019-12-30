package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.io.*;
import java.sql.*;

public class MaterialMatrixDBObj {
  protected TcmISDBModel db;

  public static final int STRING = 0;
  public static final int DATE = 2;
  public static final int INT = 1;
  public static final int NULLVAL = 3;
  String[] colHeadL = {
      "Base Fields"
  };

  String[] colHead = new String[] {
      "Item ID",
      "Part No", "Description", "Grade", "Manufacturer"
  };
  public static final int[] colWidths = {
      75, 140, 200, 65, 100
  };
  public static final int[] colTypes = {
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING
  };

  String endYear;
  String endMonth;
  String beginYear;
  String beginMonth;
  String partNo;
  String partOperator;
  String type;
  String application;
  String workarea;
  String facilityID;
  String method;
  String formater;
  Vector reportFields;
  Vector reportFields1;
  Vector ReportData = new Vector();

  public MaterialMatrixDBObj(TcmISDBModel db) {
    this.db = db;
  }

  public MaterialMatrixDBObj() {
  }

  public void setDb(TcmISDBModel db) {
    this.db = db;
  }

  //set method
  public void setEndYear(String s) {
    this.endYear = s;
  }

  public void setEndMonth(String s) {
    this.endMonth = s;
  }

  public void setBeginYear(String s) {
    this.beginYear = s;
  }

  public void setBeginMonth(String s) {
    this.beginMonth = s;
  }

  public void setPartNo(String s) {
    this.partNo = s;
  }

  public void setPartOperator(String s) {
    this.partOperator = s;
  }

  public void setType(String s) {
    this.type = s;
  }

  public void setWorkArea(String s) {
    this.workarea = s;
  }

  public void setApplication(String s) {
    this.application = s;
  }

  public void setFacilityID(String s) {
    this.facilityID = s;
  }

  public void setMethod(String s) {
    this.method = s;
  }

  //get method
  public String getEndYear() {
    return this.endYear;
  }

  public String getEndMonth() {
    return this.endMonth;
  }

  public String getBeginYear() {
    return this.beginYear;
  }

  public String getBeginMonth() {
    return this.beginMonth;
  }

  public String getPartNo() {
    return this.partNo;
  }

  public String getPartOperator() {
    return this.partOperator;
  }

  public String getType() {
    return this.type;
  }

  public String getWorkArea() {
    return this.workarea;
  }

  public String getApplication() {
    return this.application;
  }

  public String getFacilityID() {
    return this.facilityID;
  }

  public Hashtable searchPartNo(String facilityID, String searchT) throws Exception {
    Hashtable dataH = new Hashtable(4);
    Vector dataV = new Vector();
    String query = "select item_id,fac_item_id,material_desc,grade,mfg_desc, item_id || fac_item_id || material_desc || grade || mfg_desc   search_string from catalog_snapshot";
    query += " where facility_id = '" + facilityID + "'";
    if (searchT.length() > 0) {
      query += " and lower(item_id || fac_item_id || material_desc || grade || mfg_desc) like lower('%" + searchT + "%')";
    }
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Object[] oa = new Object[colHead.length];
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("fac_item_id"));
        oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
        oa[3] = BothHelpObjs.makeBlankFromNull(rs.getString("grade")); ;
        oa[4] = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc")); ;
        dataV.addElement(oa);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    dataH.put("TABLE_HEADERS", colHead);
    dataH.put("TABLE_WIDTHS", colWidths);
    dataH.put("TABLE_TYPES", colTypes);
    dataV.trimToSize();
    dataH.put("TABLE_DATA", dataV);
    return dataH;
  }

  public Hashtable getCatalog() throws Exception {
    Hashtable h = new Hashtable();
    Vector vID = new Vector();
    Vector vDesc = new Vector();
    String query = "select catalog_id,catalog_desc from catalog";
    query += " order by catalog_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        vID.addElement(rs.getString("catalog_id"));
        vDesc.addElement(rs.getString("catalog_desc"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }

    if (vID.size() > 1) {
      vID.insertElementAt("Select a Catalog", 0);
      vDesc.insertElementAt("Select a Catalog", 0);
    }
    h.put("CATALOG_ID", vID);
    h.put("CATALOG_DESC", vDesc);
    return h;
  }

  String getPartNumberSQL(String uID, String var1, String var2, String var4, boolean facApp, boolean onlyfac,String reportingEntity) {
    String query = "";
    if (facApp) {
      //NEED to do logic here
      if (facilityID.startsWith("All")) {
        if (onlyfac) {
          query += "select " + var2 + " from facility aa,user_group_member c where";
        } else {
          query += "select " + var2 + " from fac_loc_app aa,user_group_member c where";
        }
      } else {
        if (onlyfac) {
          query += "select " + var2 + " from facility aa where";
        } else {
          query += "select " + var2 + " from fac_loc_app aa where";
        }
      }
    } else { //everything else
      query += "select " + var2 + " from ";

      String tmpApp = " ";
      if (application.length() > 0 && !application.startsWith("All")) {
        tmpApp = application;
      }
      query += " (select cf.facility_id,fil.cat_part_no fac_part_no,fil.item_id,fi.part_description from catalog_part_item_group fil, catalog_facility cf, fac_item fi" +
          " where fil.catalog_id = fi.facility_id and fil.cat_part_no = fi.fac_part_no and";
      if (partNo.length() > 0) {
        if ("is".equalsIgnoreCase(partOperator)) {
          query += " fi.fac_part_no = '" + partNo + "' and";
        } else {
          query += " lower(fi.fac_part_no) like '%" + partNo.toLowerCase() + "%' and";
        }
      }
      if ("CAL".equalsIgnoreCase(db.getClient())) {
          if ("United Reporting".equals(reportingEntity)) {
            query += " fi.facility_id = 'United' and";
          }else if ("Continental Reporting".equals(reportingEntity)){
            query += " fi.facility_id = 'CAL' and";
          }
          if (facilityID.length() > 0 && !facilityID.startsWith("All")) {
            query += " cf.facility_id = '"+facilityID+"' and";
          }
      }

      if (facilityID.startsWith("All")) {
        query += " fil.catalog_id = cf.catalog_id and fil.status in ('A', 'D')) aa, user_group_member c where";
      } else {
        query += " fil.catalog_id = cf.catalog_id and fil.status in ('A', 'D')) aa where";
      }



    } //end of else 5-18-01

    if (facilityID.length() > 0 && !facilityID.startsWith("All")) {
      //if facility is facility group
      boolean temp = false;
      try {
        temp = HelpObjs.countQuery(db, "select count(*) from facility where facility_group_id = '" + facilityID + "'") > 0;
      } catch (Exception e) {
        temp = false;
      }
      if (temp) {
        query += " aa.facility_id in (select ugm.facility_id from user_group_member ugm, facility fgm" +
            " where ugm.user_group_id = 'CreateReport' and ugm.personnel_id = " + uID +
            " and ugm.facility_id = fgm.facility_id and fgm.facility_group_id = '" + facilityID + "') and";
      } else {
        query += " aa.facility_id = '" + facilityID + "' and";
      }
    }

    if (facilityID.startsWith("All")) {
      query += " aa.facility_id = c.facility_id and c.user_group_id = 'CreateReport' and c.personnel_id = " + uID + " and";
    }

    //removing the last and
    query = query.substring(0, query.length() - 3);
    query += " group by " + var4;
    return query;
  } //end of method

  String getPartUsedSQL(String uID, String var1, String var2, String var4, boolean facApp, boolean onlyfac, String reportingEntity) {
    String query = "";
    query += "select " + var2 + " from issue_status_detail aa";
    if (facilityID.startsWith("All")) {
      query += " ,user_group_member c";
    }
    // begin date
    Integer begD = new Integer(199701);
    try {
      Integer bm = new Integer(beginMonth);
      bm = new Integer(bm.intValue() + 1);
      String sm = new String(bm.toString());
      if (sm.length() < 2) {
        sm = "0" + sm;
      }
      Integer by = new Integer(beginYear);
      begD = new Integer(by.toString() + sm);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // end date
    Integer endD = new Integer(209012);
    try {
      Integer em = new Integer(endMonth);
      //add 2 to end month so I can use < (less than)
      em = new Integer(em.intValue() + 2);
      Integer ey = new Integer(endYear);
      if (em.intValue() > 12) {
        em = new Integer(1);
        ey = new Integer(ey.intValue() + 1);
      }
      String esm = new String(em.toString());
      if (esm.length() < 2) {
        esm = "0" + esm;
      }
      endD = new Integer(ey.toString() + esm);
    } catch (Exception e) {
      e.printStackTrace();
    }
    query += " where aa.date_shipped >= to_date('" + begD.toString() + "','YYYYMM') and aa.date_shipped < to_date('" + endD.toString() + "','YYYYMM') and";
    if (facilityID.length() > 0 && !facilityID.startsWith("All")) {
      //if facility is facility group
      boolean temp = false;
      try {
        temp = HelpObjs.countQuery(db, "select count(*) from facility where facility_group_id = '" + facilityID + "'") > 0;
      } catch (Exception e) {
        temp = false;
      }
      if (temp) {
        query += " aa.facility_id in (select ugm.facility_id from user_group_member ugm, facility fgm" +
            " where ugm.user_group_id = 'CreateReport' and ugm.personnel_id = " + uID +
            " and ugm.facility_id = fgm.facility_id and fgm.facility_group_id = '" + facilityID + "') and";
      } else {
        query += " aa.facility_id = '" + facilityID + "' and";
      }
    }
    if (facilityID.startsWith("All")) {
      query += " aa.facility_id = c.facility_id and c.user_group_id = 'CreateReport' and c.personnel_id = " + uID + " and";
    }

    if (!BothHelpObjs.isBlankString(reportingEntity)) {
      if (!"No Reporting Entity".equalsIgnoreCase(reportingEntity) &&
          !"All Reporting Entities".equalsIgnoreCase(reportingEntity)) {
            query += " (aa.facility_id,aa.application) in (select facility_id,application from fac_app_report_view where personnel_id = "+uID+
                     " and reporting_entity_id = '"+reportingEntity+"') and";
      }
    }

    if (application.length() > 0 && !application.startsWith("All")) {
      if (application.equalsIgnoreCase("My Work Areas")) {
        if (!BothHelpObjs.isBlankString(facilityID) && !facilityID.startsWith("All")) {
          query += " aa.application in (select application from my_workarea_facility_view ugma where ugma.personnel_id = " + uID + " and ugma.facility_id = '" + facilityID + "') and";
        } else {
          query += " aa.application in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
              " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = " + uID + ") and";
        }
      } else {
        query += " aa.application = '" + application + "' and";
      }
    }
    //removing the last and
    query = query.substring(0, query.length() - 3);
    query += "group by " + var4 + "";

    return query;
  } //end of method

  String getApprovedPartSQL(String uID, String var1, String var2, String var4, boolean facApp, boolean onlyfac, String reportingEntity) {
    String query = "";
    query = "select " + var2 + " from ";
    query += " (select ua.facility_id,ua.cat_part_no fac_part_no,cpig.item_id,ua.application,fi.part_description";
    query += ",row_number () over ( partition by ua.facility_id,ua.cat_part_no,cpig.item_id,ua.approval_application order by 1) row_rank";
    query += " from catalog_part_item_group cpig, cat_part_approval_view ua, fac_item fi ";
    if (facilityID.startsWith("All")) {
      query += " , user_group_member d";
    }
    query += " where cpig.status in ('A','D') and cpig.catalog_id = ua.catalog_id and cpig.cat_part_no = ua.cat_part_no and"+
             " cpig.part_group_no = ua.part_group_no and" +
             " cpig.catalog_id = fi.facility_id and cpig.cat_part_no = fi.fac_part_no and" +
             " ua.APPROVAL_STATUS in ('approved','Super Approved') and";
    if (facilityID.length() > 0 && !facilityID.startsWith("All")) {
      //if facility is facility group
      boolean temp = false;
      try {
        temp = HelpObjs.countQuery(db, "select count(*) from facility where facility_group_id = '" + facilityID + "'") > 0;
      } catch (Exception e) {
        temp = false;
      }
      if (temp) {
        query += " ua.facility_id in (select ugm.facility_id from user_group_member ugm, facility fgm" +
            " where ugm.user_group_id = 'CreateReport' and ugm.personnel_id = " + uID +
            " and ugm.facility_id = fgm.facility_id and fgm.facility_group_id = '" + facilityID + "') and";
      } else {
        query += " ua.facility_id = '" + facilityID + "' and";
      }
    }

    if (facilityID.startsWith("All")) {
      query += " ua.facility_id = d.facility_id and d.user_group_id = 'CreateReport' and d.personnel_id = " + uID + " and";
    }

    if (!BothHelpObjs.isBlankString(reportingEntity)) {
      if (!"No Reporting Entity".equalsIgnoreCase(reportingEntity) &&
          !"All Reporting Entities".equalsIgnoreCase(reportingEntity)) {
        query += " (ua.facility_id,ua.application) in (select facility_id,application from fac_app_report_view where personnel_id = "+uID+
                 " and reporting_entity_id = '"+reportingEntity+"') and";
      }
    }

    if (application.length() > 0 && !application.startsWith("All")) {
      if (application.equalsIgnoreCase("My Work Areas")) {
        if (!BothHelpObjs.isBlankString(facilityID) && !facilityID.startsWith("All")) {
          query += " ua.application in (select application from my_workarea_facility_view ugma where ugma.personnel_id = " + uID + " and ugma.facility_id = '" + facilityID + "' union all select 'All' application from dual) and";
        } else {
          query += " ua.application in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
              " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = " + uID + " union all select 'All' application from dual) and";
        }
      } else {
        query += " ua.application in ('" + application + "','All') and";
      }
    }
    //removing the last and
    query = query.substring(0, query.length() - 3);
    query += ") aa where row_rank = 1";
    return query;
  } //end of method

  String getApprovedPartSQLOld(String uID, String var1, String var2, String var4, boolean facApp, boolean onlyfac, String reportingEntity) {
    String query = "";
    query = "select " + var2 + " from ";
    query += " (select ua.facility_id,ua.cat_part_no fac_part_no,cpig.item_id,ua.application,fi.part_description from catalog_part_item_group cpig, cat_part_approval_view ua, fac_item fi ";
    if (facilityID.startsWith("All")) {
      query += " , user_group_member d";
    }
    query += " where cpig.status in ('A','D') and cpig.catalog_id = ua.catalog_id and cpig.cat_part_no = ua.cat_part_no and cpig.part_group_no = ua.part_group_no and" +
        " cpig.catalog_id = fi.facility_id and cpig.cat_part_no = fi.fac_part_no and" +
        " ua.APPROVAL_STATUS in ('approved','Super Approved') and";
    if (facilityID.length() > 0 && !facilityID.startsWith("All")) {
      //if facility is facility group
      boolean temp = false;
      try {
        temp = HelpObjs.countQuery(db, "select count(*) from facility where facility_group_id = '" + facilityID + "'") > 0;
      } catch (Exception e) {
        temp = false;
      }
      if (temp) {
        query += " ua.facility_id in (select ugm.facility_id from user_group_member ugm, facility fgm" +
            " where ugm.user_group_id = 'CreateReport' and ugm.personnel_id = " + uID +
            " and ugm.facility_id = fgm.facility_id and fgm.facility_group_id = '" + facilityID + "') and";
      } else {
        query += " ua.facility_id = '" + facilityID + "' and";
      }
    }

    if (facilityID.startsWith("All")) {
      query += " ua.facility_id = d.facility_id and d.user_group_id = 'CreateReport' and d.personnel_id = " + uID + " and";
    }

    if (!BothHelpObjs.isBlankString(reportingEntity)) {
      if (!"No Reporting Entity".equalsIgnoreCase(reportingEntity) &&
          !"All Reporting Entities".equalsIgnoreCase(reportingEntity)) {
        query += " (ua.facility_id,ua.application) in (select facility_id,application from fac_app_report_view where personnel_id = "+uID+
                 " and reporting_entity_id = '"+reportingEntity+"') and";
      }
    }

    if (application.length() > 0 && !application.startsWith("All")) {
      if (application.equalsIgnoreCase("My Work Areas")) {
        if (!BothHelpObjs.isBlankString(facilityID) && !facilityID.startsWith("All")) {
          query += " ua.application in (select application from my_workarea_facility_view ugma where ugma.personnel_id = " + uID + " and ugma.facility_id = '" + facilityID + "' union all select 'All' application from dual) and";
        } else {
          query += " ua.application in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
              " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = " + uID + " union all select 'All' application from dual) and";
        }
      } else {
        query += " ua.application in ('" + application + "','All') and";
      }
    }
    //removing the last and
    query = query.substring(0, query.length() - 3);
    query += ") aa group by " + var4 + "";
    return query;
  } //end of method

  String getPartInInventorySQL(String uID, String var1, String var2, String var4, boolean facApp, boolean onlyfac, Integer inventoryMonth, Integer inventoryDay, String inventoryYear, String reportingEntity) {
    String query = "";
    query += "select " + var2 + " from inventory_status_detail aa,facility f";
    if (facilityID.startsWith("All")) {
      query += " ,user_group_member c";
    }
    // inventory date
    Integer inventoryDate = new Integer(199701);
    try {
      inventoryMonth = new Integer(inventoryMonth.intValue() + 1);
      String sm = new String(inventoryMonth.toString());
      if (sm.length() < 2) {
        sm = "0" + sm;
      }
      inventoryDay = new Integer(inventoryDay.intValue() + 1);
      String sd = new String(inventoryDay.toString());
      if (sd.length() < 2) {
        sd = "0" + sd;
      }
      inventoryDate = new Integer(inventoryYear + sm + sd);
    } catch (Exception e) {
      e.printStackTrace();
    }

    query += " where aa.facility_id = f.inventory_group_default and aa.daily_date = to_date('" + inventoryDate.toString() + "','YYYYMMDD')";
    if (facilityID.length() > 0 && !facilityID.startsWith("All")) {
      //if facility is facility group
      boolean temp = false;
      try {
        temp = HelpObjs.countQuery(db, "select count(*) from facility where facility_group_id = '" + facilityID + "'") > 0;
      } catch (Exception e) {
        temp = false;
      }
      if (temp) {
        query += " and f.facility_id in (select ugm.facility_id from user_group_member ugm, facility fgm" +
            " where ugm.user_group_id = 'CreateReport' and ugm.personnel_id = " + uID +
            " and ugm.facility_id = fgm.facility_id and fgm.facility_group_id = '" + facilityID + "')";
      } else {
        query += " and f.facility_id = '" + facilityID + "' ";
      }
    }
    if (facilityID.startsWith("All")) {
      query += " and f.facility_id = c.facility_id and c.user_group_id = 'CreateReport' and c.personnel_id = " + uID;
    }

    if (!BothHelpObjs.isBlankString(reportingEntity)) {
      if (!"No Reporting Entity".equalsIgnoreCase(reportingEntity) &&
          !"All Reporting Entities".equalsIgnoreCase(reportingEntity)) {
        query += " and aa. reporting_entity_id = '"+reportingEntity+"' ";
      }
    }

    if (application.length() > 0 && !application.startsWith("All") && !application.equalsIgnoreCase("My Work Areas")) {
        query += " aa.application  = '" + application + "'";
    }
    query += " group by " + var4 + "";

    return query;
  } //end of method

  public Hashtable getReportData(TcmISDBModel db, Hashtable inData, String uID, PrintWriter pw) throws Exception {
    Hashtable Result = new Hashtable();

    endYear = inData.get("END_YEARN").toString();
    endMonth = inData.get("END_MONTH").toString();
    beginYear = inData.get("BEGIN_YEARN").toString();
    beginMonth = inData.get("BEGIN_MONTH").toString();
    partNo = (String) inData.get("PART_NUMBER");
    partOperator = (String) inData.get("PART_OPERATOR");
    type = (String) inData.get("TYPE");
    application = inData.get("WORK_AREA").toString();
    facilityID = inData.get("FACILITY_ID").toString();
    method = (String) inData.get("METHOD");
    reportFields = (Vector) inData.get("REPORT_FIELDS"); //chemical lists
    reportFields1 = (Vector) inData.get("REPORT_FIELDS1"); //report fields
    formater = inData.get("FORMAT").toString();
    String reportingEntity = (String) inData.get("REPORTING_ENTITY");

    String var1 = "";
    String var2 = "";
    String var3 = "";
    String var4 = "";
    String query = null;
    String outSideCol = "";
    String outSideGroup = "";
    boolean outSideGroupBy = false;
    boolean partNoInReport = reportFields1.contains("Part No.");

    //defining chemical lists
    int i = 0;
    for (i = 0; i < reportFields.size(); i++) {
      String temp = (String) reportFields.elementAt(i);
      temp = temp.substring(0, temp.indexOf(" - "));
      if (partNoInReport) {
        if (formater.equalsIgnoreCase("0")) {
          var1 += "decode(fx_item_list_percent('" + temp + "',xx.item_id),null,' ','Yes') \"" + temp + "\",";
        } else if (formater.equalsIgnoreCase("1")) {
          var1 += "to_char(fx_item_list_percent('" + temp + "',xx.item_id),'999.9') \"" + temp + "\",";
        } else if (formater.equalsIgnoreCase("2")) {
          //var1 += "to_char(fx_item_list_percent('"+temp+"',xx.item_id),'0.99EEEE') \""+temp+"\",";
          var1 += "fx_item_list_percent('" + temp + "',xx.item_id) \"" + temp + "\",";
        } else if (formater.equalsIgnoreCase("3")) {
          var1 += "fx_sig_fig(fx_item_weight(xx.item_id) * xx.quantity_ordered * fx_item_list_percent('" + temp + "',xx.item_id)/100,3) \"" + temp + "\",";
        } else {
          var1 += "fx_item_list_percent('" + temp + "',xx.item_id) \"" + temp + "\",";
        }
      } else {
        if (formater.equalsIgnoreCase("0") || formater.equalsIgnoreCase("1") || formater.equalsIgnoreCase("2")) {
          var1 += "decode(max(fx_item_list_percent('" + temp + "',xx.item_id)),0,' ',null,' ','Yes') \"" + temp + "\",";
        } else {
          var1 += "fx_sig_fig(sum(fx_item_weight(xx.item_id) * xx.quantity_ordered * fx_item_list_percent('" + temp + "',xx.item_id)/100),3) \"" + temp + "\",";
        }
        outSideGroupBy = true;
      }
    }
    if (var1.length() > 0) { //removing the last commas
      var1 = var1.substring(0, var1.length() - 1);
    }

    //defining report fields
    for (i = 0; i < reportFields1.size(); i++) {
      String temp = (String) reportFields1.elementAt(i);
      if (temp.equalsIgnoreCase("Facility")) {
        var2 += "aa.facility_id,";
        var4 += "aa.facility_id,";
        outSideCol += "xx.facility_id,";
        outSideGroup += "xx.facility_id,";
      } else if (temp.equalsIgnoreCase("Work Area")) {
        if (type.equalsIgnoreCase("Part Number")) {
          String tmpApp = " ";
          if (application.length() > 0 && !application.startsWith("All")) {
            tmpApp = application;
          }
          var2 += "'" + tmpApp + "' application,";
        } else {
          var2 += "nvl(aa.application,'SITE') application,";
          var4 += "aa.application,";
        }
        outSideCol += "xx.application,";
        outSideGroup += "xx.application,";
      } else if (temp.equalsIgnoreCase("Part No.")) {
        var2 += "aa.fac_part_no,";
        var4 += "aa.fac_part_no,";
        outSideCol += "xx.fac_part_no,";
        outSideGroup += "xx.fac_part_no,";
      } else if (temp.equalsIgnoreCase("Part Description")) {
        var2 += "aa.part_description,";
        var4 += "aa.part_description,";
        outSideCol += "xx.part_description,";
        outSideGroup += "xx.part_description,";
      } else if (temp.equalsIgnoreCase("Packaging")) {
        //outSideCol += "fx_kit_packaging(xx.item_id) packaging,";
        //outSideGroup += "fx_kit_packaging(xx.item_id),";
        outSideCol += "iis.packaging,";
        outSideGroup += "iis.packaging,";
      } else if (temp.equalsIgnoreCase("Month")) {
        if (type.equalsIgnoreCase("Part Number") || type.equalsIgnoreCase("All Approved")) {
          var2 += "' ' month1,";
        } else {
          var2 += "to_char(date_shipped,'Mon-yy') month1,";
          var4 += "to_char(date_shipped,'Mon-yy'),";
        }
        outSideCol += "xx.month1,";
        outSideGroup += "xx.month1,";
      } else if (temp.equalsIgnoreCase("Quantity")) {
        if (type.equalsIgnoreCase("Part Number") || type.equalsIgnoreCase("All Approved")) {
          var2 += "' ' quantity_ordered,";
        } else {
          var2 += "sum(quantity) quantity_ordered,";
        }
        outSideCol += "xx.quantity_ordered,";
        outSideGroup += "xx.quantity_ordered,";
      } else if (temp.equalsIgnoreCase("Manufacturer")) {
        //outSideCol += "fx_mfg_desc(xx.item_id)  mfg_desc,";
        //outSideGroup += "fx_mfg_desc(xx.item_id),";
        outSideCol += "iis.mfg_desc,";
        outSideGroup += "iis.mfg_desc,";
      } else if (temp.equalsIgnoreCase("Item Unit Weight (lb.)")) {
        outSideCol += "iis.item_wt item_unit_weight,";
        outSideGroup += "iis.item_wt,";
      } else if (temp.equalsIgnoreCase("Item Total Weight (lb.)")) {
        if (type.equalsIgnoreCase("Part Number") || type.equalsIgnoreCase("All Approved")) {
          outSideCol += "' ' item_weight_shipped,";
        }else {
          //if quantity was not selected as report field then add to sql
          if (var2.indexOf("quantity_ordered,") < 0) {
            var2 += "sum(quantity) quantity_ordered,";
          }
          outSideCol += "iis.item_wt*xx.quantity_ordered item_weight_shipped,";
          outSideGroup += "iis.item_wt*xx.quantity_ordered,";
        }
      }
    }
    if (var2.length() > 0) {
      var2 = var2.substring(0, var2.length() - 1); //removing the last commas
    }
    if (var4.length() > 0) {
      var4 = var4.substring(0, var4.length() - 1); //removing the last commas
    }
    if (outSideCol.length() > 0) { //removing the last commas
      outSideCol = outSideCol.substring(0, outSideCol.length() - 1);
    }
    if (outSideGroup.length() > 0) { //removing the last commas
      outSideGroup = outSideGroup.substring(0, outSideGroup.length() - 1);
    }

    //find out whether if users only selected facility and/or work area for report field(s)
    boolean facApp = false;
    boolean onlyfac = false;
    if (reportFields1.size() == 1 || reportFields1.size() == 2) {
      if (reportFields1.size() == 1) {
        if (reportFields1.contains("Facility") || reportFields1.contains("Work Area")) {
          facApp = true;
          if (reportFields1.contains("Facility")) {
            onlyfac = true;
          }
        }
      } else {
        if (reportFields1.contains("Facility") && reportFields1.contains("Work Area")) {
          facApp = true;
        }
      }
    }
    //now determine what report option user selected
    if (type.equalsIgnoreCase("Part Number")) {
      if (var2.length() > 0) {
        var2 += ",aa.item_id";
      }else {
        var2 += "aa.item_id";
      }
      if (var4.length() > 0) {
        var4 += ",aa.item_id";
      }else {
        var4 += "aa.item_id";
      }
      //don't restrict to reporting entitty coz inventory is by facilities not work areas
      query = getPartNumberSQL(uID, var1, var2, var4, facApp, onlyfac,reportingEntity);
      if (var1.length() > 0) {
        query = "select " + outSideCol + "," + var1 + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      } else {
        query = "select " + outSideCol + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      }
    } else if (type.equalsIgnoreCase("All Used")) {
      if (var2.length() > 0) {
        var2 += ",aa.item_id";
      }else {
        var2 += "aa.item_id";
      }
      if (var4.length() > 0) {
        var4 += ",aa.item_id";
      }else {
        var4 += "aa.item_id";
      }
      if (!reportFields1.contains("Quantity") && formater.equalsIgnoreCase("3")) {
        var2 += ",sum(aa.quantity) quantity_ordered";
      }
      query = getPartUsedSQL(uID, var1, var2, var4, facApp, onlyfac,reportingEntity);
      if (var1.length() > 0) {
        query = "select " + outSideCol + "," + var1 + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      } else {
        query = "select " + outSideCol + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      }
    } else if (type.equalsIgnoreCase("All Approved")) {
      if (var2.length() > 0) {
        var2 += ",aa.item_id";
      }else {
        var2 += "aa.item_id";
      }
      if (var4.length() > 0) {
        var4 += ",aa.item_id";
      }else {
        var4 += "aa.item_id";
      }
      query = getApprovedPartSQL(uID, var1, var2, var4, facApp, onlyfac,reportingEntity);
      if (var1.length() > 0) {
        query = "select " + outSideCol + "," + var1 + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      } else {
        query = "select " + outSideCol + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      }
    } else if (type.equalsIgnoreCase("Parts in Inventory")) {
      if (var2.length() > 0) {
        var2 += ",aa.item_id";
      }else {
        var2 += "aa.item_id";
      }
      if (var4.length() > 0) {
        var4 += ",aa.item_id";
      }else {
        var4 += "aa.item_id";
      }
      if (!reportFields1.contains("Quantity") && formater.equalsIgnoreCase("3")) {
        var2 += ",sum(aa.quantity) quantity_ordered";
      }
      Integer inventoryMonth = new Integer(inData.get("INVENTORY_MONTH").toString());
      Integer inventoryDay = new Integer(inData.get("INVENTORY_DAY").toString());
      String inventoryYear = (String) inData.get("INVENTORY_YEARN");
      //don't restrict to reporting entitty coz inventory is by facilities not work areas
      query = getPartInInventorySQL(uID, var1, var2, var4, facApp, onlyfac, inventoryMonth, inventoryDay, inventoryYear, reportingEntity);
      if (var1.length() > 0) {
        query = "select " + outSideCol + "," + var1 + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      } else {
        query = "select " + outSideCol + " from item_info_snap iis, (" + query + ") xx"+
                " where iis.item_id = xx.item_id";
        if (outSideGroupBy) {
          query += " group by " + outSideGroup;
        }
      }
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    int numRecs = 0;
    try {
      //System.out.print("Run Querry: " + query);
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        numRecs += 1;
        int j = 0;
        //report fields
        for (i = 0; i < reportFields1.size(); i++) {
          String temp = (String) reportFields1.elementAt(i);
          if (temp.equalsIgnoreCase("Facility")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("facility_id")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Work Area")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("application")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Part No.")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_no")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Part Description")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("part_description")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Packaging")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("packaging")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Quantity")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("quantity_ordered")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Month")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("month1")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Manufacturer")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("mfg_desc")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Item Unit Weight (lb.)")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("item_unit_weight")));
            pw.println("</TD>\n");
          } else if (temp.equalsIgnoreCase("Item Total Weight (lb.)")) {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString("item_weight_shipped")));
            pw.println("</TD>\n");
          }
          j++;
        }
        //chem lists
        for (i = 0; i < reportFields.size(); i++) {
          String temp = (String) reportFields.elementAt(i);
          temp = temp.substring(0, temp.indexOf(" - "));
          String temp1 = BothHelpObjs.makeBlankFromNull(rs.getString(temp));
          if ( (formater.equalsIgnoreCase("2")) && (temp1.length() >= 1) && !"0".equalsIgnoreCase(temp1)) {
            String newformat = " ";
            try {
              double val = Double.parseDouble(temp1);
              newformat = this.toScientific(val);
            } catch (Exception e) {
              e.printStackTrace();
              throw e;
            }
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(newformat);
            pw.println("</TD>\n");
          } else {
            pw.println("<TD ALIGN=\"LEFT\">\n");
            pw.println(temp1);
            pw.println("</TD>\n");
          }
          j++;
        }
        pw.println("</TR>\n");
      }
    } catch (Exception e) {
      Result.put("RECORDS", "Error");
      e.printStackTrace();
      return Result;
    } finally {
      dbrs.close();
    }
    Result.put("RECORDS", "" + numRecs + "");
    return Result;
  }

  public static String toScientific(double d) {
    int exp = (int) Math.floor(Math.log(d) / Math.log(10.0));
    double fraction = d / Math.pow(10.0, exp);
    return "" + fraction + "E" + exp;
  }

  public Hashtable saveTemplate(Hashtable inData) throws Exception {
    String action = (String) inData.get("SAVE_ACTION");
    String template = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( (String) inData.get("TEMPLATE_NAME"));
    Integer userID = (Integer) inData.get("USER_ID");
    boolean result = false;
    String msg = new String();
    boolean override = false;
    Hashtable resultH = new Hashtable();
    try {
      if (action.equalsIgnoreCase("new")) {
        if (templateExist(template, userID.intValue())) {
          msg = "Template name already exist.\nDo you want to overwrite it?";
          result = false;
          override = true;
        } else {
          createTemplate(inData);
          msg = "Template was successfully saved.";
          result = true;
        }
      } else { //updating existing template
        //first delete it
        if (deleteTemplate(template, userID.intValue())) {
          createTemplate(inData);
          msg = "Template was successfully saved.";
          result = true;
        } else {
          msg = "An error occurred while updating template.\nPlease restart tcmIS and try again.\n If the problem recurs, contact tcmIS Customer Service Representative (CSR).";
          result = false;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    resultH.put("SUCCESS", new Boolean(result));
    resultH.put("MSG", msg);
    resultH.put("OVERRIDE", new Boolean(override));

    return (resultH);
  }

  //this method will insert data into template table with template(report_hash_seq)
  public String createReportTemplate(String userID, Hashtable inData) throws Exception {
    String sessionID = userID + "xyz";
    try {
      Integer x = new Integer(HelpObjs.getNextValFromSeq(db, "report_hash_seq"));
      Hashtable innerH = (Hashtable) inData.get("SELECTED_DATA");
      innerH.put("USER_ID", userID);
      for (Enumeration enuma = innerH.keys(); enuma.hasMoreElements(); ) {
        String key = enuma.nextElement().toString();
        String value = "";
        if ("REPORT_FIELDS".equalsIgnoreCase(key)) {
          value = HelpObjs.getStringValuesFromVector( (Vector) innerH.get(key));
        } else if ("REPORT_FIELDS1".equalsIgnoreCase(key)) {
          value = HelpObjs.getStringValuesFromVector( (Vector) innerH.get(key));
        } else {
          value = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(innerH.get(key).toString());
        }
        db.doQuery("insert into report_hash_table(report_id,field_name,field_value) values(" + x.toString() + ",'" + key + "','" + value + "')");
      }
      sessionID = x.toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return sessionID;
  } //end of method

  public Hashtable loadReportTemplate(String sessionID) throws Exception {
    String query = "select field_name,field_value from report_hash_table where report_id = " + sessionID;
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String key = rs.getString("field_name");
        String value = BothHelpObjs.makeBlankFromNull(rs.getString("field_value"));
        if ("REPORT_FIELDS".equalsIgnoreCase(key)) {
          Vector v = HelpObjs.getVectorFromString(value);
          result.put(key, v);
        } else if ("REPORT_FIELDS1".equalsIgnoreCase(key)) {
          Vector v = HelpObjs.getVectorFromString(value);
          result.put(key, v);
        } else {
          result.put(key, value);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  } //end of method

  void createTemplate(Hashtable inData) throws Exception {
    String template = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( (String) inData.get("TEMPLATE_NAME"));
    Integer userID = (Integer) inData.get("USER_ID");
    Hashtable h = (Hashtable) inData.get("SELECTED_DATA");
    endYear = h.get("END_YEAR").toString();
    endMonth = h.get("END_MONTH").toString();
    beginYear = h.get("BEGIN_YEAR").toString();
    beginMonth = h.get("BEGIN_MONTH").toString();
    partNo = (String) h.get("PART_NUMBER");
    partOperator = (String) h.get("PART_OPERATOR");
    type = (String) h.get("TYPE");
    workarea = h.get("WORK_AREA").toString();
    facilityID = h.get("FACILITY_ID").toString();
    String reportingEntity = (String)h.get("REPORTING_ENTITY");
    reportFields = (Vector) h.get("REPORT_FIELDS");
    reportFields1 = (Vector) h.get("REPORT_FIELDS1");
    formater = h.get("FORMAT").toString();
    boolean inventoryData = false;
    Integer inventoryMonth = new Integer(0);
    Integer inventoryDay = new Integer(0);
    Integer inventoryYear = new Integer(0);
    if (h.containsKey("INVENTORY_MONTH")) {
      inventoryMonth = (Integer) h.get("INVENTORY_MONTH");
      inventoryDay = (Integer) h.get("INVENTORY_DAY");
      inventoryYear = (Integer) h.get("INVENTORY_YEAR");
      inventoryData = true;
    }
    //chemical lists
    String myReportFields = new String("");
    for (int i = 0; i < reportFields.size(); i++) {
      String temp3 = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(reportFields.elementAt(i).toString());
      //first change commas to its hex \2C
      String tmpS = "";
      StringTokenizer st = new StringTokenizer(temp3, ",");
      while (st.hasMoreTokens()) {
        tmpS += st.nextToken().toString() + "/2C";
      }
      //remove the last \2C
      if (tmpS.length() > 3) {
        tmpS = tmpS.substring(0, tmpS.length() - 3);
      }
      myReportFields += tmpS + ",";
    }
    //removing the last commas ','
    if (myReportFields.length() > 0) {
      myReportFields = myReportFields.substring(0, myReportFields.length() - 1);
    }

    //base fields
    String myReportFields1 = new String("");
    for (int i = 0; i < reportFields1.size(); i++) {
      myReportFields1 += (String) reportFields1.elementAt(i) + ",";
    }
    //removing the last commas ','
    if (myReportFields1.length() > 0) {
      myReportFields1 = myReportFields1.substring(0, myReportFields1.length() - 1);
    }

    ResultSet rs = null;
    try {
      String query = "";
      if (inventoryData) {
        query = "insert into material_matrix_template "+
                "(personnel_id,template,type,Part_Number,facility_id,APPLICATION,REPORT_FIELDS,REPORT_FIELDS1,"+
                "BEGIN_MONTH, BEGIN_YEAR, END_MONTH, END_YEAR, FORMAT,inventory_month,inventory_day,inventory_year,part_operator,reporting_entity_id)"+
                "values (" + userID + ",'" + template + "','" + type + "','" + partNo + "','" + facilityID + "','" + workarea + "','" + myReportFields + "'"+
                ",'" + myReportFields1 + "','" + beginMonth + "','" + beginYear + "','" + endMonth + "','" + endYear + "','" + formater + "'," +
                inventoryMonth.intValue() + "," + inventoryDay.intValue() + "," + inventoryYear.intValue() + ",'" + partOperator + "','"+reportingEntity+"')";
      } else {
        query = "insert into material_matrix_template "+
                "(personnel_id,template,type,Part_Number,facility_id,APPLICATION,REPORT_FIELDS,REPORT_FIELDS1,"+
                "BEGIN_MONTH, BEGIN_YEAR, END_MONTH, END_YEAR, FORMAT,part_operator,reporting_entity_id)"+
                "values (" + userID + ",'" + template + "','" + type + "','" + partNo + "','" + facilityID + "','" + workarea + "','" + myReportFields + "'"+
                ",'" + myReportFields1 + "','" + beginMonth + "','" + beginYear + "','" + endMonth + "','" + endYear + "','" + formater + "','" +
                partOperator + "','"+reportingEntity+"')";
      }
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public boolean deleteTemplate(String templateName, int userID) throws Exception {
    String query = "delete from material_matrix_template where personnel_id = " + userID;
    query += " and template = '" + templateName + "'";
    try {
      //System.out.println("n\n-------- query: " + query);
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean templateExist(String templateName, int userID) throws Exception {
    String query = "select count(*) from material_matrix_template where personnel_id = " + userID;
    query += " and lower(template) = lower('" + templateName + "')";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int count = 0;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      dbrs.close();
    }
    return (count > 0);
  }

  public Vector getTemplate(Hashtable inData) throws Exception {
    Vector v = new Vector();
    String query = "select template from material_matrix_template";
    query += " where personnel_id = " + inData.get("USER_ID");
    query += " order by template";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("template"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }

    if (v.size() > 1) {
      v.insertElementAt("Select a template", 0);
    }
    return v;
  }

  public Hashtable getMatrixTemplateInfo(Hashtable inData) throws Exception {
    Hashtable result = new Hashtable();
    Vector baseField = getBaseField();
    Vector chemicalList = new Vector();
    String query = "select list_id,list_name from list order by list_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String list = BothHelpObjs.makeBlankFromNull(rs.getString("list_id"));
        list += " - " + BothHelpObjs.makeBlankFromNull(rs.getString("list_name"));
        chemicalList.addElement(list); //id - name
      }

      //check to see if client uses display Parts in Inventory
      query = "select count(*) from tcmis_feature where feature = 'materialMatrix.partsInInventory' and feature_mode = 1";
      result.put("DISPLAY_PARTS_IN_INVENTORY", new Boolean(HelpObjs.countQuery(db, query) > 0));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }

    Vector v = new Vector(2);
    v.addElement("is");
    v.addElement("contains");
    result.put("SEARCH_OPERATOR", v);

    result.put("TYPE", "Part Number");
    result.put("BASE_FIELDS", baseField);
    result.put("CHEMICAL_LIST", chemicalList);
    //now get my list of facilities and work area
    AdHocReportsDBObj adHocReportsDBObj = new AdHocReportsDBObj(db);
    result.put("FACILITIES_INFO",adHocReportsDBObj.getFacilityWorkArea(new Integer(inData.get("USERID").toString()),new Hashtable()));

    return result;
  } //end of method

  public Vector getBaseField() throws Exception {
    Vector v = new Vector();
    String query = "select base_field from base_field where report_type = 'MaterialMatrix' order by display_order";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("base_field")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  }

  public Hashtable getCategory() throws Exception {
    Hashtable categoryH = new Hashtable();
    String query = "select category_id,category_desc from vv_category order by category_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      Vector categoryDesc = new Vector();
      Vector categoryID = new Vector();
      while (rs.next()) {
        categoryDesc.addElement(rs.getString("category_desc"));
        categoryID.addElement(rs.getString("category_id"));
      }

      categoryH.put("CATEGORY_DESC", categoryDesc);
      categoryH.put("CATEGORY_ID", categoryID);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }

    return categoryH;
  }

  public void insert(String col, String val, int type) throws Exception {
    Integer I;
    DBResultSet dbrs = null;
    ResultSet rs = null;

    String query = new String("update report_template set " + col + "=");
    switch (type) {
      case INT:
        I = new Integer(val);
        query = query + I.intValue();
        break;
      case DATE:
        if (val.equals("nowDate")) {
          query = query + " SYSDATE";
        } else {
          query = query + " to_date('" + val + "','MM/dd/yyyy HH24:MI:SS')";
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
    //query += " where traveler_id = '"+ +"'";
    //System.out.println("&&&&&&&&&&&&&&&& " + query);
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,
                       "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
  }

  public Hashtable loadTemplateInfo(Hashtable inData) throws Exception {
    Integer userID = (Integer) inData.get("USER_ID");
    String template = (String) inData.get("TEMPLATE");
    String query = "select * from material_matrix_template where personnel_id = '" + userID + "'";
    query += " and template = '" + template + "'";
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result.put("TYPE", BothHelpObjs.makeBlankFromNull(rs.getString("TYPE")));
        result.put("FACILITY_ID", BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID")));
        result.put("REPORTING_ENTITY", BothHelpObjs.makeBlankFromNull(rs.getString("REPORTING_ENTITY_ID")));
        result.put("WORK_AREA", BothHelpObjs.makeBlankFromNull(rs.getString("APPLICATION")));
        result.put("PART_NUMBER", BothHelpObjs.makeBlankFromNull(rs.getString("PART_NUMBER")));
        result.put("PART_OPERATOR", BothHelpObjs.makeBlankFromNull(rs.getString("part_operator")));
        result.put("BEGIN_MONTH", BothHelpObjs.makeBlankFromNull(rs.getString("begin_month")));
        result.put("BEGIN_YEAR", BothHelpObjs.makeBlankFromNull(rs.getString("begin_year")));
        result.put("END_MONTH", BothHelpObjs.makeBlankFromNull(rs.getString("end_month")));
        result.put("END_YEAR", BothHelpObjs.makeBlankFromNull(rs.getString("end_year")));
        result.put("FORMAT", BothHelpObjs.makeBlankFromNull(rs.getString("FORMAT")));
        result.put("INVENTORY_MONTH", BothHelpObjs.makeBlankFromNull(rs.getString("inventory_month")));
        result.put("INVENTORY_DAY", BothHelpObjs.makeBlankFromNull(rs.getString("inventory_day")));
        result.put("INVENTORY_YEAR", BothHelpObjs.makeBlankFromNull(rs.getString("inventory_year")));

        String reportFields = BothHelpObjs.makeBlankFromNull(rs.getString("REPORT_FIELDS"));
        String reportFields1 = BothHelpObjs.makeBlankFromNull(rs.getString("REPORT_FIELDS1"));

        Vector v = new Vector();
        if (reportFields.length() > 0) {
          //doesn't work coz commas exist within data -- StringTokenizer st = new StringTokenizer(reportFields,",");
          StringTokenizer st = new StringTokenizer(reportFields, ","); //2C hex for commas
          while (st.hasMoreTokens()) {
            String tmp = st.nextElement().toString();
            Vector field = new Vector();
            convertToCommas(tmp, field);

            String temp = "";
            for (int i = 0; i < field.size(); i++) {
              temp += (String) field.elementAt(i) + ",";
            }
            //removing the last commas
            if (temp.length() > 0) {
              temp = temp.substring(0, temp.length() - 1);
            }
            v.addElement(temp);
          }
          result.put("REPORT_FIELDS", v);
        } else {
          result.put("REPORT_FIELDS", v); //send back an empty vector
        }

        Vector v1 = new Vector();
        if (reportFields1.length() > 0) {
          StringTokenizer st = new StringTokenizer(reportFields1, ",");
          while (st.hasMoreTokens()) {
            v1.addElement(st.nextToken());
          }
          result.put("REPORT_FIELDS1", v1);
        } else {
          result.put("REPORT_FIELDS1", v1); //send back an empty vector
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  }

  void convertToCommas(String tmp, Vector field) {
    int pos = tmp.indexOf("/2C");
    if (pos < 0) { //string does not contain commas
      field.addElement(tmp);
    } else {
      field.addElement(tmp.substring(0, pos));
      tmp = tmp.substring(pos + 3);
      convertToCommas(tmp, field);
    }
  } //end of method
} //end of class
