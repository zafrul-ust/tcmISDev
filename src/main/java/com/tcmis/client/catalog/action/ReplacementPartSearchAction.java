package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.process.ReplacementPartSearchProcess;
import com.tcmis.client.catalog.beans.ReplacementPartSearchInputBean;
import com.tcmis.client.catalog.beans.FacItemCpigUseApprovalBean;

/******************************************************************************
 * Controller for Replacement Part Search page
 * @version 1.0
 ******************************************************************************/

public class ReplacementPartSearchAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "replacementpartsearchmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		// Get the user
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
		
		String userAction = request.getParameter("uAction");
		if (form == null || userAction == null)
		{    	 
			return (mapping.findForward("success"));
		}

		ReplacementPartSearchInputBean inputBean = new ReplacementPartSearchInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		ReplacementPartSearchProcess process = new ReplacementPartSearchProcess(this.getDbUser(request));
		if ( userAction.equals("search") ) {
			Vector<FacItemCpigUseApprovalBean> c = (Vector<FacItemCpigUseApprovalBean>) process.getReplacementPartBeanCollection(inputBean, personnelBean);
			Object[] results = process.createRelationalObjects(c);
			request.setAttribute("replacementPartBeanCollection", results[0]);
			request.setAttribute("rowCountPart", results[1]);
			request.setAttribute("rowCountItem", results[2]);

		}else if (userAction.equals("createExcel")) {
			this.setExcel(response, "ReplacementPartSearch");
			try {
				process.getExcelReport(inputBean, personnelBean).write(response.getOutputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}

		return (mapping.findForward("success"));
    }
  
}
