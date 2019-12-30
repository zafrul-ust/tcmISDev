package com.tcmis.internal.hub.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ItemCountInventoryViewBean;
import com.tcmis.internal.hub.beans.ItemManagementInputBean;
import com.tcmis.internal.hub.process.ItemManagementProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

/******************************************************************************
 * Controller for ItemManagement
 * @version 1.0
	******************************************************************************/

public class ItemManagementAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "itemmanagementmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
	 "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("newitemManagement"))
    {
      return (mapping.findForward("nopermissions"));
    }
	
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

	if (form != null) {
	 ItemManagementProcess process = new ItemManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
	 ItemManagementInputBean inputBean = new ItemManagementInputBean();
	 BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
	 String userAction = "none";

	 if ( ( (DynaBean) form).get("userAction") != null) {
		userAction = (String) ( (DynaBean) form).get("userAction");
	 }

	 if (userAction.equals("update")) {
/*
 * 
 		if (!this.isTcmISTokenValid(request, true)) {
		 BaseException be = new BaseException("Duplicate form submission");
		 be.setMessageKey("error.submit.duplicate");
		 throw be;
		}
		this.saveTcmISToken(request);
*/
		DynaBean dynaBean = (DynaBean) form;

		Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form,"itemInvDetailViewBean", new ItemCountInventoryViewBean(), getTcmISLocale(request));
		Collection results = process.updateItemInvColl(updateBeanCollection, personnelId);
		Iterator resultsIter = results.iterator();
		Collection errorMessageCollection = null;
		if (resultsIter.hasNext()) {
		 errorMessageCollection = (Collection) resultsIter.next();
		}
		request.setAttribute("errorMessageCollection", errorMessageCollection);

		Collection itemInvDetailViewBeanColl = process.getSearchResult(
		 inputBean);
		request.setAttribute("itemInvDetailViewBeanColl",
		 itemInvDetailViewBeanColl);
		return (mapping.findForward("success"));
	 }
	 else if (userAction.equals("search")) {
		this.saveTcmISToken(request);
		Collection itemInvDetailViewBeanColl = process.getSearchResult(
		 inputBean);
		request.setAttribute("itemInvDetailViewBeanColl",
		 itemInvDetailViewBeanColl);
		return (mapping.findForward("success"));
	 }
	 else if (userAction.equals("createExcel")) {
		 this.setExcel(response,"ItemManagement");
		ItemManagementProcess itemManagementProcess = new ItemManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
		itemManagementProcess.createExcelFile(inputBean,personnelBean.getLocale()).write(response.getOutputStream());
		return noForward;
	 }
	 else {
		Collection igOvBeanCollection = process.getItemMgmtIgOvBeanCollection(personnelId);
		request.setAttribute("itemManagementIgOvColl", igOvBeanCollection);
		this.saveTcmISToken(request);
		return (mapping.findForward("success"));
	 }
	}
	return (mapping.findForward("success"));
 }
}