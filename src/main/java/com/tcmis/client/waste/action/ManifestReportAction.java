package com.tcmis.client.waste.action;

import javax.servlet.http.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import org.apache.struts.action.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.waste.process.ManifestReportProcess;
import com.tcmis.client.waste.beans.ManifestReportInputBean;

/******************************************************************************
 * Controller for TDSF Report
 * @version 1.0
 ******************************************************************************/
public class ManifestReportAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "manifestreportmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }


    //populate drop downs
    //If you need access to who is logged in
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");

    ManifestReportProcess process = new ManifestReportProcess(this.getDbUser(request));

    if (form != null) {
      this.saveTcmISToken(request);
      ManifestReportInputBean inputBean = new ManifestReportInputBean();
      BeanHandler.copyAttributes(form, inputBean);
      if (inputBean.getSubmitSearch() != null && inputBean.getSubmitSearch().trim().length() > 0) {
        //Pass the result collection in request
        request.setAttribute("manifestReportCollection", process.getManifestReportData(inputBean));
        return (mapping.findForward("showresults"));
      } else if (((DynaBean) form).get("action") != null && "createPdf".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        String pdfUrl = process.getPdfReport(inputBean);
        if (pdfUrl.length() > 0) {
          request.setAttribute("filePath", pdfUrl);
          return mapping.findForward("viewfile");
        }else {
          return mapping.findForward("genericerrorpage");
        }
      }	else if (( (DynaBean) form).get("action") != null &&
                ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("start")) {
              //making sure user has permission to view this page
              if (!personnelBean.getPermissionBean().hasWasteLocationPermission("TsdfReport","","","")) {
                return (mapping.findForward("nopermissions"));
              }
              //first get header dropdown data
              request.setAttribute("manifestReportDropdownCollection", process.getManifestDropdownData(personnelBean.getPersonnelId()));
         return mapping.findForward("success");
      }

    }
    return (mapping.findForward("success"));
  }

}
