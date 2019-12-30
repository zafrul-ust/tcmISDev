package com.tcmis.client.report.process;


import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.admin.factory.SearchDropDownBeanFactory;
import com.tcmis.common.admin.beans.KeyValuePairBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.report.factory.ListBeanFactory;
import com.tcmis.client.report.beans.TcmisConstantBean;
import com.tcmis.client.report.factory.TcmisConstantBeanFactory;
import com.tcmis.client.report.beans.FormattedUsageInputBean;
import com.tcmis.client.report.beans.UsageReportTemplateBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class FormattedMonthlyVocProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FormattedMonthlyVocProcess(String client) {
    super(client);
  }

  public void createReport(FormattedUsageInputBean bean,
                           PersonnelBean personnelBean, javax.servlet.http.HttpServletResponse response) throws
      BaseException,
      Exception {
	  Locale locale  = personnelBean.getLocale();
	  //this.setExcel(response, this.getClass().getSimpleName()+".xls");
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  com.tcmis.common.util.ExcelHandler pw = new com.tcmis.common.util.ExcelHandler(library);

        DbManager dbManager = new DbManager(getClient());
        //writer.write("formatted monthly voc");
        pw.write(response.getOutputStream());
        
  }


}