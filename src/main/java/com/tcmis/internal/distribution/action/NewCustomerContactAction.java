package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.CustomerContactBean;
import com.tcmis.internal.distribution.process.CustomerRequestProcess;
import com.tcmis.internal.distribution.process.CustomerUpdateProcess;

/******************************************************************************
 * Controller for supplier requests
 * @version 1.0
 ******************************************************************************/
public class NewCustomerContactAction
extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "newcustomercontact");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		String uAction = (String)( (DynaBean) form).get("uAction");

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		//	if (!personnelBean.getPermissionBean().hasUserPagePermission("newShipToAddress"))   {
		//         return (mapping.findForward("nopermissions"));
		//    }


		CustomerContactBean inputBean = new CustomerContactBean();
		BeanHandler.copyAttributes(form, inputBean);
		CustomerRequestProcess process = new CustomerRequestProcess(getDbUser(request),getLocale(request));
		CustomerUpdateProcess custUpdateProcess = new CustomerUpdateProcess(getDbUser(request),getLocale(request));
		//request.setAttribute("customerContactColl",cprocess.searchNewCustomerContact(inputBean.getCustomerId().toString()));
		request.setAttribute("vvJobFunctionCollection", process.getJobFunctionDropDown());

		if ("new".equals(uAction) ) {
			/*if (!personnelBean.getPermissionBean().hasFacilityPermission("newRemitTo",null,null))
      {
        request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
        VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
        Collection c = vvDataProcess.getNormalizedCountryState();
        request.setAttribute("vvCountryBeanCollection",c);
        return (mapping.findForward("success"));
     }*/


			Object[] results = custUpdateProcess.callCreateContact(inputBean,personnelBean);
			String err = results[0].toString();
			if (err.trim().length() > 0)
				request.setAttribute("tcmISError", err);
			else
				request.setAttribute("done", "Y");
			request.setAttribute("customerBean",inputBean );
			request.setAttribute("personnelId",results[1] );
		}

		if ("showRecord".equals(uAction) ) {

			Collection coll = custUpdateProcess.getCustomerContact(inputBean);
			if( coll.size() != 0 )
			{	
				request.setAttribute("customerContact",coll.toArray()[0]);


			}
			return mapping.findForward("success");
		}
		if ("modify".equals(uAction) ) {
			Collection coll = custUpdateProcess.getCustomerContact(inputBean);
			if( coll.size() != 0 )
			{	
				request.setAttribute("customerContact",coll.toArray()[0]);


			}


			Object[] results = custUpdateProcess.modifyCustomerContact(inputBean,personnelBean);
			String err = results[0].toString();
			if (err.trim().length() > 0)
				request.setAttribute("tcmISError", err);
			else
				request.setAttribute("done", "Y");
			request.setAttribute("customerBean",inputBean );
			request.setAttribute("personnelId",results[1] );


		}

		if ("addAndShow".equals(uAction) ) {       	
			Object[] results = custUpdateProcess.callCreateContact(inputBean,personnelBean);
			String err = results[0].toString();
			if (err.trim().length() > 0)
				request.setAttribute("tcmISError", err);
			else
				request.setAttribute("done", "Y");
			request.setAttribute("customerBean",inputBean );
			request.setAttribute("personnelId",results[1] );
			
			inputBean.setContactPersonnelId((BigDecimal) results[1]);
			
		    Collection coll = custUpdateProcess.getCustomerContact(inputBean);
		    if( (coll.size() != 0) && (err.trim().length()==0) )
			{	
 			 request.setAttribute("customerContact",coll.toArray()[0]);
   			}
		    
		    if("Yes".equals(request.getParameter("fromQuickCustomerView")))
		    	return mapping.findForward("getNewContact");
		
		}

		request.setAttribute("fromCustomerDetail", request.getParameter("fromCustomerDetail"));  
		request.setAttribute("fromContactLookup", request.getParameter("fromContactLookup"));   

		return mapping.findForward("success");
	}
}