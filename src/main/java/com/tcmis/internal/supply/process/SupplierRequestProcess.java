package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.*;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
//import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
//import com.tcmis.internal.distribution.factory.CustomerAddRequestBeanFactory;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.supply.beans.*;
import com.tcmis.internal.supply.factory.*;

/******************************************************************************
 * Process for searching new supplier requests
 * @version 1.0
 *****************************************************************************/
public class SupplierRequestProcess
    extends GenericProcess {

  public SupplierRequestProcess(String client,Locale locale) {
    super(client,locale);
  }

  public Collection getSearchData(LogisticsInputBean bean) throws
      BaseException {

    SearchCriteria criteria = new SearchCriteria();
    
    if( !isBlank(bean.getStatus()) ) {
    	if("All Except Draft".equals(bean.getStatus())) 
    		criteria.addCriterion("supplierRequestStatus", SearchCriterion.NOT_EQUAL, "Draft");
    	else 
    		criteria.addCriterion("supplierRequestStatus",SearchCriterion.EQUALS, bean.getStatus());
    }
    
    criteria.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument() );
    if( !isBlank(bean.getDorfrom()) )
        criteria.addCriterion("transactionDate", SearchCriterion.FROM_DATE, bean.getDorfrom() );
    if( !isBlank(bean.getDorto()) )
        criteria.addCriterion("transactionDate", SearchCriterion.TO_DATE, bean.getDorto() );
    
    return 	factory.setBean(new SupplierAddRequestViewBean())
    				.select(criteria,null,"SUPPLIER_ADD_REQUEST_VIEW");
  }

  public SupplierAddRequestViewBean getRequestDetail(BigDecimal id) throws BaseException {

//	  factory.setBeanObject(new SupplierAddRequestViewBean());
	  if(id == null) return null;
	  
	  Collection c = factory.setBean(new SupplierAddRequestViewBean()).select(
			  new SearchCriteria("supplierRequestId",SearchCriterion.EQUALS,""+id),null,"SUPPLIER_ADD_REQUEST_VIEW");
	  if( c.size() !=0 ) return ((Vector<SupplierAddRequestViewBean>)c).get(0);
	  else return null;
  }
  private void copyBean(SupplierAddressViewBean a,SupplierAddRequestBean b) {
/*		private String supplier;
		private String supplierName;
		private String countryAbbrev;
		private String stateAbbrev;
		private String addressLine1;
		private String addressLine2;
		private String addressLine3;
		private String city;
		private String zip;
		private String locationDesc;
		private String clientLocationCode;
		private String mainPhone;
		private String defaultPaymentTerms;
		private String status;
		private String formerSupplierName;
		private String qualificationLevel;
		private String email;
		private String supplierEmail;
		private String statusChangeComments;
		private String newSupplierId;
		private String newSupplierName;
		private String federalTaxId;
		private String eSupplierId;
		private String accountNumber;
		private String transport;
		private String comments;
		private String ok;	
		private String firstName;
		private String lastName;
		private String nickname;
		private String phone;
		private String phoneExtension;
		private String fax;
		private String smallBusiness;
		private String minorityBusiness;
		private String disadvantagedBusiness;
		private String billingLocationId;
		private String isoCertified;
		private String hubWithLocalCertified;
		private String mbeWithLocalCertified;
		private String wbeWithLocalCertified;
		private String sdbWithSbaCertified;
		private String category8aWithSbaCertified;
		private String hubWithSbaCertified;
		private String educationalInstitution;
		private String mbeBlackAmerican;
		private String mbeAsianPacificAmerican;
		private String mbeNativeAmerican;
		private String mbeAsianIndianAmerican;
		private String mbeOther;
		private String womanOwned;
		private String vietNamVeteranOwned;
		private String disabledVeteranOwned;
		private String nonProfitOrganization;
		private String laborSurplusArea;
		private String laborSurplusAndRuralArea;
		private String ruralArea;
		private String debarred;
		private String sapVendorCode;
		private String vatRegistrationNumber;

		// do not auto-generate the getter and setter for the following property:
		private String e_SupplierId; // this is to work around the strange problem with the property named eSupplierId
*/
b.setSupplier(a.getSupplier());
b.setSupplierName(a.getSupplierName());
b.setCountryAbbrev(a.getCountryAbbrev());
b.setStateAbbrev(a.getStateAbbrev());
b.setAddressLine1(a.getAddressLine1());
b.setAddressLine2(a.getAddressLine2());
b.setAddressLine3(a.getAddressLine3());
b.setCity(a.getCity());
b.setZip(a.getZip());
//b.setLocationDesc(a.getLocationDesc());
//b.setClientLocationCode(a.getClientLocationCode());
b.setSupplierMainPhone(a.getMainPhone());
b.setDefaultPaymentTerms(a.getDefaultPaymentTerms());
//b.setStatus(a.getStatus());
b.setFormerSupplierName(a.getFormerSupplierName());
b.setQualificationLevel(a.getQualificationLevel());
//b.setSupplierContactEmail(a.getEmail());
b.setSupplierContactEmail(a.getSupplierEmail());
//b.setSupplierNotes(a.getStatusChangeComments()); // check this.
//b.setNewSupplierId(a.getNewSupplierId());
//b.setNewSupplierName(a.getNewSupplierName());
b.setFederalTaxId(a.getFederalTaxId());
b.setESupplierId(a.getESupplierId());
b.setHaasAccountNumber(a.getAccountNumber()); // check this
//b.setTransport(a.getTransport());
//b.setSupplierNotes(a.getComments());	// check this.
b.setSupplierContactFirstName(a.getFirstName());
b.setSupplierContactLastName(a.getLastName());
b.setSupplierContactNickname(a.getNickname());
b.setSupplierContactPhone(a.getPhone()); 
b.setSupplierContactPhoneExt(a.getPhoneExtension());
b.setSupplierMainFax(a.getFax()); // check this.
b.setSupplierContactFax(a.getFax());
b.setSmallBusiness(a.getSmallBusiness());
b.setMinorityBusiness(a.getMinorityBusiness());
b.setDisadvantagedBusiness(a.getDisadvantagedBusiness());
//b.setBillingLocationId(a.getBillingLocationId());
b.setIso9001Certified(a.getIso9001Certified()); // check this.
//private String as9100Certified;
//private String as9120Certified;
b.setHubWithLocalCertified(a.getHubWithLocalCertified());
b.setMbeWithLocalCertified(a.getMbeWithLocalCertified());
b.setWbeWithLocalCertified(a.getWbeWithLocalCertified());
b.setSdbWithSbaCertified(a.getSdbWithSbaCertified());
b.setCategory8aWithSbaCertified(a.getCategory8aWithSbaCertified());
b.setHubWithSbaCertified(a.getHubWithSbaCertified());
b.setEducationalInstitution(a.getEducationalInstitution());
b.setMbeBlackAmerican(a.getMbeBlackAmerican());
b.setMbeAsianPacificAmerican(a.getMbeAsianPacificAmerican());
b.setMbeNativeAmerican(a.getMbeNativeAmerican());
b.setMbeAsianIndianAmerican(a.getMbeAsianIndianAmerican());
b.setMbeOther(a.getMbeOther());
b.setWomanOwned(a.getWomanOwned());
b.setVietNamVeteranOwned(a.getVietNamVeteranOwned());
b.setDisabledVeteranOwned(a.getDisabledVeteranOwned());
b.setNonProfitOrganization(a.getNonProfitOrganization());
//b.setLaborSurplusArea(a.getLaborSurplusArea());
//b.setLaborSurplusAndRuralArea(a.getLaborSurplusAndRuralArea());
//b.setRuralArea(a.getRuralArea());
b.setDebarred(a.getDebarred());
b.setSapVendorCode(a.getSapVendorCode());
b.setVatRegistrationNumber(a.getVatRegistrationNumber());
b.setTypeOfPurchase(a.getTypeOfPurchase());		
b.setOpsEntityId(a.getOpsEntityId());
	  
  }
  public SupplierAddRequestBean getRequestDetailForModify(SupplierAddRequestBean sbean,String SupplierRequestStatus,PersonnelBean personnelBean ) throws BaseException {
	  String supplier = sbean.getSupplier();
	  SupplierAddRequestBean bean = new SupplierAddRequestBean();
	  String type = sbean.getSupplierRequestType();
      bean.setRequester(new BigDecimal(personnelBean.getPersonnelId()));
	  bean.setSupplierRequestStatus("Draft");
	  bean.setSupplierRequestId(new BigDecimal(dbManager.getOracleSequence("pei_project_id_seq")));
	  bean.setSupplierRequestType(sbean.getSupplierRequestType());
//	  bean.setSupplierRequestStatus(SupplierRequestStatus);//sbean.getSupplierRequestStatus());
	  bean.setRequester(new BigDecimal(personnelBean.getPersonnelId()));
	  if( !isBlank(supplier ) ) {
	  SearchCriteria criteria = new SearchCriteria("supplier",SearchCriterion.EQUALS,supplier);
	  SupplierAddressViewBean abean = ((Vector<SupplierAddressViewBean>) factory.setBean(new SupplierAddressViewBean())
	  						.select(criteria,null,"SUPPLIER_ADDRESS_VIEW")).get(0);
	  
	  copyBean(abean,bean);
	  }
//	  if( "Activate".equals(type) || "Modify".equals(type) )
//    also inserts payment term stuff.	  
//	  else
//	  if( !isBlank(bean.getSupplierRequestId() ) )
		  new SupplierAddRequestBeanFactory(dbManager).insert(bean, locale);
		  if( !isBlank(supplier ) ) {
		  Vector beancoll = new Vector();
		  beancoll.add( getSupplierPaymentForModify(bean.getSupplier())); 
		  beancoll.add( getSupplierIgPaymentForModify(bean.getSupplier())); 
		  updateBeanColl(personnelBean, beancoll,bean); 
		  }
      bean.setRequesterName(personnelBean.getLastName()+", "+personnelBean.getFirstName());
      bean.setRequesterPhone(personnelBean.getPhone());
      bean.setRequesterEmail(personnelBean.getEmail());
	  return bean;
  }

  public SupplierAddRequestBean getRequestDetailForNew(SupplierAddRequestBean sbean,String SupplierRequestStatus,PersonnelBean personnelBean ) throws BaseException {
	  SupplierAddRequestBean bean = new SupplierAddRequestBean();
	  String type = sbean.getSupplierRequestType();
      bean.setRequester(new BigDecimal(personnelBean.getPersonnelId()));
	  bean.setSupplierRequestStatus("Draft");
//	  bean.setSupplierRequestId(new BigDecimal(dbManager.getOracleSequence("pei_project_id_seq")));
	  bean.setSupplierRequestType("New");
//	  bean.setSupplierRequestStatus(SupplierRequestStatus);//sbean.getSupplierRequestStatus());
	  bean.setRequester(new BigDecimal(personnelBean.getPersonnelId()));
//	  if( "Activate".equals(type) || "Modify".equals(type) )
//    also inserts payment term stuff.	  
//	  else
//	  if( !isBlank(bean.getSupplierRequestId() ) )
      bean.setRequesterName(personnelBean.getLastName()+", "+personnelBean.getFirstName());
      bean.setRequesterPhone(personnelBean.getPhone());
      bean.setRequesterEmail(personnelBean.getEmail());
	  return bean;
  }

  
  public Collection getPaymentTermsDropDown() throws BaseException {
	  return factory.setBean( new VvPaymentTermsBean() )
	  				.select(new SearchCriteria("status",SearchCriterion.EQUALS,"ACTIVE"), new SortCriteria("paymentTerms"),"VV_PAYMENT_TERMS");
  }
  public Collection getTypeOfPurchaseDropDown() throws BaseException {
	  return factory.setBean( new VvTypeOfPurchaseBean() ).select(
			  new SearchCriteria("vendorOrigin",SearchCriterion.EQUALS,"TCMIS"), null,"VV_TYPE_OF_PURCHASE");
  }

  public Collection getOperatingEntityDropDown() throws BaseException {
	  return factory.setBean( new com.tcmis.internal.supply.beans.OperatingEntityBean() )
	  				.select(new SearchCriteria(), null,"operating_entity");
  }

  public Collection getQualificationLevelDropDown() throws BaseException {
	  return factory.setBean( new VvQualificationLevelBean() )
    			    .select(new SearchCriteria(),new SortCriteria("qualificationLevelDesc"),"VV_QUALIFICATION_LEVEL");
  }

  public BigDecimal save(SupplierAddRequestBean bean, PersonnelBean personnelBean,String oriDefaultPaymentTerms,Vector beancoll) throws BaseException {
	  boolean flag = false;
	  SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
			  dbManager);
	  PermissionBean pbean = personnelBean.getPermissionBean();
	  if(bean.getSupplierRequestId() == null ) {
		  bean.setSupplierRequestId(new BigDecimal(dbManager.getOracleSequence("pei_project_id_seq")));
		  factory.insert(bean, locale);
	  }
