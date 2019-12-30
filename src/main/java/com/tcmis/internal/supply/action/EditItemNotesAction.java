package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
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
import com.tcmis.internal.supply.beans.ItemBuyerCommentsViewBean;
import com.tcmis.internal.supply.process.ItemNotesProcess;

/******************************************************************************
 * Controller for UpdateReceiptNotes
 * @version 1.0
	******************************************************************************/
public class EditItemNotesAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "edititemnotes");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	//populate fields
	ItemNotesProcess notesProcess = new ItemNotesProcess(this.getDbUser(request),getTcmISLocaleString(request));
	//If the update button was pressed the value will be not null
	if ( ( (DynaBean) form).get("update") != null &&
	 ( (String) ( (DynaBean) form).get("update")).length() > 0) {
	 if (!this.isTcmISTokenValid(request, true)) {
		BaseException be = new BaseException("Duplicate form submission");
		be.setMessageKey("error.submit.duplicate");
		throw be;
	 }
	 this.saveTcmISToken(request);

	 PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	 
//	 Collection itemNotesCollection = BeanHandler.getBeans((DynaBean) form,"itemNotesBean", new ItemBuyerCommentsViewBean(),getTcmISLocale(request));
		
	 DynaBean dynaForm = (DynaBean) form;
	 List itemNoteBeans = (List) dynaForm.get("itemNotesBean");
	 Iterator iterator = itemNoteBeans.iterator();
	 Collection itemNotesCollection = new Vector();
	 while (iterator.hasNext()) {
		LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
		ItemBuyerCommentsViewBean commentsBean = new ItemBuyerCommentsViewBean();
		BeanHandler.copyAttributes(lazyBean, commentsBean, getTcmISLocale(request));
		itemNotesCollection.add(commentsBean);
	 }

	 try {
		notesProcess.updateNotes(itemNotesCollection, personnelBean);
	 }
	 catch (BaseException be) {
		request.setAttribute("errorMessage", be.getMessage());
	 }  
	 if ( ( (DynaBean) form).get("itemId") != null &&
		( (String) ( (DynaBean) form).get("itemId")).length() > 0) {
	 request.setAttribute("itemNotesColl",notesProcess.getNotes( ( (DynaBean) form).get("itemId").toString()));
	 }
  	return (mapping.findForward("showresults"));
	}

	// first check for a valid item id
  if ( ( (DynaBean) form).get("itemId") != null &&
		( (String) ( (DynaBean) form).get("itemId")).length() > 0) {
		//Pass the result item id in request
		request.setAttribute("itemNotesColl",notesProcess.getNotes( ( (DynaBean) form).get("itemId").toString()));
	 }
	 this.saveTcmISToken(request);

	return (mapping.findForward("showresults"));
 }
}