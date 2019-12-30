package com.tcmis.internal.hub.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.process.TruckLoadFreightProcess;
import com.tcmis.internal.hub.beans.FreightTlTrackingViewBean;

import java.util.Collection;

/******************************************************************************
 * Controller for truckload freight
 * @version 1.0
     ******************************************************************************/
public class HazmatDetailAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showhazmatdetail");
		request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

	  if (form == null) return (mapping.findForward("success"));

		String action = (String) ((DynaBean) form).get("uAction");

		TruckLoadFreightProcess process = new TruckLoadFreightProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//copy date from dyna form to the data form
	 	FreightTlTrackingViewBean bean = new FreightTlTrackingViewBean();
	 	BeanHandler.copyAttributes(form, bean);
		if ("search".equalsIgnoreCase(action)) {
			//load data
			request.setAttribute("beanCollection",process.getHazmatDetail(bean));
		}
	 	return (mapping.findForward("success"));
  }
}
