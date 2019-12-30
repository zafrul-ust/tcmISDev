package com.tcmis.client.catalog.factory;


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
import com.tcmis.client.catalog.beans.CatalogPartUnitOfSaleBean;


/******************************************************************************
 * CLASSNAME: CatalogPartUnitOfSaleBeanFactory <br>
 * @version: 1.0, Apr 25, 2007 <br>
 *****************************************************************************/


public class CatalogPartUnitOfSaleBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH = "UNIT_OF_SALE_QUANTITY_PER_EACH";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";

	//table name
	public String TABLE = "CATALOG_PART_UNIT_OF_SALE";


	//constructor
	public CatalogPartUnitOfSaleBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("unitOfSaleQuantityPerEach")) {
			return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CatalogPartUnitOfSaleBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CatalogPartUnitOfSaleBean catalogPartUnitOfSaleBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + catalogPartUnitOfSaleBean.getCompanyId());

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


	public int delete(CatalogPartUnitOfSaleBean catalogPartUnitOfSaleBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + catalogPartUnitOfSaleBean.getCompanyId());

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
	public int insert(CatalogPartUnitOfSaleBean catalogPartUnitOfSaleBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(catalogPartUnitOfSaleBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CatalogPartUnitOfSaleBean catalogPartUnitOfSaleBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_UNIT_OF_SALE + "," +
			ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "," +
			ATTRIBUTE_COMMENTS + ")" +
 values (
			SqlHandler.delimitString(catalogPartUnitOfSaleBean.getCompanyId()) + "," +
			SqlHandler.delimitString(catalogPartUnitOfSaleBean.getCatalogId()) + "," +
			SqlHandler.delimitString(catalogPartUnitOfSaleBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(catalogPartUnitOfSaleBean.getUnitOfSale()) + "," +
			StringHandler.nullIfZero(catalogPartUnitOfSaleBean.getUnitOfSaleQuantityPerEach()) + "," +
			SqlHandler.delimitString(catalogPartUnitOfSaleBean.getComments()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CatalogPartUnitOfSaleBean catalogPartUnitOfSaleBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(catalogPartUnitOfSaleBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CatalogPartUnitOfSaleBean catalogPartUnitOfSaleBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(catalogPartUnitOfSaleBean.getCompanyId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(catalogPartUnitOfSaleBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(catalogPartUnitOfSaleBean.getCatPartNo()) + "," +
			ATTRIBUTE_UNIT_OF_SALE + "=" + 
				SqlHandler.delimitString(catalogPartUnitOfSaleBean.getUnitOfSale()) + "," +
			ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "=" + 
				StringHandler.nullIfZero(catalogPartUnitOfSaleBean.getUnitOfSaleQuantityPerEach()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(catalogPartUnitOfSaleBean.getComments()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				StringHandler.nullIfZero(catalogPartUnitOfSaleBean.getCompanyId());

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

		Collection catalogPartUnitOfSaleBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CatalogPartUnitOfSaleBean catalogPartUnitOfSaleBean = new CatalogPartUnitOfSaleBean();
			load(dataSetRow, catalogPartUnitOfSaleBean);
			catalogPartUnitOfSaleBeanColl.add(catalogPartUnitOfSaleBean);
		}

		return catalogPartUnitOfSaleBeanColl;
	}

public Collection selectDistinctUOS(SearchCriteria criteria) throws
	BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = selectDistinctUOS(criteria, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection selectDistinctUOS(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection cpuosBeanColl = new Vector();

	String query = "select distinct unit_of_sale from " + TABLE + " " +
	 getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 CatalogPartUnitOfSaleBean cpuosBean = new CatalogPartUnitOfSaleBean();
	 load(dataSetRow, cpuosBean, true);
	 cpuosBeanColl.add(cpuosBean);
	}

	return cpuosBeanColl;
 }
        
}