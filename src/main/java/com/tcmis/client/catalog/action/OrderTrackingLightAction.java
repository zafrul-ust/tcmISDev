package com.tcmis.client.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.client.catalog.beans.ApplicationObjBean;
import com.tcmis.client.catalog.beans.OrderTrackingInputBean;
import com.tcmis.client.catalog.beans.PkgOrderTrackPrOrderTrackBean;
import com.tcmis.client.catalog.process.OrderTrackingLightProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for Order Tracking Light
 * This page is for Haas version
 * @version 1.0
 ******************************************************************************/
public class OrderTrackingLightAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "haasordertrackingmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("haasOrderTracking")) {
			return (mapping.findForward("nopermissions"));
		}

		// if form is not null we have to perform some action
		if (form != null) {
			this.saveTcmISToken(request);
			// copy date from dyna form to the data form
			OrderTrackingInputBean bean = new OrderTrackingInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			String action = (String) ((DynaBean) form).get("action");

			if ("search".equalsIgnoreCase(action)) {
				OrderTrackingLightProcess orderTrackingLightProcess = new OrderTrackingLightProcess(this.getDbUser(request), getTcmISLocaleString(request));

				request.setAttribute("pkgOrderTrackPrOrderTrackBeanCollection", orderTrackingLightProcess.getsearchResult(bean, personnelBean));

				return (mapping.findForward("showresults"));
			} else if ("createExcel".equalsIgnoreCase(action)) {
				OrderTrackingLightProcess orderTrackingLightProcess = new OrderTrackingLightProcess(this.getDbUser(request), getTcmISLocaleString(request));
				this.setExcel(response, "OrderTracking");
				bean.setPersonnelId(personnelBean.getPersonnelId());
				bean.setButtonCreateExcel("createExcel");
				bean.setMaxData(new BigDecimal(1000000));
				orderTrackingLightProcess.getExcelReport(bean,personnelBean);

				return noForward;
			} else if ("getApplicationColl".equalsIgnoreCase(action)) {
				OrderTrackingLightProcess orderTrackingLightProcess = new OrderTrackingLightProcess(this.getDbUser(request), getTcmISLocaleString(request));

				Vector<ApplicationObjBean> applicationColl = (Vector<ApplicationObjBean>) orderTrackingLightProcess.getApplicationColl(bean.getCompanyId(),bean.getFacilityId(),personnelBean.getPersonnelId());

				JSONObject results = new JSONObject();
				JSONArray resultsArray = new JSONArray();
				for (ApplicationObjBean applicationBean : applicationColl) {
					JSONObject docJSON = new JSONObject();
					docJSON.put("application", applicationBean.getApplication());
					docJSON.put("applicationDesc", applicationBean.getApplicationDesc());
					resultsArray.put(docJSON);
				}
				results.put("applicationColl", resultsArray);

				// Write out the data
				PrintWriter out = response.getWriter();
				out.write(results.toString(3));
				out.close();

				return noForward;
			} else if ("truncateMR".equalsIgnoreCase(action)) {
				Collection<PkgOrderTrackPrOrderTrackBean> orders = BeanHandler.getGridBeans((DynaBean) form, "prOrderTrackBean", new PkgOrderTrackPrOrderTrackBean(), getTcmISLocale(), "selected");
				
				OrderTrackingLightProcess orderTrackingLightProcess = new OrderTrackingLightProcess(this.getDbUser(request), getTcmISLocaleString(request));
				orderTrackingLightProcess.truncateMRs(personnelBean, orders);
				request.setAttribute("pkgOrderTrackPrOrderTrackBeanCollection", orderTrackingLightProcess.getsearchResult(bean, personnelBean));

				return (mapping.findForward("showresults"));				
			} else {
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), getTcmISLocaleString(request));
				Collection tmpCol = personnelBean.getMyWorkareaFacilityOvBeanCollection();

				if (tmpCol == null || tmpCol.size() == 0) {
					personnelBean.setMyWorkareaFacilityOvBeanCollection(orderTrackingProcess.getMyFacAppDropdownDataTest(personnelBean.getPersonnelId()));
				}

				tmpCol = personnelBean.getMyCompanyDefaultFacilityIdCollection();
				if (tmpCol == null || tmpCol.size() == 0) {
					personnelBean.setMyCompanyDefaultFacilityIdCollection(orderTrackingProcess.getMyCompanpyDefaultFacility(personnelBean.getPersonnelId()));
				}
				// make sure that user has at least one work area
				tmpCol = personnelBean.getMyWorkareaFacilityOvBeanCollection();

				if (tmpCol == null || tmpCol.size() == 0) {
					return (mapping.findForward("nopermissions"));
				} else {
					return (mapping.findForward("success"));
				}
			}
		}
		return mapping.findForward("success");
	}
}