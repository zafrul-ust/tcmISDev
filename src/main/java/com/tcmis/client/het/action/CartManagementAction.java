package com.tcmis.client.het.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.het.beans.CartManagementInputBean;
import com.tcmis.client.het.beans.HetCartInputBean;
import com.tcmis.client.het.beans.HetCartViewBean;
import com.tcmis.client.het.process.CartManagementProcess;
import com.tcmis.client.het.process.ShipmentReceivingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class CartManagementAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, CartManagementProcess process, CartManagementInputBean input, PersonnelBean user) throws BaseException {
		Collection<HetCartViewBean> results = process.getSearchData(input, user);
		input.setTotalLines(results.size());
		request.setAttribute("cartCollection", results);
		request.setAttribute("input", input);
		input.setCompanyId(user.getCompanyId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "cartmanagementmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			CartManagementInputBean input = new CartManagementInputBean(form, getTcmISLocale());

			CartManagementProcess process = new CartManagementProcess(getDbUser(), getTcmISLocale());
			if (input.isUpdate()) {
				checkToken(request);

				// Grab the input rows
				Collection<HetCartInputBean> carts = BeanHandler.getBeans((DynaBean) form, "cartManagement", new HetCartInputBean(), getLocale(request));

				String msg = process.updateCarts(input, carts, user);

				if (!StringHandler.isBlankString(msg)) {
					request.setAttribute("tcmISError", msg);
				}
				else {
					request.setAttribute("updateSuccess", "Y");
				}
				doSearch(request, process, input, user);
			}
			else if (input.isSearch()) {
				doSearch(request, process, input, user);
				saveTcmISToken(request);
			}
			//populate drop downs
			if (user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				ShipmentReceivingProcess shipProcess = new ShipmentReceivingProcess(getDbUser(), getTcmISLocale());
				user.setFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(shipProcess.getUserFacilityWorkAreaGroupWorkAreaCollection(user.getPersonnelId()));
			}

			//get default facilityId
			if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
				user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(), user.getCompanyId()));
			}
		}

		return next;
	}

}