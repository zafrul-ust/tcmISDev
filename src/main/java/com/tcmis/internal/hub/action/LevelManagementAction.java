package com.tcmis.internal.hub.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;

/******************************************************************************
 * Controller for level management
 * @version 1.0
     ******************************************************************************/
public class LevelManagementAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "levelmanagement");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    //populate drop downs

    if (form == null) {
      this.saveTcmISToken(request);
    }
    //if form is not null we have to perform some action
    else {
      if (!this.isTcmISTokenValid(request, true)) {
        BaseException be = new BaseException("Duplicate form submission");
        be.setMessageKey("error.submit.duplicate");
        throw be;
      }
      this.saveTcmISToken(request);

    }

    return (mapping.findForward("success"));
  }
}
