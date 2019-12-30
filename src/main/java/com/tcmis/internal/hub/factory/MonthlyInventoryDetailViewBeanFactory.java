package com.tcmis.internal.hub.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailViewBean;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailInputBean;

/******************************************************************************
 * CLASSNAME: MonthlyInventoryDetailViewBeanFactory <br>
 * @version: 1.0, Feb 15, 2005 <br>
 *****************************************************************************/

public class MonthlyInventoryDetailViewBeanFactory
	extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_PRIORITY = "PRIORITY";
  public String ATTRIBUTE_BI_INVENTORY = "BI_INVENTORY";
  public String ATTRIBUTE_BI_INVENTORY_VALUE = "BI_INVENTORY_VALUE";
  public String ATTRIBUTE_BI_HAAS_OWNED_QUANTITY = "BI_HAAS_OWNED_QUANTITY";
  public String ATTRIBUTE_BI_HAAS_OWNED_VALUE = "BI_HAAS_OWNED_VALUE";
  public String ATTRIBUTE_BI_CONSIGNED_QUANTITY = "BI_CONSIGNED_QUANTITY";
  public String ATTRIBUTE_BI_CONSIGNED_VALUE = "BI_CONSIGNED_VALUE";
  public String ATTRIBUTE_BI_CUSTOMER_OWNED_QUANTITY =
	  "BI_CUSTOMER_OWNED_QUANTITY";
  public String ATTRIBUTE_BI_CUSTOMER_OWNED_VALUE = "BI_CUSTOMER_OWNED_VALUE";
  public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
  public String ATTRIBUTE_REC_INVENTORY_VALUE = "REC_INVENTORY_VALUE";
  public String ATTRIBUTE_REC_HAAS_OWNED_QUANTITY = "REC_HAAS_OWNED_QUANTITY";
  public String ATTRIBUTE_REC_HAAS_OWNED_VALUE = "REC_HAAS_OWNED_VALUE";
  public String ATTRIBUTE_REC_CONSIGNED_QUANTITY = "REC_CONSIGNED_QUANTITY";
  public String ATTRIBUTE_REC_CONSIGNED_VALUE = "REC_CONSIGNED_VALUE";
  public String ATTRIBUTE_REC_CUSTOMER_OWNED_QUANTITY =
	  "REC_CUSTOMER_OWNED_QUANTITY";
  public String ATTRIBUTE_REC_CUSTOMER_OWNED_VALUE = "REC_CUSTOMER_OWNED_VALUE";
  public String ATTRIBUTE_EI_INVENTORY = "EI_INVENTORY";
  public String ATTRIBUTE_EI_INVENTORY_VALUE = "EI_INVENTORY_VALUE";
  public String ATTRIBUTE_EI_HAAS_OWNED_QUANTITY = "EI_HAAS_OWNED_QUANTITY";
  public String ATTRIBUTE_EI_HAAS_OWNED_VALUE = "EI_HAAS_OWNED_VALUE";
  public String ATTRIBUTE_EI_CONSIGNED_QUANTITY = "EI_CONSIGNED_QUANTITY";
  public String ATTRIBUTE_EI_CONSIGNED_VALUE = "EI_CONSIGNED_VALUE";
  public String ATTRIBUTE_EI_CUSTOMER_OWNED_QUANTITY =
	  "EI_CUSTOMER_OWNED_QUANTITY";
  public String ATTRIBUTE_EI_CUSTOMER_OWNED_VALUE = "EI_CUSTOMER_OWNED_VALUE";
  public String ATTRIBUTE_NET_USAGE_QUANTITY = "NET_USAGE_QUANTITY";
  public String ATTRIBUTE_NET_USAGE_VALUE = "NET_USAGE_VALUE";
  public String ATTRIBUTE_USAGE_HAAS_OWNED_QUANTITY =
	  "USAGE_HAAS_OWNED_QUANTITY";
  public String ATTRIBUTE_USAGE_HAAS_OWNED_VALUE = "USAGE_HAAS_OWNED_VALUE";
  public String ATTRIBUTE_USAGE_CONSIGNED_QUANTITY = "USAGE_CONSIGNED_QUANTITY";
  public String ATTRIBUTE_USAGE_CONSIGNED_VALUE = "USAGE_CONSIGNED_VALUE";
  public String ATTRIBUTE_USAGE_CUSTOMER_OWNED_QUANTITY =
	  "USAGE_CUSTOMER_OWNED_QUANTITY";
  public String ATTRIBUTE_USAGE_CUSTOMER_OWNED_VALUE =
	  "USAGE_CUSTOMER_OWNED_VALUE";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_COUNT_UOM = "COUNT_UOM";
  public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_DIRECTED_FACILITY_ID = "DIRECTED_FACILITY_ID";
	public String ATTRIBUTE_DIRECTED_COMPANY_ID = "DIRECTED_COMPANY_ID";

  //table name
  public String TABLE = "MONTHLY_INVENTORY_DETAIL_VIEW";

  //constructor
  public MonthlyInventoryDetailViewBeanFactory(DbManager dbManager) {
	super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
	if (attributeName.equals("invoicePeriod")) {
	  return ATTRIBUTE_INVOICE_PERIOD;
	}
	else if (attributeName.equals("catPartNo")) {
	  return ATTRIBUTE_CAT_PART_NO;
	}
	else if (attributeName.equals("catalogId")) {
	  return ATTRIBUTE_CATALOG_ID;
	}
	else if (attributeName.equals("companyId")) {
	  return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("itemId")) {
	  return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("itemDesc")) {
	  return ATTRIBUTE_ITEM_DESC;
	}
	else if (attributeName.equals("inventoryGroup")) {
	  return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("status")) {
	  return ATTRIBUTE_STATUS;
	}
	else if (attributeName.equals("priority")) {
	  return ATTRIBUTE_PRIORITY;
	}
	else if (attributeName.equals("biInventory")) {
	  return ATTRIBUTE_BI_INVENTORY;
	}
	else if (attributeName.equals("biInventoryValue")) {
	  return ATTRIBUTE_BI_INVENTORY_VALUE;
	}
	else if (attributeName.equals("biHaasOwnedQuantity")) {
	  return ATTRIBUTE_BI_HAAS_OWNED_QUANTITY;
	}
	else if (attributeName.equals("biHaasOwnedValue")) {
	  return ATTRIBUTE_BI_HAAS_OWNED_VALUE;
	}
	else if (attributeName.equals("biConsignedQuantity")) {
	  return ATTRIBUTE_BI_CONSIGNED_QUANTITY;
	}
	else if (attributeName.equals("biConsignedValue")) {
	  return ATTRIBUTE_BI_CONSIGNED_VALUE;
	}
	else if (attributeName.equals("biCustomerOwnedQuantity")) {
	  return ATTRIBUTE_BI_CUSTOMER_OWNED_QUANTITY;
	}
	else if (attributeName.equals("biCustomerOwnedValue")) {
	  return ATTRIBUTE_BI_CUSTOMER_OWNED_VALUE;
	}
	else if (attributeName.equals("quantityReceived")) {
	  return ATTRIBUTE_QUANTITY_RECEIVED;
	}
	else if (attributeName.equals("recInventoryValue")) {
	  return ATTRIBUTE_REC_INVENTORY_VALUE;
	}
	else if (attributeName.equals("recHaasOwnedQuantity")) {
	  return ATTRIBUTE_REC_HAAS_OWNED_QUANTITY;
	}
	else if (attributeName.equals("recHaasOwnedValue")) {
	  return ATTRIBUTE_REC_HAAS_OWNED_VALUE;
	}
	else if (attributeName.equals("recConsignedQuantity")) {
	  return ATTRIBUTE_REC_CONSIGNED_QUANTITY;
	}
	else if (attributeName.equals("recConsignedValue")) {
	  return ATTRIBUTE_REC_CONSIGNED_VALUE;
	}
	else if (attributeName.equals("recCustomerOwnedQuantity")) {
	  return ATTRIBUTE_REC_CUSTOMER_OWNED_QUANTITY;
	}
	else if (attributeName.equals("recCustomerOwnedValue")) {
	  return ATTRIBUTE_REC_CUSTOMER_OWNED_VALUE;
	}
	else if (attributeName.equals("eiInventory")) {
	  return ATTRIBUTE_EI_INVENTORY;
	}
	else if (attributeName.equals("eiInventoryValue")) {
	  return ATTRIBUTE_EI_INVENTORY_VALUE;
	}
	else if (attributeName.equals("eiHaasOwnedQuantity")) {
	  return ATTRIBUTE_EI_HAAS_OWNED_QUANTITY;
	}
	else if (attributeName.equals("eiHaasOwnedValue")) {
	  return ATTRIBUTE_EI_HAAS_OWNED_VALUE;
	}
	else if (attributeName.equals("eiConsignedQuantity")) {
	  return ATTRIBUTE_EI_CONSIGNED_QUANTITY;
	}
	else if (attributeName.equals("eiConsignedValue")) {
	  return ATTRIBUTE_EI_CONSIGNED_VALUE;
	}
	else if (attributeName.equals("eiCustomerOwnedQuantity")) {
	  return ATTRIBUTE_EI_CUSTOMER_OWNED_QUANTITY;
	}
	else if (attributeName.equals("eiCustomerOwnedValue")) {
	  return ATTRIBUTE_EI_CUSTOMER_OWNED_VALUE;
	}
	else if (attributeName.equals("netUsageQuantity")) {
	  return ATTRIBUTE_NET_USAGE_QUANTITY;
	}
	else if (attributeName.equals("netUsageValue")) {
	  return ATTRIBUTE_NET_USAGE_VALUE;
	}
	else if (attributeName.equals("usageHaasOwnedQuantity")) {
	  return ATTRIBUTE_USAGE_HAAS_OWNED_QUANTITY;
	}
	else if (attributeName.equals("usageHaasOwnedValue")) {
	  return ATTRIBUTE_USAGE_HAAS_OWNED_VALUE;
	}
	else if (attributeName.equals("usageConsignedQuantity")) {
	  return ATTRIBUTE_USAGE_CONSIGNED_QUANTITY;
	}
	else if (attributeName.equals("usageConsignedValue")) {
	  return ATTRIBUTE_USAGE_CONSIGNED_VALUE;
	}
	else if (attributeName.equals("usageCustomerOwnedQuantity")) {
	  return ATTRIBUTE_USAGE_CUSTOMER_OWNED_QUANTITY;
	}
	else if (attributeName.equals("usageCustomerOwnedValue")) {
	  return ATTRIBUTE_USAGE_CUSTOMER_OWNED_VALUE;
	}
	else if (attributeName.equals("packaging")) {
	  return ATTRIBUTE_PACKAGING;
	}
	else if (attributeName.equals("countUom")) {
	  return ATTRIBUTE_COUNT_UOM;
	}
	else if (attributeName.equals("currencyId")) {
	  return ATTRIBUTE_CURRENCY_ID;
	}
	else if (attributeName.equals("directedFacilityId")) {
		return ATTRIBUTE_DIRECTED_FACILITY_ID;
	}
	else if (attributeName.equals("directedCompanyId")) {
		return ATTRIBUTE_DIRECTED_COMPANY_ID;
	}
	else {
	  return super.getColumnName(attributeName);
	}
  }

  //get column types
  public int getType(String attributeName) {
	return super.getType(attributeName, MonthlyInventoryDetailViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
	   public int delete(MonthlyInventoryDetailViewBean monthlyInventoryDetailViewBean)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
	 "" + monthlyInventoryDetailViewBean.getInvoicePeriod());
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
   public int delete(MonthlyInventoryDetailViewBean monthlyInventoryDetailViewBean, Connection conn)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
	 "" + monthlyInventoryDetailViewBean.getInvoicePeriod());
	return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

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

  public int delete(SearchCriteria criteria, Connection conn) throws
	  BaseException {

	String sqlQuery = " delete from " + TABLE + " " +
		getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
	   public int insert(MonthlyInventoryDetailViewBean monthlyInventoryDetailViewBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = insert(monthlyInventoryDetailViewBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
   }
   public int insert(MonthlyInventoryDetailViewBean monthlyInventoryDetailViewBean, Connection conn)
	throws BaseException {
	SqlManager sqlManager = new SqlManager();
	String query  =
	 "insert into " + TABLE + " (" +
	 ATTRIBUTE_INVOICE_PERIOD + "," +
	 ATTRIBUTE_CAT_PART_NO + "," +
	 ATTRIBUTE_CATALOG_ID + "," +
	 ATTRIBUTE_COMPANY_ID + "," +
	 ATTRIBUTE_ITEM_ID + "," +
	 ATTRIBUTE_ITEM_DESC + "," +
	 ATTRIBUTE_INVENTORY_GROUP + "," +
	 ATTRIBUTE_STATUS + "," +
	 ATTRIBUTE_PRIORITY + "," +
	 ATTRIBUTE_BI_INVENTORY + "," +
	 ATTRIBUTE_BI_INVENTORY_VALUE + "," +
	 ATTRIBUTE_BI_HAAS_OWNED_QUANTITY + "," +
	 ATTRIBUTE_BI_HAAS_OWNED_VALUE + "," +
	 ATTRIBUTE_BI_CONSIGNED_QUANTITY + "," +
	 ATTRIBUTE_BI_CONSIGNED_VALUE + "," +
	 ATTRIBUTE_BI_CUSTOMER_OWNED_QUANTITY + "," +
	 ATTRIBUTE_BI_CUSTOMER_OWNED_VALUE + "," +
	 ATTRIBUTE_QUANTITY_RECEIVED + "," +
	 ATTRIBUTE_REC_INVENTORY_VALUE + "," +
	 ATTRIBUTE_REC_HAAS_OWNED_QUANTITY + "," +
	 ATTRIBUTE_REC_HAAS_OWNED_VALUE + "," +
	 ATTRIBUTE_REC_CONSIGNED_QUANTITY + "," +
	 ATTRIBUTE_REC_CONSIGNED_VALUE + "," +
	 ATTRIBUTE_REC_CUSTOMER_OWNED_QUANTITY + "," +
	 ATTRIBUTE_REC_CUSTOMER_OWNED_VALUE + "," +
	 ATTRIBUTE_EI_INVENTORY + "," +
	 ATTRIBUTE_EI_INVENTORY_VALUE + "," +
	 ATTRIBUTE_EI_HAAS_OWNED_QUANTITY + "," +
	 ATTRIBUTE_EI_HAAS_OWNED_VALUE + "," +
	 ATTRIBUTE_EI_CONSIGNED_QUANTITY + "," +
	 ATTRIBUTE_EI_CONSIGNED_VALUE + "," +
	 ATTRIBUTE_EI_CUSTOMER_OWNED_QUANTITY + "," +
	 ATTRIBUTE_EI_CUSTOMER_OWNED_VALUE + "," +
	 ATTRIBUTE_NET_USAGE_QUANTITY + "," +
	 ATTRIBUTE_NET_USAGE_VALUE + "," +
	 ATTRIBUTE_USAGE_HAAS_OWNED_QUANTITY + "," +
	 ATTRIBUTE_USAGE_HAAS_OWNED_VALUE + "," +
	 ATTRIBUTE_USAGE_CONSIGNED_QUANTITY + "," +
	 ATTRIBUTE_USAGE_CONSIGNED_VALUE + "," +
	 ATTRIBUTE_USAGE_CUSTOMER_OWNED_QUANTITY + "," +
	 ATTRIBUTE_USAGE_CUSTOMER_OWNED_VALUE + "," +
	 ATTRIBUTE_PACKAGING + "," +
	 ATTRIBUTE_COUNT_UOM + ")" +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getInvoicePeriod()) + "," +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCatPartNo()) + "," +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCatalogId()) + "," +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCompanyId()) + "," +
	   StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getItemId()) + "," +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getItemDesc()) + "," +
	 SqlHandler.delimitString(monthlyInventoryDetailViewBean.getInventoryGroup()) + "," +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getStatus()) + "," +
	   StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getPriority()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiInventory()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiInventoryValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiHaasOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiHaasOwnedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiConsignedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiConsignedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiCustomerOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiCustomerOwnedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getQuantityReceived()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecInventoryValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecHaasOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecHaasOwnedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecConsignedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecConsignedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecCustomerOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecCustomerOwnedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiInventory()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiInventoryValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiHaasOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiHaasOwnedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiConsignedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiConsignedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiCustomerOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiCustomerOwnedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getNetUsageQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getNetUsageValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageHaasOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageHaasOwnedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageConsignedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageConsignedValue()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageCustomerOwnedQuantity()) + "," +
	 StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageCustomerOwnedValue()) + "," +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getPackaging()) + "," +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCountUom()) + ")";
	return sqlManager.update(conn, query);
   }
