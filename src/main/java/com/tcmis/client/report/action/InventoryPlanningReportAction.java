package com.tcmis.client.report.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.client.report.process.InventoryPlanningReportProcess;
import com.tcmis.client.report.beans.*;



public class InventoryPlanningReportAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
	  
	  PersonnelBean personnelBean = null;
		
		BigDecimal personnelId = null;
		
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "inventoryplanningreport");
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    
	personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	personnelId = new BigDecimal(personnelBean.getPersonnelId());

	if ( !personnelBean.getPermissionBean().hasUserPagePermission("businessPlanningReport") )
    {
      return (mapping.findForward("nopermissions"));
    }
	 //loading search data for page
    String companyId = ((PersonnelBean) this.getSessionObject(request, "personnelBean")).getCompanyId().toString();
    if("Radian".equalsIgnoreCase(companyId))
    {
    	companyId ="HAAS";
    }
    
    InventoryPlanningReportProcess inputProcess = new InventoryPlanningReportProcess(this.getDbUser(request),getLocale(request));
     
    	 //if form is not null we have to perform some action
    if (form != null) {
    	
    	if (log.isDebugEnabled()) {
            log.debug("form is not null");
          }
        	
        }
      
      InventoryPlanningInputBean bean = (InventoryPlanningInputBean) form;
      bean.setCompanyId(companyId);
      
      if("createReport".equalsIgnoreCase(bean.getAction()))
        {
    	  if(!"batch".equalsIgnoreCase(bean.getReportGenerationType())) {
              this.setExcel(response,"BusinessPlanReport");
          }
    	  inputProcess.runReport(bean, personnelBean, response.getOutputStream());
			return noForward;
		} 
    	  
      
      request.setAttribute("facCountyAreaColl", inputProcess.getSearchDropDown(companyId));
      
      return (mapping.findForward("success"));
      
  }
  
  
}

