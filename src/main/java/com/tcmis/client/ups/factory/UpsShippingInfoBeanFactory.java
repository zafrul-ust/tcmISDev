package com.tcmis.client.ups.factory;


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
import com.tcmis.client.ups.beans.UpsShippingInfoBean;


/******************************************************************************
 * CLASSNAME: UpsShippingInfoBeanFactory <br>
 * @version: 1.0, Feb 19, 2008 <br>
 *****************************************************************************/


public class UpsShippingInfoBeanFactory extends BaseBeanFactory {

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
	public String ATTRIBUTE_UPS_ACCOUNT_NUMBER = "UPS_ACCOUNT_NUMBER";
	public String ATTRIBUTE_UPS_ACCOUNT_ADDRESS_1 = "UPS_ACCOUNT_ADDRESS_1";
	public String ATTRIBUTE_UPS_ACCOUNT_ADDRESS_2 = "UPS_ACCOUNT_ADDRESS_2";
	public String ATTRIBUTE_UPS_ACCOUNT_ADDRESS_3 = "UPS_ACCOUNT_ADDRESS_3";
	public String ATTRIBUTE_UPS_ACCOUNT_CITY = "UPS_ACCOUNT_CITY";
	public String ATTRIBUTE_UPS_ACCOUNT_STATE = "UPS_ACCOUNT_STATE";
	public String ATTRIBUTE_UPS_COUNTRY_CODE = "UPS_COUNTRY_CODE";
	public String ATTRIBUTE_UPS_ACCOUNT_ZIP = "UPS_ACCOUNT_ZIP";
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

	//table name
	public String TABLE = "UPS_SHIPPING_INFO";


