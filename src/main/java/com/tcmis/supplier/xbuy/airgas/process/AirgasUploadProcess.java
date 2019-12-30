package com.tcmis.supplier.xbuy.airgas.process;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

public class AirgasUploadProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public AirgasUploadProcess(String client) {
		super(client);
	}

	//reads the scanner file and inserts it into the db
	//the fields should be in order:
	//box label id, box id, pallet id, delivery_ticket, tracking number, carrier name, scan user id, scan date, box rfid, pallet rfid
	public String uploadFile(ScannerUploadInputBean inputBean) throws BaseException {
		String errorMessage = "";
		int loadcount = 0 ;
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
			// reading the file and putting the values in a bean
			try {

				java.io.PrintWriter outtxt = new java.io.PrintWriter(new File("/webdata/html/reports/temp/AirgasMapping.txt"));
				//    	  java.io.PrintWriter outtxt = new java.io.PrintWriter(new File("C:\\AirgasMapping.txt"));
				java.io.InputStream myxls = new java.io.FileInputStream(inputBean.getTheFile());
				HSSFWorkbook wb     = new HSSFWorkbook(myxls);
				HSSFSheet sheet = wb.getSheetAt(0);
				int CellCount =sheet.getRow(1).getLastCellNum();

				SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
				java.util.Date now = new java.util.Date();
				String nowS = dateFormat.format(now);

				for(int i = 2 ;i<=sheet.getLastRowNum();i++){
					//System.out.println("Line:"+i);
					HSSFRow row     = sheet.getRow(i);        // i+1 row// first sheet
					if( row == null ) continue;
					String line="";
					String prefix="START MESSAGE";
					String deli="";
					for(short j=0;j< CellCount ;j++) {
						HSSFCell cell   = row.getCell(j);
						if( cell == null ) { row = null; break; }
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							line += deli + prefix + cell.getRichStringCellValue().getString().replaceAll("[\\xA0]" , "").trim() + prefix;
						}
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
							line += deli + prefix + cell.getNumericCellValue() + prefix;
						else
							line += deli + prefix + "" + prefix;
						deli=",";
					}
					if( row != null ) {
						outtxt.println(line + "," + nowS);
						loadcount++;
					}
				}

				outtxt.close();
				Process p = Runtime.getRuntime().exec("/usr/local/bin/airgasloader");
				/*
          java.io.BufferedReader stdInput = new java.io.BufferedReader(new
        		  java.io.InputStreamReader(p.getInputStream()));

          java.io.BufferedReader stdError = new java.io.BufferedReader(new
        		  java.io.InputStreamReader(p.getErrorStream()));
				 */
				int exitVal = p.waitFor();

			}
			catch (Exception e) {
				e.printStackTrace();
				errorMessage = "Error reading scanner upload file:" + e.getMessage();
				throw new BaseException(e);
			}
		}
		else {
			errorMessage = "file is null";
		}
		if( loadcount != 0 )
			errorMessage = "Total records loaded: " + loadcount;
		return errorMessage;
	}

}