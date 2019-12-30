package com.tcmis.supplier.xbuy.airgas.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.supplier.xbuy.airgas.beans.*;
import com.tcmis.supplier.xbuy.airgas.process.PageAirgasProcess;
import com.tcmis.supplier.xbuy.airgas.process.AirgasProcess;
/******************************************************************************
 * Controller for FreightAdvice
 * @version 1.0
	******************************************************************************/

public class PageOrderSubmitAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

/*
 * 
 	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "airgasmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}
*/
	 
	if (form == null) 	return (mapping.findForward("success"));

	 String action = (String) ( (DynaBean) form).get("uAction");
	 // get input bean information from search/result page.
	 OrderSubmitInputBean inputBean = new OrderSubmitInputBean();
	 BeanHandler.copyAttributes(form, inputBean);
	 
	 if ( "testAirgasService".equals(action) ) {
		 // set up result page.
		 AirgasProcess process = new AirgasProcess();
		 request.setAttribute("itemWeightExampleViewBeanColl",  process.OrderSubmit(inputBean));
		 return (mapping.findForward("success"));
	 }	 else
	 if ( "testURL".equals(action) ) {
			 // set up result page.
		 PageAirgasProcess process = new PageAirgasProcess(this.getDbUser(request));
			 request.setAttribute("itemWeightExampleViewBeanColl",  process.OrderSubmit(inputBean));
			 return (mapping.findForward("success"));
		 }	 else
	 if ( "display".equals(action) ) {
				return (mapping.findForward("success"));
   	 }  else
  		 if ( "update".equals(action) ) {
	 			return (mapping.findForward("success"));
   	 }  
	 return noForward;
}
}
