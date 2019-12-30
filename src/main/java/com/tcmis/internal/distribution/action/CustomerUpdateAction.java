package com.tcmis.internal.distribution.action;

import java.util.Collection;

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
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.beans.CustomerCarrierInfoBean;
import com.tcmis.internal.distribution.beans.CustomerContactBean;
import com.tcmis.internal.distribution.beans.UserOpsEntityPgViewBean;
import com.tcmis.internal.distribution.process.CustLookUpProcess;
import com.tcmis.internal.distribution.process.CustomerRequestProcess;
import com.tcmis.internal.distribution.process.CustomerUpdateProcess;

/******************************************************************************
 * Controller for CustomerAddressSearch
 * @version 1.0
 ******************************************************************************/
public class CustomerUpdateAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		//  login

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customerupdate");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}


		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		if (!personnelBean.getPermissionBean().hasUserPagePermission("customerSearch"))
		{
			return (mapping.findForward("nopermissions"));
		}

		//  result
		CustLookUpProcess process = new CustLookUpProcess(this);
		CustomerRequestProcess sprocess = new CustomerRequestProcess(getDbUser(request),getLocale(request));
		CustomerUpdateProcess custUpdateProcess = new CustomerUpdateProcess(getDbUser(request),getLocale(request));
		//  main


		String uAction = null;
		//VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
		//request.setAttribute("vvCountryBeanCollection", vvDataProcess.getNormalizedCountryState());
		request.setAttribute("companyBean", process.getCompanyDropDown());
		request.setAttribute("vvIndustryCollection", sprocess.getIndustryDropDown());
		request.setAttribute("vvSapIndustryKeyCollection", sprocess.getSapIndustryKeyDropDown());
		request.setAttribute("vvInvoiceConsolidationCollection", sprocess.getInvoiceConsolidationDropDown());
		request.setAttribute("vvCreditStatusCollection", sprocess.getCreditStatusDropDown());
		request.setAttribute("vvJobFunctionCollection", sprocess.getJobFunctionDropDown());
		request.setAttribute("vvLocale", sprocess.getVvLocaleDropDown());
		request.setAttribute("labelColl", sprocess.getSearchResultObjects( "select distinct LABEL_FORMAT from INVENTORY_GROUP_LABEL_FORMAT x where LABEL_TYPE = 'exitLabel'" ) );

		
		//request.setAttribute("vvPaymentTermsBeanCollection", sprocess.getPaymentTermsDropDown());
		request.setAttribute("vvTaxTypeColl", sprocess.getTaxTypeDropDown() );
		request.setAttribute("vvpriceListCollection",
		    	process.getSearchResult("select distinct ops_entity_id,price_group_id, price_group_name from user_ops_entity_pg_view where personnel_id = {0} and company_id = {1} order by ops_entity_id,price_group_name",
		    			new UserOpsEntityPgViewBean(),personnelBean.getPersonnelId(),personnelBean.getCompanyId()));
		request.setAttribute("vvpriceListCollectionAll",
		    	process.getSearchResult("select distinct ops_entity_id,price_group_id, price_group_name from user_ops_entity_pg_view order by ops_entity_id,price_group_name",
		    			new UserOpsEntityPgViewBean(),personnelBean.getPersonnelId(),personnelBean.getCompanyId()));
		//request.setAttribute("priceList", sprocess.getPriceListDropDown());
		uAction = (String) ( (DynaBean)form).get("uAction");
		String customerId = request.getParameter("customerId");	



		Collection errorMsgs = null;
		// If customer id is null throw an error.
		if(null==customerId)
		{
			return mapping.findForward("genericerrorpage");
		}

//		 show all parameters in java files
		/*java.util.Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()) {
			String name = (String) e.nextElement();
			System.out.println("Name:"+name+":value:"+request.getParameter(name));
		}*/

		if( form == null || (uAction = (String) ( (DynaBean)form).get("uAction")) == null ) {
			//		request.setAttribute("companyBean", process.getCompanyDropDown());
			if( customerId != null && customerId.length() > 0) {
				Collection coll = process.searchNewCustomer(customerId);//
				if( coll.size() != 0 )
				{	
					request.setAttribute("customerBean",coll.toArray()[0]);

				}
				Collection <CustomerCarrierInfoBean> carrierInfoViewBeanCollection = process.getCarrierInfoViewBeanCollection(customerId);
				request.setAttribute("carrierInfoViewBeanColl", carrierInfoViewBeanCollection);
				request.setAttribute("shipToBeanColl",process.searchShipToBeanColl(customerId));

			}
		}
		if( "update".equals(uAction)) 
		{
			String err = "";
			// check credit status only on update. if it's stop then call a proc.
			String creditStatus = (String) ( (DynaBean)form).get("creditStatus");

			CustomerAddRequestViewBean inputBean = new CustomerAddRequestViewBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			err = custUpdateProcess.callModifyCustomer(inputBean,personnelBean);


			CustomerContactBean bean = new CustomerContactBean(); 
			Collection<CustomerContactBean>  beans = BeanHandler.getBeans((DynaBean) form, "contactBean", bean,getTcmISLocale(request));  	    

			errorMsgs =  custUpdateProcess.updateCustomerContact(beans,inputBean, personnelBean);  

			// call a proc if credit status is Stop on update.
			if("Stop".equalsIgnoreCase(creditStatus))
			{
				err+= custUpdateProcess.callCancelCustomer(customerId, personnelBean);
			}
			
			Collection  shipToCollection = BeanHandler.getBeans((DynaBean) form, "shiptoBean", new CustomerAddressSearchViewBean(),getTcmISLocale(request));
			Collection errorShipToMsgs = custUpdateProcess.updateShipTo(shipToCollection, personnelBean,customerId);
			if (!errorShipToMsgs.isEmpty())
				errorMsgs.add(errorShipToMsgs);

			if ((err.trim().length()==0) && (!errorMsgs.isEmpty())  )
				request.setAttribute("tcmISErrors", errorMsgs);

			if (err.trim().length() > 0)
				request.setAttribute("tcmISErrors", err);
			else
				request.setAttribute("done", "Y");


			if( customerId != null && customerId.length() > 0) {
				Collection coll = process.searchNewCustomer(customerId);//
				if( coll.size() != 0 )
				{	
					request.setAttribute("customerBean",coll.toArray()[0]);

				}
				Collection <CustomerCarrierInfoBean> carrierInfoViewBeanCollection = process.getCarrierInfoViewBeanCollection(customerId);
				request.setAttribute("carrierInfoViewBeanColl", carrierInfoViewBeanCollection);
				request.setAttribute("shipToBeanColl",process.searchShipToBeanColl(customerId));

			}
		}  

		request.setAttribute("opsPgColl",
		    	process.getSearchResult("select * from user_ops_entity_pg_view where personnel_id = {0} and company_id = {1} order by price_group_name",
		    			new UserOpsEntityPgViewBean(),personnelBean.getPersonnelId(),personnelBean.getCompanyId()));
		request.setAttribute("customerContactColl",process.searchNewCustomerContact(customerId));
		return mapping.findForward("success");
	}
}