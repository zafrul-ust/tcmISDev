package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;

import java.util.Collection;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.BinsToScanProcess;


public class BinsToScanAction extends TcmISBaseAction{
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
//login
	if (!this.isLoggedIn(request)) {
		  request.setAttribute("requestedPage", "binstoscanmain");
		  request.setAttribute("requestedURLWithParameters",
		  this.getRequestedURLWithParameters(request));
		  return (mapping.findForward("login"));
    }
	
	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("binsToScan"))
    {
      return (mapping.findForward("nopermissions"));
    }

//main
    if( form == null )
		return (mapping.findForward("success"));
   
    String uAction = (String) ( (DynaBean)form).get("userAction");
	BinsToScanProcess process = new BinsToScanProcess(this.getDbUser(request),getTcmISLocaleString(request));
	BinsToScanInputBean inputBean = new BinsToScanInputBean();
	inputBean.setDoTrim(true);
	BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
//result
    if ( ((DynaBean) form).get("submitSearch") != null && 
      ((String)((DynaBean) form).get("submitSearch")).length() > 0) {
    	request.setAttribute("binsToScanViewBeanCollection", process.getSearchData(inputBean));
    } else if ("createExcel".equals(uAction)) {		
		Collection c =  process.getSearchData(inputBean);
		try {
			this.setExcel(response,"Bins to Scan");
			process.getExcelReport(c,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}return noForward;	
	}
//  search
    else {
  		request.setAttribute("hubRoomOvBeanCollection", process.getDropDownData(((PersonnelBean)this.getSessionObject(request, "personnelBean")).getHubInventoryGroupOvBeanCollection()));
    }
	return (mapping.findForward("success"));
  }
}