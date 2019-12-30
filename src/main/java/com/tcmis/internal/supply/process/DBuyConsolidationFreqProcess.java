package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.hub.beans.MinMaxLevelInputBean;
import com.tcmis.internal.supply.beans.DBuyConsolidationFreqInputBean;
import com.tcmis.internal.supply.beans.DBuyConsolidationFreqViewBean;
import com.tcmis.internal.supply.factory.DBuyConsolidationFreqViewBeanFactory;
import com.tcmis.internal.supply.factory.VvSupplyPathTypeBeanFactory;



public class DBuyConsolidationFreqProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public DBuyConsolidationFreqProcess(String client, String locale) {
		super(client, locale);
	}

	public DBuyConsolidationFreqProcess(String client) {
		super(client);
	}
	
	public Collection getSupplyPathType() throws
	NoDataException, BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		VvSupplyPathTypeBeanFactory vvSupplyPathTypeBeanFactory = new
		VvSupplyPathTypeBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("buyPageSearch",SearchCriterion.EQUALS,"Y");
		criteria.addCriterion("supplyPath",SearchCriterion.IN,"'Wbuy','Ibuy','Dbuy'");
		return vvSupplyPathTypeBeanFactory.select(criteria);
	}

	public Collection getSearchResult(DBuyConsolidationFreqInputBean bean,PersonnelBean personnelBean) throws BaseException {
		
		DbManager dbManager = new DbManager(getClient(), getLocale());
		DBuyConsolidationFreqViewBeanFactory factory = new DBuyConsolidationFreqViewBeanFactory(dbManager);
		 SearchCriteria criteria = new SearchCriteria();
		
		try {
			if(!StringHandler.isBlankString(bean.getOpsEntityId()))
				criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId());
			if (!StringHandler.isBlankString(bean.getInventoryGroup())) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			} else {
				String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
				if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0)
					invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' ";
				if (bean.getHub() != null && bean.getHub().length() != 0)
					invQuery += " and hub = '" + bean.getHub() + "' ";
				if( bean.getOpsEntityId()!=null && bean.getOpsEntityId().length()>0 )
					invQuery +=  " and ops_entity_id = '" + bean.getOpsEntityId() +"' ";
				criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
			}
			
			SortCriteria sort = new SortCriteria();
			sort.setSortAscending(true);		
			sort.addCriterion("inventoryGroup,runDay,runTime,supplyPath");		  	
			return factory.select(criteria, sort);
		}  
		finally {
			dbManager = null;
			factory = null;
		}		
  }
	
  	
	
	public Collection update(Collection dBuyConsolidateFreqColl,PersonnelBean personnelBean,DBuyConsolidationFreqInputBean inputBean)  throws BaseException, Exception {
		  Collection errorCollection = new Vector(0);
		  String errorMsg = null;
		  DbManager dbManager = new DbManager(getClient(),getLocale());
		  GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		  PermissionBean perbean = personnelBean.getPermissionBean();
		  for( DBuyConsolidationFreqViewBean bean:(Collection<DBuyConsolidationFreqViewBean>) dBuyConsolidateFreqColl)
			  if("true".equals(bean.getOkDoUpdate())) {
				  if( !perbean.hasOpsEntityPermission("dBuyConsolidationFreq", bean.getOpsEntityId(), null))
					  continue;
				  try {
					     errorMsg = updateCurrentValue(bean,personnelBean,inputBean);
					
					  if( errorMsg != null ) {
						  //errorMsg += "\n"+library.getString("error.db.update")+" "+ bean.getInventoryGroup();
						  errorCollection.add(errorMsg);
					  }
				  }
				  catch(Exception e) {
					  errorMsg = ""+library.getString("error.db.update")+" "+ bean.getInventoryGroup(); 
					  log.error(errorMsg , e);
					 // MailProcess.sendEmail("deverror@tcmis.com", "", MailProcess.DEFAULT_FROM_EMAIL, "Min Max Level Change Error", e.getMessage());
					  errorCollection.add(errorMsg);
				  }
			  }
		  return errorCollection;
	  }	
	
	public Collection delete(Collection dBuyConsolidateFreqColl,PersonnelBean personnelBean,DBuyConsolidationFreqInputBean inputBean)  throws BaseException, Exception {
		  Collection errorCollection = new Vector(0);
		  String errorMsg = null;
		  PermissionBean perbean = personnelBean.getPermissionBean();
		  for( DBuyConsolidationFreqViewBean bean:(Collection<DBuyConsolidationFreqViewBean>) dBuyConsolidateFreqColl)
			  if("true".equals(bean.getOkDoUpdate())) {
				  if( !perbean.hasOpsEntityPermission("dBuyConsolidationFreq", bean.getOpsEntityId(), null))
					  continue;
				  try {
					     errorMsg = updateCurrentValue(bean,personnelBean,inputBean);
					  						  
					  if( errorMsg != null ) {
						  errorMsg += "\n"+library.getString("error.db.delete")+" "+ bean.getInventoryGroup();
						  errorCollection.add(errorMsg);
					  }
				  }
				  catch(Exception e) {
					  errorMsg = ""+library.getString("error.db.delete")+" "+ bean.getInventoryGroup(); 
					  log.error(errorMsg , e);
					 // MailProcess.sendEmail("deverror@tcmis.com", "", MailProcess.DEFAULT_FROM_EMAIL, "Min Max Level Change Error", e.getMessage());
					  errorCollection.add(errorMsg);
				  }
			  }
		  return errorCollection;
	  }	
	
	
	
	 private String updateCurrentValue(DBuyConsolidationFreqViewBean bean,PersonnelBean personnelBean,DBuyConsolidationFreqInputBean inputBean)  throws
     BaseException, Exception {
     Collection inArgs = new Vector(8);
  
   if(bean.getInventoryGroup() != null) {
     inArgs.add(bean.getInventoryGroup());
   }
   else {
     inArgs.add("");
   }
   if(bean.getOldSupplyPath() != null) {
     inArgs.add(bean.getOldSupplyPath());
   }
   else {
     inArgs.add("");
   }
   if(bean.getSupplyPath() != null) {
	     inArgs.add(bean.getSupplyPath());
	   }
	   else {
	     inArgs.add("");
	   }
   if(bean.getOldRunTime() != null) {
     inArgs.add(bean.getOldRunTime());
   }
   else {
     inArgs.add("");
   }
   if(bean.getRunTime() != null) {
     inArgs.add(bean.getRunTime());
   }
   else {
     inArgs.add("");
   }
   if(inputBean.getuAction().equals("update"))
   {
	  if(bean.getEnteredBy().equalsIgnoreCase(""))
	  {
		inArgs.add("INSERT");   
	  }
	  else
	  {
		inArgs.add("UPDATE");   
	  }
   }
   
  if(inputBean.getuAction().equals("delete")){
	  inArgs.add("DELETE");
  }
	  
   inArgs.add(""+personnelBean.getPersonnelId());
   
   if(bean.getRunDay() != null) {
     inArgs.add(bean.getRunDay());
   }
   else {
     inArgs.add("");
   }
  
   Collection outArgs = new Vector(1);
   outArgs.add(new Integer(java.sql.Types.VARCHAR));

   
   DbManager dbManager = new DbManager(getClient(),getLocale());
   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   log.debug("pkg_consolidate_buyorder.p_set_ig_to_consolidate_buy"+ inArgs+ outArgs);
   Collection resultCollection = procFactory.doProcedure("pkg_consolidate_buyorder.p_set_ig_to_consolidate_buy", inArgs, outArgs);
   Iterator iterator = resultCollection.iterator();
   int count = 0;
   String result = null;
   while(iterator.hasNext()) {
     result = (String)iterator.next();
   }
   if(log.isDebugEnabled()) {
     log.debug("Result:" + result);
   }
   if(result != null && result.indexOf("THERE IS NO CHANGE OR NO RECORD IN CPI WITH YOUR CRITERIA") == -1 ) {
     return (result);
   }
   
   return null;
 }
	
