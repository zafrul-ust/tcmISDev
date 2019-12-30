package com.tcmis.client.dla.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.SQLException;

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
import com.tcmis.client.dla.beans.DlaDailyShipStatusBean; 


/******************************************************************************
 * CLASSNAME: DlaVmiBeanFactory <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class DlaDailyShipStatusBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE"; 
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
        public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";

	//table name
	public String TABLE = "DLA_DAILY_SHIP_STATUS";


	//constructor
	public DlaDailyShipStatusBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DlaDailyShipStatusBean.class);
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

		Collection dlaVmiBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DlaDailyShipStatusBean dlaVmiBean = new DlaDailyShipStatusBean();
			load(dataSetRow, dlaVmiBean);
			dlaVmiBeanColl.add(dlaVmiBean);
		}

		return dlaVmiBeanColl;
	}
        
        public Collection select(String searchDate) throws BaseException {
 
           String query = "select distinct rlie.desired_ship_date need_date, po.customer_po, po.radian_po, sup.supplier_name , issue.SHIP_CONFIRM_DATE, location.location_desc ";
           query += "from buy_order bo, po , supplier sup, customer.request_line_item_extension rlie, customer.issue, location ";
           query += "where bo.mr_number in ";
           query += " ( select pr_number from customer.customer_po_stage x where company_id='USGOV' and trading_partner='dla' and transaction_type='850' and status not in ('TEST','IGNORE', 'CANCELLED') ) ";
           query += "and po.radian_po = bo.radian_po ";
           query += "and trunc(rlie.desired_ship_date) = trunc( to_date('" + searchDate  + "','MM-DD-YYYY') ) ";
           query += "and po.supplier = sup.supplier ";
           query += "and rlie.pr_number = bo.mr_number ";
           query += "and issue.ship_from_location_id = location.location_id (+)";
           query += "and bo.MR_NUMBER = issue.PR_NUMBER (+) ";
           query += "order by issue.ship_confirm_date ";
           
           
           Collection dlaVmiBeanColl = new Vector();

           Connection connection = this.getDbManager().getConnection();              
           
           DataSet dataSet = new SqlManager().select(connection, query);
           Iterator dataIter = dataSet.iterator();
           while (dataIter.hasNext()) {
                DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                DlaDailyShipStatusBean dlaVmiBean = new DlaDailyShipStatusBean();
                load(dataSetRow, dlaVmiBean);
                dlaVmiBeanColl.add(dlaVmiBean);
           }           

           this.getDbManager().returnConnection(connection);
           
           return dlaVmiBeanColl;           
        }
        
 }