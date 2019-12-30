package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.UsageImportConversionViewBean;
import com.tcmis.client.catalog.beans.UsageImportInputBean;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.catalog.process.UsageImportInfoProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.report.process.ClientChemListProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;


/******************************************************************************
 * Controller for Usage Import Info page
 * @version 1.0
 ******************************************************************************/



public class UsageImportInfoAction extends TcmISBaseAction {
	
	private void doSearch(UsageImportInfoProcess process,  UsageImportInputBean inputBean , PersonnelBean user) throws BaseException, Exception{
     	Collection coll = process.getSearchResults(inputBean);
     	
     	this.saveTcmISToken(request);
        request.setAttribute("usageImportConversionBeanColl",coll);
        request.setAttribute("uomColl",process.getUOMColl(inputBean.getFacilityId()));
        request.setAttribute("vocetFugitiveCategoryColl",process.getVocetFugitiveCategoryColl(inputBean.getFacilityId())); 
        CabinetDefinitionManagementProcess cDMProcess = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
        request.setAttribute("vvUnidocsStorageContainerBeanColl",cDMProcess.getVvUnidocsStorageContainerBeanColl());
		request.setAttribute("vvTierIITemperatureCodeBeanColl",cDMProcess.getVvTierIITemperatureCodeBeanColl());
		ClientChemListProcess clientChemListProcess = new ClientChemListProcess(this.getDbUser(request), getTcmISLocaleString(request));
		request.setAttribute("purchasingMethodBeanColl",clientChemListProcess.getPurchasingMethod(user.getCompanyId(), inputBean.getFacilityId()));
	}
	
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "usageimportinfomain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
        if (!personnelBean.getPermissionBean().hasUserPagePermission("usageImportInfo"))
        {
            return (mapping.findForward("nopermissions"));
        }

        String uAction = request.getParameter("uAction");
		
		UsageImportInfoProcess process = new UsageImportInfoProcess(this.getDbUser(request),getTcmISLocaleString(request));
		
        if ("search".equals(uAction)) {
        	UsageImportInputBean inputBean = new UsageImportInputBean();
    		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
    		doSearch(process,inputBean,personnelBean);
            
        } else if ("changeCustomerMsdsDb".equals(uAction)) {
        	String facilityId = request.getParameter("facilityId");
        	Collection resultColl = process.getCompanyIdandCustomerMsdsDb(facilityId);
        	     
            request.setAttribute("resultColl",resultColl);
            return (mapping.findForward("setValues"));
        }
        else if ("update".equals(uAction)) {
        	UsageImportInputBean inputBean = new UsageImportInputBean();
    		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			/*Need to check if the user has permissions to update this data. if they do not have the permission
			we show a error message.
    		if (!user.getPermissionBean().hasInventoryGroupPermission("BuyOrder", null, null, null)) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				// repopulate the search results
				request.setAttribute("poExpediteViewBeanColl", process.getSearchResult(input, user));
				return (mapping.findForward("success"));
			}*/
    		
			// If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);

			// get the data from grid
			Collection<UsageImportConversionViewBean> beans = BeanHandler.getBeans((DynaBean) form, "usageImportConversionBean", new UsageImportConversionViewBean(), getTcmISLocale(request));

			String errorMsgs = process.update(beans, personnelBean);
			
			if(errorMsgs.length() > 0)
				request.setAttribute("tcmISErrors", errorMsgs);
			
			// After update the data, we do the re-search to refresh the window
    		doSearch(process,inputBean,personnelBean);

        } else if ("dataDupCheck".equals(uAction)) {
        	UsageImportConversionViewBean validateBean = new UsageImportConversionViewBean();
			BeanHandler.copyAttributes(form, validateBean, getTcmISLocale(request));
			request.setAttribute("count",process.getDupCount(validateBean));
            
            return (mapping.findForward("setDupCount"));
		} else if ("createExcel".equals(uAction)) {
			UsageImportInputBean inputBean = new UsageImportInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
            this.setExcel(response, "UsageImportConversionExcel");
            process.getExcelReport(personnelBean,inputBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale"),this.getDbUser(request)).write(response.getOutputStream());
            return noForward;
        } else {
        	OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
        	request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
        }
		return (mapping.findForward("success"));
	}
}   //end of class