public ExcelHandler getExcelReport(Collection<DBuyConsolidationFreqViewBean> data, Locale locale) throws
    BaseException, Exception {
  
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
//write column headers
	pw.addRow();
/*Pass the header keys for the Excel.*/
	String[] headerkeys = {
	  "label.inventorygroup","label.supplypath","label.runtime","label.runday",
	  "label.updatedby","label.updateddate"};
	/*This array defines the type of the excel column.
	0 means default depending on the data type. 
	pw.TYPE_PARAGRAPH defaults to 40 characters.
	pw.TYPE_CALENDAR set up the date with no time.
	pw.TYPE_DATE set up the date with time.*/
	int[] types = {
	  0,0,pw.TYPE_NUMBER,pw.TYPE_NUMBER,
	  0,pw.TYPE_CALENDAR};
	
	/*This array defines the default width of the column when the Excel file opens.
	0 means the width will be default depending on the data type.*/
	int[] widths = {
	  15,12,6,6,
	  15,12};
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	int[] horizAligns = {
	  0,0,0,0,
	  0,0};
	  
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
	// format the numbers to the special columns
	pw.setColumnDigit(6, 2);
	pw.setColumnDigit(7, 2);
	


	for (DBuyConsolidationFreqViewBean member : data) {
	  
    pw.addRow();
    pw.addCell(member.getInventoryGroup());
    pw.addCell(member.getSupplyPath());
    if (member.getRunTime() != null)
    {
    	BigDecimal twelve = new BigDecimal(12);
    	if(member.getRunTime().compareTo(twelve) == 1 || member.getRunTime().subtract(twelve).abs().compareTo(twelve) == 0)
    	{
    		BigDecimal tmp = member.getRunTime().subtract(twelve).abs();
    			if(tmp.compareTo(twelve) == 0)
    				pw.addCell("12 am");
    			else
    				pw.addCell(member.getRunTime().subtract(twelve).abs().toPlainString() + " pm");
    	}
    	else
    	{
    		if(member.getRunTime().compareTo(twelve) == 0)
    			pw.addCell("12 pm");
    		else
    			pw.addCell(member.getRunTime().toPlainString() + " am");
    	}
    }
    else
    	pw.addCell("");
	if(member.getRunDay() != null)
    	switch (member.getRunDay().intValueExact()) {
			case 1: pw.addCell("Monday");
		    break;
			case 2: pw.addCell("Tuesday");
		    break;
			case 3: pw.addCell("Wednesday");
		    break;
			case 4: pw.addCell("Thursday");
		    break;
			case 5: pw.addCell("Friday");
		    break;
			case 6: pw.addCell("Saturday");
		    break;
			case 7: pw.addCell("Sunday");
		    break;
		    default: pw.addCell("Everyday");
		    break;
	    }
	else
		 pw.addCell("");
    pw.addCell(member.getEnteredBy());

    pw.addCell(member.getTransactionDate());
   
  }
  return pw;
}
public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, 
    THURSDAY, FRIDAY, SATURDAY 
}
}



