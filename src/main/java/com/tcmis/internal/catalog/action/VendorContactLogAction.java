package com.tcmis.internal.catalog.action;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ContactLogHistoryBean;
import com.tcmis.internal.catalog.beans.VendorContactLogInputBean;
import com.tcmis.internal.catalog.process.VendorContactLogProcess;

public class VendorContactLogAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "vendorContactlog");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
			VendorContactLogInputBean input = new VendorContactLogInputBean();
			BeanHandler.copyAttributes((DynaBean) form, input);
			VendorContactLogProcess process = new VendorContactLogProcess(personnelBean.getPersonnelIdBigDecimal(), getDbUser());
			
			if (input.isSearch()) {
				doSearch(input, process);
			}
			else if (input.isSave()) {
				doSave(input, process, personnelBean);
				doSearch(input, process);
				request.setAttribute("loadAction", "clearForm");
			}
			else if (input.isViewDocumentAction()) {
				// if contact_log_id is null, then create new record, because documents must be associated with a contact_log_id
				request.setAttribute("contactLogId", input.getContactLogId());
				String savedContactStatus = input.getContactStatus();
				input.setContactStatus("");
				doSave(input, process, personnelBean);
				input.setContactStatus(savedContactStatus);
				doSearch(input, process);
				request.setAttribute("loadAction", "viewDocuments");
			}
			else {
				loadPage(input, process);
			}
		}
		return next;
	}
	
	private void doSave(VendorContactLogInputBean input, VendorContactLogProcess process, PersonnelBean personnelBean) throws BaseException {
		// if contact_log_id is null, then create new record, because documents must be associated with a contact_log_id
		if (input.hasContactLogId()) {
			process.updateContactLog(input, personnelBean);
		}
		else {
			request.setAttribute("contactLogId", process.insertContactLog(input, personnelBean));
		}
	}
	
	private void doSearch(VendorContactLogInputBean input, VendorContactLogProcess process) throws BaseException {
		Collection<ContactLogHistoryBean> logHistory = process.searchContactLogs(input);
		ContactLogHistoryBean draftLog = logHistory.stream().filter(l -> ! l.isFinalStatus()).findAny().orElse(null);
		if (input.isViewDocumentAction() && draftLog != null) {
			draftLog.setContactStatus(input.getContactStatus());
		}
		request.setAttribute("contactLogBean", draftLog);
		request.setAttribute("contactLogHistoryBeanCollection", logHistory.stream().filter(l -> l.isFinalStatus()).collect(Collectors.toList()));
		
		loadPage(input,process);
	}
	
	private void loadPage(VendorContactLogInputBean input, VendorContactLogProcess process) throws BaseException {
		request.setAttribute("contactPurposeColl", process.contactPurposeValues());
		request.setAttribute("contactStatusColl", process.contactStatusValues());
		request.setAttribute("contactTypeColl", process.contactTypeValues());
	}
}
