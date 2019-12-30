package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.catalog.process.CatalogAddReqCancellationProcess;

/******************************************************************************
 * Controller for CatalogAddReqCancellationAction
 * @version 1.0
	******************************************************************************/
public class CatalogAddReqCancellationAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
        //call the process here
        CatalogAddReqCancellationProcess process = new CatalogAddReqCancellationProcess(this.getDbUser(request));
        process.processData(request.getParameter("companyId"),request.getParameter("catalogSource"));
        return mapping.findForward("success");
    }
}
