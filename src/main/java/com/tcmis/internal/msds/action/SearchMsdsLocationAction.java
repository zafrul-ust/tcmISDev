package com.tcmis.internal.msds.action;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.internal.report.beans.ErrorBean;

import com.tcmis.internal.msds.beans.*;
import com.tcmis.internal.msds.process.*;

/******************************************************************************
 *
 * @version 1.0
     ******************************************************************************/
public class SearchMsdsLocationAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "searchmsdslocation");
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("showlogin"));
    }
    PersonnelBean pBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		PermissionBean permissionBean = pBean.getPermissionBean();
    if(!permissionBean.hasFacilityPermission("MillerUpdateMsds", "All",null)) {
      BaseException be = new BaseException("No permission");
      be.setMessageKey("error.generic.permission");
      throw be;
    }
    //since I'm redoing the search if there is an error I need to relay the error bean
    if(request.getAttribute("errorBean") != null) {
      request.setAttribute("errorBean", request.getAttribute("errorBean"));
    }
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

    //if form is not null we have to perform some action
    if (form != null) {
      try {
        //copy date from dyna form to the data form
        MsdsInputBean bean = new MsdsInputBean(true);
        BeanHandler.copyAttributes(form, bean);
        //MsdsInputBean bean = this.copyAttributes((MsdsInputForm)form);
/*
        if (!this.isTokenValid(request, true)) {
          BaseException be = new BaseException("Duplicate form submission");
          be.setMessageKey("error.submit.duplicate");
          this.saveToken(request);
          throw be;
        }
        this.saveToken(request);
*/
        request.setAttribute("currentMsdsLoadViewBeanCollection",
                             process.getSearchResult(bean));
      }
      catch(GeneralException ge) {
        BaseException bex = new BaseException();
        bex.setMessageKey("error.generic");
        //since I want to display the oracle error I need to add it to the request
        ErrorBean errorBean = new ErrorBean();
        errorBean.setCause(ge.getMessage());
        request.setAttribute("errorBean", errorBean);
        throw bex;
      }
      catch(BaseException be) {
        be.setMessageKey("error.input.invalid");
        throw be;
      }
    }

    return mapping.findForward("showinput");
  }
}
