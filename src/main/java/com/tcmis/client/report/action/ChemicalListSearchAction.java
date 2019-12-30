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
import com.tcmis.client.report.process.ChemicalListSearchProcess;
import com.tcmis.client.report.beans.ChemicalListSearchInputBean;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class ChemicalListSearchAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      return (mapping.findForward("login"));
    }


      String listId = request.getParameter("listId");
      String listName = request.getParameter("listName");
      ChemicalListSearchProcess chemicalListSearchProcess = new ChemicalListSearchProcess(this.getDbUser(request));
      Collection listBeanCollection = chemicalListSearchProcess.getCASForList(listId);
      request.setAttribute("listBeanCollection", listBeanCollection);
      request.setAttribute("listId",listId);
      request.setAttribute("listName",listName);


    this.saveTcmISToken(request);

    return mapping.findForward("success");
  }
}