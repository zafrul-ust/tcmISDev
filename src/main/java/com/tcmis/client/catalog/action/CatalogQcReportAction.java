package com.tcmis.client.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.process.CatalogQcReportProcess;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for CatalogQcAction
 * @version 1.0
	******************************************************************************/
public class CatalogQcReportAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
        //call the process here
        CatalogQcReportProcess process = new CatalogQcReportProcess(this.getDbUser(request));
        process.runCatalogQcReport(request.getParameter("companyId"));
        return mapping.findForward("success");
    }
}
