package com.tcmis.internal.supply.action;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.XbuyViewBean;
import com.tcmis.internal.supply.process.XBuyOrderProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * ***************************************************************************
 * Controller for new supplier requests
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class XBuyOrderAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

   /*if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "buyordersmain");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}*/
    /*No login required as this is being called from the database.
    If you want to build a webpage please use a different action class.
    */

		XBuyOrderProcess process = new XBuyOrderProcess (this.getDbUser(request));
		String result = "OK";
    XbuyViewBean bean = new XbuyViewBean();
	  BeanHandler.copyAttributes(form, bean);
	  if( request.getRequestURI().indexOf("stockquery") != -1)
		  result = process.submitStockQuery();
	  else 
    result = process.submitAirgasOrder(bean);

    PrintWriter writer = response.getWriter();
    writer.write(result);
    writer.close();
    return noForward;
	}
}