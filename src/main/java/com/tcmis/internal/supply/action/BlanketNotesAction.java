package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.POTextInputBean;
import com.tcmis.internal.supply.beans.BpoNotesViewBean;

import com.tcmis.internal.supply.process.BlanketNotesProcess;
//import com.tcmis.internal.supply.beans.POTextViewBean;

/******************************************************************************
 * Controller for Blanket Purchase Order Notes page
 * @version 1.0
 ******************************************************************************/

public class BlanketNotesAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "blanketnotesmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		String personnelName = personnelBean.getLastName();
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		/*
     	if (! permissionBean.hasFacilityPermission("POText", null, null)) 
     	{
        	return mapping.findForward("nopermissions");
     	}
		*/
		String phase = request.getParameter("phase");
		String pONumber = request.getParameter("pONumber");
		
    	request.setAttribute("phase", phase );		
    	request.setAttribute("pONumber", pONumber );		
		
		if (form == null) 
		{  	    	
			return (mapping.findForward("success"));
		}
		
		BlanketNotesProcess process = new BlanketNotesProcess(this.getDbUser(request));
				
		if (phase.equals("init") )
		{
			Collection pOTextViewBeanCollection = process.getPOTextBeanCollection( pONumber );
			//log.debug("pOTextViewBeanCollection.size() = [" + pOTextViewBeanCollection.size() + "]; ");
	             
			request.setAttribute("pOTextViewBeanCollection", pOTextViewBeanCollection);

			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		} 
      
		if (phase.equals("AddNote")) 
		{  
			BpoNotesViewBean insertBean = new BpoNotesViewBean();
			
			BeanHandler.copyAttributes(form, insertBean);
			insertBean.setBpo( new BigDecimal( pONumber ) );
			insertBean.setUserId( personnelId );
			
			process.insertPoNotes( insertBean );    
			
			Collection pOTextViewBeanCollection = process.getPOTextBeanCollection( pONumber );            
			request.setAttribute("pOTextViewBeanCollection", pOTextViewBeanCollection);			
		}      		
		return (mapping.findForward("success"));
    } 
}
