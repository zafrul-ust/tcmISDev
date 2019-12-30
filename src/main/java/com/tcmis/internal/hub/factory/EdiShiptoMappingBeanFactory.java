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
import com.tcmis.internal.hub.beans.EdiShiptoMappingBean;


/******************************************************************************
 * CLASSNAME: EdiShiptoMappingBeanFactory <br>
 * @version: 1.0, May 6, 2005 <br>
 *****************************************************************************/


public class EdiShiptoMappingBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SHIPTO_PARTY_ID = "SHIPTO_PARTY_ID";
	public String ATTRIBUTE_HAAS_SHIPTO_COMPANY_ID = "HAAS_SHIPTO_COMPANY_ID";
	public String ATTRIBUTE_HAAS_SHIPTO_LOCATION_ID = "HAAS_SHIPTO_LOCATION_ID";
	public String ATTRIBUTE_DEFAULT_FACILITY_ID = "DEFAULT_FACILITY_ID";
	public String ATTRIBUTE_DEFAULT_APPLICATION = "DEFAULT_APPLICATION";
	public String ATTRIBUTE_DEFAULT_CATALOG_ID = "DEFAULT_CATALOG_ID";
	public String ATTRIBUTE_DEFAULT_DELIVERY_POINT = "DEFAULT_DELIVERY_POINT";

	//table name
	public String TABLE = "EDI_SHIPTO_MAPPING";


	//constructor
	public EdiShiptoMappingBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("shiptoPartyId")) {
			return ATTRIBUTE_SHIPTO_PARTY_ID;
		}
		else if(attributeName.equals("haasShiptoCompanyId")) {
			return ATTRIBUTE_HAAS_SHIPTO_COMPANY_ID;
		}
		else if(attributeName.equals("haasShiptoLocationId")) {
			return ATTRIBUTE_HAAS_SHIPTO_LOCATION_ID;
		}
		else if(attributeName.equals("defaultFacilityId")) {
			return ATTRIBUTE_DEFAULT_FACILITY_ID;
		}
		else if(attributeName.equals("defaultApplication")) {
			return ATTRIBUTE_DEFAULT_APPLICATION;
		}
		else if(attributeName.equals("defaultCatalogId")) {
			return ATTRIBUTE_DEFAULT_CATALOG_ID;
		}
		else if(attributeName.equals("defaultDeliveryPoint")) {
			return ATTRIBUTE_DEFAULT_DELIVERY_POINT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, EdiShiptoMappingBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(EdiShiptoMappingBean ediShiptoMappingBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + ediShiptoMappingBean.getCompanyId());

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


	public int delete(EdiShiptoMappingBean ediShiptoMappingBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + ediShiptoMappingBean.getCompanyId());

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
	public int insert(EdiShiptoMappingBean ediShiptoMappingBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(ediShiptoMappingBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(EdiShiptoMappingBean ediShiptoMappingBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_SHIPTO_PARTY_ID + "," +
			ATTRIBUTE_HAAS_SHIPTO_COMPANY_ID + "," +
			ATTRIBUTE_HAAS_SHIPTO_LOCATION_ID + "," +
			ATTRIBUTE_DEFAULT_FACILITY_ID + "," +
			ATTRIBUTE_DEFAULT_APPLICATION + "," +
			ATTRIBUTE_DEFAULT_CATALOG_ID + "," +
			ATTRIBUTE_DEFAULT_DELIVERY_POINT + ")" +
 values (
			SqlHandler.delimitString(ediShiptoMappingBean.getCompanyId()) + "," +
			SqlHandler.delimitString(ediShiptoMappingBean.getShiptoPartyId()) + "," +
			SqlHandler.delimitString(ediShiptoMappingBean.getHaasShiptoCompanyId()) + "," +
			SqlHandler.delimitString(ediShiptoMappingBean.getHaasShiptoLocationId()) + "," +
			SqlHandler.delimitString(ediShiptoMappingBean.getDefaultFacilityId()) + "," +
			SqlHandler.delimitString(ediShiptoMappingBean.getDefaultApplication()) + "," +
			SqlHandler.delimitString(ediShiptoMappingBean.getDefaultCatalogId()) + "," +
			SqlHandler.delimitString(ediShiptoMappingBean.getDefaultDeliveryPoint()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(EdiShiptoMappingBean ediShiptoMappingBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(ediShiptoMappingBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(EdiShiptoMappingBean ediShiptoMappingBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getCompanyId()) + "," +
			ATTRIBUTE_SHIPTO_PARTY_ID + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getShiptoPartyId()) + "," +
			ATTRIBUTE_HAAS_SHIPTO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getHaasShiptoCompanyId()) + "," +
			ATTRIBUTE_HAAS_SHIPTO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getHaasShiptoLocationId()) + "," +
			ATTRIBUTE_DEFAULT_FACILITY_ID + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getDefaultFacilityId()) + "," +
			ATTRIBUTE_DEFAULT_APPLICATION + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getDefaultApplication()) + "," +
			ATTRIBUTE_DEFAULT_CATALOG_ID + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getDefaultCatalogId()) + "," +
			ATTRIBUTE_DEFAULT_DELIVERY_POINT + "=" + 
				SqlHandler.delimitString(ediShiptoMappingBean.getDefaultDeliveryPoint()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				StringHandler.nullIfZero(ediShiptoMappingBean.getCompanyId());

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

		Collection ediShiptoMappingBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			EdiShiptoMappingBean ediShiptoMappingBean = new EdiShiptoMappingBean();
			load(dataSetRow, ediShiptoMappingBean);
			ediShiptoMappingBeanColl.add(ediShiptoMappingBean);
		}

		return ediShiptoMappingBeanColl;
	}
}