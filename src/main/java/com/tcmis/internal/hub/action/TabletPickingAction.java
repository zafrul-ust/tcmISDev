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
import com.tcmis.internal.hub.beans.PicklistSelectionViewBean;
import com.tcmis.internal.hub.process.TabletPickingProcess;

/******************************************************************************
 * Controller for PicklistPicking
 * 
 * @version 1.0
 ******************************************************************************/
public class TabletPickingAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		String forward = "success";
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "tabletpickingmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		if (!personnelBean.getPermissionBean().hasUserPagePermission("tabletPicking")) {
			return (mapping.findForward("nopermissions"));
		}

		TabletPickingProcess process = new TabletPickingProcess(this.getDbUser(request));

		PicklistSelectionViewBean input = new PicklistSelectionViewBean();
		BeanHandler.copyAttributes(form, input);

		if (input.isSearch()) {
			request.setAttribute("picklistColl", process.getSearchResult(input, personnelBean));
			this.saveTcmISToken(request);
		}
		else if (input.isPickableUnits()) {

			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("Picking", null, null, null)) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				request.setAttribute("picklistColl", process.getSearchResult(input, personnelBean));
				return (mapping.findForward("success"));
			}

			checkToken(request);
			PicklistSelectionViewBean picklistBean = new PicklistSelectionViewBean();
			Collection<PicklistSelectionViewBean> beans;
			try {
				beans = BeanHandler.getBeans((DynaBean) form, "picklistViewBean", picklistBean, getTcmISLocale(request), "ok");
				Object[] results = process.createPicklist(input, beans, personnelBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale"));

				request.setAttribute("picklistColl", process.getSearchResult(input, personnelBean));
				request.setAttribute("picklistId", results[0]);
				this.setSessionObject(request, results[0], "PICKLIST_ID");
				request.setAttribute("tcmISErrors", results[1]);

			}
			catch (Exception ex) {
				request.setAttribute("tcmISErrors", ex.toString());
			}

		}
		else if (input.isCreateExcel()) {
			this.setExcel(response, "Picking_PickList");
			process.getExcelReport(input, personnelBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}

		return mapping.findForward(forward);
	}
}
