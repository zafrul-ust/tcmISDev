package com.tcmis.client.report.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.AdHocTemplateBean;
import com.tcmis.client.report.beans.AdHocUsageInputBean;
import com.tcmis.client.report.beans.BaseFieldViewBean;
import com.tcmis.client.report.beans.FacAppDockDpViewBean;
import com.tcmis.client.report.beans.FacAppReportViewBean;
import com.tcmis.client.report.beans.UsageReportTemplateBean;
import com.tcmis.client.report.factory.AdHocReportDataMapper;
import com.tcmis.client.report.factory.IAdHocReportDataMapper;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseExcelReportProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandlerSXSSF;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Process to create Ad Hoc Usage report
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class AdHocUsageReportProcess extends BaseExcelReportProcess {
	final String ENHANCED_REPORTING = "EnhancedReportingServices";
	Log log = LogFactory.getLog(this.getClass());
	UsageReportTemplateBean bean;
	PersonnelBean personnelBean;
	//Writer writer;
	OutputStream os;
	OutputStreamWriter writer;
	Locale locale;
	ResourceLibrary library;
	final String separator = "|";
	final String separatorRegex = "\\|";
	String[] reportFields = null;
	String reportFieldString = "";
	String templateName = "null";
	String reportType = "AdHocUsage";
	String chemicalListFormatString = "";
	String chemicalListString = "";
	IAdHocReportDataMapper dataMapper;

    boolean interactiveTimeout;
	ExcelHandlerSXSSF pw;

    public AdHocUsageReportProcess(String client,Locale locale) {
		super(client);
		this.locale = locale;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}
    
    public AdHocUsageReportProcess(String client,Locale locale,IAdHocReportDataMapper dataMapper) {
		this(client, locale);
		this.dataMapper = dataMapper;
	}
    
    private IAdHocReportDataMapper getDataMapper() {
    	if (dataMapper == null) {
    		dataMapper = new AdHocReportDataMapper(new DbManager(getClient(), getLocale()));
    	}
    	return dataMapper;
    }

    public boolean runReport(UsageReportTemplateBean bean, PersonnelBean personnelBean, OutputStream os) throws BaseException, Exception {
		interactiveTimeout = false;
		this.bean = bean;
		this.personnelBean = personnelBean;
		this.os = os;
		writer = new OutputStreamWriter(os);
		if ("batch".equalsIgnoreCase(bean.getReportGenerationType())) {
			interactiveTimeout = true;
			BatchReport bp = new BatchReport();
			new Thread(bp).start();
			writer.write("<html>");
			writer.write(library.getString("label.batchreportmessage") + " " + personnelBean.getEmail());
			writer.write("</html>");
			writer.close();
		} else {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Future<?> future = executor.submit(new InteractiveReport());
			try {
				future.get(this.getReportInteractiveSleepTime(), TimeUnit.SECONDS); // set timeout
				interactiveTimeout = false;
			}catch(TimeoutException e) {
				synchronized(this) {
					if ( ! interactiveTimeout) { // if interactiveTimeout has been set, the report is ready, so don't wait
						interactiveTimeout = true;
						writer.write("<html>");
						writer.write(library.getString("label.interactivereporttimeoutmessage") + "<br />");
						writer.write(library.getString("label.batchreportmessage") + " " + personnelBean.getEmail());
						writer.write("</html>");
						writer.close();
					}else {
						interactiveTimeout = false;
					}
				}
			}catch (Exception e) {
				log.fatal("error in thread:" + e.getMessage(), e);
			}
		}
		
		return interactiveTimeout;
	}


	public void convertDateStringToDateObject(AdHocUsageInputBean bean, UsageReportTemplateBean templateBean) {
		SimpleDateFormat dateParser = new SimpleDateFormat(library.getString("java.dateformat"),locale);
		dateParser.setLenient(false);
		try {
			if (bean.getBeginDateJsp() != null){
				templateBean.setBeginDateJsp(dateParser.parse(bean.getBeginDateJsp()));
			}
			if (bean.getEndDateJsp() != null){
				templateBean.setEndDateJsp(dateParser.parse(bean.getEndDateJsp()));
			}
		}catch (Exception e) {

		}
	}

	public void getDefaultReportDate(AdHocUsageInputBean bean) {
		SimpleDateFormat dateParser = new SimpleDateFormat(library.getString("java.dateformat"),locale);
		dateParser.setLenient(false);
		if (StringHandler.isBlankString(bean.getBeginDateJsp())){
			//get begin date this month of last year
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR),Calendar.JANUARY,1);
			bean.setBeginDateJsp(dateParser.format(cal.getTime()));
		}
		if (StringHandler.isBlankString(bean.getEndDateJsp())){
			//get end date today
			Calendar cal = Calendar.getInstance();
			/*if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
				cal.set(cal.get(Calendar.YEAR)-1,Calendar.DECEMBER,31);
			}else {
				cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1,1);
			}  */
			bean.setEndDateJsp(dateParser.format(cal.getTime()));
		}
	}

	public Collection getReportFieldNameFromId(Collection reportFieldList, String[] selectedReportFields) {
		Collection result = new Vector(selectedReportFields.length);
		for (int i = 0; i < selectedReportFields.length; i++) {
			String fieldId = selectedReportFields[i];
			Iterator iter = reportFieldList.iterator();
			while (iter.hasNext()) {
				BaseFieldViewBean tmpBean = (BaseFieldViewBean)iter.next();
				if (fieldId.equals(tmpBean.getBaseFieldId().toString())) {
					BaseFieldViewBean bean = new BaseFieldViewBean();
					bean.setBaseFieldId(tmpBean.getBaseFieldId());
					bean.setDescription(tmpBean.getDescription());
					bean.setName(tmpBean.getName());
					result.add(bean);
					break;
				}
			}
		}
		return result;
	}

	public void copyData(Collection baseFieldBeanCollection,Collection listOptionBeanCollection,Collection templateData, AdHocUsageInputBean bean) {
		SimpleDateFormat dateParser = new SimpleDateFormat(library.getString("java.dateformat"),locale);
		dateParser.setLenient(false);
		Iterator iter = templateData.iterator();
		while (iter.hasNext()) {
			AdHocTemplateBean tempBean = (AdHocTemplateBean)iter.next();
			bean.setTemplateName(tempBean.getTemplateName());
			bean.setReportType(tempBean.getQueryType());   			//list, singleChemical, or all

			bean.setChemicalListName(tempBean.getListId());
			bean.setCasNumber(tempBean.getCasNumber());
			bean.setFacilityGroupId(tempBean.getFacilityGroupId());
			bean.setFacilityId(tempBean.getFacilityId());
			if (tempBean.getAreaId() != null)
                bean.setAreaId(tempBean.getAreaId().toString());
			bean.setAreaName(tempBean.getAreaName());
            if (tempBean.getBuildingId() != null)
                bean.setBuildingId(tempBean.getBuildingId().toString());
			bean.setBuildingName(tempBean.getBuildingName());
			if (tempBean.getFloorId() != null)
				bean.setFloorId(tempBean.getFloorId().toString());
			if (tempBean.getRoomId() != null)
				bean.setRoomId(tempBean.getRoomId().toString());
			if (tempBean.getReportingEntityId() != null)
				bean.setReportingEntityId(tempBean.getReportingEntityId());			
			bean.setApplication(tempBean.getApplication());
			bean.setApplicationDesc(tempBean.getApplicationDesc());
			bean.setDockId(tempBean.getDockLocationId());
			bean.setDeliveryPoint(tempBean.getDeliveryPoint());
			if (tempBean.getFlammabilityControlZoneId() != null)
                bean.setFlammabilityControlZoneId(tempBean.getFlammabilityControlZoneId().toString());
			bean.setFlammabilityControlZoneDesc(tempBean.getFlammabilityControlZoneDesc());
			if (tempBean.getVocZoneId() != null)
                bean.setVocZoneId(tempBean.getVocZoneId().toString());
			bean.setVocZoneDescription(tempBean.getVocZoneDescription());
			
			String beginDate = "";
			if (tempBean.getStartDate() != null) {
				beginDate = dateParser.format(tempBean.getStartDate());
			}
			bean.setBeginDateJsp(beginDate);

			String endDate = "";
			if (tempBean.getStopDate() != null) {
				endDate = dateParser.format(tempBean.getStopDate());
			}
			bean.setEndDateJsp(endDate);

			bean.setMaterialCategoryId(StringHandler.emptyIfNull(tempBean.getMaterialCategoryId()));
			bean.setMaterialSubcategoryId(StringHandler.emptyIfNull(tempBean.getMaterialSubcategoryId()));
			bean.setPartNumberCriteria(tempBean.getCatPartNoSearchType());
			bean.setPartNumber(tempBean.getCatPartNo());
			bean.setManufacturerCriteria(tempBean.getManufacturerSearchType());
			bean.setManufacturer(tempBean.getManufacturer());
			if (tempBean.getChemicalList() != null && tempBean.getChemicalList().length() > 0) {
				//since | is special in regular expression
				String stringSplitSeparator = tempBean.getSep();
				if ("|".equals(stringSplitSeparator)) {
					stringSplitSeparator = "["+stringSplitSeparator+"]";
				}
				String[] chemicalList = tempBean.getChemicalList().split(stringSplitSeparator);
				//bean.setChemicalFieldList(chemicalList);
				bean.setChemicalFieldListId(tempBean.getChemicalList());
				//NEED TO SET ALL THE SELECTED REPORT FIELDS HERE
				//THIS WAY HTML OPTION WILL SELECTED THEM AND THEY CAN BE REMOVE THRU JAVASCRIPT
				//bean.setBar(chemicalList);
				//NEED TO GET ALL THE SELECTED REPORT FIELDS
				//SET THEM INTO ReportTemplateFieldBean
				AdHocInventoryReportProcess invProcess = new AdHocInventoryReportProcess(this.getClient(),locale);
				bean.setChemicalFieldCollection(invProcess.getListNameFromId(listOptionBeanCollection,chemicalList));
			}
			if (tempBean.getList() != null && tempBean.getList().length() > 0) {
				//since | is special in regular expression
				String stringSplitSeparator = tempBean.getSep();
				if ("|".equals(stringSplitSeparator)) {
					stringSplitSeparator = "["+stringSplitSeparator+"]";
				}
				String[] reportFieldList = tempBean.getList().split(stringSplitSeparator);
				bean.setReportFieldList(reportFieldList);
				//NEED TO SET ALL THE SELECTED REPORT FIELDS HERE
				//THIS WAY HTML OPTION WILL SELECTED THEM AND THEY CAN BE REMOVE THRU JAVASCRIPT
				bean.setFoo(reportFieldList);
				//NEED TO GET ALL THE SELECTED REPORT FIELDS
				//SET THEM INTO ReportTemplateFieldBean
				bean.setReportFieldCollection(this.getReportFieldNameFromId(baseFieldBeanCollection,reportFieldList));
			}
			bean.setReportGenerationType(tempBean.getOutputMode());
			bean.setReportName(tempBean.getReportName());
			String templateId = "";
			if (tempBean.getTemplateId() != null) {
				templateId = tempBean.getTemplateId().toString();
			}
			bean.setTemplateId(templateId);
			bean.setAllowEdit(tempBean.getAllowEdit());
			bean.setOwner(tempBean.getOwner());
			if (tempBean.getUserGroupId() != null) {
				bean.setUserGroupId(tempBean.getUserGroupId().toString());
			}
			bean.setTemplateDescription(tempBean.getTemplateDescription());
			bean.setGlobalizationLabel(tempBean.getGlobalizationLabel());
			bean.setGlobalizationLabelLetter(tempBean.getGlobalizationLabelLetter());
			bean.setListId(tempBean.getListId());
			bean.setCasNum(tempBean.getCasNumber());
			bean.setListFormat(tempBean.getChemicalListFormat());
			bean.setGateKeeping(tempBean.getGateKeeping());
			bean.setIncludeOpenOrders(tempBean.getIncludeOpenOrders());			
			bean.setReportPeriodType(tempBean.getReportPeriodType());
			bean.setEmailMessage(tempBean.getEmailMessage());
			bean.setEmailSubject(tempBean.getEmailSubject());
			if (tempBean.getEmailUserGroupId() != null)
				bean.setEmailUserGroupId(tempBean.getEmailUserGroupId().toString());
			bean.setEmailMessageNeg(tempBean.getEmailMessageNeg());
			bean.setEmailSubjectNeg(tempBean.getEmailSubjectNeg());
			if (tempBean.getEmailUserGroupIdNeg() != null)
				bean.setEmailUserGroupIdNeg(tempBean.getEmailUserGroupIdNeg().toString());
			String reportPeriodDay = "";
			if (tempBean.getReportPeriodDay() != null)
				reportPeriodDay = tempBean.getReportPeriodDay().toString();
			if (tempBean.getReportPeriodType() != null && !tempBean.getReportPeriodType().equalsIgnoreCase("")){					
				if (tempBean.getReportPeriodType().equalsIgnoreCase("specificDates")) {				
					//
				} else if (tempBean.getReportPeriodType().equalsIgnoreCase("numberOfDays")) {					
					bean.setNumOfDays(reportPeriodDay);
				} else if (tempBean.getReportPeriodType().equalsIgnoreCase("dayOfWeek")) {
					bean.setSelDayOfWeek(reportPeriodDay);
				} else if (tempBean.getReportPeriodType().equalsIgnoreCase("dayOfMonth")) {
					bean.setSelDayOfMonth(reportPeriodDay);
				} else if (tempBean.getReportPeriodType().equalsIgnoreCase("dayOfYear")) {
					bean.setSelDayOfYear(reportPeriodDay);
					//Date date = new SimpleDateFormat("D yyyy").parse(tempBean.getReportPeriodDay() + " " + Calendar.getInstance().get(Calendar.YEAR));
					Calendar calendar = Calendar.getInstance();
					if (tempBean.getReportPeriodDay() != null) {
					    calendar.set(Calendar.DAY_OF_YEAR, tempBean.getReportPeriodDay().intValue());
					    bean.setDayOfYearJsp(dateParser.format(calendar.getTime()));
					} else {
						bean.setDayOfYearJsp("");
					}
				}
			}
			bean.setOverFlamCtrlZnLimit(tempBean.getOverFlamCtrlZnLimit());
			if (tempBean.getOverFlamCtrlZnLmtPercent() != null)
				bean.setOverFlamCtrlZnLmtPercent(tempBean.getOverFlamCtrlZnLmtPercent().toString());
			bean.setCompanyName(tempBean.getCompanyName());
			bean.setCreatedByName(tempBean.getCreatedByName());
			bean.setUserGroupDesc(tempBean.getUserGroupDesc());
			bean.setFlammable(tempBean.getFlammable());
			bean.setDeptId(tempBean.getDeptId());
			bean.setDeptName(tempBean.getDeptName()); 
			
			break;
		}
	}

	//this method get every template for given user if templateName is pass in as null/empty
	//otherwise, it will get specific template for user
	public Collection getTemplate(BigDecimal personnelId, BigDecimal templateId, String allowEdit) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AdHocTemplateBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("reportType",SearchCriterion.EQUALS,reportType);
		criteria.addCriterion("personnelId",SearchCriterion.EQUALS,personnelId.toString());
		if (templateId != null) {
			criteria.addCriterion("templateId",SearchCriterion.EQUALS,templateId.toString());
		}
		if ("Y".equalsIgnoreCase(allowEdit)) {
			criteria.addCriterion("allowEdit",SearchCriterion.EQUALS,"Y");
		}
		criteria.addCriterion("pageId",SearchCriterion.IS,null);
        SortCriteria sortcriteria = new SortCriteria();
		sortcriteria.addCriterion("templateName");
		sortcriteria.addCriterion("templateId");
        return factory.select(criteria,sortcriteria,"ad_hoc_template");
	}

	public void deleteTemplate(BigDecimal personnelId, String template) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		Vector inArgs = new Vector(3);
		Vector outArgs = new Vector(1);
		inArgs.add(reportType);
		inArgs.add(personnelId.toString());
		inArgs.add(template);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		Collection coll = factory.doProcedure("pkg_ad_hoc_report.p_delete_template",inArgs,outArgs);
		if (log.isDebugEnabled()) {
			if (coll.size() == 1) {
				if (((Vector)coll).get(0) != null) {
					log.debug(((Vector)coll).get(0).toString());
				}
			}
		}
	}

	public void setMissingDataFromTemplate(String source) {
		String urlPageArg = "adhocusagereport.do?submitValue=open&templateId=";
		try {
			//get data from existing template because it's not carry to jsp
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				Collection templateColl = getTemplate(personnelBean.getPersonnelIdBigDecimal(),new BigDecimal(bean.getTemplateId()),null);
				Iterator iter = templateColl.iterator();
				AdHocTemplateBean templateBean = null;
				while (iter.hasNext()) {
					templateBean = (AdHocTemplateBean)iter.next();
					break;
				}
				if (templateBean != null) {
					bean.setCompanyId(templateBean.getCompanyId());
					bean.setOwner(templateBean.getOwner());
					bean.setStatus(templateBean.getStatus());
					bean.setUrlPageArg(templateBean.getUrlPageArg());
					bean.setUserGroupId(StringHandler.emptyIfNull(templateBean.getUserGroupId()));
					bean.setHeader(StringHandler.emptyIfNull(templateBean.getHeader()));
                    bean.setFooter(StringHandler.emptyIfNull(templateBean.getFooter()));
                    if ("report".equals(source))
                        bean.setTemplateDescription(StringHandler.emptyIfNull(templateBean.getTemplateDescription()));
                    bean.setGlobalizationLabel(StringHandler.emptyIfNull(templateBean.getGlobalizationLabel()));
                    bean.setGlobalizationLabelLetter(StringHandler.emptyIfNull(templateBean.getGlobalizationLabelLetter()));
                    bean.setCreatedByName(templateBean.getCreatedByName());
                    bean.setCompanyName(templateBean.getCompanyName());
                    bean.setUserGroupDesc(templateBean.getUserGroupDesc());
                }else {
					bean.setTemplateId("''");
					bean.setCompanyId(personnelBean.getCompanyId());
					bean.setOwner("PERSONNEL_ID");
					bean.setStatus("A");
					bean.setUrlPageArg(urlPageArg);
					bean.setUserGroupId("");
					bean.setCreatedByName(StringHandler.emptyIfNull(personnelBean.getLastName())+", " + StringHandler.emptyIfNull(personnelBean.getFirstName()));
				}
			}else {
				bean.setTemplateId("''");
				bean.setCompanyId(personnelBean.getCompanyId());
				bean.setOwner("PERSONNEL_ID");
				bean.setStatus("A");
				bean.setUrlPageArg(urlPageArg);
				bean.setUserGroupId("");
				bean.setCreatedByName(StringHandler.emptyIfNull(personnelBean.getLastName())+", " + StringHandler.emptyIfNull(personnelBean.getFirstName()));
			}
		}catch(Exception e) {
			e.printStackTrace();
			bean.setTemplateId("''");
			bean.setCompanyId(personnelBean.getCompanyId());
			bean.setOwner("PERSONNEL_ID");
			bean.setStatus("A");
			bean.setUrlPageArg(urlPageArg);
			bean.setUserGroupId("");
			bean.setCreatedByName(StringHandler.emptyIfNull(personnelBean.getLastName())+", " + StringHandler.emptyIfNull(personnelBean.getFirstName()));
		}
	}  //end of method

	public String saveTemplate(UsageReportTemplateBean bean, PersonnelBean personnelBean)  throws BaseException {
		String result = null;
		try {
			if (personnelBean.isFeatureReleasedForMyFacilities(ENHANCED_REPORTING, personnelBean.getCompanyId())) {
				this.bean = bean;
				this.personnelBean = personnelBean;
				setMissingDataFromTemplate("save");
				result = getDataMapper().saveTemplate(this.bean, this.personnelBean, getClient());
			}
			else {
				result = saveTemplateLegacy(bean, personnelBean);
			}
		} catch(SQLException e) {
			throw new BaseException(e);
		}
		return result;
	}
	
	public String saveTemplateLegacy(UsageReportTemplateBean bean, PersonnelBean personnelBean)  throws BaseException {
		this.bean = bean;
		this.personnelBean = personnelBean;
		templateName = "'"+SqlHandler.validQuery( bean.getTemplateName())+"'";
		
		setMissingDataFromTemplate("save");
		
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		String query = "select pkg_ad_hoc_report.fx_save_template("+createStringForDatabaseFx("save")+") from dual";
		DataSet queryDataSet = dbManager.select(query);
		DataSetRow dsr = queryDataSet.getDataSetRow(1);  
		String reportQuery = dsr.getString(queryDataSet.getColumnName(1));
		String[] result = reportQuery.split(":");
		if ("OK".equalsIgnoreCase(result[0])) {
			reportQuery = "OK";
			bean.setTemplateId(result[1]);
		} 
		return reportQuery;
	} //end of method

	String createStringForDatabaseFx(String source) {
		reportFields = bean.getReportFieldList();
		reportFieldString = "";
		if (reportFields != null) {
			for (int i = 0; i < reportFields.length; i++) {
				reportFieldString += reportFields[i] + separator;
			}
			reportFieldString = StringHandler.stripLast(reportFieldString, separator);
		}
		if (bean.getBaseFieldId() != null) {
			reportFieldString = bean.getBaseFieldId();
			reportFieldString = StringHandler.stripLast(reportFieldString, separator);
			bean.setReportFieldList(reportFieldString.split("|"));
		}
		
//		//check for space holder
//		if ("xxblankxx".equals(reportFieldString)) {
//			reportFieldString = "";
//		}
		
		Date myEndDate = null;
		if (bean.getEndDateJsp() != null) {
			if ("null".equals(templateName)) {
				myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDateJsp());
			}else {
				//if saving template then keep date as is
				myEndDate =  bean.getEndDateJsp();
			}
		}
			
		String chemicalList = "null";
		String casNumber = "null";
		String partNumber = "null";
		String materialCategory = "null";
		String mfg = "null";
		String partLocation = "null";
		String partCategory = "null";
		String reportName = "null";
		String tmpReportingEntityId = "''";
		String gridTypeReportOption = "";
		
		if(bean.getGridSubmit() == null || bean.getGridSubmit().trim().equals("")) {
			gridTypeReportOption = "all";
		} else {
			if( bean.getGridType() != null && bean.getGridType().equalsIgnoreCase("cas")) {			
				gridTypeReportOption = "singleChemical";
			} else {
				gridTypeReportOption = "all";
			}
		}
