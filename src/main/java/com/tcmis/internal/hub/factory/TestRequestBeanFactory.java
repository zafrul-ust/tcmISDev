package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.Globals;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.TestRequestInputBean;
import com.tcmis.internal.hub.beans.TestResultBean;
import com.tcmis.internal.hub.beans.LabTestReceiptBean;

/**
 * @author erika.altman
 *
 */
public class TestRequestBeanFactory extends BaseBeanFactory {
	Log log = LogFactory.getLog(this.getClass());
	
	public static final String SEARCH_VIEW = "LAB_TEST_DATA_SEARCH_VIEW";
	public static final String OBJECT_VIEW = "LAB_TEST_REQUEST_DATA_OV";

	public final String ATTRIBUTE_TEST_REQUEST_ID = "TEST_REQUEST_ID";
	public final String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";	
	public final String ATTRIBUTE_STATUS = "STATUS";
	public final String ATTRIBUTE_PART_DESC = "PART_DESC";
	public final String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public final String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public final String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";	
	public final String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public final String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public final String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public final String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
	public final String ATTRIBUTE_DATE_OF_SHIPMENT = "DATE_OF_SHIPMENT";
	public final String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public final String ATTRIBUTE_SAMPLE_SIZE = "SAMPLE_SIZE";
	public final String ATTRIBUTE_DATE_TO_LAB = "DATE_TO_LAB";
	public final String ATTRIBUTE_DATE_RECEIVED_BY_LAB = "DATE_RECEIVED_BY_LAB";
	public final String ATTRIBUTE_DATE_TEST_COMPLETE = "DATE_TEST_COMPLETE";
	public final String ATTRIBUTE_MATERIAL_QUALIFIED = "MATERIAL_QUALIFIED";
	public final String ATTRIBUTE_QUALIFICATION_DATE = "QUALIFICATION_DATE";
	public final String ATTRIBUTE_EXPIRATION_DATE = "EXPIRATION_DATE";
	public final String ATTRIBUTE_DATE_CLOSED = "DATE_CLOSED";
	public final String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public final String ATTRIBUTE_CREATE_DATE = "CREATE_DATE";
	public final String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public final String ATTRIBUTE_FACILITY = "FACILITY";
	
	
	// Fields only available in SEARCH_VIEW
	public final String ATTRIBUTE_LAB_AGE = "LAB_AGE";
	
	// Fields only available in OBJECT_VIEW
	public final String ATTRIBUTE_SENT_BY = "SENT_BY";
	public final String ATTRIBUTE_RECEIVED_BY = "RECEIVED_BY";	
	public final String ATTRIBUTE_COMPLETED_BY = "COMPLETED_BY";
	public final String ATTRIBUTE_QUALIFIED_BY = "QUALIFIED_BY";
	public final String ATTRIBUTE_CLOSED_BY = "CLOSED_BY";
	public final String ATTRIBUTE_CREATED_BY = "CREATED_BY";
	public final String ATTRIBUTE_RETURNED_BY = "RETURNED_BY";
	public final String ATTRIBUTE_HUB = "HUB";
	public final String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public final String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public final String ATTRIBUTE_PO_LINE = "PO_LINE";
	public final String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
	public final String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public final String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public final String ATTRIBUTE_DATE_CANCELLED = "DATE_CANCELLED";
	public final String ATTRIBUTE_CANCELLED_BY_NAME = "CANCELLED_BY_NAME";
	public final String ATTRIBUTE_REASON_FOR_CANCELLATION = "REASON_FOR_CANCELLATION";
	public final String ATTRIBUTE_QUALITY_TRACKING_NUMBER = "QUALITY_TRACKING_NUMBER";
	public final String ATTRIBUTE_LAB_TEST_RESULTS_COLL = "LAB_TEST_RESULTS_COLL";
	
	// Fields in the LAB_TEST table used for record creation.
	public final String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public final String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public final String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
    public final String ATTRIBUTE_FREQUENCY = "FREQUENCY";
    public final String ATTRIBUTE_LAB_TEST_RECEIPT_COLL = "LAB_TEST_RECEIPT_COLL";

    // Keep track of the current context in which the factory is being used.
	private String currentContext = null;
	
	public String getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(String currentContext) {
		this.currentContext = currentContext;
	}

	public TestRequestBeanFactory(DbManager dbManager) {
		super(dbManager);
	}
	
