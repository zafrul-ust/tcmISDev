package com.tcmis.internal.invoice.process;

import java.io.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.invoice.beans.*;
import com.tcmis.internal.invoice.factory.*;

/**
 * Process for creating Arvin Meritor invoice.
 */

public class AjaxProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public AjaxProcess(String client) {
    super(client);
  }

  public String getMaxInvoicePeriod(String company) throws BaseException {
    InvoicePeriodBean bean = null;
    String invoiceGroup = null;
    String companyId = null;
    if(company.equalsIgnoreCase("ARVIN_MERITOR")) {
      companyId = "ARVIN_MERITOR";
      invoiceGroup = "ARM";
    }
    else if(company.equalsIgnoreCase("BAE")) {
      companyId = "BAE";
      invoiceGroup = "BAE";
    }
    else if(company.equalsIgnoreCase("BOEING_DECATUR")) {
      companyId = "BOEING";
      invoiceGroup = "BD";
    }
    else if(company.equalsIgnoreCase("BOEING_OFFSITE")) {
      companyId = "BOEING";
      invoiceGroup = "BDOS";
    }
    else if(company.equalsIgnoreCase("CAL")) {
      companyId = "CAL";
      invoiceGroup = "CAL";
    }
    else if(company.equalsIgnoreCase("DANA")) {
      companyId = "DANA";
      invoiceGroup = "DANA";
    }
    else if(company.equalsIgnoreCase("DOE")) {
      companyId = "DOE";
      invoiceGroup = "SLAC";
    }
    else if(company.equalsIgnoreCase("DRS")) {
      companyId = "DRS";
      invoiceGroup = "DRS";
    }
    else if(company.equalsIgnoreCase("GOLETAEW")) {
      companyId = "RAYTHEON";
      invoiceGroup = "EW";
    }
    else if(company.equalsIgnoreCase("KELLY")) {
      companyId = "LOCKHEED";
      invoiceGroup = "KELLY";
    }
    else if(company.equalsIgnoreCase("L3")) {
      companyId = "L3";
      invoiceGroup = "L-3";
    }
    else if(company.equalsIgnoreCase("SYRACUSE")) {
      companyId = "LOCKHEED";
      invoiceGroup = "SYR";
    }
    else if(company.equalsIgnoreCase("MILLER")) {
      companyId = "MILLER";
      invoiceGroup = "MILLER";
    }
    else if(company.equalsIgnoreCase("FSS")) {
      companyId = "RAYTHEON";
      invoiceGroup = "FSS";
    }
    else if(company.equalsIgnoreCase("QOS")) {
      companyId = "QOS";
      invoiceGroup = "QOS";
    }
    else if(company.equalsIgnoreCase("RAC")) {
      companyId = "RAYTHEON";
      invoiceGroup = "RAC";
    }
    else if(company.equalsIgnoreCase("SAUER_DANFOSS")) {
      companyId = "SAUER_DANFOSS";
      invoiceGroup = "SD";
    }
    else if(company.equalsIgnoreCase("SWA")) {
      companyId = "SWA";
      invoiceGroup = "SWA";
    }
    else if(company.equalsIgnoreCase("UTC_HSD")) {
      companyId = "UTC";
      invoiceGroup = "HSD";
    }
    else if(company.equalsIgnoreCase("UTC_PWA")) {
      companyId = "UTC";
      invoiceGroup = "PWA";
    }
    else if(company.equalsIgnoreCase("UTC_SAC")) {
      companyId = "UTC";
      invoiceGroup = "SAC";
    }
    DbManager dbManager = new DbManager(getClient());
    InvoicePeriodBeanFactory factory = new InvoicePeriodBeanFactory(dbManager);
    Iterator iterator = factory.selectMaxInvoicePeriod(companyId, invoiceGroup).iterator();
    while(iterator.hasNext()) {
      bean = (InvoicePeriodBean)iterator.next();
    }
    //FileWriter fileWriter = new FileWriter(xmlFile);
    StringWriter stringWriter = new StringWriter();
    try {
      BeanWriter beanWriter = new BeanWriter(stringWriter);
      // Configure betwixt
      // For more details see java docs or later in the main documentation
      beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
      beanWriter.getBindingConfiguration().setMapIDs(false);
      beanWriter.enablePrettyPrint();
      beanWriter.write("data", bean);
    }
    catch(Exception e) {
      log.error("Error writing data", e);
    }
    return stringWriter.toString();
    //return bean.getInvoicePeriod().toString();
  }
}