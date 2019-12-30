package com.tcmis.client.raytheon.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.raytheon.beans.XcblOrderDetailBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: XcblOrderDetailBeanFactory <br>
 * @version: 1.0, Jul 20, 2005 <br>
 *****************************************************************************/

public class XcblOrderDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_XCBL_ORDER_ID = "XCBL_ORDER_ID";
  public String ATTRIBUTE_BUYER_LINE_ITEM_NUM = "BUYER_LINE_ITEM_NUM";
  public String ATTRIBUTE_MANUFACTURER_PART_ID = "MANUFACTURER_PART_ID";
  public String ATTRIBUTE_SELLER_PART_ID = "SELLER_PART_ID";
  public String ATTRIBUTE_BUYER_PART_ID = "BUYER_PART_ID";
  public String ATTRIBUTE_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_OFF_CATALOG_FLAG = "OFF_CATALOG_FLAG";
  public String ATTRIBUTE_UOM = "UOM";
  public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
  public String ATTRIBUTE_CURRENCY = "CURRENCY";
  public String ATTRIBUTE_NOTES = "NOTES";
  public String ATTRIBUTE_ID = "ID";
  public String ATTRIBUTE_PRICE_BASIS_UOM = "PRICE_BASIS_UOM";
  public String ATTRIBUTE_PRICE_BASIS_QUANTITY = "PRICE_BASIS_QUANTITY";

  //table name
  public String TABLE = "XCBL_ORDER_DETAIL";

  //constructor
  public XcblOrderDetailBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("ccblOrderId")) {
      return ATTRIBUTE_XCBL_ORDER_ID;
    }
    else if (attributeName.equals("buyerLineItemNum")) {
      return ATTRIBUTE_BUYER_LINE_ITEM_NUM;
    }
    else if (attributeName.equals("manufacturerPartId")) {
      return ATTRIBUTE_MANUFACTURER_PART_ID;
    }
    else if (attributeName.equals("sellerPartId")) {
      return ATTRIBUTE_SELLER_PART_ID;
    }
    else if (attributeName.equals("buyerPartId")) {
      return ATTRIBUTE_BUYER_PART_ID;
    }
    else if (attributeName.equals("itemDescription")) {
      return ATTRIBUTE_ITEM_DESCRIPTION;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("offCatalogFlag")) {
      return ATTRIBUTE_OFF_CATALOG_FLAG;
    }
    else if (attributeName.equals("uom")) {
      return ATTRIBUTE_UOM;
    }
    else if (attributeName.equals("unitPrice")) {
      return ATTRIBUTE_UNIT_PRICE;
    }
    else if (attributeName.equals("currency")) {
      return ATTRIBUTE_CURRENCY;
    }
    else if (attributeName.equals("notes")) {
      return ATTRIBUTE_NOTES;
    }
    else if (attributeName.equals("id")) {
      return ATTRIBUTE_ID;
    }
    else if (attributeName.equals("priceBasisUom")) {
      return ATTRIBUTE_PRICE_BASIS_UOM;
    }
    else if (attributeName.equals("priceBasisQuantity")) {
      return ATTRIBUTE_PRICE_BASIS_QUANTITY;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, XcblOrderDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(XcblOrderDetailBean xcblOrderDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("raytheonXcblOrderId", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailBean.getRaytheonXcblOrderId());
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
   public int delete(XcblOrderDetailBean xcblOrderDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("raytheonXcblOrderId", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailBean.getRaytheonXcblOrderId());
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

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
  }

  //insert
  public int insert(XcblOrderDetailBean xcblOrderDetailBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(xcblOrderDetailBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(XcblOrderDetailBean xcblOrderDetailBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_XCBL_ORDER_ID + "," +
        ATTRIBUTE_BUYER_LINE_ITEM_NUM + "," +
        ATTRIBUTE_MANUFACTURER_PART_ID + "," +
        ATTRIBUTE_SELLER_PART_ID + "," +
        ATTRIBUTE_BUYER_PART_ID + "," +
        ATTRIBUTE_ITEM_DESCRIPTION + "," +
        ATTRIBUTE_QUANTITY + "," +
        ATTRIBUTE_OFF_CATALOG_FLAG + "," +
        ATTRIBUTE_UOM + "," +
        ATTRIBUTE_UNIT_PRICE + "," +
        ATTRIBUTE_CURRENCY + "," +
        ATTRIBUTE_NOTES + "," +
        ATTRIBUTE_ID + "," +
        ATTRIBUTE_PRICE_BASIS_UOM + "," +
        ATTRIBUTE_PRICE_BASIS_QUANTITY + ")" +
        " values (" +
        xcblOrderDetailBean.getXcblOrderId() + "," +
        xcblOrderDetailBean.getBuyerLineItemNum() + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getManufacturerPartId()) + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getSellerPartId()) + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getBuyerPartId()) + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getItemDescription()) + "," +
        xcblOrderDetailBean.getQuantity() + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getOffCatalogFlag()) + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getUom()) + "," +
        xcblOrderDetailBean.getUnitPrice() + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getCurrency()) + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getNotes()) + "," +
        xcblOrderDetailBean.getId() + "," +
        SqlHandler.delimitString(xcblOrderDetailBean.getPriceBasisUom()) + "," +
        xcblOrderDetailBean.getPriceBasisQuantity() + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(XcblOrderDetailBean xcblOrderDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(xcblOrderDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(XcblOrderDetailBean xcblOrderDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_XCBL_ORDER_ID + "=" +
      StringHandler.nullIfZero(xcblOrderDetailBean.getXcblOrderId()) + "," +
     ATTRIBUTE_BUYER_LINE_ITEM_NUM + "=" +
      StringHandler.nullIfZero(xcblOrderDetailBean.getBuyerLineItemNum()) + "," +
     ATTRIBUTE_MANUFACTURER_PART_ID + "=" +
      SqlHandler.delimitString(xcblOrderDetailBean.getManufacturerPartId()) + "," +
     ATTRIBUTE_ITEM_DESCRIPTION + "=" +
      SqlHandler.delimitString(xcblOrderDetailBean.getItemDescription()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(xcblOrderDetailBean.getQuantity()) + "," +
     ATTRIBUTE_OFF_CATALOG_FLAG + "=" +
      SqlHandler.delimitString(xcblOrderDetailBean.getOffCatalogFlag()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(xcblOrderDetailBean.getUom()) + "," +
     ATTRIBUTE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(xcblOrderDetailBean.getUnitPrice()) + "," +
     ATTRIBUTE_CURRENCY + "=" +
      SqlHandler.delimitString(xcblOrderDetailBean.getCurrency()) + "," +
     ATTRIBUTE_NOTES + "=" +
      SqlHandler.delimitString(xcblOrderDetailBean.getNotes()) + "," +
     ATTRIBUTE_ID + "=" +
      StringHandler.nullIfZero(xcblOrderDetailBean.getId()) + "," +
     ATTRIBUTE_PRICE_BASIS_UOM + "=" +
      SqlHandler.delimitString(xcblOrderDetailBean.getPriceBasisUom()) + "," +
     ATTRIBUTE_PRICE_BASIS_QUANTITY + "=" +
      StringHandler.nullIfZero(xcblOrderDetailBean.getPriceBasisQuantity()) + " " +
     "where " + ATTRIBUTE_XCBL_ORDER_ID + "=" +
      xcblOrderDetailBean.getXcblOrderId();
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

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection xcblOrderDetailBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      XcblOrderDetailBean xcblOrderDetailBean = new XcblOrderDetailBean();
      load(dataSetRow, xcblOrderDetailBean);
      xcblOrderDetailBeanColl.add(xcblOrderDetailBean);
    }
    return xcblOrderDetailBeanColl;
  }
}