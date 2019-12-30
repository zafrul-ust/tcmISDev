package com.tcmis.client.catalog.process;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class CatalogQcReportProcess extends GenericProcess {

    ResourceLibrary library = null;
    String invoiceFilePath = null;
    DbManager dbManager;
    Connection connection = null;
    GenericSqlFactory genericSqlFactory;

    public CatalogQcReportProcess(String client) {
        super(client);
        library = new ResourceLibrary("report");
    }

    public void runCatalogQcReport(String companyId) throws BaseException {
        try {
            dbManager = new DbManager(getClient(), this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            StringBuilder emsg = new StringBuilder();
            //number of SDS qced last 7 days
            StringBuilder query = new StringBuilder("select count(*) from company_msds_qc where approve_date > sysdate - 7");
            BigDecimal numberOfMsdsQced = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            //number of data points companies interested in
            query = new StringBuilder("select count(*) from (");
            query.append("select distinct msds_column_name from vv_msds_qc_column where company_id in ('Radian'");
            if (!StringHandler.isBlankString(companyId))
                query.append(",'").append(companyId).append("'");
            query.append(")");
            query.append(" and (global_reportable = 'Y' or company_reportable = 'Y'))");
            BigDecimal numberOfDataPoints = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            //number of SDS with errors
            query = new StringBuilder("select count(*) from (");
            query.append(" select distinct cmq.material_id,cmq.revision_date from company_msds_qc cmq, company_msds_qc_detail cmqd");
            query.append(" where cmq.company_id = cmqd.company_id and cmq.material_id = cmqd.material_id");
            query.append(" and cmq.revision_date = cmqd.revision_date and cmq.approve_date > sysdate - 7");
            if (!StringHandler.isBlankString(companyId))
                query.append(" and cmq.company_id = '").append(companyId).append("'");
            query.append(" union ");
            query.append(" select distinct mq.material_id,mq.revision_date from msds_qc mq, msds_qc_detail mqd");
            query.append(" where mq.material_id = mqd.material_id and mq.revision_date = mqd.revision_date");
            query.append(" and exists (select null from company_msds_qc cmq where cmq.material_id = mq.material_id");
            query.append(" and cmq.revision_date = mq.revision_date and cmq.approve_date > sysdate -7");
            if (!StringHandler.isBlankString(companyId))
                query.append(" and cmq.company_id = '").append(companyId).append("'");
            query.append("))");
            BigDecimal numberOfMsdsWithErrors = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            //get number of errors found in qc
            query = new StringBuilder("select sum(error_count) error_count from (");
            query.append(" select count(*) error_count from company_msds_qc cmq, company_msds_qc_detail cmqd");
            query.append(" where cmq.company_id = cmqd.company_id and cmq.material_id = cmqd.material_id");
            query.append(" and cmq.revision_date = cmqd.revision_date and cmqd.column_name in (select msds_column_name");
            query.append(" from customer.vv_msds_qc_column where company_id in ('Radian'");
            if (!StringHandler.isBlankString(companyId))
                query.append(",'").append(companyId).append("'");
            query.append(")");
            query.append(" and (global_reportable = 'Y' or company_reportable = 'Y'))");
            query.append(" and cmq.approve_date > sysdate -7");
            query.append(" union all ");
            query.append(" select count(*) error_count from msds_qc mq, msds_qc_detail mqd");
            query.append(" where mq.material_id = mqd.material_id and mq.revision_date = mqd.revision_date");
            query.append(" and mqd.column_name in (select msds_column_name");
            query.append(" from customer.vv_msds_qc_column where company_id = 'Radian'");
            query.append(" and global_reportable = 'Y')");
            query.append(" and exists (select null from company_msds_qc cmq where cmq.material_id = mq.material_id");
            query.append(" and cmq.revision_date = mq.revision_date and cmq.approve_date > sysdate -7");
            if (!StringHandler.isBlankString(companyId))
                query.append(" and cmq.company_id = '").append(companyId).append("'");
            query.append("))");
            BigDecimal numberOfErrorFound = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            //number of data significant points companies interested in
            query = new StringBuilder("select count(*) from (");
            query.append("select distinct msds_column_name from vv_msds_qc_column where company_id =");
            if (!StringHandler.isBlankString(companyId))
                query.append(" '").append(companyId).append("'");
            query.append(" and company_reportable = 'Y' and critical_data = 'Y')");
            BigDecimal numberOfSignificantDataPoints = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            //number of significant data found in qc
            query = new StringBuilder("select sum(error_count) error_count from (");
            query.append(" select count(*) error_count from company_msds_qc cmq, company_msds_qc_detail cmqd");
            query.append(" where cmq.company_id = cmqd.company_id and cmq.material_id = cmqd.material_id");
            query.append(" and cmq.revision_date = cmqd.revision_date and cmqd.column_name in (select msds_column_name");
            query.append(" from customer.vv_msds_qc_column where critical_data = 'Y'");
            if (!StringHandler.isBlankString(companyId))
                query.append(" and company_id = '").append(companyId).append("'");
            query.append(")");
            query.append(" and cmq.approve_date > sysdate -7");
            query.append(" union all ");
            query.append(" select count(*) error_count from msds_qc mq, msds_qc_detail mqd");
            query.append(" where mq.material_id = mqd.material_id and mq.revision_date = mqd.revision_date");
            query.append(" and mqd.column_name in (select msds_column_name");
            query.append(" from customer.vv_msds_qc_column where critical_data = 'Y'");
            if (!StringHandler.isBlankString(companyId))
                query.append(" and company_id = '").append(companyId).append("'");
            query.append(")");
            query.append(" and mq.approve_date > sysdate -7");
            query.append(" and exists (select null from company_msds_qc cmq where cmq.material_id = mq.material_id");
            query.append(" and cmq.revision_date = mq.revision_date and cmq.approve_date > sysdate -7");
            if (!StringHandler.isBlankString(companyId))
                query.append(" and cmq.company_id = '").append(companyId).append("'");
            query.append("))");
            BigDecimal numberOfCriticalErrorFound = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));

            BigDecimal successRate = new BigDecimal(100 - ((numberOfErrorFound.floatValue()/(numberOfMsdsQced.floatValue()*numberOfDataPoints.floatValue()))*100));
            BigDecimal criticalSuccessRate = new BigDecimal(100 - ((numberOfCriticalErrorFound.floatValue()/(numberOfMsdsQced.floatValue()*numberOfSignificantDataPoints.floatValue()))*100));
            //putting data into body of email
            emsg.append("Below are the details that we QCed and completed to your company standard in the last 7 days.").append("\n");
            emsg.append("\n").append("# of [M]SDS QC\t\t\t\t: ").append(numberOfMsdsQced.intValue());
            emsg.append("\n").append("# of [M]SDS with problem\t\t: ").append(numberOfMsdsWithErrors.intValue());
            emsg.append("\n").append("Data Points Total\t\t\t: ").append(numberOfMsdsQced.intValue()*numberOfDataPoints.intValue());
            emsg.append("\n").append("Data Point Errors\t\t\t: ").append(numberOfErrorFound);
            emsg.append("\n").append("Data Point Success Rate\t\t: ").append(successRate.setScale(2,RoundingMode.HALF_UP).toString()).append("%");
            emsg.append("\n").append("Significant Data Errors\t\t\t: ").append(numberOfCriticalErrorFound);
            emsg.append("\n").append("Significant Data Success Rate\t\t: ").append(criticalSuccessRate.setScale(2,RoundingMode.HALF_UP).toString()).append("%");

            //writing data to excel handler
			ExcelHandlerSXSSF pw = new ExcelHandlerSXSSF(library);
			//row headers
			pw.addTable("Data");
			//row headers
			pw.addRow();
            pw.addCellBold("Material ID");
            pw.addCellBold("Revision Date");
            pw.addCellBold("URL");
            pw.addCellBold("Site");
            pw.addCellBold("MSDS Number");
            //fill in the data
            query = new StringBuilder("select m.material_id,m.revision_date,m.content,xref.customer_msds_db,xref.customer_msds_number");
            query.append(" from msds m, company_msds_qc cmq, customer_msds_xref xref");
            query.append(" where m.material_id = cmq.material_id and m.revision_date = cmq.revision_date and cmq.approve_date > sysdate -7");
            if (!StringHandler.isBlankString(companyId))
                query.append(" and cmq.company_id = '").append(companyId).append("'");
            query.append(" and cmq.company_id = xref.company_id(+) and cmq.material_id = xref.material_id(+)");
            query.append(" order by cmq.material_id,m.revision_date,customer_msds_db,customer_msds_number");
            ResourceLibrary resourceLib = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
            genericSqlFactory.selectIntoExcel(query.toString(),connection,pw,resourceLib);

            //write file to temp directory
            StringBuilder excelFileName = new StringBuilder("catalog_qc_report").append(Calendar.getInstance().getTimeInMillis()).append(".xlsx");
            StringBuilder excelFileNamePrefix = new StringBuilder("catalog_qc_report").append(Calendar.getInstance().getTimeInMillis());
            File tempFile = File.createTempFile(excelFileNamePrefix.toString(), ".xlsx");
            FileOutputStream fos = new FileOutputStream(tempFile);
            pw.write(fos);
            //send report to user group
            emsg.append("\n\n").append("Attached file contains all [M]SDS that we QCed the in last 7 days.");
            String maillist = genericSqlFactory.selectSingleValue("select string_agg(email) from personnel where company_id = 'LOCKHEED' and personnel_id in ( select personnel_id from user_group_member where company_id = 'LOCKHEED' and user_group_id = 'CatalogQcReportNotification')", connection);
            if (!isBlank(maillist)) {
                String to[] = maillist.split(",");
                for (int i = 0; i < to.length; i++)
                    MailProcess.sendEmail(to[i], "", MailProcess.DEFAULT_FROM_EMAIL, "Catalog QC Report", emsg.toString(), excelFileName.toString(), tempFile.getAbsolutePath(),true);
            } else {
                MailProcess.sendEmail("deverror@tcmis.com", "", MailProcess.DEFAULT_FROM_EMAIL, "Catalog QC Report - no user with CatalogQcNotification", emsg.toString(), excelFileName.toString(), tempFile.getAbsolutePath(),true);
            }
            fos.close();
        } catch (Exception ex) {
            String error = ex.getMessage();
            ex.printStackTrace();
            MailProcess.sendEmail("deverror@tcmis.com", "", "deverror@tcmis.com", "Error while running Catalog QC Report", error);
        } finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
        log.debug("DONE");
    } //end of method

} //end of class