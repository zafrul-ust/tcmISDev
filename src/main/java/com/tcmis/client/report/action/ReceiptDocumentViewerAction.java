package com.tcmis.client.report.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.report.beans.FacilityPrRulesBean;
import com.tcmis.common.util.StringHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.MyWorkareaFacilityViewBean;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.report.beans.ReceiptDocumentViewerInputBean;
import com.tcmis.client.report.process.ReceiptDocumentViewerProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for viewing and receipt documents
 * @version 1.0
 ******************************************************************************/
public class ReceiptDocumentViewerAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "receiptdocumentviewermain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		
	     if (!user.getPermissionBean().hasUserPagePermission("deliveries"))
	       return (mapping.findForward("nopermissions"));

	 	ReceiptDocumentViewerProcess process = new ReceiptDocumentViewerProcess(getDbUser(request), getTcmISLocaleString(request));
		ReceiptDocumentViewerInputBean input = new ReceiptDocumentViewerInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));
		if (input.isSearch()) {
			request.setAttribute("receiptDocumentOvBeanCollection", process.getSearchResult(input,user.getPersonnelIdBigDecimal()));
			this.overrideMaxData(request);	//don't apply max data for rest of codes below
			request.setAttribute("prRulesViewCollection", process.getPrRulesForFacility(input,user.getCompanyId(),user.getPersonnelIdBigDecimal()));
			request.setAttribute("inputBean", input);
			request.setAttribute("deliveriesCostData", process.isFeatureRelease(user,input,"DeliveriesCostData"));
			request.setAttribute("deliveriesCustomerData", process.isFeatureRelease(user,input,"DeliveriesCustomerData"));
			if (input.isGroupByMR())
				return mapping.findForward("showmrresults");
			else
				return mapping.findForward("showlotresults");
		}else if (input.isCreateExcel()) {
			if (StringHandler.isBlankString(input.getDeliveriesCostData())) {
				if (process.isFeatureRelease(user,input,"DeliveriesCostData"))
					input.setDeliveriesCostData("Y");
				else
					input.setDeliveriesCostData("N");
			}
			this.overrideMaxData(request);	//don't apply max data for rest of codes below
			Collection results = process.getSearchResult(input,user.getPersonnelIdBigDecimal());
			setExcel(response, "ReceiptDocumentViewGenerateExcel");
			process.getExcelReport(user,input, results, getLocale(request)).write(response.getOutputStream());
			return noForward;
		}else if ("multiselect".equalsIgnoreCase(request.getParameter("uAction"))) {
			return (mapping.findForward("multiselect"));
		}else {
			this.overrideMaxData(request);	//don't apply max data for rest of codes below
			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request));
			Vector<MyWorkareaFacilityViewBean> v = (Vector<MyWorkareaFacilityViewBean>)orderTrackingProcess.getMyWorkareaFacilityViewBean(user.getPersonnelIdBigDecimal());
			request.setAttribute("myWorkareaFacilityViewBeanCollection", v);
			request.setAttribute("poRequiredFacs", process.getFacilityRequiredPo(v,user.getCompanyId()));
			if (user.isFeatureReleased("SelectMultipleDropDownOption",null,user.getCompanyId()))
				request.setAttribute("myFacilityGroupFacilityCollection",process.getUserFacilityGroupFacilityDropDownData(user.getPersonnelIdBigDecimal(),user.getCompanyId()));
			else
				request.setAttribute("myFacilityGroupFacilityCollection",new Vector(0));
			return mapping.findForward("success");
		}

	}
} //end of class