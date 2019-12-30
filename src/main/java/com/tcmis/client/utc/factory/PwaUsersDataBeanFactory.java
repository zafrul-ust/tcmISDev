package com.tcmis.client.utc.factory;

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
import com.tcmis.client.utc.beans.PwaUsersDataBean;

/******************************************************************************
 * CLASSNAME: PwaUsersDataBeanFactory <br>
 * @version: 1.0, May 18, 2005 <br>
 *****************************************************************************/

public class PwaUsersDataBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_FTP_LOAD_DATE = "FTP_LOAD_DATE";
  public String ATTRIBUTE_PERSON_ID_TYPE = "PERSON_ID_TYPE";
  public String ATTRIBUTE_SITE_CODE = "SITE_CODE";
  public String ATTRIBUTE_PERSON_ID = "PERSON_ID";
  public String ATTRIBUTE_ELN = "ELN";
  public String ATTRIBUTE_EFN = "EFN";
  public String ATTRIBUTE_SMTP_ADDRESS = "SMTP_ADDRESS";
  public String ATTRIBUTE_PC_ACRONYM = "PC_ACRONYM";
  public String ATTRIBUTE_DEPTID = "DEPTID";
  public String ATTRIBUTE_HAAS_ENABLE = "HAAS_ENABLE";
  public String ATTRIBUTE_CREATE_MR = "CREATE_MR";
  public String ATTRIBUTE_RELEASER = "RELEASER";
  public String ATTRIBUTE_CREATE_NEW_CHEMICAL = "CREATE_NEW_CHEMICAL";
  public String ATTRIBUTE_ADMINISTRATOR = "ADMINISTRATOR";
  public String ATTRIBUTE_CREATE_REPORTS = "CREATE_REPORTS";
  public String ATTRIBUTE_APPROVED_WORK_AREAS = "APPROVED_WORK_AREAS";
  public String ATTRIBUTE_DOLLAR_LIMIT = "DOLLAR_LIMIT";
  public String ATTRIBUTE_APPROVER = "APPROVER";
  public String ATTRIBUTE_APPROVER_ID_TYPE = "APPROVER_ID_TYPE";
  public String ATTRIBUTE_APPROVER_ID = "APPROVER_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_PERSONNEL_ID_ID = "PERSONNEL_ID_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_TCM_APPROVER_ID = "TCM_APPROVER_ID";

  //table name
  public String TABLE = "PWA_USERS_DATA";

  //constructor
  public PwaUsersDataBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("ftpLoadDate")) {
      return ATTRIBUTE_FTP_LOAD_DATE;
    }
    else if (attributeName.equals("personIdType")) {
      return ATTRIBUTE_PERSON_ID_TYPE;
    }
    else if (attributeName.equals("siteCode")) {
      return ATTRIBUTE_SITE_CODE;
    }
    else if (attributeName.equals("personId")) {
      return ATTRIBUTE_PERSON_ID;
    }
    else if (attributeName.equals("eln")) {
      return ATTRIBUTE_ELN;
    }
    else if (attributeName.equals("efn")) {
      return ATTRIBUTE_EFN;
    }
    else if (attributeName.equals("smtpAddress")) {
      return ATTRIBUTE_SMTP_ADDRESS;
    }
    else if (attributeName.equals("pcAcronym")) {
      return ATTRIBUTE_PC_ACRONYM;
    }
    else if (attributeName.equals("deptid")) {
      return ATTRIBUTE_DEPTID;
    }
    else if (attributeName.equals("haasEnable")) {
      return ATTRIBUTE_HAAS_ENABLE;
    }
    else if (attributeName.equals("createMr")) {
      return ATTRIBUTE_CREATE_MR;
    }
    else if (attributeName.equals("releaser")) {
      return ATTRIBUTE_RELEASER;
    }
    else if (attributeName.equals("createNewChemical")) {
      return ATTRIBUTE_CREATE_NEW_CHEMICAL;
    }
    else if (attributeName.equals("administrator")) {
      return ATTRIBUTE_ADMINISTRATOR;
    }
    else if (attributeName.equals("createReports")) {
      return ATTRIBUTE_CREATE_REPORTS;
    }
    else if (attributeName.equals("approvedWorkAreas")) {
      return ATTRIBUTE_APPROVED_WORK_AREAS;
    }
    else if (attributeName.equals("dollarLimit")) {
      return ATTRIBUTE_DOLLAR_LIMIT;
    }
    else if (attributeName.equals("approver")) {
      return ATTRIBUTE_APPROVER;
    }
    else if (attributeName.equals("approverIdType")) {
      return ATTRIBUTE_APPROVER_ID_TYPE;
    }
    else if (attributeName.equals("approverId")) {
      return ATTRIBUTE_APPROVER_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("personnelIdId")) {
      return ATTRIBUTE_PERSONNEL_ID_ID;
    }
    else if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("tcmApproverId")) {
      return ATTRIBUTE_TCM_APPROVER_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PwaUsersDataBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(PwaUsersDataBean pwaUsersDataBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("ftpLoadDate", "SearchCriterion.EQUALS",
     "" + pwaUsersDataBean.getFtpLoadDate());
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
   public int delete(PwaUsersDataBean pwaUsersDataBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("ftpLoadDate", "SearchCriterion.EQUALS",
     "" + pwaUsersDataBean.getFtpLoadDate());
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

  public int deleteAllRecords() throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = deleteAllRecords(connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int deleteAllRecords(Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE;
    return new SqlManager().update(conn, sqlQuery);
  }


  //insert
  public int insert(PwaUsersDataBean pwaUsersDataBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(pwaUsersDataBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(PwaUsersDataBean pwaUsersDataBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_FTP_LOAD_DATE + "," +
        ATTRIBUTE_PERSON_ID_TYPE + "," +
        ATTRIBUTE_SITE_CODE + "," +
        ATTRIBUTE_PERSON_ID + "," +
        ATTRIBUTE_ELN + "," +
        ATTRIBUTE_EFN + "," +
        ATTRIBUTE_SMTP_ADDRESS + "," +
        ATTRIBUTE_PC_ACRONYM + "," +
        ATTRIBUTE_DEPTID + "," +
        ATTRIBUTE_HAAS_ENABLE + "," +
        ATTRIBUTE_CREATE_MR + "," +
        ATTRIBUTE_RELEASER + "," +
        ATTRIBUTE_CREATE_NEW_CHEMICAL + "," +
        ATTRIBUTE_ADMINISTRATOR + "," +
        ATTRIBUTE_CREATE_REPORTS + "," +
        ATTRIBUTE_APPROVED_WORK_AREAS + "," +
        ATTRIBUTE_DOLLAR_LIMIT + "," +
        ATTRIBUTE_APPROVER + "," +
        ATTRIBUTE_APPROVER_ID_TYPE + "," +
        ATTRIBUTE_APPROVER_ID + "," +
        ATTRIBUTE_FACILITY_ID + "," +
        ATTRIBUTE_PERSONNEL_ID_ID + "," +
        ATTRIBUTE_PERSONNEL_ID + "," +
        ATTRIBUTE_TCM_APPROVER_ID + ")" +
        " values (" +
        DateHandler.getOracleToDateFunction(pwaUsersDataBean.getFtpLoadDate()) +
        "," +
        SqlHandler.delimitString(pwaUsersDataBean.getPersonIdType()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getSiteCode()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getPersonId()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getEln()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getEfn()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getSmtpAddress()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getPcAcronym()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getDeptid()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getHaasEnable()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getCreateMr()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getReleaser()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getCreateNewChemical()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getAdministrator()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getCreateReports()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getApprovedWorkAreas()) + "," +
        pwaUsersDataBean.getDollarLimit() + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getApprover()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getApproverIdType()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getApproverId()) + "," +
        SqlHandler.delimitString(pwaUsersDataBean.getFacilityId()) + "," +
        pwaUsersDataBean.getPersonnelIdId() + "," +
        pwaUsersDataBean.getPersonnelId() + "," +
        pwaUsersDataBean.getTcmApproverId() + ")";
    return sqlManager.update(conn, query);
  }

  /*
    //update
    public int update(PwaUsersDataBean pwaUsersDataBean)
     throws BaseException {
     Connection connection = null;
     int result = 0;
     try {
      connection = getDbManager().getConnection();
      result = update(pwaUsersDataBean, connection);
     }
     finally {
      this.getDbManager().returnConnection(connection);
     }
     return result;
    }
    public int update(PwaUsersDataBean pwaUsersDataBean, Connection conn)
     throws BaseException {
     String query  = "update " + TABLE + " set " +
      ATTRIBUTE_FTP_LOAD_DATE + "=" +
       DateHandler.getOracleToDateFunction(pwaUsersDataBean.getFtpLoadDate()) + "," +
      ATTRIBUTE_PERSON_ID_TYPE + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getPersonIdType()) + "," +
      ATTRIBUTE_SITE_CODE + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getSiteCode()) + "," +
      ATTRIBUTE_PERSON_ID + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getPersonId()) + "," +
      ATTRIBUTE_ELN + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getEln()) + "," +
      ATTRIBUTE_EFN + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getEfn()) + "," +
      ATTRIBUTE_SMTP_ADDRESS + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getSmtpAddress()) + "," +
      ATTRIBUTE_PC_ACRONYM + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getPcAcronym()) + "," +
      ATTRIBUTE_DEPTID + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getDeptid()) + "," +
      ATTRIBUTE_HAAS_ENABLE + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getHaasEnable()) + "," +
      ATTRIBUTE_CREATE_MR + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getCreateMr()) + "," +
      ATTRIBUTE_RELEASER + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getReleaser()) + "," +
      ATTRIBUTE_CREATE_NEW_CHEMICAL + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getCreateNewChemical()) + "," +
      ATTRIBUTE_ADMINISTRATOR + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getAdministrator()) + "," +
      ATTRIBUTE_CREATE_REPORTS + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getCreateReports()) + "," +
      ATTRIBUTE_APPROVED_WORK_AREAS + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getApprovedWorkAreas()) + "," +
      ATTRIBUTE_DOLLAR_LIMIT + "=" +
       StringHandler.nullIfZero(pwaUsersDataBean.getDollarLimit()) + "," +
      ATTRIBUTE_APPROVER + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getApprover()) + "," +
      ATTRIBUTE_APPROVER_ID_TYPE + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getApproverIdType()) + "," +
      ATTRIBUTE_APPROVER_ID + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getApproverId()) + "," +
      ATTRIBUTE_FACILITY_ID + "=" +
       SqlHandler.delimitString(pwaUsersDataBean.getFacilityId()) + "," +
      ATTRIBUTE_PERSONNEL_ID_ID + "=" +
       StringHandler.nullIfZero(pwaUsersDataBean.getPersonnelIdId()) + "," +
      ATTRIBUTE_PERSONNEL_ID + "=" +
       StringHandler.nullIfZero(pwaUsersDataBean.getPersonnelId()) + "," +
      ATTRIBUTE_TCM_APPROVER_ID + "=" +
       StringHandler.nullIfZero(pwaUsersDataBean.getTcmApproverId()) + " " +
      "where " + ATTRIBUTE_FTP_LOAD_DATE + "=" +
       pwaUsersDataBean.getFtpLoadDate();
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

    Collection pwaUsersDataBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PwaUsersDataBean pwaUsersDataBean = new PwaUsersDataBean();
      load(dataSetRow, pwaUsersDataBean);
      pwaUsersDataBeanColl.add(pwaUsersDataBean);
    }

    return pwaUsersDataBeanColl;
  } //end of method

      /*****************************************************************************
   * Calls named procedure. Note that date
   * arguments should be passed as <code>java.sql.Timestamp</code>
   *
   * @param procName name of the procedure to call
   *        inParameters arguments to call the procedure with
   * @throws BaseException If there are problems calling the procedure
       ****************************************************************************/
  public void doProcedure(String procedure, Collection inParameters) throws
      BaseException {
    Connection connection = null;
    try {
      connection = getDbManager().getConnection();
      this.doProcedure(connection, procedure, inParameters);
    }
    finally {
      try {
        getDbManager().returnConnection(connection);
      }
      catch (Exception e) {
        //ignore
      }
    }
  }

      /*****************************************************************************
   * Calls named procedure. Note that date
   * arguments should be passed as <code>java.sql.Timestamp</code>
   *
   * @param connection database connection to use
   *        procName name of the procedure to call
   *        inParameters arguments to call the procedure with
   * @throws BaseException If there are problems calling the procedure
       ****************************************************************************/
  public void doProcedure(Connection connection,
                          String procedure,
                          Collection inParameters) throws BaseException {
    new SqlManager().doProcedure(connection, procedure, inParameters);
  }

} //end of class