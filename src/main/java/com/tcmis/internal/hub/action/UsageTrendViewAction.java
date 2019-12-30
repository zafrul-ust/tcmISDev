package com.tcmis.internal.hub.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.UsageTrendViewInputBean;
import com.tcmis.internal.hub.process.MonthlyInventoryDetailProcess;
import com.tcmis.internal.hub.process.UsageTrendViewProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class UsageTrendViewAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "usagetrendmain");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
	 "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("costBookUsage"))
    {
      return (mapping.findForward("nopermissions"));
    }


	//if form is not null we have to perform some action
	if (form != null) {
	 //copy date from dyna form to the data form
	 UsageTrendViewInputBean bean = new UsageTrendViewInputBean();
	 BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
	 //check what button was pressed and determine where to go

	 if (bean.getSubmitSearch() != null &&
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

		UsageTrendViewProcess usageTrendViewProcess = new UsageTrendViewProcess(this.
		 getDbUser(request),getTcmISLocaleString(request));
		log.debug(bean);
		Collection c = usageTrendViewProcess.getsearchResult(bean);
		request.setAttribute("usageTrendViewCollection",c);

		return (mapping.findForward("success"));
	 }
	 else if (bean.getUserAction() != null &&
		bean.getUserAction().trim().equalsIgnoreCase("buttonCreateExcel")) {

		UsageTrendViewProcess usageTrendViewProcess = new UsageTrendViewProcess(this.
		 getDbUser(request),getTcmISLocaleString(request));
		Collection c = usageTrendViewProcess.getsearchResult(bean);
        
		this.setExcel(response,"Cost Book Usage");
		usageTrendViewProcess.writeExcelFile(c, bean.getAniversaryDate(),(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		return noForward;
/*		ResourceLibrary resource = new ResourceLibrary("report");

		File dir = new File(resource.getString("excel.report.serverpath"));
		File file = File.createTempFile("excel", ".xls", dir);

		usageTrendViewProcess.writeExcelFile( c, file.getAbsolutePath(),
		 bean.getAniversaryDate(),(java.util.Locale)request.getSession().getAttribute("tcmISLocale"));

		request.setAttribute("filePath",resource.getString("report.hosturl") +resource.getString("excel.report.urlpath") + file.getName());
		return (mapping.findForward("viewfile"));  */
	 }
	 else
	 {
		MonthlyInventoryDetailProcess monthlyInventoryDetailProcess = new
		 MonthlyInventoryDetailProcess(this.getDbUser(request),getTcmISLocaleString(request));
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		request.setAttribute("companyFacInvoiceDateOvBeanColl",
		 monthlyInventoryDetailProcess.getCompanyFacilityData(personnelId));
	 }
	 return (mapping.findForward("success"));
	}
	return mapping.findForward("success");
 }
}
