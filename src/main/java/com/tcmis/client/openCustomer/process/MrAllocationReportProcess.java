package com.tcmis.client.openCustomer.process;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.openCustomer.factory.PkgWebPrOrderTrackDetailBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class MrAllocationReportProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;

	public MrAllocationReportProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getMrAllocationReport(String mrNumber, String lineItem) throws BaseException,Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		PkgWebPrOrderTrackDetailBeanFactory factory = new PkgWebPrOrderTrackDetailBeanFactory(dbManager);

		Collection pkgOrderTrackWebPrOrderTrackDetailBeanCollection = factory.select(mrNumber,lineItem,"Y");

		//log.debug("Final collectionSize here " +finalpkgOrderTrackWebPrOrderTrackDetailBeanCollection.size() + "");
		return pkgOrderTrackWebPrOrderTrackDetailBeanCollection;
	}

	public Collection getMsdsColl(String refType, String refNumber,String prNumber,String lineItem) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ReceiptDocumentViewBean());
		String query = "select * from table (PKG_OPEN_CUSTOMER.FX_REF_MSDS('" + StringHandler.emptyIfNull(refType) + "','" +
		StringHandler.emptyIfNull(refNumber) + "',"+ StringHandler.emptyIfNull(prNumber)+",'"+StringHandler.emptyIfNull(lineItem)+"'))";
		return factory.selectQuery(query);
	}

	public Collection getCertsDocs(BigDecimal receiptId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ReceiptDocumentViewBean());

		String query="select * from table (PKG_OPEN_CUSTOMER.FX_RECEIPT_DOCUMENT("+receiptId+"))";
		return factory.selectQuery(query);
	}

	public void sendRequest(String csrEmail, PersonnelBean personnelBean, String itemId, String locale) {
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

		try {
			StringBuilder subjectMsg = new StringBuilder(library.getString("label.requestmsds"));
			subjectMsg.append(" (").append(library.getString("label.itemid")).append(":");
			subjectMsg.append(itemId).append(")");
			subjectMsg.append(" (").append(library.getString("label.language")).append(":");
			subjectMsg.append(locale).append(")");
			StringBuilder msg = new StringBuilder("Request MSDS information.");
			MailProcess.sendEmail("msds.queue@haasgroupintl.com",csrEmail,personnelBean.getEmail(),subjectMsg.toString(),msg.toString());
		}catch (Exception e) {
			e.printStackTrace();
			StringBuilder tmp = new StringBuilder("Send request email failed (MR: ");
			tmp.append(itemId);
			tmp.append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,personnelBean.getEmail(),tmp.toString(),"Unable to send email to CSR");
		}
	}



}