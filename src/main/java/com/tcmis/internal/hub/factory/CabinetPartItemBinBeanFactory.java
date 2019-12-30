package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;
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
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetPartItemBinBean;

/**
 * ***************************************************************************
 * CLASSNAME: CabinetPartItemBinBeanFactory <br>
 *
 * @version: 1.0, Oct 10, 2006 <br>
 * ***************************************************************************
 */

public class CabinetPartItemBinBeanFactory extends BaseBeanFactory {
	// column names
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

	Log log = LogFactory
		 .getLog(this.getClass());

	// table name
	public String TABLE = "CABINET_PART_ITEM_BIN";

	// constructor
	public CabinetPartItemBinBeanFactory(DbManager dbManager) {
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
	 * //delete public int delete(CabinetPartItemBinBean cabinetPartItemBinBean)
	 * throws BaseException { SearchCriteria criteria = new
	 * SearchCriteria("companyId", "SearchCriterion.EQUALS", "" +
	 * cabinetPartItemBinBean.getCompanyId()); Connection connection = null; int
	 * result = 0; try { connection = this.getDbManager().getConnection();
	 * result = this.delete(criteria, connection); } finally {
	 * this.getDbManager().returnConnection(connection); } return result; }
	 * public int delete(CabinetPartItemBinBean cabinetPartItemBinBean,
	 * Connection conn) throws BaseException { SearchCriteria criteria = new
	 * SearchCriteria("companyId", "SearchCriterion.EQUALS", "" +
	 * cabinetPartItemBinBean.getCompanyId()); return delete(criteria, conn); }
	 */

	// get column names

	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		} else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		} else if (attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		} else if (attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		} else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		} else if (attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		} else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		} else if (attributeName.equals("binId")) {
			return ATTRIBUTE_BIN_ID;
		} else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		} else if (attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		} else {
			return super
				 .getColumnName(attributeName);
		}
	}

	// get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetPartItemBinBean.class);
	}

	// insert
	public int insert(CabinetPartItemBinBean cabinetPartItemBinBean) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(cabinetPartItemBinBean, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(CabinetPartItemBinBean cabinetPartItemBinBean, Connection conn) throws BaseException {
		SqlManager sqlManager = new SqlManager();
		String query = "insert into " + TABLE + " (" +
			 ATTRIBUTE_COMPANY_ID + "," +
			 ATTRIBUTE_FACILITY_ID + "," +
			 ATTRIBUTE_APPLICATION + "," +
			 ATTRIBUTE_CATALOG_ID + "," +
			 ATTRIBUTE_CAT_PART_NO + "," +
			 ATTRIBUTE_PART_GROUP_NO + "," +
			 ATTRIBUTE_ITEM_ID + "," +
			 ATTRIBUTE_BIN_ID + "," +
			 ATTRIBUTE_STATUS + ","+
			 ATTRIBUTE_CATALOG_COMPANY_ID+
			 ")" + " values (" +
			 SqlHandler.delimitString(cabinetPartItemBinBean.getCompanyId()) + "," + 
			 SqlHandler.delimitString(cabinetPartItemBinBean.getFacilityId()) + "," +
			 SqlHandler.delimitString(cabinetPartItemBinBean.getApplication()) + "," +
			 SqlHandler.delimitString(cabinetPartItemBinBean.getCatalogId()) + "," +
			 SqlHandler.delimitString(cabinetPartItemBinBean.getCatPartNo()) + "," +
			 cabinetPartItemBinBean.getPartGroupNo() + "," +
			 cabinetPartItemBinBean.getItemId() + "," +
			 cabinetPartItemBinBean.getBinId() + "," +
			 SqlHandler.delimitString(cabinetPartItemBinBean.getStatus()) + "," +
			 SqlHandler.delimitString(cabinetPartItemBinBean.getCatalogCompanyId()) +
			 ")";
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		return sqlManager.update(conn, query);
	}

	// select
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
		Collection cabinetPartItemBinBeanColl = new Vector();
		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetPartItemBinBean cabinetPartItemBinBean = new CabinetPartItemBinBean();
			load(dataSetRow, cabinetPartItemBinBean);
			cabinetPartItemBinBeanColl.add(cabinetPartItemBinBean);
		}
		return cabinetPartItemBinBeanColl;
	}

	public int update(CabinetPartItemBinBean cabinetPartItemBinBean) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(cabinetPartItemBinBean, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(CabinetPartItemBinBean cabinetPartItemBinBean, Connection conn) throws BaseException {
		String query = "update " + TABLE + " set " + ATTRIBUTE_COMPANY_ID + "=" + SqlHandler.delimitString(cabinetPartItemBinBean
			 .getCompanyId()) + "," + ATTRIBUTE_FACILITY_ID + "=" + SqlHandler.delimitString(cabinetPartItemBinBean
			 .getFacilityId()) + "," + ATTRIBUTE_APPLICATION + "=" + SqlHandler.delimitString(cabinetPartItemBinBean
			 .getApplication()) + "," + ATTRIBUTE_CATALOG_ID + "=" + SqlHandler.delimitString(cabinetPartItemBinBean
			 .getCatalogId()) + "," + ATTRIBUTE_CAT_PART_NO + "=" + SqlHandler.delimitString(cabinetPartItemBinBean
			 .getCatPartNo()) + "," + ATTRIBUTE_PART_GROUP_NO + "=" + StringHandler.nullIfZero(cabinetPartItemBinBean
			 .getPartGroupNo()) + "," + ATTRIBUTE_ITEM_ID + "=" + StringHandler.nullIfZero(cabinetPartItemBinBean.getItemId()) + "," + ATTRIBUTE_BIN_ID + "=" + StringHandler.nullIfZero(cabinetPartItemBinBean.getBinId()) + "," + ATTRIBUTE_STATUS + "=" + SqlHandler.delimitString(cabinetPartItemBinBean.getStatus()) + " " + "where " + ATTRIBUTE_COMPANY_ID + "=" + cabinetPartItemBinBean.getCompanyId();
		return new SqlManager().update(conn, query);
	}

	public int updateStatus(BigDecimal binId, String status) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateStatus(binId, status, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updateStatus(BigDecimal binId, String status, Connection conn) throws BaseException {
		String query = "update " + TABLE + " set " + ATTRIBUTE_STATUS + "=" + SqlHandler.delimitString(status) + " " + "where " + ATTRIBUTE_BIN_ID + "=" + binId;
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		return new SqlManager().update(conn, query);
	}
}