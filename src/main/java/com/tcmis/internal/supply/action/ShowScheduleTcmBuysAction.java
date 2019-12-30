package com.tcmis.internal.supply.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.OpenScheduleBean;
import com.tcmis.internal.supply.process.ShowScheduleTcmBuysProcess;

/******************************************************************************
 * Controller for Show Schedule Tcm Buy page
 * @version 1.0
 ******************************************************************************/

public class ShowScheduleTcmBuysAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "showscheduletcmbuys");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		String action = request.getParameter("Action");	    
    	request.setAttribute("startSearchTime", new Date().getTime());
    	OpenScheduleBean inputBean = new OpenScheduleBean();
    	
    	if (form == null) {
			return (mapping.findForward("success")); 
		}
    	
    	BeanHandler.copyAttributes(form, inputBean);
    	
    	ShowScheduleTcmBuysProcess process = new ShowScheduleTcmBuysProcess(this.getDbUser(request));
    	      
		if (action != null && action.equalsIgnoreCase("showschedulde")) {
			Collection<OpenScheduleBean> showScheduleBeanCollection = process.getShowScheduleCollection(inputBean);          
			
			request.setAttribute("showScheduleBeanCollection", showScheduleBeanCollection);
			this.saveTcmISToken(request);
		}
		
		request.setAttribute("endSearchTime", new Date().getTime() ); 
		return (mapping.findForward("success"));
    }
  
}
