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
import com.tcmis.client.waste.beans.WasteLocationBean;


/******************************************************************************
 * CLASSNAME: WasteLocationBeanFactory <br>
 * @version: 1.0, Jan 9, 2007 <br>
 *****************************************************************************/


public class WasteLocationBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_WASTE_LOCATION_ID = "WASTE_LOCATION_ID";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_LOCATION_TYPE = "LOCATION_TYPE";
	public String ATTRIBUTE_EPA_ID = "EPA_ID";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_HUB_BASED = "HUB_BASED";
	public String ATTRIBUTE_TRANSFER_TO_LOCATION_DEFAULT = "TRANSFER_TO_LOCATION_DEFAULT";
	public String ATTRIBUTE_AUTO_TRANSFER_REQUEST = "AUTO_TRANSFER_REQUEST";
        public String ATTRIBUTE_TSDF = "TSDF";
        public String ATTRIBUTE_SEND_EMAIL = "SEND_EMAIL";
        public String ATTRIBUTE_LOCATION_GROUP = "LOCATION_GROUP";
        public String ATTRIBUTE_TAG_NUMBER_DISPLAY = "TAG_NUMBER_DISPLAY";

	//table name
	public String TABLE = "WASTE_LOCATION";


	//constructor
	public WasteLocationBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("wasteLocationId")) {
			return ATTRIBUTE_WASTE_LOCATION_ID;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if(attributeName.equals("locationType")) {
			return ATTRIBUTE_LOCATION_TYPE;
		}
		else if(attributeName.equals("epaId")) {
			return ATTRIBUTE_EPA_ID;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("hubBased")) {
			return ATTRIBUTE_HUB_BASED;
		}
		else if(attributeName.equals("transferToLocationDefault")) {
			return ATTRIBUTE_TRANSFER_TO_LOCATION_DEFAULT;
		}
		else if(attributeName.equals("autoTransferRequest")) {
			return ATTRIBUTE_AUTO_TRANSFER_REQUEST;
		}
                else if(attributeName.equals("tsdf")) {
                        return ATTRIBUTE_TSDF;
                }
                else if(attributeName.equals("sendEmail")) {
                        return ATTRIBUTE_SEND_EMAIL;
                }
                else if(attributeName.equals("locationGroup")) {
                        return ATTRIBUTE_LOCATION_GROUP;
                }
                else if(attributeName.equals("tagNumberDisplay")) {
                        return ATTRIBUTE_TAG_NUMBER_DISPLAY;
                }
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, WasteLocationBean.class);
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

		Collection wasteLocationBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);
                log.debug(query);
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WasteLocationBean wasteLocationBean = new WasteLocationBean();
			load(dataSetRow, wasteLocationBean);
			wasteLocationBeanColl.add(wasteLocationBean);
		}

		return wasteLocationBeanColl;
	}
}