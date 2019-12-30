package com.tcmis.internal.distribution.action;

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
import com.tcmis.internal.distribution.beans.MrDocumentInputBean;
import com.tcmis.internal.distribution.process.MrDocumentProcess;



public class MrDocumentAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	 request.setAttribute("requestedPage", "showaddmrdocument");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}
	
	// If you need access to who is logged in
	PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

	/* Need to check if the user has permissions to view this page. if they do not have the permission
		  we need to not show them the page.*/
	if (!user.getPermissionBean().hasUserPagePermission("orderEntry") && !user.getPermissionBean().hasUserPagePermission("dlaGasOrderTracking") && !user.getPermissionBean().hasUserPagePermission("usgovDlaGasOrderTracking")) {
		return (mapping.findForward("nopermissions"));
	}

	MrDocumentInputBean mrDocumentInputBean = new 	MrDocumentInputBean();
	MrDocumentProcess mrDocumentProcess = new MrDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));
	if (form != null) {
	 BeanHandler.copyAttributes(form, mrDocumentInputBean, getTcmISLocale(request));
	}

	if (mrDocumentInputBean.getPrNumber() == null) {
	 return mapping.findForward("systemerrorpage");
	}
	else if (form != null && mrDocumentInputBean.getUpdateDocuments() != null &&
			mrDocumentInputBean.getUpdateDocuments().length() > 0) {
	 DynaBean dynaForm = (DynaBean) form;
	 List mrDocumentViewBeans = (List) dynaForm.get("mrDocumentViewBean");
	 Iterator iterator = mrDocumentViewBeans.iterator();
	 int deleteCount = 0;
	 
	 if (!(user.getPermissionBean().hasInventoryGroupPermission("releaseOrder", null, null, null)||
           user.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)||
           user.getPermissionBean().hasSupplierPermission("SupplierAndHubOrders", "", "")
        	)) {
			request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
			// repopulate the search results
			request.setAttribute("mrDocumentViewBeanCollection",mrDocumentProcess.getSearchResult(mrDocumentInputBean));
			 return (mapping.findForward("showMrDocuments"));
		}
	 checkToken(request);
	 Collection mrDocumentInputBeanCollection = new Vector();
	 while (iterator.hasNext()) {
		org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
		 commons.beanutils.LazyDynaBean) iterator.next();

		MrDocumentInputBean mrDocumentUpdateInputBean = new
		 MrDocumentInputBean();
		BeanHandler.copyAttributes(lazyBean, mrDocumentUpdateInputBean, getTcmISLocale(request));
		if (mrDocumentUpdateInputBean.getOk().equalsIgnoreCase("true")) {
		 deleteCount++;
		 mrDocumentInputBeanCollection.add(mrDocumentUpdateInputBean);
		}
	 }

	 
	 if (deleteCount > 0) {
		int documentDeleteCount = mrDocumentProcess.deleteDocument(
		 mrDocumentInputBeanCollection);
	 }
	 
	 
	 request.setAttribute("mrDocumentViewBeanCollection", mrDocumentProcess.getSearchResult(mrDocumentInputBean));
	 return (mapping.findForward("showMrDocuments"));
	}
	else if (form != null && mrDocumentInputBean.getSubmitSave() != null &&
	 mrDocumentInputBean.getSubmitSave().length() > 0) {
		
	 if(mrDocumentInputBean.getTheFile() != null && !FileHandler.isValidUploadFile(mrDocumentInputBean.getTheFile()) ) {
			VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
			request.setAttribute("vvMrDocumentTypeBeanCollection", vvDataProcess.getVvMrDocumentType());
			request.setAttribute("errorMessage", "File type not allowed.");
			return (mapping.findForward("success"));
	 }
		
	 PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	 BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	 mrDocumentInputBean.setEnteredBy(personnelId);

	 String addNewDocumentUrl = "";
	 
	 addNewDocumentUrl = mrDocumentProcess.addNewDocument(mrDocumentInputBean);
	 if (addNewDocumentUrl != null && addNewDocumentUrl.length() > 0) {
		request.setAttribute("showdocument", "Yes");
		request.setAttribute("newDocumentUrl", addNewDocumentUrl);
	 }
	 else {
		VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
		request.setAttribute("vvMrDocumentTypeBeanCollection",
		 vvDataProcess.getVvMrDocumentType());
		
		request.setAttribute("errorMessage", "Document not Added");
	 }

	 return (mapping.findForward("success"));
	}
	else if (mrDocumentInputBean.getShowDocuments() != null && 	 mrDocumentInputBean.getShowDocuments().length() > 0) {
	       
	 request.setAttribute("mrDocumentViewBeanCollection",mrDocumentProcess.getSearchResult(mrDocumentInputBean));
	 this.saveTcmISToken(request);
	 return (mapping.findForward("showMrDocuments"));
	}
	else {
	 VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));

	 request.setAttribute("vvMrDocumentTypeBeanCollection",
		vvDataProcess.getVvMrDocumentType());

	
	 return mapping.findForward("success");
	} //end of method
 }
} //end of class