package com.tcmis.internal.supply.factory;


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
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.supply.beans.PoBean;


/******************************************************************************
 * CLASSNAME: PoBeanFactory <br>
 * @version: 1.0, Aug 11, 2008 <br>
 *****************************************************************************/


public class PoBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_BPO = "BPO";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_CONTACT_ID = "SUPPLIER_CONTACT_ID";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_DATE_ACCEPTED = "DATE_ACCEPTED";
	public String ATTRIBUTE_TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_DATE_CREATED = "DATE_CREATED";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_QUALIFICATION_LEVEL = "QUALIFICATION_LEVEL";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_FOLLOWUP_EMAILS = "FOLLOWUP_EMAILS";
	public String ATTRIBUTE_CONSIGNED_PO = "CONSIGNED_PO";
	public String ATTRIBUTE_DBUY_CONTRACT_ID = "DBUY_CONTRACT_ID";
	public String ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER = "EDI_DOCUMENT_CONTROL_NUMBER";
	public String ATTRIBUTE_DATE_ACKNOWLEDGEMENT = "DATE_ACKNOWLEDGEMENT";
	public String ATTRIBUTE_DBUY_STATUS = "DBUY_STATUS";
	public String ATTRIBUTE_DBUY_STATUS_SET_DATE = "DBUY_STATUS_SET_DATE";
	public String ATTRIBUTE_DBUY_USER_ID = "DBUY_USER_ID";
	public String ATTRIBUTE_BUYER_COMPANY_ID = "BUYER_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_FACILITY_ID = "SHIP_TO_FACILITY_ID";
	public String ATTRIBUTE_EDI_INTERCHANGE_CONTROL_NUMBER = "EDI_INTERCHANGE_CONTROL_NUMBER";
	public String ATTRIBUTE_EDI_TRADING_PARTNER_ID = "EDI_TRADING_PARTNER_ID";

	//table name
	public String TABLE = "PO";


	//constructor
	public PoBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("bpo")) {
			return ATTRIBUTE_BPO;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierContactId")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_ID;
		}
		else if(attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("freightOnBoard")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("dateAccepted")) {
			return ATTRIBUTE_DATE_ACCEPTED;
		}
		else if(attributeName.equals("termsAndConditions")) {
			return ATTRIBUTE_TERMS_AND_CONDITIONS;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("dateCreated")) {
			return ATTRIBUTE_DATE_CREATED;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("qualificationLevel")) {
			return ATTRIBUTE_QUALIFICATION_LEVEL;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("followupEmails")) {
			return ATTRIBUTE_FOLLOWUP_EMAILS;
		}
		else if(attributeName.equals("consignedPo")) {
			return ATTRIBUTE_CONSIGNED_PO;
		}
		else if(attributeName.equals("dbuyContractId")) {
			return ATTRIBUTE_DBUY_CONTRACT_ID;
		}
		else if(attributeName.equals("ediDocumentControlNumber")) {
			return ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER;
		}
		else if(attributeName.equals("dateAcknowledgement")) {
			return ATTRIBUTE_DATE_ACKNOWLEDGEMENT;
		}
		else if(attributeName.equals("dbuyStatus")) {
			return ATTRIBUTE_DBUY_STATUS;
		}
		else if(attributeName.equals("dbuyStatusSetDate")) {
			return ATTRIBUTE_DBUY_STATUS_SET_DATE;
		}
		else if(attributeName.equals("dbuyUserId")) {
			return ATTRIBUTE_DBUY_USER_ID;
		}
		else if(attributeName.equals("buyerCompanyId")) {
			return ATTRIBUTE_BUYER_COMPANY_ID;
		}
		else if(attributeName.equals("shipToFacilityId")) {
			return ATTRIBUTE_SHIP_TO_FACILITY_ID;
		}
		else if(attributeName.equals("ediInterchangeControlNumber")) {
			return ATTRIBUTE_EDI_INTERCHANGE_CONTROL_NUMBER;
		}
		else if(attributeName.equals("ediTradingPartnerId")) {
			return ATTRIBUTE_EDI_TRADING_PARTNER_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
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


	public int createPo(String sql)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = createPo(sql, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int createPo(String sql, Connection conn)
		throws BaseException {

		return new SqlManager().update(conn, sql);
	}

	public BigDecimal getPoNextSeq()
		throws BaseException {

		Connection connection = null;
		BigDecimal c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = getPoNextSeq( connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public BigDecimal getPoNextSeq(Connection conn)
		throws BaseException {

		BigDecimal poNumber = null;

		String query = "select po_seq.nextval po_number from dual";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			poNumber = dataSetRow.getBigDecimal("PO_NUMBER");
		}

		return poNumber;
	}

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

		Collection poBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoBean poBean = new PoBean();
			load(dataSetRow, poBean);
			poBeanColl.add(poBean);
		}

		return poBeanColl;
	}
}