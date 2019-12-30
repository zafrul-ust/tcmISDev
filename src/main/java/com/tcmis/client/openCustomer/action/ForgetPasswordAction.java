package com.tcmis.client.openCustomer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.openCustomer.process.ForgetPasswordProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

public class ForgetPasswordAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
/*
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "changepassword");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    //If you need access to who is logged in
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");  */
	ForgetPasswordProcess process = new ForgetPasswordProcess(this.getDbUser(request));
    if (form != null) {
      this.saveTcmISToken(request);
      //validate token
      if (((DynaBean) form).get("uAction") != null && "newPassword".equalsIgnoreCase(((String) ((DynaBean) form).get("uAction")))) {
    	  String newPassword = process.getPassword();
    	  
    	  String logonId = (String) ((DynaBean) form).get("logonId");
      	  String errMsg = process.createNewPassword(logonId, newPassword,"RESET");
      	  
      	  if(errMsg != null && errMsg.length() > 0 && !"ok".equalsIgnoreCase(errMsg))
      		request.setAttribute("tcmISError", errMsg);
      	  else
      		request.setAttribute("updateStatus", "success");  
      }
    }
    return (mapping.findForward("success"));
  }
}
