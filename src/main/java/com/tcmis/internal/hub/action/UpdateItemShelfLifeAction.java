package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.UpdateItemShelfLifeProcess;


/**
 * ***************************************************************************
 * Controller for UpdateItemShelfLifeAction
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class UpdateItemShelfLifeAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "updateitemshelflifemain");
			request.setAttribute("requestedURLWithParameters",
					getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) {
	        return (mapping.findForward("success"));
	    }

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		ReceivingInputBean inputBean = new ReceivingInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		UpdateItemShelfLifeProcess process = new UpdateItemShelfLifeProcess(getDbUser(request), getTcmISLocaleString(request));
		
		if ("search".equalsIgnoreCase(inputBean.getUserAction())) {
			request.setAttribute("updateShelfLifeBeanCollection", process.search(inputBean, personnelBean));
		}
		else if ("createExcel".equalsIgnoreCase(inputBean.getUserAction())) {
			this.setExcel(response,"Update Item Shelf Life");
			process.writeExcelFile(inputBean, process.search(inputBean, personnelBean), getTcmISLocale(request)).write(response.getOutputStream());
			return noForward;
		}
			
		return mapping.findForward("success");
	}
}
		
		
	
