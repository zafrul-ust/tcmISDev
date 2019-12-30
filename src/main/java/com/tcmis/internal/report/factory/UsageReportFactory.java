package com.tcmis.internal.report.factory;

import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.internal.report.beans.ReportParameterBean;

/******************************************************************************
 * This is a specialized factory for creating reports.
 *****************************************************************************/
public class UsageReportFactory
    extends BaseBeanFactory {
  Log log = LogFactory.getLog(this.getClass());

  //constructor
  public UsageReportFactory(DbManager dbManager) {
    super(dbManager);
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
          "round(wt_per_unit, 4) wt_per_unit,sum(qty_shipped) qty_shipped, " +
          "round(sum(wt_shipped), 4) wt_shipped, " +
          "round(sum(chemical_wt_shipped), 4) lbs_reportable, " +
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
          "trade_name,cas_number,percent,round(wt_per_unit, 4) wt_per_unit," +
          "sum(qty_shipped) qty_shipped, round(sum(wt_shipped), 4) wt_shipped, " +
          "round(sum(chemical_wt_shipped), 4) lbs_reportable, delivery_point," +
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
        "trade_name,round(voc_percent, 4) voc_percent,round(wt_per_unit, 4) wt_per_unit,round(sum(wt_shipped), 4) wt_shipped," +
        "round(sum(wt_voc), 4) wt_voc,round((sum(wt_per_unit)*(voc_percent/100)), 4) mixture_voc ";
//        "application_display,delivery_point_display ";

    if (groupByVector.indexOf("month") > -1) {
      select = select + ", to_char(date_shipped,'yyyymm') year_month ";
      gb = gb + ", to_char(date_shipped,'yyyymm') ";
    }

    from = "from report_material_view ";
    return select + from + where + gb + orderBy;
  }
}