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
import com.tcmis.internal.hub.beans.CabinetBinLabelViewBean;

/******************************************************************************
 * CLASSNAME: CabinetBinLabelViewBeanFactory <br>
 * @version: 1.0, Sep 20, 2006 <br>
 *****************************************************************************/

public class CabinetBinLabelViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CABINET_ID = "CABINET_ID";
  public String ATTRIBUTE_BIN_NAME = "BIN_NAME";
  public String ATTRIBUTE_BIN_ID = "BIN_ID";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_NUM_CAT_PARTS = "NUM_CAT_PARTS";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_MATERIAL_ID_STRING = "MATERIAL_ID_STRING";
  public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
  public String ATTRIBUTE_QC_DOC = "QC_DOC";
  public String ATTRIBUTE_CABINET_NAME = "CABINET_NAME";
  public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
  public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";

  //table name
  public String TABLE = "CABINET_BIN_LABEL_VIEW";

  //constructor
  public CabinetBinLabelViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("cabinetId")) {
      return ATTRIBUTE_CABINET_ID;
    }
    else if (attributeName.equals("binName")) {
      return ATTRIBUTE_BIN_NAME;
    }
    else if (attributeName.equals("binId")) {
      return ATTRIBUTE_BIN_ID;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("numCatParts")) {
      return ATTRIBUTE_NUM_CAT_PARTS;
    }
    else if (attributeName.equals("packaging")) {
      return ATTRIBUTE_PACKAGING;
    }
    else if (attributeName.equals("materialIdString")) {
      return ATTRIBUTE_MATERIAL_ID_STRING;
    }
    else if (attributeName.equals("mfgDesc")) {
      return ATTRIBUTE_MFG_DESC;
    }
    else if (attributeName.equals("qcDoc")) {
      return ATTRIBUTE_QC_DOC;
    }
    else if (attributeName.equals("cabinetName")) {
      return ATTRIBUTE_CABINET_NAME;
    }
    else if (attributeName.equals("reorderPoint")) {
      return ATTRIBUTE_REORDER_POINT;
    }
    else if (attributeName.equals("stockingLevel")) {
      return ATTRIBUTE_STOCKING_LEVEL;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CabinetBinLabelViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CabinetBinLabelViewBean cabinetBinLabelViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("cabinetId", "SearchCriterion.EQUALS",
     "" + cabinetBinLabelViewBean.getCabinetId());
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
   public int delete(CabinetBinLabelViewBean cabinetBinLabelViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("cabinetId", "SearchCriterion.EQUALS",
     "" + cabinetBinLabelViewBean.getCabinetId());
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
   public int insert(CabinetBinLabelViewBean cabinetBinLabelViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(cabinetBinLabelViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(CabinetBinLabelViewBean cabinetBinLabelViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_CABINET_ID + "," +
     ATTRIBUTE_BIN_NAME + "," +
     ATTRIBUTE_BIN_ID + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_NUM_CAT_PARTS + "," +
     ATTRIBUTE_PACKAGING + "," +
     ATTRIBUTE_MATERIAL_ID_STRING + "," +
     ATTRIBUTE_MFG_DESC + "," +
     ATTRIBUTE_QC_DOC + "," +
     ATTRIBUTE_CABINET_NAME + "," +
     ATTRIBUTE_REORDER_POINT + "," +
     ATTRIBUTE_STOCKING_LEVEL + "," +
     ATTRIBUTE_INVENTORY_GROUP + ")" +
     " values (" +
     cabinetBinLabelViewBean.getCabinetId() + "," +
     SqlHandler.delimitString(cabinetBinLabelViewBean.getBinName()) + "," +
     cabinetBinLabelViewBean.getBinId() + "," +
     cabinetBinLabelViewBean.getItemId() + "," +
     SqlHandler.delimitString(cabinetBinLabelViewBean.getCatPartNo()) + "," +
       SqlHandler.delimitString(cabinetBinLabelViewBean.getPartDescription()) + "," +
     cabinetBinLabelViewBean.getNumCatParts() + "," +
     SqlHandler.delimitString(cabinetBinLabelViewBean.getPackaging()) + "," +
       SqlHandler.delimitString(cabinetBinLabelViewBean.getMaterialIdString()) + "," +
     SqlHandler.delimitString(cabinetBinLabelViewBean.getMfgDesc()) + "," +
     SqlHandler.delimitString(cabinetBinLabelViewBean.getQcDoc()) + "," +
     SqlHandler.delimitString(cabinetBinLabelViewBean.getCabinetName()) + "," +
     cabinetBinLabelViewBean.getReorderPoint() + "," +
     cabinetBinLabelViewBean.getStockingLevel() + "," +
       SqlHandler.delimitString(cabinetBinLabelViewBean.getInventoryGroup()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(CabinetBinLabelViewBean cabinetBinLabelViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(cabinetBinLabelViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CabinetBinLabelViewBean cabinetBinLabelViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CABINET_ID + "=" +
      StringHandler.nullIfZero(cabinetBinLabelViewBean.getCabinetId()) + "," +
     ATTRIBUTE_BIN_NAME + "=" +
      SqlHandler.delimitString(cabinetBinLabelViewBean.getBinName()) + "," +
     ATTRIBUTE_BIN_ID + "=" +
      StringHandler.nullIfZero(cabinetBinLabelViewBean.getBinId()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(cabinetBinLabelViewBean.getItemId()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(cabinetBinLabelViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
       SqlHandler.delimitString(cabinetBinLabelViewBean.getPartDescription()) + "," +
     ATTRIBUTE_NUM_CAT_PARTS + "=" +
       StringHandler.nullIfZero(cabinetBinLabelViewBean.getNumCatParts()) + "," +
     ATTRIBUTE_PACKAGING + "=" +
      SqlHandler.delimitString(cabinetBinLabelViewBean.getPackaging()) + "," +
     ATTRIBUTE_MATERIAL_ID_STRING + "=" +
       SqlHandler.delimitString(cabinetBinLabelViewBean.getMaterialIdString()) + "," +
     ATTRIBUTE_MFG_DESC + "=" +
      SqlHandler.delimitString(cabinetBinLabelViewBean.getMfgDesc()) + "," +
     ATTRIBUTE_QC_DOC + "=" +
      SqlHandler.delimitString(cabinetBinLabelViewBean.getQcDoc()) + "," +
     ATTRIBUTE_CABINET_NAME + "=" +
       SqlHandler.delimitString(cabinetBinLabelViewBean.getCabinetName()) + "," +
     ATTRIBUTE_REORDER_POINT + "=" +
       StringHandler.nullIfZero(cabinetBinLabelViewBean.getReorderPoint()) + "," +
     ATTRIBUTE_STOCKING_LEVEL + "=" +
       StringHandler.nullIfZero(cabinetBinLabelViewBean.getStockingLevel()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
       SqlHandler.delimitString(cabinetBinLabelViewBean.getInventoryGroup()) + " " +
     "where " + ATTRIBUTE_CABINET_ID + "=" +
      cabinetBinLabelViewBean.getCabinetId();
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
    Collection cabinetBinLabelViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CabinetBinLabelViewBean cabinetBinLabelViewBean = new
          CabinetBinLabelViewBean();
      load(dataSetRow, cabinetBinLabelViewBean);
      cabinetBinLabelViewBeanColl.add(cabinetBinLabelViewBean);
    }
    return cabinetBinLabelViewBeanColl;
  }
}