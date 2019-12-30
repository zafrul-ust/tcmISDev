package com.tcmis.client.report.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.MyWorkareaFacilityViewBean;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.beans.PrRulesViewBean;
import com.tcmis.client.report.beans.MaterialTransferHistoryInputBean;
import com.tcmis.client.report.process.MaterialTransferHistoryProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for Material Transfer History
 * @version 1.0
 ******************************************************************************/
public class MaterialTransferHistoryAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "materialtransferhistorymain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		
	     if (!user.getPermissionBean().hasUserPagePermission("materialTransferHistory"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }
	     
	     MaterialTransferHistoryProcess process = new MaterialTransferHistoryProcess(getDbUser(request), getTcmISLocaleString(request));

	 	MaterialTransferHistoryInputBean input = new MaterialTransferHistoryInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));
		if (input.isSearch()) {
			request.setAttribute("materialTransferHistoryColl", process.getSearchResult(input));
				
			return mapping.findForward("success");
		}
		else if (input.isCreateExcel()) {
			setExcel(response, "MaterialTransferHistoryExcel");
			process.getExcelReport(input, process.getSearchResult(input), getLocale(request)).write(response.getOutputStream());
			return noForward;
		}
		else {
			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request));
			Vector<MyWorkareaFacilityViewBean> v = (Vector<MyWorkareaFacilityViewBean>)orderTrackingProcess.getMyWorkareaFacilityViewBean(user.getPersonnelIdBigDecimal());
			request.setAttribute("myWorkareaFacilityViewBeanCollection", v);
			return mapping.findForward("success");
		}

	}
} //end of class
