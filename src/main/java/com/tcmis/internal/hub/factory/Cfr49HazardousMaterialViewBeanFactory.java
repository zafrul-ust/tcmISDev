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
import com.tcmis.internal.hub.beans.Cfr49HazardousMaterialViewBean;

/******************************************************************************
 * CLASSNAME: Cfr49HazardousMaterialViewBeanFactory <br>
 * @version: 1.0, Nov 5, 2007 <br>
 *****************************************************************************/

public class Cfr49HazardousMaterialViewBeanFactory extends BaseBeanFactory 
{
	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_HAZMAT_ID = "HAZMAT_ID";
	public String ATTRIBUTE_SYMBOL = "SYMBOL";
	public String ATTRIBUTE_HAZARDOUS_MATERIAL_DESCRIPTION = "HAZARDOUS_MATERIAL_DESCRIPTION";
	public String ATTRIBUTE_HAZARD_CLASS = "HAZARD_CLASS";
	public String ATTRIBUTE_IDENTIFICATION_NUMBER = "IDENTIFICATION_NUMBER";
	public String ATTRIBUTE_PACKING_GROUP = "PACKING_GROUP";
	public String ATTRIBUTE_LABEL_CODE = "LABEL_CODE";
	public String ATTRIBUTE_SPECIAL_PROVISION = "SPECIAL_PROVISION";
	public String ATTRIBUTE_PACKAGING_EXCEPTION = "PACKAGING_EXCEPTION";
	public String ATTRIBUTE_PACKAGING_NONBULK = "PACKAGING_NONBULK";
	public String ATTRIBUTE_PACKAGING_BULK = "PACKAGING_BULK";
	public String ATTRIBUTE_QUANTITY_LIMITATION_PASSENGER = "QUANTITY_LIMITATION_PASSENGER";
	public String ATTRIBUTE_QUANTITY_LIMITATION_CARGO = "QUANTITY_LIMITATION_CARGO";
	public String ATTRIBUTE_VESSEL_STOWAGE_LOCATION = "VESSEL_STOWAGE_LOCATION";
	public String ATTRIBUTE_VESSEL_STOWAGE_OTHER = "VESSEL_STOWAGE_OTHER";
	public String ATTRIBUTE_PROPER_SHIPPING_NAME = "PROPER_SHIPPING_NAME";
	public String ATTRIBUTE_PICKABLE = "PICKABLE";
	public String ATTRIBUTE_REFERENCE_HAZMAT_ID = "REFERENCE_HAZMAT_ID";
	public String ATTRIBUTE_SHIPPING_NAME_COUNT = "SHIPPING_NAME_COUNT";

	//table name
	public String TABLE = "CFR_49_HAZARDOUS_MATERIAL_VIEW";


