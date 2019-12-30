package com.tcmis.client.report.factory;


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
import com.tcmis.client.report.beans.AdHocTemplateBean;


/******************************************************************************
 * CLASSNAME: AdHocTemplateBeanFactory <br>
 * @version: 1.0, Mar 17, 2009 <br>
 *****************************************************************************/


public class AdHocTemplateBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_REPORT_TYPE = "REPORT_TYPE";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_TEMPLATE_NAME = "TEMPLATE_NAME";
	public String ATTRIBUTE_LIST = "LIST";
	public String ATTRIBUTE_SEP = "SEP";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_STOP_DATE = "STOP_DATE";
	public String ATTRIBUTE_LIST_ID = "LIST_ID";
	public String ATTRIBUTE_ADDL_CONSTRAINT = "ADDL_CONSTRAINT";
	public String ATTRIBUTE_ADDL_FROM = "ADDL_FROM";
	public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CAT_PART_NO_SEARCH_TYPE = "CAT_PART_NO_SEARCH_TYPE";
	public String ATTRIBUTE_DOCK_LOCATION_ID = "DOCK_LOCATION_ID";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_CATEGORY_ID = "CATEGORY_ID";
	public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
	public String ATTRIBUTE_MANUFACTURER_SEARCH_TYPE = "MANUFACTURER_SEARCH_TYPE";
	public String ATTRIBUTE_INCLUDE_COLUMN_ALIAS = "INCLUDE_COLUMN_ALIAS";
	public String ATTRIBUTE_PART_LOCATION = "PART_LOCATION";
	public String ATTRIBUTE_PART_CATEGORY = "PART_CATEGORY";
	public String ATTRIBUTE_ACCUMULATION_POINT = "ACCUMULATION_POINT";
	public String ATTRIBUTE_VENDOR_ID = "VENDOR_ID";
	public String ATTRIBUTE_MANAGEMENT_OPTION_LIST = "MANAGEMENT_OPTION_LIST";
	public String ATTRIBUTE_EXCLUDE_HUB_WASTE = "EXCLUDE_HUB_WASTE";
	public String ATTRIBUTE_MATRIX_QUERY_TYPE = "MATRIX_QUERY_TYPE";
	public String ATTRIBUTE_CHEMICAL_LIST = "CHEMICAL_LIST";
	public String ATTRIBUTE_CHEMICAL_LIST_FORMAT = "CHEMICAL_LIST_FORMAT";
	public String ATTRIBUTE_VENDOR_PROFILE_ID = "VENDOR_PROFILE_ID";

	//table name
	public String TABLE = "AD_HOC_TEMPLATE";


	//constructor
	public AdHocTemplateBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("reportType")) {
			return ATTRIBUTE_REPORT_TYPE;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("templateName")) {
			return ATTRIBUTE_TEMPLATE_NAME;
		}
		else if(attributeName.equals("list")) {
			return ATTRIBUTE_LIST;
		}
		else if(attributeName.equals("sep")) {
			return ATTRIBUTE_SEP;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("stopDate")) {
			return ATTRIBUTE_STOP_DATE;
		}
		else if(attributeName.equals("listId")) {
			return ATTRIBUTE_LIST_ID;
		}
		else if(attributeName.equals("addlConstraint")) {
			return ATTRIBUTE_ADDL_CONSTRAINT;
		}
		else if(attributeName.equals("addlFrom")) {
			return ATTRIBUTE_ADDL_FROM;
		}
		else if(attributeName.equals("casNumber")) {
			return ATTRIBUTE_CAS_NUMBER;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("catPartNoSearchType")) {
			return ATTRIBUTE_CAT_PART_NO_SEARCH_TYPE;
		}
		else if(attributeName.equals("dockLocationId")) {
			return ATTRIBUTE_DOCK_LOCATION_ID;
		}
		else if(attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if(attributeName.equals("categoryId")) {
			return ATTRIBUTE_CATEGORY_ID;
		}
		else if(attributeName.equals("manufacturer")) {
			return ATTRIBUTE_MANUFACTURER;
		}
		else if(attributeName.equals("manufacturerSearchType")) {
			return ATTRIBUTE_MANUFACTURER_SEARCH_TYPE;
		}
		else if(attributeName.equals("includeColumnAlias")) {
			return ATTRIBUTE_INCLUDE_COLUMN_ALIAS;
		}
		else if(attributeName.equals("partLocation")) {
			return ATTRIBUTE_PART_LOCATION;
		}
		else if(attributeName.equals("partCategory")) {
			return ATTRIBUTE_PART_CATEGORY;
		}
		else if(attributeName.equals("accumulationPoint")) {
			return ATTRIBUTE_ACCUMULATION_POINT;
		}
		else if(attributeName.equals("vendorId")) {
			return ATTRIBUTE_VENDOR_ID;
		}
		else if(attributeName.equals("managementOptionList")) {
			return ATTRIBUTE_MANAGEMENT_OPTION_LIST;
		}
		else if(attributeName.equals("excludeHubWaste")) {
			return ATTRIBUTE_EXCLUDE_HUB_WASTE;
		}
		else if(attributeName.equals("matrixQueryType")) {
			return ATTRIBUTE_MATRIX_QUERY_TYPE;
		}
		else if(attributeName.equals("chemicalList")) {
			return ATTRIBUTE_CHEMICAL_LIST;
		}
		else if(attributeName.equals("chemicalListFormat")) {
			return ATTRIBUTE_CHEMICAL_LIST_FORMAT;
		}
		else if(attributeName.equals("vendorProfileId")) {
			return ATTRIBUTE_VENDOR_PROFILE_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, AdHocTemplateBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(AdHocTemplateBean adHocTemplateBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + adHocTemplateBean.getCompanyId());

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


	public int delete(AdHocTemplateBean adHocTemplateBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + adHocTemplateBean.getCompanyId());

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
	public int insert(AdHocTemplateBean adHocTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(adHocTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(AdHocTemplateBean adHocTemplateBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_REPORT_TYPE + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_TEMPLATE_NAME + "," +
			ATTRIBUTE_LIST + "," +
			ATTRIBUTE_SEP + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_STOP_DATE + "," +
			ATTRIBUTE_LIST_ID + "," +
			ATTRIBUTE_ADDL_CONSTRAINT + "," +
			ATTRIBUTE_ADDL_FROM + "," +
			ATTRIBUTE_CAS_NUMBER + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_CAT_PART_NO_SEARCH_TYPE + "," +
			ATTRIBUTE_DOCK_LOCATION_ID + "," +
			ATTRIBUTE_DELIVERY_POINT + "," +
			ATTRIBUTE_CATEGORY_ID + "," +
			ATTRIBUTE_MANUFACTURER + "," +
			ATTRIBUTE_MANUFACTURER_SEARCH_TYPE + "," +
			ATTRIBUTE_INCLUDE_COLUMN_ALIAS + "," +
			ATTRIBUTE_PART_LOCATION + "," +
			ATTRIBUTE_PART_CATEGORY + "," +
			ATTRIBUTE_ACCUMULATION_POINT + "," +
			ATTRIBUTE_VENDOR_ID + "," +
			ATTRIBUTE_MANAGEMENT_OPTION_LIST + "," +
			ATTRIBUTE_EXCLUDE_HUB_WASTE + "," +
			ATTRIBUTE_MATRIX_QUERY_TYPE + "," +
			ATTRIBUTE_CHEMICAL_LIST + "," +
			ATTRIBUTE_CHEMICAL_LIST_FORMAT + "," +
			ATTRIBUTE_VENDOR_PROFILE_ID + ")" +
			" values (" +
			SqlHandler.delimitString(adHocTemplateBean.getCompanyId()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getReportType()) + "," +
			adHocTemplateBean.getPersonnelId() + "," +
			SqlHandler.delimitString(adHocTemplateBean.getTemplateName()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getList()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getSep()) + "," +
			DateHandler.getOracleToDateFunction(adHocTemplateBean.getStartDate()) + "," +
			DateHandler.getOracleToDateFunction(adHocTemplateBean.getStopDate()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getListId()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getAddlConstraint()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getAddlFrom()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getCasNumber()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getFacilityId()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getApplication()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getCatPartNoSearchType()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getDockLocationId()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getDeliveryPoint()) + "," +
			adHocTemplateBean.getCategoryId() + "," +
			SqlHandler.delimitString(adHocTemplateBean.getManufacturer()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getManufacturerSearchType()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getIncludeColumnAlias()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getPartLocation()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getPartCategory()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getAccumulationPoint()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getVendorId()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getManagementOptionList()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getExcludeHubWaste()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getMatrixQueryType()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getChemicalList()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getChemicalListFormat()) + "," +
			SqlHandler.delimitString(adHocTemplateBean.getVendorProfileId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(AdHocTemplateBean adHocTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(adHocTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(AdHocTemplateBean adHocTemplateBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getCompanyId()) + "," +
			ATTRIBUTE_REPORT_TYPE + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getReportType()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" + 
				StringHandler.nullIfZero(adHocTemplateBean.getPersonnelId()) + "," +
			ATTRIBUTE_TEMPLATE_NAME + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getTemplateName()) + "," +
			ATTRIBUTE_LIST + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getList()) + "," +
			ATTRIBUTE_SEP + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getSep()) + "," +
			ATTRIBUTE_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(adHocTemplateBean.getStartDate()) + "," +
			ATTRIBUTE_STOP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(adHocTemplateBean.getStopDate()) + "," +
			ATTRIBUTE_LIST_ID + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getListId()) + "," +
			ATTRIBUTE_ADDL_CONSTRAINT + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getAddlConstraint()) + "," +
			ATTRIBUTE_ADDL_FROM + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getAddlFrom()) + "," +
			ATTRIBUTE_CAS_NUMBER + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getCasNumber()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getApplication()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getCatPartNo()) + "," +
			ATTRIBUTE_CAT_PART_NO_SEARCH_TYPE + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getCatPartNoSearchType()) + "," +
			ATTRIBUTE_DOCK_LOCATION_ID + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getDockLocationId()) + "," +
			ATTRIBUTE_DELIVERY_POINT + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getDeliveryPoint()) + "," +
			ATTRIBUTE_CATEGORY_ID + "=" + 
				StringHandler.nullIfZero(adHocTemplateBean.getCategoryId()) + "," +
			ATTRIBUTE_MANUFACTURER + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getManufacturer()) + "," +
			ATTRIBUTE_MANUFACTURER_SEARCH_TYPE + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getManufacturerSearchType()) + "," +
			ATTRIBUTE_INCLUDE_COLUMN_ALIAS + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getIncludeColumnAlias()) + "," +
			ATTRIBUTE_PART_LOCATION + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getPartLocation()) + "," +
			ATTRIBUTE_PART_CATEGORY + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getPartCategory()) + "," +
			ATTRIBUTE_ACCUMULATION_POINT + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getAccumulationPoint()) + "," +
			ATTRIBUTE_VENDOR_ID + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getVendorId()) + "," +
			ATTRIBUTE_MANAGEMENT_OPTION_LIST + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getManagementOptionList()) + "," +
			ATTRIBUTE_EXCLUDE_HUB_WASTE + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getExcludeHubWaste()) + "," +
			ATTRIBUTE_MATRIX_QUERY_TYPE + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getMatrixQueryType()) + "," +
			ATTRIBUTE_CHEMICAL_LIST + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getChemicalList()) + "," +
			ATTRIBUTE_CHEMICAL_LIST_FORMAT + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getChemicalListFormat()) + "," +
			ATTRIBUTE_VENDOR_PROFILE_ID + "=" + 
				SqlHandler.delimitString(adHocTemplateBean.getVendorProfileId()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				adHocTemplateBean.getCompanyId();

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

		Collection adHocTemplateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			AdHocTemplateBean adHocTemplateBean = new AdHocTemplateBean();
			load(dataSetRow, adHocTemplateBean);
			adHocTemplateBeanColl.add(adHocTemplateBean);
		}

		return adHocTemplateBeanColl;
	}
}