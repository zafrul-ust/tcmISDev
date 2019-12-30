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

import com.tcmis.client.report.beans.AdHocMaterialMatrixInputBean;
import com.tcmis.client.report.beans.AdHocTemplateBean;
import com.tcmis.client.report.beans.BaseFieldViewBean;
import com.tcmis.client.report.beans.ListBean;
import com.tcmis.client.report.beans.MaterialmatrixReportTemplateBean;
import com.tcmis.client.report.factory.TcmisFeatureBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
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
 * Process to create Ad Hoc Material Matrix report
 * @version 1.0
 *****************************************************************************/
public class AdHocMaterialMatrixReportOriginalProcess
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
    String chemicalListFormat = "";
    String templateName = "null";
	String reportType = "MaterialMatrix";

	public AdHocMaterialMatrixReportOriginalProcess(String client, Locale locale) {
		super(client);
		this.locale = locale;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}

	public void runReport(MaterialmatrixReportTemplateBean bean,
			PersonnelBean personnelBean, OutputStream os) throws
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
			bean.setFacilityId(tempBean.getFacilityId());
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
				//NEED TO SET ALL THE SELECTED REPORT FIELDS HERE
				//THIS WAY HTML OPTION WILL SELECTED THEM AND THEY CAN BE REMOVE THRU JAVASCRIPT
				bean.setBar(chemicalList);
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

	public void setMissingDataFromTemplate() {
		String urlPageArg = "adhocmaterialmatrixoriginal.do?submitValue=open&templateId=";
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

	public String saveTemplate(MaterialmatrixReportTemplateBean bean, PersonnelBean personnelBean)  throws BaseException {
		this.bean = bean;
		this.personnelBean = personnelBean;
		templateName = "'"+SqlHandler.validQuery(bean.getTemplateName())+"'";

		setMissingDataFromTemplate();

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
		chemicalFields = bean.getChemicalFieldList();
		reportFieldString = "";
		for (int i = 0; i < reportFields.length; i++) {
			reportFieldString += reportFields[i] + separator;
		}
		reportFieldString = StringHandler.stripLast(reportFieldString, separator);
		//check for space holder
		if ("xxblankxx".equals(reportFieldString)) {
			reportFieldString = "";
		}
		chemicalListString = "";
        chemicalListFormat = "";
        if ("saveTemplate".equalsIgnoreCase(calledFrom))
            chemicalListFormat = bean.getListFormat();

        if (chemicalFields != null) {
			for (int i = 0; i < chemicalFields.length; i++) {
				chemicalListString += chemicalFields[i] + separator;
                if (!"saveTemplate".equalsIgnoreCase(calledFrom))
                    chemicalListFormat += bean.getListFormat() + separator;
            }
			chemicalListString = StringHandler.stripLast(chemicalListString, separator);
            if (!"saveTemplate".equalsIgnoreCase(calledFrom))
                chemicalListFormat = StringHandler.stripLast(chemicalListFormat, separator);
        }
		if ("xxblankxx".equals(chemicalListString)) {
			chemicalListString = "";
            chemicalListFormat = "";
        }
		Date myEndDate = null;
		Date myBeginDate = bean.getBeginDate();
		if ("INVENTORY".equalsIgnoreCase(bean.getReportCriteria())) {
			myBeginDate = bean.getInventoryDate();
		}

		if (bean.getEndDate() != null) {
			if ("null".equals(templateName)) {
				myEndDate = DateHandler.add(Calendar.DATE, 1, bean.getEndDate());
			}else {
				//if saving template then keep date as is
				myEndDate =  bean.getEndDate();
			}
		}
		String partNumber = "null";
		String partNumberCriteria = "null";
		String tmpReportingEntityId = "''";
		if (bean.getPartNumber() != null && bean.getPartNumber().trim().length() > 0) {
			partNumber = "'"+SqlHandler.validQuery(bean.getPartNumber().trim())+"'";
		}
		if (bean.getPartNumberCriteria() != null) {
			partNumberCriteria = "'"+bean.getPartNumberCriteria()+"'";
		}
		String reportName = null;
		if (bean.getReportName() !=null && bean.getReportName().trim().length() > 0) {
			reportName = "'"+SqlHandler.validQuery(bean.getReportName())+"'";
		}

		if (!StringHandler.isBlankString(bean.getReportingEntityId()) &&
			 !"No Reporting Entity".equalsIgnoreCase(bean.getReportingEntityId())) {
			tmpReportingEntityId = "'"+bean.getReportingEntityId()+"'";
		}

		String lastModifiedBy = "''";
		String lastModifiedOn = "null";
		if (!"''".equalsIgnoreCase(bean.getTemplateId())) {
			lastModifiedBy = "'"+personnelBean.getPersonnelId()+"'";
			lastModifiedOn = "sysdate";
		}

		String functionValue =
		bean.getTemplateId()+","+																						//template id
		"'MaterialMatrix',"+																								//report type
		"'"+reportFieldString+"',"+          																		//base field id
		"'"+personnelBean.getPersonnelId()+"',"+            												   	//user id
		"'"+separator+"',"+                                 														//separator
		DateHandler.getOracleToDateFunction(myBeginDate, "yyyyMMdd", "YYYYMMDD")+","+      	      //start date
		DateHandler.getOracleToDateFunction(myEndDate, "yyyyMMdd", "YYYYMMDD")+","+                	//end date
		"null,"+																												//list id
		"null,"+																												//additional where
		"null,"+																												//additional from
		"null,"+     																											//CAS number
		"'"+bean.getFacilityId()+"',"+																					//facility id
		"'"+bean.getApplication()+"',"+																					//work area
		partNumber+","+																										//part number
		partNumberCriteria+","+					                                                     	//part number search by type
		"null,"+                                                                                   	//dock
		"null,"+                                                                                   	//delivery point
		"null,"+                                                                                   	//category
		"null,"+																												//manufacturer
		"null,"+                                                                                   	//mfg search by type
		"'N',"+                        																					//column name is alias
		"null,"+                                                                                   	//part location
		"null,"+                                                                                   	//part category
		"null,"+																												//accumulation point
		"null,"+																												//vendor id
		"null,"+            																								//waste item id
		"null,"+ 																												//waste management options
		"null,"+																												//exclude hub
		"'"+bean.getReportCriteria()+"',"+																			//material matrix type [PART,USED,APPROVED]
		"'"+chemicalListString+"',"+																						//chemical list
		"'"+chemicalListFormat+"',"+																					//chemical list format
		templateName+","+																									//template name
		"'"+bean.getReportGenerationType()+"',"+			 															//output mode [active,batch]
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

	public void createReport() throws BaseException,Exception {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		templateName = "null";
		try {

			setMissingDataFromTemplate();

			String reportQuery = createStringForDatabaseFx("createReport");
			String fieldLengthNull = "-1";
			GenericSqlFactory myFactory = new GenericSqlFactory(dbManager,new BaseFieldViewBean());
			String query = "select base_field_id,name,display_length,hyperlink from table (pkg_ad_hoc_report.fx_column_param('MaterialMatrix','" + reportFieldString + "','" + separator + "','" + fieldLengthNull + "','" + chemicalListString + "','"+chemicalListFormat+"'))";
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
				File tempFile = File.createTempFile("AdHocMaterialMatrix", ".xls");
				FileOutputStream fos = new FileOutputStream(tempFile);
				pw.write(fos);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocmaterialmatrixreport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xls", tempFile.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace(System.out);
			ExcelHandler eh = new ExcelHandler(library);
			writeErrorReport(eh);
			if ("interactive".equalsIgnoreCase(bean.getReportGenerationType())) {
				eh.write(os);
			} else {
				File tempFile = File.createTempFile("AdHocMaterialMatrix", ".xls");
				FileOutputStream fos = new FileOutputStream(tempFile);
				eh.write(fos);
				//now email the file
				MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("adhocmaterialmatrixreport.title"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xls", tempFile.getAbsolutePath());
			}
			throw new BaseException(e);
		}
		log.debug("done with adhoc material matrix report");
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
		eh.addTdRegionBold(library.getString("adhocmaterialmatrixreport.title"),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.date") + ": " + DateHandler.formatDate(new Date(), library.getString("java.dateformat"), locale),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.time") + ": " + DateHandler.formatDate(new Date(), "h:mm a z", locale),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.selectioncriteria"),1,4);
		eh.addRow();
		String temp = "";
		eh.addTdRegionBold(library.getString("label.facility")+": "+StringHandler.emptyIfNull(bean.getFacilityName()),1,4);
		eh.addRow();
		eh.addTdRegionBold(library.getString("label.workarea")+": "+StringHandler.emptyIfNull(bean.getApplicationDesc()),1,4);
		eh.addRow();
		if("PART".equalsIgnoreCase(bean.getReportCriteria())) {
			temp = "(" + StringHandler.emptyIfNull(bean.getPartNumberCriteria()) + ") " + StringHandler.emptyIfNull(bean.getPartNumber());
		}else if("APPROVED".equalsIgnoreCase(bean.getReportCriteria())) {
			temp = library.getString("label.partsapproved");
		}else if("USED".equalsIgnoreCase(bean.getReportCriteria())) {
			temp = library.getString("label.partsusedbetween");
			if (bean.getBeginDate() != null) {
				temp += " "+DateHandler.formatDate(bean.getBeginDate(), library.getString("java.dateformat"), locale);
			}
			if (bean.getEndDate() != null) {
				temp += " and "+DateHandler.formatDate(bean.getEndDate(), library.getString("java.dateformat"), locale);
			}
		}
		eh.addTdRegionBold(library.getString("label.partnumber")+": "+StringHandler.emptyIfNull(temp),1,4);
		eh.addRow();
		if(bean.getReportingEntity() != null) {
			eh.addTdRegionBold(library.getString("label.workareagroup")+": "+StringHandler.emptyIfNull(bean.getReportingEntity()),1,4);
			eh.addRow();
		}
		eh.addTdRegionBold(library.getString("adhocmaterialmatrixreport.label.listformat")+": "+StringHandler.emptyIfNull(bean.getListFormat()),1,4);
		eh.addRow();
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
}