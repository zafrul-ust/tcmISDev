package com.tcmis.internal.report.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.report.beans.ErrorBean;
import com.tcmis.internal.report.beans.UsersSavedQueriesBean;
import com.tcmis.internal.report.process.SaveQueryProcess;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class ViewQueryAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
 		  request.setAttribute("requestedPage", "showviewquery");
      return (mapping.findForward("login"));
    }
    try {
      SaveQueryProcess process = new SaveQueryProcess(getDbUser(request));
      UsersSavedQueriesBean queryBean = new UsersSavedQueriesBean();
      queryBean.setCompanyId(this.getCompanyId(request));
      queryBean.setPersonnelId(this.getPersonnelId(request));
      Collection c = process.getUserQueries(queryBean);
      request.setAttribute("usersSavedQueriesBeanCollection", c);
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
    return (mapping.findForward("showviewquery"));
  }
}
