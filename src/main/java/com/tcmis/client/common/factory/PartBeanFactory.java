package com.tcmis.client.common.factory;


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
import com.tcmis.client.common.beans.PartBean;


/******************************************************************************
 * CLASSNAME: PartBeanFactory <br>
 * @version: 1.0, Jan 14, 2008 <br>
 *****************************************************************************/


public class PartBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_GRADE = "GRADE";
	public String ATTRIBUTE_PKG_STYLE = "PKG_STYLE";
	public String ATTRIBUTE_PART_SIZE = "PART_SIZE";
	public String ATTRIBUTE_SIZE_UNIT = "SIZE_UNIT";
	public String ATTRIBUTE_TECH_SPEC = "TECH_SPEC";
	public String ATTRIBUTE_REMARK = "REMARK";
	public String ATTRIBUTE_AIRCRAFT = "AIRCRAFT";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_PACKING_INSTR_CODE = "PACKING_INSTR_CODE";
	public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
	public String ATTRIBUTE_PART_DESC = "PART_DESC";
	public String ATTRIBUTE_NET_WT_UNIT = "NET_WT_UNIT";
	public String ATTRIBUTE_NET_WT = "NET_WT";
	public String ATTRIBUTE_CASE_QTY = "CASE_QTY";
	public String ATTRIBUTE_DIMENSION = "DIMENSION";
	public String ATTRIBUTE_RECERT = "RECERT";
	public String ATTRIBUTE_STOCKING_TYPE = "STOCKING_TYPE";
	public String ATTRIBUTE_SIZE_VERIFIED = "SIZE_VERIFIED";
	public String ATTRIBUTE_COMPONENTS_PER_ITEM = "COMPONENTS_PER_ITEM";
	public String ATTRIBUTE_SIZE_VARIES = "SIZE_VARIES";
	public String ATTRIBUTE_ITEM_VERIFIED = "ITEM_VERIFIED";
	public String ATTRIBUTE_ITEM_VERIFIED_BY = "ITEM_VERIFIED_BY";
	public String ATTRIBUTE_DATE_ITEM_VERIFIED = "DATE_ITEM_VERIFIED";
	public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
	public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
	public String ATTRIBUTE_ORM_D = "ORM_D";
	public String ATTRIBUTE_PKG_GT_RQ = "PKG_GT_RQ";
	public String ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT = "BULK_PKG_MARINE_POLLUTANT";
	public String ATTRIBUTE_SHIP_CHANGE_ID = "SHIP_CHANGE_ID";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_MIN_TEMP = "MIN_TEMP";
	public String ATTRIBUTE_MAX_TEMP = "MAX_TEMP";
	public String ATTRIBUTE_TEMP_UNITS = "TEMP_UNITS";
	public String ATTRIBUTE_COMPONENT_ITEM_ID = "COMPONENT_ITEM_ID";
	public String ATTRIBUTE_TEMP_VERIFIED = "TEMP_VERIFIED";
	public String ATTRIBUTE_TEMP_VERIFIED_BY = "TEMP_VERIFIED_BY";
	public String ATTRIBUTE_DATE_TEMP_VERIFIED = "DATE_TEMP_VERIFIED";

	//table name
	public String TABLE = "PART";


	//constructor
	public PartBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("grade")) {
			return ATTRIBUTE_GRADE;
		}
		else if(attributeName.equals("pkgStyle")) {
			return ATTRIBUTE_PKG_STYLE;
		}
		else if(attributeName.equals("partSize")) {
			return ATTRIBUTE_PART_SIZE;
		}
		else if(attributeName.equals("sizeUnit")) {
			return ATTRIBUTE_SIZE_UNIT;
		}
		else if(attributeName.equals("techSpec")) {
			return ATTRIBUTE_TECH_SPEC;
		}
		else if(attributeName.equals("remark")) {
			return ATTRIBUTE_REMARK;
		}
		else if(attributeName.equals("aircraft")) {
			return ATTRIBUTE_AIRCRAFT;
		}
		else if(attributeName.equals("mfgPartNo")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if(attributeName.equals("packingInstrCode")) {
			return ATTRIBUTE_PACKING_INSTR_CODE;
		}
		else if(attributeName.equals("storageTemp")) {
			return ATTRIBUTE_STORAGE_TEMP;
		}
		else if(attributeName.equals("partDesc")) {
			return ATTRIBUTE_PART_DESC;
		}
		else if(attributeName.equals("netWtUnit")) {
			return ATTRIBUTE_NET_WT_UNIT;
		}
		else if(attributeName.equals("netWt")) {
			return ATTRIBUTE_NET_WT;
		}
		else if(attributeName.equals("caseQty")) {
			return ATTRIBUTE_CASE_QTY;
		}
		else if(attributeName.equals("dimension")) {
			return ATTRIBUTE_DIMENSION;
		}
		else if(attributeName.equals("recert")) {
			return ATTRIBUTE_RECERT;
		}
		else if(attributeName.equals("stockingType")) {
			return ATTRIBUTE_STOCKING_TYPE;
		}
		else if(attributeName.equals("sizeVerified")) {
			return ATTRIBUTE_SIZE_VERIFIED;
		}
		else if(attributeName.equals("componentsPerItem")) {
			return ATTRIBUTE_COMPONENTS_PER_ITEM;
		}
		else if(attributeName.equals("sizeVaries")) {
			return ATTRIBUTE_SIZE_VARIES;
		}
		else if(attributeName.equals("itemVerified")) {
			return ATTRIBUTE_ITEM_VERIFIED;
		}
		else if(attributeName.equals("itemVerifiedBy")) {
			return ATTRIBUTE_ITEM_VERIFIED_BY;
		}
		else if(attributeName.equals("dateItemVerified")) {
			return ATTRIBUTE_DATE_ITEM_VERIFIED;
		}
		else if(attributeName.equals("shelfLifeDays")) {
			return ATTRIBUTE_SHELF_LIFE_DAYS;
		}
		else if(attributeName.equals("shelfLifeBasis")) {
			return ATTRIBUTE_SHELF_LIFE_BASIS;
		}
		else if(attributeName.equals("ormDd")) {
			return ATTRIBUTE_ORM_D;
		}
		else if(attributeName.equals("pkgGtRq")) {
			return ATTRIBUTE_PKG_GT_RQ;
		}
		else if(attributeName.equals("bulkPkgMarinePollutant")) {
			return ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT;
		}
		else if(attributeName.equals("shipChangeId")) {
			return ATTRIBUTE_SHIP_CHANGE_ID;
		}
		else if(attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if(attributeName.equals("minTemp")) {
			return ATTRIBUTE_MIN_TEMP;
		}
		else if(attributeName.equals("maxTemp")) {
			return ATTRIBUTE_MAX_TEMP;
		}
		else if(attributeName.equals("tempUnits")) {
			return ATTRIBUTE_TEMP_UNITS;
		}
		else if(attributeName.equals("componentItemId")) {
			return ATTRIBUTE_COMPONENT_ITEM_ID;
		}
		else if(attributeName.equals("tempVerified")) {
			return ATTRIBUTE_TEMP_VERIFIED;
		}
		else if(attributeName.equals("tempVerifiedBy")) {
			return ATTRIBUTE_TEMP_VERIFIED_BY;
		}
		else if(attributeName.equals("dateTempVerified")) {
			return ATTRIBUTE_DATE_TEMP_VERIFIED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PartBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PartBean partBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("partId", "SearchCriterion.EQUALS",
			"" + partBean.getPartId());

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


	public int delete(PartBean partBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("partId", "SearchCriterion.EQUALS",
			"" + partBean.getPartId());

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
	public int insert(PartBean partBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(partBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PartBean partBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PART_ID + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_GRADE + "," +
			ATTRIBUTE_PKG_STYLE + "," +
			ATTRIBUTE_PART_SIZE + "," +
			ATTRIBUTE_SIZE_UNIT + "," +
			ATTRIBUTE_TECH_SPEC + "," +
			ATTRIBUTE_REMARK + "," +
			ATTRIBUTE_AIRCRAFT + "," +
			ATTRIBUTE_MFG_PART_NO + "," +
			ATTRIBUTE_PACKING_INSTR_CODE + "," +
			ATTRIBUTE_STORAGE_TEMP + "," +
			ATTRIBUTE_PART_DESC + "," +
			ATTRIBUTE_NET_WT_UNIT + "," +
			ATTRIBUTE_NET_WT + "," +
			ATTRIBUTE_CASE_QTY + "," +
			ATTRIBUTE_DIMENSION + "," +
			ATTRIBUTE_RECERT + "," +
			ATTRIBUTE_STOCKING_TYPE + "," +
			ATTRIBUTE_SIZE_VERIFIED + "," +
			ATTRIBUTE_COMPONENTS_PER_ITEM + "," +
			ATTRIBUTE_SIZE_VARIES + "," +
			ATTRIBUTE_ITEM_VERIFIED + "," +
			ATTRIBUTE_ITEM_VERIFIED_BY + "," +
			ATTRIBUTE_DATE_ITEM_VERIFIED + "," +
			ATTRIBUTE_SHELF_LIFE_DAYS + "," +
			ATTRIBUTE_SHELF_LIFE_BASIS + "," +
			ATTRIBUTE_ORM_D + "," +
			ATTRIBUTE_PKG_GT_RQ + "," +
			ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT + "," +
			ATTRIBUTE_SHIP_CHANGE_ID + "," +
			ATTRIBUTE_INSERT_DATE + "," +
			ATTRIBUTE_MIN_TEMP + "," +
			ATTRIBUTE_MAX_TEMP + "," +
			ATTRIBUTE_TEMP_UNITS + "," +
			ATTRIBUTE_COMPONENT_ITEM_ID + "," +
			ATTRIBUTE_TEMP_VERIFIED + "," +
			ATTRIBUTE_TEMP_VERIFIED_BY + "," +
			ATTRIBUTE_DATE_TEMP_VERIFIED + ")" +
			" values (" +
			partBean.getPartId() + "," +
			partBean.getMaterialId() + "," +
			partBean.getItemId() + "," +
			SqlHandler.delimitString(partBean.getGrade()) + "," +
			SqlHandler.delimitString(partBean.getPkgStyle()) + "," +
			partBean.getPartSize() + "," +
			SqlHandler.delimitString(partBean.getSizeUnit()) + "," +
			SqlHandler.delimitString(partBean.getTechSpec()) + "," +
			SqlHandler.delimitString(partBean.getRemark()) + "," +
			SqlHandler.delimitString(partBean.getAircraft()) + "," +
			SqlHandler.delimitString(partBean.getMfgPartNo()) + "," +
			SqlHandler.delimitString(partBean.getPackingInstrCode()) + "," +
			SqlHandler.delimitString(partBean.getStorageTemp()) + "," +
			SqlHandler.delimitString(partBean.getPartDesc()) + "," +
			SqlHandler.delimitString(partBean.getNetWtUnit()) + "," +
			partBean.getNetWt() + "," +
			partBean.getCaseQty() + "," +
			SqlHandler.delimitString(partBean.getDimension()) + "," +
			SqlHandler.delimitString(partBean.getRecert()) + "," +
			SqlHandler.delimitString(partBean.getStockingType()) + "," +
			SqlHandler.delimitString(partBean.getSizeVerified()) + "," +
			partBean.getComponentsPerItem() + "," +
			SqlHandler.delimitString(partBean.getSizeVaries()) + "," +
			SqlHandler.delimitString(partBean.getItemVerified()) + "," +
			SqlHandler.delimitString(partBean.getItemVerifiedBy()) + "," +
			DateHandler.getOracleToDateFunction(partBean.getDateItemVerified()) + "," +
			partBean.getShelfLifeDays() + "," +
			SqlHandler.delimitString(partBean.getShelfLifeBasis()) + "," +
			SqlHandler.delimitString(partBean.getOrmD()) + "," +
			SqlHandler.delimitString(partBean.getPkgGtRq()) + "," +
			SqlHandler.delimitString(partBean.getBulkPkgMarinePollutant()) + "," +
			partBean.getShipChangeId() + "," +
			DateHandler.getOracleToDateFunction(partBean.getInsertDate()) + "," +
			partBean.getMinTemp() + "," +
			partBean.getMaxTemp() + "," +
			SqlHandler.delimitString(partBean.getTempUnits()) + "," +
			partBean.getComponentItemId() + "," +
			SqlHandler.delimitString(partBean.getTempVerified()) + "," +
			SqlHandler.delimitString(partBean.getTempVerifiedBy()) + "," +
			DateHandler.getOracleToDateFunction(partBean.getDateTempVerified()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PartBean partBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(partBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PartBean partBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PART_ID + "=" + 
				StringHandler.nullIfZero(partBean.getPartId()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" + 
				StringHandler.nullIfZero(partBean.getMaterialId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(partBean.getItemId()) + "," +
			ATTRIBUTE_GRADE + "=" + 
				SqlHandler.delimitString(partBean.getGrade()) + "," +
			ATTRIBUTE_PKG_STYLE + "=" + 
				SqlHandler.delimitString(partBean.getPkgStyle()) + "," +
			ATTRIBUTE_PART_SIZE + "=" + 
				StringHandler.nullIfZero(partBean.getPartSize()) + "," +
			ATTRIBUTE_SIZE_UNIT + "=" + 
				SqlHandler.delimitString(partBean.getSizeUnit()) + "," +
			ATTRIBUTE_TECH_SPEC + "=" + 
				SqlHandler.delimitString(partBean.getTechSpec()) + "," +
			ATTRIBUTE_REMARK + "=" + 
				SqlHandler.delimitString(partBean.getRemark()) + "," +
			ATTRIBUTE_AIRCRAFT + "=" + 
				SqlHandler.delimitString(partBean.getAircraft()) + "," +
			ATTRIBUTE_MFG_PART_NO + "=" + 
				SqlHandler.delimitString(partBean.getMfgPartNo()) + "," +
			ATTRIBUTE_PACKING_INSTR_CODE + "=" + 
				SqlHandler.delimitString(partBean.getPackingInstrCode()) + "," +
			ATTRIBUTE_STORAGE_TEMP + "=" + 
				SqlHandler.delimitString(partBean.getStorageTemp()) + "," +
			ATTRIBUTE_PART_DESC + "=" + 
				SqlHandler.delimitString(partBean.getPartDesc()) + "," +
			ATTRIBUTE_NET_WT_UNIT + "=" + 
				SqlHandler.delimitString(partBean.getNetWtUnit()) + "," +
			ATTRIBUTE_NET_WT + "=" + 
				StringHandler.nullIfZero(partBean.getNetWt()) + "," +
			ATTRIBUTE_CASE_QTY + "=" + 
				StringHandler.nullIfZero(partBean.getCaseQty()) + "," +
			ATTRIBUTE_DIMENSION + "=" + 
				SqlHandler.delimitString(partBean.getDimension()) + "," +
			ATTRIBUTE_RECERT + "=" + 
				SqlHandler.delimitString(partBean.getRecert()) + "," +
			ATTRIBUTE_STOCKING_TYPE + "=" + 
				SqlHandler.delimitString(partBean.getStockingType()) + "," +
			ATTRIBUTE_SIZE_VERIFIED + "=" + 
				SqlHandler.delimitString(partBean.getSizeVerified()) + "," +
			ATTRIBUTE_COMPONENTS_PER_ITEM + "=" + 
				StringHandler.nullIfZero(partBean.getComponentsPerItem()) + "," +
			ATTRIBUTE_SIZE_VARIES + "=" + 
				SqlHandler.delimitString(partBean.getSizeVaries()) + "," +
			ATTRIBUTE_ITEM_VERIFIED + "=" + 
				SqlHandler.delimitString(partBean.getItemVerified()) + "," +
			ATTRIBUTE_ITEM_VERIFIED_BY + "=" + 
				SqlHandler.delimitString(partBean.getItemVerifiedBy()) + "," +
			ATTRIBUTE_DATE_ITEM_VERIFIED + "=" + 
				DateHandler.getOracleToDateFunction(partBean.getDateItemVerified()) + "," +
			ATTRIBUTE_SHELF_LIFE_DAYS + "=" + 
				StringHandler.nullIfZero(partBean.getShelfLifeDays()) + "," +
			ATTRIBUTE_SHELF_LIFE_BASIS + "=" + 
				SqlHandler.delimitString(partBean.getShelfLifeBasis()) + "," +
			ATTRIBUTE_ORM_D + "=" + 
				SqlHandler.delimitString(partBean.getOrmD()) + "," +
			ATTRIBUTE_PKG_GT_RQ + "=" + 
				SqlHandler.delimitString(partBean.getPkgGtRq()) + "," +
			ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT + "=" + 
				SqlHandler.delimitString(partBean.getBulkPkgMarinePollutant()) + "," +
			ATTRIBUTE_SHIP_CHANGE_ID + "=" + 
				StringHandler.nullIfZero(partBean.getShipChangeId()) + "," +
			ATTRIBUTE_INSERT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(partBean.getInsertDate()) + "," +
			ATTRIBUTE_MIN_TEMP + "=" + 
				StringHandler.nullIfZero(partBean.getMinTemp()) + "," +
			ATTRIBUTE_MAX_TEMP + "=" + 
				StringHandler.nullIfZero(partBean.getMaxTemp()) + "," +
			ATTRIBUTE_TEMP_UNITS + "=" + 
				SqlHandler.delimitString(partBean.getTempUnits()) + "," +
			ATTRIBUTE_COMPONENT_ITEM_ID + "=" + 
				StringHandler.nullIfZero(partBean.getComponentItemId()) + "," +
			ATTRIBUTE_TEMP_VERIFIED + "=" + 
				SqlHandler.delimitString(partBean.getTempVerified()) + "," +
			ATTRIBUTE_TEMP_VERIFIED_BY + "=" + 
				SqlHandler.delimitString(partBean.getTempVerifiedBy()) + "," +
			ATTRIBUTE_DATE_TEMP_VERIFIED + "=" + 
				DateHandler.getOracleToDateFunction(partBean.getDateTempVerified()) + " " +
			"where " + ATTRIBUTE_PART_ID + "=" +
				partBean.getPartId();

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

		Collection partBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PartBean partBean = new PartBean();
			load(dataSetRow, partBean);
			partBeanColl.add(partBean);
		}

		return partBeanColl;
	}

	public Collection selectMlItem(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectMlItem(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectMlItem(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection partBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PartBean partBean = new PartBean();
			load(dataSetRow, partBean);
			//clear grade and mfgPartNo
			partBean.setGrade("");
			partBean.setMfgPartNo("");
			partBeanColl.add(partBean);
		}

		return partBeanColl;
	}
}