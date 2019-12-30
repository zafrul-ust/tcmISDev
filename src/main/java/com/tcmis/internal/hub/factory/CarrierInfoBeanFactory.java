package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.CarrierInfoBean;

/******************************************************************************
 * CLASSNAME: CarrierInfoBeanFactory <br>
 * @version: 1.0, Feb 23, 2007 <br>
 *****************************************************************************/

public class CarrierInfoBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_SHIP_TO_LOC_ID = "SHIP_TO_LOC_ID";
  public String ATTRIBUTE_BILL_TO_LOC_ID = "BILL_TO_LOC_ID";
  public String ATTRIBUTE_ACCOUNT = "ACCOUNT";
  public String ATTRIBUTE_NOTES = "NOTES";
  public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_HAAS_VENDOR = "HAAS_VENDOR";
  public String ATTRIBUTE_CARRIER_METHOD = "CARRIER_METHOD";
  public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_MIN_GROSS_SHIPMENT_WEIGHT =
      "MIN_GROSS_SHIPMENT_WEIGHT";
  public String ATTRIBUTE_MAX_GROSS_SHIPMENT_WEIGHT =
      "MAX_GROSS_SHIPMENT_WEIGHT";
  public String ATTRIBUTE_MAX_GROSS_INDIVIDUAL_WEIGHT =
      "MAX_GROSS_INDIVIDUAL_WEIGHT";
  public String ATTRIBUTE_MODIFICATION_COMMENTS = "MODIFICATION_COMMENTS";

  //table name
  public String TABLE = "CARRIER_INFO";

  //constructor
  public CarrierInfoBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("carrierCode")) {
      return ATTRIBUTE_CARRIER_CODE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("shipToLocId")) {
      return ATTRIBUTE_SHIP_TO_LOC_ID;
    }
    else if (attributeName.equals("billToLocId")) {
      return ATTRIBUTE_BILL_TO_LOC_ID;
    }
    else if (attributeName.equals("account")) {
      return ATTRIBUTE_ACCOUNT;
    }
    else if (attributeName.equals("notes")) {
      return ATTRIBUTE_NOTES;
    }
    else if (attributeName.equals("carrierName")) {
      return ATTRIBUTE_CARRIER_NAME;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("haasVendor")) {
      return ATTRIBUTE_HAAS_VENDOR;
    }
    else if (attributeName.equals("carrierMethod")) {
      return ATTRIBUTE_CARRIER_METHOD;
    }
    else if (attributeName.equals("supplier")) {
      return ATTRIBUTE_SUPPLIER;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("freightOnBoard")) {
      return ATTRIBUTE_FREIGHT_ON_BOARD;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("minGrossShipmentWeight")) {
      return ATTRIBUTE_MIN_GROSS_SHIPMENT_WEIGHT;
    }
    else if (attributeName.equals("maxGrossShipmentWeight")) {
      return ATTRIBUTE_MAX_GROSS_SHIPMENT_WEIGHT;
    }
    else if (attributeName.equals("maxGrossIndividualWeight")) {
      return ATTRIBUTE_MAX_GROSS_INDIVIDUAL_WEIGHT;
    }
    else if (attributeName.equals("modificationComments")) {
      return ATTRIBUTE_MODIFICATION_COMMENTS;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CarrierInfoBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CarrierInfoBean carrierInfoBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("carrierCode", "SearchCriterion.EQUALS",
     "" + carrierInfoBean.getCarrierCode());
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
   public int delete(CarrierInfoBean carrierInfoBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("carrierCode", "SearchCriterion.EQUALS",
     "" + carrierInfoBean.getCarrierCode());
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
   public int insert(CarrierInfoBean carrierInfoBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(carrierInfoBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(CarrierInfoBean carrierInfoBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_CARRIER_CODE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_SHIP_TO_LOC_ID + "," +
     ATTRIBUTE_BILL_TO_LOC_ID + "," +
     ATTRIBUTE_ACCOUNT + "," +
     ATTRIBUTE_NOTES + "," +
     ATTRIBUTE_CARRIER_NAME + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_HAAS_VENDOR + "," +
     ATTRIBUTE_CARRIER_METHOD + "," +
     ATTRIBUTE_SUPPLIER + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_FREIGHT_ON_BOARD + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_MIN_GROSS_SHIPMENT_WEIGHT + "," +
     ATTRIBUTE_MAX_GROSS_SHIPMENT_WEIGHT + "," +
     ATTRIBUTE_MAX_GROSS_INDIVIDUAL_WEIGHT + "," +
     ATTRIBUTE_MODIFICATION_COMMENTS + ")" +
     " values (" +
     SqlHandler.delimitString(carrierInfoBean.getCarrierCode()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getCompanyId()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getShipToLocId()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getBillToLocId()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getAccount()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getNotes()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getCarrierName()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getHub()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getHaasVendor()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getCarrierMethod()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getSupplier()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getFreightOnBoard()) + "," +
     SqlHandler.delimitString(carrierInfoBean.getStatus()) + "," +
     carrierInfoBean.getMinGrossShipmentWeight() + "," +
     carrierInfoBean.getMaxGrossShipmentWeight() + "," +
     carrierInfoBean.getMaxGrossIndividualWeight() + "," +
     SqlHandler.delimitString(carrierInfoBean.getModificationComments()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(CarrierInfoBean carrierInfoBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(carrierInfoBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CarrierInfoBean carrierInfoBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CARRIER_CODE + "=" +
      SqlHandler.delimitString(carrierInfoBean.getCarrierCode()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(carrierInfoBean.getCompanyId()) + "," +
     ATTRIBUTE_SHIP_TO_LOC_ID + "=" +
      SqlHandler.delimitString(carrierInfoBean.getShipToLocId()) + "," +
     ATTRIBUTE_BILL_TO_LOC_ID + "=" +
      SqlHandler.delimitString(carrierInfoBean.getBillToLocId()) + "," +
     ATTRIBUTE_ACCOUNT + "=" +
      SqlHandler.delimitString(carrierInfoBean.getAccount()) + "," +
     ATTRIBUTE_NOTES + "=" +
      SqlHandler.delimitString(carrierInfoBean.getNotes()) + "," +
     ATTRIBUTE_CARRIER_NAME + "=" +
      SqlHandler.delimitString(carrierInfoBean.getCarrierName()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(carrierInfoBean.getHub()) + "," +
     ATTRIBUTE_HAAS_VENDOR + "=" +
      SqlHandler.delimitString(carrierInfoBean.getHaasVendor()) + "," +
     ATTRIBUTE_CARRIER_METHOD + "=" +
      SqlHandler.delimitString(carrierInfoBean.getCarrierMethod()) + "," +
     ATTRIBUTE_SUPPLIER + "=" +
      SqlHandler.delimitString(carrierInfoBean.getSupplier()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(carrierInfoBean.getInventoryGroup()) + "," +
     ATTRIBUTE_FREIGHT_ON_BOARD + "=" +
      SqlHandler.delimitString(carrierInfoBean.getFreightOnBoard()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(carrierInfoBean.getStatus()) + "," +
     ATTRIBUTE_MIN_GROSS_SHIPMENT_WEIGHT + "=" +
       StringHandler.nullIfZero(carrierInfoBean.getMinGrossShipmentWeight()) + "," +
     ATTRIBUTE_MAX_GROSS_SHIPMENT_WEIGHT + "=" +
       StringHandler.nullIfZero(carrierInfoBean.getMaxGrossShipmentWeight()) + "," +
     ATTRIBUTE_MAX_GROSS_INDIVIDUAL_WEIGHT + "=" +
       StringHandler.nullIfZero(carrierInfoBean.getMaxGrossIndividualWeight()) + "," +
     ATTRIBUTE_MODIFICATION_COMMENTS + "=" +
       SqlHandler.delimitString(carrierInfoBean.getModificationComments()) + " " +
     "where " + ATTRIBUTE_CARRIER_CODE + "=" +
      carrierInfoBean.getCarrierCode();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException {
    Collection carrierInfoBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CarrierInfoBean carrierInfoBean = new CarrierInfoBean();
      load(dataSetRow, carrierInfoBean);
      carrierInfoBeanColl.add(carrierInfoBean);
    }
    return carrierInfoBeanColl;
  }
}