package com.tcmis.internal.catalog.process;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.ContactLogDocumentInputBean;
import com.tcmis.internal.catalog.beans.ContactLogDocumentViewBean;

public class ContactLogDocumentProcess extends BaseProcess {

	private String client;
	private ResourceLibrary library;
	
	public ContactLogDocumentProcess(String client,String locale) {
		super(client,locale);
        library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
    }
	
	public String uploadDocument(ContactLogDocumentInputBean inputBean, PersonnelBean personnelBean) throws BaseException, Exception {
		ResourceLibrary webResources = new ResourceLibrary("contactdocument");
		String errorMessage = "";
		if (inputBean.getTheFile() != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			try {
				String filename = inputBean.getTheFile().getName();
				inputBean.setDocumentUrl(webResources.getLabel("contactlog.documentfile.path")+webResources.getLabel("contactlog.documentfile.urlpath")+filename);
				FileHandler.copy(inputBean.getTheFile(), new File(inputBean.getDocumentUrl()));
			}
			catch(Exception e) {
				e.printStackTrace();
				errorMessage = library.getLabel("error.subject");
			}
		}
		
		if (StringUtils.isEmpty(errorMessage)) {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ContactLogDocumentInputBean());
			
			StringBuilder query = new StringBuilder("select DOCUMENT_SEQ.nextval from dual");
	        BigDecimal nextDocId = new BigDecimal(factory.selectSingleValue(query.toString()));
	        
	        String filename = inputBean.getTheFile().getName();
			inputBean.setDocumentUrl(filename);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormatPattern());
	        // order is document_id, contact_document_type, personnel_id, contact_log_id, document_url, document_date, insert_date, document_name
			StringBuilder stmt = new StringBuilder("insert into contact_document VALUES(");
			stmt.append(nextDocId+", ").append("'"+inputBean.getContactDocumentType()+"', ");
			stmt.append(personnelBean.getPersonnelId()+", ").append(inputBean.getContactLogId()+", ");
			stmt.append("'"+inputBean.getDocumentUrl()+"', ");
			stmt.append("TO_DATE('"+dateFormat.format(inputBean.getDocumentDate())+"', 'DD-MON-YYYY HH24:MI \"AM\"'), ");
			stmt.append("sysdate, ").append(SqlHandler.delimitString(inputBean.getDocumentName())+")");
			
			factory.deleteInsertUpdate(stmt.toString());
		}
		
		return errorMessage;
	}
	
	public Collection<ContactLogDocumentViewBean> retrieveDocumentList(ContactLogDocumentInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ContactLogDocumentViewBean());
		Collection<ContactLogDocumentViewBean> documentList = null;
		
		StringBuilder query = new StringBuilder("select cd.contact_document_type, ");
		query.append("fx_personnel_id_to_name(cd.personnel_id) personnel_name, ");
		query.append("cd.document_url, cd.document_date, cd.insert_date, cd.document_name, ");
		query.append("cd.contact_log_id, cd.document_id ");
		query.append("from contact_document cd where ");
		query.append("contact_log_id = "+inputBean.getContactLogId());
		query.append(" order by cd.insert_date");
		
		documentList = factory.selectQuery(query.toString());
		
		ResourceLibrary webResources = new ResourceLibrary("contactdocument");
		for (Iterator<ContactLogDocumentViewBean> it = documentList.iterator();it.hasNext();) {
			ContactLogDocumentViewBean bean = it.next();
			bean.setDocumentUrl(webResources.getLabel("contactlog.documentfile.hosturl")+webResources.getLabel("contactlog.documentfile.urlpath")+bean.getDocumentUrl());
		}
		
		return documentList;
	}
	
	public String getDateFormatPattern() {
		return library.getLabel("java.datetimeformat");
	}
	
	public void deleteDocument(ContactLogDocumentInputBean inputBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		
		StringBuilder stmt = new StringBuilder("delete from contact_document where ");
		stmt.append("contact_log_id = "+inputBean.getContactLogId());
		stmt.append(" and document_id = "+inputBean.getDocumentId());
		
		factory.deleteInsertUpdate(stmt.toString());
	}
}
