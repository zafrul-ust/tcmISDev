package com.tcmis.client.report.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.het.process.HetContainerInventoryProcess;
import com.tcmis.client.report.process.ClientChemListProcess;
import com.tcmis.client.report.process.ListManagementProcess;
import com.tcmis.client.report.beans.ClientChemListInputBean;
import com.tcmis.client.report.beans.GtImportConsumptionStageBean;
import com.tcmis.client.report.beans.ListManagementInputBean;
import com.tcmis.client.report.beans.ListManagementViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;


/**
 * Controller for list Management.
 */

public class ClientChemListAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientchemlist");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PersonnelBean user = personnelBean;//(PersonnelBean) getSessionObject(request, "personnelBean");

		if ( !personnelBean.getPermissionBean().hasUserPagePermission("listManagement") )
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
//			request.setAttribute("unitsOfMeasure", new HetContainerInventoryProcess(getDbUser(), getTcmISLocale()).getUOMs());
			return mapping.findForward("success");
		}
		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			ClientChemListInputBean inputBean = new ClientChemListInputBean();
			BeanHandler.copyAttributes(form, inputBean,this.getTcmISLocale());
			
			ClientChemListProcess process = new ClientChemListProcess(this.getDbUser(request),getTcmISLocaleString(request));
			if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction()))
			{
				request.setAttribute("listChemicalColl",process.getListChemicals(inputBean,personnelBean,this.getPathCompany(request)));

				this.saveTcmISToken(request);
				return (mapping.findForward("success"));

			}
			if (inputBean.getuAction() !=null &&  "update".equals(inputBean.getuAction()))
			{
				checkToken(request);
//				if (!personnelBean.getPermissionBean().hasPermission("editCustomerList", null))
//				{
//					request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
//					request.setAttribute("listChemicalColl",process.getListChemicals(inputBean,personnelBean,this.getPathCompany(request)));
//					return (mapping.findForward("success"));
//				}				
				GtImportConsumptionStageBean bean = new GtImportConsumptionStageBean();
				Collection<GtImportConsumptionStageBean> beans;
				try {
					beans = BeanHandler.getGridBeans((DynaBean) form, "listManagementViewBean", bean,getTcmISLocale(request),"delete");

					Collection errorMsgs =  process.update(beans, personnelBean);
					if(errorMsgs != null && errorMsgs.size() > 0){
						request.setAttribute("tcmISErrors", errorMsgs); 
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block				
					request.setAttribute("tcmISErrors", e.toString());
				}

				request.setAttribute("listChemicalColl",process.getListChemicals(inputBean,personnelBean,this.getPathCompany(request)));

				return (mapping.findForward("success"));

			}
//			if (inputBean.getuAction() !=null &&  "new".equals(inputBean.getuAction())){
//				if (!personnelBean.getPermissionBean().hasPermission("editCustomerList", null)){
//					return (mapping.findForward("nopermissions"));
//				}
//
//				return mapping.findForward("shownewlist");
//
//			}
//			if (inputBean.getuAction() !=null &&  "addnewlist".equals(inputBean.getuAction())){
//
//				Vector errorMsgs = (Vector)process.addNewList(inputBean,personnelBean);        		 
//
//
//				if(errorMsgs.get(0) != "")
//				{
//					request.setAttribute("newListId",errorMsgs.get(0));
//					request.setAttribute("listColl",process.getLists(personnelBean));
//					request.setAttribute("closeNewListWin", "Y");
//
//				}
//				else
//				{
//					request.setAttribute("tcmISError", errorMsgs.get(1));
//					request.setAttribute("closeNewListWin", "N");				
//				}
//				return mapping.findForward("shownewlist"); 
//			}
//

			else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction()))
			{
				this.setExcel(response, "ClientChemicalExcel");
				process.getExcelReport(process.getListChemicals(inputBean,personnelBean,this.getPathCompany(request)),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				return noForward;
			}

//			else if (inputBean.getuAction() !=null &&  "showUploadList".equals(inputBean.getuAction()))
//			{
//				if (!personnelBean.getPermissionBean().hasPermission("editCustomerList", null)){
//					return (mapping.findForward("nopermissions"));
//				}
//
//				return mapping.findForward("showupdatelist");
//
//			}
//			if ("processUpload".equals(request.getParameter("uAction"))){
//
//				ScannerUploadInputBean bean = new ScannerUploadInputBean();
//				BeanHandler.copyAttributes(form, bean);
//
//				Vector errorMsgs =  process.uploadListFile(bean,personnelBean);        		 
//				//				request.setAttribute("tcmISError", tcmISError);
//				if( !process.isBlank(errorMsgs.get(0)) )
//				{
//					request.setAttribute("newListId",errorMsgs.get(0));
//					request.setAttribute("listColl",process.getLists(personnelBean));
//				}
//				if( process.isBlank(errorMsgs.get(1)) ) {
//					request.setAttribute("closeNewListWin", "Y");
//				}
//				else
//				{
//					request.setAttribute("tcmISError", errorMsgs.get(1));
//					//						request.setAttribute("closeNewListWin", "Y");
//					request.setAttribute("closeNewListWin", "N");				
//				}
//				return mapping.findForward("showupdatelist");
//			}

			/////////////			
			else 
			{
				request.setAttribute("listColl",process.getLists(personnelBean));
			}
		}
		return mapping.findForward("success");
	}
}