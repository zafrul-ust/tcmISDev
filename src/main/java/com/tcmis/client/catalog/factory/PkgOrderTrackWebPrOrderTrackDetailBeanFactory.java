package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.PkgOrderTrackWebPrOrderTrackDetailBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CustomerCreditBean;
import com.tcmis.internal.hub.beans.IssueBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.factory.ReceiptDocumentViewBeanFactory;

/******************************************************************************
 * CLASSNAME: PkgOrderTrackWebPrOrderTrackDetailBeanFactory <br>
 * @version: 1.0, May 6, 2005 <br>
 *****************************************************************************/

public class PkgOrderTrackWebPrOrderTrackDetailBeanFactory extends BaseBeanFactory {

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
  public String ATTRIBUTE_PROGRAM_ID = "PROGRAM_ID";
  public String ATTRIBUTE_OWNER_SEGMENT_ID = "OWNER_SEGMENT_ID";

  //table name
  public String TABLE = "PKG_WEB_PR_ORDER_TRACK_DETAIL";
  public String ATTRIBUTE_QUALITY_ID_LABEL = "QUALITY_ID_LABEL";
  public String ATTRIBUTE_QUALITY_ID = "QUALITY_ID";
  public String ATTRIBUTE_CAT_PART_ATTRIBUTE_HEADER = "CAT_PART_ATTRIBUTE_HEADER";
  public String ATTRIBUTE_CAT_PART_ATTRIBUTE = "CAT_PART_ATTRIBUTE";

  //constructor
  public PkgOrderTrackWebPrOrderTrackDetailBeanFactory(DbManager dbManager) {
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
	 }else if (attributeName.equals("programId")) {
		  return ATTRIBUTE_PROGRAM_ID;
	 }else if (attributeName.equals("ownerSegmentId")) {
		  return ATTRIBUTE_OWNER_SEGMENT_ID;
	 }else if (attributeName.equals("qualityIdLabel")) {
		  return ATTRIBUTE_QUALITY_ID_LABEL;
	 }else if (attributeName.equals("qualityId")) {
		  return ATTRIBUTE_QUALITY_ID;
	 }else if (attributeName.equals("catPartAttributeHeader")) {
		  return ATTRIBUTE_CAT_PART_ATTRIBUTE_HEADER;
	 }else if (attributeName.equals("catPartAttribute")) {
		  return ATTRIBUTE_CAT_PART_ATTRIBUTE;
	 }
	 else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PkgOrderTrackWebPrOrderTrackDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
    public int delete(PkgOrderTrackWebPrOrderTrackDetailBean pkgWebPrOrderTrackDetailBean)
   throws BaseException {
   SearchCriteria criteria = new SearchCriteria("useApprover", "SearchCriterion.EQUALS",
   "" + pkgWebPrOrderTrackDetailBean.getUseApprover());
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
    public int delete(PkgOrderTrackWebPrOrderTrackDetailBean pkgWebPrOrderTrackDetailBean, Connection conn)
   throws BaseException {
   SearchCriteria criteria = new SearchCriteria("useApprover", "SearchCriterion.EQUALS",
   "" + pkgWebPrOrderTrackDetailBean.getUseApprover());
   return delete(criteria, conn);
    }
   */

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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
    public int insert(PkgOrderTrackWebPrOrderTrackDetailBean pkgWebPrOrderTrackDetailBean)
   throws BaseException {
   Connection connection = null;
   int result = 0;
   try {
   connection = getDbManager().getConnection();
   result = insert(pkgWebPrOrderTrackDetailBean, connection);
   }
   finally {
   this.getDbManager().returnConnection(connection);
   }
   return result;
    }
    public int insert(PkgOrderTrackWebPrOrderTrackDetailBean pkgWebPrOrderTrackDetailBean, Connection conn)
   throws BaseException {
   SqlManager sqlManager = new SqlManager();
   String query  =
   "insert into " + TABLE + " (" +
   ATTRIBUTE_USE_APPROVER + "," +
   ATTRIBUTE_USE_APPROVER_NAME + "," +
   ATTRIBUTE_FAC_PART_NO + "," +
   ATTRIBUTE_PR_NUMBER + "," +
   ATTRIBUTE_LINE_ITEM + "," +
   ATTRIBUTE_ALLOCATED_QUANTITY + "," +
   ATTRIBUTE_STATUS + "," +
   ATTRIBUTE_REF + "," +
   ATTRIBUTE_REFERENCE_DATE + "," +
   ATTRIBUTE_NOTES + "," +
   ATTRIBUTE_APPLICATION + "," +
   ATTRIBUTE_ORDER_QUANTITY + "," +
   ATTRIBUTE_APPLICATION_DESC + "," +
   ATTRIBUTE_ITEM_TYPE + "," +
   ATTRIBUTE_REQUIRED_DATETIME + "," +
   ATTRIBUTE_DELIVERY_TYPE + "," +
   ATTRIBUTE_ALLOCATION_REFERENCE_DATE + "," +
   ATTRIBUTE_MFG_LOT + "," +
   ATTRIBUTE_LOT_STATUS + "," +
   ATTRIBUTE_EXPIRE_DATE + ")" +
   " values (" +
   pkgWebPrOrderTrackDetailBean.getUseApprover() + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getUseApproverName()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getFacPartNo()) + "," +
   pkgWebPrOrderTrackDetailBean.getPrNumber() + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getLineItem()) + "," +
   pkgWebPrOrderTrackDetailBean.getAllocatedQuantity() + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getStatus()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getRef()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getReferenceDate()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getNotes()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getApplication()) + "," +
   pkgWebPrOrderTrackDetailBean.getOrderQuantity() + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getApplicationDesc()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getItemType()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getRequiredDatetime()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getDeliveryType()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getAllocationReferenceDate()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getMfgLot()) + "," +
   SqlHandler.delimitString(pkgWebPrOrderTrackDetailBean.getLotStatus()) + "," +
   DateHandler.getOracleToDateFunction(pkgWebPrOrderTrackDetailBean.getExpireDate()) + ")";
   return sqlManager.update(conn, query);
    }
