package com.tcmis.internal.hub.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.hub.beans.CustomerInventoryToReceiveBean;
import com.tcmis.client.common.beans.OwnerSegmentBean;


/******************************************************************************
 * CLASSNAME: CustomerInventoryToReceiveBeanFactory <br>
 * @version: 1.0, Sep 5, 2008 <br>
 *****************************************************************************/


public class CustomerInventoryToReceiveBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_QUANTITY_TO_RECEIVE = "QUANTITY_TO_RECEIVE";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";
	public String ATTRIBUTE_EXPECTED_DELIVERY_DATE = "EXPECTED_DELIVERY_DATE";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_TOTAL_QUANTITY_RECEIVED = "TOTAL_QUANTITY_RECEIVED";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
	public String ATTRIBUTE_CUSTOMER_PO_LINE = "CUSTOMER_PO_LINE";
	public String ATTRIBUTE_DOC_NUM = "DOC_NUM";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_DATE_LAST_UPDATED = "DATE_LAST_UPDATED";
	public String ATTRIBUTE_LAST_UPDATED_BY = "LAST_UPDATED_BY";
	public String ATTRIBUTE_UPDATED_BY_NAME = "UPDATED_BY_NAME";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_CLAUSES_SET_1 = "CLAUSES_SET_1";
	public String ATTRIBUTE_CLAUSES_SET_2 = "CLAUSES_SET_2";
	public String ATTRIBUTE_CLAUSES_SET_3 = "CLAUSES_SET_3";
	public String ATTRIBUTE_CLAUSES_SET_4 = "CLAUSES_SET_4";
	public String ATTRIBUTE_CLAUSES_SET_5 = "CLAUSES_SET_5";
	public String ATTRIBUTE_SUPPLIER_CODE = "SUPPLIER_CODE";
	public String ATTRIBUTE_MSDS_ID = "MSDS_ID";
	public String ATTRIBUTE_ENTERED_BY = "ENTERED_BY";
	public String ATTRIBUTE_ENTERED_BY_NAME = "ENTERED_BY_NAME";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_OWNER_SEGMENT_ID = "OWNER_SEGMENT_ID";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
	public String ATTRIBUTE_ACCOUNT_NUMBER3 = "ACCOUNT_NUMBER3";
	public String ATTRIBUTE_ACCOUNT_NUMBER4 = "ACCOUNT_NUMBER4";
	public String ATTRIBUTE_CUSTOMER_RECEIPT_ID = "CUSTOMER_RECEIPT_ID";
	public String ATTRIBUTE_QUALITY_TRACKING_NUMBER = "QUALITY_TRACKING_NUMBER";
	public String ATTRIBUTE_OWNER_SEGMENT_DESC = "OWNER_SEGMENT_DESC";
	public String ATTRIBUTE_AUTOMATED_PUTAWAY = "AUTOMATED_PUTAWAY";
	public String ATTRIBUTE_DATA_TRANSFER_STATUS = "DATA_TRANSFER_STATUS";

	//table name
	public String TABLE = "CUSTOMER_INV_TO_RECEIVE_VIEW";
	public String  UPD_TABLE = "CUSTOMER_INVENTORY_TO_RECEIVE";

	//constructor
	public CustomerInventoryToReceiveBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("ownerCompanyId")) {
			return ATTRIBUTE_OWNER_COMPANY_ID;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("quantityToReceive")) {
			return ATTRIBUTE_QUANTITY_TO_RECEIVE;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("dateInserted")) {
			return ATTRIBUTE_DATE_INSERTED;
		}
		else if(attributeName.equals("expectedDeliveryDate")) {
			return ATTRIBUTE_EXPECTED_DELIVERY_DATE;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("totalQuantityReceived")) {
			return ATTRIBUTE_TOTAL_QUANTITY_RECEIVED;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else if(attributeName.equals("customerPoLine")) {
			return ATTRIBUTE_CUSTOMER_PO_LINE;
		}
		else if(attributeName.equals("docNum")) {
			return ATTRIBUTE_DOC_NUM;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("buyerId")) {
			return ATTRIBUTE_BUYER_ID;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("dateLastUpdated")) {
			return ATTRIBUTE_DATE_LAST_UPDATED;
		}
		else if(attributeName.equals("lastUpdatedBy")) {
			return ATTRIBUTE_LAST_UPDATED_BY;
		}
		else if(attributeName.equals("updatedByName")) {
			return ATTRIBUTE_UPDATED_BY_NAME;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("clausesSet11")) {
			return ATTRIBUTE_CLAUSES_SET_1;
		}
		else if(attributeName.equals("clausesSet22")) {
			return ATTRIBUTE_CLAUSES_SET_2;
		}
		else if(attributeName.equals("clausesSet33")) {
			return ATTRIBUTE_CLAUSES_SET_3;
		}
		else if(attributeName.equals("clausesSet44")) {
			return ATTRIBUTE_CLAUSES_SET_4;
		}
		else if(attributeName.equals("clausesSet55")) {
			return ATTRIBUTE_CLAUSES_SET_5;
		}
		else if(attributeName.equals("supplierCode")) {
			return ATTRIBUTE_SUPPLIER_CODE;
		}
		else if(attributeName.equals("msdsId")) {
			return ATTRIBUTE_MSDS_ID;
		}
		else if(attributeName.equals("enteredBy")) {
			return ATTRIBUTE_ENTERED_BY;
		}
		else if(attributeName.equals("enteredByName")) {
			return ATTRIBUTE_ENTERED_BY_NAME;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("ownerSegmentId")) {
			return ATTRIBUTE_OWNER_SEGMENT_ID;
		}
		else if(attributeName.equals("accountNumber")) {
			return ATTRIBUTE_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("accountNumber2")) {
			return ATTRIBUTE_ACCOUNT_NUMBER2;
		}
		else if(attributeName.equals("accountNumber3")) {
			return ATTRIBUTE_ACCOUNT_NUMBER3;
		}
		else if(attributeName.equals("accountNumber4")) {
			return ATTRIBUTE_ACCOUNT_NUMBER4;
		}
		else if(attributeName.equals("customerReceiptId")) {
			return ATTRIBUTE_CUSTOMER_RECEIPT_ID;
		}
		else if(attributeName.equals("qualityTrackingNumber")) {
			return ATTRIBUTE_QUALITY_TRACKING_NUMBER;
		}
		else if(attributeName.equals("ownerSegmentDesc")) {
			return ATTRIBUTE_OWNER_SEGMENT_DESC;
		}
		else if(attributeName.equals("automatedPutaway")) {
			return ATTRIBUTE_AUTOMATED_PUTAWAY;
		}
		else if(attributeName.equals("dataTransferStatus")) {
			return ATTRIBUTE_DATA_TRANSFER_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerInventoryToReceiveBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustomerInventoryToReceiveBean customerInventoryToReceiveBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("ownerCompanyId", "SearchCriterion.EQUALS",
			"" + customerInventoryToReceiveBean.getOwnerCompanyId());

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


	public int delete(CustomerInventoryToReceiveBean customerInventoryToReceiveBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("ownerCompanyId", "SearchCriterion.EQUALS",
			"" + customerInventoryToReceiveBean.getOwnerCompanyId());

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
	public int insert(CustomerInventoryToReceiveBean customerInventoryToReceiveBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerInventoryToReceiveBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustomerInventoryToReceiveBean customerInventoryToReceiveBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_OWNER_COMPANY_ID + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_QUANTITY_TO_RECEIVE + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_DATE_INSERTED + "," +
			ATTRIBUTE_EXPECTED_DELIVERY_DATE + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_TOTAL_QUANTITY_RECEIVED + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE + "," +
			ATTRIBUTE_DOC_NUM + "," +
			ATTRIBUTE_NOTES + "," +
			ATTRIBUTE_BUYER_ID + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_DATE_LAST_UPDATED + "," +
			ATTRIBUTE_LAST_UPDATED_BY + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_CLAUSES_SET_1 + "," +
			ATTRIBUTE_CLAUSES_SET_2 + "," +
			ATTRIBUTE_CLAUSES_SET_3 + "," +
			ATTRIBUTE_CLAUSES_SET_4 + ")" +
			" values (" +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getOwnerCompanyId()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getPoNumber()) + "," +
			customerInventoryToReceiveBean.getQuantityToReceive() + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getCatalogCompanyId()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getCatalogId()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getCatPartNo()) + "," +
			DateHandler.getOracleToDateFunction(customerInventoryToReceiveBean.getDateInserted()) + "," +
			DateHandler.getOracleToDateFunction(customerInventoryToReceiveBean.getExpectedDeliveryDate()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getInventoryGroup()) + "," +
			customerInventoryToReceiveBean.getTotalQuantityReceived() + "," +
			customerInventoryToReceiveBean.getItemId() + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getCustomerPoNo()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getCustomerPoLine()) + "," +
			customerInventoryToReceiveBean.getDocNum() + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getNotes()) + "," +
			customerInventoryToReceiveBean.getBuyerId() + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getSupplierName()) + "," +
			DateHandler.getOracleToDateFunction(customerInventoryToReceiveBean.getDateLastUpdated()) + "," +
			customerInventoryToReceiveBean.getLastUpdatedBy() + "," +
			customerInventoryToReceiveBean.getPartGroupNo() + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getClausesSet1()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getClausesSet2()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getClausesSet3()) + "," +
			SqlHandler.delimitString(customerInventoryToReceiveBean.getClausesSet4()) + ")";

		return sqlManager.update(conn, query);
	}

