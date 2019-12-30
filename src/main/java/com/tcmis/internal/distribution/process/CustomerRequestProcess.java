package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.admin.beans.*;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.factory.UserOpsEntityHubIgOvBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.distribution.beans.*;
import com.tcmis.internal.distribution.factory.*;

/******************************************************************************
 * Process for searching new customer requests
 * @version 1.0
 *****************************************************************************/
public class CustomerRequestProcess
    extends GenericProcess {

	  public CustomerRequestProcess(TcmISBaseAction act) throws BaseException {
		    super(act);
	  }

  public CustomerRequestProcess(String client,Locale locale) {
    super(client,locale);
  }

  public Collection getSearchData(LogisticsInputBean bean) throws
      BaseException {

    SearchCriteria criteria = new SearchCriteria();
    
    if( !isBlank(bean.getStatus()) )
    	criteria.addCriterion("customerRequestStatus",SearchCriterion.EQUALS,bean.getStatus());
    
    criteria.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument() );
    if( !isBlank(bean.getDorfrom()) )
        criteria.addCriterion("transactionDate", SearchCriterion.FROM_DATE, bean.getDorfrom() );
    if( !isBlank(bean.getDorto()) )
        criteria.addCriterion("transactionDate", SearchCriterion.TO_DATE, bean.getDorto() );
    
    return 	factory.setBean(new CustomerAddRequestViewBean())
    				.select(criteria,null,"CUSTOMER_ADD_REQUEST_VIEW");
  }

  public CustomerAddRequestViewBean getRequestDetail(BigDecimal id) throws BaseException {

	  Collection c = factory.setBean(new CustomerAddRequestViewBean()).select(
			  new SearchCriteria("customerRequestId",SearchCriterion.EQUALS,""+id),null,"CUSTOMER_ADD_REQUEST_VIEW");
	  if( c.size() !=0 ) return ((Vector<CustomerAddRequestViewBean>)c).get(0);
	  else return null;
  }

  public CustomerAddRequestViewBean getRequestDetailForModify(CustomerAddRequestViewBean sbean,PersonnelBean personnelBean) throws BaseException {
	  BigDecimal customerRequestId = sbean.getCustomerRequestId();
	  SearchCriteria criteria = new SearchCriteria("customer",SearchCriterion.EQUALS,""+customerRequestId);

	  CustomerAddRequestViewBean bean = new CustomerAddRequestViewBean();

	  bean.setCustomerRequestStatus("Draft");
	  bean.setCustomerRequestId(new BigDecimal(dbManager.getOracleSequence("CUSTOMER_ADD_REQUEST_SEQ")));
	  bean.setCustomerRequestType(sbean.getCustomerRequestType());
	  bean.setRequester(new BigDecimal(personnelBean.getPersonnelId()));
      bean.setRequesterName(personnelBean.getLastName()+", "+personnelBean.getFirstName());
      bean.setRequesterPhone(personnelBean.getPhone());
      bean.setRequesterEmail(personnelBean.getEmail());
      new CustomerAddRequestBeanFactory(dbManager).insert(bean, locale);
	  return bean;
  }

  public String addCustomerContact(CustomerContactAddRequestBean sbean) throws BaseException {
	  CustomerContactAddRequestBeanFactory factory = new CustomerContactAddRequestBeanFactory(dbManager);
	  if( "false".equals(sbean.getManagement()))sbean.setManagement("N");
	  else sbean.setManagement("Y");
	  if( "false".equals(sbean.getAccountsPayable()))sbean.setAccountsPayable("N");
	  else sbean.setAccountsPayable("Y");
	  if( "false".equals(sbean.getReceiving()))sbean.setReceiving("N");
	  else sbean.setReceiving("Y");
	  if( "false".equals(sbean.getPurchasing()))sbean.setPurchasing("N");
	  else sbean.setPurchasing("Y");
	  if( "false".equals(sbean.getQualityAssurance()))sbean.setQualityAssurance("N");
	  else sbean.setQualityAssurance("Y");
	  factory.insert(sbean);
  	  return ""; 
  }
  public Collection getCustomerContact(BigDecimal customerRequestId) throws BaseException {
	  factory.setBean(new CustomerContactAddRequestBean());
	  return factory.selectQuery("select * from CUSTOMER_CONTACT_ADD_REQUEST where customer_request_id = " + customerRequestId);
  }

  public String updateCustomerContact(CustomerContactAddRequestBean sbean) throws BaseException {
	  CustomerContactAddRequestBeanFactory factory = new CustomerContactAddRequestBeanFactory(dbManager);
  	  factory.update(sbean);
  	  return "";
  }
  public String deleteCustomerContact(CustomerAddRequestViewBean bean) throws BaseException {
	  CustomerContactAddRequestBeanFactory factory = new CustomerContactAddRequestBeanFactory(dbManager);
	  SearchCriteria sc = new SearchCriteria("customerRequestId", SearchCriterion.EQUALS,bean.getCustomerRequestId().toString());
	  //sc.addCriterion("lastName", SearchCriterion.EQUALS, sbean.getLastName());
  	  factory.delete(sc);
  	return ""; 
  }
  public Collection getIndustryDropDown() throws BaseException {
	  return factory.setBean( new VvIndustryBean() ).select(null, new SortCriteria("industry"),"global.vv_industry");
  }
  public Collection getSapIndustryKeyDropDown() throws BaseException {
	  return factory.setBean( new VvSapIndustryBean() ).select(null, new SortCriteria("sapIndustryKey"),"global.VV_SAP_INDUSTRY");
  }
  public Collection getTaxTypeDropDown() throws BaseException {
	  return factory.setBean( new VvTaxTypeBean() ).select(null, new SortCriteria("taxType"),"global.vv_tax_type");
  }
  public Collection getInvoiceConsolidationDropDown() throws BaseException {
	  return factory.setBean( new VvInvoiceConsolidationBean() ).select(null, new SortCriteria("invoiceConsolidation"),"global.vv_invoice_consolidation");
  }
  public Collection getCreditStatusDropDown() throws BaseException {
	  return factory.setBean( new VvCreditStatusBean() ).select(null, new SortCriteria("creditStatus"),"global.vv_credit_status");
  }
  public Collection getCurrencyDropDown() throws BaseException {
	  return factory.setBean( new VvCurrencyBean() ).select(null, new SortCriteria("currency_description"),"global.vv_currency");
  }
  public Collection getJobFunctionDropDown() throws BaseException {
	  return factory.setBean( new VvContactTypeBean() ).select(null, null,"global.vv_contact_type");
  }  
  public Collection getVvLocaleDropDown() throws BaseException {
      return factory.setBean( new VvLocaleBean() ).select(null, new SortCriteria("localeDisplay"),"vv_locale");
  }  
  public Collection getOpsPrice() throws BaseException {
	  return factory.setBean( new OpsEntityPriceGroupVwBean() ).select(null,new SortCriteria("opsEntityId"),"OPS_ENTITY_PRICE_GROUP_VW");
  }
  
  public Collection getValidTaxCountry() throws BaseException {
	  SearchCriteria st = new SearchCriteria("vatExempt", SearchCriterion.EQUALS,"Y");
	  return factory.setBean( new VvCountryBean() ).select(st, new SortCriteria("countryAbbrev"),"global.vv_country");
  }
  
  public BigDecimal save(CustomerAddRequestViewBean bean, PersonnelBean personnelBean,String newComopanyId,Vector beancoll) throws BaseException {

    CustomerAddRequestBeanFactory factory = new CustomerAddRequestBeanFactory(dbManager);
    boolean flag = false;
//    boolean sendemail = false;
    bean.setRequester(new BigDecimal(personnelBean.getPersonnelId()));
//only new company need approval
//	bean.setDateNewCompanyApproved(new Date());
//	bean.setNewCompanyApprover(new BigDecimal(personnelBean.getPersonnelId()));
//    if( "Draft".equals(bean.getCustomerRequestStatus() ) ) { 
    	//new CustomerAddRequestBeanFactory(dbManager).insert(bean);
//
//    	if ( "Y".equals(newComopanyId) ) {
//    		boolean hasp = personnelBean.getPermissionBean().hasOpsEntityPermission("NewCompany", null, null);
//    		//        if () {
//    		if (hasp) {
//    			String NewCompanyMsg  = approveNewCompany(bean,personnelBean);
//    			if( NewCompanyMsg == null ) {
//    				bean.setDatePaymentTermsApproved(new Date());
//    				bean.setPaymentTermsApprover(new BigDecimal(personnelBean.getPersonnelId()));
//    				bean.setCustomerRequestStatus("Pending Financial Approval");
//    				flag = true;
//    			}
//    			else {
//    				bean.setCustomerRequestStatus("Pending New Company Approval");
//    			}
//    		}
//    		else 
//    			bean.setCustomerRequestStatus("Pending New Company Approval");
//    	}
//    	else 
//    		bean.setCustomerRequestStatus("Pending Financial Approval");
//    	sendemail = true;
//    }
    PermissionBean pbean = personnelBean.getPermissionBean();
	if( pbean.hasOpsEntityPermission("NewCompany", null, null) ||
		pbean.hasOpsEntityPermission("NewCustomerApprover", null, null) ||
		pbean.hasInventoryGroupPermission("GenerateOrders", null,null,null ) ) {
			updateBeanColl(beancoll,bean);
    		factory.update(bean,flag,this.locale);
    }
//    if( sendemail ) this.sendNotificationMail(bean, personnelBean);

    return bean.getCustomerRequestId();
  }
  public BigDecimal delete(CustomerAddRequestViewBean bean,PersonnelBean personnelBean) throws BaseException {

	    CustomerAddRequestBeanFactory factory = new CustomerAddRequestBeanFactory(dbManager);
	    boolean flag = false;
	    PermissionBean pbean = personnelBean.getPermissionBean();
		if( pbean.hasOpsEntityPermission("NewCompany", null, null) ||
			pbean.hasOpsEntityPermission("NewCustomerApprover", null, null) ||
			pbean.hasInventoryGroupPermission("GenerateOrders", null,null,null ) ) {
				deleteBeanColl(bean);
	    		factory.delete(bean);
	    }
//	    if( sendemail ) this.sendNotificationMail(bean, personnelBean);

	    return bean.getCustomerRequestId();
  }
  private void deleteBeanColl(CustomerAddRequestViewBean bean) throws BaseException{
      deleteCustomerContact(bean);
      deleteCustomerShipto(bean);
      deleteCustomerCarrier(bean);
      deleteCustomerEntityPG(bean);
  }
  public BigDecimal saveCustomerRequest(CustomerAddRequestViewBean bean, PersonnelBean personnelBean,String newComopanyId,Vector beancoll) throws BaseException {

	    CustomerAddRequestBeanFactory factory = new CustomerAddRequestBeanFactory(dbManager);
	    boolean flag = false;
	    boolean sendemail = false;
	    bean.setRequester(new BigDecimal(personnelBean.getPersonnelId()));
	//only new company need approval
//		bean.setDateNewCompanyApproved(new Date());
//		bean.setNewCompanyApprover(new BigDecimal(personnelBean.getPersonnelId()));
	    if( "Draft".equals(bean.getCustomerRequestStatus() ) ) { 
//	    	new CustomerAddRequestBeanFactory(dbManager).insert(bean);
	    	if ( "Y".equals(newComopanyId) ) {
	    		boolean hasp = personnelBean.getPermissionBean().hasOpsEntityPermission("NewCompany", null, null);
	    		//        if () {
	    		if (hasp) {
	    			String NewCompanyMsg  = approveNewCompany(bean,personnelBean);
	    			if( NewCompanyMsg == null ) {
	    				bean.setDatePaymentTermsApproved(new Date());
	    				bean.setPaymentTermsApprover(new BigDecimal(personnelBean.getPersonnelId()));
	    				bean.setCustomerRequestStatus("Pending Financial Approval");
	    				flag = true;
	    			}
	    			else {
	    				bean.setCustomerRequestStatus("Pending New Company Approval");
	    			}
	    		}
	    		else 
	    			bean.setCustomerRequestStatus("Pending New Company Approval");
	    	}
	    	else 
	    		bean.setCustomerRequestStatus("Pending Financial Approval");
	    	sendemail = true;
		    updateBeanColl(beancoll,bean);
		    factory.update(bean,flag,locale);
		    if( sendemail ) this.sendNotificationMail(bean, personnelBean);
	    }

	    return bean.getCustomerRequestId();
  }

  
  private String updateBeanColl(Vector beancoll,CustomerAddRequestViewBean bean) throws BaseException { 

	  String ErrorMsg = "";
  
      	//      if ("saveContact".equals(uAction) ) 
        { 
            Collection<CustomerContactAddRequestBean> beans = (Collection<CustomerContactAddRequestBean>) beancoll.get(0);
            deleteCustomerContact(bean);
            for(CustomerContactAddRequestBean sbean:beans) {
            	if( "New".equals(sbean.getChanged()) ) ErrorMsg += addCustomerContact(sbean);
            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += addCustomerContact(sbean);
//            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += updateCustomerContact(sbean);
//            	if( "Remove".equals(sbean.getChanged()) ) ErrorMsg += deleteCustomerContact(sbean);
            }
        }
//        if ("saveShipto".equals(uAction) ) 
        { 
//        	if( "Remove".equals(sbean.getChanged()) ) 
        	ErrorMsg += deleteCustomerShipto(bean);
            Collection<CustomerShiptoAddRequestBean> beans1 = (Collection<CustomerShiptoAddRequestBean>) beancoll.get(1);
            for(CustomerShiptoAddRequestBean sbean:beans1) {
            	if( "None".equals(sbean.getShipToStateAbbrev())) sbean.setShipToStateAbbrev("");
            	if( "New".equals(sbean.getChanged()) ) ErrorMsg += addCustomerShipto(sbean);
            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += addCustomerShipto(sbean);
//            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += updateCustomerShipto(sbean);
//            	if( "Remove".equals(sbean.getChanged()) ) ErrorMsg += deleteCustomerShipto(sbean);
            }
        }
//      if ("saveCarrier".equals(uAction) ) 
        { 
//        	if( "Remove".equals(sbean.getChanged()) ) 
        		ErrorMsg += deleteCustomerCarrier(bean);
            Collection<CustomerCarrierAddRequestBean> beans2 = (Collection<CustomerCarrierAddRequestBean>) beancoll.get(2);
            for(CustomerCarrierAddRequestBean sbean:beans2) {
            	if( "New".equals(sbean.getChanged()) ) ErrorMsg += addCustomerCarrier(sbean);
            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += addCustomerCarrier(sbean);
//            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += updateCustomerShipto(sbean);
//            	if( "Remove".equals(sbean.getChanged()) ) ErrorMsg += deleteCustomerShipto(sbean);
            }
        }
//      Tax 
        { 
//        	if( "Remove".equals(sbean.getChanged()) ) 
        		ErrorMsg += deleteCustomerTax(bean);
            Collection<CustomerTaxExemptAddReqBean> beans3 = (Collection<CustomerTaxExemptAddReqBean>) beancoll.get(3);
            for(CustomerTaxExemptAddReqBean sbean:beans3) {
            	if( "None".equals(sbean.getStateAbbrev())) sbean.setStateAbbrev("");
            	if( "New".equals(sbean.getChanged()) ) ErrorMsg += addCustomerTax(sbean);
            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += addCustomerTax(sbean);
//            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += updateCustomerShipto(sbean);
//            	if( "Remove".equals(sbean.getChanged()) ) ErrorMsg += deleteCustomerShipto(sbean);
            }
        }
//      Bill To Entity PG 
        { 
//        	if( "Remove".equals(sbean.getChanged()) ) 
        		ErrorMsg += deleteCustomerEntityPG(bean);
            Collection<CustEntPriceGpAddRequestBean> beans4 = (Collection<CustEntPriceGpAddRequestBean>) beancoll.get(4);
            for(CustEntPriceGpAddRequestBean sbean:beans4) {
            	if( "New".equals(sbean.getChanged()) ) ErrorMsg += addCustomerEntityPG(sbean);
// default to changed.            	
            	if( isBlank(sbean.getChanged()) ||"Y".equals(sbean.getChanged() ) )ErrorMsg += addCustomerEntityPG(sbean);
//            	if( "Y".equals(sbean.getChanged()) ) ErrorMsg += updateCustomerShipto(sbean);
//            	if( "Remove".equals(sbean.getChanged()) ) ErrorMsg += deleteCustomerShipto(sbean);
            }
        }
                
        return ErrorMsg;

}
  
  
  private boolean needPaymentTermsApproval(String paymentTerms) throws
      BaseException {
	  
    SearchCriteria criteria = new SearchCriteria("paymentTerms",
                                                 SearchCriterion.EQUALS,
                                                 paymentTerms);
    criteria.addCriterion("status", SearchCriterion.EQUALS, "ACTIVE");
    criteria.addCriterion("approvalRequired", SearchCriterion.EQUALS, "Y");
    	
    Collection c = factory.setBean(new VvPaymentTermsBean()).select(criteria, null,"VV_PAYMENT_TERMS");
    return c.size() > 0;
  }

  
  public String approveNewComopanyProc(CustomerAddRequestViewBean bean,PersonnelBean personnelBean,Vector beancoll) throws
  BaseException {
	  CustomerAddRequestBeanFactory factory = new CustomerAddRequestBeanFactory(dbManager);
	  if (personnelBean.getPermissionBean().hasOpsEntityPermission("NewCompany", null, null)) 
	  {
		  ResourceLibrary library = new ResourceLibrary(
				  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		  String errorMsg = library.getString("generic.error");

		  try {
			  updateBeanColl(beancoll,bean);
			  String NewCompanyMsg  = approveNewCompany(bean,personnelBean);
			  if( NewCompanyMsg == null ) {
				  bean.setPaymentTermsApprover(new BigDecimal(personnelBean.getPersonnelId()));
				  bean.setDatePaymentTermsApproved(new Date());
				  bean.setCustomerRequestStatus("Pending Financial Approval");
				  factory.update(bean,true,locale); // update payment approved date.
			  }
		  } catch(Exception ex){
			  return errorMsg;
		  }
		  this.sendNotificationMail(bean, personnelBean);
	  }
	  return null;
  }
  
  public String approveNewCompany(CustomerAddRequestViewBean bean,
		  PersonnelBean personnelBean) throws
		  BaseException {

	  CustomerAddRequestBeanFactory factory = new CustomerAddRequestBeanFactory(dbManager);
//	  if (personnelBean.getPermissionBean().hasOpsEntityPermission("NewCompany", null, null) ) 
      if ( true )
	  {
		  boolean hasError = false;

		  Collection c = null;
		  try {
			  bean.setApprover(new BigDecimal(personnelBean.getPersonnelId()));
			  //bean.setCustomerRequestStatus("Approved");
			  //bean.setDateApproved(new Date());
//			  factory.update(bean,false); // don't update don't update payment approved date.
			  Collection inArgs =
			  buildProcedureInput(
	        			  bean.getCompanyId(),
	        			  bean.getCompanyName(),
	        			  bean.getCompanyShortName()
	          );

			  Collection outArgs = new Vector(1);
			  outArgs.add(new Integer(java.sql.Types.VARCHAR));
			  c = dbManager.doProcedure("pkg_customer_setup.add_company", inArgs, outArgs);
		  } catch(Exception ex){
			  hasError = true;
		  }
		  String procResult = "";
		  if( c != null ) {
			  Iterator it = c.iterator();
			  while(it.hasNext()) {
				  procResult =  (String)it.next();
			  }
			  if(procResult != null && procResult.length() > 0 && !"OK".equalsIgnoreCase(procResult)) {
				  hasError = true;
				  log.error("pkg_contract_setup.add_company returned:" + procResult);
			  }
			  if(log.isDebugEnabled()) {
				  log.debug("pkg_contract_setup.add_company returned:" + procResult);
			  }
		  }
		  if( hasError ) {
			  ResourceLibrary library = new ResourceLibrary(
					  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			  String errorMsg = library.getString("generic.error");
			  return errorMsg;
		  }
//		  this.sendNotificationMail(bean, personnelBean);
	  }
	  return null;
  }

  
  public String approveCustomerRequest(CustomerAddRequestViewBean bean,PersonnelBean personnelBean,Vector beancoll) throws
  BaseException {

	  CustomerAddRequestBeanFactory factory = new CustomerAddRequestBeanFactory(dbManager);
	  if (personnelBean.getPermissionBean().hasOpsEntityPermission("NewCustomerApprover", null, null) ) 
	  {
		  updateBeanColl(beancoll,bean);

		  boolean hasError = false;

		  Collection c = null;
		  try {
			  bean.setApprover(new BigDecimal(personnelBean.getPersonnelId()));
			  bean.setCustomerRequestStatus("Approved");
			  bean.setDateApproved(new Date());
			  factory.update(bean,false,locale); // don't update don't update payment approved date.
			  Collection inArgs = new Vector();
			  inArgs.add(bean.getCustomerRequestId());

			  //      inArgs.add(bean.getCustomerRequestStatus());
			  // update sap vendero code.
			  if( bean.getSapCustomerCode().length() == 0 )
				  inArgs.add("Y");
			  else
				  inArgs.add("N");

			  inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));

			  Collection outArgs = new Vector(1);
			  outArgs.add(new Integer(java.sql.Types.VARCHAR));
			  //      if( "Activate".equals(bean.getCustomerRequestType()) || "Modify".equals(bean.getCustomerRequestType() ) )
			  //       	c = dbManager.doProcedure("pkg_contract_setup.modify_customer", inArgs, outArgs);
			  //      else
			  c = dbManager.doProcedure("pkg_customer_setup.create_customer_from_request", inArgs, outArgs);
		  } catch(Exception ex){
			  hasError = true;
		  }
		  String procResult = "";
		  if( c != null ) {
			  Iterator it = c.iterator();
			  while(it.hasNext()) {
				  procResult =  (String)it.next();
			  }
			  if(procResult != null && procResult.length() > 0 && !"OK".equalsIgnoreCase(procResult)) {
				  hasError = true;
				  log.error("pkg_contract_setup.create_customer_from_request returned:" + procResult);
			  }
			  if(log.isDebugEnabled()) {
				  log.debug("pkg_contract_setup.create_customer_from_request returned:" + procResult);
			  }
		  }
		  if( hasError ) {
			  ResourceLibrary library = new ResourceLibrary(
					  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			  String errorMsg = library.getString("generic.error");
			  bean.setApprover(null);
			  bean.setCustomerRequestStatus("Pending Financial Approval");
			  bean.setDateApproved(null);
			  try{
				  factory.update(bean,false,locale); // don't update transaction date, don't update payment approved date.
			  }
			  catch(Exception ex){}
			  return errorMsg+"\n"+procResult;
		  }
		  this.sendNotificationMail(bean, personnelBean);
	  }
	  return null;
  }

  public String rejectCustomerRequest(CustomerAddRequestViewBean bean,PersonnelBean personnelBean,Vector beancoll) throws
  BaseException {
	  CustomerAddRequestBeanFactory factory = new CustomerAddRequestBeanFactory(dbManager);
	  if (personnelBean.getPermissionBean().hasOpsEntityPermission("NewCustomerApprover", null, null) ) 
	  {

		  ResourceLibrary library = new ResourceLibrary(
				  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		  String errorMsg = library.getString("generic.error");

		  try {
			  updateBeanColl(beancoll,bean);
			  bean.setApprover(new BigDecimal(personnelBean.getPersonnelId()));
			  bean.setCustomerRequestStatus("Rejected");
			  bean.setDateApproved(new Date());
			  factory.update(bean,false,locale); // don't update transaction date, don't update payment approved date.
		  } catch(Exception ex){
			  return errorMsg;
		  }
		  this.sendNotificationMail(bean, personnelBean);
	  }
	  return null;
  }

  public void sendNotificationMail(CustomerAddRequestViewBean bean,
		  PersonnelBean personnelBean) {
	  try {
		  PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		  Collection c = null;
		  String subject = null;
		  String message = null;
		  String pre = "New Customer Request for ";
		  if( "Pending New Company Approval".equals(bean.getCustomerRequestType()) )
			  pre = "Request to create a New Company for ";
		  if( "Pending Financial Approval".equals(bean.getCustomerRequestType()) )
			  pre = "Request to approve New Customer for ";

		  //if( "Activate".equals(bean.getCustomerRequestType()) )
		  //pre = "Request to activate customer ";
		  //if( "Modify".equals(bean.getCustomerRequestType()) )
		  //pre = "Request to modify customer ";
//		  UserOpsEntityHubIgOvBean
		  Collection<UserOpsEntityHubIgOvBean> uc = (Collection<UserOpsEntityHubIgOvBean>)personnelBean.getOpsHubIgOvBeanCollection();
		  String[] list = new String[uc.size()]; 
		  int ii = 0 ;
		  
		  ResourceLibrary resource = new ResourceLibrary("label");
          String hostUrl = resource.getString("label.hosturl");
          
		  for( UserOpsEntityHubIgOvBean ub: uc) 
			  list[ii++] = ub.getOpsEntityId();
		  
		  if ("Pending New Company Approval".equalsIgnoreCase(bean.getCustomerRequestStatus())) {
			  c = factory.selectDistinctUserByOpsPermission("NewCompany",list);
			  subject = pre + bean.getCustomerName() + ", Requested by " + bean.getRequesterName() + " - Waiting New Company Approval";
			  message = pre + bean.getCustomerName() + ", Requested by " + bean.getRequesterName() + " - Waiting New Company Approval \n\n" +
			  "Please click on the link below.\n\n" +
			  hostUrl+"/tcmIS/distribution/customerrequestupdate.do?uAction=viewrequestdetail&customerRequestId=" + bean.getCustomerRequestId() + "\n\n" +
			  "Notes:\n" + bean.getCustomerNotes();
		  }
		  else if ("Pending Financial Approval".equalsIgnoreCase(bean.
				  getCustomerRequestStatus())) {
			  c = factory.selectDistinctUserByOpsPermission("NewCustomerApprover",list);
			  subject = pre + bean.getCustomerName() + ", Requested by " + bean.getRequesterName() + " - Waiting Financial Approval";
			  message = pre + bean.getCustomerName() + ", Requested by " + bean.getRequesterName() + " - Waiting Financial Approval \n\n" +
			  "Please click on the link below.\n\n" +
			  hostUrl+"/tcmIS/distribution/customerrequestupdate.do?uAction=viewrequestdetail&customerRequestId=" + bean.getCustomerRequestId() + "\n\n" +
			  "Notes:\n" + bean.getCustomerNotes();
		  }
		  // change to the requester email
		  else if ("Approved".equalsIgnoreCase(bean.getCustomerRequestStatus())) {
			  c = factory.selectDistinctEmail(new SearchCriteria("personnelId", SearchCriterion.EQUALS, "" + bean.getRequester()));
			  subject = pre + bean.getCustomerName() + " Approved";
			  message = pre + bean.getCustomerName() + " has been Approved. \n\n" +
			  "Please click on the link below.\n\n" +
			  hostUrl+"/tcmIS/distribution/customerrequestupdate.do?uAction=viewrequestdetail&customerRequestId=" + bean.getCustomerRequestId() + " ";
		  }
		  // change to the requester email
		  else if ("Rejected".equalsIgnoreCase(bean.getCustomerRequestStatus())) {
			  c = factory.selectDistinctEmail(new SearchCriteria("personnelId", SearchCriterion.EQUALS, "" + bean.getRequester()));
			  subject = pre + bean.getCustomerName() + " Rejected";
			  message = pre + bean.getCustomerName() + " has been Rejected. \n\n" +
			  "Please click on the link below.\n\n" +
			  hostUrl+"/tcmIS/distribution/customerrequestupdate.do?uAction=viewrequestdetail&customerRequestId="  + bean.getCustomerRequestId() + "\n\n" +
			  "Reason:\n" + bean.getCustomerNotes();
		  }
		  String[] to = new String[c.size()];
		  Iterator it = c.iterator();
		  for (int i = 0; it.hasNext(); i++) {
			  PersonnelBean b = (PersonnelBean) it.next();
			  to[i] = b.getEmail();
		  }
		  String[] cc = new String[0];
		  if(log.isDebugEnabled()) {
			  for(int i=0; i<to.length;i++) {
				  log.debug("Sending email to:" + to[i]);
			  }
		  }

		  //MailProcess.sendEmail("mwennberg@haastcm.com", "", MailProcess.DEFAULT_FROM_EMAIL,subject, message);
		  MailProcess.sendEmail(to, cc, subject, message, true);
	  }
	  catch(Exception e) {
		  log.error("Error sending email.", e);
	  }
  }
  
  public ExcelHandler getCustomerRequestExcel(LogisticsInputBean inputBean) throws
  NoDataException, BaseException, Exception {

  Vector<CustomerAddRequestViewBean> data = (Vector<CustomerAddRequestViewBean>)getSearchData( inputBean);
ResourceLibrary library = new ResourceLibrary(
		"com.tcmis.common.resources.CommonResources", this.getLocaleObject());

ExcelHandler pw = new ExcelHandler(library);

pw.addTable();
//write column headers
pw.addRow();
/*Pass the header keys for the Excel.*/
String[] headerkeys = {
"label.requestid","label.status","label.requestor", "label.customername","label.comments","label.requestdate",
"label.paymentterms",
"label.approvedby", "label.approvedon",
"label.sapvendorcode","label.customerid"};
/*This array defines the type of the excel column.
0 means default depending on the data type. */
int[] types = {
pw.TYPE_NUMBER,0,0,0,pw.TYPE_PARAGRAPH, pw.TYPE_CALENDAR,
0,0,pw.TYPE_CALENDAR
};

int[] widths = {
		0,0,15,20,0,0,
		0,20
		};

/*This array defines the horizontal alignment of the data in a cell.
0 means excel defaults the horizontal alignemnt by the data type.*/

pw.applyColumnHeader(headerkeys, types, widths, null );

// now write data
//int i = 1;
String indefinite = library.getString("label.indefinite");
for (CustomerAddRequestViewBean member : data) {

  pw.addRow();

  pw.addCell(member.getCustomerRequestId());
  pw.addCell(member.getCustomerRequestStatus());
  pw.addCell(member.getRequesterName());
  pw.addCell(member.getCustomerName());
  pw.addCell(member.getCustomerNotes());
  pw.addCell(member.getTransactionDate());
  pw.addCell(member.getPaymentTerms());
  pw.addCell(member.getApproverName());
  pw.addCell(member.getDateApproved());
  pw.addCell(member.getSapCustomerCode());
  pw.addCell(member.getCustomerId());
  }
	return pw;
  }

 public String addCustomerShipto(CustomerShiptoAddRequestBean sbean) throws BaseException {
	  CustomerShiptoAddRequestBeanFactory factory = new CustomerShiptoAddRequestBeanFactory(dbManager);
	  factory.insert(sbean);
  	  return ""; 
  }
  public Collection getCustomerShipto(BigDecimal customerRequestId) throws BaseException {
	  factory.setBean(new CustomerShiptoAddRequestBean());
	  return factory.selectQuery("select * from CUSTOMER_SHIPTO_ADD_REQ_VIEW where customer_request_id = " + customerRequestId);
  }

  public String updateCustomerShipto(CustomerShiptoAddRequestBean sbean) throws BaseException {
	  CustomerShiptoAddRequestBeanFactory factory = new CustomerShiptoAddRequestBeanFactory(dbManager);
  	  factory.update(sbean);
  	  return "";
  }
  public String deleteCustomerShipto(CustomerAddRequestViewBean bean) throws BaseException {
	  CustomerShiptoAddRequestBeanFactory factory = new CustomerShiptoAddRequestBeanFactory(dbManager);
	  SearchCriteria sc = new SearchCriteria("customerRequestId", SearchCriterion.EQUALS,bean.getCustomerRequestId().toString());
//	  sc.addCriterion("shipToAddressLine1", SearchCriterion.EQUALS, sbean.getShipToAddressLine1());
//	  sc.addCriterion("shipToAddressLine2", SearchCriterion.EQUALS, sbean.getShipToAddressLine2());
  	  factory.delete(sc);
  	return ""; 
  }

  public String addCustomerCarrier(CustomerCarrierAddRequestBean sbean) throws BaseException {
	  CustomerCarrierAddRequestBeanFactory factory = new CustomerCarrierAddRequestBeanFactory(dbManager);
	  factory.insert(sbean);
  	  return ""; 
  }
  public String addCustomerEntityPG(CustEntPriceGpAddRequestBean sbean) throws BaseException {
	  CustEntPriceGpAddRequestBeanFactory factory = new CustEntPriceGpAddRequestBeanFactory(dbManager);
	  factory.insert(sbean);
  	  return ""; 
  }
  public String addCustomerTax(CustomerTaxExemptAddReqBean sbean) throws BaseException {
	  CustomerTaxExemptAddReqBeanFactory factory = new CustomerTaxExemptAddReqBeanFactory(dbManager);
	  factory.insert(sbean);
  	  return ""; 
  }
  public Collection getCustomerCarrier(BigDecimal customerRequestId) throws BaseException {
	  factory.setBean(new CustomerCarrierAddRequestBean());
	  return factory.selectQuery("select * from CUSTOMER_Carrier_ADD_REQUEST where customer_request_id = " + customerRequestId);
  }
  public Collection getCustomerTax(BigDecimal customerRequestId) throws BaseException {
	  factory.setBean(new CustomerTaxExemptAddReqBean());
	  return factory.selectQuery("select * from customer_tax_exempt_add_req_vw where customer_request_id = " + customerRequestId);
  }
  public Collection getCustomerEntityPG(BigDecimal customerRequestId) throws BaseException {
	  factory.setBean(new CustEntPriceGpAddRequestBean());
	  return factory.selectQuery("select * from CUST_ENT_PRICE_GP_ADD_REQUEST where customer_request_id = " + customerRequestId);
  }
  public String updateCustomerCarrier(CustomerCarrierAddRequestBean sbean) throws BaseException {
	  CustomerCarrierAddRequestBeanFactory factory = new CustomerCarrierAddRequestBeanFactory(dbManager);
  	  factory.update(sbean);
  	  return "";
  }
  public String updateCustomerEntityPG(CustEntPriceGpAddRequestBean sbean) throws BaseException {
	  CustEntPriceGpAddRequestBeanFactory factory = new CustEntPriceGpAddRequestBeanFactory(dbManager);
  	  factory.update(sbean);
  	  return "";
  }
  public String deleteCustomerCarrier(CustomerAddRequestViewBean bean) throws BaseException {
	  CustomerCarrierAddRequestBeanFactory factory = new CustomerCarrierAddRequestBeanFactory(dbManager);
	  SearchCriteria sc = new SearchCriteria("customerRequestId", SearchCriterion.EQUALS,bean.getCustomerRequestId().toString());
//	  sc.addCriterion("CarrierAddressLine1", SearchCriterion.EQUALS, sbean.getCarrierAddressLine1());
//	  sc.addCriterion("CarrierAddressLine2", SearchCriterion.EQUALS, sbean.getCarrierAddressLine2());
  	  factory.delete(sc);
  	return ""; 
  }
  public String deleteCustomerEntityPG(CustomerAddRequestViewBean bean) throws BaseException {
	  CustEntPriceGpAddRequestBeanFactory factory = new CustEntPriceGpAddRequestBeanFactory(dbManager);
	  SearchCriteria sc = new SearchCriteria("customerRequestId", SearchCriterion.EQUALS,bean.getCustomerRequestId().toString());
//	  sc.addCriterion("CarrierAddressLine1", SearchCriterion.EQUALS, sbean.getCarrierAddressLine1());
//	  sc.addCriterion("CarrierAddressLine2", SearchCriterion.EQUALS, sbean.getCarrierAddressLine2());
  	  factory.delete(sc);
  	return ""; 
  }
  public String deleteCustomerTax(CustomerAddRequestViewBean bean) throws BaseException {
	  CustomerTaxExemptAddReqBeanFactory factory = new CustomerTaxExemptAddReqBeanFactory(dbManager);
	  SearchCriteria sc = new SearchCriteria("customerRequestId", SearchCriterion.EQUALS,bean.getCustomerRequestId().toString());
  	  factory.delete(sc);
  	return ""; 
  }
     
}