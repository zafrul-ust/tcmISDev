package com.tcmis.client.swa.factory;


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
import com.tcmis.client.swa.beans.MatlIctBean;


/******************************************************************************
 * CLASSNAME: MatlIctBeanFactory <br>
 * @version: 1.0, Apr 19, 2007 <br>
 *****************************************************************************/


public class MatlIctBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ISSUE_DOCUMENT_NUMBER = "ISSUE_DOCUMENT_NUMBER";
	public String ATTRIBUTE_DOCUMENT_TYPE = "DOCUMENT_TYPE";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_MANF_PART_NUMBER = "MANF_PART_NUMBER";
	public String ATTRIBUTE_NUMBER_REQUESTED = "NUMBER_REQUESTED";
	public String ATTRIBUTE_NUMBER_ISSUED = "NUMBER_ISSUED";
	public String ATTRIBUTE_REQUESTING_EMPLOYEE_ID = "REQUESTING_EMPLOYEE_ID";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_ISSUE_CREDIT_TRANSFER_DATE = "ISSUE_CREDIT_TRANSFER_DATE";
	public String ATTRIBUTE_ACN = "ACN";
	public String ATTRIBUTE_SHIPPING_STATION = "SHIPPING_STATION";
	public String ATTRIBUTE_SHIPPING_DEPARTMENT = "SHIPPING_DEPARTMENT";
	public String ATTRIBUTE_SHIPMENT_DATE = "SHIPMENT_DATE";
	public String ATTRIBUTE_RECEIVING_STATION = "RECEIVING_STATION";
	public String ATTRIBUTE_RECEIVING_DEPARTMENT = "RECEIVING_DEPARTMENT";
	public String ATTRIBUTE_TCM_LOAD_DATE = "TCM_LOAD_DATE";
	public String ATTRIBUTE_FILE_NAME = "FILE_NAME";
	public String ATTRIBUTE_VERIFICATION_CODE = "VERIFICATION_CODE";
	public String ATTRIBUTE_PROCESSED_DATE = "PROCESSED_DATE";
	public String ATTRIBUTE_CUMULATIVE_RECEIVED = "CUMULATIVE_RECEIVED";
	public String ATTRIBUTE_CHEMICAL_FLAG = "CHEMICAL_FLAG";
	public String ATTRIBUTE_END_USE_DATETIME = "END_USE_DATETIME";
	public String ATTRIBUTE_END_USE_COMPLETE = "END_USE_COMPLETE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_LAST_MOD = "LAST_MOD";
	public String ATTRIBUTE_ITEM_QTY_ISSUED = "ITEM_QTY_ISSUED";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_CATEGORY = "CATEGORY";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_LOCATION = "LOCATION";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_EMISSION_FACTOR = "EMISSION_FACTOR";
	public String ATTRIBUTE_VOLUME_GAL = "VOLUME_GAL";

	//table name
	public String TABLE = "MATL_ICT";


	//constructor
	public MatlIctBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("issueDocumentNumber")) {
			return ATTRIBUTE_ISSUE_DOCUMENT_NUMBER;
		}
		else if(attributeName.equals("documentType")) {
			return ATTRIBUTE_DOCUMENT_TYPE;
		}
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("manfPartNumber")) {
			return ATTRIBUTE_MANF_PART_NUMBER;
		}
		else if(attributeName.equals("numberRequested")) {
			return ATTRIBUTE_NUMBER_REQUESTED;
		}
		else if(attributeName.equals("numberIssued")) {
			return ATTRIBUTE_NUMBER_ISSUED;
		}
		else if(attributeName.equals("requestingEmployeeId")) {
			return ATTRIBUTE_REQUESTING_EMPLOYEE_ID;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("issueCreditTransferDate")) {
			return ATTRIBUTE_ISSUE_CREDIT_TRANSFER_DATE;
		}
		else if(attributeName.equals("acn")) {
			return ATTRIBUTE_ACN;
		}
		else if(attributeName.equals("shippingStation")) {
			return ATTRIBUTE_SHIPPING_STATION;
		}
		else if(attributeName.equals("shippingDepartment")) {
			return ATTRIBUTE_SHIPPING_DEPARTMENT;
		}
		else if(attributeName.equals("shipmentDate")) {
			return ATTRIBUTE_SHIPMENT_DATE;
		}
		else if(attributeName.equals("receivingStation")) {
			return ATTRIBUTE_RECEIVING_STATION;
		}
		else if(attributeName.equals("receivingDepartment")) {
			return ATTRIBUTE_RECEIVING_DEPARTMENT;
		}
		else if(attributeName.equals("tcmLoadDate")) {
			return ATTRIBUTE_TCM_LOAD_DATE;
		}
		else if(attributeName.equals("fileName")) {
			return ATTRIBUTE_FILE_NAME;
		}
		else if(attributeName.equals("verificationCode")) {
			return ATTRIBUTE_VERIFICATION_CODE;
		}
		else if(attributeName.equals("processedDate")) {
			return ATTRIBUTE_PROCESSED_DATE;
		}
		else if(attributeName.equals("cumulativeReceived")) {
			return ATTRIBUTE_CUMULATIVE_RECEIVED;
		}
		else if(attributeName.equals("chemicalFlag")) {
			return ATTRIBUTE_CHEMICAL_FLAG;
		}
		else if(attributeName.equals("endUseDatetime")) {
			return ATTRIBUTE_END_USE_DATETIME;
		}
		else if(attributeName.equals("endUseComplete")) {
			return ATTRIBUTE_END_USE_COMPLETE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("lastMod")) {
			return ATTRIBUTE_LAST_MOD;
		}
		else if(attributeName.equals("itemQtyIssued")) {
			return ATTRIBUTE_ITEM_QTY_ISSUED;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("category")) {
			return ATTRIBUTE_CATEGORY;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("location")) {
			return ATTRIBUTE_LOCATION;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("emissionFactor")) {
			return ATTRIBUTE_EMISSION_FACTOR;
		}
		else if(attributeName.equals("volumeGal")) {
			return ATTRIBUTE_VOLUME_GAL;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MatlIctBean.class);
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

	//insert
	public int insert(MatlIctBean matlIctBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(matlIctBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(MatlIctBean matlIctBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ISSUE_DOCUMENT_NUMBER + "," +
			ATTRIBUTE_DOCUMENT_TYPE + "," +
			ATTRIBUTE_PART_NUMBER + "," +
			ATTRIBUTE_MANF_PART_NUMBER + "," +
			ATTRIBUTE_NUMBER_REQUESTED + "," +
			ATTRIBUTE_NUMBER_ISSUED + "," +
			ATTRIBUTE_REQUESTING_EMPLOYEE_ID + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_ISSUE_CREDIT_TRANSFER_DATE + "," +
			ATTRIBUTE_ACN + "," +
			ATTRIBUTE_SHIPPING_STATION + "," +
			ATTRIBUTE_SHIPPING_DEPARTMENT + "," +
			ATTRIBUTE_SHIPMENT_DATE + "," +
			ATTRIBUTE_RECEIVING_STATION + "," +
			ATTRIBUTE_RECEIVING_DEPARTMENT + "," +
			ATTRIBUTE_TCM_LOAD_DATE + "," +
			ATTRIBUTE_FILE_NAME + "," +
			ATTRIBUTE_VERIFICATION_CODE + "," +
			ATTRIBUTE_PROCESSED_DATE + "," +
			ATTRIBUTE_CUMULATIVE_RECEIVED + "," +
			ATTRIBUTE_CHEMICAL_FLAG + "," +
			ATTRIBUTE_END_USE_DATETIME + "," +
			ATTRIBUTE_END_USE_COMPLETE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_LAST_MOD + "," +
			ATTRIBUTE_ITEM_QTY_ISSUED + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_CATEGORY + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_LOCATION + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_EMISSION_FACTOR + "," +
			ATTRIBUTE_VOLUME_GAL + ")" +
			" values (" +
			matlIctBean.getIssueDocumentNumber() + "," +
			SqlHandler.delimitString(matlIctBean.getDocumentType()) + "," +
			SqlHandler.delimitString(matlIctBean.getPartNumber()) + "," +
			SqlHandler.delimitString(matlIctBean.getManfPartNumber()) + "," +
			matlIctBean.getNumberRequested() + "," +
			matlIctBean.getNumberIssued() + "," +
			matlIctBean.getRequestingEmployeeId() + "," +
			matlIctBean.getStatus() + "," +
			DateHandler.getOracleToDateFunction(matlIctBean.getIssueCreditTransferDate()) + "," +
			SqlHandler.delimitString(matlIctBean.getAcn()) + "," +
			SqlHandler.delimitString(matlIctBean.getShippingStation()) + "," +
			SqlHandler.delimitString(matlIctBean.getShippingDepartment()) + "," +
			DateHandler.getOracleToDateFunction(matlIctBean.getShipmentDate()) + "," +
			SqlHandler.delimitString(matlIctBean.getReceivingStation()) + "," +
			SqlHandler.delimitString(matlIctBean.getReceivingDepartment()) + "," +
			DateHandler.getOracleToDateFunction(matlIctBean.getTcmLoadDate()) + "," +
			SqlHandler.delimitString(matlIctBean.getFileName()) + "," +
			matlIctBean.getVerificationCode() + "," +
			DateHandler.getOracleToDateFunction(matlIctBean.getProcessedDate()) + "," +
			matlIctBean.getCumulativeReceived() + "," +
			SqlHandler.delimitString(matlIctBean.getChemicalFlag()) + "," +
			DateHandler.getOracleToDateFunction(matlIctBean.getEndUseDatetime()) + "," +
			SqlHandler.delimitString(matlIctBean.getEndUseComplete()) + "," +
			matlIctBean.getItemId() + "," +
			DateHandler.getOracleToDateFunction(matlIctBean.getLastMod()) + "," +
			matlIctBean.getItemQtyIssued() + "," +
			SqlHandler.delimitString(matlIctBean.getFacilityId()) + "," +
			SqlHandler.delimitString(matlIctBean.getCategory()) + "," +
			SqlHandler.delimitString(matlIctBean.getApplication()) + "," +
			SqlHandler.delimitString(matlIctBean.getLocation()) + "," +
			SqlHandler.delimitString(matlIctBean.getPartDescription()) + "," +
			matlIctBean.getEmissionFactor() + "," +
			matlIctBean.getVolumeGal() + ")";

		return sqlManager.update(conn, query);
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

		Collection matlIctBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MatlIctBean matlIctBean = new MatlIctBean();
			load(dataSetRow, matlIctBean);
			matlIctBeanColl.add(matlIctBean);
		}

		return matlIctBeanColl;
	}
}