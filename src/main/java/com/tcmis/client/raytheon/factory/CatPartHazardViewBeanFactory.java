package com.tcmis.client.raytheon.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.raytheon.beans.CatPartHazardViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;


/******************************************************************************
 * CLASSNAME: CatPartHazardViewBeanFactory <br>
 * @version: 1.0, Oct 4, 2006 <br>
 *****************************************************************************/


public class CatPartHazardViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_HEALTH = "HEALTH";
	public String ATTRIBUTE_FLAMMABILITY = "FLAMMABILITY";
	public String ATTRIBUTE_REACTIVITY = "REACTIVITY";
	public String ATTRIBUTE_SPECIFIC_HAZARD = "SPECIFIC_HAZARD";
	public String ATTRIBUTE_TARGET_ORGAN = "TARGET_ORGAN";
	public String ATTRIBUTE_SIGNAL_WORD = "SIGNAL_WORD";
	public String ATTRIBUTE_HMIS_FLAMMABILITY = "HMIS_FLAMMABILITY";
	public String ATTRIBUTE_HMIS_REACTIVITY = "HMIS_REACTIVITY";
	public String ATTRIBUTE_HMIS_HEALTH = "HMIS_HEALTH";

	//table name
	public String TABLE = "CAT_PART_HAZARD_VIEW";


	//constructor
	public CatPartHazardViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("health")) {
			return ATTRIBUTE_HEALTH;
		}
		else if(attributeName.equals("flammability")) {
			return ATTRIBUTE_FLAMMABILITY;
		}
		else if(attributeName.equals("reactivity")) {
			return ATTRIBUTE_REACTIVITY;
		}
		else if(attributeName.equals("specificHazard")) {
			return ATTRIBUTE_SPECIFIC_HAZARD;
		}
		else if(attributeName.equals("targetOrgan")) {
			return ATTRIBUTE_TARGET_ORGAN;
		}
		else if(attributeName.equals("signalWord")) {
			return ATTRIBUTE_SIGNAL_WORD;
		}
		else if(attributeName.equals("hmisFlammability")) {
			return ATTRIBUTE_HMIS_FLAMMABILITY;
		}
		else if(attributeName.equals("hmisReactivity")) {
			return ATTRIBUTE_HMIS_REACTIVITY;
		}
		else if(attributeName.equals("hmisHealth")) {
			return ATTRIBUTE_HMIS_HEALTH;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CatPartHazardViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CatPartHazardViewBean catPartHazardViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + catPartHazardViewBean.getCompanyId());

		Connection connection = null;
		int result = 0;
		try {
			connection = this.getDbManager().getConnection();
			result = this.delete(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int delete(CatPartHazardViewBean catPartHazardViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + catPartHazardViewBean.getCompanyId());

		return delete(criteria, conn);
	}
*/

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


//you need to verify the primary key(s) before uncommenting this
/*
	//insert
	public int insert(CatPartHazardViewBean catPartHazardViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(catPartHazardViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CatPartHazardViewBean catPartHazardViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_PART_ID + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_MATERIAL_DESC + "," +
			ATTRIBUTE_HEALTH + "," +
			ATTRIBUTE_FLAMMABILITY + "," +
			ATTRIBUTE_REACTIVITY + "," +
			ATTRIBUTE_SPECIFIC_HAZARD + "," +
			ATTRIBUTE_TARGET_ORGAN + "," +
			ATTRIBUTE_SIGNAL_WORD + "," +
			ATTRIBUTE_HMIS_FLAMMABILITY + "," +
			ATTRIBUTE_HMIS_REACTIVITY + "," +
			ATTRIBUTE_HMIS_HEALTH + ")" +
			" values (" +
			SqlHandler.delimitString(catPartHazardViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getCatPartNo()) + "," +
			catPartHazardViewBean.getPartGroupNo() + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getPartShortName()) + "," +
			catPartHazardViewBean.getItemId() + "," +
			catPartHazardViewBean.getPartId() + "," +
			catPartHazardViewBean.getMaterialId() + "," +
			DateHandler.getOracleToDateFunction(catPartHazardViewBean.getRevisionDate()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getMaterialDesc()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getHealth()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getFlammability()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getReactivity()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getSpecificHazard()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getTargetOrgan()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getSignalWord()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getHmisFlammability()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getHmisReactivity()) + "," +
			SqlHandler.delimitString(catPartHazardViewBean.getHmisHealth()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CatPartHazardViewBean catPartHazardViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(catPartHazardViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CatPartHazardViewBean catPartHazardViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getCompanyId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" +
				StringHandler.nullIfZero(catPartHazardViewBean.getPartGroupNo()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getPartShortName()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(catPartHazardViewBean.getItemId()) + "," +
			ATTRIBUTE_PART_ID + "=" +
				StringHandler.nullIfZero(catPartHazardViewBean.getPartId()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" +
				StringHandler.nullIfZero(catPartHazardViewBean.getMaterialId()) + "," +
			ATTRIBUTE_REVISION_DATE + "=" +
				DateHandler.getOracleToDateFunction(catPartHazardViewBean.getRevisionDate()) + "," +
			ATTRIBUTE_MATERIAL_DESC + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getMaterialDesc()) + "," +
			ATTRIBUTE_HEALTH + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getHealth()) + "," +
			ATTRIBUTE_FLAMMABILITY + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getFlammability()) + "," +
			ATTRIBUTE_REACTIVITY + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getReactivity()) + "," +
			ATTRIBUTE_SPECIFIC_HAZARD + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getSpecificHazard()) + "," +
			ATTRIBUTE_TARGET_ORGAN + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getTargetOrgan()) + "," +
			ATTRIBUTE_SIGNAL_WORD + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getSignalWord()) + "," +
			ATTRIBUTE_HMIS_FLAMMABILITY + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getHmisFlammability()) + "," +
			ATTRIBUTE_HMIS_REACTIVITY + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getHmisReactivity()) + "," +
			ATTRIBUTE_HMIS_HEALTH + "=" +
				SqlHandler.delimitString(catPartHazardViewBean.getHmisHealth()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				catPartHazardViewBean.getCompanyId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection catPartHazardViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CatPartHazardViewBean catPartHazardViewBean = new CatPartHazardViewBean();
			load(dataSetRow, catPartHazardViewBean);
			catPartHazardViewBeanColl.add(catPartHazardViewBean);
		}

		return catPartHazardViewBeanColl;
	}
}