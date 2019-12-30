package com.tcmis.internal.hub.process;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.CompanyBeanFactory;
import com.tcmis.common.admin.factory.UserOpsEntityHubIgOvBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.DbuyContractAddChargeViewBean;
import com.tcmis.internal.hub.beans.ItemDirectSupplyInputBean;
import com.tcmis.internal.hub.beans.ItemDirectSupplyViewBean;
import com.tcmis.internal.supply.beans.DBuyConsolidationFreqInputBean;
import com.tcmis.internal.supply.beans.DBuyConsolidationFreqViewBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletOutputStream;

/******************************************************************************
 * Process for ItemManagement
 * @version 1.0
 *****************************************************************************/

public class ItemDirectedSupplyProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());
 Connection connection = null;
GenericSqlFactory genericSqlFactory;
private ResourceLibrary library = null;
 public ItemDirectedSupplyProcess(String client) {
	super(client);
 }
 
 public ItemDirectedSupplyProcess(String client,String locale) {
	    super(client,locale);
	    library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
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

public Collection getSearchData(ItemDirectSupplyInputBean inputBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemDirectSupplyViewBean());
	
	SearchCriteria searchCriteria = new SearchCriteria();
    searchCriteria.addCriterion("inventoryGroup",
			 SearchCriterion.EQUALS,
			 inputBean.getInventoryGroup());
    searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument());
    return factory.select(searchCriteria, null, "HUB_ITEM_DIRECTED_SUPPLY_view");
 }

public String delete(Collection<ItemDirectSupplyViewBean> beans, PersonnelBean personnel) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),this.getLocale());
	GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemDirectSupplyViewBean());
	PermissionBean permissionBean = personnel.getPermissionBean();
	String errMsg = "";
	for(ItemDirectSupplyViewBean b :beans)
	{
		if (!permissionBean.hasOpsEntityPermission("itemDirectSupply", b.getOpsEntityId(), null))
	    {
			errMsg = "No Permission to Delete";
		    return errMsg;
	    }
		if(b.isOkDoUpdate())
		{
			String query = "delete from HUB_ITEM_DIRECTED_SUPPLY where item_id = "+b.getItemId()+
			" and branch_plant = "+b.getBranchPlant()+" and source_hub ="+b.getSourceHub()+
			" and inventory_group ='"+b.getInventoryGroup()+"'";
			factory.deleteInsertUpdate(query);
			
	  }
	}
	return errMsg;
  } 

public void getExcelReport(ItemDirectSupplyInputBean inputBean, ServletOutputStream out, Locale locale) throws
	Exception
	{		
	Collection data = getSearchData(inputBean);
	
		  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		  ExcelHandler pw = new ExcelHandler(library);

	Iterator iterator = data.iterator();
	  pw.addSheet();
	  pw.addRow();
//	now write data
        String[] headerkeys = {"label.destinationig","label.itemid","label.itemdesc","label.packaging",
        		"label.sourceinventorygroup","label.enteredby","label.entereddate"};

        int  [] widths = {12, 10, 20, 12, 12,12,0} ;

        int [] types =   {0,0,0,0,0,0,ExcelHandler.TYPE_CALENDAR};

        int[] aligns = {0,0,0,0,0,0,0};

       pw.applyColumnHeader(headerkeys, types, widths, aligns);

    while(iterator.hasNext())
	{
    	ItemDirectSupplyViewBean bean = (ItemDirectSupplyViewBean) iterator.next();
		pw.addRow();
		pw.addCell(bean.getInventoryGroupName());
		pw.addCell(bean.getItemId());
		pw.addCell(bean.getItemDesc());
		pw.addCell(bean.getPackaging());
		pw.addCell(bean.getSourceInventoryGroupName());
		pw.addCell(bean.getEnteredBy());
		pw.addCell(bean.getEnteredDate());
		
    }
	  pw.write(out);
}


 public String addnewitem(ItemDirectSupplyViewBean beans, BigDecimal personalId ) throws
	BaseException {
	 String result = "OK";
	DbManager dbManager = new DbManager(getClient(),getLocale());
	connection = dbManager.getConnection();
	genericSqlFactory = new GenericSqlFactory(dbManager);
try {
	Collection inArgs = new Vector(6);
    if(beans.getBranchPlant() != null) {
      inArgs.add(beans.getBranchPlant());
    }
    else {
      inArgs.add("");
    }
    if(beans.getItemId() != null) {
      inArgs.add(beans.getItemId());
    }
    else {
      inArgs.add("");
    }
    if(beans.getSourceHub() != null) {
      inArgs.add(beans.getSourceHub());
    }
    else {
      inArgs.add("");
    }
    if(beans.getInventoryGroup() != null) {
        inArgs.add(beans.getInventoryGroup());
      }
      else {
        inArgs.add("");
      }
    if(beans.getSourceInventoryGroup() != null) {
        inArgs.add(beans.getSourceInventoryGroup());
      }
      else {
        inArgs.add("");
      }
    if(beans.getEnteredBy() != null) {
        inArgs.add(beans.getEnteredBy());
      }
      else {
        inArgs.add(personalId);
      }
    
    Collection outArgs = new Vector();
	outArgs.add(new Integer(java.sql.Types.VARCHAR));
   
     
     log.info("P_INS_HUB_ITEM_DIRECTED_SUPPLY  " + inArgs);
     GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
     Collection procedureData = genericSqlFactory.doProcedure(connection,"P_INS_HUB_ITEM_DIRECTED_SUPPLY", inArgs, outArgs);
		Iterator i11 = procedureData.iterator();
		while (i11.hasNext()) {
			result = (String) i11.next();
		}
	}catch (Exception ee) {
		result = library.getString("error.db.create");
		ee.printStackTrace();
				
	}
	return result;
 }
}


