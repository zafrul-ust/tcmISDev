package com.tcmis.client.report.action;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.report.process.GenericReportProcess;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Apr 1, 2008
 * Time: 4:38:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MsdsRevisionReportAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "msdsrevisionreport");
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

    return mapping.findForward("success");
  }
}
