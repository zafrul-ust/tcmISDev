package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.NonChemicalReceivingQcProcess;
import com.tcmis.common.admin.beans.PersonnelBean;

public class NonChemicalReceivedReceiptsAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	 request.setAttribute("requestedPage", "shownonchemicalreceivedreceipts");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	ReceivingInputBean receivingInputBean = new ReceivingInputBean();
	BeanHandler.copyAttributes(form, receivingInputBean, getTcmISLocale(request));

	request.setAttribute("receivedReceipts",
	 receivingInputBean.getReceivedReceipts());
	request.setAttribute("hubNumber", receivingInputBean.getSourceHub());
	request.setAttribute("inventoryGroup", receivingInputBean.getInventoryGroup());

	NonChemicalReceivingQcProcess nonChemicalReceivingQcProcess = new
	 NonChemicalReceivingQcProcess(this.getDbUser(request),getTcmISLocaleString(request));

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
	 "personnelBean");
	Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();

	request.setAttribute("receivedReceiptsCollection",
	 nonChemicalReceivingQcProcess.getNonChemicalResult(receivingInputBean.
	 getReceivedReceipts(),
	 receivingInputBean.getSourceHub(), receivingInputBean.getInventoryGroup(),
	 hubInventoryGroupOvBeanCollection,
	 "Y".equalsIgnoreCase(receivingInputBean.getJustReceived())));

	return mapping.findForward("shownonchemicalreceivedreceipts");
 }
} //end of class