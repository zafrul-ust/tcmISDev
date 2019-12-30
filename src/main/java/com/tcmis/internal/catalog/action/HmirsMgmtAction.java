package com.tcmis.internal.catalog.action;

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
import com.tcmis.internal.catalog.beans.HmirsMgmtInputBean;
import com.tcmis.internal.catalog.beans.HmirsMgmtViewBean;
import com.tcmis.internal.catalog.process.HmirsMgmtProcess;

/******************************************************************************
 * Controller for Material Search page
 * @version 1.0
 ******************************************************************************/

public class HmirsMgmtAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward forward = mapping.findForward("success");
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "hmirsmgmtmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		HmirsMgmtInputBean input = new HmirsMgmtInputBean(form, getTcmISLocale(request), "dateformat");
		try {
			HmirsMgmtProcess process = new HmirsMgmtProcess(this.getDbUser(request));
		
			if (input.isSearch()) {
				Collection<HmirsMgmtViewBean> materialBeanCollection = process.getSearchResults(input);
	
				request.setAttribute("hmirsRoadMap", materialBeanCollection);
			}
			else if (input.isUpdate()) {
				if (user.getPermissionBean().hasFacilityPermission("UpdateHMIRS", "", getCompanyId(request))) {
					Collection<HmirsMgmtViewBean> mappings = BeanHandler.getBeans((DynaBean) form, "hmirsRoadMapData", new HmirsMgmtViewBean());
					process.updateHmirsRoadMap(mappings, user);
				}
				Collection<HmirsMgmtViewBean> materialBeanCollection = process.getSearchResults(input);
	
				request.setAttribute("hmirsRoadMap", materialBeanCollection);
			}
			else if (input.isCreateExcel()) {
				this.overrideMaxData(request);
				this.setExcel(response, "HMIRS_Mgmt");
				process.getExcelReport(input, user, getTcmISLocale(request)).write(response.getOutputStream());
				log.debug("CREATE EXCEL COMPLETED");
				forward = noForward;
			}
		} catch(BaseException e) {
			request.setAttribute("tcmisError", e.getMessage());
            log.error(e);
		}

		return forward;
	}

}
