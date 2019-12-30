package com.tcmis.internal.hub.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
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
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.ReceivingViewBean;
import com.tcmis.internal.hub.beans.IsValidTransferReceiptBean;
import com.tcmis.internal.hub.process.BinLabelsProcess;
import com.tcmis.internal.hub.process.NonChemicalReceivingQcProcess;
import com.tcmis.internal.hub.process.ReceivingProcess;
import com.tcmis.internal.hub.process.ReceivingQcProcess;
import com.tcmis.internal.hub.process.ShipmentHistoryProcess;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ReceivingAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "showreceiving");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) {
			//this.saveTcmISToken(request);
		}

		//populate drop downs
		//ShipmentHistoryProcess igdprocess = new ShipmentHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
		if (this.getSessionObject(request, "hubPrefViewOvBeanCollection") == null) {
			/*this.setSessionObject(request,
		 igdprocess.getDropDownData( ( (PersonnelBean)this.
		 getSessionObject(request, "personnelBean")).getPersonnelId()),
		 "hubPrefViewOvBeanCollection");*/

			VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),this.getTcmISLocale(request));
			this.setSessionObject(request, vvDataProcess.getActiveVvLotStatus(), "vvLotStatusBeanCollection");
			request.setAttribute("searchWhat", "PO");
		}

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			ReceivingInputBean inputBean = new ReceivingInputBean();
			inputBean.setDoTrim(true);
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			//log.debug("bean:" + bean.getSourceHub());

			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
			request.setAttribute("receivingOnlyBins",
					binLabelsProcess.getReceivingOnlyBins(request.getParameter("sourceHub")));
			//check what button was pressed and determine where to go
			if (inputBean.getSubmitSearch() != null && inputBean.getSubmitSearch().trim().length() > 0) {
				this.saveTcmISToken(request);
				//check if it's chemical or non-chemical
				ReceivingProcess process = new ReceivingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				/*if (log.isDebugEnabled()) {
			log.debug("category:" + inputBean.getCategory() + " Hub Name " +	inputBean.getSourceHubName() + "");
		  }*/
				Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();

				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					//get search result
					Collection c = process.getChemicalResult(inputBean, personnelBean.getPermissionBean().hasInventoryGroupPermission("Receiving", null, inputBean.getSourceHubName(), null), personnelBean);
					//add result and pass it to the jsp page
					//request.setAttribute("receivingViewBeanCollection", c);
					this.setSessionObject(request, c, "receivingViewBeanCollection");
					request.setAttribute("receivingViewRelationBeanCollection", process.createRelationalObject(c));
					return (mapping.findForward("showchemical"));
				} else {
					//get search result
					Collection c = process.getNonChemicalResult(inputBean, personnelBean);
					//add result and pass it to the jsp page
					this.setSessionObject(request, c, "receivingViewBeanCollection");
					request.setAttribute("receivingViewRelationBeanCollection", process.createRelationalObject(c));
					return (mapping.findForward("shownonchemical"));
				}
			} else
			if ((inputBean.getSubmitReceive() != null && inputBean.getSubmitReceive().trim().length() > 0) || (inputBean.getDuplicateLine() != null && inputBean.getDuplicateLine().length() > 0) || (inputBean.getDuplicatePkgLine() != null && inputBean.getDuplicatePkgLine().length() > 0) || (inputBean.getDuplicateKitLine() != null && inputBean.getDuplicateKitLine().length() > 0)) {
				if (!this.isTcmISTokenValid(request, true)) {
					BaseException be = new BaseException("Duplicate form submission");
					be.setMessageKey("error.submit.duplicate");
					throw be;
				}
				this.saveTcmISToken(request);
				ReceivingProcess process = new ReceivingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				Vector savedBeanVector = new Vector((Collection) this.getSessionObject(request, "receivingViewBeanCollection"));

				// Cast form to DynaBean
				DynaBean dynaForm = (DynaBean) form;
				List receivingViewBeans = (List) dynaForm.get("receivingViewBean");
				Iterator iterator = receivingViewBeans.iterator();
				//int count = 0;
				
                
				Collection receivingViewBeanInputCollection = new Vector();
				while (iterator.hasNext()) {
					org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
						 commons.beanutils.LazyDynaBean) iterator.next();					
/*
					String expirationDate = com.tcmis.common.util.StringHandler.
						 emptyIfNull(lazyBean.get("expirationDate"));
					if ("Indefinite".equalsIgnoreCase(expirationDate)) {
						expirationDate = "01/01/3000";
					}

					if (expirationDate.length() > 0 && expirationDate.length() == 10) {
						lazyBean.set("expirationDate", expirationDate);
					}
					
*/                  
					
					ReceivingViewBean receivingViewBean = new ReceivingViewBean();
					BeanHandler.copyAttributes(lazyBean, receivingViewBean, getTcmISLocale(request));

					receivingViewBeanInputCollection.add(receivingViewBean);
				}
				receivingViewBeanInputCollection = process.copyAttributes(inputBean, receivingViewBeanInputCollection, savedBeanVector);

				if ((inputBean.getDuplicateLine() != null && inputBean.getDuplicateLine().length() > 0) || (inputBean.getDuplicateKitLine() != null && inputBean.getDuplicateKitLine().length() > 0) || (inputBean.getDuplicatePkgLine() != null && inputBean.getDuplicatePkgLine().length() > 0)) {
					request.setAttribute("duplicateLine", "Yes");
				}

				//now I can pass the collection of true data beans to the process
				Collection c = new Vector();
				c = process.createRelationalObject(receivingViewBeanInputCollection);

				HashMap receivedReceiptResult = new HashMap();
				receivedReceiptResult = process.receiveSelected(inputBean, c, personnelId);

				Collection finalReceivedReceiptsCollection = (Collection) receivedReceiptResult.get("RECEIVEDRECEIPTSCOLLECTION");
				String errorMessage = (String) receivedReceiptResult.get("ERRORMESSAGE");
				String receivedReceipts = (String) receivedReceiptResult.get("RECEIVEDRECEIPTS");
				Collection finalNewBeanCollection = process.flattenRelationship(inputBean, receivingViewBeanInputCollection, finalReceivedReceiptsCollection);
				//log.info("Final collectionSize here " +finalReceivedReceiptsCollection.size() + " newBeanVector  "+receivingViewBeanInputCollection.size()+"  finalNewBeanVector "+finalNewBeanCollection.size()+"");

				request.setAttribute("errorMessage", errorMessage);
				request.setAttribute("receivedReceipts", receivedReceipts);
				this.setSessionObject(request, finalNewBeanCollection, "receivingViewBeanCollection");
				request.removeAttribute("receivingViewRelationBeanCollection");
				request.setAttribute("receivingViewRelationBeanCollection", finalReceivedReceiptsCollection);

				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					return (mapping.findForward("showchemical"));
				} else {
					return (mapping.findForward("shownonchemical"));
				}
			} else
			if (inputBean.getUserAction() != null && inputBean.getUserAction().equalsIgnoreCase("createExcel")) {
				ResourceLibrary resource = new ResourceLibrary("report");
				ReceivingProcess process = new ReceivingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				this.saveTcmISToken(request);

				Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
				Collection c = new Vector();
				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					c = process.getChemicalResult(inputBean, false, personnelBean);
				} else {
					c = process.getNonChemicalResult(inputBean, personnelBean);
				}                
				this.setExcel(response,"Receiving QC");
				process.writeExcelFile(inputBean, c, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				
				return noForward;
			} else
			if (inputBean.getUserAction() != null && inputBean.getUserAction().equalsIgnoreCase("checkValidReceiptId")) {
				ReceivingProcess process = new ReceivingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				String itemId = request.getParameter("itemId");
				String transferReceiptId = request.getParameter("transferReceiptId");
				String originalReceiptId = request.getParameter("originalReceiptId");
				String rowNumber = request.getParameter("rowNumber");
				Vector<IsValidTransferReceiptBean> results = (Vector) process.isValidTransferRecceipt(originalReceiptId,transferReceiptId);
				
				request.setAttribute("isValidReceiptId", results.get(0).getValidReceipt());
				request.setAttribute("inventoryGroup", results.get(0).getInventoryGroup());
				request.setAttribute("mfgLot", results.get(0).getMfgLot());
				request.setAttribute("itemId", itemId);
				request.setAttribute("rowNumber", rowNumber);
				return  mapping.findForward("checkjsonvalidreceiptid");
			}
			this.setSessionObject(request, null, "receivingViewBeanCollection");
			return mapping.findForward("showinput");
		} else {
			this.setSessionObject(request, null, "receivingViewBeanCollection");
			return mapping.findForward("showinput");
		}
	}
}