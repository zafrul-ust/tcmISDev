package com.tcmis.internal.hub.process;

import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.beans.VvLotStatusBean;

/******************************************************************************
 * Process for InventoryReport
 * @version 1.0
 *****************************************************************************/
public class InventoryReportProcess
    extends GenericProcess {
  Log log = LogFactory.getLog(this.getClass());

  public InventoryReportProcess(String client,Locale locale) {
    super(client,locale);
  }
  
  public Vector getInventoryReport(LogisticsInputBean inputBean,PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new LogisticsViewBean());
		
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("quantity", SearchCriterion.NOT_EQUAL,"0");
		if(!StringHandler.isBlankString(inputBean.getOpsEntityId())) 
			searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		if(!StringHandler.isBlankString(inputBean.getHub())) 
			searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
	    if(!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
	        searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
	    }
	    else {
	    	String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
	    	if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
	    		invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
	    	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
	    }
	    if( inputBean.getRoom() != null && inputBean.getRoom().length() != 0 ) {
	    	searchCriteria.addCriterion("room", SearchCriterion.EQUALS, inputBean.getRoom());
	    }
	    
	    if (!StringHandler.isBlankString(inputBean.getBinfrom())) 
			searchCriteria.addCriterion("bin", SearchCriterion.GREATER_THAN_OR_EQUAL_TO,inputBean.getBinfrom(), SearchCriterion.IGNORE_CASE);
		
		if (!StringHandler.isBlankString(inputBean.getBinto())) {
			StringBuilder binTo = new StringBuilder(inputBean.getBinto());
			binTo.append("ZZZ");
			searchCriteria.addCriterion("bin", SearchCriterion.LESS_THAN_OR_EQUAL_TO,binTo.toString(), SearchCriterion.IGNORE_CASE);
		}
		
	    boolean containsAll = false;
	    String[] values = inputBean.getLotStatus();
	    if( values != null )
	    	for(int i = 0 ; i < values.length;i++)
	    		if( "".equals(values[i]) ) {
	    			containsAll = true;
	    			break;
	    		}
	    if( "Inclusive".equals(inputBean.getCheckbox() ) ) {
	    	if( !containsAll ) 
	        {
	    		searchCriteria.addCriterionArray("lotStatus", SearchCriterion.IN, inputBean.getLotStatus());
	    	}
	    }
	    else {
	    		searchCriteria.addCriterionArray("lotStatus", SearchCriterion.NOT_IN, inputBean.getLotStatus());
	    }

	    if( inputBean.getDorfrom() != null )
	    	searchCriteria.addCriterion("dateOfReceipt", SearchCriterion.FROM_DATE,inputBean.getDorfrom() );
	    if( inputBean.getDorto() != null )
	    	searchCriteria.addCriterion("dateOfReceipt", SearchCriterion.TO_DATE,inputBean.getDorto());
	    

	    Vector<LogisticsViewBean> v = (Vector<LogisticsViewBean>)factory.select(searchCriteria, null,"logistics_view");//;,"LOGISTICS_VIEW");
	    return v;
  	}
  
  	public Vector getLotStatusLegend() throws BaseException {
		DbManager dbManager = new DbManager(getClient());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VvLotStatusBean());
		
		String query = "select LOT_STATUS, decode(PICKABLE,'N','No','Yes') pickable, ALLOCATION_ORDER, SCRAP_ALLOCATION_ORDER, DESCRIPTION, jsp_label " +
					   "from vv_lot_status where active = 'Y' order by lot_status";
		Vector<VvLotStatusBean> v = (Vector<VvLotStatusBean>)factory.selectQuery(query);
		return v;
	}

	public ExcelHandler getInventoryReportExcel(Vector<LogisticsViewBean> data,PersonnelBean personnelBean) throws
	  NoDataException, BaseException, Exception {
	
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		
		ExcelHandler pw = new ExcelHandler(library);
		
		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
		"label.operatingentity","label.hub","label.inventorygroup", "label.item","label.partno",
		"label.description","label.specs","label.receiptid", "label.onhand", "label.currencyid",
		"label.cost","label.lotstatus","label.mfglot","label.bin","label.expiredate",
		"label.dor","receiving.label.deliveryticket","label.comments" };
		/*This array defines the type of the excel column.
		0 means default depending on the data type. */
		int[] types = {
		0,0,0,0,0,
		pw.TYPE_PARAGRAPH, 0,0,pw.TYPE_NUMBER,0,
		0,0,0,0,pw.TYPE_CALENDAR,
		pw.TYPE_CALENDAR,0,pw.TYPE_PARAGRAPH
		};
		
		int[] widths = { 10 };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		
		pw.applyColumnHeader(headerkeys, types, widths, null );
		
		// now write data
		pw.setColumnDigit(10, 4);
		
		String indefinite = library.getString("label.indefinite");
		for (LogisticsViewBean member : data) {
		
		  pw.addRow();
		
		  pw.addCell(member.getOperatingEntityName());
		  pw.addCell(member.getHub());
		  pw.addCell(member.getInventoryGroup());
		  pw.addCell(member.getItemId());
		  pw.addCell(member.getClientPartNos());
		  pw.addCell(!StringHandler.isBlankString(member.getPartShortName())?member.getPartShortName():member.getItemDesc());
		  pw.addCell(member.getSpecs());
		  pw.addCell(member.getReceiptId());
		  pw.addCell(member.getQuantity());
		  if(member.getUnitCost() == null) 
			  pw.addCell("");
		  else
			  pw.addCell(member.getCurrencyId());
		  pw.addCell(member.getUnitCost());
		  pw.addCell(member.getLotStatus());
		  pw.addCell(member.getMfgLot());
		  pw.addCell(member.getBin());
		//  pw.addCell((member.getExpireDate().getYear()==1100)?indefinite:member.getExpireDate());
		  pw.addCell(member.getExpireDate());
		  pw.addCell(member.getDateOfReceipt());
		  pw.addCell(member.getDeliveryTicket());
		  if(!"<BR>".equals(member.getNotes()))
			  pw.addCell(member.getNotes());
		  else
			  pw.addCell("");   
		}
		return pw;
	}

}
