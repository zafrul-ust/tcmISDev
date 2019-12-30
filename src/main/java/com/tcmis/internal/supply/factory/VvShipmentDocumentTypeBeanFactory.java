package com.tcmis.internal.supply.factory;

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

import com.tcmis.common.admin.beans.VvReceiptDocumentTypeBean;

public class VvShipmentDocumentTypeBeanFactory extends BaseBeanFactory {

	  Log log = LogFactory.getLog(this.getClass());

	  //column names
	  public String ATTRIBUTE_DOCUMENT_TYPE = "DOCUMENT_TYPE";
	  public String ATTRIBUTE_DOCUMENT_TYPE_DESC = "DOCUMENT_TYPE_DESC";

	  //table name
	  public String TABLE = "VV_SHIPMENT_DOCUMENT_TYPE";

	  //constructor
	  public VvShipmentDocumentTypeBeanFactory(DbManager dbManager) {
	    super(dbManager);
	  }

	  //get column names
	  public String getColumnName(String attributeName) {
	    if (attributeName.equals("documentType")) {
	      return ATTRIBUTE_DOCUMENT_TYPE;
	    }
	    else if (attributeName.equals("documentTypeDesc")) {
	      return ATTRIBUTE_DOCUMENT_TYPE_DESC;
	    }
	    else {
	      return super.getColumnName(attributeName);
	    }
	  }

	  //get column types
	  public int getType(String attributeName) {
	    return super.getType(attributeName, VvReceiptDocumentTypeBean.class);
	  }

	//you need to verify the primary key(s) before uncommenting this
	  /*
	//delete
	   public int delete(VvReceiptDocumentTypeBean vvReceiptDocumentTypeBean)
	    throws BaseException {
	    SearchCriteria criteria = new SearchCriteria("documentType", "SearchCriterion.EQUALS",
	     "" + vvReceiptDocumentTypeBean.getDocumentType());
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
	   public int delete(VvReceiptDocumentTypeBean vvReceiptDocumentTypeBean, Connection conn)
	    throws BaseException {
	    SearchCriteria criteria = new SearchCriteria("documentType", "SearchCriterion.EQUALS",
	     "" + vvReceiptDocumentTypeBean.getDocumentType());
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

	  public int delete(SearchCriteria criteria, Connection conn) throws
	      BaseException {
	    String sqlQuery = " delete from " + TABLE + " " +
	        getWhereClause(criteria);
	    return new SqlManager().update(conn, sqlQuery);
	  }

	//you need to verify the primary key(s) before uncommenting this
	  /*
	//insert
	   public int insert(VvReceiptDocumentTypeBean vvReceiptDocumentTypeBean)
	    throws BaseException {
	    Connection connection = null;
	    int result = 0;
	    try {
	     connection = getDbManager().getConnection();
	     result = insert(vvReceiptDocumentTypeBean, connection);
	    }
	    finally {
	     this.getDbManager().returnConnection(connection);
	    }
	    return result;
	   }
	   public int insert(VvReceiptDocumentTypeBean vvReceiptDocumentTypeBean, Connection conn)
	    throws BaseException {
	    SqlManager sqlManager = new SqlManager();
	    String query  =
	     "insert into " + TABLE + " (" +
	     ATTRIBUTE_DOCUMENT_TYPE + "," +
	     ATTRIBUTE_DOCUMENT_TYPE_DESC + ")" +
	     " values (" +
	       SqlHandler.delimitString(vvReceiptDocumentTypeBean.getDocumentType()) + "," +
	       SqlHandler.delimitString(vvReceiptDocumentTypeBean.getDocumentTypeDesc()) + ")";
	    return sqlManager.update(conn, query);
	   }
	//update
	   public int update(VvReceiptDocumentTypeBean vvReceiptDocumentTypeBean)
	    throws BaseException {
	    Connection connection = null;
	    int result = 0;
	    try {
	     connection = getDbManager().getConnection();
	     result = update(vvReceiptDocumentTypeBean, connection);
	    }
	    finally {
	     this.getDbManager().returnConnection(connection);
	    }
	    return result;
	   }
	   public int update(VvReceiptDocumentTypeBean vvReceiptDocumentTypeBean, Connection conn)
	    throws BaseException {
	    String query  = "update " + TABLE + " set " +
	     ATTRIBUTE_DOCUMENT_TYPE + "=" +
	       SqlHandler.delimitString(vvReceiptDocumentTypeBean.getDocumentType()) + "," +
	     ATTRIBUTE_DOCUMENT_TYPE_DESC + "=" +
	      SqlHandler.delimitString(vvReceiptDocumentTypeBean.getDocumentTypeDesc()) + " " +
	     "where " + ATTRIBUTE_DOCUMENT_TYPE + "=" +
	      vvReceiptDocumentTypeBean.getDocumentType();
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

	  public Collection select(SearchCriteria criteria, Connection conn) throws
	      BaseException {
	    Collection vvReceiptDocumentTypeBeanColl = new Vector();
	    String sortBy = " order by " + ATTRIBUTE_DOCUMENT_TYPE_DESC + "";
	    String query = "select * from " + TABLE + " " +
	        getWhereClause(criteria) + sortBy;
	    DataSet dataSet = new SqlManager().select(conn, query);
	    Iterator dataIter = dataSet.iterator();
	    while (dataIter.hasNext()) {
	      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	      VvReceiptDocumentTypeBean vvReceiptDocumentTypeBean = new
	          VvReceiptDocumentTypeBean();
	      load(dataSetRow, vvReceiptDocumentTypeBean);
	      vvReceiptDocumentTypeBeanColl.add(vvReceiptDocumentTypeBean);
	    }
	    return vvReceiptDocumentTypeBeanColl;
	  }
	}
