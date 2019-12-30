package com.tcmis.internal.supply.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;
import com.tcmis.internal.supply.beans.PoLineExpediteDateBean;


/******************************************************************************
 * CLASSNAME: PoLineExpediteDateBeanFactory <br>
 * @version: 1.0, Aug 13, 2008 <br>
 *****************************************************************************/


public class PoLineExpediteDateBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_DATE_ENTERED = "DATE_ENTERED";
	public String ATTRIBUTE_REVISED_PROMISED_DATE = "REVISED_PROMISED_DATE";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_ENTERED_BY  = "ENTERED_BY ";

	//table name
	public String TABLE = "PO_LINE_EXPEDITE_DATE";


	//constructor
	public PoLineExpediteDateBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("dateEntered")) {
			return ATTRIBUTE_DATE_ENTERED;
		}
		else if(attributeName.equals("revisedPromisedDate")) {
			return ATTRIBUTE_REVISED_PROMISED_DATE;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("enteredBy")) {
			return ATTRIBUTE_ENTERED_BY;
		}	 
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoLineExpediteDateBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoLineExpediteDateBean poLineExpediteDateBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poLineExpediteDateBean.getRadianPo());

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


	public int delete(PoLineExpediteDateBean poLineExpediteDateBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poLineExpediteDateBean.getRadianPo());

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
	//insert
	public int insert(PoLineExpediteDateBean poLineExpediteDateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poLineExpediteDateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoLineExpediteDateBean poLineExpediteDateBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_DATE_ENTERED + "," +
			ATTRIBUTE_REVISED_PROMISED_DATE + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_ENTERED_BY+ ")" +
			" values (" +
			poLineExpediteDateBean.getRadianPo() + "," +
			poLineExpediteDateBean.getPoLine() + "," +
			"sysdate," +
			DateHandler.getOracleToDateFunction(poLineExpediteDateBean.getRevisedPromisedDate()) + "," +
			SqlHandler.delimitString(poLineExpediteDateBean.getComments()) 	+ "," +
			poLineExpediteDateBean.getEnteredBy()	+ ")";

		return sqlManager.update(conn, query);
	}

  //insert
	public int insert(PoLineDetailViewBean poLineDetailViewBean, BigDecimal personnelId)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poLineDetailViewBean,personnelId, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoLineDetailViewBean poLineDetailViewBean,BigDecimal personnelId, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_DATE_ENTERED + "," +
			ATTRIBUTE_REVISED_PROMISED_DATE + "," +
			ATTRIBUTE_COMMENTS + "," +
            ATTRIBUTE_ENTERED_BY+ ")" +
            " values (" +
			poLineDetailViewBean.getRadianPo() + "," +
			poLineDetailViewBean.getPoLine() + "," +
			"sysdate," +
			DateHandler.getOracleToDateFunction(poLineDetailViewBean.getVendorShipDate()) + "," +
			SqlHandler.delimitString(poLineDetailViewBean.getExpediteComments()) + ","+
            personnelId.toString()+ ")";
        return sqlManager.update(conn, query);
	}
/*

	//update
	public int update(PoLineExpediteDateBean poLineExpediteDateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poLineExpediteDateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoLineExpediteDateBean poLineExpediteDateBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poLineExpediteDateBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(poLineExpediteDateBean.getPoLine()) + "," +
			ATTRIBUTE_DATE_ENTERED + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteDateBean.getDateEntered()) + "," +
			ATTRIBUTE_REVISED_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteDateBean.getRevisedPromisedDate()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(poLineExpediteDateBean.getComments()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poLineExpediteDateBean.getRadianPo();

		return new SqlManager().update(conn, query);
	}
*/

	//select
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

		Collection poLineExpediteDateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoLineExpediteDateBean poLineExpediteDateBean = new PoLineExpediteDateBean();
			load(dataSetRow, poLineExpediteDateBean);
			poLineExpediteDateBeanColl.add(poLineExpediteDateBean);
		}

		return poLineExpediteDateBeanColl;
	}
}