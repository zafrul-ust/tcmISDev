package com.tcmis.client.report.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.FacilityStockingLevelInputBean;
import com.tcmis.client.report.beans.FacilityStockingLevelViewBean;
import com.tcmis.common.admin.beans.FacilityBean;
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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

public class FacilityStockingLevelProcess extends BaseProcess {
	  Log log = LogFactory.getLog(this.getClass());

	  public FacilityStockingLevelProcess(String client) {
	    super(client);
	  }
		
	  public FacilityStockingLevelProcess(String client, String locale) {
	    super(client,locale);
	  }

	  public Collection<FacilityStockingLevelViewBean> getFacilityStockingLevel(FacilityStockingLevelInputBean inputSearchBean, PersonnelBean personnelBean)
		throws BaseException {

			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FacilityStockingLevelViewBean());
			SearchCriteria criteria = new SearchCriteria();
						
			if(!StringHandler.isBlankString(inputSearchBean.getFacilityId()))
				criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputSearchBean.getFacilityId());
			if(!StringHandler.isBlankString(inputSearchBean.getSearchText()))
				criteria.addCriterion("catPartNo", SearchCriterion.LIKE, inputSearchBean.getSearchText(),SearchCriterion.IGNORE_CASE);
			if(!StringHandler.isBlankString(personnelBean.getCompanyId()))
				criteria.addCriterion("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
			
			SortCriteria sort = new SortCriteria();
	        sort.setSortAscending(true);

	        sort.addCriterion("catPartNo");
	        return factory.select(criteria, sort, "facility_stocking_level_view");
		           			
      }
	  
	  public Collection<FacilityBean> getFacilities(PersonnelBean personnelBean)
		throws BaseException {

			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FacilityBean());
			
			String query = "select * from USER_FACILITY_VIEW where personnel_id = "+personnelBean.getPersonnelId();
			
			return factory.selectQuery(query);
	  }
	 
		
	  public ExcelHandler getExcelReport(Collection<FacilityStockingLevelViewBean> data, Locale locale) throws
	      NoDataException, BaseException, Exception {
	    
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
	//write column headers
		pw.addRow();
	/*Pass the header keys for the Excel.*/
			
		  
		 String[] headerkeys = {
	                "label.facility","label.partnumber","label.partdesc",
	                "label.itemdesc","label.packaging","label.minstockinglevel",
	                "label.maxstockinglevel","label.totalstockinglevel","label.avgstockinglevel",
	                "label.workareacount"
	        };

	        int[] types = {
	                0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH,0,0,0,0,0,0
	        };


	        int[] widths = {
	                0,0,20,20,15,0,0,0,0,0
	        };

	        int[] horizAligns = {
	                0,0,0,0,0,0,0,0,0,0
	        };

	        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		
		
		// format the numbers to the special columns
		

		for (FacilityStockingLevelViewBean member : data) {
		  
	      pw.addRow();
	      pw.addCell(member.getFacilityName());
	      pw.addCell(member.getCatPartNo());      
	      pw.addCell(member.getPartDescription());
	      pw.addCell(member.getItemDesc());
	      pw.addCell(member.getPurchasePackaging());
	      pw.addCell(member.getMinStockingLevel());
	      pw.addCell(member.getMaxStockingLevel());
	      pw.addCell(member.getTotalStockingLevel());
	      pw.addCell(member.getAvgStockingLevel());
	      pw.addCell(member.getWorkAreaCount());
	            
	    
	    }
	    return pw;
	  }
	  
	}

