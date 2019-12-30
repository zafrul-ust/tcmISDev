package com.tcmis.internal.supply.action;

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
import com.tcmis.internal.supply.beans.NewRemitToInputBean;
import com.tcmis.internal.supply.beans.SupplierBillingLocationViewBean;
import com.tcmis.internal.supply.process.NewRemitToProcess;

/******************************************************************************
 * Controller for NewRemitTo
 * @version 1.0
 ******************************************************************************/

public class NewRemitToAction extends TcmISBaseAction
{
	
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception 
			{
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute(REQUESTED_PAGE, NEW_REMIT_TO_MAIN);
			request.setAttribute(REQUESTED_URL_WITH_PARAMETERS, this.getRequestedURLWithParameters(request));
			return (mapping.findForward(FORWARD_TO_LOGIN));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, PERSONNEL_BEAN);		


		if (!personnelBean.getPermissionBean().hasUserPagePermission("newRemitTo"))
		{
			return (mapping.findForward(FORWARD_TO_NOPERMISSIONS));
		}

		NewRemitToInputBean inputBean = new NewRemitToInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		NewRemitToProcess process = new NewRemitToProcess(this.getDbUser(request),getTcmISLocaleString(request));

		if (inputBean.getAction() != null && OPTION_SEARCH.equals(inputBean.getAction())) 
		{    	  
			Collection <SupplierBillingLocationViewBean> supplierBillingLocViewBeanCollection = process.getSupplierBillingLocationViewBeanCollection(inputBean);

			request.setAttribute(REQ_SUPPLIER_BILLING_LOC_VIEW_BEAN_COLL, supplierBillingLocViewBeanCollection);			
			this.saveTcmISToken(request);
		}

		else if (inputBean.getAction() != null && OPTION_CREATE_EXCEL.equals(inputBean.getAction())) 
		{	 
			this.setExcel(response, EXCEL_FILE_NAME);
			process.getExcelReport(inputBean,
					(java.util.Locale) request.getSession().getAttribute(TCMIS_LOCALE)).write(response.getOutputStream());
			return noForward;
		}
	     else if (inputBean.getAction() != null &&	OPTION_UPDATE.equals(inputBean.getAction())) 
		 {	 
			  if (!personnelBean.getPermissionBean().hasFacilityPermission("newRemitTo",null,null))
	          {
	              request.setAttribute("tcmISError",getResourceBundleValue(request,ERROR_NO_ACCESS_TO_PAGE));
	              request.setAttribute(REQ_SUPPLIER_BILLING_LOC_VIEW_BEAN_COLL,  process.getSupplierBillingLocationViewBeanCollection(inputBean));

	          }

	          checkToken(request);
	          SupplierBillingLocationViewBean supplierBean = new SupplierBillingLocationViewBean();
	          Collection<SupplierBillingLocationViewBean> beans = BeanHandler.getBeans((DynaBean) form, SUPPLIER_BILLING_LOC_VIEW_BEAN, supplierBean,getTcmISLocale(request));
	          Collection errorMsgs =  process.update(beans,personnelBean);
              request.setAttribute(REQ_SUPPLIER_BILLING_LOC_VIEW_BEAN_COLL, process.getSupplierBillingLocationViewBeanCollection(inputBean));                   		  
              request.setAttribute(TCM_IS_ERRORS, errorMsgs);    
	     }    
	     else if (inputBean.getAction() != null &&	"reject".equals(inputBean.getAction()) && inputBean.getRowIndex() != null) 
		 {	 
			  if (!personnelBean.getPermissionBean().hasFacilityPermission("newRemitTo",null,null))
	          {
	              request.setAttribute("tcmISError",getResourceBundleValue(request,ERROR_NO_ACCESS_TO_PAGE));
	              request.setAttribute(REQ_SUPPLIER_BILLING_LOC_VIEW_BEAN_COLL,  process.getSupplierBillingLocationViewBeanCollection(inputBean));

	          }

	          checkToken(request);
	          SupplierBillingLocationViewBean supplierBean = new SupplierBillingLocationViewBean();
	          Vector<SupplierBillingLocationViewBean> beans = (Vector)BeanHandler.getBeans((DynaBean) form, SUPPLIER_BILLING_LOC_VIEW_BEAN, supplierBean,getTcmISLocale(request));
	          SupplierBillingLocationViewBean rejectedBean = beans.get(inputBean.getRowIndex().intValue());
	          Collection errorMsgs =  process.reject(rejectedBean,personnelBean);
              request.setAttribute(REQ_SUPPLIER_BILLING_LOC_VIEW_BEAN_COLL, process.getSupplierBillingLocationViewBeanCollection(inputBean));                   		  
              request.setAttribute(TCM_IS_ERRORS, errorMsgs);    
	     }      



		return (mapping.findForward(FORWARD_TO_SUCCESS));
			}


	// Constant String names.
	private static final String EXCEL_FILE_NAME = "Supplier_Billing_Loc_List";
	private static final String REQ_SUPPLIER_BILLING_LOC_VIEW_BEAN_COLL = "supplierBillingLocViewBeanColl";
	private static final String TCMIS_LOCALE = "tcmISLocale";
	private static final String FORWARD_TO_NOPERMISSIONS = "nopermissions";
	private static final String FORWARD_TO_SUCCESS = "success";
	private static final String OPTION_CREATE_EXCEL = "createExcel";
	private static final String OPTION_SEARCH = "search";
	private static final String PERSONNEL_BEAN = "personnelBean";
	private static final String FORWARD_TO_LOGIN = "login";
	private static final String REQUESTED_URL_WITH_PARAMETERS = "requestedURLWithParameters";
	private static final String NEW_REMIT_TO_MAIN = "newremittomain";
	private static final String REQUESTED_PAGE = "requestedPage";
	private static final String OPTION_UPDATE = "update";
	private static final String ERROR_NO_ACCESS_TO_PAGE = "error.noaccesstopage";
	private static final String SUPPLIER_BILLING_LOC_VIEW_BEAN = "supplierBillingLocViewBean";
	private static final String TCM_IS_ERRORS = "tcmISErrors";
}
