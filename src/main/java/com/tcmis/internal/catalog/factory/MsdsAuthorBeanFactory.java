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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.MsdsAuthorBean;
import com.tcmis.internal.catalog.beans.MsdsBean;

/******************************************************************************
 * CLASSNAME: MsdsAuthorBeanFactory <br>
 * @version: 1.0, Dec 10, 2010 <br>
 *****************************************************************************/

public class MsdsAuthorBeanFactory extends BaseBeanFactory {
	public String ATTRIBUTE_MSDS_AUTHOR_ID = "MSDS_AUTHOR_ID";
	public String ATTRIBUTE_MSDS_AUTHOR_DESC = "MSDS_AUTHOR_DESC";
	public String ATTRIBUTE_AUTHOR_URL = "AUTHOR_URL";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_CONTACT = "CONTACT";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";

    //table name
	public String TABLE = "GLOBAL.MSDS_AUTHOR";

	Log log = LogFactory.getLog(getClass());


	//constructor
	public MsdsAuthorBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	
	private SearchCriteria getKeyCriteria(MsdsAuthorBean msdsAuthorBean) {
		return getKeyCriteria(msdsAuthorBean.getMsdsAuthorId());
	}

	private SearchCriteria getKeyCriteria(BigDecimal msdsAuthorId) {
		SearchCriteria criteria = new SearchCriteria("msdsAuthorId", SearchCriterion.EQUALS, "" + msdsAuthorId);
		return criteria;
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("msdsAuthorId")) {
			return ATTRIBUTE_MSDS_AUTHOR_ID;
		}
		else if(attributeName.equals("msdsAuthorDesc")) {
			return ATTRIBUTE_MSDS_AUTHOR_DESC;
		}
		else if(attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if(attributeName.equals("authorUrl")) {
			return ATTRIBUTE_AUTHOR_URL;
		}
		else if(attributeName.equals("contact")) {
			return ATTRIBUTE_CONTACT;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}
	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, MsdsAuthorBean.class);
	}

	//insert
	public int insert(MsdsAuthorBean msdsAuthorBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(msdsAuthorBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(MsdsAuthorBean msdsAuthorBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
				ATTRIBUTE_MSDS_AUTHOR_ID + "," +
				ATTRIBUTE_MSDS_AUTHOR_DESC + "," +
				ATTRIBUTE_AUTHOR_URL + "," +
				ATTRIBUTE_PHONE + "," +
				ATTRIBUTE_CONTACT + "," +
				ATTRIBUTE_EMAIL + "," +
				ATTRIBUTE_NOTES + "," +
				ATTRIBUTE_COUNTRY_ABBREV +
			")" +
			" values (" +
				msdsAuthorBean.getMsdsAuthorId() + "," +
				SqlHandler.delimitString(msdsAuthorBean.getMsdsAuthorDesc()) + "," +
				SqlHandler.delimitString(msdsAuthorBean.getAuthorUrl()) + "," +
				SqlHandler.delimitString(msdsAuthorBean.getPhone()) + "," +
				SqlHandler.delimitString(msdsAuthorBean.getContact()) + "," +
				SqlHandler.delimitString(msdsAuthorBean.getEmail()) + "," +
				SqlHandler.delimitString(msdsAuthorBean.getNotes()) + "," +
				SqlHandler.delimitString(msdsAuthorBean.getCountryAbbrev())
            + ")";

		return sqlManager.update(conn, query);
	}

	//update
	public int update(MsdsAuthorBean msdsAuthorBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(msdsAuthorBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(MsdsAuthorBean msdsAuthorBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update ").append(TABLE).append(" set ");
		query.append(ATTRIBUTE_MSDS_AUTHOR_ID).append("=").append(msdsAuthorBean.getMsdsAuthorId()).append(",");
		query.append(ATTRIBUTE_MSDS_AUTHOR_DESC).append("=").append(SqlHandler.delimitString(msdsAuthorBean.getMsdsAuthorDesc())).append(",");
		query.append(ATTRIBUTE_AUTHOR_URL).append("=").append(SqlHandler.delimitString(msdsAuthorBean.getAuthorUrl())).append(",");
		query.append(ATTRIBUTE_PHONE).append("=").append(SqlHandler.delimitString(msdsAuthorBean.getPhone())).append(",");
		query.append(ATTRIBUTE_CONTACT).append("=").append(SqlHandler.delimitString(msdsAuthorBean.getContact())).append(",");
		query.append(ATTRIBUTE_EMAIL).append("=").append(SqlHandler.delimitString(msdsAuthorBean.getEmail())).append(",");
		query.append(ATTRIBUTE_NOTES).append("=").append(SqlHandler.delimitString(msdsAuthorBean.getNotes())).append(",");
		query.append(ATTRIBUTE_COUNTRY_ABBREV).append("=").append(SqlHandler.delimitString(msdsAuthorBean.getCountryAbbrev()));
        query.append(" ").append(getWhereClause(getKeyCriteria(msdsAuthorBean)));

		return new SqlManager().update(conn, query.toString());
	}

	public Collection<MsdsAuthorBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		Collection<MsdsAuthorBean> c = null;
		try {
			connection = getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection<MsdsAuthorBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<MsdsAuthorBean> msdsAuthorBeanColl = new Vector<MsdsAuthorBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MsdsAuthorBean msdsAuthorBean = new MsdsAuthorBean();
			load(dataSetRow, msdsAuthorBean);
			msdsAuthorBeanColl.add(msdsAuthorBean);
		}

		return msdsAuthorBeanColl;
	}
	
	public MsdsAuthorBean select(BigDecimal msdsAuthorId) throws BaseException {
		MsdsAuthorBean msdsAuthorBean = null;
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			msdsAuthorBean = select(msdsAuthorId, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return msdsAuthorBean;
	}

	public MsdsAuthorBean select(BigDecimal msdsAuthorId, Connection conn) throws BaseException {
		Collection<MsdsAuthorBean> results = select(getKeyCriteria(msdsAuthorId), null, conn);
		return results.isEmpty() ? null : results.iterator().next();
	}
	
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

	public Collection select( String searchArgument, Connection conn)	throws BaseException
	{
		Collection msdsAuthorBeanColl = new Vector();

		String escapedSearchText = searchArgument.replace("'", "''");
		
		StringBuilder queryBuffer = new StringBuilder("select * from global.msds_author ");
		queryBuffer.append(" where lower( MSDS_AUTHOR_DESC || MSDS_AUTHOR_ID ) like lower('%");
		queryBuffer.append(escapedSearchText);
		queryBuffer.append("%') order by MSDS_AUTHOR_ID, MSDS_AUTHOR_DESC asc ");

		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MsdsAuthorBean msdsAuthorBean = new MsdsAuthorBean();
			load(dataSetRow, msdsAuthorBean);
			msdsAuthorBeanColl.add(msdsAuthorBean);
		}
		return msdsAuthorBeanColl;
	}
	
	public BigDecimal getNextMsdsAuthorId() throws BaseException
	{
		Connection connection = null;
		int result = 0;
		BigDecimal msdsAuthorId;
		try
		{
			connection = getDbManager().getConnection();
			msdsAuthorId = getNextMsdsAuthorId( connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return msdsAuthorId;
	}

	public BigDecimal getNextMsdsAuthorId( Connection conn)	throws BaseException
	{
		String query = "select global.mfg_seq.nextval from dual ";

		DataSet dataSet = new SqlManager().select(conn, query );

		Iterator dataIter = dataSet.iterator();
		BigDecimal msdsAuthorId = new BigDecimal( 0 );
		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			if (log.isDebugEnabled()) {
				log.debug("dataSetRow.toString() = [" + dataSetRow.toString() + "]; ");
			}
			msdsAuthorId = new BigDecimal( dataSetRow.getInt("NEXTVAL") );
			if (log.isDebugEnabled()) {
				log.debug("nextMfgId.toString() = [" + msdsAuthorId.toString() + "]; ");
			}
		}
		return msdsAuthorId;
	}
}