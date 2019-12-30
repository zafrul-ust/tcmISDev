package com.tcmis.client.waste.factory;


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
import com.tcmis.client.waste.beans.WasteTsdfSourceViewBean;


/******************************************************************************
 * CLASSNAME: WasteTsdfSourceViewBeanFactory <br>
 * @version: 1.0, Jan 19, 2007 <br>
 *****************************************************************************/


public class WasteTsdfSourceViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_TSDF_COMPANY_ID = "TSDF_COMPANY_ID";
	public String ATTRIBUTE_TSDF_EPA_ID = "TSDF_EPA_ID";
	public String ATTRIBUTE_TSDF_NAME = "TSDF_NAME";
	public String ATTRIBUTE_TSDF_FACILITY_ID = "TSDF_FACILITY_ID";
	public String ATTRIBUTE_TSDF_LOCATION_DESC = "TSDF_LOCATION_DESC";
	public String ATTRIBUTE_TSDF_LOCATION_STATUS = "TSDF_LOCATION_STATUS";
	public String ATTRIBUTE_GENERATOR_COMPANY_ID = "GENERATOR_COMPANY_ID";
	public String ATTRIBUTE_GENERATOR_FACILITY_ID = "GENERATOR_FACILITY_ID";
	public String ATTRIBUTE_GENERATOR_WASTE_LOCATION_ID = "GENERATOR_WASTE_LOCATION_ID";
	public String ATTRIBUTE_TSDF_FACILITY_ID_FOR_GENERATOR = "TSDF_FACILITY_ID_FOR_GENERATOR";
	public String ATTRIBUTE_GENERATOR_ACTIVE = "GENERATOR_ACTIVE";
	public String ATTRIBUTE_GENERATOR_LOCATION_DESC = "GENERATOR_LOCATION_DESC";
	public String ATTRIBUTE_GENERATOR_LOCATION_STATUS = "GENERATOR_LOCATION_STATUS";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
	public String ATTRIBUTE_ADMIN = "ADMIN";

	//table name
	public String TABLE = "WASTE_TSDF_SOURCE_VIEW";


	//constructor
	public WasteTsdfSourceViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("tsdfCompanyId")) {
			return ATTRIBUTE_TSDF_COMPANY_ID;
		}
		else if(attributeName.equals("tsdfEpaId")) {
			return ATTRIBUTE_TSDF_EPA_ID;
		}
		else if(attributeName.equals("tsdfName")) {
			return ATTRIBUTE_TSDF_NAME;
		}
		else if(attributeName.equals("tsdfFacilityId")) {
			return ATTRIBUTE_TSDF_FACILITY_ID;
		}
		else if(attributeName.equals("tsdfLocationDesc")) {
			return ATTRIBUTE_TSDF_LOCATION_DESC;
		}
		else if(attributeName.equals("tsdfLocationStatus")) {
			return ATTRIBUTE_TSDF_LOCATION_STATUS;
		}
		else if(attributeName.equals("generatorCompanyId")) {
			return ATTRIBUTE_GENERATOR_COMPANY_ID;
		}
		else if(attributeName.equals("generatorFacilityId")) {
			return ATTRIBUTE_GENERATOR_FACILITY_ID;
		}
		else if(attributeName.equals("generatorWasteLocationId")) {
			return ATTRIBUTE_GENERATOR_WASTE_LOCATION_ID;
		}
		else if(attributeName.equals("tsdfFacilityIdForGenerator")) {
			return ATTRIBUTE_TSDF_FACILITY_ID_FOR_GENERATOR;
		}
		else if(attributeName.equals("generatorActive")) {
			return ATTRIBUTE_GENERATOR_ACTIVE;
		}
		else if(attributeName.equals("generatorLocationDesc")) {
			return ATTRIBUTE_GENERATOR_LOCATION_DESC;
		}
		else if(attributeName.equals("generatorLocationStatus")) {
			return ATTRIBUTE_GENERATOR_LOCATION_STATUS;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("userGroupId")) {
			return ATTRIBUTE_USER_GROUP_ID;
		}
		else if(attributeName.equals("admin")) {
			return ATTRIBUTE_ADMIN;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, WasteTsdfSourceViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(WasteTsdfSourceViewBean wasteTsdfSourceViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("tsdfCompanyId", "SearchCriterion.EQUALS",
			"" + wasteTsdfSourceViewBean.getTsdfCompanyId());

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


	public int delete(WasteTsdfSourceViewBean wasteTsdfSourceViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("tsdfCompanyId", "SearchCriterion.EQUALS",
			"" + wasteTsdfSourceViewBean.getTsdfCompanyId());

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
	public int insert(WasteTsdfSourceViewBean wasteTsdfSourceViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(wasteTsdfSourceViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(WasteTsdfSourceViewBean wasteTsdfSourceViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_TSDF_COMPANY_ID + "," +
			ATTRIBUTE_TSDF_EPA_ID + "," +
			ATTRIBUTE_TSDF_NAME + "," +
			ATTRIBUTE_TSDF_FACILITY_ID + "," +
			ATTRIBUTE_TSDF_LOCATION_DESC + "," +
			ATTRIBUTE_TSDF_LOCATION_STATUS + "," +
			ATTRIBUTE_GENERATOR_COMPANY_ID + "," +
			ATTRIBUTE_GENERATOR_FACILITY_ID + "," +
			ATTRIBUTE_GENERATOR_WASTE_LOCATION_ID + "," +
			ATTRIBUTE_TSDF_FACILITY_ID_FOR_GENERATOR + "," +
			ATTRIBUTE_GENERATOR_ACTIVE + "," +
			ATTRIBUTE_GENERATOR_LOCATION_DESC + "," +
			ATTRIBUTE_GENERATOR_LOCATION_STATUS + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_USER_GROUP_ID + "," +
			ATTRIBUTE_ADMIN + ")" +
			" values (" +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfCompanyId()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfEpaId()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfName()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfFacilityId()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfLocationDesc()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfLocationStatus()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorCompanyId()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorFacilityId()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorWasteLocationId()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfFacilityIdForGenerator()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorActive()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorLocationDesc()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorLocationStatus()) + "," +
			wasteTsdfSourceViewBean.getPersonnelId() + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getUserGroupId()) + "," +
			SqlHandler.delimitString(wasteTsdfSourceViewBean.getAdmin()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(WasteTsdfSourceViewBean wasteTsdfSourceViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(wasteTsdfSourceViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(WasteTsdfSourceViewBean wasteTsdfSourceViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TSDF_COMPANY_ID + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfCompanyId()) + "," +
			ATTRIBUTE_TSDF_EPA_ID + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfEpaId()) + "," +
			ATTRIBUTE_TSDF_NAME + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfName()) + "," +
			ATTRIBUTE_TSDF_FACILITY_ID + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfFacilityId()) + "," +
			ATTRIBUTE_TSDF_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfLocationDesc()) + "," +
			ATTRIBUTE_TSDF_LOCATION_STATUS + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfLocationStatus()) + "," +
			ATTRIBUTE_GENERATOR_COMPANY_ID + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorCompanyId()) + "," +
			ATTRIBUTE_GENERATOR_FACILITY_ID + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorFacilityId()) + "," +
			ATTRIBUTE_GENERATOR_WASTE_LOCATION_ID + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorWasteLocationId()) + "," +
			ATTRIBUTE_TSDF_FACILITY_ID_FOR_GENERATOR + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getTsdfFacilityIdForGenerator()) + "," +
			ATTRIBUTE_GENERATOR_ACTIVE + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorActive()) + "," +
			ATTRIBUTE_GENERATOR_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorLocationDesc()) + "," +
			ATTRIBUTE_GENERATOR_LOCATION_STATUS + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getGeneratorLocationStatus()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" + 
				StringHandler.nullIfZero(wasteTsdfSourceViewBean.getPersonnelId()) + "," +
			ATTRIBUTE_USER_GROUP_ID + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getUserGroupId()) + "," +
			ATTRIBUTE_ADMIN + "=" + 
				SqlHandler.delimitString(wasteTsdfSourceViewBean.getAdmin()) + " " +
			"where " + ATTRIBUTE_TSDF_COMPANY_ID + "=" +
				wasteTsdfSourceViewBean.getTsdfCompanyId();

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

		Collection wasteTsdfSourceViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WasteTsdfSourceViewBean wasteTsdfSourceViewBean = new WasteTsdfSourceViewBean();
			load(dataSetRow, wasteTsdfSourceViewBean);
			wasteTsdfSourceViewBeanColl.add(wasteTsdfSourceViewBean);
		}

		return wasteTsdfSourceViewBeanColl;
	}
}