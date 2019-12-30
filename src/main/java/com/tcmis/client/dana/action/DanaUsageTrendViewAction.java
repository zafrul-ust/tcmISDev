package com.tcmis.client.dana.action;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.dana.beans.UsageTrendViewInputBean;
import com.tcmis.client.dana.beans.FacilityAniversaryDateViewBean;
import com.tcmis.client.dana.process.UsageTrendViewProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class DanaUsageTrendViewAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showusagetrend");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	/*if (form == null) {
	 this.saveToken(request);
	}*/
	//if form is not null we have to perform some action
	if (form != null) {
	 /*if (!this.isTokenValid(request, true)) {
		BaseException be = new BaseException("Duplicate form submission");
		be.setMessageKey("error.submit.duplicate");
		this.saveToken(request);
		throw be;
	 }
	 this.saveToken(request);*/

	 //copy date from dyna form to the data form
	 UsageTrendViewInputBean bean = new UsageTrendViewInputBean();
	 BeanHandler.copyAttributes(form, bean);
	 //check what button was pressed and determine where to go

	 if (bean.getUserAction() != null &&
		"getstartdates".equalsIgnoreCase(bean.getUserAction())) {
		UsageTrendViewProcess usageTrendViewProcess = new UsageTrendViewProcess(this.
		 getDbUser(request));
		Collection c = usageTrendViewProcess.getStartDates(bean);

		this.setSessionObject(request, c,
		 "facilityAniversaryDateViewBeanCollection");
		return mapping.findForward("showinput");
	 }
	 else if (bean.getSubmitSearch() != null &&
		bean.getSubmitSearch().trim().length() > 0) {
		/*//The page only shows the facility, but I need inventory groups associated with that facility to get the correct results
		 //from the UsageTrendViewProcess. Currently it is one facility to one inventory group so this will work
		 //will have to get sophisticated if there are more than one inventory group for a facility.
		 //I am going through the invoice dates bean to get the inventory group and setting it in the input bean before I send the input
		 //bean to the Process.
		 Collection invociePeiordColl = (Collection)this.getSessionObject(
			request, "facilityAniversaryDateViewBeanCollection");
		 Iterator i11 = invociePeiordColl.iterator();
		 String inventoryGroup = "";
		 while (i11.hasNext()) {
		FacilityAniversaryDateViewBean facilityAniversaryDateViewBean = (
		 FacilityAniversaryDateViewBean) i11.next(); ;
		inventoryGroup = facilityAniversaryDateViewBean.getInventoryGroup();
		 }
		 bean.setInventoryGroup(inventoryGroup);*/
		 bean.setInventoryGroup("All");
		 bean.setUnitsOrDollars("Y");
		 bean.setCompanyId(this.getDbUser(request));

		UsageTrendViewProcess usageTrendViewProcess = new UsageTrendViewProcess(this.
		 getDbUser(request));
		Collection c = usageTrendViewProcess.getsearchResult(bean);

		this.setSessionObject(request, c, "usageTrendViewCollection");

		return (mapping.findForward("showdetails"));
	 }
	 else if (bean.getButtonCreateExcel() != null &&
		bean.getButtonCreateExcel().trim().length() > 0) {

		UsageTrendViewProcess usageTrendViewProcess = new UsageTrendViewProcess(this.
		 getDbUser(request));

		ResourceLibrary resource = new ResourceLibrary("report");

		File dir = new File(resource.getString("excel.report.serverpath"));
		File file = File.createTempFile("excel", ".xls", dir);

		usageTrendViewProcess.writeExcelFile( (Collection)this.getSessionObject(
		 request, "usageTrendViewCollection"), file.getAbsolutePath(),
		 bean.getAniversaryDate(),(java.util.Locale)request.getSession().getAttribute("tcmISLocale"));
		this.setSessionObject(request,
		 resource.getString("report.hosturl") +
		 resource.getString("excel.report.urlpath") + file.getName(), "filePath");

		request.setAttribute("doexcelpopup", "Yes");

		return (mapping.findForward("showdetails"));
	 }
	 //now I can pass the collection of true data beans to the process
	 return (mapping.findForward("showinput"));
	}
	else {
	 UsageTrendViewProcess usageTrendViewProcess = new UsageTrendViewProcess(this.
		getDbUser(request));
	 Collection c = usageTrendViewProcess.getDistinctFacilities();

	 this.setSessionObject(request, c, "facilityBeanCollection");
	}

	return mapping.findForward("showinput");
 }
}
