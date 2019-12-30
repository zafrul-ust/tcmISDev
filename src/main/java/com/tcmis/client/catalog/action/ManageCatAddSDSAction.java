package com.tcmis.client.catalog.action;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.catalog.beans.ManageCatAddSDSInputBean;
import com.tcmis.client.catalog.process.ManageCatAddSDSProcess;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;

public class ManageCatAddSDSAction extends TcmISBaseAction {
	private void doSearch(ManageCatAddSDSProcess process, ManageCatAddSDSInputBean input) throws BaseException, Exception {
		request.setAttribute("startSearchTime", new Date().getTime());
		Vector<FileUploadBean> results = process.getSearchResult(input, request.getRequestURL().toString());
		input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("fileUploadBeanCollection", results);
		request.setAttribute("searchInput", input);
		request.setAttribute("endSearchTime", new Date().getTime());
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request, true)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "managecataddsds");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			return mapping.findForward("login");
		}
		
		ActionForward next = mapping.findForward("success");
		if (form != null) {
			ManageCatAddSDSProcess process = new ManageCatAddSDSProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			//copy date from dyna form to the data form
			ManageCatAddSDSInputBean inputBean = new ManageCatAddSDSInputBean(form, this.getTcmISLocale(request));
			if (inputBean.isUpdate()) {
				this.saveTcmISToken(request);
				Collection<FileUploadBean> beanColl = BeanHandler.getBeans((DynaBean) form, "fileUploadBean", new FileUploadBean(), getTcmISLocale(), "okDoUpdate");
				process.deleteUserUploadedMsds(beanColl, request.getRequestURL().toString());
			}
			
			request.setAttribute("editable", "y".equalsIgnoreCase(inputBean.getEditable()) && inputBean.getRequestId() != null ? "Y" : "N");
			doSearch(process, inputBean);
		}
		
		return next;
	}
}