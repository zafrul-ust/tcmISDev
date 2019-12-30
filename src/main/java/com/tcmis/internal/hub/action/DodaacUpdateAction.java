package com.tcmis.internal.hub.action;

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
import com.tcmis.internal.hub.beans.DpmsAddressInputBean;
import com.tcmis.internal.hub.beans.LocationSearchViewBean;
import com.tcmis.internal.hub.process.DodaacUpdateProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class DodaacUpdateAction
extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "dodaacupdatemain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("dodaacUpdate"))
		{
			return (mapping.findForward("nopermissions"));
		}

		String forward = "success";
		if (form != null) {
			DodaacUpdateProcess process = new DodaacUpdateProcess(this.getDbUser(request),getTcmISLocaleString(request));
			DpmsAddressInputBean inputBean = new DpmsAddressInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			String action = (String) ((DynaBean) form).get("action");
			if (log.isDebugEnabled()) {
				log.debug("action" + action);
			}
			if (action != null && "createExcel".equals(action)) {
				this.setExcel(response, "Address List");
				process.getExcelReport(inputBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				return noForward;
			}
			request.setAttribute("vvDodaacTypeCollection", process.getLocationTypeList());
			if ("search".equals(action)) {
				request.setAttribute("locationSearchViewBeanCollection", process.getAddressList(inputBean));
			}
			else if ("update".equals(action)) {
				if (!this.isTcmISTokenValid(request, true)) {
					BaseException be = new BaseException("Duplicate form submission");
					be.setMessageKey("error.submit.duplicate");
					throw be;
				}

				LocationSearchViewBean locationbean = new LocationSearchViewBean();
				Collection<LocationSearchViewBean> beans = BeanHandler.getBeans((DynaBean) form, "locationSearchBean", locationbean, getTcmISLocale(request));
				process.updateLocation(beans);
				request.setAttribute("locationSearchViewBeanCollection", process.getAddressList(inputBean));
			}
			else{
				return (mapping.findForward(forward));
			}
			this.saveTcmISToken(request);
			//VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
			//request.setAttribute("vvCountryBeanCollection", vvDataProcess.getNormalizedCountryState());
		}

		return (mapping.findForward(forward));

	}

}
