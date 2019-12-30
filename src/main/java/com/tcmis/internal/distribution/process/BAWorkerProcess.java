package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.CountScanLoadBean;
import com.tcmis.internal.hub.factory.CountScanLoadBeanFactory;
import com.tcmis.ws.scanner.beans.CountScanLoadList;
import com.tcmis.ws.scanner.beans.ScanDataBean;
import com.tcmis.internal.hub.process.*;
import com.tcmis.ws.scanner.beans.ScanDataList;
import org.apache.commons.logging.Log;

public class BAWorkerProcess implements Runnable {
	DbManager dbManager = null;
	Log log = null;
	
	public BAWorkerProcess(DbManager dbManager, org.apache.commons.logging.Log log) {
		this.dbManager = dbManager;
		this.log = log;
	}
	public void run() {
		// TODO Auto-generated method stub
		String errorMessage = "";
		Collection processResult = null;
		BigDecimal errorCount = new BigDecimal("0");
		try {
			Thread.currentThread().sleep(100);
		      GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		      Vector params = new Vector();
		      try {
		         procFactory.doProcedure("P_PROCESS_BA_ORDERS",params);
		      } catch (BaseException bep) {
		         log.error("Base Exception calling P_PROCESS_BA_ORDERS: " + bep);
		      }
		}
		catch (Exception ex1) {
			ex1.printStackTrace();
			errorMessage += ex1.getMessage();
		}
//		BulkMailProcess bulkMailProcess = new
//		BulkMailProcess(client);
//
//		try {
//			if (errorCount.intValue() == 0)
//			{
//				//String wwwHome = resource.getString("upload.hosturl");
//				//String resultUrl = wwwHome + resource.getString("upload.inventorycountresult");
//				errorMessage += "\n\nThe item count scan upload has been processed " +
//				"successfully.\n\n\nUpload Sequence: "+ nextupLoadSeq;
//				bulkMailProcess.sendBulkEmail(personnelId, "Item Count Scanner Upload Results",
//						errorMessage, true);
//				bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
//						"Item Count Scanner Upload Results", errorMessage, true);
//			}
//			else
//			{
//				errorMessage = "\n\nThere was an error prcessing the item count scan upload." +
//				"\n\nThe tech center has been notified.\n\nUpload Sequence: "+ nextupLoadSeq;
//				bulkMailProcess.sendBulkEmail(personnelId, "Error processing Item Count Scanner Upload Results",
//						errorMessage, true);
//				bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
//						"Error processing Hub Scanner Upload Results", errorMessage, true);
//			}
//			Log.debug(errorMessage);
//		}
//		catch(Exception ex){};

	}

}
