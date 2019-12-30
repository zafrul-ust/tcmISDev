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
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.beans.CustomerLookupSearchViewBean;
import com.tcmis.internal.distribution.process.CustomerLookupSearchProcess;
import com.tcmis.internal.hub.beans.LogisticsViewBean;

  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class CustomerLookupSearchAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
    	
   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customerlookupsearchmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
	}		
   
   	request.setAttribute("valueElementId",request.getParameter("valueElementId"));
	request.setAttribute("displayElementId",request.getParameter("displayElementId"));
   
   	
//  main
  	if( form == null )
      	return (mapping.findForward("success"));
  	String uAction = (String) ( (DynaBean)form).get("uAction");
	if( uAction == null ) return mapping.findForward("success");

//  result
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	CustomerLookupSearchProcess process = new CustomerLookupSearchProcess(this);
  	
	CustomerLookupSearchViewBean inputBean = new CustomerLookupSearchViewBean();
    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

    if ( uAction.equals("searchCustomer") ||  uAction.equals("search")) {
    	Object[] results = process.showResult(inputBean);		
		request.setAttribute("CustomerAddressSearchViewCollection", results[0]);
		request.setAttribute("rowCountPart", results[1]);
        return (mapping.findForward("success"));
    }    
    else if (uAction.equals("createExcel") ) {

    	Object[] objs = process.showResult(inputBean);
    	
		this.setExcel(response,"cust_search_lookup");
		process.getExcelReport((Vector<CustomerAddressSearchViewBean>)objs[0], (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		return noForward;

	}
      return mapping.findForward("success");
    }
  }