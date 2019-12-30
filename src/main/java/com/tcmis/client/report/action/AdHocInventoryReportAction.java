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
import com.tcmis.client.report.beans.AdHocInventoryReportTemplateBean;
import com.tcmis.client.report.process.AdHocInventoryReportProcess;
import com.tcmis.client.report.process.AdHocUsageReportProcess;
import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.client.report.process.PublishTemplateProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;


/**
 * Controller for Ad Hoc Inventory report.
 */

public class AdHocInventoryReportAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "adhocinventoryreport");
			//This is to save any parameters passed in the URL, so that they 
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

        PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
        AdHocInventoryReportProcess adHocInventoryReportProcess = new AdHocInventoryReportProcess(this.getDbUser(request),getLocale(request));
        GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
        
        //the reason for this check is because we are using this action class for other popup
        if(!"multiselect".equalsIgnoreCase(request.getParameter("uAction")) &&
           !"editChemListLoad".equalsIgnoreCase(request.getParameter("uAction")) &&
           !"editChemListSearch".equalsIgnoreCase(request.getParameter("submitValue")) &&
		   !"createChemListExcel".equalsIgnoreCase(request.getParameter("submitValue")) &&
           !"editFieldListPreSelect".equalsIgnoreCase(request.getParameter("uAction"))) {
            if (!personnelBean.getPermissionBean().hasUserPagePermission("adHocInventoryNew")) {
               return (mapping.findForward("nopermissions"));
             }

             if (personnelBean.getReportFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getReportFacilityAreaBuildingFloorRoomColl().size() == 0) {
                Collection tmpColl = adHocInventoryReportProcess.getFacilityAreaBldgRm(personnelBean.getPersonnelIdBigDecimal(),personnelBean.getCompanyId());
                personnelBean.setReportFacilityAreaBuildingFloorRoomColl(tmpColl);
                request.setAttribute("facAppReportViewBeanCollection", tmpColl);
             }else {
                request.setAttribute("facAppReportViewBeanCollection",personnelBean.getReportFacilityAreaBuildingFloorRoomColl());
             }
        }

        request.setAttribute("reportFieldBeanCollection", new Vector(0));
		request.setAttribute("chemicalListBeanCollection", new Vector(0));
		//get user group
	    request.setAttribute("authorizedFacilityUsersForUg", genericReportProcess.getAuthorizedUserFacilitiesForUsergroup(personnelBean, "AdhocGateKeeping",personnelBean.getCompanyId()));
		//if form is not null we have to perform some action 
		if (form != null) {
			AdHocInventoryInputBean bean = (AdHocInventoryInputBean) form;
			
			 
			bean.setPersonnelId(personnelBean.getPersonnelIdBigDecimal().toString());
            if ("save".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				AdHocInventoryReportTemplateBean templateBean = new AdHocInventoryReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));				
				templateBean.setPersonnelId(new BigDecimal(personnelBean.getPersonnelId()));
				
                //Get search drop down
                //GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
              //  request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
               // request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("Inventory"));
                loadPageData(adHocInventoryReportProcess,personnelBean);
				if (!"OK".equalsIgnoreCase(adHocInventoryReportProcess.saveTemplate(templateBean,personnelBean))) {
					//String[] selectedChemicalList = bean.getChemicalFieldList();
					//bean.setBar(selectedChemicalList);
					//String[] selectedReportFieldList = bean.getReportFieldList();
					//bean.setFoo(selectedReportFieldList);

					//bean.setChemicalFieldCollection(adHocInventoryReportProcess.getListNameFromId((Collection)request.getAttribute("listOptionBeanCollection"),selectedChemicalList));
					request.setAttribute("chemicalListBeanCollection",bean.getChemicalFieldCollection());
                    AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));
                    String[] selectedReportFieldList =  bean.getBaseFieldId().split("[|]");
                    bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("reportFieldBeanCollection"),selectedReportFieldList));
					request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
				}else {
					bean.setTemplateId(templateBean.getTemplateId());
					getTemplateInfo(request,personnelBean,adHocInventoryReportProcess,bean);
				}
            } else if ("open".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
				//Get search drop down
            	loadPageData(adHocInventoryReportProcess,personnelBean);
                //GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
                //request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
                //request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("Inventory"));
                getTemplateInfo(request,personnelBean,adHocInventoryReportProcess,bean);
            } else if ("submit".equalsIgnoreCase(bean.getSubmitValue())) {
				AdHocInventoryReportTemplateBean templateBean = new AdHocInventoryReportTemplateBean();
				BeanHandler.copyAttributes(bean, templateBean, getLocale(request));
				boolean timedOut = adHocInventoryReportProcess.runReport(templateBean, personnelBean, response.getOutputStream());
				if ( ! timedOut && "interactive".equalsIgnoreCase(templateBean.getReportGenerationType())) {
					this.setExcelHeaderXlsx(response);
					adHocInventoryReportProcess.writeWorkbookToBrowser();
				}
				return noForward;
			} else if ("clearTemplate".equalsIgnoreCase(bean.getSubmitValue())) {
				AdHocInventoryInputBean tmp = new AdHocInventoryInputBean();
				BeanHandler.copyAttributes(tmp, bean, getLocale(request));
                //Get search drop down
				loadPageData(adHocInventoryReportProcess,personnelBean);
				request.setAttribute("gridType","list");
                //GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
                //request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
                //request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields("Inventory"));
			}else if ("editChemListLoad".equalsIgnoreCase(request.getParameter("uAction"))) {
				return (mapping.findForward("editchemlistload"));
            }else if ("editChemListSearch".equalsIgnoreCase(bean.getSubmitValue())) {
				request.setAttribute("searchResults", adHocInventoryReportProcess.listSearch(bean));
				return (mapping.findForward("editchemlistsearch"));
			}else if ("createChemListExcel".equalsIgnoreCase(bean.getSubmitValue())) {
				this.setExcel(response,"ChemListExcel");
				adHocInventoryReportProcess.createChemListExcel(bean).write(response.getOutputStream());
				return noForward;
            }else if ("editFieldListPreSelect".equalsIgnoreCase(request.getParameter("uAction"))) {
				return (mapping.findForward("editfieldlistpreselect"));
			}else if ("multiselect".equalsIgnoreCase(request.getParameter("uAction"))) {
				return (mapping.findForward("multiselect"));
			} 
            else {
				//load initial data
            	loadPageData(adHocInventoryReportProcess,personnelBean);
            	request.setAttribute("gridType","list");
            	request.setAttribute("preSelectDropsDowns","N");
            }
   

			this.saveTcmISToken(request);
		}
		return (mapping.findForward("success"));
	}
	
	private void loadPageData(AdHocInventoryReportProcess adHocInventoryReportProcess, PersonnelBean personnelBean) throws BaseException
	{
        try {
            request.setAttribute("pageId", "adHocInv");
            MsdsViewerProcess process = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
            request.setAttribute("vocUnitColl", process.getVvVocUnit());
            request.setAttribute("physicalStateColl",process.getPhysicalStateColl());
            request.setAttribute("ppColl",process.getPPColl());
            request.setAttribute("specificHazardColl",process.getSpecificHazardColl());
            request.setAttribute("vaporPressureUnitColl",process.getVaporPressureUnitColl());
            request.setAttribute("facilityCatalogColl",process.getFacilityCatalogColl());
            //request.setAttribute("vvMaterialSubcategoryColl",process.getVvMaterialSubcategory());
            //show flammability & voc zones
            request.setAttribute("showFlammabilityVocZone",adHocInventoryReportProcess.getShowFlammabilityVocZone(personnelBean.getCompanyId()));
       }catch(Exception e) {
		throw new BaseException();
	 }
    }

	void getTemplateInfo(HttpServletRequest request, PersonnelBean personnelBean,AdHocInventoryReportProcess adHocInventoryReportProcess, AdHocInventoryInputBean bean) {
		try {
			BigDecimal templateId = new BigDecimal(0);
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				templateId = new BigDecimal(bean.getTemplateId());
			}
	        GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
			Collection templateData = adHocInventoryReportProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
			adHocInventoryReportProcess.copyData(genericReportProcess.getBaseFields("Inventory"),genericReportProcess.getReportList(),templateData,bean);
				
			request.setAttribute("gridType","list");
			Vector<AdHocInventoryInputBean> gridBeans = null;
			boolean isList = false;
			if(bean.getCasNum() != null && !StringHandler.isBlankString(bean.getCasNum()))
			{
				request.setAttribute("gridType","cas");
				gridBeans = (Vector<AdHocInventoryInputBean>)adHocInventoryReportProcess.getGridInfo(isList, bean.getTemplateId());
				request.setAttribute("casColl", gridBeans);
			}
			else if(bean.getListId() != null && !StringHandler.isBlankString(bean.getListId()))
			{
				isList = true;
				gridBeans = (Vector<AdHocInventoryInputBean>)adHocInventoryReportProcess.getGridInfo(isList, bean.getTemplateId());
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
						b.setHasAmountThreshold(b.getHasAmountThreshold());		
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
						
						if (cellSplitSpace[0] != null && (cellSplitSpace[0].equalsIgnoreCase("") || cellSplitSpace[0].equalsIgnoreCase("None") 
								|| cellSplitSpace[0].equalsIgnoreCase("OnList") || cellSplitSpace[0].equalsIgnoreCase("NotOnList") 
								|| cellSplitSpace[0].equalsIgnoreCase("TriggersThreshold") || cellSplitSpace[0].equalsIgnoreCase("TriggersCompositionThreshold"))) {
							b.setOperator("");
							b.setValue("");
							if (cellSplitSpace[0].equalsIgnoreCase("TriggersThreshold") && b.getHasAmountThreshold() != null && b.getHasAmountThreshold().equalsIgnoreCase("Y") && cellSplitSpace.length > 1) {
								b.setValue(cellSplitSpace[1]);
							}
						} else if (cellSplitSpace[1] != null && (cellSplitSpace[1].equalsIgnoreCase("notlisted") || cellSplitSpace[1].equalsIgnoreCase("trace"))) {
							b.setOperator(cellSplitSpace[1]);
							b.setValue("");
						}
						else if(cellSplitSpace[2] != null && (cellSplitSpace[2].equalsIgnoreCase("null")))
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