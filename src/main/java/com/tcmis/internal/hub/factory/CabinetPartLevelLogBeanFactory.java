package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.CabinetPartLevelLogBean;

/******************************************************************************
 * CLASSNAME: CabinetPartLevelLogBeanFactory <br>
 * @version: 1.0, Oct 16, 2006 <br>
 *****************************************************************************/

public class CabinetPartLevelLogBeanFactory
		extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_OLD_REORDER_POINT = "OLD_REORDER_POINT";
	public String ATTRIBUTE_OLD_STOCKING_LEVEL = "OLD_STOCKING_LEVEL";
	public String ATTRIBUTE_NEW_REORDER_POINT = "NEW_REORDER_POINT";
	public String ATTRIBUTE_NEW_STOCKING_LEVEL = "NEW_STOCKING_LEVEL";
	public String ATTRIBUTE_DATE_MODIFIED = "DATE_MODIFIED";
	public String ATTRIBUTE_MODIFIED_BY = "MODIFIED_BY";
	public String ATTRIBUTE_REMARKS = "REMARKS";
	public String ATTRIBUTE_OLD_LEAD_TIME_DAYS = "OLD_LEAD_TIME_DAYS";
	public String ATTRIBUTE_NEW_LEAD_TIME_DAYS = "NEW_LEAD_TIME_DAYS";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_OLD_REORDER_QUANTITY = "OLD_REORDER_QUANTITY";
	public String ATTRIBUTE_NEW_REORDER_QUANTITY = "NEW_REORDER_QUANTITY";
	public String ATTRIBUTE_OLD_KANBAN_REORDER_QUANTITY = "OLD_KANBAN_REORDER_QUANTITY";
	public String ATTRIBUTE_NEW_KANBAN_REORDER_QUANTITY = "NEW_KANBAN_REORDER_QUANTITY";
	public String ATTRIBUTE_OLD_LEVEL_HOLD_END_DATE = "OLD_LEVEL_HOLD_END_DATE";
	public String ATTRIBUTE_NEW_LEVEL_HOLD_END_DATE = "NEW_LEVEL_HOLD_END_DATE";
	  
	//table name
	public String TABLE = "CABINET_PART_LEVEL_LOG";

	//constructor
	public CabinetPartLevelLogBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
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
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if (attributeName.equals("oldReorderPoint")) {
			return ATTRIBUTE_OLD_REORDER_POINT;
		}
		else if (attributeName.equals("oldStockingLevel")) {
			return ATTRIBUTE_OLD_STOCKING_LEVEL;
		}
		else if (attributeName.equals("newReorderPoint")) {
			return ATTRIBUTE_NEW_REORDER_POINT;
		}
		else if (attributeName.equals("newStockingLevel")) {
			return ATTRIBUTE_NEW_STOCKING_LEVEL;
		}
		else if (attributeName.equals("dateModified")) {
			return ATTRIBUTE_DATE_MODIFIED;
		}
		else if (attributeName.equals("modifiedBy")) {
			return ATTRIBUTE_MODIFIED_BY;
		}
		else if (attributeName.equals("remarks")) {
			return ATTRIBUTE_REMARKS;
		}
		else if (attributeName.equals("oldLeadTimeDays")) {
			return ATTRIBUTE_OLD_LEAD_TIME_DAYS;
		}
		else if (attributeName.equals("newLeadTimeDays")) {
			return ATTRIBUTE_NEW_LEAD_TIME_DAYS;
		}else if (attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}else if (attributeName.equals("oldReorderQuantity")) {
			return ATTRIBUTE_OLD_REORDER_QUANTITY;
		}else if (attributeName.equals("newReorderQuantity")) {
			return ATTRIBUTE_NEW_REORDER_QUANTITY;
		}else if (attributeName.equals("oldKanbanReorderQuantity")) {
			return ATTRIBUTE_OLD_KANBAN_REORDER_QUANTITY;
		}else if (attributeName.equals("newKanbanReorderQuantity")) {
			return ATTRIBUTE_NEW_KANBAN_REORDER_QUANTITY;
		} else if (attributeName.equals("newLevelHoldEndDate")) {
		    return ATTRIBUTE_NEW_LEVEL_HOLD_END_DATE;
	    } else if (attributeName.equals("oldLevelHoldEndDate")) {
	    	return ATTRIBUTE_OLD_LEVEL_HOLD_END_DATE;
	    } else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetPartLevelLogBean.class);
	}

//you need to verify the primary key(s) before uncommenting this

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

	public int delete(SearchCriteria criteria, Connection conn) throws
			BaseException {
		String sqlQuery = " delete from " + TABLE + " " +
				getWhereClause(criteria);
		return new SqlManager().update(conn, sqlQuery);
	}


