package com.tcmis.client.report.action;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.report.process.SpecificListManagementProcess;
import com.tcmis.client.report.beans.ListManagementInputBean;
import com.tcmis.client.report.beans.ListManagementViewBean;


/**
 * Controller for list Management.
 */

public class SpecificListManagementAction extends TcmISBaseAction {
	
	private void doSearch(SpecificListManagementProcess process, ListManagementInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		//Collection<PoExpediteViewBean> results = process.getSearchResult(input, user);
		//input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("listChemicalColl",process.getListChemicals(inputBean,personnelBean));
		request.setAttribute("uAction","search");
		request.setAttribute("thresholdColNames",process.getThresholdColNames(inputBean));
		// save the token if update actions can be performed later.
		saveTcmISToken(request);
	}
	

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "listmanagementmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
		 if ( !personnelBean.getPermissionBean().hasUserPagePermission("listManagement") )
		    {
		      return (mapping.findForward("nopermissions"));
		    }

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			ListManagementInputBean inputBean = new ListManagementInputBean();
			BeanHandler.copyAttributes(form, inputBean);

			SpecificListManagementProcess process = new SpecificListManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
			if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction()))
	        {
				request.setAttribute("startSearchTime", new Date().getTime());
				doSearch(process,inputBean,personnelBean);
				request.setAttribute("endSearchTime", new Date().getTime());
	            return (mapping.findForward("success"));
	        }
			else if (inputBean.getuAction() !=null &&  "updateSpecificList".equals(inputBean.getuAction()))
	        {
				checkToken(request);
				if (!personnelBean.getPermissionBean().hasPermission("editCustomerList", null)){
					request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
					request.setAttribute("listChemicalColl",process.getListChemicals(inputBean,personnelBean));
					return (mapping.findForward("success"));
				}				
				ListManagementViewBean bean = new ListManagementViewBean();
				Collection<ListManagementViewBean> beans;
				try {
					beans = BeanHandler.getBeans((DynaBean) form, "listManagementViewBean", bean,getTcmISLocale(request));
					
					Collection errorMsgs =  process.updateSpecificList(beans, personnelBean);
					if(errorMsgs != null && errorMsgs.size() > 0){
					request.setAttribute("tcmISErrors", errorMsgs); 
					}
		         else
	                {
	                    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale(request));
	                    Vector updateMsg = new Vector();
	                    updateMsg.add(library.getString("label.updatewarning"));
	               	 	request.setAttribute("tcmISErrors",updateMsg);
	                }
				} catch (Exception e) {
					// TODO Auto-generated catch block				
					request.setAttribute("tcmISErrors", e.toString());
				}
				 
				doSearch(process,inputBean,personnelBean);
				 //request.setAttribute("listChemicalColl",process.getListChemicals(inputBean,personnelBean));
          	 	 
          		 return (mapping.findForward("success"));
	           
	       }				
	         else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction()))
	         {
	             this.setExcel(response, "ListChemicalExcel");
	             process.getExcelReport(inputBean,process.getListChemicals(inputBean,personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	             return noForward;
	        }
		}
		return mapping.findForward("success");
	}
}