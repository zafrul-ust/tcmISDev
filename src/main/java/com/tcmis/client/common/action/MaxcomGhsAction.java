package com.tcmis.client.common.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class MaxcomGhsAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	 if (!this.isLoggedIn(request))	{
			request.setAttribute("requestedPage", "maxcomGhs");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
	}
	 
	PersonnelBean personnelBean = null;
    personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
    
    if (!personnelBean.getPermissionBean().hasUserPagePermission("maxcomSC"))
    {
      return (mapping.findForward("nopermissions"));
    }


	return mapping.findForward("success");
 }
}
