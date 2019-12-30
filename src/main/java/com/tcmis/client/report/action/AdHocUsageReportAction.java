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
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.report.process.*;
import com.tcmis.client.report.beans.*;
import com.tcmis.client.common.process.MsdsViewerProcess;


/**
 * Controller for Ad Hoc Usage report.
 */

public class AdHocUsageReportAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "adhocusagereport"); 
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

	 //loading search data for page
	 PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	 
	 if(!personnelBean.getPermissionBean().hasUserPagePermission("adHocUsageNew")) 
	 {
       return (mapping.findForward("nopermissions"));
     }

    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));
    GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
	//get user group
    request.setAttribute("authorizedFacilityUsersForUg", genericReportProcess.getAuthorizedUserFacilitiesForUsergroup(personnelBean, "AdhocGateKeeping",personnelBean.getCompanyId()));
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
		  loadDropdownData(request,genericReportProcess,personnelBean);

		  UsageReportTemplateBean templateBean = new UsageReportTemplateBean();
          BeanHandler.copyAttributes(bean, templateBean,getLocale(request));
		  adHocUsageReportProcess.convertDateStringToDateObject(bean,templateBean);
		  if (!"OK".equalsIgnoreCase(adHocUsageReportProcess.saveTemplate(templateBean, personnelBean))) {
			  //String[] selectedReportFieldList = bean.getReportFieldList();
			  //bean.setFoo(selectedReportFieldList);
			  //bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("baseFieldBeanCollection"),selectedReportFieldList));
			  //request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
			  request.setAttribute("chemicalListBeanCollection",bean.getChemicalFieldCollection());              
              String[] selectedReportFieldList =  bean.getBaseFieldId().split("[|]");
              bean.setReportFieldCollection(adHocUsageReportProcess.getReportFieldNameFromId((Collection)request.getAttribute("reportFieldBeanCollection"),selectedReportFieldList));
			  request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());				
		  } else {
			  bean.setTemplateId(templateBean.getTemplateId());
			  getTemplateInfo(request,personnelBean,adHocUsageReportProcess,bean);
		  }

		  adHocUsageReportProcess.getDefaultReportDate(bean); 
		}else if ("open".equalsIgnoreCase(bean.getSubmitValue()) && !(bean.getSubmitReport() != null && bean.getSubmitReport().length() > 0)) {
		  //load dropdown data
		  loadDropdownData(request,genericReportProcess,personnelBean);
 
		  getTemplateInfo(request,personnelBean,adHocUsageReportProcess,bean);

		  adHocUsageReportProcess.getDefaultReportDate(bean);
		}else if ("submit".equalsIgnoreCase(bean.getSubmitValue())) {
			UsageReportTemplateBean templateBean = new UsageReportTemplateBean();
			BeanHandler.copyAttributes(bean, templateBean,getLocale(request));
			adHocUsageReportProcess.convertDateStringToDateObject(bean,templateBean);
			boolean timedOut = adHocUsageReportProcess.runReport(templateBean, personnelBean, response.getOutputStream());
			if ( ! timedOut && "interactive".equalsIgnoreCase(templateBean.getReportGenerationType())) {
				this.setExcelHeaderXlsx(response);
				adHocUsageReportProcess.writeWorkbookToBrowser();
			}
			return noForward;
        }else if ("clearTemplate".equalsIgnoreCase(bean.getSubmitValue())) {
			//load dropdown data
		   loadDropdownData(request,genericReportProcess,personnelBean);

			AdHocUsageInputBean tmp = new AdHocUsageInputBean();
			BeanHandler.copyAttributes(tmp, bean,getLocale(request));
			adHocUsageReportProcess.getDefaultReportDate(bean);
			request.setAttribute("gridType","list");
		}else {
			//load dropdown data
		    loadDropdownData(request,genericReportProcess,personnelBean);
			adHocUsageReportProcess.getDefaultReportDate(bean);
			request.setAttribute("gridType","list");
			request.setAttribute("adHocUsageInputBean",bean);
			request.setAttribute("preSelectDropsDowns","N");
		}

      this.saveTcmISToken(request);
    }
    return (mapping.findForward("success"));
  }

