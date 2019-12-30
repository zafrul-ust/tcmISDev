package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.HomeCompanyBean;

/******************************************************************************
 * CLASSNAME: HomeCompanyBeanFactory <br>
 * @version: 1.0, Apr 20, 2005 <br>
 *****************************************************************************/

public class HomeCompanyBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_HOME_COMPANY_ID = "HOME_COMPANY_ID";
 public String ATTRIBUTE_HOME_CURRENCY_ID = "HOME_CURRENCY_ID";
 public String ATTRIBUTE_OPERATING_COMPANY_ID = "OPERATING_COMPANY_ID";
 public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
 public String ATTRIBUTE_PO_IMAGE_URL = "PO_IMAGE_URL";
 public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
 public String ATTRIBUTE_BILL_TO_LOCATION_ID = "BILL_TO_LOCATION_ID";
 public String ATTRIBUTE_HOME_COMPANY_SHORT_NAME = "HOME_COMPANY_SHORT_NAME";
 public String ATTRIBUTE_REMIT_TO_COMPANY_ID = "REMIT_TO_COMPANY_ID";
 public String ATTRIBUTE_REMIT_TO_LOCATION_ID = "REMIT_TO_LOCATION_ID";
 public String ATTRIBUTE_PO_TERMS_AND_CONDITIONS = "PO_TERMS_AND_CONDITIONS";
 public String ATTRIBUTE_TAX_ID = "TAX_ID";
 public String ATTRIBUTE_LOGO_IMAGE_URL = "LOGO_IMAGE_URL";

 //table name
 public String TABLE = "HOME_COMPANY";

 //constructor
 public HomeCompanyBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("homeCompanyId")) {
	 return ATTRIBUTE_HOME_COMPANY_ID;
	}
	else if (attributeName.equals("homeCurrencyId")) {
	 return ATTRIBUTE_HOME_CURRENCY_ID;
	}
	else if (attributeName.equals("operatingCompanyId")) {
	 return ATTRIBUTE_OPERATING_COMPANY_ID;
	}
	else if (attributeName.equals("operatingEntityName")) {
	 return ATTRIBUTE_OPERATING_ENTITY_NAME;
	}
	else if (attributeName.equals("poImageUrl")) {
	 return ATTRIBUTE_PO_IMAGE_URL;
	}
	else if (attributeName.equals("billToCompanyId")) {
	 return ATTRIBUTE_BILL_TO_COMPANY_ID;
	}
	else if (attributeName.equals("billToLocationId")) {
	 return ATTRIBUTE_BILL_TO_LOCATION_ID;
	}
	else if (attributeName.equals("homeCompanyShortName")) {
	 return ATTRIBUTE_HOME_COMPANY_SHORT_NAME;
	}
	else if (attributeName.equals("remitToCompanyId")) {
	 return ATTRIBUTE_REMIT_TO_COMPANY_ID;
	}
	else if (attributeName.equals("remitToLocationId")) {
	 return ATTRIBUTE_REMIT_TO_LOCATION_ID;
	}
	else if (attributeName.equals("poTermsAndConditions")) {
	 return ATTRIBUTE_PO_TERMS_AND_CONDITIONS;
	}
	else if (attributeName.equals("taxId")) {
	 return ATTRIBUTE_TAX_ID;
	}
	else if (attributeName.equals("logoImageUrl")) {
	 return ATTRIBUTE_LOGO_IMAGE_URL;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, HomeCompanyBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(HomeCompanyBean homeCompanyBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("homeCompanyId", "SearchCriterion.EQUALS",
		"" + homeCompanyBean.getHomeCompanyId());
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
	public int delete(HomeCompanyBean homeCompanyBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("homeCompanyId", "SearchCriterion.EQUALS",
		"" + homeCompanyBean.getHomeCompanyId());
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
	public int insert(HomeCompanyBean homeCompanyBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(homeCompanyBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(HomeCompanyBean homeCompanyBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_HOME_COMPANY_ID + "," +
		ATTRIBUTE_HOME_CURRENCY_ID + "," +
		ATTRIBUTE_OPERATING_COMPANY_ID + "," +
		ATTRIBUTE_HOME_COMPANY_NAME + "," +
		ATTRIBUTE_PO_IMAGE_URL + "," +
		ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
		ATTRIBUTE_BILL_TO_LOCATION_ID + "," +
		ATTRIBUTE_HOME_COMPANY_SHORT_NAME + "," +
		ATTRIBUTE_REMIT_TO_COMPANY_ID + "," +
		ATTRIBUTE_REMIT_TO_LOCATION_ID + "," +
		ATTRIBUTE_PO_TERMS_AND_CONDITIONS + "," +
		ATTRIBUTE_TAX_ID + ")" +
		" values (" +
		SqlHandler.delimitString(homeCompanyBean.getHomeCompanyId()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getHomeCurrencyId()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getOperatingCompanyId()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getOperatingEntityName()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getPoImageUrl()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getBillToCompanyId()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getBillToLocationId()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getHomeCompanyShortName()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getRemitToCompanyId()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getRemitToLocationId()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getPoTermsAndConditions()) + "," +
		SqlHandler.delimitString(homeCompanyBean.getTaxId()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(HomeCompanyBean homeCompanyBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(homeCompanyBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(HomeCompanyBean homeCompanyBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_HOME_COMPANY_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getHomeCompanyId()) + "," +
		ATTRIBUTE_HOME_CURRENCY_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getHomeCurrencyId()) + "," +
		ATTRIBUTE_OPERATING_COMPANY_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getOperatingCompanyId()) + "," +
		ATTRIBUTE_HOME_COMPANY_NAME + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getOperatingEntityName()) + "," +
		ATTRIBUTE_PO_IMAGE_URL + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getPoImageUrl()) + "," +
		ATTRIBUTE_BILL_TO_COMPANY_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getBillToCompanyId()) + "," +
		ATTRIBUTE_BILL_TO_LOCATION_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getBillToLocationId()) + "," +
		ATTRIBUTE_HOME_COMPANY_SHORT_NAME + "=" +
	 SqlHandler.delimitString(homeCompanyBean.getHomeCompanyShortName()) + "," +
		ATTRIBUTE_REMIT_TO_COMPANY_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getRemitToCompanyId()) + "," +
		ATTRIBUTE_REMIT_TO_LOCATION_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getRemitToLocationId()) + "," +
		ATTRIBUTE_PO_TERMS_AND_CONDITIONS + "=" +
	 SqlHandler.delimitString(homeCompanyBean.getPoTermsAndConditions()) + "," +
		ATTRIBUTE_TAX_ID + "=" +
		 SqlHandler.delimitString(homeCompanyBean.getTaxId()) + " " +
		"where " + ATTRIBUTE_HOME_COMPANY_ID + "=" +
		 homeCompanyBean.getHomeCompanyId();
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

	Collection homeCompanyBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 HomeCompanyBean homeCompanyBean = new HomeCompanyBean();
	 load(dataSetRow, homeCompanyBean);
	 homeCompanyBeanColl.add(homeCompanyBean);
	}

	return homeCompanyBeanColl;
 }

 //select home company logo for packing list
 public Collection selectLogoForPackingList(String[] shipmentId) throws
	BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = selectLogoForPackingList(shipmentId, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection selectLogoForPackingList(String[] shipmentId,
	Connection conn) throws BaseException {

	Collection homeCompanyBeanColl = new Vector();

	String query =
	 "select distinct hc.logo_image_url from home_company hc, hub h, shipment s" + " where hc.home_company_id = h.home_company_id and h.branch_plant = s.branch_plant" +
	 " and s.shipment_id in (";
	for (int i = 0; i < shipmentId.length; i++) {
	 query += shipmentId[i] + ",";
	}
	//remove the last commas ',' and add close parathensis
	query = query.substring(0, query.length() - 1) + ")";
	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 HomeCompanyBean homeCompanyBean = new HomeCompanyBean();
	 load(dataSetRow, homeCompanyBean);
	 homeCompanyBeanColl.add(homeCompanyBean);
	}

	return homeCompanyBeanColl;
 }

}