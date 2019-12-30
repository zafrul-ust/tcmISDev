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
import com.tcmis.internal.supply.beans.LppRangeBean;
import com.tcmis.internal.supply.beans.SuggestedSupplierBean;
import com.tcmis.internal.supply.process.ShowLppRangesProcess;
import com.tcmis.internal.supply.process.ShowSuggestedSupplierProcess;

/******************************************************************************
 * Controller for Show Schedule Tcm Buy page
 * @version 1.0
 ******************************************************************************/

public class ShowSuggestedSupplierAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "showlppranges");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		String action = request.getParameter("Action");	    
    	request.setAttribute("startSearchTime", new Date().getTime());
    	SuggestedSupplierBean inputBean = new SuggestedSupplierBean();
    	
    	if (form == null) {
			return (mapping.findForward("success")); 
		}
    	
    	BeanHandler.copyAttributes(form, inputBean);
    	
    	ShowSuggestedSupplierProcess process = new ShowSuggestedSupplierProcess(this.getDbUser(request));
    	      
		if (action != null && action.equalsIgnoreCase("showsuggestedsupplier")) {
			Collection<SuggestedSupplierBean> suggestedSupplierBeansCol = process.getSuggestedSupplierCol(inputBean);          
			
			request.setAttribute("suggestedSupplierBeansCol", suggestedSupplierBeansCol);
			this.saveTcmISToken(request);
		}
		
		request.setAttribute("endSearchTime", new Date().getTime() ); 
		return (mapping.findForward("success"));
    }
  
}
