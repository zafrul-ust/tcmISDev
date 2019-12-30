package com.tcmis.client.catalog.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.beans.OrderTrackDetailHeaderViewBean;
import com.tcmis.client.catalog.process.MrAllocationReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.distribution.beans.JdeOrderStageBean;

public class MrAllocationReportAction extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    /*if (!this.isLoggedIn(request)) {
     request.setAttribute("requestedPage", "showmrallocationreport");
     request.setAttribute("requestedURLWithParameters",
     this.getRequestedURLWithParameters(request));
     return (mapping.findForward("login"));
      }*/

    if (request.getParameter("mrNumber") == null && (request.getParameter("fromJDE")  != null) && request.getParameter("jdeOrderNo") == null) {
      return mapping.findForward("systemerrorpage");
    }else {
      String companyId = null;
	  String mrNumber = null;
	  String lineItem = null;
	  String fromCustomerOrdertracking = request.getParameter("fromCustomerOrdertracking");
      PersonnelBean user = null;
      
      MrAllocationReportProcess mrAllocationReportProcess = new MrAllocationReportProcess(this.getDbUser(request),getTcmISLocaleString(request));
      
      if (request.getParameter("fromJDE")  != null  && request.getParameter("fromJDE").equals("Y")){
    	// The user ID could change. Check permission every access.
    	  //if (!this.isLoggedIn(request)) {
    		    user = new PersonnelBean();
    		    user.setCompanyId(this.getClient(request));
				if (request.getParameter("userId") == null || request.getParameter("userId").length() == 0) {
					return mapping.findForward("nopermissions");
				}
				user.setLogonId(request.getParameter("userId").toLowerCase());
				user.setCompanyId("Radian"); 
				LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
				user = loginProcess.loginWeb(user, false);
            	user.setDbUser(this.getDbUser(request));
				
				if (!user.getPermissionBean().hasOpsEntityPermission("jdeLogin", "", "Radian"))
				{
					return (mapping.findForward("nopermissions"));
				} 
	            else{
	            	user.setOpsHubIgOvBeanCollection(loginProcess.getOpsHubIgData(user));
	            	this.setSessionObject(request, user, "personnelBean");
	            }
			/*}
			else 
				user = (PersonnelBean)this.getSessionObject(request,"personnelBean");*/
    	  
    	  Collection jdeOrders = mrAllocationReportProcess.getJdeOrderInfo(new BigDecimal(request.getParameter("jdeOrderNo")));
    	  
    	  if(jdeOrders != null && jdeOrders.size() > 0) {
	    	  JdeOrderStageBean jdeOrderInfo = (JdeOrderStageBean) jdeOrders.iterator().next();
	    	  
	    	  companyId = jdeOrderInfo.getCompanyId();
		      mrNumber = jdeOrderInfo.getPrNumber().toString();
    	  }
    	  else
    		  return mapping.findForward("systemerrorpage");
      }
      else{
	      companyId = request.getParameter("companyId");
	      mrNumber = request.getParameter("mrNumber");
	      lineItem = request.getParameter("lineItem");
		  user = (PersonnelBean) getSessionObject(request, "personnelBean");
      }

      //if form is not null we have to perform some action
      if (form != null) {
        //check what button was pressed and determine where to go
        if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          request.setAttribute("companyId", companyId);
          request.setAttribute("mrNumber", mrNumber);
          request.setAttribute("lineItem", lineItem);
          request.setAttribute("fromCustomerOrdertracking", fromCustomerOrdertracking);

          OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean = new OrderTrackDetailHeaderViewBean();
          orderTrackDetailHeaderViewBean = mrAllocationReportProcess.getOrderTrackDetailHeaderViewBean(companyId,mrNumber);
          request.setAttribute("orderTrackDetailHeaderViewBean", orderTrackDetailHeaderViewBean);
 
          return (mapping.findForward("showsearch"));
          
        } else if (((DynaBean) form).get("action") != null && "result".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          Collection c = mrAllocationReportProcess.getMrAllocationReport(companyId,mrNumber,lineItem,fromCustomerOrdertracking);
  	Object[] results = mrAllocationReportProcess.getExtraInfo(c);
  	request.setAttribute("qualityIdLabelColumnHeader", results[0]);
  	request.setAttribute("catPartAttrColumnHeader", results[1]);

          request.setAttribute("pkgOrderTrackWebPrOrderTrackDetailBeanCollection", c);
          
          if (fromCustomerOrdertracking != null && "Y".equalsIgnoreCase(fromCustomerOrdertracking))
        	  return (mapping.findForward("showcustomerresults"));
          else
        	  return (mapping.findForward("showresults"));
          
        } else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        	this.setExcel(response,"MrAllocation");
          if (fromCustomerOrdertracking != null && "Y".equalsIgnoreCase(fromCustomerOrdertracking))
        	  mrAllocationReportProcess.getExcelReportfromCustomerOrdertracking(companyId,mrNumber,lineItem,(java.util.Locale)request.getSession().getAttribute("tcmISLocale"),fromCustomerOrdertracking).write(response.getOutputStream());
          else {
        	  mrAllocationReportProcess.getExcelReport(companyId,mrNumber,lineItem,(java.util.Locale)request.getSession().getAttribute("tcmISLocale"),fromCustomerOrdertracking,user).write(response.getOutputStream());
          }
          return noForward;
        }
      } else {
        request.setAttribute("companyId", companyId);
        request.setAttribute("mrNumber", mrNumber);
        request.setAttribute("lineItem", lineItem);
        request.setAttribute("fromCustomerOrdertracking", fromCustomerOrdertracking);
      }
      return mapping.findForward("success");
    }
  } //end of method
} //end of class
