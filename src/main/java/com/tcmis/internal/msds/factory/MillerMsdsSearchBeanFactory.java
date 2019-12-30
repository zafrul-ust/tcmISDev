package com.tcmis.internal.msds.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.msds.beans.MillerMsdsSearchBean;
import com.tcmis.internal.msds.beans.MsdsInputBean;

/******************************************************************************
 * CLASSNAME: MillerMsdsSearchBeanFactory <br>
 * @version: 1.0, Jun 1, 2005 <br>
 *****************************************************************************/

public class MillerMsdsSearchBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CUSTOMER_MSDS_NO = "CUSTOMER_MSDS_NO";
  public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
  public String ATTRIBUTE_CAT_PART_NO_STRING = "CAT_PART_NO_STRING";
  public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
  public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
  public String ATTRIBUTE_CONTENT = "CONTENT";
  public String ATTRIBUTE_ON_LINE = "ON_LINE";
  public String ATTRIBUTE_DEPARTMENT = "DEPARTMENT";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_HAZARD_CLASSIFICATION = "HAZARD_CLASSIFICATION";

  //table name
  public String TABLE = "MILLER_MSDS_SEARCH";

  //constructor
  public MillerMsdsSearchBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("customerMsdsNo")) {
      return ATTRIBUTE_CUSTOMER_MSDS_NO;
    }
    else if (attributeName.equals("tradeName")) {
      return ATTRIBUTE_TRADE_NAME;
    }
    else if (attributeName.equals("catPartNoString")) {
      return ATTRIBUTE_CAT_PART_NO_STRING;
    }
    else if (attributeName.equals("manufacturer")) {
      return ATTRIBUTE_MANUFACTURER;
    }
    else if (attributeName.equals("materialId")) {
      return ATTRIBUTE_MATERIAL_ID;
    }
    else if (attributeName.equals("content")) {
      return ATTRIBUTE_CONTENT;
    }
    else if (attributeName.equals("onLine")) {
      return ATTRIBUTE_ON_LINE;
    }
    else if (attributeName.equals("department")) {
      return ATTRIBUTE_DEPARTMENT;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("hazardClassification")) {
      return ATTRIBUTE_HAZARD_CLASSIFICATION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, MillerMsdsSearchBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(MillerMsdsSearchBean millerMsdsSearchBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("customerMsdsNo", "SearchCriterion.EQUALS",
     "" + millerMsdsSearchBean.getCustomerMsdsNo());
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
       public int delete(MillerMsdsSearchBean millerMsdsSearchBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("customerMsdsNo", "SearchCriterion.EQUALS",
     "" + millerMsdsSearchBean.getCustomerMsdsNo());
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
   public int insert(MillerMsdsSearchBean millerMsdsSearchBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(millerMsdsSearchBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(MillerMsdsSearchBean millerMsdsSearchBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_CUSTOMER_MSDS_NO + "," +
     ATTRIBUTE_TRADE_NAME + "," +
     ATTRIBUTE_CAT_PART_NO_STRING + "," +
     ATTRIBUTE_MANUFACTURER + "," +
     ATTRIBUTE_MATERIAL_ID + "," +
     ATTRIBUTE_CONTENT + "," +
     ATTRIBUTE_ON_LINE + "," +
     ATTRIBUTE_DEPARTMENT + "," +
     ATTRIBUTE_STATUS + ")" +
     " values (" +
     SqlHandler.delimitString(millerMsdsSearchBean.getCustomerMsdsNo()) + "," +
     SqlHandler.delimitString(millerMsdsSearchBean.getTradeName()) + "," +
       SqlHandler.delimitString(millerMsdsSearchBean.getCatPartNoString()) + "," +
     SqlHandler.delimitString(millerMsdsSearchBean.getManufacturer()) + "," +
     SqlHandler.delimitString(millerMsdsSearchBean.getMaterialId()) + "," +
     SqlHandler.delimitString(millerMsdsSearchBean.getContent()) + "," +
     SqlHandler.delimitString(millerMsdsSearchBean.getOnLine()) + "," +
     SqlHandler.delimitString(millerMsdsSearchBean.getDepartment()) + "," +
     SqlHandler.delimitString(millerMsdsSearchBean.getStatus()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(MillerMsdsSearchBean millerMsdsSearchBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(millerMsdsSearchBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(MillerMsdsSearchBean millerMsdsSearchBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CUSTOMER_MSDS_NO + "=" +
       SqlHandler.delimitString(millerMsdsSearchBean.getCustomerMsdsNo()) + "," +
     ATTRIBUTE_TRADE_NAME + "=" +
      SqlHandler.delimitString(millerMsdsSearchBean.getTradeName()) + "," +
     ATTRIBUTE_CAT_PART_NO_STRING + "=" +
       SqlHandler.delimitString(millerMsdsSearchBean.getCatPartNoString()) + "," +
     ATTRIBUTE_MANUFACTURER + "=" +
      SqlHandler.delimitString(millerMsdsSearchBean.getManufacturer()) + "," +
     ATTRIBUTE_MATERIAL_ID + "=" +
      SqlHandler.delimitString(millerMsdsSearchBean.getMaterialId()) + "," +
     ATTRIBUTE_CONTENT + "=" +
      SqlHandler.delimitString(millerMsdsSearchBean.getContent()) + "," +
     ATTRIBUTE_ON_LINE + "=" +
      SqlHandler.delimitString(millerMsdsSearchBean.getOnLine()) + "," +
     ATTRIBUTE_DEPARTMENT + "=" +
      SqlHandler.delimitString(millerMsdsSearchBean.getDepartment()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(millerMsdsSearchBean.getStatus()) + " " +
     "where " + ATTRIBUTE_CUSTOMER_MSDS_NO + "=" +
      millerMsdsSearchBean.getCustomerMsdsNo();
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

    Collection millerMsdsSearchBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MillerMsdsSearchBean millerMsdsSearchBean = new MillerMsdsSearchBean();
      load(dataSetRow, millerMsdsSearchBean);
      millerMsdsSearchBeanColl.add(millerMsdsSearchBean);
    }

    return millerMsdsSearchBeanColl;
  }

  public Collection select(MsdsInputBean bean) throws
      BaseException {
    Connection connection = null;
    Collection millerMsdsSearchBeanColl = null;
    Collection in = new Vector(7);
    Collection out = new Vector(1);
    in.add(bean.getFacilityId());
    in.add(bean.getDepartment());
    in.add(bean.getBuilding());
    in.add(bean.getFloor());
    in.add("order by " + bean.getSortBy());
    in.add(bean.getSearch());
    in.add(bean.getStatus());
    in.add(bean.getHazardClassification());

    out.add(new Integer(java.sql.Types.VARCHAR));
    out = getDbManager().doProcedure("PR_MSDS_QUERY", in, out);

    Iterator iterator = out.iterator();
    String query = null;
    while (iterator.hasNext()) {
      query = (String) iterator.next(); ;
    }
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    try {
      connection = this.getDbManager().getConnection();
      millerMsdsSearchBeanColl = new Vector();
      DataSet dataSet = new SqlManager().select(connection, query);
      Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        MillerMsdsSearchBean millerMsdsSearchBean = new MillerMsdsSearchBean();
        load(dataSetRow, millerMsdsSearchBean);
        millerMsdsSearchBeanColl.add(millerMsdsSearchBean);
      }
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return millerMsdsSearchBeanColl;
  }

}