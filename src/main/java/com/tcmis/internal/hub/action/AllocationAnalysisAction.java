package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.AllocationAnalysisInputBean;
import com.tcmis.internal.hub.process.AllocationAnalysisProcess;

/******************************************************************************
 * Controller for receiving
 * 
 * @version 1.0
 ******************************************************************************/
public class AllocationAnalysisAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		// login
		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "allocationanalysismain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("allocationAnalysis")) {
			return (mapping.findForward("nopermissions"));
		}

		// main
		if (form != null) {
			// result
			VvDataProcess vvDataProcess = new VvDataProcess(getDbUser(request), getTcmISLocale(request));
			AllocationAnalysisProcess process = new AllocationAnalysisProcess(getDbUser(request), getTcmISLocaleString(request));
			AllocationAnalysisInputBean input = new AllocationAnalysisInputBean();
			BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

			if (input.isSearch()) {
				request.setAttribute("prOpenOrderBeanCollection", process.getSearchData(input));
			} else if (input.isCreateExcel()) {
				Collection c = process.getSearchData(input);
				try {
					setExcel(response, "AllocationAnalysis");
					process.getExcelReport(c, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}				
				return noForward;
			} else {
				request.setAttribute("vvLotStatusBeanCollection", vvDataProcess.getActiveVvLotStatus());
				request.setAttribute("hubInventoryGroupFacOvBeanCollection", process.getHubDropDownData(new BigDecimal(((PersonnelBean) this.getSessionObject(request, "personnelBean")).getPersonnelId())));
			}
		}
		
		return mapping.findForward("success");
	}
}
