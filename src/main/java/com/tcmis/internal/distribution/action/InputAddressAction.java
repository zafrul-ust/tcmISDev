package com.tcmis.internal.distribution.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for customer requests
 * @version 1.0
     ******************************************************************************/
public class InputAddressAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "inputaddress");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
 
    //VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
    //request.setAttribute("vvCountryBeanCollection", vvDataProcess.getNormalizedCountryState());
    

    	return mapping.findForward("success");
  }
}