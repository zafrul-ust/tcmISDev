package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.BoxLabelBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.PicklistReprintViewBean;
import com.tcmis.internal.hub.process.PicklistPickingProcess;
import com.tcmis.internal.print.process.UnitExtLabelProcess;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for PicklistReprint
 * @version 1.0
 ******************************************************************************/
public class PicklistReprintAction extends TcmISBaseAction {
	
	private static ResourceLibrary reportProperties	= new ResourceLibrary("report");

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
			throws BaseException, Exception {

		String forward = "success";
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "picklistreprintmain");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		String userAction = null;
		if (form == null) {
			return (mapping.findForward("success"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		PicklistPickingProcess process = new PicklistPickingProcess(this.getDbUser(request));

		String fromPickingPicklist = request.getParameter("fromPickingPicklist");
		request.setAttribute("fromPickingPicklist",fromPickingPicklist);
		
		String fromCarrierPicking = request.getParameter("fromCarrierPicking");
		request.setAttribute("fromCarrierPicking",fromCarrierPicking);
		
		String picklistIdParam = request.getParameter("picklistId");
		//      String sortBy = request.getParameter("sortBy");
		String action = request.getParameter("action");
		PicklistReprintViewBean bean = new PicklistReprintViewBean();
		BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
		if (bean.getPicklistId()!=null && action.equalsIgnoreCase("search")) {
			Collection picklistPrint = process.getPicklistReprint(bean);
			
			if("Y".equals(fromCarrierPicking.toUpperCase()))
				//picklistPrint = process.attachSerialNumbers(picklistPrint);
				process.attachSerialNumbers(picklistPrint);
			
			request.setAttribute("picklistPrintColl", picklistPrint);
			/*This is to release the page quickly, can be removed after moving to new code*/
			if (picklistPrint != null && picklistPrint.size() > 0) {
				this.setSessionObject(request, process.bolPrintData(picklistPrint),"PRINTDATA");
				this.setSessionObject(request, bean.getPicklistId(),"PICKLIST_ID");           
			}
			request.setAttribute("picklistId", bean.getPicklistId());
		}
		else if ("updateSerialNumbers".equals(action)) {
			Collection<BoxLabelBean> boxLabels = new Vector();
			Collection<PicklistReprintViewBean> picklistReprintViewBeans = BeanHandler.getBeans((DynaBean)form,"picklistBean",new PicklistReprintViewBean(),this.getTcmISLocale(request), "ok");
			
			if (picklistReprintViewBeans.size() > 0) {	
				// get all box label fields for the issue(s) selected for update
				Iterator itr = picklistReprintViewBeans.iterator();
				while(itr.hasNext()) {
					PicklistReprintViewBean picklistReprintViewBean = (PicklistReprintViewBean)itr.next();
					Collection<BoxLabelBean> boxes = BeanHandler.getBeans((DynaBean)form, picklistReprintViewBean.getPrNumber()+"-"+picklistReprintViewBean.getLineItem()+"-"
																			+picklistReprintViewBean.getBin()+"boxLabelBean",new BoxLabelBean(),this.getTcmISLocale(request));
					
					boxLabels.addAll(boxes);
				}			
				
				process.updateSerialNumbers(boxLabels);				
			}
		
			Collection picklistPrint = process.getPicklistReprint(bean);
			process.attachSerialNumbers(picklistPrint);
			request.setAttribute("picklistPrintColl", picklistPrint);
			
			if (picklistPrint != null && picklistPrint.size() > 0) {
				this.setSessionObject(request, process.bolPrintData(picklistPrint),"PRINTDATA");
				this.setSessionObject(request, bean.getPicklistId(),"PICKLIST_ID");           
			}
			request.setAttribute("picklistId", bean.getPicklistId());
		}
		else if ("createExcel".equals(action))   {
			this.setExcel(response, "Regenerated_PickList");
			process.getReprintExcelReport(fromPickingPicklist, bean,(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else if ("createConsPLExcel".equals(action)) {
			this.setExcel(response, "Consolidated_PickList");
			process.getConsPicklistExcelReport(picklistIdParam, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("printUnitExtLabels".equals(action)) {
			@SuppressWarnings("unchecked")
			Collection<LabelInputBean> labelInputs = BeanHandler.getBeans((DynaBean) form, "picklistBean", new LabelInputBean(), "ok");
			HashMap<String,LabelInputBean> processedPallets = new HashMap<String,LabelInputBean>();
			StringBuilder printPalletSerialNumbers = new StringBuilder();
			
			UnitExtLabelProcess labelProcess = new UnitExtLabelProcess(this.getDbUser(request));
			
      	    for (LabelInputBean unitExtBean : labelInputs) {
				labelProcess.completeInput(unitExtBean, personnelBean);
				
				// only print labels if this pallet hasn't already been printed
				if(!processedPallets.containsKey(unitExtBean.getPalletId())) {
					labelProcess.buildUnitExtLabels(unitExtBean);					

					processedPallets.put(unitExtBean.getPalletId(), unitExtBean);
					
					if(unitExtBean.isPrintBoxLabel() && unitExtBean.getNumBoxLabels() > 5) {
						if(printPalletSerialNumbers.length() > 0)
							printPalletSerialNumbers.append(",");
						printPalletSerialNumbers.append(unitExtBean.getPalletId());
					}
				}
      	    }

      	    if(printPalletSerialNumbers.length() > 0) {
      	    	String reportRequest = reportProperties.getString("report.hosturl") + "HaasReports/report/printserialnumbers.do?palletIds=" + printPalletSerialNumbers.toString();   	    
      	    	response.sendRedirect(response.encodeRedirectURL(reportRequest));
      	    }
      	  
      	    return noForward;
		}

		else if (((DynaBean) form).get("action") != null) {
			Collection picklistPrintColl = new Vector();
			DynaBean dynaBean = (DynaBean) form;
			List picklistBeans = (List) dynaBean.get("picklistBean");
			if (picklistBeans != null) {
				Iterator iterator = picklistBeans.iterator();
				while (iterator.hasNext()) {
					PicklistReprintViewBean picklistBean = new PicklistReprintViewBean();
					LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
					BeanHandler.copyAttributes(lazyBean, picklistBean);
					if (picklistBean.getOk()!=null) {
						picklistBean.setPicklistId(new BigDecimal(picklistIdParam));
						picklistPrintColl.add(picklistBean);
					}
				}
			}
			this.setSessionObject(request, process.bolPrintData(picklistPrintColl),"PRINTDATA");
			this.setSessionObject(request, picklistIdParam,"PICKLIST_ID");
			request.setAttribute("action", action);
			request.setAttribute("source", "picking");
			forward = "printbol";
		}
		return mapping.findForward(forward);
	}
}
