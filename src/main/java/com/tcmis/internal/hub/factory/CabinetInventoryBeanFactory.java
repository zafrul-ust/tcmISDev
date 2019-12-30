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
import com.tcmis.internal.hub.beans.CabinetInventoryBean;
import com.tcmis.internal.hub.beans.CabinetInventoryInputBean;


/******************************************************************************
 * CLASSNAME: CabinetInventoryBeanFactory <br>
 * @version: 1.0, Sep 26, 2007 <br>
 *****************************************************************************/


public class CabinetInventoryBeanFactory extends BaseBeanFactory
{

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_QTY_AVAILABLE_AFTER_ALLOC = "QTY_AVAILABLE_AFTER_ALLOC";
	public String ATTRIBUTE_UNIT_COST = "UNIT_COST";
	public String ATTRIBUTE_CABINET_ID = "CABINET_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_ORDERING_APPLICATION = "ORDERING_APPLICATION";
	public String ATTRIBUTE_USE_APPLICATION = "USE_APPLICATION";
	public String ATTRIBUTE_CABINET_NAME = "CABINET_NAME";
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_COUNT_DATETIME = "COUNT_DATETIME";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COUNTED_BY_PERSONNEL_ID = "COUNTED_BY_PERSONNEL_ID";
	public String ATTRIBUTE_COUNT_QUANTITY = "COUNT_QUANTITY";
	public String ATTRIBUTE_DATE_PROCESSED = "DATE_PROCESSED";
	public String ATTRIBUTE_MAX_COUNT_DATETIME = "MAX_COUNT_DATETIME";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_COUNTED_BY_NAME = "COUNTED_BY_NAME";
	public String ATTRIBUTE_QTY_ISSUED_AFTER_COUNT = "QTY_ISSUED_AFTER_COUNT";
	public String ATTRIBUTE_QC_DOC = "QC_DOC";
	public String ATTRIBUTE_TOTAL_QUANTITY = "TOTAL_QUANTITY";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_LEAD_TIME_DAYS = "LEAD_TIME_DAYS";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_COUNT_TYPE = "COUNT_TYPE";

	//table name
	public String TABLE = "NAWAZ_BEAN_CREATOR";


	//constructor
	public CabinetInventoryBeanFactory(DbManager dbManager)
	{
		super(dbManager);
	}

