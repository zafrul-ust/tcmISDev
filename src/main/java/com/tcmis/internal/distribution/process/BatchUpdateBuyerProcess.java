package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.distribution.beans.BatchUpdateBuyerInputBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;


public class BatchUpdateBuyerProcess extends GenericProcess {
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	public BatchUpdateBuyerProcess(String client) {
		super(client);
	}

	public BatchUpdateBuyerProcess(String client, String locale) {
		super(client, locale);
	}
	
	public Vector doBatchUpdateBuyer(BatchUpdateBuyerInputBean inputBean,BigDecimal personnelId) throws BaseException {
		Vector result = new Vector(2);
		Connection connection = dbManager.getConnection();
		SearchCriteria sc= new SearchCriteria();

        try {
      /*    String rowCount = "";
		    
	
	        if( !isBlank(inputBean.getOpsEntityId()))
	        {
	           StringBuilder countQuery = new StringBuilder("select count(*) from dbuy_contract where status <> 'INACTIVE' and ops_entity_id = '");
	            countQuery.append(inputBean.getOpsEntityId()).append("' and inventory_group = 'All'");
	           if (inputBean.getItemId() != null && !"".equals(inputBean.getItemId()))
	    		   countQuery.append(" and item_id = ").append(inputBean.getItemId());
			   if (inputBean.getSupplier() != null && !"".equals(inputBean.getSupplier()))
				   countQuery.append(" and supplier = '").append(inputBean.getSupplier()).append("'");
	           rowCount = factory.selectSingleValue(countQuery.toString(),connection);
	        }
*/
            String updateRowCount = "0";
            String allIGrowCount = "0";
            Collection<DbuyContractPriceOvBean> c = null;
            String start = "Y";
            
            StringBuilder updateQuery  = new StringBuilder("update dbuy_contract set ");
            
            if (inputBean.getBuyer() != null && !"".equals(inputBean.getBuyer())) {
          	   updateQuery.append("buyer = ").append(inputBean.getBuyer()).append(",");
          	   start = "N";
            }
            
            if (inputBean.getCarrier() != null && !"".equals(inputBean.getCarrier())) {
         	   if(start == "Y") {
         		   updateQuery.append("carrier = '").append(inputBean.getCarrier()).append("',");
         		   start = "N";
         	   }	  
         	   else 
         		   updateQuery.append(" carrier = '").append(inputBean.getCarrier()).append("',");
            } 
           
            if (inputBean.getCriticalOrderCarrier() != null && !"".equals(inputBean.getCriticalOrderCarrier())) {
               if(start == "Y") {
          		   updateQuery.append("critical_order_carrier = '").append(inputBean.getCriticalOrderCarrier()).append("',");
          		   start = "N";
          	   }	  
          	   else 
          		   updateQuery.append(" critical_order_carrier = '").append(inputBean.getCriticalOrderCarrier()).append("',");
         	   
            }
            
            if (inputBean.getSupplierContactId() != null && !"".equals(inputBean.getSupplierContactId())) {
               if(start == "Y") {
           		   updateQuery.append("supplier_contact_id = ").append(inputBean.getSupplierContactId()).append(",");
           		   start = "N";
           	   }	  
           	   else 
           		   updateQuery.append(" supplier_contact_id = ").append(inputBean.getSupplierContactId()).append(",");
            }
            
            if (inputBean.getRemainingShelfLifePercent() != null && !"".equals(inputBean.getRemainingShelfLifePercent())) {
               if(start == "Y") {
            	   updateQuery.append("remaining_shelf_life_percent = ").append(inputBean.getRemainingShelfLifePercent()).append(",");
            	   start = "N";
               }	  
               else 
            	   updateQuery.append(" remaining_shelf_life_percent = ").append(inputBean.getRemainingShelfLifePercent()).append(",");
            }
            
            if (inputBean.getPricingType() != null && !"".equals(inputBean.getPricingType())) {
               if(start == "Y") {
           		   updateQuery.append("pricing_type = '").append(inputBean.getPricingType()).append("',");
           		   start = "N";
           	   }	  
           	   else 
           		   updateQuery.append(" pricing_type = '").append(inputBean.getPricingType()).append("',");
            }
            
            if (inputBean.getSupplyPath() != null && !"".equals(inputBean.getSupplyPath())) {
               if(start == "Y") {
           		   updateQuery.append("supply_path = '").append(inputBean.getSupplyPath()).append("',");
           		   start = "N";
           	   }	  
           	   else 
           		   updateQuery.append(" supply_path = '").append(inputBean.getSupplyPath()).append("',");
            }
            
            updateQuery.append(" updated_by = ").append(personnelId).append(", updated_date = sysdate");
            
            StringBuilder updateQuery2 = new StringBuilder("");
            StringBuilder selectQuery2 = new StringBuilder("");
            if(inputBean.getStartDate() != null) {
	            updateQuery2  = new StringBuilder("update dbuy_contract_price set unit_price = ").append(inputBean.getUnitPrice());
	            updateQuery2.append(", currency_id = '").append(inputBean.getCurrencyId()).append("'");
	            updateQuery2.append(", updated_by = ").append(personnelId).append(", updated_date = sysdate");
	            updateQuery2.append(" where start_date = ").append("TO_DATE('").append(dateFormatter.format(inputBean.getStartDate())).append("', 'MM/DD/RRRR hh24:mi:ss')");
	            updateQuery2.append(" and dbuy_contract_id in (select dbuy_contract_id from DBUY_CONTRACT_PRICE_OV");
	            
	            selectQuery2  = new StringBuilder("select distinct dbuy_contract_id from dbuy_contract X where status <> 'INACTIVE' and dbuy_contract_id in (select dbuy_contract_id from dbuy_contract");
            }
   /*         
            if (rowCount.equalsIgnoreCase("0"))
            {
               
			   StringBuilder whereClause = new StringBuilder(" where status <> 'INACTIVE' and ops_entity_id = '").append(inputBean.getOpsEntityId()).append("'");

			   if (inputBean.getItemId() != null && !"".equals(inputBean.getItemId()))
				   whereClause.append(" and item_id = ").append(inputBean.getItemId());

			   if (inputBean.getSupplier() != null && !"".equals(inputBean.getSupplier()))
				   whereClause.append(" and supplier = '").append(inputBean.getSupplier()).append("'");

			   if (inputBean.getShipToLocationId() != null && !"".equals(inputBean.getShipToLocationId())&& !"All".equals(inputBean.getShipToLocationId()))
				   whereClause.append(" and ship_to_location_id = '").append(inputBean.getShipToLocationId()).append("' and ship_to_company_id = '").append(inputBean.getShipToCompanyId()).append("'");

               if ("Y".equals(inputBean.getAllInventoryGroups()))
				   whereClause.append(" and inventory_group = 'All'");
               else if (inputBean.getInventoryGroup() != null && !"".equals(inputBean.getInventoryGroup()))
				   whereClause.append(" and inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
			   else
               {
                   if (inputBean.getHub() != null && !"".equals(inputBean.getHub()))
                   {
                	   whereClause.append(" and inventory_group in (select inventory_group from user_inventory_group where hub= "+inputBean.getHub()+" and personnel_Id = "+personnelId+" and ops_entity_id = '"+inputBean.getOpsEntityId()+"')");
                   }
                   else
                   {
                	   whereClause.append(" and inventory_group in (select inventory_group from user_inventory_group where personnel_Id = "+personnelId+" and ops_entity_id = '"+inputBean.getOpsEntityId()+"')");
                   }
               }
           
               if(start == "N")
            	   factory.deleteInsertUpdate(updateQuery.append(whereClause).toString(), connection);
               
               if(inputBean.getStartDate() != null) {
            	   factory.deleteInsertUpdate(updateQuery2.append(whereClause).append(")").toString(), connection);
            	   
            	   selectQuery2.append(whereClause).append(") and NOT EXISTS (select NULL from dbuy_contract_price where ");
            	   selectQuery2.append(" DBUY_CONTRACT_ID = X.DBUY_CONTRACT_ID AND start_date = ").append("TO_DATE('").append(dateFormatter.format(inputBean.getStartDate())).append("', 'MM/DD/RRRR hh24:mi:ss'))");
            	   c = factory.setBean(new DbuyContractPriceOvBean()).selectQuery(selectQuery2.toString());
               }
               
               StringBuilder rowCountQuery = new StringBuilder("SELECT count(*) from dbuy_contract");
               updateRowCount = factory.selectSingleValue(rowCountQuery.append(whereClause).toString(),connection);
            }
            else
            {  */
            	StringBuilder allIgQuery = new StringBuilder(updateQuery.toString());
                StringBuilder allIgWhereClause = new StringBuilder(" where status <> 'INACTIVE' and ops_entity_id = '").append(inputBean.getOpsEntityId()).append("'");
                
                StringBuilder allIgSelectQuery = new StringBuilder(selectQuery2.toString());

                if (inputBean.getItemId() != null && !"".equals(inputBean.getItemId()))
				   allIgWhereClause.append(" and item_id = ").append(inputBean.getItemId());

                if (inputBean.getSupplier() != null && !"".equals(inputBean.getSupplier()))
				   allIgWhereClause.append(" and supplier = '").append(inputBean.getSupplier()).append("'");

                if (inputBean.getShipToLocationId() != null && !"".equals(inputBean.getShipToLocationId())&& !"All".equals(inputBean.getShipToLocationId()))
				   allIgWhereClause.append(" and ship_to_location_id = '").append(inputBean.getShipToLocationId()).append("' and ship_to_company_id = '").append(inputBean.getShipToCompanyId()).append("'");

                allIgQuery.append(allIgWhereClause);
                allIgQuery.append(" and inventory_group = 'All'");
                  
                if(start == "N" && "Y".equals(inputBean.getAllInventoryGroups()))
                	factory.deleteInsertUpdate(allIgQuery.toString(), connection);
                
                StringBuilder allIgQuery2 = new StringBuilder(updateQuery2.toString());
                if(inputBean.getStartDate() != null && "Y".equals(inputBean.getAllInventoryGroups())) {
             	   factory.deleteInsertUpdate(allIgQuery2.append(allIgWhereClause).append(")").toString(), connection);
             	   
             	   allIgSelectQuery.append(allIgWhereClause).append(") and NOT EXISTS (select NULL from dbuy_contract_price where ");
             	   allIgSelectQuery.append(" DBUY_CONTRACT_ID = X.DBUY_CONTRACT_ID AND start_date = ").append("TO_DATE('").append(dateFormatter.format(inputBean.getStartDate())).append("', 'MM/DD/RRRR hh24:mi:ss'))");
             	   c = factory.setBean(new DbuyContractPriceOvBean()).selectQuery(allIgSelectQuery.toString(), connection);	
                }
         

                if("Y".equals(inputBean.getAllInventoryGroups())) {
	                StringBuilder allIgRowCountQuery = new StringBuilder("SELECT count(*) from dbuy_contract");
	                allIgRowCountQuery.append(allIgWhereClause);
	                allIgRowCountQuery.append(" and inventory_group = 'All'");
	                allIGrowCount = factory.selectSingleValue(allIgRowCountQuery.toString(),connection);
                }

               
               StringBuilder whereClause = new StringBuilder(" where status <> 'INACTIVE' and ops_entity_id = '").append(inputBean.getOpsEntityId()).append("'");

			   if (inputBean.getItemId() != null && !"".equals(inputBean.getItemId()))
				   whereClause.append(" and item_id = ").append(inputBean.getItemId());

			   if (inputBean.getSupplier() != null && !"".equals(inputBean.getSupplier()))
				   whereClause.append(" and supplier = '").append(inputBean.getSupplier()).append("'");

			   if (inputBean.getShipToLocationId() != null && !"".equals(inputBean.getShipToLocationId())&& !"All".equals(inputBean.getShipToLocationId()))
				   whereClause.append(" and ship_to_location_id = '").append(inputBean.getShipToLocationId()).append("' and ship_to_company_id = '").append(inputBean.getShipToCompanyId()).append("'");

               if (inputBean.getInventoryGroupList() != null && !"".equals(inputBean.getInventoryGroupList()))
				   whereClause.append(" and inventory_group in (").append(inputBean.getInventoryGroupList()).append(")");
			   else
               {
                   if (inputBean.getHub() != null && !"".equals(inputBean.getHub()))
                   {
                	   whereClause.append(" and inventory_group <> 'All' and inventory_group in (select inventory_group from user_inventory_group where hub= "+inputBean.getHub()+" and personnel_Id = "+personnelId+" and ops_entity_id = '"+inputBean.getOpsEntityId()+"')");
                   }
                   else
                   {
                	   whereClause.append(" and inventory_group <> 'All' and inventory_group in (select inventory_group from user_inventory_group where personnel_Id = "+personnelId+" and ops_entity_id = '"+inputBean.getOpsEntityId()+"')");
                   }
               }
                
               if(start == "N")
            	   factory.deleteInsertUpdate(updateQuery.append(whereClause).toString(), connection);
               
               if(inputBean.getStartDate() != null) {
             	   factory.deleteInsertUpdate(updateQuery2.append(whereClause).append(")").toString(), connection);
             	  
             	   selectQuery2.append(whereClause).append(") and NOT EXISTS (select NULL from dbuy_contract_price where ");
             	   selectQuery2.append(" DBUY_CONTRACT_ID = X.DBUY_CONTRACT_ID AND start_date = ").append("TO_DATE('").append(dateFormatter.format(inputBean.getStartDate())).append("', 'MM/DD/RRRR hh24:mi:ss'))");
             	   
             	   Collection ccc = factory.setBean(new DbuyContractPriceOvBean()).selectQuery(selectQuery2.toString(), connection);
             	   
             	   if(c == null)
             		   c = ccc;
             	   else
             		   c.addAll(ccc);
               }
               
               StringBuilder rowCountQuery = new StringBuilder("SELECT count(*) from dbuy_contract");
               updateRowCount = factory.selectSingleValue(rowCountQuery.append(whereClause).toString(),connection);
   //         }
            
            StringBuilder insertQuery2 = new StringBuilder("");
            if(c != null && c.size() > 0) {
	            for (DbuyContractPriceOvBean bean : c) {
	            	insertQuery2  = new StringBuilder("insert into dbuy_contract_price (dbuy_contract_id, start_date, currency_id, unit_price, entered_by, entered_date)");
	            	insertQuery2.append(" values ( ").append(bean.getDbuyContractId()).append(",TO_DATE('").append(dateFormatter.format(inputBean.getStartDate())).append("', 'MM/DD/RRRR hh24:mi:ss'),'");
	            	insertQuery2.append(inputBean.getCurrencyId()).append("',").append(inputBean.getUnitPrice()).append(",").append(personnelId).append(", sysdate)");
	            	factory.deleteInsertUpdate(insertQuery2.toString(), connection);
	            }
            }

            BigDecimal updateRowCountValue = new BigDecimal(updateRowCount);
            BigDecimal allIGrowCountValue = new BigDecimal(allIGrowCount);

            result.add(library.getString("label.rowsupdated").replace("{0}", ""+updateRowCountValue.add(allIGrowCountValue)+""));
            result.add("done");
           
            return result;
		}catch (Exception e) {
				e.printStackTrace();
				
				result.add(library.getString("error.db.update"));
				result.add("error");
			
				return result;
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}
	}

}
