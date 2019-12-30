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
import com.tcmis.internal.hub.beans.CustPoAddressChangeViewBean;


/******************************************************************************
 * CLASSNAME: CustPoAddressChangeViewBeanFactory <br>
 * @version: 1.0, Mar 14, 2008 <br>
 *****************************************************************************/


public class CustPoAddressChangeViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID = "ADDRESS_CHANGE_REQUEST_ID";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_ORIG_MARK_FOR_DODAAC = "ORIG_MARK_FOR_DODAAC";
	public String ATTRIBUTE_ORIG_SHIP_TO_DODAAC = "ORIG_SHIP_TO_DODAAC";
	public String ATTRIBUTE_SHIPPING_NOTES = "SHIPPING_NOTES";
	public String ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID = "CUSTOMER_HAAS_CONTRACT_ID";
	public String ATTRIBUTE_RELEASE_NUM = "RELEASE_NUM";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_PROJECT_CODE = "PROJECT_CODE";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
	public String ATTRIBUTE_FMS_CASE_NUM = "FMS_CASE_NUM";
	public String ATTRIBUTE_PORT_OF_EMBARKATION = "PORT_OF_EMBARKATION";
	public String ATTRIBUTE_PORT_OF_DEBARKATION = "PORT_OF_DEBARKATION";
	public String ATTRIBUTE_TRANSPORTATION_PRIORITY = "TRANSPORTATION_PRIORITY";
	public String ATTRIBUTE_TRANSACTION_REF_NUM = "TRANSACTION_REF_NUM";
	public String ATTRIBUTE_TRANSPORTATION_CONTROL_NUM = "TRANSPORTATION_CONTROL_NUM";
	public String ATTRIBUTE_RDD = "RDD";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_REQUISITION_NUMBER = "REQUISITION_NUMBER";
	public String ATTRIBUTE_CALL_NUMBER = "CALL_NUMBER";
	public String ATTRIBUTE_ST_COUNTRY_ABBREV = "ST_COUNTRY_ABBREV";
	public String ATTRIBUTE_ST_STATE_ABBREV = "ST_STATE_ABBREV";
	public String ATTRIBUTE_ST_CITY = "ST_CITY";
	public String ATTRIBUTE_ST_ZIP = "ST_ZIP";
	public String ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY = "ST_ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY = "ST_ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY = "ST_ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY = "ST_ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_MF_COUNTRY_ABBREV = "MF_COUNTRY_ABBREV";
	public String ATTRIBUTE_MF_STATE_ABBREV = "MF_STATE_ABBREV";
	public String ATTRIBUTE_MF_CITY = "MF_CITY";
	public String ATTRIBUTE_MF_ZIP = "MF_ZIP";
	public String ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY = "MF_ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY = "MF_ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY = "MF_ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY = "MF_ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_NSN = "NSN";
	public String ATTRIBUTE_CLIN = "CLIN";
	public String ATTRIBUTE_INDEXING_TYPE = "INDEXING_TYPE";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_GROSS_WEIGHT_LBS = "GROSS_WEIGHT_LBS";
	public String ATTRIBUTE_CUBIC_FEET = "CUBIC_FEET";
	public String ATTRIBUTE_SIGNAL_CODE = "SIGNAL_CODE";
	public String ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE = "SUPPLEMENTARY_ADDR_CODE";
	public String ATTRIBUTE_DOC_ID_CODE = "DOC_ID_CODE";
	public String ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE = "ORIGINAL_TRANSACTION_TYPE";
	public String ATTRIBUTE_ORIG_MARK_FOR_LOCATION_ID = "ORIG_MARK_FOR_LOCATION_ID";
	public String ATTRIBUTE_ORIG_SHIP_TO_LOCATION_ID = "ORIG_SHIP_TO_LOCATION_ID";
    public String ATTRIBUTE_MARK_FOR_DODAAC_TYPE = "MARK_FOR_DODAAC_TYPE";
	public String ATTRIBUTE_SHIP_TO_DODAAC_TYPE = "SHIP_TO_DODAAC_TYPE";
    public String ATTRIBUTE_NOL = "NOL";
    public String ATTRIBUTE_DATE_ADDRESS_OK = "DATE_ADDRESS_OK";
    public String ATTRIBUTE_VERIFIED_BY_NAME = "VERIFIED_BY_NAME";
    public String ATTRIBUTE_MARK_FOR_ADDRESS1 = "MARK_FOR_ADDRESS1";
    public String ATTRIBUTE_MARK_FOR_ADDRESS2 = "MARK_FOR_ADDRESS2";
    public String ATTRIBUTE_MARK_FOR_ADDRESS3 = "MARK_FOR_ADDRESS3";
    public String ATTRIBUTE_MARK_FOR_ADDRESS4 = "MARK_FOR_ADDRESS4";
    public String ATTRIBUTE_SHIPTO_ADDRESS1 = "SHIPTO_ADDRESS1";
    public String ATTRIBUTE_SHIPTO_ADDRESS2 = "SHIPTO_ADDRESS2";
    public String ATTRIBUTE_SHIPTO_ADDRESS3 = "SHIPTO_ADDRESS3";
    public String ATTRIBUTE_SHIPTO_ADDRESS4 = "SHIPTO_ADDRESS4";

    //table name
	public String TABLE = "CUST_PO_ADDRESS_CHANGE_VIEW";


	//constructor
	public CustPoAddressChangeViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("addressChangeRequestId")) {
			return ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("origMarkForDodaac")) {
			return ATTRIBUTE_ORIG_MARK_FOR_DODAAC;
		}
		else if(attributeName.equals("origShipToDodaac")) {
			return ATTRIBUTE_ORIG_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("shippingNotes")) {
			return ATTRIBUTE_SHIPPING_NOTES;
		}
		else if(attributeName.equals("customerHaasContractId")) {
			return ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID;
		}
		else if(attributeName.equals("releaseNum")) {
			return ATTRIBUTE_RELEASE_NUM;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("projectCode")) {
			return ATTRIBUTE_PROJECT_CODE;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
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
		else if(attributeName.equals("transportationPriority")) {
			return ATTRIBUTE_TRANSPORTATION_PRIORITY;
		}
		else if(attributeName.equals("transactionRefNum")) {
			return ATTRIBUTE_TRANSACTION_REF_NUM;
		}
		else if(attributeName.equals("transportationControlNum")) {
			return ATTRIBUTE_TRANSPORTATION_CONTROL_NUM;
		}
		else if(attributeName.equals("rdd")) {
			return ATTRIBUTE_RDD;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("requisitionNumber")) {
			return ATTRIBUTE_REQUISITION_NUMBER;
		}
		else if(attributeName.equals("callNumber")) {
			return ATTRIBUTE_CALL_NUMBER;
		}
		else if(attributeName.equals("stCountryAbbrev")) {
			return ATTRIBUTE_ST_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("stStateAbbrev")) {
			return ATTRIBUTE_ST_STATE_ABBREV;
		}
		else if(attributeName.equals("stCity")) {
			return ATTRIBUTE_ST_CITY;
		}
		else if(attributeName.equals("stZip")) {
			return ATTRIBUTE_ST_ZIP;
		}
		else if(attributeName.equals("stAddressLine1Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("stAddressLine2Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("stAddressLine3Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("stAddressLine4Display")) {
			return ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("mfCountryAbbrev")) {
			return ATTRIBUTE_MF_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("mfStateAbbrev")) {
			return ATTRIBUTE_MF_STATE_ABBREV;
		}
		else if(attributeName.equals("mfCity")) {
			return ATTRIBUTE_MF_CITY;
		}
		else if(attributeName.equals("mfZip")) {
			return ATTRIBUTE_MF_ZIP;
		}
		else if(attributeName.equals("mfAddressLine1Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("mfAddressLine2Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("mfAddressLine3Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("mfAddressLine4Display")) {
			return ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("nsn")) {
			return ATTRIBUTE_NSN;
		}
		else if(attributeName.equals("clin")) {
			return ATTRIBUTE_CLIN;
		}
		else if(attributeName.equals("indexingType")) {
			return ATTRIBUTE_INDEXING_TYPE;
		}
		else if(attributeName.equals("shippedAsSingle")) {
			return ATTRIBUTE_SHIPPED_AS_SINGLE;
		}
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("grossWeightLbs")) {
			return ATTRIBUTE_GROSS_WEIGHT_LBS;
		}
		else if(attributeName.equals("cubicFeet")) {
			return ATTRIBUTE_CUBIC_FEET;
		}
		else if(attributeName.equals("signalCode")) {
			return ATTRIBUTE_SIGNAL_CODE;
		}
		else if(attributeName.equals("supplementaryAddrCode")) {
			return ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE;
		}
		else if(attributeName.equals("docIdCode")) {
			return ATTRIBUTE_DOC_ID_CODE;
		}
		else if(attributeName.equals("originalTransactionType")) {
			return ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("origMarkForLocationId")) {
			return ATTRIBUTE_ORIG_MARK_FOR_LOCATION_ID;
		}
		else if(attributeName.equals("origShipToLocationId")) {
			return ATTRIBUTE_ORIG_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("markForDodaacType")) {
			return ATTRIBUTE_MARK_FOR_DODAAC_TYPE;
		}
		else if(attributeName.equals("shipToDodaacType")) {
			return ATTRIBUTE_SHIP_TO_DODAAC_TYPE;
		}
        else if(attributeName.equals("nol")) {
			return ATTRIBUTE_NOL;
		}
        else if(attributeName.equals("dateAddressOk")) {
			return ATTRIBUTE_DATE_ADDRESS_OK;
		}
        else if(attributeName.equals("verifiedByName")) {
			return ATTRIBUTE_VERIFIED_BY_NAME;
		}
        else if(attributeName.equals("markForAddress1")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS1;
	    }
        else if(attributeName.equals("markForAddress2")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS2;
	    }
        else if(attributeName.equals("markForAddress3")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS3;
	    }
        else if(attributeName.equals("markForAddress4")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS4;
	    }
        else if(attributeName.equals("shiptoAddress1")) {
			return ATTRIBUTE_SHIPTO_ADDRESS1;
	    }
        else if(attributeName.equals("shiptoAddress2")) {
			return ATTRIBUTE_SHIPTO_ADDRESS2;
	    }
        else if(attributeName.equals("shiptoAddress3")) {
			return ATTRIBUTE_SHIPTO_ADDRESS3;
	    }
        else if(attributeName.equals("shiptoAddress4")) {
			return ATTRIBUTE_SHIPTO_ADDRESS4;
	    }
        else {
			return super.getColumnName(attributeName);
		}
		
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustPoAddressChangeViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustPoAddressChangeViewBean custPoAddressChangeViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + custPoAddressChangeViewBean.getCompanyId());

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


	public int delete(CustPoAddressChangeViewBean custPoAddressChangeViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + custPoAddressChangeViewBean.getCompanyId());

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
	public int insert(CustPoAddressChangeViewBean custPoAddressChangeViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(custPoAddressChangeViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustPoAddressChangeViewBean custPoAddressChangeViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_ORIG_MARK_FOR_DODAAC + "," +
			ATTRIBUTE_ORIG_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_SHIPPING_NOTES + "," +
			ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "," +
			ATTRIBUTE_RELEASE_NUM + "," +
			ATTRIBUTE_MILSTRIP_CODE + "," +
			ATTRIBUTE_PROJECT_CODE + "," +
			ATTRIBUTE_PRIORITY_RATING + "," +
			ATTRIBUTE_FMS_CASE_NUM + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NUM + "," +
			ATTRIBUTE_RDD + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_REQUISITION_NUMBER + "," +
			ATTRIBUTE_CALL_NUMBER + "," +
			ATTRIBUTE_ST_COUNTRY_ABBREV + "," +
			ATTRIBUTE_ST_STATE_ABBREV + "," +
			ATTRIBUTE_ST_CITY + "," +
			ATTRIBUTE_ST_ZIP + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY + "," +
			ATTRIBUTE_MF_COUNTRY_ABBREV + "," +
			ATTRIBUTE_MF_STATE_ABBREV + "," +
			ATTRIBUTE_MF_CITY + "," +
			ATTRIBUTE_MF_ZIP + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY + "," +
			ATTRIBUTE_NSN + "," +
			ATTRIBUTE_CLIN + "," +
			ATTRIBUTE_INDEXING_TYPE + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "," +
			ATTRIBUTE_GROSS_WEIGHT_LBS + "," +
			ATTRIBUTE_CUBIC_FEET + "," +
			ATTRIBUTE_SIGNAL_CODE + "," +
			ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE + "," +
			ATTRIBUTE_DOC_ID_CODE + "," +
			ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE + "," +
			ATTRIBUTE_ORIG_MARK_FOR_LOCATION_ID + "," +
			ATTRIBUTE_ORIG_SHIP_TO_LOCATION_ID + ")" +
			" values (" +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getCompanyId()) + "," +
			custPoAddressChangeViewBean.getAddressChangeRequestId() + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStatus()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigMarkForDodaac()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigShipToDodaac()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getShippingNotes()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getCustomerHaasContractId()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getReleaseNum()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMilstripCode()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getProjectCode()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getPriorityRating()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getFmsCaseNum()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getPortOfEmbarkation()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getPortOfDebarkation()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getTransportationPriority()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getTransactionRefNum()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getTransportationControlNum()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getRdd()) + "," +
			custPoAddressChangeViewBean.getQuantity() + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getUom()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getRequisitionNumber()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getCallNumber()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStCountryAbbrev()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStStateAbbrev()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStCity()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStZip()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine1Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine2Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine3Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine4Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfCountryAbbrev()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfStateAbbrev()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfCity()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfZip()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine1Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine2Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine3Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine4Display()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getNsn()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getClin()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getIndexingType()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getShippedAsSingle()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getRequiresOverpack()) + "," +
			custPoAddressChangeViewBean.getGrossWeightLbs() + "," +
			custPoAddressChangeViewBean.getCubicFeet() + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getSignalCode()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getSupplementaryAddrCode()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getDocIdCode()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getOriginalTransactionType()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigMarkForLocationId()) + "," +
			SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigShipToLocationId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CustPoAddressChangeViewBean custPoAddressChangeViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(custPoAddressChangeViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustPoAddressChangeViewBean custPoAddressChangeViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getCompanyId()) + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(custPoAddressChangeViewBean.getAddressChangeRequestId()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStatus()) + "," +
			ATTRIBUTE_ORIG_MARK_FOR_DODAAC + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigMarkForDodaac()) + "," +
			ATTRIBUTE_ORIG_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigShipToDodaac()) + "," +
			ATTRIBUTE_SHIPPING_NOTES + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getShippingNotes()) + "," +
			ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getCustomerHaasContractId()) + "," +
			ATTRIBUTE_RELEASE_NUM + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getReleaseNum()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMilstripCode()) + "," +
			ATTRIBUTE_PROJECT_CODE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getProjectCode()) + "," +
			ATTRIBUTE_PRIORITY_RATING + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getPriorityRating()) + "," +
			ATTRIBUTE_FMS_CASE_NUM + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getFmsCaseNum()) + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getPortOfEmbarkation()) + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getPortOfDebarkation()) + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getTransportationPriority()) + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getTransactionRefNum()) + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NUM + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getTransportationControlNum()) + "," +
			ATTRIBUTE_RDD + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getRdd()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(custPoAddressChangeViewBean.getQuantity()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getUom()) + "," +
			ATTRIBUTE_REQUISITION_NUMBER + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getRequisitionNumber()) + "," +
			ATTRIBUTE_CALL_NUMBER + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getCallNumber()) + "," +
			ATTRIBUTE_ST_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStCountryAbbrev()) + "," +
			ATTRIBUTE_ST_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStStateAbbrev()) + "," +
			ATTRIBUTE_ST_CITY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStCity()) + "," +
			ATTRIBUTE_ST_ZIP + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStZip()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_1_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine1Display()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_2_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine2Display()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_3_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine3Display()) + "," +
			ATTRIBUTE_ST_ADDRESS_LINE_4_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getStAddressLine4Display()) + "," +
			ATTRIBUTE_MF_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfCountryAbbrev()) + "," +
			ATTRIBUTE_MF_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfStateAbbrev()) + "," +
			ATTRIBUTE_MF_CITY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfCity()) + "," +
			ATTRIBUTE_MF_ZIP + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfZip()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_1_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine1Display()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_2_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine2Display()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_3_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine3Display()) + "," +
			ATTRIBUTE_MF_ADDRESS_LINE_4_DISPLAY + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getMfAddressLine4Display()) + "," +
			ATTRIBUTE_NSN + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getNsn()) + "," +
			ATTRIBUTE_CLIN + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getClin()) + "," +
			ATTRIBUTE_INDEXING_TYPE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getIndexingType()) + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getShippedAsSingle()) + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getRequiresOverpack()) + "," +
			ATTRIBUTE_GROSS_WEIGHT_LBS + "=" + 
				StringHandler.nullIfZero(custPoAddressChangeViewBean.getGrossWeightLbs()) + "," +
			ATTRIBUTE_CUBIC_FEET + "=" + 
				StringHandler.nullIfZero(custPoAddressChangeViewBean.getCubicFeet()) + "," +
			ATTRIBUTE_SIGNAL_CODE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getSignalCode()) + "," +
			ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getSupplementaryAddrCode()) + "," +
			ATTRIBUTE_DOC_ID_CODE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getDocIdCode()) + "," +
			ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getOriginalTransactionType()) + "," +
			ATTRIBUTE_ORIG_MARK_FOR_LOCATION_ID + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigMarkForLocationId()) + "," +
			ATTRIBUTE_ORIG_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(custPoAddressChangeViewBean.getOrigShipToLocationId()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				custPoAddressChangeViewBean.getCompanyId();

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

		Collection custPoAddressChangeViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustPoAddressChangeViewBean custPoAddressChangeViewBean = new CustPoAddressChangeViewBean();
			load(dataSetRow, custPoAddressChangeViewBean);
			custPoAddressChangeViewBeanColl.add(custPoAddressChangeViewBean);
		}

		return custPoAddressChangeViewBeanColl;
	}
}