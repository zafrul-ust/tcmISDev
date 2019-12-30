package com.tcmis.client.common.factory;

import com.tcmis.client.common.beans.ChemicalApproverBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: ChemicalApproverBeanFactory <br>
 * @version: 1.0, Jan 17, 2008 <br>
 *****************************************************************************/


public class ChemicalApproverBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPROVAL_ROLE = "APPROVAL_ROLE";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_ACTIVE = "ACTIVE";
	public String ATTRIBUTE_SUPER_USER = "SUPER_USER";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_EMAIL = "EMAIL";

	//table name
	public String TABLE = "CHEMICAL_APPROVER";


	//constructor
	public ChemicalApproverBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("approvalRole")) {
			return ATTRIBUTE_APPROVAL_ROLE;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("active")) {
			return ATTRIBUTE_ACTIVE;
		}
		else if(attributeName.equals("superUser")) {
			return ATTRIBUTE_SUPER_USER;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ChemicalApproverBean.class);
	}

	public int delete(SearchCriteria criteria)
		throws BaseException {

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


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection chemicalApproverBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ChemicalApproverBean chemicalApproverBean = new ChemicalApproverBean();
			load(dataSetRow, chemicalApproverBean);
			chemicalApproverBeanColl.add(chemicalApproverBean);
		}

		return chemicalApproverBeanColl;
	}

	public Collection selectApproverEmailForRequest(BigDecimal requestId, String approvalRole)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectApproverEmailForRequest(requestId,approvalRole,connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectApproverEmailForRequest(BigDecimal requestId, String approvalRole, Connection conn)
		throws BaseException {

		Collection chemicalApproverBeanColl = new Vector();

		String query = "select distinct p.email from personnel p, chemical_approver ca, catalog_add_request_new carn"+
									 " where carn.request_id = "+requestId.toString()+" and carn.company_id = ca.company_id"+
									 " and carn.eng_eval_facility_id = ca.facility_id and carn.catalog_id = ca.catalog_id"+
				           " and ca.active = 'y' and ca.approval_role = '"+approvalRole+"' and ca.personnel_id = p.personnel_id";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ChemicalApproverBean chemicalApproverBean = new ChemicalApproverBean();
			load(dataSetRow, chemicalApproverBean);
			chemicalApproverBeanColl.add(chemicalApproverBean);
		}

		return chemicalApproverBeanColl;
	}

}