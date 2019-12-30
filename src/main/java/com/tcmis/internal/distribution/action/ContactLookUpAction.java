package com.tcmis.internal.distribution.action;

import java.net.URLDecoder;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.ContactLookUpInputBean;
import com.tcmis.internal.distribution.process.ContactLookUpProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class ContactLookUpAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {
//	  login
 	
	   	if (!this.isLoggedIn(request)) {
				request.setAttribute("requestedPage", "contactlookupmain");
				request.setAttribute("requestedURLWithParameters",
						this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
		}
			
	   	String customerName = URLDecoder.decode(request.getParameter("customerName"), "UTF-8");
	//   	System.out.println("queryString:"+request.getQueryString());
	    if (request != null && request.getQueryString() != null) {
	   	            String queryString= request.getQueryString();
	   	            String[] queryArr = queryString.split("&");
	   	            for(String param : queryArr) {
	   	                if(param.startsWith("customerName="))
	   	                {
	   	                    try {
	   	                    	customerName = URLDecoder.decode(param.substring(param.indexOf("=")+1), "UTF-8");
	   	                    }
	   	                    catch(Exception ex) {
	   	                        ex.printStackTrace();
	   	                    }
	   	                }
	   	            }
	   	}
	   		    
	    request.setAttribute("customerName", customerName);

//	  main
//	  	if( form == null )
//	      	return (mapping.findForward("success"));
	  	String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) return mapping.findForward("success");

//	  result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		ContactLookUpProcess process = new ContactLookUpProcess(this.getDbUser(request),getTcmISLocaleString(request));
	  	
		ContactLookUpInputBean inputBean = new ContactLookUpInputBean();
	    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
	    

	    if ( uAction.equals("search") || uAction.equals("searchContact")) {
	    	Collection results = process.getSearchResult(inputBean);	
	    	request.setAttribute("CustomerContactSearchViewCollection", results);
//	    	Object[] results = process.showResult(inputBean);		
//			request.setAttribute("CustomerContactSearchViewCollection", results[0]);
//			request.setAttribute("rowCountPart", results[1]);
	        return (mapping.findForward("success"));
	    }
	    else if (uAction.equals("createExcel") ) {	    	  
	    	  Vector v = (Vector) process.getSearchResult(inputBean);
				this.setExcel(response,"Contact Search");
				process.getExcelReport(v, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				return noForward;
	  
	    }
//	  search    
	      else {/* set up search page data*/
	 //     	request.setAttribute("setUpCollection", process.getSetUpCollection(new BigDecimal(personnelBean.getPersonnelId())));
	      }
	      return mapping.findForward("success");
	    }
	  }