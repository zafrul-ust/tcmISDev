package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.VvHetApplicationMethodBean;
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
 * CLASSNAME: VvHetApplicationMethodBean <br>
 * 
 * @version: 1.0, Aug 11, 2011 <br>
 *****************************************************************************/

public class VvHetApplicationMethodBeanFactory extends BaseBeanFactory {

	Log				log						= LogFactory.getLog(this.getClass());

	// column names
	public String	ATTRIBUTE_COMPANY_ID	= "COMPANY_ID";
	public String	ATTRIBUTE_FOR_SOLVENT	= "FOR_SOLVENT";
	public String	ATTRIBUTE_METHOD_CODE	= "METHOD_CODE";
	public String	ATTRIBUTE_METHOD		= "METHOD";
	public String	ATTRIBUTE_METHOD_ID		= "METHOD_ID";

	// table name
	public String	TABLE					= "VV_HET_APPLICATION_METHOD";

	// constructor
	public VvHetApplicationMethodBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	// get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("forSolvent")) {
			return ATTRIBUTE_FOR_SOLVENT;
		}
		else if (attributeName.equals("methodCode")) {
			return ATTRIBUTE_METHOD_CODE;
		}
		else if (attributeName.equals("method")) {
			return ATTRIBUTE_METHOD;
		}
		else if (attributeName.equals("methodId")) {
			return ATTRIBUTE_METHOD_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	// get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, VvHetApplicationMethodBean.class);
	}

	public int delete(VvHetApplicationMethodBean vvHetApplicationMethodBean) throws BaseException {
		SearchCriteria criteria = new SearchCriteria("methodId", SearchCriterion.EQUALS, "" + vvHetApplicationMethodBean.getMethodId());
		return delete(criteria);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	// you need to verify the primary key(s) before uncommenting this

	// insert
	public int insert(VvHetApplicationMethodBean vvHetApplicationMethodBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(vvHetApplicationMethodBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(VvHetApplicationMethodBean vvHetApplicationMethodBean, Connection conn) throws BaseException {
		int newId = getDbManager().getOracleSequence("HET_MISC_SEQ");
		vvHetApplicationMethodBean.setMethodId(new BigDecimal(newId));
		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (" +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_FOR_SOLVENT + "," +
		ATTRIBUTE_METHOD_CODE + "," +
		ATTRIBUTE_METHOD + "," + 
		ATTRIBUTE_METHOD_ID +
		")" + " values (" +
		SqlHandler.delimitString(vvHetApplicationMethodBean.getCompanyId()) + "," +
		"'" + (vvHetApplicationMethodBean.isForSolvent() ? "Y" : "N") + "'," +
		SqlHandler.delimitString(vvHetApplicationMethodBean.getMethodCode()) + "," +
		SqlHandler.delimitString(vvHetApplicationMethodBean.getMethod()) + "," +
		vvHetApplicationMethodBean.getMethodId() +
		")";

		return sqlManager.update(conn, query);
	}

	// update
	public int update(VvHetApplicationMethodBean vvHetApplicationMethodBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(vvHetApplicationMethodBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(VvHetApplicationMethodBean vvHetApplicationMethodBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set " +
				ATTRIBUTE_METHOD_CODE + "=" + SqlHandler.delimitString(vvHetApplicationMethodBean.getMethodCode()) + ", " +
				ATTRIBUTE_METHOD + "=" + SqlHandler.delimitString(vvHetApplicationMethodBean.getMethod()) + ", " +
				ATTRIBUTE_FOR_SOLVENT + "='" + ((vvHetApplicationMethodBean.isForSolvent() ? "Y" : "N")) + "' " +
				"where " + ATTRIBUTE_METHOD_ID + "=" + vvHetApplicationMethodBean.getMethodId();

		return new SqlManager().update(conn, query);
	}

	// select
	public Collection select(SearchCriteria criteria) throws BaseException {

		return select(criteria, null);

	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection vvHetApplicationMethodBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			VvHetApplicationMethodBean vvHetApplicationMethodBean = new VvHetApplicationMethodBean();
			load(dataSetRow, vvHetApplicationMethodBean);
			vvHetApplicationMethodBeanColl.add(vvHetApplicationMethodBean);
		}

		return vvHetApplicationMethodBeanColl;
	}
}