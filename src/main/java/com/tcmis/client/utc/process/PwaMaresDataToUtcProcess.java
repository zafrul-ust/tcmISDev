package com.tcmis.client.utc.process;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;

/******************************************************************************
 * Process to load MARES data to UTC.
 * @version 1.0
 *****************************************************************************/
public class PwaMaresDataToUtcProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public static final String[] HEADER_COLUMN = {"PART_NUMBER","PO_NUM","PS_QTY","PS_DATE","PACKING_SLIP_NO",
                                                "CURE_DATE","SHIPMENT_ID","MFG_LOT_NUM","MFG_EXP_DATE","SUPPLIER_NAME"};
  public static final String[] fileNamePref = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
   														  "R","S","T","U","V","W","X","Y","Z"};

  public PwaMaresDataToUtcProcess(String client) {
    super(client);
  }

  public void load() throws Exception {
    ResourceLibrary maresLibrary = new ResourceLibrary("PWAMaresReport");
    String fromFileName = "";
    String localFilePath = maresLibrary.getString("local.ftp.dir");
    String toFileName = "";
    String remoteFilePath = maresLibrary.getString("local.remote.ftp.dir");
    String baseName = maresLibrary.getString("base.filename");
    SimpleDateFormat prefixFmt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String now = prefixFmt.format(new java.util.Date());
    fromFileName = localFilePath+now+"-"+baseName;
	 toFileName = remoteFilePath+baseName;
	 DbManager dbManager = new DbManager(getClient());
	 Connection connection = dbManager.getConnection();
	 try {
        //get data
		  BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fromFileName));
        if (bufferedWriter != null) {
			  String msg = "Report is ready on server.\nMaterial_id: \n";

			  Statement sqlStatement = connection.createStatement();
			  ResultSet sqlResult = sqlStatement.executeQuery("select distinct * from pwa_mares_report_view");
			  Vector successfulReceipt = new Vector();
			  while (sqlResult.next()) {
				  BigDecimal currentReceiptId = new BigDecimal(sqlResult.getString("PACKING_SLIP_NO").trim());
				  if (!"OK".equals(getReceiptDocument(currentReceiptId,maresLibrary))) {
				  	 continue;
			  	  }else {
				    successfulReceipt.add(currentReceiptId);
			  	  }
				  for (int i = 0; i < HEADER_COLUMN.length; i++) {
					 bufferedWriter.write(sqlResult.getString(HEADER_COLUMN[i]));
					 //keep track of part number to send to Chris Doskos
					 if ("PART_NUMBER".equalsIgnoreCase(HEADER_COLUMN[i])) {
						 msg += "    "+sqlResult.getString(HEADER_COLUMN[i])+"\n";
					 }
				  }
				  bufferedWriter.newLine();
			  }
			  bufferedWriter.close();
			  //if there is something to report
			  if (successfulReceipt.size() > 0) {
			  	 //move file to it place
			    FileHandler.copy(new File(fromFileName),new File(toFileName));
				 //mark receipt as sent
				 markReceiptAsSent(successfulReceipt);
				 //send out email notification
			    sendEmail("Daily MARES report is on server",msg,"MaresReport");
			  }else {
				  //remove empty file
				  File file = new File(fromFileName);
				  file.delete();
			  }
		  }
    } catch (Exception e) {
      log.error("Error getting PWA MARES data (to UTC)", e);
		File file = new File(toFileName);
		file.delete();
		throw e;
    } finally {
		dbManager.returnConnection(connection);
	 }
  } //end of method

  void markReceiptAsSent(Vector successfulReceipt)	throws Exception {
	  DbManager dbManager = new DbManager(getClient());
	  Connection connection = dbManager.getConnection();
	  try {
		  SqlManager sqlManager = new SqlManager();
		  for (int i = 0; i < successfulReceipt.size(); i++) {
			  String query = "insert into utc.pwa_receipt_sent_to_mares(receipt_id,date_sent)"+
					           " values("+successfulReceipt.elementAt(i).toString()+",sysdate)";
			  sqlManager.update(connection, query);
		  }
	  }catch (Exception e) {
		  log.error("Error while trying to mark receipt as sent (MARES to UTC)");
		  throw e;
	  } finally {
		  dbManager.returnConnection(connection);
	  }
  }

  String getReceiptDocument(BigDecimal currentReceiptId, ResourceLibrary maresLibrary) throws Exception {
	  String result = "OK";
	  DbManager dbManager = new DbManager(getClient());
	  Connection connection = dbManager.getConnection();
	  try {
		  String serverURL = maresLibrary.getString("server.url");
		  String serverURLPath = maresLibrary.getString("server.url.path");
		  String remoteFilePath = maresLibrary.getString("local.remote.ftp.dir");
		  Statement sqlStatement = connection.createStatement();
		  ResultSet sqlResult = sqlStatement.executeQuery("select document_url"+
		                                                  " from receipt_document_view where company_id = 'UTC' and"+
				  										  " receipt_id = "+currentReceiptId.toString());
		  int count = 0;
		  while (sqlResult.next()) {
		  	 String tmpFile = sqlResult.getString("document_url");
			 //need to handle both full path and relative path
			 //full path: replace https://www.tcmis.com/ with blank
			 tmpFile = tmpFile.replace(serverURL,"");
			 //relative path /receipt_document...: remove the first /
			 if (tmpFile.indexOf("/") == 0)
				 tmpFile = tmpFile.substring(1);
			 //copy file to UTC ftp directory for pickup
			 File file = new File(serverURLPath+tmpFile);
			 if (file.exists()) {
				String fileExt = tmpFile.substring(tmpFile.lastIndexOf("."));
				if (count == 0) {
					FileHandler.copy(file,new File(remoteFilePath+currentReceiptId+fileExt));
				}else {
					FileHandler.copy(file,new File(remoteFilePath+currentReceiptId+"_"+fileNamePref[count]+fileExt));
				}
				count++;
			 }
		  } //end of while

		  //if there's no receipt document then notify receiver
		  if (count == 0) {
			 String msg = "There is no receiving document for receipt: "+currentReceiptId+".\n"+
				           "Please upload receiving document.";
			 sendEmail("Please upload receiving document",msg,"MaresReleaser");
			 result = "No Document";
		  }
	  }catch (Exception ex) {
      log.error("Error getting receipt documents for PWA MARES (to UTC processing).", ex);
      throw ex;
    } finally {
		dbManager.returnConnection(connection);
	 }
	 return result;
  }  //end of method

  void sendEmail(String subject,String emailMessage,String userGroupId) throws Exception {
    try {
      DbManager dbManager = new DbManager(getClient());
      PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
      Collection c = factory.selectUsersEmailByGroup("All",userGroupId);
      String[] to = new String[c.size()];
      Iterator dataIter = c.iterator();
      int i = 0;
      boolean hasEmail = false;
		int lastPersonnelId = 0;
		while (dataIter.hasNext()) {
        PersonnelBean personnelBean = (PersonnelBean) dataIter.next();
        if (personnelBean.getEmail() != null && personnelBean.getEmail().length() > 0
				&& personnelBean.getPersonnelId() != lastPersonnelId) {
          to[i++] = personnelBean.getEmail();
          hasEmail = true;
        }
		  lastPersonnelId = personnelBean.getPersonnelId();
		}
      //if no one in group send email developers, otherwise send email to hub
      if (!hasEmail) {
        String[] toDev = {"deverror@tcmis.com"};
        MailProcess.sendEmail(toDev, new String[0], "PWA MARES - no member for "+userGroupId, emailMessage, false);
      }else {
        MailProcess.sendEmail(to, new String[0], subject, emailMessage, true);
      }
    }catch (Exception ex) {
      log.error("Error sending error mail for PWA MARES (to UTC processing).", ex);
      throw ex;
    }
  } //end of method

} //end of class
