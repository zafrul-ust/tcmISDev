package com.tcmis.client.report.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.report.process.*;
import com.tcmis.client.report.beans.*;


/**
 * Controller for Ad Hoc Inventory report.
 */

public class AdHocInventoryReportOriginalAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "adhocinventoryoriginal");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
	     PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("adHocInventory"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

		AdHocInventoryReportOriginalProcess adHocInventoryReportOriginalProcess = new AdHocInventoryReportOriginalProcess(this.getDbUser(request),getLocale(request));

        if (personnelBean.getReportFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getReportFacilityAreaBuildingFloorRoomColl().size() == 0) {
            Collection tmpColl = adHocInventoryReportOriginalProcess.getFacilityAreaBldgRm(personnelBean.getPersonnelIdBigDecimal());
            personnelBean.setReportFacilityAreaBuildingFloorRoomColl(tmpColl);
            request.setAttribute("facAppReportViewBeanCollection", tmpColl);
        }else {
            request.setAttribute("facAppReportViewBeanCollection",personnelBean.getReportFacilityAreaBuildingFloorRoomColl());
        }

        request.setAttribute("reportFieldBeanCollection", new Vector(0));
		request.setAttribute("chemicalListBeanCollection", new Vector(0));

		//if form is not null we have to perform some action
		if (form != null) {
			AdHocInventoryInputBean bean = (AdHocInventoryInputBean) form;
			bean.setPersonnelId(personnelBean.getPersonnelIdBigDecimal().toString());
            log.debug(bean.toString());

            if ("save".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				AdHocInventoryReportTemplateBean templateBean = new AdHocInventoryReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				templateBean.setPersonnelId(new BigDecimal(personnelBean.getPersonnelId()));
                //Get search drop down
                GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
                request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
                request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("Inventory"));

				if (!"OK".equalsIgnoreCase(adHocInventoryReportOriginalProcess.saveTemplate(templateBean,personnelBean))) {
					String[] selectedChemicalList = bean.getChemicalFieldList();
					bean.setBar(selectedChemicalList);
					String[] selectedReportFieldList = bean.getReportFieldList();
					bean.setFoo(selectedReportFieldList);

					bean.setChemicalFieldCollection(adHocInventoryReportOriginalProcess.getListNameFromId((Collection)request.getAttribute("listOptionBeanCollection"),selectedChemicalList));
					request.setAttribute("chemicalListBeanCollection",bean.getChemicalFieldCollection());
                    AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));
                    bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("baseFieldBeanCollection"),selectedReportFieldList));
					request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
				}else {
					bean.setTemplateId(templateBean.getTemplateId());
					getTemplateInfo(request,personnelBean,adHocInventoryReportOriginalProcess,bean);
				}
            } else if ("open".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				//Get search drop down
                GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
                request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
                request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("Inventory"));
                getTemplateInfo(request,personnelBean,adHocInventoryReportOriginalProcess,bean);
            } else if ("submit".equalsIgnoreCase(bean.getSubmitValue())) {
				AdHocInventoryReportTemplateBean templateBean = new AdHocInventoryReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				if (!"batch".equalsIgnoreCase(templateBean.getReportGenerationType())) {
					this.setExcelHeader(response);
				}
				adHocInventoryReportOriginalProcess.runReport(templateBean, personnelBean, response.getOutputStream());
				return noForward;
			} else if ("clearTemplate".equalsIgnoreCase(bean.getSubmitValue())) {
				AdHocInventoryInputBean tmp = new AdHocInventoryInputBean();
				BeanHandler.copyAttributes(tmp, bean, getLocale(request));
                //Get search drop down
                GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
                request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
                request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("Inventory"));
            }else {
				//load initial data
                //Get search drop down
                GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
                request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
                request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("Inventory"));
            }

			this.saveTcmISToken(request);
		}
		return (mapping.findForward("success"));
	}

	void getTemplateInfo(HttpServletRequest request, PersonnelBean personnelBean,AdHocInventoryReportOriginalProcess adHocInventoryReportOriginalProcess, AdHocInventoryInputBean bean) {
		try {
			BigDecimal templateId = new BigDecimal(0);
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				templateId = new BigDecimal(bean.getTemplateId());
			}
			Collection templateData = adHocInventoryReportOriginalProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
			adHocInventoryReportOriginalProcess.copyData((Collection)request.getAttribute("baseFieldBeanCollection"),(Collection)request.getAttribute("listOptionBeanCollection"),templateData,bean);

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