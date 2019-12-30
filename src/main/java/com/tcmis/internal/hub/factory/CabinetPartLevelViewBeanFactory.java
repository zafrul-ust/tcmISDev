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
import com.tcmis.internal.hub.beans.CabinetPartLevelViewBean;

/**
 * ***************************************************************************
 * CLASSNAME: CabinetPartLevelViewBeanFactory <br>
 *
 * @version: 1.0, Oct 10, 2006 <br>
 * ***************************************************************************
 */

public class CabinetPartLevelViewBeanFactory extends BaseBeanFactory {

	// column names
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_BIN_NAME = "BIN_NAME";
	public String ATTRIBUTE_CABINET_ID = "CABINET_ID";
	public String ATTRIBUTE_CABINET_NAME = "CABINET_NAME";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COUNT_TYPE = "COUNT_TYPE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_LABEL_SPEC = "LABEL_SPEC";
	public String ATTRIBUTE_LEAD_TIME_DAYS = "LEAD_TIME_DAYS";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_MATERIAL_HANDLING_CODE = "MATERIAL_HANDLING_CODE";
	public String ATTRIBUTE_MATERIAL_ID_STRING = "MATERIAL_ID_STRING";
	public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_USE_APPLICATION = "USE_APPLICATION";
	public String ATTRIBUTE_BIN_PART_STATUS = "BIN_PART_STATUS";
	public String ATTRIBUTE_LEVEL_HOLD_END_DATE = "LEVEL_HOLD_END_DATE";
	
	Log log = LogFactory.getLog(this.getClass());

	// table name
	public String TABLE = "CABINET_PART_LEVEL_VIEW";