//update
    public int update(PkgOrderTrackWebPrOrderTrackDetailBean pkgWebPrOrderTrackDetailBean)
   throws BaseException {
   Connection connection = null;
   int result = 0;
   try {
   connection = getDbManager().getConnection();
   result = update(pkgWebPrOrderTrackDetailBean, connection);
   }
   finally {
   this.getDbManager().returnConnection(connection);
   }
   return result;
    }
    public int update(PkgOrderTrackWebPrOrderTrackDetailBean pkgOrderTrackWebPrOrderTrackDetailBean, Connection conn)
   throws BaseException {
   String query  = "update " + TABLE + " set " +
   ATTRIBUTE_USE_APPROVER + "=" +
    StringHandler.nullIfZero(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApprover()) + "," +
   ATTRIBUTE_USE_APPROVER_NAME + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApproverName()) + "," +
   ATTRIBUTE_FAC_PART_NO + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo()) + "," +
   ATTRIBUTE_PR_NUMBER + "=" +
    StringHandler.nullIfZero(pkgOrderTrackWebPrOrderTrackDetailBean.getPrNumber()) + "," +
   ATTRIBUTE_LINE_ITEM + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem()) + "," +
   ATTRIBUTE_ALLOCATED_QUANTITY + "=" +
    StringHandler.nullIfZero(pkgOrderTrackWebPrOrderTrackDetailBean.getAllocatedQuantity()) + "," +
   ATTRIBUTE_STATUS + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getStatus()) + "," +
   ATTRIBUTE_REF + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getRef()) + "," +
   ATTRIBUTE_REFERENCE_DATE + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getReferenceDate()) + "," +
   ATTRIBUTE_NOTES + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getNotes()) + "," +
   ATTRIBUTE_APPLICATION + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getApplication()) + "," +
   ATTRIBUTE_ORDER_QUANTITY + "=" +
    StringHandler.nullIfZero(pkgOrderTrackWebPrOrderTrackDetailBean.getOrderQuantity()) + "," +
   ATTRIBUTE_APPLICATION_DESC + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getApplicationDesc()) + "," +
   ATTRIBUTE_ITEM_TYPE + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getItemType()) + "," +
   ATTRIBUTE_REQUIRED_DATETIME + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getRequiredDatetime()) + "," +
   ATTRIBUTE_DELIVERY_TYPE + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryType()) + "," +
   ATTRIBUTE_ALLOCATION_REFERENCE_DATE + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getAllocationReferenceDate()) + "," +
   ATTRIBUTE_MFG_LOT + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getMfgLot()) + "," +
   ATTRIBUTE_LOT_STATUS + "=" +
    SqlHandler.delimitString(pkgOrderTrackWebPrOrderTrackDetailBean.getLotStatus()) + "," +
   ATTRIBUTE_EXPIRE_DATE + "=" +
    DateHandler.getOracleToDateFunction(pkgOrderTrackWebPrOrderTrackDetailBean.getExpireDate()) + " " +
   "where " + ATTRIBUTE_USE_APPROVER + "=" +
    pkgOrderTrackWebPrOrderTrackDetailBean.getUseApprover();
   return new SqlManager().update(conn, query);
    }
   */
  //select
  public Collection select(String companyId, String mrNumber, String lineItem, String fromCustomerOrdertracking) throws BaseException,Exception {
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

    if (companyId != null && companyId.length() > 0) {
      cin.add(new String(companyId));
    } else {
      cin.add("");
    }
    log.debug(cin);
    Collection cout = new Vector();
    cout.add(new Integer(java.sql.Types.VARCHAR));
    Collection result = null;
    SqlManager sqlManager = new SqlManager();
    try {
      result = sqlManager.doProcedure(connection, "PKG_ORDER_TRACK_TEST.PR_ORDER_TRACK_DETAIL", cin, cout);
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
      PkgOrderTrackWebPrOrderTrackDetailBean pkgOrderTrackWebPrOrderTrackDetailBean = new PkgOrderTrackWebPrOrderTrackDetailBean();
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
	ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(this.getDbManager());
	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("receiptId", SearchCriterion.EQUALS, documentId);
	criteria.addCriterion("documentUrl", SearchCriterion.IS_NOT, null);
	if (fromCustomerOrdertracking != null && "Y".equalsIgnoreCase(fromCustomerOrdertracking)) {
		criteria.addCriterion("documentType", SearchCriterion.IN, "'COC','COA'");
	}
	return factory.select(criteria);
 }
 
 public Collection getMsdsColl(String refType, BigDecimal refNumber,BigDecimal prNumber,String lineItem) throws BaseException {
		// DbManager dbManager = new DbManager(getClient(), getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(this.getDbManager(), new ReceiptDocumentViewBean());
		 String query = "select * from table (pkg_order_track_test.FX_REF_MSDS('" + StringHandler.emptyIfNull(refType) + "','" +
                 StringHandler.emptyIfNull(refNumber) + "',"+NumberHandler.emptyIfNull(prNumber)+",'"+StringHandler.emptyIfNull(lineItem)+"'))";
		 return factory.selectQuery(query);
 }
/* 
 public Collection getPackListColl(BigDecimal prNumber, String lineItem) throws BaseException {
		// DbManager dbManager = new DbManager(getClient(), getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(this.getDbManager(), new IssueBean());
		 String query = "select distinct pr_number,shipment_id from issue where pr_number = "+prNumber+" and line_item = "+lineItem+" and ship_confirm_date is null";
		 return factory.selectQuery(query);
 } */
 
 public Collection getInvoiceColl(BigDecimal prNumber, BigDecimal shipmentId, String lineItem) throws BaseException {
		// DbManager dbManager = new DbManager(getClient(), getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(this.getDbManager(), new IssueBean());
		 String query = "select distinct invoice from invoice_detail where pr_number = "+prNumber+" and shipment_id = "+shipmentId+" and line_item = "+lineItem+" and rownum = 1";
		 return factory.selectQuery(query);
 }

}