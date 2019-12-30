package com.tcmis.client.catalog.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ClientInventoryCommentsViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: ChemSynonymViewBeanFactory <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/

public class ClientInventoryCommentsViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

	//table name
	public String TABLE = "CLIENT_INVENTORY_COMMENTS";


	//constructor
	public ClientInventoryCommentsViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
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
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ClientInventoryCommentsViewBean.class);
	}




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

	//insert
	public int insert(ClientInventoryCommentsViewBean clientCommentsBean)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(clientCommentsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ClientInventoryCommentsViewBean clientCommentsBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			//ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_COMMENTS + ")" +
			" values (" +
			SqlHandler.delimitString(clientCommentsBean.getCatalogCompanyId()) + "," +
			//SqlHandler.delimitString(clientCommentsBean.getCompanyId()) + "," +
			SqlHandler.delimitString(clientCommentsBean.getCatalogId()) + "," +
			SqlHandler.delimitString(clientCommentsBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(clientCommentsBean.getPartGroupNo().toString()) + "," +
			SqlHandler.delimitString(clientCommentsBean.getFacilityId()) + "," +
			SqlHandler.delimitString(clientCommentsBean.getComments()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ClientInventoryCommentsViewBean clientCommentsBean, SearchCriteria criteria)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(clientCommentsBean, connection, criteria);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ClientInventoryCommentsViewBean clientCommentsBean, Connection conn,SearchCriteria criteria)
	throws BaseException {

		String query  = "update " + TABLE + " set " +
		ATTRIBUTE_COMMENTS + "=" +
		SqlHandler.delimitString(clientCommentsBean.getComments())+
		" "+ getWhereClause(criteria);
		return new SqlManager().update(conn, query);
	}
	/**/
	@SuppressWarnings("unchecked")
	public Collection select( String searchArgument )	throws BaseException
	{
		Connection connection = null;
		Collection c = null;
		try
		{
			connection = this.getDbManager().getConnection();
			c = select( searchArgument, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	public Collection select(String searchArgument, Connection conn)	throws BaseException
	{
		Collection clientCommentViewBeanColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder(" select * from "); // + TABLE + " " +
		queryBuffer.append(TABLE);
		queryBuffer.append(searchArgument);
		queryBuffer.append("%') )  ");

		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ClientInventoryCommentsViewBean clientCommentsViewBean = new ClientInventoryCommentsViewBean();
			load(dataSetRow, clientCommentsViewBean);
			clientCommentViewBeanColl.add(clientCommentsViewBean);
			//   log.debug("chemSynonymViewBean.toString() = [" + chemSynonymViewBean.toString() + "]; ");
		}
		return clientCommentViewBeanColl;
	}


	//select
	@SuppressWarnings("unchecked")
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

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

	@SuppressWarnings("unchecked")
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection clientCommentViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ClientInventoryCommentsViewBean clientcommentsViewBean = new ClientInventoryCommentsViewBean();
			load(dataSetRow, clientcommentsViewBean);
			clientCommentViewBeanColl.add(clientcommentsViewBean);
		}

		return clientCommentViewBeanColl;
	}
}