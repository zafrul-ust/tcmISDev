package com.tcmis.client.openCustomer.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.openCustomer.beans.UserShiptoAdminBean;
import com.tcmis.client.openCustomer.process.ShipToPermissionProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.UserFacilitySelectOvProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for ShiptoPermissions
 * @version 1.0
 ******************************************************************************/
public class ShipToPermissionsAction extends TcmISBaseAction {
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm inputForm, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		//  login
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "shiptopermissionmain".toLowerCase());
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		request.setAttribute("personnelId", request.getParameter("personnelId")); //modifiee
		request.setAttribute("userId", user.getPersonnelIdBigDecimal()); //modifier

		UserShiptoAdminBean input = new UserShiptoAdminBean(inputForm, getTcmISLocale(), user.getPersonnelIdBigDecimal());

		if (input.isNoAction())
			return mapping.findForward("success"); // should not happen

		//  main
		else if (input.isInit()) {
			String fullName = request.getParameter("fullName");
			request.setAttribute("fullName", fullName);
			request.getSession().setAttribute("MODIFIEEFULLNAME", fullName);

			UserFacilitySelectOvProcess process = new UserFacilitySelectOvProcess(getDbUser(request), getTcmISLocaleString(request));
			ShipToPermissionProcess sProcess = new ShipToPermissionProcess(getDbUser(request), getTcmISLocaleString(request));

			request.setAttribute("customerFacilityColl", sProcess.getCustomerShiptoDropdowns(input.getUserId(), input.getPersonnelId()));
		}
		//  result
		else if (input.isSearch()) {
			ShipToPermissionProcess process = new ShipToPermissionProcess(getDbUser(request), getTcmISLocaleString(request));
			request.setAttribute("userShiptoAdminBeanColl", process.getSearchResult(input));

		}
		else if (input.isCreateExcel()) {
			ShipToPermissionProcess process = new ShipToPermissionProcess(getDbUser(request), getTcmISLocaleString(request));
			setExcel(response, "ShiptoPermissionExcel");
			process.getExcelReport(process.getSearchResult(input), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		//  update
		else if (input.isUpdate()) {
			ShipToPermissionProcess process = new ShipToPermissionProcess(getDbUser(request), getTcmISLocaleString(request));
			Collection<UserShiptoAdminBean> beans = BeanHandler.getBeans( (DynaBean)inputForm, "UserShiptoAdminBean", new UserShiptoAdminBean());
			Vector errorMsgs = new Vector();
			for (UserShiptoAdminBean shipTo : beans) {
				if (shipTo.isFacilityAccessChanged() || shipTo.isInvoiceAccessChanged()) {
					String errorMsg = process.updateAccess(shipTo);
					if (errorMsg.length() > 0)
						errorMsgs.add(errorMsg);
				}
			}

			request.setAttribute("userShiptoAdminBeanColl", process.getSearchResult(input));
			request.setAttribute("tcmISErrors", errorMsgs);

		}

		return mapping.findForward("success");
	}

}