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
import com.tcmis.internal.hub.beans.TransactionInputBean;
import com.tcmis.internal.hub.process.TransactionsProcess;

/******************************************************************************
 * Controller for transaction
 * @version 1.0
     ******************************************************************************/
public class TransactionAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request,true)) {
      request.setAttribute("requestedPage", "transactions");
      request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    //populate drop downs
    TransactionsProcess txnProcess = new TransactionsProcess(this.getDbUser(request));

    //If you need access to who is logged in
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");

    //Do stuff to get data you are going to need to build the search options
    //or any other extra data you need.
    Collection invgrpColl = personnelBean.getHubInventoryGroupOvBeanCollection();

    request.setAttribute("hubColl",invgrpColl);

    if (form == null) {
      this.saveTcmISToken(request);
    }
    //if form is not null we have to perform some action
    else {
      TransactionInputBean inputBean = new TransactionInputBean();
      BeanHandler.copyAttributes(form, inputBean);

       //If the search button was pressed the getSubmitSearch() value will be not null
       if (inputBean.getSubmitSearch() != null &&
           inputBean.getSubmitSearch().trim().length() > 0) {

          Collection txns = txnProcess.getTransactions(personnelBean, inputBean,invgrpColl);
          //Pass the result collection in request
          request.setAttribute("transactionsColl",txns);

          return (mapping.findForward("showresults"));
       }
    }

    return (mapping.findForward("showresults"));
  }
}
