package com.tcmis.internal.invoice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for show login page
 * @version 1.0
     ******************************************************************************/
public class ShowLoginAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    PersonnelBean bean = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      try {
        bean = (PersonnelBean) session.getAttribute("personnelBean");
      }
      catch (Exception e) {
        //ignore
      }
    }

    if (bean == null) {
      return (mapping.findForward("showlogin"));
    }
    else {
      return (mapping.findForward("showindex"));
    }

//return (mapping.findForward("showinvoice"));
  }
}
