package com.tcmis.client.report.action;

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
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.report.process.CostReportProcess;
import com.tcmis.client.report.beans.CostReportInputBean;

/**
 * ***************************************************************************
 * Controller for cost report
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CostReportAction extends TcmISBaseAction {

public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
		request.setAttribute("requestedPage", "costreportsearch");
		request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
		return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("costReport"))
    {
      return (mapping.findForward("nopermissions"));
    }

	
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	CostReportProcess costReportProcess = new CostReportProcess(this.getDbUser(request));

	//if form is not null we have to perform some action
	if (form != null) {
		this.saveTcmISToken(request);
		//copy date from dyna form to the data form
		CostReportInputBean bean = new CostReportInputBean();
		BeanHandler.copyAttributes(form, bean,getLocale(request));

		//check what button was pressed and determine where to go
		if (((DynaBean) form).get("action") != null && "generateReport".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			if ("html".equals(bean.getReportType())) {
				this.setSessionObject(request,bean,"costReportInputBean"+personnelBean.getPersonnelId());
				return (mapping.findForward("htmlReportSuccess"));
			}else {
				this.setExcel(response,"CostReport");
				costReportProcess.getExcelReport(personnelId,bean, getLocale(request)).write(response.getOutputStream());
				return noForward;
			}
		} else if (((DynaBean) form).get("action") != null && "showAdditionalCharge".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			String whereClauseForLink = null;
			if(((DynaBean) form).get("whereClauseForLink") != null) {
				whereClauseForLink = (String)((DynaBean) form).get("whereClauseForLink");
			}
			request.setAttribute("addChargeCollection",costReportProcess.getAdditionalCharge(personnelId,bean,whereClauseForLink,getLocale(request)));
			return (mapping.findForward("showaddcharge"));
		} else if (((DynaBean) form).get("action") != null && "showSalesTax".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			String whereClauseForLink = null;
			if(((DynaBean) form).get("whereClauseForLink") != null) {
				whereClauseForLink = (String)((DynaBean) form).get("whereClauseForLink");
			}
			request.setAttribute("salesTaxCollection",costReportProcess.getSalesTax(personnelId,bean,whereClauseForLink,getLocale(request)));
			return (mapping.findForward("showSalesTax"));
		} else if (((DynaBean) form).get("action") != null && "saveTemplate".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			String templateId = costReportProcess.saveTemplate(personnelBean,bean,getLocale(request));
			request.setAttribute("companiesFacilitiesDropdown", costReportProcess.getCompaniesFacilitiesData(personnelId));
			request.setAttribute("catPartAttributeHeader",costReportProcess.getCatPartAttributeHeader(personnelId));
			request.setAttribute("qualityIdLabel",costReportProcess.getQualityIdLabel(personnelId));
			if (!StringHandler.isBlankString(templateId)) {
				bean.setTemplateId(templateId);
				request.setAttribute("costReportInputBean",costReportProcess.openTemplate(personnelId,bean));
			}else {
				request.setAttribute("costReportInputBean",new CostReportInputBean());	
			}
			request.setAttribute("itemTypeCollection",costReportProcess.getItemType("itemType"));
			request.setAttribute("invoiceTypeCollection",costReportProcess.getItemType("invoiceType"));
			//todo - NEED TO DO LOGIC HERE
		   request.setAttribute("hasPublishPermission","Y");
		   request.setAttribute("hasUnpublishPermission","Y");

			return (mapping.findForward("success"));
			//return noForward;
		} else if (((DynaBean) form).get("action") != null && "openTemplate".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			request.setAttribute("companiesFacilitiesDropdown", costReportProcess.getCompaniesFacilitiesData(personnelId));
			request.setAttribute("catPartAttributeHeader",costReportProcess.getCatPartAttributeHeader(personnelId));
			request.setAttribute("qualityIdLabel",costReportProcess.getQualityIdLabel(personnelId));
			request.setAttribute("costReportInputBean",costReportProcess.openTemplate(personnelId,bean));
			request.setAttribute("itemTypeCollection",costReportProcess.getItemType("itemType"));
			request.setAttribute("invoiceTypeCollection",costReportProcess.getItemType("invoiceType"));

			//todo - NEED TO DO LOGIC HERE
		   request.setAttribute("hasPublishPermission","Y");
		   request.setAttribute("hasUnpublishPermission","Y");

			return (mapping.findForward("openTemplate"));
		} else if (((DynaBean) form).get("action") != null && "getTemplateList".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			request.setAttribute("myTemplateCollection",costReportProcess.getTemplate(personnelId,null,null));
			return (mapping.findForward("getTemplate"));
		} else if (((DynaBean) form).get("action") != null && "clearTemplate".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			request.setAttribute("companiesFacilitiesDropdown", costReportProcess.getCompaniesFacilitiesData(personnelId));
			request.setAttribute("catPartAttributeHeader",costReportProcess.getCatPartAttributeHeader(personnelId));
			request.setAttribute("qualityIdLabel",costReportProcess.getQualityIdLabel(personnelId));
			request.setAttribute("costReportInputBean",new CostReportInputBean());
			request.setAttribute("itemTypeCollection",costReportProcess.getItemType("itemType"));
			request.setAttribute("invoiceTypeCollection",costReportProcess.getItemType("invoiceType"));
			return (mapping.findForward("clearTemplate"));
		} else {
			//log initial data for dropdown
			request.setAttribute("companiesFacilitiesDropdown", costReportProcess.getCompaniesFacilitiesData(personnelId));
			request.setAttribute("catPartAttributeHeader",costReportProcess.getCatPartAttributeHeader(personnelId));
			request.setAttribute("qualityIdLabel",costReportProcess.getQualityIdLabel(personnelId));
			request.setAttribute("costReportInputBean",new CostReportInputBean());
			request.setAttribute("itemTypeCollection",costReportProcess.getItemType("itemType"));
			request.setAttribute("invoiceTypeCollection",costReportProcess.getItemType("invoiceType"));
			return (mapping.findForward("success"));
		}
	}
	return mapping.findForward("success");
}
} //end of class