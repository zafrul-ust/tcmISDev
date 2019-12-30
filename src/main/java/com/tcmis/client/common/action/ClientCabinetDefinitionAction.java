package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetManagementInputBean;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;

/**
 * ***************************************************************************
 * Controller for cabinet definition
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class ClientCabinetDefinitionAction

	 extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetdefinitionmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//populate drop downs
		if (form == null) return (mapping.findForward("success"));
		String action = (String) ((DynaBean) form).get("uAction");

		CabinetDefinitionManagementProcess process = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
		if (action == null || action.length() == 0) {
			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			if (personnelBean.getUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || personnelBean.getUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().size() == 0) {
				personnelBean.setUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(process.createRelationalObject(process.getUserFacilityWorkAreaGroupWorkAreaData(personnelBean.getPersonnelId())));
			}
			//get default facilityId
			if (StringHandler.isBlankString(personnelBean.getMyDefaultFacilityId())) {
			   OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				personnelBean.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(new BigDecimal(personnelBean.getPersonnelId()),personnelBean.getCompanyId()));
			}
			return (mapping.findForward("success"));
		}

		CabinetManagementInputBean inputBean = new CabinetManagementInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		//If the search button was pressed the getSubmitSearch() value will be not null

		if (action.equalsIgnoreCase("createExcel")) {
			try {
				this.setExcel(response, "CabinetDefinition");
				process.getExcelReport(inputBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		} else {
			Collection dataColl = process.getSearchData(inputBean);
			request.setAttribute("cabinetDefinitionColl",dataColl);
			Object[] results = process.getCabinetManagementRowSpan(dataColl);
			request.setAttribute("rowCountFirstLevel", results[0]);
			request.setAttribute("rowCountSecondLevel", results[1]);
		}
		return (mapping.findForward("success"));
	}
}
