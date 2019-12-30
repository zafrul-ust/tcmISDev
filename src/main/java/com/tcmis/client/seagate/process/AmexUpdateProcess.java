package com.tcmis.client.seagate.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.seagate.beans.AbstractAmexRecord;
import com.tcmis.client.seagate.beans.AmexAdjustmentBean;
import com.tcmis.client.seagate.beans.AmexChargebackBean;
import com.tcmis.client.seagate.beans.AmexHeaderRecord;
import com.tcmis.client.seagate.beans.AmexOtherFeesBean;
import com.tcmis.client.seagate.beans.AmexRocBean;
import com.tcmis.client.seagate.beans.AmexSocBean;
import com.tcmis.client.seagate.beans.AmexSummaryBean;
import com.tcmis.client.seagate.beans.AmexTrailerRecord;
import com.tcmis.client.seagate.factory.AmexAdjustmentBeanFactory;
import com.tcmis.client.seagate.factory.AmexChargebackBeanFactory;
import com.tcmis.client.seagate.factory.AmexOtherFeesBeanFactory;
import com.tcmis.client.seagate.factory.AmexRocBeanFactory;
import com.tcmis.client.seagate.factory.AmexSocBeanFactory;
import com.tcmis.client.seagate.factory.AmexSummaryBeanFactory;
import com.tcmis.client.seagate.util.NewFileList;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.invoice.beans.InvoiceSubmissionBean;
import com.tcmis.internal.invoice.factory.InvoiceSubmissionBeanFactory;

