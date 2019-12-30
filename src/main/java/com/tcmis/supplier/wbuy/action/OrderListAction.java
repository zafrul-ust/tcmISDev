package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.supplier.wbuy.beans.OrderListInputBean;
import com.tcmis.supplier.wbuy.beans.WbuyStatusViewBean;
import com.tcmis.supplier.wbuy.process.OrderListProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
* Controller for receiving
* @version 1.0
******************************************************************************/

/**
 * Change History
 * --------------
 * 03/23/09 - Shahzad Butt - Recreated the action to bring up search results as well 
 * 							 as populate excel spreadsheet with search results for a
 * 							 given search criteria.
 *
 */

public class OrderListAction extends TcmISBaseAction  {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "orderlistmain");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",	this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	//If you need access to who is logged in
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	/*Need to check if the user has permissions to view this page. if they do not have the permission
	we need to not show them the page.*/
	
	if (!personnelBean.getPermissionBean().hasUserPagePermission("wBuy"))
	  {
	    return (mapping.findForward("nopermissions"));
	  }

     String forward = "success";
	//if form is not null we have to perform some action
	 
	 //copy date from dyna form to the data bean
	 OrderListInputBean orderListInputBeanObj = new OrderListInputBean();
	 
	 BeanHandler.copyAttributes(form, orderListInputBeanObj, getTcmISLocale(request));
	 
	 // create new process.
	 OrderListProcess orderingProcessObj = null;
	 	 
	 // get supplier info for multi select.
	 orderingProcessObj = new OrderListProcess(this.getDbUser(request));
     request.setAttribute("supplierLocationOvBean",  orderingProcessObj.getSearchDropDown(personnelId));
    
	 /*in this example we are checking to see if the search submit button was clicked.
	 If the search button was clicked the getSubmitSearch() value will be not null*/
	 if (null!=orderListInputBeanObj.getAction() &&	"search".equals(orderListInputBeanObj.getAction())) {
	  		 
		 orderingProcessObj = new OrderListProcess(this.getDbUser(request));
		 
		 /*Pass the input bean to the process to get the resutlset from the database*/
		Collection <WbuyStatusViewBean>c = orderingProcessObj.getSearchResultsUsingSuppliers(orderListInputBeanObj);

		//Pass the result collection in request
		request.setAttribute("WbuyStatusViewBean",c);

		// save the token if update actions can be performed later.
		saveTcmISToken(request);
		return (mapping.findForward(forward));
	 }
	
	 else if (null!=orderListInputBeanObj.getAction() && "createExcel".equals(orderListInputBeanObj.getAction())) {
         this.setExcel(response, "PoOrderList");
         orderingProcessObj = new OrderListProcess(this.getDbUser(request));
         orderingProcessObj.getExcelReport(orderListInputBeanObj, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
         return noForward;
     }
	 
	
	

	return (mapping.findForward(forward));
}
 
}
