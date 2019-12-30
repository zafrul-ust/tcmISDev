package com.tcmis.client.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;


/******************************************************************************
 * Controller for Manufacturer Search page
 * @version 1.0
 ******************************************************************************/

public class ClientManageUseCodeAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "manageusecode");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else {

			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			String userAction = request.getParameter("uAction");

			if (form != null && userAction != null) {
				DynaBean dynaForm = (DynaBean) form;

				if (userAction.equals("search")) {
					String facilityId = (String) dynaForm.get("facilityId");
					String companyId = (String) dynaForm.get("companyId");
					GenericSqlFactory genericSqlFactory = new GenericSqlFactory(new DbManager(this.getDbUser(request)));
					genericSqlFactory.setBean(new FacLocAppBean());
					request.setAttribute("useCodes", genericSqlFactory.selectQuery("select application_use_group_id use_code_id,application_use_group_name use_code_name, application_use_group_desc use_code_desc from application_use_group where application_use_group_id <> 'All' and COMPANY_ID ='" + companyId + "' and facility_id ='" + facilityId +"' order by application_use_group_name"));
				}

			}
		}
		return next;
	}
}
