package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
 ******************************************************************************/
public class LabelAction
extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showcabinetlabel");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//LabelProcess process = new LabelProcess(this.getDbUser(request));


		if (form == null) {

		}
		//if form is not null we have to perform some action
		else if (form != null &&
				((DynaBean) form).get("baseLineButton") != null &&
				((String)((DynaBean) form).get("baseLineButton")).length() > 0) {
			//if (!this.isTokenValid(request, true)) {
			//  BaseException be = new BaseException("Duplicate form submission");
			//  be.setMessageKey("error.submit.duplicate");
			//  throw be;
			//}

			if (log.isDebugEnabled()) {
				log.debug("cabinetId:" + (String)((DynaBean) form).get("cabinetId0"));
			}

			//generate base line
		}
		else if (form != null &&
				((DynaBean) form).get("generateBinLabelButton") != null &&
				((String)((DynaBean) form).get("generateBinLabelButton")).length() > 0) {
			//if (!this.isTokenValid(request, true)) {
			//  BaseException be = new BaseException("Duplicate form submission");
			//  be.setMessageKey("error.submit.duplicate");
			//  throw be;
			//}


			//generate bin labels
		}
		else if (form != null &&
				((DynaBean) form).get("generateCabinetLabelButton") != null &&
				((String)((DynaBean) form).get("generateCabinetLabelButton")).length() > 0) {
			//if (!this.isTokenValid(request, true)) {
			//  BaseException be = new BaseException("Duplicate form submission");
			//  be.setMessageKey("error.submit.duplicate");
			//  throw be;
			//}


			//generate cabinet labels
		}
		else if (form != null &&
				((DynaBean) form).get("generateCabinetBinLabelButton") != null &&
				((String)((DynaBean) form).get("generateCabinetBinLabelButton")).length() > 0) {
			//if (!this.isTokenValid(request, true)) {
			//  BaseException be = new BaseException("Duplicate form submission");
			//  be.setMessageKey("error.submit.duplicate");
			//  throw be;
			//}


			//generate cabinet & bin labels
		}
		else if (form != null &&
				((DynaBean) form).get("generateAllLabelButton") != null &&
				((String)((DynaBean) form).get("generateAllLabelButton")).length() > 0) {
			//if (!this.isTokenValid(request, true)) {
			//  BaseException be = new BaseException("Duplicate form submission");
			//  be.setMessageKey("error.submit.duplicate");
			//  throw be;
			//}


			//generate all labels
		}

		return (mapping.findForward("success"));
	}
}