package com.tcmis.internal.supply.action;

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
import com.tcmis.internal.supply.beans.PoDocumentInputBean;
import com.tcmis.internal.supply.process.PoDocumentProcess;

public class PoDocumentAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	 request.setAttribute("requestedPage", "showaddpodocument");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}
	
	// If you need access to who is logged in
	PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

	/* Need to check if the user has permissions to view this page. if they do not have the permission
		  we need to not show them the page.*/
	if (!user.getPermissionBean().hasUserPagePermission("openPos")) {
		return (mapping.findForward("nopermissions"));
	}

	PoDocumentInputBean poDocumentInputBean = new 	PoDocumentInputBean();
	if (form != null) {
	 BeanHandler.copyAttributes(form, poDocumentInputBean, getTcmISLocale(request));
	}
	PoDocumentProcess poDocumentProcess = new PoDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));
	if (poDocumentInputBean.getRadianPo() == null) {
	 return mapping.findForward("systemerrorpage");
	}
	else if (poDocumentInputBean.getUpdateDocuments() != null && poDocumentInputBean.getUpdateDocuments().length() > 0) {
	 DynaBean dynaForm = (DynaBean) form;
	 List poDocumentViewBeans = (List) dynaForm.get("poDocumentViewBean");
	 Iterator iterator = poDocumentViewBeans.iterator();
	 int deleteCount = 0;
	 
	 if (!user.getPermissionBean().hasInventoryGroupPermission("BuyOrder", poDocumentInputBean.getInventoryGroup(), null, null)) {
			request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
			// repopulate the search results
			request.setAttribute("poDocumentViewBeanCollection",poDocumentProcess.getSearchResult(poDocumentInputBean));
			 return (mapping.findForward("showPoDocuments"));
		}

		
		checkToken(request);

	 Collection poDocumentInputBeanCollection = new Vector();
	 while (iterator.hasNext()) {
		org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
		 commons.beanutils.LazyDynaBean) iterator.next();

		PoDocumentInputBean poDocumentUpdateInputBean = new
		 PoDocumentInputBean();
		BeanHandler.copyAttributes(lazyBean, poDocumentUpdateInputBean, getTcmISLocale(request));
		if (poDocumentUpdateInputBean.getOk().equalsIgnoreCase("true"))
		{
		 deleteCount++;
		 poDocumentInputBeanCollection.add(poDocumentUpdateInputBean);
		}
	 }

	 
	 if (deleteCount > 0)
	 {
		int documentDeleteCount = poDocumentProcess.deleteDocument(poDocumentInputBeanCollection);
	 }
	 
	 
	 request.setAttribute("poDocumentViewBeanCollection",poDocumentProcess.getSearchResult(poDocumentInputBean));
	 return (mapping.findForward("showPoDocuments"));
	}
	else if (form != null && poDocumentInputBean.getSubmitSave() != null && poDocumentInputBean.getSubmitSave().length() > 0) {
		if(poDocumentInputBean.getTheFile() != null && !FileHandler.isValidUploadFile(poDocumentInputBean.getTheFile())) {
			VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
			request.setAttribute("vvPoDocumentTypeBeanCollection", vvDataProcess.getVvPoDocumentType());
			request.setAttribute("companyIdsCollection",
			vvDataProcess.getCompanyIdsForInventoryGroup(poDocumentInputBean.getInventoryGroup()));
			request.setAttribute("errorMessage", "Yes");
			return (mapping.findForward("success"));
		 }
		
	 PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	 BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	 poDocumentInputBean.setEnteredBy(personnelId);

	 String addNewDocumentUrl = "";
	 
	 addNewDocumentUrl = poDocumentProcess.addNewDocument(poDocumentInputBean);
	 if (addNewDocumentUrl != null && addNewDocumentUrl.length() > 0) {
		request.setAttribute("showdocument", "Yes");
		request.setAttribute("newDocumentUrl", addNewDocumentUrl);
	 }
	 else {
		VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
		request.setAttribute("vvPoDocumentTypeBeanCollection", vvDataProcess.getVvPoDocumentType());
		request.setAttribute("companyIdsCollection",
		vvDataProcess.getCompanyIdsForInventoryGroup(poDocumentInputBean.getInventoryGroup()));

		request.setAttribute("errorMessage", "Yes");
	 }

	 return (mapping.findForward("success"));
	}
	else if (poDocumentInputBean.getShowDocuments() != null && poDocumentInputBean.getShowDocuments().length() > 0) {
	      
	 request.setAttribute("poDocumentViewBeanCollection",poDocumentProcess.getSearchResult(poDocumentInputBean));
	 this.saveTcmISToken(request);
	 return (mapping.findForward("showPoDocuments"));
	}
	else {
	 VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));

	 request.setAttribute("vvPoDocumentTypeBeanCollection",
		vvDataProcess.getVvPoDocumentType());

	
	 return mapping.findForward("success");
	} //end of method
 }
} //end of class