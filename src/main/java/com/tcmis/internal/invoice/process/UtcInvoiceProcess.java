package com.tcmis.internal.invoice.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.*;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.beans.InvoiceFormatViewUtc1Bean;
import com.tcmis.internal.invoice.beans.InvoiceBean;
import com.tcmis.internal.invoice.factory.InvoiceFormatViewUtc1BeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;

/***********************************************************
 * Process for creating UTC invoice.
 ***********************************************************/

public class UtcInvoiceProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public UtcInvoiceProcess(String client) {
    super(client);
  }

  /******************************************************************************
   * Creates a word invoice in xml format using 
   * com.tcmis.common.util.TcmisTransformer.<BR>
   *
   * @param bean  the input form
   * @param writer  the writer from the http response.
   * @throws NoDataException If there is no data for the selected criteria
   * @throws BaseException If an <code>SQLException</code> is thrown
   * @throws Exception If an error not expected by the code is thrown
   ****************************************************************************/
  public void getWordInvoice(InvoiceInputBean bean, Writer writer) throws NoDataException, BaseException {
    try {
      File xmlFile = File.createTempFile("XML", null);
      FileWriter fileWriter = new FileWriter(xmlFile);
      BeanWriter beanWriter = new BeanWriter(fileWriter);
      // Configure betwixt
      // For more details see java docs or later in the main documentation
      //beanWriter.getXMLIntrospector().setAttributesForPrimitives(false);
      beanWriter.getXMLIntrospector().getConfiguration().
          setAttributesForPrimitives(false);
      beanWriter.getBindingConfiguration().setMapIDs(false);
      beanWriter.enablePrettyPrint();
      DbManager dbManager = new DbManager(getClient());
      InvoiceFormatViewUtc1BeanFactory factory = new
          InvoiceFormatViewUtc1BeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, "" + bean.getInvoicePeriod());
      criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, bean.getDivision());
      Collection c = factory.selectInvoice(bean.getInvoicePeriod(), bean.getDivision());
      if (c == null || c.size() == 0) {
        throw new NoDataException("Query returned no data");
      }
      if(log.isDebugEnabled()) {
        log.debug("COLLECTION:" + c);
      }
      beanWriter.write("invoices", c);
      fileWriter.flush();
      javax.xml.transform.stream.StreamSource source = new javax.xml.transform.
          stream.StreamSource(xmlFile);
      ResourceLibrary library = new ResourceLibrary("invoice");
      File xslFile = null;
      if(bean.getDivision().equalsIgnoreCase("HSD")) {
        xslFile = new File(library.getString("word.utchsdinvoice.xsl"));
      }
      else if(bean.getDivision().equalsIgnoreCase("PWA")) {
        xslFile = new File(library.getString("word.utcpwainvoice.xsl"));
      }
      else if(bean.getDivision().equalsIgnoreCase("SAC")) {
        xslFile = new File(library.getString("word.utcsacinvoice.xsl"));
      }
      else if(bean.getDivision().equalsIgnoreCase("SAC-HSI")) {
    	  xslFile = new File(library.getString("word.utchsiinvoice.xsl"));
      }
      else if(bean.getDivision().equalsIgnoreCase("SAC-KEYSTONE")) {
    	  xslFile = new File(library.getString("word.utckeyinvoice.xsl"));
      }
      java.io.PrintWriter out = new PrintWriter(writer);
      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
          stream.StreamResult(out);
      com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
      out.flush();
      out.close();
    }
    catch (Exception e) {
      log.fatal("Exception:" + e.getMessage());
      BaseException be = new BaseException("Error creating word invoice for L3:" + e.getMessage());
      be.setRootCause(e);
      throw be;
    }
  }



}
