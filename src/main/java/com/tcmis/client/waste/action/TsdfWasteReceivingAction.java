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
import com.tcmis.client.waste.process.TsdfWasteReceivingProcess;
import com.tcmis.client.waste.beans.TsdfWasteReceivingInputBean;

/******************************************************************************
 * Controller for TDSF Waste Receiving
 * @version 1.0
 ******************************************************************************/
public class TsdfWasteReceivingAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {


    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "tsdfwastereceivingmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }


    //populate drop downs
    //If you need access to who is logged in
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");

    TsdfWasteReceivingProcess process = new TsdfWasteReceivingProcess(this.getDbUser(request));

    if (form == null) {
      //this.saveToken(request);
    } else { //if form is not null we have to perform some action
      this.saveTcmISToken(request);
      TsdfWasteReceivingInputBean inputBean = new TsdfWasteReceivingInputBean();
      BeanHandler.copyAttributes(form, inputBean);

      //validate token
      if (((DynaBean) form).get("action") != null && "receive".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        if (!this.isTcmISTokenValid(request, true)) {
          BaseException be = new BaseException("Duplicate form submission");
          be.setMessageKey("error.submit.duplicate");
          this.saveTcmISToken(request);
          throw be;
        }
      }

      if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        //Pass the result collection in request
        request.setAttribute("tsdfWasteReceivingCollection", process.getTsdfWaste(inputBean));
        return (mapping.findForward("showresults"));
      } else if (((DynaBean) form).get("action") != null && "receive".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        Collection c = new Vector();
        DynaBean dynaBean = (DynaBean) form;
        List tsdfWasteReceivingInputBean = (List) dynaBean.get("tsdfWasteReceivingInputBean");
        if (tsdfWasteReceivingInputBean != null) {
          Iterator iterator = tsdfWasteReceivingInputBean.iterator();
          while (iterator.hasNext()) {
            TsdfWasteReceivingInputBean bean = new TsdfWasteReceivingInputBean();
            org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();
            BeanHandler.copyAttributes(lazyBean, bean);
            c.add(bean);
          }
        }
        //update
        process.receive(c,personnelBean.getPersonnelId());

        //refresh page
        //Pass search result collection in request
        request.setAttribute("tsdfWasteReceivingCollection", process.getTsdfWaste(inputBean));
        return (mapping.findForward("showresults"));

      } else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        this.setExcelHeader(response);
        //process.getExcelReport(invgrpColl,inputBean, response.getWriter(), request.getLocale());
      } else if (((DynaBean) form).get("action") != null && "start".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        //making sure user has permission to view this page
        if (!personnelBean.getPermissionBean().hasWasteLocationPermission("WasteLocManager","","","")) {
          return (mapping.findForward("nopermissions"));
        }
        request.setAttribute("wasteTsdfSourceViewBeanCollection", process.getNormalizedSearchData(personnelBean.getPersonnelId()));
        return (mapping.findForward("success"));
      }
    }

    return (mapping.findForward("success"));
  }

}
