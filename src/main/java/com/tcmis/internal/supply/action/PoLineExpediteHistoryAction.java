package com.tcmis.internal.supply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.PoLineExpediteHistoryViewBean;
import com.tcmis.internal.supply.process.PoLineExpediteHistoryProcess;
  
  public class PoLineExpediteHistoryAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws BaseException, Exception {

  	if (!this.isLoggedIn(request)) {
  		  request.setAttribute("requestedPage", "polineexpeditehistory".toLowerCase());
  		  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
  		  return (mapping.findForward("login"));
      }

    /*Since there is no search section we consider this the start time. This should be done only for
    * pages that have no search section.*/
    request.setAttribute("startSearchTime", new Date().getTime() );
    String forward = "success";

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    if (!personnelBean.getPermissionBean().hasUserPagePermission("openPos"))
    {
          return (mapping.findForward("nopermissions"));
    }

    PoLineExpediteHistoryViewBean inputBean = new PoLineExpediteHistoryViewBean();
    BeanHandler.copyAttributes(form, inputBean);

    PoLineExpediteHistoryProcess process = new PoLineExpediteHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
    String action = request.getParameter("action");
    if (action != null && "search".equals(action)) {
       request.setAttribute("poLineExpediteHistoryViewBeanColl",
                 process.getSearchResult(inputBean,personnelBean));
    }
    /*Since there is no search section we have to set end Time here as we cannot use the client side time.
    Client can be anywhere in the world.This should be done only for pages that have no search section.*/
    request.setAttribute("endSearchTime", new Date().getTime() );
    return (mapping.findForward(forward));
   }
  }