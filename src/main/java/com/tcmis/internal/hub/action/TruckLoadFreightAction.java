package com.tcmis.internal.hub.action;

import javax.servlet.http.*;
import java.util.Vector;
import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.process.TruckLoadFreightProcess;
import com.tcmis.internal.hub.beans.FreightTlTrackingViewBean;

import java.util.Collection;

/******************************************************************************
 * Controller for truckload freight
 * @version 1.0
     ******************************************************************************/
public class TruckLoadFreightAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "truckloadfreightmain");
		request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    if (!personnelBean.getPermissionBean().hasUserPagePermission("truckloadFreight"))
    {
      return (mapping.findForward("nopermissions"));
    }

	  if (form == null) return (mapping.findForward("success"));

		String action = (String) ((DynaBean) form).get("uAction");

	    
	    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getTcmISLocale(request));
	    
		TruckLoadFreightProcess process = new TruckLoadFreightProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//copy date from dyna form to the data form
	 	FreightTlTrackingViewBean bean = new FreightTlTrackingViewBean();
	 	BeanHandler.copyAttributes(form, bean);
		if ("search".equalsIgnoreCase(action)) {
			//load data
			request.setAttribute("truckLoadFreightColl",process.getSearchData(bean,personnelBean));
		}else if ("update".equalsIgnoreCase(action)) {
			Collection<FreightTlTrackingViewBean> beans = BeanHandler.getBeans((DynaBean)form,"freightTlTrackingViewBean",new FreightTlTrackingViewBean(),this.getTcmISLocale(request));
			String errorMsg = process.updateData(personnelBean,bean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			//reload data
			request.setAttribute("truckLoadFreightColl",process.getSearchData(bean,personnelBean));
		}else if ("cancel".equalsIgnoreCase(action)) {
			Collection<FreightTlTrackingViewBean> beans = BeanHandler.getBeans((DynaBean)form,"freightTlTrackingViewBean",new FreightTlTrackingViewBean(),this.getTcmISLocale(request));
			Vector errorMsgs = process.cancelData(personnelBean,bean,beans);
			request.setAttribute("tcmISErrors", errorMsgs);
			//reload data
			request.setAttribute("truckLoadFreightColl",process.getSearchData(bean,personnelBean));
		} else if ("createExcel".equalsIgnoreCase(action)) {
         this.setExcel(response,"TruckLoadFreight");
         process.getExcelReport(bean,personnelBean,personnelBean.getLocale()).write(response.getOutputStream());
	      return noForward;
		}else {
			request.setAttribute("dropdownColl",process.createDropdownRelationalObject(process.getDropdownData(personnelBean)));
			request.setAttribute("hasEditPermission",process.hasEditPermission(personnelBean));
		}


	 return (mapping.findForward("success"));
  }
}
