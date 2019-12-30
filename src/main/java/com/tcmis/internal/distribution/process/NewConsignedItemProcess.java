package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.distribution.beans.NewConsignedItemInputBean;


public class NewConsignedItemProcess extends GenericProcess {
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public NewConsignedItemProcess(String client) {
		super(client);
	}

	public NewConsignedItemProcess(String client, String locale) {
		super(client, locale);
	}
	
	public String addNewItem(NewConsignedItemInputBean bean) throws BaseException {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = null;

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if(bean!=null )
		{

			try {
					inArgs = new Vector(11);				    
					inArgs.add(bean.getDestInventoryGroup());
					inArgs.add(bean.getItemId());
					inArgs.add(bean.getSpecListId());
					inArgs.add(bean.getSpecDetail());
					inArgs.add(bean.getSpecCoc());
					inArgs.add(bean.getSpecCoa());
					inArgs.add(bean.getSpecLibrary());
					inArgs.add(bean.getUnitPrice());
					inArgs.add(bean.getCurrencyId());
					inArgs.add(bean.getSupplyFrom());
					inArgs.add(bean.getSourceInventoryGroup());
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					if(log.isDebugEnabled()) {
						log.debug("Input Args for pkg_consigned_item.p_add_new_item :" + inArgs);
					}			   
					Vector error = (Vector)factory.doProcedure("pkg_consigned_item.p_add_new_item", inArgs, outArgs);
					if( error.size()>0 && error.get(0) != null && !"ok".equalsIgnoreCase((String)error.get(0)))
					{
						errorMsg = (String) error.get(0);
					}			     

			} catch (Exception e) {
				errorMsg = "Error Adding Consigned Item : "+ bean.getItemId();
			}
		}  

		return errorMsg;
	}
	
	
	public String getSrcInventoryGroup(NewConsignedItemInputBean bean) throws BaseException {
		String result = null;
		
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		
		String query = "select source_inventory_group from inventory_group_definition where hub='"+bean.getHub()+
		                "' and inventory_group ='"+bean.getDestInventoryGroup()+"'";
		
		return factory.selectSingleValue(query);
				
				
	}
	
}


