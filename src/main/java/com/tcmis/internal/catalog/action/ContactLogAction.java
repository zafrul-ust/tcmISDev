package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ContactLogBean;
import com.tcmis.internal.catalog.beans.ContactLogDocumentViewBean;
import com.tcmis.internal.catalog.beans.ContactLogHistoryBean;
import com.tcmis.internal.catalog.beans.ContactLogMaterialBean;
import com.tcmis.internal.catalog.process.ContactLogProcess;

public class ContactLogAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "contactlog");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			ContactLogProcess process = new ContactLogProcess(this.getDbUser(), this.getTcmISLocaleString(request));
			SimpleDateFormat dateFormat = new SimpleDateFormat(process.getDateFormat());
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
			ContactLogBean clb = new ContactLogBean();
			BeanHandler.copyAttributes(form,clb,dateFormat.toPattern());//,getLocale(request));
			String uAction = request.getParameter("uAction");
			request.setAttribute("dateFormatPattern", dateFormat.toPattern());
			request.setAttribute("personnelId", personnelBean.getPersonnelId());
			if (uAction.equals("view")) {
				Collection<ContactLogMaterialBean> mdbc = new ArrayList<ContactLogMaterialBean>();
				clb.setCurrentMaterialId(clb.getMaterialId());
				clb.setCurrentRevisionDate(clb.getRevisionDate());
				Collection<ContactLogHistoryBean> logHistoryDataColl = process.retrieveLogHistory(clb);
				for (ContactLogHistoryBean bean : logHistoryDataColl) {
					// if a record is in draft status, then load it; we cannot have multiple drafts at once for the same material and revision date
                    //and the record does not come from Maxcom
                    if (("N".equals(bean.isFinalStatus()) || StringHandler.isBlankString(bean.getContactStatus())) && bean.getRecertAttemptId() == null) {
						clb.setContactLogId(bean.getContactLogId());
						clb.setContactName(bean.getContactName());
						clb.setContactLogType(bean.getContactLogType());
						clb.setContactPurpose(bean.getContactPurpose());
						clb.setContactReference(bean.getContactReference());
						clb.setContactStatus(bean.getContactStatus());
						clb.setNotes(bean.getNotes());
						mdbc = process.retrieveMsdsByLogId(clb);
						break;
					}
				}
				
				Collection<String> purposeColl = process.contactPurposeValues();
				Collection<String> statusColl = process.contactStatusValues();
				Collection<String> typeColl = process.contactTypeValues();
				
				clb.setContactPurposeColl(purposeColl);
				clb.setContactStatusColl(statusColl);
				clb.setContactTypeColl(typeColl);
				clb.setDraftStatus(process.findDraftStatus());
				
				if (mdbc.isEmpty()) {
					ContactLogMaterialBean clmb1 = new ContactLogMaterialBean();
					clmb1.setMaterialId(clb.getMaterialId());
					clmb1.setRevisionDate(clb.getRevisionDate());
					mdbc.add(clmb1);
				}
				clb.setMaterialDataColl(mdbc);
				request.setAttribute("contactLogBean", clb);
			}
			else if (uAction.equals("logHistory")) {
				if (clb.getCurrentMaterialId() != null && clb.getCurrentRevisionDate() != null) {
					clb.setLogHistoryDataColl(process.retrieveLogHistory(clb));
					
					process.createLogHistoryJasonObject(clb, response, dateFormat);
				}
		    	
		    	next = noForward;
			}
			else if (uAction.equals("msdsRevisionSelect")) {
				next = mapping.findForward("msdsRevisionSelect");
			}
			else if (uAction.equals("revisionDates")){
				if (clb.getMaterialId() != null) {
					Collection<ContactLogMaterialBean> revisionDates = process.retrieveRevisionDates(clb.getMaterialId());
					
					JSONObject docJSONData = new JSONObject();
					
			    	JSONArray resultsArray = new JSONArray();
					for (ContactLogMaterialBean date : revisionDates) {
						resultsArray.put(dateFormat.format(date.getRevisionDate()));
					}
					
					docJSONData.put("dates", resultsArray);
					
					// Write out the data
					PrintWriter out = response.getWriter();
					out.write(docJSONData.toString(3));
					out.close();
				}
		    	
		    	next = noForward;
			}
			else if (uAction.equals("submitLog")) {
				// if contact log id is null, then there is no record, so create a new one
				if (clb.getContactLogId() == null) {
					Collection<ContactLogMaterialBean> materialData = (Collection<ContactLogMaterialBean>)BeanHandler.getBeans((DynaBean) form, "materialDataDiv", 
							"datetimeformat", new ContactLogMaterialBean(), this.getTcmISLocale(request));
					clb.setMaterialDataColl(materialData);
					BigDecimal contactLogId = process.insertContactRecord(personnelBean, clb, dateFormat);
					if (contactLogId == null) {
						clearContactLogBean(clb);
					}
					else {
						clb.setContactLogId(contactLogId);
					}
				}
				// here we are modifying an old record
				else {
					Collection<ContactLogMaterialBean> materialData = (Collection<ContactLogMaterialBean>)BeanHandler.getBeans((DynaBean) form, "materialDataDiv", 
							"datetimeformat", new ContactLogMaterialBean(), this.getTcmISLocale(request));
					clb.setMaterialDataColl(materialData);
					BigDecimal contactLogId = process.reviseContactRecord(clb, dateFormat);
					if (contactLogId == null) {
						clearContactLogBean(clb);
					}
				}
				
				Collection<String> purposeColl = process.contactPurposeValues();
				Collection<String> statusColl = process.contactStatusValues();
				Collection<String> typeColl = process.contactTypeValues();
				
				clb.setContactPurposeColl(purposeColl);
				clb.setContactStatusColl(statusColl);
				clb.setContactTypeColl(typeColl);
				
				request.setAttribute("contactLogBean", clb);
			}
			else if (uAction.equals("viewDocuments")) {
				// if contact_log_id is null, then create new record, because documents must be associated with a contact_log_id
				if (clb.getContactLogId() == null) {
					Collection<ContactLogMaterialBean> materialData = (Collection<ContactLogMaterialBean>)BeanHandler.getBeans((DynaBean) form, "materialDataDiv", 
							"datetimeformat", new ContactLogMaterialBean(), this.getTcmISLocale(request));
					clb.setMaterialDataColl(materialData);
					String stat = clb.getContactStatus();
					clb.setContactStatus(process.findDraftStatus());
					BigDecimal contactLogId = process.insertContactRecord(personnelBean, clb, dateFormat);
					clb.setContactLogId(contactLogId);
					clb.setContactStatus(stat);
					Collection<String> purposeColl = process.contactPurposeValues();
					Collection<String> statusColl = process.contactStatusValues();
					Collection<String> typeColl = process.contactTypeValues();
					
					clb.setContactPurposeColl(purposeColl);
					clb.setContactStatusColl(statusColl);
					clb.setContactTypeColl(typeColl);
					request.setAttribute("contactLogBean", clb);
				}
				request.setAttribute("viewDocuments", "yes");
			}
			else if (uAction.equals("deleteMaterial")) {
				if (clb.getContactLogId() != null &&
						clb.getCurrentMaterialId() != null &&
						clb.getCurrentRevisionDate() != null) {
					process.deleteMaterial(clb, dateFormat);
				}
				next = noForward;
			}
		}
		return next;
	}
	
	private void clearContactLogBean(ContactLogBean bean) {
		bean.setContactLogId(null);
		bean.setContactName(null);
		bean.setContactLogType(null);
		bean.setContactPurpose(null);
		bean.setContactReference(null);
		bean.setContactStatus(null);
		bean.setNotes(null);
	}
}
