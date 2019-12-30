package com.tcmis.internal.hub.factory;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;

/******************************************************************************
 * CLASSNAME: InventoryGroupDefinitionBeanFactory <br>
 * @version: 1.0, Jan 21, 2005 <br>
 *****************************************************************************/

public class InventoryGroupDefinitionBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_NEED_TO_PRINT_LABEL = "NEED_TO_PRINT_LABEL";
  public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
  public String ATTRIBUTE_XFER_SOURCE_ORIGINATE = "XFER_SOURCE_ORIGINATE";
  public String ATTRIBUTE_DIRECTED_COMPANY_ID = "DIRECTED_COMPANY_ID";
  public String ATTRIBUTE_DIRECTED_FACILITY_ID = "DIRECTED_FACILITY_ID";
  public String ATTRIBUTE_DIRECTED_APPLICATION = "DIRECTED_APPLICATION";
  public String ATTRIBUTE_CREDIT_RETURN = "CREDIT_RETURN";
  public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
  public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT = "MANAGE_KITS_AS_SINGLE_UNIT";
  public String ATTRIBUTE_DEFAULT_BUYER_ID = "DEFAULT_BUYER_ID";
  public String ATTRIBUTE_STORAGE_AREA = "STORAGE_AREA";
  public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
  public String ATTRIBUTE_CREDIT_AT_OVER_COUNT = "CREDIT_AT_OVER_COUNT";
  public String ATTRIBUTE_LABEL_USE_WITH_INCLUDE_LOT = "LABEL_USE_WITH_INCLUDE_LOT";
  public String ATTRIBUTE_CSR_PERSONNEL_ID = "CSR_PERSONNEL_ID";
  public String ATTRIBUTE_PO_COPY_FROM_LAST_BUY = "PO_COPY_FROM_LAST_BUY";
  public String ATTRIBUTE_DIRECTED_DELIVERY_POINT = "DIRECTED_DELIVERY_POINT";
  public String ATTRIBUTE_DEFAULT_DBUY_BUYER = "DEFAULT_DBUY_BUYER";
  public String ATTRIBUTE_PACKING_LIST_TEMPLATE = "PACKING_LIST_TEMPLATE";
  public String ATTRIBUTE_PACKING_LIST_NUM_COPIES = "PACKING_LIST_NUM_COPIES";
  public String ATTRIBUTE_TRANSFER_PACKING_LIST_TEMPLATE = "TRANSFER_PACKING_LIST_TEMPLATE";

  //table name
  public String TABLE = "INVENTORY_GROUP_DEFINITION";

  //constructor
  public InventoryGroupDefinitionBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("needToPrintLabel")) {
      return ATTRIBUTE_NEED_TO_PRINT_LABEL;
    }
    else if (attributeName.equals("inventoryGroupName")) {
      return ATTRIBUTE_INVENTORY_GROUP_NAME;
    }
    else if (attributeName.equals("xferSourceOriginate")) {
      return ATTRIBUTE_XFER_SOURCE_ORIGINATE;
    }
    else if (attributeName.equals("directedCompanyId")) {
      return ATTRIBUTE_DIRECTED_COMPANY_ID;
    }
    else if (attributeName.equals("directedFacilityId")) {
      return ATTRIBUTE_DIRECTED_FACILITY_ID;
    }
    else if (attributeName.equals("directedApplication")) {
      return ATTRIBUTE_DIRECTED_APPLICATION;
    }
    else if (attributeName.equals("creditReturn")) {
      return ATTRIBUTE_CREDIT_RETURN;
    }
    else if (attributeName.equals("locationId")) {
      return ATTRIBUTE_LOCATION_ID;
    }
    else if (attributeName.equals("manageKitsAsSingleUnit")) {
      return ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT;
    }
    else if (attributeName.equals("defaultBuyerId")) {
      return ATTRIBUTE_DEFAULT_BUYER_ID;
    }
    else if (attributeName.equals("storageArea")) {
      return ATTRIBUTE_STORAGE_AREA;
    }
    else if (attributeName.equals("issueGeneration")) {
      return ATTRIBUTE_ISSUE_GENERATION;
    }
    else if (attributeName.equals("creditAtOverCount")) {
      return ATTRIBUTE_CREDIT_AT_OVER_COUNT;
    }
    else if (attributeName.equals("labelUseWithIncludeLot")) {
      return ATTRIBUTE_LABEL_USE_WITH_INCLUDE_LOT;
    }
    else if (attributeName.equals("csrPersonnelId")) {
      return ATTRIBUTE_CSR_PERSONNEL_ID;
    }
    else if (attributeName.equals("poCopyFromLastBuy")) {
      return ATTRIBUTE_PO_COPY_FROM_LAST_BUY;
    }
    else if (attributeName.equals("directedDeliveryPoint")) {
      return ATTRIBUTE_DIRECTED_DELIVERY_POINT;
    }
    else if (attributeName.equals("defaultDbuyBuyer")) {
      return ATTRIBUTE_DEFAULT_DBUY_BUYER;
    }
    else if (attributeName.equals("packingListTemplate")) {
      return ATTRIBUTE_PACKING_LIST_TEMPLATE;
    }
    else if (attributeName.equals("packingListNumCopies")) {
      return ATTRIBUTE_PACKING_LIST_NUM_COPIES;
    }
    else if (attributeName.equals("transferPackingListTemplate")) {
        return ATTRIBUTE_TRANSFER_PACKING_LIST_TEMPLATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InventoryGroupDefinitionBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InventoryGroupDefinitionBean inventoryGroupDefinitionBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "=",
     "" + inventoryGroupDefinitionBean.getInventoryGroup());
    Connection connection = this.getDbManager().getConnection();
    int result = this.delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int delete(InventoryGroupDefinitionBean inventoryGroupDefinitionBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "=",
     "" + inventoryGroupDefinitionBean.getInventoryGroup());
    return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = getDbManager().getConnection();
    int result = delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
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
   public int insert(InventoryGroupDefinitionBean inventoryGroupDefinitionBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = insert(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int insert(InventoryGroupDefinitionBean inventoryGroupDefinitionBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_NEED_TO_PRINT_LABEL + "," +
     ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
     ATTRIBUTE_XFER_SOURCE_ORIGINATE + "," +
     ATTRIBUTE_DIRECTED_COMPANY_ID + "," +
     ATTRIBUTE_DIRECTED_FACILITY_ID + "," +
     ATTRIBUTE_DIRECTED_APPLICATION + "," +
     ATTRIBUTE_CREDIT_RETURN + "," +
     ATTRIBUTE_LOCATION_ID + "," +
     ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "," +
     ATTRIBUTE_DEFAULT_BUYER_ID + "," +
     ATTRIBUTE_STORAGE_AREA + "," +
     ATTRIBUTE_ISSUE_GENERATION + "," +
     ATTRIBUTE_CREDIT_AT_OVER_COUNT + "," +
     ATTRIBUTE_LABEL_USE_WITH_INCLUDE_LOT + "," +
     ATTRIBUTE_CSR_PERSONNEL_ID + "," +
     ATTRIBUTE_PO_COPY_FROM_LAST_BUY + "," +
     ATTRIBUTE_DIRECTED_DELIVERY_POINT + "," +
     ATTRIBUTE_DEFAULT_DBUY_BUYER + ")" +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getHub()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getNeedToPrintLabel()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getInventoryGroupName()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getXferSourceOriginate()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedCompanyId()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedFacilityId()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedApplication()) + "," +
       SqlHandler.delimitString(inventoryGroupDefinitionBean.getCreditReturn()) + "," +
       SqlHandler.delimitString(inventoryGroupDefinitionBean.getLocationId()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getManageKitsAsSingleUnit()) + "," +
     StringHandler.nullIfZero(inventoryGroupDefinitionBean.getDefaultBuyerId()) + "," +
       SqlHandler.delimitString(inventoryGroupDefinitionBean.getStorageArea()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getIssueGeneration()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getCreditAtOverCount()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getLabelUseWithIncludeLot()) + "," +
     StringHandler.nullIfZero(inventoryGroupDefinitionBean.getCsrPersonnelId()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getPoCopyFromLastBuy()) + "," +
     SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedDeliveryPoint()) + "," +
     StringHandler.nullIfZero(inventoryGroupDefinitionBean.getDefaultDbuyBuyer()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InventoryGroupDefinitionBean inventoryGroupDefinitionBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = update(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int update(InventoryGroupDefinitionBean inventoryGroupDefinitionBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getInventoryGroup()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getHub()) + "," +
     ATTRIBUTE_NEED_TO_PRINT_LABEL + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getNeedToPrintLabel()) + "," +
     ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getInventoryGroupName()) + "," +
     ATTRIBUTE_XFER_SOURCE_ORIGINATE + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getXferSourceOriginate()) + "," +
     ATTRIBUTE_DIRECTED_COMPANY_ID + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedCompanyId()) + "," +
     ATTRIBUTE_DIRECTED_FACILITY_ID + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedFacilityId()) + "," +
     ATTRIBUTE_DIRECTED_APPLICATION + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedApplication()) + "," +
     ATTRIBUTE_CREDIT_RETURN + "=" +
       SqlHandler.delimitString(inventoryGroupDefinitionBean.getCreditReturn()) + "," +
     ATTRIBUTE_LOCATION_ID + "=" +
       SqlHandler.delimitString(inventoryGroupDefinitionBean.getLocationId()) + "," +
     ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getManageKitsAsSingleUnit()) + "," +
     ATTRIBUTE_DEFAULT_BUYER_ID + "=" +
      StringHandler.nullIfZero(inventoryGroupDefinitionBean.getDefaultBuyerId()) + "," +
     ATTRIBUTE_STORAGE_AREA + "=" +
       SqlHandler.delimitString(inventoryGroupDefinitionBean.getStorageArea()) + "," +
     ATTRIBUTE_ISSUE_GENERATION + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getIssueGeneration()) + "," +
     ATTRIBUTE_CREDIT_AT_OVER_COUNT + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getCreditAtOverCount()) + "," +
     ATTRIBUTE_LABEL_USE_WITH_INCLUDE_LOT + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getLabelUseWithIncludeLot()) + "," +
     ATTRIBUTE_CSR_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(inventoryGroupDefinitionBean.getCsrPersonnelId()) + "," +
     ATTRIBUTE_PO_COPY_FROM_LAST_BUY + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getPoCopyFromLastBuy()) + "," +
     ATTRIBUTE_DIRECTED_DELIVERY_POINT + "=" +
      SqlHandler.delimitString(inventoryGroupDefinitionBean.getDirectedDeliveryPoint()) + "," +
     ATTRIBUTE_DEFAULT_DBUY_BUYER + "=" +
      StringHandler.nullIfZero(inventoryGroupDefinitionBean.getDefaultDbuyBuyer()) + " " +
     "where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
       StringHandler.nullIfZero(inventoryGroupDefinitionBean.getInventoryGroup());
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = this.getDbManager().getConnection();
    Collection c = select(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection inventoryGroupDefinitionBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InventoryGroupDefinitionBean inventoryGroupDefinitionBean = new
          InventoryGroupDefinitionBean();
      load(dataSetRow, inventoryGroupDefinitionBean);
      inventoryGroupDefinitionBeanColl.add(inventoryGroupDefinitionBean);
    }

    return inventoryGroupDefinitionBeanColl;
  }

  public Collection selectForTemplateNameNCopies(String[] shipmentId) throws BaseException {

    Connection connection = this.getDbManager().getConnection();
    Collection c = selectForTemplateNameNCopies(shipmentId, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection selectForTemplateNameNCopies(String[] shipmentId, Connection conn) throws
      BaseException {

    Collection inventoryGroupDefinitionBeanColl = new Vector();

    String query = "select distinct igd.packing_list_template,igd.packing_list_num_copies, igd.transfer_packing_list_template from " + TABLE + " igd, shipment s"+
                   " where igd.inventory_group = s.inventory_group and s.shipment_id in (";
    
    for (int i = 0; i < shipmentId.length; i++) {
      query += shipmentId[i]+",";
    }
    //remove the last commas ',' and add close parathensis
    query = query.substring(0,query.length()-1)+")";
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InventoryGroupDefinitionBean inventoryGroupDefinitionBean = new
          InventoryGroupDefinitionBean();
      load(dataSetRow, inventoryGroupDefinitionBean);
      inventoryGroupDefinitionBeanColl.add(inventoryGroupDefinitionBean);
    }

    return inventoryGroupDefinitionBeanColl;
  }


  public Collection getInventoryGroups(SearchCriteria criteria) throws
      BaseException {

    Collection inventoryGroupDefinitionBeanColl = new Vector();

    String query = "select " + ATTRIBUTE_INVENTORY_GROUP + "," +
        ATTRIBUTE_HUB + "," +
        ATTRIBUTE_INVENTORY_GROUP_NAME +
        " from " + TABLE +
        " where " + ATTRIBUTE_HUB +
        " in (select BRANCH_PLANT from facility)" +
        " order by " + ATTRIBUTE_HUB + "," +
        ATTRIBUTE_INVENTORY_GROUP;
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Connection connection = getDbManager().getConnection();
    DataSet dataSet = new SqlManager().select(connection, query);
    getDbManager().returnConnection(connection);
    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InventoryGroupDefinitionBean inventoryGroupDefinitionBean = new
          InventoryGroupDefinitionBean();
      load(dataSetRow, inventoryGroupDefinitionBean);
      inventoryGroupDefinitionBeanColl.add(inventoryGroupDefinitionBean);
    }

    return inventoryGroupDefinitionBeanColl;
  }

}