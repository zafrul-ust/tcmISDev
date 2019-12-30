package com.tcmis.internal.catalog.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ContactLogDocumentInputBean;
import com.tcmis.internal.catalog.beans.ContactLogDocumentViewBean;
import com.tcmis.internal.catalog.process.ContactLogDocumentProcess;

public class ContactLogDocumentAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "contactlogdocument");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			ContactLogDocumentProcess process = new ContactLogDocumentProcess(this.getDbUser(), this.getTcmISLocaleString(request));
			request.setAttribute("dateFormatPattern", process.getDateFormatPattern());
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
			ContactLogDocumentInputBean inputBean = new ContactLogDocumentInputBean();
			BeanHandler.copyAttributes(form,inputBean,getLocale(request));
			String uAction = request.getParameter("uAction");
			if (StringUtils.equals(uAction,"uploadDocument")) {
				String errorMsg = process.uploadDocument(inputBean, personnelBean);
				if ( ! StringUtils.isEmpty(errorMsg)) {
					request.setAttribute("errorMessage", errorMsg);
					next = noForward;
				}
			}
			else if (StringUtils.equals(uAction,"view")) {
				Collection<ContactLogDocumentViewBean> documentList = process.retrieveDocumentList(inputBean);
				request.setAttribute("documentList", documentList);
			}
			else if (StringUtils.equals(uAction,"deleteDocument")) {
				if (inputBean.getContactLogId() != null) {
					process.deleteDocument(inputBean);
				}
				next = noForward;
			}
			else {
				VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request), getTcmISLocale(request));
				request.setAttribute("vvContactDocumentTypeBeanCollection", vvDataProcess.getVvContactDocumentType());
			}
			request.setAttribute("contactLogId", inputBean.getContactLogId());
		}
		return next;
	}
}
