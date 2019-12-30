package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.PickingGroupBean;
import com.tcmis.internal.hub.beans.PickingGroupMgmtInputBean;
import com.tcmis.internal.hub.process.PickingGroupMgmtProcess;

public class PickingGroupMgmtAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
			throws BaseException, Exception {
		String forward = "success";
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "pickinggroupmgmgtmain");			
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
		if (!personnelBean.getPermissionBean().hasUserPagePermission("pickingGroupMgmt")) {
			return (mapping.findForward("nopermissions"));
		}
		
		PickingGroupMgmtInputBean input = new PickingGroupMgmtInputBean();
		BeanHandler.copyAttributes(form, input);
		
		PickingGroupMgmtProcess process = new PickingGroupMgmtProcess(this.getDbUser(request), this.getLocale(request));
		request.setAttribute("vvPickingStateColl", process.getPickingStateColl());
		request.setAttribute("vvPickingGroupColl", process.getHubPickingGroupColl(input));
		
		if (input.isUpdate()) {
			try {
				Collection<PickingGroupBean> pickingGroupColl = BeanHandler.getBeans((DynaBean) form, "pickingGroupData", new PickingGroupBean());
				process.updatePickingGroups(input, personnelBean, pickingGroupColl);
			}
			catch(BaseException e) {
				request.setAttribute("tcmISError", e.getMessage());
			}
			finally {
				input.setuAction("search");
			}
		}
		
		if (input.isSearch()) {
			try {
				request.setAttribute("pickingGroupColl", process.search(input));
				request.setAttribute("hubCompanyId", "Radian");
			}
			catch(BaseException e) {
				request.setAttribute("tcmisError", e.getMessage());
			}
		}
		else if (input.isCreateExcel())   {
			this.setExcel(response, "Picking_Group_List");
			process.getExcelReport(input,personnelBean,
					(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		
		return mapping.findForward(forward);
	}
}
