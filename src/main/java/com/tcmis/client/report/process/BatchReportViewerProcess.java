package com.tcmis.client.report.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.io.Writer;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.report.factory.BatchReportBeanFactory;
import com.tcmis.client.report.beans.BatchReportViewerInputBean;
import com.tcmis.client.report.beans.BatchReportBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.exceptions.BaseException;

/******************************************************************************
 * Process to build a web page for user to view his/her batch reports.
 * @version 1.0
 *****************************************************************************/
public class BatchReportViewerProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public BatchReportViewerProcess(String client) {
    super(client);
  }

  public Collection getSearchResult(BigDecimal personnelId) throws Exception {
    DbManager dbManager = new DbManager(getClient());
    BatchReportBeanFactory factory = new BatchReportBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("personnelId", SearchCriterion.EQUALS, personnelId.toString());
	 criteria.addCriterion("status", SearchCriterion.IS, "null");
	 SortCriteria sortCriteria = new SortCriteria();
    sortCriteria.addCriterion("reportDate");
    return factory.select(criteria,sortCriteria);
  } //end of method

	public void deleteReport(Collection beans) throws Exception {
    DbManager dbManager = new DbManager(getClient());
	 if (beans != null && beans.size() > 0) {
	 	BatchReportBeanFactory factory = new BatchReportBeanFactory(dbManager);
		Iterator iter = beans.iterator();
		while(iter.hasNext()) {
			BatchReportViewerInputBean inputBean = (BatchReportViewerInputBean)iter.next();
			if ("delete".equals(inputBean.getModified())) {
			   String whereClause = "personnel_id = "+inputBean.getPersonnelId().toString()+
					 						" and report_name = '"+inputBean.getReportName()+"' and "+
					 						"to_char(report_date,'MM/dd/yyyy hh:mi:ss') = '"+inputBean.getReportDate()+"'";
				factory.updateDelete(whereClause);
			}
		}
	 }
  } //end of method

public void createExcelReport(BigDecimal personnelId, Writer writer, Locale locale) throws NoDataException, BaseException, Exception {
   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
   Collection data = this.getSearchResult(personnelId);

   Iterator iterator = data.iterator();
   PrintWriter pw = new PrintWriter(writer);
   pw.println("<html>");
   //result table
   pw.println("<table border=\"1\">");
   //write column headers
	pw.println("<tr>");
		pw.print("<th>"+library.getString("label.reportdate")+ "</th>");
		pw.print("<th>"+library.getString("label.reportname")+ "</th>");
	pw.println("</tr>");
   //now write data
   while (iterator.hasNext()) {
     pw.println("<tr>");
	  BatchReportBean bean = (BatchReportBean)iterator.next();
	  pw.println("<td>" +DateHandler.formatDate(bean.getReportDate(),"MM/dd/yyyy") + "</td>");
	  pw.println("<td>" +StringHandler.emptyIfNull(bean.getReportName()) + "</td>");
	  pw.println("</tr>");
   }
	pw.println("</table>");
   pw.println("</html>");
   pw.flush();
   pw.close();
 } //end of method

} //end of class
