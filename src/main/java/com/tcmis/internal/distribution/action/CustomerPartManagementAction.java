package com.tcmis.internal.distribution.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.process.CustomerPartManagementProcess;
import com.tcmis.internal.distribution.beans.CustomerPartManagementInputBean;

import java.util.Vector;
import java.util.Collection;

/******************************************************************************
 * Controller for Customer Part Management page
 * @version 1.0
 ******************************************************************************/

public class CustomerPartManagementAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customerpartmanagementmain");
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		if (form == null) {
			return (mapping.findForward("success"));
		}
		String action = request.getParameter("uAction");
		if (action == null) {
			action = "";
		}
		CustomerPartManagementInputBean inputBean = new CustomerPartManagementInputBean();
	   BeanHandler.copyAttributes(form, inputBean);
	   CustomerPartManagementProcess process = new CustomerPartManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
		String forward = "success";
		if (action.equals("search") ) {
			saveTcmISToken(request);
			Collection dataColl = process.getSearchData(inputBean);
			request.setAttribute("customerPartManagementCollection", dataColl);
			request.setAttribute("dataRowSpanHashMap",process.getSearchDataRowSpan(dataColl));
		} else if (action.equals("update") ) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection beans = BeanHandler.getBeans((DynaBean) form, "customerPartManagementDiv", new CustomerPartManagementInputBean(), this.getTcmISLocale(request));
			Collection errorMsgs = (Collection)process.updateData(inputBean,beans);
			if(errorMsgs != null)
				request.setAttribute("tcmISErrors", errorMsgs);
			//research data
			Collection dataColl = process.getSearchData(inputBean);
			request.setAttribute("customerPartManagementCollection", dataColl);
			request.setAttribute("dataRowSpanHashMap",process.getSearchDataRowSpan(dataColl));

		}		
		else if (action.equals("delete") ) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection beans = BeanHandler.getBeans((DynaBean) form, "customerPartManagementDiv", new CustomerPartManagementInputBean(), this.getTcmISLocale(request));
			process.deleteData(inputBean,beans);
			//research data
			Collection dataColl = process.getSearchData(inputBean);
			request.setAttribute("customerPartManagementCollection", dataColl);
			request.setAttribute("dataRowSpanHashMap",process.getSearchDataRowSpan(dataColl));
		} else if (action.equals("createExcel")) {
        	this.setExcel(response,"CustomerPartManagement");
         process.getExcelReport(inputBean).write(response.getOutputStream());
	      return noForward;
		}else {
			//load initial data
		}

		return (mapping.findForward(forward));
    }
  
}