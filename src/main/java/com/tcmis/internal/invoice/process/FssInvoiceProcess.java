package com.tcmis.internal.invoice.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.invoice.beans.FssManualInvoicePeriodBean;
import com.tcmis.internal.invoice.beans.InvoiceBean;
import com.tcmis.internal.invoice.beans.InvoiceLineBean;
import com.tcmis.internal.invoice.beans.InvoicePeriodBean;
import com.tcmis.internal.invoice.factory.FssManualInvoicePeriodBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;
import com.tcmis.internal.invoice.factory.InvoicePeriodBeanFactory;

/***********************************************************
 * Process for creating Slac invoice.
 ***********************************************************/

public class FssInvoiceProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	int ROW_LENGTH = 394;
	private static final String FILE_NAME = "radian-invoice.txt";
	private static final String PROJ = "10144482    ";
	private static final String VENDOR = "Haas Corp                          ";
	private static final String ZERO = "000000000000.00";
	private static final String TERM = "0C";
	private static final String QTY = "0000000001";
	private static final String PMETH = "C";


	public FssInvoiceProcess(String client) {
		super(client);
	}

	/******************************************************************************
	 * Creates an text invoice for FSS.<BR>
	 *
	 * @throws BaseException If an <code>SQLException</code> is thrown
	 * @throws Exception If an error not expected by the code is thrown
	 ****************************************************************************/
	public void createInvoices() throws BaseException {
		try {
			DbManager dbManager = new DbManager(this.getClient());
			//check if there are any invoices to create
			Collection invoicePeriodColl = this.getInvoicePeriodForProcess();
			Iterator iter = invoicePeriodColl.iterator();
			while (iter.hasNext()) {
				FssManualInvoicePeriodBean fssManualInvoicePeriodBean = (FssManualInvoicePeriodBean) iter.next();
				BigDecimal invoicePeriod = fssManualInvoicePeriodBean.getInvoicePeriod();
				if (invoicePeriod != null) {
					Date startDate = null;
					Date endDate = null;
					//set up buffer for a row
					//StringBuilder rowTemplate = new StringBuilder(ROW_LENGTH);
					StringBuilder row = null;

					ResourceLibrary library = new ResourceLibrary("invoice");
					String backupDir = library.getString("fss.invoice.backupdir");
					String filePath = library.getString("fss.invoice.dir") + FILE_NAME;
					FileWriter fileWriter = new FileWriter(new File(filePath));

					InvoicePeriodBeanFactory invoicePeriodBeanFactory = new InvoicePeriodBeanFactory(dbManager);
					SearchCriteria periodCriteria = new SearchCriteria("invoicePeriod", SearchCriterion.EQUALS, invoicePeriod.toString());
					periodCriteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, "FSS");
					Collection coll = invoicePeriodBeanFactory.select(periodCriteria);
					Iterator it = coll.iterator();
					while(it.hasNext()) {
						InvoicePeriodBean invoicePeriodBean = (InvoicePeriodBean)it.next();
						startDate = invoicePeriodBean.getStartDate();
						endDate = invoicePeriodBean.getEndDate();
					}
					InvoiceBeanFactory factory = new InvoiceBeanFactory(dbManager);
					Collection c = factory.selectFss(invoicePeriod);
					if(c == null || c.size() == 0) {
						MailProcess.sendEmail("deverror@haastcm.com",
								"",
								"nofssinvoice@tcmis.com",
								"There are no invoices",
								"There are no invoices for period " + invoicePeriod);
					}
					Iterator iterator = c.iterator();
					BigDecimal invoice = null;
					//int i = 0;
					DecimalFormat invoiceAmountFormat = new DecimalFormat("00000000000.00");
					DecimalFormat invoiceLineFormat = new DecimalFormat("0000");
					while (iterator.hasNext()) {
						InvoiceBean b = (InvoiceBean) iterator.next();
						row = new StringBuilder("");
						//row = rowTemplate;
						row.append("1");
						row.append(VENDOR);
						row.append(PROJ);
						row.append(b.getInvoice());
						this.appendSpaces(row, 12-b.getInvoice().toString().length());
						row.append(DateHandler.formatDate(new Date(), "yyyyMMdd"));
						//if invoice amount is negative insert a minus sign, otherwise a zero
						if(b.getInvoiceAmount().signum() < 0) {
							row.append("-");
						}
						else {
							row.append("0");
						}
						row.append(invoiceAmountFormat.format(b.getInvoiceAmount().abs()));
						row.append(ZERO);
						row.append(ZERO);
						row.append(ZERO);
						//empty between 128 and 183
						this.appendSpaces(row, 55);
						row.append(TERM);
						row.append(b.getLocationLabel());
						//strip out the string "CALTEX" if it's there
						if(b.getAccountSysLabel().startsWith("CALTEX")) {
							b.setAccountSysLabel(b.getAccountSysLabel().substring(7));
						}
						row.append(b.getAccountSysLabel());
						this.appendSpaces(row, 204-b.getAccountSysLabel().length());
						//done with header row
						fileWriter.write(row.toString() + "\n");
						Iterator iterator2 = b.getInvoiceLineCollection().iterator();
						while(iterator2.hasNext()) {
							InvoiceLineBean lineBean = (InvoiceLineBean) iterator2.next();
							//System.out.println("invoice line:" + lineBean.getInvoiceLine());
							row = new StringBuilder("");
							row.append("2");
							row.append(lineBean.getInvoice());
							this.appendSpaces(row, 12 - lineBean.getInvoice().toString().length());
							row.append(VENDOR);
							row.append(invoiceLineFormat.format(lineBean.getInvoiceLine()));
							row.append(DateHandler.formatDate(startDate, "yyyyMMdd"));
							row.append(DateHandler.formatDate(endDate, "yyyyMMdd"));
							//nothing between 68 and 76
							this.appendSpaces(row, 8);
							if ("RTIS".equalsIgnoreCase(b.getAccountSysLabel()) ||
									"MKMold".equalsIgnoreCase(b.getAccountSysLabel()) ||
									"ELCAN".equalsIgnoreCase(b.getAccountSysLabel())) {
								//nothing between 76 and 91
								this.appendSpaces(row, 15);
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber()));
								this.appendSpaces(row, 15 - StringHandler.emptyIfNull(lineBean.getAccountNumber()).length());
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber2()));
								this.appendSpaces(row, 5 - StringHandler.emptyIfNull(lineBean.getAccountNumber2()).length());
							}
							else if ("SES".equalsIgnoreCase(b.getAccountSysLabel()) ||
									"MISSISSIPPI".equalsIgnoreCase(b.getAccountSysLabel()) ||
									StringHandler.emptyIfNull(b.getAccountSysLabel()).startsWith("COMMAND")) {
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber()).toUpperCase());
								this.appendSpaces(row, 10 - StringHandler.emptyIfNull(lineBean.getAccountNumber()).length());
								//nothing between 86 and 91
								this.appendSpaces(row, 5);
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber2()).toUpperCase());
								this.appendSpaces(row, 20 - StringHandler.emptyIfNull(lineBean.getAccountNumber2()).length());
							}
							else if ("MFAB".equalsIgnoreCase(b.getAccountSysLabel())) {
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber()));
								this.appendSpaces(row, 15 - StringHandler.emptyIfNull(lineBean.getAccountNumber()).length());
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber2()));
								this.appendSpaces(row, 20 - StringHandler.emptyIfNull(lineBean.getAccountNumber2()).length());
							}
							else if (StringHandler.emptyIfNull(b.getAccountSysLabel()).startsWith("MRP") ||
									StringHandler.emptyIfNull(b.getAccountSysLabel()).startsWith("Ft") ||
									StringHandler.emptyIfNull(b.getAccountSysLabel()).startsWith("SAP") ||
									StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("MKM") > -1 ||
									"Crosspan".equalsIgnoreCase(b.getAccountSysLabel()) ||
									"WVRTSC".equalsIgnoreCase(b.getAccountSysLabel()) ||
									"RES".equalsIgnoreCase(b.getAccountSysLabel()) ||
									"ADC".equalsIgnoreCase(b.getAccountSysLabel()) ||
									"RAY SERVICE".equalsIgnoreCase(b.getAccountSysLabel())) {
								//nothing between 76 and 91
								this.appendSpaces(row, 15);
								//trim account number since some times it's longer than 15
								if(StringHandler.emptyIfNull(lineBean.getAccountNumber()).length() > 15) {
									lineBean.setAccountNumber(lineBean.getAccountNumber().substring(0,15));
								}
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber()).toUpperCase());
								this.appendSpaces(row, 20 - StringHandler.emptyIfNull(lineBean.getAccountNumber()).length());
							}
							else if (StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("Tucson") > -1 ||
									StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("RES ") > -1 ||
									StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("APEX ") > -1 ||
									StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("FTWAYNE APEX") > -1) {
								if (StringHandler.emptyIfNull(lineBean.getAccountNumber2()).length() == 5) {
									row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber2()));
									this.appendSpaces(row, 10 - StringHandler.emptyIfNull(lineBean.getAccountNumber2()).length());
								}
								else {
									this.appendSpaces(row, 10);
								}
								this.appendSpaces(row, 25);
							}
							//if invoice amount is negative insert a minus sign, otherwise a zero
							if (lineBean.getTotalAmount().signum() < 0) {
								row.append("-");
							}
							else {
								row.append("0");
							}
							row.append(invoiceAmountFormat.format(lineBean.getTotalAmount().abs()));
							row.append(QTY);
							//nothing between 136 and 138
							this.appendSpaces(row, 2);
							//if invoice amount is negative insert a minus sign, otherwise a zero
							if (lineBean.getTotalAmount().signum() < 0) {
								row.append("-");
							}
							else {
								row.append("0");
							}
							row.append(invoiceAmountFormat.format(lineBean.getTotalAmount().abs()));
							//nothing between 153 and 174
							this.appendSpaces(row, 21);
							if (StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("RES ") > -1 &&
									StringHandler.emptyIfNull(lineBean.getAccountNumber()).length() == 6) {
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber()));
								this.appendSpaces(row, 20 - StringHandler.emptyIfNull(lineBean.getAccountNumber()).length());
							}
							else {
								this.appendSpaces(row, 20);
							}
							//nothing between 194 and 322
							this.appendSpaces(row, 128);
							if (StringHandler.emptyIfNull(lineBean.getAccountNumber()).length() == 13 &&
									(StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("Tucson") > -1 ||
											StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("RES ") > -1 ||
											StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("APEX ") > -1 ||
											StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("FTWAYNE APEX") > -1)) {
								row.append(lineBean.getAccountNumber().substring(0,9));
								this.appendSpaces(row, 3);
								row.append(lineBean.getAccountNumber().substring(9));
							}
							else {
								this.appendSpaces(row, 16);
							}
							//nothing between 338 and 343
							this.appendSpaces(row, 5);
							row.append(PMETH);
							//nothing between 344 and 363
							this.appendSpaces(row, 19);
							if (StringHandler.emptyIfNull(lineBean.getAccountNumber()).length() == 7 &&
									(StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("Tucson") > -1 ||
											StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("RES ") > -1 ||
											StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("APEX ") > -1 ||
											StringHandler.emptyIfNull(b.getAccountSysLabel()).indexOf("FTWAYNE APEX") > -1)) {
								row.append(StringHandler.emptyIfNull(lineBean.getAccountNumber()));
								this.appendSpaces(row, 20 - StringHandler.emptyIfNull(lineBean.getAccountNumber()).length());
							}
							else {
								this.appendSpaces(row, 20);
							}
							this.appendSpaces(row, 10);
							//end if child row
							fileWriter.write(row.toString()+ "\n");
						}
					}
					fileWriter.flush();
					fileWriter.close();
					this.setFilePermission(filePath);
					FileHandler.copy(new File(filePath), new File(backupDir + FILE_NAME + "." + invoicePeriod));
					this.incrementInvoicePeriod(invoicePeriod);
					dbManager.doProcedure("p_indy_committed_cost", null);
				}else {
					MailProcess.sendEmail("deverror@haastcm.com",
							"",
							"nofssinvoice@tcmis.com",
							"No new invoice period",
					"Could not find a new invoice period.");
				}
				//update fss_manual_invoice_process
				FssManualInvoicePeriodBeanFactory fssManualInvoicePeriodBeanFactory = new FssManualInvoicePeriodBeanFactory(dbManager);
				fssManualInvoicePeriodBean.setStatus("Processed");
				fssManualInvoicePeriodBeanFactory.update(fssManualInvoicePeriodBean);
			} //end of while loop
		}catch (Exception e) {
			log.fatal("Error creating invoices for FSS:" + e.getMessage());
			BaseException be = new BaseException("Error creating invoices for FSS:" +
					e.getMessage());
			be.setRootCause(e);
			MailProcess.sendEmail("deverror@haastcm.com",
					"",
					"fssinvoice@tcmis.com",
					"Error creating invoices",
					"Error creating fss invoices, check the logs." +
					e.getMessage());
			throw be;
		}

	}

	private void appendSpaces(StringBuilder sb, int numberOfSpaces) {
		for(int i=0; i < numberOfSpaces; i++) {
			sb.append(" ");
		}
	}

	//I'm just copying the existing format from Steve's invoices so I can't do anything about the format...
	private void writeHeader(BigDecimal invoicePeriod, Writer writer) throws
	BaseException, Exception {
		writer.write("");

	}

	private BigDecimal getLastInvoicePeriod() throws NoDataException,
	BaseException, Exception {
		BigDecimal b = null;
		ResourceLibrary library = new ResourceLibrary("invoice");
		BufferedReader in = new BufferedReader(new FileReader(library.getString("fssinvoiceperiod.submitted.file")));
		String line = null;
		do {
			line = in.readLine();
			if(line != null) {
				b = new BigDecimal(line);
			}
		}
		while(line != null);

		return b;
	}

	private BigDecimal getNewInvoicePeriod() throws NoDataException,
	BaseException, Exception {
		BigDecimal b = null;
		DbManager dbManager = new DbManager(this.getClient());
		InvoiceBeanFactory factory = new InvoiceBeanFactory(dbManager);
		Collection c = factory.selectMaxFssInvoicePeriod();
		Iterator it = c.iterator();
		while (it.hasNext()) {
			InvoiceBean bean = (InvoiceBean) it.next();
			b = bean.getInvoicePeriod();
		}
		if(b.compareTo(this.getLastInvoicePeriod()) < 1) {
			//no new invoice period, reset invoice period
			b = null;
		}
		return b;
	}

	private Collection getInvoicePeriodForProcess() throws NoDataException, BaseException, Exception {
		Collection result = new Vector(0);
		DbManager dbManager = new DbManager(this.getClient());
		FssManualInvoicePeriodBeanFactory factory = new FssManualInvoicePeriodBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("status", SearchCriterion.EQUALS, "New");
		Collection c = factory.select(criteria);
		/* not going to compare at this time
	 Iterator it = c.iterator();
	 while (it.hasNext()) {
		FssManualInvoicePeriodBean bean = (FssManualInvoicePeriodBean) it.next();
		if(bean.getInvoicePeriod().compareTo(this.getLastInvoicePeriod()) < 1) {
			//notify that invoice period already processed
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","FSS invoice period: "+bean.getInvoicePeriod()+" already processed","FSS invoice period already processed");
			bean.setStatus("Error");
			factory.update(bean);
		 }else {
			result.add(bean);
		}
	 }
		 */
		return c;
	}

	private void incrementInvoicePeriod(BigDecimal invoicePeriod) throws BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("invoice");
		FileWriter fw = new FileWriter(library.getString("fssinvoiceperiod.submitted.file"), true);
		fw.write(invoicePeriod.toString() + "\n");
		fw.flush();
		fw.close();
	}

	private void setFilePermission(String filePath) throws BaseException, Exception {
		Process p = Runtime.getRuntime().exec("chmod 664 " + filePath);
		p.waitFor() ;
		p = Runtime.getRuntime().exec("chown apache " + filePath);
		p.waitFor() ;
		p = Runtime.getRuntime().exec("chgrp raybill " + filePath);
		p.waitFor() ;
	}
}