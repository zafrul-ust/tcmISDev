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
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.hub.beans.CountScanLoadBean;


/******************************************************************************
 * CLASSNAME: CountScanLoadBeanFactory <br>
 * @version: 1.0, Oct 12, 2006 <br>
 *****************************************************************************/


public class CountScanLoadBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DATE_COUNTED = "DATE_COUNTED";
	public String ATTRIBUTE_TYPE_ITEM_OR_PN = "TYPE_ITEM_OR_PN";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_LOAD_INVENTORY_GROUP = "LOAD_INVENTORY_GROUP";
	public String ATTRIBUTE_LOAD_ID = "LOAD_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_COUNT_TYPE = "COUNT_TYPE";
	public String ATTRIBUTE_UOM_COUNTED_QUANTITY = "UOM_COUNTED_QUANTITY";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_DATETIME_LOADED = "DATETIME_LOADED";
	public String ATTRIBUTE_COUNT_ID = "COUNT_ID";
	public String ATTRIBUTE_DATETIME_PROCESSED = "DATETIME_PROCESSED";
	public String ATTRIBUTE_PROCESS_STATEMENT = "PROCESS_STATEMENT";
	public String ATTRIBUTE_SCAN_DATE = "SCAN_DATE";

	//table name
	public String TABLE = "COUNT_SCAN_LOAD";


	//constructor
	public CountScanLoadBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("dateCounted")) {
			return ATTRIBUTE_DATE_COUNTED;
		}
		else if(attributeName.equals("typeItemOrPn")) {
			return ATTRIBUTE_TYPE_ITEM_OR_PN;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("loadInventoryGroup")) {
		 return ATTRIBUTE_LOAD_INVENTORY_GROUP;
		}
		else if(attributeName.equals("loadId")) {
			return ATTRIBUTE_LOAD_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("countType")) {
			return ATTRIBUTE_COUNT_TYPE;
		}
		else if(attributeName.equals("uomCountedQuantity")) {
			return ATTRIBUTE_UOM_COUNTED_QUANTITY;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("datetimeLoaded")) {
			return ATTRIBUTE_DATETIME_LOADED;
		}
		else if(attributeName.equals("countId")) {
			return ATTRIBUTE_COUNT_ID;
		}
		else if(attributeName.equals("datetimeProcessed")) {
			return ATTRIBUTE_DATETIME_PROCESSED;
		}
		else if(attributeName.equals("processStatement")) {
			return ATTRIBUTE_PROCESS_STATEMENT;
		}
		else if(attributeName.equals("scanDate")) {
		 return ATTRIBUTE_SCAN_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CountScanLoadBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CountScanLoadBean countScanLoadBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dateCounted", "SearchCriterion.EQUALS",
			"" + countScanLoadBean.getDateCounted());

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


	public int delete(CountScanLoadBean countScanLoadBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dateCounted", "SearchCriterion.EQUALS",
			"" + countScanLoadBean.getDateCounted());

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

	//insert
	public int insert(CountScanLoadBean countScanLoadBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(countScanLoadBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CountScanLoadBean countScanLoadBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DATE_COUNTED + "," +
			ATTRIBUTE_SCAN_DATE + "," +
			ATTRIBUTE_TYPE_ITEM_OR_PN + "," +
			ATTRIBUTE_LOAD_INVENTORY_GROUP + "," +
			ATTRIBUTE_LOAD_ID + "," +
			/*ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_COUNT_TYPE + "," +*/
			ATTRIBUTE_UOM_COUNTED_QUANTITY + "," +
			/*ATTRIBUTE_DATETIME_LOADED + "," +
			ATTRIBUTE_COUNT_ID + "," +
			ATTRIBUTE_DATETIME_PROCESSED + "," +
			ATTRIBUTE_PROCESS_STATEMENT + ")" +*/
    	ATTRIBUTE_PERSONNEL_ID + ")" +
			" values (" +
			DateHandler.getOracleToDateFunction(countScanLoadBean.getDateCounted()) + "," +
			DateHandler.getOracleToDateFunction(countScanLoadBean.getScanDate()) + "," +
			SqlHandler.delimitString(countScanLoadBean.getTypeItemOrPn()) + "," +
			SqlHandler.delimitString(countScanLoadBean.getLoadInventoryGroup()) + "," +
			countScanLoadBean.getLoadId() + "," +
			/*SqlHandler.delimitString(countScanLoadBean.getCompanyId()) + "," +
			SqlHandler.delimitString(countScanLoadBean.getCatalogId()) + "," +
			SqlHandler.delimitString(countScanLoadBean.getCatPartNo()) + "," +
			countScanLoadBean.getPartGroupNo() + "," +
			countScanLoadBean.getItemId() + "," +
			SqlHandler.delimitString(countScanLoadBean.getCountType()) + "," +*/
			countScanLoadBean.getUomCountedQuantity() + "," +
			/*DateHandler.getOracleToDateFunction(countScanLoadBean.getDatetimeLoaded()) + "," +
			countScanLoadBean.getCountId() + "," +
			DateHandler.getOracleToDateFunction(countScanLoadBean.getDatetimeProcessed()) + "," +
      SqlHandler.delimitString(countScanLoadBean.getProcessStatement()) + */
			countScanLoadBean.getPersonnelId() + ")";

		return sqlManager.update(conn, query);
	}

/*
	//update
	public int update(CountScanLoadBean countScanLoadBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(countScanLoadBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CountScanLoadBean countScanLoadBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_DATE_COUNTED + "=" +
				DateHandler.getOracleToDateFunction(countScanLoadBean.getDateCounted()) + "," +
			ATTRIBUTE_TYPE_ITEM_OR_PN + "=" +
				SqlHandler.delimitString(countScanLoadBean.getTypeItemOrPn()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(countScanLoadBean.getInventoryGroup()) + "," +
			ATTRIBUTE_LOAD_ID + "=" +
				StringHandler.nullIfZero(countScanLoadBean.getLoadId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(countScanLoadBean.getCompanyId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(countScanLoadBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(countScanLoadBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" +
				StringHandler.nullIfZero(countScanLoadBean.getPartGroupNo()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(countScanLoadBean.getItemId()) + "," +
			ATTRIBUTE_COUNT_TYPE + "=" +
				SqlHandler.delimitString(countScanLoadBean.getCountType()) + "," +
			ATTRIBUTE_UOM_COUNTED_QUANTITY + "=" +
				StringHandler.nullIfZero(countScanLoadBean.getUomCountedQuantity()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(countScanLoadBean.getPersonnelId()) + "," +
			ATTRIBUTE_DATETIME_LOADED + "=" +
				DateHandler.getOracleToDateFunction(countScanLoadBean.getDatetimeLoaded()) + "," +
			ATTRIBUTE_COUNT_ID + "=" +
				StringHandler.nullIfZero(countScanLoadBean.getCountId()) + "," +
			ATTRIBUTE_DATETIME_PROCESSED + "=" +
				DateHandler.getOracleToDateFunction(countScanLoadBean.getDatetimeProcessed()) + "," +
			ATTRIBUTE_PROCESS_STATEMENT + "=" +
				SqlHandler.delimitString(countScanLoadBean.getProcessStatement()) + " " +
			"where " + ATTRIBUTE_DATE_COUNTED + "=" +
				countScanLoadBean.getDateCounted();

		return new SqlManager().update(conn, query);
	}
*/

	/*Getting the next upload Id*/
		 public BigDecimal getUploadId()
			throws BaseException {
			Connection connection = null;
			int result = 0;
			try {
			 connection = getDbManager().getConnection();
			 result = getDbManager().getOracleSequence("count_scan_load_seq");
			}
			finally {
			 this.getDbManager().returnConnection(connection);
			}
			return new BigDecimal(result);
		 }

/*Call P_COUNT_SCAN_PROCESS */
		 public Collection processScan(BigDecimal uploadId) throws
			Exception {
			Connection connection = this.getDbManager().getConnection();
			Collection cin = new Vector();
			String errorMessage = "";
			cin.add(uploadId);
			Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.NUMERIC));
			log.info("Calling P_COUNT_SCAN_PROCESS for uploadid "+uploadId+"");
			Collection result = this.getDbManager().doProcedure("P_COUNT_SCAN_PROCESS",
			 cin, cout);
			this.getDbManager().returnConnection(connection);
			return result;
		 }

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

		Collection countScanLoadBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CountScanLoadBean countScanLoadBean = new CountScanLoadBean();
			load(dataSetRow, countScanLoadBean);
			countScanLoadBeanColl.add(countScanLoadBean);
		}

		return countScanLoadBeanColl;
	}
}