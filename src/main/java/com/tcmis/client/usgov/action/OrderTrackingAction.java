package com.tcmis.client.usgov.action;


import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.math.BigDecimal;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/******************************************************************************
 * Controller for
 * @version 1.0
 ******************************************************************************/
public class OrderTrackingAction
extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "usgovdlagasordertrackingmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		/*Need to check if the user has permissions to view this page. if they do not have the permission
		we need to not show them the page.*/

		if (!personnelBean.getPermissionBean().hasUserPagePermission("usgovDlaGasOrderTracking"))
		{
			return (mapping.findForward("nopermissions"));
		}

		if( form == null ) return mapping.findForward("success");

		String uAction = (String) ( (DynaBean)form).get("action");
		OrderTrackingProcess process = new OrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
		OrderTrackingInputBean inputBean = new OrderTrackingInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if( "search".equals(uAction) ) {
			//request.setAttribute("dlaClientOrderTrackingViewBeanCollection", process.getNormalizedSearchData(inputBean));
			request.setAttribute("dlaClientOrderTrackingViewBeanCollection", process.getSearchData(inputBean));
		}else if ("createExcel".equals(uAction)) {

			Collection c = process.getSearchData(inputBean);
			try {
				this.setExcel(response,"Order Tracking");
				process.getExcelReportNoSplit(c,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
	   }else {

	   }  
	   return mapping.findForward("success");
   }
}
