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
import com.tcmis.internal.hub.beans.ReceivingViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;

/******************************************************************************
 * CLASSNAME: NoBuyOrderPoReceivingViewBeanFactory <br>
 * @version: 1.0, Aug 23, 2005 <br>
 *****************************************************************************/

public class NoBuyOrderPoReceivingViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
 public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
 public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
 public String ATTRIBUTE_LAST_SUPPLIER = "LAST_SUPPLIER";
 public String ATTRIBUTE_QTY_OPEN = "QTY_OPEN";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
 public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
 public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
 public String ATTRIBUTE_TRANS_TYPE = "TRANS_TYPE";
 public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
 public String ATTRIBUTE_USE_FACILITY_ID = "USE_FACILITY_ID";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_END_DATE = "END_DATE";
 //public String ATTRIBUTE_EXPECTED = "EXPECTED";
 public String ATTRIBUTE_PACKAGING = "PACKAGING";
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
 public String ATTRIBUTE_HUB = "HUB";
 public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";
 public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
 public String ATTRIBUTE_NONINTEGER_RECEIVING = "NONINTEGER_RECEIVING";
 public String ATTRIBUTE_OPS_ENTITY_ID= "OPS_ENTITY_ID";
 public String ATTRIBUTE_OPS_COMPANY_ID= "OPS_COMPANY_ID";

 //table name
 public String TABLE = "NO_BUY_ORDER_PO_RECEIVING_VIEW";

 //constructor
 public NoBuyOrderPoReceivingViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("dateOfReceipt")) {
	 return ATTRIBUTE_DATE_OF_RECEIPT;
	}
	else if (attributeName.equals("branchPlant")) {
	 return ATTRIBUTE_BRANCH_PLANT;
	}
	else if (attributeName.equals("radianPo")) {
	 return ATTRIBUTE_RADIAN_PO;
	}
	else if (attributeName.equals("lastSupplier")) {
	 return ATTRIBUTE_LAST_SUPPLIER;
	}
	else if (attributeName.equals("qtyOpen")) {
	 return ATTRIBUTE_QTY_OPEN;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("itemDescription")) {
	 return ATTRIBUTE_ITEM_DESCRIPTION;
	}
	else if (attributeName.equals("catPartNo")) {
	 return ATTRIBUTE_CAT_PART_NO;
	}
	else if (attributeName.equals("partDescription")) {
	 return ATTRIBUTE_PART_DESCRIPTION;
	}
	else if (attributeName.equals("transType")) {
	 return ATTRIBUTE_TRANS_TYPE;
	}
	else if (attributeName.equals("lineItem")) {
	 return ATTRIBUTE_LINE_ITEM;
	}
	else if (attributeName.equals("useFacilityId")) {
	 return ATTRIBUTE_USE_FACILITY_ID;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("endDate")) {
	 return ATTRIBUTE_END_DATE;
	}
	/*else if(attributeName.equals("expected")) {
	 return ATTRIBUTE_EXPECTED;
		 }*/
	else if (attributeName.equals("packaging")) {
	 return ATTRIBUTE_PACKAGING;
	}
	else if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("inventoryGroupName")) {
	 return ATTRIBUTE_INVENTORY_GROUP_NAME;
	}
	else if (attributeName.equals("customerPo")) {
	 return ATTRIBUTE_CUSTOMER_PO;
	}
	else if (attributeName.equals("nonintegerReceiving")) {
	 return ATTRIBUTE_NONINTEGER_RECEIVING;
	}
	else if(attributeName.equals("opsEntityId")) {
	 return ATTRIBUTE_OPS_ENTITY_ID;
	}
	else if(attributeName.equals("opsCompanyId")) {
	 return ATTRIBUTE_OPS_COMPANY_ID;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, ReceivingViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(NoBuyOrderPoReceivingViewBean noBuyOrderPoReceivingViewBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("dateOfReceipt", "SearchCriterion.EQUALS",
		"" + noBuyOrderPoReceivingViewBean.getDateOfReceipt());
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
	public int delete(NoBuyOrderPoReceivingViewBean noBuyOrderPoReceivingViewBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("dateOfReceipt", "SearchCriterion.EQUALS",
		"" + noBuyOrderPoReceivingViewBean.getDateOfReceipt());
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
	 public int insert(NoBuyOrderPoReceivingViewBean noBuyOrderPoReceivingViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(noBuyOrderPoReceivingViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(NoBuyOrderPoReceivingViewBean noBuyOrderPoReceivingViewBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_DATE_OF_RECEIPT + "," +
		ATTRIBUTE_BRANCH_PLANT + "," +
		ATTRIBUTE_RADIAN_PO + "," +
		ATTRIBUTE_LAST_SUPPLIER + "," +
		ATTRIBUTE_QTY_OPEN + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_ITEM_DESCRIPTION + "," +
		ATTRIBUTE_CAT_PART_NO + "," +
		ATTRIBUTE_PART_DESCRIPTION + "," +
		ATTRIBUTE_TRANS_TYPE + "," +
		ATTRIBUTE_LINE_ITEM + "," +
		ATTRIBUTE_USE_FACILITY_ID + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_END_DATE + "," +
		ATTRIBUTE_EXPECTED + "," +
		ATTRIBUTE_PACKAGING + "," +
		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_INVENTORY_GROUP_NAME + ")" +
		" values (" +
		DateHandler.getOracleToDateFunction(noBuyOrderPoReceivingViewBean.getDateOfReceipt()) + "," +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getBranchPlant()) + "," +
		noBuyOrderPoReceivingViewBean.getRadianPo() + "," +
		SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getLastSupplier()) + "," +
		noBuyOrderPoReceivingViewBean.getQtyOpen() + "," +
		noBuyOrderPoReceivingViewBean.getItemId() + "," +
		SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getItemDescription()) + "," +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getCatPartNo()) + "," +
		SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getPartDescription()) + "," +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getTransType()) + "," +
		noBuyOrderPoReceivingViewBean.getLineItem() + "," +
		SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getUseFacilityId()) + "," +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getCompanyId()) + "," +
		DateHandler.getOracleToDateFunction(noBuyOrderPoReceivingViewBean.getEndDate()) + "," +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getExpected()) + "," +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getPackaging()) + "," +
		SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getInventoryGroup()) + "," +
		SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getInventoryGroupName()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	 public int update(NoBuyOrderPoReceivingViewBean noBuyOrderPoReceivingViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(noBuyOrderPoReceivingViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(NoBuyOrderPoReceivingViewBean noBuyOrderPoReceivingViewBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_DATE_OF_RECEIPT + "=" +
		 DateHandler.getOracleToDateFunction(noBuyOrderPoReceivingViewBean.getDateOfReceipt()) + "," +
		ATTRIBUTE_BRANCH_PLANT + "=" +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getBranchPlant()) + "," +
		ATTRIBUTE_RADIAN_PO + "=" +
	 StringHandler.nullIfZero(noBuyOrderPoReceivingViewBean.getRadianPo()) + "," +
		ATTRIBUTE_LAST_SUPPLIER + "=" +
		 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getLastSupplier()) + "," +
		ATTRIBUTE_QTY_OPEN + "=" +
	 StringHandler.nullIfZero(noBuyOrderPoReceivingViewBean.getQtyOpen()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
	 StringHandler.nullIfZero(noBuyOrderPoReceivingViewBean.getItemId()) + "," +
		ATTRIBUTE_ITEM_DESCRIPTION + "=" +
		 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getItemDescription()) + "," +
		ATTRIBUTE_CAT_PART_NO + "=" +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getCatPartNo()) + "," +
		ATTRIBUTE_PART_DESCRIPTION + "=" +
		 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getPartDescription()) + "," +
		ATTRIBUTE_TRANS_TYPE + "=" +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getTransType()) + "," +
		ATTRIBUTE_LINE_ITEM + "=" +
	 StringHandler.nullIfZero(noBuyOrderPoReceivingViewBean.getLineItem()) + "," +
		ATTRIBUTE_USE_FACILITY_ID + "=" +
		 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getUseFacilityId()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getCompanyId()) + "," +
		ATTRIBUTE_END_DATE + "=" +
		 DateHandler.getOracleToDateFunction(noBuyOrderPoReceivingViewBean.getEndDate()) + "," +
		ATTRIBUTE_EXPECTED + "=" +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getExpected()) + "," +
		ATTRIBUTE_PACKAGING + "=" +
	 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getPackaging()) + "," +
		ATTRIBUTE_INVENTORY_GROUP + "=" +
		 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getInventoryGroup()) + "," +
		ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
		 SqlHandler.delimitString(noBuyOrderPoReceivingViewBean.getInventoryGroupName()) + " " +
		"where " + ATTRIBUTE_DATE_OF_RECEIPT + "=" +
		 noBuyOrderPoReceivingViewBean.getDateOfReceipt();
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

	Collection noBuyOrderPoReceivingViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ReceivingViewBean receivingViewBean = new ReceivingViewBean();
	 load(dataSetRow, receivingViewBean);
	 noBuyOrderPoReceivingViewBeanColl.add(receivingViewBean);
	}

	return noBuyOrderPoReceivingViewBeanColl;
 }

 public Collection selectSorted(SearchCriteria criteria,
	ReceivingInputBean bean,boolean iscollection) throws BaseException {

	//construct sort by clause
	String sortBy = "";
	if (bean.getSort().equalsIgnoreCase("Date Exptd/Supplier")) {
	 sortBy = " order by " + ATTRIBUTE_LAST_SUPPLIER;
	}
	else if (bean.getSort().equalsIgnoreCase("PO/POLine")) {
	 sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM;
	}
	else if (bean.getSort().equalsIgnoreCase("Supplier/Date Exptd")) {
	 sortBy = " order by " + ATTRIBUTE_LAST_SUPPLIER;
	}
	else if (bean.getSort().equalsIgnoreCase("Item Id/Date Exptd")) {
	 sortBy = " order by " + ATTRIBUTE_ITEM_ID;
	}

	Collection noBuyOrderPoReceivingViewBeanColl = new Vector();
	String whereClause = getWhereClause(criteria);
	if (iscollection) {
	 if (whereClause.trim().length() > 0) {
		whereClause += "and ";
	 }

	 whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " +
		ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION +
		" where " + ATTRIBUTE_HUB + " = '" + bean.getSourceHub() + "' and " +
		ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() +
		"') ";
	}

	String query = "select * from " + TABLE + " " + whereClause +
	 sortBy;

	Connection connection = getDbManager().getConnection();
	DataSet dataSet = new SqlManager().select(connection, query);
	getDbManager().returnConnection(connection);

	Iterator dataIter = dataSet.iterator();
	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ReceivingViewBean receivingViewBean = new ReceivingViewBean();
	 load(dataSetRow, receivingViewBean);
	 noBuyOrderPoReceivingViewBeanColl.add(receivingViewBean);
	}

	return noBuyOrderPoReceivingViewBeanColl;
 }

 public String selectPoLineToReceive(String radianPO, String catPartNo,
	Date dateOfReceipt,String inventoryGroup) throws BaseException {
	String poLineToReceive = "";
	String query = "select fx_no_buy_order_po_line(" + radianPO + ",'" +
	 catPartNo + "',to_date('" +
	 com.tcmis.common.util.DateHandler.formatOracleDate(dateOfReceipt) +
	 "','MM/DD/YYYY'),'"+inventoryGroup+"') " + ATTRIBUTE_LINE_ITEM + " from dual";

	Connection connection = getDbManager().getConnection();
	DataSet dataSet = new SqlManager().select(connection, query);
	getDbManager().returnConnection(connection);

	Iterator dataIter = dataSet.iterator();
	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ReceivingViewBean receivingViewBean = new ReceivingViewBean();
	 load(dataSetRow, receivingViewBean);

	 poLineToReceive = "" + receivingViewBean.getLineItem() + "";
	}

	return poLineToReceive;
 }
}