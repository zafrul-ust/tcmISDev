package com.tcmis.internal.invoice.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 2, 2008
 * Time: 10:56:42 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;

/***********************************************************
 * Process for creating invoice.
 ***********************************************************/

public class CaterpillarInvoiceProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CaterpillarInvoiceProcess(String client) {
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
  public void getWordInvoice(InvoiceInputBean bean, Writer writer) throws NoDataException,
      BaseException, Exception {

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
      InvoiceBeanFactory factory = new InvoiceBeanFactory(dbManager);

      SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, "CATERPILLAR");
      criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, bean.getInvoicePeriod().toString());
      Collection c = factory.selectCaterpillar(criteria);

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
      File xslFile = new File(library.getString("word.caterpillarinvoice.xsl"));
      java.io.PrintWriter out = new PrintWriter(writer);
      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
          stream.StreamResult(out);
      com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
      out.flush();
      out.close();

  }
}
