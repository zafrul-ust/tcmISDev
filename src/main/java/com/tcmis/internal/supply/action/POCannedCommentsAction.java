package com.tcmis.internal.supply.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.supply.process.POCannedCommentsProcess;
//import com.tcmis.internal.supply.beans.POTextViewBean;

/******************************************************************************
 * Controller for Purchase Order Canned Comments page
 * @version 1.0
 ******************************************************************************/

public class POCannedCommentsAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "pocannedcommentsmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		if (form == null) 
		{  	    	
			return (mapping.findForward("success"));
		}
		
		POCannedCommentsProcess process = new POCannedCommentsProcess(this.getDbUser(request));
				
		Collection pOCannedCommentsBeanCollection = process.getPOCannedCommentsBeanCollection();
		request.setAttribute("pOCannedCommentsBeanCollection", pOCannedCommentsBeanCollection);

		this.saveTcmISToken(request);
		return (mapping.findForward("success"));
    } 
}
