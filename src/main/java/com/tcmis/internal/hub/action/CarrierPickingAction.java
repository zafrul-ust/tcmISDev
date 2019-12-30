package com.tcmis.internal.hub.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.PicklistSelectionViewBean;
import com.tcmis.internal.hub.process.InventoryGroupCarrierOvProcess;
import com.tcmis.internal.hub.process.PicklistPickingProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * Controller for PicklistPicking
 * @version 1.0
 ******************************************************************************/
public class CarrierPickingAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "carrierpickingmain");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		String userAction = null;
		if (form == null
				|| (userAction = ((String) ((DynaBean) form).get("action"))) == null) {
			return (mapping.findForward("success"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");

		if (!personnelBean.getPermissionBean().hasUserPagePermission("carrierPicking"))
		{
			return (mapping.findForward("nopermissions"));
		}

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		//if form is not null we have to perform some action
		if (userAction.equals("display")) {
			InventoryGroupCarrierOvProcess aaProcess = new InventoryGroupCarrierOvProcess(this.getDbUser(request),getTcmISLocaleString(request));
			request.setAttribute("InventoryGroupCarrierOvColl", aaProcess.getSearchResult(personnelId));
			request.setAttribute("invgrpColl", personnelBean.getHubInventoryGroupOvBeanCollection() );
			return (mapping.findForward("success"));
		}

		PicklistPickingProcess process = new PicklistPickingProcess(this.getDbUser(request),getTcmISLocaleString(request));
		DynaBean dynaBean = (DynaBean) form;
		String forward = "success";
		PicklistSelectionViewBean bean = new PicklistSelectionViewBean();
		BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
		
		if (userAction.equals("search")) {

			this.saveTcmISToken(request);
			request.setAttribute("picklistColl", process.getSearchResult(bean,personnelBean));
		}
		else if ( userAction.equalsIgnoreCase("generate") ) {
			if (!this.isTcmISTokenValid(request, true)) {
				BaseException be = new BaseException("Duplicate form submission");
				be.setMessageKey("error.submit.duplicate");
				throw be;
			}
			this.saveTcmISToken(request);

			Collection c = BeanHandler.getBeans(dynaBean,"picklistBean", new PicklistSelectionViewBean(), getTcmISLocale(request));

			Object[] results = process.createPicklist(bean,c,personnelBean,(java.util.Locale) request.getSession().getAttribute("tcmISLocale"));
			request.setAttribute("picklistId", results[0]);
			request.setAttribute("errorCodes", results[1]);  
			request.setAttribute("picklistColl", process.getSearchResult(bean, personnelBean));      
		} 
		else if ( userAction.equalsIgnoreCase("createExcel")){
			this.setExcel(response, "Carrier Picking List");
			process.getExcelReport(bean,personnelBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			
			return noForward;
		}

		return mapping.findForward(forward);
	}

}