	// Overridden to map column names to fields on TestRequestInputBean.
	@Override
	public String getColumnName(String attributeName){
		
		if("partDescription".equals(attributeName)){
			if(SEARCH_VIEW.equals(getCurrentContext())){
				return ATTRIBUTE_PART_DESCRIPTION;
			} else {
				return ATTRIBUTE_PART_DESC;
			}			
		}
		if("labAge".equals(attributeName)){
			return ATTRIBUTE_LAB_AGE;
		}
		else if("itemDescription".equals(attributeName)){
			return ATTRIBUTE_ITEM_DESC;
		}
		else if("itemId".equals(attributeName)){
			return ATTRIBUTE_ITEM_ID;
		}
		else if("dateOfManufacture".equals(attributeName)){
			return ATTRIBUTE_DATE_OF_MANUFACTURE;
		}
		else if("dateOfReceipt".equals(attributeName)){
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if("dateOfShipment".equals(attributeName)){
			return ATTRIBUTE_DATE_OF_SHIPMENT;
		}
		else if ("mfgLot".equals(attributeName)){
			return ATTRIBUTE_MFG_LOT;
		}
		else if ("lotStatus".equals(attributeName)){
			return ATTRIBUTE_LOT_STATUS;
		}
		else if("poNumber".equals(attributeName)){
			return ATTRIBUTE_RADIAN_PO;
		}
		else if("receiptId".equals(attributeName)){
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if("toLabSignature".equals(attributeName)){
			return ATTRIBUTE_SENT_BY;
		}		
		else if("samplesReceivedSignature".equals(attributeName)){
			return ATTRIBUTE_RECEIVED_BY;
		}
		else if("testCompletedSignature".equals(attributeName)){
			return ATTRIBUTE_COMPLETED_BY;
		}
		else if("qualificationSignature".equals(attributeName)){
			return ATTRIBUTE_QUALIFIED_BY;
		}
		else if("closedSignature".equals(attributeName)){
			return ATTRIBUTE_CLOSED_BY;
		}
		else if("closedDate".equals(attributeName)){
			return ATTRIBUTE_DATE_CLOSED;
		}
		else if("createDate".equals(attributeName)){
			return ATTRIBUTE_CREATE_DATE;
		}
		else if("createSignature".equals(attributeName)){
			return ATTRIBUTE_CREATED_BY;
		}
		else if("companyId".equals(attributeName)){
			return ATTRIBUTE_COMPANY_ID;
		}
		else if("catalogCompanyId".equals(attributeName)){
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if("catalogId".equals(attributeName)){
			return ATTRIBUTE_CATALOG_ID;
		}
		else if("catPartNo".equals(attributeName)){
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if("partGroupNo".equals(attributeName)){
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if("testRequestId".equals(attributeName)){
			return ATTRIBUTE_TEST_REQUEST_ID;
		}
		else if("requestStatus".equals(attributeName)){
			return ATTRIBUTE_STATUS;
		}
		else if("sampleSize".equals(attributeName)){
			return ATTRIBUTE_SAMPLE_SIZE;
		}
		else if("materialQualified".equals(attributeName)){
			return ATTRIBUTE_MATERIAL_QUALIFIED;
		}
		else if("dateToLab".equals(attributeName)){
			return ATTRIBUTE_DATE_TO_LAB;
		}
		else if("dateReceivedByLab".equals(attributeName)){
			return ATTRIBUTE_DATE_RECEIVED_BY_LAB;
		}
		else if("dateTestComplete".equals(attributeName)){
			return ATTRIBUTE_DATE_TEST_COMPLETE;
		}
		else if("dateMaterialQualified".equals(attributeName)){
			return ATTRIBUTE_QUALIFICATION_DATE;
		}
		else if("expirationDate".equals(attributeName)){
			return ATTRIBUTE_EXPIRATION_DATE;
		}
		else if("returnedBy".equals(attributeName)){
			return ATTRIBUTE_RETURNED_BY;
		}
		else if("hub".equals(attributeName)){
			return ATTRIBUTE_HUB;
		}
		else if("inventoryGroup".equals(attributeName)){
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if("inventoryGroupName".equals(attributeName)){
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if("poLine".equals(attributeName)){
			return ATTRIBUTE_PO_LINE;
		}
		else if("quantityReceived".equals(attributeName)){
			return ATTRIBUTE_QUANTITY_RECEIVED;
		}
		else if("lotStatus".equals(attributeName)){
			return ATTRIBUTE_LOT_STATUS;
		}
		else if("opsEntityId".equals(attributeName)){
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if("dateCancelled".equals(attributeName)){
			return ATTRIBUTE_DATE_CANCELLED;
		}
		else if("cancelledByName".equals(attributeName)){
			return ATTRIBUTE_CANCELLED_BY_NAME;
		}
		else if("reasonForCancellation".equals(attributeName)){
			return ATTRIBUTE_REASON_FOR_CANCELLATION;
		}
		else if("labTestResultsColl".equals(attributeName)){
			return ATTRIBUTE_LAB_TEST_RESULTS_COLL;
		}
		else if("qualityTrackingNumber".equals(attributeName)){
			return ATTRIBUTE_QUALITY_TRACKING_NUMBER;
		}
		else if("requestor".equals(attributeName)){
			return ATTRIBUTE_REQUESTOR;
		}
		else if("facility".equals(attributeName)){
			return ATTRIBUTE_FACILITY;
		}
        else if("frequency".equals(attributeName)){
			return ATTRIBUTE_FREQUENCY;
		}
        else if("labTestReceiptColl".equals(attributeName)){
			return ATTRIBUTE_LAB_TEST_RECEIPT_COLL;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}
	
	@Override
	public int getType(String attributeName){
		return super.getType(attributeName, TestRequestInputBean.class);
	}

	// Select a single test request using the OBJECT_VIEW to display for editing.
	public Collection<TestRequestInputBean> select(BigDecimal testRequestId) throws BaseException{
		Connection connection = null;
		Collection<TestRequestInputBean> results = new Vector<TestRequestInputBean>();
		setCurrentContext(OBJECT_VIEW);
		
		SearchCriteria criteria = new SearchCriteria();
		// If there are no arguments provided, we can't select a record.
		if(testRequestId == null){
			return results;
		}
		if(testRequestId != null){
			criteria.addCriterion(ATTRIBUTE_TEST_REQUEST_ID, SearchCriterion.EQUALS, testRequestId.toString());
		}

		try {
			connection = getDbManager().getConnection();

			Map<String, Class<?>>  connTypeMap = connection.getTypeMap();
			connTypeMap.put(TestRequestInputBean.SQLTypeName, Class.forName("com.tcmis.internal.hub.beans.TestRequestInputBean"));
			connTypeMap.put(TestResultBean.SQLTypeName, Class.forName("com.tcmis.internal.hub.beans.TestResultBean"));
            connTypeMap.put(LabTestReceiptBean.SQLTypeName, Class.forName("com.tcmis.internal.hub.beans.LabTestReceiptBean"));

            String query = new StringBuilder("select value(p) from ").append(OBJECT_VIEW).append(" p ").append("WHERE ").append(ATTRIBUTE_TEST_REQUEST_ID).append(" = ").append(testRequestId.toString()).toString();
			log.debug(query);
			try {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(query);
				
				while (rs.next()) {
					Object o = rs.getObject(1);
					results.add((TestRequestInputBean) o);
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
		}
		catch (Exception e) {
			log.error("select error:" + e.getMessage(), e);
			DbSelectException ex = new DbSelectException("error.db.select");

			ex.setRootCause(e);
			throw ex;
		}
		finally {
			getDbManager().returnConnection(connection);
		}

		return results;
	}
	
	// Select a collection of test requests from the SEARCH_VIEW to display as search results.
	// Returns an empty collection when there are no records found.
	public Collection<TestRequestInputBean> select(SearchCriteria search) throws BaseException{
		Connection connection = null;
		Collection<TestRequestInputBean> results = new Vector<TestRequestInputBean>();
		setCurrentContext(SEARCH_VIEW);
		try {
			connection = getDbManager().getConnection();
			
			String query = new StringBuilder("select * from ").append(SEARCH_VIEW).append(" ").append(getWhereClause(search)).toString();
			log.debug(query);
			try {
				DataSet dataSet = new SqlManager().select(connection, query);
				Iterator dataIter = dataSet.iterator();
				TestRequestInputBean trib = null;
				DataSetRow dsr = null;
				while (dataIter.hasNext()) {
					dsr = (DataSetRow) dataIter.next();
					trib = new TestRequestInputBean();
					load(dsr, trib);
					results.add(trib);
				}
			}
			catch (BaseException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
		}
		catch (Exception e) {
			log.error("select error:" + e.getMessage(), e);
			DbSelectException ex = new DbSelectException("error.db.select");

			ex.setRootCause(e);
			throw ex;
		}
		finally {
			getDbManager().returnConnection(connection);
		}

		return results;	
	}
	
	// Create a new test request from the data provided.
	public Collection insert(BigDecimal receiptId, BigDecimal createdByPersonnelId) throws BaseException {
		GenericProcedureFactory gpf = new GenericProcedureFactory(this.getDbManager());
		Connection conn = this.getDbManager().getConnection();
		Collection c = null;
		Collection<Object> inParameters = new Vector<Object>(), outParameters = new Vector<Object>();
		inParameters.add(receiptId);
		inParameters.add(createdByPersonnelId);
		
		outParameters.add(new Integer(java.sql.Types.NUMERIC)); // test request id
		outParameters.add(new Integer(java.sql.Types.VARCHAR)); // error value
		
		
		try {
			// Returns a collection of result objects based on the type order of the outParameters collection.
			c = gpf.doProcedure(conn, "pkg_lab_testing.P_LAB_TEST_INSERT", inParameters, outParameters);
		}
		catch(Exception ex){
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
			c = new Vector<Object>();
			c.add(library.getString(Globals.DB_PROCEDURE_EXCEPTION));
		}
		finally {
			this.getDbManager().returnConnection(conn);
		}
		return c;
	}

	// Gets the test request ID from the search view based on the parent test request ID.
	public BigDecimal getTestRequestId(BigDecimal parentTestRequestId) throws BaseException {
		GenericSqlFactory gsf = new GenericSqlFactory(this.getDbManager());
		StringBuilder query = new StringBuilder("SELECT ").append(ATTRIBUTE_TEST_REQUEST_ID).append(" FROM ").append(SEARCH_VIEW).append(" WHERE PARENT_TEST_REQUEST_ID = ").append(parentTestRequestId);
		String id = gsf.selectSingleValue(query.toString());
		return new BigDecimal(id);		
	}	
}
