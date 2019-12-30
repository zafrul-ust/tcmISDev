package com.tcmis.client.swa.factory;


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
import com.tcmis.client.swa.beans.MatlChmStageBean;


/******************************************************************************
 * CLASSNAME: MatlChmBeanFactory <br>
 * @version: 1.0, Apr 19, 2007 <br>
 *****************************************************************************/


public class MatlChmStageBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_MANF_PART_NUMBER = "MANF_PART_NUMBER";
	public String ATTRIBUTE_PART_NAME = "PART_NAME";
	public String ATTRIBUTE_PART_DESC = "PART_DESC";
	public String ATTRIBUTE_UNIT_OF_ISSUE = "UNIT_OF_ISSUE";
	public String ATTRIBUTE_INVENTORY = "INVENTORY";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_STOP_DATE = "STOP_DATE";
	public String ATTRIBUTE_CHEMICAL_FLAG = "CHEMICAL_FLAG";
	public String ATTRIBUTE_CONVERSION_FACTOR = "CONVERSION_FACTOR";
	public String ATTRIBUTE_TCM_LOAD_DATE = "TCM_LOAD_DATE";

	//table name
	public String TABLE = "MATL_CHM_STAGE";


	//constructor
	public MatlChmStageBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("manfPartNumber")) {
			return ATTRIBUTE_MANF_PART_NUMBER;
		}
		else if(attributeName.equals("partName")) {
			return ATTRIBUTE_PART_NAME;
		}
		else if(attributeName.equals("partDesc")) {
			return ATTRIBUTE_PART_DESC;
		}
		else if(attributeName.equals("unitOfIssue")) {
			return ATTRIBUTE_UNIT_OF_ISSUE;
		}
		else if(attributeName.equals("inventory")) {
			return ATTRIBUTE_INVENTORY;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("stopDate")) {
			return ATTRIBUTE_STOP_DATE;
		}
		else if(attributeName.equals("chemicalFlag")) {
			return ATTRIBUTE_CHEMICAL_FLAG;
		}
		else if(attributeName.equals("conversionFactor")) {
			return ATTRIBUTE_CONVERSION_FACTOR;
		}
		else if(attributeName.equals("tcmLoadDate")) {
			return ATTRIBUTE_TCM_LOAD_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MatlChmStageBean.class);
	}

	
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
	public int insert(MatlChmStageBean matlChmStageBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(matlChmStageBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	
	public int insert(MatlChmStageBean matlChmStageBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PART_NUMBER + "," +
			ATTRIBUTE_MANF_PART_NUMBER + "," +
			ATTRIBUTE_PART_NAME + "," +
			ATTRIBUTE_PART_DESC + "," +
			ATTRIBUTE_UNIT_OF_ISSUE + "," +
			ATTRIBUTE_INVENTORY + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_STOP_DATE + "," +
			ATTRIBUTE_CHEMICAL_FLAG + "," +
			ATTRIBUTE_CONVERSION_FACTOR + "," +
			ATTRIBUTE_TCM_LOAD_DATE + ")" +
			" values (" +
			SqlHandler.delimitString(matlChmStageBean.getPartNumber()) + "," +
			SqlHandler.delimitString(matlChmStageBean.getManfPartNumber()) + "," +
			SqlHandler.delimitString(matlChmStageBean.getPartName()) + "," +
			SqlHandler.delimitString(matlChmStageBean.getPartDesc()) + "," +
			SqlHandler.delimitString(matlChmStageBean.getUnitOfIssue()) + "," +
			matlChmStageBean.getInventory() + "," +
			DateHandler.getOracleToDateFunction(matlChmStageBean.getStartDate()) + "," +
			DateHandler.getOracleToDateFunction(matlChmStageBean.getStopDate()) + "," +
			SqlHandler.delimitString(matlChmStageBean.getChemicalFlag()) + "," +
			matlChmStageBean.getConversionFactor() + "," +
			DateHandler.getOracleToDateFunction(matlChmStageBean.getTcmLoadDate()) + ")";
		return sqlManager.update(conn, query);
	}

	
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

		Collection matlChmBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MatlChmStageBean matlChmStageBean = new MatlChmStageBean();
			load(dataSetRow, matlChmStageBean);
			matlChmBeanColl.add(matlChmStageBean);
		}

		return matlChmBeanColl;
	}
	
	
	/*****************************************************************************
	 * Calls named procedure. Note that date
     * arguments should be passed as <code>java.sql.Timestamp</code>
     *
     * @param procName name of the procedure to call
     *        inParameters arguments to call the procedure with
     * @throws BaseException If there are problems calling the procedure
     ****************************************************************************/
	public void doProcedure(String procedure, Collection inParameters) throws BaseException {
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
	 * @param procName name of the procedure to call
	 *        inParameters arguments to call the procedure with
	 *        outParameters output arguments to call the procedure with
	 * @returns results from procedure if any
	 * @throws BaseException If there are problems calling the procedure
	 ****************************************************************************/
	public Collection doProcedure(String procedure, Collection inParameters, Collection outParameters) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.doProcedure(connection, procedure, inParameters, outParameters);
	    } finally {
	    	try {
	    		getDbManager().returnConnection(connection);
	    	} catch (Exception e) {
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
	
	
	/*****************************************************************************
	 * Calls named procedure. Note that date
	 * arguments should be passed as <code>java.sql.Timestamp</code>
	 *
	 * @param connection database connection to use
	 *        procName name of the procedure to call
	 *        inParameters arguments to call the procedure with
	 *        outParameters output arguments to call the procedure with
	 * @returns results from procedure if any
	 * @throws BaseException If there are problems calling the procedure
	 ****************************************************************************/
	public Collection doProcedure(Connection connection, String procedure, Collection inParameters, Collection outParameters) throws BaseException {
		return new SqlManager().doProcedure(connection, procedure, inParameters, outParameters);
	}

} //end of class