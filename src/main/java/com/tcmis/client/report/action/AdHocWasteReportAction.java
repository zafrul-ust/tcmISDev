package com.tcmis.client.report.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
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
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.report.process.*;
import com.tcmis.client.report.beans.*;
import com.tcmis.client.catalog.process.OrderTrackingProcess;

/**
 * Controller for Ad Hoc Waste report.
 */

public class AdHocWasteReportAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "adhocwastereport");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
		request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("AdHocWaste"));
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		AdHocWasteReportProcess adHocWasteReportProcess = new AdHocWasteReportProcess(this.getDbUser(request),getLocale(request));
		Collection wasteFacAppActVendorViewBeanCollection = adHocWasteReportProcess.getNormalizedWasteDropDownData();
		request.setAttribute("wasteFacAppActVendorViewBeanCollection", wasteFacAppActVendorViewBeanCollection);

		request.setAttribute("reportFieldBeanCollection", new Vector(0));

		//if form is not null we have to perform some action
		if (form != null) {
			AdHocWasteInputBean bean = (AdHocWasteInputBean) form;
			bean.setPersonnelId(personnelId.toString());

			if ("save".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				WasteReportTemplateBean templateBean = new WasteReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				adHocWasteReportProcess.convertDateStringToDateObject(bean,templateBean);
				templateBean.setPersonnelId(new BigDecimal(personnelBean.getPersonnelId()));
				if (!"OK".equalsIgnoreCase(adHocWasteReportProcess.saveTemplate(templateBean,personnelBean))) {
					//get drop down for application
					Collection applicationCollection = adHocWasteReportProcess.getApplicationDropDownData(bean.getFacilityId(), wasteFacAppActVendorViewBeanCollection);
					request.setAttribute("applicationCollection", applicationCollection);
					//get drop down for accumulation point
					request.setAttribute("accumulationPointCollection", adHocWasteReportProcess.getAccumulationPointDropDownData(bean.getApplication(), applicationCollection));
					//get drop down for vendor
					request.setAttribute("vendorCollection", adHocWasteReportProcess.getVendorDropDownData(bean.getFacilityId(), wasteFacAppActVendorViewBeanCollection));

				  String[] selectedReportFieldList = bean.getReportFieldList();
				  bean.setFoo(selectedReportFieldList);
				  AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));
				  bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("baseFieldBeanCollection"),selectedReportFieldList));
				  request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
			  }else {
					bean.setTemplateId(templateBean.getTemplateId());
			  		getTemplateInfo(request,personnelBean,adHocWasteReportProcess,bean,wasteFacAppActVendorViewBeanCollection);
			  }

			  adHocWasteReportProcess.getDefaultReportDate(bean);
			} else if ("open".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				getTemplateInfo(request,personnelBean,adHocWasteReportProcess,bean,wasteFacAppActVendorViewBeanCollection);
				adHocWasteReportProcess.getDefaultReportDate(bean);
			} else if ("submit".equalsIgnoreCase(bean.getSubmitValue())) {
				WasteReportTemplateBean templateBean = new WasteReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				adHocWasteReportProcess.convertDateStringToDateObject(bean,templateBean);
				if (!"batch".equalsIgnoreCase(templateBean.getReportGenerationType())) {
					this.setExcelHeader(response);
				}
				adHocWasteReportProcess.runReport(templateBean, personnelBean, response.getOutputStream());
				return noForward;
			} else if ("clearTemplate".equalsIgnoreCase(bean.getSubmitValue())) {
				AdHocWasteInputBean tmp = new AdHocWasteInputBean();
				BeanHandler.copyAttributes(tmp, bean, getLocale(request));
				adHocWasteReportProcess.getDefaultReportDate(bean);
			}else {
				//load initial data
				adHocWasteReportProcess.getDefaultReportDate(bean);
			}

			this.saveTcmISToken(request);
		}
		return (mapping.findForward("success"));
	}

	void getTemplateInfo(HttpServletRequest request, PersonnelBean personnelBean,AdHocWasteReportProcess adHocWasteReportProcess, AdHocWasteInputBean bean, Collection wasteFacAppActVendorViewBeanCollection) {
		try {
			BigDecimal templateId = new BigDecimal(0);
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				templateId = new BigDecimal(bean.getTemplateId());
			}
			Collection templateData = adHocWasteReportProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
			adHocWasteReportProcess.copyData((Collection)request.getAttribute("baseFieldBeanCollection"),templateData,bean);

			//get drop down for application
			Collection applicationCollection = adHocWasteReportProcess.getApplicationDropDownData(bean.getFacilityId(), wasteFacAppActVendorViewBeanCollection);
			request.setAttribute("applicationCollection", applicationCollection);
			//get drop down for accumulation point
			request.setAttribute("accumulationPointCollection", adHocWasteReportProcess.getAccumulationPointDropDownData(bean.getApplication(), applicationCollection));
			//get drop down for vendor
			request.setAttribute("vendorCollection", adHocWasteReportProcess.getVendorDropDownData(bean.getFacilityId(), wasteFacAppActVendorViewBeanCollection));
			request.setAttribute("reportFieldBeanCollection", bean.getReportFieldCollection());

			if ("Y".equalsIgnoreCase(bean.getAllowEdit())) {
			  //can publish template
			  PublishTemplateProcess process = new PublishTemplateProcess(this.getDbUser(request),getLocale(request));
			  Collection col = process.getUserPublishUserGroups(personnelBean,templateId);
			  if (personnelBean.getPermissionBean().hasFacilityPermission("PublishCompanyTemplate","",personnelBean.getCompanyId()) ||
					personnelBean.getPermissionBean().hasFacilityPermission("PublishIndividualTemplate","",personnelBean.getCompanyId()) ||
					col.size() > 0) {
				bean.setPublishTemplate("Y");
			  }
			  //can unpublish template
			  if ("PERSONNEL_ID".equalsIgnoreCase(bean.getOwner())) {
				col = process.getTemplatePublishToUsers(templateId);
				if (col.size() > 0) {
					bean.setUnpublishTemplate("Y");
				}
			  }else if ("USER_GROUP_ID".equalsIgnoreCase(bean.getOwner())) {
				bean.setUnpublishTemplate("Y");
			  }else {
				  if (personnelBean.getPermissionBean().hasFacilityPermission("PublishCompanyTemplate","",personnelBean.getCompanyId())) {
					bean.setUnpublishTemplate("Y");
				  }
			  }
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}