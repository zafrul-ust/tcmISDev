package com.tcmis.client.report.action;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.client.report.process.FormattedUsageProcess;
import com.tcmis.client.report.process.FormattedVocUsageProcess;
import com.tcmis.client.report.beans.FormattedUsageInputBean;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.DynaBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Apr 1, 2008
 * Time: 4:25:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedVocUsageAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

      if (!this.isLoggedIn(request)) {
        request.setAttribute("requestedPage", "formattedvocusagereport");
        return (mapping.findForward("login"));
      }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
        "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
    Collection facilityCollection = genericReportProcess.
        getNormalizedFacAppReportData(personnelId);
    request.setAttribute("facAppReportViewBeanCollection", facilityCollection);

    if(form != null) {
        FormattedUsageInputBean inputBean = new FormattedUsageInputBean();
        BeanHandler.copyAttributes(form, inputBean);
        if (( (DynaBean) form).get("submitValue") != null &&
             ( (String) ( (DynaBean) form).get("submitValue")).length() > 0) {
            if (log.isDebugEnabled()) {
              log.debug("Running report");
            }
            this.setExcelHeader(response);
            FormattedVocUsageProcess process = new FormattedVocUsageProcess(this.getDbUser(request));
            process.createReport(inputBean, personnelBean, response.getWriter(), request.getLocale());
        }
    }

    return mapping.findForward("success");
  }
}