	@Override
	public String getColumnName(String attributeName)
	{
		if(attributeName.equals("qtyAvailableAfterAlloc")) {
			return ATTRIBUTE_QTY_AVAILABLE_AFTER_ALLOC;
		}
		else if(attributeName.equals("unitCost")) {
			return ATTRIBUTE_UNIT_COST;
		}
		else if(attributeName.equals("cabinetId")) {
			return ATTRIBUTE_CABINET_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("orderingApplication")) {
			return ATTRIBUTE_ORDERING_APPLICATION;
		}
		else if(attributeName.equals("useApplication")) {
			return ATTRIBUTE_USE_APPLICATION;
		}
		else if(attributeName.equals("cabinetName")) {
			return ATTRIBUTE_CABINET_NAME;
		}
		else if(attributeName.equals("binId")) {
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
		else if(attributeName.equals("countedByPersonnelId")) {
			return ATTRIBUTE_COUNTED_BY_PERSONNEL_ID;
		}
		else if(attributeName.equals("countQuantity")) {
			return ATTRIBUTE_COUNT_QUANTITY;
		}
		else if(attributeName.equals("dateProcessed")) {
			return ATTRIBUTE_DATE_PROCESSED;
		}
		else if(attributeName.equals("maxCountDatetime")) {
			return ATTRIBUTE_MAX_COUNT_DATETIME;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("countedByName")) {
			return ATTRIBUTE_COUNTED_BY_NAME;
		}
		else if(attributeName.equals("qtyIssuedAfterCount")) {
			return ATTRIBUTE_QTY_ISSUED_AFTER_COUNT;
		}
		else if(attributeName.equals("qcDoc")) {
			return ATTRIBUTE_QC_DOC;
		}
		else if(attributeName.equals("totalQuantity")) {
			return ATTRIBUTE_TOTAL_QUANTITY;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if(attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if(attributeName.equals("leadTimeDays")) {
			return ATTRIBUTE_LEAD_TIME_DAYS;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("countType")) {
			return ATTRIBUTE_COUNT_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName)
	{
		return super.getType(attributeName, CabinetInventoryBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(CabinetInventoryBean cabinetInventoryBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("qtyAvailableAfterAlloc", "SearchCriterion.EQUALS",
			"" + cabinetInventoryBean.getQtyAvailableAfterAlloc());

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


	public int delete(CabinetInventoryBean cabinetInventoryBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("qtyAvailableAfterAlloc", "SearchCriterion.EQUALS",
			"" + cabinetInventoryBean.getQtyAvailableAfterAlloc());

		return delete(criteria, conn);
	}


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

	 */
	//you need to verify the primary key(s) before uncommenting this
	/*
	//insert
	public int insert(CabinetInventoryBean cabinetInventoryBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(cabinetInventoryBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CabinetInventoryBean cabinetInventoryBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_QTY_AVAILABLE_AFTER_ALLOC + "," +
			ATTRIBUTE_UNIT_COST + "," +
			ATTRIBUTE_CABINET_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_ORDERING_APPLICATION + "," +
			ATTRIBUTE_USE_APPLICATION + "," +
			ATTRIBUTE_CABINET_NAME + "," +
			ATTRIBUTE_BIN_ID + "," +
			ATTRIBUTE_COUNT_DATETIME + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_COUNTED_BY_PERSONNEL_ID + "," +
			ATTRIBUTE_COUNT_QUANTITY + "," +
			ATTRIBUTE_DATE_PROCESSED + "," +
			ATTRIBUTE_MAX_COUNT_DATETIME + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_COUNTED_BY_NAME + "," +
			ATTRIBUTE_QTY_ISSUED_AFTER_COUNT + "," +
			ATTRIBUTE_QC_DOC + "," +
			ATTRIBUTE_TOTAL_QUANTITY + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_REORDER_POINT + "," +
			ATTRIBUTE_STOCKING_LEVEL + "," +
			ATTRIBUTE_LEAD_TIME_DAYS + ")" +
			" values (" +
			SqlHandler.delimitString(cabinetInventoryBean.getQtyAvailableAfterAlloc()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getUnitCost()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCabinetId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getFacilityId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getOrderingApplication()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getUseApplication()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCabinetName()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getBinId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCountDatetime()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getReceiptId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCompanyId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCountedByPersonnelId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCountQuantity()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getDateProcessed()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getMaxCountDatetime()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getHub()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getMfgLot()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getExpireDate()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getItemId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getItemDesc()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCountedByName()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getQtyIssuedAfterCount()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getQcDoc()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getTotalQuantity()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCatalogId()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getReorderPoint()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getStockingLevel()) + "," +
			SqlHandler.delimitString(cabinetInventoryBean.getLeadTimeDays()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CabinetInventoryBean cabinetInventoryBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(cabinetInventoryBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CabinetInventoryBean cabinetInventoryBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_QTY_AVAILABLE_AFTER_ALLOC + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getQtyAvailableAfterAlloc()) + "," +
			ATTRIBUTE_UNIT_COST + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getUnitCost()) + "," +
			ATTRIBUTE_CABINET_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCabinetId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getFacilityId()) + "," +
			ATTRIBUTE_ORDERING_APPLICATION + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getOrderingApplication()) + "," +
			ATTRIBUTE_USE_APPLICATION + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getUseApplication()) + "," +
			ATTRIBUTE_CABINET_NAME + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCabinetName()) + "," +
			ATTRIBUTE_BIN_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getBinId()) + "," +
			ATTRIBUTE_COUNT_DATETIME + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCountDatetime()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getReceiptId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCompanyId()) + "," +
			ATTRIBUTE_COUNTED_BY_PERSONNEL_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCountedByPersonnelId()) + "," +
			ATTRIBUTE_COUNT_QUANTITY + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCountQuantity()) + "," +
			ATTRIBUTE_DATE_PROCESSED + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getDateProcessed()) + "," +
			ATTRIBUTE_MAX_COUNT_DATETIME + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getMaxCountDatetime()) + "," +
			ATTRIBUTE_HUB + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getHub()) + "," +
			ATTRIBUTE_MFG_LOT + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getMfgLot()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getExpireDate()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getItemDesc()) + "," +
			ATTRIBUTE_COUNTED_BY_NAME + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCountedByName()) + "," +
			ATTRIBUTE_QTY_ISSUED_AFTER_COUNT + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getQtyIssuedAfterCount()) + "," +
			ATTRIBUTE_QC_DOC + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getQcDoc()) + "," +
			ATTRIBUTE_TOTAL_QUANTITY + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getTotalQuantity()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getCatPartNo()) + "," +
			ATTRIBUTE_REORDER_POINT + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getReorderPoint()) + "," +
			ATTRIBUTE_STOCKING_LEVEL + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getStockingLevel()) + "," +
			ATTRIBUTE_LEAD_TIME_DAYS + "=" +
				SqlHandler.delimitString(cabinetInventoryBean.getLeadTimeDays()) + " " +
			"where " + ATTRIBUTE_QTY_AVAILABLE_AFTER_ALLOC + "=" +
				cabinetInventoryBean.getQtyAvailableAfterAlloc();

		return new SqlManager().update(conn, query);
	}
	 */
	/*
	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

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
		throws BaseException {

		Collection cabinetInventoryBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CabinetInventoryBean cabinetInventoryBean = new CabinetInventoryBean();
			load(dataSetRow, cabinetInventoryBean);
			cabinetInventoryBeanColl.add(cabinetInventoryBean);
		}

		return cabinetInventoryBeanColl;
	}
	 */
	//select from procedure
	public Collection getCabinetInventoryBeanCollection (CabinetInventoryInputBean bean)
	throws BaseException
	{
		Collection cabinetInventoryBeanColl = new Vector();

		Connection connection = this.getDbManager().getConnection();

		Collection cin = new Vector();
		cin.add(bean.getCompanyId());				// position 1

		if (bean.getFacilityId() != null && bean.getFacilityId().trim().length() > 0
			 && !"All".equalsIgnoreCase(bean.getFacilityId())) {
			cin.add(new String(bean.getFacilityId()));	// position 2
		}else{
			cin.add("");								// position 2
		}

		if (bean.getApplication() != null && bean.getApplication().length() > 0 &&
				!"All".equalsIgnoreCase(bean.getApplication())){
			cin.add(bean.getApplication());	// position 3
		}else{
			if (bean.getUseApplication()!= null && bean.getUseApplication().length() > 0 && !"All".equalsIgnoreCase(bean.getUseApplication())) {
				cin.add(bean.getUseApplication());	// position 3
			}else {
				cin.add("");								// position 3
			}
		}

		String cabinetIdCsvString = getCsvStringFromArray ( bean.getCabinetIdArray() );

		cin.add(cabinetIdCsvString);			// position 4

		if (bean.getSearchArgument() != null && bean.getSearchArgument().length() > 0)
		{

			if (bean.getMatchingMode().equalsIgnoreCase("Contains"))
			{
				cin.add("Y");			// position 5
			}
			else
			{
				cin.add("");	// position 5
			}
			if (bean.getSearchField() != null && bean.getSearchField().length() > 0)
			{
				cin.add(new String(bean.getSearchField()));		// position 6
			}
			else
			{
				cin.add("");						// position 6
			}

			cin.add(new String(bean.getSearchArgument()));	// position 7

		}
		else
		{
			cin.add("");	// position 5
			cin.add("");	// position 6
			cin.add("");	// position 7
		}

		if (bean.getExpireInFrom() != null )
		{
			cin.add( bean.getExpireInFrom() );	// position 8
		}
		else
		{
			cin.add("");					// position 8
		}

		if (bean.getExpireInTo() != null )
		{
			cin.add(bean.getExpireInTo());		// position 9
		}
		else
		{
			cin.add("");					// position 9
		}

		if (bean.getSortBy() != null && bean.getSortBy().length() > 0)
		{
			cin.add(new String(bean.getSortBy())); 	// position 10
		}
		else
		{
			cin.add("");					// position 10
		}

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection result = null;
		SqlManager sqlManager = new SqlManager();
		try
		{
			log.debug(cin);
			//TODO: Procedure passes COUNT_DATETIME and EXPIRE_DATE as strings. Please update the procedure. Then we can update CabinetInventoryBean to make the type as Date.
			result = sqlManager.doProcedure(connection, "PR_CABINET_INV_COUNT_REPORT",	cin, cout);
		}
		finally
		{
		}

		Iterator resultIterator = result.iterator();
		String searchQuery = "";
		while (resultIterator.hasNext())
		{
			searchQuery = (String) resultIterator.next(); ;
		}
		//log.debug("searchQuery = [" + searchQuery + "]; ");
		try
		{
			connection = this.getDbManager().getConnection();
			DataSet dataSet = new SqlManager().select(connection, searchQuery);
			Iterator dataIter = dataSet.iterator();
			boolean positiveQ = ( bean.getPositiveQ() == null || !bean.getPositiveQ().equals("Yes") );
			while (dataIter.hasNext())
			{
				DataSetRow dataSetRow = (DataSetRow) dataIter.next();
				CabinetInventoryBean cabinetInventoryBean = new
				CabinetInventoryBean();
				load(dataSetRow, cabinetInventoryBean);
				String totalQ = cabinetInventoryBean.getTotalQuantity().toString();
				if(  positiveQ || !totalQ.equals("0") )
					cabinetInventoryBeanColl.add(cabinetInventoryBean);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		/*
		for (int i = 0; i < cin.size(); i++ )
		{
			String valueString;
			if ( ( (Vector) cin).elementAt( i ) == null)
			{
				valueString = "(null)";
			}
			else
			{
				valueString = ( (Vector) cin).elementAt( i ).toString();
			}

			log.debug("cin." + (i + 1) + ".toString() = [" + valueString + "];");
		}
		 */
		return cabinetInventoryBeanColl;
	}

	public static String getCsvStringFromArray (String[] stringArray)
	{
		StringBuilder csvStringBuilder = new StringBuilder();
		if(stringArray == null || stringArray.length == 0)
			return "";
		for(int i = 0; i < stringArray.length; i++)
			if( stringArray[ i ].equals("") )
				return "";
		for(int i = 0; i < stringArray.length; i++) 	{
			if (i > 0)	{
				csvStringBuilder.append(",");
			}
			csvStringBuilder.append("'");
			csvStringBuilder.append(stringArray[ i ]);
			csvStringBuilder.append("'");
		}
		return csvStringBuilder.toString();
	}
}
