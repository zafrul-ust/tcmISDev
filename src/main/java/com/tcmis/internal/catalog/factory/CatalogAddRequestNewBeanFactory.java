package com.tcmis.internal.catalog.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.math.BigDecimal;
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
import com.tcmis.internal.catalog.beans.CatalogAddRequestNewBean;

/******************************************************************************
 * CLASSNAME: CatalogAddRequestNewBeanFactory <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/

public class CatalogAddRequestNewBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
  public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
  public String ATTRIBUTE_REQUEST_DATE = "REQUEST_DATE";
  public String ATTRIBUTE_REQUEST_STATUS = "REQUEST_STATUS";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_STOCKED = "STOCKED";
  public String ATTRIBUTE_INIT_90_DAY = "INIT_90_DAY";
  public String ATTRIBUTE_SHELF_LIFE = "SHELF_LIFE";
  public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
  public String ATTRIBUTE_SHELF_LIFE_UNIT = "SHELF_LIFE_UNIT";
  public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
  public String ATTRIBUTE_VENDOR_CONTACT_NAME = "VENDOR_CONTACT_NAME";
  public String ATTRIBUTE_VENDOR_CONTACT_EMAIL = "VENDOR_CONTACT_EMAIL";
  public String ATTRIBUTE_VENDOR_CONTACT_PHONE = "VENDOR_CONTACT_PHONE";
  public String ATTRIBUTE_VENDOR_CONTACT_FAX = "VENDOR_CONTACT_FAX";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_STARTING_VIEW = "STARTING_VIEW";
  public String ATTRIBUTE_SUGGESTED_VENDOR = "SUGGESTED_VENDOR";
  public String ATTRIBUTE_VENDOR_PART_NO = "VENDOR_PART_NO";
  public String ATTRIBUTE_REQUEST_REJECTED = "REQUEST_REJECTED";
  public String ATTRIBUTE_LAST_UPDATED = "LAST_UPDATED";
  public String ATTRIBUTE_ENGINEERING_EVALUATION = "ENGINEERING_EVALUATION";
  public String ATTRIBUTE_ENG_EVAL_WORK_AREA = "ENG_EVAL_WORK_AREA";
  public String ATTRIBUTE_ENG_EVAL_FACILITY_ID = "ENG_EVAL_FACILITY_ID";
  public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
  public String ATTRIBUTE_FREE_SAMPLE = "FREE_SAMPLE";
  public String ATTRIBUTE_EVAL_TYPE = "EVAL_TYPE";
  public String ATTRIBUTE_REPLACES_PART_NO = "REPLACES_PART_NO";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CUSTOMER_REQUEST_ID = "CUSTOMER_REQUEST_ID";
  public String ATTRIBUTE_CUSTOMER_REQUESTOR_ID = "CUSTOMER_REQUESTOR_ID";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_SHELF_LIFE_SOURCE = "SHELF_LIFE_SOURCE";
  public String ATTRIBUTE_SUBMIT_DATE = "SUBMIT_DATE";
  public String ATTRIBUTE_EVAL_STATUS = "EVAL_STATUS";
  public String ATTRIBUTE_EVAL_REJECTED_BY = "EVAL_REJECTED_BY";
  public String ATTRIBUTE_EVAL_REJECTED_COMMENT = "EVAL_REJECTED_COMMENT";
  public String ATTRIBUTE_APPROVAL_GROUP_SEQ = "APPROVAL_GROUP_SEQ";
  public String ATTRIBUTE_QC_STATUS = "QC_STATUS";
  public String ATTRIBUTE_QC_DATE = "QC_DATE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_POSS = "POSS";
  public String ATTRIBUTE_POSS_EST_MON_USAGE = "POSS_EST_MON_USAGE";
  public String ATTRIBUTE_POSS_STORE = "POSS_STORE";
  public String ATTRIBUTE_LAST_CHANGED_BY = "LAST_CHANGED_BY";
  public String ATTRIBUTE_CAT_PART_DIRECTED_CHRG_NO = "CAT_PART_DIRECTED_CHRG_NO";
  public String ATTRIBUTE_CATALOG_ITEM_ID = "CATALOG_ITEM_ID";
  public String ATTRIBUTE_QUALITY_CONTROL = "QUALITY_CONTROL";
  public String ATTRIBUTE_CONSUMABLE = "CONSUMABLE";
  public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT = "MANAGE_KITS_AS_SINGLE_UNIT";
  public String ATTRIBUTE_CATEGORY = "CATEGORY";
  public String ATTRIBUTE_PART_NO_COMMENT = "PART_NO_COMMENT";
  public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
  public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_UNIT_OF_SALE_PRICE = "UNIT_OF_SALE_PRICE";
  public String ATTRIBUTE_CAT_ADD_PART_NEEDS_REVIEW = "CAT_ADD_PART_NEEDS_REVIEW";
  public String ATTRIBUTE_OLD_CAT_PART_NO = "OLD_CAT_PART_NO";

  //table name
  public String TABLE = "CATALOG_ADD_REQUEST_NEW";

  //constructor
  public CatalogAddRequestNewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("requestId")) {
      return ATTRIBUTE_REQUEST_ID;
    } else if (attributeName.equals("requestor")) {
      return ATTRIBUTE_REQUESTOR;
    } else if (attributeName.equals("requestDate")) {
      return ATTRIBUTE_REQUEST_DATE;
    } else if (attributeName.equals("requestStatus")) {
      return ATTRIBUTE_REQUEST_STATUS;
    } else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    } else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    } else if (attributeName.equals("stocked")) {
      return ATTRIBUTE_STOCKED;
    } else if (attributeName.equals("init90Day")) {
      return ATTRIBUTE_INIT_90_DAY;
    } else if (attributeName.equals("shelfLife")) {
      return ATTRIBUTE_SHELF_LIFE;
    } else if (attributeName.equals("shelfLifeBasis")) {
      return ATTRIBUTE_SHELF_LIFE_BASIS;
    } else if (attributeName.equals("shelfLifeUnit")) {
      return ATTRIBUTE_SHELF_LIFE_UNIT;
    } else if (attributeName.equals("storageTemp")) {
      return ATTRIBUTE_STORAGE_TEMP;
    } else if (attributeName.equals("vendorContactName")) {
      return ATTRIBUTE_VENDOR_CONTACT_NAME;
    } else if (attributeName.equals("vendorContactEmail")) {
      return ATTRIBUTE_VENDOR_CONTACT_EMAIL;
    } else if (attributeName.equals("vendorContactPhone")) {
      return ATTRIBUTE_VENDOR_CONTACT_PHONE;
    } else if (attributeName.equals("vendorContactFax")) {
      return ATTRIBUTE_VENDOR_CONTACT_FAX;
    } else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    } else if (attributeName.equals("startingView")) {
      return ATTRIBUTE_STARTING_VIEW;
    } else if (attributeName.equals("suggestedVendor")) {
      return ATTRIBUTE_SUGGESTED_VENDOR;
    } else if (attributeName.equals("vendorPartNo")) {
      return ATTRIBUTE_VENDOR_PART_NO;
    } else if (attributeName.equals("requestRejected")) {
      return ATTRIBUTE_REQUEST_REJECTED;
    } else if (attributeName.equals("lastUpdated")) {
      return ATTRIBUTE_LAST_UPDATED;
    } else if (attributeName.equals("engineeringEvaluation")) {
      return ATTRIBUTE_ENGINEERING_EVALUATION;
    } else if (attributeName.equals("engEvalWorkArea")) {
      return ATTRIBUTE_ENG_EVAL_WORK_AREA;
    } else if (attributeName.equals("engEvalFacilityId")) {
      return ATTRIBUTE_ENG_EVAL_FACILITY_ID;
    } else if (attributeName.equals("accountSysId")) {
      return ATTRIBUTE_ACCOUNT_SYS_ID;
    } else if (attributeName.equals("freeSample")) {
      return ATTRIBUTE_FREE_SAMPLE;
    } else if (attributeName.equals("evalType")) {
      return ATTRIBUTE_EVAL_TYPE;
    } else if (attributeName.equals("replacesPartNo")) {
      return ATTRIBUTE_REPLACES_PART_NO;
    } else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    } else if (attributeName.equals("customerRequestId")) {
      return ATTRIBUTE_CUSTOMER_REQUEST_ID;
    } else if (attributeName.equals("customerRequestorId")) {
      return ATTRIBUTE_CUSTOMER_REQUESTOR_ID;
    } else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    } else if (attributeName.equals("shelfLifeSource")) {
      return ATTRIBUTE_SHELF_LIFE_SOURCE;
    } else if (attributeName.equals("submitDate")) {
      return ATTRIBUTE_SUBMIT_DATE;
    } else if (attributeName.equals("evalStatus")) {
      return ATTRIBUTE_EVAL_STATUS;
    } else if (attributeName.equals("evalRejectedBy")) {
      return ATTRIBUTE_EVAL_REJECTED_BY;
    } else if (attributeName.equals("evalRejectedComment")) {
      return ATTRIBUTE_EVAL_REJECTED_COMMENT;
    } else if (attributeName.equals("approvalGroupSeq")) {
      return ATTRIBUTE_APPROVAL_GROUP_SEQ;
    } else if (attributeName.equals("qcStatus")) {
      return ATTRIBUTE_QC_STATUS;
    } else if (attributeName.equals("qcDate")) {
      return ATTRIBUTE_QC_DATE;
    } else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    } else if (attributeName.equals("poss")) {
      return ATTRIBUTE_POSS;
    } else if (attributeName.equals("possEstMonUsage")) {
      return ATTRIBUTE_POSS_EST_MON_USAGE;
    } else if (attributeName.equals("possStore")) {
      return ATTRIBUTE_POSS_STORE;
    } else if (attributeName.equals("lastChangedBy")) {
      return ATTRIBUTE_LAST_CHANGED_BY;
    } else if (attributeName.equals("catPartDirectedChrgNo")) {
      return ATTRIBUTE_CAT_PART_DIRECTED_CHRG_NO;
    } else if (attributeName.equals("catalogItemId")) {
      return ATTRIBUTE_CATALOG_ITEM_ID;
    } else if (attributeName.equals("qualityControl")) {
      return ATTRIBUTE_QUALITY_CONTROL;
    } else if (attributeName.equals("consumable")) {
      return ATTRIBUTE_CONSUMABLE;
    } else if (attributeName.equals("manageKitsAsSingleUnit")) {
      return ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT;
    } else if (attributeName.equals("category")) {
      return ATTRIBUTE_CATEGORY;
    } else if (attributeName.equals("partNoComment")) {
      return ATTRIBUTE_PART_NO_COMMENT;
    } else if (attributeName.equals("baselinePrice")) {
      return ATTRIBUTE_BASELINE_PRICE;
    } else if (attributeName.equals("catalogPrice")) {
      return ATTRIBUTE_CATALOG_PRICE;
    } else if (attributeName.equals("unitOfSale")) {
      return ATTRIBUTE_UNIT_OF_SALE;
    } else if (attributeName.equals("unitOfSalePrice")) {
      return ATTRIBUTE_UNIT_OF_SALE_PRICE;
    } else if (attributeName.equals("catAddPartNeedsReview")) {
      return ATTRIBUTE_CAT_ADD_PART_NEEDS_REVIEW;
    } else if (attributeName.equals("oldCatPartNo")) {
      return ATTRIBUTE_OLD_CAT_PART_NO;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CatalogAddRequestNewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CatalogAddRequestNewBean catalogAddRequestNewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("requestId", "SearchCriterion.EQUALS",
     "" + catalogAddRequestNewBean.getRequestId());
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
   public int delete(CatalogAddRequestNewBean catalogAddRequestNewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("requestId", "SearchCriterion.EQUALS",
     "" + catalogAddRequestNewBean.getRequestId());
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
  public int insert(CatalogAddRequestNewBean catalogAddRequestNewBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(catalogAddRequestNewBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(CatalogAddRequestNewBean catalogAddRequestNewBean, Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query = "insert into " + TABLE + " (" + ATTRIBUTE_REQUEST_ID + "," + ATTRIBUTE_REQUESTOR + "," + ATTRIBUTE_REQUEST_DATE + "," + ATTRIBUTE_REQUEST_STATUS + "," + ATTRIBUTE_CATALOG_ID + "," + ATTRIBUTE_CAT_PART_NO + "," + ATTRIBUTE_STOCKED +
        "," + ATTRIBUTE_INIT_90_DAY + "," + ATTRIBUTE_SHELF_LIFE + "," + ATTRIBUTE_SHELF_LIFE_BASIS + "," + ATTRIBUTE_SHELF_LIFE_UNIT + "," + ATTRIBUTE_STORAGE_TEMP + "," + ATTRIBUTE_VENDOR_CONTACT_NAME + "," + ATTRIBUTE_VENDOR_CONTACT_EMAIL + "," +
        ATTRIBUTE_VENDOR_CONTACT_PHONE + "," + ATTRIBUTE_VENDOR_CONTACT_FAX + "," + ATTRIBUTE_ITEM_ID + "," + ATTRIBUTE_STARTING_VIEW + "," + ATTRIBUTE_SUGGESTED_VENDOR + "," + ATTRIBUTE_VENDOR_PART_NO + "," + ATTRIBUTE_REQUEST_REJECTED + "," +
        ATTRIBUTE_LAST_UPDATED + "," + ATTRIBUTE_ENGINEERING_EVALUATION + "," + ATTRIBUTE_ENG_EVAL_WORK_AREA + "," + ATTRIBUTE_ENG_EVAL_FACILITY_ID + "," + ATTRIBUTE_ACCOUNT_SYS_ID + "," + ATTRIBUTE_FREE_SAMPLE + "," + ATTRIBUTE_EVAL_TYPE + "," +
        ATTRIBUTE_REPLACES_PART_NO + "," + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_CUSTOMER_REQUEST_ID + "," + ATTRIBUTE_CUSTOMER_REQUESTOR_ID + "," + ATTRIBUTE_PART_GROUP_NO + "," + ATTRIBUTE_SHELF_LIFE_SOURCE + "," + ATTRIBUTE_SUBMIT_DATE + "," +
        ATTRIBUTE_EVAL_STATUS + "," + ATTRIBUTE_EVAL_REJECTED_BY + "," + ATTRIBUTE_EVAL_REJECTED_COMMENT + "," + ATTRIBUTE_APPROVAL_GROUP_SEQ + "," + ATTRIBUTE_QC_STATUS + "," + ATTRIBUTE_QC_DATE + "," + ATTRIBUTE_PART_DESCRIPTION + "," +
        ATTRIBUTE_POSS + "," + ATTRIBUTE_POSS_EST_MON_USAGE + "," + ATTRIBUTE_POSS_STORE + "," + ATTRIBUTE_LAST_CHANGED_BY + "," + ATTRIBUTE_CAT_PART_DIRECTED_CHRG_NO + "," + ATTRIBUTE_CATALOG_ITEM_ID + "," + ATTRIBUTE_QUALITY_CONTROL + "," +
        ATTRIBUTE_CONSUMABLE + "," + ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "," + ATTRIBUTE_CATEGORY + "," + ATTRIBUTE_PART_NO_COMMENT + "," + ATTRIBUTE_BASELINE_PRICE + "," + ATTRIBUTE_CATALOG_PRICE + "," + ATTRIBUTE_UNIT_OF_SALE + "," +
        ATTRIBUTE_UNIT_OF_SALE_PRICE + "," + ATTRIBUTE_CAT_ADD_PART_NEEDS_REVIEW + "," + ATTRIBUTE_OLD_CAT_PART_NO + ")" + " values (" + catalogAddRequestNewBean.getRequestId() + "," + catalogAddRequestNewBean.getRequestor() + "," +
        DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getRequestDate()) + "," + catalogAddRequestNewBean.getRequestStatus() + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getCatalogId()) + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getCatPartNo()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getStocked()) + "," + catalogAddRequestNewBean.getInit90Day() + "," + catalogAddRequestNewBean.getShelfLife() + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getShelfLifeBasis()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getShelfLifeUnit()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getStorageTemp()) + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactName()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactEmail()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactPhone()) +
        "," + SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactFax()) + "," + catalogAddRequestNewBean.getItemId() + "," + catalogAddRequestNewBean.getStartingView() + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getSuggestedVendor()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getVendorPartNo()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getRequestRejected()) + "," +
        DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getLastUpdated()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getEngineeringEvaluation()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getEngEvalWorkArea()) +
        "," + SqlHandler.delimitString(catalogAddRequestNewBean.getEngEvalFacilityId()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getAccountSysId()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getFreeSample()) + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getEvalType()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getReplacesPartNo()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getCompanyId()) + "," +
        catalogAddRequestNewBean.getCustomerRequestId() + "," + catalogAddRequestNewBean.getCustomerRequestorId() + "," + catalogAddRequestNewBean.getPartGroupNo() + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getShelfLifeSource()) + "," +
        DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getSubmitDate()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getEvalStatus()) + "," + catalogAddRequestNewBean.getEvalRejectedBy() + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getEvalRejectedComment()) + "," + catalogAddRequestNewBean.getApprovalGroupSeq() + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getQcStatus()) + "," +
        DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getQcDate()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getPartDescription()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getPoss()) + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getPossEstMonUsage()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getPossStore()) + "," + catalogAddRequestNewBean.getLastChangedBy() + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getCatPartDirectedChrgNo()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getCatalogItemId()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getQualityControl()) + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getConsumable()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getManageKitsAsSingleUnit()) + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getCategory()) + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getPartNoComment()) + "," + catalogAddRequestNewBean.getBaselinePrice() + "," + catalogAddRequestNewBean.getCatalogPrice() + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getUnitOfSale()) + "," + catalogAddRequestNewBean.getUnitOfSalePrice() + "," + SqlHandler.delimitString(catalogAddRequestNewBean.getCatAddPartNeedsReview()) + "," +
        SqlHandler.delimitString(catalogAddRequestNewBean.getOldCatPartNo()) + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
      public int update(CatalogAddRequestNewBean catalogAddRequestNewBean)
   throws BaseException {
   Connection connection = null;
   int result = 0;
   try {
    connection = getDbManager().getConnection();
    result = update(catalogAddRequestNewBean, connection);
   }
   finally {
    this.getDbManager().returnConnection(connection);
   }
   return result;
      }
      public int update(CatalogAddRequestNewBean catalogAddRequestNewBean, Connection conn)
   throws BaseException {
   String query  = "update " + TABLE + " set " +
    ATTRIBUTE_REQUEST_ID + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getRequestId()) + "," +
    ATTRIBUTE_REQUESTOR + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getRequestor()) + "," +
    ATTRIBUTE_REQUEST_DATE + "=" +
     DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getRequestDate()) + "," +
    ATTRIBUTE_REQUEST_STATUS + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getRequestStatus()) + "," +
    ATTRIBUTE_CATALOG_ID + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getCatalogId()) + "," +
    ATTRIBUTE_CAT_PART_NO + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getCatPartNo()) + "," +
    ATTRIBUTE_STOCKED + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getStocked()) + "," +
    ATTRIBUTE_INIT_90_DAY + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getInit90Day()) + "," +
    ATTRIBUTE_SHELF_LIFE + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getShelfLife()) + "," +
    ATTRIBUTE_SHELF_LIFE_BASIS + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getShelfLifeBasis()) + "," +
    ATTRIBUTE_SHELF_LIFE_UNIT + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getShelfLifeUnit()) + "," +
    ATTRIBUTE_STORAGE_TEMP + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getStorageTemp()) + "," +
    ATTRIBUTE_VENDOR_CONTACT_NAME + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactName()) + "," +
    ATTRIBUTE_VENDOR_CONTACT_EMAIL + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactEmail()) + "," +
    ATTRIBUTE_VENDOR_CONTACT_PHONE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactPhone()) + "," +
    ATTRIBUTE_VENDOR_CONTACT_FAX + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getVendorContactFax()) + "," +
    ATTRIBUTE_ITEM_ID + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getItemId()) + "," +
    ATTRIBUTE_STARTING_VIEW + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getStartingView()) + "," +
    ATTRIBUTE_SUGGESTED_VENDOR + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getSuggestedVendor()) + "," +
    ATTRIBUTE_VENDOR_PART_NO + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getVendorPartNo()) + "," +
    ATTRIBUTE_REQUEST_REJECTED + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getRequestRejected()) + "," +
    ATTRIBUTE_LAST_UPDATED + "=" +
     DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getLastUpdated()) + "," +
    ATTRIBUTE_ENGINEERING_EVALUATION + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getEngineeringEvaluation()) + "," +
    ATTRIBUTE_ENG_EVAL_WORK_AREA + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getEngEvalWorkArea()) + "," +
    ATTRIBUTE_ENG_EVAL_FACILITY_ID + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getEngEvalFacilityId()) + "," +
    ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getAccountSysId()) + "," +
    ATTRIBUTE_FREE_SAMPLE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getFreeSample()) + "," +
    ATTRIBUTE_EVAL_TYPE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getEvalType()) + "," +
    ATTRIBUTE_REPLACES_PART_NO + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getReplacesPartNo()) + "," +
    ATTRIBUTE_COMPANY_ID + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getCompanyId()) + "," +
    ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getCustomerRequestId()) + "," +
    ATTRIBUTE_CUSTOMER_REQUESTOR_ID + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getCustomerRequestorId()) + "," +
    ATTRIBUTE_PART_GROUP_NO + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getPartGroupNo()) + "," +
    ATTRIBUTE_SHELF_LIFE_SOURCE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getShelfLifeSource()) + "," +
    ATTRIBUTE_SUBMIT_DATE + "=" +
     DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getSubmitDate()) + "," +
    ATTRIBUTE_EVAL_STATUS + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getEvalStatus()) + "," +
    ATTRIBUTE_EVAL_REJECTED_BY + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getEvalRejectedBy()) + "," +
    ATTRIBUTE_EVAL_REJECTED_COMMENT + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getEvalRejectedComment()) + "," +
    ATTRIBUTE_APPROVAL_GROUP_SEQ + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getApprovalGroupSeq()) + "," +
    ATTRIBUTE_QC_STATUS + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getQcStatus()) + "," +
    ATTRIBUTE_QC_DATE + "=" +
     DateHandler.getOracleToDateFunction(catalogAddRequestNewBean.getQcDate()) + "," +
    ATTRIBUTE_PART_DESCRIPTION + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getPartDescription()) + "," +
    ATTRIBUTE_POSS + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getPoss()) + "," +
    ATTRIBUTE_POSS_EST_MON_USAGE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getPossEstMonUsage()) + "," +
    ATTRIBUTE_POSS_STORE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getPossStore()) + "," +
    ATTRIBUTE_LAST_CHANGED_BY + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getLastChangedBy()) + "," +
    ATTRIBUTE_CAT_PART_DIRECTED_CHRG_NO + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getCatPartDirectedChrgNo()) + "," +
    ATTRIBUTE_CATALOG_ITEM_ID + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getCatalogItemId()) + "," +
    ATTRIBUTE_QUALITY_CONTROL + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getQualityControl()) + "," +
    ATTRIBUTE_CONSUMABLE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getConsumable()) + "," +
    ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getManageKitsAsSingleUnit()) + "," +
    ATTRIBUTE_CATEGORY + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getCategory()) + "," +
    ATTRIBUTE_PART_NO_COMMENT + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getPartNoComment()) + "," +
    ATTRIBUTE_BASELINE_PRICE + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getBaselinePrice()) + "," +
    ATTRIBUTE_CATALOG_PRICE + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getCatalogPrice()) + "," +
    ATTRIBUTE_UNIT_OF_SALE + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getUnitOfSale()) + "," +
    ATTRIBUTE_UNIT_OF_SALE_PRICE + "=" +
     StringHandler.nullIfZero(catalogAddRequestNewBean.getUnitOfSalePrice()) + "," +
    ATTRIBUTE_CAT_ADD_PART_NEEDS_REVIEW + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getCatAddPartNeedsReview()) + "," +
    ATTRIBUTE_OLD_CAT_PART_NO + "=" +
     SqlHandler.delimitString(catalogAddRequestNewBean.getOldCatPartNo()) + " " +
    "where " + ATTRIBUTE_REQUEST_ID + "=" +
     catalogAddRequestNewBean.getRequestId();
   return new SqlManager().update(conn, query);
      }
   */

  //request_seq
  public BigDecimal selectNextRequestIdFromSequence() throws BaseException {

    Connection connection = null;
    BigDecimal requestId = null;
    try {
      connection = this.getDbManager().getConnection();
      requestId = selectNextRequestIdFromSequence(connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return requestId;
  }

  public BigDecimal selectNextRequestIdFromSequence(Connection conn) throws BaseException {
    BigDecimal requestId = null;

    String query = "select request_seq.nextval request_id from dual";

    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogAddRequestNewBean catalogAddRequestNewBean = new CatalogAddRequestNewBean();
      load(dataSetRow, catalogAddRequestNewBean);
      requestId = catalogAddRequestNewBean.getRequestId();
    }

    return requestId;
  }

// Larry, update.
  public int update(String requestId, String itemId)
  throws BaseException {

	  Connection connection = null;
	  int result = 0;
	  try {
		  connection = getDbManager().getConnection();
		  result = update( requestId, itemId, connection);
	  }
	  finally {
		  this.getDbManager().returnConnection(connection);
	  }
	  return result;
  }

  public int update(String requestId, String itemId, Connection conn)
  throws BaseException {

	  String query  = "update " + TABLE + " set ITEM_ID = " + itemId + " where REQUEST_ID = " + requestId + " ";

	  return new SqlManager().update(conn, query);
  }
  //select
  public Collection select(SearchCriteria criteria) throws BaseException {
	return select(criteria,null);
  }
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

    Collection catalogAddRequestNewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogAddRequestNewBean catalogAddRequestNewBean = new CatalogAddRequestNewBean();
      load(dataSetRow, catalogAddRequestNewBean);
      catalogAddRequestNewBeanColl.add(catalogAddRequestNewBean);
    }

    return catalogAddRequestNewBeanColl;
  }
}