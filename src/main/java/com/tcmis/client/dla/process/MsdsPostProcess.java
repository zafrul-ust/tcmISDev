package com.tcmis.client.dla.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:48:59 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;



import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.xmlbeans.XmlCalendar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcmis.client.dla.beans.DlaGasRoadmapViewBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.PasswordProcess;
import com.tcmis.common.db.ConnectionPool;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.InvalidPasswordException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

import com.tcmis.ws.scanner.beans.PersonnelBean;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.CabinetListBean;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.Receipt;
//import com.tcmis.ws.scanner.beans.ScanDataList;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.Duration;

/**
 * ***************************************************************************
 * Process for processing kilfrost orders.
 *
 * @version 1.0
 *          ***************************************************************************
package com.tcmis.ws.scanner.service;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.org.apache.xalan.internal.xslt.Process;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.ConnectionPool;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.ScanDataBean;
import com.tcmis.internal.hub.factory.CabinetInventoryCountStageBeanFactory;
import com.tcmis.internal.hub.process.CabinetCountProcess;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.CabinetListBean;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.PersonnelBean;
import com.tcmis.ws.scanner.beans.Receipt;
import com.tcmis.ws.scanner.beans.ScanDataList;

public class ScannerService{
	
	public String testEcho(String str){ 
		return str; 
	}
	public PersonnelBean getPersonnel(String logonId){
		Connection conn = null;
		PersonnelBean personnel = new PersonnelBean();
		
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
////			conn = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmqa","tcm_ops","n0s3ha1r");
    		conn = ConnectionPool.getConnection("TCM_OPS");
			Statement st = conn.createStatement();
			
			//get resultset
			personnel.setStatus("501");
			if( logonId == null || logonId.length() == 0 ) throw new Exception("logonId required.");
			String query = "select personnel_id, logon_id from global.personnel where lower(logon_id) = '"+logonId.toLowerCase()+"'";
			
			ResultSet rs = st.executeQuery(query);
			boolean hasData = false;
			BigDecimal personnelId = null;
			while (rs.next()) {
				personnel.setLogonId( rs.getString("logon_id") );
				personnelId=rs.getBigDecimal("personnel_id");
				personnel.setPersonnelId( personnelId );
				personnel.setStatus("502");
				hasData = true;
			}			
			rs.close();
			if( !hasData ) return personnel;
// new query with cabinet			
//			query = "SELECT DISTINCT b.company_id,b.facility_id,facility_name FROM " +
//			"customer.user_group_member_cabinet b, customer.facility f where b.company_id = f.company_id and b.facility_id = f.facility_id and b.user_group_id = 'Scanner Fucntions' AND b.personnel_id = " +
//					personnelId + " ) ORDER BY facility_name"; 
			
			query = "SELECT DISTINCT company_id,facility_id,facility_name FROM hub_cabinet_view WHERE facility_id IN" + 
			        "( SELECT facility_id FROM user_group_member WHERE user_group_id = 'Scanner Fucntions' AND personnel_id = " +
			        personnelId + " ) ORDER BY facility_name"; 
			rs = st.executeQuery(query);
			System.out.println(query);
			Vector<Facility> facs = new Vector();
			boolean begin = true;
			while (rs.next()) {
				String cid = rs.getString(1);
				String id = rs.getString(2);
				String name = rs.getString(3);
				Facility f = new Facility();
				f.setFacilityId(id);
				f.setFacilityName(name);
				facs.add(f);
			}
			int size = facs.size();
			Facility[] facss = new Facility[size];
			facs.copyInto(facss);
			personnel.setFacilityInfo(facss);
			personnel.setStatus("OK");
			rs.close();	
			st.close();
		}catch(Exception ex){ ex.printStackTrace();throw new RuntimeException("cannot continue");}
		finally{ try{ conn.close(); }catch(Exception ex){} }

		return personnel;
	}
	public CabinetListBean getCabinetReceiptItem(PersonnelBean personnel){
		Connection conn = null;
		CabinetListBean list = new CabinetListBean();
		
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmqa","tcm_ops","n0s3ha1r");
			//    		conn = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@phoenix.tcmis.com:1521:tcmqa","tcm_ops","n0s3ha1r");
    		conn = ConnectionPool.getConnection("TCM_OPS");
			Statement st = conn.createStatement();
			
			//get resultset
			list.setStatus("501");
			BigDecimal personnelId = personnel.getPersonnelId();
			if( personnelId == null ) throw new Exception("Personnel Id required.");
			list.setPersonnelId(personnelId);
			Facility[] facss = personnel.getFacilityInfo();
			if( facss == null || facss.length == 0 ) throw new Exception("Facility Id(s) required.");
			list.setStatus("502");
// new query with user_group_member_cabinet.
//			StringBuilder faclist = new StringBuilder("select string_agg(c.cabinet_id) " +
//					"FROM customer.user_group_member_cabinet b, customer.cabinet c " + 
//					"where b.company_id = c.company_id and b.facility_id = c.facility_id " + 
//					"and b.application = c.ordering_application "+
//					"and b.user_group_id = 'Scanner Fucntions' AND b.personnel_id = " +	personnelId + 
//					" and b.facility_id in ('");			
			StringBuilder faclist = new StringBuilder("select string_agg(cabinet_id) from hub_cabinet_view where facility_id in ('");
			int len = facss.length;
			int i = 0;
			String pre="";
			try {
			for( ;i< len ;i++) {
				String facilityId = facss[i].getFacilityId().trim();
				if( facilityId.length() == 0 ) throw new Exception("");
				faclist.append(pre);
				faclist.append(facilityId+"'");
				pre =",'";
			}
			faclist.append(")");
			}catch(Exception ex) { throw new Exception("Valid Facility Id required. Position:"+i);}
			list.setStatus("503");
			

			String query = faclist.toString();
			
			ResultSet rs = st.executeQuery(query);
			System.out.println(query);
			boolean hasData = false;
			String binlist = null;
			while (rs.next()) {
				binlist = rs.getString(1);
				hasData = true;
			}			
			rs.close();
			if( !hasData ) return list;
			list.setStatus("504");
//			list.setCabinetlist(binlist);
			query = "SELECT distinct receipt_id, customer_receipt_id,item_id FROM cabinet_receipt_item_view WHERE cabinet_id IN("+binlist+
					") AND item_id IS NOT NULL ORDER BY customer_receipt_id ASC";
			rs = st.executeQuery(query);
			System.out.println(query);
			Vector<Receipt> recs = new Vector();
			boolean begin = true;
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String item = rs.getString(3);
				Receipt f = new Receipt();
				f.setReceiptId(id);
				f.setCustomerReceiptId(name);
				f.setItemId(item);
				recs.add(f);
			}
			int size = recs.size();
			Receipt[] recss = new Receipt[size];
			recs.copyInto(recss);
			list.setReceiptInfo(recss);
			personnel.setStatus("OK");
			rs.close();
			
			list.setStatus("505");
			query = "SELECT distinct bin_id,bin_name, item_id,cabinet_id,company_id,status,count_type FROM     cabinet_bin_item_view WHERE cabinet_id IN("+binlist+
			        ") AND item_id IS NOT NULL ORDER BY cabinet_id, bin_id,item_id ASC";
			rs = st.executeQuery(query);
			System.out.println(query);
			Vector<BinItem> items = new Vector();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String item = rs.getString(3);
				String cabinet = rs.getString(4);
				String company = rs.getString(5);
				String status = rs.getString(6);
				String countType = rs.getString(7);
				
				BinItem f = new BinItem();
				f.setBinId(id);
				f.setBinName(name);
				f.setItemId(item);
				f.setCabinetId(cabinet);
				f.setCompanyId(company);
				f.setStatus(status);
				f.setCountType(countType);
				
				items.add(f);
			}
			int isize = items.size();
			BinItem[] iss = new BinItem[isize];
			items.copyInto(iss);
			list.setItemInfo(iss);
			list.setStatus("OK");
			rs.close();	
			st.close();
		}catch(Exception ex){ ex.printStackTrace();throw new RuntimeException("cannot continue");}
		finally{ try{ conn.close(); }catch(Exception ex){} }

		return list;
	}

	private BigDecimal getNextUpLoadSeq(DbManager dbManager1) 
    {
     BigDecimal b = null;
     Connection connection = null;
     try {
     	connection = dbManager1.getConnection();
         b = new BigDecimal("" +new SqlManager().getOracleSequence(connection, "upload_sequence"));
     }
     catch(Exception ex){}
     finally {
    	 try {
    		 dbManager1.returnConnection(connection);
    	 }
     	 catch(Exception ee){}
 		dbManager1 = null;
 		connection = null;
     }
     return b;
    }

	public String doUpload(ScanDataList beans, BigDecimal personnelId) {
//        BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());

		{
			// copy file to server
//			File f = bean.getTheFile();
//			String fileType = f.getName().substring(f.getName().lastIndexOf("."));
//			bean.setFileName(fileType);
			ResourceLibrary resource = new ResourceLibrary("scannerupload");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssS");
			String backupdir = resource.getString("upload.backupdir");
			if( backupdir == null ) backupdir = "";
			String filename = backupdir + "Upload"+format.format(new Date())+".xls";
			File saveFile = new File(filename);
			ExcelHandler pw = new ExcelHandler(resource);
			pw.addRow();
			pw.addCell("Bin Id");
			pw.addCell("Receipt Id");
			pw.addCell("Quantity");
			pw.addCell("Date/Time");
			pw.addCell("Personnel Id");
			pw.addCell("Company Id");
			pw.addCell("Count Type");
			for(ScanDataBean scannedBean:beans.getScanData()) {
				pw.addRow();
				pw.addCell(scannedBean.getBinId());
				pw.addCell(scannedBean.getReceiptId());
				pw.addCell(scannedBean.getCountQuantity());
				pw.addCell(scannedBean.getCountDatetime());
				pw.addCell(scannedBean.getPersonnelId());
				pw.addCell(scannedBean.getCompanyId());
				pw.addCell(scannedBean.getCountType());
			}
			try {
				pw.write(new FileOutputStream(saveFile));
			} catch(Exception ex){
				return "Fail To Generate Backup File, Please contact TCMIS";
			}
//			MailProcess.sendEmail = false;
//			if( 1==1 ) return "OK";
			CabinetCountProcess.sendErrorEmail("ScanUpload", "data backup file", filename);
			BigDecimal nextupLoadSeq = null;
			try {
			DbManager dbManager = new DbManager("TCM_OPS");
			CabinetInventoryCountStageBeanFactory factory = new CabinetInventoryCountStageBeanFactory(dbManager);
			
			Collection binIdsScanned = new Vector();
			String mrsCreated = "";
	        String companyId = "";
	        nextupLoadSeq = getNextUpLoadSeq(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			factory.delete(criteria);
			} catch(Exception ee){}
			if( nextupLoadSeq == null )
				return "Cannot initialize database sequence";
			CabinetCountWorkerProcess p = new CabinetCountWorkerProcess(beans,personnelId,nextupLoadSeq); 
			Thread thread = new Thread(p);
			thread.start();
		}
	return "OK";
	}
}
 */

