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
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Apr 1, 2008
 * Time: 4:38:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonthlyVOCReportAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "monthlyvocreport");
      return (mapping.findForward("login"));
    }
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
        "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
    Collection facilityCollection = genericReportProcess.
        getNormalizedFacAppReportData(personnelId);
    request.setAttribute("facAppReportViewBeanCollection", facilityCollection);

    // Get search drop down
    Collection listDropDown = genericReportProcess.getReportList();
    request.setAttribute("listOptionBeanCollection", listDropDown);
    
    // Set current month and year
    GenericReportProcess reportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
    Collection yearCollection = reportProcess.getBeginYear();
    request.setAttribute("yearCollection", yearCollection);
    Calendar calendar = Calendar.getInstance();
    int currentMonth = calendar.get(Calendar.MONTH);
    int currentYear = calendar.get(Calendar.YEAR);

    if (yearCollection.size() > 1) {
      if (currentMonth == 0) {
        //if current month is January then the begin month is December
        request.setAttribute("beginMonth", (new Integer(11)).toString());
      }else {
        request.setAttribute("beginMonth", (new Integer(currentMonth - 1)).toString());
      }
      request.setAttribute("beginYear", (new Integer(currentYear)).toString());
    }else {
      //set begin month to January if this is the first year
      request.setAttribute("beginMonth", (new Integer(Calendar.JANUARY)).toString());
      request.setAttribute("beginYear", (new Integer(currentYear)).toString());
    }

    return mapping.findForward("success");
  }
}
