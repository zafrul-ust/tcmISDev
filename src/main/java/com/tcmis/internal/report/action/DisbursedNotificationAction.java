package com.tcmis.internal.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.internal.report.process.DisbursedNotificationProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for DisbursedNotificationAction
 * @version 1.0
	******************************************************************************/
public class DisbursedNotificationAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
        //call the process here
        DisbursedNotificationProcess process = new DisbursedNotificationProcess(this.getDbUser(request),request.getRequestURL().substring(0,request.getRequestURL().indexOf("/tcmIS")));
        process.processData();
        return mapping.findForward("success");
    }
}
