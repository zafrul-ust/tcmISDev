package com.tcmis.client.report.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.factory.SearchDropDownBeanFactory;
import com.tcmis.common.admin.beans.KeyValuePairBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.report.factory.ListBeanFactory;
import com.tcmis.client.report.beans.TcmisConstantBean;
import com.tcmis.client.report.factory.TcmisConstantBeanFactory;
import com.tcmis.client.report.factory.MsdsRevisionViewBeanFactory;
import com.tcmis.client.report.beans.FormattedUsageInputBean;
import com.tcmis.client.report.beans.UsageReportTemplateBean;
import com.tcmis.client.report.beans.MsdsRevisionViewBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class FormattedMsdsRevisionProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FormattedMsdsRevisionProcess(String client) {
    super(client);
  }

  public void createReport(FormattedUsageInputBean inputBean,
                           PersonnelBean personnelBean, Writer writer, Locale locale) throws
      BaseException,
      Exception {
      ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        DbManager dbManager = new DbManager(getClient());
        MsdsRevisionViewBeanFactory factory = new MsdsRevisionViewBeanFactory(dbManager);
        SearchCriteria criteria = new SearchCriteria();
        if(!StringHandler.isBlankString(inputBean.getFacilityId())) {
            criteria.addCriterion("facility", SearchCriterion.EQUALS, inputBean.getFacilityId());
        }
        Collection<MsdsRevisionViewBean> c = factory.select(criteria, new SortCriteria());
      writer.write("<html>");

      this.writeReportHeader(inputBean, writer, locale);
      writer.write("<table border=\"1\">");
      writer.write("<tr>");
      writer.write("<th>"+library.getString("label.partnumber")+"</th>");
      writer.write("<th>"+library.getString("label.tradename")+"</th>");
      writer.write("<th>"+library.getString("label.manufacturer")+"</th>");
      writer.write("<th>"+library.getString("label.lastrevisiondate")+"</th>");
      writer.write("<th>"+library.getString("label.lastrequestdate")+"</th>");
      writer.write("</tr>");
        for(MsdsRevisionViewBean bean:c){
            writer.write("<tr>");
            writer.write("<td>" + StringHandler.emptyIfNull(bean.getPartNo()) + "</td>");
            writer.write("<td>" + StringHandler.emptyIfNull(bean.getTradeName()) + "</td>");
            writer.write("<td>" + StringHandler.emptyIfNull(bean.getManufacturer()) + "</td>");
            writer.write("<td>" + StringHandler.emptyIfNull(bean.getLastRevisionDate()) + "</td>");
            writer.write("<td>" + StringHandler.emptyIfNull(bean.getLastRequestDate()) + "</td>");
            writer.write("</tr>");
        }
      writer.write("</table>");
      writer.write("</html>");
        writer.flush();
        writer.close();
  }

      private void writeReportHeader(FormattedUsageInputBean bean,Writer writer,Locale locale) throws
      BaseException,
      Exception {
      ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
      writer.write("<table>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");

      writer.write(library.getString("formattedmsdsrevisionreport.title"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write(library.getString("label.date") + " " + DateHandler.formatDate(new Date(), "MM/dd/yyyy"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write(library.getString("label.time") + " " + DateHandler.formatDate(new Date(), "h:mm a z"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write(library.getString("label.facility"));
      writer.write("</td>");
      writer.write("<td>");
      writer.write(StringHandler.emptyIfNull(bean.getFacilityId()));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("</table>");
  }
}