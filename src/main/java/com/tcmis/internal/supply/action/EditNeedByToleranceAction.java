package com.tcmis.internal.supply.action;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.EditNeedByToleranceInputBean;
import com.tcmis.internal.supply.process.EditNeedByToleranceProcess;

/******************************************************************************
 * Controller for UpdateReceiptNotes
 * @version 1.0
	******************************************************************************/
public class EditNeedByToleranceAction
 extends TcmISBaseAction 
  {
	 public ActionForward executeAction(ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws BaseException, Exception {
		 
			if (!isLoggedIn(request)) {
				request.setAttribute("requestedPage", "updatemain");
				request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
			}

			// If you need access to who is logged in
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			/* Need to check if the user has permissions to view this page. if they do not have the permission
				  we need to not show them the page.*/
		    if (!user.getPermissionBean().hasUserPagePermission("dBuyConsolidationFreq") ){
				return (mapping.findForward("nopermissions"));
			}
	 		EditNeedByToleranceInputBean input = new EditNeedByToleranceInputBean(); 
	 		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));	
	 		PermissionBean permissionBean = user.getPermissionBean();
			if (input.isUpdate() && permissionBean.hasOpsEntityPermission("dBuyConsolidationFreq", input.getOpsEntityId(), null)) {
				
			
				EditNeedByToleranceProcess process = new EditNeedByToleranceProcess(getDbUser(request), getTcmISLocaleString(request));
				Vector errorCollection = process.update(input, user);
			     if (errorCollection != null) {
			          request.setAttribute("tcmISErrors", errorCollection);
			     }
			     else
			     {
			    	 request.setAttribute("wAction", "updateSuccess");
			    	 request.setAttribute("needByToleranceSave", input.getNeedByTolerance());
			   	}
			 	return (mapping.findForward("success"));

			}
			else
				return (mapping.findForward("success"));
	 }
		
}
