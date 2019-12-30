package com.tcmis.client.dla.factory;


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
import com.tcmis.client.dla.beans.DlaIntOrderCurrentViewBean;


/******************************************************************************
 * CLASSNAME: DlaIntOrderCurrentViewBeanFactory <br>
 * @version: 1.0, Aug 5, 2008 <br>
 *****************************************************************************/


public class DlaIntOrderCurrentViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CONTRACT_NUMBER = "CONTRACT_NUMBER";
	public String ATTRIBUTE_CALL_NO = "CALL_NO";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_SHIP_TO_INFO = "SHIP_TO_INFO";
	public String ATTRIBUTE_MARK_FOR_DODAAC = "MARK_FOR_DODAAC";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_TRANSPORTATION_CONTROL_NO = "TRANSPORTATION_CONTROL_NO";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_TRACKING_NO = "TRACKING_NO";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_DOC_NUM_WITH_SUFFIX = "DOC_NUM_WITH_SUFFIX";
	public String ATTRIBUTE_DISPLAY_DETAIL_STATUS = "DISPLAY_DETAIL_STATUS";
	public String ATTRIBUTE_DISPLAY_QUANTITY_SHIPPED = "DISPLAY_QUANTITY_SHIPPED";
	public String ATTRIBUTE_DISPLAY_QUANTITY_OPEN = "DISPLAY_QUANTITY_OPEN";
	public String ATTRIBUTE_DISPLAY_PROJECTED_DATE_SHIPPED = "DISPLAY_PROJECTED_DATE_SHIPPED";
	public String ATTRIBUTE_DISPLAY_ACTUAL_SHIP_DATE = "DISPLAY_ACTUAL_SHIP_DATE";
	public String ATTRIBUTE_SHIP_FROM = "SHIP_FROM";
	public String ATTRIBUTE_REPORT_DATE = "REPORT_DATE";
	public String ATTRIBUTE_ISSUER = "ISSUER";
	public String ATTRIBUTE_EDI_856_TO_DLA_ELLIS = "EDI_856_TO_DLA_ELLIS";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_VIA_LOCATION_ID = "SHIP_VIA_LOCATION_ID";
	public String ATTRIBUTE_DATE_997 = "DATE_997";
	public String ATTRIBUTE_EDI_850_TO_AIRGAS = "EDI_850_TO_AIRGAS";
	public String ATTRIBUTE_AIRGAS_997_RECEIVED = "AIRGAS_997_RECEIVED";
	public String ATTRIBUTE_PO_CONFIRM_DATE = "PO_CONFIRM_DATE";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_CODE = "SHIP_TO_ADDRESS_CODE";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";

  //table name
	public String TABLE = "DLA_INT_ORDER_CURRENT_VIEW";


	//constructor
	public DlaIntOrderCurrentViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("contractNumber")) {
			return ATTRIBUTE_CONTRACT_NUMBER;
		}
		else if(attributeName.equals("callNo")) {
			return ATTRIBUTE_CALL_NO;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("shipToInfo")) {
			return ATTRIBUTE_SHIP_TO_INFO;
		}
		else if(attributeName.equals("markForDodaac")) {
			return ATTRIBUTE_MARK_FOR_DODAAC;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("transportationControlNo")) {
			return ATTRIBUTE_TRANSPORTATION_CONTROL_NO;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("trackingNo")) {
			return ATTRIBUTE_TRACKING_NO;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("docNumWithSuffix")) {
			return ATTRIBUTE_DOC_NUM_WITH_SUFFIX;
		}
		else if(attributeName.equals("displayDetailStatus")) {
			return ATTRIBUTE_DISPLAY_DETAIL_STATUS;
		}
		else if(attributeName.equals("displayQuantityShipped")) {
			return ATTRIBUTE_DISPLAY_QUANTITY_SHIPPED;
		}
		else if(attributeName.equals("displayQuantityOpen")) {
			return ATTRIBUTE_DISPLAY_QUANTITY_OPEN;
		}
		else if(attributeName.equals("displayProjectedDateShipped")) {
			return ATTRIBUTE_DISPLAY_PROJECTED_DATE_SHIPPED;
		}
		else if(attributeName.equals("displayActualShipDate")) {
			return ATTRIBUTE_DISPLAY_ACTUAL_SHIP_DATE;
		}
		else if(attributeName.equals("shipFrom")) {
			return ATTRIBUTE_SHIP_FROM;
		}
		else if(attributeName.equals("reportDate")) {
			return ATTRIBUTE_REPORT_DATE;
		}
		else if(attributeName.equals("issuer")) {
			return ATTRIBUTE_ISSUER;
		}
		else if(attributeName.equals("edi856ToDlaEllis")) {
			return ATTRIBUTE_EDI_856_TO_DLA_ELLIS;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipViaLocationId")) {
			return ATTRIBUTE_SHIP_VIA_LOCATION_ID;
		}
		else if(attributeName.equals("date997")) {
			return ATTRIBUTE_DATE_997;
		}
		else if(attributeName.equals("edi850ToAirgas")) {
			return ATTRIBUTE_EDI_850_TO_AIRGAS;
		}
		else if(attributeName.equals("airgas997Received")) {
			return ATTRIBUTE_AIRGAS_997_RECEIVED;
		}
		else if(attributeName.equals("poConfirmDate")) {
			return ATTRIBUTE_PO_CONFIRM_DATE;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("shipToAddressCode")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_CODE;
		}
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DlaIntOrderCurrentViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DlaIntOrderCurrentViewBean dlaIntOrderCurrentViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("contractNumber", "SearchCriterion.EQUALS",
			"" + dlaIntOrderCurrentViewBean.getContractNumber());

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


	public int delete(DlaIntOrderCurrentViewBean dlaIntOrderCurrentViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("contractNumber", "SearchCriterion.EQUALS",
			"" + dlaIntOrderCurrentViewBean.getContractNumber());

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
	public int insert(DlaIntOrderCurrentViewBean dlaIntOrderCurrentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dlaIntOrderCurrentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DlaIntOrderCurrentViewBean dlaIntOrderCurrentViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CONTRACT_NUMBER + "," +
			ATTRIBUTE_CALL_NO + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_SHIP_TO_INFO + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NO + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_TRACKING_NO + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_DOC_NUM_WITH_SUFFIX + "," +
			ATTRIBUTE_DISPLAY_DETAIL_STATUS + "," +
			ATTRIBUTE_DISPLAY_QUANTITY_SHIPPED + "," +
			ATTRIBUTE_DISPLAY_QUANTITY_OPEN + "," +
			ATTRIBUTE_DISPLAY_PROJECTED_DATE_SHIPPED + "," +
			ATTRIBUTE_DISPLAY_ACTUAL_SHIP_DATE + "," +
			ATTRIBUTE_SHIP_FROM + "," +
			ATTRIBUTE_REPORT_DATE + "," +
			ATTRIBUTE_ISSUER + "," +
			ATTRIBUTE_EDI_856_TO_DLA_ELLIS + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "," +
			ATTRIBUTE_DATE_997 + "," +
			ATTRIBUTE_EDI_850_TO_AIRGAS + "," +
			ATTRIBUTE_AIRGAS_997_RECEIVED + "," +
			ATTRIBUTE_PO_CONFIRM_DATE + "," +
			ATTRIBUTE_COMMENTS + ")" +
			" values (" +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getContractNumber()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getCallNo()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipToInfo()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getMarkForDodaac()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getCatPartNo()) + "," +
			dlaIntOrderCurrentViewBean.getQuantity() + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getTransportationControlNo()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getCarrier()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getTrackingNo()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getPartShortName()) + "," +
			dlaIntOrderCurrentViewBean.getRadianPo() + "," +
			dlaIntOrderCurrentViewBean.getPoLine() + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDocNumWithSuffix()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDisplayDetailStatus()) + "," +
			dlaIntOrderCurrentViewBean.getDisplayQuantityShipped() + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDisplayQuantityOpen()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDisplayProjectedDateShipped()) + "," +
			DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getDisplayActualShipDate()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipFrom()) + "," +
			DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getReportDate()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getIssuer()) + "," +
			DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getEdi856ToDlaEllis()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipViaLocationId()) + "," +
			DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getDate997()) + "," +
			DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getEdi850ToAirgas()) + "," +
			DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getAirgas997Received()) + "," +
			DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getPoConfirmDate()) + "," +
			SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getComments()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DlaIntOrderCurrentViewBean dlaIntOrderCurrentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dlaIntOrderCurrentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DlaIntOrderCurrentViewBean dlaIntOrderCurrentViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CONTRACT_NUMBER + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getContractNumber()) + "," +
			ATTRIBUTE_CALL_NO + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getCallNo()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_TO_INFO + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipToInfo()) + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getMarkForDodaac()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaIntOrderCurrentViewBean.getQuantity()) + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NO + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getTransportationControlNo()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getCarrier()) + "," +
			ATTRIBUTE_TRACKING_NO + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getTrackingNo()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getPartShortName()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dlaIntOrderCurrentViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(dlaIntOrderCurrentViewBean.getPoLine()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getSupplierName()) + "," +
			ATTRIBUTE_DOC_NUM_WITH_SUFFIX + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDocNumWithSuffix()) + "," +
			ATTRIBUTE_DISPLAY_DETAIL_STATUS + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDisplayDetailStatus()) + "," +
			ATTRIBUTE_DISPLAY_QUANTITY_SHIPPED + "=" + 
				StringHandler.nullIfZero(dlaIntOrderCurrentViewBean.getDisplayQuantityShipped()) + "," +
			ATTRIBUTE_DISPLAY_QUANTITY_OPEN + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDisplayQuantityOpen()) + "," +
			ATTRIBUTE_DISPLAY_PROJECTED_DATE_SHIPPED + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getDisplayProjectedDateShipped()) + "," +
			ATTRIBUTE_DISPLAY_ACTUAL_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getDisplayActualShipDate()) + "," +
			ATTRIBUTE_SHIP_FROM + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipFrom()) + "," +
			ATTRIBUTE_REPORT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getReportDate()) + "," +
			ATTRIBUTE_ISSUER + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getIssuer()) + "," +
			ATTRIBUTE_EDI_856_TO_DLA_ELLIS + "=" + 
				DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getEdi856ToDlaEllis()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_DATE_997 + "=" + 
				DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getDate997()) + "," +
			ATTRIBUTE_EDI_850_TO_AIRGAS + "=" + 
				DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getEdi850ToAirgas()) + "," +
			ATTRIBUTE_AIRGAS_997_RECEIVED + "=" + 
				DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getAirgas997Received()) + "," +
			ATTRIBUTE_PO_CONFIRM_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaIntOrderCurrentViewBean.getPoConfirmDate()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(dlaIntOrderCurrentViewBean.getComments()) + " " +
			"where " + ATTRIBUTE_CONTRACT_NUMBER + "=" +
				dlaIntOrderCurrentViewBean.getContractNumber();

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

		Collection dlaIntOrderCurrentViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DlaIntOrderCurrentViewBean dlaIntOrderCurrentViewBean = new DlaIntOrderCurrentViewBean();
			load(dataSetRow, dlaIntOrderCurrentViewBean);
			dlaIntOrderCurrentViewBeanColl.add(dlaIntOrderCurrentViewBean);
		}

		return dlaIntOrderCurrentViewBeanColl;
	}
}