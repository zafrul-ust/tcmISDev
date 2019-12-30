package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.OverrideApprovalBean;
import com.tcmis.internal.hub.process.OverrideApprovalProcess;

public class OverrideApprovalAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "success";
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "overrideapproval");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		OverrideApprovalProcess process = new OverrideApprovalProcess(this.getDbUser(request));
		OverrideApprovalBean input = new OverrideApprovalBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale());
		
		if (input.isSearch()) {
			request.setAttribute("overrideApprovalBean", process.view(input.getPickingUnitId(),input.getRid(),input.getPickingState()));
		}
		else if (input.isUpdate()) {
			process.update(input, user, request);
		}
		return mapping.findForward(forward);
	}
}
