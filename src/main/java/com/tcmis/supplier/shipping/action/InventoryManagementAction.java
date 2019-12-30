package com.tcmis.supplier.shipping.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.supplier.shipping.beans.CylinderManagementBean;
import com.tcmis.supplier.shipping.beans.InventoryManagementInputBean;
import com.tcmis.supplier.shipping.beans.TransactionMgmtViewBean;
import com.tcmis.supplier.shipping.process.InventoryManagementProcess;

public class InventoryManagementAction extends TcmISBaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "inventorymanagementmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			//set flag so page can be accessed without going through home page
			request.setAttribute("standalone", "true");
			return mapping.findForward("login");
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("inventoryManagement"))
			return mapping.findForward("nopermissions");

		ActionForward next = mapping.findForward("success");
		if (form != null) {
			InventoryManagementProcess process = new InventoryManagementProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			InventoryManagementInputBean inputBean = new InventoryManagementInputBean(form, getLocale());
			if (inputBean.isSearch()) {
				Collection<TransactionMgmtViewBean> coll = process.getSearchResult(inputBean);
				inputBean.setTotalLines(coll.size());
				request.setAttribute("transactionManagementViewBeanCollection", coll);
				request.setAttribute("vvInventoryAdjCodeBeanColl", process.getInventoryAdjustmentCodes(inputBean.getSupplier()));
				request.setAttribute("searchInput", inputBean);
			} else if (inputBean.isCreateExcel()) {
				this.setExcel(response, "Inventory Management");
				process.createExcelReport(inputBean).write(response.getOutputStream());
				next = noForward;
			} else if (inputBean.isAddPartUpdateMinMax()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				PrintWriter out = response.getWriter();
				out.write(process.addPartUpdateMinMax(resultBean, personnelBean.getPersonnelIdBigDecimal()));
				out.close();
				next = noForward;
			} else if (inputBean.isUpdateInsertInventory()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				PrintWriter out = response.getWriter();
				out.write(process.updateInsertInventory(resultBean, personnelBean.getPersonnelIdBigDecimal()));
				out.close();
				next = noForward;
			} else if (inputBean.isUpdateInsertPO()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				PrintWriter out = response.getWriter();
				out.write(process.updateInsertPo(resultBean, personnelBean.getPersonnelIdBigDecimal()));
				out.close();
				next = noForward;
			} else if (inputBean.isUpdateInsertBin()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				PrintWriter out = response.getWriter();
				out.write(process.updateInsertBin(resultBean));
				out.close();
				next = noForward;
			} else if (inputBean.isAddAdjustment()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				PrintWriter out = response.getWriter();
				out.write(process.addAdjustment(resultBean, personnelBean.getPersonnelIdBigDecimal()));
				out.close();
				next = noForward;
			} else if (inputBean.isConvertTransaction()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				PrintWriter out = response.getWriter();
				out.write(process.convertTransaction(resultBean, personnelBean.getPersonnelIdBigDecimal()));
				out.close();
				next = noForward;
			} else if (inputBean.isPartAjaxSearch()) {
				AutocompleteInputBean autocompleteBean = new AutocompleteInputBean(request);
				BeanHandler.copyAttributes(form, autocompleteBean, getTcmISLocale(request));
		        
		        Collection<String> results =  process.getPart(autocompleteBean, request.getParameter("isInventoryLevelIdRequired"));
		        
		        // Write out the data
		        response.setCharacterEncoding("utf-8");	  
		        PrintWriter out = response.getWriter();
		    	for(String id: results)
		    		out.println(id);
				out.close();
				next = noForward;
			} else if (inputBean.isBinAjaxSearch()) {
				AutocompleteInputBean autocompleteBean = new AutocompleteInputBean(request);
				BeanHandler.copyAttributes(form, autocompleteBean, getTcmISLocale(request));
		        
		        Collection<String> results =  process.getBin(autocompleteBean);
		        
		        // Write out the data
		        response.setCharacterEncoding("utf-8");	  
		        PrintWriter out = response.getWriter();
		    	for(String id: results)
		    		out.println(id);
				out.close();
				next = noForward;
			} else if (inputBean.isConvertiblePartAjaxSearch()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				Collection<TransactionMgmtViewBean> results =  process.getConvertibleParts(resultBean);
				JSONArray convertibleParts = new JSONArray();
				JSONObject convertiblePart;
				for (TransactionMgmtViewBean bean : results) {
					convertiblePart = new JSONObject();
					convertiblePart.put("id", bean.getCatPartNo());
					convertiblePart.put("name", bean.getCatPartNo());
					convertibleParts.put(convertiblePart);
				}
				PrintWriter out = response.getWriter();
				out.write(convertibleParts.toString());
				out.close();
				next = noForward;
			} else if (inputBean.isShowMinMaxHistory()) {
				TransactionMgmtViewBean resultBean = new TransactionMgmtViewBean(form, getLocale());
				request.setAttribute("startSearchTime", new Date().getTime());
				request.setAttribute("inventoryLevelLogBeanCollection", process.getInventoryLevelMinMaxHistory(resultBean.getInventoryLevelId()));
				request.setAttribute("endSearchTime", new Date().getTime());
				next = mapping.findForward("showMinMaxHistory");
			} else if (inputBean.isPrintInventoryLabel()) {
				LabelInputBean labelInputBean = new LabelInputBean();
				labelInputBean.setLabelType("container");
				labelInputBean.setPaperSize("31");
				BeanHandler.copyAttributes(form, labelInputBean);
				ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocale());
				Collection<LocationLabelPrinterBean> locationLabelPrinterCollection = new ZplDataProcess(this.getDbUser(request)).getLocationLabelPrinter(personnelBean, labelInputBean);
				if (locationLabelPrinterCollection == null || locationLabelPrinterCollection.isEmpty()) {
					request.setAttribute("tcmISError", library.getString("error.noprinterlocationpleaseselect"));
					next = mapping.findForward("genericerrorpage");
				} else if (locationLabelPrinterCollection.size() == 1) {
					labelInputBean.setPrinterPath(locationLabelPrinterCollection.iterator().next().getPrinterPath());
					String filePath;
					try {
						TransactionMgmtViewBean resultBean = (TransactionMgmtViewBean) BeanHandler.getBean((DynaBean) form, "TransactionManagementViewBean", new TransactionMgmtViewBean(), getLocale());
						filePath = process.printInventoryLabel(resultBean, labelInputBean, new Vector<String>(Arrays.asList("DropShip Hudson DLA II")));
					} catch (Exception e) {
						log.error(e);
						request.setAttribute("tcmISError", library.getString("generic.error"));
						filePath = null;
					}
					//if label is not printed but instead saved as a file or there's an error generating the label
					if (filePath == null || filePath.trim().length() > 0) {
						request.setAttribute("filePath", filePath);
						next = mapping.findForward("viewfile");
					} else
						//if label is printed
						next = mapping.findForward("printlabels");
				} else {
					request.setAttribute("paperSize", labelInputBean.getPaperSize());
					request.setAttribute("sourcePage", "inventorymanagementresults");
					request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
					if (labelInputBean.isDoPrintLabelsNow())
						request.setAttribute("printLabelsNow", labelInputBean.getPrintLabelsNow());
					next = mapping.findForward("printerchoice");
				}
			} else {
				request.setAttribute("locationBeanCollection", process.getLocationDropdownDataWithEditMinMaxPermission(personnelBean));
				request.setAttribute("transactionTypeStatusBeanCollection", process.getTransactionTypeStatusData());
			}
		}
		
		return next;
	}
}