package com.tcmis.client.catalog.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.catalog.beans.PersonnelCatalogViewBean;

/******************************************************************************
 * CLASSNAME: PersonnelCatalogViewBeanFactory <br>
 * @version: 1.0, Dec 5, 2006 <br>
 *****************************************************************************/

public class PersonnelCatalogViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";

  //table name
  public String TABLE = "PERSONNEL_CATALOG_VIEW";

  //constructor
  public PersonnelCatalogViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PersonnelCatalogViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(PersonnelCatalogViewBean personnelCatalogViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + personnelCatalogViewBean.getPersonnelId());
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
   public int delete(PersonnelCatalogViewBean personnelCatalogViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + personnelCatalogViewBean.getPersonnelId());
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
   public int insert(PersonnelCatalogViewBean personnelCatalogViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(personnelCatalogViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(PersonnelCatalogViewBean personnelCatalogViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_CATALOG_ID + ")" +
     " values (" +
     personnelCatalogViewBean.getPersonnelId() + "," +
     SqlHandler.delimitString(personnelCatalogViewBean.getCatalogId()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(PersonnelCatalogViewBean personnelCatalogViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(personnelCatalogViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(PersonnelCatalogViewBean personnelCatalogViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
       StringHandler.nullIfZero(personnelCatalogViewBean.getPersonnelId()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(personnelCatalogViewBean.getCatalogId()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      personnelCatalogViewBean.getPersonnelId();
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
    Collection personnelCatalogViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PersonnelCatalogViewBean personnelCatalogViewBean = new
          PersonnelCatalogViewBean();
      load(dataSetRow, personnelCatalogViewBean);
      personnelCatalogViewBeanColl.add(personnelCatalogViewBean);
    }
    return personnelCatalogViewBeanColl;
  }

  public Collection selectDistinct(SearchCriteria criteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectDistinct(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectDistinct(SearchCriteria criteria, Connection conn) throws
      BaseException {
    Collection personnelCatalogViewBeanColl = new Vector();
    String query = "select distinct * from " + TABLE + " " +
        getWhereClause(criteria) + " ORDER BY " + ATTRIBUTE_CATALOG_ID;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PersonnelCatalogViewBean personnelCatalogViewBean = new
          PersonnelCatalogViewBean();
      load(dataSetRow, personnelCatalogViewBean);
      personnelCatalogViewBeanColl.add(personnelCatalogViewBean);
    }
    return personnelCatalogViewBeanColl;
  }
}