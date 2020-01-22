package com.tcmis.client.api.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.api.process.LaunchTcmisProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Controller for LaunchTcmisAction
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class LaunchTcmisAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		String payloadId=request.getParameter("payloadId"); // to retrieve payloadID and logon id.payloadId
		String logonId=request.getParameter("logonId");
		String ecommerceSource=request.getParameter("ecommerceSource");

		if (form == null && !StringHandler.isBlankString(ecommerceSource)) {
			PersonnelBean user = new PersonnelBean();
			try {
				LaunchTcmisProcess process = new LaunchTcmisProcess(this.getDbUser(request));
				if(Integer.parseInt(process.getPayloadIdCount(payloadId,logonId)) > 0) {
					user.setCompanyId(this.getClient(request));
					user.setLogonId(logonId);				
					LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
					user = loginProcess.loginWeb(user, false);
	                user.setDbUser(this.getDbUser(request));
	                this.setSessionObject(request, user, "personnelBean");
					Locale locale = null;
					locale = new Locale("en", "US");
					com.tcmis.common.admin.action.LoginAction.setLocaleAnyway(request, request.getSession(), locale.toString());
					user.setLocale(locale);
				} else
					return mapping.findForward("nopermissions");
			} catch (Exception ex) {
				return mapping.findForward("nopermissions");
			}
			user.setCustomerReturnId(payloadId);
			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(user.getPersonnelIdBigDecimal()));
			request.setAttribute("myDefaultFacilityId",orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(),user.getCompanyId()));
			user.setEcommerceLogon("Y");
			user.setEcommerceSource(ecommerceSource);
			user.setPayloadTimestamp(request.getParameter("timestamp"));
			user.setEcommerceLanguage(request.getParameter("lang"));
			return mapping.findForward("application");
		}
		return mapping.findForward("success");
	}
}
