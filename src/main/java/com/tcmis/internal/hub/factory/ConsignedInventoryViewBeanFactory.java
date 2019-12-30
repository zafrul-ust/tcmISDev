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
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.hub.beans.ConsignedInventoryViewBean;


/******************************************************************************
 * CLASSNAME: ConsignedInventoryViewBeanFactory <br>
 * @version: 1.0, Apr 29, 2008 <br>
 *****************************************************************************/


public class ConsignedInventoryViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PO = "PO";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public String ATTRIBUTE_ISSUED = "ISSUED";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";

	//table name
	public String TABLE = "CONSIGNED_INVENTORY_VIEW";


	//constructor
	public ConsignedInventoryViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("po")) {
			return ATTRIBUTE_PO;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("dateOfReceipt")) {
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if(attributeName.equals("issued")) {
			return ATTRIBUTE_ISSUED;
		}
		else if(attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ConsignedInventoryViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ConsignedInventoryViewBean consignedInventoryViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + consignedInventoryViewBean.getInventoryGroup());

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


	public int delete(ConsignedInventoryViewBean consignedInventoryViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + consignedInventoryViewBean.getInventoryGroup());

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
	public int insert(ConsignedInventoryViewBean consignedInventoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(consignedInventoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ConsignedInventoryViewBean consignedInventoryViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_PO + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "," +
			ATTRIBUTE_ISSUED + "," +
			ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_CAT_PART_NO + ")" +
			" values (" +
			SqlHandler.delimitString(consignedInventoryViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getHubName()) + "," +
			consignedInventoryViewBean.getItemId() + "," +
			consignedInventoryViewBean.getPo() + "," +
			consignedInventoryViewBean.getUnitPrice() + "," +
			consignedInventoryViewBean.getReceiptId() + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getMfgLot()) + "," +
			DateHandler.getOracleToDateFunction(consignedInventoryViewBean.getDateOfReceipt()) + "," +
			consignedInventoryViewBean.getIssued() + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getPurchasingUnitOfMeasure()) + "," +
			DateHandler.getOracleToDateFunction(consignedInventoryViewBean.getShipConfirmDate()) + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(consignedInventoryViewBean.getCatPartNo()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ConsignedInventoryViewBean consignedInventoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(consignedInventoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ConsignedInventoryViewBean consignedInventoryViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getSupplierName()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getHubName()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(consignedInventoryViewBean.getItemId()) + "," +
			ATTRIBUTE_PO + "=" + 
				StringHandler.nullIfZero(consignedInventoryViewBean.getPo()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(consignedInventoryViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(consignedInventoryViewBean.getReceiptId()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getMfgLot()) + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "=" + 
				DateHandler.getOracleToDateFunction(consignedInventoryViewBean.getDateOfReceipt()) + "," +
			ATTRIBUTE_ISSUED + "=" + 
				StringHandler.nullIfZero(consignedInventoryViewBean.getIssued()) + "," +
			ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getPurchasingUnitOfMeasure()) + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "=" + 
				DateHandler.getOracleToDateFunction(consignedInventoryViewBean.getShipConfirmDate()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getItemDesc()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(consignedInventoryViewBean.getCatPartNo()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				consignedInventoryViewBean.getInventoryGroup();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection<ConsignedInventoryViewBean> select(SearchCriteria criteria, SortCriteria sortCriteria)
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
	public Collection<ConsignedInventoryViewBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection consignedInventoryViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ConsignedInventoryViewBean consignedInventoryViewBean = new ConsignedInventoryViewBean();
			load(dataSetRow, consignedInventoryViewBean);
			consignedInventoryViewBeanColl.add(consignedInventoryViewBean);
		}

		return consignedInventoryViewBeanColl;
	}

	public Collection<ConsignedInventoryViewBean> select(String hub, String inventoryGroup, BigDecimal itemId, Date startDate, Date endDate, String partNumber)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(hub,inventoryGroup, itemId, startDate, endDate, partNumber, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection<ConsignedInventoryViewBean> select(String hub, String inventoryGroup, BigDecimal itemId, Date startDate, Date endDate, String partNumber, Connection conn)
		throws BaseException {

		Collection consignedInventoryViewBeanColl = new Vector();

		String query = "select hub_name, inventory_group, supplier_name, po, item_id, item_desc, " +
                "sum(issued) issued, " +
                "purchasing_unit_of_measure, unit_price " +
                "from consigned_inventory_view a " +
                "where 1=1 ";
        if(!StringHandler.isBlankString(hub)) {
            query = query.concat(" and branch_plant = " + SqlHandler.delimitString(hub));
        }
        if(!StringHandler.isBlankString(inventoryGroup)) {
            query = query.concat(" and inventory_group = " + SqlHandler.delimitString(inventoryGroup));
        }
        if(itemId != null) {
            query = query.concat(" and item_id = " + itemId);
        }
        if(startDate != null || endDate != null){
            query = query.concat(" and exists (select issue_id from issue x where x.issue_id = a.issue_id ");
            if(startDate != null) {
                query = query.concat(" and x.SHIP_CONFIRM_DATE >= " + DateHandler.getOracleToDateFunction(startDate));
            }
            if(endDate != null){
              final long MILLISECONDS_PER_DAY = 86400000;
              Date newEndDate = new Date( endDate.getTime() + MILLISECONDS_PER_DAY );
              query = query.concat(" and x.SHIP_CONFIRM_DATE < " + DateHandler.getOracleToDateFunction(newEndDate));
            }
            query = query.concat(")");
        }
        query = query.concat(" group by hub_name, inventory_group, supplier_name, po, item_id, item_desc,purchasing_unit_of_measure,unit_price having abs(sum(issued)*unit_price) > 0.005");

        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ConsignedInventoryViewBean consignedInventoryViewBean = new ConsignedInventoryViewBean();
			load(dataSetRow, consignedInventoryViewBean);
			consignedInventoryViewBeanColl.add(consignedInventoryViewBean);
		}

		return consignedInventoryViewBeanColl;
	}
}