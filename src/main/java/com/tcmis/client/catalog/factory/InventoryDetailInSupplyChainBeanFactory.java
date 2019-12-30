package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.InventoryDetailInSupplyChainBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: InvDetailInSupplyChainBeanFactory <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class InventoryDetailInSupplyChainBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_REF_NO = "REF_NO";
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_STATUS = "STATUS";
 public String ATTRIBUTE_QUANTITY = "QUANTITY";
 public String ATTRIBUTE_REFERENCE = "REFERENCE";
 public String ATTRIBUTE_READY_TO_SHIP_DATE = "READY_TO_SHIP_DATE";
 public String ATTRIBUTE_NOTES = "NOTES";

 //table name
 public String TABLE = "INV_DETAIL_IN_SUPPLY_CHAIN";

 //constructor
 public InventoryDetailInSupplyChainBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("refNo")) {
	 return ATTRIBUTE_REF_NO;
	}
	else if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("status")) {
	 return ATTRIBUTE_STATUS;
	}
	else if (attributeName.equals("quantity")) {
	 return ATTRIBUTE_QUANTITY;
	}
	else if (attributeName.equals("reference")) {
	 return ATTRIBUTE_REFERENCE;
	}
	else if (attributeName.equals("readyToShipDate")) {
	 return ATTRIBUTE_READY_TO_SHIP_DATE;
	}
	else if (attributeName.equals("notes")) {
	 return ATTRIBUTE_NOTES;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, InventoryDetailInSupplyChainBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(InvDetailInSupplyChainBean invDetailInSupplyChainBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("refNo", "SearchCriterion.EQUALS",
		"" + invDetailInSupplyChainBean.getRefNo());
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
	public int delete(InvDetailInSupplyChainBean invDetailInSupplyChainBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("refNo", "SearchCriterion.EQUALS",
		"" + invDetailInSupplyChainBean.getRefNo());
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
	public int insert(InvDetailInSupplyChainBean invDetailInSupplyChainBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(invDetailInSupplyChainBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(InvDetailInSupplyChainBean invDetailInSupplyChainBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_REF_NO + "," +
		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_STATUS + "," +
		ATTRIBUTE_QUANTITY + "," +
		ATTRIBUTE_REFERENCE + "," +
		ATTRIBUTE_READY_TO_SHIP_DATE + "," +
		ATTRIBUTE_NOTES + ")" +
		" values (" +
		invDetailInSupplyChainBean.getRefNo() + "," +
	 SqlHandler.delimitString(invDetailInSupplyChainBean.getInventoryGroup()) + "," +
		invDetailInSupplyChainBean.getItemId() + "," +
		SqlHandler.delimitString(invDetailInSupplyChainBean.getStatus()) + "," +
		invDetailInSupplyChainBean.getQuantity() + "," +
		SqlHandler.delimitString(invDetailInSupplyChainBean.getReference()) + "," +
		DateHandler.getOracleToDateFunction(invDetailInSupplyChainBean.getReadyToShipDate()) + "," +
		SqlHandler.delimitString(invDetailInSupplyChainBean.getNotes()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(InvDetailInSupplyChainBean invDetailInSupplyChainBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(invDetailInSupplyChainBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(InvDetailInSupplyChainBean invDetailInSupplyChainBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_REF_NO + "=" +
		 StringHandler.nullIfZero(invDetailInSupplyChainBean.getRefNo()) + "," +
		ATTRIBUTE_INVENTORY_GROUP + "=" +
	 SqlHandler.delimitString(invDetailInSupplyChainBean.getInventoryGroup()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
		 StringHandler.nullIfZero(invDetailInSupplyChainBean.getItemId()) + "," +
		ATTRIBUTE_STATUS + "=" +
		 SqlHandler.delimitString(invDetailInSupplyChainBean.getStatus()) + "," +
		ATTRIBUTE_QUANTITY + "=" +
		 StringHandler.nullIfZero(invDetailInSupplyChainBean.getQuantity()) + "," +
		ATTRIBUTE_REFERENCE + "=" +
	 SqlHandler.delimitString(invDetailInSupplyChainBean.getReference()) + "," +
		ATTRIBUTE_READY_TO_SHIP_DATE + "=" +
		 DateHandler.getOracleToDateFunction(invDetailInSupplyChainBean.getReadyToShipDate()) + "," +
		ATTRIBUTE_NOTES + "=" +
		 SqlHandler.delimitString(invDetailInSupplyChainBean.getNotes()) + " " +
		"where " + ATTRIBUTE_REF_NO + "=" +
		 invDetailInSupplyChainBean.getRefNo();
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

	Collection invDetailInSupplyChainBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 InventoryDetailInSupplyChainBean invDetailInSupplyChainBean = new
		InventoryDetailInSupplyChainBean();
	 load(dataSetRow, invDetailInSupplyChainBean);
	 invDetailInSupplyChainBeanColl.add(invDetailInSupplyChainBean);
	}

	return invDetailInSupplyChainBeanColl;
 }

 public Collection select(String query, Connection conn) throws BaseException {

	Collection invDetailInSupplyChainBeanColl = new Vector();

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 InventoryDetailInSupplyChainBean inventoryDetailInSupplyChainBean = new
		InventoryDetailInSupplyChainBean();
	 load(dataSetRow, inventoryDetailInSupplyChainBean);
	 invDetailInSupplyChainBeanColl.add(inventoryDetailInSupplyChainBean);
	}

	return invDetailInSupplyChainBeanColl;
 }

}