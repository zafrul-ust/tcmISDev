package com.tcmis.internal.catalog.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.KitIndexingQueueInputBean;
import com.tcmis.internal.catalog.beans.KitIndexingQueueViewBean;
import com.tcmis.internal.catalog.process.KitIndexingQueueProcess;

public class KitIndexingQueueAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "kitindexingqueue");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// set the standalone flag to true since MSDS Maintenance should work as a standalone app
			request.setAttribute("standalone", "true");
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			/*if (!personnelBean.getPermissionBean().hasUserPagePermission("kitIndexingQueue")) {
				return (mapping.findForward("nopermissions"));
			}*/
			
			// if form is not null we have to perform some action
			if (form != null) {
				// copy date from dyna form to the data form
				KitIndexingQueueInputBean bean = new KitIndexingQueueInputBean();
				BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
	
				KitIndexingQueueProcess process = new KitIndexingQueueProcess(this.getDbUser(request), getTcmISLocaleString(request));
				
				String action = (String) ((DynaBean) form).get("uAction");
				if ("search".equalsIgnoreCase(action)) {
					request.setAttribute("kitCollection", process.getSearchResult(bean));
					request.setAttribute("searchCriteria", bean);
                }else if ("getNewKitNumber".equalsIgnoreCase(action)) {
                    request.setAttribute("newKitNumber", process.getNewKitNumber());
                    request.setAttribute("kitCollection", new Vector(0));
					request.setAttribute("searchCriteria", bean);
                } else if ("createExcel".equalsIgnoreCase(action)) {
					this.setExcel(response,"KitIndexingQueue");
					process.getExcelReport(bean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
					return noForward;
				}else if ("update".equalsIgnoreCase(action)){
					KitIndexingQueueViewBean updateBean = new KitIndexingQueueViewBean();
					Collection<KitIndexingQueueViewBean> rows = BeanHandler.getGridBeans((DynaBean) form, "KitIndexingQueueViewBean", updateBean, (java.util.Locale)request.getSession().getAttribute("tcmISLocale"), "ok");
				    process.updateQueue(rows);
					request.setAttribute("kitCollection", process.getSearchResult(bean));
				}else {
					request.setAttribute("catalogCol", process.getCompanyCatalogs(personnelBean.getPersonnelId()));
				}
			}
		}
		return next;
	}
}
