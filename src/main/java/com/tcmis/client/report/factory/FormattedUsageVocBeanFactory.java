package com.tcmis.client.report.factory;

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
import com.tcmis.client.report.beans.FormattedUsageVocBean;

/******************************************************************************
 * CLASSNAME: ListBeanFactory <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/
public class FormattedUsageVocBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";
  public String ATTRIBUTE_RPT_CHEMICAL = "RPT_CHEMICAL";
  public String ATTRIBUTE_FACILITY = "FACILITY";
  public String ATTRIBUTE_WORK_AREA = "WORK_AREA";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
  public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
  public String ATTRIBUTE_QTY_SHIPPED = "QTY_SHIPPED";
  public String ATTRIBUTE_WT_PER_UNIT = "WT_PER_UNIT";
  public String ATTRIBUTE_WT_SHIPPED = "WT_SHIPPED";
  public String ATTRIBUTE_PERCENT = "PERCENT";
  public String ATTRIBUTE_LBS_REPORTABLE = "LBS_REPORTABLE";
  public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
  public String ATTRIBUTE_WT_VOC = "WT_VOC";

  //constructor
  public FormattedUsageVocBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("casNumber")) {
      return ATTRIBUTE_CAS_NUMBER;
    } else if (attributeName.equals("rptChemical")) {
      return ATTRIBUTE_RPT_CHEMICAL;
    } else if (attributeName.equals("facility")) {
      return ATTRIBUTE_FACILITY;
    } else if (attributeName.equals("workArea")) {
      return ATTRIBUTE_WORK_AREA;
    } else if (attributeName.equals("deliveryPoint")) {
      return ATTRIBUTE_DELIVERY_POINT;
    } else if (attributeName.equals("facPartNo")) {
      return ATTRIBUTE_FAC_PART_NO;
    } else if (attributeName.equals("tradeName")) {
      return ATTRIBUTE_TRADE_NAME;
    } else if (attributeName.equals("qtyShipped")) {
      return ATTRIBUTE_QTY_SHIPPED;
    } else if (attributeName.equals("wtPerUnit")) {
      return ATTRIBUTE_WT_PER_UNIT;
    } else if (attributeName.equals("wtShipped")) {
      return ATTRIBUTE_WT_SHIPPED;
    } else if (attributeName.equals("percent")) {
      return ATTRIBUTE_PERCENT;
    } else if (attributeName.equals("lbsReportable")) {
      return ATTRIBUTE_LBS_REPORTABLE;
    } else if (attributeName.equals("dateShipped")) {
      return ATTRIBUTE_DATE_SHIPPED;
    } else if (attributeName.equals("wtVoc")) {
      return ATTRIBUTE_WT_VOC;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, FormattedUsageVocBean.class);
  }

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

    Collection formattedUsageVocBeanColl = new Vector();

    String query = "select * from ";

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      FormattedUsageVocBean formattedUsageVocBean = new FormattedUsageVocBean();
      load(dataSetRow, formattedUsageVocBean);
      formattedUsageVocBeanColl.add(formattedUsageVocBean);
    }

    return formattedUsageVocBeanColl;
  }

} //end of class