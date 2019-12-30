package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.beans.NWeekInventoryInputBean;
import com.tcmis.internal.hub.process.NWeekInventoryProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class NWeekInventoryAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "nweekinventorymain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
        if (!personnelBean.getPermissionBean().hasUserPagePermission("nWeekInv"))
        {
            return (mapping.findForward("nopermissions"));
        }

		String forward = "success";      

		NWeekInventoryInputBean inputBean = new NWeekInventoryInputBean();
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));

		NWeekInventoryProcess process = new NWeekInventoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
	
		
		// Search
		if (null!=inputBean.getAction() &&	"search".equals(inputBean.getAction()))  {
			request.setAttribute("lessThanNWeekViewBeanColl", process.getSearchResult(inputBean,personnelBean));
			this.saveTcmISToken(request);
			return (mapping.findForward(forward));
		}
		//  Create Excel
		else if (null!=inputBean.getAction() && "createExcel".equals(inputBean.getAction())) {
			this.setExcel(response, "N_Week_Search_List");
			process.getExcelReport(inputBean,personnelBean,
					(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}		

	else{
		this.saveTcmISToken(request);		
	}

	return (mapping.findForward(forward));
}
}
