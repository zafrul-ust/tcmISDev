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
import com.tcmis.internal.hub.beans.VvLotStatusBean;
import com.tcmis.internal.hub.process.BinLabelsProcess;
import com.tcmis.internal.hub.process.NonChemicalReceivingQcProcess;
import com.tcmis.internal.hub.process.ReceivingQcProcess1;


/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ReceivingQcAction1 extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "receivingqcmain");
			request.setAttribute("requestedURLWithParameters",
					getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
			
		VvDataProcess vvDataProcess = new VvDataProcess(getDbUser(request), getTcmISLocale(request));
		Collection vvLotStatusBeanCollection = vvDataProcess.getActiveVvLotStatus(personnelBean);
		request.setAttribute("vvLotStatusBeanCollection", vvLotStatusBeanCollection);
		request.setAttribute("searchWhat", "PO");
		
		ReceivingInputBean inputBean = new ReceivingInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		ReceivingQcProcess1 receivingQcProcess = new ReceivingQcProcess1(getDbUser(request), getTcmISLocaleString(request));
		
		if (form == null) {
	        return (mapping.findForward("success"));
	    }

		if ( "loaddata".equalsIgnoreCase(inputBean.getUserAction()) ) {
			BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
			request.setAttribute("bins",
					binLabelsProcess.getReceivingOnlyBins(request.getParameter("itemId"),request.getParameter("hub")));
	        return (mapping.findForward("loaddata"));
	    }	

		if ("chemicals".equalsIgnoreCase(inputBean.getCategory()) && "search".equalsIgnoreCase(inputBean.getUserAction())) {
			
			Collection c = receivingQcProcess.getChemicalResult(inputBean, personnelBean,true);
			Collection relationalCollection = receivingQcProcess.createRelationalObject(c);
			String labelReceipts = receivingQcProcess.receiptsToLabelList(relationalCollection);
			com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
			//log.info("Here labelReceipts "+labelReceipts+"");
			request.setAttribute("labelReceipts", labelReceipts);
			this.setSessionObject(request, c, "receivingQcViewBeanCollection");
			request.setAttribute("receivingQcViewRelationBeanCollection", relationalCollection);

			return (mapping.findForward("showchemical"));
		}
		else if (!"chemicals".equalsIgnoreCase(inputBean.getCategory()) && "search".equalsIgnoreCase(inputBean.getUserAction()))
		{
			//get search result
			NonChemicalReceivingQcProcess nonChemicalReceivingQcProcess = new NonChemicalReceivingQcProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			Collection c = nonChemicalReceivingQcProcess.getNonChemicalResult(inputBean, hubInventoryGroupOvBeanCollection);
			this.setSessionObject(request, c, "receivingQcViewBeanCollection");
			request.setAttribute("receivingQcViewRelationBeanCollection", receivingQcProcess.createRelationalObject(c));
			request.setAttribute("labelReceipts", "");
			return (mapping.findForward("shownonchemical"));
		}
		else if ("confirm".equals(inputBean.getUserAction())) 
		{
			 DynaBean dynaForm = (DynaBean) form;
			 List beans = (List) dynaForm.get("ReceivingQcBean");
			 Iterator iterator = beans.iterator();
				
				Collection receivingQcViewBeanInputCollection = new Vector();
				while (iterator.hasNext()) {
					org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
							 commons.beanutils.LazyDynaBean) iterator.next();
					ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(lazyBean, receiptDescriptionViewBean, getTcmISLocale(request));
					
					if (receiptDescriptionViewBean.getQualityControlItem() != null && "Y".equalsIgnoreCase(receiptDescriptionViewBean.getQualityControlItem().trim())) {
						Iterator vvLotStatusIterator = vvLotStatusBeanCollection.iterator();
						while (vvLotStatusIterator.hasNext()) {
							VvLotStatusBean vvLotStatusBean = (VvLotStatusBean) vvLotStatusIterator.next();
							
							String lotStatus = vvLotStatusBean.getLotStatus();
							if (lotStatus.equalsIgnoreCase(receiptDescriptionViewBean.getLotStatus())) {
								String certified = vvLotStatusBean.getCertified();
								if ("Y".equalsIgnoreCase(certified)) {
									receiptDescriptionViewBean.setCertificationUpdate("Yes");
								}
							}
						}
					}
					
					receivingQcViewBeanInputCollection.add(receiptDescriptionViewBean);
				}
				Collection c = new Vector();
				c = receivingQcProcess.createRelationalObject(receivingQcViewBeanInputCollection);

				HashMap receivedQcReceiptResult = new HashMap();
				receivedQcReceiptResult = receivingQcProcess.receiveSelected(inputBean,c, personnelBean);

				Collection finalReceivedQcReceiptsCollection = (Collection) receivedQcReceiptResult.get("RECEIVEDQCCOLLECTION");
				String errorMessage = (String) receivedQcReceiptResult.get("ERRORMESSAGE");
				//Collection finalNewBeanCollection = receivingQcProcess.flattenRelationship(inputBean, beans, finalReceivedQcReceiptsCollection);
				//log.info("Final collectionSize here " +finalReceivedQcReceiptsCollection.size() + " newBeanVector  "+receivingQcViewBeanInputCollection.size()+"  finalNewBeanVector "+finalNewBeanCollection.size()+"");

				String labelReceipts = receivingQcProcess.receiptsToLabelList(finalReceivedQcReceiptsCollection);
				com.tcmis.common.util.StringHandler.replace(labelReceipts, ",", "%44");
				request.setAttribute("labelReceipts", labelReceipts);
				request.setAttribute("errorMessage", errorMessage);
				this.setSessionObject(request, finalReceivedQcReceiptsCollection, "receivingQcViewBeanCollection");
				request.setAttribute("receivingQcViewRelationBeanCollection", finalReceivedQcReceiptsCollection);
				if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
					return (mapping.findForward("showchemical"));
				} else {
					return (mapping.findForward("shownonchemical"));
				}
			} 
			else
			if (inputBean.getUserAction() != null && inputBean.getUserAction().equalsIgnoreCase("createExcel")) {
				ResourceLibrary resource = new ResourceLibrary("report");
				
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
				return noForward;
							
				
			}
			
		
		return mapping.findForward("success");
	}
}
		
		
	
