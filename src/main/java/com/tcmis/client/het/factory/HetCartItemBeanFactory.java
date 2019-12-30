package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetCartItemBean;
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
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetCartItemBeanFactory <br>
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetCartItemBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CART_ID = "CART_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_CONTAINER_ID = "CONTAINER_ID";
	public String ATTRIBUTE_SORT_ORDER = "SORT_ORDER";

	//table name
	public String TABLE = "HET_CART_ITEM";

	//constructor
	public HetCartItemBeanFactory(DbManager dbManager) {
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
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("containerId")) {
			return ATTRIBUTE_CONTAINER_ID;
		}
		else if (attributeName.equals("sortOrder")) {
			return ATTRIBUTE_SORT_ORDER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HetCartItemBean.class);
	}

	public SearchCriteria getKeyCriteria(HetCartItemBean HetCartItemBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + HetCartItemBean.getCompanyId());
		criteria.addCriterion("cartId", SearchCriterion.EQUALS, "" + HetCartItemBean.getCartId());
		criteria.addCriterion("containerId", SearchCriterion.EQUALS, "" + HetCartItemBean.getContainerId());
		return criteria;
	}

	//delete
	public int delete(HetCartItemBean HetCartItemBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(HetCartItemBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(HetCartItemBean HetCartItemBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(HetCartItemBean), conn);
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

	//insert
	public int insert(HetCartItemBean HetCartItemBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(HetCartItemBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(HetCartItemBean HetCartItemBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_CART_ID + ",";
		query += ATTRIBUTE_CONTAINER_ID + ",";
		query += ATTRIBUTE_SORT_ORDER;
		if (HetCartItemBean.getItemId() != null) {
			query += "," + ATTRIBUTE_ITEM_ID;
		}
		query += ")";
		query += " values (";
		query += SqlHandler.delimitString(HetCartItemBean.getCompanyId()) + ",";
		query += HetCartItemBean.getCartId() + ",";
		query += SqlHandler.delimitString(HetCartItemBean.getContainerId()) + ",";
		query += HetCartItemBean.getSortOrder();
		if (HetCartItemBean.getItemId() != null) {
			query += "," + HetCartItemBean.getItemId();
		}
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<HetCartItemBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<HetCartItemBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<HetCartItemBean> HetCartItemBeanColl = new Vector<HetCartItemBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			HetCartItemBean HetCartItemBean = new HetCartItemBean();
			load(dataSetRow, HetCartItemBean);
			HetCartItemBeanColl.add(HetCartItemBean);
		}

		return HetCartItemBeanColl;
	}

	public int update(HetCartItemBean HetCartItemBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(HetCartItemBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(HetCartItemBean HetCartItemBean, Connection conn) throws BaseException {
		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_SORT_ORDER + "=" + StringHandler.nullIfZero(HetCartItemBean.getSortOrder()) + " ";
		query += getWhereClause(getKeyCriteria(HetCartItemBean));
		return new SqlManager().update(conn, query);
	}

	public boolean cartHasItems(BigDecimal cartId) throws BaseException {
		Connection connection = null;
		try {
			StringBuilder query = new StringBuilder("select count(*) from ").append(TABLE).append(" where cart_id = ").append(cartId);
			connection = getDbManager().getConnection();
			return !"0".equals(new SqlManager().select(connection, query.toString()) );
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}
}