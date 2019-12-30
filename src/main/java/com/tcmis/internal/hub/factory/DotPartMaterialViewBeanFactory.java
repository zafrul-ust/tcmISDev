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
import com.tcmis.internal.hub.beans.DotPartMaterialViewBean;


/******************************************************************************
 * CLASSNAME: DotPartMaterialViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class DotPartMaterialViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PKG_GT_RQ = "PKG_GT_RQ";
	public String ATTRIBUTE_TEST_PKG_GT_RQ = "TEST_PKG_GT_RQ";
	public String ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT = "BULK_PKG_MARINE_POLLUTANT";
	public String ATTRIBUTE_ORM_D = "ORM_D";
	public String ATTRIBUTE_WEIGHT_LB = "WEIGHT_LB";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_HAZARD_CLASS = "HAZARD_CLASS";
	public String ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS = "SUBSIDIARY_HAZARD_CLASS";
	public String ATTRIBUTE_PACKING_GROUP = "PACKING_GROUP";
	public String ATTRIBUTE_IDENTIFICATION_NUMBER = "IDENTIFICATION_NUMBER";
	public String ATTRIBUTE_HAZMAT_ID = "HAZMAT_ID";
	public String ATTRIBUTE_PROPER_SHIPPING_NAME = "PROPER_SHIPPING_NAME";
	public String ATTRIBUTE_DRY_ICE = "DRY_ICE";
	public String ATTRIBUTE_ERG = "ERG";
	public String ATTRIBUTE_MARINE_POLLUTANT = "MARINE_POLLUTANT";
	public String ATTRIBUTE_REPORTABLE_QUANTITY_LB = "REPORTABLE_QUANTITY_LB";
	public String ATTRIBUTE_SHIPPING_INFO_REMARKS = "SHIPPING_INFO_REMARKS";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_LAST_CHANGED_BY = "LAST_CHANGED_BY";
	public String ATTRIBUTE_LAST_UPDATED = "LAST_UPDATED";
	public String ATTRIBUTE_HAZMAT_TECHNICAL_NAME = "HAZMAT_TECHNICAL_NAME";
	public String ATTRIBUTE_SYMBOL = "SYMBOL";
	public String ATTRIBUTE_SCHEDULE_B = "SCHEDULE_B";
	public String ATTRIBUTE_SCHEDULE_B_DESC = "SCHEDULE_B_DESC";
	public String ATTRIBUTE_ECCN = "ECCN";

	//table name
	public String TABLE = "DOT_PART_MATERIAL_VIEW";


	//constructor
	public DotPartMaterialViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("pkgGtRq")) {
			return ATTRIBUTE_PKG_GT_RQ;
		}
		else if(attributeName.equals("testPkgGtRq")) {
			return ATTRIBUTE_TEST_PKG_GT_RQ;
		}
		else if(attributeName.equals("bulkPkgMarinePollutant")) {
			return ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT;
		}
		else if(attributeName.equals("ormDd")) {
			return ATTRIBUTE_ORM_D;
		}
		else if(attributeName.equals("weightLb")) {
			return ATTRIBUTE_WEIGHT_LB;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("hazardClass")) {
			return ATTRIBUTE_HAZARD_CLASS;
		}
		else if(attributeName.equals("subsidiaryHazardClass")) {
			return ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS;
		}
		else if(attributeName.equals("packingGroup")) {
			return ATTRIBUTE_PACKING_GROUP;
		}
		else if(attributeName.equals("identificationNumber")) {
			return ATTRIBUTE_IDENTIFICATION_NUMBER;
		}
		else if(attributeName.equals("hazmatId")) {
			return ATTRIBUTE_HAZMAT_ID;
		}
		else if(attributeName.equals("properShippingName")) {
			return ATTRIBUTE_PROPER_SHIPPING_NAME;
		}
		else if(attributeName.equals("dryIce")) {
			return ATTRIBUTE_DRY_ICE;
		}
		else if(attributeName.equals("erg")) {
			return ATTRIBUTE_ERG;
		}
		else if(attributeName.equals("marinePollutant")) {
			return ATTRIBUTE_MARINE_POLLUTANT;
		}
		else if(attributeName.equals("reportableQuantityLb")) {
			return ATTRIBUTE_REPORTABLE_QUANTITY_LB;
		}
		else if(attributeName.equals("shippingInfoRemarks")) {
			return ATTRIBUTE_SHIPPING_INFO_REMARKS;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("lastChangedBy")) {
			return ATTRIBUTE_LAST_CHANGED_BY;
		}
		else if(attributeName.equals("lastUpdated")) {
			return ATTRIBUTE_LAST_UPDATED;
		}
		else if(attributeName.equals("hazmatTechnicalName")) {
			return ATTRIBUTE_HAZMAT_TECHNICAL_NAME;
		}
		else if(attributeName.equals("symbol")) {
			return ATTRIBUTE_SYMBOL;
		}
		else if(attributeName.equals("scheduleBb")) {
			return ATTRIBUTE_SCHEDULE_B;
		}
		else if(attributeName.equals("scheduleBbDesc")) {
			return ATTRIBUTE_SCHEDULE_B_DESC;
		}
		else if(attributeName.equals("eccn")) {
			return ATTRIBUTE_ECCN;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DotPartMaterialViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DotPartMaterialViewBean dotPartMaterialViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("partId", "SearchCriterion.EQUALS",
			"" + dotPartMaterialViewBean.getPartId());

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


	public int delete(DotPartMaterialViewBean dotPartMaterialViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("partId", "SearchCriterion.EQUALS",
			"" + dotPartMaterialViewBean.getPartId());

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
	public int insert(DotPartMaterialViewBean dotPartMaterialViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dotPartMaterialViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DotPartMaterialViewBean dotPartMaterialViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PART_ID + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_PKG_GT_RQ + "," +
			ATTRIBUTE_TEST_PKG_GT_RQ + "," +
			ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT + "," +
			ATTRIBUTE_ORM_D + "," +
			ATTRIBUTE_WEIGHT_LB + "," +
			ATTRIBUTE_MATERIAL_DESC + "," +
			ATTRIBUTE_HAZARD_CLASS + "," +
			ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS + "," +
			ATTRIBUTE_PACKING_GROUP + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "," +
			ATTRIBUTE_HAZMAT_ID + "," +
			ATTRIBUTE_PROPER_SHIPPING_NAME + "," +
			ATTRIBUTE_DRY_ICE + "," +
			ATTRIBUTE_ERG + "," +
			ATTRIBUTE_MARINE_POLLUTANT + "," +
			ATTRIBUTE_REPORTABLE_QUANTITY_LB + "," +
			ATTRIBUTE_SHIPPING_INFO_REMARKS + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_LAST_CHANGED_BY + "," +
			ATTRIBUTE_LAST_UPDATED + "," +
			ATTRIBUTE_HAZMAT_TECHNICAL_NAME + "," +
			ATTRIBUTE_SYMBOL + "," +
			ATTRIBUTE_SCHEDULE_B + "," +
			ATTRIBUTE_SCHEDULE_B_DESC + "," +
			ATTRIBUTE_ECCN + ")" +
			" values (" +
			dotPartMaterialViewBean.getPartId() + "," +
			dotPartMaterialViewBean.getMaterialId() + "," +
			dotPartMaterialViewBean.getItemId() + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getPkgGtRq()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getTestPkgGtRq()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getBulkPkgMarinePollutant()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getOrmD()) + "," +
			dotPartMaterialViewBean.getWeightLb() + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getMaterialDesc()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getHazardClass()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getSubsidiaryHazardClass()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getPackingGroup()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getIdentificationNumber()) + "," +
			dotPartMaterialViewBean.getHazmatId() + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getProperShippingName()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getDryIce()) + "," +
			dotPartMaterialViewBean.getErg() + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getMarinePollutant()) + "," +
			dotPartMaterialViewBean.getReportableQuantityLb() + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getShippingInfoRemarks()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getLastChangedBy()) + "," +
			DateHandler.getOracleToDateFunction(dotPartMaterialViewBean.getLastUpdated()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getHazmatTechnicalName()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getSymbol()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getScheduleB()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getScheduleBDesc()) + "," +
			SqlHandler.delimitString(dotPartMaterialViewBean.getEccn()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DotPartMaterialViewBean dotPartMaterialViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dotPartMaterialViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DotPartMaterialViewBean dotPartMaterialViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PART_ID + "=" + 
				StringHandler.nullIfZero(dotPartMaterialViewBean.getPartId()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" + 
				StringHandler.nullIfZero(dotPartMaterialViewBean.getMaterialId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(dotPartMaterialViewBean.getItemId()) + "," +
			ATTRIBUTE_PKG_GT_RQ + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getPkgGtRq()) + "," +
			ATTRIBUTE_TEST_PKG_GT_RQ + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getTestPkgGtRq()) + "," +
			ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getBulkPkgMarinePollutant()) + "," +
			ATTRIBUTE_ORM_D + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getOrmD()) + "," +
			ATTRIBUTE_WEIGHT_LB + "=" + 
				StringHandler.nullIfZero(dotPartMaterialViewBean.getWeightLb()) + "," +
			ATTRIBUTE_MATERIAL_DESC + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getMaterialDesc()) + "," +
			ATTRIBUTE_HAZARD_CLASS + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getHazardClass()) + "," +
			ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getSubsidiaryHazardClass()) + "," +
			ATTRIBUTE_PACKING_GROUP + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getPackingGroup()) + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getIdentificationNumber()) + "," +
			ATTRIBUTE_HAZMAT_ID + "=" + 
				StringHandler.nullIfZero(dotPartMaterialViewBean.getHazmatId()) + "," +
			ATTRIBUTE_PROPER_SHIPPING_NAME + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getProperShippingName()) + "," +
			ATTRIBUTE_DRY_ICE + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getDryIce()) + "," +
			ATTRIBUTE_ERG + "=" + 
				StringHandler.nullIfZero(dotPartMaterialViewBean.getErg()) + "," +
			ATTRIBUTE_MARINE_POLLUTANT + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getMarinePollutant()) + "," +
			ATTRIBUTE_REPORTABLE_QUANTITY_LB + "=" + 
				StringHandler.nullIfZero(dotPartMaterialViewBean.getReportableQuantityLb()) + "," +
			ATTRIBUTE_SHIPPING_INFO_REMARKS + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getShippingInfoRemarks()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getPackaging()) + "," +
			ATTRIBUTE_LAST_CHANGED_BY + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getLastChangedBy()) + "," +
			ATTRIBUTE_LAST_UPDATED + "=" + 
				DateHandler.getOracleToDateFunction(dotPartMaterialViewBean.getLastUpdated()) + "," +
			ATTRIBUTE_HAZMAT_TECHNICAL_NAME + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getHazmatTechnicalName()) + "," +
			ATTRIBUTE_SYMBOL + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getSymbol()) + "," +
			ATTRIBUTE_SCHEDULE_B + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getScheduleB()) + "," +
			ATTRIBUTE_SCHEDULE_B_DESC + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getScheduleBDesc()) + "," +
			ATTRIBUTE_ECCN + "=" + 
				SqlHandler.delimitString(dotPartMaterialViewBean.getEccn()) + " " +
			"where " + ATTRIBUTE_PART_ID + "=" +
				dotPartMaterialViewBean.getPartId();

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

		Collection dotPartMaterialViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DotPartMaterialViewBean dotPartMaterialViewBean = new DotPartMaterialViewBean();
			load(dataSetRow, dotPartMaterialViewBean);
			dotPartMaterialViewBeanColl.add(dotPartMaterialViewBean);
		}

		return dotPartMaterialViewBeanColl;
	}
}