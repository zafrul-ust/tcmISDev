package com.tcmis.internal.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.report.beans.UsersSavedQueriesBean;
import com.tcmis.internal.report.process.SaveQueryProcess;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class DeleteQueryAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException {

    if (!this.isLoggedIn(request)) {
      return (mapping.findForward("login"));
    }
    UsersSavedQueriesBean bean = new UsersSavedQueriesBean();
    BeanHandler.copyAttributes(form, bean);
    bean.setCompanyId(this.getCompanyId(request));
    bean.setPersonnelId(this.getPersonnelId(request));
    SaveQueryProcess process = new SaveQueryProcess(this.
        getDbUser(request));
    process.deleteQuery(bean);

    return (mapping.findForward("viewquery"));
  }
}
