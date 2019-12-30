package com.tcmis.internal.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.report.beans.OpenPicksInputBean;
import com.tcmis.internal.report.process.OpenPicksProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class OpenPicksAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		

        if (!this.isLoggedIn(request)) {
           request.setAttribute("requestedPage", "openpicksmain");
           request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
           return (mapping.findForward("login"));
        }
		
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		   if (!personnelBean.getPermissionBean().hasUserPagePermission("openPicks"))
        {
            return (mapping.findForward("nopermissions"));
        }

		String forward = "success";      

		OpenPicksInputBean inputBean = new OpenPicksInputBean();
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));

		OpenPicksProcess process = new OpenPicksProcess(this.getDbUser(request),getTcmISLocaleString(request));
		
		// Search
		if (null!=inputBean.getAction() &&	"search".equals(inputBean.getAction()))  {
			request.setAttribute("openPicksColl", process.getSearchResult(inputBean));
			this.saveTcmISToken(request);			
		}
		//  Create Excel
		else if (null!=inputBean.getAction() && "createExcel".equals(inputBean.getAction())) {
			this.setExcel(response, "Open_Picks_Search_List");
			process.getExcelReport(inputBean,personnelBean,
					(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else if (null!=inputBean.getAction() && "getPickList".equals(inputBean.getAction())) {
			request.setAttribute("pickListIdData", process.getPickListId(inputBean));
			this.saveTcmISToken(request);
			return (mapping.findForward("getjsonobj"));
			
		}	

	else{
		this.saveTcmISToken(request);		
	}

	return (mapping.findForward(forward));
}
	
}
