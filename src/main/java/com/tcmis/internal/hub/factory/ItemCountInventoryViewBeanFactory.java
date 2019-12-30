package com.tcmis.internal.hub.factory;


import java.sql.Connection;
import java.sql.Timestamp;
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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ItemCountInventoryViewBean;


/******************************************************************************
 * CLASSNAME: ItemCountInventoryViewBeanFactory <br>
 * @version: 1.0, Nov 3, 2009 <br>
 *****************************************************************************/


public class ItemCountInventoryViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_STORAGE_AREA = "STORAGE_AREA";
	public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD = "RECEIPT_PROCESSING_METHOD";
	public String ATTRIBUTE_COUNT_UOM = "COUNT_UOM";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_BILLING_METHOD = "BILLING_METHOD";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_PART_ON_HAND = "PART_ON_HAND";
	public String ATTRIBUTE_PART_IN_PURCHASING = "PART_IN_PURCHASING";
	public String ATTRIBUTE_ITEM_ON_HAND = "ITEM_ON_HAND";
	public String ATTRIBUTE_ITEM_IN_PURCHASING = "ITEM_IN_PURCHASING";
	public String ATTRIBUTE_AREA_ID = "AREA_ID";
	public String ATTRIBUTE_PLANT_ID = "PLANT_ID";
	public String ATTRIBUTE_BLDG_ID = "BLDG_ID";
	public String ATTRIBUTE_DEPT_ID = "DEPT_ID";
	public String ATTRIBUTE_PROCESS_ID = "PROCESS_ID";
	public String ATTRIBUTE_PERCENT = "PERCENT";
	public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
	public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC = "RECEIPT_PROCESSING_METHOD_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_TANK_NAME = "TANK_NAME";
	public String ATTRIBUTE_ALLOW_FORCE_BUY = "ALLOW_FORCE_BUY";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_REORDER_QUANTITY = "REORDER_QUANTITY";
	public String ATTRIBUTE_LAST_COUNT_DATE = "LAST_COUNT_DATE";
	public String ATTRIBUTE_ITEM_PACKAGING = "ITEM_PACKAGING";
	public String ATTRIBUTE_PRICING = "PRICING";
	public String ATTRIBUTE_ICMRC_STATUS = "ICMRC_STATUS";
	public String ATTRIBUTE_STATUS = "STATUS";

	//table name
	public String TABLE = "ITEM_COUNT_INVENTORY_VIEW";


	//constructor
	public ItemCountInventoryViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("storageArea")) {
			return ATTRIBUTE_STORAGE_AREA;
		}
		else if(attributeName.equals("issueGeneration")) {
			return ATTRIBUTE_ISSUE_GENERATION;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("receiptProcessingMethod")) {
			return ATTRIBUTE_RECEIPT_PROCESSING_METHOD;
		}
		else if(attributeName.equals("countUom")) {
			return ATTRIBUTE_COUNT_UOM;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("billingMethod")) {
			return ATTRIBUTE_BILLING_METHOD;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("partOnHand")) {
			return ATTRIBUTE_PART_ON_HAND;
		}
		else if(attributeName.equals("partInPurchasing")) {
			return ATTRIBUTE_PART_IN_PURCHASING;
		}
		else if(attributeName.equals("itemOnHand")) {
			return ATTRIBUTE_ITEM_ON_HAND;
		}
		else if(attributeName.equals("itemInPurchasing")) {
			return ATTRIBUTE_ITEM_IN_PURCHASING;
		}
		else if(attributeName.equals("areaId")) {
			return ATTRIBUTE_AREA_ID;
		}
		else if(attributeName.equals("plantId")) {
			return ATTRIBUTE_PLANT_ID;
		}
		else if(attributeName.equals("bldgId")) {
			return ATTRIBUTE_BLDG_ID;
		}
		else if(attributeName.equals("deptId")) {
			return ATTRIBUTE_DEPT_ID;
		}
		else if(attributeName.equals("processId")) {
			return ATTRIBUTE_PROCESS_ID;
		}
		else if(attributeName.equals("percent")) {
			return ATTRIBUTE_PERCENT;
		}
		else if(attributeName.equals("stockingMethod")) {
			return ATTRIBUTE_STOCKING_METHOD;
		}
		else if(attributeName.equals("receiptProcessingMethodDesc")) {
			return ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("tankName")) {
			return ATTRIBUTE_TANK_NAME;
		}
		else if(attributeName.equals("allowForceBuy")) {
			return ATTRIBUTE_ALLOW_FORCE_BUY;
		}
		else if(attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if(attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if(attributeName.equals("reorderQuantity")) {
			return ATTRIBUTE_REORDER_QUANTITY;
		}
		else if(attributeName.equals("lastCountDate")) {
			return ATTRIBUTE_LAST_COUNT_DATE;
		}
		else if(attributeName.equals("itemPackaging")) {
			return ATTRIBUTE_ITEM_PACKAGING;
		}
		else if(attributeName.equals("pricing")) {
			return ATTRIBUTE_PRICING;
		}
		else if(attributeName.equals("icmrcStatus")) {
			return ATTRIBUTE_ICMRC_STATUS;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ItemCountInventoryViewBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(ItemCountInventoryViewBean itemCountInventoryViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + itemCountInventoryViewBean.getInventoryGroup());

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


	public int delete(ItemCountInventoryViewBean itemCountInventoryViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + itemCountInventoryViewBean.getInventoryGroup());

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
	public int insert(ItemCountInventoryViewBean itemCountInventoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(itemCountInventoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ItemCountInventoryViewBean itemCountInventoryViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_STORAGE_AREA + "," +
			ATTRIBUTE_ISSUE_GENERATION + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "," +
			ATTRIBUTE_COUNT_UOM + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_BILLING_METHOD + "," +
			ATTRIBUTE_APPLICATION_DESC + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_PART_ON_HAND + "," +
			ATTRIBUTE_PART_IN_PURCHASING + "," +
			ATTRIBUTE_ITEM_ON_HAND + "," +
			ATTRIBUTE_ITEM_IN_PURCHASING + "," +
			ATTRIBUTE_AREA_ID + "," +
			ATTRIBUTE_PLANT_ID + "," +
			ATTRIBUTE_BLDG_ID + "," +
			ATTRIBUTE_DEPT_ID + "," +
			ATTRIBUTE_PROCESS_ID + "," +
			ATTRIBUTE_PERCENT + "," +
			ATTRIBUTE_STOCKING_METHOD + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_TANK_NAME + "," +
			ATTRIBUTE_ALLOW_FORCE_BUY + "," +
			ATTRIBUTE_REORDER_POINT + "," +
			ATTRIBUTE_STOCKING_LEVEL + "," +
			ATTRIBUTE_REORDER_QUANTITY + "," +
			ATTRIBUTE_LAST_COUNT_DATE + "," +
			ATTRIBUTE_ITEM_PACKAGING + "," +
			ATTRIBUTE_PRICING + "," +
			ATTRIBUTE_ICMRC_STATUS + "," +
			ATTRIBUTE_STATUS + ")" +
			" values (" +
			SqlHandler.delimitString(itemCountInventoryViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getStorageArea()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getIssueGeneration()) + "," +
			itemCountInventoryViewBean.getItemId() + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getCatPartNo()) + "," +
			itemCountInventoryViewBean.getPartGroupNo() + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getReceiptProcessingMethod()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getCountUom()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getItemType()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getBillingMethod()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getApplicationDesc()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getCatalogCompanyId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getApplication()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getPartOnHand()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getPartInPurchasing()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getItemOnHand()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getItemInPurchasing()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getAreaId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getPlantId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getBldgId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getDeptId()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getProcessId()) + "," +
			itemCountInventoryViewBean.getPercent() + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getStockingMethod()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getReceiptProcessingMethodDesc()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getTankName()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getAllowForceBuy()) + "," +
			itemCountInventoryViewBean.getReorderPoint() + "," +
			itemCountInventoryViewBean.getStockingLevel() + "," +
			itemCountInventoryViewBean.getReorderQuantity() + "," +
			DateHandler.getOracleToDateFunction(itemCountInventoryViewBean.getLastCountDate()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getItemPackaging()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getPricing()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getIcmrcStatus()) + "," +
			SqlHandler.delimitString(itemCountInventoryViewBean.getStatus()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ItemCountInventoryViewBean itemCountInventoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(itemCountInventoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ItemCountInventoryViewBean itemCountInventoryViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_STORAGE_AREA + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getStorageArea()) + "," +
			ATTRIBUTE_ISSUE_GENERATION + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getIssueGeneration()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(itemCountInventoryViewBean.getItemId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" +
				StringHandler.nullIfZero(itemCountInventoryViewBean.getPartGroupNo()) + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getReceiptProcessingMethod()) + "," +
			ATTRIBUTE_COUNT_UOM + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getCountUom()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getItemType()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getItemDesc()) + "," +
			ATTRIBUTE_PACKAGING + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getPackaging()) + "," +
			ATTRIBUTE_BILLING_METHOD + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getBillingMethod()) + "," +
			ATTRIBUTE_APPLICATION_DESC + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getApplicationDesc()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getCatalogCompanyId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getApplication()) + "," +
			ATTRIBUTE_PART_ON_HAND + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getPartOnHand()) + "," +
			ATTRIBUTE_PART_IN_PURCHASING + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getPartInPurchasing()) + "," +
			ATTRIBUTE_ITEM_ON_HAND + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getItemOnHand()) + "," +
			ATTRIBUTE_ITEM_IN_PURCHASING + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getItemInPurchasing()) + "," +
			ATTRIBUTE_AREA_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getAreaId()) + "," +
			ATTRIBUTE_PLANT_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getPlantId()) + "," +
			ATTRIBUTE_BLDG_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getBldgId()) + "," +
			ATTRIBUTE_DEPT_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getDeptId()) + "," +
			ATTRIBUTE_PROCESS_ID + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getProcessId()) + "," +
			ATTRIBUTE_PERCENT + "=" +
				StringHandler.nullIfZero(itemCountInventoryViewBean.getPercent()) + "," +
			ATTRIBUTE_STOCKING_METHOD + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getStockingMethod()) + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getReceiptProcessingMethodDesc()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_TANK_NAME + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getTankName()) + "," +
			ATTRIBUTE_ALLOW_FORCE_BUY + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getAllowForceBuy()) + "," +
			ATTRIBUTE_REORDER_POINT + "=" +
				StringHandler.nullIfZero(itemCountInventoryViewBean.getReorderPoint()) + "," +
			ATTRIBUTE_STOCKING_LEVEL + "=" +
				StringHandler.nullIfZero(itemCountInventoryViewBean.getStockingLevel()) + "," +
			ATTRIBUTE_REORDER_QUANTITY + "=" +
				StringHandler.nullIfZero(itemCountInventoryViewBean.getReorderQuantity()) + "," +
			ATTRIBUTE_LAST_COUNT_DATE + "=" +
				DateHandler.getOracleToDateFunction(itemCountInventoryViewBean.getLastCountDate()) + "," +
			ATTRIBUTE_ITEM_PACKAGING + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getItemPackaging()) + "," +
			ATTRIBUTE_PRICING + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getPricing()) + "," +
			ATTRIBUTE_ICMRC_STATUS + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getIcmrcStatus()) + "," +
			ATTRIBUTE_STATUS + "=" +
				SqlHandler.delimitString(itemCountInventoryViewBean.getStatus()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				itemCountInventoryViewBean.getInventoryGroup();

		return new SqlManager().update(conn, query);
	}
	 */
	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException
	{
		Connection connection = null;
		Collection c = null;
		try
		{
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException
	{
		Collection itemCountInventoryViewBeanColl = new Vector();
		StringBuilder queryBuffer = new StringBuilder("select * from ");
		queryBuffer.append(TABLE);
		queryBuffer.append(" ");
		queryBuffer.append( getWhereClause( criteria ) );
		if (sortCriteria != null)
		{
			queryBuffer.append( getOrderByClause( sortCriteria ) );
		}
		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemCountInventoryViewBean itemCountInventoryViewBean = new ItemCountInventoryViewBean();
			load(dataSetRow, itemCountInventoryViewBean);
			itemCountInventoryViewBeanColl.add(itemCountInventoryViewBean);
		}
		return itemCountInventoryViewBeanColl;
	}

	public void updateItemIcmrcStatus(ItemCountInventoryViewBean updateBean) throws BaseException
	{
		Collection inArgs = new Vector(3);
		inArgs.add( updateBean.getItemId() );
		inArgs.add(updateBean.getInventoryGroup() );
		inArgs.add(updateBean.getIcmrcStatus() );

		this.getDbManager().doProcedure("PR_ITEM_COUNT_MR_CREATE_UPDATE", inArgs);
	}

	public Collection forceBuyItemInventory(ItemCountInventoryViewBean updateBean, String personnelId) throws BaseException
	{
		Collection inArgs = new Vector(11);
		inArgs.add( updateBean.getItemId() );   				// position 1
		inArgs.add(updateBean.getInventoryGroup() );			// position 2
		inArgs.add(updateBean.getReplenishQty() );				// position 3
		inArgs.add(updateBean.getCatalogId() );					// position 4
		inArgs.add(updateBean.getCatPartNo() );					// position 5
		inArgs.add(updateBean.getPartGroupNo() );				// position 6
		inArgs.add(updateBean.getFacilityId() );				// position 7
		inArgs.add(updateBean.getApplication() );				// position 8
		inArgs.add(updateBean.getCompanyId() );					// position 9
		inArgs.add(personnelId );								// position 10
		inArgs.add( new Timestamp(updateBean.getNeedDate().getTime() ) );					// position 11

		Collection outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Collection optionInArgs = new Vector();
		/*		if(updateBean.getComment() == null) updateBean.setComment("");  */
		optionInArgs.add(updateBean.getComment());
		optionInArgs.add(updateBean.getMrNumber());
		optionInArgs.add(updateBean.getMrLineItem());
		optionInArgs.add(updateBean.getCatalogCompanyId());
		optionInArgs.add(updateBean.getRemainingShelfLifePercent());
		
		if (log.isDebugEnabled()) {
			log.debug("p_force_buy_item_inventory"+inArgs+optionInArgs);
		}
		Collection result = this.getDbManager().doProcedure("p_force_buy_item_inventory", inArgs, outArgs, optionInArgs);

		return result;
	}

	/*public  Timestamp getTimestampFromDateString(String dateString, java.util.Locale locale)
	{
		Timestamp timestamp = null;
		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat( "dd-MMM-yyyy", (java.util.Locale) locale );
			Date date = dateFormat.parse( dateString );
			timestamp = new Timestamp( date.getTime() );
		}
		catch (ParseException e)
		{
			log.debug("date parse exception on date string: [" + dateString + "]; ");
		}
		return timestamp;
	}*/

}