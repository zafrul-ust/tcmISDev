package com.tcmis.internal.hub.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ImageViewerBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceiptImageInputBean;
import com.tcmis.internal.hub.process.ReceiptDocumentProcess;

public class ReceiptImageAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "showaddreceiptdocument");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = ((PersonnelBean) getSessionObject(request, "personnelBean"));
		ReceiptImageInputBean input = new ReceiptImageInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

		ReceiptDocumentProcess receiptDocumentProcess = new ReceiptDocumentProcess(getDbUser(request), getTcmISLocaleString(request));
		if (input.isUpdate()) {
			receiptDocumentProcess.setItemImage(input, user);
			return noForward;
		}
		else {
			if (input.getReceiptId() == null) {
				return mapping.findForward("systemerrorpage");
			}
			else {
				request.setAttribute("imageCollection", receiptDocumentProcess.getReceiptImageSearchResult(input));
				request.setAttribute("displayTitle", "Images for ReceiptId: " + input.getReceiptId());

			}

			return mapping.findForward("success");
		} // end of method
	}
} // end of class