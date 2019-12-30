package com.tcmis.internal.hub.process;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.ExcessInventoryViewBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/******************************************************************************
 * Process for ExcessInventory
 * @version 1.0
 *****************************************************************************/

public class ExcessInventoryProcess extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());
   
   public ExcessInventoryProcess(String client) {
      super(client);
   }
   
   public ExcessInventoryProcess(String client, String locale) {
      super(client,locale);
   }
   
   public Collection<ExcessInventoryViewBean> getSearchData(ExcessInventoryViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      
      GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ExcessInventoryViewBean());
      SearchCriteria criteria = new SearchCriteria();
      SortCriteria sort = new SortCriteria();
      if (inputBean.getOpsEntityId()!=null && inputBean.getOpsEntityId().length()>0)
         criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS,inputBean.getOpsEntityId());
      if (inputBean.getHub()!=null && inputBean.getHub().length()>0)
         criteria.addCriterion("hub", SearchCriterion.EQUALS,inputBean.getHub());
      if (inputBean.getInventoryGroup()!=null && inputBean.getInventoryGroup().length()>0)
      {	  
         criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,inputBean.getInventoryGroup());
      }
      
    else {

        String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
       
        if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
        invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
        
        criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);

    }
      
      sort.addCriterion("itemId");
      return factory.select(criteria,sort,"excess_inventory_view");            
   }
   
   public ExcelHandler getExcelReport(ExcessInventoryViewBean inputBean,Collection data, Locale locale) throws Exception {
      ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
      Iterator iterator = data.iterator();
      ExcelHandler pw = new ExcelHandler(library);
      pw.addTable();

//	  write column headers
      pw.addRow();
      pw.addCellKeyBold("label.operatingentity");
      pw.addCell(inputBean.getOpsEntityId());
      pw.addCellKeyBold("label.hub");
      pw.addCell(inputBean.getHub());
      pw.addCellKeyBold("label.invgrp");
      pw.addCell(inputBean.getInventoryGroup());
      pw.addRow();
      pw.addRow();
      String[] headerkeys = {
            "label.operatingentity","label.hub","label.inventorygroup","label.itemid","label.partnum",
            "label.description","label.onhand","label.onorder","label.inpurchasing",
            "label.currency","label.averagecost", "label.expiredate"};
      int[] widths = {15,10,12,9,20,
                      60,12,8, 12,
                      9, 10,10};
      int [] types = {0,0,0,0,0,
                      0,0,0,0,
                      0,0,pw.TYPE_CALENDAR};                    
      int[] aligns = {0,0,0,0,0,
                      0,0,0,0,0,0,
                      0,0,0};

      pw.applyColumnHeader(headerkeys, types, widths, aligns);
      
      pw.setColumnDigit(10, 4);

      ExcessInventoryViewBean bean = null;
      while(iterator.hasNext()) {
         bean = (ExcessInventoryViewBean) iterator.next();
         pw.addRow();
         pw.addCell(bean.getOperatingEntityName());
         pw.addCell(bean.getHubName());
         pw.addCell(bean.getInventoryGroupName());         
         pw.addCell(bean.getItemId());
         pw.addCell(bean.getPartNo());
         pw.addCell(bean.getPartShortName());
         pw.addCell(bean.getOnHand());
         pw.addCell(bean.getOnOrder());
         pw.addCell(bean.getInPurchasing());
         if(bean.getAverageCost() == null) 
				pw.addCell("");
		 else
			 pw.addCell(bean.getCurrencyId());
         pw.addCell(bean.getAverageCost());
         pw.addCell(bean.getExpireDate());         
         
      }
       return pw;
   }
}
