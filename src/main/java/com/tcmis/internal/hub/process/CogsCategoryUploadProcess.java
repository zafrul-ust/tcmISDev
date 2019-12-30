package com.tcmis.internal.hub.process;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.CSVReader;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;

public class CogsCategoryUploadProcess extends GenericProcess {
	
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	ResourceLibrary directoryName = new ResourceLibrary("tcmISWebResource");
	DbManager dbManager;
    Connection connection = null;
    GenericSqlFactory genericSqlFactory;
    
	public CogsCategoryUploadProcess(TcmISBaseAction act) throws BaseException {
		super(act);		
	}
	
	public static boolean isValidInt(String s) {
		try {
			int number = (new BigDecimal(s)).intValue();	
		}
		catch (Exception e) {
			return false;
		}
		return true;

	} //end testValidInt

	public ArrayList<String> uploadFile(FileUploadBean inputBean,PersonnelBean pbean) throws DbReturnConnectionException {
		ArrayList<String> returnMessage =  new ArrayList<String>();
		int numberOfErrors = 0;
		int row = 0;
		int count = 0;
		if (inputBean.getTheFile() == null) {
			returnMessage.add("EXCEPTION");
			returnMessage.add("<br/>File is Empty");
			return returnMessage;
		}
		if (log.isDebugEnabled()) {
			log.debug(inputBean.getTheFile().getName());
		}
		
		try {
			dbManager = new DbManager(this.getClient(), this.getLocale());						
		    connection = dbManager.getConnection();	                
	        genericSqlFactory = new GenericSqlFactory(dbManager);
	        
			//reading the file and putting the values in a bean
			//String dirname = directoryName.getString("savecogsuploadfile");
	        String dirname = directoryName.getString("saveltempreportpath");
			File dir = new File(dirname);
			File f = File.createTempFile("tmpCogsCategoryUpload_", ".csv", dir);
			FileHandler.copy(inputBean.getTheFile(), f);					
			Vector<Vector<String>> excelData = CSVReader.read(new FileInputStream(f));
			//create the insert query for the temporary table		
			StringBuffer query = new StringBuffer("INSERT ALL ");
			ArrayList<String> queries = new ArrayList<String>();
			String insertOfquery = " INTO ITEM_CYCLE_COUNT (ITEM_ID, COUNT_MONTH, COUNT_CATEGORY, SALES_VELOCITY) VALUES ( ";
			//Capture the row errors and add them to file error
			StringBuilder lineError;
			StringBuilder fileError = new StringBuilder();

			for( Vector<String> v : excelData ) 
			{
				boolean blnLineError = false;
				lineError = new StringBuilder();
				row++;				
				if( v == null  )  continue;
				if( row == 1 ) {
					if (!validHeader(v)) {						 
						returnMessage.add("EXCEPTION");
						returnMessage.add(library.getString("error.upload.invalidHeader"));
						return returnMessage;
					}
					continue;
				}
				if( row == 2  )  continue; //this row after the header has the 100% cogs value details
														
				String groupType = null;
				String salesVelocity = null;
				String itemId = null;
				String count1Month = null;
				String count2Month = null;				
				String count3Month = null;
				String count4Month = null;				
				
				//Check the group of the first row - cannot be null
				if (StringHandler.isBlankString((String)v.get(0))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("Group");	
					blnLineError = true;
				} else 
					groupType = (String)v.get(0);

				
				//check for null or empty string. IssuedMonthRank or SalesVelocity can be empty
				if (!isValidInt((String)v.get(1))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("IssuedMonthRank");					
				} else {
					salesVelocity = (String)v.get(1);
				}
				
				//get the itemid - cannot be null
				if (StringHandler.isBlankString((String)v.get(6))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("ITEM_ID");				
					blnLineError = true;
				}else {
					itemId = (String)v.get(6);
				}
				
				//Count 1 Month should not be null or 0. It should be > 0 
				if ( !blnLineError) {
					if (!isValidInt((String)v.get(2))) {
						if(lineError.length() > 0)
							lineError.append(", ");
						lineError.append("Count 1 Month");
					} else {
						//Round down if not integer quantity 
						int tmpVal = (new BigDecimal(v.get(2), new MathContext(0,RoundingMode.DOWN))).intValue();
						if ((tmpVal < 1 || tmpVal > 12) && (groupType.equalsIgnoreCase("A") || groupType.equalsIgnoreCase("B") || groupType.equalsIgnoreCase("C"))) {
		                    if(lineError.length() > 0)
							    lineError.append(", ");
						    lineError.append("Count 1 Month");
		                } else {
		                	count1Month = tmpVal + "";
		                	query.append(insertOfquery);
		                	query.append(itemId + ", " + count1Month + ", " + SqlHandler.delimitString(groupType) + ", " + salesVelocity);
		                	query.append(")\n");
		                }
					}
				}
				//Count 2 Month can be null for Group C
				if ( !blnLineError && (!StringHandler.isBlankString((String)v.get(3)) || groupType.equalsIgnoreCase("A") || groupType.equalsIgnoreCase("B"))) {
					if (!isValidInt((String)v.get(3))) {
						if(lineError.length() > 0)
							lineError.append(", ");
						lineError.append("Count 2 Month");
					} else {
						//Round down if not integer quantity 
						int tmpVal = (new BigDecimal(v.get(3), new MathContext(0,RoundingMode.DOWN))).intValue();
						if ((tmpVal < 1 || tmpVal > 12) && (groupType.equalsIgnoreCase("A") || groupType.equalsIgnoreCase("B"))) {
		                    if(lineError.length() > 0)
							    lineError.append(", ");
						    lineError.append("Count 2 Month");
		                } else {
		                	count2Month = tmpVal + "";
							query.append(insertOfquery);
		                	query.append(itemId + ", " + count2Month + ", " + SqlHandler.delimitString(groupType) + ", " + salesVelocity);
		                	query.append(")\n");
		                }
					}
				}
				
				//Count 3 Month can be null for Group B and C
				if (!blnLineError && (!StringHandler.isBlankString((String)v.get(4)) || groupType.equalsIgnoreCase("A"))) {
					if (!isValidInt((String)v.get(4))) {
						if(lineError.length() > 0)
							lineError.append(", ");
						lineError.append("Count 3 Month");
					} else {
						//Round down if not integer quantity 
						int tmpVal = (new BigDecimal(v.get(4), new MathContext(0,RoundingMode.DOWN))).intValue();
						if ((tmpVal < 1 || tmpVal > 12) && groupType.equalsIgnoreCase("A")) {
		                    if(lineError.length() > 0)
							    lineError.append(", ");
						    lineError.append("Count 3 Month");
		                } else {
		                	count3Month = tmpVal + "";
		                	query.append(insertOfquery);
		                	query.append(itemId + ", " + count3Month + ", " + SqlHandler.delimitString(groupType) + ", " + salesVelocity);
		                	query.append(")\n");
		                }
					}
				}
				
				//Count 4 Month can be null for Group B and C
				if (!blnLineError && (!StringHandler.isBlankString((String)v.get(5)) || groupType.equalsIgnoreCase("A"))) {
					if (!isValidInt((String)v.get(5))) {
						if(lineError.length() > 0)
							lineError.append(", ");
						lineError.append("Count 4 Month");
					} else {
						//Round down if not integer quantity 
						int tmpVal = (new BigDecimal(v.get(5), new MathContext(0,RoundingMode.DOWN))).intValue();
						if ((tmpVal < 1 || tmpVal > 12) && groupType.equalsIgnoreCase("A")) {
		                    if(lineError.length() > 0)
							    lineError.append(", ");
						    lineError.append("Count 4 Month");
		                } else {
		                	count4Month = tmpVal + "";
		                	query.append(insertOfquery);
		                	query.append(itemId + ", " + count4Month + ", " + SqlHandler.delimitString(groupType) + ", " + salesVelocity);
		                	query.append(")\n");
		                }
					}
				}
				
				// only build the query if there are no errors
				if(lineError.length() > 0) {
					numberOfErrors++;
					if (numberOfErrors <= 100)
						fileError.append("Row " + row + ": Invalid data found in column: " + lineError.toString() + "\n");					
				} else if(lineError.length() < 1) {
					count++;					
					if( row % 100 == 0 || (row == excelData.size())) {
						query.append(" SELECT * FROM DUAL ");
						queries.add(query.toString());
						query = new StringBuffer("INSERT ALL ");
					}
				}		
			}
			
			if (fileError.length() > 0) {
				//If the file has any errors while loading send the file with an error message
				//send email to the logged in user for any mismatch found
				String subject = "";
				if (numberOfErrors > 100) {
					subject = library.getString("error.cogscategoryuploadfailed");
					fileError.append(library.getString("error.manymanymore"));
					returnMessage.add("MISMATCH");			
					returnMessage.add(library.getString("error.upload.morethan100errors"));
				} else { 
					subject = library.getString("error.subject");
				}
				StringBuffer messageBody = new StringBuffer();
				messageBody.append(library.getString("error.emailmessage") + "\n");
				messageBody.append(fileError.toString() + "\n");
				MailProcess.sendEmail(pbean.getEmail(), null, MailProcess.DEFAULT_FROM_EMAIL, subject, messageBody.toString());
				if (log.isDebugEnabled())
					log.debug("During the CogsCategoryUpload " + numberOfErrors + " errors were encountered and an email has " +
							"been sent to the following email address - " + pbean.getEmail() + " with the first 100 errors.");
			}
			if( numberOfErrors <= 100 && query.length() > 0 ) {
				connection.setAutoCommit(false); 
				//truncate the table
				genericSqlFactory.deleteInsertUpdate("TRUNCATE TABLE ITEM_CYCLE_COUNT", connection);
				int rowsInserted = 0;
				//insert only if there are no errors and there is at least one row to be inserted
				for (int i=0; i < queries.size(); i++) {					
					rowsInserted += genericSqlFactory.deleteInsertUpdate(queries.get(i).toString(), connection);
				}
				if (numberOfErrors == 0 ) {
					returnMessage.add("OK");
					returnMessage.add(library.format("label.upload.successnoerrors", rowsInserted));
				} else {
					returnMessage.add("OK");
					returnMessage.add(library.format("label.upload.successwitherrors", numberOfErrors, rowsInserted));
				}
				connection.commit();
				connection.setAutoCommit(true);				
			}
		} catch (Exception ex) {
			returnMessage.add("EXCEPTION");
			returnMessage.add(library.getString("error.upload.generic"));
			log.error("Exception thrown while processing the file", ex);
		} finally {			
            dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return returnMessage;
	}
	
    //Validate the header
	private boolean validHeader(Vector<String> v) {
		boolean blnValidHeader = true;
		try {
			if(!((String)v.get(0)).equalsIgnoreCase("GROUP")) {
				blnValidHeader = false;
				return blnValidHeader;
			}			
			if(!((String)v.get(1)).equalsIgnoreCase("IssuedMonthRank")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(2)).equalsIgnoreCase("Count 1 Month")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(3)).equalsIgnoreCase("Count 2 Month")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(4)).equalsIgnoreCase("Count 3 Month")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(5)).equalsIgnoreCase("Count 4 Month")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(6)).equalsIgnoreCase("ITEM_ID")) {
				blnValidHeader = false;
				return blnValidHeader;
			}			
		} catch (Exception e) {
			blnValidHeader = false;
		}		
		return blnValidHeader;
	}
}
