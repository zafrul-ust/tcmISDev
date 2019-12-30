package com.tcmis.client.purchasing.factory;


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
import com.tcmis.client.purchasing.beans.BuyerCompanyPoViewBean;


/******************************************************************************
 * CLASSNAME: BuyerCompanyPoViewBeanFactory <br>
 * @version: 1.0, Apr 8, 2008 <br>
 *****************************************************************************/


public class BuyerCompanyPoViewBeanFactory extends BaseBeanFactory {

	//column names
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_DATE_ISSUED = "DATE_ISSUED";
	public String ATTRIBUTE_DEFAULT_BUYER_ID = "DEFAULT_BUYER_ID";
	public String ATTRIBUTE_EXPECTED_DELIVERY_DATE = "EXPECTED_DELIVERY_DATE";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_QUANTITY_TO_RECEIVE = "QUANTITY_TO_RECEIVE";
	public String ATTRIBUTE_READ_ONLY = "READ_ONLY";
	public String ATTRIBUTE_SIZE_UNIT = "SIZE_UNIT";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	Log log = LogFactory.getLog(this.getClass());

	//table name
	public String TABLE = "BUYER_COMPANY_PO_VIEW";


	//constructor
	public BuyerCompanyPoViewBeanFactory(DbManager dbManager) {
		super(dbManager);
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

	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("userId")) {
			return ATTRIBUTE_USER_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("readOnly")) {
			return ATTRIBUTE_READ_ONLY;
		}
		else if(attributeName.equals("dateIssued")) {
			return ATTRIBUTE_DATE_ISSUED;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("defaultBuyerId")) {
			return ATTRIBUTE_DEFAULT_BUYER_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if(attributeName.equals("sizeUnit")) {
			return ATTRIBUTE_SIZE_UNIT;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("quantityToReceive")) {
			return ATTRIBUTE_QUANTITY_TO_RECEIVE;
		}
		else if(attributeName.equals("expectedDeliveryDate")) {
			return ATTRIBUTE_EXPECTED_DELIVERY_DATE;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, BuyerCompanyPoViewBean.class);
	}

	//select
	public Boolean hasPermission(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Boolean b = new Boolean(false);
		try {
			connection = this.getDbManager().getConnection();
			b = hasPermission(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return b;
	}
	public Boolean hasPermission(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String query = "select read_only from " + TABLE + " " +getWhereClause(criteria)+
				           " and read_only = 'N'";

		DataSet dataSet = new SqlManager().select(conn, query);
		return (new Boolean(dataSet.size() > 0));
	}

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

		Collection buyerCompanyPoViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BuyerCompanyPoViewBean buyerCompanyPoViewBean = new BuyerCompanyPoViewBean();
			load(dataSetRow, buyerCompanyPoViewBean);
			buyerCompanyPoViewBeanColl.add(buyerCompanyPoViewBean);
		}

		return buyerCompanyPoViewBeanColl;
	}

}