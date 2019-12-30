package com.tcmis.client.report.action;


import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.report.beans.ScanScoreInputBean;
import com.tcmis.client.report.beans.ScanScoreReportViewBean;
import com.tcmis.client.report.process.ScanScoreReportProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;

public class ScanScoreReportAction extends TcmISBaseAction
{
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
			if (!this.isLoggedIn(request)) {
			 request.setAttribute("requestedPage", "scanscorereportmain");
			 request.setAttribute("requestedURLWithParameters",
				this.getRequestedURLWithParameters(request));
			 return (mapping.findForward("login"));
			}
   
			ScanScoreReportProcess synonymProcess = new ScanScoreReportProcess(this.getDbUser(request),getTcmISLocaleString(request));
			// copy date from dyna form to the data bean
			ScanScoreInputBean inputBean = new ScanScoreInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
			/*Need to check if the user has permissions to view this page. if they do not have the permission
	        we need to not show them the page.*/
	        if (!personnelBean.getPermissionBean().hasUserPagePermission("workAreaScanReport"))
	        {
	            return (mapping.findForward("nopermissions"));
	        }

			if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction()))
	        {
	             Collection<ScanScoreReportViewBean> scanScoreViewBeanColl = synonymProcess.getScanScoreReport(inputBean,personnelBean);
	             request.setAttribute("scanScoreViewBeanColl", scanScoreViewBeanColl);
	             this.saveTcmISToken(request);
	             return (mapping.findForward("success"));

	       }
			
		 else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction()))
	         {
	             this.setExcel(response, "ScanScoreReportExcel");
	             synonymProcess.getExcelReport(synonymProcess.getScanScoreReport(inputBean, personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	             return noForward;
	        }
		 else{		
			request.setAttribute("divFacGrpFacReAppOvBeanColl",synonymProcess.getDropDowns(inputBean.getCompanyId()));
			return (mapping.findForward("success"));
		 }
		 }
		}