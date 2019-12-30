package com.tcmis.internal.hub.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.sql.Connection;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.MrAddChargeViewBean;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class AddPrinterLocationProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

	private DbManager dbManager;
	private Connection connection = null;
	private ResourceLibrary library = null;

  public AddPrinterLocationProcess(String client) {
    super(client);
  }

	public AddPrinterLocationProcess(String client,String locale)  {
	    super(client,locale);
	}

 
	public Collection getResult(String selectedHub) throws BaseException {
	      DbManager dbManager = new DbManager(getClient(),getLocale());
	      GenericSqlFactory factory = new GenericSqlFactory(dbManager, new LocationLabelPrinterBean());      
	      SearchCriteria criteria = new SearchCriteria();      
	      
	         criteria.addCriterion("hub", SearchCriterion.EQUALS, "hub");
	            
	      SortCriteria scriteria = new SortCriteria();      
	      return factory.select(criteria,scriteria, "location_label_printer");
	   }

	
	public String insertPrinterLocation(PrinterLocationBean bean) throws BaseException, Exception {
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
        String result="";
        String output="";
        
        String query1 = "select printer_location from PRINTER_LOCATION where printer_location ='"+bean.getPrinterLocation()+"'";
        output= genericSqlFactory.selectSingleValue(query1);
        if(output.length() == 0)
        {
			String query  = "insert into PRINTER_LOCATION(printer_location,company_id,supplier,supplier_location_id,facility_id,hub) values(" +
			                "'"+bean.getPrinterLocation()+"','"+bean.getCompanyId()+"','"
			                +bean.getSupplier()+"','','"
			                +bean.getFacilityId()+"','"
			                +bean.getHub()+"')";
			   try{
	             genericSqlFactory.deleteInsertUpdate(query);
			   }
			   catch (Exception e) {
					e.printStackTrace();
					result = library.getString("error.db.update");
				  }
        } 
        else
        {
        	result = "Printer Location already exists";
        }
             return result;
	}

public String insertPrinterPath(LocationLabelPrinterBean bean,PrinterLocationBean plBean) throws BaseException, Exception {
	
	DbManager dbManager = new DbManager(getClient(),getLocale());
	   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
    String result="";
    
	String query  = "insert into LOCATION_LABEL_PRINTER(printer_location,label_stock,printer_path,"+
		            "printer_resolution_dpi,company_id,supplier,supplier_location_id,facility_id,hub) values(" +
	                "'"+plBean.getPrinterLocation()+"','"
	                +bean.getLabelStock()+"','"
	                +bean.getPrinterPath()+"',"+
	                bean.getPrinterResolutionDpi()+",'"
	                +plBean.getCompanyId()+"','"
	                +plBean.getSupplier()+
	                "','','"
	                +plBean.getFacilityId()+"','"
	                +plBean.getHub()+"')";
	   try{
         genericSqlFactory.deleteInsertUpdate(query);
	   }
	   catch (Exception e) {
			e.printStackTrace();
			result = library.getString("error.db.update");
		  }
         
         return result;
   }
}