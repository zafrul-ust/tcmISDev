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
import com.tcmis.internal.hub.beans.InventoryTransferDetailViewBean;
import com.tcmis.internal.hub.process.TransferRequestProcess;

/******************************************************************************
 * Controller for transaction
 * @version 1.0
     ******************************************************************************/
public class TransferTransactionsAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "transfertransactions");
      request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    //populate drop downs
    TransferRequestProcess transferProcess = new TransferRequestProcess(this.getDbUser(request));

    if (form == null) {
      this.saveTcmISToken(request);
    }
    //if form is not null we have to perform some action
    else {
      InventoryTransferDetailViewBean inputBean = new InventoryTransferDetailViewBean();
      BeanHandler.copyAttributes(form, inputBean);

       
       if (inputBean.getTransferRequestId() != null &&
           inputBean.getTransferRequestId().intValue() > 0) {

          Collection txns = transferProcess.getTransferTransactions(inputBean);
          
          //Pass the result collection in request
          request.setAttribute("transferTransactionsColl",txns);

          return (mapping.findForward("showresults"));
       }
    }

    return (mapping.findForward("showresults"));
  }
}
