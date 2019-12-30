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
import com.tcmis.internal.supply.beans.PoEmailViewBean;

import com.tcmis.internal.supply.process.PoEmailProcess;
//import com.tcmis.internal.supply.beans.POTextViewBean;

/*****************************************************************************
 * Controller for Purchase Order Email Notes page
 * @version 1.0
 ******************************************************************************/

public class PoEmailAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "poemailmain");
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
		
		PoEmailProcess process = new PoEmailProcess(this.getDbUser(request));
				
		if (phase.equals("init") )
		{
			Collection pOTextViewBeanCollection = process.getPOTextBeanCollection( pONumber );
	             
			request.setAttribute("pOTextViewBeanCollection", pOTextViewBeanCollection);

			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		} 
      
		if (phase.equals("AddNote")) 
		{  
			PoEmailViewBean insertBean = new PoEmailViewBean();
			
			BeanHandler.copyAttributes(form, insertBean);
			insertBean.setRadianPo( new BigDecimal( pONumber ) );
			insertBean.setUserId( personnelId );
			
			process.insertPoEmail( insertBean );    
			
			Collection pOTextViewBeanCollection = process.getPOTextBeanCollection( pONumber );            
			request.setAttribute("pOTextViewBeanCollection", pOTextViewBeanCollection);			
		}      		
		return (mapping.findForward("success"));
    } 
}
