package com.tcmis.client.utc.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.utc.beans.Cr658HeaderViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

/******************************************************************************
 * CLASSNAME: Cr658HeaderViewBeanFactory <br>
 * @version: 1.0, May 9, 2005 <br>
 *****************************************************************************/

public class Cr658HeaderViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_RECORD_ID = "RECORD_ID";
  public String ATTRIBUTE_BUKRS = "BUKRS";
  public String ATTRIBUTE_XBLNR = "XBLNR";
  public String ATTRIBUTE_BLDAT = "BLDAT";
  public String ATTRIBUTE_LIFNR = "LIFNR";
  public String ATTRIBUTE_BKTXT = "BKTXT";
  public String ATTRIBUTE_CBLNR = "CBLNR";
  public String ATTRIBUTE_WAERS = "WAERS";
  public String ATTRIBUTE_WRBTR = "WRBTR";
  public String ATTRIBUTE_WMWST = "WMWST";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_ISSUE_COST_REVISION = "ISSUE_COST_REVISION";
  public String ATTRIBUTE_COUNTER = "COUNTER";
  public String ATTRIBUTE_CLOCKID = "CLOCKID";

  //table name
  public String TABLE = "CR658_HEADER_VIEW";

  //constructor
  public Cr658HeaderViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("recordId")) {
      return ATTRIBUTE_RECORD_ID;
    }
    else if (attributeName.equals("bukrs")) {
      return ATTRIBUTE_BUKRS;
    }
    else if (attributeName.equals("xblnr")) {
      return ATTRIBUTE_XBLNR;
    }
    else if (attributeName.equals("bldat")) {
      return ATTRIBUTE_BLDAT;
    }
    else if (attributeName.equals("lifnr")) {
      return ATTRIBUTE_LIFNR;
    }
    else if (attributeName.equals("bktxt")) {
      return ATTRIBUTE_BKTXT;
    }
    else if (attributeName.equals("cblnr")) {
      return ATTRIBUTE_CBLNR;
    }
    else if (attributeName.equals("waers")) {
      return ATTRIBUTE_WAERS;
    }
    else if (attributeName.equals("wrbtr")) {
      return ATTRIBUTE_WRBTR;
    }
    else if (attributeName.equals("wmwst")) {
      return ATTRIBUTE_WMWST;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("issueId")) {
      return ATTRIBUTE_ISSUE_ID;
    }
    else if (attributeName.equals("issueCostRevision")) {
      return ATTRIBUTE_ISSUE_COST_REVISION;
    }
    else if (attributeName.equals("counter")) {
      return ATTRIBUTE_COUNTER;
    }
    else if (attributeName.equals("clockid")) {
      return ATTRIBUTE_CLOCKID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, Cr658HeaderViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(Cr658HeaderViewBean cr658HeaderViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("recordId", "SearchCriterion.EQUALS",
     "" + cr658HeaderViewBean.getRecordId());
    Connection connection = null;
    int result = 0;
    try {
     connection = this.getDbManager().getConnection();
     result = this.delete(criteria, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int delete(Cr658HeaderViewBean cr658HeaderViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("recordId", "SearchCriterion.EQUALS",
     "" + cr658HeaderViewBean.getRecordId());
    return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = delete(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {

    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(Cr658HeaderViewBean cr658HeaderViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(cr658HeaderViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(Cr658HeaderViewBean cr658HeaderViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_RECORD_ID + "," +
     ATTRIBUTE_BUKRS + "," +
     ATTRIBUTE_XBLNR + "," +
     ATTRIBUTE_BLDAT + "," +
     ATTRIBUTE_LIFNR + "," +
     ATTRIBUTE_BKTXT + "," +
     ATTRIBUTE_CBLNR + "," +
     ATTRIBUTE_WAERS + "," +
     ATTRIBUTE_WRBTR + "," +
     ATTRIBUTE_WMWST + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_ISSUE_ID + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "," +
     ATTRIBUTE_COUNTER + ")" +
     " values (" +
     SqlHandler.delimitString(cr658HeaderViewBean.getRecordId()) + "," +
     SqlHandler.delimitString(cr658HeaderViewBean.getBukrs()) + "," +
     SqlHandler.delimitString(cr658HeaderViewBean.getXblnr()) + "," +
       DateHandler.getOracleToDateFunction(cr658HeaderViewBean.getBldat()) + "," +
     SqlHandler.delimitString(cr658HeaderViewBean.getLifnr()) + "," +
     SqlHandler.delimitString(cr658HeaderViewBean.getBktxt()) + "," +
     SqlHandler.delimitString(cr658HeaderViewBean.getCblnr()) + "," +
     SqlHandler.delimitString(cr658HeaderViewBean.getWaers()) + "," +
     cr658HeaderViewBean.getWrbtr() + "," +
     cr658HeaderViewBean.getWmwst() + "," +
     SqlHandler.delimitString(cr658HeaderViewBean.getFacilityId()) + "," +
     cr658HeaderViewBean.getInvoice() + "," +
     cr658HeaderViewBean.getIssueId() + "," +
     cr658HeaderViewBean.getIssueCostRevision() + "," +
     cr658HeaderViewBean.getCounter() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(Cr658HeaderViewBean cr658HeaderViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(cr658HeaderViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(Cr658HeaderViewBean cr658HeaderViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_RECORD_ID + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getRecordId()) + "," +
     ATTRIBUTE_BUKRS + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getBukrs()) + "," +
     ATTRIBUTE_XBLNR + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getXblnr()) + "," +
     ATTRIBUTE_BLDAT + "=" +
       DateHandler.getOracleToDateFunction(cr658HeaderViewBean.getBldat()) + "," +
     ATTRIBUTE_LIFNR + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getLifnr()) + "," +
     ATTRIBUTE_BKTXT + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getBktxt()) + "," +
     ATTRIBUTE_CBLNR + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getCblnr()) + "," +
     ATTRIBUTE_WAERS + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getWaers()) + "," +
     ATTRIBUTE_WRBTR + "=" +
      StringHandler.nullIfZero(cr658HeaderViewBean.getWrbtr()) + "," +
     ATTRIBUTE_WMWST + "=" +
      StringHandler.nullIfZero(cr658HeaderViewBean.getWmwst()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(cr658HeaderViewBean.getFacilityId()) + "," +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(cr658HeaderViewBean.getInvoice()) + "," +
     ATTRIBUTE_ISSUE_ID + "=" +
      StringHandler.nullIfZero(cr658HeaderViewBean.getIssueId()) + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "=" +
       StringHandler.nullIfZero(cr658HeaderViewBean.getIssueCostRevision()) + "," +
     ATTRIBUTE_COUNTER + "=" +
      StringHandler.nullIfZero(cr658HeaderViewBean.getCounter()) + " " +
     "where " + ATTRIBUTE_RECORD_ID + "=" +
      cr658HeaderViewBean.getRecordId();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection cr658HeaderViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    //Cr658ItemViewBeanFactory fac = new Cr658ItemViewBeanFactory(getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      Cr658HeaderViewBean cr658HeaderViewBean = new Cr658HeaderViewBean();
      load(dataSetRow, cr658HeaderViewBean);
      //SearchCriteria itemCriteria = new SearchCriteria("xblnr", SearchCriterion.EQUALS,cr658HeaderViewBean.getXblnr());
      //cr658HeaderViewBean.setCr658ItemViewBeanCollection(fac.select(itemCriteria));
      cr658HeaderViewBeanColl.add(cr658HeaderViewBean);
    }

    return cr658HeaderViewBeanColl;
  }
}
