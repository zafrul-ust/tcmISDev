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
import com.tcmis.internal.hub.beans.ConsolidatedBolDetailViewBean;


/******************************************************************************
 * CLASSNAME: ConsolidatedBolDetailViewBeanFactory <br>
 * @version: 1.0, Mar 9, 2006 <br>
 *****************************************************************************/


public class ConsolidatedBolDetailViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_HAZARDOUS = "HAZARDOUS";
	public String ATTRIBUTE_DOT = "DOT";
	public String ATTRIBUTE_NET_WEIGHT_LB = "NET_WEIGHT_LB";
        public String ATTRIBUTE_MR_LINE = "MR_LINE";
        public String ATTRIBUTE_MAJOR_HAZARD_CLASS = "MAJOR_HAZARD_CLASS";
        public String ATTRIBUTE_TOTAL_NET_WEIGHT_LB = "TOTAL_NET_WEIGHT_LB";
        public String ATTRIBUTE_TOTAL_NET_WT_FOR_MHC = "TOTAL_NET_WT_FOR_MHC";

	//table name
	public String TABLE = "CONSOLIDATED_BOL_DETAIL_VIEW";


	//constructor
	public ConsolidatedBolDetailViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("hazardous")) {
			return ATTRIBUTE_HAZARDOUS;
		}
		else if(attributeName.equals("dot")) {
			return ATTRIBUTE_DOT;
		}
		else if(attributeName.equals("netWeightLb")) {
			return ATTRIBUTE_NET_WEIGHT_LB;
                }
                else if(attributeName.equals("mrLine")) {
                        return ATTRIBUTE_MR_LINE;
                }
                else if(attributeName.equals("majorHazardClass")) {
                        return ATTRIBUTE_MAJOR_HAZARD_CLASS;
                }
                else if(attributeName.equals("totalNetWeightLb")) {
                        return ATTRIBUTE_TOTAL_NET_WEIGHT_LB;
                }
                else if(attributeName.equals("totalNetWtForMhc")) {
                        return ATTRIBUTE_TOTAL_NET_WT_FOR_MHC;
                }
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ConsolidatedBolDetailViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + consolidatedBolDetailViewBean.getShipmentId());

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


	public int delete(ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + consolidatedBolDetailViewBean.getShipmentId());

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
	public int insert(ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(consolidatedBolDetailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_HAZARDOUS + "," +
			ATTRIBUTE_DOT + "," +
			ATTRIBUTE_NET_WEIGHT_LB + ")" +
			" values (" +
			consolidatedBolDetailViewBean.getShipmentId() + "," +
			SqlHandler.delimitString(consolidatedBolDetailViewBean.getHazardous()) + "," +
			SqlHandler.delimitString(consolidatedBolDetailViewBean.getDot()) + "," +
			consolidatedBolDetailViewBean.getNetWeightLb() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(consolidatedBolDetailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SHIPMENT_ID + "=" +
				StringHandler.nullIfZero(consolidatedBolDetailViewBean.getShipmentId()) + "," +
			ATTRIBUTE_HAZARDOUS + "=" +
				SqlHandler.delimitString(consolidatedBolDetailViewBean.getHazardous()) + "," +
			ATTRIBUTE_DOT + "=" +
				SqlHandler.delimitString(consolidatedBolDetailViewBean.getDot()) + "," +
			ATTRIBUTE_NET_WEIGHT_LB + "=" +
				StringHandler.nullIfZero(consolidatedBolDetailViewBean.getNetWeightLb()) + " " +
			"where " + ATTRIBUTE_SHIPMENT_ID + "=" +
				consolidatedBolDetailViewBean.getShipmentId();

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

		Collection consolidatedBolDetailViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean = new ConsolidatedBolDetailViewBean();
			load(dataSetRow, consolidatedBolDetailViewBean);
			consolidatedBolDetailViewBeanColl.add(consolidatedBolDetailViewBean);
		}

		return consolidatedBolDetailViewBeanColl;
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

                Collection consolidatedBolDetailViewBeanColl = new Vector();
                //the reason that I do it this way to for speed.  The view does not returns if I say shipment_id in(..,..)
                String query = "select SHIPMENT_ID, HAZARDOUS, DOT, NET_WEIGHT_LB, MR_LINE, TOTAL_NET_WEIGHT_LB from " + TABLE + " where shipment_id = "+shipmentId[0];
                for (int i = 1; i < shipmentId.length; i++) {
                  query += " union all select SHIPMENT_ID, HAZARDOUS, DOT, NET_WEIGHT_LB, MR_LINE, TOTAL_NET_WEIGHT_LB from " + TABLE + " where shipment_id = "+shipmentId[i];
                }
                query += " order by shipment_id,HAZARDOUS";

                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean = new ConsolidatedBolDetailViewBean();
                        load(dataSetRow, consolidatedBolDetailViewBean);
                        consolidatedBolDetailViewBeanColl.add(consolidatedBolDetailViewBean);
                }
                return consolidatedBolDetailViewBeanColl;
        }

        public Collection selectForTotalNetWeightMajorHazardClass(String[] shipmentId)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = selectForTotalNetWeightMajorHazardClass(shipmentId, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection selectForTotalNetWeightMajorHazardClass(String[] shipmentId, Connection conn)
                throws BaseException {

                Collection consolidatedBolDetailViewBeanColl = new Vector();
                //the reason that I do it this way to for speed.  The view does not returns if I say shipment_id in(..,..)
                String query = "select shipment_id,major_hazard_class, sum(net_weight_lb)||' lbs ' || major_hazard_class total_net_wt_for_mhc"+
                               " from " + TABLE + " where shipment_id = "+shipmentId[0]+
                               " group by shipment_id,major_hazard_class";
                for (int i = 1; i < shipmentId.length; i++) {
                  query += " union all select SHIPMENT_ID,major_hazard_class, sum(net_weight_lb)||' lbs ' || major_hazard_class total_net_wt_for_mhc"+
                               " from " + TABLE + " where shipment_id = "+shipmentId[i]+
                               " group by shipment_id,major_hazard_class";
                }
                query += " order by shipment_id,major_hazard_class";

                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean = new ConsolidatedBolDetailViewBean();
                        load(dataSetRow, consolidatedBolDetailViewBean);
                        consolidatedBolDetailViewBeanColl.add(consolidatedBolDetailViewBean);
                }
                return consolidatedBolDetailViewBeanColl;
        }

}