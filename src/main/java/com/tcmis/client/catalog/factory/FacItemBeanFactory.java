package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.FacItemBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: FacItemBeanFactory <br>
 * @version: 1.0, Aug 17, 2005 <br>
 *****************************************************************************/

public class FacItemBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_SPEC_ID = "SPEC_ID";
 public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
 public String ATTRIBUTE_STOCKED = "STOCKED";
 public String ATTRIBUTE_SPEC_LIBRARY = "SPEC_LIBRARY";
 public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
 public String ATTRIBUTE_MINIMUM_BUY = "MINIMUM_BUY";
 public String ATTRIBUTE_SHELF_LIFE = "SHELF_LIFE";
 public String ATTRIBUTE_SHELF_LIFE_UNIT = "SHELF_LIFE_UNIT";
 public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
 public String ATTRIBUTE_C_OF_C = "C_OF_C";
 public String ATTRIBUTE_C_OF_A = "C_OF_A";
 public String ATTRIBUTE_ANNUAL_USAGE = "ANNUAL_USAGE";
 public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
 public String ATTRIBUTE_SHELF_LIFE_RELAXED = "SHELF_LIFE_RELAXED";
 public String ATTRIBUTE_SHIP_CHRG = "SHIP_CHRG";
 public String ATTRIBUTE_TEST_CHRG = "TEST_CHRG";
 public String ATTRIBUTE_MIN_BUY_AMT = "MIN_BUY_AMT";
 public String ATTRIBUTE_MISC_CHRG = "MISC_CHRG";
 public String ATTRIBUTE_CYL_RENT_CHRG = "CYL_RENT_CHRG";
 public String ATTRIBUTE_SETUP_CHRG = "SETUP_CHRG";
 public String ATTRIBUTE_CERT_CHRG = "CERT_CHRG";
 public String ATTRIBUTE_EXPED_CHRG = "EXPED_CHRG";
 public String ATTRIBUTE_CUT_CHRG = "CUT_CHRG";
 public String ATTRIBUTE_DRY_ICE_CHRG = "DRY_ICE_CHRG";
 public String ATTRIBUTE_HAZMAT_CHRG = "HAZMAT_CHRG";
 public String ATTRIBUTE_PACK_CHRG = "PACK_CHRG";
 public String ATTRIBUTE_ENVIRO_CHRG = "ENVIRO_CHRG";
 public String ATTRIBUTE_REG_CHRG = "REG_CHRG";
 public String ATTRIBUTE_ORDER_POINT = "ORDER_POINT";
 public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
 public String ATTRIBUTE_ESTABLISHED_STOCK = "ESTABLISHED_STOCK";
 public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
 public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
 public String ATTRIBUTE_BILLING_CATEGORY = "BILLING_CATEGORY";
 public String ATTRIBUTE_TOTAL_RECERTS_ALLOWED = "TOTAL_RECERTS_ALLOWED";
 public String ATTRIBUTE_SHELF_LIFE_EXTENSION = "SHELF_LIFE_EXTENSION";
 public String ATTRIBUTE_CATALOG_NOTES = "CATALOG_NOTES";
 public String ATTRIBUTE_SEARCH = "SEARCH";
 public String ATTRIBUTE_CONSUMABLE = "CONSUMABLE";
 public String ATTRIBUTE_CATEGORY = "CATEGORY";
 public String ATTRIBUTE_LABEL_SPEC = "LABEL_SPEC";
 public String ATTRIBUTE_ALTERNATE_NAME = "ALTERNATE_NAME";
 public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH =
	"UNIT_OF_SALE_QUANTITY_PER_EACH";
 public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
 public String ATTRIBUTE_PART_HAZARD_IMAGE = "PART_HAZARD_IMAGE";
 public String ATTRIBUTE_INCOMING_TESTING = "INCOMING_TESTING";
 public String ATTRIBUTE_RECERT_INSTRUCTIONS = "RECERT_INSTRUCTIONS";

 //table name
 public String TABLE = "FAC_ITEM";

 //constructor
 public FacItemBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("specId")) {
	 return ATTRIBUTE_SPEC_ID;
	}
	else if (attributeName.equals("facPartNo")) {
	 return ATTRIBUTE_FAC_PART_NO;
	}
	else if (attributeName.equals("stocked")) {
	 return ATTRIBUTE_STOCKED;
	}
	else if (attributeName.equals("specLibrary")) {
	 return ATTRIBUTE_SPEC_LIBRARY;
	}
	else if (attributeName.equals("unitPrice")) {
	 return ATTRIBUTE_UNIT_PRICE;
	}
	else if (attributeName.equals("minimumBuy")) {
	 return ATTRIBUTE_MINIMUM_BUY;
	}
	else if (attributeName.equals("shelfLife")) {
	 return ATTRIBUTE_SHELF_LIFE;
	}
	else if (attributeName.equals("shelfLifeUnit")) {
	 return ATTRIBUTE_SHELF_LIFE_UNIT;
	}
	else if (attributeName.equals("shelfLifeBasis")) {
	 return ATTRIBUTE_SHELF_LIFE_BASIS;
	}
	else if (attributeName.equals("cOfCc")) {
	 return ATTRIBUTE_C_OF_C;
	}
	else if (attributeName.equals("cOfAa")) {
	 return ATTRIBUTE_C_OF_A;
	}
	else if (attributeName.equals("annualUsage")) {
	 return ATTRIBUTE_ANNUAL_USAGE;
	}
	else if (attributeName.equals("storageTemp")) {
	 return ATTRIBUTE_STORAGE_TEMP;
	}
	else if (attributeName.equals("shelfLifeRelaxed")) {
	 return ATTRIBUTE_SHELF_LIFE_RELAXED;
	}
	else if (attributeName.equals("shipChrg")) {
	 return ATTRIBUTE_SHIP_CHRG;
	}
	else if (attributeName.equals("testChrg")) {
	 return ATTRIBUTE_TEST_CHRG;
	}
	else if (attributeName.equals("minBuyAmt")) {
	 return ATTRIBUTE_MIN_BUY_AMT;
	}
	else if (attributeName.equals("miscChrg")) {
	 return ATTRIBUTE_MISC_CHRG;
	}
	else if (attributeName.equals("cylRentChrg")) {
	 return ATTRIBUTE_CYL_RENT_CHRG;
	}
	else if (attributeName.equals("setupChrg")) {
	 return ATTRIBUTE_SETUP_CHRG;
	}
	else if (attributeName.equals("certChrg")) {
	 return ATTRIBUTE_CERT_CHRG;
	}
	else if (attributeName.equals("expedChrg")) {
	 return ATTRIBUTE_EXPED_CHRG;
	}
	else if (attributeName.equals("cutChrg")) {
	 return ATTRIBUTE_CUT_CHRG;
	}
	else if (attributeName.equals("dryIceChrg")) {
	 return ATTRIBUTE_DRY_ICE_CHRG;
	}
	else if (attributeName.equals("hazmatChrg")) {
	 return ATTRIBUTE_HAZMAT_CHRG;
	}
	else if (attributeName.equals("packChrg")) {
	 return ATTRIBUTE_PACK_CHRG;
	}
	else if (attributeName.equals("enviroChrg")) {
	 return ATTRIBUTE_ENVIRO_CHRG;
	}
	else if (attributeName.equals("regChrg")) {
	 return ATTRIBUTE_REG_CHRG;
	}
	else if (attributeName.equals("orderPoint")) {
	 return ATTRIBUTE_ORDER_POINT;
	}
	else if (attributeName.equals("stockingLevel")) {
	 return ATTRIBUTE_STOCKING_LEVEL;
	}
	else if (attributeName.equals("establishedStock")) {
	 return ATTRIBUTE_ESTABLISHED_STOCK;
	}
	else if (attributeName.equals("unitOfSale")) {
	 return ATTRIBUTE_UNIT_OF_SALE;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("partDescription")) {
	 return ATTRIBUTE_PART_DESCRIPTION;
	}
	else if (attributeName.equals("catalogPrice")) {
	 return ATTRIBUTE_CATALOG_PRICE;
	}
	else if (attributeName.equals("billingCategory")) {
	 return ATTRIBUTE_BILLING_CATEGORY;
	}
	else if (attributeName.equals("totalRecertsAllowed")) {
	 return ATTRIBUTE_TOTAL_RECERTS_ALLOWED;
	}
	else if (attributeName.equals("shelfLifeExtension")) {
	 return ATTRIBUTE_SHELF_LIFE_EXTENSION;
	}
	else if (attributeName.equals("catalogNotes")) {
	 return ATTRIBUTE_CATALOG_NOTES;
	}
	else if (attributeName.equals("search")) {
	 return ATTRIBUTE_SEARCH;
	}
	else if (attributeName.equals("consumable")) {
	 return ATTRIBUTE_CONSUMABLE;
	}
	else if (attributeName.equals("category")) {
	 return ATTRIBUTE_CATEGORY;
	}
	else if (attributeName.equals("labelSpec")) {
	 return ATTRIBUTE_LABEL_SPEC;
	}
	else if (attributeName.equals("alternateName")) {
	 return ATTRIBUTE_ALTERNATE_NAME;
	}
	else if (attributeName.equals("unitOfSaleQuantityPerEach")) {
	 return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
	}
	else if (attributeName.equals("partShortName")) {
	 return ATTRIBUTE_PART_SHORT_NAME;
	}
	else if (attributeName.equals("partHazardImage")) {
	 return ATTRIBUTE_PART_HAZARD_IMAGE;
	}
	else if (attributeName.equals("incomingTesting")) {
		 return ATTRIBUTE_INCOMING_TESTING;
		}
	else if (attributeName.equals("recertInstructions")) {
		 return ATTRIBUTE_RECERT_INSTRUCTIONS;
		}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, FacItemBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(FacItemBean facItemBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
		"" + facItemBean.getFacilityId());
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
	public int delete(FacItemBean facItemBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
		"" + facItemBean.getFacilityId());
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
	public int insert(FacItemBean facItemBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(facItemBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(FacItemBean facItemBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_FACILITY_ID + "," +
		ATTRIBUTE_SPEC_ID + "," +
		ATTRIBUTE_FAC_PART_NO + "," +
		ATTRIBUTE_STOCKED + "," +
		ATTRIBUTE_SPEC_LIBRARY + "," +
		ATTRIBUTE_UNIT_PRICE + "," +
		ATTRIBUTE_MINIMUM_BUY + "," +
		ATTRIBUTE_SHELF_LIFE + "," +
		ATTRIBUTE_SHELF_LIFE_UNIT + "," +
		ATTRIBUTE_SHELF_LIFE_BASIS + "," +
		ATTRIBUTE_C_OF_C + "," +
		ATTRIBUTE_C_OF_A + "," +
		ATTRIBUTE_ANNUAL_USAGE + "," +
		ATTRIBUTE_STORAGE_TEMP + "," +
		ATTRIBUTE_SHELF_LIFE_RELAXED + "," +
		ATTRIBUTE_SHIP_CHRG + "," +
		ATTRIBUTE_TEST_CHRG + "," +
		ATTRIBUTE_MIN_BUY_AMT + "," +
		ATTRIBUTE_MISC_CHRG + "," +
		ATTRIBUTE_CYL_RENT_CHRG + "," +
		ATTRIBUTE_SETUP_CHRG + "," +
		ATTRIBUTE_CERT_CHRG + "," +
		ATTRIBUTE_EXPED_CHRG + "," +
		ATTRIBUTE_CUT_CHRG + "," +
		ATTRIBUTE_DRY_ICE_CHRG + "," +
		ATTRIBUTE_HAZMAT_CHRG + "," +
		ATTRIBUTE_PACK_CHRG + "," +
		ATTRIBUTE_ENVIRO_CHRG + "," +
		ATTRIBUTE_REG_CHRG + "," +
		ATTRIBUTE_ORDER_POINT + "," +
		ATTRIBUTE_STOCKING_LEVEL + "," +
		ATTRIBUTE_ESTABLISHED_STOCK + "," +
		ATTRIBUTE_UNIT_OF_SALE + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_PART_DESCRIPTION + "," +
		ATTRIBUTE_CATALOG_PRICE + "," +
		ATTRIBUTE_BILLING_CATEGORY + "," +
		ATTRIBUTE_TOTAL_RECERTS_ALLOWED + "," +
		ATTRIBUTE_SHELF_LIFE_EXTENSION + "," +
		ATTRIBUTE_CATALOG_NOTES + "," +
		ATTRIBUTE_SEARCH + "," +
		ATTRIBUTE_CONSUMABLE + "," +
		ATTRIBUTE_CATEGORY + "," +
		ATTRIBUTE_LABEL_SPEC + "," +
		ATTRIBUTE_ALTERNATE_NAME + "," +
		ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "," +
		ATTRIBUTE_PART_SHORT_NAME + "," +
		ATTRIBUTE_PART_HAZARD_IMAGE + ")" +
	values (
		SqlHandler.delimitString(facItemBean.getFacilityId()) + "," +
		SqlHandler.delimitString(facItemBean.getSpecId()) + "," +
		SqlHandler.delimitString(facItemBean.getFacPartNo()) + "," +
		SqlHandler.delimitString(facItemBean.getStocked()) + "," +
		SqlHandler.delimitString(facItemBean.getSpecLibrary()) + "," +
		StringHandler.nullIfZero(facItemBean.getUnitPrice()) + "," +
		StringHandler.nullIfZero(facItemBean.getMinimumBuy()) + "," +
		StringHandler.nullIfZero(facItemBean.getShelfLife()) + "," +
		SqlHandler.delimitString(facItemBean.getShelfLifeUnit()) + "," +
		SqlHandler.delimitString(facItemBean.getShelfLifeBasis()) + "," +
		SqlHandler.delimitString(facItemBean.getCOfC()) + "," +
		SqlHandler.delimitString(facItemBean.getCOfA()) + "," +
		StringHandler.nullIfZero(facItemBean.getAnnualUsage()) + "," +
		SqlHandler.delimitString(facItemBean.getStorageTemp()) + "," +
		SqlHandler.delimitString(facItemBean.getShelfLifeRelaxed()) + "," +
		StringHandler.nullIfZero(facItemBean.getShipChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getTestChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getMinBuyAmt()) + "," +
		StringHandler.nullIfZero(facItemBean.getMiscChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getCylRentChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getSetupChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getCertChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getExpedChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getCutChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getDryIceChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getHazmatChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getPackChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getEnviroChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getRegChrg()) + "," +
		StringHandler.nullIfZero(facItemBean.getOrderPoint()) + "," +
		StringHandler.nullIfZero(facItemBean.getStockingLevel()) + "," +
		SqlHandler.delimitString(facItemBean.getEstablishedStock()) + "," +
		SqlHandler.delimitString(facItemBean.getUnitOfSale()) + "," +
		SqlHandler.delimitString(facItemBean.getCompanyId()) + "," +
		SqlHandler.delimitString(facItemBean.getPartDescription()) + "," +
		StringHandler.nullIfZero(facItemBean.getCatalogPrice()) + "," +
		SqlHandler.delimitString(facItemBean.getBillingCategory()) + "," +
		StringHandler.nullIfZero(facItemBean.getTotalRecertsAllowed()) + "," +
		StringHandler.nullIfZero(facItemBean.getShelfLifeExtension()) + "," +
		SqlHandler.delimitString(facItemBean.getCatalogNotes()) + "," +
		SqlHandler.delimitString(facItemBean.getSearch()) + "," +
		SqlHandler.delimitString(facItemBean.getConsumable()) + "," +
		SqlHandler.delimitString(facItemBean.getCategory()) + "," +
		SqlHandler.delimitString(facItemBean.getLabelSpec()) + "," +
		SqlHandler.delimitString(facItemBean.getAlternateName()) + "," +
	 StringHandler.nullIfZero(facItemBean.getUnitOfSaleQuantityPerEach()) + "," +
		SqlHandler.delimitString(facItemBean.getPartShortName()) + "," +
		SqlHandler.delimitString(facItemBean.getPartHazardImage()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(FacItemBean facItemBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(facItemBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(FacItemBean facItemBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_FACILITY_ID + "=" +
		 SqlHandler.delimitString(facItemBean.getFacilityId()) + "," +
		ATTRIBUTE_SPEC_ID + "=" +
		 SqlHandler.delimitString(facItemBean.getSpecId()) + "," +
		ATTRIBUTE_FAC_PART_NO + "=" +
		 SqlHandler.delimitString(facItemBean.getFacPartNo()) + "," +
		ATTRIBUTE_STOCKED + "=" +
		 SqlHandler.delimitString(facItemBean.getStocked()) + "," +
		ATTRIBUTE_SPEC_LIBRARY + "=" +
		 SqlHandler.delimitString(facItemBean.getSpecLibrary()) + "," +
		ATTRIBUTE_UNIT_PRICE + "=" +
		 StringHandler.nullIfZero(facItemBean.getUnitPrice()) + "," +
		ATTRIBUTE_MINIMUM_BUY + "=" +
		 StringHandler.nullIfZero(facItemBean.getMinimumBuy()) + "," +
		ATTRIBUTE_SHELF_LIFE + "=" +
		 StringHandler.nullIfZero(facItemBean.getShelfLife()) + "," +
		ATTRIBUTE_SHELF_LIFE_UNIT + "=" +
		 SqlHandler.delimitString(facItemBean.getShelfLifeUnit()) + "," +
		ATTRIBUTE_SHELF_LIFE_BASIS + "=" +
		 SqlHandler.delimitString(facItemBean.getShelfLifeBasis()) + "," +
		ATTRIBUTE_C_OF_C + "=" +
		 SqlHandler.delimitString(facItemBean.getCOfC()) + "," +
		ATTRIBUTE_C_OF_A + "=" +
		 SqlHandler.delimitString(facItemBean.getCOfA()) + "," +
		ATTRIBUTE_ANNUAL_USAGE + "=" +
		 StringHandler.nullIfZero(facItemBean.getAnnualUsage()) + "," +
		ATTRIBUTE_STORAGE_TEMP + "=" +
		 SqlHandler.delimitString(facItemBean.getStorageTemp()) + "," +
		ATTRIBUTE_SHELF_LIFE_RELAXED + "=" +
		 SqlHandler.delimitString(facItemBean.getShelfLifeRelaxed()) + "," +
		ATTRIBUTE_SHIP_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getShipChrg()) + "," +
		ATTRIBUTE_TEST_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getTestChrg()) + "," +
		ATTRIBUTE_MIN_BUY_AMT + "=" +
		 StringHandler.nullIfZero(facItemBean.getMinBuyAmt()) + "," +
		ATTRIBUTE_MISC_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getMiscChrg()) + "," +
		ATTRIBUTE_CYL_RENT_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getCylRentChrg()) + "," +
		ATTRIBUTE_SETUP_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getSetupChrg()) + "," +
		ATTRIBUTE_CERT_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getCertChrg()) + "," +
		ATTRIBUTE_EXPED_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getExpedChrg()) + "," +
		ATTRIBUTE_CUT_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getCutChrg()) + "," +
		ATTRIBUTE_DRY_ICE_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getDryIceChrg()) + "," +
		ATTRIBUTE_HAZMAT_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getHazmatChrg()) + "," +
		ATTRIBUTE_PACK_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getPackChrg()) + "," +
		ATTRIBUTE_ENVIRO_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getEnviroChrg()) + "," +
		ATTRIBUTE_REG_CHRG + "=" +
		 StringHandler.nullIfZero(facItemBean.getRegChrg()) + "," +
		ATTRIBUTE_ORDER_POINT + "=" +
		 StringHandler.nullIfZero(facItemBean.getOrderPoint()) + "," +
		ATTRIBUTE_STOCKING_LEVEL + "=" +
		 StringHandler.nullIfZero(facItemBean.getStockingLevel()) + "," +
		ATTRIBUTE_ESTABLISHED_STOCK + "=" +
		 SqlHandler.delimitString(facItemBean.getEstablishedStock()) + "," +
		ATTRIBUTE_UNIT_OF_SALE + "=" +
		 SqlHandler.delimitString(facItemBean.getUnitOfSale()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" +
		 SqlHandler.delimitString(facItemBean.getCompanyId()) + "," +
		ATTRIBUTE_PART_DESCRIPTION + "=" +
		 SqlHandler.delimitString(facItemBean.getPartDescription()) + "," +
		ATTRIBUTE_CATALOG_PRICE + "=" +
		 StringHandler.nullIfZero(facItemBean.getCatalogPrice()) + "," +
		ATTRIBUTE_BILLING_CATEGORY + "=" +
		 SqlHandler.delimitString(facItemBean.getBillingCategory()) + "," +
		ATTRIBUTE_TOTAL_RECERTS_ALLOWED + "=" +
		 StringHandler.nullIfZero(facItemBean.getTotalRecertsAllowed()) + "," +
		ATTRIBUTE_SHELF_LIFE_EXTENSION + "=" +
		 StringHandler.nullIfZero(facItemBean.getShelfLifeExtension()) + "," +
		ATTRIBUTE_CATALOG_NOTES + "=" +
		 SqlHandler.delimitString(facItemBean.getCatalogNotes()) + "," +
		ATTRIBUTE_SEARCH + "=" +
		 SqlHandler.delimitString(facItemBean.getSearch()) + "," +
		ATTRIBUTE_CONSUMABLE + "=" +
		 SqlHandler.delimitString(facItemBean.getConsumable()) + "," +
		ATTRIBUTE_CATEGORY + "=" +
		 SqlHandler.delimitString(facItemBean.getCategory()) + "," +
		ATTRIBUTE_LABEL_SPEC + "=" +
		 SqlHandler.delimitString(facItemBean.getLabelSpec()) + "," +
		ATTRIBUTE_ALTERNATE_NAME + "=" +
		 SqlHandler.delimitString(facItemBean.getAlternateName()) + "," +
		ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "=" +
	 StringHandler.nullIfZero(facItemBean.getUnitOfSaleQuantityPerEach()) + "," +
		ATTRIBUTE_PART_SHORT_NAME + "=" +
		 SqlHandler.delimitString(facItemBean.getPartShortName()) + "," +
		ATTRIBUTE_PART_HAZARD_IMAGE + "=" +
		 SqlHandler.delimitString(facItemBean.getPartHazardImage()) + " " +
		"where " + ATTRIBUTE_FACILITY_ID + "=" +
		 StringHandler.nullIfZero(facItemBean.getFacilityId());
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

	Collection facItemBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 FacItemBean facItemBean = new FacItemBean();
	 load(dataSetRow, facItemBean);
	 facItemBeanColl.add(facItemBean);
	}

	return facItemBeanColl;
 }

 public Collection selectDistinctUOS(SearchCriteria criteria) throws
	BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = selectDistinctUOS(criteria, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection selectDistinctUOS(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection facItemBeanColl = new Vector();

	String query = "select distinct unit_of_sale from " + TABLE + " " +
	 getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 FacItemBean facItemBean = new FacItemBean();
	 load(dataSetRow, facItemBean, true);
	 facItemBeanColl.add(facItemBean);
	}

	return facItemBeanColl;
 }

}