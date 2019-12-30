package com.tcmis.internal.hub.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.*;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
     ******************************************************************************/
public class CabinetLabelAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showcabinetlabel");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    //populate drop downs
    CabinetLabelProcess process = new CabinetLabelProcess(this.getDbUser(request));
    request.setAttribute("hubCabinetViewBeanCollection",
                         process.getAllLabelData( (PersonnelBean)this.
        getSessionObject(request, "personnelBean")));

    if (form == null) {
      
    }
    //if form is not null we have to perform some action
    else if (((DynaBean) form).get("submitSearch") != null && 
             ((String)((DynaBean) form).get("submitSearch")).length() > 0) {

      //if (!this.isTokenValid(request, true)) {
      //  BaseException be = new BaseException("Duplicate form submission");
      //  be.setMessageKey("error.submit.duplicate");
      //  throw be;
      //}
      

      //copy date from dyna form to the data form
      CabinetLabelInputBean bean = new CabinetLabelInputBean();
      BeanHandler.copyAttributes(form, bean);
      request.setAttribute("searchResultBeanCollection",
                           process.getSearchData(bean));
    }


    return (mapping.findForward("success"));
  }
}
