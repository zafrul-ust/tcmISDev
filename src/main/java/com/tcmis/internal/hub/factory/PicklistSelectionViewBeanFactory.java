package com.tcmis.internal.hub.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.Globals;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.PicklistSelectionViewBean;


/******************************************************************************
 * CLASSNAME: PicklistSelectionViewBeanFactory <br>
 * @version: 1.0, Jan 12, 2007 <br>
 *****************************************************************************/

public class PicklistSelectionViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
	public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
	public String ATTRIBUTE_RELEASE_DATE = "RELEASE_DATE";
	public String ATTRIBUTE_NEED_DATE_PREFIX = "NEED_DATE_PREFIX";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PICK_QTY = "PICK_QTY";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_SCRAP = "SCRAP";
	public String ATTRIBUTE_MR_NOTES = "MR_NOTES";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_HAZMAT_ID_MISSING = "HAZMAT_ID_MISSING";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_PICKUP_TIME = "PICKUP_TIME";
	public String ATTRIBUTE_STOP_NUMBER = "STOP_NUMBER";
	public String ATTRIBUTE_TRAILER_NUMBER = "TRAILER_NUMBER";
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_TRANSPORTATION_MODE = "TRANSPORTATION_MODE";
	public String ATTRIBUTE_SHIP_TO_LOCATION_DESC = "SHIP_TO_LOCATION_DESC";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_STATE_ABBREV = "SHIP_TO_STATE_ABBREV";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_CONSOLIDATION_NUMBER = "CONSOLIDATION_NUMBER";
	public String ATTRIBUTE_OCONUS_FLAG = "OCONUS_FLAG";
	public String ATTRIBUTE_DOT = "DOT";
	public String ATTRIBUTE_UNIT_GROSS_WEIGHT_LB = "UNIT_GROSS_WEIGHT_LB";
	public String ATTRIBUTE_SEAVAN = "SEAVAN";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_MR_COUNT = "MR_COUNT";
	public String ATTRIBUTE_TRANSPORTATION_PRIORITY = "TRANSPORTATION_PRIORITY";
	public String ATTRIBUTE_HAZARDOUS = "HAZARDOUS";
	public String ATTRIBUTE_SPLIT_TCN = "SPLIT_TCN";
	public String ATTRIBUTE_ACK_SENT = "ACK_SENT";
	public String ATTRIBUTE_AIR_GROUND_INDICATOR = "AIR_GROUND_INDICATOR";
	public String ATTRIBUTE_CUSTOMER_SERVICE_REP_ID = "CUSTOMER_SERVICE_REP_ID";
	public String ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME = "CUSTOMER_SERVICE_REP_NAME";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_MATERIAL_REQUEST_ORIGIN = "MATERIAL_REQUEST_ORIGIN";
	public String ATTRIBUTE_ADDRESS_LINE_1_DISPLAY = "ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_2_DISPLAY = "ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_3_DISPLAY = "ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_4_DISPLAY = "ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_5_DISPLAY = "ADDRESS_LINE_5_DISPLAY";
	public String ATTRIBUTE_CMS = "CMS";
	public String ATTRIBUTE_DISTRIBUTION = "DISTRIBUTION";
	public String ATTRIBUTE_SHIPPING_REFERENCE = "SHIPPING_REFERENCE";
	public String ATTRIBUTE_ADR = "ADR";
	public String ATTRIBUTE_ADR_MISSING = "ADR_MISSING";
	public String ATTRIBUTE_DEST_INVENTORY_GROUP_NAME = "DEST_INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
	public String ATTRIBUTE_ABC_CLASSIFICATION = "ABC_CLASSIFICATION";
	/*	public String ATTRIBUTE_CUSTOMER_NOTE = "CUSTOMER_NOTE";
	public String ATTRIBUTE_SHIPTO_NOTE = "SHIPTO_NOTE";
	public String ATTRIBUTE_PR_INTERNAL_NOTE = "PR_INTERNAL_NOTE";
	public String ATTRIBUTE_LINE_INTERNAL_NOTE = "LINE_INTERNAL_NOTE";
	public String ATTRIBUTE_LINE_PURCHASING_NOTE = "LINE_PURCHASING_NOTE";  */
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";

	//table name
	public String TABLE = "PICKLIST_SELECTION_VIEW";


	//constructor
	public PicklistSelectionViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("stockingMethod")) {
			return ATTRIBUTE_STOCKING_METHOD;
		}
		else if(attributeName.equals("deliveryType")) {
			return ATTRIBUTE_DELIVERY_TYPE;
		}
		else if(attributeName.equals("releaseDate")) {
			return ATTRIBUTE_RELEASE_DATE;
		}
		else if(attributeName.equals("needDatePrefix")) {
			return ATTRIBUTE_NEED_DATE_PREFIX;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("pickQty")) {
			return ATTRIBUTE_PICK_QTY;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("scrap")) {
			return ATTRIBUTE_SCRAP;
		}
		else if(attributeName.equals("mrNotes")) {
			return ATTRIBUTE_MR_NOTES;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("hazmatIdMissing")) {
			return ATTRIBUTE_HAZMAT_ID_MISSING;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("pickupTime")) {
			return ATTRIBUTE_PICKUP_TIME;
		}
		else if(attributeName.equals("stopNumber")) {
			return ATTRIBUTE_STOP_NUMBER;
		}
		else if(attributeName.equals("trailerNumber")) {
			return ATTRIBUTE_TRAILER_NUMBER;
		}
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("transportationMode")) {
			return ATTRIBUTE_TRANSPORTATION_MODE;
		}
		else if(attributeName.equals("shipToLocationDesc")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_DESC;
		}
		else if(attributeName.equals("shipToCity")) {
			return ATTRIBUTE_SHIP_TO_CITY;
		}
		else if(attributeName.equals("shipToStateAbbrev")) {
			return ATTRIBUTE_SHIP_TO_STATE_ABBREV;
		}
		else if(attributeName.equals("shipToZip")) {
			return ATTRIBUTE_SHIP_TO_ZIP;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("shippedAsSingle")) {
			return ATTRIBUTE_SHIPPED_AS_SINGLE;
		}
		else if(attributeName.equals("consolidationNumber")) {
			return ATTRIBUTE_CONSOLIDATION_NUMBER;
		}
		else if(attributeName.equals("oconusFlag")) {
			return ATTRIBUTE_OCONUS_FLAG;
		}
		else if(attributeName.equals("dot")) {
			return ATTRIBUTE_DOT;
		}
		else if(attributeName.equals("unitGrossWeightLb")) {
			return ATTRIBUTE_UNIT_GROSS_WEIGHT_LB;
		}
		else if(attributeName.equals("seavan")) {
			return ATTRIBUTE_SEAVAN;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("mrCount")) {
			return ATTRIBUTE_MR_COUNT;
		}
		else if(attributeName.equals("transportationPriority")) {
			return ATTRIBUTE_TRANSPORTATION_PRIORITY;
		}
		else if(attributeName.equals("hazardous")) {
			return ATTRIBUTE_HAZARDOUS;
		}
		else if(attributeName.equals("splitTcn")) {
			return ATTRIBUTE_SPLIT_TCN;
		}
		else if(attributeName.equals("ackSent")) {
			return ATTRIBUTE_ACK_SENT;
		}
		else if(attributeName.equals("airGroundIndicator")) {
			return ATTRIBUTE_AIR_GROUND_INDICATOR;
		}
		else if(attributeName.equals("customerServiceRepId")) {
			return ATTRIBUTE_CUSTOMER_SERVICE_REP_ID;
		}
		else if(attributeName.equals("customerServiceRepName")) {
			return ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("facilityName")) {
			return ATTRIBUTE_FACILITY_NAME;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("materialRequestOrigin")) {
			return ATTRIBUTE_MATERIAL_REQUEST_ORIGIN;
		}
		else if(attributeName.equals("addressLine1Display")) {
			return ATTRIBUTE_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("addressLine2Display")) {
			return ATTRIBUTE_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("addressLine3Display")) {
			return ATTRIBUTE_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("addressLine4Display")) {
			return ATTRIBUTE_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("addressLine5Display")) {
			return ATTRIBUTE_ADDRESS_LINE_5_DISPLAY;
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
		else if (attributeName.equals("adr")) {
			return ATTRIBUTE_ADR;
		}
		else if (attributeName.equals("adrMissing")) {
			return ATTRIBUTE_ADR_MISSING;
		}
		else if (attributeName.equals("destInventoryGroupName")) {
			return ATTRIBUTE_DEST_INVENTORY_GROUP_NAME;
		}
		else if (attributeName.equals("deliveryPointDesc")) {
			return ATTRIBUTE_DELIVERY_POINT_DESC;
		}
		else if (attributeName.equals("abcClassification")) {
			return ATTRIBUTE_ABC_CLASSIFICATION;
		}
		/*		else if(attributeName.equals("customerNote")) {
			return ATTRIBUTE_CUSTOMER_NOTE;
		}
		else if(attributeName.equals("shipToNote")) {
			return ATTRIBUTE_SHIPTO_NOTE;
		}
		else if(attributeName.equals("prInternalNote")) {
			return ATTRIBUTE_PR_INTERNAL_NOTE;
		}
		else if(attributeName.equals("lineInternalNote")) {
			return ATTRIBUTE_LINE_INTERNAL_NOTE;
		}
		else if(attributeName.equals("linePurchasingNote")) {
			return ATTRIBUTE_LINE_PURCHASING_NOTE;
		}  */
		else if (attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if (attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, PicklistSelectionViewBean.class);
	}

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {
		Collection picklistSelectionViewBeanColl = new Vector();
		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			PicklistSelectionViewBean picklistSelectionViewBean = new PicklistSelectionViewBean();
			load(dataSetRow, picklistSelectionViewBean);
			picklistSelectionViewBeanColl.add(picklistSelectionViewBean);
		}
		return picklistSelectionViewBeanColl;
	}

	public Collection selectPicking(SearchCriteria criteria, SortCriteria sortCriteria,String additionalWhereClause) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectPicking(criteria, sortCriteria,additionalWhereClause, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectPicking(SearchCriteria criteria, SortCriteria sortCriteria,String additionalWhereClause, Connection conn) throws BaseException {
		Collection picklistSelectionViewBeanColl = new Vector();
		String query = "select "+ATTRIBUTE_UNIT_GROSS_WEIGHT_LB+","+ATTRIBUTE_DOT+","+ATTRIBUTE_OCONUS_FLAG+",min("+ATTRIBUTE_HAZMAT_ID_MISSING + ")"+ ATTRIBUTE_HAZMAT_ID_MISSING +
		",sum("+ATTRIBUTE_PICK_QTY+") "+ATTRIBUTE_PICK_QTY+","+
		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_HUB + "," +
		ATTRIBUTE_PR_NUMBER + "," +
		ATTRIBUTE_LINE_ITEM + "," +
		ATTRIBUTE_STOCKING_METHOD + "," +
		ATTRIBUTE_DELIVERY_TYPE + "," +
		ATTRIBUTE_RELEASE_DATE + "," +
		ATTRIBUTE_NEED_DATE_PREFIX + "," +
		ATTRIBUTE_NEED_DATE + "," +
		ATTRIBUTE_APPLICATION + "," +
		ATTRIBUTE_APPLICATION_DESC + "," +
		ATTRIBUTE_FACILITY_ID + "," +
		ATTRIBUTE_PART_DESCRIPTION + "," +
		"substr(" + ATTRIBUTE_PACKAGING + ",1,200)" + ATTRIBUTE_PACKAGING + "," +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_CAT_PART_NO + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_DELIVERY_POINT + "," +
		ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
		ATTRIBUTE_REQUESTOR + "," +
		ATTRIBUTE_SCRAP + "," +
		"fx_mr_notes(" + ATTRIBUTE_COMPANY_ID + ", " +  ATTRIBUTE_PR_NUMBER + "," +  ATTRIBUTE_LINE_ITEM + "," + ATTRIBUTE_SHIPPED_AS_SINGLE + "," + ATTRIBUTE_SEAVAN + ") " + ATTRIBUTE_MR_NOTES + "," +
		ATTRIBUTE_CRITICAL + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_CARRIER_CODE + "," +
		ATTRIBUTE_PICKUP_TIME + "," +
		ATTRIBUTE_STOP_NUMBER + "," +
		ATTRIBUTE_TRAILER_NUMBER + "," +
		ATTRIBUTE_PACKING_GROUP_ID + "," +
		ATTRIBUTE_CARRIER_NAME + "," +
		ATTRIBUTE_TRANSPORTATION_MODE + "," +
		ATTRIBUTE_SHIP_TO_LOCATION_DESC + "," +
		ATTRIBUTE_SHIP_TO_CITY + "," +
		ATTRIBUTE_SHIP_TO_STATE_ABBREV + "," +
		ATTRIBUTE_SHIP_TO_ZIP + "," +
		ATTRIBUTE_MILSTRIP_CODE + "," +
		ATTRIBUTE_SHIP_TO_DODAAC + "," +
		ATTRIBUTE_REQUIRES_OVERPACK + "," +
		ATTRIBUTE_SHIPPED_AS_SINGLE + ","+ATTRIBUTE_CONSOLIDATION_NUMBER+ "," +
		ATTRIBUTE_MR_COUNT + "," +
		ATTRIBUTE_TRANSPORTATION_PRIORITY  + ","+
		ATTRIBUTE_HAZARDOUS  + ","+
		ATTRIBUTE_SPLIT_TCN + ","+
		ATTRIBUTE_ACK_SENT	+ ","+
		ATTRIBUTE_AIR_GROUND_INDICATOR + ","+
		ATTRIBUTE_CUSTOMER_SERVICE_REP_ID + ","+
		ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME + ","+
		ATTRIBUTE_OPS_ENTITY_ID  + ","+
		ATTRIBUTE_OPERATING_ENTITY_NAME + ","+
		ATTRIBUTE_CUSTOMER_ID + ","+
		ATTRIBUTE_CUSTOMER_NAME + ","+
		ATTRIBUTE_FACILITY_NAME + ","+
		ATTRIBUTE_INVENTORY_GROUP_NAME + ","+
		ATTRIBUTE_MATERIAL_REQUEST_ORIGIN + ","+
		ATTRIBUTE_ADDRESS_LINE_1_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_2_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_3_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_4_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_5_DISPLAY + ","+
		ATTRIBUTE_CMS + ","+
		ATTRIBUTE_DISTRIBUTION + ","+
		ATTRIBUTE_SHIPPING_REFERENCE + ","+
		ATTRIBUTE_ADR_MISSING + ","+
		ATTRIBUTE_DEST_INVENTORY_GROUP_NAME + ","+
		ATTRIBUTE_ABC_CLASSIFICATION + ","+
		ATTRIBUTE_DELIVERY_POINT_DESC + ","+
		ATTRIBUTE_CATALOG_PRICE + ","+
		ATTRIBUTE_CURRENCY_ID +
		" from " + TABLE + " " + getWhereClause(criteria);
		/*			ATTRIBUTE_ADDRESS_LINE_5_DISPLAY + ","+
			ATTRIBUTE_CUSTOMER_NOTE + ","+
			ATTRIBUTE_SHIPTO_NOTE + ","+
			ATTRIBUTE_PR_INTERNAL_NOTE + ","+
			ATTRIBUTE_LINE_INTERNAL_NOTE + ","+
			ATTRIBUTE_LINE_PURCHASING_NOTE+	" from " + TABLE + " " + getWhereClause(criteria);  */

		query+=additionalWhereClause+	" group by  "+ATTRIBUTE_UNIT_GROSS_WEIGHT_LB+","+ATTRIBUTE_DOT+","+ATTRIBUTE_OCONUS_FLAG+","+		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_HUB + "," +
		ATTRIBUTE_PR_NUMBER + "," +
		ATTRIBUTE_LINE_ITEM + "," +
		ATTRIBUTE_STOCKING_METHOD + "," +
		ATTRIBUTE_DELIVERY_TYPE + "," +
		ATTRIBUTE_RELEASE_DATE + "," +
		ATTRIBUTE_NEED_DATE_PREFIX + "," +
		ATTRIBUTE_NEED_DATE + "," +
		ATTRIBUTE_APPLICATION + "," +
		ATTRIBUTE_APPLICATION_DESC + "," +
		ATTRIBUTE_FACILITY_ID + "," +
		ATTRIBUTE_PART_DESCRIPTION + "," +
		"substr("+ATTRIBUTE_PACKAGING + ",1,200)," +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_CAT_PART_NO + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_DELIVERY_POINT + "," +
		ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
		ATTRIBUTE_REQUESTOR + "," +
		ATTRIBUTE_SCRAP + "," +
		ATTRIBUTE_SEAVAN + "," +
		ATTRIBUTE_CRITICAL + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_CARRIER_CODE + "," +
		ATTRIBUTE_PICKUP_TIME + "," +
		ATTRIBUTE_STOP_NUMBER + "," +
		ATTRIBUTE_TRAILER_NUMBER + "," +
		ATTRIBUTE_PACKING_GROUP_ID + "," +
		ATTRIBUTE_CARRIER_NAME + "," +
		ATTRIBUTE_TRANSPORTATION_MODE + "," +
		ATTRIBUTE_SHIP_TO_LOCATION_DESC + "," +
		ATTRIBUTE_SHIP_TO_CITY + "," +
		ATTRIBUTE_SHIP_TO_STATE_ABBREV + "," +
		ATTRIBUTE_SHIP_TO_ZIP + "," +
		ATTRIBUTE_MILSTRIP_CODE + "," +
		ATTRIBUTE_SHIP_TO_DODAAC + "," +
		ATTRIBUTE_REQUIRES_OVERPACK + "," +
		ATTRIBUTE_SHIPPED_AS_SINGLE + ","+
		ATTRIBUTE_CONSOLIDATION_NUMBER+","+ ATTRIBUTE_MR_COUNT + "," +
		ATTRIBUTE_TRANSPORTATION_PRIORITY  + ","+
		ATTRIBUTE_HAZARDOUS + ","+
		ATTRIBUTE_SPLIT_TCN + ","+
		ATTRIBUTE_ACK_SENT	+ ","+
		ATTRIBUTE_AIR_GROUND_INDICATOR + ","+
		ATTRIBUTE_CUSTOMER_SERVICE_REP_ID + ","+
		ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME + ","+
		ATTRIBUTE_OPS_ENTITY_ID  + ","+
		ATTRIBUTE_OPERATING_ENTITY_NAME + ","+
		ATTRIBUTE_CUSTOMER_ID + ","+
		ATTRIBUTE_CUSTOMER_NAME + ","+
		ATTRIBUTE_FACILITY_NAME + ","+
		ATTRIBUTE_INVENTORY_GROUP_NAME + ","+
		ATTRIBUTE_MATERIAL_REQUEST_ORIGIN + ","+
		ATTRIBUTE_ADDRESS_LINE_1_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_2_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_3_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_4_DISPLAY + ","+
		ATTRIBUTE_ADDRESS_LINE_5_DISPLAY + ","+
		ATTRIBUTE_CMS + ","+
		ATTRIBUTE_DISTRIBUTION + ","+
		ATTRIBUTE_SHIPPING_REFERENCE + ","+
		ATTRIBUTE_ADR_MISSING + ", "+
		ATTRIBUTE_DEST_INVENTORY_GROUP_NAME + ", "+
		ATTRIBUTE_ABC_CLASSIFICATION + ","+
		ATTRIBUTE_DELIVERY_POINT_DESC + ","+
		ATTRIBUTE_CATALOG_PRICE + ","+
		ATTRIBUTE_CURRENCY_ID + " ";
		;
		/*			ATTRIBUTE_ADDRESS_LINE_5_DISPLAY + ","+
			ATTRIBUTE_CUSTOMER_NOTE + ","+
			ATTRIBUTE_SHIPTO_NOTE + ","+
			ATTRIBUTE_PR_INTERNAL_NOTE + ","+
			ATTRIBUTE_LINE_INTERNAL_NOTE + ","+
			ATTRIBUTE_LINE_PURCHASING_NOTE+ " ";   */

		query+=	getOrderByClause(sortCriteria);
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			PicklistSelectionViewBean picklistSelectionViewBean = new PicklistSelectionViewBean();
			load(dataSetRow, picklistSelectionViewBean);
			picklistSelectionViewBeanColl.add(picklistSelectionViewBean);
		}
		return picklistSelectionViewBeanColl;
	}

	public Collection createPicklist(Collection inArgs,Collection outArgs, Collection optionalInArgs,   Locale locale) throws BaseException {
		GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
		Connection conn = this.getDbManager().getConnection();
		if (log.isDebugEnabled()) {
			log.debug("calling p_create_picklist "+inArgs+" "+outArgs+" "+optionalInArgs);
		}

		Collection c = null;
		try {
			c = procFactory.doProcedure(conn, "p_create_picklist", inArgs, outArgs,optionalInArgs);
		}
		catch(Exception ex)
		{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			c = new Vector();
			c.add(library.getString(Globals.DB_PROCEDURE_EXCEPTION));
		}
		finally {
			this.getDbManager().returnConnection(conn);
		}
		return c;
	}
}