package com.tcmis.client.common.action;

import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;

import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.common.beans.ViewMsdsInputBean;
import com.tcmis.client.common.beans.MsdsLocaleViewBean;
import com.tcmis.client.common.process.ViewMsdsProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Controller for View MSDS page
 * @version 1.0
 ******************************************************************************/

public class ViewMsdsAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
/*		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "viewmsds");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
*/
		String uAction = request.getParameter("uAction");
		
		ViewMsdsProcess process = new ViewMsdsProcess(this.getDbUser(request),getTcmISLocaleString(request));
		ViewMsdsInputBean inputBean = new ViewMsdsInputBean();
		BeanHandler.copyAttributes(form, inputBean);
		
		HttpSession session = request.getSession(false);
     
        if ("viewMsds".equals(uAction)) {
            request.setAttribute("msdsUrl",process.getMsdsUrl(inputBean.getMaterialId(), inputBean.getLocaleCode(), inputBean.getRevisionDate()));
            
        	return (mapping.findForward("msdsPage"));
        }else if ("viewComposition".equals(uAction)) {
        	request.setAttribute("compositionColl",process.getCompositionInfo(inputBean.getRevisionDateTimeStamp(), inputBean.getMaterialId(), inputBean.getFacility()));
        	
        	CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
        	request.setAttribute("showCustomerChemicalId",catalogProcess.isFeatureReleased("ALL","ShowCustomerChemicalId"));
        	
        	return (mapping.findForward("compositionPage"));
        }else if ("viewProperties".equals(uAction)) {
        	request.setAttribute("materialProperties", process.getMaterialProperties(inputBean.getMaterialId(), inputBean.getLocaleCode(), inputBean.getRevisionDate()));
  
        	return (mapping.findForward("propertiesPage"));
        }else if ("viewShipping".equals(uAction)) {
        	request.setAttribute("shippingInfo", process.getShippingInfo(inputBean.getMaterialId()));
  
        	return (mapping.findForward("shippingPage"));
        }else if ("viewLists".equals(uAction)) {
       // 	String revisionDate = request.getParameter("revisionDate");
       // 	request.setAttribute("listColl", process.getListInfo(revisionDate, materialId, facility, "lists", null));
        	request.setAttribute("materialListCollection", process.getMaterialLists(inputBean));
        	return (mapping.findForward("listsPage"));
        }else if ("viewReportList".equals(uAction)) {
        	request.setAttribute("reportListCollection", process.getReportListCollection(request,inputBean,request.getParameter("lookupListId")));
        	request.setAttribute("reportListName", request.getParameter("listName"));
        	request.setAttribute("reportSublistName", request.getParameter("sublistName"));
        	request.setAttribute("onlist", request.getParameter("onlist"));
        	return (mapping.findForward("success"));
        }else if ("getMSDSInfo".equals(uAction)) {
            Vector<MsdsLocaleViewBean> titleInfo = (Vector<MsdsLocaleViewBean>) process.getTitleInfo(inputBean);
        	if(titleInfo != null && titleInfo.size() > 0) {
	        	request.setAttribute("titleInfo", titleInfo.get(0));
	        	Vector<MsdsLocaleViewBean> sideViewInfo = (Vector<MsdsLocaleViewBean>) process.getSideViewInfo(inputBean, isModuleHaas(request));
	        	if (inputBean.getRevisionDate() == null) {
	                SimpleDateFormat fmt = new SimpleDateFormat("dd/MMM/yyyy");
	                inputBean.setRevisionDate(fmt.format(((MsdsLocaleViewBean)sideViewInfo.firstElement()).getRevisionDate()));
	            }
	        	if(sideViewInfo != null && sideViewInfo.size() > 0)
	        		request.setAttribute("sideViewInfo", sideViewInfo.get(0));
	        	request.setAttribute("notice", process.getNotice(inputBean));
                request.setAttribute("revisionMeetsCompanyStandard", process.getRevisionMeetsCompanyStandard(inputBean));
            }
        	else {
        		request.setAttribute("titleInfo", null);
        	}
        	return (mapping.findForward("getMSDSInfo"));
        }else if ("uploadRevision".equals(uAction)) {
            
            // 	request.setAttribute("listColl", process.getListInfo(revisionDate, materialId, facility, "lists", null));
            return (mapping.findForward("msdsUploadRevision"));
        }else if ("submitRevision".equals(uAction)) {
        	if(inputBean.getTheFile() != null && !FileHandler.isValidUploadFile(inputBean.getTheFile())) {
        		request.setAttribute("errMsg", "File type not allowed.");
        	} else {
	        	String errMsg = process.sendNewRevisionRequest(inputBean);
	        	if("".equals(errMsg))
	        		request.setAttribute("done", "OK");
	        	else	
	        		request.setAttribute("errMsg", errMsg);
        	}
       //     request.setAttribute("listColl", process.getListInfo(revisionDate, materialId, facility, "lists", null));
            return (mapping.findForward("uploadRevision"));    
        }else if (inputBean.getMaterialId() != null || (inputBean.getItemId() != null)){
        	if (inputBean.getItemId() != null) {
                request.setAttribute("itemMaterialColl", process.getMaterialsByItem(inputBean));
              }
            //load initial data
        	String localeCode = process.getLocaleCode(session, request, inputBean.getFacility());
            inputBean.setLocaleCode(localeCode);
            process.calculateLatestRevisionDateAndLocale(inputBean);
            Vector<MsdsLocaleViewBean> titleInfo = (Vector<MsdsLocaleViewBean>) process.getTitleInfo(inputBean);
        	if(titleInfo != null && titleInfo.size() > 0) {
	        	request.setAttribute("titleInfo", titleInfo.get(0));
	        	Vector<MsdsLocaleViewBean> sideViewInfo = (Vector<MsdsLocaleViewBean>) process.getSideViewInfo(inputBean, isModuleHaas(request));
	        	if(sideViewInfo != null && sideViewInfo.size() > 0)
	        		request.setAttribute("sideViewInfo", sideViewInfo.get(0));
	        	
	        	request.setAttribute("revisionDateColl", process.getRevisionDateDropdownColl(inputBean.getMaterialId(), inputBean.getFacility(), isModuleHaas(request)));
	        	request.setAttribute("notice", process.getNotice(inputBean));
                request.setAttribute("revisionMeetsCompanyStandard", process.getRevisionMeetsCompanyStandard(inputBean));
                //passing the selected MSDS
                request.setAttribute("selectedRevisionDate",inputBean.getRevisionDate());
                request.setAttribute("selectedRevisionDateTimestamp",inputBean.getRevisionDateTimeStamp());
                request.setAttribute("selectedLocaleCode",inputBean.getLocaleCode());
            }else {
        		request.setAttribute("titleInfo", null);
        	}	
        	
        	CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request),getTcmISLocaleString(request));
        	String active = StringHandler.isBlankString(inputBean.getFacility()) ? catalogProcess.isFeatureReleasedAnyFacility("HideMsdsViewerActionOptions") : catalogProcess.isFeatureReleased(inputBean.getFacility(), "HideMsdsViewerActionOptions");
        	String hideMsdsViewerActionOptions = "N";
            if("Y".equalsIgnoreCase(active) && !this.isLoggedIn(request))
        		hideMsdsViewerActionOptions =  "Y";

            request.setAttribute("hideMsdsViewerActionOptions",hideMsdsViewerActionOptions);
            ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");
        	request.setAttribute("hostUrl", resource.getString("hosturl"));
        }
        return (mapping.findForward("success"));
		
	}

	private Boolean isModuleHaas(HttpServletRequest request) {
		String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
		Boolean IsModuleHaas = "/haas".equals(module);
		return IsModuleHaas;
	}
}   //end of class