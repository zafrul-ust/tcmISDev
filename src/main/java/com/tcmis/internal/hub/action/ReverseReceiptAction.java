package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.hub.process.ReceivingQcProcess;

public class ReverseReceiptAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	  request.setAttribute("requestedPage", "showlabel");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}


	if (request.getParameter("receiptId") == null) {
	  return mapping.findForward("systemerrorpage");
	}
	else if (form != null && ( (DynaBean) form).get("submitReverse") != null &&
			 ( (String) ( (DynaBean) form).get("submitReverse")).length() > 0) {
	  boolean reverseReceipt = false;
	  ReceivingQcProcess receivingQcProcess = new ReceivingQcProcess(this.
		  getDbUser(request));
		 String receiptId = request.getParameter("receiptId");
		 log.info("reverseReceipt   "+receiptId+"");
	  reverseReceipt = receivingQcProcess.reverseReceipt(new BigDecimal(receiptId));
	  if (reverseReceipt)
	  {
		request.setAttribute("successMessage", "Yes");
	  }
	  else
	  {
		request.setAttribute("errorMessage", "Yes");
	  }

	  return (mapping.findForward("showresults"));
	}
	else {
	  String receiptId = request.getParameter("receiptId");

	  request.setAttribute("receiptId",receiptId);
	  return mapping.findForward("showinput");
	} //end of method
  }
} //end of class