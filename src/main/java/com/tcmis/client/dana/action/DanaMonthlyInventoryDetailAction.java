package com.tcmis.client.dana.action;

import java.io.File;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailInputBean;
import com.tcmis.internal.hub.process.MonthlyInventoryDetailProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class DanaMonthlyInventoryDetailAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showmonthlyinventorydetail");
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
	 MonthlyInventoryDetailInputBean bean = new MonthlyInventoryDetailInputBean();
	 BeanHandler.copyAttributes(form, bean);

	 /*if (!this.isTokenValid(request,
		true) && (bean.getUserAction() != null && bean.getUserAction().length() > 0)) {
		BaseException be = new BaseException("Duplicate form submission");
		be.setMessageKey("error.submit.duplicate");
		this.saveTcmISToken(request);
		throw be;
	 }
	 this.saveTcmISToken(request);
   */

	 //check what button was pressed and determine where to go
	 if (bean.getUserAction() != null &&
		"getinvociedates".equalsIgnoreCase(bean.getUserAction())) {
		/*Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)this.getSessionObject(
			request, "hubInventoryGroupOvBeanCollection"));*/
		MonthlyInventoryDetailProcess monthlyInventoryDetailProcess = new
		 MonthlyInventoryDetailProcess(this.getDbUser(request));
		Collection c = monthlyInventoryDetailProcess.getCustomerInvoicePeriods(bean, null);

		this.setSessionObject(request, c, "facilityInvoicePeriodViewBeanCollection");
		return mapping.findForward("showinput");
	 }
	 else if (bean.getSubmitSearch() != null &&
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

	 /*Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)this.getSessionObject(
			request, "hubInventoryGroupOvBeanCollection"));*/
	 MonthlyInventoryDetailProcess monthlyInventoryDetailProcess = new
		MonthlyInventoryDetailProcess(this.getDbUser(request));
	 Collection c = monthlyInventoryDetailProcess.getSearchResult(bean, null);

		this.setSessionObject(request, c,
		 "monthlyInventoryDetailViewBeanCollection");

		return (mapping.findForward("showresults"));
	 }
	 else if (bean.getButtonCreateExcel() != null &&
		bean.getButtonCreateExcel().trim().length() > 0) {

		MonthlyInventoryDetailProcess monthlyInventoryDetailProcess = new
		 MonthlyInventoryDetailProcess(this.getDbUser(request));

		ResourceLibrary resource = new ResourceLibrary("report");

		File dir = new File(resource.getString("excel.report.serverpath"));
		File file = File.createTempFile("excel", ".xls", dir);

		monthlyInventoryDetailProcess.writeCoustomerExcelFile( (Collection)this.
		 getSessionObject(request, "monthlyInventoryDetailViewBeanCollection"),
		 file.getAbsolutePath(), (java.util.Locale)request.getSession().getAttribute("tcmISLocale"));

		this.setSessionObject(request,
		 resource.getString("report.hosturl") +
		 resource.getString("excel.report.urlpath") + file.getName(), "filePath");

		request.setAttribute("doexcelpopup", "Yes");
		return (mapping.findForward("showresults"));
	 }
	 return (mapping.findForward("showinput"));
	}
	else {
	 MonthlyInventoryDetailProcess monthlyInventoryDetailProcess = new
		MonthlyInventoryDetailProcess(this.getDbUser(request));
	 Collection c = monthlyInventoryDetailProcess.getFacilities();

	 this.setSessionObject(request, c, "facilityBeanCollection");
	}

	return mapping.findForward("showinput");
 }
}
