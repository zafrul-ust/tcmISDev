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
import com.tcmis.internal.hub.beans.FreightAdviceViewBean;


/******************************************************************************
 * CLASSNAME: FreightAdviceViewBeanFactory <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class FreightAdviceViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_SCHEDULED_SHIP_DATE = "SCHEDULED_SHIP_DATE";
	public String ATTRIBUTE_SHIP_VIA = "SHIP_VIA";
	public String ATTRIBUTE_ULTIMATE_DODAAC = "ULTIMATE_DODAAC";
	public String ATTRIBUTE_ULTIMATE_SHIP_TO = "ULTIMATE_SHIP_TO";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_CARRIER_TRACKING_NUMBER = "CARRIER_TRACKING_NUMBER";
	public String ATTRIBUTE_TRANSPORTATION_MODE = "TRANSPORTATION_MODE";
	public String ATTRIBUTE_TRAILER_NUMBER = "TRAILER_NUMBER";
	public String ATTRIBUTE_STOP_NUMBER = "STOP_NUMBER";
	public String ATTRIBUTE_MR_LINE = "MR_LINE";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_WEIGHT = "WEIGHT";
	public String ATTRIBUTE_CUBE = "CUBE";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_CONSOLIDATION_NUMBER = "CONSOLIDATION_NUMBER";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
	public String ATTRIBUTE_NOTES = "NOTES";

	//table name
	public String TABLE = "FREIGHT_ADVICE_VIEW";


	//constructor
	public FreightAdviceViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("scheduledShipDate")) {
			return ATTRIBUTE_SCHEDULED_SHIP_DATE;
		}
		else if(attributeName.equals("shipVia")) {
			return ATTRIBUTE_SHIP_VIA;
		}
		else if(attributeName.equals("ultimateDodaac")) {
			return ATTRIBUTE_ULTIMATE_DODAAC;
		}
		else if(attributeName.equals("ultimateShipTo")) {
			return ATTRIBUTE_ULTIMATE_SHIP_TO;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("carrierTrackingNumber")) {
			return ATTRIBUTE_CARRIER_TRACKING_NUMBER;
		}
		else if(attributeName.equals("transportationMode")) {
			return ATTRIBUTE_TRANSPORTATION_MODE;
		}
		else if(attributeName.equals("trailerNumber")) {
			return ATTRIBUTE_TRAILER_NUMBER;
		}
		else if(attributeName.equals("stopNumber")) {
			return ATTRIBUTE_STOP_NUMBER;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("weight")) {
			return ATTRIBUTE_WEIGHT;
		}
		else if(attributeName.equals("cube")) {
			return ATTRIBUTE_CUBE;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("consolidationNumber")) {
			return ATTRIBUTE_CONSOLIDATION_NUMBER;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FreightAdviceViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(FreightAdviceViewBean freightAdviceViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("packingGroupId", "SearchCriterion.EQUALS",
			"" + freightAdviceViewBean.getPackingGroupId());

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


	public int delete(FreightAdviceViewBean freightAdviceViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("packingGroupId", "SearchCriterion.EQUALS",
			"" + freightAdviceViewBean.getPackingGroupId());

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
	public int insert(FreightAdviceViewBean freightAdviceViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(freightAdviceViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(FreightAdviceViewBean freightAdviceViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PACKING_GROUP_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_SCHEDULED_SHIP_DATE + "," +
			ATTRIBUTE_SHIP_VIA + "," +
			ATTRIBUTE_ULTIMATE_DODAAC + "," +
			ATTRIBUTE_ULTIMATE_SHIP_TO + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_CARRIER_TRACKING_NUMBER + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "," +
			ATTRIBUTE_TRAILER_NUMBER + "," +
			ATTRIBUTE_STOP_NUMBER + "," +
			ATTRIBUTE_MR_LINE + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FAC_PART_NO + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_WEIGHT + "," +
			ATTRIBUTE_CUBE + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_CONSOLIDATION_NUMBER + "," +
			ATTRIBUTE_MILSTRIP_CODE + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_DATE_SHIPPED + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "," +
			ATTRIBUTE_NOTES + ")" +
			" values (" +
			freightAdviceViewBean.getPackingGroupId() + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getHub()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getInventoryGroup()) + "," +
			DateHandler.getOracleToDateFunction(freightAdviceViewBean.getScheduledShipDate()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getShipVia()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getUltimateDodaac()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getUltimateShipTo()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getCarrierTrackingNumber()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getTransportationMode()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getTrailerNumber()) + "," +
			freightAdviceViewBean.getStopNumber() + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getMrLine()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getFacPartNo()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getQuantity()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getStatus()) + "," +
			freightAdviceViewBean.getWeight() + "," +
			freightAdviceViewBean.getCube() + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getPartShortName()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getConsolidationNumber()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getMilstripCode()) + "," +
			freightAdviceViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getLineItem()) + "," +
			DateHandler.getOracleToDateFunction(freightAdviceViewBean.getDateShipped()) + "," +
			DateHandler.getOracleToDateFunction(freightAdviceViewBean.getShipConfirmDate()) + "," +
			SqlHandler.delimitString(freightAdviceViewBean.getNotes()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(FreightAdviceViewBean freightAdviceViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(freightAdviceViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(FreightAdviceViewBean freightAdviceViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PACKING_GROUP_ID + "=" + 
				StringHandler.nullIfZero(freightAdviceViewBean.getPackingGroupId()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getHub()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_SCHEDULED_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(freightAdviceViewBean.getScheduledShipDate()) + "," +
			ATTRIBUTE_SHIP_VIA + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getShipVia()) + "," +
			ATTRIBUTE_ULTIMATE_DODAAC + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getUltimateDodaac()) + "," +
			ATTRIBUTE_ULTIMATE_SHIP_TO + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getUltimateShipTo()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getCarrierName()) + "," +
			ATTRIBUTE_CARRIER_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getCarrierTrackingNumber()) + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getTransportationMode()) + "," +
			ATTRIBUTE_TRAILER_NUMBER + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getTrailerNumber()) + "," +
			ATTRIBUTE_STOP_NUMBER + "=" + 
				StringHandler.nullIfZero(freightAdviceViewBean.getStopNumber()) + "," +
			ATTRIBUTE_MR_LINE + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getMrLine()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FAC_PART_NO + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getFacPartNo()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getQuantity()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getStatus()) + "," +
			ATTRIBUTE_WEIGHT + "=" + 
				StringHandler.nullIfZero(freightAdviceViewBean.getWeight()) + "," +
			ATTRIBUTE_CUBE + "=" + 
				StringHandler.nullIfZero(freightAdviceViewBean.getCube()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getPartShortName()) + "," +
			ATTRIBUTE_CONSOLIDATION_NUMBER + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getConsolidationNumber()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getMilstripCode()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(freightAdviceViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getLineItem()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(freightAdviceViewBean.getDateShipped()) + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "=" + 
				DateHandler.getOracleToDateFunction(freightAdviceViewBean.getShipConfirmDate()) + "," +
			ATTRIBUTE_NOTES + "=" + 
				SqlHandler.delimitString(freightAdviceViewBean.getNotes()) + " " +
			"where " + ATTRIBUTE_PACKING_GROUP_ID + "=" +
				freightAdviceViewBean.getPackingGroupId();

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

		Collection freightAdviceViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FreightAdviceViewBean freightAdviceViewBean = new FreightAdviceViewBean();
			load(dataSetRow, freightAdviceViewBean);
			freightAdviceViewBeanColl.add(freightAdviceViewBean);
		}

		return freightAdviceViewBeanColl;
	}
}