//insert
	 public int insert(CabinetPartLevelLogBean cabinetPartLevelLogBean)
		throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = insert(cabinetPartLevelLogBean, connection);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }

	 public int insert(CabinetPartLevelLogBean cabinetPartLevelLogBean, Connection conn)
		throws BaseException {
		SqlManager sqlManager = new SqlManager();
		String query  =
		 "insert into " + TABLE + " (" +
		 ATTRIBUTE_COMPANY_ID + "," +
		 ATTRIBUTE_CATALOG_ID + "," +
		 ATTRIBUTE_CATALOG_COMPANY_ID + "," +
		 ATTRIBUTE_CAT_PART_NO + "," +
		 ATTRIBUTE_PART_GROUP_NO + "," +
		 ATTRIBUTE_FACILITY_ID + "," +
		 ATTRIBUTE_APPLICATION + "," +
		 ATTRIBUTE_OLD_REORDER_POINT + "," +
		 ATTRIBUTE_OLD_STOCKING_LEVEL + "," +
		 ATTRIBUTE_NEW_REORDER_POINT + "," +
		 ATTRIBUTE_NEW_STOCKING_LEVEL + "," +
		 ATTRIBUTE_DATE_MODIFIED + "," +
		 ATTRIBUTE_MODIFIED_BY + "," +
		 ATTRIBUTE_REMARKS + "," +
		 ATTRIBUTE_OLD_LEAD_TIME_DAYS + "," +
		 ATTRIBUTE_NEW_LEAD_TIME_DAYS + "," +
		 ATTRIBUTE_OLD_REORDER_QUANTITY + "," +
		 ATTRIBUTE_NEW_REORDER_QUANTITY + "," +
		 ATTRIBUTE_OLD_KANBAN_REORDER_QUANTITY + "," +
		 ATTRIBUTE_NEW_KANBAN_REORDER_QUANTITY +
		 ")" +
		 " values (" +
		 SqlHandler.delimitString(cabinetPartLevelLogBean.getCompanyId()) + "," +
		 SqlHandler.delimitString(cabinetPartLevelLogBean.getCatalogId()) + "," +
		 SqlHandler.delimitString(cabinetPartLevelLogBean.getCatalogCompanyId()) + "," +
		 SqlHandler.delimitString(cabinetPartLevelLogBean.getCatPartNo()) + "," +
		 cabinetPartLevelLogBean.getPartGroupNo() + "," +
		 SqlHandler.delimitString(cabinetPartLevelLogBean.getFacilityId()) + "," +
		 SqlHandler.delimitString(cabinetPartLevelLogBean.getApplication()) + "," +
		 cabinetPartLevelLogBean.getOldReorderPoint() + "," +
		 cabinetPartLevelLogBean.getOldStockingLevel() + "," +
		 cabinetPartLevelLogBean.getNewReorderPoint() + "," +
		 cabinetPartLevelLogBean.getNewStockingLevel() + "," +
		 DateHandler.getOracleToDateFunction(cabinetPartLevelLogBean.getDateModified()) + "," +
		 cabinetPartLevelLogBean.getModifiedBy() + "," +
		 SqlHandler.delimitString(cabinetPartLevelLogBean.getRemarks()) + "," +
		 cabinetPartLevelLogBean.getOldLeadTimeDays() + "," +
		 cabinetPartLevelLogBean.getNewLeadTimeDays() + "," +
		 cabinetPartLevelLogBean.getOldReorderQuantity() + "," +
		 cabinetPartLevelLogBean.getNewReorderQuantity() + "," +
		 cabinetPartLevelLogBean.getOldKanbanReorderQuantity() + "," +
		 cabinetPartLevelLogBean.getNewKanbanReorderQuantity() +
		 ")";
		 if(log.isDebugEnabled()) {
			 log.debug("QUERY:" + query);
		 }
		return sqlManager.update(conn, query);
	 }


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

	public Collection select(SearchCriteria criteria, Connection conn) throws
			BaseException {
		Collection cabinetPartLevelLogBeanColl = new Vector();
		String query = "select * from " + TABLE + " " +
				getWhereClause(criteria);
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetPartLevelLogBean cabinetPartLevelLogBean = new
					CabinetPartLevelLogBean();
			load(dataSetRow, cabinetPartLevelLogBean);
			cabinetPartLevelLogBeanColl.add(cabinetPartLevelLogBean);
		}
		return cabinetPartLevelLogBeanColl;
	}
}