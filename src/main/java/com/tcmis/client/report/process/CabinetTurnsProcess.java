package com.tcmis.client.report.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.client.report.beans.InventoryTurnReportTblBean;
import com.tcmis.client.report.beans.InventoryTurnTotalsBean;
import com.tcmis.client.report.factory.DivFacGrpFacReAppOvBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.PrintInvoiceTotalsBean;


public class CabinetTurnsProcess  extends BaseProcess {
      
	
	  public CabinetTurnsProcess(String client) {
	    super(client);
	  }
		
	  public CabinetTurnsProcess(String client, String locale) {
	    super(client,locale);
	  }
	  
	  private String ShowTurnsSummary = "";
	  
	  public Collection getDropDowns(String companyId) throws BaseException
	  {
		  DivFacGrpFacReAppOvBeanFactory divFacGrpFacOvBeanFactory = new DivFacGrpFacReAppOvBeanFactory(new DbManager(getClient(),getLocale()));
		  SearchCriteria searchCriteria = new SearchCriteria();
		  searchCriteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);	 
		  return  divFacGrpFacOvBeanFactory.selectObject(searchCriteria);
	  }
	  
	  public Collection<InventoryTurnReportTblBean> getSearchResults(InventoryTurnReportTblBean bean, PersonnelBean personnelBean)
		throws BaseException {

		    DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new InventoryTurnReportTblBean());
			Collection<InventoryTurnReportTblBean> c = null;
			
			StringBuilder query= new StringBuilder("select * from table (pkg_cabinet_report.fx_inventory_turn_report(");
			query.append( "'" ).append(StringHandler.emptyIfNull(bean.getCompanyId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getDivisionId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getFacilityGroupId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getFacilityId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getReportingEntityId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getApplication())).append("'," ).append(25)
			.append(",'").append(StringHandler.emptyIfNull(bean.getOnlyWithBin())).append("'" ).append( "))");

            if(personnelBean.isFeatureReleased("ShowTurnsSummary", bean.getFacilityId(),bean.getCompanyId()))
			{
				query.append(" order by application");
				ShowTurnsSummary = "Yes";
			}
			
			c = factory.selectQuery(query.toString());

			return c;
	  }
	  
	  public ExcelHandler getExcelReport(Collection<InventoryTurnReportTblBean> data, Locale locale) throws
      		NoDataException, BaseException, Exception {
		 		      
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
			BigDecimal total = new BigDecimal("0");
			Collection applicationColl = new Vector();
			Hashtable applicationTotals = new Hashtable();
			pw.addTable();
		//write column headers
			pw.addRow();
		/*Pass the header keys for the Excel.*/
			String[] headerkeys = {
		 	"label.company","label.division","label.facilitygroup","label.facility","label.workareagroup",
		 	"label.workarea","label.catalog","label.partnumber", "label.reorderpoint", "label.stockinglevel",
		 	"label.reorderquantity","label.kanbanreorderqty","label.description","label.lastscan","label.lastusedqty",
		 	"label.m1","label.m2","label.m3","label.m4","label.m5","label.m6",
		 	"label.halfyeartotal","label.avguseperweek","label.turns"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type. 
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = {
			  0,0,0,0,0,
			  0,0,0,0,0,
			  0,0,pw.TYPE_PARAGRAPH,pw.TYPE_CALENDAR,0,
			  0,0,0,0,0,0,
			  0,0,0};
			
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = {
					  10,0,10,0,0,
					  15,0,0,0,0,
					  0,0,20,0,0,
					  0,0,0,0,0,0,
					  0,0,0};
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = {
					  0,0,0,0,0,
					  0,0,0,0,0,
					  0,0,0,0,0,
					  0,0,0,0,0,0,
					  0,0,0};
			  
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
			
			// format the numbers to the special columns
			pw.setColumnDigit(22, 2);
			pw.setColumnDigit(23, 2);
		
		    
			for (InventoryTurnReportTblBean member : data) {
					  			
				  pw.addRow();
			      pw.addCell(member.getCompanyName());
			      pw.addCell(member.getDivisionDescription());
			      pw.addCell(member.getFacilityGroupDescription());
			      pw.addCell(member.getFacilityName());
			      pw.addCell(member.getReportingEntityDescription());
			      pw.addCell(member.getApplicationDesc());
			      pw.addCell(member.getCatalogDesc());
			      pw.addCell(member.getCatPartNo());
			      pw.addCell(member.getReorderPoint());
			      pw.addCell(member.getStockingLevel());
			      pw.addCell(member.getReorderQuantity());
			      pw.addCell(member.getKanbanReorderQuantity());
			      pw.addCell(member.getPartDescription());
			      pw.addCell(member.getLastCounted());
			      pw.addCell(member.getLastUsedQty());
			      pw.addCell(member.getUsedMonth1());
			      pw.addCell(member.getUsedMonth2());
			      pw.addCell(member.getUsedMonth3());
			      pw.addCell(member.getUsedMonth4());
			      pw.addCell(member.getUsedMonth5());
			      pw.addCell(member.getUsedMonth6());
			      pw.addCell(member.getUsedHalfYear());
			      pw.addCell(member.getAvgUsePerWeek());
			      pw.addCell(member.getTurnsLastYear());
			      
			      if(ShowTurnsSummary.equalsIgnoreCase("Yes"))
				      {
				      String application = member.getApplication() == null ? "" : member.getApplication();
	
					  if (!applicationColl.contains(application)) {
						  InventoryTurnTotalsBean totalsBean2 = new InventoryTurnTotalsBean();
						  totalsBean2.setApplication(application);
						  			  
	
						  applicationTotals.put(application,totalsBean2);
						  applicationColl.add(application);
					  }
	
						  InventoryTurnTotalsBean totalsBean = (InventoryTurnTotalsBean)applicationTotals.get(application);
		                  
						  totalsBean.setApplicationDesc(member.getApplicationDesc());
						  totalsBean.setStockingLevel(member.getStockingLevel());
						  totalsBean.setLastUsedQty(member.getLastUsedQty());
						  totalsBean.setUsedMonth1(member.getUsedMonth1());
						  totalsBean.setUsedMonth2(member.getUsedMonth2());
						  totalsBean.setUsedMonth3(member.getUsedMonth3());
						  totalsBean.setUsedMonth4(member.getUsedMonth4());
						  totalsBean.setUsedMonth5(member.getUsedMonth5());
						  totalsBean.setUsedMonth6(member.getUsedMonth6());
						  totalsBean.setUsedHalfYear(member.getUsedHalfYear());
						  totalsBean.setAvgUsePerWeek(member.getAvgUsePerWeek());
						  totalsBean.setTurnsLastYear(member.getTurnsLastYear());
						  totalsBean.setRecords(new BigDecimal("1"));
						  
					 }
			     } 
				try {
					 Enumeration E;
					 if(ShowTurnsSummary.equalsIgnoreCase("Yes"))
					 {
					  pw.addRow();
					  pw.addTdEmpty(5);
					  pw.addTh("label.total");
					   for (E = applicationTotals.keys(); E.hasMoreElements(); ) {
					   String key = (String) E.nextElement();
					   InventoryTurnTotalsBean totalsBean = (InventoryTurnTotalsBean)applicationTotals.get(key);

					      pw.addRow();
			    		  pw.addCell("");
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell(totalsBean.getApplicationDesc());
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell(totalsBean.getStockingLevel());
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell("");
					      pw.addCell(totalsBean.getLastUsedQty());
					      pw.addCell(totalsBean.getUsedMonth1());
					      pw.addCell(totalsBean.getUsedMonth2());
					      pw.addCell(totalsBean.getUsedMonth3());
					      pw.addCell(totalsBean.getUsedMonth4());
					      pw.addCell(totalsBean.getUsedMonth5());
					      pw.addCell(totalsBean.getUsedMonth6());
					      pw.addCell(totalsBean.getUsedHalfYear());
					      pw.addCell(totalsBean.getAvgUsePerWeek());
					      BigDecimal turns = new BigDecimal("0");
					      if(!totalsBean.getTurnsLastYear().equals(new BigDecimal("0")))
					      {turns = totalsBean.getTurnsLastYear().divide(totalsBean.getRecords(), 2);}
					        else
					      {turns = totalsBean.getTurnsLastYear();}	
					      pw.addCell(turns);
					    }
					 }
				}
				catch (Exception e) {

				  }
						
				return pw;
			}

}