/******************************************************
 * XmlOrderAction.java
 * 
 * Handle incoming EBP (xCBL) order from FEC via
 * Pantellos
 ******************************************************
 */
package com.tcmis.client.fec.action;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.tcmis.client.fec.process.EBPOrderProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlReaderAction;

public class XmlOrderAction extends XmlReaderAction {

	@Override
	public void processCall(String dbUser, String xmlString) throws BaseException {
		String orderString="";
		String saveDir = "/ftpdata/ftp-files/fec/ebp/";
		Date now = new Date();
		long savedNow = now.getTime();
		String feedFileName = saveDir + "perfect" + savedNow + ".xml";
		String xmlFileName = saveDir + "ebpxml" + savedNow + ".xml";

		/*
      log.debug("pre - processing order: " + feedFileName);
      // save to local dir
      try {
         FileOutputStream xmlFOS = new FileOutputStream(feedFileName);
         xmlFOS.write( xmlString.getBytes() );
         xmlFOS.close();
         log.debug("ebp xml feed saved to: " + feedFileName);
      } catch (FileNotFoundException fnfe) {
         log.error("unable to create file: " + feedFileName + " : " + fnfe);
      } catch (IOException ioe) {
         log.error("unable to write to file: " + feedFileName + " : " + ioe);
      }

      // read in data to stage table
      EBPOrderProcess ebpOrderProcess = new EBPOrderProcess(dbUser);
      orderString = ebpOrderProcess.preProcess(feedFileName);
      log.debug("got order: " + orderString);

      try {
         FileOutputStream xmlFOS = new FileOutputStream(xmlFileName);
         xmlFOS.write( orderString.getBytes() );
         xmlFOS.close();
         log.debug("ebp xml feed saved to: " + xmlFileName);
      } catch (FileNotFoundException fnfe) {
         log.error("unable to create file: " + xmlFileName + " : " + fnfe);
      } catch (IOException ioe) {
         log.error("unable to write to file: " + xmlFileName + " : " + ioe);
      }

      ebpOrderProcess.process(xmlFileName);
		 */

		try {
			FileOutputStream xmlFOS = new FileOutputStream(xmlFileName);
			xmlFOS.write( xmlString.getBytes() );
			xmlFOS.close();
			if (log.isDebugEnabled()) {
				log.debug("ebp xml feed saved to: " + xmlFileName);
			}
		} catch (FileNotFoundException fnfe) {
			log.error("unable to create file: " + xmlFileName + " : " + fnfe);
		} catch (IOException ioe) {
			log.error("unable to write to file: " + xmlFileName + " : " + ioe);
		}

		EBPOrderProcess ebpOrderProcess = new EBPOrderProcess(dbUser);
		ebpOrderProcess.process(xmlFileName);
		ebpOrderProcess.databaseUpdate();

		// send an email notification back to FEC
		if (! ebpOrderProcess.sendReceivedNotificationEmail() ) {
			log.error("EBP Notification email not sent! OrdNum: " + ebpOrderProcess.getOrderNumber());
		}
	}

}
