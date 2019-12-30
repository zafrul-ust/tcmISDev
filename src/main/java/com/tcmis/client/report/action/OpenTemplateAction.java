package com.tcmis.client.report.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.report.process.AdHocInventoryReportProcess;
import com.tcmis.client.report.process.AdHocMaterialMatrixReportProcess;
import com.tcmis.client.report.process.AdHocUsageReportProcess;
import com.tcmis.client.report.process.AdHocWasteReportProcess;
import com.tcmis.client.report.process.CostReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/**
 * Controller for open report template.
 */

public class OpenTemplateAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			return (mapping.findForward("login"));
		}
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		String reportType = request.getParameter("reportType");
	   if("usage".equalsIgnoreCase(reportType)) {
			AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",adHocUsageReportProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,null));
		}else if("materialmatrix".equalsIgnoreCase(reportType)) {
			AdHocMaterialMatrixReportProcess materialMatrixProcess = new AdHocMaterialMatrixReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",materialMatrixProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,null));
		 }else if("waste".equalsIgnoreCase(reportType)) {
		 	AdHocWasteReportProcess wasteProcess = new AdHocWasteReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",wasteProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,null));
		}else if ("cost".equalsIgnoreCase(reportType)) {
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			CostReportProcess costReportProcess = new CostReportProcess(this.getDbUser(request));
			request.setAttribute("templateCollection",costReportProcess.getTemplate(personnelId,null,null));
		}else if("inventory".equalsIgnoreCase(reportType)) {
			AdHocInventoryReportProcess process = new AdHocInventoryReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",process.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,null));
		}

		return (mapping.findForward("success"));
	}

}