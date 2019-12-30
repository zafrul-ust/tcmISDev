package com.tcmis.internal.print.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.print.beans.StaticLabelInputBean;
import com.tcmis.internal.print.process.StaticLabelProcess;

public class PrintStaticLabelsAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "printstaticlabels");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean)getSessionObject(request, "personnelBean");
		StaticLabelInputBean input = new StaticLabelInputBean((DynaBean) form);
		StaticLabelProcess process = new StaticLabelProcess(getDbUser(request));


		if (input.isPrint()) {
			String printerPath = process.getPrinterPath(user);
			String labelUrl = process.buildStaticLabels(input.getLabelFileName(), printerPath, input.getPrintQuantity());

			if (labelUrl.length() > 0) {
				request.setAttribute("filePath", labelUrl);
				return mapping.findForward("viewfile");
			}
			else {
				return mapping.findForward("systemerrorpage");
			}
		}
		else {
			request.setAttribute("staticLabels", process.getStaticLabels());
		}
		return mapping.findForward("success");
	}
} //end of class