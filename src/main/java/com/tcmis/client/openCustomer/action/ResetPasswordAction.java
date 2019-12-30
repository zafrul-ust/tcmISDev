package com.tcmis.client.openCustomer.action;

import com.tcmis.common.admin.beans.ErrorBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.openCustomer.process.ChangePasswordProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

public class ResetPasswordAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "resetpassword");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    //If you need access to who is logged in
    if (form != null) {
      this.saveTcmISToken(request);
      //validate token
      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
      if (((DynaBean) form).get("action") != null && "resetPassword".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
    	  ChangePasswordProcess process = new ChangePasswordProcess(this.getDbUser(request), personnelBean);
        if (!this.isTcmISTokenValid(request, true)) {
          BaseException be = new BaseException("Duplicate form submission");
          be.setMessageKey("error.submit.duplicate");
          this.saveTcmISToken(request);
          throw be;
        }
        String newPassword = "";
	      String personnelId = "";
        if (((LazyValidatorActionForm) form).get("newPassword") != null) {
          newPassword = ((LazyValidatorActionForm) form).get("newPassword").toString();
        }
	      if (((DynaBean) form).get("personnelId") != null) {
          personnelId = ((DynaBean) form).get("personnelId").toString();
        }
	      request.setAttribute("personnelId",personnelId);
				PersonnelBean userBean = new PersonnelBean();
	      userBean.setPersonnelId((new BigDecimal(personnelId)).intValue());
	      userBean.setClearTextPassword(newPassword);
        ErrorBean errorBean = process.resetPassword(userBean, request.getLocale());
        if (!errorBean.getShowError()) {
          String forward = "success";
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
      }else {
	      request.setAttribute("personnelId",request.getParameter("personnelId"));
      }
    }else {
	    request.setAttribute("personnelId",request.getParameter("personnelId"));
    }
    return (mapping.findForward("success"));
  }
}
