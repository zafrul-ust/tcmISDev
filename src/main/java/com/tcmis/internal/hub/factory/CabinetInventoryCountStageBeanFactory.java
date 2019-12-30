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
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;


/******************************************************************************
 * CLASSNAME: CabinetInventoryCountStageBeanFactory <br>
 * @version: 1.0, Jul 27, 2006 <br>
 *****************************************************************************/


public class CabinetInventoryCountStageBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_COUNT_DATETIME = "COUNT_DATETIME";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_COUNT_QUANTITY = "COUNT_QUANTITY";
	public String ATTRIBUTE_DATE_PROCESSED = "DATE_PROCESSED";
	public String ATTRIBUTE_UPLOAD_SEQUENCE = "UPLOAD_SEQUENCE";
    
    //table name
	public String TABLE = "CABINET_INVENTORY_COUNT_STAGE";


	//constructor
	public CabinetInventoryCountStageBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
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
		return super.getType(attributeName, CabinetInventoryCountStageBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CabinetInventoryCountStageBean cabinetInventoryCountStageBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("binId", "SearchCriterion.EQUALS",
			"" + cabinetInventoryCountStageBean.getBinId());

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


	public int delete(CabinetInventoryCountStageBean cabinetInventoryCountStageBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("binId", "SearchCriterion.EQUALS",
			"" + cabinetInventoryCountStageBean.getBinId());

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
	public int insert(CabinetInventoryCountStageBean cabinetInventoryCountStageBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(cabinetInventoryCountStageBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(CabinetInventoryCountStageBean cabinetInventoryCountStageBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BIN_ID + "," +
			ATTRIBUTE_COUNT_DATETIME + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			//ATTRIBUTE_DATE_PROCESSED + "," +
            ATTRIBUTE_UPLOAD_SEQUENCE + "," +
            ATTRIBUTE_COUNT_QUANTITY + ")" +
			" values (" +
			cabinetInventoryCountStageBean.getBinId() + "," +
			DateHandler.getOracleToDateFunction(cabinetInventoryCountStageBean.getCountDatetime()) + "," +
			cabinetInventoryCountStageBean.getReceiptId() + "," +
			SqlHandler.delimitString(cabinetInventoryCountStageBean.getCompanyId()) + "," +
			cabinetInventoryCountStageBean.getPersonnelId() + "," +
			//DateHandler.getOracleToDateFunction(cabinetInventoryCountStageBean.getDateProcessed()) + "," +
            cabinetInventoryCountStageBean.getUploadSequence() + "," +
            cabinetInventoryCountStageBean.getCountQuantity() + ")";

		return sqlManager.update(conn, query);
	}

    //deleteDuplicateScans
	 public int deleteDuplicateScans(BigDecimal personnelId) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = deleteDuplicateScans(connection,personnelId);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }


	 public int deleteDuplicateScans(Connection conn,BigDecimal personnelId) throws BaseException {
		String deleteDupFromSatgeTable = "delete from cabinet_inventory_count_stage where (BIN_ID, COUNT_DATETIME, RECEIPT_ID, COMPANY_ID, PERSONNEL_ID, COUNT_QUANTITY, nvl(DATE_PROCESSED,sysdate+6757)) in ( ";
           deleteDupFromSatgeTable +=" select y.BIN_ID, y.COUNT_DATETIME, y.RECEIPT_ID, y.COMPANY_ID, y.PERSONNEL_ID, y.COUNT_QUANTITY, nvl(y.DATE_PROCESSED,sysdate+6757) from cabinet_inventory_count x,cabinet_inventory_count_stage y where x.COUNT_DATETIME = y.COUNT_DATETIME and ";
           deleteDupFromSatgeTable +=" x.bin_id = y.bin_id and x.receipt_id = y.receipt_id ";
           deleteDupFromSatgeTable +=" and x.personnel_id  = "+personnelId+" and x.COUNT_DATETIME > sysdate -10) ";

		return new SqlManager().update(conn, deleteDupFromSatgeTable);
	 }

	//Consolodate cabinet Scan
	 public int consolidateCabinetScan(BigDecimal personnelId,BigDecimal nextupLoadSeq) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = consolidateCabinetScan(connection,personnelId,nextupLoadSeq);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }


	 public int consolidateCabinetScan(Connection conn,BigDecimal personnelId,BigDecimal nextupLoadSeq) throws BaseException {
		String query = "insert into cabinet_inventory_count (bin_id,count_datetime,receipt_id,company_id,personnel_id, count_quantity,date_processed,upload_sequence) ";
		query += "select bin_id,count_datetime,receipt_id,company_id,personnel_id,sum(count_quantity),date_processed,upload_sequence from cabinet_inventory_count_stage where upload_sequence = "+nextupLoadSeq+" ";
		query += "group by bin_id,count_datetime,receipt_id,company_id,personnel_id, date_processed,upload_sequence ";

		return new SqlManager().update(conn, query);
	 }

	 //Update date processed. Mark items as processed
	 public int updateDateProcessed(String binIdsScannedList,BigDecimal personnelId,BigDecimal nextupLoadSeq) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = updateDateProcessed(connection,binIdsScannedList,personnelId,nextupLoadSeq);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }

	 public int updateDateProcessed(Connection conn,String binIdsScannedList,BigDecimal personnelId,BigDecimal nextupLoadSeq) throws BaseException {
        String query = "update cabinet_inventory_count c set date_processed =sysdate  where upload_sequence = "+nextupLoadSeq+" and bin_id in ("+binIdsScannedList+") and date_processed is null";
/*
         and ( BIN_ID, COUNT_DATETIME, RECEIPT_ID) ";
		query += " in (select c1.BIN_ID,c1.COUNT_DATETIME, c1.RECEIPT_ID from cabinet_part_item_bin b, cabinet_mr_create_view e ,cabinet_inventory_count c1	 ";
		query += " where  b.bin_id=c1.bin_id and b.company_id = e.company_id (+) and b.facility_id = e.ordering_facility (+) and b.application = e.ordering_application (+) ";
		query += " and b.catalog_id = e.catalog_id (+) and b.cat_part_no = e.cat_part_no (+) and b.part_group_no = e.part_group_no (+)  ";
		query += " and nvl(e.count_datetime,c1.count_datetime - 1) < c1.count_datetime) ";
*/

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
        String query = "update cabinet_inventory_count set date_processed = sysdate  where BIN_ID in ("+binIdsScannedList+") and date_processed is null";
		return new SqlManager().update(conn, query);
	 }
/*
	//update
	public int update(CabinetInventoryCountStageBean cabinetInventoryCountStageBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(cabinetInventoryCountStageBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CabinetInventoryCountStageBean cabinetInventoryCountStageBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BIN_ID + "=" +
				StringHandler.nullIfZero(cabinetInventoryCountStageBean.getBinId()) + "," +
			ATTRIBUTE_COUNT_DATETIME + "=" +
				DateHandler.getOracleToDateFunction(cabinetInventoryCountStageBean.getCountDatetime()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" +
				StringHandler.nullIfZero(cabinetInventoryCountStageBean.getReceiptId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryCountStageBean.getCompanyId()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(cabinetInventoryCountStageBean.getPersonnelId()) + "," +
			ATTRIBUTE_COUNT_QUANTITY + "=" +
				StringHandler.nullIfZero(cabinetInventoryCountStageBean.getCountQuantity()) + "," +
			ATTRIBUTE_DATE_PROCESSED + "=" +
				DateHandler.getOracleToDateFunction(cabinetInventoryCountStageBean.getDateProcessed()) + " " +
			"where " + ATTRIBUTE_BIN_ID + "=" +
				cabinetInventoryCountStageBean.getBinId();

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

		Collection cabinetInventoryCountStageBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CabinetInventoryCountStageBean cabinetInventoryCountStageBean = new CabinetInventoryCountStageBean();
			load(dataSetRow, cabinetInventoryCountStageBean);
			cabinetInventoryCountStageBeanColl.add(cabinetInventoryCountStageBean);
		}

		return cabinetInventoryCountStageBeanColl;
	}
}