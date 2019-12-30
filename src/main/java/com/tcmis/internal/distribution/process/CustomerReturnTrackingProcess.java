package com.tcmis.internal.distribution.process;

import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CustomerReturnTrackingInputBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestViewBean;



/******************************************************************************
 * Process for logistics
 * @version 1.0
 *****************************************************************************/
public class CustomerReturnTrackingProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CustomerReturnTrackingProcess(String client,Locale locale) {
    super(client,locale);
  }

  public Collection getSearchResult(CustomerReturnTrackingInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerReturnRequestViewBean());
		
		SearchCriteria searchCriteria = new SearchCriteria();
	
		if(!StringHandler.isBlankString(inputBean.getOpsEntityId())) 
			searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		if(!StringHandler.isBlankString(inputBean.getHub())) 
			searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
	    if(!StringHandler.isBlankString(inputBean.getInventoryGroup())){ 
	        searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
	    }
        else {
            String invQuery = " select distinct inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
            if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
                invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
            searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
        }
	    

	    String s = null;

	    s = inputBean.getSearchArgument();
	    if ( s != null && !s.equals("") ) {
	    	String mode = inputBean.getSearchMode();
	    	String field = inputBean.getSearchField();

	    	if( mode.equals("is") )
	    	{
	    		if( field.equalsIgnoreCase("prNumber") )
	    		{
	    			searchCriteria.addCriterion(field,SearchCriterion.EQUALS,s);
	    		}
	    		else if ( field.equalsIgnoreCase("customerRmaId") )
	    		{
	    			searchCriteria.addCriterion(field,SearchCriterion.EQUALS,s);
	    		}
	    		else
	    		{
	    			searchCriteria.addCriterion(field,SearchCriterion.EQUALS,
	    					s,SearchCriterion.IGNORE_CASE);
	    		}
	    	}
	    	if( mode.equals("contains"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.LIKE,
	    				s,SearchCriterion.IGNORE_CASE);
	    	if( mode.equals("startsWith"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.STARTS_WITH,
	    				s,SearchCriterion.IGNORE_CASE); 
	    	if( mode.equals("endsWith"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.ENDS_WITH,
	    				s,SearchCriterion.IGNORE_CASE);
	    }
	    
	    if ( inputBean.getRmaStatus() != null && !("").equals(inputBean.getRmaStatus()) ) {
	    	searchCriteria.addCriterion("rmaStatus",SearchCriterion.EQUALS,inputBean.getRmaStatus());
	    }
	    
	    if(inputBean.getSearchOption() != null && "1".equals(inputBean.getSearchOption())) 
	    	searchCriteria.addCriterion("rmaStatus",SearchCriterion.NOT_IN,"'Complete','Draft'");
//	    	searchCriteria.addCriterion("rmaStatus",SearchCriterion.IN,"'Submitted', 'Draft'");
	    if (inputBean.getSearchOption() != null && "2".equals(inputBean.getSearchOption()) && inputBean.getDays() != null) {
	    	Calendar cal = Calendar.getInstance();   
	        int days =(inputBean.getDays().intValue())*(-1);
	        cal.add(Calendar.DATE,days);  	    	
            searchCriteria.addCriterion("requestStartDate",SearchCriterion.FROM_DATE,cal.getTime());
         }
	    SortCriteria scriteria = new SortCriteria();
	    
	return factory.select(searchCriteria, scriteria,"customer_return_request_view");
 }


public ExcelHandler getExcelReport(Collection customerReturnRequestColl) throws
  NoDataException, BaseException, Exception {
	Collection<CustomerReturnRequestViewBean> data = customerReturnRequestColl; 
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	ExcelHandler pw = new ExcelHandler(library);

pw.addTable();
//write column headers
pw.addRow();
/*Pass the header keys for the Excel.*/

String[] headerkeys = {
"label.rma","label.csr","label.operatingentity","label.inventorygroup","label.customer","label.customerpoline", "label.mrline","label.customerpn",
"label.ourpn","label.description","label.returnquantityrequested","label.returnquantityauthorized", "label.currencyid","label.extprice",
"label.status","label.reason","label.comments"};
/*This array defines the type of the excel column.
0 means default depending on the data type. */
int[] types = {
0,0,0,0,0,0,0,
0,0,0,0,0,0,0,
0,0,0};

int[] widths = {
	      0,20,20,20,20,18,0,10,
	      0,18,12,12,0,0,
	      15,20,20};

/*This array defines the horizontal alignment of the data in a cell.
0 means excel defaults the horizontal alignemnt by the data type.*/

pw.applyColumnHeader(headerkeys, types, widths, null );

pw.setColumnDigit(13,2);
// now write data
//int i = 1;
for (CustomerReturnRequestViewBean member : data) {

  pw.addRow();

  pw.addCell(member.getCustomerRmaId());
  pw.addCell(member.getCsrName());
  pw.addCell(member.getOpsEntityName()); 
  pw.addCell(member.getInventoryGroupName()); 
  pw.addCell(member.getCustomerName());
  if (member.getPoNumber() == null) member.setPoNumber("");
  if (member.getReleaseNumber() == null) member.setReleaseNumber("");
  pw.addCell(member.getPoNumber()+"-"+member.getReleaseNumber());
  pw.addCell(member.getPrNumber()+"-"+member.getLineItem());
  pw.addCell(member.getFacPartNo());
  pw.addCell(member.getHaasPartNo());
  pw.addCell(member.getPartDescription());
  pw.addCell(member.getQuantityReturnRequested());
  pw.addCell(member.getQuantityReturnAuthorized());
  pw.addCell(member.getCurrencyId());
  if(member.getQuantityReturnAuthorized() == null || member.getUnitPrice() == null)
	  pw.addCell("");
  else
	  pw.addCell(member.getQuantityReturnAuthorized().multiply(member.getUnitPrice()));    
  pw.addCell(member.getRmaStatus());
  pw.addCell(member.getReasonDescription());
  pw.addCell(member.getReturnNotes());
}
return pw;
}



}
