package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.admin.process.UserOpsEntityAdminViewProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.CustomerCarrierAddRequestBean;
import com.tcmis.internal.distribution.beans.CustomerContactAddRequestBean;
import com.tcmis.internal.distribution.beans.CustomerShiptoAddRequestBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.supply.beans.*;
import com.tcmis.internal.supply.process.*;

import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for supplier requests
 * @version 1.0
     ******************************************************************************/
public class SupplierRequestAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "supplierrequest");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    String uAction = (String)( (DynaBean) form).get("uAction");
    
    if ( uAction == null || uAction.length() == 0 ) 
        return mapping.findForward("success");

    String forward = "results";
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		

	if (!personnelBean.getPermissionBean().hasUserPagePermission("newSupplierRequest") &&
            personnelBean.getPermissionBean().hasUserPagePermission("supplierSearch") )   {
         return (mapping.findForward("nopermissions"));
    }
	    	    
    SupplierRequestProcess process = new SupplierRequestProcess(this.getDbUser(request),this.getLocale(request));
    LogisticsInputBean bean = new LogisticsInputBean();
    BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request) );

    if ( "search".equals(uAction) ) {
      request.setAttribute("beanCollection", process.getSearchData(bean));
      return mapping.findForward(forward);
    }
    if ( "createExcel".equals(uAction) ) {
    	try {
			this.setExcel(response,"SupplierRequest");
			process.getSupplierRequestExcel(bean).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    }


    UserOpsEntityAdminViewProcess adminProcess = new UserOpsEntityAdminViewProcess(this);        
    request.setAttribute("opsHubIgOvBeanCollection", adminProcess.getOpsHubIgData(""+personnelBean.getPersonnelId()+"",personnelBean.getCompanyId()));
    request.setAttribute("searchSupplierRequestInputBean", bean);
    request.setAttribute("vvPaymentTermsBeanCollection", process.getPaymentTermsDropDown());
    request.setAttribute("vvQualificationLevelBeanCollection", process.getQualificationLevelDropDown());
    
    //VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
    //request.setAttribute("vvCountryBeanCollection", vvDataProcess.getNormalizedCountryState());
    request.setAttribute("vvTypeOfPurchase", process.getTypeOfPurchaseDropDown());
//    request.setAttribute("vvOperatingEntity", process.getOperatingEntityDropDown());
    
    forward = "showrequestdetail";
    SupplierAddRequestBean sbean = new SupplierAddRequestBean();
    BeanHandler.copyAttributes(form, sbean, this.getTcmISLocale(request) );

    if ("new".equals(uAction) ) {
    	request.setAttribute("supplierAddRequestViewBean",process.getRequestDetailForNew(sbean,"",personnelBean));
    	return mapping.findForward(forward);
    }
    
    /*java.util.Enumeration e = request.getParameterNames();
    while(e.hasMoreElements()) {
    	String name = (String) e.nextElement();
    	System.out.println("Name:"+name+":value:"+request.getParameter(name));
    }
*/
    Vector beancoll = new Vector(2);
    beancoll.add( BeanHandler.getBeans((DynaBean)form,"SupplierAddPaymentTermsBean",new SuppAddPaymentTermsViewBean(),this.getTcmISLocale()));
    beancoll.add( BeanHandler.getBeans((DynaBean)form,"SupplierAddExcPaymentTermsBean",new SuppExcAddPaytermsViewBean(),this.getTcmISLocale()));

    
    if ("modify".equals(uAction) ) {
    	if( "Change Payment Terms".equals(sbean.getSupplierRequestType()) ) {
    		request.setAttribute("supplierAddRequestViewBean",process.getRequestDetailForModify(sbean,"",personnelBean));
    	}
    	else if( "Activate".equals(sbean.getSupplierRequestType()) ) {
//    		sbean.setSupplierRequestStatus("New");
    		request.setAttribute("supplierAddRequestViewBean",process.getRequestDetailForModify(sbean,"",personnelBean));
    	}
    	else if( "Modify".equals(sbean.getSupplierRequestType()) ) {
//    		sbean.setSupplierRequestStatus("New");
    		request.setAttribute("supplierAddRequestViewBean",process.getRequestDetailForModify(sbean,"",personnelBean));
    	}
    	else {
// This part of the code should be wrong, investigate
    	SupplierAddRequestViewBean bb = process.getRequestDetail(sbean.getSupplierRequestId());
    	if( bb != null && "Modify".equals(request.getParameter("supplierRequestType"))) 
    			bb.setSupplierRequestType("Modify");
    	request.setAttribute("supplierAddRequestViewBean", bb);

    	}
        request.setAttribute("PaymentColl",process.getSupplierPaymentForModify(sbean.getSupplier()));
        request.setAttribute("PaymentIgColl",process.getSupplierIgPaymentForModify(sbean.getSupplier()));
    	return mapping.findForward(forward);    
    }
    
    BigDecimal id = sbean.getSupplierRequestId();
    
    if (!"viewrequestdetail".equals(uAction)) {
    	this.saveTcmISToken(request);
    	//PaymentColl PaymentIgColl
    	//<div id="SupplierAddPaymentTermsBean" style="height:150px;display: none;" ></div>
    	//<div id="SupplierAddExcPaymentTermsBean" style="height:150px;display: none;" ></div>

    	try {
    		if ("save".equals(uAction)) 
    			id = process.save(sbean, personnelBean, request.getParameter("oriDefaultPaymentTerms"),beancoll);
    		else if ("deletedraft".equals(uAction))
    			process.delete(sbean,personnelBean);
    		else if ("submitdraft".equals(uAction))
    			id = process.saveSupplierRequest(sbean, personnelBean, request.getParameter("oriDefaultPaymentTerms"),beancoll);
    		else if ("approve".equals(uAction)) 
    			request.setAttribute("tcmISError",process.approveSupplierRequest(sbean, personnelBean,beancoll));
    		else if ("approvepaymentterms".equals(uAction)) 
    			request.setAttribute("tcmISError",process.approvePaymentTerms(sbean, personnelBean,beancoll));
    		else if ("reject".equals(uAction))
    			request.setAttribute("tcmISError",process.rejectSupplierRequest(sbean, personnelBean,beancoll));
    		else if ("changePaymentTerms".equals(uAction) ) 
    			request.setAttribute("tcmISError",process.changePaymentTerms(sbean, personnelBean,beancoll));
    		else if ("approvePaymentTermsDirect".equals(uAction) ) 
    			request.setAttribute("tcmISError",process.approvePaymentTermsDirect(sbean, personnelBean,beancoll));
		}
		catch(Exception ex){
			ResourceLibrary library = new ResourceLibrary(
					"com.tcmis.common.resources.CommonResources", this.getTcmISLocale(request));
			String errorMsg = library.getString("generic.error");
			request.setAttribute("tcmISError",errorMsg);
		}
    }
    request.setAttribute("PaymentColl",process.getSupplierPayment(sbean.getSupplierRequestId()));
    request.setAttribute("PaymentIgColl",process.getSupplierIgPayment(sbean.getSupplierRequestId()));
    request.setAttribute("supplierAddRequestViewBean", process.getRequestDetail(id));
    return mapping.findForward(forward);
  }
}