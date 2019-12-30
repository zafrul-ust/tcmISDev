package com.tcmis.client.report.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.tcmis.client.report.beans.BaseFieldViewBean;
import com.tcmis.client.report.beans.PublishTemplateInputBean;
import com.tcmis.client.report.beans.UsageReportTemplateBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;

public interface IAdHocReportDataMapper {
	
	public static final String STAGE_PROCESS = "STAGE";
	public static final String QUERY_PROCESS = "QUERY";
	public static final String REPORT_PREP_PROCESS = "REPORT PREP";

	public String saveTemplate(UsageReportTemplateBean bean, PersonnelBean personnelBean, String client) 
			throws BaseException, SQLException;
	public String shareTemplate(BigDecimal templateId, String companyId, BigDecimal personnelId)
			throws BaseException, SQLException;
	public String publishTemplateToUserGroupAndCompany(PublishTemplateInputBean inputBean, String owner, String userGroupId, PersonnelBean personnelBean, String client)
			throws BaseException, SQLException;
	public String inactivateTemplate(BigDecimal templateId, String companyId)
			throws BaseException, SQLException;
	public String unshareTemplate(BigDecimal templateId, String companyId, BigDecimal userId)
			throws BaseException, SQLException;
	public String deleteTemplate(BigDecimal templateId, String companyId)
			throws BaseException, SQLException;
	public List<BaseFieldViewBean> columnParams(BigDecimal[] reportFieldList, String[] chemList, String[] chemListFormat, String reportType, String companyId, BigDecimal displayLength, Connection conn)
			throws BaseException, IllegalStateException, SQLException;
	public void logMessage(String process, String startStop, String source, String error, BigDecimal fileSize, Connection connection) 
			throws BaseException, SQLException;

}
