package  com.tcmis.internal.distribution.factory;


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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.SupplierEntitySearchViewBean;


/******************************************************************************
 * CLASSNAME: SupplierEntitySearchViewBeanFactory <br>
 * @version: 1.0, Oct 26, 2009 <br>
 *****************************************************************************/


public class SupplierEntitySearchViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
	public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_ZIP = "ZIP";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_MAIN_PHONE = "MAIN_PHONE";
	public String ATTRIBUTE_DEFAULT_PAYMENT_TERMS = "DEFAULT_PAYMENT_TERMS";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_FORMER_SUPPLIER_NAME = "FORMER_SUPPLIER_NAME";
	public String ATTRIBUTE_QUALIFICATION_LEVEL = "QUALIFICATION_LEVEL";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_SUPPLIER_EMAIL = "SUPPLIER_EMAIL";
	public String ATTRIBUTE_FIRST_NAME = "FIRST_NAME";
	public String ATTRIBUTE_LAST_NAME = "LAST_NAME";
	public String ATTRIBUTE_NICKNAME = "NICKNAME";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_PHONE_EXTENSION = "PHONE_EXTENSION";
	public String ATTRIBUTE_FAX = "FAX";
	public String ATTRIBUTE_DEBARRED = "DEBARRED";
	public String ATTRIBUTE_TYPE_OF_PURCHASE = "TYPE_OF_PURCHASE";
	public String ATTRIBUTE_SUPPLIER_NOTES = "SUPPLIER_NOTES";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_APPROVED_BY = "APPROVED_BY";
	public String ATTRIBUTE_APPROVED_ON = "APPROVED_ON";
	public String ATTRIBUTE_PAYMENT_TERM_STATUS = "PAYMENT_TERM_STATUS";

	//table name
	public String TABLE = "SUPPLIER_ENTITY_SEARCH_VIEW";


	//constructor
	public SupplierEntitySearchViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("addressLine1")) {
			return ATTRIBUTE_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("addressLine2")) {
			return ATTRIBUTE_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("city")) {
			return ATTRIBUTE_CITY;
		}
		else if(attributeName.equals("zip")) {
			return ATTRIBUTE_ZIP;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if(attributeName.equals("mainPhone")) {
			return ATTRIBUTE_MAIN_PHONE;
		}
		else if(attributeName.equals("defaultPaymentTerms")) {
			return ATTRIBUTE_DEFAULT_PAYMENT_TERMS;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("formerSupplierName")) {
			return ATTRIBUTE_FORMER_SUPPLIER_NAME;
		}
		else if(attributeName.equals("qualificationLevel")) {
			return ATTRIBUTE_QUALIFICATION_LEVEL;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("supplierEmail")) {
			return ATTRIBUTE_SUPPLIER_EMAIL;
		}
		else if(attributeName.equals("firstName")) {
			return ATTRIBUTE_FIRST_NAME;
		}
		else if(attributeName.equals("lastName")) {
			return ATTRIBUTE_LAST_NAME;
		}
		else if(attributeName.equals("nickname")) {
			return ATTRIBUTE_NICKNAME;
		}
		else if(attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if(attributeName.equals("phoneExtension")) {
			return ATTRIBUTE_PHONE_EXTENSION;
		}
		else if(attributeName.equals("fax")) {
			return ATTRIBUTE_FAX;
		}
		else if(attributeName.equals("debarred")) {
			return ATTRIBUTE_DEBARRED;
		}
		else if(attributeName.equals("typeOfPurchase")) {
			return ATTRIBUTE_TYPE_OF_PURCHASE;
		}
		else if(attributeName.equals("supplierNotes")) {
			return ATTRIBUTE_SUPPLIER_NOTES;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("approvedBy")) {
			return ATTRIBUTE_APPROVED_BY;
		}
		else if(attributeName.equals("approvedOn")) {
			return ATTRIBUTE_APPROVED_ON;
		}
		else if(attributeName.equals("paymentTermStatus")) {
			return ATTRIBUTE_PAYMENT_TERM_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierEntitySearchViewBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(SupplierEntitySearchViewBean supplierEntitySearchViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + supplierEntitySearchViewBean.getSupplier());

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


	public int delete(SupplierEntitySearchViewBean supplierEntitySearchViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + supplierEntitySearchViewBean.getSupplier());

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
	public int insert(SupplierEntitySearchViewBean supplierEntitySearchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(supplierEntitySearchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SupplierEntitySearchViewBean supplierEntitySearchViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_STATE_ABBREV + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_CITY + "," +
			ATTRIBUTE_ZIP + "," +
			ATTRIBUTE_LOCATION_DESC + "," +
			ATTRIBUTE_MAIN_PHONE + "," +
			ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_FORMER_SUPPLIER_NAME + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "," +
			ATTRIBUTE_EMAIL + "," +
			ATTRIBUTE_SUPPLIER_EMAIL + "," +
			ATTRIBUTE_FIRST_NAME + "," +
			ATTRIBUTE_LAST_NAME + "," +
			ATTRIBUTE_NICKNAME + "," +
			ATTRIBUTE_PHONE + "," +
			ATTRIBUTE_PHONE_EXTENSION + "," +
			ATTRIBUTE_FAX + "," +
			ATTRIBUTE_DEBARRED + "," +
			ATTRIBUTE_TYPE_OF_PURCHASE + "," +
			ATTRIBUTE_SUPPLIER_NOTES + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_APPROVED_BY + "," +
			ATTRIBUTE_APPROVED_ON + "," +
			ATTRIBUTE_PAYMENT_TERM_STATUS + ")" +
			" values (" +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getAddressLine1()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getAddressLine2()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getCity()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getZip()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getLocationDesc()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getMainPhone()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getDefaultPaymentTerms()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getStatus()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getFormerSupplierName()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getQualificationLevel()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getEmail()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplierEmail()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getFirstName()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getLastName()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getNickname()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getPhone()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getPhoneExtension()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getFax()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getDebarred()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getTypeOfPurchase()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplierNotes()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getPaymentTerms()) + "," +
			supplierEntitySearchViewBean.getApprovedBy() + "," +
			DateHandler.getOracleToDateFunction(supplierEntitySearchViewBean.getApprovedOn()) + "," +
			SqlHandler.delimitString(supplierEntitySearchViewBean.getPaymentTermStatus()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SupplierEntitySearchViewBean supplierEntitySearchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(supplierEntitySearchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SupplierEntitySearchViewBean supplierEntitySearchViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplierName()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getStateAbbrev()) + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getAddressLine1()) + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getAddressLine2()) + "," +
			ATTRIBUTE_CITY + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getCity()) + "," +
			ATTRIBUTE_ZIP + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getZip()) + "," +
			ATTRIBUTE_LOCATION_DESC + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getLocationDesc()) + "," +
			ATTRIBUTE_MAIN_PHONE + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getMainPhone()) + "," +
			ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getDefaultPaymentTerms()) + "," +
			ATTRIBUTE_STATUS + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getStatus()) + "," +
			ATTRIBUTE_FORMER_SUPPLIER_NAME + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getFormerSupplierName()) + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getQualificationLevel()) + "," +
			ATTRIBUTE_EMAIL + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getEmail()) + "," +
			ATTRIBUTE_SUPPLIER_EMAIL + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplierEmail()) + "," +
			ATTRIBUTE_FIRST_NAME + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getFirstName()) + "," +
			ATTRIBUTE_LAST_NAME + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getLastName()) + "," +
			ATTRIBUTE_NICKNAME + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getNickname()) + "," +
			ATTRIBUTE_PHONE + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getPhone()) + "," +
			ATTRIBUTE_PHONE_EXTENSION + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getPhoneExtension()) + "," +
			ATTRIBUTE_FAX + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getFax()) + "," +
			ATTRIBUTE_DEBARRED + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getDebarred()) + "," +
			ATTRIBUTE_TYPE_OF_PURCHASE + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getTypeOfPurchase()) + "," +
			ATTRIBUTE_SUPPLIER_NOTES + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getSupplierNotes()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getOpsEntityId()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getPaymentTerms()) + "," +
			ATTRIBUTE_APPROVED_BY + "=" +
				StringHandler.nullIfZero(supplierEntitySearchViewBean.getApprovedBy()) + "," +
			ATTRIBUTE_APPROVED_ON + "=" +
				DateHandler.getOracleToDateFunction(supplierEntitySearchViewBean.getApprovedOn()) + "," +
			ATTRIBUTE_PAYMENT_TERM_STATUS + "=" +
				SqlHandler.delimitString(supplierEntitySearchViewBean.getPaymentTermStatus()) + " " +
			"where " + ATTRIBUTE_SUPPLIER + "=" +
				supplierEntitySearchViewBean.getSupplier();

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

		Collection supplierEntitySearchViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierEntitySearchViewBean supplierEntitySearchViewBean = new SupplierEntitySearchViewBean();
			load(dataSetRow, supplierEntitySearchViewBean);
			supplierEntitySearchViewBeanColl.add(supplierEntitySearchViewBean);
		}

		return supplierEntitySearchViewBeanColl;
	}


	public Collection select(String supplierName, String opsEntityId,String activeOnly, String country)	throws BaseException
	{
		Connection connection = null;
		Collection c = null;
		try
		{
			connection = this.getDbManager().getConnection();
			c = select(supplierName, opsEntityId,activeOnly,country, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}



	public Collection select(String supplierName,String opsEntityId,String activeOnly,String country, Connection conn)	throws BaseException
	{
		Collection supplierInfoColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder("select * from "); // + TABLE + " " +
		queryBuffer.append(TABLE);
		queryBuffer.append(" where lower(FORMER_SUPPLIER_NAME||ADDRESS_LINE_1||ADDRESS_LINE_2||SUPPLIER||SUPPLIER_NAME||CITY) like lower('%");
		queryBuffer.append(supplierName);
		queryBuffer.append("%') ");
		if(!StringHandler.isBlankString(opsEntityId))
		{
			queryBuffer.append(" and ");
			queryBuffer.append("  nvl(OPS_ENTITY_ID,'"+opsEntityId+"') ='");
			queryBuffer.append(opsEntityId+"'");
		}


		if(!StringHandler.isBlankString(country)  && !("All").equalsIgnoreCase(country))
		{
			queryBuffer.append(" and ");
			queryBuffer.append(" COUNTRY_ABBREV ='");
			queryBuffer.append(country+"'");
		}
		if(!StringHandler.isBlankString(activeOnly))
		{
			queryBuffer.append(" and ");
			queryBuffer.append(" status ='Active' ");
			queryBuffer.append(" and ").append(ATTRIBUTE_PAYMENT_TERM_STATUS).append(" = 'Active'");
			queryBuffer.append(" and ").append(ATTRIBUTE_PAYMENT_TERMS).append(" is not null");
		}
		queryBuffer.append(" order by SUPPLIER_NAME asc");
		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierEntitySearchViewBean supplierInfoBean = new SupplierEntitySearchViewBean();
			load(dataSetRow, supplierInfoBean);
			//			log.debug("supplierAddressViewBean.toString() = [" + supplierAddressViewBean.toString() + "]; ");
			supplierInfoColl.add(supplierInfoBean);
		}
		return supplierInfoColl;
	}
}