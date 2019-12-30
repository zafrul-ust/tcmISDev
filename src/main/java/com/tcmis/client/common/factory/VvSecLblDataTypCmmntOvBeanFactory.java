package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.VvSecLblDataTypCmmntOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;


/******************************************************************************
 * CLASSNAME: OpenUserShiptoSelOvBeanFactory <br>
 * @version: 1.0, Feb 16, 2011 <br>
 *****************************************************************************/


public class VvSecLblDataTypCmmntOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_TYPE_ID = "TYPE_ID";
	public String ATTRIBUTE_TYPE_NAME = "TYPE_NAME";
	public String ATTRIBUTE_COMMENT_LIST = "COMMENT_LIST";

	//table name
	public String TABLE = "VV_SEC_LBL_DATA_TYP_CMMNT_OV";

	//constructor
	public VvSecLblDataTypCmmntOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("typeId")) {
			return ATTRIBUTE_TYPE_ID;
		}
		else if(attributeName.equals("typeName")) {
			return ATTRIBUTE_TYPE_NAME;
		}
		else if(attributeName.equals("commentList")) {
			return ATTRIBUTE_COMMENT_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VvSecLblDataTypCmmntOvBean.class);
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
	 return selectObject(criteria,null);
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
		 connection = this.getDbManager().getConnection();
		 java.util.Map map = connection.getTypeMap();
		 map.put("CUSTOMER.VV_SEC_LBL_DATA_TYP_CMMNT_OBJ",
			Class.forName("com.tcmis.client.common.beans.VvSecLblDataTypCmmntOvBean"));
		 map.put("CUSTOMER.VV_SEC_LBL_DAT_TYP_COMMENT_OBJ",
			Class.forName("com.tcmis.client.common.beans.VvSecLblDatTypCommentObjBean"));

		 c = selectObject(criteria, sortCriteria,connection);
		}
		catch (Exception e) {
		 log.error("selectObject error:" + e.getMessage());
		 DbSelectException ex = new DbSelectException("error.db.select");
		 ex.setRootCause(e);
		 throw ex;
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return c;
	 }

	 public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
		BaseException {
		Collection ovBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + getOrderByClause(sortCriteria);
		 log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					VvSecLblDataTypCmmntOvBean b = (VvSecLblDataTypCmmntOvBean) rs.getObject(1);
					ovBeanColl.add(b);
				}
				rs.close();
				st.close();
			}
			catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
			return ovBeanColl;
	}

}