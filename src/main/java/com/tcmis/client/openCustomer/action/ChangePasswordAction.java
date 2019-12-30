package com.tcmis.client.openCustomer.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.validator.LazyValidatorActionForm;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;

import com.tcmis.common.admin.beans.ErrorBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.openCustomer.process.ChangePasswordProcess;
import com.tcmis.common.util.EncryptHandler;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

public class ChangePasswordAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "changepassword");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }  
    //If you need access to who is logged in
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    ChangePasswordProcess process = new ChangePasswordProcess(this.getDbUser(request), personnelBean);
    if (form != null) {
      this.saveTcmISToken(request);
      //validate token
      if (((DynaBean) form).get("action") != null && "changePassword".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
    	try {
	    	if (!this.isTcmISTokenValid(request, true)) {
	          BaseException be = new BaseException("Duplicate form submission");
	          be.setMessageKey("error.submit.duplicate");
	          this.saveTcmISToken(request);
	          throw be;
	    	}
    	}
    	catch(Exception ex){}
    	  
        String oldPassword = "";
        String newPassword = "";
        byte[] salt = null;
        
        if (((LazyValidatorActionForm) form).get("oldPassword") != null) {
          oldPassword = ((LazyValidatorActionForm) form).get("oldPassword").toString();
        }
        if (((LazyValidatorActionForm) form).get("newPassword") != null) {
          newPassword = ((LazyValidatorActionForm) form).get("newPassword").toString();
          salt = EncryptHandler.generateSalt();
        }

        ErrorBean errorBean = process.changePassword(personnelBean, oldPassword, newPassword, salt, request.getLocale());
        if (!errorBean.getShowError()) {
        		personnelBean.setClearTextPassword(newPassword);
        		personnelBean.setPassword(EncryptHandler.pbkdf2Encrypt(newPassword, salt));
          	String forward = "success";

			 	String requestedURLWithParameters = (String) ((LazyValidatorActionForm) form).get("requestedURLWithParameters");
				if (requestedURLWithParameters == null  || requestedURLWithParameters.indexOf("application.do") != -1)
				{
					forward = "application";
				}

			  request.setAttribute("updateStatus","success");
          return (mapping.findForward(forward));
        }else {
          request.setAttribute("errorBean",errorBean);
          String forward = (String) ((LazyValidatorActionForm) form).get("requestedPage");
          if (forward != null) {
            request.setAttribute("requestedPage", forward); }
          String requestedURLWithParameters = (String) ((LazyValidatorActionForm) form).get("requestedURLWithParameters");
          if (requestedURLWithParameters != null) {
            request.setAttribute("requestedURLWithParameters", requestedURLWithParameters);
          }
        }
      }
    }
    return (mapping.findForward("success"));
  }
}
