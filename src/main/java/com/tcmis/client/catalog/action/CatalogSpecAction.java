package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogSpecInputBean;
import com.tcmis.client.catalog.process.CatalogSpecProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for catalog spec
 * 
 * @version 1.0
 ******************************************************************************/
public class CatalogSpecAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "catalogspecmain");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/*Need to check if the user has permissions to view this page. if they do not have the permission
        we need to not show them the page.*/
        if (!personnelBean.getPermissionBean().hasUserPagePermission("specUpdate"))
        {
            return (mapping.findForward("nopermissions"));
        }
		
		String forward = "success";

		CatalogSpecProcess process = new CatalogSpecProcess(this.getDbUser(request), getTcmISLocaleString(request));

		//if form is not null we have to perform some action
		if (form == null) {
			//this.saveToken(request);
			forward = "main";
		}
		//if form is not null we have to perform some action
		else if (((DynaBean) form).get("action") != null && ((String) ((DynaBean) form).get("action")).equalsIgnoreCase("start")) {
			request.setAttribute("personnelCatalogViewBeanCollection", process.getCatalogDropDown(new BigDecimal(this.getPersonnelId(request))));
			forward = "search";
		}
		else if (((DynaBean) form).get("action") != null && ((String) ((DynaBean) form).get("action")).equalsIgnoreCase("update")) {
			//log.debug("update");
			Collection c = new Vector();
			DynaBean dynaBean = (DynaBean) form;
			List catalogSpecInputBeans = (List) dynaBean.get("catalogSpecInputBean");
			//log.debug("catalogSpecInputBeans:" + catalogSpecInputBeans);
			if (catalogSpecInputBeans != null) {
				Iterator iterator = catalogSpecInputBeans.iterator();
				//int count = 0;
				while (iterator.hasNext()) {
					CatalogSpecInputBean inputBean = new CatalogSpecInputBean();
					org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();
					//log.debug(lazyBean.get("coc"));
					BeanHandler.copyAttributes(lazyBean, inputBean, getTcmISLocale(request));
					inputBean.setPersonnelId(new BigDecimal(this.getPersonnelId(request)));

					c.add(inputBean);
				}
			}
			process.update(c);
			CatalogSpecInputBean bean = new CatalogSpecInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			request.setAttribute("catalogSpecViewBeanCollection", process.getSearchData(bean));
			forward = "result";
		}
		else if (((DynaBean) form).get("action") != null && ((String) ((DynaBean) form).get("action")).equalsIgnoreCase("history")) {
			log.debug("history");
			CatalogSpecInputBean bean = new CatalogSpecInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			request.setAttribute("catalogSpecHistoryBeanCollection", process.getHistory(bean));
			forward = "viewhistory";
		}
		else if (((DynaBean) form).get("action") != null && ((String) ((DynaBean) form).get("action")).equalsIgnoreCase("createExcel")) {
			//log.debug("Creating excel");
			CatalogSpecInputBean bean = new CatalogSpecInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			this.setExcel(response, "CatalogSpec");
			process.getExcelReport(bean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else if (((DynaBean) form).get("submitSearch") != null && ((String) ((DynaBean) form).get("submitSearch")).length() > 0) {
			//log.debug("search");
			CatalogSpecInputBean bean = new CatalogSpecInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			request.setAttribute("catalogSpecViewBeanCollection", process.getSearchData(bean));
			forward = "result";
		}
		if (log.isDebugEnabled()) {
			log.debug("forward:" + forward);
		}
		return mapping.findForward(forward);
	}
}