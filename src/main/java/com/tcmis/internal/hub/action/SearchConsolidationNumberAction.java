package com.tcmis.internal.hub.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.process.FreightAdviceProcess;
import com.tcmis.internal.hub.beans.FreightAdviceInputBean;

import java.util.Collection;

/******************************************************************************
 * Controller for search consolidation number
 * @version 1.0
     ******************************************************************************/
public class SearchConsolidationNumberAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "searchconsolidationnumber");
		request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

	  if (form == null) return (mapping.findForward("success"));

		String action = (String) ((DynaBean) form).get("uAction");

		FreightAdviceProcess process = new FreightAdviceProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//copy date from dyna form to the data form
	 	FreightAdviceInputBean bean = new FreightAdviceInputBean();
	 	BeanHandler.copyAttributes(form, bean,getTcmISLocale(request));
		if ("search".equalsIgnoreCase(action)) {
			//load data
			request.setAttribute("beanColl",process.getConsolidationNumber(bean));
		}else {
			//do nothing
		}


	 return (mapping.findForward("success"));
  }
}
