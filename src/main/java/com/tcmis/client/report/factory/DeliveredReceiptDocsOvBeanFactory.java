package com.tcmis.client.report.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.DeliveredReceiptDocsOvBean;
import com.tcmis.client.report.beans.DeliveredReceiptDocsViewBean;
import com.tcmis.client.report.beans.FacPlanningReportOvBean;
import com.tcmis.client.report.beans.ReceiptDocsObjBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;


/******************************************************************************
 * CLASSNAME: HubIgBuyerOvBeanFactory <br>
 * @version: 1.0, May 7, 2008 <br>
 *****************************************************************************/


public class DeliveredReceiptDocsOvBeanFactory extends BaseBeanFactory {

	
	Log log = LogFactory.getLog(this.getClass());

	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CATALOG_DESC = "CATALOG_DESC";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_SPEC = "SPEC";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH = "UNIT_OF_SALE_QUANTITY_PER_EACH";
	public String ATTRIBUTE_DOCUMENT_URL = "DOCUMENT_URL";
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
    public String ATTRIBUTE_MR_LINE = "MR_LINE";
    public String ATTRIBUTE_PO_LINE = "PO_LINE";
    public String ATTRIBUTE_MR_LINE_QTY = "MR_LINE_QTY";
    public String ATTRIBUTE_QUANTITY_ISSUED = "QUANTITY_ISSUED";
    public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_DELIVERY_CONFIRMATION_DATE = "DELIVERY_CONFIRMATION_DATE";
	public String ATTRIBUTE_CONFIRMED_BY = "CONFIRMED_BY";
	public String ATTRIBUTE_DELIVERY_COMMENTS = "DELIVERY_COMMENTS";
	public String ATTRIBUTE_DOCUMENTS = "DOCUMENTS";
	public String ATTRIBUTE_PUT_AWAY_ACCEPTED_BY = "PUT_AWAY_ACCEPTED_BY";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	
	//table name
	public String TABLE = "DELIVERED_RECEIPT_DOCS_OV";


	//constructor
	public DeliveredReceiptDocsOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
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
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("releaseNumber")) {
			return ATTRIBUTE_RELEASE_NUMBER;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catalogDesc")) {
			return ATTRIBUTE_CATALOG_DESC;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("spec")) {
			return ATTRIBUTE_SPEC;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if(attributeName.equals("baselinePrice")) {
			return ATTRIBUTE_BASELINE_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("unitOfSaleQuantityPerEach")) {
			return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
		}
		else if(attributeName.equals("documentUrl")) {
			return ATTRIBUTE_DOCUMENT_URL;
		}
		else if(attributeName.equals("onLine")) {
			return ATTRIBUTE_ON_LINE;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("mrLineQty")) {
			return ATTRIBUTE_MR_LINE_QTY;
		}
		else if(attributeName.equals("quantityIssued")) {
			return ATTRIBUTE_QUANTITY_ISSUED;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if(attributeName.equals("deliveryConfirmationDate")) {
			return ATTRIBUTE_DELIVERY_CONFIRMATION_DATE;
		}
		else if(attributeName.equals("confirmedBy")) {
			return ATTRIBUTE_CONFIRMED_BY;
		}
		else if(attributeName.equals("deliveryComments")) {
			return ATTRIBUTE_DELIVERY_COMMENTS;
		}
		else if(attributeName.equals("documents")) {
			return ATTRIBUTE_DOCUMENTS;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("putAwayAcceptedBy")) {
			return ATTRIBUTE_PUT_AWAY_ACCEPTED_BY;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DeliveredReceiptDocsViewBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(HubIgBuyerOvBean hubIgBuyerOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + hubIgBuyerOvBean.getHub());

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


	public int delete(HubIgBuyerOvBean hubIgBuyerOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + hubIgBuyerOvBean.getHub());

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

		Collection deliveredReceiptDocsColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReceiptDocsObjBean receiptDocsObjBean = new ReceiptDocsObjBean();
			load(dataSetRow, receiptDocsObjBean);
			deliveredReceiptDocsColl.add(receiptDocsObjBean);
		}

		return deliveredReceiptDocsColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria,null);
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("CUSTOMER.RECEIPT_DOCS_OBJ",
					Class.forName(
					"com.tcmis.client.report.beans.ReceiptDocsObjBean"));
			map.put("CUSTOMER.DELIVERED_RECEIPT_DOCS_OBJ",
					Class.forName(
					"com.tcmis.client.report.beans.DeliveredReceiptDocsOvBean"));
			

			c = selectObject(criteria, sortCriteria,connection);
		}
		catch (Exception e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
	BaseException {
		Collection deliveredReceiptDocsColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				ReceiptDocsObjBean b = (ReceiptDocsObjBean) rs.getObject(1);
				deliveredReceiptDocsColl.add(b);
			}
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		return deliveredReceiptDocsColl;
	}

}
