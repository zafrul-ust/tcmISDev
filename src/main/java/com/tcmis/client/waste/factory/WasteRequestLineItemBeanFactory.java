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
import com.tcmis.client.waste.beans.WasteRequestLineItemBean;


/******************************************************************************
 * CLASSNAME: WasteRequestLineItemBeanFactory <br>
 * @version: 1.0, Mar 20, 2007 <br>
 *****************************************************************************/


public class WasteRequestLineItemBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_WASTE_REQUEST_ID = "WASTE_REQUEST_ID";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_NOTE = "NOTE";
	public String ATTRIBUTE_REPLACE_CONTAINER = "REPLACE_CONTAINER";
	public String ATTRIBUTE_TRAVELER_ID = "TRAVELER_ID";
	public String ATTRIBUTE_GENERATION_POINT = "GENERATION_POINT";
	public String ATTRIBUTE_SEAL_DATE = "SEAL_DATE";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_REQUESTED_TRANSFER_DATE = "REQUESTED_TRANSFER_DATE";
	public String ATTRIBUTE_REQUESTED_TRANSFER_TIME = "REQUESTED_TRANSFER_TIME";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
	public String ATTRIBUTE_RELEASE_DATE = "RELEASE_DATE";
	public String ATTRIBUTE_TAG_NUMBER = "TAG_NUMBER";

	//table name
	public String TABLE = "WASTE_REQUEST_LINE_ITEM";


	//constructor
	public WasteRequestLineItemBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("wasteRequestId")) {
			return ATTRIBUTE_WASTE_REQUEST_ID;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("note")) {
			return ATTRIBUTE_NOTE;
		}
		else if(attributeName.equals("replaceContainer")) {
			return ATTRIBUTE_REPLACE_CONTAINER;
		}
		else if(attributeName.equals("travelerId")) {
			return ATTRIBUTE_TRAVELER_ID;
		}
		else if(attributeName.equals("generationPoint")) {
			return ATTRIBUTE_GENERATION_POINT;
		}
		else if(attributeName.equals("sealDate")) {
			return ATTRIBUTE_SEAL_DATE;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("requestedTransferDate")) {
			return ATTRIBUTE_REQUESTED_TRANSFER_DATE;
		}
		else if(attributeName.equals("requestedTransferTime")) {
			return ATTRIBUTE_REQUESTED_TRANSFER_TIME;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("releaseNumber")) {
			return ATTRIBUTE_RELEASE_NUMBER;
		}
		else if(attributeName.equals("releaseDate")) {
			return ATTRIBUTE_RELEASE_DATE;
		}
		else if(attributeName.equals("tagNumber")) {
			return ATTRIBUTE_TAG_NUMBER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, WasteRequestLineItemBean.class);
	}


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

	//update
	public int updateTagNumber(SearchCriteria criteria,String tagNumber)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateTagNumber(criteria,connection,tagNumber);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int updateTagNumber(SearchCriteria criteria,Connection conn,String tagNumber)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TAG_NUMBER + "= '" +tagNumber+"' "+
			getWhereClause(criteria);
        log.debug(query);
		return new SqlManager().update(conn, query);
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

		Collection wasteRequestLineItemBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WasteRequestLineItemBean wasteRequestLineItemBean = new WasteRequestLineItemBean();
			load(dataSetRow, wasteRequestLineItemBean);
			wasteRequestLineItemBeanColl.add(wasteRequestLineItemBean);
		}

		return wasteRequestLineItemBeanColl;
	}
}