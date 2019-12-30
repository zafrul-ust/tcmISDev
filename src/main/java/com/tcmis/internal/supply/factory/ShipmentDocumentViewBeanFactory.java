package com.tcmis.internal.supply.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.supply.beans.ShipmentDocumentInputBean;
import com.tcmis.internal.supply.beans.ShipmentDocumentViewBean;

public class ShipmentDocumentViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DOCUMENT_ID = "DOCUMENT_ID";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_DOCUMENT_NAME = "DOCUMENT_NAME";
	public String ATTRIBUTE_DOCUMENT_TYPE = "DOCUMENT_TYPE";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_DOCUMENT_DATE = "DOCUMENT_DATE";
	public String ATTRIBUTE_DOCUMENT_URL = "DOCUMENT_URL";
	public String ATTRIBUTE_ENTERED_BY = "ENTERED_BY";
	public String ATTRIBUTE_ENTERED_BY_NAME = "ENTERED_BY_NAME";
	public String ATTRIBUTE_DOCUMENT_TYPE_DESC = "DOCUMENT_TYPE_DESC";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
	

	//table name
	public String TABLE = "SHIPMENT_DOCUMENT_VIEW";
	public String INSERT_TABLE = "SHIPMENT_DOCUMENT";


	//constructor
	public ShipmentDocumentViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("documentId")) {
			return ATTRIBUTE_DOCUMENT_ID;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("documentName")) {
			return ATTRIBUTE_DOCUMENT_NAME;
		}
		else if(attributeName.equals("documentType")) {
			return ATTRIBUTE_DOCUMENT_TYPE;
		}
		else if(attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if(attributeName.equals("documentDate")) {
			return ATTRIBUTE_DOCUMENT_DATE;
		}
		else if(attributeName.equals("documentUrl")) {
			return ATTRIBUTE_DOCUMENT_URL;
		}
		else if(attributeName.equals("enteredBy")) {
			return ATTRIBUTE_ENTERED_BY;
		}
		else if(attributeName.equals("enteredByName")) {
			return ATTRIBUTE_ENTERED_BY_NAME;
		}
		else if(attributeName.equals("documentTypeDesc")) {
			return ATTRIBUTE_DOCUMENT_TYPE_DESC;
		}
	 	else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
	 	else if (attributeName.equals("companyName")) {
			return ATTRIBUTE_COMPANY_NAME;
		}
	 	else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName,ShipmentDocumentViewBean.class);
	}




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

		String sqlQuery = " delete from " + INSERT_TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


//you need to verify the primary key(s) before uncommenting this
	 public int insert(ShipmentDocumentInputBean shipmentDocInputBean) throws
		BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 //shipmentDocInputBean = selectDocumentId(shipmentDocInputBean);
		 result = insert(shipmentDocInputBean, connection);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }

	 public int insert(ShipmentDocumentInputBean shipmentDocInputBean,
		Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query =
		 "insert into " + INSERT_TABLE + " (" +
		 ATTRIBUTE_DOCUMENT_ID + "," +
		 ATTRIBUTE_SHIPMENT_ID + "," +
		 ATTRIBUTE_DOCUMENT_NAME + "," +
		 ATTRIBUTE_DOCUMENT_TYPE + "," +
		 ATTRIBUTE_INSERT_DATE + "," +
		 ATTRIBUTE_DOCUMENT_DATE + "," +
		 ATTRIBUTE_DOCUMENT_URL + "," +
		 ATTRIBUTE_ENTERED_BY + "," +
		 ATTRIBUTE_COMPANY_ID + ")" +
		 " values (" +
		 shipmentDocInputBean.getDocumentId() + "," +
		 shipmentDocInputBean.getShipmentId() + "," +
		 SqlHandler.delimitString(shipmentDocInputBean.getDocumentName()) + "," +
		 SqlHandler.delimitString(shipmentDocInputBean.getDocumentType()) + "," +
		 "sysdate," +
		 "sysdate," +
		 SqlHandler.delimitString(shipmentDocInputBean.getDocumentUrl()) + "," +
		 shipmentDocInputBean.getEnteredBy()+ "," +
		 SqlHandler.delimitString(shipmentDocInputBean.getCompanyId())+")";
		 

	return sqlManager.update(conn, query);
 }

 public ShipmentDocumentInputBean selectDocumentId(ShipmentDocumentInputBean
		 shipmentDocInputBean) throws BaseException {
	Connection connection = null;
	try {
	 connection = getDbManager().getConnection();
	 String query = "select nvl(max(" + ATTRIBUTE_DOCUMENT_ID + "),0)+1 " +
		ATTRIBUTE_DOCUMENT_ID + " from " + TABLE + " where " + ATTRIBUTE_SHIPMENT_ID +
		" = " + shipmentDocInputBean.getShipmentId();
	
	 DataSet dataSet = new SqlManager().select(connection, query);
	 Iterator dataIter = dataSet.iterator();

	 while (dataIter.hasNext()) {
		DataSetRow dataSetRow = (DataSetRow) dataIter.next();
		ShipmentDocumentViewBean shipmentDocViewBean = new
		ShipmentDocumentViewBean();
		load(dataSetRow, shipmentDocViewBean);
		shipmentDocInputBean.setDocumentId(shipmentDocViewBean.
		 getDocumentId());
	 }
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}

	return shipmentDocInputBean;
 }



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

		Collection shipmentDocViewBeanColl = new Vector();
		String sortBy = " order by " +ATTRIBUTE_DOCUMENT_DATE + " desc";
		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + " " + sortBy;

		DataSet dataSet = new SqlManager().select(conn, query);
	

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ShipmentDocumentViewBean shipmentDocViewBean = new ShipmentDocumentViewBean();
			load(dataSetRow, shipmentDocViewBean);
			shipmentDocViewBeanColl.add(shipmentDocViewBean);
		}

		return shipmentDocViewBeanColl;
	}
}
