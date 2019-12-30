package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.InventoryAdjustmentsInputBean;
import com.tcmis.internal.hub.beans.PendingInventoryAdjustmentBean;

/**
 * ******************************************************************
 * Process for the InventoryAdjustmentsProcess Section
 * 
 * @version 1.0
 * *****************************************************************         
 */

public class InventoryAdjustmentsProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private DbManager dbManager;
	private Connection connection = null;
	private GenericSqlFactory genericSqlFactory;

	public InventoryAdjustmentsProcess(String client, String locale) {
		super(client, locale);
	}

	public InventoryAdjustmentsProcess(String client) {
		super(client);
	}

/*
TODO Future development - 
1. Ability for reviewer to change the quantity that they approve.
2. Deal with receiving error when they receive more than actually on dock.
*/

    public Collection<PendingInventoryAdjustmentBean> getSearchResult(	InventoryAdjustmentsInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());		

		GenericSqlFactory inventoryAdjustmentsFactory = new GenericSqlFactory(dbManager,new PendingInventoryAdjustmentBean());  
		
		SearchCriteria searchCriteria = new SearchCriteria();
		SortCriteria sortCcriteria = new SortCriteria();
		
        if(!StringHandler.isBlankString(inputBean.getOpsEntityId()))
            searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());       
      
        if(!StringHandler.isBlankString(inputBean.getHub()))
            searchCriteria.addCriterion("branchPlant", SearchCriterion.EQUALS, inputBean.getHub());
        if(!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
            searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
        }
        else {
            String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
            if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
                invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
            searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
        }
				
        searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument() );
        
		sortCcriteria.setSortAscending(true);		
		
		sortCcriteria.addCriterion("opsEntityId,inventoryGroup,itemId");	
		
		Collection<PendingInventoryAdjustmentBean> c = inventoryAdjustmentsFactory.select(searchCriteria, sortCcriteria, TABLE_NAME);
		
		return c;
		
	}
    
    
    public Collection<PendingInventoryAdjustmentBean> getSumResult(	InventoryAdjustmentsInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
    	dbManager = new DbManager(getClient(),this.getLocale());
		connection = dbManager.getConnection();
		GenericSqlFactory inventoryAdjustmentsFactory = new GenericSqlFactory(dbManager,new PendingInventoryAdjustmentBean());  
		
				
		SearchCriteria searchCriteria = new SearchCriteria();
				
        if(!StringHandler.isBlankString(inputBean.getOpsEntityId()))
            searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());       
      
        if(!StringHandler.isBlankString(inputBean.getHub()))
            searchCriteria.addCriterion("branchPlant", SearchCriterion.EQUALS, inputBean.getHub());
        if(!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
            searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
        }
        else {
            String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
            if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
                invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
            searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
        }
				
        searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument() );
        
				
		String query = "select inventory_group_name, sum(extended_price) extended_price, home_currency_id from PENDING_INVENTORY_ADJUSTMENT"+ " " +inventoryAdjustmentsFactory.getWhereClause(searchCriteria)+
		               " group by INVENTORY_GROUP_NAME ,home_currency_id  order by INVENTORY_GROUP_NAME";
		
		Collection<PendingInventoryAdjustmentBean> c = inventoryAdjustmentsFactory.selectQuery(query, connection);
		
		return c;
		
	}


	public ExcelHandler getExcelReport(InventoryAdjustmentsInputBean bean, PersonnelBean personnelBean, Locale locale)	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(	"com.tcmis.common.resources.CommonResources", locale);
		
		Collection<PendingInventoryAdjustmentBean> data = getSearchResult(bean,personnelBean);

		ExcelHandler pw = new ExcelHandler(library);
		
		pw.addTable();
		// write column headers
		pw.addRow();
		
		String[] headerkeys = 
				{ "label.operatingentity","label.hub","label.inventorygroup","label.item",
				"label.shortDesc","label.pkg","label.receiptid","label.lotstatus","label.qty",
				"label.cost","label.extcost","label.requestor",
				"label.requestdate","label.requestorcomments"
		          };
		int[] widths = {12,12,12,12,30,12,12,12,12,12,12,12,12,36  };
		int[] types =
		        { 
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,
				pw.TYPE_NUMBER,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_NUMBER,
				pw.TYPE_NUMBER,pw.TYPE_NUMBER,pw.TYPE_NUMBER,
				pw.TYPE_STRING,pw.TYPE_CALENDAR,pw.TYPE_STRING
				};	
		
		int[] aligns = {0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);	
		
		for (PendingInventoryAdjustmentBean member : data) {
			pw.addRow();			
			pw.addCell(member.getOperatingEntityName());			
			pw.addCell(member.getHubName());
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getLotStatus());
			pw.addCell(member.getQuantity().multiply(new BigDecimal(-1)));
			pw.addCell(member.getFullUnitPrice().multiply(new BigDecimal(-1)));
			pw.addCell(member.getExtendedPrice().multiply(new BigDecimal(-1)));
			pw.addCell(member.getRequestor());
			pw.addCell(member.getRequestDate());
			pw.addCell(member.getRequestorComment());
			
		}
		return pw;

	}
	
	
	
	public Collection update(Collection <PendingInventoryAdjustmentBean> pendingInventoryBeanCollection,PersonnelBean personnelBean) throws
	BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		//Collection resultCollection = null;
		Vector errorMessages = new Vector();
        String statusOrSapCode = null;
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		if((pendingInventoryBeanCollection!=null) && (!pendingInventoryBeanCollection.isEmpty()))
		{
			String preInventoryGroup = "";
			String preItemId = "";
			String doNextOk = "NO";
			for(PendingInventoryAdjustmentBean pendingInvBean : pendingInventoryBeanCollection) {
				/*
				 * Check to see if the user has the permissions to do the
				 * update.
				 */
				if (!personnelBean.getPermissionBean().hasOpsEntityPermission("inventoryAdjustment",pendingInvBean.getOpsEntityId(),null))
					continue;	
				try {
					if(!preInventoryGroup.equals(pendingInvBean.getInventoryGroup()) || !preItemId.equals(pendingInvBean.getItemId().toString())) {
					
						preInventoryGroup = pendingInvBean.getInventoryGroup();
						preItemId = pendingInvBean.getItemId().toString();
						inArgs = new Vector(4);
						inArgs.add(personnelId);
						inArgs.add(pendingInvBean.getTotalPrice());
						inArgs.add(pendingInvBean.getOpsEntityId());
						inArgs.add(pendingInvBean.getCompanyId());
                        inArgs.add(pendingInvBean.getItemId());
                        inArgs.add(pendingInvBean.getHomeCurrencyId());
                        inArgs.add(pendingInvBean.getInventoryGroup());

                        outArgs = new Vector(1);
						outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		
						Vector results = (Vector) factory.doProcedure("Pkg_inventory_adjustment.p_process_inventory_adj", inArgs,outArgs);
						doNextOk = (String) results.get(0);
						
						if("NO".equals(doNextOk)) 
							errorMessages.add(library.getString("label.insufficientapprovallimit"));
						
					}
				
					
					if("OK".equals(doNextOk) && !StringHandler.isBlankString(pendingInvBean.getApprove()) && (null!=pendingInvBean.getRequestId()) ) 
					{
					  inArgs = new Vector(2);
												
					  inArgs.add(pendingInvBean.getRequestId());
                      inArgs.add("approved");
                      inArgs.add(personnelId);
                      inArgs.add(pendingInvBean.getReviewerComment());
                      outArgs = new Vector(1);
   					  outArgs.add( new Integer(java.sql.Types.VARCHAR) );
					  Vector error = (Vector) factory.doProcedure("PKG_INVENTORY_ADJUSTMENT.p_approve_inventory_adjustment", inArgs,outArgs);
                      if (error.get(0) != null && !((String) error.get(0)).equalsIgnoreCase(""))
	               	  {
				        errorMessages.add(error.get(0));
			          }
                        if(log.isDebugEnabled()) {
							log.debug("Call to Pending Inventory Adjustments Proc For Updae:" + inArgs);
						}
						if(error.size()>0 && error.get(0) != null)
						{
							errorCode = (String) error.get(0);
							log.info("Error in Procedure For update  of Pending Inventory Adjustments: "+pendingInvBean.getRequestId()+" Error Code "+errorCode+" ");
							errorMessages.add(errorCode);
						}			     
					}			
				} catch (Exception e) {
					errorMsg = "Error updating Pending Inventory Info !";
					errorMessages.add(errorMsg);
				}
			}
			factory = null;
			dbManager = null;
		} 

		return errorMessages;
	}
	
	
	public Collection reject (Collection <PendingInventoryAdjustmentBean> pendingInventoryBeanCollection,PersonnelBean personnelBean) throws
	BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		//Collection resultCollection = null;
		Vector errorMessages = new Vector();
        String statusOrSapCode = null;
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		if((pendingInventoryBeanCollection!=null) && (!pendingInventoryBeanCollection.isEmpty()))
		{
			for(PendingInventoryAdjustmentBean pendingInvBean : pendingInventoryBeanCollection) {
				/*
				 * Check to see if the user has the permissions to do the
				 * update.
				 */
			//	if (!personnelBean.getPermissionBean().hasOpsEntityPermission("InventoryAdjustment",pendingInvBean.getOpsEntityId(),null))
				//	continue;	
				try {
					if(!StringHandler.isBlankString(pendingInvBean.getApprove()) && (null!=pendingInvBean.getRequestId()) ) 
					{
						inArgs = new Vector(2);
												
					  inArgs.add(pendingInvBean.getRequestId());
                      inArgs.add("rejected");
                      inArgs.add(personnelId);
                      inArgs.add(pendingInvBean.getReviewerComment());
                      outArgs = new Vector(1);
   					  outArgs.add( new Integer(java.sql.Types.VARCHAR) );
					  Vector error = (Vector) factory.doProcedure("PKG_INVENTORY_ADJUSTMENT.p_approve_inventory_adjustment", inArgs,outArgs);
                      if (error.get(0) != null && !((String) error.get(0)).equalsIgnoreCase(""))
	               	  {
				        errorMessages.add(error.get(0));
			          }
                        if(log.isDebugEnabled()) {
							log.debug("Call to Pending Inventory Adjustments Proc For Updae:" + inArgs);
						}
						if(error.size()>0 && error.get(0) != null)
						{
							errorCode = (String) error.get(0);
							log.info("Error in Procedure For update  of Pending Inventory Adjustments: "+pendingInvBean.getRequestId()+" Error Code "+errorCode+" ");
							errorMessages.add(errorCode);
						}			     
					}			
				} catch (Exception e) {
					errorMsg = "Error updating Pending Inventory Info !";
					errorMessages.add(errorMsg);
				}
			}
			factory = null;
			dbManager = null;
		} 

		return errorMessages;
	}

  private static final String TABLE_NAME ="PENDING_INVENTORY_ADJUSTMENT";
}