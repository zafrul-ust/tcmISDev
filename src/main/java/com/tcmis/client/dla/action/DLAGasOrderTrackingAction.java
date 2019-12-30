package com.tcmis.client.dla.action;

import com.tcmis.client.dla.process.DLAGasOrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.supplier.shipping.beans.SupplierShippingInputBean;
import com.tcmis.supplier.shipping.process.ShipmentSelectionProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Vector;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
 ******************************************************************************/
public class DLAGasOrderTrackingAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "dlagasordertrackingmain");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

    BigDecimal personnelId = new BigDecimal(((PersonnelBean) this.getSessionObject(request, "personnelBean")).getPersonnelId());
    
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("dlaGasOrderTracking"))
    {
      return (mapping.findForward("nopermissions"));
    }
    
    if (form == null) {
			return (mapping.findForward("success"));
    }
        
    String userAction = (String) ((DynaBean) form).get("userAction");
    DynaBean dynaBean = (DynaBean) form;
    SupplierShippingInputBean inputbean = new SupplierShippingInputBean();
    BeanHandler.copyAttributes(dynaBean, inputbean, getTcmISLocale(request));
    inputbean.setPersonnelId(personnelId);

    if ("search".equals(userAction)) {
        DLAGasOrderTrackingProcess process = new DLAGasOrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
        Object[] results = process.showResult(inputbean, personnelBean);
        request.setAttribute("DLAOrderViewBeanCollection", results[0]);
        request.setAttribute("rowCountPart", results[1]);
    }
    else if ("createExcel".equals(userAction)) {
        DLAGasOrderTrackingProcess process = new DLAGasOrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
        String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
        module = module.substring(1, module.length());

        Vector v = (Vector) process.getSearchResult(inputbean, personnelBean);
        this.setExcel(response,"DLAGasOrderTracking");
        process.getExcelReportNoSplit(v, (java.util.Locale)request.getSession().getAttribute("tcmISLocale"),module,personnelBean).write(response.getOutputStream());
        return noForward;
    }
    else if ("changeSupplier".equals(userAction)) {
        DLAGasOrderTrackingProcess process = new DLAGasOrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
        request.setAttribute("inventoryGroupColl", process.getFacilityInventoryGroup());
        return (mapping.findForward("changesupplier"));
    }
    else if ("changeSupplierDone".equals(userAction)) {
        DLAGasOrderTrackingProcess process = new DLAGasOrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
        String messageToUser = process.changeSupplier(request.getParameter("radianPO"),request.getParameter("inventoryGroup"));
        if(messageToUser!="")
        {
         request.setAttribute("refresh", "");
        }
        else
        {
         request.setAttribute("refresh", "refresh");
        }
        request.setAttribute("messageToUser", messageToUser);
        request.setAttribute("inventoryGroupColl", process.getFacilityInventoryGroup());
        return (mapping.findForward("changesupplier"));
    }
    else if ("cancelOrder".equals(userAction)) {
        return (mapping.findForward("cancelorder"));
    }
    else if ("cancelOrderDone".equals(userAction)) {
        DLAGasOrderTrackingProcess process = new DLAGasOrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
        request.setAttribute("messageToUser", process.cancelOrder(request.getParameter("docNumWithSuffix"),request.getParameter("comments")) );
        request.setAttribute("refresh", "refresh");
        return (mapping.findForward("cancelorder"));
    }
    else {
        ShipmentSelectionProcess process = new ShipmentSelectionProcess(this.getDbUser(request),getTcmISLocaleString(request));
        request.setAttribute("supplierLocationOvBeanCollection", process.getDLASearchDropDown(personnelId));
        request.setAttribute("dlaHubBeanCollection",process.getDlaHubs());
    }
    return (mapping.findForward("success"));
	}
}
