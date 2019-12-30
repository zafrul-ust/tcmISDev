package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.process.ReceiptDocumentProcess;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

public class ReceiptDocumentAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	 request.setAttribute("requestedPage", "showaddreceiptdocument");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	ReceiptDocumentInputBean receiptDocumentInputBean = new
	 ReceiptDocumentInputBean();
	if (form != null) {
	 BeanHandler.copyAttributes(form, receiptDocumentInputBean, getTcmISLocale(request));
	}

	if (receiptDocumentInputBean.getReceiptId() == null) {
	 return mapping.findForward("systemerrorpage");
	}
	else if (form != null && receiptDocumentInputBean.getUpdateDocuments() != null &&
	 receiptDocumentInputBean.getUpdateDocuments().length() > 0) {
	 DynaBean dynaForm = (DynaBean) form;
	 List receiptDocumentViewBeans = (List) dynaForm.get(
		"receiptDocumentViewBean");
	 Iterator iterator = receiptDocumentViewBeans.iterator();
	 int deleteCount = 0;

	 Collection receiptDocumentInputBeanCollection = new Vector();
	 while (iterator.hasNext()) {
		org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
		 commons.beanutils.LazyDynaBean) iterator.next();

		ReceiptDocumentInputBean receiptDocumentUpdateInputBean = new
		 ReceiptDocumentInputBean();
		BeanHandler.copyAttributes(lazyBean, receiptDocumentUpdateInputBean, getTcmISLocale(request));
		if (receiptDocumentUpdateInputBean.getOk() != null) {
		 //log.debug("rowid     " + receiptDocumentUpdateInputBean.getOk() + "");
		 deleteCount++;
		 receiptDocumentInputBeanCollection.add(receiptDocumentUpdateInputBean);
		}
	 }

	 ReceiptDocumentProcess receiptDocumentProcess = new ReceiptDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));
	 if (deleteCount > 0) {
		int documentDeleteCount = receiptDocumentProcess.deleteDocument(
		 receiptDocumentInputBeanCollection);
	 }

	 request.setAttribute("receiptDocumentViewBeanCollection",
		receiptDocumentProcess.getSearchResult(receiptDocumentInputBean));
	 return (mapping.findForward("showDocuments"));
	}
	else if (form != null && receiptDocumentInputBean.getSubmitSave() != null && receiptDocumentInputBean.getSubmitSave().length() > 0) {
		
	 if(receiptDocumentInputBean.getTheFile() != null && !FileHandler.isValidUploadFile(receiptDocumentInputBean.getTheFile())) {
		 ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
		 request.setAttribute("vvReceiptDocumentTypeBeanCollection", receivingQcCheckListProcess.getVvReceiptDocumentType());
			
		 VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
		 request.setAttribute("companyIdsCollection", vvDataProcess.getCompanyIdsForInventoryGroup(receiptDocumentInputBean.getInventoryGroup()));
		 request.setAttribute("errorMessage", "Yes");
		 return (mapping.findForward("success"));
	 }

	 PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	 BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	 receiptDocumentInputBean.setEnteredBy(personnelId);

	 String addNewDocumentUrl = "";
	 ReceiptDocumentProcess receiptDocumentProcess = new ReceiptDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));

	 addNewDocumentUrl = receiptDocumentProcess.addNewDocument(receiptDocumentInputBean);
	 
	 if (addNewDocumentUrl != null && addNewDocumentUrl.length() > 0) {
		request.setAttribute("showdocument", "Yes");
		request.setAttribute("newDocumentUrl", addNewDocumentUrl);
		request.setAttribute("receiptId", receiptDocumentInputBean.getReceiptId());
		request.setAttribute("documentId", receiptDocumentInputBean.getDocumentId());
	 }
	 else {
		
		ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
		request.setAttribute("vvReceiptDocumentTypeBeanCollection", receivingQcCheckListProcess.getVvReceiptDocumentType());
		
		VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
		request.setAttribute("companyIdsCollection", vvDataProcess.getCompanyIdsForInventoryGroup(receiptDocumentInputBean.getInventoryGroup()));
		
		request.setAttribute("errorMessage", "Yes");
	 }

	 return (mapping.findForward("success"));
	}
	else if (receiptDocumentInputBean.getShowDocuments() != null &&
	 receiptDocumentInputBean.getShowDocuments().length() > 0) {
	 ReceiptDocumentProcess receiptDocumentProcess = new ReceiptDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));

	 request.setAttribute("receiptDocumentViewBeanCollection",
		receiptDocumentProcess.getSearchResult(receiptDocumentInputBean));
	 return (mapping.findForward("showDocuments"));
	}
	else {
	 ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
		request.setAttribute("vvReceiptDocumentTypeBeanCollection",
		receivingQcCheckListProcess.getVvReceiptDocumentType());

	 ReceiptDocumentProcess receiptDocumentProcess = new ReceiptDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));
	 request.setAttribute("companyIdsCollection",
		receiptDocumentProcess.getCompanyIdsForInventoryGroupAndItem(receiptDocumentInputBean.
		getInventoryGroup(),receiptDocumentInputBean.getReceiptId()));

	 return mapping.findForward("success");
	} //end of method
 }
} //end of class