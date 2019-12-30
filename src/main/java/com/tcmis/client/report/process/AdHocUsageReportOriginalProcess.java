package com.tcmis.client.report.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.tcmis.client.report.beans.AdHocUsageInputBean;
import com.tcmis.client.report.beans.BaseFieldViewBean;
import com.tcmis.client.report.beans.FacAppDockDpViewBean;
import com.tcmis.client.report.beans.FacAppReportViewBean;
import com.tcmis.client.report.beans.UsageReportTemplateBean;
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

/**
 * ***************************************************************************
 * Process to create Ad Hoc Usage report
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class AdHocUsageReportOriginalProcess extends BaseExcelReportProcess {
	Log log = LogFactory.getLog(this.getClass());
	UsageReportTemplateBean bean;
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
	String reportType = "AdHocUsage";

	public AdHocUsageReportOriginalProcess(String client,Locale locale) {
		super(client);
		this.locale = locale;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}

	public void runReport(UsageReportTemplateBean bean, PersonnelBean personnelBean, OutputStream os) throws BaseException, Exception {
		this.bean = bean;
		this.personnelBean = personnelBean;
		this.os = os;
		writer = new OutputStreamWriter(os);
		if ("batch".equalsIgnoreCase(bean.getReportGenerationType())) {
			BatchReport bp = new BatchReport();
			new Thread(bp).start();
			writer.write("<html>");
			writer.write(library.getString("label.batchreportmessage") + " " + personnelBean.getEmail());
			writer.write("</html>");
			writer.close();
		} else {
			createReport();
		}
	}


	public void convertDateStringToDateObject(AdHocUsageInputBean bean, UsageReportTemplateBean templateBean) {
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
					bean.setName(tmpBean.getName());
					result.add(bean);
					break;
				}
			}
		}
		return result;
	}

	public void copyData(Collection baseFieldBeanCollection,Collection templateData, AdHocUsageInputBean bean) {
		SimpleDateFormat dateParser = new SimpleDateFormat(library.getString("java.dateformat"),locale);
		dateParser.setLenient(false);
		Iterator iter = templateData.iterator();
		while (iter.hasNext()) {
			AdHocTemplateBean tempBean = (AdHocTemplateBean)iter.next();
			bean.setTemplateName(tempBean.getTemplateName());
			bean.setReportType(tempBean.getQueryType());   			//list, singleChemical, or all

			bean.setChemicalListName(tempBean.getListId());
			bean.setCasNumber(tempBean.getCasNumber());
			bean.setFacilityId(tempBean.getFacilityId());
			bean.setReportingEntityId(tempBean.getReportingEntityId());
			bean.setApplication(tempBean.getApplication());
			bean.setDockId(tempBean.getDockLocationId());
			bean.setDeliveryPoint(tempBean.getDeliveryPoint());
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

			String materialCategory = "";
			if (tempBean.getCategoryId() != null) {
				materialCategory = tempBean.getCategoryId().toString();
			}
			bean.setMaterialCategory(materialCategory);
			bean.setPartNumberCriteria(tempBean.getCatPartNoSearchType());
			bean.setPartNumber(tempBean.getCatPartNo());
			bean.setManufacturerCriteria(tempBean.getManufacturerSearchType());
			bean.setManufacturer(tempBean.getManufacturer());
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

	public void setMissingDataFromTemplate() {
		String urlPageArg = "adhocusageoriginal.do?submitValue=open&templateId=";
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

	public String saveTemplate(UsageReportTemplateBean bean, PersonnelBean personnelBean)  throws BaseException {
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
	} //end of method

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
		String chemicalList = "null";
		String casNumber = "null";
		String partNumber = "null";
		String materialCategory = "null";
		String mfg = "null";
		String partLocation = "null";
		String partCategory = "null";
		String reportName = "null";
		String tmpReportingEntityId = "''";
		if (bean.getReportType() != null && bean.getReportType().trim().length() > 0 && bean.getReportType().equalsIgnoreCase("list")) {
			if (bean.getChemicalListName() != null && bean.getChemicalListName().trim().length() > 0) {
				chemicalList = "'" + SqlHandler.validQuery(bean.getChemicalListName()) + "' ";
			}
		} else if (bean.getReportType() != null && bean.getReportType().trim().length() > 0 && bean.getReportType().equalsIgnoreCase("singleChemical")) {
			casNumber = "'" + SqlHandler.validQuery(bean.getCasNumber()) + "' ";
		}
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
			if (bean.getReportType() != null && bean.getReportType().trim().length() > 0) {
				partCategory = "'"+bean.getReportType()+"'";
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

		String functionValue =
		bean.getTemplateId()+","+																						//template id
		"'AdHocUsage',"+																									//report type
		"'"+reportFieldString+"',"+          																		//base field id
		"'"+personnelBean.getPersonnelId()+"',"+            												   //user id
		"'"+separator+"',"+                                 													//separator
		DateHandler.getOracleToDateFunction(bean.getBeginDate(), "yyyyMMdd", "YYYYMMDD")+","+      //start date
		DateHandler.getOracleToDateFunction(myEndDate, "yyyyMMdd", "YYYYMMDD")+","+                //end date
		chemicalList+","+																									//list id
		"null,"+																												//additional where
		"null,"+																												//additional from
		casNumber+","+																										//CAS number
		"'"+bean.getFacilityId()+"',"+																					//facility id
		"'"+bean.getApplication()+"',"+																				//work area
		partNumber+","+																										//part number
		"'"+bean.getPartNumberCriteria()+"',"+                                                     //part number search by type
		tmpDockId+","+				                                                                  //dock
		tmpDeliveryPoint+","+				                                                          //delivery point
		materialCategory+","+                                                                      //category
		mfg+","+																												//manufacturer
		"'"+bean.getManufacturerCriteria()+"',"+                                                   //mfg search by type
		"'N',"+                        																				//column name is alias
		partLocation+","+                                                                          //part location
		partCategory+","+				 																					//part category
		"null,"+																												//accumulation point
		"null,"+																												//vendor id
		"null,"+       																										//waste item id
		"null,"+																												//waste management options
		"null,"+																												//exclude hub
		"'"+bean.getReportType()+"',"+																					//query type [list,singlechemical,all]
		"null,"+																												//chemical list
		"null,"+																												//chemical list format
		templateName+","+       																							//template name
		"'"+bean.getReportGenerationType()+"',"+																	//output mode [active,batch]
		reportName+","+																										//report name
		"null,"+																												//management option desc [waste]
		"null,"+                                                                                    //waste profile desc [waste]
		"'"+bean.getCompanyId()+"',"+		      	   															//company_id
		"'N',"+																												//debug
		tmpReportingEntityId+","+																						//reporting entity id
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
		"null,"+																												//po number
        "null,"+                                                                                                                // area_id
		"null,"+			                                                                                                    // building_id
		"null,"+			                                                                                                    // room_id
        "null,"+                                                                                                                //facility_group
        "null,"+                                                                                                                //division
        "null,"+			                                                                                                    // customer_part_no
		"null,"+			                                                                                                    // shipping_reference
		"null,"+                                                                                                               // customer_invoice_no
        "null,"+	                                                                                                            //invoice number
        "null,"+                                                                                                                //search parameter
        "null,"+                                                	                                                            //search value
        "null,"+                                                	                                                            //msds match type
        "null,"+                                            	                                                                //mfg
    	"null,"+                                                    	                                                        //physical state
    	"null,"+                                    	                                                                        //ph
    	"null,"+                                                	                                                            //ph compare
    	"null,"+                                                	                                                            //flash point
    	"null,"+                                                        	                                                    //flash point compare
    	"null,"+	                                                                                                            //flash point unit
    	"null,"+	                                                                                                            //vapor pressure
    	"null,"+	                                                                                                            //vapor pressure compare
    	"null,"+	                                                                                                            //vapor pressure unit
    	"null,"+	                                                                                                            //voc
    	"null,"+	                                                                                                            //voc compare
    	"null,"+                                                                                                                //voc unit
    	"null,"+                                                                                                                //solid
        "null,"+                                                                                                                //solid compare
    	"null,"+                                                                                                                //nfpa health
    	"null,"+                                                                                                                //nfpa health compare
    	"null,"+                                                                                                                //nfpa flammability
    	"null,"+                                                                                                                //nfpa flammability compare
    	"null,"+                                                                                                                //nfpa reactivity
    	"null,"+                                                                                                                //nfpa reactivity compare
    	"null,"+                                                                                                                //specific hazard
    	"null,"+                                                                                                                //hmis health
    	"null,"+                                                                                                                //hmis health compare
    	"null,"+                                                                                                                //hmis flammability
    	"null,"+                                                                                                                //hmis flammability compare
    	"null,"+                                                                                                                //hmis reactivity
    	"null,"+                                                                                                                //hmis reactivity compare
    	"null,"+                                                                                                                //personal protection
        "null,"+                                                                                                                //constraint seperator
        "null,"+	                                                                                                            //full database
        "null,"+	                                                                                                            //approved
        "null,"+                                                                                                                //kit only
        "null,"+                                                                                                                //stocked
      	"null,"+                                                                                                                //voc less water exempt
    	"null,"+                                                                                                                //voc less water exempt compare
    	"null,"+                                                                                                               //vol less water exempt unit
    	"null,"+                                                                                                                 //dept
        "null";                                                                                                                 //material category

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

	/*
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
	*/


	public void createReport() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		templateName = "null";
        Connection connection = dbManager.getConnection();
        GenericSqlFactory myFactory;
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

			/*
			Clob tmpClob = (Clob) dsr.get(queryDataSet.getColumnName(1));
			String reportQuery = tmpClob.getSubString((long)1,(int)tmpClob.length());
			 */
			//get display length for report fields

			/*
			query = "select pkg_ad_hoc_report.fx_display_length('AdHocUsage','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "') from dual";
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
			query = "select pkg_ad_hoc_report.fx_sql_header('AdHocUsage','" + reportFieldString + "','" + separator + "',null) from dual";
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
			myFactory = new GenericSqlFactory(dbManager,new BaseFieldViewBean());
			String query = "select base_field_id,name,display_length,hyperlink from table (pkg_ad_hoc_report.fx_column_param('AdHocUsage','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "'))";
			Collection paramColl = myFactory.selectQuery(query,connection);
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
			else
				gridQuery = bean.getGridSubmit();

            String chemicalList = "";
            String casNumber = "";
            if (bean.getReportType() != null && bean.getReportType().trim().length() > 0 && bean.getReportType().equalsIgnoreCase("list")) {
                if (bean.getChemicalListName() != null && bean.getChemicalListName().trim().length() > 0) {
                    chemicalList = bean.getChemicalListName();
                }
            } else if (bean.getReportType() != null && bean.getReportType().trim().length() > 0 && bean.getReportType().equalsIgnoreCase("singleChemical")) {
                casNumber = bean.getCasNumber();
            }

            //NEED TO CALL STAGE TABLE BEFORE CALLING RESULT
            //NEED TO TURN OFF AUTO COMMIT
            connection.setAutoCommit(false);
            Collection inArgs = new Vector(8);
            inArgs.add(personnelBean.getPersonnelIdBigDecimal());  					//-- a_personnel_id personnel.personnel_id%type,
            inArgs.add(personnelBean.getCompanyId());  								//--a_company_id facility.company_id%type,
            inArgs.add(reportFieldString);											//--a_list varchar2,
            inArgs.add(bean.getBeginDate());										//--a_start date default sysdate,
            Date myEndDate = null;
            if (bean.getEndDate() != null) {
                myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDate());
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
            inArgs.add(chemicalList);                                               //--a_list_id ad_hoc_template.list_id%type default null,
            inArgs.add(casNumber);	                                                //--null, --a_cas_number varchar2,
            inArgs.add(null);														//--a_area_id varchar2 default null,
            inArgs.add(null);														//--a_building_id varchar2 default null,
            inArgs.add(null);														//--a_floor_id customer.floor.floor_id%type default null,
            inArgs.add(null);														//--a_room_id room.room_id%type default null,
            inArgs.add(null);														//--a_mfg_id manufacturer.mfg_id%type default null,
            inArgs.add(null);														//--a_dept_id varchar2 default null,
            inArgs.add(separator);													//--a_sep varchar2 default '|'
            myFactory.doProcedure(connection,"pkg_ad_hoc_report.p_stage_usage",inArgs);

            //get data
			query = "select pkg_ad_hoc_report.fx_sql_result("+reportQuery+") from dual";
			Object[] myData = myFactory.selectCursorIntoObjectArray(query,connection);
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
							if (rowData[i] != null) {
                                pw.addCell((String)rowData[i]);
                            }else {
                                pw.addCell("");
                            }
							break;
					}
				}
				firstRow = false;
			}

			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				pw.write(os);
			} else {
				File tempFile = File.createTempFile("AdHocUsage", ".xls");
				FileOutputStream fos = new FileOutputStream(tempFile);
				pw.write(fos);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocusagereport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xls", tempFile.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace(System.out);
			ExcelHandler eh = new ExcelHandler(library);
			writeErrorReport(eh);
			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				eh.write(os);
			} else {
				File tempFile = File.createTempFile("AdHocUsage", ".xls");
				FileOutputStream fos = new FileOutputStream(tempFile);
				eh.write(fos);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocusagereport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xls", tempFile.getAbsolutePath());
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

	private void writeErrorReport (ExcelHandler eh) {
		eh.addRow();
		eh.setColumnParagraph(0);
		eh.setColumnWidthNow(0,50);
		eh.addCellBold(library.getString("label.reportunexpectederror"));
		eh.addRow();
	}

	private void writeReportHeader(ExcelHandler eh) throws BaseException, Exception {
		eh.addRow();
		eh.addTdRegionBold(library.getString("adhocusagereport.title"),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.date") + ": " + DateHandler.formatDate(new Date(), library.getString("java.dateformat"), locale),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.time") + ": " + DateHandler.formatDate(new Date(), "h:mm a z", locale),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.selectioncriteria"),1,4);
		eh.addRow();
		if ("All".equalsIgnoreCase(bean.getReportType())) {
			eh.addTdRegionBold(library.getString("label.chemicals") + ": "+library.getString("label.all"),1,4);
		} else if ("singleChemical".equalsIgnoreCase(bean.getReportType())) {
			eh.addTdRegionBold(library.getString("label.chemicals") + ": "+StringHandler.emptyIfNull(bean.getCasNumber()),1,4);
		} else if ("list".equalsIgnoreCase(bean.getReportType())) {
			eh.addTdRegionBold(library.getString("label.chemicals") + ": "+StringHandler.emptyIfNull(bean.getListDesc()),1,4);
		}

		eh.addRow();
		eh.addTdRegionBold(library.getString("label.facility")+": "+StringHandler.emptyIfNull(bean.getFacilityName()),1,4);
		if (bean.getReportingEntity() != null) {
			eh.addRow();
			eh.addTdRegionBold(library.getString("label.workareagroup")+": "+StringHandler.emptyIfNull(bean.getReportingEntity()),1,4);
		}
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.workarea")+": "+StringHandler.emptyIfNull(bean.getApplicationDesc()),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.dock")+": "+StringHandler.emptyIfNull(bean.getDockDesc()),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.deliverypoint")+": "+StringHandler.emptyIfNull(bean.getDeliveryPointDesc()),1,4);
		eh.addRow();
		String tmp = "";
		if (bean.getPartNumber() != null && bean.getPartNumber().length() > 0) {
			tmp ="(" + StringHandler.emptyIfNull(bean.getPartNumberCriteria()) + ") " + StringHandler.emptyIfNull(bean.getPartNumber());
		}
		eh.addTdRegionBold(library.getString("label.partnumber")+": "+tmp,1,4);
		eh.addRow();
		//eh.addTdRegionBold(library.getString("label.category")+": "+StringHandler.emptyIfNull(bean.getCategoryDesc()),1,4);
		//eh.addRow();
		tmp = "";
		if (bean.getManufacturer() != null && bean.getManufacturer().length() > 0) {
			tmp ="(" + StringHandler.emptyIfNull(bean.getManufacturerCriteria()) + ") " + StringHandler.emptyIfNull(bean.getManufacturer());
		}
		eh.addTdRegionBold(library.getString("label.manufacturer")+": "+tmp,1,4);
		eh.addRow();
		tmp = "";
		if (bean.getBeginDate() != null) {
			tmp = DateHandler.formatDate(bean.getBeginDate(), library.getString("java.dateformat"), locale);
		}
		eh.addTdRegionBold(library.getString("label.begindate")+": "+tmp,1,4);
		eh.addRow();
		tmp = "";
		if (bean.getEndDate() != null) {
			tmp = DateHandler.formatDate(bean.getEndDate(), library.getString("java.dateformat"), locale);
		}
		eh.addTdRegionBold(library.getString("label.enddate")+": "+tmp,1,4);
		eh.addRow();
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
}