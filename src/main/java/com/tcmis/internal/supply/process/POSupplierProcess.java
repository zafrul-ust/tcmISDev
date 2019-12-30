package com.tcmis.internal.supply.process;

//import java.io.*;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.factory.VvCountryBeanFactory;
import com.tcmis.common.admin.factory.VvPaymentTermsBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.POSupplierInputBean;
import com.tcmis.internal.supply.beans.PaymentTermIgExceptionViewBean;
import com.tcmis.internal.supply.beans.SuppEntityPaymentTermsViewBean;
import com.tcmis.internal.distribution.beans.SupplierEntitySearchViewBean;
import com.tcmis.internal.supply.beans.SupplierAddressViewBean;
import com.tcmis.internal.supply.factory.SupplierAddressViewBeanFactory;
import com.tcmis.internal.distribution.beans.SupplierEntitySearchViewBean;

/******************************************************************************
 * Process used by POSupplierAction
 * @version 1.0
 *****************************************************************************/

public class POSupplierProcess  extends BaseProcess 
{ 
	Log log = LogFactory.getLog(this.getClass());

	public POSupplierProcess(String client) 
	{
		super(client);
	}  
	
	public Collection getSupplierAddressViewBeanCollection(POSupplierInputBean bean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		SupplierAddressViewBeanFactory factory = new SupplierAddressViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();		
		/*	if (!StringHandler.isBlankString(bean.getSearchArgument()) ) 
		{			
			searchArgument =  ;
		}		

		if (!StringHandler.isBlankString() ) 
		{			
			searchCriteria.addCriterion("countryAbbrev", SearchCriterion.LIKE, bean.getCountryAbbrev());
		}*/

		Collection c = factory.select(bean.getSearchArgument(), bean.getOpsEntityId(), bean.getCountryAbbrev(),bean.getActiveOnly());
		//log.debug("supplierAddress collection size: [" + c.size() + "]; "); 

		return c;
	}

	public Collection getSupplierEntitySearchViewBeanCollection(POSupplierInputBean bean) throws BaseException, Exception 
	{
		boolean opsEntityBlank = StringHandler.isBlankString(bean.getOpsEntityId());
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new SupplierEntitySearchViewBean());
		StringBuilder queryBuffer = new StringBuilder("select supplier, supplier_name");
		queryBuffer.append(", country_abbrev, state_abbrev, address_line_1, address_line_2");
		queryBuffer.append(", city, zip, location_desc, main_phone, default_payment_terms, status, former_supplier_name");
		queryBuffer.append(", qualification_level, email, supplier_email, first_name, last_name, nickname, phone");
		queryBuffer.append(", phone_extension, fax, debarred, type_of_purchase, supplier_notes, payment_term_status");
		if (opsEntityBlank) {
			queryBuffer.append(", MIN(ops_entity_id) ops_entity_id, MIN(payment_terms) payment_terms");
			queryBuffer.append(", MIN(approved_by) approved_by, MIN(approved_on) approved_on");
		}
		else {
			queryBuffer.append(", ops_entity_id, payment_terms, approved_by, approved_on");
		}
		queryBuffer.append(" from SUPPLIER_ENTITY_SEARCH_VIEW");
		queryBuffer.append(" where lower(FORMER_SUPPLIER_NAME||ADDRESS_LINE_1||ADDRESS_LINE_2||SUPPLIER||SUPPLIER_NAME) like lower(").append(SqlHandler.delimitString("%" + bean.getSearchArgument() + "%")).append(")");
		
		if (!StringHandler.isBlankString(bean.getCountryAbbrev()) && !("All").equalsIgnoreCase(bean.getCountryAbbrev()))
			queryBuffer.append(" and COUNTRY_ABBREV = ").append(SqlHandler.delimitString(bean.getCountryAbbrev()));
		if (!StringHandler.isBlankString(bean.getActiveOnly()))
			queryBuffer.append(" and PAYMENT_TERM_STATUS = 'Active'");
		if(!opsEntityBlank)
			queryBuffer.append(" and nvl(OPS_ENTITY_ID, ").append(SqlHandler.delimitString(bean.getOpsEntityId())).append(") = ").append(SqlHandler.delimitString(bean.getOpsEntityId()));
		else {
			queryBuffer.append(" GROUP BY supplier, supplier_name");
			queryBuffer.append(", country_abbrev, state_abbrev, address_line_1, address_line_2");
			queryBuffer.append(", city, zip, location_desc, main_phone, default_payment_terms, status, former_supplier_name");
			queryBuffer.append(", qualification_level, email, supplier_email, first_name, last_name, nickname, phone");
			queryBuffer.append(", phone_extension, fax, debarred, type_of_purchase, supplier_notes, payment_term_status");
		}
		queryBuffer.append(" order by SUPPLIER_NAME asc");

