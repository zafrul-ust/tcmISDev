package com.tcmis.client.report.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.report.process.*;
import com.tcmis.client.report.beans.*;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class ManagementOptionSearchAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      return (mapping.findForward("login"));
    }

    //if form is not null we have to perform some action
    if (form != null) {
      //copy date from dyna form to the data form
      ProfileSearchInputBean bean = new ProfileSearchInputBean();
      BeanHandler.copyAttributes(form, bean);

      if (bean.getSearchButton() != null) {
        GenericReportProcess process = new GenericReportProcess(this.getDbUser(request),getLocale(request));
        request.setAttribute("vvWasteManagementOptionBeanCollection", process.getManagementOptions(bean));
      }

      this.saveTcmISToken(request);
    }
    return mapping.findForward("success");
  }
}