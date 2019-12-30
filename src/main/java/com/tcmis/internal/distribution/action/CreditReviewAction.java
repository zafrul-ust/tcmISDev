package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerCreditBean;
import com.tcmis.internal.distribution.process.ScratchPadProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class CreditReviewAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "creditreview");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		/* if (!personnelBean.getPermissionBean().hasUserPagePermission("receiptSpec"))
       {
         return (mapping.findForward("nopermissions"));
       }*/

		String forward = "success";       

		String customerId = request.getParameter("customerId");
		String opsEntityId = request.getParameter("opsEntityId");
		
		if(StringHandler.isBlankString(customerId))
		{
			return mapping.findForward("genericerrorpage");
		}		
		
		
		ScratchPadProcess process = new ScratchPadProcess(this.getDbUser(request),getTcmISLocaleString(request));         
		
		// Search
		Vector<CustomerCreditBean> customerCreditColl = (Vector<CustomerCreditBean>)process.doCustomerCredit(new BigDecimal(customerId), opsEntityId);
    	request.setAttribute("customerCreditColl", customerCreditColl.get(0));

		
		return (mapping.findForward(forward));
	}
}
