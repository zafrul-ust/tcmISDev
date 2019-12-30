package com.tcmis.internal.supply.action;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.internal.hub.process.AllocationAnalysisProcess;
import com.tcmis.internal.supply.beans.*;
import com.tcmis.internal.supply.process.*;

import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for showing surplus
 * @version 1.0
     ******************************************************************************/
public class SurplusInventoryAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showbuyorders");
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    //if form is not null we have to perform some action
    if (form == null) {
    }
    else {
      SurplusInventoryProcess process = new SurplusInventoryProcess(this.getDbUser(request));
      request.setAttribute("surplusInventoryViewBeanCollection",
                           process.getSurplusData(new BigDecimal((String)((DynaBean)form).get("itemId"))));
    }

    return mapping.findForward("success");
  }
}