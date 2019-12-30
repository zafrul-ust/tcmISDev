package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.CatalogItemSynonymInputBean;
import com.tcmis.internal.distribution.beans.CatalogItemSynonymViewBean;
import com.tcmis.internal.distribution.process.CatalogItemSynonymProcess;


public class CatalogItemSynonymAction extends TcmISBaseAction
{
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
			if (!this.isLoggedIn(request)) {
			 request.setAttribute("requestedPage", "catalogitemsynonymmain");
			 request.setAttribute("requestedURLWithParameters",
				this.getRequestedURLWithParameters(request));
			 return (mapping.findForward("login"));
			}
   
			CatalogItemSynonymProcess synonymProcess = new CatalogItemSynonymProcess(this.getDbUser(request),getTcmISLocaleString(request));
			// copy date from dyna form to the data bean
			CatalogItemSynonymInputBean inputBean = new CatalogItemSynonymInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
			/*Need to check if the user has permissions to view this page. if they do not have the permission
	        we need to not show them the page.*/
	        if (!personnelBean.getPermissionBean().hasUserPagePermission("catalogItemSynonym"))
	        {
	            return (mapping.findForward("nopermissions"));
	        }

			if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction()))
	        {
	             Collection<CatalogItemSynonymViewBean> itemSynonymColl = synonymProcess.getCatalogItemSynonyms(inputBean,personnelBean);
	             request.setAttribute("itemSynonymViewBeanColl", itemSynonymColl);
	             this.saveTcmISToken(request);
	             return (mapping.findForward("success"));

	       }
			if (inputBean.getuAction() !=null &&  "update".equals(inputBean.getuAction()))
	        {
				checkToken(request);
				
				Collection<CatalogItemSynonymViewBean> itemNotesCollection = BeanHandler.getBeans((DynaBean) form,"catalogItemSynonymBean", new CatalogItemSynonymViewBean(),getTcmISLocale(request));
              
				String errorMsg =   synonymProcess.updateSynonym(itemNotesCollection);
				if (!"OK".equalsIgnoreCase(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
          	 
          	 	 request.setAttribute("itemSynonymViewBeanColl",synonymProcess.getCatalogItemSynonyms(inputBean, personnelBean));
          	 	 
          		 return (mapping.findForward("success"));
	           
	       }
			if (inputBean.getuAction() !=null &&  "submit".equals(inputBean.getuAction())){
				
				CatalogItemSynonymViewBean catalogItemSynonymViewBean =new CatalogItemSynonymViewBean();
                /*TODO Remove these.*/
                catalogItemSynonymViewBean.setCatalogCompanyId("HAAS");
				catalogItemSynonymViewBean.setCatalogId("Global");
			    BeanHandler.copyAttributes(form, catalogItemSynonymViewBean, getTcmISLocale(request));

			     synonymProcess.addSynonym(catalogItemSynonymViewBean, personnelBean);        		 
	        	 request.setAttribute("done", "Y");
				
			}
	         else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction()))
	         {
	             this.setExcel(response, "CatalogSynonymExcel");
	             synonymProcess.getExcelReport(synonymProcess.getCatalogItemSynonyms(inputBean, personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	             return noForward;
	        }
					
			return (mapping.findForward("success"));
		 }
		}
