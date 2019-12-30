package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.client.common.beans.MsdsViewerInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for MSDS viewer Search page
 * @version 1.0
 ******************************************************************************/

public class AdvancedMsdsViewerAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "advancedmsdsviewermain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

        PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
        
        if (!personnelBean.getPermissionBean().hasUserPagePermission("advancedMsds"))
        {
          return (mapping.findForward("nopermissions"));
        }

        String uAction = request.getParameter("uAction");
        MsdsViewerProcess process = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
        MsdsViewerInputBean inputBean = new MsdsViewerInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
        if ("search".equals(uAction)) {
            Collection coll = process.getSearchResults(inputBean,personnelBean);
            if(coll != null && coll.size() > 0) {
                if ("Y".equals(process.allowMixture( inputBean.getFacilityId() ) ) ) {
		            request.setAttribute("showMixture","Y");
                }

        //        process.calculatingNumberOfKitsPerItem(coll);
            }   
            request.setAttribute("msdsViewerBeanCollection",coll);
        }else if ("createExcel".equals(uAction)) {
        	Collection coll = process.getSearchResults(inputBean,personnelBean);
            this.setExcel(response, "MsdsViewerDisplayExcel");
            process.getExcelReport(inputBean,(Collection<MsdsSearchDataTblBean>) coll,(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
            return noForward;
        }
        else if("showtextarea".equals(uAction))
        {
        	return (mapping.findForward("showtextarea"));
        }
        else {
    		request.setAttribute("pageId", "msdsAnalysis");
			request.setAttribute("gridType","list");
        	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    		
			request.setAttribute("facilityWorkareaColl", process.getMyWorkareaFacilityViewBean(personnelId));
			request.setAttribute("vocUnitColl", process.getVvVocUnit());
        	//load initial data
            // request.setAttribute("facilityWorkareaColl", process.getFacilityWorkareaColl());
            request.setAttribute("physicalStateColl",process.getPhysicalStateColl());
            request.setAttribute("ppColl",process.getPPColl());
            //request.setAttribute("listColl",process.getListColl());
            request.setAttribute("specificHazardColl",process.getSpecificHazardColl()); 
            request.setAttribute("vaporPressureUnitColl",process.getVaporPressureUnitColl());
        }
		return (mapping.findForward("success"));
	}
}   //end of class
