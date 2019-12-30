package com.tcmis.internal.report.factory;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.internal.report.beans.AdhocUsageReportBean;
import com.tcmis.internal.report.beans.AdhocWasteReportBean;
import com.tcmis.internal.report.beans.MaterialMatrixReportBean;
import com.tcmis.internal.report.beans.MsdsReportBean;
import com.tcmis.internal.report.beans.ReportParameterBean;

/******************************************************************************
 * This is a specialized factory for creating reports.
 *****************************************************************************/
public class GenericReportFactory
    extends BaseBeanFactory {
  Log log = LogFactory.getLog(this.getClass());

  //constructor
  public GenericReportFactory(DbManager dbManager) {
    super(dbManager);
  }

      /*******************************************************************************
       * Queries fx_ad_hoc_sql (pl/sql function) to get sql query to execute, executes
   * query given but pl/sql function and returns resulting <code>DataSet</code>
   * @param bean <code>GenericReportBean</code> used to hold data used to
   * construct query.
       *******************************************************************************/
  public DataSet getDataSetForAdhocUsage(AdhocUsageReportBean bean) throws
      Exception {
    String query = getQueryForAdhocUsage(bean);
    //System.out.println("FX QUERY" + query);
    DataSet dataSet = getDbManager().select(query);
    if (log.isInfoEnabled()) {
      log.info("DATASET:" + dataSet);
    }
    Iterator iterator = dataSet.iterator();
    while (iterator.hasNext()) {
      DataSetRow row = (DataSetRow) iterator.next();
      query = row.getString("QUERY");
    }
    dataSet = getDbManager().select(query);
    if (log.isInfoEnabled()) {
      log.info("DATA SET:" + dataSet);
    }
    return dataSet;
  }

  //this logic should be in the process but I want to keep the sql
  //in the factory. Hopefully it'll be fixed some day...
  //constructs pl/sql query
  private String getQueryForAdhocUsage(AdhocUsageReportBean bean) {
    String where = "";
    String loc = "";
    String reportCategory = "";
    String beginDay = bean.getBeginDay();
    String endDay = bean.getEndDay();
    String separator = "|";
    String beginDate = "";
    String endDate = "";
    Integer begD = new Integer(199701);
    try {
      Integer bm = new Integer(bean.getBeginMonth());
      bm = new Integer(bm.intValue() + 1);
      String sm = new String(bm.toString());
      if (sm.length() < 2) {
        sm = "0" + sm;
      }
      Integer by = new Integer(bean.getBeginYear());
      if (beginDay.length() < 2) {
        beginDay = "0" + beginDay;
      }
      begD = new Integer(by.toString() + sm + beginDay);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    Integer endD = new Integer(209012);
    try {
      Integer em = new Integer(bean.getEndMonth());
      em = new Integer(em.intValue() + 1);
      Integer ey = new Integer(bean.getEndYear());
      String esm = new String(em.toString());
      if (esm.length() < 2) {
        esm = "0" + esm;
      }
      if (beginDay.length() < 2) {
        beginDay = "0" + beginDay;
      }
      endD = new Integer(ey.toString() + esm + endDay);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    beginDate = "to_date('" + begD.toString() + "','YYYYMMDD')";
    endDate = "to_date('" + endD.toString() + "','YYYYMMDD') + 1";

    String additionalFrom = "";
    String reportField = "";
    Collection c = bean.getReportFields();
    Iterator iterator = c.iterator();
    while (iterator.hasNext()) {
      String s = iterator.next().toString().trim();
      if (s.length() > 0) {
        reportField += s + separator;
      }
    }
    //removing the last | pipe
    if (reportField.length() > 0) {
      reportField = reportField.substring(0, reportField.length() - 1);
    }

    //building where clause
    if (bean.getFacilityID() != null &&
        bean.getFacilityID().trim().length() > 0 &&
        ! (bean.getFacilityID().startsWith("All"))) {

      where += "a.facility=''" + bean.getFacilityID() + "'' and ";
    }
    else if (bean.getFacilityID() != null &&
             bean.getFacilityID().startsWith("All")) {
      where += "a.facility = ugm.facility_id and " +
          "ugm.user_group_id =''CreateReport'' and " +
          "ugm.personnel_id = " + bean.getUserID() + " and ";
      additionalFrom += "user_group_member ugm";
    }
    if ( (bean.getApplication().trim().length() > 0) &&
        (! (bean.getApplication().equalsIgnoreCase("All Work Areas")))) {
      if (bean.getApplication().equalsIgnoreCase("My Work Areas")) {
        if (bean.getFacilityID() != null &&
            bean.getFacilityID().trim().length() > 0 &&
            !bean.getFacilityID().startsWith("All")) {
          where += "a.location in (select application from " +
              "my_workarea_facility_view ugma where " +
              "ugma.personnel_id = " + bean.getUserID() +
              " and ugma.facility_id = ''" + bean.getFacilityID() + "'') and ";
        }
        else {
          where += "a.location in (select application from " +
              "user_group_member ugm, my_workarea_facility_view ugma " +
              "where ugm.facility_id = ugma.facility_id" +
              " and ugm.personnel_id = ugma.personnel_id and " +
              "ugm.user_group_id = ''CreateReport'' and " +
              "ugma.personnel_id = " + bean.getUserID() + ") and ";
        }
      }
      else {
        where += "a.location=''" + bean.getApplication() + "'' and ";
      }
    }
    if (bean.getCategoryID() != null &&
        bean.getCategoryID().trim().length() > 0 &&
        !bean.getCategoryID().equalsIgnoreCase("All Categories")) {
      where += "a.category_id=''" + bean.getCategoryID() + "'' and ";
    }

    String sqlListID = null;
    if (bean.getType() != null && bean.getType().equalsIgnoreCase("List")) {
      if (bean.getListID() != null && bean.getListID().trim().length() > 0) {
        sqlListID = "'" + bean.getListID() + "'";
      }
    }
    String sqlCASID = null;
    if (bean.getType() != null && bean.getType().equalsIgnoreCase("Single")) {
      if (bean.getCASListID() != null &&
          bean.getCASListID().trim().length() > 0) {
        sqlCASID = "'" + bean.getCASListID() + "'";
      }
    }

    if (bean.getPartNo() != null && bean.getPartNo().trim().length() > 0) {
      where += "a.fac_part_id=''" + bean.getPartNo() + "'' and ";
    }
    if (bean.getManufacturer() != null &&
        bean.getManufacturer().trim().length() > 0) {
      where += "a.mfg_id=''" + bean.getManufacturer() + "'' and ";
    }
    //*********change this to check for client instead
     if (bean.getLoc() != null &&
         bean.getLoc().trim().length() > 0) {
       where += "a.part_location=''" + loc + "'' and ";
     }
    if (bean.getReportCategory() != null &&
        bean.getReportCategory().trim().length() > 0) {
      where += "a.root_category=''" + reportCategory + "'' and ";
    }

    //removing the last "and "
    if (where.length() > 1) {
      where = where.substring(0, where.length() - 4);
    }

    String query = "select fx_ad_hoc_sql('AdHocUsage'," +
        "'" + reportField + "','" +
        separator + "'," +
        beginDate + "," +
        endDate + "," +
        sqlListID + ",'" +
        where + "','" +
        additionalFrom + "'," +
        sqlCASID +
        ") as QUERY from dual";

    return query;
  }

      /*******************************************************************************
   * Gets query from <code>getWhereClauseForUsage</code> and returns a<code>
   * DataSet</code> to the process to create a report.
       *******************************************************************************/
  public DataSet getDataSetForUsage(ReportParameterBean bean) throws Exception {
    return getDbManager().select(getQueryForUsage(bean));
  }

  private String getQueryForUsage(ReportParameterBean bean) {
    //the group_by column contains comma separated strings...
    //parse it and store the strings in a vector
    String groupByString = bean.getGroupBy();
    Vector groupByVector = new Vector(3);
    if (groupByString != null && groupByString.trim().length() > 0) {
      StringTokenizer tokenizer = new StringTokenizer(groupByString, ",");
      while (tokenizer.hasMoreTokens()) {
        groupByVector.add(tokenizer.nextToken());
      }
    }
    else {
      //add default grouping
      groupByVector.add("FACILITY");
      groupByVector.add("WORK_AREA");
    }

    String where = "where ";
    String from = "";
    String orderBy = "";

    // facility
    if (bean.getFacility() != null &&
        bean.getFacility().trim().length() > 0 &&
        !bean.getFacility().equalsIgnoreCase("all")) {
      where = where + "facility = '" + bean.getFacility() + "' and ";
    }
    else if (bean.getFacility() != null &&
             bean.getFacility().equalsIgnoreCase("All")) {
      where += "facility in (select facility_id from user_group_member " +
          "where user_group_id = 'CreateReport' and personnel_id = " +
          bean.getUserId() + ") and ";
    }
    // work area
    if (bean.getWorkArea() != null &&
        bean.getWorkArea().trim().length() > 0 &&
        !bean.getWorkArea().equalsIgnoreCase("all")) {
      if (bean.getWorkArea().equalsIgnoreCase("My Work Areas")) {
        if (bean.getFacility() != null &&
            bean.getFacility().trim().length() > 0 &&
            !bean.getFacility().equalsIgnoreCase("all")) {
          where +=
              "location in (select application from my_workarea_facility_view " +
              "ugma where ugma.personnel_id = " + bean.getUserId() +
              " and ugma.facility_id = '" + bean.getFacility() + "') and ";
        }
        else {
          where +=
              "location in (select application from user_group_member ugm, " +
              "my_workarea_facility_view ugma where " +
              "ugm.facility_id = ugma.facility_id" +
              " and ugm.personnel_id = ugma.personnel_id and " +
              "ugm.user_group_id = 'CreateReport' and ugma.personnel_id = " +
              bean.getUserId() + ") and ";
        }
      }
      else {
        where = where + "location = '" + bean.getWorkArea() + "' and ";
      }
    }
    // begin date
    Integer begD = new Integer(199701);
    try {
      Integer bm = new Integer(bean.getBegmonth());
      bm = new Integer(bm.intValue() + 1);
      String sm = new String(bm.toString());
      if (sm.length() < 2) {
        sm = "0" + sm;
      }
      Integer by = new Integer(bean.getBegyear());
      begD = new Integer(by.toString() + sm);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // end date
    Integer endD = new Integer(209012);
    try {
      Integer em = new Integer(bean.getEndmonth());
      //add 2 to end month so I can use < (less than)
      em = new Integer(em.intValue() + 2);
      Integer ey = new Integer(bean.getEndyear());
      if (em.intValue() > 12) {
        em = new Integer(1);
        ey = new Integer(ey.intValue() + 1);
      }
      String esm = new String(em.toString());
      if (esm.length() < 2) {
        esm = "0" + esm;
      }
      endD = new Integer(ey.toString() + esm);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    where = where + "date_shipped >= to_date('" + begD.toString() +
        "','YYYYMM') and date_shipped < to_date('" + endD.toString() +
        "','YYYYMM') ";

    if (bean.getSearchType().equalsIgnoreCase("list")) {
      where = where + "and list_id = '" + bean.getListId() + "' ";
    }
    if (bean.getSearchType().equalsIgnoreCase("CAS_NUM")) {
      where = where + "and cas_number = '" + bean.getCasNum() + "' ";
    }

    //order by
    orderBy = "order by ";
    for (int i = 0; i < groupByVector.size(); i++) {
      String s = groupByVector.elementAt(i).toString();
      if (s.equalsIgnoreCase("FACILITY")) {
        s = "facility";
      }
      else if (s.equalsIgnoreCase("WORK_AREA")) {
        s = "location";
      }
      else if (s.equalsIgnoreCase("CAS_NUM")) {
        s = "cas_number";
      }
      else if (s.equalsIgnoreCase("DEL_POINT")) {
        s = "delivery_point";
      }
      else if (s.equalsIgnoreCase("MONTH")) {
        s = "year_month";
      }
      orderBy += s + ",";
    }
    if (bean.getSortBy() != null &&
        bean.getSortBy().equalsIgnoreCase("DELIVERY_POINT")) {
      orderBy += "delivery_point,";
    }
    else if (bean.getSortBy() != null &&
             bean.getSortBy().equalsIgnoreCase("WORK_AREA")) {
      orderBy = orderBy + "location,";
    }
    else if (bean.getSortBy() != null &&
             bean.getSortBy().equalsIgnoreCase("PART_NUM")) {
      orderBy = orderBy + "fac_part_id,";
    }
    //remove last comma if anything has been added to order by, otherwise to empty
    if (orderBy.endsWith(",")) {
      orderBy = orderBy.substring(0, orderBy.length() - 1);
    }
    else {
      orderBy = "";
    }
    // now build the select, from and groupby parts and put the query together
    String query = "";
    if (bean.getSearchType().equalsIgnoreCase("list")) {
      // select
      String select1 = "select fac_part_id,display_name,location,facility," +
          "trade_name,cas_number,percent," +
          "wt_per_unit,sum(qty_shipped) qty_shipped, " +
          "sum(wt_shipped) wt_shipped, " +
          "sum(chemical_wt_shipped) lbs_reportable, " +
          "delivery_point,application_display,delivery_point_display ";
      String groupBy1 = " group by fac_part_id,location,facility,trade_name," +
          "cas_number,display_name,percent,wt_per_unit," +
          "delivery_point,application_display,delivery_point_display ";
      if (groupByVector.indexOf("month") > -1) {
        select1 = select1 + ", to_char(date_shipped,'yyyymm') year_month ";
        groupBy1 = groupBy1 + ", to_char(date_shipped,'yyyymm') ";
      }
      from = "from report_list_view_new ";
      query = select1 + from + where + groupBy1 + orderBy;
    }
    else {
      String select = "select fac_part_id,display_name,location,facility," +
          "trade_name,cas_number,percent,wt_per_unit," +
          "sum(qty_shipped) qty_shipped, sum(wt_shipped) wt_shipped, " +
          "sum(chemical_wt_shipped) lbs_reportable, delivery_point." +
          "application_display,delivery_point_display ";
      String gb =
          " group by fac_part_id,location,facility,trade_name,cas_number," +
          "display_name,percent,wt_per_unit,delivery_point," +
          "application_display,delivery_point_display ";
      if (groupByVector.indexOf("month") > -1) {
        select = select + ", to_char(date_shipped,'yyyymm') year_month ";
        gb = gb + ", to_char(date_shipped,'yyyymm') ";
      }
      from = "from report_composition_view ";
      query = select + from + where + gb + orderBy;
    }
    return query;
  }

      /*******************************************************************************
   * Gets query from <code>getQueryForVOCUsage</code> and returns a <code>
   * DataSet</code> to the process to create a report.
       *******************************************************************************/
  public DataSet getDataSetForVOCUsage(ReportParameterBean bean) throws
      Exception {
    return getDbManager().select(getQueryForVOCUsage(bean));
  }

  private String getQueryForVOCUsage(ReportParameterBean bean) {
    //for some reason the group_by column contains comma separated strings...
    //parse it and store the strings in a vector
    String groupByString = bean.getGroupBy();
    Vector groupByVector = new Vector(3);
    if (groupByString != null && groupByString.trim().length() > 0) {
      StringTokenizer tokenizer = new StringTokenizer(groupByString, ",");
      while (tokenizer.hasMoreTokens()) {
        groupByVector.add(tokenizer.nextToken());
      }
    }
    else {
      //add default grouping
      groupByVector.add("FACILITY");
      groupByVector.add("WORK_AREA");
    }

    String where = "where ";
    String from = "";
    String orderBy = "";

    // where
    // facility
    if (bean.getFacility() != null &&
        bean.getFacility().trim().length() > 0 &&
        !bean.getFacility().equalsIgnoreCase("All")) {
      where = where + "facility = '" + bean.getFacility() + "' and ";
    }
    else if (bean.getFacility() != null &&
             bean.getFacility().equalsIgnoreCase("All")) {
      where += "facility in (select facility_id from user_group_member where " +
          "user_group_id = 'CreateReport' and personnel_id = " +
          bean.getUserId() + ") and ";
    }
// work area
    if (bean.getWorkArea() != null &&
        bean.getWorkArea().trim().length() > 0 &&
        !bean.getWorkArea().equalsIgnoreCase("All")) {
      if (bean.getWorkArea().equalsIgnoreCase("My Work Areas")) {
        if (bean.getFacility() != null &&
            bean.getFacility().trim().length() > 0 &&
            !bean.getFacility().equalsIgnoreCase("All")) {
          where += "location in (select application from " +
              "my_workarea_facility_view ugma where ugma.personnel_id = " +
              bean.getUniqueId() + " and ugma.facility_id = '" +
              bean.getFacility() + "') and ";
        }
        else {
          where +=
              "location in (select application from user_group_member ugm, " +
              "my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
              " and ugm.personnel_id = ugma.personnel_id and " +
              "ugm.user_group_id = 'CreateReport' and ugma.personnel_id = " +
              bean.getUserId() + ") and ";
        }
      }
      else {
        where = where + "location = '" + bean.getWorkArea() + "' and ";
      }
    }

    // begin date
    Integer begD = new Integer(199701);
    try {
      Integer bm = new Integer(bean.getBegmonth());
      bm = new Integer(bm.intValue() + 1);
      String sm = new String(bm.toString());
      if (sm.length() < 2) {
        sm = "0" + sm;
      }
      Integer by = new Integer(bean.getBegyear());
      begD = new Integer(by.toString() + sm);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // end date
    Integer endD = new Integer(209012);
    try {
      Integer em = new Integer(bean.getEndmonth());
      //add 2 to end month so I can use < (less than)
      em = new Integer(em.intValue() + 2);
      Integer ey = new Integer(bean.getEndyear());
      if (em.intValue() > 12) {
        em = new Integer(1);
        ey = new Integer(ey.intValue() + 1);
      }
      String esm = new String(em.toString());
      if (esm.length() < 2) {
        esm = "0" + esm;
      }
      endD = new Integer(ey.toString() + esm);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    where = where + "date_shipped >= to_date('" + begD.toString() +
        "','YYYYMM') and date_shipped < to_date('" + endD.toString() +
        "','YYYYMM') ";

    // order by
    orderBy = "order by ";
    for (int i = 0; i < groupByVector.size(); i++) {
      String s = groupByVector.elementAt(i).toString();
      if (s.equalsIgnoreCase("FACILITY")) {
        s = "facility";
      }
      else if (s.equalsIgnoreCase("WORK_AREA")) {
        s = "location";
      }
      else if (s.equalsIgnoreCase("DEL_POINT")) {
        s = "delivery_point";
      }
      else if (s.equalsIgnoreCase("MONTH")) {
        s = "year_month";
      }
      else if (s.equalsIgnoreCase("CAS_NUM")) {
        s = ""; //not using this anymore
      }
      if (s.length() > 0) {
        orderBy = orderBy + s + ",";
      }
    }
    if (bean.getSortBy() != null &&
        bean.getSortBy().equalsIgnoreCase("DELIVERY_POINT")) {
      orderBy = orderBy + "delivery_point,";
    }
    else if (bean.getSortBy() != null &&
             bean.getSortBy().equalsIgnoreCase("WORK_AREA")) {
      orderBy = orderBy + "location,";
    }
    else if (bean.getSortBy() != null &&
             bean.getSortBy().equalsIgnoreCase("PART_NUM")) {
      orderBy = orderBy + "fac_part_id,";
    }
    if (orderBy.endsWith(",")) {
      orderBy = orderBy.substring(0, orderBy.length() - 1);
    }
    else {
      orderBy = "";
    }

    // group by
    String gb = " group by fac_part_id,facility,location,trade_name," +
        "voc_percent,wt_per_unit ";
//        "voc_percent,wt_per_unit,application_display,delivery_point_display ";

    String select =
        "select fac_part_id,facility,location,sum(qty_shipped) qty_shipped," +
        "trade_name,voc_percent,wt_per_unit,sum(wt_shipped) wt_shipped," +
        "sum(wt_voc) wt_voc,(sum(wt_per_unit)*(voc_percent/100)) mixture_voc ";
//        "application_display,delivery_point_display ";

    if (groupByVector.indexOf("month") > -1) {
      select = select + ", to_char(date_shipped,'yyyymm') year_month ";
      gb = gb + ", to_char(date_shipped,'yyyymm') ";
    }

    from = "from report_material_view ";
    System.out.println("QUERY:" + select + from + where + gb + orderBy);
    return select + from + where + gb + orderBy;
  }

  public DataSet getDataSetForMaterialMatrix(MaterialMatrixReportBean bean) throws
      Exception {
    return getDbManager().select(getQueryForMaterialMatrix(bean));
  }

  private String getQueryForMaterialMatrix(MaterialMatrixReportBean bean) {

    String const0 = "decode(nvl(a.percent,-1000),-1000,null,a.percent)";
    String var1 = "";
    String var2 = "";
    String var3 = "";
    String var4 = "";
    String query = null;

    //defining chemical lists
    int i = 0;
    Iterator iterator = bean.getChemicalReportFields().iterator();
    while (iterator.hasNext()) {
      String temp = (String) iterator.next();
      if (temp != null &&
          temp.trim().length() > 0 &&
          temp.indexOf(" - ") > -1) {
        temp = temp.substring(0, temp.indexOf(" - "));
      }
      else {
        temp = "";
      }
      var1 += "decode(sign(sum(round(decode(a.LIST_id,'" + temp + "'," +
          const0 +
          ",null ),2))),-1,'DATA unavailable',sum(round(decode(a.LIST_id,'" +
          temp + "'," + const0 + ",null ),2)) ) \"" + temp + "\",";
    }
    if (var1.length() > 0) {
      //removing the last commas
      var1 = var1.substring(0, var1.length() - 1);
    }
    //defining report fields
    iterator = bean.getReportFields().iterator();
    while (iterator.hasNext()) {
      String temp = (String) iterator.next();
      if (temp != null && temp.equalsIgnoreCase("Facility")) {
        var2 += "b.facility_id,";
        var4 += "b.facility_id,";
      }
      else if (temp.equalsIgnoreCase("Work Area")) {
        if (bean.getType() != null &&
            bean.getType().equalsIgnoreCase("Part Number")) {
          String tmpApp = " ";
          if (bean.getWorkAreaName() != null &&
              bean.getWorkAreaName().trim().length() > 0 &&
              !bean.getWorkAreaName().startsWith("All")) {
            tmpApp = bean.getWorkAreaName();
          }
          var2 += "'" + tmpApp + "' application,";
        }
        else {
          var2 += "nvl(b.application,'SITE') application,";
          var4 += "b.application,";
        }
      }
      else if (temp.equalsIgnoreCase("Part No.")) {
        var2 += "b.fac_PART_NO,";
        var4 += "b.fac_PART_NO,";
      }
      else if (temp.equalsIgnoreCase("Part Description")) {
        var2 += "a.part_desc,";
        var4 += "a.part_desc,";
      }
      else if (temp.equalsIgnoreCase("Packaging")) {
        var2 += "a.packaging,";
        var4 += "a.packaging,";
      }
      else if (temp.equalsIgnoreCase("Month")) {
        if ( (bean.getType() != null &&
              bean.getType().equalsIgnoreCase("Part Number")) ||
            (bean.getType() != null &&
             bean.getType().equalsIgnoreCase("All Approved"))) {
          var2 += "' ' month1,";
        }
        else {
          var2 += "b.month1,";
          var4 += "b.month1,";
        }
      }
      else if (temp.equalsIgnoreCase("Quantity")) {
        if ( (bean.getType() != null &&
              bean.getType().equalsIgnoreCase("Part Number")) ||
            (bean.getType() != null &&
             bean.getType().equalsIgnoreCase("All Approved"))) {
          var2 += "' ' quantity_ordered,";
        }
        else {
          var3 = ",sum(b.quantity) quantity_ordered";
        }
      }
      else if (temp.equalsIgnoreCase("Manufacturer")) {
        var2 += "fx_mfg_desc(a.item_id)  mfg_desc,";
        var4 += "fx_mfg_desc(a.item_id),";
      }
    }
    if (var2.length() > 0) {
      var2 = var2.substring(0, var2.length() - 1); //removing the last commas
    }
    if (var4.length() > 0) {
      var4 = var4.substring(0, var4.length() - 1); //removing the last commas
    }

    if (bean.getType() != null &&
        bean.getType().equalsIgnoreCase("Part Number")) {
      //5-18-01 users only interested in facility and/or workarea
      boolean facApp = false;
      boolean onlyfac = false;
      if (bean.getReportFields().size() == 1 ||
          bean.getReportFields().size() == 2) {
        if (bean.getReportFields().size() == 1) {
          if (bean.getReportFields().contains("Facility") ||
              bean.getReportFields().contains("Work Area")) {
            facApp = true;
            if (bean.getReportFields().contains("Facility")) {
              onlyfac = true;
            }
          }
        }
        else {
          if (bean.getReportFields().contains("Facility") &&
              bean.getReportFields().contains("Work Area")) {
            facApp = true;
          }
        }
      }
      query = "";
      if (facApp) {
        //NEED to do logic here
        if (bean.getFacilityID().startsWith("All")) {
          if (onlyfac) {
            query += "select " + var2 +
                " from facility b,user_group_member c where";
          }
          else {
            query += "select " + var2 +
                " from fac_loc_app b,user_group_member c where";
          }
        }
        else {
          if (onlyfac) {
            query += "select " + var2 + " from facility b where";
          }
          else {
            query += "select " + var2 + " from fac_loc_app b where";
          }
        }

      }
      else { //everything else
        if (var1.length() < 1) {
          query += "select " + var2 + " from (select distinct item_id,part_desc,packaging from chem_percent_snapshot) a,";
        }
        else {
          query += "select " + var2 + ", " + var1 +
              " from chem_percent_snapshot a,";
        }

        String tmpApp = " ";
        if (bean.getWorkAreaName() != null &&
            bean.getWorkAreaName().trim().length() > 0 &&
            !bean.getWorkAreaName().startsWith("All")) {
          tmpApp = bean.getWorkAreaName();
        }
        query += " (select cf.facility_id,fil.cat_part_no fac_part_no,fil.item_id from catalog_part_item_group fil, catalog_facility cf";
        if (bean.getFacilityID() != null &&
            bean.getFacilityID().startsWith("All")) {
          query += " where fil.catalog_id = cf.catalog_id and fil.status in ('A', 'D')) b, user_group_member c where";
        }
        else {
          query +=
              " where fil.catalog_id = cf.catalog_id and fil.status in ('A', 'D')) b where";
        }
        query += " a.item_id=b.item_id and";
      } //end of else 5-18-01

      if (bean.getPartNo() != null && bean.getPartNo().trim().length() > 0) {
        query += " b.fac_part_no = '" + bean.getPartNo() + "' and";
      }
      if (bean.getFacilityID() != null &&
          bean.getFacilityID().trim().length() > 0 &&
          !bean.getFacilityID().startsWith("All")) {
        query += " b.facility_id = '" + bean.getFacilityID() + "' and";
      }
      //4-30-01
      if (bean.getFacilityID() != null && bean.getFacilityID().startsWith("All")) {
        query += " b.facility_id = c.facility_id and c.user_group_id = 'CreateReport' and c.personnel_id = " +
            bean.getUserID() + " and";
      }

      //removing the last and
      query = query.substring(0, query.length() - 3);
      if (var1.length() > 1) {
        query += " group by " + var4;
      }
      else {
        query += " order by " + var4;
      }
    }
    else if (bean.getType() != null &&
             bean.getType().equalsIgnoreCase("All Used")) {
      query = "";
      if (var1.length() < 1) {
        query += "select " + var2 + " " + var3 + " from (select distinct item_id,part_desc,packaging from chem_percent_snapshot) a,";
      }
      else {
        query += "select " + var2 + ", " + var1 + " " + var3 +
            " from chem_percent_snapshot a,";
      }
      query += " (select facility_id, item_id, fac_part_no,date_shipped,to_char(date_shipped,'mm/yyyy') month1 ,application,quantity from issue_status_detail) b";
      if (bean.getFacilityID() != null && bean.getFacilityID().startsWith("All")) {
        query += " ,user_group_member c";
      }
      query += " where a.item_id=b.item_id and";

      // begin date
      Integer begD = new Integer(199701);
      try {
        Integer bm = new Integer(bean.getBeginMonth());
        bm = new Integer(bm.intValue() + 1);
        String sm = new String(bm.toString());
        if (sm.length() < 2) {
          sm = "0" + sm;
        }
        Integer by = new Integer(bean.getBeginYear());
        begD = new Integer(by.toString() + sm);
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      // end date
      Integer endD = new Integer(209012);
      try {
        Integer em = new Integer(bean.getEndMonth());
        //add 2 to end month so I can use < (less than)
        em = new Integer(em.intValue() + 2);
        Integer ey = new Integer(bean.getEndYear());
        if (em.intValue() > 12) {
          em = new Integer(1);
          ey = new Integer(ey.intValue() + 1);
        }
        String esm = new String(em.toString());
        if (esm.length() < 2) {
          esm = "0" + esm;
        }
        endD = new Integer(ey.toString() + esm);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      query += " b.date_shipped >= to_date('" + begD.toString() +
          "','YYYYMM') and b.date_shipped < to_date('" + endD.toString() +
          "','YYYYMM') and";
      if (bean.getFacilityID() != null &&
          bean.getFacilityID().trim().length() > 0 &&
          !bean.getFacilityID().startsWith("All")) {
        query += " b.facility_id = '" + bean.getFacilityID() + "' and";
      }
      //4-30-01
      if (bean.getFacilityID() != null &&
          bean.getFacilityID().startsWith("All")) {
        query += " b.facility_id = c.facility_id and c.user_group_id = 'CreateReport' and c.personnel_id = " +
            bean.getUserID() + " and";
      }

      if (bean.getWorkAreaName() != null &&
          bean.getWorkAreaName().trim().length() > 0 &&
          !bean.getWorkAreaName().startsWith("All")) {
        if (bean.getWorkAreaName().equalsIgnoreCase("My Work Areas")) {
          if (bean.getFacilityID() != null &&
              bean.getFacilityID().trim().length() > 0 &&
              !bean.getFacilityID().startsWith("All")) {
            query += " b.application in (select application from my_workarea_facility_view ugma where ugma.personnel_id = " +
                bean.getUserID() + " and ugma.facility_id = '" +
                bean.getFacilityID() + "') and";
          }
          else {
            query += " b.application in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
                " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = " +
                bean.getUserID() + ") and";
          }
        }
        else {
          query += " b.application = '" + bean.getWorkAreaName() + "' and";
        }
      }
      //removing the last and
      query = query.substring(0, query.length() - 3);
      if (var1.length() > 1 || var3.length() > 1) {
        query += "group by " + var4 + "";
      }
    }
    else {
      query = "";
      if (var1.length() < 1) {
        query += "select " + var2 + " from (select distinct item_id,part_desc,packaging from chem_percent_snapshot) a,use_approval b, catalog_part_item_group c";
      }
      else {
        query += "select " + var2 + ", " + var1 +
            " from chem_percent_snapshot a,use_approval b, catalog_part_item_group c";
      }
      if (bean.getFacilityID() != null &&
          bean.getFacilityID().startsWith("All")) {
        query += " , user_group_member d";
      }
      query += " where a.item_id = c.item_id and c.status in ('A','D') and c.cat_part_no = b.fac_part_no(+) and c.part_group_no = b.part_group_no(+) and";
      query +=
          " c.catalog_id = b.catalog_id(+) and b.APPROVAL_STATUS(+) = 'approved' and";
      if (bean.getFacilityID() != null &&
          bean.getFacilityID().trim().length() > 0 &&
          !bean.getFacilityID().startsWith("All")) {
        query += " b.facility_id = '" + bean.getFacilityID() + "' and";
      }
      //4-30-01
      if (bean.getFacilityID() != null &&
          bean.getFacilityID().startsWith("All")) {
        query += " b.facility_id = d.facility_id and d.user_group_id = 'CreateReport' and d.personnel_id = " +
            bean.getUserID() + " and";
      }

      if (bean.getWorkAreaName() != null &&
          bean.getWorkAreaName().trim().length() > 0 &&
          !bean.getWorkAreaName().startsWith("All")) {
        if (bean.getWorkAreaName().equalsIgnoreCase("My Work Areas")) {
          if (bean.getFacilityID() != null &&
              bean.getFacilityID().trim().length() > 0 &&
              !bean.getFacilityID().startsWith("All")) {
            query += " b.application in (select application from my_workarea_facility_view ugma where ugma.personnel_id = " +
                bean.getUserID() + " and ugma.facility_id = '" +
                bean.getFacilityID() +
                "' union all select 'All' application from dual) and";
          }
          else {
            query += " b.application in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
                " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = " +
                bean.getUserID() +
                " union all select 'All' application from dual) and";
          }
        }
        else {
          query += " b.application in ('" + bean.getWorkAreaName() +
              "','All') and";
        }
      }
      //removing the last and
      query = query.substring(0, query.length() - 3);
      if (var1.length() > 1) {
        query += "group by " + var4 + "";
      }
    }
    System.out.println("QUERY:" + query);
    return query;
  }

  public DataSet getDataSetForAdhocWaste(AdhocWasteReportBean bean) throws
      Exception {
    String query = getQueryForAdhocWaste(bean);
    //System.out.println("FX QUERY" + query);
    DataSet dataSet = getDbManager().select(query);
    if (log.isInfoEnabled()) {
      log.info("DATASET:" + dataSet);
    }
    Iterator iterator = dataSet.iterator();
    while (iterator.hasNext()) {
      DataSetRow row = (DataSetRow) iterator.next();
      query = row.getString("QUERY");
    }
    dataSet = getDbManager().select(query);
    if (log.isInfoEnabled()) {
      log.info("DATA SET:" + dataSet);
    }
    return dataSet;
  }

  private String getQueryForAdhocWaste(AdhocWasteReportBean bean) {
    String reportField = "";
    String where = "";
    String separator = "|";

    boolean groupBy = false;
    boolean speciated = false;

    Iterator iterator = bean.getReportFields().iterator();
    while (iterator.hasNext()) {
      reportField += (String) iterator.next() + separator;
    }
    //removing the last '|'
    if (reportField.length() > 0) {
      reportField = reportField.substring(0, reportField.length() - 1);
    }

    if (bean.getFacilityID() != null &&
        bean.getFacilityID().trim().length() > 0 &&
        !bean.getFacilityID().startsWith("All")) {
      where += "a.facility_id=''" + bean.getFacilityID() + "'' and ";
    }
    if (bean.getFacilityID() != null &&
        bean.getFacilityID().startsWith("All")) {
      where += "a.facility_id = ugm.facility_id and ugm.user_group_id = ''CreateReport'' and ugm.personnel_id = " +
          bean.getUserID() + " and ";
    }
    if (bean.getWorkAreaName() != null &&
        bean.getWorkAreaName().trim().length() > 0 &&
        !bean.getWorkAreaName().startsWith("All")) {
      where += "a.application like ''%" + bean.getWorkAreaName() + "%'' and ";
    }
    if (bean.getVendor() != null &&
        bean.getVendor().trim().length() > 0 &&
        !bean.getVendor().startsWith("All")) {
      where += "a.vendor_id=''" + bean.getVendor() + "'' and ";
    }
    if (bean.getAccumePt() != null &&
        bean.getAccumePt().trim().length() > 0 &&
        !bean.getAccumePt().startsWith("All")) {
      where += "a.generation_point=''" + bean.getAccumePt() + "'' and ";
    }
    if (bean.getType() != null && bean.getType().equalsIgnoreCase("Profile")) {
      where += "a.vendor_profile_id=''" + bean.getProfileId() + "'' and ";
    }
    if (bean.getMgmtOption() != null &&
        bean.getMgmtOption().trim().length() > 0) {
      where += "a.management_option = ''" + bean.getMgmtOption() + "'' and ";
    }
    //removing last 'and '
    if (where.length() > 1) {
      where = where.substring(0, where.length() - 4);
    }

    //user choose not to see hub waste
    if ("Y".equalsIgnoreCase(bean.getHubWaste())) {
      where += " and a.facility_id = wl.facility_id and a.generation_point = wl.waste_location_id and wl.hub_based = ''N'' ";
    }

    // begin date
    String beginDate = "";
    Integer begD = new Integer(199701);
    try {
      Integer bm = new Integer(bean.getBeginMonth());
      bm = new Integer(bm.intValue() + 1);
      String sm = new String(bm.toString());
      if (sm.length() < 2) {
        sm = "0" + sm;
      }
      Integer by = new Integer(bean.getBeginYear());
      begD = new Integer(by.toString() + sm);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    // end date
    String endDate = "";
    Integer endD = new Integer(209012);
    try {
      Integer em = new Integer(bean.getEndMonth());
      em = new Integer(em.intValue() + 1);
      Integer ey = new Integer(bean.getEndYear());
      if (em.intValue() > 12) {
        em = new Integer(1);
        ey = new Integer(ey.intValue() + 1);
      }
      String esm = new String(em.toString());
      if (esm.length() < 2) {
        esm = "0" + esm;
      }
      endD = new Integer(ey.toString() + esm);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    beginDate = "to_date('" + begD.toString() + "','YYYYMM')";
    endDate = "last_day(to_date('" + endD.toString() + "','YYYYMM'))";

    String additionalFrom = "";
    if (bean.getFacilityID() != null &&
        bean.getFacilityID().startsWith("All")) {
      additionalFrom += "user_group_member ugm,";

      //user choose not to see hub waste
      if ("Y".equalsIgnoreCase(bean.getHubWaste())) {
        additionalFrom += "waste_location wl,";
      }
    }
    else {
      //user choose not to see hub waste
      if ("Y".equalsIgnoreCase(bean.getHubWaste())) {
        additionalFrom += "waste_location wl,";
      }
    }
    //removing the last ','
    if (additionalFrom.length() > 1) {
      additionalFrom = additionalFrom.substring(0, additionalFrom.length() - 1);
    }

    return "select fx_ad_hoc_sql('AdHocWaste','" + reportField + "','" +
        separator + "'," + beginDate + "," + endDate + ",null,'" + where +
        "','" + additionalFrom + "') as QUERY from dual";
  }

  public DataSet getDataSetForMsds(MsdsReportBean bean) throws Exception {
    String query = "select * from MSDS_REVISION_VIEW where FACILITY = '" +
        bean.getFacility() + "'";
    return getDbManager().select(query);
  }
}
