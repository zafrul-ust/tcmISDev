package com.tcmis.client.fedex.factory;


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
import com.tcmis.client.fedex.beans.FedexShippingInfoBean;


/******************************************************************************
 * CLASSNAME: FedexShippingInfoBeanFactory <br>
 * @version: 1.0, Jan 29, 2009 <br>
 *****************************************************************************/


public class FedexShippingInfoBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ORDER_NO = "ORDER_NO";
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_BOX_ID = "BOX_ID";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_PACKAGE_CODE = "PACKAGE_CODE";
	public String ATTRIBUTE_SERVICE_CODE = "SERVICE_CODE";
	public String ATTRIBUTE_SHIPMENT_CHARGE_TYPE = "SHIPMENT_CHARGE_TYPE";	
	public String ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_1 = "FEDEX_ACCOUNT_ADDRESS_1";
	public String ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_2 = "FEDEX_ACCOUNT_ADDRESS_2";
	public String ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_3 = "FEDEX_ACCOUNT_ADDRESS_3";
	public String ATTRIBUTE_FEDEX_ACCOUNT_CITY = "FEDEX_ACCOUNT_CITY";
	public String ATTRIBUTE_FEDEX_ACCOUNT_STATE = "FEDEX_ACCOUNT_STATE";
	public String ATTRIBUTE_FEDEX_COUNTRY_CODE = "FEDEX_COUNTRY_CODE";
	public String ATTRIBUTE_FEDEX_ACCOUNT_ZIP = "FEDEX_ACCOUNT_ZIP";
	public String ATTRIBUTE_SERVICE_TYPE = "SERVICE_TYPE";
	public String ATTRIBUTE_FREIGHT_TERMS = "FREIGHT_TERMS";
	public String ATTRIBUTE_SHIP_TO_ID = "SHIP_TO_ID";
	public String ATTRIBUTE_SHIP_TO_ADDR_QUAL = "SHIP_TO_ADDR_QUAL";
	public String ATTRIBUTE_SHIP_TO_COMPANY = "SHIP_TO_COMPANY";
	public String ATTRIBUTE_SHIP_TO_ADD1 = "SHIP_TO_ADD1";
	public String ATTRIBUTE_SHIP_TO_ADD2 = "SHIP_TO_ADD2";
	public String ATTRIBUTE_SHIP_TO_ADD3 = "SHIP_TO_ADD3";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_STATE = "SHIP_TO_STATE";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_SHIP_TO_COUNTRY = "SHIP_TO_COUNTRY";
	public String ATTRIBUTE_SHIP_TO_PHONE = "SHIP_TO_PHONE";
	public String ATTRIBUTE_SHIP_FROM_ID = "SHIP_FROM_ID";
	public String ATTRIBUTE_SHIP_FROM_ADDR_QUAL = "SHIP_FROM_ADDR_QUAL";
	public String ATTRIBUTE_SHIP_FROM_COMPANY = "SHIP_FROM_COMPANY";
	public String ATTRIBUTE_SHIP_FROM_ADD_1 = "SHIP_FROM_ADD_1";
	public String ATTRIBUTE_SHIP_FROM_ADD_2 = "SHIP_FROM_ADD_2";
	public String ATTRIBUTE_SHIP_FROM_ADD_3 = "SHIP_FROM_ADD_3";
	public String ATTRIBUTE_SHIP_FROM_CITY = "SHIP_FROM_CITY";
	public String ATTRIBUTE_SHIP_FROM_STATE = "SHIP_FROM_STATE";
	public String ATTRIBUTE_SHIP_FROM_COUNTRY = "SHIP_FROM_COUNTRY";
	public String ATTRIBUTE_SHIP_FROM_ZIP = "SHIP_FROM_ZIP";
	public String ATTRIBUTE_SHIP_FROM_PHONE = "SHIP_FROM_PHONE";
	public String ATTRIBUTE_FREIGHT_CLASS = "FREIGHT_CLASS";
	public String ATTRIBUTE_BOX_QUANTITY = "BOX_QUANTITY";
	public String ATTRIBUTE_WEIGHT = "WEIGHT";
	public String ATTRIBUTE_WEIGHT_UM = "WEIGHT_UM";
	public String ATTRIBUTE_HAZ_WEIGHT = "HAZ_WEIGHT";
    public String ATTRIBUTE_HAZMAT_FLAG = "HAZMAT_FLAG";
	public String ATTRIBUTE_CARRIER_SECURITY_KEY = "CARRIER_SECURITY_KEY";
	public String ATTRIBUTE_CARRIER_METER_NUMBER = "CARRIER_METER_NUMBER";
	public String ATTRIBUTE_SHIP_TIME_STAMP = "SHIP_TIME_STAMP";
  public String ATTRIBUTE_CARRIER_ACCOUNT = "CARRIER_ACCOUNT";
	public String ATTRIBUTE_CARRIER_PASSWORD = "CARRIER_PASSWORD";

  //table name
	public String TABLE = "FEDEX_SHIPPING_INFO";


	//constructor
	public FedexShippingInfoBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("orderNo")) {
			return ATTRIBUTE_ORDER_NO;
		}
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("boxId")) {
			return ATTRIBUTE_BOX_ID;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("packageCode")) {
			return ATTRIBUTE_PACKAGE_CODE;
		}
		else if(attributeName.equals("serviceCode")) {
			return ATTRIBUTE_SERVICE_CODE;
		}
		else if(attributeName.equals("shipmentChargeType")) {
			return ATTRIBUTE_SHIPMENT_CHARGE_TYPE;
		}
		else if(attributeName.equals("carrierAccount")) {
			return ATTRIBUTE_CARRIER_ACCOUNT;
		}
		else if(attributeName.equals("fedexAccountAddress1")) {
			return ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_1;
		}
		else if(attributeName.equals("fedexAccountAddress2")) {
			return ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_2;
		}
		else if(attributeName.equals("fedexAccountAddress3")) {
			return ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_3;
		}
		else if(attributeName.equals("fedexAccountCity")) {
			return ATTRIBUTE_FEDEX_ACCOUNT_CITY;
		}
		else if(attributeName.equals("fedexAccountState")) {
			return ATTRIBUTE_FEDEX_ACCOUNT_STATE;
		}
		else if(attributeName.equals("fedexCountryCode")) {
			return ATTRIBUTE_FEDEX_COUNTRY_CODE;
		}
		else if(attributeName.equals("fedexAccountZip")) {
			return ATTRIBUTE_FEDEX_ACCOUNT_ZIP;
		}
		else if(attributeName.equals("serviceType")) {
			return ATTRIBUTE_SERVICE_TYPE;
		}
		else if(attributeName.equals("freightTerms")) {
			return ATTRIBUTE_FREIGHT_TERMS;
		}
		else if(attributeName.equals("shipToId")) {
			return ATTRIBUTE_SHIP_TO_ID;
		}
		else if(attributeName.equals("shipToAddrQual")) {
			return ATTRIBUTE_SHIP_TO_ADDR_QUAL;
		}
		else if(attributeName.equals("shipToCompany")) {
			return ATTRIBUTE_SHIP_TO_COMPANY;
		}
		else if(attributeName.equals("shipToAdd1")) {
			return ATTRIBUTE_SHIP_TO_ADD1;
		}
		else if(attributeName.equals("shipToAdd2")) {
			return ATTRIBUTE_SHIP_TO_ADD2;
		}
		else if(attributeName.equals("shipToAdd3")) {
			return ATTRIBUTE_SHIP_TO_ADD3;
		}
		else if(attributeName.equals("shipToCity")) {
			return ATTRIBUTE_SHIP_TO_CITY;
		}
		else if(attributeName.equals("shipToState")) {
			return ATTRIBUTE_SHIP_TO_STATE;
		}
		else if(attributeName.equals("shipToZip")) {
			return ATTRIBUTE_SHIP_TO_ZIP;
		}
		else if(attributeName.equals("shipToCountry")) {
			return ATTRIBUTE_SHIP_TO_COUNTRY;
		}
		else if(attributeName.equals("shipToPhone")) {
			return ATTRIBUTE_SHIP_TO_PHONE;
		}
		else if(attributeName.equals("shipFromId")) {
			return ATTRIBUTE_SHIP_FROM_ID;
		}
		else if(attributeName.equals("shipFromAddrQual")) {
			return ATTRIBUTE_SHIP_FROM_ADDR_QUAL;
		}
		else if(attributeName.equals("shipFromCompany")) {
			return ATTRIBUTE_SHIP_FROM_COMPANY;
		}
		else if(attributeName.equals("shipFromAdd1")) {
			return ATTRIBUTE_SHIP_FROM_ADD_1;
		}
		else if(attributeName.equals("shipFromAdd2")) {
			return ATTRIBUTE_SHIP_FROM_ADD_2;
		}
		else if(attributeName.equals("shipFromAdd3")) {
			return ATTRIBUTE_SHIP_FROM_ADD_3;
		}
		else if(attributeName.equals("shipFromCity")) {
			return ATTRIBUTE_SHIP_FROM_CITY;
		}
		else if(attributeName.equals("shipFromState")) {
			return ATTRIBUTE_SHIP_FROM_STATE;
		}
		else if(attributeName.equals("shipFromCountry")) {
			return ATTRIBUTE_SHIP_FROM_COUNTRY;
		}
		else if(attributeName.equals("shipFromZip")) {
			return ATTRIBUTE_SHIP_FROM_ZIP;
		}
		else if(attributeName.equals("shipFromPhone")) {
			return ATTRIBUTE_SHIP_FROM_PHONE;
		}
		else if(attributeName.equals("freightClass")) {
			return ATTRIBUTE_FREIGHT_CLASS;
		}
		else if(attributeName.equals("boxQuantity")) {
			return ATTRIBUTE_BOX_QUANTITY;
		}
		else if(attributeName.equals("weight")) {
			return ATTRIBUTE_WEIGHT;
		}
		else if(attributeName.equals("weightUm")) {
			return ATTRIBUTE_WEIGHT_UM;
		}
		else if(attributeName.equals("hazmatFlag")) {
			return ATTRIBUTE_HAZMAT_FLAG;
		}
		else if(attributeName.equals("carrierSecurityKey")) {
			return ATTRIBUTE_CARRIER_SECURITY_KEY;
		}
		else if(attributeName.equals("carrierMeterNumber")) {
			return ATTRIBUTE_CARRIER_METER_NUMBER;
		}
		else if(attributeName.equals("shipTimeStamp")) {
			return ATTRIBUTE_SHIP_TIME_STAMP;
		}
		else if(attributeName.equals("carrierPassword")) {
			return ATTRIBUTE_CARRIER_PASSWORD;
		}
		else if(attributeName.equals("hazWeight")) {
			return ATTRIBUTE_HAZ_WEIGHT;
		}        
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FedexShippingInfoBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(FedexShippingInfoBean fedexShippingInfoBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("orderNo", "SearchCriterion.EQUALS",
			"" + fedexShippingInfoBean.getOrderNo());

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


	public int delete(FedexShippingInfoBean fedexShippingInfoBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("orderNo", "SearchCriterion.EQUALS",
			"" + fedexShippingInfoBean.getOrderNo());

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
	public int insert(FedexShippingInfoBean fedexShippingInfoBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(fedexShippingInfoBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(FedexShippingInfoBean fedexShippingInfoBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ORDER_NO + "," +
			ATTRIBUTE_PACKING_GROUP_ID + "," +
			ATTRIBUTE_BOX_ID + "," +
			ATTRIBUTE_CARRIER_CODE + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_PACKAGE_CODE + "," +
			ATTRIBUTE_SERVICE_CODE + "," +
			ATTRIBUTE_SHIPMENT_CHARGE_TYPE + "," +
			ATTRIBUTE_UPS_ACCOUNT_NUMBER + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_1 + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_2 + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_3 + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_CITY + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_STATE + "," +
			ATTRIBUTE_FEDEX_COUNTRY_CODE + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ZIP + "," +
			ATTRIBUTE_SERVICE_TYPE + "," +
			ATTRIBUTE_FREIGHT_TERMS + "," +
			ATTRIBUTE_SHIP_TO_ID + "," +
			ATTRIBUTE_SHIP_TO_ADDR_QUAL + "," +
			ATTRIBUTE_SHIP_TO_COMPANY + "," +
			ATTRIBUTE_SHIP_TO_ADD1 + "," +
			ATTRIBUTE_SHIP_TO_ADD2 + "," +
			ATTRIBUTE_SHIP_TO_ADD3 + "," +
			ATTRIBUTE_SHIP_TO_CITY + "," +
			ATTRIBUTE_SHIP_TO_STATE + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY + "," +
			ATTRIBUTE_SHIP_TO_PHONE + "," +
			ATTRIBUTE_SHIP_FROM_ID + "," +
			ATTRIBUTE_SHIP_FROM_ADDR_QUAL + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY + "," +
			ATTRIBUTE_SHIP_FROM_ADD_1 + "," +
			ATTRIBUTE_SHIP_FROM_ADD_2 + "," +
			ATTRIBUTE_SHIP_FROM_ADD_3 + "," +
			ATTRIBUTE_SHIP_FROM_CITY + "," +
			ATTRIBUTE_SHIP_FROM_STATE + "," +
			ATTRIBUTE_SHIP_FROM_COUNTRY + "," +
			ATTRIBUTE_SHIP_FROM_ZIP + "," +
			ATTRIBUTE_SHIP_FROM_PHONE + "," +
			ATTRIBUTE_FREIGHT_CLASS + "," +
			ATTRIBUTE_BOX_QUANTITY + "," +
			ATTRIBUTE_WEIGHT + "," +
			ATTRIBUTE_WEIGHT_UM + "," +
			ATTRIBUTE_HAZMAT_FLAG + ")" +
			" values (" +
			SqlHandler.delimitString(fedexShippingInfoBean.getOrderNo()) + "," +
			fedexShippingInfoBean.getPackingGroupId() + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getBoxId()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getCarrierCode()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getCarrierName()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getPackageCode()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getServiceCode()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipmentChargeType()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getUpsAccountNumber()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountAddress1()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountAddress2()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountAddress3()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountCity()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountState()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFedexCountryCode()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountZip()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getServiceType()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFreightTerms()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToId()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToAddrQual()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToCompany()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToAdd1()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToAdd2()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToAdd3()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToCity()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToState()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToZip()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToCountry()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipToPhone()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromId()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAddrQual()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromCompany()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAdd1()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAdd2()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAdd3()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromCity()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromState()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromCountry()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromZip()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getShipFromPhone()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getFreightClass()) + "," +
			fedexShippingInfoBean.getBoxQuantity() + "," +
			fedexShippingInfoBean.getWeight() + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getWeightUm()) + "," +
			SqlHandler.delimitString(fedexShippingInfoBean.getHazmatFlag()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(FedexShippingInfoBean fedexShippingInfoBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(fedexShippingInfoBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(FedexShippingInfoBean fedexShippingInfoBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ORDER_NO + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getOrderNo()) + "," +
			ATTRIBUTE_PACKING_GROUP_ID + "=" + 
				StringHandler.nullIfZero(fedexShippingInfoBean.getPackingGroupId()) + "," +
			ATTRIBUTE_BOX_ID + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getBoxId()) + "," +
			ATTRIBUTE_CARRIER_CODE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getCarrierCode()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getCarrierName()) + "," +
			ATTRIBUTE_PACKAGE_CODE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getPackageCode()) + "," +
			ATTRIBUTE_SERVICE_CODE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getServiceCode()) + "," +
			ATTRIBUTE_SHIPMENT_CHARGE_TYPE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipmentChargeType()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_NUMBER + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getUpsAccountNumber()) + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_1 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountAddress1()) + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_2 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountAddress2()) + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ADDRESS_3 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountAddress3()) + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_CITY + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountCity()) + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_STATE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountState()) + "," +
			ATTRIBUTE_FEDEX_COUNTRY_CODE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFedexCountryCode()) + "," +
			ATTRIBUTE_FEDEX_ACCOUNT_ZIP + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFedexAccountZip()) + "," +
			ATTRIBUTE_SERVICE_TYPE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getServiceType()) + "," +
			ATTRIBUTE_FREIGHT_TERMS + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFreightTerms()) + "," +
			ATTRIBUTE_SHIP_TO_ID + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToId()) + "," +
			ATTRIBUTE_SHIP_TO_ADDR_QUAL + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToAddrQual()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToCompany()) + "," +
			ATTRIBUTE_SHIP_TO_ADD1 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToAdd1()) + "," +
			ATTRIBUTE_SHIP_TO_ADD2 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToAdd2()) + "," +
			ATTRIBUTE_SHIP_TO_ADD3 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToAdd3()) + "," +
			ATTRIBUTE_SHIP_TO_CITY + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToCity()) + "," +
			ATTRIBUTE_SHIP_TO_STATE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToState()) + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToZip()) + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToCountry()) + "," +
			ATTRIBUTE_SHIP_TO_PHONE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipToPhone()) + "," +
			ATTRIBUTE_SHIP_FROM_ID + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromId()) + "," +
			ATTRIBUTE_SHIP_FROM_ADDR_QUAL + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAddrQual()) + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromCompany()) + "," +
			ATTRIBUTE_SHIP_FROM_ADD_1 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAdd1()) + "," +
			ATTRIBUTE_SHIP_FROM_ADD_2 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAdd2()) + "," +
			ATTRIBUTE_SHIP_FROM_ADD_3 + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromAdd3()) + "," +
			ATTRIBUTE_SHIP_FROM_CITY + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromCity()) + "," +
			ATTRIBUTE_SHIP_FROM_STATE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromState()) + "," +
			ATTRIBUTE_SHIP_FROM_COUNTRY + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromCountry()) + "," +
			ATTRIBUTE_SHIP_FROM_ZIP + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromZip()) + "," +
			ATTRIBUTE_SHIP_FROM_PHONE + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getShipFromPhone()) + "," +
			ATTRIBUTE_FREIGHT_CLASS + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getFreightClass()) + "," +
			ATTRIBUTE_BOX_QUANTITY + "=" + 
				StringHandler.nullIfZero(fedexShippingInfoBean.getBoxQuantity()) + "," +
			ATTRIBUTE_WEIGHT + "=" + 
				StringHandler.nullIfZero(fedexShippingInfoBean.getWeight()) + "," +
			ATTRIBUTE_WEIGHT_UM + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getWeightUm()) + "," +
			ATTRIBUTE_HAZMAT_FLAG + "=" + 
				SqlHandler.delimitString(fedexShippingInfoBean.getHazmatFlag()) + " " +
			"where " + ATTRIBUTE_ORDER_NO + "=" +
				fedexShippingInfoBean.getOrderNo();

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

		Collection fedexShippingInfoBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FedexShippingInfoBean fedexShippingInfoBean = new FedexShippingInfoBean();
			load(dataSetRow, fedexShippingInfoBean);
			fedexShippingInfoBeanColl.add(fedexShippingInfoBean);
		}

		return fedexShippingInfoBeanColl;
	}
}