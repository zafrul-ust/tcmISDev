package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserPageAdminViewBean;
import com.tcmis.common.admin.beans.UserFacilitySelectOvBean;
import com.tcmis.common.admin.beans.UserPageSelectViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

import com.tcmis.common.admin.beans.UserGroupAccessInputBean;
import com.tcmis.common.admin.process.UserFacilitySelectOvProcess;
import com.tcmis.common.admin.process.UserFacilityUgAdminOvProcess;

import com.tcmis.common.admin.beans.UserFacilityUgAdminOvBean;
import com.tcmis.common.admin.beans.UserFacilityAdminViewBean;

/**
 * ***************************************************************************
 * Controller for FacilityPermissions
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CompanyFacilitiesAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

//  login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "facilitypermissionmain".toLowerCase());
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId());	 //modifier
		String action = (String) ((DynaBean) form).get("uAction");			//
		if (action == null) return mapping.findForward("fail");				// should not happen
		
		
		if (action.equals("display")) {
			UserFacilitySelectOvProcess process = new UserFacilitySelectOvProcess(this.getDbUser(request),getTcmISLocaleString(request));
			UserFacilitySelectOvBean bean = new UserFacilitySelectOvBean();
			DynaBean dynaBean = (DynaBean) form;
			BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
			bean.setUserId(pid);
			bean.setPersonnelId(pid);
			Vector v = (Vector) process.getSearchResult(bean);
			request.setAttribute("UserFacilitySelectOvCollection", v);
		}
//  result
		else if (action.startsWith("search")) {

			UserFacilityUgAdminOvProcess process = new UserFacilityUgAdminOvProcess(this.getDbUser(request),getTcmISLocaleString(request));
			DynaBean dynaBean = (DynaBean) form;
			UserFacilityUgAdminOvBean bean = new UserFacilityUgAdminOvBean();
			BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
			bean.setUserId(pid);
			bean.setPersonnelId(pid);
			Vector headerColl = (Vector) process.getHeader(bean);
			request.setAttribute("facilityPermissionsHeaderBeanCollection", headerColl);
			Vector detailColl = (Vector) process.getSearchResult(bean);
			process.synchSupplierLocationHeaderAndDetail((Collection) headerColl, (Collection) detailColl);
			request.setAttribute("FacilityAdminBeanCollection", detailColl);
		}
		else if (action.equals("createXSL")) {
			this.setExcel(response,"FacilityPermission");
			try {
				UserFacilityUgAdminOvProcess process = new UserFacilityUgAdminOvProcess(this.getDbUser(request),getTcmISLocaleString(request));
				DynaBean dynaBean = (DynaBean) form;
				UserFacilityUgAdminOvBean bean = new UserFacilityUgAdminOvBean();

				BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
				bean.setUserId(pid);

				Vector headerColl = (Vector) process.getHeader(bean);
				request.setAttribute("facilityPermissionsHeaderBeanCollection", headerColl);
				Vector detailColl = (Vector) process.getSearchResult(bean);
				process.synchSupplierLocationHeaderAndDetail((Collection) headerColl, (Collection) detailColl);
				BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
				process.getExcelReport(bean, headerColl, detailColl, personnelBean.getLocale(), (String) request.getSession().getAttribute("MODIFIEEFULLNAME")).write(response.getOutputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}


//   search result
		return mapping.findForward("success");
	}

}