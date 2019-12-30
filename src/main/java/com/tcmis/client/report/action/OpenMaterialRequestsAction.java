package com.tcmis.client.report.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.tcmis.client.report.beans.OpenMaterialRequestsInputBean;
import com.tcmis.client.report.beans.OpenMaterialRequestsViewBean;
import com.tcmis.client.report.process.OpenMaterialRequestsProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;


public class OpenMaterialRequestsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "openmaterialrequestsmain");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}  
		
		OpenMaterialRequestsProcess process = new OpenMaterialRequestsProcess(this.getDbUser(request),getTcmISLocaleString(request));
		// copy date from dyna form to the data bean
		OpenMaterialRequestsInputBean inputBean = new OpenMaterialRequestsInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
        PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction())) {
             Collection<OpenMaterialRequestsViewBean> openMaterialRequestsColl = process.getOpenMaterialRequests(inputBean,personnelBean);
             request.setAttribute("openMaterialRequestsColl", openMaterialRequestsColl);
             return (mapping.findForward("success"));

        } else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction())) {
             this.setExcel(response, "OpenMaterialRequestsExcel");
             process.getExcelReport(process.getOpenMaterialRequests(inputBean, personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale"),inputBean, personnelBean).write(response.getOutputStream());
             return noForward;
        } else {
        	 request.setAttribute("userFacilitiesColl", process.getFacilities(personnelBean));
        }

		return (mapping.findForward("success"));
	 }
}
