package com.tcmis.internal.print.factory;


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
import com.tcmis.internal.print.beans.TcnPalletViewBean;


/******************************************************************************
 * CLASSNAME: TcnPalletViewBeanFactory <br>
 * @version: 1.0, Mar 4, 2008 <br>
 *****************************************************************************/


public class TcnPalletViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PALLET_ID = "PALLET_ID";
	public String ATTRIBUTE_PALLET_NUMBER = "PALLET_NUMBER";
	public String ATTRIBUTE_NUMBER_OF_PALLETS = "NUMBER_OF_PALLETS";
	public String ATTRIBUTE_SHIP_DATE = "SHIP_DATE";
	public String ATTRIBUTE_GROSS_WEIGHT = "GROSS_WEIGHT";
	public String ATTRIBUTE_CUBIC_FEET = "CUBIC_FEET";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_NSN = "NSN";
	public String ATTRIBUTE_UNIT_OF_ISSUE = "UNIT_OF_ISSUE";
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

	//table name
	public String TABLE = "TCN_PALLET_VIEW";


	//constructor
	public TcnPalletViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("nsn")) {
			return ATTRIBUTE_NSN;
		}
		else if(attributeName.equals("unitOfIssue")) {
			return ATTRIBUTE_UNIT_OF_ISSUE;
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
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, TcnPalletViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(TcnPalletViewBean tcnPalletViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + tcnPalletViewBean.getInventoryGroup());

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


	public int delete(TcnPalletViewBean tcnPalletViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + tcnPalletViewBean.getInventoryGroup());

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
	public int insert(TcnPalletViewBean tcnPalletViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(tcnPalletViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(TcnPalletViewBean tcnPalletViewBean, Connection conn)
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
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_NSN + "," +
			ATTRIBUTE_UNIT_OF_ISSUE + "," +
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
			ATTRIBUTE_SHIP_VIA_LINE4 + "," +
			ATTRIBUTE_PALLET_RFID + "," +
			ATTRIBUTE_CARRIER_CODE + "," +
			ATTRIBUTE_CONSOLIDATION_NUMBER + ")" +
			" values (" +
			SqlHandler.delimitString(tcnPalletViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getPalletId()) + "," +
			tcnPalletViewBean.getPalletNumber() + "," +
			tcnPalletViewBean.getNumberOfPallets() + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipDate()) + "," +
			tcnPalletViewBean.getGrossWeight() + "," +
			tcnPalletViewBean.getCubicFeet() + "," +
			tcnPalletViewBean.getQuantity() + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getNsn()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getUnitOfIssue()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getMilstripCode()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getTcn()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getTransportationPriority()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getProjectCode()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getFmsCaseNum()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getPortOfEmbarkation()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getPortOfDebarkation()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getTac()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getRic()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getRdd()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipFromDodaac()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipFromCompanyId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getIgdCompanyId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getIgdLocationId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine1()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine2()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine3()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine4()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipToLine1()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipToLine2()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipToLine3()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipToLine4()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipViaDodaac()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipViaCompanyId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine1()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine2()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine3()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine4()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getPalletRfid()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getCarrierCode()) + "," +
			SqlHandler.delimitString(tcnPalletViewBean.getConsolidationNumber()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(TcnPalletViewBean tcnPalletViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(tcnPalletViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(TcnPalletViewBean tcnPalletViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_PALLET_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getPalletId()) + "," +
			ATTRIBUTE_PALLET_NUMBER + "=" + 
				StringHandler.nullIfZero(tcnPalletViewBean.getPalletNumber()) + "," +
			ATTRIBUTE_NUMBER_OF_PALLETS + "=" + 
				StringHandler.nullIfZero(tcnPalletViewBean.getNumberOfPallets()) + "," +
			ATTRIBUTE_SHIP_DATE + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipDate()) + "," +
			ATTRIBUTE_GROSS_WEIGHT + "=" + 
				StringHandler.nullIfZero(tcnPalletViewBean.getGrossWeight()) + "," +
			ATTRIBUTE_CUBIC_FEET + "=" + 
				StringHandler.nullIfZero(tcnPalletViewBean.getCubicFeet()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(tcnPalletViewBean.getQuantity()) + "," +
			ATTRIBUTE_NSN + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getNsn()) + "," +
			ATTRIBUTE_UNIT_OF_ISSUE + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getUnitOfIssue()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getMilstripCode()) + "," +
			ATTRIBUTE_TCN + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getTcn()) + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getTransportationPriority()) + "," +
			ATTRIBUTE_PROJECT_CODE + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getProjectCode()) + "," +
			ATTRIBUTE_FMS_CASE_NUM + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getFmsCaseNum()) + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getPortOfEmbarkation()) + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getPortOfDebarkation()) + "," +
			ATTRIBUTE_TAC + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getTac()) + "," +
			ATTRIBUTE_RIC + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getRic()) + "," +
			ATTRIBUTE_RDD + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getRdd()) + "," +
			ATTRIBUTE_SHIP_FROM_DODAAC + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipFromDodaac()) + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipFromCompanyId()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_IGD_COMPANY_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getIgdCompanyId()) + "," +
			ATTRIBUTE_IGD_LOCATION_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getIgdLocationId()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE1 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine1()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE2 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine2()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE3 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine3()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE4 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipFromLine4()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_TO_LINE1 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipToLine1()) + "," +
			ATTRIBUTE_SHIP_TO_LINE2 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipToLine2()) + "," +
			ATTRIBUTE_SHIP_TO_LINE3 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipToLine3()) + "," +
			ATTRIBUTE_SHIP_TO_LINE4 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipToLine4()) + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipViaDodaac()) + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipViaCompanyId()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE1 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine1()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE2 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine2()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE3 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine3()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE4 + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getShipViaLine4()) + "," +
			ATTRIBUTE_PALLET_RFID + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getPalletRfid()) + "," +
			ATTRIBUTE_CARRIER_CODE + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getCarrierCode()) + "," +
			ATTRIBUTE_CONSOLIDATION_NUMBER + "=" + 
				SqlHandler.delimitString(tcnPalletViewBean.getConsolidationNumber()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				tcnPalletViewBean.getInventoryGroup();

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

		Collection tcnPalletViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			TcnPalletViewBean tcnPalletViewBean = new TcnPalletViewBean();
			load(dataSetRow, tcnPalletViewBean);
			tcnPalletViewBeanColl.add(tcnPalletViewBean);
		}

		return tcnPalletViewBeanColl;
	}
}