//	  if( pbean.hasOpsEntityPermission("NewPaymentTerms", null, null) ||
//			  pbean.hasOpsEntityPermission("NewCustomerApprover", null, null) ||
//			  pbean.hasInventoryGroupPermission("GenerateOrders", null,null,null ) ) 
	  {
		  updateBeanColl(personnelBean,beancoll,bean);
		  factory.update(bean,flag,locale); // don't update transaction date, don't update payment approved date.
	  }
	  return bean.getSupplierRequestId();
  }

// This is actually submit now.
  public BigDecimal saveSupplierRequest(SupplierAddRequestBean bean, PersonnelBean personnelBean,String oriDefaultPaymentTerms,Vector beancoll) throws BaseException {

    SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
        dbManager);
    String type = bean.getSupplierRequestType();
    if(bean.getSupplierRequestId() == null ) {
    	bean.setSupplierRequestId(new BigDecimal(dbManager.getOracleSequence("pei_project_id_seq")));
    	factory.insert(bean, locale);
    }

    String status = bean.getSupplierRequestStatus();
    PermissionBean pbean = personnelBean.getPermissionBean();//
    boolean upddatedate = false;
    BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId());
    Date today = new Date();

//    if( "Pending Payment Terms".equals(status) ) {
//    	if( upddatedate == setApprovedOnInfoBeanColl(beancoll, bean, pbean,today,pid ) ) {
//    		bean.setDatePaymentTermsApproved(today);
//    		bean.setPaymentTermApprover(pid);
//    		bean.setSupplierRequestStatus("Pending Approval");
//    		this.sendNotificationMail(bean, personnelBean);
//    	}
//    	updateBeanColl(beancoll,bean);
 ///   	factory.update(bean,upddatedate); // true then update transaction date
   // 	return bean.getSupplierRequestId();
    //}
    
    if ( "Draft".equals(status) ) {
//        if ( isBlank(bean.getSupplierRequestId()) || ( ("Activate".equals(type) || "Modify".equals(type))&&"Draft".equals(status)) ) {
//      if( isBlank(bean.getSupplierRequestId()) ) 
//    	  bean.setSupplierRequestId(new BigDecimal(dbManager.getOracleSequence("pei_project_id_seq")));
      if ((!("Modify".equals(type)&&bean.getDefaultPaymentTerms().equals(oriDefaultPaymentTerms)))
    		  &&this.needPaymentTermsApproval(bean.getDefaultPaymentTerms())) {
    	  bean.setSupplierRequestStatus("Pending Payment Terms");
      }
      else {
    	  bean.setSupplierRequestStatus("Pending Approval");
      }
      updateBeanColl(personnelBean,beancoll,bean);
      factory.update(bean,upddatedate,locale);
      this.sendNotificationMail(bean, personnelBean,beancoll);
    }
    return bean.getSupplierRequestId();
  }

  public BigDecimal delete(SupplierAddRequestBean bean,PersonnelBean personnelBean) throws BaseException {
	  SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(dbManager);
	    boolean flag = false;
	    PermissionBean pbean = personnelBean.getPermissionBean();
//		if( pbean.hasOpsEntityPermission("NewCompany", null, null) ||
//			pbean.hasOpsEntityPermission("NewCustomerApprover", null, null) ||
//			pbean.hasInventoryGroupPermission("GenerateOrders", null,null,null ) ) 
		{
				deleteBeanColl(bean);
	    		factory.delete(bean);
	    }
//	    if( sendemail ) this.sendNotificationMail(bean, personnelBean);

	    return bean.getSupplierRequestId();
}

  private void deleteBeanColl(SupplierAddRequestBean bean) throws BaseException { 

	  deletePaymentTemrs(bean);
	  deleteIgException(bean);
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

  
  public String changePaymentTerms(SupplierAddRequestBean bean,
		  PersonnelBean personnelBean,Vector beancoll) throws
		  BaseException {
	  SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
			  dbManager);
	  if ( personnelBean.getPermissionBean().hasFacilityPermission("Sourcing", null, null) ||
		   personnelBean.getPermissionBean().hasInventoryGroupPermission("BuyOrder", null, null,null) ) {
		  ResourceLibrary library = new ResourceLibrary(
				  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		  String errorMsg = library.getString("generic.error");

		  try {
//			  bean.setPaymentTermApprover(new BigDecimal(personnelBean.getPersonnelId()));
//			  bean.setDatePaymentTermsApproved(new Date());
			  bean.setSupplierRequestStatus("Pending Payment Terms");
			  updateBeanColl(personnelBean,beancoll,bean);
			  factory.updatePaymentTerms(bean);
		  } catch(Exception ex){
			  return errorMsg;
		  }
		  if( 1 == this.sendNotificationMail(bean, personnelBean,beancoll) && !"Activate".equals(bean.getSupplierRequestType()) ) { // continue to next step 
			  bean.setSupplierRequestStatus("Pending Approval");
			  factory.update(bean,false,locale); // don't update payment approve date
	  	  }
	  }
	  return null;
  }

  public String approvePaymentTerms(SupplierAddRequestBean bean,
                                  PersonnelBean personnelBean,Vector beancoll) throws
      BaseException {
    SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
        dbManager);
    PermissionBean pbean = personnelBean.getPermissionBean();//
    boolean upddatedate = false;
    BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId());
    Date today = new Date();
    if (pbean.hasOpsEntityPermission("NewPaymentTerms", null, null)
    		&& bean.getRequester().compareTo(personnelBean.getPersonnelIdBigDecimal()) != 0) {
    	ResourceLibrary library = new ResourceLibrary(
    			"com.tcmis.common.resources.CommonResources", this.getLocaleObject());
    	String errorMsg = library.getString("generic.error");
    	try {
			 upddatedate = setApprovedOnInfoBeanColl(beancoll, bean, pbean,today,pid );
			 if( upddatedate  ) {
				 bean.setPaymentTermApprover(new BigDecimal(personnelBean.getPersonnelId()));
    			bean.setDatePaymentTermsApproved(new Date());
    			bean.setSupplierRequestStatus("Pending Approval");
    	    	this.sendNotificationMail(bean, personnelBean,beancoll);
    		}
    		updateBeanColl(personnelBean,beancoll,bean);
			 factory.update(bean,upddatedate,locale); // update payment approved date.
    	} catch(Exception ex){
    		return errorMsg;
    	}
    }
    return null;
  }
  
  public String approvePaymentTermsDirect(SupplierAddRequestBean bean,
		  PersonnelBean personnelBean,Vector beancoll) throws
		  BaseException {
	  SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
			  dbManager);
	    PermissionBean pbean = personnelBean.getPermissionBean();//
	    boolean upddatedate = false;
	    BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId());
	    Date today = new Date();
	  
	  if (personnelBean.getPermissionBean().hasOpsEntityPermission("NewSuppApprover", null, null)
			  && bean.getRequester().compareTo(personnelBean.getPersonnelIdBigDecimal()) != 0)
	  {
		  ResourceLibrary library = new ResourceLibrary(
				  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		  String errorMsg = library.getString("generic.error");

		  try {
			  updateBeanColl(personnelBean,beancoll,bean);
			  //upddatedate = setApprovedOnInfoBeanColl(beancoll, bean, pbean,today,pid);
			  //if( upddatedate) {
				  //bean.setDatePaymentTermsApproved(today);
				  //bean.setPaymentTermApprover(pid);
				  //bean.setSupplierRequestStatus("Approved");
				  //factory.approvePaymentTermsDirect(bean,true); // update payment approved date.
//				  PROCEDURE p_approve_payment_terms(
//					      a_supplier_request_id   IN       supplier_add_request.supplier_request_id%TYPE,
//					      a_error_message         IN OUT   VARCHAR2
//					   ) IS
				  Collection outArgs = new Vector(1);
				  outArgs.add(new Integer(java.sql.Types.VARCHAR));
				  errorMsg = runProc(buildProcedureInput( bean.getSupplierRequestId() ),outArgs,"pkg_contract_setup.p_approve_payment_terms");
			  //}

		  } catch(Exception ex){
			  return errorMsg;
		  }
		  //this.sendNotificationMail(bean, personnelBean,null);
	  }
	  return null;
  }
