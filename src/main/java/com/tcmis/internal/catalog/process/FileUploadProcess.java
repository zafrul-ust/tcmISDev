package com.tcmis.internal.catalog.process;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

public class FileUploadProcess extends BaseProcess {

	public FileUploadProcess(String client,Locale locale) {
		super(client,locale);
	}

	//reads the scanner file and inserts it into the db
	//the fields should be in order:
	//box label id, box id, pallet id, delivery_ticket, tracking number, carrier name, scan user id, scan date, box rfid, pallet rfid
	public String uploadFile(FileUploadBean inputBean) throws BaseException {
		String errorMessage = "";
		int loadcount = 0 ;
		if (inputBean.getTheFile() != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			DbManager dbManager = new DbManager(getClient());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection inArgs = null;
			Collection outArgs = null;
			Iterator iterator = null;
			String message = null;
			// reading the file and putting the values in a bean
			try {

				//    	  java.io.PrintWriter outtxt = new java.io.PrintWriter(new File("C:\\AirgasMapping.txt"));

				FileHandler.copy(inputBean.getTheFile(), new File("/webdata/html/"+inputBean.getLocation()+"/"+inputBean.getFilename()));
				// 		 FileHandler.copy(inputBean.getTheFile(), new File("c:\\"+inputBean.getLocation()+"\\"+inputBean.getFilename()));
				//outtxt.close();
				//    	  Process p = Runtime.getRuntime().exec("/usr/local/bin/airgasloader");
				/*
          java.io.BufferedReader stdInput = new java.io.BufferedReader(new
        		  java.io.InputStreamReader(p.getInputStream()));

          java.io.BufferedReader stdError = new java.io.BufferedReader(new
        		  java.io.InputStreamReader(p.getErrorStream()));
				 */
				//          int exitVal = p.waitFor();

			}
			catch (Exception e) {
				e.printStackTrace();
				errorMessage = "Error uploading file:";
			}
		}
		else {
			log.debug("file is null");
		}
		if( loadcount != 0 )
			errorMessage = "Total records loaded: " + loadcount;
		return errorMessage;
	}

}