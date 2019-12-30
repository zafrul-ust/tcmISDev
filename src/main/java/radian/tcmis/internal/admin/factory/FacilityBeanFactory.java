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
import radian.tcmis.internal.admin.beans.FacilityBean;
import radian.tcmis.internal.admin.factory.LocationBeanFactory;

/******************************************************************************
 * CLASSNAME: FacilityBeanFactory <br>
 * @version: 1.0, Mar 31, 2004 <br>
 *****************************************************************************/

public class FacilityBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_FACILITY_TYPE = "FACILITY_TYPE";
  public String ATTRIBUTE_MAIL_LOCATION = "MAIL_LOCATION";
  public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
  public String ATTRIBUTE_SHIPPING_LOCATION = "SHIPPING_LOCATION";
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_PREFERRED_WAREHOUSE = "PREFERRED_WAREHOUSE";
  public String ATTRIBUTE_JDE_ID = "JDE_ID";
  public String ATTRIBUTE_JDE_BILL_TO = "JDE_BILL_TO";
  public String ATTRIBUTE_ACTIVE = "ACTIVE";
  public String ATTRIBUTE_EPA_ID = "EPA_ID";
  public String ATTRIBUTE_STATE_GENERATOR_ID = "STATE_GENERATOR_ID";
  public String ATTRIBUTE_TIME_ZONE = "TIME_ZONE";
  public String ATTRIBUTE_WASTE_TRANSFER_SET_POINT = "WASTE_TRANSFER_SET_POINT";
  public String ATTRIBUTE_ECOMMERCE = "ECOMMERCE";
  public String ATTRIBUTE_PROGRAM_CONTACT = "PROGRAM_CONTACT";
  public String ATTRIBUTE_E_GL_SEGMENT2 = "E_GL_SEGMENT2";
  public String ATTRIBUTE_INVENTORY_GROUP_DEFAULT = "INVENTORY_GROUP_DEFAULT";
  public String ATTRIBUTE_PROCESS_DETAIL_REQUIRED = "PROCESS_DETAIL_REQUIRED";
  public String ATTRIBUTE_COMPANY_FACILITY_ID = "COMPANY_FACILITY_ID";
  public String ATTRIBUTE_COMPANY_APPROVER_ID = "COMPANY_APPROVER_ID";
  public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";
  public String ATTRIBUTE_CAT_ADD_APPROVAL_DETAIL_NEEDED =
      "CAT_ADD_APPROVAL_DETAIL_NEEDED";
  public String ATTRIBUTE_SALES_TAX_BASIS = "SALES_TAX_BASIS";
  public String ATTRIBUTE_PAY_SALES_TAX = "PAY_SALES_TAX";
  public String ATTRIBUTE_USE_LIMITS_RESTRICTION = "USE_LIMITS_RESTRICTION";
  public String ATTRIBUTE_PLANT_ID = "PLANT_ID";
  public String ATTRIBUTE_BLDG_ID = "BLDG_ID";
  public String ATTRIBUTE_DIVISION_ID = "DIVISION_ID";
  public String ATTRIBUTE_SIC_CODE = "SIC_CODE";
  public String ATTRIBUTE_DUN_AND_BRADSTREET_NUMBER =
      "DUN_AND_BRADSTREET_NUMBER";
  public String ATTRIBUTE_PHYSICAL_LOCATION = "PHYSICAL_LOCATION";

  //sequence and table names
  public String SEQUENCE = "FACILITY_SEQ";
  public String TABLE = "FACILITY";

  //constructor
  public FacilityBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("facilityType")) {
      return ATTRIBUTE_FACILITY_TYPE;
    }
    else if (attributeName.equals("mailLocation")) {
      return ATTRIBUTE_MAIL_LOCATION;
    }
    else if (attributeName.equals("facilityName")) {
      return ATTRIBUTE_FACILITY_NAME;
    }
    else if (attributeName.equals("shippingLocation")) {
      return ATTRIBUTE_SHIPPING_LOCATION;
    }
    else if (attributeName.equals("branchPlant")) {
      return ATTRIBUTE_BRANCH_PLANT;
    }
    else if (attributeName.equals("preferredWarehouse")) {
      return ATTRIBUTE_PREFERRED_WAREHOUSE;
    }
    else if (attributeName.equals("jdeId")) {
      return ATTRIBUTE_JDE_ID;
    }
    else if (attributeName.equals("jdeBillTo")) {
      return ATTRIBUTE_JDE_BILL_TO;
    }
    else if (attributeName.equals("active")) {
      return ATTRIBUTE_ACTIVE;
    }
    else if (attributeName.equals("epaId")) {
      return ATTRIBUTE_EPA_ID;
    }
    else if (attributeName.equals("stateGeneratorId")) {
      return ATTRIBUTE_STATE_GENERATOR_ID;
    }
    else if (attributeName.equals("timeZone")) {
      return ATTRIBUTE_TIME_ZONE;
    }
    else if (attributeName.equals("wasteTransferSetPoint")) {
      return ATTRIBUTE_WASTE_TRANSFER_SET_POINT;
    }
    else if (attributeName.equals("ecommerce")) {
      return ATTRIBUTE_ECOMMERCE;
    }
    else if (attributeName.equals("programContact")) {
      return ATTRIBUTE_PROGRAM_CONTACT;
    }
    else if (attributeName.equals("eGlSegment2")) {
      return ATTRIBUTE_E_GL_SEGMENT2;
    }
    else if (attributeName.equals("inventoryGroupDefault")) {
      return ATTRIBUTE_INVENTORY_GROUP_DEFAULT;
    }
    else if (attributeName.equals("processDetailRequired")) {
      return ATTRIBUTE_PROCESS_DETAIL_REQUIRED;
    }
    else if (attributeName.equals("companyFacilityId")) {
      return ATTRIBUTE_COMPANY_FACILITY_ID;
    }
    else if (attributeName.equals("companyApproverId")) {
      return ATTRIBUTE_COMPANY_APPROVER_ID;
    }
    else if (attributeName.equals("priceGroupId")) {
      return ATTRIBUTE_PRICE_GROUP_ID;
    }
    else if (attributeName.equals("catAddApprovalDetailNeeded")) {
      return ATTRIBUTE_CAT_ADD_APPROVAL_DETAIL_NEEDED;
    }
    else if (attributeName.equals("salesTaxBasis")) {
      return ATTRIBUTE_SALES_TAX_BASIS;
    }
    else if (attributeName.equals("paySalesTax")) {
      return ATTRIBUTE_PAY_SALES_TAX;
    }
    else if (attributeName.equals("useLimitsRestriction")) {
      return ATTRIBUTE_USE_LIMITS_RESTRICTION;
    }
    else if (attributeName.equals("plantId")) {
      return ATTRIBUTE_PLANT_ID;
    }
    else if (attributeName.equals("bldgId")) {
      return ATTRIBUTE_BLDG_ID;
    }
    else if (attributeName.equals("divisionId")) {
      return ATTRIBUTE_DIVISION_ID;
    }
    else if (attributeName.equals("sicCode")) {
      return ATTRIBUTE_SIC_CODE;
    }
    else if (attributeName.equals("dunAndBradstreetNumber")) {
      return ATTRIBUTE_DUN_AND_BRADSTREET_NUMBER;
    }
    else if (attributeName.equals("physicalLocation")) {
      return ATTRIBUTE_PHYSICAL_LOCATION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, FacilityBean.class);
  }

  //delete
  public int delete(FacilityBean facilityBean, TcmISDBModel dbModel) throws
      BaseException {
    SearchCriteria criteria = new SearchCriteria("facilityId", "=",
                                                 "" +
                                                 facilityBean.getFacilityId());
    return delete(criteria, dbModel.getConnection());
  }

  public int delete(FacilityBean facilityBean, Connection conn) throws
      BaseException {
    SearchCriteria criteria = new SearchCriteria("facilityId", "=",
                                                 "" +
                                                 facilityBean.getFacilityId());
    return delete(criteria, conn);
  }

  public int delete(SearchCriteria criteria, TcmISDBModel dbModel) throws
      BaseException {
    return delete(criteria, dbModel.getConnection());
  }

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {
    String query = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return new SqlManager().update(conn, query);
  }

  //insert
  public int insert(FacilityBean facilityBean, TcmISDBModel dbModel) throws
      BaseException {
    return insert(facilityBean, dbModel.getConnection());
  }

  public int insert(FacilityBean facilityBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_FACILITY_ID + "," +
        ATTRIBUTE_COMPANY_ID + "," +
        ATTRIBUTE_FACILITY_TYPE + "," +
        ATTRIBUTE_MAIL_LOCATION + "," +
        ATTRIBUTE_FACILITY_NAME + "," +
        ATTRIBUTE_SHIPPING_LOCATION + "," +
        ATTRIBUTE_BRANCH_PLANT + "," +
        ATTRIBUTE_PREFERRED_WAREHOUSE + "," +
        ATTRIBUTE_JDE_ID + "," +
        ATTRIBUTE_JDE_BILL_TO + "," +
        ATTRIBUTE_ACTIVE + "," +
        ATTRIBUTE_EPA_ID + "," +
        ATTRIBUTE_STATE_GENERATOR_ID + "," +
        ATTRIBUTE_TIME_ZONE + "," +
        ATTRIBUTE_WASTE_TRANSFER_SET_POINT + "," +
        ATTRIBUTE_ECOMMERCE + "," +
        ATTRIBUTE_PROGRAM_CONTACT + "," +
        ATTRIBUTE_E_GL_SEGMENT2 + "," +
        ATTRIBUTE_INVENTORY_GROUP_DEFAULT + "," +
        ATTRIBUTE_PROCESS_DETAIL_REQUIRED + "," +
        ATTRIBUTE_COMPANY_FACILITY_ID + "," +
        ATTRIBUTE_COMPANY_APPROVER_ID + "," +
        ATTRIBUTE_PRICE_GROUP_ID + "," +
        ATTRIBUTE_CAT_ADD_APPROVAL_DETAIL_NEEDED + "," +
        ATTRIBUTE_SALES_TAX_BASIS + "," +
        ATTRIBUTE_PAY_SALES_TAX + "," +
        ATTRIBUTE_USE_LIMITS_RESTRICTION + "," +
        ATTRIBUTE_PLANT_ID + "," +
        ATTRIBUTE_BLDG_ID + "," +
        ATTRIBUTE_DIVISION_ID + "," +
        ATTRIBUTE_SIC_CODE + "," +
        ATTRIBUTE_DUN_AND_BRADSTREET_NUMBER + "," +
        ATTRIBUTE_PHYSICAL_LOCATION + ")" +
        SqlHandler.delimitString(facilityBean.getFacilityId()) + "," +
        SqlHandler.delimitString(facilityBean.getCompanyId()) + "," +
        SqlHandler.delimitString(facilityBean.getFacilityType()) + "," +
        SqlHandler.delimitString(facilityBean.getMailLocation()) + "," +
        SqlHandler.delimitString(facilityBean.getFacilityName()) + "," +
        SqlHandler.delimitString(facilityBean.getShippingLocation()) + "," +
        SqlHandler.delimitString(facilityBean.getBranchPlant()) + "," +
        SqlHandler.delimitString(facilityBean.getPreferredWarehouse()) + "," +
        StringHandler.nullIfZero(facilityBean.getJdeId()) + "," +
        StringHandler.nullIfZero(facilityBean.getJdeBillTo()) + "," +
        SqlHandler.delimitString(facilityBean.getActive()) + "," +
        SqlHandler.delimitString(facilityBean.getEpaId()) + "," +
        SqlHandler.delimitString(facilityBean.getStateGeneratorId()) + "," +
        SqlHandler.delimitString(facilityBean.getTimeZone()) + "," +
        StringHandler.nullIfZero(facilityBean.getWasteTransferSetPoint()) + "," +
        SqlHandler.delimitString(facilityBean.getEcommerce()) + "," +
        StringHandler.nullIfZero(facilityBean.getProgramContact()) + "," +
        SqlHandler.delimitString(facilityBean.getEGlSegment2()) + "," +
        SqlHandler.delimitString(facilityBean.getInventoryGroupDefault()) + "," +
        SqlHandler.delimitString(facilityBean.getProcessDetailRequired()) + "," +
        StringHandler.nullIfZero(facilityBean.getCompanyFacilityId()) + "," +
        SqlHandler.delimitString(facilityBean.getCompanyApproverId()) + "," +
        SqlHandler.delimitString(facilityBean.getPriceGroupId()) + "," +
        SqlHandler.delimitString(facilityBean.getCatAddApprovalDetailNeeded()) +
        "," +
        SqlHandler.delimitString(facilityBean.getSalesTaxBasis()) + "," +
        SqlHandler.delimitString(facilityBean.getPaySalesTax()) + "," +
        SqlHandler.delimitString(facilityBean.getUseLimitsRestriction()) + "," +
        SqlHandler.delimitString(facilityBean.getPlantId()) + "," +
        SqlHandler.delimitString(facilityBean.getBldgId()) + "," +
        SqlHandler.delimitString(facilityBean.getDivisionId()) + "," +
        SqlHandler.delimitString(facilityBean.getSicCode()) + "," +
        SqlHandler.delimitString(facilityBean.getDunAndBradstreetNumber()) +
        "," +
        SqlHandler.delimitString(facilityBean.getPhysicalLocation()) + ")";
    if(log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  //update
  public int update(FacilityBean facilityBean, TcmISDBModel dbModel) throws
      BaseException {
    return update(facilityBean, dbModel.getConnection());
  }

  public int update(FacilityBean facilityBean, Connection conn) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_FACILITY_ID + "=" +
        SqlHandler.delimitString(facilityBean.getFacilityId()) + "," +
        ATTRIBUTE_COMPANY_ID + "=" +
        SqlHandler.delimitString(facilityBean.getCompanyId()) + "," +
        ATTRIBUTE_FACILITY_TYPE + "=" +
        SqlHandler.delimitString(facilityBean.getFacilityType()) + "," +
        ATTRIBUTE_MAIL_LOCATION + "=" +
        SqlHandler.delimitString(facilityBean.getMailLocation()) + "," +
        ATTRIBUTE_FACILITY_NAME + "=" +
        SqlHandler.delimitString(facilityBean.getFacilityName()) + "," +
        ATTRIBUTE_SHIPPING_LOCATION + "=" +
        SqlHandler.delimitString(facilityBean.getShippingLocation()) + "," +
        ATTRIBUTE_BRANCH_PLANT + "=" +
        SqlHandler.delimitString(facilityBean.getBranchPlant()) + "," +
        ATTRIBUTE_PREFERRED_WAREHOUSE + "=" +
        SqlHandler.delimitString(facilityBean.getPreferredWarehouse()) + "," +
        ATTRIBUTE_JDE_ID + "=" +
        StringHandler.nullIfZero(facilityBean.getJdeId()) + "," +
        ATTRIBUTE_JDE_BILL_TO + "=" +
        StringHandler.nullIfZero(facilityBean.getJdeBillTo()) + "," +
        ATTRIBUTE_ACTIVE + "=" +
        SqlHandler.delimitString(facilityBean.getActive()) + "," +
        ATTRIBUTE_EPA_ID + "=" +
        SqlHandler.delimitString(facilityBean.getEpaId()) + "," +
        ATTRIBUTE_STATE_GENERATOR_ID + "=" +
        SqlHandler.delimitString(facilityBean.getStateGeneratorId()) + "," +
        ATTRIBUTE_TIME_ZONE + "=" +
        SqlHandler.delimitString(facilityBean.getTimeZone()) + "," +
        ATTRIBUTE_WASTE_TRANSFER_SET_POINT + "=" +
        StringHandler.nullIfZero(facilityBean.getWasteTransferSetPoint()) + "," +
        ATTRIBUTE_ECOMMERCE + "=" +
        SqlHandler.delimitString(facilityBean.getEcommerce()) + "," +
        ATTRIBUTE_PROGRAM_CONTACT + "=" +
        StringHandler.nullIfZero(facilityBean.getProgramContact()) + "," +
        ATTRIBUTE_E_GL_SEGMENT2 + "=" +
        SqlHandler.delimitString(facilityBean.getEGlSegment2()) + "," +
        ATTRIBUTE_INVENTORY_GROUP_DEFAULT + "=" +
        SqlHandler.delimitString(facilityBean.getInventoryGroupDefault()) + "," +
        ATTRIBUTE_PROCESS_DETAIL_REQUIRED + "=" +
        SqlHandler.delimitString(facilityBean.getProcessDetailRequired()) + "," +
        ATTRIBUTE_COMPANY_FACILITY_ID + "=" +
        StringHandler.nullIfZero(facilityBean.getCompanyFacilityId()) + "," +
        ATTRIBUTE_COMPANY_APPROVER_ID + "=" +
        SqlHandler.delimitString(facilityBean.getCompanyApproverId()) + "," +
        ATTRIBUTE_PRICE_GROUP_ID + "=" +
        SqlHandler.delimitString(facilityBean.getPriceGroupId()) + "," +
        ATTRIBUTE_CAT_ADD_APPROVAL_DETAIL_NEEDED + "=" +
        SqlHandler.delimitString(facilityBean.getCatAddApprovalDetailNeeded()) +
        "," +
        ATTRIBUTE_SALES_TAX_BASIS + "=" +
        SqlHandler.delimitString(facilityBean.getSalesTaxBasis()) + "," +
        ATTRIBUTE_PAY_SALES_TAX + "=" +
        SqlHandler.delimitString(facilityBean.getPaySalesTax()) + "," +
        ATTRIBUTE_USE_LIMITS_RESTRICTION + "=" +
        SqlHandler.delimitString(facilityBean.getUseLimitsRestriction()) + "," +
        ATTRIBUTE_PLANT_ID + "=" +
        SqlHandler.delimitString(facilityBean.getPlantId()) + "," +
        ATTRIBUTE_BLDG_ID + "=" +
        SqlHandler.delimitString(facilityBean.getBldgId()) + "," +
        ATTRIBUTE_DIVISION_ID + "=" +
        SqlHandler.delimitString(facilityBean.getDivisionId()) + "," +
        ATTRIBUTE_SIC_CODE + "=" +
        SqlHandler.delimitString(facilityBean.getSicCode()) + "," +
        ATTRIBUTE_DUN_AND_BRADSTREET_NUMBER + "=" +
        SqlHandler.delimitString(facilityBean.getDunAndBradstreetNumber()) +
        "," +
        ATTRIBUTE_PHYSICAL_LOCATION + "=" +
        SqlHandler.delimitString(facilityBean.getPhysicalLocation()) + " " +
        "where " + ATTRIBUTE_FACILITY_ID + "=" +
        SqlHandler.delimitString(facilityBean.getFacilityId());
    if(log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return new SqlManager().update(conn, query);
  }

  public int updateSicCodeDunAndBradstreetNumber(String sicCode,
                                                 String dunAndBradstreetNumber,
                                                 String facilityId,
                                                 TcmISDBModel dbModel) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_DUN_AND_BRADSTREET_NUMBER + "=" +
        SqlHandler.delimitString(dunAndBradstreetNumber) + "," +
        ATTRIBUTE_SIC_CODE + "=" +
        SqlHandler.delimitString(sicCode) +
        " where " + ATTRIBUTE_FACILITY_ID + "=" +
        SqlHandler.delimitString(facilityId);
    if(log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return new SqlManager().update(dbModel.getConnection(), query);
  }

  //select
  public Collection select(SearchCriteria criteria, TcmISDBModel dbModel) throws
      BaseException {

    return select(criteria, dbModel.getConnection());
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection facilityBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    LocationBeanFactory factory = new LocationBeanFactory(getClient());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      FacilityBean facilityBean = new FacilityBean();
      load(dataSetRow, facilityBean);
      //add location collection
      SearchCriteria searchCriteria = new SearchCriteria();
      searchCriteria.addCriterion("locationId",
                                  SearchCriterion.EQUALS,
                                  dataSetRow.getString(this.
          ATTRIBUTE_MAIL_LOCATION));
      facilityBean.setLocationBeanCollection(factory.select(searchCriteria,
          conn));
      facilityBeanColl.add(facilityBean);
    }

    return facilityBeanColl;
  }

  public DataSet getDataSet(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection facilityBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    return dataSet;
  }

}
