package com.tcmis.internal.catalog.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.DocumentBean;
import com.tcmis.internal.catalog.beans.MfrNotDocumentInputBean;
import com.tcmis.internal.catalog.process.MfrNotificationDocumentProcess;
import com.tcmis.internal.catalog.process.MfrNotificationMgmtProcess;

public class MfrNotificationDocumentAction extends TcmISBaseAction {

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
			MfrNotificationDocumentProcess process = new MfrNotificationDocumentProcess(this.getDbUser(), this.getTcmISLocaleString(request));
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
			MfrNotDocumentInputBean inputBean = new MfrNotDocumentInputBean();
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
				Collection<DocumentBean> documentList = process.retrieveDocumentList(inputBean);
				request.setAttribute("documentList", documentList);
			}
			else if (StringUtils.equals(uAction,"deleteDocument")) {
				if (inputBean.getDocumentId() != null) {
					process.deleteDocument(inputBean, personnelBean);
				}
				next = noForward;
			}
			else if (StringUtils.equals(uAction,"undeleteDocument")) {
				if (inputBean.getDocumentId() != null) {
					process.deleteDocument(inputBean, personnelBean, true);
				}
				next = noForward;
			}
			else {
				MfrNotificationMgmtProcess mgmtProcess = new MfrNotificationMgmtProcess(this.getDbUser(request));
				request.setAttribute("categoryCollection", mgmtProcess.getNotificationCategories());
			}
		}
		return next;
	}
}
