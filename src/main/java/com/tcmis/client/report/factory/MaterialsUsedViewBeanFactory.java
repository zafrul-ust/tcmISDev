package com.tcmis.client.report.factory;


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
import com.tcmis.client.report.beans.MaterialsUsedViewBean;
import com.tcmis.client.report.beans.MaterialsUsedInputBean;

/******************************************************************************
 * CLASSNAME: MaterialsUsedViewBeanFactory <br>
 * @version: 1.0, Feb 21, 2006 <br>
 *****************************************************************************/


public class MaterialsUsedViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_ITEM_QUANTITY_DELIVERED = "ITEM_QUANTITY_DELIVERED";

	//table name
	public String TABLE = "MATERIALS_USED_VIEW";


	//constructor
	public MaterialsUsedViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
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
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("mfgDesc")) {
			return ATTRIBUTE_MFG_DESC;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("itemQuantityDelivered")) {
			return ATTRIBUTE_ITEM_QUANTITY_DELIVERED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MaterialsUsedViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(MaterialsUsedViewBean materialsUsedViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
			"" + materialsUsedViewBean.getFacilityId());

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


	public int delete(MaterialsUsedViewBean materialsUsedViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
			"" + materialsUsedViewBean.getFacilityId());

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
	public int insert(MaterialsUsedViewBean materialsUsedViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(materialsUsedViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(MaterialsUsedViewBean materialsUsedViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_MATERIAL_DESC + "," +
			ATTRIBUTE_MFG_DESC + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_ITEM_QUANTITY_DELIVERED + ")" +
			" values (" +
			SqlHandler.delimitString(materialsUsedViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(materialsUsedViewBean.getApplication()) + "," +
			SqlHandler.delimitString(materialsUsedViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(materialsUsedViewBean.getCatPartNo()) + "," +
			materialsUsedViewBean.getPartGroupNo() + "," +
			materialsUsedViewBean.getItemId() + "," +
			materialsUsedViewBean.getMaterialId() + "," +
			SqlHandler.delimitString(materialsUsedViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(materialsUsedViewBean.getMaterialDesc()) + "," +
			SqlHandler.delimitString(materialsUsedViewBean.getMfgDesc()) + "," +
			DateHandler.getOracleToDateFunction(materialsUsedViewBean.getDateDelivered()) + "," +
			materialsUsedViewBean.getItemQuantityDelivered() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(MaterialsUsedViewBean materialsUsedViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(materialsUsedViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(MaterialsUsedViewBean materialsUsedViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(materialsUsedViewBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(materialsUsedViewBean.getApplication()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(materialsUsedViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(materialsUsedViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" +
				StringHandler.nullIfZero(materialsUsedViewBean.getPartGroupNo()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(materialsUsedViewBean.getItemId()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" +
				StringHandler.nullIfZero(materialsUsedViewBean.getMaterialId()) + "," +
			ATTRIBUTE_PACKAGING + "=" +
				SqlHandler.delimitString(materialsUsedViewBean.getPackaging()) + "," +
			ATTRIBUTE_MATERIAL_DESC + "=" +
				SqlHandler.delimitString(materialsUsedViewBean.getMaterialDesc()) + "," +
			ATTRIBUTE_MFG_DESC + "=" +
				SqlHandler.delimitString(materialsUsedViewBean.getMfgDesc()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" +
				DateHandler.getOracleToDateFunction(materialsUsedViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_ITEM_QUANTITY_DELIVERED + "=" +
				StringHandler.nullIfZero(materialsUsedViewBean.getItemQuantityDelivered()) + " " +
			"where " + ATTRIBUTE_FACILITY_ID + "=" +
				materialsUsedViewBean.getFacilityId();

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

		Collection materialsUsedViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MaterialsUsedViewBean materialsUsedViewBean = new MaterialsUsedViewBean();
			load(dataSetRow, materialsUsedViewBean);
			materialsUsedViewBeanColl.add(materialsUsedViewBean);
		}

		return materialsUsedViewBeanColl;
	}

 //select
	public Collection select(MaterialsUsedInputBean materialsUsedInputBean)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(materialsUsedInputBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(MaterialsUsedInputBean materialsUsedInputBean, Connection conn)
		throws BaseException {

		Collection materialsUsedViewBeanColl = new Vector();

		String query = "select " + ATTRIBUTE_FACILITY_ID + ", " +
		 ATTRIBUTE_CATALOG_ID + "," + ATTRIBUTE_CAT_PART_NO + "," +
		 ATTRIBUTE_PART_GROUP_NO + "," + ATTRIBUTE_ITEM_ID + ","
		 + ATTRIBUTE_MATERIAL_ID + "," + ATTRIBUTE_PACKAGING + "," +
		 ATTRIBUTE_MATERIAL_DESC + "," + ATTRIBUTE_MFG_DESC + ",sum(" +
		 ATTRIBUTE_ITEM_QUANTITY_DELIVERED + ") from " + TABLE
		 + " WHERE " + ATTRIBUTE_DATE_DELIVERED + " BETWEEN to_date('" +
		 com.tcmis.common.util.DateHandler.formatDate(materialsUsedInputBean.
		 getDateDeliveredStart(), "MM/dd/yyyy") +
		 "','MM/DD/YYYY') AND to_date('" +
		 com.tcmis.common.util.DateHandler.formatDate(materialsUsedInputBean.
		 getDateDeliveredEnd(), "MM/dd/yyyy") +
		 "','MM/DD/YYYY') AND facility_id = '" +
		 materialsUsedInputBean.getFacilityId() + "' AND application = '" +
		 materialsUsedInputBean.getApplication() + "'	GROUP BY " +
		 ATTRIBUTE_FACILITY_ID + ", " + ATTRIBUTE_CATALOG_ID + "," +
		 ATTRIBUTE_CAT_PART_NO + "," + ATTRIBUTE_PART_GROUP_NO + "," +
		 ATTRIBUTE_ITEM_ID + ","
		 + ATTRIBUTE_MATERIAL_ID + "," + ATTRIBUTE_PACKAGING + "," +
		 ATTRIBUTE_MATERIAL_DESC + "," + ATTRIBUTE_MFG_DESC + "";

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MaterialsUsedViewBean materialsUsedViewBean = new MaterialsUsedViewBean();
			load(dataSetRow, materialsUsedViewBean);
			materialsUsedViewBeanColl.add(materialsUsedViewBean);
		}

		return materialsUsedViewBeanColl;
	}


}