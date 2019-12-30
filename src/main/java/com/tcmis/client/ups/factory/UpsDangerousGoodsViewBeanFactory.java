package com.tcmis.client.ups.factory;


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
import com.tcmis.client.ups.beans.UpsDangerousGoodsViewBean;


/******************************************************************************
 * CLASSNAME: UpsDangerousGoodsViewBeanFactory <br>
 * @version: 1.0, Jan 29, 2009 <br>
 *****************************************************************************/


public class UpsDangerousGoodsViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_REFERENCE_NUMBER = "REFERENCE_NUMBER";
	public String ATTRIBUTE_REGULATION_SET = "REGULATION_SET";
	public String ATTRIBUTE_REPORTABLE_QUANTITY = "REPORTABLE_QUANTITY";
	public String ATTRIBUTE_SHIPPING_NAME = "SHIPPING_NAME";
	public String ATTRIBUTE_TECHNICAL_NAME = "TECHNICAL_NAME";
	public String ATTRIBUTE_HAZARD_CLASS = "HAZARD_CLASS";
	public String ATTRIBUTE_SUBRISK_CLASS = "SUBRISK_CLASS";
	public String ATTRIBUTE_IDENTIFICATION_NUMBER = "IDENTIFICATION_NUMBER";
	public String ATTRIBUTE_PACKING_GROUP_NUMBER = "PACKING_GROUP_NUMBER";
	public String ATTRIBUTE_ADDITIONAL_DESC = "ADDITIONAL_DESC";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_OF_MEASURE = "UNIT_OF_MEASURE";
	public String ATTRIBUTE_PACKAGE_TYPE = "PACKAGE_TYPE";
	public String ATTRIBUTE_CUSTOM_PACKAGE_TYPE = "CUSTOM_PACKAGE_TYPE";
	public String ATTRIBUTE_PACKING_INSTRUCTIONS = "PACKING_INSTRUCTIONS";
	public String ATTRIBUTE_TRANSPORTATION_MODE = "TRANSPORTATION_MODE";
	public String ATTRIBUTE_LABEL_REQUIRED = "LABEL_REQUIRED";
	public String ATTRIBUTE_EMERGENCY_PHONE = "EMERGENCY_PHONE";
	public String ATTRIBUTE_DANGEROUS_GOODS_OPTION = "DANGEROUS_GOODS_OPTION";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_UPS_FORBIDDEN = "UPS_FORBIDDEN";
	public String ATTRIBUTE_DOT_LABEL_TYPE = "DOT_LABEL_TYPE";

	//table name
	public String TABLE = "UPS_DANGEROUS_GOODS_VIEW";


	//constructor
	public UpsDangerousGoodsViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("referenceNumber")) {
			return ATTRIBUTE_REFERENCE_NUMBER;
		}
		else if(attributeName.equals("regulationSet")) {
			return ATTRIBUTE_REGULATION_SET;
		}
		else if(attributeName.equals("reportableQuantity")) {
			return ATTRIBUTE_REPORTABLE_QUANTITY;
		}
		else if(attributeName.equals("shippingName")) {
			return ATTRIBUTE_SHIPPING_NAME;
		}
		else if(attributeName.equals("technicalName")) {
			return ATTRIBUTE_TECHNICAL_NAME;
		}
		else if(attributeName.equals("hazardClass")) {
			return ATTRIBUTE_HAZARD_CLASS;
		}
		else if(attributeName.equals("subriskClass")) {
			return ATTRIBUTE_SUBRISK_CLASS;
		}
		else if(attributeName.equals("identificationNumber")) {
			return ATTRIBUTE_IDENTIFICATION_NUMBER;
		}
		else if(attributeName.equals("packingGroupNumber")) {
			return ATTRIBUTE_PACKING_GROUP_NUMBER;
		}
		else if(attributeName.equals("additionalDesc")) {
			return ATTRIBUTE_ADDITIONAL_DESC;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitOfMeasure")) {
			return ATTRIBUTE_UNIT_OF_MEASURE;
		}
		else if(attributeName.equals("packageType")) {
			return ATTRIBUTE_PACKAGE_TYPE;
		}
		else if(attributeName.equals("customPackageType")) {
			return ATTRIBUTE_CUSTOM_PACKAGE_TYPE;
		}
		else if(attributeName.equals("packingInstructions")) {
			return ATTRIBUTE_PACKING_INSTRUCTIONS;
		}
		else if(attributeName.equals("transportationMode")) {
			return ATTRIBUTE_TRANSPORTATION_MODE;
		}
		else if(attributeName.equals("labelRequired")) {
			return ATTRIBUTE_LABEL_REQUIRED;
		}
		else if(attributeName.equals("emergencyPhone")) {
			return ATTRIBUTE_EMERGENCY_PHONE;
		}
		else if(attributeName.equals("dangerousGoodsOption")) {
			return ATTRIBUTE_DANGEROUS_GOODS_OPTION;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("upsForbidden")) {
			return ATTRIBUTE_UPS_FORBIDDEN;
		}
		else if(attributeName.equals("dotLabelType")) {
			return ATTRIBUTE_DOT_LABEL_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, UpsDangerousGoodsViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(UpsDangerousGoodsViewBean upsDangerousGoodsViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("referenceNumber", "SearchCriterion.EQUALS",
			"" + upsDangerousGoodsViewBean.getReferenceNumber());

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


	public int delete(UpsDangerousGoodsViewBean upsDangerousGoodsViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("referenceNumber", "SearchCriterion.EQUALS",
			"" + upsDangerousGoodsViewBean.getReferenceNumber());

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
	public int insert(UpsDangerousGoodsViewBean upsDangerousGoodsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(upsDangerousGoodsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(UpsDangerousGoodsViewBean upsDangerousGoodsViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_REFERENCE_NUMBER + "," +
			ATTRIBUTE_REGULATION_SET + "," +
			ATTRIBUTE_REPORTABLE_QUANTITY + "," +
			ATTRIBUTE_SHIPPING_NAME + "," +
			ATTRIBUTE_TECHNICAL_NAME + "," +
			ATTRIBUTE_HAZARD_CLASS + "," +
			ATTRIBUTE_SUBRISK_CLASS + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "," +
			ATTRIBUTE_PACKING_GROUP_NUMBER + "," +
			ATTRIBUTE_ADDITIONAL_DESC + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UNIT_OF_MEASURE + "," +
			ATTRIBUTE_PACKAGE_TYPE + "," +
			ATTRIBUTE_CUSTOM_PACKAGE_TYPE + "," +
			ATTRIBUTE_PACKING_INSTRUCTIONS + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "," +
			ATTRIBUTE_LABEL_REQUIRED + "," +
			ATTRIBUTE_EMERGENCY_PHONE + "," +
			ATTRIBUTE_DANGEROUS_GOODS_OPTION + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_UPS_FORBIDDEN + ")" +
			" values (" +
			upsDangerousGoodsViewBean.getReferenceNumber() + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getRegulationSet()) + "," +
			upsDangerousGoodsViewBean.getReportableQuantity() + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getShippingName()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getTechnicalName()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getHazardClass()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getSubriskClass()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getIdentificationNumber()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getPackingGroupNumber()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getAdditionalDesc()) + "," +
			upsDangerousGoodsViewBean.getQuantity() + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getUnitOfMeasure()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getPackageType()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getCustomPackageType()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getPackingInstructions()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getTransportationMode()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getLabelRequired()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getEmergencyPhone()) + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getDangerousGoodsOption()) + "," +
			upsDangerousGoodsViewBean.getMaterialId() + "," +
			SqlHandler.delimitString(upsDangerousGoodsViewBean.getUpsForbidden()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(UpsDangerousGoodsViewBean upsDangerousGoodsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(upsDangerousGoodsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(UpsDangerousGoodsViewBean upsDangerousGoodsViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_REFERENCE_NUMBER + "=" + 
				StringHandler.nullIfZero(upsDangerousGoodsViewBean.getReferenceNumber()) + "," +
			ATTRIBUTE_REGULATION_SET + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getRegulationSet()) + "," +
			ATTRIBUTE_REPORTABLE_QUANTITY + "=" + 
				StringHandler.nullIfZero(upsDangerousGoodsViewBean.getReportableQuantity()) + "," +
			ATTRIBUTE_SHIPPING_NAME + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getShippingName()) + "," +
			ATTRIBUTE_TECHNICAL_NAME + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getTechnicalName()) + "," +
			ATTRIBUTE_HAZARD_CLASS + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getHazardClass()) + "," +
			ATTRIBUTE_SUBRISK_CLASS + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getSubriskClass()) + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getIdentificationNumber()) + "," +
			ATTRIBUTE_PACKING_GROUP_NUMBER + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getPackingGroupNumber()) + "," +
			ATTRIBUTE_ADDITIONAL_DESC + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getAdditionalDesc()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(upsDangerousGoodsViewBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_OF_MEASURE + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getUnitOfMeasure()) + "," +
			ATTRIBUTE_PACKAGE_TYPE + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getPackageType()) + "," +
			ATTRIBUTE_CUSTOM_PACKAGE_TYPE + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getCustomPackageType()) + "," +
			ATTRIBUTE_PACKING_INSTRUCTIONS + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getPackingInstructions()) + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getTransportationMode()) + "," +
			ATTRIBUTE_LABEL_REQUIRED + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getLabelRequired()) + "," +
			ATTRIBUTE_EMERGENCY_PHONE + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getEmergencyPhone()) + "," +
			ATTRIBUTE_DANGEROUS_GOODS_OPTION + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getDangerousGoodsOption()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" + 
				StringHandler.nullIfZero(upsDangerousGoodsViewBean.getMaterialId()) + "," +
			ATTRIBUTE_UPS_FORBIDDEN + "=" + 
				SqlHandler.delimitString(upsDangerousGoodsViewBean.getUpsForbidden()) + " " +
			"where " + ATTRIBUTE_REFERENCE_NUMBER + "=" +
				upsDangerousGoodsViewBean.getReferenceNumber();

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

		Collection upsDangerousGoodsViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UpsDangerousGoodsViewBean upsDangerousGoodsViewBean = new UpsDangerousGoodsViewBean();
			load(dataSetRow, upsDangerousGoodsViewBean);
			upsDangerousGoodsViewBeanColl.add(upsDangerousGoodsViewBean);
		}

		return upsDangerousGoodsViewBeanColl;
	}
}