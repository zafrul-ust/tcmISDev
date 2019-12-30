package com.tcmis.internal.hub.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.process.TruckloadCarrierSearchProcess;
import com.tcmis.internal.hub.beans.TlLtlCarrierIgViewBean;

import java.util.Collection;

/******************************************************************************
 * Controller for truckload carrier search
 * @version 1.0
     ******************************************************************************/
public class TruckloadCarrierSearchAction
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

	  if (form == null) return (mapping.findForward("success"));

		String action = (String) ((DynaBean) form).get("uAction");

	   PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		TruckloadCarrierSearchProcess process = new TruckloadCarrierSearchProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//copy date from dyna form to the data form
		TlLtlCarrierIgViewBean bean = new TlLtlCarrierIgViewBean();
	 	BeanHandler.copyAttributes(form, bean);
		if ("search".equalsIgnoreCase(action)) {
			//load data
			request.setAttribute("truckloadCarrierSearchColl",process.getSearchData(bean));
		} else if ("createExcel".equalsIgnoreCase(action)) {
         this.setExcel(response,"TruckloadCarrierSearch");
         process.getExcelReport(bean, personnelBean.getLocale()).write(response.getOutputStream());
	      return noForward;
		}else {
			//do nothing right now
			request.setAttribute("inventoryGroup",bean.getInventoryGroup());
		}


	 return (mapping.findForward("success"));
  }
}
