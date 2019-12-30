package com.tcmis.client.common.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;

import java.sql.Connection;


import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.client.common.beans.MsdsDocumentInputBean;
import com.tcmis.client.common.beans.MsdsDocumentViewBean;


/**
 * ***************************************************************************
 * CLASSNAME: MsdsDocumentViewBeanFactory <br>
 *
 * @version: 1.0, July 6 2010<br>2.0, February 14, 2018<br>
 * ***************************************************************************
 */


public class MsdsDocumentViewBeanFactory extends BaseBeanFactory {

    Log log = LogFactory.getLog(this.getClass());

    //column names
    public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
    public String ATTRIBUTE_DOCUMENT_ID = "DOCUMENT_ID";
    public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
    public String ATTRIBUTE_DOCUMENT_NAME = "DOCUMENT_NAME";
    public String ATTRIBUTE_DOCUMENT_TYPE = "DOCUMENT_TYPE";
    public String ATTRIBUTE_DOCUMENT_TYPE_NAME = "DOCUMENT_TYPE_NAME";
    public String ATTRIBUTE_DOCUMENT_DATE = "DOCUMENT_DATE";
    public String ATTRIBUTE_DOCUMENT_URL = "DOCUMENT_URL";
    public String ATTRIBUTE_ENTERED_ON = "ENTERED_ON";
    public String ATTRIBUTE_ENTERED_BY_NAME = "ENTERED_BY_NAME";
    public String ATTRIBUTE_ENTERED_BY = "ENTERED_BY";
    public String ATTRIBUTE_DELETED_ON = "DELETED_ON";
    public String ATTRIBUTE_DELETED_BY = "DELETED_BY";
    public String ATTRIBUTE_DELETED_BY_NAME = "DELETED_BY_NAME";
    public String ATTRIBUTE_STATUS = "STATUS";
    public String ATTRIBUTE_DOCUMENT_SOURCE = "DOCUMENT_SOURCE";

    //table name
    public String SEARCH_TABLE = "MATERIAL_DOCUMENT_VIEW";
    public String DOCUMENT_TABLE = "DOCUMENT";
    public String INSERT_COMPANY_TABLE = "COMPANY_MATERIAL_DOCUMENT";
    public String INSERT_GLOBAL_TABLE = "MATERIAL_DOCUMENT";


    //constructor
    public MsdsDocumentViewBeanFactory(DbManager dbManager) {
        super(dbManager);
    }


    //get column names
    public String getColumnName(String attributeName) {
        if (attributeName.equals("documentId")) {
            return ATTRIBUTE_DOCUMENT_ID;
        } else if (attributeName.equals("materialId")) {
            return ATTRIBUTE_MATERIAL_ID;
        } else if (attributeName.equals("documentName")) {
            return ATTRIBUTE_DOCUMENT_NAME;
        } else if (attributeName.equals("documentType")) {
            return ATTRIBUTE_DOCUMENT_TYPE;
        } else if (attributeName.equals("documentDate")) {
            return ATTRIBUTE_DOCUMENT_DATE;
        } else if (attributeName.equals("documentUrl")) {
            return ATTRIBUTE_DOCUMENT_URL;
        } else if (attributeName.equals("enteredBy")) {
            return ATTRIBUTE_ENTERED_BY;
        } else if (attributeName.equals("enteredOn")) {
            return ATTRIBUTE_ENTERED_ON;
        } else if (attributeName.equals("enteredByName")) {
            return ATTRIBUTE_ENTERED_BY_NAME;
        } else if (attributeName.equals("documentTypeName")) {
            return ATTRIBUTE_DOCUMENT_TYPE_NAME;
        } else if (attributeName.equals("companyId")) {
            return ATTRIBUTE_COMPANY_ID;
        } else if (attributeName.equals("deletedOn")) {
            return ATTRIBUTE_DELETED_ON;
        } else if (attributeName.equals("deletedBy")) {
            return ATTRIBUTE_DELETED_BY;
        } else if (attributeName.equals("deletedByName")) {
            return ATTRIBUTE_DELETED_BY_NAME;
        } else if (attributeName.equals("status")) {
            return ATTRIBUTE_STATUS;
        } else if (attributeName.equals("documentSource")) {
            return ATTRIBUTE_DOCUMENT_SOURCE;
        } else {
            return super.getColumnName(attributeName);
        }
    }


    //get column types
    public int getType(String attributeName) {
        return super.getType(attributeName, MsdsDocumentViewBean.class);
    }


    public int delete(SearchCriteria criteria, BigDecimal personnelId)
            throws BaseException {

        Connection connection = null;
        int result = 0;
        try {
            connection = getDbManager().getConnection();
            result = delete(criteria, connection, personnelId);
        }
        finally {
            this.getDbManager().returnConnection(connection);
        }
        return result;
    }


    public int delete(SearchCriteria criteria, Connection conn, BigDecimal personnelId) throws BaseException {
        String sqlQuery = " update " + DOCUMENT_TABLE + " set status = 'I', deleted_on = sysdate, deleted_by =" + personnelId + " " +getWhereClause(criteria);
        return new SqlManager().update(conn, sqlQuery);
    }


    //you need to verify the primary key(s) before uncommenting this
    public int insert(MsdsDocumentInputBean msdsDocumentInputBean) throws BaseException {

        Connection connection = null;
        int result = 0;
        try {
            connection = getDbManager().getConnection();
            result = insert(msdsDocumentInputBean, connection);
        }
        finally {
            this.getDbManager().returnConnection(connection);
        }
        return result;
    }

