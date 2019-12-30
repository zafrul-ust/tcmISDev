package com.tcmis.internal.distribution.process;

import com.tcmis.internal.distribution.beans.*;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.UserEntityAdminViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import java.math.BigDecimal;
import java.util.*;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class AddChargeProcess
  		extends GenericProcess {

   public AddChargeProcess(TcmISBaseAction act) throws BaseException{
	    super(act);
  }
   
   public String updateLine(MrAddChargeViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	   
 *  procedure P_UPDATE_LINE_CHARGE (
	a_company_id request_line_item_add_charge.company_id%type,
	a_pr_number request_line_item_add_charge.pr_number%type,
	a_line_item request_line_item_add_charge.line_item%type,
	a_item_id request_line_item_add_charge.item_id%type,
	a_price request_line_item_add_charge.price%type,
	a_charge_recurrence request_line_item_add_charge.charge_recurrence%type,
	a_error out varchar2)
	is	   
*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
       String pkgCall = "";
       if (bean.getOrderType().equalsIgnoreCase("MR"))
       {
           pkgCall = "PKG_RLI_SALES_ORDER.P_UPDATE_LINE_CHARGE";
       }
       else
       {
           pkgCall = "PKG_SALES_QUOTE.P_UPDATE_LINE_CHARGE";
       }
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
       return getProcError(
			   buildProcedureInput(
					   bean.getCompanyId(),
					   bean.getPrNumber(),
					   bean.getLineItem(),
					   bean.getItemId(),
					   bean.getPrice(),
					   bean.getChargeRecurrence()
			   ),outArgs,pkgCall);
   }
   public String removeLine(MrAddChargeViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	   procedure P_DROP_LINE_CHARGE (
				a_company_id request_line_item_add_charge.company_id%type,
				a_pr_number request_line_item_add_charge.pr_number%type,
				a_line_item request_line_item_add_charge.line_item%type,
				a_item_id request_line_item_add_charge.item_id%type,
				a_error out varchar2)
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
   public String addLineCharge(MrAddChargeViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	   procedure P_ADD_LINE_CHARGE (
				a_company_id request_line_item_add_charge.company_id%type,
				a_pr_number request_line_item_add_charge.pr_number%type,
				a_line_item request_line_item_add_charge.line_item%type,
				a_item_id request_line_item_add_charge.item_id%type,
				a_price request_line_item_add_charge.price%type,
// dropped				a_currency_id request_line_item_add_charge.currency_id%type,
				a_charge_recurrence request_line_item_add_charge.charge_recurrence%type,
				a_error out varchar2)
			is	   
*/	   // I don't have update routine now.
       String pkgCall = "";
       if ("MR".equalsIgnoreCase(bean.getOrderType()))
       {
           pkgCall = "PKG_RLI_SALES_ORDER.p_add_line_charge";
       }
       else
       {
           pkgCall = "PKG_SALES_QUOTE.p_add_line_charge";
       }
       return getProcError(
			   buildProcedureInput(bean.getCompanyId(),bean.getPrNumber(),bean.getLineItem(),bean.getItemId(),bean.getPrice(),bean.getChargeRecurrence())
			   ,null,pkgCall);

   }
   
   public String updateHeader(MrAddChargeViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	
procedure p_update_header_charge (
	a_company_id purchase_request.company_id%type,
	a_pr_number in out purchase_request.pr_number%type,
    a_line_item request_line_item.line_item%type,
	a_item_id item.item_id%type,
	a_catalog_price request_line_item.catalog_price%type,	
	a_charge_recurrence request_line_item.charge_recurrence%type,    
	a_error out varchar2,
    a_quantity request_line_item.quantity%type default 1,
	a_catalog_company_id request_line_item.catalog_company_id%type default 'HAAS',
	a_catalog_id request_line_item.catalog_id%type default 'Global',
	a_part_group_no request_line_item.part_group_no%type default 1)        
*/	   // I don't have update routine now.
       String pkgCall = "";
       BigDecimal itemId = bean.getItemId();
       if( isBlank(itemId) ) itemId = new BigDecimal("148742");

       if (bean.getOrderType().equalsIgnoreCase("MR"))
       {           
           pkgCall = "PKG_RLI_SALES_ORDER.p_update_header_charge";
       }
       else
       {
          pkgCall = "PKG_SALES_QUOTE.p_update_header_charge";
       }
       if( "true".equals(bean.getTaxExempt())) bean.setTaxExempt("Y");
	   else bean.setTaxExempt("N");

	   Collection inArgs = buildProcedureInput(bean.getCompanyId(),bean.getPrNumber(),bean.getLineItem(),itemId,bean.getPrice(),bean.getChargeRecurrence(),bean.getDescription(),bean.getTaxExempt());
       Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
	   return getProcError(inArgs,outArgs,pkgCall);

   }

   public String addHeaderCharge(MrAddChargeViewBean bean,BigDecimal personnelId) throws BaseException, Exception {
//	   setting business logic...
/*	

procedure P_ADD_HEADER_CHARGE (
	a_company_id purchase_request.company_id%type,
	a_pr_number in purchase_request.pr_number%type,
	a_item_id item.item_id%type,
	a_catalog_price request_line_item.catalog_price%type,
	a_currency_id request_line_item.currency_id%type,
	a_charge_recurrence request_line_item.charge_recurrence%type,
	a_tax_exempt request_line_item.tax_exempt%type,
	a_add_charge_description request_line_item.add_charge_description%type,
	a_line_item out request_line_item.line_item%type,
	a_error out varchar2,
	a_application request_line_item.application%type default null,
	a_po_number request_line_item.po_number%type default null,
	a_quantity request_line_item.quantity%type default 1,
	a_catalog_company_id request_line_item.catalog_company_id%type default 'HAAS',
	a_catalog_id request_line_item.catalog_id%type default 'Global',
	a_part_group_no request_line_item.part_group_no%type default 1)
is

*/
	   // I don't have update routine now.
	   if( "true".equals(bean.getTaxExempt())) bean.setTaxExempt("Y");
	   else bean.setTaxExempt("N");
	   Collection inArgs = new Vector();

	   Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
	   try {
       String pkgCall = "";
       BigDecimal itemId = bean.getItemId();
       if( isBlank(itemId) ) itemId = new BigDecimal("148742");
       if (bean.getOrderType().equalsIgnoreCase("MR"))
       {
           inArgs = buildProcedureInput(bean.getCompanyId(),bean.getPrNumber(),itemId,bean.getPrice(),bean.getCurrencyId(),bean.getChargeRecurrence(),bean.getTaxExempt(),bean.getDescription());
           pkgCall = "PKG_RLI_SALES_ORDER.p_add_header_charge";
       }
       else
       {
           inArgs = buildProcedureInput(bean.getCompanyId(),bean.getPrNumber(),itemId,bean.getPrice(),bean.getCurrencyId(),bean.getChargeRecurrence(),bean.getTaxExempt(),bean.getDescription(),personnelId);
           pkgCall = "PKG_SALES_QUOTE.p_add_header_charge";
       }
       Collection coll = factory.doProcedure(pkgCall, inArgs,outArgs);

	   String errorMsg = (String)((Vector)coll).get(1);
	   if( errorMsg != null && errorMsg.length() > 0 && !errorMsg.equalsIgnoreCase("OK"))
		   return errorMsg;
	   }catch(Exception ex){ 
		   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		   return library.getString("generic.error");
	   };
	   return "";

   }

   public String removeHeader(MrAddChargeViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	
CREATE OR REPLACE procedure P_DELETE_RLI_LINE (
	a_pr_number request_line_item.pr_number%type,
	a_line_item request_line_item.line_item%type,
	a_error out varchar2) is
		is
*/	   // I don't have update routine now.
	   
//	   Collection inArgs = buildProcedureInput(bean.getCompanyId(),bean.getPrNumber(),bean.getItemId(),bean.getPrice(),bean.getCurrencyId(),bean.getChargeRecurrence());
	   
	   Collection inArgs = buildProcedureInput(bean.getPrNumber(),bean.getLineItem());

	   Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );	   
	   try {
       String pkgCall = "";
       if (bean.getOrderType().equalsIgnoreCase("MR"))
       {
           pkgCall = "P_DELETE_RLI_LINE";
       }
       else
       {
           pkgCall = "PKG_SALES_QUOTE.P_DELETE_SALES_QUOTE_LINE";
       }
       Collection coll = factory.doProcedure(pkgCall, inArgs,outArgs);

	   String errorMsg = (String)((Vector)coll).get(0);
	   if( errorMsg != null && errorMsg.length() > 0 )
		   return errorMsg;
	   }catch(Exception ex){ 
		   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		   return library.getString("generic.error");
	   };
	   return "";

   }
}
