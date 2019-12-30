package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.CatalogVendorProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.ContactLogDocumentViewBean;
import com.tcmis.internal.catalog.beans.ContactLogHistoryBean;
import com.tcmis.internal.catalog.beans.VendorContactLogInputBean;

public class VendorContactLogProcess extends CatalogVendorProcess {

	private final ContactLogProcess contactLogProcess;
	
	public VendorContactLogProcess(BigDecimal personnelId, String client) {
		super(personnelId, client);
		contactLogProcess = new ContactLogProcess(client, this.getLocale());
	}
	
	public VendorContactLogProcess(BigDecimal personnelId, String client, String locale) {
		super(personnelId, client);
		contactLogProcess = new ContactLogProcess(client, locale);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ContactLogHistoryBean> searchContactLogs(VendorContactLogInputBean input) throws BaseException {
		ResourceLibrary webResources = new ResourceLibrary("contactdocument");
		String query = "select l.*, d.document_name"
				+ ", '" + webResources.getLabel("contactlog.documentfile.hosturl") 
						+ webResources.getLabel("contactlog.documentfile.urlpath") + "' || d.document_url document_url"
				+ ", fx_personnel_id_to_name(l.personnel_id) personnelName"
				+ " from contact_log l, contact_document d, contact_log_catalog_queue lq"
				+ " where lq.q_id = "+input.getqId()
				+ " and lq.contact_log_id = l.contact_log_id"
				+ " and l.contact_log_id = d.contact_log_id(+)"
				+ " order by l.contact_datetime desc";
		
		Collection<ContactLogHistoryBean> documentLogHistory = (Collection<ContactLogHistoryBean>)factory.setBean(new ContactLogHistoryBean()).selectQuery(query);
		List<ContactLogHistoryBean> logHistory = new ArrayList<>();
		
		BigDecimal lastContactLogId = null;
		ContactLogHistoryBean logEntry = null;
		for (ContactLogHistoryBean bean : documentLogHistory) {
			if (bean.getContactLogId().equals(lastContactLogId)) {
				logEntry.setDocumentName(logEntry.getDocumentName()+";"+bean.getDocumentName());
				logEntry.setDocumentUrl(logEntry.getDocumentUrl()+";"+bean.getDocumentUrl());
			}
			else {
				if (logEntry != null) {
					logHistory.add(logEntry);
				}
				logEntry = bean;
				lastContactLogId = bean.getContactLogId();
			}
		}
		if (logEntry != null) {
			logHistory.add(logEntry);
		}
		
		return logHistory;
	}
	
	/**
	 * 
	 * @param logInputBean
	 * @param personnelBean
	 * @return the id of the newly created contact log
	 * @throws BaseException
	 */
	public BigDecimal insertContactLog(VendorContactLogInputBean logInputBean, PersonnelBean personnelBean) throws BaseException {
		Connection conn = dbManager.getConnection();
		BigDecimal nextLogId = null;
		try {
			StringBuilder query = new StringBuilder("select TCM_OPS.CONTACT_LOG_SEQ.nextval from dual");
            nextLogId = new BigDecimal(factory.selectSingleValue(query.toString(),conn));
			
			StringBuilder stmt = new StringBuilder("insert into contact_log ")
					.append("(contact_log_id, personnel_id, contact_datetime")
					.append(StringUtils.isEmpty(logInputBean.getContactStatus())?"":", contact_status")
					.append(StringUtils.isEmpty(logInputBean.getContactPurpose())?"":", contact_purpose")
					.append(StringUtils.isEmpty(logInputBean.getContactLogType())?"":", contact_log_type")
					.append(StringUtils.isEmpty(logInputBean.getContactReference())?"":", contact_reference")
					.append(StringUtils.isEmpty(logInputBean.getContactName())?"":", contact_name")
					.append(") VALUES(").append(nextLogId).append(", ")
					.append(personnelBean.getPersonnelId()).append(", sysdate")
					.append(StringUtils.isEmpty(logInputBean.getContactStatus())?"":", "+SqlHandler.delimitString(logInputBean.getContactStatus()))
					.append(StringUtils.isEmpty(logInputBean.getContactPurpose())?"":", "+SqlHandler.delimitString(logInputBean.getContactPurpose()))
					.append(StringUtils.isEmpty(logInputBean.getContactLogType())?"":", "+SqlHandler.delimitString(logInputBean.getContactLogType()))
					.append(StringUtils.isEmpty(logInputBean.getContactReference())?"":", "+SqlHandler.delimitString(logInputBean.getContactReference()))
					.append(StringUtils.isEmpty(logInputBean.getContactName())?"":", "+SqlHandler.delimitString(logInputBean.getContactName())).append(")");
	        
	        factory.deleteInsertUpdate(stmt.toString(), conn);
	        
	        StringBuilder qStmt = new StringBuilder("insert into contact_log_catalog_queue"
	        		+ " VALUES(").append(nextLogId).append(",").append(logInputBean.getqId()).append(")");
	        
	        factory.deleteInsertUpdate(qStmt.toString(), conn);
		} finally {
			dbManager.returnConnection(conn);
		}
		return nextLogId;
	}
	
	public void updateContactLog(VendorContactLogInputBean logInputBean, PersonnelBean personnelBean) throws BaseException {
		StringBuilder stmt = new StringBuilder("update contact_log set ")
				.append("contact_datetime=sysdate");
		if ( ! StringUtils.isEmpty(logInputBean.getContactStatus())) {
			stmt.append(", contact_status=").append(SqlHandler.delimitString(logInputBean.getContactStatus()));
		}
		if ( ! StringUtils.isEmpty(logInputBean.getContactPurpose())) {
        	stmt.append(", contact_purpose=").append(SqlHandler.delimitString(logInputBean.getContactPurpose()));
		}
		if ( ! StringUtils.isEmpty(logInputBean.getContactLogType())) {
        	stmt.append(", contact_log_type=").append(SqlHandler.delimitString(logInputBean.getContactLogType()));
		}
		if ( ! StringUtils.isEmpty(logInputBean.getContactReference())) {
        	stmt.append(", contact_reference=").append(SqlHandler.delimitString(logInputBean.getContactReference()));
		}
		if ( ! StringUtils.isEmpty(logInputBean.getContactName())) {
        	stmt.append(", contact_name=").append(SqlHandler.delimitString(logInputBean.getContactName()));
		}
        stmt.append(" where contact_log_id="+logInputBean.getContactLogId());
        		
        factory.deleteInsertUpdate(stmt.toString());
	}

	public Collection<String> contactPurposeValues() throws BaseException {
		return contactLogProcess.contactPurposeValues();
	}
	
	public Collection<String> contactTypeValues() throws BaseException {
		return contactLogProcess.contactTypeValues();
	}
	
	public Collection<String> contactStatusValues() throws BaseException {
		return contactLogProcess.contactStatusValues();
	}
	
	public Collection<ContactLogDocumentViewBean> retrieveDocuments(VendorContactLogInputBean input) throws BaseException{
		return contactLogProcess.retrieveDocuments(input.getContactLogId());
	}
}
