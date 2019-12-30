package com.tcmis.client.report.action;

import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.report.process.ChartProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
 ******************************************************************************/
public class CreateDashboardChartAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
//    if (!this.isLoggedIn(request)) {
//      return (mapping.findForward("login"));
//    }
log.debug("calling action");
    ChartProcess process = new ChartProcess(getDbUser(request));

    Collection fileNames = process.generateChemicalDashboardChart();
    request.setAttribute("map", process.getMap());
    request.setAttribute("fileNames", fileNames);
    request.setAttribute("chartType",
                         "TcmIS Chemical Dashboard");
/*
    if (log.isDebugEnabled()) {
      log.debug("FILENAMES:" + fileNames.toString());
    }
*/
    return (mapping.findForward("success"));
  }
}