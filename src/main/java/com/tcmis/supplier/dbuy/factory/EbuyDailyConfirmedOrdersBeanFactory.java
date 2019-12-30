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
import com.tcmis.supplier.dbuy.beans.EbuyDailyConfirmedOrdersBean;


/******************************************************************************
 * CLASSNAME: EbuyDailyConfirmedOrdersBeanFactory <br>
 * @version: 1.0, Feb 16, 2007 <br>
 *****************************************************************************/


public class EbuyDailyConfirmedOrdersBeanFactory extends BaseBeanFactory {
   
   Log log = LogFactory.getLog(this.getClass());
   
   //column names
   public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
   public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
   public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
   public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
   public String ATTRIBUTE_SHIP_DATE = "SHIP_DATE";
   public String ATTRIBUTE_DATE_CONFIRMED = "DATE_CONFIRMED";
   
   //table name
   public String TABLE = "EBUY_DAILY_CONFIRMED_ORDERS";
   
   
   //constructor
   public EbuyDailyConfirmedOrdersBeanFactory(DbManager dbManager) {
      super(dbManager);
   }
   
   
   //get column names
   public String getColumnName(String attributeName) {
      if(attributeName.equals("supplyPath")) {
         return ATTRIBUTE_SUPPLY_PATH;
      }
      else if(attributeName.equals("supplier")) {
         return ATTRIBUTE_SUPPLIER;
      }
      else if(attributeName.equals("radianPo")) {
         return ATTRIBUTE_RADIAN_PO;
      }
      else if(attributeName.equals("promisedDate")) {
         return ATTRIBUTE_PROMISED_DATE;
      }
      else if(attributeName.equals("shipDate")) {
         return ATTRIBUTE_SHIP_DATE;
      }
      else if(attributeName.equals("dateConfirmed")) {
         return ATTRIBUTE_DATE_CONFIRMED;
      }
      else {
         return super.getColumnName(attributeName);
      }
   }
   
   
   //get column types
   public int getType(String attributeName) {
      return super.getType(attributeName, EbuyDailyConfirmedOrdersBean.class);
   }
   
   
   //you need to verify the primary key(s) before uncommenting this
/*
        //delete
        public int delete(EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean)
                throws BaseException {
 
                SearchCriteria criteria = new SearchCriteria("supplyPath", "SearchCriterion.EQUALS",
                        "" + ebuyDailyConfirmedOrdersBean.getSupplyPath());
 
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
 
 
        public int delete(EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean, Connection conn)
                throws BaseException {
 
                SearchCriteria criteria = new SearchCriteria("supplyPath", "SearchCriterion.EQUALS",
                        "" + ebuyDailyConfirmedOrdersBean.getSupplyPath());
 
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
        public int insert(EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean)
                throws BaseException {
 
                Connection connection = null;
                int result = 0;
                try {
                        connection = getDbManager().getConnection();
                        result = insert(ebuyDailyConfirmedOrdersBean, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return result;
        }
 
 
        public int insert(EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean, Connection conn)
                throws BaseException {
 
                SqlManager sqlManager = new SqlManager();
 
                String query  =
                        "insert into " + TABLE + " (" +
                        ATTRIBUTE_SUPPLY_PATH + "," +
                        ATTRIBUTE_SUPPLIER + "," +
                        ATTRIBUTE_RADIAN_PO + "," +
                        ATTRIBUTE_PROMISED_DATE + "," +
                        ATTRIBUTE_SHIP_DATE + "," +
                        ATTRIBUTE_DATE_CONFIRMED + ")" +
 values (
                        SqlHandler.delimitString(ebuyDailyConfirmedOrdersBean.getSupplyPath()) + "," +
                        SqlHandler.delimitString(ebuyDailyConfirmedOrdersBean.getSupplier()) + "," +
                        StringHandler.nullIfZero(ebuyDailyConfirmedOrdersBean.getRadianPo()) + "," +
                        DateHandler.getOracleToDateFunction(ebuyDailyConfirmedOrdersBean.getPromisedDate()) + "," +
                        DateHandler.getOracleToDateFunction(ebuyDailyConfirmedOrdersBean.getShipDate()) + "," +
                        DateHandler.getOracleToDateFunction(ebuyDailyConfirmedOrdersBean.getDateConfirmed()) + ")";
 
                return sqlManager.update(conn, query);
        }
 
 
        //update
        public int update(EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean)
                throws BaseException {
 
                Connection connection = null;
                int result = 0;
                try {
                        connection = getDbManager().getConnection();
                        result = update(ebuyDailyConfirmedOrdersBean, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return result;
        }
 
 
        public int update(EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean, Connection conn)
                throws BaseException {
 
                String query  = "update " + TABLE + " set " +
                        ATTRIBUTE_SUPPLY_PATH + "=" +
                                SqlHandler.delimitString(ebuyDailyConfirmedOrdersBean.getSupplyPath()) + "," +
                        ATTRIBUTE_SUPPLIER + "=" +
                                SqlHandler.delimitString(ebuyDailyConfirmedOrdersBean.getSupplier()) + "," +
                        ATTRIBUTE_RADIAN_PO + "=" +
                                StringHandler.nullIfZero(ebuyDailyConfirmedOrdersBean.getRadianPo()) + "," +
                        ATTRIBUTE_PROMISED_DATE + "=" +
                                DateHandler.getOracleToDateFunction(ebuyDailyConfirmedOrdersBean.getPromisedDate()) + "," +
                        ATTRIBUTE_SHIP_DATE + "=" +
                                DateHandler.getOracleToDateFunction(ebuyDailyConfirmedOrdersBean.getShipDate()) + "," +
                        ATTRIBUTE_DATE_CONFIRMED + "=" +
                                DateHandler.getOracleToDateFunction(ebuyDailyConfirmedOrdersBean.getDateConfirmed()) + " " +
                        "where " + ATTRIBUTE_SUPPLY_PATH + "=" +
                                StringHandler.nullIfZero(ebuyDailyConfirmedOrdersBean.getSupplyPath());
 
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
      
      Collection ebuyDailyConfirmedOrdersBeanColl = new Vector();
      
      String query = "select * from " + TABLE + " " +
      getWhereClause(criteria);
      
      DataSet dataSet = new SqlManager().select(conn, query);
      
      Iterator dataIter = dataSet.iterator();
      
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow)dataIter.next();
         EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean = new EbuyDailyConfirmedOrdersBean();
         load(dataSetRow, ebuyDailyConfirmedOrdersBean);
         ebuyDailyConfirmedOrdersBeanColl.add(ebuyDailyConfirmedOrdersBean);
      }
      
      return ebuyDailyConfirmedOrdersBeanColl;
   }
   
   public Collection selectEbuys()
   throws BaseException {
      
      Connection connection = null;
      Collection c = null;
      try {
         connection = this.getDbManager().getConnection();
         c = selectEbuys(connection);
      }
      finally {
         this.getDbManager().returnConnection(connection);
      }
      return c;
   }
   
   public Collection selectEbuysTime(String weeks)
   throws BaseException {
      
      Connection connection = null;
      Collection c = null;
      int weeksNum = 2;  // default time to look up
      if (weeks!=null && weeks.length()>0) {
         try {
            weeksNum = Integer.parseInt(weeks);
         } catch (NumberFormatException nfe) {
            log.info("illegal number passed in for # of weeks: " + weeks);
         }
      }
      try {
         connection = this.getDbManager().getConnection();
         c = selectEbuys(connection,weeksNum);
      }
      finally {
         this.getDbManager().returnConnection(connection);
      }
      return c;
   }
   
   
   public Collection selectEbuys(Connection conn)
   throws BaseException {
      Collection ebuyDailyConfirmedOrdersBeanColl = new Vector();
      
      String query = "select bo.supply_path,sup.supplier_name supplier, po.radian_po, ";
      query += "line.PROMISED_DATE, line.vendor_ship_date ship_date, line.date_confirmed ";
      query += "from po, po_line line, supplier sup, buy_order bo  ";
      query += "where (line.date_confirmed > (SYSDATE-1))   ";
      query += "and po.radian_po = line.radian_po  ";
      query += "and po.supplier = sup.supplier  ";
      query += "and po.radian_po = bo.radian_po  ";
      query += "and (bo.supply_path = 'Wbuy' or bo.supply_path='Dbuy')     ";
      query += "  and line.vendor_ship_date is not null ";
      
      DataSet dataSet = new SqlManager().select(conn, query);
      
      Iterator dataIter = dataSet.iterator();
      
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow)dataIter.next();
         EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean = new EbuyDailyConfirmedOrdersBean();
         load(dataSetRow, ebuyDailyConfirmedOrdersBean);
         ebuyDailyConfirmedOrdersBeanColl.add(ebuyDailyConfirmedOrdersBean);
      }
      
      return ebuyDailyConfirmedOrdersBeanColl;
   }
   
   public Collection selectEbuys(Connection conn, int weeks)
   throws BaseException {           
      Collection ebuyDailyConfirmedOrdersBeanColl = new Vector();
      int days = weeks * 7;
      
      String query = "select bo.supply_path,sup.supplier_name supplier, po.radian_po, ";
      query += "line.PROMISED_DATE, line.vendor_ship_date ship_date, line.date_confirmed ";
      query += "from po, po_line line, supplier sup, buy_order bo  ";
      query += "where (line.date_confirmed > (SYSDATE-" + days + "))   ";
      query += "and po.radian_po = line.radian_po  ";
      query += "and po.supplier = sup.supplier  ";
      query += "and po.radian_po = bo.radian_po  ";
      query += "and (bo.supply_path = 'Wbuy' or bo.supply_path='Dbuy')     ";
      query += "  and line.vendor_ship_date is not null ";
      
      DataSet dataSet = new SqlManager().select(conn, query);
      
      Iterator dataIter = dataSet.iterator();
      
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow)dataIter.next();
         EbuyDailyConfirmedOrdersBean ebuyDailyConfirmedOrdersBean = new EbuyDailyConfirmedOrdersBean();
         load(dataSetRow, ebuyDailyConfirmedOrdersBean);
         ebuyDailyConfirmedOrdersBeanColl.add(ebuyDailyConfirmedOrdersBean);
      }
      
      return ebuyDailyConfirmedOrdersBeanColl;
   }
   
}