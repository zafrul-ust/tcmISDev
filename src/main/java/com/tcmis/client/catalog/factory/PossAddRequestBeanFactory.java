package com.tcmis.client.catalog.factory;

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
import com.tcmis.client.catalog.beans.PossAddRequestBean;

/******************************************************************************
 * CLASSNAME: PossAddRequestBeanFactory <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/

public class PossAddRequestBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SPEC_ID = "SPEC_ID";
  public String ATTRIBUTE_MSDS_NUMBER = "MSDS_NUMBER";
  public String ATTRIBUTE_SUGGESTED_VENDOR = "SUGGESTED_VENDOR";
  public String ATTRIBUTE_MAX_SUGGESTED_VENDOR_PART_NO = "MAX_SUGGESTED_VENDOR_PART_NO";
  public String ATTRIBUTE_PROPER_SHIPPING_NAME = "PROPER_SHIPPING_NAME";
  public String ATTRIBUTE_HAZARD_CLASS = "HAZARD_CLASS";
  public String ATTRIBUTE_UN_NUMBER = "UN_NUMBER";
  public String ATTRIBUTE_NA_NUMBER = "NA_NUMBER";
  public String ATTRIBUTE_PACKING_GROUP = "PACKING_GROUP";
  public String ATTRIBUTE_NFPA_HMIS_NUMBERS = "NFPA_HMIS_NUMBERS";
  public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
  public String ATTRIBUTE_POSS_NO = "POSS_NO";
  public String ATTRIBUTE_TRADENAME = "TRADENAME";
  public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
  public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
  public String ATTRIBUTE_POSS_SIZE = "POSS_SIZE";
  public String ATTRIBUTE_MANUFACTURERS_PART = "MANUFACTURERS_PART";
  public String ATTRIBUTE_DWG_OR_SPEC_NUMBER = "DWG_OR_SPEC_NUMBER";
  public String ATTRIBUTE_TESTING_REQS_DOCUMENT = "TESTING_REQS_DOCUMENT";
  public String ATTRIBUTE_QA_ATT_PKG_ATT = "QA_ATT_PKG_ATT";
  public String ATTRIBUTE_DUP_INSTOCK = "DUP_INSTOCK";
  public String ATTRIBUTE_DUP_PARTNO = "DUP_PARTNO";
  public String ATTRIBUTE_NEW_POSS = "NEW_POSS";
  public String ATTRIBUTE_REPLACE_POSS = "REPLACE_POSS";
  public String ATTRIBUTE_REPL_PARTNO = "REPL_PARTNO";
  public String ATTRIBUTE_ISS_TOZERO_DELETE = "ISS_TOZERO_DELETE";
  public String ATTRIBUTE_DEL_UPON_RECV = "DEL_UPON_RECV";
  public String ATTRIBUTE_DISP_OTHER = "DISP_OTHER";
  public String ATTRIBUTE_DISP_OTHER_TEXT = "DISP_OTHER_TEXT";
  public String ATTRIBUTE_EST_MON_USAGE = "EST_MON_USAGE";
  public String ATTRIBUTE_STORE = "STORE";
  public String ATTRIBUTE_SL_INIT_RETEST = "SL_INIT_RETEST";
  public String ATTRIBUTE_AGE = "AGE";
  public String ATTRIBUTE_ORD_FACILITY = "ORD_FACILITY";
  public String ATTRIBUTE_ORD_SOURCE_CD = "ORD_SOURCE_CD";
  public String ATTRIBUTE_APPROVED_USERS = "APPROVED_USERS";
  public String ATTRIBUTE_REQUESTOR_LAST = "REQUESTOR_LAST";
  public String ATTRIBUTE_REQUESTOR_FIRST = "REQUESTOR_FIRST";
  public String ATTRIBUTE_REQUESTOR_MIDDLE = "REQUESTOR_MIDDLE";
  public String ATTRIBUTE_MFG_ADDRESS = "MFG_ADDRESS";
  public String ATTRIBUTE_MFG_PHONE = "MFG_PHONE";
  public String ATTRIBUTE_DISTRIBUTOR_ADDRESS = "DISTRIBUTOR_ADDRESS";
  public String ATTRIBUTE_DISTRIBUTOR_PHONE = "DISTRIBUTOR_PHONE";
  public String ATTRIBUTE_ADDITIONAL_SPECS = "ADDITIONAL_SPECS";
  public String ATTRIBUTE_SPECIAL_NOTES = "SPECIAL_NOTES";

  //table name
  public String TABLE = "POSS_ADD_REQUEST";

  //constructor
  public PossAddRequestBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("specId")) {
      return ATTRIBUTE_SPEC_ID;
    } else if (attributeName.equals("msdsNumber")) {
      return ATTRIBUTE_MSDS_NUMBER;
    } else if (attributeName.equals("suggestedVendor")) {
      return ATTRIBUTE_SUGGESTED_VENDOR;
    } else if (attributeName.equals("maxSuggestedVendorPartNo")) {
      return ATTRIBUTE_MAX_SUGGESTED_VENDOR_PART_NO;
    } else if (attributeName.equals("properShippingName")) {
      return ATTRIBUTE_PROPER_SHIPPING_NAME;
    } else if (attributeName.equals("hazardClass")) {
      return ATTRIBUTE_HAZARD_CLASS;
    } else if (attributeName.equals("unNumber")) {
      return ATTRIBUTE_UN_NUMBER;
    } else if (attributeName.equals("naNumber")) {
      return ATTRIBUTE_NA_NUMBER;
    } else if (attributeName.equals("packingGroup")) {
      return ATTRIBUTE_PACKING_GROUP;
    } else if (attributeName.equals("nfpaHmisNumbers")) {
      return ATTRIBUTE_NFPA_HMIS_NUMBERS;
    } else if (attributeName.equals("requestId")) {
      return ATTRIBUTE_REQUEST_ID;
    } else if (attributeName.equals("possNo")) {
      return ATTRIBUTE_POSS_NO;
    } else if (attributeName.equals("tradename")) {
      return ATTRIBUTE_TRADENAME;
    } else if (attributeName.equals("description")) {
      return ATTRIBUTE_DESCRIPTION;
    } else if (attributeName.equals("manufacturer")) {
      return ATTRIBUTE_MANUFACTURER;
    } else if (attributeName.equals("possSize")) {
      return ATTRIBUTE_POSS_SIZE;
    } else if (attributeName.equals("manufacturersPart")) {
      return ATTRIBUTE_MANUFACTURERS_PART;
    } else if (attributeName.equals("dwgOrSpecNumber")) {
      return ATTRIBUTE_DWG_OR_SPEC_NUMBER;
    } else if (attributeName.equals("testingReqsDocument")) {
      return ATTRIBUTE_TESTING_REQS_DOCUMENT;
    } else if (attributeName.equals("qaAttPkgAtt")) {
      return ATTRIBUTE_QA_ATT_PKG_ATT;
    } else if (attributeName.equals("dupInstock")) {
      return ATTRIBUTE_DUP_INSTOCK;
    } else if (attributeName.equals("dupPartno")) {
      return ATTRIBUTE_DUP_PARTNO;
    } else if (attributeName.equals("newPoss")) {
      return ATTRIBUTE_NEW_POSS;
    } else if (attributeName.equals("replacePoss")) {
      return ATTRIBUTE_REPLACE_POSS;
    } else if (attributeName.equals("replPartno")) {
      return ATTRIBUTE_REPL_PARTNO;
    } else if (attributeName.equals("issTozeroDelete")) {
      return ATTRIBUTE_ISS_TOZERO_DELETE;
    } else if (attributeName.equals("delUponRecv")) {
      return ATTRIBUTE_DEL_UPON_RECV;
    } else if (attributeName.equals("dispOther")) {
      return ATTRIBUTE_DISP_OTHER;
    } else if (attributeName.equals("dispOtherText")) {
      return ATTRIBUTE_DISP_OTHER_TEXT;
    } else if (attributeName.equals("estMonUsage")) {
      return ATTRIBUTE_EST_MON_USAGE;
    } else if (attributeName.equals("store")) {
      return ATTRIBUTE_STORE;
    } else if (attributeName.equals("slInitRetest")) {
      return ATTRIBUTE_SL_INIT_RETEST;
    } else if (attributeName.equals("age")) {
      return ATTRIBUTE_AGE;
    } else if (attributeName.equals("ordFacility")) {
      return ATTRIBUTE_ORD_FACILITY;
    } else if (attributeName.equals("ordSourceCd")) {
      return ATTRIBUTE_ORD_SOURCE_CD;
    } else if (attributeName.equals("approvedUsers")) {
      return ATTRIBUTE_APPROVED_USERS;
    } else if (attributeName.equals("requestorLast")) {
      return ATTRIBUTE_REQUESTOR_LAST;
    } else if (attributeName.equals("requestorFirst")) {
      return ATTRIBUTE_REQUESTOR_FIRST;
    } else if (attributeName.equals("requestorMiddle")) {
      return ATTRIBUTE_REQUESTOR_MIDDLE;
    } else if (attributeName.equals("mfgAddress")) {
      return ATTRIBUTE_MFG_ADDRESS;
    } else if (attributeName.equals("mfgPhone")) {
      return ATTRIBUTE_MFG_PHONE;
    } else if (attributeName.equals("distributorAddress")) {
      return ATTRIBUTE_DISTRIBUTOR_ADDRESS;
    } else if (attributeName.equals("distributorPhone")) {
      return ATTRIBUTE_DISTRIBUTOR_PHONE;
    } else if (attributeName.equals("additionalSpecs")) {
      return ATTRIBUTE_ADDITIONAL_SPECS;
    } else if (attributeName.equals("specialNotes")) {
      return ATTRIBUTE_SPECIAL_NOTES;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PossAddRequestBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(PossAddRequestBean possAddRequestBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("specId", "SearchCriterion.EQUALS",
     "" + possAddRequestBean.getSpecId());
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
   public int delete(PossAddRequestBean possAddRequestBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("specId", "SearchCriterion.EQUALS",
     "" + possAddRequestBean.getSpecId());
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

  //insert
  public int insert(PossAddRequestBean possAddRequestBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(possAddRequestBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(PossAddRequestBean possAddRequestBean, Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query = "insert into " + TABLE + " (" + ATTRIBUTE_SPEC_ID + "," + ATTRIBUTE_MSDS_NUMBER + "," + ATTRIBUTE_SUGGESTED_VENDOR + "," + ATTRIBUTE_MAX_SUGGESTED_VENDOR_PART_NO + "," + ATTRIBUTE_PROPER_SHIPPING_NAME + "," +
        ATTRIBUTE_HAZARD_CLASS + "," + ATTRIBUTE_UN_NUMBER + "," + ATTRIBUTE_NA_NUMBER + "," + ATTRIBUTE_PACKING_GROUP + "," + ATTRIBUTE_NFPA_HMIS_NUMBERS + "," + ATTRIBUTE_REQUEST_ID + "," + ATTRIBUTE_POSS_NO + "," + ATTRIBUTE_TRADENAME + "," +
        ATTRIBUTE_DESCRIPTION + "," + ATTRIBUTE_MANUFACTURER + "," + ATTRIBUTE_POSS_SIZE + "," + ATTRIBUTE_MANUFACTURERS_PART + "," + ATTRIBUTE_DWG_OR_SPEC_NUMBER + "," + ATTRIBUTE_TESTING_REQS_DOCUMENT + "," + ATTRIBUTE_QA_ATT_PKG_ATT + "," +
        ATTRIBUTE_DUP_INSTOCK + "," + ATTRIBUTE_DUP_PARTNO + "," + ATTRIBUTE_NEW_POSS + "," + ATTRIBUTE_REPLACE_POSS + "," + ATTRIBUTE_REPL_PARTNO + "," + ATTRIBUTE_ISS_TOZERO_DELETE + "," + ATTRIBUTE_DEL_UPON_RECV + "," + ATTRIBUTE_DISP_OTHER + "," +
        ATTRIBUTE_DISP_OTHER_TEXT + "," + ATTRIBUTE_EST_MON_USAGE + "," + ATTRIBUTE_STORE + "," + ATTRIBUTE_SL_INIT_RETEST + "," + ATTRIBUTE_AGE + "," + ATTRIBUTE_ORD_FACILITY + "," + ATTRIBUTE_ORD_SOURCE_CD + "," + ATTRIBUTE_APPROVED_USERS + "," +
        ATTRIBUTE_REQUESTOR_LAST + "," + ATTRIBUTE_REQUESTOR_FIRST + "," + ATTRIBUTE_REQUESTOR_MIDDLE + "," + ATTRIBUTE_MFG_ADDRESS + "," + ATTRIBUTE_MFG_PHONE + "," + ATTRIBUTE_DISTRIBUTOR_ADDRESS + "," + ATTRIBUTE_DISTRIBUTOR_PHONE + "," +
        ATTRIBUTE_ADDITIONAL_SPECS + "," + ATTRIBUTE_SPECIAL_NOTES + ")" + " values (" + SqlHandler.delimitString(possAddRequestBean.getSpecId()) + "," + SqlHandler.delimitString(possAddRequestBean.getMsdsNumber()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getSuggestedVendor()) + "," + SqlHandler.delimitString(possAddRequestBean.getMaxSuggestedVendorPartNo()) + "," + SqlHandler.delimitString(possAddRequestBean.getProperShippingName()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getHazardClass()) + "," + SqlHandler.delimitString(possAddRequestBean.getUnNumber()) + "," + SqlHandler.delimitString(possAddRequestBean.getNaNumber()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getPackingGroup()) + "," + SqlHandler.delimitString(possAddRequestBean.getNfpaHmisNumbers()) + "," + possAddRequestBean.getRequestId() + "," + SqlHandler.delimitString(possAddRequestBean.getPossNo()) +
        "," + SqlHandler.delimitString(possAddRequestBean.getTradename()) + "," + SqlHandler.delimitString(possAddRequestBean.getDescription()) + "," + SqlHandler.delimitString(possAddRequestBean.getManufacturer()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getPossSize()) + "," + SqlHandler.delimitString(possAddRequestBean.getManufacturersPart()) + "," + SqlHandler.delimitString(possAddRequestBean.getDwgOrSpecNumber()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getTestingReqsDocument()) + "," + SqlHandler.delimitString(possAddRequestBean.getQaAttPkgAtt()) + "," + SqlHandler.delimitString(possAddRequestBean.getDupInstock()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getDupPartno()) + "," + SqlHandler.delimitString(possAddRequestBean.getNewPoss()) + "," + SqlHandler.delimitString(possAddRequestBean.getReplacePoss()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getReplPartno()) + "," + SqlHandler.delimitString(possAddRequestBean.getIssTozeroDelete()) + "," + SqlHandler.delimitString(possAddRequestBean.getDelUponRecv()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getDispOther()) + "," + SqlHandler.delimitString(possAddRequestBean.getDispOtherText()) + "," + SqlHandler.delimitString(possAddRequestBean.getEstMonUsage()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getStore()) + "," + SqlHandler.delimitString(possAddRequestBean.getSlInitRetest()) + "," + SqlHandler.delimitString(possAddRequestBean.getAge()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getOrdFacility()) + "," + SqlHandler.delimitString(possAddRequestBean.getOrdSourceCd()) + "," + SqlHandler.delimitString(possAddRequestBean.getApprovedUsers()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getRequestorLast()) + "," + SqlHandler.delimitString(possAddRequestBean.getRequestorFirst()) + "," + SqlHandler.delimitString(possAddRequestBean.getRequestorMiddle()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getMfgAddress()) + "," + SqlHandler.delimitString(possAddRequestBean.getMfgPhone()) + "," + SqlHandler.delimitString(possAddRequestBean.getDistributorAddress()) + "," +
        SqlHandler.delimitString(possAddRequestBean.getDistributorPhone()) + "," + SqlHandler.delimitString(possAddRequestBean.getAdditionalSpecs()) + "," + SqlHandler.delimitString(possAddRequestBean.getSpecialNotes()) + ")";

    return sqlManager.update(conn, query);
  }

  /*
    //update
    public int update(PossAddRequestBean possAddRequestBean)
     throws BaseException {
     Connection connection = null;
     int result = 0;
     try {
      connection = getDbManager().getConnection();
      result = update(possAddRequestBean, connection);
     }
     finally {
      this.getDbManager().returnConnection(connection);
     }
     return result;
    }
    public int update(PossAddRequestBean possAddRequestBean, Connection conn)
     throws BaseException {
     String query  = "update " + TABLE + " set " +
      ATTRIBUTE_SPEC_ID + "=" +
       SqlHandler.delimitString(possAddRequestBean.getSpecId()) + "," +
      ATTRIBUTE_MSDS_NUMBER + "=" +
       SqlHandler.delimitString(possAddRequestBean.getMsdsNumber()) + "," +
      ATTRIBUTE_SUGGESTED_VENDOR + "=" +
       SqlHandler.delimitString(possAddRequestBean.getSuggestedVendor()) + "," +
      ATTRIBUTE_MAX_SUGGESTED_VENDOR_PART_NO + "=" +
       SqlHandler.delimitString(possAddRequestBean.getMaxSuggestedVendorPartNo()) + "," +
      ATTRIBUTE_PROPER_SHIPPING_NAME + "=" +
       SqlHandler.delimitString(possAddRequestBean.getProperShippingName()) + "," +
      ATTRIBUTE_HAZARD_CLASS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getHazardClass()) + "," +
      ATTRIBUTE_UN_NUMBER + "=" +
       SqlHandler.delimitString(possAddRequestBean.getUnNumber()) + "," +
      ATTRIBUTE_NA_NUMBER + "=" +
       SqlHandler.delimitString(possAddRequestBean.getNaNumber()) + "," +
      ATTRIBUTE_PACKING_GROUP + "=" +
       SqlHandler.delimitString(possAddRequestBean.getPackingGroup()) + "," +
      ATTRIBUTE_NFPA_HMIS_NUMBERS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getNfpaHmisNumbers()) + "," +
      ATTRIBUTE_REQUEST_ID + "=" +
       StringHandler.nullIfZero(possAddRequestBean.getRequestId()) + "," +
      ATTRIBUTE_POSS_NO + "=" +
       SqlHandler.delimitString(possAddRequestBean.getPossNo()) + "," +
      ATTRIBUTE_TRADENAME + "=" +
       SqlHandler.delimitString(possAddRequestBean.getTradename()) + "," +
      ATTRIBUTE_DESCRIPTION + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDescription()) + "," +
      ATTRIBUTE_MANUFACTURER + "=" +
       SqlHandler.delimitString(possAddRequestBean.getManufacturer()) + "," +
      ATTRIBUTE_POSS_SIZE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getPossSize()) + "," +
      ATTRIBUTE_MANUFACTURERS_PART + "=" +
       SqlHandler.delimitString(possAddRequestBean.getManufacturersPart()) + "," +
      ATTRIBUTE_DWG_OR_SPEC_NUMBER + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDwgOrSpecNumber()) + "," +
      ATTRIBUTE_TESTING_REQS_DOCUMENT + "=" +
       SqlHandler.delimitString(possAddRequestBean.getTestingReqsDocument()) + "," +
      ATTRIBUTE_QA_ATT_PKG_ATT + "=" +
       SqlHandler.delimitString(possAddRequestBean.getQaAttPkgAtt()) + "," +
      ATTRIBUTE_DUP_INSTOCK + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDupInstock()) + "," +
      ATTRIBUTE_DUP_PARTNO + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDupPartno()) + "," +
      ATTRIBUTE_NEW_POSS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getNewPoss()) + "," +
      ATTRIBUTE_REPLACE_POSS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getReplacePoss()) + "," +
      ATTRIBUTE_REPL_PARTNO + "=" +
       SqlHandler.delimitString(possAddRequestBean.getReplPartno()) + "," +
      ATTRIBUTE_ISS_TOZERO_DELETE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getIssTozeroDelete()) + "," +
      ATTRIBUTE_DEL_UPON_RECV + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDelUponRecv()) + "," +
      ATTRIBUTE_DISP_OTHER + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDispOther()) + "," +
      ATTRIBUTE_DISP_OTHER_TEXT + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDispOtherText()) + "," +
      ATTRIBUTE_EST_MON_USAGE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getEstMonUsage()) + "," +
      ATTRIBUTE_STORE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getStore()) + "," +
      ATTRIBUTE_SL_INIT_RETEST + "=" +
       SqlHandler.delimitString(possAddRequestBean.getSlInitRetest()) + "," +
      ATTRIBUTE_AGE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getAge()) + "," +
      ATTRIBUTE_ORD_FACILITY + "=" +
       SqlHandler.delimitString(possAddRequestBean.getOrdFacility()) + "," +
      ATTRIBUTE_ORD_SOURCE_CD + "=" +
       SqlHandler.delimitString(possAddRequestBean.getOrdSourceCd()) + "," +
      ATTRIBUTE_APPROVED_USERS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getApprovedUsers()) + "," +
      ATTRIBUTE_REQUESTOR_LAST + "=" +
       SqlHandler.delimitString(possAddRequestBean.getRequestorLast()) + "," +
      ATTRIBUTE_REQUESTOR_FIRST + "=" +
       SqlHandler.delimitString(possAddRequestBean.getRequestorFirst()) + "," +
      ATTRIBUTE_REQUESTOR_MIDDLE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getRequestorMiddle()) + "," +
      ATTRIBUTE_MFG_ADDRESS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getMfgAddress()) + "," +
      ATTRIBUTE_MFG_PHONE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getMfgPhone()) + "," +
      ATTRIBUTE_DISTRIBUTOR_ADDRESS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDistributorAddress()) + "," +
      ATTRIBUTE_DISTRIBUTOR_PHONE + "=" +
       SqlHandler.delimitString(possAddRequestBean.getDistributorPhone()) + "," +
      ATTRIBUTE_ADDITIONAL_SPECS + "=" +
       SqlHandler.delimitString(possAddRequestBean.getAdditionalSpecs()) + "," +
      ATTRIBUTE_SPECIAL_NOTES + "=" +
       SqlHandler.delimitString(possAddRequestBean.getSpecialNotes()) + " " +
      "where " + ATTRIBUTE_SPEC_ID + "=" +
       possAddRequestBean.getSpecId();
     return new SqlManager().update(conn, query);
    }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

    Collection possAddRequestBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PossAddRequestBean possAddRequestBean = new PossAddRequestBean();
      load(dataSetRow, possAddRequestBean);
      possAddRequestBeanColl.add(possAddRequestBean);
    }

    return possAddRequestBeanColl;
  }
}