public class MsdsPostProcess
extends GenericProcess{
	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;

	public MsdsPostProcess(String client) throws BaseException {
		super(client);
		library = new ResourceLibrary("scannerupload");
	}

	public String getPersonnel(String Company,String logonId,String password,String noFacilities){
		Connection conn = null;
		try { try { // This is to make sure connection is closed.
//			Class.forName("oracle.jdbc.driver.OracleDriver");
////			conn = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmqa","tcm_ops","n0s3ha1r");
// xml date.			
//			GregorianCalendar cal =  
//			Calendar cal = GregorianCalendar.getInstance();
////			XmlCalendar cal = new XmlCalendar();//.getInstance();
//			cal.add(Calendar.HOUR,12); cal.set(Calendar.MILLISECOND, 0);
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
////			String validUntil = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)cal).toXMLFormat();//.toString();
////			String validUntil = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)cal).toXMLFormat();//.toString();
//			String validUntil = format.format(cal.getTime());
//			validUntil = validUntil.substring(0,validUntil.length()-2)+":00";
			
//			String token = Calendar.getInstance().getTimeInMillis()+"";
//			String token = this.factory.getFunctionValue("sys_guid");
//		    String s = getProcError(
//					   buildProcedureInput(
//							   logonId,
//							   Company,
//							   token
//					   ),null,"Pkg_token.p_insert_tcmis_token");
//			if( !isBlank(s) ) return personnel;
			
//			tq = "insert into tcmis_token(user_name,user_info,client_name,last_update_time,token) values( '"+logonId.toLowerCase()+"'" + 
//				",'wstoken','"+Company+"',sysdate +.5,'"+token+"')";
//			personnel.setToken(token);
//			this.factory.deleteInsertUpdate(tq);

//			personnel.setReason("Cannot find data for personnel:"+logonId);
//			conn = dbManager.getConnection();
//			Statement st = conn.createStatement();
//
//			//get resultset
//			String query = "select personnel_id, logon_id from personnel where lower(logon_id) = '"+logonId.toLowerCase()+"'";
//			log.debug(query);
//
//			ResultSet rs = st.executeQuery(query);
//			boolean hasData = false;
//			BigDecimal personnelId = null;
//			while (rs.next()) {
//				personnel.setLogonId( rs.getString("logon_id") );
//				personnelId=rs.getBigDecimal("personnel_id");
//				personnel.setPersonnelId( personnelId );
//				hasData = true;
//			}			
//			rs.close();
//			st.close();
//			if( !hasData ) return personnel;
// new query with cabinet			
//			query = "SELECT DISTINCT b.company_id,b.facility_id,facility_name FROM " +
//			"customer.user_group_member_cabinet b, customer.facility f where b.company_id = f.company_id and b.facility_id = f.facility_id and b.user_group_id = 'Scanner Fucntions' AND b.personnel_id = " +
//					personnelId + " ) ORDER BY facility_name"; 
		}
		finally{ if( conn != null ) conn.close(); }
	    }catch(Exception ex){ ex.printStackTrace(); } 

		return "";
	}


//	public String processOrder(String company,String xmlString, String noFacilities) throws
//	BaseException {
//		try {
//			String dirname = library.getString("upload.backupdir");
//			//        	dirname = "c:\\GeneratedJava\\";
//			File orderFile = FileHandler.saveTempFile(xmlString, "Personnel_", ".xml", dirname);
//			OrderParser parser = new OrderParser(getClient());
//			PersonnelBean personnel = parser.parsePersonnel(orderFile);
//			PersonnelBean list = getPersonnel(company,personnel.getLogonId(),personnel.getPassword(),noFacilities);
//			return getResponseXml(company,list,noFacilities);
//			
//		}
//		catch(Exception e) {
//			log.fatal("Error processing Personnel", e);
//			throw new BaseException(e.getCause());
//		}
//	}


	public String getPostXml() throws BaseException {
		StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://deliveryorder/\">\n<soap:Header/>\n\n\n");
		try {
			String filename = new Date().toString();//"testfile";
			this.factory.setBean(new DlaGasRoadmapViewBean());
			Vector<DlaGasRoadmapViewBean> data = (Vector<DlaGasRoadmapViewBean>)this.factory.selectQuery("select * from DLA_GAS_ROADMAP_VIEW");
//			<soap:Body><ns1:sendFileElement xmlns:ns1="http://delivery.msds.dscr.dla.mil/"><ns1:file_name>testfile</ns1:file_name><ns1:arr><ns1:nsn>6830-01-296-2459</ns1:nsn><ns1:contractor_serial_number>15725405012007</ns1:contractor_serial_number><ns1:contract_and_do>SPM4AR-07-D-0100-08ED</ns1:contract_and_do><ns1:vendor_sos>Advanced Specialty Gases Inc</ns1:vendor_sos><ns1:rp_mfgr_cage>1SKZ6</ns1:rp_mfgr_cage><ns1:msds_responsible_party>Advanced Specialty Gases</ns1:msds_responsible_party><ns1:vendor_sos_cage>1SKZ6</ns1:vendor_sos_cage><ns1:msds_preparation_rev_date>5/1/2007</ns1:msds_preparation_rev_date><ns1:product_name>Freon 116 Refridgerant/Nitrous Oxide Blend 90/10</ns1:product_name></ns1:arr></ns1:sendFileElement></soap:Body></soap:Envelope>
			sw.append("<soap:Body>\n<ns1:sendFile>\n");
//			<ns1:arr><ns1:nsn>6830-01-296-2459</ns1:nsn><ns1:contractor_serial_number>15725405012007</ns1:contractor_serial_number>
			//<ns1:contract_and_do>SPM4AR-07-D-0100-08ED</ns1:contract_and_do><ns1:vendor_sos>
//			Advanced Specialty Gases Inc</ns1:vendor_sos>
//			<ns1:rp_mfgr_cage>1SKZ6</ns1:rp_mfgr_cage>
//			<ns1:msds_responsible_party>Advanced Specialty Gases</ns1:msds_responsible_party>
//			<ns1:vendor_sos_cage>1SKZ6</ns1:vendor_sos_cage>
//			<ns1:msds_preparation_rev_date>5/1/2007</ns1:msds_preparation_rev_date>
//			<ns1:product_name>Freon 116 Refridgerant/Nitrous Oxide Blend 90/10</ns1:product_name></ns1:arr></ns1:sendFileElement></soap:Body></soap:Envelope>
			int count = 0 ;
			SimpleDateFormat format = new SimpleDateFormat("EEMMMddyyyySSS");
			sw.append("<arg0>msds_govt_");
			long timeNow = new Date().getTime();;
			Date timeExpire = new Date(System.currentTimeMillis()+20*60*1000);
			sw.append(format.format(timeNow));
			sw.append(".xml</arg0>\n");
			format.applyPattern("yyyyMMdd");
				
			for(DlaGasRoadmapViewBean rec:data) {
				count++;
				String dates = "";
				try {
					dates = new SimpleDateFormat("yyyyMMdd").format(rec.getLatestRevDate()); 
				} catch(Exception eee){};
//				if( count == 1 ) continue;
				sw.append("<arg1>");
				sw.append("<nsn>"+rec.getNsn()+"</nsn>");
				sw.append("<contractor_serial_number>"+rec.getMsdsId()+"</contractor_serial_number>");
				sw.append("<contract_and_do>"+rec.getContractNumber()+"</contract_and_do>");
				
				
				format.applyPattern("MM/dd/yyyy HH:mm");
				sw.append("<created_time_stamp>"+format.format(timeNow)+"</created_time_stamp>");
				sw.append("<expired_time_stamp>"+format.format(timeExpire)+"</expired_time_stamp>");				

				sw.append("<vendor_sos>"+ StringEscapeUtils.escapeXml( rec.getManufacturer() )+"</vendor_sos>");
				sw.append("<rp_mfgr_cage>"+rec.getManufacturerCage()+"</rp_mfgr_cage>");
				sw.append("<msds_responsible_party>"+ StringEscapeUtils.escapeXml( rec.getSubcontractor() )+"</msds_responsible_party>");
				sw.append("<vendor_sos_cage>"+rec.getSubcontractorCage()+"</vendor_sos_cage>");
				sw.append("<msds_preparation_rev_date>"+dates+"</msds_preparation_rev_date>");
				sw.append("<product_name>"+StringEscapeUtils.escapeXml( rec.getTradeName() )+"</product_name>");
				sw.append("</arg1>\n");
//	        	rec.setNsn("6830-01-296-2459"); //NSN
//	        	rec.setContractor_serial_number("15725405012007"); //MSDS_ID
//	        	rec.setContract_and_do("SPM4AR-07-D-0100-08ED"); //CONTRACT_NUMBER
//	        	rec.setVendor_sos("Advanced Specialty Gases Inc"); //MANUFACTURER
//	        	rec.setRp_mfgr_cage("1SKZ6"); //MANUFACTURER_CAGE
//	        	rec.setMsds_responsible_party("Advanced Specialty Gases"); //SUBCONTRACTOR
//	        	rec.setVendor_sos_cage("1SKZ6"); //SUBCONTRACTOR_CAGE
//	        	rec.setMsds_preparation_rev_date("5/1/2007"); //LAST_REV_DATE
//	        	rec.setProduct_name("Freon 116 Refridgerant/Nitrous Oxide Blend 90/10"); //TRADE_NAME
			}
			sw.append("</ns1:sendFile>\n</soap:Body>\n</soap:Envelope>\n");
			
			
//			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//			Document document = documentBuilder.newDocument();
//			Element
//			rootElement  = document.createElement("CabinetReceiptItemResponse");
//			rootElement.setAttributeNS("http://beans.scanner.ws.tcmis.com/xsd", "xmlns", "");
//			document.appendChild(rootElement);
//
//			Element
//			BinItems  = document.createElement("BinItems");
//			for(BinItem bin:list.getItemInfo()) {
//			Element
//			BinId  = document.createElement("BinId");
//			BinId.appendChild(document.createTextNode(bin.getBinId()));
//			BinItems.appendChild(BinId);
//			Element
//			orderNumberElement  = document.createElement("BinName");
//			orderNumberElement.appendChild(document.createTextNode(bin.getBinName());
//			}
//			DOMSource source = new DOMSource(document);
//			StreamResult result = new StreamResult(new StringOutputStream(sw));
//			TransformerFactory transFactory = TransformerFactory.newInstance();
//			Transformer transformer = transFactory.newTransformer();
//			transformer.transform(source, result);
			if (log.isDebugEnabled()) {
				log.debug("returning:" + sw.toString());
			}
			System.out.println("filename:"+filename+"\n\n\n");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new BaseException(e);
		}
		return sw.toString();
	}
	public boolean isValidPassword(String companyId,String logonId,String password) {
//		if (log.isInfoEnabled()) {
//			log.info(loginBean.getLogonId() + " logging in for " + loginBean.getClient());
//		}
		boolean r = false;
		com.tcmis.common.admin.beans.PersonnelBean loginBean = new com.tcmis.common.admin.beans.PersonnelBean();
		try {
		loginBean.setCompanyId(companyId);
		loginBean.setLogonId(logonId);
		loginBean.setPassword(password);
		loginBean.setClient(this.getClient());
		
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("logonId", SearchCriterion.EQUALS, loginBean.getLogonId(), SearchCriterion.IGNORE_CASE);

		if (loginBean.getCompanyId() != null && loginBean.getCompanyId().length() > 0) {
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, loginBean.getCompanyId());
		}
		Collection c = factory.select(criteria);
		if (log.isDebugEnabled()) {
			log.debug("Collection size=" + c.size());
		}
		if (c.size() == 0) {
			throw new NoDataException("error.loginid.invalid");
		}
		Iterator iterator = c.iterator();
		Object bean =  null;
		while (iterator.hasNext()) {
			bean = iterator.next();
		}
		//check password
		PasswordProcess passwordProcess = new PasswordProcess(getClient(), (com.tcmis.common.admin.beans.PersonnelBean) bean);
		return passwordProcess.isPasswordValid(loginBean.getPassword());
		} catch(Exception e){}
		return false;
	}  

//	public static String getVerifyTokenStr(String Company,String logonId,String token) {
//		return "select token from tcmis_token where lower(user_name) = '"+logonId.toLowerCase()+"'" +
//		" and user_info = 'wstoken' and client_name = '"+Company+"'" +
//		" and last_update_time > sysdate and token ='"+token+"'";
//	}
}
