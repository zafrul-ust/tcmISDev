package com.tcmis.client.waste.factory;


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
import com.tcmis.client.waste.beans.WstContCaHazLabelViewBean;


/******************************************************************************
 * CLASSNAME: WstContCaHazLabelViewBeanFactory <br>
 * @version: 1.0, Feb 14, 2007 <br>
 *****************************************************************************/


public class WstContCaHazLabelViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CONTAINER_ID = "CONTAINER_ID";
	public String ATTRIBUTE_LPAD_CONTAINER_ID = "LPAD_CONTAINER_ID";
	public String ATTRIBUTE_WASTE_ITEM_ID = "WASTE_ITEM_ID";
	public String ATTRIBUTE_EPA_ID = "EPA_ID";
	public String ATTRIBUTE_MANIFEST = "MANIFEST";
	public String ATTRIBUTE_ACCUMULATION_START_DATE = "ACCUMULATION_START_DATE";
	public String ATTRIBUTE_GEN_NAME = "GEN_NAME";
	public String ATTRIBUTE_GEN_ADDRESS = "GEN_ADDRESS";
	public String ATTRIBUTE_GEN_PHONE = "GEN_PHONE";
	public String ATTRIBUTE_GEN_CITY = "GEN_CITY";
	public String ATTRIBUTE_GEN_STATE = "GEN_STATE";
	public String ATTRIBUTE_GEN_ZIP = "GEN_ZIP";
	public String ATTRIBUTE_EPA_WASTE_NO = "EPA_WASTE_NO";
	public String ATTRIBUTE_CA_WASTE_NO = "CA_WASTE_NO";
	public String ATTRIBUTE_CONTENTS_COMPOSITION = "CONTENTS_COMPOSITION";
	public String ATTRIBUTE_PHYSICAL_STATE_SOLID = "PHYSICAL_STATE_SOLID";
	public String ATTRIBUTE_PHYSICAL_STATE_LIQUID = "PHYSICAL_STATE_LIQUID";
	public String ATTRIBUTE_FLAMMABLE = "FLAMMABLE";
	public String ATTRIBUTE_TOXIC = "TOXIC";
	public String ATTRIBUTE_CORROSIVE = "CORROSIVE";
	public String ATTRIBUTE_REACTIVE = "REACTIVE";
	public String ATTRIBUTE_HAZARD_OTHER = "HAZARD_OTHER";
	public String ATTRIBUTE_HAZARD_OTHER_DETAIL = "HAZARD_OTHER_DETAIL";
	public String ATTRIBUTE_DOT = "DOT";

	//table name
	public String TABLE = "WST_CONT_CA_HAZ_LABEL_VIEW";


	//constructor
	public WstContCaHazLabelViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("containerId")) {
			return ATTRIBUTE_CONTAINER_ID;
		}
		else if(attributeName.equals("lpadContainerId")) {
			return ATTRIBUTE_LPAD_CONTAINER_ID;
		}
		else if(attributeName.equals("wasteItemId")) {
			return ATTRIBUTE_WASTE_ITEM_ID;
		}
		else if(attributeName.equals("epaId")) {
			return ATTRIBUTE_EPA_ID;
		}
		else if(attributeName.equals("manifest")) {
			return ATTRIBUTE_MANIFEST;
		}
		else if(attributeName.equals("accumulationStartDate")) {
			return ATTRIBUTE_ACCUMULATION_START_DATE;
		}
		else if(attributeName.equals("genName")) {
			return ATTRIBUTE_GEN_NAME;
		}
		else if(attributeName.equals("genAddress")) {
			return ATTRIBUTE_GEN_ADDRESS;
		}
		else if(attributeName.equals("genPhone")) {
			return ATTRIBUTE_GEN_PHONE;
		}
		else if(attributeName.equals("genCity")) {
			return ATTRIBUTE_GEN_CITY;
		}
		else if(attributeName.equals("genState")) {
			return ATTRIBUTE_GEN_STATE;
		}
		else if(attributeName.equals("genZip")) {
			return ATTRIBUTE_GEN_ZIP;
		}
		else if(attributeName.equals("epaWasteNo")) {
			return ATTRIBUTE_EPA_WASTE_NO;
		}
		else if(attributeName.equals("caWasteNo")) {
			return ATTRIBUTE_CA_WASTE_NO;
		}
		else if(attributeName.equals("contentsComposition")) {
			return ATTRIBUTE_CONTENTS_COMPOSITION;
		}
		else if(attributeName.equals("physicalStateSolid")) {
			return ATTRIBUTE_PHYSICAL_STATE_SOLID;
		}
		else if(attributeName.equals("physicalStateLiquid")) {
			return ATTRIBUTE_PHYSICAL_STATE_LIQUID;
		}
		else if(attributeName.equals("flammable")) {
			return ATTRIBUTE_FLAMMABLE;
		}
		else if(attributeName.equals("toxic")) {
			return ATTRIBUTE_TOXIC;
		}
		else if(attributeName.equals("corrosive")) {
			return ATTRIBUTE_CORROSIVE;
		}
		else if(attributeName.equals("reactive")) {
			return ATTRIBUTE_REACTIVE;
		}
		else if(attributeName.equals("hazardOther")) {
			return ATTRIBUTE_HAZARD_OTHER;
		}
		else if(attributeName.equals("hazardOtherDetail")) {
			return ATTRIBUTE_HAZARD_OTHER_DETAIL;
		}
		else if(attributeName.equals("dot")) {
			return ATTRIBUTE_DOT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, WstContCaHazLabelViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(WstContCaHazLabelViewBean wstContCaHazLabelViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + wstContCaHazLabelViewBean.getCompanyId());

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


	public int delete(WstContCaHazLabelViewBean wstContCaHazLabelViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + wstContCaHazLabelViewBean.getCompanyId());

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
	public int insert(WstContCaHazLabelViewBean wstContCaHazLabelViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(wstContCaHazLabelViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(WstContCaHazLabelViewBean wstContCaHazLabelViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CONTAINER_ID + "," +
			ATTRIBUTE_LPAD_CONTAINER_ID + "," +
			ATTRIBUTE_WASTE_ITEM_ID + "," +
			ATTRIBUTE_EPA_ID + "," +
			ATTRIBUTE_MANIFEST + "," +
			ATTRIBUTE_ACCUMULATION_START_DATE + "," +
			ATTRIBUTE_GEN_NAME + "," +
			ATTRIBUTE_GEN_ADDRESS + "," +
			ATTRIBUTE_GEN_PHONE + "," +
			ATTRIBUTE_GEN_CITY + "," +
			ATTRIBUTE_GEN_STATE + "," +
			ATTRIBUTE_GEN_ZIP + "," +
			ATTRIBUTE_EPA_WASTE_NO + "," +
			ATTRIBUTE_CA_WASTE_NO + "," +
			ATTRIBUTE_CONTENTS_COMPOSITION + "," +
			ATTRIBUTE_PHYSICAL_STATE_SOLID + "," +
			ATTRIBUTE_PHYSICAL_STATE_LIQUID + "," +
			ATTRIBUTE_FLAMMABLE + "," +
			ATTRIBUTE_TOXIC + "," +
			ATTRIBUTE_CORROSIVE + "," +
			ATTRIBUTE_REACTIVE + "," +
			ATTRIBUTE_HAZARD_OTHER + "," +
			ATTRIBUTE_HAZARD_OTHER_DETAIL + "," +
			ATTRIBUTE_DOT + ")" +
			" values (" +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getCompanyId()) + "," +
			wstContCaHazLabelViewBean.getContainerId() + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getLpadContainerId()) + "," +
			wstContCaHazLabelViewBean.getWasteItemId() + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getEpaId()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getManifest()) + "," +
			DateHandler.getOracleToDateFunction(wstContCaHazLabelViewBean.getAccumulationStartDate()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenName()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenAddress()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenPhone()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenCity()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenState()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenZip()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getEpaWasteNo()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getCaWasteNo()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getContentsComposition()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getPhysicalStateSolid()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getPhysicalStateLiquid()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getFlammable()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getToxic()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getCorrosive()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getReactive()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getHazardOther()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getHazardOtherDetail()) + "," +
			SqlHandler.delimitString(wstContCaHazLabelViewBean.getDot()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(WstContCaHazLabelViewBean wstContCaHazLabelViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(wstContCaHazLabelViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(WstContCaHazLabelViewBean wstContCaHazLabelViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getCompanyId()) + "," +
			ATTRIBUTE_CONTAINER_ID + "=" +
				StringHandler.nullIfZero(wstContCaHazLabelViewBean.getContainerId()) + "," +
			ATTRIBUTE_LPAD_CONTAINER_ID + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getLpadContainerId()) + "," +
			ATTRIBUTE_WASTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(wstContCaHazLabelViewBean.getWasteItemId()) + "," +
			ATTRIBUTE_EPA_ID + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getEpaId()) + "," +
			ATTRIBUTE_MANIFEST + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getManifest()) + "," +
			ATTRIBUTE_ACCUMULATION_START_DATE + "=" +
				DateHandler.getOracleToDateFunction(wstContCaHazLabelViewBean.getAccumulationStartDate()) + "," +
			ATTRIBUTE_GEN_NAME + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenName()) + "," +
			ATTRIBUTE_GEN_ADDRESS + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenAddress()) + "," +
			ATTRIBUTE_GEN_PHONE + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenPhone()) + "," +
			ATTRIBUTE_GEN_CITY + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenCity()) + "," +
			ATTRIBUTE_GEN_STATE + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenState()) + "," +
			ATTRIBUTE_GEN_ZIP + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getGenZip()) + "," +
			ATTRIBUTE_EPA_WASTE_NO + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getEpaWasteNo()) + "," +
			ATTRIBUTE_CA_WASTE_NO + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getCaWasteNo()) + "," +
			ATTRIBUTE_CONTENTS_COMPOSITION + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getContentsComposition()) + "," +
			ATTRIBUTE_PHYSICAL_STATE_SOLID + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getPhysicalStateSolid()) + "," +
			ATTRIBUTE_PHYSICAL_STATE_LIQUID + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getPhysicalStateLiquid()) + "," +
			ATTRIBUTE_FLAMMABLE + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getFlammable()) + "," +
			ATTRIBUTE_TOXIC + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getToxic()) + "," +
			ATTRIBUTE_CORROSIVE + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getCorrosive()) + "," +
			ATTRIBUTE_REACTIVE + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getReactive()) + "," +
			ATTRIBUTE_HAZARD_OTHER + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getHazardOther()) + "," +
			ATTRIBUTE_HAZARD_OTHER_DETAIL + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getHazardOtherDetail()) + "," +
			ATTRIBUTE_DOT + "=" +
				SqlHandler.delimitString(wstContCaHazLabelViewBean.getDot()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				wstContCaHazLabelViewBean.getCompanyId();

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

		Collection wstContCaHazLabelViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WstContCaHazLabelViewBean wstContCaHazLabelViewBean = new WstContCaHazLabelViewBean();
			load(dataSetRow, wstContCaHazLabelViewBean);
			wstContCaHazLabelViewBeanColl.add(wstContCaHazLabelViewBean);
		}

		return wstContCaHazLabelViewBeanColl;
	}
}