package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.beans.CustomerContactBean;
import com.tcmis.internal.distribution.beans.CustomerContactViewBean;

/******************************************************************************
 * Process for searching new customer requests
 * @version 1.0
 *****************************************************************************/
public class CustomerUpdateProcess
extends GenericProcess {

	public CustomerUpdateProcess(String client,Locale locale) {
		super(client,locale);
	}



	public String callModifyCustomer(CustomerAddRequestViewBean bean,PersonnelBean personnelBean)  throws BaseException {

		if(	"true".equals(bean.getBillingChange()) || "true".equals(bean.getOtherChange()))
		{
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			/*
			 *    PROCEDURE modify_customer(
	      a_customer_id                 IN       customer.bill_to_customer.customer_id%TYPE,
	      a_customer_name               IN       customer.bill_to_customer.customer_name%TYPE,
	      a_bill_to_company_id          IN       customer.bill_to_customer.bill_to_company_id%TYPE,
	      a_bill_to_location_id         IN       customer.bill_to_customer.bill_to_location_id%TYPE,
	      a_payer_id                    IN       customer.bill_to_customer.payer_id%TYPE,
	      a_industry                    IN       customer.bill_to_customer.industry%TYPE,
	      a_invoice_consolidation       IN       customer.bill_to_customer.invoice_consolidation%TYPE,
	      a_payment_terms               IN       customer.bill_to_customer.payment_terms%TYPE,
	      a_currency_id                 IN       customer.bill_to_customer.currency_id%TYPE,
	      a_fixed_currency              IN       customer.bill_to_customer.fixed_currency%TYPE,
	      a_credit_limit                IN       customer.bill_to_customer.credit_limit%TYPE,
	      a_overdue_limit               IN       customer.bill_to_customer.overdue_limit%TYPE,
	      a_overdue_limit_basis         IN       customer.bill_to_customer.overdue_limit_basis%TYPE,
	      a_credit_status               IN       customer.bill_to_customer.credit_status%TYPE,
	      a_price_group_id              IN       customer.bill_to_customer.price_group_id%TYPE,
	      a_shelf_life_required         IN       customer.bill_to_customer.shelf_life_required%TYPE,
	      a_ship_complete               IN       customer.bill_to_customer.ship_complete%TYPE,
	      a_sap_customer_code           IN       customer.bill_to_customer.sap_customer_code%TYPE,
	      a_tax_registration_type       IN       customer.bill_to_customer.tax_registration_type%TYPE,
	      a_tax_registration_number     IN       customer.bill_to_customer.tax_registration_number%TYPE,
	      a_tax_registration_type_2     IN       customer.bill_to_customer.tax_registration_type_2%TYPE,
	      a_tax_registration_number_2   IN       customer.bill_to_customer.tax_registration_number_2%TYPE,
	      a_tax_registration_type_3     IN       customer.bill_to_customer.tax_registration_type_3%TYPE,
	      a_tax_registration_number_3   IN       customer.bill_to_customer.tax_registration_number_3%TYPE,
	      a_tax_registration_type_4     IN       customer.bill_to_customer.tax_registration_type_4%TYPE,
	      a_tax_registration_number_4   IN       customer.bill_to_customer.tax_registration_number_4%TYPE,
	      a_tax_currency_id             IN       customer.bill_to_customer.tax_currency_id%TYPE,
	      a_email_order_ack             IN       customer.bill_to_customer.email_order_ack%TYPE,
	      a_ship_mfg_coc                IN       customer.bill_to_customer.ship_mfg_coc%TYPE,
	      a_ship_mfg_coa                IN       customer.bill_to_customer.ship_mfg_coa%TYPE,
	      a_ship_msds                   IN       customer.bill_to_customer.ship_msds%TYPE,
	      a_location_desc               IN       customer.LOCATION.location_desc%TYPE,
	      a_country_abbrev              IN       customer.LOCATION.country_abbrev%TYPE,
	      a_state_abbrev                IN       customer.LOCATION.state_abbrev%TYPE,
	      a_address_line_1               IN       customer.LOCATION.address_line_1_display%TYPE,
	      a_address_line_2               IN       customer.LOCATION.address_line_2_display%TYPE,
	      a_address_line_3               IN       customer.LOCATION.address_line_3_display%TYPE,
	      a_address_line_4               IN       customer.LOCATION.address_line_4_display%TYPE,
	      a_address_line_5               IN       customer.LOCATION.address_line_5_display%TYPE,
	      a_city                        IN       customer.LOCATION.city%TYPE,
	      a_zip                         IN       customer.LOCATION.zip%TYPE,
	      a_zip_plus                    IN       VARCHAR2,
	      a_charge_freight              IN       customer.bill_to_customer.charge_freight%TYPE,
	      a_field_sales_rep_id          IN       customer.bill_to_customer.field_sales_rep_id%TYPE,
	      a_personnel_id                IN       GLOBAL.personnel.personnel_id%TYPE,
	      a_error_message               OUT      VARCHAR2
	
			 */
			Vector error  = new Vector();
			String errorCode = "You don't have permission to add/modify customer";
			if (personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null) ||
					personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailUpdate", null, null)  ||
					personnelBean.getPermissionBean().hasOpsEntityPermission("GenerateOrders", null, null) )
			{
				Collection inArgs =
					buildProcedureInput(
							bean.getCustomerId(),
							bean.getCustomerName(),
							bean.getBillToCompanyId(),
							bean.getBillToLocationId(),
							bean.getPayerId(),
							bean.getIndustry(),
							bean.getInvoiceConsolidation(),
							bean.getPaymentTerms(),
							bean.getCurrencyId(),
							bean.getFixedCurrency(),
							bean.getCreditLimit(),
							bean.getOverdueLimit(),
							bean.getOverdueLimitBasis(),
							bean.getCreditStatus(),
							bean.getPriceGroupId(),
							bean.getShelfLifeRequired(),
							bean.getShipComplete(),
							bean.getSapCustomerCode(),
							bean.getTaxRegistrationType(),
							bean.getTaxRegistrationNumber(),
							bean.getTaxRegistrationType2(),
							bean.getTaxRegistrationNumber2(),
							bean.getTaxRegistrationType3(),
							bean.getTaxRegistrationNumber3(),
							bean.getTaxRegistrationType4(),
							bean.getTaxRegistrationNumber4(),
							bean.getTaxCurrencyId(),
							bean.getEmailOrderAck(),
							bean.getShipMfgCoc(),
							bean.getShipMfgCoa(),
							bean.getShipMsds(),
							bean.getLocationDesc(),
							bean.getCountryAbbrev(),
							bean.getStateAbbrev(),
							bean.getAddressLine1(),
							bean.getAddressLine2(),
							bean.getAddressLine3(),
							bean.getAddressLine4(),
							bean.getAddressLine5(),
							bean.getCity(),
							bean.getZip(),
							bean.getZipPlus(),
							bean.getChargeFreight(),
							bean.getFieldSalesRepId(),
							"true".equals(bean.getOtherChange())?personnelId.intValue():null,
							bean.getCustomerNotes(),
							bean.getSapIndustryKey(),
							bean.getExitLabelFormat(),
							"true".equals(bean.getBillingChange())?	personnelId.intValue():null,
							bean.getAbcClassification(),
							bean.getTaxNotes(),
							bean.getAutoEmailInvoice(),
							bean.getAutoEmailAddress(),
							bean.getAutoEmailBatchSize()					
					);
				
				Vector outArgs = new Vector();
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				if (log.isDebugEnabled()) {
					log.debug("inArgs:"+inArgs);
				}
				//unblock this for real run
				error = (Vector) factory.doProcedure("pkg_customer_setup.modify_customer", inArgs, outArgs);
				//Vector error = new Vector();
				errorCode = "";
			}
			if(error.size()>0 && error.get(0) != null)
			{
				errorCode = error.get(0).toString();
			}
			//    sendNotificationMail(bean,personnelBean);
			// }
			return errorCode;
		}
		else
			return "";
	}


	public Object[] modifyCustomerContact(CustomerContactBean bean,PersonnelBean personnelBean)  throws BaseException {

		/*
	        PROCEDURE modify_customer_contact(
	      	      a_customer_id            IN       customer.customer_contact.customer_id%TYPE,
			      a_bill_to_company_id     IN       customer.bill_to_customer.bill_to_company_id%TYPE,
			      a_contact_id             IN       customer.customer_contact.contact_personnel_id%TYPE,
			      a_contact_type           IN       customer.customer_contact.contact_type%TYPE,
			      a_first_name             IN       global.personnel.first_name%TYPE,
			      a_last_name              IN       global.personnel.last_name%TYPE,
			      a_nickname               IN       customer.customer_contact.nickname%TYPE,
			      a_phone                  IN       global.personnel.phone%TYPE,
			      a_mobile                 IN       global.personnel.alt_phone%TYPE,
			      a_fax                    IN       global.personnel.fax%TYPE,
			      a_email                  IN       global.personnel.email%TYPE,
			      a_status                 IN       customer.customer_contact.status%TYPE,
			      a_purchasing             IN       customer.customer_contact.purchasing%TYPE,
			      a_accounts_payable       IN       customer.customer_contact.accounts_payable%TYPE,
			      a_receiving              IN       customer.customer_contact.receiving%TYPE,
			      a_quality_assurance      IN       customer.customer_contact.quality_assurance%TYPE,
			      a_management             IN       customer.customer_contact.MANAGEMENT%TYPE,
			      a_error_message1         OUT      VARCHAR2
		 */
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		BigDecimal newContactId = null;
		Vector error  = new Vector();
		String errorCode = "You don't have permission to add/modify customer contact";
		if (personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null) ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailUpdate", null, null)  ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("GenerateOrders", null, null) )
		{

			try {
				Collection inArgs =
					buildProcedureInput(
							bean.getCustomerId(),
							bean.getBillToCompanyId(),
							bean.getContactPersonnelId(),
							bean.getContactType(),
							bean.getFirstName(),
							bean.getLastName(),
							bean.getNickname(),
							bean.getPhone(),
							bean.getMobile(),
							bean.getFax(),
							bean.getEmail(),
							bean.getStatus(),
							bean.getPurchasing(),
							bean.getAccountsPayable(),
							bean.getReceiving(),
							bean.getQualityAssurance(),
							bean.getManagement(),
							"true".equals(bean.getDefaultContact())?"Y":""
					);
				Vector outArgs = new Vector();
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				outArgs.add( new Integer(java.sql.Types.NUMERIC) );
				if (log.isDebugEnabled()) {
					log.debug("inArgs:"+inArgs);
				}
				//unblock this for real run
				error = (Vector) factory.doProcedure("pkg_customer_setup.modify_customer_contact", inArgs, outArgs);
				//Vector error = new Vector();
				errorCode = ""; // no error.
				if(error.size()>0 && error.get(0) != null)
				{
					errorCode = error.get(0).toString();
				}
				newContactId = (BigDecimal) error.get(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorCode = "Error Modifying Customer Contact: "+ bean.getCustomerId()+" "+e.toString();
			}

			//    sendNotificationMail(bean,personnelBean);
		}


		Object[] objs = {errorCode,newContactId};
		return objs;
	}



	public Collection getCustomerContact(CustomerContactBean inputBean) throws BaseException
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerContactViewBean());


		SearchCriteria searchCriteria = new SearchCriteria();

		searchCriteria.addCriterion("customerId",	SearchCriterion.EQUALS,	inputBean.getCustomerId().toString());
		if (inputBean.getContactPersonnelId() !=null)
		{
			searchCriteria.addCriterion("contactPersonnelId",	SearchCriterion.EQUALS,inputBean.getContactPersonnelId().toString());
		}

		return factory.select(searchCriteria, null, "CUSTOMER_CONTACT_VIEW");


	}

	public Object[] callCreateContact(CustomerContactBean bean,PersonnelBean personnelBean)  throws BaseException {

		/*
	        PROCEDURE modify_customer_contact(
	      	      a_customer_id            IN       customer.customer_contact.customer_id%TYPE,
	      	      a_contact_personnel_id   IN       customer.customer_contact.contact_personnel_id%TYPE,
	      	      a_contact_type           IN       customer.customer_contact.contact_type%TYPE,
	      	      a_first_name             IN       customer.customer_contact.first_name%TYPE,
	      	      a_last_name              IN       customer.customer_contact.last_name%TYPE,
	      	      a_nickname               IN       customer.customer_contact.nickname%TYPE,
	      	      a_phone                  IN       customer.customer_contact.phone%TYPE,
	      	      a_mobile                 IN       customer.customer_contact.mobile%TYPE,
	      	      a_fax                    IN       customer.customer_contact.fax%TYPE,
	      	      a_email                  IN       customer.customer_contact.email%TYPE,
	      	      a_status                 IN       customer.customer_contact.status%TYPE,
	      	      a_purchasing             IN       customer.customer_contact.purchasing%TYPE,
	      	      a_accounts_payable       IN       customer.customer_contact.accounts_payable%TYPE,
	      	      a_receiving              IN       customer.customer_contact.receiving%TYPE,
	      	      a_quality_assurance      IN       customer.customer_contact.quality_assurance%TYPE,
	      	      a_management             IN       customer.customer_contact.MANAGEMENT%TYPE,
	                a_error_message1         OUT      VARCHAR2
		 */
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		BigDecimal newContactId = null;
		Vector error  = new Vector();
		String errorCode = "You don't have permission to add/modify customer contact";
		if (personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null) ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailUpdate", null, null)  ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("GenerateOrders", null, null) )
		{

			try {
				Collection inArgs =
					buildProcedureInput(
							bean.getCustomerId(),
							bean.getBillToCompanyId(),
							bean.getContactPersonnelId(),
							bean.getContactType(),
							bean.getFirstName(),
							bean.getLastName(),
							bean.getNickname(),
							bean.getPhone(),
							bean.getMobile(),
							bean.getFax(),
							bean.getEmail(),
							"ACTIVE",
							bean.getPurchasing(),
							bean.getAccountsPayable(),
							bean.getReceiving(),
							bean.getQualityAssurance(),
							bean.getManagement(),
							"true".equals(bean.getDefaultContact())?"Y":""
					);
				Vector outArgs = new Vector();
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				outArgs.add( new Integer(java.sql.Types.NUMERIC) );
				if (log.isDebugEnabled()) {
					log.debug("inArgs:"+inArgs);
				}
				//unblock this for real run
				error = (Vector) factory.doProcedure("pkg_customer_setup.modify_customer_contact", inArgs, outArgs);
				//Vector error = new Vector();
				errorCode = ""; // no error.
				if(error.size()>0 && error.get(0) != null)
				{
					errorCode = error.get(0).toString();
				}
				newContactId = (BigDecimal) error.get(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorCode = "Error Adding New Customer Contact: "+ bean.getCustomerId()+" "+e.toString();
			}

			//    sendNotificationMail(bean,personnelBean);
		}
		Object[] objs = {errorCode,newContactId};
		return objs;
	}

	// The following is called from customer update page and it updates the status of the customer contact.

	public Vector updateCustomerContact(Collection<CustomerContactBean>  beans, CustomerAddRequestViewBean inputBean,PersonnelBean personnelBean)  throws BaseException {

		/*
	        PROCEDURE modify_customer_contact(
	      	      a_customer_id            IN       customer.customer_contact.customer_id%TYPE,
			      a_bill_to_company_id     IN       customer.bill_to_customer.bill_to_company_id%TYPE,
			      a_contact_id             IN       customer.customer_contact.contact_personnel_id%TYPE,
			      a_contact_type           IN       customer.customer_contact.contact_type%TYPE,
			      a_first_name             IN       global.personnel.first_name%TYPE,
			      a_last_name              IN       global.personnel.last_name%TYPE,
			      a_nickname               IN       customer.customer_contact.nickname%TYPE,
			      a_phone                  IN       global.personnel.phone%TYPE,
			      a_mobile                 IN       global.personnel.alt_phone%TYPE,
			      a_fax                    IN       global.personnel.fax%TYPE,
			      a_email                  IN       global.personnel.email%TYPE,
			      a_status                 IN       customer.customer_contact.status%TYPE,
			      a_purchasing             IN       customer.customer_contact.purchasing%TYPE,
			      a_accounts_payable       IN       customer.customer_contact.accounts_payable%TYPE,
			      a_receiving              IN       customer.customer_contact.receiving%TYPE,
			      a_quality_assurance      IN       customer.customer_contact.quality_assurance%TYPE,
			      a_management             IN       customer.customer_contact.MANAGEMENT%TYPE,
			      a_error_message1         OUT      VARCHAR2
		 */
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		Vector error  =null;
		Vector finalErrVec = new Vector();
		String errorCode = "You don't have permission to add/modify customer contact";

		if (personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null) ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailUpdate", null, null)  ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("GenerateOrders", null, null) )
		{
				for ( CustomerContactBean bean: beans)
				{
					if(bean.getContactInfoChange())
					{
					try {
						Collection inArgs =
							buildProcedureInput(
									bean.getCustomerId(),
									inputBean.getBillToCompanyId(),
									bean.getContactPersonnelId(),
									bean.getContactType(),
									bean.getFirstName(),
									bean.getLastName(),
									bean.getNickname(),
									bean.getPhone(),
									bean.getMobile(),
									bean.getFax(),
									bean.getEmail(),
									"false".equals(bean.getStatus())?"INACTIVE":("true".equals(bean.getStatus())?"ACTIVE":bean.getStatus()),
											bean.getPurchasing(),
											bean.getAccountsPayable(),
											bean.getReceiving(),
											bean.getQualityAssurance(),
											bean.getManagement(),
											"true".equals(bean.getDefaultContact())?"Y":""
							);
						Vector outArgs = new Vector();
						outArgs.add( new Integer(java.sql.Types.VARCHAR) );
						outArgs.add( new Integer(java.sql.Types.NUMERIC) );
						if (log.isDebugEnabled()) {
							log.debug("inArgs:"+inArgs);
						}
						//unblock this for real run
						error = (Vector) factory.doProcedure("pkg_customer_setup.modify_customer_contact", inArgs, outArgs);
						//Vector error = new Vector();
						errorCode = ""; // no error.
						if(error.size()>0 && error.get(0) != null)
						{
							errorCode = error.get(0).toString();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						errorCode = "Error Modifying Customer Contact: "+ bean.getCustomerId()+" "+e.toString();
	
					}
					if(errorCode.trim().length()>0)
						finalErrVec.add(errorCode);
				}
			}
			
		}
		else
		{
			finalErrVec.add(errorCode);
		}


		return finalErrVec;
	}

	public Collection updateShipTo(Collection <CustomerAddressSearchViewBean> shipToCollection, PersonnelBean personnelBean,String customerId) throws
	BaseException, Exception {
		String errorMsg = "";
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		Vector finalErrVec = new Vector();
		String errorCode = "You don't have permission to add/modify customer contact";
		if (personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null) ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailUpdate", null, null)  ||
				personnelBean.getPermissionBean().hasOpsEntityPermission("GenerateOrders", null, null) )
		{
			for(CustomerAddressSearchViewBean shipToBean : shipToCollection) {
				try {
					//if(!StringHandler.isBlankString(shipToBean.getOk()) && !StringHandler.isBlankString(poSupplierBean.getStatus())) {
					if(shipToBean.getShipInfoChange())
					{
						String status = "I";
						if ("true".equals(shipToBean.getShiptoStatus())) status = "A";
						/*String query;
						if(shipToBean.getShiptoStatus().equalsIgnoreCase("false") && shipToBean.getStatus().equalsIgnoreCase("A") || shipToBean.getShiptoStatus().equalsIgnoreCase("true") && shipToBean.getStatus().equalsIgnoreCase("I"))
						{
							shipToBean.setUpdatedBy(personnelBean.getPersonnelIdBigDecimal());
							shipToBean.setUpdatedOn(new Date(System.currentTimeMillis()));
							query = "update customer.facility_dock set status = '" + status + "', updated_by = '" + shipToBean.getUpdatedBy() + "', updated_on = TO_DATE('" + shipToBean.getUpdatedOn() + "', 'YYYY-MM-DD') where facility_id = '" + shipToBean.getFacilityId() + "' and dock_location_id = '" +shipToBean.getShipToLocationId() + "' and company_id = '" + shipToBean.getCompanyId() + "'";
						}
						else
							query = "update facility_dock set status = '" + status + "' where facility_id = '" + shipToBean.getFacilityId() + "' and dock_location_id = '" +shipToBean.getShipToLocationId() + "' and company_id = '" + shipToBean.getCompanyId() + "'";
						factory.deleteInsertUpdate(query);*/
						/*pkg_customer_setup.p_modify_customer_ship_to(
								a_customer_id                 IN       NUMBER,
								a_company_id                  IN       customer.facility.company_id%TYPE,
								a_facility_id                 IN       customer.facility.facility_id%TYPE,
								a_shipto_location_id          IN       customer.location.location_id%TYPE,
								a_sales_agent_id              IN       customer.facility.sales_agent_id%TYPE,
								a_field_sales_rep_id          IN       customer.facility.field_sales_rep_id%TYPE,
								a_price_group_id              IN       customer.price_group.price_group_id%TYPE,
								a_shipto_note                 IN       customer.facility_dock.internal_note%TYPE,
								a_error_message               OUT      VARCHAR2)
									Collection inArgs =*/
						errorMsg = getProcError(buildProcedureInput(
								customerId, // unfortunately I cannot make sure it's a number.
								shipToBean.getCompanyId(),
								shipToBean.getFacilityId(),
								shipToBean.getShipToLocationId(),
								shipToBean.getSalesAgentId(),
								shipToBean.getFieldSalesRepId(),
								shipToBean.getFacilityPriceGroupId(),
								shipToBean.getShiptoNotes(),
								shipToBean.getMsdsLocaleOverride(),
								status,
								personnelBean.getPersonnelIdBigDecimal()
						),
						null,"pkg_customer_setup.p_modify_customer_ship_to");
						if( !isBlank(errorMsg) )
							errorMessages.add(errorMsg);
					}
				} catch (Exception e) {
					errorMsg = "Error updating Ship To ";
					errorMessages.add(errorMsg);
				}
			}
		}
		else {
			errorMessages.add(errorCode);
		}
		return errorMessages;
	}


	public String callCancelCustomer(String customerId,PersonnelBean personnelBean)  throws BaseException {

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		BigDecimal cusId = new BigDecimal(customerId);
		/*
 			a_customer_id purchase_request.customer_id%type,
			a_commit char default 'Y'

		 */
		String errorCode = "";

		if (personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null))
		{

			Collection inArgs =
				buildProcedureInput(	cusId	);

			//unblock this for real run
			factory.doProcedure("Pkg_rli_sales_order.p_cancel_customer", inArgs);
		}
		else
		{
			errorCode = "You don't have permission to add/modify customer contact";
		}

		return errorCode;
	}

}