package com.tcmis.client.waste.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.waste.beans.TsdfReportViewBean;


/******************************************************************************
 * CLASSNAME: TsdfReportViewBeanFactory <br>
 * @version: 1.0, Feb 23, 2007 <br>
 *****************************************************************************/


public class TsdfReportViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_TSDF_VENDOR_ID = "TSDF_VENDOR_ID";
	public String ATTRIBUTE_TSDF_VENDOR_DESC = "TSDF_VENDOR_DESC";
	public String ATTRIBUTE_TSDF_MANIFEST = "TSDF_MANIFEST";
	public String ATTRIBUTE_TSDF_SHIP_DATE = "TSDF_SHIP_DATE";
	public String ATTRIBUTE_TSDF_VENDOR_PROFILE_ID = "TSDF_VENDOR_PROFILE_ID";
	public String ATTRIBUTE_TSDF_PACKAGING = "TSDF_PACKAGING";
	public String ATTRIBUTE_TSDF_CONTAINER_ID = "TSDF_CONTAINER_ID";
	public String ATTRIBUTE_GEN_MANIFEST = "GEN_MANIFEST";
	public String ATTRIBUTE_GEN_SHIP_DATE = "GEN_SHIP_DATE";
	public String ATTRIBUTE_GEN_VENDOR_PROFILE_ID = "GEN_VENDOR_PROFILE_ID";
	public String ATTRIBUTE_GEN_PACKAGING = "GEN_PACKAGING";
	public String ATTRIBUTE_GEN_COMPANY_ID = "GEN_COMPANY_ID";
	public String ATTRIBUTE_GEN_FACILITY_ID = "GEN_FACILITY_ID";
	public String ATTRIBUTE_GENERATION_POINT = "GENERATION_POINT";
	public String ATTRIBUTE_GEN_WR_LINE_ITEM = "GEN_WR_LINE_ITEM";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_GEN_CONTAINER_ID = "GEN_CONTAINER_ID";
	public String ATTRIBUTE_TSDF_COMPANY_ID = "TSDF_COMPANY_ID";
	public String ATTRIBUTE_TSDF_WASTE_REQUEST_ID = "TSDF_WASTE_REQUEST_ID";
	public String ATTRIBUTE_TSDF_LINE_ITEM = "TSDF_LINE_ITEM";
	public String ATTRIBUTE_TSDF_FACILITY_ID = "TSDF_FACILITY_ID";
	public String ATTRIBUTE_TSDF_LOCATION_ID = "TSDF_LOCATION_ID";
	public String ATTRIBUTE_GEN_VENDOR_ID = "GEN_VENDOR_ID";
	public String ATTRIBUTE_CONTAINER_ANCESTRY = "CONTAINER_ANCESTRY";
        public String ATTRIBUTE_TAG_NUMBER = "TAG_NUMBER";
        public String ATTRIBUTE_GEN_PROFILE_URL = "GEN_PROFILE_URL";
        public String ATTRIBUTE_TSDF_PROFILE_URL = "TSDF_PROFILE_URL";
        public String ATTRIBUTE_LOCATION_GROUP = "LOCATION_GROUP";

	//table name
	public String TABLE = "TSDF_REPORT_VIEW";


	//constructor
	public TsdfReportViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("tsdfVendorId")) {
			return ATTRIBUTE_TSDF_VENDOR_ID;
		}
		else if(attributeName.equals("tsdfVendorDesc")) {
			return ATTRIBUTE_TSDF_VENDOR_DESC;
		}
		else if(attributeName.equals("tsdfManifest")) {
			return ATTRIBUTE_TSDF_MANIFEST;
		}
		else if(attributeName.equals("tsdfShipDate")) {
			return ATTRIBUTE_TSDF_SHIP_DATE;
		}
		else if(attributeName.equals("tsdfVendorProfileId")) {
			return ATTRIBUTE_TSDF_VENDOR_PROFILE_ID;
		}
		else if(attributeName.equals("tsdfPackaging")) {
			return ATTRIBUTE_TSDF_PACKAGING;
		}
		else if(attributeName.equals("tsdfContainerId")) {
			return ATTRIBUTE_TSDF_CONTAINER_ID;
		}
		else if(attributeName.equals("genManifest")) {
			return ATTRIBUTE_GEN_MANIFEST;
		}
		else if(attributeName.equals("genShipDate")) {
			return ATTRIBUTE_GEN_SHIP_DATE;
		}
		else if(attributeName.equals("genVendorProfileId")) {
			return ATTRIBUTE_GEN_VENDOR_PROFILE_ID;
		}
		else if(attributeName.equals("genPackaging")) {
			return ATTRIBUTE_GEN_PACKAGING;
		}
		else if(attributeName.equals("genCompanyId")) {
			return ATTRIBUTE_GEN_COMPANY_ID;
		}
		else if(attributeName.equals("genFacilityId")) {
			return ATTRIBUTE_GEN_FACILITY_ID;
		}
		else if(attributeName.equals("generationPoint")) {
			return ATTRIBUTE_GENERATION_POINT;
		}
		else if(attributeName.equals("genWrLineItem")) {
			return ATTRIBUTE_GEN_WR_LINE_ITEM;
		}
		else if(attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if(attributeName.equals("genContainerId")) {
			return ATTRIBUTE_GEN_CONTAINER_ID;
		}
		else if(attributeName.equals("tsdfCompanyId")) {
			return ATTRIBUTE_TSDF_COMPANY_ID;
		}
		else if(attributeName.equals("tsdfWasteRequestId")) {
			return ATTRIBUTE_TSDF_WASTE_REQUEST_ID;
		}
		else if(attributeName.equals("tsdfLineItem")) {
			return ATTRIBUTE_TSDF_LINE_ITEM;
		}
		else if(attributeName.equals("tsdfFacilityId")) {
			return ATTRIBUTE_TSDF_FACILITY_ID;
		}
		else if(attributeName.equals("tsdfLocationId")) {
			return ATTRIBUTE_TSDF_LOCATION_ID;
		}
		else if(attributeName.equals("genVendorId")) {
			return ATTRIBUTE_GEN_VENDOR_ID;
		}
		else if(attributeName.equals("containerAncestry")) {
			return ATTRIBUTE_CONTAINER_ANCESTRY;
		}
                else if(attributeName.equals("tagNumber")) {
                        return ATTRIBUTE_TAG_NUMBER;
                }
                else if(attributeName.equals("genProfileUrl")) {
                        return ATTRIBUTE_GEN_PROFILE_URL;
                }
                else if(attributeName.equals("tsdfProfileUrl")) {
                        return ATTRIBUTE_TSDF_PROFILE_URL;
                }
                else if(attributeName.equals("locationGroup")) {
                        return ATTRIBUTE_LOCATION_GROUP;
                }
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, TsdfReportViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(TsdfReportViewBean tsdfReportViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("tsdfVendorId", "SearchCriterion.EQUALS",
			"" + tsdfReportViewBean.getTsdfVendorId());

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


	public int delete(TsdfReportViewBean tsdfReportViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("tsdfVendorId", "SearchCriterion.EQUALS",
			"" + tsdfReportViewBean.getTsdfVendorId());

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
	public int insert(TsdfReportViewBean tsdfReportViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(tsdfReportViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(TsdfReportViewBean tsdfReportViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_TSDF_VENDOR_ID + "," +
			ATTRIBUTE_TSDF_VENDOR_DESC + "," +
			ATTRIBUTE_TSDF_MANIFEST + "," +
			ATTRIBUTE_TSDF_SHIP_DATE + "," +
			ATTRIBUTE_TSDF_VENDOR_PROFILE_ID + "," +
			ATTRIBUTE_TSDF_PACKAGING + "," +
			ATTRIBUTE_TSDF_CONTAINER_ID + "," +
			ATTRIBUTE_GEN_MANIFEST + "," +
			ATTRIBUTE_GEN_SHIP_DATE + "," +
			ATTRIBUTE_GEN_VENDOR_PROFILE_ID + "," +
			ATTRIBUTE_GEN_PACKAGING + "," +
			ATTRIBUTE_GEN_COMPANY_ID + "," +
			ATTRIBUTE_GEN_FACILITY_ID + "," +
			ATTRIBUTE_GENERATION_POINT + "," +
			ATTRIBUTE_GEN_WR_LINE_ITEM + "," +
			ATTRIBUTE_REQUESTOR + "," +
			ATTRIBUTE_GEN_CONTAINER_ID + "," +
			ATTRIBUTE_TSDF_COMPANY_ID + "," +
			ATTRIBUTE_TSDF_WASTE_REQUEST_ID + "," +
			ATTRIBUTE_TSDF_LINE_ITEM + "," +
			ATTRIBUTE_TSDF_FACILITY_ID + "," +
			ATTRIBUTE_TSDF_LOCATION_ID + "," +
			ATTRIBUTE_GEN_VENDOR_ID + "," +
			ATTRIBUTE_CONTAINER_ANCESTRY + ")" +
			" values (" +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfVendorId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfVendorDesc()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfManifest()) + "," +
			DateHandler.getOracleToDateFunction(tsdfReportViewBean.getTsdfShipDate()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfVendorProfileId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfPackaging()) + "," +
			tsdfReportViewBean.getTsdfContainerId() + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenManifest()) + "," +
			DateHandler.getOracleToDateFunction(tsdfReportViewBean.getGenShipDate()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenVendorProfileId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenPackaging()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenCompanyId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenFacilityId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenerationPoint()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenWrLineItem()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getRequestor()) + "," +
			tsdfReportViewBean.getGenContainerId() + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfCompanyId()) + "," +
			tsdfReportViewBean.getTsdfWasteRequestId() + "," +
			tsdfReportViewBean.getTsdfLineItem() + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfFacilityId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getTsdfLocationId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getGenVendorId()) + "," +
			SqlHandler.delimitString(tsdfReportViewBean.getContainerAncestry()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(TsdfReportViewBean tsdfReportViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(tsdfReportViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(TsdfReportViewBean tsdfReportViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TSDF_VENDOR_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfVendorId()) + "," +
			ATTRIBUTE_TSDF_VENDOR_DESC + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfVendorDesc()) + "," +
			ATTRIBUTE_TSDF_MANIFEST + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfManifest()) + "," +
			ATTRIBUTE_TSDF_SHIP_DATE + "=" +
				DateHandler.getOracleToDateFunction(tsdfReportViewBean.getTsdfShipDate()) + "," +
			ATTRIBUTE_TSDF_VENDOR_PROFILE_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfVendorProfileId()) + "," +
			ATTRIBUTE_TSDF_PACKAGING + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfPackaging()) + "," +
			ATTRIBUTE_TSDF_CONTAINER_ID + "=" +
				StringHandler.nullIfZero(tsdfReportViewBean.getTsdfContainerId()) + "," +
			ATTRIBUTE_GEN_MANIFEST + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenManifest()) + "," +
			ATTRIBUTE_GEN_SHIP_DATE + "=" +
				DateHandler.getOracleToDateFunction(tsdfReportViewBean.getGenShipDate()) + "," +
			ATTRIBUTE_GEN_VENDOR_PROFILE_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenVendorProfileId()) + "," +
			ATTRIBUTE_GEN_PACKAGING + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenPackaging()) + "," +
			ATTRIBUTE_GEN_COMPANY_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenCompanyId()) + "," +
			ATTRIBUTE_GEN_FACILITY_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenFacilityId()) + "," +
			ATTRIBUTE_GENERATION_POINT + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenerationPoint()) + "," +
			ATTRIBUTE_GEN_WR_LINE_ITEM + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenWrLineItem()) + "," +
			ATTRIBUTE_REQUESTOR + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getRequestor()) + "," +
			ATTRIBUTE_GEN_CONTAINER_ID + "=" +
				StringHandler.nullIfZero(tsdfReportViewBean.getGenContainerId()) + "," +
			ATTRIBUTE_TSDF_COMPANY_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfCompanyId()) + "," +
			ATTRIBUTE_TSDF_WASTE_REQUEST_ID + "=" +
				StringHandler.nullIfZero(tsdfReportViewBean.getTsdfWasteRequestId()) + "," +
			ATTRIBUTE_TSDF_LINE_ITEM + "=" +
				StringHandler.nullIfZero(tsdfReportViewBean.getTsdfLineItem()) + "," +
			ATTRIBUTE_TSDF_FACILITY_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfFacilityId()) + "," +
			ATTRIBUTE_TSDF_LOCATION_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getTsdfLocationId()) + "," +
			ATTRIBUTE_GEN_VENDOR_ID + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getGenVendorId()) + "," +
			ATTRIBUTE_CONTAINER_ANCESTRY + "=" +
				SqlHandler.delimitString(tsdfReportViewBean.getContainerAncestry()) + " " +
			"where " + ATTRIBUTE_TSDF_VENDOR_ID + "=" +
				tsdfReportViewBean.getTsdfVendorId();

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

		Collection tsdfReportViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);
         log.debug(query);
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			TsdfReportViewBean tsdfReportViewBean = new TsdfReportViewBean();
			load(dataSetRow, tsdfReportViewBean);
			tsdfReportViewBeanColl.add(tsdfReportViewBean);
		}

		return tsdfReportViewBeanColl;
	}
}