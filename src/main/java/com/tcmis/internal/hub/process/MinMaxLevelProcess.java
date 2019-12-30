package com.tcmis.internal.hub.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.client.common.beans.CabinetManagementBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class MinMaxLevelProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

  public MinMaxLevelProcess(String client) {
    super(client);
  }

  public MinMaxLevelProcess(String client,String locale) {
	    super(client,locale);
  }

  	public Collection getSearchData(PersonnelBean personnelBean,MinMaxLevelInputBean bean) throws
	 BaseException, Exception {

	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 CurrentMinMaxLevelViewBeanFactory factory = new
		CurrentMinMaxLevelViewBeanFactory(dbManager);
	 SearchCriteria criteria = new SearchCriteria();
	 
/*	    if(!StringHandler.isBlankString(bean.getOpsEntityId()))
	        criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId()); */
	    if(!StringHandler.isBlankString(bean.getHub()))
	        criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
	    if(!StringHandler.isBlankString(bean.getInventoryGroup())) {
	        criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
	    }
	    else {
	        String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
	        if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
	            invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
	        criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
	    }

	 if (!StringHandler.isBlankString(bean.getCriteria())) {
		if ("itemId".equalsIgnoreCase(bean.getCriterion())) {
		 criteria.addCriterion("itemId", SearchCriterion.EQUALS,
			bean.getCriteria());
		}
		else if ("partNumber".equalsIgnoreCase(bean.getCriterion())) {
		 criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,
			bean.getCriteria(), SearchCriterion.IGNORE_CASE);
		}
	 }

	 SortCriteria sortCriteria = new SortCriteria();
	 sortCriteria.addCriterion("inventoryGroup");
     sortCriteria.addCriterion("catPartNo");
	 return factory.selectWithChildren(criteria, sortCriteria);
  }

  public Collection getHistoryData(PersonnelBean personnelBean, MinMaxLevelInputBean bean) throws
      BaseException, Exception {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    MinMaxLevelLogViewBeanFactory factory = new
        MinMaxLevelLogViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    //criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getBranchPlant());
/*    if(!StringHandler.isBlankString(bean.getOpsEntityId()))
    criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId()); */
	if(!StringHandler.isBlankString(bean.getHub()))
	    criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
	if(!StringHandler.isBlankString(bean.getInventoryGroup())) {
	    criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
	}
	else {
	    String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
	    if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
	        invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
	    criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
	}

    if(!StringHandler.isBlankString(bean.getCatalogId())) {
        criteria.addCriterion("catalogId", SearchCriterion.EQUALS,
                              bean.getCatalogId());
     }
    if(!StringHandler.isBlankString(bean.getInventoryGroup())) {
       criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
                             bean.getInventoryGroup());
    }
    if(!StringHandler.isBlankString(bean.getItemId())) {
       criteria.addCriterion("itemId", SearchCriterion.EQUALS,
                               bean.getItemId());
    }
    if(!StringHandler.isBlankString(bean.getCatPartNo())) {
       criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,
                               bean.getCatPartNo());
    }
