package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.SearchPoInputBean;
import com.tcmis.internal.supply.process.SearchPurchaseOrderProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class SearchPurchaseOrderAction extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "searchposmain");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	SearchPurchaseOrderProcess searchPoProcess = new SearchPurchaseOrderProcess(this.getDbUser(request),getTcmISLocaleString(request));
	VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocaleString(request));

         //If you need access to who is logged in
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    
//	Collection invgrpColl = personnelBean.getHubInventoryGroupOvBeanCollection();
//    request.setAttribute("hubColl",invgrpColl);
    
	String action = request.getParameter("uAction");
	String searchFromPopup = request.getParameter("searchFromPopup");
          //copy date from dyna form to the data bean
          SearchPoInputBean inputBean = new SearchPoInputBean();
          BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

          //If the search button was pressed the getSubmitSearch() value will be not null
          if ("search".equalsIgnoreCase(action)) {
                Collection pos = searchPoProcess.getPOs(inputBean, personnelBean);
                //Pass the result collection in request
                request.setAttribute("poSearchColl",pos);
                request.setAttribute("vvSupplyPathBeanCollection",vvDataProcess.getSupplyPathType());
          }   
          else if ( "createXSL".equalsIgnoreCase(action) ) {
                  Collection pos = searchPoProcess.getPOs(inputBean, personnelBean);
                  //Pass the result collection in request
                  request.setAttribute("poSearchColl",pos);
                  request.setAttribute("vvSupplyPathBeanCollection",vvDataProcess.getSupplyPathType()); 

      			this.setExcel(response,"SearchPO_"+personnelId);
    	    	
      			searchPoProcess.getExcelReport(pos,response.getOutputStream(), personnelBean.getLocale());
      			return noForward;
          }
          else
          {
           //Do stuff to get data you are going to need to build the search options
           //or any other extra data you need.           
      	   request.setAttribute("buyerColl",searchPoProcess.getBuyerDropDown());      	   
          }
          
          if (searchFromPopup != null && searchFromPopup.equalsIgnoreCase("true"))
     		  request.setAttribute("searchFromPopup", "true");
     	  else 
     		 request.setAttribute("searchFromPopup", "false");
     	  
          
          return (mapping.findForward("showresults"));	       
 }
}
