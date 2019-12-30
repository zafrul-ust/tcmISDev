package com.tcmis.internal.supply.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.POCarrierInputBean;
//import com.tcmis.internal.supply.beans.CarrierInfoBean;
import com.tcmis.internal.supply.process.POCarrierProcess;

/******************************************************************************
 * Controller for Purchase Order Carrier search page
 * @version 1.0
 ******************************************************************************/

public class POCarrierAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "pocarriermain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		if (form == null) return (mapping.findForward("success"));

		POCarrierInputBean inputBean = new POCarrierInputBean();
		String uAction =  (String)( (DynaBean) form).get("uAction");	
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		POCarrierProcess process = new POCarrierProcess(this.getDbUser(request),getTcmISLocaleString(request));
		String inventoryGroup = (String) request.getParameter("inventoryGroup");
		String source = (String) request.getParameter("source");
		if ( "search".equals(uAction) ) 
		{    	  
			Collection carrierInfoBeanCollection = process.getCarrierInfoBeanCollection(inputBean, inventoryGroup, source);
             
			request.setAttribute("carrierInfoBeanCollection", carrierInfoBeanCollection);
			this.saveTcmISToken(request);
		} 
      
		return (mapping.findForward("success"));
    }
  
}
