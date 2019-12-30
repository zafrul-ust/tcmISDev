package com.tcmis.client.report.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.report.beans.InventoryPartSelectionViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

/******************************************************************************
 * CLASSNAME: InventoryPartSelectionViewBeanFactory <br>
 * @version: 1.0, Nov 1, 2005 <br>
 *****************************************************************************/

public class InventoryPartSelectionViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";

  //table name
  public String TABLE = "INVENTORY_PART_SELECTION_VIEW";

  //constructor
  public InventoryPartSelectionViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InventoryPartSelectionViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InventoryPartSelectionViewBean inventoryPartSelectionViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + inventoryPartSelectionViewBean.getCompanyId());
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
   public int delete(InventoryPartSelectionViewBean inventoryPartSelectionViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + inventoryPartSelectionViewBean.getCompanyId());
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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(InventoryPartSelectionViewBean inventoryPartSelectionViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(inventoryPartSelectionViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InventoryPartSelectionViewBean inventoryPartSelectionViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_CAT_PART_NO + ")" +
     " values (" +
     SqlHandler.delimitString(inventoryPartSelectionViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(inventoryPartSelectionViewBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(inventoryPartSelectionViewBean.getCatPartNo()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InventoryPartSelectionViewBean inventoryPartSelectionViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(inventoryPartSelectionViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InventoryPartSelectionViewBean inventoryPartSelectionViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(inventoryPartSelectionViewBean.getCompanyId()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
       SqlHandler.delimitString(inventoryPartSelectionViewBean.getInventoryGroup()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(inventoryPartSelectionViewBean.getCatPartNo()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      inventoryPartSelectionViewBean.getCompanyId();
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
    Collection inventoryPartSelectionViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DailyPartInventorySumViewBeanFactory detailFactory = new
        DailyPartInventorySumViewBeanFactory(getDbManager());
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InventoryPartSelectionViewBean inventoryPartSelectionViewBean = new
          InventoryPartSelectionViewBean();
      load(dataSetRow, inventoryPartSelectionViewBean);
      SearchCriteria detailCriteria = new SearchCriteria();
      detailCriteria.addCriterion("dailyDate",
                                  SearchCriterion.GREATER_THAN_OR_EQUAL_TO,
                                  "11/01/2005");
      detailCriteria.addCriterion("dailyDate",
                                  SearchCriterion.LESS_THAN_OR_EQUAL_TO,
                                  "11/01/2005");
      detailCriteria.addCriterion("catPartNo",
                                  SearchCriterion.EQUALS,
                                  inventoryPartSelectionViewBean.getCatPartNo());
      detailCriteria.addCriterion("inventoryGroup",
                                  SearchCriterion.EQUALS,
                                  inventoryPartSelectionViewBean.getInventoryGroup());
      inventoryPartSelectionViewBean.setDetailCollection(detailFactory.select(
          detailCriteria));
      inventoryPartSelectionViewBeanColl.add(inventoryPartSelectionViewBean);
    }
    return inventoryPartSelectionViewBeanColl;
  }
}