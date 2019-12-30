package com.tcmis.client.common.action;

import java.util.Collection;
import java.math.BigDecimal;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryInputBean;
import com.tcmis.internal.hub.process.CabinetInventoryProcess;
import com.tcmis.internal.hub.process.CabinetManagementProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.report.process.AdHocInventoryReportProcess;

/**
 * ***************************************************************************
 * Controller for Cabinet Inventory
 *
 * @version 1.0
 *          ************************************************************************
 */

public class ClientCabinetInventoryAction extends TcmISBaseAction

{
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "newclientcabinetinventorymain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

        PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,	 "personnelBean");
        if (!personnelBean.getPermissionBean().hasUserPagePermission("newClientCabinetInventory"))
        {
            return (mapping.findForward("nopermissions"));
        }

        String forward = "success";
		if (form != null) {
			String uAction = (String) ((DynaBean) form).get("uAction");
			CabinetInventoryInputBean inputBean = new CabinetInventoryInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

			if ("search".equalsIgnoreCase(uAction)) {
				CabinetInventoryProcess cabinetInventoryProcess = new CabinetInventoryProcess(this.getDbUser(request), getTcmISLocaleString(request));
				Collection cabinetInventoryBeanCollection = cabinetInventoryProcess.getCabinetInventoryBeanCollection(inputBean);
				request.setAttribute("cabinetInventoryBeanCollection", cabinetInventoryBeanCollection);
			} else if ("createExcel".equalsIgnoreCase(uAction)) {
				try {
					CabinetInventoryProcess cabinetInventoryProcess = new CabinetInventoryProcess(this.getDbUser(request), getTcmISLocaleString(request));
					this.setExcel(response, "ClientCabinetInventory");
					cabinetInventoryProcess.createExcelFile(inputBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale"),personnelBean).write(response.getOutputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
			} else {

		        if (personnelBean.getFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getFacilityAreaBuildingFloorRoomColl().size() == 0) {
		            CabinetDefinitionManagementProcess cabinetDefinitionManagementProcess = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
                    Collection facilityCollection = cabinetDefinitionManagementProcess.getFacilityAreaBldgRm(personnelBean);
		            personnelBean.setFacilityAreaBuildingFloorRoomColl(facilityCollection);
		            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
		        }else {
		            request.setAttribute("facAppReportViewBeanCollection",personnelBean.getFacilityAreaBuildingFloorRoomColl());
		        }
		        
				//get default facilityId
				if(personnelBean.getMyCompanyDefaultFacilityIdCollection() == null || personnelBean.getMyCompanyDefaultFacilityIdCollection().size() == 0) {
					OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
					personnelBean.setMyCompanyDefaultFacilityIdCollection(orderTrackingProcess.getMyCompanpyDefaultFacility(personnelBean.getPersonnelId()));
				}
				
				CabinetDefinitionManagementProcess process = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
				
				request.setAttribute("storageContainerColl", new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request)).getVvUnidocsStorageContainerBeanColl());
			}
		}

		return (mapping.findForward(forward));
	}

}
