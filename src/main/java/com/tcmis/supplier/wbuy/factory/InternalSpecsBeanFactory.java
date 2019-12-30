package com.tcmis.supplier.wbuy.factory;


import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.supplier.wbuy.beans.InternalSpecsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: InternalSpecsBeanFactory <br>
 * @version: 1.0, Sep 9, 2005 <br>
 *****************************************************************************/


public class InternalSpecsBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SPEC_ID = "SPEC_ID";
	public String ATTRIBUTE_SPEC_ID_DISPLAY = "SPEC_ID_DISPLAY";
	public String ATTRIBUTE_DETAIL = "DETAIL";
//	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SPEC_LIBRARY_DESC = "SPEC_LIBRARY_DESC";
	public String ATTRIBUTE_CONTENT = "CONTENT";
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
	public String ATTRIBUTE_CURRENT_COC_REQUIREMENT = "CURRENT_COC_REQUIREMENT";
	public String ATTRIBUTE_CURRENT_COA_REQUIREMENT = "CURRENT_COA_REQUIREMENT";
	public String ATTRIBUTE_SAVED_COC = "SAVED_COC";
	public String ATTRIBUTE_SAVED_COA = "SAVED_COA";
	public String ATTRIBUTE_COC_REQ_AT_SAVE = "COC_REQ_AT_SAVE";
	public String ATTRIBUTE_COA_REQ_AT_SAVE = "COA_REQ_AT_SAVE";
//	public String ATTRIBUTE_COC = "COC";
//	public String ATTRIBUTE_COA = "COA";
//	public String ATTRIBUTE_ATTACH = "ATTACH";
	//public String ATTRIBUTE_OK = "OK";
//	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_SPEC_LIBRARY = "SPEC_LIBRARY";
	public String ATTRIBUTE_COLOR = "COLOR";
	public String ATTRIBUTE_COLOR_AT_SAVE = "COLOR_AT_SAVE";        
//	public String ATTRIBUTE_CERT_REQUIRED_CURRENT = "CERT_REQUIRED_CURRENT";
//	public String ATTRIBUTE_CERT_REQUIRED_AT_SAVE = "CERT_REQUIRED_AT_SAVE";
//	public String ATTRIBUTE_DATE_LAST_CHANGED = "DATE_LAST_CHANGED";
//	public String ATTRIBUTE_STATUS = "STATUS";

	//table name
	public String TABLE = "INTERNAL_SPECS";


	//constructor
	public InternalSpecsBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("specId")) {
			return ATTRIBUTE_SPEC_ID;
		}
		else if(attributeName.equals("specIdDisplay")) {
			return ATTRIBUTE_SPEC_ID_DISPLAY;
		}
		else if(attributeName.equals("detail")) {
			return ATTRIBUTE_DETAIL;
		}
//		else if(attributeName.equals("companyId")) {
//			return ATTRIBUTE_COMPANY_ID;
//		}
		else if(attributeName.equals("specLibraryDesc")) {
			return ATTRIBUTE_SPEC_LIBRARY_DESC;
		}
		else if(attributeName.equals("content")) {
			return ATTRIBUTE_CONTENT;
		}
		else if(attributeName.equals("onLine")) {
			return ATTRIBUTE_ON_LINE;
		}
		else if(attributeName.equals("currentCocRequirement")) {
			return ATTRIBUTE_CURRENT_COC_REQUIREMENT;
		}
		else if(attributeName.equals("currentCoaRequirement")) {
			return ATTRIBUTE_CURRENT_COA_REQUIREMENT;
		}
		else if(attributeName.equals("savedCoc")) {
			return ATTRIBUTE_SAVED_COC;
		}
		else if(attributeName.equals("savedCoa")) {
			return ATTRIBUTE_SAVED_COA;
		}
		else if(attributeName.equals("cocReqAtSave")) {
			return ATTRIBUTE_COC_REQ_AT_SAVE;
		}
		else if(attributeName.equals("coaReqAtSave")) {
			return ATTRIBUTE_COA_REQ_AT_SAVE;
		}
//		else if(attributeName.equals("attach")) {
//			return ATTRIBUTE_ATTACH;
//		}
//		else if(attributeName.equals("ok")) {
//			return ATTRIBUTE_OK;
//		}
//		else if(attributeName.equals("itemId")) {
//			return ATTRIBUTE_ITEM_ID;
//		}
		else if(attributeName.equals("specLibrary")) {
			return ATTRIBUTE_SPEC_LIBRARY;
		}
		else if(attributeName.equals("color")) {
			return ATTRIBUTE_COLOR;
		}
		else if(attributeName.equals("colorAtSave")) {
			return ATTRIBUTE_COLOR_AT_SAVE;
		}
