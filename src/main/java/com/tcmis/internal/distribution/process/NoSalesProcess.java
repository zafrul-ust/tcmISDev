package com.tcmis.internal.distribution.process;

import java.text.DecimalFormat;
import java.util.*;
import org.apache.commons.logging.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.NoSalesInputBean;
import com.tcmis.internal.distribution.beans.NoSalesViewBean;



/******************************************************************************
 * Process for logistics
 * @version 1.0
 *****************************************************************************/
public class NoSalesProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public NoSalesProcess(String client,Locale locale) {
    super(client,locale);
  }

  public Collection getSearchResult(PersonnelBean personnelBean, NoSalesInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new NoSalesViewBean());
		
		SearchCriteria searchCriteria = new SearchCriteria();
	
		if(!StringHandler.isBlankString(inputBean.getOpsEntityId())) 
			searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		if(!StringHandler.isBlankString(inputBean.getHub())) 
			searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
	    if(!StringHandler.isBlankString(inputBean.getInventoryGroup())) 
	        searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
	    else {
	    	String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
	    	if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
	    		invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
	    	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
	    }
	    
	    if (inputBean.getNotIssuedIn() != null ) {
            searchCriteria.addCriterion("notIssuedIn",SearchCriterion.GREATER_THAN_OR_EQUAL_TO,inputBean.getNotIssuedIn().toString());
         }
	    
	return factory.select(searchCriteria, null,"no_sales_view");
 }


public ExcelHandler getExcelReport(Collection noSalsColl) throws
  NoDataException, BaseException, Exception {
	Collection<NoSalesViewBean> data = noSalsColl; 
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	ExcelHandler pw = new ExcelHandler(library);

pw.addTable();
//write column headers
pw.addRow();
/*Pass the header keys for the Excel.*/

String[] headerkeys = {
"label.operatingentity","label.hub","label.inventorygroup", "label.partno","label.description",
"label.onhand",
"label.currencyid","label.averagecost","label.value","label.lastsaledate"};
/*This array defines the type of the excel column.
0 means default depending on the data type. */
int[] types = {
0,0,0,0,pw.TYPE_PARAGRAPH,
0,
0,0,0,pw.TYPE_CALENDAR};

int[] widths = {
	      20,0,0,0,0,
	      0,
	      0,0,0,0};

/*This array defines the horizontal alignment of the data in a cell.
0 means excel defaults the horizontal alignemnt by the data type.*/

pw.applyColumnHeader(headerkeys, types, widths, null );

pw.setColumnDigit(7, 4);
pw.setColumnDigit(8, 2);	


// now write data
//int i = 1;
for (NoSalesViewBean member : data) {

  pw.addRow();

  pw.addCell(member.getOperatingEntityName());
  pw.addCell(member.getHubName());
  pw.addCell(member.getInventoryGroup());
  pw.addCell(member.getPartNo());
  pw.addCell(member.getPartShortName());
  pw.addCell(member.getOnHand()); 
  if(member.getAverageCost() == null) {
	  pw.addCell("");pw.addCell("");pw.addCell("");
  } else {
	  pw.addCell(member.getCurrencyId());   
	  pw.addCell(member.getAverageCost());  
	  pw.addCell(member.getOnHand().multiply(member.getAverageCost()));  
  }
  pw.addCell(member.getLastSaleDate());
}
return pw;
}



}
