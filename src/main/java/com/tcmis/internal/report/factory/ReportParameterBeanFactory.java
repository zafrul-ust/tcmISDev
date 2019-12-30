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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.report.beans.ReportParameterBean;

/******************************************************************************
 * CLASSNAME: ReportParameterBeanFactory <br>
 * @version: 1.0, Feb 26, 2004 <br>
 *****************************************************************************/

public class ReportParameterBeanFactory
    extends BaseBeanFactory {

  //column names
  public String ATTRIBUTE_FILE_TYPE = "FILE_TYPE";
  public String ATTRIBUTE_ACTION = "ACTION";
  public String ATTRIBUTE_FACILITY = "FACILITY";
  public String ATTRIBUTE_WORK_AREA = "WORK_AREA";
  public String ATTRIBUTE_BEGMONTH = "BEGMONTH";
  public String ATTRIBUTE_BEGYEAR = "BEGYEAR";
  public String ATTRIBUTE_ENDMONTH = "ENDMONTH";
  public String ATTRIBUTE_ENDYEAR = "ENDYEAR";
  public String ATTRIBUTE_SEARCH_TYPE = "SEARCH_TYPE";
  public String ATTRIBUTE_LIST_ID = "LIST_ID";
  public String ATTRIBUTE_KEYWORD = "KEYWORD";
  public String ATTRIBUTE_CAS_NUM = "CAS_NUM";
  public String ATTRIBUTE_CHEM_DESC = "CHEM_DESC";
  public String ATTRIBUTE_USER_ID = "USER_ID";
  public String ATTRIBUTE_REPORT_NAME = "REPORT_NAME";
  public String ATTRIBUTE_SORT_BY = "SORT_BY";
  public String ATTRIBUTE_GROUP_BY = "GROUP_BY";
  public String ATTRIBUTE_CURRENT_SCREEN = "CURRENT_SCREEN";
  public String ATTRIBUTE_GROUP_MATRIX = "GROUP_MATRIX";
  public String ATTRIBUTE_UNIQUE_ID = "UNIQUE_ID";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_DATE_REQUESTED = "DATE_REQUESTED";

  //sequence and table names
  public String SEQUENCE = "FILE_TYPE";
  public String TABLE = "REPORT_PARAMETER";

  Log log = LogFactory.getLog(this.getClass());

  private String client;

  //constructor
  public ReportParameterBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("fileType")) {
      return ATTRIBUTE_FILE_TYPE;
    }
    if (attributeName.equals("action")) {
      return ATTRIBUTE_ACTION;
    }
    if (attributeName.equals("facility")) {
      return ATTRIBUTE_FACILITY;
    }
    if (attributeName.equals("workArea")) {
      return ATTRIBUTE_WORK_AREA;
    }
    if (attributeName.equals("begmonth")) {
      return ATTRIBUTE_BEGMONTH;
    }
    if (attributeName.equals("begyear")) {
      return ATTRIBUTE_BEGYEAR;
    }
    if (attributeName.equals("endmonth")) {
      return ATTRIBUTE_ENDMONTH;
    }
    if (attributeName.equals("endyear")) {
      return ATTRIBUTE_ENDYEAR;
    }
    if (attributeName.equals("searchType")) {
      return ATTRIBUTE_SEARCH_TYPE;
    }
    if (attributeName.equals("listId")) {
      return ATTRIBUTE_LIST_ID;
    }
    if (attributeName.equals("keyword")) {
      return ATTRIBUTE_KEYWORD;
    }
    if (attributeName.equals("casNum")) {
      return ATTRIBUTE_CAS_NUM;
    }
    if (attributeName.equals("chemDesc")) {
      return ATTRIBUTE_CHEM_DESC;
    }
    if (attributeName.equals("userId")) {
      return ATTRIBUTE_USER_ID;
    }
    if (attributeName.equals("reportName")) {
      return ATTRIBUTE_REPORT_NAME;
    }
    if (attributeName.equals("sortBy")) {
      return ATTRIBUTE_SORT_BY;
    }
    if (attributeName.equals("groupBy")) {
      return ATTRIBUTE_GROUP_BY;
    }
    if (attributeName.equals("currentScreen")) {
      return ATTRIBUTE_CURRENT_SCREEN;
    }
    if (attributeName.equals("groupMatrix")) {
      return ATTRIBUTE_GROUP_MATRIX;
    }
    if (attributeName.equals("uniqueId")) {
      return ATTRIBUTE_UNIQUE_ID;
    }
    if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    if (attributeName.equals("dateRequested")) {
      return ATTRIBUTE_DATE_REQUESTED;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ReportParameterBean.class);
  }

  /*
//delete
   public int delete(ReportParameterBean reportParameterBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("fileType", "=",
     "" + reportParameterBean.getFileType());
    return delete(criteria);
   }
   public int delete(ReportParameterBean reportParameterBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("fileType", "=",
     "" + reportParameterBean.getFileType());
    return delete(criteria, conn);
   }
   public int delete(SearchCriteria criteria)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int rowCount = delete(criteria, connection);
    getDbManager().returnConnection(connection);
    return rowCount;
   }
   public int delete(SearchCriteria criteria, Connection conn)
    throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
     getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
   }
//insert
   public int insert(ReportParameterBean reportParameterBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int rowCount = insert(reportParameterBean, connection);
    updateAuditFields(reportParameterBean);
    getDbManager().returnConnection(connection);
    return rowCount;
   }
   public int insert(ReportParameterBean reportParameterBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    reportParameterBean.
     setFileType(sqlManager.getOracleSequence(conn, SEQUENCE));
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_FILE_TYPE + "," +
     ATTRIBUTE_ACTION + "," +
     ATTRIBUTE_FACILITY + "," +
     ATTRIBUTE_WORK_AREA + "," +
     ATTRIBUTE_BEGMONTH + "," +
     ATTRIBUTE_BEGYEAR + "," +
     ATTRIBUTE_ENDMONTH + "," +
     ATTRIBUTE_ENDYEAR + "," +
     ATTRIBUTE_SEARCH_TYPE + "," +
     ATTRIBUTE_LIST_ID + "," +
     ATTRIBUTE_KEYWORD + "," +
     ATTRIBUTE_CAS_NUM + "," +
     ATTRIBUTE_CHEM_DESC + "," +
     ATTRIBUTE_USER_ID + "," +
     ATTRIBUTE_REPORT_NAME + "," +
     ATTRIBUTE_SORT_BY + "," +
     ATTRIBUTE_GROUP_BY + "," +
     ATTRIBUTE_CURRENT_SCREEN + "," +
     ATTRIBUTE_GROUP_MATRIX + "," +
     ATTRIBUTE_UNIQUE_ID + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_DATE_REQUESTED + ")" +
     SqlHandler.delimitString(reportParameterBean.getFileType()) + "," +
     SqlHandler.delimitString(reportParameterBean.getAction()) + "," +
     SqlHandler.delimitString(reportParameterBean.getFacility()) + "," +
     SqlHandler.delimitString(reportParameterBean.getWorkArea()) + "," +
     SqlHandler.delimitString(reportParameterBean.getBegmonth()) + "," +
     SqlHandler.delimitString(reportParameterBean.getBegyear()) + "," +
     SqlHandler.delimitString(reportParameterBean.getEndmonth()) + "," +
     SqlHandler.delimitString(reportParameterBean.getEndyear()) + "," +
     SqlHandler.delimitString(reportParameterBean.getSearchType()) + "," +
     SqlHandler.delimitString(reportParameterBean.getListId()) + "," +
     SqlHandler.delimitString(reportParameterBean.getKeyword()) + "," +
     SqlHandler.delimitString(reportParameterBean.getCasNum()) + "," +
     SqlHandler.delimitString(reportParameterBean.getChemDesc()) + "," +
     SqlHandler.delimitString(reportParameterBean.getUserId()) + "," +
     SqlHandler.delimitString(reportParameterBean.getReportName()) + "," +
     SqlHandler.delimitString(reportParameterBean.getSortBy()) + "," +
     SqlHandler.delimitString(reportParameterBean.getGroupBy()) + "," +
     SqlHandler.delimitString(reportParameterBean.getCurrentScreen()) + "," +
     SqlHandler.delimitString(reportParameterBean.getGroupMatrix()) + "," +
     SqlHandler.delimitString(reportParameterBean.getUniqueId()) + "," +
     SqlHandler.delimitString(reportParameterBean.getStatus()) + "," +
     DateHandler.getOracleToDateFunction(reportParameterBean.getDateRequested()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(ReportParameterBean reportParameterBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int rowCount = update(reportParameterBean, connection);
    updateAuditFields(reportParameterBean);
    getDbManager().returnConnection(connection);
    return rowCount;
   }
   public int update(ReportParameterBean reportParameterBean, Connection conn)
    throws BaseException {
    String query  = 			"update " + TABLE + " set " +
     ATTRIBUTE_FILE_TYPE + "=" +
      SqlHandler.delimitString(reportParameterBean.getFileType()) + "," +
     ATTRIBUTE_ACTION + "=" +
      SqlHandler.delimitString(reportParameterBean.getAction()) + "," +
     ATTRIBUTE_FACILITY + "=" +
      SqlHandler.delimitString(reportParameterBean.getFacility()) + "," +
     ATTRIBUTE_WORK_AREA + "=" +
      SqlHandler.delimitString(reportParameterBean.getWorkArea()) + "," +
     ATTRIBUTE_BEGMONTH + "=" +
      SqlHandler.delimitString(reportParameterBean.getBegmonth()) + "," +
     ATTRIBUTE_BEGYEAR + "=" +
      SqlHandler.delimitString(reportParameterBean.getBegyear()) + "," +
     ATTRIBUTE_ENDMONTH + "=" +
      SqlHandler.delimitString(reportParameterBean.getEndmonth()) + "," +
     ATTRIBUTE_ENDYEAR + "=" +
      SqlHandler.delimitString(reportParameterBean.getEndyear()) + "," +
     ATTRIBUTE_SEARCH_TYPE + "=" +
      SqlHandler.delimitString(reportParameterBean.getSearchType()) + "," +
     ATTRIBUTE_LIST_ID + "=" +
      SqlHandler.delimitString(reportParameterBean.getListId()) + "," +
     ATTRIBUTE_KEYWORD + "=" +
      SqlHandler.delimitString(reportParameterBean.getKeyword()) + "," +
     ATTRIBUTE_CAS_NUM + "=" +
      SqlHandler.delimitString(reportParameterBean.getCasNum()) + "," +
     ATTRIBUTE_CHEM_DESC + "=" +
      SqlHandler.delimitString(reportParameterBean.getChemDesc()) + "," +
     ATTRIBUTE_USER_ID + "=" +
      SqlHandler.delimitString(reportParameterBean.getUserId()) + "," +
     ATTRIBUTE_REPORT_NAME + "=" +
      SqlHandler.delimitString(reportParameterBean.getReportName()) + "," +
     ATTRIBUTE_SORT_BY + "=" +
      SqlHandler.delimitString(reportParameterBean.getSortBy()) + "," +
     ATTRIBUTE_GROUP_BY + "=" +
      SqlHandler.delimitString(reportParameterBean.getGroupBy()) + "," +
     ATTRIBUTE_CURRENT_SCREEN + "=" +
      SqlHandler.delimitString(reportParameterBean.getCurrentScreen()) + "," +
     ATTRIBUTE_GROUP_MATRIX + "=" +
      SqlHandler.delimitString(reportParameterBean.getGroupMatrix()) + "," +
     ATTRIBUTE_UNIQUE_ID + "=" +
      SqlHandler.delimitString(reportParameterBean.getUniqueId()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(reportParameterBean.getStatus()) + "," +
     ATTRIBUTE_DATE_REQUESTED + "=" +
      DateHandler.getOracleToDateFunction(reportParameterBean.getDateRequested()) + " " +
     "where " + ATTRIBUTE_FILE_TYPE + "=" +
      StringHandler.nullIfZero(reportParameterBean.getFileType());
    return new SqlManager().update(conn,
     query,
     TABLE,
     ATTRIBUTE_FILE_TYPE,
     reportParameterBean.getFileType(),
     reportParameterBean.getModifyDate());
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws
      Exception {

    Collection reportParameterBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = getDbManager().select(query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ReportParameterBean reportParameterBean = new ReportParameterBean();
      load(dataSetRow, reportParameterBean);
      reportParameterBeanColl.add(reportParameterBean);
    }

    return reportParameterBeanColl;
  }

  public DataSet getDataSet(ReportParameterBean bean) throws Exception {
    return getDbManager().select(getWhereClause(bean));
  }

  private String getWhereClause(ReportParameterBean bean) {

    //for some reason the group_by column contains comma separated strings...
    //parse it and store the strings in a vector
    String groupByString = bean.getGroupBy();
    StringTokenizer tokenizer = new StringTokenizer(groupByString, ",");
    Vector groupByVector = new Vector(tokenizer.countTokens());
    while (tokenizer.hasMoreTokens()) {
      groupByVector.add(tokenizer.nextToken());
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
      orderBy = orderBy + s + ", ";
    }
    if (bean.getSortBy() != null &&
        bean.getSortBy().equalsIgnoreCase("DELIVERY_POINT")) {
      orderBy = orderBy + "delivery_point ";
    }
    else if (bean.getSortBy() != null &&
             bean.getSortBy().equalsIgnoreCase("WORK_AREA")) {
      orderBy = orderBy + "location ";
    }
    else if (bean.getSortBy() != null &&
             bean.getSortBy().equalsIgnoreCase("PART_NUM")) {
      orderBy = orderBy + "fac_part_id ";
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
          "sum(chemical_wt_shipped) lbs_reportable, delivery_point," +
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

  /*
//check
   public boolean check(SearchCriteria criteria)
    throws BaseException {
    String query = "select * from " + TABLE + " " +
     getWhereClause(criteria);
    return getDbManager().check(query);
   }
//update audit fields
   public void updateAuditFields(ReportParameterBean reportParameterBean)
    throws BaseException {
    super.updateAuditFields(reportParameterBean,
     TABLE,
     ATTRIBUTE_FILE_TYPE,
     reportParameterBean.getFileType());
   }
   */
}