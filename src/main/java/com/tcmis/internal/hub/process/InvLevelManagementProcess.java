package com.tcmis.internal.hub.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.InvLevelManagementBean;
import com.tcmis.internal.hub.beans.InvLevelManagementInputBean;

import com.tcmis.internal.hub.factory.InvLevelManagementBeanFactory;

/******************************************************************************
 * Process for InvLevelManagement
 * @version 1.0
 *****************************************************************************/

public class InvLevelManagementProcess
    extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
	boolean DEBUG = true;
  
	public InvLevelManagementProcess(String client) 
	{
		super(client);
	}  
	
	public InvLevelManagementProcess(String client,String locale) {
	    super(client,locale);
    }
  
	public Collection getInvLevelManagementBeanCollection (InvLevelManagementInputBean inputBean) throws BaseException 
    {
	    DbManager dbManager = new DbManager(getClient(),getLocale());		
	    InvLevelManagementBeanFactory factory = new InvLevelManagementBeanFactory(dbManager);
		
		return factory.getInvLevelManagementBeanCollection (inputBean );
    }
	
	public ExcelHandler createExcelFile(InvLevelManagementInputBean bean, Locale locale) throws
      Exception 
    {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

		Collection data = this.getInvLevelManagementBeanCollection (bean);
		//log.debug("data:" + data);
		Iterator iterator = data.iterator();
		  ExcelHandler pw = new ExcelHandler(library);
		  
		  pw.addTable();

//		  write column headers
		pw.addRow();
        String[] headerkeys = {
        "label.operatingentity","label.hub","label.inventorygroup","label.itemid","label.itemdescription","label.customerpart",
		"label.stockingmethod","label.reorderpoint","label.stockinglevel","label.reorderquantity",
		"label.onhand","label.currencyid","label.onhandvalue",
		"label.pickableandnotalloc","label.nonpickableandnotalloc","label.onorderandnotalloc","label.inpurchasingandnotalloc",
		"label.qtyintransit","label.lastreceived",
		"label.issuedlast30","label.issued30-60","label.issued60-90",
        "label.currencyid","label.minunitprice","label.avgunitprice","label.maxunitprice",
        "label.avgreorderpointvalue","label.avgstockinglevelvalue"
        } ;

      int[] widths = {15,12,15,0,0,12,
                    0,0,0,0,0,0,
                    12,12,12,12,10,
                    10,10,10,10,
                    0,10,10,10,15,15,10};
      int [] types = {0,0,0,0,pw.TYPE_PARAGRAPH,0,
                    0,0,0,0,0,0,0,
                    0,0,0,0,pw.TYPE_CALENDAR,
                    0,0,0,0,0,
                    0,0};
      int[] aligns = {0,0,0,0,0,0,
                    0,0,0,0,0,0,
                    0,0,0,0,0,
                    0,0,0,0,0,
                    0,0};

      pw.applyColumnHeader(headerkeys, types, widths, aligns) ;

        //now write data
      pw.setColumnDigit(10, 2);
      for(int i=20; i <25; i ++)
    	  pw.setColumnDigit(i, 4);
      
		while(iterator.hasNext()) 
		{
			InvLevelManagementBean invLevelManagementBean = (InvLevelManagementBean)iterator.next();

			pw.addRow();
			pw.addCell(invLevelManagementBean.getOperatingEntityName());
			pw.addCell(invLevelManagementBean.getHubName());
			pw.addCell(invLevelManagementBean.getInventoryGroupName());
			pw.addCell(invLevelManagementBean.getItemId());	
			pw.addCell(invLevelManagementBean.getItemDesc());	
			pw.addCell(invLevelManagementBean.getCustomerPart());	
			pw.addCell(invLevelManagementBean.getStockingMethod() );
			pw.addCell(invLevelManagementBean.getReorderPoint());
			pw.addCell(invLevelManagementBean.getStockingLevel());
			pw.addCell(invLevelManagementBean.getReorderQuantity());	
			pw.addCell(invLevelManagementBean.getOnHand());
			if(invLevelManagementBean.getOnHandValue() == null) 
				pw.addCell("");
			else 
				pw.addCell(invLevelManagementBean.getCurrencyId());	
			pw.addCell(invLevelManagementBean.getOnHandValue());
			pw.addCell(invLevelManagementBean.getPickableMinusAlloc());
			pw.addCell(invLevelManagementBean.getNonpickableMinusAlloc());			
			pw.addCell(invLevelManagementBean.getOnorderMinusAlloc());						
			pw.addCell(invLevelManagementBean.getInpurchasingMinusAlloc());
            pw.addCell(invLevelManagementBean.getInTransit());
            pw.addCell(invLevelManagementBean.getLastReceived());
			pw.addCell(invLevelManagementBean.getIssuedLast30());
			pw.addCell(invLevelManagementBean.getIssued3060());
			pw.addCell(invLevelManagementBean.getIssued6090());
			if(invLevelManagementBean.getMinUnitPrice() == null && invLevelManagementBean.getAvgUnitPrice() == null && invLevelManagementBean.getMaxUnitPrice() == null &&
					invLevelManagementBean.getAvgRpValue() == null && invLevelManagementBean.getAvgSlValue() == null) 
				pw.addCell("");
			else 
				pw.addCell(invLevelManagementBean.getCurrencyId());	
			pw.addCell(invLevelManagementBean.getMinUnitPrice());
			pw.addCell(invLevelManagementBean.getAvgUnitPrice());
			pw.addCell(invLevelManagementBean.getMaxUnitPrice());
			pw.addCell(invLevelManagementBean.getAvgRpValue());
			pw.addCell(invLevelManagementBean.getAvgSlValue());	
			
		}
		return pw;
    }
	
}

