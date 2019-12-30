package com.tcmis.internal.distribution.process;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.factory.ItemBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.DbuyContractAddChargeInputBean;
import com.tcmis.internal.distribution.beans.DbuyContractAddChargeViewBean;
import com.tcmis.internal.supply.beans.SuppEntityItemNotesViewBean;



public class DbuyAdditionalChargesProcess  extends BaseProcess {

	  public DbuyAdditionalChargesProcess(String client) {
	    super(client);
	  }
		
	  public DbuyAdditionalChargesProcess(String client, String locale) {
	    super(client,locale);
	  }
	  
	  public Collection<DbuyContractAddChargeViewBean> getDbuyCharges(DbuyContractAddChargeInputBean bean, PersonnelBean personnelBean)
		throws BaseException {

			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new DbuyContractAddChargeViewBean());
			SearchCriteria criteria = new SearchCriteria();
			
			criteria.addCriterion("dbuyContractId", SearchCriterion.EQUALS, bean.getDbuyContractId().toString());
			criteria.addCriterion("startDate", SearchCriterion.IS_DATE, bean.getStartDate());	
			
				SortCriteria sort = new SortCriteria();
				sort.setSortAscending(true);		
				
	            sort.addCriterion("addChargeItemId");
	            return factory.select(criteria, sort, "dbuy_contract_addcharge_view");
	  }
	  
	  public String update(Collection<DbuyContractAddChargeViewBean> beans, PersonnelBean personnel,DbuyContractAddChargeInputBean bean) throws BaseException {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new DbuyContractAddChargeViewBean());
			String result = "";
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
			DateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy"); 
			String dateString= dateFormat.format(bean.getStartDate());
			for(DbuyContractAddChargeViewBean b :beans)
			{
				try {
				if(b.isOkDoUpdate())
				{
					String query; 
		    		if(b.getIsNewLine())
		    		{
		    			query = "insert into dbuy_contract_addcharge" +
		    			" (dbuy_contract_id, add_charge_item_id, unit_price, start_date, entered_by,entered_date) values " +
		    			"("+b.getDbuyContractId()+ ","+ 
		    			b.getAddChargeItemId()+"," + 
		    			b.getUnitPrice()+","+
		    			"to_date('"+dateString+"','MM/DD/YYYY'),"+
		    			personnel.getPersonnelId()+ ",sysdate)";
		    		}
		    		else
		    		{
						 query = "update dbuy_contract_addcharge SET unit_price =" + b.getUnitPrice() +
						", start_date = to_date('"+dateString+
						"','MM/DD/YYYY'), updated_by="+personnel.getPersonnelId()+
						", updated_date=sysdate"+
						" where add_charge_item_id =" + b.getAddChargeItemId()+
						" and dbuy_contract_id = "+b.getDbuyContractId();
		    		}
					
					
					factory.deleteInsertUpdate(query);
					
						
				}
				}
				catch (Exception e) {
					e.printStackTrace();
					result = library.getString("error.db.update");
				  }
			}
			return result;
		  }
	  
	  public String delete(Collection<DbuyContractAddChargeViewBean> beans, PersonnelBean personnel) throws BaseException {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new DbuyContractAddChargeViewBean());
			for(DbuyContractAddChargeViewBean b :beans)
			{
				if(b.isOkDoUpdate())
				{
					String query = "delete from dbuy_contract_addcharge where dbuy_contract_id = "+b.getDbuyContractId()+" and add_charge_item_id = "+b.getAddChargeItemId();
					factory.deleteInsertUpdate(query);
					
			  }
			}
			return "";
		  } 
		  
	  
	  public Collection listCharges(DbuyContractAddChargeInputBean bean)
		{
		    DateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy"); 
			String dateString= dateFormat.format(bean.getStartDate());
			Collection chargeList = null;
			try {
				DbManager dbManager = new DbManager(this.getClient());
				ItemBeanFactory itemFactory = new ItemBeanFactory(dbManager);

				String chkAckQry = "select item.item_id,item.item_desc from item, vv_item_type it" +
						" where item.item_type = it.item_type and it.ma_add_charge = 'y' and it.cost = 'y' " +
						"and it.reimbursable = 'y' and item.item_id not in (select add_charge_item_id from dbuy_contract_addcharge where dbuy_contract_id = "+bean.getDbuyContractId()+
						" and start_date = to_date('"+dateString+
								"','MM/DD/YYYY')) order by item.item_id";
				
									
				chargeList = itemFactory.select(chkAckQry);

			} catch (BaseException be) {
				log.error("Base Exception in DbuyAdditionalChargesProcess::listCharges: " + be);
			} catch (Exception e) {
				log.error("unknown exception in listCharges:: " + e);
			}
			return chargeList;
		}
	    
}
