package com.tcmis.internal.supply.factory;

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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.SupplierContactBean;

/******************************************************************************
 * CLASSNAME: SupplierContactBeanFactory <br>
 * 
 * @version: 1.0, Aug 28, 2007 <br>
 *****************************************************************************/

public class SupplierContactBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_CONTACT_ID = "CONTACT_ID";
	public String ATTRIBUTE_CONTACT_TYPE = "CONTACT_TYPE";
	public String ATTRIBUTE_FIRST_NAME = "FIRST_NAME";
	public String ATTRIBUTE_LAST_NAME = "LAST_NAME";
	public String ATTRIBUTE_NICKNAME = "NICKNAME";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_PHONE_EXTENSION = "PHONE_EXTENSION";
	public String ATTRIBUTE_FAX = "FAX";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_PASSWORD = "PASSWORD";
	public String ATTRIBUTE_STATUS = "STATUS";

	//table name
	public String TABLE = "SUPPLIER_CONTACT";

	//constructor
	public SupplierContactBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if (attributeName.equals("contactId")) {
			return ATTRIBUTE_CONTACT_ID;
		}
		else if (attributeName.equals("contactType")) {
			return ATTRIBUTE_CONTACT_TYPE;
		}
		else if (attributeName.equals("firstName")) {
			return ATTRIBUTE_FIRST_NAME;
		}
		else if (attributeName.equals("lastName")) {
			return ATTRIBUTE_LAST_NAME;
		}
		else if (attributeName.equals("nickname")) {
			return ATTRIBUTE_NICKNAME;
		}
		else if (attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if (attributeName.equals("phoneExtension")) {
			return ATTRIBUTE_PHONE_EXTENSION;
		}
		else if (attributeName.equals("fax")) {
			return ATTRIBUTE_FAX;
		}
		else if (attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if (attributeName.equals("password")) {
			return ATTRIBUTE_PASSWORD;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierContactBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(SupplierContactBean supplierContactBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + supplierContactBean.getSupplier());

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


	public int delete(SupplierContactBean supplierContactBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + supplierContactBean.getSupplier());

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

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
	//insert
	public int insert(SupplierContactBean supplierContactBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(supplierContactBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SupplierContactBean supplierContactBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_CONTACT_ID + "," +
			ATTRIBUTE_CONTACT_TYPE + "," +
			ATTRIBUTE_FIRST_NAME + "," +
			ATTRIBUTE_LAST_NAME + "," +
			ATTRIBUTE_NICKNAME + "," +
			ATTRIBUTE_PHONE + "," +
			ATTRIBUTE_PHONE_EXTENSION + "," +
			ATTRIBUTE_FAX + "," +
			ATTRIBUTE_EMAIL + "," +
			ATTRIBUTE_PASSWORD + "," +
			ATTRIBUTE_STATUS + ")" +
			" values (" +
			SqlHandler.delimitString(supplierContactBean.getSupplier()) + "," +
			supplierContactBean.getContactId() + "," +
			SqlHandler.delimitString(supplierContactBean.getContactType()) + "," +
			SqlHandler.delimitString(supplierContactBean.getFirstName()) + "," +
			SqlHandler.delimitString(supplierContactBean.getLastName()) + "," +
			SqlHandler.delimitString(supplierContactBean.getNickname()) + "," +
			SqlHandler.delimitString(supplierContactBean.getPhone()) + "," +
			SqlHandler.delimitString(supplierContactBean.getPhoneExtension()) + "," +
			SqlHandler.delimitString(supplierContactBean.getFax()) + "," +
			SqlHandler.delimitString(supplierContactBean.getEmail()) + "," +
			SqlHandler.delimitString(supplierContactBean.getPassword()) + "," +
			SqlHandler.delimitString(supplierContactBean.getStatus()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SupplierContactBean supplierContactBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(supplierContactBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SupplierContactBean supplierContactBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER + "=" +
				SqlHandler.delimitString(supplierContactBean.getSupplier()) + "," +
			ATTRIBUTE_CONTACT_ID + "=" +
				StringHandler.nullIfZero(supplierContactBean.getContactId()) + "," +
			ATTRIBUTE_CONTACT_TYPE + "=" +
				SqlHandler.delimitString(supplierContactBean.getContactType()) + "," +
			ATTRIBUTE_FIRST_NAME + "=" +
				SqlHandler.delimitString(supplierContactBean.getFirstName()) + "," +
			ATTRIBUTE_LAST_NAME + "=" +
				SqlHandler.delimitString(supplierContactBean.getLastName()) + "," +
			ATTRIBUTE_NICKNAME + "=" +
				SqlHandler.delimitString(supplierContactBean.getNickname()) + "," +
			ATTRIBUTE_PHONE + "=" +
				SqlHandler.delimitString(supplierContactBean.getPhone()) + "," +
			ATTRIBUTE_PHONE_EXTENSION + "=" +
				SqlHandler.delimitString(supplierContactBean.getPhoneExtension()) + "," +
			ATTRIBUTE_FAX + "=" +
				SqlHandler.delimitString(supplierContactBean.getFax()) + "," +
			ATTRIBUTE_EMAIL + "=" +
				SqlHandler.delimitString(supplierContactBean.getEmail()) + "," +
			ATTRIBUTE_PASSWORD + "=" +
				SqlHandler.delimitString(supplierContactBean.getPassword()) + "," +
			ATTRIBUTE_STATUS + "=" +
				SqlHandler.delimitString(supplierContactBean.getStatus()) + " " +
			"where " + ATTRIBUTE_SUPPLIER + "=" +
				supplierContactBean.getSupplier();

		return new SqlManager().update(conn, query);
	}
	 */

	public Collection select(String supplier, String searchArgument) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(supplier, searchArgument, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(String supplier, String searchArgument, Connection conn) throws BaseException {
		Collection supplierContactBeanColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder("select * from "); // + TABLE + " " +
		queryBuffer.append(TABLE);
		queryBuffer.append(" where SUPPLIER = '");
		queryBuffer.append(supplier);

		queryBuffer.append("' and ( lower(NICKNAME||LAST_NAME||FIRST_NAME) like lower('%");
		queryBuffer.append(searchArgument);
		queryBuffer.append("%') and CONTACT_TYPE <> 'In Active') order by NICKNAME asc");

		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString());

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			SupplierContactBean supplierContactBean = new SupplierContactBean();
			load(dataSetRow, supplierContactBean);
			//			log.debug("supplierContactBean.toString() = [" + supplierContactBean.toString() + "]; ");
			supplierContactBeanColl.add(supplierContactBean);
		}
		return supplierContactBeanColl;
	}

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection supplierContactBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			SupplierContactBean supplierContactBean = new SupplierContactBean();
			load(dataSetRow, supplierContactBean);
			supplierContactBeanColl.add(supplierContactBean);
		}

		return supplierContactBeanColl;
	}
}