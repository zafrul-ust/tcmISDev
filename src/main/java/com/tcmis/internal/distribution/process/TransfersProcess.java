package com.tcmis.internal.distribution.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.UserOpsEntityHubIgOvBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.TransferRequestReportViewBean;
import com.tcmis.internal.distribution.beans.TransfersInputBean;


/******************************************************************************
 * Process for logistics
 * @version 1.0
 *****************************************************************************/
public class TransfersProcess extends BaseProcess {
  private static final Log log = LogFactory.getLog(TransfersProcess.class);

  public TransfersProcess(String client,Locale locale) {
    super(client,locale);
  }
  
  public Collection getAllOpsHubIgData(PersonnelBean loginBean) throws BaseException {
	    //log.debug("Calling getHubInventoryGroupData" + getClient());
	    if ("TCM_OPS".equalsIgnoreCase(getClient())) {
	      SearchCriteria criteria = new SearchCriteria();
	      criteria.addCriterion("personnelId", SearchCriterion.EQUALS, "-1");
	      
	      if (loginBean.getCompanyId() != null && loginBean.getCompanyId().length() > 0) {
		        criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + loginBean.getCompanyId());
		      }
	      
	      DbManager dbManager = new DbManager(getClient(),getLocale());
	      UserOpsEntityHubIgOvBeanFactory factory = new UserOpsEntityHubIgOvBeanFactory(dbManager);
	      return factory.selectObjectWithUserIg(criteria);
	      } else {
	      return null;
	    }    
	  }


  public Collection getSearchResult(PersonnelBean personnelBean,TransfersInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new TransferRequestReportViewBean());
		
		SearchCriteria searchCriteria = new SearchCriteria();
	
		if(!StringHandler.isBlankString(inputBean.getSourceOpsEntityId()) && ! "all".equals(inputBean.getSourceOpsEntityId())) 
			searchCriteria.addCriterion("sourceOpsEntityId", SearchCriterion.EQUALS, inputBean.getSourceOpsEntityId());
		if(!StringHandler.isBlankString(inputBean.getSourceHub()) && ! "all".equals(inputBean.getSourceHub())) 
			searchCriteria.addCriterion("sourceHub", SearchCriterion.EQUALS, inputBean.getSourceHub());
	    if(!StringHandler.isBlankString(inputBean.getSourceInventoryGroup()) && ! "all".equals(inputBean.getSourceInventoryGroup())) 
	        searchCriteria.addCriterion("sourceInventoryGroup", SearchCriterion.EQUALS, inputBean.getSourceInventoryGroup());
	    else {
	    	if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 && "my".equals(inputBean.getSourceEntities())) {
	    		String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
	    		invQuery +=  " and company_id = 	'" + personnelBean.getCompanyId() +"' ";
	    		searchCriteria.addCriterion("sourceInventoryGroup", SearchCriterion.IN, invQuery);
	    	}
	    }
	    
	    
		if(!StringHandler.isBlankString(inputBean.getDestinationOpsEntityId()) && ! "all".equals(inputBean.getDestinationOpsEntityId())) 
			searchCriteria.addCriterion("destinationOpsEntityId", SearchCriterion.EQUALS, inputBean.getDestinationOpsEntityId());
		if(!StringHandler.isBlankString(inputBean.getDestinationHub()) && ! "all".equals(inputBean.getDestinationHub())) 
			searchCriteria.addCriterion("destinationHub", SearchCriterion.EQUALS, inputBean.getDestinationHub());
	    if(!StringHandler.isBlankString(inputBean.getDestinationInventoryGroup()) && ! "all".equals(inputBean.getDestinationInventoryGroup())) 
	        searchCriteria.addCriterion("destinationInventoryGroup", SearchCriterion.EQUALS, inputBean.getDestinationInventoryGroup());
	    else {
	    	if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 && "my".equals(inputBean.getDestinationEntities())) {
	    		String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
	    		invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
	    		searchCriteria.addCriterion("destinationInventoryGroup", SearchCriterion.IN, invQuery);
	    	}
	    }
	    
	    
	    if ( inputBean.getSearchArgument() != null && !inputBean.getSearchArgument().equals("") ) {
	    	String mode = inputBean.getSearchMode();
	    	String field = inputBean.getSearchField();
	    	
	    	if( mode.equals("is") )
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.EQUALS,
	    				inputBean.getSearchArgument());
	    	if( mode.equals("contains"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.LIKE,
	    				inputBean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
	    	if( mode.equals("startsWith"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.STARTS_WITH,
	    				inputBean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
	    	if( mode.equals("endsWith"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.ENDS_WITH,
	    				inputBean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
	    }
	    
	    if(inputBean.getToDate() != null) {
	    	searchCriteria.addCriterion("requestDate",
	                              SearchCriterion.TO_DATE,
	                              inputBean.getToDate());
	    }
	    if(inputBean.getFromDate() != null) {
	    	searchCriteria.addCriterion("requestDate",
	                              SearchCriterion.FROM_DATE,
	                              inputBean.getFromDate());
	    }
	    if(inputBean.getStatus() != null && !inputBean.getStatus().equals("")) {
	    
	    	if(inputBean.getStatus().equalsIgnoreCase("Open"))
	    	{
	    		String [] args = {"Open","Hold"};
	    	 	searchCriteria.addCriterionArray("status",  SearchCriterion.IN, args);
	    	}
	    	else
		    	searchCriteria.addCriterion("status",
		                              SearchCriterion.EQUALS,
		                              inputBean.getStatus(),SearchCriterion.IGNORE_CASE);
	    }
	      
	return factory.select(searchCriteria, null,"TRANSFER_REQUEST_REPORT_VIEW");
 }


public ExcelHandler getExcelReport(Collection transfersColl) throws
  NoDataException, BaseException, Exception {
	Collection<TransferRequestReportViewBean> data = transfersColl; 
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
	//write column headers
	pw.addRow();
	/*Pass the header keys for the Excel.*/
	pw.addThRegion("label.source",2,1);
	pw.addThRegion("label.destination",2,1);
	pw.addThRegion("label.item",2,1);
	pw.addThRegion("label.desc",2,1);
	pw.addThRegion("label.transferrequestid",2,1);
	pw.addThRegion("label.customer",2,1);
	pw.addThRegion("label.customerpartnumber",2,1);
	pw.addThRegion("label.qtyneeded",2,1);
	pw.addThRegion("label.qtyreceived",2,1);
	pw.addThRegion("label.qtyintransit",2,1);
	pw.addThRegion("label.carrier",2,1);
	pw.addThRegion("label.trackingnumber",2,1);
	
	/*
	pw.addCellKeyBold("label.operatingentity");
	pw.addCellKeyBold("label.hub");
	pw.addCellKeyBold("label.inventorygroup");
	pw.addCellKeyBold("label.operatingentity");
	pw.addCellKeyBold("label.hub");
	pw.addCellKeyBold("label.inventorygroup");
	*/
	String[] headerkeys = {
	"label.inventorygroup","label.inventorygroup",
	"label.item","label.desc","label.transferrequestid","label.customer","label.customerpartnumber","label.qtyneeded", "label.qtyreceived", "label.qtyintransit", "label.carrier", "label.trackingnumber"};
	/*This array defines the type of the excel column.
	0 means default depending on the data type. */
	int[] types = {
				0,0,
				0,0,0,0,0,0,0,0,0,0};
	
	int[] widths = {
				15,20,
				0,20,0,15,15,0,0,0,0,0};
	
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	
	pw.applyColumnHeader(headerkeys, types, widths, null );
	
	// now write data
	//int i = 1;
	for (TransferRequestReportViewBean member : data) {
	
	  pw.addRow();
	
	 // pw.addCell(member.getSourceOperatingEntityName());
	  //pw.addCell(member.getSourceHub());
	   // pw.addCell(member.getDestOperatingEntityName());
	 // pw.addCell(member.getDestinationHub());
	  pw.addCell(member.getSourceInvGroupName());
	  pw.addCell(member.getDestinationInvGroupName());
	  pw.addCell(member.getItemId());
	  pw.addCell(member.getItemDesc());
	  pw.addCell(member.getTransferRequestId());
	  pw.addCell(member.getCustomerName());
	  pw.addCell(member.getDistCustomerPartList());
	  pw.addCell(member.getQuantityNeeded());
	  pw.addCell(member.getQuantityReceived());
	  pw.addCell(member.getQuantityInTransit());
	  pw.addCell(member.getCarrierName());
	  pw.addCell(member.getTrackingNumber()); 
	}
	return pw;
}

	public Collection releaseTransferRequest(Collection<TransferRequestReportViewBean> beans, int personnelId) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		for (TransferRequestReportViewBean inputBean : beans) {
			//if (inputBean.isOkDoUpdate()) 
			{ // Check the OK box was/was not checked value in the bean

				/*
				 * Check to see if the user has the permissions to do the
				 * update, for the inventory group of each line.
				 */

					// convert the value of a hchstatus column
					try {
						inArgs = new Vector(4);
						inArgs.add(inputBean.getCompanyId());
						inArgs.add(inputBean.getTransferRequestId().toPlainString());
						inArgs.add(personnelId);
						inArgs.add("Y");
						
						//inArgs.add(inputBean.getRadianPo());
						outArgs = new Vector(1);
						outArgs.add(new Integer(java.sql.Types.VARCHAR));
						log.debug("Release Transfer Request arguments: " + inArgs);

						// uncomment and modify the following codes to call a specific procedure
						Vector error = (Vector) factory.doProcedure("PKG_INVENTORY_TRANSFER.P_RELEASE_QUALITY_HOLD", inArgs, outArgs);

				      if(error.size()>0 && error.get(0) != null)
				      {
				     	 String errorCode = (String) error.get(0);
				     	 log.info("Error in Procedure P_RELEASE_IT_REQUEST: "+inputBean.getTransferRequestId() +" Error Code "+errorCode+" ");
				     	 errorMessages.add(errorCode);
				      }  
					}
					catch (Exception e) {
						errorMsg = "Error releaseTransferRequest: " + inArgs;
						errorMessages.add(errorMsg);
					}
			}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	
	public List closeTransfer(Collection<TransferRequestReportViewBean> beans, PersonnelBean personnelBean) throws BaseException {
		List<String> errorMessages = new ArrayList();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		for (TransferRequestReportViewBean inputBean : beans) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("OrderMaintenance", inputBean.getSourceInventoryGroup(), null, null)) {
				errorMsg = "No Permission: " + inputBean.getTransferRequestId();
				errorMessages.add(errorMsg);
				continue;
			}

			try {
				inArgs = new Vector(1);
				inArgs.add(inputBean.getTransferRequestId());
						
				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				Vector error = (Vector) factory.doProcedure("pkg_inventory_transfer.p_close_inventory_transfer_req", inArgs, outArgs);

				if(error.size()>0 && error.get(0) != null)
				{
				     String errorCode = (String) error.get(0);
				     errorMessages.add(errorCode);
				}  
			}
			catch (Exception e) {
				errorMsg = "Error closeTransfer: " + inArgs;
				errorMessages.add(errorMsg);
			}
		}
		if(log.isDebugEnabled()) 
	    	 log.info("Error in closing transfers: "+errorMessages);
		
		return (errorMessages.size() > 0 ? errorMessages : null);
	}

}
