package com.tcmis.internal.hub.action;


import java.util.*;
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
import com.tcmis.internal.hub.process.CabinetManagementProcess;
import com.tcmis.internal.hub.process.ConsignedInventoryReportProcess;

import java.io.*;

/******************************************************************************
 * Controller for
 * @version 1.0
	******************************************************************************/
public class ConsignedInventoryReportAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "consignedinventoryreportmain");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request,"personnelBean");
	if (!personnelBean.getPermissionBean().hasUserPagePermission("consignedInventoryReport"))
	{
	   return (mapping.findForward("nopermissions"));
	}

    ConsignedInventoryReportProcess process = new ConsignedInventoryReportProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
	//if form is not null we have to perform some action
	if (form == null) {

	}
	//if form is not null we have to perform some action
	else {
	 ConsignedInventoryReportInputBean bean = new ConsignedInventoryReportInputBean();
	 // enable locale for dates.
	 BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

//log.debug("action:" + ( (DynaBean) form).get("action"));
     if ( ( (DynaBean) form).get("submitSearch") != null &&
		( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {
		request.setAttribute("consignedInventoryViewBeanCollection", process.getReportData(bean));
	 }
	 else if (( (DynaBean) form).get("action") != null &&
             ( (String) ( (DynaBean) form).get("action")).length() > 0) {
			try {
				this.setExcel(response,"ConsignedInventoryReport");
				process.getExcelReport(bean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			}
			catch (Exception ex) {
			 ex.printStackTrace();
			 return mapping.findForward("genericerrorpage");
			}
			return noForward;
	 }
    }
    return mapping.findForward("success");
 }
}
