package com.tcmis.internal.catalog.process;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.catalog.beans.DocumentBean;
import com.tcmis.internal.catalog.beans.MfrNotDocumentInputBean;

public class MfrNotificationDocumentProcess extends GenericProcess {

	private ResourceLibrary library;
	
	public MfrNotificationDocumentProcess(String client,String locale) {
		super(client,locale);
        library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
    }
	
	@SuppressWarnings("unchecked")
	public String uploadDocument(MfrNotDocumentInputBean inputBean, PersonnelBean personnelBean) throws BaseException, Exception {
		ResourceLibrary webResources = new ResourceLibrary("mfrnotdocument");
		String errorMessage = "";
		String documentUrl = "";
		if (inputBean.getTheFile() != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			try {
				String date = new SimpleDateFormat("yyyy/MM/").format(new Date());
				String urlPath = webResources.getLabel("mfrnot.documentfile.urlpath")+date;
				String absolutePath = webResources.getLabel("mfrnot.documentfile.path");
				String filename = getFilename(inputBean, absolutePath+urlPath);
				documentUrl = urlPath+filename;
				File dir = new File(absolutePath+urlPath);
				if ( ! dir.exists()) {
					dir.mkdirs();
				}
				FileHandler.copy(inputBean.getTheFile(), new File(absolutePath+documentUrl));
			}
			catch(Exception e) {
				log.error(e);
				errorMessage = library.getLabel("error.subject");
			}
		}

		
		try {
			@SuppressWarnings("rawtypes")
			Collection inArgs = new Vector(4);
			inArgs.add(inputBean.getNotificationId());
			inArgs.add(inputBean.getMfrReqCategoryId());
			inArgs.add(inputBean.getDocumentName());
			inArgs.add("Manufacturer Notification");
			inArgs.add("ManufacturerNotification");
			inArgs.add(inputBean.getDocumentDate());
			if ( ! documentUrl.startsWith("/")) {
				documentUrl = "/"+documentUrl;
			}
			inArgs.add(documentUrl);
			inArgs.add(personnelBean.getPersonnelIdBigDecimal());
			factory.doProcedure("pkg_manufacturer_notification.p_mfr_add_document",inArgs);
		}
		catch (Exception e) {
			log.error(e);
		}
		
		return errorMessage;
	}
	
	private String getFilename(MfrNotDocumentInputBean inputBean, String savePath) {
		return getFilename(inputBean, savePath, 1);
	}
	
	private String getFilename(MfrNotDocumentInputBean inputBean, String savePath, int cntr) {
		String fileName = "mfr_notification_" + inputBean.getNotificationId() + "-" + inputBean.getMfrReqCategoryId() + "-" + cntr + ".pdf";
		if (new File(savePath + fileName).exists()) {
			return getFilename(inputBean, savePath, cntr + 1);
		}
		return fileName;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DocumentBean> retrieveDocumentList(MfrNotDocumentInputBean inputBean) throws BaseException {
		Collection<DocumentBean> documentList = null;
		
		String excludeDeleted = "";
		if ( ! inputBean.isShowDeleted()) {
			excludeDeleted = " and status = 'A'";
		}
		
		String query = "select d.document_id, d.document_name, d.document_type, d.document_date"
				+ ", d.document_url, d.entered_on, d.entered_by, d.deleted_on, d.deleted_by, d.status"
				+ ", fx_personnel_id_to_name(d.entered_by) entered_by_name, fx_personnel_id_to_name(d.deleted_by) deleted_by_name"
				+ " from document d, mfr_notification_req_detail mnrd, mfr_notification_req_category mnrc"
				+ " where d.document_id = mnrd.document_id"
				+ " and mnrc.notification_id = " + inputBean.getNotificationId()
				+ " and mnrc.mfr_req_category_id = " + inputBean.getMfrReqCategoryId()
				+ " and mnrc.notification_category_id = mnrd.notification_category_id"
				+ excludeDeleted
				+ " order by d.document_name";
		
		
		documentList = factory.setBean(new DocumentBean()).selectQuery(query.toString());
		return documentList;
	}
	
	public String getDateFormatPattern() {
		return library.getLabel("java.datetimeformat");
	}
	
	public void deleteDocument(MfrNotDocumentInputBean inputBean, PersonnelBean user) throws BaseException, Exception {
		deleteDocument(inputBean, user, false);
	}
	
	public void deleteDocument(MfrNotDocumentInputBean inputBean, PersonnelBean user, boolean undelete) throws BaseException, Exception {
		String stmt = "";
		if (undelete) {
			stmt = "update document set status = 'A' where document_id = "+inputBean.getDocumentId();
		}
		else {
			stmt = "update document set status = 'I', deleted_on = sysdate, deleted_by = " + user.getPersonnelIdBigDecimal()
				+ " where document_id = " + inputBean.getDocumentId();
		}
		
		factory.deleteInsertUpdate(stmt);
	}
}
