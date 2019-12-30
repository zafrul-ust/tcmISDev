package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Vector;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.hub.beans.FreightInvoiceStageBean;
import com.tcmis.internal.hub.beans.FreightInvoicingInputBean;
import com.tcmis.internal.hub.beans.FreightRadianPOBean;


public class FreightInvoicingProcess extends BaseProcess {
  	Log log = LogFactory.getLog(this.getClass());

  	public FreightInvoicingProcess(String client, String locale) {
  		super(client,locale);
  	}
  	
  	public Collection<FreightInvoiceStageBean> getSearchData(FreightInvoicingInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new FreightInvoiceStageBean());
		
		StringBuilder query = new StringBuilder("SELECT * FROM freight_invoice_stage");
		StringBuilder queryCriteria = new StringBuilder();
		
		if(inputBean.isSearchByLoadId()) {
			queryCriteria.append("load_id ");
		}
		else if(inputBean.isSearchByOrderNumber()) {
			queryCriteria.append("order_number ");			
		}
		else {
			// default criteria
			queryCriteria.append("invoice_number ");
		}
		
		if(inputBean.isSearchLike())
			queryCriteria.append(SearchCriterion.LIKE).append(" '%").append(SqlHandler.validQuery(inputBean.getSearchArgument())).append("%'");
		else
			queryCriteria.append(SearchCriterion.EQUALS).append(SqlHandler.delimitString(inputBean.getSearchArgument()));
				
		String[] statusInputs = inputBean.getFreightInvoiceStatus();
		
		if (statusInputs != null) {
			StringBuilder statusCriteria = new StringBuilder(" status in (");
			boolean isAllStatus = false;
			
			for(int i=0; i < statusInputs.length; ++i) {
				if (statusInputs[i].equals("All")) {
					isAllStatus = true;
					break;
				}
				else {
					if (i != 0)
						statusCriteria.append(", ");
					statusCriteria.append(SqlHandler.delimitString(statusInputs[i]));
				}
			}
			
			statusCriteria.append(")");
			
			if (!isAllStatus) {
				if (queryCriteria.length() > 0)
					queryCriteria.append(" AND ");
				
				queryCriteria.append(statusCriteria.toString());
			}
		}

		query.append(" WHERE ");
		query.append(queryCriteria.toString());
		