*/
	//update
	public int update(CustomerInventoryToReceiveBean customerInventoryToReceiveBean,PersonnelBean personnelBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerInventoryToReceiveBean, connection,personnelBean);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustomerInventoryToReceiveBean customerInventoryToReceiveBean, Connection conn,PersonnelBean personnelBean)
		throws BaseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		
		String query  = "update " + UPD_TABLE + " set " +
				ATTRIBUTE_QUANTITY_TO_RECEIVE + "=" + 
				StringHandler.nullIfZero(customerInventoryToReceiveBean.getQuantityToReceive()) + "," +
				ATTRIBUTE_EXPECTED_DELIVERY_DATE + "= TO_DATE('" + 
				dateFormatter.format(customerInventoryToReceiveBean.getExpectedDeliveryDate()) + "','mm/dd/yyyy')," +
				ATTRIBUTE_NOTES + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getNotes()) + "'," +
				ATTRIBUTE_OWNER_SEGMENT_ID + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getOwnerSegmentId()) + "'," +
				ATTRIBUTE_ACCOUNT_NUMBER + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getAccountNumber()) + "'," +
				ATTRIBUTE_ACCOUNT_NUMBER2 + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getAccountNumber2()) + "'," +
				ATTRIBUTE_ACCOUNT_NUMBER3 + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getAccountNumber3()) + "'," +
				ATTRIBUTE_ACCOUNT_NUMBER4 + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getAccountNumber4()) + "'," +
				ATTRIBUTE_CUSTOMER_RECEIPT_ID + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getCustomerReceiptId()) + "'," +
				ATTRIBUTE_QUALITY_TRACKING_NUMBER + "=" + "'" +
				StringHandler.emptyIfNull(customerInventoryToReceiveBean.getQualityTrackingNumber()) + "'," +
				ATTRIBUTE_DATE_LAST_UPDATED + "= sysdate," + 
				ATTRIBUTE_LAST_UPDATED_BY + "=" + personnelBean.getPersonnelId()+
				" where " + ATTRIBUTE_DOC_NUM + "='" + customerInventoryToReceiveBean.getDocNum()+"'";

		return new SqlManager().update(conn, query);
	}


	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		return select(criteria,null);

		}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection customerInventoryToReceiveBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
        String currentCompanyId = "";
        Collection ownerSegmentColl = new Vector();
        while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerInventoryToReceiveBean customerInventoryToReceiveBean = new CustomerInventoryToReceiveBean();
			load(dataSetRow, customerInventoryToReceiveBean);
            //get owner segment of company changed
            if (!currentCompanyId.equals(customerInventoryToReceiveBean.getOwnerCompanyId())) {
                ownerSegmentColl = getOwnerSegmentData(customerInventoryToReceiveBean.getOwnerCompanyId(),conn);
            }
            customerInventoryToReceiveBean.setOwnerSegmentColl(ownerSegmentColl);
            currentCompanyId = customerInventoryToReceiveBean.getOwnerCompanyId();
            customerInventoryToReceiveBeanColl.add(customerInventoryToReceiveBean);
		}

		return customerInventoryToReceiveBeanColl;
	}

    public Collection<OwnerSegmentBean> getOwnerSegmentData(String companyId, Connection conn) throws BaseException {
	    Collection ownerSegmentColl = new Vector();
        StringBuilder query = new StringBuilder("select * from owner_segment where company_id = '").append(companyId).append("'");
        query.append(" order by owner_segment_desc");
        DataSet dataSet = new SqlManager().select(conn, query.toString());
        Iterator dataIter = dataSet.iterator();
        while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			OwnerSegmentBean bean = new OwnerSegmentBean();
			load(dataSetRow, bean);
            ownerSegmentColl.add(bean);
		}
        return ownerSegmentColl;
    }
}