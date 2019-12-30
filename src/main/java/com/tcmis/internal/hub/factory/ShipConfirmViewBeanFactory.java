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
import java.util.HashMap;
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
import com.tcmis.internal.hub.beans.ShipConfirmViewBean;
import com.tcmis.internal.hub.beans.ShipmentBean;

/******************************************************************************
 * CLASSNAME: ShipConfirmViewBeanFactory <br>
 * @version: 1.0, Jan 25, 2007 <br>
 *****************************************************************************/

public class ShipConfirmViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
  public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
  public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
  public String ATTRIBUTE_SOURCE_HUB_NAME = "SOURCE_HUB_NAME";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_DATE_PICKED = "DATE_PICKED";
  public String ATTRIBUTE_ISSUER_NAME = "ISSUER_NAME";
  public String ATTRIBUTE_BATCH = "BATCH";
  public String ATTRIBUTE_DELIVERED_DATE = "DELIVERED_DATE";
  public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
  public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
  public String ATTRIBUTE_HAZARD_CATEGORY = "HAZARD_CATEGORY";
  public String ATTRIBUTE_OVER_SHIPPED = "OVER_SHIPPED";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_AUTO_SHIP_CONFIRM = "AUTO_SHIP_CONFIRM";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
  public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
  public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
  public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
  public String ATTRIBUTE_CUSTOMER_SERVICE_REP_ID = "CUSTOMER_SERVICE_REP_ID";
  public String ATTRIBUTE_CSR_NAME = "CSR_NAME";
  public String ATTRIBUTE_ISSUE_QC_DATE = "ISSUE_QC_DATE";
  public String ATTRIBUTE_ISSUE_QC_USER_NAME = "ISSUE_QC_USER_NAME";
  public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
  public String ATTRIBUTE_MATERIAL_REQUEST_ORIGIN = "MATERIAL_REQUEST_ORIGIN";
  public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 = "SHIP_TO_ADDRESS_LINE_1";
  public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 = "SHIP_TO_ADDRESS_LINE_2";
  public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 = "SHIP_TO_ADDRESS_LINE_3";
  public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 = "SHIP_TO_ADDRESS_LINE_4";
  public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 = "SHIP_TO_ADDRESS_LINE_5";
  public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
  public String ATTRIBUTE_CUSTOMER_NOTE = "CUSTOMER_NOTE";
  public String ATTRIBUTE_SHIPTO_NOTE = "SHIPTO_NOTE";
  public String ATTRIBUTE_PR_INTERNAL_NOTE = "PR_INTERNAL_NOTE";
  public String ATTRIBUTE_LINE_INTERNAL_NOTE = "LINE_INTERNAL_NOTE";
  public String ATTRIBUTE_LINE_PURCHASING_NOTE = "LINE_PURCHASING_NOTE";
  public String ATTRIBUTE_HAZARDOUS = "HAZARDOUS";
  public String ATTRIBUTE_CMS = "CMS";
  public String ATTRIBUTE_DISTRIBUTION = "DISTRIBUTION";
  public String ATTRIBUTE_SHIPPING_REFERENCE = "SHIPPING_REFERENCE";
  public String ATTRIBUTE_PRINT_INVOICE = "PRINT_INVOICE";
  public String ATTRIBUTE_CONSIGNMENT_TRANSFER = "CONSIGNMENT_TRANSFER";
  public String ATTRIBUTE_PICKING_UNIT_ID = "PICKING_UNIT_ID";
  public String ATTRIBUTE_BOX_COUNT = "BOX_COUNT";
  public String ATTRIBUTE_LAST_PRO_FORMA_PRINT_DATE = "LAST_PRO_FORMA_PRINT_DATE";
  public String ATTRIBUTE_PRO_FORMA_REQUIRED = "PRO_FORMA_REQUIRED";
  
  //table name SHIPPING_REFERENCE, PRINT_INVOICE, CONSIGNMENT_TRANSFER

  public String TABLE = "SHIP_CONFIRM_VIEW";

  //constructor
  public ShipConfirmViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("shipmentId")) {
      return ATTRIBUTE_SHIPMENT_ID;
    }
    else if (attributeName.equals("carrierCode")) {
      return ATTRIBUTE_CARRIER_CODE;
    }
    else if (attributeName.equals("trackingNumber")) {
      return ATTRIBUTE_TRACKING_NUMBER;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("sourceHub")) {
      return ATTRIBUTE_SOURCE_HUB;
    }
    else if (attributeName.equals("sourceHubName")) {
      return ATTRIBUTE_SOURCE_HUB_NAME;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("datePicked")) {
      return ATTRIBUTE_DATE_PICKED;
    }
    else if (attributeName.equals("issuerName")) {
      return ATTRIBUTE_ISSUER_NAME;
    }
    else if (attributeName.equals("batch")) {
      return ATTRIBUTE_BATCH;
    }
    else if (attributeName.equals("deliveredDate")) {
      return ATTRIBUTE_DELIVERED_DATE;
    }
    else if (attributeName.equals("shipToCompanyId")) {
      return ATTRIBUTE_SHIP_TO_COMPANY_ID;
    }
    else if (attributeName.equals("shipToLocationId")) {
      return ATTRIBUTE_SHIP_TO_LOCATION_ID;
    }
    else if (attributeName.equals("hazardCategory")) {
      return ATTRIBUTE_HAZARD_CATEGORY;
    }
    else if (attributeName.equals("overShipped")) {
      return ATTRIBUTE_OVER_SHIPPED;
    }
    else if (attributeName.equals("deliveryPoint")) {
      return ATTRIBUTE_DELIVERY_POINT;
    }
    else if (attributeName.equals("applicationDesc")) {
      return ATTRIBUTE_APPLICATION_DESC;
    }
    else if (attributeName.equals("autoShipConfirm")) {
      return ATTRIBUTE_AUTO_SHIP_CONFIRM;
    }
    else if (attributeName.equals("companyName")) {
        return ATTRIBUTE_COMPANY_NAME;
      }
    else if (attributeName.equals("facilityName")) {
        return ATTRIBUTE_FACILITY_NAME;
      }
    else if (attributeName.equals("customerId")) {
        return ATTRIBUTE_CUSTOMER_ID;
      }
    else if (attributeName.equals("customerName")) {
        return ATTRIBUTE_CUSTOMER_NAME;
      }
    else if (attributeName.equals("customerServiceRepId")) {
        return ATTRIBUTE_CUSTOMER_SERVICE_REP_ID;
      }
    else if (attributeName.equals("csrName")) {
        return ATTRIBUTE_CSR_NAME;
      }
    else if (attributeName.equals("issueQcDate")) {
        return ATTRIBUTE_ISSUE_QC_DATE;
      }
    else if (attributeName.equals("issueQcUserName")) {
        return ATTRIBUTE_ISSUE_QC_USER_NAME;
      }
    else if (attributeName.equals("currencyId")) {
        return ATTRIBUTE_CURRENCY_ID;
      }
    else if (attributeName.equals("materialRequestOrigin")) {
        return ATTRIBUTE_MATERIAL_REQUEST_ORIGIN;
      }
    else if (attributeName.equals("shipToAddressLine1")) {
        return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1;
      }
    else if (attributeName.equals("shipToAddressLine2")) {
        return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2;
      }
    else if (attributeName.equals("shipToAddressLine3")) {
        return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3;
      }
    else if (attributeName.equals("shipToAddressLine4")) {
        return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4;
      }
    else if (attributeName.equals("shipToAddressLine5")) {
        return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5;
      }
    else if (attributeName.equals("paymentTerms")) {
        return ATTRIBUTE_PAYMENT_TERMS;
      }
    else if (attributeName.equals("customerNote")) {
        return ATTRIBUTE_CUSTOMER_NOTE;
      }
    else if (attributeName.equals("shiptoNote")) {
        return ATTRIBUTE_SHIPTO_NOTE;
      }
    else if (attributeName.equals("prInternalNote")) {
        return ATTRIBUTE_PR_INTERNAL_NOTE;
      }
    else if (attributeName.equals("lineInternalNote")) {
        return ATTRIBUTE_LINE_INTERNAL_NOTE;
      }
    else if (attributeName.equals("linePurchasingNote")) {
        return ATTRIBUTE_LINE_PURCHASING_NOTE;
      }
    else if (attributeName.equals("hazardous")) {
        return ATTRIBUTE_HAZARDOUS;
      }
    else if (attributeName.equals("cms")) {
		return ATTRIBUTE_CMS;
	}
	else if (attributeName.equals("distribution")) {
		return ATTRIBUTE_DISTRIBUTION;
	}
	else if (attributeName.equals("shippingReference")) {
		return ATTRIBUTE_SHIPPING_REFERENCE;
	}
	else if (attributeName.equals("printInvoice")) {
		return ATTRIBUTE_PRINT_INVOICE;
	}
	else if (attributeName.equals("consignmentTransfer")) {
		return ATTRIBUTE_CONSIGNMENT_TRANSFER;
	}
	else if (attributeName.equals("pickingUnitId")) {
		return ATTRIBUTE_PICKING_UNIT_ID;
	}
	else if (attributeName.equals("boxCount")) {
		return ATTRIBUTE_BOX_COUNT;
	}
	else if (attributeName.equals("lastProFormaPrintDate")) {
		return ATTRIBUTE_LAST_PRO_FORMA_PRINT_DATE;
	}
	else if (attributeName.equals("proFormaRequired")) {
		return ATTRIBUTE_PRO_FORMA_REQUIRED;
	}
    //table name SHIPPING_REFERENCE, PRINT_INVOICE, CONSIGNMENT_TRANSFER
    
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ShipConfirmViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(ShipConfirmViewBean shipConfirmViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
     "" + shipConfirmViewBean.getShipmentId());
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
   public int delete(ShipConfirmViewBean shipConfirmViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
     "" + shipConfirmViewBean.getShipmentId());
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
   public int insert(ShipConfirmViewBean shipConfirmViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(shipConfirmViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(ShipConfirmViewBean shipConfirmViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_SHIPMENT_ID + "," +
     ATTRIBUTE_CARRIER_CODE + "," +
     ATTRIBUTE_TRACKING_NUMBER + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_SOURCE_HUB + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_DATE_PICKED + "," +
     ATTRIBUTE_ISSUER_NAME + "," +
     ATTRIBUTE_BATCH + "," +
     ATTRIBUTE_DELIVERED_DATE + "," +
     ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
     ATTRIBUTE_HAZARD_CATEGORY + "," +
     ATTRIBUTE_OVER_SHIPPED + "," +
     ATTRIBUTE_DELIVERY_POINT + "," +
     ATTRIBUTE_APPLICATION_DESC + ")" +
     " values (" +
     shipConfirmViewBean.getShipmentId() + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getCarrierCode()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getTrackingNumber()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getInventoryGroup()) + "," +
     shipConfirmViewBean.getPrNumber() + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getLineItem()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getApplication()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getCatPartNo()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getSourceHub()) + "," +
     shipConfirmViewBean.getQuantity() + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getDatePicked()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getIssuerName()) + "," +
     shipConfirmViewBean.getBatch() + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getDeliveredDate()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getShipToCompanyId()) + "," +
       SqlHandler.delimitString(shipConfirmViewBean.getShipToLocationId()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getHazardCategory()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getOverShipped()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getDeliveryPoint()) + "," +
     SqlHandler.delimitString(shipConfirmViewBean.getApplicationDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(ShipConfirmViewBean shipConfirmViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(shipConfirmViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(ShipConfirmViewBean shipConfirmViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_SHIPMENT_ID + "=" +
      StringHandler.nullIfZero(shipConfirmViewBean.getShipmentId()) + "," +
     ATTRIBUTE_CARRIER_CODE + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getCarrierCode()) + "," +
     ATTRIBUTE_TRACKING_NUMBER + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getTrackingNumber()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getInventoryGroup()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(shipConfirmViewBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getLineItem()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getCompanyId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getFacilityId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getApplication()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_SOURCE_HUB + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getSourceHub()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(shipConfirmViewBean.getQuantity()) + "," +
     ATTRIBUTE_DATE_PICKED + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getDatePicked()) + "," +
     ATTRIBUTE_ISSUER_NAME + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getIssuerName()) + "," +
     ATTRIBUTE_BATCH + "=" +
      StringHandler.nullIfZero(shipConfirmViewBean.getBatch()) + "," +
     ATTRIBUTE_DELIVERED_DATE + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getDeliveredDate()) + "," +
     ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" +
       SqlHandler.delimitString(shipConfirmViewBean.getShipToCompanyId()) + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
       SqlHandler.delimitString(shipConfirmViewBean.getShipToLocationId()) + "," +
     ATTRIBUTE_HAZARD_CATEGORY + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getHazardCategory()) + "," +
     ATTRIBUTE_OVER_SHIPPED + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getOverShipped()) + "," +
     ATTRIBUTE_DELIVERY_POINT + "=" +
      SqlHandler.delimitString(shipConfirmViewBean.getDeliveryPoint()) + "," +
     ATTRIBUTE_APPLICATION_DESC + "=" +
       SqlHandler.delimitString(shipConfirmViewBean.getApplicationDesc()) + " " +
     "where " + ATTRIBUTE_SHIPMENT_ID + "=" +
      shipConfirmViewBean.getShipmentId();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
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

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException {
    Collection shipConfirmViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    ShipConfirmViewBean shipConfirmViewBean = null;
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      shipConfirmViewBean = new ShipConfirmViewBean();
      load(dataSetRow, shipConfirmViewBean);
      shipConfirmViewBeanColl.add(shipConfirmViewBean);
    }
