package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
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
 * Controller for UpdateReceiptNotes
 * @version 1.0
     ******************************************************************************/
public class UpdateReceiptNotesAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "updatereceiptnotes");
      request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
        
    //populate drop downs
    ReceiptsProcess receiptProcess = new ReceiptsProcess("TCM_OPS");     

    //If the search button was pressed the getSubmitSearch() value will be not null
    if (((DynaBean) form).get("addNotes") != null && 
          ((String)((DynaBean) form).get("addNotes")).length() > 0) {    

      ReceiptBean inputBean = new ReceiptBean();
      BeanHandler.copyAttributes(form, inputBean);      
      receiptProcess.updateReceiptNotes(inputBean);    
      
      //Pass the success receipt msg in request
      request.setAttribute("receiptMessage",inputBean.getReceiptId());
      
    } else {

      ReceiptBean inputBean = new ReceiptBean();
      BeanHandler.copyAttributes(form, inputBean);      
      ReceiptBean receipt = receiptProcess.getReceipt(inputBean);    
      //Pass the result receipt in request
      request.setAttribute("receipt",receipt);
    }

    return (mapping.findForward("showresults"));        
  }
}
