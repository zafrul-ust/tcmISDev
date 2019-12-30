package com.tcmis.client.report.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.client.report.beans.AdHocInventoryInputBean;
import com.tcmis.client.report.beans.AdHocMaterialMatrixInputBean;
import com.tcmis.client.report.beans.MaterialmatrixReportTemplateBean;
import com.tcmis.client.report.process.AdHocInventoryReportProcess;
import com.tcmis.client.report.process.AdHocMaterialMatrixReportProcess;
import com.tcmis.client.report.process.AdHocUsageReportProcess;
import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.client.report.process.PublishTemplateProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

//import com.tcmis.client.catalog.process.OrderTrackingProcess;

/**
 * Controller for Ad Hoc Material Matrix report.
 */

public class AdHocMaterialMatrixReportAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "adhocmaterialmatrixreport");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		request.setAttribute("pageId", "adHocMatMx");
	    if (!personnelBean.getPermissionBean().hasUserPagePermission("adHocMaterialmatrixNew"))
	    {
	      return (mapping.findForward("nopermissions"));
	    }	

		AdHocMaterialMatrixReportProcess adHocMaterialMatrixReportProcess = new AdHocMaterialMatrixReportProcess(this.getDbUser(request),getLocale(request));
		//if form is not null we have to perform some action
		if (form != null) {
			AdHocMaterialMatrixInputBean bean = (AdHocMaterialMatrixInputBean) form;
			bean.setPersonnelId(personnelBean.getPersonnelIdBigDecimal().toString());

			if ("save".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
                //getInitialData(request,personnelBean,adHocMaterialMatrixReportProcess);
                MaterialmatrixReportTemplateBean templateBean = new MaterialmatrixReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				templateBean.setPersonnelId(new BigDecimal(personnelBean.getPersonnelId()));
				adHocMaterialMatrixReportProcess.convertDateStringToDateObject(bean,templateBean);
				loadPageData(personnelBean, adHocMaterialMatrixReportProcess);
				if (!"OK".equalsIgnoreCase(adHocMaterialMatrixReportProcess.saveTemplate(templateBean,personnelBean))) {
					
					request.setAttribute("chemicalListBeanCollection",bean.getChemicalFieldCollection());
					String[] selectedReportFieldList =  bean.getBaseFieldId().split("[|]");
                    AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));
					bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("reportFieldBeanCollection"),selectedReportFieldList));                    
					request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
				}else {
					bean.setTemplateId(templateBean.getTemplateId());
					getTemplateInfo(request,personnelBean,adHocMaterialMatrixReportProcess,bean);
				}
				adHocMaterialMatrixReportProcess.getDefaultReportDate(bean);
			} else if ("open".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				//getInitialData(request,personnelBean,adHocMaterialMatrixReportProcess);
	            getTemplateInfo(request,personnelBean,adHocMaterialMatrixReportProcess,bean);
                adHocMaterialMatrixReportProcess.getDefaultReportDate(bean);
                loadPageData(personnelBean, adHocMaterialMatrixReportProcess);
			} else if ("submit".equalsIgnoreCase(bean.getSubmitValue())) {
				MaterialmatrixReportTemplateBean templateBean = new MaterialmatrixReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				adHocMaterialMatrixReportProcess.convertDateStringToDateObject(bean,templateBean);
				boolean timedOut = adHocMaterialMatrixReportProcess.runReport(templateBean, personnelBean, response.getOutputStream());
				if ( ! timedOut && "interactive".equalsIgnoreCase(templateBean.getReportGenerationType())) {
					this.setExcelHeaderXlsx(response);
					adHocMaterialMatrixReportProcess.writeWorkbookToBrowser();
				}
				return noForward;
				//adHocMaterialMatrixReportProcess.runReport(templateBean, personnelBean, response.getWriter(),  request.getLocale());
			} else if ("clearTemplate".equalsIgnoreCase(bean.getSubmitValue())) {
               // getInitialData(request,personnelBean,adHocMaterialMatrixReportProcess);
                AdHocMaterialMatrixInputBean tmp = new AdHocMaterialMatrixInputBean();
				BeanHandler.copyAttributes(tmp, bean, getLocale(request));
                adHocMaterialMatrixReportProcess.getDefaultReportDate(bean);
                loadPageData(personnelBean, adHocMaterialMatrixReportProcess);
                request.setAttribute("gridType","list");
			}else {
				//load initial data
                //getInitialData(request,personnelBean,adHocMaterialMatrixReportProcess);
                adHocMaterialMatrixReportProcess.getDefaultReportDate(bean);

			    loadPageData(personnelBean,adHocMaterialMatrixReportProcess);
            	request.setAttribute("gridType","list");
            	request.setAttribute("preSelectDropsDowns","N");
			}

			this.saveTcmISToken(request);
		}
		return (mapping.findForward("success"));
	}
	

	
	private void loadPageData(PersonnelBean personnelBean, AdHocMaterialMatrixReportProcess adHocMaterialMatrixReportProcess) throws Exception
	{
		AdHocInventoryReportProcess adHocInventoryReportProcess = new AdHocInventoryReportProcess(this.getDbUser(request),getLocale(request));
        if (personnelBean.getReportFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getReportFacilityAreaBuildingFloorRoomColl().size() == 0) {
            Collection facilityCollection = adHocInventoryReportProcess.getFacilityAreaBldgRm(personnelBean.getPersonnelIdBigDecimal(),personnelBean.getCompanyId());
            personnelBean.setReportFacilityAreaBuildingFloorRoomColl(facilityCollection);
            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
        }else {
            request.setAttribute("facAppReportViewBeanCollection",personnelBean.getReportFacilityAreaBuildingFloorRoomColl());
        }
        request.setAttribute("showFlammabilityVocZone",adHocInventoryReportProcess.getShowFlammabilityVocZone(personnelBean.getCompanyId()));
		request.setAttribute("pageId", "adHocMatMx");
        MsdsViewerProcess process = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
		request.setAttribute("vocUnitColl", process.getVvVocUnit());
        request.setAttribute("physicalStateColl",process.getPhysicalStateColl());
        request.setAttribute("ppColl",process.getPPColl());
        request.setAttribute("specificHazardColl",process.getSpecificHazardColl()); 
        request.setAttribute("vaporPressureUnitColl",process.getVaporPressureUnitColl());
        request.setAttribute("matCatFacIdsColl",adHocMaterialMatrixReportProcess.getShowMaterialCategory(personnelBean.getCompanyId()));
        request.setAttribute("facilityCatalogColl",process.getFacilityCatalogColl());
	}

    void getTemplateInfo(HttpServletRequest request, PersonnelBean personnelBean,AdHocMaterialMatrixReportProcess adHocMaterialMatrixReportProcess, AdHocMaterialMatrixInputBean bean) {
		try {
			BigDecimal templateId = new BigDecimal(0);
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				templateId = new BigDecimal(bean.getTemplateId());
			}
			
	        GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
			Collection templateData = adHocMaterialMatrixReportProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
			adHocMaterialMatrixReportProcess.copyData(genericReportProcess.getBaseFields("MaterialMatrix"),genericReportProcess.getReportList(),templateData,bean);

			request.setAttribute("gridType","list");
			Vector<AdHocInventoryInputBean> gridBeans = null;
			boolean isList = false;
			if(bean.getCasNum() != null && !StringHandler.isBlankString(bean.getCasNum()))
			{
				request.setAttribute("gridType","cas");
				gridBeans = (Vector<AdHocInventoryInputBean>)adHocMaterialMatrixReportProcess.getGridInfo(isList, bean.getTemplateId());
				request.setAttribute("casColl", gridBeans);
			}
			else if(bean.getListId() != null && !StringHandler.isBlankString(bean.getListId()))
			{
				isList = true;
				gridBeans = (Vector<AdHocInventoryInputBean>)adHocMaterialMatrixReportProcess.getGridInfo(isList, bean.getTemplateId());
				request.setAttribute("listColl", gridBeans);
			}
			
			if(gridBeans != null && !gridBeans.isEmpty())
			{
				String[] cellSplitSpace = null;
				//String[] listFormatSplit = null;
				//String[] chemicalFieldListIdSplit = null;
				String temp = bean.getListFormat();
				temp += " ";
				bean.setListFormat(temp);
				HashMap<String, String> listFormatSplit = null;	
				HashMap<String, String> chemicalFieldListIdSplit = null;
				
				if (!StringHandler.isBlankString(bean.getListFormat()) && !StringHandler.isBlankString(bean.getChemicalFieldListId())) {
					//listFormatSplit = bean.getListFormat().split("[|]");
					//chemicalFieldListIdSplit = bean.getChemicalFieldListId().split("[|]");
					ArrayList<HashMap<String, String>> result =  genericReportProcess.separateFormatIdAsPerListId(bean.getListFormat(),bean.getChemicalFieldListId());
					listFormatSplit = (HashMap<String, String>)result.get(0);//get format ids from 0th position					
					chemicalFieldListIdSplit = (HashMap<String, String>)result.get(1);//get list ids from 1st positions
				}
				
				int i=0;
				for(AdHocInventoryInputBean b: gridBeans)
				{
					if(isList)
					{
						b.setListId(b.getId());						
						b.setHasThreshold(b.getHasThreshold());						
						b.setListName(b.getName());
//						if (listFormatSplit != null && i < listFormatSplit.length) {
//							b.setListFormat(listFormatSplit[i]);							
//							b.setListFormatText(genericReportProcess.getListFormatDisplayTextForId(listFormatSplit[i], b.getId(),personnelBean.getCompanyId(),"Y"));
//							if (i >= chemicalFieldListIdSplit.length)
//								b.setChemicalFieldListId("");
//							else 
//								b.setChemicalFieldListId(chemicalFieldListIdSplit[i]);
//						} else {
//							b.setListFormat("");
//							b.setListFormatText("");
//							b.setChemicalFieldListId("");
//						}
						if (listFormatSplit != null && chemicalFieldListIdSplit != null) {
							String listformatid = (String)listFormatSplit.get(b.getId());
							b.setListFormat(listformatid);
							b.setListFormatText(genericReportProcess.getListFormatDisplayTextForId(listformatid, b.getId(),personnelBean.getCompanyId(),"Y"));
							b.setChemicalFieldListId((String) chemicalFieldListIdSplit.get(b.getId()));
						} else {
							b.setListFormat("");
							b.setListFormatText("");
							b.setChemicalFieldListId("");
						}
					}
					else
					{
						b.setCasNum(b.getId());
						b.setChemicalName(b.getName());
					}
					if(b.getConstraint() != null && b.getConstraint().length() > 0)
					{
						cellSplitSpace = b.getConstraint().split(" ");
						b.setConstraint(cellSplitSpace[0]);
						   
						if (cellSplitSpace[0] != null && (cellSplitSpace[0].equalsIgnoreCase("") || cellSplitSpace[0].equalsIgnoreCase("None") || cellSplitSpace[0].equalsIgnoreCase("OnList") || cellSplitSpace[0].equalsIgnoreCase("NotOnList") || cellSplitSpace[0].equalsIgnoreCase("TriggersThreshold"))) {
							b.setOperator("");
							b.setValue("");
						} else if (cellSplitSpace[1] != null && (cellSplitSpace[1].equalsIgnoreCase("notlisted") || cellSplitSpace[1].equalsIgnoreCase("trace"))) {
							b.setOperator(cellSplitSpace[1]);
							b.setValue("");
						}
						else if(cellSplitSpace[2] != null && cellSplitSpace[2].equalsIgnoreCase("null"))
						{
							b.setOperator(cellSplitSpace[1] + " " +  cellSplitSpace[2]);
							b.setValue("");
						}
						else
						{
							b.setOperator(cellSplitSpace[1]);
							b.setValue(cellSplitSpace[2]);
						}
					}
					i=i+1;
				}
			} 
			
			//get drop down for application
			request.setAttribute("reportFieldBeanCollection", bean.getReportFieldCollection());
			request.setAttribute("chemicalListBeanCollection", bean.getChemicalFieldCollection());
			request.setAttribute("templateBean", bean);

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