package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogAddFormBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CatalogAddFormBeanFactory <br>
 * @version: 1.0, Dec 3, 2010 <br>
 *****************************************************************************/

public class CatalogAddFormBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
	public String ATTRIBUTE_FORM_ID = "FORM_ID";
	public String ATTRIBUTE_USED_AS = "USED_AS";
	public String ATTRIBUTE_USE_TYPE = "USE_TYPE";
	public String ATTRIBUTE_SUBSTRATE_PART = "SUBSTRATE_PART";
	public String ATTRIBUTE_SUBSTRATE = "SUBSTRATE";
	public String ATTRIBUTE_SENT_OUTSIDE_US = "SENT_OUTSIDE_US";
	public String ATTRIBUTE_AUTOCLAVABLE = "AUTOCLAVABLE";
	public String ATTRIBUTE_STRUCTURAL = "STRUCTURAL";
	public String ATTRIBUTE_MATERIAL_TYPE = "MATERIAL_TYPE";

	//table name
	public String TABLE = "CATALOG_ADD_FORM";

	//constructor
	public CatalogAddFormBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("requestId")) {
			return ATTRIBUTE_REQUEST_ID;
		}
		else if (attributeName.equals("formId")) {
			return ATTRIBUTE_FORM_ID;
		}
		else if (attributeName.equals("usedAs")) {
			return ATTRIBUTE_USED_AS;
		}
		else if (attributeName.equals("useType")) {
			return ATTRIBUTE_USE_TYPE;
		}
		else if (attributeName.equals("substratePart")) {
			return ATTRIBUTE_SUBSTRATE_PART;
		}
		else if (attributeName.equals("substrate")) {
			return ATTRIBUTE_SUBSTRATE;
		}
		else if (attributeName.equals("sentOutsideUs")) {
			return ATTRIBUTE_SENT_OUTSIDE_US;
		}
		else if (attributeName.equals("autoclavable")) {
			return ATTRIBUTE_AUTOCLAVABLE;
		}
		else if (attributeName.equals("structural")) {
			return ATTRIBUTE_STRUCTURAL;
		}
		else if (attributeName.equals("materialType")) {
			return ATTRIBUTE_MATERIAL_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CatalogAddFormBean.class);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

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

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//insert
	public int insert(CatalogAddFormBean catalogAddFormBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(catalogAddFormBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(CatalogAddFormBean catalogAddFormBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (" + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_REQUEST_ID + "," + ATTRIBUTE_FORM_ID + "," + ATTRIBUTE_USED_AS + "," + ATTRIBUTE_USE_TYPE + "," + ATTRIBUTE_SUBSTRATE_PART + "," + ATTRIBUTE_SUBSTRATE
		+ "," + ATTRIBUTE_SENT_OUTSIDE_US + "," + ATTRIBUTE_AUTOCLAVABLE + "," + ATTRIBUTE_STRUCTURAL + "," + ATTRIBUTE_MATERIAL_TYPE + ")" + " values (" + SqlHandler.delimitString(catalogAddFormBean.getCompanyId()) + ","
		+ catalogAddFormBean.getRequestId() + "," + SqlHandler.delimitString(catalogAddFormBean.getFormId()) + "," + SqlHandler.delimitString(catalogAddFormBean.getUsedAs()) + ","
		+ SqlHandler.delimitString(catalogAddFormBean.getUseType()) + "," + SqlHandler.delimitString(catalogAddFormBean.getSubstratePart()) + "," + SqlHandler.delimitString(catalogAddFormBean.getSubstrate()) + ","
		+ SqlHandler.delimitString(catalogAddFormBean.getSentOutsideUs()) + "," + SqlHandler.delimitString(catalogAddFormBean.getAutoclavable()) + "," + SqlHandler.delimitString(catalogAddFormBean.getStructural()) + ","
		+ SqlHandler.delimitString(catalogAddFormBean.getMaterialType()) + ")";

		return sqlManager.update(conn, query);
	}

	//update
	public int update(CatalogAddFormBean catalogAddFormBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(catalogAddFormBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(CatalogAddFormBean catalogAddFormBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set " + ATTRIBUTE_COMPANY_ID + "=" + SqlHandler.delimitString(catalogAddFormBean.getCompanyId()) + "," + ATTRIBUTE_REQUEST_ID + "=" + StringHandler.nullIfZero(catalogAddFormBean.getRequestId()) + ","
		+ ATTRIBUTE_FORM_ID + "=" + SqlHandler.delimitString(catalogAddFormBean.getFormId()) + "," + ATTRIBUTE_USED_AS + "=" + SqlHandler.delimitString(catalogAddFormBean.getUsedAs()) + "," + ATTRIBUTE_USE_TYPE + "="
		+ SqlHandler.delimitString(catalogAddFormBean.getUseType()) + "," + ATTRIBUTE_SUBSTRATE_PART + "=" + SqlHandler.delimitString(catalogAddFormBean.getSubstratePart()) + "," + ATTRIBUTE_SUBSTRATE + "="
		+ SqlHandler.delimitString(catalogAddFormBean.getSubstrate()) + "," + ATTRIBUTE_SENT_OUTSIDE_US + "=" + SqlHandler.delimitString(catalogAddFormBean.getSentOutsideUs()) + "," + ATTRIBUTE_AUTOCLAVABLE + "="
		+ SqlHandler.delimitString(catalogAddFormBean.getAutoclavable()) + "," + ATTRIBUTE_STRUCTURAL + "=" + SqlHandler.delimitString(catalogAddFormBean.getStructural()) + "," + ATTRIBUTE_MATERIAL_TYPE + "="
		+ SqlHandler.delimitString(catalogAddFormBean.getMaterialType()) + " " + "where " + ATTRIBUTE_COMPANY_ID + "=" + catalogAddFormBean.getCompanyId();

		return new SqlManager().update(conn, query);
	}

	//select
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

		Collection catalogAddFormBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CatalogAddFormBean catalogAddFormBean = new CatalogAddFormBean();
			load(dataSetRow, catalogAddFormBean);
			catalogAddFormBeanColl.add(catalogAddFormBean);
		}

		return catalogAddFormBeanColl;
	}
}