public class AmexUpdateProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static boolean locked;
	public final int RECORD_SIZE = 450;
	public final int RECORD_TYPE_INDEX = 42;
	public final int RECORD_DETAIL_INDEX0 = 43;
	public final int RECORD_DETAIL_INDEX1 = 44;

	public final int RECORD_SUMMARY = 1;
	public final int RECORD_DETAIL = 2;
	public final int RECORD_ROC = 3;

	public final int RECORD_SOC = 10;
	public final int RECORD_CHARGEBACK = 20;
	public final int RECORD_ADJUSTMENT = 30;
	public final int RECORD_ASSETBILLING = 40;
	public final int RECORD_COMMISSION = 41;
	public final int RECORD_OTHER_FEES = 50;

	public final int HEADER = 0;
	public final int SUMMARY = 100;
	public final int SOC = 210;
	public final int CHARGEBACK = 220;
	public final int ADJUSTMENT = 230;
	public final int ASSET_BILLING = 240;
	public final int TAKE_ONE_COMMISSION = 241;
	public final int OTHER_FEES = 250;
	public final int ROC = 311;
	public final int TRAILER = 9;

	private byte[] data, sdata;
	private AbstractAmexRecord amexRecord = null;
	private BigDecimal amexSocLine = new BigDecimal("0");
	private BigDecimal amexRocId = new BigDecimal("0");
	private String paymentId = null;
	private Date paymentDate = null;
	private BigDecimal paymentAmount = null;
	private DbManager dbManager;

	public AmexUpdateProcess(String client) throws Exception {
		super(client);
	}

	public void processFiles() throws Exception {
		if (!lockIt()) {
			log.info("The invoice process is locked, try again later.");
			MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com",
					"SEAGATE - AmexUpdateProcess is locked",
			"See log file");
		}
		else {
			Connection connection = null;
			try {
				dbManager = new DbManager(getClient());
				//NewFileList newfiles = new NewFileList(new File("/home/mathias/amex"),
				//                                       new File("/home/mathias/amex/.history"));
				NewFileList newfiles = new NewFileList(new File("/amex"),new File("/amex/.history"));
				if (log.isDebugEnabled()) {
					log.debug("Found " + newfiles.size() + " files.");
				}
				Iterator it = newfiles.iterator();
				while (it.hasNext()) {
					connection = dbManager.getConnection();
					connection.setAutoCommit(false);
					paymentId = null;
					paymentDate = null;
					paymentAmount = null;
					File f = (File) it.next();
					log.info("Processing Amex Report: " + f.getName());
					FileInputStream in = new FileInputStream(f);
					while (true) {
						getNextRecord(in);
						if (amexRecord == null) {
							continue;
						}
						if ((amexRecord.getClass()).isInstance(new AmexHeaderRecord())) {
							log.debug("FOUND HEADER");
							continue;
						}
						if ((amexRecord.getClass()).isInstance(new AmexTrailerRecord())) {
							log.debug("FOUND TRAILER");
							break;
						}
						log.debug("SAVING!!!!!!!!!!!!!!!!!!!!");
						save();
					}
					connection.commit();
					connection.setAutoCommit(true);
					dbManager.returnConnection(connection);
					connection = null;
					newfiles.addToHistory(f);
				}
			}
			catch (Exception e) {
				log.error("There was an error:", e);
				MailProcess.sendEmail("deverror@tcmis.com", null,
						"deverror@tcmis.com",
						"SEAGATE - Amex update", e.getMessage());
			}
			finally {
				//in case there was an error...
				try {
					connection.rollback();
					connection.setAutoCommit(true);
					dbManager.returnConnection(connection);
				}
				catch(Exception e) {
					//ignore
				}
				unlockIt();
			}
		}
	}

	private void getNextRecord(InputStream in) throws Exception {
		data = new byte[RECORD_SIZE];
		sdata = new byte[RECORD_SIZE];
		String recordName;
		int recordType;
		int detailType;

		if (in.read(data, 0, RECORD_SIZE) < 0) {
			log.debug("file end prematurely.");
			throw new Exception("file end prematurely.");
		}
		recordName = new String(data, 0, 5, "US-ASCII");
		if (log.isDebugEnabled()) {
			log.debug("Getting next record: " + recordName);
		}
		if ("DFHDR".equals(recordName)) {
			amexRecord = new AmexHeaderRecord();
		}
		else if ("DFTRL".equals(recordName)) {
			amexRecord = new AmexTrailerRecord();
		}
		else {
			recordType = data[RECORD_TYPE_INDEX] - '0';
			detailType = (10 * (data[RECORD_DETAIL_INDEX0] - '0')) +
			(data[RECORD_DETAIL_INDEX1] - '0');
			if (log.isDebugEnabled()) {
				log.debug("record type: " + recordType);
				log.debug("detail type: " + detailType);
			}
			switch (recordType) {
				case RECORD_SUMMARY:
					amexRecord = getAmexSummaryBean(data);
					paymentDate = amexRecord.getPaymentDate();
					paymentId = amexRecord.getPaymentId();
					paymentAmount = ( (AmexSummaryBean) amexRecord).getPaymentAmount();
					break;
				case RECORD_DETAIL:
					switch (detailType) {
						case RECORD_SOC:
							amexRecord = getAmexSocBean(data);
							break;
						case RECORD_CHARGEBACK:
							amexRecord = getAmexChargebackBean(data);
							break;
						case RECORD_ADJUSTMENT:
							amexRecord = getAmexAdjustmentBean(data);
							break;
						case RECORD_ASSETBILLING:
						case RECORD_COMMISSION:
						case RECORD_OTHER_FEES:
							amexRecord = getAmexOtherFeesBean(data);
							break;
					}
					break;
				case RECORD_ROC:
					amexRecord = getAmexRocBean(data);
					break;
			}
		}
	}

	private void save() throws BaseException {
		if ((amexRecord.getClass()).isInstance(new AmexSummaryBean())) {
			AmexSummaryBeanFactory factory = new AmexSummaryBeanFactory(dbManager);
			if (factory.isPresent( (AmexSummaryBean) amexRecord)) {
				factory.update( (AmexSummaryBean) amexRecord);
			}
			else {
				factory.insert( (AmexSummaryBean) amexRecord);
			}
		}
		else if ((amexRecord.getClass()).isInstance(new AmexSocBean())) {
			AmexSocBeanFactory factory = new AmexSocBeanFactory(dbManager);
			if (factory.isPresent( (AmexSocBean) amexRecord)) {
				factory.update( (AmexSocBean) amexRecord);
			}
			else {
				factory.insert( (AmexSocBean) amexRecord);
			}
		}
		else if ((amexRecord.getClass()).isInstance(new AmexRocBean())) {
			AmexRocBeanFactory factory = new AmexRocBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("entryId",
					SearchCriterion.EQUALS,
					( (AmexRocBean) amexRecord).getEntryRef().toString());

			InvoiceSubmissionBeanFactory historyFactory = new InvoiceSubmissionBeanFactory(dbManager);
			Collection c = historyFactory.select(criteria);
			Iterator iterator = c.iterator();
			while (iterator.hasNext()) {
				InvoiceSubmissionBean historyBean = (InvoiceSubmissionBean)iterator.next();
				AmexRocBean tempBean = (AmexRocBean) amexRecord;
				tempBean.setPoNumber(historyBean.getPoNumber());
				//tempBean.setPrNumber(null);
				tempBean.setLineItem(NumberHandler.emptyIfNull(historyBean.getLineItem()));
				tempBean.setIssueId(historyBean.getIssueId());
				tempBean.setInvoice(historyBean.getInvoice());

				if (factory.isPresent( (AmexRocBean) amexRecord)) {
					factory.update(tempBean);
				}
				else {
					factory.insert(tempBean);
				}
			}
		}
		else if ((amexRecord.getClass()).isInstance(new AmexChargebackBean())) {
			AmexChargebackBeanFactory factory = new AmexChargebackBeanFactory(
					dbManager);
			if (factory.isPresent( (AmexChargebackBean) amexRecord)) {
				factory.update( (AmexChargebackBean) amexRecord);
			}
			else {
				factory.insert( (AmexChargebackBean) amexRecord);
			}
		}
		else if ((amexRecord.getClass()).isInstance(new AmexAdjustmentBean())) {
			AmexAdjustmentBeanFactory factory = new AmexAdjustmentBeanFactory(
					dbManager);

			if (factory.isPresent( (AmexAdjustmentBean) amexRecord)) {
				factory.update( (AmexAdjustmentBean) amexRecord);
			}
			else {
				factory.insert( (AmexAdjustmentBean) amexRecord);
			}
		}
		else if ((amexRecord.getClass()).isInstance(new AmexOtherFeesBean())) {
			AmexOtherFeesBeanFactory factory = new AmexOtherFeesBeanFactory(
					dbManager);

			if (factory.isPresent( (AmexOtherFeesBean) amexRecord)) {
				factory.update( (AmexOtherFeesBean) amexRecord);
			}
			else {
				factory.insert( (AmexOtherFeesBean) amexRecord);
			}
		}
		else {
			log.error("RECORD TYPE NOT FOUND:" + amexRecord.getClass().toString());
		}

	}

	private AmexSummaryBean getAmexSummaryBean(byte[] data) throws
	NumberFormatException, UnsupportedEncodingException {
		amexSocLine = new BigDecimal("0");
		amexRocId = new BigDecimal("0");
		AmexSummaryBean bean = new AmexSummaryBean();
		this.populateCommonAttributes(data, bean);
		bean.setPaymentAmount(decodeAmount(data, 52, 11));
		bean.setDebitBalanceAmount(decodeAmount(data, 63, 9));
		bean.setAbaBankId(new String(data, 72, 9, "US-ASCII").trim());
		bean.setDdaAccountId(new String(data, 81, 17).trim());
		return bean;
	}

	private AmexSocBean getAmexSocBean(byte[] data) throws
	NumberFormatException,
	UnsupportedEncodingException {
		amexSocLine = amexSocLine.add(new BigDecimal("1"));
		amexRocId = new BigDecimal("0");
		AmexSocBean bean = new AmexSocBean();
		this.populateCommonAttributes(data, bean);
		bean.setSocId(new BigDecimal(new String(data, 59, 6, "US-ASCII").trim()));
		bean.setSocLine(amexSocLine);
		bean.setPaymentId(paymentId);
		bean.setSubmitDate(getJulianDate(new String(data, 45, 4, "US-ASCII").trim(),
				new String(data, 49, 3, "US-ASCII").trim()));
		bean.setProcessDate(getJulianDate(new String(data, 52, 4, "US-ASCII").
				trim(),
				new String(data, 56, 3, "US-ASCII").
				trim()));
		bean.setSocAmount(decodeAmount(data, 65, 11));
		bean.setDiscountAmount(decodeAmount(data, 76, 9));
		bean.setNetSocAmount(decodeAmount(data, 99, 11));
		bean.setDiscountRate(new BigDecimal(new BigInteger(new String(data, 110,
				5,
		"US-ASCII").trim()), 5)); // 5 decimal places
		bean.setRocCount(decodeAmount(data, 152, 5, false));
		bean.setPcard( (data[166] == 'P'));
		return bean;
	}

	private AmexRocBean getAmexRocBean(byte[] data) throws
	NumberFormatException,
	UnsupportedEncodingException {
		amexRocId = amexRocId.add(new BigDecimal("1"));
		AmexRocBean bean = new AmexRocBean();
		this.populateCommonAttributes(data, bean);
		bean.setRocId(amexRocId);
		bean.setPaymentId(paymentId);
		bean.setSocId(new BigDecimal(new String(data, 59, 6, "US-ASCII").trim()));
		bean.setSocLine(amexSocLine);
		bean.setSubmitDate(getJulianDate(new String(data, 45, 4, "US-ASCII").trim(),
				new String(data, 49, 3, "US-ASCII").trim()));
		bean.setProcessDate(getJulianDate(new String(data, 52, 4, "US-ASCII").
				trim(),
				new String(data, 56, 3, "US-ASCII").
				trim()));
		bean.setSocAmount(decodeAmount(data, 65, 13));
		bean.setRocAmount(decodeAmount(data, 78, 13));
		bean.setCreditCardNumber(new String(data, 91, 15, "US-ASCII").trim());
		bean.setPoNumber(new String(data, 106, 11, "US-ASCII").trim());
		bean.setEntryRef(new BigDecimal(new String(data, 117, 19, "US-ASCII").
				trim()));
		return bean;
	}

	private AmexChargebackBean getAmexChargebackBean(byte[] data) throws
	NumberFormatException, UnsupportedEncodingException {
		AmexChargebackBean bean = new AmexChargebackBean();
		this.populateCommonAttributes(data, bean);
		bean.setPaymentId(paymentId);
		bean.setSubmitDate(getJulianDate(new String(data, 45, 4, "US_ASCII").trim(),
				new String(data, 49, 3, "US_ASCII").trim()));
		bean.setProcessDate(getJulianDate(new String(data, 52, 4, "US_ASCII").
				trim(),
				new String(data, 56, 3, "US_ASCII").
				trim()));
		bean.setSocId(new BigDecimal(new String(data, 59, 6, "US_ASCII").trim()));
		bean.setSocLine(amexSocLine);
		bean.setSocAmount(decodeAmount(data, 65, 11));
		bean.setChargebackAmount(decodeAmount(data, 76, 9));
		bean.setDiscountAmount(decodeAmount(data, 85, 9));
		bean.setServiceFeeAmount(decodeAmount(data, 94, 7));
		bean.setNetChargebackAmount(decodeAmount(data, 108, 9));
		bean.setDiscountRate(new BigDecimal(new BigInteger(new String(data, 117,
				5,
		"US_ASCII").trim()), 5)); // 5 decimal places
		bean.setServiceFeeRate(new BigDecimal(new BigInteger(new String(data, 122,
				5, "US_ASCII").trim()), 5)); // 5 decimal places
		bean.setChargebackReason(new String(data, 143, 280, "US_ASCII").trim());

		return bean;
	}

	private AmexAdjustmentBean getAmexAdjustmentBean(byte[] data) throws
	NumberFormatException, UnsupportedEncodingException {
		AmexAdjustmentBean bean = new AmexAdjustmentBean();
		this.populateCommonAttributes(data, bean);
		bean.setAdjustmentDate(getJulianDate(new String(data, 45, 4, "US-ASCII").
				trim(),
				new String(data, 49, 3, "US-ASCII").
				trim()));
		bean.setAdjustmentId(new BigDecimal(new String(data, 52, 6, "US-ASCII").
				trim()));
		bean.setAdjustmentAmount(decodeAmount(data, 58, 9));
		bean.setDiscountAmount(decodeAmount(data, 67, 9));
		bean.setServiceFeeAmount(decodeAmount(data, 76, 7));
		//netAdjustmentAmount = decodeAmount(data, 90, 9);
		bean.setDiscountRate(new BigDecimal(new BigInteger(new String(data, 99, 5,
		"US-ASCII").trim()), 5)); // 5 decimal places
		bean.setServiceFeeRate(new BigDecimal(new BigInteger(new String(data, 104,
				5, "US-ASCII").trim()), 5)); // 5 decimal places
		//ccNumber = new String(data,125, 17, "US-ASCII").trim();
		bean.setAdjustmentReason(new String(data, 142, 280, "US-ASCII").trim());
		bean.setPaymentDate(paymentDate);
		bean.setPaymentId(paymentId);
		return bean;
	}

	private AmexOtherFeesBean getAmexOtherFeesBean(byte[] data) throws
	NumberFormatException, UnsupportedEncodingException {
		AmexOtherFeesBean bean = new AmexOtherFeesBean();
		this.populateCommonAttributes(data, bean);
		bean.setPaymentId(paymentId);
		bean.setPaymentDate(paymentDate);
		bean.setProcessDate(getJulianDate(new String(data, 45, 4, "US-ASCII").
				trim(),
				new String(data, 49, 3, "US-ASCII").
				trim()));
		bean.setAssetBillingAmount(decodeAmount(data, 52, 9));
		bean.setAssetBillingDescription(new String(data, 61, 65, "US-ASCII").trim());
		bean.setCommissionAmount(decodeAmount(data, 126, 9));
		bean.setCommissionDescription(new String(data, 135, 80, "US-ASCII").trim());
		bean.setOtherFeeAmount(decodeAmount(data, 215, 9));
		bean.setOtherFeeDescription(new String(data, 224, 80, "US-ASCII").trim());
		bean.setFeeId(decodeAmount(data, 304, 9));

		return bean;
	}

	private void populateCommonAttributes(byte[] data, AbstractAmexRecord bean) throws
	NumberFormatException, UnsupportedEncodingException {
		String recordName = new String(data, 0, 5, "US-ASCII");
		if (!"DFHDR".equals(recordName) && !"DFTRL".equals(recordName)) {
			bean.setPayeeId(new BigDecimal(new String(data, 0, 10, "US-ASCII")));
			bean.setSubmitter(new BigDecimal(new String(data, 10, 10, "US-ASCII")));
			bean.setUnit(new String(data, 20, 10, "US-ASCII"));
			bean.setPaymentId(new String(data, 34, 8, "US-ASCII"));
			bean.setPaymentDate(getJulianDate(new String(data, 30, 4, "US-ASCII"),
					new String(data, 34, 3, "US-ASCII")));
		}

	}

	private BigDecimal decodeAmount(byte[] data, int start, int len) throws
	NumberFormatException, UnsupportedEncodingException {
		return decodeAmount(data, start, len, true);
	}

	private BigDecimal decodeAmount(byte[] data, int start, int len,
			boolean currency) throws
			UnsupportedEncodingException {
		int sign = 1;
		int index = start + len - 1;
		switch (data[index]) {
			case '{':
				sign = 1;
				data[index] = '0';
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
				sign = 1;
				data[index] = (byte) ( data[index] - 'A' + '1');
				break;
			case '}':
				sign = -1;
				data[index] = '0';
				break;
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
				sign = -1;
				data[index] = (byte) ( data[index] - 'J' + '1');
				break;
		}
		BigInteger rawamt = new BigInteger(new String(data, start, len,
		"US-ASCII"));
		int scale = currency ? 2 : 0;
		BigDecimal amt = new BigDecimal(rawamt, scale);
		return sign > 0 ? amt : amt.negate();
	}

	private Date getJulianDate(String year, String julianDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.DAY_OF_YEAR, Integer.parseInt(julianDay));
		return calendar.getTime();
	}

	protected boolean lockIt() {
		synchronized (AmexUpdateProcess.class) {
			if (!locked) {
				return locked = true;
			}
			return false;
		}
	}

	protected boolean unlockIt() {
		synchronized (AmexUpdateProcess.class) {
			if (locked) {
				return! (locked = false);
			}
			return false;
		}
	}

}