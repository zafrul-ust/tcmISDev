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
import com.tcmis.supplier.shipping.beans.MslBoxViewBean;


/******************************************************************************
 * CLASSNAME: MslBoxViewBeanFactory <br>
 * @version: 1.0, Dec 1, 2007 <br>
 *****************************************************************************/


public class MslBoxViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_BOX_ID = "BOX_ID";
	public String ATTRIBUTE_PALLET_ID = "PALLET_ID";
	public String ATTRIBUTE_BOX_NUMBER = "BOX_NUMBER";
	public String ATTRIBUTE_NUMBER_OF_BOXES = "NUMBER_OF_BOXES";
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
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
  public String ATTRIBUTE_BOX_RFID = "BOX_RFID";
	public String ATTRIBUTE_FLASH_POINT = "FLASH_POINT";

  //table name
	public String TABLE = "MSL_BOX_VIEW";


	//constructor
	public MslBoxViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("boxId")) {
			return ATTRIBUTE_BOX_ID;
		}
		else if(attributeName.equals("palletId")) {
			return ATTRIBUTE_PALLET_ID;
		}
		else if(attributeName.equals("boxNumber")) {
			return ATTRIBUTE_BOX_NUMBER;
		}
		else if(attributeName.equals("numberOfBoxes")) {
			return ATTRIBUTE_NUMBER_OF_BOXES;
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
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("boxRfid")) {
			return ATTRIBUTE_BOX_RFID;
		}
		else if(attributeName.equals("flashpoint")) {
			return ATTRIBUTE_FLASH_POINT;
		}
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MslBoxViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(MslBoxViewBean mslBoxViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + mslBoxViewBean.getInventoryGroup());

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


	public int delete(MslBoxViewBean mslBoxViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + mslBoxViewBean.getInventoryGroup());

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
	public int insert(MslBoxViewBean mslBoxViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(mslBoxViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(MslBoxViewBean mslBoxViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UNIT_OF_SALE + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_BOX_ID + "," +
			ATTRIBUTE_PALLET_ID + "," +
			ATTRIBUTE_BOX_NUMBER + "," +
			ATTRIBUTE_NUMBER_OF_BOXES + "," +
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
			SqlHandler.delimitString(mslBoxViewBean.getInventoryGroup()) + "," +
			mslBoxViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(mslBoxViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getCatPartNo()) + "," +
			mslBoxViewBean.getQuantity() + "," +
			SqlHandler.delimitString(mslBoxViewBean.getUnitOfSale()) + "," +
			mslBoxViewBean.getUnitPrice() + "," +
			SqlHandler.delimitString(mslBoxViewBean.getBoxId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getPalletId()) + "," +
			mslBoxViewBean.getBoxNumber() + "," +
			mslBoxViewBean.getNumberOfBoxes() + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipDate()) + "," +
			mslBoxViewBean.getGrossWeight() + "," +
			mslBoxViewBean.getCubicFeet() + "," +
			SqlHandler.delimitString(mslBoxViewBean.getMilstripCode()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getTcn()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getTransportationPriority()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getProjectCode()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getFmsCaseNum()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getPortOfEmbarkation()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getPortOfDebarkation()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getTac()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getRic()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getRdd()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipFromDodaac()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipFromCompanyId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getIgdCompanyId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getIgdLocationId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipFromLine1()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipFromLine2()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipFromLine3()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipFromLine4()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipToLine1()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipToLine2()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipToLine3()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipToLine4()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipViaDodaac()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipViaCompanyId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipViaLine1()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipViaLine2()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipViaLine3()) + "," +
			SqlHandler.delimitString(mslBoxViewBean.getShipViaLine4()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(MslBoxViewBean mslBoxViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(mslBoxViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(MslBoxViewBean mslBoxViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(mslBoxViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getLineItem()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(mslBoxViewBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_OF_SALE + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getUnitOfSale()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(mslBoxViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_BOX_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getBoxId()) + "," +
			ATTRIBUTE_PALLET_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getPalletId()) + "," +
			ATTRIBUTE_BOX_NUMBER + "=" + 
				StringHandler.nullIfZero(mslBoxViewBean.getBoxNumber()) + "," +
			ATTRIBUTE_NUMBER_OF_BOXES + "=" + 
				StringHandler.nullIfZero(mslBoxViewBean.getNumberOfBoxes()) + "," +
			ATTRIBUTE_SHIP_DATE + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipDate()) + "," +
			ATTRIBUTE_GROSS_WEIGHT + "=" + 
				StringHandler.nullIfZero(mslBoxViewBean.getGrossWeight()) + "," +
			ATTRIBUTE_CUBIC_FEET + "=" + 
				StringHandler.nullIfZero(mslBoxViewBean.getCubicFeet()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getMilstripCode()) + "," +
			ATTRIBUTE_TCN + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getTcn()) + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getTransportationPriority()) + "," +
			ATTRIBUTE_PROJECT_CODE + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getProjectCode()) + "," +
			ATTRIBUTE_FMS_CASE_NUM + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getFmsCaseNum()) + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getPortOfEmbarkation()) + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getPortOfDebarkation()) + "," +
			ATTRIBUTE_TAC + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getTac()) + "," +
			ATTRIBUTE_RIC + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getRic()) + "," +
			ATTRIBUTE_RDD + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getRdd()) + "," +
			ATTRIBUTE_SHIP_FROM_DODAAC + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipFromDodaac()) + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipFromCompanyId()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_IGD_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getIgdCompanyId()) + "," +
			ATTRIBUTE_IGD_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getIgdLocationId()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE1 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipFromLine1()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE2 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipFromLine2()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE3 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipFromLine3()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE4 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipFromLine4()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_TO_LINE1 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipToLine1()) + "," +
			ATTRIBUTE_SHIP_TO_LINE2 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipToLine2()) + "," +
			ATTRIBUTE_SHIP_TO_LINE3 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipToLine3()) + "," +
			ATTRIBUTE_SHIP_TO_LINE4 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipToLine4()) + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipViaDodaac()) + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipViaCompanyId()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE1 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipViaLine1()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE2 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipViaLine2()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE3 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipViaLine3()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE4 + "=" + 
				SqlHandler.delimitString(mslBoxViewBean.getShipViaLine4()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				mslBoxViewBean.getInventoryGroup();

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

		Collection mslBoxViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MslBoxViewBean mslBoxViewBean = new MslBoxViewBean();
			load(dataSetRow, mslBoxViewBean);
			mslBoxViewBeanColl.add(mslBoxViewBean);
		}

		return mslBoxViewBeanColl;
	}
}