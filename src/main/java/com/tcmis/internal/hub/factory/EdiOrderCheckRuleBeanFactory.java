package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.EdiOrderCheckRuleBean;


/******************************************************************************
 * CLASSNAME: EdiOrderCheckRuleBeanFactory <br>
 * @version: 1.0, Jul 5, 2007 <br>
 *****************************************************************************/


public class EdiOrderCheckRuleBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_RULE_ID = "RULE_ID";
	public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID1 = "CONTACT_PERSONNEL_ID1";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID2 = "CONTACT_PERSONNEL_ID2";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID3 = "CONTACT_PERSONNEL_ID3";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID4 = "CONTACT_PERSONNEL_ID4";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID5 = "CONTACT_PERSONNEL_ID5";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID6 = "CONTACT_PERSONNEL_ID6";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID7 = "CONTACT_PERSONNEL_ID7";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID8 = "CONTACT_PERSONNEL_ID8";

	//table name
	public String TABLE = "EDI_ORDER_CHECK_RULE";


	//constructor
	public EdiOrderCheckRuleBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("ruleId")) {
			return ATTRIBUTE_RULE_ID;
		}
		else if(attributeName.equals("transactionType")) {
			return ATTRIBUTE_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("contactPersonnelId1")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID1;
		}
		else if(attributeName.equals("contactPersonnelId2")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID2;
		}
		else if(attributeName.equals("contactPersonnelId3")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID3;
		}
		else if(attributeName.equals("contactPersonnelId4")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID4;
		}
		else if(attributeName.equals("contactPersonnelId5")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID5;
		}
		else if(attributeName.equals("contactPersonnelId6")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID6;
		}
		else if(attributeName.equals("contactPersonnelId7")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID7;
		}
		else if(attributeName.equals("contactPersonnelId8")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID8;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, EdiOrderCheckRuleBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(EdiOrderCheckRuleBean ediOrderCheckRuleBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + ediOrderCheckRuleBean.getCompanyId());

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


	public int delete(EdiOrderCheckRuleBean ediOrderCheckRuleBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + ediOrderCheckRuleBean.getCompanyId());

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
	public int insert(EdiOrderCheckRuleBean ediOrderCheckRuleBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(ediOrderCheckRuleBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(EdiOrderCheckRuleBean ediOrderCheckRuleBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_RULE_ID + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID1 + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID2 + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID3 + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID4 + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID5 + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID6 + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID7 + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID8 + ")" +
 values (
			SqlHandler.delimitString(ediOrderCheckRuleBean.getCompanyId()) + "," +
			SqlHandler.delimitString(ediOrderCheckRuleBean.getRuleId()) + "," +
			SqlHandler.delimitString(ediOrderCheckRuleBean.getTransactionType()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId1()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId2()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId3()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId4()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId5()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId6()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId7()) + "," +
			StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId8()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(EdiOrderCheckRuleBean ediOrderCheckRuleBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(ediOrderCheckRuleBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(EdiOrderCheckRuleBean ediOrderCheckRuleBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(ediOrderCheckRuleBean.getCompanyId()) + "," +
			ATTRIBUTE_RULE_ID + "=" + 
				SqlHandler.delimitString(ediOrderCheckRuleBean.getRuleId()) + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(ediOrderCheckRuleBean.getTransactionType()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID1 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId1()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID2 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId2()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID3 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId3()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID4 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId4()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID5 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId5()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID6 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId6()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID7 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId7()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID8 + "=" + 
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getContactPersonnelId8()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				StringHandler.nullIfZero(ediOrderCheckRuleBean.getCompanyId());

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

		Collection ediOrderCheckRuleBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			EdiOrderCheckRuleBean ediOrderCheckRuleBean = new EdiOrderCheckRuleBean();
			load(dataSetRow, ediOrderCheckRuleBean);
			ediOrderCheckRuleBeanColl.add(ediOrderCheckRuleBean);
		}

		return ediOrderCheckRuleBeanColl;
	}
}