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
import com.tcmis.client.catalog.beans.CatalogAddItemBean;

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
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_SUGGESTED_VENDOR = "SUGGESTED_VENDOR";
  public String ATTRIBUTE_VENDOR_CONTACT_NAME = "VENDOR_CONTACT_NAME";
  public String ATTRIBUTE_VENDOR_CONTACT_EMAIL = "VENDOR_CONTACT_EMAIL";
  public String ATTRIBUTE_VENDOR_CONTACT_PHONE = "VENDOR_CONTACT_PHONE";
  public String ATTRIBUTE_VENDOR_CONTACT_FAX = "VENDOR_CONTACT_FAX";
  public String ATTRIBUTE_STARTING_VIEW = "STARTING_VIEW";

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
	 } else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
	 } else if (attributeName.equals("ItemId")) {
      return ATTRIBUTE_ITEM_ID;
	 } else if (attributeName.equals("suggestedVendor")) {
      return ATTRIBUTE_SUGGESTED_VENDOR;
	 } else if (attributeName.equals("vendorContactName")) {
      return ATTRIBUTE_VENDOR_CONTACT_NAME;
	 } else if (attributeName.equals("vendorContactEmail")) {
      return ATTRIBUTE_VENDOR_CONTACT_EMAIL;
	 } else if (attributeName.equals("vendorContactPhone")) {
      return ATTRIBUTE_VENDOR_CONTACT_PHONE;
	 } else if (attributeName.equals("vendorContactFax")) {
      return ATTRIBUTE_VENDOR_CONTACT_FAX;
	 } else if (attributeName.equals("startingView")) {
      return ATTRIBUTE_STARTING_VIEW;
	 } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CatalogAddItemBean.class);
  }

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
        ATTRIBUTE_CATALOG_COMPONENT_ITEM_ID + "," + ATTRIBUTE_CATALOG_COMPONENT_ID + "," + ATTRIBUTE_CUSTOMER_REVISION_DATE + "," + ATTRIBUTE_LABEL_COLOR +"," + ATTRIBUTE_LINE_ITEM +
		  ","+ATTRIBUTE_SUGGESTED_VENDOR+","+ATTRIBUTE_VENDOR_CONTACT_PHONE+","+ATTRIBUTE_STARTING_VIEW+")" +
		  " values (" + catalogAddItemBean.getRequestId() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getMaterialDesc()) + "," + SqlHandler.delimitString(catalogAddItemBean.getManufacturer()) + "," + catalogAddItemBean.getMaterialId() + "," + catalogAddItemBean.getPartSize() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getSizeUnit()) + "," + SqlHandler.delimitString(catalogAddItemBean.getPkgStyle()) + "," + catalogAddItemBean.getMaterialApprovedBy() + "," +
        DateHandler.getOracleToDateFunction(catalogAddItemBean.getMaterialApprovedOn()) + "," + catalogAddItemBean.getItemApprovedBy() + "," + DateHandler.getOracleToDateFunction(catalogAddItemBean.getItemApprovedOn()) + "," +
        SqlHandler.delimitString(catalogAddItemBean.getMfgCatalogId()) + "," + catalogAddItemBean.getPartId() + "," + SqlHandler.delimitString(catalogAddItemBean.getMaterialType()) + "," + catalogAddItemBean.getCaseQty() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getDimension()) + "," + SqlHandler.delimitString(catalogAddItemBean.getGrade()) + "," + SqlHandler.delimitString(catalogAddItemBean.getMfgTradeName()) + "," + catalogAddItemBean.getNetwt() + "," +
        SqlHandler.delimitString(catalogAddItemBean.getNetwtUnit()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCompanyId()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCustomerMsdsNumber()) + "," +
        catalogAddItemBean.getComponentsPerItem() + "," + SqlHandler.delimitString(catalogAddItemBean.getSampleOnly()) + "," + SqlHandler.delimitString(catalogAddItemBean.getPossSize()) + "," +
        SqlHandler.delimitString(catalogAddItemBean.getPossSpecialNote()) + "," + catalogAddItemBean.getShelfLifeDays() + "," + SqlHandler.delimitString(catalogAddItemBean.getShelfLifeBasis()) + "," +
        SqlHandler.delimitString(catalogAddItemBean.getStorageTemp()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCatalogComponentItemId()) + "," + SqlHandler.delimitString(catalogAddItemBean.getCatalogComponentId()) + "," +
        DateHandler.getOracleToDateFunction(catalogAddItemBean.getCustomerRevisionDate()) + "," + SqlHandler.delimitString(catalogAddItemBean.getLabelColor())+","+catalogAddItemBean.getLineItem()+
		  "," + SqlHandler.delimitString(catalogAddItemBean.getSuggestedVendor())+"," + SqlHandler.delimitString(catalogAddItemBean.getVendorContactPhone())+","+catalogAddItemBean.getStartingView()+")";

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

    String query = "insert into catalog_add_item_orig (company_id,request_id,line_item,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
      "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
      "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note,suggested_vendor,vendor_contact_phone,starting_view)"+
      " (select company_id,request_id,line_item,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
      "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
      "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note,suggested_vendor,vendor_contact_phone,starting_view "+
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

    String query = "insert into catalog_add_item_qc (company_id,request_id,line_item,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
          "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
          "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,status,comments,suggested_vendor,vendor_contact_phone,starting_view)"+
          " (select company_id,request_id,line_item,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
          "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
          "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,"+
          "decode(material_id,null,'Pending MSDS','0','Pending MSDS','Pending QC'),'"+comments+"',suggested_vendor,vendor_contact_phone,starting_view from catalog_add_item where request_id = "+requestId.intValue()+")";

    sqlManager.update(conn, query);
  }

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