package com.tcmis.supplier.shipping.action;

	import java.math.BigDecimal;
	import java.util.*;

	import javax.servlet.http.*;

	import org.apache.struts.action.*;
	import org.apache.commons.beanutils.DynaBean;

	import com.tcmis.common.admin.beans.PersonnelBean;
	import com.tcmis.common.exceptions.*;
	import com.tcmis.common.framework.*;
	import com.tcmis.common.util.*;
	import com.tcmis.supplier.shipping.beans.*;
import com.tcmis.supplier.shipping.process.*;

	/******************************************************************************
	 * Controller for cabinet labels
	 * @version 1.0
	     ******************************************************************************/
	public class LabelingAction extends TcmISBaseAction {

	  public ActionForward executeAction(ActionMapping mapping,
	                                     ActionForm form,
	                                     HttpServletRequest request,
	                                     HttpServletResponse response) throws
	      BaseException, Exception {
	    if (!this.isLoggedIn(request)) {
	      request.setAttribute("requestedPage", "usgovlabelingmain");
	      request.setAttribute("requestedURLWithParameters",
	                           this.getRequestedURLWithParameters(request));
	      return (mapping.findForward("login"));
	    }
	    
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
		 "personnelBean");
	    
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("usGovlabeling"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

	    if (form == null ) {	    	
	        request.setAttribute("supplier", request.getParameter("supplier"));
	        request.setAttribute("action", request.getParameter("action"));
	        request.setAttribute("shipFromLocationId", request.getParameter("shipFromLocationId"));
	        request.setAttribute("picklistId", request.getParameter("picklistId"));
		    return (mapping.findForward("success"));
	    }
	    String action = (String)( (DynaBean) form).get("_action");
	    BigDecimal personnelId = personnelBean.getPersonnelIdBigDecimal();

	    //SupplierLocPicklistOvBean updatebean = new SupplierLocPicklistOvBean();
//	    radian.web.SupplierLocPicklist.SupplierLocPicklist abc = null;
	    //if form is not null we have to perform some action
	    if ( action== null ) {
     	  LabelingProcess process = new LabelingProcess(this.getDbUser(request));
	      SupplierPackingSummaryViewBean inputbean = new SupplierPackingSummaryViewBean(); 
		  BeanHandler.copyAttributes(form, inputbean);		  
	      Vector v = (Vector) process.getSearchDropDown(personnelId);
	      request.setAttribute("supplierLocPicklistOvBeanCollection", v);
	      //this.saveTcmISToken(request);
	      return (mapping.findForward("success"));
	    }
	    LabelingProcess process = new LabelingProcess(this.getDbUser(request));
	    if ( action.equals("search")) {
		    Collection c = new Vector();
		    DynaBean dynaBean = (DynaBean) form;
		    SupplierShippingInputBean inputbean = new SupplierShippingInputBean();
        inputbean.setPersonnelId(personnelId);
        BeanHandler.copyAttributes(form, inputbean);
			  //request.getSession().setAttribute("SPinputbean",inputbean);
			  Object[] result = process.showResults(inputbean);
			  request.setAttribute("suppPackSummaryViewBeanCollection", result[0]);
			  request.setAttribute("rowCountPart", result[1]);
			  request.setAttribute("rowCountItem", result[2]);
		    }
      	else if ( action.equals("undoMrLine")) {
        DynaBean dynaBean = (DynaBean) form;
        Collection beans = BeanHandler.getBeans(dynaBean,"labelBean", new SupplierPackingSummaryViewBean());

		SupplierShippingInputBean inputbean = new SupplierShippingInputBean();
        inputbean.setPersonnelId(personnelId);
        BeanHandler.copyAttributes(form, inputbean);
		
        String errorMsg = process.update(beans);
		if (!"OK".equalsIgnoreCase(errorMsg)) {
			request.setAttribute("tcmISError", errorMsg);
		}
        
        Object[] result = process.showResults(inputbean);
        request.setAttribute("suppPackSummaryViewBeanCollection", result[0]);
			  request.setAttribute("rowCountPart", result[1]);
			  request.setAttribute("rowCountItem", result[2]);
		    }

      /*else if ( action.equals("createExcel")) {
		      Collection c = new Vector();
		      DynaBean dynaBean = (DynaBean) form;
		      SupplierPackingSummaryViewBean inputbean = new SupplierPackingSummaryViewBean(); 
			  BeanHandler.copyAttributes(form, inputbean);
			  //request.getSession().setAttribute("SPinputbean",inputbean);
			  process.showInventory(inputbean,request);
			  Vector v= (Vector) request.getAttribute("pageNameViewBeanCollection");
			  this.setExcelHeader(response);
		      process.getExcelReport(v, response.getWriter(), request);
		      return (mapping.findForward("viewfile"));
			  }*/
	    return (mapping.findForward("success"));
	  }
	}