	// constructor
	public CabinetPartLevelViewBeanFactory(DbManager dbManager) {
		super(dbManager);
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

	// you need to verify the primary key(s) before uncommenting this
	/*
	 * //delete public int delete(CabinetPartLevelViewBean
	 * cabinetPartLevelViewBean) throws BaseException { SearchCriteria criteria
	 * = new SearchCriteria("sourceHub", "SearchCriterion.EQUALS", "" +
	 * cabinetPartLevelViewBean.getSourceHub()); Connection connection = null;
	 * int result = 0; try { connection = this.getDbManager().getConnection();
	 * result = this.delete(criteria, connection); } finally {
	 * this.getDbManager().returnConnection(connection); } return result; }
	 * public int delete(CabinetPartLevelViewBean cabinetPartLevelViewBean,
	 * Connection conn) throws BaseException { SearchCriteria criteria = new
	 * SearchCriteria("sourceHub", "SearchCriterion.EQUALS", "" +
	 * cabinetPartLevelViewBean.getSourceHub()); return delete(criteria, conn);
	 * }
	 */

	// get column names

	public String getColumnName(String attributeName) {
		if (attributeName.equals("sourceHub")) {
			return ATTRIBUTE_SOURCE_HUB;
		} else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		} else if (attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		} else if (attributeName.equals("materialIdString")) {
			return ATTRIBUTE_MATERIAL_ID_STRING;
		} else if (attributeName.equals("materialHandlingCode")) {
			return ATTRIBUTE_MATERIAL_HANDLING_CODE;
		} else if (attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		} else if (attributeName.equals("mfgDesc")) {
			return ATTRIBUTE_MFG_DESC;
		} else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		} else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		} else if (attributeName
			 .equals("application")) {
			return ATTRIBUTE_APPLICATION;
		} else if (attributeName
			 .equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		} else if (attributeName
			 .equals("labelSpec")) {
			return ATTRIBUTE_LABEL_SPEC;
		} else if (attributeName
			 .equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		} else if (attributeName
			 .equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		} else if (attributeName
			 .equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		} else if (attributeName
			 .equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		} else if (attributeName
			 .equals("binId")) {
			return ATTRIBUTE_BIN_ID;
		} else if (attributeName
			 .equals("cabinetId")) {
			return ATTRIBUTE_CABINET_ID;
		} else if (attributeName
			 .equals("binName")) {
			return ATTRIBUTE_BIN_NAME;
		} else if (attributeName
			 .equals("cabinetName")) {
			return ATTRIBUTE_CABINET_NAME;
		} else if (attributeName
			 .equals("useApplication")) {
			return ATTRIBUTE_USE_APPLICATION;
		} else if (attributeName
			 .equals("status")) {
			return ATTRIBUTE_STATUS;
		} else if (attributeName
			 .equals("countType")) {
			return ATTRIBUTE_COUNT_TYPE;
		} else if (attributeName
			 .equals("leadTimeDays")) {
			return ATTRIBUTE_LEAD_TIME_DAYS;
		} else if (attributeName
			 .equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		} else if (attributeName.equals("binPartStatus")) {
			return ATTRIBUTE_BIN_PART_STATUS;
		} else if (attributeName.equals("levelHoldEndDate")) {
	    	return ATTRIBUTE_LEVEL_HOLD_END_DATE;
	    } else {
			return super
				 .getColumnName(attributeName);
		}
	}

	// get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetPartLevelViewBean.class);
	}

	// you need to verify the primary key(s) before uncommenting this
	/*
	 * //insert public int insert(CabinetPartLevelViewBean
	 * cabinetPartLevelViewBean) throws BaseException { Connection connection =
	 * null; int result = 0; try { connection = getDbManager().getConnection();
	 * result = insert(cabinetPartLevelViewBean, connection); } finally {
	 * this.getDbManager().returnConnection(connection); } return result; }
	 * public int insert(CabinetPartLevelViewBean cabinetPartLevelViewBean,
	 * Connection conn) throws BaseException { SqlManager sqlManager = new
	 * SqlManager(); String query = "insert into " + TABLE + " (" +
	 * ATTRIBUTE_SOURCE_HUB + "," + ATTRIBUTE_ITEM_ID + "," +
	 * ATTRIBUTE_PACKAGING + "," + ATTRIBUTE_MATERIAL_ID_STRING + "," +
	 * ATTRIBUTE_MATERIAL_HANDLING_CODE + "," + ATTRIBUTE_MATERIAL_DESC + "," +
	 * ATTRIBUTE_MFG_DESC + "," + ATTRIBUTE_COMPANY_ID + "," +
	 * ATTRIBUTE_FACILITY_ID + "," + ATTRIBUTE_APPLICATION + "," +
	 * ATTRIBUTE_CATALOG_ID + "," + ATTRIBUTE_LABEL_SPEC + "," +
	 * ATTRIBUTE_CAT_PART_NO + "," + ATTRIBUTE_PART_GROUP_NO + "," +
	 * ATTRIBUTE_REORDER_POINT + "," + ATTRIBUTE_STOCKING_LEVEL + "," +
	 * ATTRIBUTE_BIN_ID + "," + ATTRIBUTE_CABINET_ID + "," + ATTRIBUTE_BIN_NAME
	 * + "," + ATTRIBUTE_CABINET_NAME + "," + ATTRIBUTE_USE_APPLICATION + "," +
	 * ATTRIBUTE_STATUS + "," + ATTRIBUTE_LEAD_TIME_DAYS + ")" + " values (" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getSourceHub()) + "," +
	 * cabinetPartLevelViewBean.getItemId() + "," +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getPackaging()) + "," +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getMaterialIdString())
	 * + "," +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getMaterialHandlingCode
	 * ()) + "," +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getMaterialDesc()) +
	 * "," + SqlHandler.delimitString(cabinetPartLevelViewBean.getMfgDesc()) +
	 * "," + SqlHandler.delimitString(cabinetPartLevelViewBean.getCompanyId()) +
	 * "," + SqlHandler.delimitString(cabinetPartLevelViewBean.getFacilityId())
	 * + "," +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getApplication()) + ","
	 * + SqlHandler.delimitString(cabinetPartLevelViewBean.getCatalogId()) + ","
	 * + SqlHandler.delimitString(cabinetPartLevelViewBean.getLabelSpec()) + ","
	 * + SqlHandler.delimitString(cabinetPartLevelViewBean.getCatPartNo()) + ","
	 * + cabinetPartLevelViewBean.getPartGroupNo() + "," +
	 * cabinetPartLevelViewBean.getReorderPoint() + "," +
	 * cabinetPartLevelViewBean.getStockingLevel() + "," +
	 * cabinetPartLevelViewBean.getBinId() + "," +
	 * cabinetPartLevelViewBean.getCabinetId() + "," +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getBinName()) + "," +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getCabinetName()) + ","
	 * + SqlHandler.delimitString(cabinetPartLevelViewBean.getUseApplication())
	 * + "," + SqlHandler.delimitString(cabinetPartLevelViewBean.getStatus()) +
	 * "," + cabinetPartLevelViewBean.getLeadTimeDays() + ")"; return
	 * sqlManager.update(conn, query); } //update public int
	 * update(CabinetPartLevelViewBean cabinetPartLevelViewBean) throws
	 * BaseException { Connection connection = null; int result = 0; try {
	 * connection = getDbManager().getConnection(); result =
	 * update(cabinetPartLevelViewBean, connection); } finally {
	 * this.getDbManager().returnConnection(connection); } return result; }
	 * public int update(CabinetPartLevelViewBean cabinetPartLevelViewBean,
	 * Connection conn) throws BaseException { String query = "update " + TABLE
	 * + " set " + ATTRIBUTE_SOURCE_HUB + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getSourceHub()) + "," +
	 * ATTRIBUTE_ITEM_ID + "=" +
	 * StringHandler.nullIfZero(cabinetPartLevelViewBean.getItemId()) + "," +
	 * ATTRIBUTE_PACKAGING + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getPackaging()) + "," +
	 * ATTRIBUTE_MATERIAL_ID_STRING + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getMaterialIdString())
	 * + "," + ATTRIBUTE_MATERIAL_HANDLING_CODE + "=" +
	 * SqlHandler.delimitString(
	 * cabinetPartLevelViewBean.getMaterialHandlingCode()) + "," +
	 * ATTRIBUTE_MATERIAL_DESC + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getMaterialDesc()) +
	 * "," + ATTRIBUTE_MFG_DESC + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getMfgDesc()) + "," +
	 * ATTRIBUTE_COMPANY_ID + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getCompanyId()) + "," +
	 * ATTRIBUTE_FACILITY_ID + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getFacilityId()) + ","
	 * + ATTRIBUTE_APPLICATION + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getApplication()) + ","
	 * + ATTRIBUTE_CATALOG_ID + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getCatalogId()) + "," +
	 * ATTRIBUTE_LABEL_SPEC + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getLabelSpec()) + "," +
	 * ATTRIBUTE_CAT_PART_NO + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getCatPartNo()) + "," +
	 * ATTRIBUTE_PART_GROUP_NO + "=" +
	 * StringHandler.nullIfZero(cabinetPartLevelViewBean.getPartGroupNo()) + ","
	 * + ATTRIBUTE_REORDER_POINT + "=" +
	 * StringHandler.nullIfZero(cabinetPartLevelViewBean.getReorderPoint()) +
	 * "," + ATTRIBUTE_STOCKING_LEVEL + "=" +
	 * StringHandler.nullIfZero(cabinetPartLevelViewBean.getStockingLevel()) +
	 * "," + ATTRIBUTE_BIN_ID + "=" +
	 * StringHandler.nullIfZero(cabinetPartLevelViewBean.getBinId()) + "," +
	 * ATTRIBUTE_CABINET_ID + "=" +
	 * StringHandler.nullIfZero(cabinetPartLevelViewBean.getCabinetId()) + "," +
	 * ATTRIBUTE_BIN_NAME + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getBinName()) + "," +
	 * ATTRIBUTE_CABINET_NAME + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getCabinetName()) + ","
	 * + ATTRIBUTE_USE_APPLICATION + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getUseApplication()) +
	 * "," + ATTRIBUTE_STATUS + "=" +
	 * SqlHandler.delimitString(cabinetPartLevelViewBean.getStatus()) + "," +
	 * ATTRIBUTE_LEAD_TIME_DAYS + "=" +
	 * StringHandler.nullIfZero(cabinetPartLevelViewBean.getLeadTimeDays()) +
	 * " " + "where " + ATTRIBUTE_SOURCE_HUB + "=" +
	 * cabinetPartLevelViewBean.getSourceHub(); return new
	 * SqlManager().update(conn, query); }
	 */

	// select

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
		Collection cabinetPartLevelViewBeanColl = new Vector();
		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetPartLevelViewBean cabinetPartLevelViewBean = new CabinetPartLevelViewBean();
			load(dataSetRow, cabinetPartLevelViewBean);
			cabinetPartLevelViewBeanColl.add(cabinetPartLevelViewBean);
		}
		return cabinetPartLevelViewBeanColl;
	}

	public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectDistinct(criteria, sortCriteria, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {
		Collection cabinetPartLevelViewBeanColl = new Vector();
		String query = "select distinct * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetPartLevelViewBean cabinetPartLevelViewBean = new CabinetPartLevelViewBean();
			load(dataSetRow, cabinetPartLevelViewBean);
			cabinetPartLevelViewBeanColl.add(cabinetPartLevelViewBean);
		}
		return cabinetPartLevelViewBeanColl;
	}
}