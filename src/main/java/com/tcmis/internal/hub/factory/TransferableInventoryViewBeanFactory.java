package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.TransferableInventoryViewBean;

/******************************************************************************
 * CLASSNAME: TransferableInventoryViewBeanFactory <br>
 * @version: 1.0, Nov 9, 2006 <br>
 *****************************************************************************/

public class TransferableInventoryViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
  public String ATTRIBUTE_XFER_SOURCE_ORIGINATE = "XFER_SOURCE_ORIGINATE";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_ON_HAND = "ON_HAND";
  public String ATTRIBUTE_ALLOCATABLE = "ALLOCATABLE";
  public String ATTRIBUTE_SEARCH_STRING = "SEARCH_STRING";
  public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
  public String ATTRIBUTE_SPEC_LIST = "SPEC_LIST";
  public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
  public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
  public String ATTRIBUTE_DESTINATION_INVENTORY_GROUP = "DESTINATION_INVENTORY_GROUP";
  public String ATTRIBUTE_DESTINATION_INV_GROUP_NAME = "DESTINATION_INV_GROUP_NAME";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_DIST_CUSTOMER_PART_LIST = "DIST_CUSTOMER_PART_LIST";

  //table name
  public String TABLE = "TRANSFERABLE_INVENTORY_VIEW";

  //constructor
  public TransferableInventoryViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("itemDesc")) {
      return ATTRIBUTE_ITEM_DESC;
    }
    else if (attributeName.equals("xferSourceOriginate")) {
      return ATTRIBUTE_XFER_SOURCE_ORIGINATE;
    }
    else if (attributeName.equals("packaging")) {
      return ATTRIBUTE_PACKAGING;
    }
    else if (attributeName.equals("onHand")) {
      return ATTRIBUTE_ON_HAND;
    }
    else if (attributeName.equals("allocatable")) {
        return ATTRIBUTE_ALLOCATABLE;
      }
    else if (attributeName.equals("searchString")) {
      return ATTRIBUTE_SEARCH_STRING;
    }
    else if (attributeName.equals("inventoryGroupName")) {
      return ATTRIBUTE_INVENTORY_GROUP_NAME;
    }
    else if (attributeName.equals("specList")) {
        return ATTRIBUTE_SPEC_LIST;
    }
    else if (attributeName.equals("opsEntityId")) {
        return ATTRIBUTE_OPS_ENTITY_ID;
    }
    else if (attributeName.equals("opsCompanyId")) {
        return ATTRIBUTE_OPS_COMPANY_ID;
    }
    else if (attributeName.equals("destInventoryGroup")) {
        return ATTRIBUTE_DESTINATION_INVENTORY_GROUP;
    }
    else if (attributeName.equals("destInvGroupName")) {
        return ATTRIBUTE_DESTINATION_INV_GROUP_NAME;
    }
    else if (attributeName.equals("companyId")) {
        return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("catalogCompanyId")) {
        return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
    else if (attributeName.equals("catalogId")) {
        return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catPartNo")) {
        return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("partGroupNo")) {
        return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("distCustomerPartList")) {
        return ATTRIBUTE_DIST_CUSTOMER_PART_LIST;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, TransferableInventoryViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(TransferableInventoryViewBean transferableInventoryViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
     "" + transferableInventoryViewBean.getInventoryGroup());
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
   public int delete(TransferableInventoryViewBean transferableInventoryViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
     "" + transferableInventoryViewBean.getInventoryGroup());
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
       public int insert(TransferableInventoryViewBean transferableInventoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(transferableInventoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(TransferableInventoryViewBean transferableInventoryViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_ITEM_DESC + "," +
     ATTRIBUTE_XFER_SOURCE_ORIGINATE + "," +
     ATTRIBUTE_PACKAGING + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_SEARCH_STRING + "," +
     ATTRIBUTE_INVENTORY_GROUP_NAME + ")" +
     " values (" +
     SqlHandler.delimitString(transferableInventoryViewBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(transferableInventoryViewBean.getHub()) + "," +
     transferableInventoryViewBean.getItemId() + "," +
       SqlHandler.delimitString(transferableInventoryViewBean.getItemDesc()) + "," +
     SqlHandler.delimitString(transferableInventoryViewBean.getXferSourceOriginate()) + "," +
       SqlHandler.delimitString(transferableInventoryViewBean.getPackaging()) + "," +
     transferableInventoryViewBean.getQuantity() + "," +
     SqlHandler.delimitString(transferableInventoryViewBean.getSearchString()) + "," +
     SqlHandler.delimitString(transferableInventoryViewBean.getInventoryGroupName()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(TransferableInventoryViewBean transferableInventoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(transferableInventoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(TransferableInventoryViewBean transferableInventoryViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(transferableInventoryViewBean.getInventoryGroup()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(transferableInventoryViewBean.getHub()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
       StringHandler.nullIfZero(transferableInventoryViewBean.getItemId()) + "," +
     ATTRIBUTE_ITEM_DESC + "=" +
       SqlHandler.delimitString(transferableInventoryViewBean.getItemDesc()) + "," +
     ATTRIBUTE_XFER_SOURCE_ORIGINATE + "=" +
      SqlHandler.delimitString(transferableInventoryViewBean.getXferSourceOriginate()) + "," +
     ATTRIBUTE_PACKAGING + "=" +
       SqlHandler.delimitString(transferableInventoryViewBean.getPackaging()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
       StringHandler.nullIfZero(transferableInventoryViewBean.getQuantity()) + "," +
     ATTRIBUTE_SEARCH_STRING + "=" +
      SqlHandler.delimitString(transferableInventoryViewBean.getSearchString()) + "," +
     ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
      SqlHandler.delimitString(transferableInventoryViewBean.getInventoryGroupName()) + " " +
     "where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
      transferableInventoryViewBean.getInventoryGroup();
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

  public Collection select(SearchCriteria criteria,  Connection conn) throws
      BaseException {
    Collection transferableInventoryViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      TransferableInventoryViewBean transferableInventoryViewBean = new
          TransferableInventoryViewBean();
      load(dataSetRow, transferableInventoryViewBean);
      transferableInventoryViewBeanColl.add(transferableInventoryViewBean);
    }
    return transferableInventoryViewBeanColl;
  }
}