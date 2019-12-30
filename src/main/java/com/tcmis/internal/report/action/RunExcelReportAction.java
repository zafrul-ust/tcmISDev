package com.tcmis.internal.report.action;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.exceptions.GeneralException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.report.beans.ErrorBean;
import com.tcmis.internal.report.beans.ExcelReportBean;
import com.tcmis.internal.report.process.ExcelReportProcess;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class RunExcelReportAction
    extends TcmISBaseAction {

  //static final String REPORT_SERVER_PATH_TEMP = "/webdata/html/reports/temp/";
  //static final String REPORT_URL_PATH_TEMP = "/reports/temp/";

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
		 request.setAttribute("requestedPage", "runexcelreport");
      return (mapping.findForward("login"));
    }

    ExcelReportBean bean = null;
    HttpSession session = request.getSession(false);
    try {
      bean = (ExcelReportBean)this.getSessionObject(request, "excelReportBean");
      if (bean != null) {
        ExcelReportProcess excelReportProcess =
            new ExcelReportProcess(getDbUser(request));

        ResourceLibrary resource = new ResourceLibrary("report");
        if (log.isDebugEnabled()) {
          log.debug("server path:" +
                    resource.getString("excel.report.serverpath"));
          log.debug("url path:" + resource.getString("excel.report.urlpath"));
        }
        File dir = new File(resource.getString("excel.report.serverpath"));
        File file = File.createTempFile("excel", ".xls", dir);

        excelReportProcess.writeExcelFile(this.getPersonnelId(request),
                                          file.getAbsolutePath(), bean);
        request.setAttribute("filePath",
                             resource.getString("excel.report.urlpath") +
                             file.getName());
        return (mapping.findForward("viewfile"));
      }
      else {
        if (log.isDebugEnabled()) {
          log.debug("bean is null!");
          throw new BeanCopyException("No bean");
        }
      }
    }
    catch (BeanCopyException bce) {
      //error copying bean
      log.info("Bean copy ERROR:" + bce.getMessage());
      BaseException bex = new BaseException(bce);
      bex.setMessageKey("error.bean.copy");
      throw bex;
    }
    catch (DbUpdateException uex) {
      //error copying bean
      log.info("db update ERROR:" + uex.getMessage());
      BaseException bex = new BaseException(uex);
      bex.setMessageKey("excel.label.maskmsg");
      //since I want to display the oracle error I need to add it to the request
      ErrorBean errorBean = new ErrorBean();
      errorBean.setCause(uex.getMessage());
      request.setAttribute("errorBean", errorBean);

      throw bex;
    }
    catch (GeneralException gex) {
      //error copying bean
      log.info("general ERROR:" + gex.getMessage());
      BaseException bex = new BaseException(gex);
      bex.setMessageKey("error.query.permission");
      //since I want to display the oracle error I need to add it to the request
      ErrorBean errorBean = new ErrorBean();
      errorBean.setCause(gex.getMessage());
      request.setAttribute("errorBean", errorBean);

      throw bex;
    }

    catch (BaseException be) {
      //BaseException bex = new BaseException(be);
      be.setMessageKey("error.db.query");
      //since I want to display the oracle error I need to add it to the request
      ErrorBean errorBean = new ErrorBean();
      errorBean.setCause(be.getRootCause().getLocalizedMessage());
      request.setAttribute("errorBean", errorBean);
      throw be;
    }
    finally {
      try {
        //session.removeAttribute("excelReportBean");
      }
      catch (Exception e) {
        //ignore
      }
    }
    return (mapping.findForward("home"));
  }
}
