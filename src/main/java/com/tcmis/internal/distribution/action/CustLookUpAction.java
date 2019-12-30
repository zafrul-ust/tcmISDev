package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.process.CustLookUpProcess;

/******************************************************************************
 * Controller for CustomerAddressSearch
 * @version 1.0
 ******************************************************************************/
public class CustLookUpAction
extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		//  login

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customersearchmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		HttpSession session = request.getSession();
		BigDecimal currentCustomerId = (BigDecimal) session.getAttribute("customerId");
		request.setAttribute("currentCustomerId", currentCustomerId);
		if (log.isDebugEnabled()) {
			log.debug("currentCustomerId"+currentCustomerId);
		}

		//  main
		if( form == null )
			return (mapping.findForward("success"));
		String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) return mapping.findForward("success");

		//  result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		CustLookUpProcess process = new CustLookUpProcess(this);

		CustomerAddressSearchViewBean inputBean = new CustomerAddressSearchViewBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if ( uAction.equals("searchCustomer") ||  uAction.equals("search")) {
			Object[] results = process.showResult(inputBean, personnelBean);
			request.setAttribute("CustomerAddressSearchViewCollection", results[0]);
			request.setAttribute("rowCountPart", results[1]);
			request.setAttribute("personnelId", personnelBean.getPersonnelId());
			request.setAttribute("lastName", personnelBean.getLastName());
			request.setAttribute("firstName", personnelBean.getFirstName());
			return (mapping.findForward("success"));
		}
		else if (uAction.equals("createExcel") ) {

			Vector v = (Vector) process.getSearchResult(inputBean, personnelBean);
			this.setExcel(response,"Customer Search");
			process.getExcelReport(v, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;

		}
		//  search
		else {/* set up search page data*/
			//     	request.setAttribute("setUpCollection", process.getSetUpCollection(new BigDecimal(personnelBean.getPersonnelId())));
		}
		return mapping.findForward("success");
	}
}