package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReconciliationInputBean;
import com.tcmis.internal.hub.beans.ReconciliationViewBean;
import com.tcmis.internal.hub.process.BinLabelsProcess;
import com.tcmis.internal.hub.process.BinsToScanProcess;
import com.tcmis.internal.hub.process.ReconciliationProcess;

/******************************************************************************
 * Controller for reconciliation
 * @version 1.0
 ******************************************************************************/
public class ReconciliationAction
extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request,true)) {
			request.setAttribute("requestedPage", "reconciliationmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

        /*TODO needed to comment this out for goleta rio*/
        /*if (!personnelBean.getPermissionBean().hasUserPagePermission("reconciliation"))
		{
			return (mapping.findForward("nopermissions"));
		}*/

		ReconciliationInputBean inputBean = new ReconciliationInputBean();
		ReconciliationProcess process = new ReconciliationProcess(this.getDbUser(request),this.getTcmISLocale(request));
				
		BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
		String action =  (String)( (DynaBean) form).get("uAction");	
		String result = "OK";

		Date date = new Date();
		if(!StringHandler.isBlankString(inputBean.getCountDateString())) {
			long ll = Long.parseLong(inputBean.getCountDateString());
			date.setTime(ll);
			inputBean.setCountDate(date);
		}
		 inputBean.setLotStatus(request.getParameterValues("lotStatus"));
		if ( "search".equals(action) ) {
			request.setAttribute("beanCollection", process.getData(personnelBean,inputBean));
			saveTcmISToken(request);	
		}
		else if("getRoom".equalsIgnoreCase(action))
		{
			BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
			request.setAttribute("countDateColl", binLabelsProcess.getRoom(inputBean.getHub(), inputBean.getBinTo()));
			return mapping.findForward("getjsonobj");
		}
		else if ( "update".equals(action) ) {
			if ((!personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation",null,null,null)) &&
					(!personnelBean.getPermissionBean().hasInventoryGroupPermission("inventoryReconciliation",null,null,null)) )
			{
				request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
				request.setAttribute("beanCollection", process.getData(personnelBean,inputBean));
				return (mapping.findForward("success"));
			}   

			checkToken(request);
			ReconciliationViewBean bean = new ReconciliationViewBean();
			Collection <ReconciliationViewBean> beans = BeanHandler.getBeans((DynaBean) form, "ReconciliationBean", bean,getTcmISLocale(request)); 
			Collection errorMsgs = process.update(personnelBean, inputBean, beans);
			request.setAttribute("beanCollection", process.getData(personnelBean,inputBean));
			request.setAttribute("tcmISErrors", errorMsgs);  
			//saveTcmISToken(request);	
		}
		else if ( "approve".equals(action) ) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation",null,inputBean.getHubName(),null))
			{
				request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
				request.setAttribute("beanCollection", process.getData(personnelBean,inputBean));
				return (mapping.findForward("success"));
			}   

			checkToken(request); 
			
			ReconciliationViewBean bean = new ReconciliationViewBean();
			Collection <ReconciliationViewBean> beans = BeanHandler.getBeans((DynaBean) form, "ReconciliationBean", bean,getTcmISLocale(request)); 
			Collection errorMsgs = process.update(personnelBean, inputBean, beans);
			request.setAttribute("tcmISErrors", errorMsgs);  
						
			Vector errorMsgs1 = process.approveRecon(personnelId, inputBean);
			request.setAttribute("beanCollection", process.getData(personnelBean,inputBean));
			if(!errorMsgs1.firstElement().toString().equalsIgnoreCase("ok"))
				request.setAttribute("tcmISErrors", errorMsgs1);       	
		}
		else if ( "createExcel".equals(action) ) {  	
			this.setExcel(response,"ReconciliationExcel");
			
			process.getReconciliationExcel(personnelBean,inputBean, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());

			return noForward;
		}
		else if ( "getCountDateDropdown".equals(action)) {
			request.setAttribute("countDateColl", process.getCountDateDropDown(inputBean));
			request.setAttribute("deletedCountId", "");
			return (mapping.findForward("getjsonobj"));		
		}
		else if ( "showAddCountDate".equals(action)) {
			if ((!personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation",null,inputBean.getHubName(),null)) &&
				(!personnelBean.getPermissionBean().hasInventoryGroupPermission("inventoryReconciliation",null,inputBean.getHubName(),null)) )
			{
				request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));	
				request.setAttribute("fromReconCountDate", "Y");  
				return (mapping.findForward("nopermissions"));
			} 
			else
			{
				
				
				return (mapping.findForward("showdatecount"));
			}
						
		}	
		else if ( "insertCountDate".equals(action)) {

			if ((!personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation",null,null,null)) &&
					(!personnelBean.getPermissionBean().hasInventoryGroupPermission("inventoryReconciliation",null,null,null)) )
			{
				request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
				request.setAttribute("fromReconCountDate", "Y");  
				return (mapping.findForward("nopermissions"));
			} 


			Object[] resultsArr =	process.startNewCount(inputBean,personnelBean.getPersonnelId());

			result = resultsArr[0].toString();
			if("OK".equalsIgnoreCase(result))
			{	
				request.setAttribute("countDateColl", process.getCountDateDropDown(inputBean));

				request.setAttribute("closeAddDateCountWin", "Y");
			}
			else
			{	
				request.setAttribute("tcmISErrors", resultsArr[0].toString());

				request.setAttribute("closeAddDateCountWin", "N");
			}	
			return (mapping.findForward("showdatecount"));			
		}	
		else if ( "setCountDateClose".equals(action)) {

			if ((!personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation",null,null,null)) &&
					(!personnelBean.getPermissionBean().hasInventoryGroupPermission("inventoryReconciliation",null,null,null)) )
			{
				request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
				request.setAttribute("fromReconCountDate", "Y");
				request.setAttribute("deletedCountId", "");
				return (mapping.findForward("getjsonobj"));
			} 


			result = process.setCountDateClose(inputBean);
			if("OK".equalsIgnoreCase(result)) {
				request.setAttribute("deletedCountId", inputBean.getCountId());
				request.setAttribute("countDateColl", process.getCountDateDropDown(inputBean));
			}
			else
				request.setAttribute("tcmISError", result);
			return (mapping.findForward("getjsonobj"));		
		}	
		else {
            BinsToScanProcess binProcess = new BinsToScanProcess(this.getDbUser(request));
            Vector<HubInventoryGroupOvBean> hubInventoryGroupOvBeanCollection = (Vector)((PersonnelBean) this.getSessionObject(request, "personnelBean")).getHubInventoryGroupOvBeanCollection();
            String hub = hubInventoryGroupOvBeanCollection.firstElement().getBranchPlant();
            inputBean.setHub(hub);
            request.setAttribute("countDateColl", process.getCountDateDropDown(inputBean));
		    request.setAttribute("hubRoomOvBeanCollection", binProcess.getDropDownData(hubInventoryGroupOvBeanCollection));		
		}

		return (mapping.findForward("success"));
	}
}
