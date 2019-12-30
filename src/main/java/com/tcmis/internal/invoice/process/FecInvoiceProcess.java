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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.invoice.beans.InvoiceBean;
import com.tcmis.internal.invoice.beans.InvoiceDetailBean;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;

/***********************************************************
 * Process for creating Fec invoice.
 ***********************************************************/

public class FecInvoiceProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public FecInvoiceProcess(String client) {
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
	public void getAddChargeWordInvoice(InvoiceInputBean bean, Writer writer) throws NoDataException,
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
		/*
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("companyId", SearchCriterion.EQUALS, "FEC");
      criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, bean.getInvoicePeriod().toString());
      criteria.addCriterion("poNumber", SearchCriterion.EQUALS, "xyz");
      criteria.addCriterion("commodity", SearchCriterion.EQUALS, "Add Charge");
		 */
		Collection c = factory.selectFec("FEC", "xyz", "Add Charge", bean.getInvoicePeriod());
		if (c == null || c.size() == 0) {
			throw new NoDataException("Query returned no data");
		}
		if(log.isDebugEnabled()) {
			log.debug("COLLECTION:" + c);
		}
		//now I need to loop thru and put a count on the invoice and sum the additional charges
		BigDecimal previousInvoice = new BigDecimal("0");
		BigDecimal count = new BigDecimal("1");
		BigDecimal invoiceAmount = null;
		Iterator iterator = c.iterator();
		while(iterator.hasNext()) {
			InvoiceBean invoiceBean = (InvoiceBean)iterator.next();
			if(previousInvoice.compareTo(invoiceBean.getInvoice()) != 0) {
				count = new BigDecimal("1");
			}
			else {
				count = count.add(new BigDecimal("1"));
			}
			invoiceBean.setCount(count);
			previousInvoice = invoiceBean.getInvoice();
			//sum the additional charges
			invoiceAmount = new BigDecimal("0");
			Iterator detailIterator = invoiceBean.getInvoiceDetailCollection().iterator();
			while(detailIterator.hasNext()) {
				InvoiceDetailBean detailBean = (InvoiceDetailBean)detailIterator.next();
				invoiceAmount = invoiceAmount.add(detailBean.getAdditionalChargeAmount());
				//fix rounding issue
				detailBean.setAdditionalChargeAmount(detailBean.getAdditionalChargeAmount().setScale(5, BigDecimal.ROUND_HALF_UP));
			}
			invoiceBean.setInvoiceAmount(invoiceAmount.setScale(5, BigDecimal.ROUND_HALF_UP));
		}
		if(log.isDebugEnabled()) {
			log.debug("COLL:" + c);
		}
		beanWriter.write("invoices", c);
		fileWriter.flush();
		javax.xml.transform.stream.StreamSource source = new javax.xml.transform.
		stream.StreamSource(xmlFile);
		ResourceLibrary library = new ResourceLibrary("invoice");
		File xslFile = new File(library.getString("word.fecacinvoice.xsl"));
		java.io.PrintWriter out = new PrintWriter(writer);
		javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
		stream.StreamResult(out);
		com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
		out.flush();
		out.close();

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

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "FEC");
		criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, bean.getInvoicePeriod().toString());
		if(bean.getInvoiceNumber() != null) {
			criteria.addCriterion("invoice", SearchCriterion.EQUALS, bean.getInvoiceNumber().toString());
		}


		Collection c = factory.selectWithDetail(criteria);
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
		File xslFile = new File(library.getString("word.fecinvoice.xsl"));
		java.io.PrintWriter out = new PrintWriter(writer);
		javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
		stream.StreamResult(out);
		com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
		out.flush();
		out.close();

	}
}
