package com.tcmis.internal.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.report.beans.ErrorBean;
import com.tcmis.internal.report.beans.ExcelReportBean;
import com.tcmis.internal.report.beans.UsersSavedQueriesBean;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class RunReportRelayAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request,true)) {
      return (mapping.findForward("login"));
    }
    ExcelReportBean bean = new ExcelReportBean();
    try {
      BeanHandler.copyAttributes(form, bean);
      if (bean.getSubmit() != null && bean.getSubmit().length() > 0) {
        if (log.isDebugEnabled()) {
          log.debug("running report");
        }
        //put data in session and forward to relay jsp
        HttpSession session = request.getSession(false);
        session.setAttribute("excelReportBean", bean);
        return (mapping.findForward("reportrelay"));
      }
      else if (bean.getSaveQuery() != null && bean.getSaveQuery().length() > 0) {
        //show window where the user can save their query
        UsersSavedQueriesBean queryBean = new UsersSavedQueriesBean();
        queryBean.setQuery(bean.getQuery());
        request.setAttribute("UsersSavedQueriesBean", queryBean);
        return (mapping.findForward("showsavequery"));
      }
    }
    catch (com.tcmis.common.exceptions.BeanCopyException bce) {
      //error copying bean
      log.info("ERROR:" + bce.getMessage());
      BaseException bex = new BaseException(bce);
      bex.setMessageKey("error.bean.copy");
      throw bex;
    }
    catch (com.tcmis.common.exceptions.BaseException be) {
      BaseException bex = new BaseException(be);
      bex.setMessageKey("error.db.query");
      //since I want to display the oracle error I need to add it to the request
      ErrorBean errorBean = new ErrorBean();
      errorBean.setCause(be.getRootCause().getLocalizedMessage());
      request.setAttribute("errorBean", errorBean);
      throw bex;
    }
    return (mapping.findForward("home"));
  }

}
