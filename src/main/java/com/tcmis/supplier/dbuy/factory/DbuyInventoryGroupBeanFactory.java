package com.tcmis.supplier.dbuy.factory;


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
import com.tcmis.supplier.dbuy.beans.DbuyInventoryGroupBean;


/******************************************************************************
 * CLASSNAME: DbuyInventoryGroupBeanFactory <br>
 * @version: 1.0, May 5, 2006 <br>
 *****************************************************************************/


public class DbuyInventoryGroupBeanFactory extends BaseBeanFactory {
   
   Log log = LogFactory.getLog(this.getClass());
   
   //column names
   public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
   
   //table name
   public String TABLE = "DBUY_INVENTORY_GROUP";
   
   
   //constructor
   public DbuyInventoryGroupBeanFactory(DbManager dbManager) {
      super(dbManager);
   }
   
   
   //get column names
   public String getColumnName(String attributeName) {
      if(attributeName.equals("inventoryGroup")) {
         return ATTRIBUTE_INVENTORY_GROUP;
      }
      else {
         return super.getColumnName(attributeName);
      }
   }
   
   
   //get column types
   public int getType(String attributeName) {
      return super.getType(attributeName, DbuyInventoryGroupBean.class);
   }
   
   
   //you need to verify the primary key(s) before uncommenting this
/*
        //delete
        public int delete(DbuyInventoryGroupBean dbuyInventoryGroupBean)
                throws BaseException {
 
                SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
                        "" + dbuyInventoryGroupBean.getInventoryGroup());
 
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
 
 
        public int delete(DbuyInventoryGroupBean dbuyInventoryGroupBean, Connection conn)
                throws BaseException {
 
                SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
                        "" + dbuyInventoryGroupBean.getInventoryGroup());
 
                return delete(criteria, conn);
        }
 */
   
   public int delete(SearchCriteria criteria)
   throws BaseException {
      
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
   
   
   public int delete(SearchCriteria criteria, Connection conn)
   throws BaseException {
      
      String sqlQuery = " delete from " + TABLE + " " +
      getWhereClause(criteria);
      
      return new SqlManager().update(conn, sqlQuery);
   }
   
   
   //you need to verify the primary key(s) before uncommenting this
/*
        //insert
        public int insert(DbuyInventoryGroupBean dbuyInventoryGroupBean)
                throws BaseException {
 
                Connection connection = null;
                int result = 0;
                try {
                        connection = getDbManager().getConnection();
                        result = insert(dbuyInventoryGroupBean, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return result;
        }
 
 
        public int insert(DbuyInventoryGroupBean dbuyInventoryGroupBean, Connection conn)
                throws BaseException {
 
                SqlManager sqlManager = new SqlManager();
 
                String query  =
                        "insert into " + TABLE + " (" +
                        ATTRIBUTE_INVENTORY_GROUP + ")" +
 values (
                        SqlHandler.delimitString(dbuyInventoryGroupBean.getInventoryGroup()) + ")";
 
                return sqlManager.update(conn, query);
        }
 
 
        //update
        public int update(DbuyInventoryGroupBean dbuyInventoryGroupBean)
                throws BaseException {
 
                Connection connection = null;
                int result = 0;
                try {
                        connection = getDbManager().getConnection();
                        result = update(dbuyInventoryGroupBean, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return result;
        }
 
 
        public int update(DbuyInventoryGroupBean dbuyInventoryGroupBean, Connection conn)
                throws BaseException {
 
                String query  = "update " + TABLE + " set " +
                        ATTRIBUTE_INVENTORY_GROUP + "=" +
                                SqlHandler.delimitString(dbuyInventoryGroupBean.getInventoryGroup()) + " " +
                        "where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
                                StringHandler.nullIfZero(dbuyInventoryGroupBean.getInventoryGroup());
 
                return new SqlManager().update(conn, query);
        }
 */
   
   //select
   public Collection select(SearchCriteria criteria)
   throws BaseException {
      
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
   
   public Collection select(SearchCriteria criteria, Connection conn)
   throws BaseException {
      
      Collection dbuyInventoryGroupBeanColl = new Vector();
      
      String query = "select * from " + TABLE + " " +
      getWhereClause(criteria);
      
      DataSet dataSet = new SqlManager().select(conn, query);
      
      Iterator dataIter = dataSet.iterator();
      
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow)dataIter.next();
         DbuyInventoryGroupBean dbuyInventoryGroupBean = new DbuyInventoryGroupBean();
         load(dataSetRow, dbuyInventoryGroupBean);
         dbuyInventoryGroupBeanColl.add(dbuyInventoryGroupBean);
      }
      
      return dbuyInventoryGroupBeanColl;
   }
   
   public Collection select()
   throws BaseException {
      
      
      Connection connection = null;
      Collection dbuyInventoryGroupBeanColl = new Vector();
      try {
         connection = this.getDbManager().getConnection();
         String query = "select distinct inventory_group from dbuy_contract where status = 'DBUY'";
         
         DataSet dataSet = new SqlManager().select(connection, query);
         
         Iterator dataIter = dataSet.iterator();
         
         while (dataIter.hasNext()) {
            DataSetRow dataSetRow = (DataSetRow)dataIter.next();
            DbuyInventoryGroupBean dbuyInventoryGroupBean = new DbuyInventoryGroupBean();
            load(dataSetRow, dbuyInventoryGroupBean);
            dbuyInventoryGroupBeanColl.add(dbuyInventoryGroupBean);
         }
      }
      finally {
         this.getDbManager().returnConnection(connection);
      }
      return dbuyInventoryGroupBeanColl;
      
   }
}