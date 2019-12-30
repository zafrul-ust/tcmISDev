package com.tcmis.client.catalog.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.process.LookupOrderNameProcess;
import com.tcmis.common.admin.beans.AutocompleteInputBean;


public class LookupOrderNameAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		LookupOrderNameProcess process = new LookupOrderNameProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
		// copy date from dyna form to the data bean
		AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
        
        Collection<String> results =  process.getSimilarOrderNames(inputBean);
        
        // Write out the data
        response.setCharacterEncoding("utf-8");	  
        PrintWriter out = response.getWriter();
    	for(String id: results)
    		out.println(id);
		out.close();
		return noForward;	
	}
}
