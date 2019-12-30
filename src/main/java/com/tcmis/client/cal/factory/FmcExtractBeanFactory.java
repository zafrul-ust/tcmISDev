package com.tcmis.client.cal.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.cal.beans.FmcExtractBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: FmcExtractBeanFactory <br>
 * @version: 1.0, Mar 17, 2005 <br>
 *****************************************************************************/

public class FmcExtractBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_KEY_ID = "KEY_ID";
  public String ATTRIBUTE_TRL_TYPE = "TRL_TYPE";
  public String ATTRIBUTE_TRL_DATE = "TRL_DATE";
  public String ATTRIBUTE_TRL_COSTCODE = "TRL_COSTCODE";
  public String ATTRIBUTE_TRL_EVENT = "TRL_EVENT";
  public String ATTRIBUTE_TRL_PART = "TRL_PART";
  public String ATTRIBUTE_TRL_QUANTITY = "TRL_QUANTITY";
  public String ATTRIBUTE_TRL_PRICE = "TRL_PRICE";
  public String ATTRIBUTE_PAR_CLASS = "PAR_CLASS";
  public String ATTRIBUTE_WORK_ORDER_DESCRIPTION = "WORK_ORDER_DESCRIPTION";
  public String ATTRIBUTE_COST_CODE_DESCRIPTION = "COST_CODE_DESCRIPTION";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_PAR_UOM = "PAR_UOM";
  public String ATTRIBUTE_UNIT_OF_MEASURE_DESCRIPTION =
      "UNIT_OF_MEASURE_DESCRIPTION";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_FILE_NAME = "FILE_NAME";
  public String ATTRIBUTE_LOAD_DATE = "LOAD_DATE";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_ITEM_QUANTITY_ISSUED = "ITEM_QUANTITY_ISSUED";
  public String ATTRIBUTE_CATEGORY = "CATEGORY";
  public String ATTRIBUTE_APPLICATION_METHOD = "APPLICATION_METHOD";
  public String ATTRIBUTE_END_USE_DATETIME = "END_USE_DATETIME";
  public String ATTRIBUTE_END_USE_COMPLETE = "END_USE_COMPLETE";
  public String ATTRIBUTE_LAST_MOD = "LAST_MOD";
  public String ATTRIBUTE_ITEM_QTY_ISSUED = "ITEM_QTY_ISSUED";
  public String ATTRIBUTE_EMISSION_FACTOR = "EMISSION_FACTOR";
  public String ATTRIBUTE_VOLUME_GAL = "VOLUME_GAL";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_TRA_ORG = "TRA_ORG";

  //table name
  public String TABLE = "FMC_EXTRACT";

  //constructor
  public FmcExtractBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("keyId")) {
      return ATTRIBUTE_KEY_ID;
    }
    else if (attributeName.equals("trlType")) {
      return ATTRIBUTE_TRL_TYPE;
    }
    else if (attributeName.equals("trlDate")) {
      return ATTRIBUTE_TRL_DATE;
    }
    else if (attributeName.equals("trlCostcode")) {
      return ATTRIBUTE_TRL_COSTCODE;
    }
    else if (attributeName.equals("trlEvent")) {
      return ATTRIBUTE_TRL_EVENT;
    }
    else if (attributeName.equals("trlPart")) {
      return ATTRIBUTE_TRL_PART;
    }
    else if (attributeName.equals("trlQuantity")) {
      return ATTRIBUTE_TRL_QUANTITY;
    }
    else if (attributeName.equals("trlPrice")) {
      return ATTRIBUTE_TRL_PRICE;
    }
    else if (attributeName.equals("parClass")) {
      return ATTRIBUTE_PAR_CLASS;
    }
    else if (attributeName.equals("workOrderDescription")) {
      return ATTRIBUTE_WORK_ORDER_DESCRIPTION;
    }
    else if (attributeName.equals("costCodeDescription")) {
      return ATTRIBUTE_COST_CODE_DESCRIPTION;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("parUom")) {
      return ATTRIBUTE_PAR_UOM;
    }
    else if (attributeName.equals("unitOfMeasureDescription")) {
      return ATTRIBUTE_UNIT_OF_MEASURE_DESCRIPTION;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("fileName")) {
      return ATTRIBUTE_FILE_NAME;
    }
    else if (attributeName.equals("loadDate")) {
      return ATTRIBUTE_LOAD_DATE;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("itemQuantityIssued")) {
      return ATTRIBUTE_ITEM_QUANTITY_ISSUED;
    }
    else if (attributeName.equals("category")) {
      return ATTRIBUTE_CATEGORY;
    }
    else if (attributeName.equals("applicationMethod")) {
      return ATTRIBUTE_APPLICATION_METHOD;
    }
    else if (attributeName.equals("endUseDatetime")) {
      return ATTRIBUTE_END_USE_DATETIME;
    }
    else if (attributeName.equals("endUseComplete")) {
      return ATTRIBUTE_END_USE_COMPLETE;
    }
    else if (attributeName.equals("lastMod")) {
      return ATTRIBUTE_LAST_MOD;
    }
    else if (attributeName.equals("itemQtyIssued")) {
      return ATTRIBUTE_ITEM_QTY_ISSUED;
    }
    else if (attributeName.equals("emissionFactor")) {
      return ATTRIBUTE_EMISSION_FACTOR;
    }
    else if (attributeName.equals("volumeGal")) {
      return ATTRIBUTE_VOLUME_GAL;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("traOrg")) {
      return ATTRIBUTE_TRA_ORG;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, FmcExtractBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(FmcExtractBean fmcExtractBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("keyId", "SearchCriterion.EQUALS",
     "" + fmcExtractBean.getKeyId());
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
   public int delete(FmcExtractBean fmcExtractBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("keyId", "SearchCriterion.EQUALS",
     "" + fmcExtractBean.getKeyId());
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

//insert
  public int insert(FmcExtractBean fmcExtractBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(fmcExtractBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(FmcExtractBean fmcExtractBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        //ATTRIBUTE_KEY_ID + "," +
        ATTRIBUTE_TRL_TYPE + "," +
        ATTRIBUTE_TRL_DATE + "," +
        ATTRIBUTE_TRL_COSTCODE + "," +
        ATTRIBUTE_TRL_EVENT + "," +
        ATTRIBUTE_TRL_PART + "," +
        ATTRIBUTE_TRL_QUANTITY + "," +
        ATTRIBUTE_TRL_PRICE + "," +
        ATTRIBUTE_PAR_CLASS + "," +
        ATTRIBUTE_WORK_ORDER_DESCRIPTION + "," +
        ATTRIBUTE_COST_CODE_DESCRIPTION + "," +
        ATTRIBUTE_PART_DESCRIPTION + "," +
        ATTRIBUTE_PAR_UOM + "," +
        ATTRIBUTE_UNIT_OF_MEASURE_DESCRIPTION + "," +
        //ATTRIBUTE_ITEM_ID + "," +
        ATTRIBUTE_FILE_NAME + "," +
        //ATTRIBUTE_LOAD_DATE + "," +
        //ATTRIBUTE_CAT_PART_NO + "," +
        //ATTRIBUTE_ITEM_QUANTITY_ISSUED + "," +
        //ATTRIBUTE_CATEGORY + "," +
        //ATTRIBUTE_APPLICATION_METHOD + "," +
        //ATTRIBUTE_END_USE_DATETIME + "," +
        //ATTRIBUTE_END_USE_COMPLETE + "," +
        //ATTRIBUTE_LAST_MOD + "," +
        //ATTRIBUTE_ITEM_QTY_ISSUED + "," +
        //ATTRIBUTE_EMISSION_FACTOR + "," +
        //ATTRIBUTE_VOLUME_GAL + "," +
        //ATTRIBUTE_APPLICATION + "," +
        //ATTRIBUTE_FACILITY_ID + "," +
        ATTRIBUTE_TRA_ORG + ")" +
        " values (" +
        //StringHandler.nullIfZero(fmcExtractBean.getKeyId()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getTrlType()) + "," +
        DateHandler.getOracleToDateFunction(fmcExtractBean.getTrlDate()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getTrlCostcode()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getTrlEvent()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getTrlPart()) + "," +
        StringHandler.nullIfZero(fmcExtractBean.getTrlQuantity()) + "," +
        StringHandler.nullIfZero(fmcExtractBean.getTrlPrice()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getParClass()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getWorkOrderDescription()) +
        "," +
        SqlHandler.delimitString(fmcExtractBean.getCostCodeDescription()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getPartDescription()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getParUom()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getUnitOfMeasureDescription()) +
        "," +
        //StringHandler.nullIfZero(fmcExtractBean.getItemId()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getFileName()) + "," +
        //DateHandler.getOracleToDateFunction(fmcExtractBean.getLoadDate()) + "," +
        //SqlHandler.delimitString(fmcExtractBean.getCatPartNo()) + "," +
        //StringHandler.nullIfZero(fmcExtractBean.getItemQuantityIssued()) + "," +
        //SqlHandler.delimitString(fmcExtractBean.getCategory()) + "," +
        //SqlHandler.delimitString(fmcExtractBean.getApplicationMethod()) + "," +
        //DateHandler.getOracleToDateFunction(fmcExtractBean.getEndUseDatetime()) +
        //"," +
        //SqlHandler.delimitString(fmcExtractBean.getEndUseComplete()) + "," +
        //DateHandler.getOracleToDateFunction(fmcExtractBean.getLastMod()) + "," +
        //StringHandler.nullIfZero(fmcExtractBean.getItemQtyIssued()) + "," +
        //StringHandler.nullIfZero(fmcExtractBean.getEmissionFactor()) + "," +
        //StringHandler.nullIfZero(fmcExtractBean.getVolumeGal()) + "," +
        //SqlHandler.delimitString(fmcExtractBean.getApplication()) + "," +
        //SqlHandler.delimitString(fmcExtractBean.getFacilityId()) + "," +
        SqlHandler.delimitString(fmcExtractBean.getTraOrg()) + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(FmcExtractBean fmcExtractBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(fmcExtractBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
     public int update(FmcExtractBean fmcExtractBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_KEY_ID + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getKeyId()) + "," +
       ATTRIBUTE_TRL_TYPE + "=" +
        SqlHandler.delimitString(fmcExtractBean.getTrlType()) + "," +
       ATTRIBUTE_TRL_DATE + "=" +
       DateHandler.getOracleToDateFunction(fmcExtractBean.getTrlDate()) + "," +
       ATTRIBUTE_TRL_COSTCODE + "=" +
        SqlHandler.delimitString(fmcExtractBean.getTrlCostcode()) + "," +
       ATTRIBUTE_TRL_EVENT + "=" +
        SqlHandler.delimitString(fmcExtractBean.getTrlEvent()) + "," +
       ATTRIBUTE_TRL_PART + "=" +
        SqlHandler.delimitString(fmcExtractBean.getTrlPart()) + "," +
       ATTRIBUTE_TRL_QUANTITY + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getTrlQuantity()) + "," +
       ATTRIBUTE_TRL_PRICE + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getTrlPrice()) + "," +
       ATTRIBUTE_PAR_CLASS + "=" +
        SqlHandler.delimitString(fmcExtractBean.getParClass()) + "," +
       ATTRIBUTE_WORK_ORDER_DESCRIPTION + "=" +
       SqlHandler.delimitString(fmcExtractBean.getWorkOrderDescription()) + "," +
       ATTRIBUTE_COST_CODE_DESCRIPTION + "=" +
       SqlHandler.delimitString(fmcExtractBean.getCostCodeDescription()) + "," +
       ATTRIBUTE_PART_DESCRIPTION + "=" +
        SqlHandler.delimitString(fmcExtractBean.getPartDescription()) + "," +
       ATTRIBUTE_PAR_UOM + "=" +
        SqlHandler.delimitString(fmcExtractBean.getParUom()) + "," +
       ATTRIBUTE_UNIT_OF_MEASURE_DESCRIPTION + "=" +
       SqlHandler.delimitString(fmcExtractBean.getUnitOfMeasureDescription()) + "," +
       ATTRIBUTE_ITEM_ID + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getItemId()) + "," +
       ATTRIBUTE_FILE_NAME + "=" +
        SqlHandler.delimitString(fmcExtractBean.getFileName()) + "," +
       ATTRIBUTE_LOAD_DATE + "=" +
       DateHandler.getOracleToDateFunction(fmcExtractBean.getLoadDate()) + "," +
       ATTRIBUTE_CAT_PART_NO + "=" +
        SqlHandler.delimitString(fmcExtractBean.getCatPartNo()) + "," +
       ATTRIBUTE_ITEM_QUANTITY_ISSUED + "=" +
       StringHandler.nullIfZero(fmcExtractBean.getItemQuantityIssued()) + "," +
       ATTRIBUTE_CATEGORY + "=" +
        SqlHandler.delimitString(fmcExtractBean.getCategory()) + "," +
       ATTRIBUTE_APPLICATION_METHOD + "=" +
        SqlHandler.delimitString(fmcExtractBean.getApplicationMethod()) + "," +
       ATTRIBUTE_END_USE_DATETIME + "=" +
       DateHandler.getOracleToDateFunction(fmcExtractBean.getEndUseDatetime()) + "," +
       ATTRIBUTE_END_USE_COMPLETE + "=" +
        SqlHandler.delimitString(fmcExtractBean.getEndUseComplete()) + "," +
       ATTRIBUTE_LAST_MOD + "=" +
       DateHandler.getOracleToDateFunction(fmcExtractBean.getLastMod()) + "," +
       ATTRIBUTE_ITEM_QTY_ISSUED + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getItemQtyIssued()) + "," +
       ATTRIBUTE_EMISSION_FACTOR + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getEmissionFactor()) + "," +
       ATTRIBUTE_VOLUME_GAL + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getVolumeGal()) + "," +
       ATTRIBUTE_APPLICATION + "=" +
        SqlHandler.delimitString(fmcExtractBean.getApplication()) + "," +
       ATTRIBUTE_FACILITY_ID + "=" +
        SqlHandler.delimitString(fmcExtractBean.getFacilityId()) + "," +
       ATTRIBUTE_TRA_ORG + "=" +
        SqlHandler.delimitString(fmcExtractBean.getTraOrg()) + " " +
       "where " + ATTRIBUTE_KEY_ID + "=" +
        StringHandler.nullIfZero(fmcExtractBean.getKeyId());
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

    Collection fmcExtractBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      FmcExtractBean fmcExtractBean = new FmcExtractBean();
      load(dataSetRow, fmcExtractBean);
      fmcExtractBeanColl.add(fmcExtractBean);
    }

    return fmcExtractBeanColl;
  }
}