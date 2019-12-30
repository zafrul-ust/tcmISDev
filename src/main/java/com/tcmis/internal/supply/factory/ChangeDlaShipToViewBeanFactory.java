package com.tcmis.internal.supply.factory;


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
import com.tcmis.internal.supply.beans.ChangeDlaShipToViewBean;


/******************************************************************************
 * CLASSNAME: ChangeDlaShipToViewBeanFactory <br>
 * @version: 1.0, Feb 26, 2009 <br>
 *****************************************************************************/


public class ChangeDlaShipToViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_VIA_DODAAC = "SHIP_VIA_DODAAC";
	public String ATTRIBUTE_SHIP_VIA_LOCATION_ID = "SHIP_VIA_LOCATION_ID";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_PORT_OF_EMBARKATION = "PORT_OF_EMBARKATION";
	public String ATTRIBUTE_PORT_OF_DEBARKATION = "PORT_OF_DEBARKATION";
	public String ATTRIBUTE_ST_COUNTRY_ABBREV = "ST_COUNTRY_ABBREV";
	public String ATTRIBUTE_ST_STATE_ABBREV = "ST_STATE_ABBREV";
	public String ATTRIBUTE_ST_CITY = "ST_CITY";
	public String ATTRIBUTE_ST_ZIP = "ST_ZIP";
	public String ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY = "ST_ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY = "ST_ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY = "ST_ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY = "ST_ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_MF_COUNTRY_ABBREV = "MF_COUNTRY_ABBREV";
	public String ATTRIBUTE_MF_STATE_ABBREV = "MF_STATE_ABBREV";
	public String ATTRIBUTE_MF_CITY = "MF_CITY";
	public String ATTRIBUTE_MF_ZIP = "MF_ZIP";
	public String ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY = "MF_ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY = "MF_ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY = "MF_ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY = "MF_ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_DESIRED_SHIP_DATE = "DESIRED_SHIP_DATE";
    
    //table name
	public String TABLE = "CHANGE_DLA_SHIP_TO_VIEW";


	//constructor
	public ChangeDlaShipToViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipViaDodaac")) {
			return ATTRIBUTE_SHIP_VIA_DODAAC;
		}
		else if(attributeName.equals("shipViaLocationId")) {
			return ATTRIBUTE_SHIP_VIA_LOCATION_ID;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("portOfEmbarkation")) {
			return ATTRIBUTE_PORT_OF_EMBARKATION;
		}
		else if(attributeName.equals("portOfDebarkation")) {
			return ATTRIBUTE_PORT_OF_DEBARKATION;
		}
		else if(attributeName.equals("stCountryAbbrev")) {
			return ATTRIBUTE_ST_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("stStateAbbrev")) {
			return ATTRIBUTE_ST_STATE_ABBREV;
		}
		else if(attributeName.equals("stCity")) {
			return ATTRIBUTE_ST_CITY;
		}
		else if(attributeName.equals("stZip")) {
			return ATTRIBUTE_ST_ZIP;
		}
		else if(attributeName.equals("stAddressLine1Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("stAddressLine2Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("stAddressLine3Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("stAddressLine4Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("mfCountryAbbrev")) {
			return ATTRIBUTE_MF_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("mfStateAbbrev")) {
			return ATTRIBUTE_MF_STATE_ABBREV;
		}
		else if(attributeName.equals("mfCity")) {
			return ATTRIBUTE_MF_CITY;
		}
		else if(attributeName.equals("mfZip")) {
			return ATTRIBUTE_MF_ZIP;
		}
		else if(attributeName.equals("mfAddressLine1Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("mfAddressLine2Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("mfAddressLine3Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("mfAddressLine4Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
        else if(attributeName.equals("desiredShipDate")) {
			return ATTRIBUTE_DESIRED_SHIP_DATE;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ChangeDlaShipToViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ChangeDlaShipToViewBean changeDlaShipToViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + changeDlaShipToViewBean.getPrNumber());

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


	public int delete(ChangeDlaShipToViewBean changeDlaShipToViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + changeDlaShipToViewBean.getPrNumber());

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
	public int insert(ChangeDlaShipToViewBean changeDlaShipToViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(changeDlaShipToViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ChangeDlaShipToViewBean changeDlaShipToViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "," +
			ATTRIBUTE_NOTES + "," +
			ATTRIBUTE_MILSTRIP_CODE + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "," +
			ATTRIBUTE_ST_COUNTRY_ABBREV + "," +
			ATTRIBUTE_ST_STATE_ABBREV + "," +
			ATTRIBUTE_ST_CITY + "," +
			ATTRIBUTE_ST_ZIP + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY + "," +
			ATTRIBUTE_MF_COUNTRY_ABBREV + "," +
			ATTRIBUTE_MF_STATE_ABBREV + "," +
			ATTRIBUTE_MF_CITY + "," +
			ATTRIBUTE_MF_ZIP + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY + "," +
			ATTRIBUTE_FAC_PART_NO + ")" +
			" values (" +
			changeDlaShipToViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getShipViaDodaac()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getNotes()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMilstripCode()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getPortOfEmbarkation()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getPortOfDebarkation()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStCountryAbbrev()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStStateAbbrev()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStCity()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStZip()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine1Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine2Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine3Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine4Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfCountryAbbrev()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfStateAbbrev()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfCity()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfZip()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine1Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine2Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine3Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine4Display()) + "," +
			SqlHandler.delimitString(changeDlaShipToViewBean.getFacPartNo()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ChangeDlaShipToViewBean changeDlaShipToViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(changeDlaShipToViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ChangeDlaShipToViewBean changeDlaShipToViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(changeDlaShipToViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getLineItem()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getShipViaDodaac()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_NOTES + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getNotes()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMilstripCode()) + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getPortOfEmbarkation()) + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getPortOfDebarkation()) + "," +
			ATTRIBUTE_ST_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStCountryAbbrev()) + "," +
			ATTRIBUTE_ST_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStStateAbbrev()) + "," +
			ATTRIBUTE_ST_CITY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStCity()) + "," +
			ATTRIBUTE_ST_ZIP + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStZip()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine1Display()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine2Display()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine3Display()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getStAddressLine4Display()) + "," +
			ATTRIBUTE_MF_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfCountryAbbrev()) + "," +
			ATTRIBUTE_MF_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfStateAbbrev()) + "," +
			ATTRIBUTE_MF_CITY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfCity()) + "," +
			ATTRIBUTE_MF_ZIP + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfZip()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine1Display()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine2Display()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine3Display()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getMfAddressLine4Display()) + "," +
			ATTRIBUTE_FAC_PART_NO + "=" + 
				SqlHandler.delimitString(changeDlaShipToViewBean.getFacPartNo()) + " " +
			"where " + ATTRIBUTE_PR_NUMBER + "=" +
				changeDlaShipToViewBean.getPrNumber();

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

		Collection changeDlaShipToViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ChangeDlaShipToViewBean changeDlaShipToViewBean = new ChangeDlaShipToViewBean();
			load(dataSetRow, changeDlaShipToViewBean);
			changeDlaShipToViewBeanColl.add(changeDlaShipToViewBean);
		}

		return changeDlaShipToViewBeanColl;
	}
	
	public Collection doProcedure(String procedure,
			Collection inParameters,
			Collection outParameters) throws
			BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = getDbManager().getConnection();
			c = this.doProcedure(connection, procedure, inParameters, outParameters);
		}
		finally {
			try {
				getDbManager().returnConnection(connection);
			}
			catch (Exception e) {
//				ignore
			}
		}
		return c;
	}

	public Collection doProcedure(Connection connection,
			String procedure,
			Collection inParameters,
			Collection outParameters) throws BaseException {
		return new SqlManager().doProcedure(connection, procedure, inParameters,
				outParameters);
	}

	
}