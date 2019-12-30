package com.tcmis.internal.hub.action;

import java.io.File;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.internal.hub.beans.ReceivingHistoryViewBean;
import com.tcmis.internal.hub.process.ReceivingHistoryProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;

public class ReceivingHistoryAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	 request.setAttribute("requestedPage", "showreceivedreceipts");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	ReceivingHistoryViewBean inputBean = new ReceivingHistoryViewBean();
	BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

	if (inputBean.getItemId() == null && inputBean.getRadianPo() == null) {
	 return mapping.findForward("systemerrorpage");
	}
	else {
	 ReceivingHistoryProcess receivingHistoryProcess = new
		ReceivingHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));

	 Collection c = receivingHistoryProcess.getReceivingHistory(inputBean);
	 request.setAttribute("receivingHistoryViewBeanCollection", c);

	 request.setAttribute("itemId", inputBean.getItemId());
	 request.setAttribute("radianPo", inputBean.getRadianPo());
	 request.setAttribute("poLine", inputBean.getPoLine());
	 request.setAttribute("inventoryGroup", inputBean.getInventoryGroup());
	 if (inputBean.getApproved() !=null)
	 {
		return mapping.findForward("showapprovedreceipts");
	 }
	 else
	 {
		return mapping.findForward("showreceivedreceipts");
	 }
	}
 } //end of method
} //end of class
