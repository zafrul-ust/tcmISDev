package com.tcmis.client.openCustomer.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.openCustomer.beans.PkgWebPrOrderTrackDetailBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.IssueBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;

/******************************************************************************
 * CLASSNAME: PkgOrderTrackWebPrOrderTrackDetailBeanFactory <br>
 * @version: 1.0, May 6, 2005 <br>
 *****************************************************************************/

public class PkgWebPrOrderTrackDetailBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_USE_APPROVER = "USE_APPROVER";
  public String ATTRIBUTE_USE_APPROVER_NAME = "USE_APPROVER_NAME";
  public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ALLOCATED_QUANTITY = "ALLOCATED_QUANTITY";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_REF = "REF";
  public String ATTRIBUTE_REFERENCE_DATE = "REFERENCE_DATE";
  public String ATTRIBUTE_NOTES = "NOTES";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
  public String ATTRIBUTE_REQUIRED_DATETIME = "REQUIRED_DATETIME";
  public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
  public String ATTRIBUTE_ALLOCATION_REFERENCE_DATE = "ALLOCATION_REFERENCE_DATE";
  public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
  public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
  public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
  public String ATTRIBUTE_RELEASE_DATE = "RELEASE_DATE";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_RECEIPT_DOCUMENT = "RECEIPT_DOCUMENT";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
  public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
  public String ATTRIBUTE_REF_TYPE = "REF_TYPE";
  public String ATTRIBUTE_REF_NUMBER = "REF_NUMBER";
  public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
  public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
  public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_ORDER_DATE = "ORDER_DATE";
  public String ATTRIBUTE_CUSTOMER_PART_NO = "CUSTOMER_PART_NO";

  //table name
  public String TABLE = "PKG_WEB_PR_ORDER_TRACK_DETAIL";

  //constructor
  public PkgWebPrOrderTrackDetailBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("useApprover")) {
      return ATTRIBUTE_USE_APPROVER;
    } else if (attributeName.equals("useApproverName")) {
      return ATTRIBUTE_USE_APPROVER_NAME;
    } else if (attributeName.equals("facPartNo")) {
      return ATTRIBUTE_FAC_PART_NO;
    } else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    } else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    } else if (attributeName.equals("allocatedQuantity")) {
      return ATTRIBUTE_ALLOCATED_QUANTITY;
    } else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    } else if (attributeName.equals("ref")) {
      return ATTRIBUTE_REF;
    } else if (attributeName.equals("referenceDate")) {
      return ATTRIBUTE_REFERENCE_DATE;
    } else if (attributeName.equals("notes")) {
      return ATTRIBUTE_NOTES;
    } else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    } else if (attributeName.equals("orderQuantity")) {
      return ATTRIBUTE_ORDER_QUANTITY;
    } else if (attributeName.equals("applicationDesc")) {
      return ATTRIBUTE_APPLICATION_DESC;
    } else if (attributeName.equals("itemType")) {
      return ATTRIBUTE_ITEM_TYPE;
    } else if (attributeName.equals("requiredDatetime")) {
      return ATTRIBUTE_REQUIRED_DATETIME;
    } else if (attributeName.equals("deliveryType")) {
      return ATTRIBUTE_DELIVERY_TYPE;
    } else if (attributeName.equals("allocationReferenceDate")) {
      return ATTRIBUTE_ALLOCATION_REFERENCE_DATE;
    } else if (attributeName.equals("mfgLot")) {
      return ATTRIBUTE_MFG_LOT;
    } else if (attributeName.equals("lotStatus")) {
      return ATTRIBUTE_LOT_STATUS;
    } else if (attributeName.equals("expireDate")) {
      return ATTRIBUTE_EXPIRE_DATE;
    } else if (attributeName.equals("releaseDate")) {
      return ATTRIBUTE_RELEASE_DATE;
    } else if (attributeName.equals("packaging")) {
      return ATTRIBUTE_PACKAGING;
    } else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
	 } else if (attributeName.equals("receiptDocument")) {
      return ATTRIBUTE_RECEIPT_DOCUMENT;
	 }else if (attributeName.equals("partDescription")) {
	      return ATTRIBUTE_PART_DESCRIPTION;
	 }else if (attributeName.equals("trackingNumber")) {
	      return ATTRIBUTE_TRACKING_NUMBER;
	 }else if (attributeName.equals("carrierName")) {
	      return ATTRIBUTE_CARRIER_NAME;
	 }else if (attributeName.equals("refType")) {
	      return ATTRIBUTE_REF_TYPE;
	 }else if (attributeName.equals("refNumber")) {
	      return ATTRIBUTE_REF_NUMBER;
	 }else if (attributeName.equals("shipmentId")) {
	      return ATTRIBUTE_SHIPMENT_ID;
	 }else if (attributeName.equals("locationId")) {
	      return ATTRIBUTE_LOCATION_ID;
	 }else if (attributeName.equals("locationDesc")) {
	      return ATTRIBUTE_LOCATION_DESC;
	 }else if (attributeName.equals("deliveryPoint")) {
	      return ATTRIBUTE_DELIVERY_POINT;
	 }else if (attributeName.equals("deliveryPointDesc")) {
	      return ATTRIBUTE_DELIVERY_POINT_DESC;
	 }else if (attributeName.equals("itemId")) {
	      return ATTRIBUTE_ITEM_ID;
	 }else if (attributeName.equals("orderDate")) {
	      return ATTRIBUTE_ORDER_DATE;
	 }else if (attributeName.equals("customerPartNo")) {
	      return ATTRIBUTE_CUSTOMER_PART_NO;
	 }else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PkgWebPrOrderTrackDetailBean.class);
  }


  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = delete(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

    String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

  //select
  public Collection select(String mrNumber, String lineItem, String fromCustomerOrdertracking) throws BaseException,Exception {
    Connection connection = this.getDbManager().getConnection();
    try {
      connection.setAutoCommit(false);
    } catch (SQLException ex) {
    }

    Collection cin = new Vector();
    if (mrNumber != null && mrNumber.length() > 0) {
      cin.add(new String(mrNumber));
    } else {
      cin.add("");
    }

    if (lineItem != null && lineItem.length() > 0) {
      cin.add(new String(lineItem));
    } else {
      cin.add("");
    }

    cin.add("");

    log.debug(cin);
    Collection cout = new Vector();
    cout.add(new Integer(java.sql.Types.VARCHAR));
    Collection result = null;
    SqlManager sqlManager = new SqlManager();
    try {
      result = sqlManager.doProcedure(connection, "PKG_OPEN_CUSTOMER.P_ORDER_TRACK_DETAIL", cin, cout);
    } finally {
    }

    Iterator i11 = result.iterator();
    String searchQuery = "";
    while (i11.hasNext()) {
      searchQuery = (String) i11.next(); ;
    }
    Collection c = select(searchQuery, connection, fromCustomerOrdertracking);
    try {
      connection.setAutoCommit(true);
    } finally {
    }

    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection select(String query, Connection conn, String fromCustomerOrdertracking) throws BaseException {

    Collection pkgMrAllocationReportBeanColl = new Vector();

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PkgWebPrOrderTrackDetailBean pkgOrderTrackWebPrOrderTrackDetailBean = new PkgWebPrOrderTrackDetailBean();
      load(dataSetRow, pkgOrderTrackWebPrOrderTrackDetailBean);
		if (!StringHandler.isBlankString(pkgOrderTrackWebPrOrderTrackDetailBean.getReceiptDocument())) {
			pkgOrderTrackWebPrOrderTrackDetailBean.setReceiptDocumentColl(getReceiptDocumentColl(pkgOrderTrackWebPrOrderTrackDetailBean.getReceiptDocument(),fromCustomerOrdertracking));
		}
		if (fromCustomerOrdertracking != null && "Y".equalsIgnoreCase(fromCustomerOrdertracking)) {
			pkgOrderTrackWebPrOrderTrackDetailBean.setMsdsColl(getMsdsColl(pkgOrderTrackWebPrOrderTrackDetailBean.getRefType(), pkgOrderTrackWebPrOrderTrackDetailBean.getRefNumber(),
                    pkgOrderTrackWebPrOrderTrackDetailBean.getPrNumber(),pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem()));
		}
		if (pkgOrderTrackWebPrOrderTrackDetailBean.getShipmentId() != null) {
//			pkgOrderTrackWebPrOrderTrackDetailBean.setPackListColl(getPackListColl(pkgOrderTrackWebPrOrderTrackDetailBean.getPrNumber(), pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem()));
			pkgOrderTrackWebPrOrderTrackDetailBean.setInvoiceColl(getInvoiceColl(pkgOrderTrackWebPrOrderTrackDetailBean.getPrNumber(),
                    pkgOrderTrackWebPrOrderTrackDetailBean.getShipmentId(), pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem()));
		}
		pkgMrAllocationReportBeanColl.add(pkgOrderTrackWebPrOrderTrackDetailBean);
	 }

    return pkgMrAllocationReportBeanColl;
  }

 public Collection getReceiptDocumentColl(String documentId, String fromCustomerOrdertracking) throws BaseException {
	GenericSqlFactory factory = new GenericSqlFactory(this.getDbManager(), new IssueBean());
	StringBuilder query = new StringBuilder("SELECT * FROM TABLE (PKG_OPEN_CUSTOMER.FX_RECEIPT_DOCUMENT(").append(documentId).append(")) WHERE DOCUMENT_URL IS NOT NULL");
	if (fromCustomerOrdertracking != null && "Y".equalsIgnoreCase(fromCustomerOrdertracking)) {
		query.append(" AND DOCUMENT_TYPE IN ('COC','COA')");
	}
	query.append(" ORDER BY DOCUMENT_DATE DESC");
	return factory.selectQuery(query.toString());
 }
 
 public Collection getMsdsColl(String refType, BigDecimal refNumber,BigDecimal prNumber,String lineItem) throws BaseException {
		 GenericSqlFactory factory = new GenericSqlFactory(this.getDbManager(), new ReceiptDocumentViewBean());
		 String query = "select * from table (PKG_OPEN_CUSTOMER.FX_REF_MSDS('" + StringHandler.emptyIfNull(refType) + "','" +
                 StringHandler.emptyIfNull(refNumber) + "',"+NumberHandler.emptyIfNull(prNumber)+",'"+StringHandler.emptyIfNull(lineItem)+"'))";
		 return factory.selectQuery(query);
 }
 
 public Collection getInvoiceColl(BigDecimal prNumber, BigDecimal shipmentId, String lineItem) throws BaseException {
		 GenericSqlFactory factory = new GenericSqlFactory(this.getDbManager(), new IssueBean());
		 String query = "select * from table (PKG_OPEN_CUSTOMER.FX_INVOICE_DETAIL("+NumberHandler.emptyIfNull(prNumber)+","+NumberHandler.emptyIfNull(shipmentId)+",'"+StringHandler.emptyIfNull(lineItem)+"'))";
		 return factory.selectQuery(query);
 }

}
