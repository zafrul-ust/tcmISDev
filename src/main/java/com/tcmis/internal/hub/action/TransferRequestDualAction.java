package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.TransferRequestProcess;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
     ******************************************************************************/
public class TransferRequestDualAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

//login
	if (!this.isLoggedIn(request)) {
		  request.setAttribute("requestedPage", "transferrequestmain");
		  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
		  return (mapping.findForward("login"));	
    }	

	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	if (!personnelBean.getPermissionBean().hasUserPagePermission("transferRequest"))
	{
	   return (mapping.findForward("nopermissions"));
	}
//main
	if( form == null )
		return (mapping.findForward("success"));

//result
    TransferRequestProcess process = new TransferRequestProcess(this.getDbUser(request),getTcmISLocaleString(request));
    TransferRequestInputBean bean = new TransferRequestInputBean();
//no locale dependency stuff
    BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

    //if form is not null we have to perform some action
    if (     ( (DynaBean) form).get("uAction") != null &&
    		( (String) ( (DynaBean) form).get("uAction")).equalsIgnoreCase("search")){

      request.setAttribute("transferableInventoryViewBeanCollection", process.getSearchData(bean, personnelBean));
      request.setAttribute("sourceInventoryGroup", process.getSourceInventoryGroup(bean));
      //request.setAttribute("hubInventoryGroupOvBeanCollection", process.getTransferToDropdown( request.getParameter("opsEntityId")));
      saveTcmISToken(request);
    }
    else if (( (DynaBean) form).get("uAction") != null &&
             ( (String) ( (DynaBean) form).get("uAction")).equalsIgnoreCase("transfer")){
      checkToken(request);
      DynaBean dynaBean = (DynaBean) form;
//contains locale dependent inputs.
      Vector c = (Vector) BeanHandler.getBeans(dynaBean, "transferRequestInputBean",new TransferableInventoryViewBean(),getTcmISLocale(request));
      Object[] objs = process.transfer(c,personnelBean);
      request.setAttribute("errorCollection", objs[0]);
      request.setAttribute("errorMsgCollection", objs[1]);
      request.setAttribute("sucessfulTransferColl", objs[2]);
      request.setAttribute("doneTransfer", "Y");
      request.setAttribute("transferableInventoryViewBeanCollection", process.getSearchData(bean, personnelBean));
      request.setAttribute("sourceInventoryGroup", process.getSourceInventoryGroup(bean));
      //request.setAttribute("hubInventoryGroupOvBeanCollection", process.getTransferToDropdown(request.getParameter("opsEntityId")));
    }
    else if ( ( (DynaBean) form).get("uAction") != null &&
            ( (String) ( (DynaBean) form).get("uAction")).equalsIgnoreCase("createExcel")){
   	     this.setExcel(response, "TransferrequestExcel");
        process.getExcelReport(process.getSearchData(bean, personnelBean), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        return noForward;
   }
    else
    {
        request.setAttribute("destinationHubInvGroupOvBeanColl", process.getTransferToDropdown());
    }
//serach
    return mapping.findForward("success");
  }
}

