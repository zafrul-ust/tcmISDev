package com.tcmis.internal.hub.action;

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
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CountScanSetupViewBean;
import com.tcmis.internal.hub.beans.ItemCountScanSheetInputBean;
import com.tcmis.internal.hub.process.ItemCountScanSheetProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class ItemCountScanSheetAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showitemcountscansheet");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}
	
	PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

	// Verify view permissions
	if (!user.getPermissionBean().hasUserPagePermission("itemCountScanSheet")) {
		return (mapping.findForward("nopermissions"));
	}

	//if form is not null we have to perform some action
	if (form == null) {

	}
	//if form is not null we have to perform some action
	else if (form != null &&
	 ( (DynaBean) form).get("submitSearch") != null &&
	 ( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {
	 //if (!this.isTokenValid(request, true)) {
	 //  BaseException be = new BaseException("Duplicate form submission");
	 //  be.setMessageKey("error.submit.duplicate");
	 //  throw be;
	 //}

	 //copy data from dyna form to the data form
	 ItemCountScanSheetInputBean bean = new ItemCountScanSheetInputBean();
	 BeanHandler.copyAttributes(form, bean);
	 ItemCountScanSheetProcess process = new ItemCountScanSheetProcess(this.
		getDbUser(request));
	 request.setAttribute("countScanSetupViewBeanCollection",
		process.getSearchData(bean, ( (PersonnelBean)this.getSessionObject(request,
		"personnelBean")).getHubInventoryGroupOvBeanCollection(),null));
	}
	else if (form != null &&
	 ( (DynaBean) form).get("submitPrint") != null &&
	 ( (String) ( (DynaBean) form).get("submitPrint")).length() > 0) {

	 ItemCountScanSheetProcess process = new ItemCountScanSheetProcess(this.
		getDbUser(request));
	 DynaBean dynaForm = (DynaBean) form;
	 List countScanSetupViewBeanBeans = (List) dynaForm.get(
		"countScanSetupViewBean");
	 Iterator iterator = countScanSetupViewBeanBeans.iterator();
	 int deleteCount = 0;
	 Collection countScanSetupViewInputBeanCollection = new Vector();
	 while (iterator.hasNext()) {
		org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
		 commons.beanutils.LazyDynaBean) iterator.next();
		CountScanSetupViewBean countScanSetupViewBean = new
		 CountScanSetupViewBean();
		BeanHandler.copyAttributes(lazyBean, countScanSetupViewBean);
		if (countScanSetupViewBean.getOk() != null) {
		 //log.debug("rowid     " + countScanSetupViewBean.getOk() + "");
		 deleteCount++;
		 countScanSetupViewInputBeanCollection.add(countScanSetupViewBean);
		}
	 }

	 ItemCountScanSheetInputBean bean = new ItemCountScanSheetInputBean();
	 BeanHandler.copyAttributes(form, bean);

	 /*Collection countScanSetupViewBeanCollection = process.getSearchData(bean,
		( (PersonnelBean)this.getSessionObject(request,
		"personnelBean")).getHubInventoryGroupOvBeanCollection(),countScanSetupViewInputBeanCollection);*/

	 String pdfUrl = process.buildScanSheet(countScanSetupViewInputBeanCollection);
	 if (pdfUrl.length() > 0) {
		request.setAttribute("filePath", pdfUrl);
		return mapping.findForward("viewfile");
	 }
	 else {
		return mapping.findForward("systemerrorpage");
	 }
	}
	return mapping.findForward("success");
 }
}