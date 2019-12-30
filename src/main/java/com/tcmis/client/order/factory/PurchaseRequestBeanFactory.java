package com.tcmis.client.order.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Date;
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
import com.tcmis.client.order.beans.PurchaseRequestBean;

/******************************************************************************
 * CLASSNAME: PurchaseRequestBeanFactory <br>
 * @version: 1.0, Jun 26, 2006 <br>
 *****************************************************************************/

public class PurchaseRequestBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_REQUEST_DATE = "REQUEST_DATE";
  public String ATTRIBUTE_PR_STATUS = "PR_STATUS";
  public String ATTRIBUTE_SHIP_TO = "SHIP_TO";
  public String ATTRIBUTE_REQUESTED_FINANCE_APPROVER =
      "REQUESTED_FINANCE_APPROVER";
  public String ATTRIBUTE_REJECTION_REASON = "REJECTION_REASON";
  public String ATTRIBUTE_REQUESTED_RELEASER = "REQUESTED_RELEASER";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_FORWARD_TO = "FORWARD_TO";
  public String ATTRIBUTE_END_USER = "END_USER";
  public String ATTRIBUTE_DEPARTMENT = "DEPARTMENT";
  public String ATTRIBUTE_ENGINEERING_EVALUATION = "ENGINEERING_EVALUATION";
  public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
  public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
  public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CREDIT_CARD_TYPE = "CREDIT_CARD_TYPE";
  public String ATTRIBUTE_CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
  public String ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE =
      "CREDIT_CARD_EXPIRATION_DATE";
  public String ATTRIBUTE_CREDIT_CARD_NAME = "CREDIT_CARD_NAME";
  public String ATTRIBUTE_CONTACT_INFO = "CONTACT_INFO";
  public String ATTRIBUTE_SUBMITTED_DATE = "SUBMITTED_DATE";
  public String ATTRIBUTE_LAST_UPDATED = "LAST_UPDATED";
  public String ATTRIBUTE_LAST_UPDATED_BY = "LAST_UPDATED_BY";
  public String ATTRIBUTE_POS_USER = "POS_USER";
  public String ATTRIBUTE_MR_RELEASED_DATE = "MR_RELEASED_DATE";
  public String ATTRIBUTE_FINANCE_APPROVED_DATE = "FINANCE_APPROVED_DATE";

  //table name
  public String TABLE = "PURCHASE_REQUEST";

  //constructor
  public PurchaseRequestBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("requestor")) {
      return ATTRIBUTE_REQUESTOR;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("requestDate")) {
      return ATTRIBUTE_REQUEST_DATE;
    }
    else if (attributeName.equals("prStatus")) {
      return ATTRIBUTE_PR_STATUS;
    }
    else if (attributeName.equals("shipTo")) {
      return ATTRIBUTE_SHIP_TO;
    }
    else if (attributeName.equals("requestedFinanceApprover")) {
      return ATTRIBUTE_REQUESTED_FINANCE_APPROVER;
    }
    else if (attributeName.equals("rejectionReason")) {
      return ATTRIBUTE_REJECTION_REASON;
    }
    else if (attributeName.equals("requestedReleaser")) {
      return ATTRIBUTE_REQUESTED_RELEASER;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("forwardTo")) {
      return ATTRIBUTE_FORWARD_TO;
    }
    else if (attributeName.equals("endUser")) {
      return ATTRIBUTE_END_USER;
    }
    else if (attributeName.equals("department")) {
      return ATTRIBUTE_DEPARTMENT;
    }
    else if (attributeName.equals("engineeringEvaluation")) {
      return ATTRIBUTE_ENGINEERING_EVALUATION;
    }
    else if (attributeName.equals("requestId")) {
      return ATTRIBUTE_REQUEST_ID;
    }
    else if (attributeName.equals("accountSysId")) {
      return ATTRIBUTE_ACCOUNT_SYS_ID;
    }
    else if (attributeName.equals("chargeType")) {
      return ATTRIBUTE_CHARGE_TYPE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("creditCardType")) {
      return ATTRIBUTE_CREDIT_CARD_TYPE;
    }
    else if (attributeName.equals("creditCardNumber")) {
      return ATTRIBUTE_CREDIT_CARD_NUMBER;
    }
    else if (attributeName.equals("creditCardExpirationDate")) {
      return ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE;
    }
    else if (attributeName.equals("creditCardName")) {
      return ATTRIBUTE_CREDIT_CARD_NAME;
    }
    else if (attributeName.equals("contactInfo")) {
      return ATTRIBUTE_CONTACT_INFO;
    }
    else if (attributeName.equals("submittedDate")) {
      return ATTRIBUTE_SUBMITTED_DATE;
    }
    else if (attributeName.equals("lastUpdated")) {
      return ATTRIBUTE_LAST_UPDATED;
    }
    else if (attributeName.equals("lastUpdatedBy")) {
      return ATTRIBUTE_LAST_UPDATED_BY;
    }
    else if (attributeName.equals("posUser")) {
      return ATTRIBUTE_POS_USER;
    }
    else if (attributeName.equals("mrReleasedDate")) {
      return ATTRIBUTE_MR_RELEASED_DATE;
    }
    else if (attributeName.equals("financeApprovedDate")) {
      return ATTRIBUTE_FINANCE_APPROVED_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PurchaseRequestBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(PurchaseRequestBean purchaseRequestBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + purchaseRequestBean.getPrNumber());
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
   public int delete(PurchaseRequestBean purchaseRequestBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + purchaseRequestBean.getPrNumber());
    return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

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

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {

    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(PurchaseRequestBean purchaseRequestBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(purchaseRequestBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(PurchaseRequestBean purchaseRequestBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_REQUESTOR + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_REQUEST_DATE + "," +
     ATTRIBUTE_PR_STATUS + "," +
     ATTRIBUTE_SHIP_TO + "," +
     ATTRIBUTE_REQUESTED_FINANCE_APPROVER + "," +
     ATTRIBUTE_REJECTION_REASON + "," +
     ATTRIBUTE_REQUESTED_RELEASER + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_FORWARD_TO + "," +
     ATTRIBUTE_END_USER + "," +
     ATTRIBUTE_DEPARTMENT + "," +
     ATTRIBUTE_ENGINEERING_EVALUATION + "," +
     ATTRIBUTE_REQUEST_ID + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "," +
     ATTRIBUTE_CHARGE_TYPE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_CREDIT_CARD_TYPE + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "," +
     ATTRIBUTE_CREDIT_CARD_NAME + "," +
     ATTRIBUTE_CONTACT_INFO + "," +
     ATTRIBUTE_SUBMITTED_DATE + "," +
     ATTRIBUTE_LAST_UPDATED + "," +
     ATTRIBUTE_LAST_UPDATED_BY + "," +
     ATTRIBUTE_POS_USER + "," +
     ATTRIBUTE_MR_RELEASED_DATE + "," +
     ATTRIBUTE_FINANCE_APPROVED_DATE + ")" +
     " values (" +
     purchaseRequestBean.getPrNumber() + "," +
     purchaseRequestBean.getRequestor() + "," +
     SqlHandler.delimitString(purchaseRequestBean.getFacilityId()) + "," +
     DateHandler.getOracleToDateFunction(purchaseRequestBean.getRequestDate()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getPrStatus()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getShipTo()) + "," +
     purchaseRequestBean.getRequestedFinanceApprover() + "," +
     SqlHandler.delimitString(purchaseRequestBean.getRejectionReason()) + "," +
     purchaseRequestBean.getRequestedReleaser() + "," +
     SqlHandler.delimitString(purchaseRequestBean.getPoNumber()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getForwardTo()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getEndUser()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getDepartment()) + "," +
       SqlHandler.delimitString(purchaseRequestBean.getEngineeringEvaluation()) + "," +
     purchaseRequestBean.getRequestId() + "," +
     SqlHandler.delimitString(purchaseRequestBean.getAccountSysId()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getChargeType()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getCompanyId()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getCreditCardType()) + "," +
     purchaseRequestBean.getCreditCardNumber() + "," +
     DateHandler.getOracleToDateFunction(purchaseRequestBean.getCreditCardExpirationDate()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getCreditCardName()) + "," +
     SqlHandler.delimitString(purchaseRequestBean.getContactInfo()) + "," +
     DateHandler.getOracleToDateFunction(purchaseRequestBean.getSubmittedDate()) + "," +
     DateHandler.getOracleToDateFunction(purchaseRequestBean.getLastUpdated()) + "," +
     purchaseRequestBean.getLastUpdatedBy() + "," +
     purchaseRequestBean.getPosUser() + "," +
     DateHandler.getOracleToDateFunction(purchaseRequestBean.getMrReleasedDate()) + "," +
     DateHandler.getOracleToDateFunction(purchaseRequestBean.getFinanceApprovedDate()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(PurchaseRequestBean purchaseRequestBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(purchaseRequestBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(PurchaseRequestBean purchaseRequestBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(purchaseRequestBean.getPrNumber()) + "," +
     ATTRIBUTE_REQUESTOR + "=" +
      StringHandler.nullIfZero(purchaseRequestBean.getRequestor()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getFacilityId()) + "," +
     ATTRIBUTE_REQUEST_DATE + "=" +
      DateHandler.getOracleToDateFunction(purchaseRequestBean.getRequestDate()) + "," +
     ATTRIBUTE_PR_STATUS + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getPrStatus()) + "," +
     ATTRIBUTE_SHIP_TO + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getShipTo()) + "," +
     ATTRIBUTE_REQUESTED_FINANCE_APPROVER + "=" +
      StringHandler.nullIfZero(purchaseRequestBean.getRequestedFinanceApprover()) + "," +
     ATTRIBUTE_REJECTION_REASON + "=" +
       SqlHandler.delimitString(purchaseRequestBean.getRejectionReason()) + "," +
     ATTRIBUTE_REQUESTED_RELEASER + "=" +
       StringHandler.nullIfZero(purchaseRequestBean.getRequestedReleaser()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getPoNumber()) + "," +
     ATTRIBUTE_FORWARD_TO + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getForwardTo()) + "," +
     ATTRIBUTE_END_USER + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getEndUser()) + "," +
     ATTRIBUTE_DEPARTMENT + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getDepartment()) + "," +
     ATTRIBUTE_ENGINEERING_EVALUATION + "=" +
       SqlHandler.delimitString(purchaseRequestBean.getEngineeringEvaluation()) + "," +
     ATTRIBUTE_REQUEST_ID + "=" +
      StringHandler.nullIfZero(purchaseRequestBean.getRequestId()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getAccountSysId()) + "," +
     ATTRIBUTE_CHARGE_TYPE + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getChargeType()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getCompanyId()) + "," +
     ATTRIBUTE_CREDIT_CARD_TYPE + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getCreditCardType()) + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
       StringHandler.nullIfZero(purchaseRequestBean.getCreditCardNumber()) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(purchaseRequestBean.getCreditCardExpirationDate()) + "," +
     ATTRIBUTE_CREDIT_CARD_NAME + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getCreditCardName()) + "," +
     ATTRIBUTE_CONTACT_INFO + "=" +
      SqlHandler.delimitString(purchaseRequestBean.getContactInfo()) + "," +
     ATTRIBUTE_SUBMITTED_DATE + "=" +
      DateHandler.getOracleToDateFunction(purchaseRequestBean.getSubmittedDate()) + "," +
     ATTRIBUTE_LAST_UPDATED + "=" +
      DateHandler.getOracleToDateFunction(purchaseRequestBean.getLastUpdated()) + "," +
     ATTRIBUTE_LAST_UPDATED_BY + "=" +
      StringHandler.nullIfZero(purchaseRequestBean.getLastUpdatedBy()) + "," +
     ATTRIBUTE_POS_USER + "=" +
      StringHandler.nullIfZero(purchaseRequestBean.getPosUser()) + "," +
     ATTRIBUTE_MR_RELEASED_DATE + "=" +
      DateHandler.getOracleToDateFunction(purchaseRequestBean.getMrReleasedDate()) + "," +
     ATTRIBUTE_FINANCE_APPROVED_DATE + "=" +
      DateHandler.getOracleToDateFunction(purchaseRequestBean.getFinanceApprovedDate()) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" +
      purchaseRequestBean.getPrNumber();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

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

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection purchaseRequestBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PurchaseRequestBean purchaseRequestBean = new PurchaseRequestBean();
      load(dataSetRow, purchaseRequestBean);
      purchaseRequestBeanColl.add(purchaseRequestBean);
    }

    return purchaseRequestBeanColl;
  }

   public int updateCreditCard(BigDecimal prNumber, String ccName, BigDecimal ccNumber, Date ccExpirationDate)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = updateCreditCard(prNumber, ccName, ccNumber, ccExpirationDate, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int updateCreditCard(BigDecimal prNumber, String ccName, BigDecimal ccNumber, Date ccExpirationDate, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
       StringHandler.nullIfZero(ccNumber) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(ccExpirationDate) + "," +
     ATTRIBUTE_CREDIT_CARD_NAME + "=" +
      SqlHandler.delimitString(ccName) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" + prNumber;
    return new SqlManager().update(conn, query);
   }

   public int updatePrStatus(BigDecimal prNumber, String prStatus, Date submittedDate)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = updatePrStatus(prNumber, prStatus, submittedDate, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int updatePrStatus(BigDecimal prNumber, String prStatus, Date submittedDate, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PR_STATUS + "=" +
       SqlHandler.delimitString(prStatus) + "," +
     ATTRIBUTE_SUBMITTED_DATE + "=" +
      DateHandler.getOracleToDateFunction(submittedDate) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" + prNumber;
    return new SqlManager().update(conn, query);
   }
}