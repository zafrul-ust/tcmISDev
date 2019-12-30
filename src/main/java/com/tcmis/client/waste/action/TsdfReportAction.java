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
import com.tcmis.client.waste.process.TsdfReportProcess;
import com.tcmis.client.waste.beans.TsdfReportInputBean;

/******************************************************************************
 * Controller for TDSF Report
 * @version 1.0
 ******************************************************************************/
public class TsdfReportAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "tsdfcontainerreportmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }


    //populate drop downs
    //If you need access to who is logged in
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");

    TsdfReportProcess process = new TsdfReportProcess(this.getDbUser(request));


    if (form == null) {
      //this.saveToken(request);
    } else { //if form is not null we have to perform some action
      this.saveTcmISToken(request);
      TsdfReportInputBean inputBean = new TsdfReportInputBean();
      BeanHandler.copyAttributes(form, inputBean);

      //validate token
      if (((DynaBean) form).get("action") != null && "updateWasteTagNumber".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        if (!this.isTcmISTokenValid(request, true)) {
          BaseException be = new BaseException("Duplicate form submission");
          be.setMessageKey("error.submit.duplicate");
          this.saveTcmISToken(request);
          throw be;
        }
      }


      if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        //Pass the result collection in request
        request.setAttribute("tsdfReportCollection", process.getTsdfReportData(inputBean));
        return (mapping.findForward("showresults"));
      } else if (((DynaBean) form).get("action") != null && "updateWasteTagNumber".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        //first update tag number
        process.updateTagNumber(((String)((DynaBean) form).get("wasteRequestIdLine")),((String)((DynaBean) form).get("newWasteTagNumber")));
        //Pass the result collection in request
        request.setAttribute("tsdfReportCollection", process.getTsdfReportData(inputBean));
        return (mapping.findForward("showresults"));
      } else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
    	  this.setExcel(response,"TsdfReport");
        process.getExcelReport(inputBean, personnelBean.getLocale()).write(response.getOutputStream());
        return noForward;
      } else if (((DynaBean) form).get("action") != null && "start".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        //making sure user has permission to view this page
        if (!personnelBean.getPermissionBean().hasWasteLocationPermission("TsdfReport","","","")) {
          return (mapping.findForward("nopermissions"));
        }
        //first get header dropdown data
        request.setAttribute("wasteTsdfReportDropdownOvBeanCollection", process.getTsdfDropdownData(personnelBean.getPersonnelId()));
        return (mapping.findForward("success"));
      }
    }

    return (mapping.findForward("success"));
  }

}
