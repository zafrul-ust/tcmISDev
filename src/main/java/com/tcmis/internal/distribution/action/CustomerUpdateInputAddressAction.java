package com.tcmis.internal.distribution.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.distribution.beans.CustomerUpdateInputAddressBean;
import com.tcmis.internal.distribution.process.CustomerUpdateInputAddressProcess;

/******************************************************************************
 * Controller for customer requests
 * @version 1.0
     ******************************************************************************/
public class CustomerUpdateInputAddressAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "inputaddress");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    // Get the user
	PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
    if (!user.getPermissionBean().hasOpsEntityPermission("editShipto", null, null)) {
			return (mapping.findForward("nopermissions"));
    }
     
    if(request.getParameter("uAction") != null && request.getParameter("uAction").equalsIgnoreCase("search"))
    {
        CustomerUpdateInputAddressProcess customerUpdateInputAddressProcess = new CustomerUpdateInputAddressProcess(this.getDbUser(request));
        VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
        Collection s =  vvDataProcess.getNormalizedCountryState();
        request.setAttribute("vvCountryBeanCollection", s);
    	Collection c = customerUpdateInputAddressProcess.getAddressInfo(request.getParameter("billToCompanyId"), request.getParameter("locationId"));
    	request.setAttribute("customerUpdateInputAddressBeanCollection", c) ;
     	return mapping.findForward("success");
    }
    else
    {
         CustomerUpdateInputAddressBean customerUpdateInputAddressBean = new CustomerUpdateInputAddressBean(form);
    	 CustomerUpdateInputAddressProcess customerUpdateInputAddressProcess = new CustomerUpdateInputAddressProcess(this.getDbUser(request));
    	 customerUpdateInputAddressProcess.updateAddress(customerUpdateInputAddressBean);
    	 	return noForward;
    }

    
  
  }
}