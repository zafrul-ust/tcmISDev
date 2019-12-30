package com.tcmis.internal.hub.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.ScannedReceiptReportViewBean;
import com.tcmis.internal.hub.beans.ScannedReceiptsBean;
import com.tcmis.internal.hub.factory.ScannedReceiptReportViewBeanFactory;
import com.tcmis.internal.hub.factory.ScannedBinMissingReceiptBeanFactory;

import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.ExcelHandler;;
/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class CycleCountProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CycleCountProcess(String client) {
    super(client);
  }

  public Collection getsearchResult(String uploadId,boolean excelReport) throws
	  BaseException {
	DbManager dbManager = new DbManager(getClient());

	ScannedReceiptReportViewBeanFactory factory =
		new ScannedReceiptReportViewBeanFactory(dbManager);

	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("uploadId",
						  SearchCriterion.EQUALS,uploadId.toString());

	/*if (!excelReport)
	{
	criteria.addCriterion(factory.ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT,
						  SearchCriterion.NOT_EQUAL,factory.ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT);

	criteria.addCriterion(factory.ATTRIBUTE_SCANNED_BIN,
						  SearchCriterion.NOT_EQUAL,factory.ATTRIBUTE_EXPECTED_BIN);
	}*/

	return factory.selectForErrorReport(criteria,excelReport);
  }

  public Collection getMissingReceipts(String uploadId) throws
		BaseException {
	  DbManager dbManager = new DbManager(getClient());

	  ScannedBinMissingReceiptBeanFactory factory =
		  new ScannedBinMissingReceiptBeanFactory(dbManager);

	  SearchCriteria criteria = new SearchCriteria();
	  criteria.addCriterion("uploadId",
							SearchCriterion.EQUALS,uploadId.toString());

	  return factory.selectDistinct(criteria);
	}

  public Collection createRelationalObject(Collection scannedReceiptReportViewBeanCollection) {
   Collection finalScannedReceiptReportViewBeanCollection = new
	   Vector();
   String nextReceiptNumber = "";
   String nextUploaedId = "";

   int sameReceiptNumberCount = 0;
   Vector collectionVector = new Vector(
	   scannedReceiptReportViewBeanCollection);
   Vector scannedReceiptV = new Vector();

   for (int loop = 0; loop < collectionVector.size(); loop++) {

	 ScannedReceiptReportViewBean
		 currentScannedReceiptReportViewBean = (
			   ScannedReceiptReportViewBean) collectionVector.elementAt(
					 loop);
	 String currentReceiptNumber = ""+
		 currentScannedReceiptReportViewBean.getReceiptId()+"";
	 String currentUploadId = ""+
		 currentScannedReceiptReportViewBean.getUploadId()+"";

	 if (! ( (loop + 1) == collectionVector.size())) {
	   ScannedReceiptReportViewBean
		   nextScannedReceiptReportViewBean = (
			 ScannedReceiptReportViewBean) collectionVector.elementAt(
			 loop + 1);

		 nextReceiptNumber = ""+nextScannedReceiptReportViewBean.getReceiptId()+"";
		 nextUploaedId = "" + nextScannedReceiptReportViewBean.getUploadId() +
			 "";
	   }
	   else {
		 nextReceiptNumber = "";
		 nextUploaedId = "";
	   }

	   boolean sameReceiptNumber = false;
	   if ( currentReceiptNumber.equalsIgnoreCase(nextReceiptNumber) && nextUploaedId.equalsIgnoreCase(currentUploadId) ) {
		 sameReceiptNumber = true;
		 sameReceiptNumberCount++;
	   }

	   ScannedReceiptsBean rcannedReceiptsBean = new
		   ScannedReceiptsBean();
	   rcannedReceiptsBean.setScannedBin(
		   currentScannedReceiptReportViewBean.getScannedBin());
	   rcannedReceiptsBean.setCountedQuantity(
		   currentScannedReceiptReportViewBean.getCountedQuantity());

	   scannedReceiptV.add(rcannedReceiptsBean);

	   if (sameReceiptNumber) {

	   }
	   else {
		 currentScannedReceiptReportViewBean.setScannedReceiptsCollection( (Vector)
			 scannedReceiptV.clone());
		 scannedReceiptV = new Vector();
		 currentScannedReceiptReportViewBean.setRowSpan(new BigDecimal(sameReceiptNumberCount + 1));

	   finalScannedReceiptReportViewBeanCollection.add(
				 currentScannedReceiptReportViewBean);
	   sameReceiptNumberCount = 0;
	 }
   }

   log.info("Final collectionSize here " +
			finalScannedReceiptReportViewBeanCollection.size() + "");
   return finalScannedReceiptReportViewBeanCollection;
 }

  public void writeExcelFile(Collection scannedReceiptReportViewBeanCollection,String filePath,java.util.Locale locale) throws
	  Exception {
	DbManager dbManager = new DbManager(getClient());
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();
	  

	//print rows
	Iterator i11 = scannedReceiptReportViewBeanCollection.iterator();
	int count = 0;
	while (i11.hasNext()) {
	  count++;
	  ScannedReceiptReportViewBean
		  scannedReceiptReportViewBean = (
		  ScannedReceiptReportViewBean) i11.next(); ;

	  if (count ==1) {
		  pw.addRow();
		  pw.addCellKeyBold("cyclecountresults.label.uploadid");
		  pw.addCell(scannedReceiptReportViewBean.getUploadId());
		  pw.addRow();
		/*pw.println("<TD><B>Upload Date:</B></TD>");
		pw.println("<TD>"+com.tcmis.common.util.DateHandler.formatDate(scannedReceiptReportViewBean.getUploadDatetime(),"MM/dd/yyyy HH:mm:ss")+"</TD>");*/

		  pw.addRow();
		  pw.addRow();
		//write column headers
		pw.addCellKeyBold("label.receiptid");
		pw.addCellKeyBold("label.lotstatus");
		pw.addCellKeyBold("cyclecountresults.label.expectedbin");
		pw.addCellKeyBold("cyclecountresults.label.expectedqty");
		pw.addCellKeyBold("cyclecountresults.label.scannedbin");
		pw.addCellKeyBold("label.scannedqty");
		pw.addCellKeyBold("cyclecountresults.label.totalscannedqty");
	  }

	  

	  Collection scannedReceiptsCollection = scannedReceiptReportViewBean.getScannedReceiptsCollection();

	  int sameReceiptCount = 0;
	  Iterator il1receipt = scannedReceiptsCollection.iterator();
	  while (il1receipt.hasNext()) {
		sameReceiptCount++;
		ScannedReceiptsBean scannedReceiptsBean = (
			ScannedReceiptsBean) il1receipt.next(); ;
			pw.addRow();
			pw.addCell(scannedReceiptReportViewBean.getReceiptId());
			pw.addCell(scannedReceiptReportViewBean.getLotStatus());
			pw.addCell(scannedReceiptReportViewBean.getExpectedBin());
			pw.addCell(scannedReceiptReportViewBean.getExpectedQtyForReceipt());

			pw.addCell(scannedReceiptsBean.getScannedBin());
			pw.addCell(scannedReceiptsBean.getCountedQuantity());

			if (sameReceiptCount == 1) pw.addCell(scannedReceiptReportViewBean.getTotalQtyCountedForReceipt());
		}
	}
	pw.write(new FileOutputStream(filePath));
  }
}