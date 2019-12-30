package com.tcmis.client.report.action;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.report.process.*;

/**
 * Controller for Ad Hoc Waste report.
 */

public class SaveTemplateAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      return (mapping.findForward("login"));
    }
      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    	String reportType = request.getParameter("reportType");
    	String gateKeeping = request.getParameter("gateKeeping");
    	String facilityId = request.getParameter("facilityId");
    	GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
	   if("usage".equalsIgnoreCase(reportType)) {
			AdHocUsageReportProcess adHocUsageReportProcess = new AdHocUsageReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",adHocUsageReportProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,"Y"));
			if (gateKeeping != null && gateKeeping.equalsIgnoreCase("true")) {
				request.setAttribute("userGroupCollection",genericReportProcess.getUserGroupsForFacility(personnelBean, facilityId));
			}
		}else if("materialmatrix".equalsIgnoreCase(reportType)) {
			AdHocMaterialMatrixReportProcess materialMatrixProcess = new AdHocMaterialMatrixReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",materialMatrixProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,"Y"));
		 }else if("waste".equalsIgnoreCase(reportType)) {
		 	AdHocWasteReportProcess wasteProcess = new AdHocWasteReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",wasteProcess.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,"Y"));
		}else if ("cost".equalsIgnoreCase(reportType)) {
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			CostReportProcess costReportProcess = new CostReportProcess(this.getDbUser(request));
			request.setAttribute("templateCollection",costReportProcess.getTemplate(personnelId,null,"Y"));
		}else if("inventory".equalsIgnoreCase(reportType)) {
			AdHocInventoryReportProcess process = new AdHocInventoryReportProcess(this.getDbUser(request),getLocale(request));
			request.setAttribute("templateCollection",process.getTemplate(new BigDecimal(personnelBean.getPersonnelId()),null,"Y"));
			if (gateKeeping != null && gateKeeping.equalsIgnoreCase("true")) {
				request.setAttribute("userGroupCollection",genericReportProcess.getUserGroupsForFacility(personnelBean, facilityId));
			}
		}
    	return (mapping.findForward("showinput"));
  }

}