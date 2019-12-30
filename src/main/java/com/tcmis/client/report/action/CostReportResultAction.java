package com.tcmis.client.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.report.beans.CostReportInputBean;
import com.tcmis.client.report.process.CostReportProcess;

import java.math.BigDecimal;

public class CostReportResultAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		//get data from session
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		CostReportInputBean inputBean = (CostReportInputBean) this.getSessionObject(request, "costReportInputBean"+personnelBean.getPersonnelId());
		if (inputBean == null) {
			return mapping.findForward("systemerrorpage");
		}else {
			//if form is not null we have to perform some action
			if (form != null) {
				//check what button was pressed and determine where to go
				if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
					request.setAttribute("costReportInputBean",inputBean);
					return (mapping.findForward("showsearch"));
				} else if (((DynaBean) form).get("action") != null && "result".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
					CostReportProcess costReportProcess = new CostReportProcess(this.getDbUser(request));
					request.setAttribute("costReportViewBeanCollection",costReportProcess.getSearchHtmlResults(personnelId,inputBean,this.getLocale(request)));
					request.setAttribute("reportFields",inputBean.getReportFields());
					request.setAttribute("sqlFields",inputBean.getSqlFields());
					request.setAttribute("totalPerCurrency",inputBean.getTotalPerCurrencyDisplay());
					//remove session object
					this.removeSessionObject(request,"costReportInputBean"+personnelBean.getPersonnelId());
					return (mapping.findForward("showresults"));
				}
			}
			return mapping.findForward("success");
		}
	} //end of method
} //end of class
