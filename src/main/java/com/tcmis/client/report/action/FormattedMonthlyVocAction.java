package com.tcmis.client.report.action;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.client.report.process.FormattedUsageProcess;
import com.tcmis.client.report.process.FormattedMonthlyVocProcess;
import com.tcmis.client.report.beans.FormattedUsageInputBean;
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
 * Time: 4:41:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedMonthlyVocAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
      if (!this.isLoggedIn(request)) {
        request.setAttribute("requestedPage", "formattedmonthlyvocreport");
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
        if ("submit".equalsIgnoreCase(inputBean.getSubmitValue())) {
            if (log.isDebugEnabled()) {
              log.debug("Running report");
            }
            FormattedMonthlyVocProcess process = new FormattedMonthlyVocProcess(this.getDbUser(request));
            process.createReport(inputBean, personnelBean, response);
            return noForward;
        }
    }
/*
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showformattedusagereport");
      return (mapping.findForward("login"));
    }


    if (form == null) {
      this.saveTcmISToken(request);
    }

    //if form is not null we have to perform some action
    if (form != null) {
      //copy date from dyna form to the data form
      FormattedUsageInputBean bean = new FormattedUsageInputBean();
      BeanHandler.copyAttributes(form, bean);

      FormattedUsageProcess formattedUsageProcess = new FormattedUsageProcess(this.getDbUser(request));

      //PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
      //BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
      BigDecimal personnelId = new BigDecimal(86030);
      OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request));
      request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
      //Get search drop down
      Collection listDropDown = formattedUsageProcess.getReportList();
      request.setAttribute("listOptionBeanCollection", listDropDown);
      Collection yearCollection = formattedUsageProcess.getBeginYear();
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
        request.setAttribute("beginYear", (new Integer(currentYear - 1)).toString());
      }else {
        //set begin month to January if this is the first year
        request.setAttribute("beginMonth", (new Integer(Calendar.JANUARY)).toString());
        request.setAttribute("beginYear", (new Integer(currentYear)).toString());
      }
      request.setAttribute("endMonth", (new Integer(currentMonth)).toString());
      request.setAttribute("endYear", (new Integer(currentYear)).toString());
      //Get group by
      if ("Generate Report".equalsIgnoreCase(bean.getGenerateReport())) {
        formattedUsageProcess.generateReport(bean);
        request.setAttribute("groupByOptionList", formattedUsageProcess.getGroupByOptionList(bean.getGroupByOptionList()));
        request.setAttribute("groupByList", formattedUsageProcess.getGroupByList(bean.getGroupByList()));
      } else {
        String[] groupByOption = {"Delivery Point", "Month"};
        request.setAttribute("groupByOptionList", formattedUsageProcess.getGroupByOptionList(groupByOption));
        String[] groupBy = {"Facility", "Work Area", "CAS #/SARA Group"};
        request.setAttribute("groupByList", formattedUsageProcess.getGroupByList(groupBy));
      }
      request.setAttribute("orderBy", "Part Number");

      this.saveTcmISToken(request);
    }
    */
    return mapping.findForward("success");
  }
}
