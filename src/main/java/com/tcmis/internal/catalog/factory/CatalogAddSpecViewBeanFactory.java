package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.CatalogAddSpecViewBean;

/******************************************************************************
 * CLASSNAME: CatalogAddSpecQcBeanFactory <br>
 *****************************************************************************/

public class CatalogAddSpecViewBeanFactory extends BaseBeanFactory {
	public String ATTRIBUTE_SPEC_ID = "SPEC_ID";
	public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
	public String ATTRIBUTE_SPEC_NAME = "SPEC_NAME";
	public String ATTRIBUTE_SPEC_TITLE = "SPEC_TITLE";
	public String ATTRIBUTE_SPEC_VERSION = "SPEC_VERSION";
	public String ATTRIBUTE_SPEC_AMENDMENT = "SPEC_AMENDMENT";
	public String ATTRIBUTE_SPEC_DATE = "SPEC_DATE";
	public String ATTRIBUTE_CONTENT = "CONTENT";
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SPEC_LIBRARY = "SPEC_LIBRARY";
	public String ATTRIBUTE_COC = "COC";
	public String ATTRIBUTE_COA = "COA";
	public String ATTRIBUTE_ITAR = "ITAR";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_SPEC_SOURCE = "SPEC_SOURCE";

    //table name
	public String TABLE = "CUSTOMER.CATALOG_ADD_SPEC_QC";

	Log log = LogFactory.getLog(getClass());