//		else if(attributeName.equals("certRequiredCurrent")) {
//			return ATTRIBUTE_CERT_REQUIRED_CURRENT;
//		}
//		else if(attributeName.equals("certRequiredAtSave")) {
//			return ATTRIBUTE_CERT_REQUIRED_AT_SAVE;
//		}
//		else if(attributeName.equals("dateLastChanged")) {
//			return ATTRIBUTE_DATE_LAST_CHANGED;
//		}
//		else if(attributeName.equals("status")) {
//			return ATTRIBUTE_STATUS;
//		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InternalSpecsBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InternalSpecsBean internalSpecsBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("specId", "SearchCriterion.EQUALS",
			"" + internalSpecsBean.getSpecId());

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


	public int delete(InternalSpecsBean internalSpecsBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("specId", "SearchCriterion.EQUALS",
			"" + internalSpecsBean.getSpecId());

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
	public int insert(InternalSpecsBean internalSpecsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(internalSpecsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InternalSpecsBean internalSpecsBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SPEC_ID + "," +
			ATTRIBUTE_SPEC_ID_DISPLAY + "," +
			ATTRIBUTE_DETAIL + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_SPEC_LIBRARY_DESC + "," +
			ATTRIBUTE_CONTENT + "," +
			ATTRIBUTE_ON_LINE + "," +
			ATTRIBUTE_COC + "," +
			ATTRIBUTE_COA + "," +
			ATTRIBUTE_ATTACH + "," +
			ATTRIBUTE_OK + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_SPEC_LIBRARY + "," +
			ATTRIBUTE_COLOR + "," +
			ATTRIBUTE_CERT_REQUIRED_CURRENT + "," +
			ATTRIBUTE_CERT_REQUIRED_AT_SAVE + "," +
			ATTRIBUTE_DATE_LAST_CHANGED + "," +
			ATTRIBUTE_STATUS + ")" +
 values (
			SqlHandler.delimitString(internalSpecsBean.getSpecId()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getSpecIdDisplay()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getDetail()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getCompanyId()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getSpecLibraryDesc()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getContent()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getOnLine()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getCoc()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getCoa()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getAttach()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getOk()) + "," +
			StringHandler.nullIfZero(internalSpecsBean.getItemId()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getSpecLibrary()) + "," +
			StringHandler.nullIfZero(internalSpecsBean.getColor()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getCertRequiredCurrent()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getCertRequiredAtSave()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getDateLastChanged()) + "," +
			SqlHandler.delimitString(internalSpecsBean.getStatus()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InternalSpecsBean internalSpecsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(internalSpecsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InternalSpecsBean internalSpecsBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SPEC_ID + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getSpecId()) + "," +
			ATTRIBUTE_SPEC_ID_DISPLAY + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getSpecIdDisplay()) + "," +
			ATTRIBUTE_DETAIL + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getDetail()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getCompanyId()) + "," +
			ATTRIBUTE_SPEC_LIBRARY_DESC + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getSpecLibraryDesc()) + "," +
			ATTRIBUTE_CONTENT + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getContent()) + "," +
			ATTRIBUTE_ON_LINE + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getOnLine()) + "," +
			ATTRIBUTE_COC + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getCoc()) + "," +
			ATTRIBUTE_COA + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getCoa()) + "," +
			ATTRIBUTE_ATTACH + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getAttach()) + "," +
			ATTRIBUTE_OK + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getOk()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(internalSpecsBean.getItemId()) + "," +
			ATTRIBUTE_SPEC_LIBRARY + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getSpecLibrary()) + "," +
			ATTRIBUTE_COLOR + "=" + 
				StringHandler.nullIfZero(internalSpecsBean.getColor()) + "," +
			ATTRIBUTE_CERT_REQUIRED_CURRENT + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getCertRequiredCurrent()) + "," +
			ATTRIBUTE_CERT_REQUIRED_AT_SAVE + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getCertRequiredAtSave()) + "," +
			ATTRIBUTE_DATE_LAST_CHANGED + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getDateLastChanged()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(internalSpecsBean.getStatus()) + " " +
			"where " + ATTRIBUTE_SPEC_ID + "=" +
				StringHandler.nullIfZero(internalSpecsBean.getSpecId());

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

		Collection internalSpecsBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InternalSpecsBean internalSpecsBean = new InternalSpecsBean();
			load(dataSetRow, internalSpecsBean);
			internalSpecsBeanColl.add(internalSpecsBean);
		}

		return internalSpecsBeanColl;
	}

	public Collection select(String query)
		throws BaseException {

		Collection internalSpecsBeanColl = new Vector();

  		Connection connection = null;
    	try {
    	    connection = this.getDbManager().getConnection();
                

			DataSet dataSet = new SqlManager().select(connection, query);
	
			Iterator dataIter = dataSet.iterator();
	
			while (dataIter.hasNext()) {
				DataSetRow dataSetRow = (DataSetRow)dataIter.next();
				InternalSpecsBean internalSpecsBean = new InternalSpecsBean();
				load(dataSetRow, internalSpecsBean);
				internalSpecsBeanColl.add(internalSpecsBean);
			}
         
    	} catch (Exception e) {
                return internalSpecsBeanColl;
        } finally {
			this.getDbManager().returnConnection(connection);
		}

		return internalSpecsBeanColl;
	}
        
  }