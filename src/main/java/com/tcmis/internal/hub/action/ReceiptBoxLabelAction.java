package com.tcmis.internal.hub.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ContainerLabelMasterViewBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.process.LabelProcess;
import com.tcmis.internal.hub.process.ZplProcess;

public class ReceiptBoxLabelAction extends TcmISBaseAction {
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "printreceiptboxlabels");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form != null) {
			try {
				LabelInputBean labelInputBean = new LabelInputBean();
				BeanHandler.copyAttributes(form, labelInputBean);

				PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

				if (labelInputBean.isDoReceiptboxlabels()) {

					// Get the session object that holds the beans previously retrieved from the database
					Collection dbBeans = (Collection) getSessionObject(request, "labelDataCollection");

					// Cast form to DynaBean to get the passed data collection
					DynaBean dynaForm = (DynaBean) form;
					List userModifiedBeans = (List) dynaForm.get("containerLabelMasterViewBean");

					// If we're printing labels via this path, default printing kit labels to OFF
					labelInputBean.setSkipKitLabels("YES");
					return printLabels(mapping, labelInputBean, personnelBean, dbBeans, userModifiedBeans);

				}
				else if (labelInputBean.isDoLabelreceipts()) {
					LabelProcess labelProcess = new LabelProcess(this.getDbUser(request), getTcmISLocaleString(request));

					if (personnelBean.getPrinterLocation() != null && personnelBean.getPrinterLocation().length() > 0) {

						ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
						Collection locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean, labelInputBean);
						if (locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1) {
							request.setAttribute("labelReceipts", labelInputBean.getLabelReceipts());
							request.setAttribute("paperSize", labelInputBean.getPaperSize());
							request.setAttribute("sourcePage", "printreceiptboxlabels");
							request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
							if (labelInputBean.isDoPrintLabelsNow()) {
								request.setAttribute("printLabelsNow", labelInputBean.getPrintLabelsNow());
							}
							return mapping.findForward("printerchoice");
						}

						Collection<ContainerLabelMasterViewBean> dbBeans = labelProcess.getReceiptLabelData(labelInputBean.getLabelReceipts());

						if (labelInputBean.isDoPrintLabelsNow()) {
							// Get the user modified beans with the print quantities
							if( "Y".equals(request.getParameter("FromLogisticsNew"))) {
						    	Vector<ContainerLabelMasterViewBean> beans = (Vector<ContainerLabelMasterViewBean>)BeanHandler.getGridBeans((DynaBean)form,"LogisticsBean",new ContainerLabelMasterViewBean(),getTcmISLocale(request),"ok");
						    	return printLabelsLogisticsNew(mapping, labelInputBean, personnelBean, dbBeans, beans);
							}
							DynaBean dynaForm = (DynaBean) form;
							List userModifiedBeans = (List) dynaForm.get("containerLabelMasterViewBean");
							return printLabels(mapping, labelInputBean, personnelBean, dbBeans, userModifiedBeans);
						}
						else {
							setSessionObject(request, dbBeans, "labelDataCollection");
							request.setAttribute("labelDataCollection", dbBeans);
							request.setAttribute("labelReceipts", labelInputBean.getLabelReceipts());
							request.setAttribute("printerPath", labelInputBean.getPrinterPath());
							request.setAttribute("paperSize", labelInputBean.getPaperSize());

							for (ContainerLabelMasterViewBean clmvBean : dbBeans) {
								if (clmvBean.isInventoryGroupTucsonRay()) {
									request.setAttribute("tucsonRay", "true");
									break;
								}
							}
							return mapping.findForward("showinput");
						}
					}
					else {
						return mapping.findForward("printerchoice");
					}
				}
				return mapping.findForward("showinput");
			}
			catch (BaseException be) {
				processExceptions(request, mapping, be);
			}
			catch (Exception e) {
				processExceptions(request, mapping, new BaseException(e));
			}
		}
		return mapping.findForward("genericerrorpage");
	}

	@SuppressWarnings("unchecked")
	private ActionForward printLabels(ActionMapping mapping, LabelInputBean labelInputBean, PersonnelBean user, Collection<ContainerLabelMasterViewBean> dbBeans, Collection<LazyDynaBean> userModifiedBeans) throws Exception {
		ResourceLibrary resource = new ResourceLibrary("label");
		Vector printableBeans = new Vector();

		for (LazyDynaBean userdataBean : userModifiedBeans) {

			BigDecimal userReceiptId = new BigDecimal(StringHandler.emptyIfNull(userdataBean.get("receiptId")));

			// Match up the user entered bean with the fully populated bean from the DB
			for (ContainerLabelMasterViewBean dbBean : dbBeans) {
				if (userReceiptId.equals(dbBean.getReceiptId())) {

					String quantityReceived = StringHandler.emptyIfNull(userdataBean.get("quantityReceived"));
					if (StringHandler.isBlankString(quantityReceived)) {
						break;
					}
					dbBean.setQuantityReceived(new BigDecimal(quantityReceived));

					String quantityOnLabel = StringHandler.emptyIfNull(userdataBean.get("quantityOnLabel"));
					if (quantityOnLabel.length() > 0) {
						dbBean.setQuantityOnLabel(quantityOnLabel);
					}

					String startingPrintId = StringHandler.emptyIfNull(userdataBean.get("startingPrintId"));
					if (startingPrintId.length() > 0) {
						dbBean.setStartingPrintId(Integer.parseInt(startingPrintId));
					}

					printableBeans.addElement(dbBean);
					break;
				}
			}
		}

		File tempDir = new File(resource.getString("label.serverpath"));
		File tempFile = File.createTempFile("largelabeljnlp", ".jnlp", tempDir);

		String dbUser = getDbUser(request);

		ZplDataProcess zplDataProcess = new ZplDataProcess(dbUser);
		Collection locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(user, labelInputBean);
		ZplProcess zplProcess = new ZplProcess(dbUser);
		zplProcess.buildReceiptZpl(printableBeans, user, labelInputBean.getPaperSize(), "", locationLabelPrinterCollection, labelInputBean.isPrintKitLabels(), tempFile.getAbsolutePath());

		if (!(labelInputBean.getPaperSize() != null && labelInputBean.getPaperSize().equalsIgnoreCase("receiptDocument")))
		{
			//Update Receipt Table upon printing label with the last print date and pickable='y'
			LabelProcess labelProcess = new LabelProcess(this.getDbUser(request),getTcmISLocaleString(request));
			labelProcess.updateReceiptPickableStatus(labelInputBean.getLabelReceipts(),
					"Y", labelInputBean.getPaperSize());
		}
		setSessionObject(request, resource.getString("label.urlpath") + tempFile.getName(), "filePath");
		request.setAttribute("doexcelpopup", "Yes");
		return mapping.findForward("viewfile");
	}

	private ActionForward printLabelsLogisticsNew(ActionMapping mapping, LabelInputBean labelInputBean, PersonnelBean user, Collection<ContainerLabelMasterViewBean> dbBeans, Vector<ContainerLabelMasterViewBean> userModifiedBeans) throws Exception {
		ResourceLibrary resource = new ResourceLibrary("label");
		Vector printableBeans = new Vector();

		for (ContainerLabelMasterViewBean userdataBean : userModifiedBeans) {

			BigDecimal userReceiptId = new BigDecimal(StringHandler.emptyIfNull(userdataBean.getReceiptId()));

			// Match up the user entered bean with the fully populated bean from the DB
			for (ContainerLabelMasterViewBean dbBean : dbBeans) {
				if (userReceiptId.equals(dbBean.getReceiptId())) {

					dbBean.setQuantityReceived(userdataBean.getNoOfLabels());

					String quantityOnLabel = StringHandler.emptyIfNull(userdataBean.getQuantityOnLabel());
					if (quantityOnLabel.length() > 0) {
						dbBean.setQuantityOnLabel(quantityOnLabel);
					}

					String startingPrintId = StringHandler.emptyIfNull(userdataBean.getStartingPrintId());
					if (startingPrintId.length() > 0) {
						dbBean.setStartingPrintId(Integer.parseInt(startingPrintId));
					}

					printableBeans.addElement(dbBean);
					break;
				}
			}
		}

		File tempDir = new File(resource.getString("label.serverpath"));
		File tempFile = File.createTempFile("largelabeljnlp", ".jnlp", tempDir);

		String dbUser = getDbUser(request);

		ZplDataProcess zplDataProcess = new ZplDataProcess(dbUser);
		Collection locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(user, labelInputBean);
		ZplProcess zplProcess = new ZplProcess(dbUser);
		zplProcess.buildReceiptZpl(printableBeans, user, labelInputBean.getPaperSize(), "", locationLabelPrinterCollection, labelInputBean.isPrintKitLabels(), tempFile.getAbsolutePath());

		if (!(labelInputBean.getPaperSize() != null && labelInputBean.getPaperSize().equalsIgnoreCase("receiptDocument")))
		{
			//Update Receipt Table upon printing label with the last print date and pickable='y'
			LabelProcess labelProcess = new LabelProcess(this.getDbUser(request),getTcmISLocaleString(request));
			labelProcess.updateReceiptPickableStatus(labelInputBean.getLabelReceipts(),
					"Y", labelInputBean.getPaperSize());
		}
		setSessionObject(request, resource.getString("label.urlpath") + tempFile.getName(), "filePath");
		request.setAttribute("doexcelpopup", "Yes");
		return mapping.findForward("viewfile");
	}
	

} //end of class