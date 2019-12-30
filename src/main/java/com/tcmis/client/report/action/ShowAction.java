package com.tcmis.client.report.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/**
 * Forwards to "success" if user is logged in, otherwise to "login".
 */

public class ShowAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {


    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showreports");
      return (mapping.findForward("login"));
    }
    return (mapping.findForward("success"));
  }

}