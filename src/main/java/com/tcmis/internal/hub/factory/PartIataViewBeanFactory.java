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
import com.tcmis.internal.hub.beans.PartIataViewBean;


/******************************************************************************
 * CLASSNAME: PartIataViewBeanFactory <br>
 * @version: 1.0, Jul 8, 2008 <br>
 *****************************************************************************/


public class PartIataViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_FINAL_SHIPPING_NAME = "FINAL_SHIPPING_NAME";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_NET_SIZE = "NET_SIZE";
	public String ATTRIBUTE_NET_SIZE_UNIT = "NET_SIZE_UNIT";
	public String ATTRIBUTE_PHYSICAL_STATE = "PHYSICAL_STATE";
	public String ATTRIBUTE_LAST_UPDATED_BY = "LAST_UPDATED_BY";
	public String ATTRIBUTE_LAST_UPDATED_DATE = "LAST_UPDATED_DATE";
	public String ATTRIBUTE_IATA_PROPER_SHIPPING_NAME = "IATA_PROPER_SHIPPING_NAME";
	public String ATTRIBUTE_IATA_TECHNICAL_NAME = "IATA_TECHNICAL_NAME";
	public String ATTRIBUTE_HAZARD_CLASS = "HAZARD_CLASS";
	public String ATTRIBUTE_PACKING_GROUP = "PACKING_GROUP";
	public String ATTRIBUTE_IDENTIFICATION_NUMBER = "IDENTIFICATION_NUMBER";
	public String ATTRIBUTE_IATA_SUBRISK = "IATA_SUBRISK";
	public String ATTRIBUTE_IATA_SHIPPING_REMARK = "IATA_SHIPPING_REMARK";
	public String ATTRIBUTE_IATA_MIXTURE_SOLUTION = "IATA_MIXTURE_SOLUTION";

	//table name
	public String TABLE = "PART_IATA_VIEW";


	//constructor
	public PartIataViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("finalShippingName")) {
			return ATTRIBUTE_FINAL_SHIPPING_NAME;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("netSize")) {
			return ATTRIBUTE_NET_SIZE;
		}
		else if(attributeName.equals("netSizeUnit")) {
			return ATTRIBUTE_NET_SIZE_UNIT;
		}
		else if(attributeName.equals("physicalState")) {
			return ATTRIBUTE_PHYSICAL_STATE;
		}
		else if(attributeName.equals("lastUpdatedBy")) {
			return ATTRIBUTE_LAST_UPDATED_BY;
		}
		else if(attributeName.equals("lastUpdatedDate")) {
			return ATTRIBUTE_LAST_UPDATED_DATE;
		}
		else if(attributeName.equals("iataProperShippingName")) {
			return ATTRIBUTE_IATA_PROPER_SHIPPING_NAME;
		}
		else if(attributeName.equals("iataTechnicalName")) {
			return ATTRIBUTE_IATA_TECHNICAL_NAME;
		}
		else if(attributeName.equals("hazardClass")) {
			return ATTRIBUTE_HAZARD_CLASS;
		}
		else if(attributeName.equals("packingGroup")) {
			return ATTRIBUTE_PACKING_GROUP;
		}
		else if(attributeName.equals("identificationNumber")) {
			return ATTRIBUTE_IDENTIFICATION_NUMBER;
		}
		else if(attributeName.equals("iataSubrisk")) {
			return ATTRIBUTE_IATA_SUBRISK;
		}
		else if(attributeName.equals("iataShippingRemark")) {
			return ATTRIBUTE_IATA_SHIPPING_REMARK;
		}
		else if(attributeName.equals("iataMixtureSolution")) {
			return ATTRIBUTE_IATA_MIXTURE_SOLUTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PartIataViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PartIataViewBean partIataViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + partIataViewBean.getItemId());

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


	public int delete(PartIataViewBean partIataViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + partIataViewBean.getItemId());

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
	public int insert(PartIataViewBean partIataViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(partIataViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PartIataViewBean partIataViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_PART_ID + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_MATERIAL_DESC + "," +
			ATTRIBUTE_FINAL_SHIPPING_NAME + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_NET_SIZE + "," +
			ATTRIBUTE_NET_SIZE_UNIT + "," +
			ATTRIBUTE_PHYSICAL_STATE + "," +
			ATTRIBUTE_LAST_UPDATED_BY + "," +
			ATTRIBUTE_LAST_UPDATED_DATE + "," +
			ATTRIBUTE_IATA_PROPER_SHIPPING_NAME + "," +
			ATTRIBUTE_IATA_TECHNICAL_NAME + "," +
			ATTRIBUTE_HAZARD_CLASS + "," +
			ATTRIBUTE_PACKING_GROUP + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "," +
			ATTRIBUTE_IATA_SUBRISK + "," +
			ATTRIBUTE_IATA_SHIPPING_REMARK + "," +
			ATTRIBUTE_IATA_MIXTURE_SOLUTION + ")" +
			" values (" +
			partIataViewBean.getItemId() + "," +
			partIataViewBean.getPartId() + "," +
			partIataViewBean.getMaterialId() + "," +
			SqlHandler.delimitString(partIataViewBean.getMaterialDesc()) + "," +
			SqlHandler.delimitString(partIataViewBean.getFinalShippingName()) + "," +
			SqlHandler.delimitString(partIataViewBean.getPackaging()) + "," +
			partIataViewBean.getNetSize() + "," +
			SqlHandler.delimitString(partIataViewBean.getNetSizeUnit()) + "," +
			SqlHandler.delimitString(partIataViewBean.getPhysicalState()) + "," +
			SqlHandler.delimitString(partIataViewBean.getLastUpdatedBy()) + "," +
			SqlHandler.delimitString(partIataViewBean.getLastUpdatedDate()) + "," +
			SqlHandler.delimitString(partIataViewBean.getIataProperShippingName()) + "," +
			SqlHandler.delimitString(partIataViewBean.getIataTechnicalName()) + "," +
			SqlHandler.delimitString(partIataViewBean.getHazardClass()) + "," +
			SqlHandler.delimitString(partIataViewBean.getPackingGroup()) + "," +
			SqlHandler.delimitString(partIataViewBean.getIdentificationNumber()) + "," +
			SqlHandler.delimitString(partIataViewBean.getIataSubrisk()) + "," +
			SqlHandler.delimitString(partIataViewBean.getIataShippingRemark()) + "," +
			SqlHandler.delimitString(partIataViewBean.getIataMixtureSolution()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PartIataViewBean partIataViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(partIataViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PartIataViewBean partIataViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(partIataViewBean.getItemId()) + "," +
			ATTRIBUTE_PART_ID + "=" + 
				StringHandler.nullIfZero(partIataViewBean.getPartId()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" + 
				StringHandler.nullIfZero(partIataViewBean.getMaterialId()) + "," +
			ATTRIBUTE_MATERIAL_DESC + "=" + 
				SqlHandler.delimitString(partIataViewBean.getMaterialDesc()) + "," +
			ATTRIBUTE_FINAL_SHIPPING_NAME + "=" + 
				SqlHandler.delimitString(partIataViewBean.getFinalShippingName()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(partIataViewBean.getPackaging()) + "," +
			ATTRIBUTE_NET_SIZE + "=" + 
				StringHandler.nullIfZero(partIataViewBean.getNetSize()) + "," +
			ATTRIBUTE_NET_SIZE_UNIT + "=" + 
				SqlHandler.delimitString(partIataViewBean.getNetSizeUnit()) + "," +
			ATTRIBUTE_PHYSICAL_STATE + "=" + 
				SqlHandler.delimitString(partIataViewBean.getPhysicalState()) + "," +
			ATTRIBUTE_LAST_UPDATED_BY + "=" + 
				SqlHandler.delimitString(partIataViewBean.getLastUpdatedBy()) + "," +
			ATTRIBUTE_LAST_UPDATED_DATE + "=" + 
				SqlHandler.delimitString(partIataViewBean.getLastUpdatedDate()) + "," +
			ATTRIBUTE_IATA_PROPER_SHIPPING_NAME + "=" + 
				SqlHandler.delimitString(partIataViewBean.getIataProperShippingName()) + "," +
			ATTRIBUTE_IATA_TECHNICAL_NAME + "=" + 
				SqlHandler.delimitString(partIataViewBean.getIataTechnicalName()) + "," +
			ATTRIBUTE_HAZARD_CLASS + "=" + 
				SqlHandler.delimitString(partIataViewBean.getHazardClass()) + "," +
			ATTRIBUTE_PACKING_GROUP + "=" + 
				SqlHandler.delimitString(partIataViewBean.getPackingGroup()) + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "=" + 
				SqlHandler.delimitString(partIataViewBean.getIdentificationNumber()) + "," +
			ATTRIBUTE_IATA_SUBRISK + "=" + 
				SqlHandler.delimitString(partIataViewBean.getIataSubrisk()) + "," +
			ATTRIBUTE_IATA_SHIPPING_REMARK + "=" + 
				SqlHandler.delimitString(partIataViewBean.getIataShippingRemark()) + "," +
			ATTRIBUTE_IATA_MIXTURE_SOLUTION + "=" + 
				SqlHandler.delimitString(partIataViewBean.getIataMixtureSolution()) + " " +
			"where " + ATTRIBUTE_ITEM_ID + "=" +
				partIataViewBean.getItemId();

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

		Collection partIataViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PartIataViewBean partIataViewBean = new PartIataViewBean();
			load(dataSetRow, partIataViewBean);
			partIataViewBeanColl.add(partIataViewBean);
		}

		return partIataViewBeanColl;
	}
}