package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.WriteOnRequestInputBean;
import com.tcmis.internal.hub.process.WriteOnRequestProcess;

/******************************************************************************
 * Controller for NewRemitTo
 * @version 1.0
 ******************************************************************************/

public class WriteOnRequestAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception	{


		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "writeonrequest");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		


		/*if (!personnelBean.getPermissionBean().hasUserPagePermission("customercarrier"))
		{
			return (mapping.findForward("nopermissions"));
		}*/

		WriteOnRequestInputBean inputBean = new WriteOnRequestInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		WriteOnRequestProcess process = new WriteOnRequestProcess(this.getDbUser(request),getTcmISLocaleString(request));
		
		
	 if (inputBean.getAction() != null && "addNewRequest".equals(inputBean.getAction())) 
		{ 

			Collection errorMsgs = process.createRequest(inputBean,personnelBean );			
			request.setAttribute("tcmISErrors", errorMsgs);			

			if(errorMsgs.isEmpty() )
			{
				request.setAttribute("closeNewRequestWin", "Y");
					
			}
			else
			{
				request.setAttribute("closeNewRequestWin", "N");				
			}			
			
		}
		


		return (mapping.findForward("success"));
	}



}
