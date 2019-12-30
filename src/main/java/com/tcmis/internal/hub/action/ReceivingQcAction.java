package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.common.util.ResourceLibrary;

import java.io.File;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.NonChemicalReceivingQcProcess;
import com.tcmis.internal.hub.process.ReceivingQcProcess;
import com.tcmis.internal.hub.process.ShipmentHistoryProcess;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ReceivingQcAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "showreceivingqc");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) {
			//this.saveTcmISToken(request);
		}

		//populate drop downs
		//ShipmentHistoryProcess igdprocess = new ShipmentHistoryProcess(this.getDbUser(request));
		if (this.getSessionObject(request, "hubPrefViewOvBeanCollection") == null) {
			/*this.setSessionObject(request,
					  igdprocess.getDropDownData( ( (PersonnelBean)this.
	  getSessionObject(request, "personnelBean")).getPersonnelId()),
					  "hubPrefViewOvBeanCollection");*/

			VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request), this.getTcmISLocale(request));
			this.setSessionObject(request, vvDataProcess.getActiveVvLotStatus(), "vvLotStatusBeanCollection");

			request.setAttribute("searchWhat", "PO");
		}

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			ReceivingInputBean inputBean = new ReceivingInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			//log.debug("inputBean:" + inputBean.getSourceHub());

			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

			//check what button was pressed and determine where to go
			if ("searchTransferRequestId".equals(inputBean.getUserAction()) || (inputBean.getSubmitSearch() != null && inputBean.getSubmitSearch().trim().length() > 0)) {
				this.saveTcmISToken(request);
				//check if it's chemical or non-chemical
				ReceivingQcProcess process = new ReceivingQcProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				/*if (log.isDebugEnabled()) {
			  log.debug("category:" + inputBean.getCategory()+" Hub Name "+inputBean.getSourceHubName()+"");
		  }*/

				Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();

				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					//get search result
					Collection c = process.getChemicalResult(inputBean, personnelBean,true);
					//add result and pass it to the jsp page
					Collection relationalCollection = process.createRelationalObject(c);
					String labelReceipts = process.receiptsToLabelList(relationalCollection);
					com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
					//log.info("Here labelReceipts "+labelReceipts+"");
					request.setAttribute("labelReceipts", labelReceipts);

					this.setSessionObject(request, c, "receivingQcViewBeanCollection");
					request.setAttribute("receivingQcViewRelationBeanCollection", relationalCollection);

					return (mapping.findForward("showchemical"));
				} else {
					//get search result
					NonChemicalReceivingQcProcess nonChemicalReceivingQcProcess = new NonChemicalReceivingQcProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
					Collection c = nonChemicalReceivingQcProcess.getNonChemicalResult(inputBean, hubInventoryGroupOvBeanCollection);
					//add result and pass it to the jsp page
					this.setSessionObject(request, c, "receivingQcViewBeanCollection");
					request.setAttribute("labelReceipts", "");

					request.setAttribute("receivingQcViewRelationBeanCollection", process.createRelationalObject(c));
					return (mapping.findForward("shownonchemical"));
				}
			} else
			if ((inputBean.getSubmitReceive() != null && inputBean.getSubmitReceive().trim().length() > 0) || (inputBean.getSubmitPrint() != null && inputBean.getSubmitPrint().trim().length() > 0)) {
				if (!this.isTcmISTokenValid(request, true)) {
					BaseException be = new BaseException("Duplicate form submission");
					be.setMessageKey("error.submit.duplicate");
					throw be;
				}
				this.saveTcmISToken(request);

				Vector savedBeanVector = new Vector((Collection) this.getSessionObject(request, "receivingQcViewBeanCollection"));
				//Vector newBeanVector = new Vector();
				//Vector finalNewBeanVector = new Vector();
				Collection vvLotStatusBeanCollection = (Collection) this.getSessionObject(request, "vvLotStatusBeanCollection");
				//int kitSize = 0;

				// Cast form to DynaBean
				DynaBean dynaForm = (DynaBean) form;
				List receivingQcViewBeans = (List) dynaForm.get("receivingQcViewBean");
				Iterator iterator = receivingQcViewBeans.iterator();
				//int count = 0;
				ReceivingQcProcess process = new ReceivingQcProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				Collection receivingQcViewBeanInputCollection = new Vector();
				while (iterator.hasNext()) {
					org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
						 commons.beanutils.LazyDynaBean) iterator.next();
/*
					String expireDate = com.tcmis.common.util.StringHandler.
						 emptyIfNull(lazyBean.get("expireDate"));
					if ("Indefinite".equalsIgnoreCase(expireDate)) {
						expireDate = "01/01/3000";
					}

					if (expireDate.length() > 0 && expireDate.length() == 10) {
						lazyBean.set("expireDate", expireDate);
					}
*/
					ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(lazyBean, receiptDescriptionViewBean, getTcmISLocale(request));
//					receiptDescriptionViewBean = process.processIndefinite(receiptDescriptionViewBean);
					receivingQcViewBeanInputCollection.add(receiptDescriptionViewBean);
				}

				ReceivingQcProcess receivingQcProcess = new ReceivingQcProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				receivingQcViewBeanInputCollection = receivingQcProcess.copyAttributes(inputBean, receivingQcViewBeanInputCollection, savedBeanVector, vvLotStatusBeanCollection);

				//now I can pass the collection of true data beans to the process
				//ReceivingQcProcess process = new ReceivingQcProcess(this.getDbUser(request));
				Collection c = new Vector();
				c = receivingQcProcess.createRelationalObject(receivingQcViewBeanInputCollection);

				HashMap receivedQcReceiptResult = new HashMap();
				receivedQcReceiptResult = receivingQcProcess.receiveSelected(inputBean, c, personnelBean);

				Collection finalReceivedQcReceiptsCollection = (Collection) receivedQcReceiptResult.get("RECEIVEDQCCOLLECTION");
				String errorMessage = (String) receivedQcReceiptResult.get("ERRORMESSAGE");
				Collection finalNewBeanCollection = receivingQcProcess.flattenRelationship(inputBean, receivingQcViewBeanInputCollection, finalReceivedQcReceiptsCollection);
				//log.info("Final collectionSize here " +finalReceivedQcReceiptsCollection.size() + " newBeanVector  "+receivingQcViewBeanInputCollection.size()+"  finalNewBeanVector "+finalNewBeanCollection.size()+"");

				String labelReceipts = receivingQcProcess.receiptsToLabelList(finalNewBeanCollection);
				com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
				request.setAttribute("labelReceipts", labelReceipts);
				request.setAttribute("errorMessage", errorMessage);
				this.setSessionObject(request, finalNewBeanCollection, "receivingQcViewBeanCollection");
				request.removeAttribute("receivingQcViewRelationBeanCollection");
				request.setAttribute("receivingQcViewRelationBeanCollection", finalReceivedQcReceiptsCollection);

				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					return (mapping.findForward("showchemical"));
				} else {
					return (mapping.findForward("shownonchemical"));
				}
			} else
			if (inputBean.getUserAction() != null && inputBean.getUserAction().equalsIgnoreCase("createExcel")) {
				ResourceLibrary resource = new ResourceLibrary("report");
				ReceivingQcProcess receivingQcProcess = new ReceivingQcProcess(this.getDbUser(request), this.getTcmISLocaleString(request));

       Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
       Collection c = new Vector();
				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					c = receivingQcProcess.getChemicalResult(inputBean, personnelBean,true);
				} else {
					//get search result
					NonChemicalReceivingQcProcess nonChemicalReceivingQcProcess = new NonChemicalReceivingQcProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
					c = nonChemicalReceivingQcProcess.getNonChemicalResult(inputBean, hubInventoryGroupOvBeanCollection);
				}                
				this.setExcel(response,"Receiving QC");
				receivingQcProcess.writeExcelFile(inputBean, receivingQcProcess.createRelationalObject(c), (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			
/*
				File dir = new File(resource.getString("excel.report.serverpath"));
				File file = File.createTempFile("excel", ".xls", dir);
				receivingQcProcess.writeExcelFile(inputBean, receivingQcProcess.createRelationalObject(c), file.getAbsolutePath(),personnelBean.getLocale());
				this.setSessionObject(request, resource.getString("report.hosturl") + resource.getString("excel.report.urlpath") + file.getName(), "filePath");
*/
				/*request.setAttribute("doexcelpopup", "Yes");
				request.setAttribute("receivingQcViewRelationBeanCollection", receivingQcProcess.createRelationalObject(c));
				this.saveTcmISToken(request);
				
				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					return (mapping.findForward("showchemical"));
				} else {
					return (mapping.findForward("shownonchemical"));
				}*/
				return noForward;
			}
			this.setSessionObject(request, null, "receivingQcViewBeanCollection");
			return mapping.findForward("showinput");
		} else {
			this.setSessionObject(request, null, "receivingQcViewBeanCollection");
			return mapping.findForward("showinput");
		}
		
	}
}