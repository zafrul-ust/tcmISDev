package com.tcmis.client.openCustomer.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.openCustomer.beans.OpenUserCustomerOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * CLASSNAME: OpenUserCustomerOvBeanFactory <br>
 * @version: 1.0, Jul 27, 2010 <br>
 *****************************************************************************/

public class OpenUserCustomerOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_USER_FACILITY_COLLECTION = "USER_FACILITY_COLLECTION";

	//table name
	public String TABLE = "OPEN_USER_CUSTOMER_OV";

	//constructor
	public OpenUserCustomerOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if (attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if (attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("userFacilityCollection")) {
			return ATTRIBUTE_USER_FACILITY_COLLECTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, OpenUserCustomerOvBean.class);
	}

	//select
	@SuppressWarnings("unchecked")
	public Collection<OpenUserCustomerOvBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Collection<OpenUserCustomerOvBean> results = new Vector();
		Connection connection = null;
		Statement st = null;
		ResultSet rows = null;
		try {
			connection = getDbManager().getConnection();

			java.util.Map map = connection.getTypeMap();
			map.put("CUSTOMER.OPEN_USER_CUSTOMER_OBJ", Class.forName("com.tcmis.client.openCustomer.beans.OpenUserCustomerOvBean"));
			map.put("CUSTOMER.OPEN_USER_FACILITY_OBJ", Class.forName("com.tcmis.client.openCustomer.beans.OpenUserFacilityOvBean"));
			String query = new StringBuilder("select value(p) from ").append(TABLE).append(" p ").append(getWhereClause(criteria)).append(getOrderByClause(sortCriteria)).toString();
			log.debug(query);

			st = connection.createStatement();
			rows = st.executeQuery(query);
			while (rows.next()) {
				results.add((OpenUserCustomerOvBean) rows.getObject(1));
			}
		}
		catch (Exception e) {
			throw new BaseException(e);
		}
		finally {
			try {
				if (rows != null) {
					rows.close();
				}
				if (st != null) {
					st.close();
				}
			}
			catch (SQLException sqle) {
				// Discard exception in finally
			}
			getDbManager().returnConnection(connection);
		}
		return results;
	}

}