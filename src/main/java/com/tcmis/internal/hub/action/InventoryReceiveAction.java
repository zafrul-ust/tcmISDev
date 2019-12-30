package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.InventoryReceiveInputBean;
import com.tcmis.internal.hub.beans.CustomerInventoryToReceiveBean;
import com.tcmis.internal.hub.process.InventoryReceiveProcess;
import com.tcmis.internal.hub.process.InventoryGroupCarrierOvProcess;


/******************************************************************************
 * Controller for InventoryReceive
 * @version 1.0
 ******************************************************************************/
public class InventoryReceiveAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
     throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "inventoryreceivemain");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

       PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		String forward = "success";
		
		PermissionBean permissionBean = personnelBean.getPermissionBean();
	    if ( !permissionBean.hasUserPagePermission("custInvReceiving"))
	    {
	      return (mapping.findForward("nopermissions"));
	    }

	    String userAction = null;
		if (form == null
				|| (userAction = ((String) ((DynaBean) form).get("uAction"))) == null) {
			return (mapping.findForward("success"));
		}
		
		//if form is not null we have to perform some action
		if (userAction.equals("display")) {
			InventoryGroupCarrierOvProcess aaProcess = new InventoryGroupCarrierOvProcess(this.getDbUser(request),getTcmISLocaleString(request));
			request.setAttribute("InventoryGroupCarrierOvColl", aaProcess.getSearchResult(personnelId));
			request.setAttribute("invgrpColl", personnelBean.getHubInventoryGroupOvBeanCollection() );
			return (mapping.findForward(forward));
		}

		InventoryReceiveProcess process = new InventoryReceiveProcess(this.getDbUser(request),getTcmISLocaleString(request));
		InventoryReceiveInputBean bean = new InventoryReceiveInputBean();
        BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
        
		if (userAction.equals("search")) {
			
           this.saveTcmISToken(request);
           request.setAttribute("CustomerInventoryToReceiveBeanColl", process.getSearchData(bean,personnelBean));
		}
	    else if ( userAction.equals("createExcel")) {
			try {
		    	this.setExcel(response,"inventory_receive");
				process.getExcelReport(bean,personnelBean, response.getOutputStream(), personnelBean.getLocale());
			}
			catch (Exception ex) {
			 ex.printStackTrace();
			 return mapping.findForward("genericerrorpage");
			}
			return noForward;
	    }
	    else if ( userAction.equals("update")) {
	    	
	    	checkToken(request);
	    	
	    	if (!permissionBean.hasInventoryGroupPermission("custInvReceiving", bean.getInventoryGroup(), null, personnelBean.getCompanyId()))
	        {
	              request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
	              request.setAttribute("CustomerInventoryToReceiveBeanColl", process.getSearchData(bean,personnelBean));
	              return (mapping.findForward("success"));
	        }
	    	
	    	DynaBean dynaForm = (DynaBean) form;
	   	    List beans = (List) dynaForm.get("inventoryToReceiveBean");
	   	    Iterator iterator = beans.iterator(); 
			 
			 int updateCount = 0;
			 Collection customerInvToReceiveBeanColl = new Vector();
			 while (iterator.hasNext()) {
				org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();
				CustomerInventoryToReceiveBean customerInventoryToReceiveBean = new CustomerInventoryToReceiveBean();
				BeanHandler.copyAttributes(lazyBean, customerInventoryToReceiveBean, getTcmISLocale(request));
				if (customerInventoryToReceiveBean.getOk().equalsIgnoreCase("true")) {
					updateCount++;
				 customerInvToReceiveBeanColl.add(customerInventoryToReceiveBean);
				}
			 }
			 if (updateCount > 0){
	         request.setAttribute("tcmISError", process.update(customerInvToReceiveBeanColl,personnelBean.getLocale(),personnelBean));
			 }
	         request.setAttribute("CustomerInventoryToReceiveBeanColl", process.getSearchData(bean,personnelBean));
	    }
	    else if ( userAction.equals("new")){
	    	if (!permissionBean.hasInventoryGroupPermission("custInvReceiving", bean.getInventoryGroup(), null, personnelBean.getCompanyId()))
		    {
		      return (mapping.findForward("nopermissions"));
		    }
	    	request.setAttribute("userIgCompanyOwnerBeanCollection", process.getCompanyDropDown(personnelId,bean.getInventoryGroup(),bean.getHub(),"custInvReceiving"));
	    	return mapping.findForward("shownewcitr");
	    }
	    else if ( userAction.equals("addnewitem")){
	    	CustomerInventoryToReceiveBean beans = new CustomerInventoryToReceiveBean();
	        BeanHandler.copyAttributes(form, beans, getTcmISLocale(request));
	        
	        String errorMsgs = process.addnewitem(beans,personnelBean);
			request.setAttribute("tcmISErrors", errorMsgs); 

			if(errorMsgs == "" )
			{
				request.setAttribute("closeNewCitrWin", "Y");
				
			}
			else
			{
				request.setAttribute("closeNewCitrWin", "N");				
			}
	        return mapping.findForward("shownewcitr"); 
	    }
	    

		return mapping.findForward(forward);
  }
}
