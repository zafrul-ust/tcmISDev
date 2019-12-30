package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetCartBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetCartBeanFactory <br>
 * @version: 1.0, May 27, 2011 <br>
 *****************************************************************************/

public class HetCartBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CART_ID = "CART_ID";
	public String ATTRIBUTE_CART_NAME = "CART_NAME";
	public String ATTRIBUTE_CART_STATUS = "CART_STATUS";
	public String ATTRIBUTE_APPLICATION_ID = "APPLICATION_ID";
	public String ATTRIBUTE_EMPLOYEE = "EMPLOYEE";
	public String ATTRIBUTE_LAST_LOGGED = "LAST_LOGGED";
	public String ATTRIBUTE_CHECKED_OUT = "CHECKED_OUT";

	//table name
	public String TABLE = "HET_CART";

	//constructor
	public HetCartBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("cartId")) {
			return ATTRIBUTE_CART_ID;
		}
		else if (attributeName.equals("cartName")) {
			return ATTRIBUTE_CART_NAME;
		}
		else if (attributeName.equals("cartStatus")) {
			return ATTRIBUTE_CART_STATUS;
		}
		else if (attributeName.equals("applicationId")) {
			return ATTRIBUTE_APPLICATION_ID;
		}
		else if (attributeName.equals("lastLogged")) {
			return ATTRIBUTE_LAST_LOGGED;
		}
		else if (attributeName.equals("checkedOut")) {
			return ATTRIBUTE_CHECKED_OUT;
		}
		else if (attributeName.equals("employee")) {
			return ATTRIBUTE_EMPLOYEE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HetCartBean.class);
	}

	public SearchCriteria getKeyCriteria(HetCartBean HetCartBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + HetCartBean.getCompanyId());
		criteria.addCriterion("cartId", SearchCriterion.EQUALS, "" + HetCartBean.getCartId());
		return criteria;
	}

	//delete
	public int delete(HetCartBean HetCartBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(HetCartBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(HetCartBean HetCartBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(HetCartBean), conn);
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

	public int insert(HetCartBean HetCartBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(HetCartBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(HetCartBean HetCartBean, Connection conn) throws BaseException {
		int newId = getDbManager().getOracleSequence("HET_MISC_SEQ");
		SqlManager sqlManager = new SqlManager();
		HetCartBean.setCartId(new BigDecimal(newId));

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_CART_ID + ",";
		query += ATTRIBUTE_CART_NAME + ",";
		query += ATTRIBUTE_CART_STATUS + ",";
		query += ATTRIBUTE_LAST_LOGGED + ",";
		query += ATTRIBUTE_CHECKED_OUT + ",";
		query += ATTRIBUTE_EMPLOYEE + ",";
		query += ATTRIBUTE_APPLICATION_ID + ")";
		query += " values (";
		query += SqlHandler.delimitString(HetCartBean.getCompanyId()) + ",";
		query += newId + ",";
		query += SqlHandler.delimitString(HetCartBean.getCartName()) + ",";
		query += SqlHandler.delimitString(HetCartBean.getCartStatus()) + ",";
		query += "sysdate,";
		query += DateHandler.getOracleToDateFunction(HetCartBean.getCheckedOut()) + ",";
		query += SqlHandler.delimitString(HetCartBean.getEmployee()) + ",";
		query += HetCartBean.getApplicationId();
		query += ")";
		return sqlManager.update(conn, query);
	}

	//update
	public int update(HetCartBean HetCartBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(HetCartBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(HetCartBean HetCartBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_CART_NAME + "=" + SqlHandler.delimitString(HetCartBean.getCartName()) + ",";
		query += ATTRIBUTE_CART_STATUS + "=" + SqlHandler.delimitString(HetCartBean.getCartStatus()) + ",";
		query += ATTRIBUTE_CHECKED_OUT + "=" + DateHandler.getOracleToDateFunction(HetCartBean.getCheckedOut()) + ",";
		query += ATTRIBUTE_EMPLOYEE + "=" + SqlHandler.delimitString(HetCartBean.getEmployee()) + ",";
		query += ATTRIBUTE_APPLICATION_ID + "=" + StringHandler.nullIfZero(HetCartBean.getApplicationId()) + " ";
		query += getWhereClause(getKeyCriteria(HetCartBean));

		return new SqlManager().update(conn, query);
	}

	public void markLastLogged(String companyId, BigDecimal cartId) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			String query = "update " + TABLE + " set ";
			query += ATTRIBUTE_LAST_LOGGED + "= sysdate, ";
			query += ATTRIBUTE_CART_STATUS + "= 'I' ";
			query += "where " + ATTRIBUTE_CART_ID  +" = " + cartId;
			query += " and " + ATTRIBUTE_COMPANY_ID  +" = " + SqlHandler.delimitString(companyId) ;

			new SqlManager().update(connection, query);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<HetCartBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<HetCartBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<HetCartBean> HetCartBeanColl = new Vector<HetCartBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			HetCartBean HetCartBean = new HetCartBean();
			load(dataSetRow, HetCartBean);
			HetCartBeanColl.add(HetCartBean);
		}

		return HetCartBeanColl;
	}

	public boolean isCartCheckedOut(String cartName) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(getDbManager());
		String query = "select count(*) from " + TABLE + " where " + ATTRIBUTE_CART_NAME  + " = '" + cartName + "' and CART_STATUS = 'A'";
		return !"0".equals(factory.selectSingleValue(query));
	}

}