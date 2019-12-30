package com.tcmis.client.waste.factory;

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
import com.tcmis.client.waste.beans.TsdfReportDropdownOvBean;

/******************************************************************************
 * CLASSNAME: TsdfReportDropdownOvBeanFactory <br>
 * @version: 1.0, Mar 1, 2007 <br>
 *****************************************************************************/

public class TsdfReportDropdownOvBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_TSDF_FACILITY_NAME = "TSDF_FACILITY_NAME";
  public String ATTRIBUTE_TSDF_FACILITY_ID = "TSDF_FACILITY_ID";
  public String ATTRIBUTE_TSDF_VENDOR_ID = "TSDF_VENDOR_ID";
  public String ATTRIBUTE_TSDF_VENDOR_NAME = "TSDF_VENDOR_NAME";
  public String ATTRIBUTE_TO_VENDOR_LIST = "TO_VENDOR_LIST";
  public String ATTRIBUTE_WASTE_GENERATION_COMPANY_LIST = "WASTE_GENERATION_COMPANY_LIST";

  //table name
  public String TABLE = "TSDF_REPORT_DROPDOWN_OV";

  //constructor
  public TsdfReportDropdownOvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    } else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    } else if (attributeName.equals("tsdfFacilityName")) {
      return ATTRIBUTE_TSDF_FACILITY_NAME;
    } else if (attributeName.equals("tsdfFacilityId")) {
      return ATTRIBUTE_TSDF_FACILITY_ID;
    } else if (attributeName.equals("tsdfVendorId")) {
      return ATTRIBUTE_TSDF_VENDOR_ID;
    } else if (attributeName.equals("tsdfVendorName")) {
      return ATTRIBUTE_TSDF_VENDOR_NAME;
    } else if (attributeName.equals("toVendorList")) {
      return ATTRIBUTE_TO_VENDOR_LIST;
    } else if (attributeName.equals("wasteGenerationCompanyList")) {
      return ATTRIBUTE_WASTE_GENERATION_COMPANY_LIST;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, TsdfReportDropdownOvBean.class);
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("CUSTOMER.TSDF_REPORT_DROPDOWN_OBJ", Class.forName("com.tcmis.client.waste.beans.TsdfReportDropdownOvBean"));
      map.put("CUSTOMER.TO_VENDOR_OBJ", Class.forName("com.tcmis.client.waste.beans.ToVendorObjBean"));
      map.put("CUSTOMER.WASTE_GENERATION_COMPANY_OBJ", Class.forName("com.tcmis.client.waste.beans.WasteGenerationCompanyObjBean"));
      map.put("CUSTOMER.GEN_FACILITY_OBJ", Class.forName("com.tcmis.client.waste.beans.GenFacilityObjBean"));
      map.put("CUSTOMER.WASTE_LOCATION_OBJ", Class.forName("com.tcmis.client.waste.beans.WasteLocationObjBean"));
      c = selectObject(criteria, connection);
    } catch (Exception e) {
      log.error("selectObject error:" + e.getMessage());
      DbSelectException ex = new DbSelectException("error.db.select");
      ex.setRootCause(e);
      throw ex;
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws BaseException {

    Collection tsdfReportDropdownOvBeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " + getWhereClause(criteria);

    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        TsdfReportDropdownOvBean b = (TsdfReportDropdownOvBean) rs.getObject(1);
        tsdfReportDropdownOvBeanColl.add(b);
      }
      rs.close();
      st.close();
    } catch (SQLException e) {
      log.error("selectObject error:" + e.getMessage());
      DbSelectException ex = new DbSelectException("error.db.select");
      ex.setRootCause(e);
      throw ex;
    }
    return tsdfReportDropdownOvBeanColl;
  } //end of class

} //end of class