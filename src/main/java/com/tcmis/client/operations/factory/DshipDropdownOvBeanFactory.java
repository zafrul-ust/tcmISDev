package com.tcmis.client.operations.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.client.operations.beans.DshipDropdownOvBean;

/******************************************************************************
 * CLASSNAME: OtrackDropdownOvBeanFactory <br>
 * @version: 1.0, Mar 1, 2007 <br>
 *****************************************************************************/

public class DshipDropdownOvBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
  public String ATTRIBUTE_FACILITY_LIST = "FACILITY_LIST";

  //table name
  public String TABLE = "dship_dropdown_ov";

  //constructor
  public DshipDropdownOvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    } else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    } else if (attributeName.equals("companyName")) {
      return ATTRIBUTE_COMPANY_NAME;
    } else if (attributeName.equals("facilityList")) {
      return ATTRIBUTE_FACILITY_LIST;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, DshipDropdownOvBean.class);
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("CUSTOMER.DSHIP_DROPDOWN_OBJ", Class.forName("com.tcmis.client.operations.beans.DshipDropdownOvBean"));
      map.put("CUSTOMER.DSHIP_FACILITY_OBJ", Class.forName("com.tcmis.client.operations.beans.DshipFacilityObjBean"));
      map.put("CUSTOMER.DSHIP_FAC_DOCK_OBJ", Class.forName("com.tcmis.client.operations.beans.DshipFacilityDockObjBean"));
      c = selectObject(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws BaseException, Exception {

    Collection dshipDropdownOvBeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " + getWhereClause(criteria);

    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      DshipDropdownOvBean b = (DshipDropdownOvBean) rs.getObject(1);
      dshipDropdownOvBeanColl.add(b);
    }
    rs.close();
    st.close();

    return dshipDropdownOvBeanColl;
} //end of class

} //end of class