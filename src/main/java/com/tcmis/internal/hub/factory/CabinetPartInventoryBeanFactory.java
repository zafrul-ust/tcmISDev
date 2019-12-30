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
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.hub.beans.CabinetPartInventoryBean;

/******************************************************************************
 * CLASSNAME: CabinetPartInventoryBeanFactory <br>
 * @version: 1.0, Oct 16, 2006 <br>
 *****************************************************************************/

public class CabinetPartInventoryBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_LEAD_TIME_DAYS = "LEAD_TIME_DAYS";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_REORDER_QUANTITY = "REORDER_QUANTITY";
	public String ATTRIBUTE_KANBAN_REORDER_QUANTITY = "KANBAN_REORDER_QUANTITY";
	public String ATTRIBUTE_BIN_NAME = "BIN_NAME";
	public String ATTRIBUTE_COUNT_TYPE = "COUNT_TYPE";
	public String ATTRIBUTE_DEFAULT_PART_TYPE = "DEFAULT_PART_TYPE";
	public String ATTRIBUTE_DEFAULT_PERMIT_ID = "DEFAULT_PERMIT_ID";
	public String ATTRIBUTE_DEFAULT_APPLICATION_METHOD_COD = "DEFAULT_APPLICATION_METHOD_COD";
	public String ATTRIBUTE_DEFAULT_SUBSTRATE_CODE = "DEFAULT_SUBSTRATE_CODE";
	public String ATTRIBUTE_TIER_II_TEMPERATURE_CODE = "TIER_II_TEMPERATURE_CODE";
    public String ATTRIBUTE_MODIFIED_BY = "MODIFIED_BY";
    public String ATTRIBUTE_DATE_MODIFIED = "DATE_MODIFIED";
    public String ATTRIBUTE_REMARKS = "REMARKS";

    //table name
	public String TABLE = "CABINET_PART_INVENTORY";

	//constructor
	public CabinetPartInventoryBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if (attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if (attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if (attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if (attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if (attributeName.equals("leadTimeDays")) {
			return ATTRIBUTE_LEAD_TIME_DAYS;
		}
		else if (attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if (attributeName.equals("binId")) {
			return ATTRIBUTE_BIN_ID;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if (attributeName.equals("reorderQuantity")) {
			return ATTRIBUTE_REORDER_QUANTITY;
		}
		else if (attributeName.equals("kanbanReorderQuantity")) {
			return ATTRIBUTE_KANBAN_REORDER_QUANTITY;
		}
		else if (attributeName.equals("binName")) {
			return ATTRIBUTE_BIN_NAME;
		}
		else if (attributeName.equals("countType")) {
			return ATTRIBUTE_COUNT_TYPE;
		}
		else if (attributeName.equals("defaultPartType")) {
			return ATTRIBUTE_DEFAULT_PART_TYPE;
		}
		else if (attributeName.equals("defaultPermitId")) {
			return ATTRIBUTE_DEFAULT_PERMIT_ID;
		}
		else if (attributeName.equals("defaultApplicationMethodCod")) {
			return ATTRIBUTE_DEFAULT_APPLICATION_METHOD_COD;
		}
		else if (attributeName.equals("defaultSubstrateId")) {
			return ATTRIBUTE_DEFAULT_SUBSTRATE_CODE;
		}
		else if (attributeName.equals("tierIIStorageTemperature")) {
			return ATTRIBUTE_TIER_II_TEMPERATURE_CODE;
		}else if (attributeName.equals("modifiedBy")) {
			return ATTRIBUTE_MODIFIED_BY;
		}else if (attributeName.equals("dateModified")) {
			return ATTRIBUTE_DATE_MODIFIED;
		}else if (attributeName.equals("remarks")) {
			return ATTRIBUTE_REMARKS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetPartInventoryBean.class);
	}

	public int delete(SearchCriteria criteria) throws BaseException {
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

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);
		return new SqlManager().update(conn, sqlQuery);
	}

	//insert
	public int insert(CabinetPartInventoryBean cabinetPartInventoryBean) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(cabinetPartInventoryBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(CabinetPartInventoryBean cabinetPartInventoryBean, Connection conn) throws BaseException {
		SqlManager sqlManager = new SqlManager();
		String query = "insert into " + TABLE + " (" + ATTRIBUTE_COMPANY_ID + ","
		+ ATTRIBUTE_FACILITY_ID + ","
		+ ATTRIBUTE_APPLICATION + ","
		+ ATTRIBUTE_CATALOG_ID + ","
		+ ATTRIBUTE_CAT_PART_NO + ","
		+ ATTRIBUTE_PART_GROUP_NO + ","
		+ ATTRIBUTE_REORDER_POINT + ","
		+ ATTRIBUTE_STOCKING_LEVEL + ","
		+ ATTRIBUTE_LEAD_TIME_DAYS + ","
		+ ATTRIBUTE_CATALOG_COMPANY_ID + ","
		+ ATTRIBUTE_BIN_ID + ","
		+ ATTRIBUTE_STATUS + ","
		+ ATTRIBUTE_REORDER_QUANTITY + ","
		+ ATTRIBUTE_KANBAN_REORDER_QUANTITY + ","
		+ ATTRIBUTE_BIN_NAME + ","
		+ ATTRIBUTE_DEFAULT_PART_TYPE + ","
		+ ATTRIBUTE_DEFAULT_PERMIT_ID + ","
		+ ATTRIBUTE_DEFAULT_APPLICATION_METHOD_COD + ","
		+ ATTRIBUTE_DEFAULT_SUBSTRATE_CODE + ","
		+ ATTRIBUTE_TIER_II_TEMPERATURE_CODE  + ","
		+ ATTRIBUTE_COUNT_TYPE +","
        + ATTRIBUTE_MODIFIED_BY  + ","
        + ATTRIBUTE_DATE_MODIFIED  + ","
        + ATTRIBUTE_REMARKS  + ""
        + ")"
		+ " values ("
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getCompanyId()) + ","
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getFacilityId()) + ","
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getApplication()) + ","
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getCatalogId()) + ","
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getCatPartNo()) + ","
		+ cabinetPartInventoryBean.getPartGroupNo() + ","
		+ cabinetPartInventoryBean.getReorderPoint() + ","
		+ cabinetPartInventoryBean.getStockingLevel() + ","
		+ cabinetPartInventoryBean.getLeadTimeDays() + ","
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getCatalogCompanyId()) + ","
		+ cabinetPartInventoryBean.getBinId() + ",'"
		+ cabinetPartInventoryBean.getStatus() + "',"
		+ cabinetPartInventoryBean.getReorderQuantity() + ","
		+ cabinetPartInventoryBean.getKanbanReorderQuantity() + ","
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getBinName()) + ","
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultPartType()) + ","
		+  SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultPermitId()) + ","
		+  SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultApplicationMethodCod()) + ","
		+  SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultSubstrateCode()) + ","
		+  SqlHandler.delimitString(cabinetPartInventoryBean.getTierIIStorageTemperatureCode()) + ",'"
		+ cabinetPartInventoryBean.getCountType() + "',"
        + cabinetPartInventoryBean.getModifiedBy() + ","
        + "sysdate,"
        +  SqlHandler.delimitString(cabinetPartInventoryBean.getRemarks())
        + ")";
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		return sqlManager.update(conn, query);
	}

	public int update(CabinetPartInventoryBean cabinetPartInventoryBean) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(cabinetPartInventoryBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(CabinetPartInventoryBean cabinetPartInventoryBean, Connection conn) throws BaseException {
		String query = "update " + TABLE + " set "
		+ ATTRIBUTE_REORDER_POINT + "=" + cabinetPartInventoryBean.getReorderPoint() + ","
		+ ATTRIBUTE_STOCKING_LEVEL + "=" + cabinetPartInventoryBean.getStockingLevel() + ","
		+ ATTRIBUTE_LEAD_TIME_DAYS+ "=" + cabinetPartInventoryBean.getLeadTimeDays() + ","
		+ ATTRIBUTE_REORDER_QUANTITY + "=" + cabinetPartInventoryBean.getReorderQuantity() + ","
		+ ATTRIBUTE_DEFAULT_PART_TYPE + "=" + SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultPartType()) + ","
		+ ATTRIBUTE_DEFAULT_PERMIT_ID + "=" +  SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultPermitId()) + ","
		+ ATTRIBUTE_DEFAULT_APPLICATION_METHOD_COD + "=" +  SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultApplicationMethodCod()) + ","
		+ ATTRIBUTE_DEFAULT_SUBSTRATE_CODE + "=" +  SqlHandler.delimitString(cabinetPartInventoryBean.getDefaultSubstrateCode()) + ","
		+ ATTRIBUTE_KANBAN_REORDER_QUANTITY + "=" + cabinetPartInventoryBean.getKanbanReorderQuantity()+","
        + ATTRIBUTE_MODIFIED_BY  + "="+cabinetPartInventoryBean.getModifiedBy()+","
    	+ ATTRIBUTE_REMARKS + "=" +  SqlHandler.delimitString(cabinetPartInventoryBean.getRemarks()) + ","
        + ATTRIBUTE_DATE_MODIFIED  + "= sysdate"
        + " where " + ATTRIBUTE_COMPANY_ID + "=" + SqlHandler.delimitString(cabinetPartInventoryBean.getCompanyId()) + " and " + ATTRIBUTE_CATALOG_ID + "="
		+ SqlHandler.delimitString(cabinetPartInventoryBean.getCatalogId()) + " and " + ATTRIBUTE_CATALOG_COMPANY_ID + "=" + SqlHandler.delimitString(cabinetPartInventoryBean.getCatalogCompanyId()) + " and " + ATTRIBUTE_FACILITY_ID
		+ "=" + SqlHandler.delimitString(cabinetPartInventoryBean.getFacilityId()) + " and " + ATTRIBUTE_APPLICATION + "=" + SqlHandler.delimitString(cabinetPartInventoryBean.getApplication()) + " and " + ATTRIBUTE_CAT_PART_NO
		+ "=" + SqlHandler.delimitString(cabinetPartInventoryBean.getCatPartNo()) + " and " + ATTRIBUTE_PART_GROUP_NO + "=" + cabinetPartInventoryBean.getPartGroupNo();

		if (cabinetPartInventoryBean.getBinId() != null) {
			query += " and " + ATTRIBUTE_BIN_ID + "=" + cabinetPartInventoryBean.getBinId();
		}

		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		return new SqlManager().update(conn, query);
	}

	/*
	 where COMPANY_ID = 'BOEING' and CATALOG_ID='Decatur'
	 and FACILITY_ID = 'Decatur' and APPLICATION ='BD-CC0001' and CAT_PART_NO = 'DPM0530*2' and PART_GROUP_NO = 1
	 */

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {
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

	public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
		Collection cabinetPartInventoryBeanColl = new Vector();
		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetPartInventoryBean cabinetPartInventoryBean = new CabinetPartInventoryBean();
			load(dataSetRow, cabinetPartInventoryBean);
			cabinetPartInventoryBeanColl.add(cabinetPartInventoryBean);
		}
		return cabinetPartInventoryBeanColl;
	}
}