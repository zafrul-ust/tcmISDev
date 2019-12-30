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
import com.tcmis.client.catalog.beans.BuyerMaterialRequestViewBean;

/******************************************************************************
 * CLASSNAME: BuyerMaterialRequestViewBeanFactory <br>
 * @version: 1.0, Aug 27, 2007 <br>
 *****************************************************************************/

public class BuyerMaterialRequestViewBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
  public String ATTRIBUTE_FULL_NAME = "FULL_NAME";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
  public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_EMAIL = "EMAIL";

  //table name
  public String TABLE = "BUYER_MATERIAL_REQUEST_VIEW";

  //constructor
  public BuyerMaterialRequestViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("branchPlant")) {
      return ATTRIBUTE_BRANCH_PLANT;
    } else if (attributeName.equals("buyerId")) {
      return ATTRIBUTE_BUYER_ID;
    } else if (attributeName.equals("fullName")) {
      return ATTRIBUTE_FULL_NAME;
    } else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    } else if (attributeName.equals("radianPo")) {
      return ATTRIBUTE_RADIAN_PO;
    } else if (attributeName.equals("receiptId")) {
      return ATTRIBUTE_RECEIPT_ID;
    } else if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    } else if (attributeName.equals("email")) {
      return ATTRIBUTE_EMAIL;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, BuyerMaterialRequestViewBean.class);
  }

  //select
  public Collection select(String companyId, BigDecimal prNumber, String lineItem) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(companyId, prNumber, lineItem, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(String companyId, BigDecimal prNumber, String lineItem, Connection conn) throws BaseException {

    Collection buyerMaterialRequestViewBeanColl = new Vector();

    String query = "select distinct bo.branch_plant,bo.buyer_id,fx_customer_personnel_name(bo.buyer_id) full_name,"+
                   "fx_personnel_id_to_email(bo.buyer_id) email,bo.pr_number,bo.radian_po,r.receipt_id "+
                   " from tcm_ops.buy_order bo, tcm_ops.receipt r" + " where bo.mr_number = " +
                   prNumber.toString() + " and bo.mr_line_item = '" + lineItem + "' and" +
                   " bo.date_issued = (select max(date_issued) from tcm_ops.buy_order" +
                   " where mr_number = " + prNumber.toString() + " and mr_line_item = '" + lineItem + "')" +
                   " and bo.radian_po = r.radian_po(+) and bo.item_id = r.item_id(+)";

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      BuyerMaterialRequestViewBean buyerMaterialRequestViewBean = new BuyerMaterialRequestViewBean();
      load(dataSetRow, buyerMaterialRequestViewBean);
      buyerMaterialRequestViewBeanColl.add(buyerMaterialRequestViewBean);
    }

    return buyerMaterialRequestViewBeanColl;
  } //end of method

  public Collection selectCsrNExpiderForHub(String companyId, String branchPlant) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCsrNExpiderForHub(companyId,branchPlant, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCsrNExpiderForHub(String companyId, String branchPlant, Connection conn) throws BaseException {

    Collection csrNExpiderForHubColl = new Vector();

    String query = "select distinct p.email from personnel p, facility f, user_group_member ugm "+
                   "where p.personnel_id = ugm.personnel_id and ugm.facility_id = f.facility_id and ugm.company_id = f.company_id "+
                   " and f.branch_plant = "+branchPlant+" and f.company_id = '"+companyId+"' and ugm.user_group_id = 'CSR' union "+
                   "select personnel_id from user_group_member where user_group_id = 'Expediter' and company_id = '"+companyId+"'";


    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      BuyerMaterialRequestViewBean buyerMaterialRequestViewBean = new BuyerMaterialRequestViewBean();
      load(dataSetRow, buyerMaterialRequestViewBean);
      csrNExpiderForHubColl.add(buyerMaterialRequestViewBean.getEmail());
    }

    return csrNExpiderForHubColl;
  } //end of method

  public Collection selectCsrForMr(String companyId, BigDecimal prNumber) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCsrForMr(companyId,prNumber, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCsrForMr(String companyId, BigDecimal prNumber, Connection conn) throws BaseException {

    Collection csrForMrColl = new Vector();

    String query = "select distinct p.email from personnel p, facility f, user_group_member ugm, purchase_request pr "+
                   "where p.personnel_id = ugm.personnel_id and ugm.facility_id = f.preferred_warehouse and ugm.company_id = f.company_id and "+
                   "ugm.user_group_id = 'CSR' and pr.facility_id = f.facility_id and pr.company_id = f.company_id and pr.company_id = '"+companyId+"'"+
                   " and pr.pr_number = "+prNumber.toString();

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      BuyerMaterialRequestViewBean buyerMaterialRequestViewBean = new BuyerMaterialRequestViewBean();
      load(dataSetRow, buyerMaterialRequestViewBean);
      csrForMrColl.add(buyerMaterialRequestViewBean.getEmail());
    }

    return csrForMrColl;
  } //end of method

} //end of class