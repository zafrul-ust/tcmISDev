package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.InventoryDetailOnHandMaterialBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: InvDetailOnHandMaterialBeanFactory <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class InventoryDetailOnHandMaterialBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_QUANTITY = "QUANTITY";
 public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
 public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
 public String ATTRIBUTE_READY_TO_SHIP_DATE = "READY_TO_SHIP_DATE";
 public String ATTRIBUTE_REFERENCE = "REFERENCE";
 public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";

 //table name
 public String TABLE = "INV_DETAIL_ON_HAND_MATERIAL";

 //constructor
 public InventoryDetailOnHandMaterialBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("receiptId")) {
	 return ATTRIBUTE_RECEIPT_ID;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("lotStatus")) {
	 return ATTRIBUTE_LOT_STATUS;
	}
	else if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("quantity")) {
	 return ATTRIBUTE_QUANTITY;
	}
	else if (attributeName.equals("mfgLot")) {
	 return ATTRIBUTE_MFG_LOT;
	}
	else if (attributeName.equals("expireDate")) {
	 return ATTRIBUTE_EXPIRE_DATE;
	}
	else if (attributeName.equals("readyToShipDate")) {
	 return ATTRIBUTE_READY_TO_SHIP_DATE;
	}
	else if (attributeName.equals("reference")) {
	 return ATTRIBUTE_REFERENCE;
	}
	else if (attributeName.equals("dateOfReceipt")) {
	 return ATTRIBUTE_DATE_OF_RECEIPT;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, InventoryDetailOnHandMaterialBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(InvDetailOnHandMaterialBean invDetailOnHandMaterialBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("receiptId", "SearchCriterion.EQUALS",
		"" + invDetailOnHandMaterialBean.getReceiptId());
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
	public int delete(InvDetailOnHandMaterialBean invDetailOnHandMaterialBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("receiptId", "SearchCriterion.EQUALS",
		"" + invDetailOnHandMaterialBean.getReceiptId());
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

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	public int insert(InvDetailOnHandMaterialBean invDetailOnHandMaterialBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(invDetailOnHandMaterialBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(InvDetailOnHandMaterialBean invDetailOnHandMaterialBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_RECEIPT_ID + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_LOT_STATUS + "," +
		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_QUANTITY + "," +
		ATTRIBUTE_MFG_LOT + "," +
		ATTRIBUTE_EXPIRE_DATE + "," +
		ATTRIBUTE_READY_TO_SHIP_DATE + "," +
		ATTRIBUTE_REFERENCE + ")" +
		" values (" +
		invDetailOnHandMaterialBean.getReceiptId() + "," +
		invDetailOnHandMaterialBean.getItemId() + "," +
	 SqlHandler.delimitString(invDetailOnHandMaterialBean.getLotStatus()) + "," +
		SqlHandler.delimitString(invDetailOnHandMaterialBean.getInventoryGroup()) + "," +
		invDetailOnHandMaterialBean.getQuantity() + "," +
		SqlHandler.delimitString(invDetailOnHandMaterialBean.getMfgLot()) + "," +
		DateHandler.getOracleToDateFunction(invDetailOnHandMaterialBean.getExpireDate()) + "," +
		DateHandler.getOracleToDateFunction(invDetailOnHandMaterialBean.getReadyToShipDate()) + "," +
		SqlHandler.delimitString(invDetailOnHandMaterialBean.getReference()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(InvDetailOnHandMaterialBean invDetailOnHandMaterialBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(invDetailOnHandMaterialBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(InvDetailOnHandMaterialBean invDetailOnHandMaterialBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_RECEIPT_ID + "=" +
	 StringHandler.nullIfZero(invDetailOnHandMaterialBean.getReceiptId()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
		 StringHandler.nullIfZero(invDetailOnHandMaterialBean.getItemId()) + "," +
		ATTRIBUTE_LOT_STATUS + "=" +
	 SqlHandler.delimitString(invDetailOnHandMaterialBean.getLotStatus()) + "," +
		ATTRIBUTE_INVENTORY_GROUP + "=" +
		 SqlHandler.delimitString(invDetailOnHandMaterialBean.getInventoryGroup()) + "," +
		ATTRIBUTE_QUANTITY + "=" +
	 StringHandler.nullIfZero(invDetailOnHandMaterialBean.getQuantity()) + "," +
		ATTRIBUTE_MFG_LOT + "=" +
		 SqlHandler.delimitString(invDetailOnHandMaterialBean.getMfgLot()) + "," +
		ATTRIBUTE_EXPIRE_DATE + "=" +
		 DateHandler.getOracleToDateFunction(invDetailOnHandMaterialBean.getExpireDate()) + "," +
		ATTRIBUTE_READY_TO_SHIP_DATE + "=" +
		 DateHandler.getOracleToDateFunction(invDetailOnHandMaterialBean.getReadyToShipDate()) + "," +
		ATTRIBUTE_REFERENCE + "=" +
	 SqlHandler.delimitString(invDetailOnHandMaterialBean.getReference()) + " " +
		"where " + ATTRIBUTE_RECEIPT_ID + "=" +
		 invDetailOnHandMaterialBean.getReceiptId();
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

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection invDetailOnHandMaterialBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 InventoryDetailOnHandMaterialBean invDetailOnHandMaterialBean = new
		InventoryDetailOnHandMaterialBean();
	 load(dataSetRow, invDetailOnHandMaterialBean);
	 invDetailOnHandMaterialBeanColl.add(invDetailOnHandMaterialBean);
	}

	return invDetailOnHandMaterialBeanColl;
 }

 public Collection select(String query, Connection conn) throws BaseException {

	Collection invDetailOnHandMaterialBeanColl = new Vector();

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 InventoryDetailOnHandMaterialBean inventoryDetailOnHandMaterialBean = new
		InventoryDetailOnHandMaterialBean();
	 load(dataSetRow, inventoryDetailOnHandMaterialBean);
	 invDetailOnHandMaterialBeanColl.add(inventoryDetailOnHandMaterialBean);
	}

	return invDetailOnHandMaterialBeanColl;
 }

}