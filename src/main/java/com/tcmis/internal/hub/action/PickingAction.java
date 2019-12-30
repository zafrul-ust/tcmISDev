package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for picking
 * @version 1.0
 ******************************************************************************/
public class PickingAction
extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "picking");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		//populate drop downs

		if (form == null) {
			this.saveTcmISToken(request);
		}
		//if form is not null we have to perform some action
		else {
			if (!this.isTcmISTokenValid(request, true)) {
				BaseException be = new BaseException("Duplicate form submission");
				be.setMessageKey("error.submit.duplicate");
				throw be;
			}
			this.saveTcmISToken(request);

			DynaValidatorForm myform = (DynaValidatorForm) form;
			if (log.isDebugEnabled()) {
				log.debug("hub:" + myform.get("hub"));
				log.debug("picklist:" + myform.get("submitGeneratePicklist"));
			}
		}
		return (mapping.findForward("success"));
	}
}
