package com.tcmis.internal.distribution.action;

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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerCarrierInfoBean;
import com.tcmis.internal.distribution.process.CustomerCarrierProcess;

/******************************************************************************
 * Controller for NewRemitTo
 * @version 1.0
 ******************************************************************************/

public class CustomerCarrierAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception	{


		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "customercarriermain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		


		/*if (!personnelBean.getPermissionBean().hasUserPagePermission("customercarrier"))
		{
			return (mapping.findForward("nopermissions"));
		}*/

		CustomerCarrierInfoBean inputBean = new CustomerCarrierInfoBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		CustomerCarrierProcess process = new CustomerCarrierProcess(this.getDbUser(request),getTcmISLocaleString(request));

		request.setAttribute("valueElementId",request.getParameter("valueElementId"));
		request.setAttribute("displayElementId",request.getParameter("displayElementId"));	
		request.setAttribute("displayAccount",request.getParameter("displayAccount"));
		
		if (inputBean.getAction() != null && "search".equals(inputBean.getAction())) 
		{    	  
			Collection <CustomerCarrierInfoBean> carrierInfoViewBeanCollection = process.getCarrierInfoViewBeanCollection(inputBean);
			request.setAttribute("carrierInfoViewBeanColl", carrierInfoViewBeanCollection);			
			this.saveTcmISToken(request);
		}

		else if (inputBean.getAction() != null && "newcarrier".equals(inputBean.getAction())) 
		{	 
			//process.createNewCarrier(inputBean);
			request.setAttribute("fromCustomerDetail", request.getParameter("fromCustomerDetail"));
			request.setAttribute("customerId", request.getParameter("customerId"));
			return (mapping.findForward("shownewcarrier"));
			
		}

		else if (inputBean.getAction() != null && "newcarrieraccount".equals(inputBean.getAction())) 
		{	 
			request.setAttribute("carrierNameCollection", process.getVvCarrier());			
			request.setAttribute("fromCustomerDetail", request.getParameter("fromCustomerDetail"));
			request.setAttribute("customerId", request.getParameter("customerId"));
			return (mapping.findForward("shownewcarrieraccount"));
		}
		else if (inputBean.getAction() != null && "addNewCarrier".equals(inputBean.getAction())) 
		{ 

			Collection errorMsgs = process.createNewCarrier(inputBean);
			
			request.setAttribute("tcmISErrors", errorMsgs); 
			request.setAttribute("fromCustomerDetail", request.getParameter("fromCustomerDetail"));
			request.setAttribute("customerId", request.getParameter("customerId"));

			if(errorMsgs.isEmpty() )
			{
				request.setAttribute("closeNewCarrierWin", "Y");
					
			}
			else
			{
				request.setAttribute("closeNewCarrierWin", "N");				
			}
			
				
			return (mapping.findForward("shownewcarrier"));
		}

		else if (inputBean.getAction() != null && "addNewCarrierAccount".equals(inputBean.getAction())) 
		{	 

			Object[] results = process.createNewCarrierAccount(inputBean);

			Collection errorMsgs = (Collection) results[0];

			Boolean validFlag = (Boolean) results[1];


			if(errorMsgs.isEmpty() && validFlag.booleanValue())
			{
				
				if("Yes".equals(inputBean.getFromCustomerDetail()))
				{
					request.setAttribute("closeCustomerDetailWin", "Y");
					request.setAttribute("closeNewAcctWin", "N");
				}
				else
				{
					request.setAttribute("closeNewAcctWin", "Y");	
					request.setAttribute("closeCustomerDetailWin", "N");
				}
				
				request.setAttribute("carrierAccountId", results[2]);
			}
			else
			{
				request.setAttribute("closeNewAcctWin", "N");	
				request.setAttribute("closeCustomerDetailWin", "N");
			}
			request.setAttribute("carrierNameCollection", process.getVvCarrier());	

			request.setAttribute("tcmISErrors", errorMsgs);

			return (mapping.findForward("shownewcarrieraccount"));
		}


		return (mapping.findForward("success"));
	}



}
