package com.tcmis.internal.distribution.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.OpsEntityRemittanceViewBean;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class NewItemProcess
   extends BaseProcess {
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

}
