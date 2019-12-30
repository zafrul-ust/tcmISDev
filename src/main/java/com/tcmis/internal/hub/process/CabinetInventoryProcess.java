package com.tcmis.internal.hub.process;

import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.CabinetInventoryBean;
import com.tcmis.internal.hub.beans.CabinetInventoryInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.factory.CabinetInventoryBeanFactory;
import com.tcmis.internal.hub.factory.LogisticsViewBeanFactory;
import com.tcmis.internal.report.beans.OpenPicksViewBean;

/******************************************************************************
 * Process for Cabinet Inventory
 * @version 1.0
 *****************************************************************************/

public class CabinetInventoryProcess
    extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
  
	public CabinetInventoryProcess(String client) 
	{
		super(client);
	}  
  
	public CabinetInventoryProcess(String client,String locale) {
	    super(client,locale);
    }

	public static String getCsvStringFromArray (String[] stringArray)
	{
		StringBuilder csvStringBuilder = new StringBuilder();
		if(stringArray == null || stringArray.length == 0)
			return "''";
		for(int i = 0; i < stringArray.length; i++)
			if( stringArray[ i ].equals("") )
				return "''";

		csvStringBuilder.append("'");
		for(int i = 0; i < stringArray.length; i++) 	{
			if (i > 0)	{
				csvStringBuilder.append(",");
			}
			csvStringBuilder.append(stringArray[ i ]);
		}
		csvStringBuilder.append("'");
		return csvStringBuilder.toString();
	}
	
	public Collection getCabinetInventoryBeanCollection (CabinetInventoryInputBean inputBean) throws BaseException 
    {
		 DbManager dbManager = new DbManager(getClient(),getLocale());
		 GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetInventoryBean());
		 StringBuilder query = new StringBuilder("select * from table (pkg_stocking_application.fx_stocking_inv_count_report('").append(inputBean.getCompanyId()).append("','");
		 query.append(inputBean.getFacilityId()).append("'");
		 if (inputBean.getApplication() != null  && inputBean.getApplication().length() > 0 && !"All".equalsIgnoreCase(inputBean.getApplication())) {
		 	query.append(",'").append(inputBean.getApplication()).append("'");
		 }else {
			 if (inputBean.getUseApplication()!= null && inputBean.getUseApplication().length() > 0 && !"All".equalsIgnoreCase(inputBean.getUseApplication())) {
			 	query.append(",'").append(inputBean.getUseApplication()).append("'");
			 }else {
				query.append(",''");
			 }
		 }
		 query.append(",").append(getCsvStringFromArray(inputBean.getCabinetIdArray()));
		 if (inputBean.getSearchArgument() != null && inputBean.getSearchArgument().length() > 0) {
			 if (inputBean.getMatchingMode().equalsIgnoreCase("Contains"))
			 {
				 query.append(",'Y'");			// position 5
			 }
			 else
			 {
				 query.append(",''");	// position 5
			 }
			 if (inputBean.getSearchField() != null && inputBean.getSearchField().length() > 0)
			 {
				 query.append(",'").append(inputBean.getSearchField()).append("'");		// position 6
			 }
			 else
			 {
				 query.append(",''");						// position 6
			 }
			 query.append(",").append(SqlHandler.delimitString(inputBean.getSearchArgument()));	// position 7
		 }else{
			 query.append(",''");	// position 5
			 query.append(",''");	// position 6
			 query.append(",''");	// position 7
		 }

		 if (inputBean.getExpireInFrom() != null ) {
			 query.append(",'").append(inputBean.getExpireInFrom()).append("'");	// position 8
		 }else{
			 query.append(",''");					// position 8
		 }

		 if (inputBean.getExpireInTo() != null ) {
			 query.append(",'").append(inputBean.getExpireInTo()).append("'");		// position 9
		 }else{
			 query.append(",''");					// position 9
		 }

		 if (inputBean.getSortBy() != null && inputBean.getSortBy().length() > 0) {
			 query.append(",'").append(inputBean.getSortBy()).append("'"); 	// position 10
		 }else{
			 query.append(",''");					// position 10
		 }
		 
		 if ("Yes".equalsIgnoreCase(inputBean.getPositiveQ())) {
			 query.append(",'Y'");					// position 11
		 }else {
			 query.append(",''");					// position 11
		 }
		 
		 if (inputBean.getTierIiStorage() != null && inputBean.getTierIiStorage().length() > 0) {
			 query.append(",'").append(inputBean.getTierIiStorage()).append("'"); 	// position 12
		 }else{
			 query.append(",''");					// position 12
		 }
		 
		 if (inputBean.getAreaId() != null && inputBean.getAreaId().length() > 0) {
			 query.append(",'").append(inputBean.getAreaId().replaceAll("\\|",",")).append("'"); 	// position 13
		 }else{
			 query.append(",''");					// position 13
		 }
		 
		 if (inputBean.getBuildingId() != null && inputBean.getBuildingId().length() > 0) {
			 query.append(",'").append(inputBean.getBuildingId().replaceAll("\\|",",")).append("'"); 	// position 14
		 }else{
			 query.append(",''");					// position 14
		 }
		 
		 if (inputBean.getDeptId() != null && inputBean.getDeptId().length() > 0) {
			 query.append(",'").append(inputBean.getDeptId().replaceAll("\\|",",")).append("'"); 	// position 15
		 }else{
			 query.append(",''");					// position 15
		 }
		 
		 query.append("))");
		 
		 return genericSqlFactory.selectQuery(query.toString());
    }

	public ExcelHandler createExcelFile(CabinetInventoryInputBean bean, Locale locale, PersonnelBean personnelBean) throws Exception  {
		  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		  ExcelHandler pw = new ExcelHandler(library);
		  

		Collection<CabinetInventoryBean> data = this.getCabinetInventoryBeanCollection (bean);
		String dpmOrParNum="label.partnum";
		for (CabinetInventoryBean member : data) {
		if(null!=member.getHub())
		{
		  if("2118".equals(member.getHub()))	
		  {
			  dpmOrParNum = "label.dpm";
			  break;
		  }		  
			 
		}
		}

        boolean homeCompanyOwned = personnelBean.isFeatureReleased("HomeCompanyOwned",bean.getFacilityId(),bean.getCompanyId());
		Iterator iterator = data.iterator();
		pw.addTable();
		pw.addRow();

        String[] headerkeys;
        int[] widths;
        int [] types;
        int[] aligns;
        if (homeCompanyOwned) {
           headerkeys = new String[]{"label.workarea","label.counttype",dpmOrParNum,"label.reorderpoint","label.stockinglevel","label.reorderquantity",
                        "label.kanbanreorderqty","label.leadtimeindays","label.item","label.status","label.description","label.msdsnumber","label.receiptid","label.mfglot","label.expirationdate","label.qualitynote",
                        "label.lastcountdate","label.lastcountqty","label.deliveredsincelastcount","label.transferredsincelastcount","label.totalqty","label.currencyid","label.unitcost","label.area","label.building",
                        "label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.physicalstate","label.packaging"};

           widths = new int[]{0,0,0,0,0,0,          //6
                    0,0,0,0,0,0,0,0,0,0,            //10
                    0,0,0,0,0,0,0,0,0,0,            //10
                    15,15,15,0,15};                 //5

           types = new int[]{0,0,0,0,0,0,
                    0,0,0,0,ExcelHandler.TYPE_PARAGRAPH,0,0,0,pw.TYPE_DATE,0,
                    pw.TYPE_DATE,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0};

            aligns = new int[]{0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0};
        }else {
            headerkeys = new String[]{"label.workarea","label.counttype",dpmOrParNum,"label.reorderpoint","label.stockinglevel","label.reorderquantity",
                        "label.kanbanreorderqty","label.leadtimeindays","label.item","label.status","label.description","label.msdsnumber","label.receiptid","label.mfglot","label.expirationdate","label.qualitynote",
                        "label.lastcountdate","label.lastcountqty","label.deliveredsincelastcount","label.totalqty","label.currencyid","label.unitcost","label.area","label.building",
                        "label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.physicalstate","label.packaging"};

            widths = new int[]{0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,          //only 9 because qty_transfered_since_last_count is not display
                    15,15,15,0,15};

           types = new int[]{0,0,0,0,0,0,
                    0,0,0,0,ExcelHandler.TYPE_PARAGRAPH,0,0,0,pw.TYPE_DATE,0,
                    pw.TYPE_DATE,0,0,0,0,0,0,0,0,   //only 9 because qty_transfered_since_last_count is not display
                    0,0,0,0,0};

            aligns = new int[]{0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,          //only 9 because qty_transfered_since_last_count is not display
                    0,0,0,0,0};
        }

        pw.applyColumnHeader(headerkeys, types, widths, aligns);
    	//DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        pw.setColumnDigit(13, 4);
		while(iterator.hasNext()) 
		{
			CabinetInventoryBean cabinetInventoryBean = (CabinetInventoryBean)iterator.next();

			pw.addRow();

			pw.addCell(cabinetInventoryBean.getCabinetName());
			pw.addCell(cabinetInventoryBean.getCountType());
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
			pw.addCell(cabinetInventoryBean.getReorderPoint());
			pw.addCell(cabinetInventoryBean.getStockingLevel());
			pw.addCell(cabinetInventoryBean.getReorderQuantity());
			pw.addCell(cabinetInventoryBean.getKanbanReorderQuantity());
			pw.addCell(cabinetInventoryBean.getLeadTimeDays());
			pw.addCell(cabinetInventoryBean.getItemId());
			if("A".equalsIgnoreCase(cabinetInventoryBean.getCpigStatus()))
				pw.addCell(library.getString("label.active"));
			else if("D".equalsIgnoreCase(cabinetInventoryBean.getCpigStatus()))
				pw.addCell(library.getString("label.drawdown"));
			else if("O".equalsIgnoreCase(cabinetInventoryBean.getCpigStatus()))
				pw.addCell(library.getString("label.obsolete"));
			else 
				pw.addCell("");
			pw.addCell(cabinetInventoryBean.getItemDesc());
			pw.addCell(cabinetInventoryBean.getMaterialIdString());
			pw.addCell(cabinetInventoryBean.getReceiptId());
			pw.addCell(cabinetInventoryBean.getMfgLot());
			pw.addCell(cabinetInventoryBean.getExpireDate());
			pw.addCell(cabinetInventoryBean.getQualityTrackingNumber());
			pw.addCell(cabinetInventoryBean.getCountDatetime());
			pw.addCell(cabinetInventoryBean.getCountQuantity());
			pw.addCell(cabinetInventoryBean.getQtyIssuedAfterCount());
            if (homeCompanyOwned) {
                pw.addCell(cabinetInventoryBean.getQtyTransferredAfterCount());
            }
            pw.addCell(cabinetInventoryBean.getTotalQuantity());
			if(cabinetInventoryBean.getUnitCost() == null) 
				pw.addCell("");
			else
				pw.addCell(cabinetInventoryBean.getCurrencyId());
			pw.addCell(cabinetInventoryBean.getUnitCost());
            pw.addCell(cabinetInventoryBean.getAreaName());
            pw.addCell(cabinetInventoryBean.getBuildingName());
            pw.addCell(cabinetInventoryBean.getTierIIStorage());
            pw.addCell(cabinetInventoryBean.getTierIiPressure());
            pw.addCell(cabinetInventoryBean.getTierIiTemperature());
            pw.addCell(cabinetInventoryBean.getPhysicalState());
            pw.addCell(cabinetInventoryBean.getPackaging());

        }
		return pw;
	}
	
  	private String getPropertyValue(String propertyName, ResourceLibrary library)
  	{
  		String propertyValue = propertyName; // just in case the propertyName is not found in the library;
  		try
  		{
  			propertyValue = library.getString( propertyName );
  		}
  		catch (Exception e)
  		{
  			log.debug("caught exception resolving property: [" + propertyName + "]; " + e.toString() );
  		}
  		return propertyValue;
  	}
	 
	//  the following is used by InventoryDetailsAction...
	
	public Collection getLogisticsViewBeanCollection(String itemIdString, String hub) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());
		LogisticsViewBeanFactory factory = new LogisticsViewBeanFactory(dbManager);
		
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("itemId", SearchCriterion.EQUALS, itemIdString );
		searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, hub );
		searchCriteria.addCriterion("quantity", SearchCriterion.NOT_EQUAL, "0" );
		// order by item_id,lot_status, receipt_id
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "itemId" );
		sortCriteria.addCriterion( "lotStatus" );
		sortCriteria.addCriterion( "receiptId" );
		Collection c = factory.select( searchCriteria, sortCriteria );
	   
		//log.debug("LogisticsViewBean collection size: [" + c.size() + "]; "); 
	    
		return c;
	}
}

