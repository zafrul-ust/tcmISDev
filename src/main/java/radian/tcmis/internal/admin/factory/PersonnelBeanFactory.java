package radian.tcmis.internal.admin.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.framework.BaseBeanFactory;
import radian.tcmis.common.db.SqlManager;
import radian.tcmis.common.util.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.internal.admin.beans.PersonnelBean;

/******************************************************************************
 * CLASSNAME: PersonnelBeanFactory <br>
 * @version: 1.0, Apr 2, 2004 <br>
 *****************************************************************************/

public class PersonnelBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_MAIL_LOCATION = "MAIL_LOCATION";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_FIRST_NAME = "FIRST_NAME";
  public String ATTRIBUTE_LAST_NAME = "LAST_NAME";
  public String ATTRIBUTE_MID_INITIAL = "MID_INITIAL";
  public String ATTRIBUTE_PHONE = "PHONE";
  public String ATTRIBUTE_ALT_PHONE = "ALT_PHONE";
  public String ATTRIBUTE_PAGER = "PAGER";
  public String ATTRIBUTE_EMAIL = "EMAIL";
  public String ATTRIBUTE_SHIPPING_LOCATION = "SHIPPING_LOCATION";
  public String ATTRIBUTE_FAX = "FAX";
  public String ATTRIBUTE_PROXY_NAME = "PROXY_NAME";
  public String ATTRIBUTE_PROXY_PORT = "PROXY_PORT";
  public String ATTRIBUTE_LOGON_ID = "LOGON_ID";
  public String ATTRIBUTE_PASSWORD = "PASSWORD";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_PASSWORD_EXPIRE_DATE = "PASSWORD_EXPIRE_DATE";
  public String ATTRIBUTE_PREFERRED_ACCOUNT_SYS_ID = "PREFERRED_ACCOUNT_SYS_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

  //sequence and table names
  public String SEQUENCE = "PERSONNEL_SEQ";
  public String TABLE = "PERSONNEL";

  //constructor
  public PersonnelBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    if (attributeName.equals("mailLocation")) {
      return ATTRIBUTE_MAIL_LOCATION;
    }
    if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    if (attributeName.equals("firstName")) {
      return ATTRIBUTE_FIRST_NAME;
    }
    if (attributeName.equals("lastName")) {
      return ATTRIBUTE_LAST_NAME;
    }
    if (attributeName.equals("midInitial")) {
      return ATTRIBUTE_MID_INITIAL;
    }
    if (attributeName.equals("phone")) {
      return ATTRIBUTE_PHONE;
    }
    if (attributeName.equals("altPhone")) {
      return ATTRIBUTE_ALT_PHONE;
    }
    if (attributeName.equals("pager")) {
      return ATTRIBUTE_PAGER;
    }
    if (attributeName.equals("email")) {
      return ATTRIBUTE_EMAIL;
    }
    if (attributeName.equals("shippingLocation")) {
      return ATTRIBUTE_SHIPPING_LOCATION;
    }
    if (attributeName.equals("fax")) {
      return ATTRIBUTE_FAX;
    }
    if (attributeName.equals("proxyName")) {
      return ATTRIBUTE_PROXY_NAME;
    }
    if (attributeName.equals("proxyPort")) {
      return ATTRIBUTE_PROXY_PORT;
    }
    if (attributeName.equals("logonId")) {
      return ATTRIBUTE_LOGON_ID;
    }
    if (attributeName.equals("password")) {
      return ATTRIBUTE_PASSWORD;
    }
    if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    if (attributeName.equals("passwordExpireDate")) {
      return ATTRIBUTE_PASSWORD_EXPIRE_DATE;
    }
    if (attributeName.equals("preferredAccountSysId")) {
      return ATTRIBUTE_PREFERRED_ACCOUNT_SYS_ID;
    }
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PersonnelBean.class);
  }

  /*
//delete
   public int delete(PersonnelBean personnelBean, TcmISDBModel dbModel)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "=",
     "" + personnelBean.getPersonnelId());
    return delete(criteria, dbModel.getConnection());
   }
   public int delete(PersonnelBean personnelBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "=",
     "" + personnelBean.getPersonnelId());
    return delete(criteria, conn);
   }
   public int delete(SearchCriteria criteria, TcmISDBModel dbModel)
    throws BaseException {
    return delete(criteria, dbModel.getConnection());
   }
   public int delete(SearchCriteria criteria, Connection conn)
    throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
     getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
   }
//insert
   public int insert(PersonnelBean personnelBean, TcmISDBModel dbModel)
    throws BaseException {
    return insert(personnelBean, dbModel.getConnection());
   }
   public int insert(PersonnelBean personnelBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    personnelBean.
     setPersonnelId(sqlManager.getOracleSequence(conn, SEQUENCE));
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_MAIL_LOCATION + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_FIRST_NAME + "," +
     ATTRIBUTE_LAST_NAME + "," +
     ATTRIBUTE_MID_INITIAL + "," +
     ATTRIBUTE_PHONE + "," +
     ATTRIBUTE_ALT_PHONE + "," +
     ATTRIBUTE_PAGER + "," +
     ATTRIBUTE_EMAIL + "," +
     ATTRIBUTE_SHIPPING_LOCATION + "," +
     ATTRIBUTE_FAX + "," +
     ATTRIBUTE_PROXY_NAME + "," +
     ATTRIBUTE_PROXY_PORT + "," +
     ATTRIBUTE_LOGON_ID + "," +
     ATTRIBUTE_PASSWORD + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_PASSWORD_EXPIRE_DATE + "," +
     ATTRIBUTE_PREFERRED_ACCOUNT_SYS_ID + "," +
     ATTRIBUTE_COMPANY_ID + ")" +
     StringHandler.nullIfZero(personnelBean.getPersonnelId()) + "," +
     SqlHandler.delimitString(personnelBean.getMailLocation()) + "," +
     SqlHandler.delimitString(personnelBean.getFacilityId()) + "," +
     SqlHandler.delimitString(personnelBean.getFirstName()) + "," +
     SqlHandler.delimitString(personnelBean.getLastName()) + "," +
     SqlHandler.delimitString(personnelBean.getMidInitial()) + "," +
     SqlHandler.delimitString(personnelBean.getPhone()) + "," +
     SqlHandler.delimitString(personnelBean.getAltPhone()) + "," +
     SqlHandler.delimitString(personnelBean.getPager()) + "," +
     SqlHandler.delimitString(personnelBean.getEmail()) + "," +
     SqlHandler.delimitString(personnelBean.getShippingLocation()) + "," +
     SqlHandler.delimitString(personnelBean.getFax()) + "," +
     SqlHandler.delimitString(personnelBean.getProxyName()) + "," +
     StringHandler.nullIfZero(personnelBean.getProxyPort()) + "," +
     SqlHandler.delimitString(personnelBean.getLogonId()) + "," +
     SqlHandler.delimitString(personnelBean.getPassword()) + "," +
     SqlHandler.delimitString(personnelBean.getStatus()) + "," +
     DateHandler.getOracleToDateFunction(personnelBean.getPasswordExpireDate()) + "," +
     SqlHandler.delimitString(personnelBean.getPreferredAccountSysId()) + "," +
     SqlHandler.delimitString(personnelBean.getCompanyId()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(PersonnelBean personnelBean, TcmISDBModel dbModel)
    throws BaseException {
    return update(personnelBean, dbModel.getConnection());
   }
   public int update(PersonnelBean personnelBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(personnelBean.getPersonnelId()) + "," +
     ATTRIBUTE_MAIL_LOCATION + "=" +
      SqlHandler.delimitString(personnelBean.getMailLocation()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(personnelBean.getFacilityId()) + "," +
     ATTRIBUTE_FIRST_NAME + "=" +
      SqlHandler.delimitString(personnelBean.getFirstName()) + "," +
     ATTRIBUTE_LAST_NAME + "=" +
      SqlHandler.delimitString(personnelBean.getLastName()) + "," +
     ATTRIBUTE_MID_INITIAL + "=" +
      SqlHandler.delimitString(personnelBean.getMidInitial()) + "," +
     ATTRIBUTE_PHONE + "=" +
      SqlHandler.delimitString(personnelBean.getPhone()) + "," +
     ATTRIBUTE_ALT_PHONE + "=" +
      SqlHandler.delimitString(personnelBean.getAltPhone()) + "," +
     ATTRIBUTE_PAGER + "=" +
      SqlHandler.delimitString(personnelBean.getPager()) + "," +
     ATTRIBUTE_EMAIL + "=" +
      SqlHandler.delimitString(personnelBean.getEmail()) + "," +
     ATTRIBUTE_SHIPPING_LOCATION + "=" +
      SqlHandler.delimitString(personnelBean.getShippingLocation()) + "," +
     ATTRIBUTE_FAX + "=" +
      SqlHandler.delimitString(personnelBean.getFax()) + "," +
     ATTRIBUTE_PROXY_NAME + "=" +
      SqlHandler.delimitString(personnelBean.getProxyName()) + "," +
     ATTRIBUTE_PROXY_PORT + "=" +
      StringHandler.nullIfZero(personnelBean.getProxyPort()) + "," +
     ATTRIBUTE_LOGON_ID + "=" +
      SqlHandler.delimitString(personnelBean.getLogonId()) + "," +
     ATTRIBUTE_PASSWORD + "=" +
      SqlHandler.delimitString(personnelBean.getPassword()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(personnelBean.getStatus()) + "," +
     ATTRIBUTE_PASSWORD_EXPIRE_DATE + "=" +
      DateHandler.getOracleToDateFunction(personnelBean.getPasswordExpireDate()) + "," +
     ATTRIBUTE_PREFERRED_ACCOUNT_SYS_ID + "=" +
       SqlHandler.delimitString(personnelBean.getPreferredAccountSysId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(personnelBean.getCompanyId()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(personnelBean.getPersonnelId());
    return new SqlManager().update(conn, query);
   }
   */
  //select
  public Collection select(SearchCriteria criteria, TcmISDBModel dbModel) throws
      BaseException {

    return select(criteria, dbModel.getConnection());
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection personnelBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PersonnelBean personnelBean = new PersonnelBean();
      load(dataSetRow, personnelBean);
      personnelBeanColl.add(personnelBean);
    }

    return personnelBeanColl;
  }

  /*public Collection selectHubInfo(PersonnelBean bean, TcmISDBModel dbModel) throws
      BaseException {

    Collection personnelBeanColl = new Vector();

    String query = "select distinct h.preferred_warehouse,h.branch_plant " +
        "from user_group_member u, hub_destination_facility_view h " +
        "where u.personnel_id = " +
        StringHandler.nullIfZero(bean.getPersonnelId()) +
        " and u.company_id = " + SqlHandler.delimitString(bean.getCompanyId()) +
        " and h.preferred_warehouse = u.facility_id " +
        " and h.company_id = u.company_id";
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(dbModel.getConnection(), query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PersonnelBean personnelBean = new PersonnelBean();
      load(dataSetRow, personnelBean);
      personnelBeanColl.add(personnelBean);
    }

    return personnelBeanColl;
  }*/

  /*public Collection selectWarehouseInfo(PersonnelBean bean,
                                        TcmISDBModel dbModel) throws
      BaseException {

    Collection personnelBeanColl = new Vector();

    String query = "select distinct h.branch_plant, " +
        "h.facility_id, " +
        "h.facility_name, " +
        "h.company_id " +
        "from user_group_member u, " +
        "hub_destination_facility_view h " +
        "where u.personnel_id = " +
        StringHandler.nullIfZero(bean.getPersonnelId()) +
        " and u.company_id = " + SqlHandler.delimitString(bean.getCompanyId()) +
        " and h.preferred_warehouse = u.facility_id " +
        "order by h.branch_plant,h.facility_id,h.company_id";
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(dbModel.getConnection(), query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PersonnelBean personnelBean = new PersonnelBean();
      load(dataSetRow, personnelBean);
      personnelBeanColl.add(personnelBean);
    }

    return personnelBeanColl;
  }*/

}