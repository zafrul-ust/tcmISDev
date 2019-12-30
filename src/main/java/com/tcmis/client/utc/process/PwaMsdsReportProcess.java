package com.tcmis.client.utc.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.utc.beans.PwaMsdsViewBean;
import com.tcmis.client.utc.factory.PwaMsdsViewBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process to create MSDS data for UTC.
 * @version 1.0
 *****************************************************************************/
public class PwaMsdsReportProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PwaMsdsReportProcess(String client) {
		super(client);
	}

	public void load() throws Exception {
		ResourceLibrary specLibrary = new ResourceLibrary("PWAFeedData");
		//String reportFileNameSub = specLibrary.getString("msds.filename.sub");
		//String reportFileNameNoSub = specLibrary.getString("msds.filename.nosub");
		String reportFileNamePwaMsdsReport = specLibrary.getString("msds.filename.pwamsdsreport");
		String localFilePath = specLibrary.getString("msds.local.dir");
		String remoteFilePath = specLibrary.getString("msds.remote.dir");
		String fromFileName = "";
		String toFileName = "";

		//first remove everything from directory
		removeOldFiles(localFilePath);
		//create new report files and copy msds images
		//PrintWriter pwSub = new PrintWriter(new FileOutputStream(localFilePath+reportFileNameSub));
		//PrintWriter pwNonSub = new PrintWriter(new FileOutputStream(localFilePath+reportFileNameNoSub));
		//PWA MSDS revision is different than Haas MSDS Revision
		PrintWriter pwaMsdsReport = new PrintWriter(new FileOutputStream(localFilePath+reportFileNamePwaMsdsReport));
		pwaMsdsReport.println("MATERIAL_ID,SAP_SUBSTANCE,MANUFACTURER,IMAGE_FILE,HAAS_REVISION_DATE,PWA_REVISION_DATE");

		try {
			//get data from view
			DbManager dbManager = new DbManager(getClient());
			PwaMsdsViewBeanFactory factory = new PwaMsdsViewBeanFactory(dbManager);
			//first synch data before running report
			log.debug("PWA MSDS Report processing data - utc/msdsreport: Running PKG_PWA_FEED.p_load_cpig to synch data.");
			factory.updateCpigForPwa();
			//get data
			Collection c = factory.select(null);
			Iterator dataIter = c.iterator();
			String errorMessage = "";
			if (log.isDebugEnabled()) {
				log.debug("PWA MSDS Report processing data - utc/msdsreport size of data: "+c.size());
			}
			boolean dataWithDifferentRev = false;
			while (dataIter.hasNext()) {
				PwaMsdsViewBean pwaMsdsViewBean = (PwaMsdsViewBean) dataIter.next();
				String catalogComponentId = StringHandler.emptyIfNull(pwaMsdsViewBean.getCatalogComponentId());
				String msdsFileName = StringHandler.emptyIfNull(pwaMsdsViewBean.getUrlIn());
				String outputFileName = StringHandler.emptyIfNull(pwaMsdsViewBean.getOutputFilename());
				//pwaMsdsViewBean.getDifferentRev() - this method returns true if revision_date (haas) is
				//different than pwa_revision_date.  False - if both are the same
				if ("TRUE".equalsIgnoreCase(pwaMsdsViewBean.getDifferentRev())) {
					/*
          if (catalogComponentId.length() > 0) {
            //substance
            pwSub.print(catalogComponentId + ",");
            pwSub.print("\"" + StringHandler.emptyIfNull(pwaMsdsViewBean.getMfgName()) + "\",");
            pwSub.print(outputFileName + ",");
            pwSub.print(DateHandler.formatDate(pwaMsdsViewBean.getRevisionDate(), "MM/dd/yyyy"));
            pwSub.println("");
            if (msdsFileName.length() > 1 && outputFileName.length() > 1) {
              errorMessage = copyMsdsImage(remoteFilePath + msdsFileName, localFilePath + outputFileName, errorMessage);
            }
          } else {
            //no substance
            pwNonSub.print("\"" + StringHandler.emptyIfNull(pwaMsdsViewBean.getMfgName()) + "\",");
            pwNonSub.print(outputFileName + ",");
            pwNonSub.print(DateHandler.formatDate(pwaMsdsViewBean.getRevisionDate(), "MM/dd/yyyy"));
            pwNonSub.println("");
            if (msdsFileName.length() > 1 && outputFileName.length() > 1) {
              errorMessage = copyMsdsImage(remoteFilePath + msdsFileName, localFilePath + outputFileName, errorMessage);
            }
          }
					 */
					dataWithDifferentRev = true;
					pwaMsdsReport.print(StringHandler.emptyIfNull(pwaMsdsViewBean.getCatalogComponentItemId())+",");
					pwaMsdsReport.print(catalogComponentId+",");
					pwaMsdsReport.print("\""+StringHandler.emptyIfNull(pwaMsdsViewBean.getMfgName())+"\",");
					pwaMsdsReport.print(StringHandler.emptyIfNull(pwaMsdsViewBean.getContent())+",");
					pwaMsdsReport.print(StringHandler.emptyIfNull(DateHandler.formatDate(pwaMsdsViewBean.getRevisionDate(),"MM/dd/yyyy")+","));
					pwaMsdsReport.print(StringHandler.emptyIfNull(DateHandler.formatDate(pwaMsdsViewBean.getPwaRevisionDate(),"MM/dd/yyyy")));
					pwaMsdsReport.println("");
				} //end of msds with different revision date
			} //end of loop
			//close file
			//pwSub.close();
			//pwNonSub.close();
			pwaMsdsReport.close();
			if (errorMessage.length() > 1) {
				sendEmailToAdmin(errorMessage);
			}
			//also send email to PwaMsdsReport if data contain parts with different revision date
			if (dataWithDifferentRev) {
				String[] attachmentPath = {localFilePath+reportFileNamePwaMsdsReport};
				String[] attachmentName = {"MSDS_WITH_DIFFERENT_REVISION_DATE.CSV"};
				sendEmailToUser(attachmentPath,attachmentName);
			}

		} catch (Exception e) {
			log.error("Error PWA MSDS Report", e);
			throw e;
		}
	} //end of method

	void removeOldFiles(String localFilePath) {
		log.debug("PWA MSDS Report removing old files - utc/msdsreport.");
		try {
			File f2 = new File(localFilePath);
			String[] list = f2.list();
			for (int k = 0; k < list.length; k++) {
				File f = new File(localFilePath+list[k]);
				if (f.exists()) {
					f.delete();
				}
			}
		}catch (Exception e) {
			log.error("Error PWA MSDS Report - deleting msds images", e);
			MailProcess.sendEmail("deverror@tcmis.com", null,
					"deverror@tcmis.com",
					"Error PWA MSDS Report - deleting msds images",
			"See log file.");
		}
	} //end of method

	String copyMsdsImage(String fromFileName,String toFileName, String result) throws Exception {
		try {
			//check to see if file exist
			File f = new File(fromFileName);
			if (!f.exists()) {
				result += "MSDS image no file found: "+fromFileName+"\n";
				return result;
			}
			//copy file
			File source = new File(fromFileName);
			File destination = new File(toFileName);
			FileHandler.copy(source,destination);

			return result;
		}catch (Exception e) {
			log.error("Error PWA MSDS Report - copying msds images", e);
			throw e;
		}
	} //end of method

	void sendEmailToAdmin(String emailMessage) throws Exception {
		try {
			DbManager dbManager = new DbManager(getClient());
			PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
			Collection c = factory.selectUsersEmailByGroup("All","PwaMsdsReportError");
			String[] to = new String[c.size()];
			Iterator dataIter = c.iterator();
			int i = 0;
			boolean hasEmail = false;
			while (dataIter.hasNext()) {
				PersonnelBean personnelBean = (PersonnelBean) dataIter.next();
				if (personnelBean.getEmail() != null && personnelBean.getEmail().length() > 0) {
					to[i++] = personnelBean.getEmail();
					hasEmail = true;
				}
			}
			//if no one in group send email developers, otherwise send email to hub
			if (!hasEmail) {
				String[] toDev = {"deverror@tcmis.com"};
				MailProcess.sendEmail(toDev, new String[0], "PWA MSDS Report - no member for PwaMsdsReportError", emailMessage, false);
			}else {
				MailProcess.sendEmail(to, new String[0], "PWA MSDS Report", emailMessage, true);
			}
		}catch (Exception ex) {
			log.error("Error sending error mail for PWA MSDS Report (processing).", ex);
			throw ex;
		}
	} //end of method

	void sendEmailToUser(String[] attachmentPath, String[] attachmentName) throws Exception {
		try {
			DbManager dbManager = new DbManager(getClient());
			PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
			Collection c = factory.selectUsersEmailByGroup("All","PwaMsdsReport");
			String[] to = new String[c.size()];
			Iterator dataIter = c.iterator();
			int i = 0;
			boolean hasEmail = false;
			while (dataIter.hasNext()) {
				PersonnelBean personnelBean = (PersonnelBean) dataIter.next();
				if (personnelBean.getEmail() != null && personnelBean.getEmail().length() > 0) {
					to[i++] = personnelBean.getEmail();
					hasEmail = true;
				}
			}
			//if no one in group send email developers, otherwise send email to hub
			if (!hasEmail) {
				String[] toDev = {"deverror@tcmis.com"};
				MailProcess.sendEmail(toDev, new String[0], "PWA MSDS Report - no member for PwaMsdsReport","PWA MSDS Report - no member for PwaMsdsReport", false);
			}else {
				MailProcess.sendEmail(to,new String[0],MailProcess.DEFAULT_FROM_EMAIL,"MSDS report from Haas TCM","Please see attached file(s)",attachmentName,attachmentPath);
			}
		}catch (Exception ex) {
			log.error("Error sending error mail for PWA MSDS Report (processing).", ex);
			throw ex;
		}
	} //end of method

} //end of class
