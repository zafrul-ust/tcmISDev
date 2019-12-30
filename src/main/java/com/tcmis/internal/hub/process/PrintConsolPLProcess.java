package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;

public class PrintConsolPLProcess extends BaseProcess{
	
	public PrintConsolPLProcess(String client) {
		super(client);
	}

	public PrintConsolPLProcess(String client,String locale) {
		super(client,locale);
	}
	
	public String getTemplateName(String shipmentId)
	{
		DbManager dbManager = new DbManager(getClient());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager);
        String result="";
        String query = "select distinct igd.packing_list_template from INVENTORY_GROUP_DEFINITION igd, shipment s"+
                       " where igd.inventory_group = s.inventory_group and s.shipment_id = "+shipmentId;
        
        try {
			 result = factory.selectSingleValue(query);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result.equalsIgnoreCase("PackingListReport"))
		{
			result= "DistConsolPLRptDef";
		}
		else
		{
			result= "UKDistConsolPLRptDef";
		}
        
		return result;
	}
	
	public void setWarrantyDate(String shipmentId)
	{
		try{
		     DbManager dbManager = new DbManager(getClient(),getLocale());
		     GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		     Collection cin  = new Vector(1);
			 	cin.add(shipmentId);
			 	procFactory.doProcedure("pkg_defined_shelf_life.p_shipment_cust_warranty_date", cin);
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  
			  }

     }
}
