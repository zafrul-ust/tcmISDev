package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tcmis.internal.hub.beans.ShippingSampleInputBean;
import com.tcmis.internal.hub.process.PickingStatusProcess;
import com.tcmis.internal.hub.process.ShippingSampleProcess;

/******************************************************************************
 * Controller for shipping sample
 * 
 * @version 1.0
 ******************************************************************************/
public class ShippingSampleAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "shippingsample");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}
		
		if (form != null) {
			ShippingSampleInputBean inputBean = new ShippingSampleInputBean(form);
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocale());
			if (inputBean.getHub() != null && inputBean.getReceiptId() != null) {
				ShippingSampleProcess process = new ShippingSampleProcess(getDbUser(request), getTcmISLocale(request));
				PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
				if (inputBean.isStoreAndPrintSampleData()) {
					BigDecimal newPickingUnitId = null;
					try {
						newPickingUnitId = process.insertSampleData(inputBean);
					} catch (Exception e) {
						request.setAttribute("tcmISError", library.getString("error.db.update"));
					}
					if (newPickingUnitId != null) {
						LabelInputBean labelInputBean = new LabelInputBean();
						BeanHandler.copyAttributes(form, labelInputBean);
						Collection locationLabelPrinterCollection = new ZplDataProcess(this.getDbUser(request)).getLocationLabelPrinter(user, labelInputBean);
						if (locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1) {
							request.setAttribute("paperSize", labelInputBean.getPaperSize());
							request.setAttribute("sourcePage", "shippingsample");
							request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
							if (labelInputBean.isDoPrintLabelsNow()) {
								request.setAttribute("printLabelsNow", labelInputBean.getPrintLabelsNow());
							}
							
							return mapping.findForward("printerchoice");
						}
						labelInputBean.setPrinterPath(((LocationLabelPrinterBean) locationLabelPrinterCollection.iterator().next()).getPrinterPath());
						PickingStatusInputBean pickingStatusBean = new PickingStatusInputBean();
						pickingStatusBean.setPickingUnitId(newPickingUnitId);
						pickingStatusBean.setLabelPrintType(inputBean.getLabelType());
						pickingStatusBean.setLabelPrintQty(inputBean.getLabelQuantity());
						String filePath = new PickingStatusProcess(this.getDbUser(request)).printSampleDeliveryLabel(pickingStatusBean, labelInputBean);
						if (filePath == null)
							request.setAttribute("tcmISError", new ResourceLibrary("com.tcmis.common.resources.CommonResources").getString("generic.error"));
						else if (filePath.trim().length() == 0)
							return mapping.findForward("printlabels");
						else {
							request.setAttribute("filePath", filePath);
							return mapping.findForward("viewfile");
						}
					}
				}
				request.setAttribute("companyCollection", process.getCompanyDropDownData(inputBean.getReceiptId()));
				request.setAttribute("shippingSampleInputBean", inputBean);
			} else
				request.setAttribute("tcmISError", library.getString("label.hub") + "|" + library.getString("label.receiptid") + ":" + library.getString("main.nodatafound"));
		}

		return mapping.findForward("success");
	}
}