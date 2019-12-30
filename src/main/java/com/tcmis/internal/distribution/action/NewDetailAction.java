package com.tcmis.internal.distribution.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.AddSpecsBean;
import com.tcmis.internal.distribution.process.AddSpecsProcess;
import com.tcmis.internal.distribution.process.NewDetailProcess;

public class NewDetailAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "newdetail");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}	

		//Main
		String uAction = (String) ( (DynaBean)form).get("uAction");

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/* if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders",null,null,null))
         {           

             return (mapping.findForward("nopermissions"));
         }	*/

		//Process search
		AddSpecsProcess asProcess = new AddSpecsProcess(this.getDbUser(request),getTcmISLocaleString(request));
		NewDetailProcess process = new NewDetailProcess(this.getDbUser(request),getTcmISLocaleString(request));		

		AddSpecsBean inputBean = new AddSpecsBean();

		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

	    request.setAttribute("fromReceiptSpec", request.getParameter("fromReceiptSpec"));
	    request.setAttribute("fromSpecQC", request.getParameter("fromSpecQC"));
	    String fromSpecQC = request.getParameter("fromSpecQC");

		// Search
		if ("addDetail".equals(uAction))
		{
			String errMsg;
			
			if(fromSpecQC != null && fromSpecQC.equals("Yes")){
				String requestId = request.getParameter("requestId");
				String companyId = request.getParameter("companyId");
				errMsg = process.createNewDetailQC(inputBean, requestId, companyId);
			}
			else
				errMsg = process.createNewDetail(inputBean);
			
			if( errMsg != "" && errMsg != null && !"ok".equalsIgnoreCase(errMsg)) {
				request.setAttribute("tcmISError", errMsg);
			}
			else  
				request.setAttribute("closeNewSpecWin", "Y");
				
			request.setAttribute("specDetailTypeCol", asProcess.getSpecDetails("type"));
			request.setAttribute("specDetailClassCol", asProcess.getSpecDetails("class"));
			request.setAttribute("specDetailFormCol", asProcess.getSpecDetails("form"));
			request.setAttribute("specDetailGroupCol", asProcess.getSpecDetails("group"));
			request.setAttribute("specDetailGradeCol", asProcess.getSpecDetails("grade"));
			request.setAttribute("specDetailStyleCol", asProcess.getSpecDetails("style"));
			request.setAttribute("specDetailFinishCol", asProcess.getSpecDetails("finish"));
			request.setAttribute("specDetailSizeCol", asProcess.getSpecDetails("size"));
			request.setAttribute("specDetailColorCol", asProcess.getSpecDetails("color"));	
			
			return (mapping.findForward("success"));
		}
		else {
			if(fromSpecQC != null && fromSpecQC.equals("Yes")){
				String requestId = request.getParameter("requestId");
				String companyId = request.getParameter("companyId");
				request.setAttribute("specQCDetailBean",process.getSpecQCDetails(inputBean, requestId, companyId));
			}
			
			request.setAttribute("specDetailTypeCol", asProcess.getSpecDetails("type"));
			request.setAttribute("specDetailClassCol", asProcess.getSpecDetails("class"));
			request.setAttribute("specDetailFormCol", asProcess.getSpecDetails("form"));
			request.setAttribute("specDetailGroupCol", asProcess.getSpecDetails("group"));
			request.setAttribute("specDetailGradeCol", asProcess.getSpecDetails("grade"));
			request.setAttribute("specDetailStyleCol", asProcess.getSpecDetails("style"));
			request.setAttribute("specDetailFinishCol", asProcess.getSpecDetails("finish"));
			request.setAttribute("specDetailSizeCol", asProcess.getSpecDetails("size"));
			request.setAttribute("specDetailColorCol", asProcess.getSpecDetails("color"));
			
			return mapping.findForward("success");
		}
	}

}
