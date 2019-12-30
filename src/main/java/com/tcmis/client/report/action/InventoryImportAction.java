package com.tcmis.client.report.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.report.beans.ClientChemListInputBean;
import com.tcmis.client.report.beans.GtImportInventoryBean;
import com.tcmis.client.report.process.InventoryImportProcess;
import com.tcmis.client.report.process.ClientChemListProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;


/**
 * Controller for Inventory Import.
 */

public class InventoryImportAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "inventoryimportmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PersonnelBean user = personnelBean;//(PersonnelBean) getSessionObject(request, "personnelBean");

		if ( !personnelBean.getPermissionBean().hasUserPagePermission("inventoryImport") )
		{
			return (mapping.findForward("nopermissions"));
		}
		if (form == null) {
			//populate drop downs
			if (user.getUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				CabinetDefinitionManagementProcess cdmProcess = new CabinetDefinitionManagementProcess(getDbUser(request), getTcmISLocaleString(request));
				user.setUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(cdmProcess.createRelationalObject(cdmProcess.getUserFacilityWorkAreaGroupWorkAreaData(user.getPersonnelId())));
			}

			//get default facilityId
			if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
				user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(), user.getCompanyId()));
			}
			
			
			request.setAttribute("purchasingMethod", new ClientChemListProcess(getDbUser(), getTcmISLocale()).getPurchasingMethod(this.getPathCompany(request),""));
			return mapping.findForward("success");
		}
		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			ClientChemListInputBean inputBean = new ClientChemListInputBean();
			BeanHandler.copyAttributes(form, inputBean,this.getTcmISLocale());
			
			InventoryImportProcess process = new InventoryImportProcess(this.getDbUser(request),getTcmISLocaleString(request));
			if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction())) {
				request.setAttribute("inventoryImportColl",process.getInventoryImport(inputBean,personnelBean,this.getPathCompany(request)));
				this.saveTcmISToken(request);
				return (mapping.findForward("success"));
			}if (inputBean.getuAction() !=null &&  "update".equals(inputBean.getuAction())) {
				checkToken(request);
				GtImportInventoryBean bean = new GtImportInventoryBean();
				Collection<GtImportInventoryBean> beans;
				try {
					beans = BeanHandler.getGridBeans((DynaBean) form, "listManagementViewBean", bean,getTcmISLocale(request),"delete");

					Collection errorMsgs =  process.update(beans, personnelBean);
					if(errorMsgs != null && errorMsgs.size() > 0){
						request.setAttribute("tcmISErrors", errorMsgs); 
					}
				} catch (Exception e) {
					request.setAttribute("tcmISErrors", e.toString());
				}

				request.setAttribute("inventoryImportColl",process.getInventoryImport(inputBean,personnelBean,this.getPathCompany(request)));

				return (mapping.findForward("success"));

			}else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction())) {
				this.setExcel(response, "InventoryImportExcel");
				process.getExcelReport(process.getInventoryImport(inputBean,personnelBean,this.getPathCompany(request)),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				return noForward;
			}
		}
		return mapping.findForward("success");
	}
}