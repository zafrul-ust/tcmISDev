package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.InvLevelManagementInputBean;
import com.tcmis.internal.hub.process.InvLevelManagementProcess;

/******************************************************************************
 * Controller for InvLevelManagement
 * @version 1.0
     ******************************************************************************/

public class InvLevelManagementAction extends TcmISBaseAction

{
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws

      BaseException, Exception 
  {
    if (!this.isLoggedIn(request,true)) 
    {
      request.setAttribute("requestedPage", "invlevelmanagementmain");
	  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    
    if (!personnelBean.getPermissionBean().hasUserPagePermission("levelManagement"))
    {
      return (mapping.findForward("nopermissions"));
    }
    
    if (form != null) 
    {         	  
    	if  ( ( (DynaBean) form).get("uAction") != null && ( (String) ( (DynaBean) form).get("uAction")).equals( "search"))     		  
    	{    	 
        	InvLevelManagementInputBean inputBean = new InvLevelManagementInputBean();
        	BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
    		inputBean.setPersonnelId(personnelId);
    		InvLevelManagementProcess invLevelManagementProcess = new InvLevelManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
    	 
    		Collection invLevelManagementBeanCollection = invLevelManagementProcess.getInvLevelManagementBeanCollection(inputBean);
        
    		request.setAttribute("invLevelManagementBeanCollection", invLevelManagementBeanCollection);
    		return (mapping.findForward("success"));        
    	}
    	else
    	if  (   ( (DynaBean) form).get("uAction") != null && ( (String) ( (DynaBean) form).get("uAction")).equals( "createExcel")) 
    	{ 
    		this.setExcel(response,"InvLevelManagement");
        	InvLevelManagementInputBean inputBean = new InvLevelManagementInputBean();
        	BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
    		inputBean.setPersonnelId(personnelId);
            
            InvLevelManagementProcess invLevelManagementProcess = new InvLevelManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
        
    		invLevelManagementProcess.createExcelFile(inputBean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
    		return noForward;
    	}
    	else
  	  	{
    		//populate drop downs
    		Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
    		return (mapping.findForward("success"));   	  
  	  	}
    }
   
    return (mapping.findForward("success"));
  }

}
