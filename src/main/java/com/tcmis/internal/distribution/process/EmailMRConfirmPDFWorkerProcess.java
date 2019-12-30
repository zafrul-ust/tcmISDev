package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.NetHandler;

public class EmailMRConfirmPDFWorkerProcess implements Runnable {
	public static Log	log			= LogFactory.getLog(EmailMRConfirmPDFWorkerProcess.class);

	String emailAddr;
	String subject;
	String msg;
	String attachmentName;
	String url;
	String csrEmail;
	public EmailMRConfirmPDFWorkerProcess(String emailAddr,String subject,String msg,String attachmentName,String url,String csrEmail){
		this.emailAddr=emailAddr;
		this.subject=subject;
		this.msg=msg;
		this.attachmentName=attachmentName;
		this.url=url;
		this.csrEmail = csrEmail;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.currentThread().sleep(100);
			log.debug("Retrieving " + url);
			String attachmentPath = NetHandler.getHttpPostResponse(url, null, null, "");
			log.debug("emailAddr:"+emailAddr+"Confirm MR Attachment File Path:"+attachmentPath);
			MailProcess.sendEmail(emailAddr,"",csrEmail,//"tcmis@tcmis.com",
					subject,
					msg,
					attachmentName,
					attachmentPath);
		}catch(Exception ex){
			log.error("Error sending email", ex);
		};
	}
}
