package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
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

import com.tcmis.client.common.beans.*;
import com.tcmis.client.common.process.MrApproverDataProcess;



/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class MrApproverDataAction
	extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {
/*
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "mrapproverdata");
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}


	  	if( form == null )
	      	return (mapping.findForward("success"));
*/	  	
	  	String action = (String) ( (DynaBean)form).get("action");
	  	if( action == null ) return mapping.findForward("success");

//	  result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		MrApproverDataProcess process = new MrApproverDataProcess(this.getDbUser(request),getTcmISLocaleString(request));
	  	
	    if ( action.equals("searchLineItem") ) {

	    	BigDecimal prNumber = new BigDecimal(request.getParameter("prNumber").toString());
	    	String lineItem = request.getParameter("lineItem").toString();
	    	
	    	if (prNumber != null) {
	    		Collection requestLineItemColl = process.getApprover(prNumber, lineItem);
				  request.setAttribute("requestLineItemColl",requestLineItemColl);
	    	}	    	
	    } else if ( action.equals("searchPr") ) {
	    	BigDecimal prNumber = new BigDecimal(request.getParameter("prNumber").toString());
	    	
	    	if (prNumber != null) {
				  Collection purchaseRequestColl = process.getPurchaseRequestApprover(prNumber);
				  request.setAttribute("purchaseRequestColl",purchaseRequestColl);
	    	}	    	
	    }
	    else {/* set up search page data*/
	    	
	    }
	    return mapping.findForward("success");
	}
}
