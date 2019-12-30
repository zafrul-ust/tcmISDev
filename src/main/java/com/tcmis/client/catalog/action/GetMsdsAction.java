package com.tcmis.client.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CustomerMaterialNumberBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.process.GetMsdsProcess;
import com.tcmis.common.admin.beans.AutocompleteInputBean;


public class GetMsdsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		GetMsdsProcess process = new GetMsdsProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
//		 copy date from dyna form to the data bean
		AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
        
        Vector<CustomerMaterialNumberBean> results =  (Vector<CustomerMaterialNumberBean>)process.getMSDSColl(inputBean);
        
        // Write out the data
        response.setCharacterEncoding("utf-8");	  
        PrintWriter out = response.getWriter();
        
        // There is : in the trade_name. Therefore we use :: to pipe the vales. No status for this auto-complete since material_id is a BigDecimal type.

    	for (int i = 0;i < results.size();i++)
    	{
            String currMsds = results.get(i).getCustomerMsdsOrMixtureNo();
    		StringBuilder currLine = new StringBuilder(results.get(i).getCustomerMsdsOrMixtureNo()+"::"+results.get(i).getCustomerMsdsOrMixtureNo()+":;~"+results.get(i).getMaterialId()+"::"+results.get(i).getMaterialDesc()+"::"+results.get(i).getCustomerMsdsNumber());
    		while(i + 1 < results.size() && currMsds.equalsIgnoreCase(results.get(i + 1).getCustomerMsdsOrMixtureNo()))
    			currLine.append(":;~"+results.get(++i).getMaterialId()+"::"+results.get(i).getMaterialDesc()+"::"+results.get(i).getCustomerMsdsNumber());
    		out.println(currLine.toString());  
    	}
    	
		out.close();
		return noForward;	
	}

}
