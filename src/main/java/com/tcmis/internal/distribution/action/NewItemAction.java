package com.tcmis.internal.distribution.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.process.NewItemProcess;

/******************************************************************************
 * Controller for supplier requests
 * @version 1.0
     ******************************************************************************/
public class NewItemAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "newitem");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    NewItemProcess process = new NewItemProcess(this.getDbUser(request), getTcmISLocaleString(request));
    request.setAttribute("opsEntityRemittanceBeanColl", process.getCurrencyDropDown());
    
    String inventoryGroup = request.getParameter("inventoryGroup"); 
    
    String uAction = (String)( (DynaBean) form).get("uAction");
    if ( "getItemDesc".equals(uAction)){
		
    	String itemId = (String)( (DynaBean) form).get("itemId");
		request.setAttribute("itemDesc", process.getItemDesc(itemId));
		
		return (mapping.findForward("getItemDesc"));  
    }
    else if( "getBuyTypeFlag".equals(uAction)){	
    	// Write out the data for the ajax call
    	PrintWriter out = response.getWriter();
		out.write(process.getBuyTypeFlag(inventoryGroup));
		out.close();	
    }
    else{
		request.setAttribute("buyTypeFlag", process.getBuyTypeFlag(inventoryGroup));
    }

    return mapping.findForward("success");
  }
}