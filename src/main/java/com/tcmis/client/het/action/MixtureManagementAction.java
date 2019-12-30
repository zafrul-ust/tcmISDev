package com.tcmis.client.het.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.het.beans.HetMixtureManagementViewBean;
import com.tcmis.client.het.beans.MixtureManagementInputBean;
import com.tcmis.client.het.process.MixtureManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class MixtureManagementAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, MixtureManagementProcess process, MixtureManagementInputBean input, PersonnelBean user) throws BaseException {
		Collection<HetMixtureManagementViewBean> results = process.getSearchResult(input, user);
		request.setAttribute("mixtureColl", results);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "mixturemanagementmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

			MixtureManagementProcess process = new MixtureManagementProcess(getDbUser(), getTcmISLocale());
			MixtureManagementInputBean input = new MixtureManagementInputBean(form, getTcmISLocale());

			// Search
			if (input.isSearch()) {
				doSearch(request, process, input, personnelBean);
				saveTcmISToken(request);
			}

			//  Update
			else if (input.isUpdate()) {
				checkToken(request);

				// Grab the input rows
				Collection<HetMixtureManagementViewBean> method = BeanHandler.getBeans((DynaBean) form, "mixtureManagementBean", new HetMixtureManagementViewBean(), getTcmISLocale());

				String msg = process.updateMethod(method, personnelBean);

				if (!StringHandler.isBlankString(msg)) {
					request.setAttribute("tcmISError", msg);
				}
				else {
					request.setAttribute("updateSuccess", "Y");
				}
				doSearch(request, process, input, personnelBean);
			}
			else if (input.isCreateExcel()) {
				setExcel(response, "MixtureManagementExcel");
				process.getExcelReport(process.getSearchResult(input, personnelBean), getTcmISLocale()).write(response.getOutputStream());
				next = noForward;
			} else {
				// Set initial value
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				Collection dropDownColl = orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId);

				request.setAttribute("myWorkareaFacilityViewBeanCollection", dropDownColl);
				request.setAttribute("companyId", "LOCKHEED");
			}

		}
		return next;
	}

}
