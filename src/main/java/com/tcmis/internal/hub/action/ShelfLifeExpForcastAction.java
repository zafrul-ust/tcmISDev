package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.process.BinsToScanProcess;
import com.tcmis.internal.hub.process.LogisticsProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
     ******************************************************************************/
public class ShelfLifeExpForcastAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "shelflifeexpforcastmain");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    //populate drop downs
    LogisticsInputBean inputBean = new LogisticsInputBean();
    LogisticsProcess process = new LogisticsProcess(this.getDbUser(request),this.getTcmISLocale(request));
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
    if (!personnelBean.getPermissionBean().hasUserPagePermission("shelfLifeForcast"))
    {
      return (mapping.findForward("nopermissions"));
    }

    if (form == null) {
    	return (mapping.findForward("success"));
    }

    BeanHandler.copyAttributes(form, inputBean);
    inputBean.setLotStatus(request.getParameterValues("lotStatus"));
    String action =  (String)( (DynaBean) form).get("uAction");			//
    if ( "search".equals(action) ) {
    	request.setAttribute("beanCollection", process.getExpiredInventory(inputBean,personnelBean));
    }
    else if ( "createExcel".equals(action) ) {
    	try {
			this.setExcel(response,"ShelfLifeExpForcast");
			process.getShelfLifeExpReportExcel(inputBean,personnelBean).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    }
    else if ( "loaddata".equals(action) ) {
    	request.setAttribute("bins", process.loadData(inputBean));
        return (mapping.findForward("loaddata"));
    }
    return (mapping.findForward("success"));
  }
}
