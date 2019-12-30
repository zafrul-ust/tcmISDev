package com.tcmis.client.dana.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.dana.beans.FacilityAniversaryDateViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: FacilityAniversaryDateViewBeanFactory <br>
 * @version: 1.0, Feb 9, 2005 <br>
 *****************************************************************************/

public class FacilityAniversaryDateViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_ANIVERSARY_DATE = "ANIVERSARY_DATE";

 //table name
 public String TABLE = "FACILITY_ANIVERSARY_DATE_VIEW";

 //constructor
 public FacilityAniversaryDateViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("aniversaryDate")) {
	 return ATTRIBUTE_ANIVERSARY_DATE;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, FacilityAniversaryDateViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(FacilityAniversaryDateViewBean facilityAniversaryDateViewBean)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
	"" + facilityAniversaryDateViewBean.getInventoryGroup());
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
	 public int delete(FacilityAniversaryDateViewBean facilityAniversaryDateViewBean, Connection conn)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
	"" + facilityAniversaryDateViewBean.getInventoryGroup());
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
	 public int insert(FacilityAniversaryDateViewBean facilityAniversaryDateViewBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	connection = getDbManager().getConnection();
	result = insert(facilityAniversaryDateViewBean, connection);
	}
	finally {
	this.getDbManager().returnConnection(connection);
	}
	return result;
	 }
	 public int insert(FacilityAniversaryDateViewBean facilityAniversaryDateViewBean, Connection conn)
	throws BaseException {
	SqlManager sqlManager = new SqlManager();
	String query  =
	"insert into " + TABLE + " (" +
	ATTRIBUTE_INVENTORY_GROUP + "," +
	ATTRIBUTE_FACILITY_ID + "," +
	ATTRIBUTE_ANIVERSARY_DATE + ")" +
	SqlHandler.delimitString(facilityAniversaryDateViewBean.getInventoryGroup()) + "," +
	 SqlHandler.delimitString(facilityAniversaryDateViewBean.getFacilityId()) + "," +
	SqlHandler.delimitString(facilityAniversaryDateViewBean.getAniversaryDate()) + ")";
	return sqlManager.update(conn, query);
	 }
//update
	 public int update(FacilityAniversaryDateViewBean facilityAniversaryDateViewBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	connection = getDbManager().getConnection();
	result = update(facilityAniversaryDateViewBean, connection);
	}
	finally {
	this.getDbManager().returnConnection(connection);
	}
	return result;
	 }
	 public int update(FacilityAniversaryDateViewBean facilityAniversaryDateViewBean, Connection conn)
	throws BaseException {
	String query  = "update " + TABLE + " set " +
	ATTRIBUTE_INVENTORY_GROUP + "=" +
	 SqlHandler.delimitString(facilityAniversaryDateViewBean.getInventoryGroup()) + "," +
	ATTRIBUTE_FACILITY_ID + "=" +
	 SqlHandler.delimitString(facilityAniversaryDateViewBean.getFacilityId()) + "," +
	ATTRIBUTE_ANIVERSARY_DATE + "=" +
	 SqlHandler.delimitString(facilityAniversaryDateViewBean.getAniversaryDate()) + " " +
	"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
	StringHandler.nullIfZero(facilityAniversaryDateViewBean.getInventoryGroup());
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

	Collection facilityAniversaryDateViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 FacilityAniversaryDateViewBean facilityAniversaryDateViewBean = new
		FacilityAniversaryDateViewBean();
	 load(dataSetRow, facilityAniversaryDateViewBean);
	 facilityAniversaryDateViewBeanColl.add(facilityAniversaryDateViewBean);
	}

	return facilityAniversaryDateViewBeanColl;
 }

 //select Distinct Facility
 public Collection selectDistinctFacility(SearchCriteria criteria) throws
	BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = selectDistinctFacility(criteria, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection selectDistinctFacility(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection facilityAniversaryDateViewBeanColl = new Vector();

	String query = "select distinct FACILITY_ID from " + TABLE + " " +
	 getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 FacilityAniversaryDateViewBean facilityAniversaryDateViewBean = new
		FacilityAniversaryDateViewBean();
	 load(dataSetRow, facilityAniversaryDateViewBean);
	 facilityAniversaryDateViewBeanColl.add(facilityAniversaryDateViewBean);
	}

	return facilityAniversaryDateViewBeanColl;
 }

}