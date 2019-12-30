package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.client.common.beans.MsdsViewerInputBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Controller for MSDS viewer Search page
 * @version 1.0
 ******************************************************************************/

public class MsdsViewerAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

        if( form == null ) return (mapping.findForward("success"));

        String uAction = request.getParameter("uAction");
        PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
        MsdsViewerProcess process = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
        MsdsViewerInputBean inputBean = new MsdsViewerInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
        if ("search".equals(uAction)) {
            Collection coll = process.getSearchResults(inputBean,personnelBean);
            if(coll != null && coll.size() > 0) {
		        request.setAttribute("showMixture",process.allowMixture( inputBean.getFacilityId()));
                request.setAttribute("showFacilityUseCode",process.showFacilityUseCode(inputBean.getFacilityId()));
                CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request),getTcmISLocaleString(request));
                request.setAttribute("showMSDS",catalogProcess.isFeatureReleased("ALL","ShowKitMaterialMsds"));
                request.setAttribute("showCASNumber",catalogProcess.isFeatureReleased("ALL","ShowCASNumber"));
            }else {
                request.setAttribute("showMixture","N");
                request.setAttribute("showFacilityUseCode","N");
                request.setAttribute("showMSDS","N");
                request.setAttribute("showCASNumber","N");
            }
  
            if("Y".equals(inputBean.getKitOnly()) && coll != null && coll.size() > 0) {
                process.calculatingNumberOfKitsPerMsds(coll);
            	request.setAttribute("itemCatalogBeanCollection",coll);
            	request.setAttribute("fromAdvancedMSDSSearch","Y");
                if(request.getParameter("pageId") != null && request.getParameter("pageId").equalsIgnoreCase("advMsds"))
        			return (mapping.findForward("advancedOnlyKitMSDS"));
                else
                	return (mapping.findForward("onlyKitMSDS"));
            }else {
            	request.setAttribute("msdsViewerBeanCollection",coll);
            }
        }else if ("createExcel".equals(uAction)) {
        	Collection coll = process.getSearchResults(inputBean,personnelBean);
        	
        	this.setExcel(response, "MsdsViewerDisplayExcel");
        	if("Y".equals(inputBean.getKitOnly())) 
        		process.getKitExcelReport(inputBean,(Collection<MsdsSearchDataTblBean>) coll,personnelBean,(java.util.Locale) request.getSession().getAttribute("tcmISLocale"),this.getDbUser(request)).write(response.getOutputStream());
        	else 
	            process.getExcelReport(inputBean,(Collection<MsdsSearchDataTblBean>) coll,(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
            return noForward;
        }else {
            //load initial data
            request.setAttribute("facilityWorkareaColl", process.getFacilityWorkareaColl());
            CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request),getTcmISLocaleString(request));
            request.setAttribute("showCASNumber",catalogProcess.isFeatureReleased("ALL","ShowCASNumber"));
        }
        if(request.getParameter("pageId") != null && request.getParameter("pageId").equalsIgnoreCase("advMsds"))
    			return (mapping.findForward("advancedsuccess"));
        	else
        		return (mapping.findForward("success"));
	}
}   //end of class
