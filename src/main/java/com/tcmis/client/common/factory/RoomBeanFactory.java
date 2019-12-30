package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.RoomBean;
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
 * CLASSNAME: RoomBeanFactory <br>
 * @version: 1.0, Jan 11, 2011 <br>
 *****************************************************************************/

public class RoomBeanFactory extends BaseBeanFactory {

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_FLOOR_ID = "FLOOR_ID";
	public String ATTRIBUTE_INTERIOR = "INTERIOR";
	public String ATTRIBUTE_MAP = "MAP";
	public String ATTRIBUTE_MAP_GRID = "MAP_GRID";
	public String ATTRIBUTE_ROOM_DESCRIPTION = "ROOM_DESCRIPTION";
	public String ATTRIBUTE_ROOM_ID = "ROOM_ID";
	public String ATTRIBUTE_ROOM_NAME = "ROOM_NAME";
	public String ATTRIBUTE_SPRINKLER = "SPRINKLER";
	public String ATTRIBUTE_UNIDOCS_CONTROL_AREA = "UNIDOCS_CONTROL_AREA";
	Log log = LogFactory.getLog(getClass());

	//table name
	public String TABLE = "ROOM";

	//constructor
	public RoomBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//delete
	public int delete(RoomBean roomBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(roomBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(RoomBean roomBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(roomBean), conn);
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

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("interior")) {
			return ATTRIBUTE_INTERIOR;
		}
		else if (attributeName.equals("floorId")) {
			return ATTRIBUTE_FLOOR_ID;
		}
		else if (attributeName.equals("roomId")) {
			return ATTRIBUTE_ROOM_ID;
		}
		else if (attributeName.equals("map")) {
			return ATTRIBUTE_MAP;
		}
		else if (attributeName.equals("mapGrid")) {
			return ATTRIBUTE_MAP_GRID;
		}
		else if (attributeName.equals("unidocsControlArea")) {
			return ATTRIBUTE_UNIDOCS_CONTROL_AREA;
		}
		else if (attributeName.equals("sprinkler")) {
			return ATTRIBUTE_SPRINKLER;
		}
		else if (attributeName.equals("roomDescription")) {
			return ATTRIBUTE_ROOM_DESCRIPTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	public SearchCriteria getKeyCriteria(RoomBean roomBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + roomBean.getCompanyId());
		criteria.addCriterion("floorId", SearchCriterion.EQUALS, "" + roomBean.getFloorId());
		criteria.addCriterion("roomId", SearchCriterion.EQUALS, "" + roomBean.getRoomId());
		return criteria;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, RoomBean.class);
	}

	//insert
	public int insert(RoomBean roomBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(roomBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(RoomBean roomBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_FLOOR_ID + ",";
		query += ATTRIBUTE_ROOM_ID + ",";
		query += ATTRIBUTE_ROOM_NAME + ",";
		query += ATTRIBUTE_MAP + ",";
		query += ATTRIBUTE_MAP_GRID + ",";
		query += ATTRIBUTE_UNIDOCS_CONTROL_AREA + ",";
		query += ATTRIBUTE_SPRINKLER + ",";
		query += ATTRIBUTE_INTERIOR + ",";
		query += ATTRIBUTE_ROOM_DESCRIPTION + ")";
		query += " values (";
		query += SqlHandler.delimitString(roomBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(roomBean.getFloorId()) + ",";
		query += SqlHandler.delimitString(roomBean.getRoomId()) + ",";
		query += SqlHandler.delimitString(roomBean.getRoomName()) + ",";
		query += SqlHandler.delimitString(roomBean.getMap()) + ",";
		query += SqlHandler.delimitString(roomBean.getMapGrid()) + ",";
		query += roomBean.getUnidocsControlArea() + ",";
		query += SqlHandler.delimitString(roomBean.getSprinkler()) + ",";
		query += roomBean.isInterior() ? "'Y'," : "'N',";
		query += SqlHandler.delimitString(roomBean.getRoomDescription());
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<RoomBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<RoomBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<RoomBean> roomBeanColl = new Vector<RoomBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			RoomBean roomBean = new RoomBean();
			load(dataSetRow, roomBean);
			roomBeanColl.add(roomBean);
		}

		return roomBeanColl;
	}

	public RoomBean selectBean(String companyId, String facilityId, String floorId, String roomId) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + companyId);
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + facilityId);
		criteria.addCriterion("floorId", SearchCriterion.EQUALS, "" + floorId);
		criteria.addCriterion("roomId", SearchCriterion.EQUALS, "" + roomId);

		Collection<RoomBean> results = select(criteria, null);
		return results.isEmpty() ? null : results.iterator().next();
	}

	//update
	public int update(RoomBean roomBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(roomBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(RoomBean roomBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_MAP + "=" + SqlHandler.delimitString(roomBean.getMap()) + ",";
		query += ATTRIBUTE_MAP_GRID + "=" + SqlHandler.delimitString(roomBean.getMapGrid()) + ",";
		query += ATTRIBUTE_UNIDOCS_CONTROL_AREA + "=" + StringHandler.nullIfZero(roomBean.getUnidocsControlArea()) + ",";
		query += ATTRIBUTE_SPRINKLER + "=" + SqlHandler.delimitString(roomBean.getSprinkler()) + ",";
		query += ATTRIBUTE_INTERIOR + "=" + (roomBean.isInterior() ? "'Y'," : "'N',");
		query += ATTRIBUTE_ROOM_DESCRIPTION + "=" + SqlHandler.delimitString(roomBean.getRoomDescription()) + " ";
		query += getWhereClause(getKeyCriteria(roomBean));

		return new SqlManager().update(conn, query);
	}

	public int updateMinimum(RoomBean roomBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return updateMinimum(roomBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int updateMinimum(RoomBean roomBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_ROOM_NAME + "=" + SqlHandler.delimitString(roomBean.getRoomName()) + ",";
		query += ATTRIBUTE_INTERIOR + "=" + (roomBean.isInterior() ? "'Y'," : "'N',");
		query += ATTRIBUTE_ROOM_DESCRIPTION + "=" + SqlHandler.delimitString(roomBean.getRoomDescription()) + " ";
		query += getWhereClause(getKeyCriteria(roomBean));

		return new SqlManager().update(conn, query);
	}
}