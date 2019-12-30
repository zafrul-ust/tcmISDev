package com.tcmis.client.dla.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.SQLException;

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
import com.tcmis.client.dla.beans.DlaVmiBean;


/******************************************************************************
 * CLASSNAME: DlaVmiBeanFactory <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class DlaVmiBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO"; 
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_TRANSACTION_REF_NUM = "TRANSACTION_REF_NUM";
	public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public String ATTRIBUTE_DOC_ID_CODE = "DOC_ID_CODE";
	public String ATTRIBUTE_TRANSACTION_REF_LINE_NUM = "TRANSACTION_REF_LINE_NUM";

	//table name
	public String TABLE = "DLA_VMI";


	//constructor
	public DlaVmiBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("transactionRefNum")) {
			return ATTRIBUTE_TRANSACTION_REF_NUM;
		}
		else if(attributeName.equals("transactionType")) {
			return ATTRIBUTE_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("docIdCode")) {
			return ATTRIBUTE_DOC_ID_CODE;
		}
		else if(attributeName.equals("transactionRefLineNum")) {
			return ATTRIBUTE_TRANSACTION_REF_LINE_NUM;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DlaVmiBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DlaVmiBean dlaVmiBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catPartNo", "SearchCriterion.EQUALS",
			"" + dlaVmiBean.getCatPartNo());

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


	public int delete(DlaVmiBean dlaVmiBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catPartNo", "SearchCriterion.EQUALS",
			"" + dlaVmiBean.getCatPartNo());

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
	public int insert(DlaVmiBean dlaVmiBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dlaVmiBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DlaVmiBean dlaVmiBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "," +
			ATTRIBUTE_DOC_ID_CODE + "," +
			ATTRIBUTE_TRANSACTION_REF_LINE_NUM + ")" +
 values (
			SqlHandler.delimitString(dlaVmiBean.getCatPartNo()) + "," +
			StringHandler.nullIfZero(dlaVmiBean.getQuantity()) + "," +
			DateHandler.getOracleToDateFunction(dlaVmiBean.getTransactionDate()) + "," +
			SqlHandler.delimitString(dlaVmiBean.getTransactionRefNum()) + "," +
			SqlHandler.delimitString(dlaVmiBean.getTransactionType()) + "," +
			SqlHandler.delimitString(dlaVmiBean.getDocIdCode()) + "," +
			StringHandler.nullIfZero(dlaVmiBean.getTransactionRefLineNum()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DlaVmiBean dlaVmiBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dlaVmiBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DlaVmiBean dlaVmiBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(dlaVmiBean.getCatPartNo()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaVmiBean.getQuantity()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaVmiBean.getTransactionDate()) + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "=" + 
				SqlHandler.delimitString(dlaVmiBean.getTransactionRefNum()) + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(dlaVmiBean.getTransactionType()) + "," +
			ATTRIBUTE_DOC_ID_CODE + "=" + 
				SqlHandler.delimitString(dlaVmiBean.getDocIdCode()) + "," +
			ATTRIBUTE_TRANSACTION_REF_LINE_NUM + "=" + 
				StringHandler.nullIfZero(dlaVmiBean.getTransactionRefLineNum()) + " " +
			"where " + ATTRIBUTE_CAT_PART_NO + "=" +
				StringHandler.nullIfZero(dlaVmiBean.getCatPartNo());

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

		Collection dlaVmiBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DlaVmiBean dlaVmiBean = new DlaVmiBean();
			load(dataSetRow, dlaVmiBean);
			dlaVmiBeanColl.add(dlaVmiBean);
		}

		return dlaVmiBeanColl;
	}
        
        public Collection select(String catPartNo, String unitOfSale) throws BaseException {
           //String query =  "select cat_part_no, quantity, transaction_date, transaction_ref_num, transaction_type, doc_id_code, transaction_ref_line_num ";
           //query += " from customer.usgov_vmi where company_id='USGOV' and cat_part_no in ('"+catPartNo +"') order by  cat_part_no,TRANSACTION_TYPE";
           
           String query = "select * from  customer.usgov_vmi_UNIT_OF_SALE_view ";
           query += "where company_id='USGOV' and  cat_part_no = '" +catPartNo + "'and UNIT_OF_SALE='" + unitOfSale + "'";
           
           
           Collection dlaVmiBeanColl = new Vector();

           Connection connection = this.getDbManager().getConnection();              
           
           DataSet dataSet = new SqlManager().select(connection, query);
           Iterator dataIter = dataSet.iterator();
           while (dataIter.hasNext()) {
                DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                DlaVmiBean dlaVmiBean = new DlaVmiBean();
                load(dataSetRow, dlaVmiBean);
                dlaVmiBeanColl.add(dlaVmiBean);
           }           

           this.getDbManager().returnConnection(connection);
           
           return dlaVmiBeanColl;           
        }
}