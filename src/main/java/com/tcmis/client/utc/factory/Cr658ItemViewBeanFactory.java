package com.tcmis.client.utc.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.utc.beans.Cr658ItemViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: Cr658ItemViewBeanFactory <br>
 * @version: 1.0, May 9, 2005 <br>
 *****************************************************************************/

public class Cr658ItemViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_RECORD_ID = "RECORD_ID";
  public String ATTRIBUTE_BUKRS = "BUKRS";
  public String ATTRIBUTE_XBLNR = "XBLNR";
  public String ATTRIBUTE_XITEM = "XITEM";
  public String ATTRIBUTE_BLDAT = "BLDAT";
  public String ATTRIBUTE_LIFNR = "LIFNR";
  public String ATTRIBUTE_ZMWSK = "ZMWSK";
  public String ATTRIBUTE_MWSKZ = "MWSKZ";
  public String ATTRIBUTE_TXJCD = "TXJCD";
  public String ATTRIBUTE_SGTXT = "SGTXT";
  public String ATTRIBUTE_MATKL = "MATKL";
  public String ATTRIBUTE_KOSTL = "KOSTL";
  public String ATTRIBUTE_HKONT = "HKONT";
  public String ATTRIBUTE_ZZAPPROP = "ZZAPPROP";
  public String ATTRIBUTE_ZZPROJ = "ZZPROJ";
  public String ATTRIBUTE_ACCTNG_01 = "ACCTNG_01";
  public String ATTRIBUTE_ACCTNG_02 = "ACCTNG_02";
  public String ATTRIBUTE_ACCTNG_03 = "ACCTNG_03";
  public String ATTRIBUTE_ACCTNG_04 = "ACCTNG_04";
  public String ATTRIBUTE_ACCTNG_05 = "ACCTNG_05";
  public String ATTRIBUTE_ZALLOW_IND = "ZALLOW_IND";
  public String ATTRIBUTE_ASSET_NUM = "ASSET_NUM";
  public String ATTRIBUTE_WAERS = "WAERS";
  public String ATTRIBUTE_WRBTR = "WRBTR";
  public String ATTRIBUTE_WMWST = "WMWST";
  public String ATTRIBUTE_KBETR = "KBETR";
  public String ATTRIBUTE_MENGE = "MENGE";
  public String ATTRIBUTE_MEINS = "MEINS";
  public String ATTRIBUTE_ZKEYID = "ZKEYID";
  public String ATTRIBUTE_ZEMAIL = "ZEMAIL";
  public String ATTRIBUTE_ZOTBUY = "ZOTBUY";
  public String ATTRIBUTE_ZCUST = "ZCUST";
  public String ATTRIBUTE_ZNAME = "ZNAME";
  public String ATTRIBUTE_ZEXTPO = "ZEXTPO";
  public String ATTRIBUTE_ZEXTPOLN = "ZEXTPOLN";
  public String ATTRIBUTE_IDNLF = "IDNLF";
  public String ATTRIBUTE_ZUNPR = "ZUNPR";
  public String ATTRIBUTE_ZTIER2 = "ZTIER2";
  public String ATTRIBUTE_ACCRUE_STX = "ACCRUE_STX";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_COUNTER = "COUNTER";

  //table name
  public String TABLE = "CR658_ITEM_VIEW";

  //constructor
  public Cr658ItemViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("recordId")) {
      return ATTRIBUTE_RECORD_ID;
    }
    else if (attributeName.equals("bukrs")) {
      return ATTRIBUTE_BUKRS;
    }
    else if (attributeName.equals("xblnr")) {
      return ATTRIBUTE_XBLNR;
    }
    else if (attributeName.equals("xitem")) {
      return ATTRIBUTE_XITEM;
    }
    else if (attributeName.equals("bldat")) {
      return ATTRIBUTE_BLDAT;
    }
    else if (attributeName.equals("lifnr")) {
      return ATTRIBUTE_LIFNR;
    }
    else if (attributeName.equals("zmwsk")) {
      return ATTRIBUTE_ZMWSK;
    }
    else if (attributeName.equals("mwskz")) {
      return ATTRIBUTE_MWSKZ;
    }
    else if (attributeName.equals("txjcd")) {
      return ATTRIBUTE_TXJCD;
    }
    else if (attributeName.equals("sgtxt")) {
      return ATTRIBUTE_SGTXT;
    }
    else if (attributeName.equals("matkl")) {
      return ATTRIBUTE_MATKL;
    }
    else if (attributeName.equals("kostl")) {
      return ATTRIBUTE_KOSTL;
    }
    else if (attributeName.equals("hkont")) {
      return ATTRIBUTE_HKONT;
    }
    else if (attributeName.equals("zzapprop")) {
      return ATTRIBUTE_ZZAPPROP;
    }
    else if (attributeName.equals("zzproj")) {
      return ATTRIBUTE_ZZPROJ;
    }
    else if (attributeName.equals("acctng01")) {
      return ATTRIBUTE_ACCTNG_01;
    }
    else if (attributeName.equals("acctng02")) {
      return ATTRIBUTE_ACCTNG_02;
    }
    else if (attributeName.equals("acctng03")) {
      return ATTRIBUTE_ACCTNG_03;
    }
    else if (attributeName.equals("acctng04")) {
      return ATTRIBUTE_ACCTNG_04;
    }
    else if (attributeName.equals("acctng05")) {
      return ATTRIBUTE_ACCTNG_05;
    }
    else if (attributeName.equals("zallowInd")) {
      return ATTRIBUTE_ZALLOW_IND;
    }
    else if (attributeName.equals("assetNum")) {
      return ATTRIBUTE_ASSET_NUM;
    }
    else if (attributeName.equals("waers")) {
      return ATTRIBUTE_WAERS;
    }
    else if (attributeName.equals("wrbtr")) {
      return ATTRIBUTE_WRBTR;
    }
    else if (attributeName.equals("wmwst")) {
      return ATTRIBUTE_WMWST;
    }
    else if (attributeName.equals("kbetr")) {
      return ATTRIBUTE_KBETR;
    }
    else if (attributeName.equals("menge")) {
      return ATTRIBUTE_MENGE;
    }
    else if (attributeName.equals("meins")) {
      return ATTRIBUTE_MEINS;
    }
    else if (attributeName.equals("zkeyid")) {
      return ATTRIBUTE_ZKEYID;
    }
    else if (attributeName.equals("zemail")) {
      return ATTRIBUTE_ZEMAIL;
    }
    else if (attributeName.equals("zotbuy")) {
      return ATTRIBUTE_ZOTBUY;
    }
    else if (attributeName.equals("zcust")) {
      return ATTRIBUTE_ZCUST;
    }
    else if (attributeName.equals("zname")) {
      return ATTRIBUTE_ZNAME;
    }
    else if (attributeName.equals("zextpo")) {
      return ATTRIBUTE_ZEXTPO;
    }
    else if (attributeName.equals("zextpoln")) {
      return ATTRIBUTE_ZEXTPOLN;
    }
    else if (attributeName.equals("idnlf")) {
      return ATTRIBUTE_IDNLF;
    }
    else if (attributeName.equals("zunpr")) {
      return ATTRIBUTE_ZUNPR;
    }
    else if (attributeName.equals("ztier2")) {
      return ATTRIBUTE_ZTIER2;
    }
    else if (attributeName.equals("accrueStx")) {
      return ATTRIBUTE_ACCRUE_STX;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("counter")) {
      return ATTRIBUTE_COUNTER;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, Cr658ItemViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(Cr658ItemViewBean cr658ItemViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("recordId", "SearchCriterion.EQUALS",
     "" + cr658ItemViewBean.getRecordId());
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
   public int delete(Cr658ItemViewBean cr658ItemViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("recordId", "SearchCriterion.EQUALS",
     "" + cr658ItemViewBean.getRecordId());
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
   public int insert(Cr658ItemViewBean cr658ItemViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(cr658ItemViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(Cr658ItemViewBean cr658ItemViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_RECORD_ID + "," +
     ATTRIBUTE_BUKRS + "," +
     ATTRIBUTE_XBLNR + "," +
     ATTRIBUTE_XITEM + "," +
     ATTRIBUTE_BLDAT + "," +
     ATTRIBUTE_LIFNR + "," +
     ATTRIBUTE_ZMWSK + "," +
     ATTRIBUTE_MWSKZ + "," +
     ATTRIBUTE_TXJCD + "," +
     ATTRIBUTE_SGTXT + "," +
     ATTRIBUTE_MATKL + "," +
     ATTRIBUTE_KOSTL + "," +
     ATTRIBUTE_HKONT + "," +
     ATTRIBUTE_ZZAPPROP + "," +
     ATTRIBUTE_ZZPROJ + "," +
     ATTRIBUTE_ACCTNG_01 + "," +
     ATTRIBUTE_ACCTNG_02 + "," +
     ATTRIBUTE_ACCTNG_03 + "," +
     ATTRIBUTE_ACCTNG_04 + "," +
     ATTRIBUTE_ACCTNG_05 + "," +
     ATTRIBUTE_ZALLOW_IND + "," +
     ATTRIBUTE_ASSET_NUM + "," +
     ATTRIBUTE_WAERS + "," +
     ATTRIBUTE_WRBTR + "," +
     ATTRIBUTE_WMWST + "," +
     ATTRIBUTE_KBETR + "," +
     ATTRIBUTE_MENGE + "," +
     ATTRIBUTE_MEINS + "," +
     ATTRIBUTE_ZKEYID + "," +
     ATTRIBUTE_ZEMAIL + "," +
     ATTRIBUTE_ZOTBUY + "," +
     ATTRIBUTE_ZCUST + "," +
     ATTRIBUTE_ZNAME + "," +
     ATTRIBUTE_ZEXTPO + "," +
     ATTRIBUTE_ZEXTPOLN + "," +
     ATTRIBUTE_IDNLF + "," +
     ATTRIBUTE_ZUNPR + "," +
     ATTRIBUTE_ZTIER2 + "," +
     ATTRIBUTE_ACCRUE_STX + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_COUNTER + ")" +
     " values (" +
     SqlHandler.delimitString(cr658ItemViewBean.getRecordId()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getBukrs()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getXblnr()) + "," +
     cr658ItemViewBean.getXitem() + "," +
     DateHandler.getOracleToDateFunction(cr658ItemViewBean.getBldat()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getLifnr()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZmwsk()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getMwskz()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getTxjcd()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getSgtxt()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getMatkl()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getKostl()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getHkont()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZzapprop()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZzproj()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getAcctng01()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getAcctng02()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getAcctng03()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getAcctng04()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getAcctng05()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZallowInd()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getAssetNum()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getWaers()) + "," +
     cr658ItemViewBean.getWrbtr() + "," +
     cr658ItemViewBean.getWmwst() + "," +
     cr658ItemViewBean.getKbetr() + "," +
     cr658ItemViewBean.getMenge() + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getMeins()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZkeyid()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZemail()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZotbuy()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZcust()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZname()) + "," +
     cr658ItemViewBean.getZextpo() + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZextpoln()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getIdnlf()) + "," +
     cr658ItemViewBean.getZunpr() + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getZtier2()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getAccrueStx()) + "," +
     SqlHandler.delimitString(cr658ItemViewBean.getFacilityId()) + "," +
     cr658ItemViewBean.getCounter() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(Cr658ItemViewBean cr658ItemViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(cr658ItemViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(Cr658ItemViewBean cr658ItemViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_RECORD_ID + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getRecordId()) + "," +
     ATTRIBUTE_BUKRS + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getBukrs()) + "," +
     ATTRIBUTE_XBLNR + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getXblnr()) + "," +
     ATTRIBUTE_XITEM + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getXitem()) + "," +
     ATTRIBUTE_BLDAT + "=" +
      DateHandler.getOracleToDateFunction(cr658ItemViewBean.getBldat()) + "," +
     ATTRIBUTE_LIFNR + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getLifnr()) + "," +
     ATTRIBUTE_ZMWSK + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZmwsk()) + "," +
     ATTRIBUTE_MWSKZ + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getMwskz()) + "," +
     ATTRIBUTE_TXJCD + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getTxjcd()) + "," +
     ATTRIBUTE_SGTXT + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getSgtxt()) + "," +
     ATTRIBUTE_MATKL + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getMatkl()) + "," +
     ATTRIBUTE_KOSTL + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getKostl()) + "," +
     ATTRIBUTE_HKONT + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getHkont()) + "," +
     ATTRIBUTE_ZZAPPROP + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZzapprop()) + "," +
     ATTRIBUTE_ZZPROJ + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZzproj()) + "," +
     ATTRIBUTE_ACCTNG_01 + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getAcctng01()) + "," +
     ATTRIBUTE_ACCTNG_02 + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getAcctng02()) + "," +
     ATTRIBUTE_ACCTNG_03 + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getAcctng03()) + "," +
     ATTRIBUTE_ACCTNG_04 + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getAcctng04()) + "," +
     ATTRIBUTE_ACCTNG_05 + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getAcctng05()) + "," +
     ATTRIBUTE_ZALLOW_IND + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZallowInd()) + "," +
     ATTRIBUTE_ASSET_NUM + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getAssetNum()) + "," +
     ATTRIBUTE_WAERS + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getWaers()) + "," +
     ATTRIBUTE_WRBTR + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getWrbtr()) + "," +
     ATTRIBUTE_WMWST + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getWmwst()) + "," +
     ATTRIBUTE_KBETR + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getKbetr()) + "," +
     ATTRIBUTE_MENGE + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getMenge()) + "," +
     ATTRIBUTE_MEINS + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getMeins()) + "," +
     ATTRIBUTE_ZKEYID + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZkeyid()) + "," +
     ATTRIBUTE_ZEMAIL + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZemail()) + "," +
     ATTRIBUTE_ZOTBUY + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZotbuy()) + "," +
     ATTRIBUTE_ZCUST + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZcust()) + "," +
     ATTRIBUTE_ZNAME + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZname()) + "," +
     ATTRIBUTE_ZEXTPO + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getZextpo()) + "," +
     ATTRIBUTE_ZEXTPOLN + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZextpoln()) + "," +
     ATTRIBUTE_IDNLF + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getIdnlf()) + "," +
     ATTRIBUTE_ZUNPR + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getZunpr()) + "," +
     ATTRIBUTE_ZTIER2 + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getZtier2()) + "," +
     ATTRIBUTE_ACCRUE_STX + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getAccrueStx()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(cr658ItemViewBean.getFacilityId()) + "," +
     ATTRIBUTE_COUNTER + "=" +
      StringHandler.nullIfZero(cr658ItemViewBean.getCounter()) + " " +
     "where " + ATTRIBUTE_RECORD_ID + "=" +
      cr658ItemViewBean.getRecordId();
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

    Collection cr658ItemViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      Cr658ItemViewBean cr658ItemViewBean = new Cr658ItemViewBean();
      load(dataSetRow, cr658ItemViewBean);
      cr658ItemViewBeanColl.add(cr658ItemViewBean);
    }
    return cr658ItemViewBeanColl;
  }
}