/*
    if(!StringHandler.isBlankString(bean.getCriteria())) {
      if ("itemId".equalsIgnoreCase(bean.getCriterion())) {
        criteria.addCriterion("itemId", SearchCriterion.EQUALS,
                              bean.getCriteria());
      }
      else if ("partNumber".equalsIgnoreCase(bean.getCriterion())) {
        criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,
                              bean.getCriteria());
      }
    }
*/
    SortCriteria sortCriteria = new SortCriteria("dateModified");
    sortCriteria.setSortAscending(false);
    return factory.select(criteria, sortCriteria);
  }

  public Collection update(Collection minMaxLevelInputBeanCollection,PersonnelBean personnelBean)  throws BaseException, Exception {
	  Collection errorCollection = new Vector(0);
	  String errorMsg = null;
	  PermissionBean perbean = personnelBean.getPermissionBean();
	  for( MinMaxLevelInputBean bean:(Collection<MinMaxLevelInputBean>) minMaxLevelInputBeanCollection)
		  if("true".equals(bean.getOkDoUpdate())) {
			  if( !perbean.hasInventoryGroupPermission("MinMaxChg", bean.getInventoryGroup(), null, null) )
				  continue;
			  try {
				  errorMsg = updateCurrentValue(bean,personnelBean);
				  if( errorMsg != null ) {
					  errorMsg += "\n"+library.getString("label.errorchangingminmaxlevelsfor")+" "+ bean.getInventoryGroup() + "," + bean.getCatalogId() + "," + bean.getCatPartNo();
					  errorCollection.add(errorMsg);
				  }
			  }
			  catch(Exception e) {
				  errorMsg = ""+library.getString("label.errorchangingminmaxlevelsfor")+" "+ bean.getInventoryGroup() + "," + bean.getCatalogId() + "," + bean.getCatPartNo(); 
				  log.error(errorMsg , e);
				  MailProcess.sendEmail("deverror@tcmis.com", "", MailProcess.DEFAULT_FROM_EMAIL, "Min Max Level Change Error", e.getMessage());
				  errorCollection.add(errorMsg);
			  }
		  }
	  return errorCollection;
  }

  private boolean isRowChanged(MinMaxLevelInputBean bean)  throws
      BaseException, Exception {
    boolean flag = this.isLookAheadDaysChanged(bean);

    if(!(bean.getOldReorderPoint() == null && bean.getReorderPoint() == null)) {
      if ( (bean.getOldReorderPoint() == null && bean.getReorderPoint() != null) ||
          (bean.getOldReorderPoint() != null && bean.getReorderPoint() == null) ||
          (bean.getOldReorderPoint().compareTo(bean.getReorderPoint()) != 0)) {
        flag = true;
      }
    }

    if(!(bean.getOldReorderQuantity() == null && bean.getReorderQuantity() == null)) {
      if ( (bean.getOldReorderQuantity() == null && bean.getReorderQuantity() != null) ||
          (bean.getOldReorderQuantity() != null && bean.getReorderQuantity() == null) ||
          (bean.getOldReorderQuantity().compareTo(bean.getReorderQuantity()) != 0)) {
        flag = true;
      }
    }

    if(!(bean.getOldStockingLevel() == null && bean.getStockingLevel() == null)) {
      if ( (bean.getOldStockingLevel() == null && bean.getStockingLevel() != null) ||
          (bean.getOldStockingLevel() != null && bean.getStockingLevel() == null) ||
          (bean.getOldStockingLevel().compareTo(bean.getStockingLevel()) != 0)) {
        flag = true;
      }
    }

    if(!(bean.getOldStockingMethod() == null && bean.getStockingMethod() == null)) {
      if ( (bean.getOldStockingMethod() == null && bean.getStockingMethod() != null) ||
          (bean.getOldStockingMethod() != null && bean.getStockingMethod() == null) ||
          (!bean.getOldStockingMethod().equalsIgnoreCase(bean.getStockingMethod()))) {
        flag = true;
      }
    }
    
    flag = isLevelHoldEndDateChanged(bean);
    
    flag = isProjectedLeadTimeChanged(bean);
    
    if(log.isDebugEnabled()) {
      log.debug("This bean is:" + flag);
      log.debug(bean);
    }
    return flag;
  }

  private boolean isLevelHoldEndDateChanged(MinMaxLevelInputBean bean)  throws
	BaseException, Exception {
	boolean flag = false;
	if(!(bean.getOldLevelHoldEndDate() == null && bean.getLevelHoldEndDate() == null)) {
	  if ( (bean.getOldLevelHoldEndDate() == null && bean.getLevelHoldEndDate() != null) ||
	      (bean.getOldLevelHoldEndDate() != null && bean.getLevelHoldEndDate() == null) ||
	      (bean.getOldLevelHoldEndDate().compareTo(bean.getLevelHoldEndDate()) != 0)) {
	    flag = true;
	  }
	}
	return flag;	
  }

