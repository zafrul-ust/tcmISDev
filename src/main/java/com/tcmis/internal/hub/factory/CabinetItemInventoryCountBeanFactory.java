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
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;


/******************************************************************************
 * CLASSNAME: CabinetItemInventoryCountBeanFactory <br>
 * @version: 1.0, Sep 7, 2007 <br>
 *****************************************************************************/


public class CabinetItemInventoryCountBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_COUNT_DATETIME = "COUNT_DATETIME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_COUNT_QUANTITY = "COUNT_QUANTITY";
	public String ATTRIBUTE_DATE_PROCESSED = "DATE_PROCESSED";
	public String ATTRIBUTE_UPLOAD_SEQUENCE = "UPLOAD_SEQUENCE";
    
    //table name
	public String TABLE = "CABINET_ITEM_INVENTORY_COUNT";


	//constructor
	public CabinetItemInventoryCountBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("binId")) {
			return ATTRIBUTE_BIN_ID;
		}
		else if(attributeName.equals("countDatetime")) {
			return ATTRIBUTE_COUNT_DATETIME;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("countQuantity")) {
			return ATTRIBUTE_COUNT_QUANTITY;
		}
		else if(attributeName.equals("dateProcessed")) {
			return ATTRIBUTE_DATE_PROCESSED;
		}
		else if(attributeName.equals("uploadSequence")) {
			return ATTRIBUTE_UPLOAD_SEQUENCE;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetItemInventoryCountBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CabinetItemInventoryCountBean cabinetItemInventoryCountBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("binId", "SearchCriterion.EQUALS",
			"" + cabinetItemInventoryCountBean.getBinId());

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


	public int delete(CabinetItemInventoryCountBean cabinetItemInventoryCountBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("binId", "SearchCriterion.EQUALS",
			"" + cabinetItemInventoryCountBean.getBinId());

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

	//insert
	public int insert(CabinetItemInventoryCountBean cabinetItemInventoryCountBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(cabinetItemInventoryCountBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CabinetItemInventoryCountBean cabinetItemInventoryCountBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();
		String queryDate ="";
		if(cabinetItemInventoryCountBean.getCountDatetime()!=null)
		{
			queryDate=DateHandler.getOracleToDateFunction(cabinetItemInventoryCountBean.getCountDatetime());
		}
		else
		{
			queryDate = "sysdate";
		}

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BIN_ID + "," +
			ATTRIBUTE_COUNT_DATETIME + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
            ATTRIBUTE_UPLOAD_SEQUENCE + "," +
            ATTRIBUTE_COUNT_QUANTITY + ")" +
//			ATTRIBUTE_COUNT_QUANTITY + "," +
//			ATTRIBUTE_DATE_PROCESSED + ")" +
			" values (" +
			cabinetItemInventoryCountBean.getBinId() + "," +
			queryDate + "," +
			cabinetItemInventoryCountBean.getItemId() + "," +
			SqlHandler.delimitString(cabinetItemInventoryCountBean.getCompanyId()) + "," +
			cabinetItemInventoryCountBean.getPersonnelId() + "," +
            cabinetItemInventoryCountBean.getUploadSequence() + "," +
            cabinetItemInventoryCountBean.getCountQuantity() + ")" ;
//		cabinetItemInventoryCountBean.getCountQuantity() + "," +
//		" sysdate " + ")";

		return sqlManager.update(conn, query);
	}


/*	
	public int update(CabinetItemInventoryCountBean cabinetItemInventoryCountBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(cabinetItemInventoryCountBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CabinetItemInventoryCountBean cabinetItemInventoryCountBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BIN_ID + "=" + 
				StringHandler.nullIfZero(cabinetItemInventoryCountBean.getBinId()) + "," +
			ATTRIBUTE_COUNT_DATETIME + "=" + 
				DateHandler.getOracleToDateFunction(cabinetItemInventoryCountBean.getCountDatetime()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(cabinetItemInventoryCountBean.getItemId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(cabinetItemInventoryCountBean.getCompanyId()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" + 
				StringHandler.nullIfZero(cabinetItemInventoryCountBean.getPersonnelId()) + "," +
			ATTRIBUTE_COUNT_QUANTITY + "=" + 
				StringHandler.nullIfZero(cabinetItemInventoryCountBean.getCountQuantity()) + "," +
			ATTRIBUTE_DATE_PROCESSED + "=" + 
				DateHandler.getOracleToDateFunction(cabinetItemInventoryCountBean.getDateProcessed()) + " " +
			"where " + ATTRIBUTE_BIN_ID + "=" +
				cabinetItemInventoryCountBean.getBinId();

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

		Collection cabinetItemInventoryCountBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CabinetItemInventoryCountBean cabinetItemInventoryCountBean = new CabinetItemInventoryCountBean();
			load(dataSetRow, cabinetItemInventoryCountBean);
			cabinetItemInventoryCountBeanColl.add(cabinetItemInventoryCountBean);
		}

		return cabinetItemInventoryCountBeanColl;
	}

    //Update date processed. Mark items as processed
	 public int updateDateProcessed(String binIdsScannedList,String companyId, BigDecimal personnelId) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = updateDateProcessed(connection,binIdsScannedList,companyId,personnelId);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }

	 public int updateDateProcessed(BigDecimal uploadId,String companyId, BigDecimal personnelId) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = updateDateProcessed(connection,uploadId,companyId,personnelId);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }
	 
	 public int updateDateProcessed(Connection conn,BigDecimal uploadId, String companyId, BigDecimal personnelId) throws BaseException {
	        String query = "update cabinet_item_inventory_count x  set date_processed = sysdate  where PERSONNEL_ID = "+personnelId+" and COMPANY_ID = '"+companyId+"' and UPLOAD_SEQUENCE = "+uploadId+" and date_processed is null ";         
	/*                "and exists (select * from  cabinet_item_mr_create_view i,cabinet_part_item_bin p ";
	  query += " where  p.item_id = i.item_id (+) and  p.company_id = i.company_id (+) and  p.facility_id = i.ordering_facility (+) and  ";
	  query += " p.application = i.ordering_application (+) and  p.catalog_id = i.catalog_id (+) and  p.cat_part_no = i.cat_part_no (+) and ";
	  query += " p.part_group_no = i.part_group_no (+) and  nvl(i.count_datetime, x.count_datetime - 1) < x.count_datetime and  p.bin_id = x.bin_id) and date_processed is null ";*/
			return new SqlManager().update(conn, query);
		 }
	 public int updateDateProcessed(Connection conn,String binIdsScannedList, String companyId, BigDecimal personnelId) throws BaseException {
        String query = "update cabinet_item_inventory_count x  set date_processed = sysdate  where PERSONNEL_ID = "+personnelId+" and COMPANY_ID = '"+companyId+"' and BIN_ID in ("+binIdsScannedList+") and date_processed is null ";         
/*                "and exists (select * from  cabinet_item_mr_create_view i,cabinet_part_item_bin p ";
  query += " where  p.item_id = i.item_id (+) and  p.company_id = i.company_id (+) and  p.facility_id = i.ordering_facility (+) and  ";
  query += " p.application = i.ordering_application (+) and  p.catalog_id = i.catalog_id (+) and  p.cat_part_no = i.cat_part_no (+) and ";
  query += " p.part_group_no = i.part_group_no (+) and  nvl(i.count_datetime, x.count_datetime - 1) < x.count_datetime and  p.bin_id = x.bin_id) and date_processed is null ";*/
		return new SqlManager().update(conn, query);
	 }

    //Update date processed for any unprocessed scans from a previous scan for the bins that were just uploaded.
	 public int updateDateProcessedForPrevCounts(String binIdsScannedList,BigDecimal personnelId) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = updateDateProcessedForPrevCounts(connection,binIdsScannedList,personnelId);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }

	 public int updateDateProcessedForPrevCounts(Connection conn,String binIdsScannedList, BigDecimal personnelId) throws BaseException {
        String query = "update cabinet_item_inventory_count set date_processed = sysdate  where BIN_ID in ("+binIdsScannedList+") and date_processed is null";
		return new SqlManager().update(conn, query);
	 }
	 public int updateDateProcessedForPrevCounts(BigDecimal uploadId,BigDecimal personnelId) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = updateDateProcessedForPrevCounts(connection,uploadId,personnelId);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }
	 public int updateDateProcessedForPrevCounts(Connection conn,BigDecimal uploadId, BigDecimal personnelId) throws BaseException {
	        String query = "update cabinet_item_inventory_count set date_processed = sysdate where UPLOAD_SEQUENCE = "+uploadId+" and date_processed is null";
			return new SqlManager().update(conn, query);
	 }
}