		return genericSqlFactory.selectQuery(query.toString()); 
	}
  	
  	public String updateData(Collection beans) throws BaseException {
		String result = "OK";
		FreightInvoiceStageBean bean = null;
		
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new FreightInvoiceStageBean());
			Iterator iter = beans.iterator();		
			
			while(iter.hasNext()) {
				bean = (FreightInvoiceStageBean)iter.next();
				
				if (!bean.getOk()) continue;
				
				String query = "UPDATE freight_invoice_stage SET "
						+ "invoice_number = " + SqlHandler.delimitString(bean.getInvoiceNumber()) + ", " 
						+ "order_number = " + SqlHandler.delimitString(bean.getOrderNumber()) + ", " 
						+ "gl_code = " + SqlHandler.delimitString(bean.getGlCode()) + ", " 
						+ "order_type = " + SqlHandler.delimitString(bean.getOrderType()) + ", " 
						+ "status = " + SqlHandler.delimitString(bean.getStatus()) + ", "
						+ "comments = " +  SqlHandler.delimitString(bean.getComments()) + " "
						+ "WHERE "
						+ "freight_invoice_stage_id = " + bean.getFreightInvoiceStageId()
						;

				genericSqlFactory.deleteInsertUpdate(query);
			}

		}catch (Exception e) {
			result = "Error saving data ";
					
			if(bean != null)
				result += "for Load ID " + bean.getLoadId() + ", Load Line " + bean.getLoadLine() + " (Invoice Number " + bean.getInvoiceNumber() + ")";
			
			log.error(result, e);			
		}
		
		return result;
	}
  	
  	/*Ths is to reprocess the invoice lines*/
	public String reprocessInvoiceLines(FreightInvoicingInputBean bean) throws BaseException {
		String result = "OK";
		
		Collection inArgs = new Vector();
		try {
			inArgs.add((bean.getReprocessCarrier() != null) ? bean.getReprocessCarrier():"");
			inArgs.add((bean.getReprocessCurrencyId() != null) ? bean.getReprocessCurrencyId():"" );
			inArgs.add((bean.getReprocessLoadId() != null) ? bean.getReprocessLoadId():"" );
			
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			procFactory.doProcedure("PKG_FREIGHT_FORWARDER_DATA.P_FREIGHT_INVOICE_PROCESS", inArgs);
		}
		catch (Exception e) {
			result = "Error reprocessing the invoice lines for Load ID "+bean.getReprocessLoadId();
			log.error(result, e);			
		}
		return result;
	}
	
	//get freight carrier list
	public Collection<String> getFreightRadianCarrier() throws BaseException {
		
		try {
			
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
			
			StringBuilder query = new StringBuilder("SELECT DISTINCT carrier FROM freight_radian_po");
			query.append(" ORDER BY carrier");
			Collection<String> radianCarrierList = (Collection<String>) genericSqlFactory.setBean(new FreightRadianPOBean())
													.selectQuery(query.toString()).stream()
													.map(carrier -> ((FreightRadianPOBean)carrier).getCarrier()).collect(Collectors.toList());
			return radianCarrierList;
		} catch (Exception e) {
			log.error(e);
		}
		return Collections.emptyList();
	}
  	
  	public ExcelHandler getExcelReport(FreightInvoicingInputBean bean, Locale locale) throws NoDataException, BaseException, Exception {
  	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
  	  Collection<FreightInvoiceStageBean> data = this.getSearchData(bean);
  	  ExcelHandler pw = new ExcelHandler(library);

  	  pw.addTable();
  	  
  	  //result table
  	  //write column headers
  	  pw.addRow();

  	  /*Pass the header keys for the Excel.*/
  	  String[] headerkeys = {"label.invoicenumber","label.invoicedate","label.ordernumber","label.glcode","label.carrier", "label.trackingnumber","label.ordertype","label.transportationmode","label.loadid","label.loadline","label.status","label.errordetail","label.comments"};

  	  /*This array defines the type of the excel column.
  	    0 means default depending on the data type. */
  	  int[] types = {0,0,0,0,0,0,0,0,0,0,0,0,0};
     
  	  /*This array defines the default width of the column when the Excel file opens.
     		0 means the width will be default depending on the data type.*/
  	  int[] widths = {0,0,0,0,0,0,0,0,0,0,0,0,0};
     
  	  /*This array defines the horizontal alignment of the data in a cell.
     		0 means excel defaults the horizontal alignemnt by the data type.*/
  	  int[] horizAligns = {0,0,0,0,0,0,0,0,0,0,0,0,0};

  	  pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
  	  
  	  //now write data
  	  for (FreightInvoiceStageBean member : data) {
  		  pw.addRow();
  		  pw.addCell(member.getInvoiceNumber());
  		  pw.addCell(member.getInvoiceDate());
  		  pw.addCell(member.getOrderNumber());
  		  pw.addCell(member.getGlCode());
  		  pw.addCell(member.getCarrier());
  		  pw.addCell(member.getTrackingNumber());
  		  pw.addCell(member.getOrderType());
  		  pw.addCell(member.getTransportationMode());
  		  pw.addCell(member.getLoadId());
  		  pw.addCell(member.getLoadLine());
  		  pw.addCell(member.getStatus());
  		  pw.addCell(member.getErrorDetail());
  		  pw.addCell(member.getComments());
  	  }
     
  	  return pw;
   }
  	
  	public LinkedHashMap<String,String> getStatuses() {
  		// there is currently no table of valid statuses so it is manually defined here
  		
  		LinkedHashMap<String, String> statuses = new LinkedHashMap<String, String>(); 
  		
  		statuses.put("DUPLICATE", "label.duplicate"); 
  		statuses.put("Error", "label.errormixedcase"); 
  		statuses.put("GL", "label.gl"); 
  		statuses.put("NEW", "label.new"); 
  		statuses.put("PROCESSED", "label.processed"); 
  		statuses.put("Ready", "label.ready"); 
  		statuses.put("WAIT", "label.wait"); 
  		
  		return statuses;
  	}
  	
  	//return the list of unique Load ID
  	public Collection<BigDecimal> getDistinctLoadId(Collection<FreightInvoiceStageBean> freightInvoiceStageBean) {
  		Collection<BigDecimal> loadIdList = freightInvoiceStageBean.stream().map(
  					loadId -> ((FreightInvoiceStageBean)loadId).getLoadId())
  					.collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
  		  return loadIdList;
  	}
}
