package com.tcmis.client.report.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.report.beans.FormattedScaqmdInputBean;
import com.tcmis.client.report.process.FormattedScaqmdProcess;

/******************************************************************************
 * Action for creating Scaqmd reports
 * @version 1.0
 *****************************************************************************/
public class FormattedScaqmdAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showformattedscaqmdreport");
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}


		//if form is not null we have to perform some action
		if (form != null) {
			//this.saveTcmISToken(request);
			//copy date from dyna form to the data form
			FormattedScaqmdInputBean bean = new FormattedScaqmdInputBean();
			BeanHandler.copyAttributes(form, bean);

			FormattedScaqmdProcess formattedScaqmdProcess = new FormattedScaqmdProcess(this.getDbUser(request));
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			PermissionBean permissionBean = personnelBean.getPermissionBean();
			if (!(permissionBean.hasFacilityPermission("CreateReport", "LAX - ACMX",null)
			 || permissionBean.hasFacilityPermission("CreateReport", "LAX - AS",null)
			 || permissionBean.hasFacilityPermission("CreateReport", "LAX - CC",null)
			 || permissionBean.hasFacilityPermission("CreateReport", "LAX - FMX",null)
			 || permissionBean.hasFacilityPermission("CreateReport", "LAX - TOGSE", null))) {
			 return mapping.findForward("nopermissions");
			}
			//request.setAttribute("scaqmdReportPermission", formattedScaqmdProcess.hasPermissionToAccessReport(personnelId));

			/*Get search drop down
			Collection yearCollection = formattedScaqmdProcess.getBeginYear();
			request.setAttribute("yearCollection", yearCollection);
			*/
			Calendar beginDate = Calendar.getInstance();
			int beginYear = beginDate.get(Calendar.YEAR);
			int beginMonth = beginDate.get(Calendar.MONTH)+1;   //+1 coz calendar month 0-11
			beginMonth = beginMonth-1;  //-1 last month
			if (beginMonth == 0) {
				beginMonth = 12;
				beginYear = beginYear -1;
			}
			String tmpMonth = (new Integer(beginMonth)).toString();
			if (tmpMonth.length()==1) {
				tmpMonth = "0"+tmpMonth;
			}
			request.setAttribute("beginDate", tmpMonth+"/01/"+beginYear);
			Calendar endDate = Calendar.getInstance();
			endDate.set(endDate.get(Calendar.YEAR),endDate.get(Calendar.MONTH),0,0,0,0);
			int endYear = endDate.get(Calendar.YEAR);
			int endMonth = endDate.get(Calendar.MONTH)+1;   //+1 coz calendar month 0-11
			//endMonth = endMonth-1;  //-1 last month
			if (endMonth == 0) {
				endMonth = 12;
				endYear = endYear -1;
			}
			tmpMonth = (new Integer(endMonth)).toString();
			if (tmpMonth.length()==1) {
				tmpMonth = "0"+tmpMonth;
			}
			String endDay = (new Integer(endDate.get(endDate.DATE))).toString();
			if (endDay.length() == 1) {
				endDay = "0"+endDay;
			}
			request.setAttribute("endDate", tmpMonth+"/"+endDay+"/"+endYear);

			if ("Generate Report".equalsIgnoreCase(bean.getGenerateReport())) {
				String filePath = formattedScaqmdProcess.generateReport(bean);
				if (filePath != null) {
					if (filePath.length() > 1) {
						this.setSessionObject(request, filePath, "filePath");
						request.setAttribute("showReport", "Yes");
					}else {
						return mapping.findForward("systemerrorpage");
					}
				}else  {
					return mapping.findForward("systemerrorpage");
				}
			}
		}
		return mapping.findForward("success");
	} //end of method
} //end of class
