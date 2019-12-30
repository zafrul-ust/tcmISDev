package com.tcmis.internal.distribution.process;

import com.tcmis.internal.distribution.beans.*;


import com.tcmis.internal.supply.factory.DbuyContractPriceOvBeanFactory;
import com.tcmis.internal.supply.beans.DbuyContractPriceBreakObjBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;

import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;

import java.sql.Connection;
import java.math.BigDecimal;
import java.util.*;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class SPLChangePriorityProcess extends GenericProcess {
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());	 
	  
   public SPLChangePriorityProcess(TcmISBaseAction act) throws BaseException{
	    super(act);
   }
 
   public Collection getDbuy(DbuyContractPriceOvBean bean) throws BaseException {
	   GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DbuyContractPriceOvBean());
	   StringBuilder query = new StringBuilder("select distinct item_id,item_desc,ship_to_location_id,inventory_group_name,priority,supplier_name,dbuy_contract_id from DBUY_CONTRACT_PRICE_OV where ");
	   if( !isBlank(bean.getOpsEntityId())){
		query.append("OPS_ENTITY_ID  = '");
		query.append(bean.getOpsEntityId());}
	   if( !isBlank(bean.getInventoryGroup())){
		query.append("' and INVENTORY_GROUP = '");
		query.append(bean.getInventoryGroup());}
	   if( !isBlank(bean.getItemId())){
		query.append("' and ITEM_ID = ");
		query.append(bean.getItemId());}
	   if( !isBlank(bean.getShipToCompanyId())){
		query.append(" and SHIP_TO_COMPANY_ID = '");
		query.append(bean.getShipToCompanyId());}
	   if( !isBlank(bean.getShipToLocationId())){
		query.append("' and SHIP_TO_LOCATION_ID = '");
		query.append(bean.getShipToLocationId());}
		query.append("' order by PRIORITY");
	
	   Collection c = factory.selectQuery(query.toString());
     
       return c;
  }
   
   public String checkPriorityCount(DbuyContractPriceOvBean bean) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CustomerCreditBean());
		
		StringBuilder query = new StringBuilder("select count(*) from dbuy_contract where OPS_ENTITY_ID  = '");
		query.append(bean.getOpsEntityId());
		query.append("' and ITEM_ID = ");
		query.append(bean.getItemId());
		query.append(" and INVENTORY_GROUP = '");
		query.append(bean.getInventoryGroup());
		query.append("' and SHIP_TO_COMPANY_ID = '");
		query.append(bean.getShipToCompanyId());
		query.append("' and SHIP_TO_LOCATION_ID = '");
		query.append(bean.getShipToLocationId());
		query.append("' order by PRIORITY");
		
//log.debug(query);	
		return factory.selectSingleValue(query.toString());
  }
   
   public String updatePriority(Collection<DbuyContractPriceOvBean> beans) throws BaseException, Exception {
	   DbManager dbManager = new DbManager(getClient(), getLocale());
	   GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CustomerCreditBean());
	   Connection connection = dbManager.getConnection();
	   String result = null;
	   
	   try {
//			connection.setAutoCommit(false);
		   int i = -1;	
		   
		   for(DbuyContractPriceOvBean bean:beans) {
		    	StringBuilder updateQuery  = new StringBuilder("update dbuy_contract set priority = ").append(i).append(" where dbuy_contract_id = ").append(bean.getDbuyContractId());
		    	factory.deleteInsertUpdate(updateQuery.toString(), connection);
		    	i --;
		    }
		   
    		for(DbuyContractPriceOvBean bean:beans) {
    	//		if(!bean.getPriority().equals(bean.getOldPriority())) {
    				StringBuilder updateQuery  = new StringBuilder("update dbuy_contract set priority = ").append(bean.getPriority()).append(" where dbuy_contract_id = ").append(bean.getDbuyContractId());
    				factory.deleteInsertUpdate(updateQuery.toString(), connection);
    	//		}
    		}
    		return result;
	   }catch (Exception e) {
			e.printStackTrace();
			result = library.getString("error.db.update");
			return result;
	   }
	   finally {
			if (connection != null) {
//				connection.commit();
//				connection.setAutoCommit(true);
				dbManager.returnConnection(connection);
			}
		}
  }
 

}
