package com.tcmis.internal.hub.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jul 8, 2008
 * Time: 10:53:51 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetManagementInputBean;
import com.tcmis.internal.hub.beans.ConsignedInventoryReportInputBean;
import com.tcmis.internal.hub.beans.IataInputBean;
import com.tcmis.internal.hub.process.CabinetManagementProcess;
import com.tcmis.internal.hub.process.ConsignedInventoryReportProcess;
import com.tcmis.internal.hub.process.IataProcess;

import java.io.*;

/******************************************************************************
 * Controller for
 * @version 1.0
	******************************************************************************/
public class IataAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "iatamain");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	IataProcess process = new IataProcess(this.getDbUser(request));
	//if form is not null we have to perform some action
	if (form == null) {

	}
	//if form is not null we have to perform some action
	else {
	 IataInputBean bean = new IataInputBean();
	 BeanHandler.copyAttributes(form, bean);
//log.debug("bean:"+bean);
//log.debug("action:" + ( (DynaBean) form).get("action"));
     if ( ( (DynaBean) form).get("submitSearch") != null &&
		( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {
		//request.setAttribute("consignedInventoryViewBeanCollection", process.getReportData(bean));
	 }
	 else if (( (DynaBean) form).get("action") != null &&
             ( (String) ( (DynaBean) form).get("action")).length() > 0) {
		//log.debug("update");

	 }
    }
    return mapping.findForward("success");
 }
}