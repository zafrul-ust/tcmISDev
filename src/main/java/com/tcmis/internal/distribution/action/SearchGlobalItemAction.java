package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import java.util.Vector;

import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.action.SearchMsdsAction;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.process.ItemCatalogProcess;

public class SearchGlobalItemAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception 
			{
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "searchglobalitem");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
						
		if (form == null) 
		{  
			return (mapping.findForward("success"));
		}

		CatalogInputBean bean = new CatalogInputBean();
		BeanHandler.copyAttributes(form, bean);
		String action =  (String)( (DynaBean) form).get("uAction");			//
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
		
		if(personnelBean.isFeatureReleased("MaterialItemOnly",bean.getFacilityId(),personnelBean.getCompanyId()))
		{
			if ( "search".equals(action) ) {
				if("Y".equalsIgnoreCase(bean.getShowMaterialOnly()))
				{
					ActionForward searchMsdsActionForward = new SearchMsdsAction().executeAction(mapping,
	                        form,
	                        request,
	                        response); 
					if("Success".equalsIgnoreCase(searchMsdsActionForward.getName()));
					{
						request.setAttribute("resultsOnlyCalledFrom", "newCatalogAddProcess");
						return (mapping.findForward("materialSuccess"));
					}
				}
				else
				{
					this.setSessionObject(request, new Vector(), "clientLabelBeanCollection");
					ItemCatalogProcess itemCatalogProcess = new ItemCatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
					request.setAttribute("itemCatalogBeanCollection", itemCatalogProcess.searchItemOnlyCatalog(bean));
				}
	
				this.saveTcmISToken(request);
			}
			else if ( action.equals("createXSL")||action.equals("createExcel")) {
				if("Y".equalsIgnoreCase(bean.getShowMaterialOnly()))
				{
					return new SearchMsdsAction().executeAction(mapping,
	                        form,
	                        request,
	                        response);
				}
				else
				{
					try {
							this.setExcel(response,"Search Global Item");
							ItemCatalogProcess itemCatalogProcess = new ItemCatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
							itemCatalogProcess.getItemExcelReport(bean).write(response.getOutputStream());
						}
						catch (Exception ex) {
							ex.printStackTrace();
							return mapping.findForward("genericerrorpage");
						}
					
					return noForward;
				}
			}
		}
		else
		{
			if ( "search".equals(action) ) {
					this.setSessionObject(request, new Vector(), "clientLabelBeanCollection");
					ItemCatalogProcess itemCatalogProcess = new ItemCatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
					request.setAttribute("itemCatalogBeanCollection", itemCatalogProcess.searchGlobalItemCatalog(bean));

				this.saveTcmISToken(request);
			}
			else if ( action.equals("createXSL")||action.equals("createExcel")) {
					try {
							this.setExcel(response,"Search Global Item");
							ItemCatalogProcess itemCatalogProcess = new ItemCatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
							itemCatalogProcess.getItemExcelReport(bean).write(response.getOutputStream());
						}
						catch (Exception ex) {
							ex.printStackTrace();
							return mapping.findForward("genericerrorpage");
						}
					
					return noForward;
			}
		}
		
		return (mapping.findForward("success"));
	}
}
