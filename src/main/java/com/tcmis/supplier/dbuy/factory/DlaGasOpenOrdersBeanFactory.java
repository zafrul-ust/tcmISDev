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
import com.tcmis.supplier.dbuy.beans.DlaGasOpenOrdersBean;


/******************************************************************************
 * CLASSNAME: DlaGasOpenOrdersBeanFactory <br>
 * @version: 1.0, Feb 21, 2008 <br>
 *****************************************************************************/


public class DlaGasOpenOrdersBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_DATE_ACKNOWLEDGEMENT = "DATE_ACKNOWLEDGEMENT";
	public String ATTRIBUTE_DATE_CONFIRMED = "DATE_CONFIRMED";
        public String ATTRIBUTE_SHIP_DATE = "SHIP_DATE";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_STATUS = "STATUS";
        public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
        public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";

	//table name
	public String TABLE = "DLA_GAS_OPEN_ORDERS";


	//constructor
	public DlaGasOpenOrdersBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("dateAcknowledgement")) {
			return ATTRIBUTE_DATE_ACKNOWLEDGEMENT;
		}
		else if(attributeName.equals("dateConfirmed")) {
			return ATTRIBUTE_DATE_CONFIRMED;
		}
		else if(attributeName.equals("shipDate")) {
			return ATTRIBUTE_SHIP_DATE;
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
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DlaGasOpenOrdersBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DlaGasOpenOrdersBean dlaGasOpenOrdersBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dlaGasOpenOrdersBean.getRadianPo());

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


	public int delete(DlaGasOpenOrdersBean dlaGasOpenOrdersBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dlaGasOpenOrdersBean.getRadianPo());

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
	public int insert(DlaGasOpenOrdersBean dlaGasOpenOrdersBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dlaGasOpenOrdersBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DlaGasOpenOrdersBean dlaGasOpenOrdersBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "," +
			ATTRIBUTE_DATE_CONFIRMED + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_STATUS + ")" +
 values (
			StringHandler.nullIfZero(dlaGasOpenOrdersBean.getRadianPo()) + "," +
			SqlHandler.delimitString(dlaGasOpenOrdersBean.getCustomerPo()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasOpenOrdersBean.getDateSent()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasOpenOrdersBean.getDateAcknowledgement()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasOpenOrdersBean.getDateConfirmed()) + "," +
			SqlHandler.delimitString(dlaGasOpenOrdersBean.getSupplier()) + "," +
			SqlHandler.delimitString(dlaGasOpenOrdersBean.getSupplierName()) + "," +
			SqlHandler.delimitString(dlaGasOpenOrdersBean.getStatus()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DlaGasOpenOrdersBean dlaGasOpenOrdersBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dlaGasOpenOrdersBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DlaGasOpenOrdersBean dlaGasOpenOrdersBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dlaGasOpenOrdersBean.getRadianPo()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(dlaGasOpenOrdersBean.getCustomerPo()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasOpenOrdersBean.getDateSent()) + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasOpenOrdersBean.getDateAcknowledgement()) + "," +
			ATTRIBUTE_DATE_CONFIRMED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasOpenOrdersBean.getDateConfirmed()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(dlaGasOpenOrdersBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dlaGasOpenOrdersBean.getSupplierName()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(dlaGasOpenOrdersBean.getStatus()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				StringHandler.nullIfZero(dlaGasOpenOrdersBean.getRadianPo());

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

		Collection dlaGasOpenOrdersBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DlaGasOpenOrdersBean dlaGasOpenOrdersBean = new DlaGasOpenOrdersBean();
			load(dataSetRow, dlaGasOpenOrdersBean);
			dlaGasOpenOrdersBeanColl.add(dlaGasOpenOrdersBean);
		}

		return dlaGasOpenOrdersBeanColl;
	}
        
	public Collection select()
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
        
 	public Collection select(Connection conn)
		throws BaseException {

		Collection dlaGasOpenOrdersBeanColl = new Vector();

		String query = "SELECT  x.radian_po, x.customer_po, date_sent, x.date_acknowledgement, ";
                query += "(SELECT MAX (xx.date_confirmed) ";
                query += "FROM po_line xx ";
                query += "WHERE xx.radian_po = x.radian_po) date_confirmed, ";
                query += "(select MAX(promised_date) from dbuy_acknowledgement dx where dx.radian_po=x.radian_po) ship_date, ";
                query += "z.supplier, z.supplier_name, y.status,x.ship_to_company_id, x.ship_to_location_id ";
                query += "FROM po x, buy_order y, supplier z ";
                query += "WHERE x.radian_po = y.radian_po AND ";
                query += "y.supply_path = 'Dbuy' AND ";
                query += "x.supplier = z.supplier AND ";
                query += "y.status <> 'Closed' AND ";
                query += "z.transport = 'VAN' AND ";
                query += "z.supplier in ('10022037','10022038') AND ";
                query += "date_sent is not null AND ";
                query += "x.radian_po NOT IN (select distinct radian_po from advance_shipment_notice)";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DlaGasOpenOrdersBean dlaGasOpenOrdersBean = new DlaGasOpenOrdersBean();
			load(dataSetRow, dlaGasOpenOrdersBean);
			dlaGasOpenOrdersBeanColl.add(dlaGasOpenOrdersBean);
		}

		return dlaGasOpenOrdersBeanColl;
	}
               
}