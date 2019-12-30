package com.tcmis.client.utc.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogCartBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class OciCatalogAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		PersonnelBean user = null;
		String uAction = request.getParameter("uAction");
		BigDecimal personnelId = null;

		OrderTrackingProcess orderTrackingProcess = null;

		if (form == null) {
			try {
				user = new PersonnelBean();
				user.setCompanyId(this.getClient(request));
				if (request.getParameter("userid") == null || request.getParameter("userid").length() == 0) {
					return mapping.findForward("nopermissions");
				}
				user.setLogonId(request.getParameter("userid"));
				user.setClearTextPassword(request.getParameter("passwd"));
				LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
				user = loginProcess.loginWeb(user, false);
                user.setDbUser(this.getDbUser(request));
                this.setSessionObject(request, user, "personnelBean");
				Locale locale = null;
				if ("fr".equalsIgnoreCase(request.getParameter("locale"))) {
					locale = new Locale("fr", "FR");
					com.tcmis.common.admin.action.LoginAction.setLocaleAnyway(request, request.getSession(), locale.toString());
					user.setLocale(locale);
				} else {
					locale = new Locale("en", "US");
					com.tcmis.common.admin.action.LoginAction.setLocaleAnyway(request, request.getSession(), locale.toString());
					user.setLocale(locale);
				}
			} catch (Exception ex) {
				return mapping.findForward("nopermissions");
			}

			orderTrackingProcess = new OrderTrackingProcess(this.
				 getDbUser(request), this.getTcmISLocaleString(request));
			personnelId = new BigDecimal(user.getPersonnelId());
			request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
			request.setAttribute("myDefaultFacilityId",orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			// enable utc ocicatalog only features
			request.setAttribute("showcart", "true");
			request.setAttribute("showmenu", "true");
			request.setAttribute("ecommerceLogon", "Y");
			request.setAttribute("ecommerceSource", "OCI");
			return mapping.findForward("success");
		}

		if (!this.isLoggedIn(request)) return mapping.findForward("nopermissions");

		System.out.print("locale==" + request.getSession().getAttribute("tcmISLocale"));
		user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		orderTrackingProcess = new OrderTrackingProcess(this.
			 getDbUser(request), this.getTcmISLocaleString(request));
		personnelId = new BigDecimal(user.getPersonnelId());

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			CatalogInputBean bean = new CatalogInputBean();

			BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));

//	 for( String key: (java.util.Set<String>) request.getParameterMap().keySet() ) {
//		 System.out.println(key+":"+request.getParameter(key));
//	 }
			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));

			if (uAction != null && uAction.equals("showCart")) {
				Collection<CatalogCartBean> beans = BeanHandler.getBeans((DynaBean) form, "CatalogCartBean", new CatalogCartBean(), this.getLocale(request));
				request.setAttribute("CatalogCartBeanCollection", beans);
				return mapping.findForward("showcart");
			} else if ("search".equals(uAction)) {
				Vector<PrCatalogScreenSearchBean> c = (Vector<PrCatalogScreenSearchBean>) catalogProcess.getSearchResult(bean, user);
				Object[] results = catalogProcess.createRelationalObjects(c);
				request.setAttribute("prCatalogScreenSearchBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("rowCountItem", results[2]);
				this.saveTcmISToken(request);
			} else if ("createXSL".equals(uAction)) {
				this.setExcel(response, "OCICatalogSearch");
				try {
					catalogProcess.getPartExcelReport(bean, user).write(response.getOutputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
			} else if ("loaddata".equals(uAction)) {
				PrCatalogScreenSearchBean pbean = new PrCatalogScreenSearchBean();
				BeanHandler.copyAttributes(form, pbean, this.getTcmISLocale(request));

				request.setAttribute("specColl", catalogProcess.getSpecMenu(pbean));
				request.setAttribute("partInvColl", catalogProcess.getInventoryMenu(pbean));
				request.setAttribute("stockingReorder", catalogProcess.getStockingReorder(pbean));
				request.setAttribute("ImgLit", catalogProcess.getImgLit(pbean));

				return mapping.findForward("success");
			}
//	 search frame
			else {
				request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
				request.setAttribute("myDefaultFacilityId",orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			}
			return mapping.findForward("success");
		}
		return mapping.findForward("success");
	}
}
