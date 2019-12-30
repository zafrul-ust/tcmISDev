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
import com.tcmis.internal.catalog.beans.CatalogAddItemBean;

/******************************************************************************
 * CLASSNAME: CatalogAddItemBeanFactory <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/

public class CatalogAddItemBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
  public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
  public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
  public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
  public String ATTRIBUTE_PART_SIZE = "PART_SIZE";
  public String ATTRIBUTE_SIZE_UNIT = "SIZE_UNIT";
  public String ATTRIBUTE_PKG_STYLE = "PKG_STYLE";
  public String ATTRIBUTE_MATERIAL_APPROVED_BY = "MATERIAL_APPROVED_BY";
  public String ATTRIBUTE_MATERIAL_APPROVED_ON = "MATERIAL_APPROVED_ON";
  public String ATTRIBUTE_ITEM_APPROVED_BY = "ITEM_APPROVED_BY";
  public String ATTRIBUTE_ITEM_APPROVED_ON = "ITEM_APPROVED_ON";
  public String ATTRIBUTE_MFG_CATALOG_ID = "MFG_CATALOG_ID";
  public String ATTRIBUTE_PART_ID = "PART_ID";
  public String ATTRIBUTE_MATERIAL_TYPE = "MATERIAL_TYPE";
  public String ATTRIBUTE_CASE_QTY = "CASE_QTY";
  public String ATTRIBUTE_DIMENSION = "DIMENSION";
  public String ATTRIBUTE_GRADE = "GRADE";
  public String ATTRIBUTE_MFG_TRADE_NAME = "MFG_TRADE_NAME";
  public String ATTRIBUTE_NETWT = "NETWT";
  public String ATTRIBUTE_NETWT_UNIT = "NETWT_UNIT";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CUSTOMER_MSDS_NUMBER = "CUSTOMER_MSDS_NUMBER";
  public String ATTRIBUTE_COMPONENTS_PER_ITEM = "COMPONENTS_PER_ITEM";
  public String ATTRIBUTE_SAMPLE_ONLY = "SAMPLE_ONLY";
  public String ATTRIBUTE_POSS_SIZE = "POSS_SIZE";
  public String ATTRIBUTE_POSS_SPECIAL_NOTE = "POSS_SPECIAL_NOTE";
  public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
  public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
  public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
  public String ATTRIBUTE_CATALOG_COMPONENT_ITEM_ID = "CATALOG_COMPONENT_ITEM_ID";
  public String ATTRIBUTE_CATALOG_COMPONENT_ID = "CATALOG_COMPONENT_ID";
  public String ATTRIBUTE_CUSTOMER_REVISION_DATE = "CUSTOMER_REVISION_DATE";
  public String ATTRIBUTE_LABEL_COLOR = "LABEL_COLOR";

  //table name
  public String TABLE = "CATALOG_ADD_ITEM";

  //constructor
  public CatalogAddItemBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("requestId")) {
      return ATTRIBUTE_REQUEST_ID;
    } else if (attributeName.equals("materialDesc")) {
      return ATTRIBUTE_MATERIAL_DESC;
    } else if (attributeName.equals("manufacturer")) {
      return ATTRIBUTE_MANUFACTURER;
    } else if (attributeName.equals("materialId")) {
      return ATTRIBUTE_MATERIAL_ID;
    } else if (attributeName.equals("partSize")) {
      return ATTRIBUTE_PART_SIZE;
    } else if (attributeName.equals("sizeUnit")) {
      return ATTRIBUTE_SIZE_UNIT;
    } else if (attributeName.equals("pkgStyle")) {
      return ATTRIBUTE_PKG_STYLE;
    } else if (attributeName.equals("materialApprovedBy")) {
      return ATTRIBUTE_MATERIAL_APPROVED_BY;
    } else if (attributeName.equals("materialApprovedOn")) {
      return ATTRIBUTE_MATERIAL_APPROVED_ON;
    } else if (attributeName.equals("itemApprovedBy")) {
      return ATTRIBUTE_ITEM_APPROVED_BY;
    } else if (attributeName.equals("itemApprovedOn")) {
      return ATTRIBUTE_ITEM_APPROVED_ON;
    } else if (attributeName.equals("mfgCatalogId")) {
      return ATTRIBUTE_MFG_CATALOG_ID;
    } else if (attributeName.equals("partId")) {
      return ATTRIBUTE_PART_ID;
    } else if (attributeName.equals("materialType")) {
      return ATTRIBUTE_MATERIAL_TYPE;
    } else if (attributeName.equals("caseQty")) {
      return ATTRIBUTE_CASE_QTY;
    } else if (attributeName.equals("dimension")) {
      return ATTRIBUTE_DIMENSION;
    } else if (attributeName.equals("grade")) {
      return ATTRIBUTE_GRADE;
    } else if (attributeName.equals("mfgTradeName")) {
      return ATTRIBUTE_MFG_TRADE_NAME;
    } else if (attributeName.equals("netwt")) {
      return ATTRIBUTE_NETWT;
    } else if (attributeName.equals("netwtUnit")) {
      return ATTRIBUTE_NETWT_UNIT;
    } else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    } else if (attributeName.equals("customerMsdsNumber")) {
      return ATTRIBUTE_CUSTOMER_MSDS_NUMBER;
    } else if (attributeName.equals("componentsPerItem")) {
      return ATTRIBUTE_COMPONENTS_PER_ITEM;
    } else if (attributeName.equals("sampleOnly")) {
      return ATTRIBUTE_SAMPLE_ONLY;
    } else if (attributeName.equals("possSize")) {
      return ATTRIBUTE_POSS_SIZE;
    } else if (attributeName.equals("possSpecialNote")) {
      return ATTRIBUTE_POSS_SPECIAL_NOTE;
    } else if (attributeName.equals("shelfLifeDays")) {
      return ATTRIBUTE_SHELF_LIFE_DAYS;
    } else if (attributeName.equals("shelfLifeBasis")) {
      return ATTRIBUTE_SHELF_LIFE_BASIS;
    } else if (attributeName.equals("storageTemp")) {
      return ATTRIBUTE_STORAGE_TEMP;
    } else if (attributeName.equals("catalogComponentItemId")) {
      return ATTRIBUTE_CATALOG_COMPONENT_ITEM_ID;
    } else if (attributeName.equals("catalogComponentId")) {
      return ATTRIBUTE_CATALOG_COMPONENT_ID;
    } else if (attributeName.equals("customerRevisionDate")) {
      return ATTRIBUTE_CUSTOMER_REVISION_DATE;
    } else if (attributeName.equals("labelColor")) {
      return ATTRIBUTE_LABEL_COLOR;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CatalogAddItemBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CatalogAddItemBean catalogAddItemBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("requestId", "SearchCriterion.EQUALS",
     "" + catalogAddItemBean.getRequestId());
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
   public int delete(CatalogAddItemBean catalogAddItemBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("requestId", "SearchCriterion.EQUALS",
     "" + catalogAddItemBean.getRequestId());
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
  public int insert(CatalogAddItemBean catalogAddItemBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(catalogAddItemBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(CatalogAddItemBean catalogAddItemBean, Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query = "insert into " + TABLE + " (" + ATTRIBUTE_REQUEST_ID + "," + ATTRIBUTE_MATERIAL_DESC + "," + ATTRIBUTE_MANUFACTURER + "," + ATTRIBUTE_MATERIAL_ID + "," + ATTRIBUTE_PART_SIZE + "," + ATTRIBUTE_SIZE_UNIT + "," + ATTRIBUTE_PKG_STYLE +
        "," + ATTRIBUTE_MATERIAL_APPROVED_BY + "," + ATTRIBUTE_MATERIAL_APPROVED_ON + "," + ATTRIBUTE_ITEM_APPROVED_BY + "," + ATTRIBUTE_ITEM_APPROVED_ON + "," + ATTRIBUTE_MFG_CATALOG_ID + "," + ATTRIBUTE_PART_ID + "," + ATTRIBUTE_MATERIAL_TYPE +
        "," + ATTRIBUTE_CASE_QTY + "," + ATTRIBUTE_DIMENSION + "," + ATTRIBUTE_GRADE + "," + ATTRIBUTE_MFG_TRADE_NAME + "," + ATTRIBUTE_NETWT + "," + ATTRIBUTE_NETWT_UNIT + "," + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_CUSTOMER_MSDS_NUMBER + "," +
        ATTRIBUTE_COMPONENTS_PER_ITEM + "," + ATTRIBUTE_SAMPLE_ONLY + "," + ATTRIBUTE_POSS_SIZE + "," + ATTRIBUTE_POSS_SPECIAL_NOTE + "," + ATTRIBUTE_SHELF_LIFE_DAYS + "," + ATTRIBUTE_SHELF_LIFE_BASIS + "," + ATTRIBUTE_STORAGE_TEMP + "," +
        ATTRIBUTE_CATALOG_COMPONENT_ITEM_ID + "," + ATTRIBUTE_CATALOG_COMPONENT_ID + "," + ATTRIBUTE_CUSTOMER_REVISION_DATE + "," + ATTRIBUTE_LABEL_COLOR + ")" + " values (" + catalogAddItemBean.getRequestId() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getMaterialDesc()) + "," + SqlHandler.delimitString(catalogAddItemBean.getManufacturer()) + "," + catalogAddItemBean.getMaterialId() + "," + catalogAddItemBean.getPartSize() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getSizeUnit()) + "," + SqlHandler.delimitString(catalogAddItemBean.getPkgStyle()) + "," + catalogAddItemBean.getMaterialApprovedBy() + "," +
        DateHandler.getOracleToDateFunction(catalogAddItemBean.getMaterialApprovedOn()) + "," + catalogAddItemBean.getItemApprovedBy() + "," + DateHandler.getOracleToDateFunction(catalogAddItemBean.getItemApprovedOn()) + "," +
        SqlHandler.delimitString(catalogAddItemBean.getMfgCatalogId()) + "," + catalogAddItemBean.getPartId() + "," + SqlHandler.delimitString(catalogAddItemBean.getMaterialType()) + "," + catalogAddItemBean.getCaseQty() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getDimension()) + "," + SqlHandler.delimitString(catalogAddItemBean.getGrade()) + "," + SqlHandler.delimitString(catalogAddItemBean.getMfgTradeName()) + "," + catalogAddItemBean.getNetwt() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getNetwtUnit()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCompanyId()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCustomerMsdsNumber()) + "," +
        catalogAddItemBean.getComponentsPerItem() + "," + SqlHandler.delimitString(catalogAddItemBean.getSampleOnly()) + "," + SqlHandler.delimitString(catalogAddItemBean.getPossSize()) + "," +
        SqlHandler.delimitString(catalogAddItemBean.getPossSpecialNote()) + "," + catalogAddItemBean.getShelfLifeDays() + "," + SqlHandler.delimitString(catalogAddItemBean.getShelfLifeBasis()) + "," +
        SqlHandler.delimitString(catalogAddItemBean.getStorageTemp()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCatalogComponentItemId()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCatalogComponentId()) + "," +
        DateHandler.getOracleToDateFunction(catalogAddItemBean.getCustomerRevisionDate()) + "," + SqlHandler.delimitString(catalogAddItemBean.getLabelColor()) + ")";

    return sqlManager.update(conn, query);
  }

  public void createOrig(BigDecimal requestId) throws BaseException {
    Connection connection = null;
    try {
      connection = getDbManager().getConnection();
      createOrig(requestId, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
  }

  public void createOrig(BigDecimal requestId, Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();

    String query = "insert into catalog_add_item_orig (company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
      "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
      "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note)"+
      " (select company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
      "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
      "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note "+
      "from catalog_add_item where request_id = "+requestId.intValue()+")";

    sqlManager.update(conn, query);
  }

  public void createQc(BigDecimal requestId, String comments) throws BaseException {
    Connection connection = null;
    try {
      connection = getDbManager().getConnection();
      createQc(requestId,comments, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
  }

  public void createQc(BigDecimal requestId, String comments, Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();

    String query = "insert into catalog_add_item_qc (company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
          "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
          "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,status,comments)"+
          " (select company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
          "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
          "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,"+
          "decode(material_id,null,'Pending MSDS','0','Pending MSDS','Pending QC'),'"+comments+"' from catalog_add_item where request_id = "+requestId.intValue()+")";

    sqlManager.update(conn, query);
  }


  //you need to verify the primary key(s) before uncommenting this
  /*
//update
   public int update(CatalogAddItemBean catalogAddItemBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(catalogAddItemBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CatalogAddItemBean catalogAddItemBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_REQUEST_ID + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getRequestId()) + "," +
     ATTRIBUTE_MATERIAL_DESC + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getMaterialDesc()) + "," +
     ATTRIBUTE_MANUFACTURER + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getManufacturer()) + "," +
     ATTRIBUTE_MATERIAL_ID + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getMaterialId()) + "," +
     ATTRIBUTE_PART_SIZE + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getPartSize()) + "," +
     ATTRIBUTE_SIZE_UNIT + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getSizeUnit()) + "," +
     ATTRIBUTE_PKG_STYLE + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getPkgStyle()) + "," +
     ATTRIBUTE_MATERIAL_APPROVED_BY + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getMaterialApprovedBy()) + "," +
     ATTRIBUTE_MATERIAL_APPROVED_ON + "=" +
      DateHandler.getOracleToDateFunction(catalogAddItemBean.getMaterialApprovedOn()) + "," +
     ATTRIBUTE_ITEM_APPROVED_BY + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getItemApprovedBy()) + "," +
     ATTRIBUTE_ITEM_APPROVED_ON + "=" +
      DateHandler.getOracleToDateFunction(catalogAddItemBean.getItemApprovedOn()) + "," +
     ATTRIBUTE_MFG_CATALOG_ID + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getMfgCatalogId()) + "," +
     ATTRIBUTE_PART_ID + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getPartId()) + "," +
     ATTRIBUTE_MATERIAL_TYPE + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getMaterialType()) + "," +
     ATTRIBUTE_CASE_QTY + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getCaseQty()) + "," +
     ATTRIBUTE_DIMENSION + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getDimension()) + "," +
     ATTRIBUTE_GRADE + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getGrade()) + "," +
     ATTRIBUTE_MFG_TRADE_NAME + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getMfgTradeName()) + "," +
     ATTRIBUTE_NETWT + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getNetwt()) + "," +
     ATTRIBUTE_NETWT_UNIT + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getNetwtUnit()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getCompanyId()) + "," +
     ATTRIBUTE_CUSTOMER_MSDS_NUMBER + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getCustomerMsdsNumber()) + "," +
     ATTRIBUTE_COMPONENTS_PER_ITEM + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getComponentsPerItem()) + "," +
     ATTRIBUTE_SAMPLE_ONLY + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getSampleOnly()) + "," +
     ATTRIBUTE_POSS_SIZE + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getPossSize()) + "," +
     ATTRIBUTE_POSS_SPECIAL_NOTE + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getPossSpecialNote()) + "," +
     ATTRIBUTE_SHELF_LIFE_DAYS + "=" +
      StringHandler.nullIfZero(catalogAddItemBean.getShelfLifeDays()) + "," +
     ATTRIBUTE_SHELF_LIFE_BASIS + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getShelfLifeBasis()) + "," +
     ATTRIBUTE_STORAGE_TEMP + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getStorageTemp()) + "," +
     ATTRIBUTE_CATALOG_COMPONENT_ITEM_ID + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getCatalogComponentItemId()) + "," +
     ATTRIBUTE_CATALOG_COMPONENT_ID + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getCatalogComponentId()) + "," +
     ATTRIBUTE_CUSTOMER_REVISION_DATE + "=" +
      DateHandler.getOracleToDateFunction(catalogAddItemBean.getCustomerRevisionDate()) + "," +
     ATTRIBUTE_LABEL_COLOR + "=" +
      SqlHandler.delimitString(catalogAddItemBean.getLabelColor()) + " " +
     "where " + ATTRIBUTE_REQUEST_ID + "=" +
      catalogAddItemBean.getRequestId();
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

    Collection catalogAddItemBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogAddItemBean catalogAddItemBean = new CatalogAddItemBean();
      load(dataSetRow, catalogAddItemBean);
      catalogAddItemBeanColl.add(catalogAddItemBean);
    }

    return catalogAddItemBeanColl;
  }
}