/*
  public String approvePaymentTermsActivation(SupplierAddRequestBean bean,
		  PersonnelBean personnelBean) throws
		  BaseException {
	  SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
			  dbManager);
	  if (personnelBean.getPermissionBean().hasOpsEntityPermission(
			  "NewPaymentTerms", null, null)) {
		  ResourceLibrary library = new ResourceLibrary(
				  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		  String errorMsg = library.getString("generic.error");

		  try {
			  bean.setPaymentTermApprover(new BigDecimal(personnelBean.getPersonnelId()));
			  bean.setDatePaymentTermsApproved(new Date());
			  bean.setSupplierRequestStatus("Pending Approval");
			  factory.approvePaymentTermsDirect(bean,false); // update payment approved date.
		  } catch(Exception ex){
			  return errorMsg;
		  }
		  this.sendNotificationMail(bean, personnelBean);
	  }
	  return null;
  }
*/
  public String approveSupplierRequest(SupplierAddRequestBean bean,
                                     PersonnelBean personnelBean,Vector beancoll) throws
      BaseException {

	SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
        dbManager);
    if (personnelBean.getPermissionBean().hasOpsEntityPermission("NewSuppApprover", null, null) 
    		&& bean.getRequester().compareTo(personnelBean.getPersonnelIdBigDecimal()) != 0) {
     boolean hasError = false;

     Collection c = null;
     try {
      bean.setApprover(new BigDecimal(personnelBean.getPersonnelId()));
      bean.setSupplierRequestStatus("Approved");
      bean.setDateApproved(new Date());
//      updateBeanColl(beancoll,bean);
      factory.update(bean,false,locale); // don't update don't update payment approved date.
      Collection inArgs = new Vector();
      inArgs.add(bean.getSupplierRequestId());
      
//      inArgs.add(bean.getSupplierRequestStatus());
      // update sap vendero code.
      if( bean.getSapVendorCode().length() == 0 )
    	  inArgs.add("Y");
      else
          inArgs.add("N");
    	  
      inArgs.add(personnelBean.getPersonnelId());

      Collection outArgs = new Vector(1);
      outArgs.add(new Integer(java.sql.Types.VARCHAR));
      if( "Activate".equals(bean.getSupplierRequestType()) || "Modify".equals(bean.getSupplierRequestType() ) )
       	c = dbManager.doProcedure("pkg_contract_setup.modify_supplier", inArgs, outArgs);
      else
      	c = dbManager.doProcedure("pkg_contract_setup.create_supplier_from_request", inArgs, outArgs);
      } catch(Exception ex){
    	  hasError = true;
      }
	  String procResult = "";
      if( c != null ) {
    	  Iterator it = c.iterator();
    	  while(it.hasNext()) {
    		  procResult =  (String)it.next();
    	  }
    	  if(procResult != null && procResult.length() > 0) {
    		  hasError = true;
    		  log.error("pkg_contract_setup.create_supplier_from_request returned:" + procResult);
    	  }
    	  if(log.isDebugEnabled()) {
    		  log.debug("pkg_contract_setup.create_supplier_from_request returned:" + procResult);
    	  }
		  approvePaymentTermsDirect(bean,personnelBean,beancoll);
		}
      if( hasError ) {
    		 ResourceLibrary library = new ResourceLibrary(
    					"com.tcmis.common.resources.CommonResources", this.getLocaleObject());
    	     String errorMsg = library.getString("generic.error");
    	  bean.setApprover(null);
    	  bean.setSupplierRequestStatus("Pending Approval");
    	  bean.setDateApproved(null);
    	  try{
    	      factory.update(bean,false,locale); // don't update transaction date, don't update payment approved date.
    	  }
    	  catch(Exception ex){}
    	  return errorMsg+"\n"+procResult;
      }
      this.sendNotificationMail(bean, personnelBean,null);
    }
    return null;
  }

  public String rejectSupplierRequest(SupplierAddRequestBean bean,
                                     PersonnelBean personnelBean,Vector beancoll) throws
      BaseException {
    SupplierAddRequestBeanFactory factory = new SupplierAddRequestBeanFactory(
        dbManager);
    if (personnelBean.getPermissionBean().hasOpsEntityPermission("NewSuppApprover", null, null) ||
    	personnelBean.getPermissionBean().hasOpsEntityPermission("NewPaymentTerms", null, null)) 
    {
		 ResourceLibrary library = new ResourceLibrary(
					"com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	     String errorMsg = library.getString("generic.error");

 	try {

      bean.setApprover(new BigDecimal(personnelBean.getPersonnelId()));
      bean.setSupplierRequestStatus("Rejected");
      bean.setDateApproved(new Date());
// we will save grid data for rejection too.
      updateBeanColl(personnelBean,beancoll,bean);
      factory.update(bean,false,locale); // don't update transaction date, don't update payment approved date.
 	} catch(Exception ex){
    	  return errorMsg;
      }
      this.sendNotificationMail(bean, personnelBean,null);
    }
    return null;
  }

  private int sendNotificationMail(SupplierAddRequestBean bean,
                                  PersonnelBean personnelBean, Vector beancoll) { 
    try {
      ResourceLibrary resource = new ResourceLibrary("label");
      String hostUrl = resource.getString("label.hosturl");	
    	
      PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
      Collection c = null;
      String subject = null;
      String message = null;
      boolean isActivate = false;
      String pre = "New Supplier Request for ";
		if( "Change Payment Terms".equals(bean.getSupplierRequestType()) )
        		pre = "Request to change payment terms for ";
      if( "Activate".equals(bean.getSupplierRequestType()) ) {
      		pre = "Request to activate supplier ";
            isActivate = true;
      }
      if( "Modify".equals(bean.getSupplierRequestType()) )
      		pre = "Request to modify supplier ";
      if ("Pending Payment Terms".equalsIgnoreCase(bean.getSupplierRequestStatus())) {
// Pass the beancoll if need to send out email.
// Currently beancall is passed only if its coming from Change Payment Terms state or draft state.
// Thus if its coming from Pending Payment Terms state ( not all of the be approved, or new payment term
// are added but the person has no permission to approve, 
// then THE EMAIL WILL NOT BEEN SENT.
    	  if( beancoll == null ) return 1;
    	  HashSet<String> opsmap = new HashSet();
    	  Vector<SuppAddPaymentTermsViewBean> paycoll = (Vector<SuppAddPaymentTermsViewBean>)beancoll.get(0);
    	  Vector<SuppExcAddPaytermsViewBean> exccoll = (Vector<SuppExcAddPaytermsViewBean>) beancoll.get(1);
// for payment terms, if its for activate, everyone gets email.
// if not activate, only new status and current status different get email.    	  
    	  for(SuppAddPaymentTermsViewBean b:paycoll)
    		  if( isActivate || !b.getPaymentTerms().equals(b.getCurrentPaymentTerms()) || !b.getNewStatus().equals(b.getCurrentStatus()))
    		  opsmap.add(b.getOpsEntityId());
    	  for(SuppExcAddPaytermsViewBean b:exccoll)
    		  opsmap.add(b.getOpsEntityId());
    	if( opsmap.size() == 0 )
    		return 1;
    	else
    		c = factory.selectDistinctUserByOpsPermission("NewPaymentTerms",opsmap.toArray());
        subject = pre + bean.getSupplierName() + ", Requested by " + bean.getRequesterName() + " - Waiting Payment Term Approval";
        message = pre + bean.getSupplierName() + ", Requested by " + bean.getRequesterName() + " - Waiting Payment Term Approval \n\n" +
                  "Please click on the link below.\n\n" +
                  hostUrl + "/tcmIS/supply/supplierrequest.do?uAction=viewrequestdetail&supplierRequestId=" + bean.getSupplierRequestId() + "\n\n" +
                  "Notes:\n" + bean.getSupplierNotes();
      }
      else if ("Pending Approval".equalsIgnoreCase(bean.getSupplierRequestStatus())) {
			// Pass the beancoll if need to send out email.
			// Currently beancall is passed only if its coming from Change Payment Terms state or draft state.
			// Thus if its coming from Pending Payment Terms state ( not all of the be approved, or new payment term
			// are added but the person has no permission to approve,
			// then THE EMAIL WILL NOT BEEN SENT.
    	  if( beancoll == null ) return 0;
    	  HashSet<String> opsmap = new HashSet();
    	  Vector<SuppAddPaymentTermsViewBean> paycoll = (Vector<SuppAddPaymentTermsViewBean>)beancoll.get(0);
    	  Vector<SuppExcAddPaytermsViewBean> exccoll = (Vector<SuppExcAddPaytermsViewBean>) beancoll.get(1);
    	  for(SuppAddPaymentTermsViewBean b:paycoll)
				opsmap.add(b.getOpsEntityId());

			for(SuppExcAddPaytermsViewBean b:exccoll)
				opsmap.add(b.getOpsEntityId());

		  c = factory.selectDistinctUserByOpsPermission("NewSuppApprover",opsmap.toArray());
        subject = pre + bean.getSupplierName() + ", Requested by " + bean.getRequesterName() + " - Waiting Approval";
        message = pre + bean.getSupplierName() + ", Requested by " + bean.getRequesterName() + " - Waiting Approval \n\n" +
                  "Please click on the link below.\n\n" +
                  hostUrl + "/tcmIS/supply/supplierrequest.do?uAction=viewrequestdetail&supplierRequestId=" + bean.getSupplierRequestId() + "\n\n" +
                  "Notes:\n" + bean.getSupplierNotes();
      }
      // change to the requester email
      else if ("Approved".equalsIgnoreCase(bean.getSupplierRequestStatus())) {
        c = factory.selectDistinctEmail(new SearchCriteria("personnelId", SearchCriterion.EQUALS, "" + bean.getRequester()));
        subject = pre + bean.getSupplierName() + " Approved";
        message = pre + bean.getSupplierName() + " has been Approved. \n\n" +
                  "Please click on the link below.\n\n" +
                  hostUrl + "/tcmIS/supply/supplierrequest.do?uAction=viewrequestdetail&supplierRequestId=" + bean.getSupplierRequestId() + " ";

          /*BulkMailProcess bulkMailProcess = new	BulkMailProcess(this.getClient());
          bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),subject, message, true);*/
      }
      // change to the requester email
      else if ("Rejected".equalsIgnoreCase(bean.getSupplierRequestStatus())) {
        c = factory.selectDistinctEmail(new SearchCriteria("personnelId", SearchCriterion.EQUALS, "" + bean.getRequester()));
        subject = pre + bean.getSupplierName() + " Rejected";
        message = pre + bean.getSupplierName() + " has been Rejected. \n\n" +
                  "Please click on the link below.\n\n" +
                  hostUrl + "/tcmIS/supply/supplierrequest.do?uAction=viewrequestdetail&supplierRequestId="  + bean.getSupplierRequestId() + "\n\n" +
                  "Reason:\n" + bean.getSupplierNotes();
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
		if (to.length > 0) {
			MailProcess.sendEmail(to, cc, subject, message, true);
		}
	 }
    catch(Exception e) {
      log.error("Error sending email.", e);
		e.printStackTrace();
	 }
    return 0;
  }
  
  public ExcelHandler getSupplierRequestExcel(LogisticsInputBean inputBean) throws
  NoDataException, BaseException, Exception {

  Vector<SupplierAddRequestViewBean> data = (Vector<SupplierAddRequestViewBean>)getSearchData( inputBean);
ResourceLibrary library = new ResourceLibrary(
		"com.tcmis.common.resources.CommonResources", this.getLocaleObject());

ExcelHandler pw = new ExcelHandler(library);

pw.addTable();
//write column headers
pw.addRow();
/*Pass the header keys for the Excel.*/
String[] headerkeys = {
"label.requestid","label.status","label.requestor", "label.suppliername","label.comments","label.enteredon",
"label.paymentterms","label.paymenttermsapprovedby","label.paymenttermsapprovedon","label.approvedby", "label.approvedon",
"label.sapvendorcode","label.supplierid"};
/*This array defines the type of the excel column.
0 means default depending on the data type. */
int[] types = {
pw.TYPE_NUMBER,0,0,0,pw.TYPE_PARAGRAPH, pw.TYPE_CALENDAR,
0,0,pw.TYPE_CALENDAR,0,pw.TYPE_CALENDAR
};

int[] widths = {
		0,0,15,0,0,0,
		0,16,16,10
		};

/*This array defines the horizontal alignment of the data in a cell.
0 means excel defaults the horizontal alignemnt by the data type.*/

pw.applyColumnHeader(headerkeys, types, widths, null );

// now write data
//int i = 1;
String indefinite = library.getString("label.indefinite");
for (SupplierAddRequestViewBean member : data) {

  pw.addRow();

  pw.addCell(member.getSupplierRequestId());
  pw.addCell(member.getSupplierRequestStatus());
  pw.addCell(member.getRequesterName());
  pw.addCell(member.getSupplierName());
  pw.addCell(member.getSupplierNotes());
  pw.addCell(member.getTransactionDate());
  pw.addCell(member.getDefaultPaymentTerms());
  pw.addCell(member.getPaymentTermApproverName());
  pw.addCell(member.getDatePaymentTermsApproved());
  pw.addCell(member.getApproverName());
  pw.addCell(member.getDateApproved());
  pw.addCell(member.getSapVendorCode());
  pw.addCell(member.getSupplier());
  }
	return pw;
  }

  public Collection getVendorCodeData(LogisticsInputBean bean) throws BaseException {
	  return factory.setBean(new SapVendorBean()).select(new SearchCriteria().addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument() ),null,"ledger.sap_vendor");
  }
  public Collection getCustomerCodeData(LogisticsInputBean bean) throws BaseException {
	  return factory.setBean(new com.tcmis.internal.distribution.beans.SapCustomerBean()).
	  	select(new SearchCriteria().addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument() ),null,"ledger.sap_customer");
}

  public Collection getSupplierPayment(BigDecimal customerRequestId) throws BaseException {
	  factory.setBean(new SuppAddPaymentTermsViewBean());
	  return factory.selectQuery("select * from SUPP_ADD_PAYMENT_TERMS_VIEW where supplier_request_id = " + customerRequestId);
  }
  public Collection getSupplierIgPayment(BigDecimal customerRequestId) throws BaseException {
	  factory.setBean(new SuppExcAddPaytermsViewBean());
	  return factory.selectQuery("select * from supp_exc_add_payterms_view where supplier_request_id = " + customerRequestId);
  }
  public Collection getSupplierPaymentForModify(String supplier) throws BaseException {
	  factory.setBean(new SuppAddPaymentTermsViewBean());
	  return factory.selectQuery("select x.*,PAYMENT_TERMS CURRENT_PAYMENT_TERMS, PAYMENT_TERM_STATUS CURRENT_STATUS, PAYMENT_TERM_STATUS NEW_STATUS from SUPP_ENTITY_PAYMENT_TERMS_VIEW x where supplier = " + getSqlString(supplier));
  }
  public Collection getSupplierIgPaymentForModify(String supplier) throws BaseException {
	  factory.setBean(new SuppExcAddPaytermsViewBean());
	  return factory.selectQuery("select x.*, DEFAULT_PAYMENT_TERMS CURRENT_PAYMENT_TERMS, DEFAULT_PAYMENT_TERMS PAYMENT_TERMS from payment_term_ig_exception_view x where supplier = " + getSqlString(supplier));
  }

  private boolean setApprovedOnInfoBeanColl(Vector beancoll,SupplierAddRequestBean bean, PermissionBean pbean,Date today,BigDecimal pid) throws BaseException { 

	  Boolean allset = true;
//	  Boolean hasone = false;
//	  BigDecimal supplierRequestId = bean.getSupplierRequestId();
//      if ("saveContact".equals(uAction) ) 
        { 
            Collection<SuppAddPaymentTermsViewBean> beans = (Collection<SuppAddPaymentTermsViewBean>) beancoll.get(0);
            for(SuppAddPaymentTermsViewBean ssbean:beans) {
//            	if( ssbean.getSupplierRequestId() == null )
//            		ssbean.setSupplierRequestId(supplierRequestId);
            	if( !ssbean.getPaymentTerms().equals(ssbean.getCurrentPaymentTerms()) ||
            		  !ssbean.getNewStatus().equals(ssbean.getCurrentStatus()) ) {
            		  if( ssbean.getApprovedOn() == null ) {
            			  if( pbean.hasOpsEntityPermission("NewPaymentTerms", ssbean.getOpsEntityId(), null ) ) {
            				  ssbean.setApprovedBy(pid);
            				  ssbean.setApprovedOn(today);
            			  }
                		  else
                  			allset = false;
            		  }
            	}
            }
        }
//      if ("saveShipto".equals(uAction) )
        { 
            Collection<SuppExcAddPaytermsViewBean> beans1 = (Collection<SuppExcAddPaytermsViewBean>) beancoll.get(1);
            for(SuppExcAddPaytermsViewBean ssbean:beans1) { 
//            	if( ssbean.getSupplierRequestId() == null )
//            		ssbean.setSupplierRequestId(supplierRequestId);
            	if( ssbean.getApprovedOn() == null ) {
            		if( pbean.hasOpsEntityPermission("NewPaymentTerms", ssbean.getExcOpsEntityId(), null ) ) {
		            	ssbean.setApprovedBy(pid);
		            	ssbean.setApprovedOn(today);
            		}
            		else
            			allset = false;
            	}
            }
        }
		  return allset;
}
  
  private String updateBeanColl(PersonnelBean personnelBean, Vector beancoll,SupplierAddRequestBean bean) throws BaseException { 

	  String ErrorMsg = "";

	  PermissionBean pbean = personnelBean.getPermissionBean();
      	//      if ("saveContact".equals(uAction) ) 
        { 
            Collection<SuppAddPaymentTermsViewBean> beans = (Collection<SuppAddPaymentTermsViewBean>) beancoll.get(0);
            deletePaymentTemrs(bean);
            for(SuppAddPaymentTermsViewBean ssbean:beans) {
//            	if( pbean.hasOpsEntityPermission("NewPaymentTerms", ssbean.getOpsEntityId(), null)) {
	            	ssbean.setSupplierRequestId(bean.getSupplierRequestId());
	            	ErrorMsg += addPaymentTerms(ssbean);
//            	}
            }
        }
//        if ("saveShipto".equals(uAction) ) 
        { 
//        	if( "Remove".equals(ssbean.getChanged()) ) 
        	ErrorMsg += deleteIgException(bean);
            Collection<SuppExcAddPaytermsViewBean> beans1 = (Collection<SuppExcAddPaytermsViewBean>) beancoll.get(1);
            for(SuppExcAddPaytermsViewBean ssbean:beans1) { 
//            	if( pbean.hasOpsEntityPermission("NewPaymentTerms", ssbean.getOpsEntityId(), null)) {
	            	ssbean.setSupplierRequestId(bean.getSupplierRequestId());
                    if (ssbean.getInventoryGroup() != null && !(ssbean.getInventoryGroup().length()==0) && !ssbean.getInventoryGroup().equalsIgnoreCase("All"))
                    ErrorMsg += addIgExc(ssbean);
//            	}
            }
        }
        return ErrorMsg;

}
  public String deletePaymentTemrs(SupplierAddRequestBean bean) throws BaseException {
	  SupplierAddPaymentTermsBeanFactory factory = new SupplierAddPaymentTermsBeanFactory(dbManager);
	  SearchCriteria sc = new SearchCriteria("supplierRequestId", SearchCriterion.EQUALS,bean.getSupplierRequestId().toString());
  	  factory.delete(sc);
  	return ""; 
  }
  public String deleteIgException(SupplierAddRequestBean bean) throws BaseException {
	  SupplierAddExcPaymentTermsBeanFactory factory = new SupplierAddExcPaymentTermsBeanFactory(dbManager);
	  SearchCriteria sc = new SearchCriteria("supplierRequestId", SearchCriterion.EQUALS,bean.getSupplierRequestId().toString());
  	  factory.delete(sc);
  	return ""; 
  }
  public String addPaymentTerms(SuppAddPaymentTermsViewBean sbean) throws BaseException {
	  SupplierAddPaymentTermsBeanFactory factory = new SupplierAddPaymentTermsBeanFactory(dbManager);
	  factory.insert(sbean);
  	  return ""; 
  }
  public String addIgExc(SuppExcAddPaytermsViewBean sbean) throws BaseException {
	  SupplierAddExcPaymentTermsBeanFactory factory = new SupplierAddExcPaymentTermsBeanFactory(dbManager);
	  factory.insert(sbean);
  	  return ""; 
  }

  private String runProc(Collection inArgs,Collection outArgs,String procname) {
  boolean hasError = false;
  String errorMsg = "";
  Collection c = null;
  
  try {
	  c = dbManager.doProcedure(procname, inArgs, outArgs);
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
		  log.error(procname+" returned:" + procResult);
	  }
  }
  if( hasError ) {
	  ResourceLibrary library = new ResourceLibrary(
			  "com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	  errorMsg = library.getString("generic.error");
  }
  return errorMsg;
  }
}