package com.tcmis.internal.hub.action;

import java.math.BigDecimal;

import java.util.Collection;

import java.util.Vector;
import java.util.ArrayList;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;


import org.apache.struts.action.*;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserOpsEntityHubIgOvBean;

import com.tcmis.common.admin.beans.PermissionBean;

import com.tcmis.common.exceptions.BaseException;

import com.tcmis.common.framework.TcmISBaseAction;

import com.tcmis.common.util.BeanHandler;

import com.tcmis.internal.hub.beans.PickingInputBean;

import com.tcmis.internal.hub.beans.PicklistViewBean;

import com.tcmis.internal.hub.process.AllocationAnalysisProcess;

import com.tcmis.internal.hub.process.PickingQcProcess;

/**
 * ***************************************************************************
 * <p/>
 * Controller for picking qc
 *
 * @version 1.0
 *          <p/>
 *          ****************************************************************************
 */

public class PickingQcAction

		extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping,
																		 ActionForm form,
																		 HttpServletRequest request,
																		 HttpServletResponse response) throws	BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "pickingqcmain");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = ((PersonnelBean) this.getSessionObject(request, "personnelBean"));
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		if (!permissionBean.hasFacilityPermission("Picking QC", null, null)) {
			return mapping.findForward("nopermissions");
		}
		
	 //if form is not null we have to perform some action
		//copy date from dyna form to the data form
		PickingInputBean bean = new PickingInputBean();
		if(form != null)
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
		//If the search button was pressed the getSubmitSearch() value will be not null
		if ( bean.getUserAction() != null &&  bean.getUserAction().equals("search") ) {
			//log.debug("here in search");
			PickingQcProcess process = new PickingQcProcess(this.getDbUser(request),getTcmISLocaleString(request));
			request.setAttribute("picklistColl", process.getNormalizedPicklistCollection(bean));
			this.saveTcmISToken(request);
		}else if ( bean.getUserAction() != null &&  bean.getUserAction().equals("createExcel") ) {
			PickingQcProcess process = new PickingQcProcess(this.getDbUser(request),getTcmISLocaleString(request));
			Vector v = (Vector) process.getPickList(bean);
			this.setExcel(response,"Picking QC");
			process.createExcelFile(v, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		} else if  ( bean.getUserAction() != null &&  bean.getUserAction().equals("confirm")) {
			//log.debug("here in cofirm");
			if (!this.isTcmISTokenValid(request, true)) {
				BaseException be = new BaseException("Duplicate form submission");
				be.setMessageKey("error.submit.duplicate");
				throw be;
			}
			this.saveTcmISToken(request);
			PickingQcProcess process = new PickingQcProcess(this.getDbUser(request),getTcmISLocaleString(request));
			/*DynaBean dynaForm = (DynaBean) form;
			List returnBeans = (List) dynaForm.get("topBean");
			Iterator iterator = returnBeans.iterator();
			Collection returnBeanInputCollection = new Vector();
			while (iterator.hasNext()) {
				LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
				PicklistViewBean picklistViewBean = new PicklistViewBean();
				BeanHandler.copyAttributes(lazyBean, picklistViewBean, getTcmISLocale(request));
				returnBeanInputCollection.add(picklistViewBean);
			}
			process.enterPick(returnBeanInputCollection, personnelId);
			process.lineClose(returnBeanInputCollection);
			request.setAttribute("picklistColl", process.getNormalizedPicklistCollection(bean));*/
			Collection<PicklistViewBean> returnBeans = BeanHandler.getGridBeans((DynaBean) form, "PicklistViewBean", new PicklistViewBean(), getTcmISLocale(), "ok");
			process.enterPick(returnBeans, personnelId);
			//process.lineItemAllocate(returnBeans);
			request.setAttribute("picklistColl", process.getNormalizedPicklistCollection(bean));
		} else if (bean.getUserAction() != null && 
				((bean.getUserAction().equalsIgnoreCase("printBolLong")
                || bean.getUserAction().equalsIgnoreCase("printBolShort")
                || bean.getUserAction().equalsIgnoreCase("printBoxLabels"))
                || bean.getUserAction().equalsIgnoreCase("printExitLabels")
                || bean.getUserAction().equalsIgnoreCase("rePrintLabels")
                ||bean.getUserAction().equalsIgnoreCase("generatedelvtkt")
				)) 
			{
		  PickingQcProcess process = new PickingQcProcess(this.getDbUser(request),getTcmISLocaleString(request));
          Collection<PicklistViewBean> printColl = BeanHandler.getGridBeans((DynaBean) form, "PicklistViewBean", new PicklistViewBean(), getTcmISLocale(), "ok");
		  //Collection printColl =	BeanHandler.getBeans((DynaBean) form, "PicklistViewBean", new PicklistViewBean(), getTcmISLocale(request));
          this.setSessionObject(request, "","PICKLIST_ID");
          //this.setSessionObject(request, picklistIdParam,"PICKLIST_ID");
          request.setAttribute("action", bean.getUserAction());
          request.setAttribute("source", "pickingQC");
          BigDecimal mroCount =((PicklistViewBean)(printColl.iterator().next())).getMaterialRequestOriginCount();
          if(bean.getUserAction().equalsIgnoreCase("printExitLabels"))
          {
        	  request.setAttribute("materilReqOriginCount", mroCount);
        	  this.setSessionObject(request, process.exitLabelPrintData(printColl),"PICKQC_DATA");
        
          }
          else if(bean.getUserAction().equalsIgnoreCase("rePrintLabels"))
          {
        	  request.setAttribute("materilReqOriginCount", mroCount);  
          	  this.setSessionObject(request, process.rePrintData(printColl),"PICKQC_DATA");
          }
          else if(bean.getUserAction().equalsIgnoreCase("generatedelvtkt"))
          {
        	  request.setAttribute("materilReqOriginCount", mroCount);  
          	  this.setSessionObject(request, process.deliverTicket(printColl),"PICKQC_DATA");
          }
          else
        	 this.setSessionObject(request, process.bolPrintData(printColl),"PRINTDATA");
          return (mapping.findForward("printbol"));
		} 

		else {
			//log.debug("here in initial");
			//populate drop downs
			PickingQcProcess process = new PickingQcProcess(this.getDbUser(request),getTcmISLocaleString(request));
			String hub = null;
			if(request.getParameter("uAction") != null && request.getParameter("uAction").equalsIgnoreCase("popPickList"))
			{
				hub = request.getParameter("hub");
				request.setAttribute("hubPicklistCollection", process.populatePickList(hub));
				return mapping.findForward("poppicklist");
			}
			hub = ((UserOpsEntityHubIgOvBean)(personnelBean.getOpsHubIgOvBeanCollection().iterator().next())).getDefaultHub();
			if(hub == null)
				hub = ((HubInventoryGroupOvBean)(personnelBean.getHubInventoryGroupOvBeanCollection().iterator().next())).getHubName();
			request.setAttribute("hubPicklistCollection", process.populatePickList(hub));
			saveTcmISToken(request);
			AllocationAnalysisProcess aaProcess = new AllocationAnalysisProcess(this.getDbUser(request));
			request.setAttribute("hubInventoryGroupFacOvBeanCollection", aaProcess.getHubDropDownData(personnelId));
		}
		
		return (mapping.findForward("success"));
	}
}

