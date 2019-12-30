package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.FacLocDeliveryPointBean;
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
 * CLASSNAME: FacLocDeliveryPointBeanFactory <br>
 * @version: 1.0, Jan 10, 2011 <br>
 *****************************************************************************/

public class FacLocDeliveryPointBeanFactory extends BaseBeanFactory {

	public String ATTRIBUTE_BUILDING_ID = "BUILDING_ID";
	public String ATTRIBUTE_BUILDING_DESC = "BUILDING_DESC";
	public String ATTRIBUTE_BUILDING_NAME = "BUILDING_NAME";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
	public String ATTRIBUTE_DELIVERY_POINT_LONG_DESC = "DELIVERY_POINT_LONG_DESC";
	public String ATTRIBUTE_DELIVERY_CONTACT = "DELIVERY_CONTACT";
	public String ATTRIBUTE_DELIVERY_CONTACT_PHONE = "DELIVERY_CONTACT_PHONE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_INVENTORY_GROUP_OVERRIDE = "INVENTORY_GROUP_OVERRIDE";
	public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
	public String ATTRIBUTE_ROOM_ID = "ROOM_ID";
	public String ATTRIBUTE_ROOM_DESC = "ROOM_DESC";
	public String ATTRIBUTE_ROOM_NAME = "ROOM_NAME";
	public String ATTRIBUTE_SCRAP_OBSOLETE = "SCRAP_OBSOLETE";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_AREA_DESC = "AREA_DESC";
	public String ATTRIBUTE_AREA_ID = "AREA_ID";
	public String ATTRIBUTE_AREA_NAME = "AREA_NAME";


	//table name
	public String TABLE = "FAC_LOC_DELIVERY_POINT";
	private boolean creatingWhereClause = false;

