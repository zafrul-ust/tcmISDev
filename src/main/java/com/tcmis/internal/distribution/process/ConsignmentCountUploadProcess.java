package com.tcmis.internal.distribution.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.CSVReader;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.Globals;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.AddSpecsBean;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;
import com.tcmis.ws.scanner.process.CabinetCountWorkerProcess;


public class ConsignmentCountUploadProcess extends GenericProcess {
	ResourceLibrary library = null;

	public ConsignmentCountUploadProcess(TcmISBaseAction client) throws BaseException{
		super(client);
		library = new ResourceLibrary("scannerupload");
	}
	public ConsignmentCountUploadProcess(String client) throws BaseException{
		super(client);
		library = new ResourceLibrary("scannerupload");
	}
// This code is to fix the double quote issue	
	private Vector<Vector<String>> SimCSVReader(FileInputStream ff) {
//		CSVReader.read(ff,'\t');
		BufferedReader bf = new BufferedReader( new InputStreamReader(ff) );
		Vector result = new Vector();
		String s = null;
		try {
			while( (s = bf.readLine() ) != null) {
				String[] ss = s.split("\t");
				Vector<String> v = new Vector();
				result.add( v );
				if( ss == null || ss.length == 0 ) {
					continue;
				}
				for(String sss: ss) {
					if( sss == null )
						sss = "";
					v.add(sss.trim());
				}
			}
		}catch(Exception ex){};
		return result;
	}
	public String uploadBAEFile(ScannerUploadInputBean inputBean,PersonnelBean pbean) throws BaseException {

		// thcnage this from true for false for production.		
		boolean test = false;
		int loadcount = 0 ;
		String error = "";
		boolean missing = false;
		// get 
		String fileName = inputBean.getDocumentName();
		int ind = fileName.indexOf("/");
		if( ind != -1 )
			fileName = fileName.substring(fileName.indexOf("/"));
		ind = fileName.lastIndexOf("\\");
		if( ind != -1 )
			fileName = fileName.substring(fileName.indexOf("\\"));
		String countStr = this.factory.selectSingleValue("Select count (*) from consignment_rec_proc_stage where file_name  = "+this.getSqlString(fileName) );
		if( !countStr.equals("0") ) {
			ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			return java.text.MessageFormat.format(res.getString("error.consigmnentfileloaded"),fileName);
		}
		FileInputStream ff = null;
		if (inputBean.getTheFile() != null) {
			if(log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			//			DbManager dbManager = new DbManager(getClient());
			//			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection inArgs = null;
			Collection outArgs = null;
			Iterator iterator = null;
			String message = null;

			// reading the file and putting the values in a bean
			try {
				String dirname = library.getString("upload.backupdir");
				File dir = new File(dirname);
				File f = File.createTempFile("BAE_", ".csv", dir);
				FileHandler.copy(inputBean.getTheFile(),f);
				//        	dirname = "c:\\GeneratedJava\\";
//				StringBuilder xmlString = new StringBuilder();
				//    	  java.io.PrintWriter outtxt = new java.io.PrintWriter(new File("C:\\AirgasMapping.txt"));
//				BigDecimal nextupLoadSeq = getNextUpLoadSeq(dbManager); //this.factory.selectSingleValue("select dual from upload_sequence");
				BigDecimal nextupLoadSeq = this.factory.getSequence("upload_sequence");//getNextUpLoadSeq(dbManager); //this.factory.selectSingleValue("select dual from upload_sequence");
				//				SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm" );
				SimpleDateFormat dateFormat = new SimpleDateFormat( "dd-MMM-yy HH:mm" ); //10-Mar-11 12:02
				
				//				java.util.Date now = new java.util.Date();
				//				String nowS = dateFormat.format(now);
				ff = new FileInputStream(inputBean.getTheFile());

				Vector<Vector<String>> sv = SimCSVReader(ff);
				//CSVReader.read(ff,'\t');
				//				Vector<Vector<String>> sv = CSVReader.read(ff);
				//				Vector<Vector<String>> sv = ExcelHandler.read(ff);
				String header ="";
				int count = 0 ;
				for(Vector<String> v : sv) {
					count++;
					try {
						if( count == 1 ) { header = v.get(0); continue; }// line 1 is header.
						if( v == null || v.size() == 0 )  continue; // line 2 empty
						if( count < 4 ) continue; // line 3 is header.
						//Date	ITEM_ID	Description	Qty	Prime No	Batch No	Man. Batch No	Transfer No	Engineer	Bin Location	Stock Type	SLE Date	Price
						if( v.size() < 8 ) continue;
						if( v.get(0).equalsIgnoreCase("Total")) continue;
						{ // 0 date/time test.
							String ss = v.get(0);
							if( ss == null || ss.trim().length()== 0)continue; // empty line
						}
						//					String query = "insert into Consignment_Rec_proc_Prestage( Ship_confirm_date,ITEM_ID,ITEM_Description,ISSUE_QUANTITY,customer_part_no,Receipt_id_chr,first_receipt,second_receipt,misc_info,Transfer_No,Engineer,Bin_Location,Stock_Type,SLE_Date,Price,DATA_SOURCE,LOAD_DATE,STATUS,FILE_NAME,UPLOAD_SEQ_ID,PERSONNEL_ID) values(";
						String query = "insert into Consignment_Rec_proc_Prestage( Ship_confirm_date,ITEM_ID,ITEM_Description,ISSUE_QUANTITY,customer_part_no,Receipt_id_chr,first_receipt,second_receipt,                      Engineer,                                 Price,DATA_SOURCE,LOAD_DATE,STATUS,FILE_NAME,UPLOAD_SEQ_ID,PERSONNEL_ID,LOAD_LINE) values(";
						int pos = 0 ;
						//v.remove(8); // the last one is not needed.
						for(String s: v) {
							if( s != null)
								s = s.trim();
							if( pos == 0 ) { // 0 date/time
								java.util.Date d = dateFormat.parse(s);
								query += this.getSqlString(d) +",";
							}
							else {
								if( pos == 7 && s != null  )
									s = s.replaceAll(",","");
								query += this.getSqlString(s) +",";
								if( pos == 5 ) { // get first and second resecipt
									String rec1 = null;
									String rec2 = null;
									if( s!= null ){
										String[] recs = s.split("/", 2);
										rec1 = recs[0];
										//									if( "3776848/499348-0".equals(s) )
										//										System.out.println("here");
										if( recs.length > 1)
											rec2 = recs[1];
									}
									query += this.getSqlString(rec1) +","+this.getSqlString(rec2) +",";
								}
							}
							pos++;
						}
						//					header = inputBean.getFileName(); 
						query += "'IPOS',sysdate,'NEW',"+this.getSqlString(fileName)+","+nextupLoadSeq+","+pbean.getPersonnelId()+","+(loadcount+1)+")";// load line starts from one.
						if( test )
							System.out.println(query);
						else
							this.factory.deleteInsertUpdate(query);
						loadcount++;
					}  catch(Exception ex){ error += ("<br/>Encounter problem on line: "+count); missing = true;}
				}
				System.out.println("calling proc with records: " + loadcount);
				if( !test ) {
					Collection coll = factory.doProcedure("TCM_OPS.PKG_CONSIGNMENT_INVENTORY.P_LOAD_IPOS_DATA", 
							buildProcedureInput(nextupLoadSeq,new BigDecimal(pbean.getPersonnelId())),new Vector());
				}
			}
			catch(Exception ex){error += ex.getMessage();ex.printStackTrace();}
			finally{ try{ ff.close(); }catch(Exception eee){} }
		}
		else {
			log.debug("file is null");
		}
		//		if( loadcount != 0 )
		error += "<br/>Total records loaded: " + loadcount;
		if( missing ) {
			error += "<br/><br/>1: Copy the uploaded file to a new file.<br/>2. Fix the bad record in the new file.<br/>3. Remove all the loaded records from the  new file.<br/>4. Load the new file.";
		}
		return error;
	}

	public String uploadAutoCribData(ScannerUploadInputBean inputBean) throws BaseException {

		// thcnage this from true for false for production.		
		boolean test = false;
		int loadcount = 0 ;
		String error = "";
		boolean missing = false;
		// get 
		//				String fileName = inputBean.getDocumentName();
		//				int ind = fileName.indexOf("/");
		//				if( ind != -1 )
		//					fileName = fileName.substring(fileName.indexOf("/"));
		//				ind = fileName.lastIndexOf("\\");
		//				if( ind != -1 )
		//					fileName = fileName.substring(fileName.indexOf("\\"));
		//				String countStr = this.factory.selectSingleValue("Select count (*) from consignment_rec_proc_stage where file_name  = "+this.getSqlString(fileName) );
		//				if( !countStr.equals("0") ) {
		//					ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		//					return java.text.MessageFormat.format(res.getString("error.consigmnentfileloaded"),fileName);
		//				}
//		error += "<br/>Total records loaded: " + loadcount;

		if (inputBean.getTheFile() == null) {
			log.debug("Autocrib data file is null");
			return "<br/>File is Empty";
		}
		if(log.isDebugEnabled()) {
			log.debug(inputBean.getTheFile().getName());
		}
		FileInputStream ff = null;
		//				if (inputBean.getTheFile() != null) 
		{
			//					if(log.isDebugEnabled()) {
			//						log.debug(inputBean.getTheFile().getName());
			//					}
			//					DbManager dbManager = new DbManager(getClient());
			//					GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);

			// reading the file and putting the values in a bean
			try {
				String dirname = library.getString("upload.backupdir");
				File dir = new File(dirname);
				File f = File.createTempFile("Hamilton_", ".csv", dir);
				FileHandler.copy(inputBean.getTheFile(),f);
//				StringBuilder xmlString = new StringBuilder();
					  
				BigDecimal nextupLoadSeq = this.factory.getSequence("upload_sequence");//getNextUpLoadSeq(dbManager); //this.factory.selectSingleValue("select dual from upload_sequence");
				String rquery = "select transaction_number from AUTOCRIB_DATA_PROC_STAGE";
				Object[] c = this.factory.selectIntoObjectArray(rquery);
				Vector rids = (Vector) c[2];
				HashSet<String> existed_transactions = new HashSet();
				for(Object rido:rids) {
					Object[] objArr = (Object[]) rido; 
					String rid = objArr[0].toString();
					existed_transactions.add(rid);
				}
				// MS Date.				2011-03-04 15:32:10.797							
//				SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
				SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy hh:mm:ssa" );
				//				java.util.Date now = new java.util.Date();
				//				String nowS = dateFormat.format(now);
				//						ff = new FileInputStream(inputBean.getTheFile());
				//
				ff = new FileInputStream(inputBean.getTheFile());
				Vector<Vector<String>> sv = CSVReader.read(ff);
//				Vector<String[]> sv = inputBean;//CSVReader.read(ff,'\t');
				//						Vector<Vector<String>> sv = CSVReader.read(ff);
				//						Vector<Vector<String>> sv = ExcelHandler.read(ff);
				int count = 0 ;
				// AUTOCRIB_DATA_PROC_PRESTAGE            
				//			            TRANSACTION_NUMBER NUMBER, TranNo
				//			            TRANSACTION_DATE DATE, date||time
				//			            INVENTORY_GROUP VARCHAR2 (30 BYTE), station
				//			            BIN VARCHAR2 (25 BYTE), 
				//			            TRANS_TYPE VARCHAR2 (5 BYTE),
				//			            ITEM_TYPE NUMBER,
				//			            QUANTITY NUMBER,
				//			            PACK_QUANTITY NUMBER,
				//			            BURN_QUANTITY NUMBER,
				//			            STATUS             VARCHAR2(20 BYTE),
				// AFTER THIS IS NOT FROM inFields
				//			            DATA_SOURCE        VARCHAR2(20 BYTE) DEFAULT 'AUTOCRIB',
				//			            LOAD_DATE          DATE                       DEFAULT SYSDATE,
				//			            UPLOAD_SEQ_ID      NUMBER,
				//			            LOAD_LINE          NUMBER
				String[] Fields = {"TRANSACTION_NUMBER","TRANSACTION_DATE","INVENTORY_GROUP","Bin","TRANSACTION_TYPE","QUANTITY","PACK_QUANTITY","BURN_QUANTITY","CUSTOMER_PART_NO","EMPLOYEE_NO"};// remove "ITEM_TYPE" (after transaction_type),remove STATUS and add CUSTOMER_PART_NO
				for(Vector<String> v : sv) {
					count++;
					try {
						//Date	ITEM_ID	Description	Qty	Prime No	Batch No	Man. Batch No	Transfer No	Engineer	Bin Location	Stock Type	SLE Date	Price
						//							String query = "insert into Consignment_Rec_proc_Prestage( Ship_confirm_date,ITEM_ID,ITEM_Description,ISSUE_QUANTITY,customer_part_no,Receipt_id_chr,first_receipt,second_receipt,misc_info,Transfer_No,Engineer,Bin_Location,Stock_Type,SLE_Date,Price,DATA_SOURCE,LOAD_DATE,STATUS,FILE_NAME,UPLOAD_SEQ_ID,PERSONNEL_ID) values(";
						//								AUTOCRIB_DATA_PROC_PRESTAGE
						String query = "insert into AUTOCRIB_DATA_PROC_PRESTAGE (";
						for(String s:Fields)
							query += s +",";
						query += "DATA_SOURCE,LOAD_DATE,personnel_id,STATUS,UPLOAD_SEQ_ID,LOAD_LINE) values(";
						int pos = 0 ;
						Date TRANSACTION_DATE = null;
						String dateStr = "";
						String timeStr = "";
						String TRANSACTION_NUMBER = "";
						String INVENTORY_GROUP = "";
						String Bin = "";
						String TRANSACTION_TYPE = "";
//						String ITEM_TYPE = "";
						BigDecimal QUANTITY = null;//"";
						BigDecimal PACK_QUANTITY = null;//"";
						BigDecimal BURN_QUANTITY = null;//"";
//						String STATUS = "";
						String CUSTOMER_PART_NO = "";
						String EMPLOYEE_NO = "";
						for(String s: v) {
							pos++;
							if( s != null)
								s = s.trim();
							if( pos == 3 && "Date :".equals(s) ) {
								pos = 2;
							}
							if( pos == 22 )  
								INVENTORY_GROUP  = s;
							if( pos == 23 )  
								Bin  = s;
							if( pos == 24 )  
								dateStr  = s;
							if( pos == 25 )  
								timeStr  = s;
							if( pos == 26 )  
								CUSTOMER_PART_NO  = s;
							if( pos == 27 )  
								TRANSACTION_TYPE  = s;
							if( pos == 28 )  {
								if( s.length() != 0 ) {
									try {
										BURN_QUANTITY = new BigDecimal(s);
									} catch(Exception ex){}
								}
							}
							if( pos == 29 )  {
								if( s.length() != 0 ) {
									try {
										QUANTITY = new BigDecimal(s);
									} catch(Exception ex){}
								}
							}
							if( pos == 30 )  {
								if( s.length() != 0 ) {
									try {
										PACK_QUANTITY = new BigDecimal(s);
									} catch(Exception ex){}
								}
							}
							if( pos == 33 )  
								EMPLOYEE_NO  = s;
//							pos++;
							
//							query += this.getSqlString(date) +",";
//							else 
//								if( pos >=5 && pos <= 8){ //numbers
//									BigDecimal number = null;
//									if( s.length() == 0 ) {
//										try {
//											number = new BigDecimal(s);
//										} catch(Exception ex){}
//									}
//									query += this.getSqlString(number) +",";
//								}
//								else { // varchar
//									query += this.getSqlString(s) +",";
//								}
						}
						//							header = inputBean.getFileName(); 
						//				            DATA_SOURCE        VARCHAR2(20 BYTE) DEFAULT 'AUTOCRIB',
						//				            LOAD_DATE          DATE                       DEFAULT SYSDATE,
						//				            UPLOAD_SEQ_ID      NUMBER,
						//				            LOAD_LINE          NUMBER
						try {
							TRANSACTION_DATE = dateFormat.parse(dateStr+" "+timeStr);
						}catch(Exception ex){}
//						{"TRANSACTION_NUMBER","TRANSACTION_DATE","INVENTORY_GROUP","Bin","TRANSACTION_TYPE","QUANTITY","PACK_QUANTITY","BURN_QUANTITY","CUSTOMER_PART_NO"};// remove STATUS and add CUSTOMER_PART_NO						
						TRANSACTION_NUMBER = INVENTORY_GROUP+"|"+dateStr+"|"+timeStr+"|"+CUSTOMER_PART_NO+"|"+TRANSACTION_TYPE;
						query += this.getSqlString(TRANSACTION_NUMBER) +",";
						query += this.getSqlString(TRANSACTION_DATE) +",";
						query += this.getSqlString(INVENTORY_GROUP) +",";
						query += this.getSqlString(Bin) +",";
						query += this.getSqlString(TRANSACTION_TYPE) +",";
						query += this.getSqlString(QUANTITY) +",";
						query += this.getSqlString(PACK_QUANTITY) +",";
						query += this.getSqlString(BURN_QUANTITY) +",";
						query += this.getSqlString(CUSTOMER_PART_NO) +",";
						query += this.getSqlString(EMPLOYEE_NO) +",";
						query += "'AUTOCRIBFile',sysdate,"+inputBean.getEnteredBy()+",'NEW',"+nextupLoadSeq+","+(loadcount+1)+")";// load line starts from one.
						if( !existed_transactions.contains(TRANSACTION_NUMBER) ) {
							if( test )
								System.out.println(query);
							else {
								this.factory.deleteInsertUpdate(query);
							}
							loadcount++;
						}
					}  catch(Exception ex){error += ("<br/>Encountered problem on line: "+count); missing = true;}
				}
				log.debug("calling proc with records: " + loadcount);
				if( !test )
				{
						log.debug( "calling pkg_consignment_inventory.p_load_autocrib_data for uploadseq: "+ nextupLoadSeq);
						Collection coll = factory.doProcedure("pkg_consignment_inventory.p_load_autocrib_data", 
								buildProcedureInput(nextupLoadSeq),new Vector());
				}
			}
			catch(Exception ex){error = ex.getMessage();ex.printStackTrace();}
			finally{ try{ ff.close(); }catch(Exception eee){} }
		}
		//				else {
		//					log.debug("file is null");
		//				}
		//				if( loadcount != 0 )
		error += "<br/>Record count: " + loadcount + "<br/>File was uploaded into the system for processing. You will be informed when it is processed.";
		if( missing ) {
			error += "<br/><br/>To resolve errors:<br/>1: Copy the uploaded file to a new file.<br/>2. Fix the bad record in the new file.<br/>3. Load the new file.";
		}
		return error;
	}
	
	public String processSAS(ScannerUploadInputBean inputBean,PersonnelBean pbean) throws BaseException {

		// thcnage this from true for false for production.		
		boolean test = false;
		int loadcount = 0 ;
		String error = "";
		boolean missing = false;
		// get 
		//				String fileName = inputBean.getDocumentName();
		//				int ind = fileName.indexOf("/");
		//				if( ind != -1 )
		//					fileName = fileName.substring(fileName.indexOf("/"));
		//				ind = fileName.lastIndexOf("\\");
		//				if( ind != -1 )
		//					fileName = fileName.substring(fileName.indexOf("\\"));
		//				String countStr = this.factory.selectSingleValue("Select count (*) from consignment_rec_proc_stage where file_name  = "+this.getSqlString(fileName) );
		//				if( !countStr.equals("0") ) {
		//					ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		//					return java.text.MessageFormat.format(res.getString("error.consigmnentfileloaded"),fileName);
		//				}
//		error += "<br/>Total records loaded: " + loadcount;

		if (inputBean.getTheFile() == null) {
			log.debug("Autocrib data file is null");
			return "<br/>File is Empty";
		}
		if(log.isDebugEnabled()) {
			log.debug(inputBean.getTheFile().getName());
		}
		FileInputStream ff = null;
		//				if (inputBean.getTheFile() != null) 
		{
			//					if(log.isDebugEnabled()) {
			//						log.debug(inputBean.getTheFile().getName());
			//					}
			//					DbManager dbManager = new DbManager(getClient());
			//					GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);

			// reading the file and putting the values in a bean
			try {
        		String dirname = library.getString("upload.backupdir");
        		File dir = new File(dirname);
        		File f = File.createTempFile("SAS_", ".xlsx", dir);
				FileHandler.copy(inputBean.getTheFile(),f);
				String fName = f.getAbsolutePath();				
//	public String processSAS(ScannerUploadInputBean String fName,PersonnelBean pbean) throws BaseException {

		// thcnage this from true for false for production.		
		// get 
		String docName = inputBean.getDocumentName();
		if(docName == null) docName = "";
		int ind = docName.indexOf("/");
		if( ind != -1 )
			docName = docName.substring(docName.lastIndexOf("/")+1);
		ind = docName.lastIndexOf("\\");
		if( ind != -1 )
			docName = docName.substring(docName.lastIndexOf("\\")+1);
		if( docName.length() > 50 )
			docName = docName.substring(docName.length()-50);
		
//		String countStr = this.factory.selectSingleValue("Select count (*) from consignment_rec_proc_stage where file_name  = "+this.getSqlString(fileName) );
//		if( !countStr.equals("0") ) {
//			ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
//			return java.text.MessageFormat.format(res.getString("error.consigmnentfileloaded"),fileName);
//		}
//		FileInputStream ff = null;
//		WHS:Status PN:RMC PN:HAAS PN:Descr:Trans date:Time:SAS Order Nr:Trans Type:Trans Type Desc:Move description:SAS Lot  Nr:Basic U/M:DIM:Qty:New OHB:Price:Order Value:Lot Ref 1:LOT Ref 2:MTRFTX:
//		try {
//				fName = "C:\\GeneratedJava\\SAS\\UsageW12.xlsx";
        Workbook wb = new XSSFWorkbook(fName);
//		if (inputBean.getTheFile() != null)         {
//        	if(log.isDebugEnabled()) {
//        		log.debug(fileName);
//        	}
        	//			DbManager dbManager = new DbManager(getClient());
        	//			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
        	Collection inArgs = null;
        	Collection outArgs = null;
        	Iterator iterator = null;
        	String message = null;

        	// reading the file and putting the values in a bean
//        	try {
        		// Larry: uncover this line.
        		//				FileHandler.copy(inputBean.getTheFile(),f);
        		//        	dirname = "c:\\GeneratedJava\\";
        		//				StringBuilder xmlString = new StringBuilder();
        		//    	  java.io.PrintWriter outtxt = new java.io.PrintWriter(new File("C:\\AirgasMapping.txt"));
        		//				BigDecimal nextupLoadSeq = getNextUpLoadSeq(dbManager); //this.factory.selectSingleValue("select dual from upload_sequence");
        		BigDecimal nextupLoadSeq = this.factory.getSequence("upload_sequence");//getNextUpLoadSeq(dbManager); //this.factory.selectSingleValue("select dual from upload_sequence");
        		//				SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm" );
        		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMdd HH:mm" ); //10-Mar-11 12:02

        		//				java.util.Date now = new java.util.Date();
        		//				String nowS = dateFormat.format(now);
        		//				ff = new FileInputStream(inputBean.getTheFile());
        		ff = new FileInputStream(fName);

        		String header ="";
        		int count = 0 ;
        		Sheet sheet = null;
        		for (int ii = 0; ii < wb.getNumberOfSheets(); ii++) {
        			sheet = wb.getSheetAt(ii);
        			break;
        		}
        		for (Row row : sheet) {
        			count++;
        			String dateS = null;
        			if( count == 1 )  
        				continue;
        			String query = "insert into TCM_OPS.SAS_DATA_PROCESS_PRESTAGE( INVENTORY_GROUP,PN_STATUS,CUSTOMER_PART_NO,ITEM_ID,ITEM_DESC,TRANSACTION_DATE,CUSTOMER_ORDER_NO,TRANSACTION_TYPE,TRANSACTION_TYPE_DESC,TRANSFER,SAS_LOT_NUM,BASE_UOM,PART_SIZE,QUANTITY,NEW_QUANTITY,PRICE,NET_AMOUNT,LOT_REF1,LOT_REF2,TRANSFER_ISSUE_ID,DATA_SOURCE,LOAD_DATE,STATUS,UPLOAD_SEQ_ID,LOAD_LINE,FILE_NAME) values("; 
        			try {
        				int pos = 0;
        				boolean needskip = true;
        				for (Cell cell : row) {
        					pos++;
        					if( needskip && pos == 3 ) {
        						pos--; // should be 2 now.
        						needskip = false;
        						continue; // skip the third column ( Status WHS ) 
        					}
        					String ss = cell.toString();
        					//							if( ss == null || ss.trim().length()== 0)continue; // empty line
        					if( ss != null)
        						ss = ss.trim();
        					if( pos == 6 ) {
        						dateS = ((int)cell.getNumericCellValue()) +" ";
        						continue;
        					}
        					if( pos == 7 ) {
        						dateS += ss;
        						Date date = null;
        						try {
        							date = dateFormat.parse(dateS);
        						} catch(Exception eee){};
        						query += this.getSqlString(date) +",";
        						continue;
        					}
        					//								INVENTORY_GROUP	1		Y	VARCHAR2 (30 Byte)		No
        					//								PN_STATUS	2		Y	NUMBER		No
        					//								CUSTOMER_PART_NO	3		Y	VARCHAR2 (30 Byte)		No
        					//								ITEM_ID	4		Y	NUMBER		No
        					//								ITEM_DESC	5		Y	VARCHAR2 (200 Byte)		No
        					//								TRANSACTION_DATE	6		Y	DATE		No
        					//								CUSTOMER_ORDER_NO	7		Y	VARCHAR2 (30 Byte)		No
        					//								TRANSACTION_TYPE	8		Y	NUMBER		No
        					//								TRANSACTION_TYPE_DESC	9		Y	VARCHAR2 (200 Byte)		No
        					//								TRANSFER	10		Y	VARCHAR2 (100 Byte)		No
        					//								SAS_LOT_NUM	11		Y	NUMBER		No
        					//								BASE_UOM	12		Y	VARCHAR2 (2 Byte)		No
        					//								PART_SIZE	13		Y	VARCHAR2 (100 Byte)		No
        					//								QUANTITY	14		Y	NUMBER		No
        					//								NEW_QUANTITY	15		Y	NUMBER		No
        					//								PRICE	16		Y	NUMBER		No
        					//								NET_AMOUNT	17		Y	NUMBER		No
        					//								LOT_REF1	18		Y	VARCHAR2 (100 Byte)		No
        					//								LOT_REF2	19		Y	VARCHAR2 (100 Byte)		No
        					//								TRANSFER_ISSUE_ID	20		Y	VARCHAR2 (30 Byte)		No
        					//								DATA_SOURCE	21		Y	VARCHAR2 (20 Byte)	'SAS'	No
        					//								LOAD_DATE	22		Y	DATE	SYSDATE	No
        					//								STATUS	23		Y	VARCHAR2 (20 Byte)		No
        					//								UPLOAD_SEQ_ID	24		Y	NUMBER		No
        					//								LOAD_LINE	25		Y	NUMBER		No

        					//						String query = "insert into TCM_OPS.SAS_DATA_PROCESS_PRESTAGE( Ship_confirm_date,ITEM_ID,ITEM_Description,ISSUE_QUANTITY,customer_part_no,Receipt_id_chr,first_receipt,second_receipt,                      Engineer,                                 Price,DATA_SOURCE,LOAD_DATE,STATUS,'SAS',UPLOAD_SEQ_ID,LOAD_LINE,PERSONNEL_ID) values(";
        					//						for(String s: v) {
        					
        					if( pos ==2  ) { //numbers as string 
//        						BigDecimal number = null;
//        						if( ss.length() != 0 ) {
//        							try {
//        								number = new BigDecimal(ss);
//        							} catch(Exception ex){}
//        						}
        						query += ss +",";
        					}
        					else  if( pos == 4|| pos == 9 )  { //numbers || pos == 12 is like 2 number but in excel is a string.
        						BigDecimal number = null;
        						if( ss.length() != 0 ) {
        							try {
        								int ii = ((int)cell.getNumericCellValue());
        								number = new BigDecimal(ii);
        							} catch(Exception ex){
//        								ex.printStackTrace();
        							}
        						}
        						query += this.getSqlString(number) +",";
        					}
        					else if( pos >=15 && pos <= 18) { //numbers
        						BigDecimal number = null;
        						if( ss.length() != 0 ) {
        							try {
        								number = new BigDecimal(ss);
        							} catch(Exception ex){}
        						}
        						query += this.getSqlString(number) +",";
        					}
        					else { // varchar
        						query += this.getSqlString(ss) +",";
        					}
        				}             
        				query += "'SAS',sysdate,'NEW',"+nextupLoadSeq+","+(loadcount+1)+","+this.getSqlString(docName)+")";// load line starts from one.
        				if( test )
        					System.out.println(query);
        				else
        					this.factory.deleteInsertUpdate(query);
        				loadcount++;
        			}  catch(Exception ex){ error += ("<br/>Encounter problem on line: "+count); missing = true;}
        		}
        		System.out.println("calling proc with records: " + loadcount);
        		if( !test ) {
        			SASConsignmentCountUploadWorkerProcess p = new SASConsignmentCountUploadWorkerProcess(this.client,nextupLoadSeq,new BigDecimal(pbean.getPersonnelId())); 
    				Thread thread = new Thread(p);
    				thread.start();
        			
        		}
        	}
        	catch(Exception ex){error = ex.getMessage();ex.printStackTrace();}
			finally{ try{ ff.close(); }catch(Exception eee){} }
        }
		//		if( loadcount != 0 )
		error += "<br/>Total records loaded: " + loadcount;
		if( missing ) {
			error += "<br/><br/>1: Copy the uploaded file to a new file.<br/>2. Fix the bad record in the new file.<br/>3. Remove all the loaded records from the  new file.<br/>4. Load the new file.";
		}
		return error;
	}
	public static void main(String[] args) {
		try {
		try {
	    	String fname = "C:\\GeneratedJava\\SAS\\BAE_20-02-12.txt";//UsageW12.xlsx";

//			FileInputStream ff = new FileInputStream("c:\\GeneratedJava\\test1020.xls");//SAS\\UsageW12_2.xls");
	    	ConsignmentCountUploadProcess process  = new ConsignmentCountUploadProcess("TCM_OPS");
	    	ScannerUploadInputBean inputBean = new ScannerUploadInputBean();
	    	inputBean.setTheFile(new File(fname));
	    	inputBean.setDocumentName(fname);
	    	PersonnelBean pbean = new PersonnelBean();
	    	pbean.setPersonnelId(15286);
			process.uploadBAEFile(inputBean,pbean);
//			process.processSAS(inputBean,pbean);
		}catch(RuntimeException ex){
			ex.printStackTrace();
		}
	}catch(Exception eex){
		eex.printStackTrace();
	}
	}
}
