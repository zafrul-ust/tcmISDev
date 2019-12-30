package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.sql.Connection;
import java.sql.Timestamp;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.ConnectionPool;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.AllocationDetailBean;
import com.tcmis.internal.distribution.beans.AllocationInputBean;
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
import com.tcmis.internal.distribution.beans.InvoiceCorrPrintHcViewBean;
import com.tcmis.internal.distribution.beans.InvoiceCorrPrintLineViewBean;
import com.tcmis.internal.distribution.beans.InvoiceCorrPrintViewBean;
import com.tcmis.internal.distribution.beans.MrAddChargeViewBean;
import com.tcmis.internal.distribution.factory.CustomerAddRequestBeanFactory;
import com.tcmis.internal.hub.beans.LogisticsInputBean;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class InvoiceCorrectionProcess
  		extends GenericProcess {

   public InvoiceCorrectionProcess(TcmISBaseAction act) throws BaseException{
	    super(act);
   }
   
   public InvoiceCorrectionProcess(String client,String locale) {
	    super(client,locale);
   }
   public String invoiceRollback(InvoiceCorrPrintViewBean bean,PersonnelBean pbean) throws BaseException, Exception {
//	   setting business logic...
/*	   
   P_INV_CORRECTION_ROLLBACK(a_invoice IN customer.invoice.invoice%type, 
           a_error OUT VARCHAR2)
	is	   
*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORRECTION_ROLLBACK";
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
       return getProcError(
			   buildProcedureInput(
					   bean.getInvoice()
			   ),outArgs,pkgCall);
   }
   
   public String submitInvoiceCorr(InvoiceCorrPrintViewBean bean,PersonnelBean pbean) throws BaseException, Exception {
//	   setting business logic...
/*	   
   PROCEDURE P_INV_CORRECTION_CREATE(a_invoice IN customer.invoice.invoice%type, 
           a_error OUT VARCHAR2)
	is	   
*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORRECTION_CREATE";
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
       return getProcError(
			   buildProcedureInput(
					   bean.getInvoice()
			   ),outArgs,pkgCall);
   }

   public String initInvoiceCorr(InvoiceCorrPrintViewBean bean,PersonnelBean pbean) throws BaseException, Exception {
//	   setting business logic...
/*	   
			P_INV_CORRECTION(a_invoice IN customer.invoice.invoice%type, 
					a_requestor global.personnel.personnel_id%type, 
					a_error OUT VARCHAR2) 
	is	   
*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORRECTION";
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
       return getProcError(
			   buildProcedureInput(
					   bean.getInvoice(),
					   pbean.getPersonnelId()
			   ),outArgs,pkgCall);
   }
// no out param or first out param error code and need error code only
	protected String getProcError(Collection inArgs,Collection outArgs,String procname,Vector... outv) {
		boolean hasError = false;
		String errorMsg = "";
		Collection c = null;
		
		if( inArgs == null ) inArgs = new Vector();
		if( outArgs == null ) {
			outArgs = new Vector(); 
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		}
		
		try {
			c = dbManager.doProcedure(procname, inArgs, outArgs);
		} catch(Exception ex){
			hasError = true;
		}
		String procResult = "";
		if( c != null ) {
			Iterator it = c.iterator();
			if(it.hasNext()) procResult =  (String)it.next();
			if( !isBlank(procResult) && !"OK".equalsIgnoreCase(procResult)) {
				hasError = true;
				log.error(procname+" returned:" + procResult);
			}
			if( outv != null && outv.length > 0 && outv[0] != null ) {
				outv[0].add(procResult);
				while( it.hasNext()) {
					outv[0].add(it.next());//  .add(it.next());
				}
			}
		}
		if( hasError ) {
			return procResult;
		}
		return errorMsg;
	}
   
   
   public String updateLine(InvoiceCorrPrintLineViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	   
P_INV_CORR_UPDATE_LINE_CHARGE(
    a_invoice IN customer.invoice_correction.original_invoice%type, 
    a_pr_number in customer.invoice_add_charge_correction.pr_number%type,
    a_line_item in customer.invoice_add_charge_correction.line_item%type,
    a_ac_item_id    IN customer.invoice_add_charge_correction.add_charge_item_id%type,
    a_ac_price IN customer.invoice_prep.invoice_unit_price%type,
    a_delete_charge IN varchar2, 
    a_error OUT VARCHAR2)
*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORR_UPDATE_LINE_CHARGE";
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
	   String deleteCharge = "N";
	   if( "true".equalsIgnoreCase(bean.getDeleteCharge()))
		   deleteCharge = "Y";
       return getProcError(
			   buildProcedureInput(
					   bean.getInvoice(),
					   bean.getPrNumber(),
					   bean.getLineItem(),
					   bean.getItemId(),
					   bean.getAdjustedUnitPrice(),
					   deleteCharge
			   ),outArgs,pkgCall);
   }
   public String removeLine(MrAddChargeViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	
P_INV_CORR_UPDATE_LINE_CHARGE(
    a_invoice IN customer.invoice_correction.original_invoice%type, 
    a_pr_number in customer.invoice_add_charge_correction.pr_number%type,
    a_line_item in customer.invoice_add_charge_correction.line_item%type,
    a_ac_item_id    IN customer.invoice_add_charge_correction.add_charge_item_id%type,
    a_ac_price IN customer.invoice_prep.invoice_unit_price%type,
    a_delete_charge IN varchar2, 
    a_error OUT VARCHAR2)
	   */	   // I don't have update routine now.
       String pkgCall = "";
       if (bean.getOrderType().equalsIgnoreCase("MR"))
       {
           pkgCall = "PKG_RLI_SALES_ORDER.P_DROP_LINE_CHARGE";
       }
       else
       {
           pkgCall = "PKG_SALES_QUOTE.P_DROP_LINE_CHARGE";
       }
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
       return getProcError(buildProcedureInput(bean.getCompanyId(),bean.getPrNumber(),bean.getLineItem(),bean.getItemId()),
			  outArgs,pkgCall);
   }
   public String addLineCharge(InvoiceCorrPrintLineViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*
P_INV_CORR_INSERT_LINE_CHARGE(
    a_invoice IN customer.invoice_correction.original_invoice%type, 
    a_pr_number in customer.invoice_add_charge_correction.pr_number%type,
    a_line_item in customer.invoice_add_charge_correction.line_item%type,
    a_ac_item_id IN customer.invoice_add_charge_correction.add_charge_item_id%type,
    a_ac_price IN customer.invoice_add_charge_correction.add_charge_amount%type,
    a_error OUT VARCHAR2)

This procedure inserts an new line charge to the mr and line.
			is	   
*/	   // I don't have update routine now.
       String  pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORR_INSERT_LINE_CHARGE";
       return getProcError(
			   buildProcedureInput(bean.getInvoice(),bean.getPrNumber(),bean.getLineItem(),bean.getItemId(),bean.getAdjustedUnitPrice())
			   ,null,pkgCall);

   }
   
   public String updateHeader(InvoiceCorrPrintHcViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	   Larry Note: I am not sure about this one.
P_INV_CORRECTION_UPDATE(
    a_invoice IN customer.invoice_correction.original_invoice%type, 
    a_pr_number in customer.invoice_correction.pr_number%type,
    a_line_item in customer.invoice_correction.line_item%type,
    a_invoice_price IN customer.invoice_prep.invoice_unit_price%type,
    a_delete_charge IN varchar2, 
    a_error OUT VARCHAR2) 
*/
	   // I don't have update routine now.
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORRECTION_UPDATE";
       BigDecimal itemId = bean.getItemId();
       if( isBlank(itemId) ) itemId = new BigDecimal("148742");
       if( "true".equals(bean.getDeleteCharge()) )
    	   bean.setDeleteCharge("Y");
       else
    	   bean.setDeleteCharge("N");
       
	   Collection inArgs = buildProcedureInput(bean.getInvoice(),bean.getPrNumber(),bean.getLineItem(),bean.getAdjustedPrice(),bean.getDeleteCharge());
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
	   return getProcError(inArgs,outArgs,pkgCall);

   }

   public String addHeaderCharge(InvoiceCorrPrintHcViewBean bean,InvoiceCorrPrintLineViewBean lb) throws BaseException, Exception {
//	   setting business logic...
/*	

Larry,

I've written the procedure to insert new Header charges for Invoice corrections.  The call is below:


Pkg_invoice_correction.PROCEDURE P_INV_CORR_INSERT_HEAD_CHARGE(
    a_invoice IN customer.invoice_correction.original_invoice%type, 
    a_company_id in customer.request_line_item.company_id%type,
    a_pr_number in customer.invoice_add_charge_correction.pr_number%type,
    a_ac_item_id IN customer.invoice_add_charge_correction.add_charge_item_id%type,
    a_ac_desc IN customer.request_line_item.add_charge_description%type,
    a_ac_price IN customer.invoice_add_charge_correction.add_charge_amount%type,
    a_currency_id IN customer.request_line_item.currency_id%type,
    a_error OUT VARCHAR2);


Diane
*/
	   // I don't have update routine now.
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORR_INSERT_HEAD_CHARGE";
       BigDecimal itemId = bean.getItemId();
       if( isBlank(itemId) ) itemId = new BigDecimal("148742");
       return getProcError(
    		   buildProcedureInput(bean.getInvoice(),lb.getCompanyId(),bean.getPrNumber(),itemId,bean.getChargeDescription(),bean.getAdjustedPrice(),lb.getCurrencyId())
    		   ,null,pkgCall) ;
   }

   public String removeHeader(InvoiceCorrPrintHcViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	
P_INV_CORRECTION_UPDATE(
    a_invoice IN customer.invoice_correction.original_invoice%type, 
    a_pr_number in customer.invoice_correction.pr_number%type,
    a_line_item in customer.invoice_correction.line_item%type,
    a_invoice_price IN customer.invoice_prep.invoice_unit_price%type,
    a_delete_charge IN varchar2, 
    a_error OUT VARCHAR2) 

This procedure is called to either update a price on a MR-Line for the invoice or Mark for delete a header charge line.
*/	   // I don't have update routine now.
	   
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORRECTION_UPDATE";
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
       return getProcError(
			   buildProcedureInput(
					   bean.getInvoice(),
					   bean.getPrNumber(),
					   "",
//					   bean.getItemId(),
					   bean.getCatalogPrice(),
					   "Y"
			   ),outArgs,pkgCall);
   }
///////////// material line.
   public String updateMaterialLine(InvoiceCorrPrintLineViewBean bean,InvoiceCorrPrintViewBean ib) throws BaseException, Exception {
//	   setting business logic...
/*	   
P_INV_CORRECTION_UPDATE(
    a_invoice IN customer.invoice_correction.original_invoice%type, 
    a_pr_number in customer.invoice_correction.pr_number%type,
    a_line_item in customer.invoice_correction.line_item%type,
    a_invoice_price IN customer.invoice_prep.invoice_unit_price%type,
    a_delete_charge IN varchar2, 
    a_error OUT VARCHAR2) 

This procedure is called to either update a price on a MR-Line for the invoice or Mark for delete a header charge line.
	is	   
*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
       String pkgCall = "PKG_INVOICE_CORRECTION.P_INV_CORRECTION_UPDATE";
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );

       return getProcError(
			   buildProcedureInput(
					   ib.getInvoice(),
					   ib.getPrNumber(),
					   bean.getLineItem(),
					   bean.getAdjustedUnitPrice().multiply(bean.getUnitOfSaleQuantityPerEach()),
					   "N"
			   ),outArgs,pkgCall);
   }
	public void setPrintDate(BigDecimal invoiceNum,PersonnelBean personnelBean) throws Exception {
		try {
			// call P_INVOICE_SET_PRINT_DATE and pass 
//					a_shipping_print_date (sysdate)
//					a_shipping_print_personnel_id (personnel_id, you might have to pass this to the call)
//					pass null to  
//					a_billing_print_date 
//					a_billing_print_personnel_id

					
					DbManager dbManager = new DbManager(getClient(),getLocale());
//					CREATE OR REPLACE procedure P_INVOICE_SET_PRINT_DATE (
//							a_invoice invoice.invoice%type,
//							a_billing_print_date invoice.billing_print_date%type,
//							a_billing_print_personnel_id invoice.billing_print_personnel_id%type,
//							a_shipping_print_date invoice.shipping_print_date%type,
//							a_shipping_print_personnel_id invoice.shipping_print_personnel_id%type) is
			        GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			        Vector inArgs = new Vector();
			        inArgs.add(invoiceNum);
//			        if( fromConfirmShipment ) {
//			        inArgs.add(null);
//			        inArgs.add(null);
//			        inArgs.add(new Timestamp(new Date().getTime()));
//			        inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));
//			        }
//			        else 
// using billing date.
			        {
			            inArgs.add(new Timestamp(new Date().getTime()));
			            inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));
			            inArgs.add(null);
			            inArgs.add(null);
			        }
			        factory.doProcedure("P_INVOICE_SET_PRINT_DATE", inArgs);
					}catch(Exception ex){}// don't interrupt printing.
		return;
	}
  
}
