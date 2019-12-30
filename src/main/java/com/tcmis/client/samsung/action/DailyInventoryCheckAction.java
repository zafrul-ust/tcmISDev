package com.tcmis.client.samsung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.samsung.process.OrderFulfillmentProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for DailyInventoryCheckAction
 * @version 1.0
	******************************************************************************/
public class DailyInventoryCheckAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
        //call the process here
        OrderFulfillmentProcess process = new OrderFulfillmentProcess(this.getDbUser(request),true);
        process.dailyInventoryCheck(request.getParameter("companyId"));
        return mapping.findForward("success");
    }
}
