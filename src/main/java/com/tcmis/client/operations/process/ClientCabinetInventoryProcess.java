package com.tcmis.client.operations.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.operations.beans.CabinetInventoryCountBean;
import com.tcmis.client.operations.beans.ClientCabinetInventoryInputBean;
import com.tcmis.client.operations.beans.HubPreferredWarehouseViewBean;
import com.tcmis.client.operations.factory.CabinetInventoryCountBeanFactory;
import com.tcmis.client.operations.factory.HubPreferredWarehouseViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryBean;

/******************************************************************************
 * Process for Cabinet Inventory
 * @version 1.0
 *****************************************************************************/

public class ClientCabinetInventoryProcess
    extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
  
	public ClientCabinetInventoryProcess(String client) 
	{
		super(client);
	}  
  
	public ClientCabinetInventoryProcess(String client,String locale) {
	    super(client,locale);
    }
	
		
	public Collection getSearchResult(	ClientCabinetInventoryInputBean inputBean) throws BaseException,Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());		
		CabinetInventoryCountBeanFactory cabinetInvFactory = new CabinetInventoryCountBeanFactory(dbManager);
		Collection<CabinetInventoryCountBean> c = cabinetInvFactory.selectUsingProc(inputBean);
		return c;
	}

	public ExcelHandler createExcelFile(ClientCabinetInventoryInputBean bean, Locale locale) throws
      							Exception 
    {
		  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		  ExcelHandler pw = new ExcelHandler(library);
		  

		Collection<CabinetInventoryCountBean> data = getSearchResult(bean);
		String dpmOrParNum="label.partnum";
		for (CabinetInventoryCountBean member : data) {
		if(null!=member.getHub())
		{
		  if("2118".equals(member.getHub()))	
		  {
			  dpmOrParNum = "label.dpm";
			  break;
		  }		  
			 
		}
		}
		
		pw.addTable();
		pw.addRow();
		 
        String[] headerkeys =    {"label.item","label.status","label.description",dpmOrParNum,"label.workarea","label.cabinet",
		"label.totalqty","label.openmrqty","label.currencyid","label.unitcost","label.reorderpoint",
		"label.stockinglevel","label.leadtimeindays"};
        int[] widths = {0,0,30,16,16,0,0,0,0,0,0,0,0};
        int [] types = {pw.TYPE_NUMBER,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] aligns = {0,0,0,0,0,0,0,0,0,0,0,0,0};

        pw.applyColumnHeader(headerkeys, types, widths, aligns);
    	//DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        
        pw.setColumnDigit(8, 4);
		
        for (CabinetInventoryCountBean cabinetInventoryBean : data) 
        {
        	pw.addRow();
			pw.addCell(cabinetInventoryBean.getItemId());
			if("A".equalsIgnoreCase(cabinetInventoryBean.getStatus()))
				pw.addCell(library.getString("label.active"));
			else if("D".equalsIgnoreCase(cabinetInventoryBean.getStatus()))
				pw.addCell(library.getString("label.drawdown"));
			else if("O".equalsIgnoreCase(cabinetInventoryBean.getStatus()))
				pw.addCell(library.getString("label.obsolete"));
			else 
				pw.addCell("");
			pw.addCell(cabinetInventoryBean.getItemDesc());	
			if("2118".equals(cabinetInventoryBean.getHub()))	
		    {
		 	 if(!StringHandler.isBlankString(cabinetInventoryBean.getQcDoc())) 
		     pw.addCell(cabinetInventoryBean.getQcDoc());
		 	 else
		 	 pw.addCell("");	 
		    }
		    else
		    {
	    	 if(!StringHandler.isBlankString(cabinetInventoryBean.getCatPartNo())) 
			 pw.addCell(cabinetInventoryBean.getCatPartNo());
			 else
			 pw.addCell("");		   
		    }			
			pw.addCell(cabinetInventoryBean.getUseApplication());			
			pw.addCell(cabinetInventoryBean.getCabinetName());
			pw.addCell(cabinetInventoryBean.getTotalQuantity());
			pw.addCell(cabinetInventoryBean.getOpenMrQty());	
			if(cabinetInventoryBean.getUnitCost() == null) 
				pw.addCell("");
			else
				pw.addCell(cabinetInventoryBean.getCurrencyId());
			pw.addCell(cabinetInventoryBean.getUnitCost());
			pw.addCell(cabinetInventoryBean.getReorderPoint());
			pw.addCell(cabinetInventoryBean.getStockingLevel());
			pw.addCell(cabinetInventoryBean.getLeadTimeDays());			
			
		}
		return pw;
	}
	
  
	 
  	public Collection<HubPreferredWarehouseViewBean> getHubPreferredWareHouseList() throws BaseException,
	Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		
		HubPreferredWarehouseViewBeanFactory factory = new HubPreferredWarehouseViewBeanFactory(dbManager);			
		
		Collection<HubPreferredWarehouseViewBean> c = factory.select(null, null);
		return c;
}
  	
}

