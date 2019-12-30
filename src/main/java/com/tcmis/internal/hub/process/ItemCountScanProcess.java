package com.tcmis.internal.hub.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.CountScanLoadBean;
import com.tcmis.internal.hub.beans.InventoryCountScanInputBean;
import com.tcmis.internal.hub.factory.CountScanLoadBeanFactory;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

public class ItemCountScanProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public ItemCountScanProcess(String client) {
	super(client);
 }

 public String doUpload(InventoryCountScanInputBean bean,
	BigDecimal personnelId) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	CountScanLoadBeanFactory factory = new
	 CountScanLoadBeanFactory(dbManager);
	Collection countScanLoadBeanCollection = new Vector();
	String personnelIdMessage = "";

	if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) {
	 //copy file to server
	 File f = bean.getTheFile();
	 String fileType = f.getName().substring(f.getName().lastIndexOf("."));
	 bean.setFileName(fileType);
	 ResourceLibrary resource = new ResourceLibrary("scannerupload");
	 BigDecimal upLoadId = factory.getUploadId();

	 try {
		File dir = new File(resource.getString("upload.serverpath"));
		File file = File.createTempFile("itemcount-",
		 "" + fileType + "", dir);

		FileHandler.copy(bean.getTheFile(), file);
		bean.setFileName(file.getName());

		// reading the file and putting the values in a bean
		BufferedReader in = new BufferedReader(new FileReader(file));
		String ln = new String();
		StringTokenizer st = null;
		while ( (ln = in.readLine()) != null) {
		 st = new StringTokenizer(ln, ",");

		 if (st.countTokens() > 1) {
			int loopcount = 0;
			CountScanLoadBean countScanLoadBean = new
			 CountScanLoadBean();
			countScanLoadBean.setLoadId(upLoadId);

			while (st.hasMoreTokens()) {
			 loopcount++;
			 String tok = st.nextToken();
			 String tokenValue = "";

			 tokenValue = tok == null ? "" : tok.trim();
			 if (loopcount == 1) {
				if (tokenValue == null || tokenValue.length() == 0) {
				 countScanLoadBean.setPersonnelId(personnelId);
				 personnelIdMessage = "Did not get a personnel ID for who did the scan upon upload. Using the personnel Id of the person logged into to tcmIS as the scanner ID.";
				}
				else {
				 BigDecimal counterId = com.tcmis.common.util.NumberHandler.
					zeroBigDecimalIfNull(tokenValue);
				 countScanLoadBean.setPersonnelId(counterId);
				}
			 }
			 else if (loopcount == 2) {
				countScanLoadBean.setTypeItemOrPn(tokenValue);
			 }
			 else if (loopcount == 3) {
				countScanLoadBean.setLoadInventoryGroup(tokenValue);
			 }
			 else if (loopcount == 4) {
				BigDecimal uomCountedQuantity = com.tcmis.common.util.NumberHandler.
				 zeroBigDecimalIfNull(tokenValue);
				countScanLoadBean.setUomCountedQuantity(uomCountedQuantity);
			 }
			 else if (loopcount == 5) {
				countScanLoadBean.setScanDate(com.tcmis.common.util.
				 DateHandler.getDateFromString("yyyy/MM/dd HH:mm:ss", tokenValue));
			 }
			 else if (loopcount == 6) {
				countScanLoadBean.setDateCounted(com.tcmis.common.util.
				 DateHandler.getDateFromString("yyyy/MM/dd HH:mm:ss", tokenValue));
			 }
			}
			countScanLoadBeanCollection.add(countScanLoadBean);
		 }
		}
		in.close();

		//file.deleteOnExit();
	 }
	 catch (Exception e) {
		BaseException be = new BaseException("Can't put document file on server:" +
		 e.getMessage());
		be.setRootCause(e);
		e.printStackTrace();
		throw be;
	 }

	 //insert into data table
	 String errorMessage = "";
	 Iterator itemCountScanIterator = countScanLoadBeanCollection.
		iterator();
	 while (itemCountScanIterator.hasNext()) {
		CountScanLoadBean countScanLoadBean = (
		 CountScanLoadBean) itemCountScanIterator.next();

		try {
		 factory.insert(countScanLoadBean);
		}
		catch (BaseException ex) {
		 ex.printStackTrace();
		 errorMessage += ex.getMessage();
		}
	 }

	 Collection processResult = null;
	 BigDecimal errorCount = new BigDecimal("0");
	 //calling the procedure to process the scans
	 try {
		processResult = factory.processScan(upLoadId);
		Iterator processResultIterator = processResult.iterator();

		int processResultCount = 0;
		String closePoLineerrorCode = "";
		while (processResultIterator.hasNext()) {
		 if (processResultCount == 0) {
			BigDecimal errorCountRerulst = (BigDecimal) processResultIterator.next();
			//log.info("errorCountRerulst   "+errorCountRerulst+"");
			if (errorCountRerulst == null || errorCountRerulst.intValue() == 0) {
			 errorCount = new BigDecimal("0");
			}
			else
			{
			 errorCount = new BigDecimal("1");
			}
		 }
		 processResultCount++;
		}

	 }
	 catch (Exception ex1) {
		ex1.printStackTrace();
		errorMessage += ex1.getMessage();
	 }

	 //sending out emails to let the user know the results
	 BulkMailProcess bulkMailProcess = new
		BulkMailProcess(this.getClient());

   if (errorCount.intValue() == 0)
	 {
		//String wwwHome = resource.getString("upload.hosturl");
		//String resultUrl = wwwHome + resource.getString("upload.inventorycountresult");
		String message = personnelIdMessage + "\n\nThe item count scan upload has been processed " +
		 "successfully.\n\n\n";
		bulkMailProcess.sendBulkEmail(personnelId, "Item Count Scanner Upload Results",
		 message, true);
		bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
		 "Item Count Scanner Upload Results", message, true);
	 }
	 else
	 {
		String message = personnelIdMessage + "\n\nThere was an error prcessing the item count scan upload." +
		 "\n\nThe tech center has been notified.";
		bulkMailProcess.sendBulkEmail(personnelId, "Error processing Item Count Scanner Upload Results",
		 message, true);
		bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
		 "Error processing Scanner Upload Results", message, true);
	 }

	 return errorMessage;
	}
	else {
	 return "No File";
	}
 }
}