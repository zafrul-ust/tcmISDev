package com.tcmis.internal.hub.action;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.ShipmentBean;
import com.tcmis.internal.hub.beans.ShipmentHistoryInputBean;
import com.tcmis.internal.hub.process.ShipmentHistoryProcess;


public class ShipmentHistoryAction extends TcmISBaseAction{
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "shipmenthistorymain");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request,"personnelBean");
	if (!personnelBean.getPermissionBean().hasUserPagePermission("shipmentHistory"))
	{
	   return (mapping.findForward("nopermissions"));
	}

    if( form == null ) return mapping.findForward("success");
    String uAction = (String) ( (DynaBean)form).get("uAction");
	ShipmentHistoryProcess process = new ShipmentHistoryProcess(getDbUser(request),getTcmISLocaleString(request));
	
	if( uAction == null ) {
	    	request.setAttribute("hubPrefViewOvBeanCollection",process.getDropDownData(personnelBean.getPersonnelId()));	    	
	    	return mapping.findForward("success");
	}

    ShipmentHistoryInputBean bean = new ShipmentHistoryInputBean();
    BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

    if( uAction.equals("search") ) {
    	saveTcmISToken(request);
    }
    else if( uAction.equals("update") ) {
    	checkToken(request);
    	process.updateShipping(BeanHandler.getBeans((DynaBean)form, "shipmentBean", new ShipmentBean(), getTcmISLocale(request)));
    }
    else if( uAction.equals("createExcel") ) {
		try {
			this.setExcel(response,"ShipHistory");
			process.getExcelReport(personnelBean,bean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    }
    Object[] result = process.getSearchResult(personnelBean,bean,null);
    request.setAttribute("shipmentHistoryViewBeanCollection", result[0]);
    request.setAttribute("carrierInfoCollection", result[1]);
    request.setAttribute("carrierInfoALL", result[2]);
    request.setAttribute("motIncotermCollection",process.getIncotermForModeOfTransport());
    request.setAttribute("showProForma", result[3]);
    return mapping.findForward("success");
  }
}