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
import com.tcmis.client.report.beans.ScaqmdRule109ViewBean;


/******************************************************************************
 * CLASSNAME: ScaqmdRule109ViewBeanFactory <br>
 * @version: 1.0, Dec 16, 2005 <br>
 *****************************************************************************/


public class ScaqmdRule109ViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_KEY_ID = "KEY_ID";
	public String ATTRIBUTE_PERMIT_NO = "PERMIT_NO";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_SHIPMENT_DATE = "SHIPMENT_DATE";
	public String ATTRIBUTE_FACILITY_GROUP_ID = "FACILITY_GROUP_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_FOR_USE_BY = "FOR_USE_BY";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_AMOUNT_USED = "AMOUNT_USED";
	public String ATTRIBUTE_UNITS = "UNITS";
	public String ATTRIBUTE_VOC_COATING_LB_PER_GAL = "VOC_COATING_LB_PER_GAL";
	public String ATTRIBUTE_VOC_MATL_LB_PER_GAL = "VOC_MATL_LB_PER_GAL";
	public String ATTRIBUTE_VOC_EMISSIONS_LB = "VOC_EMISSIONS_LB";
	public String ATTRIBUTE_MIXING_RATIO = "MIXING_RATIO";
	public String ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG = "VOC_COMP_VAPOR_PRESSURE_MMHG";

	//table name
	public String TABLE = "SCAQMD_RULE_109_VIEW";


	//constructor
	public ScaqmdRule109ViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("keyId")) {
			return ATTRIBUTE_KEY_ID;
		}
		else if(attributeName.equals("permitNo")) {
			return ATTRIBUTE_PERMIT_NO;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("shipmentDate")) {
			return ATTRIBUTE_SHIPMENT_DATE;
		}
		else if(attributeName.equals("facilityGroupId")) {
			return ATTRIBUTE_FACILITY_GROUP_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
                else if(attributeName.equals("partDescription")) {
                        return ATTRIBUTE_PART_DESCRIPTION;
                }
		else if(attributeName.equals("forUseBy")) {
			return ATTRIBUTE_FOR_USE_BY;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("amountUsed")) {
			return ATTRIBUTE_AMOUNT_USED;
		}
		else if(attributeName.equals("units")) {
			return ATTRIBUTE_UNITS;
		}
		else if(attributeName.equals("vocCoatingLbPerGal")) {
			return ATTRIBUTE_VOC_COATING_LB_PER_GAL;
		}
		else if(attributeName.equals("vocMatlLbPerGal")) {
			return ATTRIBUTE_VOC_MATL_LB_PER_GAL;
		}
		else if(attributeName.equals("vocEmissionsLb")) {
			return ATTRIBUTE_VOC_EMISSIONS_LB;
		}
		else if(attributeName.equals("mixingRatio")) {
			return ATTRIBUTE_MIXING_RATIO;
		}
		else if(attributeName.equals("vocCompVaporPressureMmhg")) {
			return ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ScaqmdRule109ViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ScaqmdRule109ViewBean scaqmdRule109ViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("keyId", "SearchCriterion.EQUALS",
			"" + scaqmdRule109ViewBean.getKeyId());

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


	public int delete(ScaqmdRule109ViewBean scaqmdRule109ViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("keyId", "SearchCriterion.EQUALS",
			"" + scaqmdRule109ViewBean.getKeyId());

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
	public int insert(ScaqmdRule109ViewBean scaqmdRule109ViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(scaqmdRule109ViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ScaqmdRule109ViewBean scaqmdRule109ViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_KEY_ID + "," +
			ATTRIBUTE_PERMIT_NO + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_SHIPMENT_DATE + "," +
			ATTRIBUTE_FACILITY_GROUP_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_FOR_USE_BY + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_AMOUNT_USED + "," +
			ATTRIBUTE_UNITS + "," +
			ATTRIBUTE_VOC_COATING_LB_PER_GAL + "," +
			ATTRIBUTE_VOC_MATL_LB_PER_GAL + "," +
			ATTRIBUTE_VOC_EMISSIONS_LB + "," +
			ATTRIBUTE_MIXING_RATIO + "," +
			ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG + ")" +
			" values (" +
			scaqmdRule109ViewBean.getKeyId() + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getPermitNo()) + "," +
			scaqmdRule109ViewBean.getItemId() + "," +
			DateHandler.getOracleToDateFunction(scaqmdRule109ViewBean.getShipmentDate()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getFacilityGroupId()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getForUseBy()) + "," +
			scaqmdRule109ViewBean.getMaterialId() + "," +
			DateHandler.getOracleToDateFunction(scaqmdRule109ViewBean.getRevisionDate()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getAmountUsed()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getUnits()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getVocCoatingLbPerGal()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getVocMatlLbPerGal()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getVocEmissionsLb()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getMixingRatio()) + "," +
			SqlHandler.delimitString(scaqmdRule109ViewBean.getVocCompVaporPressureMmhg()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ScaqmdRule109ViewBean scaqmdRule109ViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(scaqmdRule109ViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ScaqmdRule109ViewBean scaqmdRule109ViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_KEY_ID + "=" +
				StringHandler.nullIfZero(scaqmdRule109ViewBean.getKeyId()) + "," +
			ATTRIBUTE_PERMIT_NO + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getPermitNo()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(scaqmdRule109ViewBean.getItemId()) + "," +
			ATTRIBUTE_SHIPMENT_DATE + "=" +
				DateHandler.getOracleToDateFunction(scaqmdRule109ViewBean.getShipmentDate()) + "," +
			ATTRIBUTE_FACILITY_GROUP_ID + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getFacilityGroupId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_FOR_USE_BY + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getForUseBy()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" +
				StringHandler.nullIfZero(scaqmdRule109ViewBean.getMaterialId()) + "," +
			ATTRIBUTE_REVISION_DATE + "=" +
				DateHandler.getOracleToDateFunction(scaqmdRule109ViewBean.getRevisionDate()) + "," +
			ATTRIBUTE_AMOUNT_USED + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getAmountUsed()) + "," +
			ATTRIBUTE_UNITS + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getUnits()) + "," +
			ATTRIBUTE_VOC_COATING_LB_PER_GAL + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getVocCoatingLbPerGal()) + "," +
			ATTRIBUTE_VOC_MATL_LB_PER_GAL + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getVocMatlLbPerGal()) + "," +
			ATTRIBUTE_VOC_EMISSIONS_LB + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getVocEmissionsLb()) + "," +
			ATTRIBUTE_MIXING_RATIO + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getMixingRatio()) + "," +
			ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG + "=" +
				SqlHandler.delimitString(scaqmdRule109ViewBean.getVocCompVaporPressureMmhg()) + " " +
			"where " + ATTRIBUTE_KEY_ID + "=" +
				scaqmdRule109ViewBean.getKeyId();

		return new SqlManager().update(conn, query);
	}
