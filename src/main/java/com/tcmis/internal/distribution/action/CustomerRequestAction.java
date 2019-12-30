package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.supply.process.SupplierRequestProcess;
import com.tcmis.internal.distribution.beans.*;
import com.tcmis.internal.distribution.process.*;

import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for customer requests
 * @version 1.0
     ******************************************************************************/
public class CustomerRequestAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "customerrequest");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		

//  uncomment this when do permission
	if (!personnelBean.getPermissionBean().hasUserPagePermission("customerRequest"))   
//	if( false )
	{
         return (mapping.findForward("nopermissions"));
    }

    String uAction = (String)( (DynaBean) form).get("uAction");
    
    if ( uAction == null || uAction.length() == 0 ) 
        return mapping.findForward("success");

    String forward = "results";
	    	    
    CustomerRequestProcess process = new CustomerRequestProcess(this.getDbUser(request),this.getLocale(request));
    LogisticsInputBean bean = new LogisticsInputBean();
    BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request) );

    if ( "search".equals(uAction) ) {
      request.setAttribute("beanCollection", process.getSearchData(bean));
      return mapping.findForward(forward);
    }
    if ( "createExcel".equals(uAction) ) {
    	try {
			this.setExcel(response,"CustomerRequest");
			process.getCustomerRequestExcel(bean).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    }
/*
    request.setAttribute("searchCustomerRequestInputBean", bean);
    request.setAttribute("vvPaymentTermsBeanCollection", process.getPaymentTermsDropDown());
    request.setAttribute("vvIndustryCollection", process.getIndustryDropDown() );
    request.setAttribute("vvTaxTypeColl", process.getTaxTypeDropDown() );
    request.setAttribute("vvJobFunctionCollection", process.getJobFunctionDropDown());
    request.setAttribute("vvInvoiceConsolidationCollection", process.getInvoiceConsolidationDropDown());
	//request.setAttribute("vvOpsHubIg", process.getOpsHubIgData(new BigDecimal("86405"),"Radian"));
	request.setAttribute("priceList", process.getPriceListDropDown());
	
    VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
    SupplierRequestProcess sprocess = new SupplierRequestProcess(getDbUser(),getLocale(request));
    request.setAttribute("vvCountryBeanCollection", vvDataProcess.getNormalizedCountryState());
//    request.setAttribute("vvOperatingEntity", sprocess.getOperatingEntityDropDown());
    
    forward = "showrequestdetail";
    CustomerAddRequestViewBean sbean = new CustomerAddRequestViewBean();
    BeanHandler.copyAttributes(form, sbean, this.getTcmISLocale(request) );
    if (sbean.getCompanyId() != null)
    {
        sbean.setCompanyId(sbean.getCompanyId().toUpperCase());
    }
    CustLookUpProcess cprocess = new CustLookUpProcess(this);
    request.setAttribute("companyBean", cprocess.getCompanyDropDown());
    if ("new".equals(uAction) ) { 
		request.setAttribute("customerAddRequestViewBean",process.getRequestDetailForModify(sbean,personnelBean));
    	return mapping.findForward(forward);
    }
    if ("modify".equals(uAction) ) {
    	if( "Change Payment Terms".equals(sbean.getCustomerRequestType()) ) {
    		request.setAttribute("customerAddRequestViewBean",process.getRequestDetailForModify(sbean,personnelBean));
    	}
    	else if( "Activate".equals(sbean.getCustomerRequestType()) ) {
//    		sbean.setCustomerRequestStatus("New");
    		request.setAttribute("customerAddRequestViewBean",process.getRequestDetailForModify(sbean,personnelBean));
    	}
    	else if( "Modify".equals(sbean.getCustomerRequestType()) ) {
//    		sbean.setCustomerRequestStatus("New");
    		request.setAttribute("customerAddRequestViewBean",process.getRequestDetailForModify(sbean,personnelBean));
    	}
    	else {
// This part of the code should be wrong, investigate
    	CustomerAddRequestViewBean bb = process.getRequestDetail(sbean.getCustomerRequestId());
    	if( bb != null && "Modify".equals(request.getParameter("customerRequestType"))) 
    			bb.setCustomerRequestType("Modify");
    	request.setAttribute("customerAddRequestViewBean", bb);

    	}
    	return mapping.findForward(forward);    
    }
    
    BigDecimal id = sbean.getCustomerRequestId();
    
    if (!"viewrequestdetail".equals(uAction)) {
    	this.saveTcmISToken(request);
		Vector beancoll = new Vector(3);
//					java.util.Enumeration e = request.getParameterNames();
//			while(e.hasMoreElements()) {
//				String name = (String) e.nextElement();
//				System.out.println("Name:"+name+":value:"+request.getParameter(name));
//			}

        beancoll.add( BeanHandler.getBeans((DynaBean)form,"CustomerContactAddRequestBean",new CustomerContactAddRequestBean(),this.getTcmISLocale()));
        beancoll.add( BeanHandler.getBeans((DynaBean)form,"CustomerShiptoAddRequestBean",new CustomerShiptoAddRequestBean(),this.getTcmISLocale()));
        beancoll.add( BeanHandler.getBeans((DynaBean)form,"CustomerCarrierAddRequestBean",new CustomerCarrierAddRequestBean(),this.getTcmISLocale()));
		try {
	    	if ("save".equals(uAction))
		    	process.save(sbean, personnelBean, request.getParameter("newCompanyId"),beancoll);
	    	else if ("deletedraft".equals(uAction))
		    	process.delete(sbean,personnelBean);
	    	else if ("submitdraft".equals(uAction))
		    	process.saveCustomerRequest(sbean, personnelBean, request.getParameter("newCompanyId"),beancoll);
	    	else if ("approve".equals(uAction)) 
	    		request.setAttribute("tcmISError",process.approveCustomerRequest(sbean, personnelBean,beancoll));
	    	else if ("approvenewcompany".equals(uAction)) 
	    		request.setAttribute("tcmISError",process.approveNewComopanyProc(sbean, personnelBean,beancoll));
	    	else if ("reject".equals(uAction))
	    		request.setAttribute("tcmISError",process.rejectCustomerRequest(sbean, personnelBean,beancoll));
//	    	goes to approved after approval new company 
//	    	else if ("approveNewCompanyDirect".equals(uAction) ) 
//	        	request.setAttribute("tcmISError",process.approveNewCompanyDirect(sbean, personnelBean,beancoll));
		}
		catch(Exception ex){
			ResourceLibrary library = new ResourceLibrary(
					"com.tcmis.common.resources.CommonResources", this.getTcmISLocale(request));
			String errorMsg = library.getString("generic.error");
			request.setAttribute("tcmISError",errorMsg);
		}
    }
    CustomerAddRequestViewBean newbean = process.getRequestDetail(id);
    request.setAttribute("customerAddRequestViewBean", newbean);
//    request.setAttribute("customerAddRequestViewBean", sbean);
    request.setAttribute("beanColl",process.getCustomerContact(sbean.getCustomerRequestId()));
    request.setAttribute("shipColl",process.getCustomerShipto(sbean.getCustomerRequestId()));
    request.setAttribute("carrierColl",process.getCustomerCarrier(sbean.getCustomerRequestId()));
*/
    return mapping.findForward(forward);
  }
}