//update
	   public int update(MonthlyInventoryDetailViewBean monthlyInventoryDetailViewBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = update(monthlyInventoryDetailViewBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
   }
   public int update(MonthlyInventoryDetailViewBean monthlyInventoryDetailViewBean, Connection conn)
	throws BaseException {
	String query  = "update " + TABLE + " set " +
	 ATTRIBUTE_INVOICE_PERIOD + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getInvoicePeriod()) + "," +
	 ATTRIBUTE_CAT_PART_NO + "=" +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCatPartNo()) + "," +
	 ATTRIBUTE_CATALOG_ID + "=" +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCatalogId()) + "," +
	 ATTRIBUTE_COMPANY_ID + "=" +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCompanyId()) + "," +
	 ATTRIBUTE_ITEM_ID + "=" +
	   StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getItemId()) + "," +
	 ATTRIBUTE_ITEM_DESC + "=" +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getItemDesc()) + "," +
	 ATTRIBUTE_INVENTORY_GROUP + "=" +
	  SqlHandler.delimitString(monthlyInventoryDetailViewBean.getInventoryGroup()) + "," +
	 ATTRIBUTE_STATUS + "=" +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getStatus()) + "," +
	 ATTRIBUTE_PRIORITY + "=" +
	   StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getPriority()) + "," +
	 ATTRIBUTE_BI_INVENTORY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiInventory()) + "," +
	 ATTRIBUTE_BI_INVENTORY_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiInventoryValue()) + "," +
	 ATTRIBUTE_BI_HAAS_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiHaasOwnedQuantity()) + "," +
	 ATTRIBUTE_BI_HAAS_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiHaasOwnedValue()) + "," +
	 ATTRIBUTE_BI_CONSIGNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiConsignedQuantity()) + "," +
	 ATTRIBUTE_BI_CONSIGNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiConsignedValue()) + "," +
	 ATTRIBUTE_BI_CUSTOMER_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiCustomerOwnedQuantity()) + "," +
	 ATTRIBUTE_BI_CUSTOMER_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getBiCustomerOwnedValue()) + "," +
	 ATTRIBUTE_QUANTITY_RECEIVED + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getQuantityReceived()) + "," +
	 ATTRIBUTE_REC_INVENTORY_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecInventoryValue()) + "," +
	 ATTRIBUTE_REC_HAAS_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecHaasOwnedQuantity()) + "," +
	 ATTRIBUTE_REC_HAAS_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecHaasOwnedValue()) + "," +
	 ATTRIBUTE_REC_CONSIGNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecConsignedQuantity()) + "," +
	 ATTRIBUTE_REC_CONSIGNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecConsignedValue()) + "," +
	 ATTRIBUTE_REC_CUSTOMER_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecCustomerOwnedQuantity()) + "," +
	 ATTRIBUTE_REC_CUSTOMER_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getRecCustomerOwnedValue()) + "," +
	 ATTRIBUTE_EI_INVENTORY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiInventory()) + "," +
	 ATTRIBUTE_EI_INVENTORY_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiInventoryValue()) + "," +
	 ATTRIBUTE_EI_HAAS_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiHaasOwnedQuantity()) + "," +
	 ATTRIBUTE_EI_HAAS_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiHaasOwnedValue()) + "," +
	 ATTRIBUTE_EI_CONSIGNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiConsignedQuantity()) + "," +
	 ATTRIBUTE_EI_CONSIGNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiConsignedValue()) + "," +
	 ATTRIBUTE_EI_CUSTOMER_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiCustomerOwnedQuantity()) + "," +
	 ATTRIBUTE_EI_CUSTOMER_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getEiCustomerOwnedValue()) + "," +
	 ATTRIBUTE_NET_USAGE_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getNetUsageQuantity()) + "," +
	 ATTRIBUTE_NET_USAGE_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getNetUsageValue()) + "," +
	 ATTRIBUTE_USAGE_HAAS_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageHaasOwnedQuantity()) + "," +
	 ATTRIBUTE_USAGE_HAAS_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageHaasOwnedValue()) + "," +
	 ATTRIBUTE_USAGE_CONSIGNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageConsignedQuantity()) + "," +
	 ATTRIBUTE_USAGE_CONSIGNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageConsignedValue()) + "," +
	 ATTRIBUTE_USAGE_CUSTOMER_OWNED_QUANTITY + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageCustomerOwnedQuantity()) + "," +
	 ATTRIBUTE_USAGE_CUSTOMER_OWNED_VALUE + "=" +
	  StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getUsageCustomerOwnedValue()) + "," +
	 ATTRIBUTE_PACKAGING + "=" +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getPackaging()) + "," +
	 ATTRIBUTE_COUNT_UOM + "=" +
	   SqlHandler.delimitString(monthlyInventoryDetailViewBean.getCountUom()) + " " +
	 "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
	   StringHandler.nullIfZero(monthlyInventoryDetailViewBean.getInvoicePeriod());
	return new SqlManager().update(conn, query);
   }
   */

  //select
	public Collection select(SearchCriteria criteria,MonthlyInventoryDetailInputBean
	 bean, boolean iscollection) throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	  connection = this.getDbManager().getConnection();
	  c = select(criteria,bean,iscollection,connection);
	}
	finally {
	  this.getDbManager().returnConnection(connection);
	}
	return c;
  }

	public Collection select(SearchCriteria criteria,MonthlyInventoryDetailInputBean
	 bean, boolean iscollection, Connection conn) throws
	  BaseException {

	Collection monthlyInventoryDetailViewBeanColl = new Vector();
	String whereClause = getWhereClause(criteria);
	if (iscollection) {
	 if (whereClause.trim().length() > 0) {
		whereClause += "and ";
	 }

	 whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " +
		ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION +
		" where " + ATTRIBUTE_HUB + " = '" + bean.getHub() + "' and " +
		ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() +
		"') ";
	}

	String query = "select * from " + TABLE + " " +
		whereClause;

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	  DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	  MonthlyInventoryDetailViewBean monthlyInventoryDetailViewBean = new
		  MonthlyInventoryDetailViewBean();
	  load(dataSetRow, monthlyInventoryDetailViewBean);
	  monthlyInventoryDetailViewBeanColl.add(monthlyInventoryDetailViewBean);
	}

	return monthlyInventoryDetailViewBeanColl;
  }
}