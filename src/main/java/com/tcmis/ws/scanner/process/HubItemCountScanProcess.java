package com.tcmis.ws.scanner.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.CountScanLoadBean;
import com.tcmis.internal.hub.beans.InventoryCountScanInputBean;
import com.tcmis.internal.hub.factory.CountScanLoadBeanFactory;
import com.tcmis.ws.scanner.beans.CountScanLoadList;
import com.tcmis.ws.scanner.beans.ScanDataBean;
import com.tcmis.ws.scanner.beans.ScanDataList;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

public class HubItemCountScanProcess
 extends GenericProcess {
 Log log = LogFactory.getLog(this.getClass());

 public HubItemCountScanProcess(String client) {
	super(client);
 }

 public CountScanLoadList parseResultSubmit(File file) throws BaseException {
//	    Class[] bigDecimalClass = {
//	        BigDecimal.class};
//	    Class[] dateClass = {
//	        Date.class};
//	    ScanDataList orderBean = null;
	    CountScanLoadList beans = null;
//	    Vector<CountScanLoadBean> beans = null;
//	    CountScanLoadBean countScanLoadBean = new CountScanLoadBean();
	    
	    try {
	    	Digester digester = new Digester();
	        digester.addObjectCreate("ItemScanSubmit", CountScanLoadList.class);

	        digester.addCallMethod("ItemScanSubmit/PersonnelId", "setPersonnelIdStr", 0);
	        digester.addCallMethod("ItemScanSubmit/Token", "setToken", 0);
	        
	        digester.addObjectCreate("ItemScanSubmit/Scans/Scan", CountScanLoadBean.class);
	           digester.addSetNext("ItemScanSubmit/Scans/Scan", "addScanDataBean");

//	        digester.addObjectCreate("ItemScanSubmit", Vector.class);
////  This personnel is not used.	        
////	        digester.addCallMethod("ItemScanSubmit/PersonnelId", "setPersonnelIdStr", 0);
//	        digester.addObjectCreate("ItemScanSubmit/Scans/Scan", CountScanLoadBean.class);
//	             digester.addSetNext("ItemScanSubmit/Scans/Scan", "add");
	        digester.addCallMethod("ItemScanSubmit/Scans/Scan/POU", "setLoadInventoryGroup", 0);
 	        digester.addCallMethod("ItemScanSubmit/Scans/Scan/Item", "setTypeItemOrPn", 0);
	        digester.addCallMethod("ItemScanSubmit/Scans/Scan/CountQuantity", "setUomCountedQuantityStr", 0);
	        digester.addCallMethod("ItemScanSubmit/Scans/Scan/ItemScanDate", "setScanDateStr", 0);
	        digester.addCallMethod("ItemScanSubmit/Scans/Scan/InvGrpScanDate", "setDateCountedStr", 0);
	        digester.addCallMethod("ItemScanSubmit/Scans/Scan/PersonnelId", "setPersonnelIdStr", 0);
	        
	        digester.parse(file);
	        beans = (CountScanLoadList) digester.getRoot();
	        log.debug(beans);
	    }
	    catch (Exception e) {
	        System.out.println("Error:" + e.getMessage());
	      log.error("Error:" + e.getMessage());
	      e.printStackTrace(System.out);
	      BaseException be = new BaseException(e);
	      be.setMessageKey("");
	      be.setRootCause(e);
	      throw be;
	    }
	    return beans;
	  }

 public String doUpload(String xmlString) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	CountScanLoadBeanFactory factory = new
	 CountScanLoadBeanFactory(dbManager);
	Vector<CountScanLoadBean> countScanLoadBeanCollection = new Vector();
	BigDecimal personnelId = null;
	//String personnelIdMessage = "";
	String errorMessage = "File Parse Error.";
//	String status = "OK";
//	if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) 
	try 
	{
		 ResourceLibrary resource = new ResourceLibrary("scannerupload");
		String dirname = resource.getString("upload.backupdir");
		//        	dirname = "c:\\GeneratedJava\\";
		File file = FileHandler.saveTempFile(xmlString, "HubItemCountScan_", ".xml", dirname);
//backupdir + "Upload"+format.format(new Date())+".xls"			
		CabinetCountProcess.sendErrorEmailAttach("HubItemCountScan", "hub item scan data backup file", file.getAbsolutePath());
	 //copy file to server
//	 File f = bean.getTheFile();
//	 String fileType = f.getName().substring(f.getName().lastIndexOf("."));
	 String fileType = ".xml";
//	 bean.setFileName(fileType);
//	 ResourceLibrary resource = new ResourceLibrary("scannerupload");
	 BigDecimal upLoadId = factory.getUploadId();

//	 try {
		File dir = new File(resource.getString("upload.serverpath"));
//		File file = File.createTempFile("itemcount-",
//		 "" + fileType + "", dir);

//		FileHandler.copy(bean.getTheFile(), file);
		CountScanLoadList beans = parseResultSubmit(file);
		personnelId = beans.getPersonnelId();
		countScanLoadBeanCollection = beans.getScanData();
		
//		bean.setFileName(file.getName());
//
//		// reading the file and putting the values in a bean
//		BufferedReader in = new BufferedReader(new FileReader(file));
//		String ln = new String();
//		StringTokenizer st = null;
//		while ( (ln = in.readLine()) != null) {
//		 st = new StringTokenizer(ln, ",");
//
//		 if (st.countTokens() > 1) {
//			int loopcount = 0;
//			CountScanLoadBean countScanLoadBean = new
//			 CountScanLoadBean();
//			countScanLoadBean.setLoadId(upLoadId);
//
//			while (st.hasMoreTokens()) {
//			 loopcount++;
//			 String tok = st.nextToken();
//			 String tokenValue = "";
//
//			 tokenValue = tok == null ? "" : tok.trim();
//			 if (loopcount == 1) {
//				if (tokenValue == null || tokenValue.length() == 0) {
//				 countScanLoadBean.setPersonnelId(personnelId);
//				 personnelIdMessage = "Did not get a personnel ID for who did the scan upon upload. Using the personnel Id of the person logged into to tcmIS as the scanner ID.";
//				}
//				else {
//				 BigDecimal counterId = com.tcmis.common.util.NumberHandler.
//					zeroBigDecimalIfNull(tokenValue);
//				 countScanLoadBean.setPersonnelId(counterId);
//				}
//			 }
//			 else if (loopcount == 2) {
//				countScanLoadBean.setTypeItemOrPn(tokenValue);
//			 }
//			 else if (loopcount == 3) {
//				countScanLoadBean.setLoadInventoryGroup(tokenValue);
//			 }
//			 else if (loopcount == 4) {
//				BigDecimal uomCountedQuantity = com.tcmis.common.util.NumberHandler.
//				 zeroBigDecimalIfNull(tokenValue);
//				countScanLoadBean.setUomCountedQuantity(uomCountedQuantity);
//			 }
//			 else if (loopcount == 5) {
//				countScanLoadBean.setScanDate(com.tcmis.common.util.
//				 DateHandler.getDateFromString("yyyy/MM/dd HH:mm:ss", tokenValue));
//			 }
//			 else if (loopcount == 6) {
//				countScanLoadBean.setDateCounted(com.tcmis.common.util.
//				 DateHandler.getDateFromString("yyyy/MM/dd HH:mm:ss", tokenValue));
//			 }
//			}
//			countScanLoadBeanCollection.add(countScanLoadBean);
//		 }
//		}
//		in.close();

		//file.deleteOnExit();
	 	String Company = "Radian";
	 	errorMessage = "TokenFailure: Please sign in again.";
		boolean hastoken = false;
//		for(CountScanLoadBean list:countScanLoadBeanCollection) 
		{
				//			DbManager dbManager = new DbManager("TCM_OPS");
				String logonId = this.factory.selectSingleValue("select logon_id from personnel where personnel_id = " + personnelId );
				String query = null;//PersonnelProcess.getVerifyTokenStr(Company,logonId,list.getToken());
			    String s = this.factory.getFunctionValue("Pkg_token.fx_verify_tcmis_token",
						   logonId,
						   Company,
						   beans.getToken()
						   );
			    // temporarily don't check token.
//				s="OK";
				log.debug("token valid:"+beans.getToken()+s);
				if( "OK".equals(s) ) hastoken = true;
//				String s = this.factory.selectSingleValue(query);
//				if( this.isBlank(s) ) throw new Exception("Valid token required.");
		}
		log.debug("hastoken:"+hastoken);
		if( !hastoken ) throw new Exception("");
//	 }
//	 catch (Exception e) {
//		BaseException be = new BaseException("Can't put document file on server:" +
//		 e.getMessage());
//		be.setRootCause(e);
//		e.printStackTrace();
//		throw be;
//	 }

	 //insert into data table
//	 errorMessage = "";

//	 Collection processResult = null;
//	 BigDecimal errorCount = new BigDecimal("0");
	 HubItemCountWorkerProcess p = new HubItemCountWorkerProcess(beans,factory,upLoadId,getClient()); 
	 Thread thread = new Thread(p);
	 thread.start();
	 errorMessage = "Upload Successful";

	 //calling the procedure to process the scans
//	 try {
//		processResult = factory.processScan(upLoadId);
//		Iterator processResultIterator = processResult.iterator();
//
//		int processResultCount = 0;
//		String closePoLineerrorCode = "";
//		while (processResultIterator.hasNext()) {
//		 if (processResultCount == 0) {
//			BigDecimal errorCountRerulst = (BigDecimal) processResultIterator.next();
//			//log.info("errorCountRerulst   "+errorCountRerulst+"");
//			if (errorCountRerulst == null || errorCountRerulst.intValue() == 0) {
//			 errorCount = new BigDecimal("0");
//			}
//			else
//			{
//			 errorCount = new BigDecimal("1");
//			}
//		 }
//		 processResultCount++;
//		}
//
//	 }
//	 catch (Exception ex1) {
//		ex1.printStackTrace();
//		errorMessage += ex1.getMessage();
//	 }

	 //sending out emails to let the user know the results
//	 BulkMailProcess bulkMailProcess = new
//		BulkMailProcess(this.getClient());
//
//   if (errorCount.intValue() == 0)
//	 {
//		//String wwwHome = resource.getString("upload.hosturl");
//		//String resultUrl = wwwHome + resource.getString("upload.inventorycountresult");
//	   	errorMessage += "\n\nThe item count scan upload has been processed " +
//		 "successfully.\n\n\n";
//		bulkMailProcess.sendBulkEmail(personnelId, "Item Count Scanner Upload Results",
//				errorMessage, true);
//		bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
//		 "Item Count Scanner Upload Results", errorMessage, true);
//	 }
//	 else
//	 {
//		 errorMessage = "\n\nThere was an error prcessing the item count scan upload." +
//		 "\n\nThe tech center has been notified.";
//		bulkMailProcess.sendBulkEmail(personnelId, "Error processing Item Count Scanner Upload Results",
//				errorMessage, true);
//		bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
//		 "Error processing Scanner Upload Results", errorMessage, true);
//	 }
	}
	catch(Exception ex){};
	
	StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><ItemScanSubmitResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\"><Result>"+errorMessage+"</Result></ItemScanSubmitResponse>");
	return sw.toString();
//	else {
//	 return "No File";
//	}
 }
}

