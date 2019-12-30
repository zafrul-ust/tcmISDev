package com.tcmis.client.catalog.action;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.client.catalog.process.PackageStyleSearchProcess;
import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.VvPkgStyleBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for Package Style Search page
 * @version 1.0
 ******************************************************************************/

public class PackageStyleSearchAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request))
		{
			request.setAttribute("requestedPage", "packagestylesearchmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    

		String userAction = request.getParameter("uAction");			
		if (form == null || userAction == null)
		{    	 
			return (mapping.findForward("success"));
		}

	  	if ( userAction.equals("search") )
		{
            DynaBean dynaForm = (DynaBean) form;
			String searchArgument = (String) dynaForm.get("searchArgument");
			PackageStyleSearchProcess process = new PackageStyleSearchProcess(this.getDbUser(request));
			request.setAttribute("packageStyleBeanCollection", process.getPackageStyleBeanCollection( searchArgument));
            return (mapping.findForward("success"));
        }else if (userAction.equals("autoCompleteSearch")) {
            PackageStyleSearchProcess process = new PackageStyleSearchProcess(this.getDbUser(request));
            //copy date from dyna form to the data bean
            AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
            BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

            Collection<VvPkgStyleBean> results =  process.getPackageStyleBeanCollection(inputBean);

           // Write out the data
           response.setCharacterEncoding("utf-8");	  
           PrintWriter out = response.getWriter();
           // status needs to be in the third position or just id:name with no statue
           // If we need to add different values, please leave the third one empty like id:name::something else
            for(VvPkgStyleBean bean: results)
            out.println(bean.getPkgStyle());
            out.close();
            return noForward;
        }
        return (mapping.findForward("success"));
    }
}
