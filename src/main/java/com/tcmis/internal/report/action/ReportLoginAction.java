package com.tcmis.internal.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class ReportLoginAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    //copy input into bean
    PersonnelBean personnelBean = new PersonnelBean();
    BeanHandler.copyAttributes(form, personnelBean);
    //login
    if (personnelBean != null && personnelBean.getClient() != null) {
      LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
      personnelBean = loginProcess.loginWeb(personnelBean);
      if (personnelBean != null) {
        if (log.isInfoEnabled()) {
          log.info("bean:" + personnelBean.getLogonId() + " - " +
                   personnelBean.getPersonnelId() + " - " +
                   personnelBean.getClient());
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("personnelBean", personnelBean);
        CompanyBean company = loginProcess.getCompany(personnelBean);
		session.setAttribute("timeout", company.getAppTimeout());
		session.setAttribute("timeoutWait", company.getAppTimeoutWait());
      }
      else {
        if (log.isDebugEnabled()) {
          log.debug("PersonnelBean is null!");
        }
        BaseException be = new BaseException();
        be.setRootCause(new Exception("Invalid login"));
        be.setMessageKey("error.login.invalid");
        //request.setAttribute(org.apache.struts.Globals.MESSAGE_KEY, "foo");
        throw be;
      }
    }
    else {
      BaseException be = new BaseException();
      be.setRootCause(new Exception("Invalid login"));
      be.setMessageKey("error.login.invalid");
      throw be;
    }
    return (mapping.findForward("success"));
  }
}
