package com.tcmis.internal.invoice.process;


import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.*;
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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.beans.InvoiceFormatViewVolvoNrvBean;
import com.tcmis.internal.invoice.beans.InvoiceBean;
import com.tcmis.internal.invoice.factory.InvoiceFormatViewVolvoNrvBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;

/***********************************************************
 * Process for creating UTC invoice.
 ***********************************************************/

public class VolvoNrvInvoiceProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public VolvoNrvInvoiceProcess(String client) {
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
      InvoiceFormatViewVolvoNrvBeanFactory factory = new
          InvoiceFormatViewVolvoNrvBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, "" + bean.getInvoicePeriod());
      //criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, bean.getDivision());
      SortCriteria sortCriteria = new SortCriteria();
      sortCriteria.addCriterion("invoice");
      Collection c = factory.select(criteria, sortCriteria);
      if (c == null || c.size() == 0) {
        throw new NoDataException("Query returned no data");
      }
      c = this.normalizeData(c);
      if(log.isDebugEnabled()) {
        log.debug("COLLECTION:" + c);
      }
      beanWriter.write("invoices", c);
      fileWriter.flush();
      javax.xml.transform.stream.StreamSource source = new javax.xml.transform.
          stream.StreamSource(xmlFile);
      ResourceLibrary library = new ResourceLibrary("invoice");
      File xslFile = null;
      xslFile = new File(library.getString("word.volvonrvinvoice.xsl"));

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

  private Collection normalizeData(Collection flatCollection) throws
      NoDataException, BaseException, Exception {
    Collection normalizedCollection = null;
    if(flatCollection != null && flatCollection.size() > 0) {
      normalizedCollection = new Vector();
      BigDecimal previousInvoice = null;
      InvoiceFormatViewVolvoNrvBean parentBean = null;
      InvoiceFormatViewVolvoNrvBean childBean = null;
      Iterator iterator = flatCollection.iterator();
      while(iterator.hasNext()) {
        InvoiceFormatViewVolvoNrvBean flatBean = (InvoiceFormatViewVolvoNrvBean)iterator.next();
        if(previousInvoice == null) {
          //first time thru loop
          parentBean = new InvoiceFormatViewVolvoNrvBean();
          childBean = new InvoiceFormatViewVolvoNrvBean();
          this.copyParentData(flatBean, parentBean);
          this.copyChildData(flatBean, childBean);
          parentBean.addLineBean((InvoiceFormatViewVolvoNrvBean)childBean.clone());
          childBean = new InvoiceFormatViewVolvoNrvBean();
        }
        else if(previousInvoice.compareTo(flatBean.getInvoice()) == 0) {
          //same invoice
          this.copyChildData(flatBean, childBean);
          parentBean.addLineBean((InvoiceFormatViewVolvoNrvBean)childBean.clone());
          childBean = new InvoiceFormatViewVolvoNrvBean();
        }
        else {
          //different invoice
          normalizedCollection.add((InvoiceFormatViewVolvoNrvBean)parentBean.clone());
          parentBean = new InvoiceFormatViewVolvoNrvBean();
          childBean = new InvoiceFormatViewVolvoNrvBean();
          this.copyParentData(flatBean, parentBean);
          this.copyChildData(flatBean, childBean);
          parentBean.addLineBean((InvoiceFormatViewVolvoNrvBean)childBean.clone());
          childBean = new InvoiceFormatViewVolvoNrvBean();
        }
        previousInvoice = flatBean.getInvoice();
      }
      normalizedCollection.add((InvoiceFormatViewVolvoNrvBean)parentBean.clone());
    }
//log.debug("normalized:" + normalizedCollection);
    return normalizedCollection;
  }

  private void copyParentData(InvoiceFormatViewVolvoNrvBean fromBean, InvoiceFormatViewVolvoNrvBean toBean) {
    toBean.setInvoice(fromBean.getInvoice());
    toBean.setInvoiceDate(fromBean.getInvoiceDate());
    //toBean.setInvoiceAmount(fromBean.getInvoiceAmount());
    toBean.setPoNumber(fromBean.getPoNumber());
  }

  private void copyChildData(InvoiceFormatViewVolvoNrvBean fromBean, InvoiceFormatViewVolvoNrvBean toBean) {
    toBean.setPoNumber(fromBean.getPoNumber());
    toBean.setQuantity(fromBean.getQuantity());
    toBean.setPartDescription(fromBean.getPartDescription());
    toBean.setUom(fromBean.getUom());
    toBean.setUomPrice(fromBean.getUomPrice());
    toBean.setTotalPrice(fromBean.getTotalPrice());
    toBean.setCatPartNo(fromBean.getCatPartNo());
    toBean.setHaasPo(fromBean.getHaasPo());
  }

}