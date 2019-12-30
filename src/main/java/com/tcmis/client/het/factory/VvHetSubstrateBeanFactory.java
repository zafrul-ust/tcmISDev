package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.VvHetSubstrateBean;
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
 * CLASSNAME: VvHetSubstrateBeanFactory <br>
 * @version: 1.0, Aug 11, 2011 <br>
 *****************************************************************************/

public class VvHetSubstrateBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SUBSTRATE_CODE = "SUBSTRATE_CODE";
	public String ATTRIBUTE_SUBSTRATE = "SUBSTRATE";
	public String ATTRIBUTE_SUBSTRATE_ID = "SUBSTRATE_ID";

	//table name
	public String TABLE = "VV_HET_SUBSTRATE";

	//constructor
	public VvHetSubstrateBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("substrateCode")) {
			return ATTRIBUTE_SUBSTRATE_CODE;
		}
		else if (attributeName.equals("substrate")) {
			return ATTRIBUTE_SUBSTRATE;
		}
		else if (attributeName.equals("substrateId")) {
			return ATTRIBUTE_SUBSTRATE_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, VvHetSubstrateBean.class);
	}

	//delete
	public int delete(VvHetSubstrateBean vvHetSubstrateBean) throws BaseException {
		SearchCriteria criteria = new SearchCriteria("substrateId", SearchCriterion.EQUALS, "" + vvHetSubstrateBean.getSubstrateId());
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

	//insert
	public int insert(VvHetSubstrateBean vvHetSubstrateBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(vvHetSubstrateBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(VvHetSubstrateBean vvHetSubstrateBean, Connection conn) throws BaseException {
		int newId = getDbManager().getOracleSequence("HET_MISC_SEQ");
		vvHetSubstrateBean.setSubstrateId(new BigDecimal(newId));
		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (" + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_SUBSTRATE_CODE + "," + ATTRIBUTE_SUBSTRATE + "," + ATTRIBUTE_SUBSTRATE_ID + ")" + " values ("
		+ SqlHandler.delimitString(vvHetSubstrateBean.getCompanyId()) + "," + SqlHandler.delimitString(vvHetSubstrateBean.getSubstrateCode()) + "," + SqlHandler.delimitString(vvHetSubstrateBean.getSubstrate()) + ","
		+ vvHetSubstrateBean.getSubstrateId() + ")";

		return sqlManager.update(conn, query);
	}

	//update
	public int update(VvHetSubstrateBean vvHetSubstrateBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(vvHetSubstrateBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(VvHetSubstrateBean vvHetSubstrateBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set " + ATTRIBUTE_SUBSTRATE_CODE + "=" + SqlHandler.delimitString(vvHetSubstrateBean.getSubstrateCode()) + "," + ATTRIBUTE_SUBSTRATE + "=" + SqlHandler.delimitString(vvHetSubstrateBean.getSubstrate())
		+ " " + "where " + ATTRIBUTE_SUBSTRATE_ID + "=" + vvHetSubstrateBean.getSubstrateId();

		return new SqlManager().update(conn, query);
	}

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {

		return select(criteria, null);

	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection vvHetSubstrateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			VvHetSubstrateBean vvHetSubstrateBean = new VvHetSubstrateBean();
			load(dataSetRow, vvHetSubstrateBean);
			vvHetSubstrateBeanColl.add(vvHetSubstrateBean);
		}

		return vvHetSubstrateBeanColl;
	}
}