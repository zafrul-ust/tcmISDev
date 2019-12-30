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

//import com.tcmis.client.catalog.process.OrderTrackingProcess;

/**
 * Controller for Ad Hoc Material Matrix report.
 */

public class AdHocMaterialMatrixReportOriginalAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "adhocmaterialmatrixoriginal");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));

		AdHocMaterialMatrixReportOriginalProcess adHocMaterialMatrixReportOriginalProcess = new AdHocMaterialMatrixReportOriginalProcess(this.getDbUser(request),getLocale(request));
		//if form is not null we have to perform some action
		if (form != null) {
			AdHocMaterialMatrixInputBean bean = (AdHocMaterialMatrixInputBean) form;
			bean.setPersonnelId(personnelBean.getPersonnelIdBigDecimal().toString());
			Collection reportingEntityCollection = null;

			if ("save".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
                getInitialData(request,personnelBean,adHocMaterialMatrixReportOriginalProcess);
                MaterialmatrixReportTemplateBean templateBean = new MaterialmatrixReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				templateBean.setPersonnelId(new BigDecimal(personnelBean.getPersonnelId()));
				adHocMaterialMatrixReportOriginalProcess.convertDateStringToDateObject(bean,templateBean);

				if (!"OK".equalsIgnoreCase(adHocMaterialMatrixReportOriginalProcess.saveTemplate(templateBean,personnelBean))) {
					String[] selectedChemicalList = bean.getChemicalFieldList();
					bean.setBar(selectedChemicalList);
					String[] selectedReportFieldList = bean.getReportFieldList();
					bean.setFoo(selectedReportFieldList);

					bean.setChemicalFieldCollection(adHocMaterialMatrixReportOriginalProcess.getListNameFromId((Collection)request.getAttribute("listOptionBeanCollection"),selectedChemicalList));
					request.setAttribute("chemicalListBeanCollection",bean.getChemicalFieldCollection());
					bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("baseFieldBeanCollection"),selectedReportFieldList));
					request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
				}else {
					bean.setTemplateId(templateBean.getTemplateId());
					getTemplateInfo(request,personnelBean,adHocMaterialMatrixReportOriginalProcess,bean);
				}
				adHocMaterialMatrixReportOriginalProcess.getDefaultReportDate(bean);
			} else if ("open".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				getInitialData(request,personnelBean,adHocMaterialMatrixReportOriginalProcess);
                getTemplateInfo(request,personnelBean,adHocMaterialMatrixReportOriginalProcess,bean);
                adHocMaterialMatrixReportOriginalProcess.getDefaultReportDate(bean);
			} else if ("submit".equalsIgnoreCase(bean.getSubmitValue())) {
				MaterialmatrixReportTemplateBean templateBean = new MaterialmatrixReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				adHocMaterialMatrixReportOriginalProcess.convertDateStringToDateObject(bean,templateBean);
				if (!"batch".equalsIgnoreCase(templateBean.getReportGenerationType())) {
					this.setExcelHeader(response);
				}
				adHocMaterialMatrixReportOriginalProcess.runReport(templateBean, personnelBean, response.getOutputStream());
				return noForward;
				//adHocMaterialMatrixReportOriginalProcess.runReport(templateBean, personnelBean, response.getWriter(),  request.getLocale());
			} else if ("clearTemplate".equalsIgnoreCase(bean.getSubmitValue())) {
                getInitialData(request,personnelBean,adHocMaterialMatrixReportOriginalProcess);
                AdHocMaterialMatrixInputBean tmp = new AdHocMaterialMatrixInputBean();
				BeanHandler.copyAttributes(tmp, bean, getLocale(request));
                adHocMaterialMatrixReportOriginalProcess.getDefaultReportDate(bean);
			}else {
				//load initial data
                getInitialData(request,personnelBean,adHocMaterialMatrixReportOriginalProcess);
                adHocMaterialMatrixReportOriginalProcess.getDefaultReportDate(bean);
			}

			this.saveTcmISToken(request);
		}
		return (mapping.findForward("success"));
	}

    void getInitialData(HttpServletRequest request, PersonnelBean personnelBean, AdHocMaterialMatrixReportOriginalProcess adHocMaterialMatrixReportOriginalProcess) {
        try {
            GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
            Collection facilityCollection = genericReportProcess.getNormalizedFacAppReportData(new BigDecimal(personnelBean.getPersonnelId()));
            request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("MaterialMatrix"));
            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);

            request.setAttribute("displayPartsInInventory", adHocMaterialMatrixReportOriginalProcess.displayPartsInInventory());

            //Get search drop down
            Collection listDropDown = genericReportProcess.getReportList();
            request.setAttribute("listOptionBeanCollection", listDropDown);

            request.setAttribute("reportFieldBeanCollection", new Vector(0));
            request.setAttribute("chemicalListBeanCollection", new Vector(0));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    void getTemplateInfo(HttpServletRequest request, PersonnelBean personnelBean,AdHocMaterialMatrixReportOriginalProcess adHocMaterialMatrixReportOriginalProcess, AdHocMaterialMatrixInputBean bean) {
		try {
			BigDecimal templateId = new BigDecimal(0);
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				templateId = new BigDecimal(bean.getTemplateId());
			}
			Collection templateData = adHocMaterialMatrixReportOriginalProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
			adHocMaterialMatrixReportOriginalProcess.copyData((Collection)request.getAttribute("baseFieldBeanCollection"),(Collection)request.getAttribute("listOptionBeanCollection"),templateData,bean);

			//get drop down for application
			request.setAttribute("reportFieldBeanCollection", bean.getReportFieldCollection());
			request.setAttribute("chemicalListBeanCollection", bean.getChemicalFieldCollection());

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