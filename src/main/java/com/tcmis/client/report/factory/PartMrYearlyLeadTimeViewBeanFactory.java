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
import com.tcmis.client.report.beans.PartMrYearlyLeadTimeViewBean;

/******************************************************************************
 * CLASSNAME: PartMrYearlyLeadTimeViewBeanFactory <br>
 * @version: 1.0, May 25, 2006 <br>
 *****************************************************************************/

public class PartMrYearlyLeadTimeViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_RELEASE_DATE = "RELEASE_DATE";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
  public String ATTRIBUTE_X_DATE = "X_DATE";
  public String ATTRIBUTE_LEAD_TIME = "LEAD_TIME";
  public String ATTRIBUTE_TENTH = "TENTH";
  public String ATTRIBUTE_MEDIAN = "MEDIAN";
  public String ATTRIBUTE_NINTIETH = "NINTIETH";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

  //table name
  public String TABLE = "PART_MR_YEARLY_LEAD_TIME_VIEW";

  //constructor
  public PartMrYearlyLeadTimeViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("issueId")) {
      return ATTRIBUTE_ISSUE_ID;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("deliveryType")) {
      return ATTRIBUTE_DELIVERY_TYPE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("receiptId")) {
      return ATTRIBUTE_RECEIPT_ID;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("releaseDate")) {
      return ATTRIBUTE_RELEASE_DATE;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
    }
    else if (attributeName.equals("dateOfReceipt")) {
      return ATTRIBUTE_DATE_OF_RECEIPT;
    }
    else if (attributeName.equals("XDate")) {
      return ATTRIBUTE_X_DATE;
    }
    else if (attributeName.equals("leadTime")) {
      return ATTRIBUTE_LEAD_TIME;
    }
    else if (attributeName.equals("tenth")) {
      return ATTRIBUTE_TENTH;
    }
    else if (attributeName.equals("median")) {
      return ATTRIBUTE_MEDIAN;
    }
    else if (attributeName.equals("nintieth")) {
      return ATTRIBUTE_NINTIETH;
    }
	 else if (attributeName.equals("catalogCompanyId")) {
      return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
	 else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PartMrYearlyLeadTimeViewBean.class);
  }

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

    Collection partMrYearlyLeadTimeViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PartMrYearlyLeadTimeViewBean partMrYearlyLeadTimeViewBean = new
          PartMrYearlyLeadTimeViewBean();
      load(dataSetRow, partMrYearlyLeadTimeViewBean);
      partMrYearlyLeadTimeViewBeanColl.add(partMrYearlyLeadTimeViewBean);
    }

    return partMrYearlyLeadTimeViewBeanColl;
  }
}