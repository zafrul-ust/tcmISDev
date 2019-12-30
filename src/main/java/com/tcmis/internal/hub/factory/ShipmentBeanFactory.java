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
import com.tcmis.internal.hub.beans.ShipmentBean;

/******************************************************************************
 * CLASSNAME: ShipmentBeanFactory <br>
 * @version: 1.0, Feb 19, 2007 <br>
 *****************************************************************************/

public class ShipmentBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
  public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
  public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
  public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";
  public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
  public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
  public String ATTRIBUTE_BILL_OF_LADING = "BILL_OF_LADING";
  public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
  public String ATTRIBUTE_IATA_SIGNATURE_ID = "IATA_SIGNATURE_ID";
  public String ATTRIBUTE_VERIFIED_FOR_ASN = "VERIFIED_FOR_ASN";
  public String ATTRIBUTE_VERIFIED_FOR_ASN_COMMENTS = "VERIFIED_FOR_ASN_COMMENTS";
  public String ATTRIBUTE_IATA_ADDITIONAL_HANDLING = "IATA_ADDITIONAL_HANDLING";
  public String ATTRIBUTE_IATA_DATE_SIGNED = "IATA_DATE_SIGNED";
  public String ATTRIBUTE_CARRIER_LABEL_FILENAME = "CARRIER_LABEL_FILENAME";
  public String ATTRIBUTE_CARRIER_CURRENCY_CODE = "CARRIER_CURRENCY_CODE";
  public String ATTRIBUTE_CARRIER_TOTAL_CHARGES = "CARRIER_TOTAL_CHARGES";
  public String ATTRIBUTE_DEFAULT_TX_CARRIER_CODE = "DEFAULT_TX_CARRIER_CODE";//defaultTxCarrierCode
  public String ATTRIBUTE_DEFAULT_TX_CARRIER_NAME = "DEFAULT_TX_CARRIER_NAME";//defaultTxCarrierName
  public String ATTRIBUTE_DEFAULT_TX_CARRIER_ACCT = "DEFAULT_TX_CARRIER_ACCT";//defaultTxCarrierAcct
  public String ATTRIBUTE_DEFAULT_TX_CARRIER_MTHD = "DEFAULT_TX_CARRIER_MTHD";//defaultTxCarrierMthd
  public String ATTRIBUTE_DEFAULT_TX_CARRIER_COMP = "DEFAULT_TX_CARRIER_COMP";//defaultTxCarrierComp
  public String ATTRIBUTE_MODE_OF_TRANSPORT = "MODE_OF_TRANSPORT";
  public String ATTRIBUTE_INCOTERM = "INCOTERM";
  public String ATTRIBUTE_INVOICE_AT_SHIPPING = "INVOICE_AT_SHIPPING";
  public String ATTRIBUTE_INCOTERM_REQUIRED = "INCOTERM_REQUIRED";
  public String ATTRIBUTE_INVOICE_BY = "INVOICE_BY";
  public String ATTRIBUTE_PROPOSED_EXPORT_DATE = "PROPOSED_EXPORT_DATE";
  public String ATTRIBUTE_LAST_PRO_FORMA_PRINT_DATE = "LAST_PRO_FORMA_PRINT_DATE";
  public String ATTRIBUTE_PRO_FORMA_REQUIRED = "PRO_FORMA_REQUIRED";
  

	//table name
	public String TABLE = "SHIPMENT";

	//constructor
	public ShipmentBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}
		else if(attributeName.equals("dateInserted")) {
			return ATTRIBUTE_DATE_INSERTED;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
		else if(attributeName.equals("billOfLading")) {
			return ATTRIBUTE_BILL_OF_LADING;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("iataSignatureId")) {
			return ATTRIBUTE_IATA_SIGNATURE_ID;
		}
		else if(attributeName.equals("verifiedForAsn")) {
			return ATTRIBUTE_VERIFIED_FOR_ASN;
		}
		else if(attributeName.equals("verifiedForAsnComments")) {
			return ATTRIBUTE_VERIFIED_FOR_ASN_COMMENTS;
		}
		else if(attributeName.equals("iataAdditionalHandling")) {
			return ATTRIBUTE_IATA_ADDITIONAL_HANDLING;
		}
		else if(attributeName.equals("iataDateSigned")) {
			return ATTRIBUTE_IATA_DATE_SIGNED;
		}
		else if(attributeName.equals("carrierLabelFilename")) {
			return ATTRIBUTE_CARRIER_LABEL_FILENAME;
		}
		else if(attributeName.equals("carrierCurrencyCode")) {
			return ATTRIBUTE_CARRIER_CURRENCY_CODE;
		}
		else if(attributeName.equals("carrierTotalCharges")) {
			return ATTRIBUTE_CARRIER_TOTAL_CHARGES;
		}
		else if(attributeName.equals("defaultTxCarrierCode")) {
			return ATTRIBUTE_DEFAULT_TX_CARRIER_CODE;
		}
		else if(attributeName.equals("defaultTxCarrierName")) {
			return ATTRIBUTE_DEFAULT_TX_CARRIER_NAME;
		}
		else if(attributeName.equals("defaultTxCarrierAcct")) {
			return ATTRIBUTE_DEFAULT_TX_CARRIER_ACCT;
		}
		else if(attributeName.equals("defaultTxCarrierMthd")) {
			return ATTRIBUTE_DEFAULT_TX_CARRIER_MTHD;
		}
		else if(attributeName.equals("defaultTxCarrierComp")) {
			return ATTRIBUTE_DEFAULT_TX_CARRIER_COMP;
		}
		else if(attributeName.equals("modeOfTransport")) {
			return ATTRIBUTE_MODE_OF_TRANSPORT;
		}
		else if(attributeName.equals("incoterm")) {
			return ATTRIBUTE_INCOTERM;
		}
		else if(attributeName.equals("incotermRequired")) {
			return ATTRIBUTE_INCOTERM_REQUIRED;
		}
		else if(attributeName.equals("invoiceAtShipping")) {
			return ATTRIBUTE_INVOICE_AT_SHIPPING;
		}
		else if(attributeName.equals("invoiceBy")) {
			return ATTRIBUTE_INVOICE_BY;
		}
		else if(attributeName.equals("proposedExportDate")) {
			return ATTRIBUTE_PROPOSED_EXPORT_DATE;
		}
		else if (attributeName.equals("lastProFormaPrintDate")) {
			return ATTRIBUTE_LAST_PRO_FORMA_PRINT_DATE;
		}
		else if (attributeName.equals("proFormaRequired")) {
			return ATTRIBUTE_PRO_FORMA_REQUIRED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ShipmentBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(ShipmentBean shipmentBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
     "" + shipmentBean.getShipmentId());
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
   public int delete(ShipmentBean shipmentBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
     "" + shipmentBean.getShipmentId());
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

   //update
	public int update(ShipmentBean shipmentBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(shipmentBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updateFromHistory(ShipmentBean shipmentBean, Connection conn)
	throws BaseException {

	String query  = "update " + TABLE + " set " +
		ATTRIBUTE_SHIPMENT_ID + "=" + 
			StringHandler.nullIfZero(shipmentBean.getShipmentId()) + "," +
		ATTRIBUTE_TRACKING_NUMBER + "=" + 
			SqlHandler.delimitString(shipmentBean.getTrackingNumber()) + "," +
		ATTRIBUTE_CARRIER_CODE + "=" + 
			SqlHandler.delimitString(shipmentBean.getCarrierCode()) + "," +
		ATTRIBUTE_MODE_OF_TRANSPORT + "=" + 
			SqlHandler.delimitString(shipmentBean.getModeOfTransport()) + "," +
		ATTRIBUTE_INCOTERM + "=" + 
			SqlHandler.delimitString(shipmentBean.getIncoterm()) + //"," +
//		ATTRIBUTE_CARRIER_NAME + "=" + 
//			SqlHandler.delimitString(shipmentBean.getCarrierName()) + "," +
//		ATTRIBUTE_DELIVERY_TICKET + "=" + 
//			SqlHandler.delimitString(shipmentBean.getDeliveryTicket()) + "," +
//		ATTRIBUTE_CARRIER_LABEL_FILENAME + "=" + 
//			SqlHandler.delimitString(shipmentBean.getCarrierLabelFilename()) + "," +
//		ATTRIBUTE_CARRIER_CURRENCY_CODE + "=" + 
//			SqlHandler.delimitString(shipmentBean.getCarrierCurrencyCode()) + "," +
//		ATTRIBUTE_CARRIER_TOTAL_CHARGES + "=" + 
//			StringHandler.nullIfZero(shipmentBean.getCarrierTotalCharges()) + " " +
		" where " + ATTRIBUTE_SHIPMENT_ID + "=" +
			shipmentBean.getShipmentId();

	return new SqlManager().update(conn, query);
}


	public int update(ShipmentBean shipmentBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(shipmentBean.getShipmentId()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(shipmentBean.getTrackingNumber()) + "," +
			ATTRIBUTE_CARRIER_CODE + "=" + 
				SqlHandler.delimitString(shipmentBean.getCarrierCode()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(shipmentBean.getInventoryGroup()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(shipmentBean.getBranchPlant()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(shipmentBean.getShipToLocationId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(shipmentBean.getCompanyId()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				DateHandler.getOracleToDateFunction(shipmentBean.getDateDelivered()) + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "=" + 
				DateHandler.getOracleToDateFunction(shipmentBean.getShipConfirmDate()) + "," +
			ATTRIBUTE_DATE_INSERTED + "=" + 
				DateHandler.getOracleToDateFunction(shipmentBean.getDateInserted()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(shipmentBean.getCarrierName()) + "," +
			ATTRIBUTE_DELIVERY_TICKET + "=" + 
				SqlHandler.delimitString(shipmentBean.getDeliveryTicket()) + "," +
			ATTRIBUTE_BILL_OF_LADING + "=" + 
				SqlHandler.delimitString(shipmentBean.getBillOfLading()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(shipmentBean.getDateShipped()) + "," +
			ATTRIBUTE_IATA_SIGNATURE_ID + "=" + 
				StringHandler.nullIfZero(shipmentBean.getIataSignatureId()) + "," +
			ATTRIBUTE_VERIFIED_FOR_ASN + "=" + 
				SqlHandler.delimitString(shipmentBean.getVerifiedForAsn()) + "," +
			ATTRIBUTE_VERIFIED_FOR_ASN_COMMENTS + "=" + 
				SqlHandler.delimitString(shipmentBean.getVerifiedForAsnComments()) + "," +
			ATTRIBUTE_IATA_ADDITIONAL_HANDLING + "=" + 
				SqlHandler.delimitString(shipmentBean.getIataAdditionalHandling()) + "," +
			ATTRIBUTE_IATA_DATE_SIGNED + "=" + 
				DateHandler.getOracleToDateFunction(shipmentBean.getIataDateSigned()) + "," +
			ATTRIBUTE_CARRIER_LABEL_FILENAME + "=" + 
				SqlHandler.delimitString(shipmentBean.getCarrierLabelFilename()) + "," +
			ATTRIBUTE_CARRIER_CURRENCY_CODE + "=" + 
				SqlHandler.delimitString(shipmentBean.getCarrierCurrencyCode()) + "," +
			ATTRIBUTE_CARRIER_TOTAL_CHARGES + "=" + 
				StringHandler.nullIfZero(shipmentBean.getCarrierTotalCharges()) + ", " +
			ATTRIBUTE_MODE_OF_TRANSPORT + "=" + 
				SqlHandler.delimitString(shipmentBean.getModeOfTransport()) + "," +
			ATTRIBUTE_INCOTERM + "=" + 
				SqlHandler.delimitString(shipmentBean.getIncoterm()) + "  " +			
			"where " + ATTRIBUTE_SHIPMENT_ID + "=" +
				shipmentBean.getShipmentId();

		return new SqlManager().update(conn, query);
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

		Collection shipmentBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ShipmentBean shipmentBean = new ShipmentBean();
			load(dataSetRow, shipmentBean);
			shipmentBeanColl.add(shipmentBean);
		}

		return shipmentBeanColl;
	}

public Collection selectWithCarrier(SearchCriteria criteria, SortCriteria sortCriteria, String confirmedShipments) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectWithCarrier(criteria, sortCriteria, confirmedShipments,connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

 public Collection selectWithCarrier(String query, String confirmedShipments) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectWithCarrier(query, confirmedShipments,connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

 public Collection selectWithCarrier(SearchCriteria criteria, SortCriteria sortCriteria, String confirmedShipments, Connection conn) throws
 BaseException {

	 String query = "select * from " + TABLE + " " +
	 getWhereClause(criteria);
	 if (confirmedShipments !=null && confirmedShipments.length() > 0)
	 {
		 query += "and ("+ATTRIBUTE_SHIP_CONFIRM_DATE+" IS NULL or "+ATTRIBUTE_SHIPMENT_ID+" in ("+confirmedShipments+")) ";
	 }
	 else
	 {
		 query += "and "+ATTRIBUTE_SHIP_CONFIRM_DATE+" IS NULL ";
	 }
	 query +=getOrderByClause(sortCriteria);
	 return selectWithCarrier(query,confirmedShipments,conn);
 }
  public Collection selectWithCarrier(String query, String confirmedShipments, Connection conn) throws
      BaseException {
    Collection shipmentBeanColl = new Vector();
      
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ShipmentBean shipmentBean = new ShipmentBean();
      load(dataSetRow, shipmentBean);
      shipmentBeanColl.add(shipmentBean);
    }
    HashMap<String,Collection> map = new HashMap();
    for(ShipmentBean bean:(Collection<ShipmentBean>)shipmentBeanColl) {
    	map.put(bean.getInventoryGroup(), null);
    }
    CarrierInfoBeanFactory factory = new CarrierInfoBeanFactory(getDbManager());
    SortCriteria sc = new SortCriteria();
    sc.addCriterion("carrierName");
    sc.addCriterion("carrierMethod");
    sc.addCriterion("carrierCode");
    for(String key:map.keySet()){
    	map.put(key,factory.select(new SearchCriteria("inventoryGroup", SearchCriterion.IN, "'"+key+"','ALL'"), sc));
    }
    for(ShipmentBean bean:(Collection<ShipmentBean>)shipmentBeanColl)
    	bean.setCarrierInfoBeanCollection( map.get(bean.getInventoryGroup()) );
    return shipmentBeanColl;
  }

  public Collection selectDistinct(SearchCriteria criteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectDistinct(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectDistinct(SearchCriteria criteria, Connection conn) throws
      BaseException {
    Collection shipmentBeanColl = new Vector();
    String query = "select distinct shipment_id,TRACKING_NUMBER,CARRIER_CODE from " + TABLE + " " +
        getWhereClause(criteria) + " order by SHIPMENT_ID";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ShipmentBean shipmentBean = new ShipmentBean();
      load(dataSetRow, shipmentBean);
      shipmentBeanColl.add(shipmentBean);
    }
    return shipmentBeanColl;
  }
}