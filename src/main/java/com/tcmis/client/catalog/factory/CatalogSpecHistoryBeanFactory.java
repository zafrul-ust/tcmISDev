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
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.catalog.beans.CatalogSpecHistoryBean;
import com.tcmis.client.catalog.beans.CatalogSpecInputBean;

/******************************************************************************
 * CLASSNAME: CatalogSpecViewBeanFactory <br>
 * @version: 1.0, Dec 1, 2006 <br>
 *****************************************************************************/

public class CatalogSpecHistoryBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
  public String ATTRIBUTE_DETAIL = "DETAIL";
  public String ATTRIBUTE_DATE_SET = "DATE_SET";
  public String ATTRIBUTE_SET_BY = "SET_BY";
  public String ATTRIBUTE_COC = "COC";
  public String ATTRIBUTE_COA = "COA";
  public String ATTRIBUTE_SPEC_ID = "SPEC_ID";

  //table name
  //public String TABLE = "CATALOG_SPEC_VIEW";

  //constructor
  public CatalogSpecHistoryBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("facPartNo")) {
      return ATTRIBUTE_FAC_PART_NO;
    }
    else if (attributeName.equals("detail")) {
      return ATTRIBUTE_DETAIL;
    }
    else if (attributeName.equals("dateSet")) {
      return ATTRIBUTE_DATE_SET;
    }
    else if (attributeName.equals("setBy")) {
      return ATTRIBUTE_SET_BY;
    }
    else if (attributeName.equals("coc")) {
      return ATTRIBUTE_COC;
    }
    else if (attributeName.equals("coa")) {
      return ATTRIBUTE_COA;
    }
    else if (attributeName.equals("specId")) {
      return ATTRIBUTE_SPEC_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CatalogSpecHistoryBean.class);
  }




  public Collection getHistory(CatalogSpecInputBean bean) throws
      BaseException, Exception {
    Collection catalogSpecHistoryBeanCollection = new Vector();

    DataSet ds = this.getDbManager().select(this.getHistoryQuery(bean));
    Iterator dataIter = ds.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogSpecHistoryBean catalogSpecHistoryBean = new CatalogSpecHistoryBean();
      load(dataSetRow, catalogSpecHistoryBean);
      catalogSpecHistoryBeanCollection.add(catalogSpecHistoryBean);
    }
    return catalogSpecHistoryBeanCollection;
  }

  private String getHistoryQuery(CatalogSpecInputBean bean)  throws
      BaseException, Exception {

    Collection inArgs = new Vector(3);
    if(bean.getHistoryCatalogId() != null) {
      inArgs.add(bean.getHistoryCatalogId());
    }
    else {
      inArgs.add("");
    }
    if(bean.getHistoryCatPartNo() != null) {
      inArgs.add(bean.getHistoryCatPartNo());
    }
    else {
      inArgs.add("");
    }
    if(bean.getHistorySpecId() != null) {
      inArgs.add(bean.getHistorySpecId());
    }
    else {
      inArgs.add("");
    }
    Collection outArgs = new Vector(1);
    outArgs.add(new Integer(java.sql.Types.VARCHAR));

    GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
    Collection resultCollection = procFactory.doProcedure("p_catalog_spec_history", inArgs, outArgs);
    Iterator iterator = resultCollection.iterator();
    String result = null;
    while(iterator.hasNext()) {
      result = (String)iterator.next();
    }
    if(log.isDebugEnabled()) {
      log.debug("Result:" + result);
    }
    return result;
  }
}