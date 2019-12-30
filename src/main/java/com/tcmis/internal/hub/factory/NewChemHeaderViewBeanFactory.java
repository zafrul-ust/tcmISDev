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
import com.tcmis.internal.hub.beans.NewChemHeaderViewBean;


/******************************************************************************
 * CLASSNAME: NewChemHeaderViewBeanFactory <br>
 * @version: 1.0, Jan 14, 2008 <br>
 *****************************************************************************/


public class NewChemHeaderViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_LIST_ITEM_ID = "LIST_ITEM_ID";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_CATALOG_DESC = "CATALOG_DESC";
	public String ATTRIBUTE_COMP_FAC = "COMP_FAC";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_NEW_CHEM_PACKAGING_CHANGE = "NEW_CHEM_PACKAGING_CHANGE";

	//table name
	public String TABLE = "NEW_CHEM_HEADER_VIEW";


	//constructor
	public NewChemHeaderViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("listItemId")) {
			return ATTRIBUTE_LIST_ITEM_ID;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("catalogDesc")) {
			return ATTRIBUTE_CATALOG_DESC;
		}
		else if(attributeName.equals("compFac")) {
			return ATTRIBUTE_COMP_FAC;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("newChemPackagingChange")) {
			return ATTRIBUTE_NEW_CHEM_PACKAGING_CHANGE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, NewChemHeaderViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(NewChemHeaderViewBean newChemHeaderViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
			"" + newChemHeaderViewBean.getCatalogId());

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


	public int delete(NewChemHeaderViewBean newChemHeaderViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
			"" + newChemHeaderViewBean.getCatalogId());

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
	public int insert(NewChemHeaderViewBean newChemHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(newChemHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(NewChemHeaderViewBean newChemHeaderViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_LIST_ITEM_ID + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_CATALOG_DESC + "," +
			ATTRIBUTE_COMP_FAC + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_NEW_CHEM_PACKAGING_CHANGE + ")" +
			" values (" +
			SqlHandler.delimitString(newChemHeaderViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getCatalogCompanyId()) + "," +
			newChemHeaderViewBean.getListItemId() + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getPartShortName()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getCatalogDesc()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getCompFac()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getPartDescription()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(newChemHeaderViewBean.getNewChemPackagingChange()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(NewChemHeaderViewBean newChemHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(newChemHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(NewChemHeaderViewBean newChemHeaderViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getCatalogCompanyId()) + "," +
			ATTRIBUTE_LIST_ITEM_ID + "=" + 
				StringHandler.nullIfZero(newChemHeaderViewBean.getListItemId()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getPartShortName()) + "," +
			ATTRIBUTE_CATALOG_DESC + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getCatalogDesc()) + "," +
			ATTRIBUTE_COMP_FAC + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getCompFac()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getPartDescription()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getFacilityId()) + "," +
			ATTRIBUTE_NEW_CHEM_PACKAGING_CHANGE + "=" + 
				SqlHandler.delimitString(newChemHeaderViewBean.getNewChemPackagingChange()) + " " +
			"where " + ATTRIBUTE_CATALOG_ID + "=" +
				newChemHeaderViewBean.getCatalogId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection newChemHeaderViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			NewChemHeaderViewBean newChemHeaderViewBean = new NewChemHeaderViewBean();
			load(dataSetRow, newChemHeaderViewBean);
			newChemHeaderViewBeanColl.add(newChemHeaderViewBean);
		}

		return newChemHeaderViewBeanColl;
	}
}