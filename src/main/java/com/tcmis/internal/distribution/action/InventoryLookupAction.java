package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.distribution.beans.SalesQuoteLineViewBean;
import com.tcmis.internal.distribution.process.InventoryLookupProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
     ******************************************************************************/
public class InventoryLookupAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "inventorylookupmain");
	  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    HttpSession session = request.getSession();

	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	
    //populate drop downs
    LogisticsInputBean inputBean = new LogisticsInputBean();
    InventoryLookupProcess process = new InventoryLookupProcess(this.getDbUser(request),getTcmISLocale(request));

    if (form == null) {
        return (mapping.findForward("success"));
    }

    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request) );
    String uAction =  (String)( (DynaBean) form).get("uAction");		
    if ( "search".equals(uAction) ) {
    	saveTcmISToken(request);
    	Object[] objs = process.getSearchResult(inputBean);
    	request.setAttribute("logisticsViewBeanCollection", objs[0]);
//    	request.setAttribute("rowSpan", objs[1]);
//    	request.setAttribute("hasRowSpan", objs[2]);
    }
    if ( "addReceipts".equals(uAction) ) {
    	checkToken(request);
/*    	if ((!personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation",null,null,null)) ||
				(!personnelBean.getPermissionBean().hasInventoryGroupPermission("inventoryReconciliation",null,null,null)) )
		{
    		Object[] objs = process.getSearchResult(inputBean);
        	request.setAttribute("logisticsViewBeanCollection", objs[0]);
			return (mapping.findForward("success"));
		}  */
		
		Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "logisticsViewBean", new LogisticsViewBean(), getTcmISLocale(request));
		Collection errorMsgs = process.doAddReceipts(personnelBean, inputBean, updateBeanCollection);
		if (errorMsgs != null)
			request.setAttribute("tcmISErrors", errorMsgs);
		else 
			request.setAttribute("successfullyAdded", "Y");
		
		Object[] objs = process.getSearchResult(inputBean);
    	request.setAttribute("logisticsViewBeanCollection", objs[0]);
    }
    if ( "createExcel".equals(uAction) ) {
    	this.setExcel(response, "InventoryExcel");
    	
    	Object[] objs = process.getSearchResult(inputBean);
        process.getExcelReport((Vector<LogisticsViewBean>)objs[0], (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        return noForward;
    }
    return (mapping.findForward("success"));
  }
}
