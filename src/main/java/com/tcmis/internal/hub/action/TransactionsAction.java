package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Map;
import java.util.Iterator;

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
import com.tcmis.internal.hub.beans.TransactionInputBean;
import com.tcmis.internal.hub.beans.TransactionTrackingViewBean;
import com.tcmis.internal.hub.process.TransactionsProcess;

/******************************************************************************
 * Controller for transaction
 * @version 1.0
     ******************************************************************************/
public class TransactionsAction
    extends TcmISBaseAction 
{

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception 
  {

    if (!this.isLoggedIn(request,true)) 
    {
        request.setAttribute("requestedPage", "transactionsmain");
        request.setAttribute("requestedURLWithParameters",
		    this.getRequestedURLWithParameters(request));
        return (mapping.findForward("login"));
    }

   /*TODO- make ops views available to raytheon schema, so goleta pages can login as Raytheon not Radian
   Commenting out the page permissions check, because of raytheon goleta,
   we verify their password using company_id RAYTHEON but do all functionality as Radian.
   So cannot check pagepermissions for Receiving, Receiving QC, logistics, transactions, nopicklistpicking till this
   is changed.
   */
    
  PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request,"personnelBean");
	/*if (!personnelBean.getPermissionBean().hasUserPagePermission("transactions"))
	{
	   return (mapping.findForward("nopermissions"));
	}*/
  
    TransactionInputBean inputBean = new TransactionInputBean();
    BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));
		TransactionsProcess txnProcess = new TransactionsProcess(this.getDbUser(request),getTcmISLocaleString(request));

		Collection invgrpColl = personnelBean.getHubInventoryGroupOvBeanCollection();

    	if (inputBean.getSubmitSearch() != null &&
            inputBean.getSubmitSearch().trim().length() > 0)
    	{
    		Collection<TransactionTrackingViewBean> txns = txnProcess.getTransactions(personnelBean,inputBean,invgrpColl);
    		request.setAttribute("transactionsColl",txns);
      }
      else if (( (DynaBean) form).get("uAction") != null &&
              ( (String) ( (DynaBean) form).get("uAction")).equalsIgnoreCase("createExcel") ) {
        this.setExcel(response,"Transactions");
      try {
        txnProcess.getExcelReport(inputBean,txnProcess.getTransactions(personnelBean,inputBean,invgrpColl),
             personnelBean.getLocale()).write(response.getOutputStream());
      }
      catch (Exception ex) {
       ex.printStackTrace();
       return mapping.findForward("genericerrorpage");
      }
      return noForward;
      }      

    return (mapping.findForward("success"));
  }
}