private boolean isLookAheadDaysChanged(MinMaxLevelInputBean bean)  throws
      BaseException, Exception {
    boolean flag = false;
    if(!(bean.getOldLookAheadDays() == null && bean.getLookAheadDays() == null)) {
      if ( (bean.getOldLookAheadDays() == null && bean.getLookAheadDays() != null) ||
          (bean.getOldLookAheadDays() != null && bean.getLookAheadDays() == null) ||
          (bean.getOldLookAheadDays().compareTo(bean.getLookAheadDays()) != 0)) {
        flag = true;
      }
    }
    return flag;
  }
  
  
  private boolean isProjectedLeadTimeChanged(MinMaxLevelInputBean bean)  throws
  		BaseException, Exception {
	boolean flag = false;
	if(!(bean.getOldProjectedLeadTime() == null && bean.getProjectedLeadTime() == null)) {
	  if ( (bean.getOldProjectedLeadTime() == null && bean.getProjectedLeadTime() != null) ||
	      (bean.getOldProjectedLeadTime() != null && bean.getProjectedLeadTime() == null) ||
	      (bean.getOldProjectedLeadTime().compareTo(bean.getProjectedLeadTime()) != 0)) {
	    flag = true;
	  }
	}
	return flag;
}


  private String updateCurrentValue(MinMaxLevelInputBean bean,PersonnelBean personnelBean)  throws
      BaseException, Exception {
    Collection inArgs = new Vector(15);
    if( !"MM".equals(bean.getStockingMethod() ) ) {
    	bean.setReorderPoint(null);
    	bean.setStockingLevel(null);
    	bean.setReorderQuantity(null);
    }
    if(bean.getInventoryGroup() != null) {
      inArgs.add(bean.getInventoryGroup());
    }
    else {
      inArgs.add("");
    }
    if(bean.getCompanyId() != null) {
      inArgs.add(bean.getCompanyId());
    }
    else {
      inArgs.add("");
    }
    if(bean.getCatalogId() != null) {
      inArgs.add(bean.getCatalogId());
    }
    else {
      inArgs.add("");
    }
    if(bean.getCatPartNo() != null) {
      inArgs.add(bean.getCatPartNo());
    }
    else {
      inArgs.add("");
    }
    if(bean.getPartGroupNo() != null) {
      inArgs.add(bean.getPartGroupNo());
    }
    else {
      inArgs.add("");
    }
    if(bean.getOldStockingMethod() != null) {
      inArgs.add(bean.getOldStockingMethod());
    }
    else {
      inArgs.add("");
    }
    if(bean.getStockingMethod() != null) {
      inArgs.add(bean.getStockingMethod());
    }
    else {
      inArgs.add("");
    }
    if(bean.getReorderPoint() != null) {
      inArgs.add(bean.getReorderPoint());
    }
    else {
      inArgs.add("");
    }
    if(bean.getStockingLevel() != null) {
      inArgs.add(bean.getStockingLevel());
    }
    else {
      inArgs.add("");
    }
    if(bean.getLookAheadDays() != null) {
      inArgs.add(bean.getLookAheadDays());
    }
    else {
      inArgs.add("");
    }
    if(bean.getReorderQuantity() != null && !bean.getReorderQuantity().equals(new BigDecimal(0))) {
      inArgs.add(bean.getReorderQuantity());
    }
    else {
      inArgs.add("");
    }
    if(bean.getReceiptProcessingMethod() != null) {
      inArgs.add(bean.getReceiptProcessingMethod());
    }
    else {
      inArgs.add("");
    }
    if(bean.getBillingMethod() != null) {
      inArgs.add(bean.getBillingMethod());
    }
    else {
      inArgs.add("");
    }
        
    inArgs.add(""+personnelBean.getPersonnelId());
    
    if(bean.getRemarks() != null) {
      inArgs.add(bean.getRemarks());
    }
    else {
      inArgs.add("");
    }
    if(bean.getDropShipOverride()!=null && bean.getDropShipOverride().contains("true")) {
        inArgs.add("Y");
      }
      else {
        inArgs.add("N");
      }

    Collection outArgs = new Vector(1);
    outArgs.add(new Integer(java.sql.Types.VARCHAR));

    Collection inArgs2 = new Vector(1);
    inArgs2.add(bean.getCatalogCompanyId());
    
    //Add the two new columns
    if(bean.getLevelHoldEndDate() != null) {
    	inArgs2.add(bean.getLevelHoldEndDate());
      }
    else {
    	inArgs2.add("");
    }
    
    if(bean.getProjectedLeadTime() != null) {
    	inArgs2.add(bean.getProjectedLeadTime());
      }
    else {
    	inArgs2.add("");
    }
    
    DbManager dbManager = new DbManager(getClient(),getLocale());
    GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
    //log.debug("PKG_LEVEL_MANAGEMENT.PR_UPDATE_LEVELS_FOR_EXITMM"+ inArgs+ outArgs+inArgs2);
    Collection resultCollection = procFactory.doProcedure("PKG_LEVEL_MANAGEMENT.PR_UPDATE_LEVELS_FOR_EXITMM", inArgs, outArgs,inArgs2);
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
      return ("There was an error calling PKG_LEVEL_MANAGEMENT.PR_UPDATE_LEVELS_FOR_EXITMM:" + result);
    }
    if(this.isLookAheadDaysChanged(bean)) {
      try {
        inArgs = new Vector(5);
        if (bean.getCompanyId() != null) {
          inArgs.add(bean.getCompanyId());
        }
        else {
          inArgs.add("");
        }
        if (bean.getCatalogId() != null) {
          inArgs.add(bean.getCatalogId());
        }
        else {
          inArgs.add("");
        }
        if (bean.getCatPartNo() != null) {
          inArgs.add(bean.getCatPartNo());
        }
        else {
          inArgs.add("");
        }
        if (bean.getInventoryGroup() != null) {
          inArgs.add(bean.getInventoryGroup());
        }
        else {
          inArgs.add("");
        }
        if (bean.getPartGroupNo() != null) {
          inArgs.add(bean.getPartGroupNo());
        }
        else {
          inArgs.add("");
        }
        procFactory.doProcedure("p_cpi_rli_allocate", inArgs);
      }
      catch(Exception e) {
        //I'm just eating this error as this procedure is called hourly from a cron anyway
        log.error("Error calling p_cpi_rli_allocate", e);
        MailProcess.sendEmail("deverror@tcmis.com", "", MailProcess.DEFAULT_FROM_EMAIL, "Error calling p_cpi_rli_allocate", e.getMessage());
      }
    }
    return null;
  }
  
	public ExcelHandler createExcelFile(Collection bean, Locale locale) throws Exception {
		
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<CurrentMinMaxLevelViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
    //write column headers
		pw.addRow();
    /*Pass the header keys for the Excel.*/
    String[] headerkeys = {
      "label.inventorygroup","label.company","label.catalog", "label.partnumber","label.partgroupnumber","label.specification",
      "label.itemid","label.currentstockingmethod","label.stockingmethod", "label.reorderpoint", "label.stockinglevel",
      "label.reorderquantity","label.lookaheaddays", "label.levelholdenddate", "label.projectedleadtimeindays", 
      "label.receiptprocessingmethod", "label.billingmethod", "label.changecomments"};    
    /*This array defines the type of the excel column.
    0 means default depending on the data type. */
    int[] types = {
      0,0,0,0,0,0,
      0,0,0,0,0,
      0,0,0,0,0,0,0};
    /*This array defines the default width of the column when the Excel file opens.
    0 means the width will be default depending on the data type.*/
    int[] widths = {
      12,15,18,12,0,12,
      0,0,0,0,0,
      0,0,0,0,16,20,15};
    /*This array defines the horizontal alignment of the data in a cell.
    0 means excel defaults the horizontal alignemnt by the data type.*/
    int[] horizAligns = {
      0,0,0,0,0,0,
      0,0,0,0,0,
      0,0,0,0,0,0,0};
    
    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
    
		// now write data
	  int i = 1;
		for (CurrentMinMaxLevelViewBean member : data) {
      i++;
      pw.addRow();

      pw.addCell(member.getInventoryGroupName());
      pw.addCell(member.getCompanyName());
      pw.addCell(member.getCatalogDesc());
      pw.addCell(member.getCatPartNo());
      pw.addCell(member.getPartGroupNo());
      pw.addCell(member.getSpecList());
      pw.addCell(member.getItemId());
      pw.addCell(member.getCurrentStockingMethod());
      pw.addCell(member.getStockingMethod());
      pw.addCell(member.getReorderPoint());
      pw.addCell(member.getStockingLevel());
      pw.addCell(member.getReorderQuantity());
	  pw.addCell(member.getLookAheadDays());
	  pw.addCell(member.getLevelHoldEndDate());
	  pw.addCell(member.getProjectedLeadTime());
	  pw.addCell(member.getReceiptProcessingMethod());	 
	  pw.addCell(member.getBillingMethod());
	  pw.addCell("");
	  }
		return pw;
	}

	public Collection getAuthorizedUsersForUsergroup(PersonnelBean personnelBean, String userGroupId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new MinMaxLevelInputBean());
		StringBuilder query = new StringBuilder("SELECT DISTINCT OPS_ENTITY_ID FROM USER_GROUP_MEMBER_OPS_ENTITY WHERE PERSONNEL_ID = ").append(personnelBean.getPersonnelId());
		query.append(" AND USER_GROUP_ID = '").append(userGroupId).append("'");
		query.append(" AND COMPANY_ID = '").append(personnelBean.getCompanyId()).append("'");		
        return genericSqlFactory.selectQuery(query.toString());        
	}

	public ExcelHandler createTemplateData(PersonnelBean personnelBean, MinMaxLevelInputBean inputBean, Locale locale) throws Exception {
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        ExcelHandler pw = new ExcelHandler(library);
        pw.addTable();
        pw.addRow();

        String[] headerkeys = {
            "excel.company_id","excel.catalog_id","excel.inventory_group","excel.fac_part_no",
            "excel.final_reorder_pt","excel.reorder_quantity", "excel.final_stocking_level",
            "excel.exit_mm","excel.hub_manager_comments","excel.catalog_company_id", "excel.level_hold_end_date", "excel.projected_lead_time"
        };
        /*This array defines the type of the excel column.
        0 means default depending on the data type. */
        int[] types = {0,0,0,0,0,0,0,0,0,0,0,0};
        /*This array defines the default width of the column when the Excel file opens.
        0 means the width will be default depending on the data type.*/
        int[] widths = {0,0,0,0,0,0,0,0,0,0,0,0};
        /*This array defines the horizontal alignment of the data in a cell.
        0 means excel defaults the horizontal alignemnt by the data type.*/
        int[] horizAligns = {0,0,0,0,0,0,0,0,0,0,0,0};

        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

        // now write data
        DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CurrentMinMaxLevelViewBean());
        StringBuilder query = new StringBuilder("select company_id,catalog_id,inventory_group,cat_part_no,reorder_point,reorder_quantity,stocking_level,");
        query.append("catalog_company_id,level_hold_end_date,projected_lead_time");
        query.append(" from catalog_part_inventory where");
        if (StringHandler.isBlankString(inputBean.getInventoryGroup())) {
            query.append(" INVENTORY_GROUP IN");
            query.append(" (select uig.inventory_group from customer.user_inventory_group uig, tcm_ops.inventory_group_definition igd");
            query.append(" where uig.PERSONNEL_ID = ").append(personnelBean.getPersonnelId()).append(" and uig.COMPANY_ID = 'Radian'");
            query.append(" and uig.inventory_group = igd.inventory_group and igd.hub = ").append(inputBean.getHub()).append(")");
        }else {
            query.append(" inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
        }
        query.append(" and stocking_method = 'MM' order by INVENTORY_GROUP,CAT_PART_NO asc");
        Iterator iter = genericSqlFactory.selectQuery(query.toString()).iterator();

        while(iter.hasNext()) {
            CurrentMinMaxLevelViewBean bean = (CurrentMinMaxLevelViewBean)iter.next();
            pw.addRow();
            pw.addCell(bean.getCompanyId());
            pw.addCell(bean.getCatalogId());
            pw.addCell(bean.getInventoryGroup());
            pw.addCell(bean.getCatPartNo());
            pw.addCell(bean.getReorderPoint());
            pw.addCell(bean.getReorderQuantity());
            pw.addCell(bean.getStockingLevel());
            pw.addCell("FALSE");
            pw.addCell("");
            pw.addCell(bean.getCatalogCompanyId());
            pw.addCell(bean.getLevelHoldEndDate());
            pw.addCell(bean.getProjectedLeadTime());
        }
        return pw;
    }
}