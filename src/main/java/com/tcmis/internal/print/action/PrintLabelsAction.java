package com.tcmis.internal.print.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingSummaryViewBean;
import com.tcmis.internal.print.process.HubLabelProcess;
import com.tcmis.common.admin.process.ZplDataProcess;

public class PrintLabelsAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "printunitboxlabels");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		LabelInputBean labelInputBean = new LabelInputBean();
		labelInputBean.setPaperSize("31");
		Collection suppPackSummaryViewBeanCollection = new Vector();
		BeanHandler.copyAttributes(form, labelInputBean);

		// Cast form to DynaBean
		DynaBean dynaForm = (DynaBean) form;
		List labelBeans = (List) dynaForm.get("labelBean");
		Iterator iterator = labelBeans.iterator();
		while (iterator.hasNext()) {
			org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();

			SupplierPackingSummaryViewBean supplierPackingSummaryViewBean = new SupplierPackingSummaryViewBean();
			BeanHandler.copyAttributes(lazyBean, supplierPackingSummaryViewBean);
			if (supplierPackingSummaryViewBean.getOk() != null && supplierPackingSummaryViewBean.getOk().length() > 0) {
				// log.info(""+supplierPackingSummaryViewBean.getPrNumber()+"
				// "+supplierPackingSummaryViewBean.getLineItem()+"");
				suppPackSummaryViewBeanCollection.add(supplierPackingSummaryViewBean);
			}
		}

		/*
		 * SupplierPackingSummaryViewBean supplierPackingSummaryViewBean = new
		 * SupplierPackingSummaryViewBean();
		 * supplierPackingSummaryViewBean.setPrNumber(new BigDecimal("486206"));
		 * supplierPackingSummaryViewBean.setLineItem("2");
		 * supplierPackingSummaryViewBean.setPicklistId(new
		 * BigDecimal("183664"));
		 * suppPackSummaryViewBeanCollection.add(supplierPackingSummaryViewBean)
		 * ;
		 */

		// log.info("labelReceipts "+labelInputBean.getLabelReceipts()+"
		// printerPath "+labelInputBean.getPrinterPath()+"");
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		HubLabelProcess labelProcess = new HubLabelProcess(this.getDbUser(request));

		if (suppPackSummaryViewBeanCollection.size() > 0) {
			Collection locationLabelPrinterCollection = new Vector();
			if (personnelBean.getPrinterLocation() != null && personnelBean.getPrinterLocation().length() > 0 && !"811".equalsIgnoreCase(labelInputBean.getPaperSize())) {
				ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
				locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean, labelInputBean);
				if (locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1) {
					request.setAttribute("sourcePage", "printunitboxlabels");
					request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
					return mapping.findForward("printerchoice");
				}
			}
			else {
				// Build PDF Labels here
				return mapping.findForward("pagenotavailable");
			}

			// String labelUrl =
			// labelProcess.buildUnitBoxLabels(personnelBean,labelInputBean,locationLabelPrinterCollection,suppPackSummaryViewBeanCollection);
			String labelUrl = "";
			if (labelUrl.length() > 0) {
				request.setAttribute("filePath", labelUrl);
				// this.setSessionObject(request, labelUrl, "filePath");
				return mapping.findForward("viewfile");
			}
			else {
				return mapping.findForward("systemerrorpage");
			}
		}
		else {
			return mapping.findForward("systemerrorpage");
		}
	}
} // end of class