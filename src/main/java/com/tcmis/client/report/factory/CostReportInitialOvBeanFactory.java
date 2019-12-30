package com.tcmis.client.report.factory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.client.report.beans.CostReportInitialOvBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * CLASSNAME: CostReportInitialOvBeanFactory <br>
 * @version: 1.0, Jan 05, 2009 <br>
 *****************************************************************************/

public class CostReportInitialOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
  public String ATTRIBUTE_COST_REPORT_GROUP_LIST = "COST_REPORT_GROUP_LIST";
  public String ATTRIBUTE_COMPANY_ACCOUNT_SYS_LIST = "COMPANY_ACCOUNT_SYS_LIST";

  //table name
  public String TABLE = "COST_REPORT_INITIAL_OV";

  //constructor
  public CostReportInitialOvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
	 else if (attributeName.equals("companyName")) {
      return ATTRIBUTE_COMPANY_NAME;
    }
	 else if (attributeName.equals("costReportGroupList")) {
      return ATTRIBUTE_COST_REPORT_GROUP_LIST;
    }
	 else if (attributeName.equals("companyAccountSysList")) {
      return ATTRIBUTE_COMPANY_ACCOUNT_SYS_LIST;
    }
	 else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CostReportInitialOvBean.class);
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("CUSTOMER.COMPANY_COST_REPORT_GROUP_OBJ",Class.forName("com.tcmis.client.report.beans.CostReportInitialOvBean"));
      map.put("CUSTOMER.COST_REPORT_GROUP_OBJ",Class.forName("com.tcmis.client.report.beans.CostReportGroupObjBean"));
		map.put("CUSTOMER.FACILITY_APP_OBJ",Class.forName("com.tcmis.client.report.beans.FacilityAppObjBean"));
		map.put("CUSTOMER.APPLICATION_OBJ",Class.forName("com.tcmis.client.report.beans.ApplicationObjBean"));
		map.put("CUSTOMER.ACCOUNT_SYS_OBJ",Class.forName("com.tcmis.client.report.beans.AccountSysObjBean"));
		map.put("CUSTOMER.ACCOUNT_SYS_CHARGE_TYPE_OBJ",Class.forName("com.tcmis.client.report.beans.AccountSysChargeTypeObjBean"));
		c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection beanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +getWhereClause(criteria) + " ORDER BY " + ATTRIBUTE_COMPANY_NAME;
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      CostReportInitialOvBean b = (CostReportInitialOvBean) rs.getObject(1);
      beanColl.add(b);
    }
    rs.close();
    st.close();
    return beanColl;
  }
}