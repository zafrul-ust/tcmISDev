package com.tcmis.client.report.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.AdHocTemplateBean;
import com.tcmis.client.report.beans.AdHocWasteInputBean;
import com.tcmis.client.report.beans.BaseFieldViewBean;
import com.tcmis.client.report.beans.WasteFacAppActVendorViewBean;
import com.tcmis.client.report.beans.WasteReportTemplateBean;
import com.tcmis.client.report.factory.WasteFacAppActVendorViewBeanFactory;
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
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process to create Ad Hoc Waste report
 * @version 1.0
 *****************************************************************************/
public class AdHocWasteReportProcess
extends BaseExcelReportProcess {
	Log log = LogFactory.getLog(this.getClass());
	WasteReportTemplateBean bean;
	PersonnelBean personnelBean;
	//Writer writer;
	OutputStream os;
	OutputStreamWriter writer;
	Locale locale;
	ResourceLibrary library;
	String separator = "|";
	String[] reportFields = null;
	String reportFieldString = "";
	String templateName = "null";
	String reportType = "AdHocWaste";

	public AdHocWasteReportProcess(String client, Locale locale) {
		super(client);
		this.locale = locale;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}

	public void runReport(WasteReportTemplateBean bean,PersonnelBean personnelBean, OutputStream os) throws
	BaseException,
	Exception {
		this.bean = bean;
		this.personnelBean = personnelBean;
		this.os = os;
		writer = new OutputStreamWriter(os);
		if("batch".equalsIgnoreCase(bean.getReportGenerationType())) {
			BatchReport bp = new BatchReport();
			new Thread(bp).start();
			writer.write("<html>");
			writer.write(library.getString("label.batchreportmessage") + " " + personnelBean.getEmail());
			writer.write("</html>");
			writer.close();
		}
		else {
			createReport();
		}
	}

	public Collection getWasteDropDownData() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		WasteFacAppActVendorViewBeanFactory factory = new
		WasteFacAppActVendorViewBeanFactory(
				dbManager);
		return factory.select(new SearchCriteria());
	}

	public Collection getNormalizedWasteDropDownData() throws BaseException,
	Exception {
		Collection normalizedCollection = new Vector();
		Collection c = this.getWasteDropDownData();

		Iterator it = c.iterator();
		String previousFacility = "";
		String previousApplication = "";
		String previousWasteLocation = "";
		String previousVendor = "";
		WasteFacAppActVendorViewBean facilityBean = null;
		WasteFacAppActVendorViewBean applicationBean = null;
		WasteFacAppActVendorViewBean wasteLocationBean = null;
		WasteFacAppActVendorViewBean vendorBean = null;
		while (it.hasNext()) {
			WasteFacAppActVendorViewBean flatBean = (WasteFacAppActVendorViewBean) it.
			next();
			if (!previousFacility.equalsIgnoreCase(flatBean.getFacilityId())) {
				//new facility -> add bean to collection unless it's first time thru loop
				if (previousFacility.length() == 0) {
					facilityBean = new WasteFacAppActVendorViewBean();
					applicationBean = new WasteFacAppActVendorViewBean();
					wasteLocationBean = new WasteFacAppActVendorViewBean();
					this.copyFacilityData(flatBean, facilityBean);
					this.copyApplicationData(flatBean, applicationBean);
					this.copyWasteLocationData(flatBean, wasteLocationBean);
					//vendor
					vendorBean = new WasteFacAppActVendorViewBean();
					this.copyVendorData(flatBean, vendorBean);
				}
				else {
					//vendor
					if(!previousVendor.equalsIgnoreCase(flatBean.getVendorId())){
						WasteFacAppActVendorViewBean tempBean10 = (WasteFacAppActVendorViewBean)vendorBean.clone();
						facilityBean.addVendorBean(tempBean10);
					}
					WasteFacAppActVendorViewBean tempBean6 = (
							WasteFacAppActVendorViewBean)
							wasteLocationBean.clone();
					applicationBean.addWasteLocationBean(tempBean6);
					WasteFacAppActVendorViewBean tempBean4 = (
							WasteFacAppActVendorViewBean)
							applicationBean.clone();
					facilityBean.addApplicationBean(tempBean4);
					WasteFacAppActVendorViewBean tempBean = (WasteFacAppActVendorViewBean)
					facilityBean.clone();
					normalizedCollection.add(tempBean);
					facilityBean = new WasteFacAppActVendorViewBean();
					applicationBean = new WasteFacAppActVendorViewBean();
					wasteLocationBean = new WasteFacAppActVendorViewBean();
					vendorBean = new WasteFacAppActVendorViewBean();
					this.copyFacilityData(flatBean, facilityBean);
					this.copyApplicationData(flatBean, applicationBean);
					this.copyWasteLocationData(flatBean, wasteLocationBean);
					this.copyVendorData(flatBean, vendorBean);
					//vendor
					//since the data in this view isn't "clean" I'll have to "clean" it here
					/*
          if (previousVendor != null &&
              previousVendor.trim().length() > 0 &&
              !previousVendor.equalsIgnoreCase(flatBean.getVendorId())) {
            WasteFacAppActVendorViewBean tempBean10 = (
                WasteFacAppActVendorViewBean)
                vendorBean.clone();
            facilityBean.addVendorBean(tempBean10);
            vendorBean = new WasteFacAppActVendorViewBean();
            this.copyVendorData(flatBean, vendorBean);
          }
					 */
				}
			}
			else {
				//same facility
				//vendor
				if(!previousVendor.equalsIgnoreCase(flatBean.getVendorId())){
					WasteFacAppActVendorViewBean tempBean13 = (
							WasteFacAppActVendorViewBean)
							vendorBean.clone();
					facilityBean.addVendorBean(tempBean13);
					vendorBean = new WasteFacAppActVendorViewBean();
					this.copyVendorData(flatBean, vendorBean);
				}
				if (!previousApplication.equalsIgnoreCase(flatBean.getApplication())) {
					//new application
					WasteFacAppActVendorViewBean tempBean11 = (
							WasteFacAppActVendorViewBean)
							vendorBean.clone();
					wasteLocationBean.addVendorBean(tempBean11);
					WasteFacAppActVendorViewBean tempBean7 = (
							WasteFacAppActVendorViewBean)
							wasteLocationBean.clone();
					applicationBean.addWasteLocationBean(tempBean7);
					WasteFacAppActVendorViewBean tempBean2 = (
							WasteFacAppActVendorViewBean)
							applicationBean.clone();
					facilityBean.addApplicationBean(tempBean2);
					applicationBean = new WasteFacAppActVendorViewBean();
					wasteLocationBean = new WasteFacAppActVendorViewBean();
					vendorBean = new WasteFacAppActVendorViewBean();
					this.copyApplicationData(flatBean, applicationBean);
					this.copyWasteLocationData(flatBean, wasteLocationBean);
					this.copyVendorData(flatBean, vendorBean);
				}
				else {
					//same application
					if (!previousWasteLocation.equalsIgnoreCase(flatBean.getWasteLocationId())) {
						WasteFacAppActVendorViewBean tempBean12 = (
								WasteFacAppActVendorViewBean)
								vendorBean.clone();
						wasteLocationBean.addVendorBean(tempBean12);
						WasteFacAppActVendorViewBean tempBean3 = (
								WasteFacAppActVendorViewBean)
								wasteLocationBean.clone();
						applicationBean.addWasteLocationBean(tempBean3);
						wasteLocationBean = new WasteFacAppActVendorViewBean();
						vendorBean = new WasteFacAppActVendorViewBean();
						this.copyWasteLocationData(flatBean, wasteLocationBean);
						this.copyVendorData(flatBean, vendorBean);
					}
					/*
          else {
            //same waste location, should be different vendor
            WasteFacAppActVendorViewBean tempBean13 = (
                WasteFacAppActVendorViewBean)
                vendorBean.clone();
            wasteLocationBean.addVendorBean(tempBean13);
            vendorBean = new WasteFacAppActVendorViewBean();
            this.copyVendorData(flatBean, vendorBean);
          }
					 */
				}
			}
			previousFacility = flatBean.getFacilityId();
			previousApplication = flatBean.getApplication();
			previousWasteLocation = flatBean.getWasteLocationId();
			previousVendor = flatBean.getVendorId();
		}
		WasteFacAppActVendorViewBean tempBean14 = (
				WasteFacAppActVendorViewBean)
				vendorBean.clone();
		facilityBean.addVendorBean(tempBean14);
		WasteFacAppActVendorViewBean tempBean6 = (WasteFacAppActVendorViewBean)
		wasteLocationBean.clone();
		applicationBean.addWasteLocationBean(tempBean6);
		WasteFacAppActVendorViewBean tempBean4 = (WasteFacAppActVendorViewBean)
		applicationBean.clone();
		facilityBean.addApplicationBean(tempBean4);
		WasteFacAppActVendorViewBean tempBean = (WasteFacAppActVendorViewBean)
		facilityBean.
		clone();
		normalizedCollection.add(tempBean);

		return normalizedCollection;
	}


	private void copyFacilityData(WasteFacAppActVendorViewBean fromBean,
			WasteFacAppActVendorViewBean toBean) {
		toBean.setFacilityId(fromBean.getFacilityId());
		toBean.setFacilityName(fromBean.getFacilityName());
	}

	private void copyApplicationData(WasteFacAppActVendorViewBean fromBean,
			WasteFacAppActVendorViewBean toBean) {
		toBean.setApplication(fromBean.getApplication());
		toBean.setApplicationDesc(fromBean.getApplicationDesc());
	}

	private void copyWasteLocationData(WasteFacAppActVendorViewBean fromBean,
			WasteFacAppActVendorViewBean toBean) {
		toBean.setWasteLocationId(fromBean.getWasteLocationId());
		toBean.setWasteLocationDesc(fromBean.getWasteLocationDesc());
	}

	private void copyVendorData(WasteFacAppActVendorViewBean fromBean,
			WasteFacAppActVendorViewBean toBean) {
		toBean.setVendorId(fromBean.getVendorId());
		toBean.setCompanyName(fromBean.getCompanyName());
	}

	public void convertDateStringToDateObject(AdHocWasteInputBean bean, WasteReportTemplateBean templateBean) {
		SimpleDateFormat dateParser = new SimpleDateFormat(library.getString("java.dateformat"),locale);
		dateParser.setLenient(false);
		try {
			if (bean.getBeginDateJsp() != null){
				templateBean.setBeginDate(dateParser.parse(bean.getBeginDateJsp()));
			}
			if (bean.getEndDateJsp() != null){
				templateBean.setEndDate(dateParser.parse(bean.getEndDateJsp()));
			}
		}catch (Exception e) {

		}
	}

	public void getDefaultReportDate(AdHocWasteInputBean bean) {
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
			/*
			if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
				cal.set(cal.get(Calendar.YEAR)-1,Calendar.DECEMBER,1);
			}else {
				cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1,1);
			}*/
			bean.setEndDateJsp(dateParser.format(cal.getTime()));
		}
	}

	public void copyData(Collection baseFieldBeanCollection,Collection templateData, AdHocWasteInputBean bean) {
		SimpleDateFormat dateParser = new SimpleDateFormat(library.getString("java.dateformat"),locale);
		dateParser.setLenient(false);
		Iterator iter = templateData.iterator();
		while (iter.hasNext()) {
			AdHocTemplateBean tempBean = (AdHocTemplateBean)iter.next();
			bean.setTemplateName(tempBean.getTemplateName());
			bean.setFacilityId(tempBean.getFacilityId());
			bean.setApplication(tempBean.getApplication());
			bean.setAccumulationPoint(tempBean.getAccumulationPoint());
			bean.setVendor(tempBean.getVendorId());
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
			if (tempBean.getVendorProfileId() != null) {
				bean.setProfileId(tempBean.getVendorProfileId().toString());
			}
			bean.setProfileDesc(tempBean.getWasteProfileDesc());
			bean.setManagementOption(tempBean.getManagementOptionList());
			bean.setManagementOptionDesc(tempBean.getManagementOptionDesc());
			bean.setExcludeWaste(tempBean.getExcludeHubWaste());
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
				AdHocUsageReportProcess usageProcess = new AdHocUsageReportProcess(this.getClient(),locale);
				bean.setReportFieldCollection(usageProcess.getReportFieldNameFromId(baseFieldBeanCollection,reportFieldList));
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
		sortcriteria.addCriterion("templateDescription");
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

	public void setMissingDataFromTemplate() {
		String urlPageArg = "adhocwastereport.do?submitValue=open&templateId=";
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
				}else {
					bean.setTemplateId("''");
					bean.setCompanyId(personnelBean.getCompanyId());
					bean.setOwner("PERSONNEL_ID");
					bean.setStatus("A");
					bean.setUrlPageArg(urlPageArg);
					bean.setUserGroupId("");
				}
			}else {
				bean.setTemplateId("''");
				bean.setCompanyId(personnelBean.getCompanyId());
				bean.setOwner("PERSONNEL_ID");
				bean.setStatus("A");
				bean.setUrlPageArg(urlPageArg);
				bean.setUserGroupId("");
			}
		}catch(Exception e) {
			e.printStackTrace();
			bean.setTemplateId("''");
			bean.setCompanyId(personnelBean.getCompanyId());
			bean.setOwner("PERSONNEL_ID");
			bean.setStatus("A");
			bean.setUrlPageArg(urlPageArg);
			bean.setUserGroupId("");
		}
	}  //end of method

	public String saveTemplate(WasteReportTemplateBean bean, PersonnelBean personnelBean)  throws BaseException {
		this.bean = bean;
		this.personnelBean = personnelBean;
		templateName = "'"+SqlHandler.validQuery(bean.getTemplateName())+"'";

		setMissingDataFromTemplate();

		DbManager dbManager = new DbManager(getClient(),locale.toString());
		String query = "select pkg_ad_hoc_report.fx_save_template("+createStringForDatabaseFx()+") from dual";
		DataSet queryDataSet = dbManager.select(query);
		DataSetRow dsr = queryDataSet.getDataSetRow(1);
		String reportQuery = dsr.getString(queryDataSet.getColumnName(1));
		String[] result = reportQuery.split(":");
		if ("OK".equalsIgnoreCase(result[0])) {
			reportQuery = "OK";
			bean.setTemplateId(result[1]);
		}
		return reportQuery;
	}

	String createStringForDatabaseFx() {
		reportFields = bean.getReportFieldList();
		reportFieldString = "";
		for (int i = 0; i < reportFields.length; i++) {
			reportFieldString += reportFields[i] + separator;
		}
		reportFieldString = StringHandler.stripLast(reportFieldString, separator);
		//check for space holder
		if ("xxblankxx".equals(reportFieldString)) {
			reportFieldString = "";
		}
		Date myEndDate = null;
		if (bean.getEndDate() != null) {
			if ("null".equals(templateName)) {
				myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDate());
			}else {
				//if saving template then keep date as is
				myEndDate =  bean.getEndDate();
			}
		}
		String application = "null";
		String profileId = "null";
		String profileDesc = "null";
		String excludeHub = "null";
		String managementOption = "null";
		String managementOptionDesc = "null";
		String reportName = "null";
		if (bean.getApplication() != null && bean.getApplication().trim().length() > 0) {
			if (!"*All*".equalsIgnoreCase(bean.getApplication())) {
				application = "'"+bean.getApplication().trim()+"'";
			}else {
				//if application = *All* and saving then save it
				if (!StringHandler.isBlankString(templateName) && !"null".equalsIgnoreCase(templateName)) {
					application = "'"+bean.getApplication().trim()+"'";
				}
			}
		}
		if (bean.getProfileId() != null && bean.getProfileId().trim().length() > 0) {
			profileId = "'"+SqlHandler.validQuery(bean.getProfileId().trim())+"'";
			profileDesc = "'"+SqlHandler.validQuery(bean.getProfileDesc().trim())+"'";
		}
		if ("Y".equalsIgnoreCase(bean.getExcludeWaste())) {
			excludeHub = "'Y'";
		}
		if (bean.getManagementOption() != null && bean.getManagementOption().trim().length() > 0) {
			managementOption = "'"+SqlHandler.validQuery(bean.getManagementOption().trim())+"'";
			managementOptionDesc = "'"+SqlHandler.validQuery(bean.getManagementOptionDesc().trim())+"'";
		}
		if (bean.getReportName() !=null && bean.getReportName().trim().length() > 0) {
			reportName = "'"+SqlHandler.validQuery(bean.getReportName())+"'";
		}

		String lastModifiedBy = "''";
		String lastModifiedOn = "null";
		if (!"''".equalsIgnoreCase(bean.getTemplateId())) {
			lastModifiedBy = "'"+personnelBean.getPersonnelId()+"'";
			lastModifiedOn = "sysdate";
		}

		String functionValue = 
		bean.getTemplateId()+","+																						//template id
		"'AdHocWaste',"+																									//report type
		"'"+reportFieldString+"',"+          																		//base field id
		"'"+personnelBean.getPersonnelId()+"',"+            												   	//user id
		"'"+separator+"',"+                                 														//separator
		DateHandler.getOracleToDateFunction(bean.getBeginDate(), "yyyyMMdd", "YYYYMMDD")+","+      	//start date
		DateHandler.getOracleToDateFunction(myEndDate, "yyyyMMdd", "YYYYMMDD")+","+                	//end date
		"null,"+																												//list id
		"null,"+																												//additional where
		"null,"+																												//additional from
		"null,"+     																											//CAS number
		"'"+bean.getFacilityId()+"',"+																					//facility id
		application+","+																										//work area
		"null,"+																												//part number
		"null,"+										                                                     	//part number search by type
		"null,"+                                                                                   	//dock
		"null,"+                                                                                   	//delivery point
		"null,"+                                                                                   	//category
		"null,"+																												//manufacturer
		"null,"+                                                                                  	//mfg search by type
		"'N',"+                        																					//column name is alias
		"null,"+                                                                                   	//part location
		"null,"+                                                                                   	//part category
		"'"+bean.getAccumulationPoint()+"',"+																			//accumulation point
		"'"+bean.getVendor()+"',"+																						//vendor id
		profileId+","+       																								//waste item id
		managementOption+","+																								//waste management options
		excludeHub+","+																										//exclude hub
		"null,"+																												//material matrix type [part,used,approved]
		"null,"+																												//chemical list
		"null,"+																												//chemical list format
		templateName+","+       																							//template name
		"'"+bean.getReportGenerationType()+"',"+																		//output mode [active,batch]
		reportName+","+																										//report name
		managementOptionDesc+","+  																						//management option desc [waste]
		profileDesc+","+                                                                           //waste profile desc [waste]
		"'"+bean.getCompanyId()+"',"+		      	   															//company_id
		"'N',"+																												//debug
		"null,"+																												//reporting entity id
		"'"+bean.getUserGroupId()+"',"+																				//NEW STARTS HERE user_group_id
		"'"+bean.getOwner()+"',"+ 																						//owner
		"'"+bean.getStatus()+"',"+                           													//status
		lastModifiedBy+","+																								//last modified by
		lastModifiedOn+","+																								//last modified on
		"'"+SqlHandler.validQuery(bean.getTemplateDescription().trim())+"',"+							//template description
		"'"+bean.getUrlPageArg()+"',"+																				//url page arg
		"null,"+																												//account sys id --starts cost report data
		"null,"+																												//charge type
		"null,"+																												//charge number 1
		"null,"+																												//charge number 2
		"null,"+																												//search by
		"null,"+																												//search text
		"null,"+																											   //invoice
		"null,"+																												//invoice period
		"null,"+																												//report fields
		"null,"+																												//invoice start date
		"null,"+																												//invoice end date
		"null,"+																												//requestor
		"null,"+																												//requestor name
		"null,"+																												//commodity
		"null,"+																												//date delivered group by
		"null,"+																												//source hub
		"null,"+																												//item type
		"null,"+																												//search type
		"null,"+																												//output file type
		"null,"+																												//uom
		"null,"+																												//charge number 3
		"null,"+																												//charge number 4
		"null";																												//po number


		return functionValue;
	}

	public void createReport() throws BaseException,Exception {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		templateName = "null";
		try {
			/*
			String query = "select pkg_ad_hoc_report.fx_sql("+createStringForDatabaseFx()+") from dual";
			if (log.isDebugEnabled()) {
				log.debug("query:" + query);
			}
			DataSet queryDataSet = dbManager.select(query);
			DataSetRow dsr = queryDataSet.getDataSetRow(1);
			String reportQuery = dsr.getString(queryDataSet.getColumnName(1));
			 */
			/*Clob tmpClob = (Clob) dsr.get(queryDataSet.getColumnName(1));
			String reportQuery = tmpClob.getSubString((long)1,(int)tmpClob.length());
			 */
			//get display length for report fields
			/*
			query = "select pkg_ad_hoc_report.fx_display_length('AdHocWaste','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "') from dual";
			queryDataSet = dbManager.select(query);
			dsr = queryDataSet.getDataSetRow(1);
			String reportFieldLength = dsr.getString(queryDataSet.getColumnName(1));
			//since | is special in regular expression
		   String stringSplitSeparator = separator;
			if ("|".equals(stringSplitSeparator)) {
				stringSplitSeparator = "["+stringSplitSeparator+"]";
			}
			String[] fieldLengArray = reportFieldLength.split(stringSplitSeparator);
		   //get column name for report fields
			//because alias can only handle column name less than or equal to 30 characters
			query = "select pkg_ad_hoc_report.fx_sql_header('AdHocWaste','" + reportFieldString + "','" + separator + "',null) from dual";
			queryDataSet = dbManager.select(query);
			dsr = queryDataSet.getDataSetRow(1);
			//String columnNameString = dsr.getString(queryDataSet.getColumnName(1));
		   tmpClob = (Clob) dsr.get(queryDataSet.getColumnName(1));
			String columnNameString = tmpClob.getSubString((long)1,(int)tmpClob.length());
			String[] columnName = columnNameString.split(stringSplitSeparator);
			 */
			setMissingDataFromTemplate();

			String reportQuery = createStringForDatabaseFx();
			String fieldLengthNull = "-1";
			GenericSqlFactory myFactory = new GenericSqlFactory(dbManager,new BaseFieldViewBean());
			String query = "select base_field_id,name,display_length,hyperlink from table (pkg_ad_hoc_report.fx_column_param('AdHocWaste','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "'))";
			Collection paramColl = myFactory.selectQuery(query);
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

			//get data
			query = "select pkg_ad_hoc_report.fx_sql_result("+reportQuery+") from dual";
			Object[] myData = myFactory.selectCursorIntoObjectArray(query);
			//Object[] myData = myFactory.selectIntoObjectArray(reportQuery);
			//String[] columnName = (String[])myData[GenericSqlFactory.COLUMN_NAME_INDEX];
			int[] columnType = (int[])myData[GenericSqlFactory.COLUMN_TYPE_INDEX];
			Collection reportData = (Collection)myData[GenericSqlFactory.DATA_INDEX];

			//writing data to excel handler
			ExcelHandler pw = new ExcelHandler(library);
			this.writeReportHeader(pw);
			//row headers
			pw.addRow();
			for (int x = 0; x < columnName.length; x++) {
				pw.addCellBold(columnName[x]);
			}

			Iterator iter = reportData.iterator();
			boolean firstRow = true;
			while (iter.hasNext()) {
				pw.addRow();
				Object[] rowData = (Object[])iter.next();
				if (firstRow) {
					//setting columns length
					for (int i = 0; i < fieldLengArray.length; i++) {
						String tmp = fieldLengArray[i];
						if (!"-1".equalsIgnoreCase(tmp)) {
							pw.setColumnParagraph(i);
							pw.setColumnWidthNow(i,(new BigDecimal(tmp)).intValue());
						}
					}
					//setting columns for hyperlink
					for (int j = 0; j < hyperLinkArray.length; j++) {
						if ("Y".equals(hyperLinkArray[j])) {
							pw.setColumnHyperlink(j);
						}
					}
				}
				//populating data
				for (int i = 0; i < columnType.length; i++) {
					switch (columnType[i]) {
						case Types.NUMERIC:
							pw.addCell((BigDecimal)rowData[i]);
							break;
						case Types.DATE:
						case Types.TIMESTAMP:
							if (firstRow) {
								pw.setColumnDateFormat(i,library.getString("java.exceldateformat"));
								pw.setColumnWidthNow(i,11);
							}
							pw.addCell((Date)rowData[i]);
							break;
						default:
							pw.addCell((String)rowData[i]);
							break;
					}
				}
				firstRow = false;
			}

			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				pw.write(os);
			} else {
				File tempFile = File.createTempFile("AdHocWaste", ".xls");
				FileOutputStream fos = new FileOutputStream(tempFile);
				pw.write(fos);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocwastereport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xls", tempFile.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace(System.out);
			ExcelHandler eh = new ExcelHandler(library);
			writeErrorReport(eh);
			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				eh.write(os);
			} else {
				File tempFile = File.createTempFile("AdHocWaste", ".xls");
				FileOutputStream fos = new FileOutputStream(tempFile);
				eh.write(fos);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocwastereport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xls", tempFile.getAbsolutePath());
			}
			throw new BaseException(e);
		}
		log.debug("done with adhoc waste report");
	}

	private void writeErrorReport (ExcelHandler eh) {
		eh.addRow();
		eh.setColumnParagraph(0);
		eh.setColumnWidthNow(0,50);
		eh.addCellBold(library.getString("label.reportunexpectederror"));
		eh.addRow();
	}

	private void writeReportHeader(ExcelHandler eh) throws BaseException,Exception {
		eh.addRow();
		eh.addTdRegionBold(library.getString("adhocwastereport.title"),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.date") + ": " + DateHandler.formatDate(new Date(), library.getString("java.dateformat"), locale),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.time") + ": " + DateHandler.formatDate(new Date(), "h:mm a z", locale),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.selectioncriteria"),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.facility")+": "+StringHandler.emptyIfNull(bean.getFacilityName()),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.workarea")+": "+StringHandler.emptyIfNull(bean.getApplicationDesc()),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("adhocwastereport.label.accumulationpoint")+": "+StringHandler.emptyIfNull(bean.getAccumulationPointDesc()),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.vendor")+": "+StringHandler.emptyIfNull(bean.getVendorDesc()),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("adhocwastereport.label.profile")+": "+StringHandler.emptyIfNull(bean.getProfileDesc()),1,4);
		eh.addRow();
		String temp = "";
		if(bean.getExcludeWaste() != null && bean.getExcludeWaste().length() > 0) {
			temp = library.getString("label.yes");
		}
		else {
			temp = library.getString("label.no");
		}
		eh.addTdRegionBold(library.getString("adhocwastereport.label.excludehubwaste")+": "+StringHandler.emptyIfNull(temp),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("adhocwastereport.label.managementoption")+": "+StringHandler.emptyIfNull(bean.getManagementOptionDesc()),1,4);
		eh.addRow();
		temp = "";
		if (bean.getBeginDate() != null) {
			temp = DateHandler.formatDate(bean.getBeginDate(), library.getString("java.dateformat"), locale);
		}
		eh.addTdRegionBold(library.getString("label.begindate")+": "+temp,1,4);
		eh.addRow();
		temp = "";
		if (bean.getEndDate() != null) {
			temp = DateHandler.formatDate(bean.getEndDate(), library.getString("java.dateformat"), locale);
		}
		eh.addTdRegionBold(library.getString("label.enddate")+": "+temp,1,4);
		eh.addRow();
	}

	public Collection getApplicationDropDownData(String facilityId, Collection c) throws
	BaseException, Exception {
		boolean found = false;
		Collection applicationCollection = null;
		Iterator iterator = c.iterator();
		while (iterator.hasNext() && !found) {
			WasteFacAppActVendorViewBean bean = (WasteFacAppActVendorViewBean)
			iterator.next();
			if (facilityId.equalsIgnoreCase(bean.getFacilityId())) {
				applicationCollection = bean.getApplicationCollection();
				found = true;
			}
		}
		return applicationCollection;
	}

	public Collection getAccumulationPointDropDownData(String application,
			Collection c) throws BaseException, Exception {
		boolean found = false;
		Collection accumulationPointCollection = null;
		Iterator iterator = c.iterator();
		while (iterator.hasNext() && !found) {
			WasteFacAppActVendorViewBean bean = (WasteFacAppActVendorViewBean)
			iterator.next();
			if (application.equalsIgnoreCase(bean.getApplication())) {
				accumulationPointCollection = bean.getWasteLocationIdCollection();
				found = true;
			}
		}
		return accumulationPointCollection;
	}

	public Collection getVendorDropDownData(String facilityId, Collection c) throws
	BaseException, Exception {
		boolean found = false;
		Collection vendorCollection = null;
		Iterator iterator = c.iterator();
		while (iterator.hasNext() && !found) {
			WasteFacAppActVendorViewBean bean = (WasteFacAppActVendorViewBean)
			iterator.next();
			if (facilityId.equalsIgnoreCase(bean.getFacilityId())) {
				vendorCollection = bean.getVendorIdCollection();
				found = true;
			}
		}
		return vendorCollection;
	}



	//inner class for running batch reporting
	class BatchReport extends Thread {
		public BatchReport() {
			super("Waste");
		}

		@Override
		public void run() {
			try {
				createReport();
			}
			catch(Exception e){
				log.fatal("error in thread:" + e.getMessage(),e);
			}
		}
	}
}