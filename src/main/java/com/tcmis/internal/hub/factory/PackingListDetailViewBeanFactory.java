package com.tcmis.internal.hub.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
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
import com.tcmis.internal.hub.beans.PackingListDetailViewBean;

/******************************************************************************
 * CLASSNAME: PackingListDetailViewBeanFactory <br>
 * @version: 1.0, Apr 20, 2005 <br>
 *****************************************************************************/

public class PackingListDetailViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
  public String ATTRIBUTE_QUANTITY_DELIVERED = "QUANTITY_DELIVERED";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
  public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
  public String ATTRIBUTE_TCM_REF_NO = "TCM_REF_NO";
  public String ATTRIBUTE_QA_STATEMENT = "QA_STATEMENT";
  public String ATTRIBUTE_INBOUND_CERTIFICATE = "INBOUND_CERTIFICATE";
  public String ATTRIBUTE_USAGE_FACTOR = "USAGE_FACTOR";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";

  //table name
  public String TABLE = "PACKING_LIST_DETAIL_VIEW";

  //constructor
  public PackingListDetailViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("shipmentId")) {
      return ATTRIBUTE_SHIPMENT_ID;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("radianPo")) {
      return ATTRIBUTE_RADIAN_PO;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("receiptId")) {
      return ATTRIBUTE_RECEIPT_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("partShortName")) {
      return ATTRIBUTE_PART_SHORT_NAME;
    }
    else if (attributeName.equals("quantityDelivered")) {
      return ATTRIBUTE_QUANTITY_DELIVERED;
    }
    else if (attributeName.equals("unitOfSale")) {
      return ATTRIBUTE_UNIT_OF_SALE;
    }
    else if (attributeName.equals("deliveryPoint")) {
      return ATTRIBUTE_DELIVERY_POINT;
    }
    else if (attributeName.equals("deliveryPointDesc")) {
      return ATTRIBUTE_DELIVERY_POINT_DESC;
    }
    else if (attributeName.equals("packaging")) {
      return ATTRIBUTE_PACKAGING;
    }
    else if (attributeName.equals("mfgLot")) {
      return ATTRIBUTE_MFG_LOT;
    }
    else if (attributeName.equals("expireDate")) {
      return ATTRIBUTE_EXPIRE_DATE;
    }
    else if (attributeName.equals("tcmRefNo")) {
      return ATTRIBUTE_TCM_REF_NO;
    }
	 else if (attributeName.equals("qaStatement")) {
      return ATTRIBUTE_QA_STATEMENT;
    }
	 else if (attributeName.equals("inboundCertificate")) {
      return ATTRIBUTE_INBOUND_CERTIFICATE;
    }
	else if (attributeName.equals("usageFactor")) {
	      return ATTRIBUTE_USAGE_FACTOR;
	}
    else if (attributeName.equals("partDescription")) {
	      return ATTRIBUTE_PART_DESCRIPTION;
	}
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PackingListDetailViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(PackingListDetailViewBean packingListDetailViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
     "" + packingListDetailViewBean.getShipmentId());
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
   public int delete(PackingListDetailViewBean packingListDetailViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
     "" + packingListDetailViewBean.getShipmentId());
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
   public int insert(PackingListDetailViewBean packingListDetailViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(packingListDetailViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(PackingListDetailViewBean packingListDetailViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_SHIPMENT_ID + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_RADIAN_PO + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_RECEIPT_ID + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_PART_SHORT_NAME + "," +
     ATTRIBUTE_QUANTITY_DELIVERED + "," +
     ATTRIBUTE_UNIT_OF_SALE + "," +
     ATTRIBUTE_DELIVERY_POINT + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + ")" +
     " values (" +
     packingListDetailViewBean.getShipmentId() + "," +
     packingListDetailViewBean.getPrNumber() + "," +
     SqlHandler.delimitString(packingListDetailViewBean.getLineItem()) + "," +
     packingListDetailViewBean.getRadianPo() + "," +
     SqlHandler.delimitString(packingListDetailViewBean.getPoNumber()) + "," +
     packingListDetailViewBean.getReceiptId() + "," +
     SqlHandler.delimitString(packingListDetailViewBean.getCatPartNo()) + "," +
       SqlHandler.delimitString(packingListDetailViewBean.getPartShortName()) + "," +
     packingListDetailViewBean.getQuantityDelivered() + "," +
       SqlHandler.delimitString(packingListDetailViewBean.getUnitOfSale()) + "," +
       SqlHandler.delimitString(packingListDetailViewBean.getDeliveryPoint()) + "," +
     SqlHandler.delimitString(packingListDetailViewBean.getDeliveryPointDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(PackingListDetailViewBean packingListDetailViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(packingListDetailViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(PackingListDetailViewBean packingListDetailViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_SHIPMENT_ID + "=" +
       StringHandler.nullIfZero(packingListDetailViewBean.getShipmentId()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(packingListDetailViewBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(packingListDetailViewBean.getLineItem()) + "," +
     ATTRIBUTE_RADIAN_PO + "=" +
      StringHandler.nullIfZero(packingListDetailViewBean.getRadianPo()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(packingListDetailViewBean.getPoNumber()) + "," +
     ATTRIBUTE_RECEIPT_ID + "=" +
       StringHandler.nullIfZero(packingListDetailViewBean.getReceiptId()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(packingListDetailViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_PART_SHORT_NAME + "=" +
       SqlHandler.delimitString(packingListDetailViewBean.getPartShortName()) + "," +
     ATTRIBUTE_QUANTITY_DELIVERED + "=" +
      StringHandler.nullIfZero(packingListDetailViewBean.getQuantityDelivered()) + "," +
     ATTRIBUTE_UNIT_OF_SALE + "=" +
       SqlHandler.delimitString(packingListDetailViewBean.getUnitOfSale()) + "," +
     ATTRIBUTE_DELIVERY_POINT + "=" +
       SqlHandler.delimitString(packingListDetailViewBean.getDeliveryPoint()) + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "=" +
      SqlHandler.delimitString(packingListDetailViewBean.getDeliveryPointDesc()) + " " +
     "where " + ATTRIBUTE_SHIPMENT_ID + "=" +
      packingListDetailViewBean.getShipmentId();
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

    Collection packingListDetailViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PackingListDetailViewBean packingListDetailViewBean = new
          PackingListDetailViewBean();
      load(dataSetRow, packingListDetailViewBean);
      packingListDetailViewBeanColl.add(packingListDetailViewBean);
    }

    return packingListDetailViewBeanColl;
  }

  public Collection selectForPackingList(String[] shipmentId) throws
      BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectForPackingList(shipmentId, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectForPackingList(String[] shipmentId, Connection conn) throws
      BaseException {

    Collection packingListDetailViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " where shipment_id in (";
    for (int i = 0; i < shipmentId.length; i++) {
      query += shipmentId[i] + ",";
    }
    //remove the last commas ',' and add close parathensis
    query = query.substring(0, query.length() - 1) + ")";
	query += " order by " + ATTRIBUTE_DELIVERY_POINT + ","+ATTRIBUTE_PR_NUMBER+","+ATTRIBUTE_LINE_ITEM+"" ;

    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PackingListDetailViewBean packingListDetailViewBean = new
          PackingListDetailViewBean();
      load(dataSetRow, packingListDetailViewBean);
      packingListDetailViewBeanColl.add(packingListDetailViewBean);
    }

    return packingListDetailViewBeanColl;
  }
}