		return factory.selectQuery(queryBuffer.toString());
	}



	public ExcelHandler getExcelReport(Collection<SupplierEntitySearchViewBean> data, Locale locale)
	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = 
		{		"label.supplierid","label.suppliername","label.supplieraddress","label.existingpricelist","label.level",
				"label.comments","label.defaultpaymentterms","label.phone","label.email","label.newsupplier",
				"label.fedtaxid","label.vatregistration","label.esupplierid","label.sapvendorcode","label.accountnumber","label.status"
		};
		int[] widths = {12,12,26,12,12,
				        26,12,12,12,12,
				        12,12,12,12,12 };
		int[] types =
		{ 
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		};	

		int[] aligns = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		

		for (SupplierEntitySearchViewBean member : data) {
			pw.addRow();			
			pw.addCell(member.getSupplier());
			pw.addCell(member.getSupplierName());
			pw.addCell(member.getAddressLine1()+" "+(StringHandler.isBlankString(member.getAddressLine2())?"":member.getAddressLine2())+" "+
			(StringHandler.isBlankString(member.getAddressLine3())?"":member.getAddressLine3())+" "+
			(StringHandler.isBlankString(member.getStateAbbrev() )?"":member.getStateAbbrev())+" "+
			(StringHandler.isBlankString(member.getZip())?"":member.getZip())+" "+
			(StringHandler.isBlankString(member.getCountryAbbrev())?"":member.getCountryAbbrev()));
			pw.addCell(member.getExistingSupplierPriceList());
			pw.addCell(member.getQualificationLevel());
			pw.addCell(member.getSupplierNotes());
			pw.addCell(member.getDefaultPaymentTerms());
			pw.addCell(member.getMainPhone());		
			pw.addCell(member.getEmail());
			pw.addCell(member.getNewSupplierId());
			pw.addCell(member.getFederalTaxId());
			pw.addCell(member.getVatRegistrationNumber());
			pw.addCell(member.getE_SupplierId());
			pw.addCell(member.getSapVendorCode());
			pw.addCell(member.getAccountNumber());
			pw.addCell(member.getActivePaymentTerms());	

		}
		return pw;

	}
	
	public ExcelHandler getSupplierAddressExcelReport(Collection<SupplierAddressViewBean> data, Locale locale)
	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = 
		{		"label.supplierid","label.suppliername","label.supplieraddress","label.existingpricelist","label.level",
				"label.comments","label.defaultpaymentterms","label.phone","label.email","label.newsupplier",
				"label.fedtaxid","label.vatregistration","label.esupplierid","label.sapvendorcode","label.accountnumber","label.status"
		};
		int[] widths = {12,12,26,12,12,
				        26,12,12,12,12,
				        12,12,12,12,12 };
		int[] types =
		{ 
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		};	

		int[] aligns = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		

		for (SupplierAddressViewBean member : data) {
			pw.addRow();			
			pw.addCell(member.getSupplier());
			pw.addCell(member.getSupplierName());
			pw.addCell(member.getAddressLine1()+" "+(StringHandler.isBlankString(member.getAddressLine2())?"":member.getAddressLine2())+" "+
			(StringHandler.isBlankString(member.getAddressLine3())?"":member.getAddressLine3())+" "+
			(StringHandler.isBlankString(member.getStateAbbrev() )?"":member.getStateAbbrev())+" "+
			(StringHandler.isBlankString(member.getZip())?"":member.getZip())+" "+
			(StringHandler.isBlankString(member.getCountryAbbrev())?"":member.getCountryAbbrev()));
			pw.addCell(member.getExistingSupplierPriceList());
			pw.addCell(member.getQualificationLevel());
			pw.addCell(member.getSupplierNotes());
			pw.addCell(member.getDefaultPaymentTerms());
			pw.addCell(member.getMainPhone());		
			pw.addCell(member.getEmail());
			pw.addCell(member.getNewSupplierId());
			pw.addCell(member.getFederalTaxId());
			pw.addCell(member.getVatRegistrationNumber());
			pw.addCell(member.getE_SupplierId());
			pw.addCell(member.getSapVendorCode());
			pw.addCell(member.getAccountNumber());
			pw.addCell(member.getActivePaymentTerms());	

		}
		return pw;

	}


	public Collection getCountryList() throws
	BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());
		VvCountryBeanFactory factory = new VvCountryBeanFactory(dbManager);
		SortCriteria scriteria = new SortCriteria();
		scriteria.setSortAscending(true);
		scriteria.addCriterion("country");	
		return factory.select(null,scriteria); 
	}
	
	public Collection getPaymentTermsDropDown() throws BaseException {
	    DbManager dbManager = new DbManager(getClient());
	    VvPaymentTermsBeanFactory factory = new VvPaymentTermsBeanFactory(dbManager);
	    return factory.select(null, null);
	  }
	
	
	public Collection update(Collection <SupplierEntitySearchViewBean> poSuppolierViewBeanCollection) throws
	BaseException, Exception {
//		Collection inArgs = null;
//		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		//Collection resultCollection = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

		if((poSuppolierViewBeanCollection!=null) && (!poSuppolierViewBeanCollection.isEmpty()))
		{
			for(SupplierEntitySearchViewBean poSupplierBean : poSuppolierViewBeanCollection) {
				try {
					if(!StringHandler.isBlankString(poSupplierBean.getOk()) && !StringHandler.isBlankString(poSupplierBean.getStatus())) {
					/*	if((!StringHandler.isBlankString(poSupplierBean.getOk()) && !StringHandler.isBlankString(poSupplierBean.getStatus())) &&
							(!StringHandler.isBlankString(poSupplierBean.getDefaultPaymentTerms()) && 
							!StringHandler.isBlankString(poSupplierBean.getComments()) ) && 
							!StringHandler.isBlankString(poSupplierBean.getStatusChangeComments())) {
						inArgs = new Vector(4);
						inArgs.add(poSupplierBean.getStatus());
						inArgs.add(poSupplierBean.getDefaultPaymentTerms());
						inArgs.add(poSupplierBean.getComments());
						inArgs.add(poSupplierBean.getStatusChangeComments());
						outArgs = new Vector(1);
						outArgs.add(new Integer(java.sql.Types.VARCHAR));
						if(log.isDebugEnabled()) {
							log.debug("Po Supplier Proc:" + inArgs);
						}		*/	   
						Vector error = null; 
						//(Vector) factory.doProcedure("PROC_NAME", inArgs, outArgs);
						String query = "SupplierEntitySearchViewBean supplier set status = '" + poSupplierBean.getStatus() + "' ,supplier_notes = '"+poSupplierBean.getSupplierNotes()+"' where supplier = '" + poSupplierBean.getSupplier() +"' " ;
						factory.deleteInsertUpdate(query);
		/*				if(error.size()>0 && error.get(0) != null)
						{
							errorCode = (String) error.get(0);
							log.info("Error in Procedure For Po Supplier Section: "+poSupplierBean.getSupplier()+"-"+poSupplierBean.getDefaultPaymentTerms()+" Error Code "+errorCode+" ");
							errorMessages.add(errorCode);

						}			     */

					}			
				} catch (Exception e) {
					errorMsg = "Error updating PO: "+ poSupplierBean.getStatus()+"-"+poSupplierBean.getDefaultPaymentTerms()+ "";
					errorMessages.add(errorMsg);
				}
			}

		} 
		errorMessages.add("errorMsg");
		return errorMessages;
	}
	
	
	public Collection <SuppEntityPaymentTermsViewBean> getCurrentPaymentTerms(POSupplierInputBean bean) throws BaseException, Exception 
	{
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SuppEntityPaymentTermsViewBean());
		
		Collection <SuppEntityPaymentTermsViewBean> c = null;
		
		SearchCriteria searchCriteria = new SearchCriteria();		
			
			  			                          
		if(null!=bean.getSupplierId())
		{
			searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS, bean.getSupplierId().toString());
		}		
		
		c = factory.select(searchCriteria, null, "SUPP_ENTITY_PAYMENT_TERMS_VIEW");

		return c;
	}
	
	
	public Collection <PaymentTermIgExceptionViewBean> getPaymentTermsExceptions(POSupplierInputBean bean) throws BaseException, Exception 
	{
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PaymentTermIgExceptionViewBean());
		
		Collection <PaymentTermIgExceptionViewBean> c = null;
		
		SearchCriteria searchCriteria = new SearchCriteria();		
			
			  			                          
		if(null!=bean.getSupplierId())
		{
			searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS,  bean.getSupplierId().toString());
		}		
		
		c = factory.select(searchCriteria, null, "payment_term_ig_exception_view");

		return c;
	}
}




