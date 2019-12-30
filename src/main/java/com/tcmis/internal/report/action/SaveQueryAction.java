package com.tcmis.internal.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.report.beans.UsersSavedQueriesBean;
import com.tcmis.internal.report.process.SaveQueryProcess;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class SaveQueryAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException {

    if (!this.isLoggedIn(request)) {
		 request.setAttribute("requestedPage", "showsavequery");
      return (mapping.findForward("login"));
    }
    UsersSavedQueriesBean bean = new UsersSavedQueriesBean();
    try {
      BeanHandler.copyAttributes(form, bean);
      bean.setCompanyId(this.getCompanyId(request));
      bean.setPersonnelId(this.getPersonnelId(request));
      //log.debug(bean.getCompanyId());
      //log.debug(bean.getDateSaved());
      //log.debug("" + bean.getPersonnelId());
      //log.debug(bean.getQuery());
      //log.debug(bean.getQueryName());
      //log.debug(bean.getQueryNameOld());
      SaveQueryProcess process = new SaveQueryProcess(this.
          getDbUser(request));
      process.saveQuery(bean);
    }
    catch (DbUpdateException be) {
      //a query by that exists
      if (log.isErrorEnabled()) {
        log.error("Error saving query");
      }
      be.setMessageKey("error.savequery.duplicate");
      //add data to request
      request.setAttribute("UsersSavedQueriesBean", bean);
      throw be;
    }
    catch (BaseException be) {
      if (log.isErrorEnabled()) {
        log.error("Error saving query");
      }
      be.setMessageKey("error.savequery.missingdata");
      throw be;
    }
    return (mapping.findForward("viewquery"));
  }
}
