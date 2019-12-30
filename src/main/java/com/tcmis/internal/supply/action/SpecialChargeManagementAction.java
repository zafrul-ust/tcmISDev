package com.tcmis.internal.supply.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.SpecialChargeManagementInputBean;
import com.tcmis.internal.supply.beans.SpecialChargePoLookupBean;
import com.tcmis.internal.supply.process.SpecialChargeManagementProcess;

/******************************************************************************
 * Controller for NewRemitTo
 * @version 1.0
 ******************************************************************************/

public class SpecialChargeManagementAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "specialchargemanagement");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			return mapping.findForward("login");
		}
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
				
		//if form is not null we have to perform some action
		if (form != null) {
			SpecialChargeManagementProcess process = new SpecialChargeManagementProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			SpecialChargeManagementInputBean inputBean = new SpecialChargeManagementInputBean(form, getTcmISLocale());
			if (inputBean.isChangeStatus()) {
				Collection<SpecialChargePoLookupBean> beanColl = BeanHandler.getBeans((DynaBean) form, "specialChargePOBean", new SpecialChargePoLookupBean(), getTcmISLocale(), "toModify");
				process.changePoStatus(beanColl, personnelBean.getPersonnelIdBigDecimal());
			}
			
			Collection<SpecialChargePoLookupBean> result = process.getSearchResult(inputBean);
			request.setAttribute("specialChargePoColl", result);
			if (result != null)
				inputBean.setTotalLines(result.size());
			else
				inputBean.setTotalLines(0);
			request.setAttribute("searchInput", inputBean);
		}
		
		return mapping.findForward("success");
	} //end of method
}
