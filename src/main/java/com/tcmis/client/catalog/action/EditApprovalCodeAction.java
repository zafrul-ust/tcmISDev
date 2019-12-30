package com.tcmis.client.catalog.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.EditApprovalCodeInputBean;
import com.tcmis.client.catalog.beans.EditApprovalCodeViewBean;
import com.tcmis.client.catalog.process.EditApprovalCodeProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class EditApprovalCodeAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "success";
		
		// login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "editapprovalcodes".toLowerCase());
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		EditApprovalCodeInputBean input = new EditApprovalCodeInputBean();
        BeanHandler.copyAttributes(form, input, getTcmISLocale(request));
		if ( ! personnelBean.getPermissionBean().hasFacilityPermission("EditUseCodeExpiration", input.getFacilityId(), input.getCompanyId())) {
			return (mapping.findForward("nopermissions"));
		}
		
        EditApprovalCodeProcess process = new EditApprovalCodeProcess(this.getDbUser(request),getTcmISLocaleString(request));
        
        String uAction = input.getuAction();
        
        if ("submit".equalsIgnoreCase(uAction) || ("submitClose").equalsIgnoreCase(uAction)) {
        	Collection<EditApprovalCodeViewBean> editApprovalCodeBeanColl = BeanHandler.getBeans((DynaBean) form, "EditApprovalCodeBean", "dateformat", new EditApprovalCodeViewBean(), getTcmISLocale(request));
        	process.submitChanges(input, editApprovalCodeBeanColl);
        	if ("submit".equalsIgnoreCase(uAction)) {
        		uAction = "view";
        	}
        }
        
        if ("view".equalsIgnoreCase(uAction)) {
        	request.setAttribute("EditApprovalCodeBeanColl", process.viewApprovalCodes(input));
        }
        
        return mapping.findForward(forward);
	}
}
