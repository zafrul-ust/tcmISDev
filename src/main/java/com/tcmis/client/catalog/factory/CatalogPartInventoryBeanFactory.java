package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatalogPartInventoryBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: CatalogPartInventoryBeanFactory <br>
 * @version: 1.0, Feb 28, 2006 <br>
 *****************************************************************************/

public class CatalogPartInventoryBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_INITIAL_ANNUAL_USE_ESTIMATE =
	"INITIAL_ANNUAL_USE_ESTIMATE";
 public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
 public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
 public String ATTRIBUTE_ESTABLISHED_STOCK_FLAG = "ESTABLISHED_STOCK_FLAG";
 public String ATTRIBUTE_SHARING_CUTOFF_QUANTITY = "SHARING_CUTOFF_QUANTITY";
 public String ATTRIBUTE_RESERVE_QUANTITY = "RESERVE_QUANTITY";
 public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
 public String ATTRIBUTE_SOURCE_HUB_EXCEPTION = "SOURCE_HUB_EXCEPTION";
 public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
 public String ATTRIBUTE_LOOK_AHEAD_DAYS = "LOOK_AHEAD_DAYS";
 public String ATTRIBUTE_DROP_SHIP_OVERRIDE = "DROP_SHIP_OVERRIDE";
 public String ATTRIBUTE_STOCKING_METHOD_TIME_STAMP =
	"STOCKING_METHOD_TIME_STAMP";
 public String ATTRIBUTE_QUALITY_CONTROL = "QUALITY_CONTROL";
 public String ATTRIBUTE_POS_OPTION = "POS_OPTION";
 public String ATTRIBUTE_STATUS = "STATUS";
 public String ATTRIBUTE_LAST_REORDER_POINT_INCREASED =
	"LAST_REORDER_POINT_INCREASED";
 public String ATTRIBUTE_REORDER_QUANTITY = "REORDER_QUANTITY";
 public String ATTRIBUTE_TANK_NAME = "TANK_NAME";
 public String ATTRIBUTE_INVENTORY_FACILITY_ID = "INVENTORY_FACILITY_ID";
 public String ATTRIBUTE_INVENTORY_APPLICATION = "INVENTORY_APPLICATION";
 public String ATTRIBUTE_USE_FACILITY_ID = "USE_FACILITY_ID";
 public String ATTRIBUTE_USE_APPLICATION = "USE_APPLICATION";
 public String ATTRIBUTE_LOW_ALARM = "LOW_ALARM";
 public String ATTRIBUTE_HIGH_ALARM = "HIGH_ALARM";
 public String ATTRIBUTE_CAPACITY = "CAPACITY";
 public String ATTRIBUTE_COUNT_UOM = "COUNT_UOM";
 public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD =
	"RECEIPT_PROCESSING_METHOD";
 public String ATTRIBUTE_BILLING_METHOD = "BILLING_METHOD";
 public String ATTRIBUTE_BILLING_METHOD_EFFECTIVE_DATE =
	"BILLING_METHOD_EFFECTIVE_DATE";
 public String ATTRIBUTE_VIRTUAL_INVENTORY = "VIRTUAL_INVENTORY";
 public String ATTRIBUTE_MEDIAN_MR_LEAD_TIME = "MEDIAN_MR_LEAD_TIME";

 //table name
 public String TABLE = "CATALOG_PART_INVENTORY";

 //constructor
 public CatalogPartInventoryBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("catalogId")) {
	 return ATTRIBUTE_CATALOG_ID;
	}
	else if (attributeName.equals("catPartNo")) {
	 return ATTRIBUTE_CAT_PART_NO;
	}
	else if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("initialAnnualUseEstimate")) {
	 return ATTRIBUTE_INITIAL_ANNUAL_USE_ESTIMATE;
	}
	else if (attributeName.equals("reorderPoint")) {
	 return ATTRIBUTE_REORDER_POINT;
	}
	else if (attributeName.equals("stockingLevel")) {
	 return ATTRIBUTE_STOCKING_LEVEL;
	}
	else if (attributeName.equals("establishedStockFlag")) {
	 return ATTRIBUTE_ESTABLISHED_STOCK_FLAG;
	}
	else if (attributeName.equals("sharingCutoffQuantity")) {
	 return ATTRIBUTE_SHARING_CUTOFF_QUANTITY;
	}
	else if (attributeName.equals("reserveQuantity")) {
	 return ATTRIBUTE_RESERVE_QUANTITY;
	}
	else if (attributeName.equals("stockingMethod")) {
	 return ATTRIBUTE_STOCKING_METHOD;
	}
	else if (attributeName.equals("sourceHubException")) {
	 return ATTRIBUTE_SOURCE_HUB_EXCEPTION;
	}
	else if (attributeName.equals("partGroupNo")) {
	 return ATTRIBUTE_PART_GROUP_NO;
	}
	else if (attributeName.equals("lookAheadDays")) {
	 return ATTRIBUTE_LOOK_AHEAD_DAYS;
	}
	else if (attributeName.equals("dropShipOverride")) {
	 return ATTRIBUTE_DROP_SHIP_OVERRIDE;
	}
	else if (attributeName.equals("stockingMethodTimeStamp")) {
	 return ATTRIBUTE_STOCKING_METHOD_TIME_STAMP;
	}
	else if (attributeName.equals("qualityControl")) {
	 return ATTRIBUTE_QUALITY_CONTROL;
	}
	else if (attributeName.equals("posOption")) {
	 return ATTRIBUTE_POS_OPTION;
	}
	else if (attributeName.equals("status")) {
	 return ATTRIBUTE_STATUS;
	}
	else if (attributeName.equals("lastReorderPointIncreased")) {
	 return ATTRIBUTE_LAST_REORDER_POINT_INCREASED;
	}
	else if (attributeName.equals("reorderQuantity")) {
	 return ATTRIBUTE_REORDER_QUANTITY;
	}
	else if (attributeName.equals("tankName")) {
	 return ATTRIBUTE_TANK_NAME;
	}
	else if (attributeName.equals("inventoryFacilityId")) {
	 return ATTRIBUTE_INVENTORY_FACILITY_ID;
	}
	else if (attributeName.equals("inventoryApplication")) {
	 return ATTRIBUTE_INVENTORY_APPLICATION;
	}
	else if (attributeName.equals("useFacilityId")) {
	 return ATTRIBUTE_USE_FACILITY_ID;
	}
	else if (attributeName.equals("useApplication")) {
	 return ATTRIBUTE_USE_APPLICATION;
	}
	else if (attributeName.equals("lowAlarm")) {
	 return ATTRIBUTE_LOW_ALARM;
	}
	else if (attributeName.equals("highAlarm")) {
	 return ATTRIBUTE_HIGH_ALARM;
	}
	else if (attributeName.equals("capacity")) {
	 return ATTRIBUTE_CAPACITY;
	}
	else if (attributeName.equals("countUom")) {
	 return ATTRIBUTE_COUNT_UOM;
	}
	else if (attributeName.equals("receiptProcessingMethod")) {
	 return ATTRIBUTE_RECEIPT_PROCESSING_METHOD;
	}
	else if (attributeName.equals("billingMethod")) {
	 return ATTRIBUTE_BILLING_METHOD;
	}
	else if (attributeName.equals("billingMethodEffectiveDate")) {
	 return ATTRIBUTE_BILLING_METHOD_EFFECTIVE_DATE;
	}
	else if (attributeName.equals("virtualInventory")) {
	 return ATTRIBUTE_VIRTUAL_INVENTORY;
	}
	else if (attributeName.equals("medianMrLeadTime")) {
	 return ATTRIBUTE_MEDIAN_MR_LEAD_TIME;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, CatalogPartInventoryBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(CatalogPartInventoryBean catalogPartInventoryBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
		"" + catalogPartInventoryBean.getCatalogId());
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
	public int delete(CatalogPartInventoryBean catalogPartInventoryBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
		"" + catalogPartInventoryBean.getCatalogId());
	 return delete(criteria, conn);
	}
	*/

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

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	public int insert(CatalogPartInventoryBean catalogPartInventoryBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(catalogPartInventoryBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(CatalogPartInventoryBean catalogPartInventoryBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_CAT_PART_NO + "," +
		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_INITIAL_ANNUAL_USE_ESTIMATE + "," +
		ATTRIBUTE_REORDER_POINT + "," +
		ATTRIBUTE_STOCKING_LEVEL + "," +
		ATTRIBUTE_ESTABLISHED_STOCK_FLAG + "," +
		ATTRIBUTE_SHARING_CUTOFF_QUANTITY + "," +
		ATTRIBUTE_RESERVE_QUANTITY + "," +
		ATTRIBUTE_STOCKING_METHOD + "," +
		ATTRIBUTE_SOURCE_HUB_EXCEPTION + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_LOOK_AHEAD_DAYS + "," +
		ATTRIBUTE_DROP_SHIP_OVERRIDE + "," +
		ATTRIBUTE_STOCKING_METHOD_TIME_STAMP + "," +
		ATTRIBUTE_QUALITY_CONTROL + "," +
		ATTRIBUTE_POS_OPTION + "," +
		ATTRIBUTE_STATUS + "," +
		ATTRIBUTE_LAST_REORDER_POINT_INCREASED + "," +
		ATTRIBUTE_REORDER_QUANTITY + "," +
		ATTRIBUTE_TANK_NAME + "," +
		ATTRIBUTE_INVENTORY_FACILITY_ID + "," +
		ATTRIBUTE_INVENTORY_APPLICATION + "," +
		ATTRIBUTE_USE_FACILITY_ID + "," +
		ATTRIBUTE_USE_APPLICATION + "," +
		ATTRIBUTE_LOW_ALARM + "," +
		ATTRIBUTE_HIGH_ALARM + "," +
		ATTRIBUTE_CAPACITY + "," +
		ATTRIBUTE_COUNT_UOM + "," +
		ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "," +
		ATTRIBUTE_BILLING_METHOD + "," +
		ATTRIBUTE_BILLING_METHOD_EFFECTIVE_DATE + "," +
		ATTRIBUTE_VIRTUAL_INVENTORY + "," +
		ATTRIBUTE_MEDIAN_LEAD_TIME + ")" +
		" values (" +
		SqlHandler.delimitString(catalogPartInventoryBean.getCatalogId()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getCatPartNo()) + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getInventoryGroup()) + "," +
		catalogPartInventoryBean.getInitialAnnualUseEstimate() + "," +
		catalogPartInventoryBean.getReorderPoint() + "," +
		catalogPartInventoryBean.getStockingLevel() + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getEstablishedStockFlag()) + "," +
		catalogPartInventoryBean.getSharingCutoffQuantity() + "," +
		catalogPartInventoryBean.getReserveQuantity() + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getStockingMethod()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getSourceHubException()) + "," +
		catalogPartInventoryBean.getPartGroupNo() + "," +
		catalogPartInventoryBean.getLookAheadDays() + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getDropShipOverride()) + "," +
		DateHandler.getOracleToDateFunction(catalogPartInventoryBean.getStockingMethodTimeStamp()) + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getQualityControl()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getPosOption()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getStatus()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getLastReorderPointIncreased()) + "," +
		catalogPartInventoryBean.getReorderQuantity() + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getTankName()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getInventoryFacilityId()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getInventoryApplication()) + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getUseFacilityId()) + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getUseApplication()) + "," +
		catalogPartInventoryBean.getLowAlarm() + "," +
		catalogPartInventoryBean.getHighAlarm() + "," +
		catalogPartInventoryBean.getCapacity() + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getCountUom()) + "," +
		SqlHandler.delimitString(catalogPartInventoryBean.getReceiptProcessingMethod()) + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getBillingMethod()) + "," +
		DateHandler.getOracleToDateFunction(catalogPartInventoryBean.getBillingMethodEffectiveDate()) + "," +
	 SqlHandler.delimitString(catalogPartInventoryBean.getVirtualInventory()) + "," +
		catalogPartInventoryBean.getMedianLeadTime() + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(CatalogPartInventoryBean catalogPartInventoryBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(catalogPartInventoryBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(CatalogPartInventoryBean catalogPartInventoryBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_CATALOG_ID + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getCatalogId()) + "," +
		ATTRIBUTE_CAT_PART_NO + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getCatPartNo()) + "," +
		ATTRIBUTE_INVENTORY_GROUP + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getInventoryGroup()) + "," +
		ATTRIBUTE_INITIAL_ANNUAL_USE_ESTIMATE + "=" +
		 StringHandler.nullIfZero(catalogPartInventoryBean.getInitialAnnualUseEstimate()) + "," +
		ATTRIBUTE_REORDER_POINT + "=" +
	 StringHandler.nullIfZero(catalogPartInventoryBean.getReorderPoint()) + "," +
		ATTRIBUTE_STOCKING_LEVEL + "=" +
	 StringHandler.nullIfZero(catalogPartInventoryBean.getStockingLevel()) + "," +
		ATTRIBUTE_ESTABLISHED_STOCK_FLAG + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getEstablishedStockFlag()) + "," +
		ATTRIBUTE_SHARING_CUTOFF_QUANTITY + "=" +
		 StringHandler.nullIfZero(catalogPartInventoryBean.getSharingCutoffQuantity()) + "," +
		ATTRIBUTE_RESERVE_QUANTITY + "=" +
	 StringHandler.nullIfZero(catalogPartInventoryBean.getReserveQuantity()) + "," +
		ATTRIBUTE_STOCKING_METHOD + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getStockingMethod()) + "," +
		ATTRIBUTE_SOURCE_HUB_EXCEPTION + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getSourceHubException()) + "," +
		ATTRIBUTE_PART_GROUP_NO + "=" +
	 StringHandler.nullIfZero(catalogPartInventoryBean.getPartGroupNo()) + "," +
		ATTRIBUTE_LOOK_AHEAD_DAYS + "=" +
	 StringHandler.nullIfZero(catalogPartInventoryBean.getLookAheadDays()) + "," +
		ATTRIBUTE_DROP_SHIP_OVERRIDE + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getDropShipOverride()) + "," +
		ATTRIBUTE_STOCKING_METHOD_TIME_STAMP + "=" +
		 DateHandler.getOracleToDateFunction(catalogPartInventoryBean.getStockingMethodTimeStamp()) + "," +
		ATTRIBUTE_QUALITY_CONTROL + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getQualityControl()) + "," +
		ATTRIBUTE_POS_OPTION + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getPosOption()) + "," +
		ATTRIBUTE_STATUS + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getStatus()) + "," +
		ATTRIBUTE_LAST_REORDER_POINT_INCREASED + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getLastReorderPointIncreased()) + "," +
		ATTRIBUTE_REORDER_QUANTITY + "=" +
	 StringHandler.nullIfZero(catalogPartInventoryBean.getReorderQuantity()) + "," +
		ATTRIBUTE_TANK_NAME + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getTankName()) + "," +
		ATTRIBUTE_INVENTORY_FACILITY_ID + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getInventoryFacilityId()) + "," +
		ATTRIBUTE_INVENTORY_APPLICATION + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getInventoryApplication()) + "," +
		ATTRIBUTE_USE_FACILITY_ID + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getUseFacilityId()) + "," +
		ATTRIBUTE_USE_APPLICATION + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getUseApplication()) + "," +
		ATTRIBUTE_LOW_ALARM + "=" +
		 StringHandler.nullIfZero(catalogPartInventoryBean.getLowAlarm()) + "," +
		ATTRIBUTE_HIGH_ALARM + "=" +
		 StringHandler.nullIfZero(catalogPartInventoryBean.getHighAlarm()) + "," +
		ATTRIBUTE_CAPACITY + "=" +
		 StringHandler.nullIfZero(catalogPartInventoryBean.getCapacity()) + "," +
		ATTRIBUTE_COUNT_UOM + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getCountUom()) + "," +
		ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "=" +
		 SqlHandler.delimitString(catalogPartInventoryBean.getReceiptProcessingMethod()) + "," +
		ATTRIBUTE_BILLING_METHOD + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getBillingMethod()) + "," +
		ATTRIBUTE_BILLING_METHOD_EFFECTIVE_DATE + "=" +
		 DateHandler.getOracleToDateFunction(catalogPartInventoryBean.getBillingMethodEffectiveDate()) + "," +
		ATTRIBUTE_VIRTUAL_INVENTORY + "=" +
	 SqlHandler.delimitString(catalogPartInventoryBean.getVirtualInventory()) + "," +
		ATTRIBUTE_MEDIAN_LEAD_TIME + "=" +
	 StringHandler.nullIfZero(catalogPartInventoryBean.getMedianLeadTime()) + " " +
		"where " + ATTRIBUTE_CATALOG_ID + "=" +
		 catalogPartInventoryBean.getCatalogId();
	 return new SqlManager().update(conn, query);
	}
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

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection catalogPartInventoryBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 CatalogPartInventoryBean catalogPartInventoryBean = new
		CatalogPartInventoryBean();
	 load(dataSetRow, catalogPartInventoryBean);
	 catalogPartInventoryBeanColl.add(catalogPartInventoryBean);
	}

	return catalogPartInventoryBeanColl;
 }

 public BigDecimal selectMedianMrLeadTime(String companyId, BigDecimal prNumber, String lineItem) throws BaseException {

        Connection connection = null;
        BigDecimal c = null;
        try {
         connection = this.getDbManager().getConnection();
         c = selectMedianLeadTime(companyId,prNumber,lineItem, connection);
        }
        finally {
         this.getDbManager().returnConnection(connection);
        }
        return c;
 }

 public BigDecimal selectMedianLeadTime(String companyId, BigDecimal prNumber, String lineItem,Connection conn) throws BaseException {
        BigDecimal result = null;
        String query = "select nvl(cpi.median_mr_lead_time,14) median_mr_lead_time from catalog_part_inventory cpi,request_line_item rli"+
                       " where cpi.company_id = rli.company_id and cpi.catalog_id = rli.catalog_id"+
                       " and cpi.inventory_group = rli.inventory_group and cpi.cat_part_no = rli.fac_part_no"+
                       " and cpi.part_group_no = rli.part_group_no and rli.company_id = '"+companyId+"'"+
                       " and rli.pr_number = "+prNumber.toString()+" and rli.line_item = "+lineItem;


        DataSet dataSet = new SqlManager().select(conn, query);

        Iterator dataIter = dataSet.iterator();

        while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow) dataIter.next();
         CatalogPartInventoryBean catalogPartInventoryBean = new
                CatalogPartInventoryBean();
         load(dataSetRow, catalogPartInventoryBean);
         result = catalogPartInventoryBean.getMedianMrLeadTime();
        }
        return result;
 } //end of method

} //end of class