package com.tcmis.client.catalog.factory;

import com.tcmis.client.catalog.beans.FacilityIgViewOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * CLASSNAME: FacilityIgViewOvBeanFactory <br>
 * @version: 1.0, May 12, 2005 <br>
 *****************************************************************************/

public class FacilityIgViewOvBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
 public String ATTRIBUTE_INVENTORY_GROUPS = "INVENTORY_GROUPS";

 //table name
 public String TABLE = "FACILITY_IG_VIEW_OV";

 //constructor
 public FacilityIgViewOvBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("facilityName")) {
	 return ATTRIBUTE_FACILITY_NAME;
	}
    else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
    else if (attributeName.equals("personnelId")) {
	 return ATTRIBUTE_PERSONNEL_ID;
	}
    else if (attributeName.equals("inventoryGroups")) {
	 return ATTRIBUTE_INVENTORY_GROUPS;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, FacilityIgViewOvBean.class);
 }

 public Collection selectUserObject(String companyId, BigDecimal userId) throws BaseException,Exception {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 java.util.Map map = connection.getTypeMap();
	 map.put("CUSTOMER.FACILITY_IG_VIEW_OBJ",
		Class.forName("com.tcmis.client.catalog.beans.FacilityIgViewOvBean"));
	 map.put("CUSTOMER.INVENTORY_GROUP_NAME",
		Class.forName("com.tcmis.client.catalog.beans.InventoryGroupNameOvBean"));

	 c = selectUserObject(companyId, userId, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection selectUserObject(String companyId, BigDecimal userId, Connection conn) throws BaseException, Exception {

	Collection userFacilityIgBeanColl = new Vector();

    //get specific facility data
    String query = "select value(p) from " + TABLE + " p where "+
                   " company_id = '"+companyId+"' and personnel_id = "+userId.toString()+" order by facility_name";
	if (log.isDebugEnabled()) {
	 log.debug("QUERY:" + query);
	}
	Statement st = conn.createStatement();
	ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
	 FacilityIgViewOvBean b = (FacilityIgViewOvBean) rs.getObject(1);
	 userFacilityIgBeanColl.add(b);
	}
	rs.close();
	st.close();
	return userFacilityIgBeanColl;
 }
}