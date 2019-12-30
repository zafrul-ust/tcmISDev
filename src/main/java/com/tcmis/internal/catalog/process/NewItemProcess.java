package com.tcmis.internal.catalog.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.CatalogQueueItemBean;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.OpsEntityRemittanceViewBean;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class NewItemProcess
   extends GenericProcess { 
   Log log = LogFactory.getLog(this.getClass());

   public NewItemProcess(String client) {
  	super(client);
   }
   
   public NewItemProcess(String client,String locale) {
	    super(client,locale);
  }

   public Collection getCurrencyDropDown() throws
	BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new OpsEntityRemittanceViewBean());
		Collection c = null;
		StringBuilder tmpQry = new StringBuilder("select distinct ops_entity_id,currency_id,currency_display from ops_entity_remittance_view");

		c = factory.selectQuery(tmpQry.toString());

		return c;
	}
	public Collection getVvLocaleDropDown() throws BaseException {
	      return factory.setBean( new VvLocaleBean() ).select(null, new SortCriteria("localeDisplay"),"vv_locale");
	}  

	public String[] insertData(CatalogQueueItemBean bean,String supplier) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CatalogQueueItemBean());
		
		String checkQuery = "select catalog_queue_item_id from catalog_queue_item where item_id = " + getSqlString(bean.getItemId()) +
				" and locale_code = " + getSqlString(bean.getLocaleCode()) +
				" and task = "+ getSqlString(bean.getTask()) ;
		String catalogQueueItemId = factory.selectSingleValue(checkQuery);
		String returnData[] =  {"error","",""}; 
		try {
		if( isBlank(catalogQueueItemId) ) {
		String query = "insert into catalog_queue_item ( ITEM_ID , ADDL_INGRED_ITEM_ID, SOURCING_ITEM_ID , LOCALE_CODE , TASK ) values ( " +
				this.getSqlString(bean.getItemId()) +"," +
				this.getSqlString(bean.getAddlIngredItemId()) +"," +
				this.getSqlString(bean.getSourcingItemId()) +"," +
				this.getSqlString(bean.getLocaleCode()) +"," +
				this.getSqlString(bean.getTask()) +" ) ";
		factory.deleteInsertUpdate(query);
		catalogQueueItemId = factory.selectSingleValue(checkQuery);
		String returnData1[] =  {"new",catalogQueueItemId,bean.getTask(),bean.getLocaleCode()};
		returnData = returnData1;
//		checkQuery = "select catalog_Vendor_assignment_id from catalog_Vendor_assignment_id from catalog_queue_item where item_id = " + getSqlString(bean.getItemId()) +
//				" and locale_code = " + getSqlString(bean.getLocaleCode()) +
//				" and task = "+ getSqlString(bean.getTask()) ;
		} else {
		String query = "update catalog_queue_item set ITEM_ID = " + getSqlString(bean.getItemId()) +
				", ADDL_INGRED_ITEM_ID = " + getSqlString(bean.getAddlIngredItemId()) +
				", SOURCING_ITEM_ID = " + getSqlString(bean.getSourcingItemId()) +
				", LOCALE_CODE = " +getSqlString(bean.getLocaleCode()) +
				", TASK = "+ getSqlString(bean.getTask()) +
				" where catalog_Queue_Item_Id) " + getSqlString(catalogQueueItemId);
		factory.deleteInsertUpdate(query);
		String returnData2[] =  {"existed",catalogQueueItemId,"",""};
		returnData = returnData2;
		}
		}catch(Exception ex) {};
		return returnData;
	}


   public String getItemDesc(String itemId) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(), getLocale());
	   GenericSqlFactory factory = new GenericSqlFactory(dbManager);
	   StringBuilder query = new StringBuilder("select part_description from catalog_item where item_id = ").append(itemId);
		
	   return factory.selectSingleValue(query.toString());
   }
   
   public String getBuyTypeFlag(String inventoryGroup) throws BaseException {
	   String defaultFlag = "N";
	   
	   if(!StringHandler.isBlankString(inventoryGroup) && !inventoryGroup.toUpperCase().equals("ALL")){   
		   DbManager dbManager = new DbManager(getClient(), getLocale());
		   GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		   StringBuilder query = new StringBuilder("select buy_type_flag from inventory_group_definition where inventory_group = ").append(SqlHandler.delimitString(inventoryGroup));
			
		   return factory.selectSingleValue(query.toString());
	   }
	   
	   return defaultFlag;
   }
 
   public Collection getLocaleForTask(String task) throws BaseException {
	   String query = "select * from CATALOG_TASK_LOCALE_MISSING_VW where task = "+ getSqlString(task);
	   return factory.setBean( new VvLocaleBean() ).selectQuery(query);
   }
   

}
