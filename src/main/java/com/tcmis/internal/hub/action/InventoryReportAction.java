package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.process.BinsToScanProcess;
import com.tcmis.internal.hub.process.InventoryReportProcess;

/******************************************************************************
 * Controller for InventoryReport
 * @version 1.0
     ******************************************************************************/
public class InventoryReportAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "inventoryreportmain");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    PermissionBean permmissionBean = personnelBean.getPermissionBean();
    if ( !permmissionBean.hasUserPagePermission("inventoryByReceipt") )
    {
      return (mapping.findForward("nopermissions"));
    }

    //populate drop downs
    LogisticsInputBean inputBean = new LogisticsInputBean();
    InventoryReportProcess iRProcessrocess = new InventoryReportProcess(this.getDbUser(request),this.getTcmISLocale(request));
	VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
	request.setAttribute("vvLotStatusBeanCollection", vvDataProcess.getActiveVvLotStatus());
    if (form == null) {

    	BinsToScanProcess process1 = new BinsToScanProcess(this.getDbUser(request));
		request.setAttribute("hubRoomOvBeanCollection", process1.getDropDownData(((PersonnelBean) this
						.getSessionObject(request, "personnelBean")).getHubInventoryGroupOvBeanCollection()));
    	return (mapping.findForward("success"));
    }

    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
    inputBean.setLotStatus(request.getParameterValues("lotStatus"));
    String action =  (String)( (DynaBean) form).get("uAction");			//
    if ( "search".equals(action) ) {
    	request.setAttribute("beanCollection", iRProcessrocess.getInventoryReport(inputBean,personnelBean));
    }
    else if ( "createExcel".equals(action) ) {
    	
    	try {
			this.setExcel(response,"InventoryReport");
			iRProcessrocess.getInventoryReportExcel(iRProcessrocess.getInventoryReport(inputBean,personnelBean),personnelBean).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    }
    else if ( "loaddata".equals(action) ) {
    	request.setAttribute("vvLotStatusLegend", iRProcessrocess.getLotStatusLegend());
        return (mapping.findForward("loaddata"));
    }
    return (mapping.findForward("success"));
  }
}
