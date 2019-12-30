package com.tcmis.internal.supply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.PrintPoListInputBean;
import com.tcmis.internal.supply.process.PrintPoListProcess;

/******************************************************************************
 * Controller for Print PO list
 * @version 1.0
	******************************************************************************/
public class PrintPoListAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {
        if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "printpolist");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}
        if (form != null) {
          this.saveTcmISToken(request);
          PrintPoListInputBean inputBean = new PrintPoListInputBean();
          BeanHandler.copyAttributes(form, inputBean);
          if (inputBean.isSearch()) {
            PrintPoListProcess printPoListProcess = new PrintPoListProcess(this.getDbUser(request));
            request.setAttribute("printPoListBeanCollection", printPoListProcess.getSearchResult(inputBean.getSearchType(), inputBean.getSearchText()));
          }
        }
	return mapping.findForward("showresults");
 }
} //end of class
