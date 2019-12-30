package com.tcmis.internal.supply.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.process.FindPoLineProcess;


public class FindBuyOrderPOLineQtyAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		FindPoLineProcess process = new FindPoLineProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
		MaterialRequestInputBean inputBean = new MaterialRequestInputBean();
		BeanHandler.copyAttributes(form, inputBean);
        String qty =  process.getBuyOrderPoLineQty(inputBean);
        
        PrintWriter out = response.getWriter();
        
    	out.println(qty);
    	
		out.close();
		return noForward;	
	}

}


