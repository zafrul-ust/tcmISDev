package com.tcmis.client.catalog.action;

import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.client.catalog.process.FlowDownSearchProcess;

/******************************************************************************
 * Controller for flowdown Search page
 * @version 1.0
 ******************************************************************************/

public class FlowDownSearchAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "flowdownsearchmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		String userAction = request.getParameter("uAction");
		if (form == null || userAction == null)
		{    	 
			return (mapping.findForward("success"));
		}
	  	DynaBean dynaForm = (DynaBean) form;

	  	if ( userAction.equals("search") )
		{
			String searchArgument = (String) dynaForm.get("searchArgument");
			String catalogId = (String) dynaForm.get("catalogId");
			String companyId = (String) dynaForm.get("companyId");
			FlowDownSearchProcess process = new FlowDownSearchProcess(this.getDbUser(request));
			request.setAttribute("flowdownBeanCollection", process.getFlowDownBeanCollection( searchArgument,catalogId,companyId));
		}

		return (mapping.findForward("success"));
    }
  
}