//	void getTemplateInfo(HttpServletRequest request,PersonnelBean personnelBean,AdHocUsageReportProcess adHocUsageReportProcess, AdHocUsageInputBean bean) {
//		try {
//		  BigDecimal templateId = new BigDecimal(0);
//		  if (!StringHandler.isBlankString(bean.getTemplateId())) {
//				templateId = new BigDecimal(bean.getTemplateId());
//		  }
//
//		  Collection templateData = adHocUsageReportProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
//		  adHocUsageReportProcess.copyData((Collection)request.getAttribute("baseFieldBeanCollection"),templateData,bean);
//		  request.setAttribute("reportFieldBeanCollection",bean.getReportFieldCollection());
//
//		  if ("Y".equalsIgnoreCase(bean.getAllowEdit())) {
//			  //can publish template
//			  PublishTemplateProcess process = new PublishTemplateProcess(this.getDbUser(request),getLocale(request));
//			  Collection col = process.getUserPublishUserGroups(personnelBean,templateId);
//			  if (personnelBean.getPermissionBean().hasFacilityPermission("PublishCompanyTemplate","",personnelBean.getCompanyId()) ||
//					personnelBean.getPermissionBean().hasFacilityPermission("PublishIndividualTemplate","",personnelBean.getCompanyId()) ||
//					col.size() > 0) {
//			  	bean.setPublishTemplate("Y");
//			  }
//			  //can unpublish template
//			  if ("PERSONNEL_ID".equalsIgnoreCase(bean.getOwner())) {
//			  	col = process.getTemplatePublishToUsers(templateId);
//				if (col.size() > 0) {
//					bean.setUnpublishTemplate("Y");
//				}
//			  }else if ("USER_GROUP_ID".equalsIgnoreCase(bean.getOwner())) {
//			  	bean.setUnpublishTemplate("Y");
//			  }else {
//				  if (personnelBean.getPermissionBean().hasFacilityPermission("PublishCompanyTemplate","",personnelBean.getCompanyId())) {
//					bean.setUnpublishTemplate("Y");
//				  }
//			  }
//		  }
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	void getTemplateInfo(HttpServletRequest request, PersonnelBean personnelBean,AdHocUsageReportProcess adHocUsageReportProcess, AdHocUsageInputBean bean) {
		try {
			BigDecimal templateId = new BigDecimal(0);
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				templateId = new BigDecimal(bean.getTemplateId());
			}
	        GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
	        Collection templateData = adHocUsageReportProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),templateId,null);
			//adHocUsageReportProcess.copyData((Collection)request.getAttribute("baseFieldBeanCollection"),templateData,bean);
	        adHocUsageReportProcess.copyData(genericReportProcess.getBaseFields("AdHocUsage"),genericReportProcess.getReportList(),templateData,bean);
				
			request.setAttribute("gridType","list");
			Vector<AdHocUsageInputBean> gridBeans = null;
			boolean isList = false;
			if(bean.getCasNum() != null && !StringHandler.isBlankString(bean.getCasNum()))
			{
				request.setAttribute("gridType","cas");
				gridBeans = (Vector<AdHocUsageInputBean>)adHocUsageReportProcess.getGridInfo(isList, bean.getTemplateId());
				request.setAttribute("casColl", gridBeans);
			}
			else if(bean.getListId() != null && !StringHandler.isBlankString(bean.getListId()))
			{
				isList = true;
				gridBeans = (Vector<AdHocUsageInputBean>)adHocUsageReportProcess.getGridInfo(isList, bean.getTemplateId());
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
				for(AdHocUsageInputBean b: gridBeans)
				{
					if(isList)
					{
						b.setListId(b.getId());						
						b.setHasThreshold(b.getHasThreshold());		
						b.setHasAmountThreshold(b.getHasAmountThreshold());	
						b.setListName(b.getName());
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
	  			  } else if ("USER_GROUP_ID".equalsIgnoreCase(bean.getOwner())) {
	  			  	bean.setUnpublishTemplate("Y");
	  			  } else {
	  				  if (personnelBean.getPermissionBean().hasFacilityPermission("PublishCompanyTemplate","",personnelBean.getCompanyId())) {
	  					bean.setUnpublishTemplate("Y");
	  			  }
	  		  }
	  		}
            
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
  void loadDropdownData(HttpServletRequest request, GenericReportProcess genericReportProcess, PersonnelBean personnelBean) throws BaseException {
	 try {
		 request.setAttribute("pageId", "adHocUsageRpt");
         AdHocInventoryReportProcess adHocInventoryReportProcess = new AdHocInventoryReportProcess(this.getDbUser(request),getLocale(request));
         if (personnelBean.getReportFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getReportFacilityAreaBuildingFloorRoomColl().size() == 0) {
            Collection facilityCollection = adHocInventoryReportProcess.getFacilityAreaBldgRm(personnelBean.getPersonnelIdBigDecimal(),personnelBean.getCompanyId());
            personnelBean.setReportFacilityAreaBuildingFloorRoomColl(facilityCollection);
            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
         }else {
            request.setAttribute("facAppReportViewBeanCollection",personnelBean.getReportFacilityAreaBuildingFloorRoomColl());
         }
         //show flammability & voc zones
         request.setAttribute("showFlammabilityVocZone",adHocInventoryReportProcess.getShowFlammabilityVocZone(personnelBean.getCompanyId()));
         //docks and delivery points
         request.setAttribute("facAppDockDpViewBeanCollection",genericReportProcess.getNormalizedDockDeliveryPoints(personnelBean.getPersonnelIdBigDecimal()));
         MsdsViewerProcess process = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
         request.setAttribute("facilityCatalogColl",process.getFacilityCatalogColl());
         //request.setAttribute("baseFieldBeanCollection",genericReportProcess.getBaseFields("AdHocUsage"));
         //request.setAttribute("vvCategoryBeanCollection",genericReportProcess.getMaterialCategories());
         //request.setAttribute("facAppReportViewBeanCollection", genericReportProcess.getNormalizedFacAppReportData(personnelId));
		 //request.setAttribute("listOptionBeanCollection", genericReportProcess.getReportList());
		 //request.setAttribute("reportFieldBeanCollection",new Vector(0));
	 }catch(Exception e) {
		throw new BaseException();
	 }
  }


}