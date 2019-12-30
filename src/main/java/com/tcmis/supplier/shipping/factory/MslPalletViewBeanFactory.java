package com.tcmis.supplier.shipping.factory;


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
import com.tcmis.supplier.shipping.beans.MslPalletViewBean;


/******************************************************************************
 * CLASSNAME: MslPalletViewBeanFactory <br>
 * @version: 1.0, Dec 1, 2007 <br>
 *****************************************************************************/


public class MslPalletViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PALLET_ID = "PALLET_ID";
	public String ATTRIBUTE_PALLET_NUMBER = "PALLET_NUMBER";
	public String ATTRIBUTE_NUMBER_OF_PALLETS = "NUMBER_OF_PALLETS";
	public String ATTRIBUTE_SHIP_DATE = "SHIP_DATE";
	public String ATTRIBUTE_GROSS_WEIGHT = "GROSS_WEIGHT";
	public String ATTRIBUTE_CUBIC_FEET = "CUBIC_FEET";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_TCN = "TCN";
	public String ATTRIBUTE_TRANSPORTATION_PRIORITY = "TRANSPORTATION_PRIORITY";
	public String ATTRIBUTE_PROJECT_CODE = "PROJECT_CODE";
	public String ATTRIBUTE_FMS_CASE_NUM = "FMS_CASE_NUM";
	public String ATTRIBUTE_PORT_OF_EMBARKATION = "PORT_OF_EMBARKATION";
	public String ATTRIBUTE_PORT_OF_DEBARKATION = "PORT_OF_DEBARKATION";
	public String ATTRIBUTE_TAC = "TAC";
	public String ATTRIBUTE_RIC = "RIC";
	public String ATTRIBUTE_RDD = "RDD";
	public String ATTRIBUTE_SHIP_FROM_DODAAC = "SHIP_FROM_DODAAC";
	public String ATTRIBUTE_SHIP_FROM_COMPANY_ID = "SHIP_FROM_COMPANY_ID";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_IGD_COMPANY_ID = "IGD_COMPANY_ID";
	public String ATTRIBUTE_IGD_LOCATION_ID = "IGD_LOCATION_ID";
	public String ATTRIBUTE_SHIP_FROM_LINE1 = "SHIP_FROM_LINE1";
	public String ATTRIBUTE_SHIP_FROM_LINE2 = "SHIP_FROM_LINE2";
	public String ATTRIBUTE_SHIP_FROM_LINE3 = "SHIP_FROM_LINE3";
	public String ATTRIBUTE_SHIP_FROM_LINE4 = "SHIP_FROM_LINE4";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_TO_LINE1 = "SHIP_TO_LINE1";
	public String ATTRIBUTE_SHIP_TO_LINE2 = "SHIP_TO_LINE2";
	public String ATTRIBUTE_SHIP_TO_LINE3 = "SHIP_TO_LINE3";
	public String ATTRIBUTE_SHIP_TO_LINE4 = "SHIP_TO_LINE4";
	public String ATTRIBUTE_SHIP_VIA_DODAAC = "SHIP_VIA_DODAAC";
	public String ATTRIBUTE_SHIP_VIA_COMPANY_ID = "SHIP_VIA_COMPANY_ID";
	public String ATTRIBUTE_SHIP_VIA_LOCATION_ID = "SHIP_VIA_LOCATION_ID";
	public String ATTRIBUTE_SHIP_VIA_LINE1 = "SHIP_VIA_LINE1";
	public String ATTRIBUTE_SHIP_VIA_LINE2 = "SHIP_VIA_LINE2";
	public String ATTRIBUTE_SHIP_VIA_LINE3 = "SHIP_VIA_LINE3";
	public String ATTRIBUTE_SHIP_VIA_LINE4 = "SHIP_VIA_LINE4";
	public String ATTRIBUTE_PALLET_RFID = "PALLET_RFID";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_CONSOLIDATION_NUMBER = "CONSOLIDATION_NUMBER";
	public String ATTRIBUTE_FLASHPOINT_INFO = "FLASHPOINT_INFO";
	public String ATTRIBUTE_PALLET_PROJECT_CODE = "PALLET_PROJECT_CODE";
	public String ATTRIBUTE_CARRIER_PRO_NUMBER = "CARRIER_PRO_NUMBER";

  //table name
	public String TABLE = "MSL_PALLET_VIEW";


	//constructor
	public MslPalletViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("palletId")) {
			return ATTRIBUTE_PALLET_ID;
		}
		else if(attributeName.equals("palletNumber")) {
			return ATTRIBUTE_PALLET_NUMBER;
		}
		else if(attributeName.equals("numberOfPallets")) {
			return ATTRIBUTE_NUMBER_OF_PALLETS;
		}
		else if(attributeName.equals("shipDate")) {
			return ATTRIBUTE_SHIP_DATE;
		}
		else if(attributeName.equals("grossWeight")) {
			return ATTRIBUTE_GROSS_WEIGHT;
		}
		else if(attributeName.equals("cubicFeet")) {
			return ATTRIBUTE_CUBIC_FEET;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("tcn")) {
			return ATTRIBUTE_TCN;
		}
		else if(attributeName.equals("transportationPriority")) {
			return ATTRIBUTE_TRANSPORTATION_PRIORITY;
		}
		else if(attributeName.equals("projectCode")) {
			return ATTRIBUTE_PROJECT_CODE;
		}
		else if(attributeName.equals("fmsCaseNum")) {
			return ATTRIBUTE_FMS_CASE_NUM;
		}
		else if(attributeName.equals("portOfEmbarkation")) {
			return ATTRIBUTE_PORT_OF_EMBARKATION;
		}
		else if(attributeName.equals("portOfDebarkation")) {
			return ATTRIBUTE_PORT_OF_DEBARKATION;
		}
		else if(attributeName.equals("tac")) {
			return ATTRIBUTE_TAC;
		}
		else if(attributeName.equals("ric")) {
			return ATTRIBUTE_RIC;
		}
		else if(attributeName.equals("rdd")) {
			return ATTRIBUTE_RDD;
		}
		else if(attributeName.equals("shipFromDodaac")) {
			return ATTRIBUTE_SHIP_FROM_DODAAC;
		}
		else if(attributeName.equals("shipFromCompanyId")) {
			return ATTRIBUTE_SHIP_FROM_COMPANY_ID;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("igdCompanyId")) {
			return ATTRIBUTE_IGD_COMPANY_ID;
		}
		else if(attributeName.equals("igdLocationId")) {
			return ATTRIBUTE_IGD_LOCATION_ID;
		}
		else if(attributeName.equals("shipFromLine1")) {
			return ATTRIBUTE_SHIP_FROM_LINE1;
		}
		else if(attributeName.equals("shipFromLine2")) {
			return ATTRIBUTE_SHIP_FROM_LINE2;
		}
		else if(attributeName.equals("shipFromLine3")) {
			return ATTRIBUTE_SHIP_FROM_LINE3;
		}
		else if(attributeName.equals("shipFromLine4")) {
			return ATTRIBUTE_SHIP_FROM_LINE4;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipToLine1")) {
			return ATTRIBUTE_SHIP_TO_LINE1;
		}
		else if(attributeName.equals("shipToLine2")) {
			return ATTRIBUTE_SHIP_TO_LINE2;
		}
		else if(attributeName.equals("shipToLine3")) {
			return ATTRIBUTE_SHIP_TO_LINE3;
		}
		else if(attributeName.equals("shipToLine4")) {
			return ATTRIBUTE_SHIP_TO_LINE4;
		}
		else if(attributeName.equals("shipViaDodaac")) {
			return ATTRIBUTE_SHIP_VIA_DODAAC;
		}
		else if(attributeName.equals("shipViaCompanyId")) {
			return ATTRIBUTE_SHIP_VIA_COMPANY_ID;
		}
		else if(attributeName.equals("shipViaLocationId")) {
			return ATTRIBUTE_SHIP_VIA_LOCATION_ID;
		}
		else if(attributeName.equals("shipViaLine1")) {
			return ATTRIBUTE_SHIP_VIA_LINE1;
		}
		else if(attributeName.equals("shipViaLine2")) {
			return ATTRIBUTE_SHIP_VIA_LINE2;
		}
		else if(attributeName.equals("shipViaLine3")) {
			return ATTRIBUTE_SHIP_VIA_LINE3;
		}
		else if(attributeName.equals("shipViaLine4")) {
			return ATTRIBUTE_SHIP_VIA_LINE4;
		}
		else if(attributeName.equals("palletRfid")) {
			return ATTRIBUTE_PALLET_RFID;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("consolidationNumber")) {
			return ATTRIBUTE_CONSOLIDATION_NUMBER;
		}
		else if(attributeName.equals("flashpointInfo")) {
			return ATTRIBUTE_FLASHPOINT_INFO;
		}
		else if(attributeName.equals("palletProjectCode")) {
			return ATTRIBUTE_PALLET_PROJECT_CODE;
		}
		else if(attributeName.equals("carrierProNumber")) {
			return ATTRIBUTE_CARRIER_PRO_NUMBER;
		}    
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MslPalletViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(MslPalletViewBean mslPalletViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + mslPalletViewBean.getInventoryGroup());

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


	public int delete(MslPalletViewBean mslPalletViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + mslPalletViewBean.getInventoryGroup());

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
	public int insert(MslPalletViewBean mslPalletViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(mslPalletViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(MslPalletViewBean mslPalletViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_PALLET_ID + "," +
			ATTRIBUTE_PALLET_NUMBER + "," +
			ATTRIBUTE_NUMBER_OF_PALLETS + "," +
			ATTRIBUTE_SHIP_DATE + "," +
			ATTRIBUTE_GROSS_WEIGHT + "," +
			ATTRIBUTE_CUBIC_FEET + "," +
			ATTRIBUTE_MILSTRIP_CODE + "," +
			ATTRIBUTE_TCN + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "," +
			ATTRIBUTE_PROJECT_CODE + "," +
			ATTRIBUTE_FMS_CASE_NUM + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "," +
			ATTRIBUTE_TAC + "," +
			ATTRIBUTE_RIC + "," +
			ATTRIBUTE_RDD + "," +
			ATTRIBUTE_SHIP_FROM_DODAAC + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_IGD_COMPANY_ID + "," +
			ATTRIBUTE_IGD_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_FROM_LINE1 + "," +
			ATTRIBUTE_SHIP_FROM_LINE2 + "," +
			ATTRIBUTE_SHIP_FROM_LINE3 + "," +
			ATTRIBUTE_SHIP_FROM_LINE4 + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_TO_LINE1 + "," +
			ATTRIBUTE_SHIP_TO_LINE2 + "," +
			ATTRIBUTE_SHIP_TO_LINE3 + "," +
			ATTRIBUTE_SHIP_TO_LINE4 + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_VIA_LINE1 + "," +
			ATTRIBUTE_SHIP_VIA_LINE2 + "," +
			ATTRIBUTE_SHIP_VIA_LINE3 + "," +
			ATTRIBUTE_SHIP_VIA_LINE4 + ")" +
			" values (" +
			SqlHandler.delimitString(mslPalletViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getPalletId()) + "," +
			mslPalletViewBean.getPalletNumber() + "," +
			mslPalletViewBean.getNumberOfPallets() + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipDate()) + "," +
			mslPalletViewBean.getGrossWeight() + "," +
			mslPalletViewBean.getCubicFeet() + "," +
			SqlHandler.delimitString(mslPalletViewBean.getMilstripCode()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getTcn()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getTransportationPriority()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getProjectCode()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getFmsCaseNum()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getPortOfEmbarkation()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getPortOfDebarkation()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getTac()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getRic()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getRdd()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipFromDodaac()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipFromCompanyId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getIgdCompanyId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getIgdLocationId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipFromLine1()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipFromLine2()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipFromLine3()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipFromLine4()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipToLine1()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipToLine2()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipToLine3()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipToLine4()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipViaDodaac()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipViaCompanyId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipViaLine1()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipViaLine2()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipViaLine3()) + "," +
			SqlHandler.delimitString(mslPalletViewBean.getShipViaLine4()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(MslPalletViewBean mslPalletViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(mslPalletViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(MslPalletViewBean mslPalletViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_PALLET_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getPalletId()) + "," +
			ATTRIBUTE_PALLET_NUMBER + "=" + 
				StringHandler.nullIfZero(mslPalletViewBean.getPalletNumber()) + "," +
			ATTRIBUTE_NUMBER_OF_PALLETS + "=" + 
				StringHandler.nullIfZero(mslPalletViewBean.getNumberOfPallets()) + "," +
			ATTRIBUTE_SHIP_DATE + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipDate()) + "," +
			ATTRIBUTE_GROSS_WEIGHT + "=" + 
				StringHandler.nullIfZero(mslPalletViewBean.getGrossWeight()) + "," +
			ATTRIBUTE_CUBIC_FEET + "=" + 
				StringHandler.nullIfZero(mslPalletViewBean.getCubicFeet()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getMilstripCode()) + "," +
			ATTRIBUTE_TCN + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getTcn()) + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getTransportationPriority()) + "," +
			ATTRIBUTE_PROJECT_CODE + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getProjectCode()) + "," +
			ATTRIBUTE_FMS_CASE_NUM + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getFmsCaseNum()) + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getPortOfEmbarkation()) + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getPortOfDebarkation()) + "," +
			ATTRIBUTE_TAC + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getTac()) + "," +
			ATTRIBUTE_RIC + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getRic()) + "," +
			ATTRIBUTE_RDD + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getRdd()) + "," +
			ATTRIBUTE_SHIP_FROM_DODAAC + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipFromDodaac()) + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipFromCompanyId()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_IGD_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getIgdCompanyId()) + "," +
			ATTRIBUTE_IGD_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getIgdLocationId()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE1 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipFromLine1()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE2 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipFromLine2()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE3 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipFromLine3()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE4 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipFromLine4()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_TO_LINE1 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipToLine1()) + "," +
			ATTRIBUTE_SHIP_TO_LINE2 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipToLine2()) + "," +
			ATTRIBUTE_SHIP_TO_LINE3 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipToLine3()) + "," +
			ATTRIBUTE_SHIP_TO_LINE4 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipToLine4()) + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipViaDodaac()) + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipViaCompanyId()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE1 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipViaLine1()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE2 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipViaLine2()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE3 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipViaLine3()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE4 + "=" + 
				SqlHandler.delimitString(mslPalletViewBean.getShipViaLine4()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				mslPalletViewBean.getInventoryGroup();

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

		Collection mslPalletViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MslPalletViewBean mslPalletViewBean = new MslPalletViewBean();
			load(dataSetRow, mslPalletViewBean);
			mslPalletViewBeanColl.add(mslPalletViewBean);
		}

		return mslPalletViewBeanColl;
	}
}