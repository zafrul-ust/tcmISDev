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
import com.tcmis.internal.hub.beans.ReceivingReportViewBean;


/******************************************************************************
 * CLASSNAME: ReceivingReportViewBeanFactory <br>
 * @version: 1.0, Oct 20, 2008 <br>
 *****************************************************************************/


public class ReceivingReportViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";

	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
	public String ATTRIBUTE_RELEASE_NO = "RELEASE_NO";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_ITEM_UOM = "ITEM_UOM";
	public String ATTRIBUTE_QUANTITY_RECEIVED_ITEM_UOM = "QUANTITY_RECEIVED_ITEM_UOM";
	public String ATTRIBUTE_QUANTITY_RETURNED_ITEM_UOM = "QUANTITY_RETURNED_ITEM_UOM";
	public String ATTRIBUTE_PO_LINE_QUANTITY_ITEM_UOM = "PO_LINE_QUANTITY_ITEM_UOM";
	public String ATTRIBUTE_UOS = "UOM";
	public String ATTRIBUTE_QUANTITY_RECEIVED_UOS = "QUANTITY_RECEIVED_UOS";
	public String ATTRIBUTE_QUANTITY_RETURNED_UOS = "QUANTITY_RETURNED_UOS";
	public String ATTRIBUTE_PO_LINE_QUANTITY_UOS = "PO_LINE_QUANTITY_UOS";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
	public String ATTRIBUTE_PO_LINE_QUANTITY = "PO_LINE_QUANTITY";

	//table name
	public String TABLE = "customer_receipt_history_view";


	//constructor
	public ReceivingReportViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("dateOfReceipt")) {
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else if(attributeName.equals("releaseNo")) {
			return ATTRIBUTE_RELEASE_NO;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("itemUom")) {
			return ATTRIBUTE_ITEM_UOM;
		}
		else if(attributeName.equals("quantityReceivedItemUom")) {
			return ATTRIBUTE_QUANTITY_RECEIVED_ITEM_UOM;
		}
		else if(attributeName.equals("quantityReturnedItemUom")) {
			return ATTRIBUTE_QUANTITY_RETURNED_ITEM_UOM;
		}
		else if(attributeName.equals("poLineQuantityItemUom")) {
			return ATTRIBUTE_PO_LINE_QUANTITY_ITEM_UOM;
		}
		else if(attributeName.equals("uos")) {
			return ATTRIBUTE_UOS;
		}
		else if(attributeName.equals("quantityReceivedUos")) {
			return ATTRIBUTE_QUANTITY_RECEIVED_UOS;
		}
		else if(attributeName.equals("quantityReturnedUos")) {
			return ATTRIBUTE_QUANTITY_RETURNED_UOS;
		}
		else if(attributeName.equals("poLineQuantityUos")) {
			return ATTRIBUTE_PO_LINE_QUANTITY_UOS;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("quantityReceived")) {
			return ATTRIBUTE_QUANTITY_RECEIVED;
		}
		else if(attributeName.equals("poLineQuantity")) {
			return ATTRIBUTE_PO_LINE_QUANTITY;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ReceivingReportViewBean.class);
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

		Collection receivingReportViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +getWhereClause(criteria)+" "+getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReceivingReportViewBean receivingReportViewBean = new ReceivingReportViewBean();
			load(dataSetRow, receivingReportViewBean);
			receivingReportViewBeanColl.add(receivingReportViewBean);
		}

		return receivingReportViewBeanColl;
	}

	//select
	public Collection selectData(String query)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectData(connection,query);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectData(Connection conn, String query)
		throws BaseException {

		Collection receivingReportViewBeanColl = new Vector();

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReceivingReportViewBean receivingReportViewBean = new ReceivingReportViewBean();
			load(dataSetRow, receivingReportViewBean);
			receivingReportViewBeanColl.add(receivingReportViewBean);
		}

		return receivingReportViewBeanColl;
	}
}