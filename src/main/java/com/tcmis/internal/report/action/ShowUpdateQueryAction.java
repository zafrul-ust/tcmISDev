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

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

public class ShowUpdateQueryAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      return (mapping.findForward("login"));
    }
    try {
      SaveQueryProcess process = new SaveQueryProcess(getDbUser(request));
      UsersSavedQueriesBean bean = new UsersSavedQueriesBean();
      BeanHandler.copyAttributes(form, bean);
      bean.setCompanyId(this.getCompanyId(request));
      bean.setPersonnelId(this.getPersonnelId(request));
      request.setAttribute("usersSavedQueriesBean", process.getUserQuery(bean));
    }
    catch (com.tcmis.common.exceptions.BaseException be) {
      BaseException bex = new BaseException(be);
      bex.setMessageKey("error.db.query");
      throw bex;
    }
    return (mapping.findForward("showupdatequery"));
  }

}