*/

       //select
       public Collection select(String beginDate, String endDate, String workGroup)
               throws BaseException {

               Connection connection = null;
               Collection c = null;
               try {
                       connection = this.getDbManager().getConnection();
                       c = select(beginDate, endDate, connection, workGroup);
               }
               finally {
                       this.getDbManager().returnConnection(connection);
               }
               return c;
       }
       public Collection select(String beginDate, String endDate, Connection conn, String workGroup)
               throws BaseException {

               Collection scaqmdRule109ViewBeanColl = new Vector();

               String query = "select * from " + TABLE + " where shipment_date >= to_date('"+beginDate+"','mm/dd/yyyy')"+
                              " and shipment_date < to_date('"+endDate+"','mm/dd/yyyy')+1";
               if ("GSE".equalsIgnoreCase(workGroup)) {
                 query += " and facility_group_id = 'TOGSE'";
               }else if ("ACMX".equalsIgnoreCase(workGroup)) {
                 query += " and facility_group_id = 'ACMX'";
               }else {
                 //no restriction
               }
               DataSet dataSet = new SqlManager().select(conn, query);

               Iterator dataIter = dataSet.iterator();

               while (dataIter.hasNext()) {
                       DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                       ScaqmdRule109ViewBean scaqmdRule109ViewBean = new ScaqmdRule109ViewBean();
                       load(dataSetRow, scaqmdRule109ViewBean);
                       scaqmdRule109ViewBeanColl.add(scaqmdRule109ViewBean);
               }

               return scaqmdRule109ViewBeanColl;
       }

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

		Collection scaqmdRule109ViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ScaqmdRule109ViewBean scaqmdRule109ViewBean = new ScaqmdRule109ViewBean();
			load(dataSetRow, scaqmdRule109ViewBean);
			scaqmdRule109ViewBeanColl.add(scaqmdRule109ViewBean);
		}

		return scaqmdRule109ViewBeanColl;
	}
}
