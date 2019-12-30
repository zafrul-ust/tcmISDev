package com.tcmis.internal.hub.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.dana.factory.FacilityAniversaryDateViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.UsageTrendViewBean;
import com.tcmis.internal.hub.beans.UsageTrendViewInputBean;
import com.tcmis.internal.hub.factory.UsageTrendViewBeanFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.ExcelHandler;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class UsageTrendViewProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public UsageTrendViewProcess(String client) {
	super(client);
 }
 
 public UsageTrendViewProcess(String client,String locale) {
	    super(client,locale);
}

 public Collection getsearchResult(UsageTrendViewInputBean bean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());

	UsageTrendViewBeanFactory factory = new UsageTrendViewBeanFactory(dbManager);
	/*SearchCriteria criteria = new SearchCriteria();
	 criteria.addCriterion("facilityId",
			SearchCriterion.EQUALS,
			bean.getfacilityId());*/

	return factory.select(bean);
 }

 public Collection getDistinctFacilities() throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	FacilityAniversaryDateViewBeanFactory factory = new
	 FacilityAniversaryDateViewBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();

	criteria.addCriterion("aniversaryDate", SearchCriterion.IS_NOT, "null");

	return factory.selectDistinctFacility(criteria);
 }

 public Collection getStartDates(UsageTrendViewInputBean bean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	FacilityAniversaryDateViewBeanFactory factory = new
	 FacilityAniversaryDateViewBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();
	//add inventory group to criteria if not "All"
	if (bean.getfacilityId() != null &&
	 !bean.getfacilityId().equalsIgnoreCase("ALL") &&
	 bean.getfacilityId().length() > 0) {
	 criteria.addCriterion("facilityId", SearchCriterion.EQUALS,
		bean.getfacilityId());
	}

	criteria.addCriterion("aniversaryDate", SearchCriterion.IS_NOT, "null");

	return factory.select(criteria);
 }

 public ExcelHandler writeExcelFile(Collection searchCollection,Date reportStartDateO, Locale locale) throws BaseException, Exception {
	if (log.isDebugEnabled()) {
	 log.debug("calling excel report process");
	}
	 ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  ExcelHandler pw = new ExcelHandler(library);
	 
	pw.addTable();
	pw.addRow();
	pw.addThRegion("label.item",2,1);
	pw.addThRegion("label.desc",2,1);
	pw.addThRegion("label.mfg",2,1);
	pw.addThRegion("label.packaging",2,1);
	pw.addThRegion("label.countuom",2,1);
	pw.addThRegion("label.anualbaseline",2,1);
	pw.addThRegion("label.prioryear",2,1);
	pw.addThRegion("label.ytd",1,3);
	pw.addThRegion("label.current",1,12);

	pw.addRow();
//	pw.addCell("");pw.addCell("");pw.addCell("");pw.addCell("");pw.addCell("");pw.addCell("");pw.addCell("");
	pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();
	pw.addCellKeyBold("label.bl");
	pw.addCellKeyBold("label.prioryear");
	pw.addCellKeyBold("label.curyear");

//dd-MMM-yyyy
  Date reportStartDate = reportStartDateO;

/*	int startYear = Integer.parseInt(reportStartDate.substring(6, 10));
	int startMonth = (Integer.parseInt(reportStartDate.substring(0, 2))) - 1;
	int startDay = Integer.parseInt(reportStartDate.substring(3, 5));

	GregorianCalendar calendar = new GregorianCalendar(startYear, startMonth,
	 startDay);
*/
  	GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance(locale);
	calendar.setTime(reportStartDate);
	
	SimpleDateFormat sdf = new SimpleDateFormat("MMM",locale);
	SimpleDateFormat sdfyy = new SimpleDateFormat("yy",locale);

	int monthCount = 0;
	while (monthCount < 12) {
	 monthCount++;
	 String monthName = sdf.format(calendar.getTime());
	 String year = sdfyy.format(calendar.getTime());

	 pw.addCellBold( monthName + " " + year );

	 calendar.add(2, 1);
	}
	
	//Set format 
	String[] headerkeys = {
		      "label.item","label.desc","label.mfg","label.packaging","label.countuom",
		      "label.prioryear","label.bl","label.current","label.prioryear","label.curyear",
		      "label.jun","label.jul","label.aug","label.sep","label.oct",
		      "label.nov","label.dec","label.jan","label.feb","label.mar",
		      "label.apr","label.may"};
	int[] types = {
		      0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,0,
		      pw.TYPE_NUMBER,pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER,pw.TYPE_NUMBER,
		      0,0,0,0,0,
		      0,0,0,0,0,
		      0,0
		      };
		   
		    int[] widths = {
		      0,0,0,0,12,
		      12,12,0,11,10,
		      0,0,0,0,0,
		      0,0,0,0,0,
		      0,0};
		   
		    int[] horizAligns = {
		      pw.ALIGN_CENTER,0,0,0,pw.ALIGN_CENTER,
		      0,0,0,0,0,
		      0,0,0,0,0,
		      0,0,0,0,0,
		      0,0};
	      
   pw.setColumnHeader(headerkeys,types, widths, horizAligns);
   pw.applyColumnWidth();
   
	//print rows
	Collection<UsageTrendViewBean> data = searchCollection;
	
	for (UsageTrendViewBean member : data) {
		 pw.addRow();

		 pw.addCell(member.getItemId());
		 pw.addCell(member.getItemDesc());
		 pw.addCell(member.getManufacturer());
		 pw.addCell(member.getPackaging());
		 pw.addCell(member.getCountUom());
		 
		 pw.addCell(member.getBaselineAnnualUsage());
		 pw.addCell(member.getPriorYearUsage());
		 pw.addCell(member.getBaselineAnnualUsageYtd());
		 pw.addCell(member.getPriorYearUsageYtd());
		 pw.addCell(member.getCurrentYearYtd());
		 
		 pw.addCell(member.getMonth0());
		 pw.addCell(member.getMonth1());
		 pw.addCell(member.getMonth2());
		 pw.addCell(member.getMonth3());
		 pw.addCell(member.getMonth4());
		 pw.addCell(member.getMonth5());
		 pw.addCell(member.getMonth6());
		 pw.addCell(member.getMonth7());
		 pw.addCell(member.getMonth8());
		 pw.addCell(member.getMonth9());
		 pw.addCell(member.getMonth10());
		 pw.addCell(member.getMonth11());
	}
 	return pw;
 }

 private boolean validateInput(UsageTrendViewInputBean bean) {
	/*
	 if (bean == null ||
	 bean.getCompanyId() == null || bean.getCompanyId().trim().length() < 1 ||
	 bean.getPersonnelId() == 0 ||
	 bean.getQueryName() == null || bean.getQueryName().trim().length() < 1 ||
	 bean.getQuery() == null || bean.getQuery().trim().length() < 1) {
		return false;
	 }
	 else {
		return true;
	 }
	 */
	return true;
 }

}
