package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.process.AllocationAnalysisProcess;
import com.tcmis.internal.hub.process.ReceivingReportProcess;
import com.tcmis.internal.hub.beans.ReceivingReportInputBean;

/**
 * ***************************************************************************
 * Controller for receiving report
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ReceivingReportAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "receivingreportmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) {
			this.saveTcmISToken(request);
		}
		
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("customerReceivingHistory"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }


		//if form is not null we have to perform some action
		if (form != null) {
			ReceivingReportProcess process = new ReceivingReportProcess(this.getDbUser(request),getTcmISLocaleString(request));

			//copy date from dyna form to the data form
			ReceivingReportInputBean bean = new ReceivingReportInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			if (((DynaBean) form).get("uAction") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("uAction")))) {
				request.setAttribute("whichUnit",bean.getUnitOfMessure());
				request.setAttribute("receivingReportCollection", process.createObjectFromData(process.getSearchResult(bean, personnelBean)));
			} else if (((DynaBean) form).get("uAction") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("uAction")))) {
				this.setExcel(response, "Receiving Report");
				process.getExcelReport(bean, personnelBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				return noForward;
			} else {
				//AllocationAnalysisProcess allocationAnalysisProcess = new AllocationAnalysisProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				//request.setAttribute("hubInventoryGroupFacOvBeanCollection", allocationAnalysisProcess.getHubDropDownData(new BigDecimal(((PersonnelBean) this.getSessionObject(request, "personnelBean")).getPersonnelId())));
			}
		}
		return mapping.findForward("success");
	}
}
