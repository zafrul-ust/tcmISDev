package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.*;

import org.apache.struts.action.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.MsdsSearchInputBean;
import com.tcmis.client.common.process.MsdsSearchProcess;

/******************************************************************************
 * Controller for Search for MSDS page
 * @version 1.0
 ******************************************************************************/

public class MsdsSearchAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception {
		
		 if (!this.isLoggedIn(request))	{
				request.setAttribute("requestedPage", "msdssearchmain");
				request.setAttribute("requestedURLWithParameters",
						this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
		}
		 
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	    
	    if (!personnelBean.getPermissionBean().hasUserPagePermission("msdsSearch"))
	    {
	      return (mapping.findForward("nopermissions"));
	    }
		
		if (form == null ) {
			return (mapping.findForward("success"));
		}

		MsdsSearchInputBean bean = new MsdsSearchInputBean();
		BeanHandler.copyAttributes(form, bean);
		String userAction = bean.getuAction();
        MsdsSearchProcess msdsSearchProcess = new MsdsSearchProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
        if ( "search".equals(userAction)) {
        	request.setAttribute("msdsSearchBeanCollection", msdsSearchProcess.searchMsds(bean));
        }
        else{
        	
        	request.setAttribute("msdsSearchCompanyColl", msdsSearchProcess.getMsdsCompanyColl());
        }
        
        

		return (mapping.findForward("success"));
    }
  
}

