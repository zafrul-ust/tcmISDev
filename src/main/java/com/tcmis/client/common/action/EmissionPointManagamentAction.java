package com.tcmis.client.common.action;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.EmissionPointManagementBean;
import com.tcmis.client.common.process.EmissionPointManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class EmissionPointManagamentAction extends TcmISBaseAction {
	private void doSearch(EmissionPointManagementProcess process, EmissionPointManagementBean input) throws BaseException, Exception {
		request.setAttribute("startSearchTime", new Date().getTime());
		Vector<EmissionPointManagementBean> results = process.getPageSearchResult(input);
		request.setAttribute("endSearchTime", new Date().getTime());
		input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("facAppEmissionPointBeanCollection", results);
		// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
		request.setAttribute("searchInput", input);
		// save the token if update actions can be performed later.
		this.saveTcmISToken(request);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "emissionPointManagement");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			return mapping.findForward("login");
		}
				
		//if form is not null we have to perform some action
		if (form != null) {
			// Get the user
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			
			EmissionPointManagementProcess process = new EmissionPointManagementProcess(this.getDbUser(request), this.getTcmISLocale(request));
			//copy date from dyna form to the data form
			EmissionPointManagementBean inputBean = new EmissionPointManagementBean(form, getTcmISLocale());
			if (inputBean.isSearch()) {
				
			} else if (inputBean.isUpdate()) {
				this.saveTcmISToken(request);
				Collection<EmissionPointManagementBean> beanColl = BeanHandler.getBeans((DynaBean) form, "facAppEmissionPointBean", new EmissionPointManagementBean(), getTcmISLocale(), "changed");
				request.setAttribute("tcmISError", process.updateFacAppEmissionPoint(inputBean, beanColl));
			}
			doSearch(process, inputBean);
		}
		
		return mapping.findForward("success");
	}
}