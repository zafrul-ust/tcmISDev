package com.tcmis.internal.distribution.action;


import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.distribution.beans.ItemLookupInputBean;
import com.tcmis.internal.distribution.process.ItemLookupProcess;


public class ItemLookupAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "itemlookupmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) {
			return (mapping.findForward("success"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
        if (!personnelBean.getPermissionBean().hasUserPagePermission("itemLookup"))
        {
            return (mapping.findForward("nopermissions"));
        }
		ItemLookupInputBean input = new ItemLookupInputBean(form);
		Locale locale = getTcmISLocale(request);
		
		if (input.isSearch()) {
			ItemLookupProcess process = new ItemLookupProcess(getDbUser(request), locale);
			request.setAttribute("itemCatalogBeanCollection", process.search(input, personnelBean));

			this.saveTcmISToken(request);
		}
		else if (input.isCreateExcel()) {
			try {
				setExcel(response, "Search Global Item");
				ItemLookupProcess process = new ItemLookupProcess(getDbUser(request), locale);
				process.getItemExcelReport(input, personnelBean).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}

		return (mapping.findForward("success"));
	}
}
