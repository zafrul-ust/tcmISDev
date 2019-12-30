package com.tcmis.internal.hub.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.InventoryCountScanInputBean;
import com.tcmis.internal.hub.process.CabinetCountProcess;

public class CabinetCountAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showcabinetcountupload");
			// This is to save any parameters passed in the URL, so
			// that they
			// can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));

			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("cabinetScanning"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

		InventoryCountScanInputBean inventoryCountScanInputBean = new InventoryCountScanInputBean();

		if (form != null) {
			BeanHandler.copyAttributes(form, inventoryCountScanInputBean);

			if (inventoryCountScanInputBean.getSubmitSave() != null) {
				if (!this.isTcmISTokenValid(request, true)) {
					BaseException be = new BaseException("Duplicate form submission");
					be.setMessageKey("error.submit.duplicate");
					this.saveTcmISToken(request);
					throw be;
				}
				this.saveTcmISToken(request);
			}

			if (form != null && inventoryCountScanInputBean.getSubmitSave() != null && inventoryCountScanInputBean.getSubmitSave().length() > 0) {
				BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
				inventoryCountScanInputBean.setEnteredBy(personnelId);

				String errorMessage = "";
				CabinetCountProcess cabinetCountProcess = new CabinetCountProcess(getDbUser(request), getTcmISLocale());
				errorMessage = cabinetCountProcess.doUpload(inventoryCountScanInputBean, personnelId);

				if (errorMessage != null && errorMessage.length() > 0) {
					request.setAttribute("errorMessage", errorMessage);

				}
				else {
					request.setAttribute("uploadSucess", "Yes");
				}

				return (mapping.findForward("showresults"));
			}
		}
		this.saveTcmISToken(request);
		return mapping.findForward("showinput");
	} // end of method
} // end of class