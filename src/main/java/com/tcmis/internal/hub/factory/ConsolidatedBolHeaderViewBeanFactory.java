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
import com.tcmis.internal.hub.beans.ConsolidatedBolHeaderViewBean;


/******************************************************************************
 * CLASSNAME: ConsolidatedBolHeaderViewBeanFactory <br>
 * @version: 1.0, Mar 9, 2006 <br>
 *****************************************************************************/


public class ConsolidatedBolHeaderViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_FROM_ADDRESS = "FROM_ADDRESS";
	public String ATTRIBUTE_TO_ADDRESS = "TO_ADDRESS";

	//table name
	public String TABLE = "CONSOLIDATED_BOL_HEADER_VIEW";


	//constructor
	public ConsolidatedBolHeaderViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("fromAddress")) {
			return ATTRIBUTE_FROM_ADDRESS;
		}
		else if(attributeName.equals("toAddress")) {
			return ATTRIBUTE_TO_ADDRESS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ConsolidatedBolHeaderViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + consolidatedBolHeaderViewBean.getShipmentId());

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


	public int delete(ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + consolidatedBolHeaderViewBean.getShipmentId());

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
	public int insert(ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(consolidatedBolHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_FROM_ADDRESS + "," +
			ATTRIBUTE_TO_ADDRESS + "," +
			ATTRIBUTE_MR_LINE + ")" +
			" values (" +
			consolidatedBolHeaderViewBean.getShipmentId() + "," +
			SqlHandler.delimitString(consolidatedBolHeaderViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(consolidatedBolHeaderViewBean.getTrackingNumber()) + "," +
			DateHandler.getOracleToDateFunction(consolidatedBolHeaderViewBean.getDateDelivered()) + "," +
			SqlHandler.delimitString(consolidatedBolHeaderViewBean.getFromAddress()) + "," +
			SqlHandler.delimitString(consolidatedBolHeaderViewBean.getToAddress()) + "," +
			SqlHandler.delimitString(consolidatedBolHeaderViewBean.getMrLine()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(consolidatedBolHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SHIPMENT_ID + "=" +
				StringHandler.nullIfZero(consolidatedBolHeaderViewBean.getShipmentId()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" +
				SqlHandler.delimitString(consolidatedBolHeaderViewBean.getCarrierName()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" +
				SqlHandler.delimitString(consolidatedBolHeaderViewBean.getTrackingNumber()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" +
				DateHandler.getOracleToDateFunction(consolidatedBolHeaderViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_FROM_ADDRESS + "=" +
				SqlHandler.delimitString(consolidatedBolHeaderViewBean.getFromAddress()) + "," +
			ATTRIBUTE_TO_ADDRESS + "=" +
				SqlHandler.delimitString(consolidatedBolHeaderViewBean.getToAddress()) + "," +
			ATTRIBUTE_MR_LINE + "=" +
				SqlHandler.delimitString(consolidatedBolHeaderViewBean.getMrLine()) + " " +
			"where " + ATTRIBUTE_SHIPMENT_ID + "=" +
				consolidatedBolHeaderViewBean.getShipmentId();

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

		Collection consolidatedBolHeaderViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean = new ConsolidatedBolHeaderViewBean();
			load(dataSetRow, consolidatedBolHeaderViewBean);
			consolidatedBolHeaderViewBeanColl.add(consolidatedBolHeaderViewBean);
		}

		return consolidatedBolHeaderViewBeanColl;
	}

        public Collection selectForConsolidatedBol(String[] shipmentId)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = selectForConsolidatedBol(shipmentId, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection selectForConsolidatedBol(String[] shipmentId, Connection conn)
                throws BaseException {

                Collection consolidatedBolHeaderViewBeanColl = new Vector();
                //the reason that I do it this way to for speed.  The view does not returns if I say shipment_id in(..,..)
                String query = "select SHIPMENT_ID, CARRIER_NAME, TRACKING_NUMBER, DATE_DELIVERED, FROM_ADDRESS, TO_ADDRESS"+
                               " from " + TABLE + " where shipment_id = "+shipmentId[0];
                for (int i = 1; i < shipmentId.length; i++) {
                  query += " union all select SHIPMENT_ID, CARRIER_NAME, TRACKING_NUMBER, DATE_DELIVERED, FROM_ADDRESS, TO_ADDRESS"+
                           " from " + TABLE + " where shipment_id = "+shipmentId[i];
                }
                query += " order by shipment_id";

                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean = new ConsolidatedBolHeaderViewBean();
                        load(dataSetRow, consolidatedBolHeaderViewBean);
                        consolidatedBolHeaderViewBeanColl.add(consolidatedBolHeaderViewBean);
                }
                return consolidatedBolHeaderViewBeanColl;
        }

}