package com.tcmis.internal.hub.action;
import java.io.File;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
//import org.apache.struts.validator.LazyValidatorActionForm;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.PassThroughReportInputBean;
import com.tcmis.internal.hub.process.PassThroughReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Map;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	 ******************************************************************************/
public class PassThroughReportAction
	extends TcmISBaseAction 
{
   public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws BaseException, Exception 
  {
	//log.debug("* >>--> mapping.getPath() = [" + mapping.getPath() + "]" );
	  
	if (!this.isLoggedIn(request)) 
	{
		request.setAttribute("requestedPage", "passthroughreportmain");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
		return (mapping.findForward("login"));
	}


	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
	 "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("passthroughReport"))
    {
      return (mapping.findForward("nopermissions"));
    }
	
	if (form == null) 
	{
	  	request.setAttribute("igBilledIssuesViewBeanCollection", null);
		return mapping.findForward("success");	  	
	}
	
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	
	PassThroughReportProcess passThroughReportProcess = new
	 PassThroughReportProcess(this.getDbUser(request),getTcmISLocaleString(request));

	//copy date from dyna form to the data form	
	PassThroughReportInputBean bean = new PassThroughReportInputBean();
	BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

	Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
	String userAction = (String)( (DynaBean) form).get("userAction");
	// if (bean.getSubmitSearch() != null &&
	//	  bean.getSubmitSearch().trim().length() > 0) 
	if (userAction != null && userAction.equals("search") ) 		 				
	{
		//log.debug("processing search form submission...");
		Collection c = passThroughReportProcess.getSearchResult(bean,hubInventoryGroupOvBeanCollection);
		//log.debug("igBilledIssuesViewBeanCollection.size() = " + c.size() + ";"); 

		request.setAttribute("igBilledIssuesViewBeanCollection", c);
		return (mapping.findForward("success"));
	}
	//if (bean.getButtonCreateExcel() != null &&
	//		   bean.getButtonCreateExcel().trim().length() > 0) 
	// if ( mapping.getPath().equals("/passthroughreportexcel") )		
	else if (userAction != null && userAction.equals("createExcel") ) 		 		
	{
		Collection c = passThroughReportProcess.getSearchResult(bean,hubInventoryGroupOvBeanCollection);
		this.setExcel(response,"PassThroughReport");
		passThroughReportProcess.createExcelFile(c, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		return noForward;		
/*		
		Collection c = passThroughReportProcess.getSearchResult(bean,hubInventoryGroupOvBeanCollection);
		ResourceLibrary resource = new ResourceLibrary("report");
		File dir = new File(resource.getString("excel.report.serverpath"));
		File file = File.createTempFile("passThroughReport_excel", ".xls", dir);
		passThroughReportProcess.writeExcelFile( c,	file.getAbsolutePath(),personnelBean.getLocale());

		this.setSessionObject(request,
							  resource.getString("report.hosturl") +
							  resource.getString("excel.report.urlpath") +
							  file.getName(), "filePath");
		request.setAttribute("doexcelpopup", "Yes");
		return (mapping.findForward("viewfile"));  */
	}
  	request.setAttribute("igBilledIssuesViewBeanCollection", null);
	return mapping.findForward("success");
  }
}
