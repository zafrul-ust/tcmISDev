package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.common.util.ResourceLibrary;

import java.io.File;

import com.tcmis.internal.hub.beans.ReceivingDocumentReportBean;
import com.tcmis.internal.hub.process.ReceivingDocumentReportProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.NonChemicalReceivingQcProcess;
import com.tcmis.internal.hub.process.ReceivingQcProcess;
import com.tcmis.internal.hub.process.ShipmentHistoryProcess;

/**
 * ***************************************************************************
 * Controller for receiving document report
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ReceivingDocumentReportAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "receivingdocumentreportmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			ReceivingDocumentReportBean inputBean = new ReceivingDocumentReportBean(form,getTcmISLocale(request));
			ReceivingDocumentReportProcess process = new ReceivingDocumentReportProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			if (inputBean.isSearch()) {
				this.saveTcmISToken(request);
				request.setAttribute("receivingDocumentReportCollection",process.getSearchResult(inputBean));
			} else if (inputBean.isCreateExcel()) {
				this.setExcel(response,"Receiving Document Report");
				process.createExcelReport(inputBean).write(response.getOutputStream());
				return noForward;
			}
		}
		return mapping.findForward("success");
	}
}