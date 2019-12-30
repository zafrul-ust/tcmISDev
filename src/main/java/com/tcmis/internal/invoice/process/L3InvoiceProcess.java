package com.tcmis.internal.invoice.process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.beans.InvoiceViewL3Bean;
import com.tcmis.internal.invoice.beans.InvoiceViewL3DetailBean;
import com.tcmis.internal.invoice.factory.InvoiceViewL3BeanFactory;

/*****************************************************************
 * Process for L3 invoices.
 ****************************************************************/

public class L3InvoiceProcess
    extends BaseProcess {

  public L3InvoiceProcess(String client) {
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
  public void getWordInvoice(InvoiceInputBean bean, Writer writer) throws
      NoDataException, BaseException, Exception {

      File xmlFile = File.createTempFile("XML", null);
      FileWriter fileWriter = new FileWriter(xmlFile);
      BeanWriter beanWriter = new BeanWriter(fileWriter);
      // Configure betwixt
      // For more details see java docs or later in the main documentation
      beanWriter.getXMLIntrospector().getConfiguration().
          setAttributesForPrimitives(false);
      beanWriter.getBindingConfiguration().setMapIDs(false);
      beanWriter.enablePrettyPrint();
      DbManager dbManager = new DbManager(this.getClient());
     InvoiceViewL3BeanFactory factory = new
          InvoiceViewL3BeanFactory(dbManager);
      Collection collection = factory.select(new BigDecimal("" + bean.getInvoicePeriod()));
      if (collection == null || collection.size() == 0) {
        throw new NoDataException("Query returned no data");
      }
      if(log.isDebugEnabled()) {
        log.debug("COLLECTION:" + collection);
      }
      //add invoice count
      BigDecimal invoiceCount = new BigDecimal("" + collection.size());
      Iterator iterator = collection.iterator();
      int i = 1;
      BigDecimal previousInvoiceNumber = new BigDecimal("0");
      while(iterator.hasNext()) {
        InvoiceViewL3Bean b = (InvoiceViewL3Bean)iterator.next();
        //if(previousInvoiceNumber.intValue() - b.getInvoice().intValue() != 0) {
        //  i = 1;
        //  previousInvoiceNumber = b.getInvoice();
        //}
        b.setInvoiceNumber(new BigDecimal("" + i));
        b.setInvoiceCount(invoiceCount);
        i++;

        Iterator detailIterator = b.getDetail().iterator();
        for(int j = 1;detailIterator.hasNext();j++) {
          InvoiceViewL3DetailBean detailBean = (InvoiceViewL3DetailBean)detailIterator.next();
          detailBean.setLineCount(new BigDecimal("" + j));
          
        }
      }
      beanWriter.write("invoices", collection);
      fileWriter.flush();
      javax.xml.transform.stream.StreamSource source = new javax.xml.transform.
          stream.StreamSource(xmlFile);
      ResourceLibrary library = new ResourceLibrary("invoice");
      File xslFile = new File(library.getString("word.l3invoice.xsl"));
      java.io.PrintWriter out = new PrintWriter(writer);
      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
          stream.StreamResult(out);
      com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
      out.flush();
      out.close();

  }

}
