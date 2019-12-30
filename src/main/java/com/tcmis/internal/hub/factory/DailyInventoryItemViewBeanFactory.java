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

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.hub.beans.DailyInventoryItemViewBean;


/******************************************************************************
 * CLASSNAME: DailyInventoryItemViewBeanFactory <br>
 * @version: 1.0, Jun 15, 2006 <br>
 *****************************************************************************/


public class DailyInventoryItemViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DAILY_DATE = "DAILY_DATE";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_HAAS_MATERIAL_VALUE = "HAAS_MATERIAL_VALUE";
	public String ATTRIBUTE_CUST_MATERIAL_VALUE = "CUST_MATERIAL_VALUE";
	public String ATTRIBUTE_CONS_MATERIAL_VALUE = "CONS_MATERIAL_VALUE";
	public String ATTRIBUTE_SUM_MATERIAL_VALUE = "SUM_MATERIAL_VALUE";
	public String ATTRIBUTE_HAAS_FULL_VALUE = "HAAS_FULL_VALUE";
	public String ATTRIBUTE_CUSTOMER_FULL_VALUE = "CUSTOMER_FULL_VALUE";
	public String ATTRIBUTE_CONSIGNED_FULL_VALUE = "CONSIGNED_FULL_VALUE";
	public String ATTRIBUTE_SUM_FULL_VALUE = "SUM_FULL_VALUE";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_CUSTOMER_OWENED_QTY = "CUSTOMER_OWENED_QTY";
	public String ATTRIBUTE_HAAS_OWENED_QTY = "HAAS_OWENED_QTY";
	public String ATTRIBUTE_CONSIGENED_QTY = "CONSIGENED_QTY";


	//table name
	public String TABLE = "DAILY_INVENTORY_ITEM_VIEW";


	//constructor
	public DailyInventoryItemViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("dailyDate")) {
			return ATTRIBUTE_DAILY_DATE;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("haasMaterialValue")) {
			return ATTRIBUTE_HAAS_MATERIAL_VALUE;
		}
		else if(attributeName.equals("custMaterialValue")) {
			return ATTRIBUTE_CUST_MATERIAL_VALUE;
		}
		else if(attributeName.equals("consMaterialValue")) {
			return ATTRIBUTE_CONS_MATERIAL_VALUE;
		}
		else if(attributeName.equals("sumMaterialValue")) {
			return ATTRIBUTE_SUM_MATERIAL_VALUE;
		}
		else if(attributeName.equals("haasFullValue")) {
			return ATTRIBUTE_HAAS_FULL_VALUE;
		}
		else if(attributeName.equals("customerFullValue")) {
			return ATTRIBUTE_CUSTOMER_FULL_VALUE;
		}
		else if(attributeName.equals("consignedFullValue")) {
			return ATTRIBUTE_CONSIGNED_FULL_VALUE;
		}
		else if(attributeName.equals("sumFullValue")) {
			return ATTRIBUTE_SUM_FULL_VALUE;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("customerOwenedQty")) {
			return ATTRIBUTE_CUSTOMER_OWENED_QTY;
		}
		else if(attributeName.equals("haasOwenedQty")) {
			return ATTRIBUTE_HAAS_OWENED_QTY;
		}
		else if(attributeName.equals("consignedQty")) {
			return ATTRIBUTE_CONSIGENED_QTY;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DailyInventoryItemViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DailyInventoryItemViewBean dailyInventoryItemViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dailyDate", "SearchCriterion.EQUALS",
			"" + dailyInventoryItemViewBean.getDailyDate());

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

	public int delete(DailyInventoryItemViewBean dailyInventoryItemViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dailyDate", "SearchCriterion.EQUALS",
			"" + dailyInventoryItemViewBean.getDailyDate());

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
	public int insert(DailyInventoryItemViewBean dailyInventoryItemViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dailyInventoryItemViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DailyInventoryItemViewBean dailyInventoryItemViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DAILY_DATE + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_HAAS_MATERIAL_VALUE + "," +
			ATTRIBUTE_CUST_MATERIAL_VALUE + "," +
			ATTRIBUTE_CONS_MATERIAL_VALUE + "," +
			ATTRIBUTE_SUM_MATERIAL_VALUE + "," +
			ATTRIBUTE_HAAS_FULL_VALUE + "," +
			ATTRIBUTE_CUSTOMER_FULL_VALUE + "," +
			ATTRIBUTE_CONSIGNED_FULL_VALUE + "," +
			ATTRIBUTE_SUM_FULL_VALUE + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_ITEM_DESC + ")" +
			" values (" +
			DateHandler.getOracleToDateFunction(dailyInventoryItemViewBean.getDailyDate()) + "," +
			SqlHandler.delimitString(dailyInventoryItemViewBean.getInventoryGroup()) + "," +
			dailyInventoryItemViewBean.getItemId() + "," +
			dailyInventoryItemViewBean.getQuantity() + "," +
			dailyInventoryItemViewBean.getHaasMaterialValue() + "," +
			dailyInventoryItemViewBean.getCustMaterialValue() + "," +
			dailyInventoryItemViewBean.getConsMaterialValue() + "," +
			dailyInventoryItemViewBean.getSumMaterialValue() + "," +
			dailyInventoryItemViewBean.getHaasFullValue() + "," +
			dailyInventoryItemViewBean.getCustomerFullValue() + "," +
			dailyInventoryItemViewBean.getConsignedFullValue() + "," +
			dailyInventoryItemViewBean.getSumFullValue() + "," +
			SqlHandler.delimitString(dailyInventoryItemViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(dailyInventoryItemViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(dailyInventoryItemViewBean.getItemDesc()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DailyInventoryItemViewBean dailyInventoryItemViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dailyInventoryItemViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DailyInventoryItemViewBean dailyInventoryItemViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_DAILY_DATE + "=" +
				DateHandler.getOracleToDateFunction(dailyInventoryItemViewBean.getDailyDate()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(dailyInventoryItemViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getItemId()) + "," +
			ATTRIBUTE_QUANTITY + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getQuantity()) + "," +
			ATTRIBUTE_HAAS_MATERIAL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getHaasMaterialValue()) + "," +
			ATTRIBUTE_CUST_MATERIAL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getCustMaterialValue()) + "," +
			ATTRIBUTE_CONS_MATERIAL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getConsMaterialValue()) + "," +
			ATTRIBUTE_SUM_MATERIAL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getSumMaterialValue()) + "," +
			ATTRIBUTE_HAAS_FULL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getHaasFullValue()) + "," +
			ATTRIBUTE_CUSTOMER_FULL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getCustomerFullValue()) + "," +
			ATTRIBUTE_CONSIGNED_FULL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getConsignedFullValue()) + "," +
			ATTRIBUTE_SUM_FULL_VALUE + "=" +
				StringHandler.nullIfZero(dailyInventoryItemViewBean.getSumFullValue()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(dailyInventoryItemViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PACKAGING + "=" +
				SqlHandler.delimitString(dailyInventoryItemViewBean.getPackaging()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" +
				SqlHandler.delimitString(dailyInventoryItemViewBean.getItemDesc()) + " " +
			"where " + ATTRIBUTE_DAILY_DATE + "=" +
				dailyInventoryItemViewBean.getDailyDate();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {

	 Connection connection = null;
	 Collection c = null;
	 try {
		connection = this.getDbManager().getConnection();
		c = select(criteria, null, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return c;
	}

	public Collection select(SearchCriteria criteria,
	 SortCriteria sortCriteria) throws BaseException {

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
	 Connection conn) throws BaseException {

	 Collection dailyInventoryItemViewBeanColl = new Vector();

	 String query = "select * from " + TABLE + " " +
		getWhereClause(criteria);
	 if (sortCriteria != null) {
		query += getOrderByClause(sortCriteria);
	 }

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DailyInventoryItemViewBean dailyInventoryItemViewBean = new DailyInventoryItemViewBean();
			load(dataSetRow, dailyInventoryItemViewBean);
			dailyInventoryItemViewBeanColl.add(dailyInventoryItemViewBean);
		}

		return dailyInventoryItemViewBeanColl;
	}
}