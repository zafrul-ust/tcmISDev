package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.hub.beans.CabinetLabelInputBean;
import com.tcmis.internal.hub.process.CabinetBinLabelProcess;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
 ******************************************************************************/
public class CabinetBinLabelAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showcabinetlabel");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		CabinetLabelInputBean input = new CabinetLabelInputBean(form, getLocale(request));
		request.setAttribute("cabinetLabelInputBean", input);
		CabinetBinLabelProcess process = new CabinetBinLabelProcess(this.getDbUser(request));

		if (input.hasCabinet()) {
			request.setAttribute("cabinetBinLabelViewBeanCollection", process.getBinData(input));
		}

		return (mapping.findForward("success"));
	}
}