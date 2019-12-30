package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.QuotationHistoryViewBean;
import com.tcmis.internal.distribution.process.QuotationHistoryProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class QuotationHistoryAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "quotationhistory");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	 /*  if (!personnelBean.getPermissionBean().hasUserPagePermission("orderEntry"))
       {
         return (mapping.findForward("nopermissions"));
       }*/

		String forward = "success";       
		request.setAttribute("startSearchTime", new Date().getTime());     

		QuotationHistoryViewBean inputBean = new QuotationHistoryViewBean();
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));

		QuotationHistoryProcess process = new QuotationHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));         

		// Search
		if ( (inputBean.getAction() == null)  || ("".equals(inputBean.getAction()) ) ) {
			request.setAttribute("quoteHistoryViewBeanCollection", process.getSearchResult(inputBean, personnelBean));

			this.saveTcmISToken(request);

		}

		request.setAttribute("endSearchTime", new Date().getTime() );      
		return (mapping.findForward(forward));
	}
}
