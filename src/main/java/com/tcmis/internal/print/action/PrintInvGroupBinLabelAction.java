package com.tcmis.internal.print.action;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.process.LabelProcess;

public class PrintInvGroupBinLabelAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward nextPage = mapping.findForward("viewfile");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "printmrpodocumentlabel");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			nextPage = mapping.findForward("login");
		}
		else {

			LabelInputBean input = new LabelInputBean(form);
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			LabelProcess labelProcess = new LabelProcess(getDbUser(request), getTcmISLocaleString(request));
			Collection userLabelPrinters = Collections.EMPTY_LIST;

			if (!StringHandler.isBlankString(user.getPrinterLocation()) && !"811".equalsIgnoreCase(input.getPaperSize())) {
				ZplDataProcess zplDataProcess = new ZplDataProcess(getDbUser(request));
				userLabelPrinters = zplDataProcess.getLocationLabelPrinter(user, input);
				if (userLabelPrinters != null && userLabelPrinters.size() > 1) {
					request.setAttribute("paperSize", input.getPaperSize());
					request.setAttribute("labelType", input.getLabelType());
					request.setAttribute("hub", input.getHub());
					request.setAttribute("inventoryGroup", input.getInventoryGroup());
					request.setAttribute("sourcePage", "printinvgroupbinlabel");

					request.setAttribute("locationLabelPrinterCollection", userLabelPrinters);
					return mapping.findForward("printerchoice");
				}
			}

			String generatedLabelFileURL = labelProcess.buildInvGroupBinLabels(user, input, userLabelPrinters);

			if (StringHandler.isBlankString(generatedLabelFileURL)) {
				nextPage = mapping.findForward("systemerrorpage");
			}
			else {
				request.setAttribute("filePath", generatedLabelFileURL);
				request.setAttribute("doexcelpopup", "Yes");
			}
		}
		return nextPage;
	}
} //end of class