package com.tcmis.client.openCustomer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.client.openCustomer.process.ContactUsProcess;

public class ContactUsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "contactus");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}   	

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		
		ContactUsProcess process = new ContactUsProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
		if( form == null )
	      	return (mapping.findForward("success"));

		String uAction = (String) ( (DynaBean)form).get("uAction");
		
		if ( "sendEmail".equals(uAction)) {
			String mrNumber = (String) ( (DynaBean)form).get("mrNumber");
			String customerServiceRepEmail = (String) ( (DynaBean)form).get("customerServiceRepEmail");
			String subject = (String) ( (DynaBean)form).get("subject");
			String msg = (String) ( (DynaBean)form).get("msg");
			process.sendRequest(personnelBean, mrNumber, customerServiceRepEmail, subject, msg);
			
			request.setAttribute("done","Y");
	    }
		else {
			request.setAttribute("fullName", personnelBean.getFirstName() + " " + personnelBean.getLastName());
		}

		return mapping.findForward("success");

	}

}
