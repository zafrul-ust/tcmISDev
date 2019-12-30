package com.tcmis.internal.invoice.process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
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
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.beans.InvoiceViewDanaBean;
import com.tcmis.internal.invoice.factory.InvoiceViewDanaBeanFactory;

/***********************************************************
 * Process for creating Dana invoice.
 ***********************************************************/

public class DanaInvoiceProcess
extends BaseProcess {
	
	String xslFilenameResource = "word.danainvoice.xsl";
	String tableName = null;

	public DanaInvoiceProcess(String client) {
		super(client);
	}
	
	public DanaInvoiceProcess (String client,String xslFilenameResource,String tableName) {
		super(client);
		this.xslFilenameResource = xslFilenameResource;
		this.tableName = tableName;
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
		InvoiceViewDanaBeanFactory factory = new
		InvoiceViewDanaBeanFactory(
				dbManager, tableName);
		Collection c = null;
		if("DANA-CGPS".equalsIgnoreCase(bean.getClient())) {
			c = factory.selectCgps(bean.getInvoicePeriod());
		}
		else if("DANA-CG".equalsIgnoreCase(bean.getClient())){
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("invoicePeriod",
					SearchCriterion.EQUALS,
					"" + bean.getInvoicePeriod());
			criteria.addCriterion("invoiceGroup",
					SearchCriterion.EQUALS,
					bean.getClient());
			c = factory.select(criteria);
			c = this.normalizeCgData(c);
			if(log.isDebugEnabled()) {
				log.debug("normalizedd:" + c);
			}
		}
		else {
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("invoicePeriod",
					SearchCriterion.EQUALS,
					"" + bean.getInvoicePeriod());
			criteria.addCriterion("invoiceGroup",
					SearchCriterion.EQUALS,
					bean.getClient());
			c = factory.select(criteria);
		}
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
		if("DANA-CGPS".equalsIgnoreCase(bean.getClient())) {
			xslFile = new File(library.getString("word.danacgpsinvoice.xsl"));
		}
		else {
			//xslFile = new File(library.getString("word.danainvoice." + bean.getClient().toLowerCase() + ".xsl"));
			xslFile = new File(library.getString(xslFilenameResource));
		}
		java.io.PrintWriter out = new PrintWriter(writer);
		javax.xml.transform.stream.StreamResult result = new javax.xml.transform.
		stream.StreamResult(out);
		com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
		out.flush();
		out.close();
	}

	private Collection normalizeCgData(Collection flatCollection) throws
	NoDataException, BaseException, Exception {
		Collection normalizedCollection = null;
		if(flatCollection != null && flatCollection.size() > 0) {
			normalizedCollection = new Vector();
			BigDecimal previousInvoice = null;
			InvoiceViewDanaBean parentBean = null;
			InvoiceViewDanaBean childBean = null;
			Iterator iterator = flatCollection.iterator();
			while(iterator.hasNext()) {
				InvoiceViewDanaBean flatBean = (InvoiceViewDanaBean)iterator.next();
				if(previousInvoice == null) {
					//first time thru loop
					parentBean = new InvoiceViewDanaBean();
					childBean = new InvoiceViewDanaBean();
					this.copyParentData(flatBean, parentBean);
					this.copyChildData(flatBean, childBean);
					parentBean.addLineBean((InvoiceViewDanaBean)childBean.clone());
					childBean = new InvoiceViewDanaBean();
				}
				else if(previousInvoice.compareTo(flatBean.getInvoice()) == 0) {
					//same invoice
					this.copyChildData(flatBean, childBean);
					parentBean.addLineBean((InvoiceViewDanaBean)childBean.clone());
					childBean = new InvoiceViewDanaBean();
				}
				else {
					//different invoice
					normalizedCollection.add(parentBean.clone());
					parentBean = new InvoiceViewDanaBean();
					childBean = new InvoiceViewDanaBean();
					this.copyParentData(flatBean, parentBean);
					this.copyChildData(flatBean, childBean);
					parentBean.addLineBean((InvoiceViewDanaBean)childBean.clone());
					childBean = new InvoiceViewDanaBean();
				}
				previousInvoice = flatBean.getInvoice();
			}
			normalizedCollection.add(parentBean.clone());
		}
		log.debug("normalized:" + normalizedCollection);
		return normalizedCollection;
	}

	private void copyParentData(InvoiceViewDanaBean fromBean, InvoiceViewDanaBean toBean) {
		toBean.setInvoice(fromBean.getInvoice());
		toBean.setInvoiceDate(fromBean.getInvoiceDate());
		toBean.setInvoiceAmount(fromBean.getInvoiceAmount());
		toBean.setPoNumber(fromBean.getPoNumber());
	}

	private void copyChildData(InvoiceViewDanaBean fromBean, InvoiceViewDanaBean toBean) {
		toBean.setLineAmount(fromBean.getLineAmount());
		toBean.setLineDescription(fromBean.getLineDescription());
	}
}
