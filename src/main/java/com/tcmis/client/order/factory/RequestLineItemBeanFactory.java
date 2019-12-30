package com.tcmis.client.order.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Date;
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
import com.tcmis.client.order.beans.RequestLineItemBean;

/******************************************************************************
 * CLASSNAME: RequestLineItemBeanFactory <br>
 * @version: 1.0, Jun 26, 2006 <br>
 *****************************************************************************/

public class RequestLineItemBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
  public String ATTRIBUTE_VENDOR_QUALIFIER = "VENDOR_QUALIFIER";
  public String ATTRIBUTE_PROPOSED_VENDOR = "PROPOSED_VENDOR";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_QUOTED_PRICE = "QUOTED_PRICE";
  public String ATTRIBUTE_REMARK = "REMARK";
  public String ATTRIBUTE_REQUIRED_DATETIME = "REQUIRED_DATETIME";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
  public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
  public String ATTRIBUTE_RELEASE_DATE = "RELEASE_DATE";
  public String ATTRIBUTE_CRITICAL = "CRITICAL";
  public String ATTRIBUTE_LAST_UPDATED = "LAST_UPDATED";
  public String ATTRIBUTE_LAST_UPDATED_BY = "LAST_UPDATED_BY";
  public String ATTRIBUTE_DOC_NUM = "DOC_NUM";
  public String ATTRIBUTE_DOC_STATE = "DOC_STATE";
  public String ATTRIBUTE_SALES_ORDER = "SALES_ORDER";
  public String ATTRIBUTE_NOTES = "NOTES";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
  public String ATTRIBUTE_DELIVERY_QUANTITY = "DELIVERY_QUANTITY";
  public String ATTRIBUTE_DELIVERY_FREQUENCY = "DELIVERY_FREQUENCY";
  public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
  public String ATTRIBUTE_RELAX_SHELF_LIFE = "RELAX_SHELF_LIFE";
  public String ATTRIBUTE_CANCEL_REQUEST = "CANCEL_REQUEST";
  public String ATTRIBUTE_CANCEL_REQUESTER = "CANCEL_REQUESTER";
  public String ATTRIBUTE_CANCEL_STATUS = "CANCEL_STATUS";
  public String ATTRIBUTE_CANCEL_AUTHORIZER = "CANCEL_AUTHORIZER";
  public String ATTRIBUTE_CANCEL_ACTION_DATE = "CANCEL_ACTION_DATE";
  public String ATTRIBUTE_CANCEL_COMMENT = "CANCEL_COMMENT";
  public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
  public String ATTRIBUTE_SO_CREATION_DATE = "SO_CREATION_DATE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_EXAMPLE_ITEM_ID = "EXAMPLE_ITEM_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_SCRAP = "SCRAP";
  public String ATTRIBUTE_CATALOG_NAME = "CATALOG_NAME";
  public String ATTRIBUTE_EXAMPLE_PACKAGING = "EXAMPLE_PACKAGING";
  public String ATTRIBUTE_USE_APPROVAL_STATUS = "USE_APPROVAL_STATUS";
  public String ATTRIBUTE_USE_APPROVER = "USE_APPROVER";
  public String ATTRIBUTE_USE_APPROVAL_DATE = "USE_APPROVAL_DATE";
  public String ATTRIBUTE_USE_APPROVAL_COMMENT = "USE_APPROVAL_COMMENT";
  public String ATTRIBUTE_DPAS_CODE = "DPAS_CODE";
  public String ATTRIBUTE_DO_NUMBER = "DO_NUMBER";
  public String ATTRIBUTE_CARD_RECEIPT = "CARD_RECEIPT";
  public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_PREPAID_AMOUNT = "PREPAID_AMOUNT";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_REQUEST_LINE_STATUS = "REQUEST_LINE_STATUS";
  public String ATTRIBUTE_LAST_SHIPPED = "LAST_SHIPPED";
  public String ATTRIBUTE_CAT_PART_NO_REVISION = "CAT_PART_NO_REVISION";
  public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
  public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
  public String ATTRIBUTE_CATEGORY_STATUS = "CATEGORY_STATUS";
  public String ATTRIBUTE_ARCHIVED_QTY_SHIPPED = "ARCHIVED_QTY_SHIPPED";
  public String ATTRIBUTE_NOTICE = "NOTICE";
  public String ATTRIBUTE_TOTAL_QUANTITY_ISSUED = "TOTAL_QUANTITY_ISSUED";
  public String ATTRIBUTE_TOTAL_QUANTITY_DELIVERED = "TOTAL_QUANTITY_DELIVERED";
  public String ATTRIBUTE_DATE_LAST_DELIVERED = "DATE_LAST_DELIVERED";
  public String ATTRIBUTE_CABINET_REPLENISHMENT = "CABINET_REPLENISHMENT";
  public String ATTRIBUTE_REQUESTED_DISPENSED_SIZE_UNIT =
      "REQUESTED_DISPENSED_SIZE_UNIT";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH =
      "UNIT_OF_SALE_QUANTITY_PER_EACH";
  public String ATTRIBUTE_BILLING_METHOD = "BILLING_METHOD";
  public String ATTRIBUTE_ORDER_QUANTITY_RULE = "ORDER_QUANTITY_RULE";
  public String ATTRIBUTE_DROP_SHIP_OVERRIDE = "DROP_SHIP_OVERRIDE";
  public String ATTRIBUTE_CUSTOMER_REQUISITION_NUMBER =
      "CUSTOMER_REQUISITION_NUMBER";
  public String ATTRIBUTE_TAX_EXEMPT = "TAX_EXEMPT";
  
  //table name
  public String TABLE = "REQUEST_LINE_ITEM";

  //constructor
  public RequestLineItemBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("shipToLocationId")) {
      return ATTRIBUTE_SHIP_TO_LOCATION_ID;
    }
    else if (attributeName.equals("vendorQualifier")) {
      return ATTRIBUTE_VENDOR_QUALIFIER;
    }
    else if (attributeName.equals("proposedVendor")) {
      return ATTRIBUTE_PROPOSED_VENDOR;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("quotedPrice")) {
      return ATTRIBUTE_QUOTED_PRICE;
    }
    else if (attributeName.equals("remark")) {
      return ATTRIBUTE_REMARK;
    }
    else if (attributeName.equals("requiredDatetime")) {
      return ATTRIBUTE_REQUIRED_DATETIME;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("releaseNumber")) {
      return ATTRIBUTE_RELEASE_NUMBER;
    }
    else if (attributeName.equals("facPartNo")) {
      return ATTRIBUTE_FAC_PART_NO;
    }
    else if (attributeName.equals("releaseDate")) {
      return ATTRIBUTE_RELEASE_DATE;
    }
    else if (attributeName.equals("critical")) {
      return ATTRIBUTE_CRITICAL;
    }
    else if (attributeName.equals("lastUpdated")) {
      return ATTRIBUTE_LAST_UPDATED;
    }
    else if (attributeName.equals("lastUpdatedBy")) {
      return ATTRIBUTE_LAST_UPDATED_BY;
    }
    else if (attributeName.equals("docNum")) {
      return ATTRIBUTE_DOC_NUM;
    }
    else if (attributeName.equals("docState")) {
      return ATTRIBUTE_DOC_STATE;
    }
    else if (attributeName.equals("salesOrder")) {
      return ATTRIBUTE_SALES_ORDER;
    }
    else if (attributeName.equals("notes")) {
      return ATTRIBUTE_NOTES;
    }
    else if (attributeName.equals("deliveryPoint")) {
      return ATTRIBUTE_DELIVERY_POINT;
    }
    else if (attributeName.equals("deliveryType")) {
      return ATTRIBUTE_DELIVERY_TYPE;
    }
    else if (attributeName.equals("deliveryQuantity")) {
      return ATTRIBUTE_DELIVERY_QUANTITY;
    }
    else if (attributeName.equals("deliveryFrequency")) {
      return ATTRIBUTE_DELIVERY_FREQUENCY;
    }
    else if (attributeName.equals("chargeType")) {
      return ATTRIBUTE_CHARGE_TYPE;
    }
    else if (attributeName.equals("relaxShelfLife")) {
      return ATTRIBUTE_RELAX_SHELF_LIFE;
    }
    else if (attributeName.equals("cancelRequest")) {
      return ATTRIBUTE_CANCEL_REQUEST;
    }
    else if (attributeName.equals("cancelRequester")) {
      return ATTRIBUTE_CANCEL_REQUESTER;
    }
    else if (attributeName.equals("cancelStatus")) {
      return ATTRIBUTE_CANCEL_STATUS;
    }
    else if (attributeName.equals("cancelAuthorizer")) {
      return ATTRIBUTE_CANCEL_AUTHORIZER;
    }
    else if (attributeName.equals("cancelActionDate")) {
      return ATTRIBUTE_CANCEL_ACTION_DATE;
    }
    else if (attributeName.equals("cancelComment")) {
      return ATTRIBUTE_CANCEL_COMMENT;
    }
    else if (attributeName.equals("itemType")) {
      return ATTRIBUTE_ITEM_TYPE;
    }
    else if (attributeName.equals("soCreationDate")) {
      return ATTRIBUTE_SO_CREATION_DATE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("exampleItemId")) {
      return ATTRIBUTE_EXAMPLE_ITEM_ID;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("scrap")) {
      return ATTRIBUTE_SCRAP;
    }
    else if (attributeName.equals("catalogName")) {
      return ATTRIBUTE_CATALOG_NAME;
    }
    else if (attributeName.equals("examplePackaging")) {
      return ATTRIBUTE_EXAMPLE_PACKAGING;
    }
    else if (attributeName.equals("useApprovalStatus")) {
      return ATTRIBUTE_USE_APPROVAL_STATUS;
    }
    else if (attributeName.equals("useApprover")) {
      return ATTRIBUTE_USE_APPROVER;
    }
    else if (attributeName.equals("useApprovalDate")) {
      return ATTRIBUTE_USE_APPROVAL_DATE;
    }
    else if (attributeName.equals("useApprovalComment")) {
      return ATTRIBUTE_USE_APPROVAL_COMMENT;
    }
    else if (attributeName.equals("dpasCode")) {
      return ATTRIBUTE_DPAS_CODE;
    }
    else if (attributeName.equals("doNumber")) {
      return ATTRIBUTE_DO_NUMBER;
    }
    else if (attributeName.equals("cardReceipt")) {
      return ATTRIBUTE_CARD_RECEIPT;
    }
    else if (attributeName.equals("payloadId")) {
      return ATTRIBUTE_PAYLOAD_ID;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("prepaidAmount")) {
      return ATTRIBUTE_PREPAID_AMOUNT;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("requestLineStatus")) {
      return ATTRIBUTE_REQUEST_LINE_STATUS;
    }
    else if (attributeName.equals("lastShipped")) {
      return ATTRIBUTE_LAST_SHIPPED;
    }
    else if (attributeName.equals("catPartNoRevision")) {
      return ATTRIBUTE_CAT_PART_NO_REVISION;
    }
    else if (attributeName.equals("catalogPrice")) {
      return ATTRIBUTE_CATALOG_PRICE;
    }
    else if (attributeName.equals("baselinePrice")) {
      return ATTRIBUTE_BASELINE_PRICE;
    }
    else if (attributeName.equals("categoryStatus")) {
      return ATTRIBUTE_CATEGORY_STATUS;
    }
    else if (attributeName.equals("archivedQtyShipped")) {
      return ATTRIBUTE_ARCHIVED_QTY_SHIPPED;
    }
    else if (attributeName.equals("notice")) {
      return ATTRIBUTE_NOTICE;
    }
    else if (attributeName.equals("totalQuantityIssued")) {
      return ATTRIBUTE_TOTAL_QUANTITY_ISSUED;
    }
    else if (attributeName.equals("totalQuantityDelivered")) {
      return ATTRIBUTE_TOTAL_QUANTITY_DELIVERED;
    }
    else if (attributeName.equals("dateLastDelivered")) {
      return ATTRIBUTE_DATE_LAST_DELIVERED;
    }
    else if (attributeName.equals("cabinetReplenishment")) {
      return ATTRIBUTE_CABINET_REPLENISHMENT;
    }
    else if (attributeName.equals("requestedDispensedSizeUnit")) {
      return ATTRIBUTE_REQUESTED_DISPENSED_SIZE_UNIT;
    }
    else if (attributeName.equals("applicationDesc")) {
      return ATTRIBUTE_APPLICATION_DESC;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("currencyId")) {
      return ATTRIBUTE_CURRENCY_ID;
    }
    else if (attributeName.equals("unitOfSale")) {
      return ATTRIBUTE_UNIT_OF_SALE;
    }
    else if (attributeName.equals("unitOfSaleQuantityPerEach")) {
      return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
    }
    else if (attributeName.equals("billingMethod")) {
      return ATTRIBUTE_BILLING_METHOD;
    }
    else if (attributeName.equals("orderQuantityRule")) {
      return ATTRIBUTE_ORDER_QUANTITY_RULE;
    }
    else if (attributeName.equals("dropShipOverride")) {
      return ATTRIBUTE_DROP_SHIP_OVERRIDE;
    }
    else if (attributeName.equals("customerRequisitionNumber")) {
      return ATTRIBUTE_CUSTOMER_REQUISITION_NUMBER;
    }
    else if (attributeName.equals("taxExempt")) {
      return ATTRIBUTE_TAX_EXEMPT;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, RequestLineItemBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(RequestLineItemBean requestLineItemBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + requestLineItemBean.getPrNumber());
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
   public int delete(RequestLineItemBean requestLineItemBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + requestLineItemBean.getPrNumber());
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
   public int insert(RequestLineItemBean requestLineItemBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(requestLineItemBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(RequestLineItemBean requestLineItemBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
     ATTRIBUTE_VENDOR_QUALIFIER + "," +
     ATTRIBUTE_PROPOSED_VENDOR + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_QUOTED_PRICE + "," +
     ATTRIBUTE_REMARK + "," +
     ATTRIBUTE_REQUIRED_DATETIME + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_RELEASE_NUMBER + "," +
     ATTRIBUTE_FAC_PART_NO + "," +
     ATTRIBUTE_RELEASE_DATE + "," +
     ATTRIBUTE_CRITICAL + "," +
     ATTRIBUTE_LAST_UPDATED + "," +
     ATTRIBUTE_LAST_UPDATED_BY + "," +
     ATTRIBUTE_DOC_NUM + "," +
     ATTRIBUTE_DOC_STATE + "," +
     ATTRIBUTE_SALES_ORDER + "," +
     ATTRIBUTE_NOTES + "," +
     ATTRIBUTE_DELIVERY_POINT + "," +
     ATTRIBUTE_DELIVERY_TYPE + "," +
     ATTRIBUTE_DELIVERY_QUANTITY + "," +
     ATTRIBUTE_DELIVERY_FREQUENCY + "," +
     ATTRIBUTE_CHARGE_TYPE + "," +
     ATTRIBUTE_RELAX_SHELF_LIFE + "," +
     ATTRIBUTE_CANCEL_REQUEST + "," +
     ATTRIBUTE_CANCEL_REQUESTER + "," +
     ATTRIBUTE_CANCEL_STATUS + "," +
     ATTRIBUTE_CANCEL_AUTHORIZER + "," +
     ATTRIBUTE_CANCEL_ACTION_DATE + "," +
     ATTRIBUTE_CANCEL_COMMENT + "," +
     ATTRIBUTE_ITEM_TYPE + "," +
     ATTRIBUTE_SO_CREATION_DATE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_EXAMPLE_ITEM_ID + "," +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_SCRAP + "," +
     ATTRIBUTE_CATALOG_NAME + "," +
     ATTRIBUTE_EXAMPLE_PACKAGING + "," +
     ATTRIBUTE_USE_APPROVAL_STATUS + "," +
     ATTRIBUTE_USE_APPROVER + "," +
     ATTRIBUTE_USE_APPROVAL_DATE + "," +
     ATTRIBUTE_USE_APPROVAL_COMMENT + "," +
     ATTRIBUTE_DPAS_CODE + "," +
     ATTRIBUTE_DO_NUMBER + "," +
     ATTRIBUTE_CARD_RECEIPT + "," +
     ATTRIBUTE_PAYLOAD_ID + "," +
     ATTRIBUTE_PART_GROUP_NO + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_PREPAID_AMOUNT + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_REQUEST_LINE_STATUS + "," +
     ATTRIBUTE_LAST_SHIPPED + "," +
     ATTRIBUTE_CAT_PART_NO_REVISION + "," +
     ATTRIBUTE_CATALOG_PRICE + "," +
     ATTRIBUTE_BASELINE_PRICE + "," +
     ATTRIBUTE_CATEGORY_STATUS + "," +
     ATTRIBUTE_ARCHIVED_QTY_SHIPPED + "," +
     ATTRIBUTE_NOTICE + "," +
     ATTRIBUTE_TOTAL_QUANTITY_ISSUED + "," +
     ATTRIBUTE_TOTAL_QUANTITY_DELIVERED + "," +
     ATTRIBUTE_DATE_LAST_DELIVERED + "," +
     ATTRIBUTE_CABINET_REPLENISHMENT + "," +
     ATTRIBUTE_REQUESTED_DISPENSED_SIZE_UNIT + "," +
     ATTRIBUTE_APPLICATION_DESC + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_CURRENCY_ID + "," +
     ATTRIBUTE_UNIT_OF_SALE + "," +
     ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "," +
     ATTRIBUTE_BILLING_METHOD + "," +
     ATTRIBUTE_ORDER_QUANTITY_RULE + "," +
     ATTRIBUTE_DROP_SHIP_OVERRIDE + "," +
     ATTRIBUTE_CUSTOMER_REQUISITION_NUMBER + ")" +
     " values (" +
     requestLineItemBean.getPrNumber() + "," +
     SqlHandler.delimitString(requestLineItemBean.getLineItem()) + "," +
     requestLineItemBean.getItemId() + "," +
     SqlHandler.delimitString(requestLineItemBean.getApplication()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getShipToLocationId()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getVendorQualifier()) + "," +
     requestLineItemBean.getProposedVendor() + "," +
     requestLineItemBean.getQuantity() + "," +
     requestLineItemBean.getQuotedPrice() + "," +
     SqlHandler.delimitString(requestLineItemBean.getRemark()) + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getRequiredDatetime()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getPoNumber()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getReleaseNumber()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getFacPartNo()) + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getReleaseDate()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getCritical()) + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getLastUpdated()) + "," +
     requestLineItemBean.getLastUpdatedBy() + "," +
     requestLineItemBean.getDocNum() + "," +
     SqlHandler.delimitString(requestLineItemBean.getDocState()) + "," +
     requestLineItemBean.getSalesOrder() + "," +
     SqlHandler.delimitString(requestLineItemBean.getNotes()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getDeliveryPoint()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getDeliveryType()) + "," +
     requestLineItemBean.getDeliveryQuantity() + "," +
       SqlHandler.delimitString(requestLineItemBean.getDeliveryFrequency()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getChargeType()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getRelaxShelfLife()) + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getCancelRequest()) + "," +
     requestLineItemBean.getCancelRequester() + "," +
     SqlHandler.delimitString(requestLineItemBean.getCancelStatus()) + "," +
     requestLineItemBean.getCancelAuthorizer() + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getCancelActionDate()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getCancelComment()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getItemType()) + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getSoCreationDate()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getCompanyId()) + "," +
     requestLineItemBean.getExampleItemId() + "," +
     SqlHandler.delimitString(requestLineItemBean.getCatalogId()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getScrap()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getCatalogName()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getExamplePackaging()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getUseApprovalStatus()) + "," +
     requestLineItemBean.getUseApprover() + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getUseApprovalDate()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getUseApprovalComment()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getDpasCode()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getDoNumber()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getCardReceipt()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getPayloadId()) + "," +
     requestLineItemBean.getPartGroupNo() + "," +
     requestLineItemBean.getExtendedPrice() + "," +
     requestLineItemBean.getPrepaidAmount() + "," +
     SqlHandler.delimitString(requestLineItemBean.getInventoryGroup()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getRequestLineStatus()) + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getLastShipped()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getCatPartNoRevision()) + "," +
     requestLineItemBean.getCatalogPrice() + "," +
     requestLineItemBean.getBaselinePrice() + "," +
     SqlHandler.delimitString(requestLineItemBean.getCategoryStatus()) + "," +
     requestLineItemBean.getArchivedQtyShipped() + "," +
     requestLineItemBean.getNotice() + "," +
     requestLineItemBean.getTotalQuantityIssued() + "," +
     requestLineItemBean.getTotalQuantityDelivered() + "," +
     DateHandler.getOracleToDateFunction(requestLineItemBean.getDateLastDelivered()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getCabinetReplenishment()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getRequestedDispensedSizeUnit()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getApplicationDesc()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getFacilityId()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getCurrencyId()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getUnitOfSale()) + "," +
     requestLineItemBean.getUnitOfSaleQuantityPerEach() + "," +
     SqlHandler.delimitString(requestLineItemBean.getBillingMethod()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getOrderQuantityRule()) + "," +
       SqlHandler.delimitString(requestLineItemBean.getDropShipOverride()) + "," +
     SqlHandler.delimitString(requestLineItemBean.getCustomerRequisitionNumber()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(RequestLineItemBean requestLineItemBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(requestLineItemBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(RequestLineItemBean requestLineItemBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(requestLineItemBean.getLineItem()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getItemId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(requestLineItemBean.getApplication()) + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
       SqlHandler.delimitString(requestLineItemBean.getShipToLocationId()) + "," +
     ATTRIBUTE_VENDOR_QUALIFIER + "=" +
       SqlHandler.delimitString(requestLineItemBean.getVendorQualifier()) + "," +
     ATTRIBUTE_PROPOSED_VENDOR + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getProposedVendor()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getQuantity()) + "," +
     ATTRIBUTE_QUOTED_PRICE + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getQuotedPrice()) + "," +
     ATTRIBUTE_REMARK + "=" +
      SqlHandler.delimitString(requestLineItemBean.getRemark()) + "," +
     ATTRIBUTE_REQUIRED_DATETIME + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getRequiredDatetime()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(requestLineItemBean.getPoNumber()) + "," +
     ATTRIBUTE_RELEASE_NUMBER + "=" +
      SqlHandler.delimitString(requestLineItemBean.getReleaseNumber()) + "," +
     ATTRIBUTE_FAC_PART_NO + "=" +
      SqlHandler.delimitString(requestLineItemBean.getFacPartNo()) + "," +
     ATTRIBUTE_RELEASE_DATE + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getReleaseDate()) + "," +
     ATTRIBUTE_CRITICAL + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCritical()) + "," +
     ATTRIBUTE_LAST_UPDATED + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getLastUpdated()) + "," +
     ATTRIBUTE_LAST_UPDATED_BY + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getLastUpdatedBy()) + "," +
     ATTRIBUTE_DOC_NUM + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getDocNum()) + "," +
     ATTRIBUTE_DOC_STATE + "=" +
      SqlHandler.delimitString(requestLineItemBean.getDocState()) + "," +
     ATTRIBUTE_SALES_ORDER + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getSalesOrder()) + "," +
     ATTRIBUTE_NOTES + "=" +
      SqlHandler.delimitString(requestLineItemBean.getNotes()) + "," +
     ATTRIBUTE_DELIVERY_POINT + "=" +
      SqlHandler.delimitString(requestLineItemBean.getDeliveryPoint()) + "," +
     ATTRIBUTE_DELIVERY_TYPE + "=" +
      SqlHandler.delimitString(requestLineItemBean.getDeliveryType()) + "," +
     ATTRIBUTE_DELIVERY_QUANTITY + "=" +
       StringHandler.nullIfZero(requestLineItemBean.getDeliveryQuantity()) + "," +
     ATTRIBUTE_DELIVERY_FREQUENCY + "=" +
       SqlHandler.delimitString(requestLineItemBean.getDeliveryFrequency()) + "," +
     ATTRIBUTE_CHARGE_TYPE + "=" +
      SqlHandler.delimitString(requestLineItemBean.getChargeType()) + "," +
     ATTRIBUTE_RELAX_SHELF_LIFE + "=" +
      SqlHandler.delimitString(requestLineItemBean.getRelaxShelfLife()) + "," +
     ATTRIBUTE_CANCEL_REQUEST + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getCancelRequest()) + "," +
     ATTRIBUTE_CANCEL_REQUESTER + "=" +
       StringHandler.nullIfZero(requestLineItemBean.getCancelRequester()) + "," +
     ATTRIBUTE_CANCEL_STATUS + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCancelStatus()) + "," +
     ATTRIBUTE_CANCEL_AUTHORIZER + "=" +
       StringHandler.nullIfZero(requestLineItemBean.getCancelAuthorizer()) + "," +
     ATTRIBUTE_CANCEL_ACTION_DATE + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getCancelActionDate()) + "," +
     ATTRIBUTE_CANCEL_COMMENT + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCancelComment()) + "," +
     ATTRIBUTE_ITEM_TYPE + "=" +
      SqlHandler.delimitString(requestLineItemBean.getItemType()) + "," +
     ATTRIBUTE_SO_CREATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getSoCreationDate()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCompanyId()) + "," +
     ATTRIBUTE_EXAMPLE_ITEM_ID + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getExampleItemId()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCatalogId()) + "," +
     ATTRIBUTE_SCRAP + "=" +
      SqlHandler.delimitString(requestLineItemBean.getScrap()) + "," +
     ATTRIBUTE_CATALOG_NAME + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCatalogName()) + "," +
     ATTRIBUTE_EXAMPLE_PACKAGING + "=" +
       SqlHandler.delimitString(requestLineItemBean.getExamplePackaging()) + "," +
     ATTRIBUTE_USE_APPROVAL_STATUS + "=" +
       SqlHandler.delimitString(requestLineItemBean.getUseApprovalStatus()) + "," +
     ATTRIBUTE_USE_APPROVER + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getUseApprover()) + "," +
     ATTRIBUTE_USE_APPROVAL_DATE + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getUseApprovalDate()) + "," +
     ATTRIBUTE_USE_APPROVAL_COMMENT + "=" +
       SqlHandler.delimitString(requestLineItemBean.getUseApprovalComment()) + "," +
     ATTRIBUTE_DPAS_CODE + "=" +
      SqlHandler.delimitString(requestLineItemBean.getDpasCode()) + "," +
     ATTRIBUTE_DO_NUMBER + "=" +
      SqlHandler.delimitString(requestLineItemBean.getDoNumber()) + "," +
     ATTRIBUTE_CARD_RECEIPT + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCardReceipt()) + "," +
     ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(requestLineItemBean.getPayloadId()) + "," +
     ATTRIBUTE_PART_GROUP_NO + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getPartGroupNo()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getExtendedPrice()) + "," +
     ATTRIBUTE_PREPAID_AMOUNT + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getPrepaidAmount()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(requestLineItemBean.getInventoryGroup()) + "," +
     ATTRIBUTE_REQUEST_LINE_STATUS + "=" +
       SqlHandler.delimitString(requestLineItemBean.getRequestLineStatus()) + "," +
     ATTRIBUTE_LAST_SHIPPED + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getLastShipped()) + "," +
     ATTRIBUTE_CAT_PART_NO_REVISION + "=" +
       SqlHandler.delimitString(requestLineItemBean.getCatPartNoRevision()) + "," +
     ATTRIBUTE_CATALOG_PRICE + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getCatalogPrice()) + "," +
     ATTRIBUTE_BASELINE_PRICE + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getBaselinePrice()) + "," +
     ATTRIBUTE_CATEGORY_STATUS + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCategoryStatus()) + "," +
     ATTRIBUTE_ARCHIVED_QTY_SHIPPED + "=" +
       StringHandler.nullIfZero(requestLineItemBean.getArchivedQtyShipped()) + "," +
     ATTRIBUTE_NOTICE + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getNotice()) + "," +
     ATTRIBUTE_TOTAL_QUANTITY_ISSUED + "=" +
       StringHandler.nullIfZero(requestLineItemBean.getTotalQuantityIssued()) + "," +
     ATTRIBUTE_TOTAL_QUANTITY_DELIVERED + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getTotalQuantityDelivered()) + "," +
     ATTRIBUTE_DATE_LAST_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(requestLineItemBean.getDateLastDelivered()) + "," +
     ATTRIBUTE_CABINET_REPLENISHMENT + "=" +
       SqlHandler.delimitString(requestLineItemBean.getCabinetReplenishment()) + "," +
     ATTRIBUTE_REQUESTED_DISPENSED_SIZE_UNIT + "=" +
      SqlHandler.delimitString(requestLineItemBean.getRequestedDispensedSizeUnit()) + "," +
     ATTRIBUTE_APPLICATION_DESC + "=" +
       SqlHandler.delimitString(requestLineItemBean.getApplicationDesc()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(requestLineItemBean.getFacilityId()) + "," +
     ATTRIBUTE_CURRENCY_ID + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCurrencyId()) + "," +
     ATTRIBUTE_UNIT_OF_SALE + "=" +
      SqlHandler.delimitString(requestLineItemBean.getUnitOfSale()) + "," +
     ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "=" +
      StringHandler.nullIfZero(requestLineItemBean.getUnitOfSaleQuantityPerEach()) + "," +
     ATTRIBUTE_BILLING_METHOD + "=" +
      SqlHandler.delimitString(requestLineItemBean.getBillingMethod()) + "," +
     ATTRIBUTE_ORDER_QUANTITY_RULE + "=" +
       SqlHandler.delimitString(requestLineItemBean.getOrderQuantityRule()) + "," +
     ATTRIBUTE_DROP_SHIP_OVERRIDE + "=" +
       SqlHandler.delimitString(requestLineItemBean.getDropShipOverride()) + "," +
     ATTRIBUTE_CUSTOMER_REQUISITION_NUMBER + "=" +
      SqlHandler.delimitString(requestLineItemBean.getCustomerRequisitionNumber()) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" +
      requestLineItemBean.getPrNumber();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

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

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection requestLineItemBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      RequestLineItemBean requestLineItemBean = new RequestLineItemBean();
      load(dataSetRow, requestLineItemBean);
      requestLineItemBeanColl.add(requestLineItemBean);
    }

    return requestLineItemBeanColl;
  }

  public int updateDoNumber(String doNumber, BigDecimal prNumber, BigDecimal lineItem)
		  throws BaseException {
	  Connection connection = null;
	  int result = 0;
	  try {
		  connection = getDbManager().getConnection();
		  result = updateDoNumber(doNumber, prNumber, lineItem, connection);
	  }
	  finally {
		  this.getDbManager().returnConnection(connection);
	  }
	  return result;
  }

  public int updateDoNumber(String doNumber, BigDecimal prNumber, BigDecimal lineItem, Connection conn)
		  throws BaseException {
	  String query  = "update " + TABLE + " set " +
			  ATTRIBUTE_DO_NUMBER + "=" +
			  SqlHandler.delimitString(doNumber) + ", " +
			  ATTRIBUTE_PO_NUMBER + "=" +
			  SqlHandler.delimitString(doNumber) + " " +
			  "where " + ATTRIBUTE_PR_NUMBER + "=" + prNumber +
			  " and " + ATTRIBUTE_LINE_ITEM + "=" + lineItem;
	  return new SqlManager().update(conn, query);
  }

  public int updateDoNumberAndTaxStatus(String doNumber, BigDecimal prNumber, BigDecimal lineItem,String taxExempt)
		  throws BaseException {
	  Connection connection = null;
	  int result = 0;
	  try {
		  connection = getDbManager().getConnection();
		  result = updateDoNumberAndTaxStatus(doNumber, prNumber, lineItem, taxExempt, connection);
	  }
	  finally {
		  this.getDbManager().returnConnection(connection);
	  }
	  return result;
  }

  public int updateDoNumberAndTaxStatus(String doNumber, BigDecimal prNumber, BigDecimal lineItem, String taxExempt, Connection conn)
		  throws BaseException {
	  String query  = "update " + TABLE + " set " +
			  ATTRIBUTE_DO_NUMBER + "=" +
			  SqlHandler.delimitString(doNumber) + ", " +
			  ATTRIBUTE_PO_NUMBER + "=" +
			  SqlHandler.delimitString(doNumber) + ", " +
			  ATTRIBUTE_TAX_EXEMPT + "=" +
			  SqlHandler.delimitString(taxExempt) + " " +
			  "where " + ATTRIBUTE_PR_NUMBER + "=" + prNumber +
			  " and " + ATTRIBUTE_LINE_ITEM + "=" + lineItem;
	  return new SqlManager().update(conn, query);
  }

  public int updateStatus(String requestLineStatus, String categoryStatus, Date releaseDate, BigDecimal prNumber, BigDecimal lineItem)
		  throws BaseException {
	  Connection connection = null;
	  int result = 0;
	  try {
		  connection = getDbManager().getConnection();
		  result = updateStatus(requestLineStatus, categoryStatus, releaseDate, prNumber, lineItem, connection);
	  }
	  finally {
		  this.getDbManager().returnConnection(connection);
	  }
	  return result;
  }

   public int updateStatus(String requestLineStatus, String categoryStatus, Date releaseDate, BigDecimal prNumber, BigDecimal lineItem, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_REQUEST_LINE_STATUS + "=" +
      SqlHandler.delimitString(requestLineStatus) + ", " +
     ATTRIBUTE_CATEGORY_STATUS + "=" +
      SqlHandler.delimitString(categoryStatus) + ", " +
     ATTRIBUTE_RELEASE_DATE + "=" +
      DateHandler.getOracleToDateFunction(releaseDate) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" + prNumber +
     " and " + ATTRIBUTE_LINE_ITEM + "=" + lineItem;
    return new SqlManager().update(conn, query);
   }

   public int updateQuantity(BigDecimal qty,String companyId, BigDecimal prNumber, String lineItem)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = updateQuantity(qty,companyId, prNumber, lineItem, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int updateQuantity(BigDecimal qty, String companyId, BigDecimal prNumber, String lineItem, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_QUANTITY + "=" +
      SqlHandler.validBigDecimal(qty) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" + prNumber +
     " and " + ATTRIBUTE_LINE_ITEM + "=" + lineItem+
     " and " + ATTRIBUTE_COMPANY_ID + "='" + companyId+"'";
    return new SqlManager().update(conn, query);
   }

}