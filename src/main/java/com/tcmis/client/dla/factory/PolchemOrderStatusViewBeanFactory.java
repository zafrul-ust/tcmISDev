package com.tcmis.client.dla.factory;


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
import com.tcmis.client.dla.beans.PolchemOrderStatusViewBean;


/******************************************************************************
 * CLASSNAME: FreightAdviceViewBeanFactory <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class PolchemOrderStatusViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_CURRENT_STATUS = "CURRENT_STATUS";
	public String ATTRIBUTE_CURRENT_STATUS_DATE = "CURRENT_STATUS_DATE";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
	public String ATTRIBUTE_ORDER_RECEIVED_DATE = "ORDER_RECEIVED_DATE";
	public String ATTRIBUTE_ORDER_STATUS = "ORDER_STATUS";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ORDER_CREATED_DATE = "ORDER_CREATED_DATE";
	public String ATTRIBUTE_ALLOCATED_DATE = "ALLOCATED_DATE";
	public String ATTRIBUTE_BACKORDERED_DATE = "BACKORDERED_DATE";
	public String ATTRIBUTE_RELEASED_DATE = "RELEASED_DATE";
	public String ATTRIBUTE_FA_SENT_DATE = "FA_SENT_DATE";
	public String ATTRIBUTE_FA_RECEIVED_DATE = "FA_RECEIVED_DATE";
	public String ATTRIBUTE_PICKLIST_PRINT_DATE = "PICKLIST_PRINT_DATE";
	public String ATTRIBUTE_PACKED_DATE = "PACKED_DATE";
	public String ATTRIBUTE_SHIPPED_DATE = "SHIPPED_DATE";
	public String ATTRIBUTE_SC_SENT_DATE = "SC_SENT_DATE";
	
	//table name
	public String TABLE = "POLCHEM_ORDER_STATUS_VIEW";


	//constructor
	public PolchemOrderStatusViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("currentStatus")) {
			return ATTRIBUTE_CURRENT_STATUS;
		}
		else if(attributeName.equals("currentStatusDate")) {
			return ATTRIBUTE_CURRENT_STATUS_DATE;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("quanty")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("ownerCompanyId")) {
			return ATTRIBUTE_OWNER_COMPANY_ID;
		}
		else if(attributeName.equals("orderReceivedDate")) {
			return ATTRIBUTE_ORDER_RECEIVED_DATE;
		}
		else if(attributeName.equals("orderStatus")) {
			return ATTRIBUTE_ORDER_STATUS;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("orderCreatedDate")) {
			return ATTRIBUTE_ORDER_CREATED_DATE;
		}
		else if(attributeName.equals("allocatedDate")) {
			return ATTRIBUTE_ALLOCATED_DATE;
		}
		else if(attributeName.equals("backorderedDate")) {
			return ATTRIBUTE_BACKORDERED_DATE;
		}
		else if(attributeName.equals("releasedDate")) {
			return ATTRIBUTE_RELEASED_DATE;
		}
		else if(attributeName.equals("faSentDate")) {
			return ATTRIBUTE_FA_SENT_DATE;
		}
		else if(attributeName.equals("faReceivedDate")) {
			return ATTRIBUTE_FA_RECEIVED_DATE;
		}
		else if(attributeName.equals("picklistPrintDate")) {
			return ATTRIBUTE_PICKLIST_PRINT_DATE;
		}
		else if(attributeName.equals("packedDate")) {
			return ATTRIBUTE_PACKED_DATE;
		}
		else if(attributeName.equals("shippedDate")) {
			return ATTRIBUTE_SHIPPED_DATE;
		}
		else if(attributeName.equals("scSentDate")) {
			return ATTRIBUTE_SC_SENT_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PolchemOrderStatusViewBean.class);
	}

	/*public int delete(SearchCriteria criteria)
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

		Collection polchemOrderStatusViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PolchemOrderStatusViewBean freightAdviceViewBean = new PolchemOrderStatusViewBean();
			load(dataSetRow, freightAdviceViewBean);
			polchemOrderStatusViewBeanColl.add(freightAdviceViewBean);
		}

		return polchemOrderStatusViewBeanColl;
	}
}