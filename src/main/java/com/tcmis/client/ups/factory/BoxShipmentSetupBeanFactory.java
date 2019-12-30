package com.tcmis.client.ups.factory;


import com.tcmis.client.ups.beans.BoxShipmentSetupBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: BoxShipmentSetupBeanFactory <br>
 * @version: 1.0, Jan 28, 2008 <br>
 *****************************************************************************/


public class BoxShipmentSetupBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BOX_ID = "BOX_ID";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SHIPPING_BATCH_ID = "SHIPPING_BATCH_ID";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_USGOV_SHIPMENT_NUMBER = "USGOV_SHIPMENT_NUMBER";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
	public String ATTRIBUTE_USGOV_TCN = "USGOV_TCN";
	public String ATTRIBUTE_CARRIER_TRANSPORT_CHARGES = "CARRIER_TRANSPORT_CHARGES";
	public String ATTRIBUTE_CARRIER_SERVICE_CHARGES = "CARRIER_SERVICE_CHARGES";
	public String ATTRIBUTE_CARRIER_TOTAL_CHARGES = "CARRIER_TOTAL_CHARGES";
	public String ATTRIBUTE_CARRIER_BILLING_WEIGHT = "CARRIER_BILLING_WEIGHT";
	public String ATTRIBUTE_CARRIER_LABEL_FILENAME = "CARRIER_LABEL_FILENAME";
	public String ATTRIBUTE_CARRIER_CONTROL_LOG_RCPT = "CARRIER_CONTROL_LOG_RCPT";
	public String ATTRIBUTE_CARRIER_BOX_SERVICE_CHARGE = "CARRIER_BOX_SERVICE_CHARGE";
	public String ATTRIBUTE_CARRIER_SHIP_TRACKING_NUM = "CARRIER_SHIP_TRACKING_NUM";
	public String ATTRIBUTE_CARRIER_LABEL = "CARRIER_LABEL";

	//table name
	public String TABLE = "BOX_SHIPMENT_SETUP";


	//constructor
	public BoxShipmentSetupBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("boxId")) {
			return ATTRIBUTE_BOX_ID;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("shippingBatchId")) {
			return ATTRIBUTE_SHIPPING_BATCH_ID;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("usgovShipmentNumber")) {
			return ATTRIBUTE_USGOV_SHIPMENT_NUMBER;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else if(attributeName.equals("usgovTcn")) {
			return ATTRIBUTE_USGOV_TCN;
		}
		else if(attributeName.equals("carrierTransportCharges")) {
			return ATTRIBUTE_CARRIER_TRANSPORT_CHARGES;
		}
		else if(attributeName.equals("carrierServiceCharges")) {
			return ATTRIBUTE_CARRIER_SERVICE_CHARGES;
		}
		else if(attributeName.equals("carrierTotalCharges")) {
			return ATTRIBUTE_CARRIER_TOTAL_CHARGES;
		}
		else if(attributeName.equals("carrierBillingWeight")) {
			return ATTRIBUTE_CARRIER_BILLING_WEIGHT;
		}
		else if(attributeName.equals("carrierLabelFilename")) {
			return ATTRIBUTE_CARRIER_LABEL_FILENAME;
		}
		else if(attributeName.equals("carrierControlLogRcpt")) {
			return ATTRIBUTE_CARRIER_CONTROL_LOG_RCPT;
		}
		else if(attributeName.equals("carrierBoxServiceCharge")) {
			return ATTRIBUTE_CARRIER_BOX_SERVICE_CHARGE;
		}
		else if(attributeName.equals("carrierShipTrackingNum")) {
			return ATTRIBUTE_CARRIER_SHIP_TRACKING_NUM;
		}
		else if(attributeName.equals("carrierLabel")) {
			return ATTRIBUTE_CARRIER_LABEL;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, BoxShipmentSetupBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(BoxShipmentSetupBean boxShipmentSetupBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("boxId", "SearchCriterion.EQUALS",
			"" + boxShipmentSetupBean.getBoxId());

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


	public int delete(BoxShipmentSetupBean boxShipmentSetupBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("boxId", "SearchCriterion.EQUALS",
			"" + boxShipmentSetupBean.getBoxId());

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
/*
	public int insertBlob(BoxShipmentSetupBean boxShipmentSetupBean)
		throws BaseException, Exception {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			//result = insert(boxShipmentSetupBean, connection);
            File file = new File(boxShipmentSetupBean.getCarrierLabelFilename());
            InputStream is = new FileInputStream(file);
            PreparedStatement ps = null;
String sql = "insert into " + TABLE + " (" +
			ATTRIBUTE_BOX_ID + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_USGOV_SHIPMENT_NUMBER + "," +
			ATTRIBUTE_CARRIER_TRANSPORT_CHARGES + "," +
			ATTRIBUTE_CARRIER_SERVICE_CHARGES + "," +
			ATTRIBUTE_CARRIER_TOTAL_CHARGES + "," +
			ATTRIBUTE_CARRIER_BILLING_WEIGHT + "," +
			ATTRIBUTE_CARRIER_LABEL_FILENAME + "," +
			ATTRIBUTE_CARRIER_BOX_SERVICE_CHARGE + "," +
			ATTRIBUTE_CARRIER_SHIP_TRACKING_NUM + "," +
			ATTRIBUTE_CARRIER_LABEL + ")" +
			" values (?, ?, ?, ?,?,?,?,?,?,?,?,?,?)";
ps = connection.prepareStatement(sql);
ps.setString(1,boxShipmentSetupBean .getBoxId());
ps.setString(2, boxShipmentSetupBean.getTrackingNumber());
ps.setString(3, boxShipmentSetupBean.getCarrierName());
ps.setBigDecimal(4, boxShipmentSetupBean.getShipmentId());
            ps.setString(5, boxShipmentSetupBean.getUsgovShipmentNumber());
            ps.setBigDecimal(6, boxShipmentSetupBean.getCarrierTransportCharges());
            ps.setBigDecimal(7, boxShipmentSetupBean.getCarrierServiceCharges());
            ps.setBigDecimal(8, boxShipmentSetupBean.getCarrierTotalCharges());
            ps.setBigDecimal(9, boxShipmentSetupBean.getCarrierBillingWeight());
            ps.setString(10, boxShipmentSetupBean.getCarrierLabelFilename());
            ps.setBigDecimal(11, boxShipmentSetupBean.getCarrierBoxServiceCharge());
            ps.setString(12, boxShipmentSetupBean.getCarrierShipTrackingNum());
ps.setBinaryStream(13, is, (int) file.length());
ps.executeUpdate();
        }
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}
*/
	//insert
	public int insert(BoxShipmentSetupBean boxShipmentSetupBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(boxShipmentSetupBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(BoxShipmentSetupBean boxShipmentSetupBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BOX_ID + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_DELIVERY_TICKET + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_DATE_SHIPPED + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_SHIPPING_BATCH_ID + "," +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_USGOV_SHIPMENT_NUMBER + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "," +
			ATTRIBUTE_USGOV_TCN + "," +
			ATTRIBUTE_CARRIER_TRANSPORT_CHARGES + "," +
			ATTRIBUTE_CARRIER_SERVICE_CHARGES + "," +
			ATTRIBUTE_CARRIER_TOTAL_CHARGES + "," +
			ATTRIBUTE_CARRIER_BILLING_WEIGHT + "," +
			ATTRIBUTE_CARRIER_LABEL_FILENAME + "," +
			ATTRIBUTE_CARRIER_CONTROL_LOG_RCPT + "," +
			ATTRIBUTE_CARRIER_BOX_SERVICE_CHARGE + "," +
			ATTRIBUTE_CARRIER_SHIP_TRACKING_NUM + ")" +
			" values (" +
			SqlHandler.delimitString(boxShipmentSetupBean.getBoxId()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getTrackingNumber()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getCarrierName()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getDeliveryTicket()) + "," +
			DateHandler.getOracleToDateFunction(boxShipmentSetupBean.getDateDelivered()) + "," +
			DateHandler.getOracleToDateFunction(boxShipmentSetupBean.getDateShipped()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getSupplier()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getShipFromLocationId()) + "," +
			boxShipmentSetupBean.getShippingBatchId() + "," +
			boxShipmentSetupBean.getShipmentId() + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getUsgovShipmentNumber()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getCompanyId()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getCustomerPoNo()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getUsgovTcn()) + "," +
			boxShipmentSetupBean.getCarrierTransportCharges() + "," +
			boxShipmentSetupBean.getCarrierServiceCharges() + "," +
			boxShipmentSetupBean.getCarrierTotalCharges() + "," +
			boxShipmentSetupBean.getCarrierBillingWeight() + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getCarrierLabelFilename()) + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getCarrierControlLogRcpt()) + "," +
			boxShipmentSetupBean.getCarrierBoxServiceCharge() + "," +
			SqlHandler.delimitString(boxShipmentSetupBean.getCarrierShipTrackingNum()) + ")";

		return sqlManager.update(conn, query);

    }
/*
//you need to verify the primary key(s) before uncommenting this

	//update
	public int update(BoxShipmentSetupBean boxShipmentSetupBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(boxShipmentSetupBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(BoxShipmentSetupBean boxShipmentSetupBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BOX_ID + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getBoxId()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getTrackingNumber()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getCarrierName()) + "," +
			ATTRIBUTE_DELIVERY_TICKET + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getDeliveryTicket()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				DateHandler.getOracleToDateFunction(boxShipmentSetupBean.getDateDelivered()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(boxShipmentSetupBean.getDateShipped()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getShipToLocationId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getInventoryGroup()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getSupplier()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_SHIPPING_BATCH_ID + "=" + 
				StringHandler.nullIfZero(boxShipmentSetupBean.getShippingBatchId()) + "," +
			ATTRIBUTE_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(boxShipmentSetupBean.getShipmentId()) + "," +
			ATTRIBUTE_USGOV_SHIPMENT_NUMBER + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getUsgovShipmentNumber()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getCompanyId()) + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getCustomerPoNo()) + "," +
			ATTRIBUTE_USGOV_TCN + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getUsgovTcn()) + "," +
			ATTRIBUTE_CARRIER_TRANSPORT_CHARGES + "=" + 
				StringHandler.nullIfZero(boxShipmentSetupBean.getCarrierTransportCharges()) + "," +
			ATTRIBUTE_CARRIER_SERVICE_CHARGES + "=" + 
				StringHandler.nullIfZero(boxShipmentSetupBean.getCarrierServiceCharges()) + "," +
			ATTRIBUTE_CARRIER_TOTAL_CHARGES + "=" + 
				StringHandler.nullIfZero(boxShipmentSetupBean.getCarrierTotalCharges()) + "," +
			ATTRIBUTE_CARRIER_BILLING_WEIGHT + "=" + 
				StringHandler.nullIfZero(boxShipmentSetupBean.getCarrierBillingWeight()) + "," +
			ATTRIBUTE_CARRIER_LABEL_FILENAME + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getCarrierLabelFilename()) + "," +
			ATTRIBUTE_CARRIER_CONTROL_LOG_RCPT + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getCarrierControlLogRcpt()) + "," +
			ATTRIBUTE_CARRIER_BOX_SERVICE_CHARGE + "=" + 
				StringHandler.nullIfZero(boxShipmentSetupBean.getCarrierBoxServiceCharge()) + "," +
			ATTRIBUTE_CARRIER_SHIP_TRACKING_NUM + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getCarrierShipTrackingNum()) + "," +
			ATTRIBUTE_CARRIER_LABEL + "=" + 
				SqlHandler.delimitString(boxShipmentSetupBean.getCarrierLabel()) + " " +
			"where " + ATTRIBUTE_BOX_ID + "=" +
				boxShipmentSetupBean.getBoxId();

		return new SqlManager().update(conn, query);
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

		Collection boxShipmentSetupBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BoxShipmentSetupBean boxShipmentSetupBean = new BoxShipmentSetupBean();
			load(dataSetRow, boxShipmentSetupBean);
			boxShipmentSetupBeanColl.add(boxShipmentSetupBean);
		}

		return boxShipmentSetupBeanColl;
	}
}