package com.tcmis.internal.supply.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.supply.beans.PoSearchResultBean;


/******************************************************************************
 * CLASSNAME: PoSearchResultBeanFactory <br>
 * @version: 1.0, Sep 22, 2006 <br>
 *****************************************************************************/


public class PoSearchResultBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_EXT_PRICE = "EXT_PRICE";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME"; 
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_DATE_CREATED = "DATE_CREATED";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_REVISED_PROMISED_DATE = "REVISED_PROMISED_DATE";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_SHIP_TO_ADDRESS = "SHIP_TO_ADDRESS";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_QTY_RECEIVED = "QTY_RECEIVED";
	public String ATTRIBUTE_DATE_CONFIRMED = "DATE_CONFIRMED";
	
	//table name
	public String TABLE = "PO_SEARCH_RESULT";


	//constructor
	public PoSearchResultBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("extPrice")) {
			return ATTRIBUTE_EXT_PRICE;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("dateCreated")) {
			return ATTRIBUTE_DATE_CREATED;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("revisedPromisedDate")) {
			return ATTRIBUTE_REVISED_PROMISED_DATE;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("shipToAddress")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS;
		}
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("qtyReceived")) {
			return ATTRIBUTE_QTY_RECEIVED;
		}
		else if(attributeName.equals("dateConfirmed")) {
			return ATTRIBUTE_DATE_CONFIRMED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoSearchResultBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoSearchResultBean poSearchResultBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("critical", "SearchCriterion.EQUALS",
			"" + poSearchResultBean.getCritical());

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


	public int delete(PoSearchResultBean poSearchResultBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("critical", "SearchCriterion.EQUALS",
			"" + poSearchResultBean.getCritical());

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
	public int insert(PoSearchResultBean poSearchResultBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poSearchResultBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoSearchResultBean poSearchResultBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_EXT_PRICE + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_BUYER + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_DATE_CREATED + "," +
			ATTRIBUTE_DATE_SENT + ")" +
 values (
			SqlHandler.delimitString(poSearchResultBean.getCritical()) + "," +
			StringHandler.nullIfZero(poSearchResultBean.getItemId()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getItemDesc()) + "," +
			StringHandler.nullIfZero(poSearchResultBean.getUnitPrice()) + "," +
			StringHandler.nullIfZero(poSearchResultBean.getQuantity()) + "," +
			StringHandler.nullIfZero(poSearchResultBean.getExtPrice()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getPaymentTerms()) + "," +
			StringHandler.nullIfZero(poSearchResultBean.getRadianPo()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getSupplier()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getSupplierName()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getBuyerName()) + "," +
			StringHandler.nullIfZero(poSearchResultBean.getBuyer()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getHubName()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getDateCreated()) + "," +
			SqlHandler.delimitString(poSearchResultBean.getDateSent()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoSearchResultBean poSearchResultBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poSearchResultBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoSearchResultBean poSearchResultBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getCritical()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(poSearchResultBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getItemDesc()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(poSearchResultBean.getUnitPrice()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(poSearchResultBean.getQuantity()) + "," +
			ATTRIBUTE_EXT_PRICE + "=" + 
				StringHandler.nullIfZero(poSearchResultBean.getExtPrice()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getPaymentTerms()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poSearchResultBean.getRadianPo()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getSupplierName()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getBuyerName()) + "," +
			ATTRIBUTE_BUYER + "=" + 
				StringHandler.nullIfZero(poSearchResultBean.getBuyer()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getBranchPlant()) + "," +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getHubName()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getInventoryGroup()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_DATE_CREATED + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getDateCreated()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				SqlHandler.delimitString(poSearchResultBean.getDateSent()) + " " +
			"where " + ATTRIBUTE_CRITICAL + "=" +
				StringHandler.nullIfZero(poSearchResultBean.getCritical());

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

		Collection poSearchResultBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoSearchResultBean poSearchResultBean = new PoSearchResultBean();
			load(dataSetRow, poSearchResultBean);
			poSearchResultBeanColl.add(poSearchResultBean);
		}

		return poSearchResultBeanColl;
	}
        
	public Collection select(DataSet dataSet)
		throws BaseException {
                   
		Collection poSearchResultBeanColl = new Vector();

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoSearchResultBean poSearchResultBean = new PoSearchResultBean();
			load(dataSetRow, poSearchResultBean);
			poSearchResultBeanColl.add(poSearchResultBean);
		}

		return poSearchResultBeanColl;
	}
        
        public Collection search(Collection params) 
                throws BaseException {
          GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());          

          // out params registry
          Collection outParams = new Vector(2);          
          outParams.add(new Integer(oracle.jdbc.OracleTypes.CURSOR));
          outParams.add(new Integer(java.sql.Types.VARCHAR));        
          
          //TODO: Procedure passes the datecreated as string. Modified the procedure and then the bean and then the .jsp files if necessary
          outParams = procFactory.doProcedure("pkg_po.pr_po_search",params,outParams);

          if (outParams != null) {
            Iterator iter = outParams.iterator();
            if (iter.hasNext()) {
               Object o = iter.next();                     
               DataSet dataSet = new DataSet();
               try {
                  SqlManager sqlMgr = new SqlManager();
                  sqlMgr.populateDataSet(dataSet,(ResultSet)o);                  
                  return select(dataSet);
               } catch (SQLException sqle) {
            	  sqle.printStackTrace();
                  log.error("SQL Exception populating return dat set from PO Search " + sqle);
               }
            }           
          } else {
           log.error("out params is NULL!");
          }
                   
          return (Collection) null; 
        }
        
}