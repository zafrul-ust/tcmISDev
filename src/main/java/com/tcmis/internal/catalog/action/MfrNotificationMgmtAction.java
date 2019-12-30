package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.MfrNotificationMgmtInputBean;
import com.tcmis.internal.catalog.beans.MfrNotificationMgmtViewBean;
import com.tcmis.internal.catalog.process.MfrNotificationMgmtProcess;

public class MfrNotificationMgmtAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		MfrNotificationMgmtProcess process = new MfrNotificationMgmtProcess(getDbUser());
		
		ActionForward forward = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "mfrnotificationmgmtmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}
		
		MfrNotificationMgmtInputBean input = new MfrNotificationMgmtInputBean();
		BeanHandler.copyAttributes((DynaBean) form, input);
		if (input.isCategoryLookup()) {
			//Collection<MfrNotificationCategoryBean> components = BeanHandler.getBeans((DynaBean) form, "category", "datetimeformat", new MfrNotificationCategoryBean(), getLocale());
			request.setAttribute("categories", process.getNotificationCategories(parseSelectedCategories(input.getSelectedCategories())));
			forward = mapping.findForward("categoryLookup");
		}
		else if (input.isSearch()) {
			Collection<MfrNotificationMgmtViewBean> notificationReqs = process.searchNotificationRequests(input);
			request.setAttribute("mfrNotificationBeanCollection", notificationReqs);
			request.setAttribute("chemicalApprovers", process.getChemicalApprovers());
		}
		else if (input.isCreate()) {
			forward = noForward;
			PrintWriter out = response.getWriter();
			out.print(process.generateNotificationId());
			out.close();
		}
		else if (input.isUpdate()) {
			@SuppressWarnings("unchecked")
			Collection<MfrNotificationMgmtViewBean> mfrNotReqs = BeanHandler.getBeans((DynaBean) form, "mfrNotificationMgmtBean", "datetimeformat", new MfrNotificationMgmtViewBean(), getLocale());
			process.updateAssignees(mfrNotReqs);
			Collection<MfrNotificationMgmtViewBean> notificationReqs = process.searchNotificationRequests(input);
			request.setAttribute("mfrNotificationBeanCollection", notificationReqs);
			request.setAttribute("chemicalApprovers", process.getChemicalApprovers());
		}
		else if (input.isCreateExcel()) {
			this.setExcel(response, "Mfr_Notification_List");
			process.getExcelReport(input, user, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else {
			request.setAttribute("chemicalApprovers", process.getChemicalApprovers());
		}
		/*else if (input.getuAction().equals("mfrNotTest")) {
			request.setAttribute("mfrNotIndex", input.getMfrNotIndex().intValue()+1);
			forward = mapping.findForward("mfrNotTest");
		}*/
		return forward;
	}
	
	private Collection<BigDecimal> parseSelectedCategories(String selectedCategories) {
		List<BigDecimal> selectedList = new ArrayList<BigDecimal>();
		if ( ! (selectedCategories == null || selectedCategories.isEmpty())) {
			selectedList = Arrays.stream(selectedCategories.split(",")).collect(Collectors.mapping(c -> new BigDecimal(c.toString().trim()), Collectors.toList()));
		}
		return selectedList;
	}
}
