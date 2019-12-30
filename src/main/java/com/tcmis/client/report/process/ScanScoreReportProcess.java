package com.tcmis.client.report.process;

import java.util.Collection;
import java.util.Vector;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.report.beans.ScanScoreInputBean;
import com.tcmis.client.report.beans.ScanScoreReportViewBean;
import com.tcmis.client.report.factory.DivFacGrpFacOvBeanFactory;
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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;


public class ScanScoreReportProcess  extends BaseProcess {

	  public ScanScoreReportProcess(String client) {
	    super(client);
	  }
		
	  public ScanScoreReportProcess(String client, String locale) {
	    super(client,locale);
	  }
	  
	  public Collection<ScanScoreReportViewBean> getScanScoreReport(ScanScoreInputBean bean, PersonnelBean personnelBean)
		throws BaseException {

		  DbManager dbManager = new DbManager(getClient(),getLocale());
			 GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ScanScoreReportViewBean());
			Collection<ScanScoreReportViewBean> c = null;
			String div = "";
			String fac_Group = "";
						
			StringBuilder query= new StringBuilder("select * from table (pkg_cabinet_report.fx_scan_score_report(");
			query.append( "'" ).append(personnelBean.getCompanyId()).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getDivisionId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getFacilityGroupId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getFacilityId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getReportingEntityId())).append("'" )
			.append(",'").append(StringHandler.emptyIfNull(bean.getApplication())).append("'" )
		    .append(",'" ).append(3).append("'" )
		    .append(",'").append(StringHandler.emptyIfNull(bean.getOnlyWithBin())).append("'" )
			.append( "))");
			
			c = factory.selectQuery(query.toString());
			
			if(c.size() > 1)
			{
				Vector tmp = (Vector)c;
				ScanScoreReportViewBean tmpBean = (ScanScoreReportViewBean)tmp.remove(0);
				tmpBean.setCompanyName("");
				tmpBean.setFacilityGroupDescription("");
				tmpBean.setFacilityName("All");
				tmp.add(tmpBean);
			}
			
			return c;
	 }
	  
	  public Collection getDropDowns(String companyId) throws BaseException
	  {
		  DivFacGrpFacReAppOvBeanFactory divFacGrpFacOvBeanFactory = new DivFacGrpFacReAppOvBeanFactory(new DbManager(getClient(),getLocale()));
		  SearchCriteria searchCriteria = new SearchCriteria();
		  searchCriteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);	 
		  return  divFacGrpFacOvBeanFactory.selectObject(searchCriteria);
	  }
	 	 		 
	  
 public ExcelHandler getExcelReport(Collection<ScanScoreReportViewBean> data, Locale locale) throws
      NoDataException, BaseException, Exception {
    
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
//write column headers
	pw.addRow();
/*Pass the header keys for the Excel.*/
	String[] headerkeys = {
 	"label.company","label.division","label.facilitygroup","label.facility","label.workareagroup","label.workarea",
 	"label.totalbins","label.7daysscan","label.7daysper","label.14daysscan","label.14daysper","label.30daysscan","label.30daysper","label.90daysscan","label.90daysper","label.180daysscan","label.180daysper" };
	/*This array defines the type of the excel column.
	0 means default depending on the data type. 
	pw.TYPE_PARAGRAPH defaults to 40 characters.
	pw.TYPE_CALENDAR set up the date with no time.
	pw.TYPE_DATE set up the date with time.*/
	int[] types = {
	  0,pw.TYPE_NUMBER,pw.TYPE_PARAGRAPH,0
	  };
	
	/*This array defines the default width of the column when the Excel file opens.
	0 means the width will be default depending on the data type.*/
	int[] widths = {
	  15,15,15,15,15,15,10,10,10,10,10,10,10,10,10,10,10
	  };
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	int[] horizAligns = {
	  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
	  };
	  
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
	// format the numbers to the special columns
	// pw.setColumnDigit(6, 2);
	// pw.setColumnDigit(7, 2);

	for (ScanScoreReportViewBean member : data) {
	  
      pw.addRow();
      pw.addCell(member.getCompanyName());
      pw.addCell(member.getDivisionDescription());
      pw.addCell(member.getFacilityGroupDescription());
      pw.addCell(member.getFacilityName());
      pw.addCell(member.getReportingEntityDescription());
      pw.addCell(member.getApplicationDesc());
      pw.addCell(member.getBinTotal());
      pw.addCell(member.getCounted7());
      pw.addCell(member.getScore7());
      pw.addCell(member.getCounted14());
      pw.addCell(member.getScore14());
      pw.addCell(member.getCounted30());
      pw.addCell(member.getScore30());
      pw.addCell(member.getCounted90());
      pw.addCell(member.getScore90());
      pw.addCell(member.getCounted180());
      pw.addCell(member.getScore180());
    
    }
    return pw;
  }
  
	    
}

