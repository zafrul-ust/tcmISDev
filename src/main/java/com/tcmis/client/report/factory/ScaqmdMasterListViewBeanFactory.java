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
import com.tcmis.client.report.beans.ScaqmdMasterListViewBean;


/******************************************************************************
 * CLASSNAME: ScaqmdMasterListViewBeanFactory <br>
 * @version: 1.0, Dec 16, 2005 <br>
 *****************************************************************************/


public class ScaqmdMasterListViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DATE_ADDED_TO_INVENTORY = "DATE_ADDED_TO_INVENTORY";
	public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_COMPONENT = "COMPONENT";
	public String ATTRIBUTE_MATERIAL_CATEGORY = "MATERIAL_CATEGORY";
	public String ATTRIBUTE_PRODUCT_ID_M_AND_E_NO = "PRODUCT_ID_M_AND_E_NO";
	public String ATTRIBUTE_VOC_COATING_LB_PER_GAL = "VOC_COATING_LB_PER_GAL";
	public String ATTRIBUTE_VOC_MATL_LB_PER_GAL = "VOC_MATL_LB_PER_GAL";
	public String ATTRIBUTE_MIXING_RATIO = "MIXING_RATIO";
	public String ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG = "VOC_COMP_VAPOR_PRESSURE_MMHG";
	public String ATTRIBUTE_FOR_USE_BY = "FOR_USE_BY";
	public String ATTRIBUTE_ACTIVITY_OR_SUBSTRATE_DESC = "ACTIVITY_OR_SUBSTRATE_DESC";
	public String ATTRIBUTE_APPLICABLE_DISTRICT_RULE = "APPLICABLE_DISTRICT_RULE";
	public String ATTRIBUTE_CATEGORY_COMMENT = "CATEGORY_COMMENT";
	public String ATTRIBUTE_RULE_SUBCATEGORY = "RULE_SUBCATEGORY";

	//table name
	public String TABLE = "SCAQMD_MASTER_LIST_VIEW";


	//constructor
	public ScaqmdMasterListViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("dateAddedToInventory")) {
			return ATTRIBUTE_DATE_ADDED_TO_INVENTORY;
		}
		else if(attributeName.equals("manufacturer")) {
			return ATTRIBUTE_MANUFACTURER;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("component")) {
			return ATTRIBUTE_COMPONENT;
		}
		else if(attributeName.equals("materialCategory")) {
			return ATTRIBUTE_MATERIAL_CATEGORY;
		}
		else if(attributeName.equals("productIdMmAndEeNo")) {
			return ATTRIBUTE_PRODUCT_ID_M_AND_E_NO;
		}
		else if(attributeName.equals("vocCoatingLbPerGal")) {
			return ATTRIBUTE_VOC_COATING_LB_PER_GAL;
		}
		else if(attributeName.equals("vocMatlLbPerGal")) {
			return ATTRIBUTE_VOC_MATL_LB_PER_GAL;
		}
		else if(attributeName.equals("mixingRatio")) {
			return ATTRIBUTE_MIXING_RATIO;
		}
		else if(attributeName.equals("vocCompVaporPressureMmhg")) {
			return ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG;
		}
		else if(attributeName.equals("forUseBy")) {
			return ATTRIBUTE_FOR_USE_BY;
		}
		else if(attributeName.equals("activityOrSubstrateDesc")) {
			return ATTRIBUTE_ACTIVITY_OR_SUBSTRATE_DESC;
		}
		else if(attributeName.equals("applicableDistrictRule")) {
			return ATTRIBUTE_APPLICABLE_DISTRICT_RULE;
		}
		else if(attributeName.equals("categoryComment")) {
			return ATTRIBUTE_CATEGORY_COMMENT;
		}
		else if(attributeName.equals("ruleSubcategory")) {
			return ATTRIBUTE_RULE_SUBCATEGORY;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ScaqmdMasterListViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ScaqmdMasterListViewBean scaqmdMasterListViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dateAddedToInventory", "SearchCriterion.EQUALS",
			"" + scaqmdMasterListViewBean.getDateAddedToInventory());

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


	public int delete(ScaqmdMasterListViewBean scaqmdMasterListViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dateAddedToInventory", "SearchCriterion.EQUALS",
			"" + scaqmdMasterListViewBean.getDateAddedToInventory());

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
	public int insert(ScaqmdMasterListViewBean scaqmdMasterListViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(scaqmdMasterListViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ScaqmdMasterListViewBean scaqmdMasterListViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DATE_ADDED_TO_INVENTORY + "," +
			ATTRIBUTE_MANUFACTURER + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_COMPONENT + "," +
			ATTRIBUTE_MATERIAL_CATEGORY + "," +
			ATTRIBUTE_PRODUCT_ID_M_AND_E_NO + "," +
			ATTRIBUTE_VOC_COATING_LB_PER_GAL + "," +
			ATTRIBUTE_VOC_MATL_LB_PER_GAL + "," +
			ATTRIBUTE_MIXING_RATIO + "," +
			ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG + "," +
			ATTRIBUTE_FOR_USE_BY + "," +
			ATTRIBUTE_ACTIVITY_OR_SUBSTRATE_DESC + "," +
			ATTRIBUTE_APPLICABLE_DISTRICT_RULE + "," +
			ATTRIBUTE_CATEGORY_COMMENT + "," +
			ATTRIBUTE_RULE_SUBCATEGORY + ")" +
			" values (" +
			DateHandler.getOracleToDateFunction(scaqmdMasterListViewBean.getDateAddedToInventory()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getManufacturer()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getPartDescription()) + "," +
			scaqmdMasterListViewBean.getComponent() + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getMaterialCategory()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getProductIdMAndENo()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getVocCoatingLbPerGal()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getVocMatlLbPerGal()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getMixingRatio()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getVocCompVaporPressureMmhg()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getForUseBy()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getActivityOrSubstrateDesc()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getApplicableDistrictRule()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getCategoryComment()) + "," +
			SqlHandler.delimitString(scaqmdMasterListViewBean.getRuleSubcategory()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ScaqmdMasterListViewBean scaqmdMasterListViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(scaqmdMasterListViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ScaqmdMasterListViewBean scaqmdMasterListViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_DATE_ADDED_TO_INVENTORY + "=" +
				DateHandler.getOracleToDateFunction(scaqmdMasterListViewBean.getDateAddedToInventory()) + "," +
			ATTRIBUTE_MANUFACTURER + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getManufacturer()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getPartDescription()) + "," +
			ATTRIBUTE_COMPONENT + "=" +
				StringHandler.nullIfZero(scaqmdMasterListViewBean.getComponent()) + "," +
			ATTRIBUTE_MATERIAL_CATEGORY + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getMaterialCategory()) + "," +
			ATTRIBUTE_PRODUCT_ID_M_AND_E_NO + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getProductIdMAndENo()) + "," +
			ATTRIBUTE_VOC_COATING_LB_PER_GAL + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getVocCoatingLbPerGal()) + "," +
			ATTRIBUTE_VOC_MATL_LB_PER_GAL + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getVocMatlLbPerGal()) + "," +
			ATTRIBUTE_MIXING_RATIO + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getMixingRatio()) + "," +
			ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getVocCompVaporPressureMmhg()) + "," +
			ATTRIBUTE_FOR_USE_BY + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getForUseBy()) + "," +
			ATTRIBUTE_ACTIVITY_OR_SUBSTRATE_DESC + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getActivityOrSubstrateDesc()) + "," +
			ATTRIBUTE_APPLICABLE_DISTRICT_RULE + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getApplicableDistrictRule()) + "," +
			ATTRIBUTE_CATEGORY_COMMENT + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getCategoryComment()) + "," +
			ATTRIBUTE_RULE_SUBCATEGORY + "=" +
				SqlHandler.delimitString(scaqmdMasterListViewBean.getRuleSubcategory()) + " " +
			"where " + ATTRIBUTE_DATE_ADDED_TO_INVENTORY + "=" +
				scaqmdMasterListViewBean.getDateAddedToInventory();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection scaqmdMasterListViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ScaqmdMasterListViewBean scaqmdMasterListViewBean = new ScaqmdMasterListViewBean();
			load(dataSetRow, scaqmdMasterListViewBean);
			scaqmdMasterListViewBeanColl.add(scaqmdMasterListViewBean);
		}

		return scaqmdMasterListViewBeanColl;
	}
}