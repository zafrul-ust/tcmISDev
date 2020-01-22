package com.tcmis.internal.distribution.action;

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

import com.tcmis.internal.distribution.beans.CustomerReturnTrackingInputBean;
import com.tcmis.internal.distribution.process.CustomerReturnTrackingProcess;

public class CustomerReturnTrackingAction extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws BaseException, Exception {

    	if (!this.isLoggedIn(request)) {
    	      request.setAttribute("requestedPage", "customerreturntrackingmain");
    				request.setAttribute("requestedURLWithParameters",
    											 this.getRequestedURLWithParameters(request));
    	      return (mapping.findForward("login"));
    	}
  	
    	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    	if (!personnelBean.getPermissionBean().hasUserPagePermission("customerReturnTracking"))
  	    {
  	    return (mapping.findForward("nopermissions"));
  	    }
 
    
    	CustomerReturnTrackingInputBean inputBean = new CustomerReturnTrackingInputBean();
    	CustomerReturnTrackingProcess process = new CustomerReturnTrackingProcess(this.getDbUser(request),this.getTcmISLocale(request));
    	
    	    if (form == null) return (mapping.findForward("success"));
    	
    	    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
    	    String action =  (String)( (DynaBean) form).get("uAction");			//
    	    if ( "search".equals(action) ) {
    	    	request.setAttribute("customerReturnRequestViewCollection", process.getSearchResult(inputBean, personnelBean, true));
    	    }
    	    else if ( "createExcel".equals(action) ) {  	    	
    				this.setExcel(response,"CustomerReturnRequest");
    				process.getExcelReport(inputBean, personnelBean).write(response.getOutputStream());  			
    			return noForward;
    	    }
    	   
    	    return (mapping.findForward("success"));
    	}
  }