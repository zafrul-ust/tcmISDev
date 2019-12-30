package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerOpenInvoiceViewBean;
import com.tcmis.internal.distribution.beans.CustomerOpenOrdersViewBean;
import com.tcmis.internal.distribution.beans.CustomerUnappliedCashViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class CreditReviewDetailsProcess extends GenericProcess  {
	Log log = LogFactory.getLog(this.getClass());

	public CreditReviewDetailsProcess(String client,String locale) {
		super(client,locale);
	} 

	public Collection<CustomerUnappliedCashViewBean> getCustomerUnappliedCash(String customerId,String opsEntityId ) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerUnappliedCashViewBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_CUSTOMER_CREDIT.FX_customer_unapplied_cash(").append(customerId);
		query.append(",'").append(opsEntityId).append("'))");
		
          return factory.selectQuery(query.toString());
	}

	public Collection<CustomerOpenInvoiceViewBean> getCustomerOpenInvoices(String customerId,String opsEntityId ) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerOpenInvoiceViewBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_CUSTOMER_CREDIT.FX_customer_open_invoice(").append(customerId);
		query.append(",'").append(opsEntityId).append("')) order by days_late desc");
		
		return factory.selectQuery(query.toString());
	}
	
	
	public Collection<CustomerOpenOrdersViewBean> getCustomerOpenOrders(String customerId,String opsEntityId ) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerOpenOrdersViewBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_CUSTOMER_CREDIT.FX_customer_open_orders(").append(customerId);
		query.append(",'").append(opsEntityId).append("')) order by submitted_date desc");
		
		return factory.selectQuery(query.toString());
	}
	
	public ExcelHandler getUnappliedCashExcel(Collection<CustomerUnappliedCashViewBean> data, Locale locale) throws
    NoDataException, BaseException, Exception {
  
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
		
			pw.addTable();
		//write column headers
			pw.addRow();
		/*Pass the header keys for the Excel.*/
			String[] headerkeys = {
			  "label.operatingentity","label.currencyid","label.amount"};
		
			int[] types = {
			  0,0,0};
			
			int[] widths = {
			  30,20,20};
		
			int[] horizAligns = {
			  0,0,0};
			  
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
			
			// format the numbers to the special columns
			pw.setColumnDigit(2, 2);
		
			for (CustomerUnappliedCashViewBean member : data) {
			  
		    pw.addRow();
		    pw.addCell(member.getPaidToEntityName());
		    if(member.getHomeCurrencyAmount() == null)
		    	pw.addCell("");
		    else
		    	pw.addCell(member.getHomeCurrencyId());
		    pw.addCell(member.getHomeCurrencyAmount());
		  }
		  return pw;
	}
	
	public ExcelHandler getOpenInvoicesExcel(Collection<CustomerOpenInvoiceViewBean> data, Locale locale) throws
    NoDataException, BaseException, Exception {
  
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
		
			pw.addTable();
		//write column headers
			pw.addRow();
		/*Pass the header keys for the Excel.*/
			String[] headerkeys = {
			  "label.operatingentity","label.invoicenumber","label.po",
			  "label.currencyid","label.open","label.invoicedate","label.duedate", 
			  "label.daysLate"};
		
			int[] types = {
			  0,12,10,
			  0, 0, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR,
			  0};
			
			int[] widths = {
			  15,12,6,
			  0,0,0,0,
			  0};
		
			int[] horizAligns = {
			  0,0,0,
			  0,0,0,0,
			  0};
			  
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
			
			// format the numbers to the special columns
			pw.setColumnDigit(6, 2);
		
			for (CustomerOpenInvoiceViewBean member : data) {
			  
		    pw.addRow();
		    pw.addCell(member.getPaidToEntityName());
		    pw.addCell(member.getInvoiceNumber());
		    pw.addCell(member.getReferenceNumber());
		    if(member.getHomeCurrencyAmount() == null)
		    	pw.addCell("");
		    else
		    	pw.addCell(member.getHomeCurrencyId());
		    pw.addCell(member.getHomeCurrencyAmount());
		    pw.addCell(member.getInvoiceDate());
		    pw.addCell(member.getPaymentDueDate());
		    pw.addCell(member.getDaysLate());
		  }
		  return pw;
	}


	public ExcelHandler getOpenOrdersExcel(Collection<CustomerOpenOrdersViewBean> data, Locale locale) throws
    NoDataException, BaseException, Exception {
  
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
		
			pw.addTable();
		//write column headers
			pw.addRow();
		/*Pass the header keys for the Excel.*/
			String[] headerkeys = {
			  "label.operatingentity","label.creditterms","label.mrnumber","label.po",
			  "label.currencyid","label.open","label.orderdate"};
		
			int[] types = {
			  0,0,0,0,
			  0, pw.TYPE_NUMBER, pw.TYPE_CALENDAR};
			
			int[] widths = {
			  15,15,12,16,
			  0,0,0};
		
			int[] horizAligns = {
			  0,0,0,0,
			  0,0,0};
			  
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
			
			// format the numbers to the special columns
			pw.setColumnDigit(6, 2);
		
			for (CustomerOpenOrdersViewBean member : data) {
			  
		    pw.addRow();
		    pw.addCell(member.getOrderedFromEntityName());
		    pw.addCell(member.getPaymentTerms());
		    pw.addCell(member.getPrNumber());
		    pw.addCell(member.getPoNumber());
		    if(member.getHomeCurrencyAmount() == null)
		    	pw.addCell("");
		    else
		    	pw.addCell(member.getHomeCurrencyId());
		    pw.addCell(member.getHomeCurrencyAmount());
		    pw.addCell(member.getSubmittedDate());
		  }
		  return pw;
	}

	
}