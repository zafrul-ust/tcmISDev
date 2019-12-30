package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.process.CatalogReportProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CatalogReportAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		PersonnelBean personnelBean = null;
		
		BigDecimal personnelId = null;
		OrderTrackingProcess orderTrackingProcess = null;
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "catalogreportmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (!this.isLoggedIn(request)) return mapping.findForward("nopermissions");

		personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		if (!personnelBean.getPermissionBean().hasUserPagePermission("catalogReport")) {
			return (mapping.findForward("nopermissions"));
		}
		
		//copy date from dyna form to the data form
		CatalogInputBean bean = new CatalogInputBean();
		BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));
		String uAction = bean.getuAction();
		CatalogReportProcess catalogReportProcess = new CatalogReportProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		if ("search".equals(uAction)) {
				bean.setPrintScreen(false);
				Vector<PrCatalogScreenSearchBean> c = (Vector<PrCatalogScreenSearchBean>) catalogReportProcess.getSearchResult(bean, personnelId);
				Object[] results = catalogReportProcess.createRelationalObjects(c);
				request.setAttribute("prCatalogScreenSearchBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("rowCountItem", results[2]);
				return mapping.findForward("success");
			
		} else if ("createXSL".equals(uAction)) {
			this.setExcel(response, "CatalogReport");
			try {
				
				catalogReportProcess.getPartExcelReport(bean, personnelId).write(response.getOutputStream());			
			} catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		} 
		else if ("loaddata".equals(uAction)) {
			PrCatalogScreenSearchBean pbean = new PrCatalogScreenSearchBean();
			BeanHandler.copyAttributes(form, pbean, this.getTcmISLocale(request));
			
			request.setAttribute("partInvColl", catalogReportProcess.getInventoryMenu(pbean));
			request.setAttribute("kitMsdsNumber",catalogReportProcess.getKitMsdsNumber(pbean));
		}  
		
		else {
			orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			request.setAttribute("userFacCatAppOvBeanColl", catalogReportProcess.getUserFacCatAppOvBeanColl(personnelId));
			if (StringHandler.isBlankString(personnelBean.getMyDefaultFacilityId())) {
				personnelBean.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(personnelId,personnelBean.getCompanyId()));
			}
			
			return mapping.findForward("success");
		}
		return mapping.findForward("success");
	}
}
