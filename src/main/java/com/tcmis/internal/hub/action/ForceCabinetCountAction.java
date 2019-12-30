package com.tcmis.internal.hub.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.process.ForceCabinetCountProcess;

public class ForceCabinetCountAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "forcecabinetcount");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		/*
		 * Need to check if the user has permissions to view this page.
		 * if they do not have the permission we need to not show them
		 * the page.
		 */

		if (!personnelBean.getPermissionBean().hasUserPagePermission("forceCabinetCount")) {
			return (mapping.findForward("nopermissions"));
		}

		// main
		if (form == null)
			return mapping.findForward("showinput");
		// search
		String uAction = (String) ((DynaBean) form).get("uAction");
		if (uAction == null)
			return mapping.findForward("showinput");
		// result

		/*
		 * Need to check if the user has permissions to update this
		 * data. if they do not have the permission we show a error
		 * message.
		 */
		//if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("forceCabinetCount", null, null, null)) {
		//	request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
		//	return (mapping.findForward("success"));
		//}

		String userId = (String) ((DynaBean) form).get("userId");
		String bins = (String) ((DynaBean) form).get("bins");
        String upLoadSeq = (String) ((DynaBean) form).get("upLoadSeq");

        // check if  args were passed
		if (StringHandler.isBlankString(userId) || StringHandler.isBlankString(bins)) {
			request.setAttribute("errorMessage", getResourceBundleValue(request, "error.input.invalid"));
		}
		else {
			StringBuilder cleanBins = new StringBuilder();
			for (String bin : bins.split(",\\s*|\\s+")) {
				if (cleanBins.length() > 0) {
					cleanBins.append(',');
				}
				cleanBins.append(bin);
			}
			// 	
			ForceCabinetCountProcess process = new ForceCabinetCountProcess(getDbUser(request), getTcmISLocale());
			String errorMessage = process.doUpload(cleanBins.toString(),	new BigDecimal(userId),new BigDecimal(upLoadSeq));
			// if I got here everything went fine, add "ok" message
			if (errorMessage.length() == 0) {
				request.setAttribute("result", "ok");
			}
			else {
				request.setAttribute("errorMessage", errorMessage);
			}
		}
		return mapping.findForward("showinput");
	}

}
