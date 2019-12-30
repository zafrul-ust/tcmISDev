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
import com.tcmis.client.report.process.AdHocUsageReportProcess;
import com.tcmis.client.report.beans.*;


/**
 * Controller for Ad Hoc Usage report.
 */

public class AdHocUsageReportOriginalAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "adhocusageoriginal");
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

	 //loading search data for page
	 PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    AdHocUsageReportOriginalProcess adHocUsageReportProcess = new AdHocUsageReportOriginalProcess(this.getDbUser(request),getLocale(request));
    GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));

	 //if form is not null we have to perform some action
    if (form != null) {
      if (log.isDebugEnabled()) {
        log.debug("form is not null");
      }
      AdHocUsageInputBean bean = (AdHocUsageInputBean) form;
      bean.setPersonnelId(personnelId.toString());
      Collection reportingEntityCollection = null;
      Collection dockCollection = null;
      if ("save".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
		  //load dropdown data
		  loadDropdownData(request,genericReportProcess,personnelId);

		  UsageReportTemplateBean templateBean = new UsageReportTemplateBean();
        BeanHandler.copyAttributes(bean, templateBean,getLocale(request));
		  adHocUsageReportProcess.convertDateStringToDateObject(bean,templateBean);
		  if (!"OK".equalsIgnoreCase(adHocUsageReportProcess.saveTemplate(templateBean, personnelBean))) {
			  String[] selectedReportFieldList = bean.getReportFieldList();
			  bean.setFoo(selectedReportFieldList);
			  bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("baseFieldBeanCollection"),selectedReportFieldList));
			  request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
		  }else {
			  bean.setTemplateId(templateBean.getTemplateId());
			  getTemplateInfo(request,personnelBean,adHocUsageReportProcess,bean);
		  }

		  adHocUsageReportProcess.getDefaultReportDate(bean);
		}else if ("open".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
		  //load dropdown data
		  loadDropdownData(request,genericReportProcess,personnelId);

		  getTemplateInfo(request,personnelBean,adHocUsageReportProcess,bean);

		  adHocUsageReportProcess.getDefaultReportDate(bean);
		}else if ("submit".equalsIgnoreCase(bean.getSubmitValue())) {
        UsageReportTemplateBean templateBean = new UsageReportTemplateBean();
		  BeanHandler.copyAttributes(bean, templateBean,getLocale(request));
		  adHocUsageReportProcess.convertDateStringToDateObject(bean,templateBean);
		  if(!"batch".equalsIgnoreCase(templateBean.getReportGenerationType())) {
            this.setExcelHeader(response);
        }
        adHocUsageReportProcess.runReport(templateBean, personnelBean, response.getOutputStream());
        return noForward;
      }else if ("clearTemplate".equalsIgnoreCase(bean.getSubmitValue())) {
			//load dropdown data
		   loadDropdownData(request,genericReportProcess,personnelId);

			AdHocUsageInputBean tmp = new AdHocUsageInputBean();
			BeanHandler.copyAttributes(tmp, bean,getLocale(request));
			adHocUsageReportProcess.getDefaultReportDate(bean);
		}else {
			//load dropdown data
		   loadDropdownData(request,genericReportProcess,personnelId);
			adHocUsageReportProcess.getDefaultReportDate(bean);
			request.setAttribute("adHocUsageInputBean",bean);
		}

      this.saveTcmISToken(request);
    }
    return (mapping.findForward("success"));
  }

	void getTemplateInfo(HttpServletRequest request,PersonnelBean personnelBean,AdHocUsageReportOriginalProcess adHocUsageReportOriginalProcess, AdHocUsageInputBean bean) {
		try {
		  BigDecimal templateId = new BigDecimal(0);
		  if (!StringHandler.isBlankString(bean.getTemplateId())) {
				templateId = new BigDecimal(bean.getTemplateId());
		  }

		  Collection templateData = adHocUsageReportOriginalProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
		  adHocUsageReportOriginalProcess.copyData((Collection)request.getAttribute("baseFieldBeanCollection"),templateData,bean);
		  request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());

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

  void loadDropdownData(HttpServletRequest request, GenericReportProcess genericReportProcess, BigDecimal personnelId) throws BaseException {
	 try {
		 request.setAttribute("baseFieldBeanCollection",genericReportProcess.getBaseFields("AdHocUsage"));
		 request.setAttribute("vvCategoryBeanCollection",genericReportProcess.getMaterialCategories());
		 request.setAttribute("facAppDockDpViewBeanCollection",genericReportProcess.getNormalizedDockDeliveryPoints(personnelId));
		 request.setAttribute("facAppReportViewBeanCollection", genericReportProcess.getNormalizedFacAppReportData(personnelId));
		 //Get search drop down
		 request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
		 request.setAttribute("reportFieldBeanCollection",new Vector(0));
	 }catch(Exception e) {
		throw new BaseException();
	 }
  }


}