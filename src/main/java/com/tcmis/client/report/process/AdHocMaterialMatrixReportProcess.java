package com.tcmis.client.report.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.AdHocInventoryInputBean;
import com.tcmis.client.report.beans.AdHocMaterialMatrixInputBean;
import com.tcmis.client.report.beans.AdHocTemplateBean;
import com.tcmis.client.report.beans.BaseFieldViewBean;
import com.tcmis.client.report.beans.FacAreaBlgFloorRmStgView;
import com.tcmis.client.report.beans.ListBean;
import com.tcmis.client.report.beans.MaterialmatrixReportTemplateBean;
import com.tcmis.client.report.factory.TcmisFeatureBeanFactory;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.admin.beans.FeatureReleaseBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;

/******************************************************************************
 * Process to create Ad Hoc Material Matrix report
 * @version 1.0
 *****************************************************************************/
public class AdHocMaterialMatrixReportProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	MaterialmatrixReportTemplateBean bean;
	PersonnelBean personnelBean;
	//Writer writer;
	OutputStream os;
	OutputStreamWriter writer;
	Locale locale;
	ResourceLibrary library;
	String separator = "|";
	String[] reportFields = null;
	String[] chemicalFields = null;
	String reportFieldString = "";
	String chemicalListString = "";
	String templateName = "null";
	String reportType = "MaterialMatrix";
    String chemicalListFormatString = "";
    
    boolean interactiveTimeout;
	ExcelHandlerSXSSF pw;

    public AdHocMaterialMatrixReportProcess(String client, Locale locale) {
		super(client);
		this.locale = locale;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}

	public boolean runReport(MaterialmatrixReportTemplateBean bean,
			PersonnelBean personnelBean, OutputStream os) throws
			BaseException,
			Exception {
		this.interactiveTimeout = false;
		this.bean = bean;
		this.personnelBean = personnelBean;
		this.os = os;
		writer = new OutputStreamWriter(os);
		if("batch".equalsIgnoreCase(bean.getReportGenerationType())) {
			this.interactiveTimeout = true;
			BatchReport bp = new BatchReport();
			new Thread(bp).start();
			writer.write("<html>");
			writer.write(library.getString("label.batchreportmessage") + " " + personnelBean.getEmail());
			writer.write("</html>");
			writer.close();
		}
		else {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Future<?> future = executor.submit(new InteractiveReport());
			try {
				future.get(this.getReportInteractiveSleepTime(), TimeUnit.SECONDS); // set timeout for 8 minutes
				this.interactiveTimeout = false;
			}
			catch(TimeoutException e) {
				synchronized(this) {
					if ( ! interactiveTimeout) { // if interactiveTimeout has been set, the report is ready, so don't wait
						this.interactiveTimeout = true;
						writer.write("<html>");
						writer.write(library.getString("label.interactivereporttimeoutmessage") + "<br />");
						writer.write(library.getString("label.batchreportmessage") + " " + personnelBean.getEmail());
						writer.write("</html>");
						writer.close();
					}
					else {
						this.interactiveTimeout = false;
					}
				}
			}
			catch (Exception e) {
				log.fatal("error in thread:" + e.getMessage(), e);
			}
		}
		
		return this.interactiveTimeout;
	}

	public void convertDateStringToDateObject(AdHocMaterialMatrixInputBean bean, MaterialmatrixReportTemplateBean templateBean) {
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

	public void getDefaultReportDate(AdHocMaterialMatrixInputBean bean) {
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
		if (StringHandler.isBlankString(bean.getInventoryDate())){
			//get end date today
			Calendar cal = Calendar.getInstance();
			bean.setInventoryDate(dateParser.format(cal.getTime()));
		}
	}

	public Collection getListNameFromId(Collection listList, String[] selectedLists) {
		Collection result = new Vector(selectedLists.length);
		for (int i = 0; i < selectedLists.length; i++) {
			String fieldId = selectedLists[i];
			Iterator iter = listList.iterator();
			while (iter.hasNext()) {
				ListBean tmpBean = (ListBean)iter.next();
				if (fieldId.equals(tmpBean.getListId())) {
					ListBean bean = new ListBean();
					bean.setListId(tmpBean.getListId());
					bean.setListName(tmpBean.getListName());
					result.add(bean);
					break;
				}
			}
		}
		return result;
	}
	
	public void copyData(Collection baseFieldBeanCollection,Collection listOptionBeanCollection,Collection templateData, AdHocMaterialMatrixInputBean bean) {
		SimpleDateFormat dateParser = new SimpleDateFormat(library.getString("java.dateformat"),locale);
		dateParser.setLenient(false);
		Iterator iter = templateData.iterator();
		while (iter.hasNext()) {
			AdHocTemplateBean tempBean = (AdHocTemplateBean)iter.next();
			bean.setTemplateName(tempBean.getTemplateName());
			bean.setFacilityGroupId(tempBean.getFacilityGroupId());
			bean.setFacilityId(tempBean.getFacilityId());
            if (tempBean.getAreaId() != null)
                bean.setAreaId(tempBean.getAreaId().toString());
			bean.setAreaName(tempBean.getAreaName());
            if (tempBean.getBuildingId() != null)
                bean.setBuildingId(tempBean.getBuildingId().toString());
			bean.setBuildingName(tempBean.getBuildingName());
			bean.setDeptId(tempBean.getDeptId());
			bean.setDeptName(tempBean.getDeptName());

			if (tempBean.getFloorId() != null)
				bean.setFloorId(tempBean.getFloorId().toString());

			if (tempBean.getRoomId() != null)
				bean.setRoomId(tempBean.getRoomId().toString());

			if (tempBean.getReportingEntityId() != null)
				bean.setReportingEntityId(tempBean.getReportingEntityId());
			bean.setApplication(tempBean.getApplication());
			bean.setReportCriteria(tempBean.getQueryType());   			//list, singleChemical, or all
			bean.setPartNumberCriteria(tempBean.getCatPartNoSearchType());
			bean.setPartNumber(tempBean.getCatPartNo());
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
			bean.setListFormat(tempBean.getChemicalListFormat());

			if (tempBean.getChemicalList() != null && tempBean.getChemicalList().length() > 0) {
				//since | is special in regular expression
				String stringSplitSeparator = tempBean.getSep();
				if ("|".equals(stringSplitSeparator)) {
					stringSplitSeparator = "["+stringSplitSeparator+"]";
				}
				String[] chemicalList = tempBean.getChemicalList().split(stringSplitSeparator);
				bean.setChemicalFieldList(chemicalList);
				bean.setChemicalFieldListId(tempBean.getChemicalList());
				//NEED TO SET ALL THE SELECTED REPORT FIELDS HERE
				//THIS WAY HTML OPTION WILL SELECTED THEM AND THEY CAN BE REMOVE THRU JAVASCRIPT
				//bean.setBar(chemicalList);
				//NEED TO GET ALL THE SELECTED REPORT FIELDS
				//SET THEM INTO ReportTemplateFieldBean
				bean.setChemicalFieldCollection(getListNameFromId(listOptionBeanCollection,chemicalList));
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
				//bean.setFoo(reportFieldList);
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

			bean.setPhCompare(tempBean.getMsdsPhCompare()==null?"":tempBean.getMsdsPhCompare());
			bean.setPh(tempBean.getMsdsPh()==null?"":tempBean.getMsdsPh().toPlainString());
			bean.setFlashPointCompare(tempBean.getMsdsFpCompare());
			bean.setFlashPoint(tempBean.getMsdsFlashPt()==null?"":tempBean.getMsdsFlashPt().toPlainString());
			bean.setTemperatureUnit(tempBean.getMsdsFpUnit());
		    bean.setVocSearchSelect(tempBean.getVocUnit());
			bean.setVocPercentCompare(tempBean.getVocCompare());
			bean.setVocPercent(tempBean.getVoc()==null?"":tempBean.getVoc().toPlainString());
		    bean.setVocLwesSearchSelect(tempBean.getVocLwesUnit());
		    bean.setVocLwesPercentCompare(tempBean.getVocLwesCompare());
		    bean.setVocLwesPercent(tempBean.getVocLwes()==null?"":tempBean.getVocLwes().toPlainString());
			bean.setSolidsPercentCompare(tempBean.getMsdsSpCompare());
			bean.setSolidsPercent(tempBean.getSolidsPercent()==null?"":tempBean.getSolidsPercent().toPlainString());
			bean.setHealth(tempBean.getNfpaHealth());
			bean.setHealthCompare(tempBean.getNfpaHealthComp());
			bean.setFlammability(tempBean.getNfpaFlam());
			bean.setFlammabilityCompare(tempBean.getNfpaFlamComp());
			bean.setReactivity(tempBean.getNfpaReactivity());
			bean.setReactivityCompare(tempBean.getNfpaReactComp());
			bean.setSpecificHazard(tempBean.getSpecificHazard());
			bean.setHmisHealth(tempBean.getHmisHealth());
			bean.setHmisHealthCompare(tempBean.getHmisHealthComp());
			bean.setHmisFlammability(tempBean.getHmisFlam());
			bean.setHmisFlammabilityCompare(tempBean.getHmisFlamComp());
			bean.setHmisReactivity(tempBean.getHmisReactivity());
			bean.setHmisReactivityCompare(tempBean.getHmisReactComp());
			bean.setPersonalProtection(tempBean.getPersonalProt());
			bean.setPhysicalState(tempBean.getPhysicalState());
			bean.setVaporPressureCompare(tempBean.getMsdsVpCompare());
			bean.setVaporPressureUnit(tempBean.getMsdsVpUnit());
			bean.setVaporPressure(tempBean.getVaporPressure()==null?"":tempBean.getVaporPressure().toPlainString());
			bean.setListId(tempBean.getListId());
			bean.setCasNum(tempBean.getCasNumber());
			bean.setApplication(tempBean.getApplication());
			bean.setApplicationDesc(tempBean.getApplicationDesc());
			bean.setSearchField(tempBean.getSearchParameter());
			bean.setMatchType(tempBean.getMsdsMatchType());
			bean.setSearchText(tempBean.getSearchValue());
			bean.setMfgId(tempBean.getMfgId()==null?"":tempBean.getMfgId().toPlainString());
			bean.setReportCriteria(tempBean.getQueryType());
			bean.setMfgDesc(tempBean.getMfgDesc());
			bean.setReportingEntityId(tempBean.getReportingEntityId());
			bean.setCatalogId(tempBean.getCatalogId());
			bean.setMaterialCategoryId(tempBean.getMaterialCategoryId());
			bean.setCatalogCompanyId(tempBean.getCatalogCompanyId());
			bean.setMaterialCategoryName(tempBean.getMaterialCategoryName());
			bean.setMaterialSubcategoryName(tempBean.getMaterialSubcategoryName());
			bean.setMaterialSubcategoryId(tempBean.getMaterialSubcategoryId());
			bean.setCompanyName(tempBean.getCompanyName());
			bean.setCreatedByName(tempBean.getCreatedByName());
			bean.setUserGroupDesc(tempBean.getUserGroupDesc());
			bean.setFlammable(tempBean.getFlammable());
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
			}		}
	}

	public void setMissingDataFromTemplate(String source) {
		String urlPageArg = "adhocmaterialmatrixreport.do?submitValue=open&templateId=";
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
                    if ("createReport".equals(source))
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

	public String saveTemplate(MaterialmatrixReportTemplateBean bean, PersonnelBean personnelBean)  throws BaseException {
		this.bean = bean;
		this.personnelBean = personnelBean;
		templateName = "'"+SqlHandler.validQuery(bean.getTemplateName())+"'";

		setMissingDataFromTemplate("saveTemplate");

		DbManager dbManager = new DbManager(getClient(),locale.toString());
		String query = "select pkg_ad_hoc_report.fx_save_template("+createStringForDatabaseFx("saveTemplate")+") from dual";
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

	String createStringForDatabaseFx(String calledFrom) {
		reportFields = bean.getReportFieldList();
		//chemicalFields = bean.getChemicalFieldList();
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
				
		if (bean.getListFormat() != null) {
			String tempListFormat = bean.getListFormat();
			tempListFormat = StringHandler.stripLast(tempListFormat, separator);
			bean.setListFormat(tempListFormat);
		}
		
		
		//check for space holder
		if ("xxblankxx".equals(reportFieldString)) {
			reportFieldString = "";
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
		
		String tmpReportingEntityId = "''";
		String reportName = null;
		if (bean.getReportName() !=null && bean.getReportName().trim().length() > 0) {
			reportName = "'"+SqlHandler.validQuery(bean.getReportName())+"'";
		}

		String lastModifiedBy = "''";
		String lastModifiedOn = "null";
		if (!"''".equalsIgnoreCase(bean.getTemplateId())) {
			lastModifiedBy = "'"+personnelBean.getPersonnelId()+"'";
			lastModifiedOn = "sysdate";
		}
        Date myEndDate = null;
		if (bean.getEndDateJsp() != null) {
			if ("null".equals(templateName)) {
				myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDateJsp());
			}else {
				//if saving template then keep date as is
				myEndDate =  bean.getEndDateJsp();
			}
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

        String functionValue =
		bean.getTemplateId() + ","+                                                                          //a_template_id
		"'MaterialMatrix',"+																			    //a_report_type
		"'"+reportFieldString+"',"+          																//a_list base field id
		""+personnelBean.getPersonnelId()+","+            												   	//a_personnel_id
		"'"+separator+"',"+                                 												//a_sep separator
		DateHandler.getOracleToDateFunction(bean.getBeginDateJsp(), "yyyyMMdd", "YYYYMMDD")+","+            //a_start_date
		DateHandler.getOracleToDateFunction(myEndDate, "yyyyMMdd", "YYYYMMDD")+","+ 					    //a_stop_date
		(bean.getGridType().equalsIgnoreCase("list")? gridQuery.toString():"null,")  +						//a_list_id list grid vals
		"null,"+																							//a_addl_constraint additional where
		"null,"+																							//a_addl_from additional from
		(bean.getGridType().equalsIgnoreCase("cas")? gridQuery.toString():"null,")  +		   				//a_cas_number cas grid	vals
		"'"+bean.getFacilityId()+"',"+																		//a_facility_id
		(StringHandler.isBlankString(bean.getApplication()) ? "null," : "'" + bean.getApplication() + "',") +  //a_application
		"null,"+ 																							//a_cat_part_no
		"null,"+											                                                //a_cat_part_no_search_type
		"null,"+                                                                                   			//a_dock_location_id
		"null,"+                                                                                   			//a_delivery_point
		"null,"+                                                                                   			//a_category_id
		"null,"+																							//a_manufacturer
		"null,"+                                                                                   			//a_manufacturer_search_type
		"'N',"+                        																		//a_include_column_alias is alias
		"null,"+                                                                                   			//a_part_location
		"null,"+                                                                                   			//a_part_category
		"null,"+																							//a_accumulation_point
		"null,"+																							//a_vendor_id
		"null,"+            																				//a_vendor_profile_id waste item id
		"null,"+ 																							//a_management_option_list waste management options
		"null,"+																							//a_exclude_hub_waste
		"'"+StringHandler.emptyIfNull(bean.getReportCriteria())+"',"+										//a_query_type material matrix type [PART,USED,APPROVED,ATTACHEDTOFACILITY]
		"'"+StringHandler.emptyIfNull(bean.getChemicalFieldListId())+"',"+									//a_chemical_list
		"'"+StringHandler.emptyIfNull(bean.getListFormat())+"',"+											//a_chemical_list_format
		templateName+","+																					//a_template_name
		"'"+StringHandler.emptyIfNull(bean.getReportGenerationType())+"',"+			 						//a_output_mode [active,batch]
		reportName+","+																						//a_report_name
		"null,"+																							//a_management_option_desc [waste]
		"null,"+                                                                                    		//a_waste_profile_desc
		"'"+bean.getCompanyId()+"',"+		      	   														//a_company_id
		"'N',"+																								//a_debug
		"'"+StringHandler.emptyIfNull(bean.getReportingEntityId())+"',"+									//a_reporting_entity_id
		"'"+StringHandler.emptyIfNull(bean.getUserGroupId())+"',"+											//a_user_group_id
		"'"+StringHandler.emptyIfNull(bean.getOwner())+"',"+ 												//a_owner
		"'"+StringHandler.emptyIfNull(bean.getStatus())+"',"+                           					//a_status
		lastModifiedBy+","+																					//a_last_modified_by
		lastModifiedOn+","+																					 //a_last_modified_on
		"'"+SqlHandler.validQuery(bean.getTemplateDescription().trim())+"',"+								//a_template_description
		"'"+StringHandler.emptyIfNull(bean.getUrlPageArg())+"',"+											//a_url_page_arg
		"null,"+																							//a_account_sys_id --starts cost report data
		"null,"+																							//a_charge_type
		"null,"+																							//a_charge_number_1
		"null,"+																							//a_charge_number_2
		"null,"+																							//a_search_by
		"null,"+																							//a_search_text
		"null,"+																							 //a_invoice
		"null,"+																							//a_invoice_period
		"null,"+																							//a_report_field
		"null,"+																							//a_invoice_start_date
		"null,"+																							//a_invoice_end_date
		"null,"+																							//a_requestor
		"null,"+																							//a_requestor_name
		"null,"+																							//a_commodity
		"null,"+																							//a_date_delivered_group_by
		"null,"+																							//a_source_hub
		"null,"+																							//a_item_type
		"null,"+																							//a_search_type
		"null,"+																							//a_output_file_type
		"null,"+																							//a_uom
		"null,"+																							//a_charge_number_3
		"null,"+																							//a_charge_number_4
        "null,"+																							//a_po_number
        "'"+StringHandler.emptyIfNull(bean.getAreaId())+"',"+												//a_area_id
        "'"+StringHandler.emptyIfNull(bean.getBuildingId())+"',"+											//a_building_id
        "'"+StringHandler.emptyIfNull(bean.getFloorId())+"',"+												//a_floor_id
        "'"+StringHandler.emptyIfNull(bean.getRoomId())+"',"+												//a_room_id
        facilityGroupId +																					//a_facility_group_id
        "null,"+																							//a_division_id
        "null,"+																							//a_customer_part_no
        "null,"+																							//a_shipping_reference
        "null,"+																							//a_customer_invoice_no
        "null,"+																							//a_invoice_number
        "'"+bean.getSearchField()+"',"+																		//a_search_parameter
        "'"+StringHandler.emptyIfNull(bean.getSearchText()) +"',"+											//a_search_value
        "'"+StringHandler.emptyIfNull(bean.getMatchType()) +"',"+											//a_msds_match_type
         "'"+StringHandler.emptyIfNull(bean.getMfgId())+"',"+												//a_mfg_id
    	 "'"+StringHandler.emptyIfNull(bean.getPhysicalState())+"',"+										//a_physical_state
    	 StringHandler.nullIfEmpty(bean.getPh())+","+														//a_msds_ph
    	 "'"+StringHandler.emptyIfNull(bean.getPhCompare())+"',"+											//a_msds_ph_compare
    	 "'"+StringHandler.emptyIfNull(bean.getFlashPoint())+"',"+											//a_msds_flash_pt
    	 "'"+StringHandler.emptyIfNull(bean.getFlashPointCompare())+"',"+									//a_msds_fp_compare
    	 "'"+StringHandler.emptyIfNull(bean.getTemperatureUnit())+"',"+										//a_msds_fp_unit
    	 StringHandler.nullIfEmpty(bean.getVaporPressure())+","+											//a_vapor_pressure
    	 "'"+StringHandler.emptyIfNull(bean.getVaporPressureCompare())+"',"+								//a_msds_vp_compare
    	 "'"+StringHandler.emptyIfNull(bean.getVaporPressureUnit())+"',"+									//a_msds_vp_unit
    	 StringHandler.nullIfEmpty(bean.getVocPercent())+","+												//a_voc
    	 "'"+StringHandler.emptyIfNull(bean.getVocPercentCompare())+"',"+									//a_voc_compare
    	 "'"+StringHandler.emptyIfNull(bean.getVocSearchSelect())+"',"+										//a_voc_unit
    	 StringHandler.nullIfEmpty(bean.getSolidsPercent())+","+											//a_solids_percent
    	 "'"+StringHandler.emptyIfNull(bean.getSolidsPercentCompare())+"',"+								//a_msds_sp_compare
    	 "'"+StringHandler.emptyIfNull(bean.getHealth())+"',"+ 												//a_nfpa_health
    	 "'"+StringHandler.emptyIfNull(bean.getHealthCompare())+"',"+										//a_nfpa_health_comp
    	 "'"+StringHandler.emptyIfNull(bean.getFlammability())+"',"+										//a_nfpa_flam
    	 "'"+StringHandler.emptyIfNull(bean.getFlammabilityCompare())+"',"+									//a_nfpa_flam_comp
    	 "'"+StringHandler.emptyIfNull(bean.getReactivity())+"',"+											//a_nfpa_reactivity
    	 "'"+StringHandler.emptyIfNull(bean.getReactivityCompare())+"',"+									//a_nfpa_react_comp
    	 "'"+StringHandler.emptyIfNull(bean.getSpecificHazard())+"',"+										//a_specific_hazard
    	 "'"+StringHandler.emptyIfNull(bean.getHmisHealth())+"',"+											//a_hmis_health
    	 "'"+StringHandler.emptyIfNull(bean.getHmisHealthCompare())+"',"+									//a_hmis_health_comp
    	 "'"+StringHandler.emptyIfNull(bean.getHmisFlammability())+"',"+									//a_hmis_flam
    	 "'"+StringHandler.emptyIfNull(bean.getHmisFlammabilityCompare())+"',"+								//a_hmis_flam_comp
    	 "'"+StringHandler.emptyIfNull(bean.getHmisReactivity())+"',"+										//a_hmis_reactivity
    	 "'"+StringHandler.emptyIfNull(bean.getHmisReactivityCompare())+"',"+								//a_hmis_react_comp
    	 "'"+StringHandler.emptyIfNull(bean.getPersonalProtection())+"',"+									//a_personal_prot
         "null,"+																							//a_constraint_seperator
         "null,"+																							//a_full_db_search
         "null,"+																							//a_approved_search
         "null,"+																							//a_kit_only
         "null,"+																							//a_stocked
      	StringHandler.nullIfEmpty(bean.getVocLwesPercent())+","+											//a_voc_lwes
    	"'"+StringHandler.emptyIfNull(bean.getVocLwesPercentCompare())+"',"+								//a_voc_lwes_compare
    	"'"+StringHandler.emptyIfNull(bean.getVocLwesSearchSelect())+ "',"+									//a_voc_lwes_unit
    	"'"+StringHandler.emptyIfNull(bean.getDeptId()) + "',"+												//a_dept_id
    	(bean.getIsMatCatFX()?
    	    	"'"+StringHandler.emptyIfNull(bean.getMaterialCategoryId())+"',"+										//a_material_category_id														//a_material_category_id
    	    	"'"+StringHandler.emptyIfNull(bean.getMaterialSubcategoryId())+"',"+									//a_material_subcategory_id
    	    	"'"+StringHandler.emptyIfNull(bean.getCatalogCompanyId())+"',"+											//a_catalog_company_id
    	    	"'"+StringHandler.emptyIfNull(bean.getCatalogId())+"',"													//a_catalog_id
    	    	:"null,"+
    	    	"null,"+
    	    	"null,"+
    	    	"null,"
    	    	) +
    	    	("saveTemplate".equalsIgnoreCase(calledFrom) ?"null,null,":"")  +										//a_header ad_hoc_template.header%type default null, a_footer ad_hoc_template.footer%type default null,
    	    	"null,"+																								//a_composition_percent_operator ad_hoc_template.composition_percent_operator%type default null,
    	    	"null,"+																								//a_composition_percent_limit ad_hoc_template.composition_percent_limit%type default null,
    	    	"null,"+																								//a_amount_limit_operator ad_hoc_template.amount_limit_operator%type default null,
    	    	"null,"+																								//a_amount_limit ad_hoc_template.amount_limit%type default null,
    	    	"null,"+																								//a_amount_limit_unit ad_hoc_template.amount_limit_unit%type default null,
    	    	"null,"+																								//a_trace ad_hoc_template.trace%type default null,
    	    	"null,"+																								//a_trade_secret ad_hoc_template.trade_secret%type default null,
    	    	"null,"+																								//a_include_open_orders ad_hoc_template.include_open_orders%type default null,
    	    	"null,"+																								//a_gatekeeping ad_hoc_template.gatekeeping%type default null,
    	    	"null,"+																								//a_report_period_type ad_hoc_template.report_period_type%type default 'specificDates',
    	    	"null,"+																								//a_report_period_day ad_hoc_template.report_period_day%type default null,
    	    	"null,"+																								//a_email_subject ad_hoc_template.email_subject%type default null,
    	    	"null,"+																								//a_email_message ad_hoc_template.email_message%type default null,
    	    	"null,"+																								//a_email_user_group_id ad_hoc_template.email_user_group_id%type default null,
    	    	"null,"+																								//a_email_subject_neg ad_hoc_template.email_subject_neg%type default null,
    	    	"null,"+																								//a_email_message_neg ad_hoc_template.email_message_neg%type default null,
    	    	"null,"+																								//a_email_user_group_id_neg ad_hoc_template.email_user_group_id_neg%type default null,
    	    	("saveTemplate".equalsIgnoreCase(calledFrom) ?"null,null,null,":"")  +									//a_over_flam_ctrl_zn_limit,a_over_flam_ctrl_zn_lmt_percnt,a_flammability_control_zone_id
    	    	"null,"+																								//a_voc_zone_id or a_debug_email,
    	    	(!"Y".equalsIgnoreCase(bean.getFlammable()) ?"null":"'Y'") +","+										//a_flammable ad_hoc_template.flammable%type default null,
    	    	"null,"+																								//a_fp_test_detect ad_hoc_template.fp_test_detect%type default '<',
    	    	"null,"+																								//a_fp_test ad_hoc_template.fp_test%type default 100,
    	    	"null,"+																								//a_fp_test_unit ad_hoc_template.fp_test_unit%type default 'F',
    	    	"null,"+																								//a_nfpa_test_detect ad_hoc_template.nfpa_test_detect%type default '>=',
    	    	"null,"+																								//a_nfpa_test ad_hoc_template.nfpa_test%type default 3,
    	    	"null,"+																								//a_hmis_test_detect ad_hoc_template.hmis_test_detect%type default '>=',
    	    	"null,"+																								//a_hmis_test ad_hoc_template.hmis_test%type default 3,
    	    	"null,"+																								//a_positive_output ad_hoc_template.positive_output%type default 'Y',
    	    	"null,"+																								//a_negative_output ad_hoc_template.negative_output%type default 'N',
    	    	"null";																									// a_null_output ad_hoc_template.null_output%type default null

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
			query = "select pkg_ad_hoc_report.fx_display_length('MaterialMatrix','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "') from dual";
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
			query = "select pkg_ad_hoc_report.fx_sql_header('MaterialMatrix','" + reportFieldString + "','" + separator + "','" + chemicalListString + "') from dual";
			queryDataSet = dbManager.select(query);
			dsr = queryDataSet.getDataSetRow(1);
			//String columnNameString = dsr.getString(queryDataSet.getColumnName(1));
		   tmpClob = (Clob) dsr.get(queryDataSet.getColumnName(1));
			String columnNameString = tmpClob.getSubString((long)1,(int)tmpClob.length());
			String[] columnName = columnNameString.split(stringSplitSeparator);
			 */

			setMissingDataFromTemplate("createReport");

			String reportQuery = createStringForDatabaseFx("createReport");
			String fieldLengthNull = "-1";
			GenericSqlFactory myFactory = new GenericSqlFactory(dbManager,new BaseFieldViewBean());
			String query = "select base_field_id,name,display_length,hyperlink from table (pkg_ad_hoc_report.fx_column_param('MaterialMatrix','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "','" + chemicalListString +"','"+chemicalListFormatString+ "'))";
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

			//writing data to excel handler
			pw = new ExcelHandlerSXSSF(library);
			//row headers
			pw.addTable(library.getString("label.data"));

            //header
            if(!StringHandler.isBlankString(bean.getHeader())) {
				pw.addRow();
				pw.addTdRegionBold(bean.getHeader(),1,columnName.length);
			}

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
            query = "select pkg_ad_hoc_report.fx_sql_result("+reportQuery+") from dual";
            myFactory.selectCursorIntoExcel(query,dbManager.getConnection(),pw,library);

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
				excelFileName.append("AdHocMaterialMatrix").append(personnelBean.getPersonnelId()).append(Calendar.getInstance().getTimeInMillis()).append(".xlsx");
                excelFileNamePrefix.append("AdHocMaterialMatrix").append(personnelBean.getPersonnelId()).append(Calendar.getInstance().getTimeInMillis());
            } else {
				excelFileName.append(bean.getReportName().replaceAll(" ","_").replaceAll("[./:*?\\\"<>|]","_")).append("_").append(Calendar.getInstance().getTimeInMillis()).append(".xlsx");
                excelFileNamePrefix.append(bean.getReportName().replaceAll(" ","_").replaceAll("[./:*?\\\"<>|]","_")).append("_").append(Calendar.getInstance().getTimeInMillis());
            }

            //subject of email
            String tmpReportHeader = library.getString("adhocmaterialmatrixreport.title");
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

            this.writeReportHeader(pw,tmpReportHeader,tmpReportName);
			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				synchronized(this) {
					if (interactiveTimeout) {
						File excelFile = new File(resource.getString("saveltempreportpath")+excelFileName);
						FileOutputStream fos = new FileOutputStream(excelFile);
						pw.write(fos);
						//now email the file
						StringBuilder tmpMsg = new StringBuilder(library.getString("label.hereisyourreport")+": ");
                        tmpMsg.append(resource.getString("jnlpcodebase")).append(resource.getString("tempreporturl")).append(excelFileName);
                        if (pw.getDataRowCount() > pw.getMaxRowCount()) {
                        	tmpMsg.append("\n\n").append(library.getString("excel.message.maxrows"));
                        }
                        tmpMsg.append("\n\n").append(library.getString("label.reportdeletefromserver"));
                        MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, tmpEmailSub,tmpMsg.toString(),true);
					}
					else {
						interactiveTimeout = true;
					}
				}
			} else {
				//write file to temp directory
                File tempFile = File.createTempFile(excelFileNamePrefix.toString(), ".xlsx");
                FileOutputStream fos = new FileOutputStream(tempFile);
                pw.write(fos);
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
				writer.write("<html>");
                writer.write(library.getString("label.reportunexpectederror"));
                writer.write("</html>");
                writer.close();
			} else {
                ExcelHandlerSXSSF eh = new ExcelHandlerSXSSF(library);
			    writeErrorReport(eh);
                File tempFile = File.createTempFile("AdHocMaterialMatrix", ".xlsx");
				FileOutputStream fos = new FileOutputStream(tempFile);
				eh.write(fos);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocmaterialmatrixreport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xlsx", tempFile.getAbsolutePath(),true);
			}
			throw new BaseException(e);
		}
		log.debug("done with adhoc material matrix report");
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
		}
		catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace(System.out);
			ExcelHandlerSXSSF eh = new ExcelHandlerSXSSF(library);
			writeErrorReport(eh);
			eh.write(os);
		}
	}

	private void writeReportHeader(ExcelHandlerSXSSF eh, String reportHeader, String reportName) throws BaseException,Exception {

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
			Vector<FacilityBean> facilityBeans = (Vector<FacilityBean>)factory.selectQuery(query.toString());

			for(FacilityBean bean :facilityBeans)
				facilityName.append(bean.getFacilityName() + "; ");

		}else
			facilityName.append(bean.getFacilityName());

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

		if(bean.getReportCriteria().equalsIgnoreCase("USED")) {
			eh.addRow();
			eh.addCell(library.getString("label.begindate"));
			if (bean.getBeginDate() != null) {
				eh.addCell(bean.getBeginDateJsp().toString());
			} else {
				eh.addCell("");
			}
			eh.setColumnWidthNow(0, 20);
			eh.setColumnWidthNow(1, 20);
			eh.addRow();
			eh.addCell(library.getString("label.enddate"));
			Date myEndDate = null;
			if (bean.getEndDateJsp() != null) {
				if ("null".equals(templateName)) {
					myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDateJsp());
				} else {
					//if saving template then keep date as is
					myEndDate = bean.getEndDateJsp();
				}
			}
			if (myEndDate != null) {
				eh.addCell(myEndDate.toString());
			} else {
				eh.addCell("");
			}
		}

        eh.addRow();
		eh.addCell(library.getString("label.facilitygroup"));
		eh.addCell(bean.getFacilityGroupName());
		eh.addRow();
		eh.addCell(library.getString("label.facility"));
		eh.addCell(facilityName.toString());
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
		if(personnelBean.isFeatureReleased("ShowFlammabilityControlZone", bean.getFacilityId(),bean.getCompanyId()))
		{
			eh.addCell(library.getString("label.flammable"));
			eh.addCell(bean.getFlammable());
			eh.addRow();
		}
		eh.addCell(library.getString("label.workareagroup"));
		eh.addCell(bean.getReportingEntityName());
		eh.addRow();
		eh.addCell(library.getString("label.department"));
		eh.addCell(bean.getDeptName());
		eh.addRow();
		eh.addCell(library.getString("label.workarea"));
		eh.addCell(bean.getApplicationDesc());
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
		if(bean.getReportCriteria().equalsIgnoreCase("approved"))
			eh.addCell(library.getString("label.approved"));
		else
			eh.addCell(library.getString("label.associatewithfacility"));
		eh.addCell("");
		eh.addRow();
		eh.addCell(library.getString("label.search"));
		eh.addCell(bean.getSearchFieldDesc() + " " + bean.getMatchTypeDesc() + " " + bean.getSearchText());
		eh.addRow();
		eh.addCell(library.getString("label.manufacturer"));
		eh.addCell(bean.getManufacturer());
		eh.addRow();
		eh.addCell(library.getString("label.physicalstate"));
		eh.addCell(bean.getPhysicalState());
		eh.addRow();
		eh.addCell(library.getString("label.ph"));
		eh.addCell(bean.getPh() == null || bean.getPh().length() == 0 ? "" :bean.getPhCompare() + " " + bean.getPh());
		eh.addRow();
		eh.addCell(library.getString("label.flashpoint"));
		eh.addCell(bean.getFlashPoint() == null || bean.getFlashPoint().length() == 0  ? "" :bean.getFlashPointCompare() + " " + bean.getFlashPoint() + " " + bean.getTemperatureUnit());
		eh.addRow();
		eh.addCell(library.getString("label.vaporpressure"));
		eh.addCell(bean.getVaporPressure() == null || bean.getVaporPressure().length() == 0  ? "" :bean.getVaporPressureCompare() + " " + bean.getVaporPressure() + " " + bean.getVaporPressureUnit());
		eh.addRow();
		eh.addCell(library.getString("label.voc"));
		eh.addCell(bean.getVocPercent() == null || bean.getVocPercent().length() == 0  ? "" :bean.getVocPercentCompare() + " " + bean.getVocPercent() + " " + bean.getVocSearchSelect());
		eh.addRow();
		eh.addCell(library.getString("label.voclwes"));
		eh.addCell(bean.getVocLwesPercent() == null || bean.getVocLwesPercent().length() == 0  ? "" :bean.getVocLwesPercentCompare() + " " + bean.getVocLwesPercent() + " " + bean.getVocLwesSearchSelect());
		eh.addRow();
		eh.addCell("%" + library.getString("label.solids"));
		eh.addCell(bean.getSolidsPercent() == null || bean.getSolidsPercent().length() == 0  ? "" :bean.getSolidsPercentCompare() + " " +bean.getSolidsPercent());
		eh.addRow();
		String nfpa = library.getString("label.nfpa");
		eh.addCell(nfpa+ " " + library.getString("label.health"));
		eh.addCell(bean.getHealth() == null || bean.getHealth().length() == 0  ? "" :bean.getHealthCompare() + " " +bean.getHealth());
		eh.addRow();
		eh.addCell(nfpa+ " " + library.getString("label.flammability"));
		eh.addCell(bean.getFlammability() == null || bean.getFlammability().length() == 0  ? "" :bean.getFlammabilityCompare() + " " +bean.getFlammability());
		eh.addRow();
		eh.addCell(nfpa+ " " + library.getString("label.reactivity"));
		eh.addCell(bean.getReactivity() == null || bean.getReactivity().length() == 0  ? "" :bean.getReactivityCompare() + " " +bean.getReactivity());
		eh.addRow();
		eh.addCell(nfpa+ " " + library.getString("label.hazard"));
		eh.addCell(bean.getSpecificHazardDesc() == null || bean.getSpecificHazardDesc().length() == 0  ? "" :bean.getSpecificHazardDesc());
		eh.addRow();
		String hmis = library.getString("label.hmis");
		eh.addCell(hmis+ " " + library.getString("label.health"));
		eh.addCell(bean.getHmisHealth() == null || bean.getHmisHealth().length() == 0  ? "" :bean.getHmisHealthCompare() + " " +bean.getHmisHealth());
		eh.addRow();
		eh.addCell(hmis+ " " + library.getString("label.flammability"));
		eh.addCell(bean.getHmisFlammability() == null || bean.getHmisFlammability().length() == 0  ? "" :bean.getHmisFlammabilityCompare() + " " +bean.getHmisFlammability());
		eh.addRow();
		eh.addCell(hmis+ " " + library.getString("label.reactivity"));
		eh.addCell(bean.getHmisReactivity() == null || bean.getHmisReactivity().length() == 0  ? "" :bean.getHmisReactivityCompare() + " " +bean.getHmisReactivity());
		eh.addRow();
		eh.addCell(hmis+ " " + library.getString("label.personalProtection"));
		eh.addCell(bean.getPersonalProtectionDesc());
		eh.addRow();
		eh.addCell(library.getString("adhocusagereport.label.materialcategory"));
		eh.addCell(bean.getMaterialCategoryName());
		eh.addRow();
		eh.addCell(library.getString("label.workarea"));
		//eh.addRow();
		//eh.addCell("");
		eh.addCell(bean.getApplicationDesc());
		eh.addRow();
		eh.addCell("");
		eh.addCell("");
		eh.addRow();
		boolean isCas = bean.getGridType().equalsIgnoreCase("cas") ? true:false;
		if(isCas)
		{
			eh.addCell(library.getString("label.cas"));
			//eh.addCell("");
			eh.addCell(library.getString("label.chemicalname"));
		}
		else
		{
			eh.addCell(library.getString("label.list"));
			//eh.addCell("");
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
				if (result != null && result.size() > 0 )
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
					if(listFormatSplit != null && listFormatSplit.size() > 0 && gridRow.length > 0) {
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
					else
						constraint = "Average";
					//index++;
					eh.addCell(constraint);
					if(gridRowVals[index+1].equalsIgnoreCase("notlisted") || gridRowVals[index+1].equalsIgnoreCase("trace"))
					{
						eh.addCell(gridRowVals[index+1]);
						eh.addCell("");
					}
					else
					{
						eh.addCell(gridRowVals[index+1]);
						eh.addCell(gridRowVals[index+2]);
					}
				} else if (gridRowVals != null && gridRowVals.length > 0) {
					String constraint = "";
					if(gridRowVals[index].equalsIgnoreCase("OnList"))						
						constraint = "On List";
					else if(gridRowVals[index].equalsIgnoreCase("NotOnList"))
						constraint = "Not on List";
					else if(gridRowVals[index].equalsIgnoreCase("TriggersThreshold"))
						constraint = "Triggers Threshold";
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
		// if max row count was exceeded, write alert message
		if (eh.getDataRowCount() > eh.getMaxRowCount()) {
			// There should already be a new row added, so just add the cell
			eh.addCell(library.getString("excel.message.maxrows"));
		}
	}

	public boolean displayPartsInInventory() throws BaseException, Exception {
		boolean flag = false;
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		TcmisFeatureBeanFactory factory = new TcmisFeatureBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("feature", SearchCriterion.EQUALS, "materialMatrix.partsInInventory");
		criteria.addCriterion("featureMode", SearchCriterion.EQUALS,"1");
		if(factory.select(criteria, null).size() > 0) {
			flag = true;
		}
		return flag;
	}

	//inner class for running batch reporting
	class BatchReport extends Thread {
		public BatchReport() {
			super("MaterialMatrix");
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

	public Collection<FacAreaBlgFloorRmStgView> getStorageInfo(String storageIdString) throws Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FacAreaBlgFloorRmStgView());
		String search = "select * from fac_area_blg_floor_rm_wa_view where storage_area_id in ('"+storageIdString.replaceAll("\\|", "','")+"') order by STORAGE_AREA_DESC";
		return factory.selectQuery(search);
	}

	public Collection<AdHocInventoryInputBean> getGridInfo(boolean isList, String templateId) throws Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new AdHocInventoryInputBean());
		String search = "";
		if(isList)
			search = "select * from table (pkg_ad_hoc_report.fx_template_list("+ templateId +"))";
		else
			search = "select * from table (pkg_ad_hoc_report.fx_template_cas("+ templateId +"))";
		return factory.selectQuery(search);
	}

	public Collection<FeatureReleaseBean> getShowMaterialCategory(String companyId) throws Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FeatureReleaseBean());
		return factory.selectQuery("select * from feature_release where company_id = '"+companyId+"' and feature = 'ShowMaterialCategory'");
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