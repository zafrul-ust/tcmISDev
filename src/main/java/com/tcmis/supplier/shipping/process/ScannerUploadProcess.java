package com.tcmis.supplier.shipping.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

public class ScannerUploadProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ScannerUploadProcess(String client) {
		super(client);
	}

	//reads the scanner file and inserts it into the db
	//the fields should be in order:
	//box label id, box id, pallet id, delivery_ticket, tracking number, carrier name, scan user id, scan date, box rfid, pallet rfid
	public String uploadFile(ScannerUploadInputBean inputBean) throws BaseException {
		String errorMessage = "";
		if (inputBean.getTheFile() != null) {
			if(log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			DbManager dbManager = new DbManager(getClient());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection inArgs = null;
			Collection outArgs = null;
			Iterator iterator = null;
			String message = null;
			BigDecimal scanId = new BigDecimal(dbManager.getOracleSequence("packing_scan_seq"));
			// reading the file and putting the values in a bean
			try {
				BufferedReader in = new BufferedReader(new FileReader(inputBean.
						getTheFile()));

				String line = null;
				while ( (line = in.readLine()) != null) {
					if(log.isDebugEnabled()) {
						log.debug("line:" + line);
					}
					if(line.trim().length() > 0) {
						String[] result = line.split(",");
						inArgs = new Vector(11);
						if(result[0] != null && result[0].trim().length() >0) {
							inArgs.add(new BigDecimal(result[0].trim())); //box label id
						}
						else {
							inArgs.add("");
						}
						if(result[1] != null) {
							inArgs.add(result[1].trim()); //box id
						}
						else {
							inArgs.add("");
						}
						if(result[2] != null) {
							inArgs.add(result[2].trim()); //pallet id
						}
						else {
							inArgs.add("");
						}
						if(result[4] != null) {
							inArgs.add(result[4].trim()); //tracking number
						}
						else {
							inArgs.add("");
						}
						if(result[5] != null) {
							inArgs.add(result[5].trim()); //carrier name
						}
						else {
							inArgs.add("");
						}
						/*When you enter tracking data by case they have to enter the same info many times over,
            they make mistakes and the flow is not right. We re-designed the process to work as shipments.
            The scanner is not following the same process. Ignoring the delivery ticket will help reduce
             the number ship confirm errors.
						 */
						/*if(result[3] != null) {
              inArgs.add(result[3].trim()); //delivery ticket
            }
            else*/ {
        	    inArgs.add("");
            }
            if(result[8] != null) {
        	    inArgs.add(result[8].trim()); //box rfid
            }
            else {
        	    inArgs.add("");
            }
            if(result[9] != null) {
        	    inArgs.add(result[9].trim()); //pallet rfid
            }
            else {
        	    inArgs.add("");
            }
            inArgs.add(scanId); //scan id
            if(result[7] != null) {
        	    inArgs.add(DateHandler.getTimestampFromString("yyyy/MM/dd HH:mm:ss",result[7].trim())); //scan date
            }
            else {
        	    inArgs.add("");
            }
            if(result[6] != null && result[6].trim().length() >0) {
        	    inArgs.add(new BigDecimal(result[6].trim())); //SCAN_USER_ID
            }
            else {
        	    inArgs.add("");
            }

            //if (log.isDebugEnabled())
            {
        	    log.info("p_packing_scan_upload input:" + inArgs);
            }
            outArgs = new Vector(1);
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            outArgs = procFactory.doProcedure("p_packing_scan_upload", inArgs,
        		    outArgs);
            iterator = outArgs.iterator();
            while (iterator.hasNext()) {
        	    message = (String) iterator.next(); ;
            }
            if(log.isDebugEnabled()) {
        	    log.debug("p_packing_scan_upload message:" + message);
            }
            if(message != null && message.trim().length() != 0 && !message.trim().equalsIgnoreCase("OK")) {
        	    //there was some type of error message from the procedure
        	    log.error("Error calling p_packing_scan_upload:" + message);
        	    errorMessage +=message;
        	    //throw new BaseException("generic.error");
            }
					}
				}
				//now that all rows are loaded call final procedure
				inArgs = new Vector(1);
				inArgs.add(scanId);
				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				outArgs = procFactory.doProcedure("p_packing_scan", inArgs, outArgs);
				iterator = outArgs.iterator();
				while (iterator.hasNext()) {
					message = (String) iterator.next(); ;
				}
				if(log.isDebugEnabled()) {
					log.debug("final message:" + message);
				}
				if (message != null && message.trim().length() != 0 &&
						!message.trim().equalsIgnoreCase("OK")) {
					//there was some type of error message from the procedure
					log.error("Error calling p_packing_scan:" + message);
					errorMessage +=message;
					//throw new BaseException("generic.error");
				}
				in.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error("Error reading scanner upload file:" + e.getMessage());
				throw new BaseException(e);
			}
		}
		else {
			log.debug("file is null");
		}
		return errorMessage;
	}

}