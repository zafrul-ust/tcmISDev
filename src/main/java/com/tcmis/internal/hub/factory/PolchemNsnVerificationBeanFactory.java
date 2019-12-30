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
import com.tcmis.internal.hub.beans.PolchemNsnVerificationBean;


/******************************************************************************
 * CLASSNAME: PolchemNsnVerificationBeanFactory <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class PolchemNsnVerificationBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_NSN = "NSN";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_VMI_GROUPING = "VMI_GROUPING";
	public String ATTRIBUTE_GROSS_WEIGHT_LBS = "GROSS_WEIGHT_LBS";
	public String ATTRIBUTE_CUBIC_FEET = "CUBIC_FEET";
	public String ATTRIBUTE_UI = "UI";
	public String ATTRIBUTE_CONTAINERS_PER_UI = "CONTAINERS_PER_UI";
	public String ATTRIBUTE_MAX_NSN_PER_BOX = "MAX_NSN_PER_BOX";
	public String ATTRIBUTE_MAX_NSN_PER_PALLET = "MAX_NSN_PER_PALLET";
	public String ATTRIBUTE_EXTERNAL_CONTAINER = "EXTERNAL_CONTAINER";
	public String ATTRIBUTE_VERIFIED_BY = "VERIFIED_BY";
	public String ATTRIBUTE_VERIFIED_DATE = "VERIFIED_DATE";
	public String ATTRIBUTE_OCONUS_QTY_PER_PALLET = "OCONUS_QTY_PER_PALLET";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_QUANTITY_UNIT_PACK = "QUANTITY_UNIT_PACK";
	public String ATTRIBUTE_QUANTITY_WITHIN_UNIT_PACK = "QUANTITY_WITHIN_UNIT_PACK";
	public String ATTRIBUTE_QUANTITY_IN_UNIT_PACK = "QUANTITY_IN_UNIT_PACK";
	public String ATTRIBUTE_UNIT_PACK_UNIT = "UNIT_PACK_UNIT";
	public String ATTRIBUTE_UNIT_PACKAGE_TYPE = "UNIT_PACKAGE_TYPE";

	//table name
	public String TABLE = "POLCHEM_NSN_VERIFICATION";


	//constructor
	public PolchemNsnVerificationBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("nsn")) {
			return ATTRIBUTE_NSN;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("vmiGrouping")) {
			return ATTRIBUTE_VMI_GROUPING;
		}
		else if(attributeName.equals("grossWeightLbs")) {
			return ATTRIBUTE_GROSS_WEIGHT_LBS;
		}
		else if(attributeName.equals("cubicFeet")) {
			return ATTRIBUTE_CUBIC_FEET;
		}
		else if(attributeName.equals("ui")) {
			return ATTRIBUTE_UI;
		}
		else if(attributeName.equals("containersPerUi")) {
			return ATTRIBUTE_CONTAINERS_PER_UI;
		}
		else if(attributeName.equals("maxNsnPerBox")) {
			return ATTRIBUTE_MAX_NSN_PER_BOX;
		}
		else if(attributeName.equals("maxNsnPerPallet")) {
			return ATTRIBUTE_MAX_NSN_PER_PALLET;
		}
		else if(attributeName.equals("externalContainer")) {
			return ATTRIBUTE_EXTERNAL_CONTAINER;
		}
		else if(attributeName.equals("verifiedBy")) {
			return ATTRIBUTE_VERIFIED_BY;
		}
		else if(attributeName.equals("verifiedDate")) {
			return ATTRIBUTE_VERIFIED_DATE;
		}
		else if(attributeName.equals("oconusQtyPerPallet")) {
			return ATTRIBUTE_OCONUS_QTY_PER_PALLET;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("quantityUnitPack")) {
			return ATTRIBUTE_QUANTITY_UNIT_PACK;
		}
		else if(attributeName.equals("quantityWithinUnitPack")) {
			return ATTRIBUTE_QUANTITY_WITHIN_UNIT_PACK;
		}
		else if(attributeName.equals("quantityInUnitPack")) {
			return ATTRIBUTE_QUANTITY_IN_UNIT_PACK;
		}
		else if(attributeName.equals("unitPackUnit")) {
			return ATTRIBUTE_UNIT_PACK_UNIT;
		}
		else if(attributeName.equals("unitPackageType")) {
			return ATTRIBUTE_UNIT_PACKAGE_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PolchemNsnVerificationBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PolchemNsnVerificationBean polchemNsnVerificationBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("nsn", "SearchCriterion.EQUALS",
			"" + polchemNsnVerificationBean.getNsn());

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


	public int delete(PolchemNsnVerificationBean polchemNsnVerificationBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("nsn", "SearchCriterion.EQUALS",
			"" + polchemNsnVerificationBean.getNsn());

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
	public int insert(PolchemNsnVerificationBean polchemNsnVerificationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(polchemNsnVerificationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PolchemNsnVerificationBean polchemNsnVerificationBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_NSN + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_VMI_GROUPING + "," +
			ATTRIBUTE_GROSS_WEIGHT_LBS + "," +
			ATTRIBUTE_CUBIC_FEET + "," +
			ATTRIBUTE_UI + "," +
			ATTRIBUTE_CONTAINERS_PER_UI + "," +
			ATTRIBUTE_MAX_NSN_PER_BOX + "," +
			ATTRIBUTE_MAX_NSN_PER_PALLET + "," +
			ATTRIBUTE_EXTERNAL_CONTAINER + "," +
			ATTRIBUTE_VERIFIED_BY + "," +
			ATTRIBUTE_VERIFIED_DATE + "," +
			ATTRIBUTE_OCONUS_QTY_PER_PALLET + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_QUANTITY_UNIT_PACK + "," +
			ATTRIBUTE_QUANTITY_WITHIN_UNIT_PACK + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_PACK + "," +
			ATTRIBUTE_UNIT_PACK_UNIT + "," +
			ATTRIBUTE_UNIT_PACKAGE_TYPE + ")" +
			" values (" +
			SqlHandler.delimitString(polchemNsnVerificationBean.getNsn()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getPartShortName()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getVmiGrouping()) + "," +
			polchemNsnVerificationBean.getGrossWeightLbs() + "," +
			polchemNsnVerificationBean.getCubicFeet() + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getUi()) + "," +
			polchemNsnVerificationBean.getContainersPerUi() + "," +
			polchemNsnVerificationBean.getMaxNsnPerBox() + "," +
			polchemNsnVerificationBean.getMaxNsnPerPallet() + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getExternalContainer()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getVerifiedBy()) + "," +
			DateHandler.getOracleToDateFunction(polchemNsnVerificationBean.getVerifiedDate()) + "," +
			polchemNsnVerificationBean.getOconusQtyPerPallet() + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getStatus()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getQuantityUnitPack()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getQuantityWithinUnitPack()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getQuantityInUnitPack()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getUnitPackUnit()) + "," +
			SqlHandler.delimitString(polchemNsnVerificationBean.getUnitPackageType()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PolchemNsnVerificationBean polchemNsnVerificationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(polchemNsnVerificationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PolchemNsnVerificationBean polchemNsnVerificationBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_NSN + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getNsn()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getPartShortName()) + "," +
			ATTRIBUTE_VMI_GROUPING + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getVmiGrouping()) + "," +
			ATTRIBUTE_GROSS_WEIGHT_LBS + "=" + 
				StringHandler.nullIfZero(polchemNsnVerificationBean.getGrossWeightLbs()) + "," +
			ATTRIBUTE_CUBIC_FEET + "=" + 
				StringHandler.nullIfZero(polchemNsnVerificationBean.getCubicFeet()) + "," +
			ATTRIBUTE_UI + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getUi()) + "," +
			ATTRIBUTE_CONTAINERS_PER_UI + "=" + 
				StringHandler.nullIfZero(polchemNsnVerificationBean.getContainersPerUi()) + "," +
			ATTRIBUTE_MAX_NSN_PER_BOX + "=" + 
				StringHandler.nullIfZero(polchemNsnVerificationBean.getMaxNsnPerBox()) + "," +
			ATTRIBUTE_MAX_NSN_PER_PALLET + "=" + 
				StringHandler.nullIfZero(polchemNsnVerificationBean.getMaxNsnPerPallet()) + "," +
			ATTRIBUTE_EXTERNAL_CONTAINER + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getExternalContainer()) + "," +
			ATTRIBUTE_VERIFIED_BY + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getVerifiedBy()) + "," +
			ATTRIBUTE_VERIFIED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(polchemNsnVerificationBean.getVerifiedDate()) + "," +
			ATTRIBUTE_OCONUS_QTY_PER_PALLET + "=" + 
				StringHandler.nullIfZero(polchemNsnVerificationBean.getOconusQtyPerPallet()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getStatus()) + "," +
			ATTRIBUTE_QUANTITY_UNIT_PACK + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getQuantityUnitPack()) + "," +
			ATTRIBUTE_QUANTITY_WITHIN_UNIT_PACK + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getQuantityWithinUnitPack()) + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_PACK + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getQuantityInUnitPack()) + "," +
			ATTRIBUTE_UNIT_PACK_UNIT + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getUnitPackUnit()) + "," +
			ATTRIBUTE_UNIT_PACKAGE_TYPE + "=" + 
				SqlHandler.delimitString(polchemNsnVerificationBean.getUnitPackageType()) + " " +
			"where " + ATTRIBUTE_NSN + "=" +
				polchemNsnVerificationBean.getNsn();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		return select(criteria,null);

		}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection polchemNsnVerificationBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PolchemNsnVerificationBean polchemNsnVerificationBean = new PolchemNsnVerificationBean();
			load(dataSetRow, polchemNsnVerificationBean);
			polchemNsnVerificationBeanColl.add(polchemNsnVerificationBean);
		}

		return polchemNsnVerificationBeanColl;
	}
}