//		if (bean.getReportType() != null && bean.getReportType().trim().length() > 0 && bean.getReportType().equalsIgnoreCase("list")) {
//			if (bean.getChemicalListName() != null && bean.getChemicalListName().trim().length() > 0) {
//				chemicalList = "'" + SqlHandler.validQuery(bean.getChemicalListName()) + "' ";
//			}
//		} else if (bean.getReportType() != null && bean.getReportType().trim().length() > 0 && bean.getReportType().equalsIgnoreCase("singleChemical")) {
//			casNumber = "'" + SqlHandler.validQuery(bean.getCasNumber()) + "' ";
//		}		
						
		if (bean.getPartNumber() != null && bean.getPartNumber().trim().length() > 0) {
			partNumber = "'"+SqlHandler.validQuery(bean.getPartNumber().trim())+"'";
		}
		if (bean.getMaterialCategory() != null) {
			materialCategory = "'"+SqlHandler.validQuery(bean.getMaterialCategory())+"'";
		}
		if (bean.getManufacturer() != null && bean.getManufacturer().trim().length() > 0) {
			mfg = "'"+SqlHandler.validQuery(bean.getManufacturer())+"'";
		}
		if ("SWA".equalsIgnoreCase(this.getClient())) {
			if (bean.getLocation() != null && bean.getLocation().trim().length() > 0) {
				partLocation = "'"+bean.getLocation()+"'";
			}
//			if (bean.getReportType() != null && bean.getReportType().trim().length() > 0) {
//				partCategory = "'"+bean.getReportType()+"'";
//			}
			if (gridTypeReportOption != null && gridTypeReportOption.trim().length() > 0) {
				partCategory = "'"+gridTypeReportOption+"'";
			}
		}
		if (bean.getReportName() !=null && bean.getReportName().trim().length() > 0) {
			reportName = "'"+SqlHandler.validQuery(bean.getReportName())+"'";
		}
		if (!StringHandler.isBlankString(bean.getReportingEntityId()) &&
			 !"No Reporting Entity".equalsIgnoreCase(bean.getReportingEntityId())) {
			tmpReportingEntityId = "'"+bean.getReportingEntityId()+"'";
		}

		String tmpDockId = "''";
		if (!"All Docks".equalsIgnoreCase(bean.getDockId()) && !StringHandler.isBlankString(bean.getDockId())) {
			tmpDockId = "'"+bean.getDockId()+"'";
		}
		String tmpDeliveryPoint = "''";
		if (!"All Delivery Points".equalsIgnoreCase(bean.getDeliveryPoint()) && !StringHandler.isBlankString(bean.getDeliveryPoint())) {
			tmpDeliveryPoint = "'"+bean.getDeliveryPoint()+"'";
		}

		String lastModifiedBy = "''";
		String lastModifiedOn = "null";
		if (!"''".equalsIgnoreCase(bean.getTemplateId())) {
			lastModifiedBy = "'"+personnelBean.getPersonnelId()+"'";
			lastModifiedOn = "sysdate";
		}
			
		if (bean.getListFormat() != null) {
			String tempListFormat = bean.getListFormat();
			tempListFormat = StringHandler.stripLast(tempListFormat, separator);
			bean.setListFormat(tempListFormat);
		}
		
		if (bean.getChemicalFieldListId() != null) {
			String tempChemicalFieldList = bean.getChemicalFieldListId();
			tempChemicalFieldList = StringHandler.stripLast(tempChemicalFieldList, separator);
			bean.setChemicalFieldListId(tempChemicalFieldList);
		}
		
		chemicalListString = "";
        chemicalListFormatString = "";
        if (bean.getListFormat() != null && bean.getChemicalFieldListId() != null ) {
			setChemicalListAndFormat(bean.getListFormat(), bean.getChemicalFieldListId());
			chemicalListString = StringHandler.stripLast(chemicalListString, separator);
            chemicalListFormatString = StringHandler.stripLast(chemicalListFormatString, separator);
        }
		
		String gridQuery = null;
		if(bean.getGridSubmit() == null || StringHandler.isBlankString(bean.getGridSubmit()))
			gridQuery = "null,";
		else
			gridQuery = "'" + bean.getGridSubmit() + "',";

		String facilityGroupId = "";
		if(bean.getFacilityGroupId() != null && bean.getFacilityGroupId().length() > 0)
			facilityGroupId = "'"+bean.getFacilityGroupId()+"',";
		else
			facilityGroupId = "null,";

		String reportPeriodDate = "";		
		if (bean.getReportPeriodType() != null && !bean.getReportPeriodType().equalsIgnoreCase("")){					
			if (bean.getReportPeriodType().equalsIgnoreCase("specificDates")) {				
				reportPeriodDate = null;
			} else if (bean.getReportPeriodType().equalsIgnoreCase("numberOfDays")) {
				reportPeriodDate = bean.getNumOfDays();
			} else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfWeek")) {
				reportPeriodDate = bean.getSelDayOfWeek();
			} else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfMonth")) {
				reportPeriodDate = bean.getSelDayOfMonth();
			} else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfYear")) {
				reportPeriodDate = bean.getSelDayOfYear();
			}
			bean.setReportPeriodDay(reportPeriodDate);
		}
		
		String includeOpenOrders = "";
		if (bean.getIncludeOpenOrders() != null && !bean.getIncludeOpenOrders().equalsIgnoreCase("")){
			includeOpenOrders = (bean.getIncludeOpenOrders().equalsIgnoreCase("true")?"Y":"N");
		}
		
		String gateKeeping = "";
		if (bean.getGateKeeping() != null && !bean.getGateKeeping().equalsIgnoreCase("")){
			gateKeeping = (bean.getGateKeeping().equalsIgnoreCase("true")?"Y":"N");
		}
        
		String overFlamCtrlZnLimit = "";
		if (bean.getOverFlamCtrlZnLimit() != null && !bean.getOverFlamCtrlZnLimit().equalsIgnoreCase("")) {
			overFlamCtrlZnLimit = bean.getOverFlamCtrlZnLimit();
		} else {
			overFlamCtrlZnLimit = "N";
		}
			
        String functionValue =
		bean.getTemplateId() + ","+	                                                                 //a_template_id
		"'AdHocUsage',"+																			//a_report_type
		"'"+reportFieldString+"',"+          														//a_list base field id
		"'"+personnelBean.getPersonnelId()+"',"+            										//a_personnel_id
		"'"+separator+"',"+                                 										//a_sep separator
		DateHandler.getOracleToDateFunction(bean.getBeginDateJsp(), "yyyyMMdd", "YYYYMMDD")+","+      //a_start_date
		DateHandler.getOracleToDateFunction(myEndDate, "yyyyMMdd", "YYYYMMDD")+","+                //a_stop_date
		(bean.getGridType().equalsIgnoreCase("list")? gridQuery.toString():"null,")  +             //a_list_id list gridvals	
		"null,"+																					//a_addl_constraint additional where
		"null,"+																					//a_addl_from additional from
		(bean.getGridType().equalsIgnoreCase("cas")? gridQuery.toString():"null,")  +               //a_cas_number cas grid	vals
		"'"+bean.getFacilityId()+"',"+																//a_facility_id
		(StringHandler.isBlankString(bean.getApplication()) ? "null," : "'" + bean.getApplication() + "',") + 	//a_application
		partNumber+","+																				//a_cat_part_no
		"'"+bean.getPartNumberCriteria()+"',"+                                                     //a_cat_part_no_search_type
		tmpDockId+","+				                                                                //a_dock_location_id
		tmpDeliveryPoint+","+				                                                          //a_delivery_point
		materialCategory+","+                                                                      //a_category_id
		mfg+","+																					//a_manufacturer
		"'"+bean.getManufacturerCriteria()+"',"+                                                   //a_manufacturer_search_type
		"'N',"+                        																//a_include_column_alias is alias
		partLocation+","+                                                                          //a_part_location
		partCategory+","+				 															//a_part_category
		"null,"+																					//a_accumulation_point
		"null,"+																					//a_vendor_id
		"null,"+       																				//a_vendor_profile_id waste item id
		"null,"+																					//a_management_option_list waste management options
		"null,"+																					//a_exclude_hub_waste
		//"'"+bean.getReportType()+"',"+															//a_query_type query type [list,singlechemical,all]
		"'"+gridTypeReportOption+"',"+																//a_query_type query type [list,singlechemical,all]
		"'"+StringHandler.emptyIfNull(bean.getChemicalFieldListId())+"',"+	    					//a_chemical_list
		"'"+StringHandler.emptyIfNull(bean.getListFormat())+"',"+	                                //a_chemical_list_format
		templateName+","+       																	//a_template_name
		"'"+bean.getReportGenerationType()+"',"+													//a_output_mode [active,batch]
		reportName+","+																				//a_report_name
		"null,"+																					//a_management_option_desc [waste]
		"null,"+                                                                                    //a_waste_profile_desc
		"'"+bean.getCompanyId()+"',"+		      	   												//a_company_id
		"'N',"+																						//a_debug
		"'"+StringHandler.emptyIfNull(bean.getReportingEntityId())+"',"+							//a_reporting_entity_id
		"'"+StringHandler.emptyIfNull(bean.getUserGroupId())+"',"+									//a_user_group_id
		"'"+StringHandler.emptyIfNull(bean.getOwner())+"',"+ 										//a_owner
		"'"+StringHandler.emptyIfNull(bean.getStatus())+"',"+                   					//a_status
		lastModifiedBy+","+																			//a_last_modified_by
		lastModifiedOn+","+																			//a_last_modified_on
		"'"+SqlHandler.validQuery(bean.getTemplateDescription().trim())+"',"+						//a_template_description
		"'"+bean.getUrlPageArg()+"',"+																//a_url_page_arg
		"null,"+																					//a_account_sys_id --starts cost report data
		"null,"+																					//a_charge_type
		"null,"+																					//a_charge_number_1
		"null,"+																					//a_charge_number_2
		"null,"+																					//a_search_by
		"null,"+																					//a_search_text
		"null,"+																					//a_invoice
		"null,"+																					//a_invoice_period
		"null,"+																					//a_report_field
		"null,"+																					//a_invoice_start_date
		"null,"+																					//a_invoice_end_date
		"null,"+																					//a_requestor
		"null,"+																					//a_requestor_name
		"null,"+																					//a_commodity
		"null,"+																					//a_date_delivered_group_by
		"null,"+																					//a_source_hub
		"null,"+																					//a_item_type
		"null,"+																					//a_search_type
		"null,"+																					//a_output_file_type
		"null,"+																					//a_uom
		"null,"+																					//a_charge_number_3
		"null,"+																					//a_charge_number_4
		"null,"+ 																					//a_po_number
		"'"+StringHandler.emptyIfNull(bean.getAreaId())+"',"+		    							//a_area_id
        "'"+StringHandler.emptyIfNull(bean.getBuildingId())+"',"+	    							//a_building_id
        "'"+StringHandler.emptyIfNull(bean.getFloorId())+"',"+	    								//a_floor_id
        "'"+StringHandler.emptyIfNull(bean.getRoomId())+"',"+										//a_room_id
        facilityGroupId +																			//a_facility_group_id
        "null,"+	                                                                               //a_division_id
        "null,"+	                                                                                //a_customer_part_no
        "null,"+	                                                                                //a_shipping_reference
        "null,"+	                                                                                 //a_customer_invoice_no
        "null,"+	                                                                                 //a_invoice_number
        "null,"+                                                                                    //a_search_parameter
        "null,"+                                                	                               //a_search_value
        "null,"+                                                	                               //msds match type
        "null,"+                                            	                                   //a_mfg_id
    	"null,"+                                                    	                           //a_physical_state
    	"null,"+                                    	                                          //a_msds_ph
    	"null,"+                                                	                              //a_msds_ph_compare
    	"null,"+                                                	                              //a_msds_flash_pt
    	"null,"+                                                        	                      //a_msds_fp_compare
    	"null,"+	                                                                               //a_msds_fp_unit
    	"null,"+	                                                                               //a_vapor_pressure
    	"null,"+	                                                                                //a_msds_vp_compare
    	"null,"+	                                                                                //a_msds_vp_unit
    	"null,"+	                                                                               //a_voc
    	"null,"+	                                                                               //a_voc_compare
    	"null,"+                                                                                    //a_voc_unit
    	"null,"+                                                                                    //a_solids_percent
        "null,"+                                                                                    //a_msds_sp_compare
    	"null,"+                                                                                    //a_nfpa_health
    	"null,"+                                                                                    //a_nfpa_health_comp
    	"null,"+                                                                                   //a_nfpa_flam
    	"null,"+                                                                                   //a_nfpa_flam_comp
    	"null,"+                                                                                   //a_nfpa_reactivity
    	"null,"+                                                                                   //a_nfpa_react_comp
    	"null,"+                                                                                   //a_specific_hazard
    	"null,"+                                                                                    //a_hmis_health
    	"null,"+                                                                                    //a_hmis_health_comp
    	"null,"+                                                                                   //a_hmis_flam
    	"null,"+                                                                                    //a_hmis_flam_comp
    	"null,"+                                                                                    //a_hmis_reactivity
    	"null,"+                                                                                    //a_hmis_react_comp
    	//"null,"+                                                                                    //personal protection
        "null,"+                                                                                    //a_personal_prot
        "null,"+	                                                                                //a_constraint_seperator
        "null,"+	                                                                                //a_full_db_search
        "null,"+                                                                                    //a_approved_search 
        "null,"+                                                                                     //a_kit_only 
      	"null,"+                                                                                    //a_stocked 
    	"null,"+                                                                                    //a_voc_lwes 
    	"null," +                                                                                   //a_voc_lwes_compare
    	"null," +                                                                                  	//a_voc_lwes_unit
    	"'"+StringHandler.emptyIfNull(bean.getDeptId())+"',"+										//a_dept_id
    	(bean.getIsMatCatFX()?
    	"'"+StringHandler.emptyIfNull(bean.getMaterialCategoryId())+"',"+							//a_material_category_id														//a_material_category_id
    	"'"+StringHandler.emptyIfNull(bean.getMaterialSubcategoryId())+"',"+						//a_material_subcategory_id
    	"'"+StringHandler.emptyIfNull(bean.getCatalogCompanyId())+"',"+								//a_catalog_company_id
    	"'"+StringHandler.emptyIfNull(bean.getCatalogId())+"'"										//a_catalog_id
    	:"null,"+
    	"null,"+
    	"null,"+
    	"null"
    	)+
    	","+(source.equalsIgnoreCase("save")?"null,"+"null,":"")+                                                  //a_header and //a_footer
    	"null,"+																						    	//a_composition_percent_operator 
    	"null,"+																						    	//a_composition_percent_limit 
    	"null,"+																						    	//a_amount_limit_operator 
    	"null,"+																						    	//a_amount_limit 
    	"null,"+																						    	//a_amount_limit_unit 
    	"null,"+																						    	//a_trace 
    	"null,"+																						    	//a_trade_secret
    	"'"+StringHandler.emptyIfNull(includeOpenOrders)+"',"+									 	//a_include_open_orders 
    	"'"+StringHandler.emptyIfNull(gateKeeping)+"',"+											   	//a_gatekeeping 
    	"'"+StringHandler.emptyIfNull(bean.getReportPeriodType())+"',"+									  	//a_report_period_type 
    	"'"+StringHandler.emptyIfNull(reportPeriodDate)+"',"+									  	//a_report_period_day 
    	"'"+SqlHandler.validQuery(StringHandler.emptyIfNull(bean.getEmailSubject()))+"',"+				  	//a_email_subject 
    	"'"+SqlHandler.validQuery(StringHandler.emptyIfNull(bean.getEmailMessage()))+"',"+				   	//a_email_message 
    	"'"+StringHandler.emptyIfNull(bean.getEmailUserGroupId())+"',"+									   	//a_email_user_group 
    	"'"+SqlHandler.validQuery(StringHandler.emptyIfNull(bean.getEmailSubjectNeg()))+"',"+			   	//a_email_subject_neg 
    	"'"+SqlHandler.validQuery(StringHandler.emptyIfNull(bean.getEmailMessageNeg()))+"',"+			   	//a_email_message_neg 
    	"'"+StringHandler.emptyIfNull(bean.getEmailUserGroupIdNeg())+"'";                                          //a_email_user_group_neg

        //don't include this when user click generate report
        if (!"report".equalsIgnoreCase(source)) {
            functionValue +=
                ",'"+StringHandler.emptyIfNull(overFlamCtrlZnLimit)+"',"+                                   //a_over_flam_ctrl_zn_limit
                "'"+StringHandler.emptyIfNull(bean.getOverFlamCtrlZnLmtPercent())+"',"+									//a_over_flam_ctrl_zn_lmt_percnt
                "'"+StringHandler.emptyIfNull(bean.getFlammabilityControlZoneId())+"',"+								//a_flammability_control_zone
                "'"+StringHandler.emptyIfNull(bean.getVocZoneId())+"'";												//a_voc_zone        
        }
        else
        	functionValue += ",null";
        functionValue += (!"Y".equalsIgnoreCase(bean.getFlammable()) ?",null":",'Y'") +","+		//a_flammable ad_hoc_template.flammable%type default null,
		"null,"+																					//a_fp_test_detect ad_hoc_template.fp_test_detect%type default '<',
		"null,"+    																				//a_fp_test ad_hoc_template.fp_test%type default 100,
		"null,"+																					//a_fp_test_unit ad_hoc_template.fp_test_unit%type default 'F',
		"null,"+																					//a_nfpa_test_detect ad_hoc_template.nfpa_test_detect%type default '>=',
		"null,"+																						//a_nfpa_test ad_hoc_template.nfpa_test%type default 3,
		"null,"+																						//a_hmis_test_detect ad_hoc_template.hmis_test_detect%type default '>=',
		"null,"+																						//a_hmis_test ad_hoc_template.hmis_test%type default 3,
		"null,"+																						//a_positive_output ad_hoc_template.positive_output%type default 'Y',
		"null,"+																						//a_negative_output ad_hoc_template.negative_output%type default 'N',
		"null";																						//a_null_output ad_hoc_template.null_output%type default null)
        
        
        return functionValue;
	}

	public void test() throws BaseException, Exception{
		try {
			String query = "select fx_get_database_name() from dual";
			DbManager dbManager = new DbManager(getClient(),locale.toString());

			query = "select fx_kit_packaging(554494) from dual";
			GenericSqlFactory f = new GenericSqlFactory(dbManager);
			log.debug(f.selectSingleValue(query));


			/*
			DataSet queryDataSet = dbManager.select(query);
			DataSetRow dsr = queryDataSet.getDataSetRow(1);
			log.debug("String:"+dsr.getString(queryDataSet.getColumnName(1)));


			query = "select fx_echo_varchar('this is varchar2 test') from dual";
			queryDataSet = dbManager.select(query);
			dsr = queryDataSet.getDataSetRow(1);
			log.debug("String:"+dsr.getString(queryDataSet.getColumnName(1)));
			 */
			query = "select fx_echo_clob('this is clob test') from dual";
			/*
			DataSet queryDataSet = dbManager.select(query);
			DataSetRow dsr = queryDataSet.getDataSetRow(1);
			log.debug(dsr.getString(queryDataSet.getColumnName(1)));
			Clob tmpClob = (Clob) dsr.get(queryDataSet.getColumnName(1));
			if (tmpClob == null) {
				log.debug("clob is null");
			}
			log.debug(tmpClob.length());
			log.debug("clob:"+tmpClob.getSubString((long)1,(int)tmpClob.length()));
			 */

			/*
			Connection conn = dbManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while( rs.next() ) {
					 Object obj = rs.getObject(1);
					 oracle.sql.CLOB clob = (oracle.sql.CLOB)obj;
					 log.debug("length:"+clob.length()+":"+clob.getSubString((long)1,(int)clob.length()));
			}
			rs.close();
			dbManager.returnConnection(conn);
			 */
		}catch(Exception e) {
			throw e;
		}
	}

	public void test2() {
		Connection conn = null;
		try {
			conn = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod","doe","linear");
			String query = "select fx_get_database_name() from dual";
			query = "select fx_echo_varchar('this is varchar2 test') from dual";
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while( rs.next() ) {
				Object obj = rs.getObject(1);
				System.out.println(obj.getClass().getName()+":"+obj.toString());
			}
			rs.close();
			query = "select fx_echo_clob('this is clob test') from dual";
			statement = conn.prepareStatement(query);
			rs = statement.executeQuery();
			while( rs.next() ) {
				Object obj = rs.getObject(1);
				System.out.println("2:"+obj.getClass().getName()+":"+obj.toString());
				oracle.sql.CLOB clob = (oracle.sql.CLOB)obj;
				System.out.println("length:"+clob.length()+":"+clob.getSubString(1,(int)clob.length()));
			}
		}catch(Exception e) {

		} finally {
			try {
				conn.close();
			}catch (Exception ee) {

			}
		}
	}

	private Collection<BaseFieldViewBean> getColumnParamsLegacy(GenericSqlFactory myFactory, String fieldLengthNull, Connection connection) throws BaseException {
		String query = "select base_field_id,name,display_length,hyperlink from table (pkg_ad_hoc_report.fx_column_param('AdHocUsage','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "','" + chemicalListString +"','"+chemicalListFormatString+ "'))";
		@SuppressWarnings("unchecked")
		Collection<BaseFieldViewBean> paramColl = myFactory.selectQuery(query,connection);
		return paramColl;
	}
	
	private Collection<BaseFieldViewBean> getColumnParams(String companyId, BigDecimal fieldLengthNull, Connection conn) throws IllegalStateException, BaseException, SQLException {
		String[] reportFields = reportFieldString.split(separatorRegex);
		BigDecimal[] reportFieldsNumeric = new BigDecimal[reportFields.length];
		reportFieldsNumeric = Arrays.stream(reportFields)
				.map(field -> new BigDecimal(field))
				.collect(Collectors.toList()).toArray(reportFieldsNumeric);
		String[] chemicalListFormats = chemicalListFormatString.split(separatorRegex);
		String[] chemicalLists = chemicalListString.split(separatorRegex);
		return getDataMapper().columnParams(reportFieldsNumeric, chemicalLists, chemicalListFormats, "AdHocUsage", companyId, fieldLengthNull, conn);
	}

	public void createReport() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		templateName = "null";
        Connection connection = dbManager.getConnection();
        GenericSqlFactory myFactory;
		try {
			setMissingDataFromTemplate("report");
			String reportQuery = createStringForDatabaseFx("report");
			BigDecimal fieldLengthNull = new BigDecimal(-1);
			myFactory = new GenericSqlFactory(dbManager,new BaseFieldViewBean());
			Collection<BaseFieldViewBean> paramColl = null;
			if (personnelBean.isFeatureReleasedForMyFacilities(ENHANCED_REPORTING, personnelBean.getCompanyId())) {
				paramColl = getColumnParams(null, fieldLengthNull, connection);
			}
			else {
				paramColl = getColumnParamsLegacy(myFactory, fieldLengthNull.toString(), connection);
			}
			String[] columnName = new String[paramColl.size()];
			String[] fieldLengArray = new String[paramColl.size()];
			String[] hyperLinkArray = new String[paramColl.size()];
			Iterator paramIter = paramColl.iterator();
			int count = 0;
			while (paramIter.hasNext()) {
				BaseFieldViewBean bean = (BaseFieldViewBean)paramIter.next();
				columnName[count] = bean.getName();
				if (bean.getDisplayLength() == null) {
					fieldLengArray[count] = "-1";
				}else {
					fieldLengArray[count] = bean.getDisplayLength().toString();
				}
				hyperLinkArray[count] = bean.getHyperlink();
				count++;
			}
			
			String gridQuery = null;
			if(bean.getGridSubmit() == null || StringHandler.isBlankString(bean.getGridSubmit()))
				gridQuery = null;
			else {
				gridQuery = bean.getGridSubmit();
                if ("cas".equals(bean.getGridType())) 
                    gridQuery = gridQuery.replace(" percent","|percent");
            }

            String reportPeriodDate = "";
			if (bean.getReportPeriodType() != null && !bean.getReportPeriodType().equalsIgnoreCase("")){
				if (bean.getReportPeriodType().equalsIgnoreCase("specificDates")) {
					reportPeriodDate = null;
				} else if (bean.getReportPeriodType().equalsIgnoreCase("numberOfDays")) {
					reportPeriodDate = bean.getNumOfDays();
				} else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfWeek")) {
					reportPeriodDate = bean.getSelDayOfWeek();
				} else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfMonth")) {
					reportPeriodDate = bean.getSelDayOfMonth();
				} else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfYear")) {
					reportPeriodDate = bean.getSelDayOfYear();
				}
				bean.setReportPeriodDay(reportPeriodDate);
			}
	
            //NEED TO CALL STAGE TABLE BEFORE CALLING RESULT
            //NEED TO TURN OFF AUTO COMMIT
            connection.setAutoCommit(false);
            Collection inArgs = new Vector(8);
            inArgs.add(personnelBean.getPersonnelIdBigDecimal());  					//-- a_personnel_id personnel.personnel_id%type,
            inArgs.add(personnelBean.getCompanyId());  								//--a_company_id facility.company_id%type,
            inArgs.add(reportFieldString);											//--a_list varchar2,
            inArgs.add(bean.getBeginDateJsp());										//--a_start date default sysdate,
            Date myEndDate = null;
            if (bean.getEndDateJsp() != null) {
                myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDateJsp());
            }
            inArgs.add(myEndDate);													//--a_stop date default sysdate + 1,
            inArgs.add(bean.getFacilityId());										//--a_facility_id facility.facility_id%type default null,
            inArgs.add(bean.getApplication());										//--a_application varchar2 default 'All',
            inArgs.add(bean.getPartNumber());										//--a_cat_part_no varchar2 default null,
            inArgs.add(bean.getPartNumberCriteria());                               //--a_cat_part_no_search_type varchar2 default null,
            inArgs.add(bean.getDockId());                                           //--a_dock_location_id facility_dock.dock_location_id%type default null,
            inArgs.add(bean.getDeliveryPoint());                                    //--a_delivery_point fac_loc_delivery_point.delivery_point%type default null,
            inArgs.add(bean.getManufacturer());                                     //--a_manufacturer varchar2 default null,
            inArgs.add(bean.getManufacturerCriteria());                             //--a_manufacturer_search_type varchar2 default null,
            inArgs.add(bean.getGridType().equalsIgnoreCase("list")? gridQuery:null);                                         //--a_list_id ad_hoc_template.list_id%type default null,  
            inArgs.add(bean.getGridType().equalsIgnoreCase("cas")? gridQuery:null);										//--null, --a_cas_number varchar2,
            inArgs.add(bean.getAreaId());											//--a_area_id varchar2 default null,
            inArgs.add(bean.getBuildingId());										//--a_building_id varchar2 default null,
            inArgs.add(bean.getFloorId());											//--a_floor_id customer.floor.floor_id%type default null,
            inArgs.add(bean.getRoomId());											//--a_room_id room.room_id%type default null,
            inArgs.add(null);														//--a_mfg_id manufacturer.mfg_id%type default null,
            inArgs.add(bean.getDeptId());											//--a_dept_id varchar2 default null,
            inArgs.add(separator);													//--a_sep varchar2 default '|'
            inArgs.add(bean.getFacilityGroupId());									//--a_facility_group_id facility_group.facility_group_id%type default null,
            inArgs.add("N");                                                        //a_include_open_orders
            inArgs.add(bean.getReportingEntityId());                                //a_reporting_entity_id
            inArgs.add(bean.getReportPeriodType());	               	                //a_report_period_type ad_hoc_template.report_period_type%type default 'specificDates',
            inArgs.add(bean.getReportPeriodDay());                                  //a_report_period_day ad_hoc_template.report_period_day%type default null)
            inArgs.add(bean.getFlammabilityControlZoneId());                             //a_flammability_control_zone ad_hoc_template.flammability_control_zone%type default null,
            inArgs.add(bean.getVocZoneId());                                         //a_voc_zone ad_hoc_template.voc_zone%type default null
            myFactory.doProcedure(connection,"pkg_ad_hoc_report.p_stage_usage",inArgs);
            
			//writing data to excel handler
            getDataMapper().logMessage(IAdHocReportDataMapper.REPORT_PREP_PROCESS, "START", "createReport", null, null, connection);
			pw = new ExcelHandlerSXSSF(library);
			//row headers
			pw.addTable(library.getString("label.data"));
			//header
            if(!StringHandler.isBlankString(bean.getHeader())) {
				pw.addRow();
				pw.addTdRegionBold(bean.getHeader(),1,columnName.length);
			}			
			//row headers
			pw.addRow();
			for (int x = 0; x < columnName.length; x++) {
				pw.addCellBold(columnName[x]);
			}
            //setting columns length
            for (int i = 0; i < fieldLengArray.length; i++) {
                String tmp = fieldLengArray[i];
                if (!"-1".equalsIgnoreCase(tmp)) {
                    pw.setColumnParagraph(i);
                    pw.setColumnWidthNow(i,(new BigDecimal(tmp)).intValue());
                }
            }
            //tngo - 032715 added feature because POI takes a long time to create while with hyperlink when
            //there are a lot of data
            if (!personnelBean.isFeatureReleased("DisabledReportHyperLink", "ALL",personnelBean.getCompanyId())) {
                //setting columns for hyperlink
                for (int j = 0; j < hyperLinkArray.length; j++) {
                    if ("Y".equals(hyperLinkArray[j])) {
                        pw.setColumnHyperlink(j);
                    }
                }
            }

            //write data
            String query = "select pkg_ad_hoc_report.fx_sql_result("+reportQuery+") from dual";
			myFactory.selectCursorIntoExcel(query,connection,pw,library);

			//footer
            if(!StringHandler.isBlankString(bean.getFooter())) {
				pw.addRow();
				pw.addRow();
				pw.addTdRegionBold(bean.getFooter(),1,columnName.length);
			}
            
            ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");
            StringBuilder excelFileNamePrefix = new StringBuilder("");
            StringBuilder excelFileName = new StringBuilder("");
			if (StringHandler.isBlankString(bean.getReportName())) {
				excelFileName.append("AdHocUsage").append(personnelBean.getPersonnelId()).append(Calendar.getInstance().getTimeInMillis()).append(".xlsx");
                excelFileNamePrefix.append("AdHocUsage").append(personnelBean.getPersonnelId()).append(Calendar.getInstance().getTimeInMillis());
            } else {
            	excelFileName.append(bean.getReportName().replaceAll(" ","_").replaceAll("[./:*?\\\"<>|]","_")).append("_").append(Calendar.getInstance().getTimeInMillis()).append(".xlsx");
                excelFileNamePrefix.append(bean.getReportName().replaceAll(" ","_").replaceAll("[./:*?\\\"<>|]","_")).append("_").append(Calendar.getInstance().getTimeInMillis());
            }

            //subject of email
            String tmpReportHeader = library.getString("adhocusagereport.title");
            String tmpReportName = "";
            if (!StringHandler.isBlankString(bean.getReportName()))
                tmpReportName += bean.getReportName();
            if (!StringHandler.isBlankString(bean.getTemplateName()) && !StringHandler.isBlankString(bean.getTemplateId())) {
                if (!StringHandler.isBlankString(tmpReportName))
                    tmpReportName += " : ";
                if (!StringHandler.isBlankString(bean.getGlobalizationLabelLetter()))
                    tmpReportName += library.getString(bean.getGlobalizationLabelLetter());
                tmpReportName += bean.getTemplateId()+" - "+bean.getTemplateName();
            }else if (!StringHandler.isBlankString(bean.getTemplateId())) {
                if (!StringHandler.isBlankString(tmpReportName))
                    tmpReportName += " : ";
                if (!StringHandler.isBlankString(bean.getGlobalizationLabelLetter()))
                    tmpReportName += library.getString(bean.getGlobalizationLabelLetter());
                tmpReportName += bean.getTemplateId();
            }else if (!StringHandler.isBlankString(bean.getTemplateName())) {
                if (!StringHandler.isBlankString(tmpReportName))
                    tmpReportName += " : ";
                tmpReportName += bean.getTemplateName();
            }
            //put it together
            String tmpEmailSub = tmpReportHeader;
            if (!StringHandler.isBlankString(tmpReportName) && !"''".equals(tmpReportName))
                tmpEmailSub += " : "+tmpReportName;

            this.writeReportHeader(pw,tmpReportHeader,tmpReportName,connection);
			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				synchronized(this) {
					if (interactiveTimeout) {
						File excelFile = new File(resource.getString("saveltempreportpath")+excelFileName);
						FileOutputStream fos = new FileOutputStream(excelFile);
						pw.write(fos);
						getDataMapper().logMessage(IAdHocReportDataMapper.REPORT_PREP_PROCESS, "STOP", "createReport", null, new BigDecimal(excelFile.length()), connection);
						//now email the file
                        StringBuilder tmpMsg = new StringBuilder(library.getString("label.hereisyourreport")+": ");
                        tmpMsg.append(resource.getString("jnlpcodebase")).append(resource.getString("tempreporturl")).append(excelFileName);
                        if (pw.getDataRowCount() > pw.getMaxRowCount()) {
                        	tmpMsg.append("\n\n").append(library.getString("excel.message.maxrows"));
                        }
                        tmpMsg.append("\n\n").append(library.getString("label.reportdeletefromserver"));
                        MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, tmpEmailSub,tmpMsg.toString(),true);
					}else {
						interactiveTimeout = true;
						getDataMapper().logMessage(IAdHocReportDataMapper.REPORT_PREP_PROCESS, "STOP", "createReport", null, new BigDecimal(0), connection);
					}
				}
			} else {
                //write file to temp directory
                File tempFile = File.createTempFile(excelFileNamePrefix.toString(), ".xlsx");
                FileOutputStream fos = new FileOutputStream(tempFile);
                pw.write(fos);
                getDataMapper().logMessage(IAdHocReportDataMapper.REPORT_PREP_PROCESS, "STOP", "createReport", null, new BigDecimal(tempFile.length()), connection);
                if (tempFile.length() < pw.getBatchMaxReportSizeForAttachment()) {
                    //now email the file
					MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, tmpEmailSub, library.getString("label.hereisyourreport"), excelFileName.toString(), tempFile.getAbsolutePath(),true);
                }else {
                    FileHandler.move(tempFile,new File(resource.getString("saveltempreportpath")+excelFileName));
                    //now email the file
                    StringBuilder tmpMsg = new StringBuilder(library.getString("label.hereisyourreport")+": ");
                    tmpMsg.append(resource.getString("jnlpcodebase")).append(resource.getString("tempreporturl")).append(excelFileName);
                    if (pw.getDataRowCount() > pw.getMaxRowCount()) {
                    	tmpMsg.append("\n\n").append(library.getString("excel.message.maxrows"));
                    }
                    tmpMsg.append("\n\n").append(library.getString("label.reportdeletefromserver"));
                    MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, tmpEmailSub,tmpMsg.toString(),true);
                }
			}
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace(System.out);
			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				getDataMapper().logMessage(IAdHocReportDataMapper.REPORT_PREP_PROCESS, "STOP", "createReport", library.getString("label.reportunexpectederror"), new BigDecimal(0), connection);
                writer.write("<html>");
                writer.write(library.getString("label.reportunexpectederror"));
                writer.write("</html>");
                writer.close();
			} else {
                ExcelHandlerSXSSF eh = new ExcelHandlerSXSSF(library);
			    writeErrorReport(eh);
                File tempFile = File.createTempFile("AdHocUsage", ".xlsx");
				FileOutputStream fos = new FileOutputStream(tempFile);
				eh.write(fos);
				getDataMapper().logMessage(IAdHocReportDataMapper.REPORT_PREP_PROCESS, "STOP", "createReport", null, new BigDecimal(tempFile.length()), connection);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocusagereport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xlsx", tempFile.getAbsolutePath(),true);
			}
			throw new BaseException(e);
		} finally {
            connection.setAutoCommit(true);
            dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			myFactory = null;
		}
        log.debug("done with adhoc usage report");
	}

    private void writeErrorReport (ExcelHandlerSXSSF eh) {
		eh.addRow();
		eh.setColumnParagraph(0);
		eh.setColumnWidthNow(0,50);
		eh.addCellBold(library.getString("label.reportunexpectederror"));
		eh.addRow();
	}
	
	public void writeWorkbookToBrowser() throws BaseException, Exception {
		try {
			pw.write(os);
		}catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace(System.out);
			ExcelHandlerSXSSF eh = new ExcelHandlerSXSSF(library);
			writeErrorReport(eh);
			eh.write(os);
		}
	}

	private void writeReportHeader(ExcelHandlerSXSSF eh, String reportHeader, String reportName, Connection conn) throws BaseException, Exception {
		StringBuilder facilityName = new StringBuilder();
		if ("All".equalsIgnoreCase(bean.getFacilityId())) {
			StringBuilder query = new StringBuilder("select distinct f.facility_name from user_facility uf, facility f where uf.company_id = '"+personnelBean.getCompanyId()+"' and uf.personnel_id = "+personnelBean.getPersonnelId()+" and uf.company_id = f.company_id and uf.facility_id = f.facility_id");
		    if (StringHandler.isBlankString(bean.getFacilityGroupId())) {
		    	query.append(" and f.facility_id not in (select facility_id from facility_group_member where company_id = '"+personnelBean.getCompanyId()+"')");
		    }else if (!"All".equalsIgnoreCase(bean.getFacilityGroupId())) {
		    	query.append(" and f.facility_id in (select facility_id from facility_group_member where company_id = '"+personnelBean.getCompanyId()+"' and facility_group_id = '"+bean.getFacilityGroupId()+"')");
		    }

			DbManager dbManager = new DbManager(getClient());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FacilityBean());
			Vector<FacilityBean> facilityBeans = (Vector<FacilityBean>)factory.selectQuery(query.toString(),conn);

			for(FacilityBean bean :facilityBeans)
				facilityName.append(bean.getFacilityName() + "; ");

		}else
			facilityName.append(bean.getFacilityName());
		
		boolean showVocZone = personnelBean.isFeatureReleased("ShowVocZone", bean.getFacilityId(),bean.getCompanyId());
		boolean showFlammabilityControlZone = personnelBean.isFeatureReleased("ShowFlammabilityControlZone", bean.getFacilityId(),bean.getCompanyId());
		
		// reset the column types before adding a new Excel Worksheet
		// this will prevent 'invalid URI' exceptions if one of the columns happens to contain URIs
		eh.resetColumnTypes();
		eh.addTable(library.getString("label.selectioncriteria"));
		eh.addRow();
        eh.addCell(reportHeader);
        if (!StringHandler.isBlankString(reportName) && !"''".equals(reportName)) {
        	String ownerName = "";
        	if (bean.getOwner() == null || bean.getOwner().equalsIgnoreCase("")) {
        		ownerName = "";
        	} else  if (bean.getOwner().equalsIgnoreCase("PERSONNEL_ID")) {
        		ownerName = bean.getCreatedByName();
        	} else if (bean.getOwner().equalsIgnoreCase("COMPANY_ID")) {
        		ownerName = bean.getCompanyName();
        	} else if (bean.getOwner().equalsIgnoreCase("USER_GROUP_ID")) {
        		ownerName = bean.getUserGroupDesc();
        	}
        	if (ownerName != null && !ownerName.equalsIgnoreCase(""))
        		reportName = reportName + " (" + ownerName + ")";
            eh.addCell(reportName);
        }
        eh.addRow();
        eh.addCell(library.getString("label.reportdate"));
        eh.addCell(DateHandler.formatDate(new Date(), library.getString("java.datetimeformat"), locale));
        
        if (!StringHandler.isBlankString(bean.getTemplateDescription())) {
            eh.addRow();
            eh.addCell(library.getString("label.templatedescription"));
            eh.addCell(bean.getTemplateDescription());
        }

        String tmp = "";
        Date myEndDate = null;
		if (bean.getEndDateJsp() != null) {
			if ("null".equals(templateName)) {
				myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDateJsp());
			}else {
				//if saving template then keep date as is
				myEndDate =  bean.getEndDateJsp();
			}
		}
        eh.addRow();
        if (bean.getReportPeriodType().equalsIgnoreCase("specificDates")){        	
    		eh.addCell(library.getString("label.begindate"));
            if (bean.getBeginDateJsp() != null) {
                eh.addCell(bean.getBeginDateJsp().toString());
            } else {
                eh.addCell("");
            }
            eh.setColumnWidthNow(0,20);
    		eh.setColumnWidthNow(1,20);
    		eh.addRow();
    		eh.addCell(library.getString("label.enddate"));
            if (myEndDate != null) {
                eh.addCell(myEndDate.toString());
            } else {
                eh.addCell("");
            }	
        } else if (bean.getReportPeriodType().equalsIgnoreCase("numberOfDays")){
    		eh.addCell(library.getString("label.Last"));
            if (bean.getReportPeriodDay() != null) {
                eh.addCell(bean.getReportPeriodDay().toString()+" "+library.getString("label.days"));
            } else {
                eh.addCell("");
            }
            eh.setColumnWidthNow(0,20);
    		eh.setColumnWidthNow(1,20);    		
        } else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfWeek")){
        	eh.addCell(library.getString("label.sincedayofweek"));
            if (bean.getReportPeriodDay() != null) {
                //eh.addCell(bean.getReportPeriodDay().toString());
                if (bean.getReportPeriodDay().equalsIgnoreCase("1"))
                	eh.addCell(library.getString("label.sunday"));
                else if (bean.getReportPeriodDay().equalsIgnoreCase("2"))
                	eh.addCell(library.getString("label.monday"));
                else if ( bean.getReportPeriodDay().equalsIgnoreCase("3"))
                	eh.addCell(library.getString("label.tuesday"));
                else if (bean.getReportPeriodDay().equalsIgnoreCase("4"))
                	eh.addCell(library.getString("label.wednesday"));
                else if (bean.getReportPeriodDay().equalsIgnoreCase("5"))
                	eh.addCell(library.getString("label.thursday"));
                else if (bean.getReportPeriodDay().equalsIgnoreCase("6"))
                	eh.addCell(library.getString("label.friday"));
                else if (bean.getReportPeriodDay().equalsIgnoreCase("7"))
                	eh.addCell(library.getString("label.saturday"));
                else 
                	eh.addCell("");
            } else {
                eh.addCell("");
            }
            eh.setColumnWidthNow(0,20);
    		eh.setColumnWidthNow(1,20);
        } else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfMonth")){
        	eh.addCell(library.getString("label.sincedayofmonth"));
            if (bean.getReportPeriodDay() != null) {
                eh.addCell(bean.getReportPeriodDay().toString());
            } else {
                eh.addCell("");
            }
            eh.setColumnWidthNow(0,20);
    		eh.setColumnWidthNow(1,20);
        } else if (bean.getReportPeriodType().equalsIgnoreCase("dayOfYear")){
        	eh.addCell(library.getString("label.sincedayofyear"));
            if (bean.getReportPeriodDay() != null) {
                eh.addCell(bean.getReportPeriodDay().toString());
            } else {
                eh.addCell("");
            }
            eh.setColumnWidthNow(0,20);
    		eh.setColumnWidthNow(1,20);
        } 
        
		eh.addRow();
		eh.addCell(library.getString("label.facilitygroup"));
		eh.addCell(bean.getFacilityGroupName());
		eh.addRow();
		
		eh.addCell(library.getString("label.facility"));
		eh.addCell(StringHandler.emptyIfNull(bean.getFacilityName()));
		
		eh.addRow();
		eh.addCell(library.getString("label.area"));
		eh.addCell(bean.getAreaName());
		eh.addRow();
		eh.addCell(library.getString("label.building"));
		eh.addCell(bean.getBuildingName());
		eh.addRow();
		eh.addCell(library.getString("label.floor"));
		eh.addCell(bean.getFloorName());
		eh.addRow();
		eh.addCell(library.getString("label.room"));
		eh.addCell(bean.getRoomName());
		eh.addRow();
		eh.addCell(library.getString("label.department"));
		eh.addCell(bean.getDeptName());
			
		if (bean.getReportingEntityId() != null) {
			eh.addRow();
			eh.addCell(library.getString("label.workareagroup"));
			eh.addCell(StringHandler.emptyIfNull(bean.getReportingEntityName()));
		}
		eh.addRow();
		eh.addCell(library.getString("label.workarea"));
		eh.addCell(StringHandler.emptyIfNull(bean.getApplicationDesc()));
		eh.addRow();

        if(bean.getIsMatCatFX())
		{
			eh.addCell(library.getString("label.catalog"));
			eh.addCell(bean.getCatalogId());
			eh.addRow();
			eh.addCell(library.getString("label.materialcategory"));
			eh.addCell(bean.getMaterialCategoryName());
			eh.addRow();
			eh.addCell(library.getString("label.materialsubcategory"));
			eh.addCell(bean.getMaterialSubcategoryName());
			eh.addRow();
		}

        eh.addCell(library.getString("label.dock"));
		eh.addCell(StringHandler.emptyIfNull(bean.getDockDesc()));
		eh.addRow();
		eh.addCell(library.getString("label.deliverypoint"));
		eh.addCell(StringHandler.emptyIfNull(bean.getDeliveryPointDesc()));
		eh.addRow();
		tmp = "";
		if (bean.getPartNumber() != null && bean.getPartNumber().length() > 0) {
			tmp ="(" + StringHandler.emptyIfNull(bean.getPartNumberCriteria()) + ") " + StringHandler.emptyIfNull(bean.getPartNumber());
		}
		eh.addCell(library.getString("label.partnumber"));
		eh.addCell(tmp);
		eh.addRow();
		tmp = "";
		if (bean.getManufacturer() != null && bean.getManufacturer().length() > 0) {
			tmp ="(" + StringHandler.emptyIfNull(bean.getManufacturerCriteria()) + ") " + StringHandler.emptyIfNull(bean.getManufacturer());
		}
		eh.addCell(library.getString("label.manufacturer"));
		eh.addCell(tmp);
		eh.addRow();
		if (showFlammabilityControlZone) {
			eh.addCell(library.getString("label.flammable"));
			eh.addCell(bean.getFlammable());
			eh.addRow();
			eh.addCell(library.getString("label.flammabilitycontrolzone"));
			eh.addCell(bean.getFlammabilityControlZoneDesc());
			if (bean.getOverFlamCtrlZnLmtPercent() != null )
                eh.addCell(library.getString("label.overflammabilitylimit")+ " " + library.getString("label.at") + " " + bean.getOverFlamCtrlZnLmtPercent() + " % ");
            eh.addRow();
		}
		if (showVocZone) {
			eh.addCell(library.getString("label.voczone"));
			eh.addCell(bean.getVocZoneDescription());		
			eh.addRow();	
		}
		boolean isAll = false;
		if ("All".equalsIgnoreCase(bean.getGridType())) {
			eh.addCell(library.getString("label.chemicals"));
			eh.addCell(library.getString("label.all"));
			isAll = true;
		} else if ("cas".equalsIgnoreCase(bean.getGridType())) {
			eh.addCell(library.getString("label.chemicals"));
			eh.addCell(library.getString("label.cas"));
		} else if ("list".equalsIgnoreCase(bean.getGridType())) {
			eh.addCell(library.getString("label.chemicals"));
			eh.addCell(library.getString("label.list"));
		}
		eh.addRow();
		eh.addCell("");
		eh.addCell("");		
		eh.addRow();
		if (!isAll) {
			//New
			boolean isCas = bean.getGridType().equalsIgnoreCase("cas") ? true:false;
			if(isCas)
			{
				eh.addCell(library.getString("label.cas"));
				eh.addCell(library.getString("label.chemicalname"));
			}
			else
			{			
				eh.addCell(library.getString("label.list"));
				eh.addCell(library.getString("adhocmaterialmatrixreport.label.listformat"));
			}
			eh.addCell(library.getString("label.constraint"));
			eh.addCell(library.getString("label.operator"));
			eh.addCell( library.getString("label.value"));
			eh.addRow();
	
			String[] gridData = null;
			String[] gridRow = null;
			String[] gridRowVals = null;
			String[] gridRowDesc = null;
	
			if(bean.getGridSubmit() != null && !StringHandler.isBlankString(bean.getGridSubmit()))
			{
				gridData = bean.getGridSubmit().split(";");
				gridRowDesc = bean.getGridDesc().split("&@#");
				GenericReportProcess genericReportProcess = null;
				ArrayList<HashMap<String, String>> result = null;
				HashMap<String, String> listFormatSplit = null;
				if (!isCas) {
					genericReportProcess = new GenericReportProcess(this.getClient(),this.getLocaleObject());
					result = genericReportProcess.separateFormatIdAsPerListId(bean.getListFormat(),bean.getChemicalFieldListId());
					listFormatSplit = (HashMap<String, String>)result.get(0);//get format ids from 0th position
				}
				
				for(int i = 0; i < gridData.length; i++)
				{
					//eh.addCell("");
					int index = 0;
					if(isCas)
					{
						String[] casChem = gridRowDesc[i].split("#@&");
						eh.addCell(casChem[0]);
						eh.addCell(casChem[1]);
						index = 1;
						gridRowVals = gridData[i].split(" ");
	
					}
					else
					{
						eh.addCell(gridRowDesc[i]);
						gridRow = gridData[i].split("\\|");
						if(gridRow.length > 1)
							gridRowVals = gridRow[1].split(" ");
						if (listFormatSplit != null && listFormatSplit.size() > 0 && gridRow.length > 0) {						
							eh.addCell((String)listFormatSplit.get(gridRow[0]));
						} else {
							eh.addCell("");
						}
					}
					if(gridRowVals != null && gridRowVals.length > 1)
					{
	
						String constraint = "";
						if(gridRowVals[index].equalsIgnoreCase("percent_upper"))
								constraint = "Max";
						else if(gridRowVals[index].equalsIgnoreCase("percent_lower"))
								constraint = "Min";
						else if(gridRowVals[index].equalsIgnoreCase("TriggersThreshold"))
							constraint = "Triggers Threshold";						
						else
							constraint = "Average";
						//index++;
						eh.addCell(constraint);
						if(gridRowVals[index+1].equalsIgnoreCase("notlisted") || gridRowVals[index+1].equalsIgnoreCase("trace"))
						{
							eh.addCell(gridRowVals[index+1]);
							eh.addCell("");	
						}
						else if(gridRowVals[index].equalsIgnoreCase("TriggersThreshold"))
						{
							eh.addCell("");
							eh.addCell(gridRowVals[index+1]);
						}
						else
						{
							eh.addCell(gridRowVals[index+1]);
							eh.addCell(gridRowVals[index+2]);
						}
					}else if (gridRowVals != null && gridRowVals.length > 0) {
						String constraint = "";
						if(gridRowVals[index].equalsIgnoreCase("OnList"))						
								constraint = "On List";
						else if(gridRowVals[index].equalsIgnoreCase("NotOnList"))
							constraint = "Not on List";
						else if(gridRowVals[index].equalsIgnoreCase("TriggersThreshold"))
								constraint = "Triggers Threshold";						
						else if(gridRowVals[index].equalsIgnoreCase("TriggersCompositionThreshold"))
								constraint = "Triggers Composition Threshold";
						else
							constraint = "None";
						eh.addCell(constraint);
						eh.addCell("");
						eh.addCell("");
					}
					else
					{
						eh.addCell("");
						eh.addCell("");
						eh.addCell("");
					}
					gridRowVals = null;
					eh.addRow();
				}
			}
		}
        // if max row count was exceeded, write alert message
		if (eh.getDataRowCount() > eh.getMaxRowCount()) {
			// There should already be a new row added, so just add the cell
			eh.addCell(library.getString("excel.message.maxrows"));
		}
	}

	public Collection getReportingEntityDropDownData(String facilityId, Collection c) throws BaseException, Exception {
		boolean found = false;
		Collection reportingEntityCollection = null;
		if (facilityId != null && c != null) {
			Iterator iterator = c.iterator();
			while (iterator.hasNext() && !found) {
				FacAppReportViewBean bean = (FacAppReportViewBean) iterator.next();
				if (facilityId.equalsIgnoreCase(bean.getFacilityId())) {
					reportingEntityCollection = bean.getReportingEntityBeanCollection();
					found = true;
				}
			}
		}
		return reportingEntityCollection;
	}

	public Collection getApplicationDropDownData(String facility, Collection c) throws BaseException, Exception {
		boolean found = false;
		Collection applicationCollection = null;
		if (facility != null && c != null) {
			Iterator iterator = c.iterator();
			while (iterator.hasNext() && !found) {
				FacAppReportViewBean bean = (FacAppReportViewBean) iterator.next();
				if (facility.equalsIgnoreCase(bean.getFacilityId())) {
					applicationCollection = bean.getApplicationBeanCollection();
					found = true;
				}
			}
		}
		return applicationCollection;
	}

	public Collection getDockDropDownData(String facilityId, Collection c) throws BaseException, Exception {
		boolean found = false;
		Collection dockCollection = null;
		if (facilityId != null && c != null) {
			Iterator iterator = c.iterator();
			while (iterator.hasNext() && !found) {
				FacAppDockDpViewBean bean = (FacAppDockDpViewBean) iterator.next();
				if (facilityId.equalsIgnoreCase(bean.getFacilityId())) {
					dockCollection = bean.getDockBeanCollection();
					found = true;
				}
			}
		}
		return dockCollection;
	}

	public Collection getDeliveryPointDropDownData(String dock, Collection c) throws BaseException, Exception {
		boolean found = false;
		Collection deliveryPointCollection = null;
		if (dock != null && c != null) {
			Iterator iterator = c.iterator();
			while (iterator.hasNext() && !found) {
				FacAppDockDpViewBean bean = (FacAppDockDpViewBean) iterator.next();
				if (dock.equalsIgnoreCase(bean.getDockLocationId())) {
					deliveryPointCollection = bean.getDeliveryPointBeanCollection();
					found = true;
				}
			}
		}
		return deliveryPointCollection;
	}

	//inner class for running batch reporting
	class BatchReport extends Thread {
		public BatchReport() {
			super("Usage");
		}

		@Override
		public void run() {
			try {
				createReport();
			} catch (Exception e) {
				log.fatal("error in thread:" + e.getMessage(), e);
			}
		}
	}
	
	class InteractiveReport implements Runnable {
		public void run() {
			try {
				createReport();
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				log.fatal("error in thread:" + e.getMessage(), e);
			}
		}
	}
	
	public Collection<AdHocUsageInputBean> getGridInfo(boolean isList, String templateId) throws Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new AdHocUsageInputBean());
		String search = "";
		if(isList)
			search = "select * from table (pkg_ad_hoc_report.fx_template_list("+ templateId +"))";
		else
			search = "select * from table (pkg_ad_hoc_report.fx_template_cas("+ templateId +"))";
		return factory.selectQuery(search);
	}

    private void setChemicalListAndFormat(String formatId, String listId) {
		String[] formatIds = formatId.split("["+separator+"]");
		String[] listIds = listId.split("["+separator+"]");
		for (int i=0; i < listIds.length; i++) {
			String nextListId = "";			
			nextListId = listIds[i];			
            if (!formatIds[i].equalsIgnoreCase("No Display")) {
                chemicalListString += nextListId + separator;
                chemicalListFormatString += formatIds[i] + separator;
            }
        }
	}
}
