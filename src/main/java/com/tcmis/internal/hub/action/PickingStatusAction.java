package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.PickingStatusInputBean;
import com.tcmis.internal.hub.beans.PickingStatusViewBean;
import com.tcmis.internal.hub.process.PickingStatusProcess;
import com.tcmis.ws.scanner.process.PrintProcess;

/******************************************************************************
 * Controller for PicklistPicking
 * 
 * @version 1.0
 ******************************************************************************/
public class PickingStatusAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		String forward = "success";
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "pickingstatusmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		if (!user.getPermissionBean().hasUserPagePermission("pickingStatus")) {
			return (mapping.findForward("nopermissions"));
		}

		PickingStatusProcess process = new PickingStatusProcess(this.getDbUser(request));

		PickingStatusInputBean input = new PickingStatusInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale());
		request.setAttribute("vvPickingStateColl", process.getPickingStateColl());
		request.setAttribute("vvPickingGroupColl", process.getPickingGroups(input));

		if (input.isCreateExcel()) {
			this.setExcel(response, "Picking_Status_List");
			process.getExcelReport(input, user, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else if (input.hasAction()){
			if (input.isUpdate()) {
				try {
					Collection<PickingStatusViewBean> pickingStatusColl = BeanHandler.getBeans((DynaBean) form, "pickingStatusData", new PickingStatusViewBean());
					process.updatePickingStatus(input, user, pickingStatusColl);
				}
				catch (BaseException e) {
					request.setAttribute("tcmISError", e.getMessage());
					input.setuAction("");
				}
			}
			else if (input.isUpdateDotOverride()) {
				if (!user.getPermissionBean().hasFacilityPermission("overrideShippingInformation", null, null)) {
					return (mapping.findForward("nopermissions"));
				}
				else {
					process.updatePickingStatusShipmentOverride(input, user);
				}
			}
			else if (input.isPrintIataDotLabels()) {
				LabelInputBean labelInputBean = new LabelInputBean();

				BeanHandler.copyAttributes(form, labelInputBean);
				ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
				Collection locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(user, labelInputBean);
				if (locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1) {
					request.setAttribute("paperSize", labelInputBean.getPaperSize());
					request.setAttribute("sourcePage", "pickingstatusresults");
					request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
					if (labelInputBean.isDoPrintLabelsNow()) {
						request.setAttribute("printLabelsNow", labelInputBean.getPrintLabelsNow());
					}
					return mapping.findForward("printerchoice");
				}
				String filePath = process.printIataDotLabel(input, labelInputBean);
				if (filePath == null)
					request.setAttribute("tcmISError", new ResourceLibrary("com.tcmis.common.resources.CommonResources").getString("generic.error"));
				else if (filePath.trim().length() == 0)
					return mapping.findForward("printlabels");
				else {
					request.setAttribute("filePath", filePath);
					return mapping.findForward("viewfile");
				}
			}
			else if (input.isPrintStraightBOL()) {
				PrintProcess printProcess = new PrintProcess(this.getDbUser(request));
				String pdfURL = printProcess.printStraightBOLFromTCMIS(input.getTabletShipmentId(), user.getPersonnelIdBigDecimal(), user.getPrinterLocation());
				
				request.setAttribute("filePath", pdfURL);
				
				return mapping.findForward("viewfile");
			}
			else if (input.isPrintSampleDeliveryLabel()) {
				LabelInputBean labelInputBean = new LabelInputBean();

				BeanHandler.copyAttributes(form, labelInputBean);
				ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
				Collection locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(user, labelInputBean);
				if (locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1) {
					request.setAttribute("paperSize", labelInputBean.getPaperSize());
					request.setAttribute("sourcePage", "pickingstatusresults");
					request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
					if (labelInputBean.isDoPrintLabelsNow()) {
						request.setAttribute("printLabelsNow", labelInputBean.getPrintLabelsNow());
					}
					return mapping.findForward("printerchoice");
				}
				labelInputBean.setPrinterPath(((LocationLabelPrinterBean) locationLabelPrinterCollection.iterator().next()).getPrinterPath());
				String filePath = process.printSampleDeliveryLabel(input, labelInputBean);
				if (filePath == null)
					request.setAttribute("tcmISError", new ResourceLibrary("com.tcmis.common.resources.CommonResources").getString("generic.error"));
				else if (filePath.trim().length() == 0)
					return mapping.findForward("printlabels");
				else {
					request.setAttribute("filePath", filePath);
					return mapping.findForward("viewfile");
				}
			}

			request.setAttribute("pickingUnitDocuments", process.getPickingDocuments(input));
			request.setAttribute("pickingUnitColl", process.getSearchResult(input));
		}
		this.saveTcmISToken(request);
		return mapping.findForward(forward);
	}
}
