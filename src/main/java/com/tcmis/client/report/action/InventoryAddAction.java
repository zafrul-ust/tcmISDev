package com.tcmis.client.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.report.beans.GtImportInventoryBean;
import com.tcmis.client.report.process.InventoryImportProcess;
import com.tcmis.client.report.process.ClientChemListProcess;
import com.tcmis.client.report.process.UploadInventoryCSVFileProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

/**
 * Controller for Usage Import.
 */

public class InventoryAddAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "inventoryadd");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

   //     log.debug("here in InventoryAddAction");

		//if form is not null we have to perform some action
		if (form != null) {
			if ("process".equals(request.getParameter("uAction"))) {
				GtImportInventoryBean bean = new GtImportInventoryBean();
				BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
				UploadInventoryCSVFileProcess p = new UploadInventoryCSVFileProcess(getDbUser(request));
                Object result[] = p.saveInventory(bean, this.getPathCompany(request), request.getParameter("facilityId"), user.getPersonnelId() + "");
                request.setAttribute("done", "OK");
                request.setAttribute("tcmISError", result[0]);
                request.setAttribute("uploadId", result[1]);
                request.setAttribute("unitsOfMeasure", new ClientChemListProcess(getDbUser(), getTcmISLocale()).getUnitOfMeasureList(request.getParameter("facilityId")));
                request.setAttribute("purchasingMethod", new ClientChemListProcess(getDbUser(), getTcmISLocale()).getPurchasingMethod(this.getPathCompany(request),request.getParameter("facilityId")));
                if (!StringHandler.isBlankString((String)result[1])) {
                    request.setAttribute("tradeName",p.getInventoryTradeName(bean,request.getParameter("facilityId"),(String)result[1]));
                }
            }else if ("loaddata".equals(request.getParameter("uAction"))) {
            	InventoryImportProcess cclProcess = new InventoryImportProcess(getDbUser(), getTcmISLocale());
                String callbackData = cclProcess.getUnitOfMeasure(request.getParameter("companyId"), request.getParameter("facilityId"), request.getParameter("msds"), request.getParameter("partNo"));
                request.setAttribute("callbackdata", callbackData);
                return mapping.findForward("loaddata");
            }else {
                if (user.getUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				CabinetDefinitionManagementProcess cdmProcess = new CabinetDefinitionManagementProcess(getDbUser(request), getTcmISLocaleString(request));
				user.setUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(cdmProcess.createRelationalObject(cdmProcess.getUserFacilityWorkAreaGroupWorkAreaData(user.getPersonnelId())));
                }
                //get default facilityId
                if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
                    OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
                    user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(), user.getCompanyId()));
                }
                request.setAttribute("unitsOfMeasure", new ClientChemListProcess(getDbUser(), getTcmISLocale()).getUnitOfMeasureList(request.getParameter("facilityId")));
                request.setAttribute("purchasingMethod", new ClientChemListProcess(getDbUser(), getTcmISLocale()).getPurchasingMethod(this.getPathCompany(request),request.getParameter("facilityId")));
			}
		}
		return mapping.findForward("success");
	}
}