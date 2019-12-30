package com.tcmis.client.cxml.factory;

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
import com.tcmis.client.cxml.beans.ShoppingCartViewBean;

/******************************************************************************
 * CLASSNAME: ShoppingCartViewBeanFactory <br>
 * @version: 1.0, Jun 16, 2006 <br>
 *****************************************************************************/

public class ShoppingCartViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_QUOTED_PRICE = "QUOTED_PRICE";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_PREPAID_AMOUNT = "PREPAID_AMOUNT";
  public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
  public String ATTRIBUTE_ACCOUNT_SYS_DESC = "ACCOUNT_SYS_DESC";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
  public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
  public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
  public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
  public String ATTRIBUTE_CITY = "CITY";
  public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
  public String ATTRIBUTE_ZIP = "ZIP";
  public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
  public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
  public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
  public String ATTRIBUTE_ENTRY_ID = "ENTRY_ID";
  public String ATTRIBUTE_USE_FACILITY_ID = "USE_FACILITY_ID";

  //table name
  public String TABLE = "SHOPPING_CART_VIEW";

  //constructor
  public ShoppingCartViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("payloadId")) {
      return ATTRIBUTE_PAYLOAD_ID;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("quotedPrice")) {
      return ATTRIBUTE_QUOTED_PRICE;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("prepaidAmount")) {
      return ATTRIBUTE_PREPAID_AMOUNT;
    }
    else if (attributeName.equals("accountSysId")) {
      return ATTRIBUTE_ACCOUNT_SYS_ID;
    }
    else if (attributeName.equals("accountSysDesc")) {
      return ATTRIBUTE_ACCOUNT_SYS_DESC;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("shipToLocationId")) {
      return ATTRIBUTE_SHIP_TO_LOCATION_ID;
    }
    else if (attributeName.equals("addressLine11")) {
      return ATTRIBUTE_ADDRESS_LINE_1;
    }
    else if (attributeName.equals("addressLine22")) {
      return ATTRIBUTE_ADDRESS_LINE_2;
    }
    else if (attributeName.equals("addressLine33")) {
      return ATTRIBUTE_ADDRESS_LINE_3;
    }
    else if (attributeName.equals("city")) {
      return ATTRIBUTE_CITY;
    }
    else if (attributeName.equals("stateAbbrev")) {
      return ATTRIBUTE_STATE_ABBREV;
    }
    else if (attributeName.equals("zip")) {
      return ATTRIBUTE_ZIP;
    }
    else if (attributeName.equals("countryAbbrev")) {
      return ATTRIBUTE_COUNTRY_ABBREV;
    }
    else if (attributeName.equals("materialDesc")) {
      return ATTRIBUTE_MATERIAL_DESC;
    }
    else if (attributeName.equals("packaging")) {
      return ATTRIBUTE_PACKAGING;
    }
    else if (attributeName.equals("facPartNo")) {
      return ATTRIBUTE_FAC_PART_NO;
    }
    else if (attributeName.equals("itemType")) {
      return ATTRIBUTE_ITEM_TYPE;
    }
    else if (attributeName.equals("entryId")) {
      return ATTRIBUTE_ENTRY_ID;
    }
    else if (attributeName.equals("useFacilityId")) {
      return ATTRIBUTE_USE_FACILITY_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ShoppingCartViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(ShoppingCartViewBean shoppingCartViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + shoppingCartViewBean.getPrNumber());
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
       public int delete(ShoppingCartViewBean shoppingCartViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + shoppingCartViewBean.getPrNumber());
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
   public int insert(ShoppingCartViewBean shoppingCartViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(shoppingCartViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(ShoppingCartViewBean shoppingCartViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_PAYLOAD_ID + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_QUOTED_PRICE + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_PREPAID_AMOUNT + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "," +
     ATTRIBUTE_ACCOUNT_SYS_DESC + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
     ATTRIBUTE_ADDRESS_LINE_1 + "," +
     ATTRIBUTE_ADDRESS_LINE_2 + "," +
     ATTRIBUTE_ADDRESS_LINE_3 + "," +
     ATTRIBUTE_CITY + "," +
     ATTRIBUTE_STATE_ABBREV + "," +
     ATTRIBUTE_ZIP + "," +
     ATTRIBUTE_COUNTRY_ABBREV + "," +
     ATTRIBUTE_MATERIAL_DESC + "," +
     ATTRIBUTE_PACKAGING + "," +
     ATTRIBUTE_FAC_PART_NO + "," +
     ATTRIBUTE_ITEM_TYPE + "," +
     ATTRIBUTE_ENTRY_ID + "," +
     ATTRIBUTE_USE_FACILITY_ID + ")" +
     " values (" +
     shoppingCartViewBean.getPrNumber() + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getPayloadId()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getLineItem()) + "," +
     shoppingCartViewBean.getItemId() + "," +
     shoppingCartViewBean.getQuantity() + "," +
     shoppingCartViewBean.getQuotedPrice() + "," +
     shoppingCartViewBean.getExtendedPrice() + "," +
     shoppingCartViewBean.getPrepaidAmount() + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getAccountSysId()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getAccountSysDesc()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getApplication()) + "," +
       SqlHandler.delimitString(shoppingCartViewBean.getShipToLocationId()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getAddressLine1()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getAddressLine2()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getAddressLine3()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getCity()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getStateAbbrev()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getZip()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getCountryAbbrev()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getMaterialDesc()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getPackaging()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getFacPartNo()) + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getItemType()) + "," +
     shoppingCartViewBean.getEntryId() + "," +
     SqlHandler.delimitString(shoppingCartViewBean.getUseFacilityId()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(ShoppingCartViewBean shoppingCartViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(shoppingCartViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(ShoppingCartViewBean shoppingCartViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(shoppingCartViewBean.getPrNumber()) + "," +
     ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getPayloadId()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getLineItem()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(shoppingCartViewBean.getItemId()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(shoppingCartViewBean.getQuantity()) + "," +
     ATTRIBUTE_QUOTED_PRICE + "=" +
      StringHandler.nullIfZero(shoppingCartViewBean.getQuotedPrice()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      StringHandler.nullIfZero(shoppingCartViewBean.getExtendedPrice()) + "," +
     ATTRIBUTE_PREPAID_AMOUNT + "=" +
      StringHandler.nullIfZero(shoppingCartViewBean.getPrepaidAmount()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getAccountSysId()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_DESC + "=" +
       SqlHandler.delimitString(shoppingCartViewBean.getAccountSysDesc()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getApplication()) + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
       SqlHandler.delimitString(shoppingCartViewBean.getShipToLocationId()) + "," +
     ATTRIBUTE_ADDRESS_LINE_1 + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getAddressLine1()) + "," +
     ATTRIBUTE_ADDRESS_LINE_2 + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getAddressLine2()) + "," +
     ATTRIBUTE_ADDRESS_LINE_3 + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getAddressLine3()) + "," +
     ATTRIBUTE_CITY + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getCity()) + "," +
     ATTRIBUTE_STATE_ABBREV + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getStateAbbrev()) + "," +
     ATTRIBUTE_ZIP + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getZip()) + "," +
     ATTRIBUTE_COUNTRY_ABBREV + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getCountryAbbrev()) + "," +
     ATTRIBUTE_MATERIAL_DESC + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getMaterialDesc()) + "," +
     ATTRIBUTE_PACKAGING + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getPackaging()) + "," +
     ATTRIBUTE_FAC_PART_NO + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getFacPartNo()) + "," +
     ATTRIBUTE_ITEM_TYPE + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getItemType()) + "," +
     ATTRIBUTE_ENTRY_ID + "=" +
      StringHandler.nullIfZero(shoppingCartViewBean.getEntryId()) + "," +
     ATTRIBUTE_USE_FACILITY_ID + "=" +
      SqlHandler.delimitString(shoppingCartViewBean.getUseFacilityId()) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" +
      shoppingCartViewBean.getPrNumber();
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

    Collection shoppingCartViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    log.debug("Larry Debug:"+query);
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ShoppingCartViewBean shoppingCartViewBean = new ShoppingCartViewBean();
      load(dataSetRow, shoppingCartViewBean);
      shoppingCartViewBeanColl.add(shoppingCartViewBean);
    }

    return shoppingCartViewBeanColl;
  }
}