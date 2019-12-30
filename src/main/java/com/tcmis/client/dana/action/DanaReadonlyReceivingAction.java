package com.tcmis.client.dana.action;

import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.dana.process.DanaReadonlyReceivingProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.ReceivingProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class DanaReadonlyReceivingAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showreceiving");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if (form == null) {
	 //this.saveTcmISToken(request);
	}
	//if form is not null we have to perform some action
	if (form != null) {

	 //copy date from dyna form to the data form
	 ReceivingInputBean bean = new ReceivingInputBean();
	 BeanHandler.copyAttributes(form, bean);

	 /*if (!this.isTokenValid(request,
		true) && (bean.getUserAction() != null && bean.getUserAction().length() > 0)) {
		BaseException be = new BaseException("Duplicate form submission");
		be.setMessageKey("error.submit.duplicate");
		this.saveTcmISToken(request);
		throw be;
	 }
	 this.saveTcmISToken(request);*/

	 //check what button was pressed and determine where to go
	 if (bean.getSubmitSearch() != null &&
		bean.getSubmitSearch().trim().length() > 0) {

		/*//The page only shows the facility, but I need inventory groups associated with that facility to get the correct results
		//from the MonthlyInventoryDetailProcess. Currently it is one facility to one inventory group so this will work
		//will have to get sophisticated if there are more than one inventory group for a facility.
		//I am going through the invoice dates bean to get the inventory group and setting it in the input bean before I send the input
		//bean to the Process.
		Collection invociePeiordColl =  (Collection) this.getSessionObject(request,"facilityInvoicePeriodViewBeanCollection");
		Iterator i11=invociePeiordColl.iterator();
		String inventoryGroup = "";
		while ( i11.hasNext() )
		{
		FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean = (FacilityInvoicePeriodViewBean) i11.next();;
		inventoryGroup = facilityInvoicePeriodViewBean.getInventoryGroup();
		}
		bean.setInventoryGroup(inventoryGroup);*/

	 ReceivingProcess receivingProcess = new ReceivingProcess(this.getDbUser(
		request));

	 //get search result
	 Collection c = new Vector();
//		Collection c = receivingProcess.getChemicalResult(bean,false);
		//add result and pass it to the jsp page
		this.setSessionObject(request, c, "receivingViewBeanCollection");

		return (mapping.findForward("showresults"));
	 }
	 else if (bean.getUserAction() != null && bean.getUserAction().length() == 0) {
		DanaReadonlyReceivingProcess danaReadonlyReceivingProcess = new
		 DanaReadonlyReceivingProcess(this.getDbUser(request));
		Collection c = danaReadonlyReceivingProcess.getCustomerFacilityIgViewBean();

		this.setSessionObject(request, c, "customerFacilityIgViewBeanCollection");
	 }

	 return (mapping.findForward("showinput"));
	}
	else {
	 DanaReadonlyReceivingProcess danaReadonlyReceivingProcess = new
		DanaReadonlyReceivingProcess(this.getDbUser(request));
	 Collection c = danaReadonlyReceivingProcess.getCustomerFacilityIgViewBean();

	 this.setSessionObject(request, c, "customerFacilityIgViewBeanCollection");
	}

	return mapping.findForward("showinput");
 }
}
