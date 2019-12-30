package com.tcmis.internal.hub.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.SpecMisMatchDetailInputBean;
import com.tcmis.internal.hub.beans.SpecMisMatchDetailViewBean;
import com.tcmis.internal.hub.process.SpecMisMatchDetailProcess;




public class SpecMisMatchDetailAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "specmismatchdetailmain");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}  
		
		SpecMisMatchDetailProcess process = new SpecMisMatchDetailProcess(this.getDbUser(request),getTcmISLocaleString(request));
		// copy date from dyna form to the data bean
		SpecMisMatchDetailInputBean inputBean = new SpecMisMatchDetailInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/*Need to check if the user has permissions to view this page. if they do not have the permission
        we need to not show them the page.*/
		/*if (!personnelBean.getPermissionBean().hasUserPagePermission("supplierItemNotes")) {
			return (mapping.findForward("nopermissions"));
		}*/
		
		 if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction()))
         {
             Collection<SpecMisMatchDetailViewBean> SpecMisMatchColl = process.getSpecMismatchDetail(inputBean,personnelBean);
             request.setAttribute("specMismatchDetailColl", SpecMisMatchColl);
             return (mapping.findForward("success"));

         }
         else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction()))
         {
             this.setExcel(response, "SpecMismatchDetailExcel");
             process.getExcelReport(process.getSpecMismatchDetail(inputBean, personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
             return noForward;
        }
          
		
		return (mapping.findForward("success"));
	 }
}