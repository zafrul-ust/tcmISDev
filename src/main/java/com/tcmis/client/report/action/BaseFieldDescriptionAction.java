package com.tcmis.client.report.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Mar 19, 2008
 * Time: 3:36:59 PM
 * To change this template use File | Settings | File Templates.
 */

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
import com.tcmis.client.report.process.ChemicalListSearchProcess;
import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.client.report.beans.ChemicalListSearchInputBean;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class BaseFieldDescriptionAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      return (mapping.findForward("login"));
    }
    GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
    request.setAttribute("baseFieldBeanCollection", genericReportProcess.getBaseFields(request.getParameter("type")));
    return mapping.findForward("success");
  }
}