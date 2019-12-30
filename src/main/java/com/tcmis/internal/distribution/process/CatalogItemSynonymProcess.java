package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.Date;
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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CatalogItemSynonymViewBean;
import com.tcmis.internal.distribution.beans.CatalogItemSynonymInputBean;
import com.tcmis.internal.supply.beans.SuppEntityItemNotesViewBean;


public class CatalogItemSynonymProcess  extends BaseProcess {

	  public CatalogItemSynonymProcess(String client) {
	    super(client);
	  }
		
	  public CatalogItemSynonymProcess(String client, String locale) {
	    super(client,locale);
	  }
	  
	  public Collection<CatalogItemSynonymViewBean> getCatalogItemSynonyms(CatalogItemSynonymInputBean inputSearchBean, PersonnelBean personnelBean)
		throws BaseException {

			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatalogItemSynonymViewBean());
			SearchCriteria criteria = new SearchCriteria();
			
			criteria.addCriterionWithMode(inputSearchBean.getSearchField(), inputSearchBean.getSearchMode(), inputSearchBean.getSearchArgument() );
			
							
				SortCriteria sort = new SortCriteria();
				sort.setSortAscending(true);		
				
	            sort.addCriterion("itemId");
	            return factory.select(criteria, sort, "CATALOG_ITEM_SYNONYM_VIEW");
	  }
	  
	  public String updateSynonym(Collection<CatalogItemSynonymViewBean> all) throws BaseException {
		  String result = "OK";
		   DbManager dbManager = new DbManager(getClient(),getLocale());
		   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
           ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());

           for(CatalogItemSynonymViewBean bean: all) {
		        if (!bean.getPartSynonym().equals(bean.getOriginalSynonym())) {
		   try {
			   if ("OK".equalsIgnoreCase(result))
			   {
				   
				   String tmpVal = genericSqlFactory.selectSingleValue("select count(*) from " +
				   		"CATALOG_ITEM_SYNONYM where part_synonym = '" + bean.getPartSynonym() + "' " +
				   		"and catalog_company_id = '"+bean.getCatalogCompanyId()+"' " + 
				   		" and catalog_id = '" +bean.getCatalogId()+ "' and item_id =" +bean.getItemId());
				   
				    	if (!"0".equalsIgnoreCase(tmpVal)) 
				    	{
						  genericSqlFactory.deleteInsertUpdate("delete from CATALOG_ITEM_SYNONYM where " +
								"part_synonym = '" + bean.getOriginalSynonym() + "' " +
						   		"and catalog_company_id = '"+bean.getCatalogCompanyId()+"' " + 
						   		" and catalog_id = '" +bean.getCatalogId()+ "' and item_id =" +bean.getItemId());
					    }
				    	else
				    	{
				    	
					       String query  = "update CATALOG_ITEM_SYNONYM set " +
									   "part_synonym = '" + bean.getPartSynonym() + "' " +
									   "where part_synonym = '" + bean.getOriginalSynonym()+ "' " +
									   "and catalog_company_id = '"+bean.getCatalogCompanyId()+"' " + 
									   " and catalog_id = '" +bean.getCatalogId()+ "' and item_id =" +bean.getItemId();
					       genericSqlFactory.deleteInsertUpdate(query);
				    	}
				    	
	             }
			 }
		   catch (Exception e) {
				e.printStackTrace();
				result = library.getString("error.db.update");
			  }
		   
		     }
		   }
	  
			return result;
	}	
	  
	  
public void addSynonym(CatalogItemSynonymViewBean bean, PersonnelBean personnel) throws BaseException {
	        DbManager dbManager = new DbManager(getClient(),getLocale());
		   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		   try {
			    String tmpVal = genericSqlFactory.selectSingleValue("select count(*) from " +
				   		"CATALOG_ITEM_SYNONYM where part_synonym = '" + bean.getPartSynonym() + "' " +
				   		"and catalog_company_id = '"+bean.getCatalogCompanyId()+"' " + 
				   		" and catalog_id = '" +bean.getCatalogId()+ "' and item_id =" +bean.getItemId());
			    
			    if ("0".equalsIgnoreCase(tmpVal)) 
			    {
			   
				String query = "insert into CATALOG_ITEM_SYNONYM (catalog_company_id,catalog_id,item_id,part_synonym) values (" +
					"'"+bean.getCatalogCompanyId()+"','"+bean.getCatalogId()+"',"+bean.getItemId().toString()+",'"+bean.getPartSynonym()+"')";
				genericSqlFactory.deleteInsertUpdate(query);
			    }
			}catch (Exception e) {
				e.printStackTrace();
			}
	}	  
  
	 		 
	  
 public ExcelHandler getExcelReport(Collection<CatalogItemSynonymViewBean> data, Locale locale) throws
      NoDataException, BaseException, Exception {
    
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
//write column headers
	pw.addRow();
/*Pass the header keys for the Excel.*/
	String[] headerkeys = {
 	"label.catalog","label.itemid","label.itemdesc","label.synonym"
	  };
	/*This array defines the type of the excel column.
	0 means default depending on the data type. 
	pw.TYPE_PARAGRAPH defaults to 40 characters.
	pw.TYPE_CALENDAR set up the date with no time.
	pw.TYPE_DATE set up the date with time.*/
	int[] types = {
	  0,pw.TYPE_NUMBER,pw.TYPE_PARAGRAPH,0
	  };
	
	/*This array defines the default width of the column when the Excel file opens.
	0 means the width will be default depending on the data type.*/
	int[] widths = {
	  10,6,14,10
	  };
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	int[] horizAligns = {
	  0,0,0,0
	  };
	  
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
	// format the numbers to the special columns
	pw.setColumnDigit(6, 2);
	pw.setColumnDigit(7, 2);

	for (CatalogItemSynonymViewBean member : data) {
	  
      pw.addRow();
      pw.addCell(member.getCatalogDesc());
      pw.addCell(member.getItemId());
      pw.addCell(member.getItemDesc());
      pw.addCell(member.getPartSynonym());
    
    }
    return pw;
  }
  
	    
}