	//constructor
	public UpsShippingInfoBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("upsAccountNumber")) {
			return ATTRIBUTE_UPS_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("upsAccountAddress1")) {
			return ATTRIBUTE_UPS_ACCOUNT_ADDRESS_1;
		}
		else if(attributeName.equals("upsAccountAddress2")) {
			return ATTRIBUTE_UPS_ACCOUNT_ADDRESS_2;
		}
		else if(attributeName.equals("upsAccountAddress3")) {
			return ATTRIBUTE_UPS_ACCOUNT_ADDRESS_3;
		}
		else if(attributeName.equals("upsAccountCity")) {
			return ATTRIBUTE_UPS_ACCOUNT_CITY;
		}
		else if(attributeName.equals("upsAccountState")) {
			return ATTRIBUTE_UPS_ACCOUNT_STATE;
		}
		else if(attributeName.equals("upsCountryCode")) {
			return ATTRIBUTE_UPS_COUNTRY_CODE;
		}
		else if(attributeName.equals("upsAccountZip")) {
			return ATTRIBUTE_UPS_ACCOUNT_ZIP;
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
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, UpsShippingInfoBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(UpsShippingInfoBean upsShippingInfoBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("orderNo", "SearchCriterion.EQUALS",
			"" + upsShippingInfoBean.getOrderNo());

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


	public int delete(UpsShippingInfoBean upsShippingInfoBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("orderNo", "SearchCriterion.EQUALS",
			"" + upsShippingInfoBean.getOrderNo());

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
	public int insert(UpsShippingInfoBean upsShippingInfoBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(upsShippingInfoBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(UpsShippingInfoBean upsShippingInfoBean, Connection conn)
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
			ATTRIBUTE_UPS_ACCOUNT_ADDRESS_1 + "," +
			ATTRIBUTE_UPS_ACCOUNT_ADDRESS_2 + "," +
			ATTRIBUTE_UPS_ACCOUNT_ADDRESS_3 + "," +
			ATTRIBUTE_UPS_ACCOUNT_CITY + "," +
			ATTRIBUTE_UPS_ACCOUNT_STATE + "," +
			ATTRIBUTE_UPS_COUNTRY_CODE + "," +
			ATTRIBUTE_UPS_ACCOUNT_ZIP + "," +
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
			ATTRIBUTE_WEIGHT_UM + ")" +
			" values (" +
			SqlHandler.delimitString(upsShippingInfoBean.getOrderNo()) + "," +
			upsShippingInfoBean.getPackingGroupId() + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getBoxId()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getCarrierCode()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getCarrierName()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getPackageCode()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getServiceCode()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipmentChargeType()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountNumber()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountAddress1()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountAddress2()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountAddress3()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountCity()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountState()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsCountryCode()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountZip()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getServiceType()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getFreightTerms()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToId()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToAddrQual()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToCompany()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToAdd1()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToAdd2()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToAdd3()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToCity()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToState()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToZip()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToCountry()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipToPhone()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromId()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromAddrQual()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromCompany()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromAdd1()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromAdd2()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromAdd3()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromCity()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromState()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromCountry()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromZip()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getShipFromPhone()) + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getFreightClass()) + "," +
			upsShippingInfoBean.getBoxQuantity() + "," +
			upsShippingInfoBean.getWeight() + "," +
			SqlHandler.delimitString(upsShippingInfoBean.getWeightUm()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(UpsShippingInfoBean upsShippingInfoBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(upsShippingInfoBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(UpsShippingInfoBean upsShippingInfoBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ORDER_NO + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getOrderNo()) + "," +
			ATTRIBUTE_PACKING_GROUP_ID + "=" + 
				StringHandler.nullIfZero(upsShippingInfoBean.getPackingGroupId()) + "," +
			ATTRIBUTE_BOX_ID + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getBoxId()) + "," +
			ATTRIBUTE_CARRIER_CODE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getCarrierCode()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getCarrierName()) + "," +
			ATTRIBUTE_PACKAGE_CODE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getPackageCode()) + "," +
			ATTRIBUTE_SERVICE_CODE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getServiceCode()) + "," +
			ATTRIBUTE_SHIPMENT_CHARGE_TYPE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipmentChargeType()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_NUMBER + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountNumber()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_ADDRESS_1 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountAddress1()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_ADDRESS_2 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountAddress2()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_ADDRESS_3 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountAddress3()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_CITY + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountCity()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_STATE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountState()) + "," +
			ATTRIBUTE_UPS_COUNTRY_CODE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsCountryCode()) + "," +
			ATTRIBUTE_UPS_ACCOUNT_ZIP + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getUpsAccountZip()) + "," +
			ATTRIBUTE_SERVICE_TYPE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getServiceType()) + "," +
			ATTRIBUTE_FREIGHT_TERMS + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getFreightTerms()) + "," +
			ATTRIBUTE_SHIP_TO_ID + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToId()) + "," +
			ATTRIBUTE_SHIP_TO_ADDR_QUAL + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToAddrQual()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToCompany()) + "," +
			ATTRIBUTE_SHIP_TO_ADD1 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToAdd1()) + "," +
			ATTRIBUTE_SHIP_TO_ADD2 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToAdd2()) + "," +
			ATTRIBUTE_SHIP_TO_ADD3 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToAdd3()) + "," +
			ATTRIBUTE_SHIP_TO_CITY + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToCity()) + "," +
			ATTRIBUTE_SHIP_TO_STATE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToState()) + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToZip()) + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToCountry()) + "," +
			ATTRIBUTE_SHIP_TO_PHONE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipToPhone()) + "," +
			ATTRIBUTE_SHIP_FROM_ID + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromId()) + "," +
			ATTRIBUTE_SHIP_FROM_ADDR_QUAL + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromAddrQual()) + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromCompany()) + "," +
			ATTRIBUTE_SHIP_FROM_ADD_1 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromAdd1()) + "," +
			ATTRIBUTE_SHIP_FROM_ADD_2 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromAdd2()) + "," +
			ATTRIBUTE_SHIP_FROM_ADD_3 + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromAdd3()) + "," +
			ATTRIBUTE_SHIP_FROM_CITY + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromCity()) + "," +
			ATTRIBUTE_SHIP_FROM_STATE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromState()) + "," +
			ATTRIBUTE_SHIP_FROM_COUNTRY + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromCountry()) + "," +
			ATTRIBUTE_SHIP_FROM_ZIP + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromZip()) + "," +
			ATTRIBUTE_SHIP_FROM_PHONE + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getShipFromPhone()) + "," +
			ATTRIBUTE_FREIGHT_CLASS + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getFreightClass()) + "," +
			ATTRIBUTE_BOX_QUANTITY + "=" + 
				StringHandler.nullIfZero(upsShippingInfoBean.getBoxQuantity()) + "," +
			ATTRIBUTE_WEIGHT + "=" + 
				StringHandler.nullIfZero(upsShippingInfoBean.getWeight()) + "," +
			ATTRIBUTE_WEIGHT_UM + "=" + 
				SqlHandler.delimitString(upsShippingInfoBean.getWeightUm()) + " " +
			"where " + ATTRIBUTE_ORDER_NO + "=" +
				upsShippingInfoBean.getOrderNo();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection<UpsShippingInfoBean> select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection<UpsShippingInfoBean> c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection<UpsShippingInfoBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection<UpsShippingInfoBean> upsShippingInfoBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UpsShippingInfoBean upsShippingInfoBean = new UpsShippingInfoBean();
			load(dataSetRow, upsShippingInfoBean);
			upsShippingInfoBeanColl.add(upsShippingInfoBean);
		}

		return upsShippingInfoBeanColl;
	}
}