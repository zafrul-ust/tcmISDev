package com.tcmis.internal.distribution.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestBean;

/******************************************************************************
 * CLASSNAME: CustomerReturnRequestBeanFactory <br>
 * @version: 1.0, Jul 14, 2009 <br>
 *****************************************************************************/
public class CustomerReturnRequestBeanFactory extends BaseBeanFactory {
	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_RMA_ID = "CUSTOMER_RMA_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_QUANTITY_RETURN_REQUESTED = "QUANTITY_RETURN_REQUESTED";
	public String ATTRIBUTE_QUANTITY_RETURN_AUTHORIZED = "QUANTITY_RETURN_AUTHORIZED";
	public String ATTRIBUTE_ORIGINAL_UNIT_PRICE = "ORIGINAL_UNIT_PRICE";
	public String ATTRIBUTE_NEW_UNIT_PRICE = "NEW_UNIT_PRICE";
	public String ATTRIBUTE_RETURN_MATERIAL = "RETURN_MATERIAL";
	public String ATTRIBUTE_WANT_REPLACEMENT_MATERIAL = "WANT_REPLACEMENT_MATERIAL";
	public String ATTRIBUTE_RETURN_REQUESTOR_NAME = "RETURN_REQUESTOR_NAME";
	public String ATTRIBUTE_RMA_STATUS = "RMA_STATUS";
	public String ATTRIBUTE_RETURN_REQUESTOR_PHONE = "RETURN_REQUESTOR_PHONE";
	public String ATTRIBUTE_RETURN_REQUESTOR_EMAIL = "RETURN_REQUESTOR_EMAIL";
	public String ATTRIBUTE_CUSTOMER_SERVICE_REP_ID = "CUSTOMER_SERVICE_REP_ID";
	public String ATTRIBUTE_APPROVER_ID = "APPROVER_ID";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_REQUEST_START_DATE = "REQUEST_START_DATE";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_APPROVAL_DATE = "APPROVAL_DATE";
	public String ATTRIBUTE_REPLACEMENT_CAT_PART_NO = "REPLACEMENT_CAT_PART_NO";
	public String ATTRIBUTE_REJECTION_COMMENT = "REJECTION_COMMENT";
	public String ATTRIBUTE_REPLACEMENT_LINE_ITEM = "REPLACEMENT_LINE_ITEM";
	public String ATTRIBUTE_REPLACEMENT_REQUIRED_DATETIME = "REPLACEMENT_REQUIRED_DATETIME";
	public String ATTRIBUTE_REPLACEMENT_PROMISE_DATE = "REPLACEMENT_PROMISE_DATE";
	public String ATTRIBUTE_RETURN_TYPE = "RETURN_TYPE";
	public String ATTRIBUTE_DATA_TRANSFER_STATUS = "DATA_TRANSFER_STATUS";
	public String ATTRIBUTE_RETURN_NOTES = "RETURN_NOTES";
	public String ATTRIBUTE_CORRECT_ITEM_SHIPPED = "CORRECT_ITEM_SHIPPED";
	public String ATTRIBUTE_DISTRIBUTION_RETURN = "DISTRIBUTION_RETURN";
	
	//table name
	public String TABLE = "CUSTOMER_RETURN_REQUEST";

