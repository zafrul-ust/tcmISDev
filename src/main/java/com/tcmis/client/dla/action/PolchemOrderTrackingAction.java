package com.tcmis.client.dla.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.dla.beans.PolchemOrderTrackingInputBean;
import com.tcmis.client.dla.process.PolchemOrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;


/******************************************************************************
 * Controller for FreightAdvice
 * @version 1.0
 ******************************************************************************/

public class PolchemOrderTrackingAction
extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		Log log = LogFactory.getLog(this.getClass());


		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "polchemordertrackingmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		/*Need to check if the user has permissions to view this page. if they do not have the permission
		we need to not show them the page.*/

		if (!personnelBean.getPermissionBean().hasUserPagePermission("polchemOrderTracking"))
		{
			return (mapping.findForward("nopermissions"));
		}

		if( form == null ) return mapping.findForward("success");

		String uAction = (String) ( (DynaBean)form).get("uAction");
		PolchemOrderTrackingProcess process = new PolchemOrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
		PolchemOrderTrackingInputBean inputBean = new PolchemOrderTrackingInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if (log.isDebugEnabled()) {
			log.debug("uAction" + uAction);
		}

		if( "search".equals(uAction) ) {
			Object[] results = process.showResult(inputBean);
			request.setAttribute("polchemOrderStatusViewBeanCollection", results[0]);
			request.setAttribute("rowCountPart", results[1]);
		}else if ("createExcel".equals(uAction)) {

			Object[] results =  process.showResult(inputBean);
			this.setExcel(response,"Polchem Order Tracking");
			process.getExcelReport((Collection) results[0], (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;

		}else {
			request.setAttribute("vvCurrentOrderStatusBeanCollection", process.getStatusCollection());
		}
		return mapping.findForward("success");
	}
}
