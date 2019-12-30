package com.tcmis.internal.msds.action;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.msds.beans.*;
import com.tcmis.internal.msds.process.*;

/******************************************************************************
 *
 * @version 1.0
     ******************************************************************************/
public class SearchMsdsAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    //if (!this.isLoggedIn(request)) {
    //  request.setAttribute("requestedPage", "showaddmsds");
    //  return (mapping.findForward("showlogin"));
    //}

//    if (form == null) {
//      this.saveToken(request);
//    }
    MsdsProcess process = new MsdsProcess(this.getDbUser(request));
    if (this.getSessionObject(request, "facBldgFloorDeptEditOvBeanCollection") == null) {
      this.setSessionObject(request,
                            process.getFacilityDropDownData(),
                            "facBldgFloorDeptEditOvBeanCollection");
    }
    if (this.getSessionObject(request, "vvCustomerMsdsStatusBeanCollection") == null) {
      this.setSessionObject(request,
                            process.getStatusDropDownData(),
                            "vvCustomerMsdsStatusBeanCollection");
    }
    if (this.getSessionObject(request, "vvMsdsHazardClassificationBeanCollection") == null) {
      this.setSessionObject(request,
                            process.getHazardClassificationDropDownData(),
                            "vvMsdsHazardClassificationBeanCollection");
    }
    //if form is not null we have to perform some action
    if (form != null) {
      //copy date from dyna form to the data form
      MsdsInputBean bean = new MsdsInputBean(true);
      BeanHandler.copyAttributes(form, bean);
      //MsdsInputBean bean = this.copyAttributes((MsdsInputForm)form);
/*
      if (!this.isTokenValid(request, true) &&
          ( ( (MsdsInputForm) form).getSubmitSearch() != null &&
           ( (String) ( (MsdsInputForm) form).getSubmitSearch()).length() > 0)) {
        BaseException be = new BaseException("Duplicate form submission");
        be.setMessageKey("error.submit.duplicate");
        this.saveToken(request);
        throw be;
      }
      this.saveToken(request);
*/
      request.setAttribute("millerMsdsSearchBeanCollection",
                             process.getMillerSearchResult(bean));
/*
      if ( ( (MsdsInputForm) form).getSubmitSearch() != null &&
          ( ( (MsdsInputForm) form).getSubmitSearch()).length() > 0) {

        request.setAttribute("currentMsdsLoadViewBeanCollection",
                             process.getSearchResult(bean));
      }
      else if ((((MsdsInputForm)form).getSubmitAdd() != null &&
                (((MsdsInputForm) form).getSubmitAdd()).length() > 0) ||
               (((MsdsInputForm)form).getSubmitUpdate() != null &&
                (((MsdsInputForm) form).getSubmitUpdate()).length() > 0) ||
               (((MsdsInputForm)form).getSubmitNew() != null &&
                (((MsdsInputForm) form).getSubmitNew()).length() > 0)) {
        process.processMsds(bean);
        request.setAttribute("currentMsdsLoadViewBeanCollection",
                             process.getSearchResult(bean));
      }
      else if (((MsdsInputForm) form).getSubmitDelete() != null &&
               (((MsdsInputForm) form).getSubmitDelete()).length() > 0) {
        //delete
        log.debug("deleting");
        process.deleteMsdsLocation(bean);
        request.setAttribute("currentMsdsLoadViewBeanCollection",
                              process.getSearchResult(bean));
     }
*/
    }

    return mapping.findForward("success");
  }
}
