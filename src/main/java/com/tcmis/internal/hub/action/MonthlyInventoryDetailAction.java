package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CompanyFacInvoiceDateBean;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailInputBean;
import com.tcmis.internal.hub.process.MonthlyInventoryDetailProcess;
import com.tcmis.common.admin.beans.PersonnelBean;

public class MonthlyInventoryDetailAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	    request.setAttribute("requestedPage", "opsinvoiceinventorydetailmain");
		request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
		return (mapping.findForward("login"));
	}
	
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("opsInvoiceInventoryDetail"))
    {
      return (mapping.findForward("nopermissions"));
    }

	if( form == null ) 
		return (mapping.findForward("success"));

	MonthlyInventoryDetailProcess monthlyInventoryDetailProcess = new MonthlyInventoryDetailProcess(this.getDbUser(request),getTcmISLocaleString(request));

    //if form is not null we have to perform some action
    //copy date from dyna form to the data form
	MonthlyInventoryDetailInputBean bean = new MonthlyInventoryDetailInputBean();
	BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
	Collection hubInventoryGroupOvBeanCollection = ((PersonnelBean)this.getSessionObject(request,"personnelBean")).getHubInventoryGroupOvBeanCollection();
		  
	if (( (DynaBean) form).get("submitSearch") != null  &&
	  ((String)( (DynaBean) form).get("submitSearch")).trim().length() > 0) {
	  	Collection c = monthlyInventoryDetailProcess.getSearchResult(bean,hubInventoryGroupOvBeanCollection);
    this.setSessionObject(request, c,"monthlyInventoryDetailViewBeanCollection");
    return (mapping.findForward("success"));
	 }
	 else if (( (DynaBean) form).get("userAction") != null  &&
				  ((String)( (DynaBean) form).get("userAction")).trim().length() > 0) {
		this.setExcel(response,"opsInventory");		
		Collection c = monthlyInventoryDetailProcess.getSearchResult(bean,hubInventoryGroupOvBeanCollection);
		monthlyInventoryDetailProcess.writeExcelFile(c,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());	
		return noForward;
	  }
	 else
	 {
			Collection<CompanyFacInvoiceDateBean> coll = monthlyInventoryDetailProcess.getCompanyFacilityFlatData(personnelBean.getPersonnelIdBigDecimal());
			// Grouping data based on companyId and facilityId, so front-end's dropdown can use it.
			// The logic is in back-end to reduce burden on page.
			Vector<CompanyFacInvoiceDateBean> companyColl= new Vector<CompanyFacInvoiceDateBean>();
			CompanyFacInvoiceDateBean curCompanyBean = new CompanyFacInvoiceDateBean();
			CompanyFacInvoiceDateBean curFacilityBean = new CompanyFacInvoiceDateBean();
			Vector<CompanyFacInvoiceDateBean> facilityColl = new Vector<CompanyFacInvoiceDateBean>();
			Vector<CompanyFacInvoiceDateBean> endDateColl = new Vector<CompanyFacInvoiceDateBean>();
			Iterator<CompanyFacInvoiceDateBean> iterator = coll.iterator();
			boolean companyIdChanged = false;
			while (iterator.hasNext()) {
				CompanyFacInvoiceDateBean curBean = iterator.next();
				if (curCompanyBean.getCompanyId() == null || !curCompanyBean.getCompanyId().equals(curBean.getCompanyId())) {
					if (!StringHandler.isBlankString(curCompanyBean.getCompanyId())) {
						curCompanyBean.setFacilityColl(facilityColl);
						companyColl.addElement(curCompanyBean);
					}
					curCompanyBean = new CompanyFacInvoiceDateBean();
					curCompanyBean.setPersonnelId(curBean.getPersonnelId());
					curCompanyBean.setCompanyId(curBean.getCompanyId());
					curCompanyBean.setCompanyName(curBean.getCompanyName());
					
					facilityColl = new Vector<CompanyFacInvoiceDateBean>();
					
					companyIdChanged = true;
				}
				
				if (companyIdChanged || curFacilityBean.getFacilityId() == null || !curFacilityBean.getFacilityId().equals(curBean.getFacilityId())) {
					if (!StringHandler.isBlankString(curFacilityBean.getFacilityId())) {
						curFacilityBean.setInvoicePeriodColl(endDateColl);
						facilityColl.addElement(curFacilityBean);
					}
					curFacilityBean = new CompanyFacInvoiceDateBean();
					curFacilityBean.setFacilityId(curBean.getFacilityId());
					curFacilityBean.setFacilityName(curBean.getFacilityName());

					endDateColl = new Vector<CompanyFacInvoiceDateBean>();
					
					companyIdChanged = false;
				}
				
				CompanyFacInvoiceDateBean tempBean = new CompanyFacInvoiceDateBean();
				tempBean.setEndDate(curBean.getEndDate());
				tempBean.setInvoiceGroup(curBean.getInvoiceGroup());
				tempBean.setInvoicePeriod(curBean.getInvoicePeriod());
				
				endDateColl.addElement(tempBean);
				
				if (!iterator.hasNext()) {
					curFacilityBean.setInvoicePeriodColl(endDateColl);
					facilityColl.addElement(curFacilityBean);
					curCompanyBean.setFacilityColl(facilityColl);
					companyColl.addElement(curCompanyBean);
				}
			}
			request.setAttribute("companyFacInvoiceDateBeanColl", companyColl);
			
			return (mapping.findForward("success"));
	}	 
  }
}