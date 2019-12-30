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
import com.tcmis.client.waste.process.ManifestSearchProcess;
import com.tcmis.client.waste.beans.ManifestSearchInputBean;

/******************************************************************************
 * Controller for TDSF Report
 * @version 1.0
 ******************************************************************************/
public class ManifestSearchAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
    ManifestSearchProcess process = new ManifestSearchProcess(this.getDbUser(request));

    if (form != null) {
      this.saveTcmISToken(request);
      ManifestSearchInputBean inputBean = new ManifestSearchInputBean();
      BeanHandler.copyAttributes(form, inputBean);
      if (inputBean.getSubmitSearch() != null && inputBean.getSubmitSearch().trim().length() > 0) {
        //Pass the result collection in request
        request.setAttribute("manifestSearchCollection", process.getManifestSearchData(inputBean));
        return (mapping.findForward("showresults"));
      }
    }
    return (mapping.findForward("success"));
  }
}
