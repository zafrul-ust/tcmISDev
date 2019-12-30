package com.tcmis.internal.catalog.process;

import java.io.PrintWriter;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import java.net.URLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.client.common.beans.MaterialSearchViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.ContactLogBean;
import com.tcmis.internal.catalog.beans.ContactLogDocumentViewBean;
import com.tcmis.internal.catalog.beans.ContactLogHistoryBean;
import com.tcmis.internal.catalog.beans.ContactLogMaterialBean;

public class ContactLogProcess extends BaseProcess {
	private String URL = "";
	private ResourceLibrary library = null;
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	
	public ContactLogProcess(String client,String locale) {
		super(client,locale);
        library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
    }

	public Collection<ContactLogHistoryBean> retrieveLogHistory(ContactLogBean logInputBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ContactLogHistoryBean());
		Collection<ContactLogHistoryBean> logHistory = null;
		
		StringBuilder query = new StringBuilder("SELECT * ");
		query.append("FROM contact_log_view ");
		query.append("WHERE material_id="+logInputBean.getCurrentMaterialId()+" ");
		
		if(logInputBean.getCurrentRevisionDate() != null) // from MSDS Maintenance page
			query.append("AND revision_date=TO_DATE('"+dateFormatter.format(logInputBean.getCurrentRevisionDate())+"', 'MM/DD/RRRR hh24:mi:ss') ");
		else if(logInputBean.getRevisionDateString() != null) // from View Msds page
			query.append("AND revision_date=to_timestamp('").append(logInputBean.getRevisionDateString()).append(logInputBean.getRevisionDateString().indexOf("-") != -1?"','yyyy-mm-dd HH24:MI:SS.FF9')":"','mm/dd/yyyy HH:MI:SS AM') ");
		
		query.append("ORDER BY contact_datetime desc");
		
		logHistory = factory.selectQuery(query.toString());
		
