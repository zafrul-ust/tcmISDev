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
import com.tcmis.internal.hub.beans.InventoryCountScanInputBean;
import com.tcmis.internal.hub.beans.InventoryCountScanStageBean;
import com.tcmis.internal.hub.factory.InventoryCountScanStageBeanFactory;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

public class InventoryCountProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public InventoryCountProcess(String client) {
	super(client);
 }

 public String doUpload(InventoryCountScanInputBean bean,
	BigDecimal personnelId) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	InventoryCountScanStageBeanFactory factory = new
	 InventoryCountScanStageBeanFactory(dbManager);
	Collection inventoryCountScanStageBeanCollection = new Vector();
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
		File file = File.createTempFile("inventorycount-",
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
			InventoryCountScanStageBean inventoryCountScanStageBean = new
			 InventoryCountScanStageBean();
			inventoryCountScanStageBean.setUploadId(upLoadId);

			while (st.hasMoreTokens()) {
			 loopcount++;
			 String tok = st.nextToken();
			 String tokenValue = "";

			 tokenValue = tok == null ? "" : tok.trim();
			 if (loopcount == 1) {
				if (tokenValue == null || tokenValue.length() == 0) {
				 inventoryCountScanStageBean.setCounterId(personnelId);
				 personnelIdMessage = "Did not get a personnel ID for who did the scan upon upload. Using the personnel Id of the person logged into to tcmIS as the scanner ID.";
				}
				else {
				 BigDecimal counterId = com.tcmis.common.util.NumberHandler.
					zeroBigDecimalIfNull(tokenValue);
				 inventoryCountScanStageBean.setCounterId(counterId);
				}

				//DataH.put("PERSONNEL_ID", tok == null ? "" : tok.trim());
			 }
			 else if (loopcount == 2) {
				inventoryCountScanStageBean.setHub(tokenValue);
				//DataH.put("HUB", tok == null ? "" : tok.trim());
			 }
			 else if (loopcount == 3) {
				inventoryCountScanStageBean.setScannedBin(tokenValue);
				//DataH.put("BIN", tok == null ? "" : tok.trim());
			 }
			 else if (loopcount == 4) {
				BigDecimal receiptId = com.tcmis.common.util.NumberHandler.
				 zeroBigDecimalIfNull(tokenValue);
				inventoryCountScanStageBean.setReceiptId(receiptId);
				//DataH.put("RECEIPT_ID", tok == null ? "" : tok.trim());
			 }
			 else if (loopcount == 5) {
				BigDecimal quantity = com.tcmis.common.util.NumberHandler.
				 zeroBigDecimalIfNull(tokenValue);
				inventoryCountScanStageBean.setCountedQuantity(quantity);
				//DataH.put("QTY", tok == null ? "" : tok.trim());
			 }
			 else if (loopcount == 6) {
				inventoryCountScanStageBean.setScanDatetime(com.tcmis.common.util.
				 DateHandler.getDateFromString("yyyy/MM/dd HH:mm", tokenValue));
				//DataH.put("TIMESTAMP", tok == null ? "" : tok.trim());
			 }
			}
			inventoryCountScanStageBeanCollection.add(inventoryCountScanStageBean);
		 }
		}
		in.close();

		file.deleteOnExit();
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
	 Iterator inventoryCountScanIterator = inventoryCountScanStageBeanCollection.
		iterator();
	 while (inventoryCountScanIterator.hasNext()) {
		InventoryCountScanStageBean inventoryCountScanStageBean = (
		 InventoryCountScanStageBean) inventoryCountScanIterator.next();

		try {
		 factory.insert(inventoryCountScanStageBean);
		}
		catch (BaseException ex) {
		 errorMessage += ex.getMessage();
		}
	 }

	 //calling the procedure to consolidate the scans
	 try {
		factory.consolidateInventoryScan(upLoadId);
	 }
	 catch (Exception ex1) {
		errorMessage += ex1.getMessage();
	 }

	 //sending out emails to let the user know the results
	 BulkMailProcess bulkMailProcess = new
		BulkMailProcess(this.getClient());

	 String wwwHome = resource.getString("upload.hosturl");
	 String resultUrl = wwwHome +
		resource.getString("upload.inventorycountresult");
	 String message = personnelIdMessage + "\n\nThe upload has been processed " +
		"successfully, please go to the URL below to look at the results.\n\n\n" +
		resultUrl + "UserAction=Search&uploadId=" + upLoadId +
		"\n\n\nPersonnel ID : " + personnelId;

	 bulkMailProcess.sendBulkEmail(personnelId, "Hub Scanner Upload Results",
		message, true);
	 bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
		"Hub Scanner Upload Results", message, true);
	 /*bulkMailProcess.sendBulkEmail(new BigDecimal("220"),
		"Hub Scanner Upload Results", message, true);*/

	 return errorMessage;
	}
	else {
	 return "No File";
	}
 }
}