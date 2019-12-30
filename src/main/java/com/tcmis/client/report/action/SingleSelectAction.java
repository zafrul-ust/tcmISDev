package com.tcmis.client.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.report.beans.SingleSelectBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.client.report.process.SingleSelectProcess;

/******************************************************************************
 * Controller for single selection
 * @version 1.0
 ******************************************************************************/
public class SingleSelectAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "singleselectresults");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    SingleSelectBean inputBean = new SingleSelectBean();
    BeanHandler.copyAttributes(form, inputBean);
    SingleSelectProcess process = new SingleSelectProcess(this.getDbUser(request));
    request.setAttribute("singleSelectCollection", process.getData(inputBean));
    return mapping.findForward("success");
  }
}