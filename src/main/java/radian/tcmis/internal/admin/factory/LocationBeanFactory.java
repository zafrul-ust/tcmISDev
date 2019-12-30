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
import radian.tcmis.internal.admin.beans.LocationBean;

/******************************************************************************
 * CLASSNAME: LocationBeanFactory <br>
 * @version: 1.0, Mar 31, 2004 <br>
 *****************************************************************************/

public class LocationBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
  public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
  public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
  public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
  public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
  public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
  public String ATTRIBUTE_CITY = "CITY";
  public String ATTRIBUTE_ZIP = "ZIP";
  public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
  public String ATTRIBUTE_CLIENT_LOCATION_CODE = "CLIENT_LOCATION_CODE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_DATE_LAST_MODIFIED = "DATE_LAST_MODIFIED";
  public String ATTRIBUTE_DATE_SENT_TO_HAAS = "DATE_SENT_TO_HAAS";
  public String ATTRIBUTE_LOCATION_CONTACT = "LOCATION_CONTACT";
  public String ATTRIBUTE_LOCATION_SHORT_NAME = "LOCATION_SHORT_NAME";
  public String ATTRIBUTE_COUNTY = "COUNTY";

  //sequence and table names
  public String SEQUENCE = "LOCATION_SEQ";
  public String TABLE = "LOCATION";

  //constructor
  public LocationBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("locationId")) {
      return ATTRIBUTE_LOCATION_ID;
    }
    else if (attributeName.equals("countryAbbrev")) {
      return ATTRIBUTE_COUNTRY_ABBREV;
    }
    else if (attributeName.equals("stateAbbrev")) {
      return ATTRIBUTE_STATE_ABBREV;
    }
    else if (attributeName.equals("addressLine1")) {
      return ATTRIBUTE_ADDRESS_LINE_1;
    }
    else if (attributeName.equals("addressLine2")) {
      return ATTRIBUTE_ADDRESS_LINE_2;
    }
    else if (attributeName.equals("addressLine3")) {
      return ATTRIBUTE_ADDRESS_LINE_3;
    }
    else if (attributeName.equals("city")) {
      return ATTRIBUTE_CITY;
    }
    else if (attributeName.equals("zip")) {
      return ATTRIBUTE_ZIP;
    }
    else if (attributeName.equals("locationDesc")) {
      return ATTRIBUTE_LOCATION_DESC;
    }
    else if (attributeName.equals("clientLocationCode")) {
      return ATTRIBUTE_CLIENT_LOCATION_CODE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("dateLastModified")) {
      return ATTRIBUTE_DATE_LAST_MODIFIED;
    }
    else if (attributeName.equals("dateSentToHaas")) {
      return ATTRIBUTE_DATE_SENT_TO_HAAS;
    }
    else if (attributeName.equals("locationContact")) {
      return ATTRIBUTE_LOCATION_CONTACT;
    }
    else if (attributeName.equals("locationShortName")) {
      return ATTRIBUTE_LOCATION_SHORT_NAME;
    }
    else if (attributeName.equals("county")) {
      return ATTRIBUTE_COUNTY;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, LocationBean.class);
  }

  /*
//delete
   public int delete(LocationBean locationBean, TcmISDBModel dbModel)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("locationId", "=",
     "" + locationBean.getLocationId());
    return delete(criteria, dbModel.getConnection());
   }
   public int delete(LocationBean locationBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("locationId", "=",
     "" + locationBean.getLocationId());
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
   public int insert(LocationBean locationBean, TcmISDBModel dbModel)
    throws BaseException {
    return insert(locationBean, dbModel.getConnection());
   }
   public int insert(LocationBean locationBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    locationBean.
     setLocationId(sqlManager.getOracleSequence(conn, SEQUENCE));
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_LOCATION_ID + "," +
     ATTRIBUTE_COUNTRY_ABBREV + "," +
     ATTRIBUTE_STATE_ABBREV + "," +
     ATTRIBUTE_ADDRESS_LINE_1 + "," +
     ATTRIBUTE_ADDRESS_LINE_2 + "," +
     ATTRIBUTE_ADDRESS_LINE_3 + "," +
     ATTRIBUTE_CITY + "," +
     ATTRIBUTE_ZIP + "," +
     ATTRIBUTE_LOCATION_DESC + "," +
     ATTRIBUTE_CLIENT_LOCATION_CODE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_DATE_LAST_MODIFIED + "," +
     ATTRIBUTE_DATE_SENT_TO_HAAS + "," +
     ATTRIBUTE_LOCATION_CONTACT + "," +
     ATTRIBUTE_LOCATION_SHORT_NAME + "," +
     ATTRIBUTE_COUNTY + ")" +
     SqlHandler.delimitString(locationBean.getLocationId()) + "," +
     SqlHandler.delimitString(locationBean.getCountryAbbrev()) + "," +
     SqlHandler.delimitString(locationBean.getStateAbbrev()) + "," +
     SqlHandler.delimitString(locationBean.getAddressLine1()) + "," +
     SqlHandler.delimitString(locationBean.getAddressLine2()) + "," +
     SqlHandler.delimitString(locationBean.getAddressLine3()) + "," +
     SqlHandler.delimitString(locationBean.getCity()) + "," +
     SqlHandler.delimitString(locationBean.getZip()) + "," +
     SqlHandler.delimitString(locationBean.getLocationDesc()) + "," +
     SqlHandler.delimitString(locationBean.getClientLocationCode()) + "," +
     SqlHandler.delimitString(locationBean.getCompanyId()) + "," +
       DateHandler.getOracleToDateFunction(locationBean.getDateLastModified()) + "," +
       DateHandler.getOracleToDateFunction(locationBean.getDateSentToHaas()) + "," +
     StringHandler.nullIfZero(locationBean.getLocationContact()) + "," +
     SqlHandler.delimitString(locationBean.getLocationShortName()) + "," +
     SqlHandler.delimitString(locationBean.getCounty()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(LocationBean locationBean, TcmISDBModel dbModel)
    throws BaseException {
    return update(locationBean, dbModel.getConnection());
   }
   public int update(LocationBean locationBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_LOCATION_ID + "=" +
      SqlHandler.delimitString(locationBean.getLocationId()) + "," +
     ATTRIBUTE_COUNTRY_ABBREV + "=" +
      SqlHandler.delimitString(locationBean.getCountryAbbrev()) + "," +
     ATTRIBUTE_STATE_ABBREV + "=" +
      SqlHandler.delimitString(locationBean.getStateAbbrev()) + "," +
     ATTRIBUTE_ADDRESS_LINE_1 + "=" +
      SqlHandler.delimitString(locationBean.getAddressLine1()) + "," +
     ATTRIBUTE_ADDRESS_LINE_2 + "=" +
      SqlHandler.delimitString(locationBean.getAddressLine2()) + "," +
     ATTRIBUTE_ADDRESS_LINE_3 + "=" +
      SqlHandler.delimitString(locationBean.getAddressLine3()) + "," +
     ATTRIBUTE_CITY + "=" +
      SqlHandler.delimitString(locationBean.getCity()) + "," +
     ATTRIBUTE_ZIP + "=" +
      SqlHandler.delimitString(locationBean.getZip()) + "," +
     ATTRIBUTE_LOCATION_DESC + "=" +
      SqlHandler.delimitString(locationBean.getLocationDesc()) + "," +
     ATTRIBUTE_CLIENT_LOCATION_CODE + "=" +
      SqlHandler.delimitString(locationBean.getClientLocationCode()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(locationBean.getCompanyId()) + "," +
     ATTRIBUTE_DATE_LAST_MODIFIED + "=" +
       DateHandler.getOracleToDateFunction(locationBean.getDateLastModified()) + "," +
     ATTRIBUTE_DATE_SENT_TO_HAAS + "=" +
       DateHandler.getOracleToDateFunction(locationBean.getDateSentToHaas()) + "," +
     ATTRIBUTE_LOCATION_CONTACT + "=" +
      StringHandler.nullIfZero(locationBean.getLocationContact()) + "," +
     ATTRIBUTE_LOCATION_SHORT_NAME + "=" +
      SqlHandler.delimitString(locationBean.getLocationShortName()) + "," +
     ATTRIBUTE_COUNTY + "=" +
      SqlHandler.delimitString(locationBean.getCounty()) + " " +
     "where " + ATTRIBUTE_LOCATION_ID + "=" +
      StringHandler.nullIfZero(locationBean.getLocationId());
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

    Collection locationBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      LocationBean locationBean = new LocationBean();
      load(dataSetRow, locationBean);
      locationBeanColl.add(locationBean);
    }

    return locationBeanColl;
  }
}