package com.tcmis.client.utc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.utc.process.AdvancePwaCatalogAddRequestProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class AdvancePwaCatalogAddRequestAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException {
		String userAction = request.getParameter("uAction");
		try {
			StringBuffer requestURL = request.getRequestURL();
			AdvancePwaCatalogAddRequestProcess process = new AdvancePwaCatalogAddRequestProcess(this.getDbUser(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			if ("resetAndCreate".equals(userAction) && !requestURL.toString().contains("www.tcmis.com"))
				request.setAttribute("catAddRequestId",process.resetAndCreateNewCatalogAddRequest());
			else if ("advanceRequest".equals(userAction))
				process.advanceCatalogAddRequest();
	    } catch (Exception e) {
	    	log.error("Advancing PWA Catalog Add Request failed: "+userAction, e);
	        MailProcess.sendEmail("deverror@haastcm.com", null,
	                              "deverror@haastcm.com",
	                              "Advancing PWA Catalog Add Request failed: "+userAction,
	                              "Advancing PWA Catalog Add Request failed.\n" + e.getMessage());
	    }
		return mapping.findForward("success");
	}
}