		return logHistory;
	}
	
	public void createLogHistoryJasonObject(ContactLogBean clb, HttpServletResponse response, DateFormat dateFormat) throws BaseException, Exception {
		int index = 1;
		
		JSONObject results = new JSONObject();
    	
    	JSONArray resultsArray = new JSONArray();
		for (ContactLogHistoryBean bean : clb.getLogHistoryDataColl()) {
			BigDecimal contactLogId = bean.getContactLogId();
			Collection<ContactLogDocumentViewBean> documentColl = retrieveDocuments(contactLogId);
			
			StringBuilder documentNames = new StringBuilder(); 
			StringBuilder documentUrls = new StringBuilder();
			for (ContactLogDocumentViewBean document : documentColl) {
				if (documentNames.length() > 0) {
					documentNames.append(";");
				}
				documentNames.append(document.getDocumentName());
				if (documentUrls.length() > 0) {
					documentUrls.append(";");
				}
				documentUrls.append(document.getDocumentUrl());
			}
			JSONObject docJSONData = new JSONObject();
			
			JSONArray dataArray = new JSONArray();
			dataArray.put("N");
			dataArray.put(bean.getPersonnelName());
			dataArray.put(bean.getContactDatetime().getTime());
			dataArray.put(dateFormat.format(bean.getContactDatetime()));
			dataArray.put(bean.getContactStatus());
			dataArray.put(bean.getContactPurpose());
			dataArray.put(bean.getContactReference());
			dataArray.put(bean.getNotes());
            dataArray.put(bean.getContactName());
            dataArray.put(documentNames);
			dataArray.put(documentUrls);
			docJSONData.put("data", dataArray);
			docJSONData.put("id", index++);
			
			resultsArray.put(docJSONData);
		}
		results.put("rows", resultsArray);
		
		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
	}
	
	
	
	public String findDraftStatus() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ContactLogHistoryBean());
		
		StringBuilder query = new StringBuilder("SELECT contact_status FROM vv_contact_status ");
		query.append("WHERE final_status = 'N' order by contact_status");
		
		return factory.selectSingleValue(query.toString());
	}
	
	private boolean isStatusFinal(Connection conn, GenericSqlFactory factory, ContactLogBean logInputBean) throws BaseException {
		boolean flag = true;
		
		if (StringHandler.isBlankString(logInputBean.getContactStatus())) {
			flag = false;
		}else {
			StringBuilder query = new StringBuilder("SELECT final_status FROM vv_contact_status ");
			query.append("WHERE contact_status = '"+logInputBean.getContactStatus()+"'");
			String finalStatus = factory.selectSingleValue(query.toString(),conn);
			if (StringUtils.equals(finalStatus,"N")) {
				flag = false;
			}
		}
		return flag;
	}
	
	public BigDecimal insertContactRecord(PersonnelBean personnelBean, ContactLogBean logInputBean, DateFormat dateFormat) throws SQLException, BaseException{
		DbManager dbManager = new DbManager(getClient(),getLocale());
        Connection conn = dbManager.getConnection();
        GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialSearchViewBean());
        BigDecimal nextLogId = null;
        try {
            StringBuilder query = new StringBuilder("select TCM_OPS.CONTACT_LOG_SEQ.nextval from dual");
            nextLogId = new BigDecimal(factory.selectSingleValue(query.toString(),conn));

            StringBuilder stmt = new StringBuilder("insert into contact_log ");
            stmt.append("(contact_log_id, personnel_id");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactStatus())?"":", contact_status");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactPurpose())?"":", contact_purpose");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactLogType())?"":", contact_log_type");
            stmt.append(StringUtils.isEmpty(logInputBean.getNotes())?"":", notes");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactReference())?"":", contact_reference");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactName())?"":", contact_name");
            stmt.append(") VALUES("+nextLogId+", ");
            stmt.append(personnelBean.getPersonnelId());
            stmt.append(StringUtils.isEmpty(logInputBean.getContactStatus())?"":", '"+logInputBean.getContactStatus()+"'");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactPurpose())?"":", '"+logInputBean.getContactPurpose()+"'");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactLogType())?"":", '"+logInputBean.getContactLogType()+"'");
            stmt.append(StringUtils.isEmpty(logInputBean.getNotes())?"":", "+ SqlHandler.delimitString(logInputBean.getNotes()));
            stmt.append(StringUtils.isEmpty(logInputBean.getContactReference())?"":", "+SqlHandler.delimitString(logInputBean.getContactReference()));
            stmt.append(StringUtils.isEmpty(logInputBean.getContactName())?"":", "+SqlHandler.delimitString(logInputBean.getContactName())).append(")");
            factory.deleteInsertUpdate(stmt.toString(),conn);

            Collection<ContactLogMaterialBean> msdsColl = logInputBean.getMaterialDataColl();
            for (Iterator<ContactLogMaterialBean> i = msdsColl.iterator();i.hasNext();) {
                ContactLogMaterialBean bean = i.next();
                StringBuilder msdsStmt = new StringBuilder("insert into contact_log_msds ");
                msdsStmt.append("VALUES("+nextLogId+", "+bean.getMaterialId()+", ");
                msdsStmt.append("TO_DATE('"+dateFormat.format(bean.getRevisionDate())+"', 'DD-MON-YYYY HH24:MI \"AM\"'))");
                factory.deleteInsertUpdate(msdsStmt.toString(),conn);
            }

            //todo need to look and test this logic
            //after successfully create log record, check to see if Maxcom currently has open logs for this material
            query = new StringBuilder("select * from contact_log_view a where contact_log_id <> ").append(logInputBean.getContactLogId());
            query.append(" and exists (select null from contact_log_msds b where contact_log_id = ").append(logInputBean.getContactLogId());
            query.append(" and a.material_id = b.material_id and a.revision_date = b.revision_date)");
            query.append(" order by contact_log_id desc");
            factory.setBeanObject(new ContactLogHistoryBean());
            sendCancelRequestToMaxcom(factory.selectQuery(query.toString(),conn));

            if (isStatusFinal(conn, factory, logInputBean)) {
                nextLogId = null;
            }
        }catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(conn);
			dbManager = null;
			conn = null;
			factory = null;
		}
        return nextLogId;
	} //end of method

    //this method will send Maxcom a cancellation request if log is currently open in Maxcom
    private void sendCancelRequestToMaxcom(Collection contactLogs) {
        Iterator itar = contactLogs.iterator();
        int count = 0;
        while (itar.hasNext()) {
            ContactLogHistoryBean logBean = (ContactLogHistoryBean)itar.next();
            if (logBean.getRecertAttemptId() != null && count == 0 && !logBean.isFinalStatus()) {
                try {
                    ResourceLibrary webResources = new ResourceLibrary("maxcom");
                    URLConnection connection = new URL(webResources.getLabel("catadmin.cancel.contact.log.url")+logBean.getRecertAttemptId()).openConnection();
                    InputStream in = connection.getInputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = in.read(buffer)) > -1) {
                      log.debug(buffer.toString());
                    }
                    in.close();
                }catch (Exception e) {
                   e.printStackTrace();
                }
                break;
            }
            count++;
        }
    } //end of method

    public BigDecimal reviseContactRecord(ContactLogBean logInputBean, DateFormat dateFormat) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
        Connection conn = dbManager.getConnection();
        GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialSearchViewBean());
		BigDecimal logId = null;
        try {
            StringBuilder stmt = new StringBuilder("update contact_log set ");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactStatus())?"":"contact_status='"+logInputBean.getContactStatus()+"'");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactPurpose())?"":", contact_purpose='"+logInputBean.getContactPurpose()+"'");
            stmt.append(StringUtils.isEmpty(logInputBean.getContactLogType())?"":", contact_log_type='"+logInputBean.getContactLogType()+"'");
            stmt.append(StringUtils.isEmpty(logInputBean.getNotes())?"":", notes="+SqlHandler.delimitString(logInputBean.getNotes()));
            stmt.append(StringUtils.isEmpty(logInputBean.getContactReference())?"":", contact_reference="+SqlHandler.delimitString(logInputBean.getContactReference()));
            stmt.append(StringUtils.isEmpty(logInputBean.getContactName())?"":", contact_name="+SqlHandler.delimitString(logInputBean.getContactName()));
            stmt.append(" where contact_log_id="+logInputBean.getContactLogId());
            factory.deleteInsertUpdate(stmt.toString(),conn);

            Collection<ContactLogMaterialBean> msdsColl = logInputBean.getMaterialDataColl();
            for (Iterator<ContactLogMaterialBean> i = msdsColl.iterator();i.hasNext();) {
                ContactLogMaterialBean bean = i.next();
                StringBuilder msdsQuery = new StringBuilder();
                msdsQuery.append("select 1 from contact_log_msds where ");
                msdsQuery.append("contact_log_id="+logInputBean.getContactLogId());
                msdsQuery.append(" and material_id="+bean.getMaterialId());
                msdsQuery.append(" and revision_date=TO_DATE('"+dateFormat.format(bean.getRevisionDate())+"', 'DD-MON-YYYY HH24:MI \"AM\"')");
                String flag = factory.selectSingleValue(msdsQuery.toString(),conn);

                if (StringUtils.isEmpty(flag)) {
                    StringBuilder msdsStmt = new StringBuilder("insert into contact_log_msds ");
                    msdsStmt.append("VALUES("+logInputBean.getContactLogId()+", "+bean.getMaterialId()+", ");
                    msdsStmt.append("TO_DATE('"+dateFormat.format(bean.getRevisionDate())+"', 'DD-MON-YYYY HH24:MI \"AM\"'))");
                    factory.deleteInsertUpdate(msdsStmt.toString(),conn);
                }
            }


            if ( ! isStatusFinal(conn, factory, logInputBean)) {
                logId = logInputBean.getContactLogId();
            }
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(conn);
			dbManager = null;
			conn = null;
			factory = null;
		}
		return logId;
	}
	
	public Collection<String> contactPurposeValues() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new String());
		
		StringBuilder query = new StringBuilder("select CONTACT_PURPOSE from VV_CONTACT_PURPOSE order by CONTACT_PURPOSE");
        Object[] purposes = factory.selectIntoObjectArray(query.toString());
        
        Collection<Object[]> purposeObjs = (Collection)purposes[GenericSqlFactory.DATA_INDEX];
        Collection<String> myPurposes = new Vector<String>();
        for (Iterator<Object[]> it = purposeObjs.iterator(); it.hasNext();) {
        	myPurposes.add(it.next()[0].toString());
        }
        
        return myPurposes;
	}
	
	public Collection<String> contactTypeValues() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialSearchViewBean());
		
		StringBuilder query = new StringBuilder("select CONTACT_LOG_TYPE from VV_CONTACT_LOG_TYPE order by CONTACT_LOG_TYPE");
        Object[] types = factory.selectIntoObjectArray(query.toString());
        
        Collection<Object[]> typeObjs = (Collection)types[GenericSqlFactory.DATA_INDEX];
        Collection<String> myTypes = new Vector<String>();
        for (Iterator<Object[]> it = typeObjs.iterator(); it.hasNext();) {
        	myTypes.add(it.next()[0].toString());
        }
        
        return myTypes;
	}
	
	public Collection<String> contactStatusValues() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialSearchViewBean());
		
		StringBuilder query = new StringBuilder("select CONTACT_STATUS from VV_CONTACT_STATUS order by CONTACT_STATUS");
        Object[] status = factory.selectIntoObjectArray(query.toString());
        
        Collection<Object[]> statusObjs = (Collection)status[GenericSqlFactory.DATA_INDEX];
        Collection<String> myStatus = new Vector<String>();
        for (Iterator<Object[]> it = statusObjs.iterator(); it.hasNext();) {
        	myStatus.add(it.next()[0].toString());
        }
        
        return myStatus;
	}
	
	public Collection<ContactLogDocumentViewBean> retrieveDocuments(BigDecimal contactLogId) throws BaseException{
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ContactLogDocumentViewBean());
		Collection<ContactLogDocumentViewBean> documents = null;
		
		StringBuilder query = new StringBuilder("SELECT document_name, document_url ");
		query.append("FROM contact_document ");
		query.append("WHERE contact_log_id = ").append(contactLogId);
		query.append(" ORDER BY document_name");
		
		documents = factory.selectQuery(query.toString());
		
		ResourceLibrary webResources = new ResourceLibrary("contactdocument");
		for (Iterator<ContactLogDocumentViewBean> it = documents.iterator();it.hasNext();) {
			ContactLogDocumentViewBean bean = it.next();
			bean.setDocumentUrl(webResources.getLabel("contactlog.documentfile.hosturl")+webResources.getLabel("contactlog.documentfile.urlpath")+bean.getDocumentUrl());
		}
		
		return documents;
	}
	
	public Collection<ContactLogMaterialBean> retrieveMsdsByLogId(ContactLogBean logInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ContactLogMaterialBean());
		Collection<ContactLogMaterialBean> materials = null;
		
		StringBuilder query = new StringBuilder("SELECT material_id, revision_date ");
		query.append("FROM contact_log_msds ");
		query.append("WHERE contact_log_id="+logInputBean.getContactLogId());
		query.append("ORDER BY material_id asc, revision_date desc");

		materials = factory.selectQuery(query.toString());
		
		return materials;
	}
	
	public String getDateFormat() {
		return library.getString("java.datetimeformat");
	}
	
	public Collection<ContactLogMaterialBean> retrieveRevisionDates(BigDecimal materialId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ContactLogMaterialBean());
		
		String query = "select revision_date from msds where material_id = "+materialId+" ORDER BY revision_date desc";
        Collection<ContactLogMaterialBean> revisionDates = factory.selectQuery(query);
        return revisionDates;
	}
	
	public void deleteMaterial(ContactLogBean inputBean, DateFormat dateFormat) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		
		StringBuilder stmt = new StringBuilder("delete from contact_log_msds where ");
		stmt.append("contact_log_id = "+inputBean.getContactLogId());
		stmt.append(" and material_id = "+inputBean.getCurrentMaterialId());
		stmt.append(" and revision_date = TO_DATE('"+dateFormat.format(inputBean.getCurrentRevisionDate())+"', 'DD-MON-YYYY HH24:MI \"AM\"')");
		
		factory.deleteInsertUpdate(stmt.toString());
	}
}
