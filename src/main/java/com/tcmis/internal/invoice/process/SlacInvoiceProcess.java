package com.tcmis.internal.invoice.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.invoice.beans.InvoiceFormatViewSlacBean;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceFormatViewSlacBeanFactory;

/***********************************************************
 * Process for creating Slac invoice.
 ***********************************************************/

public class SlacInvoiceProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static final String header = "Facility,Work Area,Charge Type,Commodity,Invoice Type,Invoice #,Invoice Date,GL Account,Charge Number,Requester,Approver,MR Number,MR Line,Part Description,Packaging,Receipt ID,Date Delivered,Quantity,Catalog Price,Invoice Price,Add'l Charges,Net Amount,Price Flag,Contract PO";
	private static final String[] charArray = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
// Just to rebuild to make sure
	
	public SlacInvoiceProcess(String client) {
		super(client);
	}

	/******************************************************************************
	 * Creates a word invoice in xml format using com.tcmis.common.util.TcmisTransformer.<BR>
	 * 
	 * @param bean
	 *                the input form
	 * @param writer
	 *                the writer from the http response.
	 * @throws NoDataException
	 *                 If there is no data for the selected criteria
	 * @throws BaseException
	 *                 If an <code>SQLException</code> is thrown
	 * @throws Exception
	 *                 If an error not expected by the code is thrown
	 ****************************************************************************/
	public void getInvoice(InvoiceInputBean bean, Writer writer) throws NoDataException, BaseException {
		try {
			String fileName = null;
			FileWriter fileWriter = null;
			ResourceLibrary library = new ResourceLibrary("invoice");
			DbManager dbManager = new DbManager(getClient());
			InvoiceFormatViewSlacBeanFactory factory = new InvoiceFormatViewSlacBeanFactory(dbManager);
			Collection c = factory.select(new SearchCriteria("invoicePeriod", SearchCriterion.EQUALS, bean.getInvoicePeriod().toString()), null);
			Iterator iterator = c.iterator();
			BigDecimal invoice = null;
			int i = 0;
			while (iterator.hasNext()) {
				InvoiceFormatViewSlacBean b = (InvoiceFormatViewSlacBean) iterator.next();
				String contractPo = "54076";
				if ("Haas TCM Monthly Service Fee".equalsIgnoreCase(b.getPartDescription())) {
					contractPo = "54012";
				}
				//check if it's a new invoice
				if (invoice == null || invoice.longValue() != b.getInvoice().longValue()) {
					//if it's not the first time thru loop we'll be starting a new file
					if (invoice != null) {
						fileWriter.flush();
						fileWriter.close();
					}
					//new invoice
					invoice = b.getInvoice();
					fileName = "HAASINV" + DateHandler.formatDate(b.getInvoiceDate(), "MMddyy") + charArray[i] + ".csv";
					if (log.isDebugEnabled()) {
						log.debug("filename:" + fileName);
					}
					fileWriter = new FileWriter(new File(library.getString("slac.invoice.dir") + fileName));
					fileWriter.write(header + "\n");
					i++;
				}
				fileWriter.write(b.getFacilityId().replaceAll(",", " ") + ",");
				fileWriter.write(b.getApplication().replaceAll(",", " ") + ",");
				fileWriter.write(b.getChargeType().replaceAll(",", " ") + ",");
				fileWriter.write(b.getCommodity().replaceAll(",", " ") + ",");
				fileWriter.write(b.getItemType().replaceAll(",", " ") + ",");
				fileWriter.write(b.getInvoice() + ",");
				fileWriter.write(DateHandler.formatDate(b.getInvoiceDate(), "dd-MMM-yyyy") + ",");
				fileWriter.write(b.getAccountNumber().replaceAll(",", " ") + ",");
				fileWriter.write("C" + b.getAccountNumber2().replaceAll(",", " ") + ",");
				fileWriter.write(b.getRequestorName().replaceAll(",", " ") + ",");
				fileWriter.write(b.getApprover().replaceAll(",", " ") + ",");
				fileWriter.write(b.getPrNumber() + ",");
				fileWriter.write(b.getLineItem().replaceAll(",", " ") + ",");
				fileWriter.write(b.getPartDescription().replaceAll(",", " ") + ",");
				fileWriter.write(b.getPackaging().replaceAll(",", " ") + ",");
				fileWriter.write(b.getReceiptId() + ",");
				fileWriter.write(DateHandler.formatDate(b.getDateDelivered(), "dd-MMM-yyyy") + ",");
				fileWriter.write(b.getQuantity() + ",");
				fileWriter.write(b.getCatalogPrice() + ",");
				fileWriter.write(b.getInvoiceUnitPrice() + ",");
				fileWriter.write(b.getTotalAddCharge() + ",");
				fileWriter.write(b.getNetAmount() + ",");
				fileWriter.write(b.getPriceFlag().replaceAll(",", " ") + ",");
				fileWriter.write(contractPo + "\n");
			}
			fileWriter.flush();
			fileWriter.close();
			//      javax.xml.transform.stream.StreamSource source = new javax.xml.transform.stream.StreamSource(xmlFile);
			//      ResourceLibrary library = new ResourceLibrary("invoice");
			//      File xslFile = new File(library.getString("word.l3invoice.xsl"));
			java.io.PrintWriter out = new PrintWriter(writer);
			//      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(out);
			//      com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
			out.print("Invoices were successfully created. Click the back button to create more invoices.");
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

	/******************************************************************************
	 * DOCUMENT ME!
	 * 
	 * @throws NoDataException
	 *                 If there is no data for the selected criteria
	 * @throws BaseException
	 *                 If an <code>SQLException</code> is thrown
	 * @throws Exception
	 *                 If an error not expected by the code is thrown
	 ****************************************************************************/
	public void createInvoices() throws NoDataException, BaseException {
		try {
			//check if there are any invoices to create
			BigDecimal invoicePeriod = this.getNewInvoicePeriod();
			//      BigDecimal invoicePeriod = new BigDecimal("303");
			if (invoicePeriod != null) {
				String fileName = null;
				String filePath = null;
				FileWriter fileWriter = null;
				ResourceLibrary library = new ResourceLibrary("invoice");
				String backupDir = library.getString("slac.invoice.backupdir");
				DbManager dbManager = new DbManager(this.getClient());
				InvoiceFormatViewSlacBeanFactory factory = new InvoiceFormatViewSlacBeanFactory(dbManager);
				SearchCriteria criteria = new SearchCriteria("invoicePeriod", SearchCriterion.EQUALS, invoicePeriod.toString());
				criteria.addCriterion("netAmount", SearchCriterion.NOT_EQUAL, "      $0.00");
				Collection c = factory.select(criteria, null);
				Iterator iterator = c.iterator();
				BigDecimal invoice = null;
				int i = 0;
				while (iterator.hasNext()) {
					InvoiceFormatViewSlacBean b = (InvoiceFormatViewSlacBean) iterator.next();
					String contractPo = "54076";
					if ("Haas TCM Monthly Service Fee".equalsIgnoreCase(b.getPartDescription())) {
						contractPo = "54012";
					}
					//check if it's a new invoice
					if (invoice == null || invoice.longValue() != b.getInvoice().longValue()) {
						//if it's not the first time thru loop we'll be starting a new file
						if (invoice != null) {
							fileWriter.flush();
							fileWriter.close();
							//this.setFilePermission(filePath);
							//make backup
							FileHandler.copy(new File(filePath), new File(backupDir + fileName));
							this.sendNotification(invoice, filePath);
						}
						//new invoice
						invoice = b.getInvoice();
						fileName = "HAASINV" + DateHandler.formatDate(b.getInvoiceDate(), "MMddyy") + charArray[i] + ".html";
						filePath = library.getString("slac.invoice.dir") + fileName;
						if (log.isDebugEnabled()) {
							log.debug("filename:" + fileName);
						}
						fileWriter = new FileWriter(new File(filePath));
						this.writeHtmlHeader(invoicePeriod, fileWriter);
						i++;
					}
					fileWriter.write(" <tr>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getFacilityId()) + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getApplication()) + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getChargeType()) + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getCommodity()) + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getItemType()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getInvoice()) + "</td>\n");
					fileWriter.write("  <td>" + DateHandler.formatDate(b.getInvoiceDate(), "dd-MMM-yyyy") + "</td>\n");
					fileWriter.write("  <td class=xlt>" + StringHandler.emptyIfNull(b.getAccountNumber()) + "</td>\n");
					fileWriter.write("  <td class=xlt>" + "C" + b.getAccountNumber2() + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getRequestorName()) + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getApprover()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getPrNumber()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getLineItem()) + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getPartDescription()) + "</td>\n");
					fileWriter.write("  <td>" + StringHandler.emptyIfNull(b.getPackaging()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getReceiptId()) + "</td>\n");
					fileWriter.write("  <td>" + DateHandler.formatDate(b.getDateDelivered(), "dd-MMM-yyyy") + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getQuantity()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getCatalogPrice()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getInvoiceUnitPrice()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getTotalAddCharge()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getNetAmount()) + "</td>\n");
					fileWriter.write("  <td align=\"center\">" + StringHandler.emptyIfNull(b.getPriceFlag()) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(contractPo) + "</td>\n");
					fileWriter.write("  <td align=\"right\">" + StringHandler.emptyIfNull(b.getPriceDifference()) + "</td>\n");
					fileWriter.write(" </tr>\n");
				}
				fileWriter.flush();
				fileWriter.close();
				//this.setFilePermission(filePath);
				FileHandler.copy(new File(filePath), new File(backupDir + fileName));
				this.sendNotification(invoice, filePath);
				this.incrementInvoicePeriod(invoicePeriod);
			}
		}
		catch (Exception e) {
			log.fatal("Error creating invoices for SLAC:" + e.getMessage());
			BaseException be = new BaseException("Error creating invoices for SLAC:" + e.getMessage());
			be.setRootCause(e);
			MailProcess.sendEmail("deverror@haastcm.com", "", "slacinvoice@foo.com", "Error creating invoices", "Error creating slac invoices, check the logs." + e.getMessage());
			throw be;
		}

	}

	//I'm just copying the existing format from Steve's invoices so I can't do anything about the format...
	private void writeHtmlHeader(BigDecimal invoicePeriod, Writer writer) throws BaseException, Exception {
		writer.write("<html><head><title>SLAC Invoice Period " + invoicePeriod.toString() + "</title></head>\n");
		writer.write("<style id=\"SLAC\">\n");
		writer.write("<!--table\n");
		writer.write(".xlt\n");
		writer.write("        {mso-number-format:\"\\@\";}\n");
		writer.write("-->\n");
		writer.write("</style>\n");
		writer.write("</head>\n");
		writer.write("<body bgcolor=\"#FFFFFF\">\n");
		writer.write("<table border=\"1\">\n");
		writer.write("  <tr>\n");
		writer.write("    <th>Facility</th>\n");
		writer.write("    <th>Work Area</th>\n");
		writer.write("    <th>Charge Type</th>\n");
		writer.write("    <th>Commodity</th>\n");
		writer.write("    <th>Invoice Type</th>\n");
		writer.write("    <th>Invoice #</th>\n");
		writer.write("    <th>Invoice Date</th>\n");
		writer.write("    <th>GL Account</th>\n");
		writer.write("    <th>Charge Number</th>\n");
		writer.write("    <th>Requester</th>\n");
		writer.write("    <th>Approver</th>\n");
		writer.write("    <th>MR Number</th>\n");
		writer.write("    <th>MR Line</th>\n");
		writer.write("    <th>Part Description</th>\n");
		writer.write("    <th>Packaging</th>\n");
		writer.write("    <th>Receipt ID</th>\n");
		writer.write("    <th>Date Delivered</th>\n");
		writer.write("    <th>Quantity</th>\n");
		writer.write("    <th>Catalog Price</th>\n");
		writer.write("    <th>Invoice Price</th>\n");
		writer.write("    <th>Add'l Charges</th>\n");
		writer.write("    <th>Net Amount</th>\n");
		writer.write("    <th>Price Flag</th>\n");
		writer.write("    <th>Contract PO</th>\n");
		writer.write("    <th>Price Difference</th>\n");
		writer.write("  </tr>\n");
	}

	private BigDecimal getLastInvoicePeriod() throws NoDataException, BaseException, Exception {
		BigDecimal b = null;
		ResourceLibrary library = new ResourceLibrary("invoice");
		BufferedReader in = new BufferedReader(new FileReader(library.getString("invoiceperiod.submitted.file")));
		String line = null;
		do {
			line = in.readLine();
			if (!StringHandler.isBlankString(line)) {
				b = new BigDecimal(line);
			}
		} while (line != null);
		return b;
	}

	private BigDecimal getNewInvoicePeriod() throws NoDataException, BaseException, Exception {
		BigDecimal b = null;
		DbManager dbManager = new DbManager(this.getClient());
		InvoiceFormatViewSlacBeanFactory factory = new InvoiceFormatViewSlacBeanFactory(dbManager);
		Collection c = factory.selectInvoicePeriod(new SearchCriteria("invoicePeriod", SearchCriterion.GREATER_THAN, getLastInvoicePeriod().toString()));
		if (c.size() < 1) {
			//no new invoices
			//System.out.println("No new invoices");
			if (log.isDebugEnabled()) {
				log.debug("No new invoices");
			}
		}
		else if (c.size() == 1) {
			//System.out.println("Found a new invoice period");
			if (log.isDebugEnabled()) {
				log.debug("Found a new invoice period");
			}
			Iterator it = c.iterator();
			while (it.hasNext()) {
				InvoiceFormatViewSlacBean bean = (InvoiceFormatViewSlacBean) it.next();
				b = bean.getInvoicePeriod();
			}
		}
		else {
			//System.out.println("More than 1 invoice period has not been submitted");
			log.fatal("More than 1 invoice period has not been submitted");
			//somehow there is more than one invoice period that has not been submitted
			//throw error and manually find out what's going on.
			throw new BaseException("More than 1 invoice period has not been submitted");
		}
		return b;
	}

	private void incrementInvoicePeriod(BigDecimal invoicePeriod) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("invoice");
		FileWriter fw = new FileWriter(library.getString("invoiceperiod.submitted.file"), true);
		fw.write(invoicePeriod.toString() + "\n");
		fw.flush();
		fw.close();
	}

	private void setFilePermission(String filePath) throws BaseException, Exception {
		Process p = Runtime.getRuntime().exec("chmod 775 " + filePath);
		p.waitFor();
		p = Runtime.getRuntime().exec("chown slac " + filePath);
		p.waitFor();
		p = Runtime.getRuntime().exec("chgrp users " + filePath);
		p.waitFor();
	}

	/******************************************************************************
	 * Creates a word invoice in xml format using com.tcmis.common.util.TcmisTransformer.<BR>
	 * 
	 * @param bean
	 *                the input form
	 * @param writer
	 *                the writer from the http response.
	 * @throws NoDataException
	 *                 If there is no data for the selected criteria
	 * @throws BaseException
	 *                 If an <code>SQLException</code> is thrown
	 * @throws Exception
	 *                 If an error not expected by the code is thrown
	 ****************************************************************************/
	public void getServiceFeeWordInvoice(InvoiceInputBean bean, Writer writer) throws NoDataException, BaseException, Exception {
		File xmlFile = File.createTempFile("XML", null);
		FileWriter fileWriter = new FileWriter(xmlFile);
		BeanWriter beanWriter = new BeanWriter(fileWriter);
		// Configure betwixt
		// For more details see java docs or later in the main documentation
		//beanWriter.getXMLIntrospector().setAttributesForPrimitives(false);
		beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
		beanWriter.getBindingConfiguration().setMapIDs(false);
		beanWriter.enablePrettyPrint();
		DbManager dbManager = new DbManager(getClient());
		InvoiceBeanFactory factory = new InvoiceBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "DOE");
		criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, "SLAC");
		criteria.addCriterion("commodity", SearchCriterion.EQUALS, "Service");
		criteria.addCriterion("invoicePeriod", SearchCriterion.EQUALS, "" + bean.getInvoicePeriod());
		Collection c = factory.select(criteria);
		if (c == null || c.size() == 0) {
			throw new NoDataException("Query returned no data");
		}
		if (log.isDebugEnabled()) {
			log.debug("COLLECTION:" + c);
		}
		beanWriter.write("invoices", c);
		fileWriter.flush();
		javax.xml.transform.stream.StreamSource source = new javax.xml.transform.stream.StreamSource(xmlFile);
		ResourceLibrary library = new ResourceLibrary("invoice");
		File xslFile = new File(library.getString("word.slacsfinvoice.xsl"));
		java.io.PrintWriter out = new PrintWriter(writer);
		javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(out);
		com.tcmis.common.util.TcmisTransformer.transform(source, xslFile, result);
		out.flush();
		out.close();

	}

	private void sendNotification(BigDecimal invoice, String filePath) throws BaseException, Exception {
		/*
		MailProcess.sendEmail("deverror@haastcm.com",
		                "",
		                "slacinvoice@haastcm.com",
		                "Haas TCM invoice " + invoice + " is ready.",
		                "Please do not reply to this email.");
		 */
		try {
			MailProcess.sendEmail("deverror@haastcm.com", "", "slacinvoice@haastcm.com", "Haas TCM invoice " + invoice + " is ready.", "Please do not reply to this email.", "invoice.html", filePath);
		}
		catch (Exception e) {
			log.error("Error sending notification email for invoice:" + invoice + ", path:" + filePath + "." + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Vector<InvoiceFormatViewSlacBean> getInvoice(String invoice_period) throws NoDataException, BaseException {
		Vector<InvoiceFormatViewSlacBean> c = new Vector<InvoiceFormatViewSlacBean>();
		try {
			//String fileName = null;
			//FileWriter fileWriter = null;
			//ResourceLibrary library = new ResourceLibrary("invoice");
			DbManager dbManager = new DbManager(getClient());
			InvoiceFormatViewSlacBeanFactory factory = new InvoiceFormatViewSlacBeanFactory(dbManager);
			c = (Vector<InvoiceFormatViewSlacBean>) factory.select(new SearchCriteria("invoicePeriod", SearchCriterion.EQUALS, invoice_period), null);
		}
		catch (Exception e) {
			log.fatal("Exception:" + e.getMessage(), e);
			BaseException be = new BaseException("Error retrieving data for XML invoice for SLAC:" + e.getMessage());
			be.setRootCause(e);
			//	        throw be;
		}
		return c;
	}

	public Map<String, String> getInvoiceXml(String invoice_period) throws NoDataException, BaseException {
		HashMap<String, String> invoiceXML = new HashMap<String, String>();
		try {
			Vector<InvoiceFormatViewSlacBean> invoices = getInvoice(invoice_period);

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sft = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat delivery_date_format = new SimpleDateFormat("MM/dd/yyyy");
			Date now = new Date();
			String timeStamp = sf.format(now) + "T" + sft.format(now);

			Document document = null;

			String old_invoice = "";
			int line_count = 0;
			Element summary = null;
			String fileName = null;

			for (InvoiceFormatViewSlacBean bean : invoices) {
				String invoiceId = "" + bean.getInvoice();
				if (!invoiceId.equals(old_invoice)) {
					// If not the first invoice, close out the older invoice
					if (document != null) {
						invoiceXML.put(fileName, documentToString(document));
						//return invoiceXML;
					}

					fileName = "HAASINV" + DateHandler.formatDate(bean.getInvoiceDate(), "MMddyy") + "-" +invoiceId;

					// Start a new document
					document = documentBuilder.newDocument();
					Element haas = document.createElement("HAASTCM");
					haas.setAttribute("timestamp", timeStamp);
					haas.setAttribute("xml:lang", "en-US");
					document.appendChild(haas);

					// Start the new Invoice
					Element inv = document.createElement("Invoice");
					haas.appendChild(inv);
					//invoice has a summary element.
					summary = document.createElement("InvoiceSummary");
					summary.setAttribute("invoiceDate", timeStamp);
					summary.setAttribute("invoiceID", invoiceId);
					inv.appendChild(summary);
					line_count = 0;
				}

				//summary has detail Element.

				Element detail = document.createElement("Invoicedetail");
				summary.appendChild(detail);
				line_count++;

				Element e = null;
				//      <Invoiceline>1</Invoiceline>
				e = document.createElement("Invoiceline");
				e.appendChild(document.createTextNode("" + line_count));
				detail.appendChild(e);
				//      <WorkArea>XL0100</WorkArea>
				e = document.createElement("WorkArea");
				e.appendChild(document.createTextNode(bean.getApplication() != null ? StringHandler.xmlEncode(bean.getApplication()) : ""));
				detail.appendChild(e);
				//      <ChargeType>d</ChargeType>
				e = document.createElement("ChargeType");
				e.appendChild(document.createTextNode(bean.getChargeType() != null ? StringHandler.xmlEncode(bean.getChargeType()) : ""));
				detail.appendChild(e);
				//      <CommodityType>Gas</CommodityType>
				e = document.createElement("CommodityType");
				e.appendChild(document.createTextNode(bean.getCommodity() != null ? StringHandler.xmlEncode(bean.getCommodity()) : ""));
				detail.appendChild(e);
				//      <InvoiceType>MV</InvoiceType>
				e = document.createElement("InvoiceType");
				e.appendChild(document.createTextNode(bean.getItemType() != null ? StringHandler.xmlEncode(bean.getItemType()) : ""));
				detail.appendChild(e);
				//      <Account>53105</Account>
				e = document.createElement("Account");
				e.appendChild(document.createTextNode(bean.getAccountNumber() != null ? StringHandler.xmlEncode(bean.getAccountNumber()) : ""));
				detail.appendChild(e);
				//      <ChargeNo>9474332</ChargeNo>
				e = document.createElement("ChargeNo");
				e.appendChild(document.createTextNode(bean.getAccountNumber2() != null ? StringHandler.xmlEncode(bean.getAccountNumber2()) : ""));
				detail.appendChild(e);
				//      <Requestor>Todd Slater</Requestor>
				e = document.createElement("Requestor");
				e.appendChild(document.createTextNode(bean.getRequestorName() != null ? StringHandler.xmlEncode(bean.getRequestorName()) : ""));
				detail.appendChild(e);
				//      <Approver>Natalie Cramer</Approver>
				e = document.createElement("Approver");
				e.appendChild(document.createTextNode(bean.getApprover() != null ? StringHandler.xmlEncode(bean.getApprover()) : ""));
				detail.appendChild(e);
				//      <MRNumber>996401</MRNumber>
				e = document.createElement("MRNumber");
				e.appendChild(document.createTextNode("" + bean.getPrNumber() != null ? StringHandler.xmlEncode("" + bean.getPrNumber()) : ""));
				detail.appendChild(e);
				//      <MRLine>1</MRLine>
				e = document.createElement("MRLine");
				e.appendChild(document.createTextNode(bean.getLineItem() != null ? StringHandler.xmlEncode(bean.getLineItem()) : ""));
				detail.appendChild(e);
				//      <Descr>100 Liter Liquid Nitrogen</Descr>
				e = document.createElement("Descr");
				e.appendChild(document.createTextNode(bean.getPartDescription() != null ? StringHandler.xmlEncode(bean.getPartDescription()) : ""));
				detail.appendChild(e);
				//      <Packaging>Cylinder</Packaging>
				e = document.createElement("Packaging");
				e.appendChild(document.createTextNode(bean.getPackaging() != null ? StringHandler.xmlEncode(bean.getPackaging()) : ""));
				detail.appendChild(e);
				//      <ReceiptID>2953816</ReceiptID>
				e = document.createElement("ReceiptID");
				e.appendChild(document.createTextNode("" + bean.getReceiptId() != null ? StringHandler.xmlEncode("" + bean.getReceiptId()) : ""));
				detail.appendChild(e);
				//      <DeliveryDate>01/01/2009</DeliveryDate>
				e = document.createElement("DeliveryDate");
				e.appendChild(document.createTextNode(bean.getDateDelivered() != null ? StringHandler.xmlEncode(delivery_date_format.format(bean.getDateDelivered())) : ""));
				detail.appendChild(e);
				//      <Quantity>1</Quantity>
				e = document.createElement("Quantity");
				e.appendChild(document.createTextNode("" + bean.getQuantity()));
				detail.appendChild(e);
				// add attribute here.
				//      <CatalogPrice currency="USD">10.20</CatalogPrice>

				e = document.createElement("CatalogPrice");
				e.setAttribute("currency", "USD");
				e.appendChild(document.createTextNode(bean.getCatalogPrice() != null ? StringHandler.xmlEncode(bean.getCatalogPrice()) : ""));
				detail.appendChild(e);
				//      <InvoicePrice currency="USD">10.20</InvoicePrice>
				e = document.createElement("InvoicePrice");
				e.setAttribute("currency", "USD");
				e.appendChild(document.createTextNode(bean.getInvoiceUnitPrice() != null ? StringHandler.xmlEncode(bean.getInvoiceUnitPrice()) : ""));
				detail.appendChild(e);
				//      <AdditionalCharges>5</AdditionalCharges>
				e = document.createElement("AdditionalCharges");
				e.appendChild(document.createTextNode(bean.getTotalAddCharge() != null ? StringHandler.xmlEncode(bean.getTotalAddCharge()) : ""));
				detail.appendChild(e);
				//      <NetAmount>15.20</NetAmount>
				e = document.createElement("NetAmount");
				e.appendChild(document.createTextNode(bean.getNetAmount() != null ? StringHandler.xmlEncode(bean.getNetAmount()) : ""));
				detail.appendChild(e);
				//      <PriceFlag>N</PriceFlag>
				e = document.createElement("PriceFlag");
				e.appendChild(document.createTextNode(bean.getPriceFlag() != null ? StringHandler.xmlEncode(bean.getPriceFlag()) : ""));
				detail.appendChild(e);
				//      <ContractPO>0000054076</ContractPO>
				String contractPo = "54076";
				if ("Haas TCM Monthly Service Fee".equalsIgnoreCase(bean.getPartDescription())) {
					contractPo = "54012";
				}
				e = document.createElement("ContractPO");
				e.appendChild(document.createTextNode(contractPo != null ? StringHandler.xmlEncode(contractPo) : ""));
				detail.appendChild(e);
				//      <PriceDifference>1.14</PriceDifference>
				e = document.createElement("PriceDifference");
				e.appendChild(document.createTextNode(bean.getPriceDifference() != null ? bean.getPriceDifference().setScale(5, BigDecimal.ROUND_HALF_UP).toPlainString() : "0.0"));
				detail.appendChild(e);

				old_invoice = invoiceId;
			}

			if (document != null) {
				invoiceXML.put(fileName, documentToString(document));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.fatal("Exception:" + e.getMessage(), e);
			BaseException be = new BaseException("Error creating XML invoice for SLAC:" + e.getMessage());
			be.setRootCause(e);
			throw be;
		}
		return invoiceXML;
	}

	private String documentToString (Document document) throws TransformerException {
		StringWriter sw = new StringWriter();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(sw);
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		transformer.transform(source, result);
		return sw.toString();
	}

	public void postInvoices() throws NoDataException, BaseException {
		StringBuilder problems = new StringBuilder();
		try {
			//check if there are any invoices to create
			BigDecimal invoicePeriod = getNewInvoicePeriod();
			//BigDecimal invoicePeriod = new BigDecimal("303");
			if (invoicePeriod != null) {
				ResourceLibrary library = new ResourceLibrary("invoice");
				String backupDir = library.getString("slac.invoice.dir");
				String slacURL = library.getString("slac.post.url");

				// Get the invoices as XML
				Map<String, String> invoices = getInvoiceXml(invoicePeriod.toString());
				for (Map.Entry<String, String> invoice : invoices.entrySet()) {
					// Save each invoice  for backup
					FileHandler.saveFile(backupDir + File.separator + invoice.getKey() + ".xml", invoice.getValue());
					// Post each invoice to the provided URL
					String slacResponse = NetHandler.getHttpPostResponse(slacURL, null, null, invoice.getValue());
					// Save any received response
					FileHandler.saveFile(backupDir + File.separator + invoice.getKey() +  "_response.xml", slacResponse);

					if (slacResponse.indexOf("success") < 0) {
						String status = "";
						String type = "";
						String msg ="";
						Pattern typePattern = Pattern.compile("\\<IBResponse\\s+\\Qtype\\E\\=(.*?)\\>");
						Matcher typeMatch = typePattern.matcher(slacResponse);
						while (typeMatch.find()) {
							type += typeMatch.group(1);
						}

						Pattern statusPattern = Pattern.compile("\\<StatusCode\\>(.*?)\\<\\/StatusCode\\>");
						Matcher statusMatch = statusPattern.matcher(slacResponse);
						while (statusMatch.find()) {
							status += statusMatch.group(1);
						}

						Pattern msgPattern = Pattern.compile("\\<DefaultMessage\\>(.*?)\\<\\/DefaultMessage\\>");
						Matcher msgMatch = msgPattern.matcher(slacResponse);
						while (msgMatch.find()) {
							msg += msgMatch.group(1);
						}

						problems.append("Upload of ").append(invoice.getKey()).append(" was ").append(type).append(" with a status code of: ").append(status).append("\n");
						problems.append("Message was: ").append(msg).append("\n\n");
					}
				}
				incrementInvoicePeriod(invoicePeriod);
			}
		}
		catch (Exception e) {
			log.fatal("Error creating XML invoices for SLAC:" + e.getMessage(), e);
			BaseException be = new BaseException("Error creating invoices for SLAC:" + e.getMessage());
			be.setRootCause(e);
			MailProcess.sendEmail("deverror@haastcm.com", "", "deverror@tcmis.com", "Error creating SLAC  invoices", "Error creating slac XML invoices, check the logs." + e.getMessage());
			throw be;
		}
		finally {
			if (problems.length() > 0) {
				MailProcess.sendEmail("deverror@haastcm.com", "", "deverror@tcmis.com", "Error uploading SLAC  invoices", "Error uploading SLAC XML invoices, check the logs.\n" + problems.toString());
			}
		}

	}
	
	public Map<String, String> getInvoiceXmlWInvoice(String invoice) throws NoDataException, BaseException {
		HashMap<String, String> invoiceXML = new HashMap<String, String>();
		try {
			Vector<InvoiceFormatViewSlacBean> invoices = getInvoiceWInvoiceN(invoice);

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sft = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat delivery_date_format = new SimpleDateFormat("MM/dd/yyyy");
			Date now = new Date();
			String timeStamp = sf.format(now) + "T" + sft.format(now);

			Document document = null;

			String old_invoice = "";
			int line_count = 0;
			Element summary = null;
			String fileName = null;

			for (InvoiceFormatViewSlacBean bean : invoices) {
				String invoiceId = "" + bean.getInvoice();
				if (!invoiceId.equals(old_invoice)) {
					// If not the first invoice, close out the older invoice
					if (document != null) {
						invoiceXML.put(fileName, documentToString(document));
						//return invoiceXML;
					}
					SimpleDateFormat dateFormatter = new SimpleDateFormat("MMddyy");
					fileName = "HAASINV" + dateFormatter.format(bean.getInvoiceDate()) + "-" +invoiceId;

					// Start a new document
					document = documentBuilder.newDocument();
					Element haas = document.createElement("HAASTCM");
					haas.setAttribute("timestamp", timeStamp);
					haas.setAttribute("xml:lang", "en-US");
					document.appendChild(haas);

					// Start the new Invoice
					Element inv = document.createElement("Invoice");
					haas.appendChild(inv);
					//invoice has a summary element.
					summary = document.createElement("InvoiceSummary");
					summary.setAttribute("invoiceDate", timeStamp);
					summary.setAttribute("invoiceID", invoiceId);
					inv.appendChild(summary);
					line_count = 0;
				}

				//summary has detail Element.

				Element detail = document.createElement("Invoicedetail");
				summary.appendChild(detail);
				line_count++;

				Element e = null;
				//      <Invoiceline>1</Invoiceline>
				e = document.createElement("Invoiceline");
				e.appendChild(document.createTextNode("" + line_count));
				detail.appendChild(e);
				//      <WorkArea>XL0100</WorkArea>
				e = document.createElement("WorkArea");
				e.appendChild(document.createTextNode(bean.getApplication() != null ? StringHandler.xmlEncode(bean.getApplication()) : ""));
				detail.appendChild(e);
				//      <ChargeType>d</ChargeType>
				e = document.createElement("ChargeType");
				e.appendChild(document.createTextNode(bean.getChargeType() != null ? StringHandler.xmlEncode(bean.getChargeType()) : ""));
				detail.appendChild(e);
				//      <CommodityType>Gas</CommodityType>
				e = document.createElement("CommodityType");
				e.appendChild(document.createTextNode(bean.getCommodity() != null ? StringHandler.xmlEncode(bean.getCommodity()) : ""));
				detail.appendChild(e);
				//      <InvoiceType>MV</InvoiceType>
				e = document.createElement("InvoiceType");
				e.appendChild(document.createTextNode(bean.getItemType() != null ? StringHandler.xmlEncode(bean.getItemType()) : ""));
				detail.appendChild(e);
				//      <Account>53105</Account>
				e = document.createElement("Account");
				e.appendChild(document.createTextNode(bean.getAccountNumber() != null ? StringHandler.xmlEncode(bean.getAccountNumber()) : ""));
				detail.appendChild(e);
				//      <ChargeNo>9474332</ChargeNo>
				e = document.createElement("ChargeNo");
				e.appendChild(document.createTextNode(bean.getAccountNumber2() != null ? StringHandler.xmlEncode(bean.getAccountNumber2()) : ""));
				detail.appendChild(e);
				//      <Requestor>Todd Slater</Requestor>
				e = document.createElement("Requestor");
				e.appendChild(document.createTextNode(bean.getRequestorName() != null ? StringHandler.xmlEncode(bean.getRequestorName()) : ""));
				detail.appendChild(e);
				//      <Approver>Natalie Cramer</Approver>
				e = document.createElement("Approver");
				e.appendChild(document.createTextNode(bean.getApprover() != null ? StringHandler.xmlEncode(bean.getApprover()) : ""));
				detail.appendChild(e);
				//      <MRNumber>996401</MRNumber>
				e = document.createElement("MRNumber");
				e.appendChild(document.createTextNode("" + bean.getPrNumber() != null ? StringHandler.xmlEncode("" + bean.getPrNumber()) : ""));
				detail.appendChild(e);
				//      <MRLine>1</MRLine>
				e = document.createElement("MRLine");
				e.appendChild(document.createTextNode(bean.getLineItem() != null ? StringHandler.xmlEncode(bean.getLineItem()) : ""));
				detail.appendChild(e);
				//      <Descr>100 Liter Liquid Nitrogen</Descr>
				e = document.createElement("Descr");
				e.appendChild(document.createTextNode(bean.getPartDescription() != null ? StringHandler.xmlEncode(bean.getPartDescription()) : ""));
				detail.appendChild(e);
				//      <Packaging>Cylinder</Packaging>
				e = document.createElement("Packaging");
				e.appendChild(document.createTextNode(bean.getPackaging() != null ? StringHandler.xmlEncode(bean.getPackaging()) : ""));
				detail.appendChild(e);
				//      <ReceiptID>2953816</ReceiptID>
				e = document.createElement("ReceiptID");
				e.appendChild(document.createTextNode("" + bean.getReceiptId() != null ? StringHandler.xmlEncode("" + bean.getReceiptId()) : ""));
				detail.appendChild(e);
				//      <DeliveryDate>01/01/2009</DeliveryDate>
				e = document.createElement("DeliveryDate");
				e.appendChild(document.createTextNode(bean.getDateDelivered() != null ? StringHandler.xmlEncode(delivery_date_format.format(bean.getDateDelivered())) : ""));
				detail.appendChild(e);
				//      <Quantity>1</Quantity>
				e = document.createElement("Quantity");
				e.appendChild(document.createTextNode("" + bean.getQuantity()));
				detail.appendChild(e);
				// add attribute here.
				//      <CatalogPrice currency="USD">10.20</CatalogPrice>

				e = document.createElement("CatalogPrice");
				e.setAttribute("currency", "USD");
				e.appendChild(document.createTextNode(bean.getCatalogPrice() != null ? StringHandler.xmlEncode(bean.getCatalogPrice()) : ""));
				detail.appendChild(e);
				//      <InvoicePrice currency="USD">10.20</InvoicePrice>
				e = document.createElement("InvoicePrice");
				e.setAttribute("currency", "USD");
				e.appendChild(document.createTextNode(bean.getInvoiceUnitPrice() != null ? StringHandler.xmlEncode(bean.getInvoiceUnitPrice()) : ""));
				detail.appendChild(e);
				//      <AdditionalCharges>5</AdditionalCharges>
				e = document.createElement("AdditionalCharges");
				e.appendChild(document.createTextNode(bean.getTotalAddCharge() != null ? StringHandler.xmlEncode(bean.getTotalAddCharge()) : ""));
				detail.appendChild(e);
				//      <NetAmount>15.20</NetAmount>
				e = document.createElement("NetAmount");
				e.appendChild(document.createTextNode(bean.getNetAmount() != null ? StringHandler.xmlEncode(bean.getNetAmount()) : ""));
				detail.appendChild(e);
				//      <PriceFlag>N</PriceFlag>
				e = document.createElement("PriceFlag");
				e.appendChild(document.createTextNode(bean.getPriceFlag() != null ? StringHandler.xmlEncode(bean.getPriceFlag()) : ""));
				detail.appendChild(e);
				//      <ContractPO>0000054076</ContractPO>
				String contractPo = "54076";
				if ("Haas TCM Monthly Service Fee".equalsIgnoreCase(bean.getPartDescription())) {
					contractPo = "54012";
				}
				e = document.createElement("ContractPO");
				e.appendChild(document.createTextNode(contractPo != null ? StringHandler.xmlEncode(contractPo) : ""));
				detail.appendChild(e);
				//      <PriceDifference>1.14</PriceDifference>
				e = document.createElement("PriceDifference");
				e.appendChild(document.createTextNode(bean.getPriceDifference() != null ? bean.getPriceDifference().setScale(5, BigDecimal.ROUND_HALF_UP).toPlainString() : "0.0"));
				detail.appendChild(e);

				old_invoice = invoiceId;
			}

			if (document != null) {
				invoiceXML.put(fileName, documentToString(document));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.fatal("Exception:" + e.getMessage(), e);
			BaseException be = new BaseException("Error creating XML invoice for SLAC:" + e.getMessage());
			be.setRootCause(e);
			throw be;
		}
		return invoiceXML;
	}
	public Vector<InvoiceFormatViewSlacBean> getInvoiceWInvoiceN(String invoice) throws NoDataException, BaseException {
		Vector<InvoiceFormatViewSlacBean> c = new Vector<InvoiceFormatViewSlacBean>();
		try {
			//String fileName = null;
			//FileWriter fileWriter = null;
			//ResourceLibrary library = new ResourceLibrary("invoice");
			DbManager dbManager = new DbManager(getClient());
			InvoiceFormatViewSlacBeanFactory factory = new InvoiceFormatViewSlacBeanFactory(dbManager);
			c = (Vector<InvoiceFormatViewSlacBean>) factory.select(new SearchCriteria("invoice", SearchCriterion.EQUALS, invoice), null);
		}
		catch (Exception e) {
			log.fatal("Exception:" + e.getMessage(), e);
			BaseException be = new BaseException("Error retrieving data for XML invoice for SLAC:" + e.getMessage());
			be.setRootCause(e);
			//	        throw be;
		}
		return c;
	}


}