package com.tcmis.client.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.AddEditSynonymProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for Add/Edit Synonym page
 * @version 1.0
 ******************************************************************************/

public class AddEditSynonymAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		
		if (!this.isLoggedIn(request)) 
			request.setAttribute("updatePermission","N");
		else 
			request.setAttribute("updatePermission","Y");
		
		String uAction = request.getParameter("uAction");
		String facilityId = request.getParameter("facilityId");
		String materialId = request.getParameter("materialId");
		
        AddEditSynonymProcess process = new AddEditSynonymProcess(this.getDbUser(request),getTcmISLocaleString(request));
		
        if ("login".equals(uAction)) {
        	if (!this.isLoggedIn(request)) {
	        	request.setAttribute("requestedPage", "addeditsynonym");
				request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
        	}
        	else
        		request.setAttribute("synonymText",request.getParameter("synonymText"));
        } else if ("update".equals(uAction)) {
            PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
            String originalSynonymText = request.getParameter("originalSynonymText");
            String synonymText = request.getParameter("synonymText");
            String errMsg = process.updateSynonymText(facilityId, materialId, originalSynonymText, synonymText, personnelBean);
            if(errMsg.length() > 0 && !"ok".equalsIgnoreCase(errMsg))
            	request.setAttribute("errorMsg",errMsg);
            else
            	request.setAttribute("done","Y");
        }else {
            //load initial data
        	
        	request.setAttribute("synonymText",process.getSynonymText(facilityId, materialId));
        }
		return (mapping.findForward("success"));
	}
}   //end of class