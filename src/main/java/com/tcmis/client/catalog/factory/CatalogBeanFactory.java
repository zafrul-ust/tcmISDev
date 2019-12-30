package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatalogBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: CatalogBeanFactory <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/

public class CatalogBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_CATALOG_DESC = "CATALOG_DESC";
 public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_SPEC_LIBRARY = "SPEC_LIBRARY";
 public String ATTRIBUTE_DISPLAY_FLOW_DOWN = "DISPLAY_FLOW_DOWN";
 public String ATTRIBUTE_DIRECTED_CHARGE_BY_PART = "DIRECTED_CHARGE_BY_PART";
 public String ATTRIBUTE_CONSUMABLE_OPTION = "CONSUMABLE_OPTION";

 //table name
 public String TABLE = "CATALOG";

 //constructor
 public CatalogBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("catalogId")) {
	 return ATTRIBUTE_CATALOG_ID;
	}
	else if (attributeName.equals("catalogDesc")) {
	 return ATTRIBUTE_CATALOG_DESC;
	}
	else if (attributeName.equals("branchPlant")) {
	 return ATTRIBUTE_BRANCH_PLANT;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("specLibrary")) {
	 return ATTRIBUTE_SPEC_LIBRARY;
	}
	else if (attributeName.equals("displayFlowDown")) {
	 return ATTRIBUTE_DISPLAY_FLOW_DOWN;
	}
	else if (attributeName.equals("directedChargeByPart")) {
	 return ATTRIBUTE_DIRECTED_CHARGE_BY_PART;
	}
	else if (attributeName.equals("consumableOption")) {
	 return ATTRIBUTE_CONSUMABLE_OPTION;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, CatalogBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(CatalogBean catalogBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
		"" + catalogBean.getCatalogId());
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
	public int delete(CatalogBean catalogBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
		"" + catalogBean.getCatalogId());
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
	public int insert(CatalogBean catalogBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(catalogBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(CatalogBean catalogBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_CATALOG_DESC + "," +
		ATTRIBUTE_BRANCH_PLANT + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_SPEC_LIBRARY + "," +
		ATTRIBUTE_DISPLAY_FLOW_DOWN + "," +
		ATTRIBUTE_DIRECTED_CHARGE_BY_PART + "," +
		ATTRIBUTE_CONSUMABLE_OPTION + ")" +
		" values (" +
		SqlHandler.delimitString(catalogBean.getCatalogId()) + "," +
		SqlHandler.delimitString(catalogBean.getCatalogDesc()) + "," +
		SqlHandler.delimitString(catalogBean.getBranchPlant()) + "," +
		SqlHandler.delimitString(catalogBean.getCompanyId()) + "," +
		SqlHandler.delimitString(catalogBean.getSpecLibrary()) + "," +
		SqlHandler.delimitString(catalogBean.getDisplayFlowDown()) + "," +
		SqlHandler.delimitString(catalogBean.getDirectedChargeByPart()) + "," +
		SqlHandler.delimitString(catalogBean.getConsumableOption()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(CatalogBean catalogBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(catalogBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(CatalogBean catalogBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_CATALOG_ID + "=" +
		 SqlHandler.delimitString(catalogBean.getCatalogId()) + "," +
		ATTRIBUTE_CATALOG_DESC + "=" +
		 SqlHandler.delimitString(catalogBean.getCatalogDesc()) + "," +
		ATTRIBUTE_BRANCH_PLANT + "=" +
		 SqlHandler.delimitString(catalogBean.getBranchPlant()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" +
		 SqlHandler.delimitString(catalogBean.getCompanyId()) + "," +
		ATTRIBUTE_SPEC_LIBRARY + "=" +
		 SqlHandler.delimitString(catalogBean.getSpecLibrary()) + "," +
		ATTRIBUTE_DISPLAY_FLOW_DOWN + "=" +
		 SqlHandler.delimitString(catalogBean.getDisplayFlowDown()) + "," +
		ATTRIBUTE_DIRECTED_CHARGE_BY_PART + "=" +
		 SqlHandler.delimitString(catalogBean.getDirectedChargeByPart()) + "," +
		ATTRIBUTE_CONSUMABLE_OPTION + "=" +
		 SqlHandler.delimitString(catalogBean.getConsumableOption()) + " " +
		"where " + ATTRIBUTE_CATALOG_ID + "=" +
		 catalogBean.getCatalogId();
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

	Collection catalogBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 CatalogBean catalogBean = new CatalogBean();
	 load(dataSetRow, catalogBean);
	 catalogBeanColl.add(catalogBean);
	}

	return catalogBeanColl;
 }
}