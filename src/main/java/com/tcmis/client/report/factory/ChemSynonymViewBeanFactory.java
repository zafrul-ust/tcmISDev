package com.tcmis.client.report.factory;


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
import com.tcmis.client.report.beans.ChemSynonymViewBean;


/******************************************************************************
 * CLASSNAME: ChemSynonymViewBeanFactory <br>
 * @version: 1.0, Oct 10, 2005 <br>
 *****************************************************************************/


public class ChemSynonymViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PREFERRED_NAME = "PREFERRED_NAME";
	public String ATTRIBUTE_ALTERNATE_NAME = "ALTERNATE_NAME";
	public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";

	//table name
	public String TABLE = "CHEM_SYNONYM_VIEW";


	//constructor
	public ChemSynonymViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("preferredName")) {
			return ATTRIBUTE_PREFERRED_NAME;
		}
		else if(attributeName.equals("alternateName")) {
			return ATTRIBUTE_ALTERNATE_NAME;
		}
		else if(attributeName.equals("casNumber")) {
			return ATTRIBUTE_CAS_NUMBER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ChemSynonymViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ChemSynonymViewBean chemSynonymViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("preferredName", "SearchCriterion.EQUALS",
			"" + chemSynonymViewBean.getPreferredName());

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


	public int delete(ChemSynonymViewBean chemSynonymViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("preferredName", "SearchCriterion.EQUALS",
			"" + chemSynonymViewBean.getPreferredName());

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
	public int insert(ChemSynonymViewBean chemSynonymViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(chemSynonymViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ChemSynonymViewBean chemSynonymViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PREFERRED_NAME + "," +
			ATTRIBUTE_ALTERNATE_NAME + "," +
			ATTRIBUTE_CAS_NUMBER + ")" +
			" values (" +
			SqlHandler.delimitString(chemSynonymViewBean.getPreferredName()) + "," +
			SqlHandler.delimitString(chemSynonymViewBean.getAlternateName()) + "," +
			SqlHandler.delimitString(chemSynonymViewBean.getCasNumber()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ChemSynonymViewBean chemSynonymViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(chemSynonymViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ChemSynonymViewBean chemSynonymViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PREFERRED_NAME + "=" +
				SqlHandler.delimitString(chemSynonymViewBean.getPreferredName()) + "," +
			ATTRIBUTE_ALTERNATE_NAME + "=" +
				SqlHandler.delimitString(chemSynonymViewBean.getAlternateName()) + "," +
			ATTRIBUTE_CAS_NUMBER + "=" +
				SqlHandler.delimitString(chemSynonymViewBean.getCasNumber()) + " " +
			"where " + ATTRIBUTE_PREFERRED_NAME + "=" +
				chemSynonymViewBean.getPreferredName();

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

		Collection chemSynonymViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ChemSynonymViewBean chemSynonymViewBean = new ChemSynonymViewBean();
			load(dataSetRow, chemSynonymViewBean);
			chemSynonymViewBeanColl.add(chemSynonymViewBean);
		}

		return chemSynonymViewBeanColl;
	}

        //select
        public Collection selectSortByName(SearchCriteria criteria)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = selectSortByName(criteria, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection selectSortByName(SearchCriteria criteria, Connection conn)
                throws BaseException {

                Collection chemSynonymViewBeanColl = new Vector();

                String query = "select distinct cas_number, preferred_name from " + TABLE + " " +
                        getWhereClause(criteria)+" order by preferred_name";

                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        ChemSynonymViewBean chemSynonymViewBean = new ChemSynonymViewBean();
                        load(dataSetRow, chemSynonymViewBean);
                        chemSynonymViewBeanColl.add(chemSynonymViewBean);
                }

                return chemSynonymViewBeanColl;
        }


}