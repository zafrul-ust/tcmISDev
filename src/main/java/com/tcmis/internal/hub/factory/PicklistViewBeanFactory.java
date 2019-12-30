package com.tcmis.internal.hub.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.hub.beans.PicklistViewBean;


/******************************************************************************
 * CLASSNAME: PicklistViewBeanFactory <br>
 * @version: 1.0, Feb 21, 2007 <br>
 *****************************************************************************/


public class PicklistViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_MR_LINE = "MR_LINE";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
	public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PICKLIST_QUANTITY = "PICKLIST_QUANTITY";
	public String ATTRIBUTE_QUANTITY_PICKED = "QUANTITY_PICKED";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_INVENTORY_QUANTITY = "INVENTORY_QUANTITY";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_END_USER = "END_USER";
	public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
	public String ATTRIBUTE_MR_NOTES = "MR_NOTES";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_CERTIFICATION_DATE = "CERTIFICATION_DATE";
	public String ATTRIBUTE_PICKABLE = "PICKABLE";
	public String ATTRIBUTE_CERTIFIED_BY = "CERTIFIED_BY";
	public String ATTRIBUTE_CERTIFICATION_NUMBER = "CERTIFICATION_NUMBER";
	public String ATTRIBUTE_QUALITY_CONTROL_ITEM = "QUALITY_CONTROL_ITEM";
	public String ATTRIBUTE_FAC_LOC_APP_PART_COMMENT = "FAC_LOC_APP_PART_COMMENT";
	public String ATTRIBUTE_CAT_PART_COMMENT = "CAT_PART_COMMENT";
	public String ATTRIBUTE_CABINET_REPLENISHMENT = "CABINET_REPLENISHMENT";
	public String ATTRIBUTE_HAZMAT_ID_MISSING = "HAZMAT_ID_MISSING";
	public String ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE = "RECEIPT_DOCUMENT_AVAILABLE";
	public String ATTRIBUTE_NONINTEGER_RECEIVING = "NONINTEGER_RECEIVING";
	public String ATTRIBUTE_RECERT_NUMBER = "RECERT_NUMBER";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_PICKUP_TIME = "PICKUP_TIME";
	public String ATTRIBUTE_STOP_NUMBER = "STOP_NUMBER";
	public String ATTRIBUTE_TRAILER_NUMBER = "TRAILER_NUMBER";
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_TRANSPORTATION_MODE = "TRANSPORTATION_MODE";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_SHIP_TO_LOCATION_DESC = "SHIP_TO_LOCATION_DESC";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_STATE_ABBREV = "SHIP_TO_STATE_ABBREV";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_PACKAGED_AS = "PACKAGED_AS";
	public String ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_BOX = "MAX_UNIT_OF_ISSUE_PER_BOX";
	public String ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_PALLET = "MAX_UNIT_OF_ISSUE_PER_PALLET";
	public String ATTRIBUTE_CONSOLIDATION_NUMBER = "CONSOLIDATION_NUMBER";
	public String ATTRIBUTE_MR_COUNT = "MR_COUNT";
	public String ATTRIBUTE_OCONUS_FLAG = "OCONUS_FLAG";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_DOT = "DOT";
	public String ATTRIBUTE_TRANSPORTATION_PRIORITY = "TRANSPORTATION_PRIORITY";
	public String ATTRIBUTE_HAZARDOUS = "HAZARDOUS";
	public String ATTRIBUTE_RDD_COMMENT = "RDD_COMMENT";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_SPLIT_TCN = "SPLIT_TCN";
	public String ATTRIBUTE_AIR_GROUND_INDICATOR = "AIR_GROUND_INDICATOR";
	public String ATTRIBUTE_CUSTOMER_SERVICE_REP_ID = "CUSTOMER_SERVICE_REP_ID";
	public String ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME = "CUSTOMER_SERVICE_REP_NAME";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_CUSTOMER_RECEIPT_ID = "CUSTOMER_RECEIPT_ID";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_REQUESTOR_PHONE = "REQUESTOR_PHONE";
	public String ATTRIBUTE_REQUESTOR_FAX = "REQUESTOR_FAX";
	public String ATTRIBUTE_REQUESTOR_EMAIL = "REQUESTOR_EMAIL";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_SPECIAL_INSTRUCTIONS = "SPECIAL_INSTRUCTIONS";
	public String ATTRIBUTE_CARRIER_ACCOUNT_ID = "CARRIER_ACCOUNT_ID";
	public String ATTRIBUTE_CARRIER_CONTACT = "CARRIER_CONTACT";
	public String ATTRIBUTE_CARRIER_SERVICE_TYPE = "CARRIER_SERVICE_TYPE";
	public String ATTRIBUTE_INCO_TERMS = "INCO_TERMS";
	public String ATTRIBUTE_FLASH_POINT = "FLASH_POINT";
	public String ATTRIBUTE_ADDRESS_LINE_1_DISPLAY = "ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_2_DISPLAY = "ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_3_DISPLAY = "ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_4_DISPLAY = "ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_5_DISPLAY = "ADDRESS_LINE_5_DISPLAY";
	public String ATTRIBUTE_MATERIAL_REQUEST_ORIGIN = "MATERIAL_REQUEST_ORIGIN";
	public String ATTRIBUTE_SUBMITTED_BY = "SUBMITTED_BY";
	public String ATTRIBUTE_SUBMITTED_BY_NAME = "SUBMITTED_BY_NAME";
	public String ATTRIBUTE_HUB_BIN_ROOM = "HUB_BIN_ROOM";
    public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
    public String ATTRIBUTE_LOGO_IMAGE_URL = "LOGO_IMAGE_URL";
    public String ATTRIBUTE_CMS = "CMS";
	public String ATTRIBUTE_DISTRIBUTION = "DISTRIBUTION";
	public String ATTRIBUTE_SHIPPING_REFERENCE = "SHIPPING_REFERENCE";
	public String ATTRIBUTE_MR_COMPLETE_PICKABLE = "MR_COMPLETE_PICKABLE";
	public String ATTRIBUTE_CUSTOMER_PART_NO = "CUSTOMER_PART_NO";
	public String ATTRIBUTE_REPORTING_ENTITY_NAME = "REPORTING_ENTITY_NAME";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_OWNER_SEGMENT_ID = "OWNER_SEGMENT_ID";
	public String ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_1 = "ALLOCATE_BY_CHARGE_NUMBER_1";
	public String ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_2 = "ALLOCATE_BY_CHARGE_NUMBER_2";
	public String ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_3 = "ALLOCATE_BY_CHARGE_NUMBER_3";
	public String ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_4 = "ALLOCATE_BY_CHARGE_NUMBER_4";
	public String ATTRIBUTE_RECEIPT_SPEC_NAME_LIST = "RECEIPT_SPEC_NAME_LIST";
	public String ATTRIBUTE_RECEIPT_SPEC_VERSION = "RECEIPT_SPEC_VERSION";
	public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";


  //table name
	public String TABLE = "PICKLIST_VIEW";


	//constructor
	public PicklistViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("lotStatus")) {
			return ATTRIBUTE_LOT_STATUS;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("stockingMethod")) {
			return ATTRIBUTE_STOCKING_METHOD;
		}
		else if(attributeName.equals("deliveryType")) {
			return ATTRIBUTE_DELIVERY_TYPE;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("picklistQuantity")) {
			return ATTRIBUTE_PICKLIST_QUANTITY;
		}
		else if(attributeName.equals("quantityPicked")) {
			return ATTRIBUTE_QUANTITY_PICKED;
		}
		else if(attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		
		else if(attributeName.equals("applicationDesc")) {
			return 	ATTRIBUTE_APPLICATION_DESC ;
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
		else if(attributeName.equals("inventoryQuantity")) {
			return ATTRIBUTE_INVENTORY_QUANTITY;
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
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
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
		else if(attributeName.equals("endUser")) {
			return ATTRIBUTE_END_USER;
		}
		else if(attributeName.equals("transferRequestId")) {
			return ATTRIBUTE_TRANSFER_REQUEST_ID;
		}
		else if(attributeName.equals("mrNotes")) {
			return ATTRIBUTE_MR_NOTES;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("certificationDate")) {
			return ATTRIBUTE_CERTIFICATION_DATE;
		}
		else if(attributeName.equals("pickable")) {
			return ATTRIBUTE_PICKABLE;
		}
		else if(attributeName.equals("certifiedBy")) {
			return ATTRIBUTE_CERTIFIED_BY;
		}
		else if(attributeName.equals("certificationNumber")) {
			return ATTRIBUTE_CERTIFICATION_NUMBER;
		}
		else if(attributeName.equals("qualityControlItem")) {
			return ATTRIBUTE_QUALITY_CONTROL_ITEM;
		}
		else if(attributeName.equals("facLocAppPartComment")) {
			return ATTRIBUTE_FAC_LOC_APP_PART_COMMENT;
		}
		else if(attributeName.equals("catPartComment")) {
			return ATTRIBUTE_CAT_PART_COMMENT;
		}
		else if(attributeName.equals("cabinetReplenishment")) {
			return ATTRIBUTE_CABINET_REPLENISHMENT;
		}
		else if(attributeName.equals("hazmatIdMissing")) {
			return ATTRIBUTE_HAZMAT_ID_MISSING;
		}
		else if(attributeName.equals("receiptDocumentAvailable")) {
			return ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE;
		}
		else if(attributeName.equals("nonintegerReceiving")) {
			return ATTRIBUTE_NONINTEGER_RECEIVING;
		}
		else if(attributeName.equals("recertNumber")) {
			return ATTRIBUTE_RECERT_NUMBER;
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
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("shippedAsSingle")) {
			return ATTRIBUTE_SHIPPED_AS_SINGLE;
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
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("packagedAs")) {
			return ATTRIBUTE_PACKAGED_AS;
		}
		else if(attributeName.equals("maxUnitOfIssuePerBox")) {
			return ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_BOX;
		}
		else if(attributeName.equals("maxUnitOfIssuePerPallet")) {
			return ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_PALLET;
		}
		else if(attributeName.equals("consolidationNumber")) {
			return ATTRIBUTE_CONSOLIDATION_NUMBER;
		}
		else if(attributeName.equals("mrCount")) {
			return ATTRIBUTE_MR_COUNT;
		}
		else if(attributeName.equals("oconusFlag")) {
			return ATTRIBUTE_OCONUS_FLAG;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("dot")) {
			return ATTRIBUTE_DOT;
		}
		else if(attributeName.equals("transportationPriority")) {
			return ATTRIBUTE_TRANSPORTATION_PRIORITY;
		}
		else if(attributeName.equals("hazardous")) {
			return ATTRIBUTE_HAZARDOUS;
		}
		else if(attributeName.equals("rddComment")) {
			return ATTRIBUTE_RDD_COMMENT;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("splitTcn")) {
			return ATTRIBUTE_SPLIT_TCN;
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
		else if(attributeName.equals("customerReceiptId")) {
			return ATTRIBUTE_CUSTOMER_RECEIPT_ID;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("requestorPhone")) {
			return ATTRIBUTE_REQUESTOR_PHONE;
		}
		else if(attributeName.equals("requestorFax")) {
			return ATTRIBUTE_REQUESTOR_FAX;
		}
		else if(attributeName.equals("requestorEmail")) {
			return ATTRIBUTE_REQUESTOR_EMAIL;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("specialInstructions")) {
			return ATTRIBUTE_SPECIAL_INSTRUCTIONS;
		}
		else if(attributeName.equals("carrierAccountId")) {
			return ATTRIBUTE_CARRIER_ACCOUNT_ID;
		}
		else if(attributeName.equals("carrierContact")) {
			return ATTRIBUTE_CARRIER_CONTACT;
		}
		else if(attributeName.equals("carrierServiceType")) {
			return ATTRIBUTE_CARRIER_SERVICE_TYPE;
		}
		else if(attributeName.equals("incoTerms")) {
			return ATTRIBUTE_INCO_TERMS;
		}
		else if(attributeName.equals("flashPoint")) {
			return ATTRIBUTE_FLASH_POINT;
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
		else if(attributeName.equals("materialRequestOrigin")) {
			return ATTRIBUTE_MATERIAL_REQUEST_ORIGIN;
		}
		else if(attributeName.equals("submittedBy")) {
			return ATTRIBUTE_SUBMITTED_BY;
		}
		else if(attributeName.equals("submittedByName")) {
			return ATTRIBUTE_SUBMITTED_BY_NAME;
		}
		else if(attributeName.equals("hubBinRoom")) {
			return ATTRIBUTE_HUB_BIN_ROOM;
		}
        else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
        else if(attributeName.equals("logoImageUrl")) {
			return ATTRIBUTE_LOGO_IMAGE_URL;
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
		else if (attributeName.equals("mrCompletePickable")) {
			return ATTRIBUTE_MR_COMPLETE_PICKABLE;
		}
		else if (attributeName.equals("mrCompletePickable")) {
			return ATTRIBUTE_MR_COMPLETE_PICKABLE;
		}
		else if (attributeName.equals("customerPartNo")) {
			return ATTRIBUTE_CUSTOMER_PART_NO;
		}
		else if (attributeName.equals("reportingEntityName")) {
			return ATTRIBUTE_REPORTING_ENTITY_NAME;
		}
		else if (attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if (attributeName.equals("ownerSegmentId")) {
			return ATTRIBUTE_OWNER_SEGMENT_ID;
		}
		else if (attributeName.equals("allocateByChargeNumber1")) {
			return ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_1;
		}
		else if (attributeName.equals("allocateByChargeNumber2")) {
			return ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_2;
		}
		else if (attributeName.equals("allocateByChargeNumber3")) {
			return ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_3;
		}
		else if (attributeName.equals("allocateByChargeNumber4")) {
			return ATTRIBUTE_ALLOCATE_BY_CHARGE_NUMBER_4;
		}
		else if (attributeName.equals("receiptSpecNameList")) {
			return ATTRIBUTE_RECEIPT_SPEC_NAME_LIST;
		}
		else if (attributeName.equals("receiptSpecVersion")) {
			return ATTRIBUTE_RECEIPT_SPEC_VERSION;
		}
		else if (attributeName.equals("releaseNumber")) {
			return ATTRIBUTE_RELEASE_NUMBER;
		}	
        else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PicklistViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PicklistViewBean picklistViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("picklistId", "SearchCriterion.EQUALS",
			"" + picklistViewBean.getPicklistId());

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


	public int delete(PicklistViewBean picklistViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("picklistId", "SearchCriterion.EQUALS",
			"" + picklistViewBean.getPicklistId());

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

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection picklistViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PicklistViewBean picklistViewBean = new PicklistViewBean();
			load(dataSetRow, picklistViewBean);
			picklistViewBeanColl.add(picklistViewBean);
		}

		return picklistViewBeanColl;
	}
	
   public Collection select(SortCriteria sortCriteria,String searchCriteria) throws BaseException {
	      Connection connection = null;
	      Collection c = null;
	      try {
	         connection = this.getDbManager().getConnection();
	         c = select(sortCriteria,searchCriteria, connection);
	      }
	      finally {
	         this.getDbManager().returnConnection(connection);
	      }
	      return c;
	   }

	   public Collection select(SortCriteria sortCriteria,String searchCriteria, Connection conn) throws BaseException {
	      Collection picklistViewBeanColl = new Vector();

	      String query = "select * from table (pkg_pick.fx_picklist"+searchCriteria+")) " + getOrderByClause(sortCriteria);
	      DataSet dataSet = new SqlManager().select(conn, query);
	      Iterator dataIter = dataSet.iterator();
	      while (dataIter.hasNext()) {
	         DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	         PicklistViewBean picklistViewBean = new PicklistViewBean();
	         load(dataSetRow, picklistViewBean);
	         picklistViewBeanColl.add(picklistViewBean);
	      }
	      return picklistViewBeanColl;
	   }

public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,BigDecimal picklistId) throws BaseException {
      Connection connection = null;
      Collection c = null;
      try {
         connection = this.getDbManager().getConnection();
         c = select(criteria, sortCriteria,picklistId, connection);
      }
      finally {
         this.getDbManager().returnConnection(connection);
      }
      return c;
   }

   public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,BigDecimal picklistId, Connection conn) throws BaseException {
      Collection picklistViewBeanColl = new Vector();

      String query = "select * from table (pkg_pick.fx_picklist("+picklistId+")) " + getOrderByClause(sortCriteria);
      DataSet dataSet = new SqlManager().select(conn, query);
      Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow) dataIter.next();
         PicklistViewBean picklistViewBean = new PicklistViewBean();
         load(dataSetRow, picklistViewBean);
         picklistViewBeanColl.add(picklistViewBean);
      }
      return picklistViewBeanColl;
   }

/*	public Collection selectWithOrderBy(SearchCriteria criteria, String orderBy)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectWithOrderBy(criteria,orderBy, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectWithOrderBy(SearchCriteria criteria, String orderBy, Connection conn)
		throws BaseException {

		Collection picklistViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);
		if(!StringHandler.isBlankString(orderBy)) {
			query += " order by "+orderBy;
		}

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PicklistViewBean picklistViewBean = new PicklistViewBean();
			load(dataSetRow, picklistViewBean);
			picklistViewBeanColl.add(picklistViewBean);
		}

		return picklistViewBeanColl;
	}*/

   public Collection enterPick(Collection inArgs) throws BaseException {
     Collection outArgs = new Vector(2);
     outArgs.add(new Integer(java.sql.Types.INTEGER));
     outArgs.add(new Integer(java.sql.Types.VARCHAR));
     GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
     Connection conn = this.getDbManager().getConnection();
     return procFactory.doProcedure(conn, "p_enter_pick", inArgs, outArgs);
   }

   public void lineCloseFromPickqc(Collection inArgs) throws BaseException {
     GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
     Connection conn = this.getDbManager().getConnection();
     procFactory.doProcedure(conn, "p_line_close_from_pickqc", inArgs);
   }
   
   public void lineItemAllocateFromPickqc(Collection inArgs) throws BaseException {
	     GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
	     Connection conn = this.getDbManager().getConnection();
	     procFactory.doProcedure(conn, "P_LINE_ITEM_ALLOCATE", inArgs);
	   }

   public void qcPicklist(Collection inArgs) throws BaseException {
     GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
     Connection conn = this.getDbManager().getConnection();
     procFactory.doProcedure(conn, "p_qc_picklist", inArgs);
   }

   public Collection reversePick(Collection inArgs) throws BaseException {
     Collection outArgs = new Vector(1);
     outArgs.add(new Integer(java.sql.Types.VARCHAR));
     GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
     Connection conn = this.getDbManager().getConnection();
     return procFactory.doProcedure(conn, "p_reverse_pick_qc", inArgs, outArgs);
   }
}