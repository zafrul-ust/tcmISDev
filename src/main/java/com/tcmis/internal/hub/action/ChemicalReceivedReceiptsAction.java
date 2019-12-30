package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.ReceivingQcProcess;
import com.tcmis.internal.hub.process.DropshipReceivingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;

public class ChemicalReceivedReceiptsAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	  request.setAttribute("requestedPage", "showchemreceivedreceiptsmain");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}

    //if form is not null we have to perform some action
	if (form != null) {
	 //copy date from dyna form to the data bean
	 ReceivingInputBean receivingInputBean = new ReceivingInputBean();
    BeanHandler.copyAttributes(form, receivingInputBean, getTcmISLocale(request));

   //If the page is being updated I check for a valid token. I don't check for
	 //a valid token for search actions because they are going to be asynchronous once we move to AJAX
	 if (receivingInputBean.getSubmitReceive() != null) {
		if (!this.isTcmISTokenValid(request, true)) {
		 BaseException be = new BaseException("Duplicate form submission");
		 be.setMessageKey("error.submit.duplicate");
		 this.saveTcmISToken(request);
		 throw be;
		}
		this.saveTcmISToken(request);
	 }

   ReceivingQcProcess receivingQcProcess = new ReceivingQcProcess(this.getDbUser(request),getTcmISLocaleString(request));

   //If the search button was pressed the getSubmitSearch() value will be not null
	 if (receivingInputBean.getUserAction() != null &&
		receivingInputBean.getUserAction().trim().equalsIgnoreCase("search")) {
     /*request.setAttribute("justReceived", receivingInputBean.getJustReceived());
     request.setAttribute("receivedReceipts", receivingInputBean.getReceivedReceipts());
     request.setAttribute("sourceHub", receivingInputBean.getSourceHub());
     request.setAttribute("inventoryGroup", receivingInputBean.getInventoryGroup());*/
     return mapping.findForward("success");
	 }
   else if (receivingInputBean.getUserAction() != null &&
		receivingInputBean.getUserAction().trim().equalsIgnoreCase("results")) {
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	  Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();

	  Collection receivedReceiptsCollection = receivingQcProcess.
	  getChemicalResult(receivingInputBean.getReceivedReceipts(),
	  receivingInputBean.getSourceHub(), receivingInputBean.getInventoryGroup(),
	  personnelBean,"Y".equalsIgnoreCase(receivingInputBean.getJustReceived()),false);

     if (receivedReceiptsCollection.size() == 0 && receivingInputBean.getReceivedReceipts().length() > 0) {
       DropshipReceivingProcess dropshipReceivingProcess = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));

       Collection dropShipReceivedReceiptsColl = dropshipReceivingProcess.
           getChemicalResult(receivingInputBean.getReceivedReceipts());

       String labelReceipts = dropshipReceivingProcess.receiptsToLabelList(
           dropShipReceivedReceiptsColl);

       com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
       request.setAttribute("labelReceipts", labelReceipts);

       request.setAttribute("receivedReceiptsCollection", dropShipReceivedReceiptsColl);
       return mapping.findForward("showdropshipreceivedreceipts");
     }

     String labelReceipts = receivingQcProcess.receiptsToLabelList(
	  receivedReceiptsCollection);

	  com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
	  request.setAttribute("labelReceipts", labelReceipts);
	  request.setAttribute("hubNumber", receivingInputBean.getSourceHub());
    request.setAttribute("receivedReceipts", receivingInputBean.getReceivedReceipts());
     
    request.setAttribute("receivedReceiptsCollection",
	  receivingQcProcess.createRelationalObject(receivedReceiptsCollection));
	  return mapping.findForward("success");
	 }
  }

  //Saving Token
	this.saveTcmISToken(request);
  /*request.setAttribute("justReceived", request.getParameter("justReceived"));
  request.setAttribute("receivedReceipts", request.getParameter("receivedReceipts"));
  request.setAttribute("sourceHub", request.getParameter("sourceHub"));
  request.setAttribute("inventoryGroup", request.getParameter("inventoryGroup"));*/
  return mapping.findForward("success");

/*
  if (form == null) {          
    request.setAttribute("justReceived", request.getParameter("justReceived"));
    request.setAttribute("receivedReceipts", request.getParameter("receivedReceipts"));
    request.setAttribute("sourceHub", request.getParameter("sourceHub"));
    request.setAttribute("inventoryGroup", request.getParameter("inventoryGroup"));
    return mapping.findForward("success");
	}

   ReceivingInputBean receivingInputBean = new ReceivingInputBean();
    BeanHandler.copyAttributes(form, receivingInputBean, getTcmISLocale(request));

  request.setAttribute("receivedReceipts",receivingInputBean.getReceivedReceipts());
  request.setAttribute("hubNumber", receivingInputBean.getSourceHub());
  request.setAttribute("inventoryGroup", receivingInputBean.getInventoryGroup());

  ReceivingQcProcess receivingQcProcess = new ReceivingQcProcess(this.
	 getDbUser(request),getTcmISLocaleString(request));

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
	 "personnelBean");
	Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();

	Collection receivedReceiptsCollection = receivingQcProcess.
	 getChemicalResult(receivingInputBean.getReceivedReceipts(),
	 receivingInputBean.getSourceHub(), receivingInputBean.getInventoryGroup(),
	 hubInventoryGroupOvBeanCollection,"Y".equalsIgnoreCase(receivingInputBean.getJustReceived()));

	String labelReceipts = receivingQcProcess.receiptsToLabelList(
	 receivedReceiptsCollection);

	com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
	request.setAttribute("labelReceipts", labelReceipts);
	request.setAttribute("hubNumber", receivingInputBean.getSourceHub());

	request.setAttribute("receivedReceiptsCollection",
	 receivingQcProcess.createRelationalObject(receivedReceiptsCollection));
	return mapping.findForward("success");*/

} //end of class
}