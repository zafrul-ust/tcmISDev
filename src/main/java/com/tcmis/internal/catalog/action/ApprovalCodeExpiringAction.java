package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.internal.catalog.process.ApprovalCodeExpiringProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for ApprovalCodeExpiringAction
 * @version 1.0
	******************************************************************************/
public class ApprovalCodeExpiringAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
        //call the process here
        ApprovalCodeExpiringProcess process = new ApprovalCodeExpiringProcess(this.getDbUser(request));
        process.processData();
        return mapping.findForward("success");
    }
}
