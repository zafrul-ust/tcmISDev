package com.tcmis.internal.supply.factory;


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
import com.tcmis.internal.supply.beans.PoImageViewBean;


/******************************************************************************
 * CLASSNAME: PoImageViewBeanFactory <br>
 * @version: 1.0, Sep 14, 2006 <br>
 *****************************************************************************/


public class PoImageViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_IMAGE_ID = "IMAGE_ID";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_DATE_PRINTED = "DATE_PRINTED";
	public String ATTRIBUTE_FILE_URL = "FILE_URL";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_PRINTED_BY = "PRINTED_BY";
	public String ATTRIBUTE_PRINTED_BY_NAME = "PRINTED_BY_NAME";
	public String ATTRIBUTE_PRINT_TYPE = "PRINT_TYPE";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";

	//table name
	public String TABLE = "PO_IMAGE_VIEW";


	//constructor
	public PoImageViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("imageId")) {
			return ATTRIBUTE_IMAGE_ID;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("datePrinted")) {
			return ATTRIBUTE_DATE_PRINTED;
		}
		else if(attributeName.equals("fileUrl")) {
			return ATTRIBUTE_FILE_URL;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("printedBy")) {
			return ATTRIBUTE_PRINTED_BY;
		}
		else if(attributeName.equals("printedByName")) {
			return ATTRIBUTE_PRINTED_BY_NAME;
		}
		else if(attributeName.equals("printType")) {
			return ATTRIBUTE_PRINT_TYPE;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoImageViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoImageViewBean poImageViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("imageId", "SearchCriterion.EQUALS",
			"" + poImageViewBean.getImageId());

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


	public int delete(PoImageViewBean poImageViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("imageId", "SearchCriterion.EQUALS",
			"" + poImageViewBean.getImageId());

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
	public int insert(PoImageViewBean poImageViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poImageViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoImageViewBean poImageViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_IMAGE_ID + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_DATE_PRINTED + "," +
			ATTRIBUTE_FILE_URL + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_PRINTED_BY + "," +
			ATTRIBUTE_PRINTED_BY_NAME + "," +
			ATTRIBUTE_PRINT_TYPE + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_DATE_PRINTED_ORDER + ")" +
			" values (" +
			poImageViewBean.getImageId() + "," +
			poImageViewBean.getRadianPo() + "," +
			SqlHandler.delimitString(poImageViewBean.getDatePrinted()) + "," +
			SqlHandler.delimitString(poImageViewBean.getFileUrl()) + "," +
			SqlHandler.delimitString(poImageViewBean.getSupplier()) + "," +
			poImageViewBean.getPrintedBy() + "," +
			SqlHandler.delimitString(poImageViewBean.getPrintedByName()) + "," +
			SqlHandler.delimitString(poImageViewBean.getPrintType()) + "," +
			SqlHandler.delimitString(poImageViewBean.getSupplierName()) + "," +
			DateHandler.getOracleToDateFunction(poImageViewBean.getDatePrintedOrder()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoImageViewBean poImageViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poImageViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoImageViewBean poImageViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_IMAGE_ID + "=" +
				StringHandler.nullIfZero(poImageViewBean.getImageId()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" +
				StringHandler.nullIfZero(poImageViewBean.getRadianPo()) + "," +
			ATTRIBUTE_DATE_PRINTED + "=" +
				SqlHandler.delimitString(poImageViewBean.getDatePrinted()) + "," +
			ATTRIBUTE_FILE_URL + "=" +
				SqlHandler.delimitString(poImageViewBean.getFileUrl()) + "," +
			ATTRIBUTE_SUPPLIER + "=" +
				SqlHandler.delimitString(poImageViewBean.getSupplier()) + "," +
			ATTRIBUTE_PRINTED_BY + "=" +
				StringHandler.nullIfZero(poImageViewBean.getPrintedBy()) + "," +
			ATTRIBUTE_PRINTED_BY_NAME + "=" +
				SqlHandler.delimitString(poImageViewBean.getPrintedByName()) + "," +
			ATTRIBUTE_PRINT_TYPE + "=" +
				SqlHandler.delimitString(poImageViewBean.getPrintType()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" +
				SqlHandler.delimitString(poImageViewBean.getSupplierName()) + "," +
			ATTRIBUTE_DATE_PRINTED_ORDER + "=" +
				DateHandler.getOracleToDateFunction(poImageViewBean.getDatePrintedOrder()) + " " +
			"where " + ATTRIBUTE_IMAGE_ID + "=" +
				poImageViewBean.getImageId();

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
			c = select(criteria,null, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

        public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

          Connection connection = null;
          Collection c = null;
          try {
            connection = this.getDbManager().getConnection();
            c = select(criteria, sortCriteria, connection);
          } finally {
            this.getDbManager().returnConnection(connection);
          }
          return c;
        }

        public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

          Collection poImageViewBeanColl = new Vector();

          String query = "select * from " + TABLE + " " + getWhereClause(criteria);
          if (sortCriteria != null) {
            query += getOrderByClause(sortCriteria);
          }

          DataSet dataSet = new SqlManager().select(conn, query);

          Iterator dataIter = dataSet.iterator();

          while (dataIter.hasNext()) {
            DataSetRow dataSetRow = (DataSetRow) dataIter.next();
            PoImageViewBean poImageViewBean = new PoImageViewBean();
            load(dataSetRow, poImageViewBean);
            poImageViewBeanColl.add(poImageViewBean);
          }

          return poImageViewBeanColl;
        }
} //end of class