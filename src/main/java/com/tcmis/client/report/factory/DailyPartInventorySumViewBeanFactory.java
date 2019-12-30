package com.tcmis.client.report.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.report.beans.DailyPartInventorySumViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.*;

/**
 * ***************************************************************************
 * CLASSNAME: DailyPartInventorySumViewBeanFactory <br>
 *
 * @version: 1.0, Nov 28, 2005 <br>
 * ***************************************************************************
 */

public class DailyPartInventorySumViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DAILY_DATE = "DAILY_DATE";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_LOW_ALARM = "LOW_ALARM";
	public String ATTRIBUTE_HIGH_ALARM = "HIGH_ALARM";
	public String ATTRIBUTE_CAPACITY = "CAPACITY";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_CONFIRMED_PO = "CONFIRMED_PO";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_OPEN_BLANKET_PO = "OPEN_BLANKET_PO";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

	//table name
	public String TABLE = "DAILY_PART_INVENTORY_SUM_VIEW";

	//constructor
	public DailyPartInventorySumViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	public String getColumnName(String attributeName) {
		if (attributeName.equals("dailyDate")) {
			return ATTRIBUTE_DAILY_DATE;
		} else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		} else if (attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		} else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		} else if (attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		} else if (attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		} else if (attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		} else if (attributeName.equals("lowAlarm")) {
			return ATTRIBUTE_LOW_ALARM;
		} else if (attributeName.equals("highAlarm")) {
			return ATTRIBUTE_HIGH_ALARM;
		} else if (attributeName.equals("capacity")) {
			return ATTRIBUTE_CAPACITY;
		} else if (attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		} else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		} else if (attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		} else if (attributeName.equals("confirmedPo")) {
			return ATTRIBUTE_CONFIRMED_PO;
		} else if (attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		} else if (attributeName.equals("openBlanketPo")) {
			return ATTRIBUTE_OPEN_BLANKET_PO;
		} else if (attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		} else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DailyPartInventorySumViewBean.class);
	}

	public int delete(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);
		return new SqlManager().update(conn, sqlQuery);
	}

	//select

	public Collection select(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
		Collection dailyPartInventorySumViewBeanColl = new Vector();
		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			DailyPartInventorySumViewBean dailyPartInventorySumViewBean = new DailyPartInventorySumViewBean();
			load(dataSetRow, dailyPartInventorySumViewBean);
			dailyPartInventorySumViewBeanColl.add(dailyPartInventorySumViewBean);
		}
		return dailyPartInventorySumViewBeanColl;
	}

	public Collection selectInventory(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, Date fromDate, Date toDate, String catalogCompanyId) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectInventory(inventoryGroup, partNumber, partGroupNo, catalogId, fromDate, toDate,catalogCompanyId, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectInventory(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, Date fromDate, Date toDate, String catalogCompanyId, Connection conn) throws BaseException {
		Collection dailyPartInventorySumViewBeanColl = new Vector();
		String query = "select * from " + TABLE + " " + "where (QUANTITY IS NOT NULL or confirmed_po is not null) " + "and " +
			 				ATTRIBUTE_CATALOG_ID + " = " + SqlHandler.delimitString(catalogId) + " " + "and " +
			 				ATTRIBUTE_INVENTORY_GROUP + " = " + SqlHandler.delimitString(inventoryGroup) + " " + "and " +
			 				ATTRIBUTE_CAT_PART_NO + " = " + SqlHandler.delimitString(partNumber) + " " + "and " +
			 				ATTRIBUTE_PART_GROUP_NO + " = " + SqlHandler.delimitString(partGroupNo) + " " + "and " +
			 				ATTRIBUTE_DAILY_DATE + " >= " + DateHandler.getOracleToDateFunction(fromDate) + " " + "and " +
			 				ATTRIBUTE_DAILY_DATE + " <= " + DateHandler.getOracleToDateFunction(toDate);
		if (!StringHandler.isBlankString(catalogCompanyId)) {
			query += " and "+ATTRIBUTE_CATALOG_COMPANY_ID+" = "+SqlHandler.delimitString(catalogCompanyId);
		}
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			DailyPartInventorySumViewBean dailyPartInventorySumViewBean = new DailyPartInventorySumViewBean();
			load(dataSetRow, dailyPartInventorySumViewBean);
			dailyPartInventorySumViewBeanColl.add(dailyPartInventorySumViewBean);
		}
		return dailyPartInventorySumViewBeanColl;
	}
}