    public int insert(MsdsDocumentInputBean msdsDocumentInputBean, Connection conn) throws BaseException {
        SqlManager sqlManager = new SqlManager();
        
        //get new documentId from sequence
        String newDocumentId = "";  
        DataSet dataSet = new SqlManager().select(conn, "select document_sequence.nextval from dual");
        Iterator dataIter = dataSet.iterator();
        while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			newDocumentId = dataSetRow.getString("NEXTVAL");
		}

        //insert into company_material_document
    	if (!StringHandler.isBlankString(msdsDocumentInputBean.getCompanyId())) {
            //insert document information into document table
            String query = "insert into " + INSERT_COMPANY_TABLE + " (";
            query += ATTRIBUTE_COMPANY_ID + "," +
                    ATTRIBUTE_DOCUMENT_ID + "," +
                    ATTRIBUTE_MATERIAL_ID + "," +
                    ATTRIBUTE_DOCUMENT_NAME + "," +
                    ATTRIBUTE_DOCUMENT_TYPE + "," +
                    ATTRIBUTE_DOCUMENT_SOURCE + "," +
                    ATTRIBUTE_DOCUMENT_DATE + "," +
                    ATTRIBUTE_DOCUMENT_URL + "," +
                    ATTRIBUTE_ENTERED_ON + "," +
                    ATTRIBUTE_ENTERED_BY + "," +
                    ATTRIBUTE_DELETED_ON + "," +
                    ATTRIBUTE_DELETED_BY + ")" +
                    " values (";
            query += SqlHandler.delimitString(msdsDocumentInputBean.getCompanyId()) + ",";
            query += newDocumentId + "," +
                    msdsDocumentInputBean.getMaterialId()+","+
                    SqlHandler.delimitString(msdsDocumentInputBean.getDocumentName()) + "," +
                    SqlHandler.delimitString(msdsDocumentInputBean.getDocumentType()) + "," +
                    "'Catalog',"+
                    "sysdate," +
                    SqlHandler.delimitString(msdsDocumentInputBean.getDocumentUrl()) + "," +
                    "sysdate," +
                    msdsDocumentInputBean.getEnteredBy() + ",'',''" +
                    ")";
            return sqlManager.update(conn, query);
        //insert into material_document
    	}else {
            //insert document's information into document table
            String query = "insert into " + DOCUMENT_TABLE + " (";
            query += ATTRIBUTE_COMPANY_ID + "," +
                    ATTRIBUTE_DOCUMENT_ID + "," +
                    ATTRIBUTE_DOCUMENT_NAME + "," +
                    ATTRIBUTE_DOCUMENT_TYPE + "," +
                    ATTRIBUTE_DOCUMENT_SOURCE + "," +
                    ATTRIBUTE_DOCUMENT_DATE + "," +
                    ATTRIBUTE_DOCUMENT_URL + "," +
                    ATTRIBUTE_ENTERED_ON + "," +
                    ATTRIBUTE_ENTERED_BY + "," +
                    ATTRIBUTE_DELETED_ON + "," +
                    ATTRIBUTE_DELETED_BY + "," +
                    ATTRIBUTE_STATUS + ")" +
                    " values (";
            query += SqlHandler.delimitString("GLOBAL") + ",";
            query += newDocumentId + "," +
                    SqlHandler.delimitString(msdsDocumentInputBean.getDocumentName()) + "," +
                    SqlHandler.delimitString(msdsDocumentInputBean.getDocumentType()) + "," +
                    SqlHandler.delimitString(msdsDocumentInputBean.getDocumentSource()) + "," +
                    "sysdate," +
                    SqlHandler.delimitString(msdsDocumentInputBean.getDocumentUrl()) + "," +
                    "sysdate," +
                    msdsDocumentInputBean.getEnteredBy() + ",'','','A'" +
                    ")";
            sqlManager.update(conn, query);

            //insert material_document
            query = "insert into " + INSERT_GLOBAL_TABLE + " (";
            query += ATTRIBUTE_DOCUMENT_ID + "," +
                     ATTRIBUTE_MATERIAL_ID + ")" +
                     " values (";
            query += newDocumentId + "," +
                     msdsDocumentInputBean.getMaterialId() +
                    ")";
            return sqlManager.update(conn, query);
        }
    } //end of method

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

        Collection msdsDocumentViewBeanColl = new Vector();
        String sortBy = " order by " + ATTRIBUTE_DOCUMENT_DATE + " desc";
        String query = "select * from " + SEARCH_TABLE + " " +
                getWhereClause(criteria) + " " + sortBy;

        DataSet dataSet = new SqlManager().select(conn, query);


        Iterator dataIter = dataSet.iterator();

        while (dataIter.hasNext()) {
            DataSetRow dataSetRow = (DataSetRow) dataIter.next();
            MsdsDocumentViewBean msdsDocumentViewBean = new MsdsDocumentViewBean();
            load(dataSetRow, msdsDocumentViewBean);
            if (msdsDocumentViewBean.getDocumentUrl().startsWith("mfrNotificationDocuments/"))
                msdsDocumentViewBean.setDocumentUrl("/"+msdsDocumentViewBean.getDocumentUrl());
            msdsDocumentViewBeanColl.add(msdsDocumentViewBean);
        }

        return msdsDocumentViewBeanColl;
    }
}