package com.tcmis.client.report.process;

import java.util.*;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.ReportTemplateFieldBean;
import com.tcmis.client.report.beans.ReportTemplateInputBean;
import com.tcmis.client.report.beans.AdHocTemplateBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;


/******************************************************************************
 * Process to create report template
 * @version 1.0
 *****************************************************************************/
public class ReportTemplateProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library;

	public ReportTemplateProcess(String client, Locale locale) {
		super(client,locale);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}

	public Collection getReportType() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AdHocTemplateBean());
		StringBuilder query = new StringBuilder("select * from vv_report_type order by display_order");
		return factory.selectQuery(query.toString());
	}

	public Collection getTemplates(ReportTemplateInputBean inputBean,PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AdHocTemplateBean());
    	StringBuilder query = new StringBuilder("select * from ad_hoc_template where personnel_id = ").append(personnelBean.getPersonnelId());
		StringBuilder reportType = getReportTypeFromInputBean(inputBean);
		if (reportType.length() > 0) {
			query.append(" and report_type in (").append(reportType.toString()).append(")");
		}

		if (!StringHandler.isBlankString(inputBean.getSearchText())) {
			query.append(" and (").append(doSearchLogic(parseStringForGlobalizationLabelLetter(inputBean.getSearchText()))).append(")");
		}
		query.append(" order by report_type,template_name");
		return factory.selectQuery(query.toString());
	} //end of method

	private String checkPattern(Collection dataPatternColl,String txt) {
		String result = txt;
		try {
			Iterator iter = dataPatternColl.iterator();
			while (iter.hasNext()) {
				AdHocTemplateBean bean = (AdHocTemplateBean)iter.next();
				String tmp = library.getString(bean.getGlobalizationLabelLetter());
				if (txt.toLowerCase().startsWith(tmp.toLowerCase())) {
					String tmp2 = txt.substring(tmp.length());
					try {
						int t = Integer.parseInt(tmp2);
						if (t > -1) {
							if (bean.getGlobalizationLabelLetter() != null)
								return bean.getGlobalizationLabelLetter()+tmp2;
							else 
								return tmp2;
						}
					}catch(Exception ee) {
					}
				}
			}
		}catch(Exception e) {
		}
		return result;
	}
	private String parseStringForGlobalizationLabelLetter(String searchText) {
		String result = searchText;
		try {
			Collection dataPatternColl = getReportType();
			String tmpSearchText = "";
			String[] dataArray = searchText.split(" ");
			for (int i = 0; i < dataArray.length; i++) {
				if (i > 0) {
					tmpSearchText += " ";
				}
				tmpSearchText += checkPattern(dataPatternColl,dataArray[i]);
			}
			result = tmpSearchText;
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","parseStringForGlobalizationLabelLetter failed:"+this.getLocale(),searchText);
		}
		return result;
	}	//end of method

	public String doSearchLogic(String search) {
		Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " lower(globalization_label_letter||template_id||' '||template_name||' '||template_description||' '||created_by_name) like lower('%" + SqlHandler.validQuery(search) + "%')";
			return result;
		}

		//contains operation in search text
		result += " lower(globalization_label_letter||template_id||' '||template_name||' '||template_description||' '||created_by_name) like lower('%" + SqlHandler.validQuery(likes.elementAt(0).toString().trim()) + "%') ";
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			result += " " + op + " lower(globalization_label_letter||template_id||' '||template_name||' '||template_description||' '||created_by_name) " + lk + " lower('%" + SqlHandler.validQuery(searchS) + "%') ";
		}

		return result;
	}

	public ExcelHandler getExcelReport(ReportTemplateInputBean inputBean, PersonnelBean personnelBean, Locale locale)	throws BaseException, Exception {
		Collection<AdHocTemplateBean> data = getTemplates(inputBean,personnelBean);

		ExcelHandler pw = new ExcelHandler(library);

		// write column headers
		pw.addRow();

		String[] headerkeys =
		{
			 "label.id","label.name","label.description","label.reporttype","label.createdby","label.date","label.lastupdated","label.date"
		};
		int[] widths = {12,12,12,12,12,12,12,12 };
		//pw.TYPE_CALENDAR
		int[] types =
		{
			pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_CALENDAR,pw.TYPE_STRING,pw.TYPE_CALENDAR
		};

		int[] aligns = {0,0,0,0,0,0,0,0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (AdHocTemplateBean member : data) {
			pw.addRow();
			pw.addCell(member.getTemplateId());
			pw.addCell(member.getTemplateName());
			pw.addCell(member.getTemplateDescription());
			String reportType = library.getString(member.getGlobalizationLabel());
			pw.addCell(reportType);
			pw.addCell(member.getCreatedByName());
			pw.addCell(member.getDateCreated());
			pw.addCell(member.getLastModifiedByName());
			pw.addCell(member.getLastModifiedOn());
		}
		return pw;

	}

	private StringBuilder getReportTypeFromInputBean(ReportTemplateInputBean inputBean) {
		StringBuilder reportType = new StringBuilder("");
		if (!StringHandler.isBlankString(inputBean.getReportType0())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType0()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType0()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType1())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType1()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType1()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType2())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType2()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType2()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType3())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType3()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType3()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType4())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType4()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType4()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType5())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType5()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType5()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType6())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType6()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType6()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType7())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType7()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType7()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType8())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType8()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType8()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType9())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType9()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType10()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType10())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType10()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType10()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType11())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType11()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType11()).append("'");
			}
		}
		if (!StringHandler.isBlankString(inputBean.getReportType12())) {
			if (reportType.length() == 0) {
				reportType.append("'").append(inputBean.getReportType12()).append("'");
			}else {
				reportType.append(",'").append(inputBean.getReportType12()).append("'");
			}
		}
		return reportType;
	}	

} //end of class