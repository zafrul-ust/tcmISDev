package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.jfree.util.Log;

import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.CountScanLoadBean;
import com.tcmis.internal.hub.factory.CountScanLoadBeanFactory;
import com.tcmis.ws.scanner.beans.CountScanLoadList;
import com.tcmis.ws.scanner.beans.ScanDataBean;
import com.tcmis.internal.hub.process.*;
import com.tcmis.ws.scanner.beans.ScanDataList;


public class HubItemCountWorkerProcess implements Runnable {
	CountScanLoadList beans;
	BigDecimal personnelId;
	BigDecimal nextupLoadSeq;
	String client = null;
	CountScanLoadBeanFactory factory = null;
	public HubItemCountWorkerProcess(CountScanLoadList beans, CountScanLoadBeanFactory factory,BigDecimal nextupLoadSeq,String client){
		this.beans = beans;
		this.personnelId = beans.getPersonnelId();
		this.nextupLoadSeq = nextupLoadSeq;
		this.client = client;
		this.factory = factory;
	}
	public void run() {
		// TODO Auto-generated method stub
		String errorMessage = "";
		Collection processResult = null;
		BigDecimal errorCount = new BigDecimal("0");
		try {
			Thread.currentThread().sleep(100);
			//calling the procedure to process the scans
//			 Iterator itemCountScanIterator = countScanLoadBeanCollection.
//				iterator();
			 for (CountScanLoadBean countScanLoadBean:beans.getScanData()) {
//				CountScanLoadBean countScanLoadBean = (
//				 CountScanLoadBean) itemCountScanIterator.next();

				try {
				 countScanLoadBean.setLoadId(nextupLoadSeq);
				 factory.insert(countScanLoadBean);
				}
				catch (BaseException ex) {
				 ex.printStackTrace();
				 errorMessage += ex.getMessage();
				}
			 }
			
			processResult = factory.processScan(nextupLoadSeq);
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
		BulkMailProcess bulkMailProcess = new
		BulkMailProcess(client);

		try {
			if (errorCount.intValue() == 0)
			{
				//String wwwHome = resource.getString("upload.hosturl");
				//String resultUrl = wwwHome + resource.getString("upload.inventorycountresult");
				errorMessage += "\n\nThe item count scan upload has been processed " +
				"successfully.\n\n\nUpload Sequence: "+ nextupLoadSeq;
				bulkMailProcess.sendBulkEmail(personnelId, "Item Count Scanner Upload Results",
						errorMessage, true);
				bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
						"Item Count Scanner Upload Results", errorMessage, true);
			}
			else
			{
				errorMessage = "\n\nThere was an error prcessing the item count scan upload." +
				"\n\nThe tech center has been notified.\n\nUpload Sequence: "+ nextupLoadSeq;
				bulkMailProcess.sendBulkEmail(personnelId, "Error processing Item Count Scanner Upload Results",
						errorMessage, true);
				bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
						"Error processing Hub Scanner Upload Results", errorMessage, true);
			}
			Log.debug(errorMessage);
		}
		catch(Exception ex){};

	}

}