// all of them have same     getAutoShipConfirm value
    if(shipConfirmViewBean!=null && "N".equalsIgnoreCase(shipConfirmViewBean.getAutoShipConfirm())) {
        ShipmentBeanFactory fac = new ShipmentBeanFactory(getDbManager());
    
    HashMap<String,Collection> map = new HashMap();
    for(ShipConfirmViewBean bean:(Collection<ShipConfirmViewBean>)shipConfirmViewBeanColl) {
    	map.put(bean.getShipToLocationId()+"%%%"+bean.getShipToCompanyId(), null);
    }
    for(String key:map.keySet()){
    	String[] vals = key.split("%%%");
        SearchCriteria crit = new SearchCriteria();
        crit.addCriterion("shipConfirmDate", SearchCriterion.IS, "null");
        crit.addCriterion("shipToLocationId", SearchCriterion.EQUALS, vals[0]);
        crit.addCriterion("companyId", SearchCriterion.EQUALS, vals[1]);
        SortCriteria childSortCriteria = new SortCriteria();
        childSortCriteria.addCriterion("shipmentId");
        map.put(key,fac.select(crit,childSortCriteria));
    }
    for(ShipConfirmViewBean bean:(Collection<ShipConfirmViewBean>)shipConfirmViewBeanColl) 
    	bean.setShipmentBeanCollection( map.get(bean.getShipToLocationId()+"%%%"+bean.getShipToCompanyId()) );
    }
    return shipConfirmViewBeanColl;
  }
}