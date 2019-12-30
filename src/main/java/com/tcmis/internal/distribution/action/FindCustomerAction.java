package com.tcmis.internal.distribution.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.process.FindCustomerProcess;
import com.tcmis.common.admin.beans.AutocompleteInputBean;


public class FindCustomerAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		FindCustomerProcess process = new FindCustomerProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
//		 copy date from dyna form to the data bean
		AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		
        Collection<CustomerAddressSearchViewBean> results =  process.getCustomerColl(inputBean);
        
        // Write out the data
        response.setCharacterEncoding("utf-8");	 
        PrintWriter out = response.getWriter();
        // status needs to be in the third position or just id:name with no status
        // If we need to add different values, please leave the third one empty like id:name::something else 
    	for(CustomerAddressSearchViewBean bean: results)
    		out.println(bean.getCustomerId()+":"+bean.getCustomerName()+"::"+bean.getAbcClassification()+":"+bean.getBillToCompanyId());   		
		out.close();
		return noForward;	
	}

}
