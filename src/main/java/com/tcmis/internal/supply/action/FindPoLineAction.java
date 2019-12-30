package com.tcmis.internal.supply.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.PoLineBean;
import com.tcmis.internal.supply.process.FindPoLineProcess;


public class FindPoLineAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		FindPoLineProcess process = new FindPoLineProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
		PoLineBean poLineBean = new PoLineBean();
		BeanHandler.copyAttributes(form, poLineBean);
        Collection<PoLineBean> results =  process.getPoLine(poLineBean);
        
        // Write out the data
        response.setCharacterEncoding("utf-8");	  
        PrintWriter out = response.getWriter();
        
    	for(PoLineBean bean: results)
    		out.println(bean.getQuantity()+":"+bean.getUnitPrice());
    	
		out.close();
		return noForward;	
	}

}


