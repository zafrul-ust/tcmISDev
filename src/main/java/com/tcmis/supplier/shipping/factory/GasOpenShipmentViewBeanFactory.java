package com.tcmis.supplier.shipping.factory;


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
import com.tcmis.supplier.shipping.beans.GasOpenShipmentViewBean;


/******************************************************************************
 * CLASSNAME: GasOpenShipmentViewBeanFactory <br>
 * @version: 1.0, May 28, 2008 <br>
 *****************************************************************************/


public class GasOpenShipmentViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";

	//table name
	public String TABLE = "GAS_OPEN_SHIPMENT_VIEW";


	//constructor
	public GasOpenShipmentViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, GasOpenShipmentViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(GasOpenShipmentViewBean gasOpenShipmentViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + gasOpenShipmentViewBean.getShipmentId());

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


	public int delete(GasOpenShipmentViewBean gasOpenShipmentViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + gasOpenShipmentViewBean.getShipmentId());

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
	public int insert(GasOpenShipmentViewBean gasOpenShipmentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(gasOpenShipmentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(GasOpenShipmentViewBean gasOpenShipmentViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + ")" +
			" values (" +
			gasOpenShipmentViewBean.getShipmentId() + "," +
			SqlHandler.delimitString(gasOpenShipmentViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(gasOpenShipmentViewBean.getTrackingNumber()) + "," +
			SqlHandler.delimitString(gasOpenShipmentViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(gasOpenShipmentViewBean.getShipToLocationId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(GasOpenShipmentViewBean gasOpenShipmentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(gasOpenShipmentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(GasOpenShipmentViewBean gasOpenShipmentViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(gasOpenShipmentViewBean.getShipmentId()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(gasOpenShipmentViewBean.getCarrierName()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(gasOpenShipmentViewBean.getTrackingNumber()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(gasOpenShipmentViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(gasOpenShipmentViewBean.getShipToLocationId()) + " " +
			"where " + ATTRIBUTE_SHIPMENT_ID + "=" +
				gasOpenShipmentViewBean.getShipmentId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		return select(criteria,null);

		}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
		connection = this.getDbManager().getConnection();
		c = select(criteria,sortCriteria, connection);
	}
	finally {
		this.getDbManager().returnConnection(connection);
	}
	return c;
}
public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

	Collection gasOpenShipmentViewBeanColl = new Vector();

	String query = 
		"select distinct x.* from gas_pallet_shipment_view g left join gas_open_shipment_view x on " +
		"g.ship_via_location_id = x.ship_to_location_id and g.ship_via_company_id = x.ship_to_company_id " +
		getWhereClause(criteria)+
		"order by ship_to_location_id,ship_to_comopany_id";

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
		DataSetRow dataSetRow = (DataSetRow)dataIter.next();
		GasOpenShipmentViewBean gasOpenShipmentViewBean = new GasOpenShipmentViewBean();
		load(dataSetRow, gasOpenShipmentViewBean);
		gasOpenShipmentViewBeanColl.add(gasOpenShipmentViewBean);
	}

	return gasOpenShipmentViewBeanColl;
}

public Collection selectDropDown(SearchCriteria criteria, SortCriteria sortCriteria)
throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
		connection = this.getDbManager().getConnection();
		c = selectDropDown(criteria,sortCriteria, connection);
	}
	finally {
		this.getDbManager().returnConnection(connection);
	}
	return c;
}
public Collection selectDropDown(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
throws BaseException {

	Collection gasOpenShipmentViewBeanColl = new Vector();

	String query = 
		"select distinct x.* from gas_pallet_shipment_view g, gas_open_shipment_view x " +
		getWhereClause(criteria)+
		" and g.ship_via_location_id = x.ship_to_location_id and g.ship_via_company_id = x.ship_to_company_id " +
		" order by ship_to_location_id,ship_to_company_id";

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
		DataSetRow dataSetRow = (DataSetRow)dataIter.next();
		GasOpenShipmentViewBean gasOpenShipmentViewBean = new GasOpenShipmentViewBean();
		load(dataSetRow, gasOpenShipmentViewBean);
		gasOpenShipmentViewBeanColl.add(gasOpenShipmentViewBean);
	}

	return gasOpenShipmentViewBeanColl;
}
}