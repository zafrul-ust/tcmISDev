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
import com.tcmis.internal.hub.beans.PackingListHeaderViewBean;


/******************************************************************************
 * CLASSNAME: PackingListHeaderViewBeanFactory <br>
 * @version: 1.0, Apr 21, 2005 <br>
 *****************************************************************************/


public class PackingListHeaderViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_DATE_PICKED = "DATE_PICKED";
	public String ATTRIBUTE_DATE_PRINTED = "DATE_PRINTED";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_TAX_ID = "TAX_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_HUB_COMPANY_ID = "HUB_COMPANY_ID";
	public String ATTRIBUTE_HUB_LOCATION_ID = "HUB_LOCATION_ID";
	public String ATTRIBUTE_SHIP_TO_ADDRESS = "SHIP_TO_ADDRESS";
	public String ATTRIBUTE_HUB_ADDRESS = "HUB_ADDRESS";

	//table name
	public String TABLE = "PACKING_LIST_HEADER_VIEW";


	//constructor
	public PackingListHeaderViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("datePicked")) {
			return ATTRIBUTE_DATE_PICKED;
		}
		else if(attributeName.equals("datePrinted")) {
			return ATTRIBUTE_DATE_PRINTED;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("taxId")) {
			return ATTRIBUTE_TAX_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("hubCompanyId")) {
			return ATTRIBUTE_HUB_COMPANY_ID;
		}
		else if(attributeName.equals("hubLocationId")) {
			return ATTRIBUTE_HUB_LOCATION_ID;
		}
		else if(attributeName.equals("shipToAddress")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS;
		}
		else if(attributeName.equals("hubAddress")) {
			return ATTRIBUTE_HUB_ADDRESS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PackingListHeaderViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PackingListHeaderViewBean packingListHeaderViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + packingListHeaderViewBean.getShipmentId());

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


	public int delete(PackingListHeaderViewBean packingListHeaderViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + packingListHeaderViewBean.getShipmentId());

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
	public int insert(PackingListHeaderViewBean packingListHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(packingListHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PackingListHeaderViewBean packingListHeaderViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_DATE_PICKED + "," +
			ATTRIBUTE_DATE_PRINTED + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_TAX_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_HUB_COMPANY_ID + "," +
			ATTRIBUTE_HUB_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + "," +
			ATTRIBUTE_HUB_ADDRESS + ")" +
			" values (" +
			packingListHeaderViewBean.getShipmentId() + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getDatePicked()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getDatePrinted()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getTaxId()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getHubCompanyId()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getHubLocationId()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getShipToAddress()) + "," +
			SqlHandler.delimitString(packingListHeaderViewBean.getHubAddress()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PackingListHeaderViewBean packingListHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(packingListHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PackingListHeaderViewBean packingListHeaderViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SHIPMENT_ID + "=" +
				StringHandler.nullIfZero(packingListHeaderViewBean.getShipmentId()) + "," +
			ATTRIBUTE_DATE_PICKED + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getDatePicked()) + "," +
			ATTRIBUTE_DATE_PRINTED + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getDatePrinted()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_TAX_ID + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getTaxId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_HUB_COMPANY_ID + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getHubCompanyId()) + "," +
			ATTRIBUTE_HUB_LOCATION_ID + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getHubLocationId()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getShipToAddress()) + "," +
			ATTRIBUTE_HUB_ADDRESS + "=" +
				SqlHandler.delimitString(packingListHeaderViewBean.getHubAddress()) + " " +
			"where " + ATTRIBUTE_SHIPMENT_ID + "=" +
				packingListHeaderViewBean.getShipmentId();

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

		Collection packingListHeaderViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PackingListHeaderViewBean packingListHeaderViewBean = new PackingListHeaderViewBean();
			load(dataSetRow, packingListHeaderViewBean);
			packingListHeaderViewBeanColl.add(packingListHeaderViewBean);
		}

		return packingListHeaderViewBeanColl;
	}

        public Collection selectForPackingList(String[] shipmentId)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = selectForPackingList(shipmentId, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection selectForPackingList(String[] shipmentId, Connection conn)
                throws BaseException {

                Collection packingListHeaderViewBeanColl = new Vector();

                String query = "select * from " + TABLE + " where shipment_id in (";
                for (int i = 0; i < shipmentId.length; i++) {
                  query += shipmentId[i]+",";
                }
                //remove the last commas ',' and add close parathensis
                query = query.substring(0,query.length()-1)+")";
                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        PackingListHeaderViewBean packingListHeaderViewBean = new PackingListHeaderViewBean();
                        load(dataSetRow, packingListHeaderViewBean);
                        packingListHeaderViewBeanColl.add(packingListHeaderViewBean);
                }
                return packingListHeaderViewBeanColl;
        }
}