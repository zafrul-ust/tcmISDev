package com.tcmis.internal.hub.action;

import java.util.Collection;
import javax.servlet.http.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetManagementInputBean;
import com.tcmis.internal.hub.process.CabinetManagementProcess;

/******************************************************************************
 * Controller for cabinet definition
 * @version 1.0
     ******************************************************************************/

public class CabinetDefinitionAction

    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws

      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "cabinetdefinitionmain");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    //populate drop downs
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    if (!personnelBean.getPermissionBean().hasUserPagePermission("cabinetDefinitions"))
    {
      return (mapping.findForward("nopermissions"));
    }
    
    if (form == null) 
        return (mapping.findForward("success"));
    String action = (String) ( (DynaBean) form).get("userAction");
    CabinetManagementProcess cabmgtProcess = new CabinetManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
    if( action == null || action.length() == 0 ) {
        request.setAttribute("hubCabinetViewBeanCollection",
                               cabmgtProcess.getAllLabelData( (PersonnelBean)this.getSessionObject(request, "personnelBean")));
        return (mapping.findForward("success"));
    }
     {
      CabinetManagementInputBean inputBean = new CabinetManagementInputBean();
      BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
      //If the search button was pressed the getSubmitSearch() value will be not null

/* 		java.util.Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()) 
		{
			String name = (String) e.nextElement();
			System.out.println(name +":"+request.getParameter(name)+":");
		}
*/
      if ( action.equalsIgnoreCase("createExcel")) {
			try {
				this.setExcel(response,"CabinetManagement");
				cabmgtProcess.getExcelReport(inputBean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			}
			catch (Exception ex) {
			 ex.printStackTrace();
			 return mapping.findForward("genericerrorpage");
			}
			return noForward;
        }
      else {
        Collection workAreas = cabmgtProcess.getCabinetDefinitions(inputBean);
        //Pass the result collection in request
        request.setAttribute("cabinetDefinitionColl", workAreas);
      }
      // create excel
    }
    return (mapping.findForward("success"));

  }

}
