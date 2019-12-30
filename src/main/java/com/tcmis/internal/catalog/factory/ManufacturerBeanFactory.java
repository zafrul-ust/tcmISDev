package com.tcmis.internal.catalog.factory;


import java.math.BigDecimal;
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
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.ManufacturerBean;

/******************************************************************************
 * CLASSNAME: ManufacturerBeanFactory <br>
 * @version: 1.0, Jan 15, 2008 <br>
 *****************************************************************************/

public class ManufacturerBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_MFG_ID = "MFG_ID";
	public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
	public String ATTRIBUTE_MFG_URL = "MFG_URL";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_CONTACT = "CONTACT";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_MFG_SHORT_NAME = "MFG_SHORT_NAME";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";

	//table name
	public String TABLE = "MANUFACTURER";


	//constructor
	public ManufacturerBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("mfgId")) {
			return ATTRIBUTE_MFG_ID;
		}
		else if(attributeName.equals("mfgDesc")) {
			return ATTRIBUTE_MFG_DESC;
		}
		else if(attributeName.equals("mfgUrl")) {
			return ATTRIBUTE_MFG_URL;
		}
		else if(attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
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
		else if(attributeName.equals("mfgShortName")) {
			return ATTRIBUTE_MFG_SHORT_NAME;
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
		return super.getType(attributeName, ManufacturerBean.class);
	}

	public BigDecimal getNextMfgId() throws BaseException
	{
		Connection connection = null;
		int result = 0;
		BigDecimal mfgId;
		try
		{
			connection = getDbManager().getConnection();
			mfgId = getNextMfgId( connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return mfgId;
	}

	public BigDecimal getNextMfgId( Connection conn)	throws BaseException
	{
		String query = "select global.mfg_seq.nextval from dual ";

		DataSet dataSet = new SqlManager().select(conn, query );

		Iterator dataIter = dataSet.iterator();
		BigDecimal nextMfgId = new BigDecimal( 0 );
		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			if (log.isDebugEnabled()) {
				log.debug("dataSetRow.toString() = [" + dataSetRow.toString() + "]; ");
			}
			nextMfgId = new BigDecimal( dataSetRow.getInt("NEXTVAL") );
			if (log.isDebugEnabled()) {
				log.debug("nextMfgId.toString() = [" + nextMfgId.toString() + "]; ");
			}
		}
		return nextMfgId;
	}

	public int insert(ManufacturerBean manufacturerBean)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(manufacturerBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(ManufacturerBean manufacturerBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into global." + TABLE + " (" +
			ATTRIBUTE_MFG_ID + "," +
			ATTRIBUTE_MFG_DESC + "," +
			ATTRIBUTE_MFG_URL + "," +
			ATTRIBUTE_PHONE + "," +
			ATTRIBUTE_CONTACT + "," +
			ATTRIBUTE_EMAIL + "," +
			ATTRIBUTE_NOTES + "," +
			ATTRIBUTE_MFG_SHORT_NAME + "," +
			ATTRIBUTE_COUNTRY_ABBREV +
			") values (" +
			manufacturerBean.getMfgId() + "," +
			SqlHandler.delimitString(manufacturerBean.getMfgDesc()) + "," +
			SqlHandler.delimitString(manufacturerBean.getMfgUrl()) + "," +
			SqlHandler.delimitString(manufacturerBean.getPhone()) + "," +
			SqlHandler.delimitString(manufacturerBean.getContact()) + "," +
			SqlHandler.delimitString(manufacturerBean.getEmail()) + "," +
			SqlHandler.delimitString(manufacturerBean.getNotes()) + "," +
			SqlHandler.delimitString(manufacturerBean.getMfgShortName()) + "," +
			SqlHandler.delimitString(manufacturerBean.getCountryAbbrev()) +
			")";

		return sqlManager.update(conn, query);
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
		Collection manufacturerBeanColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder("select * from manufacturer ");
		queryBuffer.append(" where lower( MFG_DESC || MFG_ID ) like lower('%");
		queryBuffer.append(SqlHandler.validQuery(searchArgument));
		queryBuffer.append("%') order by MFG_ID, MFG_DESC asc ");

		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ManufacturerBean manufacturerBean = new ManufacturerBean();
			load(dataSetRow, manufacturerBean);
			manufacturerBeanColl.add(manufacturerBean);
		}
		return manufacturerBeanColl;
	}

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection manufacturerBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ManufacturerBean manufacturerBean = new ManufacturerBean();
			load(dataSetRow, manufacturerBean);
			manufacturerBeanColl.add(manufacturerBean);
		}

		return manufacturerBeanColl;
	}

	@SuppressWarnings("unchecked")
	public ManufacturerBean selectManufacturer(String mfgId) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			SearchCriteria criteria = new SearchCriteria("mfgId", SearchCriterion.EQUALS, mfgId);
			Collection<ManufacturerBean> results = select(criteria,  new SortCriteria());
			return results.isEmpty() ? null : results.iterator().next();
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


}