	//constructor
	public Cfr49HazardousMaterialViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("hazmatId")) {
			return ATTRIBUTE_HAZMAT_ID;
		}
		else if(attributeName.equals("symbol")) {
			return ATTRIBUTE_SYMBOL;
		}
		else if(attributeName.equals("hazardousMaterialDescription")) {
			return ATTRIBUTE_HAZARDOUS_MATERIAL_DESCRIPTION;
		}
		else if(attributeName.equals("hazardClass")) {
			return ATTRIBUTE_HAZARD_CLASS;
		}
		else if(attributeName.equals("identificationNumber")) {
			return ATTRIBUTE_IDENTIFICATION_NUMBER;
		}
		else if(attributeName.equals("packingGroup")) {
			return ATTRIBUTE_PACKING_GROUP;
		}
		else if(attributeName.equals("labelCode")) {
			return ATTRIBUTE_LABEL_CODE;
		}
		else if(attributeName.equals("specialProvision")) {
			return ATTRIBUTE_SPECIAL_PROVISION;
		}
		else if(attributeName.equals("packagingException")) {
			return ATTRIBUTE_PACKAGING_EXCEPTION;
		}
		else if(attributeName.equals("packagingNonbulk")) {
			return ATTRIBUTE_PACKAGING_NONBULK;
		}
		else if(attributeName.equals("packagingBulk")) {
			return ATTRIBUTE_PACKAGING_BULK;
		}
		else if(attributeName.equals("quantityLimitationPassenger")) {
			return ATTRIBUTE_QUANTITY_LIMITATION_PASSENGER;
		}
		else if(attributeName.equals("quantityLimitationCargo")) {
			return ATTRIBUTE_QUANTITY_LIMITATION_CARGO;
		}
		else if(attributeName.equals("vesselStowageLocation")) {
			return ATTRIBUTE_VESSEL_STOWAGE_LOCATION;
		}
		else if(attributeName.equals("vesselStowageOther")) {
			return ATTRIBUTE_VESSEL_STOWAGE_OTHER;
		}
		else if(attributeName.equals("properShippingName")) {
			return ATTRIBUTE_PROPER_SHIPPING_NAME;
		}
		else if(attributeName.equals("pickable")) {
			return ATTRIBUTE_PICKABLE;
		}
		else if(attributeName.equals("referenceHazmatId")) {
			return ATTRIBUTE_REFERENCE_HAZMAT_ID;
		}
		else if(attributeName.equals("shippingNameCount")) {
			return ATTRIBUTE_SHIPPING_NAME_COUNT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, Cfr49HazardousMaterialViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(Cfr49HazardousMaterialViewBean cfr49HazardousMaterialViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hazmatId", "SearchCriterion.EQUALS",
			"" + cfr49HazardousMaterialViewBean.getHazmatId());

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


	public int delete(Cfr49HazardousMaterialViewBean cfr49HazardousMaterialViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hazmatId", "SearchCriterion.EQUALS",
			"" + cfr49HazardousMaterialViewBean.getHazmatId());

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
	public int insert(Cfr49HazardousMaterialViewBean cfr49HazardousMaterialViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(cfr49HazardousMaterialViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(Cfr49HazardousMaterialViewBean cfr49HazardousMaterialViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_HAZMAT_ID + "," +
			ATTRIBUTE_SYMBOL + "," +
			ATTRIBUTE_HAZARDOUS_MATERIAL_DESCRIPTION + "," +
			ATTRIBUTE_HAZARD_CLASS + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "," +
			ATTRIBUTE_PACKING_GROUP + "," +
			ATTRIBUTE_LABEL_CODE + "," +
			ATTRIBUTE_SPECIAL_PROVISION + "," +
			ATTRIBUTE_PACKAGING_EXCEPTION + "," +
			ATTRIBUTE_PACKAGING_NONBULK + "," +
			ATTRIBUTE_PACKAGING_BULK + "," +
			ATTRIBUTE_QUANTITY_LIMITATION_PASSENGER + "," +
			ATTRIBUTE_QUANTITY_LIMITATION_CARGO + "," +
			ATTRIBUTE_VESSEL_STOWAGE_LOCATION + "," +
			ATTRIBUTE_VESSEL_STOWAGE_OTHER + "," +
			ATTRIBUTE_PROPER_SHIPPING_NAME + "," +
			ATTRIBUTE_PICKABLE + "," +
			ATTRIBUTE_REFERENCE_HAZMAT_ID + "," +
			ATTRIBUTE_SHIPPING_NAME_COUNT + ")" +
			" values (" +
			cfr49HazardousMaterialViewBean.getHazmatId() + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getSymbol()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getHazardousMaterialDescription()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getHazardClass()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getIdentificationNumber()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackingGroup()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getLabelCode()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getSpecialProvision()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackagingException()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackagingNonbulk()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackagingBulk()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getQuantityLimitationPassenger()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getQuantityLimitationCargo()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getVesselStowageLocation()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getVesselStowageOther()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getProperShippingName()) + "," +
			SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPickable()) + "," +
			cfr49HazardousMaterialViewBean.getReferenceHazmatId() + "," +
			cfr49HazardousMaterialViewBean.getShippingNameCount() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(Cfr49HazardousMaterialViewBean cfr49HazardousMaterialViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(cfr49HazardousMaterialViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(Cfr49HazardousMaterialViewBean cfr49HazardousMaterialViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_HAZMAT_ID + "=" + 
				StringHandler.nullIfZero(cfr49HazardousMaterialViewBean.getHazmatId()) + "," +
			ATTRIBUTE_SYMBOL + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getSymbol()) + "," +
			ATTRIBUTE_HAZARDOUS_MATERIAL_DESCRIPTION + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getHazardousMaterialDescription()) + "," +
			ATTRIBUTE_HAZARD_CLASS + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getHazardClass()) + "," +
			ATTRIBUTE_IDENTIFICATION_NUMBER + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getIdentificationNumber()) + "," +
			ATTRIBUTE_PACKING_GROUP + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackingGroup()) + "," +
			ATTRIBUTE_LABEL_CODE + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getLabelCode()) + "," +
			ATTRIBUTE_SPECIAL_PROVISION + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getSpecialProvision()) + "," +
			ATTRIBUTE_PACKAGING_EXCEPTION + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackagingException()) + "," +
			ATTRIBUTE_PACKAGING_NONBULK + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackagingNonbulk()) + "," +
			ATTRIBUTE_PACKAGING_BULK + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPackagingBulk()) + "," +
			ATTRIBUTE_QUANTITY_LIMITATION_PASSENGER + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getQuantityLimitationPassenger()) + "," +
			ATTRIBUTE_QUANTITY_LIMITATION_CARGO + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getQuantityLimitationCargo()) + "," +
			ATTRIBUTE_VESSEL_STOWAGE_LOCATION + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getVesselStowageLocation()) + "," +
			ATTRIBUTE_VESSEL_STOWAGE_OTHER + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getVesselStowageOther()) + "," +
			ATTRIBUTE_PROPER_SHIPPING_NAME + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getProperShippingName()) + "," +
			ATTRIBUTE_PICKABLE + "=" + 
				SqlHandler.delimitString(cfr49HazardousMaterialViewBean.getPickable()) + "," +
			ATTRIBUTE_REFERENCE_HAZMAT_ID + "=" + 
				StringHandler.nullIfZero(cfr49HazardousMaterialViewBean.getReferenceHazmatId()) + "," +
			ATTRIBUTE_SHIPPING_NAME_COUNT + "=" + 
				StringHandler.nullIfZero(cfr49HazardousMaterialViewBean.getShippingNameCount()) + " " +
			"where " + ATTRIBUTE_HAZMAT_ID + "=" +
				cfr49HazardousMaterialViewBean.getHazmatId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException 
	{
		Connection connection = null;
		Collection c = null;
		try 
		{
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally 
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException 
	{
		Collection hazMatBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) 
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			Cfr49HazardousMaterialViewBean hazMatBean = new Cfr49HazardousMaterialViewBean();
			load(dataSetRow, hazMatBean);
			hazMatBeanColl.add(hazMatBean);
		}

		return hazMatBeanColl;
	}
/*	
	public Collection select(String searchArgument)	throws BaseException 
	{

		Connection connection = null;
		Collection c = null;
		try 
		{
			connection = this.getDbManager().getConnection();
			c = select(searchArgument, connection);
		}
		finally 
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	
	public Collection select(String searchArgument, Connection conn)	throws BaseException 
	{

		Collection cfr49HazardousMaterialViewBeanColl = new Vector();

		StringBuffer queryBuffer = new StringBuffer("select * from "); // + TABLE + " " +
		queryBuffer.append(TABLE);
		queryBuffer.append(" where  ( lower(HAZARDOUS_MATERIAL_DESCRIPTION) like lower('%");
		queryBuffer.append(searchArgument);
		queryBuffer.append("%'))  order by HAZMAT_ID asc");
		
		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) 
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			Cfr49HazardousMaterialViewBean cfr49HazardousMaterialViewBean = new Cfr49HazardousMaterialViewBean();
			load(dataSetRow, cfr49HazardousMaterialViewBean);
			cfr49HazardousMaterialViewBeanColl.add(cfr49HazardousMaterialViewBean);
		}

		return cfr49HazardousMaterialViewBeanColl;
	}
*/	
}