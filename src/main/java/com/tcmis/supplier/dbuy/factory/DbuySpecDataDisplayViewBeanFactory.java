package com.tcmis.supplier.dbuy.factory;


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
import com.tcmis.supplier.dbuy.beans.DbuySpecDataDisplayViewBean;


/******************************************************************************
 * CLASSNAME: DbuySpecDataDisplayViewBeanFactory <br>
 * @version: 1.0, Jun 5, 2006 <br>
 *****************************************************************************/


public class DbuySpecDataDisplayViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_DBUY_CONTRACT_ID = "DBUY_CONTRACT_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
	public String ATTRIBUTE_SPEC_LIBRARY = "SPEC_LIBRARY";
	public String ATTRIBUTE_SPEC_ID = "SPEC_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COC = "COC";
	public String ATTRIBUTE_COA = "COA";
	public String ATTRIBUTE_GENERIC_COA = "GENERIC_COA";
	public String ATTRIBUTE_GENERIC_COC = "GENERIC_COC";
	public String ATTRIBUTE_DETAIL = "DETAIL";

	//table name
	public String TABLE = "DBUY_SPEC_DATA_DISPLAY_VIEW";


	//constructor
	public DbuySpecDataDisplayViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("dbuyContractId")) {
			return ATTRIBUTE_DBUY_CONTRACT_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("supplyPath")) {
			return ATTRIBUTE_SUPPLY_PATH;
		}
		else if(attributeName.equals("specLibrary")) {
			return ATTRIBUTE_SPEC_LIBRARY;
		}
		else if(attributeName.equals("specId")) {
			return ATTRIBUTE_SPEC_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("coc")) {
			return ATTRIBUTE_COC;
		}
		else if(attributeName.equals("coa")) {
			return ATTRIBUTE_COA;
		}
		else if(attributeName.equals("genericCoa")) {
			return ATTRIBUTE_GENERIC_COA;
		}
		else if(attributeName.equals("genericCoc")) {
			return ATTRIBUTE_GENERIC_COC;
		}
		else if(attributeName.equals("detail")) {
			return ATTRIBUTE_DETAIL;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuySpecDataDisplayViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuySpecDataDisplayViewBean dbuySpecDataDisplayViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + dbuySpecDataDisplayViewBean.getItemId());

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


	public int delete(DbuySpecDataDisplayViewBean dbuySpecDataDisplayViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + dbuySpecDataDisplayViewBean.getItemId());

		return delete(criteria, conn);
	}
*/

	public int delete(SearchCriteria criteria)
		throws BaseException {

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


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//insert
	public int insert(DbuySpecDataDisplayViewBean dbuySpecDataDisplayViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuySpecDataDisplayViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuySpecDataDisplayViewBean dbuySpecDataDisplayViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_PRIORITY + "," +
			ATTRIBUTE_DBUY_CONTRACT_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_SUPPLY_PATH + "," +
			ATTRIBUTE_SPEC_LIBRARY + "," +
			ATTRIBUTE_SPEC_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_COC + "," +
			ATTRIBUTE_COA + "," +
			ATTRIBUTE_GENERIC_COA + "," +
			ATTRIBUTE_GENERIC_COC + "," +
			ATTRIBUTE_DETAIL + ")" +
 values (
			StringHandler.nullIfZero(dbuySpecDataDisplayViewBean.getItemId()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getShipToLocationId()) + "," +
			StringHandler.nullIfZero(dbuySpecDataDisplayViewBean.getPriority()) + "," +
			StringHandler.nullIfZero(dbuySpecDataDisplayViewBean.getDbuyContractId()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getStatus()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSupplyPath()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSpecLibrary()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSpecId()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getCoc()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getCoa()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getGenericCoa()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getGenericCoc()) + "," +
			SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getDetail()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuySpecDataDisplayViewBean dbuySpecDataDisplayViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuySpecDataDisplayViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuySpecDataDisplayViewBean dbuySpecDataDisplayViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(dbuySpecDataDisplayViewBean.getItemId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_PRIORITY + "=" + 
				StringHandler.nullIfZero(dbuySpecDataDisplayViewBean.getPriority()) + "," +
			ATTRIBUTE_DBUY_CONTRACT_ID + "=" + 
				StringHandler.nullIfZero(dbuySpecDataDisplayViewBean.getDbuyContractId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSupplierName()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getStatus()) + "," +
			ATTRIBUTE_SUPPLY_PATH + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSupplyPath()) + "," +
			ATTRIBUTE_SPEC_LIBRARY + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSpecLibrary()) + "," +
			ATTRIBUTE_SPEC_ID + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getSpecId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getCompanyId()) + "," +
			ATTRIBUTE_COC + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getCoc()) + "," +
			ATTRIBUTE_COA + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getCoa()) + "," +
			ATTRIBUTE_GENERIC_COA + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getGenericCoa()) + "," +
			ATTRIBUTE_GENERIC_COC + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getGenericCoc()) + "," +
			ATTRIBUTE_DETAIL + "=" + 
				SqlHandler.delimitString(dbuySpecDataDisplayViewBean.getDetail()) + " " +
			"where " + ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(dbuySpecDataDisplayViewBean.getItemId());

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection dbuySpecDataDisplayViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + " order by dbuy_contract_id";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuySpecDataDisplayViewBean dbuySpecDataDisplayViewBean = new DbuySpecDataDisplayViewBean();
			load(dataSetRow, dbuySpecDataDisplayViewBean);
			dbuySpecDataDisplayViewBeanColl.add(dbuySpecDataDisplayViewBean);
		}

		return dbuySpecDataDisplayViewBeanColl;
	}
}