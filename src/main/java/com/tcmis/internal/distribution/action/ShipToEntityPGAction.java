package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.*;
import com.tcmis.internal.distribution.process.CustomerEntityPGProcess;
import com.tcmis.internal.distribution.process.CustomerRequestProcess;
import com.tcmis.internal.distribution.process.NewShipToAddressProcess;

import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for supplier requests
 * @version 1.0
     ******************************************************************************/
public class ShipToEntityPGAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
	  if (!this.checkLoggedIn("shiptoentitypg"))
			return mapping.findForward("login");

   
    String uAction = (String)( (DynaBean) form).get("uAction");
    
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	CustomerRequestProcess sprocess = new CustomerRequestProcess(getDbUser(request),getLocale(request));
	//request.setAttribute("priceList", sprocess.getPriceListDropDown());
//	if (!personnelBean.getPermissionBean().hasUserPagePermission("newShipToAddress"))   {
//         return (mapping.findForward("nopermissions"));
//    }
//  CustomerEntPriceGrpViewBean    
	CustomerEntityPGProcess process = new CustomerEntityPGProcess(this);
  
    OpsEntityFacilityViewBean inputBean = new OpsEntityFacilityViewBean();
    BeanHandler.copyAttributes(form, inputBean);

    if ( "loaddata".equals(uAction) ) {
    	String inv = process.getInv(inputBean);
        request.setAttribute("inv", inv);  
    	String invName = process.getInvName(inv);
        request.setAttribute("invName", invName);  
    	String pg = process.getpg(inputBean);
        request.setAttribute("pg", pg);  
    	String pgName = process.getpgName(pg);
        request.setAttribute("pgName", pgName);  
        return mapping.findForward("invpgval");
    }
    else 
		if (uAction != null && "update".equals(uAction)) {
//			this.checkToken(request);
			String ErrorMsg = "";
//			if (personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", request.getParameter("inventoryGroup"), null, null)
//					|| personnelBean.getPermissionBean().hasInventoryGroupPermission("shipConfirm", request.getParameter("inventoryGroup"), null, null)) 
			{
				Collection<OpsEntityFacilityViewBean> beans = BeanHandler.getBeans((DynaBean) form, "shipColl", new OpsEntityFacilityViewBean(), this.getTcmISLocale());
				for (OpsEntityFacilityViewBean bean : beans) {
					bean.setCompanyId(inputBean.getCompanyId());
					bean.setFacilityId(inputBean.getFacilityId());
					if ("New".equals(bean.getChanged()))
						ErrorMsg += process.addShipToPG(bean,personnelId);
					else if ("Remove".equals(bean.getChanged()))
						ErrorMsg += process.removeShipToPG(bean);
					else
						ErrorMsg += process.updateShipToPG(bean,personnelId);
				}
			}
			request.setAttribute("tcmISError", ErrorMsg);
	}
    else {
//    	String customerId = request.getParameter("customerId");
//    	request.setAttribute("defaultPriceGroupColl",
//				process.getSearchResult("select distinct price_group_id from customer_details_view where customer_id = {0}",
//		    			new UserOpsEntityPgViewBean(),customerId));
    }
    
    	//VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
    	//Collection c = vvDataProcess.getNormalizedCountryState();
        //request.setAttribute("vvCountryBeanCollection",c);
	request.setAttribute("beanColl", process.getSearchResult("select * from ops_entity_facility_view where company_id = {0} and facility_id = {1}", inputBean, inputBean.getCompanyId(), inputBean.getFacilityId()));
	request.setAttribute("facilityColl", process.getSearchResult("select facility_name from customer.facility where company_id = {0} and facility_id = {1}", inputBean, inputBean.getCompanyId(), inputBean.getFacilityId()));
    
        request.setAttribute("fromCustomerDetail", request.getParameter("fromCustomerDetail"));  
		request.setAttribute("fromShipToAddress", request.getParameter("fromShipToAddress"));
		request.setAttribute("opsPgColl",
		    	process.getSearchResult("select * from user_ops_entity_pg_view where personnel_id = {0} and company_id = {1} order by ops_entity_id,price_group_name",
		    			new UserOpsEntityPgViewBean(),personnelBean.getPersonnelId(),personnelBean.getCompanyId()));
		
    return mapping.findForward("success");
  }
}