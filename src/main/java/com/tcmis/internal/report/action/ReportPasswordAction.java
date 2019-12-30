package com.tcmis.internal.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PasswordBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.PasswordProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.InvalidPasswordException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class ReportPasswordAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    //if (!this.isLoggedIn(request)) {
    //  return (mapping.findForward("login"));
    //}
    PasswordBean passwordBean = new PasswordBean();
    BeanHandler.copyAttributes(form, passwordBean);
    //add personnelId and logon
    //passwordBean.setLogon(this.getLogonId(request));
    //passwordBean.setPersonnelId(this.getPersonnelId(request));
    PasswordProcess process = new PasswordProcess(this.getDbUser(request),
                                                  (PersonnelBean)this.
                                                  getSessionObject(request,
        "personnelBean"));
    try {
      process.changePassword(passwordBean.getPasswordOld(),
                             passwordBean.getPasswordNew(),
                             passwordBean.getPasswordCopy());
    }
    catch (InvalidPasswordException pe) {
      BaseException be = new BaseException(pe);
      be.setMessageKey("error.password.invalid");
      throw be;
    }
    catch (BaseException ge) {
      BaseException be = new BaseException(ge);
      be.setMessageKey("error.password.change");
      throw be;
    }
    return (mapping.findForward("success"));
  }
}
