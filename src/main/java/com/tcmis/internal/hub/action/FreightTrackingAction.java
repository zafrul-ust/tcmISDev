package com.tcmis.internal.hub.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.process.FreightTrackingProcess;
import com.tcmis.internal.hub.beans.HubTrackingNumbersViewBean;

import java.util.Collection;

/******************************************************************************
 * Controller for freight tracking
 * @version 1.0
     ******************************************************************************/
public class FreightTrackingAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "freighttrackingmain");
		request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    
    if (!personnelBean.getPermissionBean().hasUserPagePermission("freightTracking"))
    {
      return (mapping.findForward("nopermissions"));
    }

	

	  if (form == null) return (mapping.findForward("success"));

		String action = (String) ((DynaBean) form).get("uAction");

		FreightTrackingProcess process = new FreightTrackingProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//copy date from dyna form to the data form
	 	HubTrackingNumbersViewBean bean = new HubTrackingNumbersViewBean();
	 	BeanHandler.copyAttributes(form, bean);
		if ("search".equalsIgnoreCase(action)) {
			//load data
			request.setAttribute("freightTrackingColl",process.getSearchData(bean));
		}else if ("update".equalsIgnoreCase(action)) {
			Collection<HubTrackingNumbersViewBean> beans = BeanHandler.getBeans((DynaBean)form,"hubTrackingNumbersViewBean",new HubTrackingNumbersViewBean(),this.getTcmISLocale(request));
			String errorMsg = process.updateData(personnelBean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			//reload data
			request.setAttribute("freightTrackingColl",process.getSearchData(bean));
		} else if ("createExcel".equalsIgnoreCase(action)) {
         this.setExcel(response,"FreightTracking");
         process.getExcelReport(bean, personnelBean.getLocale()).write(response.getOutputStream());
	      return noForward;
		}else {
			request.setAttribute("dropdownColl",process.createDropdownRelationalObject(process.getDropdownData(personnelBean)));
			request.setAttribute("hasEditPermission",process.hasEditPermission(personnelBean));
		}


	 return (mapping.findForward("success"));
  }
}
