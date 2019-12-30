package com.tcmis.internal.jde.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.jde.process.PartAvailabilityProcess;

public class PartAvailabilityAction extends TcmISBaseAction {
    
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
                @SuppressWarnings("hiding") HttpServletRequest request, HttpServletResponse response) 
            throws BaseException, Exception {

    	String partNumber = request.getParameter("partNumber"); 
        if ( partNumber == null) 
            return mapping.findForward("systemerrorpage");                    
          
        PartAvailabilityProcess partAvailabilityProcess = new PartAvailabilityProcess(this.getDbUser(request),getTcmISLocaleString(request));                  
                               
        //if form is not null we have to perform some action
        if (form != null && ((DynaBean) form).get("uAction") != null) {
                    	        		
        	if ("cachedResults".equalsIgnoreCase(((DynaBean) form).get("uAction").toString()) ) {
	        			            
	            partAvailabilityProcess.getCachedPartsAvailability(partNumber, request);        	        	
	        }
	        //check what button was pressed and determine where to go
	        else if ("search".equalsIgnoreCase(((String) ((DynaBean) form).get("uAction"))) ) {
	                                              
	          	partAvailabilityProcess.getIlsPartsAvailability(partNumber, request);	            	                             
	        } 
	        else
	            return mapping.findForward("systemerrorpage");	        	        	
        } 
        
        return mapping.findForward("success");
            
    } //end of method
} //end of class
