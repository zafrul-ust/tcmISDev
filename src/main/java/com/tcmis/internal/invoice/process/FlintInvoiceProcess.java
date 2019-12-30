package com.tcmis.internal.invoice.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Oct 21, 2008
 * Time: 3:00:08 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.betwixt.io.BeanWriter;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.beans.InvoiceFormatViewBedfordBean;
import com.tcmis.internal.invoice.beans.InvoiceBean;
import com.tcmis.internal.invoice.beans.InvoiceDetailBean;
import com.tcmis.internal.invoice.beans.InvoiceLineBean;
import com.tcmis.internal.invoice.factory.InvoiceFormatViewBedfordBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceLineBeanFactory;


/***********************************************************
 * Process for creating invoice for Raytheon orders thru Exostar.
 ***********************************************************/

public class FlintInvoiceProcess
    extends BaseProcess {

  public FlintInvoiceProcess(String client) {
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
      SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, "GM");
      criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, "FLINT");
      if( bean.getInvoicePeriod() != null )
    	  criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, bean.getInvoicePeriod().toString());
      else if( bean.getInvoiceNumber() != null )
    	  criteria.addCriterion("invoice", SearchCriterion.EQUALS, bean.getInvoiceNumber().toString());
      Collection<InvoiceBean> c = factory.selectWithDetail(criteria);


      if (c == null || c.size() == 0) {
        throw new NoDataException("Query returned no data");
      }
      if(log.isDebugEnabled()) {
        log.debug("COLLECTION:" + c);
      }
      // adding po to part descripion
      // These value are hard coded every year.
      //	CAR65735 => 
      //    1438-000F   Plant Chemical Products                      (for line 1 of the invoice in tcmIS)
      //	1438-000J   Plant Purge & Detack                           (for line 2 of the invoice in tcmIS)   
      //    GMR82364 => 1438-000G Plant Chemical Products 
      //	GMR83583 => 1438-000H Plant Chemical Products

      InvoiceLineBeanFactory lineFac = new InvoiceLineBeanFactory(dbManager);
      for(InvoiceBean iibean: c) {
    	  BigDecimal ivo = iibean.getInvoice();
		  InvoiceBean tempBean = iibean;
		  tempBean.setInvoiceDetailCollection(new Vector());

		  SearchCriteria sCriteria = new SearchCriteria("invoice", SearchCriterion.EQUALS, ivo.toString());
		  Vector<InvoiceLineBean> ibeans = (Vector<InvoiceLineBean>) lineFac.select(sCriteria);
		  for(InvoiceLineBean ibean:ibeans ) {
			  Vector lineItem = (Vector) tempBean.getInvoiceDetailCollection();
			  InvoiceDetailBean dbean = new InvoiceDetailBean();
			  

//			  2008 PO_NUMBER   2009 POs
//			  CAR64985	CAR71655
//			  GMR83583 GMR88998
//			  GMR83499	
//			  GMR83472
//			  CAR65735  CAR71141
//			  GMR82364  CAR71535
			   

			  if( ibean.getPoNumber().toUpperCase().trim().endsWith("000F") ) {
				  dbean.setPartDescription("1438-000F   Plant Chemical Products");
			  }
			  else if( ibean.getPoNumber().toUpperCase().trim().endsWith("000J") ) { 	
				  dbean.setPartDescription("1438-000J   Plant Purge & Detack");
			  }    	        			  
//			  else if( ibean.getPoNumber().toUpperCase().startsWith("GMR82364") ) {
			  else if( ibean.getPoNumber().toUpperCase().startsWith("CAR71535") ) {
				  dbean.setPartDescription("1438-000G Plant Chemical Products");
			  }
//			  else if( ibean.getPoNumber().toUpperCase().startsWith("GMR83583") ) {
			  else if( ibean.getPoNumber().toUpperCase().startsWith("GMR88998") ) {
				  dbean.setPartDescription("1438-000H Plant Chemical Products");
			  }
			  else continue;
			  dbean.setQuantity(new BigDecimal(1));
			  dbean.setUnitOfSale("EA");
			  dbean.setInvoiceUnitPrice(ibean.getExtendedPrice());
			  dbean.setNetAmount(ibean.getExtendedPrice());
			  lineItem.add(dbean);
		  }
		  if( iibean.getPoNumber().toUpperCase().trim().startsWith("CAR65735") ) 
			  iibean.setPoNumber("CAR65735"); 
      }
      beanWriter.write("invoices", c);
      fileWriter.flush();
      javax.xml.transform.stream.StreamSource source = new javax.xml.transform.
          stream.StreamSource(xmlFile);
      ResourceLibrary library = new ResourceLibrary("invoice");
      File xslFile = new File(library.getString("word.flintinvoice.xsl"));
      java.io.PrintWriter out = new PrintWriter(writer);
      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
          stream.StreamResult(out);
      com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
      out.flush();
      out.close();
  }

  public void getWordInvoiceMaterial(InvoiceInputBean bean, Writer writer) throws
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
      SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, "GM");
      criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, "FLINT");
      criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, bean.getInvoicePeriod().toString());
      Collection c = factory.selectWithLine(criteria);


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
      File xslFile = new File(library.getString("word.flintinvoicematerial.xsl"));
      java.io.PrintWriter out = new PrintWriter(writer);
      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
          stream.StreamResult(out);
      com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
      out.flush();
      out.close();
  }

}