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
import com.tcmis.internal.supply.beans.BpoImageViewBean;


/******************************************************************************
 * CLASSNAME: BpoImageViewBeanFactory <br>
 * @version: 1.0, Sep 14, 2006 <br>
 *****************************************************************************/


public class BpoImageViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_IMAGE_ID = "IMAGE_ID";
	public String ATTRIBUTE_BPO = "BPO";
        public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_DATE_PRINTED = "DATE_PRINTED";
	public String ATTRIBUTE_FILE_URL = "FILE_URL";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_PRINTED_BY = "PRINTED_BY";
	public String ATTRIBUTE_PRINTED_BY_NAME = "PRINTED_BY_NAME";
	public String ATTRIBUTE_PRINT_TYPE = "PRINT_TYPE";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";

	//table name
	public String TABLE = "BPO_IMAGE_VIEW";


	//constructor
	public BpoImageViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("imageId")) {
			return ATTRIBUTE_IMAGE_ID;
		}
		else if(attributeName.equals("bpo")) {
			return ATTRIBUTE_BPO;
                }else if(attributeName.equals("radianPo")) {
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
		return super.getType(attributeName, BpoImageViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(BpoImageViewBean bpoImageViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("imageId", "SearchCriterion.EQUALS",
			"" + bpoImageViewBean.getImageId());

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


	public int delete(BpoImageViewBean bpoImageViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("imageId", "SearchCriterion.EQUALS",
			"" + bpoImageViewBean.getImageId());

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
	public int insert(BpoImageViewBean bpoImageViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(bpoImageViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(BpoImageViewBean bpoImageViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_IMAGE_ID + "," +
			ATTRIBUTE_BPO + "," +
			ATTRIBUTE_DATE_PRINTED + "," +
			ATTRIBUTE_FILE_URL + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_PRINTED_BY + "," +
			ATTRIBUTE_PRINTED_BY_NAME + "," +
			ATTRIBUTE_PRINT_TYPE + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_DATE_PRINTED_ORDER + ")" +
			" values (" +
			bpoImageViewBean.getImageId() + "," +
			bpoImageViewBean.getBpo() + "," +
			SqlHandler.delimitString(bpoImageViewBean.getDatePrinted()) + "," +
			SqlHandler.delimitString(bpoImageViewBean.getFileUrl()) + "," +
			SqlHandler.delimitString(bpoImageViewBean.getSupplier()) + "," +
			bpoImageViewBean.getPrintedBy() + "," +
			SqlHandler.delimitString(bpoImageViewBean.getPrintedByName()) + "," +
			SqlHandler.delimitString(bpoImageViewBean.getPrintType()) + "," +
			SqlHandler.delimitString(bpoImageViewBean.getSupplierName()) + "," +
			DateHandler.getOracleToDateFunction(bpoImageViewBean.getDatePrintedOrder()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(BpoImageViewBean bpoImageViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(bpoImageViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(BpoImageViewBean bpoImageViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_IMAGE_ID + "=" +
				StringHandler.nullIfZero(bpoImageViewBean.getImageId()) + "," +
			ATTRIBUTE_BPO + "=" +
				StringHandler.nullIfZero(bpoImageViewBean.getBpo()) + "," +
			ATTRIBUTE_DATE_PRINTED + "=" +
				SqlHandler.delimitString(bpoImageViewBean.getDatePrinted()) + "," +
			ATTRIBUTE_FILE_URL + "=" +
				SqlHandler.delimitString(bpoImageViewBean.getFileUrl()) + "," +
			ATTRIBUTE_SUPPLIER + "=" +
				SqlHandler.delimitString(bpoImageViewBean.getSupplier()) + "," +
			ATTRIBUTE_PRINTED_BY + "=" +
				StringHandler.nullIfZero(bpoImageViewBean.getPrintedBy()) + "," +
			ATTRIBUTE_PRINTED_BY_NAME + "=" +
				SqlHandler.delimitString(bpoImageViewBean.getPrintedByName()) + "," +
			ATTRIBUTE_PRINT_TYPE + "=" +
				SqlHandler.delimitString(bpoImageViewBean.getPrintType()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" +
				SqlHandler.delimitString(bpoImageViewBean.getSupplierName()) + "," +
			ATTRIBUTE_DATE_PRINTED_ORDER + "=" +
				DateHandler.getOracleToDateFunction(bpoImageViewBean.getDatePrintedOrder()) + " " +
			"where " + ATTRIBUTE_IMAGE_ID + "=" +
				bpoImageViewBean.getImageId();

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

          Collection bpoImageViewBeanColl = new Vector();

          String query = "select * from " + TABLE + " " + getWhereClause(criteria);
          if (sortCriteria != null) {
            query += getOrderByClause(sortCriteria);
          }

          DataSet dataSet = new SqlManager().select(conn, query);

          Iterator dataIter = dataSet.iterator();

          while (dataIter.hasNext()) {
            DataSetRow dataSetRow = (DataSetRow) dataIter.next();
            BpoImageViewBean bpoImageViewBean = new BpoImageViewBean();
            load(dataSetRow, bpoImageViewBean);
            bpoImageViewBeanColl.add(bpoImageViewBean);
          }

          return bpoImageViewBeanColl;
        }
} //end of class