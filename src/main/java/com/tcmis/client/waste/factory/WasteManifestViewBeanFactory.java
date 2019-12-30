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
import com.tcmis.client.waste.beans.WasteManifestViewBean;


/******************************************************************************
 * CLASSNAME: WasteManifestViewBeanFactory <br>
 * @version: 1.0, Mar 21, 2007 <br>
 *****************************************************************************/


public class WasteManifestViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_MANIFEST_ID = "MANIFEST_ID";
	public String ATTRIBUTE_ORDER_NO = "ORDER_NO";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_STORAGE_LOCATION_ID = "STORAGE_LOCATION_ID";
	public String ATTRIBUTE_VENDOR_ID = "VENDOR_ID";
	public String ATTRIBUTE_VENDOR_NAME = "VENDOR_NAME";
	public String ATTRIBUTE_ORDER_SUMBMIT_DATE = "ORDER_SUMBMIT_DATE";
	public String ATTRIBUTE_ACTUAL_SHIP_DATE = "ACTUAL_SHIP_DATE";
	public String ATTRIBUTE_TSDF = "TSDF";
	public String ATTRIBUTE_STORAGE_LOCATION_EPA_ID = "STORAGE_LOCATION_EPA_ID";
	public String ATTRIBUTE_LOCATION_GROUP = "LOCATION_GROUP";
	public String ATTRIBUTE_SHIP_TO_SEARCH_STRING = "SHIP_TO_SEARCH_STRING";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	//table name
	public String TABLE = "WASTE_MANIFEST_VIEW";


	//constructor
	public WasteManifestViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("manifestId")) {
			return ATTRIBUTE_MANIFEST_ID;
		}
		else if(attributeName.equals("orderNo")) {
			return ATTRIBUTE_ORDER_NO;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("storageLocationId")) {
			return ATTRIBUTE_STORAGE_LOCATION_ID;
		}
		else if(attributeName.equals("vendorId")) {
			return ATTRIBUTE_VENDOR_ID;
		}
		else if(attributeName.equals("vendorName")) {
			return ATTRIBUTE_VENDOR_NAME;
		}
		else if(attributeName.equals("orderSumbmitDate")) {
			return ATTRIBUTE_ORDER_SUMBMIT_DATE;
		}
		else if(attributeName.equals("actualShipDate")) {
			return ATTRIBUTE_ACTUAL_SHIP_DATE;
		}
		else if(attributeName.equals("tsdf")) {
			return ATTRIBUTE_TSDF;
		}
		else if(attributeName.equals("storageLocationEpaId")) {
			return ATTRIBUTE_STORAGE_LOCATION_EPA_ID;
		}
		else if(attributeName.equals("locationGroup")) {
			return ATTRIBUTE_LOCATION_GROUP;
		}
		else if(attributeName.equals("shipToSearchString")) {
			return ATTRIBUTE_SHIP_TO_SEARCH_STRING;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, WasteManifestViewBean.class);
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

		Collection wasteManifestViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WasteManifestViewBean wasteManifestViewBean = new WasteManifestViewBean();
			load(dataSetRow, wasteManifestViewBean);
			wasteManifestViewBeanColl.add(wasteManifestViewBean);
		}

		return wasteManifestViewBeanColl;
	}

        //select
        public Collection select2(String manifest)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = select2(connection,manifest);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection select2(Connection conn, String manifest)
                throws BaseException {

                Collection wasteManifestViewBeanColl = new Vector();

                String query = "select manifest_id from table (pkg_wst_manifest.fx_waste_manifest_ancestor('"+manifest+"'))";

                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        WasteManifestViewBean wasteManifestViewBean = new WasteManifestViewBean();
                        load(dataSetRow, wasteManifestViewBean);
                        wasteManifestViewBeanColl.add(wasteManifestViewBean);
                }

                return wasteManifestViewBeanColl;
        }

}