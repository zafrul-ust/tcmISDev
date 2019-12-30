package com.tcmis.internal.distribution.process;

//import java.io.*;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.factory.VvCountryBeanFactory;
import com.tcmis.common.admin.factory.VvPaymentTermsBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.EntitySupplierSearchInputBean;
import com.tcmis.internal.distribution.beans.SupplierEntitySearchViewBean;
import com.tcmis.internal.distribution.factory.SupplierEntitySearchViewBeanFactory;
import com.tcmis.internal.supply.beans.PaymentTermIgExceptionViewBean;
import com.tcmis.internal.supply.beans.SuppEntityPaymentTermsViewBean;
import com.tcmis.internal.supply.beans.SupplierAddressViewBean;

/******************************************************************************
 * Process used by POSupplierAction
 * @version 1.0
 *****************************************************************************/

public class EntitySupplierSearchProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public EntitySupplierSearchProcess(String client) 
	{
		super(client);
	}  

	public Collection getSupplierAddressViewBeanCollection(EntitySupplierSearchInputBean bean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		SupplierEntitySearchViewBeanFactory factory = new SupplierEntitySearchViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();		
		
		Collection c = factory.select(bean.getSupplierName(), bean.getOpsEntityId(), bean.getActiveOnly(), bean.getCountryAbbrev());

		return c;
	}



	public ExcelHandler getExcelReport(EntitySupplierSearchInputBean bean, Locale locale)
	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<SupplierEntitySearchViewBean> data = getSupplierAddressViewBeanCollection(bean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = 
		{		"label.supplierid","label.suppliername","label.supplieraddress","label.level","label.status",
				"label.comments","label.defaultpaymentterms","label.phone","label.email","label.newsupplier",
				"label.fedtaxid","label.vatregistration","label.esupplierid","label.accountnumber"
		};
		int[] widths = {12,12,26,12,12,
				        26,12,12,12,12,
				        12,12,12 };
		int[] types =
		{ 
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING
		};	

		int[] aligns = { 0,0,0,0,0,0,0,0,0,0,0,0,0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		

		for (SupplierEntitySearchViewBean member : data) {
			pw.addRow();			
			pw.addCell(member.getSupplier());
			pw.addCell(member.getSupplierName());
			pw.addCell(member.getAddressLine1()+" "+(StringHandler.isBlankString(member.getAddressLine2())?"":member.getAddressLine2())+" "+
			
			(StringHandler.isBlankString(member.getStateAbbrev() )?"":member.getStateAbbrev())+" "+
			(StringHandler.isBlankString(member.getZip())?"":member.getZip())+" "+
			(StringHandler.isBlankString(member.getCountryAbbrev())?"":member.getCountryAbbrev()));
			pw.addCell(member.getQualificationLevel());
			pw.addCell(member.getStatus());
			pw.addCell(member.getSupplierNotes());
			pw.addCell(member.getDefaultPaymentTerms());
			pw.addCell(member.getMainPhone());		
			pw.addCell(member.getEmail());
			pw.addCell("");
			pw.addCell("");
			pw.addCell("");
			pw.addCell("");
			pw.addCell("");
				

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
	
	
	public Collection update(Collection <SupplierAddressViewBean> poSuppolierViewBeanCollection) throws
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
			for(SupplierAddressViewBean poSupplierBean : poSuppolierViewBeanCollection) {
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
						String query = "update supplier set status = '" + poSupplierBean.getStatus() + "' ,supplier_notes = '"+poSupplierBean.getSupplierNotes()+"' where supplier = '" + poSupplierBean.getSupplier() +"' " ;
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
	
	
	public Collection <SuppEntityPaymentTermsViewBean> getCurrentPaymentTerms(EntitySupplierSearchInputBean bean) throws BaseException, Exception 
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
	
	
	public Collection <PaymentTermIgExceptionViewBean> getPaymentTermsExceptions(EntitySupplierSearchInputBean bean) throws BaseException, Exception 
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




