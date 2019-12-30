package com.tcmis.client.het.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.het.beans.HetContainerInventoryInputBean;
import com.tcmis.client.het.beans.HetContainerInventoryViewBean;
import com.tcmis.client.het.process.HetContainerInventoryProcess;
import com.tcmis.client.het.process.ShipmentReceivingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class HetContainerInventoryAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, HetContainerInventoryProcess process, HetContainerInventoryInputBean input, PersonnelBean user) throws BaseException {
		Collection<HetContainerInventoryViewBean>  flatResults = process.getSearchFlatResult(input, user);
		request.setAttribute("hetContainerInventoryColl", process.getSearchResult(flatResults));
		request.setAttribute("workAreaColl", process.getWorkAreaColl(flatResults));
		request.setAttribute("deletionCodes", process.getDeletionCodes(user.getCompanyId()));
		request.setAttribute("unitsOfMeasure", process.getUOMs());
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "hetcontainerinventorymain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		/* Need to check if the user has permissions to view this page. if they do not have the permission
			  we need to not show them the page.*/
		/*if (!user.getPermissionBean().hasUserPagePermission("openPos")) {
			return (mapping.findForward("nopermissions"));
		}*/
		HetContainerInventoryInputBean input = new HetContainerInventoryInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

		HetContainerInventoryProcess process = new HetContainerInventoryProcess(getDbUser(), getTcmISLocale());

		if (input.isSearch()) {
			saveTcmISToken(request);
			doSearch(request, process, input, user);
			return (mapping.findForward("success"));
		}
		else if (input.isGetTransferWorkAreas()) {
			request.setAttribute("workAreasForTransfer", process.getWorkAreasForTransfer(input, user));
			return (mapping.findForward("ajax"));
		}
		else if (input.isTransfer()) {
			checkToken(request);

			Collection<HetContainerInventoryViewBean> beans = BeanHandler.getBeans((DynaBean) form, "hetContainerInventoryViewBean", new HetContainerInventoryViewBean(), getTcmISLocale(request), "okDoUpdate");
			String errorMsgs = process.transfer(beans);
			if (!StringHandler.isBlankString(errorMsgs)) {
				request.setAttribute("tcmISError", errorMsgs);
			}

			doSearch(request, process, input, user);

			return (mapping.findForward("success"));
		}
		else if (input.isUpdate()) {
			checkToken(request);

			Collection<HetContainerInventoryViewBean> beans = BeanHandler.getBeans((DynaBean) form, "hetContainerInventoryViewBean", new HetContainerInventoryViewBean(), getTcmISLocale(request), "okDoUpdate");
			String errorMsgs = process.update(beans, user);
			if (!StringHandler.isBlankString(errorMsgs)) {
				request.setAttribute("tcmISError", errorMsgs);
			}

			doSearch(request, process, input, user);

			return (mapping.findForward("success"));
		}
		else if (input.isDelete()) {
			checkToken(request);

			Collection<HetContainerInventoryViewBean> beans = BeanHandler.getBeans((DynaBean) form, "hetContainerInventoryViewBean", new HetContainerInventoryViewBean(), getTcmISLocale(request), "okDoUpdate");

			String errorMsgs = process.delete(beans, user);
			if (!StringHandler.isBlankString(errorMsgs)) {
				request.setAttribute("tcmISError", errorMsgs);
			}

			doSearch(request, process, input, user);

			return (mapping.findForward("success"));
		}
		else if (input.isCreateExcel()) {
			setExcel(response, "ContainerInventoryExcel");
			process.getExcelReport(process.getSearchFlatResult(input, user), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else {
			if (user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				ShipmentReceivingProcess shipProcess = new ShipmentReceivingProcess(getDbUser(), getTcmISLocale());
				user.setFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(shipProcess.getUserFacilityWorkAreaGroupWorkAreaCollection(user.getPersonnelId()));
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
