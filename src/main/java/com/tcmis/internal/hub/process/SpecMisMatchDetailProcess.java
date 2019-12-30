package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.SpecMisMatchDetailInputBean;
import com.tcmis.internal.hub.beans.SpecMisMatchDetailViewBean;




public class SpecMisMatchDetailProcess extends BaseProcess {
	  Log log = LogFactory.getLog(this.getClass());

	  public SpecMisMatchDetailProcess(String client) {
	    super(client);
	  }
		
	  public SpecMisMatchDetailProcess(String client, String locale) {
	    super(client,locale);
	  }

	  public Collection<SpecMisMatchDetailViewBean> getSpecMismatchDetail(SpecMisMatchDetailInputBean inputSearchBean, PersonnelBean personnelBean)
		throws BaseException {

			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SpecMisMatchDetailViewBean());
			
			String query = "select * from nshaik.spec_mismatch_detail ";
			
			if(!StringHandler.isBlankString(inputSearchBean.getIssueSeverity()) ||  !StringHandler.isBlankString(inputSearchBean.getDropShip())
					|| !StringHandler.isBlankString(inputSearchBean.getCustomerName()))
					query = query + "where";
			if(!StringHandler.isBlankString(inputSearchBean.getIssueSeverity()))
			{
				 query = query + " issue_severity = '"+inputSearchBean.getIssueSeverity()+"'";
				if(!StringHandler.isBlankString(inputSearchBean.getDropShip()))
					query = query + " and dropship = '"+inputSearchBean.getDropShip()+"'";	
				if(!StringHandler.isBlankString(inputSearchBean.getCustomerName()))
					query = query + " and upper(customer_name) like upper('%"+inputSearchBean.getCustomerName()+"%')";
			}	
			else
			{
				if(!StringHandler.isBlankString(inputSearchBean.getDropShip()))
	            	query = query + " dropship = '"+inputSearchBean.getDropShip()+"'";
				if(!StringHandler.isBlankString(inputSearchBean.getCustomerName()))
	            	query = query + "  upper(customer_name) like upper('%"+inputSearchBean.getCustomerName()+"%')";
				
			}
			
			
            			
            return factory.selectQuery(query);
	  }
	 
		
	  public ExcelHandler getExcelReport(Collection<SpecMisMatchDetailViewBean> data, Locale locale) throws
	      NoDataException, BaseException, Exception {
	    
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
	//write column headers
		pw.addRow();
	/*Pass the header keys for the Excel.*/
			
		  
		pw.setColumnHeader(1, "Issue Severity");
		pw.setColumnHeader(2, "Issue Comment");
		pw.setColumnHeader(3, "Mismatch Comment");
		pw.setColumnHeader(4, "DropShip");
		pw.setColumnHeader(5, "Receipt PO");
		pw.setColumnHeader(6, "Receipt PO Line");
		pw.setColumnHeader(7, "Promise Date");
		pw.setColumnHeader(8, "Customer Name");
		pw.setColumnHeader(9, "Inventory Group");
		pw.setColumnHeader(10, "MR Line");
		pw.setColumnHeader(11, "Fac Part No");
		pw.setColumnHeader(12, "Part Description");
		pw.setColumnHeader(13, "Receipt Id");
		pw.setColumnHeader(14, "Quantity");
		pw.setColumnHeader(15, "Bought For This");
		pw.setColumnHeader(16, "Part Spec Requirement");
		pw.setColumnHeader(17, "Spec On Receipt");
		pw.setColumnHeader(18, "Ship To Address");
		
		
		// format the numbers to the special columns
		

		for (SpecMisMatchDetailViewBean member : data) {
		  
	      pw.addRow();
	      pw.addCell(member.getIssueSeverity());
	      pw.addCell(member.getIssueComment());
	      pw.addCell(member.getMismatchComment());
	      pw.addCell(member.getDropship());
	      pw.addCell(member.getReceiptPo());
	      pw.addCell(member.getReceiptPoLine());
	      pw.addCell(member.getPromiseDate());
	      pw.addCell(member.getCustomerName());
	      pw.addCell(member.getInventoryGroup());
	      pw.addCell(member.getMrLine());
	      pw.addCell(member.getFacPartNo());
	      pw.addCell(member.getPartDescription());
	      pw.addCell(member.getReceiptId());
	      pw.addCell(member.getQuantity());
	      pw.addCell(member.getBoughtForThis());
	      pw.addCell(member.getPartSpecRequirement());
	      pw.addCell(member.getSpecOnReceipt());
	      pw.addCell(member.getShipToAddress());
	           
	    
	    }
	    return pw;
	  }
	  
	}
