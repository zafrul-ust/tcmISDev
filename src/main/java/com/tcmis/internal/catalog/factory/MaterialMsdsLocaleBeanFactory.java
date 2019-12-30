package com.tcmis.internal.catalog.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.MaterialMsdsLocaleBean;

/******************************************************************************
 * CLASSNAME: MaterialMsdsLocaleViewBeanFactory <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/

public class MaterialMsdsLocaleBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_CONTENT = "CONTENT";
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_LOCALE_CODE = "LOCALE_CODE";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	
	//table name
	public String TABLE = "GLOBAL.MATERIAL_MSDS_LOCALE_VIEW";

	//constructor
	public MaterialMsdsLocaleBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("content")) {
			return ATTRIBUTE_CONTENT;
		}
		else if(attributeName.equals("onLine")){
			return ATTRIBUTE_ON_LINE;
		}
		else if(attributeName.equals("insertDate")){
			return ATTRIBUTE_INSERT_DATE;
		}
		else if(attributeName.equals("localeCode")){
			return ATTRIBUTE_LOCALE_CODE;
		}
		else if(attributeName.equals("materialDesc")){
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("tradeName")){
			return ATTRIBUTE_TRADE_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, MaterialMsdsLocaleBean.class);
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

		Collection materialMsdsLocaleViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MaterialMsdsLocaleBean materialMsdsLocaleViewBean = new MaterialMsdsLocaleBean();
			load(dataSetRow, materialMsdsLocaleViewBean);
			materialMsdsLocaleViewBeanColl.add(materialMsdsLocaleViewBean);
		}

		return materialMsdsLocaleViewBeanColl;
	}
	
	public Collection upsert(MaterialMsdsLocaleBean m) throws BaseException {
		GenericProcedureFactory factory = new GenericProcedureFactory(getDbManager());
		String isOnLine = (!StringHandler.isBlankString(m.getOnLine()) && m.getOnLine().contains("true")) ? "Y" : "N";
		Vector inArgs = new Vector();
		inArgs.add(m.getMaterialId());
		inArgs.add(m.getRevisionDate());
		inArgs.add(m.getLocaleCode());
		inArgs.add(m.getContent());
		inArgs.add(isOnLine);
		inArgs.add(m.getMaterialDesc());
		inArgs.add(m.getTradeName());
		inArgs.add("");
	
		return factory.doProcedure("global.p_material_msds_locale_upsert", inArgs, new Vector());
	}
}