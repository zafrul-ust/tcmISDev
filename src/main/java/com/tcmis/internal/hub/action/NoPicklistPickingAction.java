package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
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
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.PickViewBean;
import com.tcmis.internal.hub.beans.PickingInputBean;
import com.tcmis.internal.hub.process.AllocationAnalysisProcess;
import com.tcmis.internal.hub.process.NoPicklistPickingProcess;

/******************************************************************************
 * Controller for No Picklist Picking
 * @version 1.0
	******************************************************************************/

public class NoPicklistPickingAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {
	String forward = "success";

	if (!this.isLoggedIn(request,true)) {
	 request.setAttribute("requestedPage", "nopicklistpickingmain");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = ( (PersonnelBean)this.getSessionObject(request,
	 "personnelBean"));
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	PermissionBean permissionBean = personnelBean.getPermissionBean();
	if (!permissionBean.hasFacilityPermission("Issuing", null, null)) {
	 return mapping.findForward("nopermissions");
	}

	if (form != null) {
	 //copy date from dyna form to the data form
	 PickingInputBean bean = new PickingInputBean();
	 BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
	 NoPicklistPickingProcess process = new NoPicklistPickingProcess(this.getDbUser(request),getTcmISLocaleString(request));
	 //If the search button was pressed the getSubmitSearch() value will be not null
	 if (bean.getSubmitSearch() != null &&
		bean.getSubmitSearch().trim().length() > 0) {
		request.setAttribute("pickColl", process.getNormalizedPickCollection(bean));
		this.saveTcmISToken(request);
		return (mapping.findForward("results"));
	 }
	 else if (bean.getConfirm() != null && bean.getConfirm().trim().length() > 0) {
		if (!this.isTcmISTokenValid(request, true)) {
		 BaseException be = new BaseException("Duplicate form submission");
		 be.setMessageKey("error.submit.duplicate");
		 throw be;
		}
		this.saveTcmISToken(request);

		DynaBean dynaForm = (DynaBean) form;
		List returnBeans = (List) dynaForm.get("picklistBean");
		Iterator iterator = returnBeans.iterator();
		Collection returnBeanInputCollection = new Vector();
		while (iterator.hasNext()) {
		 LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
		 PickViewBean pickViewBean = new PickViewBean();
		 BeanHandler.copyAttributes(lazyBean, pickViewBean, getTcmISLocale(request));
		 returnBeanInputCollection.add(pickViewBean);
		}

		Collection c = new Vector();
		c = process.getNormalizedPickCollection(returnBeanInputCollection);
		HashMap pickingResult = new HashMap();
		pickingResult = process.confirmPicks(c, personnelId);
		Collection errorCodes = (Collection) pickingResult.get("errorCodes");
		Collection pickedMrs = (Collection) pickingResult.get("pickedMrs");
		request.setAttribute("pickedMrColl", pickedMrs);
		 /*This is to release the page quickly, can be removed after moving to new code*/
		if (pickedMrs != null && pickedMrs.size() > 0) {
		 this.setSessionObject(request, process.bolPrintData(pickedMrs),"pickedItems");
		}

		request.setAttribute("pickColl", process.getNormalizedPickCollection(bean));
		if (errorCodes != null && errorCodes.size() > 0) {
		 //log.debug("setting errors attribute: pickingErrorColl");
		 request.setAttribute("pickingErrorColl", errorCodes);
		}
		else
		{
		 request.setAttribute("pickingErrorColl", null);
		}

		return (mapping.findForward("results"));
	 }
   else if (bean.getUserAction() != null && bean.getUserAction().equalsIgnoreCase("nopicklistpackinglist"))
   {
    request.setAttribute("picklistPrintColl", process.getIssueHistory(bean.getBatchNumber()));
		return (mapping.findForward("success"));
   }
   else {
		//populate drop downs
		AllocationAnalysisProcess aaProcess = new AllocationAnalysisProcess(this.getDbUser(request),getTcmISLocaleString(request));
		request.setAttribute("hubInventoryGroupFacOvBeanCollection",
		 aaProcess.getHubDropDownData(personnelId));
	 }
	}
	return (mapping.findForward("success"));
 }
}