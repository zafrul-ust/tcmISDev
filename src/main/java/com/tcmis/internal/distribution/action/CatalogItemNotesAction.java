package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.CatalogItemNotesViewBean;
//import com.tcmis.internal.distribution.beans.SalesQuoteLineViewBean;
import com.tcmis.internal.distribution.process.CatalogItemNotesProcess;

/******************************************************************************
 * Controller for UpdateReceiptNotes
 * @version 1.0
	******************************************************************************/
public class CatalogItemNotesAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "catalogitemnotesmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	//populate fields
	CatalogItemNotesProcess notesProcess = new CatalogItemNotesProcess(this.getDbUser(request),getTcmISLocaleString(request));
	//If the update button was pressed the value will be not null
	if ( ( (DynaBean) form).get("uAction") != null && "update".equals(( (DynaBean) form).get("uAction"))) {
		checkToken(request);
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		 
		 Collection<CatalogItemNotesViewBean> itemNotesCollection = BeanHandler.getBeans((DynaBean) form,"catalogItemNotesBean", new CatalogItemNotesViewBean(),getTcmISLocale(request));	

		 try {
			notesProcess.processNotes(itemNotesCollection, personnelBean);
		 }
		 catch (BaseException be) {
			request.setAttribute("errorMessage", be.getMessage());
		 }  
		 if ( ( (DynaBean) form).get("itemId") != null &&
			( (String) ( (DynaBean) form).get("itemId")).length() > 0) {
		 request.setAttribute("itemNotesColl",notesProcess.getNotes( ( (DynaBean) form).get("itemId").toString()));
		 }
	  	 return (mapping.findForward("success"));
	}

	// first check for a valid item id
    if ( ( (DynaBean) form).get("itemId") != null &&
		( (String) ( (DynaBean) form).get("itemId")).length() > 0) {
		//Pass the result item id in request
    	Collection c = notesProcess.getNotes( ( (DynaBean) form).get("itemId").toString());
		request.setAttribute("itemNotesColl",c);
	}
	this.saveTcmISToken(request);

	return (mapping.findForward("success"));
 }
}