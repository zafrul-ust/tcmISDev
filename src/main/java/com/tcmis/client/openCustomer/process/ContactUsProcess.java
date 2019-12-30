package com.tcmis.client.openCustomer.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for ContactUsProcess
 * @version 1.0
 *****************************************************************************/
public class ContactUsProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;

	public ContactUsProcess(String client) {
		super(client);
	}

	public ContactUsProcess(String client, String locale) {
		super(client, locale);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public void sendRequest(PersonnelBean personnelBean, String mrNumber, String customerServiceRepEmail, String subject, String msg) {
		try {
			StringBuilder subjectMsg = null;
			if("requestConfirmation".equals(subject))
				subjectMsg = new StringBuilder(library.getString("label.requestconfirmation"));
			else if("expediteOrder".equals(subject))
				subjectMsg = new StringBuilder(library.getString("label.expediteorder"));
			else if("other".equals(subject))
				subjectMsg = new StringBuilder(library.getString("label.other"));
			subjectMsg.append("(").append(library.getString("label.mr"));
			subjectMsg.append(mrNumber).append(")");
			String tmpEmailAddress = customerServiceRepEmail;
			if (StringHandler.isBlankString(customerServiceRepEmail)) {
				tmpEmailAddress = "deverror@tcmis.com";
			}
			MailProcess.sendEmail(tmpEmailAddress,personnelBean.getEmail(),personnelBean.getEmail(),subject.toString(),msg);
		}catch (Exception e) {
			e.printStackTrace();
			StringBuilder tmp = new StringBuilder("Send request email failed (MR: ");
			tmp.append(mrNumber);
			tmp.append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,personnelBean.getEmail(),tmp.toString(),"Unable to send email to CSR");
		}
	}

} //end of class