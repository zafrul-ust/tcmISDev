package com.tcmis.client.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.SearchPersonnelInputBean;
import com.tcmis.common.admin.process.SearchPersonnelProcess;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.report.process.ReportTemplateProcess;
import com.tcmis.client.report.process.PublishTemplateProcess;
import com.tcmis.client.report.beans.ReportTemplateInputBean;

/**
 * Controller for report template.
 */

public class ReportTemplateAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "reporttemplatemain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//if form is not null we have to perform some action
		if (form != null) {
			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			//copy date from dyna form to the data form
			ReportTemplateInputBean inputBean = new ReportTemplateInputBean();
			BeanHandler.copyAttributes(form, inputBean);
			ReportTemplateProcess process = new ReportTemplateProcess(this.getDbUser(request),getLocale(request));
			if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				request.setAttribute("reportTemplateColl",process.getTemplates(inputBean,personnelBean));
			}else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				this.setExcel(response, "report_template_list");
				process.getExcelReport(inputBean,personnelBean,(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				return noForward;
			}else if (((DynaBean) form).get("action") != null && "activateTemplate".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				PublishTemplateProcess pProcess = new PublishTemplateProcess(this.getDbUser(request),getLocale(request));
				pProcess.activateTemplate(personnelBean.getCompanyId(),inputBean.getTemplateId());
				request.setAttribute("reportTemplateColl",process.getTemplates(inputBean,personnelBean));
			}else if (((DynaBean) form).get("action") != null && "inactivateTemplate".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				PublishTemplateProcess pProcess = new PublishTemplateProcess(this.getDbUser(request),getLocale(request));
				pProcess.inactivateTemplate(personnelBean.getCompanyId(),inputBean.getTemplateId(),personnelBean);
				request.setAttribute("reportTemplateColl",process.getTemplates(inputBean,personnelBean));
			}else if (((DynaBean) form).get("action") != null && "deleteTemplate".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				PublishTemplateProcess pProcess = new PublishTemplateProcess(this.getDbUser(request),getLocale(request));
				pProcess.deleteTemplate(personnelBean.getCompanyId(),inputBean.getTemplateId(),personnelBean);
				request.setAttribute("reportTemplateColl",process.getTemplates(inputBean,personnelBean));
			} else {
				//log initial data for dropdown
				request.setAttribute("reportTypeColl",process.getReportType());
			}
		}
		return mapping.findForward("success");
	}
}