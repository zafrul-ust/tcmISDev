package com.tcmis.client.raytheon.factory;


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
import com.tcmis.client.raytheon.beans.FtpMaterialRequisitionBean;


/******************************************************************************
 * CLASSNAME: FtpMaterialRequisitionBeanFactory <br>
 * @version: 1.0, Sep 19, 2006 <br>
 *****************************************************************************/


public class FtpMaterialRequisitionBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FTP_FILENAME = "FTP_FILENAME";
	public String ATTRIBUTE_FILE_LINE = "FILE_LINE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CHARGE_NO = "CHARGE_NO";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_TCM_LOAD_DATE = "TCM_LOAD_DATE";
	public String ATTRIBUTE_PROCESSED_DATE = "PROCESSED_DATE";
	public String ATTRIBUTE_PROCESSED_STATUS = "PROCESSED_STATUS";

	//table name
	public String TABLE = "FTP_MATERIAL_REQUISITION";


	//constructor
	public FtpMaterialRequisitionBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("ftpFilename")) {
			return ATTRIBUTE_FTP_FILENAME;
		}
		else if(attributeName.equals("fileLine")) {
			return ATTRIBUTE_FILE_LINE;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("chargeNo")) {
			return ATTRIBUTE_CHARGE_NO;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if(attributeName.equals("tcmLoadDate")) {
			return ATTRIBUTE_TCM_LOAD_DATE;
		}
		else if(attributeName.equals("processedDate")) {
			return ATTRIBUTE_PROCESSED_DATE;
		}
		else if(attributeName.equals("processedStatus")) {
			return ATTRIBUTE_PROCESSED_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FtpMaterialRequisitionBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(FtpMaterialRequisitionBean ftpMaterialRequisitionBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("ftpFilename", "SearchCriterion.EQUALS",
			"" + ftpMaterialRequisitionBean.getFtpFilename());

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


	public int delete(FtpMaterialRequisitionBean ftpMaterialRequisitionBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("ftpFilename", "SearchCriterion.EQUALS",
			"" + ftpMaterialRequisitionBean.getFtpFilename());

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


	//insert
	public int insert(FtpMaterialRequisitionBean ftpMaterialRequisitionBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(ftpMaterialRequisitionBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(FtpMaterialRequisitionBean ftpMaterialRequisitionBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_FTP_FILENAME + "," +
			ATTRIBUTE_FILE_LINE + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_DELIVERY_POINT + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_CHARGE_NO + "," +
			ATTRIBUTE_ORDER_QUANTITY + "," +
			ATTRIBUTE_TCM_LOAD_DATE + "," +
			ATTRIBUTE_PROCESSED_DATE + "," +
			ATTRIBUTE_PROCESSED_STATUS + ")" +
			" values (" +
			SqlHandler.delimitString(ftpMaterialRequisitionBean.getFtpFilename()) + "," +
			ftpMaterialRequisitionBean.getFileLine() + "," +
			SqlHandler.delimitString(ftpMaterialRequisitionBean.getFacilityId()) + "," +
			SqlHandler.delimitString(ftpMaterialRequisitionBean.getApplication()) + "," +
			SqlHandler.delimitString(ftpMaterialRequisitionBean.getDeliveryPoint()) + "," +
			SqlHandler.delimitString(ftpMaterialRequisitionBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(ftpMaterialRequisitionBean.getChargeNo()) + "," +
			ftpMaterialRequisitionBean.getOrderQuantity() + "," +
			DateHandler.getOracleToDateFunction(ftpMaterialRequisitionBean.getTcmLoadDate()) + "," +
			DateHandler.getOracleToDateFunction(ftpMaterialRequisitionBean.getProcessedDate()) + "," +
			SqlHandler.delimitString(ftpMaterialRequisitionBean.getProcessedStatus()) + ")";

		return sqlManager.update(conn, query);
	}

      //you need to verify the primary key(s) before uncommenting this
/*
	//update
	public int update(FtpMaterialRequisitionBean ftpMaterialRequisitionBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(ftpMaterialRequisitionBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(FtpMaterialRequisitionBean ftpMaterialRequisitionBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_FTP_FILENAME + "=" +
				SqlHandler.delimitString(ftpMaterialRequisitionBean.getFtpFilename()) + "," +
			ATTRIBUTE_FILE_LINE + "=" +
				StringHandler.nullIfZero(ftpMaterialRequisitionBean.getFileLine()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(ftpMaterialRequisitionBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(ftpMaterialRequisitionBean.getApplication()) + "," +
			ATTRIBUTE_DELIVERY_POINT + "=" +
				SqlHandler.delimitString(ftpMaterialRequisitionBean.getDeliveryPoint()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(ftpMaterialRequisitionBean.getCatPartNo()) + "," +
			ATTRIBUTE_CHARGE_NO + "=" +
				SqlHandler.delimitString(ftpMaterialRequisitionBean.getChargeNo()) + "," +
			ATTRIBUTE_ORDER_QUANTITY + "=" +
				StringHandler.nullIfZero(ftpMaterialRequisitionBean.getOrderQuantity()) + "," +
			ATTRIBUTE_TCM_LOAD_DATE + "=" +
				DateHandler.getOracleToDateFunction(ftpMaterialRequisitionBean.getTcmLoadDate()) + "," +
			ATTRIBUTE_PROCESSED_DATE + "=" +
				DateHandler.getOracleToDateFunction(ftpMaterialRequisitionBean.getProcessedDate()) + "," +
			ATTRIBUTE_PROCESSED_STATUS + "=" +
				SqlHandler.delimitString(ftpMaterialRequisitionBean.getProcessedStatus()) + " " +
			"where " + ATTRIBUTE_FTP_FILENAME + "=" +
				ftpMaterialRequisitionBean.getFtpFilename();

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

		Collection ftpMaterialRequisitionBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FtpMaterialRequisitionBean ftpMaterialRequisitionBean = new FtpMaterialRequisitionBean();
			load(dataSetRow, ftpMaterialRequisitionBean);
			ftpMaterialRequisitionBeanColl.add(ftpMaterialRequisitionBean);
		}

		return ftpMaterialRequisitionBeanColl;
	}

        /*****************************************************************************
     * Calls named procedure. Note that date
     * arguments should be passed as <code>java.sql.Timestamp</code>
     *
     * @param procName name of the procedure to call
     *        inParameters arguments to call the procedure with
     * @throws BaseException If there are problems calling the procedure
         ****************************************************************************/
    public void doProcedure(String procedure, Collection inParameters) throws
        BaseException {
      Connection connection = null;
      try {
        connection = getDbManager().getConnection();
        this.doProcedure(connection, procedure, inParameters);
      }
      finally {
        try {
          getDbManager().returnConnection(connection);
        }
        catch (Exception e) {
          //ignore
        }
      }
    }

        /*****************************************************************************
     * Calls named procedure. Note that date
     * arguments should be passed as <code>java.sql.Timestamp</code>
     *
     * @param connection database connection to use
     *        procName name of the procedure to call
     *        inParameters arguments to call the procedure with
     * @throws BaseException If there are problems calling the procedure
         ****************************************************************************/
    public void doProcedure(Connection connection,
                            String procedure,
                            Collection inParameters) throws BaseException {
      new SqlManager().doProcedure(connection, procedure, inParameters);
    }

}