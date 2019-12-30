package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.DropshipReceivingProcess;

public class DropshipReceivedReceiptsAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	  request.setAttribute("requestedPage", "showdropshipreceivedreceipts");
		request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}

	ReceivingInputBean receivingInputBean = new ReceivingInputBean();
	BeanHandler.copyAttributes(form, receivingInputBean, getTcmISLocale(request));

	request.setAttribute("receivedReceipts",receivingInputBean.getReceivedReceipts());
	//request.setAttribute("hubNumber", receivingInputBean.getSourceHub());
	//request.setAttribute("inventoryGroup", receivingInputBean.getInventoryGroup());

	DropshipReceivingProcess dropshipReceivingProcess = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));

	/*Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)this.getSessionObject(
			 request, "hubInventoryGroupOvBeanCollection"));*/

	Collection receivedReceiptsCollection = dropshipReceivingProcess.
	 getChemicalResult(receivingInputBean.getReceivedReceipts());

	String labelReceipts = dropshipReceivingProcess.receiptsToLabelList(
	 receivedReceiptsCollection);

	com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
	request.setAttribute("labelReceipts", labelReceipts);
	//request.setAttribute("hubNumber", receivingInputBean.getSourceHub());

	request.setAttribute("receivedReceiptsCollection",receivedReceiptsCollection);
	return mapping.findForward("showdropshipreceivedreceipts");
 }
} //end of class