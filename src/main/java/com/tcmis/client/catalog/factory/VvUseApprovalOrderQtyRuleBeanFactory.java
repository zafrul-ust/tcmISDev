package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.VvUseApprovalOrderQtyRuleBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: VvUseApprovalOrderQtyRuleBeanFactory <br>
 * @version: 1.0, Feb 14, 2006 <br>
 *****************************************************************************/

public class VvUseApprovalOrderQtyRuleBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_ORDER_QUANTITY_RULE = "ORDER_QUANTITY_RULE";
 public String ATTRIBUTE_ORDER_QUANTITY_RULE_DESC = "ORDER_QUANTITY_RULE_DESC";

 //table name
 public String TABLE = "VV_USE_APPROVAL_ORDER_QTY_RULE";

 //constructor
 public VvUseApprovalOrderQtyRuleBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("orderQuantityRule")) {
	 return ATTRIBUTE_ORDER_QUANTITY_RULE;
	}
	else if (attributeName.equals("orderQuantityRuleDesc")) {
	 return ATTRIBUTE_ORDER_QUANTITY_RULE_DESC;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, VvUseApprovalOrderQtyRuleBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(VvUseApprovalOrderQtyRuleBean vvUseApprovalOrderQtyRuleBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("orderQuantityRule", "SearchCriterion.EQUALS",
		"" + vvUseApprovalOrderQtyRuleBean.getOrderQuantityRule());
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
	public int delete(VvUseApprovalOrderQtyRuleBean vvUseApprovalOrderQtyRuleBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("orderQuantityRule", "SearchCriterion.EQUALS",
		"" + vvUseApprovalOrderQtyRuleBean.getOrderQuantityRule());
	 return delete(criteria, conn);
	}
	*/

 public int delete(SearchCriteria criteria) throws BaseException {

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

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	 public int insert(VvUseApprovalOrderQtyRuleBean vvUseApprovalOrderQtyRuleBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(vvUseApprovalOrderQtyRuleBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(VvUseApprovalOrderQtyRuleBean vvUseApprovalOrderQtyRuleBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_ORDER_QUANTITY_RULE + "," +
		ATTRIBUTE_ORDER_QUANTITY_RULE_DESC + ")" +
		" values (" +
		SqlHandler.delimitString(vvUseApprovalOrderQtyRuleBean.getOrderQuantityRule()) + "," +
		SqlHandler.delimitString(vvUseApprovalOrderQtyRuleBean.getOrderQuantityRuleDesc()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	 public int update(VvUseApprovalOrderQtyRuleBean vvUseApprovalOrderQtyRuleBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(vvUseApprovalOrderQtyRuleBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(VvUseApprovalOrderQtyRuleBean vvUseApprovalOrderQtyRuleBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_ORDER_QUANTITY_RULE + "=" +
		 SqlHandler.delimitString(vvUseApprovalOrderQtyRuleBean.getOrderQuantityRule()) + "," +
		ATTRIBUTE_ORDER_QUANTITY_RULE_DESC + "=" +
		 SqlHandler.delimitString(vvUseApprovalOrderQtyRuleBean.getOrderQuantityRuleDesc()) + " " +
		"where " + ATTRIBUTE_ORDER_QUANTITY_RULE + "=" +
		 vvUseApprovalOrderQtyRuleBean.getOrderQuantityRule();
	 return new SqlManager().update(conn, query);
	}
	*/

 //select
 public Collection select(SearchCriteria criteria) throws BaseException {

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

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection vvUseApprovalOrderQtyRuleBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 VvUseApprovalOrderQtyRuleBean vvUseApprovalOrderQtyRuleBean = new
		VvUseApprovalOrderQtyRuleBean();
	 load(dataSetRow, vvUseApprovalOrderQtyRuleBean);
	 vvUseApprovalOrderQtyRuleBeanColl.add(vvUseApprovalOrderQtyRuleBean);
	}

	return vvUseApprovalOrderQtyRuleBeanColl;
 }
}