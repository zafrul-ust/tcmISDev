package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.DeptBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: DeptBeanFactory <br>
 * @version: 1.0, Nov 29, 2011 <br>
 *****************************************************************************/

public class DeptBeanFactory extends BaseBeanFactory {

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	public String ATTRIBUTE_DEPT_ID = "DEPT_ID";
	public String ATTRIBUTE_DEPT_NAME = "DEPT_NAME";
	Log log = LogFactory.getLog(getClass());

	//table name
	public String TABLE = "DEPT";

	//constructor
	public DeptBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//delete
	public int delete(DeptBean deptBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(deptBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(DeptBean deptBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(deptBean), conn);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("deptId")) {
			return ATTRIBUTE_DEPT_ID;
		}
		else if (attributeName.equals("deptName")) {
			return ATTRIBUTE_DEPT_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	public SearchCriteria getKeyCriteria(DeptBean deptBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + deptBean.getCompanyId());
		criteria.addCriterion("deptId", SearchCriterion.EQUALS, "" + deptBean.getDeptId());
		return criteria;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, DeptBean.class);
	}

	public int insert(DeptBean deptBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(deptBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(DeptBean deptBean, Connection conn) throws BaseException {
		String nextId = getDbManager().getOracleSequence("misc_seq") + "";;
		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_DEPT_ID + ",";
		query += ATTRIBUTE_DEPT_NAME + ")";
		query += " values (";
		query += SqlHandler.delimitString(deptBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(nextId) + ",";
		query += SqlHandler.delimitString(deptBean.getDeptName());
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<DeptBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<DeptBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<DeptBean> deptBeanColl = new Vector<DeptBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			DeptBean deptBean = new DeptBean();
			load(dataSetRow, deptBean);
			deptBeanColl.add(deptBean);
		}

		return deptBeanColl;
	}

	//update
	public int update(DeptBean deptBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(deptBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(DeptBean deptBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_DEPT_NAME + "=" + SqlHandler.delimitString(deptBean.getDeptName()) + " ";
		query += getWhereClause(getKeyCriteria(deptBean));

		return new SqlManager().update(conn, query);
	}
}