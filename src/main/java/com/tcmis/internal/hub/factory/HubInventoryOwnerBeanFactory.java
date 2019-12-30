package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;
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
import com.tcmis.internal.hub.beans.HubInventoryOwnerBean;

/******************************************************************************
 * CLASSNAME: HubInventoryOwnerBeanFactory <br>
 * @version: 1.0, Sep 7, 2005 <br>
 *****************************************************************************/

public class HubInventoryOwnerBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_HUB = "HUB";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
 public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
 public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
 public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
 public String ATTRIBUTE_BLANKET_MR = "BLANKET_MR";
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
 public String ATTRIBUTE_STATUS = "STATUS";

 //table name
 public String TABLE = "HUB_INVENTORY_OWNER";

 //constructor
 public HubInventoryOwnerBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("hub")) {
	 return ATTRIBUTE_HUB;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("accountSysId")) {
	 return ATTRIBUTE_ACCOUNT_SYS_ID;
	}
	else if (attributeName.equals("chargeType")) {
	 return ATTRIBUTE_CHARGE_TYPE;
	}
	else if (attributeName.equals("accountNumber")) {
	 return ATTRIBUTE_ACCOUNT_NUMBER;
	}
	else if (attributeName.equals("accountNumber2")) {
	 return ATTRIBUTE_ACCOUNT_NUMBER2;
	}
	else if (attributeName.equals("blanketMr")) {
	 return ATTRIBUTE_BLANKET_MR;
	}
	else if (attributeName.equals("catalogId")) {
	 return ATTRIBUTE_CATALOG_ID;
	}
	else if (attributeName.equals("partGroupNo")) {
	 return ATTRIBUTE_PART_GROUP_NO;
	}
	else if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("poNumber")) {
	 return ATTRIBUTE_PO_NUMBER;
	}
	else if(attributeName.equals("status")) {
	 return ATTRIBUTE_STATUS;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, HubInventoryOwnerBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(HubInventoryOwnerBean hubInventoryOwnerBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
		"" + hubInventoryOwnerBean.getHub());
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
	 public int delete(HubInventoryOwnerBean hubInventoryOwnerBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
		"" + hubInventoryOwnerBean.getHub());
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
	public int insert(HubInventoryOwnerBean hubInventoryOwnerBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(hubInventoryOwnerBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	 public int insert(HubInventoryOwnerBean hubInventoryOwnerBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_HUB + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_ACCOUNT_SYS_ID + "," +
		ATTRIBUTE_CHARGE_TYPE + "," +
		ATTRIBUTE_ACCOUNT_NUMBER + "," +
		ATTRIBUTE_ACCOUNT_NUMBER2 + "," +
		ATTRIBUTE_BLANKET_MR + "," +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_FACILITY_ID + "," +
		ATTRIBUTE_PO_NUMBER + ")" +
		" values (" +
		SqlHandler.delimitString(hubInventoryOwnerBean.getHub()) + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getCompanyId()) + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getAccountSysId()) + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getChargeType()) + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getAccountNumber()) + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getAccountNumber2()) + "," +
		hubInventoryOwnerBean.getBlanketMr() + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getCatalogId()) + "," +
		hubInventoryOwnerBean.getPartGroupNo() + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getFacilityId()) + "," +
		SqlHandler.delimitString(hubInventoryOwnerBean.getPoNumber()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(HubInventoryOwnerBean hubInventoryOwnerBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(hubInventoryOwnerBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	 public int update(HubInventoryOwnerBean hubInventoryOwnerBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_HUB + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getHub()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getCompanyId()) + "," +
		ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getAccountSysId()) + "," +
		ATTRIBUTE_CHARGE_TYPE + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getChargeType()) + "," +
		ATTRIBUTE_ACCOUNT_NUMBER + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getAccountNumber()) + "," +
		ATTRIBUTE_ACCOUNT_NUMBER2 + "=" +
	 SqlHandler.delimitString(hubInventoryOwnerBean.getAccountNumber2()) + "," +
		ATTRIBUTE_BLANKET_MR + "=" +
		 StringHandler.nullIfZero(hubInventoryOwnerBean.getBlanketMr()) + "," +
		ATTRIBUTE_CATALOG_ID + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getCatalogId()) + "," +
		ATTRIBUTE_PART_GROUP_NO + "=" +
		 StringHandler.nullIfZero(hubInventoryOwnerBean.getPartGroupNo()) + "," +
		ATTRIBUTE_FACILITY_ID + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getFacilityId()) + "," +
		ATTRIBUTE_PO_NUMBER + "=" +
		 SqlHandler.delimitString(hubInventoryOwnerBean.getPoNumber()) + " " +
		"where " + ATTRIBUTE_HUB + "=" +
		 hubInventoryOwnerBean.getHub();
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

	Collection hubInventoryOwnerBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 HubInventoryOwnerBean hubInventoryOwnerBean = new HubInventoryOwnerBean();
	 load(dataSetRow, hubInventoryOwnerBean);
	 hubInventoryOwnerBeanColl.add(hubInventoryOwnerBean);
	}

	return hubInventoryOwnerBeanColl;
 }

 //select Distinct
 public Collection selectDistinct(SearchCriteria criteria) throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = selectDistinct(criteria, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection selectDistinct(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection hubInventoryOwnerBeanColl = new Vector();

	String query = "select distinct " + ATTRIBUTE_COMPANY_ID + " from " + TABLE +
	 " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 HubInventoryOwnerBean hubInventoryOwnerBean = new HubInventoryOwnerBean();
	 load(dataSetRow, hubInventoryOwnerBean);
	 hubInventoryOwnerBeanColl.add(hubInventoryOwnerBean);
	}

	return hubInventoryOwnerBeanColl;
 }

 public Collection convertReceiptToCustomerOwened(BigDecimal receiptId,
	String coustomerOwnedCompanyId) throws Exception {
	//Connection connection = this.getDbManager().getConnection();
	Collection cin = new Vector();
	log.info("coustomerOwnedCompanyId  " + coustomerOwnedCompanyId +
	 " receiptId  " + receiptId + "");
	if (coustomerOwnedCompanyId != null) {
	 cin.add(coustomerOwnedCompanyId);
	}
	else {
	 cin.add("");
	}

	if (receiptId != null) {
	 cin.add(receiptId);
	}
	else {
	 cin.add("");
	}

	Collection cout = new Vector();
	cout.add(new Integer(java.sql.Types.NUMERIC));
	cout.add(new Integer(java.sql.Types.NUMERIC));
	cout.add(new Integer(java.sql.Types.VARCHAR));

	Collection result = this.getDbManager().doProcedure(
	 "P_CONVERT_RECEIPT_TO_CUSTOMER", cin, cout);

	//this.getDbManager().returnConnection(connection);
	return result;
 }
}