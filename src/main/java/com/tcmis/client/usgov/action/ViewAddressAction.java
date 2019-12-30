package com.tcmis.client.usgov.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 5, 2008
 * Time: 11:52:33 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

import com.tcmis.client.usgov.process.OrderTrackingProcess;
import com.tcmis.client.usgov.beans.OrderTrackingInputBean;

import java.io.*;

/******************************************************************************
 * Controller for
 * @version 1.0
	******************************************************************************/
public class ViewAddressAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "ordertrackingmain");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

    OrderTrackingInputBean bean = new OrderTrackingInputBean();
    BeanHandler.copyAttributes(form, bean);
    OrderTrackingProcess process = new OrderTrackingProcess(this.getDbUser(request));
    request.setAttribute("locationBean", process.getAddress(bean));
    request.setAttribute("dodaac", bean.getUltimateDodaac());
//    RequestDispatcher rd = this.getServlet().getServletContext().getRequestDispatcher("/usgov/ViewMsds?act=msds&id=1219");
//    rd.forward(request, response);

    return (mapping.findForward("success"));
 }
}
