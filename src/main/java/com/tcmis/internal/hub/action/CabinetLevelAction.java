package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;

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
import com.tcmis.internal.hub.beans.CabinetLevelInputBean;
import com.tcmis.internal.hub.process.CabinetLevelProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class CabinetLevelAction
extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showcabinetmanagement");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		} 

		String uAction = (String) ((DynaBean) form).get("uAction");
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		
		CabinetLevelProcess process = new CabinetLevelProcess(this.getDbUser(request),getTcmISLocaleString(request));
		//if form is not null we have to perform some action
		if (form == null) {
		 	log.debug("form is null");
		}else {
			log.debug("*"+uAction+"*");
			//CabinetManagementInputForm inputForm = (CabinetManagementInputForm) form;
			CabinetLevelInputBean bean = new CabinetLevelInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			bean.setPersonnelId(new BigDecimal(((PersonnelBean)this.getSessionObject(request, "personnelBean")).getPersonnelId()));
			log.debug("bean:" + bean);
			if ("viewMinMax".equalsIgnoreCase(uAction)) {
				Collection cabinetPartLevelViewBeanCollection = process.getCurrentLevel(bean);
				request.setAttribute("cabinetPartLevelViewBeanCollection",cabinetPartLevelViewBeanCollection);
				request.setAttribute("cabinetPartLevelLogViewBeanCollection",process.getHistory(bean,cabinetPartLevelViewBeanCollection));
				this.saveTcmISToken(request);
			} else if ("update".equalsIgnoreCase(uAction)) {
				if (!this.isTcmISTokenValid(request, true)) {
					BaseException be = new BaseException("Duplicate form submission");
					be.setMessageKey("error.submit.duplicate");
					throw be;
				}
				this.saveTcmISToken(request);
				process.update(bean, personnelBean);
				//Update History by Trigger
				//process.updateHistory(bean);
				Collection cabinetPartLevelViewBeanCollection = process.getCurrentLevel(bean);
				request.setAttribute("cabinetPartLevelViewBeanCollection",cabinetPartLevelViewBeanCollection);
				request.setAttribute("cabinetPartLevelLogViewBeanCollection",process.getHistory(bean,cabinetPartLevelViewBeanCollection));
			}
		}
		return mapping.findForward("success");
	}
}