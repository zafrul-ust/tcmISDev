package com.tcmis.internal.hub.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.DailyInventoryItemViewTotalsBean;
import com.tcmis.internal.hub.beans.DailyInventoryReportInputBean;
import com.tcmis.internal.hub.process.DailyInventoryReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	 ******************************************************************************/
public class DailyInventoryReportAction
	extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	  request.setAttribute("requestedPage", "dailyinventoryreportmain");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}
	
	  PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,	 "personnelBean");
	  BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
      if (!personnelBean.getPermissionBean().hasUserPagePermission("dailyInventoryReport"))
      {
    	  return (mapping.findForward("nopermissions"));
      }

	if (form == null) {
		return mapping.findForward("success");
	}

	  DailyInventoryReportInputBean bean = new
	  DailyInventoryReportInputBean();
	  BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
	  String userAction = (String)( (DynaBean) form).get("userAction");
	  
	if (userAction != null && userAction.equals("search") ) {
		DailyInventoryReportProcess dailyInventoryReportProcess = new
		DailyInventoryReportProcess(this.getDbUser(request),getTcmISLocaleString(request));
		Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
		
		Collection c = dailyInventoryReportProcess.getSearchResult(bean,hubInventoryGroupOvBeanCollection);
		DailyInventoryItemViewTotalsBean totalBean = dailyInventoryReportProcess.getTotalBean(c);
		
		request.setAttribute("dailyInventoryItemViewBeanCollection", c);
		request.setAttribute("totalBean", totalBean);
		
	}
	else 	if (userAction != null && userAction.equals("buttonCreateExcel") ) {

		Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
		DailyInventoryReportProcess dailyInventoryReportProcess = new DailyInventoryReportProcess(this.getDbUser(request),getTcmISLocaleString(request));
		Collection c = dailyInventoryReportProcess.getSearchResult(bean,hubInventoryGroupOvBeanCollection);
		
		this.setExcel(response,"Daily Inventory Report");
		dailyInventoryReportProcess.writeExcelFile(c, dailyInventoryReportProcess.getTotalBean(c), (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());

		request.setAttribute("dailyInventoryItemViewBeanCollection", c);
		request.setAttribute("doexcelpopup", "Yes");
		return noForward;
	}
	return (mapping.findForward("success"));
  }
}
