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
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.report.process.FormattedUsageProcess;
import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.client.report.beans.FormattedUsageInputBean;
import com.tcmis.client.catalog.process.OrderTrackingProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class FormattedUsageAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

      if (!this.isLoggedIn(request)) {
        request.setAttribute("requestedPage", "formattedusagereport");
        return (mapping.findForward("login"));
      }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
        "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
    Collection facilityCollection = genericReportProcess.
        getNormalizedFacAppReportData(personnelId);
    request.setAttribute("facAppReportViewBeanCollection", facilityCollection);

    //Get search drop down
    Collection listDropDown = genericReportProcess.getReportList();
    request.setAttribute("listOptionBeanCollection", listDropDown);

    if(form != null) {
        FormattedUsageInputBean inputBean = new FormattedUsageInputBean();
        BeanHandler.copyAttributes(form, inputBean);
        if (( (DynaBean) form).get("submitValue") != null &&
             ( (String) ( (DynaBean) form).get("submitValue")).length() > 0) {
            if (log.isDebugEnabled()) {
              log.debug("Running report");
            }
            this.setExcelHeader(response);
            FormattedUsageProcess process = new FormattedUsageProcess(this.getDbUser(request));
            process.createReport(inputBean, personnelBean, response.getWriter(), request.getLocale());
        }
    }

    return mapping.findForward("success");
  }
}