	//constructor
	public CatalogAddSpecViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("specId")) {
			return ATTRIBUTE_SPEC_ID;
		}
		else if(attributeName.equals("requestId")) {
			return ATTRIBUTE_REQUEST_ID;
		}
		else if(attributeName.equals("specName")) {
			return ATTRIBUTE_SPEC_NAME;
		}
		else if(attributeName.equals("specTitle")) {
			return ATTRIBUTE_SPEC_TITLE;
		}
		else if(attributeName.equals("specVersion")) {
			return ATTRIBUTE_SPEC_VERSION;
		}
		else if(attributeName.equals("specAmendment")) {
			return ATTRIBUTE_SPEC_AMENDMENT;
		}
		else if(attributeName.equals("specDate")) {
			return ATTRIBUTE_SPEC_DATE;
		}
		else if(attributeName.equals("content")) {
			return ATTRIBUTE_CONTENT;
		}
		else if(attributeName.equals("onLine")) {
			return ATTRIBUTE_ON_LINE;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("specLibrary")) {
			return ATTRIBUTE_SPEC_LIBRARY;
		}
		else if(attributeName.equals("coc")) {
			return ATTRIBUTE_COC;
		}
		else if(attributeName.equals("coa")) {
			return ATTRIBUTE_COA;
		}
		else if(attributeName.equals("itar")) {
			return ATTRIBUTE_ITAR;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("specSource")) {
			return ATTRIBUTE_SPEC_SOURCE;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}
	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CatalogAddSpecViewBean.class);
	}

	private SearchCriteria getKeyCriteria(CatalogAddSpecViewBean catalogAddSpecQcBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("requestId", SearchCriterion.EQUALS, "" + catalogAddSpecQcBean.getRequestId());
		criteria.addCriterion("lineItem", SearchCriterion.EQUALS, "" + catalogAddSpecQcBean.getLineItem());
		return criteria; 
	}
	
	//insert
	public int insert(CatalogAddSpecViewBean catalogAddSpecQcBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(catalogAddSpecQcBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(CatalogAddSpecViewBean catalogAddSpecQcBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
				ATTRIBUTE_SPEC_ID + "," +
				ATTRIBUTE_REQUEST_ID + "," +
				ATTRIBUTE_SPEC_NAME + "," +
				ATTRIBUTE_SPEC_TITLE + "," +
				ATTRIBUTE_SPEC_VERSION + "," +
				ATTRIBUTE_SPEC_AMENDMENT + "," +
				ATTRIBUTE_SPEC_DATE + "," +
				ATTRIBUTE_CONTENT + "," +
				ATTRIBUTE_ON_LINE + "," +
				ATTRIBUTE_COMPANY_ID + "," +
				ATTRIBUTE_SPEC_LIBRARY + "," +
				ATTRIBUTE_COC + "," +
				ATTRIBUTE_COA + "," +
				ATTRIBUTE_ITAR + "," +
				ATTRIBUTE_LINE_ITEM + "," +
				ATTRIBUTE_SPEC_SOURCE +
			")" +
			" values (" +
				catalogAddSpecQcBean.getSpecId() + "," +
				catalogAddSpecQcBean.getRequestId() + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getSpecName()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getSpecTitle()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getSpecVersion()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getSpecAmendment()) + "," +
				DateHandler.getOracleToDateFunction(catalogAddSpecQcBean.getSpecDate()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getContent()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getOnLine()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getCompanyId()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getSpecLibrary()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getCoc()) + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getCoa()) + "," +
				catalogAddSpecQcBean.getLineItem() + "," +
				SqlHandler.delimitString(catalogAddSpecQcBean.getSpecSource()) + 				
            ")";

		return sqlManager.update(conn, query);
	}

	//update
	public int update(CatalogAddSpecViewBean catalogAddSpecQcBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(catalogAddSpecQcBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(CatalogAddSpecViewBean catalogAddSpecQcBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update ").append(TABLE).append(" set ");
		query.append(ATTRIBUTE_SPEC_ID).append("=").append(catalogAddSpecQcBean.getSpecId()).append(",");
		query.append(ATTRIBUTE_REQUEST_ID).append("=").append(catalogAddSpecQcBean.getRequestId()).append(",");
		query.append(ATTRIBUTE_SPEC_NAME).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getSpecName())).append(",");
		query.append(ATTRIBUTE_SPEC_TITLE).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getSpecTitle())).append(",");
		query.append(ATTRIBUTE_SPEC_VERSION).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getSpecVersion())).append(",");
		query.append(ATTRIBUTE_SPEC_AMENDMENT).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getSpecAmendment())).append(",");
		query.append(ATTRIBUTE_SPEC_DATE).append("=").append(DateHandler.getOracleToDateFunction(catalogAddSpecQcBean.getSpecDate())).append(",");
		query.append(ATTRIBUTE_CONTENT).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getContent())).append(",");
		query.append(ATTRIBUTE_ON_LINE).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getOnLine())).append(",");
		query.append(ATTRIBUTE_COMPANY_ID).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getCompanyId())).append(",");
		query.append(ATTRIBUTE_SPEC_LIBRARY).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getSpecLibrary())).append(",");
		query.append(ATTRIBUTE_COC).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getCoc())).append(",");
		query.append(ATTRIBUTE_COA).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getCoa())).append(",");
		query.append(ATTRIBUTE_ITAR).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getItar())).append(",");
		query.append(ATTRIBUTE_LINE_ITEM).append("=").append(catalogAddSpecQcBean.getLineItem()).append(",");
		query.append(ATTRIBUTE_SPEC_SOURCE).append("=").append(SqlHandler.delimitString(catalogAddSpecQcBean.getSpecSource()));
        query.append(" ").append(getWhereClause(getKeyCriteria(catalogAddSpecQcBean)));

		return new SqlManager().update(conn, query.toString());
	}

	public Collection<CatalogAddSpecViewBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		Collection<CatalogAddSpecViewBean> c = null;
		try {
			connection = getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection<CatalogAddSpecViewBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<CatalogAddSpecViewBean> catalogAddSpecQcBeanColl = new Vector<CatalogAddSpecViewBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CatalogAddSpecViewBean catalogAddSpecQcBean = new CatalogAddSpecViewBean();
			load(dataSetRow, catalogAddSpecQcBean);
			catalogAddSpecQcBeanColl.add(catalogAddSpecQcBean);
		}

		return catalogAddSpecQcBeanColl;
	}
}