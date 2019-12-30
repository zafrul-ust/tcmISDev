package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.InventoryAdjustmentsInputBean;
import com.tcmis.internal.hub.beans.PendingInventoryAdjustmentBean;
import com.tcmis.internal.hub.process.InventoryAdjustmentsProcess;

/******************************************************************************
 * Controller for Inventory Adjustments
 * @version 1.0
 ******************************************************************************/
public class InventoryAdjustmentsAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "inventoryadjustmentsmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
        if (!personnelBean.getPermissionBean().hasUserPagePermission("inventoryAdjustments"))
        {
            return (mapping.findForward("nopermissions"));
        }
       		
		String forward = "success";      

		InventoryAdjustmentsInputBean inputBean = new InventoryAdjustmentsInputBean();
		
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));

		InventoryAdjustmentsProcess process = new InventoryAdjustmentsProcess(this.getDbUser(request),getTcmISLocaleString(request));
			
		// Search
		if (null!=inputBean.getAction() &&	"search".equals(inputBean.getAction()))  {
			request.setAttribute("pendingInventoryCollBean", process.getSearchResult(inputBean,personnelBean));
			request.setAttribute("sumInventoryCollBean", process.getSumResult(inputBean,personnelBean));
			this.saveTcmISToken(request);
			return (mapping.findForward(forward));
		}
		//  Create Excel
		else if (null!=inputBean.getAction() && "createExcel".equals(inputBean.getAction())) {
			this.setExcel(response, "Inventory_Adjustments_List");
			process.getExcelReport(inputBean,personnelBean,
					(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
        //  Update 
        else if (null!=inputBean.getAction() && "approve".equals(inputBean.getAction())) {
            if (!personnelBean.getPermissionBean().hasOpsEntityPermission("inventoryAdjustment",inputBean.getOpsEntityId(),null))
            {
                request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
                request.setAttribute("pendingInventoryCollBean", process.getSearchResult(inputBean,personnelBean));
                request.setAttribute("sumInventoryCollBean", process.getSumResult(inputBean,personnelBean));
                return (mapping.findForward(forward));
            }

            checkToken(request);
            PendingInventoryAdjustmentBean bean = new PendingInventoryAdjustmentBean();
            Collection<PendingInventoryAdjustmentBean> beans = BeanHandler.getGridBeans((DynaBean) form, "pendingInventoryViewBean", bean,getTcmISLocale(request),"approve");

            Collection errorMsgs =  process.update(beans,personnelBean);
            request.setAttribute("pendingInventoryCollBean", process.getSearchResult(inputBean,personnelBean));
            request.setAttribute("sumInventoryCollBean", process.getSumResult(inputBean,personnelBean));
            request.setAttribute("tcmISErrors", errorMsgs);    
        } 
		// Reject 
        else if (null!=inputBean.getAction() && "reject".equals(inputBean.getAction())) {
            if (!personnelBean.getPermissionBean().hasOpsEntityPermission("inventoryAdjustment",inputBean.getOpsEntityId(),null))
            {
                request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
                request.setAttribute("pendingInventoryCollBean", process.getSearchResult(inputBean,personnelBean));
                request.setAttribute("sumInventoryCollBean", process.getSumResult(inputBean,personnelBean));
                return (mapping.findForward(forward));
            }

            checkToken(request);
           
            PendingInventoryAdjustmentBean bean = new PendingInventoryAdjustmentBean();
            Collection<PendingInventoryAdjustmentBean> beans = BeanHandler.getGridBeans((DynaBean) form, "pendingInventoryViewBean", bean,getTcmISLocale(request),"approve");
            
            Collection errorMsgs =  process.reject(beans,personnelBean);
            request.setAttribute("pendingInventoryCollBean", process.getSearchResult(inputBean,personnelBean));
            request.setAttribute("sumInventoryCollBean", process.getSumResult(inputBean,personnelBean));
            request.setAttribute("tcmISErrors", errorMsgs);    
        }
	else{
		this.saveTcmISToken(request);		
	}

	return (mapping.findForward(forward));
}
}
