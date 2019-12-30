package com.tcmis.client.report.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.report.beans.ScanScoreInputBean;
import com.tcmis.client.report.beans.InventoryTurnReportTblBean;
import com.tcmis.client.report.process.CabinetTurnsProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;

public class CabinetTurnsAction extends TcmISBaseAction
{
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
			if (!this.isLoggedIn(request)) {
				 request.setAttribute("requestedPage", "cabinetturnsmain");
				 request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
				 return (mapping.findForward("login"));
			}
   
			CabinetTurnsProcess process = new CabinetTurnsProcess(this.getDbUser(request),getTcmISLocaleString(request));
			// copy date from dyna form to the data bean
			InventoryTurnReportTblBean inputBean = new InventoryTurnReportTblBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
			/*Need to check if the user has permissions to view this page. if they do not have the permission
	        we need to not show them the page.*/
	        if (!personnelBean.getPermissionBean().hasUserPagePermission("cabinetTurns"))
	        {
	            return (mapping.findForward("nopermissions"));
	        }

			if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction()))
	        {
	             Collection<InventoryTurnReportTblBean> inventoryTurnReportColl = process.getSearchResults(inputBean,personnelBean);
	             request.setAttribute("inventoryTurnReportColl", inventoryTurnReportColl);
	             this.saveTcmISToken(request);
	             return (mapping.findForward("success"));
	        }
			else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction()))
			{
	             this.setExcel(response, "ScanScoreReportExcel");
	             process.getExcelReport(process.getSearchResults(inputBean, personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	             return noForward;
	        }
			else{		
				request.setAttribute("divFacGrpFacReAppOvBeanColl",process.getDropDowns(inputBean.getCompanyId()));
				return (mapping.findForward("success"));
			}
	}
}