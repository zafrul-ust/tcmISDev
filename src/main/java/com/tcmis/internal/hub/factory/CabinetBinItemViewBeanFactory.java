package com.tcmis.internal.hub.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.CabinetBinItemViewBean;


/******************************************************************************
 * CLASSNAME: CabinetBinItemViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CabinetBinItemViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CABINET_ID = "CABINET_ID";
	public String ATTRIBUTE_BIN_NAME = "BIN_NAME";
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_CABINET_NAME = "CABINET_NAME";
	public String ATTRIBUTE_PACKAGING= "PACKAGING";
	public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_USE_APPLICATION = "USE_APPLICATION";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_CATALOG_DESC = "CATALOG_DESC";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_CPIG_STATUS = "CPIG_STATUS";
	public String ATTRIBUTE_COUNT_TYPE = "COUNT_TYPE";
    public String ATTRIBUTE_PRIOR_COUNT_DATETIME = "PRIOR_COUNT_DATETIME";
    public String ATTRIBUTE_CABINET_START_DATE = "CABINET_START_DATE";
    public String ATTRIBUTE_STARTUP_COUNT_NEEDED = "STARTUP_COUNT_NEEDED";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";



    //table name
	public String TABLE = "CABINET_BIN_ITEM_VIEW";


	//constructor
	public CabinetBinItemViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("cabinetId")) {
			return ATTRIBUTE_CABINET_ID;
		}
		else if(attributeName.equals("binName")) {
			return ATTRIBUTE_BIN_NAME;
		}
		else if(attributeName.equals("binId")) {
			return ATTRIBUTE_BIN_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("cabinetName")) {
			return ATTRIBUTE_CABINET_NAME;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("description")) {
			return ATTRIBUTE_DESCRIPTION;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("useApplication")) {
			return ATTRIBUTE_USE_APPLICATION;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if(attributeName.equals("catalogDesc")) {
			return ATTRIBUTE_CATALOG_DESC;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("cpigStatus")) {
			return ATTRIBUTE_CPIG_STATUS;
		}
		else if(attributeName.equals("countType")) {
			return ATTRIBUTE_COUNT_TYPE;
		}
        else if(attributeName.equals("priorCountDatetime")) {
			return ATTRIBUTE_PRIOR_COUNT_DATETIME;
		}
        else if(attributeName.equals("cabinetStartDate")) {
			return ATTRIBUTE_CABINET_START_DATE;
		}
        else if(attributeName.equals("startupCountNeeded")) {
			return ATTRIBUTE_STARTUP_COUNT_NEEDED;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetBinItemViewBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(CabinetBinItemViewBean cabinetBinItemViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + cabinetBinItemViewBean.getCompanyId());

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


	public int delete(CabinetBinItemViewBean cabinetBinItemViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + cabinetBinItemViewBean.getCompanyId());

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
	public int insert(CabinetBinItemViewBean cabinetBinItemViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(cabinetBinItemViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CabinetBinItemViewBean cabinetBinItemViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CABINET_ID + "," +
			ATTRIBUTE_BIN_NAME + "," +
			ATTRIBUTE_BIN_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_CABINET_NAME + ")" +
			" values (" +
			SqlHandler.delimitString(cabinetBinItemViewBean.getCompanyId()) + "," +
			cabinetBinItemViewBean.getCabinetId() + "," +
			SqlHandler.delimitString(cabinetBinItemViewBean.getBinName()) + "," +
			cabinetBinItemViewBean.getBinId() + "," +
			cabinetBinItemViewBean.getItemId() + "," +
			SqlHandler.delimitString(cabinetBinItemViewBean.getCabinetName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CabinetBinItemViewBean cabinetBinItemViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(cabinetBinItemViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CabinetBinItemViewBean cabinetBinItemViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(cabinetBinItemViewBean.getCompanyId()) + "," +
			ATTRIBUTE_CABINET_ID + "=" +
				StringHandler.nullIfZero(cabinetBinItemViewBean.getCabinetId()) + "," +
			ATTRIBUTE_BIN_NAME + "=" +
				SqlHandler.delimitString(cabinetBinItemViewBean.getBinName()) + "," +
			ATTRIBUTE_BIN_ID + "=" +
				StringHandler.nullIfZero(cabinetBinItemViewBean.getBinId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(cabinetBinItemViewBean.getItemId()) + "," +
			ATTRIBUTE_CABINET_NAME + "=" +
				SqlHandler.delimitString(cabinetBinItemViewBean.getCabinetName()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				cabinetBinItemViewBean.getCompanyId();

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

		Collection cabinetBinItemViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + (sortCriteria != null ? getOrderByClause(sortCriteria) : " ORDER BY CABINET_ID,BIN_ID,ITEM_ID");

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CabinetBinItemViewBean cabinetBinItemViewBean = new CabinetBinItemViewBean();
			load(dataSetRow, cabinetBinItemViewBean);
			cabinetBinItemViewBeanColl.add(cabinetBinItemViewBean);
		}

		return cabinetBinItemViewBeanColl;
	}
}