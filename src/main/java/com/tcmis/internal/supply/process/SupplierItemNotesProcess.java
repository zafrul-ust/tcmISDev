package com.tcmis.internal.supply.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CatalogItemNotesViewBean;
import com.tcmis.internal.hub.beans.Cfr49HazardousMaterialViewBean;
import com.tcmis.internal.supply.beans.SupplierItemNotesInputBean;
import com.tcmis.internal.supply.beans.SuppEntityItemNotesViewBean;


/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class SupplierItemNotesProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public SupplierItemNotesProcess(String client) {
    super(client);
  }
	
  public SupplierItemNotesProcess(String client, String locale) {
    super(client,locale);
  }

  public Collection<SuppEntityItemNotesViewBean> getItemNotes(SupplierItemNotesInputBean inputSearchBean, PersonnelBean personnelBean)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SuppEntityItemNotesViewBean());
		SearchCriteria criteria = new SearchCriteria();
		
		
			if(!StringHandler.isBlankString(inputSearchBean.getOpsEntityId()))
				criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputSearchBean.getOpsEntityId());
			else
            {
                String opsEntityQuery = " select ops_entity_id from user_ops_entity where personnel_id = " + personnelBean.getPersonnelId() ;
                criteria.addCriterion("opsEntityId", SearchCriterion.IN, opsEntityQuery);
            }
            if(!StringHandler.isBlankString(inputSearchBean.getSupplier()))
				criteria.addCriterion("supplier", SearchCriterion.EQUALS, inputSearchBean.getSupplier());
			
			if(!StringHandler.isBlankString(inputSearchBean.getSearchArgument())){
				String mode = inputSearchBean.getSearchMode();
				String field = inputSearchBean.getSearchField();
				if (mode.equals("is")){
					criteria.addCriterion(field, SearchCriterion.EQUALS,inputSearchBean.getSearchArgument());
				}
			}
			/*	if (mode.equals("contains"))
					criteria.addCriterion(field, SearchCriterion.LIKE, inputSearchBean.getSearchArgument());
				if (mode.equals("startsWith"))
					criteria.addCriterion(field, SearchCriterion.STARTS_WITH, inputSearchBean.getSearchArgument());
				if (mode.equals("endsWith"))
					criteria.addCriterion(field, SearchCriterion.ENDS_WITH, inputSearchBean.getSearchArgument());
				
			*/	
			
			SortCriteria sort = new SortCriteria();
			sort.setSortAscending(true);		
			sort.addCriterion("opsEntityId");
            sort.addCriterion("supplier");
            sort.addCriterion("itemId");
            return factory.select(criteria, sort, "SUPP_ENTITY_ITEM_NOTES_VIEW");
  }
  
  public void addNotes(SuppEntityItemNotesViewBean bean, PersonnelBean personnel) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(),getLocale());
	   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
	   try {
			String query = "insert into supplier_entity_item_notes (ops_entity_id,personnel_id,item_id,supplier,comments,transaction_date) values (" +
				"'"+bean.getOpsEntityId()+"',"+personnel.getPersonnelId()+","+bean.getItemId().toString()+","+bean.getSupplier()+",'"+bean.getComments()+"',sysdate)";
			genericSqlFactory.deleteInsertUpdate(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
}
  
  public String update(Collection<SuppEntityItemNotesViewBean> beans, PersonnelBean personnel) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),this.getLocale());
	GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SuppEntityItemNotesViewBean());
	for(SuppEntityItemNotesViewBean b :beans)
	{
		if(b.isOkDoUpdate())
		{
			String query = "update SUPP_ENTITY_ITEM_NOTES_VIEW SET comments ='" + b.getComments() + "' where item_id =" + b.getItemId().toPlainString();
			
			if(b.getRecordNo() != null) 
				query += " and record_no =" + b.getRecordNo().toPlainString();
			
			try{
				factory.deleteInsertUpdate(query);
			}catch(Exception e){return e.toString();}
			finally{
				dbManager = null;
				factory = null;
			}
		}
	}
	return "";
  }
  
  public String delete(Collection<SuppEntityItemNotesViewBean> beans, PersonnelBean personnel) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SuppEntityItemNotesViewBean());
		for(SuppEntityItemNotesViewBean b :beans)
		{
			if(b.isOkDoUpdate())
			{
				String query = "delete from SUPP_ENTITY_ITEM_NOTES_VIEW where item_id =" + b.getItemId().toPlainString()+ " and supplier = '" + b.getSupplier() + "'"+ " and ops_entity_id = '" + b.getOpsEntityId() + "'";
				
				if(b.getRecordNo() != null) 
					query += " and record_no =" + b.getRecordNo().toPlainString();
				
				try{
					factory.deleteInsertUpdate(query);
				}catch(Exception e){return e.toString();}
				finally{
					dbManager = null;
					factory = null;
				}
			}
		}
		return "";
	  } 
	
  public ExcelHandler getExcelReport(Collection<SuppEntityItemNotesViewBean> data, Locale locale) throws
      NoDataException, BaseException, Exception {
    
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
//write column headers
	pw.addRow();
/*Pass the header keys for the Excel.*/
	String[] headerkeys = {
	  "label.supplier","label.itemid","label.itemdesc","label.transactiondate","label.enteredby","label.comments"
	  };
	/*This array defines the type of the excel column.
	0 means default depending on the data type. 
	pw.TYPE_PARAGRAPH defaults to 40 characters.
	pw.TYPE_CALENDAR set up the date with no time.
	pw.TYPE_DATE set up the date with time.*/
	int[] types = {
	  0,pw.TYPE_NUMBER,pw.TYPE_PARAGRAPH,pw.TYPE_CALENDAR,0,pw.TYPE_PARAGRAPH
	  };
	
	/*This array defines the default width of the column when the Excel file opens.
	0 means the width will be default depending on the data type.*/
	int[] widths = {
	  15,6,0,14,10,0
	  };
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	int[] horizAligns = {
	  0,0,0,0,0,0
	  };
	  
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
	// format the numbers to the special columns
	pw.setColumnDigit(6, 2);
	pw.setColumnDigit(7, 2);

	for (SuppEntityItemNotesViewBean member : data) {
	  
      pw.addRow();
      pw.addCell(member.getSupplierName());
      pw.addCell(member.getItemId());
      pw.addCell(member.getItemDesc());
      pw.addCell(member.getTransactionDate());
      pw.addCell(member.getEnteredByName());
      pw.addCell(member.getComments());
    
    }
    return pw;
  }
  
}
