package com.tcmis.internal.distribution.process;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

public class POUploadProcess extends GenericProcess {
	ResourceLibrary library = null;

	public POUploadProcess(TcmISBaseAction client) throws BaseException{
		super(client);
		library = new ResourceLibrary("scannerupload");
	}
	public POUploadProcess(String client) throws BaseException{
		super(client);
		library = new ResourceLibrary("scannerupload");
	}

	public String uploadBAFile(ScannerUploadInputBean inputBean,PersonnelBean pbean) throws BaseException {

		int loadcount = 0 ;
		String error = "";
		boolean missing = false;
		// get 
		String fileName = inputBean.getDocumentName();
		String countStr = this.factory.selectSingleValue("Select count (*) from consignment_rec_proc_stage where file_name  = "+this.getSqlString(fileName) );
		if( !countStr.equals("0") ) {
			ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			return java.text.MessageFormat.format(res.getString("error.consigmnentfileloaded"),fileName);
		}
		File input = inputBean.getTheFile();
		if ( input != null) {
			if(log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
//			DbManager dbManager = new DbManager(getClient());
//			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection inArgs = null;
			Collection outArgs = null;
			Iterator iterator = null;
			String message = null;
			HashMap<String,Integer> pos = new HashMap();
			
			// reading the file and putting the values in a bean
			try {
				String dirname = library.getString("upload.backupdir");
				File dir = new File(dirname);
				File f = File.createTempFile("BASpec2000_", ".txt", dir);
				FileHandler.copy(inputBean.getTheFile(),f);
				//        	dirname = "c:\\GeneratedJava\\";
				StringBuilder xmlString = new StringBuilder();
				//    	  java.io.PrintWriter outtxt = new java.io.PrintWriter(new File("C:\\AirgasMapping.txt"));
				BigDecimal nextupLoadSeq = factory.getSequence("upload_sequence");//getNextUpLoadSeq(dbManager); //this.factory.selectSingleValue("select upload_sequence from dual");
				SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm" );
				//				java.util.Date now = new java.util.Date();
				//				String nowS = dateFormat.format(now);

				String table = "customer_po_pre_stage";

				BufferedReader in = new BufferedReader(new FileReader( input ));
				String str; 
				String info="";
				int i = 0 ;
				while ((str = in.readLine()) != null) 
				{   
					try {
					System.out.println(str.length());
					System.out.println(str);
					info = str;
					i++;
//				} 
//				int len = info.length();
//				int record = 342;
//				int num = len/record;
//				for(int i=0; i < num ;i++) {
					int offset = 0;//i*record;
					String po = info.substring(offset,offset+11).trim();
					String part = info.substring(offset+11,offset+26).trim();
					String qty = info.substring(offset+26,offset+31).trim();
					String price = info.substring(offset+33,offset+46).trim();
// let it break;					
//					if( price == null || price.length() == 0 ) 
//						price = "null";
					String date = info.substring(offset+307,offset+315).trim();
					String currency_id = info.substring(offset+315,offset+318).trim();
					String customerId = "2951"; 
//						this.factory.selectSingleValue("select customer_id from bill_to_customer where customer_name = 'British Airways' and currency_id = "+this.getSqlString(currency_id));
					if( isBlank(customerId) ) { 
						error += ("<br/>Encountered problem on line: "+i); missing = true;
						continue;
					}
//					if( "GBP".equals(currency_id) )
//						customerId = "2952"; // make sure we change these numbers to be the final numbers.
					log.debug(po+":"+part+":"+qty+":"+price+":"+date+":"+currency_id);
					Integer pocount = pos.get(po);
					if( pocount == null ) pocount = 1;
					else pocount++;
					pos.put(po, pocount);
					String loadId = this.factory.selectSingleValue("select customer_po_load_seq.NEXTVAL from DUAL");
//			         ResultSet resultSet = st.executeQuery("select customer_po_load_seq.NEXTVAL from DUAL");
//			         BigDecimal loadId = null;
//			         if (resultSet.next()) {
//			            loadId = resultSet.getBigDecimal("NEXTVAL");
//			         }
//			         resultSet.close();
//					customer_order_type 2 means cancel...					
					String query =  "insert into customer_po_pre_stage(customer_id,currency_id,company_id,customer_po_no,customer_part_no,quantity,unit_price,date_inserted,transport,transporter_account,trading_partner,trading_partner_id,pre_860,load_line,status,load_id,requested_delivery_date,transaction_type,catalog_company_id, catalog_id, customer_po_line_no,uom,shipto_party_id)";
					String values =	" values("+customerId+",'"+currency_id+"','BA','"+po+"','"+part+"',"+qty+","+price+",sysdate,'SITA','crawley','BA','BACrawley','N',1,'NEW',"+loadId+",sysdate+7,'850','HAAS','Global',"+pocount+",'EA','17')";
					query += values;
					this.factory.deleteInsertUpdate(query);
					loadcount++;
				}
				catch(Exception ex){ error += ("<br/>Encounter problem on line: "+i); missing = true; ex.printStackTrace();}
//				System.out.println("calling proc with records: " + loadcount);
//				Collection coll = factory.doProcedure("TCM_OPS.PKG_CONSIGNMENT_INVENTORY.P_LOAD_IPOS_DATA", 
//						buildProcedureInput(nextupLoadSeq,new BigDecimal(pbean.getPersonnelId())),new Vector());

//			      GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
//			      Vector params = new Vector();
//			      try {
//			         procFactory.doProcedure("pkg_dbuy_from_customer.pre_stage_to_stage_All",params);
//			      } catch (BaseException bep) {
//			         log.error("Base Exception calling pkg_dbuy.pre_stage_to_stage(): " + bep);
//			      }
				
			}
//			finally{ try{ ff.close(); }catch(Exception eee){} }
		}
			catch(Exception ex){error = ex.getMessage();ex.printStackTrace();}
		}		
		else {
			log.debug("file is null");
		}
//		if( loadcount != 0 )
			error += "<br/>Total records loaded: " + loadcount;
			try {
				BAWorkerProcess p = new BAWorkerProcess(this.dbManager,log); 
				Thread thread = new Thread(p);
				thread.start();
			} 
			catch(Exception ex){error = ex.getMessage();ex.printStackTrace();}
		if( missing ) {
			error += "<br/><br/>1: Copy the uploaded file to a new file.<br/>2. Fix the bad record in the new file.<br/>3. Remove all the loaded records from the  new file.<br/>4. Load the new file.";
		}
		return error;
	}

//	private BigDecimal getNextUpLoadSeq(DbManager dbManager1) 
//    {
//     BigDecimal b = null;
//     Connection connection = null;
//     try {
//     	connection = dbManager1.getConnection();
//         b = new BigDecimal("" +new SqlManager().getOracleSequence(connection, "upload_sequence"));
//     }
//     catch(Exception ex){}
//     finally {
//    	 try {
//    		 dbManager1.returnConnection(connection);
//    	 }
//     	 catch(Exception ee){}
// 		dbManager1 = null;
// 		connection = null;
//     }
//     return b;
//    }
	public static void main(String[] args) {
		try {
		try {
	    	String fname = "C:\\GeneratedJava\\ScannerUpload\\BASpec2000_54094.txt";;//SAS_46990.xlsx";//BAE_20-02-12.txt";//UsageW12.xlsx";
//	    	String fname = "C:\\GeneratedJava\\SAS\\SAS_DEMO.XLSX";;//SAS_46990.xlsx";//BAE_20-02-12.txt";//UsageW12.xlsx";

//			FileInputStream ff = new FileInputStream("c:\\GeneratedJava\\test1020.xls");//SAS\\UsageW12_2.xls");
	    	POUploadProcess process  = new POUploadProcess("TCM_OPS");
	    	ScannerUploadInputBean inputBean = new ScannerUploadInputBean();
	    	inputBean.setTheFile(new File(fname));
	    	inputBean.setDocumentName(fname);
	    	PersonnelBean pbean = new PersonnelBean();
	    	pbean.setPersonnelId(15286);
//			process.uploadBAEFile(inputBean,pbean);
//	    	process.testSAS = true;
			//process.processSAS(inputBean,pbean);

			process.uploadBAFile(inputBean,pbean);
		}catch(RuntimeException ex){
			ex.printStackTrace();
		}
	}catch(Exception eex){
		eex.printStackTrace();
	}
	}

	
}
