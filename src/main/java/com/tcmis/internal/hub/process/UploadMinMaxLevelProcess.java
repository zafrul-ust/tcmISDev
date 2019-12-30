package com.tcmis.internal.hub.process;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

public class UploadMinMaxLevelProcess extends GenericProcess {
	
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	ResourceLibrary directoryName = new ResourceLibrary("scannerupload");
	DbManager dbManager;
    Connection connection = null;
    GenericSqlFactory genericSqlFactory;
    
    public UploadMinMaxLevelProcess(String client) {
		super(client);
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
    
	public static boolean isNullOrEmpty(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		else 
			return false;

	} //end isNull
	
    public ArrayList<String> saveExcelFile(ScannerUploadInputBean inputBean, PersonnelBean pbean) throws BaseException 
    {
		ArrayList<String> returnMessage =  new ArrayList<String>();
		
		int row = 0;
		int count = 0;
		int lastRow = 0;
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
			String dirname = directoryName.getString("upload.backupdir");
			File dir = new File(dirname);
			File f = File.createTempFile("tmpUploadMinMaxLevel_", ".xls", dir);
			FileHandler.copy(inputBean.getTheFile(), f);					
			Vector<Vector<String>> excelData = ExcelHandler.read(new FileInputStream(f));
			
			//create the insert query for the temporary table		
			StringBuffer query = new StringBuffer("INSERT ALL ");;
			ArrayList<String> queries = new ArrayList<String>();
			
			//Capture the row errors and add them to file error
			StringBuilder lineError;
			StringBuilder fileError = new StringBuilder();

			for( Vector<String> v : excelData ) 
			{
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
								
				boolean dataIncorrect = false;
				boolean reorderPointIncorrect = false;
				Date levelHoldEndDate = null;				
				String finalStockingLevel = null;
				String reorderQuantity = null;				
				String finalReorderPt = null;
				String projectedLeadTime = null;
				lastRow = excelData.size();
				
				//if there are less than 10 columns in a row return with exception. last 2 columns can be null
				if (v.size() < 10 ) {
					returnMessage.add("EXCEPTION");
					returnMessage.add(library.getString("error.uploadminmax.datamissing"));
					return returnMessage;
				}
				
				//check for null or empty string. all the fields are mandatory except the reorder point, stocking level, levelHoldEndDate and projected lead time
				if (isNullOrEmpty((String)v.get(0))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("COMPANY_ID");					
				}
				
				if (isNullOrEmpty((String)v.get(1))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("CATALOG_ID");					
				}
				
				if (isNullOrEmpty((String)v.get(2))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("INVENTORY_GROUP");					
				}
				
				if (isNullOrEmpty((String)v.get(3))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("FAC_PART_NO");					
				} 
				
				//Reorder Point should not be null or 0. It should be > 0 
				if (!isValidInt((String)v.get(4))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("FINAL_REORDER_PT");
					reorderPointIncorrect = true;
				} else {
					//Round down if not integer quantity 
					int tmpVal = (new BigDecimal(v.get(4), new MathContext(0,RoundingMode.DOWN))).intValue();
					if (tmpVal < 0) {
	                    if(lineError.length() > 0)
						    lineError.append(", ");
					    lineError.append("FINAL_REORDER_PT");
					    reorderPointIncorrect = true;
	                } else
	                    finalReorderPt = tmpVal + "";
				}
				
				//We should NOT have stocking_level and reorder_quantity on the same transaction (the cell will have null value) 
				if ((isNullOrEmpty((String)v.get(5)) && isNullOrEmpty((String)v.get(6))) 
						|| (!isNullOrEmpty((String)v.get(5)) && !isNullOrEmpty((String)v.get(6)))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("Enter data for REORDER_QUANTITY or FINAL_STOCKING_LEVEL");
					dataIncorrect = true;
				} 
				
				//Reorder_quantity need NOT be validated to be => then FINAL_REORDER_PT but should be >= 0 
				if (!dataIncorrect && !isNullOrEmpty((String)v.get(5))) {
					if (!isValidInt((String)v.get(5))) {
						if(lineError.length() > 0)
							lineError.append(", ");
						lineError.append("REORDER_QUANTITY");
					} else {
						//Round down if not integer quantity
						int tmpVal = (new BigDecimal(v.get(5), new MathContext(0,RoundingMode.DOWN))).intValue();
						if (tmpVal < 0) {
		                    if(lineError.length() > 0)
							    lineError.append(", ");
						    lineError.append("REORDER_QUANTITY");
		                } else 
		                	reorderQuantity = tmpVal + "";
					}
				}
				
				//stocking level
				if (!dataIncorrect && !isNullOrEmpty((String)v.get(6))) {
					if (!isValidInt((String)v.get(6))) {
						if(lineError.length() > 0)
							lineError.append(", ");
						lineError.append("FINAL_STOCKING_LEVEL");
					} else {
						//Round down if not integer quantity
						int tmpVal = (new BigDecimal(v.get(6), new MathContext(0,RoundingMode.DOWN))).intValue();
						// Stocking_level should be => then reorder_point 
						if (tmpVal >= 0 && !reorderPointIncorrect && (tmpVal >= (new BigDecimal(finalReorderPt).intValue())) ) {
							finalStockingLevel = tmpVal + "";
		                } else {		                	
		                	if(lineError.length() > 0)
							    lineError.append(", ");
						    lineError.append("FINAL_STOCKING_LEVEL");
		                }
					}
				}
				
				if (isNullOrEmpty((String)v.get(7))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("EXIT_MM");					
				} 

                /* no longer required
                if (isNullOrEmpty((String)v.get(8))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("HUB_MANAGER_COMMENTS");
				} */
				

				if (isNullOrEmpty((String)v.get(9))) {
					if(lineError.length() > 0)
						lineError.append(", ");
					lineError.append("CATALOG_COMPANY_ID");
				}
				
				//can be null
				if (v.size() >= 11) {
					if (!isNullOrEmpty((String)v.get(10))) {				
						if (StringHandler.isValidDouble((String)v.get(10))) {
							levelHoldEndDate = ExcelHandler.getDate((int)Double.parseDouble(v.get(10)));
						} else {
							if(lineError.length() > 0)
								lineError.append(", ");
							lineError.append("LEVEL_HOLD_END_DATE");
						}
					}				
				}
				
				//can be null
				if (v.size() == 12) {
					if (!isNullOrEmpty((String)v.get(11))) {
						if (!isValidInt((String)v.get(11))) {
							if(lineError.length() > 0)
								lineError.append(", ");
							lineError.append("PROJECTED_LEAD_TIME");
						} else {
							//Round down if not integer number
							int tmpVal = (new BigDecimal(v.get(11), new MathContext(0,RoundingMode.DOWN))).intValue();
							projectedLeadTime = tmpVal + "";		                
						}
					}
				}
								 
				// only build the query if there are no errors
				if(lineError.length() > 0)
					fileError.append("Row " + row + ": Invalid data found: " + lineError.toString() + "\n");
				else if(lineError.length() < 1) {
					count++;	
					query.append("INTO GT_MINMAX_LEVEL_UPLOAD (COMPANY_ID, CATALOG_ID, INVENTORY_GROUP, FAC_PART_NO, FINAL_REORDER_PT, REORDER_QUANTITY, " +
							"FINAL_STOCKING_LEVEL, EXIT_MM, HUB_MANAGER_COMMENTS, CATALOG_COMPANY_ID, LEVEL_HOLD_END_DATE, PROJECTED_LEAD_TIME) ");
					query.append("VALUES (" + SqlHandler.delimitString(v.get(0)) + ","  + SqlHandler.delimitString(v.get(1)) + ","  + SqlHandler.delimitString(v.get(2)) + ","  + SqlHandler.delimitString(v.get(3)) + ","  + finalReorderPt + ","  +
							reorderQuantity + "," + finalStockingLevel + ","  + SqlHandler.delimitString(v.get(7)) + ","  + SqlHandler.delimitString(v.get(8)) + "," +
							SqlHandler.delimitString(v.get(9)) + "," + DateHandler.getOracleToDateFunction(levelHoldEndDate)+ "," + projectedLeadTime +")\n");
										
					
					if( row % 100 == 0 || (row == excelData.size())) {
						query.append(" SELECT * FROM DUAL ");
						queries.add(query.toString());
						query = new StringBuffer("INSERT ALL ");
					}
				}
			}
		
			if (fileError.length() > 0) {				
				returnMessage.add("MISMATCH");			
				returnMessage.add(library.getString("error.upload.mismatch"));
				//If the file has any errors while loading send the file with an error message
				//send email to the logged in user for any mismatch found
				StringBuffer messageBody = new StringBuffer();
				messageBody.append("Following errors were encountered during the upload\n");
				messageBody.append(fileError.toString() + "\n");
				MailProcess.sendEmail(pbean.getEmail(), null, MailProcess.DEFAULT_FROM_EMAIL, "UploadMinMaxLevel failed", messageBody.toString());
			} else if( count > 0 ) {
				connection.setAutoCommit(false); 
				int rowsInserted = 0;
				//insert only if there are no errors and there is at least one row to be inserted
				for (int i=0; i < queries.size(); i++) {					
					rowsInserted += genericSqlFactory.deleteInsertUpdate(queries.get(i).toString(), connection);
				}
				
				String outMessage = null;
				String updatedRecords = null;
				Vector inArgs = new Vector();
				Vector outArgs = new Vector();
				inArgs.add(pbean.getPersonnelId());
	            outArgs.add(new Integer(java.sql.Types.VARCHAR));
	            outArgs.add(new Integer(java.sql.Types.VARCHAR));
	            //invoke procedure to compare the excel data
				Vector procErrorMessage = (Vector)genericSqlFactory.doProcedure(connection, "P_MINMAX_LEVEL_UPLOAD", inArgs, outArgs);
				connection.commit();
				connection.setAutoCommit(true);
				//return procedure message back to the user 
				if (procErrorMessage != null && procErrorMessage.size() > 0) {
					updatedRecords = (String) procErrorMessage.get(0);
					outMessage = (String) procErrorMessage.get(1);
					log.debug("Procedure output - " + outMessage);
					returnMessage.add(outMessage);
					if (outMessage.equalsIgnoreCase("OK")){
						returnMessage.add(library.format("label.upload.successful", updatedRecords));
						returnMessage.add(updatedRecords);
					} else if (outMessage.equalsIgnoreCase("ERROR")){
						returnMessage.add(library.format("error.upload.error", updatedRecords));
						returnMessage.add(updatedRecords);
					} else if (outMessage.equalsIgnoreCase("EXCEPTION")){
						returnMessage.add(library.getString("error.upload.generic"));						
					}
	            }	            
			}
		}
		catch (Exception ex) {
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
			if(!((String)v.get(0)).equalsIgnoreCase("COMPANY_ID")) {
				blnValidHeader = false;
				return blnValidHeader;
			}			
			if(!((String)v.get(1)).equalsIgnoreCase("CATALOG_ID")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(2)).equalsIgnoreCase("INVENTORY_GROUP")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(3)).equalsIgnoreCase("FAC_PART_NO")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(4)).equalsIgnoreCase("FINAL_REORDER_PT")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(5)).equalsIgnoreCase("REORDER_QUANTITY")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(6)).equalsIgnoreCase("FINAL_STOCKING_LEVEL")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(7)).equalsIgnoreCase("EXIT_MM")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(8)).equalsIgnoreCase("HUB_MANAGER_COMMENTS")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
//			if(!((String)v.get(9)).equalsIgnoreCase("STATUS")) {
//				blnValidHeader = false;
//				return blnValidHeader;
//			}
			if(!((String)v.get(9)).equalsIgnoreCase("CATALOG_COMPANY_ID")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(10)).equalsIgnoreCase("LEVEL_HOLD_END_DATE")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
			if(!((String)v.get(11)).equalsIgnoreCase("PROJECTED_LEAD_TIME")) {
				blnValidHeader = false;
				return blnValidHeader;
			}
		} catch (Exception e) {
			blnValidHeader = false;
		}		
		return blnValidHeader;
	}    
   
}
