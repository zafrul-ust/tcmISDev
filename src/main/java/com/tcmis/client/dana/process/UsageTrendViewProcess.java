package com.tcmis.client.dana.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.dana.beans.UsageTrendViewBean;
import com.tcmis.client.dana.beans.UsageTrendViewInputBean;
import com.tcmis.client.dana.factory.FacilityAniversaryDateViewBeanFactory;
import com.tcmis.client.dana.factory.UsageTrendViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
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

 public Collection getsearchResult(UsageTrendViewInputBean bean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());

	UsageTrendViewBeanFactory factory = new UsageTrendViewBeanFactory(dbManager);
	/*SearchCriteria criteria = new SearchCriteria();
	 criteria.addCriterion("facilityId",
			SearchCriterion.EQUALS,
			bean.getfacilityId());*/

	return factory.select(bean);
 }

 public Collection getDistinctFacilities() throws BaseException {
	DbManager dbManager = new DbManager(getClient());
	FacilityAniversaryDateViewBeanFactory factory = new
	 FacilityAniversaryDateViewBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();

	criteria.addCriterion("aniversaryDate", SearchCriterion.IS_NOT, "null");

	return factory.selectDistinctFacility(criteria);
 }

 public Collection getStartDates(UsageTrendViewInputBean bean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
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

 public void writeExcelFile(Collection searchCollection, String filePath,
	String reportStartDateO,java.util.Locale locale) throws BaseException, Exception {
	if (log.isDebugEnabled()) {
	 log.debug("calling excel report process");
	}
	 ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();

//	  write column headers
	  pw.addRow();
	  pw.addRow();
	  pw.addThRegion("label.item", 2,1);
	  pw.addThRegion("label.desc", 2,1);
	  pw.addThRegion("label.mfg", 2,1);
	  pw.addThRegion("label.packaging", 2,1);
	  pw.addThRegion("label.countuom", 2,1);
	  pw.addThRegion("label.anualbaseline", 2,1);
	  pw.addThRegion("label.prioryear", 2,1);
	  pw.addThRegion("label.ytd", 1,3);
	  pw.addThRegion("label.current", 1,12);
	  pw.addRow();
	  pw.addTdEmpty();
	  pw.addTdEmpty();
	  pw.addTdEmpty();
	  pw.addTdEmpty();
	  pw.addTdEmpty();
	  pw.addTdEmpty();
	  pw.addTdEmpty();
	  pw.addCellKeyBold("label.bl");
	  pw.addCellKeyBold("label.prioryear");
	  pw.addCellKeyBold("label.curyear");

	/*SimpleDateFormat simpleDateFormat = null;
	simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Timestamp timeStamp = null;
	try {
	 timeStamp = new Timestamp(simpleDateFormat.parse(reportStartDateO).getTime());
	}
	catch (ParseException ex) {
	}

	SimpleDateFormat finalDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	String reportStartDate = finalDateFormat.format(timeStamp);*/
	String reportStartDate = reportStartDateO;

	int startYear = Integer.parseInt(reportStartDate.substring(6, 10));
	int startMonth = (Integer.parseInt(reportStartDate.substring(0, 2))) - 1;
	int startDay = Integer.parseInt(reportStartDate.substring(3, 5));

	GregorianCalendar calendar = new GregorianCalendar(startYear, startMonth,
	 startDay);
	SimpleDateFormat sdf = new SimpleDateFormat("MMM");
	SimpleDateFormat sdfyy = new SimpleDateFormat("yy");

	int monthCount = 0;
	while (monthCount < 12) {
	 monthCount++;
	 String monthName = sdf.format(calendar.getTime());
	 String year = sdfyy.format(calendar.getTime());

	 pw.addCellBold( monthName + " " + year );
	 calendar.add(2, 1);
	}

	//print rows
	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
		pw.addRow();

	 UsageTrendViewBean UsageTrendViewBean = (UsageTrendViewBean) i11.next(); ;

	 pw.addCell(UsageTrendViewBean.getItemId());
	 pw.addCell(UsageTrendViewBean.getItemDesc());
	 pw.addCell(UsageTrendViewBean.getManufacturer());
	 pw.addCell(UsageTrendViewBean.getPackaging());
	 pw.addCell(UsageTrendViewBean.getCountUom());
	 pw.addCell(UsageTrendViewBean.getBaselineAnnualUsage());
	 pw.addCell(UsageTrendViewBean.getPriorYearUsage());
	 pw.addCell(UsageTrendViewBean.getBaselineAnnualUsageYtd());
	 pw.addCell(UsageTrendViewBean.getPriorYearUsageYtd());
	 pw.addCell(UsageTrendViewBean.getCurrentYearYtd());
	 pw.addCell(UsageTrendViewBean.getMonth0());
	 pw.addCell(UsageTrendViewBean.getMonth1());
	 pw.addCell(UsageTrendViewBean.getMonth2());
	 pw.addCell(UsageTrendViewBean.getMonth3());
	 pw.addCell(UsageTrendViewBean.getMonth4());
	 pw.addCell(UsageTrendViewBean.getMonth5());
	 pw.addCell(UsageTrendViewBean.getMonth6());
	 pw.addCell(UsageTrendViewBean.getMonth7());
	 pw.addCell(UsageTrendViewBean.getMonth8());
	 pw.addCell(UsageTrendViewBean.getMonth9());
	 pw.addCell(UsageTrendViewBean.getMonth10());
	 pw.addCell(UsageTrendViewBean.getMonth11());
	}
	pw.write(new FileOutputStream(filePath));
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
