package com.tcmis.client.het.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.het.beans.FxIncomingShipDetailDataBean;
import com.tcmis.client.het.beans.ShipmentReceivingInputBean;
import com.tcmis.client.het.process.ShipmentReceivingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class ShipmentReceivingAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "shipmentreceivingmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		ShipmentReceivingInputBean input = new ShipmentReceivingInputBean(form, getTcmISLocale());
		ShipmentReceivingProcess process = new ShipmentReceivingProcess(getDbUser(), getTcmISLocale());

		if (input.isSearch()) {
			saveTcmISToken(request);

			Collection dataColl = process.getNestedResults(user.getCompanyId(), getTcmISLocale(), input);

			request.setAttribute("fxIncomingShipDetailDataColl", dataColl);
			request.setAttribute("updateSuccess", Boolean.FALSE);

			return (mapping.findForward("success"));
		}
		else if (input.isSave()) {
			checkToken(request);

			Collection<FxIncomingShipDetailDataBean> beans = BeanHandler.getBeans((DynaBean) form, "fxIncomingShipDetailDataBean", new FxIncomingShipDetailDataBean(), getTcmISLocale(request));
			String errorMsgs = process.saveBeans(beans, input, user);
			Collection dataColl = process.getNestedResults(user.getCompanyId(), getTcmISLocale(), input);
			
			if (StringHandler.isBlankString(errorMsgs)) {
				request.setAttribute("updateSuccess", Boolean.TRUE);
			}
			else {
				request.setAttribute("updateSuccess", Boolean.FALSE);
				request.setAttribute("tcmISErrors", errorMsgs.split(","));
			}

			request.setAttribute("fxIncomingShipDetailDataColl", dataColl);
			return (mapping.findForward("success"));
		}
		else if (input.isCreateExcel()) {
			setExcel(response, "ShipmentReceivingExcel");
			process.getExcelReport(process.getFlatResults(user.getCompanyId(), input), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else {
			if (user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				user.setFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(process.getUserFacilityWorkAreaGroupWorkAreaCollection(user.getPersonnelId()));
			}

			//get default facilityId
			if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
				user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(), user.getCompanyId()));
			}
			return (mapping.findForward("success"));
		}
	}
}