	//constructor
	public CustomerReturnRequestBeanFactory(DbManager dbManager) {
		super(dbManager);
	}
	
	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("customerRmaId")) {
			return ATTRIBUTE_CUSTOMER_RMA_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("quantityReturnRequested")) {
			return ATTRIBUTE_QUANTITY_RETURN_REQUESTED;
		}
		else if(attributeName.equals("quantityReturnAuthorized")) {
			return ATTRIBUTE_QUANTITY_RETURN_AUTHORIZED;
		}
		else if(attributeName.equals("originalUnitPrice")) {
			return ATTRIBUTE_ORIGINAL_UNIT_PRICE;
		}
		else if(attributeName.equals("newUnitPrice")) {
			return ATTRIBUTE_NEW_UNIT_PRICE;
		}
		else if(attributeName.equals("returnMaterial")) {
			return ATTRIBUTE_RETURN_MATERIAL;
		}
		else if(attributeName.equals("wantReplacementMaterial")) {
			return ATTRIBUTE_WANT_REPLACEMENT_MATERIAL;
		}
		else if(attributeName.equals("returnRequestorName")) {
			return ATTRIBUTE_RETURN_REQUESTOR_NAME;
		}
		else if(attributeName.equals("rmaStatus")) {
			return ATTRIBUTE_RMA_STATUS;
		}
		else if(attributeName.equals("returnRequestorPhone")) {
			return ATTRIBUTE_RETURN_REQUESTOR_PHONE;
		}
		else if(attributeName.equals("returnRequestorEmail")) {
			return ATTRIBUTE_RETURN_REQUESTOR_EMAIL;
		}
		else if(attributeName.equals("customerServiceRepId")) {
			return ATTRIBUTE_CUSTOMER_SERVICE_REP_ID;
		}
		else if(attributeName.equals("approverId")) {
			return ATTRIBUTE_APPROVER_ID;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("requestStartDate")) {
			return ATTRIBUTE_REQUEST_START_DATE;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("approvalDate")) {
			return ATTRIBUTE_APPROVAL_DATE;
		}
		else if(attributeName.equals("replacementCatPartNo")) {
			return ATTRIBUTE_REPLACEMENT_CAT_PART_NO;
		}
		else if(attributeName.equals("rejectionComment")) {
			return ATTRIBUTE_REJECTION_COMMENT;
		}
		else if(attributeName.equals("replacementLineItem")) {
			return ATTRIBUTE_REPLACEMENT_LINE_ITEM;
		}
		else if(attributeName.equals("replacementRequiredDatetime")) {
			return ATTRIBUTE_REPLACEMENT_REQUIRED_DATETIME;
		}
		else if(attributeName.equals("replacementPromiseDate")) {
			return ATTRIBUTE_REPLACEMENT_PROMISE_DATE;
		}
		else if(attributeName.equals("returnType")) {
			return ATTRIBUTE_RETURN_TYPE;
		}
		else if(attributeName.equals("dataTransferStatus")) {
			return ATTRIBUTE_DATA_TRANSFER_STATUS;
		}
		else if (attributeName.equals("returnNotes")) {
			return ATTRIBUTE_RETURN_NOTES;
		}
		else if (attributeName.equals("correctItemShipped")) {
			return ATTRIBUTE_CORRECT_ITEM_SHIPPED;
		}
		else if (attributeName.equals("distributionReturn")) {
			return ATTRIBUTE_DISTRIBUTION_RETURN;
		} else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerReturnRequestBean.class);
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
	public int insert(CustomerReturnRequestBean customerReturnRequestBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
		connection = getDbManager().getConnection();
		result = insert(customerReturnRequestBean, connection);
	}
	finally {
		this.getDbManager().returnConnection(connection);
	}
	return result;
  }
	
	public BigDecimal insertCustomerRequest(CustomerReturnRequestBean customerReturnRequestBean) throws BaseException {
		Connection connection = null;
		BigDecimal rmaId;
		try {
			connection = getDbManager().getConnection();
			rmaId = insertCustomerRequest(customerReturnRequestBean, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		
		return rmaId;
	}
	
	public BigDecimal insertCustomerRequest(CustomerReturnRequestBean customerReturnRequestBean, Connection connection) throws BaseException {
		BigDecimal rmaId = new BigDecimal(getDbManager().getOracleSequence("customer_rma_seq"));
		customerReturnRequestBean.setCustomerRmaId(rmaId);
		insert(customerReturnRequestBean, connection);
			
		return rmaId;
	}
	
	public int insert(CustomerReturnRequestBean customerReturnRequestBean, Connection conn)
		throws BaseException {
	
		SqlManager sqlManager = new SqlManager();
	
		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_RMA_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_QUANTITY_RETURN_REQUESTED + "," +
			ATTRIBUTE_QUANTITY_RETURN_AUTHORIZED + "," +
			ATTRIBUTE_ORIGINAL_UNIT_PRICE + "," +
			ATTRIBUTE_NEW_UNIT_PRICE + "," +
			ATTRIBUTE_RETURN_MATERIAL + "," +
			ATTRIBUTE_WANT_REPLACEMENT_MATERIAL + "," +
			ATTRIBUTE_RETURN_REQUESTOR_NAME + "," +
			ATTRIBUTE_RMA_STATUS + "," +
			ATTRIBUTE_RETURN_REQUESTOR_PHONE + "," +
			ATTRIBUTE_RETURN_REQUESTOR_EMAIL + "," +
			ATTRIBUTE_CUSTOMER_SERVICE_REP_ID + "," +
			ATTRIBUTE_APPROVER_ID + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_REQUEST_START_DATE + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_APPROVAL_DATE + "," +
			ATTRIBUTE_REPLACEMENT_CAT_PART_NO + "," +
			ATTRIBUTE_REJECTION_COMMENT + "," +
			ATTRIBUTE_REPLACEMENT_LINE_ITEM + "," +
			ATTRIBUTE_REPLACEMENT_REQUIRED_DATETIME + "," +
			ATTRIBUTE_REPLACEMENT_PROMISE_DATE + "," +
			//We want to use default value if value is not provided
			(!StringHandler.isBlankString(customerReturnRequestBean.getDataTransferStatus()) ?
				ATTRIBUTE_DATA_TRANSFER_STATUS + ","
				: "") +
			ATTRIBUTE_RETURN_NOTES + "," +
			ATTRIBUTE_CORRECT_ITEM_SHIPPED + "," +
			ATTRIBUTE_RETURN_TYPE + "," +
			ATTRIBUTE_DISTRIBUTION_RETURN + ")" +
			" values (" +
			customerReturnRequestBean.getCustomerRmaId() + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getCompanyId()) + "," +
			customerReturnRequestBean.getPrNumber() + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getLineItem()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getOpsEntityId()) + "," +
			customerReturnRequestBean.getQuantityReturnRequested() + "," +
			customerReturnRequestBean.getQuantityReturnAuthorized() + "," +
			customerReturnRequestBean.getOriginalUnitPrice() + "," +
			customerReturnRequestBean.getNewUnitPrice() + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getReturnMaterial()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getWantReplacementMaterial()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getReturnRequestorName()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getRmaStatus()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getReturnRequestorPhone()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getReturnRequestorEmail()) + "," +
			customerReturnRequestBean.getCustomerServiceRepId() + "," +
			customerReturnRequestBean.getApproverId() + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getCatalogCompanyId()) + "," +
			DateHandler.getOracleToDateFunction(customerReturnRequestBean.getRequestStartDate()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getCatalogId()) + "," +
			DateHandler.getOracleToDateFunction(customerReturnRequestBean.getApprovalDate()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getReplacementCatPartNo()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getRejectionComment()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getReplacementLineItem()) + "," +
			DateHandler.getOracleToDateFunction(customerReturnRequestBean.getReplacementRequiredDatetime()) + "," +
			DateHandler.getOracleToDateFunction(customerReturnRequestBean.getReplacementPromiseDate()) + "," +
			(!StringHandler.isBlankString(customerReturnRequestBean.getDataTransferStatus()) ?
				SqlHandler.delimitString(customerReturnRequestBean.getDataTransferStatus()) + ","
				: "") +
			SqlHandler.delimitString(customerReturnRequestBean.getReturnNotes()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getCorrectItemShipped()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getReturnType()) + "," +
			SqlHandler.delimitString(customerReturnRequestBean.getDistributionReturn()) +  ")";
	
		return sqlManager.update(conn, query);
	}
	
	//update
	public int update(CustomerReturnRequestBean customerReturnRequestBean)
		throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerReturnRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}
	
	public int update(CustomerReturnRequestBean customerReturnRequestBean, Connection conn)
	throws BaseException {
		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_QUANTITY_RETURN_REQUESTED + "=" + 
				StringHandler.nullIfZero(customerReturnRequestBean.getQuantityReturnRequested()) + "," +
			ATTRIBUTE_QUANTITY_RETURN_AUTHORIZED + "=" + 
				StringHandler.nullIfZero(customerReturnRequestBean.getQuantityReturnAuthorized()) + "," +
			ATTRIBUTE_ORIGINAL_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(customerReturnRequestBean.getOriginalUnitPrice()) + "," +
			ATTRIBUTE_NEW_UNIT_PRICE + "=" + 
				customerReturnRequestBean.getNewUnitPrice() + "," +
			ATTRIBUTE_RETURN_MATERIAL + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getReturnMaterial()) + "," +
			ATTRIBUTE_WANT_REPLACEMENT_MATERIAL + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getWantReplacementMaterial()) + "," +
			ATTRIBUTE_RETURN_REQUESTOR_NAME + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getReturnRequestorName()) + "," +
			ATTRIBUTE_RMA_STATUS + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getRmaStatus()) + "," +
			ATTRIBUTE_RETURN_REQUESTOR_PHONE + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getReturnRequestorPhone()) + "," +
			ATTRIBUTE_RETURN_REQUESTOR_EMAIL + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getReturnRequestorEmail()) + "," +
			ATTRIBUTE_APPROVER_ID + "=" + 
				StringHandler.nullIfZero(customerReturnRequestBean.getApproverId()) + "," +
			ATTRIBUTE_APPROVAL_DATE + "=" + 
				DateHandler.getOracleToDateFunction(customerReturnRequestBean.getApprovalDate()) + "," +
			ATTRIBUTE_REPLACEMENT_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getReplacementCatPartNo()) + "," +
			ATTRIBUTE_REJECTION_COMMENT + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getRejectionComment()) + "," +
			ATTRIBUTE_REPLACEMENT_LINE_ITEM + "=" + 
				SqlHandler.delimitString(customerReturnRequestBean.getReplacementLineItem()) + "," +
			ATTRIBUTE_REPLACEMENT_REQUIRED_DATETIME + "=" + 
				DateHandler.getOracleToDateFunction(customerReturnRequestBean.getReplacementRequiredDatetime()) + "," +
			ATTRIBUTE_REPLACEMENT_PROMISE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(customerReturnRequestBean.getReplacementPromiseDate()) + " " +
			"where " + ATTRIBUTE_CUSTOMER_RMA_ID + "=" +
				customerReturnRequestBean.getCustomerRmaId();
	
		return new SqlManager().update(conn, query);
	}

	//select
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {
		Collection customerReturnRequestBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerReturnRequestBean customerReturnRequestBean = new CustomerReturnRequestBean();
			load(dataSetRow, customerReturnRequestBean);
			customerReturnRequestBeanColl.add(customerReturnRequestBean);
		}

		return customerReturnRequestBeanColl;
	}
}