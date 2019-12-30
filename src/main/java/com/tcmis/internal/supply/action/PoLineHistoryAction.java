package com.tcmis.internal.supply.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.PoLineHistoryViewBean;
import com.tcmis.internal.supply.process.PoLineHistoryProcess;

public class PoLineHistoryAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	  	
		if (!this.isLoggedIn(request)) {
	  		  request.setAttribute("requestedPage", "polinehistory".toLowerCase());
	  		  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
	  		  return (mapping.findForward("login"));
        }

	    /*Since there is no search section we consider this the start time. This should be done only for
	    * pages that have no search section.*/
	    request.setAttribute("startSearchTime", new Date().getTime() );
	    String forward = "success";
    
		String action = request.getParameter("Action");
		
		PoLineHistoryViewBean inputBean = new PoLineHistoryViewBean();
		
		if (form != null) {  	    	
			
			PoLineHistoryProcess process = new PoLineHistoryProcess(this.getDbUser(request));
			BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
			
			if (action.equals("view") )
			{
				Collection poLineHistoryViewBeanColl = process.getPoLineHistoryCollection(inputBean);
				log.debug("poLineHistoryViewBeanColl.size() = [" + poLineHistoryViewBeanColl.size() + "]; ");
		             
				request.setAttribute("poLineHistoryViewBeanColl", poLineHistoryViewBeanColl);
	
				this.saveTcmISToken(request);
			} 
		}
            		
		return (mapping.findForward(forward));
    }

}
