package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.process.ReceiptsProcess;

/******************************************************************************
 * Controller for ReceiptBinHistory
 * @version 1.0
     ******************************************************************************/
public class ReceiptBinHistoryAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request,true)) {
      request.setAttribute("requestedPage", "receiptbinhistory");
      request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
        
    //populate drop downs
    ReceiptsProcess receiptProcess = new ReceiptsProcess(this.getDbUser(request));
     
    ReceiptBean inputBean = new ReceiptBean();
    BeanHandler.copyAttributes(form, inputBean);        

    Collection receipts = receiptProcess.getReceiptBinHistory(inputBean);    

    //Pass the result collection in request
    request.setAttribute("receiptBinHistColl",receipts);

    return (mapping.findForward("showresults"));        
  }
}
