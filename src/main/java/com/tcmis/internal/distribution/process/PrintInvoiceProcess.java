package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.InvoiceSearchBean;
import com.tcmis.internal.distribution.beans.PrintInvoiceTotalsBean;
import com.tcmis.internal.hub.beans.ShipConfirmInputBean;

/******************************************************************************
 * Process for ship confirm
 * @version 1.0
 *****************************************************************************/
public class PrintInvoiceProcess
    extends GenericProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PrintInvoiceProcess(String client,String locale) {
	    super(client,locale);
	  }

  public Collection<InvoiceSearchBean> getSearchResults(PersonnelBean personnelBean,String documentType, String idField, ShipConfirmInputBean ib)  throws
  BaseException {
	  DbManager dbManager = new DbManager(getClient(),getLocale());
	  GenericSqlFactory factory = new GenericSqlFactory(dbManager,new InvoiceSearchBean());
	  String invoice = "";
      if( idField != null && idField.trim().length() != 0 ) {
			try {
			  if( "shipmentId".equals(documentType) )
					ib.setShipmentId(new BigDecimal(idField));
              else if( "mr".equals(documentType) )
					ib.setPrNumber(new BigDecimal(idField));
              else if( "poNumber".equals(documentType) )
					ib.setPoNumber(idField);
			}
			catch(Exception ex) {}
		}
      
      SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
     
      if ("invoice".equals(documentType) && idField != null && idField.trim().length() != 0 )
      {
    	 invoice=factory.selectSingleValue("select customer.fx_get_tcmis_invoice('"+idField+"') from dual");
      }
      
      String billingPrintDateNull = "N";
      {
          if("Y".equals(ib.getShowNeverPrinted()))
        	  billingPrintDateNull = "Y";
          else if("Y".equals(ib.getShowNotReprintedOnly()))
        	  billingPrintDateNull = "Y";
      }
      
      
      String query = "select * from table (pkg_invoice_print.fx_invoice_search("+
		StringHandler.emptyIfNull(personnelBean.getPersonnelId()) +",'" +
		StringHandler.emptyIfNull(personnelBean.getCompanyId()) +"','" +
		StringHandler.emptyIfNull(ib.getOpsEntityId()) +"','" +
		StringHandler.emptyIfNull(ib.getHub()) +"','" +
		StringHandler.emptyIfNull(ib.getInventoryGroup()) +"'," +
		ib.getCustomerId() +"," +
		(null!=ib.getShipConfirmDate()?"TO_DATE('" + dateFormatter.format( (java.util.Date) ib.getShipConfirmDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''") +"," +
		(null!=ib.getDeliveredDate()?"TO_DATE('" + dateFormatter.format( (java.util.Date) ib.getDeliveredDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''") +",'" +
		StringHandler.emptyIfNull(ib.getShipToLocationId()) +"'," +
		ib.getPrNumber() +"," +
		ib.getShipmentId() +",'" +
		StringHandler.emptyIfNull(ib.getCurrencyId()) +"','" +
		StringHandler.emptyIfNull(ib.getPoNumber()) + "','" +
		StringHandler.emptyIfNull(invoice) + "','" +
        StringHandler.emptyIfNull(billingPrintDateNull) + "',";
        
      	if("Y".equalsIgnoreCase(ib.getShowEInvoicesNotSent()))
      		query += "'Y'";
      	else if("Y".equalsIgnoreCase(ib.getShowEInvoices()))
      		query += "null";
      	else
      		query += "'N'";
      		
    	query += "))";
      
      String query1 = "";
      
      /*if ("invoice".equals(documentType) && idField != null && idField.trim().length() != 0 )
      {
        if("Y".equals(ib.getShowNeverPrinted()))
        	query1 = " and billing_print_date is null and shipping_print_date is null ";
        else if("Y".equals(ib.getShowNotReprintedOnly()))
        	query1 = " and billing_print_date is null ";  
        
        query = query + query1;
      }
      else*/
      {
        if("Y".equals(ib.getShowNeverPrinted()))
        	query1 = " where shipping_print_date is null ";
        
        query = query + query1;
      }
	  
	  return factory.selectQuery(query);
  }

  public ExcelHandler getExcelReport(Collection invoiceColl) throws
  NoDataException, BaseException, Exception {
	Collection<InvoiceSearchBean> data = invoiceColl; 
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	ExcelHandler pw = new ExcelHandler(library);
	BigDecimal total = new BigDecimal("0");
	Collection currencyColl = new Vector();
	Hashtable printInvoiceTotals = new Hashtable();
	pw.addTable();
	//write column headers
	pw.addRow();
	
	String[] headerkeys = {
	"label.customerid","label.customername","label.customerpo", "label.invoice", "label.customerinvoice","label.mrnumber","label.shipconfirmdate","label.currency",
	"label.goodsvalue","label.total","label.materialmargin","label.processprintdate","label.processprintedby", "label.reprintdate", "label.reprintedby", "label.einvoicestatus"};
	
	/*This array defines the type of the excel column.
	0 means default depending on the data type. */
	int[] types = {
				0,0,0,0,0,0,pw.TYPE_DATE,0,
				0,0,0,pw.TYPE_DATE,0,pw.TYPE_DATE,0,0};
	
	int[] widths = {
				11,20,10,10,10,15,0,0,
				0,8,0,0,15,0,15,0};
	
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	
	pw.applyColumnHeader(headerkeys, types, widths, null );
	
	// now write data
	//int i = 1;
	for (InvoiceSearchBean member : data) {
	
	  pw.addRow();
	
	  pw.addCell(member.getCustomerId());
	  pw.addCell(member.getCustomerName());
	  pw.addCell(member.getPoNumber());
	  pw.addCell(member.getInvoice());
	  pw.addCell(member.getCustomerInvoice());
	  String lineItem = StringHandler.isBlankString(member.getLineItem()) == true ? "" : ("-" + member.getLineItem());
	  pw.addCell(member.getPrNumber()+lineItem);
	  pw.addCell(member.getDateConfirmed());
	  pw.addCell(member.getCurrencyId());
	  pw.addCell(member.getTotalGoods());
	  pw.addCell(member.getTotal());
	  pw.addCell(member.getMargin());
	  pw.addCell(member.getShippingPrintDate());
	  pw.addCell(member.getShippingPrintName());
	  pw.addCell(member.getBillingPrintDate());
	  pw.addCell(member.getBillingPrintName());
	  pw.addCell(member.getAutoEmailStatus());
	  //total = total.add(member.getTotal());
	  String currencyId = member.getCurrencyId() == null ? "" : member.getCurrencyId();

	  if (!currencyColl.contains(currencyId)) {
		  PrintInvoiceTotalsBean printInvoiceTotalsBean2 = new PrintInvoiceTotalsBean();
		  printInvoiceTotalsBean2.setCurrencyId(currencyId);
		  			  

		  printInvoiceTotals.put(currencyId,printInvoiceTotalsBean2);
		  currencyColl.add(currencyId);
	  }

	  PrintInvoiceTotalsBean printInvoiceTotalsBean = (PrintInvoiceTotalsBean)printInvoiceTotals.get(currencyId);


	  printInvoiceTotalsBean.setTotal(member.getTotal());
	  
	 }
	try {
		 Enumeration E;
		 for (E = printInvoiceTotals.keys(); E.hasMoreElements(); ) {
		   String key = (String) E.nextElement();
		   PrintInvoiceTotalsBean printInvoiceTotalsBean = (PrintInvoiceTotalsBean)printInvoiceTotals.get(key);

	       pw.addRow();
	       pw.addTdEmpty(6);
	       pw.addTh("label.total");
	       pw.addCellBold(printInvoiceTotalsBean.getCurrencyId() );
	       pw.addCellBold(printInvoiceTotalsBean.getTotal().setScale(4,BigDecimal.ROUND_HALF_UP));
	       
		 }
	}
	catch (Exception e) {

	  }
			
	return pw;

  }
  
  public Collection regenEInvoice(Collection<InvoiceSearchBean> inputLines) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		for (InvoiceSearchBean inputBean : inputLines) {
			try {
				inArgs = new Vector(2);
				inArgs.add(inputBean.getInvoice());
				inArgs.add("Ready");
				inArgs.add(null);

		      Vector error = (Vector) factory.doProcedure("pkg_invoice_print.p_set_auto_email_status", inArgs, new Vector());

		      if(error.size()>0 && error.get(0) != null)
		      {
		    	  String errorCode = (String) error.get(0);
		    	  log.info("Error in Procedure pkg_invoice_print.p_set_auto_email_status: "+inputBean.getInvoice()+ " Error Code "+errorCode);
		    	  errorMessages.add(errorCode);
		      } 
			}
			catch (Exception e) {
				errorMsg = "Error staging invoice for regeneration: " + inputBean.getInvoice();
				errorMessages.add(errorMsg);
			}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
  
  public Collection<InvoiceSearchBean> getEInvoiceHistory(String invoice)  throws
  BaseException 
  {
		factory.setBean(new InvoiceSearchBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("invoice", SearchCriterion.EQUALS, invoice);
		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("emailSentDate");
		return factory.select(criteria, sort, "customer.invoice_auto_email");
  }
  
}