	//constructor
	public FacLocDeliveryPointBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//delete
	public int delete(FacLocDeliveryPointBean facLocDeliveryPointBean) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(facLocDeliveryPointBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(FacLocDeliveryPointBean facLocDeliveryPointBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(facLocDeliveryPointBean), conn);
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

	@Override
	public String getColumnName(String attributeName) {
		return creatingWhereClause ? getActualColumnName(attributeName) : getActualColumnName(attributeName);
	}

	//get column names
	public String getActualColumnName(String attributeName) {
		if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("locationId")) {
			return ATTRIBUTE_LOCATION_ID;
		}
		else if (attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if (attributeName.equals("deliveryContact")) {
			return ATTRIBUTE_DELIVERY_CONTACT;
		}
		else if (attributeName.equals("deliveryContactPhone")) {
			return ATTRIBUTE_DELIVERY_CONTACT_PHONE;
		}
		else if (attributeName.equals("deliveryPointDesc")) {
			return ATTRIBUTE_DELIVERY_POINT_DESC;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("scrapObsolete")) {
			return ATTRIBUTE_SCRAP_OBSOLETE;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if (attributeName.equals("inventoryGroupOverride")) {
			return ATTRIBUTE_INVENTORY_GROUP_OVERRIDE;
		}
		else if (attributeName.equals("deliveryPointLongDesc")) {
			return ATTRIBUTE_DELIVERY_POINT_LONG_DESC;
		}
		else if (attributeName.equals("buildingId")) {
			return ATTRIBUTE_BUILDING_ID;
		}
		else if (attributeName.equals("buildingName")) {
			return ATTRIBUTE_BUILDING_NAME;
		}
		else if (attributeName.equals("roomId")) {
			return ATTRIBUTE_ROOM_ID;
		}
		else if (attributeName.equals("roomName")) {
			return ATTRIBUTE_ROOM_NAME;
		}
		else if (attributeName.equals("buildingDesc")) {
			return ATTRIBUTE_BUILDING_DESC;
		}
		else if (attributeName.equals("roomDesc")) {
			return ATTRIBUTE_ROOM_DESC;
		}
		else if (attributeName.equals("areaDesc")) {
			return ATTRIBUTE_AREA_DESC;
		}
		else if (attributeName.equals("areaId")) {
			return ATTRIBUTE_AREA_ID;
		}
		else if (attributeName.equals("areaName")) {
			return ATTRIBUTE_AREA_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	public SearchCriteria getKeyCriteria(FacLocDeliveryPointBean facLocDeliveryPointBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + facLocDeliveryPointBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + facLocDeliveryPointBean.getFacilityId());
		criteria.addCriterion("locationId", SearchCriterion.EQUALS, "" + facLocDeliveryPointBean.getLocationId());
		criteria.addCriterion("deliveryPoint", SearchCriterion.EQUALS, "" + facLocDeliveryPointBean.getDeliveryPoint());
		return criteria;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, FacLocDeliveryPointBean.class);
	}

	@Override
	public String getWhereClause(SearchCriteria criteria) {
		try {
			creatingWhereClause = true;
			return super.getWhereClause(criteria);
		}
		finally {
			creatingWhereClause = false;
		}
	}

	//insert
	public int insert(FacLocDeliveryPointBean facLocDeliveryPointBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(facLocDeliveryPointBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(FacLocDeliveryPointBean facLocDeliveryPointBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_LOCATION_ID + ",";
		query += ATTRIBUTE_DELIVERY_POINT + ",";
		query += ATTRIBUTE_DELIVERY_CONTACT + ",";
		query += ATTRIBUTE_DELIVERY_CONTACT_PHONE + ",";
		query += ATTRIBUTE_DELIVERY_POINT_DESC + ",";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_SCRAP_OBSOLETE + ",";
		query += ATTRIBUTE_STATUS + ",";
		query += ATTRIBUTE_INVENTORY_GROUP_OVERRIDE + ",";
		query += ATTRIBUTE_DELIVERY_POINT_LONG_DESC + ",";
		query += ATTRIBUTE_ROOM_ID + ")";
		query += " values (";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getFacilityId()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getLocationId()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryPoint()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryContact()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryContactPhone()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryPointDesc()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getScrapObsolete()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getStatus()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getInventoryGroupOverride()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryPointLongDesc()) + ",";
		query += SqlHandler.delimitString(facLocDeliveryPointBean.getRoomId());
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<FacLocDeliveryPointBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<FacLocDeliveryPointBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<FacLocDeliveryPointBean> facLocDeliveryPointBeanColl = new Vector();
		
		String query = "select * from FLDP_AREA_BLDG_RM_VIEW ";
		query += getWhereClause(criteria);
		query += getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			FacLocDeliveryPointBean facLocDeliveryPointBean = new FacLocDeliveryPointBean();
			load(dataSetRow, facLocDeliveryPointBean);
			facLocDeliveryPointBeanColl.add(facLocDeliveryPointBean);
		}

		return facLocDeliveryPointBeanColl;
	}

	//update
	public int update(FacLocDeliveryPointBean facLocDeliveryPointBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(facLocDeliveryPointBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(FacLocDeliveryPointBean facLocDeliveryPointBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_DELIVERY_POINT_DESC + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryPointDesc()) + ",";
		query += ATTRIBUTE_DELIVERY_CONTACT + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryContact()) + ",";
		query += ATTRIBUTE_DELIVERY_CONTACT_PHONE + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryContactPhone()) + ",";
		query += ATTRIBUTE_SCRAP_OBSOLETE + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getScrapObsolete()) + ",";
		query += ATTRIBUTE_STATUS + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getStatus()) + ",";
		query += ATTRIBUTE_INVENTORY_GROUP_OVERRIDE + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getInventoryGroupOverride()) + ",";
		query += ATTRIBUTE_DELIVERY_POINT_LONG_DESC + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getDeliveryPointLongDesc()) + ",";
		query += ATTRIBUTE_ROOM_ID + "=" + SqlHandler.delimitString(facLocDeliveryPointBean.getRoomId()) + " ";
		query += getWhereClause(getKeyCriteria(facLocDeliveryPointBean));

		return new SqlManager().update(conn, query);
	}
}