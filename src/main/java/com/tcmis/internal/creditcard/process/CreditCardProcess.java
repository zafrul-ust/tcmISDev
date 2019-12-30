package com.tcmis.internal.creditcard.process;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import paypal.payflow.PayflowAPI;
import paypal.payflow.PayflowUtility;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.creditcard.beans.CreditCardBean;
import com.tcmis.internal.creditcard.beans.CreditCardInputBean;
import com.tcmis.internal.creditcard.beans.CreditCardLineBean;
import com.tcmis.internal.invoice.beans.InvoiceSubmissionBean;
import com.tcmis.internal.invoice.factory.InvoiceSubmissionBeanFactory;

/******************************************************************************
 * Process to process credit card payments.
 * @version 1.0
 *****************************************************************************/
public class CreditCardProcess
extends BaseProcess {

	private static final String USER = "ursradian";
	private static final String VENDOR = "ursradian";
	private static final String PWD = "rad1an";
	private static final String PARTNER = "AmericanExpress";
	private static final String TAXAMT = "0";
	private static final String TENDER = "C";
	private static final String CREDIT = "C";
	private static final String CHARGE = "S";

	private PayflowAPI pfp;
	//private CreditCardInputBean creditCardInputBean;
	//private CreditCardPaymentHistoryBean creditCardPaymentHistoryBean;
	//private String receipt;

	public CreditCardProcess(String client) {
		super(client);
	}

	public InvoiceSubmissionBean processWebPayment(CreditCardInputBean bean) throws
	BaseException {
		InvoiceSubmissionBean invoiceSubmissionBean = null;

		return invoiceSubmissionBean;
	}

	public void processPayment(CreditCardBean bean) throws BaseException {
		StringBuilder sb = new StringBuilder("");
		try {
			BigDecimal amount = bean.getInvoiceAmount();
			String type = CHARGE;
			// transaction is non-null only when reversing
			if (bean.getTxref() != null) {
				amount = amount.negate();
			}
			if (amount.signum() < 0) {
				type = CREDIT;
				amount = amount.negate();
			}

			sb.append("USER=" ).append( USER);
			sb.append("&VENDOR=" ).append( VENDOR);
			sb.append("&PARTNER=" ).append( PARTNER);
			sb.append("&PWD=" ).append( PWD);
			sb.append("&TENDER=" ).append( TENDER);
			sb.append("&TRXTYPE=" ).append( type);
			sb.append("&ACCT=" ).append( bean.getCreditCardNumber());
			sb.append("&EXPDATE=" ).append(DateHandler.formatDate(bean.getCreditCardExpirationDate(), "MMyy"));
			sb.append("&AMT=" ).append( amount);
			sb.append("&INVNUM=" ).append( bean.getInvoice());
			sb.append("&SHIPTOZIP=" ).append( bean.getZip());
			sb.append("&TAXAMT=" ).append( TAXAMT);
			sb.append("&REQNAME=" ).append( bean.getCompanyId());
			Collection lineCollection = bean.getInvoiceLines();
			Iterator iterator = lineCollection.iterator();
			// add invoice history lines and set Level 3 parameters
			//lines are 1 based so I need add one to the count
			int count = 0;
			for (int i = 0; iterator.hasNext(); i++) {
				count++;
				CreditCardLineBean line = (CreditCardLineBean) iterator.next();
				sb.append("&L_QTY");
				sb.append(count);
				sb.append("=");
				sb.append(line.getQuantity());
				sb.append("&L_UOM");
				sb.append(count);
				sb.append("=EA");
				sb.append("&L_COST");
				sb.append(count);
				sb.append("=");
				sb.append(line.getInvoiceUnitPrice());
				sb.append("&L_DESC");
				sb.append(count);
				sb.append("=LINE-");
				sb.append(count);
				sb.append("&L_CATALOGNUM");
				sb.append(count);
				sb.append("=");
				sb.append(line.getPoNumber());
				sb.append("&L_COSTCENTERNUM");
				sb.append(count);
				sb.append("=");
				sb.append(line.getIssueId());
			}
			if (log.isDebugEnabled()) {
				log.debug("Sending to paypal:" + sb.toString());
			}
			this.updateInvoiceSubmission(this.sendPayment(sb.toString()), bean);
		}
		catch (Exception e) {
			log.error("Error processing payment for " + sb.toString(), e);
			BaseException be = new BaseException(e);
			throw be;
		}
	}

	private String sendPayment(String paymentString) throws BaseException {
		pfp = newPayflowContext();
		String receipt = pfp.submitTransaction(paymentString, PayflowUtility.getRequestId());
		if (log.isDebugEnabled()) {
			log.debug("PAYPAL RECEIPT:" + receipt);
		}
		return receipt;
	}

	private void updateInvoiceSubmission(String receiptString, CreditCardBean creditCardBean) throws Exception {
		DbManager dbManager = new DbManager(getClient());
		InvoiceSubmissionBeanFactory factory = new
		InvoiceSubmissionBeanFactory(dbManager);
		InvoiceSubmissionBean invoiceSubmissionBean = null;
		Collection c = creditCardBean.getInvoiceLines();
		Iterator iterator = c.iterator();
		while (iterator.hasNext()) {
			CreditCardLineBean line = (CreditCardLineBean) iterator.next();
			invoiceSubmissionBean = new InvoiceSubmissionBean();
			Map result = StringHandler.decodeQueryString(receiptString);
			if (result.get("RESULT") != null) {
				invoiceSubmissionBean.setResultCode( ( (String[]) result.get(
				"RESULT"))[0]);
			}
			if (result.get("AUTHCODE") != null) {
				invoiceSubmissionBean.setAuthcode( ( (String[]) result.get(
				"AUTHCODE"))[0]);
			}
			if (result.get("PNREF") != null) {
				invoiceSubmissionBean.setTxref( ( (String[]) result.get("PNREF"))[
				                                                                  0]);
			}
			invoiceSubmissionBean.setCreditCardExpirationDate(creditCardBean.
					getCreditCardExpirationDate());
			invoiceSubmissionBean.setCreditCardNumber(creditCardBean.getCreditCardNumber());
			invoiceSubmissionBean.setPoNumber(line.getPoNumber());
			invoiceSubmissionBean.setEntryId(creditCardBean.getInvoice());
			invoiceSubmissionBean.setInvoice(creditCardBean.getInvoice());
			invoiceSubmissionBean.setInvoiceAmount(creditCardBean.getInvoiceAmount());
			invoiceSubmissionBean.setInvoiceDate(new Date());
			invoiceSubmissionBean.setInvoicePeriod(creditCardBean.getInvoicePeriod());
			invoiceSubmissionBean.setIssueId(line.getIssueId());
			invoiceSubmissionBean.setIssueAmount(line.getInvoiceUnitPrice().multiply(line.getQuantity()));
			//if result is null (for credits) or 0 then it's a valid transaction
			if (invoiceSubmissionBean.getResultCode() == null ||
					invoiceSubmissionBean.getResultCode().equalsIgnoreCase("0")) {
				invoiceSubmissionBean.setStatus("PROCESSED");
			}
			else {
				invoiceSubmissionBean.setStatus("ERROR");
				this.sendNotification(invoiceSubmissionBean);
			}
			factory.insert(invoiceSubmissionBean);
		}
	}

	private void sendNotification(InvoiceSubmissionBean invoiceSubmissionBean) {
		MailProcess.sendEmail("deverror@haastcm.com",
				"",
				"verisigninvoice@haastcm.com",
				"Invoice " + invoiceSubmissionBean.getInvoice() + " returned code " + invoiceSubmissionBean.getResultCode() + ".",
				"Message:" + invoiceSubmissionBean.getReasonRejected());
	}

	/**
	 * The resource file <i>f73e89fd.0 </i> is the payflow certificate.
	 *
	 * @return @throws
	 *         URISyntaxException
	 * @throws IOException
	 */

	private PayflowAPI newPayflowContext() throws BaseException {
		PayflowAPI pfp;
		try {
			String pfurl = "payflowpro.paypal.com ";

			/*
		String certpath = System.getProperty("payflow.cert.path");
      if (certpath == null) {
        File fcertpath = new File(new URI(ResourceHandler.getResource(getClass(),
            "")
                                          .toExternalForm()));
        pfp.SetCertPath(fcertpath.getCanonicalPath());
      }
      else {
        pfp.SetCertPath(certpath);
      }
      /*
			 * Create a context and static parameters for billing Amex thru Paypal
			 * PayflowPro args are (hostname,port,timeout in seconds, proxyhostname,
			 * proxyport, proxylogon, proxypassword) arg types are(String, int, int,
			 * String, int, String, String)
			 */
			//pfp.CreateContext(pfurl, 443, 30, "", 0, "", "");
			pfp = new PayflowAPI(pfurl,443,30,"",0,"","");
		}catch (Exception urie) {
			log.error("Error setting up payflowpro api", urie);
			BaseException be = new BaseException(urie);
			throw be;
		}
		return pfp;
	}

	private boolean isCreditCardInputBeanValid(CreditCardInputBean bean) {
		boolean valid = true;
		if (bean.getCcExpirationMonth() == null) {
			valid = false;
		}
		else if (bean.getCcExpirationYear() == null) {
			valid = false;
		}
		else if (bean.getCcName() == null || bean.getCcName().trim().length() < 1) {
			valid = false;
		}
		else if (bean.getCcNumber() == null || bean.getCcNumber().trim().length() < 1) {
			valid = false;
		}
		else if (bean.getChargeAmount() == null || bean.getChargeAmount().intValue() == 0) {
			valid = false;
		}
		else if (bean.getTaxAmount() == null) {
			valid = false;
		}
		else if (bean.getClient() == null || bean.getClient().trim().length() < 1) {
			valid = false;
		}
		else if (bean.getPoNumber() == null || bean.getPoNumber().trim().length() < 1) {
			valid = false;
		}
		return valid;
	}
}