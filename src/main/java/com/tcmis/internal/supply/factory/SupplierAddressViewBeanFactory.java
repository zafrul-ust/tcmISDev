package com.tcmis.internal.supply.factory;

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
import com.tcmis.internal.supply.beans.SupplierAddressViewBean;

/******************************************************************************
 * CLASSNAME: SupplierAddressViewBeanFactory <br>
 * 
 * @version: 1.0, Aug 28, 2007 <br>
 *****************************************************************************/

public class SupplierAddressViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
	public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
	public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_ZIP = "ZIP";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_CLIENT_LOCATION_CODE = "CLIENT_LOCATION_CODE";
	public String ATTRIBUTE_MAIN_PHONE = "MAIN_PHONE";
	public String ATTRIBUTE_DEFAULT_PAYMENT_TERMS = "DEFAULT_PAYMENT_TERMS";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_FORMER_SUPPLIER_NAME = "FORMER_SUPPLIER_NAME";
	public String ATTRIBUTE_QUALIFICATION_LEVEL = "QUALIFICATION_LEVEL";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_SUPPLIER_EMAIL = "SUPPLIER_EMAIL";
	public String ATTRIBUTE_STATUS_CHANGE_COMMENTS = "STATUS_CHANGE_COMMENTS";
	public String ATTRIBUTE_NEW_SUPPLIER_ID = "NEW_SUPPLIER_ID";
	public String ATTRIBUTE_NEW_SUPPLIER_NAME = "NEW_SUPPLIER_NAME";
	public String ATTRIBUTE_FEDERAL_TAX_ID = "FEDERAL_TAX_ID";
	public String ATTRIBUTE_E_SUPPLIER_ID = "E_SUPPLIER_ID";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_TRANSPORT = "TRANSPORT";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_SUPPLIER_NOTES = "SUPPLIER_NOTES";
	public String ATTRIBUTE_SAP_VENDOR_CODE = "SAP_VENDOR_CODE";
	public String ATTRIBUTE_VAT_REGISTRATION_NUMBER = "VAT_REGISTRATION_NUMBER";
	public String ATTRIBUTE_ACTIVE_PAYMENT_TERMS = "ACTIVE_PAYMENT_TERMS";
	public String ATTRIBUTE_EXISTING_SUPPLIER_PRICE_LIST = "EXISTING_SUPPLIER_PRICE_LIST";
	public String ATTRIBUTE_ISO_9001_CERTIFIED = "ISO_9001_CERTIFIED";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";

	//table name
	public String TABLE = "SUPPLIER_ADDRESS_VIEW";

	//constructor
	public SupplierAddressViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if (attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if (attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if (attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if (attributeName.equals("addressLine1")) {
			return ATTRIBUTE_ADDRESS_LINE_1;
		}
		else if (attributeName.equals("addressLine2")) {
			return ATTRIBUTE_ADDRESS_LINE_2;
		}
		else if (attributeName.equals("addressLine3")) {
			return ATTRIBUTE_ADDRESS_LINE_3;
		}
		else if (attributeName.equals("city")) {
			return ATTRIBUTE_CITY;
		}
		else if (attributeName.equals("zip")) {
			return ATTRIBUTE_ZIP;
		}
		else if (attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if (attributeName.equals("clientLocationCode")) {
			return ATTRIBUTE_CLIENT_LOCATION_CODE;
		}
		else if (attributeName.equals("mainPhone")) {
			return ATTRIBUTE_MAIN_PHONE;
		}
		else if (attributeName.equals("defaultPaymentTerms")) {
			return ATTRIBUTE_DEFAULT_PAYMENT_TERMS;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if (attributeName.equals("formerSupplierName")) {
			return ATTRIBUTE_FORMER_SUPPLIER_NAME;
		}
		else if (attributeName.equals("qualificationLevel")) {
			return ATTRIBUTE_QUALIFICATION_LEVEL;
		}
		else if (attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if (attributeName.equals("supplierEmail")) {
			return ATTRIBUTE_SUPPLIER_EMAIL;
		}
		else if (attributeName.equals("statusChangeComments")) {
			return ATTRIBUTE_STATUS_CHANGE_COMMENTS;
		}
		else if (attributeName.equals("newSupplierId")) {
			return ATTRIBUTE_NEW_SUPPLIER_ID;
		}
		else if (attributeName.equals("newSupplierName")) {
			return ATTRIBUTE_NEW_SUPPLIER_NAME;
		}
		else if (attributeName.equals("federalTaxId")) {
			return ATTRIBUTE_FEDERAL_TAX_ID;
		}
		else if (attributeName.equals("eSupplierId")) {
			return ATTRIBUTE_E_SUPPLIER_ID;
		}

		else if (attributeName.equals("e_SupplierId")) {
			return ATTRIBUTE_E_SUPPLIER_ID;
		}

		else if (attributeName.equals("accountNumber")) {
			return ATTRIBUTE_ACCOUNT_NUMBER;
		}
		else if (attributeName.equals("transport")) {
			return ATTRIBUTE_TRANSPORT;
		}
		else if (attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if (attributeName.equals("supplierNotes")) {
			return ATTRIBUTE_SUPPLIER_NOTES;
		}
		else if (attributeName.equals("sapVendorCode")) {
			return ATTRIBUTE_SAP_VENDOR_CODE;
		}
		else if (attributeName.equals("vatRegistrationNumber")) {
			return ATTRIBUTE_VAT_REGISTRATION_NUMBER;
		}
		else if (attributeName.equals("activePaymentTerms")) {
			return ATTRIBUTE_ACTIVE_PAYMENT_TERMS;
		}
		else if (attributeName.equals("existingSupplierPriceList")) {
			return ATTRIBUTE_EXISTING_SUPPLIER_PRICE_LIST;
		}
		else if (attributeName.equals("iso9001Certified")) {
			return ATTRIBUTE_ISO_9001_CERTIFIED;
		}
		else if (attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierAddressViewBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
		//delete
		public int delete(SupplierAddressViewBean supplierAddressViewBean)
			throws BaseException {

			SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
				"" + supplierAddressViewBean.getSupplier());

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


		public int delete(SupplierAddressViewBean supplierAddressViewBean, Connection conn)
			throws BaseException {

			SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
				"" + supplierAddressViewBean.getSupplier());

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

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
		//insert
		public int insert(SupplierAddressViewBean supplierAddressViewBean)
			throws BaseException {

			Connection connection = null;
			int result = 0;
			try {
				connection = getDbManager().getConnection();
				result = insert(supplierAddressViewBean, connection);
			}
			finally {
				this.getDbManager().returnConnection(connection);
			}
			return result;
		}


		public int insert(SupplierAddressViewBean supplierAddressViewBean, Connection conn)
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
				ATTRIBUTE_ADDRESS_LINE_3 + "," +
				ATTRIBUTE_CITY + "," +
				ATTRIBUTE_ZIP + "," +
				ATTRIBUTE_LOCATION_DESC + "," +
				ATTRIBUTE_CLIENT_LOCATION_CODE + "," +
				ATTRIBUTE_MAIN_PHONE + "," +
				ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "," +
				ATTRIBUTE_STATUS + "," +
				ATTRIBUTE_FORMER_SUPPLIER_NAME + "," +
				ATTRIBUTE_QUALIFICATION_LEVEL + "," +
				ATTRIBUTE_EMAIL + "," +
				ATTRIBUTE_SUPPLIER_EMAIL + "," +
				ATTRIBUTE_STATUS_CHANGE_COMMENTS + "," +
				ATTRIBUTE_NEW_SUPPLIER_ID + "," +
				ATTRIBUTE_NEW_SUPPLIER_NAME + "," +
				ATTRIBUTE_FEDERAL_TAX_ID + "," +
				ATTRIBUTE_E_SUPPLIER_ID + "," +
				ATTRIBUTE_ACCOUNT_NUMBER + "," +
				ATTRIBUTE_TRANSPORT + ")" +
				" values (" +
				SqlHandler.delimitString(supplierAddressViewBean.getSupplier()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getSupplierName()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getCountryAbbrev()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getStateAbbrev()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getAddressLine1()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getAddressLine2()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getAddressLine3()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getCity()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getZip()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getLocationDesc()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getClientLocationCode()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getMainPhone()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getDefaultPaymentTerms()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getStatus()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getFormerSupplierName()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getQualificationLevel()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getEmail()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getSupplierEmail()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getStatusChangeComments()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getNewSupplierId()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getNewSupplierName()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getFederalTaxId()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getESupplierId()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getAccountNumber()) + "," +
				SqlHandler.delimitString(supplierAddressViewBean.getTransport()) + ")";

			return sqlManager.update(conn, query);
		}


		//update
		public int update(SupplierAddressViewBean supplierAddressViewBean)
			throws BaseException {

			Connection connection = null;
			int result = 0;
			try {
				connection = getDbManager().getConnection();
				result = update(supplierAddressViewBean, connection);
			}
			finally {
				this.getDbManager().returnConnection(connection);
			}
			return result;
		}


		public int update(SupplierAddressViewBean supplierAddressViewBean, Connection conn)
			throws BaseException {

			String query  = "update " + TABLE + " set " +
				ATTRIBUTE_SUPPLIER + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getSupplier()) + "," +
				ATTRIBUTE_SUPPLIER_NAME + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getSupplierName()) + "," +
				ATTRIBUTE_COUNTRY_ABBREV + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getCountryAbbrev()) + "," +
				ATTRIBUTE_STATE_ABBREV + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getStateAbbrev()) + "," +
				ATTRIBUTE_ADDRESS_LINE_1 + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getAddressLine1()) + "," +
				ATTRIBUTE_ADDRESS_LINE_2 + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getAddressLine2()) + "," +
				ATTRIBUTE_ADDRESS_LINE_3 + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getAddressLine3()) + "," +
				ATTRIBUTE_CITY + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getCity()) + "," +
				ATTRIBUTE_ZIP + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getZip()) + "," +
				ATTRIBUTE_LOCATION_DESC + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getLocationDesc()) + "," +
				ATTRIBUTE_CLIENT_LOCATION_CODE + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getClientLocationCode()) + "," +
				ATTRIBUTE_MAIN_PHONE + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getMainPhone()) + "," +
				ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getDefaultPaymentTerms()) + "," +
				ATTRIBUTE_STATUS + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getStatus()) + "," +
				ATTRIBUTE_FORMER_SUPPLIER_NAME + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getFormerSupplierName()) + "," +
				ATTRIBUTE_QUALIFICATION_LEVEL + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getQualificationLevel()) + "," +
				ATTRIBUTE_EMAIL + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getEmail()) + "," +
				ATTRIBUTE_SUPPLIER_EMAIL + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getSupplierEmail()) + "," +
				ATTRIBUTE_STATUS_CHANGE_COMMENTS + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getStatusChangeComments()) + "," +
				ATTRIBUTE_NEW_SUPPLIER_ID + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getNewSupplierId()) + "," +
				ATTRIBUTE_NEW_SUPPLIER_NAME + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getNewSupplierName()) + "," +
				ATTRIBUTE_FEDERAL_TAX_ID + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getFederalTaxId()) + "," +
				ATTRIBUTE_E_SUPPLIER_ID + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getESupplierId()) + "," +
				ATTRIBUTE_ACCOUNT_NUMBER + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getAccountNumber()) + "," +
				ATTRIBUTE_TRANSPORT + "=" +
					SqlHandler.delimitString(supplierAddressViewBean.getTransport()) + " " +
				"where " + ATTRIBUTE_SUPPLIER + "=" +
					supplierAddressViewBean.getSupplier();

			return new SqlManager().update(conn, query);
		}
	 */

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection supplierAddressViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			SupplierAddressViewBean supplierAddressViewBean = new SupplierAddressViewBean();
			load(dataSetRow, supplierAddressViewBean);
			supplierAddressViewBeanColl.add(supplierAddressViewBean);
		}

		return supplierAddressViewBeanColl;
	}

	public Collection select(String searchArgument,String opsEntityId, String country, String activeOnly) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(searchArgument, opsEntityId, country, activeOnly, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(String searchArgument,String opsEntityId, String country, String activeOnly, Connection conn) throws BaseException {
		Collection supplierAddressViewBeanColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder("select * from "); // + TABLE + " " +
		queryBuffer.append(TABLE);
		queryBuffer.append(" where lower(FORMER_SUPPLIER_NAME||ADDRESS_LINE_1||ADDRESS_LINE_2||SUPPLIER||SUPPLIER_NAME) like lower('%");
		queryBuffer.append(searchArgument);
		queryBuffer.append("%') ");
		
		if(!StringHandler.isBlankString(opsEntityId))
		{
			queryBuffer.append(" and nvl(OPS_ENTITY_ID,'"+opsEntityId+"') ='");
			queryBuffer.append(opsEntityId+"'");
		}
		
		if (!StringHandler.isBlankString(country) && !("All").equalsIgnoreCase(country)) {
			queryBuffer.append(" and ");
			queryBuffer.append(" COUNTRY_ABBREV ='");
			queryBuffer.append(country).append("'");
		}
		if (!StringHandler.isBlankString(activeOnly)) {
			queryBuffer.append(" and ");
			queryBuffer.append(" active_payment_terms ='Active' ");
		}
		queryBuffer.append(" order by SUPPLIER_NAME asc");
		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString());

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			SupplierAddressViewBean supplierAddressViewBean = new SupplierAddressViewBean();
			load(dataSetRow, supplierAddressViewBean);
			//			log.debug("supplierAddressViewBean.toString() = [" + supplierAddressViewBean.toString() + "]; ");
			supplierAddressViewBeanColl.add(supplierAddressViewBean);
		}
		return supplierAddressViewBeanColl;
	}
}