package com.tcmis.internal.hub.action;

import java.util.Collection;
import javax.servlet.http.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryInputBean;
import com.tcmis.internal.hub.process.CabinetInventoryProcess;
import com.tcmis.internal.hub.process.CabinetManagementProcess;

/******************************************************************************
 * Controller for Cabinet Inventory
 * @version 1.0
     **************************************************************************/

public class CabinetInventoryAction extends TcmISBaseAction

{
	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "cabinetinventorymain");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("cabinetInventory"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }
    
	 	    String forward = "success"; 
			CabinetInventoryInputBean inputBean = new CabinetInventoryInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
          	  
			if  ( ( (DynaBean) form).get("action") != null && ( (String) ( (DynaBean) form).get("action")).equals( "search"))     		  
			{    	 
				CabinetInventoryProcess cabinetInventoryProcess = new CabinetInventoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
    	 
				Collection cabinetInventoryBeanCollection = cabinetInventoryProcess.getCabinetInventoryBeanCollection(inputBean);
				// log.debug("cabinetInventoryBeanCollection.size() = " + cabinetInventoryBeanCollection.size() + ";"); 
        
				request.setAttribute("cabinetInventoryBeanCollection", cabinetInventoryBeanCollection);
				        
			}
			else
			if  (   ( (DynaBean) form).get("action") != null && ( (String) ( (DynaBean) form).get("action")).equals( "createExcel")) 
			{        
				try {
					CabinetInventoryProcess cabinetInventoryProcess = new CabinetInventoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
					this.setExcel(response,"CabinetInventory");
					cabinetInventoryProcess.createExcelFile(inputBean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale"),personnelBean).write(response.getOutputStream());
				}
				catch (Exception ex) {
				 ex.printStackTrace();
				 return mapping.findForward("genericerrorpage");
				}
				return noForward;
			}
			else
			{
				//populate drop downs
				CabinetManagementProcess cabmgtProcess = new CabinetManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));

				request.setAttribute("hubCabinetViewBeanCollection",
	                         cabmgtProcess.getAllLabelData( (PersonnelBean)this.getSessionObject(request, "personnelBean")));
				  	  
			}
		
  	  
		return (mapping.findForward(forward));
  }

}
