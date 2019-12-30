package com.tcmis.internal.invoice.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 27, 2008
 * Time: 3:24:27 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.betwixt.io.BeanWriter;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.invoice.beans.InvoiceBean;
import com.tcmis.internal.invoice.beans.InvoiceDetailBean;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;

/***********************************************************
 * Process for creating Continental Airlines invoice.
 ***********************************************************/

public class TimkenInvoiceProcess
    extends BaseProcess {

  public TimkenInvoiceProcess(String client) {
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
      
      
      DbManager dbManager = new DbManager(getClient());
      InvoiceBeanFactory factory = new InvoiceBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("companyId", SearchCriterion.EQUALS,"TIMKEN");
      criteria.addCriterion("accountSysId", SearchCriterion.EQUALS,"Timken");
      
      if( bean.getInvoicePeriod() != null )
    	  criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, bean.getInvoicePeriod().toString());
      else if( bean.getInvoiceNumber() != null )
    	  criteria.addCriterion("invoice", SearchCriterion.EQUALS, bean.getInvoiceNumber().toString());
      Vector<InvoiceBean> c = factory.selectWithDetailCD(criteria);

      if (c == null || c.size() == 0) {
        throw new NoDataException("Query returned no data");
      }
      if(log.isDebugEnabled()) {
        log.debug("COLLECTION:" + c);
      }

      Vector<InvoiceBean> d = new Vector();
      for(int i=0; i< c.size(); i+=2 ) {
    	  InvoiceBean charge = c.get(i);
    	  InvoiceBean credit = c.get(i+1);
    	  System.out.println("oriNet:"+charge.getInvoiceAmount());
    	  d.add(charge);
    	  if( credit != null) {
    		  Vector<InvoiceDetailBean> vv = (Vector)(credit.getInvoiceDetailCollection());
    		  if( vv != null && vv.size() > 0 ) {
    			  // char@ge/debi@t are + numbers and cred@its are - numbers
    			  charge.setInvoiceSuffix("-D");
    			  BigDecimal creditAmount = new BigDecimal("0");
    			  for(InvoiceDetailBean dbean:vv) {
    				  creditAmount = creditAmount.add(dbean.getNetAmount());
    			  }
    			  credit.setInvoiceSuffix("-C");
    			  credit.setInvoiceAmount(creditAmount);
    			  charge.setInvoiceAmount(charge.getInvoiceAmount().subtract(creditAmount));
    			  d.add(credit);
    			  /*
    			  // just to use credit to calculate original charge.
    			  System.out.println("oriCredit:"+creditAmount);
    			  creditAmount = new BigDecimal("0");
    			  for(InvoiceDetailBean dbean:(Vector<InvoiceDetailBean>)charge.getInvoiceDetailCollection()) {
    				  creditAmount = creditAmount.add(dbean.getNetAmount());
    			  }
    			  System.out.println("oriCharge:"+creditAmount);
				  */
    		  }
    	  }
      }
          
      beanWriter.write("invoices", d);
      fileWriter.flush();
      javax.xml.transform.stream.StreamSource source = new javax.xml.transform.
          stream.StreamSource(xmlFile);
      ResourceLibrary library = new ResourceLibrary("invoice");
      File xslFile = new File(library.getString("word.timkeninvoice.xsl"));
      java.io.PrintWriter out = new PrintWriter(writer);
      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
          stream.StreamResult(out);
      com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
      out.flush();
      out.close();

  }

}