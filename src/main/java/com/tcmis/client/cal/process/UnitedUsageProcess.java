package com.tcmis.client.cal.process;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.commons.digester.Digester;

import com.tcmis.client.cal.beans.TransactionTypes;
import com.tcmis.client.cal.beans.UalExtractBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Process to parse xml file for United Usage.
 * @version 1.0
 *****************************************************************************/
public class UnitedUsageProcess
    extends GenericProcess {
 // private static final String TO_EMAIL = "dev-notification@haastcm.com";
  public UnitedUsageProcess(String client) {
    super(client);
  }
  
  static String today = "";
  /******************************************************************************
   * Looks for files to load, if there are any loads them using the load(String filePath) 
   *
   ****************************************************************************/
  public static void load() {
    ResourceLibrary calLibrary = new ResourceLibrary("CAL");
    File dir = new File(calLibrary.getString("xmlDir"));
    //check if directory to move processed files exists
    SimpleDateFormat format = new SimpleDateFormat("yyyymmdd");
    today = format.format(new java.util.Date());
    String fileName;
    File[] listOfFiles = dir.listFiles(); 
    
    try {
	    for (int i = 0; i < listOfFiles.length; i++) 
	    {
	   
	     if (listOfFiles[i].isFile()) 
	     {
	    	 fileName = listOfFiles[i].getName();
	         if (fileName.endsWith(".xml") || fileName.endsWith(".XML"))
	         {
	        	 process(dir.getAbsolutePath() + "\\"+ fileName);
	         }
	       }
	    }
    }
    catch (Exception e) {
      log.error("Error loading xml ", e);
    }
  }

  /******************************************************************************
   * Parses the file in the given path and inserts the data into the 
   * Cal.ual_extract table.<BR>
   *
   * @param filePath  path to the file to parse
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  public static void process(String filePath) throws BaseException {
	  DbManager dbManager = new DbManager("CAL");
	  GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
	  Connection connection = dbManager.getConnection();
	  
	  try {
      File file = new File(filePath);
      
      Digester digester = new Digester();
	  digester.addObjectCreate("AirportPartData/Parts/Part", UalExtractBean.class);
	  digester.addCallMethod("AirportPartData/Parts/Part/PartID", "setPartId", 0);
	  digester.addCallMethod("AirportPartData/Parts/Part/PartSuffix", "setPartSuffix", 0);
	  digester.addCallMethod("AirportPartData/Parts/Part/Bins/BinLocation", "setUalLocation", 0);
	  digester.addCallMethod("AirportPartData/Parts/Part/AirportLocation", "setStation", 0);
	  digester.addCallMethod("AirportPartData/Parts/Part/UnitOfMeasure", "setUnitOfIssue", 0);
	  		  
	  digester.addObjectCreate( "AirportPartData/Parts/Part/TransactionTypes/Receipts/Receipt", TransactionTypes.class );
   	  digester.addSetNext( "AirportPartData/Parts/Part/TransactionTypes/Receipts/Receipt", "addTransactionTypes1" );
      digester.addBeanPropertySetter( "AirportPartData/Parts/Part/TransactionTypes/Receipts/Receipt/TransactionQuantity", "txQty" );
      digester.addBeanPropertySetter( "AirportPartData/Parts/Part/TransactionTypes/Receipts/Receipt/ReceiptDateTime", "txTmstmp" );
      
      digester.addObjectCreate( "AirportPartData/Parts/Part/TransactionTypes/Issues/Issue", TransactionTypes.class );
      digester.addSetNext( "AirportPartData/Parts/Part/TransactionTypes/Issues/Issue", "addTransactionTypes2" );
      digester.addBeanPropertySetter( "AirportPartData/Parts/Part/TransactionTypes/Issues/Issue/TransactionQuantity", "txQty" );
      digester.addBeanPropertySetter( "AirportPartData/Parts/Part/TransactionTypes/Issues/Issue/ReceiptDateTime", "txTmstmp" );

      digester.addObjectCreate( "AirportPartData/Parts/Part/TransactionTypes/Adjustments/Adjustment", TransactionTypes.class );
      digester.addSetNext( "AirportPartData/Parts/Part/TransactionTypes/Adjustments/Adjustment", "addTransactionTypes3" );
      digester.addBeanPropertySetter( "AirportPartData/Parts/Part/TransactionTypes/Adjustments/Adjustment/TransactionQuantity", "txQty" );
      digester.addBeanPropertySetter( "AirportPartData/Parts/Part/TransactionTypes/Adjustments/Adjustment/ReceiptDateTime", "txTmstmp" );
   
	  UalExtractBean ualExtractBean = (UalExtractBean) digester.parse(file);

      for(TransactionTypes typeBean : (Vector<TransactionTypes>) ualExtractBean.getTransactionTypes()) {
  //   	  System.out.println("typeBean:"+typeBean);
    	  StringBuilder query = new StringBuilder("insert into ual_extract (pcn,ual_location, ual_part, station, unit_of_issue, tx_type, tx_qty, tx_tmstmp)");
    	  query.append(" values ('").append(ualExtractBean.getPartId()).append("-").append(ualExtractBean.getPartSuffix());
    	  query.append("','").append(ualExtractBean.getUalLocation()).append("', '");
    	  query.append(ualExtractBean.getPartId()).append("-").append(ualExtractBean.getPartSuffix()).append("','");
    	  query.append(ualExtractBean.getStation()).append("','").append(ualExtractBean.getUnitOfIssue()).append("','");
    	  query.append(typeBean.getTxType()).append("','").append(typeBean.getTxQty()).append("',");
    	  query.append("TO_DATE('").append(typeBean.getTxTmstmp()).append("', 'MM/DD/RRRR hh:mi:ssam'))");

    	  genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
      }   
      FileHandler.move(filePath, filePath+today);
    }
    catch (Exception e) {
    	System.out.print(e);
    }
    finally {
    	dbManager.returnConnection(connection);
		dbManager = null;
		connection = null;
    }
    
  }
  
// sCO20120518154205192001.xml 
  public static void main(String[] args){
  //	  System.out.print("start program");
  	  load();
  //	  System.out.print("end program");
  }

}
